/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.comidentification
 * @文件名：GetContIdByType.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:56:36
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.biz.comidentification;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.core.EcifDealEngineFactory;
import com.ytec.mdm.integration.transaction.facade.IEcifDealEngine;
import com.ytec.mdm.service.bo.TxCustIdentifRule;
import com.ytec.mdm.service.facade.IBizGetContId;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：GetContIdByType
 * @类描述：:按类型识别，用于新增属性的识别
 * @功能描述:根据识别类型，按照源系统客户号或ECIF客户号识别
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:56:37   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:56:37
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
public class GetContIdByType implements IBizGetContId {
	private static Logger log = LoggerFactory.getLogger(GetContIdByType.class);
	/**
	 * @属性名称:txCustIdentifRuleMap
	 * @属性描述:客户识别对应MAP
	 * @since 1.0.0
	 */
	private static Map<String,TxCustIdentifRule> txCustIdentifRuleMap=new HashMap<String,TxCustIdentifRule>();
	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public void init(){
		txCustIdentifRuleMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Object[]> txCustIdentifRuleList=baseDAO.findByNativeSQLWithIndexParam("SELECT T.RULE_NO,T.RULE_NAME,T.RULE_DEF_TYPE,T.RULE_DEAL_TYPE" +
				",T.RULE_PKG_PATH,T.RULE_DEAL_CLASS,T.RULE_EXPR,T.RULE_EXPR_DESC FROM TX_BIZ_RULE_CONF T ,TX_BIZ_RULE_GROUP G WHERE G.RULE_GROUP_NO=? AND T.RULE_GROUP_ID=G.RULE_GROUP_ID AND RULE_STAT='1'",MdmConstants.CUSTOMERIDENTIFICATION);
		if(txCustIdentifRuleList!=null &&!txCustIdentifRuleList.isEmpty()){
			Class clazz=null;
			IBizGetContId cc=null;
			String szInterface=IBizGetContId.class.getName();
			for(Object[] objs:txCustIdentifRuleList){
				TxCustIdentifRule txCustIdentifRule=new TxCustIdentifRule();
				if(StringUtil.isEmpty(objs[0])){
					log.error("客户识别规则代码为空");
					continue;
				}
				txCustIdentifRule.setRuleNo((String)objs[0]);
				txCustIdentifRule.setRuleName((String)objs[1]);
				txCustIdentifRule.setRuleDefType((String)objs[2]);
				if(objs[4]==null||objs[5]==null){
					if(objs[6]==null){
						log.error("客户识别规则处理类配置为空");
						continue;
					}
				}else{
					String className=objs[4].toString()+"."+objs[5].toString();
					try{
						clazz = Class.forName(className);
						if(ReflectionUtils.isInterface(clazz, szInterface) ){
							cc=(IBizGetContId)clazz.newInstance();
							txCustIdentifRule.setBizGetContId(cc);
						}else{
							log.error("规则处理类({}),没有实现IBizGetContId接口",className);
							continue;
						}
					}catch(Exception e){
						log.error("初始化调用客户识别规则处理类错误",e);
						continue;
					}
				}
				txCustIdentifRule.setRuleExpr((String)objs[6]);
				txCustIdentifRule.setRuleDesc((String)objs[7]);
				txCustIdentifRuleMap.put(txCustIdentifRule.getRuleNo(), txCustIdentifRule);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.service.facade.IBizGetContId#bizGetContId(com.ytec.mdm.base.bo.EcifData)
	 */
	public void bizGetContId(EcifData ecifData) {
		String ruler = (String) ecifData.getCustDiscRul();
		if(StringUtil.isEmpty(ruler)){
			log.error("获取客户识别规则为空");
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE);
			return;
		}
		TxCustIdentifRule txCustIdentifRule=null;
		if((txCustIdentifRule=txCustIdentifRuleMap.get(ruler))!=null){
			if(txCustIdentifRule.getBizGetContId()!=null){
				txCustIdentifRule.getBizGetContId().bizGetContId(ecifData);
			}else{
				log.info("使用自定义客户识别交易[{}]识别客户",txCustIdentifRule.getRuleExpr());
				IEcifDealEngine txDealEngine=EcifDealEngineFactory.getTxDealEngine(txCustIdentifRule.getRuleExpr());
				if(txDealEngine!=null){
					//保存原交易码
					String txCode=ecifData.getTxCode();
					//切换识别交易码
					ecifData.setTxCode(txCustIdentifRule.getRuleExpr());
					txDealEngine.execute(ecifData);
					log.info("自定义客户识别交易:{}",ecifData.getDetailDes());
					//还原交易码
					ecifData.setTxCode(txCode);
				}else{
					log.error("获取客户识别交易{}失败",txCustIdentifRule.getRuleExpr());
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE);
				}
			}
		}else{
			log.error("获取客户识别规则{}失败",ruler);
			ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE);
		}
		return;
	}

}
