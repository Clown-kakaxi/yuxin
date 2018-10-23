/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：EcifData.java
 * @版本信息：1.0.0
 * @日期：2014-2-13-11:31:30
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;

import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ytec.mdm.base.util.MdmConstants;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：EcifData
 * @类描述：接口统一传入参数
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午9:49:11   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午9:49:11
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class EcifData  extends TxSession{
	private Long txLogId;
	/**
	 * The primal doc.
	 * @属性描述:报文对象  
	 */
	private Document primalDoc;
	/**
	 * The request header.
	 * @属性描述:请求报文头
	 */
	private Element requestHeader ;
	/**
	 * The body node.
	 * @属性描述:请求报文体
	 */
	private Element bodyNode ; 
	/**
	 * The rep header.
	 * @属性描述:响应报文头
	 */
	private Element repHeader ; 
	/**
	 * The rep node.
	 * @属性描述:响应报文体
	 */
	private Element repNode;
	/**
	 * 验证信息*.
	 * @属性描述:权限授权码  
	 */
	private String authCode; 
	/**
	 * The auth pwd.
	 * @属性描述:权限授权密码
	 */
	private String authPwd;
	/**
	 * The op check sum.
	 * @属性描述:是否需要签名校验
	 */
	private boolean opCheckSum;
	/**
	 * The req check sum.
	 * @属性描述:请求签名信息
	 */
	private String reqCheckSum; 
	/**
	 * The req check sum body.
	 * @属性描述:签名校验体
	 */
	private String reqCheckSumBody; 
	/**
	 * The rep check sum.
	 * @属性描述:响应签名信息
	 */
	private String repCheckSum;
	/**
	 * 客户相关.
	 * @属性描述:客户标识 /客户号   
	 */
	private String custId;
	
	/**
	 * The ecif cust no.
	 * @属性描述:ECIF客户号  
	 */
	private String ecifCustNo;
	/**
	 * The cust type.
	 * @属性描述:客户类型代码
	 */
	private String custType;
	/**
	 * The cust status.
	 * @属性描述:客户状态
	 */
	private String custStatus;
	/**
	 * 数据同步相关*.
	 * @属性描述:数据同步对象
	 */
	private List dataSynchro; 
	/**
	 * 客户识别*.
	 * @属性描述:客户识别类型
	 */
	private String custDiscRul; 
	/**
	 * 查询交易*.
	 * @属性描述:查询交易模型
	 */
	private QueryModel queryModelObj;
	/**
	 * 写交易*.
	 * @属性描述:写交易模型
	 */
	private WriteModel writeModelObj;
	/**
	 * 参数*.
	 * @属性描述:参数
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
	 * @函数名称:boolean isOpCheckSum()
	 * @函数描述:
	 * @参数与返回说明: boolean isOpCheckSum()
	 * @算法描述:
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