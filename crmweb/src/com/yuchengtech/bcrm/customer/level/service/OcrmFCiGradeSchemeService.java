package com.yuchengtech.bcrm.customer.level.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.customer.level.model.OcrmFCiGradeScheme;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFCiGradeSchemeService extends CommonService {
	 public OcrmFCiGradeSchemeService(){
		   JPABaseDAO<OcrmFCiGradeScheme, Long>  baseDAO=new JPABaseDAO<OcrmFCiGradeScheme, Long>(OcrmFCiGradeScheme.class);  
		   super.setBaseDAO(baseDAO);
	   }

	//启用或者禁用
	public void batchUse(HttpServletRequest request) {
		String s = request.getParameter("ids");
		String use = request.getParameter("use");
		String jql = null;
		HashMap<String, Object> values = null;
		values = new HashMap<String, Object>();
		if("yes".equals(use)){
			jql = " update OcrmFCiGradeScheme p set p.isUsed=:isUesd where p.schemeId in ("+s+")";
			values.put("isUesd", "1");
		}
		if("no".equals(use)){
		jql = " update OcrmFCiGradeScheme p set p.isUsed=:isUesd where p.schemeId in ("+s+")";
			values.put("isUesd", "0");
		}
			super.batchUpdateByName(jql, values);
	}

	// wzy,add:根据指标编码，查询指标编码及对应的指标名称，将查询结果（Map对象（key：指标编码；value：指标名称））放到对象json中
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getIndexMap(HttpServletRequest request,
			Map<String, Object> jsonMap) {
		// 指标编码前缀（如指标编码为D002003，那么前缀为D）
		String indexPre = request.getParameter("indexPre");
		// 指标编码长度（如指标编码为D002003，那么长度为7）
		int indexCodeLength = request.getParameter("indexCodeLength") == null ? 0
				: Integer.valueOf(request.getParameter("indexCodeLength"))
						.intValue();
		// 指标公式（如：D003003+D003005+D003011+D003012+D003014）
		String indexCode = request.getParameter("indexCode");
		String codeTemp = null;
		String[] codeArr = null;
		List codeList = null;
		String codeStr = null;
		StringBuffer querySql = null;
		List indexList = null;
		Object[] indexObj = null;
		if (indexCode != null && !"".equals(indexCode) && indexPre != null
				&& !"".equals(indexPre) && indexCodeLength != 0) {
			codeArr = indexCode.split(indexPre);
			if (codeArr != null && codeArr.length > 0) {
				codeList = new ArrayList();
				for (int i = 1; i < codeArr.length; i++) {
					codeTemp = codeArr[i];
					if (codeTemp != null && !"".equals(codeTemp)) {
						codeList.add(indexPre + codeTemp.substring(0, indexCodeLength - 3));
					}
				}
			}
		}
		if (codeList != null && codeList.size() > 0) {
			codeStr = "";
			for (int i = 0; i < codeList.size(); i++) {
				codeStr += ("'" + codeList.get(i) + "'");
				if (i < codeList.size() - 1) {
					codeStr += ",";
				}
			}
		}
		if (codeStr != null && codeStr.length() > 0) {
			querySql = new StringBuffer("select");
			if("IND".equals(indexPre)){
				querySql.append(" t.index_code,");
				querySql.append(" t.index_name");
				querySql.append(" from");
				querySql.append(" ocrm_f_sys_index_base t");
				querySql.append(" where");
				querySql.append(" t.index_code in (" + codeStr + ")");
			}else if("FXQ".equals(indexPre)){
				querySql.append(" t.index_code,");
				querySql.append(" t.index_name");
				querySql.append(" from");
				querySql.append(" OCRM_F_CI_ANTI_MONEY_INDEX t");
				querySql.append(" where");
				querySql.append(" t.index_code in (" + codeStr + ")");
			}
			
			indexList = super.em.createNativeQuery(querySql.toString())
					.getResultList();
		}
		if (indexList != null && indexList.size() > 0) {
			for (int i = 0; i < indexList.size(); i++) {
				indexObj = (Object[]) indexList.get(i);
				if (indexObj != null) {
					jsonMap.put((String) indexObj[0], indexObj[1]);
				}
			}
		}
	}
}
