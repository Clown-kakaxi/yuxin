package com.yuchengtech.emp.ecif.customer.cusrelo.web;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.yuchengtech.emp.ecif.base.util.CardUtil;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustomerRelationBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustrelApprovalBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.web.vo.CusRelationLookVO;
import com.yuchengtech.emp.ecif.customer.customerinfo.service.CustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Customer;
import com.yuchengtech.emp.ecif.customer.entity.customer.Custrel;
import com.yuchengtech.emp.ecif.customer.entity.customer.CustrelApproval;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgidentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.NameTitle;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.CustomerBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.NameTitleBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.OrgidentinfoBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PersonIdentifierBS;

/**
 * <pre>
 * Title:客户关系查看
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author wuhp wuhp@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/customerrelationlook")
public class CustomerRelationController extends BaseController {
	
	@Autowired
	private CustomerInfoBS customerInfoBS;
	
	@Autowired
	private ExportReportBS exportReportBS;

	@Autowired
	private CustomerRelationBS customerRelationLookBS;
	
	@Autowired
	private CustrelApprovalBS custrelApprovalBS;
	
	@Autowired
	private ImportListBS importListBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
//	@Autowired
//	private ConvertUtils convertUtils;
	
	@Autowired
	private OrgidentinfoBS orgidentinfoBS;
	
	@Autowired
	private PersonIdentifierBS personIdentifierBS;
	
	@Autowired
	private NameTitleBS nameTitleBS;
	
	@Autowired
	private CustomerBS customerBS;
	
	private Map<String, String> codeMapRelType = Maps.newHashMap();//关系类型
	private Map<String, String> codeMapPerIdentType = Maps.newHashMap();//个人证件类型
	private Map<String, String> codeMapOrgIdentType = Maps.newHashMap();//机构证件类型
	
	//一次进入不查询
	private boolean flag = false;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/customerrel/customerrelationlook-index";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		Map<String, Object> taskMap = Maps.newHashMap();		
		if(flag == false){
			flag = true;
			return null;
		}
		SearchResult<CusRelationLookVO> searchResult = customerRelationLookBS.getTaskInstanceList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), GlobalConstants.APPROVAL_FLAG_1);
		taskMap.put("Rows", searchResult.getResult());
		taskMap.put("Total", searchResult.getTotalCount());
		return taskMap;
	}
	
	/**
	 * 获取关系类型的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapRelType.*")
	public String getCodeMapValidType(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = GlobalConstants.CODE_STR_CUSTREL_TYPE;
		//
		if(codeMapRelType == null || codeMapRelType.isEmpty()){
			codeMapRelType = Maps.newHashMap();
			codeMapRelType = this.codeUtil.getCodeMap(flag);
		}
		String result = "";
		if(codeMapRelType.get(paramValue)!= null && !codeMapRelType.get(paramValue).equals("")){
			result = codeMapRelType.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	
	/**
	 * 根据选择的关系类型，返回关系种类下拉列表
	 */
	@RequestMapping("getModeVer.*")
	@ResponseBody
	public List<Map<String,String>> getModeVer(@RequestParam("adapterId") String adapterId) {
		
		return this.customerRelationLookBS.getModeVer(adapterId);
	}
	
	/**
	 * 获取关系状态下拉列表
	 */
	@ResponseBody
	@RequestMapping("getRenderRelStatBox.*")
	public List<Map<String,String>> getComBoBox() {
		return this.customerRelationLookBS.getRenderRelStat();
	}
	
	/**
	 * 获取客户关系状态
	 */
	@ResponseBody
	@RequestMapping("getRenderRelStat.*")
	public String getRenderRelStat(String paramTypeNo, String paramValue) {
		
		List<Map<String,String>> temp = this.customerRelationLookBS.getRenderRelStat();
		
		String result = "";
		
		if(temp == null || temp.size() == 0){
			return paramValue;
		}
		for (Map<String, String> map : temp) {
			if(map.get("id").equals(paramValue)){
				result = map.get("text");
			}
			if(result != null && !result.equals("")){
				break;
			}
		}

		if (result == null || result.equals("")) {
			return paramValue;
		} else {
			return result;
		}
		
	}
	
	/**
	 * 获取关系类型
	 */
	@ResponseBody
	@RequestMapping("getCodeComBoBox.*")
	public String getCodeComBoBox(String paramTypeNo, String paramValue) {
		
		long paramValueTemp = 0;
		if(!"".equals(paramValue)){
			paramValueTemp = Long.parseLong(paramValue);
		}
		String adapterId = "";
		if(1001001000L < paramValueTemp && paramValueTemp < 2001001000L){
			adapterId = "1";
		}else if(2001001000L < paramValueTemp && paramValueTemp < 3000000000L){
			adapterId = "2";
		}else if(3000000000L < paramValueTemp && paramValueTemp < 5000000000L){
			adapterId = "3";
		}
		List<Map<String,String>> temp = this.customerRelationLookBS.getModeVer(adapterId);
		
		String result = "";
		if(temp == null || temp.size() == 0){
			return paramValue;
		}
		for (Map<String, String> map : temp) {
			if(map.get("id").equals(paramValue)){
				result = map.get("text");
			}
			if(result != null && !result.equals("")){
				break;
			}
		}
		if (result == null || result.equals("")) {
			return paramValue;
		} else {
			return result;
		}
	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
//		return "//ecif/customerrelationlook/customerrelationlook-edit";
		
		return "/ecif/customer/customerrel/customerrelationlook-edit";
	}
	
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	// POST /module/
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(CusRelationLookVO custrelVo) {
		
		CustrelApproval custrelApproval = copyInfo(custrelVo,null);
		
		Customer customerSrc = customerInfoBS.getCustIdByCustNo(custrelVo.getSrcCustNo());
		Customer customerDest = customerInfoBS.getCustIdByCustNo(custrelVo.getDestCustNo());
		
		//新增的时候判断在关系表中和审批表中是否有该条记录
		if(!GlobalConstants.OPER_STAT_UPDATE.equals(custrelApproval.getOperStat()) && !GlobalConstants.OPER_STAT_DELETE.equals(custrelApproval.getOperStat())){
			if("true".equals(isNoTypeRight(customerSrc.getCustNo(),customerDest.getCustNo(),custrelVo.getCustRelType()))){
				custrelApprovalBS.updateEntity(custrelApproval);
			}
		}else{
			custrelApprovalBS.updateEntity(custrelApproval);
		}
	}
	
	/**
	 * 通过custNo找到是否有该客户
	 */
	@RequestMapping("getCustomer.*")
	@ResponseBody
	public String getCustomer(@RequestParam("custNo") String custNo) {
		
		String flag = "false";
		Customer customer = customerInfoBS.getCustIdByCustNo(custNo);
		if(customer == null){
			flag = "true";
		}
		
		return flag;
	}
	
	/**
	 * 通过custNo返回custId
	 */
	@RequestMapping("getCustomerId.*")
	@ResponseBody
	public String getCustomerId(@RequestParam("custNo") String custNo) {
		
		String custId = "";
		Customer customer = customerInfoBS.getCustIdByCustNo(custNo);

		custId = customer.getCustId().toString()+","+customer.getCustType();
		
		return custId;
	}
	
	
	/**
	 * 判断选择的类型是否正确
	 * 类型正确返回true
	 */
	@RequestMapping("isAllType.*")
	@ResponseBody
	public String isAllType(@RequestParam("relType") String relType,@RequestParam("srcCustNo") String srcCustNo,@RequestParam("destCustNo") String destCustNo) {
		
		String[] custRelType = GlobalConstants.OTHER_CUSTREL;
		String flag = "true";
		if(StringUtils.isEmpty(relType) || StringUtils.isEmpty(srcCustNo) || StringUtils.isEmpty(destCustNo)){
			return flag;
		}
		
		Map<String,String> relMap = codeUtil.getCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		
		//类型是否允许操作,不允许返回false
		boolean isNotAllow = true;
		for(String str : custRelType){
			if(str.equals(relMap.get(relType))){
				isNotAllow = false;
			}
		}
		
		//关系对应是否正确,正确true
		boolean isCustrelRight = false;
		long intCustrelType = Long.parseLong(relType);
		
		Customer srcCustomer = customerInfoBS.getCustIdByCustNo(srcCustNo);
		Customer destCustomer = customerInfoBS.getCustIdByCustNo(destCustNo);
		
		String srcStr = "";
		String destStr = "";
		
		if(GlobalConstants.CUST_ORG_TYPE.equals(srcCustomer.getCustType())){
			srcStr = "org";
		}
		
		if(GlobalConstants.CUST_PERSON_TYPE.equals(srcCustomer.getCustType())){
			srcStr = "per";
		}
		
		if(GlobalConstants.CUST_ORG_TYPE.equals(destCustomer.getCustType())){
			destStr = "org";
		}
		
		if(GlobalConstants.CUST_PERSON_TYPE.equals(destCustomer.getCustType())){
			destStr = "per";
		}
		
		if("org".equals(srcStr) && "org".equals(destStr) && intCustrelType < 2001001000L && intCustrelType > 1001001000L){
			isCustrelRight = true;
		}else if("org".equals(srcStr) && "per".equals(destStr) && intCustrelType < 3000000000L && intCustrelType > 2001001000L){
			isCustrelRight = true;
		}else if("per".equals(srcStr) && "per".equals(destStr) && intCustrelType < 5000000000L && intCustrelType > 3000000000L){
			isCustrelRight = true;
		}
		
		if(isNotAllow == false || isCustrelRight == false ){
			flag = "false";
		}
		
		return flag;
	}
	
	/**
	 * 判断新增的用户是否已经在审批表中或者已经在关系表中
	 */
	@RequestMapping("isNoTypeRight.*")
	@ResponseBody
	public String isNoTypeRight(@RequestParam("srcCustNo") String srcCustNo,@RequestParam("destCustNo") String destCustNo,@RequestParam("custrelType") String custrelType) {
		
		String flag = "true";
		
		if(!"".equals(srcCustNo)&&!"".equals(destCustNo)&&!"".equals(custrelType)){
			Customer srcCustomer = customerInfoBS.getCustIdByCustNo(srcCustNo);
			Customer destCustomer = customerInfoBS.getCustIdByCustNo(destCustNo);
			
			//存在返回true
			boolean custrelFlag = customerRelationLookBS.isExistCustRel(srcCustomer.getCustId(), destCustomer.getCustId(), custrelType);
			
			//存在返回true
			boolean custrelAprovalFlag = custrelApprovalBS.isExistCustRelApproval(srcCustomer.getCustId(), destCustomer.getCustId(), custrelType);
			
			if(custrelFlag == true || custrelAprovalFlag == true){
				flag = "false";
			}
		}
		
		return flag;
	}
	
	public CustrelApproval copyInfo(CusRelationLookVO custrelVo,String flag){
		
		Customer srcCustomer = customerInfoBS.getCustIdByCustNo(custrelVo.getSrcCustNo());
		Customer destCustomer = customerInfoBS.getCustIdByCustNo(custrelVo.getDestCustNo());
		//
		Custrel custrel = new Custrel();
		if(!"".equals(custrelVo.getCustRelId()) && custrelVo.getCustRelId() != null ){
			custrel.setCustRelId(Long.parseLong(custrelVo.getCustRelId()));
		}
		if(srcCustomer != null){
			custrel.setSrcCustId(srcCustomer.getCustId());
		}
		if(destCustomer != null){
			custrel.setDestCustId(destCustomer.getCustId());
		}
		custrel.setRelStartDate(custrelVo.getCustRelStart());
		custrel.setRelEndDate(custrelVo.getCustRelEnd());
		custrel.setCustRelType(custrelVo.getCustRelType());
		custrel.setCustRelStat("1");
		custrel.setCustRelDesc(custrelVo.getCustRelDesc());
		//设置审批状态为未审批
		custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_1);
		//设置更新人
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		custrel.setLastUpdateUser(user.getUserName());
		//设置最后更新时间
		custrel.setLastUpdateTm(new Timestamp(new Date().getTime()));
		
		CustrelApproval custrelApproval = new CustrelApproval();
		//insert or update
		if(flag == null || flag.equals("")){
//			custrelApproval.setOperStat(custrel.getCustRelId()==null ? "01":"02");
			custrelApproval.setOperStat(custrel.getCustRelId()==null ? GlobalConstants.OPER_STAT_INSERT:GlobalConstants.OPER_STAT_UPDATE);
			//update
//			if("02".equals(custrelApproval.getOperStat())){
			if(GlobalConstants.OPER_STAT_UPDATE.equals(custrelApproval.getOperStat())){
				//得到原始对象
				Custrel custrelOrig = this.customerRelationLookBS.getEntityById(custrel.getCustRelId());
				//修改原始对象的状态
				custrelOrig.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_1);
				custrel.setSrcCustId(custrelOrig.getSrcCustId());
				custrel.setDestCustId(custrelOrig.getDestCustId());
				//this.customerRelationLookBS.updateEntity(custrel);
				this.customerRelationLookBS.updateApprovalFlag(custrel.getCustRelId(), GlobalConstants.APPROVAL_FLAG_1);
			}
		}else{
			custrelApproval.setOperStat(flag);//delete
			this.customerRelationLookBS.updateApprovalFlag(custrel.getCustRelId(), GlobalConstants.APPROVAL_FLAG_1);
			this.customerRelationLookBS.updateEntity(custrel);
		}		
		custrelApproval.setOperTime(new Timestamp(new Date().getTime()));
		custrelApproval.setCustRelId(custrel.getCustRelId());
		custrelApproval.setCustRelType(custrel.getCustRelType());
		custrelApproval.setCustRelDesc(custrel.getCustRelDesc());
		custrelApproval.setCustRelStat(custrel.getCustRelStat());
		custrelApproval.setSrcCustId(custrel.getSrcCustId());
		custrelApproval.setDestCustId(custrel.getDestCustId());
		custrelApproval.setRelStartDate(custrel.getRelStartDate());
		custrelApproval.setRelEndDate(custrel.getRelEndDate());
		//修改日期
//		custrelApproval.setOperTime();
		//修改人
		custrelApproval.setOperator(custrel.getLastUpdateUser());
		//审批状态
		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
		return custrelApproval;
	}
	
	public CustrelApproval copyInfo(Custrel custrel,String flag){		
		
		//设置审批状态为未审批
		custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_1);
		//设置更新人
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		custrel.setLastUpdateUser(user.getUserName());
		//设置最后更新时间
		custrel.setLastUpdateTm(new Timestamp(new Date().getTime()));
		
		CustrelApproval custrelApproval = new CustrelApproval();
		//insert or update
		if(flag == null || flag.equals("")){
//			custrelApproval.setOperStat(custrel.getCustRelId()==null ? "01":"02");
			custrelApproval.setOperStat(custrel.getCustRelId()==null ? GlobalConstants.OPER_STAT_INSERT:GlobalConstants.OPER_STAT_UPDATE);
			//update
//			if("02".equals(custrelApproval.getOperStat())){
			if(GlobalConstants.OPER_STAT_UPDATE.equals(custrelApproval.getOperStat())){
				//得到原始对象
				Custrel custrelOrig = this.customerRelationLookBS.getEntityById(custrel.getCustRelId());
				//修改原始对象的状态
				custrelOrig.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_1);
				custrel.setSrcCustId(custrelOrig.getSrcCustId());
				custrel.setDestCustId(custrelOrig.getDestCustId());
				
				this.customerRelationLookBS.updateEntity(custrel);
			}
		}else{
			custrelApproval.setOperStat(flag);//delete
			this.customerRelationLookBS.updateEntity(custrel);
		}		
		custrelApproval.setOperTime(new Timestamp(new Date().getTime()));
		custrelApproval.setCustRelId(custrel.getCustRelId());
		custrelApproval.setCustRelType(custrel.getCustRelType());
		custrelApproval.setCustRelDesc(custrel.getCustRelDesc());
		custrelApproval.setCustRelStat("1");
		custrelApproval.setSrcCustId(custrel.getSrcCustId());
		custrelApproval.setDestCustId(custrel.getDestCustId());
		custrelApproval.setRelStartDate(custrel.getRelStartDate());
		custrelApproval.setRelEndDate(custrel.getRelEndDate());
		//修改日期
//		custrelApproval.setOperTime();
		//修改人
		custrelApproval.setOperator(custrel.getLastUpdateUser());
		//审批状态
		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
		
		return custrelApproval;
	}
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CusRelationLookVO show(@PathVariable("id") Long id) {
		CusRelationLookVO cusRelationLookVO = this.getCustRelLookVo(id);
		return cusRelationLookVO;
	}
	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/customer/customerrel/customerrelationlook-update", "id", id);
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
	
		CustrelApproval ca = null;
		String[] ids = id.split(",");
//		if (ids.length > 1) {
			for(int i=0; i<ids.length; i++){
				CusRelationLookVO cusRelationLookVO = this.getCustRelLookVo(Long.parseLong(ids[i]));
				ca = copyInfo(cusRelationLookVO, GlobalConstants.OPER_STAT_DELETE);
				this.custrelApprovalBS.updateEntity(ca);
			}
//		} else {
//			Custrel custrel = (Custrel) this.customerRelationLookBS.getEntityById(Long.parseLong(id));
//			ca = copyInfo(custrel, "03");
//			this.custrelApprovalBS.updateEntity(ca);
//		}
		return "true";
	}
	
	private  CusRelationLookVO getCustRelLookVo(Long id){
		
		CusRelationLookVO cusRelationLookVO = new CusRelationLookVO();
		Custrel custrel = (Custrel) this.customerRelationLookBS.getEntityById(id);
		Customer srcCustomer = customerInfoBS.getCustIdByCustId(custrel.getSrcCustId());
		Customer destCustomer = customerInfoBS.getCustIdByCustId(custrel.getDestCustId());
		
		cusRelationLookVO.setCustRelId(custrel.getCustRelId().toString());
		cusRelationLookVO.setSrcCustNo(srcCustomer == null ? "" : srcCustomer.getCustNo());
		cusRelationLookVO.setDestCustNo(destCustomer == null ? "" : destCustomer.getCustNo());
		cusRelationLookVO.setSrcId(srcCustomer == null ? "" : srcCustomer.getCustId().toString());
		cusRelationLookVO.setDestId(destCustomer == null ? "" : destCustomer.getCustId().toString());
		cusRelationLookVO.setCustRelStart(custrel.getRelStartDate());
		cusRelationLookVO.setCustRelEnd(custrel.getRelEndDate());
		cusRelationLookVO.setCustRelType(custrel.getCustRelType());
		cusRelationLookVO.setCustRelDesc(custrel.getCustRelDesc());
		cusRelationLookVO.setCustRelStat(custrel.getCustRelStat());
		
		return cusRelationLookVO;
	}
	/**
	 * 下载文件
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/getReportFile.*", method = RequestMethod.POST)
	@ResponseBody
	public String getReportFile(String reportNo,String rule) {
		int report = Integer.parseInt(reportNo);
		return exportReportBS.exportCustrel(report, rule);
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
			//file = realpath + GlobalConstants.EXCEL_TEMPLATE_IMPCUSTREL_CN;
			file = exportReportBS.reportTemplate(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPCUSTREL_CN);
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
			ExportUtil.deleteFile(file);
		}else{
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
			ExportUtil.deleteFile(file);
		}
	}
	
	//跳转到上传页面
	@RequestMapping(value = "/importresult", method = RequestMethod.GET)
	public ModelAndView importResult() {
//		return new ModelAndView("/ecif/customerrelationlook/customerrelationlook-upload");
		
		return new ModelAndView("/ecif/customer/customerrel/customerrelationlook-upload");
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
//		String filePath = file.getPath().replace("\\", "/");
//		Map<String, String> fileMap = Maps.newHashMap();
//		fileMap.put("fileName", file.getName());
//		fileMap.put("fileSize", file.length() + " bytes");
//		fileMap.put("filePath", filePath);
//		fileMap.put("fileFolder", filePath.substring(0,filePath.lastIndexOf("/")));
		
		if (file != null) {
			logger.info("文件[" + file.getPath() + "==" +file.getName() + "]上传完成");
//			importListBS.importCustrelList(file.getPath(), 3);
			List<List<Object[]>> reportData = importListBS.importCustrelList(file.getPath(), 3);
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
		int result = 0;
		int count = 0;
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		List<List<Object[]>> errData = new ArrayList<List<Object[]>>();
		List<List<Object[]>> errorData = new ArrayList<List<Object[]>>();
		
		for(int i=0; i < reportData.size(); i++){
			List<Object[]> failList = new ArrayList<Object[]>();
			List<Object[]> temp = reportData.get(i);
			List<Object[]> errorTemp = new ArrayList<Object[]>();
			if(temp == null){
				continue;
			}
			if(i == 0){//读取第一个sheet
				count = temp.size();
				int j = 0;
				
				//---------------------------------
				//读取数据
				Object[] oTemp = null;
				for(Object[] o : temp){
					
					oTemp = Arrays.copyOf(o, o.length+1);					
					if("".equals(o[0].toString()) || "".equals(o[1].toString()) || "".equals(o[2].toString())
					|| "".equals(o[4].toString()) || "".equals(o[5].toString()) || "".equals(o[6].toString())
					|| "".equals(o[8].toString()) || "".equals(o[9].toString()) || "".equals(o[10].toString())){						
						oTemp[oTemp.length-1] = "必填项有空值";
						errorTemp.add(oTemp);
						result++;
						continue;
					}
					
					if(o[1].toString().length() > 40 || o[5].toString().length() > 40){
						oTemp[oTemp.length-1] = "客户证件号码长度不可大于40";
						errorTemp.add(oTemp);
						result++;
						continue;
					}
					
					if(o[2].toString().length() > 70 || o[6].toString().length() > 70){
						oTemp[oTemp.length-1] = "客户证件名称长度不可大于70";
						errorTemp.add(oTemp);
						result++;
						continue;
					}
					
					Custrel custrel = new Custrel();
					custrel.setCustRelId(null);//主键
					
					//源客户证件类型是否为空，是否正确
					if(!"".equals(o[0].toString())){
						if(!isIdentType(o[0].toString())){
							oTemp[oTemp.length-1] = "源客户证件类型不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}
					
					//源客户证件号码
					if(!"".equals(o[1].toString())){
						if(!isIdentNo(o[0].toString(),o[1].toString())){
							oTemp[oTemp.length-1] = "源客户证件号码不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}
					
					//源客户号
					if(!"".equals(o[3].toString())){
						if(!isCustNo(o[3].toString(),o[0].toString(),o[1].toString(),o[2].toString())){
							oTemp[oTemp.length-1] = "源客户号不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}else{
							//存储客户标识
							Customer customer = customerInfoBS.getCustIdByCustNo(o[3].toString());
							custrel.setSrcCustId(customer.getCustId());
						}
					}
					//目标客户证件类型是否为空，是否正确
					if(!"".equals(o[4].toString())){
						if(!isIdentType(o[4].toString())){
							oTemp[oTemp.length-1] = "目标客户证件类型不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}
					
					//目标客户证件号码
					if(!"".equals(o[5].toString())){
						if(!isIdentNo(o[4].toString(),o[5].toString())){
							oTemp[oTemp.length-1] = "目标客户证件号码不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						if(o[1].toString().equals(o[5].toString()) 
							&& o[0].toString().equals(o[4].toString())
							&& o[2].toString().equals(o[6].toString())){
							oTemp[oTemp.length-1] = "目标客户三证信息与源客户三证信息相同";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}
					
					//目标客户号
					if(!"".equals(o[7].toString())){
						if(!isCustNo(o[7].toString(),o[4].toString(),o[5].toString(),o[6].toString())){
							oTemp[oTemp.length-1] = "目标客户号不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}else{
							//存储目标客户标识
							Customer customer = customerInfoBS.getCustIdByCustNo(o[7].toString());
							custrel.setDestCustId(customer.getCustId());
						}
					}
					
					//客户关系类型
					if(!"".equals(o[8].toString())){
						if(!isCustrelType(o[0].toString(),o[4].toString(),o[8].toString())){
							oTemp[oTemp.length-1] = "关系类型不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}else{
							//判断该类型是否允许添加,不允许返回true
							if(isCustrelAdd(o[8].toString())){
								oTemp[oTemp.length-1] = "该关系类型不允许添加";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
						}
					}
					//关系状态
//					if(!"".equals(o[9].toString())){
//						if(!isCustrelStat(o[9].toString())){
//							Object[] copyObj = copyExcel(temp.get(j));
//							copyObj[copyObj.length-1] = "填写关系状态不正确";
//							result++;
//							j++;
//							failList.add(copyObj);
//							continue;							
//						}
//					}else{
//						Object[] copyObj = copyExcel(temp.get(j));
//						copyObj[copyObj.length-1] = "没有填写关系状态";
//						result++;
//						j++;
//						failList.add(copyObj);
//						continue;
//					}
					
					//关系结束时间
					if(!"".equals(o[10].toString())){
						try {
							if(ConvertUtils.getDateStrToLong(o[10].toString()).longValue() 
								< ConvertUtils.getDateStrToLong(o[9].toString()).longValue()){
								oTemp[oTemp.length-1] = "关系结束时间小于关系开始时间";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
						} catch (ParseException e) {
							oTemp[oTemp.length-1] = "关系时间格式不正确";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}
					//判断源客户证件类型是个人还是机构
					String srcIdentflag = isPersonOrg(o[0].toString().trim());
					//判断目标客户证件类型是个人还是机构
					String destIdentflag = isPersonOrg(o[4].toString().trim());
					//有源客户号且有目标客户号
					if(!"".equals(o[3].toString()) && !"".equals(o[7].toString())){
						Customer srcCustomer = customerInfoBS.getCustIdByCustNo(o[3].toString());
						Customer destCustomer = customerInfoBS.getCustIdByCustNo(o[7].toString());
						if(isNoTypeImp(srcCustomer.getCustId(), destCustomer.getCustId(), o[8].toString())){
							oTemp[oTemp.length-1] = "当前记录已存在";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}else{
						Long srcId = 0L;
						Long destId = 0L;
						//没输入源客户号，但是3证信息是正确的，向证件表里插入数据
						if("per".equals(srcIdentflag)){
//							customerRelationLookBS.saveId(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, custrel,"src");
							srcId = saveId2(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, "src");
						}
						//机构
						if("org".equals(srcIdentflag)){
//							customerRelationLookBS.saveId(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, custrel,"src");
							srcId = saveId2(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, "src");
						}
						//个人
						if("per".equals(destIdentflag)){
//							customerRelationLookBS.saveId(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, custrel,"dest");
							destId = saveId2(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, "dest");
						}
						//机构
						if("org".equals(destIdentflag)){
//							customerRelationLookBS.saveId(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, custrel,"dest");
							destId = saveId2(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, "dest");
						}
						if(isNoTypeImp(srcId, destId, o[8].toString())){
							oTemp[oTemp.length-1] = "当前记录已存在";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
					}
					
					//没输入源客户号，但是3证信息是正确的，向证件表里插入数据
					if("per".equals(srcIdentflag)){
//						customerRelationLookBS.saveId(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, custrel,"src");
						saveId(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, custrel,"src");
					}
					//机构
					if("org".equals(srcIdentflag)){
//						customerRelationLookBS.saveId(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, custrel,"src");
						saveId(o[0].toString(), o[1].toString(), o[2].toString(), srcIdentflag, custrel,"src");
					}
					//个人
					if("per".equals(destIdentflag)){
//						customerRelationLookBS.saveId(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, custrel,"dest");
						saveId(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, custrel,"dest");
					}
					//机构
					if("org".equals(destIdentflag)){
//						customerRelationLookBS.saveId(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, custrel,"dest");
						saveId(o[4].toString(), o[5].toString(), o[6].toString(), destIdentflag, custrel,"dest");
					}
					//关系类型
					saveRelType(o[8].toString().trim(),custrel);
					//关系状态
//					if("有效".equals(o[9].toString().trim())){
//						custrel.setCustRelStat("1");
//					}else if("无效".equals(o[9].toString().trim())){
//						custrel.setCustRelStat("2");
//					}
					custrel.setCustRelStat("");

					try {
						//关系开始日期
						custrel.setRelStartDate(ConvertUtils.getDateStrToData(o[9].toString()));
						//关系结束日期
						custrel.setRelEndDate(ConvertUtils.getDateStrToData(o[10].toString()));
					} catch (ParseException e) {
						oTemp[oTemp.length-1] = "关系时间格式不正确";
						errorTemp.add(oTemp);
						result++;
						continue;
					}
					//最后更新人
					custrel.setLastUpdateUser(user.getUserName());
					//最后更新系统
					custrel.setLastUpdateSys("ecif");
					//默认审批通过
					custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
					custrel.setCustRelDesc("");
					//customerRelationLookBS.updateEntity(custrel);
					CustrelApproval custrelApproval = copyInfo(custrel,null);
					custrelApprovalBS.updateEntity(custrelApproval);
					//BeanUtils.copy(entity, this);
				}		
				//---------------------------------------
				errorData.add(errorTemp);
			}
		}
		String file = exportReportBS.exportCustrel(11, errorData);
		if(result == 0){
			return "成功" + count + "条记录";
		}else{
//			return "成功" + (count-result) + "条记录，错误" + result + "条";
			return "err" + file;
		}
	}
	
	/**
	 * 判断证件类型是否正确
	 * 不为空返回true
	 */
	public boolean isIdentType(String identType){
		boolean flag = false;
		Map<String,String> perMap = codeUtil.getDescCodeMapCustIdent(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMapCustIdent(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		String perStr = perMap.get(identType);
		String orgStr = orgMap.get(identType);
		if(null != perStr && !"".equals(perStr)){
			flag = true;
		}
		if(null != orgStr && !"".equals(orgStr)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 复制excel中的数据
	 */
	public Object[] copyExcel(Object[] obj){
		Object[] copyObj = Arrays.copyOf(obj, 12);
		return copyObj;
	}
	
	/**
	 * 3证齐全并且有客户号,判断是否是同一用户
	 * 正确返回true
	 */
	public boolean isCustNo(String custNo , String identType, String identNo, String identCustName){
		
		boolean flag = false;
		Customer c = customerInfoBS.getCustIdByCustNo(custNo);
		//查看该客户的三证是否对应
		if(c!=null){
			//机构或个人
			if(GlobalConstants.CUST_PERSON_TYPE.equals(c.getCustType())){
				codeMapPerIdentType = this.codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
				identType = codeMapPerIdentType.get(identType);
				PersonIdentifier person = customerInfoBS.getPersonIdentifierByCustId(identType,identNo,identCustName);
				if(person.getCustId().toString().equals(c.getCustId().toString())){
					flag = true;
				}
			}else if(GlobalConstants.CUST_ORG_TYPE.equals(c.getCustType())){
				codeMapOrgIdentType = this.codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
				identType = codeMapOrgIdentType.get(identType);
				Orgidentinfo org = customerInfoBS.getOrgidentinfoByCustId(identType,identNo,identCustName);
				if(org.getCustId().toString().equals(c.getCustId().toString())){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 判断是个人还是机构
	 * 变量 证件类型
	 */
	public  String isPersonOrg(String desc){
		String flag = "";
		Map<String, String> listPer = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String, String> listOrg = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		if(listPer.get(desc) != null){
			flag = "per";
		}
		if(listOrg.get(desc) != null){
			flag = "org";
		}
		return flag;
	}
	
	/**
	 * 保存关系类型
	 */
	public void saveRelType(String relType,Custrel custrel){
		//域名标识
		String flag = GlobalConstants.CODE_STR_CUSTREL_TYPE;
		//返回所有查询值
		Map<String, String> codeMap = Maps.newHashMap();
		
		codeMap = codeUtil.getDescCodeMap(flag);
		String stdCode = codeMap.get(relType);
		
		custrel.setCustRelType(stdCode);
	}
	
	/**
	 * 判断新增的用户是否已经在审批表中或者已经在关系表中
	 * 存在返回true
	 */
	public boolean isNoTypeImp(Long srcCustId, Long destCustId, String custrelType) {
		boolean flag = false;
		if(srcCustId == 0L || destCustId == 0L){
			return flag;
		}
		Map<String, String> codeMapRelType1 = Maps.newHashMap();//关系类型
		codeMapRelType1 = this.codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		custrelType = codeMapRelType1.get(custrelType);
		//存在返回true
		boolean custrelFlag = customerRelationLookBS.isExistCustRel(srcCustId, destCustId, custrelType);
		//存在返回true
		boolean custrelAprovalFlag = custrelApprovalBS.isExistCustRelApproval(srcCustId, destCustId, custrelType);
		//在关系表中有该条数据，或者在关系审批表中这条数据在待审批
		if(custrelFlag == true || custrelAprovalFlag == true){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断源证件类型，目标证件类型和关系类型是否正确
	 * 正确返回true
	 */
	public boolean isCustrelType(String srcIdentType,String destIdentType,String custrelType){
		boolean flag = false;
		
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		Map<String,String> custrelTypeMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		
		//返回关系的码值
		if(custrelTypeMap.get(custrelType) == null){
			return flag;
		}
		Long intCustrelType = Long.parseLong(custrelTypeMap.get(custrelType));
		String srcType = "";
		String destType = "";
		//判断源客户证件类型
		if(orgMap.get(srcIdentType) != null){
			srcType= "org";
		}else if(perMap.get(srcIdentType) != null){
			srcType= "per";
		}
		//判断目标客户证件类型
		if(orgMap.get(destIdentType) != null){
			destType= "org";
		}else if(perMap.get(destIdentType) != null){
			destType = "per";
		}
		//
		if("org".equals(srcType) && "org".equals(destType) && intCustrelType < 2001001000L && intCustrelType > 1001001000L){
			flag = true;
		}else if("org".equals(srcType) && "per".equals(destType) && intCustrelType < 3000000000L && intCustrelType > 2001001000L){
			flag = true;
		}else if("per".equals(srcType) && "per".equals(destType) && intCustrelType < 5000000000L && intCustrelType > 3000000000L){
			flag = true;
		}
		return flag ;
	}
	
	/**
	 * 判断输入证件类型和证件号码是否正确
	 * 正确返回true
	 */
	public boolean isIdentNo(String type,String no){
		boolean flag = false;
		
		//只能为数字
		String onlyNum = "^[0-9]*{1}";
		//判断身份证号是否正确,判断前十五位是否是数字
		if(GlobalConstants.IDENTITY_ID.equals(type)
			|| GlobalConstants.IDENTITY_TEMPID.equals(type)
			|| GlobalConstants.IDENTITY_POP.equals(type)){
//			no = no.substring(0,15);
//			flag = no.matches(onlyNum);
			String validate = "";
			try {
				validate = CardUtil.IDCardValidate(no);
			} catch (ParseException e) {
				flag = false;
			}
			if(validate.equals("")){
				//身份证或临时身份证或户口簿号码不合法
				flag = true;
			}
		}else{
			//大于6位，且不能有空格
			if((no.length() > 6 || no.length() == 6) && (no.indexOf(" ") == -1)){
				flag = true;
			}
		}
		
		return flag;
	}
	
	/**
	 * 判断关系状态是否正确
	 * 正确返回true
	 */
	public boolean isCustrelStat(String custrelStat){
		boolean flag = false;
		Map<String,String> statMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_VALID_TYPE);
		String statStr = statMap.get(custrelStat);
		if(statStr!=null && !"".equals(statStr)){
			flag = true;
		}
		
		return flag;
	}	
	
	/**
	 * 判断该类型是否允许添加
	 * 不允许返回true
	 */
	public boolean isCustrelAdd(String custrelType){
		boolean flag = false;
		String[] custrelTypeArr = GlobalConstants.OTHER_CUSTREL;
		for(int i = 0; i < custrelTypeArr.length; i++){
			if(custrelTypeArr[i].equals(custrelType)){
				flag = true;
			}
			break;
		}
		
		return flag;
	}
	
	/**
	 * 判断选择的类型是否正确
	 */
	@RequestMapping("isAllTypeUpdate.*")
	@ResponseBody
	public String isAllTypeUpdate(@RequestParam("relType") String relType) {
		String[] custRelType = GlobalConstants.OTHER_CUSTREL;
		
		String flag = "true";
		Map<String,String> relMap = codeUtil.getCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		
		for(String str : custRelType){
			if(str.equals(relMap.get(relType))){
				flag = "false";
			}
		}
		
		return flag;
	}
	/**
	 * 保存客户标识,并且向证件表里插入数据
	 * 
	 */
	public void saveId(String identType1, String identNo, String identCustName,String flag,Custrel custrel,String srcOrDest){
		
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		Date creatTime = new Date();
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		String identType = perMap.get(identType1);
		if(	identType == null || "".equals(identType)){
			identType = orgMap.get(identType1);
		}
		
		if("per".equals(flag)){
			//是个人客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			PersonIdentifier personidentifier = customerInfoBS.getPersonIdentifierByCustId(identType,identNo,identCustName);
			Long custId = 0L;
			if(personidentifier == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
				custId = customerRelationLookBS.getIdentSeq();
				//保存证件信息
				personidentifier = new PersonIdentifier();
				personidentifier.setIdentId(null);
                
				personidentifier.setCustId(custId);
				personidentifier.setIdentType(identType);
				personidentifier.setIdentNo(identNo);
				personidentifier.setIdentCustName(identCustName);
				personidentifier.setLastUpdateSys("00");
				personidentifier.setLastUpdateTm(ConvertUtils.getDateToString2(creatTime));
				personidentifier.setLastUpdateUser(user.getUserName());
				//引入一个个人证件的bs
//				this.baseDAO.save(personidentifier);
				personIdentifierBS.updateEntity(personidentifier);
				
				//设置客户关系标识,判断是源客户还是目标客户
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(custId);
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(custId);
				}
				
			}else{
				custId = personidentifier.getCustId();//new Customer();
				//设置客户关系标识,判断是源客户还是目标客户
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(personidentifier.getCustId());
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(personidentifier.getCustId());
				}
			}
			NameTitle nt = nameTitleBS.getEntityById(custId);//new NameTitle();
			if(nt == null){
				nt = new NameTitle();
				nt.setCustId(custId);
				nt.setName(identCustName);
//				nt.setLastUpdateSys("00");
//				nt.setLastUpdateTm(ConvertUtils.getDateToString2(creatTime));
//				nt.setLastUpdateUser(user.getUserName());
				nameTitleBS.updateEntity(nt);
			}
			
			Customer ct = customerBS.getEntityById(custId);//new Customer();
			if(ct == null){
				ct = new Customer();
				ct.setCustId(custId);
				ct.setCustType(GlobalConstants.CUST_PERSON_TYPE);
				ct.setInoutFlag("0");
				ct.setBlankFlag("Y");
				ct.setCreateDate(creatTime);
				ct.setCreateTime(new Timestamp(creatTime.getTime()));
				ct.setCreateBranchNo("ecif");
				ct.setCreateTellerNo("ecif");
				ct.setLastUpdateSys("00");
				ct.setLastUpdateTm(ConvertUtils.getDateToString2(creatTime));
				ct.setLastUpdateUser(user.getUserName());
				customerBS.updateEntity(ct);
			}			
		}
		
		if("org".equals(flag)){
			//是机构客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			Orgidentinfo orgidentinfo = customerInfoBS.getOrgidentinfoByCustId(identType,identNo,identCustName);
			Long custId = 0L;
			if(orgidentinfo == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
				custId = customerRelationLookBS.getIdentSeq();
				//保存证件信息
				orgidentinfo = new Orgidentinfo();
				orgidentinfo.setIdentId(null);
				orgidentinfo.setCustId(custId);
				orgidentinfo.setIdentType(identType);
				orgidentinfo.setIdentNo(identNo);
				orgidentinfo.setIdentCustName(identCustName);
				orgidentinfo.setLastUpdateSys("00");
				orgidentinfo.setLastUpdateTm(ConvertUtils.getDateToString2(creatTime));
				orgidentinfo.setLastUpdateUser(user.getUserName());
				//引入一个机构证件的bs
//				this.baseDAO.save(orgidentinfo);
				orgidentinfoBS.updateEntity(orgidentinfo);
				
				//设置客户关系标识
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(custId);
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(custId);
				}
			}else{
				custId = orgidentinfo.getCustId();//new Customer();
				if("src".equals(srcOrDest)){
					custrel.setSrcCustId(orgidentinfo.getCustId());
				}else if("dest".equals(srcOrDest)){
					custrel.setDestCustId(orgidentinfo.getCustId());
				}
			}
			NameTitle nt = nameTitleBS.getEntityById(custId);//new NameTitle();
			if(nt == null){
				nt = new NameTitle();
				nt.setCustId(custId);
				nt.setName(identCustName);
//				nt.setLastUpdateSys("00");
//				nt.setLastUpdateTm(ConvertUtils.getDateToString2(creatTime));
//				nt.setLastUpdateUser(user.getUserName());
				nameTitleBS.updateEntity(nt);
			}
			
			Customer ct = customerBS.getEntityById(custId);//new Customer();
			if(ct == null){
				ct = new Customer();
				ct.setCustId(custId);
				ct.setCustType(GlobalConstants.CUST_ORG_TYPE);
				ct.setInoutFlag("0");
				ct.setBlankFlag("Y");
				ct.setCreateDate(creatTime);
				ct.setCreateTime(new Timestamp(creatTime.getTime()));
				ct.setCreateBranchNo("ecif");
				ct.setCreateTellerNo("ecif");
				ct.setLastUpdateSys("00");
				ct.setLastUpdateTm(ConvertUtils.getDateToString2(creatTime));
				ct.setLastUpdateUser(user.getUserName());
				customerBS.updateEntity(ct);
			}
		}		
	}
	public Long saveId2(String identType1, String identNo, String identCustName,String flag, String srcOrDest){
		
		Map<String,String> perMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String,String> orgMap = codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		String identType = perMap.get(identType1);
		
		if(	identType == null || "".equals(identType)){
			identType = orgMap.get(identType1);
		}
		if("per".equals(flag)){
			//是个人客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			PersonIdentifier personidentifier = customerInfoBS.getPersonIdentifierByCustId(identType,identNo,identCustName);
			Long custId = 0L;
			if(personidentifier == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
			}else{
				custId = personidentifier.getCustId();//new Customer();
			}
			return custId;
		}
		if("org".equals(flag)){
			//是机构客户，查找个人证件表，判断是否有该客户存在,存在保存客户标识，不存在生成客户标识，将证件信息存入证件表
			Orgidentinfo orgidentinfo = customerInfoBS.getOrgidentinfoByCustId(identType,identNo,identCustName);
			Long custId = 0L;
			if(orgidentinfo == null){
				//没有客户号，切证件类型中也没有客户标识，生成客户标识
			}else{
				custId = orgidentinfo.getCustId();//new Customer();
			}
			return custId;
		}
		return 0L;
	}
	
	/**
	 * 判断选择的类型是否正确
	 * 类型正确返回true
	 */
	@RequestMapping("isAllType2.*")
	@ResponseBody
	public String isAllType2(@RequestParam("relType") String relType,@RequestParam("srcCustNo") String srcCustNo,@RequestParam("destCustNo") String destCustNo) {
		
		String[] custRelType = GlobalConstants.OTHER_CUSTREL;
		String flag = "true";
		if(StringUtils.isEmpty(relType) || StringUtils.isEmpty(srcCustNo) || StringUtils.isEmpty(destCustNo)){
			return flag;
		}
		
		Map<String,String> relMap = codeUtil.getCodeMap(GlobalConstants.CODE_STR_CUSTREL_TYPE);
		
		//类型是否允许操作,不允许返回false
		boolean isNotAllow = true;
		for(String str : custRelType){
			if(str.equals(relMap.get(relType))){
				isNotAllow = false;
			}
		}
		
		//关系对应是否正确,正确true
		boolean isCustrelRight = false;
		long intCustrelType = Long.parseLong(relType);
		
		Customer srcCustomer = customerInfoBS.getCustIdByCustNo(srcCustNo);
		Customer destCustomer = customerInfoBS.getCustIdByCustNo(destCustNo);
		
		String srcStr = "";
		String destStr = "";
		
		if(GlobalConstants.CUST_ORG_TYPE.equals(srcCustomer.getCustType())){
			srcStr = "org";
		}
		
		if(GlobalConstants.CUST_PERSON_TYPE.equals(srcCustomer.getCustType())){
			srcStr = "per";
		}
		
		if(GlobalConstants.CUST_ORG_TYPE.equals(destCustomer.getCustType())){
			destStr = "org";
		}
		
		if(GlobalConstants.CUST_PERSON_TYPE.equals(destCustomer.getCustType())){
			destStr = "per";
		}
		
		if("org".equals(srcStr) && "org".equals(destStr) && intCustrelType < 2001001000L && intCustrelType > 1001001000L){
			isCustrelRight = true;
		}else if("org".equals(srcStr) && "per".equals(destStr) && intCustrelType < 3000000000L && intCustrelType > 2001001000L){
			isCustrelRight = true;
		}else if("per".equals(srcStr) && "per".equals(destStr) && intCustrelType < 5000000000L && intCustrelType > 3000000000L){
			isCustrelRight = true;
		}
		
		if(isNotAllow == false || isCustrelRight == false ){
			flag = "false";
		}
		
		return flag;
	}
}
