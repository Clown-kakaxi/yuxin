package com.yuchengtech.emp.ecif.sysmonitor.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.sysmonitor.entity.TxServiceStatus;
@Service
@Transactional(readOnly = true)
public class TxServiceStatusBS extends BaseBS<TxServiceStatus> {
	protected static Logger log = LoggerFactory
			.getLogger(TxServiceStatusBS.class);
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxServiceStatus> getTxServiceStatusList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxServiceStatus from TxServiceStatus TxServiceStatus where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}

		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by " + orderBy + " " + orderType);
		}
		
		SearchResult<TxServiceStatus> TxServiceStatusList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxServiceStatusList;
	}
			
	public TxServiceStatus updateTxServiceStatus(TxServiceStatus txServiceStatus){
		StringBuffer jql = new StringBuffer("");
		jql.append("update TxServiceStatus "); 
		jql.append(" set ");
		if(txServiceStatus.getNodeID() !=null){
			jql.append("nodeID = "+txServiceStatus.getNodeID());
			jql.append(",");
		}
		if(txServiceStatus.getHostName() !=null){
			jql.append("hostName = '"+txServiceStatus.getHostName()+"'");
			jql.append(",");
		}
		if(txServiceStatus.getIpAddr() !=null){
			jql.append("ipAddr = '"+txServiceStatus.getIpAddr()+"'");
			jql.append(",");
		}
		if(txServiceStatus.getServiceName() !=null){
			jql.append("serviceName = '"+txServiceStatus.getServiceName()+"'");
			jql.append(",");
		}
		if(txServiceStatus.getInstName() !=null){
			jql.append("instName = '"+txServiceStatus.getInstName()+"'");
			jql.append(",");
		}
		if(txServiceStatus.getServicePort() !=null){
			jql.append("servicePort = "+txServiceStatus.getServicePort());
			jql.append(",");
		}
		if(txServiceStatus.getServiceStart() !=null){
			jql.append("serviceStart = '"+txServiceStatus.getServiceStart()+"'");
			jql.append(",");
		}
		if(txServiceStatus.getProcessID() !=null){
			jql.append("processID = "+txServiceStatus.getProcessID());
			jql.append(",");
		}
		if(txServiceStatus.getStartTime() !=null){
			jql.append("startTime = "+txServiceStatus.getStartTime());
			jql.append(","); 
		}
		if(txServiceStatus.getStopTime() !=null){
			jql.append("stopTime = "+txServiceStatus.getStopTime());
			jql.append(",");  
		}
		jql.append("serviceID = "+txServiceStatus.getServiceID());
		jql.append(" where serviceID="+txServiceStatus.getServiceID());
		log.info("执行:"+jql);
		this.baseDAO.batchExecuteWithNameParam(jql.toString(), null);
		return txServiceStatus;
	}

}
