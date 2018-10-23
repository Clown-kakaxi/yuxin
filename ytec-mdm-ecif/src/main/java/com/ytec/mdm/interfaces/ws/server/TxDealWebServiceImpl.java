/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.ws.server
 * @�ļ�����TxDealWebServiceImpl.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:49:50
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.interfaces.ws.server;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�TxDealWebServiceImpl
 * @��������WEB SERVICE ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:49:50   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:49:50
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@WebService(endpointInterface = "com.ytec.mdm.interfaces.ws.server.TxDealWebService", serviceName = "txDealWebService")
public class TxDealWebServiceImpl implements TxDealWebService {
	private static Logger log = LoggerFactory.getLogger(TxDealWebServiceImpl.class);
	private Class adapterClazz;  //ִ��������
    /**
     *@���캯�� 
     */
    public TxDealWebServiceImpl() {
    	try{
    		adapterClazz = Class.forName(ServerConfiger.adapter);
		}catch(Exception e){
			log.error("�Ҳ���������",e);
		}
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.ws.server.TxDealWebService#execute(java.lang.String)
	 */
	public String execute(String req) {
    	if (!StringUtil.isEmpty(req)) {
    		try {
    			WsExecutor executor = (WsExecutor)adapterClazz.newInstance();
    			//��ʼ
    			executor.init(req);
    			//ִ��
    			executor.run();
    			//������Ӧ����
    			return executor.getResXml();
    		} catch (Exception e) {
    			log.error("��ȡ������",e);
    			return e.getMessage();
    		}catch(Throwable ex){
				log.error("���������ش���:", ex);
				return ex.getMessage();
			}
    	}
    	return "";
    }
    
}

