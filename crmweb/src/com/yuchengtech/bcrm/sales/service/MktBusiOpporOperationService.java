package com.yuchengtech.bcrm.sales.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOppor;
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOpporHisS;
import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bcrm.system.model.AdminAuthOrg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：营销管理->商机管理->商机池功能操作Service
 * @author wzy
 * @date:2013-02-22
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class MktBusiOpporOperationService  extends CommonService{

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public OcrmFMmMktBusiOppor find(long id) {
		return em.find(OcrmFMmMktBusiOppor.class, id);
	}

	// 保存(新增)商机记录
	@SuppressWarnings("unchecked")
	public void saveBusiOppor(OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		OcrmFMmMktBusiOppor ofmbo_h = null;
		String[] prod_id_arr = null;
		String[] prod_name_arr = null;
		String prod_id = ofmbo.getProdId();
		String prod_name = ofmbo.getProdName();
		if (prod_id != null && !"".equals(prod_id)) {
			prod_id_arr = prod_id.split(",");
		}
		if (prod_name != null && !"".equals(prod_name)) {
			prod_name_arr = prod_name.split(",");
		}
		if (prod_id_arr != null && prod_id_arr.length > 0) {
			for (int i = 0; i < prod_id_arr.length; i++) {// 如果选择了多个产品，循环产生商机（一个产品对应一个商机）
				ofmbo_h = new OcrmFMmMktBusiOppor();
				BeanUtils.copyProperties(ofmbo, ofmbo_h);
				// 设置部分特殊字段值
				ofmbo_h.setProdId(prod_id_arr[i]);// 产品ID
				ofmbo_h.setProdName(prod_name_arr[i]);// 产品名称
				ofmbo_h.setOpporStat("0");// 状态（0-暂存）
				ofmbo_h.setOpporSource(ofmbo.getOpporSource());// 商机来源（0-手动创建）
				ofmbo_h.setCreaterId(auth.getUserId());// 创建人ID
				ofmbo_h.setCreaterName(auth.getUsername());// 创建人名称
				ofmbo_h.setCreateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 创建人所在机构ID
				ofmbo_h.setCreateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 创建人所在机构名称
				ofmbo_h.setCreateDateTime(new Timestamp(System
						.currentTimeMillis()));// 创建时间
				ofmbo_h.setUpdateUserId(auth.getUserId());// 更新人ID
				ofmbo_h.setUpdateUserName(auth.getUsername());// 更新人名称
				ofmbo_h.setUpdateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 更新人所在机构ID
				ofmbo_h.setUpdateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 更新人所在机构名称
				ofmbo_h.setUpdateDateTime(new Timestamp(System
						.currentTimeMillis()));// 更新时间
				// 保存商机数据
				em.persist(ofmbo_h);
			}
		}
	}
/**
 * 客户群部分的新增商机
 * @param ofmbo
 * @param auth
 * @param listall
 */
	public void saveBusiOppor(OcrmFMmMktBusiOppor ofmbo, AuthUser auth,List<Object[]>  listall) {
		String custId = "";
		String custName = "";
		String custTyp = "";
		String custStat = "";
		String custLink = "";
		String custOrgId="";
		String custOrgName="";
		String custMgrId="";
		String custMgrName="";
		
		OcrmFMmMktBusiOppor ofmbo_h = null;
		String[] prod_id_arr = null;
		String[] prod_name_arr = null;
		String prod_id = ofmbo.getProdId();
		String prod_name = ofmbo.getProdName();
		if (prod_id != null && !"".equals(prod_id)) {
			prod_id_arr = prod_id.split(",");
		}
		if (prod_name != null && !"".equals(prod_name)) {
			prod_name_arr = prod_name.split(",");
		}
		
		if(listall != null && listall.size()>0){
			for(int j=0;j<listall.size();j++){
				Object[] o1 = listall.get(j);
				custId = (o1[0]==null)?"":o1[0].toString();
				custName = (o1[2]==null)?"":o1[2].toString();
				custTyp = (o1[1]==null)?"":o1[1].toString();
				custStat = (o1[3]==null)?"":o1[3].toString();
				custLink = (o1[4]==null)?"":o1[4].toString();
				custOrgId = (o1[5]==null)?"":o1[5].toString();
				custOrgName = (o1[6]==null)?"":o1[6].toString();
				custMgrId = (o1[7]==null)?"":o1[7].toString();
				custMgrName = (o1[8]==null)?"":o1[8].toString();
				if (prod_id_arr != null && prod_id_arr.length > 0) {
					for (int i = 0; i < prod_id_arr.length; i++) {// 如果选择了多个产品，循环产生商机（一个产品对应一个商机）
						ofmbo_h = new OcrmFMmMktBusiOppor();
						BeanUtils.copyProperties(ofmbo, ofmbo_h);
						// 设置部分特殊字段值
						ofmbo_h.setCustId(custId);
						ofmbo_h.setCustName(custName);
						ofmbo_h.setCustType(custTyp);
						ofmbo_h.setCustCategory(custStat);
						ofmbo_h.setCustContactName(custLink);
						ofmbo_h.setProdId(prod_id_arr[i]);// 产品ID
						ofmbo_h.setProdName(prod_name_arr[i]);// 产品名称
						ofmbo_h.setOpporStat("0");// 状态（0-暂存）
						ofmbo_h.setOpporSource(ofmbo.getOpporSource());// 商机来源（0-手动创建）
						ofmbo_h.setExecuteOrgId(custOrgId);
						ofmbo_h.setExecuteOrgName(custOrgName);
						ofmbo_h.setExecuteUserId(custMgrId);
						ofmbo_h.setExecuteUserName(custMgrName);
						ofmbo_h.setCreaterId(auth.getUserId());// 创建人ID
						ofmbo_h.setCreaterName(auth.getUsername());// 创建人名称
						ofmbo_h.setCreateOrgId(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("ID"));// 创建人所在机构ID
						ofmbo_h.setCreateOrgName(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("UNITNAME"));// 创建人所在机构名称
						ofmbo_h.setCreateDateTime(new Timestamp(System
								.currentTimeMillis()));// 创建时间
						ofmbo_h.setUpdateUserId(auth.getUserId());// 更新人ID
						ofmbo_h.setUpdateUserName(auth.getUsername());// 更新人名称
						ofmbo_h.setUpdateOrgId(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("ID"));// 更新人所在机构ID
						ofmbo_h.setUpdateOrgName(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("UNITNAME"));// 更新人所在机构名称
						ofmbo_h.setUpdateDateTime(new Timestamp(System
								.currentTimeMillis()));// 更新时间
						// 保存商机数据
						em.persist(ofmbo_h);
					}
				}	
				
		}
		}
	}
	
	// 更新(修改)商机记录
	@SuppressWarnings("unchecked")
	public void updateBusiOppor(OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		OcrmFMmMktBusiOppor ofmbo_h = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			ofmbo_h = em.find(OcrmFMmMktBusiOppor.class, ofmbo.getOpporId());
			if (ofmbo_h != null) {
				// 设置要更新的字段值
				ofmbo_h.setOpporName(ofmbo.getOpporName());// 商机名称
				ofmbo_h.setOpporType(ofmbo.getOpporType());// 商机类型
				ofmbo_h.setOpporDueDate(ofmbo.getOpporDueDate());// 商机有效期
				ofmbo_h.setOpporStage(ofmbo.getOpporStage());// 商机阶段
				ofmbo_h.setOpporStartDate(ofmbo.getOpporStartDate());// 商机开始日期
				ofmbo_h.setOpporEndDate(ofmbo.getOpporEndDate());// 商机结束日期
				ofmbo_h.setMktActivId(ofmbo.getMktActivId());// 营销活动ID
				ofmbo_h.setMktActivName(ofmbo.getMktActivName());// 营销活动名称
				ofmbo_h.setMktTargetId(ofmbo.getMktTargetId());// 营销任务指标ID
				ofmbo_h.setMktTargetName(ofmbo.getMktTargetName());// 营销任务指标名称
				ofmbo_h.setProdId(ofmbo.getProdId());// 商机产品ID
				ofmbo_h.setProdName(ofmbo.getProdName());// 商机产品名称
				ofmbo_h.setCustId(ofmbo.getCustId());// 客户ID
				ofmbo_h.setCustName(ofmbo.getCustName());// 客户名称
				ofmbo_h.setCustCategory(ofmbo.getCustCategory());// 客户类型
				ofmbo_h.setCustType(ofmbo.getCustType());// 客户状态
				ofmbo_h.setCustContactName(ofmbo.getCustContactName());// 客户联系人
				ofmbo_h.setPlanAmount(ofmbo.getPlanAmount());// 预计金额
				ofmbo_h.setPlanCost(ofmbo.getPlanCost());// 费用预算
				ofmbo_h.setOpporContent(ofmbo.getOpporContent());// 商机内容
				ofmbo_h.setMemo(ofmbo.getMemo());// 商机备注
				// 设置部分特殊字段值
				ofmbo_h.setUpdateUserId(auth.getUserId());// 更新人ID
				ofmbo_h.setUpdateUserName(auth.getUsername());// 更新人名称
				ofmbo_h.setUpdateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 更新人所在机构ID
				ofmbo_h.setUpdateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 更新人所在机构名称
				ofmbo_h.setUpdateDateTime(new Timestamp(System
						.currentTimeMillis()));// 更新时间
				// 更新商机数据
				em.merge(ofmbo_h);

				// 新增商机操作历史记录
				ofmboh = new OcrmFMmMktBusiOpporHisS();
				ofmboh.setOpporId(ofmbo_h.getOpporId());
				ofmboh.setOprDateTime(new Timestamp(System.currentTimeMillis()));
				ofmboh.setOprOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));
				ofmboh.setOprOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));
				ofmboh.setOprUserId(auth.getUserId());
				ofmboh.setOprUserName(auth.getUsername());
				ofmboh.setOprContent("“" + auth.getUsername() + "”更新商机。");
				this.saveBusiOpporOperationHis(ofmboh);
			}
		}
	}

	// 提交商机信息
	public void submitBusiOppor(OcrmFMmMktBusiOppor ofmbo, AuthUser auth,
			HttpServletResponse response) {
		String alertMsg = "";
		OcrmFMmMktBusiOppor ofmmbo_h = null;
		if (ofmbo != null) {
			if (ofmbo.getOpporId() == null || "".equals(ofmbo.getOpporId())) {
				// 1、在页面填写了商机信息，没保存到数据库，就直接提交的情况
				this.addAndSubmit(ofmbo, auth);
				ofmmbo_h = ofmbo;
			} else {
				// 2、在页面填写了商机信息，并已保存到数据库，然后提交的情况
				ofmmbo_h = this.updateAndSubmit(ofmbo, auth);
			}
			// 设置返回信息
			if (ofmmbo_h.getExecuteUserName() != null
					&& !"".equals(ofmmbo_h.getExecuteUserName())) {
				alertMsg = "0" + ofmmbo_h.getExecuteUserName();
			} else if (ofmmbo_h.getAssignOrgName() != null
					&& !"".equals(ofmmbo_h.getAssignOrgName())) {
				alertMsg = "1" + ofmmbo_h.getAssignOrgName();
			}
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(alertMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 新增商机并且提交操作
	@SuppressWarnings("unchecked")
	private void addAndSubmit(OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		AdminAuthOrg aao = null;
		OcrmFCiBelongOrg ofcbo = null;
		OcrmFCiBelongCustmgr ofcbc = null;
		OcrmFMmMktBusiOppor ofmbo_h = null;
		String[] prod_id_arr = null;
		String[] prod_name_arr = null;
		String prod_id = ofmbo.getProdId();
		String prod_name = ofmbo.getProdName();
		if (prod_id != null && !"".equals(prod_id)) {
			prod_id_arr = prod_id.split(",");
		}
		if (prod_name != null && !"".equals(prod_name)) {
			prod_name_arr = prod_name.split(",");
		}
		if (prod_id_arr != null && prod_id_arr.length > 0) {
			ofcbc = this.getBelongCustmgr(ofmbo);
			ofcbo = this.getBelongOrg(ofmbo);
			aao = this.getTopOrg();
			for (int i = 0; i < prod_id_arr.length; i++) {// 如果选择了多个产品，循环产生商机（一个产品对应一个商机）
				ofmbo_h = new OcrmFMmMktBusiOppor();
				BeanUtils.copyProperties(ofmbo, ofmbo_h);
				if (ofcbc != null) {
					String vm_exe=ofmbo.getExecuteUserId().substring(0,2);//执行客户经理是否为虚拟客户经理
					String vm_ass=ofcbc.getMgrId().substring(0,2);
					if(!"VM".equals(vm_exe) && !"VM".equals(vm_ass)){// 1、如果客户有归属客户经理，并且客户经理不是虚拟客户经理，将商机状态置成“4-执行中”，执行人就是对应的客户经理，执行机构就是对应客户经理所在机构
						ofmbo_h.setOpporStat("4");// 商机状态置成“4-执行中”
						ofmbo_h.setExecuteUserId(ofcbc.getMgrId());// 执行人ID置成“归属客户经理ID”
						ofmbo_h.setExecuteUserName(ofcbc.getMgrName());// 执行人名称置成“归属客户经理名称”
						ofmbo_h.setExecuteOrgId(ofcbc.getInstitution());// 执行机构ID置成“归属客户经理所属机构ID”
						ofmbo_h.setExecuteOrgName(ofcbc.getInstitutionName());// 执行机构名称置成“归属客户经理所属机构名称”
					}else if("VM".equals(vm_exe)){//如果执行客户经理是虚拟客户经理,将商机状态置成'1-待分配',将待分配机构置为客户的归属机构
						ofmbo_h.setOpporStat("1");
						ofmbo_h.setAssignOgrId(ofmbo.getExecuteOrgId());
						ofmbo_h.setAssignOrgName(ofmbo.getExecuteOrgName());
					}else if("VM".equals(vm_ass) && !"VM".equals(vm_exe)){//如果执行客户经理不是虚拟客户经理，并且客户是挂在虚拟客户经理名下，将商机状态设为执行中，并将该客户归属于执行客户经理
						ofmbo_h.setExecuteOrgId(ofmbo.getExecuteOrgId());
						ofmbo_h.setExecuteOrgName(ofmbo.getExecuteOrgName());
						ofmbo_h.setExecuteUserId(ofmbo.getExecuteUserId());
						ofmbo_h.setExecuteUserName(ofmbo.getExecuteUserName());
						ofmbo_h.setOpporStat("4");
						this.setBelongMgr(ofmbo_h,ofcbc);
						
					}
				}	
					
//				} else if (ofcbo != null) {
//					// 2、如果客户没有归属客户经理，但有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成客户对应的归属机构
//					ofmbo_h.setOpporStat("1");// 商机状态置成“1-待分配”
//					ofmbo_h.setAssignOgrId(ofcbo.getInstitutionCode());// 待分配机构ID置成“归属机构代码”
//					ofmbo_h.setAssignOrgName(ofcbo.getInstitutionName());// 待分配机构名称置成“归属机构名称”
//				}
//				else {
//					// 3、如果客户既没有归属客户经理，也没有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成总行
//					ofmbo_h.setOpporStat("1");// 商机状态置成“1-待分配”
//					ofmbo_h.setAssignOgrId(aao.getOrgId());// 待分配机构ID置成“总行机构代码”
//					ofmbo_h.setAssignOrgName(aao.getOrgName());// 待分配机构名称置成“总行机构名称”
//				}
				ofmbo_h.setProdId(prod_id_arr[i]);// 产品ID
				ofmbo_h.setProdName(prod_name_arr[i]);// 产品名称
				ofmbo_h.setOpporSource(ofmbo.getOpporSource());// 商机来源（0-手动创建）
				ofmbo_h.setCreaterId(auth.getUserId());// 创建人ID
				ofmbo_h.setCreaterName(auth.getUsername());// 创建人名称
				ofmbo_h.setCreateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 创建人所在机构ID
				ofmbo_h.setCreateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 创建人所在机构名称
				ofmbo_h.setCreateDateTime(new Timestamp(System
						.currentTimeMillis()));// 创建时间
				ofmbo_h.setUpdateUserId(auth.getUserId());// 更新人ID
				ofmbo_h.setUpdateUserName(auth.getUsername());// 更新人名称
				ofmbo_h.setUpdateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 更新人所在机构ID
				ofmbo_h.setUpdateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 更新人所在机构名称
				ofmbo_h.setUpdateDateTime(new Timestamp(System
						.currentTimeMillis()));// 更新时间
				// 保存商机数据
				em.persist(ofmbo_h);
			}
		}
	}

	// 修改商机并且提交操作
	@SuppressWarnings("unchecked")
	private OcrmFMmMktBusiOppor updateAndSubmit(OcrmFMmMktBusiOppor ofmbo,
			AuthUser auth) {
		AdminAuthOrg aao = null;
		OcrmFCiBelongOrg ofcbo = null;
		OcrmFCiBelongCustmgr ofcbc = null;
		OcrmFMmMktBusiOppor ofmbo_h = null;
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			ofmbo_h = em.find(OcrmFMmMktBusiOppor.class, ofmbo.getOpporId());
			if (ofmbo_h != null) {
				ofcbc = this.getBelongCustmgr(ofmbo);
				ofcbo = this.getBelongOrg(ofmbo);
				aao = this.getTopOrg();
				if (ofcbc != null) {
					String vm_exe=ofmbo.getExecuteUserId().substring(0,2);//执行客户经理是否为虚拟客户经理
					String vm_ass=ofcbc.getMgrId().substring(0,2);
					if(!"VM".equals(vm_exe) && !"VM".equals(vm_ass)){// 1、如果客户有归属客户经理，并且客户经理不是虚拟客户经理，将商机状态置成“4-执行中”，执行人就是对应的客户经理，执行机构就是对应客户经理所在机构
						ofmbo_h.setOpporStat("4");// 商机状态置成“4-执行中”
						ofmbo_h.setExecuteUserId(ofcbc.getMgrId());// 执行人ID置成“归属客户经理ID”
						ofmbo_h.setExecuteUserName(ofcbc.getMgrName());// 执行人名称置成“归属客户经理名称”
						ofmbo_h.setExecuteOrgId(ofcbc.getInstitution());// 执行机构ID置成“归属客户经理所属机构ID”
						ofmbo_h.setExecuteOrgName(ofcbc.getInstitutionName());// 执行机构名称置成“归属客户经理所属机构名称”
					}else if("VM".equals(vm_exe)){//如果执行客户经理是虚拟客户经理,将商机状态置成'1-待分配',将待分配机构置为客户的归属机构
						ofmbo_h.setOpporStat("1");
						ofmbo_h.setAssignOgrId(ofmbo.getExecuteOrgId());
						ofmbo_h.setAssignOrgName(ofmbo.getExecuteOrgName());
					}else if("VM".equals(vm_ass) && !"VM".equals(vm_exe)){//如果执行客户经理不是虚拟客户经理，并且客户是挂在虚拟客户经理名下，将商机状态设为执行中，并将该客户归属于执行客户经理
						ofmbo_h.setExecuteOrgId(ofmbo.getExecuteOrgId());
						ofmbo_h.setExecuteOrgName(ofmbo.getExecuteOrgName());
						ofmbo_h.setExecuteUserId(ofmbo.getExecuteUserId());
						ofmbo_h.setExecuteUserName(ofmbo.getExecuteUserName());
						ofmbo_h.setOpporStat("4");
						this.setBelongMgr(ofmbo_h,ofcbc);
						
					}
				}	
				// 设置要更新的字段值
				ofmbo_h.setOpporName(ofmbo.getOpporName());// 商机名称
				ofmbo_h.setOpporType(ofmbo.getOpporType());// 商机类型
				ofmbo_h.setOpporDueDate(ofmbo.getOpporDueDate());// 商机有效期
				ofmbo_h.setOpporSource(ofmbo.getOpporSource());//商机来源
				ofmbo_h.setOpporStage(ofmbo.getOpporStage());// 商机阶段
				ofmbo_h.setOpporStartDate(ofmbo.getOpporStartDate());// 商机开始日期
				ofmbo_h.setOpporEndDate(ofmbo.getOpporEndDate());// 商机结束日期
				ofmbo_h.setMktActivId(ofmbo.getMktActivId());// 营销活动ID
				ofmbo_h.setMktActivName(ofmbo.getMktActivName());// 营销活动名称
				ofmbo_h.setMktTargetId(ofmbo.getMktTargetId());// 营销任务指标ID
				ofmbo_h.setMktTargetName(ofmbo.getMktTargetName());// 营销任务指标名称
				ofmbo_h.setProdId(ofmbo.getProdId());// 商机产品ID
				ofmbo_h.setProdName(ofmbo.getProdName());// 商机产品名称
				ofmbo_h.setCustId(ofmbo.getCustId());// 客户ID
				ofmbo_h.setCustName(ofmbo.getCustName());// 客户名称
				ofmbo_h.setCustCategory(ofmbo.getCustCategory());// 客户类型
				ofmbo_h.setCustType(ofmbo.getCustType());// 客户状态
				ofmbo_h.setCustContactName(ofmbo.getCustContactName());// 客户联系人
				ofmbo_h.setPlanAmount(ofmbo.getPlanAmount());// 预计金额
				ofmbo_h.setPlanCost(ofmbo.getPlanCost());// 费用预算
				ofmbo_h.setOpporContent(ofmbo.getOpporContent());// 商机内容
				ofmbo_h.setMemo(ofmbo.getMemo());// 商机备注
				// 设置部分特殊字段值
				ofmbo_h.setUpdateUserId(auth.getUserId());// 更新人ID
				ofmbo_h.setUpdateUserName(auth.getUsername());// 更新人名称
				ofmbo_h.setUpdateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 更新人所在机构ID
				ofmbo_h.setUpdateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 更新人所在机构名称
				ofmbo_h.setUpdateDateTime(new Timestamp(System
						.currentTimeMillis()));// 更新时间
				// 更新商机数据
				em.merge(ofmbo_h);
			}
		}
		return ofmbo_h;
	}

	// 根据客户ID，查询客户归属的主办客户经理对象
	@SuppressWarnings("rawtypes")
	private OcrmFCiBelongCustmgr getBelongCustmgr(OcrmFMmMktBusiOppor ofmbo) {
		List list = null;
		String jql = null;
		OcrmFCiBelongCustmgr ofcbc = null;
		jql = "select t from OcrmFCiBelongCustmgr t where t.custId = '"
				+ ofmbo.getCustId() + "' ";
		list = em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			ofcbc = (OcrmFCiBelongCustmgr) list.get(0);
		}
		return ofcbc;
	}

	// 根据客户ID，查询客户归属的主办机构对象
	@SuppressWarnings("rawtypes")
	private OcrmFCiBelongOrg getBelongOrg(OcrmFMmMktBusiOppor ofmbo) {
		List list = null;
		String jql = null;
		OcrmFCiBelongOrg ofcbo = null;
		jql = "select t from OcrmFCiBelongOrg t where t.custId = '"
				+ ofmbo.getCustId() + "' ";
		list = em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			ofcbo = (OcrmFCiBelongOrg) list.get(0);
		}
		return ofcbo;
	}
	
	//客户挂在虚拟客户经理名下，当商机执行人是正式客户经理时，将该客户归属于商机执行人
	private void setBelongMgr(OcrmFMmMktBusiOppor ofmbo,OcrmFCiBelongCustmgr ofcbc){
		ofcbc.setMgrId(ofmbo.getExecuteUserId());
		ofcbc.setMgrName(ofmbo.getExecuteUserName());
		em.merge(ofcbc);
	}

	// 查询总行对象
	@SuppressWarnings("rawtypes")
	private AdminAuthOrg getTopOrg() {
		List list = null;
		String jql = null;
		AdminAuthOrg aao = null;
		jql = "select t from AdminAuthOrg t where t.orgLevel = '1' or t.upOrgId is null";
		list = em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			aao = (AdminAuthOrg) list.get(0);
		}
		return aao;
	}

	// 分配商机信息
	public void allocatBusiOppor(OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			// 根据“执行机构”和“执行人”进行区分：是分配到执行机构还是分配到执行人
			if (ofmbo.getAssignOgrId() != null
					&& !"".equals(ofmbo.getAssignOgrId())) {
				// 分配到机构
				this.allocatBusiOpporOrg(ofmbo, auth);
				// 保存操作历史记录
				this.addOpHis(ofmbo, auth, 0);
			} else if (ofmbo.getExecuteUserId() != null
					&& !"".equals(ofmbo.getExecuteUserId())) {
				// 分配到执行人
				this.allocatBusiOpporUser(ofmbo, auth);
				// 保存操作历史记录
				this.addOpHis(ofmbo, auth, 1);
			}
		}
	}

	// 商机分配到机构
	@SuppressWarnings("unchecked")
	private void allocatBusiOpporOrg(OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			OcrmFMmMktBusiOppor ofmbo_d = null;
			ofmbo_d = em.find(OcrmFMmMktBusiOppor.class, ofmbo.getOpporId());
			if (ofmbo_d != null) {
				if ("6".equals(ofmbo_d.getOpporStat())) {// 如果是“到期退回”状态的分配，需要更新“商机有效期”
					ofmbo_d.setOpporDueDate(ofmbo.getOpporDueDate());
				}
				ofmbo_d.setUpdateUserId(auth.getUserId());// 最近更新人ID
				ofmbo_d.setUpdateUserName(auth.getUsername());// 最近更新人名称
				ofmbo_d.setUpdateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
				ofmbo_d.setUpdateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
				ofmbo_d.setUpdateDateTime(new Timestamp(System
						.currentTimeMillis()));// 最近更新时间
				ofmbo_d.setAssignOgrId(ofmbo.getAssignOgrId());// 待分配机构ID
				ofmbo_d.setAssignOrgName(ofmbo.getAssignOrgName());// 待分配机构
				em.merge(ofmbo_d);
			}
		}
	}

	// 商机分配到客户经理
	@SuppressWarnings("unchecked")
	private void allocatBusiOpporUser(OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			OcrmFMmMktBusiOppor ofmbo_d = null;
			AdminAuthAccount aaa = null;
			AdminAuthOrg aao = null;
			ofmbo_d = em.find(OcrmFMmMktBusiOppor.class, ofmbo.getOpporId());
			if (ofmbo_d != null) {
				ofmbo_d.setOpporStat("4");// 商机状态变成“执行中(4)”
				if ("6".equals(ofmbo_d.getOpporStat())) {// 如果是“到期退回”状态的分配，需要更新“商机有效期”
					ofmbo_d.setOpporDueDate(ofmbo.getOpporDueDate());
				}
				ofmbo_d.setUpdateUserId(auth.getUserId());// 最近更新人ID
				ofmbo_d.setUpdateUserName(auth.getUsername());// 最近更新人名称
				ofmbo_d.setUpdateOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
				ofmbo_d.setUpdateOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
				ofmbo_d.setUpdateDateTime(new Timestamp(System
						.currentTimeMillis()));// 最近更新时间
				ofmbo_d.setExecuteUserId(ofmbo.getExecuteUserId());// 执行人ID
				ofmbo_d.setExecuteUserName(ofmbo.getExecuteUserName());// 执行人名称
				aaa = this.getUserOrg(ofmbo.getExecuteUserId());
				ofmbo_d.setExecuteOrgId(aaa == null ? "" : aaa.getOrgId());// 执行人所在机构ID
				aao = this.getOrg(aaa.getOrgId());
				ofmbo_d.setExecuteOrgName(aao == null ? "" : aao.getOrgName());// 执行人所在机构名称
				em.merge(ofmbo_d);
			}
		}
	}

	// 根据用户登录账号，查询对应的用户对象
	@SuppressWarnings("rawtypes")
	private AdminAuthAccount getUserOrg(String userAccout) {
		AdminAuthAccount aaa = null;
		String jql = null;
		List list = null;
		jql = "select t from AdminAuthAccount t where t.accountName = '"
				+ userAccout + "'";
		list = em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			aaa = (AdminAuthAccount) list.get(0);
		}
		return aaa;
	}

	// 根据机构ID，查询对应的机构对象
	@SuppressWarnings("rawtypes")
	private AdminAuthOrg getOrg(String orgId) {
		AdminAuthOrg aao = null;
		String jql = null;
		List list = null;
		jql = "select t from AdminAuthOrg t where t.orgId = '" + orgId + "'";
		list = em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			aao = (AdminAuthOrg) list.get(0);
		}
		return aao;
	}

	// 分配商机操作时，新增商机操作历史记录
	@SuppressWarnings("unchecked")
	private void addOpHis(OcrmFMmMktBusiOppor ofmbo, AuthUser auth, int flag) {
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			ofmboh = new OcrmFMmMktBusiOpporHisS();
			ofmboh.setOpporId(ofmbo.getOpporId());// 商机ID
			// 操作内容
			if (flag == 0) {// 分配到机构
				ofmboh.setOprContent("商机由“" + auth.getUsername() + "”分配给“"
						+ ofmbo.getAssignOrgName() + "（"
						+ ofmbo.getAssignOgrId() + "）”。");
			} else if (flag == 1) {// 分配到客户经理
				ofmboh.setOprContent("商机由“" + auth.getUsername() + "”分配给“"
						+ "“" + ofmbo.getExecuteUserName() + "（"
						+ ofmbo.getExecuteUserId() + "）”。");
			}
			ofmboh.setOprDateTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ofmboh.setOprOrgId(((HashMap<String, String>) (auth
					.getPathOrgList().get(0))).get("ID"));// 操作机构ID
			ofmboh.setOprOrgName(((HashMap<String, String>) (auth
					.getPathOrgList().get(0))).get("UNITNAME"));// 操作机构名称
			ofmboh.setOprUserId(auth.getUserId());// 操作人ID
			ofmboh.setOprUserName(auth.getUsername());// 操作人名称
			this.saveBusiOpporOperationHis(ofmboh);
		}
	}

	// 退回商机
	// 由于现在数据结构不支持层层回退，所以，执行回退操作时，
	// 直接根据客户归属情况，进行重新分配，规则和提交商机一致
	// 并且清空“执行人”、“执行机构”、“待分配机构”、“认领人”、“认领人机构”
	@SuppressWarnings("unchecked")
	public int backBusiOppor(OcrmFMmMktBusiOppor ofmbo,String reason, AuthUser auth) {
		int result = 0;
		AdminAuthOrg aao = null;
		String[] oppor_id_arr = null;
		OcrmFCiBelongOrg ofcbo = null;
		OcrmFMmMktBusiOppor ofmbo_d = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (ofmbo != null && ofmbo.getOpporId() != null) {
			oppor_id_arr = ofmbo.getOpporId().split(",");
			if (oppor_id_arr != null && oppor_id_arr.length > 0) {
				for (int i = 0; i < oppor_id_arr.length; i++) {
					ofmbo_d = em.find(OcrmFMmMktBusiOppor.class,
							oppor_id_arr[i]);
					// 判断“执行人”是否是当前用户
					if (ofmbo_d.getExecuteUserId() != null
							&& !ofmbo_d.getExecuteUserId().equals(
									auth.getUserId())) {
						result = 1;
					}
					if (result != 0) {
						return result;
					}
					// 判断“待分配机构”是否是当前用户所在机构
					if (ofmbo_d.getAssignOgrId() != null
							&& !ofmbo_d
									.getAssignOgrId()
									.equals(((HashMap<String, String>) auth
											.getPathOrgList().get(0)).get("ID"))) {
						result = 1;
					}
					if (result != 0) {
						return result;
					}
					if (ofmbo_d != null) {
						ofcbo = this.getBelongOrg(ofmbo_d);
						aao = this.getTopOrg();
						// 执行回退操作
						ofmbo_d.setExecuteUserId("");// 清空“执行人ID”
						ofmbo_d.setExecuteUserName("");// 清空“执行人名称”
						ofmbo_d.setExecuteOrgId("");// 清空“执行机构ID”
						ofmbo_d.setExecuteOrgName("");// 清空“执行机构名称”
						ofmbo_d.setAssignOgrId("");// 清空“待分配机构ID”
						ofmbo_d.setAssignOrgName("");// 清空“待分配机构名称”
						ofmbo_d.setClaimUserId("");// 清空“认领人ID”
						ofmbo_d.setClaimUserName("");// 清空“认领人名称”
						ofmbo_d.setClaimOrgId("");// 清空“认领机构ID”
						ofmbo_d.setClaimOrgName("");// 清空“认领机构名称”
						if (ofcbo != null) {
							// 如果客户有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成客户对应的归属机构
							ofmbo_d.setOpporStat("1");// 商机状态置成“1-待分配”
							ofmbo_d.setAssignOgrId(ofcbo.getInstitutionCode());// 待分配机构ID置成“归属机构代码”
							ofmbo_d.setAssignOrgName(ofcbo.getInstitutionName());// 待分配机构名称置成“归属机构名称”
						} 
//						else {
//							// 如果客户没有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成总行
//							ofmbo_d.setOpporStat("1");// 商机状态置成“1-待分配”
//							ofmbo_d.setAssignOgrId(aao.getOrgId());// 待分配机构ID置成“总行机构代码”
//							ofmbo_d.setAssignOrgName(aao.getOrgName());// 待分配机构名称置成“总行机构名称”
//						}
						ofmbo_d.setOpporStat("5");// 商机状态(退回(5))
						ofmbo_d.setUpdateUserId(auth.getUserId());// 最近更新人ID
						ofmbo_d.setUpdateUserName(auth.getUsername());// 最近更新人名称
						ofmbo_d.setUpdateOrgId(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
						ofmbo_d.setUpdateOrgName(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
						ofmbo_d.setUpdateDateTime(new Timestamp(System
								.currentTimeMillis()));// 最近更新时间
						em.merge(ofmbo_d);
						// 新增商机操作历史记录
						ofmboh = new OcrmFMmMktBusiOpporHisS();
						ofmboh.setOpporId(ofmbo_d.getOpporId());
						ofmboh.setOprDateTime(new Timestamp(System
								.currentTimeMillis()));
						ofmboh.setOprOrgId(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("ID"));
						ofmboh.setOprOrgName(((HashMap<String, String>) (auth
								.getPathOrgList().get(0))).get("UNITNAME"));
						ofmboh.setOprUserId(auth.getUserId());
						ofmboh.setOprUserName(auth.getUsername());
						ofmboh.setOprContent("“" + auth.getUsername()
								+ "”退回商机，退回原因：" + reason + "。");
						this.saveBusiOpporOperationHis(ofmboh);
					}
				}
			}
		}
		return result;
	}

	// 根据商机ID集合，删除对应的商机记录
	public String delBusiOpporByIdS(String opporIdS, AuthUser auth) {
		String result = null;
		if (opporIdS != null && !"".equals(opporIdS)) {
			String[] idArr = opporIdS.split(",");
			for (int i = 0; i < idArr.length; i++) {
				result = delBusiOpporById(idArr[i], auth);
				if (result != null && !"".equals(result)) {
					break;
				}
			}
		}
		return result;
	}

	// 根据商机ID，删除对应的商机记录
	public String delBusiOpporById(String opporId, AuthUser auth) {
		String result = null;
		String orgManagerId = null;
		OcrmFMmMktBusiOppor ofmmbo = null;
		if (opporId != null && !"".equals(opporId)) {
			ofmmbo = em.find(OcrmFMmMktBusiOppor.class, opporId);
			if (ofmmbo != null) {
				// 根据商机的创建类型判断，当前用户是否有权限删除
				if ("0".equals(ofmmbo.getOpporSource())) {
					// 1、如果是手动创建，只能由创建人和执行人删除
					if (!auth.getUserId().equals(ofmmbo.getExecuteUserId())
							&& !auth.getUserId().equals(ofmmbo.getCreaterId())) {
						result = "您没有权限删除当前选中的商机！";
						return result;
					}
				} else {
					// 2、如果非手动创建，只能由待分配机构主管删除
					orgManagerId = this.getOrgManager(ofmmbo.getAssignOgrId());
					if (!auth.getUserId().equals(orgManagerId)) {
						result = "您没有权限删除当前选中的商机！";
						return result;
					}
				}
				em.remove(ofmmbo);
			}
		}
		return result;
	}

	// 认领商机
	public void claimBusiOppor(String opporIdS, String claimType, AuthUser auth) {
		String[] id_arr = null;
		if (opporIdS != null && !"".equals(opporIdS)) {
			id_arr = opporIdS.split(",");
			if ("0".equals(claimType)) {// 客户经理认领
				this.claimUserManager(id_arr, auth);
			} else if ("1".equals(claimType)) {// 机构认领
				this.claimOrgManager(id_arr, auth);
			}
		}
	}

	// 客户经理认领商机
	@SuppressWarnings("unchecked")
	private void claimUserManager(String[] id_arr, AuthUser auth) {
		OcrmFMmMktBusiOppor ofmbo = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (id_arr != null && id_arr.length > 0) {
			for (int i = 0; i < id_arr.length; i++) {
				ofmbo = em.find(OcrmFMmMktBusiOppor.class, id_arr[i]);
				if (ofmbo != null) {
					// 更新商机信息
					ofmbo.setClaimUserId(auth.getUserId());// 认领人ID
					ofmbo.setClaimUserName(auth.getUsername());// 认领人名称
					ofmbo.setOpporStat("3");// 商机状态：待审批（3）
					ofmbo.setUpdateUserId(auth.getUserId());// 最近更新人ID
					ofmbo.setUpdateUserName(auth.getUsername());// 最近更新人名称
					ofmbo.setUpdateOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
					ofmbo.setUpdateOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
					ofmbo.setUpdateDateTime(new Timestamp(System
							.currentTimeMillis()));// 最近更新时间
					ofmbo.setAssignOgrId("");// 待分配机构ID（清空）
					ofmbo.setAssignOrgName("");// 待分配机构名称（清空）
					em.merge(ofmbo);
					// 写商机操作历史记录
					ofmboh = new OcrmFMmMktBusiOpporHisS();
					ofmboh.setOpporId(ofmbo.getOpporId());
					ofmboh.setOprDateTime(new Timestamp(System
							.currentTimeMillis()));
					ofmboh.setOprOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));
					ofmboh.setOprOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));
					ofmboh.setOprUserId(auth.getUserId());
					ofmboh.setOprUserName(auth.getUsername());
					ofmboh.setOprContent("“" + auth.getUsername()
							+ "（客户经理认领）”认领商机。");
					this.saveBusiOpporOperationHis(ofmboh);
				}
			}
		}
	}

	// 机构认领商机
	@SuppressWarnings("unchecked")
	private void claimOrgManager(String[] id_arr, AuthUser auth) {
		OcrmFMmMktBusiOppor ofmbo = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (id_arr != null && id_arr.length > 0) {
			for (int i = 0; i < id_arr.length; i++) {
				ofmbo = em.find(OcrmFMmMktBusiOppor.class, id_arr[i]);
				if (ofmbo != null) {
					// 更新商机信息
					ofmbo.setClaimOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));// 认领机构ID
					ofmbo.setClaimOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));// 认领机构名称
					ofmbo.setOpporStat("3");// 商机状态：待审批（3）
					ofmbo.setUpdateUserId(auth.getUserId());// 最近更新人ID
					ofmbo.setUpdateUserName(auth.getUsername());// 最近更新人名称
					ofmbo.setUpdateOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
					ofmbo.setUpdateOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
					ofmbo.setUpdateDateTime(new Timestamp(System
							.currentTimeMillis()));// 最近更新时间
					ofmbo.setAssignOgrId("");// 待分配机构ID（清空）
					ofmbo.setAssignOrgName("");// 待分配机构名称（清空）
					em.merge(ofmbo);
					// 写商机操作历史记录
					ofmboh = new OcrmFMmMktBusiOpporHisS();
					ofmboh.setOpporId(ofmbo.getOpporId());
					ofmboh.setOprDateTime(new Timestamp(System
							.currentTimeMillis()));
					ofmboh.setOprOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));
					ofmboh.setOprOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));
					ofmboh.setOprUserId(auth.getUserId());
					ofmboh.setOprUserName(auth.getUsername());
					ofmboh.setOprContent("“" + auth.getUsername()
							+ "（机构认领）”认领商机。");
					this.saveBusiOpporOperationHis(ofmboh);
				}
			}
		}
	}

	// 认领商机审批
	public void claimAuditBusiOppor(String opporIdS, String auditResult,
			AuthUser auth, OcrmFMmMktBusiOppor model) {
		String[] id_arr = null;
		if (opporIdS != null && !"".equals(opporIdS)) {
			id_arr = opporIdS.split(",");
			if ("0".equals(auditResult)) {// 审核通过
				this.claimAuditBusiOpporPass(id_arr, auth, model);
			} else if ("1".equals(auditResult)) {// 审核不通过
				this.claimAuditBusiOpporBack(id_arr, auth, model);
			}
		}
	}

	// 认领商机审批：同意
	@SuppressWarnings("unchecked")
	public void claimAuditBusiOpporPass(String[] id_arr, AuthUser auth,
			OcrmFMmMktBusiOppor model) {
		AdminAuthOrg aao = null;
		AdminAuthAccount aaa = null;
		OcrmFMmMktBusiOppor ofmbo = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (id_arr != null && id_arr.length > 0) {
			for (int i = 0; i < id_arr.length; i++) {
				ofmbo = em.find(OcrmFMmMktBusiOppor.class, id_arr[i]);
				if (ofmbo != null) {
					// 更新商机信息
					if (ofmbo.getClaimUserId() != null
							&& !"".equals(ofmbo.getClaimUserId())) {
						// 客户经理认领
						aaa = this.getUserByAccountName(ofmbo.getClaimUserId());
						aao = this.getOrg(aaa.getOrgId());
						ofmbo.setExecuteUserId(ofmbo.getClaimUserId());// 将“执行人ID”设置成“认领人ID”
						ofmbo.setExecuteUserName(ofmbo.getClaimUserName());// 将“执行人名称”设置成“认领人名称”
						ofmbo.setOpporStat("4");// 商机状态：执行中（4）
						ofmbo.setClaimUserId("");// 清空“认领人ID”
						ofmbo.setClaimUserName("");// 清空“认领人名称”
						ofmbo.setExecuteOrgId(aaa.getOrgId());// 执行人所在机构ID
						ofmbo.setExecuteOrgName(aao.getOrgName());// 执行人所在机构名称
					} else if (ofmbo.getClaimOrgId() != null
							&& !"".equals(ofmbo.getClaimOrgId())) {
						// 机构认领
						ofmbo.setAssignOgrId(ofmbo.getClaimOrgId());// 将“认领机构ID”设置成“待分配机构ID”
						ofmbo.setAssignOrgName(ofmbo.getClaimOrgName());// 将“认领机构名称”设置成“待分配机构名称”
						ofmbo.setOpporStat("1");// 商机状态：待分配（1）
						ofmbo.setClaimOrgId("");// 清空“待分配机构ID”
						ofmbo.setClaimOrgName("");// 清空“待分配机构名称”
					}
					ofmbo.setUpdateUserId(auth.getUserId());// 最近更新人ID
					ofmbo.setUpdateUserName(auth.getUsername());// 最近更新人名称
					ofmbo.setUpdateOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
					ofmbo.setUpdateOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
					ofmbo.setUpdateDateTime(new Timestamp(System
							.currentTimeMillis()));// 最近更新时间
					em.merge(ofmbo);
					// 写商机操作历史记录
					ofmboh = new OcrmFMmMktBusiOpporHisS();
					ofmboh.setOpporId(ofmbo.getOpporId());
					ofmboh.setOprDateTime(new Timestamp(System
							.currentTimeMillis()));
					ofmboh.setOprOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));
					ofmboh.setOprOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));
					ofmboh.setOprUserId(auth.getUserId());
					ofmboh.setOprUserName(auth.getUsername());
					ofmboh.setOprContent("“" + auth.getUsername()
							+ "”审批商机认领：同意。");
					this.saveBusiOpporOperationHis(ofmboh);
				}
			}
		}
	}

	// 认领商机审批：拒绝
	@SuppressWarnings("unchecked")
	public void claimAuditBusiOpporBack(String[] id_arr, AuthUser auth,
			OcrmFMmMktBusiOppor model) {
		AdminAuthOrg aao = null;
		OcrmFCiBelongOrg ofcbo = null;
		OcrmFMmMktBusiOppor ofmbo = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (id_arr != null && id_arr.length > 0) {
			for (int i = 0; i < id_arr.length; i++) {
				ofmbo = em.find(OcrmFMmMktBusiOppor.class, id_arr[i]);
				if (ofmbo != null) {
					ofcbo = this.getBelongOrg(ofmbo);
					aao = this.getTopOrg();
					// 更新商机信息
					ofmbo.setClaimOrgId("");// 清空认领机构ID
					ofmbo.setClaimOrgName("");// 清空认领机构名称
					ofmbo.setClaimUserId("");// 清空认领人ID
					ofmbo.setClaimUserName("");// 清空认领人名称
					if (ofcbo != null) {
						// 如果客户有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成客户对应的归属机构
						ofmbo.setOpporStat("1");// 商机状态置成“1-待分配”
						ofmbo.setAssignOgrId(ofcbo.getInstitutionCode());// 待分配机构ID置成“归属机构代码”
						ofmbo.setAssignOrgName(ofcbo.getInstitutionName());// 待分配机构名称置成“归属机构名称”
					} else {
						// 如果客户没有归属机构，将商机状态置成“1-待分配”，将待分配机构设置成总行
						ofmbo.setOpporStat("1");// 商机状态置成“1-待分配”
						ofmbo.setAssignOgrId(aao.getOrgId());// 待分配机构ID置成“总行机构代码”
						ofmbo.setAssignOrgName(aao.getOrgName());// 待分配机构名称置成“总行机构名称”
					}
					ofmbo.setUpdateUserId(auth.getUserId());// 最近更新人ID
					ofmbo.setUpdateUserName(auth.getUsername());// 最近更新人名称
					ofmbo.setUpdateOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));// 最近更新机构ID
					ofmbo.setUpdateOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));// 最近更新机构名称
					ofmbo.setUpdateDateTime(new Timestamp(System
							.currentTimeMillis()));// 最近更新时间
					em.merge(ofmbo);
					// 写商机操作历史记录
					ofmboh = new OcrmFMmMktBusiOpporHisS();
					ofmboh.setOpporId(ofmbo.getOpporId());
					ofmboh.setOprDateTime(new Timestamp(System
							.currentTimeMillis()));
					ofmboh.setOprOrgId(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("ID"));
					ofmboh.setOprOrgName(((HashMap<String, String>) (auth
							.getPathOrgList().get(0))).get("UNITNAME"));
					ofmboh.setOprUserId(auth.getUserId());
					ofmboh.setOprUserName(auth.getUsername());
					ofmboh.setOprContent("“" + auth.getUsername()
							+ "”审批商机认领：拒绝，拒绝理由：" + model.getMemo() + "。");
					this.saveBusiOpporOperationHis(ofmboh);
				}
			}
		}
	}

	// 根据ID，查询对应的商机对象
	public OcrmFMmMktBusiOppor find(String opporId) {
		return em.find(OcrmFMmMktBusiOppor.class, opporId);
	}

	// 保存商机操作历史记录
	public void saveBusiOpporOperationHis(OcrmFMmMktBusiOpporHisS ofmboh) {
		em.persist(ofmboh);
	}

	// 根据机构ID，查询对应的机构主管
	// 查询逻辑：
	// 1、在用户表中，根据机构ID，查询出该机构下的所有用户
	// 2、在用户和角色关联表中，根据用户和角色关联关系，找出角色为“主管”的用户
	// 其中，“主管角色”编码，是在代码中写死的，在不同的行实施时，需要根据具体情况进行修改
	@SuppressWarnings("rawtypes")
	public String getOrgManager(String orgId) {
		String jql = null;
		List list = null;
		StringBuffer sb = null;
		String orgManager = null;
		AdminAuthAccount aaa = null;
		if (orgId == null || "".equals(orgId)) {
			return orgManager;
		}
		// 定义机构主管角色编码(用于拼凑查询语句)，在不同的银行实施时，需要根据系统实际情况进行更改
		// zleader:总行管理人员
		// 1234:总行业务主管
		// zhbm:总行业务经理
		// fhsystem:分行系统管理员
		// fhbm:分行业务经理
		// zhhz:支行行长
		String orgManagerRoleCodeS = "('zleader','1234','zhbm','fhsystem','fhbm','zhhz')";
		sb = new StringBuffer("");
		sb.append("select a");
		sb.append("  from AdminAuthAccount a");
		sb.append(" where a.id in");
		sb.append("   (select b.accountId");
		sb.append("     from AdminAuthAccountRole b");
		sb.append("   where b.accountId in (select c.id");
		sb.append("      from AdminAuthAccount c");
		sb.append("   where c.orgId = '" + orgId + "')");
		sb.append("      and b.roleId in (select d.id");
		sb.append("      from AdminAuthRole d");
		sb.append("   where d.roleCode in " + orgManagerRoleCodeS + "))");
		jql = sb.toString();
		list = em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			aaa = (AdminAuthAccount) list.get(0);
			orgManager = aaa.getAccountName();
		}
		return orgManager;
	}

	// 判断当前用户是否能退回选中的商机
	// 判断逻辑：
	// 1、客户经理只能退回分配给自己的商机，客户只有当前一个归属客户经理时不能退回
	// 2、客户主管可以退回分配给本机构的商机，并且只能退回客户没有归属机构的商机
	public String canReturn(String userType, String opporIdS, AuthUser auth) {
		String result = "true";
		String[] id_arr = null;
		OcrmFMmMktBusiOppor ofmbo = null;
		if (opporIdS != null && !"".equals(opporIdS)) {
			id_arr = opporIdS.split(",");
			if (id_arr != null && id_arr.length > 0) {
				for (int i = 0; i < id_arr.length; i++) {
					ofmbo = em.find(OcrmFMmMktBusiOppor.class, id_arr[i]);
					if (ofmbo != null) {
						// userType，0：客户经理；1：机构主管；2：客户经理+机构主管
						if ("0".equals(userType)) {
							// 客户经理
							if (ofmbo.getExecuteUserId() != null
									&& !ofmbo.getExecuteUserId().equals(
											auth.getUserId())) {
								// 有执行人，并且不是当前用户，不能退回
								result = "false";
								return result;
							} else if (ofmbo.getExecuteUserId() != null
									&& this.isOnlyOneManager(ofmbo.getCustId())) {
								// 有执行人，并且该客户只有一个归属客户经理，不能退回
								result = "false";
								return result;
							}
						} else if ("1".equals(opporIdS)) {
							// 机构主管
							if (ofmbo.getAssignOgrId() != null
									&& !ofmbo.getAssignOgrId().equals(
											auth.getUnitId())) {
								// 有待分配机构，并且待分配机构不是当前用户所在机构，不能退回
								result = "false";
								return result;
							} else if (ofmbo.getAssignOgrId() != null
									&& ofmbo.getAssignOgrId().equals(
											auth.getUnitId())
									&& !this.isHadOrgManager(ofmbo.getCustId())) {
								// 有待分配机构，并且待分配机构是当前用户所在机构，但客户没有归属机构，不能退回
								result = "false";
								return result;
							}
						} else if ("2".equals(opporIdS)) {
							// 客户经理+机构主管
							// 客户经理
							if (ofmbo.getExecuteUserId() != null
									&& !ofmbo.getExecuteUserId().equals(
											auth.getUserId())) {
								// 有执行人，并且不是当前用户，不能退回
								result = "false";
								return result;
							} else if (ofmbo.getExecuteUserId() != null
									&& this.isOnlyOneManager(ofmbo.getCustId())) {
								// 有执行人，并且该客户只有一个归属客户经理，不能退回
								result = "false";
								return result;
							}
							// 机构主管
							if (ofmbo.getAssignOgrId() != null
									&& !ofmbo.getAssignOgrId().equals(
											auth.getUnitId())) {
								// 有待分配机构，并且待分配机构不是当前用户所在机构，不能退回
								result = "false";
								return result;
							} else if (ofmbo.getAssignOgrId() != null
									&& ofmbo.getAssignOgrId().equals(
											auth.getUnitId())
									&& !this.isHadOrgManager(ofmbo.getCustId())) {
								// 有待分配机构，并且待分配机构是当前用户所在机构，但客户没有归属机构，不能退回
								result = "false";
								return result;
							}
						} else {
							// 既不是客户经理，也不是结构主管，不能退回商机(wzy，20130422，add)
							result = "false";
							return result;
						}
					}
				}
			}
		}
		return result;
	}

	// 判断某一客户是否只有一个归属客户经理，是：返回true；否：返回false
	@SuppressWarnings("rawtypes")
	private boolean isOnlyOneManager(String custId) {
		boolean result = false;
		List list = null;
		StringBuffer sb = null;
		sb = new StringBuffer("");
		sb.append("select a");
		sb.append("  from OcrmFCiBelongCustmgr a");
		sb.append(" where a.custId = '" + custId + "'");
		list = em.createQuery(sb.toString()).getResultList();
		if (list != null && list.size() == 1) {
			result = true;
		}
		return result;
	}

	// 判断某一客户是否有归属机构，有：返回true；无：返回false
	@SuppressWarnings("rawtypes")
	private boolean isHadOrgManager(String custId) {
		boolean result = false;
		List list = null;
		StringBuffer sb = null;
		sb = new StringBuffer("");
		sb.append("select a");
		sb.append("  from OcrmFCiBelongOrg a");
		sb.append(" where a.custId = '" + custId + "'");
		list = em.createQuery(sb.toString()).getResultList();
		if (list != null && list.size() > 0) {
			result = true;
		}
		return result;
	}

	// 用户用户登录系统的账号，查询用户model对象
	private AdminAuthAccount getUserByAccountName(String accountName) {
		String jql = "";
		AdminAuthAccount aaa = null;
		try {
			if (accountName != null && !"".equals(accountName)) {
				jql = "select t from AdminAuthAccount t where t.accountName = '"
						+ accountName + "'";
				aaa = (AdminAuthAccount) em.createQuery(jql).getResultList()
						.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aaa;
	}

	// 从客户群组功能点批量创建商机(新增保存)
	public void pitchCreateBusiOpporFromCustGroup(OcrmFMmMktBusiOppor ofmbo,
			AuthUser auth) {
		String[] custIdArr = null;
		String[] custNameArr = null;
		AcrmFCiCustomer ofccd = null;
		if (ofmbo != null && ofmbo.getCustId() != null) {
			custIdArr = ofmbo.getCustId().split(",");
			custNameArr = ofmbo.getCustName().split(",");
			if (custIdArr != null && custIdArr.length > 0) {
				for (int i = 0; i < custIdArr.length; i++) {
					ofccd = this.getCustByID(custIdArr[i]);
					ofmbo.setCustId(custIdArr[i]);
					ofmbo.setCustName(custNameArr[i]);
					ofmbo.setCustType(ofccd.getCustStat());
					ofmbo.setCustCategory(ofccd.getCustType());
					this.saveBusiOppor(ofmbo, auth);
				}
			}
		}
	}

	// 从客户群组功能点批量创建商机(新增提交)
	public void pitchCreateSubmitBusiOpporFromCustGroup(
			OcrmFMmMktBusiOppor ofmbo, AuthUser auth) {
		String[] custIdArr = null;
		String[] custNameArr = null;
		AcrmFCiCustomer ofccd = null;
		if (ofmbo != null && ofmbo.getCustId() != null) {
			custIdArr = ofmbo.getCustId().split(",");
			custNameArr = ofmbo.getCustName().split(",");
			if (custIdArr != null && custIdArr.length > 0) {
				for (int i = 0; i < custIdArr.length; i++) {
					ofccd = this.getCustByID(custIdArr[i]);
					ofmbo.setCustId(custIdArr[i]);
					ofmbo.setCustName(custNameArr[i]);
					ofmbo.setCustType(ofccd.getCustStat());
					ofmbo.setCustCategory(ofccd.getCustType());
					this.addAndSubmit(ofmbo, auth);
				}
			}
		}
	}

	// 根据客户ID查询客户对象
	private AcrmFCiCustomer getCustByID(String custId) {
		return em.find(AcrmFCiCustomer.class, custId);
	}
}