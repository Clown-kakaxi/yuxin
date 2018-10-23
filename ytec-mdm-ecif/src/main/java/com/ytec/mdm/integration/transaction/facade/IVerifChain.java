/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.transaction.facade
 * @文件名：IVerifChain.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:23:47
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.integration.transaction.facade;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：IVerifChain
 * @类描述：校验链接口
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:23:47   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:23:47
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public interface IVerifChain {
	/**
	 * 增加下一个校验
	 * @param c
	 */
	public void addChain(IVerifChain c);
	/**
	 * 运行下一个校验
	 * @param ecifData
	 */
    public abstract boolean sendToChain(EcifData ecifData);
    /**
     * 获取下一个校验
     * @return
     */
    public IVerifChain getChain();
}
