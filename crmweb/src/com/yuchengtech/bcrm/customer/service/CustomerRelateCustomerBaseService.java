package com.yuchengtech.bcrm.customer.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.CustomerRelateCustomerBase2;
/**
 * 客户与客户群关系
 * @author Administrator
 */

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerRelateCustomerBaseService {
	@PersistenceContext
	private EntityManager em;

	
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	    }
	
	@SuppressWarnings("unchecked")
	//无查询条件
	public List <CustomerRelateCustomerBase2> query(String customerId,Long customerBaseId) { 
		 StringBuffer querysql=new StringBuffer();
		 querysql.append("select c from CustomerRelateCustomerBase2 c where  c.customerId=?1 and c.customerBaseId=?2");
		 Query q = em.createQuery(querysql.toString()); 
		 q.setParameter(1, customerId);
		 q.setParameter(2, customerBaseId);
	     q.setFirstResult(0);
		 q.setMaxResults(10);  
	     List<CustomerRelateCustomerBase2> list= q.getResultList(); 
		 return list;
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
	public void batchSave(JSONArray jarray,JSONArray jarray1,JSONArray jarray2,String x) throws ParseException{
		
	     if(jarray.size()>0)
	        {
	        	for (int i=0;i<jarray.size();i++)
	        	{   List<CustomerRelateCustomerBase2> list=query(jarray.get(i).toString(),Long.parseLong(x));
	        	   //Iterator it = list.iterator();

	        	    if(list.isEmpty()){
	        		CustomerRelateCustomerBase2 model=new CustomerRelateCustomerBase2();
	 	        	model.setCustomerBaseId(Long.parseLong(x));
	        		model.setCustomerId(jarray.get(i).toString());
	        		model.setCUST_NAME(jarray1.get(i).toString());
	        		model.setCUST_ZZDM(jarray2.get(i).toString());
	        		model.setRelationCreateDate(getDate());
	        		em.persist(model);}
	        	}
	        }
		
	}  
	
	   //删除
    public void remove(JSONArray jarray) {
    	 if(jarray.size()>0){
    		for(int i=0;i<jarray.size();i++){
    			CustomerRelateCustomerBase2 customerRelateCustomerBase = em.find(CustomerRelateCustomerBase2.class, Long.parseLong(jarray.get(i).toString()));;
                if (customerRelateCustomerBase != null) {
                   em.remove(customerRelateCustomerBase);
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