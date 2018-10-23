/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.fubonecif.service.svc.atomic
 * @文件名：Accont.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:01:37
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;


import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.service.facade.IMCIdentifying;
import com.ytec.fubonecif.domain.MCiCustomer;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：Accont
 * @类描述：开户
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:01:38   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:01:38
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Accont {
	private static Logger log = LoggerFactory.getLogger(Accont.class);
	
	/**
	 * 富邦华一银行ECIF数据模型中，ECIF客号即为客户标识，为同一个字段CUST_ID
	 * @param ecifData
	 * @param blankFlag
	 * @throws Exception
	 */
	public void process(EcifData ecifData, boolean blankFlag) throws Exception {
		IMCIdentifying util = (IMCIdentifying) SpringContextUtils.getBean(MdmConstants.MCIDENTIFYING);
		// 生成contId
//		ecifData.setCustId(util.getEcifCustId(ecifData.getCustType()));
		String custNoId = util.getEcifCustId(ecifData.getCustType());
		ecifData.setCustId(custNoId);
		// 生成ECIF客户号
		String newCustNo = null;
		try {
//			newCustNo = util.getEcifCustNo(ecifData.getCustType());
			newCustNo = custNoId;
			if (StringUtil.isEmpty(newCustNo)) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR);
				log.error("生成客户号错误，{}:{}",ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getCode(),ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getChDesc());
				return;
			}
		} catch (Exception e) {
			log.error("错误信息", e);
			ecifData.setStatus(ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR);
			return;
		}
		// 客户信息
		MCiCustomer customer = null;
		if(ecifData.getWriteModelObj().containsOpModel(MCiCustomer.class.getSimpleName())){
			customer=(MCiCustomer)ecifData.getWriteModelObj().getOpModelByName(MCiCustomer.class.getSimpleName());
		}else{
			customer = new MCiCustomer();
		}
		customer.setCustId(newCustNo);
		customer.setCustType(ecifData.getCustType());
		customer.setCreateDate(new Date());
		customer.setCreateTime(new Timestamp(System.currentTimeMillis()));
		customer.setCustStat(MdmConstants.STATE);
		customer.setCreateBranchNo(ecifData.getBrchNo());
		customer.setCreateTellerNo(ecifData.getTlrNo());
		if (blankFlag) {
			customer.setBlankFlag(MdmConstants.BLANK_FLAG);
		}
		// 把新生成的对象作为保存对象
		ecifData.getWriteModelObj().setOpModelList(customer);
		AddGeneral addGeneral = (AddGeneral) SpringContextUtils
				.getBean("addGeneral");
		try {
			// 调用通用保存方法，保存刚生成的对象和新增客户时的其它属性
			addGeneral.process(ecifData);
//			ecifData.setEcifCustNo(newCustNo);
			ecifData.setCustId(newCustNo);
		} catch (Exception e) {
			log.error("数据操作异常",e);
			if(ecifData.isSuccess()){
				ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			}
			return;
		}
		return;
	}
}
