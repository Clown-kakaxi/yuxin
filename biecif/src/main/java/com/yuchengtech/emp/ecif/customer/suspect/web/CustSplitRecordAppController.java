/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.bione.util.PropertiesUtils;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.nio.HttpClient;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.customerinfo.service.HCustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.HCustomer;
import com.yuchengtech.emp.ecif.customer.entity.other.CustSplitRecordApproval;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.CustSplitRecordAppBS;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/customer/custsplitrecordapp")
public class CustSplitRecordAppController extends BaseController {
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private CustSplitRecordAppBS custSplitRecordAppBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ImportListBS importListBS;
	
	@Autowired
	private HCustomerInfoBS hcustomerInfoBS;
	
	//一次进入不查询
	private boolean flag = false;	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/suspect/custsplitrecordapp-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		if(flag == false){
			flag = true;
			return null;
		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<CustSplitRecordApproval> searchResult = custSplitRecordAppBS.getCustSplitRecordList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), GlobalConstants.APPROVAL_STAT_1);
		userMap.put("Rows", searchResult.getResult());
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/customer/suspect/custsplitrecordapp-edit", "id", id);
	}
	
	/**
	 * 提交审批信息
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(CustSplitRecordApproval cm, String ids) {
		//获取登入系统用户信息
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		//String 
		//获得一个或多个主键标识specialListApprovalId
		String[] temp = ids.split(",");
		if(temp.length > 0){
			//循环所有审批信息的主键标识
			for(int i=0; i<temp.length; i++){
				CustSplitRecordApproval sa = 
					(CustSplitRecordApproval) this.custSplitRecordAppBS
						.getEntityById(Long.valueOf(temp[i]));
				//判断导入提交人与审批人是否相同
				if(!user.getUserName().equals(sa.getImportOperator())){
					//设置审批字段信息
					sa.setApprovalNote(cm.getApprovalNote());
					sa.setApprovalOperator(user.getUserName());
					sa.setApprovalStat(cm.getApprovalStat());
					sa.setApprovalTime(new Timestamp(new Date().getTime()));
					//更新审批记录
					custSplitRecordAppBS.updateEntity(sa);
					//调用合并接口
					approvalInfo(sa);
				}
			}
		}
	}
	
	/**
	 * 审批方法
	 */
	public String approvalInfo(CustSplitRecordApproval sa){
		
		if(sa.getApprovalStat().equals(GlobalConstants.APPROVAL_STAT_2)){//审批通过
			//BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
			PropertiesUtils tool = new PropertiesUtils("report.properties");
			//插入直接存入数据表
			String reportCode = "";
			String reportStr = "";
			HttpClient client = new HttpClient(tool.getProperty("host"), Integer.valueOf(tool.getProperty("port")));
			String tmp;
			//ISO8859-1 UTF-8 GBK GB2312
			try {
				String report = getReportStr(sa.getMergedCustNo(), tool);
				tmp = client.interactive(report, "/ECIF");
			} catch (IOException e1) {
				tmp = "<FaultCode>error</FaultCode><FaultString>接口应用错误</FaultString><TxnStat>ERROR</TxnStat>";
			}
			//(?s)<FaultCode>(.*?)</FaultCode>.*?<FaultString>(.*?)</FaultString>
			String regex = "(?s)<FaultString>(.*?)</FaultString>.*?<TxnStat>(.*?)</TxnStat>";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(tmp);
	        while(matcher.find()) {
	        	reportCode = matcher.group(1).trim();
	        	reportStr = matcher.group(2).trim();
	        }
			if(reportStr.equals("SUCCESS")){
				return "";
			}else{
				return reportCode;
			}
		}
		return "";
	}
	
	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap = Maps.newHashMap();
//		harvMap.put("id", GlobalConstants.APPROVAL_STAT_1);
//		harvMap.put("text", "待审批");
//		harvComboList.add(harvMap);
//		harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.APPROVAL_STAT_2);
		harvMap.put("text", "审批通过");
		harvComboList.add(harvMap);
		harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.APPROVAL_STAT_3);
		harvMap.put("text", "审批未通过");
		harvComboList.add(harvMap);
		
		return harvComboList;
	}
	
	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "export.*", method = RequestMethod.POST)
	public void export(HttpServletResponse response, String file)
			throws Exception {

		if(StringUtils.isEmpty(file) || file.equals("[object Object]")){
			// 准备工作
			String realpath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("/");
			file = realpath + GlobalConstants.EXCEL_TEMPLATE_IMPCUSTSPLITRECORD_CN;
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
		}else{
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
			ExportUtil.deleteFile(file);
		}
	}
	
	/**
	 * 下载文件
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getReportFile")
	@ResponseBody
	public String getReportFile(String repo, String custNo, String identNo, String lastUpdateSys) {
		int report = Integer.parseInt(repo);
		return exportReportBS.export(report, custNo, identNo, "", "", "", "");
	}
	
	/**
	 * 跳转到上传页面
	 * @return
	 */
	@RequestMapping(value = "/importresult", method = RequestMethod.GET)
	public ModelAndView importResult() {
		return new ModelAndView("/ecif/customer/suspect/impcustsplitrecordapp-upload");
	}
	/**
	 * 开始上传并且读取文件内容
	 * @param checkResultSort
	 * @param uploader
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/startUpload")
	@ResponseBody
	public String startUpload(Uploader uploader, HttpServletResponse response) throws Exception {
		File file = null;
		try {
			file = this.uploadFile(uploader, GlobalConstants.EXCEL_IMPORT_FOLDER, false);
		} catch (Exception e) {
			logger.info("文件上传出现异常", e);
		}
		if (file != null) {
			logger.info("文件[" + file.getName() + "]上传完成");			
			//this.readFile(file, checkResultSort);
			List<List<Object[]>> reportData = importListBS.importCustSplitRecord(file.getPath(), 3);
			ExportUtil.deleteFile(file.getPath());
			if(reportData == null){
				return "没有导入数据";
			}else{
				return getResultByData(reportData);
			}
		}
		return "文件错误";
	}
	/**
	 * 导入数据处理
	 * @param reportData
	 * @return
	 */
	public String getResultByData(List<List<Object[]>> reportData){
		//
		List<List<Object[]>> errorData = new ArrayList<List<Object[]>>();
		//
		Map<String, String> codeMapApprovalStatus1 = Maps.newHashMap();
		codeMapApprovalStatus1.put("待审批", GlobalConstants.APPROVAL_STAT_1);
		codeMapApprovalStatus1.put("审批通过", GlobalConstants.APPROVAL_STAT_2);
		codeMapApprovalStatus1.put("审批未通过", GlobalConstants.APPROVAL_STAT_3);
		//
		//BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		//PropertiesUtils tool = new PropertiesUtils("report.properties");
		int result = 0;
		int count = 0;
		for(int i=0; i < reportData.size(); i++){
			List<Object[]> temp = reportData.get(i);
			List<Object[]> errorTemp = new ArrayList<Object[]>();
			if(temp == null){
				continue;
			}
			if(i == 0){//读取第一个sheet
				count = temp.size();
				//List<SpecialList> list = new ArrayList<SpecialList>();
				//CustSplitRecord sl = null;
				Object[] oTemp = null;
				for(Object[] o : temp){
					
					oTemp = Arrays.copyOf(o, o.length+1);
					//标识	被合并客户号	拆分提交人	拆分提交时间	导入人员	导入时间	
					//审批人	审批时间	审批状态	审批意见
					if(o[0] != null && !o[0].toString().equals("") &&
							o[1] != null && !o[1].toString().equals("") &&
							o[6] != null && !o[6].toString().equals("") &&
							o[7] != null && !o[7].toString().equals("") &&
							o[8] != null && !o[8].toString().equals("")){
						
						HCustomer customer1 = hcustomerInfoBS.getHCustomerByCustNo(o[1].toString());
						if(customer1 == null){
							//被合并客户号不存在
							oTemp[oTemp.length-1] = "被合并客户不存在";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						CustSplitRecordApproval sa = 
								(CustSplitRecordApproval) this.custSplitRecordAppBS.
								getCustMergeRecordAppById(o[1].toString(), o[0].toString());
						//判断信息是否存在
						if(sa == null){
							oTemp[oTemp.length-1] = "信息不存在";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//审批状态是否存在
						String stat = codeMapApprovalStatus1.get(o[8].toString());
						if(StringUtils.isEmpty(stat)){
							oTemp[oTemp.length-1] = "审批状态无法识别";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
//						//审批状态必须是待审批状态
//						if(!sa.getApprovalStat().equals(GlobalConstants.APPROVAL_STAT_1)){
//							oTemp[oTemp.length-1] = "记录的审批状态不是待审批";
//							errorTemp.add(oTemp);
//							result++;
//							continue;
//						}
						//导入操作人与审批人是否相同
						if(o[6].toString().equals(sa.getImportOperator())){
							oTemp[oTemp.length-1] = "导入提交人与审批人不可相同";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						Timestamp tempDate = null;
						try {
							tempDate = ConvertUtils.getStrToTimestamp2(o[7].toString());
							//sl.setApprovalTime(tempDate);
						} catch (ParseException e) {
							//date error
							oTemp[oTemp.length-1] = "审批时间转换错误";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//设置审批字段信息
						sa.setApprovalNote(o[9].toString());
						sa.setApprovalOperator(o[6].toString());
						sa.setApprovalStat(stat);
						sa.setApprovalTime(tempDate);
						//更新审批记录
						custSplitRecordAppBS.updateEntity(sa);
						//调用合并接口
						String str = approvalInfo(sa);
						if(!str.equals("")){
							//报文返回结果失败或错误
							oTemp[oTemp.length-1] = str;
							errorTemp.add(oTemp);
							result++;
						}
					}else{
						//三个必填项有空值
						oTemp[oTemp.length-1] = "必填项有空值";
						errorTemp.add(oTemp);
						result++;
					}
				}
				errorData.add(errorTemp);
			}
		}
		//错误数据结果集 errorData
		String file = exportReportBS.export(77, errorData);
		//
		if(result == 0){
			return "成功" + count + "条记录";
		}else{
			//return "成功" + (count-result) + "条记录，错误" + result + "条";
			return "err" + file;
		}
	}
	
	/**
	 * 验证审批人是否与提交人相同
	 * @param custNo
	 * @param ids
	 * @return
	 */
	@RequestMapping("/validateuser")
	@ResponseBody
	public String validationUser(String ids){
		
		int result = 0;
		//获取登入系统用户信息
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		//获得一个或多个主键标识specialListApprovalId
		String[] temp = ids.split(",");
		if(temp.length > 0){
			//循环所有审批信息的主键标识
			for(int i=0; i<temp.length; i++){
				CustSplitRecordApproval sa = 
					(CustSplitRecordApproval) this.custSplitRecordAppBS.getEntityById(Long.valueOf(temp[i]));
				//判断导入操作人与审批人是否相同
				if(user.getUserName().equals(sa.getImportOperator())){
					result ++;
				}
			}
		}
		if(result == 0){
			return "";
		}else{
			return "共有"+result+"条记录的导入提交人与审批人相同";
		}		
	}
	//------------------------------------------------------------------------------------
	
	/**
	 * 生成拆分报文
	 * @param reserveCustNo 保留客户号
	 * @param mergedCustNo 被合并客户号
	 * @return
	 */
	public String getReportStr(String mergedCustNo, PropertiesUtils tool){
		//
		if(tool == null){
			tool = new PropertiesUtils("report.properties");
		}
		String BrchNme = tool.getProperty("BrchNme");
		String TlrNme = tool.getProperty("TlrNme");	
		//
		String str = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+
			"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cqr=\"www.cqrcb.com.cn\">"+
			"<soapenv:Header/><soapenv:Body><cqr:S002001010180022><RequestHeader>"+
			"<VerNo>"+tool.getProperty("VerNo")+"</VerNo>"+
			"<ReqSysCd>"+tool.getProperty("ReqSysCd")+"</ReqSysCd>"+
			"<ReqSecCd>"+tool.getProperty("ReqSecCd")+"</ReqSecCd>"+
			"<TxnTyp>"+tool.getProperty("TxnTyp")+"</TxnTyp>"+
			"<TxnMod>"+tool.getProperty("TxnMod")+"</TxnMod>"+
			"<TxnCd>"+tool.getProperty("TxnCd")+"</TxnCd>"+
			"<TxnNme>"+"解除合并客户号"/*mergedCustNo*/+"</TxnNme>"+
			"<ReqDt>"+ConvertUtils.getDateToString(new Date())+"</ReqDt>"+
			"<ReqTm>"+tool.getProperty("ReqTm")+"</ReqTm>"+
			"<ReqSeqNo>"+tool.getProperty("ReqSeqNo")+"</ReqSeqNo>"+
			"<ChnlNo>"+tool.getProperty("ChnlNo")+"</ChnlNo>"+
			"<BrchNo>"+tool.getProperty("BrchNo")+"</BrchNo>"+
			"<BrchNme>"+BrchNme+"</BrchNme>"+
			"<TrmNo>"+tool.getProperty("TrmNo")+"</TrmNo>"+
			"<TrmIP>"+tool.getProperty("TrmIP")+"</TrmIP>"+
			"<TlrNo>"+tool.getProperty("TlrNo")+"</TlrNo>"+
			"<TlrNme>"+TlrNme+"</TlrNme>"+
			"<TlrLvl>"+tool.getProperty("TlrLvl")+"</TlrLvl>"+
			"<TlrTyp>"+tool.getProperty("TlrTyp")+"</TlrTyp>"+
			"<TlrPwd>"+tool.getProperty("TlrPwd")+"</TlrPwd>"+
			"<AuthTlr>"+tool.getProperty("AuthTlr")+"</AuthTlr>"+
			"<AuthPwd>"+tool.getProperty("AuthPwd")+"</AuthPwd>"+
			"<AuthCd>"+tool.getProperty("AuthCd")+"</AuthCd>"+
			"<AuthFlg>"+tool.getProperty("AuthFlg")+"</AuthFlg>"+
			"<AuthDisc>"+tool.getProperty("AuthDisc")+"</AuthDisc>"+
			"<AuthWk>"+tool.getProperty("AuthWk")+"</AuthWk>"+
			"<SndFileNme>"+tool.getProperty("SndFileNme")+"</SndFileNme>"+
			"<BgnRec>"+tool.getProperty("BgnRec")+"</BgnRec>"+
			"<MaxRec>"+tool.getProperty("MaxRec")+"</MaxRec>"+
			"<FileHMac>"+tool.getProperty("FileHMac")+"</FileHMac>"+
			"<HMac>"+tool.getProperty("HMac")+"</HMac>"+
			"<TermSeqNo>"+tool.getProperty("TermSeqNo")+"</TermSeqNo>"+
			"<AmtFlg>"+tool.getProperty("AmtFlg")+"</AmtFlg>"+
			"<TrmDt>"+tool.getProperty("TrmDt")+"</TrmDt>"+
			"<TrmTm>"+tool.getProperty("TrmTm")+"</TrmTm>"+
			"<FrntNo>"+tool.getProperty("FrntNo")+"</FrntNo>"+
			"</RequestHeader><RequestBody>"+
			"<txcode>"+tool.getProperty("txCodeSplit")+"</txcode>"+
			"<AnExchNo>"+tool.getProperty("AnExchNoSplit")+"</AnExchNo>"+
			"<CustomId1>"+mergedCustNo+"</CustomId1>"+
			"</RequestBody></cqr:S002001010180022></soapenv:Body></soapenv:Envelope>";
		return str;
	}
	
	/*
	<Fault>
        <FaultCode>002001000000</FaultCode>
        <FaultString>成功</FaultString>
        <Detail>
           <TxnStat>SUCCESS</TxnStat>
        </Detail>
     </Fault>
	 */
}
