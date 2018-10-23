/**
 * @项目名：ytec-mdm-redevelop
 * @包名：com.ytec.fubonecif.service.component.biz.identification
 * @文件名：UIdentifierVerif.java
 * @版本信息：1.0.0
 * @日期：2014-7-22-16:34:55
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：GetCustomerNameForFb
 * @类描述：修改客户开户证件类型，证件号码，客户名称验证及其级联修改
 * @功能描述:被修改的客户开户三证信息不能在其他客户中存在
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:58:16
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:58:16
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service("uIdentifierVerif")
@SuppressWarnings("rawtypes")
public class UIdentifierVerif {
	/**
	 * @属性名称:log
	 * @属性描述:日志
	 * @since 1.0.0
	 */
	private static Logger log = LoggerFactory.getLogger(UIdentifierVerif.class);

	/**
	 * @函数名称:VerifIdentifier
	 * @函数描述:修改开户三证信息验证
	 * @参数与返回说明:
	 * @param ecifData
	 * @return
	 * @算法描述:
	 */
	public boolean VerifIdentifier(EcifData ecifData) {
		WriteModel writeModel = ecifData.getWriteModelObj();
		MCiCustomer mCiCustomer = null;
		if ((mCiCustomer = (MCiCustomer) writeModel.getOpModelByName("MCiCustomer")) != null) {
			/**
			 * 存在客户信息主表,查询原来的客户信息主表
			 ***/
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			MCiCustomer mCiCustomer_o = null;
			List result = baseDAO.findWithIndexParam("FROM MCiCustomer C WHERE C." + MdmConstants.CUSTID + "=?",
					""+ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			if (result != null && result.size() > 0) {
				mCiCustomer_o = (MCiCustomer) result.get(0);
			} else {
				log.warn("查询客户[{}]原来的客户信息主表信息为空", ecifData.getCustId());
				return true;
			}
			Identifier mCiIdentifier = new Identifier();
			/**
			 * 户名有变化
			 */
			boolean nameChanged = false;
			/**
			 * 证件类型，证件号码有变化
			 */
			boolean identChanged = false;
			/**
			 * 证件类型变化
			 */
			boolean identTypeChanged = false;
			if (mCiCustomer.getIdentType() != null && !mCiCustomer.getIdentType().equals(mCiCustomer_o.getIdentType())) {
				/**
				 * 开户证件类型有变化
				 */
				mCiIdentifier.setIdentType(mCiCustomer.getIdentType());
				identChanged = true;
				identTypeChanged = true;
				log.info("开户证件类型有变化");
			} else {
				mCiIdentifier.setIdentType(mCiCustomer_o.getIdentType());
			}
			if (mCiCustomer.getIdentNo() != null && !mCiCustomer.getIdentNo().equals(mCiCustomer_o.getIdentNo())) {
				/**
				 * 开户证件号码有变化
				 */
				mCiIdentifier.setIdentNo(mCiCustomer.getIdentNo());
				identChanged = true;
				log.info("开户证件号码有变化");
			} else {
				mCiIdentifier.setIdentNo(mCiCustomer_o.getIdentNo());
			}
			if (mCiCustomer.getCustName() != null && !mCiCustomer.getCustName().equals(mCiCustomer_o.getCustName())) {
				/**
				 * 开户户名有变化
				 */
				nameChanged = true;
				mCiIdentifier.setIdentCustName(mCiCustomer.getCustName());
				log.info("开户户名有变化");
			} else {
				mCiIdentifier.setIdentCustName(mCiCustomer_o.getCustName());
			}
			if (!identChanged && !nameChanged) {
				/**
				 * 开户三证信息无变化
				 */
				mCiIdentifier = null;
				return true;
			}
			/**
			 * 验证待修改的三证信息与其他客户相同
			 */
			List o = bizGetContIdByIdent(mCiIdentifier, ecifData.getCustId(MdmConstants.CUSTID_TYPE),
					ecifData.getCustType());
			if (o != null && o.size() > 0) {
				if (identChanged) {
					ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "存在与该客户相同的证件的客户");
				} else {
					ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "存在与该客户相同的证件户名的客户");
				}
				log.warn("修改三证信息非法,已存在与该客户相同三证信息的客户");
				return false;
			}
			if (nameChanged) {
				/**
				 * 户名有变化,级联修改证件信息表中的证件户名
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
				 * 开户证件有变化，级联修改证件表中的该信息
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
						 * 查找原来信息
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
									 * 查看证件表中是否已经存在此类证件
									 */
									identId = null;
									break;
								}
								if (mCiCustomer_o.getIdentType().equals(identType)) {
									if (identId != null) {
										log.warn("客户[{}]开户证件在[{}]表中存储有问题", ecifData.getCustId(), i.getClass()
												.getSimpleName());
									}
									identId = ReflectionUtils.getFieldValue(ident, "identId");
								}
							}
						}
						/**
						 * 证件类型发生变化,将修改的证件主键设置进去
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
					log.error("开户证件有变化，级联修改证件表中的该信息错误", e);
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
			// 判断查询出多个客户，排除失效客户(注销,合并等)。
			Object[] ob = null;
			int availableNum = 0;
			CustStatus custStatCtrl = null;
			List<String> result_ = new ArrayList<String>();
			for (int i = 0; i < result.size(); i++) {
				ob = (Object[]) result.get(i);
				if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob[3])) != null
						&& custStatCtrl.isReOpen()) {// 失效客户
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
