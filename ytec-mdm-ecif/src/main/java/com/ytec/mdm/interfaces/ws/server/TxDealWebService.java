/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.ws.server
 * @�ļ�����TxDealWebService.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:50:15
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.interfaces.ws.server;

import javax.jws.WebService;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxDealWebService
 * @��������WEB SERVICE ����ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:50:16   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:50:16
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@WebService
public interface TxDealWebService {

    /**
     * @��������:execute
     * @��������:web serviceִ�з���
     * @�����뷵��˵��:
     * 		@param req
     * 		@return
     * @�㷨����:
     */
    public String execute(String req);
    
}
