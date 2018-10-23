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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustomerRelationBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.CustrelApprovalBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.service.HCustomerRelationBS;
import com.yuchengtech.emp.ecif.customer.cusrelo.web.vo.CusRelationLookVO;
import com.yuchengtech.emp.ecif.customer.entity.customer.Custrel;
import com.yuchengtech.emp.ecif.customer.entity.customer.CustrelApproval;
import com.yuchengtech.emp.ecif.customer.entity.customer.HCustrel;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;

/**
 * <pre>
 * Title:客户关系查看
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author wuhp wuhp@yuchengtech.com
 * @version 1.00.00
 * 
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/custrelapproval")
public class CustrelApprovalController extends BaseController {

	@Autowired
	private CustomerRelationBS customerRelationLookBS;
	
	@Autowired
	private HCustomerRelationBS hcustomerRelationLookBS;
	
	@Autowired
	private CustrelApprovalBS custrelApprovalBS;
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	@Autowired
	private ImportListBS importListBS;
	
	//一次进入不查询
	private boolean flag = false;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/customerrel/custrelapproval-index";
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
		Map<String, Object> taskMap = Maps.newHashMap();
		SearchResult<CusRelationLookVO> searchResult = custrelApprovalBS.getCustrelApproval(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), GlobalConstants.APPROVAL_STAT_1);
		
		taskMap.put("Rows", searchResult.getResult());
		taskMap.put("Total", searchResult.getTotalCount());
		
		return taskMap;
	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/new", method = RequestMethod.GET)
	public ModelAndView editNew(@PathVariable("id") String id) {
		
		return new ModelAndView("/ecif/customer/customerrel/custrelapproval-edit", "custrelApprovalId", id);
	}

	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	// POST /module/
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void create(CustrelApproval custrelApproval,String asd) {
		
		//当前审批用户
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
		
		String[] ids = asd.split(",");
		
		for(int i = 0 ; i < ids.length ; i++){
			Long id = Long.parseLong(ids[i]);
			CustrelApproval ca = this.custrelApprovalBS.getEntityById(id);
	    	ca.setApprovalStat(custrelApproval.getApprovalStat());
	    	ca.setApprovalNote(custrelApproval.getApprovalNote());
	    	ca.setApprovalTime(new Timestamp(new Date().getTime()));

	    	if(user.getUserName().equals(ca.getOperator())){
	    		ca.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
	    	}
	    	
		    //审批通过
		    if(GlobalConstants.APPROVAL_STAT_2.equals(ca.getApprovalStat())){
		    	
		    	ca.setApprovalOperator(user.getUserName());
		    	Custrel custrel = null;
		    	//insert or update
				if(GlobalConstants.OPER_STAT_INSERT.equals(ca.getOperStat()) || 
						GlobalConstants.OPER_STAT_UPDATE.equals(ca.getOperStat())){
					
					custrel = new Custrel();
					custrel.setCustRelId(ca.getCustRelId());
					custrel.setCustRelType(ca.getCustRelType());
					custrel.setCustRelDesc(ca.getCustRelDesc());
					custrel.setCustRelStat(ca.getCustRelStat());
					custrel.setSrcCustId(ca.getSrcCustId());
					custrel.setDestCustId(ca.getDestCustId());
					custrel.setRelStartDate(ca.getRelStartDate());
					custrel.setRelEndDate(ca.getRelEndDate());
					custrel.setLastUpdateSys("ecif");
//					custrel.setLastUpdateUser(ca.getOperator());
					custrel.setLastUpdateTm(ca.getOperTime());
					custrel.setTxSeqNo("");
					custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
					
					custrel = this.customerRelationLookBS.updateEntity(custrel);
				}
				//delete
				if(GlobalConstants.OPER_STAT_DELETE.equals(ca.getOperStat())){
					custrel = customerRelationLookBS.getEntityById(ca.getCustRelId());
					this.customerRelationLookBS.removeEntityById(ca.getCustRelId());
				}
				
				if(custrel!=null){
				//保存历史信息
					HCustrel h = new HCustrel();
					BeanUtils.copy(custrel, h);
					h.setHisOperSys("ecif");
					h.setHisOperType("00");
					h.setHisOperTime(new Timestamp(new Date().getTime()));
					hcustomerRelationLookBS.saveOrUpdateEntity(h);
				}
				custrel = null;
				
		    }
		    
		    //审批未通过
		    if(GlobalConstants.APPROVAL_STAT_3.equals(ca.getApprovalStat())){
		    	ca.setApprovalOperator(user.getUserName());
		    	//insert 
				if(GlobalConstants.OPER_STAT_INSERT.equals(ca.getOperStat()) ){
					ca.setApprovalStat(GlobalConstants.APPROVAL_STAT_3);
				}else{
					
					if(GlobalConstants.OPER_STAT_UPDATE.equals(ca.getOperStat()) || 
							GlobalConstants.OPER_STAT_DELETE.equals(ca.getOperStat())){
						Custrel custrel = customerRelationLookBS.getEntityById(ca.getCustRelId());
						custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
						customerRelationLookBS.updateEntity(custrel);
					}
//					if("03".equals(ca.getOperator())){
//						Custrel custrel = customerRelationLookBS.getEntityById(ca.getCustRelId());
//						custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
//						customerRelationLookBS.updateEntity(custrel);
//					}
				}
		    }
		    this.custrelApprovalBS.updateEntity(ca);
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
	 * 导出页面
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
	

	
	//跳转到上传页面
	@RequestMapping(value = "/importresult", method = RequestMethod.GET)
	public ModelAndView importResult() {
		return new ModelAndView("/ecif/customer/customerrel/custrelapproval-upload");
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
//			importListBS.importCustrelApprovalList(file.getPath(), 2);
			
			List<List<Object[]>> reportData = importListBS.importCustrelApprovalList(file.getPath(), 3);
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
		
		for(int i=0; i < reportData.size(); i++){
			List<Object[]> failList = new ArrayList<Object[]>();
			List<Object[]> temp = reportData.get(i);
			if(temp == null){
				continue;
			}
			if(i == 0){//读取第一个sheet
				count = temp.size();
				CustrelApproval custrelApproval = null;
//				int j = 0;
				
				for(Object[] o : temp){
					custrelApproval = new CustrelApproval();
					custrelApproval.setApprovalOperator(user.getUserName());
					Object[] oTemp = Arrays.copyOf(o, o.length+1);
					//关系信息标识0	源客户标识1	源客户号2	源客户名称3	目标客户标识4	目标客户号5	目标客户名称6	
					//关系类型7	关系开始时间8	关系结束时间9	提交人10	提交时间11	操作状态12	
					//审批人13	审批时间14	审批状态15	审批意见16
					if(o[11] != null){
						o[11] = o[11].toString() + " ";
					}
					if(o[14] != null){
						o[14] = o[14].toString() + " ";
					}
					if(o[13] == null || o[14] == null || o[15] == null){
						oTemp[oTemp.length-1] = "审批信息不全";
						failList.add(oTemp);
			    		result++;
			    		continue;
					}
					//判断导入的审批id是否存在
					if(isApproalExist(o[0].toString(),custrelApproval)){						
						//判断提交人和审批人是否是同一人
				    	if(custrelApproval.getOperator().equals(o[13].toString())){
//				    		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
				    		oTemp[oTemp.length-1] = "提交人和审批人是同一人";
				    		failList.add(oTemp);
				    		result++;
				    		continue;
				    	}
				    	
				    	//设置审批状态
				    	if("审批通过".equals(o[15].toString().trim())){
				    		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_2);
				    	}else if("审批未通过".equals(o[15].toString().trim())){
				    		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_3);
				    	}else if("待审批".equals(o[15].toString().trim())){
				    		custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_1);
				    	}else{
				    		//没填写审批状态，或者填错审批状态
				    		oTemp[oTemp.length-1] = "审批状态填写的不对";
				    		failList.add(oTemp);
				    		result++;
				    		continue;
				    	}
				    	
				    	//设置审批时间
				    	//(new Timestamp(new Date().getTime()))
				    	Timestamp tempDate = null;
		    			try {
							tempDate = ConvertUtils.getStrToTimestamp2(o[14].toString().trim());
							//sl.setApprovalTime(tempDate);
						} catch (ParseException e) {
							//date error
							oTemp[oTemp.length-1] = "审批时间类型转换错误";
							failList.add(oTemp);
							result++;
							continue;
						}
		    			try {
//			    			if(ConvertUtils.getDateStrToLong(o[14].toString()).longValue() 
//								< custrelApproval.getOperTime().getTime()){
		    				if(ConvertUtils.getStrToTimestamp2(o[14].toString().trim()).getTime() 
									< custrelApproval.getOperTime().getTime()){
								oTemp[oTemp.length-1] = "审批时间早于提交时间";
								failList.add(oTemp);
								result++;
								continue;
							}
		    			} catch (ParseException e) {
		    				oTemp[oTemp.length-1] = "审批时间与提交时间对比错误";
							failList.add(oTemp);
							result++;
							continue;
		    			}
		    			
		    			custrelApproval.setApprovalOperator(o[13].toString());
				    	custrelApproval.setApprovalTime(tempDate);
				    	custrelApproval.setApprovalNote(o[16].toString());
						
					    //审批通过
					    if(GlobalConstants.APPROVAL_STAT_2.equals(custrelApproval.getApprovalStat())){
					    	
					    	Custrel custrel = null;
					    	//insert or update
							if("01".equals(custrelApproval.getOperStat()) || "02".equals(custrelApproval.getOperStat())){
								
								custrel = new Custrel();
								custrel.setCustRelId(custrelApproval.getCustRelId());
								custrel.setCustRelType(custrelApproval.getCustRelType());
								custrel.setCustRelDesc(custrelApproval.getCustRelDesc());
								custrel.setCustRelStat(custrelApproval.getCustRelStat());
								custrel.setSrcCustId(custrelApproval.getSrcCustId());
								custrel.setDestCustId(custrelApproval.getDestCustId());
								custrel.setRelStartDate(custrelApproval.getRelStartDate());
								custrel.setRelEndDate(custrelApproval.getRelEndDate());
								custrel.setLastUpdateSys("ecif");
								custrel.setLastUpdateUser(custrelApproval.getOperator());
								custrel.setLastUpdateTm(custrelApproval.getOperTime());
								custrel.setTxSeqNo("");
								//custrel.setApprovalFlag(custrelApproval.getApprovalStat());
								custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
								
								custrel = this.customerRelationLookBS.updateEntity(custrel);
							}else if("03".equals(custrelApproval.getOperStat())){//delete
								custrel = customerRelationLookBS.getEntityById(custrelApproval.getCustRelId());
								if(custrel != null){
									this.customerRelationLookBS.removeEntityById(custrel.getCustRelId());
								}
							}
							//保存历史信息
							HCustrel h = new HCustrel();
							BeanUtils.copy(custrel, h);
							h.setHisOperSys("ecif");
							h.setHisOperType("00");
							h.setHisOperTime(new Timestamp(new Date().getTime()));
							hcustomerRelationLookBS.saveOrUpdateEntity(h);
							custrel = null;
					    }
					    
					    //审批未通过
					    if(GlobalConstants.APPROVAL_STAT_3.equals(custrelApproval.getApprovalStat())){
					    	//insert 
							if("01".equals(custrelApproval.getOperStat()) ){
								//custrelApproval.setApprovalStat(GlobalConstants.APPROVAL_STAT_3);
							}else{								
								if("02".equals(custrelApproval.getOperStat()) || "03".equals(custrelApproval.getOperStat())){
									Custrel custrel = customerRelationLookBS.getEntityById(custrelApproval.getCustRelId());
									//custrel.setApprovalFlag(GlobalConstants.APPROVAL_FLAG_0);
									if(custrel != null){
										this.customerRelationLookBS.updateApprovalFlag(custrel.getCustRelId(), GlobalConstants.APPROVAL_FLAG_0);
									}
									//customerRelationLookBS.updateEntity(custrel);
								}
							}
					    }					    
					    this.custrelApprovalBS.updateEntity(custrelApproval);						
					}else{
						//不存在
						oTemp[oTemp.length-1] = "关系信息标识不存在，请重新下载待审批信息";
						failList.add(oTemp);
						result++;
						continue;
					}
					//custrelApprovalBS.updateEntity(custrelApproval);
				}
				errData.add(failList);
			}
		}		
		String file = exportReportBS.exportCustrel(2, errData);		
		if(result == 0){
			return "成功" + count + "条记录";
		}else{
//			return "成功" + (count-result) + "条记录，错误" + result + "条";
			return "err" + file;
		}
	}
	
	/**
	 * 判断导入的审批标识是否存在
	 */
	public boolean isApproalExist(String approvalRelId,CustrelApproval custrelApproval){
		boolean flag = false;
		Long approvalRelIdLong = 0L;
		if(!"".equals(approvalRelId)){
			approvalRelIdLong= Long.parseLong(approvalRelId);
		}
		
//		StringBuffer sql = new StringBuffer(" SELECT custrelApproval FROM CUSTREL_APPROVAL custrelApproval where custrelApproval.CUSTREL_APPROVAL_ID = ?0 ");
//		CustrelApproval custrelApprovalData = this.baseDAO.findUniqueWithIndexParam(sql.toString(),approvalRelIdLong);
		CustrelApproval custrelApprovalData = custrelApprovalBS.getEntityById(approvalRelIdLong);
		
		if(custrelApprovalData != null && GlobalConstants.APPROVAL_STAT_1.equals(custrelApprovalData.getApprovalStat())){
			 custrelApproval.setCustrelApprovalId(custrelApprovalData.getCustrelApprovalId()); 
			 custrelApproval.setCustRelId(custrelApprovalData.getCustRelId()); 
			 custrelApproval.setCustRelType(custrelApprovalData.getCustRelType()); 
			 custrelApproval.setCustRelDesc(custrelApprovalData.getCustRelDesc()); 
			 custrelApproval.setCustRelStat(custrelApprovalData.getCustRelStat()); 
			 custrelApproval.setSrcCustId(custrelApprovalData.getSrcCustId()); 
			 custrelApproval.setDestCustId(custrelApprovalData.getDestCustId()); 
			 custrelApproval.setRelStartDate(custrelApprovalData.getRelStartDate()); 
			 custrelApproval.setRelEndDate(custrelApprovalData.getRelEndDate()); 
			 custrelApproval.setOperator(custrelApprovalData.getOperator()); 
			 custrelApproval.setOperTime(custrelApprovalData.getOperTime()); 
			 custrelApproval.setOperStat(custrelApprovalData.getOperStat()); 
			 flag = true;
		}
		
		return flag;
	}
	
	
	/**
	 * 复制excel中的数据
	 */
	public Object[] copyExcel(Object[] obj){
		Object[] copyObj = Arrays.copyOf(obj, 14);
		return copyObj;
	}
	
	/**
	 * 判断审批和修改是否是同一用户
	 */
	@RequestMapping("isSameUser.*")
	@ResponseBody
	public String isSameUser(@RequestParam("ids") String ids) {
		
		String str = "";
		String[] id = ids.split(",");		
		//当前审批用户
		BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();		
		boolean flag = false;		
		for(String oneId : id){
			CustrelApproval ca = this.custrelApprovalBS.getEntityById(Long.parseLong(oneId));
			if(ca.getOperator()!=null&&ca.getOperator().equals(user.getUserName())){
				str += oneId + ",";
				flag = true;
			}
		}
		if(flag){
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
}
