package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * ECIF开户复核流程状态查询
 * @author wx
 *
 */
@Service
public class CustRecheckInfoService  extends CommonService{

	private static Logger log = LoggerFactory.getLogger(CustRecheckInfoService.class);
	public CustRecheckInfoService() {
		JPABaseDAO<AcrmFCiCustomer, String> baseDAO = new JPABaseDAO<AcrmFCiCustomer, String>(AcrmFCiCustomer.class);
		super.setBaseDAO(baseDAO);
	}
	
	/*public Map<String, Object> custRecheckInfo(String logId, String custId){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String flag = "00";
		String msg = "系统错误";
		if(StringUtils.isEmpty(custId)){
			msg = "没有ECIF客户号，无法查询流程信息";
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("flag", flag);
			retMap.put("msg", msg);
			return retMap;
		}
		//查询流程信息
		//流程id规则：customer_ + 客户号--customer_210004073490
		String instanceid = "customer_" + custId;//
		try {
			String sql_instanceInfo = "select t.* from wf_node_action_record t where t.instanceid = '"+instanceid+"'";
			List l_instanceInfo = this.baseDAO.findByNativeSQLWithIndexParam(sql_instanceInfo, null);
			if(l_instanceInfo != null && l_instanceInfo.size() >= 1){
				flag = "01";
				msg = "流程正在审批中，请勿关闭当前页面...";
				//log.info(msg);
				retMap.put("status", "success");
				retMap.put("flag", flag);
				retMap.put("msg", msg);
				return retMap;
			}
			String sql_instanceInfoEnd = "select t.NEXTNODEID from wf_node_action_recordend t where t.instanceid='"+instanceid+"' order by t.ACTTIME desc";
			List<Object[]> l_instanceInfoEnd = this.baseDAO.findByNativeSQLWithIndexParam(sql_instanceInfoEnd, null);
			if(l_instanceInfoEnd != null && l_instanceInfoEnd.size() >= 1){
				Object res = l_instanceInfoEnd.get(0);
				if(res == null){
					flag = "02";
					msg = "流程已结束，请执行下一步";
					//log.info(msg);
					retMap.put("status", "success");
					retMap.put("flag", flag);
					retMap.put("msg", msg);
					return retMap;
				}else{
					String lastNodeId = res.toString();
					if(lastNodeId != null && lastNodeId.toString().equals("WFEND")){
						flag = "03";
						msg = "流程已被撤销，请联系相关流程办理人";
						//log.info(msg);
						retMap.put("status", "success");
						retMap.put("flag", flag);
						retMap.put("msg", msg);
						return retMap;
					}
				}
			}else{
				flag = "04";
				msg = "尚未提交流程";
				//log.info(msg);
				retMap.put("status", "success");
				retMap.put("flag", flag);
				retMap.put("msg", msg);
				return retMap;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "逻辑处理错误，请联系管理员";
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		return retMap;
	}*/
	
	/**
	 * 判断流程的状态
	 * @param logId
	 * @param custId
	 * @return
	 */
	public Map<String, Object> checkFlowStatus(String custId,String instanceid){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String flag = "00";
		String msg = "没有开户";
		if(StringUtils.isEmpty(custId)){
			msg = "没有ECIF客户号，无法查询流程信息";
			log.debug(msg);
			retMap.put("status", "success");
			retMap.put("flag", flag);
			retMap.put("msg", msg);
			return retMap;
		}
		//查询流程信息
		//流程id规则：customer_ + 客户号--customer_210004073490
		//String instanceid = "customer_" + custId;//
		try {
			StringBuilder sql_instanceInfo = new StringBuilder();
			sql_instanceInfo.append("select t.instanceid,t.sendto,m.user_name from wf_node_action_record t ")
			.append(" left join admin_auth_account m on t.sendto = m.account_name ")
			.append(" where t.instanceid like 'cus_"+custId+"_%'");
			List<Object[]> l_instanceInfoList = this.baseDAO.findByNativeSQLWithIndexParam(sql_instanceInfo.toString(), null);
			if(l_instanceInfoList != null && l_instanceInfoList.size() >= 1){
				Object[] l_instanceInfo = l_instanceInfoList.get(0);
				if(l_instanceInfo != null && l_instanceInfo.length >0){
					flag = "01";
					msg = "流程正在审批中，审批人是"+l_instanceInfo[2].toString()+"["+l_instanceInfo[1].toString()+"]，请勿关闭当前页面...";
					log.debug(msg);	
					retMap.put("status", "success");
					retMap.put("sendtoAccountName", l_instanceInfo[2].toString());
					retMap.put("sendtoUserName", l_instanceInfo[1].toString());
					retMap.put("instanceid", l_instanceInfo[0].toString());
					retMap.put("flag", flag);
					retMap.put("msg", msg);
					return retMap;
				}
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append(" SELECT T.SPSTATUS FROM ");
				sb.append(" (SELECT A.INSTANCEID INSTANCEID,A.SPSTATUS SPSTATUS,A.WFENDTIME WFENDTIME FROM WF_INSTANCE_END A ");
				sb.append(" UNION SELECT B.INSTANCEID INSTANCEID,'5' SPSTATUS,B.ACTTIME WFENDTIME FROM WF_NODE_ACTION_RECORDEND B ");
				sb.append(" WHERE B.NEXTNODEID = 'WFEND' ORDER BY WFENDTIME DESC )T ");
				sb.append(" WHERE T.INSTANCEID = '"+instanceid+"' ");
				List tempRowList = this.baseDAO.findByNativeSQLWithIndexParam(sb.toString(), null);
				if(tempRowList == null || tempRowList.size() == 0){
					flag = "05";
					msg = "尚未提交复核流程";
					log.debug(msg);
					retMap.put("status", "success");
					retMap.put("flag", flag);
					retMap.put("msg", msg);
				}else{
					Object obj =  tempRowList.get(0);
					if(obj != null && !"".equals(obj.toString())){
						String spstatus = obj.toString();
						if(spstatus.equals("5")){//撤销
							flag = "04";
							msg = "流程已被撤销，请联系相关流程办理人";
							log.debug(msg);
							retMap.put("status", "success");
							retMap.put("flag", flag);
							retMap.put("msg", msg);
							return retMap;
						}else if(spstatus.equals("1")){//同意
							flag = "02";
							msg = "复核流程审批通过，请执行下一步";
							log.debug(msg);
							retMap.put("status", "success");
							retMap.put("flag", flag);
							retMap.put("msg", msg);
							return retMap;
						}else if(spstatus.equals("2")){//否决
							flag = "03";
							msg = "复核流程已被否决，请联系相关流程办理人";
							log.debug(msg);
							retMap.put("status", "success");
							retMap.put("flag", flag);
							retMap.put("msg", msg);
							return retMap;
						}
					}
					
				}
				
				
				/*StringBuilder sb = new StringBuilder();
				sb.append(" SELECT SPSTATUS FROM ( ");
				sb.append(" SELECT SPSTATUS FROM WF_INSTANCE_END ");
				sb.append(" WHERE INSTANCEID = '"+instanceid+"' ");
				if(nowFlowTime != null && !"".equals(nowFlowTime)){
					sb.append(" AND WFENDTIME >= '"+nowFlowTime+"' ");
				}
				sb.append(" ORDER BY WFENDTIME DESC) WHERE  ROWNUM <= 1 ");
				List tempRowList = this.baseDAO.findByNativeSQLWithIndexParam(sb.toString(), null);
				if(tempRowList == null || tempRowList.size() == 0){
					
					StringBuilder sql_instanceInfoEnd = new StringBuilder();
					sql_instanceInfoEnd.append(" select t.NEXTNODEID from wf_node_action_recordend t ");
					sql_instanceInfoEnd.append(" where t.instanceid='"+instanceid+"' " );
					if(nowFlowTime != null && !"".equals(nowFlowTime)){
						sql_instanceInfoEnd.append(" and t.ACTTIME >= '"+ nowFlowTime +"' ");
					}
					sql_instanceInfoEnd.append(" order by t.ACTTIME desc ");
					List<Object[]> l_instanceInfoEnd = 
							this.baseDAO.findByNativeSQLWithIndexParam(sql_instanceInfoEnd.toString(), null);
					if(l_instanceInfoEnd != null && l_instanceInfoEnd.size() >= 1){
						Object res = l_instanceInfoEnd.get(0);
						if(res != null){
							String lastNodeId = res.toString();
							if(lastNodeId != null && lastNodeId.toString().equals("WFEND")){
								flag = "04";
								msg = "流程已被撤销，请联系相关流程办理人";
								//log.info(msg);
								retMap.put("status", "success");
								retMap.put("flag", flag);
								retMap.put("msg", msg);
								return retMap;
							}
						}
					}
				}else{
					String spstatus = tempRowList.get(0).toString();
					if(spstatus.equals("1")){//同意
						flag = "02";
						msg = "复核流程审批通过，请执行下一步";
						//log.info(msg);
						retMap.put("status", "success");
						retMap.put("flag", flag);
						retMap.put("msg", msg);
						return retMap;
					}else if(spstatus.equals("2")){//否决
						flag = "03";
						msg = "复核流程已被否决，请联系相关流程办理人";
						//log.info(msg);
						retMap.put("status", "success");
						retMap.put("flag", flag);
						retMap.put("msg", msg);
						return retMap;
					}
				}*/
			}
			/*flag = "05";
			msg = "尚未提交复核流程";
			//log.info(msg);
			retMap.put("status", "success");
			retMap.put("flag", flag);
			retMap.put("msg", msg);*/
		} catch (Exception e) {
			e.printStackTrace();
			msg = "逻辑处理错误，请联系管理员";
			log.error(msg);
			retMap.put("status", "error");
			retMap.put("msg", msg);
			return retMap;
		}
		return retMap;
	}
	
	
	public Map<String, Object> getWkFlowStatus(String logId, String instanceid){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String sql_instance = "select * from wf_node_action_recordend where instanceid='"+instanceid+"'";
			List l_instanceInfoEnd = this.baseDAO.findByNativeSQLWithIndexParam(sql_instance, null);
			if(l_instanceInfoEnd != null && l_instanceInfoEnd.size() >= 1){
				retMap.put("flag", "00");
			}else{
				retMap.put("flag", "01");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
}
