package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamily;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 家庭信息Service
 * @author YOYOGI
 * 2014-8-13
 */
@Service
public class AcrmFCiPerFamilyService extends CommonService {

	public AcrmFCiPerFamilyService(){
		JPABaseDAO<AcrmFCiPerFamily, Long>  baseDAO=new JPABaseDAO<AcrmFCiPerFamily, Long>(AcrmFCiPerFamily.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	/**
	 * 确认修改客户家庭信息表
	 */
	public Object save(Object obj){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiPerFamily member = (AcrmFCiPerFamily) obj;
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		if(member.getId() == null){
			member.setLastUpdateSys("CRM");
			member.setLastUpdateUser(auth.getUserId());
			member.setLastUpdateTm(now);
			return super.save(member);
		}else{
			member.setLastUpdateSys("CRM");
			member.setLastUpdateUser(auth.getUserId());
			member.setLastUpdateTm(now);
	  		return super.save(member);
		}
	}
	
	/**
	 *  修正后   save 方法
	 */
//	public Object save(Object obj){
//	    AcrmFCiPerFamily member = (AcrmFCiPerFamily) obj;		
//	    Long id = member.getId();			
//	    String Sql;		   
//	    if(id != null){
//	    	Sql = 
//	    		" update ACRM_F_CI_PER_FAMILY fmy" +
//	    		"set fmy.busi_and_scale        ='"+  member.getBusiAndScale()+"'," + 
//	    		"    fmy.credit_amount        ='"+  member.getCreditAmount()+"'," + 
//	    		"    fmy.credit_info          ='"+ member.getCreditInfo()+"'," + 
//	    		"    fmy.debt_state           ='"+ member.getDebtState()+"'," + 
//	    		"    fmy.family_addr          ='"+ member.getFamilyAddr()+"'," + 
//	    		"    fmy.family_adverse_records='"+ member.getFamilyAdverseRecords()+"'," + 
//	    		"    fmy.fmy_jitsuryoku        ='"+ member.getFmyJitsuryoku()+"'," + 
//	    		"    fmy.has_home_car          ='"+ member.getHasHomeCar()+"'," + 
//	    		"    fmy.home_tel             ='"+ member.getHomeTel()+"'," + 
//	    		"    fmy.house_holder_name     ='"+ member.getHouseHolderName()+"'," + 
//	    		"    fmy.house_stat           ='"+ member.getHouseStat()+"'," + 
//	    		"    fmy.is_credit_family      ='"+ member.getIsCreditFamily()+"'," + 
//	    		"    fmy.is_harmony           ='"+ member.getIsHarmony()+"'," + 
//	    		"    fmy.labor_pop_num         ='"+ member.getLaborPopNum()+"'," + 
//	    		"    fmy.last_update_tm        =sysdate," + 
//	    		"    fmy.population          ='"+ member.getPopulation()+"'," + 
//	    		"    fmy.remark              ='"+ member.getRemark()+"'";	    			    			    		
//	    	this.em.createNativeQuery(Sql).executeUpdate();
//    	}else{
//    		Sql = 
//    			"insert into ACRM_F_CI_PER_FAMILY(" +
//    			"id," + 
//    			"busi_and_scale" + 
//    			",credit_amount" + 
//    			",credit_info" + 
//    			",debt_state" + 
//    			",family_addr" + 
//    			",family_adverse_records" + 
//    			",fmy_jitsuryoku" + 
//    			",has_home_car" + 
//    			",home_tel" + 
//    			",house_holder_name" + 
//    			",house_stat" + 
//    			",is_credit_family" + 
//    			",is_harmony" + 
//    			",labor_pop_num" + 
//    			",last_update_tm" + 
//    			",population" + 
//    			",remark" + 
//    			") values(id_sequence.Nextval," + 
//    			"          '"+  member.getBusiAndScale()+"',"+ 
//    			"          '"+  member.getCreditAmount()+"'," + 
//    			"          '"+ member.getCreditInfo()+"',"  + 
//    			"          '"+ member.getDebtState()+"',"  + 
//    			"          '"+ member.getFamilyAddr()+"'," + 
//    			"          '"+ member.getFamilyAdverseRecords()+"',"  + 
//    			"          '"+ member.getFmyJitsuryoku()+"',"+ 
//    			"          '"+ member.getHasHomeCar()+"',"  + 
//    			"          '"+ member.getHomeTel()+"'," + 
//    			"          '"+ member.getHouseHolderName()+"'," + 
//    			"          '"+ member.getHouseStat()+"',"+ 
//    			"          '"+ member.getIsCreditFamily()+"'," + 
//    			"          '"+ member.getIsHarmony()+"',"+ 
//    			"          '"+ member.getLaborPopNum()+"',"+ 
//    			"          sysdate," + 
//    			"          '"+ member.getPopulation()+"'," + 
//    			"          '"+ member.getRemark()+"')";  	
//	    	this.em.createNativeQuery(Sql).executeUpdate();
//    	}
//		return obj;
//	
//	}
	
	/**
	 * 删除
	 */
	public void batchDel(HttpServletRequest request) {
		String infoId = request.getParameter("infoId");
		this.em.createNativeQuery("delete from ACRM_F_CI_PER_FAMILY where ID IN ("+ infoId +")").executeUpdate();
	}
}
