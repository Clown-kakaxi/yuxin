package com.yuchengtech.emp.ecif.customer.cusrelo.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**客户关系查询Vo
 * <pre>
 * Title:
 * Description:
 * </pre>
 * 
 * @author wuhp wuhp@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CusRelationLookVO implements Serializable{
	

	private static final long serialVersionUID = 1L;
	

	//客戶关系标识
	private String custRelId;
	//客户关系审批Id
	private String custrelApprovalId;
	//源客户号
	private String srcCustNo;
	//源客户标识
	private String srcId;
	//客户名称
	private String srcName;
	//目标客户号
	private String destCustNo;
	//目标客标识
	private String destId;
	//关系客户名称
	private String destName;
	//客户关系类型
	private String custRelType;
	//关系开始日期
	private Date custRelStart;
	//关系结束日期
	private Date custRelEnd;
	//客户间关系状态
	private String custRelStat;
	//最后更新系统
	private String lastUpdateSys;
    //审批状态
	private String approvalFlag;
	//客户间关系描述
	private String custRelDesc;
	//最后更新时间
	private Timestamp lastUpdateTm;
	//提交人
	private String operator;
	//提交时间
	private Timestamp operTime;
	//操作状态
	private String operStat;
	//审批人
	private String approvalOperator;
	//审批时间
	private Timestamp approvalTime;
	//审批状态
	private String approvalStat;
	//审批意见
	private String approvalNote;
	
	
	public String getCustRelDesc() {
		return custRelDesc;
	}
	public void setCustRelDesc(String custRelDesc) {
		this.custRelDesc = custRelDesc;
	}
	public String getSrcCustNo() {
		return srcCustNo;
	}
	public void setSrcCustNo(String srcCustNo) {
		this.srcCustNo = srcCustNo;
	}
	public String getDestCustNo() {
		return destCustNo;
	}
	public void setDestCustNo(String destCustNo) {
		this.destCustNo = destCustNo;
	}
	
	public Date getCustRelStart() {
		return custRelStart;
	}
	public void setCustRelStart(Date custRelStart) {
		this.custRelStart = custRelStart;
	}
	public Date getCustRelEnd() {
		return custRelEnd;
	}
	public void setCustRelEnd(Date custRelEnd) {
		this.custRelEnd = custRelEnd;
	}
	public String getApprovalFlag() {
		return approvalFlag;
	}
	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}
	public String getCustRelStat() {
		return custRelStat;
	}
	public void setCustRelStat(String custRelStat) {
		this.custRelStat = custRelStat;
	}
	public String getCustRelId() {
		return custRelId;
	}
	public void setCustRelId(String custRelId) {
		this.custRelId = custRelId;
	}
	public String getSrcId() {
		return srcId;
	}
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}
	public String getDestId() {
		return destId;
	}
	public void setDestId(String destId) {
		this.destId = destId;
	}
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getCustRelType() {
		return custRelType;
	}
	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}
	public String getLastUpdateSys() {
		return lastUpdateSys;
	}
	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}
	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}
	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getOperTime() {
		return operTime;
	}
	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}
	public String getOperStat() {
		return operStat;
	}
	public void setOperStat(String operStat) {
		this.operStat = operStat;
	}
	public String getApprovalOperator() {
		return approvalOperator;
	}
	public void setApprovalOperator(String approvalOperator) {
		this.approvalOperator = approvalOperator;
	}
	public String getApprovalStat() {
		return approvalStat;
	}
	public void setApprovalStat(String approvalStat) {
		this.approvalStat = approvalStat;
	}
	public String getApprovalNote() {
		return approvalNote;
	}
	public void setApprovalNote(String approvalNote) {
		this.approvalNote = approvalNote;
	}
	public String getCustrelApprovalId() {
		return custrelApprovalId;
	}
	public void setCustrelApprovalId(String custrelApprovalId) {
		this.custrelApprovalId = custrelApprovalId;
	}
	public Timestamp getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	
	
}