package com.yuchengtech.bcrm.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.OcrmFCiAlianceProgram;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 联盟商管理SERVICE
 * @author hujun
 * 2014-06-23
 */
@Service
public class AlianceProgramInfoService extends CommonService{
	@Autowired
	private DataSource dsOracle;
	   public AlianceProgramInfoService(){
		   JPABaseDAO<OcrmFCiAlianceProgram, Long>  baseDAO=new JPABaseDAO<OcrmFCiAlianceProgram, Long>(OcrmFCiAlianceProgram.class);  
		   super.setBaseDAO(baseDAO);
	   }


	// 根据主键是否为空进行新增或者修改渠道
	public Object save(Object model) {
		OcrmFCiAlianceProgram ocrmFCiVipaddparamSet=(OcrmFCiAlianceProgram)model;
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUsername();
        String currenOrgId = auth.getUnitName();
		if (ocrmFCiVipaddparamSet.getId() == null) { //新增
			em.persist(ocrmFCiVipaddparamSet);
			auth.setPid(ocrmFCiVipaddparamSet.getId().toString());
		}
		//更新		
		em.merge(ocrmFCiVipaddparamSet);
	    return ocrmFCiVipaddparamSet;
	}
	//批量删除
	public void outAliance(String id,String reason) {
		OcrmFCiAlianceProgram find=(OcrmFCiAlianceProgram)this.find(Long.parseLong(id));
		find.setState("05");
		find.setOutReason(reason);
		super.save(find);
	}
	//修改状态
	public void setState(Long id){
		OcrmFCiAlianceProgram find=em.find(OcrmFCiAlianceProgram.class, id);
		find.setState("02");
		super.save(find);
	}
}
