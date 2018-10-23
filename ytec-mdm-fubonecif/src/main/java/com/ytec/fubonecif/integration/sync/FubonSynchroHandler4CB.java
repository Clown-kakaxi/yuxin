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
import java.util.ArrayList;
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
import com.ytec.fubonecif.domain.MCiAgentinfo;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiBusiType;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCrossindex;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiGrade;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiInterbank;
import com.ytec.fubonecif.domain.MCiOrg;
import com.ytec.fubonecif.domain.MCiOrgExecutiveinfo;
import com.ytec.fubonecif.domain.MCiPerLinkman;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;
import com.ytec.mdm.domain.txp.TxSyncErr;
import com.ytec.mdm.domain.txp.TxSyncLog;
import com.ytec.mdm.integration.sync.ptsync.SynchroExecuteHandler;
import com.ytec.mdm.integration.transaction.xml.XMLUtils;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif
 * @�����ƣ�FubonSynchroHandler4CB
 * @�����������һ��������ͬ�������࣬����ϵͳ�ͻ���
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
public class FubonSynchroHandler4CB extends SynchroExecuteHandler {
	private static Logger log = LoggerFactory.getLogger(SynchroBroadcastHandler.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd"); // ECIF��ESB�еı��
	// �������ݿ�
	private JPABaseDAO baseDAO;

	@Override
	public boolean execute(TxSyncConf txSyncConf, TxEvtNotice txEvtNotice) {
		String custId = txEvtNotice.getCustNo();
		if (StringUtil.isEmpty(custId)) {
			log.error("ͬ���ͻ��Ŀͻ���Ϊ��");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("ͬ���ͻ��Ŀͻ���Ϊ��");
			return false;
		}

		try {
			synchroRequestMsg = packageReqXml(custId);

			System.out.printf("synchroRequestMsg.getBytes().length=%d, synchroRequestMsg.length()=%d\n", synchroRequestMsg.getBytes().length, synchroRequestMsg.length());
		} catch (Exception e) {
			String msg = String.format("��װͬ��������ʧ��(%s), �޷�����Χϵͳ(%s)ͬ���ͻ���Ϣ", e.getLocalizedMessage(), txSyncConf.getDestSysNo());
			log.error(msg);
			log.error("������Ϣ:{}", e);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_MSG_ERROR.getCode());
			txEvtNotice.setEventDealInfo(msg);
			return false;
		}
		return true;
	}

	/**
	 * ��װ��Ӧ����
	 * 
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String packageReqXml(String custId) throws Exception {
		List list = null;
		try {
			list = getDataFromDb(custId);

			if (list == null) {
				String msg = String.format("��ѯ�ͻ�(%s)��Ϣʧ�ܣ��޷�ͬ����Ϣ������ϵͳ", custId);
				log.error(msg);
				throw new Exception(msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}

		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("hhmmss");
		Document requestDoc = DocumentHelper.createDocument();
		requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
		Element transBody = requestDoc.addElement("TransBody");

		Element requestHeader = transBody.addElement("RequestHeader");

		requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd ��Χϵͳ����
		requestHeader.addElement("ReqSeqNo").setText("");// ReqSeqNo ��Χϵͳ������ˮ��
		requestHeader.addElement("ReqDt").setText(df8.format(new Date()));// ReqDt ��������
		requestHeader.addElement("ReqTm").setText(df20.format(new Date()));// ReqTm ����ʱ��
		requestHeader.addElement("DestSysCd").setText("CB");// Ŀ��ϵͳ����
		requestHeader.addElement("ChnlNo").setText("");// ChnlNo ������
		requestHeader.addElement("BrchNo").setText("");// BrchNo ������
		requestHeader.addElement("BizLine").setText("");// BizLine ҵ������
		requestHeader.addElement("TrmNo").setText("");// TrmNo �ն˺�
		requestHeader.addElement("TrmIP").setText("");// TrmIP �ն�IP
		requestHeader.addElement("TlrNo").setText("");// TlrNo ������Ա��

		Element databody = transBody.addElement("RequestBody");
		databody.addElement("txCode").setText("CMUC");

		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = (Map<String, String>) list.get(i);
			String coreNo = map.get("coreNo");
			databody.addElement("coreNo").setText(coreNo == null ? "" : coreNo);

			databody.addElement("identType").setText(map.get("identType") == null ? "" : map.get("identType"));
			databody.addElement("identNo").setText(map.get("identNo") == null ? "" : map.get("identNo"));
			databody.addElement("identValidPeriod").setText(map.get("identValidPeriod") == null ? "" : map.get("identValidPeriod")); // ����ϵͳ֤����Ч�ڶ�ӦECIF֤������IdentCheckingDate

			String custName = map.get("custName");

			if (StringUtils.isEmpty(custName)) {
				String msg = String.format("ECIF���ݴ��󣬿ͻ�����(%s)����Ч", custName);
				log.error(msg);
				throw new Exception(msg);
			}

			Map<String, String> mapName = splitString(custName, "custName");
			if (mapName != null) {
				databody.addElement("custName").setText(mapName.get("0"));// ������1
				if (mapName.containsKey("1")) {
					databody.addElement("custName2").setText(mapName.get("1"));// ������2
				} else {
					databody.addElement("custName2");
				}
			}

			String enName = map.get("enName");
//			List booleanList = new ArrayList();
//			if(enName!=null && !"".equals(enName)){
//				char[] chr = enName.toCharArray();
//				for(int j=0;j<chr.length;j++){
//					boolean flag =returnSpecial(chr[j]);
//					booleanList.add(flag);
//				}
//			}
//			boolean enFlag = booleanList.contains(true);//�ж��Ƿ����˫�ֽڵ�
			//if(enFlag){//����˫�ֽ�
				Map<String,String> mapEnName = splitString(enName, "enName");;
				if (mapEnName != null) {
					   databody.addElement("enName").setText(mapEnName.get("0"));//Ӣ������
					if (mapEnName.containsKey("1")) {
						databody.addElement("enName2").setText(mapEnName.get("1"));// Ӣ������2
					} else {
						databody.addElement("enName2");
					}
				} else {
					databody.addElement("enName");// Ӣ������
					databody.addElement("enName2");// Ӣ������
				}
//			}else{//������˫�ֽ�
//				String enName1 = "";
//				String enName2 ="";
//				if(enName.getBytes().length>35){
//					enName1 = enName.substring(0, 35);
//					enName2 = enName.substring(35, enName.getBytes().length);
//				}else{
//					enName1 = enName.substring(0, enName.getBytes().length);
//				}
//				 databody.addElement("enName").setText(enName1);//Ӣ������
//				 databody.addElement("enName2").setText(enName2);// Ӣ������
//			}
			
		
			databody.addElement("custType").setText(map.get("custType") == null ? "" : map.get("custType"));// ��ҵ/���˱�־
			databody.addElement("custGrade").setText(map.get("custGrade") == null ? "" : map.get("custGrade"));// ����ͻ��ȼ�
			databody.addElement("nationCode").setText(map.get("nationCode") == null ? "" : map.get("nationCode"));// ���ڹ���
			String adminZone = map.get("adminZone");
			if (adminZone != null && !"".equals(adminZone)) {
				if (adminZone.equals("810000") || adminZone.equals("710000") || adminZone.equals("820000")) {
					databody.addElement("adminZone").setText(adminZone);// ֻ�и۰�̨�ŷ����������
				} else {
					databody.addElement("adminZone").setText("");// ��������
				}
			} else {
				databody.addElement("adminZone").setText("");// ��������
			}
			databody.addElement("inoutFlag").setText(map.get("inoutFlag") == null ? "" : map.get("inoutFlag"));// ����/���־
			databody.addElement("orgCustType").setText(map.get("orgCustType") == null ? "" : map.get("orgCustType"));// ��ҵ����
			databody.addElement("busiLicNo").setText(map.get("busiLicNo") == null ? "" :map.get("busiLicNo"));//Ӫҵִ��
			databody.addElement("orgType").setText(map.get("orgType") == null ? "" : map.get("orgType"));// ��������
//			if("1".equals(map.get("custType"))){
//				databody.addElement("orgSubType").setText(map.get("orgSubType") == null ? "" : map.get("orgSubType"));// ��ó������
//				databody.addElement("ifOrgSubType").setText(map.get("ifOrgSubType") == null ? "" : map.get("ifOrgSubType"));// �Ƿ���ó����־-����
//			}else{
//				databody.addElement("OrgSubType").setText(map.get("perOrgSubType") == null ? "" : map.get("perOrgSubType"));// ��ó������
//				databody.addElement("ifOrgSubType").setText(map.get("ifPerOrgSubType") == null ? "" : map.get("ifPerOrgSubType"));// �Ƿ���ó������-����
//			}
			databody.addElement("staffin").setText(map.get("staffin") == null ? "" : map.get("staffin"));//����������
			//databody.addElement("inCllType").setText(map.get("inCllType") == null ? "" : map.get("inCllType"));//��ҵ���
			databody.addElement("orgSubType").setText(map.get("orgSubType") == null ? "" : map.get("orgSubType"));// ��ó������
			databody.addElement("ifOrgSubType").setText(map.get("ifOrgSubType") == null ? "" : map.get("ifOrgSubType"));// �Ƿ���ó������
			databody.addElement("jointCustType").setText(map.get("jointCustType") == null ? "" : map.get("jointCustType"));// ��������־
			databody.addElement("custManagerNo").setText(map.get("custManagerNo") == null ? "" : map.get("custManagerNo"));// �ͻ��������
			databody.addElement("arCustFlag").setText(map.get("arCustFlag") == null ? "" : map.get("arCustFlag"));// �Ƿ�AR�ͻ���־
			databody.addElement("swift").setText(map.get("swift") == null ? "" : map.get("swift"));// swift����
			databody.addElement("riskNationCode").setText(map.get("riskNationCode") == null ? "" : map.get("riskNationCode"));// ������չ������
			String addr = map.get("addr");
			String zipCode = map.get("zipCode");
			Map<String, String> addrMap = new HashMap<String, String>();
			if (addr != null && !addr.toString().trim().equals("")) {
				addrMap = splitString(addr, "addr");
			}
			if (addrMap.containsKey("2")) {// �ʼĵ�ַ3 �����ֵ��ô�ͽ�ԭ�ȵĵ�ַ���ʱ�ϲ���һ��
				addr = addr+zipCode;
				addrMap = splitString(addr, "addr");
				databody.addElement("mailad1").setText(addrMap.get("0") == null ? "" : addrMap.get("0"));// �ʼĵ�ַ1
				if (addrMap.containsKey("1")) {// �ʼĵ�ַ2
					databody.addElement("mailad2").setText(addrMap.get("1"));
				} else {
					databody.addElement("mailad2");
				}
				databody.addElement("mailad3").setText(addrMap.get("2"));
			} else {//����������ֶβ���ֵ��ô�ͷ��õ�ַ��Ӧ���ʱ�
				databody.addElement("mailad1").setText(addrMap.get("0") == null ? "" : addrMap.get("0"));// �ʼĵ�ַ1
				if (addrMap.containsKey("1")) {// �ʼĵ�ַ2
					databody.addElement("mailad2").setText(addrMap.get("1"));
				} else {
					databody.addElement("mailad2").setText("");
				}
				if(zipCode!=null && !zipCode.toString().trim().equals("")){
					Map<String, String> ZipCodeMap = new HashMap<String, String>();
					ZipCodeMap = splitString(zipCode, "addr");
					databody.addElement("mailad3").setText(ZipCodeMap.get("0"));
				}else{
					databody.addElement("mailad3").setText("");
				}
			}
			databody.addElement("linkmanName").setText(map.get("linkmanName") == null ? "" : map.get("linkmanName"));// ��ϵ��1
			databody.addElement("contmethInfo").setText(map.get("contmethInfo") == null ? "" : map.get("contmethInfo"));// �绰����1

			String agentName = map.get("agentName");
			Map<String,String> agentMap = new HashMap<String, String>();
			if(agentName!=null && !agentName.toString().trim().equals("")){
				agentMap=splitString(agentName, "custName");
			}
			
			databody.addElement("agentName").setText(agentMap.get("0") == null ? "" : agentMap.get("0"));// �����˻���
			databody.addElement("agentIdentNo").setText(map.get("agentIdentNo") == null ? "" : map.get("agentIdentNo"));// ������֤������
			databody.addElement("agentIdentType").setText(map.get("agentIdentType") == null ? "" : map.get("agentIdentType"));// ������֤������
			databody.addElement("agentNationCode").setText(map.get("agentNationCode") == null ? "" : map.get("agentNationCode"));// �����˹���
		}

		String reqxml = XMLUtils.xmlToString(requestDoc);
		StringBuffer buf = new StringBuffer();
		buf.append(reqxml);
		return buf.toString();
	}

	@SuppressWarnings("rawtypes")
	public List getDataFromDb(String custId) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Object obj = null;
		MCiCustomer customer = new MCiCustomer();
		MCiPerson person = new MCiPerson();
		MCiBusiType busiType=new MCiBusiType();//��ҵ���
		MCiOrg org = new MCiOrg();
		MCiIdentifier ident = new MCiIdentifier();
		MCiAddress address = new MCiAddress();
		MCiGrade grade = new MCiGrade();
		MCiBelongManager manager = new MCiBelongManager();
		MCiAgentinfo agent = new MCiAgentinfo();
		MCiOrgExecutiveinfo execuInfo = new MCiOrgExecutiveinfo();
		MCiPerLinkman PerLinkMan = new MCiPerLinkman();
		MCiInterbank bank = new MCiInterbank();
		Map<String, String> map = new HashMap<String, String>();
		try {
			obj = returnEntiry(custId, "MCiCustomer");
			if (obj != null) {
				customer = (MCiCustomer) obj;
			} else if (customer.getCoreNo() != null) {
				String coreNo = customer.getCoreNo();
				Map<String, String> values = new HashMap<String, String>();
				values.put("custId", custId);
				values.put("srcSysNo", BusinessCfg.getString("cbCd"));
				MCiCrossindex crossIndex;
				try {
					crossIndex = (MCiCrossindex) baseDAO.findUniqueWithNameParam("from MCiCrossindex where custId=:custId and srcSysNo=:srcSysNo ", values);
					if (!coreNo.equals(crossIndex.getSrcSysCustNo())) {
						log.error("ECIF���ݴ���");
						String msg = String.format("ECIF���ݴ��󣬿ͻ���ͻ����Ŀͻ���(%s)�뽻���������к��Ŀͻ���(%s)��һ��", coreNo, crossIndex.getSrcSysCustNo());
						log.error(msg);
						throw new Exception(msg);
					}
				} catch (Exception e) {
					String msg = String.format("��ѯ�ͻ���Ϣ����ͨ��ECIF�ͻ���()��ѯ������������Ŀͻ��Ŵ���", custId);
					log.error(msg);
					log.error("{}", e);
					throw new Exception(msg + e.getLocalizedMessage());
				}
			} else {
				String msg = String.format("��ѯ�ͻ���Ϣ����ͨ��ECIF�ͻ���()��ѯ�ͻ�����ϢΪ��", custId);
				log.warn(msg);
				log.warn("�ͻ�ͬ����Ϣ�ѹ��ڻ�ϵͳ���ݴ���");
				return null;
				// throw new Exception(msg);
			}
			map.put("coreNo", customer.getCoreNo());
			map.put("custName", customer.getCustName());
			map.put("enName", customer.getEnName());
			map.put("custType", customer.getCustType());
			map.put("inoutFlag", customer.getInoutFlag());
			map.put("arCustFlag", customer.getArCustFlag());
			map.put("custGrade", customer.getVipFlag());//VIPFLAG
			map.put("staffin", customer.getStaffin());
			map.put("riskNationCode", customer.getRiskNationCode());
			String custType = customer.getCustType();

			List<Object> identList = returnEntiryAsList(custId, "MCiIdentifier");

			for (Object objIdent : identList) {
				MCiIdentifier identTmp = (MCiIdentifier) objIdent;
				if (identTmp.getIsOpenAccIdent() != null && MdmConstants.IS_CB_OPEN_IDENT.equals(identTmp.getIsOpenAccIdent())) {
					ident = identTmp;
					break;// ����ж������֤����������Ϊ�������⣬Ĭ�Ͽ���֤��ֻ��һ�� TODO
				}
			}
			String identType = ident.getIdentType();//����֤������
			if(identType==null || "".equals(identType) || "99".equals(identType)){
				map.put("identType", "");
			}else{
				map.put("identType", ident.getIdentType());
			}
			map.put("identNo", ident.getIdentNo());//����֤������
			
			//String identType = ident.getIdentType();
			if(identType!=null && !"".equals(identType)){
				if("X1".equals(identType)){//�жϿ���֤�����Ͷ�Ӧ������֤��
					for (Object objIdent : identList) {
						MCiIdentifier identTmp = (MCiIdentifier) objIdent;
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("5")) {
							MCiIdentifier identity = identTmp;//�ֲ�����ȡ֤����
							map.put("busiLicNo",identity.getIdentNo());
							//break;// ����ж������֤����������Ϊ�������⣬Ĭ�Ͽ���֤��ֻ��һ�� TODO
						}
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("5") && identTmp.getIdentExpiredDate()!=null) {
							ident = identTmp;
							break;// ����ж������֤����������Ϊ�������⣬Ĭ�Ͽ���֤��ֻ��һ�� TODO
						}
					}
				}else if("X2".equals(identType)){
					for (Object objIdent : identList) {
						MCiIdentifier identTmp = (MCiIdentifier) objIdent;
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("6")) {
							MCiIdentifier identity = identTmp;//�ֲ�����ȡ֤����
							map.put("busiLicNo",identity.getIdentNo());
							//break;// ����ж������֤����������Ϊ�������⣬Ĭ�Ͽ���֤��ֻ��һ�� TODO
						}
						if (identTmp.getIdentType()!=null && identTmp.getIdentType().equals("6") && identTmp.getIdentExpiredDate()!=null) {
							ident = identTmp;
							break;// ����ж������֤����������Ϊ�������⣬Ĭ�Ͽ���֤��ֻ��һ�� TODO
						}
					}
				}else{
					 if (custType != null && !"".equals(custType) && "2".equals(custType)) {// ����
						
						for(Object objIdent : identList){
							MCiIdentifier identTmp = (MCiIdentifier) objIdent;
							if (identTmp.getIdentType()!=null && (identTmp.getIdentType().equals("5") || identTmp.getIdentType().equals("6"))) {
								MCiIdentifier identity = identTmp;//�ֲ�����ȡ֤����
								map.put("busiLicNo",identity.getIdentNo());//����֤������X1��X2,����̨��֤��۰�ͨ��֤
							}
						}
					}
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (ident.getIdentExpiredDate() != null) {
				String identExpiredDate = sdf.format(ident.getIdentExpiredDate());
				map.put("identValidPeriod", identExpiredDate);
			} else {
				map.put("identValidPeriod", "");
			}
			// map.put("identValidPeriod", ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate().toString()); // TODO: ����ϵͳ֤����Ч�ڶ�ӦECIF֤������IdentExpiredDate
			
			Object objPerson = returnEntiry(custId, "MCiPerson");
			if (objPerson != null) {
				person = (MCiPerson) objPerson;
			}

			String perJoinCustType = (String) person.getJointCustType();
			if (perJoinCustType == null || "".equals(perJoinCustType.trim()) || "null".equals(perJoinCustType.toLowerCase().trim())) {
				perJoinCustType = "";
			}

			Object objOrg = returnEntiry(custId, "MCiOrg");
			if (objOrg != null) {
				org = (MCiOrg) objOrg;
			}

			String OrgJoinCustType = (String) org.getJointCustType();
			if (OrgJoinCustType == null || "".equals(OrgJoinCustType.trim()) || "null".equals(OrgJoinCustType.toLowerCase().trim())) {
				OrgJoinCustType = "";
			}

			log.info("��������־��" + "�������ǣ�" + OrgJoinCustType + "==���˵��ǣ�" + perJoinCustType);

			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				map.put("jointCustType", OrgJoinCustType);
			} else if ("2".equals(custType)) {
				map.put("jointCustType", perJoinCustType);
			}

			// map.put("jointCustType", perJoinCustType+OrgJoinCustType);

			String orgNationCode = (String) org.getNationCode();
			if (orgNationCode == null || "".equals(orgNationCode.trim()) || "null".equals(orgNationCode.toLowerCase().trim())) {
				orgNationCode = "";
			}
			String perNationCode = (String) person.getCitizenship();
			if (perNationCode == null || "".equals(perNationCode.trim()) || "null".equals(perNationCode.toLowerCase().trim())) {
				perNationCode = "";
			}

			log.info("���ڹ���" + "�������ǣ�" + orgNationCode + "==���˵��ǣ�" + perNationCode);
			// map.put("citizenship", person.getCitizenship() + "");
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				map.put("nationCode", orgNationCode);
			} else if ("2".equals(custType)) {// ����
				map.put("nationCode", perNationCode);
			}
			// map.put("nationCode", orgNationCode+perNationCode);
			map.put("orgCustType", org.getLncustp());
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				
				map.put("busiLicNo", org.getBusiLicNo());
			}
			String orgType=org.getOrgType();
			if(orgType==null || "".equals(orgType)|| "99".equals(orgType)){
			    map.put("orgType", "");
			}else{
				map.put("orgType", org.getOrgType());
			}
			String orgSubType =(String) org.getOrgSubType();
			if(orgSubType==null || "".equals(orgSubType)|| "99".equals(orgSubType)){
			    //map.put("orgSubType", "");
				orgSubType="";
			}
			String perOrgSubType=(String)person.getOrgSubType();
			if(perOrgSubType==null || "".equals(perOrgSubType) || "99".equals(perOrgSubType)){
				//map.put("perOrgSubType", "");
				perOrgSubType="";
			}
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				map.put("orgSubType", orgSubType);
			} else if ("2".equals(custType)) {// ����
				map.put("orgSubType", perOrgSubType);
			}
			
			String ifOrgSubType=(String)org.getIfOrgSubType();
			if(ifOrgSubType==null || "".equals(ifOrgSubType)){
				//map.put(ifOrgSubType, "");
				ifOrgSubType="";
			}
			
//			String perOrgSubType=person.getOrgSubType();
//			if(perOrgSubType==null || "".equals(perOrgSubType) || "99".equals(perOrgSubType)){
//				map.put("perOrgSubType", "");
//			}else{
//				map.put("perOrgSubType", person.getOrgSubType());
//			}
			String ifPerOrgSubType=(String)person.getIfOrgSubType();
			if(ifPerOrgSubType==null || "".equals(ifPerOrgSubType)){
				//map.put(ifPerOrgSubType, "");
				ifPerOrgSubType="";
			}
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				map.put("ifOrgSubType", ifOrgSubType);
			} else if ("2".equals(custType)) {// ����
				map.put("ifOrgSubType", ifPerOrgSubType);
			}
			String inCllType=org.getInCllType();
			if(inCllType==null || "".equals(inCllType)){
				inCllType="";
			}
			boolean flag=true;
			if(custType != null && !"".equals(custType) && "1".equals(custType)){//����
				
				Object objBusiType=returnBusiTypeEntiry(inCllType, "MCiBusiType");
				
				if(objBusiType != null){
					
					busiType=(MCiBusiType)objBusiType;
					while(flag){
					String fcode=busiType.getParentCode();
					if(fcode.equals("C")){
							String code=busiType.getFCode();
							if(code.equals("34") || code.equals("35") || code.equals("36") || code.equals("37") || code.equals("38")){
								map.put("inCllType", "5201");
								break;
							}
							if(code.equals("39") || code.equals("40")){
								map.put("inCllType", "5202");
								break;
							}
							if(code.equals("25") || code.equals("30") || code.equals("26") || code.equals("42") || code.equals("27")
									|| code.equals("28") || code.equals("15") || code.equals("32") || code.equals("31") || code.equals("33")){
								map.put("inCllType", "5203");
								break;
							}
							if(code.equals("20") || code.equals("29") || code.equals("13") || code.equals("17") || code.equals("16")
									|| code.equals("18") || code.equals("14") || code.equals("22") || code.equals("19")){
								map.put("inCllType", "5204");
								break;
							}
							if(code.equals("43") || code.equals("41") || code.equals("24") || code.equals("23") || code.equals("21")){
								map.put("inCllType", "5205");
								break;
							}
					}
					if(fcode.equals("U") || fcode.equals("V") || fcode.equals("Y") || fcode.equals("Z")){
							map.put("inCllType", "");
							break;
					}
					//����������ҵ֮���
					busiType=(MCiBusiType)returnBusiTypeEntiry(fcode, "MCiBusiType");
					if(busiType != null && busiType.getParentCode().equals("-1")){
						map.put("inCllType", fcode);
						flag=false;								
					}
					}
					//map.put("inCllType", busiType.getParentCode());	
				}else{
					map.put("inCllType", inCllType);
				}	
			}
			if(custType != null && !"".equals(custType) && "1".equals(custType)){//����
			
			Object addrObject = queryAddress(custId, "addrType", "01", "MCiAddress");
			if (addrObject != null) {
				address = (MCiAddress) addrObject;
			}else {
				Object addrObject1 = queryAddress(custId, "addrType", "02", "MCiAddress");
				if(addrObject1!=null){
					address = (MCiAddress) addrObject1;
				}else{
					Object addrObj = queryAddress(custId, "addrType", "07", "MCiAddress");
					if (addrObj != null) {
						address = (MCiAddress) addrObj;
					}
				}
			}
				map.put("addr", address.getAddr());
				map.put("zipCode", address.getZipcode());
			}else if("2".equals(custType)){//����
				Object addrObject = queryAddress(custId, "addrType", "01", "MCiAddress");
				if(addrObject != null){
					address = (MCiAddress) addrObject;
				}else {
					Object addrObject1 = queryAddress(custId, "addrType", "04", "MCiAddress");
					if(addrObject1 != null){
						address = (MCiAddress) addrObject1;
					}
				}
				map.put("addr", address.getAddr());
				map.put("zipCode", address.getZipcode());
			}
			String adminZone = person.getAreaCode();
			if (adminZone == null || "".equals(adminZone.trim()) || "null".equals(adminZone.toLowerCase().trim())) {
				adminZone = "";
			}
			String adminZone1 = org.getAreaCode();
			if (adminZone1 == null || "".equals(adminZone1.trim()) || "null".equals(adminZone1.toLowerCase().trim())) {
				adminZone1 = "";
			}
			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				map.put("adminZone", adminZone1);
			} else if ("2".equals(custType)) {// ����
				map.put("adminZone", adminZone);
			}
			log.info("�������룺" + "�������ǣ�" + adminZone1 + "==���˵��ǣ�" + adminZone);
			// map.put("adminZone", adminZone1+adminZone);

			Object agentObject = returnEntiry(custId, "MCiAgentinfo");
			if (agentObject != null) {
				agent = (MCiAgentinfo) agentObject;
			}

			map.put("agentName", agent.getAgentName());// �����˻���
			String agentIdentType=agent.getIdentType();
			if(agentIdentType==null || "".equals(agentIdentType) || "99".equals(agentIdentType)){
			   map.put("agentIdentType", "");// ������֤������
			}else{
			   map.put("agentIdentType", agent.getIdentType());// ������֤������
			}
			map.put("agentIdentNo", agent.getIdentNo());// ������֤������
			map.put("agentNationCode", agent.getAgentNationCode());// �����˹���

			Object LinkObject = queryAddress1(custId, "linkmanType", "21", "MCiOrgExecutiveinfo");
			if (LinkObject != null) {
				execuInfo = (MCiOrgExecutiveinfo) LinkObject;
			}

			Object PerObject = queryAddress(custId, "linkmanType", "21", "MCiPerLinkman");
			if (PerObject != null) {
				PerLinkMan = (MCiPerLinkman) PerObject;
			}
			String perlinkmanName = execuInfo.getLinkmanName();
			if (perlinkmanName == null || "".equals(perlinkmanName.trim()) || "null".equals(perlinkmanName.toLowerCase().trim())) {
				perlinkmanName = "";
			}

			String orgLinkmanName = PerLinkMan.getLinkmanName();
			if (orgLinkmanName == null || "".equals(orgLinkmanName.trim()) || "null".equals(orgLinkmanName.toLowerCase().trim())) {
				orgLinkmanName = "";
			}

			if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
//				map.put("linkmanName", orgLinkmanName);
				map.put("linkmanName", "");
			} else if ("2".equals(custType)) {// ����
//				map.put("linkmanName", perlinkmanName);
				map.put("linkmanName", "");
			}

			log.info("��ϵ��1��" + "�������ǣ�" + orgLinkmanName + "==���˵��ǣ�" + perlinkmanName);

			// map.put("linkmanName", orgLinkmanName+perlinkmanName);

			// String contmethInfo = (String)execuInfo.getHomeTel();
			
			/**
			 * ��˵����һ���ܵ��۵������µĶ��ĵĴ���������������¹ʣ����˸Ų�����
			 */
			/*
			List<MCiContmeth> contmeth = baseDAO
					.findWithIndexParam("FROM MCiContmeth where custId=? and contmethType in (" + MdmConstants.SYNC_CONTTYPE_CB_IN + ") order by lastUpdateTm desc", custId);
			String contmethInfo = contmeth == null || contmeth.size() == 0 ? "" : contmeth.get(0).getContmethInfo();
			contmethInfo = contmethInfo == null || "".equals(contmethInfo) || "null".equals(contmethInfo.toLowerCase().trim()) ? "" : contmethInfo;
			log.info(String.format("custId: %s--->> contmethInfo:%s, contmeth.size:%d\n", custId, contmethInfo, contmeth.size()));
			*/
			List<MCiContmeth> contmethList=baseDAO.findWithIndexParam("FROM MCiContmeth where custId=? and contmethType in (" + MdmConstants.SYNC_CONTTYPE_CB_IN + ") order by contmethType,lastUpdateTm desc", custId);
			if(contmethList != null && contmethList.size()>0){	
				for(MCiContmeth contmeth:(List<MCiContmeth>)contmethList){
						if("102".equals(contmeth.getContmethType())){
							map.put("contmethInfo", contmeth.getContmethInfo());
							break;
						}else{
							map.put("contmethInfo", contmeth.getContmethInfo());
						}
					}
			}
			/*
			 * String contmenthInfo1 = (String) PerLinkMan.getMobile();
			 * if (contmenthInfo1 == null && "".equals(contmenthInfo1)) {
			 * contmenthInfo1 = (String) PerLinkMan.getMobile2();
			 * if (contmenthInfo1 == null && "".equals(contmenthInfo1)) {
			 * contmenthInfo1 = (String) PerLinkMan.getTel();
			 * if (contmenthInfo1 == null && "".equals(contmenthInfo1)) {
			 * contmenthInfo1 = (String) PerLinkMan.getTel2();
			 * }
			 * }
			 * }
			 * if (contmethInfo == null || "".equals(contmethInfo.trim()) || "null".equals(contmethInfo.toLowerCase().trim())) {
			 * contmethInfo = "";
			 * }
			 * if (contmenthInfo1 == null || "".equals(contmenthInfo1.trim()) || "null".equals(contmenthInfo1.toLowerCase().trim())) {
			 * contmenthInfo1 = "";
			 * }
			 */
		//	if (custType != null && !"".equals(custType) && "1".equals(custType)) {// ����
				//map.put("contmethInfo", contmethInfo);
		//	//} else if ("2".equals(custType)) {// ����
		//		map.put("contmethInfo", contmenthInfo1);
		//	}
			//log.info("�绰����1��" + "�������ǣ�" + contmethInfo + "==���˵��ǣ�" + contmethInfo);
			// map.put("contmethInfo", contmethInfo+contmenthInfo1);
			/**
			 * ���ĵĴ������
			 */

			Object GradeObject = queryAddress(custId, "custGradeType", "08", "MCiGrade");
			if (GradeObject != null) {
				grade = (MCiGrade) GradeObject;
			}
			//map.put("custGrade", grade.getCustGrade());
			

			Object bankObject = returnEntiry(custId, "MCiInterbank");
			if (bankObject != null) {
				bank = (MCiInterbank) bankObject;
			}
			map.put("swift", bank.getSwift());
			Object ManagerOrg = returnEntiry(custId, "MCiBelongManager");
			if (ManagerOrg != null) {
				manager = (MCiBelongManager) ManagerOrg;
			}
			map.put("custManagerNo", manager.getCustManagerNo());

			list.add(map);
		} catch (Exception e) {
			log.error("���ݿ����ʧ�ܣ�" + e.getMessage());
			log.error("������Ϣ��{}", e);
		}

		return list;

	}

	public Object queryAddress1(String custId, String str, String addrType, String simpleNames) throws Exception {

		try {
			// ����
			String simpleName = simpleNames;
			// ��ñ���
			String tableName = simpleName;
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");
			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.orgCustId =:orgCustId");
			jql.append(" AND a." + str + " =:" + str + "");
			// ����ѯ���������뵽map��������
			paramMap.put("orgCustId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
		}
		return null;
	}

	public Object queryAddress(String custId, String str, String addrType, String simpleNames) throws Exception {

		try {
			// ����
			String simpleName = simpleNames;
			// ��ñ���
			String tableName = simpleName;
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");
			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.custId =:custId");
			jql.append(" AND a." + str + " =:" + str + "");
			// ����ѯ���������뵽map��������
			paramMap.put("custId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }

		} catch (Exception e) {
			log.error("��ѯ�ͻ����ʧ�ܣ�" + e.getMessage());
		}
		return null;
	}

	public Object returnBusiTypeEntiry(String fCode, String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.fCode =:fCode");
		// ����ѯ���������뵽map��������
		paramMap.put("fCode", fCode);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

	}
	public Object returnEntiry(String custId, String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// ����ѯ���������뵽map��������
		paramMap.put("custId", custId);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

	}

	public List<Object> returnEntiryAsList(String custId, String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// ����ѯ���������뵽map��������
		paramMap.put("custId", custId);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

		return null;
	}

	public  Map<String, String> splitString(String str, String type) throws Exception {
		if(str!=null && !"".equals(str.trim())){
			str = newString(str);
			int custNamecount = 50;
			int addrcount = 35;
			if (type.toString().equals("custName")) {
				return splitStr(str, custNamecount);
			} else if (type.toString().equals("addr")) { 
				return splitStr(str, addrcount); 
			}else if (type.toString().equals("enName")){
				return splitStr(str, addrcount);
			}
		}
		return null;
	}
	
	
	
	public  String newString(String str) throws Exception{
		if(str!=null && !"".equals(str.trim())){
			char[] charArray = str.toCharArray();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i <charArray.length; i++) {
				if (i == 0) {
					if(returnSpecial(charArray[i])) {
						sb.append(" " + charArray[i]); // ���ǵ��ֽڵ����ӿո�
					}else{
						sb.append(charArray[i]); // �ǵ��ֽڵ�ֱ�����
					}
				}else if(i == (charArray.length-1)){//��������һ���ַ���
					char bs = charArray[i - 1];
					if (returnSpecial(bs)) {// ǰ���Ǹ����ǵ��ֽ�
						if(returnSpecial(charArray[i])) {//���һ�����ǵ��ֽ�
							sb.append(charArray[i]+" ");
						}else{//��ǰ�ǵ��ֽ�
							sb.append(" "+charArray[i]);
						}
					}else{//ǰ���Ǹ��ǵ��ֽ�
						if(returnSpecial(charArray[i])) {//���һ�����ǵ��ֽ�
							sb.append(" "+charArray[i]+" ");
						}else{//��ǰ�ǵ��ֽ�
							sb.append(charArray[i]);
						}
					}
				}else {
					char bs = charArray[i - 1];
					char chr = charArray[i];
					if (returnSpecial(chr)) {// ��ǰ���ǵ��ֽ�
						if(returnSpecial(bs)){//ǰ���Ǹ����ǵ��ֽ�
							sb.append(chr);
						}else{//�ǵ��ֽ�
						    sb.append(" "+chr);
						}
					} else {// ��ǰ�ǵ��ֽ�
						if (returnSpecial(bs)) {// ǰ���Ǹ����ǵ��ֽ�
							 sb.append(" "+chr);
						} else {//ǰ���Ǹ��ǵ��ֽ�
							 sb.append(chr);
						}
					}
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	
	public  Map<String,String> splitStr(String str,int countType){
		char[] charArray = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		String nowStr = "";
		for (int i = 0; i <charArray.length; i++) {
			if(sb.toString().getBytes().length==(countType-1)){
				char bs = charArray[i-1];
				char chr = charArray[i];
				if(returnSpecial(chr)){//������ǵ��ֽ�
					map.put("" + count, sb.toString());
					count++;
					nowStr=" "+chr;
					sb = new StringBuffer();
				}else{//��ǰ�ǵ��ֽ�
					if(returnSpecial(bs)){//ǰ�治�ǵ��ֽ�
						map.put("" + count, sb.toString());
						count++;
						nowStr=""+chr;
						sb = new StringBuffer();
					}else{//ǰ���ǵ��ֽ�
						sb.append(chr);
						map.put("" + count, sb.toString());
						count++;
						sb = new StringBuffer();
					}
				}
			}else if(sb.toString().getBytes().length==countType){//��ǰ�����Ѿ���35��
				char bs = charArray[i-1];
				char chr = charArray[i];
				if(returnSpecial(bs)){//ǰ����һ�����ǵ��ֽ�
					int bsCount= sb.toString().lastIndexOf((bs+""));
					String strs = sb.substring(0, bsCount);
					map.put("" + count, strs);
					strs="";
					count++;
					nowStr=" "+bs+chr;
					sb = new StringBuffer();
				}else{//ǰ���Ǹ��ǵ��ֽ�
					map.put("" + count, sb.toString());
					count++;
					if(returnSpecial(chr)){//��ǰ�Ĳ��ǵ��ֽ�
						nowStr=" "+chr;
					}else{//��ǰ���ǵ��ֽ�
						nowStr=""+chr;
					}
					
					sb = new StringBuffer();
				}
			}else if (sb.toString().getBytes().length == 0) {
				char chr = charArray[i];
				String newChr = "";
				if("".equals(nowStr)){
					if(returnSpecial(charArray[i])){//��ǰ���ǵ��ֽ�
						newChr=" "+chr;
					}else{//��ǰ�ǵ��ֽ�
						newChr=""+chr;
					}
				}else{
					newChr=""+chr;
				}
				sb.append(nowStr);
				nowStr="";
				sb.append(newChr);
				if(i==(charArray.length-1)){
					map.put("" + count, sb.toString());
					count++;
					sb = new StringBuffer();
				}
			}else if(i==(charArray.length-1)){
				sb.append(charArray[i]);
				map.put("" + count, sb.toString());
				count++;
				sb = new StringBuffer();
			}else{
				sb.append(charArray[i]);
			}
		}
		return map;
	}
	
	
	
//	/**
//	 * ���ַ����ָ�
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public  Map<String, String> splitraddress(String str, int countType) throws Exception {
//		char[] charArray = str.toCharArray();
//		StringBuffer sb = new StringBuffer();
//		int count = 0;
//		Map<String, String> map = new HashMap<String, String>();
//		String nowStr = "";
//		for (int i = 0; i <charArray.length; i++) {
//			if (i == 0) {
//				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// ����һ���Ƿ��Ǻ���
//					sb.append(" " + charArray[i]); // �Ǻ��ֽ����һ���ո�
//				} else if(returnSpecial(charArray[i])) {
//					sb.append(" " + charArray[i]); // �Ǻ��ֽ����һ���ո�
//				}else{
//					sb.append(charArray[i]); // ���Ǻ���ֱ��ƴ��
//				}
//			} else {
//				if ((sb.toString().getBytes().length) >= countType || i == (charArray.length-1)) {//�жϳ����Ƿ񳬹��涨���Ȼ����Ƿ������һ���ַ�
//					
//					if (sb.toString().getBytes().length == 0 || i==(charArray.length-1)) {
//						sb.append(nowStr);
//						char bs = charArray[i-1];//ǰ��һ����
//						if((bs >= 0x4e00) && (bs <= 0x9fbb)){//���ǰ���Ǹ����Ǻ���
//							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���ֱ��ƴ��
//								sb.append(charArray[i]+" ");
//							}else{
//								if(returnSpecial(charArray[i])){
//									sb.append(charArray[i]);
//								}else{
//								   sb.append(" "+charArray[i]);
//								}
//							}
//						}else{//ǰ���Ǹ����Ǻ���
//							if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���
//								if(returnSpecial(bs)){
//									sb.append(charArray[i]);
//								}else{
//								    sb.append(" "+charArray[i]);
//								}
//							}else{//��ǰ���Ǻ���
//								if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
//								    sb.append(" "+charArray[i]);
//								}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
//									 sb.append(" "+charArray[i]);
//								}else{
//								     sb.append(charArray[i]);
//								}
//							}
//						}
//						map.put("" + count, sb.toString());
//					}else{
//						char bs = charArray[i - 1];//ǰ��һ����
//						if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {// ������һ���Ƿ��Ǻ���
//							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// ������һ���Ǻ��ӣ��ж�ǰ��һ���Ƿ��Ǻ����Ǻ������ӿո�
//								if ((sb.toString().getBytes().length) >= countType) {
//									sb.append(" "); // �Ǻ��ֽ����һ���ո�
//								} else {
//									sb.append(charArray[i] + " ");
//								}
//							} else {//���һ�����Ǻ���
//								if ((sb.toString().getBytes().length) <= countType && i == charArray.length) {
//									if(returnSpecial(bs)){
//										sb.append(charArray[i]);
//									}else{
//										sb.append(" "+charArray[i]);
//									}
//									
//								}
//								
//							}
//							nowStr = " " + charArray[i];
//							
//						} else {// ��ǰ���Ǻ��֣��ж�ǰ��һ���Ƿ��Ǻ��֣����ǰ���Ǻ��Ӳ����ӣ����ǰ�治�Ǻ��־�����
//							if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {
//								if(returnSpecial(charArray[i])){
//									sb.append(charArray[i]);
//								}else{
//								    sb.append(" "+charArray[i]);
//								}
//							} else {
//								if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
//								    sb.append(" "+charArray[i]);
//								}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
//									 sb.append(" "+charArray[i]);
//								}else{
//								     sb.append(charArray[i]);
//								}
//							}
//							nowStr = "";
//						}
//						map.put("" + count, sb.toString());
//						count++;
//						sb = new StringBuffer();
//						continue;
//					}
//					
//					
//				}
//				if (sb.toString().getBytes().length == 0) {
//					sb.append(nowStr);
//					nowStr="";
//					char bs = charArray[i-1];//ǰ��һ����
//					if((bs >= 0x4e00) && (bs <= 0x9fbb)){//���ǰ���Ǹ����Ǻ���
//						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���ֱ��ƴ��
//							sb.append(charArray[i]);
//						}else{
//							if(returnSpecial(charArray[i])){
//							   sb.append(charArray[i]);
//							}else{
//							   sb.append(" "+charArray[i]);
//							}
//						}
//					}else{
//						if((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)){//�����ǰ�Ǻ���
//							if(returnSpecial(bs) && sb.toString().getBytes().length >0){
//								sb.append(charArray[i]);
//							}else{
//							    sb.append(" "+charArray[i]);
//							}
//						}else{
//							if(returnSpecial(bs) && !returnSpecial(charArray[i]) ){
//							    sb.append(" "+charArray[i]);
//							}else if(!returnSpecial(bs) && returnSpecial(charArray[i])){
//								 sb.append(" "+charArray[i]);
//							}else if(returnSpecial(charArray[i]) && sb.toString().getBytes().length == 0){
//								 sb.append(" "+charArray[i]);
//							}else{
//								 sb.append(charArray[i]);
//							}
//							
//						}
//					}
//					
//				} else {
//					char bs = charArray[i - 1];
//					char chr = charArray[i];
//					if ((chr >= 0x4e00) && (chr <= 0x9fbb)) {// ��ǰ������Ǻ���
//						if ((bs >= 0x4e00) && (bs <= 0x9fbb)) {// ǰ���Ǹ��Ƿ��Ǻ���
//							sb.append(chr);
//						} else {
//							if(returnSpecial(bs)){
//								sb.append(chr);
//							}else{
//							    sb.append(" "+chr);
//							}
//						}
//
//					} else {// ��ǰ�õ��Ĳ��Ǻ���
//						if (((bs >= 0x4e00) && (bs <= 0x9fbb))) {// �����ǰ����Ǻ���
//							if(returnSpecial(chr)){
//								sb.append(chr);
//							}else{
//							    sb.append(" "+chr);
//							}
//						} else {//ǰ�治�Ǻ���
//							if(returnSpecial(bs) && !returnSpecial(chr)){
//								sb.append(" "+chr);
//							}else if(!returnSpecial(bs) && returnSpecial(chr)){
//								sb.append(" "+chr);
//							}else{
//							    sb.append(chr);
//							}
//						}
//					}
//				}
//			}
//		}	
//		return map;
//	}

	
	public  boolean returnSpecial(char chr){
		
		boolean flag = false;
		String s =chr+"";
		if(s.getBytes().length==1){
			flag=false;
		}else{
			flag=true;
		}
//		char[] chrs = {'��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��'};
//		for(int i =0;i<chrs.length;i++){
//			if(chr==chrs[i]){
//				flag=true;
//			}
//		}
		return flag;
	}
	// TODO �ı��ĸ�ʽ
	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {
		return true;
	}

	@Override
	public boolean executeResult() {
		if (this.synchroResponseMsg != null) {
			// log.info("����ͬ����Ӧ���ģ�\n{}", this.synchroResponseMsg);
			// TODO
			try {
				Document root = XMLUtils.stringToXml(synchroResponseMsg.substring(8));

				String txStatCodeXpath = "//TransBody/ResponseTail/TxStatCode";
				String txStatDescXpath = "//TransBody/ResponseTail/TxStatString";
				String txStatDetailXpath = "//TransBody/ResponseTail/TxStatDesc";

				Node txStatCodeNode = root.selectSingleNode(txStatCodeXpath);
				Node txStatDescNode = root.selectSingleNode(txStatDescXpath);
				Node txStatDetailNode = root.selectSingleNode(txStatDetailXpath);
				if (txStatCodeNode == null || txStatDescNode == null || txStatDetailNode == null) {
					String msg = String.format("{},�յ���Χϵͳ[%s]��Ӧ���ı�Ҫ�ڵ�Ϊ��[��Ҫ�ڵ㣺%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("cbCd"), txStatCodeXpath,
							txStatDescXpath, txStatDetailXpath);
					log.error(msg);

					syncLog = new TxSyncLog();
					syncLog.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
					syncLog.setSyncDealInfo(msg);

					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getCode());
					syncErr.setSyncDealInfo(msg);

					return false;
				}
				String txStatCode = txStatCodeNode.getText().trim();
				String txStatDesc = txStatDescNode.getText().trim();
				String txStatDetail = "��Χϵͳ[" + BusinessCfg.getString("cbCd") + "]��Ӧ:txStatDesc:{" + txStatDesc + "},txStatDetail:{" + txStatDetailNode.getText() + "}";

				syncLog = new TxSyncLog();
				syncLog.setSyncDealResult(txStatCode);
				syncLog.setSyncDealInfo(txStatDetail);

				// TODO 0000 to Constant
				if ("000000".equals(txStatCode)) {
					return true;
				} else {
					syncErr = new TxSyncErr();
					syncErr.setSyncDealResult(txStatCode);
					syncErr.setSyncDealInfo(txStatDetail);
					return false;
				}
			} catch (DocumentException e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("cbCd"), e);
				return false;
			} catch (Exception e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("cbCd"), e);
				return false;
			}
		} else {
			// TODO
			log.info("ͬ����Ӧ����Ϊ��");
			return false;
		}
	}
}
