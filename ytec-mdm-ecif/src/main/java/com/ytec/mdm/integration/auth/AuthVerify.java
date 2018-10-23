/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.auth
 * @�ļ�����AuthVerify.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:40:27
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.integration.auth;

import java.util.List;
import org.springframework.stereotype.Component;
import com.ytec.mdm.base.bo.AuthModel;
import com.ytec.mdm.integration.auth.IAuthVerify;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.SpringContextUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�AuthVerify
 * @��������������Ȩ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:40:37   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:40:37
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Component("authVerify")
@SuppressWarnings({"rawtypes","unchecked"})
public class AuthVerify implements IAuthVerify {
	private JPABaseDAO baseDAO;
	/* (non-Javadoc)
	 * @see com.ytec.mdm.base.facade.IAuthVerify#clientAuth(com.ytec.mdm.base.bo.AuthModel)
	 */
	public List clientAuth(AuthModel authModel) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		boolean globalTxAuthPdEnc=BusinessCfg.getBoolean("globalTxAuthPdEnc");
		StringBuffer jql = new StringBuffer();
		jql.append("From TxClientAuth t where t.flag ='1' and t.srcSysCd =?");
//		if(globalTxAuthPdEnc){
//			jql.append(" and t.encPwd =?" );
//		}else{
//			jql.append(" and t.password =?" );
//		}
//		return  baseDAO.findWithIndexParam(jql.toString(), authModel.getSrcSysCd(),authModel.getPassword());
		return  baseDAO.findWithIndexParam(jql.toString(), authModel.getSrcSysCd());
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.base.facade.IAuthVerify#clientAuth(com.ytec.mdm.base.bo.AuthModel)
	 */
	public List serviceAuth(AuthModel authModel) {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		return  baseDAO.findWithIndexParam("From TxServiceAuth t where t.flag ='1' and t.txCode =? and t.clientAuthId =?",
				authModel.getTxCode(),authModel.getClientAuthId());
	}
}
