/**
 * 
 */
package com.yuchengtech.emp.biappframe.authobj.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.authobj.entity.BioneLogInfo;
import com.yuchengtech.emp.biappframe.authobj.web.vo.BioneLogInfoVO;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.util.ExDateUtils;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * 
 * 
 * <pre>
 * Title:
 * Description:
 * </pre>
 * 
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("logBS")
@Transactional(readOnly = true)
public class LogBS extends BaseBS<BioneLogInfo> {
	
	protected static Logger log = LoggerFactory.getLogger(LogBS.class);
	/*@Autowired
	private LogicSysBS logicSysBS;
	private UserBS userBS; */
	
	/**
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneLogInfo> getLogList(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap) {
		String jql = "select biLog from BioneLogInfo biLog WHERE biLog.logicSysNo=:logicSysNo ";

		Map<String, Object> values = (Map<String, Object>) conditionMap
		.get("params");
		if (!conditionMap.get("jql").equals("")) {
			String jqlForAdd=(String)conditionMap.get("jql");
			String paramName="";
			if(jqlForAdd.contains("endTime")){
				int i=jqlForAdd.indexOf("endTime");
				paramName=jqlForAdd.substring(i);
				paramName=paramName.substring(paramName.indexOf(":")+1);
				jqlForAdd=jqlForAdd.replace("endTime", "occurTime");
				
			}
			Object param2=values.get(paramName);
			if(param2!=null){
				Date tmp=new Date(ExDateUtils.addDays((Date)param2, 1).getTime()+1);
				values.put(paramName, tmp);
			}
			jql += " and " + jqlForAdd + " ";
		}

		if (!StringUtils.isEmpty(orderBy)) {
			jql += "order by biLog." + orderBy + " " + orderType;
		}

		
		
		values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());

		SearchResult<BioneLogInfo> roleList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql, values);

		return roleList;
	}

	public SearchResult<BioneLogInfoVO> changeIdToNameList(SearchResult<BioneLogInfo> logResult){
		SearchResult<BioneLogInfoVO>  returnResult=new SearchResult<BioneLogInfoVO>();
		List<BioneLogInfo> logList=logResult.getResult();
		List<BioneLogInfoVO> returnList=Lists.newArrayList();
		for(BioneLogInfo log:logList){
			BioneLogInfoVO logInfo=new BioneLogInfoVO();
			BioneLogicSysInfo sysInfo=this.getEntityByProperty(BioneLogicSysInfo.class, "logicSysNo", log.getLogicSysNo());
			BioneUserInfo user=this.getEntityById(BioneUserInfo.class, log.getOperUser());
			logInfo.setLogEvent(log.getLogEvent());
			if(sysInfo!=null){
				logInfo.setLogicSysNo(sysInfo.getLogicSysName());
			}else{
				logInfo.setLogicSysNo(log.getLogicSysNo());
			}
			logInfo.setLogNo(log.getLogNo());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=sdf.format(log.getOccurTime());
			logInfo.setOccurTime(date);
			if(user!=null){
				logInfo.setOperUser(user.getUserName());
			}else{
				logInfo.setOperUser(log.getOperUser());
			}
			logInfo.setLoginIP(log.getLoginIP());
			returnList.add(logInfo);
		}
		returnResult.setResult(returnList);
		returnResult.setTotalCount(logResult.getTotalCount());
		return returnResult;
	}
	
	@Transactional(readOnly=false)
	public void addLog(String ip,String logicSysNo,String operUser,String event){
		BioneLogInfo logInfo = new BioneLogInfo();
		logInfo.setLogNo(RandomUtils.uuid2());
		logInfo.setLogicSysNo(logicSysNo);
		logInfo.setOperUser(operUser);
		logInfo.setLoginIP(ip);
		logInfo.setOccurTime(new Timestamp(System.currentTimeMillis()));
		logInfo.setLogEvent(event);
		this.saveEntity(logInfo);
	}
}
