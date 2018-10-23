package com.yuchengtech.emp.ecif.syncmanage.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.util.OIdUtils;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxEvtNotice;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxSyncConf;
import com.yuchengtech.emp.ecif.transaction.entity.TxDef;

/**
 * <pre>
 * Title: 手动数据同步
 * Description:
 * </pre>
 * 
 * @author Han Feng  hanfeng1@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class TxReSyncBS extends BaseBS<TxEvtNotice> {
	public static String EVENTDEALSTAT_WAIT="1";  //事件待处理
	public static String EVENTDEALSTAT_OVER="0";  //事件完成处理
	/**
	 * 配置列表
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @param condition
	 * @return
	 */
	public SearchResult<TxEvtNotice> getSearchResult(int firstResult,
			int maxResult, Map<String, Object> condition) {
		
		String evtResultParam = null;
		boolean flag = false;
		if(condition.get("fieldValues")!=null){
			Map<String, Object> val = (Map<String, Object>) condition
					.get("fieldValues");
			if(val.get("t.eventDealResult")!=null){
				String result = val.get("t.eventDealResult").toString();
				if(!"000000".equals(result)){
					int i = condition.get("jql").toString().indexOf("t.eventDealResult =");
					evtResultParam =condition.get("jql").toString().substring(i+20, i+20+6);
					
					flag = true;
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) condition
				.get("params");
		
		String param = condition.get("jql") != null ? condition.get("jql")
				.toString() : "";
		
		if(flag){
			System.out.println(param);
					
			values.remove(evtResultParam);
			values.put(evtResultParam, "0");
			
			param = param.replace("t.eventDealResult =", "t.eventDealResult !=");
			//param.replaceAll('t.eventDealResult =', 't.eventDealResult !=");
		
		}
				
		StringBuffer jql = new StringBuffer(500);
		jql.append("select t from TxEvtNotice t where 1=1 and t.manualFlag='0'");
		if (!StringUtils.isEmpty(param)) {
			jql.append(" and ").append(param);
		}
		jql.append(" order by t.eventDealTime desc");
		SearchResult<TxEvtNotice> result = baseDAO.findPageWithNameParam(
				firstResult, maxResult, jql.toString(), values);
		return result;
	}
	
	/**
	 * 批量同步
	 * @param idLst   需要同步eventId列表
	 * @throws Exception
	 */
	@Transactional
	public void batchSync(List<Long> idLst) throws Exception {
		if (idLst == null || idLst.size() == 0) {
			throw new Exception("参数为空");
		}
		//逐一同步
		for(int i = 0 ; i < idLst.size(); i++ ){
			//通过id找到实体对象
			StringBuffer jql = new StringBuffer("");
			jql.append("select n from TxEvtNotice n where n.eventId = " + idLst.get(i));
			
			List<TxEvtNotice> objList =this.baseDAO.findWithNameParm(jql.toString(), null);
			if(objList == null||objList.isEmpty()){
				throw new  Exception("找不到"+idLst.get(i)+"记录");
			}
			
			////?????	
			System.out.println(objList.get(0).getEventId());
			TxEvtNotice note = new TxEvtNotice();
			note = objList.get(0);
			//找到记录中有无不同id 的 相同 txCode,custId, eventSysNo, evenType, 和等待状态"1"下的 事件
            //注意！ 此处custId  需要根据实际情况该一下，因为交易那边代码还没把所有NO改成id
			List<TxEvtNotice> notices = this.baseDAO
					.findWithIndexParam(
							"select T from TxEvtNotice T where T.eventId!=?0 and T.txCode=?1 and T.custNo=?2 and T.eventSysNo=?3 and T.eventType=?4 and T.eventDealStat=?5",
							note.getEventId(),
							note.getTxCode(),
							note.getCustNo(),
							note.getEventSysNo(),
							note.getEventType(),
							this.EVENTDEALSTAT_WAIT);
			if (notices == null || notices.isEmpty()) {
				//创建手动同步事件
				TxEvtNotice txEvtNotice = new TxEvtNotice();
				txEvtNotice.setEventId(OIdUtils.getIdOfLong());
				txEvtNotice.setEventName("手动数据同步");
				txEvtNotice.setEventType("0");// 数据同步
				txEvtNotice.setEventDesc(note.getEventDesc());
				txEvtNotice.setEventTime(new Timestamp(System.currentTimeMillis()));
				txEvtNotice.setTxCode(note.getTxCode());
				//OIdUtils.setCustIdValue(txEvtNotice,ecifData.getCustId());
				txEvtNotice.setCustNo(note.getCustNo());
				txEvtNotice.setCustId(note.getCustId());
				txEvtNotice.setTxFwId(note.getTxFwId());
				txEvtNotice.setEventSysNo(note.getEventSysNo());
				txEvtNotice.setEventDealStat(this.EVENTDEALSTAT_WAIT);
				txEvtNotice.setManualEvtId(BigDecimal.valueOf(note.getEventId()));
				txEvtNotice.setManualFlag("1");
				//txEvtNotice.setManualStat("1");
				this.baseDAO.merge(txEvtNotice);
				
				//更改原事件状态
				note.setManualStat("1");
				this.baseDAO.persist(note);
			
			}else{
				throw new  Exception("事件"+note.getEventId()+"已存在于待同步队列！");
			}
		}
	}
	
}
