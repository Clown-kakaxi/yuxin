package com.yuchengtech.bcrm.customer.potentialMkt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCaC;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCheckC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiMktCheckCService extends CommonService{
	public OcrmFCiMktCheckCService(){
		JPABaseDAO<OcrmFCiMktCheckC,Long> baseDao = new JPABaseDAO<OcrmFCiMktCheckC,Long>(OcrmFCiMktCheckC.class);
		super.setBaseDAO(baseDao);
	}
	//由审查阶段退回CA准备阶段
	public void backCA(Number caId,String id) {
		this.em.createQuery("update OcrmFCiMktCheckC g set g.ifBack = '1' where g.id = '"+id+"'").executeUpdate();
//		this.em.createQuery("update OcrmFCiMktCaC  t set t.ifThirdStep = '2' where t.id = '"+caId+"'").executeUpdate();
		OcrmFCiMktCaC oldCa = this.em.find(OcrmFCiMktCaC.class, caId);
		OcrmFCiMktCaC newCa = new OcrmFCiMktCaC();
		if(oldCa != null){
			newCa.setAddAmt(oldCa.getAddAmt());
			newCa.setApplyAmt(oldCa.getApplyAmt());
			newCa.setAreaId(oldCa.getAreaId());
			newCa.setAreaName(oldCa.getAreaName());
			newCa.setCaDateP(oldCa.getCaDateP());
			newCa.setCaDateR(oldCa.getCaDateR());
			newCa.setCaHardInfo(oldCa.getCaHardInfo());
			newCa.setCallId(oldCa.getCallId());
			newCa.setCaseType(oldCa.getCaseType());
			newCa.setCheckStat(oldCa.getCheckStat());
			newCa.setCocoDate(oldCa.getCocoDate());
			newCa.setCocoInfo(oldCa.getCocoInfo());
			newCa.setCurrency(oldCa.getCurrency());
			newCa.setCustId(oldCa.getCustId());
			newCa.setCustName(oldCa.getCustName());
			newCa.setDdDate(oldCa.getDdDate());
			newCa.setDeptId(oldCa.getDeptId());
			newCa.setDeptName(oldCa.getDeptName());
			newCa.setForeignMoney(oldCa.getForeignMoney());
			newCa.setGradeLevel(oldCa.getGradeLevel());
			newCa.setGroupName(oldCa.getGroupName());
			newCa.setHardRemark(oldCa.getHardRemark());
			newCa.setIfAdd(oldCa.getIfAdd());
			newCa.setIfThirdStep("2");
			newCa.setIntentId(oldCa.getIntentId());
			newCa.setRecordDate(new Date());//改为退回时为当前时期
			newCa.setRm(oldCa.getRm());
			newCa.setSxDate(oldCa.getSxDate());
			newCa.setUpdateDate(null);
			newCa.setUserId(oldCa.getUserId());
			newCa.setPipelineId(oldCa.getPipelineId());
			newCa.setRmId(oldCa.getRmId());
			newCa.setCompType(oldCa.getCompType());
			newCa.setIfCoco(oldCa.getIfCoco());
			newCa.setSucProbability(oldCa.getSucProbability());
			this.save(newCa);
		}
	}
	
//	//获得CA表中最早的RecordDate
//	public Date getRecordDate(Long pipeliineId){
//		Map<String,Object> value=new HashMap<String,Object>();
//		List list	=super.findByJql("select a from OcrmFCiMktCaC a where a.pipelineId = '"+pipeliineId+"' order by a.recordDate asc", value);
//		OcrmFCiMktCaC temp = (OcrmFCiMktCaC)list.get(0);
//		return temp.getRecordDate();
//	}
	
	 //把IF_FOURTH_STEP 字段的撤案，退案状态修改为否；
	public void changeStat(String id) {
		this.em.createQuery("update OcrmFCiMktCheckC t set t.ifFourthStep = '6' where t.id = '"+id+"'").executeUpdate();
	}
	@Override
	public Object save(Object obj) {
		// TODO Auto-generated method stub
		return super.save(obj);
	}
	
}
