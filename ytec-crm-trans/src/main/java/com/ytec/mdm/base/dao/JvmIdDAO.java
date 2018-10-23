/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.dao
 * @文件名：JvmIdDAO.java
 * @版本信息：1.0.0
 * @日期：2014-3-26-下午4:50:25
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：JvmIdDAO
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-3-26 下午4:50:25   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-3-26 下午4:50:25
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
