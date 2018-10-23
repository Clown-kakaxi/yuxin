/**
 * @��Ŀ����
 * @������com.ytec.sampleecif.service.svc.atomic
 * @�ļ�����
 * @�汾��Ϣ��1.0.0
 * @���ڣ�
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.service.svc.atomic;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ytec.mdm.domain.biz.OcrmACiCustlossRemind;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @��Ŀ���ƣ�CRM ����
 * @�����ƣ�CustLostAlarm
 * @��������
 * @��������:���ͻ���ʧԤ����������
 * @�޸�ʱ�䣺2014-08-20
 * @�޸ı�ע��
 * @author�������
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * custlossremind
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustLostAlarm implements IEcifBizLogic{
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(CustLostAlarm.class);

	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void process(EcifData CrmData) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		/**
		 *  ��ȡ����������
		 *  custLossreMind һ��
		 *  custId
            custName
			declareCurType
			declareAmt
			tradDate
			branchNo
			currStatus
			declareDate
			�ͻ����	���ͻ���ʧԤ����
			�ͻ�����	���ͻ���ʧԤ����
			�걨����	���ͻ���ʧԤ����
			�걨���	���ͻ���ʧԤ����
			��������	���ͻ���ʧԤ����
			��֧�б��	���ͻ���ʧԤ����
			��ǰ״̬	���ͻ���ʧԤ����
			�걨ʱ��	���ͻ���ʧԤ����
		 */
		Element body = CrmData.getBodyNode();
		String txCode = body.element("txCode").getTextTrim();
		String txName = body.element("txName").getTextTrim();


		String custId =  body.element("custLossreMind").element("custId").getTextTrim();
		String custName=body.element("custLossreMind").element("custName").getTextTrim();
		String declareCurType=body.element("custLossreMind").element("declareCurType").getTextTrim();
		String declareAmt=body.element("custLossreMind").element("declareAmt").getTextTrim();
        String tradDate=body.element("custLossreMind").element("tradDate").getTextTrim();
        String branchNo=body.element("custLossreMind").element("branchNo").getTextTrim();
        String currStatus=body.element("custLossreMind").element("currStatus").getTextTrim();
        String declareDate=body.element("custLossreMind").element("declareDate").getTextTrim();

		/***
		 *
		 * ����ҵ����
		 */
        //���������������
      try{
        log.info(txName+"���ڽ�����...");
        OcrmACiCustlossRemind ocrmacicustlossremind = new OcrmACiCustlossRemind();

		ocrmacicustlossremind.setCustId(custId==null?"":custId);
		ocrmacicustlossremind.setCustName(custName==null?"":custName);
		ocrmacicustlossremind.setDeclareCurType(declareCurType==null?"":declareCurType);
		if(declareAmt!=null && !declareAmt.trim().equals("")){
			ocrmacicustlossremind.setDeclareAmt(new BigDecimal(declareAmt));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(tradDate!=null && !tradDate.trim().equals("")){
			Date newdate = sdf.parse(tradDate);
		    ocrmacicustlossremind.setTradDate(newdate);
		}
		ocrmacicustlossremind.setBranchNo(branchNo==null?"":branchNo);
		ocrmacicustlossremind.setCurrStatus(currStatus==null?"":currStatus);
		ocrmacicustlossremind.setCustName(custName==null?"":custName);
		if(declareDate!=null && !declareDate.trim().equals("")){
			Date newdeclareDate = sdf.parse(declareDate);
			ocrmacicustlossremind.setDeclareDate(newdeclareDate);
		}
		//�����ݱ��浽���ͻ���ʧԤ��������
		baseDAO.save(ocrmacicustlossremind);

	  }catch(Exception e){
		  e.printStackTrace();
		  log.error("����ʧ�ܣ�"+e.getMessage()+"���ױ����ǣ�"+txCode);
		  log.error("�����쳣����ʧ��",e.getMessage()+"���ױ��룺"+txCode);
	      CrmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
	      CrmData.setSuccess(false);
		  return ;
	  }
      Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
      CrmData.setRepNode(responseEle);
      return;
	}
}
