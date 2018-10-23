package com.yuchengtech.bob.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.json.JSONArray;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.CustomerRelateCustomerBase2;
import com.yuchengtech.bob.model.CustomerRelation;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 客户与客户群关系
 * @author Administrator
 */

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerRelationService {
	@PersistenceContext
	private EntityManager em;

	
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	    }
	

	
	public void save(CustomerRelateCustomerBase2 customerRelateCustomerBase) {
        if (customerRelateCustomerBase.getId() == null) {
            //新增
            em.persist(customerRelateCustomerBase);
        } else {
            //更新
            em.merge(customerRelateCustomerBase);
        }
    }
	public void batchSave(JSONArray jarray,JSONArray jarray1,JSONArray jarray2,String custid,String custzhname,String custzzdm,String shpct,String reladesc,String relaname) throws Exception{
		 
			 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		     String currenUserId = auth.getUserId();
	     if(jarray.size()>0)
	        {
	    	 
	        	for (int i=0;i<jarray.size();i++)
	        	{
	        		CustomerRelation model=new CustomerRelation();
	 	        	model.setDEST_CUST_ID(custid);
	 	        	model.setDEST_CUST_NAME(custzhname);
	 	        	model.setDEST_CUST_ZZDM(custzzdm);
	 	        	model.setRELA_NAME(relaname);
	 	        	model.setRELA_DESC(reladesc);
	 	        	model.setCREATOR(currenUserId);
	 	        	model.setCREAT_DATE(getDate());
	 	        	if(shpct.length()!=0){
	 	        	BigDecimal bd=new BigDecimal(shpct);
	 	        	bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	 	        	model.setSH_PCT(bd);}
	 	        	else{model.setSH_PCT(new BigDecimal(0));}
	 	        
	        		model.setRELA_CUST_ID(jarray.get(i).toString());
	        		model.setRELA_CUST_NAME(jarray1.get(i).toString());
	        		model.setRELA_CUST_ZZDM(jarray2.get(i).toString());
	        		em.persist(model);
	        	}
	        }
		
	}  
	
	   //删除
    public void remove(JSONArray jarray) {
    	 if(jarray.size()>0){
    		for(int i=0;i<jarray.size();i++){
    			CustomerRelation customerRelation = em.find(CustomerRelation.class, Long.parseLong(jarray.get(i).toString()));;
                if (customerRelation != null) {
                   em.remove(customerRelation);
                    }
    	       }  
    	  }
    }
    public Date getDate() throws ParseException{   
 	   DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");    
 	        Calendar ca = Calendar.getInstance();   
 	         int year = ca.get(Calendar.YEAR);//获取年份   
 	         int month=ca.get(Calendar.MONTH);//获取月份    
 	         int day=ca.get(Calendar.DATE);//获取日   
 	         String date = year + "-" + (month + 1 )+ "-" + day;   
 	         Date  date1 = format1.parse(date);  
 	         return date1;   
    }

}