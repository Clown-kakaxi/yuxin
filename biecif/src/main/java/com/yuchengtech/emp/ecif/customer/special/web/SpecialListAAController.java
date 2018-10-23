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
import com.yuchengtech.emp.ecif.customer.customerinfo.service.CustomerInfoBS;
import com.yuchengtech.emp.ecif.customer.entity.customer.Customer;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseorg.Orgidentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerbaseperson.PersonIdentifier;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.SpecialList;
import com.yuchengtech.emp.ecif.customer.entity.other.SpecialListApproval;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListApprovalBS;
import com.yuchengtech.emp.ecif.customer.special.service.SpecialListAABS;
import com.yuchengtech.emp.ecif.customer.special.web.vo.SpecialListVO;

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
@RequestMapping("/ecif/customer/speciallist")
public class SpecialListAAController extends BaseController {

	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private SpecialListAABS specialListBS;
	
//	@Autowired
//	private HSpecialListBS hspecialListBS;
	
	@Autowired
	private SpecialListApprovalBS specialListApprovalBS;
	
	@Autowired
	private CustomerInfoBS customerInfoBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ImportListBS importListBS;
	
	//一次进入不查询
	private boolean flag = false;
	
	private Map<String, String> codeMapValidType = Maps.newHashMap();//有效标识
	private Map<String, String> codeMapApprovalStatus = Maps.newHashMap();//审批状态
	private Map<String, String> codeMapDisStatus = Maps.newHashMap();//操作状态
	private Map<String, String> codeMapIdentType = Maps.newHashMap();//个人与机构证件类型
	private Map<String, String> codeMapSpecialListKind = Maps.newHashMap();//黑名单类型
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		//flag = false;
		return "/ecif/customer/special/speciallist-index";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
//		if(flag == false){
//			flag = true;
//			return null;
//		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<SpecialListVO> searchResult = specialListBS.getSpecialListList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
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
		return new ModelAndView("/ecif/customer/special/speciallist-edit", "id", id);
	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/customer/special/speciallist-edit";
	}
	
	/**
	 * 获取combobox证件类型
	 */
	@ResponseBody
	@RequestMapping("getComBoBoxIdentType.*")
	public List<Map<String,String>> getComBoBoxIdentType() {
		//个人与机构的证件类型
		String codeType = GlobalConstants.CODE_STR_ORGIDENT_TYPE + ","
				+ GlobalConstants.CODE_STR_PERSONIDENT_TYPE;		
		return this.codeUtil.getComBoBox2(codeType);
	}
	
	/**
	 * 获取combobox黑名单类型
	 */
	@ResponseBody
	@RequestMapping("getComBoBoxSpecialListKind.*")
	public List<Map<String,String>> getComBoBoxSpecialListKind() {
		String codeType = GlobalConstants.CODE_STR_SPECIALlIST_TYPE;//黑名单类别
		return this.specialListBS.getComBoBoxSpecialListKind(codeType);
	}
	
	/**
	 * 获取combobox有效标识
	 */
	@ResponseBody
	@RequestMapping("getComBoBoxValidType.*")
	public List<Map<String,String>> getComBoBoxValidType() {
		String codeType = GlobalConstants.CODE_STR_VALID_TYPE;//有效标识
		return this.codeUtil.getComBoBox2(codeType);
	}
	
	/**
	 * 根据选择的关系类型，返回关系种类下拉列表
	 */
	@RequestMapping("getModeVer.*")
	@ResponseBody
	public List<Map<String,String>> getModeVer(@RequestParam("adapterId") String adapterId) {
		//个人与机构的证件类型
		if(adapterId.equals(GlobalConstants.CUST_PERSON_TYPE)){
			return this.codeUtil.getComBoBox(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		}else{
			return this.codeUtil.getComBoBox(GlobalConstants.CODE_STR_ORGIDENT_TYPE);
		}
	}
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	// POST /module/
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(SpecialListVO specialListVO) {
		if(specialListVO.getSpecialListId() != null){//修改
			//更新原来黑名单的审批标识
			specialListBS.updateApprovalFlag(
					specialListVO.getSpecialListId(), GlobalConstants.APPROVAL_FLAG_1);
			//因为页面屏蔽控件，无法获取当前值，所以重新查询黑名单信息
			SpecialList sl = specialListBS.getEntityById(specialListVO.getSpecialListId());
			//对可以修改的项，获取页面值修改查询出的对象值
			sl.setEnterReason(specialListVO.getEnterReason());
			sl.setEndDate(specialListVO.getEndDate());
			//黑名单信息赋予新的vo构造
			specialListVO = new SpecialListVO(sl);
		}else{//新增
			Customer customer = customerInfoBS.getCustIdByCustNo(specialListVO.getCustNo());
			if(customer != null){//有客户就设置客户标识
				specialListVO.setCustId(customer!=null?customer.getCustId():null);
			}else{//没有客户（根据三证信息查询客户标识）
				String identType = specialListVO.getIdentType();
				String strCate = identType.substring(0, 2);
				String strCode = identType;//.substring(8);
				if(strCate.equals("02"/*GlobalConstants.CODE_STR_ORGIDENT_TYPE*/)){
					Orgidentinfo o = customerInfoBS.getOrgidentinfoByCustId(//对公，同业
							strCode, specialListVO.getIdentNo(), specialListVO.getIdentCustName());
					if(o != null){
						specialListVO.setCustId(o.getCustId());
					}else{
						specialListVO.setCustId(null);
					}
				}else{
					PersonIdentifier p = customerInfoBS.getPersonIdentifierByCustId(//对私
							strCode, specialListVO.getIdentNo(), specialListVO.getIdentCustName());
					if(p != null){
						specialListVO.setCustId(p.getCustId());
					}else{
						specialListVO.setCustId(null);
					}
				}
			}
		}
		//拷贝属性信息
		SpecialListApproval sa = copyInfo(specialListVO, null);
		//insert and update for insert speciallistapproval info
		this.specialListApprovalBS.updateEntity(sa);
	}
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SpecialListVO show(@PathVariable("id") String id) {
		Long id1 = Long.valueOf(id.split("-")[0]);
		String id2 = id.split("-")[1];
		if(id2.equals("0")){
			id2 = "";
		}
		SpecialList specialList = (SpecialList) this.specialListBS.getEntityById(id1);
		SpecialListVO temp = new SpecialListVO(id2, specialList);
		return temp;
	}
	
	/**
	 * 执行删除操作，可进行指定删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
		SpecialListApproval sa = null;
		String[] ids = id.split(",");
		//delete for insert speciallistapproval info
		if (ids.length > 0) {
			for(int i=0; i<ids.length; i++){
				SpecialList specialList = (SpecialList) this.specialListBS.getEntityById(Long.valueOf(ids[i]));
				specialListBS.updateApprovalFlag(
						specialList.getSpecialListId(), GlobalConstants.APPROVAL_FLAG_1);
				sa = copyInfo(specialList, "delete");
				this.specialListApprovalBS.updateEntity(sa);
			}
		}
		return "true";
	}
	
	/**
	 * 拷贝属性信息
	 */
	public SpecialListApproval copyInfo(SpecialList specialList, String flag){
		//GlobalConstants.CODE_STR_PERSONIDENT_TYPE
		//int codeCount = GlobalConstants.CODE_STR_ORGIDENT_TYPE.length();
		
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		
		SpecialListApproval sa = new SpecialListApproval();
		//approval info
		sa.setApprovalNote("");
		sa.setApprovalOperator("");
		sa.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
		sa.setApprovalTime(null);
		//operator info
		sa.setOperator(user.getUserName());
		sa.setOperTime(new Timestamp(new Date().getTime()));
		if(StringUtils.isEmpty(flag)){
			//insert or update
			sa.setOperStat(specialList.getSpecialListId()==null
				?GlobalConstants.OPER_STAT_INSERT
				:GlobalConstants.OPER_STAT_UPDATE);
		}else{
			//delete
			sa.setOperStat(GlobalConstants.OPER_STAT_DELETE);
		}		
		//三证信息
		sa.setIdentCustName(specialList.getIdentCustName());
		sa.setIdentNo(specialList.getIdentNo());
		sa.setIdentType(specialList.getIdentType());
		//base info
		sa.setCustId(specialList.getCustId()==null?null:specialList.getCustId());//客户标识
		sa.setStartDate(specialList.getStartDate());
		sa.setEndDate(specialList.getEndDate());
		sa.setSpecialListFlag("");
		sa.setSpecialListId(specialList.getSpecialListId()==null?null:specialList.getSpecialListId());
		sa.setSpecialListType(GlobalConstants.SPECIALLIST_TYPE);
		sa.setEnterReason(specialList.getEnterReason());
		sa.setStatFlag("1");
		sa.setSpecialListKind(specialList.getSpecialListKind());
		return sa;
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
			//file = realpath + GlobalConstants.EXCEL_TEMPLATE_IMPSPECIALLIST_CN;
			file = exportReportBS.reportTemplate(realpath, GlobalConstants.EXCEL_TEMPLATE_IMPSPECIALLIST_CN);
			ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
			ExportUtil.deleteFile(file);
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
		//String custNo, String identNo, String lastUpdateSys
		int report = Integer.parseInt(repo);
		return exportReportBS.export(report, custNo, identNo, lastUpdateSys, "", "", "");
	}
	
	/**
	 * 跳转到上传页面
	 * @return
	 */
	@RequestMapping(value = "/importresult", method = RequestMethod.GET)
	public ModelAndView importResult() {
		return new ModelAndView("/ecif/customer/special/speciallist-upload");
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
			logger.info("文件[" + file.getName() + "]上传完成");
			List<List<Object[]>> reportData = importListBS.importSpecialList(file.getPath(), 3);
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
		Map<String, String> codeMapValidType1 = Maps.newHashMap();//有效标识
		codeMapValidType1 = this.codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_VALID_TYPE);
		Map<String, String> codeMapIdentType1 = Maps.newHashMap();//个人与机构证件类型
		codeMapIdentType1 = this.codeUtil.getDescCodeMap(
					GlobalConstants.CODE_STR_ORGIDENT_TYPE + "," + 
					GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		Map<String, String> codeMapSpecialListKind1 = Maps.newHashMap();//黑名单类型
		codeMapSpecialListKind1 = this.specialListBS.getDescCodeMapSpecialListKind(
				GlobalConstants.CODE_STR_SPECIALlIST_TYPE);
		//
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		
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
				SpecialList sl = null;
//				List<Object> list = Lists.newArrayList();
				Object[] oTemp = null;
				for(Object[] o : temp){
					
					oTemp = Arrays.copyOf(o, o.length+1);
//					List<Object> err = Arrays.asList(o);
					//
					sl = new SpecialList();
					sl.setSpecialListId(null);//主键
					sl.setSpecialListType(GlobalConstants.SPECIALLIST_TYPE);//黑名单类型
					sl.setSpecialListFlag("1");//黑名单标识
					//证件类型	证件号码	证件户名	客户编号
					//黑名单分类	列入原因	状态标志	起始日期	结束日期
					//检验必填项，三证是否齐全
					if(o[0] != null && !o[0].toString().equals("") &&
							o[1] != null && !o[1].toString().equals("") &&
							o[2] != null && !o[2].toString().equals("") &&
							o[4] != null && !o[4].toString().equals("") &&
							o[5] != null && !o[5].toString().equals("") &&
							o[6] != null && !o[6].toString().equals("") &&
							o[7] != null && !o[7].toString().equals("") &&
							o[8] != null && !o[8].toString().equals("")){
						if(o[1].toString().length() > 40){
							oTemp[oTemp.length-1] = "客户证件号码长度不可大于40";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						if(o[2].toString().length() > 70){
							oTemp[oTemp.length-1] = "客户证件名称长度不可大于70";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						String it = codeMapIdentType1.get(o[0].toString());
						String skl = codeMapSpecialListKind1.get(o[4].toString());
						String vt = codeMapValidType1.get(o[6].toString());
						if(StringUtils.isEmpty(it)){
							//证件类型与码值不符
							oTemp[oTemp.length-1] = "证件类型与码值不符";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						if(StringUtils.isEmpty(skl)){
							//黑名单类别与码值不符
							oTemp[oTemp.length-1] = "黑名单类别与码值不符";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						if(StringUtils.isEmpty(vt)){
							//有效标识与码值不符
							oTemp[oTemp.length-1] = "有效标识与码值不符";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						String kind = this.specialListBS.getOtherKind(codeMapSpecialListKind1);
						if(kind.equals(skl)){
							//受控制黑名单类型不可上传
							oTemp[oTemp.length-1] = "受控制黑名单类型不可上传";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//证件号码验证
						if(GlobalConstants.IDENTITY_ID.equals(o[0].toString())
							|| GlobalConstants.IDENTITY_TEMPID.equals(o[0].toString())
							|| GlobalConstants.IDENTITY_POP.equals(o[0].toString())){
							String validate = "";
							try {
								validate = CardUtil.IDCardValidate(o[1].toString().trim());
							} catch (ParseException e) {
								//
								oTemp[oTemp.length-1] = "居民身份证或临时居民身份证或户口簿号码不合法";
								errorTemp.add(oTemp);
							}
							if(!validate.equals("")){
								//身份证号码不合法
								oTemp[oTemp.length-1] = "居民身份证或临时居民身份证或户口簿号码不合法";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
						}else{
							if(!CardUtil.otherCardValidate(o[1].toString().trim()).equals("")){
								//其他号码不合法，长度必须大于等于6位，中间不能有空格
								oTemp[oTemp.length-1] = "号码不合法，长度必须大于等于6位，中间不能有空格";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
						}
						//客户号与三证的验证
						long custIdTemp = 0L;
						String strCate = it.substring(0, 2);
						String strCode = it;//.substring(8);
						if(strCate.equals("02"/*GlobalConstants.CODE_STR_ORGIDENT_TYPE*/)){
							Orgidentinfo org = customerInfoBS.getOrgidentinfoByCustId(//对公，同业
									strCode, o[1].toString(), o[2].toString());
							if(org != null ){
								custIdTemp = org.getCustId();
							}
						}else{
							PersonIdentifier per = customerInfoBS.getPersonIdentifierByCustId(//对私
									strCode, o[1].toString(), o[2].toString());
							if(per != null){
								custIdTemp = per.getCustId();
							}
						}
						if(o[3] != null && !o[3].toString().equals("")){//有客户就设置客户标识
							Customer c = customerInfoBS.getCustIdByCustNo(o[3].toString());
							if(c != null && c.getCustId().longValue() == custIdTemp){
								sl.setCustId(c.getCustId());
							}else{
								oTemp[oTemp.length-1] = "客户号与三证信息不匹配";
								errorTemp.add(oTemp);
								result++;
								continue;
							}
						}else{//没有客户（根据三证信息查询客户标识）
							Customer c = customerInfoBS.getCustIdByCustId(custIdTemp);
							sl.setCustId(c!=null?c.getCustId():null);
						}
						//新增的黑名单已经存在
						SpecialList temp1 = specialListBS.getSpecialListByInfo(
								it, o[1].toString(), o[2].toString(), skl);
						if(temp1 != null){
							oTemp[oTemp.length-1] = "该条黑名单已经存在";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						//新增的黑名单在待审批中
						SpecialListApproval temp2 = specialListApprovalBS.getSpecialListApprovalByInfo(
								it, o[1].toString(), o[2].toString(), skl);
						if(temp2 != null){
							oTemp[oTemp.length-1] = "该条黑名单在待审批中";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						sl.setIdentType(it);
						sl.setIdentNo(o[1].toString());
						sl.setIdentCustName(o[2].toString());
						sl.setSpecialListKind(skl);
						sl.setEnterReason(o[5].toString());
						sl.setStatFlag(vt);
					}else{
						//必填项有空值
						oTemp[oTemp.length-1] = "必填项有空值";
						errorTemp.add(oTemp);
						result++;
						continue;
					}
					try {
						Date tempDate = o[7]!=null?ConvertUtils.getDateStrToData(o[7].toString()):null;
						Date tempDate2 = o[8]!=null?ConvertUtils.getDateStrToData(o[8].toString()):null;
						if(ConvertUtils.getDateStrToLong(o[8].toString()) < ConvertUtils.getDateStrToLong(o[7].toString())){
							//结束时间早于开始时间
							oTemp[oTemp.length-1] = "结束时间早于开始时间";
							errorTemp.add(oTemp);
							result++;
							continue;						
						}
						sl.setStartDate(tempDate);
						sl.setEndDate(tempDate2);
					} catch (ParseException e) {
						//时间类型转换错误
						oTemp[oTemp.length-1] = "时间类型转换错误";
						errorTemp.add(oTemp);
						result++;
						continue;
					}
					
					sl.setLastUpdateSys("ecif");
					sl.setLastUpdateUser(user.getUserName());
					sl.setLastUpdateTm(new Timestamp(new Date().getTime()));
					sl.setTxSeqNo("");
					sl.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
					//specialListBS.saveEntity(sl);
					//拷贝属性信息
					SpecialListApproval sa = copyInfo(new SpecialListVO(sl), null);
					//insert and update for insert speciallistapproval info
					this.specialListApprovalBS.updateEntity(sa);
				}
				errorData.add(errorTemp);
			}
		}
		//错误数据结果集 errorData
		String file = exportReportBS.export(11, errorData);
		//
		if(result == 0){
			return "成功" + count + "条记录";
		}else{
			//return "成功" + (count-result) + "条记录，错误" + result + "条";
			return "err" + file;
		}
	}
	/**
	 * 页面验证客户号与三证信息
	 * @param custNo
	 * @param identType
	 * @param identNo
	 * @param identCustName
	 * @return
	 */
	@RequestMapping("/validatedata")
	@ResponseBody
	public String validationData(String custNo, String identType, 
			String identNo, String identCustName, String specialListKind){
		//
		String t0 = "所选的黑名单类别不可维护";
		String t1 = "输入的客户编号不存在";
		String t2 = "客户编号对应的三证信息不符";
		String t3 = "新增的黑名单已经存在";
		String t4 = "新增的黑名单在待审批中";
		String t5 = "居民身份证或临时居民身份证或户口簿号码不合法";
		String t6 = "证件号码大于等于6位，中间不能有空格";
		//
		String strCate = identType.substring(0,2);
		String strCode = identType;//.substring(8);
		//
		Map<String, String> map = Maps.newHashMap();
		map = this.codeUtil.getDescCodeMap(GlobalConstants.CODE_STR_PERSONIDENT_TYPE);
		String code = map.get(GlobalConstants.IDENTITY_ID);
		String code1 = map.get(GlobalConstants.IDENTITY_TEMPID);
		String code2 = map.get(GlobalConstants.IDENTITY_POP);
		//
		if(!StringUtils.isEmpty(specialListKind)){
			map = Maps.newHashMap();
			String kind = this.specialListBS.getOtherKind(map);
			if(kind.equals(specialListKind)){
				return t0;
			}
		}else{
			return t0;
		}
		//
		if(!StringUtils.isEmpty(custNo)){//有客户编号
			Customer c = customerInfoBS.getCustIdByCustNo(custNo);
			if(c == null){
				return t1;
			}
			String type = c.getCustType();
			if(type.equals(GlobalConstants.CUST_PERSON_TYPE)){//对私		
				//证件验证
				if((code != null && code.equals(strCode)) 
					|| (code1 != null && code1.equals(strCode))
					|| (code2 != null && code2.equals(strCode))){
					String validate = "";
					try {
						validate = CardUtil.IDCardValidate(identNo);
					} catch (ParseException e) {
						return t5;
					}
					if(!validate.equals("")){
						//身份证号码不合法
						return t5;
					}
				}else{
					if(!CardUtil.otherCardValidate(identNo).equals("")){
						//其他号码不合法，长度必须大于等于6位，中间不能有空格
						return t6;
					}
				}
				PersonIdentifier p = customerInfoBS.getPersonIdentifierByCustId(strCode, identNo, identCustName);
				if(p != null){
					if(p.getCustId().longValue() != c.getCustId().longValue()){
						return t2;
					}
				}else{
					return t2;
				}
			}else{//对公，同业
				//证件验证
				if(!CardUtil.otherCardValidate(identNo).equals("")){
					//其他号码不合法，长度必须大于等于6位，中间不能有空格
					return t6;
				}
				Orgidentinfo o = customerInfoBS.getOrgidentinfoByCustId(strCode, identNo, identCustName);
				if(o != null){
					if(o.getCustId().longValue() != c.getCustId().longValue()){
						return t2;
					}
				}else{
					return t2;
				}
			}
		}else{//没有客户编号
			if(strCate.equals("01"/*GlobalConstants.CODE_STR_PERSONIDENT_TYPE*/)){
				//证件验证
				if((code != null && code.equals(strCode)) 
					|| (code1 != null && code1.equals(strCode))
					|| (code2 != null && code2.equals(strCode))){
					String validate = "";
					try {
						validate = CardUtil.IDCardValidate(identNo);
					} catch (ParseException e) {
						return t5;
					}
					if(!validate.equals("")){
						//身份证号码不合法
						return t5;
					}
				}else{
					if(!CardUtil.otherCardValidate(identNo).equals("")){
						//其他号码不合法，长度必须大于等于6位，中间不能有空格
						return t6;
					}
				}
			}else{
				//证件验证
				if(!CardUtil.otherCardValidate(identNo).equals("")){
					//其他号码不合法，长度必须大于等于6位，中间不能有空格
					return t6;
				}
			}
		}
		//没有客户编号不验证三证信息
		//
		SpecialList temp1 = specialListBS.getSpecialListByInfo(
				identType, identNo, identCustName, specialListKind);
		if(temp1 != null){
			return t3;
		}
		//
		SpecialListApproval temp2 = specialListApprovalBS.getSpecialListApprovalByInfo(
				identType, identNo, identCustName, specialListKind);
		if(temp2 != null){
			return t4;
		}
		return "成功";
	}

	/**
	 * 获取有效标识的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapValidType.*")
	public String getCodeMapValidType(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = GlobalConstants.CODE_STR_VALID_TYPE;
		//
		if(codeMapValidType == null || codeMapValidType.isEmpty()){
			codeMapValidType = Maps.newHashMap();
			codeMapValidType = this.codeUtil.getCodeMap(flag);
		}
		String result = "";
		if(codeMapValidType.get(paramValue)!= null && !codeMapValidType.get(paramValue).equals("")){
			result = codeMapValidType.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 获取操作类型的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapDisStatus.*")
	public String getCodeMapDisStatus(String paramTypeNo, String paramValue){
		//
		if(codeMapDisStatus == null || codeMapDisStatus.isEmpty()){
			codeMapDisStatus = Maps.newHashMap();
			codeMapDisStatus.put(GlobalConstants.OPER_STAT_INSERT, "新增");
			codeMapDisStatus.put(GlobalConstants.OPER_STAT_UPDATE, "更新");
			codeMapDisStatus.put(GlobalConstants.OPER_STAT_DELETE, "删除");
		}
		String result = "";
		if(codeMapDisStatus.get(paramValue)!= null && !codeMapDisStatus.get(paramValue).equals("")){
			result = codeMapDisStatus.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}	
	/**
	 * 获取审批状态的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapApprovalStatus.*")
	public String getCodeMapApprovalStatus(String paramTypeNo, String paramValue){
		//
		if(codeMapApprovalStatus == null || codeMapApprovalStatus.isEmpty()){
			codeMapApprovalStatus = Maps.newHashMap();
			codeMapApprovalStatus.put(GlobalConstants.APPROVAL_STAT_2, "审批通过");
			codeMapApprovalStatus.put(GlobalConstants.APPROVAL_STAT_3, "审批未通过");
		}
		String result = "";
		if(codeMapApprovalStatus.get(paramValue)!= null && !codeMapApprovalStatus.get(paramValue).equals("")){
			result = codeMapApprovalStatus.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 获取个人证件，机构证件的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapIdentType.*")
	public String getCodeMapIdentType(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = GlobalConstants.CODE_STR_ORGIDENT_TYPE + ","
				+ GlobalConstants.CODE_STR_PERSONIDENT_TYPE;
		//
		if(codeMapIdentType == null || codeMapIdentType.isEmpty()){
			codeMapIdentType = Maps.newHashMap();
			codeMapIdentType = this.codeUtil.getCodeMap(flag);
		}
		String result = "";
		if(codeMapIdentType.get(paramValue)!= null && !codeMapIdentType.get(paramValue).equals("")){
			result = codeMapIdentType.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 获取个人证件，机构证件的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapIdentType2.*")
	public String getCodeMapIdentType2(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = GlobalConstants.CODE_STR_ORGIDENT_TYPE + ","
				+ GlobalConstants.CODE_STR_PERSONIDENT_TYPE;
		//
		if(codeMapIdentType == null || codeMapIdentType.isEmpty()){
			codeMapIdentType = Maps.newHashMap();
			codeMapIdentType = this.codeUtil.getCodeMap(flag);
		}
		String result = "";
		if(codeMapIdentType.get(paramValue)!= null && !codeMapIdentType.get(paramValue).equals("")){
			result = codeMapIdentType.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 获取黑名单类型的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapSpecialListKind.*")
	public String getCodeMapSpecialListKind(String paramTypeNo, String paramValue) {
		//域名标识
		String flag = GlobalConstants.CODE_STR_SPECIALlIST_TYPE;
		//
		if(codeMapSpecialListKind == null || codeMapSpecialListKind.isEmpty()){
			codeMapSpecialListKind = Maps.newHashMap();
			codeMapSpecialListKind = this.specialListBS.getCodeMapSpecialListKind(flag);
		}
		String result = "";
		if(codeMapSpecialListKind.get(paramValue)!= null && !codeMapSpecialListKind.get(paramValue).equals("")){
			result = codeMapSpecialListKind.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
}
