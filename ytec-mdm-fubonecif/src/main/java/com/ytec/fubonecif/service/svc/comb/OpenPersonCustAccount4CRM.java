package com.ytec.fubonecif.service.svc.comb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.fubonecif.domain.MCiAddress;
import com.ytec.fubonecif.domain.MCiBelongBranch;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.fubonecif.service.component.biz.identification.UIdentifierVerif;
import com.ytec.fubonecif.service.svc.atomic.Accont;
import com.ytec.fubonecif.service.svc.atomic.AddGeneral;
import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.bo.WriteModel;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.component.biz.comidentification.GetContIdByType;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * CRM个人一键开户
 * 
 * @author 作者 wx
 * @date 创建时间:2017-8-24上午10:45:52
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenPersonCustAccount4CRM implements IEcifBizLogic {

	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory
			.getLogger(OpenCustAccForPerson.class);

	// @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		boolean isBlank = true;
		String custName = null;
		MCiCustomer nametilte = null;
		String custNameTemp = null;
		Map opMp = ecifData.getWriteModelObj().getOperMap();
		opMp.put(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD, ecifData.getOpChnlNo());
		opMp.put(MdmConstants.TX_CUST_TYPE, MdmConstants.TX_CUST_PER_TYPE);
		ecifData.setCustType(MdmConstants.TX_CUST_PER_TYPE);

		// 设置无三证是否允许开户
		boolean flag = false;
		flag = BusinessCfg.getBoolean("noIdentIsAdd");
		List generalInfoList = ecifData.getWriteModelObj().getOpModelList();
		// 封装用于识别的三证识别,无优先级顺序
		List<MCiIdentifier> identList = new ArrayList();
		MCiIdentifier itemp = null;
		for (Object obj : generalInfoList) {
			if (obj.getClass().equals(MCiIdentifier.class)) {
				flag = true;
				MCiIdentifier ident = (MCiIdentifier) obj;
				String isOpenIdentFlag = ident.getIsOpenAccIdent();
				if(!StringUtils.isEmpty(isOpenIdentFlag) && isOpenIdentFlag.equals("Y")){
					itemp = new MCiIdentifier(ident.getIdentType(),
							ident.getIdentNo(), ident.getIdentCustName());
					opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD,
							ident.getIdentType());
					opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO,
							ident.getIdentNo());
					opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME,
							ident.getIdentCustName());
					identList.add(itemp);
				}
				/** 取户名 **/
				if (MdmConstants.OPENIDENTFLAG
						.equals(ident.getIsOpenAccIdent())) {
					custName = ident.getIdentCustName();
				}
				if (custNameTemp == null) {
					custNameTemp = ident.getIdentCustName();
				} else {
					if (!custNameTemp.equals(ident.getIdentCustName())) {
						ecifData.setStatus(
								ErrorCode.ERR_ECIF_CUSTNAME.getCode(),
								"证件信息中的户名不统一");
						return;
					}
				}
			} else {
				if (obj.getClass().equals(MCiCustomer.class)) {
					nametilte = (MCiCustomer) obj;
				}
				isBlank = false;
			}
		}
		if (!flag) {
			String msg = String.format(
					"%s/证件信息无值,证件信息(证件类型、证件号码、证件户名)不齐全不允许开户",
					ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getChDesc());
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(),
					msg);
			log.error("{}:{}", ErrorCode.ERR_ECIF_NOT_EXIST_IDENT.getCode(),
					msg);
			return;
		}
		if (custName == null) {
			custName = identList.get(0).getIdentCustName();
		}
		if (nametilte != null) {
			nametilte.setCustName(custName);
		}
		// 添加三证识别信息
		 opMp.put("identList", identList);

		// 识别
		GetContIdByType findContId = (GetContIdByType) SpringContextUtils
				.getBean("getContIdByType");
		findContId.bizGetContId(ecifData);
		if (MdmConstants.checkCustomerStatus && ecifData.isSuccess()) {
			CustStatus custStatCtrl = null;
			if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus(
					ecifData.getCustStatus())) != null) {
				if (!custStatCtrl.isNormal()) {
					if (custStatCtrl.isReOpen()) {
						MCiCustomer customer = null;
						if (ecifData.getWriteModelObj().containsOpModel(
								MCiCustomer.class.getSimpleName())) {
							customer = (MCiCustomer) ecifData
									.getWriteModelObj().getOpModelByName(
											MCiCustomer.class.getSimpleName());
						} else {
							customer = new MCiCustomer();
						}
						customer.setCustStat(MdmConstants.STATE);
						ecifData.getWriteModelObj().setOpModelList(customer);
						ecifData.getWriteModelObj().setDivInsUpd(false);
						// log.info("客户({})状态({}),重新开户启用",ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
						log.info("客户({})状态({}),重新开户启用", ecifData.getCustId(),
								custStatCtrl.getCustStatusDesc());
					} else if (!custStatCtrl.isUpdate()) {
						log.warn("客户({})状态{}", ecifData.getCustId(),
								custStatCtrl.getCustStatusDesc());
						// ecifData.setStatus(ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),"该客户%s状态:%s",
						// ecifData.getEcifCustNo(),custStatCtrl.getCustStatusDesc());
						ecifData.setStatus(
								ErrorCode.ERR_ECIF_CUST_STATUS.getCode(),
								"该客户%s状态:%s", ecifData.getCustId(),
								custStatCtrl.getCustStatusDesc());
						return;
					}
				}
			}
		}
		if (ecifData.isSuccess()) {
			/**
			 * 验证开户证件修改
			 */
			UIdentifierVerif uIdentifierVerif = (UIdentifierVerif) SpringContextUtils
					.getBean("uIdentifierVerif");
			boolean v = uIdentifierVerif.VerifIdentifier(ecifData);
			if (!v) {
				return;
			}
			if (!ecifData.getWriteModelObj().isDivInsUpd()) {
				AddGeneral addGeneral = (AddGeneral) SpringContextUtils
						.getBean("addGeneral");
				try {
					addGeneral.process(ecifData);
					// ecifData.getWriteModelObj().setResult("custNo",
					// ecifData.getEcifCustNo());
					// add by liuming 20170808
					//savePhone(ecifData);
					ecifData.getWriteModelObj().setResult(
							MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
							ecifData.getCustId());
					ecifData.setStatus(
							ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getCode(),
							ErrorCode.ERR_ECIF_EXIST_CUST_UPDATE.getChDesc());
					ecifData.setSuccess(true);

				} catch (Exception e) {
					log.error("数据操作异常", e);
					if (ecifData.isSuccess()) {
						ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
					}
				}
				return;
			} else {
				// ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(),
				// "客户已存在:" + ecifData.getEcifCustNo());
				// ecifData.getWriteModelObj().setResult("custNo",
				// ecifData.getEcifCustNo());
				ecifData.setStatus(ErrorCode.ERR_ECIF_EXIST_CUST.getCode(),
						"客户已存在:" + ecifData.getCustId());
				// ecifData.getWriteModelObj().setResult("custNo",
				// ecifData.getCustId());
				ecifData.getWriteModelObj().setResult(
						MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
				return;
			}
		} else {
			if (!ErrorCode.ERR_ECIF_NOT_EXIST_CONTID.getCode().equals(
					ecifData.getRepStateCd())) {
				log.warn("返回非期望的数据:{}", ecifData.getDetailDes());
				return;
			}
			// 识别不成功，开户
			Accont accont = (Accont) SpringContextUtils.getBean("accont");
			ecifData.resetStatus();
			try {
				accont.process(ecifData, isBlank);
				ecifData.getWriteModelObj().setResult(
						MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
				// 开户成功
				saveData(ecifData);//数据落地
			} catch (Exception e) {
				e.printStackTrace();
				String logMsg = "ECIF开户失败，具体原因：" + e.getMessage();
				log.error(logMsg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
				ecifData.setSuccess(false);
				return;
			}
		}
		return;
	}
	
	
	@Transactional
	private EcifData saveData(EcifData ecifData){
		try {
			if (ecifData == null) {
				String logMsg = "CRM请求开户时数据落地失败，具体原因：传入数据为空";
				log.error(logMsg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
				ecifData.setSuccess(false);
				return ecifData;//
			}
			String ecifId = ecifData.getCustId();//成功开户的客户号
			if(StringUtils.isEmpty(ecifId)){
				String logMsg = "CRM请求开户时数据落地失败，具体原因：ECIF客户号为空";
				log.error(logMsg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
				ecifData.setSuccess(false);
				return ecifData;//
			}
			Element repNode = ecifData.getRepNode();
			if(repNode == null){
				repNode = DocumentHelper.createElement("ResponseBody");
			}
			List<Object> objList = ecifData.getWriteModelObj().getOpModelList();
			if (objList != null && objList.size() >= 0) {
				for (Object obj : objList) {
					if(obj.getClass().equals(MCiCustomer.class)){
						//保存客户信息
						MCiCustomer customerInfo = (MCiCustomer) obj;
						customerInfo.setCustId(ecifId);
						this.baseDAO.merge(customerInfo);
					}else if(obj.getClass().equals(MCiIdentifier.class)){
						//保存证件信息
						MCiIdentifier identifierInfo = (MCiIdentifier) obj;
						if(StringUtils.isEmpty(identifierInfo.getIdentNo())
								|| StringUtils.isEmpty(identifierInfo.getIdentType())
								|| StringUtils.isEmpty(identifierInfo.getIdentCustName())){
							String logMsg = "CRM请求开户时数据落地失败，具体原因：证件信息不全";
							log.error(logMsg);
							ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
							ecifData.setSuccess(false);
							return ecifData;//
						}else{
							identifierInfo.setCustId(ecifId);
							//ECIF客户号和证件类型作为联合主键
							String queIdentInfo = "select t from MCiIdentifier t where t.custId=:ecifId and t.identType=:identType";
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("ecifId", ecifId);
							params.put("identType", identifierInfo.getIdentType());
							List<MCiIdentifier> identInfoList = this.baseDAO.findWithNameParm(queIdentInfo, params);
							if(identInfoList == null || identInfoList.size() != 1){
								String sql_nextIdentId = "SELECT SEQ_IDENT_ID.NEXTVAL FROM DUAL";
								List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextIdentId);
								String str_nextIdentId = "100000";
								if (l_o != null && l_o.size() >= 1) {
									Object o = l_o.get(0);
									if (o != null && !o.toString().equals("")) {
										str_nextIdentId = o.toString();
									}
								}
								identifierInfo.setIdentId(str_nextIdentId);
							}else{
								identifierInfo = identInfoList.get(0);
							}
							
							this.baseDAO.merge(identifierInfo);
							//返回技术主键
							Element eleIdentType = DocumentHelper.createElement("identType");//联系方式
							eleIdentType.setText(identifierInfo.getIdentType());
							Element eleIdentId = DocumentHelper.createElement("identId");//联系方式ID
							eleIdentId.setText(identifierInfo.getIdentId());
							Element eleIdentify = DocumentHelper.createElement("identifyInfo");//联系方式节点
							eleIdentify.add(eleIdentType);
							eleIdentify.add(eleIdentId);
							repNode.add(eleIdentify);
							ecifData.setRepNode(repNode);//添加到返回信息
							System.err.println("当前信息：【identType："+identifierInfo.getIdentType());
							System.err.println("当前信息：【identId："+identifierInfo.getIdentId());
							System.err.println("当前信息：【identifyInfo："+eleIdentify.asXML());
						}
					}else if(obj.getClass().equals(MCiPerson.class)){
						//保存个人信息
						MCiPerson personInfo = (MCiPerson) obj;
						personInfo.setCustId(ecifId);
						this.baseDAO.merge(personInfo);
					}else if(obj.getClass().equals(MCiContmeth.class)){
						//保存联系方式信息
						MCiContmeth contmethInfo = (MCiContmeth) obj;
						contmethInfo.setCustId(ecifId);
						String contmethType = contmethInfo.getContmethType();
						String slqContmethInfo = "select t from MCiContmeth t where t.custId=:ecifId and t.contmethType=:contmethType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("ecifId", ecifId);
						params.put("contmethType", contmethType);
						List<MCiContmeth> contmethList = this.baseDAO.findWithNameParm(slqContmethInfo, params);
						//联系方式表中ECIF客户号和联系方式类型作为联合主键，不会出现同一ECIF客户号同一联系方式类型的多条数据
						if(contmethList == null || contmethList.size() != 1){
							String sql_nextContmethId = "SELECT SEQ_CONTMETH_ID.NEXTVAL FROM DUAL";
							List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextContmethId);
							String str_nextContmethId = "100000";
							if (l_o != null && l_o.size() >= 1) {
								Object o = l_o.get(0);
								if (o != null && !o.toString().equals("")) {
									str_nextContmethId = o.toString();
								}
							}
							contmethInfo.setContmethId(str_nextContmethId);
						}else{
							contmethInfo = contmethList.get(0);
						}
						this.baseDAO.merge(contmethInfo);
						//返回技术主键
						Element eleContmenthType = DocumentHelper.createElement("contmenthType");//联系方式
						eleContmenthType.setText(contmethInfo.getContmethType());
						Element eleContmethId = DocumentHelper.createElement("contmethId");//联系方式ID
						eleContmethId.setText(contmethInfo.getContmethId());
						Element eleContmenth = DocumentHelper.createElement("contMeth");//联系方式节点
						eleContmenth.add(eleContmenthType);
						eleContmenth.add(eleContmethId);
						repNode.add(eleContmenth);
						ecifData.setRepNode(repNode);//添加到返回信息
						System.err.println("当前信息：【contmenthType："+contmethInfo.getContmethType());
						System.err.println("当前信息：【contmethId："+contmethInfo.getContmethId());
						System.err.println("当前信息：【repNode："+eleContmenth.asXML());
//						ecifData.getWriteModelObj().setResult("contmeth", params);
					}else if(obj.getClass().equals(MCiAddress.class)){
						//保存地址信息
						MCiAddress addressInfo = (MCiAddress) obj;
						String addrType = addressInfo.getAddrType();
						String queAddrInfo = "select t from MCiAddress t where t.custId=:ecifId and t.addrType=:addrType";
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("ecifId", ecifId);
						params.put("addrType", addrType);
						List<MCiAddress> addrInfoList = this.baseDAO.findWithNameParm(queAddrInfo, params);
						if(addrInfoList == null || addrInfoList.size() != 1){
							String sql_nextAddressId = "SELECT SEQ_ADDR_ID.NEXTVAL FROM DUAL";
							List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextAddressId);
							String str_nextAddressId = "100000";
							if (l_o != null && l_o.size() >= 1) {
								Object o = l_o.get(0);
								if (o != null && !o.toString().equals("")) {
									str_nextAddressId = o.toString();
								}
							}
							addressInfo.setAddrId(str_nextAddressId);
						}else{
							addressInfo = addrInfoList.get(0);
						}
						addressInfo.setCustId(ecifId);
						this.baseDAO.merge(addressInfo);
						//返回技术主键
						Element eleAddrType = DocumentHelper.createElement("addrType");//联系方式
						eleAddrType.setText(addressInfo.getAddrType());
						Element eleAddrId = DocumentHelper.createElement("addrId");//联系方式ID
						eleAddrId.setText(addressInfo.getAddrId());
						Element eleAddr = DocumentHelper.createElement("address");//联系方式节点
						eleAddr.add(eleAddrType);
						eleAddr.add(eleAddrId);
						repNode.add(eleAddr);
						ecifData.setRepNode(repNode);//添加到返回信息
						System.err.println("当前信息：【addrType："+addressInfo.getAddrType());
						System.err.println("当前信息：【addrId："+addressInfo.getAddrId());
						System.err.println("当前信息：【address："+eleAddr.asXML());
					}else if(obj.getClass().equals(MCiBelongManager.class)){
						//保存归属客户经理信息
						MCiBelongManager belongMgrInfo = (MCiBelongManager) obj;
						String sql_nextBelongMgrId = "SELECT SEQ_BELONG_MANAGER_ID.NEXTVAL FROM DUAL";
						List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextBelongMgrId);
						String str_nextBelongMgrId = "100000";
						if (l_o != null && l_o.size() >= 1) {
							Object o = l_o.get(0);
							if (o != null && !o.toString().equals("")) {
								str_nextBelongMgrId = o.toString();
							}
						}
						belongMgrInfo.setBelongManagerId(str_nextBelongMgrId);
						belongMgrInfo.setCustId(ecifId);
						this.baseDAO.merge(belongMgrInfo);
					}else if(obj.getClass().equals(MCiBelongBranch.class)){//客户归属机构信息
						MCiBelongBranch belongBranch = (MCiBelongBranch) obj;
						String sql_nextBelongBranchId = "select SEQ_BELONG_BRANCH_ID.nextval from dual";
						List<Object> l_o = this.baseDAO.findByNativeSQLWithIndexParam(sql_nextBelongBranchId);
						String str_nextBelongBranchId = "100000";
						if (l_o != null && l_o.size() >= 1) {
							Object o = l_o.get(0);
							if (o != null && !o.toString().equals("")) {
								str_nextBelongBranchId = o.toString();
							}
						}
						belongBranch.setBelongBranchId(str_nextBelongBranchId);
						belongBranch.setCustId(ecifId);
						this.baseDAO.merge(belongBranch);
					}
					
				}
			}
			ecifData.setStatus(ErrorCode.SUCCESS);
			ecifData.setSuccess(true);
			return ecifData;
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "CRM请求开户时数据落地失败，具体原因："+e.getMessage();
			log.error(logMsg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), logMsg);
			ecifData.setSuccess(false);
			return ecifData;//
		}
	}
	

	public synchronized String callProcedure(List generalInfoList)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		Properties properties = new Properties();
		try {
			InputStream inputStream = OpenCustAccForPerson.class
					.getResourceAsStream("/jdbc.properties");
			properties.load(inputStream);
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = properties.getProperty("jdbc.url");
		String user = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		Connection conn = DriverManager.getConnection(url, user, password);
		try {
			// 调用网站临时表存储过程
			CallableStatement cstmt = null;
			String procedure = "{call SP_M_CI_LOAD_WZ(?,?,?,?)}";
			cstmt = conn.prepareCall(procedure);
			String identCustName = null;
			String identType = null;
			String identNo = null;
			for (Object obj : generalInfoList) {
				if (obj.getClass().equals(MCiCustomer.class)) {
					MCiCustomer ident = (MCiCustomer) obj;
					identCustName = ident.getCustName();
					identType = ident.getIdentType();
					identNo = ident.getIdentNo();
				}
			}
			cstmt.setString(1, identCustName);
			cstmt.setString(2, identType);
			cstmt.setString(3, identNo);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			// cstmt.setString(1, coreNo);
			cstmt.executeUpdate();
			conn.commit();
			String code = cstmt.getString(4);
			if ("200".equals(code)) {
				log.warn("调用存储过程异常:", "调用存储过程后临时表数据未进入正式表中，证件号为：" + identNo
						+ ";证件类型为：" + identType + ";证件户名为：" + identCustName);
			} else if ("0".equals(code)) {
				log.info("调用存储过程成功,证件号为：" + identNo + ";证件类型为：" + identType
						+ ";证件户名为：" + identCustName);
			} else {
				log.info("调用存储过程,证件号为：" + identNo + ";证件类型为：" + identType
						+ ";证件户名为：" + identCustName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	public void savePhone(EcifData ecifData) {
		// add by liuming 20170808虚拟银行(手机号)增加手机号mobilePhone
		String mobilePhone = null;
		String belongManager = null;
		WriteModel writeModel = ecifData.getWriteModelObj();
		MCiPerson mciperson = null;// 个人客户信息
		MCiBelongManager mcibelongmanager = null;// 归属客户经理
		if ((mciperson = (MCiPerson) writeModel.getOpModelByName("MCiPerson")) != null) {
			// 如何mobilePhone字段有值，代表是虚拟银行传过来的。
			mobilePhone = mciperson.getMobilePhone() == null ? "" : mciperson
					.getMobilePhone().toString();
			if ((mcibelongmanager = (MCiBelongManager) writeModel
					.getOpModelByName("MCiBelongManager")) != null) {
				belongManager = mcibelongmanager.getCustManagerNo() == null ? ""
						: mcibelongmanager.getCustManagerNo().toString();
			}
			if (null != mobilePhone && !mobilePhone.equals("")) {
				List result = baseDAO
						.findWithIndexParam(
								"FROM MCiContmeth C WHERE C."
										+ MdmConstants.CUSTID
										+ "=? AND C.contmethInfo=? AND C.contmethType='213' ",
								""
										+ ecifData
												.getCustId(MdmConstants.CUSTID_TYPE),
								mobilePhone.trim());
				if (null == result || result.size() == 0) {
					// 联系信息表中不存在该客户的手机号码，需新增到联系信息表中CONTMETH_TYPE =
					// '213'(虚拟银行手机号码)
					String insertSql = "insert into m_Ci_Contmeth(CONTMETH_ID,IS_PRIORI,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,STAT,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER) values (SEQ_CONTMETH_ID.Nextval,'9',?,'213',?,'1','VB',sysdate,?) ";
					baseDAO.batchExecuteNativeWithIndexParam(insertSql,
							ecifData.getCustId(MdmConstants.CUSTID_TYPE),
							mobilePhone.trim(), belongManager);
				}
			}
		}
		// add end
	}
}
