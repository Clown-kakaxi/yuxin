package com.yuchengtech.bcrm.finService.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.system.model.OcrmFSeTitle;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class TitleSearchService extends CommonService {

	public TitleSearchService() {
		JPABaseDAO<OcrmFSeTitle, Long> baseDAO = new JPABaseDAO<OcrmFSeTitle, Long>(
				OcrmFSeTitle.class);
		super.setBaseDAO(baseDAO);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> loadTitleRs() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();

		// String JQL = "select t from OcrmFSeTitle t where 1=1";
		// Query q = em.createQuery(sb.toString());
		// List<OcrmFSeTitle> rsList = q.getResultList();

		StringBuffer sb = new StringBuffer();
		sb
				.append(" select O.TITLE_ID,O.TITLE_NAME, O.TITLE_SORT, O.TITLE_TYPE");
		sb.append(" from OCRM_F_SE_TITLE O");
		sb.append(" where O.AVAILABLE = '1'");
		sb.append(" order by O.TITLE_SORT");
		List<Object[]> rsList = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());

		for (Object[] o : rsList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("titleId", o[0]);
			map.put("titleName", o[1]);
			// map.put("titleRemark", ost.getTitleRemark());
			// map.put("qaId", ost.getQaId());
			sb = new StringBuffer();
			sb
					.append(" select O.RESULT_ID,O.RESULT, O.RESULT_SCORING, O.TITLE_ID");
			sb.append(" from OCRM_F_SE_TITLE_RESULT O");
			sb.append(" where O.TITLE_ID = '" + o[0] + "'");
			sb.append(" order by O.RESULT_SORT");

			List titleIdL = new ArrayList();

			List<Object[]> r_mapList = baseDAO.findByNativeSQLWithIndexParam(sb
					.toString());
			for (Object[] o2 : r_mapList) {
				HashMap<String, Object> rsmap = new HashMap<String, Object>();
				rsmap.put("resultId", o2[0]);
				rsmap.put("result", o2[1]);
				rsmap.put("resultScoring", o2[2]);
				rsmap.put("titleId", o2[3]);
				titleIdL.add(rsmap);
			}

			map.put("titleIdL", titleIdL);
			rowsList.add(map);
		}
		result.put("data", rowsList);
		result.put("count", rsList.size());
		return result;

	}

	// public Map<String, Object> loadTitleRs() {
	// Map<String, Object> result = new HashMap<String, Object>();
	// List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String,
	// Object>>();
	//
	// String JQL = "select t from OcrmFSeTitle t where 1=1";
	// Query q = em.createQuery(JQL);
	// List<OcrmFSeTitle> rsList = q.getResultList();
	//		
	// for (OcrmFSeTitle ost : rsList) {
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("titleId", ost.getTitleId());
	// map.put("titleName", ost.getTitleName());
	// map.put("titleRemark", ost.getTitleRemark());
	// map.put("qaId", ost.getQaId());
	// List r_mapList = new ArrayList();
	// if (ost.getTitleIdL() != null && ost.getTitleIdL().size() > 0) {
	// List<OcrmFSeTitleResult> list = ost.getTitleIdL();
	// OcrmFSeTitleResult ostr = list.get(0);
	// for (int i = 1; i < r_mapList.size(); i++) {
	// OcrmFSeTitleResult ostr2 = list.get(i);
	// if (Long.parseLong(ostr.getResultSort().toString()) > Long
	// .parseLong(ostr2.getResultSort().toString())) {
	// OcrmFSeTitleResult ostr3 = ostr;
	// ostr = ostr2;
	// list.add(i, ostr3);
	// }
	// }
	// }
	//
	// for (OcrmFSeTitleResult ostr : ost.getTitleIdL()) {
	// HashMap<String, Object> rsmap = new HashMap<String, Object>();
	// rsmap.put("resultId", ostr.getResultId());
	// rsmap.put("result", ostr.getResult());
	// rsmap.put("resultScoring", ostr.getResultScoring());
	// rsmap.put("titleId", ostr.getTitleId().getTitleId());
	// r_mapList.add(rsmap);
	// }
	//
	// map.put("titleIdL", r_mapList);
	// rowsList.add(map);
	// }
	// result.put("data", rowsList);
	// result.put("count", rsList.size());
	// return result;
	// }
}
