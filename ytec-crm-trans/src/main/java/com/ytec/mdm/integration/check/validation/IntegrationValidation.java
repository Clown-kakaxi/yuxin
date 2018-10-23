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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;

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
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public class IntegrationValidation extends AbstractValidationChain {
	private static IVerifChain validationHead=new IntegrationValidation();
	public void init(Map arg) throws Exception{
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		Class clazz=null;
		IVerifChain cc=null;
		IVerifChain point=validationHead;
		while(it.hasNext()) {
			clazz = Class.forName(it.next());
			cc=(IVerifChain)clazz.newInstance();
			point.addChain(cc);
			point=cc;
		}
		point=null;
	}

	public IntegrationValidation() {
	}


	/**
	 * @函数名称:getInstance
	 * @函数描述:获取校验过滤器
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	public static IVerifChain getInstance(){
		return validationHead;
	}


	@Override
	public boolean reqMsgValidation(EcifData ecifData) {
		// TODO Auto-generated method stub
		if (StringUtil.isEmpty(ecifData.getTxCode())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),
					"交易代码为空");
			log.warn("交易代码为空");
			return false;
		}
		/** 交易系统 */
		if (StringUtil.isEmpty(ecifData.getOpChnlNo())) {
			ecifData.setStatus(ErrorCode.ERR_XML_FORMAT_INVALID.getCode(),
					"操作系统号或渠道号为空");
			log.warn("交易{}操作系统号或渠道号为空",ecifData.getTxCode());
			return false;
		}
		return true;
	}

}
