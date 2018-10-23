package com.yuchengtech.bcrm.finService.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.system.model.OcrmFSeTitle;
import com.yuchengtech.bcrm.system.model.OcrmFSeTitleResult;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@SuppressWarnings("unchecked")
@Transactional(value = "postgreTransactionManager")
public class RiskQuessionService {
	private JPABaseDAO baseDAO;

	public JPABaseDAO getBaseDAO() {
		return baseDAO;
	}

	@Autowired
	public void setBaseDAO(JPABaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void openOrCloseQuession(String userId, String titleId,
			String available) {
		OcrmFSeTitle title = new OcrmFSeTitle();
		Map map = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append("update OcrmFSeTitle o ");
		sb.append(" set o.available=:available,o.updator=:updator,o.updateDate=:updateDate");
		sb.append(" where o.titleId=:titleId");
		map.put("available", available);
		map.put("updator", userId);
		map.put("updateDate", new Date());
		map.put("titleId", Long.parseLong(titleId));
		try {
			baseDAO.batchExecuteWithNameParam(sb.toString(), map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		StringBuffer sb = new StringBuffer();
//		sb.append(" select O.TITLE_NAME, O.TITLE_SORT, O.TITLE_TYPE");
//		sb.append(" from OCRM_F_SE_TITLE O");
//		sb.append(" where O.TITLE_ID = '" + titleId + "'");
//
//		List list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
//		if (list != null && list.size() > 0) {
//			Object[] o = (Object[]) list.get(0);
//			title.setTitleName(o[0].toString());
//			title.setTitleSort(BigDecimal.valueOf(Long.parseLong(o[1]
//					.toString())));
//			title.setTitleType(o[2].toString());
//		}
//
//		title.setTitleId(Long.parseLong(titleId));
//		title.setUpdateDate(new Date());
//		title.setUpdator(userId);
//		title.setAvailable(available);
//		baseDAO.merge(title);
		baseDAO.flush();
	}

	public Map findResult(String titleId) {
		Map json = new HashMap();
		StringBuffer sb = new StringBuffer();
		List dataList = new ArrayList();
		sb.append(" select O.RESULT, O.RESULT_SCORING, O.RESULT_SORT");
		sb.append(" from OCRM_F_SE_TITLE_RESULT O");
		sb.append(" where O.TITLE_ID='" + titleId + "'");
		sb.append(" order by O.RESULT_SORT");
		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());
		if (list != null && list.size() > 0)
			for (Object[] o : list) {
				Map map = new HashMap();
				map.put("RESULT", o[0]);
				map.put("RESULT_SCORING", o[1]);
				map.put("RESULT_SORT", o[2]);
				dataList.add(map);
			}
		json.put("data", dataList);
		return json;
	}

	public void createQuession(Map<String, Object> map) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
		OcrmFSeTitle title = new OcrmFSeTitle();

		title.setTitleIdL(new ArrayList());

		title.setUpdateDate(new Date());
		title.setUpdator(auth.getUserId());
		Long id = null;
		for (String key : map.keySet()) {
			if (null != map.get(key) && !map.get(key).equals("")) {
				if (key.equals("TITLE_ID")) {
					id = Long.valueOf((map.get(key).toString()));
					title.setTitleId(id);
				}
				if (key.equals("TITLE_REMARK")) {
					title.setTitleRemark(map.get(key).toString());
				} else if (key.equals("TITLE_NAME")) {
					title.setTitleName(map.get(key).toString());
				}  else if (key.equals("TITLE_TYPE")) {
					title.setTitleType(map.get(key).toString());
				} 
			}
		}

		title = (OcrmFSeTitle) baseDAO.save(title);

		if (map.get("resultInfo") != null && !map.get("resultInfo").equals("")) {
			List<String> list = (List) map.get("resultInfo");
			for (String s1 : list) {
				String[] s2 = s1.split(":");
				OcrmFSeTitleResult result = new OcrmFSeTitleResult();
				result.setResult(s2[0]);
				result.setResultScoring(BigDecimal.valueOf(Long
						.parseLong(s2[1])));
				result.setResultSort(BigDecimal.valueOf(Long.parseLong(s2[2])));
				result.setTitleId(title);
				baseDAO.save(result);
			}

		}
		baseDAO.flush();
	}

	public Map findRiskParam() {
		Map json = new HashMap();
		List dataList = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select O.PARAM_NAME, O.INIT_SCORE, O.END_SCORE from OCRM_F_FM_RISK_PARAM O");
		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());

		for (Object[] o : list) {
			Map map = new HashMap();
			map.put("PARAM_NAME", o[0]);
			map.put("INIT_SCORE", o[1]);
			map.put("END_SCORE", o[2]);
			dataList.add(map);
		}
		json.put("data", dataList);
		return json;
	}
	
	
}
