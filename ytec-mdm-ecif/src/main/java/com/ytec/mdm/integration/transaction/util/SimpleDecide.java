/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.util
 * @文件名：SimpleDecide.java
 * @版本信息：1.0.0
 * @日期：2013-12-23-下午4:38:10
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.util;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IExtCaseDispatch;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：SimpleDecide
 * @类描述：简单分支调度判定函数
 * @功能描述:通过请求字段中某个值判定分支
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-23 下午4:38:10   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-23 下午4:38:10
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SimpleDecide implements IExtCaseDispatch {
	private String decideAttrName;

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.ICaseDispatch#decide(com.ytec.mdm.base.bo.EcifData)
	 */
	@Override
	public String decide(EcifData ecifData) {
		// TODO Auto-generated method stub
		String v=(String)ecifData.getParameterMap().get(decideAttrName);
		if(StringUtil.isEmpty(v)){
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_OTHER.getCode(), "请求判定标识%s数据为空", decideAttrName);
			return null;
		}else{
			return v;
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IExtCaseDispatch#init(java.lang.String)
	 */
	@Override
	public void init(String args) {
		// TODO Auto-generated method stub
		decideAttrName=args;
	}

}
