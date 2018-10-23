package com.yuchengtech.trans.impl.oneKeyOpen;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.trans.bo.TxData;
import com.yuchengtech.trans.impl.BaseTransaction;

/**
 * 核心开户交易日志
 * @author Administrator
 *
 */
public class CBOpenAccountTransaction extends BaseTransaction{
	
	private Logger log = LoggerFactory.getLogger(CBOpenAccountTransaction.class);
	
	public CBOpenAccountTransaction(TxData txData) {
		this.txData = txData;
		String port = FileTypeConstance.getBipProperty("ECIF.PORT");
		this.setTransName("核心开户");
		this.setHost(FileTypeConstance.getBipProperty("ECIF.IP"));
		this.setPort(Integer.parseInt(port));
		this.setRequestCharSet("GBK");
	}

	@Override
	public TxData analysisResMsg(TxData txData) {
		txData = read(txData);
		txData.setTxFwId(this.getCurrTxTm());
		txData.setTxCode("CBOpenAccount");
		txData.setTxName("CBOpenAccount");
		txData.setTxCnName("核心开户");
		txData.setTxMethod("SOCKET");
		txData.setTxDt(new Date());
		txData.setSrcSysCd("CRM");
		txData.setSrcSysNm("CRM");
		//其他参数
		return txData;
	}

	@Override
	/**
	 * 核心开户响应报文解析
	 * 
	 * @param msg
	 * @return
	 */
	public TxData read(TxData txData) {
		if(!StringUtils.isEmpty(txData.getTxResult())){
			return txData;
		}
		String resp = txData.getResMsg();
		if(StringUtils.isEmpty(resp)){
			String logMsg = "响应报文为空";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
		resp = resp.substring(8);
		Document doc;
		try {
			doc = DocumentHelper.parseText(resp);
			Element root = doc.getRootElement();
			Element ele_tail = root.element("ResponseTail");
			if(ele_tail != null){
				String tail_TxStatCode = ele_tail.elementTextTrim("TxStatCode");
//				String tail_TxStatDesc = ele_tail.elementTextTrim("TxStatDesc");
//				String tail_TxStatString = ele_tail.elementTextTrim("TxStatString");
				//排除开户成功和客户已开户的情况
				if(!StringUtils.isEmpty(tail_TxStatCode) && !tail_TxStatCode.equals("000000") && !tail_TxStatCode.equals("0014")){
					String errMsg = getErrMsgByCode(tail_TxStatCode);//获取错误码对应的错误信息
//					tail_TxStatDesc = StringUtils.isEmpty(tail_TxStatDesc) ? "核心开户失败" : tail_TxStatDesc;
					log.error(errMsg);
					this.setTxResult(txData, "2", "", errMsg, "error", errMsg);
					return txData;
				}
			}
			Element eleHeader = root.element("ResponseHeader");
			//获取交易流水号
			String ReqSeqNo = eleHeader.elementTextTrim("ReqSeqNo");
			txData.setAttribute("ReqSeqNo", ReqSeqNo);
			String TxStatDesc = root.element("ResponseBody").elementTextTrim("TxStatDesc");
			String TxStatCode = root.element("ResponseBody").elementTextTrim("TxStatCode");
			if(TxStatCode != null && TxStatCode.equals("000000")){
				String CUSTCOD = root.element("ResponseBody").elementTextTrim("CUSTCOD");//核心客户号
				String ACCNO = root.element("ResponseBody").elementTextTrim("ACCNO");//核心账号
				String ACCCY1 = root.element("ResponseBody").elementTextTrim("ACCCY1");//币种
				String ACCCY2 = root.element("ResponseBody").elementTextTrim("ACCCY2");//币种
				//存在只送信息 不开户的情况，此时只返回开户成功，没有核心客户号
//				if(StringUtils.isEmpty(CUSTCOD)){
//					String logMsg = "响应报文中核心客户号为空";//
//					log.error(logMsg);
//					this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
//					return txData;
//				}
				String logMsg = "核心开户成功";//
				log.info(logMsg);
				txData.setAttribute("isNewCust", true);
				txData.setAttribute("CUSTCOD", CUSTCOD);
				txData.setAttribute("ACCNO", ACCNO);
				txData.setAttribute("ACCCY1", ACCCY1);
				txData.setAttribute("ACCCY2", ACCCY2);
				this.setTxResult(txData, "0", "000000", "交易成功", "success", logMsg);
				return txData;
			}else{
				if(StringUtils.isEmpty(TxStatCode) || StringUtils.isEmpty(TxStatDesc)){
					String logMsg = "返回报文中缺少字段{TxStatCode、TxStatDesc}";
					log.error(logMsg);
					this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
					return txData;
				}else{
					String errMsg = getErrMsgByCode(TxStatCode);//获取错误码对应的错误信息
					if(TxStatCode.equals("0014")){
						String CoreNo = root.element("ResponseBody").elementTextTrim("CoreNo");//核心客户号
						String logMsg = "开户失败，客户已存在";
						log.info(logMsg);
						txData.setAttribute("isNewCust", false);
						txData.setAttribute("coreNo", CoreNo);//添加核心客户号
						this.setTxResult(txData, "2", "", logMsg, "success", logMsg);
						return txData;
					}else{
						String logMsg = "交易失败，具体原因：["+errMsg+"]";
						log.error(logMsg);
						this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
						return txData;
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			String logMsg = "响应信息异常，请联系EAI或者核心系统相关人员";
			log.error(logMsg);
			this.setTxResult(txData, "2", "", logMsg, "error", logMsg);
			return txData;
		}
	}
	
	/**
	 * 错误编号及对应的错误信息
	 * @param txStatCode
	 * @return
	 */
	private String getErrMsgByCode(String txStatCode) {
		String errMsg = "开户失败：系统错误，请联系管理员";
		if("0001".equals(txStatCode)){
			errMsg = "开户失败：参数格式不正确";
		}else if("0002".equals(txStatCode)){
			errMsg = "开户失败：参数中关键字段为空";
		}else if("0003".equals(txStatCode)){
			errMsg = "开户失败：系统开始跑批";
		}else if("0004".equals(txStatCode)){
			errMsg = "开户失败：证件类型不正确";
		}else if("0005".equals(txStatCode)){
			errMsg = "开户失败：客户名在恐怖组织名单里";
		}else if("0006".equals(txStatCode)){
			errMsg = "开户失败：国别不正确";
		}else if("0007".equals(txStatCode)){
			errMsg = "开户失败：风险国别不正确";
		}else if("0008".equals(txStatCode)){
			errMsg = "开户失败：性别不正确";
		}else if("0009".equals(txStatCode)){
			errMsg = "开户失败：出生日期不正确";
		}else if("0010".equals(txStatCode)){
			errMsg = "开户失败：客户经理不存在";
		}else if("0011".equals(txStatCode)){
			errMsg = "开户失败：行号不正确";
		}else if("0012".equals(txStatCode)){
			errMsg = "开户失败：更新表失败";
		}else if("0013".equals(txStatCode)){
			errMsg = "开户失败：未知的账户类型";
		}else if("0014".equals(txStatCode)){
			errMsg = "开户失败：客户已存在";
		}else if("0015".equals(txStatCode)){
			errMsg = "开户失败：账户类型与证件类型、国籍不匹配";
		}else if("0020".equals(txStatCode)){
			errMsg = "开户失败：批量文件无法打开";
		}
		return errMsg;
	}
}
