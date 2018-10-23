package com.yuchengtech.emp.ecif.rulemanage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.util.BeanUtils;
import com.yuchengtech.emp.ecif.rulemanage.entity.TxBizRuleConf;
import com.yuchengtech.emp.ecif.rulemanage.vo.TxBizRuleConfVO;

/**
 * <pre>
 * Description: 规则组BS 
 * </pre>	
 * @author lizongyu lizyu1@yuchengtech.com
 *
 */
@Service
@Transactional(readOnly = true)
public class TxBizRuleConfBS extends BaseBS<TxBizRuleConf>{
	
	
    public void mappingEntVo(TxBizRuleConfVO vo ,TxBizRuleConf conf){
    	vo.setApprovalOper(conf.getApprovalOper());
    	vo.setApprovalTime(conf.getApprovalTime());
    	vo.setCreateOper(conf.getCreateOper());
    	vo.setCreateTime(conf.getCreateTime());
    	vo.setEffectiveTime(conf.getEffectiveTime());
    	vo.setExpiredTime(conf.getExpiredTime());
    	vo.setParentRuleId(conf.getParentRuleId());
    	vo.setRuleBizType(conf.getRuleBizType());
    	vo.setRuleDealClass(conf.getRuleDealClass());
    	vo.setRuleDealType(conf.getRuleDealType());
    	vo.setRuleDefType(conf.getRuleDefType());
    	vo.setRuleDesc(conf.getRuleDesc());
    	vo.setRuleExpr(conf.getRuleExpr());
    	vo.setRuleExprDesc(conf.getRuleExprDesc());
    	vo.setRuleGroupId(conf.getRuleGroupId());
    	vo.setRuleId(conf.getRuleId());
    	vo.setRuleIntfType(conf.getRuleIntfType());
    	vo.setRuleName(conf.getRuleName());
    	vo.setRuleNo(conf.getRuleNo());
    	vo.setRulePkgPath(conf.getRulePkgPath());
    	vo.setRuleStat(conf.getRuleStat());
    	vo.setRuleVer(conf.getRuleVer()); 
    	vo.setUpdateOper(conf.getUpdateOper());
    	vo.setUpdateTime(conf.getUpdateTime());
    }
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxBizRuleConfVO> getRuleConfList(int pageFirstIndex,
			int pagesize, String sortname, String sortorder,
			Map<String, Object> searchCondition,String ruleGroupId) {
		StringBuffer jql = new StringBuffer("");
		if(StringUtils.isNotEmpty(ruleGroupId)){
			jql.append("select tbrc from TxBizRuleConf tbrc where tbrc.ruleGroupId = "+Long.parseLong(ruleGroupId));
		}else{
			jql.append("select tbrc from TxBizRuleConf tbrc where 1 = 1");
		}
		
		if (!searchCondition.get("jql").equals("")) {
			jql.append(" and " + searchCondition.get("jql"));
		}
		Map<String, ?> values = (Map<String, ?>) searchCondition.get("params");
		SearchResult<TxBizRuleConf> ruleList = this.baseDAO
				.findPageWithNameParam(pageFirstIndex, pagesize, jql.toString(),
						values);
		SearchResult<TxBizRuleConfVO> list = new SearchResult<TxBizRuleConfVO>();
		List<TxBizRuleConfVO> volist = new ArrayList<TxBizRuleConfVO>();
		for(int i = 0;i<ruleList.getResult().size();i++){
			TxBizRuleConfVO vo = new TxBizRuleConfVO();
			TxBizRuleConf conf = ruleList.getResult().get(i);
			vo.setRuleId(conf.getRuleId());
			vo.setRuleNo(conf.getRuleNo());
			this.mappingEntVo(vo, conf);
			if(conf.getParentRuleId()!=null)
			vo.setParentRuleName(this.getEntityById(conf.getParentRuleId()).getRuleName());	
			volist.add(vo);
		}
		list.setResult(volist);
		list.setTotalCount(ruleList.getTotalCount());
		return list;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteBatch(String[] idArr) {
		List<Long> idlist = new ArrayList<Long>();
		for (String id : idArr){
			Long lid = Long.parseLong(id);
			idlist.add(lid);
		}
		String sql;
		sql = "select  tbrc.RULE_ID from TX_BIZ_RULE_CONF tbrc where tbrc.PARENT_RULE_ID in (?0)";
		List<Long> list = this.baseDAO.createNativeQueryWithIndexParam(sql, idlist).getResultList();
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				return false;
				}
		}
		sql = "delete from TxBizRuleConf tbrc where tbrc.ruleId in (?0)";
		this.baseDAO.batchExecuteWithIndexParam(sql, idlist);
		return true;
	}

	public boolean ruleConfValid(String ruleNo, String ruleName,
			String ruleId) {
		int flag = 0;
		if(!StringUtils.isEmpty(ruleNo)){
			List<TxBizRuleConf> list = this.baseDAO.findByProperty(TxBizRuleConf.class, "ruleNo", ruleNo);
			if(list!= null&&list.size()>0){
				flag = 1;
			}
		}
		if(!StringUtils.isEmpty(ruleName)){
			List<TxBizRuleConf> list = this.baseDAO.findByProperty(TxBizRuleConf.class, "ruleName", ruleName);
			if(list!= null&&list.size()>0){
				flag = 1;
			}
		}
		if (!StringUtils.isEmpty(ruleId)) {

			String jql = "select tbrf from TxBizRuleConf tbrf where  tbrf.ruleId = ?0 and tbrf.ruleName=?1";
			List<TxBizRuleConf> list = this.baseDAO.findWithIndexParam(
					jql, Long.parseLong(ruleId), ruleName);
			if (list.size() == 1) {
				flag = 0;
			}
		}
		if(flag==1){return false;}
		return true;
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select t.ruleNo, t.ruleNo||'('||t.ruleName||')' from TxBizRuleConf t,TxBizRuleGroup g  where t.ruleGroupId=g.ruleGroupId and g.ruleGroupNo='cstidrule'  order by t.ruleNo asc");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
	}
		
	

}
