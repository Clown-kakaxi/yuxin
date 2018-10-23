package com.yuchengtech.bcrm.customer.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description 个人账户变更申请书Service 
 * @author denghj
 * @since 2015-12-11
 */

@Service
public class PrivateApplyInfoService extends CommonService{
	
	private static Logger log = Logger.getLogger(PrivateApplyInfoService.class);
	
	public PrivateApplyInfoService(){
        JPABaseDAO<OcrmFCiCustinfoUphi, Long> baseDao = new JPABaseDAO<OcrmFCiCustinfoUphi, Long>(OcrmFCiCustinfoUphi.class);
        super.setBaseDAO(baseDao);
    }
	/**
	 * 判断该客户信息是否正在走流程中
	 * @param jobName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int judgeExist(String instancePre){
		List list = this.em.createNativeQuery("SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
				+ instancePre
				+ "%'"
				+ " union  "
				+ " select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
				+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
				+ " where his.cust_id=substr('"+instancePre+"',instr ('"+instancePre+"','_')+1) and his.appr_flag='0' ").getResultList();
		if(list != null && list.size() >0 && list.get(0) != null){
			throw new BizException(1, 0, "1002", "客户记录被锁定，操作员"+list.get(0));
		}
		return 1;
	} 
	
	 /**
     * 增加修改历史信息
     * @param jarray 具体修改项
     * @param date 修改日期
     * @param flag 修改标识   毫秒级日期long
     * @param type 修改类型 
     */
	public void bathsave(JSONArray jarray,Date date,String flag,String type) {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String currenUserId = auth.getUserId();
		@SuppressWarnings("unchecked")
		List<ReviewMapping>  list = this.em.createQuery("select c from ReviewMapping c where c.moduleItem='"+type+"' order by c.tableName,c.pageColumn").getResultList();
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem")==null?"":String.valueOf(wa.get("updateItem")));
				ws.setUpdatePageItemEn(wa.get("updatePageItemEn") == null?"":String.valueOf(wa.get("updatePageItemEn")));
				ws.setUpdateBeCont(wa.get("updateBeCont")==null?"":String.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont")==null?"":String.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView")==null?"":String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId")==null?"":String.valueOf(wa.get("updateTableId")));
				
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updatePageItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());//修改项目字段名替换为表中字段名
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(flag);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn(type + "-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
	}
	
}
