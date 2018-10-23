/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.dao
 * @文件名：UpdateIdentifierDao.java
 * @版本信息：1.0.0
 * @日期：2014-2-12-10:24:20
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.fubonecif.service.svc.atomic;

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
import com.ytec.mdm.service.facade.ICoveringRule;
//import com.ytec.fubonecif.domain.MCiOrgIdentifier;
//import com.ytec.fubonecif.domain.MCiPerIdentifier;
import com.ytec.fubonecif.domain.MCiIdentifier;


/**
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：UpdateIdentifierDao
 * @类描述：证件修改DAO
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-12 上午10:24:10   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-12 上午10:24:10
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UpdateIdentifierDao {
	private static Logger log = LoggerFactory.getLogger(UpdateIdentifierDao.class);
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void process(EcifData ecifData) throws Exception {
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			
			String identifierTab = null;
			String identType=null;
			String identNo=null;
			String identCustName=null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())) {
				identifierTab = MdmConstants.MODEL_PERSONIDENTIFIER;
			}else{
				identifierTab = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			List infoList = ecifData.getWriteModelObj().getOpModelList();
			for (Object newObj : infoList) {
				if (newObj.getClass().equals(MCiIdentifier.class)) {
					MCiIdentifier identifier = (MCiIdentifier) newObj;
					identType=identifier.getIdentType();
					identNo=identifier.getIdentNo();
					identCustName=identifier.getIdentCustName();
				}
//				if (newObj.getClass().equals(MCiOrgIdentifier.class)) {
//					MCiOrgIdentifier identifier = (MCiOrgIdentifier) newObj;
//					identType=identifier.getIdentType();
//					identNo=identifier.getIdentNo();
//					identCustName=identifier.getIdentCustName();
//				}
				/**判断与修该有相同三证的客户，且客户的状态为有效的客户,由业务确定***/
				List result=baseDAO.findWithIndexParam("select count(1) from "+identifierTab+" t1 where t1.identType=? and t1.identNo=? and t2.identCustName=? and t1."+MdmConstants.CUSTID+"<>? ",identType,identNo,identCustName, ecifData.getCustId(MdmConstants.CUSTID_TYPE));
				if(result!=null && !result.isEmpty()){
					Long count=(Long)result.get(0);
					if(count>0){
						String msg = "存在与该客户相同的证件的客户";
						log.error(msg);
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), msg);
						return;
					}
				}
			}
			GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
			ICoveringRule cover = (ICoveringRule) SpringContextUtils.getBean(MdmConstants.COVERINGRULE);
			IColumnUtils columnUtils = (IColumnUtils) SpringContextUtils.getBean(MdmConstants.COLUMNUTILS);
			ObjectReturnMessage objMessage = new ObjectReturnMessage();
			for (Object newObj : infoList) {
				// 为属性设置CustId
				OIdUtils.setCustIdValue(newObj,ecifData.getCustId());
				if (MdmConstants.TX_CODE_U.equals(ecifData.getTxType())) {
					objMessage = findObj.bizGetObject(newObj, false, true,ecifData.getOpChnlNo());
				} else {
					objMessage = findObj.bizGetObject(newObj, false, false,ecifData.getOpChnlNo());
				}
				if (objMessage.isSuccessFlag()) {
					// 如果存在，根据覆盖原则，生成一个更新的对象，然后更新该数据
					Object oldObj = objMessage.getObject();
					newObj = cover.cover(ecifData,oldObj, newObj);
					if(newObj!=null){
						newObj = columnUtils.setUpdateGeneralColumns(ecifData,
							newObj);
						if (MdmConstants.isSaveHistory) {
							Object hisObj = null;
							if (!MdmConstants.chooseSaveHistory
								|| BusinessCfg.isSaveHisObj(oldObj.getClass()
										.getSimpleName())) {
								hisObj = columnUtils.toHistoryObj(oldObj,
									ecifData.getOpChnlNo(),
									MdmConstants.HIS_OPER_TYPE_U);
							}
							if (hisObj != null) {
								baseDAO.persist(hisObj);
							}
						}
						baseDAO.merge(newObj);
					}
				} else {
					if (ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode().equals(objMessage.getError().getCode())) {
						if (MdmConstants.existBusinesskeyError) {
							String msg = newObj.getClass().getSimpleName()+ "数据中有垃圾数据:业务信息项为空";
							log.error(msg);
							ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_BUSINESSKEY.getCode(), msg);
							throw new Exception(msg);
						} else {
							continue;
						}
					}
					if(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode().equals(objMessage.getError().getCode())){
						String msg = newObj.getClass().getSimpleName()+ "中有垃圾数据:ECIF不存在该数据";
						log.error(msg);
						ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY.getCode(), msg);
						throw new Exception(msg);
					}
					
					newObj = OIdUtils.createId(newObj);
					newObj = columnUtils.setCreateGeneralColumns(ecifData,
							newObj);
					baseDAO.persist(newObj);
				}
			}
			baseDAO.flush();
			ecifData.resetStatus();
		return;
	}
}
