package com.ytec.fubonecif.service.svc.comb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @项目名称：ytec-mdm-fubonecif
 * @类名称：CancelCustOrderInfo
 * @类描述：取消客户预约
 * @功能描述:
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
public class CancelCustOrderInfo implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(CancelCustOrderInfo.class);
	private JPABaseDAO baseDAO;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Element body = ecifData.getBodyNode();// 获取节点
		String txCode = body.element("txCode").getTextTrim();// 获取交易编号
		if(StringUtils.isEmpty(txCode)){
			String msg = "信息不完整，报文请求节点中txCode不允许为空";
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		// String txName = body.element("txName").getTextTrim();// 获取交易名称
		// String authType = body.element("authType").getTextTrim();// 获取权限控制类型
		// String authCode = body.element("authCode").getTextTrim();// 获取权限控制代码
		try {
			Element e_cancel = body.element("orderCancel");
			if(e_cancel == null){
				String msg = "信息不完整，报文中没有orderCancel节点";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			String sOrderSerialNo = e_cancel.element("sOrderSerialNo").getTextTrim();// 证件号码
			String certid = e_cancel.element("certid").getTextTrim();// 预约号
			if(StringUtils.isEmpty(sOrderSerialNo) || StringUtils.isEmpty(certid)){
				String msg = "信息不完整，报文请求节点中sOrderSerialNo和certid不允许为空";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
				return;
			}
			//根据证件号码、预约号查询客户号
			String sql = "SELECT * FROM ("      
					+ " SELECT T.CUSTOMERID,ROW_NUMBER()OVER (ORDER BY REGEXP_SUBSTR(CUSTOMERID,'[[:digit:]]+$') DESC) MM  FROM O_WZ_CUSTOMER_INFO T"
					+ " WHERE trim(SORDERSERIALNO)=trim('"+sOrderSerialNo+"') AND trim(CERTID)=trim('"+certid+"'))"
					+ " WHERE MM=1";//;
			List<Object[]> l_res1 = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);
			if(l_res1 == null || l_res1.size() != 1){
				String msg = "根据证件号和预约客户号查询客户信息失败";
				log.error(msg);
				ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), msg);
				return;
			}
			Object[] res1 = l_res1.get(0);
			if(res1 != null && res1.length >= 1){
				String customerId = res1[0].toString();
				String sql_up = "update O_WZ_CUSTOMER_INFO t set t.APP_STATUS='0' where t.CUSTOMERID='"+customerId+"'";
				int resInt = this.baseDAO.batchExecuteNativeWithIndexParam(sql_up, null);
				if(resInt >= 1){
					String msg = "成功更新预约信息";
					log.info(msg);
					ecifData.setStatus(ErrorCode.SUCCESS.getCode(), msg);
					ecifData.setSuccess(true);
				}else{
					String msg = "更新预约信息失败";
					log.info(msg);
					ecifData.setStatus(ErrorCode.WRN_NONE_UPDATE.getCode(), msg);
				}
			}else{
				String msg = "更新预约信息失败";
				log.info(msg);
				ecifData.setStatus(ErrorCode.WRN_NONE_UPDATE.getCode(), msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "更新预约信息失败";
			log.info(msg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), msg);
		}
	}

}
