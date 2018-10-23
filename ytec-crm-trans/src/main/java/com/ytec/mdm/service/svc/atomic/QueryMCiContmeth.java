/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.sampleecif.service.svc.atomic
 * @文件名：AddGeneral.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:01:52
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.service.svc.atomic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ytec.mdm.domain.biz.AcrmFCiCustomer;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：AddGeneral
 * @类描述：通用保存
 * @功能描述:
 * @修改时间：2013-12-17 下午12:01:53
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QueryMCiContmeth implements IEcifBizLogic{
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(QueryMCiContmeth.class);

	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void process(EcifData ecifData) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		/**
		 * 获取请求报文数据
		 */
		Element body = ecifData.getBodyNode();

		String txCode = body.element("txCode").getTextTrim();
		String authType = body.element("authType").getTextTrim();
		String authCode = body.element("authCode").getTextTrim();
		String custNo = body.element("custNo").getTextTrim();

		/***
		 * 交易业务校验
		 */
		//log.info("正在进行请求业务校验...");


		/***
		 * 交易业务处理
		 */
		//查询
		AcrmFCiCustomer customer = null;
		Object o = bizGetObject(custNo);
		if(o!=null){
			customer = (AcrmFCiCustomer)o;
		}else{

			log.info("客户数据{}不存在",custNo);
			ecifData.setStatus(ErrorCode.WRN_NONE_FOUND);
			ecifData.setSuccess(true);

			return;
		}

		/**
		 * 处理返回报文
		 */
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		Element customerEle = responseEle.addElement("customer");

		Element hand = customerEle.addElement("custNo");
		hand.setText(custNo);

		hand = customerEle.addElement("custType");
		hand.setText(customer.getCustType()==null?"":customer.getCustType());

		hand = customerEle.addElement("custStat");
		hand.setText(customer.getCustStat()==null?"":customer.getCustStat());

		hand = customerEle.addElement("potentialFlag");
		hand.setText(customer.getPotentialFlag()==null?"":customer.getPotentialFlag());

		ecifData.setRepNode(responseEle);

		return;
	}

	public Object bizGetObject(String custId){

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "MCiCustomer";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId" );

		paramMap.put("custId", new Long(custId));    //根据实际情况决定custId类型

		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() >0){
			return result.get(0);
		}
		return null;
	}
}
