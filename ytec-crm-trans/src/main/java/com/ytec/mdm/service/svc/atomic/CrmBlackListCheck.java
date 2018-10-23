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
 * @项目名称：CRM 交易
 * @类名称：CmrBlackListCheck
 * @类描述：
 * @功能描述:我行黑名单校验交易
 * @创建时间：2018-02-11
 * @author：刘叶祥
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
public class CrmBlackListCheck implements IEcifBizLogic{
	
	protected static Logger log = LoggerFactory.getLogger(CrmBlackListCheck.class);
	// 操作数据库
	@SuppressWarnings("rawtypes")
    private JPABaseDAO baseDAO;
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public void process(EcifData crmData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		log.info("CRM黑名单校验");
		Element body = crmData.getBodyNode(); // 获取节点
		if(body.element("IdentNo")==null||StringUtils.isEmpty(body.element("IdentNo").getTextTrim())){
			crmData.setSuccess(false);
			crmData.setStatus("000001", "证件号码不能为空");
			return;
		}
		String identNo = body.element("IdentNo").getTextTrim(); // 获取证件号
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
			log.info(String.format("CRM黑名单校验：证件号码为：[%s] 的客户不在我行黑名单中", identNo));
		}else{
			log.info(String.format("CRM黑名单校验：证件号码为：[%s] 的客户在我行黑名单中", identNo));
			BlacklistStatus.setText("Y");
		}
		crmData.setRepNode(responseEle);
		return;
	}
}
