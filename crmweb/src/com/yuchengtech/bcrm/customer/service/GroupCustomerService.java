package com.yuchengtech.bcrm.customer.service;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
* @ClassName: GroupCustomerService 
* @Description: 集团客户管理
* @author wangmk1 
* @date 2014-7-19 上午11:30:49 
*
 */
@Service
public class GroupCustomerService extends CommonService {
	public GroupCustomerService(){
        JPABaseDAO<OcrmFCiGroupInfo, Long> baseDao = new JPABaseDAO<OcrmFCiGroupInfo, Long>(OcrmFCiGroupInfo.class);
        super.setBaseDAO(baseDao);
    }
	
	/**
	 * 集团客户信息修改
	 */
	@Override
	public Object save(Object obj){
	 	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmFCiGroupInfo groupinfo =(OcrmFCiGroupInfo)obj;
		if(groupinfo.getId()== null){
			return super.save(groupinfo);	
		}else{
			String currentUserId=auth.getUserId();
			Date date = new Date();
			groupinfo.setUpdateUserId(currentUserId);
			groupinfo.setUpdateDate(date);
			return super.save(groupinfo);					
		}	
	}
}
