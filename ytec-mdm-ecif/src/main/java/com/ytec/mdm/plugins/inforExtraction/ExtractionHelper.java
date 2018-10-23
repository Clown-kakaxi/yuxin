/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.inforExtraction
 * @�ļ�����ExtractionHelper.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-20-����4:21:18
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.inforExtraction;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.integration.transaction.model.TxModel;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�ExtractionHelper
 * @����������Ϣ��ȡ������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-20 ����4:21:18
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-20 ����4:21:18
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ExtractionHelper {
	private Logger log = LoggerFactory.getLogger(ExtractionHelper.class);
	private static String splitCh="\\+";//�ֶ�ƴ�ӷָ��
	/**
	 * @��������:inforExtractionModel
	 * @��������:��Ϣ��ȡ����
	 * @since 1.0.0
	 */
	private ConcurrentHashMap<String, ExtractionInfo> inforExtractionModel = new ConcurrentHashMap<String, ExtractionInfo>();

	private static ExtractionHelper extractionHelper = new ExtractionHelper();

	/**
	 * @���캯��
	 */
	public ExtractionHelper() {
		// TODO Auto-generated constructor stub
	}

	public static ExtractionHelper getInstance() {
		return extractionHelper;
	}

	/**
	 * ��ȡ��չ����
	 * 
	 * @param txCode
	 *            ������
	 */
	private List<ExtractionModel> gettExtractionInfoCfg(String txCode) {
		File file = new File(BusinessCfg.getString("extractionCfgPath") + "/"
				+ txCode + ".xml");
		if (file.exists() && file.isFile()) {
			try {
				Document doc = null;
				List<ExtractionModel> extendModelList = null;
				SAXReader xmlReader = new SAXReader();
				doc = xmlReader.read(file);
				Element extendCfg = (Element) doc
						.selectSingleNode("//transaction");
				String txCodeCfg = extendCfg.attributeValue("txCode");
				if (txCode.equals(txCodeCfg)) {
					List<Element> classCfgList = extendCfg.elements("class");
					if (classCfgList != null) {
						extendModelList = new ArrayList<ExtractionModel>();
						for (Element classCfg : classCfgList) {
							ExtractionModel extendmodel = new ExtractionModel();
							extendmodel.setModelClass(classCfg
									.attributeValue("name"));
							String isRequest = classCfg
									.attributeValue("isRequest");
							if (isRequest != null && isRequest.equals("1")) {
								extendmodel.setRequest(true);
							} else {
								extendmodel.setRequest(false);
							}

							List<Element> idProList = classCfg.elements("id");
							if (idProList != null) {
								List<PropertyMapping> keyProperty = new ArrayList<PropertyMapping>();
								for (Element idPro : idProList) {
									PropertyMapping pmapping = new PropertyMapping();
									pmapping.setProCode(idPro
											.attributeValue("name"));
									pmapping.setAttRoot(idPro
											.attributeValue("AttRoot"));
									pmapping.setAttName(idPro
											.attributeValue("AttName"));
									pmapping.setDefaut(idPro
											.attributeValue("default"));
									keyProperty.add(pmapping);
								}
								extendmodel.setKeyProperty(keyProperty);

							}
							List<Element> norProList = classCfg
									.elements("property");
							if (norProList != null) {
								List<PropertyMapping> norProperty = new ArrayList<PropertyMapping>();
								for (Element norPro : norProList) {
									PropertyMapping pmapping = new PropertyMapping();
									pmapping.setProCode(norPro
											.attributeValue("name"));
									pmapping.setAttRoot(norPro
											.attributeValue("AttRoot"));
									pmapping.setAttName(norPro
											.attributeValue("AttName"));
									pmapping.setDefaut(norPro
											.attributeValue("default"));
									norProperty.add(pmapping);
								}
								extendmodel.setNorProperty(norProperty);

							}
							extendModelList.add(extendmodel);
						}

					}
					return extendModelList;
				} else {
					log.error("��չ���õĽ�����{}��ƥ��", txCode);
					return null;
				}
			} catch (Exception e) {
				log.error("������Ϣ��", e);
				return null;
			}
		} else {
			return null;
		}
	}

	private List<ExtractionModel> gettExtractionInfo(TxModel txModel) {
		String txCode = txModel.getTxDef().getTxCode();
		ExtractionInfo extractionInfo = inforExtractionModel.get(txCode);
		if (extractionInfo == null) {
			List<ExtractionModel> extractionModelList = gettExtractionInfoCfg(txCode);
			if (extractionModelList == null) {
				return null;
			}
			extractionInfo = new ExtractionInfo(extractionModelList, txModel
					.getTxDef().getUpdateTm());
			ExtractionInfo extractionInfoTemp = null;
			if ((extractionInfoTemp = inforExtractionModel.putIfAbsent(txCode,
					extractionInfo)) != null) {
				extractionInfo = extractionInfoTemp;
			}
		} else {
			if (MdmConstants.globalTxDefCheck) {
				// ����޸������Ƿ����仯
				Timestamp newTimestamp = txModel.getTxDef().getUpdateTm();
				Timestamp oldTimestamp = extractionInfo.getUpdateTm();
				if (newTimestamp != null
						&& (oldTimestamp == null || newTimestamp
								.after(oldTimestamp))) {
					List<ExtractionModel> extractionModelList = gettExtractionInfoCfg(txCode);
					if (extractionModelList == null) {
						return null;
					}
					extractionInfo = new ExtractionInfo(extractionModelList,
							txModel.getTxDef().getUpdateTm());
					inforExtractionModel.replace(txCode, extractionInfo);
				}
			}
		}
		return extractionInfo.getExtractionModelList();
	}

	/**
	 * @��������:fetchInformation
	 * @��������:��ȡ��Ϣ
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * 		@param txModel
	 * 		@return
	 * @�㷨����:
	 */
	public boolean fetchInformation(EcifData ecifData, TxModel txModel) {
		/** ��Ϣ��ȡ���� **/
		List<ExtractionModel> extendModelList = (List<ExtractionModel>) gettExtractionInfo(txModel);
		if (extendModelList != null && !extendModelList.isEmpty()) {
			String value = null;
			String attrroot = null;
			String attname = null;
			String classname = null;
			String procode = null;
			boolean isErrer = false;
			String value_i = null;
			Object obj = null;
			Element rootNode = null;
			try {
				Element root = ecifData.getBodyNode();
				WriteModel generalInfoList = ecifData.getWriteModelObj();
				for (ExtractionModel extendmodel : extendModelList) {
					classname = extendmodel.getModelClass();// ģ������
					// ������
					Class clazz = ServiceEntityMgr.getEntityByName(classname);
					if (clazz == null) {
						log.error("û���ҵ�{}��ʵ����", classname);
						ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
						return false;
					}
					// ʵ��������
					obj = clazz.newInstance();
					if (obj == null) {
						log.error("ģ������ת��Ϊ����ʧ��");
						continue;
					}
					isErrer = false;
					boolean ischange = false;
					if (extendmodel.getKeyProperty() != null) {
						for (PropertyMapping pmapping : extendmodel
								.getKeyProperty()) {
							procode = pmapping.getProCode();
							attrroot = pmapping.getAttRoot();
							if (attrroot != null && !attrroot.isEmpty()) {
								rootNode = (Element) root.selectSingleNode("//"+ attrroot);
							} else {
								rootNode = root;
							}
							if (rootNode == null) {
								if (extendmodel.isRequest()) {
									log.error("��Ϣ��ȡ{}���Ľڵ�{}Ϊ��", classname,attrroot);
								}
								isErrer = true;
								break;
							}
							attname = pmapping.getAttName();
							if (attname != null && attname.isEmpty()) {
								value = pmapping.getDefaut();
							} else {
								String attnames[] = attname.split(splitCh);
								value = "";
								for (String attnames_i : attnames) {
									value_i = rootNode.elementTextTrim(attnames_i);
									if (value_i != null) {
										value += value_i;
									}
								}
							}
							if (value == null || value.isEmpty()) {
								if (pmapping.getDefaut() == null
										|| pmapping.getDefaut().isEmpty()) {
									if (extendmodel.isRequest()) {
										log.error("��Ϣ��ȡ{}���Ľڵ�:{}����{}Ϊ��",classname,attrroot,attname);
									}
									isErrer = true;
									break;
								} else {
									value = pmapping.getDefaut();
								}
							}
							if (!setFieldValue(obj, procode, value)) {
								log.error("ת�����ݴ���");
								isErrer = true;
								break;
							}
							ischange = true;
						}
					}
					if (isErrer) {
						if (extendmodel.isRequest()) {
							log.error("������ȡ��Ϣ��ȡ����");
							ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_OTHER);
							return false;
						} else {
							continue;
						}
					}
					if (extendmodel.getNorProperty() != null) {
						for (PropertyMapping pmapping : extendmodel.getNorProperty()) {
							procode = pmapping.getProCode();
							attrroot = pmapping.getAttRoot();
							if (attrroot != null && !attrroot.isEmpty()) {
								rootNode = (Element) root.selectSingleNode("//"+ attrroot);
							} else {
								rootNode = root;
							}
							if (rootNode == null) {
								continue;
							}
							attname = pmapping.getAttName();
							if (attname != null && attname.isEmpty()) {
								value = pmapping.getDefaut();
							} else {
								String attnames[] = attname.split(splitCh);
								value = "";
								for (String attnames_i : attnames) {
									value_i = rootNode.elementTextTrim(attnames_i);
									if (value_i != null) {
										value += value_i;
									}
								}
							}
							if (value == null || value.isEmpty()) {
								if (pmapping.getDefaut() == null
										|| pmapping.getDefaut().isEmpty()) {
									continue;
								} else {
									value = pmapping.getDefaut();
								}
							}
							if (!setFieldValue(obj, procode, value)) {
								log.error("ת�����ݴ���");
							}
							ischange = true;
						}
					}
					if (ischange || extendmodel.isRequest()) {
						generalInfoList.setOpModelList(obj);
					}
				}
			} catch (Exception e) {
				log.error("��Ϣ��ȡ�쳣", e);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @��������:setFieldValue
	 * @��������:����������������
	 * @�����뷵��˵��:
	 * 		@param obj
	 * 		@param fieldName
	 * 		@param value
	 * 		@return
	 * @�㷨����:
	 */
	private boolean setFieldValue(Object obj,String fieldName,String value){
		try{
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object valueObj =convertStringToObject(value,field.getType());
			field.set(obj, valueObj);
		}catch(Exception e){
			log.error("�������ô���",e);
			return false;
		}
		return true;
	}
	
	/**
	 * @��������:convertStringToObject
	 * @��������:���ַ���ת���ɶ�Ӧ����������
	 * @�����뷵��˵��:
	 * 		@param value
	 * 		@param typeClass
	 * 		@return
	 * @�㷨����:
	 */
	private Object convertStringToObject(String value,Class typeClass){
		Object newValue = null;
		SimpleDateFormat dateFormat = null;
		if (java.lang.String.class.equals(typeClass)) {// �ַ���
			newValue = value;
		} else if (java.lang.Long.class.equals(typeClass)||long.class.equals(typeClass)) {// ����
			newValue = new Long(value);
		} else if (java.lang.Double.class.equals(typeClass)||double.class.equals(typeClass)) {// ������
			newValue = new Double(value);
		} else if (java.util.Date.class.equals(typeClass)) {// ����
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			newValue=StringUtil.reverse2Date(value,dateFormat);
			if(newValue==null){
				log.warn("{}:���ڸ�ʽ����",value);
				return null;
			}
		} else if (Integer.class.equals(typeClass)||int.class.equals(typeClass)) {
			newValue = new Integer(value);
		} else if (Time.class.equals(typeClass)) {// ʱ��
			dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date dd=StringUtil.reverse2Date(value,dateFormat);
			if(dd==null){
				log.warn("{}:ʱ���ʽ����",value);
				return null;
			}
			newValue = new Time(dd.getTime());
		} else if (Timestamp.class.equals(typeClass)) {// ʱ���
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dd=StringUtil.reverse2Date(value,dateFormat);
			if(dd==null){
				log.warn("{}:ʱ�����ʽ����",value);
				return null;
			}
			newValue = new Timestamp(dd.getTime());
		} else if (byte.class.equals(typeClass)||Byte.class.equals(typeClass)) {// �����ƴ��ֶ�
			newValue = value.getBytes();
		} else if(BigDecimal.class.equals(typeClass)){  //BigDecimal
			newValue=BigDecimal.valueOf(Double.valueOf(value));
		}else if(BigInteger.class.equals(typeClass)){//BigInteger
			newValue=BigInteger.valueOf(Long.valueOf(value));
		}else if(Boolean.class.equals(typeClass)||boolean.class.equals(typeClass)){//Boolean
			newValue=Boolean.valueOf(value);
		}else if(Short.class.equals(typeClass)||short.class.equals(typeClass)){//Short
			newValue=Short.valueOf(value);
		}else if(Float.class.equals(typeClass)||float.class.equals(typeClass)){//Float
			newValue=Float.valueOf(value);
		}else if(Character.class.equals(typeClass)||char.class.equals(typeClass)){//Character
			newValue=Character.valueOf(value.charAt(0));
		} else {
			log.warn("{}:�������Ͳ�֧��",typeClass.getClass().getSimpleName());
			return null;
		}
		return newValue;
	}

}

class ExtractionInfo {
	/**
	 * @��������:extractionModelList
	 * @��������:��Ϣ��ȡ�б�
	 * @since 1.0.0
	 */
	private List<ExtractionModel> extractionModelList;
	/**
	 * @��������:updateTm
	 * @��������:������ʱ��
	 * @since 1.0.0
	 */
	private Timestamp updateTm;

	/**
	 * @���캯��
	 */
	public ExtractionInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @���캯��
	 * @param extractionModelList
	 * @param updateTm
	 */
	public ExtractionInfo(List<ExtractionModel> extractionModelList,
			Timestamp updateTm) {
		this.extractionModelList = extractionModelList;
		this.updateTm = updateTm;
	}

	public List<ExtractionModel> getExtractionModelList() {
		return extractionModelList;
	}

	public void setExtractionModelList(List<ExtractionModel> extractionModelList) {
		this.extractionModelList = extractionModelList;
	}

	public Timestamp getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

}
