/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.sampleecif.service.svc.atomic
 * @�ļ�����
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
import com.ytec.mdm.base.dao.ProcedureHelper;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.biz.AcrmFCiCustomer;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @��Ŀ���ƣ�
 * @�����ƣ�
 * @��������
 * @��������:
 * @author�������
 * @�޸�ʱ�䣺
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class UpdateSubOrgCust implements IEcifBizLogic{
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(UpdateSubOrgCust.class);
	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void process(EcifData crmData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**
		 * ��ȡ����������
		 *
		 */
		Element body = crmData.getBodyNode();
		//һ���ڵ�
		String txCode = body.element("txCode").getTextTrim();
		String txName = body.element("txName").getTextTrim();
		//�����ڵ�
		String  custId  = body.element("customer").element("custId").getTextTrim();
		String  identType=  body.element("customer").element("identType").getTextTrim();
		String  identNo  =  body.element("customer").element("identNo").getTextTrim();
		String  custName =  body.element("customer").element("custName").getTextTrim();

		log.info("ִ�н���....."+txName);
		//��ѯ�����Ƿ����
		 Object queryObjbyCard =null;

		AcrmFCiCustomer customer = null;
		ProcedureHelper pc=new ProcedureHelper();
		 try{
			 NameUtil getName=new NameUtil();
			 String procedureName=getName.GetProcedureName();
			//���ô洢����
			 pc.callProcedureNoReturn(procedureName, new Object[]{custId});
			 queryObjbyCard = bizGetObjectbyCard(custId,identType, identNo, custName);
			 if(queryObjbyCard!=null){
				 customer = (AcrmFCiCustomer)queryObjbyCard;
			 }else{
				 log.error("δ��ѯ���ü�¼");
				 crmData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				 crmData.setSuccess(false);
				 return;
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 log.error("��ѯ���ݿⷢ��ʧ��"+e.getMessage()+"���ױ��룺"+txCode);
			 crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			 crmData.setSuccess(false);
			 return;
		 }
		  Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
	      crmData.setRepNode(responseEle);
	      return;
		}


	//����֤����ѯ��˽Ǳ���û����޸�

	public Object bizGetObjectbyCard(String custId,String identType,String identNo,String custName) throws Exception{
		//custId�ͻ����,cust_name�ͻ����ƣ� ident_no֤������,  identType֤������ ��ѯ����
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// ����
		String simpleName = "AcrmFCiCustomer";
		// ��ñ���
		String tableName = simpleName;
		// ƴװJQL��ѯ���
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// ��ѯ��
		jql.append("FROM " + tableName + " a");

		// ��ѯ���� cust_name ident_no ident_type
		jql.append(" WHERE 1=1 ");
		jql.append(" AND a.custId =:custId");
		jql.append(" AND a.identType =:identType" );
		jql.append(" AND a.identNo =:identNo" );
		jql.append(" AND a.custName =:custName" );

		paramMap.put("custId", custId);
		paramMap.put("identType", identType);    //����ʵ���������custId����
		paramMap.put("identNo", identNo);
		paramMap.put("custName", custName);

		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() >0){
			return result.get(0);
		}
		return null;
	}
}
