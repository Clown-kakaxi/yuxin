package com.yuchengtech.bcrm.customer.customerView.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;


@Service
public class AcrmFCiPotCusComService extends CommonService {

	public AcrmFCiPotCusComService(){
		
		JPABaseDAO<AcrmFCiPotCusCom, Long>  baseDAO=new JPABaseDAO<AcrmFCiPotCusCom, Long>(AcrmFCiPotCusCom.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	// 删除
	public void batchDel(HttpServletRequest request) {
		String cusId = request.getParameter("cusId");
		this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set state='1',OPERATE_TIME =systimestamp where CUS_ID in ("+cusId+")").executeUpdate();
	}
	//h恢复
	public void recoverBack(HttpServletRequest request){
		String cusId = request.getParameter("cusId");
		this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set state='0',OPERATE_TIME=systimestamp where CUS_ID in ("+cusId+")").executeUpdate();
	}
	
	//分配
	public void fbPotCusInfo(HttpServletRequest request){
		String cusId = request.getParameter("cusId");
		String type=request.getParameter("type");
		String custMgr = request.getParameter("custMgr");
		if(type.equals("2")){//分配到人	
			boolean flag= checkRecevierRole(custMgr);
			if(flag){//分配的人已经是RM
				 this.em.createNativeQuery("update acrm_f_ci_pot_cus_com set back_state='0' where cus_id in("+cusId+")").executeUpdate();	
			}
			List list = this.em.createNativeQuery("select ORG_ID from  ADMIN_AUTH_ACCOUNT  where account_name='"+custMgr+"'").getResultList();
			String orgId="";
			for(int i=0;i<list.size();i++){
				orgId = (String) list.get(i);
			}
			if(orgId!=null && !"".equals(orgId)){
				this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set cust_mgr='"+custMgr+"', main_br_id='"+orgId+"'where  CUS_ID in ("+cusId+")").executeUpdate();
			}
		}else{//分配到机构
		    this.em.createNativeQuery("update ACRM_F_CI_POT_CUS_COM set cust_mgr='"+custMgr+"' where CUS_ID in ("+cusId+")").executeUpdate();
		    
		}
	}
	
	
	
	
	 //接受角色
    public boolean checkRecevierRole(String custMgr){	
    	boolean flag = false;
    	try{	
    		List list = this.em.createNativeQuery("SELECT ROLE_CODE FROM ( SELECT F.*," +
						"(CASE WHEN " +
						" F.IDCHECK IS NULL THEN " +
						" 0 " +
						" ELSE " +
						" 1 " +
						" END) IS_CHECKED " +
						" FROM (SELECT * " +
						" FROM ADMIN_AUTH_ROLE T0 " +
						" LEFT JOIN (SELECT T1.ID AS IDCHECK, T1.ROLE_ID " +
						"  FROM ADMIN_AUTH_ACCOUNT_ROLE T1 " +
						"  LEFT JOIN ADMIN_AUTH_ACCOUNT T2 " +
						"  ON T2.ID = T1.ACCOUNT_ID " +
						"  WHERE T1.ACCOUNT_ID = (SELECT T2.ID FROM  ADMIN_AUTH_ACCOUNT T2 WHERE T2.ACCOUNT_NAME IN ('"+custMgr+"'))) E " +
						"   ON E.ROLE_ID = T0.ID " +
						"  WHERE T0.ROLE_LEVEL >= '1') F " +
						" WHERE 1 = 1 " +
						" ORDER BY F.ROLE_LEVEL) WHERE IS_CHECKED='1'").getResultList();
		    if(list!=null && list.size()==1){
		    	if(list.get(0).equals("R305") || list.get(0).equals("R105")){
		    		flag = true;
		    	}
		    }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return flag;
    }
	
	public void updatePotCusInfo(String sql){
		this.em.createNativeQuery(sql).executeUpdate();
	}
}
