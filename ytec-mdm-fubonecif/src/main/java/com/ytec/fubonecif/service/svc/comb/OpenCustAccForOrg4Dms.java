/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：OpenCustAccForOrg.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:05:24
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.service.svc.atomic.Accont;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：OpenCustAccForOrg4Dms
 * @类描述：组织客户开户4移动信贷
 * @功能描述:
 * @创建人：liuming4@yuchengtech.com
 * @创建时间：20170722
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenCustAccForOrg4Dms implements IEcifBizLogic {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(OpenCustAccForOrg4Dms.class);

	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		log.info("对公开户处理类开始处理(移动信贷)");
		boolean isBlank = true;
		String custName = null;
		String custNameTemp = null;
		Map opMp = ecifData.getWriteModelObj().getOperMap();

		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		opMp.put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_ORG_TYPE);
		ecifData.setCustType(MdmConstants.TX_CUST_ORG_TYPE);

		// 设置无三证是否允许开户
		boolean flag = true;
		log.info("无三证是否允许开户:{}", flag);
		List generalInfoList = ecifData.getWriteModelObj().getOpModelList();
		// 封装用于识别的三证识别,无优先级顺序
		List<MCiIdentifier> identList = new ArrayList();
		MCiIdentifier itemp = null;
		for (Object obj : generalInfoList) {
			if (obj.getClass().equals(MCiIdentifier.class)) {
				flag = true;
				MCiIdentifier ident = (MCiIdentifier) obj;
				itemp = new MCiIdentifier(ident.getIdentType(), ident.getIdentNo(), ident.getIdentCustName());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD, ident.getIdentType());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO, ident.getIdentNo());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME, ident.getIdentCustName());
				identList.add(itemp);
				/** 取户名 **/
				if (MdmConstants.OPENIDENTFLAG.equals(ident.getIsOpenAccIdent())) {
					custName = ident.getIdentCustName();
				}
				if (custNameTemp == null) {
					custNameTemp = ident.getIdentCustName();
				} else {
					if (!custNameTemp.equals(ident.getIdentCustName())) {
						ecifData.setStatus(ErrorCode.ERR_ECIF_CUSTNAME.getCode(), "证件信息中的户名不统一");
						return;
					}
				}
			} else {
				isBlank = false;
			}
		}

		if (!flag) {
			String msg = String.format("%s/证件信息无值,证件信息(证件类型、证件号码、证件户名)不齐全不允许开户",
					ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getChDesc());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(), msg);
			log.error("{}:{}", ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(), msg);
			return;
		}

		if (custName == null) {
			custName = identList.get(0).getIdentCustName();
		}

		// 添加三证识别信息
		opMp.put("identList", identList);

		// 识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);
		if (MdmConstants.checkCustomerStatus && ecifData.isSuccess()) {
			CustStatus custStatCtrl = null;
			if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(ecifData.getCustStatus())) != null) {
				if (!custStatCtrl.isNormal()) {
					if (custStatCtrl.isReOpen()) {
						MCiCustomer customer = null;
						if (ecifData.getWriteModelObj().containsOpModel(MCiCustomer.class.getSimpleName())) {
							customer = (MCiCustomer) ecifData.getWriteModelObj().getOpModelByName(
									MCiCustomer.class.getSimpleName());
						} else {
							customer = new MCiCustomer();
						}
						customer.setCustStat(MdmConstants.STATE);
						ecifData.getWriteModelObj().setOpModelList(customer);
						ecifData.getWriteModelObj().setDivInsUpd(false);
						log.info("客户({})状态({}),重新开户启用", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
					} else if (!custStatCtrl.isUpdate()) {
						log.warn("客户({})状态{}", ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
						ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(), "该客户%s状态:%s",
								ecifData.getCustId(), custStatCtrl.getCustStatusDesc());
						return;
					}
				}
			}
		}
		if (ecifData.isSuccess()) {
			if (!ecifData.getWriteModelObj().isDivInsUpd()) {
				AddGeneral addGeneral = (AddGeneral) SpringContextUtils.getBean("addGeneral");
				try {
					addGeneral.process(ecifData);
					
					ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
					
					ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
							ecifData.getCustId());
					ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getCode(),
								ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getChDesc());
					ecifData.setSuccess(true);
				} catch (Exception e) {
					log.error("数据操作异常", e);
					if (ecifData.isSuccess()) {
						ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
					}
				}
				return;
			} else {
				ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(), "客户已存在:" + ecifData.getCustId());
				ecifData.getWriteModelObj().setResult("custNo", ecifData.getCustId());
				return;
			}
		} else {
			if (!ErrorCode.ERR_ECIF_NOT_EXIST_CONTID.getCode().equals(ecifData.getRepStateCd())) {
				if(!ErrorCode.ERR_ECIF_NULL_IDENTTP.getCode().equals(ecifData.getRepStateCd())
						&& !ErrorCode.ERR_ECIF_NULL_IDENTNO.getCode().equals(ecifData.getRepStateCd())){
					log.warn("返回非期望的数据:{}", ecifData.getDetailDes());
					return;
				}
			}
			// 识别不成功，开户
			Accont accont = (Accont) SpringContextUtils.getBean("accont");
			ecifData.resetStatus();
			try {
				accont.process(ecifData, isBlank);
				ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getCustId());
			} catch (Exception e) {
				log.error("错误信息", e);
				ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			}
		}
		return;
	}

}
