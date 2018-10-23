package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.util.List;

import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerKeyflag;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.model.AcrmFCiAddress;
import com.yuchengtech.bcrm.customer.model.AcrmFCiCustIdentifier;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamily;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongOrg;

/**
 * @项目名称：CRM一键开户
 * @类名称：EcifAccountMsg
 * @类描述：ECIF开户报文信息字段
 * @功能描述:ECIF开户报文信息字段
 * @创建人：wx
 * @创建时间：2017年9月8日下午2:01:25
 * @修改人：wx
 * @修改时间：2017年9月8日下午2:01:25
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class EcifAccountMsg {

	private boolean isUpdate;//是否是更新数据
	private String custId;//ECIF id
	private String seqNO;//交易流水号
	private AcrmFCiCustomer customerInfo;//客户信息
	private List<AcrmFCiCustIdentifier> custIdentyList;//证件信息
	private AcrmFCiPerson personInfo;//个人信息
	private List<AcrmFCiAddress>  addressInfoList;//地址信息
	private AcrmFCiPerFamily personFamilyInfo;//房产属性
	private List<AcrmFCiContmeth> contmethInfoList;//联系方式信息
	private OcrmFCiCustjoinInfo custJoinInfo;//联名户信息
	private OcrmFCiOpenInfo custBankInfo;//银行信息
	private List<OcrmFCiAccountInfo> custAccountInfoList;//账户信息
	private OcrmMCiTaxMain ocrmMCiTaxMain;//个人声明
	private OcrmMCiTaxMain ocrmMCiTaxMain2;//从户个人声明
	private List<OcrmMCiTaxSub> ocrmMCiTaxSubList;//纳税人信息
	private List<OcrmMCiTaxSub> ocrmMCiTaxSubList2;//从户纳税人信息
	private OcrmMCiRelPerson custRelPerson;//关联人信息
	//private AcrmOBelongManager belongManager;//归属客户经理信息
	private OcrmFCiBelongCustmgr belongManager;//归属客户经理信息
	private OcrmFCiBelongOrg belongOrg;//归属客户经理机构
//	private AcrmFCiCrossindex crossIndexInfo;//交叉索引表信息
	private AcrmFCiPerKeyflag acrmFCiPerKeyflag;//在我行有无关联人
	
	
	
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSeqNO() {
		return seqNO;
	}
	public void setSeqNO(String seqNO) {
		this.seqNO = seqNO;
	}
	public AcrmFCiCustomer getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(AcrmFCiCustomer customerInfo) {
		this.customerInfo = customerInfo;
	}
	public AcrmFCiPerson getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(AcrmFCiPerson personInfo) {
		this.personInfo = personInfo;
	}
	public List<AcrmFCiAddress> getAddressInfoList() {
		return addressInfoList;
	}
	public void setAddressInfoList(List<AcrmFCiAddress> addressInfoList) {
		this.addressInfoList = addressInfoList;
	}
	public List<AcrmFCiContmeth> getContmethInfoList() {
		return contmethInfoList;
	}
	public void setContmethInfoList(List<AcrmFCiContmeth> contmethInfoList) {
		this.contmethInfoList = contmethInfoList;
	}
	public OcrmFCiCustjoinInfo getCustJoinInfo() {
		return custJoinInfo;
	}
	public void setCustJoinInfo(OcrmFCiCustjoinInfo custJoinInfo) {
		this.custJoinInfo = custJoinInfo;
	}
	public OcrmFCiOpenInfo getCustBankInfo() {
		return custBankInfo;
	}
	public void setCustBankInfo(OcrmFCiOpenInfo custBankInfo) {
		this.custBankInfo = custBankInfo;
	}
	public List<OcrmFCiAccountInfo> getCustAccountInfoList() {
		return custAccountInfoList;
	}
	public void setCustAccountInfoList(List<OcrmFCiAccountInfo> custAccountInfoList) {
		this.custAccountInfoList = custAccountInfoList;
	}
	
	public List<AcrmFCiCustIdentifier> getCustIdentyList() {
		return custIdentyList;
	}
	public void setCustIdentyList(List<AcrmFCiCustIdentifier> custIdentyList) {
		this.custIdentyList = custIdentyList;
	}
	
	public List<OcrmMCiTaxSub> getOcrmMCiTaxSubList() {
		return ocrmMCiTaxSubList;
	}
	public void setOcrmMCiTaxSubList(List<OcrmMCiTaxSub> ocrmMCiTaxSubList) {
		this.ocrmMCiTaxSubList = ocrmMCiTaxSubList;
	}
	public List<OcrmMCiTaxSub> getOcrmMCiTaxSubList2() {
		return ocrmMCiTaxSubList2;
	}
	public void setOcrmMCiTaxSubList2(List<OcrmMCiTaxSub> ocrmMCiTaxSubList2) {
		this.ocrmMCiTaxSubList2 = ocrmMCiTaxSubList2;
	}
	public OcrmMCiRelPerson getCustRelPerson() {
		return custRelPerson;
	}
	public void setCustRelPerson(OcrmMCiRelPerson custRelPerson) {
		this.custRelPerson = custRelPerson;
	}
	public AcrmFCiPerFamily getPersonFamilyInfo() {
		return personFamilyInfo;
	}
	public void setPersonFamilyInfo(AcrmFCiPerFamily personFamilyInfo) {
		this.personFamilyInfo = personFamilyInfo;
	}
	public OcrmFCiBelongCustmgr getBelongManager() {
		return belongManager;
	}
	public void setBelongManager(OcrmFCiBelongCustmgr belongManager) {
		this.belongManager = belongManager;
	}
	public OcrmFCiBelongOrg getBelongOrg() {
		return belongOrg;
	}
	public void setBelongOrg(OcrmFCiBelongOrg belongOrg) {
		this.belongOrg = belongOrg;
	}
	public OcrmMCiTaxMain getOcrmMCiTaxMain() {
		return ocrmMCiTaxMain;
	}
	public void setOcrmMCiTaxMain(OcrmMCiTaxMain ocrmMCiTaxMain) {
		this.ocrmMCiTaxMain = ocrmMCiTaxMain;
	}
	public OcrmMCiTaxMain getOcrmMCiTaxMain2() {
		return ocrmMCiTaxMain2;
	}
	public void setOcrmMCiTaxMain2(OcrmMCiTaxMain ocrmMCiTaxMain2) {
		this.ocrmMCiTaxMain2 = ocrmMCiTaxMain2;
	}
	public AcrmFCiPerKeyflag getAcrmFCiPerKeyflag() {
		return acrmFCiPerKeyflag;
	}
	public void setAcrmFCiPerKeyflag(AcrmFCiPerKeyflag acrmFCiPerKeyflag) {
		this.acrmFCiPerKeyflag = acrmFCiPerKeyflag;
	}
	
}
