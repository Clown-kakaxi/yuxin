package com.yuchengtech.bob.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerInfoService {
	
	private static int i=0;
	@SuppressWarnings("unused")
	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	}
	
/*	//带条件的查询
	public List <CustomerInfo> query(CustomerInfo customerInfo,int first,int max) { 
		 StringBuffer querysql=new StringBuffer();
		 querysql.append("select c from CustomerInfo c ");
		 	*//**	
		 	 * 客户群表空缺，客户群的条件空缺，
		 	 *//*
		 if(customerInfo.getCustomerChineseName()!=null)//客户姓名
		 {
			 querysql.append(sqlMakeUp());
			 querysql.append("customerChineseName LIKE '%");
			 querysql.append(customerInfo.getCustomerChineseName());
			 querysql.append("%'");
		 }
		 if(customerInfo.getCustomerOrgNo()!=null)//组织机构代码
		 {
			 querysql.append(sqlMakeUp());
			 querysql.append("customerOrgNo LIKE '%");
			 querysql.append(customerInfo.getCustomerOrgNo());
			 querysql.append("%'");
		 }
		 if(customerInfo.getStatus()!=null)//客户状态
		 {
			 querysql.append(sqlMakeUp());
			 querysql.append("status ='");
			 querysql.append(customerInfo.getStatus());
			 querysql.append("'");
		 }
		 if(customerInfo.getCustomerScope()!=null)//客户规模
		 {
			 querysql.append(sqlMakeUp());
			 querysql.append("customerScope ='");
			 querysql.append(customerInfo.getCustomerScope());
			 querysql.append("'");
		 }
		 if(customerInfo.getCrmScope()!=null)// 考核口径企业规模
		 {
			 querysql.append(sqlMakeUp());
			 querysql.append("crmScope ='");
			 querysql.append(customerInfo.getCrmScope());
			 querysql.append("'");
		 }
		 if(customerInfo.getHyClass()!=null)// 行业
		 {
			 querysql.append(sqlMakeUp());
			 querysql.append("hyClass LIKE '%");
			 querysql.append(customerInfo.getHyClass());
			 querysql.append("%'");
		 }
		*//**
		 * 缺少大客户级别、中小客户级别，
		 *//*
		*//**
		 * 表中缺少客户名单字段
		 *//*
		 Query q = em.createQuery(querysql.toString());   
	     q.setFirstResult(first);
 		 q.setMaxResults(max);
         List<CustomerInfo> list= q.getResultList(); 
         return list;
		 }*/
	

	
	public String sqlMakeUp(){
		String sql =new String();
	    if(i==1){
			 sql=",";}
			 else{
				 sql="where ";
				 i=i+1;
			 }
	    return sql;  
	   }
      
}