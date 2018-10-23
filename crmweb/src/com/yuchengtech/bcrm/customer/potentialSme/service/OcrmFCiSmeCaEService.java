package com.yuchengtech.bcrm.customer.potentialSme.service;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktCaC;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiSmeCaEService extends CommonService {
	public OcrmFCiSmeCaEService(){
		JPABaseDAO<OcrmFCiMktCaC,Long> baseDao = new JPABaseDAO<OcrmFCiMktCaC,Long>(OcrmFCiMktCaC.class);
		super.setBaseDAO(baseDao);
	}
	
	public void backRM(String id){
		this.em.createQuery("update OcrmFCiMktCaC c set c.ifSumbitCo = '3',c.ifThirdStep = '2' where c.id = '"+id+"'").executeUpdate();
		OcrmFCiMktCaC a = this.em.find(OcrmFCiMktCaC.class,Long.parseLong(id));
		OcrmFCiMktCaC aa = new OcrmFCiMktCaC();
		if(a != null){
			aa.setAddAmt(a.getAddAmt());
			aa.setApplyAmt(a.getApplyAmt());
			aa.setAreaId(a.getAreaId());
			aa.setAreaName(a.getAreaName());
			aa.setCaDateR(a.getCaDateR());
			aa.setCaHardInfo(a.getCaHardInfo());
			aa.setCallId(a.getCallId());
			aa.setCaseType(a.getCaseType());
			aa.setCheckStat(a.getCheckStat());
			aa.setCocoDate(a.getCocoDate());
			aa.setCocoInfo(a.getCocoInfo());
			aa.setCurrency(a.getCurrency());
			aa.setCustId(a.getCustId());
			aa.setCustName(a.getCustName());
			aa.setDdDate(a.getDdDate());
			aa.setDeptId(a.getDeptId());
			aa.setDeptName(a.getDeptName());
			aa.setForeignMoney(a.getForeignMoney());
			aa.setGradeLevel(a.getGradeLevel());
			aa.setGroupName(a.getGroupName());
			aa.setHardRemark(a.getHardRemark());
			aa.setIfAdd(a.getIfAdd());
			aa.setIfThirdStep("2");
			aa.setIntentId(a.getIntentId());
			aa.setRecordDate(new Date());
			aa.setRm(a.getRm());
			aa.setSxDate(a.getSxDate());
			aa.setUpdateDate(null);
			aa.setUserId(a.getUserId());
			aa.setPipelineId(a.getPipelineId());
			aa.setRmId(a.getRmId());
			aa.setCompType(a.getCompType());
			aa.setCustType(a.getCustType());
			aa.setIfCoco(a.getIfCoco());
			aa.setSucProbability(a.getSucProbability());
			aa.setIfSumbitCo("0");
			aa.setFirstDocuDate(a.getFirstDocuDate());
			aa.setGetDocuDate(a.getGetDocuDate());
			aa.setSendDocuDate(a.getSendDocuDate());
			aa.setCaDateS(a.getCaDateS());
			aa.setGradePersect(a.getGradePersect());
			aa.setCaPp(a.getCaPp());
			aa.setXdCaDate(a.getXdCaDate());
			this.save(aa);		
		}
	}
	
	public void goBack(String id,String flag){
		OcrmFCiMktCaC oC = (OcrmFCiMktCaC) this.find(Long.parseLong(id));
		if(oC != null){
			if("1".equals(flag)){
				oC.setIfThirdStep("2");
			}else{
				oC.setIfSumbitCo("1");
			}
			oC.setRecordDate(new Date());
			this.save(oC);
		}
	}
}
