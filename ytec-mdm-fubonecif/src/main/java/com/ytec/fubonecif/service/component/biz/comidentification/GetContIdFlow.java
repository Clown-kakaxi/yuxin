/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.component.biz.comidentification
 * @文件名：GetContIdFlow.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:56:15
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.component.biz.comidentification;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.bo.Identifier;
import com.ytec.mdm.service.component.biz.identification.AbsBizGetContId;
import com.ytec.mdm.service.facade.IBizGetContId;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：GetContIdFlow
 * @类描述：个人客户的组合识别，用于个人客户开户
 * @功能描述:按照源系统客户号先识别，然后按照三证识别
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:56:15   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:56:15
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetContIdFlow implements IBizGetContId {

	public void bizGetContId(EcifData ecifData) {

		// 按源系统客户号识别
		IBizGetContId bizGetContId = (AbsBizGetContId) SpringContextUtils
				.getBean(MdmConstants.GETCONTIDBYSRCCUSTNO);
		bizGetContId.bizGetContId(ecifData);
		if (ecifData.isSuccess()) {
			return;
		} else {
			// 按三证识别
			bizGetContId = (AbsBizGetContId) SpringContextUtils
					.getBean(MdmConstants.GETCONTIDBYIDENT);
			List<Identifier> identList = (List) ecifData
					.getWriteModelObj().getOperMap().get("identList");
			if (identList != null) {
				for (Identifier ident : identList) {
					ecifData.getWriteModelObj()
							.getOperMap()
							.put(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD,
									ident.getIdentType());
					ecifData.getWriteModelObj()
							.getOperMap()
							.put(MdmConstants.TX_DEF_GETCONTID_IDENTNO,
									ident.getIdentNo());
					ecifData.getWriteModelObj()
							.getOperMap()
							.put(MdmConstants.TX_DEF_GETCONTID_IDENTNAME,
									ident.getIdentCustName());
					bizGetContId.bizGetContId(ecifData);
					if (ecifData.isSuccess()) {
						return;
					}
				}
			}
		}
		return;
	}

}
