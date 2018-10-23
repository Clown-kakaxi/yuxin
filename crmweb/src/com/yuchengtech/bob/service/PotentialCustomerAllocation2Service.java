package com.yuchengtech.bob.service;

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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bob.model.PotentialCustomerAllocation1;
import com.yuchengtech.bob.model.PotentialCustomerAllocation2;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 潜在客户-经理-关系
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class PotentialCustomerAllocation2Service {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<PotentialCustomerAllocation2> query( String bid, String userid) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from PotentialCustomerAllocation2 c where c.BID=?1 and c.USER_ID=?2");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, bid);
        q.setParameter(2, userid);
        q.setFirstResult(0);
        q.setMaxResults(10);
        List<PotentialCustomerAllocation2> list = q.getResultList();
        return list;
    }
    // 无查询条件
    @SuppressWarnings("unchecked")
    public List<PotentialCustomerAllocation2> query2(String bid) {
        StringBuffer querysql = new StringBuffer();
        querysql.append("select c from PotentialCustomerAllocation2 c where c.BID=?1");
        Query q = em.createQuery(querysql.toString());
        q.setParameter(1, bid);
       // q.setParameter(2, unit_id);
        q.setFirstResult(0);
        q.setMaxResults(10);
        List<PotentialCustomerAllocation2> list = q.getResultList();
        return list;
    }
    public void save(JSONArray jarray,JSONArray jarray1,String unitid) throws ParseException {
   	   AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String currenUserId = auth.getUserId();
 	   for (int i=0;i<jarray.size();i++){
 		  for (int l=0;l<jarray1.size();l++){
 				List<PotentialCustomerAllocation2> list=query(jarray.get(i).toString(),jarray1.get(l).toString());	
 				if(list.isEmpty()){
        		PotentialCustomerAllocation2 potentialCustomerAllocation2=new PotentialCustomerAllocation2();
        		potentialCustomerAllocation2.setBID(jarray.get(i).toString());
        		potentialCustomerAllocation2.setUSER_ID(jarray1.get(l).toString());
        		potentialCustomerAllocation2.setCREATE_NAME(currenUserId);
        		potentialCustomerAllocation2.setCREATE_DATE(getDate());
        		potentialCustomerAllocation2.setUNIT_ID(unitid);
        		em.persist(potentialCustomerAllocation2);
 				
 				}
        		
 			  
 		  }
 		   
 	   }
    }
    public void addrelation(String bid,String userid,String unitid) throws ParseException {
		PotentialCustomerAllocation2 p2=new PotentialCustomerAllocation2();
		p2.setBID(bid);
		p2.setUSER_ID(userid);
		p2.setCREATE_NAME(userid);
		p2.setCREATE_DATE(getDate());
		p2.setUNIT_ID(unitid);
		em.persist(p2);
    }
    public void remove (List<PotentialCustomerAllocation1> l){
    	for(int j=0;j<l.size();j++){
    	List<PotentialCustomerAllocation2> list=query2(l.get(j).getBID());
    	if(!list.isEmpty()){
    		for(int i=0;i<list.size();i++){
    		PotentialCustomerAllocation2 p2=list.get(i);
    	    em.remove(p2);}
    	}
    	}
    }
    public void remove2 (JSONArray jarray,String x){
    	for (int j=0;j<jarray.size();j++){
   
    	List<PotentialCustomerAllocation2> list=query2(jarray.get(j).toString());
    	if(!list.isEmpty()){
    		for(int i=0;i<list.size();i++){
    		PotentialCustomerAllocation2 p2=list.get(i);
    	    em.remove(p2);}
    	}
    	}
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