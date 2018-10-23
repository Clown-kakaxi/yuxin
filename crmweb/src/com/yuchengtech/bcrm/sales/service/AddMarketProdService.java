package com.yuchengtech.bcrm.sales.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.sales.model.OcrmFMkActiCustomer;
import com.yuchengtech.bcrm.system.model.FwSysProp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
/**
 * 公共参数管理
 * @author changzh@yuchengtech.com
 * @since 2012-11-19
 */
@Service
public class AddMarketProdService extends CommonService{
	
	 public AddMarketProdService(){
		   JPABaseDAO<FwSysProp, Long>  baseDAO = new JPABaseDAO<FwSysProp, Long>(FwSysProp.class);  
		   super.setBaseDAO(baseDAO);
	 }
	 
//	 public Object saveData(Object p) {
//		 try {
////			 StringBuffer searchSql = new StringBuffer("select p from FwSysProp p where p.propName =?1 ");
////			 if (p.getId() != null) {
////				 searchSql.append(" and p.id <>" + p.getId());
////			 }
////			 	
////			Query query = em.createQuery(searchSql.toString());
////			query.setParameter(1, p.getPropName());
////			query.setFirstResult(0);
////				
////			List<FwSysProp> result = (List<FwSysProp>) query.getResultList();
////			if (result != null)	 {
////				/**参数名称是否重复*/
////				if (result.size() > 0) {
////					throw new BizException(1,0,"0001", "参数名称重复,请重新输入");
////				} 
////			}
//				 
//			return baseDAO.save(p);
//			 
//		 } catch (Exception e) {			 
//			 throw new BizException(1,0,"0001",e.getMessage());
//		 }		 
//	 }
	 
	 /*
	  * 查询出营销活动关联客户表中与关联产品表有：产品目标客户关系的客户数据
	  * 2013-03-29 sujm
	  * wzy，20130426，modify：修改查询逻辑，主要是修改查询的SQL
	  * */
	   @SuppressWarnings("unchecked")
//		public Map<String, Object> loadRelateCustomer(String idStr,String mktActiId){
//			   Map<String, Object> result = new HashMap<String, Object>();
//		        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
////		 		StringBuffer JQL = new StringBuffer("select t from AdminAuthAccount t , AdminAuthAccountRole t1,AdminAuthRole t2 where t1.accountId = t.id and t1.roleId =t2.id ");
//		        StringBuffer JQL = new StringBuffer("select t from OcrmFPdAimCust t "+
//						" where t.productId in ('"+idStr.replace(",", "','")+"') "+
//						" and t.custId in (select cust.custId "+
//						" from OcrmFMkActiCustomer cust "+
//						" where cust.aimCustSource = '03' "+
//						" and cust.mktActiId = '"+mktActiId+"')");
////		        if(null!=role&&role.length()>0){
////		 			JQL.append("and t2.roleCode in ('"+role.replace(",", "','")+"') ");
////		 		}
//		 		Query q = em.createQuery(JQL.toString());
//				List<OcrmFPdAimCust> rsList = q.getResultList();
//				for(OcrmFPdAimCust ost: rsList){
//					 if((null!=ost.getCustId())){
//						 HashMap<String, Object> map = new HashMap<String, Object>();
//						 map.put("custId", ost.getCustId());
//						 map.put("custName", ost.getCustName());
//						 rowsList.add(map);
//					 }
//				}
//				 result.put("data", rowsList);
//				 result.put("count", rsList.size());
//				 return result;
//		   }
	   public Map<String, Object> loadRelateCustomer(String idStr,String mktActiId){
		   Map<String, Object> result = new HashMap<String, Object>();
	        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
	        StringBuffer JQL =  new StringBuffer("select cust" +
					"  from OcrmFMkActiCustomer cust" + 
					" where cust.aimCustSource = '03'" + 
					"   and cust.mktActiId = '"+mktActiId+"'" + 
					"   and cust.custId in" + 
					"       (select distinct t.custId" + 
					"          from OcrmFCiCustFitProd t" + 
					"         where t.prodId in ('"+idStr.replace(",", "','")+"')) ");
	 		Query q = em.createQuery(JQL.toString());
			List<OcrmFMkActiCustomer> rsList = q.getResultList();
			for(OcrmFMkActiCustomer ost: rsList){
				 if((null!=ost.getCustId())){
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 map.put("custId", ost.getCustId());
					 map.put("custName", ost.getCustName());
					 rowsList.add(map);
				 }
			}
			 result.put("data", rowsList);
			 result.put("count", rsList.size());
			 return result;
	   }
	 
	 
	 public Object saveData(Object obj) {
			return baseDAO.save(obj);
		}
	 
}
