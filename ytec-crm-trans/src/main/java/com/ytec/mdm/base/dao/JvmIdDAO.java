/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.dao
 * @�ļ�����JvmIdDAO.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-3-26-����4:50:25
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.TxSysParam;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�JvmIdDAO
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-3-26 ����4:50:25   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-3-26 ����4:50:25
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class JvmIdDAO {
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void updateJvmValue(TxSysParam txSysParam,int nextval){
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		if(txSysParam==null){
			txSysParam=new TxSysParam();
			txSysParam.setParamId(OIdUtils.getIdOfLong());
			txSysParam.setParamName(MdmConstants.TXSYSPARAMNAME);
			txSysParam.setParamType("int");
		}
		txSysParam.setParamValue(String.valueOf(nextval));
		baseDAO.merge(txSysParam);
		baseDAO.flush();
	}

}
