/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����OpenCustAccForPerson.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:05:38
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.fubonecif.service.component.biz.identification.UIdentifierVerif;
import com.ytec.fubonecif.service.svc.atomic.Accont;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�OpenCustAccForPerson
 * @�����������˿ͻ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:05:38
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:05:38
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenCustAccForPerson implements IEcifBizLogic {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory
			.getLogger(OpenCustAccForPerson.class);

	//@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// TODO Auto-generated method stub
		boolean isBlank = true;
		String custName = null;
		MCiCustomer nametilte = null;
		String custNameTemp = null;
		Map opMp = ecifData.getWriteModelObj().getOperMap();

		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		opMp.put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_PER_TYPE);
		ecifData.setCustType(MdmConstants.TX_CUST_PER_TYPE);

		// ��������֤�Ƿ�������
		boolean flag = false;
		flag = BusinessCfg.getBoolean("noIdentIsAdd");
		List generalInfoList = ecifData.getWriteModelObj().getOpModelList();
		// ��װ����ʶ�����֤ʶ��,�����ȼ�˳��
		List<MCiIdentifier> identList = new ArrayList();
		MCiIdentifier itemp = null;
		for (Object obj : generalInfoList) {
			if (obj.getClass().equals(MCiIdentifier.class)) {
				flag = true;
				MCiIdentifier ident = (MCiIdentifier) obj;
				itemp = new MCiIdentifier(ident.getIdentType(),
						ident.getIdentNo(), ident.getIdentCustName());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD,
						ident.getIdentType());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO,
						ident.getIdentNo());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME,
						ident.getIdentCustName());
				identList.add(itemp);
				/** ȡ���� **/
				if (MdmConstants.OPENIDENTFLAG
						.equals(ident.getIsOpenAccIdent())) {
					custName = ident.getIdentCustName();
				}
				if (custNameTemp == null) {
					custNameTemp = ident.getIdentCustName();
				} else {
					if (!custNameTemp.equals(ident.getIdentCustName())) {
						ecifData.setStatus(
								ErrorCode.ERR_ECIF_CUSTNAME.getCode(),
								"֤����Ϣ�еĻ�����ͳһ");
						return;
					}
				}
			} else {
				if (obj.getClass().equals(MCiCustomer.class)) {
					nametilte = (MCiCustomer) obj;
				}
				isBlank = false;
			}
		}
		if (!flag) {
			String msg = String.format(
					"%s/֤����Ϣ��ֵ,֤����Ϣ(֤�����͡�֤�����롢֤������)����ȫ��������",
					ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getChDesc());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(),
					msg);
			log.error("{}:{}", ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(),
					msg);
			return;
		}
		if (custName == null) {
			custName = identList.get(0).getIdentCustName();
		}
		if (nametilte != null) {
			nametilte.setCustName(custName);
		}
		// �����֤ʶ����Ϣ
		// opMp.put("identList", identList);
		// opMp.put("ident", identList);

		// ʶ��
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils
				.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);
		if (MdmConstants.checkCustomerStatus && ecifData.isSuccess()) {
			CustStatus custStatCtrl = null;
			if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(
					ecifData.getCustStatus())) != null) {
				if (!custStatCtrl.isNormal()) {
					if (custStatCtrl.isReOpen()) {
						MCiCustomer customer = null;
						if (ecifData.getWriteModelObj().containsOpModel(
								MCiCustomer.class.getSimpleName())) {
							customer = (MCiCustomer) ecifData
									.getWriteModelObj().getOpModelByName(
											MCiCustomer.class.getSimpleName());
						} else {
							customer = new MCiCustomer();
						}
						customer.setCustStat(MdmConstants.STATE);
						ecifData.getWriteModelObj().setOpModelList(customer);
						ecifData.getWriteModelObj().setDivInsUpd(false);
						// log.info("�ͻ�({})״̬({}),���¿�������",ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
						log.info("�ͻ�({})״̬({}),���¿�������", ecifData.getCustId(),
								custStatCtrl.getCustStatusDesc());
					} else if (!custStatCtrl.isUpdate()) {
						log.warn("�ͻ�({})״̬{}", ecifData.getCustId(),
								custStatCtrl.getCustStatusDesc());
						// ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"�ÿͻ�%s״̬:%s",
						// ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
						ecifData.setStatus(
								ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),
								"�ÿͻ�%s״̬:%s", ecifData.getCustId(),
								custStatCtrl.getCustStatusDesc());
						return;
					}
				}
			}
		}
		if (ecifData.isSuccess()) {
			/**
			 * ��֤����֤���޸�
			 */
			UIdentifierVerif uIdentifierVerif = (UIdentifierVerif) SpringContextUtils
					.getBean("uIdentifierVerif");
			boolean v = uIdentifierVerif.VerifIdentifier(ecifData);
			if (!v) {
				return;
			}
			if (!ecifData.getWriteModelObj().isDivInsUpd()) {
				AddGeneral addGeneral = (AddGeneral) SpringContextUtils
						.getBean("addGeneral");
				try {
					addGeneral.process(ecifData);
					// ecifData.getWriteModelObj().setResult("custNo",
					// ecifData.getEcifCustNo());
					//add by liuming 20170808
					savePhone(ecifData);
					ecifData.getWriteModelObj().setResult(
							MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
							ecifData.getCustId());
					ecifData.setStatus(
							ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getCode(),
							ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getChDesc());
					ecifData.setSuccess(true);

				} catch (Exception e) {
					log.error("���ݲ����쳣", e);
					if (ecifData.isSuccess()) {
						ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
					}
				}
				return;
			} else {
				// ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(),
				// "�ͻ��Ѵ���:" + ecifData.getEcifCustNo());
				// ecifData.getWriteModelObj().setResult("custNo",
				// ecifData.getEcifCustNo());
				ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(),
						"�ͻ��Ѵ���:" + ecifData.getCustId());
				// ecifData.getWriteModelObj().setResult("custNo",
				// ecifData.getCustId());
				ecifData.getWriteModelObj().setResult(
						MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
				return;
			}
		} else {
			if (!ErrorCode.ERR_ECIF_NOT_EXIST_CONTID.getCode().equals(
					ecifData.getRepStateCd())) {
				log.warn("���ط�����������:{}", ecifData.getDetailDes());
				return;
			}
			// ʶ�𲻳ɹ�������
			Accont accont = (Accont) SpringContextUtils.getBean("accont");
			ecifData.resetStatus();
			try {
				accont.process(ecifData, isBlank);
				// ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(
						MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
				String updateSql="update m_ci_person t set t.personal_name='"+custName+"' where t.cust_id='"+ecifData.getCustId()+"'";
				baseDAO.batchExecuteNativeWithIndexParam(updateSql,null);
				//add by liuming 20170808
				savePhone(ecifData);
				//�����ɹ�
				try {
					callProcedure(generalInfoList);
				} catch (Exception e) {
					
					log.error("���ô洢����ʧ��", e);
				}
				//baseDAO.flush();
				
			} catch (Exception e) {
				log.error("������Ϣ", e);
				ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			}
		}
		return;
	}

	public synchronized String callProcedure(List generalInfoList)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		Properties properties = new Properties();
		try {
			InputStream inputStream = OpenCustAccForPerson.class
					.getResourceAsStream("/jdbc.properties");
			properties.load(inputStream);
			inputStream.close(); // �ر���
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = properties.getProperty("jdbc.url");
		String user = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		Connection conn = DriverManager.getConnection(url, user, password);
		try {
			// ������վ��ʱ��洢����
			CallableStatement cstmt = null;
			String procedure = "{call SP_M_CI_LOAD_WZ(?,?,?,?)}";
			cstmt = conn.prepareCall(procedure);
			String identCustName = null;
			String identType = null;
			String identNo = null;
			for (Object obj : generalInfoList) {
				if (obj.getClass().equals(MCiCustomer.class)) {
					MCiCustomer ident = (MCiCustomer) obj;
					identCustName = ident.getCustName();
					identType = ident.getIdentType();
					identNo = ident.getIdentNo();
				}
			}
			cstmt.setString(1, identCustName);
			cstmt.setString(2, identType);
			cstmt.setString(3, identNo);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			// cstmt.setString(1, coreNo);
			cstmt.executeUpdate();
			conn.commit();
			String code = cstmt.getString(4);
			if ("200".equals(code)) {
				log.warn("���ô洢�����쳣:", "���ô洢���̺���ʱ������δ������ʽ���У�֤����Ϊ��" + identNo
						+ ";֤������Ϊ��" + identType + ";֤������Ϊ��" + identCustName);
			} else if ("0".equals(code)) {
				log.info("���ô洢���̳ɹ�,֤����Ϊ��" + identNo + ";֤������Ϊ��" + identType
						+ ";֤������Ϊ��" + identCustName);
			} else {
				log.info("���ô洢����,֤����Ϊ��" + identNo + ";֤������Ϊ��" + identType
						+ ";֤������Ϊ��" + identCustName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void savePhone(EcifData ecifData ){
		//add by liuming 20170808��������(�ֻ���)�����ֻ���mobilePhone
		String mobilePhone = null;
		String belongManager =  null;
		WriteModel writeModel = ecifData.getWriteModelObj();
		MCiPerson mciperson = null;//���˿ͻ���Ϣ
		MCiBelongManager mcibelongmanager =  null;//�����ͻ�����
		if ((mciperson = (MCiPerson) writeModel.getOpModelByName("MCiPerson")) != null) {
			//���mobilePhone�ֶ���ֵ���������������д������ġ�
			mobilePhone = mciperson.getMobilePhone() == null ? "" : mciperson.getMobilePhone().toString();
			if((mcibelongmanager = (MCiBelongManager) writeModel.getOpModelByName("MCiBelongManager")) != null){
				belongManager = mcibelongmanager.getCustManagerNo() == null ? "" : mcibelongmanager.getCustManagerNo().toString();
			}
			if(null != mobilePhone && !mobilePhone.equals("")){
				List result = baseDAO.findWithIndexParam("FROM MCiContmeth C WHERE C." + MdmConstants.CUSTID + "=? AND C.contmethInfo=? AND C.contmethType='213' ",
						""+ecifData.getCustId(MdmConstants.CUSTID_TYPE),mobilePhone.trim());
				if(null == result || result.size() == 0){
					//��ϵ��Ϣ���в����ڸÿͻ����ֻ����룬����������ϵ��Ϣ����CONTMETH_TYPE = '213'(���������ֻ�����)
					String insertSql="insert into m_Ci_Contmeth(CONTMETH_ID,IS_PRIORI,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,STAT,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER) values (SEQ_CONTMETH_ID.Nextval,'9',?,'213',?,'1','VB',sysdate,?) ";
					baseDAO.batchExecuteNativeWithIndexParam(insertSql,ecifData.getCustId(MdmConstants.CUSTID_TYPE),mobilePhone.trim(),belongManager);
				}
			}
		}
		//add end
	}

}
