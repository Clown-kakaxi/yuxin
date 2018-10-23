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
import com.ytec.mdm.base.dao.JvmIdDAO;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.domain.txp.TxSysParam;

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
	 * @��������:initPubData
	 * @��������:��ʼ�����ݿ�������Ϣ
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public void initPubData() {
		log.info("��ʼ�����ݿ�������Ϣ");
		/*** ������Ϣ�ּ�ӳ��� **/
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**����ϵͳJVM ID***/
		List<TxSysParam> jvmList=baseDAO.findWithIndexParam("from TxSysParam where paramName=?", MdmConstants.TXSYSPARAMNAME);
		int jvmId=1;
		TxSysParam txSysParam=null;
		if(jvmList!=null && !jvmList.isEmpty()){
			txSysParam=(TxSysParam)jvmList.get(0);
			if(!StringUtil.isEmpty(txSysParam.getParamValue())){
				jvmId=Integer.valueOf(txSysParam.getParamValue());
				jvmId=jvmId%80;
				if(jvmId==0){
					jvmId++;
				}
			}
		}
		MdmConstants.SYSTEMJVMID=jvmId;
		JvmIdDAO jvmIdDAO=(JvmIdDAO)SpringContextUtils.getBean("jvmIdDAO");
		jvmIdDAO.updateJvmValue(txSysParam, jvmId+1);
		////////////////////////////
		if (MdmConstants.isLoadtoMem) {
			if (MdmConstants.globalTxInfoCtrl) {
				infoGrantObjectModelMap.clear();
				List<Object[]> infoCtrlList = baseDAO
						.findByNativeSQLWithIndexParam("SELECT O.OBJECT_TYPE,O.OBJECT_CODE,C.INFO_ITEM_ID,C.CTRL_TYPE,C.CTRL_DESC,C.CTRL_STAT,"
								+ "C.START_DATE,C.END_DATE,M.TAB_ID,M.COL_ID FROM TX_INFO_GRANT_OBJECT O,TX_INFO_CTRL C,TX_INFO_ITEM M where O.GRANT_OBJECT_ID=C.GRANT_OBJECT_ID AND C.INFO_ITEM_ID=M.INFO_ITEM_ID");
				if (infoCtrlList != null) {
					for (Object[] infoCtrl : infoCtrlList) {
						if (infoCtrl.length == 10) {
							InfoGrantObjectModel infoGrantObjectModel = new InfoGrantObjectModel();
							InfoGrantCtrl infoGrantCtrl = new InfoGrantCtrl();
							if (infoCtrl[0] == null) {
								continue;
							}
							infoGrantObjectModel
									.setAuthType((String) infoCtrl[0]);
							if (infoCtrl[1] == null) {
								continue;
							}
							infoGrantObjectModel
									.setAuthCode((String) infoCtrl[1]);
							if (infoCtrl[2] == null) {
								continue;
							}

							infoGrantCtrl.setInfoItemId(Long
									.valueOf(infoCtrl[2].toString()));
							if (infoCtrl[3] == null) {
								continue;
							}

							infoGrantObjectModel
									.setCtrlType((String) infoCtrl[3]);
							if (infoCtrl[8] == null) {
								continue;
							}
							infoGrantObjectModel.setTabId((Long) infoCtrl[8]);
							if (infoCtrl[9] == null) {
								continue;
							}
							infoGrantObjectModel.setColId((Long) infoCtrl[9]);

							if (infoCtrl[4] != null) {
								infoGrantCtrl.setCtrlDesc((String) infoCtrl[4]);
							}
							if (infoCtrl[5] != null) {
								infoGrantCtrl.setCtrlStat(infoCtrl[5]
										.toString());
							}
							if (infoCtrl[6] != null) {
								infoGrantCtrl.setStartDate((Date) infoCtrl[6]);
							}
							if (infoCtrl[7] != null) {
								infoGrantCtrl.setEndDate((Date) infoCtrl[7]);
							}

							infoGrantObjectModelMap.put(infoGrantObjectModel,
									infoGrantCtrl);
						}
					}
				}
			}

			/**** ��ֵӳ���ϵװ�� *****/
			List<Object[]> stdCodeMapList = baseDAO
					.findByNativeSQLWithIndexParam("SELECT SRC_SYS_CD,SRC_CODE,STD_CATE,STD_CODE FROM TX_CODE_MAP");
			srcToEcifCodeMap.clear();
			ecifToSrcCodeMap.clear();
			if (stdCodeMapList != null) {
				for (Object[] stdCodeMap : stdCodeMapList) {
					srcToEcifCodeMap.put(stdCodeMap[0] + "_" + stdCodeMap[1]
							+ "_" + stdCodeMap[2], (String) stdCodeMap[3]);
					ecifToSrcCodeMap.put(stdCodeMap[0] + "_" + stdCodeMap[3]
							+ "_" + stdCodeMap[2], (String) stdCodeMap[1]);
				}
			}

			ecifStdCodeMap.clear();
		}

		/******** ȡ��ecif���������ֵ�ֶΣ�������ֵ��Ч�Ը��� *********/
		List<Object[]> codeTableModelCfg = baseDAO
				.findByNativeSQLWithIndexParam("select distinct T.OBJ_NAME,C.COL_NAME from TX_TAB_DEF T,TX_COL_DEF C where T.TAB_ID=C.TAB_ID and C.IS_CODE='1'");
		ecifTableCode.clear();
		if (codeTableModelCfg != null) {
			for (Object[] codeTableModel : codeTableModelCfg) {
					ecifTableCode.add(codeTableModel[0].toString()+"_"+codeTableModel[1].toString());
			}
		}
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
