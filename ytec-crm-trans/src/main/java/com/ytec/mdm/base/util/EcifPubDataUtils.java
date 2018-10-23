/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.util
 * @�ļ�����EcifPubDataUtils.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:08:29
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�EcifPubDataUtils
 * @���������������ݼ���
 * @��������:���ڴ����ݿ��м��ع�����������
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:08:50
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:08:50
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifPubDataUtils {

	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(EcifPubDataUtils.class);

	/**
	 * The info grant object model map.
	 * 
	 * @��������:��Ϣ�ּ���Ȩ
	 */
	private static ConcurrentHashMap<InfoGrantObjectModel, InfoGrantCtrl> infoGrantObjectModelMap = new ConcurrentHashMap<InfoGrantObjectModel, InfoGrantCtrl>();
	/**.
	 * 
	 * @��������:��ֵ���Լ���
	 */
	public static Set<String> ecifTableCode = new HashSet<String>();

	/**
	 * The src to ecif code map.
	 * 
	 * @��������:ԭϵͳ����תECIF��׼��**.
	 */
	public static Map<String, String> srcToEcifCodeMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * @��������:ECIF��׼��תԭϵͳ����**.
	 */
	public static Map<String, String> ecifToSrcCodeMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * 
	 * @��������:ECIF��׼��
	 */
	public static Map<String, String> ecifStdCodeMap = new ConcurrentHashMap<String, String>();

	/**
	 * @���캯��
	 */
	public EcifPubDataUtils() {
	}

	/**
	 * ECIF��Ϣ�������.
	 * 
	 * @param authType
	 *            ��Ȩ����
	 * @param authCode
	 *            ��Ȩ��
	 * @param ctrlType
	 *            ���Ʒ���
	 * @param tabId
	 *            ECIF��ID
	 * @param colId
	 *            ECIF�ֶ�ID
	 * @return boolean false��ûȨ�� true����Ȩ��
	 * @�㷨����:
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
						|| level.getEndDate().after(new Date())) {// ȷ���ȽϹ���
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
	 * ECIF��׼��תԭϵͳ����.
	 * 
	 * @param srcSysCd
	 *            Դϵͳ��
	 * @param srcCode
	 *            Դϵͳ����
	 * @param stdCate
	 *            ��׼�����
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
	 * ECIF��׼��תԭϵͳ����.
	 * 
	 * @param srcSysCd
	 *            Դϵͳ��
	 * @param stdCode
	 *            ECIF����
	 * @param stdCate
	 *            ��׼�����
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
			log.error("��֤�Ƿ�ΪECIF��׼��,", e);
		}
		return null;
	}

	/**
	 * *** ��֤�Ƿ�ΪECIF��׼��.
	 * 
	 * @param stdCode
	 *            ��ֵ
	 * @param stdCate
	 *            ��ֵ����
	 * @return boolean
	 * @��������:boolean isEcifStdCode(String stdCode, String stdCate)
	 * @��������:
	 * @�����뷵��˵��: boolean isEcifStdCode(String stdCode, String stdCate)
	 * @�㷨����:
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
			log.error("��֤�Ƿ�ΪECIF��׼��", e);
		}
		return false;
	}

}
