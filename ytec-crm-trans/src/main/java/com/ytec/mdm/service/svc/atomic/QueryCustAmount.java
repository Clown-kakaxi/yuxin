package com.ytec.mdm.service.svc.atomic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;


@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QueryCustAmount implements IEcifBizLogic {

	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(QueryCustAmount.class);

	@Override
	public void process(EcifData ecifData) throws Exception {

		baseDAO = (JPABaseDAO)SpringContextUtils.getBean("baseDAO");

		/**
		 * ��ȡ����������
		 */
		Element body = ecifData.getBodyNode();
		//���ױ���
		String txCode = body.element("txCode").getTextTrim();
		//�˺�
		String acctNo = body.element("custNo").getTextTrim();

		List list=null;
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		//��ѯ
		try{
			list = bizGetObject(acctNo);
			if(list!=null){
				for(int i =0;i<list.size();i++){
					Element customerEle = responseEle.addElement("");
					Element hand = customerEle.addElement("acctNo");
					hand.setText(acctNo);

				}
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("��ѯ����ʧ�ܣ�"+e.getMessage()+"���ױ��룺"+txCode);
			ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			ecifData.setSuccess(false);
			return;
		}
		/**
		 * �����ر���
		 */
		ecifData.setRepNode(responseEle);

		return;
	}

	public List bizGetObject(String acctNo){

			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			// ����
			String simpleName = "AdminLogInfo";
			// ��ñ���
			String tableName = simpleName;
			// ƴװJQL��ѯ���
			StringBuffer jql = new StringBuffer();
			Map paramMap = new HashMap();

			// ��ѯ��
			jql.append("FROM " + tableName + " a");

			// ��ѯ����
			jql.append(" WHERE 1=1");
			jql.append(" AND a.acctNo =:acctNo" );

			paramMap.put("acctNo", acctNo);    //����ʵ���������custId����

			List result =null;
			result= baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() >0){
				return result;
			}
			return null;
		}

}
