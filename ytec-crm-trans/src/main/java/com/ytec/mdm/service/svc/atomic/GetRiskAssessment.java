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
import com.ytec.mdm.domain.biz.OcrmFFinCustRisk;
import com.ytec.mdm.domain.biz.OcrmFFinCustRiskQa;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * @��Ŀ���ƣ�
 * @�����ƣ�
 * @��������
 * @��������:
 * @�޸�ʱ�䣺
 * @�޸ı�ע��
 * @author�������
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GetRiskAssessment implements IEcifBizLogic{
	private JPABaseDAO baseDAO;
	private static Logger log = LoggerFactory.getLogger(GetRiskAssessment.class);

	@Transactional(rollbackFor={Exception.class,RuntimeException.class})
	public void process(EcifData CrmData) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

		/**
		 * ��ȡ����������
		 */


		Element body = CrmData.getBodyNode();

		String txCode = body.element("txCode").getTextTrim();
		String txName = body.element("txName").getTextTrim();


		String custId =  body.element("custrisk").element("custId").getTextTrim();
		String indageteQaScoring=body.element("custrisk").element("indageteQaScoring").getTextTrim();

		String custQId=body.element("custriskqa").element("custQId").getTextTrim();
        String custQTId=body.element("custriskqa").element("custQTId").getTextTrim();
        String scoring=body.element("custriskqa").element("scoring").getTextTrim();

		/***
		 *
		 * ����ҵ����
		 */
        //���������������
        try{
		        log.info(txName+"���ڽ�����...");
		        //����ͻ���������������
				OcrmFFinCustRisk ocrmffincustrisk = new OcrmFFinCustRisk();
				if(custQTId!=null && !custQTId.trim().equals("")){
					ocrmffincustrisk.setCustQId(new Long(custQTId));
				}
				ocrmffincustrisk.setCustId(custId==null?"":custId);
				if(indageteQaScoring!=null && !indageteQaScoring.trim().equals("")){
				  ocrmffincustrisk.setIndageteQaScoring(new BigDecimal(indageteQaScoring));
	            }
				//����ͻ�������������
				OcrmFFinCustRiskQa ocrmFfincustRiskQa = new OcrmFFinCustRiskQa();

				if(custQTId!=null && !custQTId.trim().equals("")){
					ocrmFfincustRiskQa.setCustQTId(new Long(custQTId));
					if(custQId!=null && !custQId.trim().equals("")){
						ocrmFfincustRiskQa.setCustQTId(new Long(custQId));
					}
					if(scoring!=null && !scoring.trim().equals("")){
						ocrmFfincustRiskQa.setScoring(new BigDecimal(scoring));
					}
					baseDAO.save(ocrmffincustrisk);
					baseDAO.save(ocrmFfincustRiskQa);
				}else{
					log.error("���������ڱ���ʧ��"+"���ױ�����:"+txCode);
					CrmData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY);
					CrmData.setSuccess(false);
					return;
				}
	        }catch(Exception e){
	        	e.printStackTrace();
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
