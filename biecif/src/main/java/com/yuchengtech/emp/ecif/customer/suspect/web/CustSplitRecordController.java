/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.customerinfo.service.CustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Customer;
import com.yuchengtech.emp.ecif.customer.entity.other.CustSplitRecord;
import com.yuchengtech.emp.ecif.customer.entity.other.CustSplitRecordApproval;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.CustSplitRecordAppBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.CustSplitRecordBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.SuspectGroupBS;

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
@RequestMapping("/ecif/customer/custsplitrecord")
public class CustSplitRecordController extends BaseController {
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private CustSplitRecordBS custSplitRecordBS;
	
	@Autowired
	private CustSplitRecordAppBS custSplitRecordAppBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ImportListBS importListBS;
	
	@Autowired
	private SuspectGroupBS suspectGroupBS;
	
	@Autowired
	private CustomerInfoBS customerInfoBS;
	
	//一次进入不查询
	private boolean flag = false;	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/suspect/custsplitrecord-index";
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
		SearchResult<CustSplitRecord> searchResult = custSplitRecordBS.getCustSplitRecordList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		userMap.put("Rows", searchResult.getResult());
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
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
		return new ModelAndView("/ecif/customer/suspect/impcustsplitrecord-upload");
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
		Map<String, String> codeMapComfirmType = Maps.newHashMap();//确认标识
		codeMapComfirmType.put("未确认", GlobalConstants.CUST_COMFIRM_0);
		codeMapComfirmType.put("确认", GlobalConstants.CUST_COMFIRM_1);
		//
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		PropertiesUtils tool = new PropertiesUtils("report.properties");
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
				CustSplitRecordApproval sl = null;
				Object[] oTemp = null;
				for(Object[] o : temp){
					
					oTemp = Arrays.copyOf(o, o.length+1);
					//保留客户号  => (被合并客户号) 合并提交人 合并提交时间
					if(o[0] != null && !o[0].toString().equals("") &&
							o[1] != null && !o[1].toString().equals("") &&
							o[2] != null && !o[2].toString().equals("")
							//&& o[3] != null && !o[3].toString().equals("")
							){
						//String sql="select d  from Crossindex c,Customer d 
						//where c.srcSysNo='01' and c.srcSysCustNo=?2 and c.custId=d.custId ";
						//getCustId()通过交叉索引表查客户标识
						Customer customer1 = customerInfoBS.getCustIdByCustNo(o[0].toString());
						if(customer1 == null){
							//被合并客户号存在，没在历史表中
//							oTemp[oTemp.length-1] = "被合并客户不存在";
//							errorTemp.add(oTemp);
//							result++;
//							continue;
						}
				        /*
				                     通过合并客户号找保留客户号
				        SELECT a  FROM CustMergeRecordecif a ");
						WHERE 1 = 1 AND  a.mergedCustId =?1 AND  a.mergeStat ='1' order by mergeOperTime desc 
				         */
						sl = custSplitRecordAppBS.getCustMergeRecordAppByCustId(o[0].toString());
						if(sl == null){
							sl = new CustSplitRecordApproval();
							sl.setImportOperator(user.getUserName());
							sl.setImportOperTime(new Timestamp(new Date().getTime()));
							sl.setReserveCustNo(null);
							sl.setMergedCustNo(o[0].toString());
							sl.setSplitOperator(o[1].toString());
							try {
								sl.setSplitOperTimeDate(ConvertUtils.getStrToTimestamp2(o[2].toString()));
							} catch (ParseException e) {
								//日期转换错误
								oTemp[oTemp.length-1] = "日期转换错误";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
							sl.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
							custSplitRecordAppBS.updateEntity(sl);
						}else{
							//该记录已存在
							oTemp[oTemp.length-1] = "该记录已存在";
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
		String file = exportReportBS.export(55, errorData);
		//
		if(result == 0){
			return "成功" + count + "条记录";
		}else{
			//return "成功" + (count-result) + "条记录，错误" + result + "条";
			return "err" + file;
		}
	}
//	public String getResultByData(List<List<Object[]>> reportData){
//		//
//		List<List<Object[]>> errorData = new ArrayList<List<Object[]>>();
//		//
//		Map<String, String> codeMapComfirmType = Maps.newHashMap();//确认标识
//		codeMapComfirmType.put("未确认", GlobalConstants.CUST_COMFIRM_0);
//		codeMapComfirmType.put("确认", GlobalConstants.CUST_COMFIRM_1);
//		//
//		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
//		PropertiesUtils tool = new PropertiesUtils("report.properties");
//		int result = 0;
//		int count = 0;
//		for(int i=0; i < reportData.size(); i++){
//			List<Object[]> temp = reportData.get(i);
//			List<Object[]> errorTemp = new ArrayList<Object[]>();
//			if(temp == null){
//				continue;
//			}
//			if(i == 0){//读取第一个sheet
//				count = temp.size();
//				//List<SpecialList> list = new ArrayList<SpecialList>();
//				CustSplitRecord sl = null;
//				Object[] oTemp = null;
//				for(Object[] o : temp){
//					
//					oTemp = Arrays.copyOf(o, o.length+1);
//					//保留客户号  => (被合并客户号) 合并提交人 合并提交时间
//					if(o[0] != null && !o[0].toString().equals("") &&
//							o[1] != null && !o[1].toString().equals("") &&
//							o[2] != null && !o[2].toString().equals("")
//							//&& o[3] != null && !o[3].toString().equals("")
//							){
//						
//						Customer customer1 = customerInfoBS.getCustIdByCustNo(o[0].toString());
//						if(customer1 == null){
//							//被合并客户号不存在
//							oTemp[oTemp.length-1] = "被合并客户不存在";
//							errorTemp.add(oTemp);
//							result++;
//							continue;
//						}
//						String reportCode = "";
//						String reportStr = "";
//						NIOClient client = new NIOClient(tool.getProperty("host"), Integer.valueOf(tool.getProperty("port")));
//						String tmp;
//						try {
//							String report = getReportStr(o[0].toString());
//							//report = new String(report.getBytes(),"ISO8859-1");
//							tmp = client.interactive(report, "/ECIF");
//							//tmp = new String(tmp.getBytes("ISO8859-1"),"GBK");
//						} catch (IOException e1) {
//							tmp = "<FaultCode>error</FaultCode><FaultString>接口应用错误</FaultString><TxnStat>ERROR</TxnStat>";
//						}
//						//(?s)<FaultCode>(.*?)</FaultCode>.*?<FaultString>(.*?)</FaultString>
//						String regex = "(?s)<FaultString>(.*?)</FaultString>.*?<TxnStat>(.*?)</TxnStat>";
//				        Pattern pattern = Pattern.compile(regex);
//				        Matcher matcher = pattern.matcher(tmp);
//				        while(matcher.find()) {
//				        	reportCode = matcher.group(1).trim();
//				        	reportStr = matcher.group(2).trim();
//				        }
//				        if(reportStr.equals("SUCCESS")){
//							sl = custSplitRecordBS.getCustMergeRecordByCustId(o[0].toString());
//							sl.setImportOperator(user.getUserName());
//							sl.setImportOperTime(new Date());
////							sl.setReserveCustId(customer1.getCustId());
////							sl.setMergedCustId(null);
////							sl.setSplitOperator(o[2].toString());
////							try {
////								sl.setSplitOperTimeDate(ConvertUtils.getStrToTimestamp2(o[3].toString()));
////							} catch (ParseException e) {
////								//日期转换错误
////								result++;
////								continue;
////							}
////							sl.setSplitStat(GlobalConstants.CUST_SPLIT_TYPE);
////							//根据结果改变疑似客户的合并状态
////							//成功拆分
////							SuspectList t1 = suspectGroupBS.getSuspectListGroupByCustId(o[0].toString());
////							SuspectList t2 = suspectGroupBS.getSuspectListGroupByCustId(o[1].toString());
////							if(t1 != null && t2 != null){//
////								if(t1.getSuspectGroupId() == t2.getSuspectGroupId()){//疑似
////									suspectGroupBS.updateMergeInfo(t1.getSuspectListId(), 
////											GlobalConstants.CUST_SPLIT_TYPE, new Date().toString());
////									suspectGroupBS.updateMergeInfo(t2.getSuspectListId(), 
////											GlobalConstants.CUST_SPLIT_TYPE, new Date().toString());
////								}
////							}
//							custSplitRecordBS.updateEntity(sl);
//						}else{
//							//报文返回结果失败或错误
//							oTemp[oTemp.length-1] = reportCode;
//							errorTemp.add(oTemp);
//							result++;
//						}
//					}else{
//						//三个必填项有空值
//						oTemp[oTemp.length-1] = "必填项有空值";
//						errorTemp.add(oTemp);
//						result++;
//					}
//				}
//				errorData.add(errorTemp);
//			}
//		}
//		//错误数据结果集 errorData
//		String file = exportReportBS.export(55, errorData);
//		//
//		if(result == 0){
//			return "成功" + count + "条记录";
//		}else{
//			//return "成功" + (count-result) + "条记录，错误" + result + "条";
//			return "err" + file;
//		}
//	}
//	/**
//	 * 生成拆分报文
//	 * @param reserveCustNo 保留客户号
//	 * @param mergedCustNo 被合并客户号
//	 * @return
//	 */
//	public String getReportStr(String mergedCustNo){
//		//
//		PropertiesUtils tool = new PropertiesUtils("report.properties");
//		String BrchNme = tool.getProperty("BrchNme");
//		String TlrNme = tool.getProperty("TlrNme");	
//		//
//		String str = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+
//			"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cqr=\"www.cqrcb.com.cn\">"+
//			"<soapenv:Header/><soapenv:Body><cqr:S002001010180022><RequestHeader>"+
//			"<VerNo>"+tool.getProperty("VerNo")+"</VerNo>"+
//			"<ReqSysCd>"+tool.getProperty("ReqSysCd")+"</ReqSysCd>"+
//			"<ReqSecCd>"+tool.getProperty("ReqSecCd")+"</ReqSecCd>"+
//			"<TxnTyp>"+tool.getProperty("TxnTyp")+"</TxnTyp>"+
//			"<TxnMod>"+tool.getProperty("TxnMod")+"</TxnMod>"+
//			"<TxnCd>"+tool.getProperty("TxnCd")+"</TxnCd>"+
//			"<TxnNme>"+"解除合并客户号"/*mergedCustNo*/+"</TxnNme>"+
//			"<ReqDt>"+ConvertUtils.getDateToString(new Date())+"</ReqDt>"+
//			"<ReqTm>"+tool.getProperty("ReqTm")+"</ReqTm>"+
//			"<ReqSeqNo>"+tool.getProperty("ReqSeqNo")+"</ReqSeqNo>"+
//			"<ChnlNo>"+tool.getProperty("ChnlNo")+"</ChnlNo>"+
//			"<BrchNo>"+tool.getProperty("BrchNo")+"</BrchNo>"+
//			"<BrchNme>"+BrchNme+"</BrchNme>"+
//			"<TrmNo>"+tool.getProperty("TrmNo")+"</TrmNo>"+
//			"<TrmIP>"+tool.getProperty("TrmIP")+"</TrmIP>"+
//			"<TlrNo>"+tool.getProperty("TlrNo")+"</TlrNo>"+
//			"<TlrNme>"+TlrNme+"</TlrNme>"+
//			"<TlrLvl>"+tool.getProperty("TlrLvl")+"</TlrLvl>"+
//			"<TlrTyp>"+tool.getProperty("TlrTyp")+"</TlrTyp>"+
//			"<TlrPwd>"+tool.getProperty("TlrPwd")+"</TlrPwd>"+
//			"<AuthTlr>"+tool.getProperty("AuthTlr")+"</AuthTlr>"+
//			"<AuthPwd>"+tool.getProperty("AuthPwd")+"</AuthPwd>"+
//			"<AuthCd>"+tool.getProperty("AuthCd")+"</AuthCd>"+
//			"<AuthFlg>"+tool.getProperty("AuthFlg")+"</AuthFlg>"+
//			"<AuthDisc>"+tool.getProperty("AuthDisc")+"</AuthDisc>"+
//			"<AuthWk>"+tool.getProperty("AuthWk")+"</AuthWk>"+
//			"<SndFileNme>"+tool.getProperty("SndFileNme")+"</SndFileNme>"+
//			"<BgnRec>"+tool.getProperty("BgnRec")+"</BgnRec>"+
//			"<MaxRec>"+tool.getProperty("MaxRec")+"</MaxRec>"+
//			"<FileHMac>"+tool.getProperty("FileHMac")+"</FileHMac>"+
//			"<HMac>"+tool.getProperty("HMac")+"</HMac>"+
//			"<TermSeqNo>"+tool.getProperty("TermSeqNo")+"</TermSeqNo>"+
//			"<AmtFlg>"+tool.getProperty("AmtFlg")+"</AmtFlg>"+
//			"<TrmDt>"+tool.getProperty("TrmDt")+"</TrmDt>"+
//			"<TrmTm>"+tool.getProperty("TrmTm")+"</TrmTm>"+
//			"<FrntNo>"+tool.getProperty("FrntNo")+"</FrntNo>"+
//			"</RequestHeader><RequestBody>"+
//			"<txcode>"+tool.getProperty("txCodeSplit")+"</txcode>"+
//			"<AnExchNo>"+tool.getProperty("AnExchNoSplit")+"</AnExchNo>"+
//			"<CustomId1>"+mergedCustNo+"</CustomId1>"+
//			"</RequestBody></cqr:S002001010180022></soapenv:Body></soapenv:Envelope>";
//		return str;
//	}	
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
