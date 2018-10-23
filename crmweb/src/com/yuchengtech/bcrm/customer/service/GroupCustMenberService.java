package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupMember;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 
* @ClassName: GroupCustMenberService 
* @Description: 集团成员操作
* @author wangmk1 
* @date 2014-7-22 
*
 */
@Service
public class GroupCustMenberService extends CommonService {
	public GroupCustMenberService(){
        JPABaseDAO<OcrmFCiGroupMember, Long> baseDao = new JPABaseDAO<OcrmFCiGroupMember, Long>(OcrmFCiGroupMember.class);
        super.setBaseDAO(baseDao);
    }
	
	/**
	 * 成员信息保存或修改
	 */
	@Override
	public Object save(Object obj){
	 	OcrmFCiGroupMember groupMember =(OcrmFCiGroupMember)obj;
			return super.save(groupMember);					
	}

}
