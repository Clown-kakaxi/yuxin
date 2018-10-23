/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.validation
 * @文件名：AbstractValidationChain.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:50:08
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.check.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.integration.transaction.facade.IVerifChain;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：AbstractValidationChain
 * @类描述：请求报文校验链
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:50:09   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:50:09
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class AbstractValidationChain implements IVerifChain {
	protected static Logger log = LoggerFactory.getLogger(AbstractValidationChain.class);
	/**
	 * @属性名称:nextChain
	 * @属性描述:下一个校验
	 * @since 1.0.0
	 */
	protected IVerifChain nextChain = null;

	@Override
	public void addChain(IVerifChain c) {
		// TODO Auto-generated method stub
		this.nextChain=c;
	}

	@Override
	public IVerifChain getChain() {
		// TODO Auto-generated method stub
		return nextChain;
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IVerifChain#sendToChain(com.ytec.mdm.base.bo.EcifData)
	 */
	public boolean sendToChain(EcifData ecifData){
		 if(reqMsgValidation(ecifData)){
			 if(nextChain != null){
				 return nextChain.sendToChain(ecifData);  
			 }else {
				 return true;
			 }
		 }else{
			 return false;
		 }
		 
	}
	public abstract boolean reqMsgValidation(EcifData ecifData);

}
