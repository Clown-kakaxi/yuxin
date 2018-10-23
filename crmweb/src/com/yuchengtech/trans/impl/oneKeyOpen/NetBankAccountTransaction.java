package com.yuchengtech.trans.impl.oneKeyOpen;

import java.io.StringReader;
import java.security.Provider;
import java.security.Security;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.csii.pe.common.util.CsiiUtils;
import com.csii.pe.security.EnDecrypt;
import com.csii.pe.security.EnDecryptFactory;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;

/**
 * 网银开户日志工具
 * @author Administrator
 *
 */
public class NetBankAccountTransaction extends BaseTransaction{
	
	private Logger log = LoggerFactory.getLogger(NetBankAccountTransaction.class);
	
	
	public NetBankAccountTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("CRM.NETBANK.PORT");
		this.setTransName("网银开户");
		this.setHost(FileTypeConstance.getBipProperty("CRM.NETBANK.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("UTF-8");
	}
	
	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("OpenNetBankAccount");
		txData.setTxName("OpenNetBankAccount");
		txData.setTxCnName("网银开户");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		//其他参数
		return txData;
	}

	@Override
	public TxData read(TxData txData) {
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String txStr = txData.getResMsg();
		if (StringUtils.isEmpty(txStr.trim())) {
			String logMsg = "响应报文为空";
			log.error(logMsg);
			txData = this.setTxResult(txData, "2", "", logMsg, "errorr", logMsg);
			return txData;
		}
		// dom4j解析xml格式的字符串
		txStr = txStr.substring(8);
		Document doc;
		try {
			doc = DocumentHelper.parseText(txStr);
//			SAXReader reader = new SAXReader();
//			StringReader sr = new StringReader(txStr);
//			InputSource is = new InputSource(sr);
//			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			Element ele_head = root.element("Head");
			Element e_RejCode = ele_head.element("_RejCode");
			String s_RejCode = e_RejCode.getTextTrim();// 返回码
			Element e_RejMsg = ele_head.element("_RejMsg");
			String s_RejMsg = e_RejMsg.getTextTrim();// 返回信息
			if (s_RejCode != null && s_RejCode.equals("000000")) {
				//解密密码
				Element ele_body = root.element("Body");
				//Password
				Element e_psw = ele_body.element("Password");
				if(e_psw != null){
					String str_psw = e_psw.getTextTrim();
					if(!StringUtils.isEmpty(str_psw)){
						String str_dePsw = this.decrypt(str_psw);
						txData.setAttribute("psw", str_dePsw);
						String logMsg = "开通网银成功";
						log.info(logMsg);
						txData = this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
					}else{
						String logMsg = "开通网银失败，返回密码为空";
						log.error(logMsg);
						txData = this.setTxResult(txData, "2", "", logMsg, "errorr", logMsg);
						return txData;
					}
				}else{
					String logMsg = "开通网银失败，返回报文中没有密码节点";
					log.error(logMsg);
					txData = this.setTxResult(txData, "2", "", logMsg, "errorr", logMsg);
					return txData;
				}
			} else {
				String logMsg = "网银开通失败，具体原因：" + s_RejMsg;
				log.error(logMsg);
				txData = this.setTxResult(txData, "2", "", logMsg,  "errorr", logMsg);
				return txData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String logMsg = "响应信息异常，请联系网银系统相关人员";
			log.error(logMsg);
			txData = this.setTxResult(txData, "2", "", logMsg,  "errorr", logMsg);
			return txData;
		}
		return txData;
	}

	/**
	 * 解密密码
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	private String decrypt(String pwd) throws Exception{
		Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
		if(provider == null){
			Security.addProvider(new BouncyCastleProvider());
		}
		byte[] key1Buffer = "123456781234567812345678".getBytes();
		byte[] initVector = "sPKdhdz3".getBytes();
		int key1Offset = 100;
		EnDecryptFactory.registerSecretKey(key1Offset, "DESede", key1Buffer);
		EnDecrypt enDecrypt = EnDecryptFactory.getInstance();
		byte[] textBuffer = enDecrypt.deCrypt("DESede", CsiiUtils.hexStr2Bytes(pwd),key1Offset, "CBC/PKCS5Padding", initVector);
		return new String(textBuffer);
	}
	
}
