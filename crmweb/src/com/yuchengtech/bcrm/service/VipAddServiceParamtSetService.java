package com.yuchengtech.bcrm.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.OcrmFCiAliancerviceRela;
import com.yuchengtech.bcrm.model.OcrmFCiVipaddparamSet;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class VipAddServiceParamtSetService extends CommonService{

	   public VipAddServiceParamtSetService(){
		   JPABaseDAO<OcrmFCiVipaddparamSet, Long>  baseDAO=new JPABaseDAO<OcrmFCiVipaddparamSet, Long>(OcrmFCiVipaddparamSet.class);  
		   super.setBaseDAO(baseDAO);
	   }


	// 根据主键是否为空进行新增或者修改渠道
	public Object save(Object model) {
		OcrmFCiVipaddparamSet ocrmFCiVipaddparamSet=(OcrmFCiVipaddparamSet)model;
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUsername();
        String currenOrgId = auth.getUnitName();
        String d=ocrmFCiVipaddparamSet.getAddServiceName();
        String s="";
        try {
			s=getDate(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ocrmFCiVipaddparamSet.getId() == null) { //新增
			ocrmFCiVipaddparamSet.setCreateUser(currenUserId);
			ocrmFCiVipaddparamSet.setCreateDate(new Date());
			ocrmFCiVipaddparamSet.setCreateOrg(currenOrgId);
			ocrmFCiVipaddparamSet.setAddServiceIdentify(s);
			em.persist(ocrmFCiVipaddparamSet);
		}
		//更新		
		ocrmFCiVipaddparamSet.setAddServiceIdentify(s);
		em.merge(ocrmFCiVipaddparamSet);
	    return ocrmFCiVipaddparamSet;
	}
	   //获取增值服务标识
    public String getDate(String value) throws SQLException{
    	String data=null;
		String jql = "select c from OcrmFCiAliancerviceRela c where c.addServiceName ='"+value+"'";
		Query q = em.createQuery(jql);
		List<OcrmFCiAliancerviceRela> list = q.getResultList();
		Iterator<OcrmFCiAliancerviceRela> itardadl = list.iterator();
		while(itardadl.hasNext()){
			data=itardadl.next().getAddServiceIdentify();
		}
		return data;
    	
    };
	//批量删除
//	public void batchRemove(String idStr) {
//		super.batchRemove(idStr);
//	}
}
