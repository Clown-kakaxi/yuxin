/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.fubonecif.service.svc.comb
 * @�ļ�����AddByGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:03:24
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.print.DocFlavor.SERVICE_FORMATTED;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
//import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
//import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
//import com.ytec.mdm.service.component.general.CustStatusMgr;
//import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
//import com.ytec.fubonecif.domain.MCiNametitle;
//import com.ytec.fubonecif.domain.MCiOrgIdentifier;
//import com.ytec.fubonecif.domain.MCiPerIdentifier;
//import com.ytec.fubonecif.domain.MCiIdentifier;
//import com.ytec.fubonecif.domain.MCiAccountInfo;
//import com.ytec.fubonecif.domain.MCiBankService;
import com.ytec.fubonecif.domain.OWzAccountInfo;
import com.ytec.fubonecif.domain.OWzCustomerInfo;
import com.ytec.fubonecif.domain.OWzServiceInfo;

import org.springframework.transaction.annotation.Transactional;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AddByGeneral
 * @��������ͨ�ÿͻ���Ϣ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:03:24
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:03:24
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AddInfoByGeneral implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(AddInfoByGeneral.class);
	private JPABaseDAO baseDAO;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = ecifData.getBodyNode();// ��ȡ�ڵ�
		String txCode = body.element("txCode").getTextTrim();// ��ȡ���ױ��
		String txName = body.element("txName").getTextTrim();// ��ȡ��������
		String authType = body.element("authType").getTextTrim();// ��ȡȨ�޿�������
		String authCode = body.element("authCode").getTextTrim();// ��ȡȨ�޿��ƴ���

		Element responseEle = DocumentHelper
				.createElement(MdmConstants.MSG_RESPONSE_BODY);
		try {

			//List<OWzCustomerInfo> customerInfo_list = new ArrayList<OWzCustomerInfo>();
			Vector<OWzCustomerInfo> customerInfo_list=new Vector<OWzCustomerInfo>();
			if (body.selectSingleNode("customerInfo") != null) {

				List<Element> customerInfo = body.elements("customerInfo");
				for (Element info : customerInfo) {

					String customerid = info.element("customerid")
							.getTextTrim();
					String customername = info.element("customername")
							.getTextTrim();
					String englishname = info.element("englishname")
							.getTextTrim();
					String sex = info.element("sex").getTextTrim();
					String birthday = info.element("birthday").getTextTrim();
					String country = info.element("country").getTextTrim();
					String birthplace = info.element("birthplace")
							.getTextTrim();
					String certtype = info.element("certtype").getTextTrim();
					String certid = info.element("certid").getTextTrim();
					String certdate = info.element("certdate").getTextTrim();
					String lineno = info.element("lineno").getTextTrim();
					String faxserviceno = info.element("faxserviceno")
							.getTextTrim();
					String weixin = info.element("weixin").getTextTrim();
					String channel = info.element("channel").getTextTrim();
					String elecbill = info.element("elecbill").getTextTrim();
					String attribute5 = info.element("attribute5")
							.getTextTrim();
					String attribute4 = info.element("attribute4")
							.getTextTrim();
					String attribute3 = info.element("attribute3")
							.getTextTrim();
					String usdtl = info.element("usdtl").getTextTrim();
					String usaflag = info.element("usaflag").getTextTrim();
					String relflag = info.element("relflag").getTextTrim();
					String email = info.element("email").getTextTrim();
					String position = info.element("position").getTextTrim();
					String companyname = info.element("companyname")
							.getTextTrim();
					String worktype = info.element("worktype").getTextTrim();
					String officephoneno = info.element("officephoneno")
							.getTextTrim();
					String officephonecity = info.element("officephonecity")
							.getTextTrim();
					String officecouncode = info.element("officecouncode")
							.getTextTrim();
					String officephonecoun = info.element("officephonecoun")
							.getTextTrim();
					String officephonetype = info.element("officephonetype")
							.getTextTrim();
					String homephoneno = info.element("homephoneno")
							.getTextTrim();
					String homephonecity = info.element("homephonecity")
							.getTextTrim();
					String homecouncode = info.element("homecouncode")
							.getTextTrim();
					String homephonecoun = info.element("homephonecoun")
							.getTextTrim();
					String homephonetype = info.element("homephonetype")
							.getTextTrim();
					String mobilephoneno = info.element("mobilephoneno")
							.getTextTrim();
					String mobilecouncode = info.element("mobilecouncode")
							.getTextTrim();
					String mobilephonecoun = info.element("mobilephonecoun")
							.getTextTrim();
					String postzipcode = info.element("postzipcode")
							.getTextTrim();
					String postaddrdtl = info.element("postaddrdtl")
							.getTextTrim();
					String postaddrcoun = info.element("postaddrcoun")
							.getTextTrim();
					String zipcode = info.element("zipcode").getTextTrim();
					String addrhouse = info.element("addrhouse").getTextTrim();
					String addrdtl = info.element("addrdtl").getTextTrim();
					String addrcoun = info.element("addrcoun").getTextTrim();
					String twcertid = info.element("twcertid").getTextTrim();
					String elecemail = info.element("elecemail").getTextTrim();
					String faxservice = info.element("faxservice")
							.getTextTrim();
					String jointaccount = info.element("jointaccount")
							.getTextTrim();
					String certtype2 = info.element("certtype2").getTextTrim();
					String certid2 = info.element("certid2").getTextTrim();
					String certdate2 = info.element("certdate2").getTextTrim();
					String twcertid2 = info.element("twcertid2").getTextTrim();
					String ftzflag = info.element("ftzflag").getTextTrim();
					// ����
					OWzCustomerInfo custInfo = new OWzCustomerInfo();

					custInfo.setCustomerid(customerid);
					custInfo.setCustomername(customername);
					custInfo.setEnglishname(englishname);
					custInfo.setSex(sex);
					custInfo.setBirthday(birthday);
					custInfo.setCountry(country);
					custInfo.setBirthplace(birthplace);
					custInfo.setCerttype(certtype);
					custInfo.setCertid(certid);
					custInfo.setCertdate(certdate);
					custInfo.setLineno(lineno);
					custInfo.setFaxserviceno(faxserviceno);
					custInfo.setWeixin(weixin);
					custInfo.setChannel(channel);
					custInfo.setElecbill(elecbill);
					custInfo.setAttribute5(attribute5);
					custInfo.setAttribute4(attribute4);
					custInfo.setAttribute3(attribute3);
					custInfo.setUsdtl(usdtl);
					custInfo.setUsaflag(usaflag);
					custInfo.setRelflag(relflag);
					custInfo.setEmail(email);
					custInfo.setPosition(position);
					custInfo.setCompanyname(companyname);
					custInfo.setWorktype(worktype);
					custInfo.setOfficephoneno(officephoneno);
					custInfo.setOfficephonecity(officephonecity);
					custInfo.setOfficecouncode(officecouncode);
					custInfo.setOfficephonecoun(officephonecoun);
					custInfo.setOfficephonetype(officephonetype);
					// custInfo.setOfficephoneno(officephoneno);
					custInfo.setHomephonecity(homephonecity);
					custInfo.setHomephoneno(homephoneno);
					custInfo.setHomecouncode(homecouncode);
					custInfo.setHomephonecoun(homephonecoun);
					custInfo.setHomephonetype(homephonetype);
					custInfo.setMobilephoneno(mobilephoneno);
					custInfo.setMobilecouncode(mobilecouncode);
					custInfo.setMobilephonecoun(mobilephonecoun);
					custInfo.setPostzipcode(postzipcode);
					custInfo.setPostaddrdtl(postaddrdtl);
					custInfo.setPostaddrcoun(postaddrcoun);
					custInfo.setZipcode(zipcode);
					custInfo.setAddrhouse(addrhouse);
					custInfo.setAddrdtl(addrdtl);
					custInfo.setAddrcoun(addrcoun);
					custInfo.setTwcertid(twcertid);
					custInfo.setElecemail(elecemail);
					custInfo.setFaxservice(faxservice);
					custInfo.setJointaccount(jointaccount);
					custInfo.setCerttype2(certtype2);
					custInfo.setCertid2(certid2);
					custInfo.setCertdate2(certdate2);
					custInfo.setTwcertid2(twcertid2);
					custInfo.setFtzflag(ftzflag);
					// ��ȡ���ж���
					OWzCustomerInfo obj = (OWzCustomerInfo) bizGetObject(
							customerid, custInfo);
					if (obj != null) {
						log.info("�ͻ�����{}�Ѵ���", customerid);
						try {
							throw new Exception("�ͻ������Ѵ���");
						} catch (Exception e) {
							e.printStackTrace();

						}
					}
					customerInfo_list.add(custInfo);
				}
				// }
				OWzCustomerInfo custInfos = null;
				for (int i = 0; i < customerInfo_list.size(); i++) {

					custInfos = customerInfo_list.get(i);

					baseDAO.merge(custInfos);
				}
			}
			// }

			Vector<OWzAccountInfo> accountInfo_list=new Vector<OWzAccountInfo>();
			if (body.selectSingleNode("accountInfo") != null) {

				// }
				List<Element> accountInfo = body.elements("accountInfo");
				for (Element acInfo : accountInfo) {
					String serialno = acInfo.element("serialno").getTextTrim();
					String accountno = acInfo.element("accountno")
							.getTextTrim();
					String customerid = acInfo.element("customerid")
							.getTextTrim();

					OWzAccountInfo accInfo = new OWzAccountInfo();
					accInfo.setSerialno(serialno);
					accInfo.setAccountno(accountno);
					accInfo.setCustomerid(customerid);

					OWzAccountInfo aiObj = (OWzAccountInfo) bizGetObject(
							serialno, accInfo);
					if (aiObj != null) {
						log.info("�ͻ�����{}�Ѵ���", serialno);
						try {
							throw new Exception("�ͻ������Ѵ���");
						} catch (Exception e) {
							e.printStackTrace();

						}
					}

					accountInfo_list.add(accInfo);
				}
				// }
				OWzAccountInfo accInfos = null;
				for (int i = 0; i < accountInfo_list.size(); i++) {

					accInfos = accountInfo_list.get(i);

					baseDAO.merge(accInfos);
				}
			}
			// }
			Vector<OWzServiceInfo> serviceInfo_list=new Vector<OWzServiceInfo>();
			if (body.selectSingleNode("serviceInfo") != null) {

				// }
				List<Element> serviceInfo = body.elements("serviceInfo");
				for (Element serInfo : serviceInfo) {
					String serialno = serInfo.element("serialno").getTextTrim();
					String customerid = serInfo.element("customerid")
							.getTextTrim();
					String debitcardapply = serInfo.element("debitcardapply")
							.getTextTrim();
					String debitcardapply1 = serInfo.element("debitcardapply1")
							.getTextTrim();
					String debitcardapply2 = serInfo.element("debitcardapply2")
							.getTextTrim();
					String debitcardapply3 = serInfo.element("debitcardapply3")
							.getTextTrim();
					String debitcardapply4 = serInfo.element("debitcardapply4")
							.getTextTrim();
					String networkbank = serInfo.element("networkbank")
							.getTextTrim();
					String ukeyapply = serInfo.element("ukeyapply")
							.getTextTrim();
					String shortmsg = serInfo.element("shortmsg").getTextTrim();
					String telephonebank = serInfo.element("telephonebank")
							.getTextTrim();
					String mobilephonebank = serInfo.element("mobilephonebank")
							.getTextTrim();
					String wxbank = serInfo.element("wxbank").getTextTrim();
					String elecbill = serInfo.element("elecbill").getTextTrim();
					String elecbillmail = serInfo.element("elecbillmail")
							.getTextTrim();
					String faxservicemail = serInfo.element("faxservicemail")
							.getTextTrim();
					String faxservice = serInfo.element("faxservice")
							.getTextTrim();
					String accchangenot = serInfo.element("accchangenot")
							.getTextTrim();
					String debitcardapply22 = serInfo.element(
							"debitcardapply22").getTextTrim();
					String debitcardapply42 = serInfo.element(
							"debitcardapply42").getTextTrim();
					String elecbank = serInfo.element("elecbank").getTextTrim();
					String approvepass = serInfo.element("approvepass")
							.getTextTrim();
					String mobilebankflag = serInfo.element("mobilebankflag")
							.getTextTrim();
					String approvepassloan = serInfo.element("approvepassloan")
							.getTextTrim();
					String faxserviceno = serInfo.element("faxserviceno")
							.getTextTrim();

					OWzServiceInfo bsInfo = new OWzServiceInfo();
					bsInfo.setSerialno(serialno);
					bsInfo.setCustomerid(customerid);
					bsInfo.setDebitcardapply(debitcardapply);
					bsInfo.setDebitcardapply1(debitcardapply1);
					bsInfo.setDebitcardapply2(debitcardapply2);
					bsInfo.setDebitcardapply3(debitcardapply3);
					bsInfo.setDebitcardapply4(debitcardapply4);
					bsInfo.setNetworkbank(networkbank);
					bsInfo.setUkeyapply(ukeyapply);
					bsInfo.setShortmsg(shortmsg);
					bsInfo.setTelephonebank(telephonebank);
					bsInfo.setMobilephonebank(mobilephonebank);
					bsInfo.setWxbank(wxbank);
					bsInfo.setElecbill(elecbill);
					bsInfo.setElecbillmail(elecbillmail);
					bsInfo.setFaxservicemail(faxservicemail);
					bsInfo.setFaxservice(faxservice);
					bsInfo.setAccchangenot(accchangenot);
					bsInfo.setDebitcardapply22(debitcardapply22);
					bsInfo.setDebitcardapply42(debitcardapply42);
					bsInfo.setElecbank(elecbank);
					bsInfo.setApprovepass(approvepass);
					bsInfo.setMobilebankflag(mobilebankflag);
					bsInfo.setApprovepassloan(approvepassloan);
					bsInfo.setFaxserviceno(faxserviceno);

					OWzServiceInfo bsObj = (OWzServiceInfo) bizGetObject(
							serialno, bsInfo);
					if (bsObj != null) {
						log.info("�ͻ�����{}�Ѵ���", serialno);
						try {
							throw new Exception("�ͻ������Ѵ���");
						} catch (Exception e) {
							e.printStackTrace();

						}
					}

					serviceInfo_list.add(bsInfo);
				}
				// }
				OWzServiceInfo bsInfos = null;
				for (int i = 0; i < serviceInfo_list.size(); i++) {

					bsInfos = serviceInfo_list.get(i);

					baseDAO.merge(bsInfos);
				}
			}
			// }

			
			

			//2017-10-13 ����ԤԼ�ͻ���Ϣ����
			if (body.selectSingleNode("orderInfo") != null) {
				List<Element> e_orderInfo = body.elements("orderInfo");
				if(e_orderInfo != null && e_orderInfo.size() >= 1){
					for (Element info : e_orderInfo) {
						if(info.element("customerid") == null || "".equals(info.elementTextTrim("customerid"))){
							String logMsg = "ȱ��customerid��Ϣ���޷�ͬ��ԤԼ�ͻ�����";
							log.error(logMsg);
							ecifData.setSuccess(false);
							ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), logMsg);
							return;
						}
						String customerid = info.elementTextTrim("customerid");
						String jointaccount = info.elementTextTrim("jointaccount");
						String customername = info.elementTextTrim("customername");
						String certtype = info.elementTextTrim("certtype");
						String certid = info.elementTextTrim("certid");
						String twcertid = info.elementTextTrim("twcertid");
						String certtype2 = info.elementTextTrim("certtype2");
						String certid2 = info.elementTextTrim("certid2");
						String twcertid2 = info.elementTextTrim("twcertid2");
						String mobilephonecoun = info.elementTextTrim("mobilephonecoun");
						String mobilecouncode = info.elementTextTrim("mobilecouncode");
						String mobilephoneno = info.elementTextTrim("mobilephoneno");
						String orderDate = info.elementTextTrim("orderDate");
						String orderDept = info.elementTextTrim("orderDept");
						String orderTime = info.elementTextTrim("orderTime");
						String channelNo = info.elementTextTrim("channelNo");
						String sOrderSerialNo = info.elementTextTrim("sOrderSerialNo");
						//����������Ϣ
						OWzCustomerInfo custInfo = new OWzCustomerInfo();
						custInfo.setCustomerid(customerid);
						custInfo.setJointaccount(jointaccount);
						custInfo.setCustomername(customername);
						custInfo.setCerttype(certtype);
						custInfo.setCertid(certid);
						custInfo.setTwcertid(twcertid);
						custInfo.setCerttype2(certtype2);
						custInfo.setCertid2(certid2);
						custInfo.setTwcertid2(twcertid2);
						custInfo.setMobilephonecoun(mobilephonecoun);
						custInfo.setMobilecouncode(mobilecouncode);
						custInfo.setMobilephoneno(mobilephoneno);
						custInfo.setOrderdate(orderDate);
						custInfo.setInputorg(orderDept);
						custInfo.setPretime(orderTime);
						custInfo.setChannel(channelNo);
						custInfo.setSorderserialno(sOrderSerialNo);
						custInfo.setAppStatus("1");
						this.baseDAO.merge(custInfo);
					}
				}
			}
			
			if (StringUtils.isEmpty(txCode)) {
				String msg = "��Ϣ����������������ڵ���txCode������Ϊ��";
				log.error(msg);
				ecifData.setStatus(
						ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		baseDAO.flush();
		return;
	}

	private Object bizGetObject(String customerid, OWzCustomerInfo custInfo) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ��
		String simpleName = "OWzCustomerInfo";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();
		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.customerid =:customerid");
		paramMap.put("customerid", customerid);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {

			return result.get(0);

		}

		return null;
	}

	private Object bizGetObject(String serialno, OWzAccountInfo accInfo) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ��
		String simpleName = "OWzAccountInfo";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();
		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.serialno =:serialno");
		paramMap.put("serialno", serialno);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {

			return result.get(0);

		}

		return null;
	}

	private Object bizGetObject(String serialno, OWzServiceInfo bsInfo) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ��
		String simpleName = "OWzServiceInfo";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();
		// ��ѯ��
		jql.append("FROM " + tableName + " a");
		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.serialno =:serialno");
		paramMap.put("serialno", serialno);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) {

			return result.get(0);

		}

		return null;
	}

}
