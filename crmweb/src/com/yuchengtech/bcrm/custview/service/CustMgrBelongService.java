package com.yuchengtech.bcrm.custview.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.OcrmFCiCustBelongHist;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户归属调整Service
 * @author wangwan
 * @since 2013-01-18
 */
@Service
public class CustMgrBelongService extends CommonService {
	
   public CustMgrBelongService(){
		JPABaseDAO<OcrmFCiBelongCustmgr, Long>  baseDAO = new JPABaseDAO<OcrmFCiBelongCustmgr, Long>(OcrmFCiBelongCustmgr.class);  
		super.setBaseDAO(baseDAO);
		
		JPABaseDAO<OcrmFCiBelongOrg, Long>      baseDAO2 = new JPABaseDAO<OcrmFCiBelongOrg, Long>(
				OcrmFCiBelongOrg.class);
		super.setBaseDAO(baseDAO2);
		
		JPABaseDAO<OcrmFCiCustBelongHist, Long> baseDAO3 = new JPABaseDAO<OcrmFCiCustBelongHist, Long>(
				OcrmFCiCustBelongHist.class);
		super.setBaseDAO(baseDAO3);
	}
   /**
    * 更改/新增 客户归属机构（主办）
    * @param jarray3//主办机构信息
    */
	@SuppressWarnings("unchecked")
	public void update(JSONArray jarray3){
		AuthUser auth= this.getUserSession();
		if (jarray3.size() > 0){
			for (int i = 0; i < jarray3.size(); ++i){
				JSONObject wb = (JSONObject)jarray3.get(i);
				String searchSql = "select bo from OcrmFCiBelongOrg bo where 1=1 and bo.custId = ?1 and bo.mainType = ?2";
				Query query = em.createQuery(searchSql);
				query.setParameter(1, (String)wb.get("cust_id"));
				query.setParameter(2, (String)wb.get("type"));
				List<OcrmFCiBelongOrg> result = (List<OcrmFCiBelongOrg>)query.getResultList();
				for(OcrmFCiBelongOrg aapl : result){
					
					String org_id = (String)wb.get("org_id");
					if(!org_id.equals(aapl.getInstitutionCode())&&(result.size()>0)){
						OcrmFCiBelongOrg ws = (OcrmFCiBelongOrg)this.em.find(OcrmFCiBelongOrg.class, Long.valueOf(aapl.getId()));
						if (ws != null){
				        	String institutionCode = aapl.getInstitutionCode();
				        	String institutionName = aapl.getInstitutionName();
				        	ws.setInstitutionCode((String)wb.get("org_id"));
				        	ws.setInstitutionName((String)wb.get("org_name"));
				        	ws.setAssignDate(new Date(System.currentTimeMillis()));
				        	ws.setAssignUser(auth.getUserId());
				        	ws.setAssignUsername(auth.getCname());
							this.em.merge(ws);
					
							//客户归属机构调整历史记录--取消主办记录
							OcrmFCiCustBelongHist wa = new OcrmFCiCustBelongHist();
							wa.setAssignTime(new Date(System.currentTimeMillis()));
							wa.setAssignUser(auth.getUserId());
							wa.setAssignUserName(auth.getCname());
							wa.setInstitutionCode(institutionCode);
							wa.setInstutionName(institutionName);
							wa.setMainType("1");
							wa.setCurrentInstitution(auth.getUnitId());
							wa.setCurrentInstitutionName(auth.getUnitName());
							wa.setCustId((String)wb.get("cust_id"));
							wa.setCustAssignType("2");
							this.em.persist(wa);
							
							//客户归属机构调整历史记录--新增主办记录	
							OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
							wq.setAssignTime(new Date(System.currentTimeMillis()));
							wq.setAssignUser(auth.getUserId());
							wq.setAssignUserName(auth.getCname());
							wq.setInstitutionCode((String)wb.get("org_id"));
							wq.setInstutionName((String)wb.get("org_name"));
							wq.setMainType("1");
							wq.setCurrentInstitution(auth.getUnitId());
							wq.setCurrentInstitutionName(auth.getUnitName());
							wq.setCustId((String)wb.get("cust_id"));
							wq.setCustAssignType("0");
							this.em.persist(wq);
				        }
					}
				}
				if(result.size() == 0){
					OcrmFCiBelongOrg bo = new OcrmFCiBelongOrg();
					bo.setInstitutionName((String)wb.get("org_name"));
					bo.setInstitutionCode((String)wb.get("org_id"));
					bo.setCustId((String)wb.get("cust_id"));
					bo.setMainType((String)wb.get("type"));
					bo.setInstitutionName((String)wb.get("org_name"));
					bo.setAssignUser(auth.getUserId());	//设置分配人
					bo.setAssignUsername(auth.getCname());	//设置分配人名称
					bo.setAssignDate(new Date(System.currentTimeMillis()));	//设置分配日期
					this.em.persist(bo);
					
					//客户归属机构调整历史记录--新增主办记录	
					OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
					wq.setAssignTime(new Date(System.currentTimeMillis()));
					wq.setAssignUser(auth.getUserId());
					wq.setAssignUserName(auth.getCname());
					wq.setInstitutionCode((String)wb.get("org_id"));
					wq.setInstutionName((String)wb.get("org_name"));
					wq.setMainType("1");
					wq.setCurrentInstitution(auth.getUnitId());
					wq.setCurrentInstitutionName(auth.getUnitName());
					wq.setCustId((String)wb.get("cust_id"));
					wq.setCustAssignType("0");
					this.em.persist(wq);
				}
			}
		}
	}
	/**
	 * 新增客户归属机构（协办）
	 * @param jarray//新增协办机构信息
	 */
	public void save(JSONArray jarray){
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				AuthUser auth =this.getUserSession();
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiBelongOrg bo = new OcrmFCiBelongOrg();
				bo.setInstitutionName((String)wa.get("org_name"));
				bo.setInstitutionCode((String)wa.get("org_id"));
				bo.setCustId((String)wa.get("cust_id"));
				bo.setMainType((String)wa.get("type"));
				bo.setInstitutionName((String)wa.get("org_name"));
				bo.setAssignUser(auth.getUserId());	//设置分配人
				bo.setAssignUsername(auth.getCname());	//设置分配人名称
				bo.setAssignDate(new Date(System.currentTimeMillis()));//设置分配日期
				this.em.persist(bo);
				
				//客户归属机构历史调整记录-新增协办机构
				OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
				wq.setAssignTime(new Date(System.currentTimeMillis()));
				wq.setAssignUser(auth.getUserId());
				wq.setAssignUserName(auth.getCname());
				wq.setInstitutionCode((String)wa.get("org_id"));
				wq.setInstutionName((String)wa.get("org_name"));
				wq.setMainType("2");//协办机构
				wq.setCurrentInstitution(auth.getUnitId());
				wq.setCurrentInstitutionName(auth.getUnitName());
				wq.setCustId((String)wa.get("cust_id"));
				wq.setCustAssignType("0");//新增协办机构类型
				this.em.persist(wq);
			}
		}
	}
	/**
	 * 删除客户归属机构（协办）
	 * @param jarray2//删除协办机构信息
	 */
	@SuppressWarnings("unchecked")
	public void remove(JSONArray jarray2){
		if (jarray2.size() > 0){
			for (int i = 0; i < jarray2.size(); ++i){
				AuthUser auth = this.getUserSession();
				JSONObject wb = (JSONObject)jarray2.get(i);
				String searchSql = "select bo from OcrmFCiBelongOrg bo where 1=1 and bo.custId = ?1";
				Query query = em.createQuery(searchSql);
				query.setParameter(1, (String)wb.get("cust_id"));
				List<OcrmFCiBelongOrg> result = (List<OcrmFCiBelongOrg>)query.getResultList();
				for(OcrmFCiBelongOrg aapl : result){
					String org_id = (String)wb.get("org_id");
					String type   = (String)wb.get("type");
					if(org_id.equals(aapl.getInstitutionCode()) && type.equals(aapl.getMainType())){
						OcrmFCiBelongOrg ws = (OcrmFCiBelongOrg)this.em.find(OcrmFCiBelongOrg.class, Long.valueOf(aapl.getId()));
				        if (ws != null){
				        this.em.remove(ws);
				        
						//客户归属机构历史调整记录-取消协办机构
						OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
						wq.setAssignTime(new Date(System.currentTimeMillis()));
						wq.setAssignUser(auth.getUserId());
						wq.setAssignUserName(auth.getCname());
						wq.setInstitutionCode((String)wb.get("org_id"));
						wq.setInstutionName((String)wb.get("org_name"));
						wq.setMainType("2");//协办机构
						wq.setCurrentInstitution(auth.getUnitId());
						wq.setCurrentInstitutionName(auth.getUnitName());
						wq.setCustId((String)wb.get("cust_id"));
						wq.setCustAssignType("2");//取消协办机构类型
						this.em.persist(wq);
				        }
					}
				}
			}
		}
	}
	/**
	 * 新增客户归属客户经理（协办机构）
	 * @param addArray//新增协办客户经理信息
	 * @param custIds//客户号
	 */
	public void saveMgr( JSONArray addArray,String custIds){
		
		if (addArray.size() > 0){
			for (int i = 0; i < addArray.size(); ++i){
				AuthUser auth = this.getUserSession();
				JSONObject wa = (JSONObject)addArray.get(i);
				OcrmFCiBelongCustmgr ws = new OcrmFCiBelongCustmgr();
				ws.setMgrId((String) wa.get("mgrId"));
				ws.setMgrName((String) wa.get("mgrName"));
				ws.setInstitution((String) wa.get("institution"));
				ws.setInstitutionName((String) wa.get("institution_name"));
				ws.setMainType("2");
				ws.setAssignUser(auth.getUserId());	//设置分配人
				ws.setAssignUsername(auth.getCname());	//设置分配人名称
				ws.setAssignDate(new Date(System.currentTimeMillis()));	//设置分配时间
				ws.setCustId(custIds);

				this.em.persist(ws);
				
				//客户归属机构历史调整记录-新增协办机构客户经理
				OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
				wq.setAssignTime(new Date(System.currentTimeMillis()));
				wq.setAssignUser(auth.getUserId());
				wq.setAssignUserName(auth.getCname());
				wq.setMgrId((String) wa.get("mgrId"));
				wq.setMgrName((String) wa.get("mgrName"));
				wq.setMainType("2");//协办机构客户经理
				wq.setCurrentInstitution(auth.getUnituid());
				wq.setCurrentInstitutionName(auth.getUnitName());
				wq.setCustId(custIds);
				wq.setCustAssignType("1");//新增协办机构客户经理分配
				this.em.persist(wq);
			}
		}	
	}
	/**
	 * 删除客户归属客户经理（协办）
	 * @param delArray//删除协办客户经理信息
	 * @param custIds//客户号
	 */
	public void removeMgr(JSONArray delArray ,String custIds){
		if (delArray.size() > 0){
			for (int i = 0; i < delArray.size(); ++i) {
				AuthUser auth=this.getUserSession();
				JSONObject wb = (JSONObject)delArray.get(i);
				String t = (String)wb.get("id");
				OcrmFCiBelongCustmgr ws2 = (OcrmFCiBelongCustmgr)this.em.find(OcrmFCiBelongCustmgr.class,Long.valueOf(t));
				if (ws2 != null){
					this.em.remove(ws2);
					//客户归属机构历史调整记录-删除协办机构客户经理
					OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
					wq.setAssignTime(new Date(System.currentTimeMillis()));
					wq.setAssignUser(auth.getUserId());
					wq.setAssignUserName(auth.getCname());
					wq.setMgrId((String) wb.get("mgrId"));
					wq.setMgrName((String) wb.get("mgrName"));
					wq.setMainType("2");//协办机构客户经理
					wq.setCurrentInstitution(auth.getUnitId());
					wq.setCurrentInstitutionName(auth.getUnitName());
					wq.setCustId(custIds);
					wq.setCustAssignType("3");//取消协办机构客户经理分配
					this.em.persist(wq);
				}
			}
		}
	}
		/**
		 * 更改/新增客户归属客户经理（主办）
		 * @param custIds//客户号
		 * @param mainMgrId//主办客户经理ID
		 * @param mainMgrName//主办客户经理姓名
		 * @param institution//主办客户经理所属机构号
		 * @param institution_name//主办客户经理所属机构名称
		 */
	@SuppressWarnings("unchecked")
	public void saveMainTypeMgr(String custIds, String mainMgrId,
		String mainMgrName,String institution,String institution_name) {
		AuthUser auth = this.getUserSession();
		String mainType = "1";
		String searchSql = "select bo from OcrmFCiBelongCustmgr bo where 1=1 and bo.custId = ?1 and bo.mainType='"+mainType+"'";
		Query query = em.createQuery(searchSql);
		query.setParameter(1, custIds);
		List<OcrmFCiBelongCustmgr> result = (List<OcrmFCiBelongCustmgr>)query.getResultList();
		for(OcrmFCiBelongCustmgr aapl : result){
			if(!aapl.getMgrId().equals(mainMgrId) &&(result.size()>0)){
				OcrmFCiBelongCustmgr ws = (OcrmFCiBelongCustmgr)this.em.find(OcrmFCiBelongCustmgr.class, Long.valueOf(aapl.getId()));
				if (ws != null){
		        	
					String mgrId = aapl.getMgrId();
					String mgrName = aapl.getMgrName();
		        	
					ws.setMgrId(mainMgrId);
		        	ws.setMgrName(mainMgrName);
		        	ws.setAssignDate(new Date(System.currentTimeMillis()));
		        	ws.setAssignUser(auth.getUserId());
		        	ws.setAssignUsername(auth.getCname());
		        	ws.setInstitution(institution);
		        	ws.setInstitutionName(institution_name);
		        	
					this.em.merge(ws);
					
					//客户归属机构历史调整记录-删除主办机构客户经理
					OcrmFCiCustBelongHist wq = new OcrmFCiCustBelongHist();
					wq.setAssignTime(new Date(System.currentTimeMillis()));
					wq.setAssignUser(auth.getUserId());
					wq.setAssignUserName(auth.getCname());
					wq.setMgrId(mgrId);
					wq.setMgrName(mgrName);
					wq.setMainType(aapl.getMainType());//主办机构客户经理
					wq.setCurrentInstitution(auth.getUnitId());
					wq.setCurrentInstitutionName(auth.getUnitName());
					wq.setCustId(custIds);
					wq.setCustAssignType("3");//取消主办机构客户经理分配
					this.em.persist(wq);
					
					//客户归属机构历史调整记录-新增主办机构客户经理
					OcrmFCiCustBelongHist wh = new OcrmFCiCustBelongHist();
					wh.setAssignTime(new Date(System.currentTimeMillis()));
					wh.setAssignUser(auth.getUserId());
					wh.setAssignUserName(auth.getCname());
					wh.setMgrId(mainMgrId);
					wh.setMgrName(mainMgrName);
					wh.setMainType("1");//主办机构客户经理
					wh.setCurrentInstitution(auth.getUnitId());
					wh.setCurrentInstitutionName(auth.getUnitName());
					wh.setCustId(custIds);
					wh.setCustAssignType("1");//新增主办机构客户经理分配
					this.em.persist(wh);
		        }
			}
		}
		if(result.size() == 0){

			OcrmFCiBelongCustmgr we = new OcrmFCiBelongCustmgr();
			we.setMgrId(mainMgrId);
        	we.setMgrName(mainMgrName);
        	we.setAssignDate(new Date(System.currentTimeMillis()));
        	we.setAssignUser(auth.getUserId());
        	we.setAssignUsername(auth.getCname());
        	we.setInstitution(institution);
        	we.setInstitutionName(institution_name);
        	we.setCustId(custIds);
        	we.setMainType("1");
        	we.setCheckRight("1");
        	we.setMaintainRight("1");
        	
			this.em.persist(we);
			
			//客户归属机构历史调整记录-新增主办机构客户经理
			OcrmFCiCustBelongHist wh = new OcrmFCiCustBelongHist();
			wh.setAssignTime(new Date(System.currentTimeMillis()));
			wh.setAssignUser(auth.getUserId());
			wh.setAssignUserName(auth.getCname());
			wh.setMgrId(mainMgrId);
			wh.setMgrName(mainMgrName);
			wh.setMainType("1");//主办机构客户经理
			wh.setCurrentInstitution(auth.getUnitId());
			wh.setCurrentInstitutionName(auth.getUnitName());
			wh.setCustId(custIds);
			wh.setCustAssignType("1");//新增主办机构客户经理分配
			this.em.persist(wh);
        
		}
	}
}
