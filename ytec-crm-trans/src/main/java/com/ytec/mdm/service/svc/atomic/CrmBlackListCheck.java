package com.ytec.mdm.service.svc.atomic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.exception.BizException;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
/**
 * @��Ŀ���ƣ�CRM ����
 * @�����ƣ�CmrBlackListCheck
 * @��������
 * @��������:���к�����У�齻��
 * @����ʱ�䣺2018-02-11
 * @author����Ҷ��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class CrmBlackListCheck implements IEcifBizLogic{
	
	protected static Logger log = LoggerFactory.getLogger(CrmBlackListCheck.class);
	// �������ݿ�
	@SuppressWarnings("rawtypes")
    private JPABaseDAO baseDAO;
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public void process(EcifData crmData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		log.info("CRM������У��");
		Element body = crmData.getBodyNode(); // ��ȡ�ڵ�
		if(body.element("IdentNo")==null||StringUtils.isEmpty(body.element("IdentNo").getTextTrim())){
			crmData.setSuccess(false);
			crmData.setStatus("000001", "֤�����벻��Ϊ��");
			return;
		}
		String identNo = body.element("IdentNo").getTextTrim(); // ��ȡ֤����
		String checkSql = "select t.SPECIAL_LIST_ID from acrm_f_ci_speciallist t "
				+ " where sysdate between nvl(t.start_date,sysdate)"
				+ " and nvl(t.end_date,sysdate)"
				+ " and t.special_list_flag='Y'"
				+ " and t.IDENT_NO =:identNo";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("identNo", identNo);
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		Element BlacklistStatus = responseEle.addElement("BlacklistStatus");
		List list = baseDAO.createNativeQueryWithNameParam(checkSql, paramMap).getResultList();
		if(list==null||list.size()==0){
			BlacklistStatus.setText("N");
			log.info(String.format("CRM������У�飺֤������Ϊ��[%s] �Ŀͻ��������к�������", identNo));
		}else{
			log.info(String.format("CRM������У�飺֤������Ϊ��[%s] �Ŀͻ������к�������", identNo));
			BlacklistStatus.setText("Y");
		}
		crmData.setRepNode(responseEle);
		return;
	}
}
