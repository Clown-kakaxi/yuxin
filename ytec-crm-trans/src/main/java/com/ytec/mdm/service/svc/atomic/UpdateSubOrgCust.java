/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.sampleecif.service.svc.atomic
 * @文件名：
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:01:52
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：
 * @类名称：
 * @类描述：
 * @功能描述:
 * @author：豪哥哥
 * @修改时间：
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
		 * 获取请求报文数据
		 *
		 */
		Element body = crmData.getBodyNode();
		//一级节点
		String txCode = body.element("txCode").getTextTrim();
		String txName = body.element("txName").getTextTrim();
		//二级节点
		String  custId  = body.element("customer").element("custId").getTextTrim();
		String  identType=  body.element("customer").element("identType").getTextTrim();
		String  identNo  =  body.element("customer").element("identNo").getTextTrim();
		String  custName =  body.element("customer").element("custName").getTextTrim();

		log.info("执行交易....."+txName);
		//查询对象是否存在
		 Object queryObjbyCard =null;

		AcrmFCiCustomer customer = null;
		ProcedureHelper pc=new ProcedureHelper();
		 try{
			 NameUtil getName=new NameUtil();
			 String procedureName=getName.GetProcedureName();
			//调用存储过程
			 pc.callProcedureNoReturn(procedureName, new Object[]{custId});
			 queryObjbyCard = bizGetObjectbyCard(custId,identType, identNo, custName);
			 if(queryObjbyCard!=null){
				 customer = (AcrmFCiCustomer)queryObjbyCard;
			 }else{
				 log.error("未查询到该记录");
				 crmData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				 crmData.setSuccess(false);
				 return;
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 log.error("查询数据库发生失败"+e.getMessage()+"交易编码："+txCode);
			 crmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
			 crmData.setSuccess(false);
			 return;
		 }
		  Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
	      crmData.setRepNode(responseEle);
	      return;
		}


	//根据证件查询对私潜在用户并修改

	public Object bizGetObjectbyCard(String custId,String identType,String identNo,String custName) throws Exception{
		//custId客户编号,cust_name客户名称， ident_no证件号码,  identType证件类型 查询依据
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		// 类名
		String simpleName = "AcrmFCiCustomer";
		// 获得表名
		String tableName = simpleName;
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map paramMap = new HashMap();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件 cust_name ident_no ident_type
		jql.append(" WHERE 1=1 ");
		jql.append(" AND a.custId =:custId");
		jql.append(" AND a.identType =:identType" );
		jql.append(" AND a.identNo =:identNo" );
		jql.append(" AND a.custName =:custName" );

		paramMap.put("custId", custId);
		paramMap.put("identType", identType);    //根据实际情况决定custId类型
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
