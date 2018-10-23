/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.atomic
 * @文件名：DeleteGeneral.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:02:46
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.svc.atomic;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.bo.ObjectReturnMessage;
import com.ytec.mdm.service.component.biz.identification.GetObjectByBusinessKey;
import com.ytec.mdm.service.facade.IColumnUtils;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DeleteGeneral
 * @类描述：通用属性删除
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:02:46   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:02:46
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings(
		{ "unchecked", "rawtypes" })
public class DeleteGeneral
{
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory
			.getLogger(DeleteGeneral.class);

	@Transactional
	public void process(EcifData ecifData) throws Exception
	{
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
		List objList=ecifData.getWriteModelObj().getOpModelList();
		for (Object newObj : objList)
		{
			ObjectReturnMessage objMessage = new ObjectReturnMessage();
			if(ecifData.getCustId()!=null){
				OIdUtils.setCustIdValue(newObj,ecifData.getCustId());
			}
			objMessage = findObj.bizGetObject(newObj,false,false,ecifData.getOpChnlNo());
			if (objMessage.isSuccessFlag()){
				Object oldObj = objMessage.getObject();
				baseDAO.remove(oldObj);
				if (MdmConstants.isSaveHistory) {
					Object hisObj=null;
					if(!MdmConstants.chooseSaveHistory||BusinessCfg.isSaveHisObj(oldObj.getClass().getSimpleName())){
						hisObj = columnUtils.toHistoryObj(oldObj,ecifData.getOpChnlNo(),MdmConstants.HIS_OPER_TYPE_D);
					}
					if(hisObj!=null){
						baseDAO.persist(hisObj);
					}
				}
			}else{
				if(ErrorCode.WRN_NONE_FOUND.getCode().equals(objMessage.getError().getCode())){
					log.warn("{}中删除信息不存在",newObj.getClass().getSimpleName());
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_OTHER.getCode(),"%s中删除信息不存在",newObj.getClass().getSimpleName());
				}else{
					log.warn("{}中删除信息错误[{}]",newObj.getClass().getSimpleName(),objMessage.getError().getChDesc());
					ecifData.setStatus(objMessage.getError());
				}
				return;
			}
		}
		baseDAO.flush();
		ecifData.resetStatus();
		return;
	}
}
