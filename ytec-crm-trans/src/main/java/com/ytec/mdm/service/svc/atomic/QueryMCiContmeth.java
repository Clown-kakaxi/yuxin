/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.sampleecif.service.svc.atomic
 * @�ļ�����AddGeneral.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:01:52
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AddGeneral
 * @��������ͨ�ñ���
 * @��������:
 * @�޸�ʱ�䣺2013-12-17 ����12:01:53
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
		 * ��ȡ����������
		 */
		Element body = ecifData.getBodyNode();

		String txCode = body.element("txCode").getTextTrim();
		String authType = body.element("authType").getTextTrim();
		String authCode = body.element("authCode").getTextTrim();
		String custNo = body.element("custNo").getTextTrim();

		/***
		 * ����ҵ��У��
		 */
		//log.info("���ڽ�������ҵ��У��...");


		/***
		 * ����ҵ����
		 */
		//��ѯ
		AcrmFCiCustomer customer = null;
		Object o = bizGetObject(custNo);
		if(o!=null){
			customer = (AcrmFCiCustomer)o;
		}else{

			log.info("�ͻ�����{}������",custNo);
			ecifData.setStatus(ErrorCode.WRN_NONE_FOUND);
			ecifData.setSuccess(true);

			return;
		}

		/**
		 * �����ر���
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
		// ����
		String simpleName = "MCiCustomer";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId" );

		paramMap.put("custId", new Long(custId));    //����ʵ���������custId����

		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() >0){
			return result.get(0);
		}
		return null;
	}
}
