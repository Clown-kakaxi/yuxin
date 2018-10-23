package com.yuchengtech.bcrm.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.OcrmFCiAliancerviceRela;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

@Service
public class AlianceProgramRelaService extends CommonService{
	
	   public AlianceProgramRelaService(){
		   JPABaseDAO<OcrmFCiAliancerviceRela, Long>  baseDAO=new JPABaseDAO<OcrmFCiAliancerviceRela, Long>(OcrmFCiAliancerviceRela.class);  
		   super.setBaseDAO(baseDAO);
	   }


	// 根据主键是否为空进行新增或者修改渠道
	public Object save(Object model) {
		OcrmFCiAliancerviceRela ocrmFCiVipaddparamSet=(OcrmFCiAliancerviceRela)model;
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        String currenOrgId = auth.getUnitId();
        String tempidentify=ocrmFCiVipaddparamSet.getAddServiceIdentify();//约束增值标识符唯一
        List result =  em.createQuery("select t from OcrmFCiAliancerviceRela t where 1=1 and t.addServiceIdentify ='"+tempidentify+"';").getResultList();
     		if(ocrmFCiVipaddparamSet.getId() == null) { //新增
     			if(result.size()>0){
     	     		throw new BizException(1,0,"","增值服务标识已存在，请重新编辑。");
     	     	}
				ocrmFCiVipaddparamSet.setCreateUser(currenUserId);
				ocrmFCiVipaddparamSet.setCreateDate(new Date());
				ocrmFCiVipaddparamSet.setCreateOrg(currenOrgId);		
				em.persist(ocrmFCiVipaddparamSet);
 			}
		//更新	ocrmFCiVipaddparamSet
     		if(result.size()>0){
 	     		throw new BizException(1,0,"","增值服务标识已存在，请重新编辑。");
 	     	}
     		em.merge(ocrmFCiVipaddparamSet);
     		return ocrmFCiVipaddparamSet;
	}
	//批量删除
//	public void batchRemove(String idStr) {
//		super.batchRemove(idStr);
//	}
}
