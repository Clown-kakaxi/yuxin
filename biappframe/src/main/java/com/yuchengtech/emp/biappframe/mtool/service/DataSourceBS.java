package com.yuchengtech.emp.biappframe.mtool.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDriverInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDsInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * 
 * 
 * <pre>
 * Title:
 * Description: 
 * </pre>
 * @author gaofeng  gaofeng5@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：高峰  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly=true)
public class DataSourceBS extends BaseBS<BioneDsInfo> {
	public SearchResult<BioneDsInfo> getList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		String sys = BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo();
		String str = "select ds from BioneDsInfo ds where ds.logicSysNo='"+sys+"'";
		//jql.append("select ds from BioneDsInfo ds where 1=1");
		jql.append(str);
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by ds." + orderBy + " " + orderType);
		}
		@SuppressWarnings("unchecked")
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneDsInfo> list = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(),
						values);
		return list;
	}
	//获取逻辑系统
	public List<BioneLogicSysInfo> getLogicSysData(){
		String jql="select logicsys from BioneLogicSysInfo logicsys where 1=?0 ";
		List<BioneLogicSysInfo> bioneLogicSysInfo=this.baseDAO.findWithIndexParam(jql, 1);
		return bioneLogicSysInfo;
	}
	//获取连接驱动
	public List<BioneDriverInfo> getDriverData(){
		String jql="select driver from BioneDriverInfo driver where 1=?0";
		List<BioneDriverInfo> bioneLogicSysInfo=this.baseDAO.findWithIndexParam(jql, 1);
		return bioneLogicSysInfo;
	}
	//获取URl
	public BioneDriverInfo getURLData(String driverId){
		String jql="select driver from BioneDriverInfo driver where driverId=?0";
		BioneDriverInfo bioneLogicSysInfo=this.baseDAO.findUniqueWithIndexParam(jql,driverId);
		return bioneLogicSysInfo;
	}
	//删除
	@Transactional(readOnly=false)
	public void removeDs(String dsId){
		String[] dsIds=dsId.split("/");
		for (int i = 0; i < dsIds.length; i++) {
			String sql="delete from BIONE_DS_INFO t where t.ds_id='"+dsIds[i]+"'";
			this.baseDAO.createNativeQueryWithIndexParam(sql, new Object[]{}).executeUpdate();
		}
		
		
	}
	//重名验证
	public List<BioneDsInfo> checkedDsName(String dsId,String dsName){
		List<BioneDsInfo> ds = Lists.newArrayList();
		if(!"".equals(dsId)&&dsId!=null){
			String jql = "select ds from BioneDsInfo ds where ds.dsId<>?0 and ds.dsName=?1";
			ds = this.baseDAO.findWithIndexParam(jql,dsId,dsName);
		}else{
			String jql1 = "select ds from BioneDsInfo ds where ds.dsName=?0";
			ds = this.baseDAO.findWithIndexParam(jql1,dsName);
		}
		return ds;
	}
}
