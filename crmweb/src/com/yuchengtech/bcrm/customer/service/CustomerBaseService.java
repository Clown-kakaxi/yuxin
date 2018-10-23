package com.yuchengtech.bcrm.customer.service;

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

import com.yuchengtech.bcrm.customer.model.CustomerBase2;
import com.yuchengtech.bob.vo.AuthUser;


	@Service
	@Transactional(value="postgreTransactionManager")
	public class CustomerBaseService {
		private EntityManager em;

		@PersistenceContext
		public void setEntityManager(EntityManager em) {
		        this.em = em;
		    }	

	public void save(CustomerBase2 customerBase) throws Exception {
        if (customerBase.getId() == null) {
        	customerBase.setCustomerBaseCreateDate(getDate());
        	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currenUserId = auth.getUserId();
            String OrgId = auth.getUnitId();
            customerBase.setCustomerBaseName(customerBase.getCustomerBaseName());
            customerBase.setCust_base_create_name(currenUserId);
            customerBase.setCustBaseCreateOrg(OrgId);
        	/*List <CustomerBase2> q=query();*/
        	//customerBase.setCustomerBaseNumber(queryCustomerBaseNumberAction.index());
            //新增
           em.persist(customerBase);
           CustomerBase2 customerBase2 = em.find(CustomerBase2.class, customerBase.getId());
           String s1=customerBase2.getId().toString();
           StringBuffer s = new StringBuffer("");
          
           if(s1.length()==5){
        	   s.append("C00"+s1);
  		     //String str=new String();
  	      }
  	      else if(s1.length()==6){
  	    	 s.append("C0"+s1);
  	      }
  	      else {
  	    	 s.append("C"+s1);
  		      }
  	      
           customerBase2.setCustomerBaseNumber(s.toString());
           
			em.merge(customerBase2);
           
           
           
        } else {
        	 Long id=customerBase.getId();
			 CustomerBase2 customerBase2 = em.find(CustomerBase2.class, id);
			 customerBase2.setCustomerBaseName(customerBase.getCustomerBaseName());
			 customerBase2.setCustomerBaseDescribe(customerBase.getCustomerBaseDescribe());
			 customerBase2.setShareFlag(customerBase.getShareFlag());
            //更新
           em.merge(customerBase2);
        }}
    


	
    //删除
    public void remove(JSONArray jarray) {
      if(jarray.size()>0)
	     {
	   for (int i=0;i<jarray.size();i++)
   {
		     Long id=Long.parseLong(jarray.get(i).toString());
			 CustomerBase2 customerBase = em.find(CustomerBase2.class, id);
			 /* Query q = em.createQuery("select c from CustomerRelateCustomerBase2 c where c.customerBaseId=?1 "); 
			 q.setParameter(1,id);
			 List<CustomerRelateCustomerBase2> list= q.getResultList();
		 for(int n=0;n< list.size();n++){
				 CustomerRelateCustomerBase2 customerRelateCustomerBase2 = em.find(CustomerRelateCustomerBase2.class, list.get(i).getId());
		 em.remove(customerRelateCustomerBase2);
	   }*/
	   if (customerBase != null) {
	       em.remove(customerBase);
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
