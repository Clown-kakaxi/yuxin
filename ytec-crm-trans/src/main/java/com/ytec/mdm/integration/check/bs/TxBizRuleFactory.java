/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：TxBizRuleFactory.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:46:24
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.integration.check.bs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.integration.transaction.facade.IModelFilter;
import com.ytec.mdm.integration.transaction.facade.IMsgNodeFilter;
import com.ytec.mdm.integration.transaction.model.TxBizRule;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：TxBizRuleFactory
 * @类描述：规则引擎
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:46:25
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:46:25
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public class TxBizRuleFactory {
	private static Logger log = LoggerFactory.getLogger(TxBizRuleFactory.class);
	private static Map<String,TxBizRule> txBizRuleConfMap=new HashMap<String,TxBizRule>();
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public static void init(){
		txBizRuleConfMap.clear();
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		List<Object[]> txBizRuleList=baseDAO.findByNativeSQLWithIndexParam("SELECT T.RULE_NO,T.RULE_NAME,T.RULE_DEF_TYPE,T.RULE_DEAL_TYPE" +
				",T.RULE_PKG_PATH,T.RULE_DEAL_CLASS,T.RULE_EXPR,T.RULE_EXPR_DESC FROM TX_BIZ_RULE_CONF T,TX_BIZ_RULE_GROUP G WHERE G.RULE_GROUP_NO=? and T.RULE_GROUP_ID=G.RULE_GROUP_ID AND RULE_STAT='1'",MdmConstants.INFORCHECKCONVERSION);
		if(txBizRuleList!=null &&!txBizRuleList.isEmpty()){
			Class clazz=null;
			IMsgNodeFilter cc0=null;
			IModelFilter cc1=null;
			String szInterface0=IMsgNodeFilter.class.getName();
			String szInterface1=IModelFilter.class.getName();
			for(Object[] objs:txBizRuleList){
				TxBizRule txBizRule=new TxBizRule();
				if(StringUtil.isEmpty(objs[0])){
					log.error("规则代码为空");
					continue;
				}
				txBizRule.setRuleNo((String)objs[0]);
				txBizRule.setRuleName((String)objs[1]);
				txBizRule.setRuleDefType((String)objs[2]);
				if(objs[4]==null||objs[5]==null){
					log.error("规则处理类配置为空");
					continue;
				}

				if("1".equals(objs[3])){
					if(!StringUtil.isEmpty(objs[6])){
						txBizRule.setRuleExpr(Pattern.compile((String)objs[6]));
					}else{
						log.error("规则{}的表达式为空",(String)objs[0]);
						continue;
					}
				}else{
					if(!StringUtil.isEmpty(objs[6])){
						txBizRule.setRuleExpr((String)objs[6]);
					}
				}

				String className=objs[4].toString()+"."+objs[5].toString();
				try{
					clazz = Class.forName(className);
					if(ReflectionUtils.isInterface(clazz, szInterface0) ){
						cc0=(IMsgNodeFilter)clazz.newInstance();
						txBizRule.setMsgNodeFilter(cc0);
						txBizRule.setRuleIntfType("IMsgNodeFilter");
					}else if(ReflectionUtils.isInterface(clazz, szInterface1) ){
						cc1=(IModelFilter)clazz.newInstance();
						cc1.init((String)objs[6]);
						txBizRule.setModelFilter(cc1);
						txBizRule.setRuleIntfType("IModelFilter");
					}else{
						log.error("规则处理类({}),没有实现IMsgNodeFilter或IModelFilter接口",className);
						continue;
					}
				}catch(Exception e){
					log.error("初始化调用规则处理类错误",e);
					continue;
				}

				txBizRule.setRuleDesc((String)objs[7]);
				txBizRuleConfMap.put(txBizRule.getRuleNo(), txBizRule);
			}
		}

	}
	/**
	 * @函数名称:doFilter
	 * @函数描述:信息过滤
	 * @参数与返回说明:
	 * 		@param ecifData
	 * 		@param ruler    规则
	 * 		@param attrName 属性名称
	 * 		@param value   值
	 * 		@param params 参数
	 * 		@return
	 * @算法描述:
	 */
	public static String doFilter(EcifData ecifData, String ruler,
			String attrName, String value, String... params) {
		TxBizRule txBizRule = null;
		if (ruler != null) {
			String[] rulers = ruler.split("\\,");
			for (String ruler_i : rulers) {
				if ((txBizRule = txBizRuleConfMap.get(ruler_i)) != null) {
					if (txBizRule.getMsgNodeFilter() != null) {
						String tempv = value;
						value = txBizRule.getMsgNodeFilter().execute(
								txBizRule.getRuleExpr(), value, params);
						if (value == null) {
							log.warn("属性{}:数据:{},通过校验转换规则{}({})失败", attrName,
									tempv, ruler_i, txBizRule.getRuleName());
							if (StringUtil.isEmpty(txBizRule.getRuleDesc())) {
								ecifData.setStatus(
										ErrorCode.ERR_RULE_CHECK_TRANSFORM_ERROR
												.getCode(), String.format(
												"%s:%s失败", attrName,
												txBizRule.getRuleName()));
							} else {
								ecifData.setStatus(
										ErrorCode.ERR_RULE_CHECK_TRANSFORM_ERROR
												.getCode(), String.format(
												"%s:%s失败:%s", attrName,
												txBizRule.getRuleName(),
												txBizRule.getRuleDesc()));
							}
							return null;
						}
					}
				} else {
					ecifData.setStatus(
							ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(),
							"校验规则配置错误");
					log.error("校验转换规则{}无效", ruler_i);
					return null;
				}
			}
		}
		return value;
	}

	public static boolean doFilter(EcifData ecifData,String ruler,Object entity){
		TxBizRule txBizRule=null;
		if(ruler!=null){
			String[] rulers=ruler.split("\\,");
			for(String ruler_i:rulers){
				if((txBizRule=txBizRuleConfMap.get(ruler_i))!=null){
					if(txBizRule.getModelFilter()!=null){
						boolean rul=txBizRule.getModelFilter().execute(ecifData, entity);
						if(!rul){
							log.warn("交易通过({})过滤器,通不过",txBizRule.getRuleName());
							if(ecifData.isSuccess()){
								if (StringUtil.isEmpty(txBizRule.getRuleDesc())) {
									ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(), "数据通不过(%s)过滤器",txBizRule.getRuleName());
								}else{
									ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(), txBizRule.getRuleDesc());
								}
							}
							return false;
						}
					}
				}else{
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_SUPPORT_OTHER.getCode(), "模型过滤配置错误");
					log.error("模型过滤规则{}无效",ruler_i);
					return false;
				}
			}
		}
		return true;
	}

}
