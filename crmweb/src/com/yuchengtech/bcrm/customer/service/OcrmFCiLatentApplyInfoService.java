package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.OcrmFCiLatentApplyInfo;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户认领服务类
 *
 */

@Service
@Transactional(value="postgreTransactionManager")
public class OcrmFCiLatentApplyInfoService extends CommonService {

	
	public OcrmFCiLatentApplyInfoService() {
		JPABaseDAO<OcrmFCiLatentApplyInfo, Long> baseDAO = new JPABaseDAO<OcrmFCiLatentApplyInfo, Long>(
				OcrmFCiLatentApplyInfo.class);
		super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 批量审批处理
	 * @param idStr
	 * @return
	 */
	public String batchApprove(String idStr) {
		JPABaseDAO<OcrmFCiLatentApplyInfo, Long> laiBaseDAO = new JPABaseDAO<OcrmFCiLatentApplyInfo, Long>(
				this.em,OcrmFCiLatentApplyInfo.class);
		JPABaseDAO<OcrmFCiBelongCustmgr, Long> bCBaseDAO = new JPABaseDAO<OcrmFCiBelongCustmgr, Long>(this.em,
				OcrmFCiBelongCustmgr.class);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//更新认领单中的状态
		String jql="update OcrmFCiLatentApplyInfo set approvelStatus='2' where claimtagNo in ("+idStr+")";
		Map<String,Object> values=new HashMap<String,Object>();
		this.setBaseDAO(laiBaseDAO);
		this.batchUpdateByName(jql, values);
		//根据认领单编号查找客户编号和认领人
		for (String id : idStr.split(",")) {
			this.setBaseDAO(laiBaseDAO);
			OcrmFCiLatentApplyInfo olai = (OcrmFCiLatentApplyInfo)this.find(Long.parseLong(id));
			OcrmFCiBelongCustmgr obc = new OcrmFCiBelongCustmgr();
			obc.setCustId(olai.getCustId());
			obc.setMainType("0");							//协办类型,主办是1，协办是0
			obc.setMgrId(olai.getApplyUser());				//归属客户经理编号
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
	public String approve(String idStr) {
		JPABaseDAO<OcrmFCiLatentApplyInfo, Long> laiBaseDAO = new JPABaseDAO<OcrmFCiLatentApplyInfo, Long>(
				this.em,OcrmFCiLatentApplyInfo.class);
		JPABaseDAO<OcrmFCiBelongCustmgr, Long> bCBaseDAO = new JPABaseDAO<OcrmFCiBelongCustmgr, Long>(this.em,
				OcrmFCiBelongCustmgr.class);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//更新认领单中的状态
		String jql="update OcrmFCiLatentApplyInfo set approvelStatus='2' where claimtagNo='"+idStr+"'";
		Map<String,Object> values=new HashMap<String,Object>();
		this.setBaseDAO(laiBaseDAO);
		this.batchUpdateByName(jql, values);
		//根据认领单编号查找客户编号和认领人
		this.setBaseDAO(laiBaseDAO);
		OcrmFCiLatentApplyInfo olai = (OcrmFCiLatentApplyInfo)this.find(Long.parseLong(idStr));
		OcrmFCiBelongCustmgr obc = new OcrmFCiBelongCustmgr();
		obc.setCustId(olai.getCustId());
		obc.setMainType("0");							//协办类型,主办是1，协办是0
		obc.setMgrId(olai.getApplyUser());				//归属客户经理编号
		obc.setMgrName(olai.getApplyUsername());		//归属客户经理名称
		obc.setAssignDate(new Date());
		obc.setAssignUser(auth.getUserId());			//分配人
		obc.setAssignUsername(auth.getCname());			//分配人名称
		obc.setCheckRight("1");							//1：是，0：否
		obc.setInstitution(auth.getUnitId());			//机构编号
		obc.setInstitutionName(auth.getUnitName());		//机构名称
		obc.setMaintainRight("1");						//1：是，0：否
		this.setBaseDAO(bCBaseDAO);
		this.save(obc);
		return "success";
	}
}
