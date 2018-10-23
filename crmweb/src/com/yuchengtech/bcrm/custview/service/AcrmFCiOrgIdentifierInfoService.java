package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * 对公客户视图证件信息
 * @author agile
 */
@Service
public class AcrmFCiOrgIdentifierInfoService extends CommonService {
	
	private static Logger log = Logger.getLogger(AcrmFCiOrgIdentifierInfoService.class);
	
	public AcrmFCiOrgIdentifierInfoService(){
		JPABaseDAO<AcrmFCiCustIdentifier,String> baseDao = new JPABaseDAO<AcrmFCiCustIdentifier,String>(AcrmFCiCustIdentifier.class);
		super.setBaseDAO(baseDao);
	}
	
	public Object save(Object obj){
		AcrmFCiCustIdentifier fier = (AcrmFCiCustIdentifier)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		//查询提醒人员
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(fier.getIdentId()==null){
			fier.setLastUpdateUser(auth.getUserId());
			fier.setLastUpdateTm(new Timestamp(new Date().getTime()));
			check(fier,customerId,true);
			return super.save(fier);
		}else{
			AcrmFCiCustIdentifier fif = (AcrmFCiCustIdentifier) this.find(fier.getIdentId());
		    fif.setIdentType(fier.getIdentType());
		    fif.setIdentNo(fier.getIdentNo());
		    fif.setIdentCustName(fier.getIdentCustName());
		    fif.setIdenRegDate(fier.getIdenRegDate());
		    fif.setIdentExpiredDate(fier.getIdentExpiredDate());
		    fif.setIdentOrg(fier.getIdentOrg());
		    fif.setIdentCheckFlag(fier.getIdentCheckFlag());
			fif.setLastUpdateUser(auth.getUserId());
			fif.setLastUpdateTm(new Timestamp(new Date().getTime()));
			check(fier,customerId,false);
			return super.save(fif);
		}
	}

	//验证新增项目是否已经存在
	@SuppressWarnings("unchecked")
	public void check(AcrmFCiCustIdentifier fCiOrgIdentifier,String customerId,boolean flag){
		String jql = "";
		if(flag){//新增
			jql = "select f from AcrmFCiCustIdentifier f where f.custId = '"+customerId+"' " +
		        " and f.identType = '"+fCiOrgIdentifier.getIdentType()+"' " +
				" and f.identNo = '"+fCiOrgIdentifier.getIdentNo()+"'";
		}else{//修改
			jql = "select f from AcrmFCiCustIdentifier f where f.custId = '"+customerId+"' " +
		        " and f.identType = '"+fCiOrgIdentifier.getIdentType()+"' " +
				" and f.identNo = '"+fCiOrgIdentifier.getIdentNo()+"'" +
			    " and f.identId <> '"+fCiOrgIdentifier.getIdentId()+"'";
		}
		List<AcrmFCiCustIdentifier>  c = this.findByJql(jql, null);
		for(AcrmFCiCustIdentifier f : c){
			throw new BizException(1, 0, "1002", "客户的证件类型,证件号码 已经存在!");
		}
	}
	
	/**
	 * 对公证件信息新增、修改历史记录
	 * @param jarray 修改记录数组
	 * @param date 修改日期
	 * @param flag 修改标识
	 */
	public void bathsave(JSONArray jarray,Date date,String flag) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		@SuppressWarnings("unchecked")
		List<ReviewMapping>  list =this.findByJql("select c from ReviewMapping c where c.moduleItem='对公客户证件信息'", null);
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem")==null?"":String.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont")==null?"":String.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont")==null?"":String.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView")==null?"":String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId")==null?"":String.valueOf(wa.get("updateTableId")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updateItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(flag);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn("对公客户证件信息-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
		return;
	}
	
	/**
	 * 判断该客户信息是否在审批中()
	 * @param jobName
	 * @return
	 */
	public  int judgeSec(String jobName,String instanceid,String str){
		@SuppressWarnings("unchecked")
		int i = 0;
		Object[] objs = null;
		String rst = null;
		List list = this.em.createNativeQuery("select t.* from wf_worklist t where t.WFSTATUS <> '3' and t.instanceid like '%"+instanceid+"%' " +
				" and t.WFJOBNAME  like '%"+jobName+"%'").getResultList();
		for(int j=0;j<list.size();j++){
			objs = (Object[]) list.get(j);
			rst = objs[0]+"";
			if(rst.contains(str)){
				i = i+1;
			}
		}
		return i;
	}
}
