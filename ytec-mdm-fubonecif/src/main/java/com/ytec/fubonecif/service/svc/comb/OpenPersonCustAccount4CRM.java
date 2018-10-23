package com.ytec.fubonecif.service.svc.comb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.fubonecif.domain.MCiAddress;
import com.ytec.fubonecif.domain.MCiBelongBranch;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiContmeth;
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
 * CRM����һ������
 * 
 * @author ���� wx
 * @date ����ʱ��:2017-8-24����10:45:52
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenPersonCustAccount4CRM implements IEcifBizLogic {

	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory
			.getLogger(OpenCustAccForPerson.class);

	// @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
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
				String isOpenIdentFlag = ident.getIsOpenAccIdent();
				if(!StringUtils.isEmpty(isOpenIdentFlag) && isOpenIdentFlag.equals("Y")){
					itemp = new MCiIdentifier(ident.getIdentType(),
							ident.getIdentNo(), ident.getIdentCustName());
					opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD,
							ident.getIdentType());
					opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO,
							ident.getIdentNo());
					opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME,
							ident.getIdentCustName());
					identList.add(itemp);
				}
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
		 opMp.put("identList", identList);

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
					// add by liuming 20170808
					//savePhone(ecifData);
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
				ecifData.getWriteModelObj().setResult(
						MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
				// �����ɹ�
				saveData(ecifData);//�������
			} catch (Exception e) {
				e.printStackTrace();
				String logMsg = "ECIF����ʧ�ܣ�����ԭ��" + e.getMessage();
				log.error(logMsg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
				ecifData.setSuccess(false);
				return;
			}
		}
		return;
	}
	
	
	@Transactional
	private EcifData saveData(EcifData ecifData){
		try {
			if (ecifData == null) {
				String logMsg = "CRM���󿪻�ʱ�������ʧ�ܣ�����ԭ�򣺴�������Ϊ��";
				log.error(logMsg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
				ecifData.setSuccess(false);
				return ecifData;//
			}
			String ecifId = ecifData.getCustId();//�ɹ������Ŀͻ���
			if(StringUtils.isEmpty(ecifId)){
				String logMsg = "CRM���󿪻�ʱ�������ʧ�ܣ�����ԭ��ECIF�ͻ���Ϊ��";
				log.error(logMsg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
				ecifData.setSuccess(false);
				return ecifData;//
			}
			Element repNode = ecifData.getRepNode();
			if(repNode == null){
				repNode = DocumentHelper.createElement("ResponseBody");
			}
			List<Object> objList = ecifData.getWriteModelObj().getOpModelList();
			if (objList != null && objList.size() >= 0) {
				for (Object obj : objList) {
					if(obj.getClass().equals(MCiCustomer.class)){
						//����ͻ���Ϣ
						MCiCustomer customerInfo = (MCiCustomer) obj;
						customerInfo.setCustId(ecifId);
						this.baseDAO.merge(customerInfo);
					}else if(obj.getClass().equals(MCiIdentifier.class)){
						//����֤����Ϣ
						MCiIdentifier identifierInfo = (MCiIdentifier) obj;
						if(StringUtils.isEmpty(identifierInfo.getIdentNo())
								|| StringUtils.isEmpty(identifierInfo.getIdentType())
								|| StringUtils.isEmpty(identifierInfo.getIdentCustName())){
							String logMsg = "CRM���󿪻�ʱ�������ʧ�ܣ�����ԭ��֤����Ϣ��ȫ";
							log.error(logMsg);
							ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
							ecifData.setSuccess(false);
							return ecifData;//
						}else{
							identifierInfo.setCustId(ecifId);
							//ECIF�ͻ��ź�֤��������Ϊ��������
							String queIdentInfo = "select t from MCiIdentifier t where t.custId=:ecifId and t.identType=:identType";
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("ecifId", ecifId);
							params.put("identType", identifierInfo.getIdentType());
							List<MCiIdentifier> identInfoList = this.baseDAO.findWithNameParm(queIdentInfo, params);
							if(identInfoList == null || identInfoList.size() != 1){
								String sql_nextIdentId = "SELECT SEQ_IDENT_ID.NEXTVAL FROM DUAL";
								List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextIdentId);
								String str_nextIdentId = "100000";
								if (l_o != null && l_o.size() >= 1) {
									Object o = l_o.get(0);
									if (o != null && !o.toString().equals("")) {
										str_nextIdentId = o.toString();
									}
								}
								identifierInfo.setIdentId(str_nextIdentId);
							}else{
								identifierInfo = identInfoList.get(0);
							}
							
							this.baseDAO.merge(identifierInfo);
							//���ؼ�������
							Element eleIdentType = DocumentHelper.createElement("identType");//��ϵ��ʽ
							eleIdentType.setText(identifierInfo.getIdentType());
							Element eleIdentId = DocumentHelper.createElement("identId");//��ϵ��ʽID
							eleIdentId.setText(identifierInfo.getIdentId());
							Element eleIdentify = DocumentHelper.createElement("identifyInfo");//��ϵ��ʽ�ڵ�
							eleIdentify.add(eleIdentType);
							eleIdentify.add(eleIdentId);
							repNode.add(eleIdentify);
							ecifData.setRepNode(repNode);//��ӵ�������Ϣ
							System.err.println("��ǰ��Ϣ����identType��"+identifierInfo.getIdentType());
							System.err.println("��ǰ��Ϣ����identId��"+identifierInfo.getIdentId());
							System.err.println("��ǰ��Ϣ����identifyInfo��"+eleIdentify.asXML());
						}
					}else if(obj.getClass().equals(MCiPerson.class)){
						//���������Ϣ
						MCiPerson personInfo = (MCiPerson) obj;
						personInfo.setCustId(ecifId);
						this.baseDAO.merge(personInfo);
					}else if(obj.getClass().equals(MCiContmeth.class)){
						//������ϵ��ʽ��Ϣ
						MCiContmeth contmethInfo = (MCiContmeth) obj;
						contmethInfo.setCustId(ecifId);
						String contmethType = contmethInfo.getContmethType();
						String slqContmethInfo = "select t from MCiContmeth t where t.custId=:ecifId and t.contmethType=:contmethType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("ecifId", ecifId);
						params.put("contmethType", contmethType);
						List<MCiContmeth> contmethList = this.baseDAO.findWithNameParm(slqContmethInfo, params);
						//��ϵ��ʽ����ECIF�ͻ��ź���ϵ��ʽ������Ϊ�����������������ͬһECIF�ͻ���ͬһ��ϵ��ʽ���͵Ķ�������
						if(contmethList == null || contmethList.size() != 1){
							String sql_nextContmethId = "SELECT SEQ_CONTMETH_ID.NEXTVAL FROM DUAL";
							List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextContmethId);
							String str_nextContmethId = "100000";
							if (l_o != null && l_o.size() >= 1) {
								Object o = l_o.get(0);
								if (o != null && !o.toString().equals("")) {
									str_nextContmethId = o.toString();
								}
							}
							contmethInfo.setContmethId(str_nextContmethId);
						}else{
							contmethInfo = contmethList.get(0);
						}
						this.baseDAO.merge(contmethInfo);
						//���ؼ�������
						Element eleContmenthType = DocumentHelper.createElement("contmenthType");//��ϵ��ʽ
						eleContmenthType.setText(contmethInfo.getContmethType());
						Element eleContmethId = DocumentHelper.createElement("contmethId");//��ϵ��ʽID
						eleContmethId.setText(contmethInfo.getContmethId());
						Element eleContmenth = DocumentHelper.createElement("contMeth");//��ϵ��ʽ�ڵ�
						eleContmenth.add(eleContmenthType);
						eleContmenth.add(eleContmethId);
						repNode.add(eleContmenth);
						ecifData.setRepNode(repNode);//��ӵ�������Ϣ
						System.err.println("��ǰ��Ϣ����contmenthType��"+contmethInfo.getContmethType());
						System.err.println("��ǰ��Ϣ����contmethId��"+contmethInfo.getContmethId());
						System.err.println("��ǰ��Ϣ����repNode��"+eleContmenth.asXML());
//						ecifData.getWriteModelObj().setResult("contmeth", params);
					}else if(obj.getClass().equals(MCiAddress.class)){
						//�����ַ��Ϣ
						MCiAddress addressInfo = (MCiAddress) obj;
						String addrType = addressInfo.getAddrType();
						String queAddrInfo = "select t from MCiAddress t where t.custId=:ecifId and t.addrType=:addrType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("ecifId", ecifId);
						params.put("addrType", addrType);
						List<MCiAddress> addrInfoList = this.baseDAO.findWithNameParm(queAddrInfo, params);
						if(addrInfoList == null || addrInfoList.size() != 1){
							String sql_nextAddressId = "SELECT SEQ_ADDR_ID.NEXTVAL FROM DUAL";
							List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextAddressId);
							String str_nextAddressId = "100000";
							if (l_o != null && l_o.size() >= 1) {
								Object o = l_o.get(0);
								if (o != null && !o.toString().equals("")) {
									str_nextAddressId = o.toString();
								}
							}
							addressInfo.setAddrId(str_nextAddressId);
						}else{
							addressInfo = addrInfoList.get(0);
						}
						addressInfo.setCustId(ecifId);
						this.baseDAO.merge(addressInfo);
						//���ؼ�������
						Element eleAddrType = DocumentHelper.createElement("addrType");//��ϵ��ʽ
						eleAddrType.setText(addressInfo.getAddrType());
						Element eleAddrId = DocumentHelper.createElement("addrId");//��ϵ��ʽID
						eleAddrId.setText(addressInfo.getAddrId());
						Element eleAddr = DocumentHelper.createElement("address");//��ϵ��ʽ�ڵ�
						eleAddr.add(eleAddrType);
						eleAddr.add(eleAddrId);
						repNode.add(eleAddr);
						ecifData.setRepNode(repNode);//��ӵ�������Ϣ
						System.err.println("��ǰ��Ϣ����addrType��"+addressInfo.getAddrType());
						System.err.println("��ǰ��Ϣ����addrId��"+addressInfo.getAddrId());
						System.err.println("��ǰ��Ϣ����address��"+eleAddr.asXML());
					}else if(obj.getClass().equals(MCiBelongManager.class)){
						//��������ͻ�������Ϣ
						MCiBelongManager belongMgrInfo = (MCiBelongManager) obj;
						String sql_nextBelongMgrId = "SELECT SEQ_BELONG_MANAGER_ID.NEXTVAL FROM DUAL";
						List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextBelongMgrId);
						String str_nextBelongMgrId = "100000";
						if (l_o != null && l_o.size() >= 1) {
							Object o = l_o.get(0);
							if (o != null && !o.toString().equals("")) {
								str_nextBelongMgrId = o.toString();
							}
						}
						belongMgrInfo.setBelongManagerId(str_nextBelongMgrId);
						belongMgrInfo.setCustId(ecifId);
						this.baseDAO.merge(belongMgrInfo);
					}else if(obj.getClass().equals(MCiBelongBranch.class)){//�ͻ�����������Ϣ
						MCiBelongBranch belongBranch = (MCiBelongBranch) obj;
						String sql_nextBelongBranchId = "select SEQ_BELONG_BRANCH_ID.nextval from dual";
						List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextBelongBranchId);
						String str_nextBelongBranchId = "100000";
						if (l_o != null && l_o.size() >= 1) {
							Object o = l_o.get(0);
							if (o != null && !o.toString().equals("")) {
								str_nextBelongBranchId = o.toString();
							}
						}
						belongBranch.setBelongBranchId(str_nextBelongBranchId);
						belongBranch.setCustId(ecifId);
						this.baseDAO.merge(belongBranch);
					}
					
				}
			}
			ecifData.setStatus(ErrorCode.SUCCESS);
			ecifData.setSuccess(true);
			return ecifData;
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "CRM���󿪻�ʱ�������ʧ�ܣ�����ԭ��"+e.getMessage();
			log.error(logMsg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
			ecifData.setSuccess(false);
			return ecifData;//
		}
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
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	public void savePhone(EcifData ecifData) {
		// add by liuming 20170808��������(�ֻ���)�����ֻ���mobilePhone
		String mobilePhone = null;
		String belongManager = null;
		WriteModel writeModel = ecifData.getWriteModelObj();
		MCiPerson mciperson = null;// ���˿ͻ���Ϣ
		MCiBelongManager mcibelongmanager = null;// �����ͻ�����
		if ((mciperson = (MCiPerson) writeModel.getOpModelByName("MCiPerson")) != null) {
			// ���mobilePhone�ֶ���ֵ���������������д������ġ�
			mobilePhone = mciperson.getMobilePhone() == null ? "" : mciperson
					.getMobilePhone().toString();
			if ((mcibelongmanager = (MCiBelongManager) writeModel
					.getOpModelByName("MCiBelongManager")) != null) {
				belongManager = mcibelongmanager.getCustManagerNo() == null ? ""
						: mcibelongmanager.getCustManagerNo().toString();
			}
			if (null != mobilePhone && !mobilePhone.equals("")) {
				List result = baseDAO
						.findWithIndexParam(
								"FROM MCiContmeth C WHERE C."
										+ MdmConstants.CUSTID
										+ "=? AND C.contmethInfo=? AND C.contmethType='213' ",
								""
										+ ecifData
												.getCustId(MdmConstants.CUSTID_TYPE),
								mobilePhone.trim());
				if (null == result || result.size() == 0) {
					// ��ϵ��Ϣ���в����ڸÿͻ����ֻ����룬����������ϵ��Ϣ����CONTMETH_TYPE =
					// '213'(���������ֻ�����)
					String insertSql = "insert into m_Ci_Contmeth(CONTMETH_ID,IS_PRIORI,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,STAT,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER) values (SEQ_CONTMETH_ID.Nextval,'9',?,'213',?,'1','VB',sysdate,?) ";
					baseDAO.batchExecuteNativeWithIndexParam(insertSql,
							ecifData.getCustId(MdmConstants.CUSTID_TYPE),
							mobilePhone.trim(), belongManager);
				}
			}
		}
		// add end
	}
}
