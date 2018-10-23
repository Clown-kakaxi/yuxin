/**
 * @项目名：
 * @包名：com.ytec.sampleecif.service.svc.atomic
 * @文件名：
 * @版本信息：1.0.0
 * @日期：
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：CRM 交易
 * @类名称：CustLostAlarm
 * @类描述：
 * @功能描述:大额客户流失预警新增交易
 * @修改时间：2014-08-20
 * @修改备注：
 * @author：豪哥哥
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
		 *  获取请求报文数据
		 *  custLossreMind 一级
		 *  custId
            custName
			declareCurType
			declareAmt
			tradDate
			branchNo
			currStatus
			declareDate
			客户编号	大额客户流失预警表
			客户名称	大额客户流失预警表
			申报币种	大额客户流失预警表
			申报金额	大额客户流失预警表
			交易日期	大额客户流失预警表
			分支行编号	大额客户流失预警表
			当前状态	大额客户流失预警表
			申报时间	大额客户流失预警表
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
		 * 交易业务处理
		 */
        //保存网银评估结果
      try{
        log.info(txName+"正在进行中...");
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
		//将数据保存到大额客户流失预警表里面
		baseDAO.save(ocrmacicustlossremind);

	  }catch(Exception e){
		  e.printStackTrace();
		  log.error("交易失败："+e.getMessage()+"交易编码是："+txCode);
		  log.error("交易异常保存失败",e.getMessage()+"交易编码："+txCode);
	      CrmData.setStatus(ErrorCode.ERR_DB_OPER_ERROR);
	      CrmData.setSuccess(false);
		  return ;
	  }
      Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
      CrmData.setRepNode(responseEle);
      return;
	}
}
