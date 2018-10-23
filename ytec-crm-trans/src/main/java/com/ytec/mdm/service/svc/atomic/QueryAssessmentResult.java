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
import com.ytec.mdm.domain.biz.OcrmFFinCustRisk;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
* ������ѯCRMϵͳ�е��������
* @author xuhoufei xuhf@yuchengtech.com
*
*/
@Service
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class QueryAssessmentResult  implements IEcifBizLogic{


	//�����־
	private static Logger log = LoggerFactory.getLogger(QueryAssessmentResult.class);
    //�������ݿ�
	private JPABaseDAO baseDAO;

	@Override
	public void process(EcifData crmData) throws Exception {

		baseDAO = (JPABaseDAO)SpringContextUtils.getBean("baseDAO");
		//��ȡ�ڵ�
		Element body = crmData.getBodyNode();
		//��ȡ���ױ���
		String txCode = body.element("txCode").getTextTrim();
		//��ȡ��������
		String txName = body.element("txName").getTextTrim();
		//��ȡ�ͻ���
		String custId = body.element("custId").getTextTrim();

		List<OcrmFFinCustRisk> list = null;
		try{
			list = bizGetObject(custId);
			if(list!=null && list.size()>0){
				/**
				 * �����ر���
				 */
				Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
				for(int i = 0;i<list.size();i++){
					OcrmFFinCustRisk custRisk = list.get(i);
					Element customerEle = responseEle.addElement("custrisk");
					//����ı��ĵĽڵ����ݰ����ͻ��ţ��ͻ����Ƶȣ���Ҫ���ǿͻ�ʶ�����������
					Element hand = customerEle.addElement("custId");
					hand.setText(custId);
					hand = customerEle.addElement("indageteQaScoring");
					hand.setText(custRisk.getIndageteQaScoring()+"");
					hand = customerEle.addElement("evaluateDate");
					hand.setText(custRisk.getEvaluateDate()+"");
				}
				crmData.setRepNode(responseEle);
				return ;
			}else{
				log.info("�ͻ�����{}������",custId);
				crmData.setStatus(ErrorCode.WRN_NONE_FOUND);
				crmData.setSuccess(true);
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("������ѯ�������ʧ�ܣ�"+e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(true);
			return;
		}
	}

	public List bizGetObject(String custId) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "OcrmFFinCustRisk";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map<String,String> paramMap = new HashMap<String,String>();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ����
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId" );

		//����ѯ���������뵽map��������
		paramMap.put("custId", custId);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
     	if (result != null && result.size() >0){
			return result;
		}
		return null;
	}

}
