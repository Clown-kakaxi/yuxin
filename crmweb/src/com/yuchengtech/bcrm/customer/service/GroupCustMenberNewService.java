package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;


import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupMemberNew;
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
public class GroupCustMenberNewService extends CommonService {
	public GroupCustMenberNewService(){
        JPABaseDAO<OcrmFCiGroupMemberNew, Long> baseDao = new JPABaseDAO<OcrmFCiGroupMemberNew, Long>(OcrmFCiGroupMemberNew.class);
        super.setBaseDAO(baseDao);
    }
	
	/**
	 * 成员信息保存或修改
	 */
	@Override
	public Object save(Object obj){
	 	OcrmFCiGroupMemberNew groupMember =(OcrmFCiGroupMemberNew)obj;
			return super.save(groupMember);					
	}
	
	
//	public void batchUpdateByName(String id,String grpno) {
//        Map<String, Object> values = new HashMap<String, Object>();
//        super.batchUpdateByName("DELETE FROM OcrmFCiGroupMemberNew a WHERE a.id IN ("+ id +") and a.grpNo='"+grpno+"'", values);
//    }

}
