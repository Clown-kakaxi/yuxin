package com.yuchengtech.bcrm.customer.potentialMkt.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovlC;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCheckC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktApprovlCService extends CommonService{
	public OcrmFCiMktApprovlCService(){
		JPABaseDAO<OcrmFCiMktApprovlC,Long> baseDao = new JPABaseDAO<OcrmFCiMktApprovlC,Long>(OcrmFCiMktApprovlC.class);
		super.setBaseDAO(baseDao);
	}

	public void backSC(Number scId, String id) {
		this.em.createQuery("update OcrmFCiMktApprovlC g set g.ifBack = '1' where g.id = '"+id+"'").executeUpdate();
//		this.em.createNativeQuery("update OCRM_F_CI_MKT_APPROVL_C t set t.IF_BACK = '1' where id = '"+id+"'").executeUpdate();
//		this.em.createQuery("update OcrmFCiMktCheckC t set t.ifFourthStep = '6' where t.id = '"+scId+"'").executeUpdate();
		OcrmFCiMktCheckC oldCheck = this.em.find(OcrmFCiMktCheckC.class, scId);
		OcrmFCiMktCheckC newCheck = new OcrmFCiMktCheckC();
		if(oldCheck != null){
			newCheck.setAddAmt(oldCheck.getAddAmt());
			newCheck.setApplyAmt(oldCheck.getApplyAmt());
			newCheck.setAreaId(oldCheck.getAreaId());
			newCheck.setAreaName(oldCheck.getAreaName());
			newCheck.setCaForm(oldCheck.getCaForm());
			newCheck.setCaId(oldCheck.getCaId());
			newCheck.setCallId(oldCheck.getCallId());
			newCheck.setCaseType(oldCheck.getCaseType());
			newCheck.setCcDate(oldCheck.getCcDate());
			newCheck.setCheckStat(oldCheck.getCheckStat());
			newCheck.setCo(oldCheck.getCo());
			newCheck.setCompType(oldCheck.getCompType());
			newCheck.setCurrency(oldCheck.getCurrency());
			newCheck.setCustId(oldCheck.getCustId());
			newCheck.setCustName(oldCheck.getCustName());
			newCheck.setDeptId(oldCheck.getDeptId());
			newCheck.setDeptName(oldCheck.getDeptName());
			newCheck.setForeignMoney(oldCheck.getForeignMoney());
			newCheck.setGradeLevel(oldCheck.getGradeLevel());
			newCheck.setGroupName(oldCheck.getGroupName());
			newCheck.setIfAdd(oldCheck.getIfAdd());
			newCheck.setIfFourthStep("6");
			newCheck.setMemo(oldCheck.getMemo());
			newCheck.setQaDate(oldCheck.getQaDate());
			newCheck.setRecordDate(new Date());
			newCheck.setRm(oldCheck.getRm());
			newCheck.setRmCDate(oldCheck.getRmCDate());
			newCheck.setRmDate(oldCheck.getRmDate());
			newCheck.setSpLevel(oldCheck.getSpLevel());
			newCheck.setUpdateDate(null);
			newCheck.setUserId(oldCheck.getUserId());
			newCheck.setXdCaDate(oldCheck.getXdCaDate());
			newCheck.setXsCcDate(oldCheck.getXsCcDate());
			newCheck.setPipelineId(oldCheck.getPipelineId());
			newCheck.setRmId(oldCheck.getRmId());
			newCheck.setIfBack(oldCheck.getIfBack());
			newCheck.setRefuseReason(oldCheck.getRefuseReason());
			newCheck.setReasonRemark(oldCheck.getReasonRemark());
			newCheck.setAddCaseContent(oldCheck.getAddCaseContent());
			newCheck.setAddCaseDate(oldCheck.getAddCaseDate());
			newCheck.setCheckProgress(oldCheck.getCheckProgress());
			this.save(newCheck);
		}
	}
	 //把IF_FOURTH_STEP 字段的撤案，退案状态修改为否；
	public void changeStat(String id) {
		this.em.createQuery("update OcrmFCiMktApprovlC t set t.ifFifthStep = '6' where t.id = '"+id+"'").executeUpdate();
	}

	@Override
	public Object save(Object obj) {
		return super.save(obj);
	}
}
