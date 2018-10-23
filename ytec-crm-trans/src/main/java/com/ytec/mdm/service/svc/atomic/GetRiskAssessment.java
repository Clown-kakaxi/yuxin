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
 * @项目名称：
 * @类名称：
 * @类描述：
 * @功能描述:
 * @修改时间：
 * @修改备注：
 * @author：豪哥哥
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
		 * 获取请求报文数据
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
		 * 交易业务处理
		 */
        //保存网银评估结果
        try{
		        log.info(txName+"正在进行中...");
		        //保存客户风险特性评估表
				OcrmFFinCustRisk ocrmffincustrisk = new OcrmFFinCustRisk();
				if(custQTId!=null && !custQTId.trim().equals("")){
					ocrmffincustrisk.setCustQId(new Long(custQTId));
				}
				ocrmffincustrisk.setCustId(custId==null?"":custId);
				if(indageteQaScoring!=null && !indageteQaScoring.trim().equals("")){
				  ocrmffincustrisk.setIndageteQaScoring(new BigDecimal(indageteQaScoring));
	            }
				//保存客户风险评估答卷表
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
					log.error("主键不存在保存失败"+"交易编码是:"+txCode);
					CrmData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_TECHNICALKEY);
					CrmData.setSuccess(false);
					return;
				}
	        }catch(Exception e){
	        	e.printStackTrace();
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
