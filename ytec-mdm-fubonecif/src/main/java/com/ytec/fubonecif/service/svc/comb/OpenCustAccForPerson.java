/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.comb
 * @文件名：OpenCustAccForPerson.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:05:38
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.MCiBelongManager;
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：OpenCustAccForPerson
 * @类描述：个人客户开户
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:05:38
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:05:38
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenCustAccForPerson implements IEcifBizLogic {
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory
			.getLogger(OpenCustAccForPerson.class);

	//@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// TODO Auto-generated method stub
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
				itemp = new MCiIdentifier(ident.getIdentType(),
						ident.getIdentNo(), ident.getIdentCustName());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD,
						ident.getIdentType());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO,
						ident.getIdentNo());
				opMp.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME,
						ident.getIdentCustName());
				identList.add(itemp);
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
		// opMp.put("identList", identList);
		// opMp.put("ident", identList);

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
					//add by liuming 20170808
					savePhone(ecifData);
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
				// ecifData.getWriteModelObj().setResult(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO, ecifData.getEcifCustNo());
				ecifData.getWriteModelObj().setResult(
						MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO,
						ecifData.getCustId());
				String updateSql="update m_ci_person t set t.personal_name='"+custName+"' where t.cust_id='"+ecifData.getCustId()+"'";
				baseDAO.batchExecuteNativeWithIndexParam(updateSql,null);
				//add by liuming 20170808
				savePhone(ecifData);
				//开户成功
				try {
					callProcedure(generalInfoList);
				} catch (Exception e) {
					
					log.error("调用存储过程失败", e);
				}
				//baseDAO.flush();
				
			} catch (Exception e) {
				log.error("错误信息", e);
				ecifData.setStatus(ErrorCode.ERR_SERVER_BIZLOGIC_ERROR);
			}
		}
		return;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void savePhone(EcifData ecifData ){
		//add by liuming 20170808虚拟银行(手机号)增加手机号mobilePhone
		String mobilePhone = null;
		String belongManager =  null;
		WriteModel writeModel = ecifData.getWriteModelObj();
		MCiPerson mciperson = null;//个人客户信息
		MCiBelongManager mcibelongmanager =  null;//归属客户经理
		if ((mciperson = (MCiPerson) writeModel.getOpModelByName("MCiPerson")) != null) {
			//如何mobilePhone字段有值，代表是虚拟银行传过来的。
			mobilePhone = mciperson.getMobilePhone() == null ? "" : mciperson.getMobilePhone().toString();
			if((mcibelongmanager = (MCiBelongManager) writeModel.getOpModelByName("MCiBelongManager")) != null){
				belongManager = mcibelongmanager.getCustManagerNo() == null ? "" : mcibelongmanager.getCustManagerNo().toString();
			}
			if(null != mobilePhone && !mobilePhone.equals("")){
				List result = baseDAO.findWithIndexParam("FROM MCiContmeth C WHERE C." + MdmConstants.CUSTID + "=? AND C.contmethInfo=? AND C.contmethType='213' ",
						""+ecifData.getCustId(MdmConstants.CUSTID_TYPE),mobilePhone.trim());
				if(null == result || result.size() == 0){
					//联系信息表中不存在该客户的手机号码，需新增到联系信息表中CONTMETH_TYPE = '213'(虚拟银行手机号码)
					String insertSql="insert into m_Ci_Contmeth(CONTMETH_ID,IS_PRIORI,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,STAT,LAST_UPDATE_SYS,LAST_UPDATE_TM,LAST_UPDATE_USER) values (SEQ_CONTMETH_ID.Nextval,'9',?,'213',?,'1','VB',sysdate,?) ";
					baseDAO.batchExecuteNativeWithIndexParam(insertSql,ecifData.getCustId(MdmConstants.CUSTID_TYPE),mobilePhone.trim(),belongManager);
				}
			}
		}
		//add end
	}

}
