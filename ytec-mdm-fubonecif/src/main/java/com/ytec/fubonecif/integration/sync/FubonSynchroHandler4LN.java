/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.integration.sync
 * @�ļ�����SynchroToSystemHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:08:43
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.integration.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ytec.fubonecif.domain.MCiAddress;
import com.ytec.fubonecif.domain.MCiBelongBranch;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiGrade;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiOrg;
import com.ytec.fubonecif.domain.MCiOrgBusiinfo;
import com.ytec.fubonecif.domain.MCiOrgExecutiveinfo;
import com.ytec.fubonecif.domain.MCiOrgIssuestock;
import com.ytec.fubonecif.domain.MCiOrgKeyflag;
import com.ytec.fubonecif.domain.MCiOrgRegisterinfo;
import com.ytec.fubonecif.domain.MCiPerKeyflag;
import com.ytec.fubonecif.domain.MCiPerMateinfo;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxLog;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;
import com.ytec.mdm.integration.sync.ptsync.SynchroExecuteHandler;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.interfaces.socket.normalsocket.client.SocketClient;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�FubonSynchroHandler4LN
 * @�����������һ��������ͬ�������࣬�Ŵ�ϵͳ�ͻ���
 * @��������:
 * @�����ˣ�wangtb@yuchengtech.com
 * @����ʱ�䣺
 * @�޸��ˣ�wangtb@yuchengtech.com
 * @�޸�ʱ�䣺
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Component
@Scope("prototype")
public class FubonSynchroHandler4LN extends SynchroExecuteHandler {
	private static Logger log = LoggerFactory.getLogger(FubonSynchroHandler4LN.class);
	private JPABaseDAO<?, ?> baseDAO = null;
	private Document requestDoc;
	private String syncCustNo;
	private String syncCustType;
	private String origTxReqSeqNo;

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice) {
		syncCustNo = txEvtNotice.getCustNo();
		origTxReqSeqNo = txEvtNotice.getTxFwId();
		if (StringUtil.isEmpty(syncCustNo)) {
			log.error("ͬ���ͻ��Ŀͻ���Ϊ��");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ���ͻ��Ŀͻ���Ϊ��");
			return false;
		}

		String custType = null;
		// TODO: ��ȡcustType��ͨ���ͻ��Ų�ѯcustomer��
		baseDAO = (JPABaseDAO<?, ?>) SpringContextUtils.getBean("baseDAO");
		MCiCustomer cust = null;

		List<MCiCustomer> custList = baseDAO.findWithIndexParam("FROM MCiCustomer where custId=?", syncCustNo);

		if (custList != null && custList.size() > 1) {
			String msg = String.format("���ݴ��󣬿ͻ���[m_ci_customer]������(�ͻ���: %s)��Ψһ", syncCustNo);
			log.error(msg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "��" + msg);
			return false;
		} else if (custList.size() == 0) {
			String msg = String.format("���ݴ��󣬿ͻ���[m_ci_customer]������(�ͻ���: )������", syncCustNo);
			log.error(msg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "��" + msg);
			return false;
		}
		
		cust = custList.get(0);
		custType = cust.getCustType();
		if (StringUtil.isEmpty(custType)) {
			String msg = String.format("���ݴ��󣬿ͻ���[m_ci_customer]�����ݿͻ�����Ϊ�գ��ͻ���: ", syncCustNo);
			log.error(msg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + ", " + msg);
			return false;
		}

		// Ǳ�ڿͻ���ͬ��
		if (MdmConstants.IS_POTENTIAL_CUST.equals(cust.getPotentialFlag())) {
//			String msg = String.format("�ͻ�(�ͻ���: %s)ΪǱ�ڿͻ�����ͬ����Ϣ", syncCustNo);
//			log.warn(msg);
//			txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
//			txEvtNotice.setEventDealInfo(msg);
//			return false;
			//�ж��Ƿ����Ŵ���
			//modify by liuming 20170524
			if(cust.getLoanCustStat() == null || cust.getLoanCustStat().equals("")){
				String msg = String.format("�ͻ�(�ͻ���: %s)ΪǱ�ڿͻ�����ͬ����Ϣ", syncCustNo);
				log.warn(msg);
				txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
				txEvtNotice.setEventDealInfo(msg);
				return false;
			}

		}

		List<MCiCustomer> crsIdx = baseDAO.findWithIndexParam("FROM MCiCrossindex where custId=? and srcSysNo=?", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
		if (crsIdx != null) {
			if (crsIdx.size() == 0) {
//				String msg = String.format("�ͻ�(�ͻ���: %s)�����Ŵ�(%s)�ͻ�������Ҫͬ����Ϣ", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
//				log.warn(msg);
//				txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
//				txEvtNotice.setEventDealInfo(ErrorCode.SUCCESS.getCode() + "��" + msg);
//				return false;
				//�ж��Ƿ����Ŵ���
				//modify by liuming 20170524
				if(cust.getLoanCustStat() == null || cust.getLoanCustStat().equals("")){
				   String msg = String.format("�ͻ�(�ͻ���: %s)�����Ŵ�(%s)�ͻ�������Ҫͬ����Ϣ", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
				   log.warn(msg);
				   txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
				   txEvtNotice.setEventDealInfo(ErrorCode.SUCCESS.getCode() + "��" + msg);
				   return false;
			  }
			} else if (crsIdx.size() > 1) {
				String msg = String.format("���ݴ��󣬽���������[m_ci_crossindex]������(�ͻ���: %s,ϵͳ:%s)��Ψһ", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
				log.error(msg);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
				txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "��" + msg);
				return false;
			}
		}
		syncCustType = custType;
		// orgCustType
		if (MdmConstants.TX_CUST_ORG_TYPE.equals(custType)) {
			return asseReqMsg4Com(cust, txEvtNotice);
		}
		// perCustType
		else if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
			return asseReqMsg4Indiv(cust, txEvtNotice);
		} else {
			log.error("ͬ���Ŵ�ϵͳ���󣬴���Ŀͻ�����:{}", custType);
			return false;
		}
	}

	// TODO �ı��ĸ�ʽ
	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {

		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean asseReqMsg4Com(MCiCustomer cust, TxEvtNotice txEvtNotice) {
		requestDoc = DocumentHelper.createDocument();
		String custNo = cust.getCustId();
		// �ͻ���
		List<MCiCustomer> custList = baseDAO.findWithIndexParam("FROM MCiCustomer where custId=?", custNo);
		List<MCiIdentifier> identList = baseDAO.findWithIndexParam("FROM MCiIdentifier where custId=?", custNo);
		List<MCiOrg> orgList = baseDAO.findWithIndexParam("FROM MCiOrg where custId=?", custNo);
		List<MCiAddress> addrList = baseDAO.findWithIndexParam("FROM MCiAddress where custId=?", custNo);
		// List<MCiGrade> gradeList = baseDAO.findWithIndexParam("FROM MCiGrade where custId=?", custNo);
		List<MCiOrgBusiinfo> busiinfoList = baseDAO.findWithIndexParam("FROM MCiOrgBusiinfo where custId=?", custNo);
		List<MCiOrgRegisterinfo> reginfoList = baseDAO.findWithIndexParam("FROM MCiOrgRegisterinfo where custId=?", custNo);
		List<MCiOrgExecutiveinfo> executiveinfoList = baseDAO.findWithIndexParam("FROM MCiOrgExecutiveinfo where orgCustId=?", custNo);
		List<MCiOrgIssuestock> issuestockList = baseDAO.findWithIndexParam("FROM MCiOrgIssuestock where custId=?", custNo);
		List<MCiOrgKeyflag> keyflagList = baseDAO.findWithIndexParam("FROM MCiOrgKeyflag where custId=?", custNo);
		//add by liuming 20170712
		List<MCiBelongManager> belongManagerList = baseDAO.findWithIndexParam("FROM MCiBelongManager where custId=?", custNo);
		List<MCiBelongBranch> belongBranchList = baseDAO.findWithIndexParam("FROM MCiBelongBranch where custId=?", custNo);
		//
		MCiOrg org = null;
		MCiIdentifier ident = null;
		MCiOrgKeyflag keyflag = null;
		// MCiGroupInfo groupInfo = null;
		MCiOrgBusiinfo busiinfo = null;
		MCiOrgIssuestock issuestock = null;
		MCiOrgRegisterinfo reginfo = null;
		MCiOrgExecutiveinfo executiveinfo = null;
		//add by liuming 20170712
		MCiBelongManager belongManagerInfo = null;
		MCiBelongBranch belongBranchInfo = null;

		if (reginfoList != null && reginfoList.size() > 0) {
			reginfo = reginfoList.get(0);
		}

		if (custList.size() > 1) {
			log.error("���ݴ��󣬿ͻ���[m_ci_customer]������(�ͻ���{})��Ψһ", custNo);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "���ͻ���[m_ci_customer]������(�ͻ���" + custNo + ")��Ψһ");
			return false;
		}

		if (orgList == null || orgList.size() != 1) {
			String errMsg = String.format("���ݴ��󣬿ͻ���[m_ci_org]������(�ͻ���:%s)��¼��(%d)����", custNo, orgList.size());
			log.error(errMsg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "��" + errMsg);
			return false;
		} else {
			org = orgList.get(0);
		}

		boolean result = false;
		try {
			requestDoc.setXMLEncoding("GB2312");
			Element transBody = requestDoc.addElement("TransBody");
			Element requestHeader = transBody.addElement("RequestHeader");
			requestHeader.addElement("DestSysCd").setText("LN");

			Element requestBody = transBody.addElement("RequestBody");
			Element dataNode;

			// ���Ŵ�ֱ��
			// dataNode = requestDoc.addElement("Packet").addElement("Data");
			// ͨ��EAI���Ŵ�����
			dataNode = requestBody.addElement("Packet").addElement("Data");

			// ���Ŵ����������У��ڵ�˳��̶�������һ���ڵ�����ĸ��д�������Ŵ�ϵͳ��������ʱ���������
			Element reqNode = dataNode.addElement("Req");
			Element pubNode = dataNode.addElement("Pub");

			Element orgNode = reqNode.addElement("Org");
			Element addrNode = reqNode.addElement("Address");
			Element custNode = reqNode.addElement("Customer");
			Element identNode = reqNode.addElement("Identifier");
			Element gradeNode = reqNode.addElement("Grade");
			Element groupInfoNode = reqNode.addElement("GroupInfo");
			Element busiinfoNode = reqNode.addElement("OrgBusiinfo");
			Element executiveinfoNode = reqNode.addElement("OrgExecutiveinfo");
			Element issuestockNode = reqNode.addElement("OrgIssuestock");
			Element keyflagNode = reqNode.addElement("OrgKeyflag");
			Element registerinfoNode = reqNode.addElement("OrgRegisterinfo");

			// ���ýڵ�Ϊ<node></node>��ʽ
			reqNode.setText("");
			orgNode.setText("");
			addrNode.setText("");
			custNode.setText("");
			identNode.setText("");
			gradeNode.setText("");
			groupInfoNode.setText("");
			busiinfoNode.setText("");
			executiveinfoNode.setText("");
			issuestockNode.setText("");
			keyflagNode.setText("");
			registerinfoNode.setText("");

			pubNode.addElement("prcscd").setText("receiveCusComInfo");

			orgNode.addElement("annualIncome").setText(org.getAnnualIncome() == null ? "" : "" + org.getAnnualIncome());// Ԥ��Ӫҵ���� TODO
			//modify by liuming 20170714
//			orgNode.addElement("areaCode").setText(org.getAreaCode() == null ? "" : org.getAreaCode());// ע�����������
			String areaCode =  null;
			//�ȴӻ������в�ѯ,��ѯ�����ٵ�ע����в�ѯ.
			//������û�и��£�ֱ��ȥע����ѯ
			areaCode = reginfo.getRegisterArea();
//			if(org.getAreaCode() == null || org.getAreaCode().equals("")){
//				if(reginfo == null || reginfo.getRegisterArea()== null){
//					areaCode = "";
//				}else{
//					areaCode = reginfo.getRegisterArea();
//				}
//			}else{
//				areaCode = org.getAreaCode();
//			}
			orgNode.addElement("areaCode").setText(areaCode);// ע�����������
			orgNode.addElement("buildDate").setText(org.getBuildDate() == null ? "" : "" + org.getBuildDate());// �������� TODO
			orgNode.addElement("comHoldType").setText(org.getComHoldType() == null ? "" : org.getComHoldType());// �ع�����
			orgNode.addElement("comSpBusiness").setText(org.getComSpBusiness() == null ? "" : org.getComSpBusiness());// ���־�Ӫ��ʶ
			orgNode.addElement("comSpDetail").setText(org.getComSpDetail() == null ? "" : org.getComSpDetail());// ���־�Ӫ���
			orgNode.addElement("comSpEndDate").setText(org.getComSpEndDate() == null ? "" : "" + org.getComSpEndDate());// ���־�Ӫ�������� TODO
			orgNode.addElement("comSpLicNo").setText(org.getComSpLicNo() == null ? "" : org.getComSpLicNo());// ���־�Ӫ���֤���
			orgNode.addElement("comSpLicOrg").setText(org.getComSpLicOrg() == null ? "" : org.getComSpLicOrg());// �������֤�䷢����
			orgNode.addElement("comSpStrDate").setText(org.getComSpStrDate() == null ? "" : org.getComSpStrDate().toString());// ���־�Ӫ��ʼ���� TODO
			orgNode.addElement("employeeScale").setText(org.getEmployeeScale() == null ? "" : org.getEmployeeScale());// ��ҵ����
			orgNode.addElement("entBelong").setText(org.getEntBelong() == null ? "" : org.getEntBelong());// ������ϵ
			orgNode.addElement("entProperty").setText(org.getEntProperty() == null ? "" : org.getEntProperty());// ��ҵ����
			orgNode.addElement("entScale").setText(org.getEntScale() == null ? "" : org.getEntScale());// ��ҵ��ģ�����ࣩ
			orgNode.addElement("entScaleRh").setText(org.getEntScaleRh() == null ? "" : org.getEntScaleRh());// ��ҵ��ģ�����У�
			orgNode.addElement("finRepType").setText(org.getFinRepType() == null ? "" : org.getFinRepType());// ���񱨱�����
			orgNode.addElement("fundSource").setText(org.getFundSource() == null ? "" : org.getFundSource());// ������Դ
			orgNode.addElement("holdStockAmt").setText(org.getHoldStockAmt() == null ? "" : org.getHoldStockAmt().toString());// ӵ�����йɷݽ�� TODO
			orgNode.addElement("inCllType").setText(org.getInCllType() == null ? "" : org.getInCllType());// ������ҵ���
			orgNode.addElement("industryCategory").setText(org.getIndustryCategory() == null ? "" : org.getIndustryCategory());// ��ҵ���ࣨ��ҵ��ģ��
			orgNode.addElement("investType").setText(org.getInvestType() == null ? "" : org.getInvestType());// Ͷ������
			orgNode.addElement("legalReprGender").setText(org.getLegalReprGender() == null ? "" : org.getLegalReprGender());// �����������Ա�
			orgNode.addElement("legalReprIdentNo").setText(org.getLegalReprIdentNo() == null ? "" : org.getLegalReprIdentNo());// ����������/������֤������
			orgNode.addElement("legalReprIdentType").setText(org.getLegalReprIdentType() == null ? "" : org.getLegalReprIdentType());// ����������/������֤������
			orgNode.addElement("legalReprName").setText(org.getLegalReprName() == null ? "" : org.getLegalReprName());// ����������/����������
			orgNode.addElement("legalReprTel").setText(org.getLegalReprTel() == null ? "" : org.getLegalReprTel());// ����������/��������ϵ�绰
			orgNode.addElement("loadCardAuditDt").setText(org.getLoadCardAuditDt() == null ? "" : org.getLoadCardAuditDt().toString());// �������������� TODO
			orgNode.addElement("loadCardPwd").setText(org.getLoadCardPwd() == null ? "" : org.getLoadCardPwd());// �������
			orgNode.addElement("loanCardFlag").setText(org.getLoanCardFlag() == null ? "" : org.getLoanCardFlag());// ���޴��
			orgNode.addElement("loanCardNo").setText(org.getLoanCardNo() == null ? "" : org.getLoanCardNo());// ������
			orgNode.addElement("loanCardStat").setText(org.getLoanCardStat() == null ? "" : org.getLoanCardStat());// �����Ч��־
			orgNode.addElement("mainBusiness").setText(org.getMainBusiness() == null ? "" : org.getMainBusiness());// ��Ӫҵ��Χ
//			orgNode.addElement("mainIndustry").setText(org.getMainIndustry() == null ? "" : org.getMainIndustry());// ��ҵ����
			//modify by liuming 20170712
			orgNode.addElement("mainIndustry").setText(org.getInCllType() == null ? "" : org.getInCllType());// ��ҵ����
			
			orgNode.addElement("minorBusiness").setText(org.getMinorBusiness() == null ? "" : org.getMinorBusiness());// ��Ӫ��Χ
			orgNode.addElement("nationCode").setText(org.getNationCode() == null ? "" : org.getNationCode());// ����
			orgNode.addElement("orgCustType").setText(org.getOrgCustType() == null ? "" : org.getOrgCustType());// �ͻ�����
			orgNode.addElement("orgFex").setText(org.getOrgFex() == null ? "" : org.getOrgFex());// ����
			orgNode.addElement("orgHomepage").setText(org.getOrgHomepage() == null ? "" : org.getOrgHomepage());// ��ַ
			orgNode.addElement("orgTel").setText(org.getOrgTel() == null ? "" : org.getOrgTel());// ��ϵ�绰
			orgNode.addElement("orgZipcode").setText(org.getOrgZipcode() == null ? "" : org.getOrgZipcode());// ��������
			orgNode.addElement("superDept").setText(org.getSuperDept() == null ? "" : org.getSuperDept());// �ϼ����ܵ�λ
			orgNode.addElement("topCorpLevel").setText(org.getTopCorpLevel() == null ? "" : org.getTopCorpLevel());// ��ͷ��ҵ
			orgNode.addElement("totalAssets").setText(org.getTotalAssets() == null ? "" : org.getTotalAssets().toString());// Ԥ������ʲ��ܶ� TODO
			//add by liuming 20170524
			orgNode.addElement("creditCode").setText(org.getCreditCode() == null ? "" : org.getCreditCode());// �������ô���
			orgNode.addElement("usCreditcode").setText(org.getBusiLicNo() == null ? "" : org.getBusiLicNo());// ͳһ������ô���
			orgNode.addElement("loanOrgType").setText(org.getLoanOrgType() == null ? "" : org.getLoanOrgType());// ��֯�������
			orgNode.addElement("flagCapDtl").setText(org.getFlagCapDtl() == null ? "" : org.getFlagCapDtl());// ��֯�������ϸ��
			orgNode.addElement("yearRate").setText(org.getYearRate() == null ? "" : org.getYearRate());// �껯���˱���
			orgNode.addElement("orgState").setText(org.getOrgState() == null ? "" : org.getOrgState());// ����״̬
			orgNode.addElement("basCusState").setText(org.getBasCusState()== null ? "" : org.getBasCusState());// ������״̬
			// TODO
			// String actBusiAddr = null;// ʵ�ʾ�Ӫ��ַ addrType=08
			String legalReprAddr = null;// ���˴�������ַ or M_CI_ORG.legal_repr_addr
			String postAddr = null;// ͨѶ��ַ addrType=02 or M_CI_ORG.org_addr
			String registerAddr = null;// ע��Ǽǵ�ַ addrType=07 or M_CI_ORG_REGISTERINFO.register_addr
			String registerEnAddr = null;// ����ע��Ǽǵ�ַM_CI_ORG_REGISTERINFO.register_en_addr

			legalReprAddr = org.getLegalReprAddr();// org �������ݲ�ԭ���ϲ���Ϊ��
			postAddr = org.getOrgAddr();
			registerAddr = reginfo == null ? null : reginfo.getRegisterAddr();
			registerEnAddr = reginfo == null ? null : reginfo.getRegisterEnAddr();

			// ���˴�������ַ ���޷��ӵ�ַ���л�ȡ���ӻ�����ORG ��ȡ�ֶ� legal_repr_addr
			addrNode.addElement("legalReprAddr").setText(legalReprAddr == null ? "" : legalReprAddr);
			if (addrList != null && addrList.size() > 0) {
				for (MCiAddress address : (List<MCiAddress>) addrList) {
					
					// ʵ�ʾ�Ӫ��ַ addrType=08
					if (MdmConstants.ADDR_TYPE_ACT_BUSI.equals(address.getAddrType())) {
						addrNode.addElement("actBusiAddr").setText(address.getAddr() == null ? "" : address.getAddr()); // ��ַ��Ϣ ��ϸ��ַ����ַ���ͣ�'08'��ʵ�ʾ�Ӫ��ַ ʵ�ʾ�Ӫ��ַ acu_addr
					}
					//modify by liuyx 20171221 ��ע�͵���д������ѭ�����ÿһ���ִ��if��else���һ�����ᵼ������˺ܶ��ظ���
					// ͨѶ��ַ addrType=02 or ORG.org_addr
					/*if (postAddr == null && MdmConstants.ADDR_TYPE_POST.equals(address.getAddrType())) {
						addrNode.addElement("postAddr").setText(address.getAddr() == null ? "" : address.getAddr());
					} else {
						addrNode.addElement("postAddr").setText(postAddr == null ? "" : postAddr);
					}*/
					if (MdmConstants.ADDR_TYPE_POST.equals(address.getAddrType())) {
						if(postAddr == null){
							addrNode.addElement("postAddr").setText(address.getAddr() == null ? "" : address.getAddr());
						} else {
							addrNode.addElement("postAddr").setText(postAddr == null ? "" : postAddr);
						}
					}
					//modify by liuyx 20171221
					// ע��Ǽǵ�ַ addrType=07 or ORG.register_addr
					/*if (registerAddr == null && MdmConstants.ADDR_TYPE_REG.equals(address.getAddrType())) {
						addrNode.addElement("registerAddr").setText(address.getAddr() == null ? "" : address.getAddr());
						addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
					} else if (address != null) {
						addrNode.addElement("registerAddr").setText(registerAddr == null ? "" : registerAddr);
						addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
					}*/
					//ȫ�пͻ���ѯ�޸�ע����Ϣֻ�����˵�ַ��Ϣ������ֻȡ��ַ��Ϣ���ע���ַ��Ϣ
					if (MdmConstants.ADDR_TYPE_REG.equals(address.getAddrType())) {
						addrNode.addElement("registerAddr").setText(address.getAddr() == null ? "" : address.getAddr());
						addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
//						if(registerAddr == null){
//							addrNode.addElement("registerAddr").setText(address.getAddr() == null ? "" : address.getAddr());
//							addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
//						}else{
//							addrNode.addElement("registerAddr").setText(registerAddr == null ? "" : registerAddr);
//							addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
//						}
					}
					//modify by liuyx 20171221
					// ����ע��Ǽǵ�ַ ORG.register_en_addr
					/*if (registerEnAddr == null && MdmConstants.ADDR_TYPE_REG.equals(address.getEnAddr())) {
						addrNode.addElement("registerEnAddr").setText(address.getAddr());
					} else {
						addrNode.addElement("registerEnAddr").setText(registerEnAddr == null ? "" : registerEnAddr);
					}*/
					if (MdmConstants.ADDR_TYPE_REG.equals(address.getEnAddr())) {
						if(registerEnAddr == null){
							addrNode.addElement("registerEnAddr").setText(address.getAddr());
						}else{
							addrNode.addElement("registerEnAddr").setText(registerEnAddr == null ? "" : registerEnAddr);
						}
					}
				}
			}
			// TODO
			custNode.addElement("custId").setText(custNo);
			custNode.addElement("arCustFlag").setText(cust.getArCustFlag() == null ? "" : cust.getArCustFlag());// ����ͻ���־��AR�ͻ���־(CSPS)��
			custNode.addElement("arCustType").setText(cust.getArCustType() == null ? "" : cust.getArCustType());// ����ͻ����ͣ�AR�ͻ�����(CSPS)��
			custNode.addElement("createBranchNo").setText(cust.getCreateBranchNo() == null ? "" : cust.getCreateBranchNo());// �Ǽǻ���
			custNode.addElement("createDate").setText(cust.getCreateDate() == null ? "" : cust.getCreateDate().toString());// �Ǽ����� TODO
			custNode.addElement("createTellerNo").setText(cust.getCreateTellerNo() == null ? "" : cust.getCreateTellerNo());// �Ǽ���
			custNode.addElement("cusBankRel").setText(cust.getCusBankRel() == null ? "" : cust.getCusBankRel());// �����й�����ϵ
			custNode.addElement("cusCorpRel").setText(cust.getCusCorpRel() == null ? "" : cust.getCusCorpRel());// �����к�����ϵ
			custNode.addElement("custName").setText(cust.getCustName() == null ? "" : cust.getCustName());// �ͻ�����
			custNode.addElement("enName").setText(cust.getEnName() == null ? "" : cust.getEnName());// ��������
			custNode.addElement("inoutFlag").setText(cust.getInoutFlag() == null ? "" : cust.getInoutFlag());// ���ھ����־
			//custNode.addElement("loanCustMgr").setText(cust.getLoanCustMgr() == null ? "" : cust.getLoanCustMgr());// ���ܿͻ�����
			//modify by liuming 20170712
			if(belongManagerList != null && belongManagerList.size() > 0){
				belongManagerInfo = belongManagerList.get(0);
				String loanCustMgr =  null;
				if(belongManagerInfo.getCustManagerNo() == null){
					loanCustMgr = "";
				}else if(belongManagerInfo.getCustManagerNo().toUpperCase().equals("ADMIN")){
					loanCustMgr = belongManagerInfo.getCustManagerNo();
				}else {
					loanCustMgr = belongManagerInfo.getCustManagerNo().toUpperCase().replace("N", "");
				}
				custNode.addElement("loanCustMgr").setText(loanCustMgr);// ���ܿͻ�����
			}else{
				custNode.addElement("loanCustMgr").setText("");// ���ܿͻ�����
			}
			custNode.addElement("loanCustStat").setText(cust.getLoanCustStat() == null ? "" : cust.getLoanCustStat());// ״̬
			//custNode.addElement("loanMainBrId").setText(cust.getLoanMainBrId() == null ? "" : cust.getLoanMainBrId());// ���ܻ���
			//modify by liuming 20170712
			if(belongBranchList != null && belongBranchList.size() > 0){
				belongBranchInfo = belongBranchList.get(0);
				custNode.addElement("loanMainBrId").setText(belongBranchInfo.getBelongBranchNo() == null ? "" : belongBranchInfo.getBelongBranchNo());// ���ܻ���
			}else{
				custNode.addElement("loanMainBrId").setText("");// ���ܻ���
			}
			custNode.addElement("shortName").setText(cust.getShortName() == null ? "" : cust.getShortName());// �ͻ����
			//add by liuming 20170524
			custNode.addElement("coreNo").setText(cust.getCoreNo() == null ? "" : cust.getCoreNo());// ���Ŀͻ���
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			custNode.addElement("firstLoanDate").setText(cust.getFirstLoanDate() == null ? "" : sdf.format(cust.getFirstLoanDate()));//�Ŵ����翪������
			// TODO
			for (MCiIdentifier identTmp : identList) {
				ident = identTmp;

				// TODO
				if (MdmConstants.IDENT_TYPE_FEXC_TAX.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					identNode.addElement("fexcIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// ������֤����
				}

				// TODO
				if (MdmConstants.IS_LN_OPEN_IDENT.equals(ident.getIsOpenAccIdent()) && !StringUtil.isEmpty(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					// ���֤����Ϊ�գ���֤������ҲΪ��Ϊ��
					identNode.addElement("certType").setText(ident.getIdentNo() == null ? "" : ident.getIdentType()); // �ͻ�֤����Ϣ ֤�����ͣ�����֤����־Ϊ�� ����֤������ cert_type
					identNode.addElement("certCode").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo()); // �ͻ�֤����Ϣ ֤�����룬����֤����־Ϊ�� ����֤������ cert_code
				}
				if (MdmConstants.IDENT_TYPE_NOC.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					identNode.addElement("nocIdenRegDate").setText(ident.getIdenRegDate() == null ? "" : ident.getIdenRegDate().toString());// ��֯�����Ǽ����� TODO
					identNode.addElement("nocIdentCheckingDate").setText(ident.getIdentCheckingDate() == null ? "" : ident.getIdentCheckingDate().toString());// ��֯��������֤��쵽���� TODO
					identNode.addElement("nocIdentEffectiveDate").setText(ident.getIdentEffectiveDate() == null ? "" : ident.getIdentEffectiveDate().toString());// ��֯������Ч���� TODO
					identNode.addElement("nocIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// ��֯��������
					identNode.addElement("nocIdentOrg").setText(ident.getIdentOrg() == null ? "" : ident.getIdentOrg());// ��֯��������֤�䷢����
				}
				if (MdmConstants.IDENT_TYPE_NAT_TAX.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					identNode.addElement("natTaxIdenRegDate").setText(ident.getIdenRegDate() == null ? "" : ident.getIdenRegDate().toString());// ��˰˰��Ǽ����� TODO
					identNode.addElement("natTaxIdentCheckingDate").setText(ident.getIdentCheckingDate() == null ? "" : ident.getIdentCheckingDate().toString());// ��˰�Ǽ�֤��쵽���� TODO
					identNode.addElement("natTaxIdentExpiredDate").setText(ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate().toString());// ��˰�Ǽ���Ч�� TODO
					identNode.addElement("natTaxIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// ��˰˰��ǼǴ���
					identNode.addElement("natTaxIdentOrg").setText(ident.getIdentOrg() == null ? "" : ident.getIdentOrg());// ��˰˰��Ǽǻ���
				}
				if (MdmConstants.IDENT_TYPE_LOC_TAX.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					// TODO
					identNode.addElement("locTaxIdenRegDate").setText(ident.getIdenRegDate() == null ? "" : ident.getIdenRegDate().toString());// ��˰˰��Ǽ����� TODO
					identNode.addElement("locTaxIdentCheckingDate").setText(ident.getIdentCheckingDate() == null ? "" : ident.getIdentCheckingDate().toString());// ��˰�Ǽ�֤��쵽���� TODO
					identNode.addElement("locTaxIdentExpiredDate").setText(ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate().toString());// ��˰�Ǽ���Ч�� TODO
					identNode.addElement("locTaxIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// ��˰˰��ǼǴ���
					identNode.addElement("locTaxIdentOrg").setText(ident.getIdentOrg() == null ? "" : ident.getIdentOrg());// ��˰˰��Ǽǻ���
				}
				
				//add by liuming 20170524
				if ("Z".equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
//					orgNode.addElement("accOpenLicense").setText(ident.getIdentNo());// �������֤��׼��
				}
			}

			gradeNode.addElement("creditCustGrade").setText("");
			gradeNode.addElement("creditEvaluateDate").setText("");
			gradeNode.addElement("creditExpiredDate").setText("");
			gradeNode.addElement("orgName").setText("");
			gradeNode.addElement("outCreditCustGrade").setText("");
			gradeNode.addElement("outCreditEvaluateDate").setText("");

			// // TODO
			// MCiGrade creditGrade; // 03
			// MCiGrade outGrade;
			// gradeNode.addElement("creditCustGrade").setText(grade.getCreditCustGrade()==null?"":grade.getCreditCustGrade());//���õȼ�(ǰ��)
			// gradeNode.addElement("creditEvaluateDate").setText(grade.getCreditEvaluateDate()==null?"":grade.getCreditEvaluateDate());//������������
			// gradeNode.addElement("creditExpiredDate").setText(grade.getCreditExpiredDate()==null?"":grade.getCreditExpiredDate());//���õȼ���������
			// gradeNode.addElement("orgName").setText(grade.getOrgName()==null?"":grade.getOrgName());//���õȼ���������(�ⲿ)
			// gradeNode.addElement("outCreditCustGrade").setText(grade.getOutCreditCustGrade()==null?"":grade.getOutCreditCustGrade());//���õȼ�(�ⲿ)�����ڣ�
			// gradeNode.
			// for (MCiGrade grade : gradeList) {
			// if (grade != null) {
			// gradeNode.addElement("custGrade").setText(grade.getCustGrade() == null ? "" : grade.getCustGrade());
			// gradeNode.addElement("custGrade").setText(grade.getCustGrade() == null ? "" : grade.getCustGrade());
			// gradeNode.addElement("evaluateDate").setText(grade.getEvaluateDate() == null ? "" : grade.getEvaluateDate().toString());
			// gradeNode.addElement("evaluateDate").setText(grade.getEvaluateDate() == null ? "" : grade.getEvaluateDate().toString());
			// gradeNode.addElement("expiredDate").setText(grade.getExpiredDate() == null ? "" : grade.getExpiredDate().toString());
			// gradeNode.addElement("orgName").setText(grade.getOrgName() == null ? "" : grade.getOrgName());
			//
			// // // TODO
			// // gradeNode.addElement("creditCustGrade").setText(grade.getCreditCustGrade()==null?"":grade.getCreditCustGrade());//���õȼ�(ǰ��)
			// // gradeNode.addElement("creditEvaluateDate").setText(grade.getCreditEvaluateDate()==null?"":grade.getCreditEvaluateDate());//������������
			// // gradeNode.addElement("creditExpiredDate").setText(grade.getCreditExpiredDate()==null?"":grade.getCreditExpiredDate());//���õȼ���������
			// // gradeNode.addElement("orgName").setText(grade.getOrgName()==null?"":grade.getOrgName());//���õȼ���������(�ⲿ)
			// // gradeNode.addElement("outCreditCustGrade").setText(grade.getOutCreditCustGrade()==null?"":grade.getOutCreditCustGrade());//���õȼ�(�ⲿ)�����ڣ�
			// // gradeNode.addElement("outCreditEvaluateDate").setText(grade.getOutCreditEvaluateDate()==null?"":grade.getOutCreditEvaluateDate());//���õȼ���������(�ⲿ)
			//
			// }
			// }

			groupInfoNode.addElement("groupNo").setText("");// ���ű��
			groupInfoNode.addElement("groupType").setText("");// ���ſͻ�����
			// TODO
			// if (groupInfo != null) {
			// groupInfoNode.addElement("groupNo").setText(groupInfo.getGroupNo() == null ? "" : groupInfo.getGroupNo());// ���ű��
			// groupInfoNode.addElement("groupType").setText(groupInfo.getGroupType() == null ? "" : groupInfo.getGroupType());// ���ſͻ�����
			// }

			if (busiinfoList != null && busiinfoList.size() > 0) {
				busiinfo = busiinfoList.get(0);
				if (busiinfo != null) {
					busiinfoNode.addElement("mainProduct").setText(busiinfo.getMainProduct() == null ? "" : busiinfo.getMainProduct());
					busiinfoNode.addElement("manageStat").setText(busiinfo.getManageStat() == null ? "" : busiinfo.getManageStat());
					busiinfoNode.addElement("workFieldArea").setText(busiinfo.getWorkFieldArea() == null ? "" : busiinfo.getWorkFieldArea().toString());
					busiinfoNode.addElement("workFieldOwnership").setText(busiinfo.getWorkFieldOwnership() == null ? "" : busiinfo.getWorkFieldOwnership());
					// TODO
					// orgBusiinfoNode.addElement("mainProduct").setText(orgBusiinfo.getMainProduct()==null?"":orgBusiinfo.getMainProduct());//��Ҫ��Ʒ���
					// orgBusiinfoNode.addElement("manageStat").setText(orgBusiinfo.getManageStat()==null?"":orgBusiinfo.getManageStat());//��Ӫ״��
					// orgBusiinfoNode.addElement("workFieldArea").setText(orgBusiinfo.getWorkFieldArea()==null?"":orgBusiinfo.getWorkFieldArea());//��Ӫ�������(ƽ����)
					// orgBusiinfoNode.addElement("workFieldOwnership").setText(orgBusiinfo.getWorkFieldOwnership()==null?"":orgBusiinfo.getWorkFieldOwnership());//��Ӫ��������Ȩ
				}
			}
			for (MCiOrgExecutiveinfo executiveinfoTmp : executiveinfoList) {
				executiveinfo = executiveinfoTmp;
				// TODO
				if (MdmConstants.ORG_EXCE_ACT_CTL.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("actCtrlIdentNo").setText(executiveinfo.getIdentNo() == null ? "" : executiveinfo.getIdentNo());// ʵ�ʿ�����֤������
					executiveinfoNode.addElement("actCtrlIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// ʵ�ʿ�����֤������
					executiveinfoNode.addElement("actCtrlIndivCusId").setText(executiveinfo.getIndivCusId() == null ? "" : executiveinfo.getIndivCusId());// ʵ�ʿ����˿ͻ���
					executiveinfoNode.addElement("actCtrlLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// ʵ�ʿ���������
				}
				if (MdmConstants.ORG_EXCE_ACT_CTL_MATE.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("actCtrlMateIdentNo").setText(executiveinfo.getIdentNo() == null ? "" : executiveinfo.getIdentNo());// ʵ�ʿ�������ż֤������
					executiveinfoNode.addElement("actCtrlMateIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// ʵ�ʿ�����֤������
					executiveinfoNode.addElement("actCtrlMateIndivCusId").setText(executiveinfo.getIndivCusId() == null ? "" : executiveinfo.getIndivCusId());// ʵ�ʿ�������ż�ͻ���
					executiveinfoNode.addElement("actCtrlMateLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// ʵ�ʿ�������ż����
				}
				if (MdmConstants.ORG_EXCE_LEGAL_REPR_MATE.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("linkMateIdentNo").setText(executiveinfo.getIdentNo() == null ? "" : executiveinfo.getIdentNo());// ������ż֤������
					executiveinfoNode.addElement("linkMateIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// ������ż֤������
					executiveinfoNode.addElement("linkMateIndivCusId").setText(executiveinfo.getIndivCusId() == null ? "" : executiveinfo.getIndivCusId());// ������ż�ͻ���
					executiveinfoNode.addElement("linkMateLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// ������ż����
				}
				if (MdmConstants.ORG_EXCE_FINA_LINK.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("finaLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// ��������
				}
				if (MdmConstants.ORG_EXCE_OP.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("opLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// ��ҵ������
				}
				//add by liuming 20170705,����
				if ("5".equals(executiveinfo.getLinkmanType())) {
					if(executiveinfo.getLinkmanName() != null && !executiveinfo.getLinkmanName().equalsIgnoreCase("")){
					    orgNode.remove(orgNode.element("legalReprName"));
						orgNode.addElement("legalReprName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// ����������/����������
						orgNode.remove(orgNode.element("legalReprGender"));
						orgNode.addElement("legalReprGender").setText(executiveinfo.getGender() == null ? "" : executiveinfo.getGender());// �����������Ա�
						orgNode.remove(orgNode.element("legalReprIdentType"));
						orgNode.addElement("legalReprIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// ����������/������֤������
						orgNode.remove(orgNode.element("legalReprIdentNo"));
						orgNode.addElement("legalReprIdentNo").setText(executiveinfo.getIdentNo()== null ? "" : executiveinfo.getIdentNo());// ����������/������֤������
					}
					
				}
			}

			if (issuestockList != null && issuestockList.size() > 0) {
				issuestock = issuestockList.get(0);
				if (issuestock != null) {
					issuestockNode.addElement("marketPlace").setText(issuestock.getMarketPlace() == null ? "" : issuestock.getMarketPlace());
					issuestockNode.addElement("stockCode").setText(issuestock.getStockCode() == null ? "" : issuestock.getStockCode());
				}
			}

			if (keyflagList != null && keyflagList.size() > 0) {
				keyflag = keyflagList.get(0);
				if (keyflag != null) {
					keyflagNode.addElement("hasIeRight").setText(keyflag.getHasIeRight() == null ? "" : keyflag.getHasIeRight());
					keyflagNode.addElement("isAreaImpEnt").setText(keyflag.getIsAreaImpEnt() == null ? "" : keyflag.getIsAreaImpEnt());
					keyflagNode.addElement("isListedCorp").setText(keyflag.getIsListedCorp() == null ? "" : keyflag.getIsListedCorp());
					keyflagNode.addElement("isNewCorp").setText(keyflag.getIsNewCorp() == null ? "" : keyflag.getIsNewCorp());
					keyflagNode.addElement("isNotLocalEnt").setText(keyflag.getIsNotLocalEnt() == null ? "" : keyflag.getIsNotLocalEnt());
					keyflagNode.addElement("isNtnalMacroCtrl").setText(keyflag.getIsNtnalMacroCtrl() == null ? "" : keyflag.getIsNtnalMacroCtrl());
					keyflagNode.addElement("isPrepEnt").setText(keyflag.getIsPrepEnt() == null ? "" : keyflag.getIsPrepEnt());

					/**
					 * �Ŵ�ϵͳ���ڸ��ֶ���ECIF��ֵ��һ�Զ��ϵ���޷�ת�룬�ݲ�����
					 * keyflagNode.addElement("isRuralCorp").setText(keyflag.getIsRuralCorp() == null ? "" : keyflag.getIsRuralCorp());
					 */
					keyflagNode.addElement("isRuralCorp").setText("");
					keyflagNode.addElement("isSteelEnt").setText(keyflag.getIsSteelEnt() == null ? "" : keyflag.getIsSteelEnt());
					keyflagNode.addElement("isTwoHighEnt").setText(keyflag.getIsTwoHighEnt() == null ? "" : keyflag.getIsTwoHighEnt());
					//add by liuming 20170524
					keyflagNode.addElement("shippingInd").setText(keyflag.getShippingInd() == null ? "" : keyflag.getShippingInd());//�Ƿ�Ϊ������ҵ������ͳ�ƣ�
					keyflagNode.addElement("isTaiwanCorp").setText(keyflag.getIsTaiwanCorp() == null ? "" : keyflag.getIsTaiwanCorp());//�Ƿ�̨����ҵ
					keyflagNode.addElement("isTaiwanCorp").setText(keyflag.getIsTaiwanCorp() == null ? "" : keyflag.getIsTaiwanCorp());//�Ƿ�̨����ҵ
				}
			}
			if (reginfo != null) {
				registerinfoNode.addElement("apprDocNo").setText(reginfo.getApprDocNo() == null ? "" : reginfo.getApprDocNo());
				registerinfoNode.addElement("apprOrg").setText(reginfo.getApprOrg() == null ? "" : reginfo.getApprOrg());
				registerinfoNode.addElement("auditCon").setText(reginfo.getAuditCon() == null ? "" : reginfo.getAuditCon());
				registerinfoNode.addElement("auditDate").setText(reginfo.getAuditDate() == null ? "" : reginfo.getAuditDate().toString());
				registerinfoNode.addElement("auditEndDate").setText(reginfo.getAuditEndDate() == null ? "" : reginfo.getAuditEndDate().toString());
				registerinfoNode.addElement("endDate").setText(reginfo.getEndDate() == null ? "" : reginfo.getEndDate().toString());
				registerinfoNode.addElement("factCapital").setText(reginfo.getFactCapital() == null ? "" : reginfo.getFactCapital().toString());
				registerinfoNode.addElement("factCapitalCurr").setText(reginfo.getFactCapitalCurr() == null ? "" : reginfo.getFactCapitalCurr());
				registerinfoNode.addElement("registerCapital").setText(reginfo.getRegisterCapital() == null ? "" : reginfo.getRegisterCapital().toString());
				registerinfoNode.addElement("registerCapitalCurr").setText(reginfo.getRegisterCapitalCurr() == null ? "" : reginfo.getRegisterCapitalCurr());
				registerinfoNode.addElement("registerDate").setText(reginfo.getRegisterDate() == null ? "" : reginfo.getRegisterDate().toString());
				registerinfoNode.addElement("registerNo").setText(reginfo.getRegisterNo() == null ? "" : reginfo.getRegisterNo());
				
//				registerinfoNode.addElement("registerType").setText(reginfo.getRegisterType() == null ? "" : reginfo.getRegisterType());
				//modify by liuming 20170712 �Ŵ���CRM��ֵ��һ��,��ת��
				String registerType = null;
				if(reginfo.getRegisterType() == null){
					registerType="";
				}else if(reginfo.getRegisterType().length() >2){
					registerType = reginfo.getRegisterType().substring(0, 2);
				}else{
					registerType = reginfo.getRegisterType();
				}
				registerinfoNode.addElement("registerType").setText(registerType);
				registerinfoNode.addElement("registerZipcode").setText(reginfo.getRegisterZipcode() == null ? "" : reginfo.getRegisterZipcode());
				registerinfoNode.addElement("regOrg").setText(reginfo.getRegOrg() == null ? "" : reginfo.getRegOrg());
				//add by liuming 20170524
				registerinfoNode.addElement("regCodeType").setText(reginfo.getRegCodeType() == null ? "" : reginfo.getRegCodeType());//�Ǽ�ע�������
			}
			synchroRequestMsg = requestDoc.asXML();
			
			// TODO ����Ŵ�����ӿ��г��ȱ�ʶλΪ4λ����ʹ�����·�ʽ��װ���ģ����޸�SocketClient��packing����
			// synchroRequestMsg = String.format("%08d", synchroRequestMsg.length())+synchroRequestMsg;
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
		return result;
	}

	private boolean asseReqMsg4Indiv(MCiCustomer cust, TxEvtNotice txEvtNotice) {
		requestDoc = DocumentHelper.createDocument();
		boolean result = false;
		MCiPerson per = null;
		// MCiAddress addr = null;
		MCiContmeth con = null;
		MCiIdentifier ident = null;

		String custNo;

		custNo = "" + txEvtNotice.getCustId();

		List<MCiContmeth> custList = baseDAO.findWithIndexParam("FROM MCiCustomer where custId=?", custNo);
		List<MCiContmeth> contList = baseDAO.findWithIndexParam("FROM MCiContmeth where custId=?", custNo);
		List<MCiIdentifier> identList = baseDAO.findWithIndexParam("FROM MCiIdentifier where custId=?", custNo);
		// ��ַ���ͣ�'08'��ʵ�ʾ�Ӫ��ַ ʵ�ʾ�Ӫ��ַ acu_addr
		List<MCiAddress> addrList = baseDAO.findWithIndexParam("FROM MCiAddress where custId=?", custNo);
		List<MCiPerson> petrList = baseDAO.findWithIndexParam("FROM MCiPerson where custId=?", custNo);
		List<MCiPerKeyflag> perKeyflagList = baseDAO.findWithIndexParam("FROM MCiPerKeyflag where custId=?", custNo);
		List<MCiGrade> gradeList = baseDAO.findWithIndexParam("FROM MCiGrade where custId=?", custNo);
		List<MCiPerMateinfo> perMateList = baseDAO.findWithIndexParam("FROM MCiPerMateinfo where custId=?", custNo);

		if (custList.size() > 1) {
			log.error("���ݴ��󣬿ͻ���[m_ci_customer]�����ݿͻ���{}��Ψһ", custNo);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "�ͻ���[m_ci_customer]�����ݿͻ���{" + custNo + "}��Ψһ");
			return false;
		}

		baseDAO = (JPABaseDAO<?, ?>) SpringContextUtils.getBean("baseDAO");
		requestDoc = DocumentHelper.createDocument();

		try {
			// SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			// SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddhhmmssSSSSSS");

			Element transBody = requestDoc.addElement("TransBody");
			requestDoc.setXMLEncoding("GB2312");
			Element requestHeader = transBody.addElement("RequestHeader");
			requestHeader.addElement("DestSysCd").setText("LN");

			Element requestBody = transBody.addElement("RequestBody");
			Element dataNode;

			// ���Ŵ�ֱ��
			// dataNode = requestDoc.addElement("Packet").addElement("Data");
			// ͨ��EAI���Ŵ�����
			dataNode = requestBody.addElement("Packet").addElement("Data");

			Element reqNode = dataNode.addElement("Req");
			Element pubNode = dataNode.addElement("Pub");
			pubNode.addElement("prcscd").setText("receiveCusIndivInfo");

			Element custNode = reqNode.addElement("Customer");
			Element perNode = reqNode.addElement("Person");
			Element addrNode = reqNode.addElement("Address");
			Element contmethNode = reqNode.addElement("Contmeth");
			Element identNode = reqNode.addElement("Identifier");
			Element gradeNode = reqNode.addElement("Grade");
			Element perKeyflagNode = reqNode.addElement("PerKeyflag");
			Element perMateinfo = reqNode.addElement("PerMateinfo");

			custNode.setText("");
			perNode.setText("");
			addrNode.setText("");
			contmethNode.setText("");
			identNode.setText("");
			gradeNode.setText("");
			perKeyflagNode.setText("");
			perMateinfo.setText("");

			custNode.addElement("cus_id").setText(cust.getCustId() == null ? "" : cust.getCustId());
			// custNode.addElement("cert_type").setText(cust.getIdentType() == null ? "" : cust.getIdentType());
			// custNode.addElement("cert_code").setText(cust.getIdentNo() == null ? "" : cust.getIdentNo());
			custNode.addElement("cust_mgr").setText(cust.getLoanCustMgr() == null ? "" : cust.getLoanCustMgr());
			custNode.addElement("main_br_id").setText(cust.getLoanMainBrId() == null ? "" : cust.getLoanMainBrId());
			custNode.addElement("input_id").setText(cust.getCreateTellerNo() == null ? "" : cust.getCreateTellerNo());
			custNode.addElement("input_br_id").setText(cust.getCreateBranchNo() == null ? "" : cust.getCreateBranchNo());
			custNode.addElement("input_date").setText(cust.getCreateDate() == null ? "" : cust.getCreateDate() + "");
			custNode.addElement("flag_area").setText(cust.getInoutFlag() == null ? "" : cust.getInoutFlag());
			custNode.addElement("cus_en_name").setText(cust.getEnName() == null ? "" : cust.getEnName());
			custNode.addElement("vip_flag").setText(cust.getVipFlag() == null ? "" : cust.getVipFlag());
			custNode.addElement("indiv_occ").setText(cust.getJobType() == null ? "" : cust.getJobType());
			custNode.addElement("indiv_com_fld").setText(cust.getIndustType() == null ? "" : cust.getIndustType());
			custNode.addElement("cus_bank_rel").setText(cust.getCusBankRel() == null ? "" : cust.getCusBankRel());
			custNode.addElement("com_rel_dgr").setText(cust.getCusCorpRel() == null ? "" : cust.getCusCorpRel());

			if (petrList != null && petrList.size() == 1) {
				per = petrList.get(0);
				perNode.addElement("cus_type").setText(per.getPerCustType() == null ? "" : per.getPerCustType());
				perNode.addElement("cus_name").setText(per.getPersonalName() == null ? "" : per.getPersonalName());
				perNode.addElement("indiv_sex").setText(per.getGender() == null ? "" : per.getGender());
				perNode.addElement("indiv_ntn").setText(per.getNationality() == null ? "" : per.getNationality());
				perNode.addElement("indiv_brt_place").setText(per.getNativeplace() == null ? "" : per.getNativeplace());
				perNode.addElement("indiv_houh_reg_add").setText(per.getHukouPlace() == null ? "" : per.getHukouPlace());
				perNode.addElement("indiv_dt_of_birth").setText(per.getBirthday() == null ? "" : per.getBirthday() + "");
				perNode.addElement("indiv_hld_acnt").setText(per.getHoldAcct() == null ? "" : per.getHoldAcct());
				perNode.addElement("indiv_edt").setText(per.getHighestSchooling() == null ? "" : per.getHighestSchooling());
				perNode.addElement("indiv_dgr").setText(per.getHighestDegree() == null ? "" : per.getHighestDegree());
				perNode.addElement("indiv_mar_st").setText(per.getMarriage() == null ? "" : per.getMarriage());
				perNode.addElement("indiv_heal_st").setText(per.getHealth() == null ? "" : per.getHealth());
				perNode.addElement("indiv_rsd_addr").setText(per.getHomeAddr() == null ? "" : per.getHomeAddr());
				perNode.addElement("indiv_zip_code").setText(per.getHomeZipcode() == null ? "" : per.getHomeZipcode());
				perNode.addElement("indiv_rsd_st").setText(per.getResidence() == null ? "" : per.getResidence());
				perNode.addElement("indiv_com_phn").setText(per.getUnitTel() == null ? "" : per.getUnitTel());
				perNode.addElement("indiv_com_fax").setText(per.getUnitFex() == null ? "" : per.getUnitFex());
				perNode.addElement("indiv_com_addr").setText(per.getUnitAddr() == null ? "" : per.getUnitAddr());
				perNode.addElement("indiv_com_zip_code").setText(per.getUnitZipcode() == null ? "" : per.getUnitZipcode());
				perNode.addElement("indiv_com_cnt_name").setText(per.getCntName() == null ? "" : per.getCntName());
				perNode.addElement("indiv_work_job_y").setText(per.getCurrCareerStartDate() == null ? "" : per.getCurrCareerStartDate() + "");
				perNode.addElement("indiv_com_job_ttl").setText(per.getDuty() == null ? "" : per.getDuty());
				perNode.addElement("indiv_crtfctn").setText(per.getCareerTitle() == null ? "" : per.getCareerTitle());
				perNode.addElement("indiv_ann_incm").setText(per.getAnnualIncome() == null ? "" : per.getAnnualIncome() + "");
				perNode.addElement("indiv_sal_acc_bank").setText(per.getSalaryAcctBank() == null ? "" : per.getSalaryAcctBank());
				perNode.addElement("indiv_sal_acc_no").setText(per.getSalaryAcctNo() == null ? "" : per.getSalaryAcctNo());
				perNode.addElement("indiv_com_name").setText(per.getUnitName() == null ? "" : per.getUnitName());
				perNode.addElement("indiv_com_typ").setText(per.getUnitChar() == null ? "" : per.getUnitChar());
				perNode.addElement("bank_duty").setText(per.getBankDuty() == null ? "" : per.getBankDuty());
				perNode.addElement("indiv_country").setText(per.getCitizenship() == null ? "" : per.getCitizenship());
				perNode.addElement("fphone").setText(per.getHomeTel() == null ? "" : per.getHomeTel());
				perNode.addElement("hold_card").setText(per.getHoldCard() == null ? "" : per.getHoldCard());
				perNode.addElement("work_resume").setText(per.getResume() == null ? "" : per.getResume());
				perNode.addElement("com_hold_stk_amt").setText(per.getHoldStockAmt() == null ? "" : per.getHoldStockAmt() + "");
				perNode.addElement("remark").setText(per.getRemark() == null ? "" : per.getRemark());
				perNode.addElement("loan_card_id").setText(per.getLoanCardNo() == null ? "" : per.getLoanCardNo());
				perNode.addElement("phone").setText(per.getPostPhone() == null ? "" : per.getPostPhone());
			} else if (petrList.size() > 1) {
				log.error("���ݴ��󣬸��˿ͻ���[m_ci_person]������(�ͻ���{})��Ψһ", custNo);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
				txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "�����˿ͻ���[m_ci_person]������(�ͻ���" + custNo + ")��Ψһ");
				return false;
			}

			if (addrList != null && addrList.size() > 0) {
				for (MCiAddress address : (List<MCiAddress>) addrList) {
					if ("08".equals(address.getAddrType())) {
						addrNode.addElement("addr").setText(address.getAddr()); // ��ַ��Ϣ ��ϸ��ַ����ַ���ͣ�'08'��ʵ�ʾ�Ӫ��ַ ʵ�ʾ�Ӫ��ַ acu_addr
						addrNode.addElement("post_addr").setText(address.getAddr() == null ? "" : address.getAddr());
						addrNode.addElement("post_code").setText(address.getZipcode() == null ? "" : address.getZipcode());
						addrNode.addElement("area_code").setText(address.getAreaCode() == null ? "" : address.getAreaCode());
					} else {
						addrNode.addElement("addr").setText(address.getAddr()); // ��ַ��Ϣ ��ϸ��ַ����ַ���ͣ�'08'��ʵ�ʾ�Ӫ��ַ ʵ�ʾ�Ӫ��ַ acu_addr
						addrNode.addElement("post_addr").setText(address.getAddr() == null ? "" : address.getAddr());
						addrNode.addElement("post_code").setText(address.getZipcode() == null ? "" : address.getZipcode());
						addrNode.addElement("area_code").setText(address.getAreaCode() == null ? "" : address.getAreaCode());
					}
				}
			}

			for (int i = 0; i < contList.size(); i++) {
				con = contList.get(i);
				if ("503".equals(con.getContmethType())) {
					// contmethNode.addElement("email").setText("getEmail()");
					contmethNode.addElement("email").setText(con.getContmethInfo() == null ? "" : con.getContmethInfo());
				}
				if ("100".equals(con.getContmethType())) {
					contmethNode.addElement("mobile").setText(con.getContmethInfo() == null ? "" : con.getContmethInfo());
				}
			}

			String isOpenIdent = "1";
			for (MCiIdentifier identTmp : identList) {
				ident = identTmp;
				if (isOpenIdent.equals(ident.getIsOpenAccIdent())) {
					// ���֤����Ϊ�գ���֤������ҲΪ��Ϊ��
					identNode.addElement("certType").setText(ident.getIdentType() == null ? "" : ident.getIdentType()); // �ͻ�֤����Ϣ ֤�����ͣ�����֤����־Ϊ�� ����֤������ cert_type
					identNode.addElement("certCode").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo()); // �ͻ�֤����Ϣ ֤�����룬����֤����־Ϊ�� ����֤������ cert_code
				}

				// TODO
				if ("X25".equals(ident.getIdentType())) {
					identNode.addElement("indiv_sps_mar_code").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());
				}
				// TODO if/else
				identNode.addElement("indiv_id_exp_dt").setText(ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate() + "");

				// TODO 501-->>>error
				if ("X2".equals(ident.getIdentType())) {
					identNode.addElement("twid_no").setText(ident.getIdentNo() == null ? "" : ident.getIdentId());
				}

				// TODO 501-->>>error
				if (!"X2".equals(ident.getIdentType()) && !"X25".equals(ident.getIdentType())) {
					identNode.addElement("other_cert_type").setText(ident.getIdentType() == null ? "" : ident.getIdentType());
					identNode.addElement("other_cert_code").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());
				}
				result = true;
			}

			// Grade
			if (gradeList != null) {
				for (MCiGrade grade : gradeList) {
					if (MdmConstants.CRTEDIT_GRADE_CODE.equals(grade.getCustGradeType())) {
						gradeNode.addElement("crd_date").setText(grade.getEvaluateDate() == null ? "" : grade.getEvaluateDate().toString());// TODO
						gradeNode.addElement("crd_grade").setText(grade.getExpiredDate() == null ? "" : grade.getExpiredDate().toString());// TODO
						gradeNode.addElement("crd_grade").setText(grade.getCustGrade() == null ? "" : grade.getCustGrade());
					}
				}
			}

			// PerKeyflag
			if (perKeyflagList != null && perKeyflagList.size() > 0) {
				if (perKeyflagList.size() == 1) {
					MCiPerKeyflag keyflag = new MCiPerKeyflag();
					perKeyflagNode.addElement("agri_flg").setText(keyflag.getIsPeasant() == null ? "" : keyflag.getIsPeasant());// TODO
					perKeyflagNode.addElement("passport_flg").setText(keyflag.getForeignPassportFlag() == null ? "" : keyflag.getForeignPassportFlag());// TODO
				} else {
					log.error("���ݴ��󣬸��˿ͻ���Ҫ��־��[m_ci_per_keyflag]������(�ͻ���{})��Ψһ", custNo);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
					txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "�����ݴ��󣬸��˿ͻ���Ҫ��־��[m_ci_per_keyflag]������(�ͻ���" + custNo + ")��Ψһ");
					return false;
				}
			}

			// PerMateinfo
			if (perMateList != null && perMateList.size() > 0) {
				if (perMateList.size() == 1) {
					MCiPerMateinfo mate = perMateList.get(0);
					perMateinfo.addElement("cus_id_rel").setText(mate.getCustIdMate() == null ? "" : mate.getCustIdMate());
					perMateinfo.addElement("indiv_psp_crtfctn").setText(mate.getJobTitle() == null ? "" : mate.getJobTitle());
					perMateinfo.addElement("indiv_scom_name").setText(mate.getWorkUnit() == null ? "" : mate.getWorkUnit());
					perMateinfo.addElement("indiv_sps_duty").setText(mate.getDuty() == null ? "" : mate.getDuty());
					perMateinfo.addElement("indiv_sps_id_code").setText(mate.getIdentNo() == null ? "" : mate.getIdentNo());
					perMateinfo.addElement("indiv_sps_id_typ").setText(mate.getIdentType() == null ? "" : mate.getIdentType());
					perMateinfo.addElement("indiv_sps_job_dt").setText(mate.getWorkStartDate() == null ? "" : mate.getWorkStartDate().toString());// TODO
					perMateinfo.addElement("indiv_sps_mincm").setText(mate.getAnnualIncome() == null ? "" : mate.getAnnualIncome().toString());// TODO
					perMateinfo.addElement("indiv_sps_mphn").setText(mate.getMobile() == null ? "" : mate.getMobile());
					perMateinfo.addElement("indiv_sps_name").setText(mate.getMateName() == null ? "" : mate.getMateName());
					perMateinfo.addElement("indiv_sps_occ").setText(mate.getCareer() == null ? "" : mate.getCareer());
					perMateinfo.addElement("indiv_sps_phn").setText(mate.getOfficeTel() == null ? "" : mate.getOfficeTel());
				} else {
					log.error("���ݴ�����ż��Ϣ[m_ci_per_mateinfo]������(�ͻ���{})��Ψһ", custNo);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
					txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "�����ݴ������ݴ�����ż��Ϣ[m_ci_per_mateinfo]������(�ͻ���" + custNo + ")��Ψһ");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
		synchroRequestMsg = requestDoc.asXML();
		// TODO ����Ŵ�����ӿ��г��ȱ�ʶλΪ4λ����ʹ�����·�ʽ��װ���ģ����޸�SocketClient��packing����
		// synchroRequestMsg = String.format("%08d", synchroRequestMsg.length())+synchroRequestMsg;
		// System.out.println("�Ŵ�����ı��ģ�---------->"+synchroRequestMsg);
		return result;
	}

	@Override
	public boolean executeResult() {
		// log.info("����ͬ����Ӧ���ģ�\n[{}]", synchroResponseMsg);
		try {
			Document root = XMLUtils.stringToXml(synchroResponseMsg.substring(8));
			String txCodeXpath = "//TransBody/ResponseTail/TxStatCode";
			String txMsgXpath = "//TransBody/ResponseTail/TxStatDesc";

			Node txStatCodeNode = root.selectSingleNode(txCodeXpath);
			Node txMsgNode = root.selectSingleNode(txMsgXpath);

			if (txStatCodeNode == null || txMsgNode == null) {
				String msg = String.format("{},�յ���Χϵͳ[%s]��Ӧ���ı�Ҫ�ڵ�Ϊ��[��Ҫ�ڵ㣺%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("lnCd"), txStatCodeNode, txMsgNode);

				log.error(msg);

				syncLog = new TxSyncLog();
				syncLog.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
				syncLog.setSyncDealInfo(msg);

				syncErr = new TxSyncErr();
				syncErr.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
				syncErr.setSyncDealInfo(msg);

				return false;
			} else {
				String txStatCode = txStatCodeNode.getText().trim();
				String txStatDetail = txMsgNode.getText().trim();

				// TODO 0000 to Constant
				if ("000000".equals(txStatCode)) {
					syncLog = new TxSyncLog();
					syncLog.setSyncDealResult(txStatCode);
					syncLog.setSyncDealInfo(txStatDetail);
					// return true;
				} else {
					syncLog = new TxSyncLog();
					syncLog.setSyncDealResult(txStatCode);
					syncLog.setSyncDealInfo(txStatDetail);

					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(txStatCode);
					syncErr.setSyncDealInfo(txStatDetail);
					return false;
				}
			}

			if (MdmConstants.TX_CUST_ORG_TYPE.equals(syncCustType)) {
				String entScaleXpath = "//TransBody/Packet/Data/Res/entScale";
				String entScaleRhXpath = "//TransBody/Packet/Data/Res/entScaleRh";
				String custIdXpath = "//TransBody/Packet/Data/Res/custId";

				root.selectSingleNode(txCodeXpath).getText();

				Node entNode = root.selectSingleNode(entScaleXpath);
				Node entRhNode = root.selectSingleNode(entScaleRhXpath);
				Node custIdNode = root.selectSingleNode(custIdXpath);

				if (entNode == null || entRhNode == null || custIdNode == null) {

					String msg = String.format("{},�յ���Χϵͳ[%s]��Ӧ���ı�Ҫ�ڵ�Ϊ��[��Ҫ�ڵ㣺%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("lnCd"), entScaleXpath,
							entScaleRhXpath, custIdXpath);

					log.error(msg);

					syncLog = new TxSyncLog();
					syncLog.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
					syncLog.setSyncDealInfo(msg);

					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
					syncErr.setSyncDealInfo(msg);

					return false;
				}

				String ent = root.selectSingleNode(entScaleXpath).getText();
				String entRh = root.selectSingleNode(entScaleRhXpath).getText();
				// String custId = root.selectSingleNode(custIdXpath).getText();

				if (!StringUtils.isEmpty(ent) && !StringUtils.isEmpty(entRh) && !StringUtils.isEmpty(syncCustNo)) {

					List<TxLog> txLog = baseDAO.findWithIndexParam("FROM TxLog where txFwId=?", origTxReqSeqNo);
					String updateUser = null;
					if (txLog == null || txLog.size() == 0) {
						String reqMsg = txLog.get(0).getReqMsg();
						Document origTxReqDoc = DocumentHelper.parseText(reqMsg.trim());
						updateUser = origTxReqDoc.selectSingleNode("//TransBody/RequestHeader/TlrNo").getText();
					}
					updateUser = StringUtils.isEmpty(updateUser) ? "SYSTEM" : updateUser;

					Document fakeReqDoc = DocumentHelper.createDocument();
					fakeReqDoc.setXMLEncoding("GBK");
					Element transBody = fakeReqDoc.addElement("TransBody");
					Element header = transBody.addElement("RequestHeader");
					header.addElement("ReqSysCd").setText("ECIF");
					Date currDate = new Date();
					SimpleDateFormat dfSeq = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					SimpleDateFormat dfDt = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat dfTm = new SimpleDateFormat("hh:mm:ss");
					header.addElement("ReqSeqNo").setText(dfSeq.format(currDate));
					System.out.println("ReqSeqNo====>>>>>"+dfSeq.format(currDate));
					header.addElement("ReqDt").setText(dfDt.format(currDate));
					header.addElement("ReqTm").setText(dfTm.format(currDate));
					header.addElement("DestSysCd").setText("ECIF");
					header.addElement("ChnlNo").setText("");
					header.addElement("BrchNo").setText("");
					header.addElement("BizLine").setText("");
					header.addElement("TrmNo").setText("");
					header.addElement("TrmIP").setText("");
					header.addElement("TlrNo").setText(updateUser);
					header.addElement("Comt").setText("ECIF_RE_SYNC");
					Element body = transBody.addElement("RequestBody");
					body.addElement("txCode").setText("updateOrgCustInfo");
					body.addElement("custNo").setText(syncCustNo);
					body.addElement("txName").setText("updateOrgCustInfo");
					body.addElement("txCode").setText("updateOrgCustInfo");
					body.addElement("authType").setText("1");
					body.addElement("authCode").setText("001");
					Element org = body.addElement("org");
					org.addElement("entScale").setText(ent);
					org.addElement("entScaleRh").setText(entRh);
					String reqXml = fakeReqDoc.asXML();
					SocketClient client = new SocketClient();
					Map<String, String> map = new HashMap<String, String>();
					// map.put("ip", "192.168.2.84");
					//һ�㽫ͬ�������뽻�׷�������ͬһ��������
					map.put("ip", "127.0.0.1");
					map.put("port", "9500");
					map.put("charset", "GBK");
					map.put("timeout", "60000");
					map.put("selecttimeout", "3000");
					client.init(map);
					client.sendMsg(String.format("%08d%s", reqXml.getBytes().length, reqXml));
				}
			}
		} catch (DocumentException e) {
			log.error("�յ���Χϵͳ��������һ������ʱ����,������Ϣ:\n{}", e);
			return false;
		} catch (Exception e) {
			log.error("�յ���Χϵͳ��������һ������ʱ����,������Ϣ:\n{}", e);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String xml = "00000195<?xml version=\"1.0\" encoding=\"gb2312\" ?><Packet><Data><Res><custId>110000688488</custId><entScale>CS01</entScale><entScaleRh>CS01</entScaleRh><errorCode>0000</errorCode><errorMsg>�ɹ�</errorMsg></Res></Data></Packet>";
		new FubonSynchroHandler4LN().executeResult();
	}
}
