/**
 * @��Ŀ����ytec-mdm-redevelop
 * @������com.ytec.fubonecif.service.component.biz.identification
 * @�ļ�����UIdentifierVerif.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-7-22-16:34:55
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.component.biz.identification;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.bs.ServiceEntityMgr;
import com.ytec.mdm.service.bo.Identifier;
import com.ytec.mdm.service.component.general.CustStatusMgr;
import com.ytec.fubonecif.domain.MCiCustomer;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�GetCustomerNameForFb
 * @���������޸Ŀͻ�����֤�����ͣ�֤�����룬�ͻ�������֤���伶���޸�
 * @��������:���޸ĵĿͻ�������֤��Ϣ�����������ͻ��д���
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:58:16
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:58:16
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service("uIdentifierVerif")
@SuppressWarnings("rawtypes")
public class UIdentifierVerif {
	/**
	 * @��������:log
	 * @��������:��־
	 * @since 1.0.0
	 */
	private static Logger log = LoggerFactory.getLogger(UIdentifierVerif.class);

	/**
	 * @��������:VerifIdentifier
	 * @��������:�޸Ŀ�����֤��Ϣ��֤
	 * @�����뷵��˵��:
	 * @param ecifData
	 * @return
	 * @�㷨����:
	 */
	public boolean VerifIdentifier(EcifData ecifData) {
		WriteModel writeModel = ecifData.getWriteModelObj();
		MCiCustomer mCiCustomer = null;
		if ((mCiCustomer = (MCiCustomer) writeModel.getOpModelByName("MCiCustomer")) != null) {
			/**
			 * ���ڿͻ���Ϣ����,��ѯԭ���Ŀͻ���Ϣ����
			 ***/
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			MCiCustomer mCiCustomer_o = null;
			List result = baseDAO.findWithIndexParam("FROM MCiCustomer C WHERE C." + MdmConstants.CUSTID + "=?",
					""+ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			if (result != null && result.size() > 0) {
				mCiCustomer_o = (MCiCustomer) result.get(0);
			} else {
				log.warn("��ѯ�ͻ�[{}]ԭ���Ŀͻ���Ϣ������ϢΪ��", ecifData.getCustId());
				return true;
			}
			Identifier mCiIdentifier = new Identifier();
			/**
			 * �����б仯
			 */
			boolean nameChanged = false;
			/**
			 * ֤�����ͣ�֤�������б仯
			 */
			boolean identChanged = false;
			/**
			 * ֤�����ͱ仯
			 */
			boolean identTypeChanged = false;
			if (mCiCustomer.getIdentType() != null && !mCiCustomer.getIdentType().equals(mCiCustomer_o.getIdentType())) {
				/**
				 * ����֤�������б仯
				 */
				mCiIdentifier.setIdentType(mCiCustomer.getIdentType());
				identChanged = true;
				identTypeChanged = true;
				log.info("����֤�������б仯");
			} else {
				mCiIdentifier.setIdentType(mCiCustomer_o.getIdentType());
			}
			if (mCiCustomer.getIdentNo() != null && !mCiCustomer.getIdentNo().equals(mCiCustomer_o.getIdentNo())) {
				/**
				 * ����֤�������б仯
				 */
				mCiIdentifier.setIdentNo(mCiCustomer.getIdentNo());
				identChanged = true;
				log.info("����֤�������б仯");
			} else {
				mCiIdentifier.setIdentNo(mCiCustomer_o.getIdentNo());
			}
			if (mCiCustomer.getCustName() != null && !mCiCustomer.getCustName().equals(mCiCustomer_o.getCustName())) {
				/**
				 * ���������б仯
				 */
				nameChanged = true;
				mCiIdentifier.setIdentCustName(mCiCustomer.getCustName());
				log.info("���������б仯");
			} else {
				mCiIdentifier.setIdentCustName(mCiCustomer_o.getCustName());
			}
			if (!identChanged && !nameChanged) {
				/**
				 * ������֤��Ϣ�ޱ仯
				 */
				mCiIdentifier = null;
				return true;
			}
			/**
			 * ��֤���޸ĵ���֤��Ϣ�������ͻ���ͬ
			 */
			List o = bizGetContIdByIdent(mCiIdentifier, ecifData.getCustId(MdmConstants.CUSTID_TYPE),
					ecifData.getCustType());
			if (o != null && o.size() > 0) {
				if (identChanged) {
					ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "������ÿͻ���ͬ��֤���Ŀͻ�");
				} else {
					ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "������ÿͻ���ͬ��֤�������Ŀͻ�");
				}
				log.warn("�޸���֤��Ϣ�Ƿ�,�Ѵ�����ÿͻ���ͬ��֤��Ϣ�Ŀͻ�");
				return false;
			}
			if (nameChanged) {
				/**
				 * �����б仯,�����޸�֤����Ϣ���е�֤������
				 */
				String identifierTab = null;
				if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
					identifierTab = MdmConstants.MODEL_PERSONIDENTIFIER;
				} else {
					identifierTab = MdmConstants.MODEL_ORGIDENTIFIER;
				}

				List identifierList = baseDAO.findWithIndexParam("from " + identifierTab + " t where t."
						+ MdmConstants.CUSTID + "=?", ecifData.getCustId(MdmConstants.CUSTID_TYPE));
				if (identifierList != null) {
					for (Object i : identifierList) {
						if (identTypeChanged) {
							if (mCiCustomer_o.getIdentType().equals(ReflectionUtils.getFieldValue(i, "identType"))) {
								continue;
							}
						}
						if (identChanged) {
							ReflectionUtils.setFieldValue(i, "identNo", mCiIdentifier.getIdentNo());
						}
						ReflectionUtils.setFieldValue(i, "identCustName", mCiIdentifier.getIdentCustName());
						writeModel.setOpModelList(i);
					}
				}
			}
			if (identChanged) {
				/**
				 * ����֤���б仯�������޸�֤�����еĸ���Ϣ
				 */
				Object i = null;
				try {
					if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
						Class clazz = ServiceEntityMgr.getEntityByName(MdmConstants.MODEL_PERSONIDENTIFIER);
						i = clazz.newInstance();
					} else {
						Class clazz = ServiceEntityMgr.getEntityByName(MdmConstants.MODEL_ORGIDENTIFIER);
						i = clazz.newInstance();
					}
					if (identTypeChanged) {
						/**
						 * ����ԭ����Ϣ
						 */
						Object identId = null;
						List resultI = baseDAO.findWithIndexParam("FROM " + i.getClass().getSimpleName()
								+ " C WHERE C." + MdmConstants.CUSTID + "=?",
								ecifData.getCustId(MdmConstants.CUSTID_TYPE));
						String identType = null;
						if (resultI != null && !resultI.isEmpty()) {
							for (Object ident : resultI) {
								ReflectionUtils.getFieldValue(ident, "identType");
								if (mCiCustomer.getIdentType().equals(identType)) {
									/**
									 * �鿴֤�������Ƿ��Ѿ����ڴ���֤��
									 */
									identId = null;
									break;
								}
								if (mCiCustomer_o.getIdentType().equals(identType)) {
									if (identId != null) {
										log.warn("�ͻ�[{}]����֤����[{}]���д洢������", ecifData.getCustId(), i.getClass()
												.getSimpleName());
									}
									identId = ReflectionUtils.getFieldValue(ident, "identId");
								}
							}
						}
						/**
						 * ֤�����ͷ����仯,���޸ĵ�֤���������ý�ȥ
						 */
						List indentMoleList = null;
						boolean noIdent = false;
						if ((indentMoleList = writeModel.getOpModelListByName(i.getClass().getSimpleName())) != null
								&& !indentMoleList.isEmpty()) {
							for (Object x : indentMoleList) {
								if (mCiCustomer_o.getIdentType().equals(ReflectionUtils.getFieldValue(x, "identType"))) {
									noIdent = true;
									break;
								}
							}

							if (!noIdent) {
								for (Object x : indentMoleList) {
									if (mCiIdentifier.getIdentType().equals(
											ReflectionUtils.getFieldValue(x, "identType"))) {
										ReflectionUtils.setFieldValue(x, "identId", identId);
									}

								}
								ReflectionUtils.setFieldValue(i, "identId", identId);
							}
						}
					}
					ReflectionUtils.setFieldValue(i, "identType", mCiIdentifier.getIdentType());
					ReflectionUtils.setFieldValue(i, "identNo", mCiIdentifier.getIdentNo());
					ReflectionUtils.setFieldValue(i, "identCustName", mCiIdentifier.getIdentCustName());
					writeModel.setOpModelList(i);
				} catch (Exception e) {
					log.error("����֤���б仯�������޸�֤�����еĸ���Ϣ����", e);
				}
			}
			return true;
		} else {
			return true;
		}
	}

	private List<String> bizGetContIdByIdent(Identifier mCiIdentifier, Object custId, String custType) {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		StringBuffer jql = new StringBuffer("");
		List<String[]> result = null;
		jql.append("SELECT distinct C.custId,C.custId,C.custType,C.custStat FROM ");
		jql.append("MCiCustomer C WHERE C.identType=? AND C.identNo=? AND C.custName=? and C.custId<>?");
		if (custType != null && !custType.isEmpty()) {
			jql.append(" AND C.custType=?");
			result = baseDAO.findWithIndexParam(jql.toString(), mCiIdentifier.getIdentType(),
					mCiIdentifier.getIdentNo(), mCiIdentifier.getIdentCustName(), custId.toString(), custType);
		} else {
			result = baseDAO.findWithIndexParam(jql.toString(), mCiIdentifier.getIdentType(),
					mCiIdentifier.getIdentNo(), mCiIdentifier.getIdentCustName(), custId.toString());
		}
		if (result == null || result.size() == 0) {
			return null;
		} else {
			// �жϲ�ѯ������ͻ����ų�ʧЧ�ͻ�(ע��,�ϲ���)��
			Object[] ob = null;
			int availableNum = 0;
			CustStatus custStatCtrl = null;
			List<String> result_ = new ArrayList<String>();
			for (int i = 0; i < result.size(); i++) {
				ob = (Object[]) result.get(i);
				if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob[3])) != null
						&& custStatCtrl.isReOpen()) {// ʧЧ�ͻ�
					continue;
				} else {
					result_.add(ob[0].toString());
				}
				availableNum++;
			}
			if (availableNum == 0) {
				return null;
			} else {
				return result_;
			}
		}
	}
}
