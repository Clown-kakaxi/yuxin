/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.auth
 * @文件名：AuthVerify.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:40:27
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：AuthVerify
 * @类描述：交易授权
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:40:37   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:40:37
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
