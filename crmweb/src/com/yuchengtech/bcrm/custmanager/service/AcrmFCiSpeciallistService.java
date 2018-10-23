package com.yuchengtech.bcrm.custmanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.custmanager.model.AcrmFCiSpeciallist;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class AcrmFCiSpeciallistService extends CommonService {
	
	public AcrmFCiSpeciallistService(){
			
		JPABaseDAO<AcrmFCiSpeciallist, Long>  baseDAO=new JPABaseDAO<AcrmFCiSpeciallist, Long>(AcrmFCiSpeciallist.class);  
		   super.setBaseDAO(baseDAO);
	}
	
	   public void bathsave(JSONArray jarray,Date date,String flag,String custId) {
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
		/*
		 * 提交审批前先调用保存（更新）操作
		 * 只更新ACRM_F_CI_SPECIALLIST表中APPROVAL_FLAG状态为2-待审批
		 */
		String jql = "update AcrmFCiSpeciallist set approvalFlag=:approvalFlag,lastUpdateUser=:lastUpdateUser,lastUpdateTm=:lastUpdateTm";
		jql += " where custId=:custId";
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("approvalFlag","2");
		values.put("lastUpdateUser",currenUserId);
		values.put("lastUpdateTm",dateTime);
		values.put("custId",custId);
		super.batchUpdateByName(jql, values);
		/*
		List<ReviewMapping>  list =this.findByJql("select c from ReviewMapping c where c.moduleItem='设为特殊名单客户'", null);
			if (jarray.size() > 0){
				for (int i = 0; i < jarray.size(); ++i){
					JSONObject wa = (JSONObject)jarray.get(i);
					OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
					ws.setCustId((String)wa.get("custId"));
					wa.get("updateBeCont");
					ws.setUpdateBeCont((String)wa.get("updateBeCont"));
					ws.setUpdateAfCont((String)wa.get("updateAfCont"));
					ws.setUpdateItem((String)wa.get("updateItem"));
					for(int k=0;k<list.size();k++){
						if(((String)wa.get("updateItemEn")).equals(list.get(k).getPageColumn())){
							ws.setUpdateItemEn(list.get(k).getOriginColumn());
							ws.setUpdateTable(list.get(k).getTableName());
						}
					}
					ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				    ws.setUpdateUser(currenUserId);
				    ws.setUpdateDate(dateTime);
				    ws.setUpdateFlag(flag);
				    super.save(ws);
				}
			}
			*/
			return;
	}
	
	@SuppressWarnings("rawtypes")
	public void saveSpe(AcrmFCiSpeciallist spe){
		String custId = spe.getCustId();
		/*
		 * 先根据custId查询主键（并以此判断是否有数据，有-更新；否则新增）
		 * 调整原因：界面提交时，也需要调用此方法
		 * 修改时间：2014-10-17
		 * 修改人：wuxl2
		 */
		String sql = "SELECT S.specialListId FROM AcrmFCiSpeciallist S WHERE S.custId='"+custId+"'";
		List list = super.em.createQuery(sql).getResultList();
		String specialListId = "";
		if(list != null && list.size() > 0){
			specialListId = list.get(0).toString();
			spe.setSpecialListId(specialListId);
		}
		//删除客户原特殊名单信息
//    	super.batchUpdateByName(" delete from AcrmFCiSpeciallist s where s.custId='"+custId+"'", new HashMap());
    	super.save(spe);
	}
	/**
	 * 判断流程是否在审核中
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int check(String name){
		List list = this.em.createNativeQuery("select t.* from wf_worklist t where t.WFSTATUS <> '3' and t.WFJOBNAME like '%"+name+"%'").getResultList();
		if(list != null && list.size() >0){
			return 1;
		}
		return -1;
	}
}
