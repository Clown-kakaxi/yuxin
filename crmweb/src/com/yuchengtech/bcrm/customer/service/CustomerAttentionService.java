package com.yuchengtech.bcrm.customer.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.json.JSONArray;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.CustomerAttention;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 我关注的客户
 * @author Administrator
 */

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerAttentionService {
	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	    }
	@SuppressWarnings("unchecked")
	//无查询条件
	public List <CustomerAttention> query(String customerId,String userId) { 
		 StringBuffer querysql=new StringBuffer();
		 querysql.append("select c from CustomerAttention c where  c.customerId=?1 and c.userId=?2");
		 Query q = em.createQuery(querysql.toString()); 
		 q.setParameter(1, customerId);
		 q.setParameter(2, userId);
	     q.setFirstResult(0);
		 q.setMaxResults(10);  
	     List<CustomerAttention> list= q.getResultList(); 
		 return list;
	} 
	
/*	//取消关注
    public void update(CustomerAttention customerAttention) {
        if (customerAttention.getId() != null) {
        	CustomerAttention attention = em.find(CustomerAttention.class, customerAttention.getId());
            //将是否为关注客户字段改为否
        	attention.setImportantCustomerFlag("否");
        	//将取消关注世界改为系统时间
        	customerAttention.setEndDate(getDate());
            em.merge(customerAttention);
        } 
    }*/
	public Date getCurrentDate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		String date = format.format(new java.util.Date()).toString();
		return (Date) format.parse(date);
	}   
    
    public void addAttention(JSONArray jarray) throws Exception  {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
    	  if(jarray.size()>0)
	        {
	        	for (int i=0;i<jarray.size();i++)
	        	{
	        		List<CustomerAttention> list=query(jarray.get(i).toString(),currenUserId);
	        		if(list.isEmpty()){
	        	    CustomerAttention customerAttention=new CustomerAttention();
	        	   // customerAttention.setImportantCustomerFlag("是");
	        	    customerAttention.setCustomerId(jarray.getString(i).toString());
	        	    //customerAttention.setCustomerLevel(jarray1.getString(i).toString());
	        	    //customerAttention.setCustomerName(jarray2.getString(i).toString());
	        	    //customerAttention.setCustomerOrgNo(jarray3.getString(i).toString());
	        	    customerAttention.setUserId(currenUserId);
	        	    customerAttention.setCreateDate(getCurrentDate());
	        		em.persist(customerAttention);}
	        	}
	        }
        
    }
  
	public void batchRemove(JSONArray jarray) {
		//String[] strarray = idStr.split(",");
		
		 if(jarray.size()>0)
	        {
			    for (int i=0;i<jarray.size();i++){
	        	Long id =Long.parseLong(jarray.getString(i).toString());
	         CustomerAttention customerAttention=new CustomerAttention();
			 customerAttention = em.find(CustomerAttention.class, id);
			if (customerAttention != null) {
				em.remove(customerAttention);
			
		}}}
	} 
    
    public   Date   getDate(){ 
        Date date = null; 
        Calendar MyDate =Calendar.getInstance(); 
        MyDate.setTime(new java.util.Date()); 
        date = MyDate.getTime();
		return date;  
        } 

}