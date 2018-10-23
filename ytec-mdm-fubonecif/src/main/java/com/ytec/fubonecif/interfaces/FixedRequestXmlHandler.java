/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.interfaces
 * @文件名：FixedRequestXmlHandler.java
 * @版本信息：1.0.0
 * @日期：2014-4-16-下午2:28:06
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.interfaces;

import org.springframework.stereotype.Component;

import com.ytec.mdm.interfaces.socket.normalsocket.coder.FixedRequestCoderHandler;

/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：FixedRequestXmlHandler
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-16 下午2:28:06   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-16 下午2:28:06
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Component
public class FixedRequestXmlHandler extends FixedRequestCoderHandler {
	private byte SP = 32;
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.normalsocket.coder.FixedRequestCoderHandler#fixedAttrFormat(java.lang.String, int, java.lang.String)
	 */
	@Override
	public byte[] fixedAttrFormat(byte[] src, int leng, String DataType) {
		// TODO Auto-generated method stub
		byte[] b=new byte[leng];
		int length=0;
		if(src!=null){
			length=src.length;
			if(length>leng){
				log.warn("数据超长,length[{}]",leng);
				System.arraycopy(src, 0, b, 0, leng);
				length=leng;
			}else if(length>0){
				System.arraycopy(src, 0, b, 0, length);
			}
			for(int i=length;i<leng;i++){
				b[i]=SP;
			}
		}else{
			for(int i=length;i<leng;i++){
				b[i]=SP;
			}
		}
		return b;
	}

}
