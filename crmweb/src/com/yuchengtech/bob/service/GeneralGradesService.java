package com.yuchengtech.bob.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.GeneralGrades;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class GeneralGradesService{
	
	private EntityManager em;	
	@Autowired
	private CommonQueryService common1;
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}


	@SuppressWarnings("unchecked")
	public List<GeneralGrades> findAll() {
		Query query = getEntityManager().createQuery("select ak FROM GeneralGrades gg");
        return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public void save(GeneralGrades generalGrades) {
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		String BELONG_INSTN = generalGrades.getBELONG_INSTN();
		String tempString1 ="select t.unitname,t.superunitid,t.unit_level from acrm_f_sm_sys_units_sta  t  where t.unitid = '" +BELONG_INSTN +"'";	
		
		Map tempMap1  = common1.excuteQuery(tempString1, 0, 10);
		
		Map orgMap1 = null;
		
			try {
				orgMap1 =(Map)((List) tempMap1.get("data")).get(0);
			} catch (Exception e) {
				e.printStackTrace();
				//system.out.printlnln("客户的所属组织机构在机构表中未找到");
			}
			
		String superUnitId = (String)orgMap1.get("SUPERUNITID");
		String unitLevel = (String)orgMap1.get("UNIT_LEVEL");
		
//		序号
		Long ID = generalGrades.getID();
//		所属机构名称
		String orgName =(String)orgMap1.get("UNITNAME");
//		所属分行管理部
		String localOrgName = (String)orgMap1.get("UNITNAME");
//		申请日期
		Date appleDate = new Date();
//		填写人
		String fillerName = auth.getUserId();
		
		for(int i = 0; i >=0; i++){
			if (unitLevel.equals("2") ){
				 break;
			}else{
				String tempString2 ="select t.unitname,t.superunitid,t.unit_level from acrm_f_sm_sys_units_sta t  where t.id = '" +superUnitId +"'";
				Map tempMap2 = common1.excuteQuery(tempString2, 0, 10);
				Map orgMap2 =(Map)((List) tempMap2.get("data")).get(0);
				unitLevel = (String)orgMap2.get("UNIT_LEVEL");
				superUnitId = (String)orgMap2.get("SUPERUNITID");
				localOrgName = (String)orgMap2.get("UNITNAME");
			}
		}
		
		generalGrades.setORG_NAME(orgName);
		generalGrades.setLOCAL_ORG_NAME(localOrgName);
		generalGrades.setAPPLY_DATE(appleDate);
		generalGrades.setFILLER_NAME(fillerName);
		
		if (ID == null) {
			//新增
			generalGrades.setORG_NAME(orgName);
			generalGrades.setLOCAL_ORG_NAME(localOrgName);
			generalGrades.setAPPLY_DATE(appleDate);
			generalGrades.setFILLER_NAME(fillerName);
			
			em.persist(generalGrades);
		} else {
			//修改
			em.merge(generalGrades);
		}
	}


    public void remove(long id) {
    	GeneralGrades generalGrades = find(id);
        	if (generalGrades != null) {
            em.remove(generalGrades);
		}
    }
    
	//批量删除账户类型
	public void batchRemove(String idStr) {
		String[] strarray = idStr.split(",");
		for (int i = 0; i < strarray.length; i++) {
			long id = Long.parseLong(strarray[i]);
			GeneralGrades generalGrades = find(id);
			if (generalGrades != null) {
				em.remove(generalGrades);
			}
		}
	}
    
	private EntityManager getEntityManager() {
		return em;
	}

	public  GeneralGrades find(long id) {
		return em.find( GeneralGrades.class, id);
	}

}
