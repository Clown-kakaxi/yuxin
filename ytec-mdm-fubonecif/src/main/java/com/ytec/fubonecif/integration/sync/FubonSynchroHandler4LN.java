/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.integration.sync
 * @文件名：SynchroToSystemHandler.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:08:43
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：FubonSynchroHandler4LN
 * @类描述：富邦华一银行数据同步处理类，信贷系统客户化
 * @功能描述:
 * @创建人：wangtb@yuchengtech.com
 * @创建时间：
 * @修改人：wangtb@yuchengtech.com
 * @修改时间：
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
			log.error("同步客户的客户号为空");
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_CUSTNO_ERROR.getCode());
			txEvtNotice.setEventDealInfo("同步客户的客户号为空");
			return false;
		}

		String custType = null;
		// TODO: 获取custType，通过客户号查询customer表
		baseDAO = (JPABaseDAO<?, ?>) SpringContextUtils.getBean("baseDAO");
		MCiCustomer cust = null;

		List<MCiCustomer> custList = baseDAO.findWithIndexParam("FROM MCiCustomer where custId=?", syncCustNo);

		if (custList != null && custList.size() > 1) {
			String msg = String.format("数据错误，客户表[m_ci_customer]中数据(客户号: %s)不唯一", syncCustNo);
			log.error(msg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，" + msg);
			return false;
		} else if (custList.size() == 0) {
			String msg = String.format("数据错误，客户表[m_ci_customer]中数据(客户号: )不存在", syncCustNo);
			log.error(msg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，" + msg);
			return false;
		}
		
		cust = custList.get(0);
		custType = cust.getCustType();
		if (StringUtil.isEmpty(custType)) {
			String msg = String.format("数据错误，客户表[m_ci_customer]中数据客户类型为空，客户号: ", syncCustNo);
			log.error(msg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + ", " + msg);
			return false;
		}

		// 潜在客户不同步
		if (MdmConstants.IS_POTENTIAL_CUST.equals(cust.getPotentialFlag())) {
//			String msg = String.format("客户(客户号: %s)为潜在客户，不同步信息", syncCustNo);
//			log.warn(msg);
//			txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
//			txEvtNotice.setEventDealInfo(msg);
//			return false;
			//判断是否是信贷户
			//modify by liuming 20170524
			if(cust.getLoanCustStat() == null || cust.getLoanCustStat().equals("")){
				String msg = String.format("客户(客户号: %s)为潜在客户，不同步信息", syncCustNo);
				log.warn(msg);
				txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
				txEvtNotice.setEventDealInfo(msg);
				return false;
			}

		}

		List<MCiCustomer> crsIdx = baseDAO.findWithIndexParam("FROM MCiCrossindex where custId=? and srcSysNo=?", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
		if (crsIdx != null) {
			if (crsIdx.size() == 0) {
//				String msg = String.format("客户(客户号: %s)不是信贷(%s)客户，不需要同步信息", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
//				log.warn(msg);
//				txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
//				txEvtNotice.setEventDealInfo(ErrorCode.SUCCESS.getCode() + "，" + msg);
//				return false;
				//判断是否是信贷户
				//modify by liuming 20170524
				if(cust.getLoanCustStat() == null || cust.getLoanCustStat().equals("")){
				   String msg = String.format("客户(客户号: %s)不是信贷(%s)客户，不需要同步信息", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
				   log.warn(msg);
				   txEvtNotice.setEventDealResult(ErrorCode.SUCCESS.getCode());
				   txEvtNotice.setEventDealInfo(ErrorCode.SUCCESS.getCode() + "，" + msg);
				   return false;
			  }
			} else if (crsIdx.size() > 1) {
				String msg = String.format("数据错误，交叉索引表[m_ci_crossindex]中数据(客户号: %s,系统:%s)不唯一", syncCustNo, MdmConstants.SRC_SYS_CD_LN);
				log.error(msg);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
				txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，" + msg);
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
			log.error("同步信贷系统错误，错误的客户类型:{}", custType);
			return false;
		}
	}

	// TODO 改报文格式
	@Override
	public boolean asseReqMsg(TxSyncConf txSyncConf, Element databody) {

		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean asseReqMsg4Com(MCiCustomer cust, TxEvtNotice txEvtNotice) {
		requestDoc = DocumentHelper.createDocument();
		String custNo = cust.getCustId();
		// 客户表
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
			log.error("数据错误，客户表[m_ci_customer]中数据(客户号{})不唯一", custNo);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，客户表[m_ci_customer]中数据(客户号" + custNo + ")不唯一");
			return false;
		}

		if (orgList == null || orgList.size() != 1) {
			String errMsg = String.format("数据错误，客户表[m_ci_org]中数据(客户号:%s)记录数(%d)错误", custNo, orgList.size());
			log.error(errMsg);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，" + errMsg);
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

			// 与信贷直连
			// dataNode = requestDoc.addElement("Packet").addElement("Data");
			// 通过EAI与信贷连接
			dataNode = requestBody.addElement("Packet").addElement("Data");

			// 与信贷交互报文中，节点顺序固定，并且一级节点首字母大写，否则信贷系统解析报文时会出现问题
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

			// 设置节点为<node></node>样式
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

			orgNode.addElement("annualIncome").setText(org.getAnnualIncome() == null ? "" : "" + org.getAnnualIncome());// 预计营业收入 TODO
			//modify by liuming 20170714
//			orgNode.addElement("areaCode").setText(org.getAreaCode() == null ? "" : org.getAreaCode());// 注册地行政区划
			String areaCode =  null;
			//先从机构表中查询,查询不到再到注册表中查询.
			//机构表没有更新，直接去注册表查询
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
			orgNode.addElement("areaCode").setText(areaCode);// 注册地行政区划
			orgNode.addElement("buildDate").setText(org.getBuildDate() == null ? "" : "" + org.getBuildDate());// 成立日期 TODO
			orgNode.addElement("comHoldType").setText(org.getComHoldType() == null ? "" : org.getComHoldType());// 控股类型
			orgNode.addElement("comSpBusiness").setText(org.getComSpBusiness() == null ? "" : org.getComSpBusiness());// 特种经营标识
			orgNode.addElement("comSpDetail").setText(org.getComSpDetail() == null ? "" : org.getComSpDetail());// 特种经营情况
			orgNode.addElement("comSpEndDate").setText(org.getComSpEndDate() == null ? "" : "" + org.getComSpEndDate());// 特种经营到期日期 TODO
			orgNode.addElement("comSpLicNo").setText(org.getComSpLicNo() == null ? "" : org.getComSpLicNo());// 特种经营许可证编号
			orgNode.addElement("comSpLicOrg").setText(org.getComSpLicOrg() == null ? "" : org.getComSpLicOrg());// 特种许可证颁发机关
			orgNode.addElement("comSpStrDate").setText(org.getComSpStrDate() == null ? "" : org.getComSpStrDate().toString());// 特种经营起始日期 TODO
			orgNode.addElement("employeeScale").setText(org.getEmployeeScale() == null ? "" : org.getEmployeeScale());// 从业人数
			orgNode.addElement("entBelong").setText(org.getEntBelong() == null ? "" : org.getEntBelong());// 隶属关系
			orgNode.addElement("entProperty").setText(org.getEntProperty() == null ? "" : org.getEntProperty());// 企业性质
			orgNode.addElement("entScale").setText(org.getEntScale() == null ? "" : org.getEntScale());// 企业规模（银监）
			orgNode.addElement("entScaleRh").setText(org.getEntScaleRh() == null ? "" : org.getEntScaleRh());// 企业规模（人行）
			orgNode.addElement("finRepType").setText(org.getFinRepType() == null ? "" : org.getFinRepType());// 财务报表类型
			orgNode.addElement("fundSource").setText(org.getFundSource() == null ? "" : org.getFundSource());// 经费来源
			orgNode.addElement("holdStockAmt").setText(org.getHoldStockAmt() == null ? "" : org.getHoldStockAmt().toString());// 拥有我行股份金额 TODO
			orgNode.addElement("inCllType").setText(org.getInCllType() == null ? "" : org.getInCllType());// 行内行业类别
			orgNode.addElement("industryCategory").setText(org.getIndustryCategory() == null ? "" : org.getIndustryCategory());// 行业分类（企业规模）
			orgNode.addElement("investType").setText(org.getInvestType() == null ? "" : org.getInvestType());// 投资主体
			orgNode.addElement("legalReprGender").setText(org.getLegalReprGender() == null ? "" : org.getLegalReprGender());// 法定代表人性别
			orgNode.addElement("legalReprIdentNo").setText(org.getLegalReprIdentNo() == null ? "" : org.getLegalReprIdentNo());// 法定代表人/负责人证件号码
			orgNode.addElement("legalReprIdentType").setText(org.getLegalReprIdentType() == null ? "" : org.getLegalReprIdentType());// 法定代表人/负责人证件类型
			orgNode.addElement("legalReprName").setText(org.getLegalReprName() == null ? "" : org.getLegalReprName());// 法定代表人/负责人姓名
			orgNode.addElement("legalReprTel").setText(org.getLegalReprTel() == null ? "" : org.getLegalReprTel());// 法定代表人/负责人联系电话
			orgNode.addElement("loadCardAuditDt").setText(org.getLoadCardAuditDt() == null ? "" : org.getLoadCardAuditDt().toString());// 贷款卡最近年审日期 TODO
			orgNode.addElement("loadCardPwd").setText(org.getLoadCardPwd() == null ? "" : org.getLoadCardPwd());// 贷款卡密码
			orgNode.addElement("loanCardFlag").setText(org.getLoanCardFlag() == null ? "" : org.getLoanCardFlag());// 有无贷款卡
			orgNode.addElement("loanCardNo").setText(org.getLoanCardNo() == null ? "" : org.getLoanCardNo());// 贷款卡编号
			orgNode.addElement("loanCardStat").setText(org.getLoanCardStat() == null ? "" : org.getLoanCardStat());// 贷款卡有效标志
			orgNode.addElement("mainBusiness").setText(org.getMainBusiness() == null ? "" : org.getMainBusiness());// 主营业务范围
//			orgNode.addElement("mainIndustry").setText(org.getMainIndustry() == null ? "" : org.getMainIndustry());// 行业类型
			//modify by liuming 20170712
			orgNode.addElement("mainIndustry").setText(org.getInCllType() == null ? "" : org.getInCllType());// 行业类型
			
			orgNode.addElement("minorBusiness").setText(org.getMinorBusiness() == null ? "" : org.getMinorBusiness());// 兼营范围
			orgNode.addElement("nationCode").setText(org.getNationCode() == null ? "" : org.getNationCode());// 国别
			orgNode.addElement("orgCustType").setText(org.getOrgCustType() == null ? "" : org.getOrgCustType());// 客户类型
			orgNode.addElement("orgFex").setText(org.getOrgFex() == null ? "" : org.getOrgFex());// 传真
			orgNode.addElement("orgHomepage").setText(org.getOrgHomepage() == null ? "" : org.getOrgHomepage());// 网址
			orgNode.addElement("orgTel").setText(org.getOrgTel() == null ? "" : org.getOrgTel());// 联系电话
			orgNode.addElement("orgZipcode").setText(org.getOrgZipcode() == null ? "" : org.getOrgZipcode());// 邮政编码
			orgNode.addElement("superDept").setText(org.getSuperDept() == null ? "" : org.getSuperDept());// 上级主管单位
			orgNode.addElement("topCorpLevel").setText(org.getTopCorpLevel() == null ? "" : org.getTopCorpLevel());// 龙头企业
			orgNode.addElement("totalAssets").setText(org.getTotalAssets() == null ? "" : org.getTotalAssets().toString());// 预计年度资产总额 TODO
			//add by liuming 20170524
			orgNode.addElement("creditCode").setText(org.getCreditCode() == null ? "" : org.getCreditCode());// 机构信用代码
			orgNode.addElement("usCreditcode").setText(org.getBusiLicNo() == null ? "" : org.getBusiLicNo());// 统一社会信用代码
			orgNode.addElement("loanOrgType").setText(org.getLoanOrgType() == null ? "" : org.getLoanOrgType());// 组织机构类别
			orgNode.addElement("flagCapDtl").setText(org.getFlagCapDtl() == null ? "" : org.getFlagCapDtl());// 组织机构类别细分
			orgNode.addElement("yearRate").setText(org.getYearRate() == null ? "" : org.getYearRate());// 年化入账比例
			orgNode.addElement("orgState").setText(org.getOrgState() == null ? "" : org.getOrgState());// 机构状态
			orgNode.addElement("basCusState").setText(org.getBasCusState()== null ? "" : org.getBasCusState());// 基本户状态
			// TODO
			// String actBusiAddr = null;// 实际经营地址 addrType=08
			String legalReprAddr = null;// 法人代表户籍地址 or M_CI_ORG.legal_repr_addr
			String postAddr = null;// 通讯地址 addrType=02 or M_CI_ORG.org_addr
			String registerAddr = null;// 注册登记地址 addrType=07 or M_CI_ORG_REGISTERINFO.register_addr
			String registerEnAddr = null;// 外文注册登记地址M_CI_ORG_REGISTERINFO.register_en_addr

			legalReprAddr = org.getLegalReprAddr();// org 表中数据不原则上不会为空
			postAddr = org.getOrgAddr();
			registerAddr = reginfo == null ? null : reginfo.getRegisterAddr();
			registerEnAddr = reginfo == null ? null : reginfo.getRegisterEnAddr();

			// 法人代表户籍地址 ，无法从地址表中获取，从机构表ORG 中取字段 legal_repr_addr
			addrNode.addElement("legalReprAddr").setText(legalReprAddr == null ? "" : legalReprAddr);
			if (addrList != null && addrList.size() > 0) {
				for (MCiAddress address : (List<MCiAddress>) addrList) {
					
					// 实际经营地址 addrType=08
					if (MdmConstants.ADDR_TYPE_ACT_BUSI.equals(address.getAddrType())) {
						addrNode.addElement("actBusiAddr").setText(address.getAddr() == null ? "" : address.getAddr()); // 地址信息 详细地址，地址类型：'08'，实际经营地址 实际经营地址 acu_addr
					}
					//modify by liuyx 20171221 被注释掉的写法对于循环里的每一项都会执行if或else里的一个，会导致添加了很多重复项
					// 通讯地址 addrType=02 or ORG.org_addr
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
					// 注册登记地址 addrType=07 or ORG.register_addr
					/*if (registerAddr == null && MdmConstants.ADDR_TYPE_REG.equals(address.getAddrType())) {
						addrNode.addElement("registerAddr").setText(address.getAddr() == null ? "" : address.getAddr());
						addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
					} else if (address != null) {
						addrNode.addElement("registerAddr").setText(registerAddr == null ? "" : registerAddr);
						addrNode.addElement("registerEnAddr").setText(address.getEnAddr() == null ? "" : address.getEnAddr());
					}*/
					//全行客户查询修改注册信息只更新了地址信息表，这里只取地址信息表的注册地址信息
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
					// 外文注册登记地址 ORG.register_en_addr
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
			custNode.addElement("arCustFlag").setText(cust.getArCustFlag() == null ? "" : cust.getArCustFlag());// 保理客户标志（AR客户标志(CSPS)）
			custNode.addElement("arCustType").setText(cust.getArCustType() == null ? "" : cust.getArCustType());// 保理客户类型（AR客户类型(CSPS)）
			custNode.addElement("createBranchNo").setText(cust.getCreateBranchNo() == null ? "" : cust.getCreateBranchNo());// 登记机构
			custNode.addElement("createDate").setText(cust.getCreateDate() == null ? "" : cust.getCreateDate().toString());// 登记日期 TODO
			custNode.addElement("createTellerNo").setText(cust.getCreateTellerNo() == null ? "" : cust.getCreateTellerNo());// 登记人
			custNode.addElement("cusBankRel").setText(cust.getCusBankRel() == null ? "" : cust.getCusBankRel());// 与我行关联关系
			custNode.addElement("cusCorpRel").setText(cust.getCusCorpRel() == null ? "" : cust.getCusCorpRel());// 与我行合作关系
			custNode.addElement("custName").setText(cust.getCustName() == null ? "" : cust.getCustName());// 客户名称
			custNode.addElement("enName").setText(cust.getEnName() == null ? "" : cust.getEnName());// 外文名称
			custNode.addElement("inoutFlag").setText(cust.getInoutFlag() == null ? "" : cust.getInoutFlag());// 境内境外标志
			//custNode.addElement("loanCustMgr").setText(cust.getLoanCustMgr() == null ? "" : cust.getLoanCustMgr());// 主管客户经理
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
				custNode.addElement("loanCustMgr").setText(loanCustMgr);// 主管客户经理
			}else{
				custNode.addElement("loanCustMgr").setText("");// 主管客户经理
			}
			custNode.addElement("loanCustStat").setText(cust.getLoanCustStat() == null ? "" : cust.getLoanCustStat());// 状态
			//custNode.addElement("loanMainBrId").setText(cust.getLoanMainBrId() == null ? "" : cust.getLoanMainBrId());// 主管机构
			//modify by liuming 20170712
			if(belongBranchList != null && belongBranchList.size() > 0){
				belongBranchInfo = belongBranchList.get(0);
				custNode.addElement("loanMainBrId").setText(belongBranchInfo.getBelongBranchNo() == null ? "" : belongBranchInfo.getBelongBranchNo());// 主管机构
			}else{
				custNode.addElement("loanMainBrId").setText("");// 主管机构
			}
			custNode.addElement("shortName").setText(cust.getShortName() == null ? "" : cust.getShortName());// 客户简称
			//add by liuming 20170524
			custNode.addElement("coreNo").setText(cust.getCoreNo() == null ? "" : cust.getCoreNo());// 核心客户号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			custNode.addElement("firstLoanDate").setText(cust.getFirstLoanDate() == null ? "" : sdf.format(cust.getFirstLoanDate()));//信贷最早开户日期
			// TODO
			for (MCiIdentifier identTmp : identList) {
				ident = identTmp;

				// TODO
				if (MdmConstants.IDENT_TYPE_FEXC_TAX.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					identNode.addElement("fexcIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// 外汇许可证号码
				}

				// TODO
				if (MdmConstants.IS_LN_OPEN_IDENT.equals(ident.getIsOpenAccIdent()) && !StringUtil.isEmpty(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					// 如果证件号为空，则证件类型也为置为空
					identNode.addElement("certType").setText(ident.getIdentNo() == null ? "" : ident.getIdentType()); // 客户证件信息 证件类型，开户证件标志为是 开户证件类型 cert_type
					identNode.addElement("certCode").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo()); // 客户证件信息 证件号码，开户证件标志为是 开户证件号码 cert_code
				}
				if (MdmConstants.IDENT_TYPE_NOC.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					identNode.addElement("nocIdenRegDate").setText(ident.getIdenRegDate() == null ? "" : ident.getIdenRegDate().toString());// 组织机构登记日期 TODO
					identNode.addElement("nocIdentCheckingDate").setText(ident.getIdentCheckingDate() == null ? "" : ident.getIdentCheckingDate().toString());// 组织机构代码证年检到期日 TODO
					identNode.addElement("nocIdentEffectiveDate").setText(ident.getIdentEffectiveDate() == null ? "" : ident.getIdentEffectiveDate().toString());// 组织机构有效日期 TODO
					identNode.addElement("nocIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// 组织机构代码
					identNode.addElement("nocIdentOrg").setText(ident.getIdentOrg() == null ? "" : ident.getIdentOrg());// 组织机构代码证颁发机关
				}
				if (MdmConstants.IDENT_TYPE_NAT_TAX.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					identNode.addElement("natTaxIdenRegDate").setText(ident.getIdenRegDate() == null ? "" : ident.getIdenRegDate().toString());// 国税税务登记日期 TODO
					identNode.addElement("natTaxIdentCheckingDate").setText(ident.getIdentCheckingDate() == null ? "" : ident.getIdentCheckingDate().toString());// 国税登记证年检到期日 TODO
					identNode.addElement("natTaxIdentExpiredDate").setText(ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate().toString());// 国税登记有效期 TODO
					identNode.addElement("natTaxIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// 国税税务登记代码
					identNode.addElement("natTaxIdentOrg").setText(ident.getIdentOrg() == null ? "" : ident.getIdentOrg());// 国税税务登记机关
				}
				if (MdmConstants.IDENT_TYPE_LOC_TAX.equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
					// TODO
					identNode.addElement("locTaxIdenRegDate").setText(ident.getIdenRegDate() == null ? "" : ident.getIdenRegDate().toString());// 地税税务登记日期 TODO
					identNode.addElement("locTaxIdentCheckingDate").setText(ident.getIdentCheckingDate() == null ? "" : ident.getIdentCheckingDate().toString());// 地税登记证年检到期日 TODO
					identNode.addElement("locTaxIdentExpiredDate").setText(ident.getIdentExpiredDate() == null ? "" : ident.getIdentExpiredDate().toString());// 地税登记有效期 TODO
					identNode.addElement("locTaxIdentNo").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo());// 地税税务登记代码
					identNode.addElement("locTaxIdentOrg").setText(ident.getIdentOrg() == null ? "" : ident.getIdentOrg());// 地税税务登记机关
				}
				
				//add by liuming 20170524
				if ("Z".equals(ident.getIdentType()) && !StringUtil.isEmpty(ident.getIdentNo())) {
//					orgNode.addElement("accOpenLicense").setText(ident.getIdentNo());// 开户许可证核准号
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
			// gradeNode.addElement("creditCustGrade").setText(grade.getCreditCustGrade()==null?"":grade.getCreditCustGrade());//信用等级(前期)
			// gradeNode.addElement("creditEvaluateDate").setText(grade.getCreditEvaluateDate()==null?"":grade.getCreditEvaluateDate());//信用评定日期
			// gradeNode.addElement("creditExpiredDate").setText(grade.getCreditExpiredDate()==null?"":grade.getCreditExpiredDate());//信用等级到期日期
			// gradeNode.addElement("orgName").setText(grade.getOrgName()==null?"":grade.getOrgName());//信用等级评定机构(外部)
			// gradeNode.addElement("outCreditCustGrade").setText(grade.getOutCreditCustGrade()==null?"":grade.getOutCreditCustGrade());//信用等级(外部)（本期）
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
			// // gradeNode.addElement("creditCustGrade").setText(grade.getCreditCustGrade()==null?"":grade.getCreditCustGrade());//信用等级(前期)
			// // gradeNode.addElement("creditEvaluateDate").setText(grade.getCreditEvaluateDate()==null?"":grade.getCreditEvaluateDate());//信用评定日期
			// // gradeNode.addElement("creditExpiredDate").setText(grade.getCreditExpiredDate()==null?"":grade.getCreditExpiredDate());//信用等级到期日期
			// // gradeNode.addElement("orgName").setText(grade.getOrgName()==null?"":grade.getOrgName());//信用等级评定机构(外部)
			// // gradeNode.addElement("outCreditCustGrade").setText(grade.getOutCreditCustGrade()==null?"":grade.getOutCreditCustGrade());//信用等级(外部)（本期）
			// // gradeNode.addElement("outCreditEvaluateDate").setText(grade.getOutCreditEvaluateDate()==null?"":grade.getOutCreditEvaluateDate());//信用等级评定日期(外部)
			//
			// }
			// }

			groupInfoNode.addElement("groupNo").setText("");// 集团编号
			groupInfoNode.addElement("groupType").setText("");// 集团客户类型
			// TODO
			// if (groupInfo != null) {
			// groupInfoNode.addElement("groupNo").setText(groupInfo.getGroupNo() == null ? "" : groupInfo.getGroupNo());// 集团编号
			// groupInfoNode.addElement("groupType").setText(groupInfo.getGroupType() == null ? "" : groupInfo.getGroupType());// 集团客户类型
			// }

			if (busiinfoList != null && busiinfoList.size() > 0) {
				busiinfo = busiinfoList.get(0);
				if (busiinfo != null) {
					busiinfoNode.addElement("mainProduct").setText(busiinfo.getMainProduct() == null ? "" : busiinfo.getMainProduct());
					busiinfoNode.addElement("manageStat").setText(busiinfo.getManageStat() == null ? "" : busiinfo.getManageStat());
					busiinfoNode.addElement("workFieldArea").setText(busiinfo.getWorkFieldArea() == null ? "" : busiinfo.getWorkFieldArea().toString());
					busiinfoNode.addElement("workFieldOwnership").setText(busiinfo.getWorkFieldOwnership() == null ? "" : busiinfo.getWorkFieldOwnership());
					// TODO
					// orgBusiinfoNode.addElement("mainProduct").setText(orgBusiinfo.getMainProduct()==null?"":orgBusiinfo.getMainProduct());//主要产品情况
					// orgBusiinfoNode.addElement("manageStat").setText(orgBusiinfo.getManageStat()==null?"":orgBusiinfo.getManageStat());//经营状况
					// orgBusiinfoNode.addElement("workFieldArea").setText(orgBusiinfo.getWorkFieldArea()==null?"":orgBusiinfo.getWorkFieldArea());//经营场地面积(平方米)
					// orgBusiinfoNode.addElement("workFieldOwnership").setText(orgBusiinfo.getWorkFieldOwnership()==null?"":orgBusiinfo.getWorkFieldOwnership());//经营场地所有权
				}
			}
			for (MCiOrgExecutiveinfo executiveinfoTmp : executiveinfoList) {
				executiveinfo = executiveinfoTmp;
				// TODO
				if (MdmConstants.ORG_EXCE_ACT_CTL.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("actCtrlIdentNo").setText(executiveinfo.getIdentNo() == null ? "" : executiveinfo.getIdentNo());// 实际控制人证件号码
					executiveinfoNode.addElement("actCtrlIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// 实际控制人证件类型
					executiveinfoNode.addElement("actCtrlIndivCusId").setText(executiveinfo.getIndivCusId() == null ? "" : executiveinfo.getIndivCusId());// 实际控制人客户码
					executiveinfoNode.addElement("actCtrlLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// 实际控制人姓名
				}
				if (MdmConstants.ORG_EXCE_ACT_CTL_MATE.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("actCtrlMateIdentNo").setText(executiveinfo.getIdentNo() == null ? "" : executiveinfo.getIdentNo());// 实际控制人配偶证件号码
					executiveinfoNode.addElement("actCtrlMateIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// 实际控制人证件类型
					executiveinfoNode.addElement("actCtrlMateIndivCusId").setText(executiveinfo.getIndivCusId() == null ? "" : executiveinfo.getIndivCusId());// 实际控制人配偶客户码
					executiveinfoNode.addElement("actCtrlMateLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// 实际控制人配偶姓名
				}
				if (MdmConstants.ORG_EXCE_LEGAL_REPR_MATE.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("linkMateIdentNo").setText(executiveinfo.getIdentNo() == null ? "" : executiveinfo.getIdentNo());// 法人配偶证件号码
					executiveinfoNode.addElement("linkMateIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// 法人配偶证件类型
					executiveinfoNode.addElement("linkMateIndivCusId").setText(executiveinfo.getIndivCusId() == null ? "" : executiveinfo.getIndivCusId());// 法人配偶客户码
					executiveinfoNode.addElement("linkMateLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// 法人配偶姓名
				}
				if (MdmConstants.ORG_EXCE_FINA_LINK.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("finaLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// 财务负责人
				}
				if (MdmConstants.ORG_EXCE_OP.equals(executiveinfo.getLinkmanType())) {
					executiveinfoNode.addElement("opLinkmanName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// 企业经办人
				}
				//add by liuming 20170705,法人
				if ("5".equals(executiveinfo.getLinkmanType())) {
					if(executiveinfo.getLinkmanName() != null && !executiveinfo.getLinkmanName().equalsIgnoreCase("")){
					    orgNode.remove(orgNode.element("legalReprName"));
						orgNode.addElement("legalReprName").setText(executiveinfo.getLinkmanName() == null ? "" : executiveinfo.getLinkmanName());// 法定代表人/负责人姓名
						orgNode.remove(orgNode.element("legalReprGender"));
						orgNode.addElement("legalReprGender").setText(executiveinfo.getGender() == null ? "" : executiveinfo.getGender());// 法定代表人性别
						orgNode.remove(orgNode.element("legalReprIdentType"));
						orgNode.addElement("legalReprIdentType").setText(executiveinfo.getIdentType() == null ? "" : executiveinfo.getIdentType());// 法定代表人/负责人证件类型
						orgNode.remove(orgNode.element("legalReprIdentNo"));
						orgNode.addElement("legalReprIdentNo").setText(executiveinfo.getIdentNo()== null ? "" : executiveinfo.getIdentNo());// 法定代表人/负责人证件号码
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
					 * 信贷系统对于该字段与ECIF码值有一对多关系，无法转码，暂不交互
					 * keyflagNode.addElement("isRuralCorp").setText(keyflag.getIsRuralCorp() == null ? "" : keyflag.getIsRuralCorp());
					 */
					keyflagNode.addElement("isRuralCorp").setText("");
					keyflagNode.addElement("isSteelEnt").setText(keyflag.getIsSteelEnt() == null ? "" : keyflag.getIsSteelEnt());
					keyflagNode.addElement("isTwoHighEnt").setText(keyflag.getIsTwoHighEnt() == null ? "" : keyflag.getIsTwoHighEnt());
					//add by liuming 20170524
					keyflagNode.addElement("shippingInd").setText(keyflag.getShippingInd() == null ? "" : keyflag.getShippingInd());//是否为航运行业（银监统计）
					keyflagNode.addElement("isTaiwanCorp").setText(keyflag.getIsTaiwanCorp() == null ? "" : keyflag.getIsTaiwanCorp());//是否台资企业
					keyflagNode.addElement("isTaiwanCorp").setText(keyflag.getIsTaiwanCorp() == null ? "" : keyflag.getIsTaiwanCorp());//是否台资企业
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
				//modify by liuming 20170712 信贷与CRM码值不一致,需转换
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
				registerinfoNode.addElement("regCodeType").setText(reginfo.getRegCodeType() == null ? "" : reginfo.getRegCodeType());//登记注册号类型
			}
			synchroRequestMsg = requestDoc.asXML();
			
			// TODO 如果信贷服务接口中长度标识位为4位，则使用如下方式组装报文，并修改SocketClient中packing方法
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
		// 地址类型：'08'，实际经营地址 实际经营地址 acu_addr
		List<MCiAddress> addrList = baseDAO.findWithIndexParam("FROM MCiAddress where custId=?", custNo);
		List<MCiPerson> petrList = baseDAO.findWithIndexParam("FROM MCiPerson where custId=?", custNo);
		List<MCiPerKeyflag> perKeyflagList = baseDAO.findWithIndexParam("FROM MCiPerKeyflag where custId=?", custNo);
		List<MCiGrade> gradeList = baseDAO.findWithIndexParam("FROM MCiGrade where custId=?", custNo);
		List<MCiPerMateinfo> perMateList = baseDAO.findWithIndexParam("FROM MCiPerMateinfo where custId=?", custNo);

		if (custList.size() > 1) {
			log.error("数据错误，客户表[m_ci_customer]中数据客户号{}不唯一", custNo);
			txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
			txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "客户表[m_ci_customer]中数据客户号{" + custNo + "}不唯一");
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

			// 与信贷直连
			// dataNode = requestDoc.addElement("Packet").addElement("Data");
			// 通过EAI与信贷连接
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
				log.error("数据错误，个人客户表[m_ci_person]中数据(客户号{})不唯一", custNo);
				txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
				txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，个人客户表[m_ci_person]中数据(客户号" + custNo + ")不唯一");
				return false;
			}

			if (addrList != null && addrList.size() > 0) {
				for (MCiAddress address : (List<MCiAddress>) addrList) {
					if ("08".equals(address.getAddrType())) {
						addrNode.addElement("addr").setText(address.getAddr()); // 地址信息 详细地址，地址类型：'08'，实际经营地址 实际经营地址 acu_addr
						addrNode.addElement("post_addr").setText(address.getAddr() == null ? "" : address.getAddr());
						addrNode.addElement("post_code").setText(address.getZipcode() == null ? "" : address.getZipcode());
						addrNode.addElement("area_code").setText(address.getAreaCode() == null ? "" : address.getAreaCode());
					} else {
						addrNode.addElement("addr").setText(address.getAddr()); // 地址信息 详细地址，地址类型：'08'，实际经营地址 实际经营地址 acu_addr
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
					// 如果证件号为空，则证件类型也为置为空
					identNode.addElement("certType").setText(ident.getIdentType() == null ? "" : ident.getIdentType()); // 客户证件信息 证件类型，开户证件标志为是 开户证件类型 cert_type
					identNode.addElement("certCode").setText(ident.getIdentNo() == null ? "" : ident.getIdentNo()); // 客户证件信息 证件号码，开户证件标志为是 开户证件号码 cert_code
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
					log.error("数据错误，个人客户重要标志表[m_ci_per_keyflag]中数据(客户号{})不唯一", custNo);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
					txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，数据错误，个人客户重要标志表[m_ci_per_keyflag]中数据(客户号" + custNo + ")不唯一");
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
					log.error("数据错误，配偶信息[m_ci_per_mateinfo]中数据(客户号{})不唯一", custNo);
					txEvtNotice.setEventDealResult(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getCode());
					txEvtNotice.setEventDealInfo(ErrorCode.ERR_SYNCHRO_DATA_ERROR.getChDesc() + "，数据错误，数据错误，配偶信息[m_ci_per_mateinfo]中数据(客户号" + custNo + ")不唯一");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
		synchroRequestMsg = requestDoc.asXML();
		// TODO 如果信贷服务接口中长度标识位为4位，则使用如下方式组装报文，并修改SocketClient中packing方法
		// synchroRequestMsg = String.format("%08d", synchroRequestMsg.length())+synchroRequestMsg;
		// System.out.println("信贷个金的报文：---------->"+synchroRequestMsg);
		return result;
	}

	@Override
	public boolean executeResult() {
		// log.info("数据同步响应报文：\n[{}]", synchroResponseMsg);
		try {
			Document root = XMLUtils.stringToXml(synchroResponseMsg.substring(8));
			String txCodeXpath = "//TransBody/ResponseTail/TxStatCode";
			String txMsgXpath = "//TransBody/ResponseTail/TxStatDesc";

			Node txStatCodeNode = root.selectSingleNode(txCodeXpath);
			Node txMsgNode = root.selectSingleNode(txMsgXpath);

			if (txStatCodeNode == null || txMsgNode == null) {
				String msg = String.format("{},收到外围系统[%s]响应报文必要节点为空[需要节点：%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("lnCd"), txStatCodeNode, txMsgNode);

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

					String msg = String.format("{},收到外围系统[%s]响应报文必要节点为空[需要节点：%s,%s,%s]", ErrorCode.ERR_SYNCHRO_RESP_BODY_ERROR.getChDesc(), BusinessCfg.getString("lnCd"), entScaleXpath,
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
					//一般将同步程序与交易服务部署在同一服务器上
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
			log.error("收到外围系统报文做下一步处理时出错,错误信息:\n{}", e);
			return false;
		} catch (Exception e) {
			log.error("收到外围系统报文做下一步处理时出错,错误信息:\n{}", e);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String xml = "00000195<?xml version=\"1.0\" encoding=\"gb2312\" ?><Packet><Data><Res><custId>110000688488</custId><entScale>CS01</entScale><entScaleRh>CS01</entScaleRh><errorCode>0000</errorCode><errorMsg>成功</errorMsg></Res></Data></Packet>";
		new FubonSynchroHandler4LN().executeResult();
	}
}
