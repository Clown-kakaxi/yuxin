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
		 * 获取请求报文数据
		 */
		Element body = ecifData.getBodyNode();
		//交易编码
		String txCode = body.element("txCode").getTextTrim();
		//账号
		String acctNo = body.element("custNo").getTextTrim();

		List list=null;
		Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
		//查询
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
			log.error("查询交易失败："+e.getMessage()+"交易编码："+txCode);
			ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			ecifData.setSuccess(false);
			return;
		}
		/**
		 * 处理返回报文
		 */
		ecifData.setRepNode(responseEle);

		return;
	}

	public List bizGetObject(String acctNo){

			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			// 类名
			String simpleName = "AdminLogInfo";
			// 获得表名
			String tableName = simpleName;
			// 拼装JQL查询语句
			StringBuffer jql = new StringBuffer();
			Map paramMap = new HashMap();

			// 查询表
			jql.append("FROM " + tableName + " a");

			// 查询条件
			jql.append(" WHERE 1=1");
			jql.append(" AND a.acctNo =:acctNo" );

			paramMap.put("acctNo", acctNo);    //根据实际情况决定custId类型

			List result =null;
			result= baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() >0){
				return result;
			}
			return null;
		}

}
