/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.sync.ptsync
 * @�ļ�����ISynchroExecute.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:59:06
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.sync.ptsync;

import com.ytec.mdm.domain.txp.TxEvtNotice;
import com.ytec.mdm.domain.txp.TxSyncConf;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ISynchroExecute
 * @��������ͬ������ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:59:06   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:59:06
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface ISynchroExecute {
	/**
	 * @��������:execute
	 * @��������:ͬ�������Ĵ���
	 * @�����뷵��˵��:
	 * 		@param txSyncConf
	 * 		@param txEvtNotice
	 * 		@return
	 * @�㷨����:
	 */
	public boolean execute(TxSyncConf txSyncConf,TxEvtNotice txEvtNotice);
	/**
	 * @��������:executeResult
	 * @��������:ͬ����Ӧ���Ĵ���
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public boolean executeResult();
}
