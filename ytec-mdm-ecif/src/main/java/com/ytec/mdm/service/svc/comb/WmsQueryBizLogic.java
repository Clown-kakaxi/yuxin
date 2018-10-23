package com.ytec.mdm.service.svc.comb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.MCiAddress;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiOrg;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;


/**
 * @���������Ƹ�����ϵͳ��ѯҵ�����߼�
 * @author  SJB
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WmsQueryBizLogic implements IEcifBizLogic{
	protected static Logger log = LoggerFactory.getLogger(WmsQueryBizLogic.class);
	private JPABaseDAO baseDAO;
	
	@SuppressWarnings("unused")
	@Override
	public void process(EcifData ecifData) throws Exception {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			Element body=ecifData.getBodyNode();//��ȡ�ڵ�
			String txCode = body.element("txCode").getTextTrim();// ��ȡ���ױ��
			String txName = body.element("txName").getTextTrim();// ��ȡ��������
			//String authType = body.element("authType").getTextTrim();// ��ȡȨ�޿�������
			//String authCode = body.element("authCode").getTextTrim();// ��ȡȨ�޿��ƴ���
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			Object obj = null;
			String identType=null;
			String identNo=null;
			String coreNo=null;
			String custId=null;
			String custType=null;
			MCiCustomer customer = new MCiCustomer();
			MCiPerson person=new MCiPerson();
			MCiOrg org=new MCiOrg();
			MCiContmeth contmeth=new MCiContmeth();
			MCiAddress address=new MCiAddress();
			MCiIdentifier ident=new MCiIdentifier();
			MCiBelongManager belongManager=new MCiBelongManager();
			
			if(txCode != null && !"".equals(txCode)){
				if("wmsQueryCustInfo".equals(txCode)){
					identType = body.element("identType").getTextTrim();//��ȡ֤������
					identNo = body.element("identNo").getTextTrim();//��ȡ֤������
					//String custId=null;
					}
				if("wmsQueryEcifCustInfo".equals(txCode)){
					coreNo = body.element("coreNo").getTextTrim();//��ȡ���Ŀͻ���
					}
					try{
						if("wmsQueryCustInfo".equals(txCode)){
							obj=queryIdentityEntiry(identType, identNo, "MCiIdentifier");//ͨ��֤�����ͺ�֤�������ѯ֤����Ϣ��
								if (obj != null) {
									ident = (MCiIdentifier) obj;
									custId=ident.getCustId();
									//identType=ident.getIdentType();
									//identNo=ident.getIdentNo();
								}else {
									String msg = String.format("��ѯ�ͻ�֤����Ϣ����ͨ��֤������(%s)��֤������(%s)û�в�ѯ���κ���Ϣ ", identType, identNo);
									log.error("{},{}", msg + "���ױ����ǣ�" + txCode);
									ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), "δ�鵽�κμ�¼");
									ecifData.setSuccess(true);
									return;
								}
							}
						if("wmsQueryEcifCustInfo".equals(txCode)){
							obj=queryCustomerEntiry(coreNo, "MCiCustomer");//ͨ�����Ŀͻ��Ų�ѯ�ͻ�����
								if (obj != null) {
									customer = (MCiCustomer) obj;
									custId=customer.getCustId();
									custType=customer.getCustType();
								}else {
									String msg = String.format("��ѯ�ͻ���Ϣ����ͨ�����Ŀͻ���(%s)û�в�ѯ���κ���Ϣ ", coreNo);
									log.error("{},{}", msg + "���ױ����ǣ�" + txCode);
									ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), "δ�鵽�κμ�¼");
									ecifData.setSuccess(true);
									return;
								} 
							//�Ƹ�����ʶ���֤����������
							if (custType != null && !"".equals(custType) && "2".equals(custType)) {//���˻�
								List<Object> identList=returnEntiryAsList(custId,"MCiIdentifier");//ͨ��custId��ѯ�ͻ�֤����Ϣ
								if(identList != null && identList.size() > 0){
									for(int i=0;i<identList.size();i++){
										MCiIdentifier identTmp = (MCiIdentifier) identList.get(i);
										if("2".equals(identTmp.getIdentType()) || "7".equals(identTmp.getIdentType()) || "0".equals(identTmp.getIdentType()) 
												|| "5".equals(identTmp.getIdentType()) || "6".equals(identTmp.getIdentType()) 
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
						}
						Object objCustomer=queryEntiry(custId,"MCiCustomer");//ͨ��custId��ѯ�ͻ�����
						if (objCustomer != null) {
							customer = (MCiCustomer) objCustomer;
						}else{
							String msg = String.format("��ѯ�ͻ�(%s)��Ϣʧ��", custId);
							log.error(msg);
							throw new Exception(msg);
						}
						String sql="select to_char(max(highest_card_level)) as CARD_LVL,max(cust_grade) as CUST_GRADE " +
								   "from OCRM_F_CI_CARD_TOTAL@FBCRM where cust_id = '"+custId+"' group by cust_id";
						List<Object[]> cardApplys=baseDAO.findByNativeSQLWithIndexParam(sql, null);
						String cardLvl=null;
						String custLevel=null;//CUST_GRADE
						if(cardApplys != null && cardApplys.size() > 0){
							for(Object[] objArray:cardApplys){
								cardLvl=(String) objArray[0];
								custLevel=(String) objArray[1];
							}
						}
						custId=customer.getCustId();
						coreNo=customer.getCoreNo();
						custType=customer.getCustType();
						//identType=customer.getIdentType();
						//identNo=customer.getIdentNo();
						String intEmp = "";
						String custSex = "";
						String custName=customer.getCustName();
						String shortName=customer.getShortName();
						String enName=customer.getEnName();
						String inoutFlag=customer.getInoutFlag();
						String vipFlag=customer.getVipFlag();//����û��ֵ
						String createBranchNo=customer.getCreateBranchNo();
						//custLevel=customer.getCustLevel();
						String pinyinName=null;
						String annualIncomeScope=null;
						String citizenship=null;
						String careerType=null;
						String highestSchooling=null;
						Date birthday=null;
						Object objPerson=queryEntiry(custId,"MCiPerson");//ͨ��custId��ѯ���˿ͻ���
						if (objPerson != null) {
							person = (MCiPerson) objPerson;
							pinyinName=person.getPinyinName();
							annualIncomeScope=person.getAnnualIncomeScope();
							citizenship=person.getCitizenship();
							careerType=person.getCareerType();
							highestSchooling=person.getHighestSchooling();
							birthday=person.getBirthday();
							intEmp = "S".equals(customer.getStaffin()) ? "Y" : "N";
							custSex = "1".equals(person.getGender()) ? "M" : "2".equals(person.getGender()) ? "F" : "";
						}
						
						String orgCustType=null;
						String orgBizCustType=null;
						String legalReprName=null;
						String legalReprIdentType=null;
						String legalReprIdentNo=null;
						String lncustp=null;
						Date registerDate=null;
						String businessScope=null;
						sql="select t.REGISTER_DATE from ACRM_F_CI_ORG_REGISTERINFO@FBCRM t where t.CUST_ID='"+custId+"'";//��ѯCRM���ݿ�
						List<Object> orgRegisterInfo=baseDAO.findByNativeSQLWithIndexParam(sql, null);
						if(orgRegisterInfo != null && orgRegisterInfo.size() > 0){
							for(int i=0;i<orgRegisterInfo.size();i++){
								if(orgRegisterInfo.get(0) != null){
									registerDate=df8.parse(orgRegisterInfo.get(0).toString());
								}
							}
						}
						Object objOrg=queryEntiry(custId,"MCiOrg");//ͨ��custId��ѯ�����ͻ���
						if (objOrg != null) {
							  org= (MCiOrg)objOrg;
							  orgCustType=org.getOrgCustType();
							  //orgCustType=org.getOrgForm();
							  orgBizCustType=org.getOrgBizCustType();
							  legalReprName=org.getLegalReprName();
							  legalReprIdentType=org.getLegalReprIdentType();
							  legalReprIdentNo=org.getLegalReprIdentNo();
							  businessScope=org.getInCllType();//��ҵ�����ҵ��ģ��
							  lncustp=org.getLncustp();
						}
						String custManagerNo=null;
						String orgId=null;
						String accountName=null;
						String userName=null;
						Object objManager=queryEntiry(custId,"MCiBelongManager");
						if(objManager != null){
							belongManager=(MCiBelongManager)objManager;
							custManagerNo=belongManager.getCustManagerNo() == null ? "" : belongManager.getCustManagerNo().trim();
						}else{
 							String msg = String.format("��ѯ�ͻ�(%s)�����ͻ�������Ϣʧ��", custId);
							log.error(msg);
							throw new Exception(msg);
						}
						sql="select t.ORG_ID,t.ACCOUNT_NAME,t.USER_NAME from ADMIN_AUTH_ACCOUNT@FBCRM t where t.ACCOUNT_NAME='"+custManagerNo+"'";//��ѯCRM���ݿ�
						List<Object[]> belongManagerInfo=baseDAO.findByNativeSQLWithIndexParam(sql, null);
						if(belongManagerInfo != null && belongManagerInfo.size() > 0){
							for(int i=0;i<belongManagerInfo.size();i++){
								//log.info(belongManagerInfo.get(0)[0]+"----------"+belongManagerInfo.get(0)[1]+"----------"+belongManagerInfo.get(0)[2]);
								orgId=(String)belongManagerInfo.get(0)[0];
								accountName=(String)belongManagerInfo.get(0)[1];
								userName=(String)belongManagerInfo.get(0)[2];
							}
						}
						List<Object> contmethList=returnEntiryAsList(custId,"MCiContmeth");
						List<Object> addressList=returnEntiryAsList(custId,"MCiAddress");

						Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
						Element customerbody = responseEle.addElement("customer");
						customerbody.addElement("custId").setText(custId == null ? "" : custId);
						customerbody.addElement("coreNo").setText(coreNo == null ? "" : coreNo);
						customerbody.addElement("custType").setText(custType == null ? "" : custType);
						/*if(identType.toString().equals("5") || identType.toString().equals("6")){
							customerbody.addElement("identType1").setText(identType == null ? "" : identType);
							customerbody.addElement("identNo1").setText(identNo == null ? "" : identNo);
						}else{
							customerbody.addElement("identType").setText(identType == null ? "" : identType);
							customerbody.addElement("identNo").setText(identNo == null ? "" : identNo);
						}*/
						
						List ident1List = getIdent1Info(custId);
						List ident2List = getIdent2Info(custId);
						String identMat = "";
						if(ident1List!=null&&ident1List.size()!=0){
							Object[] identInfo = (Object[]) ident1List.get(0);
							customerbody.addElement("identType").setText(identInfo[0] == null ? "" : identInfo[0].toString());
							customerbody.addElement("identNo").setText(identInfo[1] == null ? "" : identInfo[1].toString());
							identMat = (identInfo[2] == null || "".equals(identInfo[2])) ? "" : df8.format(df.parse(identInfo[2].toString()));
							//customerbody.addElement("identMat").setText(identInfo[2] == null || "".equals(identInfo[2]) ? "" : df.format(df8.parse(identInfo[2].toString())));
						}else{
							customerbody.addElement("identType").setText("");
							customerbody.addElement("identNo").setText("");
							//customerbody.addElement("identMat").setText("");
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
//					customerbody.addElement("identType").setText(identType == null ? "" : identType);
//					customerbody.addElement("identNo").setText(identNo == null ? "" : identNo);
						customerbody.addElement("custName").setText(custName == null ? "" : custName);
						customerbody.addElement("shortName").setText(shortName == null ? "" : shortName);
						customerbody.addElement("enName").setText(enName == null ? "" : enName);
						customerbody.addElement("inoutFlag").setText(inoutFlag == null ? "" : inoutFlag);
						customerbody.addElement("vipFlag").setText(vipFlag == null ? "" : vipFlag);
						customerbody.addElement("createBranchNo").setText(createBranchNo == null ? "" : createBranchNo);
						customerbody.addElement("custLevel").setText(custLevel == null ? "" : custLevel);
						customerbody.addElement("cardLvl").setText(cardLvl == null ? "" : cardLvl);
						//add by liuming 20170809
						//identType1:̨��֤��۰�ͨ��֤֤������(֤�����ͳ���20,ע��ֻ����identTypeΪ��X2=̨�����֤����X1=�۰����֤��ʱ�����ֶ���ֵ)
						//identNo1:̨��֤��۰�ͨ��֤����(֤�����볤��32,ע��identType1��ֵ������ֶα�����ֵ)
						//5=�۰ľ��������ڵ�ͨ��֤,6=̨��ͬ�������ڵ�ͨ��֤
						/*if("wmsQueryCustInfo".equals(txCode)){
							List<Object> identList=returnEntiryAsListNew(custId,"MCiIdentifier");//ͨ��custId��ѯ�ͻ�֤����Ϣ
		                    if(identList != null 
		                    		&& identList.size()>0 
		                    		&& identType != null 
		                    		&& (identType.toString().equals("X1") || identType.toString().equals("X2"))){
								customerbody.addElement("identType1").setText(((MCiIdentifier)identList.get(0)).getIdentType());
								customerbody.addElement("identNo1").setText(((MCiIdentifier)identList.get(0)).getIdentNo());	
							}
		                    else{
								customerbody.addElement("identType1").setText("");
								customerbody.addElement("identNo1").setText("");
							}
						}
						if("wmsQueryCustInfo".equals(txCode)){
							List<Object> identList1=returnEntiryAsListNew2(custId,"MCiIdentifier");//ͨ��custId��ѯ�ͻ�֤����Ϣ
							 if(identList1 != null 
			                    		&& identList1.size()>0 
			                    		&& identType != null 
			                    		&& (identType.toString().equals("5") || identType.toString().equals("6"))){
									customerbody.addElement("identType").setText(((MCiIdentifier)identList1.get(0)).getIdentType());
									customerbody.addElement("identNo").setText(((MCiIdentifier)identList1.get(0)).getIdentNo());
							 }
						}*/
						//add end
						Element personbody = responseEle.addElement("person");
						personbody.addElement("pinyinName").setText(pinyinName == null ? "" : pinyinName);
						personbody.addElement("annualIncomeScope").setText(annualIncomeScope == null ? "" : annualIncomeScope);
						personbody.addElement("citizenship").setText(citizenship == null ? "" : citizenship);
						personbody.addElement("careerType").setText(careerType == null ? "" : careerType);
						personbody.addElement("highestSchooling").setText(highestSchooling == null ? "" : highestSchooling);
						personbody.addElement("birthday").setText(birthday == null ? "" : df8.format(birthday));
						personbody.addElement("intEmp").setText(intEmp == null ? "" : intEmp);
						personbody.addElement("custSex").setText(custSex == null ? "" : custSex);
						
						Element orgbody = responseEle.addElement("org");
						orgbody.addElement("orgCustType").setText(orgCustType == null ? "" : orgCustType);
						orgbody.addElement("orgBizCustType").setText(orgBizCustType == null ? "" : orgBizCustType);
						orgbody.addElement("legalReprName").setText(legalReprName == null ? "" : legalReprName);
						orgbody.addElement("legalReprIdentType").setText(legalReprIdentType == null ? "" : legalReprIdentType);
						orgbody.addElement("legalReprIdentNo").setText(legalReprIdentNo == null ? "" : legalReprIdentNo);
						orgbody.addElement("registerDate").setText(registerDate == null ? "" : df8.format(registerDate));
						orgbody.addElement("businessScope").setText(businessScope == null ? "" : businessScope);
						orgbody.addElement("lncustp").setText(lncustp == null ? "" : lncustp);
						//orgbody.addElement("intEmp").setText("");
						//orgbody.addElement("custSex").setText("");
						
						if(contmethList != null && contmethList.size()>0){
							for (int i=0;i<contmethList.size();i++) {
								Element contmethbody = responseEle.addElement("contmeth");
								contmeth=(MCiContmeth)contmethList.get(i);
								contmethbody.addElement("contmethId").setText(contmeth.getContmethId() == null ? "" : contmeth.getContmethId());
								contmethbody.addElement("contmethType").setText(contmeth.getContmethType() == null ? "" : contmeth.getContmethType());
								contmethbody.addElement("contmethInfo").setText(contmeth.getContmethInfo() == null ? "" : contmeth.getContmethInfo());
							}
						}else{
							String msg = String.format("����:û�в�ѯ���ͻ�(%s)�κ���ϵ��Ϣ", custId);
							log.warn(msg);
							//throw new Exception(msg);
						}
						if(addressList != null && addressList.size()>0){
							for (int i=0;i<addressList.size();i++) {
								Element addressbody = responseEle.addElement("address");
								address=(MCiAddress)addressList.get(i);
								addressbody.addElement("addrId").setText(address.getAddrId() == null ? "" : address.getAddrId());
								addressbody.addElement("addrType").setText(address.getAddrType() == null ? "" : address.getAddrType());
								addressbody.addElement("addr").setText(address.getAddr() == null ? "" : address.getAddr());
								addressbody.addElement("zipcode").setText(address.getZipcode() == null ? "" : address.getZipcode());
								addressbody.addElement("countryOrRegion").setText(address.getCountryOrRegion() == null ? "" : address.getCountryOrRegion());
							}
						}else{
							String msg = String.format("����:û�в�ѯ���ͻ�(%s)�κε�ַ��Ϣ", custId);
							log.warn(msg);
							//throw new Exception(msg);
						}
						Element managerbody = responseEle.addElement("belongManager");
						managerbody.addElement("custManagerNo").setText(custManagerNo == null ? "" : custManagerNo);
						managerbody.addElement("orgId").setText(orgId == null ? "" : orgId);
						managerbody.addElement("accountName").setText(accountName == null ? "" : accountName.trim());
						managerbody.addElement("userName").setText(userName == null ? "" : userName);
						
						ecifData.setRepNode(responseEle);//!!!
					}catch (Exception e) {
						String msg;
						if (e instanceof ParseException) {
							msg = String
									.format("����/ʱ��(%s)��ʽ�����Ϲ淶,ת������",
											e.getLocalizedMessage()
													.substring(
															e.getLocalizedMessage()
																	.indexOf('"'))
													.replace("\"", ""));
							ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
							log.error("{},{}", msg + "���ױ����ǣ�" + txCode, e);
						} else if (e instanceof NumberFormatException) {
							msg = String
									.format("��ֵ(%s)��ʽ�����Ϲ淶,ת������",
											e.getLocalizedMessage()
													.substring(
															e.getLocalizedMessage()
																	.indexOf('"'))
													.replace("\"", ""));
							ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
							log.error("{},{}", msg + "���ױ����ǣ�" + txCode, e);
						} else {
							msg = "��ѯ����ʧ��";
							log.error("{},{}", msg + "���ױ����ǣ�" + txCode, e);
							ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
						}
						ecifData.setSuccess(false);
						return;
					}
							
				}
				
	}
	
	public Object queryIdentityEntiry(String identType, String identNo,String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.identType =:identType");
		jql.append(" AND a.identNo =:identNo");
		// �Ѳ�ѯ����������map��������
		paramMap.put("identType", identType);
		paramMap.put("identNo", identNo);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

		}
	public Object queryCustomerEntiry(String coreNo,String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.coreNo =:coreNo");
		// �Ѳ�ѯ����������map��������
		paramMap.put("coreNo", coreNo);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

		}
	public Object queryEntiry(String custId,String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// �Ѳ�ѯ����������map��������
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

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
		// �Ѳ�ѯ����������map��������
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

		return null;
	}
	//add by liuming 20170809
	/*public List<Object> returnEntiryAsListNew(String custId,String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		jql.append(" AND (a.identType = '6' or a.identType = '5') and a.identNo is not null and ltrim(rtrim(a.identNo)) is not null");
		// �Ѳ�ѯ����������map��������
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

		return null;
	}
	
	public List<Object> returnEntiryAsListNew2(String custId,String tableName) {
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		jql.append(" AND (a.identType = 'X1' or a.identType = 'X2') and a.identNo is not null and ltrim(rtrim(a.identNo)) is not null");
		// �Ѳ�ѯ����������map��������
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

		return null;
	}*/
	/**
	 * ��ѯ֤��1
	 * @param custId
	 * @return
	 */
	public List<Object> getIdent1Info(String custId) {
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
