package com.yuchengtech.bob.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.yuchengtech.bob.model.PotentialCustomerAllocation1;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 潜在客户-机构-关系
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class PotentialCustomerAllocation1Service {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<PotentialCustomerAllocation1> query(String bid) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from PotentialCustomerAllocation1 c where c.BID=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, bid);
        q.setFirstResult(0);
        q.setMaxResults(10);
        List<PotentialCustomerAllocation1> list = q.getResultList();
        return list;
    }
    public List <PotentialCustomerAllocation1> querysave(JSONArray jarray,String x) throws ParseException {
    	
        List <PotentialCustomerAllocation1> l=new ArrayList<PotentialCustomerAllocation1>();
        
        if(jarray.size()>0)
        {
        	for (int i=0;i<jarray.size();i++)  {
        	List<PotentialCustomerAllocation1> list=query(jarray.get(i).toString());
   		if(!list.isEmpty()){
     		  PotentialCustomerAllocation1 p1=new PotentialCustomerAllocation1();
     		  p1=list.get(0);
     		 // if(!p1.getUNITCODE().equals(x)){
   		       l.add(p1);
   		       //}
   		}}}

      		return l;
    }

    public void save(JSONArray jarray,String x) throws ParseException {
   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String currenUserId = auth.getUserId();
 
    	  if(jarray.size()>0)
	        {
	        	for (int i=0;i<jarray.size();i++)  {
	        		List<PotentialCustomerAllocation1> list=query(jarray.get(i).toString());	
      			    PotentialCustomerAllocation1 potentialCustomerAllocation1=new PotentialCustomerAllocation1();
	        		  if(list.isEmpty()){
	        			  potentialCustomerAllocation1.setBID(jarray.get(i).toString());
	        			  potentialCustomerAllocation1.setUNITCODE(x);
	        			  potentialCustomerAllocation1.setCREATE_NAME(currenUserId);
	        			  potentialCustomerAllocation1.setCREATE_DATE(getDate());
	        			  em.persist(potentialCustomerAllocation1);
	        		  }
	        		  else{
	        			  potentialCustomerAllocation1=list.get(0);
	        			  potentialCustomerAllocation1.setUNITCODE(x);
	        			  em.merge(potentialCustomerAllocation1);
	        		  }
	        		  
	        	}
	        }
    }
    public void addunit(String Bid) throws ParseException{
    	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String currenUserId = auth.getUserId();
         String UnitId = auth.getUnitId();
         PotentialCustomerAllocation1 potentialCustomerAllocation1=new PotentialCustomerAllocation1();
         potentialCustomerAllocation1.setBID(Bid);
		  potentialCustomerAllocation1.setUNITCODE(UnitId);
		  potentialCustomerAllocation1.setCREATE_NAME(currenUserId);
		  potentialCustomerAllocation1.setCREATE_DATE(getDate());
		  em.persist(potentialCustomerAllocation1);
    	
    }
 
    
/*    // 删除
    public void remove(String idStr) {
        String[] strarray = idStr.split(",");
        for (int i = 0; i < strarray.length; i++) {
            long Mxtid = Long.parseLong(strarray[i]);
            CustomerOtherBank customerOtherBank = em.find(CustomerOtherBank.class,
                    Mxtid);
            ;
            if (customerOtherBank != null) {
                em.remove(customerOtherBank);
            }
        }
    }*/
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