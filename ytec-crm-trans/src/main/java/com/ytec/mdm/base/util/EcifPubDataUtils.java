/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：EcifPubDataUtils.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:08:29
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.InfoGrantCtrl;
import com.ytec.mdm.base.bo.InfoGrantObjectModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.NameUtil;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：EcifPubDataUtils
 * @类描述：公共数据加载
 * @功能描述:用于从数据库中加载公共配置数据
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:08:50
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:08:50
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class EcifPubDataUtils {

	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	private static Logger log = LoggerFactory.getLogger(EcifPubDataUtils.class);

	/**
	 * The info grant object model map.
	 * 
	 * @属性描述:信息分级授权
	 */
	private static ConcurrentHashMap<InfoGrantObjectModel, InfoGrantCtrl> infoGrantObjectModelMap = new ConcurrentHashMap<InfoGrantObjectModel, InfoGrantCtrl>();
	/**.
	 * 
	 * @属性描述:码值属性集合
	 */
	public static Set<String> ecifTableCode = new HashSet<String>();

	/**
	 * The src to ecif code map.
	 * 
	 * @属性描述:原系统编码转ECIF标准码**.
	 */
	public static Map<String, String> srcToEcifCodeMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * @属性描述:ECIF标准码转原系统编码**.
	 */
	public static Map<String, String> ecifToSrcCodeMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * 
	 * @属性描述:ECIF标准码
	 */
	public static Map<String, String> ecifStdCodeMap = new ConcurrentHashMap<String, String>();

	/**
	 * @构造函数
	 */
	public EcifPubDataUtils() {
	}

	/**
	 * ECIF信息级别分类.
	 * 
	 * @param authType
	 *            授权类型
	 * @param authCode
	 *            授权码
	 * @param ctrlType
	 *            控制分类
	 * @param tabId
	 *            ECIF表ID
	 * @param colId
	 *            ECIF字段ID
	 * @return boolean false：没权限 true：有权限
	 * @算法描述:
	 */
	public static boolean infoLevelRead(String authType, String authCode,
			String ctrlType, Long tabId, Long colId) {
		if (!MdmConstants.globalTxInfoCtrl) {
			return true;
		}
		if (authType == null || authCode == null) {
			return false;
		}
		if (tabId == null || colId == null) {
			return true;
		}
		if (MdmConstants.isLoadtoMem) {
			InfoGrantObjectModel selectAuth = new InfoGrantObjectModel(
					authType, authCode, ctrlType, tabId, colId);
			InfoGrantCtrl level = infoGrantObjectModelMap.get(selectAuth);
			if (level == null) {
				return false;
			} else {
				if (level.getEndDate() == null
						|| level.getEndDate().after(new Date())) {// 确定比较规则
					return true;
				} else {
					return false;
				}
			}
		} else {
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			StringBuffer jql = new StringBuffer("");
			jql.append("SELECT C.END_DATE FROM TX_INFO_GRANT_OBJECT O,TX_INFO_CTRL C,TX_INFO_ITEM M where O.GRANT_OBJECT_ID=C.GRANT_OBJECT_ID AND C.INFO_ITEM_ID=M.INFO_ITEM_ID");
			jql.append(" and  O.OBJECT_TYPE=? and O.OBJECT_CODE=? and C.CTRL_TYPE=? and M.TAB_ID=? and M.COL_ID=?");
			List<Object> objList = baseDAO.findByNativeSQLWithIndexParam(jql
					.toString(),authType,authCode,ctrlType,tabId,colId);
			if (objList != null && !objList.isEmpty()) {
				if (objList.get(0) == null
						|| ((Date) objList.get(0)).after(new Date())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		}

	}


	public static boolean isCodeTableColumn(String tableEntityName,String columnAttr) {
		return ecifTableCode.contains(tableEntityName+"_"+columnAttr);
	}

	/**
	 * ECIF标准码转原系统编码.
	 * 
	 * @param srcSysCd
	 *            源系统号
	 * @param srcCode
	 *            源系统代码
	 * @param stdCate
	 *            标准码分类
	 * @return String
	 * 
	 *         **
	 */
	public static String getEcifCode(String srcSysCd, String srcCode,
			String stdCate) {
		String val = null;
		if (MdmConstants.isLoadtoMem) {
			val = srcToEcifCodeMap
					.get(srcSysCd + "_" + srcCode + "_" + stdCate);
		}
		if (val == null) {
			try {
				JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
						.getBean("baseDAO");
				List<Object> result = baseDAO.findByNativeSQLWithIndexParam("SELECT STD_CODE FROM TX_CODE_MAP WHERE SRC_SYS_CD=? AND SRC_CODE=? AND STD_CATE=?"
						,srcSysCd,srcCode,stdCate);
				if (result != null && result.size() > 0) {
					val = (String) result.get(0);
					if (MdmConstants.isLoadtoMem) {
						srcToEcifCodeMap.put(srcSysCd + "_" + srcCode + "_"
								+ stdCate, val);
					}
				}
			} catch (Exception e) {

			}
		}
		return val;
	}

	/**
	 * ECIF标准码转原系统编码.
	 * 
	 * @param srcSysCd
	 *            源系统号
	 * @param stdCode
	 *            ECIF代码
	 * @param stdCate
	 *            标准码分类
	 * @return String
	 * 
	 *         **
	 */

	public static String getSrcCode(String srcSysCd, String stdCode,
			String stdCate) {
		String val = null;
		if (MdmConstants.isLoadtoMem) {
			val = ecifToSrcCodeMap
					.get(srcSysCd + "_" + stdCode + "_" + stdCate);
		}
		if (val == null) {
			try {
				JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
						.getBean("baseDAO");
				List<Object> result = baseDAO.findByNativeSQLWithIndexParam("SELECT SRC_CODE FROM TX_CODE_MAP WHERE SRC_SYS_CD=? AND STD_CODE=? AND STD_CATE=?",srcSysCd,stdCode,stdCate);
				if (result != null && result.size() > 0) {
					val = (String) result.get(0);
					if (MdmConstants.isLoadtoMem) {
						ecifToSrcCodeMap.put(srcSysCd + "_" + stdCode + "_"
								+ stdCate, val);
					}
				}
			} catch (Exception e) {

			}
		}
		return val;
	}

	/**
	 * Gets the std code des.
	 * 
	 * @param stdCode
	 *            the std code
	 * @param stdCate
	 *            the std cate
	 * @return the std code des
	 */
	public static String getStdCodeDes(String stdCode, String stdCate) {
		try {
			String stdCodeDesc = null;
			if (MdmConstants.isLoadtoMem) {
				if ((stdCodeDesc = ecifStdCodeMap.get(stdCate + "_" + stdCode)) != null) {
					return stdCodeDesc;
				}
			}
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			List<Object> result = baseDAO.findByNativeSQLWithIndexParam("SELECT STD_CODE_DESC FROM TX_STD_CODE WHERE STD_CODE=? AND STD_CATE=?",stdCode,stdCate);
			if (result != null && result.size() > 0) {
				stdCodeDesc = result.get(0).toString();
				if (MdmConstants.isLoadtoMem) {
					ecifStdCodeMap.put(stdCate + "_" + stdCode, stdCodeDesc);
				}
				return stdCodeDesc;
			}
		} catch (Exception e) {
			log.error("验证是否为ECIF标准码,", e);
		}
		return null;
	}

	/**
	 * *** 验证是否为ECIF标准码.
	 * 
	 * @param stdCode
	 *            码值
	 * @param stdCate
	 *            码值分类
	 * @return boolean
	 * @函数名称:boolean isEcifStdCode(String stdCode, String stdCate)
	 * @函数描述:
	 * @参数与返回说明: boolean isEcifStdCode(String stdCode, String stdCate)
	 * @算法描述:
	 */
	public static boolean isEcifStdCode(String stdCode, String stdCate) {
		try {
			if (MdmConstants.isLoadtoMem) {
				if (ecifStdCodeMap.get(stdCate + "_" + stdCode) != null) {
					return true;
				}
			}
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			List<Object> result = baseDAO
					.findByNativeSQLWithIndexParam(
							"SELECT STD_CODE_DESC FROM TX_STD_CODE WHERE STD_CODE=? AND STD_CATE=?",
							stdCode, stdCate);
			if (result != null && result.size() > 0) {
				String stdCodeDesc = result.get(0).toString();
				if (MdmConstants.isLoadtoMem) {
					ecifStdCodeMap.put(stdCate + "_" + stdCode, stdCodeDesc);
				}
				return true;
			}
		} catch (Exception e) {
			log.error("验证是否为ECIF标准码", e);
		}
		return false;
	}

}
