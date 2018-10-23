package com.yuchengtech.bcrm.sales.service;

import java.sql.Timestamp;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOppor;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;


/**
 * 
* @ClassName: CreateMktOpporService 
* @Description: 创建商机组件保存商机执行service
* @author wangmk1 
* @date 2014-10-11 下午3:17:33 
*
 */
@Service
public class CreateMktOpporService extends CommonService {
	public CreateMktOpporService(){
		JPABaseDAO<OcrmFMmMktBusiOppor,String> baseDao=new JPABaseDAO<OcrmFMmMktBusiOppor,String>(OcrmFMmMktBusiOppor.class);
		super.setBaseDAO(baseDao);
	}
	@SuppressWarnings("unchecked")
	/**
	 * 创建商机记录
	 */
	public Object save(Object obj) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmFMmMktBusiOppor opp= (OcrmFMmMktBusiOppor) JSONObject.toBean((JSONObject) obj,OcrmFMmMktBusiOppor.class);
		opp.setCreaterId(auth.getUserId());// 创建人ID
		opp.setCreaterName(auth.getUsername());// 创建人名称
		opp.setCreateOrgId(((HashMap<String, String>) (auth
				.getPathOrgList().get(0))).get("ID"));// 创建人所在机构ID
		opp.setCreateOrgName(((HashMap<String, String>) (auth
				.getPathOrgList().get(0))).get("UNITNAME"));// 创建人所在机构名称
		opp.setCreateDateTime(new Timestamp(System
				.currentTimeMillis()));// 创建时间
		opp.setUpdateUserId(auth.getUserId());// 更新人ID
		opp.setUpdateUserName(auth.getUsername());// 更新人名称
		opp.setUpdateOrgId(((HashMap<String, String>) (auth
				.getPathOrgList().get(0))).get("ID"));// 更新人所在机构ID
		opp.setUpdateOrgName(((HashMap<String, String>) (auth
				.getPathOrgList().get(0))).get("UNITNAME"));// 更新人所在机构名称
		opp.setUpdateDateTime(new Timestamp(System
				.currentTimeMillis()));// 更新时间
		return super.save(opp);
	}
}
