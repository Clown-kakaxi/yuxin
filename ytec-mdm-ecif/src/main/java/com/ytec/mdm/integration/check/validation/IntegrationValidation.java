/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：IntegrationValidation.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:51:04
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：IntegrationValidation
 * @类描述：集成层报文校验
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:51:05
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:51:05
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class IntegrationValidation extends AbstractValidationChain {
	private static IVerifChain validationHead = new IntegrationValidation();

	public void init(Map arg) throws Exception {
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		Class clazz = null;
		IVerifChain cc = null;
		IVerifChain point = validationHead;
		while (it.hasNext()) {
			clazz = Class.forName(it.next());
			cc = (IVerifChain) clazz.newInstance();
			point.addChain(cc);
			point = cc;
		}
		point = null;
	}

	public IntegrationValidation() {
	}

	/**
	 * @函数名称:getInstance
	 * @函数描述:获取校验过滤器
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	public static IVerifChain getInstance() {
		return validationHead;
	}

	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(ecifData.getTxCode())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), "交易代码为空");
			log.warn("交易代码为空");
			return false;
		}
		/** 交易系统 */
		if (StringUtil.isEmpty(ecifData.getOpChnlNo())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), "操作系统为空");
			log.warn("交易{}操作系统号为空", ecifData.getTxCode());
			return false;
		}
		/** 请求柜员号，交易操作人员 */
		if (StringUtil.isEmpty(ecifData.getTlrNo())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(), "操作柜员号为空");
			log.warn("交易{}操作柜员号为空", ecifData.getTxCode());
			return false;
		}
		/** 交易流水号 */
		if (StringUtil.isEmpty(ecifData.getReqSeqNo())) {
			ecifData.setStatus(ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getCode(), ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getChDesc());
			log.warn("交易{}{}", ecifData.getTxCode(), ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getChDesc());
			return false;
		} else {
			String reqSeqNo = ecifData.getReqSeqNo();
			SimpleDateFormat format = new SimpleDateFormat(ServerConfiger.getStringArg("reqSeqNoDateFormat"));
			try {
				long reqTimestamp = format.parse(reqSeqNo).getTime();
				long currTimestamp = (new Date()).getTime();

				// 保证ECIF交易服务与请求方的实时性，对于时间过长的交易不做
				long timeSub = (currTimestamp - reqTimestamp)/1000;
				if (timeSub >= ServerConfiger.getIntArg("socketTimeOut")) {
					String msg = String.format("%s(%ds,预期:%ds)", ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getChDesc(),timeSub,ServerConfiger.getIntArg("socketTimeOut")/1000);
					ecifData.setStatus(ErrorCode.ERR_REQ_REQSEQ_TIMEOUT.getCode(),
							msg);
					log.info("交易{}{}", ecifData.getTxCode(), msg);
					System.out.println();
					return false;
				}
			} catch (ParseException e) {
				ecifData.setStatus(ErrorCode.ERR_REQ_REQSEQ_FORMAT.getCode(),
						ErrorCode.ERR_REQ_REQSEQ_FORMAT.getChDesc());
				log.error("交易{}{}\n{}", ecifData.getTxCode(), ErrorCode.ERR_REQ_REQSEQ_FORMAT.getChDesc(),
						e.getLocalizedMessage());
				return false;
			}
		}
		return true;
	}

}
