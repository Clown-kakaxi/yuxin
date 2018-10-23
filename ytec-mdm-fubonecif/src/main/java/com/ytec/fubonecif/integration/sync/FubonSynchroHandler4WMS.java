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
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCrossindex;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiOrg;
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

@Component
@Scope("prototype")
public class FubonSynchroHandler4WMS extends SynchroExecuteHandler{
	private static Logger log = LoggerFactory.getLogger(FubonSynchroHandler4WMS.class);
	private static String ECIFSYSCD = BusinessCfg.getString("appCd");
	
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
			synchroRequestMsg = syncPackageReqXml(custId);//ƴ����װͬ��������

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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String syncPackageReqXml(String custId) throws Exception{
		List list = null;
		try {
			list = getSyncDataFromDb(custId);
			
			if (list == null) {
				String msg = String.format("��ѯ�ͻ�(%s)��Ϣʧ�ܣ��޷�ͬ����Ϣ���Ƹ�����ϵͳ", custId);
				log.error(msg);
				throw new Exception(msg);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df20 = new SimpleDateFormat("HHmmss");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Document requestDoc = DocumentHelper.createDocument();
		requestDoc.setXMLEncoding(MdmConstants.TX_XML_ENCODING);
		Element transBody = requestDoc.addElement("TransBody");

		Element requestHeader = transBody.addElement("RequestHeader");

		requestHeader.addElement("ReqSysCd").setText(ECIFSYSCD);// ReqSysCd ��Χϵͳ����
		requestHeader.addElement("ReqSeqNo").setText(df.format(new Date()).toString()+"000");// ReqSeqNo ��Χϵͳ������ˮ��
		requestHeader.addElement("ReqDt").setText(df8.format(new Date()));// ReqDt ��������
		requestHeader.addElement("ReqTm").setText(df20.format(new Date()));// ReqTm ����ʱ��
		requestHeader.addElement("DestSysCd").setText("WMS");// Ŀ��ϵͳ����
		requestHeader.addElement("ChnlNo").setText("");// ChnlNo ������
		requestHeader.addElement("BrchNo").setText("");// BrchNo ������
		requestHeader.addElement("BizLine").setText("");// BizLine ҵ������
		requestHeader.addElement("TrmNo").setText("");// TrmNo �ն˺�
		requestHeader.addElement("TrmIP").setText("");// TrmIP �ն�IP
		requestHeader.addElement("TlrNo").setText("");// TlrNo ������Ա��
		
		Element databody = transBody.addElement("RequestBody");
		
		for(int i = 0; i < list.size(); i++){
			Map<String, String> map = (Map<String, String>) list.get(i);
			String coreNo = map.get("coreNo");
			Element customerbody=databody.addElement("customer");
			customerbody.addElement("coreNo").setText(coreNo == null ? "" : coreNo);
			customerbody.addElement("custId").setText(map.get("custId") == null ? "" : map.get("custId"));
			customerbody.addElement("custType").setText(map.get("custType") == null ? "" : map.get("custType"));
			/*if(map.get("identType").toString().equals("X1") ||   map.get("identType").toString().equals("X2")){
				customerbody.addElement("identType").setText(map.get("identType") == null ? "" : map.get("identType"));
				customerbody.addElement("identNo").setText(map.get("identNo") == null ? "" : map.get("identNo"));
			}else{
				customerbody.addElement("identType1").setText(map.get("identType") == null ? "" : map.get("identType"));
				customerbody.addElement("identNo1").setText(map.get("identNo") == null ? "" : map.get("identNo"));
			}*/
//			customerbody.addElement("identType").setText(map.get("identType") == null ? "" : map.get("identType"));
//			customerbody.addElement("identNo").setText(map.get("identNo") == null ? "" : map.get("identNo"));
			customerbody.addElement("custName").setText(map.get("custName") == null ? "" : map.get("custName"));
			customerbody.addElement("enName").setText(map.get("enName") == null ? "" : map.get("enName"));
			customerbody.addElement("shortName").setText(map.get("shortName") == null ? "" : map.get("shortName"));
			customerbody.addElement("vipFlag").setText(map.get("vipFlag") == null ? "" : map.get("vipFlag"));
			customerbody.addElement("inoutFlag").setText(map.get("inoutFlag") == null ? "" : map.get("inoutFlag"));
			customerbody.addElement("custLevel").setText(map.get("custLevel") == null ? "" : map.get("custLevel"));
			customerbody.addElement("createBranchNo").setText(map.get("createBranchNo") == null ? "" : map.get("createBranchNo"));
			customerbody.addElement("cardLvl").setText(map.get("cardLvl") == null ? "" : map.get("cardLvl"));
			//add by liuming 20170808
			//identType1:̨��֤��۰�ͨ��֤֤������(֤�����ͳ���20,ע��ֻ����identTypeΪ��X2=̨�����֤����X1=�۰����֤��ʱ�����ֶ���ֵ)
			//identNo1:̨��֤��۰�ͨ��֤����(֤�����볤��32,ע��identType1��ֵ������ֶα�����ֵ)
			//5=�۰ľ��������ڵ�ͨ��֤,6=̨��ͬ�������ڵ�ͨ��֤
			/*List<Object> identifierList= baseDAO.findWithIndexParam("FROM MCiIdentifier where custId=? and (identType = '6' or identType = '5')  and identNo is not null and ltrim(rtrim(identNo)) is not null ", custId);
			MCiIdentifier identifier = new MCiIdentifier();
			if(identifierList != null 
					&& identifierList.size() > 0
					&& map.get("identType") != null
					&& (map.get("identType").toString().equals("X1") || map.get("identType").toString().equals("X2"))){
				identifier = (MCiIdentifier)identifierList.get(0);
				customerbody.addElement("identType1").setText(identifier.getIdentType());
				customerbody.addElement("identNo1").setText(identifier.getIdentNo());
			}else{
				customerbody.addElement("identType1").setText("");
				customerbody.addElement("identNo1").setText("");
			}
			List<Object> identifierList1= baseDAO.findWithIndexParam("FROM MCiIdentifier where custId=? and (identType = 'X1' or identType = 'X2')  and identNo is not null and ltrim(rtrim(identNo)) is not null ", custId);
			MCiIdentifier identifier1 = new MCiIdentifier();
			if(identifierList1 != null 
					&& identifierList1.size() > 0
					&& map.get("identType") != null
					&& (map.get("identType").toString().equals("5") || map.get("identType").toString().equals("6"))){
				identifier1 = (MCiIdentifier)identifierList1.get(0);
				customerbody.addElement("identType").setText(identifier1.getIdentType());
				customerbody.addElement("identNo").setText(identifier1.getIdentNo());
			}*/
			//add end
			
			List ident1List = getIdent1Info(custId);
			List ident2List = getIdent2Info(custId);
			String identMat = "";
			if(ident1List!=null&&ident1List.size()!=0){
				Object[] identInfo = (Object[]) ident1List.get(0);
				customerbody.addElement("identType").setText(identInfo[0] == null ? "" : identInfo[0].toString());
				customerbody.addElement("identNo").setText(identInfo[1] == null ? "" : identInfo[1].toString());
				identMat = (identInfo[2] == null || "".equals(identInfo[2])) ? "" : df8.format(df.parse(identInfo[2].toString()));
			}else{
				customerbody.addElement("identType").setText("");
				customerbody.addElement("identNo").setText("");
			}
			if(ident2List!=null&&ident2List.size()!=0){
				Object[] identInfo = (Object[]) ident2List.get(0);
				customerbody.addElement("identType1").setText(identInfo[0] == null ? "" : identInfo[0].toString());
				customerbody.addElement("identNo1").setText(identInfo[1] == null ? "" : identInfo[1].toString());
				String tempIdentMat = (identInfo[2] == null || "".equals(identInfo[2])) ? "" : df8.format(df.parse(identInfo[2].toString()));
				if(StringUtils.isNotEmpty(tempIdentMat)){
					identMat = tempIdentMat;//���X1��X2�ĸ���֤������֤����Ч�ڣ���ȡ��
				}
			}else{
				customerbody.addElement("identType1").setText("");
				customerbody.addElement("identNo1").setText("");
			}
			customerbody.addElement("identMat").setText(identMat == null ? "" : identMat);
			Element personbody=databody.addElement("person");
			personbody.addElement("pinyinName").setText(map.get("pinyinName") == null ? "" : map.get("pinyinName"));
			personbody.addElement("birthday").setText(map.get("birthday") == null ? "" : map.get("birthday"));
			personbody.addElement("highestSchooling").setText(map.get("highestSchooling") == null ? "" : map.get("highestSchooling"));
			personbody.addElement("careerType").setText(map.get("careerType") == null ? "" : map.get("careerType"));
			personbody.addElement("citizenship").setText(map.get("citizenship") == null ? "" : map.get("citizenship"));
			personbody.addElement("annualIncomeScope").setText(map.get("annualIncomeScope") == null ? "" : map.get("annualIncomeScope"));
			personbody.addElement("intEmp").setText(map.get("intEmp") == null ? "" : map.get("intEmp"));
			personbody.addElement("custSex").setText(map.get("custSex") == null ? "" : map.get("custSex"));
			
			Element orgbody=databody.addElement("org");
			orgbody.addElement("orgCustType").setText(map.get("orgCustType") == null ? "" : map.get("orgCustType"));
			orgbody.addElement("orgBizCustType").setText(map.get("orgBizCustType") == null ? "" : map.get("orgBizCustType"));
			orgbody.addElement("legalReprName").setText(map.get("legalReprName") == null ? "" : map.get("legalReprName"));
			orgbody.addElement("legalReprIdentType").setText(map.get("legalReprIdentType") == null ? "" : map.get("legalReprIdentType"));
			orgbody.addElement("legalReprIdentNo").setText(map.get("legalReprIdentNo") == null ? "" : map.get("legalReprIdentNo"));
			orgbody.addElement("lncustp").setText(map.get("lncustp") == null ? "" : map.get("lncustp"));
			orgbody.addElement("registerDate").setText(map.get("registerDate") == null ? "" : map.get("registerDate"));
			orgbody.addElement("businessScope").setText(map.get("businessScope") == null ? "" : map.get("businessScope"));
			
			List<MCiContmeth> contList= baseDAO.findWithIndexParam("FROM MCiContmeth where custId=?", custId);
			MCiContmeth contmeth = new MCiContmeth();
			if(contList != null && contList.size() > 0){
				
				for (int j=0;j<contList.size();j++) {
					Element contmethbody = databody.addElement("contmeth");
					contmeth=(MCiContmeth)contList.get(j);
					contmethbody.addElement("contmethId").setText(contmeth.getContmethId() == null ? "" : contmeth.getContmethId());
					contmethbody.addElement("contmethType").setText(contmeth.getContmethType() == null ? "" : contmeth.getContmethType());
					contmethbody.addElement("contmethInfo").setText(contmeth.getContmethInfo() == null ? "" : contmeth.getContmethInfo());
					
				}	
			}
			List<Object> addressList= baseDAO.findWithIndexParam("FROM MCiAddress where custId=?", custId);
			MCiAddress address = new MCiAddress();
			if(addressList !=null && addressList.size() > 0){
				
				for(int j=0;j<addressList.size();j++){
					Element addressbody = databody.addElement("address");
					address=(MCiAddress)addressList.get(j);
					addressbody.addElement("addrId").setText(address.getAddrId() == null ? "" : address.getAddrId());
					addressbody.addElement("addrType").setText(address.getAddrType() == null ? "" : address.getAddrType());
					addressbody.addElement("addr").setText(address.getAddr() == null ? "" : address.getAddr());
					addressbody.addElement("zipcode").setText(address.getZipcode() == null ? "" : address.getZipcode());
					addressbody.addElement("countryOrRegion").setText(address.getCountryOrRegion() == null ? "" : address.getCountryOrRegion());
				
				}
			}
			
			Element belongManagerbody=databody.addElement("belongManager");
			belongManagerbody.addElement("custManagerNo").setText(map.get("custManagerNo") == null ? "" : map.get("custManagerNo"));
			belongManagerbody.addElement("orgId").setText(map.get("orgId") == null ? "" : map.get("orgId"));
			belongManagerbody.addElement("accountName").setText(map.get("accountName") == null ? "" : map.get("accountName"));
			belongManagerbody.addElement("userName").setText(map.get("userName") == null ? "" : map.get("userName"));
			
		}
		
		String reqxml = XMLUtils.xmlToString(requestDoc);
		StringBuffer buf = new StringBuffer();
		buf.append(reqxml);
		return buf.toString();
	}

	
	@SuppressWarnings("rawtypes")
	public List getSyncDataFromDb(String custId) throws Exception{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Object obj = null;
		MCiCustomer customer = new MCiCustomer();
		MCiPerson person = new MCiPerson();
		MCiOrg org = new MCiOrg();
		//MCiContmeth contmeth = new MCiContmeth();
		//MCiAddress address = new MCiAddress();
		MCiBelongManager manager = new MCiBelongManager();
		Map<String, String> map = new HashMap<String, String>();
		
		try{
			obj = returnEntiry(custId, "MCiCustomer");
			if (obj != null) {
				customer = (MCiCustomer) obj;
			}else if(customer.getCoreNo() != null){
				String coreNo = customer.getCoreNo();
				Map<String, String> values = new HashMap<String, String>();
				values.put("custId", custId);
				values.put("srcSysNo", BusinessCfg.getString("cbCd"));
				MCiCrossindex crossIndex;
				try {
					crossIndex = (MCiCrossindex) baseDAO.findUniqueWithNameParam("from MCiCrossindex where custId=:custId and srcSysNo=:srcSysNo ", values);
					if (!coreNo.equals(crossIndex.getSrcSysCustNo())) {
						log.error("ECIF���ݴ���");
						String msg = String.format("ECIF���ݴ��󣬿ͻ���ͻ����Ŀͻ���(%s)�뽻����������Ŀͻ���(%s)��һ��", coreNo, crossIndex.getSrcSysCustNo());
						log.error(msg);
						throw new Exception(msg);
					}
				} catch (Exception e) {
					String msg = String.format("��ѯ�ͻ���Ϣ����ͨ��ECIF�ͻ���()��ѯ������������Ŀͻ��Ŵ���", custId);
					log.error(msg);
					log.error("{}", e);
					throw new Exception(msg + e.getLocalizedMessage());
				}	
			}else{
				String msg = String.format("��ѯ�ͻ���Ϣ����ͨ��ECIF�ͻ���()��ѯ�ͻ�����ϢΪ��", custId);
				log.warn(msg);
				log.warn("�ͻ�ͬ����Ϣ�ѹ��ڻ�ϵͳ���ݴ���");
				return null;
			}
			String identType=null;
			String identNo=null;
			String custType = customer.getCustType();
			map.put("coreNo", customer.getCoreNo());
			map.put("custId", customer.getCustId());
			map.put("custType", customer.getCustType());
			//map.put("identType", customer.getIdentType());
			//map.put("identNo", customer.getIdentNo());
			if (custType != null && !"".equals(custType) && "2".equals(custType)) {//���˻�
				List<Object> identList=returnEntiryAsList(custId,"MCiIdentifier");//ͨ��custId��ѯ�ͻ�֤����Ϣ
				if(identList != null && identList.size() > 0){
					for(int i=0;i<identList.size();i++){
						MCiIdentifier identTmp = (MCiIdentifier) identList.get(i);
						if("2".equals(identTmp.getIdentType()) || "7".equals(identTmp.getIdentType()) || "0".equals(identTmp.getIdentType()) 
//								|| "5".equals(identTmp.getIdentType()) || "6".equals(identTmp.getIdentType()) 
//								modify by liuming 20170809,ͬ��2=̨�����֤��X1=�۰����֤��WMS
								|| "X1".equals(identTmp.getIdentType()) || "X2".equals(identTmp.getIdentType())
								|| "X6".equals(identTmp.getIdentType()) || "X14".equals(identTmp.getIdentType())
								|| "X24".equals(identTmp.getIdentType())){
							identType=identTmp.getIdentType();
							identNo=identTmp.getIdentNo();
						}
					}
				}
			}else if ("1".equals(custType)) {//������
				List<Object> identList=returnEntiryAsList(custId,"MCiIdentifier");//ͨ��custId��ѯ�ͻ�֤����Ϣ
				if(identList != null && identList.size() > 0){
					for(int i=0;i<identList.size();i++){
						MCiIdentifier identTmp = (MCiIdentifier) identList.get(i);
						if("2X".equals(identTmp.getIdentType()) || "20".equals(identTmp.getIdentType())){
							identType=identTmp.getIdentType();
							identNo=identTmp.getIdentNo(); 
						}
					}
				}
			}
			map.put("identType", identType);
			map.put("identNo", identNo);
			map.put("custName", customer.getCustName());
			map.put("enName", customer.getEnName());
			map.put("shortName", customer.getShortName());
			map.put("vipFlag", customer.getVipFlag());//����û��ֵ
			map.put("inoutFlag", customer.getInoutFlag());
			map.put("createBranchNo",customer.getCreateBranchNo());
			
			String sql="select to_char(max(highest_card_level)) as CARD_LVL,max(cust_grade) as CUST_GRADE " +
					"from OCRM_F_CI_CARD_TOTAL@FBCRM where cust_id = '"+custId+"' group by cust_id";
			List<Object[]> cardApplys=baseDAO.findByNativeSQLWithIndexParam(sql, null);
			String cardLvl=null;
			String custLevel=null;
			if(cardApplys != null && cardApplys.size() > 0){
				for(Object[] objArray:cardApplys){
					cardLvl=(String) objArray[0];
					custLevel=(String) objArray[1];
				}
			}
			map.put("cardLvl",cardLvl);
			map.put("custLevel", custLevel); 
			map.put("intEmp", "S".equals(customer.getStaffin()) ? "Y" : "N");//Ա����ʶ
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			Object objPerson = returnEntiry(custId, "MCiPerson");
			if (objPerson != null) {
				person = (MCiPerson) objPerson;
				map.put("pinyinName", person.getPinyinName());
				map.put("birthday", sdf.format(person.getBirthday()));
				map.put("highestSchooling", person.getHighestSchooling());
				map.put("careerType", person.getCareerType());
				map.put("citizenship", person.getCitizenship());
				map.put("annualIncomeScope", person.getAnnualIncomeScope());
				map.put("custSex", "1".equals(person.getGender()) ? "M" : "2".equals(person.getGender()) ? "F" : "");//�Ա�
			}else{
				map.put("pinyinName", "");
				map.put("birthday", "");
				map.put("highestSchooling", "");
				map.put("careerType", "");
				map.put("citizenship", "");
				map.put("annualIncomeScope", "");
				map.put("custSex", "");
			}
			
			Object objOrg = returnEntiry(custId, "MCiOrg");
			if (objOrg != null) {
				org = (MCiOrg) objOrg;
				map.put("orgCustType", org.getOrgCustType());
				map.put("orgBizCustType", org.getOrgBizCustType());
				map.put("legalReprName", org.getLegalReprName());
				map.put("legalReprIdentType", org.getLegalReprIdentType());
				map.put("legalReprIdentNo", org.getLegalReprIdentNo());
				map.put("lncustp", org.getLncustp());
				Date registerDate=null;
				sql="select t.REGISTER_DATE from ACRM_F_CI_ORG_REGISTERINFO@FBCRM t where t.CUST_ID='"+custId+"'";
				List<Object> orgRegisterInfo=baseDAO.findByNativeSQLWithIndexParam(sql, null);
				for(int i=0;i<orgRegisterInfo.size();i++){
					if(orgRegisterInfo.get(0) != null){
						registerDate=sdf.parse(orgRegisterInfo.get(0).toString());
					}
				}
				map.put("registerDate", registerDate == null ? "" : sdf.format(registerDate));
				map.put("businessScope", org.getInCllType());
			}else{
				map.put("orgCustType", "");
				map.put("orgBizCustType", "");
				map.put("legalReprName", "");
				map.put("legalReprIdentType", "");
				map.put("legalReprIdentNo", "");
				map.put("lncustp", "");
				map.put("registerDate","");
				map.put("businessScope", "");
			}
			
			
			Object objManager=returnEntiry(custId,"MCiBelongManager");
			String custManagerNo=null;
			if (objManager != null) {
				manager = (MCiBelongManager) objManager;
				custManagerNo=manager.getCustManagerNo() == null ? "" : manager.getCustManagerNo().trim();
			}
			//String custManagerNo=manager.getCustManagerNo();
			String orgId=null;
			String accountName=null;
			String userName=null;
			map.put("custManagerNo", manager.getCustManagerNo());
			sql="select t.ORG_ID,t.ACCOUNT_NAME,t.USER_NAME from ADMIN_AUTH_ACCOUNT@FBCRM t where t.ACCOUNT_NAME='"+custManagerNo+"'";
			List<Object[]> belongManagerInfo=baseDAO.findByNativeSQLWithIndexParam(sql, null);
			for(int i=0;i<belongManagerInfo.size();i++){
				orgId=(String)belongManagerInfo.get(0)[0];
				accountName=(String)belongManagerInfo.get(0)[1];
				userName=(String)belongManagerInfo.get(0)[2];
			}
			map.put("orgId", orgId);
			map.put("accountName", accountName == null ? "" : accountName.trim());
			map.put("userName", userName);
			
			dataList.add(map);
			
		}catch (Exception e) {
			log.error("���ݿ����ʧ�ܣ�" + e.getMessage());
			log.error("������Ϣ��{}", e);
		}
		
		return dataList;
	}
	public List<Object> returnEntiryAsList(String custId,String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// �Ѳ�ѯ���������뵽map��������
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

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
		// �Ѳ�ѯ���������뵽map��������
		paramMap.put("custId", custId);

		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

	}
	
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
					String msg = String.format("{},�յ���Χϵͳ[%s]��Ӧ���ı�Ҫ�ڵ�Ϊ��[��Ҫ�ڵ㣺%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("wmsCd"), txStatCodeXpath,
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
				String txStatDetail = "��Χϵͳ[" + BusinessCfg.getString("wmsCd") + "]��Ӧ:txStatDesc:{" + txStatDesc + "},txStatDetail:{" + txStatDetailNode.getText() + "}";

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
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("wmsCd"), e);
				return false;
			} catch (Exception e) {
				log.error("�յ���Χϵͳ[{}]��������һ������ʱ����,������Ϣ:\n{}", BusinessCfg.getString("wmsCd"), e);
				return false;
			}
		} else {
			// TODO
			log.info("ͬ����Ӧ����Ϊ��");
			return false;
		}	
		//return false;
	}
	/**
	 * ��ѯ֤��1
	 * @param custId
	 * @return
	 */
	public List<Object> getIdent1Info(String custId) {
		//StringBuffer sb = new StringBuffer("select i.ident_type,i.ident_no from ACRM_F_CI_CUSTOMER i where i.CUST_ID =:custId ");
		StringBuffer sb = new StringBuffer("select t.ident_type, t.ident_no, t1.ident_expired_date from M_CI_CUSTOMER t left join m_ci_identifier t1 on t1.cust_id = t.cust_id and t1.ident_type = t.ident_type where t.CUST_ID =:custId ");
		Map<String, String> paramMap = new HashMap<String, String>();
		// �Ѳ�ѯ����������map��������
		paramMap.put("custId", custId);
		return baseDAO.findByNativeSQLWithNameParam(sb.toString(), paramMap);
	}
	/**
	 * ��ѯ֤��2
	 * @param custId
	 * @return
	 */
	public List<Object> getIdent2Info(String custId) {
		/*StringBuffer sb = new StringBuffer(
		        "select R.ident_type,R.ident_no " +
		        "FROM (" +
		        	"select i.ident_type,i.ident_no, " +
		        	"CASE WHEN c.ident_type='X2' and i.ident_type='6' then 1 " +
		        	"WHEN c.ident_type='X1' and i.ident_type='5' then 1 else 0 end as flag  " +
		        	"from ACRM_F_CI_CUST_IDENTIFIER i " +
		        	"inner join ACRM_F_CI_CUSTOMER c on c.cust_id = i.cust_id   " +
		        	"where i.cust_id=:custId " + 
		        " and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) order by I.IDENT_TYPE DESC" +
		        ") R  ORDER BY R.FLAG DESC");
			System.out.println(sb.toString());*/
		StringBuffer sb = new StringBuffer(
		        "select R.ident_type,R.ident_no,R.ident_expired_date " +
		        "FROM (" +
		        	"select i.ident_type,i.ident_no,i.ident_expired_date, " +
		        	"CASE WHEN c.ident_type='X2' and i.ident_type='6' then 1 " +
		        	"WHEN c.ident_type='X1' and i.ident_type='5' then 1 else 0 end as flag  " +
		        	"from M_CI_IDENTIFIER i " +
		        	"inner join M_CI_CUSTOMER c on c.cust_id = i.cust_id   " +
		        	"where i.cust_id=:custId " + 
		        " and (i.ident_type <> c.ident_type or i.ident_no <> c.ident_no) order by I.IDENT_TYPE DESC" +
		        ") R  ORDER BY R.FLAG DESC");
			System.out.println(sb.toString());
		Map<String, String> paramMap = new HashMap<String, String>();
		// �Ѳ�ѯ����������map��������
		paramMap.put("custId", custId);
		return baseDAO.findByNativeSQLWithNameParam(sb.toString(), paramMap);
	}
}
