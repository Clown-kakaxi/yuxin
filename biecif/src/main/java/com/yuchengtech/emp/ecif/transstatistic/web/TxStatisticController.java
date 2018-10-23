package com.yuchengtech.emp.ecif.transstatistic.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.transaction.entity.TxLog;
import com.yuchengtech.emp.ecif.transstatistic.service.TxStatisticBS;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成表的CRUD操作 
 * </pre>	
 * @author shangjf  shangjf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：尚吉峰		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/transstatistic")
public class TxStatisticController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxStatisticController.class);

	@Autowired
	private TxStatisticBS txStatisticBS;

	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/transstatistic/stattistic-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TxLog> searchResult = this.txStatisticBS.getTxStatisticList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Total", searchResult.getTotalCount());
		
		return resDefMap;
	}
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/bytxcode",method = RequestMethod.GET)
	public String bytxcode() {
		return "/ecif/transstatistic/stattistic-index";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/bytxcodelist.*")
	@ResponseBody
	public Map<String, Object> listbytxcode(Pager pager) {
		
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_TXCODE",new String[]{"TX_DATE","TX_CODE","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/byresult",method = RequestMethod.GET)
	public String byresult() {
		return "/ecif/transstatistic/stattistic-byresult";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/byresultlist.*")
	@ResponseBody
	public Map<String, Object> listbyresult(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_RESULT",new String[]{"TX_DATE","TX_RESULT","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	
	
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/byrtncode",method = RequestMethod.GET)
	public String byrtncode() {
		return "/ecif/transstatistic/stattistic-byrtncode";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/byrtncodelist.*")
	@ResponseBody
	public Map<String, Object> listbyrtncode(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_RTNCODE",new String[]{"TX_DATE","TX_RTN_CODE","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	

	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/bytimecosume",method = RequestMethod.GET)
	public String bytimeconsume() {
		return "/ecif/transstatistic/stattistic-bytimeconsume";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/bytimeconsumelist.*")
	@ResponseBody
	public Map<String, Object> listbytimeconsume(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_TIME_CONSUME",new String[]{"TX_DATE","TX_DEAL_TIME","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/bytxcate",method = RequestMethod.GET)
	public String bytxcate() {
		return "/ecif/transstatistic/stattistic-bytxcate";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/bytxcatelist.*")
	@ResponseBody
	public Map<String, Object> listbytxcate(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_TXCATE",new String[]{"TX_DATE","TX_KIND","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/bychannel",method = RequestMethod.GET)
	public String bychannel() {
		return "/ecif/transstatistic/stattistic-bychannel";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/bychannellist.*")
	@ResponseBody
	public Map<String, Object> listbychannel(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_CHANNEL",new String[]{"TX_DATE","TX_CHANNEL","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/bycusttype",method = RequestMethod.GET)
	public String bycusttype() {
		return "/ecif/transstatistic/stattistic-bycusttype";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/bycusttypelist.*")
	@ResponseBody
	public Map<String, Object> listbycusttype(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_CUST_TYPE",new String[]{"TX_DATE","TX_CUST_TYPE","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	/**
	 * 菜单url
	 * @return
	 */
	@RequestMapping(value = "/bysys",method = RequestMethod.GET)
	public String bysys() {
		return "/ecif/transstatistic/stattistic-bysys";
	}	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value = "/bysyslist.*")
	@ResponseBody
	public Map<String, Object> listbysys(Pager pager) {
				
		Object[] sqlParams = getSqlparams(pager);
		if(sqlParams==null) return null;
		
		String startDate = getStartdateStr(pager);
		String endDate = getEnddateStr(pager);
		
		String sql = getSql(startDate,endDate,"TX_STAT_BY_SYS",new String[]{"TX_DATE","TX_SYS","TX_COUNT"});
		List<Map> list = txStatisticBS.getTxMsgCheckInfo(sql,sqlParams);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		
		return resDefMap;
	}
	
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxLog show(@PathVariable("id") Long id) {
		TxLog model = this.txStatisticBS.getEntityById(id);
		return model;
	}
	
	
	public Object[] getSqlparams(Pager pager){
		
		Map<String,Object> condition = pager.getSearchCondition();
		Map<String, String> params = (Map)condition.get("params");
		String startDate = params.get("param0");
		String endDate = params.get("param1");
		
		Object[] sqlParams = new Object[]{startDate,endDate};
		
		if(startDate==null && endDate==null ){
			sqlParams = null;
			
		}else if(startDate!=null && endDate==null){
			sqlParams = new Object[]{startDate};
			
		}else if(startDate==null && endDate!=null){
			sqlParams = new Object[]{endDate};
			
		}else if( endDate.equals(startDate)){
			sqlParams = new Object[]{startDate};
		}else{

		}
		
		return sqlParams;
	}
	
	public String getStartdateStr(Pager pager){
		

		Map<String,Object> condition = pager.getSearchCondition();
		Map<String, String> params = (Map)condition.get("params");
		
		String startDate = params.get("param0");

		return startDate;
	}
	
	public String getEnddateStr(Pager pager){
		
		
		Map<String,Object> condition = pager.getSearchCondition();
		Map<String, String> params = (Map)condition.get("params");
		
		String endDate = params.get("param1");

		return endDate;
	}
	
	
	public Date getStartdate(Pager pager){
		
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		
		Map<String,Object> condition = pager.getSearchCondition();
		Map<String, String> params = (Map)condition.get("params");
		
		String startDate = params.get("param0");
		
		if(startDate==null) 
			return null;
		
		try {
			date = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}
	
	public Date getEnddate(Pager pager){
		
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		
		Map<String,Object> condition = pager.getSearchCondition();
		Map<String, String> params = (Map)condition.get("params");
		String endDate = params.get("param1");

		if(endDate==null) 
			return null;
				
		try {
			date = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}
	
	/**
	 * 拼接查询sql语句
	 * @param startDate
	 * @param endDate
	 * @param table
	 * @param cols
	 * @return
	 */
	public String getSql(String startDate,String endDate,String table ,String[] cols){
		
		String sql ="";
		
		if(startDate==null && endDate==null ){
			sql="select "+cols[0] +","+cols[1] +" as label,"+cols[2] +" as value from "+ table+" t  order by t.tx_date desc ";			
		}
		else if( (startDate!=null && endDate==null)||(startDate==null && endDate!=null)|| (endDate.equals(startDate))){
			sql="select "+cols[0] +","+cols[1] +" as label,"+cols[2] +" as value from "+ table+" t where to_char("+cols[0] +",'YYYY-MM-DD')=?0 order by t."+cols[0] +" desc ";
		}else{//"+ startDate+"~"+ endDate +"
			sql="select '' as "+cols[0] +","+cols[1] +" as label,sum("+cols[2] +") as value from "+ table+" t where to_char("+cols[0] +",'YYYY-MM-DD')>=?0 and to_char("+cols[0] +",'YYYY-MM-DD')<=?1 group by "+cols[1] +" order by "+cols[1] +" asc ";
		}
		
		return sql;
	}
	
}
