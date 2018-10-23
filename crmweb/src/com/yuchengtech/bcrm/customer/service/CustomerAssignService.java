package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.OcrmFCiCustBelongHist;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 分配机构和分配客户经理分配 
 * @author songxs
 * @since 2013-1-11
 */

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerAssignService extends CommonService {
	
	public CustomerAssignService() {
		JPABaseDAO<OcrmFCiBelongOrg, Long> baseDAO = new JPABaseDAO<OcrmFCiBelongOrg, Long>(
				OcrmFCiBelongOrg.class);
		super.setBaseDAO(baseDAO);
	}
	/**
	 * 主办机构的保存
	 * @param mainCode//主办机构ID
	 * @param mainName//主板机构NAME
	 * @param custIds//客户ID
	 * @param delIDs//所选择的信息的ID
	 * @return
	 * @throws Exception 
	 */
	public String saveMainOrg(String mainCode,String mainName,String custIds,String delIDs,String delOmainId,JSONArray jarray2,JSONArray jarray1,String delIDs_1 ) throws Exception{
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String[] jarray3 = delIDs.split(",");
		if(!mainCode.equals("")&&!mainName.equals("")){
			
				for(int j = 0;j < jarray3.length;j++){
					Long id = Long.valueOf(jarray3[j]);
					OcrmFCiBelongOrg we = em.find(OcrmFCiBelongOrg.class,id);
					we.setInstitutionCode(mainCode);
					we.setInstitutionName(mainName);
					we.setAssignDate(new Date());
					we.setAssignUser(auth.getUserId());
					we.setAssignUsername(auth.getUsername());
					this.em.merge(we);
				
					OcrmFCiCustBelongHist mt = new OcrmFCiCustBelongHist();//保存主办机构历史记录
					mt.setCustAssignType("0");
					mt.setCustId(we.getCustId());
					mt.setInstitutionCode(mainCode);
					mt.setInstutionName(mainName);
					mt.setMainType("1");
					mt.setMgrId("");
					mt.setMgrName("");
					saveCustomerAssignHis( mt );
			
					OcrmFCiCustBelongHist mt_1 = new OcrmFCiCustBelongHist();//保存取消主办的历史记录
					mt_1.setCustAssignType("2");
					mt_1.setCustId(we.getCustId());
					mt_1.setInstitutionCode(auth.getUnitId());
					mt_1.setInstutionName(auth.getUnitName());
					mt_1.setMainType("1");
					mt_1.setMgrId("");
					mt_1.setMgrName("");
					saveCustomerAssignHis( mt_1 );
				}
		}
		if(!delOmainId.equals("")){
			delOmainId(delOmainId, custIds, jarray2);}
		if(jarray1 != null){
			saveOmainOrg(custIds,jarray1,delIDs_1);
		}
		return "success";
	}
	/**
	 * 协办机构的保存
	 * @param custIds//客户ID
	 * @param jarray1//协办信息
	 * @return
	 * @throws Exception 
	 */
	public String saveOmainOrg(String custIds,JSONArray jarray1,String delIDs_1) throws Exception{
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (jarray1.size() > 0){
			String[] jarray = custIds.split(",");	
			for(int i = 0;i< jarray.length;i++){
				String custid = jarray[i];
				for (int j = 0; j < jarray1.size(); ++j){
					JSONObject wa = (JSONObject)jarray1.get(j);
					OcrmFCiBelongOrg ws = new OcrmFCiBelongOrg();
					ws.setInstitutionCode((String)wa.get("institutionCode"));
					ws.setInstitutionName((String)wa.get("institutionName"));
					ws.setCustId(custid);
					ws.setMainType("2");
					ws.setAssignUser(auth.getUserId());
					ws.setAssignUsername(auth.getUnitName());
					ws.setAssignDate(new Date());
					this.em.persist(ws);

					OcrmFCiCustBelongHist mt = new OcrmFCiCustBelongHist();
					mt.setCustAssignType("0");
					mt.setCustId(custid);
					mt.setInstitutionCode((String)wa.get("institutionCode"));
					mt.setInstutionName((String)wa.get("institutionName"));
					mt.setMainType("2");
					mt.setMgrId("");
					mt.setMgrName("");
					saveCustomerAssignHis( mt );
				}
			}
		}
		if(!delIDs_1.equals("")){
			String jql="delete from OcrmFCiBelongOrg c where c.id in ("+delIDs_1+")";
			Map<String,Object> values=new HashMap<String,Object>();
			this.batchUpdateByName(jql, values);	
			String[] jarray2 = custIds.split(",");	
			for(int j = 0;j<jarray2.length;j++){
				OcrmFCiCustBelongHist mt = new OcrmFCiCustBelongHist();
				mt.setCustAssignType("2");
				mt.setCustId(jarray2[j]);
				mt.setInstitutionCode(auth.getUnitId());
				mt.setInstutionName(auth.getUnitName());
				mt.setMainType("2");
				mt.setMgrId("");
				mt.setMgrName("");
				saveCustomerAssignHis( mt );
			}
		}
		return "success";
	}

	/**
	 * 删除协办机构
	 * @param delOmainId //协办机构的机构ID
	 * @param custIds //客户的ID
	 * @return
	 * @throws Exception 
	 */
	private String delOmainId(String delOmainId,String custIds,JSONArray jarray2) throws Exception{
		
		String[] jarray  = delOmainId.split(",");
		String idstr2="\'" ;
		for (int i = 0; i < jarray.length; i++){
			idstr2 = idstr2+jarray[i];
			idstr2 += "\'";
			if(i != jarray.length-1){
				idstr2 += ",'";
			}
		}
		String jql1="delete from OcrmFCiBelongOrg c where c.custId in ('"+custIds+"') and c.institutionCode in("+idstr2+")";
		Map<String,Object> values=new HashMap<String,Object>();
		this.batchUpdateByName(jql1, values);	
		String[] jarray1  = custIds.split(",");
		for(int j=0;j<jarray1.length;j++){
			String custid = jarray1[j];
			for(int k=0;k<jarray2.size();k++){
				JSONObject ww = (JSONObject)jarray2.get(k);
				OcrmFCiCustBelongHist mt = new OcrmFCiCustBelongHist();//保存主办机构历史记录
				mt.setCustAssignType("2");
				mt.setCustId(custid);
				mt.setInstitutionCode((String)ww.get("institutionCode"));
				mt.setInstutionName((String)ww.get("institutionName"));
				mt.setMainType("2");
				mt.setMgrId("");
				mt.setMgrName("");
				saveCustomerAssignHis( mt );
			}
		}
		return"success";
	}
	/**
	 * 协办客户经理的保存
	 * @param custIds//客户ID
	 * @param jarray//协办客户经理信息
	 * @return
	 * @throws Exception 
	 */
	public String saveOmainMgr(String custIds,JSONArray jarray) throws Exception{
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(jarray.size()>0){
			String[] jarray1 = custIds.split(",");
			for(int i = 0;i< jarray1.length;i++){
				for (int j = 0; j < jarray.size(); ++j){
					JSONObject ws = (JSONObject)jarray.get(j);
					OcrmFCiBelongCustmgr wa = new OcrmFCiBelongCustmgr();
					wa.setAssignDate(new Date());
					wa.setCustId(jarray1[i]);
					wa.setAssignUser(auth.getUserId());
					wa.setAssignUsername(auth.getUsername());
					wa.setCheckRight("1");
					wa.setInstitution(auth.getUnitId());
					wa.setInstitutionName(auth.getUnitName());
					wa.setMaintainRight("0");
					wa.setMainType("2");
					wa.setMgrId((String)ws.get("mgrId"));
					wa.setMgrName((String)ws.get("mgrName"));
					this.em.persist(wa);
					
					OcrmFCiCustBelongHist mt = new OcrmFCiCustBelongHist();
					mt.setCustAssignType("1");
					mt.setCustId(jarray1[i]);
					mt.setInstitutionCode("");
					mt.setInstutionName("");
					mt.setMainType("2");
					mt.setMgrId((String)ws.get("mgrId"));
					mt.setMgrName((String)ws.get("mgrName"));
					saveCustomerAssignHis( mt );
				}
			}
		}
		return"success";
	}
	/**
	 * 主办客户经理的保存
	 * @param custIds//客户ID
	 * @param mainMgrId//主办客户经理ID
	 * @param mainMgrName//主办客户经理NAME
	 * @return
	 * @throws Exception 
	 */
	public String saveMainMgr(String custIds,String mainMgrId,String mainMgrName) throws Exception{
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String[] jarray = custIds.split(",");
		for(int i = 0;i < jarray.length;i++){

			String custid = jarray[i];
			OcrmFCiBelongCustmgr wi = new OcrmFCiBelongCustmgr();
			wi.setAssignDate(new Date());
			wi.setAssignUser(auth.getUserId());
			wi.setAssignUsername(auth.getUsername());
			wi.setCheckRight("1");
			wi.setCustId(custid);
			wi.setInstitution(auth.getUnitId());
			wi.setInstitutionName(auth.getUnitName());
			wi.setMaintainRight("1");
			wi.setMainType("1");
			wi.setMgrId(mainMgrId);
			wi.setMgrName(mainMgrName);
		    this.em.persist(wi);
		    
			OcrmFCiCustBelongHist mt = new OcrmFCiCustBelongHist();
			mt.setCustAssignType("1");
			mt.setCustId(custid);
			mt.setInstitutionCode("");
			mt.setInstutionName("");
			mt.setMainType("1");
			mt.setMgrId(mainMgrId);
			mt.setMgrName(mainMgrName);
			saveCustomerAssignHis( mt );
			}		
		return "success";
	}

	private String saveCustomerAssignHis(OcrmFCiCustBelongHist mt)throws Exception{
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
			mt.setAssignTime(new Date(System.currentTimeMillis()));
			mt.setAssignUser(auth.getUserId());
			mt.setAssignUserName(auth.getUsername());
			mt.setCurrentInstitution(auth.getUnitId());
			mt.setCurrentInstitutionName(auth.getUnitName());
			this.em.persist(mt);	

		return "success";
	
	}	
	}