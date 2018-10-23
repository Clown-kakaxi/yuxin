package com.yuchengtech.bcrm.oneKeyAccount.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.model.OcrmSysLookupItem;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmFSysAcchk;
import com.yuchengtech.bcrm.oneKeyAccount.entity.OcrmOAccountType;
import com.yuchengtech.bcrm.oneKeyAccount.model.ICCardSysVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.NetBankAccountChannelVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.NetBankAccountVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.NetCheckVO;
import com.yuchengtech.bcrm.oneKeyAccount.model.TelBlackListVO;
import com.yuchengtech.bcrm.product.model.OcrmFCiCustFitProd;
import com.yuchengtech.bcrm.trade.model.TxLog;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.dataauth.model.AuthSysFilterAuth;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.oneKeyOpen.ChinaTelBlackOrderCheckTranscation;
import com.yuchengtech.trans.impl.oneKeyOpen.CustomNameCardTransaction;
import com.yuchengtech.trans.impl.oneKeyOpen.NetBankAccountTransaction;
import com.yuchengtech.trans.impl.oneKeyOpen.NetContactCheckTransaction;
import com.yuchengtech.trans.impl.oneKeyOpen.NormalCardTranscation;
import com.yuchengtech.trans.inf.Transaction;

@Service
public class OneKeyAccountService extends CommonService {
	private static Logger log = LoggerFactory
			.getLogger(OneKeyAccountService.class);
	public OneKeyAccountService() {
		JPABaseDAO<OcrmFCiCustFitProd, String> baseDAO = new JPABaseDAO<OcrmFCiCustFitProd, String>(
				OcrmFCiCustFitProd.class);
		super.setBaseDAO(baseDAO);
	}
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	/**
	 * 开通定制姓名卡
	 * @param vo
	 * @return
	 */
	public Map<String, Object> accountReserceCard(ICCardSysVO vo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String seq = getSeqNo(); //获取6位序列
			String sysDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			if(StringUtils.isEmpty(seq)){
				String logMsg = "生成交易流水号失败!";
				Log.error(logMsg);
				retMap.put("status", "error");
				retMap.put("msg", logMsg);
				return retMap;
			}
			vo.setSerialNo(seq);
			vo.setDealNo("CRM00002");
//			vo.setAccountNo("50300003900001973");
			vo.setDealTime(sysDate);
			//测试时写死机构号
			vo.setBranchNo(auth.getUnitId());
			vo.setBranchNo(auth.getUnitId() + "000");
			//502
//			vo.setBranchNo("5020000");
			String userId = auth.getUserId();
			userId = userId.substring(0,  3) + userId.substring(4, 8);
			vo.setOperatorNo(userId);
			vo.setClientNo(userId);
			//vo.setSpeCardType("1");
			String[] cols = { "交易代码", "流水号", "渠道", "账户", "交易时间", "凸印姓名", "卡产品",
					"分行", "操作员", "终端号",
					"ATM日累计转账最高限额", "ATM日累计转账最高笔数", "ATM年累计转账最高限额 ", "POS单笔消费限额"};
			Integer[] lens = { 10, 6, 3, 20, 8, 30, 3, 6, 7, 7, 40, 40, 40, 40 };// 字段长度
			String[] vals = {vo.getDealNo(), vo.getSerialNo(), vo.getChannelNo(), vo.getAccountNo(), 
							vo.getDealTime(), vo.getCustNm(), vo.getSpeCardType(), vo.getBranchNo(), 
							vo.getOperatorNo(),vo.getClientNo(),
							vo.getLMTAMT_D_ATM(), vo.getLMTCNT_D_ATM(), vo.getLMTAMT_Y_ATM(), vo.getLMTAMT_POS()};
			Map<String, Object> paramMap = spliceMessage(1, cols, lens, vals);
			if(paramMap != null && !paramMap.isEmpty()){
				String param = (String) paramMap.get("msg");
				param = String.format("%04d", param.getBytes("GBK").length) + param;//卡系统报文
				param = String.format("%08d", param.getBytes("GBK").length) + param;//EAI报文
				TxData txData = new TxData();//交易处理数据
				txData.setReqMsg(param);
				Transaction trans = new CustomNameCardTransaction(txData);
				trans.process();
				TxLog txLog = trans.getTxLog();
				this.baseDAO.save(txLog);
//				retMap = onekeyMsgAnalysisService.analysisReserceICCardAccountMsg(resultMsg);
				retMap = txData.getTxMap();
				String txStatus = txData.getStatus();
				if(!StringUtils.isEmpty(txStatus) && txStatus.equals("success")){
					//更新客户信息
					String cardNo = txData.getAttribute("cardNo").toString();
					String upJql = "update OcrmFCiOpenInfo t set t.cardNo=:cardNo where t.accNo=:accNo";
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("cardNo", cardNo);
					params.put("accNo", vo.getAccountNo());
					this.baseDAO.batchExecuteWithNameParam(upJql, params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "系统错误，请联系管理员";
			Log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
		}
		return retMap;
	}
	

	// 卡系统报文生成
		@SuppressWarnings("unused")
		public Map<String, Object> getCardSystemReportParam(ICCardSysVO cvo){ 
			TxData txData = new TxData();
			Map<String, Object> txMap = txData.getTxMap();
			
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String seq = getSeqNo(); //获取6位序列
			String date = new SimpleDateFormat("MMddHHmmss").format(new Date());
			String sysDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String serialTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			cvo.setDealNo("CRM00001");
			if(StringUtils.isEmpty(seq)){
				String logMsg = "生成交易流水号失败!";
				Log.error(logMsg);
				txMap.put("status", "failure");
				txMap.put("msg", logMsg);
				return txMap;
			}
			cvo.setSerialNo(seq);
			cvo.setChannelNo("CRM");
			cvo.setDealTime(sysDate);
			cvo.setBranchNo(auth.getUnitId()+"000");//"501000"  --auth.getUnitId()
			String authNo = auth.getUserId();
			if(!StringUtils.isEmpty(authNo) && authNo.length() >= 8){
				authNo = authNo.substring(0, 3) + authNo.substring(4, 8);
			}
			cvo.setOperatorNo(authNo);//D5010A25 --auth.getUserCode()
			cvo.setClientNo(authNo);//501N8888//"D5010A25"
			String[] cols = { "交易代码", "流水号", "渠道", "账户", "交易时间", "卡号", "主卡卡号",
					"卡序列号", "数据来源", "二磁道信息", "55域", "分行", "操作员", "终端号",
					"ATM日累计转账最高限额", "ATM日累计转账最高笔数", "ATM年累计转账最高限额 ", "POS单笔消费限额"};
			Integer[] lens = { 10, 6, 3, 20, 8, 19, 19, 2, 1, 37, -1, 6, 7, 7, 40, 40, 40, 40};// 字段长度
			String[] vals = { cvo.getDealNo(), cvo.getSerialNo(),
					cvo.getChannelNo(), cvo.getAccountNo(), cvo.getDealTime(),
					cvo.getCardNo(), cvo.getMainCardNo(),
					cvo.getCardSerialNo(), cvo.getDataSource(),
					cvo.getSecondTrackInfo(), cvo.getUnionpay55Info(),
					cvo.getBranchNo(), cvo.getOperatorNo(), cvo.getClientNo(),
					cvo.getLMTAMT_D_ATM(), cvo.getLMTCNT_D_ATM(), cvo.getLMTAMT_Y_ATM(), cvo.getLMTAMT_POS()};// 对应值
			Map<String, Object> paramMap = spliceMessage(1, cols, lens, vals);
			String param = (String) paramMap.get("msg");
			int l_param = param.length() + 16;
			if (l_param > 0) {
				if (l_param < 10) {
					param = "000" + l_param + param.toString();
				} else if (l_param < 100) {
					param = "00" + l_param + param.toString();
				} else if (l_param < 1000) {
					param = "0" + l_param + param.toString();
				} else if (l_param < 10000) {
					param = "0" + l_param + param.toString();
				}
			}

//			param += cvo.getPswCode();//cvo.getPsw().getBytes();//添加密码
			param += cvo.getPinPsw();//cvo.getPsw().getBytes();//添加密码
/*			try {
				param += new String(cvo.getPswCode(), "GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			//cvo.getPsw().getBytes();//添加密码
			//添加密码
//			int EAIMsgLen = l_param + 4 + 8;
			int EAIMsgLen = param.getBytes().length + 8;//改为16进制密码后多加了8位
//			int EAIMsgLen = param.getBytes().length;//
			if(EAIMsgLen > 0){
				if(EAIMsgLen < 10){
					param = "0000000" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 100){
					param = "000000" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 1000){
					param = "00000" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 10000){
					param = "0000" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 100000){
					param = "000" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 1000000){
					param = "00" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 10000000){
					param = "0" + EAIMsgLen + "" + param;
				}else if(EAIMsgLen < 100000000){
					param = "" + EAIMsgLen + "" + param;
				}else{
					String logMsg = "请求报文内容长度超长，无法发送请求";
					Map<String, Object> retMap = new HashMap<String, Object>();
					retMap.put("status", "error");
					retMap.put("msg", logMsg);
					return retMap;
				}
			}
			
			txData.setReqMsg(param);
			Transaction trans = new NormalCardTranscation(txData);
			trans.process();
			TxLog txLog = trans.getTxLog();
			this.baseDAO.save(txLog);
			
			txMap.put("accountCardNo", cvo.getCardNo());
			String txStatus = txData.getStatus();
			String txReString = txData.getTxResult();
			if(!StringUtils.isEmpty(txStatus) && txStatus.equals("failure")){//密码强度过低
				txMap.put("status", "failure");
				txMap.put("msg", txData.getTxRtnMsg());
			}else if(!StringUtils.isEmpty(txStatus) && txStatus.equals("success")){
				//更新客户信息
				String upJql = "update OcrmFCiOpenInfo t set t.cardNo=:cardNo where t.accNo=:accNo";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("cardNo", cvo.getCardNo());
				params.put("accNo", cvo.getAccountNo());
				this.baseDAO.batchExecuteWithNameParam(upJql, params);
			}
			return txMap;
		}

	// 联网核查测试接口
	public Map<String, Object> checkNetWork(NetCheckVO vo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String[] cols = { "请求类型", "业务种类", "核对机构代码", "操作用户", "被核对人姓名", "身份证号码" };
			Integer[] lens = { 4, 2, 12, 40, 30, 18 };// 字段长度
			vo.setBusinessCode("01");
			vo.setBankCode(auth.getUnitId());
			vo.setUserCode(auth.getUserId());
			String[] vals = { vo.getMESID(), vo.getBusinessCode(),
					vo.getBankCode(), vo.getUserCode(), vo.getName(), vo.getID() };
			String param = this.getString(2, cols, lens, vals);// 2表示联网核查系统
			Map<String, Object> spliceMsg = this.spliceMessage(2, cols, lens, vals);
			param = (String) spliceMsg.get("msg");
			TxData txData = new TxData();
			txData.setReqMsg(param);//添加请求报文
			Transaction trans = new NetContactCheckTransaction(txData);
			trans.process();
			TxLog txLog = trans.getTxLog();
			this.baseDAO.save(txLog);
			retMap = txData.getTxMap();
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("status", "error");
			retMap.put("msg", e.getMessage());
			return retMap;
		}
		return retMap;
	}

	// 网银系统xml报文
		public Map<String, Object> getCyberBankReportParam(NetBankAccountVO vo) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			try {
				AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				List<AuthSysFilterAuth> authInfos = auth.getAuthInfos();
				long userSeq = 0;//
				if(authInfos != null && authInfos.size() >= 1){
					AuthSysFilterAuth authSysFilterAuth = authInfos.get(0);
					userSeq = authSysFilterAuth.getId();
				}
				String _UserSeq = auth.getUserId();
				_UserSeq = _UserSeq.substring(0, 3) + _UserSeq.substring(4, 8);
//				_UserSeq = "5020521";//
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				//处理证件类型转换
				String idTypeSql = "select T.REL2 FROM OCRM_F_SYS_ACCHK T WHERE T.REL1='"+vo.getIdType()+"' AND T.CHK_TYPE='CC3'";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("idType", vo.getIdType());
//				List<Object> idTypeList = this.baseDAO.findByNativeSQLWithIndexParam(idTypeSql, vo.getIdType(), null);
				List<Object> idTypeList = this.baseDAO.findByNativeSQLWithNameParam(idTypeSql, null);
				vo.setIdType("P99");//没有匹配到则传P99
				if(idTypeList != null && idTypeList.size() == 1){
					Object oIdTypeWY = idTypeList.get(0);
					if(oIdTypeWY != null && !oIdTypeWY.toString().equals("")){
						vo.setIdType(oIdTypeWY.toString());
					}
				}
				StringBuffer requestMsg = new StringBuffer();
				requestMsg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				requestMsg.append("  <Message>\n");
				requestMsg.append("    <Head>\n");
				requestMsg.append("      <_TransactionId>per.MCMSignCifApply</_TransactionId>\n");// 交易码
				requestMsg.append("      <_MCHTimestamp>" + time + "</_MCHTimestamp>\n");// 时间
				requestMsg.append("      <_MChannelId>CRM</_MChannelId>\n");// 渠道
				requestMsg.append("      <_MChannelCrm>CRM</_MChannelCrm>\n");// 渠道2(EAI要求加，做逻辑处理)
				requestMsg.append("      <_LoginType>R</_LoginType>\n");// 登陆类型
				requestMsg.append("      <_DeptSeq>"+auth.getUnitId()+"</_DeptSeq>\n");// 机构序号
				requestMsg.append("      <_CifSeq>-1</_CifSeq>\n");// 客户序号
				requestMsg.append("      <_UserId>"+_UserSeq+"</_UserId>\n");// 用户序号
				requestMsg.append("      <_MCHJnlNo>"+userSeq+"</_MCHJnlNo>\n");// 渠道流水号
				requestMsg.append("      <_locale>zh_CN</_locale>\n");// 标准输出
				requestMsg.append("      <_PreCheck>true</_PreCheck>\n");//
				requestMsg.append("      <_AccessIp>127.0.0.1</_AccessIp>\n");// 当前IP
				requestMsg.append("      <_AccessHost>127.0.0.1</_AccessHost>\n");// 主机IP
				requestMsg.append("    </Head>\n");
				requestMsg.append("    <Body>\n");
				requestMsg.append("      <PwdType>02</PwdType>\n");// 密码获取方式
				requestMsg.append("      <StrLocale>zh_CN</StrLocale>\n");//
				requestMsg.append("      <PmbsTelNo>"+vo.getPmbsTelNo()+"</PmbsTelNo>\n");//手机银行签约手机号码
				requestMsg.append("      <PmbsTelHead></PmbsTelHead>\n");//住宅电话
				requestMsg.append("      <PmbsAuthMod></PmbsAuthMod>\n");//是否开通短信验证
				requestMsg.append("      <PmessOpenFlag>1</PmessOpenFlag>\n");//短信业务状态
				requestMsg.append("      <RmbAmount>0</RmbAmount>\n");//短信通知人民币起始金额
				requestMsg.append("      <ForeignAmount>0</ForeignAmount>\n");//短信通知外币起始金额
				requestMsg.append("      <StartTime>00:00</StartTime>\n");//每日短信起始时间
				requestMsg.append("      <StopTime>23:59</StopTime>\n");//每日短信结束时间
				requestMsg.append("      <MatureNoticeDays>7</MatureNoticeDays>\n");//到期提前通知天数
				requestMsg.append("      <DayPerLimit>"+vo.getDayPerLimit()+"</DayPerLimit>\n");//人民币日累计转账限额
				requestMsg.append("      <DayTransTimes>"+vo.getDayTransTimes()+"</DayTransTimes>\n");//人民币日累计转账笔数
				requestMsg.append("      <LimitPerYear>"+vo.getLimitPerYear()+"</LimitPerYear>\n");//人民币年累计转账限额
				requestMsg.append("      <Person>\n");//电子银行客户信息
				requestMsg.append("        <seq></seq>\n");//客户序列号
				requestMsg.append("        <cifNo>"+vo.getCifNo()+"</cifNo>\n");//核心客户号
				requestMsg.append("        <cifType>"+vo.getCifType()+"</cifType>\n");//客户类别
				requestMsg.append("        <birthDate>"+vo.getBirthDate()+"</birthDate>\n");//出生日期
				requestMsg.append("        <CifLevel></CifLevel>\n");//客户等级
				requestMsg.append("        <Country></Country>\n");//国家
				requestMsg.append("        <Education></Education>\n");//学历
				requestMsg.append("        <Vocation></Vocation>\n");//职业
				requestMsg.append("      </Person>\n");//
				requestMsg.append("      <BankPrdSetList></BankPrdSetList>\n");//**	
				requestMsg.append("      <User>\n");//
				requestMsg.append("        <Country></Country>\n");//
				requestMsg.append("        <UserCert></UserCert>\n");//
				requestMsg.append("      </User>\n");//
				requestMsg.append("      <UserChannel></UserChannel>\n");//用户渠道  
				requestMsg.append("      <TelList>\n");//联系电话列表
				requestMsg.append("        <Telephone>\n");//联系电话
				requestMsg.append("          <telType>"+vo.getTELType()+"</telType>\n");//电话类型
				requestMsg.append("          <telNo>"+vo.getTELNo()+"</telNo>\n");//电话号码
				requestMsg.append("          <telHead></telHead>\n");//业务电话号码
				requestMsg.append("          <telAuthFlg>Z</telAuthFlg>\n");//验证标识
				requestMsg.append("        </Telephone>\n");//
				requestMsg.append("      </TelList>\n");//
				requestMsg.append("      <EAddrList>\n");//电子通讯地址列表
				requestMsg.append("        <EAddress>\n");//
				requestMsg.append("          <eAddrType>"+vo.getEAddrType()+"</eAddrType>\n");//地址类型
				requestMsg.append("          <eAddr>"+vo.getEAddr()+"</eAddr>\n");//地址
				requestMsg.append("        </EAddress>\n");//
				requestMsg.append("      </EAddrList>\n");//
				requestMsg.append("      <AddrList>\n");//地址列表
				requestMsg.append("        <Address>\n");//
				if(!StringUtils.isEmpty(vo.getHomeAddr())){
					requestMsg.append("          <addrType>H</addrType>\n");//地址类型
					requestMsg.append("          <addr>" + vo.getHomeAddr() + "</addr>\n");//地址
					if(!StringUtils.isEmpty(vo.getPostZipCode())){
						requestMsg.append("          <postCode>" + vo.getPostZipCode() + "</postCode>\n");//邮编
					}
				}
				requestMsg.append("        </Address>\n");//
				requestMsg.append("      </AddrList>\n");//
				requestMsg.append("      <MCList>\n");//开通渠道列表
				StringBuilder sb_AuthModsMchList = new StringBuilder();
				List<NetBankAccountChannelVO> channelList = vo.getChannelList();
				boolean hadPIBS = false;
				boolean hadPMBS = false;
				if(channelList != null && channelList.size() > 0){
					for (int i = 0; i < channelList.size(); i++) {
						//CIFChannel
						NetBankAccountChannelVO netBankAccountChannelVO = channelList.get(i);
						String MChannelId = netBankAccountChannelVO.getMChannelId();
						if(!StringUtils.isEmpty(MChannelId)){
							if(MChannelId.equals("PIBS") && !hadPIBS){
								hadPIBS = true;
								requestMsg.append("        <CIFChannel>\n");//客户渠道
								requestMsg.append("          <mChannelId>"+netBankAccountChannelVO.getMChannelId()+"</mChannelId>\n");//模块渠道
								requestMsg.append("          <state>N</state>\n");//模块渠道状态
								requestMsg.append("          <Employee></Employee>\n");//客户经理信息
								requestMsg.append("          <CifLimitList></CifLimitList>\n");//
								String str_mchMobilePhone = netBankAccountChannelVO.getMchMobilePhone();
								if(str_mchMobilePhone != null && !str_mchMobilePhone.equals("")){
									requestMsg.append("          <UserChannels>\n");//
									requestMsg.append("          		<UserChannel>\n");//
									requestMsg.append("          			<mchMobilePhone>"+str_mchMobilePhone+"</mchMobilePhone>\n");//手机号
									requestMsg.append("          			<mchMobilePhoneHead></mchMobilePhoneHead>\n");//电话号码
									requestMsg.append("          		</UserChannel>\n");//
									requestMsg.append("          </UserChannels>\n");//
								}
								requestMsg.append("          <FuncGroupList></FuncGroupList>\n");//
								requestMsg.append("        </CIFChannel>\n");//
							}else if(MChannelId.equals("PMBS") && !hadPMBS){
								hadPMBS = true;
								requestMsg.append("        <CIFChannel>\n");//客户渠道
								requestMsg.append("          <mChannelId>"+netBankAccountChannelVO.getMChannelId()+"</mChannelId>\n");//模块渠道
								requestMsg.append("          <state>N</state>\n");//模块渠道状态
								requestMsg.append("          <Employee></Employee>\n");//客户经理信息
								requestMsg.append("          <CifLimitList></CifLimitList>\n");//
								String str_mchMobilePhone = netBankAccountChannelVO.getMchMobilePhone();
								if(str_mchMobilePhone != null && !str_mchMobilePhone.equals("")){
									requestMsg.append("          <UserChannels>\n");//
									requestMsg.append("          		<UserChannel>\n");//
									requestMsg.append("          			<mchMobilePhone>"+str_mchMobilePhone+"</mchMobilePhone>\n");//手机号
									requestMsg.append("          			<mchMobilePhoneHead></mchMobilePhoneHead>\n");//电话号码
									requestMsg.append("          		</UserChannel>\n");//
									requestMsg.append("          </UserChannels>\n");//
								}
								requestMsg.append("          <FuncGroupList></FuncGroupList>\n");//
								requestMsg.append("        </CIFChannel>\n");//
							}
						}
						//AuthModsMchList
						sb_AuthModsMchList.append("      	<Map>\n");//
						sb_AuthModsMchList.append("      		<AuthMod>"+netBankAccountChannelVO.getAuthMod()+"</AuthMod>\n");//审核方式
						sb_AuthModsMchList.append("      		<MchannelId>"+netBankAccountChannelVO.getMChannelId()+"</MchannelId>\n");//渠道ID
						sb_AuthModsMchList.append("      		<State>N</State>\n");//渠道状态
						sb_AuthModsMchList.append("      	</Map>\n");//
					}
				}
				requestMsg.append("      </MCList>\n");//
				requestMsg.append("      <AcList></AcList>\n");//账户签约列表
				requestMsg.append("      <FeeDicountList></FeeDicountList>\n");//
				requestMsg.append("      <AuthModsMchList>\n");//
				requestMsg.append(sb_AuthModsMchList.toString());
				requestMsg.append("      </AuthModsMchList>\n");//
				requestMsg.append("      <AuthDisplay>\n");//
				requestMsg.append("      	<CifNo>"+vo.getCifNo()+"</CifNo>\n");//核心客户号
				requestMsg.append("      	<Name>"+vo.getCustName()+"</Name>\n");//客户姓名
				requestMsg.append("      	<IdType>"+vo.getIdType()+"</IdType>\n");//证件类型
				requestMsg.append("      	<IdNo>"+vo.getIdNo()+"</IdNo>\n");//证件号
				requestMsg.append("      	<CountryName></CountryName>\n");//
				requestMsg.append("      	<BirthDate>"+vo.getBirthDate()+"</BirthDate>\n");//生日日期
				requestMsg.append("      </AuthDisplay>\n");//
				requestMsg.append("    </Body>\n");//
				requestMsg.append("  </Message>\n");//
				String EAIMsg = requestMsg.toString();
				EAIMsg = String.format("%08d", EAIMsg.getBytes("UTF-8").length) + EAIMsg;//添加报文长度
//
//				int xmlMsgLen = requestMsg.toString().getBytes("UTF-8").length;
//				if(xmlMsgLen > 0){
//					if(xmlMsgLen < 10){
//						EAIMsg = "0000000" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 100){
//						EAIMsg = "000000" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 1000){
//						EAIMsg = "00000" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 10000){
//						EAIMsg = "0000" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 100000){
//						EAIMsg = "000" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 1000000){
//						EAIMsg = "00" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 10000000){
//						EAIMsg = "0" + xmlMsgLen + "" + EAIMsg;
//					}else if(xmlMsgLen < 100000000){
//						EAIMsg = "" + xmlMsgLen + "" + EAIMsg;
//					}
//				}
				TxData txData = new TxData();
				txData.setReqMsg(EAIMsg);//添加请求报文
				Transaction trans = new NetBankAccountTransaction(txData);
				trans.process();
				TxLog txLog = trans.getTxLog();
				this.baseDAO.save(txLog);
				retMap = txData.getTxMap();
				return retMap;
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("status", "error");
				retMap.put("msg", "系统错误，请联系管理员");
				return retMap;
			}
		}

	// 判断字符串的编码格式
	public String getEncoding(String arg) {
		String encode[] = new String[] { "UTF-8", "ISO-8859-1", "GB2312",
				"GBK", "GB18030", "Big5", "Unicode", "ASCLL" };
		for (int i = 0; i < encode.length; i++) {
			try {
				if (arg.equals(new String(arg.getBytes(encode[i]), encode[i]))) {
					return encode[i];
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 电信黑名单核查
	 * 
	 * @return
	 */
	public Map<String, Object> checkTelBlackList(TelBlackListVO vo) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Date currDate = new Date();
			String date = new SimpleDateFormat("yyyyMMdd").format(currDate);
			String time = new SimpleDateFormat("HHmmss").format(currDate);
			// vo.setJydm("200501");
			vo.setJyjg(auth.getUnitId());
			vo.setJygy(auth.getUserId());
			// vo.setQdbs("CRM");
			// vo.setYybs("sfck");
			// vo.setXtbh("gabdxzp");
			vo.setQdlsh("HYCRM" + auth.getUserCode() + date + time + "");
			vo.setQdrq(date);
			vo.setQdsj(time);
			// vo.setVer("2.5.5");
			// vo.setMdlx("200501");
			// vo.setSjlx("IDType_IDNumber");
			// vo.setYhbh("*");
			String[] cols = { "交易代码", "交易机构", "交易柜员", "渠道标识", "应用标识", "系统编号",
					"渠道流水号", "渠道日期", "渠道时间", "接口版本号", "名单类型", "数据类型", "银行编号",
					"数据项", "账户名" };
			Integer[] lens = { 6, 20, 10, 10, 10, 15, 40, 8, 6, 5, 6, 45, 12, 30,
					120 };
			String[] vals = { vo.getJydm(), vo.getJyjg(), vo.getJygy(),
					vo.getQdbs(), vo.getYybs(), vo.getXtbh(), vo.getQdlsh(),
					vo.getQdrq(), vo.getQdsj(), vo.getVer(), vo.getMdlx(),
					vo.getSjlx(), vo.getYhbh(), vo.getSjx(), "*" };
			Map<String, Object> spliceMsg = this.spliceMessage(4, cols, lens, vals);
			if (spliceMsg == null || spliceMsg.isEmpty()
					|| !spliceMsg.containsKey("state")
					|| !spliceMsg.containsKey("msg")) {
				retMap.put("status", "error");
				retMap.put("msg", "电信黑名单报文拼接失败，无法核查!!!");
				return retMap;
			}
			Object resultState = spliceMsg.get("state");
			if (resultState == null || resultState.equals("error")) {
				retMap.put("status", "error");
				retMap.put("msg", "电信黑名单报文拼接失败，无法核查!!!");
				return retMap;
			}else if (resultState.equals("success")) {
				String checkMsg = spliceMsg.get("msg").toString();
				int msgLen = checkMsg.length();
				if (msgLen > 0) {
					if (msgLen < 10) {
						checkMsg = "000" + msgLen + checkMsg;
					} else if (msgLen < 100) {
						checkMsg = "00" + msgLen + checkMsg;
					} else if (msgLen < 1000) {
						checkMsg = "0" + msgLen + checkMsg;
					}
				}
				TxData txData = new TxData();
				txData.setReqMsg(checkMsg);//添加交易请求报文
				Transaction trans = new ChinaTelBlackOrderCheckTranscation(txData);
				trans.process();
				retMap = txData.getTxMap();
				TxLog txLog = trans.getTxLog();
				this.baseDAO.save(txLog);
			}
			return retMap;
		
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("status", "error");
			retMap.put("msg", "响应报文为空");
			return retMap;
		}
	}
	
	
	/**
	 * 本地黑名单校验
	 * @param identNo	证件号码
	 * @return
	 */
	public Map<String, Object> checkLocalBlackList(String identNo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(identNo)){
			String logMsg = "没有证件号码，无法核查本地黑名单";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
		}
		String checkSql = "select t.SPECIAL_LIST_ID from acrm_f_ci_speciallist t "
				+ " where sysdate between nvl(t.start_date,sysdate)"
				+ " and nvl(t.end_date,sysdate)"
				+ " and t.special_list_flag='Y'"
				+ " and t.IDENT_NO ='"+identNo+"'";
		List<Object[]> resList = this.baseDAO.findByNativeSQLWithNameParam(checkSql, null);
		if(resList == null || resList.size() != 1){
			String logMsg = "本地黑名单校验通过";
			log.info(logMsg);
			retMap.put("status", "success");
			retMap.put("msg", logMsg);
		}else{
			String logMsg = "该客户在本行黑名单中";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			return retMap;
		}
		return retMap;
	}
	

   
	    
	   
	
	
	
	private String getString(int sysFlag, String[] cols, Integer[] lens,
			String[] vals) {
		StringBuffer str = new StringBuffer();
		if (cols.length == lens.length && lens.length == vals.length) {
			for (int i = 0; i < lens.length; i++) {
				StringBuffer sb = new StringBuffer(vals[i]);
				if (lens[i] >= vals[i].length()) {
					for (int j = 0; j < (lens[i] - vals[i].length()); j++) {
						sb.append(" ");
					}
				} else {
					return this.selectSystemName(sysFlag) + "【" + cols[i]
							+ "】字段值超出长度！";
				}
				str.append(sb.toString());
			}
		} else {
			return this.selectSystemName(sysFlag) + "传入参数有误！";
		}
		return str.toString();
	}

	/**
	 * 拼接报文
	 * 
	 * @param sysFlag
	 *            报文归属系统标识
	 * @param cols
	 *            各字段名称
	 * @param lens
	 *            各字段长度（-1表示变长）
	 * @param vals
	 *            各字段内容
	 * @return
	 */
	private Map<String, Object> spliceMessage(int sysFlag, String[] cols,
			Integer[] lens, String[] vals) {
		Map<String, Object> retMap = new HashMap<String, Object>();//
		StringBuffer str = new StringBuffer();
		if (cols == null || cols.length < 1 || lens == null || lens.length < 1
				|| vals == null || vals.length < 1) {
			retMap.put("state", "error");
			retMap.put("msg", this.selectSystemName(sysFlag) + "传入参数有误，请检查后重试");
			return retMap;
		}
		if (cols.length == lens.length && lens.length == vals.length) {
			for (int i = 0; i < lens.length; i++) {
				String currField = vals[i];// 当前字段内容
				if (currField == null) {
					str.append("");
					continue;
				}
				byte[] b_currField = new byte[currField.length()];
				try {
					b_currField = currField.getBytes("GBK");
				} catch (Exception e) {
					e.printStackTrace();
				}
				int fieldLen = b_currField.length;// 当前字段长度
				int oriLen = lens[i];// 字段限定长度 -1表示变长
				// System.out.println("字段【"+cols[i]+"】限定长度：【"+lens[i]+"】\n字段内容：【"+vals[i]+"】\n字段内容长度：【"+fieldLen+"】");
				if (oriLen == -1) {// 变长时前面加上三位的字段内容长度
					if (fieldLen > 0) {
						if (fieldLen < 10) {
							currField = "00" + fieldLen + "" + currField;
						} else if (fieldLen < 100) {
							currField = "0" + fieldLen + "" + currField;
						} else if (fieldLen < 1000) {
							currField = "" + fieldLen + "" + currField;
						}
					}
				} else {
					if (fieldLen < lens[i]) {
						for (int m = 0; m < (lens[i] - fieldLen); m++) {
							currField += " ";
						}
					}
				}
				str.append(currField);
			}
			retMap.put("state", "success");
			retMap.put("msg", str.toString());
		} else {
			retMap.put("state", "error");
			retMap.put("msg", this.selectSystemName(sysFlag) + "字段个数与字段长度个数不一致，请检查后重试");
			return retMap;
		}
		return retMap;
	}

	public String selectSystemName(int key) {
		String str = "";
		switch (key) {
		case 1:
			str = "卡系统";
			break;
		case 2:
			str = "联网核查系统";
			break;
		case 3:
			str = "核心系统";
			break;
		case 4:
			str = "电信黑名单";
			break;
		default:
			str = "未知系统";
			break;
		}
		return str;
	}

	@SuppressWarnings("unchecked")
	public List<List<String>> getDimDate(String fcodes) {
		List<List<String>> rstList = new ArrayList<List<String>>();
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('"
				+ fcodes + "')";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000040");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		// Query query = em.createQuery(jql);
		// query.setParameter("fLookupId", "XD000040");
		// List<OcrmSysLookupItem> items = query.getResultList();
		if (null != items && items.size() > 0) {
			for (OcrmSysLookupItem item : items) {
				List<String> str = new ArrayList<String>();
				str.add(item.getFCode());
				str.add(item.getFValue());
				rstList.add(str);
			}
		}
		return rstList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDimZjlx(String fcodes) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('1','2','3','7','8','X3','0','5','6','X5','X14','X24')";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000040");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				
				list.add(retmap);
			}
		}
		return list;
	}
	
	/**
	 * 查询标准码表信息(境内外标志)，以及对应的账户类型
	 * @return JSONObject {"code":"境内外类型编号","value":"境内外类型名称","items":[{"code":"账户类型","value":"账户类型名称"}]}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAccountTypeData() {
		// 查询境内外标志码表数据
		JSONObject jso_jnw = new JSONObject();
		String sql = "select F_VALUE,F_CODE from OCRM_SYS_LOOKUP_ITEM where F_LOOKUP_ID = 'XD000022'";
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		if (list != null && list.size() >= 1) {
			for (int i = 0; i < list.size(); i++) {
				Object[] o_l = list.get(i);// 境内
				JSONObject jso_jnwChild = new JSONObject();
				jso_jnwChild.put("value", o_l[0].toString());
				jso_jnwChild.put("code", o_l[1].toString());
				jso_jnw.put(o_l[1].toString(), jso_jnwChild);
			}
			String jql_OcrmOAccountType = "select it from OcrmOAccountType it";
			List<OcrmOAccountType> findWithIndexParam = this.baseDAO.findWithIndexParam(jql_OcrmOAccountType);
			if (findWithIndexParam != null && findWithIndexParam.size() >= 1) {
				for (int i = 0; i < findWithIndexParam.size(); i++) {
					OcrmOAccountType ocrmOAccountType = findWithIndexParam.get(i);
					String s_side = ocrmOAccountType.getSide();
					JSONObject jso_ocrmOAccountType = new JSONObject();
					jso_ocrmOAccountType.put("value", ocrmOAccountType.getAccountname());
					jso_ocrmOAccountType.put("code", ocrmOAccountType.getAccount());
					if (s_side != null && !s_side.equals("")) {
						if (jso_jnw.containsKey(s_side)) {// D F
							JSONObject optJSONObject = jso_jnw.optJSONObject(s_side);
							if (optJSONObject.containsKey("items")) {
								JSONArray jsa_items = optJSONObject.optJSONArray("items");
								jsa_items.add(jso_ocrmOAccountType);
							} else {
								JSONArray jsa_items = new JSONArray();
								jsa_items.add(jso_ocrmOAccountType);
								optJSONObject.put("items", jsa_items);
							}
						}
					}
				}
			}
		}
		return jso_jnw;
	}
	
	
	/**
	 * 16进制字符串转为ASCLL码
	 * @param hex
	 * @return
	 */
	public String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}
	
	/**
	 * 卡类型下拉列表
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCardType(String fCode) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "";
		if(fCode != null){
			if(fCode.equals("1")){//基础卡
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('1','2','3')";
			}else if(fCode.equals("0")){//特色卡
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('001')";
			}else{
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and 1=2";
			}
		}else{
			jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and 1=2";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "CARD_TYPE");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	
	
	/**
	 * 卡样式
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCardType2(String fCode) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "";
		if(fCode != null){
			if(fCode.equals("1")||fCode.equals("2")||fCode.equals("3")){
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('1001','1002')";
			}else if(fCode.equals("001")){
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('0011','0012')";
			}else{ 	 	
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and 1=2";
			}
		}else{
			jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and 1=2";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "CARD_FC");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	
	
	/**
	 * 来源渠道下拉列表
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getChannels() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('28','05','14','99','10')";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000353");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	
	/**
	 * 对私联系信息类型下拉列表
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getContmethTypes() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('204','203','999')";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "CONTMETH_TYPES");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	
	/**
	 * 国籍下拉列表
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNationNalityList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId order by it.fId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000025");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	/**
	 * 国际区号
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getglobalRoamingStore() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId order by it.fId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "AREA_CODE");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	
	
	
	/**
	 * 发证机关所在地下拉列表
	 * @param fcodes
	 * @return
	 */
	public List<Map<String, Object>> getOrgRegionList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select  REGION_NAME from  OCRM_F_CI_IDENT_ORG_REGION group by  REGION_NAME ";
		List tempList = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);
		if(tempList != null && tempList.size() > 0){
			for (int i = 0; i < tempList.size(); i++) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", tempList.get(i));
				retmap.put("value", tempList.get(i));
				list.add(retmap);
			}
		}
		return list;
	}
	
	/**
	 * 风险国别下拉列表
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRiskCountryList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId order by it.fId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000369");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}
	
	/**
	 * 获取IC卡具的端口
	 * 2017-11-21
	 * @return
	 */
	public Map<String, Object> getICPort(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		String IcPortSql = "select * from Ocrm_sys_lookup_item t where t.f_lookup_id='XD000370'";
		List<Object[]> portList = this.baseDAO.findByNativeSQLWithIndexParam(IcPortSql, null);
		if(portList != null && portList.size() >= 1){
			Object[] os = portList.get(0);
			if(os != null && os.length >= 3){
				String IcPort = os[2] == null ? "2" : os[2].toString();
				retMap.put("IcPort", IcPort);
			}
		}
		return retMap;
	}
	
	/**
	 * 证件、境内外、账户类型的校验规则
	 * @return Map key为所有的类型1，key对应的value为key对应的类型 1下所有类型2的集合
	 */
	public Map<String, Object> getIdentAccountTypeList(String chkType){
		String jql = "select it from OcrmFSysAcchk it where it.chkType =:chkType";
		Map paraMap = new HashMap();
		paraMap.put("chkType", chkType);
		List<OcrmFSysAcchk> ocrmFSysAacchkList = this.findByJql(jql, paraMap);
		Map<String, Object> relMap = new HashMap<String, Object>();
		if(ocrmFSysAacchkList != null && !ocrmFSysAacchkList.isEmpty()){
			for (OcrmFSysAcchk ocrmFSysAcchk:ocrmFSysAacchkList) {
				Map<String,Object> resultMap = new HashMap<String, Object>();
				String rel1 = ocrmFSysAcchk.getRel1();
				String rel2 = ocrmFSysAcchk.getRel2();
				if(relMap.containsKey(rel1)){
					List<String> relList = (List<String>) relMap.get(rel1);
					if(!relList.contains(rel2)){
						relList.add(rel2);
					}
				}else{
					List<String> relList = new ArrayList<String>();
					relList.add(rel2);
					relMap.put(rel1, relList);
				}
			}
		}
		return relMap;
	}
	
	/**
	 * 获取页面卡类型和卡号的对应关系
	 * @return
	 */
	public Map<String, Object> getCardTypeCheckRelationship(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		//查询数据库中两者对应关系
		String sql = "select t.rel1 rel1,t.rel2 rel2 from OCRM_F_SYS_ACCHK t where t.CHK_TYPE='CC'";
		List<Object[]> resList = this.baseDAO.findByNativeSQLWithNameParam(sql, null);
		if(resList == null || resList.size() < 1){
			String logMsg = "没有查询到卡类型和卡号的关系，请联系管理员";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			return retMap;
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		for (int i = 0; i < resList.size(); i++) {
			Object[] os = resList.get(i);
			String rel1 = os[0].toString();
			String rel2 = os[1].toString();
			resMap.put(rel1, rel2);
		}
		retMap.put("status", "success");
		retMap.put("result", resMap);
		return retMap;//
	}
	
	
	/**
	 * 获取开通卡时需要的信息
	 * @return
	 */
	public Map<String, Object> getCardOpenInfo(){
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取数据库中的设备端口号
		String IcPortSql = "select * from Ocrm_sys_lookup_item t where t.f_lookup_id='XD000370'";
		List<Object[]> portList = this.baseDAO.findByNativeSQLWithIndexParam(IcPortSql, null);
		if(portList != null && portList.size() >= 1){
			Object[] os = portList.get(0);
			if(os != null && os.length >= 3){
				String IcPort = os[2] == null ? "2" : os[2].toString();
				retMap.put("IcPort", IcPort);
			}
		}
		//查询数据库中两者对应关系
		String sql = "select t.rel1 rel1,t.rel2 rel2 from OCRM_F_SYS_ACCHK t where t.CHK_TYPE='CC'";
		List<Object[]> resList = this.baseDAO.findByNativeSQLWithNameParam(sql, null);
		if(resList == null || resList.size() < 1){
			String logMsg = "没有查询到卡类型和卡号的关系，请联系管理员";
			log.error(logMsg);
			retMap.put("status", "error");
			retMap.put("msg", logMsg);
			return retMap;
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		for (int i = 0; i < resList.size(); i++) {
			Object[] os = resList.get(i);
			String rel1 = os[0].toString();
			String rel2 = os[1].toString();
			resMap.put(rel1, rel2);
		}
		retMap.put("status", "success");
		retMap.put("cardTypeValid", resMap);
		return retMap;
	}
	
	/**
	 * 
	    * @Title: getSeqNo
	    * @Description: TODO(生成6位序列)
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public String getSeqNo(){
		String seq = "";
		String jpql = "select to_char(SEQ_ID_6.Nextval) from dual";
		List<String> seqList = this.baseDAO.findByNativeSQLWithNameParam(jpql, null);
		if(seqList != null && seqList.size() == 1){
			seq = seqList.get(0);
		}
		return seq;
	}
	
}
  