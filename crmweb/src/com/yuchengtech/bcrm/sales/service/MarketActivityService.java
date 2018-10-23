package com.yuchengtech.bcrm.sales.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.sales.model.OcrmFMkMktActivity;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

@Service
@Transactional(value="postgreTransactionManager")
public class MarketActivityService extends CommonService{

	   public MarketActivityService(){
		   JPABaseDAO<OcrmFMkMktActivity, Long>  baseDAO=new JPABaseDAO<OcrmFMkMktActivity, Long>(OcrmFMkMktActivity.class);  
		   super.setBaseDAO(baseDAO);
	   }	
	   

//	// 查询营销活动列表
//	@SuppressWarnings("unchecked")
//	public List<OcrmFMkMktActivity> findAll() {
//		Query query = getEntityManager().createQuery(
//				"select ma FROM OcrmFMkMktActivity ma");
//		return query.getResultList();
//	}

//	// 根据mktActiId是否为空进行新增或者修改活动
//	public void save(OcrmFMkMktActivity ocrmfmkmktactivity) {
//		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currenUserId = auth.getUserId();
//        Date date = new Date(); 
//        //MarketPlan marketPlan = marketPlanService.find(OcrmFMkMktActivity.getPlanId());
//        //OcrmFMkMktActivity.setPlanName(marketPlan.getPlanName());
//        ocrmfmkmktactivity.setUpdateUser(currenUserId);
//        ocrmfmkmktactivity.setUpdateDate(date);
//		if (ocrmfmkmktactivity.getMktActiId() == null) {
//			// 新增
////			ocrmfmkmktactivity.setApproveStat("1");
//			ocrmfmkmktactivity.setCreateUser(currenUserId);
//			ocrmfmkmktactivity.setCreateDate(date);
//			em.persist(ocrmfmkmktactivity);
//		} else {
//			// 修改
//			em.merge(ocrmfmkmktactivity);
//		}
//	}

//	//批量删除营销活动
//	public void batchRemove(String idStr) {
//		String[] strarray = idStr.split(",");
//		for (int i = 0; i < strarray.length; i++) {
//			long id = Long.parseLong(strarray[i]);
//			OcrmFMkMktActivity marketActivityService = find(id);
//			if (marketActivityService != null) {
//				em.remove(marketActivityService);
//			}
//		}
//	}
	
	// 删除营销活动
//	public void remove(long id) {
//		OcrmFMkMktActivity ocrmfmkmktactivity = find(id);
//		if (ocrmfmkmktactivity != null) {
//			em.remove(ocrmfmkmktactivity);
//		}
//	}

//	//关闭营销活动
//    public void closeActivity(OcrmFMkMktActivity ocrmfmkmktactivity)
//    {
//    	if (ocrmfmkmktactivity.getMktActiId() != null)
//    	{
//    		
//    	Date currDate = new Date();	
//    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currenUserId = auth.getUserId();	
//        
//    	//设置为关闭状态
////        ocrmfmkmktactivity.setActiStatus("3");
//    	//设置关闭日期
////        ocrmfmkmktactivity.setActivityEndDate(currDate);
//    	
//        ocrmfmkmktactivity.setUpdateDate(currDate);
//        ocrmfmkmktactivity.setUpdateUser(currenUserId);
//    	em.merge(ocrmfmkmktactivity);
//    	}
//    }
//    
	   
	@Override
	public Object save(Object obj) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmFMkMktActivity mkt = (OcrmFMkMktActivity)obj;
		if(mkt.getMktActiId() == null){
			mkt.setCreateDate(new Date());
			mkt.setCreateOrg(auth.getUnitId());
			mkt.setCreateUser(auth.getUserId());
		}else{
			//由于新增时，点击了下一步，然后再点击回上一眇，就可能造成下列字段为空
			if(mkt.getCreateOrg() == null){
				mkt.setCreateDate(new Date());
				mkt.setCreateOrg(auth.getUnitId());
				mkt.setCreateUser(auth.getUserId());
			}
			mkt.setUpdateDate(new Date());
			mkt.setUpdateUser(auth.getUserId());
		}
		return super.save(mkt);
	}


	public void setState(Long id){
		   OcrmFMkMktActivity pool=em.find(OcrmFMkMktActivity.class, id);
		  // pool.setMktActiStat("2");
		   pool.setMktAppState("2");
		   em.merge(pool);
	   }
    //执行营销活动
    public void saveActivity(String idStr,String sign)
    {
    	String[] strarray = idStr.split(",");
    	boolean flag = true;
		for (int i = 0; i < strarray.length; i++) {
			String sss=strarray[i];
			List<Object[]> list = new ArrayList<Object[]>();
			List<Object[]> list2 = new ArrayList<Object[]>();
			list = this.baseDAO.findByNativeSQLWithIndexParam("select * from ocrm_f_mk_acti_customer t where t.mkt_acti_id = '"+sss+"' ");
			list2 = this.baseDAO.findByNativeSQLWithIndexParam("select * from ocrm_f_mk_acti_product t where t.mkt_acti_id = '"+sss+"' ");
			if(list.size()==0 || list2.size()==0)
				flag = false;
		}
    	
	for (int i = 0; i < strarray.length; i++) {
		long id = Long.parseLong(strarray[i]);
		OcrmFMkMktActivity ocrmfmkmktactivity = em.find(OcrmFMkMktActivity.class,id );
		if("close".equals(sign)){
				ocrmfmkmktactivity.setMktActiStat("4");	
				ocrmfmkmktactivity.setAendDate(new Date());
		}else{
			if("execute".equals(sign)){
				if(!flag){
					throw new BizException(1,0,"100010","对不起，您须先关联产品和目标客户，才能执行此操作!");
				}
				//更新关联客户表，将进展阶段更新为1：执行中
				String jql = "update OcrmFMkActiCustomer p set p.progressStep = '1' where p.mktActiId ='"+BigDecimal.valueOf(ocrmfmkmktactivity.getMktActiId())+"' ";
		    	Map<String,Object> values = new HashMap<String,Object>();
        		batchUpdateByName(jql, values);
        		//更新营销活动基本信息，更新营销活动状态为 3:执行中
				ocrmfmkmktactivity.setMktActiStat("3");
				ocrmfmkmktactivity.setAstartDate(new Date());
			}
		}
	
		em.merge(ocrmfmkmktactivity);
	}
}

    
    //审批通过营销活动
//    public void saveApprovePass(OcrmFMkMktActivity ocrmfmkmktactivity)
//    {
//    	if (ocrmfmkmktactivity.getMktActiId() != null)
//    	{
//    		
//    	Date currDate = new Date();	
//    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currenUserId = auth.getUserId();	
//        
//    	//设置为执行中状态
////        ocrmfmkmktactivity.setActiStatus("2");
////        ocrmfmkmktactivity.setApproveStat("3");
//    	//设置执行日期
////        ocrmfmkmktactivity.setActivityStartDate(currDate);
//    	
//        ocrmfmkmktactivity.setUpdateDate(currDate);
//        ocrmfmkmktactivity.setUpdateUser(currenUserId);
//    	em.merge(ocrmfmkmktactivity);
//    	}
//    }
    
//    //审批未通过营销活动
//    public void saveApproveNotPass(OcrmFMkMktActivity ocrmfmkmktactivity)
//    {
//    	if (ocrmfmkmktactivity.getMktActiId() != null)
//    	{
//    		
//    	Date currDate = new Date();	
//    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currenUserId = auth.getUserId();	
//        
//    	//设置为执行中状态
//       // ocrmfmkmktactivity.setActiStatus("2");
////        ocrmfmkmktactivity.setApproveStat("4");
//    	//设置执行日期
////        ocrmfmkmktactivity.setActivityStartDate(currDate);
//    	
//        ocrmfmkmktactivity.setUpdateDate(currDate);
//        ocrmfmkmktactivity.setUpdateUser(currenUserId);
//    	em.merge(ocrmfmkmktactivity);
//    	}
//    }

//	public OcrmFMkMktActivity find(long id) {
//		return em.find(OcrmFMkMktActivity.class, id);
//	}

}
