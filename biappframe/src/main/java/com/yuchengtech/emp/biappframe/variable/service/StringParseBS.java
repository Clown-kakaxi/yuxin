package com.yuchengtech.emp.biappframe.variable.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDriverInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDsInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.variable.entity.BioneSysVarInfo;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;

/**
 * 字符串解析
 * 
 * <pre>
 * Title: 字符串解析
 * Description: 解析处于$间的字符的值，并生成解析后的新字符串。
 * </pre>
 * 
 * @author kangligong kanglg@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：		  修改日期:     修改内容:
 * </pre>
 */
@Service
public class StringParseBS extends BaseBS<BioneSysVarInfo> {
	// 占位符预设为'$'
	private String tag = "$";
	private String logicSysNO;

	/**
	 * 获取当前占位符
	 * 
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 修改占位符
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 解析字符串
	 * 
	 * @param str
	 * @return
	 */
	public String getResult(String str) {
		String s = new String(str);
		String flag = StringUtils.substringBetween(s, tag);
		List<String> strList = Lists.newArrayList();
		while (flag != null) {
			strList.add(flag);
			s = StringUtils.replace(s, tag + flag + tag, "");
			flag = StringUtils.substringBetween(s, tag);
		}
		Map<String, String> valMap = getValueMap(strList);
		for (String val : valMap.keySet()) {
			str = StringUtils.replace(str, tag + val + tag, valMap.get(val));
		}
		return str;
	}
	
	/**
	 * 解析字符串
	 * 
	 * @param str
	 * @param logSysNo
	 * @return
	 */
	public String getResult(String str,String logSysNo){
		String s = new String(str);
		String flag = StringUtils.substringBetween(s, tag);
		List<String> strList = Lists.newArrayList();
		while (flag != null) {
			strList.add(flag);
			s = StringUtils.replace(s, tag + flag + tag, "");
			flag = StringUtils.substringBetween(s, tag);
		}
		Map<String, String> valMap = getValueMap(strList,logSysNo);
		for (String val : valMap.keySet()) {
			str = StringUtils.replace(str, tag + val + tag, valMap.get(val));
		}
		return str;
	}

	/**
	 * 通过变量集合获取其变量-值的Map
	 * 
	 * @param strList
	 * @return
	 */
	public Map<String, String> getValueMap(List<String> strList) {
		this.logicSysNO = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		Map<String, String> strMap = Maps.newHashMap();
		Map<String, List<BioneSysVarInfo>> dsMap = Maps.newHashMap();
		for (String str : strList) {
			String jql = "select info from BioneSysVarInfo info where info.logicSysNo=?0 and info.varNo=?1";
			List<BioneSysVarInfo> infoList = this.baseDAO.findWithIndexParam(
					jql, logicSysNO, str);
			if (infoList != null && infoList.size() > 0) {
				BioneSysVarInfo info = infoList.get(0);
				String varType = info.getVarType();
				if (GlobalConstants.BIONE_SYS_VAR_TYPE_CONSTANT.equals(varType)) {
					strMap.put(str, info.getVarValue());
				} else if (GlobalConstants.BIONE_SYS_VAR_TYPE_SQL
						.equals(varType)) {
					String dsId = info.getDsId();
					if (dsId != null) {
						if (dsMap.containsKey(dsId)) {
							List<BioneSysVarInfo> objList = dsMap.get(dsId);
							objList.add(info);
						} else {
							List<BioneSysVarInfo> objList = Lists
									.newArrayList();
							objList.add(info);
							dsMap.put(dsId, objList);
						}
					}
				}
			}
		}
		for (String key : dsMap.keySet()) {
			BioneDsInfo ds = this.getEntityById(BioneDsInfo.class, key);
			if (ds != null) {
				BioneDriverInfo driver = this.getEntityById(
						BioneDriverInfo.class, ds.getDriverId());
				if (driver != null) {
					String url = ds.getConnUrl();
					String usrId = ds.getConnUser();
					String passwd = ds.getConnPwd();
					String driverName = driver.getDriverName();
					if (driverName != null && !"".equals(driverName)
							&& url != null && !"".equals(url) && usrId != null
							&& !"".equals(usrId)) {
						Connection conn = null;
						ResultSet rs = null;
						Statement state = null;
						List<BioneSysVarInfo> objList = dsMap.get(key);
						try {
							Class.forName(driverName);
							conn = DriverManager.getConnection(url, usrId,
									passwd);
							state = conn.createStatement();
							for (BioneSysVarInfo obj : objList) {
								String sql = obj.getVarValue();
								rs = this.doSQL(state, sql, rs);
								if (rs != null
										&& rs.getMetaData().getColumnCount() == 1
										&& rs.next()) {
									int rowCount = rs.getRow();
									if (rowCount == 1) {
										String result = rs.getString(1);
										if (!rs.next()) {
											strMap.put(obj.getVarNo(), result);
										}
									}
								}
							}
						} catch (ClassNotFoundException e) {
						} catch (SQLException e) {
						} finally {
							try {
								rs.close();
								state.close();
								conn.close();
							} catch (SQLException e) {
							}
						}
					}
				}
			}
		}
		return strMap;
	}
	
	/**
	 * 通过变量集合获取其变量-值的Map
	 * 
	 * @param strList
	 * @param logSysNo
	 * @return
	 */
	public Map<String, String> getValueMap(List<String> strList, String logSysNo) {
		Map<String, String> strMap = Maps.newHashMap();
		Map<String, List<BioneSysVarInfo>> dsMap = Maps.newHashMap();
		for (String str : strList) {
			String jql = "select info from BioneSysVarInfo info where info.logicSysNo=?0 and info.varNo=?1";
			List<BioneSysVarInfo> infoList = this.baseDAO.findWithIndexParam(
					jql, logSysNo, str);
			if (infoList != null && infoList.size() > 0) {
				BioneSysVarInfo info = infoList.get(0);
				String varType = info.getVarType();
				if (GlobalConstants.BIONE_SYS_VAR_TYPE_CONSTANT.equals(varType)) {
					strMap.put(str, info.getVarValue());
				} else if (GlobalConstants.BIONE_SYS_VAR_TYPE_SQL
						.equals(varType)) {
					String dsId = info.getDsId();
					if (dsId != null) {
						if (dsMap.containsKey(dsId)) {
							List<BioneSysVarInfo> objList = dsMap.get(dsId);
							objList.add(info);
						} else {
							List<BioneSysVarInfo> objList = Lists
									.newArrayList();
							objList.add(info);
							dsMap.put(dsId, objList);
						}
					}
				}
			}
		}
		for (String key : dsMap.keySet()) {
			BioneDsInfo ds = this.getEntityById(BioneDsInfo.class, key);
			if (ds != null) {
				BioneDriverInfo driver = this.getEntityById(
						BioneDriverInfo.class, ds.getDriverId());
				if (driver != null) {
					String url = ds.getConnUrl();
					String usrId = ds.getConnUser();
					String passwd = ds.getConnPwd();
					String driverName = driver.getDriverName();
					if (driverName != null && !"".equals(driverName)
							&& url != null && !"".equals(url) && usrId != null
							&& !"".equals(usrId)) {
						Connection conn = null;
						ResultSet rs = null;
						Statement state = null;
						List<BioneSysVarInfo> objList = dsMap.get(key);
						try {
							Class.forName(driverName);
							conn = DriverManager.getConnection(url, usrId,
									passwd);
							state = conn.createStatement();
							for (BioneSysVarInfo obj : objList) {
								String sql = obj.getVarValue();
								rs = this.doSQL(state, sql, rs);
								if (rs != null
										&& rs.getMetaData().getColumnCount() == 1
										&& rs.next()) {
									int rowCount = rs.getRow();
									if (rowCount == 1) {
										String result = rs.getString(1);
										if (!rs.next()) {
											strMap.put(obj.getVarNo(), result);
										}
									}
								}
							}
						} catch (ClassNotFoundException e) {
						} catch (SQLException e) {
						} finally {
							try {
								if (rs != null) {
									rs.close();
								}
								if (state != null) {
									state.close();
								}
								if (conn != null) {
									conn.close();
								}
							} catch (SQLException e) {
							}
						}
					}
				}
			}
		}
		return strMap;
	}

	private ResultSet doSQL(Statement st, String sql, ResultSet rs) {
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
		return rs;
	}
}
