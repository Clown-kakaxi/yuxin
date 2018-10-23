package com.yuchengtech.bcrm.customer.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.action.MyQueryAction;
import com.yuchengtech.bcrm.customer.model.OcrmFCiTransferAppInfo;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户认领服务类
 *
 */

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerTransferAppInfoService extends CommonService {

	
	public CustomerTransferAppInfoService() {
		JPABaseDAO<OcrmFCiTransferAppInfo, Long> baseDAO = new JPABaseDAO<OcrmFCiTransferAppInfo, Long>(
				OcrmFCiTransferAppInfo.class);
		super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 批量审批处理
	 * @param idStr
	 * @return
	 */
	public String batchApprove(String idStr) {
		JPABaseDAO<OcrmFCiTransferAppInfo, Long> laiBaseDAO = new JPABaseDAO<OcrmFCiTransferAppInfo, Long>(
				this.em,OcrmFCiTransferAppInfo.class);
		JPABaseDAO<OcrmFCiBelongCustmgr, Long> bCBaseDAO = new JPABaseDAO<OcrmFCiBelongCustmgr, Long>(this.em,
				OcrmFCiBelongCustmgr.class);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//更新认领单中的状态
		String jql="update OcrmFCiTransferAppInfo set approvelStatus='2' where claimtagNo in ("+idStr+")";
		Map<String,Object> values=new HashMap<String,Object>();
		this.setBaseDAO(laiBaseDAO);
		this.batchUpdateByName(jql, values);
		//根据认领单编号查找客户编号和认领人
		for (String id : idStr.split(",")) {
			this.setBaseDAO(laiBaseDAO);
			OcrmFCiTransferAppInfo olai = (OcrmFCiTransferAppInfo)this.find(Long.parseLong(id));
			OcrmFCiBelongCustmgr obc = new OcrmFCiBelongCustmgr();
			obc.setCustId(olai.getCustId());
			obc.setMainType("0");							//协办类型,主办是1，协办是0
//			obc.setMgrId(olai.getApplyUser());				//归属客户经理编号
			obc.setAssignDate(new Date());
			obc.setAssignUser(auth.getUserId());			//分配人
			obc.setAssignUsername(auth.getCname());			//分配人名称
			obc.setCheckRight("1");							//1：是，0：否
			obc.setInstitution(auth.getUnitId());			//机构编号
			obc.setInstitutionName(auth.getUnitName());		//机构名称
			obc.setMaintainRight("1");						//1：是，0：否
			this.setBaseDAO(bCBaseDAO);
			this.save(obc);
		}
		return "success";
	}
	
	/**
	 * 审批处理
	 * @param idStr
	 * @return
	 */
	//申请表主键、客户编号、原客户经理ID、申请人ID、原机构号、调整后机构号,申请人名称，归属客户经理表主键，归属机构主键
	public String approve(String idStr0,String idStr,String idStr1,String idStr2,String idStr3,String idStr4,String idStr4_4,String idStr5,String idStr6,String idStr7,String idStr8) {
		JPABaseDAO<OcrmFCiTransferAppInfo, Long> laiBaseDAO = new JPABaseDAO<OcrmFCiTransferAppInfo, Long>(
				this.em,OcrmFCiTransferAppInfo.class);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//更新认领单中的状态
		OcrmFCiTransferAppInfo olai = em.find(OcrmFCiTransferAppInfo.class, Long.parseLong(idStr0));//客户经理调整日志表
		MyQueryAction mq = new MyQueryAction();
		Date date = new Date();
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String custOrgSign = mq.queryCustomerBelongInfo(olai.getCustId(),olai.getAfterOrgId());//判定客户转移申请是否跨机构
		if("1".equals(olai.getAppStatus().toString())){
			String jql="update OcrmFCiTransferAppInfo set appStatus='2',refuseReason='"+idStr8+"',appUserId1='"+auth.getUserId()+"',appDate1 = :appDate1,appUserName1 = '"+auth.getUsername()+"',appOrgId1 = '"+auth.getUnitId()+"' where id='"+idStr0+"'";
			Map<String,Object> values=new HashMap<String,Object>();
			values.put("appDate1", date);
			this.setBaseDAO(laiBaseDAO);
			this.batchUpdateByName(jql, values);
			this.setBaseDAO(laiBaseDAO);
			return "success";	
		}else{
			if("2".equals(olai.getAppStatus().toString())){
				if("true".equals(custOrgSign)){//判断是否跨分行，是
					String jql="update OcrmFCiTransferAppInfo set appStatus='3',refuseReason='"+idStr8+"',appUserId2='"+auth.getUserId()+"',appDate2 = :appDate2,appUserName2 = '"+auth.getUsername()+"',appOrgId2 = '"+auth.getUnitId()+"' where id='"+idStr0+"'";
					Map<String,Object> values=new HashMap<String,Object>();
					this.setBaseDAO(laiBaseDAO);
					values.put("appDate2", date);
					this.batchUpdateByName(jql, values);
					//根据认领单编号查找客户编号和认领人
					this.setBaseDAO(laiBaseDAO);
					return "success";	
				}else{//判断是否跨分行，否
					String jql="update OcrmFCiTransferAppInfo set appStatus='4',refuseReason='"+idStr8+"',appUserId3='"+auth.getUserId()+"',appDate3 = :appDate3,appUserName3 = '"+auth.getUsername()+"',appOrgId3 = '"+auth.getUnitId()+"' where id='"+idStr0+"'";
					Map<String,Object> values=new HashMap<String,Object>();
					values.put("appDate3", date);
					this.setBaseDAO(laiBaseDAO);
					this.batchUpdateByName(jql, values);
					//根据认领单编号查找客户编号和认领人
					this.setBaseDAO(laiBaseDAO);
					//更新归属客户经理表中客户的归属关系
					OcrmFCiBelongCustmgr ws2 = em.find(OcrmFCiBelongCustmgr.class, Long.parseLong(idStr6));
					ws2.setAssignDate(new Date());
					ws2.setAssignUser(auth.getUserId());
					ws2.setAssignUsername(auth.getUsername());
					ws2.setMgrId(idStr2);
					ws2.setMgrName(idStr5);
					ws2.setInstitution(idStr4);
					
					//更新归属机构中的客户归属关系
					OcrmFCiBelongOrg cfbo = em.find(OcrmFCiBelongOrg.class, Long.parseLong(idStr7));//归属机构
					cfbo.setAssignDate(new Date());
					cfbo.setAssignUser(auth.getUserId());
					cfbo.setAssignUsername(auth.getUsername());
					cfbo.setInstitutionCode(idStr4);
					cfbo.setInstitutionName(idStr4_4);
					
					this.save(ws2);//更新归属客户经理表信息
					this.save(cfbo);//更新归属机构表信息
					return "success";	
				}
			}else{
		String jql="update OcrmFCiTransferAppInfo set appStatus='4',refuseReason='"+idStr8+"',appUserId3='"+auth.getUserId()+"',appDate3 = :appDate3,appUserName3 = '"+auth.getUsername()+"',appOrgId3 = '"+auth.getUnitId()+"'  where id='"+idStr0+"'";
		Map<String,Object> values=new HashMap<String,Object>();
		values.put("appDate3", date);
		this.setBaseDAO(laiBaseDAO);
		this.batchUpdateByName(jql, values);
		//根据认领单编号查找客户编号和认领人
		this.setBaseDAO(laiBaseDAO);
		OcrmFCiBelongCustmgr ws2 = em.find(OcrmFCiBelongCustmgr.class, Long.parseLong(idStr6));
		ws2.setCustId(idStr0);
		ws2.setAssignDate(new Date());
		ws2.setAssignUser(auth.getUserId());
		ws2.setAssignUsername(auth.getUsername());
		ws2.setMgrId(idStr2);
		ws2.setMgrName(idStr5);
		ws2.setInstitution(idStr4);
		this.save(ws2);
		return "success";
			}
		}
	}
	
	 //实现用户批量申请的提交
	@SuppressWarnings("unchecked")
	public void saveApplyLogInfo(OcrmFCiTransferAppInfo ws){
		if(ws.getId()==null){
			em.persist(ws);
		}else em.merge(ws);
		System.out.println("更新");
	}
	 //实现客户转移日志写入
	@SuppressWarnings("unchecked")
	public void saveTransferLogInfo(String str,String str1){
		OcrmFCiTransferAppInfo ws2 = em.find(OcrmFCiTransferAppInfo.class,Long.parseLong(str));
		ws2.setRefuseReason(str1);
		ws2.setAppStatus("5");
		em.merge(ws2);
//		System.out.println("更新");
	}
	
}
