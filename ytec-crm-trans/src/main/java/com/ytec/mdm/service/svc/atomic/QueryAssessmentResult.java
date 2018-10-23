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
* 网银查询CRM系统中的评估结果
* @author xuhoufei xuhf@yuchengtech.com
*
*/
@Service
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class QueryAssessmentResult  implements IEcifBizLogic{


	//输出日志
	private static Logger log = LoggerFactory.getLogger(QueryAssessmentResult.class);
    //操作数据库
	private JPABaseDAO baseDAO;

	@Override
	public void process(EcifData crmData) throws Exception {

		baseDAO = (JPABaseDAO)SpringContextUtils.getBean("baseDAO");
		//获取节点
		Element body = crmData.getBodyNode();
		//获取交易编码
		String txCode = body.element("txCode").getTextTrim();
		//获取交易名称
		String txName = body.element("txName").getTextTrim();
		//获取客户号
		String custId = body.element("custId").getTextTrim();

		List<OcrmFFinCustRisk> list = null;
		try{
			list = bizGetObject(custId);
			if(list!=null && list.size()>0){
				/**
				 * 处理返回报文
				 */
				Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
				for(int i = 0;i<list.size();i++){
					OcrmFFinCustRisk custRisk = list.get(i);
					Element customerEle = responseEle.addElement("custrisk");
					//输出的报文的节点数据包含客户号，客户名称等，主要就是客户识别里面的数据
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
				log.info("客户风险{}不存在",custId);
				crmData.setStatus(ErrorCode.WRN_NONE_FOUND);
				crmData.setSuccess(true);
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("网银查询评估结果失败："+e.getMessage());
			crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			crmData.setSuccess(true);
			return;
		}
	}

	public List bizGetObject(String custId) throws Exception {

		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "OcrmFFinCustRisk";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String,String> paramMap = new HashMap<String,String>();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId" );

		//将查询的条件放入到map集合里面
		paramMap.put("custId", custId);
		List result =null;
		result= baseDAO.findWithNameParm(jql.toString(), paramMap);
     	if (result != null && result.size() >0){
			return result;
		}
		return null;
	}

}
