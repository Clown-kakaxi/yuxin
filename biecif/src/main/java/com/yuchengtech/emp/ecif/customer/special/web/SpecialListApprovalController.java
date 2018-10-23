/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.special.web;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.HSpecialList;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.SpecialList;
import com.yuchengtech.emp.ecif.customer.entity.other.SpecialListApproval;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.special.service.HSpecialListBS;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListApprovalBS;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListAABS;
import com.yuchengtech.emp.ecif.customer.special.web.vo.SpecialListApprovalVO;

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
@RequestMapping("/ecif/customer/speciallistapproval")
public class SpecialListApprovalController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(SpecialListApprovalController.class);

	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private SpecialListAABS specialListBS;
	
	@Autowired
	private HSpecialListBS hspecialListBS;
	
	@Autowired
	private SpecialListApprovalBS specialListApprovalBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ImportListBS importListBS;
	
	//一次进入不查询
	private boolean flag = false;
	
//	private Map<String, String> codeMapApprovalStatus = Maps.newHashMap();//审批状态
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/special/speciallistapproval-index";
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
		SearchResult<SpecialListApprovalVO> searchResult = specialListApprovalBS.getSpecialListApprovalList(
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
		return new ModelAndView("/ecif/customer/special/speciallistapproval-edit", "id", id);
	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/customer/special/speciallistapproval-edit";
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
	 * 获取code combobox
	 */
	@ResponseBody
	@RequestMapping("getCodeComBoBox.*")
	public String getCodeComBoBox(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = "";
		Map<String, String> temp = this.codeUtil.getCodeMap(flag);
		if(temp == null || temp.size() == 0){
			return paramValue;
		}
		
		String result = "";
		if(temp.get(paramValue)!= null && !temp.get(paramValue).equals("")){
			result = temp.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SpecialListApproval show(@PathVariable("id") String id) {
		SpecialListApproval specialListApproval = (SpecialListApproval) this.specialListApprovalBS.getEntityById(id);
		return specialListApproval;
	}
	
	/**
	 * 提交审批信息
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(SpecialListApproval specialListApproval, String ids) {
		//获取登入系统用户信息
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		//String 
		//获得一个或多个主键标识specialListApprovalId
		String[] temp = ids.split(",");
		if(temp.length > 0){
			//循环所有审批信息的主键标识
			for(int i=0; i<temp.length; i++){
				SpecialListApproval sa = 
					(SpecialListApproval) this.specialListApprovalBS
						.getEntityById(Long.valueOf(temp[i]));
				//判断提交人与审批人是否相同
				if(!user.getUserName().equals(sa.getOperator())){
					//设置审批字段信息
					sa.setApprovalNote(specialListApproval.getApprovalNote());
					sa.setApprovalOperator(user.getUserName());
					sa.setApprovalStat(specialListApproval.getApprovalStat());
					sa.setApprovalTime(new Timestamp(new Date().getTime()));
					
					specialListApprovalBS.updateEntity(sa);
					approvalInfo(sa);
				}
			}
		}
	}
	
	/**
	 * 拷贝属性信息
	 */
	public SpecialList copyInfo(SpecialListApproval specialListApproval){
		
		SpecialList sa = new SpecialList();
		//三证信息
		sa.setIdentCustName(specialListApproval.getIdentCustName());
		sa.setIdentNo(specialListApproval.getIdentNo());
		sa.setIdentType(specialListApproval.getIdentType());
		//base info
		sa.setCustId(specialListApproval.getCustId());//客户编号
		sa.setStartDate(specialListApproval.getStartDate());
		sa.setEndDate(specialListApproval.getEndDate());
		sa.setSpecialListFlag("");
		sa.setSpecialListId(specialListApproval.getSpecialListId()==null?
				null:specialListApproval.getSpecialListId());
		sa.setSpecialListType(GlobalConstants.SPECIALLIST_TYPE);
		sa.setEnterReason(specialListApproval.getEnterReason());
		sa.setStatFlag(specialListApproval.getStatFlag());
		//
		sa.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);//无审批或审批过后
		sa.setTxSeqNo("");
		sa.setLastUpdateSys("ecif");
		sa.setLastUpdateTm(new Timestamp(new Date().getTime()));
		sa.setLastUpdateUser(specialListApproval.getOperator());
		sa.setSpecialListKind(specialListApproval.getSpecialListKind());
		return sa;
	}
	
	/**
	 * 审批方法
	 */
	public void approvalInfo(SpecialListApproval sa){
		SpecialList s = null;
		
		if(sa.getApprovalStat().equals(GlobalConstants.APPROVAL_STAT_2)){//审批通过
			//删除就直接删除，插入和更新直接存入数据表
			if(sa.getOperStat().equals(GlobalConstants.OPER_STAT_DELETE)){
				s = specialListBS.getEntityById(sa.getSpecialListId());
				specialListBS.removeEntityById(sa.getSpecialListId());
			}else if(sa.getOperStat().equals(GlobalConstants.OPER_STAT_INSERT) ||
					sa.getOperStat().equals(GlobalConstants.OPER_STAT_UPDATE)){
				s = copyInfo(sa);
				s = specialListBS.updateEntity(s);				
			}
			HSpecialList h = new HSpecialList();
			BeanUtils.copy(s, h);
			h.setHisOperSys("ecif");
			h.setHisOperType("00");
			h.setHisOperTime(new Timestamp(new Date().getTime()));
			hspecialListBS.saveOrUpdateEntity(h);
			s = null;
		}else if(sa.getApprovalStat().equals(GlobalConstants.APPROVAL_STAT_3)){//审批不通过
			//删除或更新的黑名单信息改变审批标识为“无审批或审批过后”
			if(sa.getOperStat().equals(GlobalConstants.OPER_STAT_DELETE) ||
					sa.getOperStat().equals(GlobalConstants.OPER_STAT_UPDATE)){
				s = copyInfo(sa);
				specialListBS.updateApprovalFlag(
					s.getSpecialListId(), GlobalConstants.APPROVAL_FLAG_0);
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "export.*", method = RequestMethod.POST)
	public void export(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	/**
	 * 下载文件
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getReportFile")
	@ResponseBody
	public String getReportFile(String repo, String custNo, String identNo, 
			String lastUpdateSys, String srptMonth, String erptMonth) {
		int report = Integer.parseInt(repo);
		return exportReportBS.export(report, custNo, identNo, lastUpdateSys, "", srptMonth, erptMonth);
	}
	
	/**
	 * 跳转到上传页面
	 * @return
	 */
	@RequestMapping(value = "/importresult", method = RequestMethod.GET)
	public ModelAndView importResult() {
		return new ModelAndView("/ecif/customer/special/speciallistapproval-upload");
	}
	
	/**
	 * 开始上传并且读取文件内容
	 * @param uploader
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/startUpload")
	@ResponseBody
	public String startUpload(Uploader uploader, HttpServletResponse response) throws Exception {
		
		//BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		//		
		File file = null;
		try {
			file = this.uploadFile(uploader, GlobalConstants.EXCEL_IMPORT_FOLDER, false);
		} catch (Exception e) {
			logger.info("文件上传出现异常", e);
		}
		if (file != null) {
			logger.info("文件[" + file.getName() + "]上传完成");
			List<List<Object[]>> reportData = importListBS.importSpecialListApproval(file.getPath(), 3);
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
				Object[] oTemp = null;
				for(Object[] o : temp){
					if(o[11] != null){
						o[11] = o[11].toString() + " ";
					}
					if(o[14] != null){
						o[14] = o[14].toString() + " ";
					}
					oTemp = Arrays.copyOf(o, o.length+1);
					//信息标识	证件户名	证件类型	证件号码	客户编号	黑名单分类
					//列入原因	状态标志	起始日期	结束日期	提交人
					//提交时间	操作状态
					//审批人	审批时间	审批状态	审批意见
					if(o[0] != null && !o[0].toString().equals("") &&
							o[13] != null && !o[13].toString().equals("") &&
							o[14] != null && !o[14].toString().equals("") &&
							o[15] != null && !o[15].toString().equals("")){
						Timestamp tempDate = null;
						try {
							tempDate = ConvertUtils.getStrToTimestamp2(o[14].toString().trim());
							//sl.setApprovalTime(tempDate);
						} catch (ParseException e) {
							//date error
							oTemp[oTemp.length-1] = "审批时间类型转换错误";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						SpecialListApproval sa = 
								(SpecialListApproval) this.specialListApprovalBS
									.getEntityById(Long.valueOf(o[0].toString()));
						//判断信息是否存在
						if(sa == null){
							oTemp[oTemp.length-1] = "信息不存在";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						try {
			    			if(ConvertUtils.getStrToTimestamp2(o[14].toString().trim()).getTime()
								< sa.getOperTime().getTime()){
								oTemp[oTemp.length-1] = "审批时间早于提交时间";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
		    			} catch (ParseException e) {
		    				oTemp[oTemp.length-1] = "审批时间与提交时间对比错误";
		    				errorTemp.add(oTemp);
							result++;
							continue;
		    			}
						//审批状态是否存在
						String stat = codeMapApprovalStatus1.get(o[15].toString());
						if(StringUtils.isEmpty(stat)){
							oTemp[oTemp.length-1] = "审批状态与码值不符";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//审批状态必须是待审批状态
						if(!sa.getApprovalStat().equals(GlobalConstants.APPROVAL_STAT_1)){
							oTemp[oTemp.length-1] = "记录的审批状态不是待审批";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//提交人与审批人是否相同
						if(o[13].toString().equals(sa.getOperator())){
							oTemp[oTemp.length-1] = "提交人与审批人不可相同";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//设置审批字段信息
						sa.setApprovalNote(o[16]==null?"":o[16].toString());
						sa.setApprovalOperator(o[13].toString());
						sa.setApprovalStat(codeMapApprovalStatus1.get(o[15].toString()));
						sa.setApprovalTime(tempDate);						
						specialListApprovalBS.updateEntity(sa);
						approvalInfo(sa);
					}else{
						//必填项有空值
						oTemp[oTemp.length-1] = "必填项有空值";
						errorTemp.add(oTemp);
						result++;
					}
				}
				errorData.add(errorTemp);
			}
		}
		//错误数据结果集 errorData
		String file = exportReportBS.export(2, errorData);
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
				SpecialListApproval sa = 
					(SpecialListApproval) this.specialListApprovalBS.getEntityById(Long.valueOf(temp[i]));
				//判断提交人与审批人是否相同
				if(user.getUserName().equals(sa.getOperator())){
					result ++;
				}
			}
		}
		if(result == 0){
			return "";
		}else{
			return "共有"+result+"条记录的提交人与审批人相同";
		}		
	}
	
}
