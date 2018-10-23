/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����EcifData.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-13-11:31:30
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;

import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ytec.mdm.base.util.MdmConstants;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�EcifData
 * @���������ӿ�ͳһ�������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����9:49:11   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����9:49:11
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifData  extends TxSession{
	private Long txLogId;
	/**
	 * The primal doc.
	 * @��������:���Ķ���  
	 */
	private Document primalDoc;
	/**
	 * The request header.
	 * @��������:������ͷ
	 */
	private Element requestHeader ;
	/**
	 * The body node.
	 * @��������:��������
	 */
	private Element bodyNode ; 
	/**
	 * The rep header.
	 * @��������:��Ӧ����ͷ
	 */
	private Element repHeader ; 
	/**
	 * The rep node.
	 * @��������:��Ӧ������
	 */
	private Element repNode;
	/**
	 * ��֤��Ϣ*.
	 * @��������:Ȩ����Ȩ��  
	 */
	private String authCode; 
	/**
	 * The auth pwd.
	 * @��������:Ȩ����Ȩ����
	 */
	private String authPwd;
	/**
	 * The op check sum.
	 * @��������:�Ƿ���Ҫǩ��У��
	 */
	private boolean opCheckSum;
	/**
	 * The req check sum.
	 * @��������:����ǩ����Ϣ
	 */
	private String reqCheckSum; 
	/**
	 * The req check sum body.
	 * @��������:ǩ��У����
	 */
	private String reqCheckSumBody; 
	/**
	 * The rep check sum.
	 * @��������:��Ӧǩ����Ϣ
	 */
	private String repCheckSum;
	/**
	 * �ͻ����.
	 * @��������:�ͻ���ʶ /�ͻ���   
	 */
	private String custId;
	
	/**
	 * The ecif cust no.
	 * @��������:ECIF�ͻ���  
	 */
	private String ecifCustNo;
	/**
	 * The cust type.
	 * @��������:�ͻ����ʹ���
	 */
	private String custType;
	/**
	 * The cust status.
	 * @��������:�ͻ�״̬
	 */
	private String custStatus;
	/**
	 * ����ͬ�����*.
	 * @��������:����ͬ������
	 */
	private List dataSynchro; 
	/**
	 * �ͻ�ʶ��*.
	 * @��������:�ͻ�ʶ������
	 */
	private String custDiscRul; 
	/**
	 * ��ѯ����*.
	 * @��������:��ѯ����ģ��
	 */
	private QueryModel queryModelObj;
	/**
	 * д����*.
	 * @��������:д����ģ��
	 */
	private WriteModel writeModelObj;
	/**
	 * ����*.
	 * @��������:����
	 */
	private Map parameterMap;
	/**
	 * Gets the primal doc.
	 * @return the primal doc
	 */
	public Document getPrimalDoc() {
		return primalDoc;
	}
	/**
	 * Sets the primal doc.
	 * 
	 * @param primalDoc
	 *            the new primal doc
	 */
	public void setPrimalDoc(Document primalDoc) {
		this.primalDoc = primalDoc;
	}
	/**
	 * Gets the request header.
	 * 
	 * @return the request header
	 */
	public Element getRequestHeader() {
		return requestHeader;
	}
	/**
	 * Sets the request header.
	 * 
	 * @param requestHeader
	 *            the new request header
	 */
	public void setRequestHeader(Element requestHeader) {
		this.requestHeader = requestHeader;
	}
	/**
	 * Gets the body node.
	 * 
	 * @return the body node
	 */
	public Element getBodyNode() {
		return bodyNode;
	}
	/**
	 * Sets the body node.
	 * 
	 * @param bodyNode
	 *            the new body node
	 */
	public void setBodyNode(Element bodyNode) {
		this.bodyNode = bodyNode;
	}
	/**
	 * Gets the rep header.
	 * 
	 * @return the rep header
	 */
	public Element getRepHeader() {
		return repHeader;
	}
	/**
	 * Sets the rep header.
	 * 
	 * @param repHeader
	 *            the new rep header
	 */
	public void setRepHeader(Element repHeader) {
		this.repHeader = repHeader;
	}
	/**
	 * Gets the rep node.
	 * 
	 * @return the rep node
	 */
	public Element getRepNode() {
		return repNode;
	}
	/**
	 * Sets the rep node.
	 * 
	 * @param repNode
	 *            the new rep node
	 */
	public void setRepNode(Element repNode) {
		this.repNode = repNode;
	}
	/**
	 * Gets the auth code.
	 * 
	 * @return the auth code
	 */
	public String getAuthCode() {
		return authCode;
	}
	/**
	 * Sets the auth code.
	 * 
	 * @param authCode
	 *            the new auth code
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	/**
	 * Gets the auth pwd.
	 * 
	 * @return the auth pwd
	 */
	public String getAuthPwd() {
		return authPwd;
	}
	/**
	 * Sets the auth pwd.
	 * 
	 * @param authPwd
	 *            the new auth pwd
	 */
	public void setAuthPwd(String authPwd) {
		this.authPwd = authPwd;
	}
	/**
	 * Gets the req check sum.
	 * 
	 * @return the req check sum
	 */
	public String getReqCheckSum() {
		return reqCheckSum;
	}
	/**
	 * Sets the req check sum.
	 * 
	 * @param reqCheckSum
	 *            the new req check sum
	 */
	public void setReqCheckSum(String reqCheckSum) {
		this.reqCheckSum = reqCheckSum;
	}
	/**
	 * Gets the rep check sum.
	 * 
	 * @return the rep check sum
	 */
	public String getRepCheckSum() {
		return repCheckSum;
	}
	/**
	 * Sets the rep check sum.
	 * 
	 * @param repCheckSum
	 *            the new rep check sum
	 */
	public void setRepCheckSum(String repCheckSum) {
		this.repCheckSum = repCheckSum;
	}
	/**
	 * Gets the cust id.
	 * 
	 * @return the cust id
	 */
	public String getCustId() {
		return custId;
	}
	
	public Object getCustId(String type) {
//		System.out.println("ecifData.getCustId(MdmConstants.CUSTID_TYPE):"+type);
		if(type==null){
			return null;
		}
		if("L".equals(type)){
			return Long.valueOf(custId);
		}else{
			return custId;
		}
	}
	/**
	 * Sets the cust id.
	 * 
	 * @param custId
	 *            the new cust id
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * Gets the ecif cust no.
	 * 
	 * @return the ecif cust no
	 */
	public String getEcifCustNo() {
		return ecifCustNo;
	}
	/**
	 * Sets the ecif cust no.
	 * 
	 * @param ecifCustNo
	 *            the new ecif cust no
	 */
	public void setEcifCustNo(String ecifCustNo) {
		this.ecifCustNo = ecifCustNo;
	}
	/**
	 * Gets the cust type.
	 * 
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}
	/**
	 * Sets the cust type.
	 * @param custType
	 *            the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
	 * Gets the cust status.
	 * @return the cust status
	 */
	public String getCustStatus() {
		return custStatus;
	}
	/**
	 * Sets the cust status.
	 * @param custStatus
	 *            the new cust status
	 */
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	/**
	 * Gets the data synchro.
	 * @return the data synchro
	 */
	public List getDataSynchro() {
		return dataSynchro;
	}
	/**
	 * Sets the data synchro.
	 * @param dataSynchro
	 *            the new data synchro
	 */
	public void setDataSynchro(List dataSynchro) {
		this.dataSynchro = dataSynchro;
	}
	/**
	 * Gets the cust disc rul.
	 * 
	 * @return the cust disc rul
	 */
	public String getCustDiscRul() {
		return custDiscRul;
	}
	/**
	 * Sets the cust disc rul.
	 * @param custDiscRul
	 *            the new cust disc rul
	 */
	public void setCustDiscRul(String custDiscRul) {
		this.custDiscRul = custDiscRul;
	}
	/**
	 * Gets the query model obj.
	 * @return the query model obj
	 */
	public QueryModel getQueryModelObj() {
		return queryModelObj;
	}
	/**
	 * Sets the query model obj.
	 * @param queryModelObj
	 *            the new query model obj
	 */
	public void setQueryModelObj(QueryModel queryModelObj) {
		this.queryModelObj = queryModelObj;
	}
	/**
	 * Gets the write model obj.
	 * @return the write model obj
	 */
	public WriteModel getWriteModelObj() {
		return writeModelObj;
	}
	/**
	 * Sets the write model obj.
	 * 
	 * @param writeModelObj
	 *            the new write model obj
	 */
	public void setWriteModelObj(WriteModel writeModelObj) {
		this.writeModelObj = writeModelObj;
	}
	/**
	 * Gets the parameter map.
	 * 
	 * @return the parameter map
	 */
	public Map getParameterMap() {
		return parameterMap;
	}
	/**
	 * Sets the parameter map.
	 * 
	 * @param parameterMap
	 *            the new parameter map
	 */
	public void setParameterMap(Map parameterMap) {
		this.parameterMap = parameterMap;
	}
	/**
	 * Gets the value from parameter map.
	 * 
	 * @param key
	 *            the key
	 * @return the value from parameter map
	 */
	public String getValueFromParameterMap(String key){
		return (String)parameterMap.get(key);
	}
	/**
	 * Checks if is op check sum.
	 * 
	 * @return true, if checks if is op check sum
	 * @��������:boolean isOpCheckSum()
	 * @��������:
	 * @�����뷵��˵��: boolean isOpCheckSum()
	 * @�㷨����:
	 */
	public boolean isOpCheckSum() {
		return opCheckSum;
	}
	/**
	 * Sets the op check sum.
	 * 
	 * @param opCheckSum
	 *            the new op check sum
	 */
	public void setOpCheckSum(boolean opCheckSum) {
		this.opCheckSum = opCheckSum;
	}
	/**
	 * Gets the req check sum body.
	 * 
	 * @return the req check sum body
	 */
	public String getReqCheckSumBody() {
		return reqCheckSumBody;
	}
	/**
	 * Sets the req check sum body.
	 * @param reqCheckSumBody
	 *            the new req check sum body
	 */
	public void setReqCheckSumBody(String reqCheckSumBody) {
		this.reqCheckSumBody = reqCheckSumBody;
	}
	/**
	 * Gets the tx log id.
	 * @return the tx log id
	 */
	public Long getTxLogId() {
		return txLogId;
	}
	/**
	 * Sets the tx log id.
	 * @param txLogId
	 *            the new tx log id
	 */
	public void setTxLogId(Long txLogId) {
		this.txLogId = txLogId;
	}
}