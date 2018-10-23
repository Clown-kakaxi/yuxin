package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.customerView.service.CustomerQueryAllNewService;
import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对私客户视图（联系信息）
 * @author agile
 *
 */
@Service
public class AcrmFCiContmethInfoService extends CommonService {
	private static Logger log = Logger.getLogger(CustomerQueryAllNewService.class);
	
	public AcrmFCiContmethInfoService(){
		JPABaseDAO<AcrmFCiContmeth,String> baseDao = new JPABaseDAO<AcrmFCiContmeth,String>(AcrmFCiContmeth.class);
		super.setBaseDAO(baseDao);
	}
	
	public Object save(Object obj){
//		AcrmFCiContmeth contmeth = (AcrmFCiContmeth)obj;
		AcrmFCiContmeth contmeth = new AcrmFCiContmeth();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<AcrmFCiContmeth> thList = this.findByJql("select c from AcrmFCiContmeth c where c.custId = '"+customerId+"' " +
				" and c.stat = '1' " +
				" and c.contmethType = '"+contmeth.getContmethType()+"'", null);
		//根据Id是否为空判断是新增还是 更改
		if(contmeth.getContmethId()==null){
			contmeth.setLastUpdateUser(auth.getUsername());
			contmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
			contmeth.setStat("1");//1表示未删除 0标识删除
			//是否设置为首选项
			if(!thList.isEmpty()){
				for(AcrmFCiContmeth t:thList){
				   if(t.getContmethInfo().trim().equals(contmeth.getContmethInfo().trim())){
					   throw new BizException(0, 1, "1002", "联系人类型，联系人内容已经存在!");
				   }
				   if("1".equals(contmeth.getContmethType().substring(0, 1)) || "2".equals(contmeth.getContmethType().substring(0, 1))){
					   contmeth.setIsPriori("N");
				   }else{
					   contmeth.setIsPriori("9");//不是电话类的首选项为空
				   }
				}
			}else{
				if(!"1".equals(contmeth.getContmethType().substring(0, 1)) && !"2".equals(contmeth.getContmethType().substring(0, 1))){
					contmeth.setIsPriori("9");
				}else{
					contmeth.setIsPriori("Y");//是否首选项 1是0否
					//修改客户基本信息的联系号码
					super.save(contmeth);
					AcrmFCiPerson  person = (AcrmFCiPerson)em.find(AcrmFCiPerson.class, contmeth.getCustId());
					if(person!=null){
						if("100".equals(contmeth.getContmethType())){
							person.setMobilePhone(contmeth.getContmethInfo());
						}else if("203".equals(contmeth.getContmethType())){
							person.setUnitTel(contmeth.getContmethInfo());
						}else if("204".equals(contmeth.getContmethType())){
							person.setHomeTel(contmeth.getContmethInfo());
						}
						super.save(person);
					}
				}
			}
			contmeth.setLastUpdateUser(auth.getUsername());
			contmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
			contmeth.setLastUpdateSys("CRM");
			return super.save(contmeth);
		}else{
			List<AcrmFCiContmeth> list = this.findByJql("select c from AcrmFCiContmeth c where c.custId = '"+customerId+"' " +
					" and c.stat = '1' " +
					" and c.contmethId <> '"+contmeth.getContmethId()+"'" +
					" and c.contmethType = '"+contmeth.getContmethType()+"'", null);
			if(!list.isEmpty()){
				for(AcrmFCiContmeth t:list){
				   if(t.getContmethInfo().trim().equals(contmeth.getContmethInfo().trim())){
					   throw new BizException(0, 1, "1002", "联系人类型，联系人内容已经存在!");
				   }
				}
			}
			AcrmFCiContmeth contmeth2 = (AcrmFCiContmeth) this.find(contmeth.getContmethId());
			contmeth2.setContmethType(contmeth.getContmethType());
			contmeth2.setContmethInfo(contmeth.getContmethInfo());
			contmeth2.setRemark(contmeth.getRemark());
			
			contmeth2.setLastUpdateUser(auth.getUsername());
			contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
			contmeth2.setLastUpdateSys("CRM");
			return super.save(contmeth2);
		}
	}
	public void setPreference(AcrmFCiContmeth contmeth,String idStr){
		String jql1 = "update AcrmFCiContmeth c set c.isPriori = 'N' where c.custId = '"+contmeth.getCustId()+"'" +
		" and c.stat = '1' " +
		" and c.contmethType = '"+contmeth.getContmethType()+"'";
		super.batchUpdateByName(jql1, null);
		String jql = "update AcrmFCiContmeth c  set c.isPriori = 'Y' where c.contmethId in ('"
			+ idStr + "')";
		super.batchUpdateByName(jql, null);
		//更新客户基本信息联系号码
		updateCustBaseInfom(contmeth);
	}
	
	public  void updateCustBaseInfom(AcrmFCiContmeth contmeth){
		AcrmFCiPerson  person = (AcrmFCiPerson)em.find(AcrmFCiPerson.class, contmeth.getCustId());
		if(person!=null){
			if("100".equals(contmeth.getContmethType())){
				person.setMobilePhone(contmeth.getContmethInfo());
			}else if("203".equals(contmeth.getContmethType())){
				person.setUnitTel(contmeth.getContmethInfo());
			}else if("204".equals(contmeth.getContmethType())){
				person.setHomeTel(contmeth.getContmethInfo());
			}
			super.save(person);
		}
	}
	
	/**
	 * 对私客户基本信息调整历史2(3参)
	 * @param jarray
	 * @param date
	 * @param p
	 */
	public void bathsave(JSONArray jarray, Date date,String p) {
	    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String currenUserId = auth.getUserId();
		List<ReviewMapping>  list =this.findByJql("select c from ReviewMapping c where c.moduleItem='对私联系信息'", null);
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
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updateItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(p);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn("对私联系信息-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
		return;
	}
	
	/**
	 * 对私客户基本信息调整历史3(联系信息删除)
	 * @param messageId
	 * @param custId
	 * @param date
	 * @param p
	 */
	public void bathsave2(String messageId, String custId,Date date, String p) {
	    AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String currenUserId = auth.getUserId();
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String s=sdf.format(date);
	    Date dateTime = null;
		try {
			dateTime = sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
		ws.setCustId(custId);
		ws.setUpdateBeCont("delBE");
		ws.setUpdateAfCont(messageId);
		ws.setUpdateItem("delALL");
	    ws.setUpdateUser(currenUserId);
	    ws.setUpdateDate(dateTime);
	    ws.setUpdateFlag(p);
	    ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
	    super.save(ws);
			
		return;
	}
	
	/**
	 * 判断该客户信息是否在审批中()
	 * @param jobName
	 * @return
	 */
	public  int judge(String jobName,String instanceid){
		@SuppressWarnings("unchecked")
		List list = this.em.createNativeQuery("select t.* from wf_worklist t where t.WFSTATUS <> '3' and t.instanceid like '%"+instanceid+"%' " +
				" and t.WFJOBNAME  like '%"+jobName+"%'").getResultList();
		return list.size();
	}
	
	/**
	 * 判断该客户信息是否在审批中()
	 * @param jobName
	 * @return
	 */
	public  int judgeSec(String jobName,String instancePre,String str){
		List list = this.em.createNativeQuery("select t.* from wf_worklist t where t.WFSTATUS <> '3' and t.instanceid like '%"+instancePre+"%'").getResultList();
		if(list != null && list.size() >0){
			return 1;
		}
		return -1;
	}
}
