package com.yuchengtech.bcrm.finService.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.finService.model.OcrmFFinAnaAdviseVista;
import com.yuchengtech.bcrm.finService.model.OcrmFFinBalSheet;
import com.yuchengtech.bcrm.finService.model.OcrmFFinCustIo;
import com.yuchengtech.bcrm.finService.model.OcrmFFmFinAnaIndicat;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
@SuppressWarnings("unchecked")
@Transactional(value = "postgreTransactionManager")
public class FinancialAnalysisService{
	private JPABaseDAO baseDAO;

	public JPABaseDAO getBaseDAO() {
		return baseDAO;
	}

	@Autowired
	public void setBaseDAO(JPABaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public Map amountValue(String belongType, String assetDebtType,
			String custId, String assetType) {
		Map json = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append(" select o1.F_CODE, o2.AMOUNT_VALUE,o2.ID");
		sb.append(" from OCRM_SYS_LOOKUP_ITEM o1");
		sb.append(" left outer join (select a.ID,a.ASSETS_TYPE, a.AMOUNT_VALUE, a.CUST_ID");
		sb.append(" from OCRM_F_FIN_BAL_SHEET a");
		sb.append(" where a.BELONG_TYPE = '" + belongType + "'");
		sb.append(" and a.ASSET_DEBT_TYPE = '" + assetDebtType + "'");
		sb.append(" and a.CUST_ID = '" + custId
				+ "') o2 on o1.F_CODE = o2.ASSETS_TYPE");
		sb.append(" where o1.F_LOOKUP_ID = '" + assetType + "'");
		sb.append(" order by o1.f_code");

		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());
		List data = new ArrayList();
		if (list != null && list.size() > 0) {
			for (Object[] o : list) {
				Map m = new HashMap();
				m.put("ASSETS_TYPE", o[0]);
				if(o[1]!=null&&o[1].toString().equals("0")){
				m.put("AMOUNT_VALUE", "0.00");
				}else m.put("AMOUNT_VALUE",o[1]);
				m.put("INFO_ID", o[2]);
				m.put("CUST_ID", custId);
				// 用于判断客户是否对该类型的资产进行过统计
				if (o[1] != null && !o[1].equals(""))
					m.put("flag", "1");
				else
					m.put("flag", "0");

				data.add(m);
			}
		}
		json.put("data", data);
		return json;
	}

	public Map monthValue(String ioType, String custId) {
		Map map = new HashMap();
		String lookupId = null;
		if (ioType.equals("1")) {
			lookupId = "IN_TYPE";
		} else if (ioType.equals("2")) {
			lookupId = "OUT_TYPE";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" select o1.F_CODE, o2.MONEY, o2.ID");
		sb.append(" from OCRM_SYS_LOOKUP_ITEM o1");
		sb.append(" left outer join (select a.DETIAL_TYPE, a.MONEY, a.ID");
		sb.append(" from OCRM_F_FIN_CUST_IO a");
		sb.append(" where a.IO_TYPE = '" + ioType + "'");
		sb.append(" and a.CUST_ID = '" + custId
				+ "') o2 on o1.F_CODE =o2.DETIAL_TYPE");
		sb.append(" where o1.F_LOOKUP_ID = '" + lookupId + "'");
		sb.append(" order by o1.f_code");
		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());
		List data = new ArrayList();
		if (list != null && list.size() > 0) {
			for (Object[] o : list) {
				Map m = new HashMap();
				m.put("DETIAL_TYPE", o[0]);
				if(o[1]!=null&&o[1].toString().equals("0")){
					m.put("MONEY", "0.00");
				}else 
				m.put("MONEY", o[1]);
				m.put("INFO_ID", o[2]);
				m.put("CUST_ID", custId);
				// 用于判断客户是否对该类型的资产进行过统计
				if (o[1] != null && !o[1].equals(""))
					m.put("flag", "1");
				else
					m.put("flag", "0");

				data.add(m);
			}
		}
		map.put("data", data);
		return map;
	}

	public Map findAsset(String custId, String instCode) {
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT NVL(p.prod_name, A.PRODUCT_ID) AS prod_name, sum(a.BAL_RMB) as BAL");
		sql.append(" FROM ACRM_F_DP_SAVE_INFO A");
		sql.append(" left join ocrm_f_pd_prod_info p");
		sql.append(" on p.product_id = a.product_id");
		sql.append(" where   A.STATUS='A' and  A.CUST_ID = '"+ custId +"'" );
		sql.append(" group by a.product_id, p.prod_name");
		
		
//		sql.append(" select a1.DEPS_BAL,a1.FINA_BAL,a1.BOND_BAL,a1.CREDIT_CARD_BAL,a1.FUND_BAL");
//		sql.append(" from ACRM_F_AG_BUSINESS_SUM a1");
//		sql.append(" where 1=1");
//		sql.append(" and a1.CUST_ID='" + custId + "'");
//		sql.append(" and a1.INST_CODE='" + instCode + "'");
		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sql
				.toString());
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Object[] o = list.get(i);
				if (o[1] == null || o[1].equals(""))
					o[1] = 0;
				map.put(o[0], o[1]);
			}
			
		} else {
			map.put("本行资产", "0");
		}
		return map;
	}

	public Map findOther(String custId, String belongType,String lookUpId) {
		Map map = new HashMap();

		StringBuffer sb = new StringBuffer();
		sb.append(" select o1.F_VALUE, o2.AMOUNT_VALUE");
		sb.append(" from OCRM_SYS_LOOKUP_ITEM o1");
		sb.append(" left outer join (select a.ASSETS_TYPE, a.AMOUNT_VALUE");
		sb.append(" from OCRM_F_FIN_BAL_SHEET a");
		sb.append(" where a.BELONG_TYPE = '" + belongType + "'");
		sb.append(" and a.ASSET_DEBT_TYPE = '1'");
		sb.append(" and a.CUST_ID = '" + custId
				+ "') o2 on o1.F_CODE = o2.ASSETS_TYPE");
		sb.append(" where o1.F_LOOKUP_ID = '"+lookUpId+"'");
		sb.append(" order by o1.f_code");

		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());

		for (Object[] o : list) {
			if (o[1] == null || o[1].equals(""))
				o[1] = 0;
			map.put(o[0], o[1]);
		}
		return map;
	}

	public Map findHome(String custId) {
		Map map = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append(" select o1.F_VALUE, o2.MONEY");
		sb.append(" from OCRM_SYS_LOOKUP_ITEM o1");
		sb.append(" left outer join (select a.DETIAL_TYPE, a.MONEY");
		sb.append(" from OCRM_F_FIN_CUST_IO a");
		sb.append(" where a.IO_TYPE = '1'");
		sb.append(" and a.CUST_ID = '" + custId
				+ "') o2 on o1.F_CODE = o2.DETIAL_TYPE");
		sb.append(" where o1.F_LOOKUP_ID = 'IN_TYPE'");
		sb.append(" order by o1.f_code");

		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());

		for (Object[] o : list) {
			if (o[1] == null || o[1].equals(""))
				o[1] = 0;
			map.put(o[0], o[1]);
		}

		return map;
	}

	public void custIoSaveOrUpdate(Map map) {
		String userId = (String) map.get("userId");
		List<String> debtInfo = (List) map.get("debtInfo");
		List<String> assetInfo = (List) map.get("assetInfo");
		for (String s : assetInfo) {
			OcrmFFinCustIo o = new OcrmFFinCustIo();
			String[] ss = s.split(":");
			o.setCustId(ss[0]);
			o.setDetialType(ss[2]);
			o.setIoType("1");
			o.setCreateDate(new Date());
			o.setCreatorId(userId);
			if (ss[1].equals("1")) {
				if (ss[3] != null && !ss[3].equals("") && !ss[3].equals("null")) {
					// 更新
					o.setId(Long.parseLong(ss[4]));
					o.setMoney(BigDecimal.valueOf(Double.valueOf(ss[3])));

					baseDAO.merge(o);
				} else {
					// 删除
					o.setId(Long.parseLong(ss[4]));
					baseDAO.remove(baseDAO.merge(o));
				}
			} else {
				if (ss[3] != null && !ss[3].equals("null") && !ss[3].equals("")) {
					// 保存
					o.setMoney(BigDecimal.valueOf(Long.parseLong(ss[3])));
					StringBuffer sb = new StringBuffer();
					sb.append(" select id");
					sb.append(" from OCRM_F_FIN_CUST_IO");
					sb.append(" where io_type = '" + o.getIoType() + "'");
					sb.append(" and detial_type = '" + o.getDetialType() + "'");
					sb.append(" and cust_id = '" + o.getCustId() + "'");
					List list = baseDAO.findByNativeSQLWithIndexParam(sb
							.toString());
					if (list != null && list.size() > 0) {
						String id = list.get(0).toString();
						o.setId(Long.parseLong(id));
						baseDAO.merge(o);
					} else
						baseDAO.save(o);
				}
			}
		}

		for (String s : debtInfo) {
			OcrmFFinCustIo o = new OcrmFFinCustIo();
			String[] ss = s.split(":");
			o.setCustId(ss[0]);
			o.setDetialType(ss[2]);
			o.setIoType("2");
			o.setCreateDate(new Date());
			o.setCreatorId(userId);

			if (ss[1].equals("1")) {
				if (ss[3] != null && !ss[3].equals("null") && !ss[3].equals("")) {
					// 更新
					o.setId(Long.parseLong(ss[4]));
					o.setMoney(BigDecimal.valueOf(Long.parseLong(ss[3])));
					baseDAO.merge(o);
				} else {
					// 删除
					o.setId(Long.parseLong(ss[4]));
					baseDAO.remove(baseDAO.merge(o));
				}
			} else {
				if (ss[3] != null && !ss[3].equals("null") && !ss[3].equals("")) {
					o.setMoney(BigDecimal.valueOf(Long.parseLong(ss[3])));
					StringBuffer sb = new StringBuffer();
					sb.append(" select id");
					sb.append(" from OCRM_F_FIN_CUST_IO");
					sb.append(" where io_type = '" + o.getIoType() + "'");
					sb.append(" and detial_type = '" + o.getDetialType() + "'");
					sb.append(" and cust_id = '" + o.getCustId() + "'");
					List list = baseDAO.findByNativeSQLWithIndexParam(sb
							.toString());
					if (list != null && list.size() > 0) {
						String id = list.get(0).toString();
						o.setId(Long.parseLong(id));
						baseDAO.merge(o);
					} else
						baseDAO.save(o);
				}
			}
		}

		baseDAO.flush();
	}

	public void finInfoSaveOrUpdate(Map map) {

		String userId = (String) map.get("userId");
		String belongType = (String) map.get("belongType");
		List<String> debtInfo = (List) map.get("debtInfo");
		List<String> assetInfo = (List) map.get("assetInfo");
		// 保存资产信息
		for (String s : assetInfo) {
			OcrmFFinBalSheet o = new OcrmFFinBalSheet();
			String[] ss = s.split(":");
			o.setCustId(ss[0]);
			o.setAssetDebtType("1");
			o.setAssetsType(ss[2]);
			o.setBelongType(belongType);
			o.setCreateDate(new Date());
			o.setCreatorId(userId);
			if (ss[1].equals("1")) {
				if (ss[3] != null && !ss[3].equals("") && !ss[3].equals("null")) {
					// 更新
					o.setId(Long.parseLong(ss[4]));//更改后,设置新ID
					o.setAmountValue(BigDecimal.valueOf(Double
							.parseDouble(ss[3])));
					baseDAO.merge(o);
				} else {
					// 删除
					o.setId(Long.parseLong(ss[4]));
					baseDAO.remove(baseDAO.merge(o));
				}
			} else {
				if (ss[3] != null && !ss[3].equals("null") && !ss[3].equals("")) {
					// 保存 //如果没有修改...就不要存了？ 也不行，需要用id回刷
					o.setAmountValue(BigDecimal.valueOf(Double
							.parseDouble(ss[3])));
					StringBuffer sb = new StringBuffer();
					sb.append(" select id");
					sb.append(" from OCRM_F_FIN_BAL_SHEET");
					sb.append(" where assets_type = '" + o.getAssetsType()
							+ "'");
					sb.append(" and asset_debt_type = '" + o.getAssetDebtType()
							+ "'");
					sb.append(" and belong_type = '" + o.getBelongType() + "'");
					sb.append(" and cust_id='" + o.getCustId() + "'");
					List list = baseDAO.findByNativeSQLWithIndexParam(sb
							.toString());
					if (list != null && list.size() > 0) {
						String id = list.get(0).toString();
						o.setId(Long.parseLong(id));
						baseDAO.merge(o);
					} else
						baseDAO.save(o);//没改动.ID还查不到,可能是没有ID, 保存一下结束
				}
			}
		}

		// 保存负债信息
		for (String s : debtInfo) {
			OcrmFFinBalSheet o = new OcrmFFinBalSheet();//【换新的！！】
			String[] ss = s.split(":");
			o.setCustId(ss[0]);
			o.setAssetDebtType("2");
			o.setAssetsType(ss[2]);
			o.setBelongType(belongType);
			o.setCreateDate(new Date());
			o.setCreatorId(userId);

			if (ss[1].equals("1")) {
				if (ss[3] != null && !ss[3].equals("null") && !ss[3].equals("")) {
					// 更新
					o.setId(Long.parseLong(ss[4]));
					o.setAmountValue(BigDecimal.valueOf(Double
							.parseDouble(ss[3])));
					baseDAO.merge(o);
				} else {
					// 删除
					o.setId(Long.parseLong(ss[4]));
					baseDAO.remove(baseDAO.merge(o));
				}
			} else {
				if (ss[3] != null && !ss[3].equals("null") && !ss[3].equals("")) {
					// 保存
					o.setAmountValue(BigDecimal.valueOf(Double
							.parseDouble(ss[3])));
					StringBuffer sb = new StringBuffer();
					sb.append(" select id");
					sb.append(" from OCRM_F_FIN_BAL_SHEET");
					sb.append(" where assets_type = '" + o.getAssetsType()
							+ "'");
					sb.append(" and asset_debt_type = '" + o.getAssetDebtType()
							+ "'");
					sb.append(" and belong_type = '" + o.getBelongType() + "'");
					sb.append(" and cust_id='" + o.getCustId() + "'");
					List list = baseDAO.findByNativeSQLWithIndexParam(sb
							.toString());
					if (list != null && list.size() > 0) {
						String id = list.get(0).toString();
						o.setId(Long.parseLong(id));
						baseDAO.merge(o);
					} else
						baseDAO.save(o);
				}
			}
		}

		baseDAO.flush();

	}

	public Map findCustAllAssetAndDebt(String custId, String instCode) {
		Map map = new HashMap();
		Double bankAssetSum = 0.00;
		Double bankDebtSum = 0.00;
		Double bankNetAsset = 0.00;

		Double otherAssetSum = 0.00;
		Double otherDebtSum = 0.00;
		Double otherNetAsset = 0.00;

		Double anotherAssetSum = 0.00;
		Double anotherDebtSum = 0.00;
		Double anotherNetAsset = 0.00;

		Double monthIn = 0.00;
		Double monthOut = 0.00;
		Double monthNet = 0.00;

		Double assetSum = 0.00;
		Double debtSum = 0.00;
		Double newAsset = 0.00;

		List<Object[]> list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" select  belong_type,asset_debt_type, sum(amount_value)");
		sb.append(" from OCRM_F_FIN_BAL_SHEET");
		sb.append(" where cust_id = '" + custId + "'");
		sb.append(" group by asset_debt_type, belong_type");
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		for (Object[] o : list) {
			if (o[0].equals("1") && o[2] != null && !o[2].equals("")) {
				if (o[1].equals("1")) {
					otherAssetSum = Double.valueOf(o[2].toString());
				} else if (o[1].equals("2")) {
					otherDebtSum = Double.valueOf(o[2].toString());
				}
			} else if (o[0].equals("2") && o[2] != null && !o[2].equals("")) {
				if (o[1].equals("1")) {
					anotherAssetSum = Double.valueOf(o[2].toString());
				} else if (o[1].equals("2")) {
					anotherDebtSum = Double.valueOf(o[2].toString());
				}
			}
		}
		otherNetAsset = otherAssetSum - otherDebtSum;
		anotherNetAsset = anotherAssetSum - anotherDebtSum;
		sb = new StringBuffer();
		//资产
		sb.append(" SELECT NVL(p.prod_name, A.PRODUCT_ID) AS prod_name,sum(a.BAL_RMB) as BAL");
		sb.append(" FROM ACRM_F_DP_SAVE_INFO A");
		sb.append(" left join ocrm_f_pd_prod_info p");
		sb.append(" on p.product_id = a.product_id");
		sb.append(" where a.cust_ID = '"+ custId +"'");
		sb.append(" group by a.product_id, p.prod_name");
		list.clear();
//		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
//		if (list != null && list.size() > 0) {
//			Object[] o = list.get(0);
//			if (o[1] != null && !o[1].equals("")) {
//				bankAssetSum = Double.valueOf(o[1].toString());
//			}
//		}
		list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Object[] o = list.get(i);
				if (o[1] == null || o[1].equals("")){
					o[1] = 0;
				}else{
					bankAssetSum+=Double.valueOf(o[1].toString());
				}
			}
			
		} 
		sb = new StringBuffer();
		//负债
		sb.append(" SELECT NVL(p.prod_name, A.PRODUCT_ID) AS prod_name, sum(a.BAL) as BAL");
		sb.append(" FROM ACRM_F_CI_ASSET_BUSI_PROTO A");
		sb.append(" left join ocrm_f_pd_prod_info p");
		sb.append(" on p.product_id = a.product_id");
		sb.append(" where a.cust_ID = '"+ custId +"'");
		sb.append(" group by a.product_id, p.prod_name");
		list.clear();
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		if (list != null && list.size() > 0) {
			Object[] o = list.get(0);
			if (o[1] != null && !o[1].equals("")) {
				bankDebtSum = Double.valueOf(o[1].toString());
			}
		}
		bankNetAsset = bankAssetSum - bankDebtSum;
		
//		sb.append(" select asset_sum, liab_sum, net_assets");
//		sb.append(" from ACRM_F_AG_BUSINESS_SUM");
//		sb.append(" where cust_id = '" + custId + "'");
//		sb.append(" and inst_code = '" + instCode + "'");
//		list.clear();
//		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
//		if (list != null && list.size() > 0) {
//			Object[] o = list.get(0);
//			if (o[0] != null && !o[0].equals("")) {
//				bankAssetSum = Double.valueOf(o[0].toString());
//			}
//			if (o[1] != null && !o[1].equals("")) {
//				bankDebtSum = Double.valueOf(o[1].toString());
//			}
//			if (o[2] != null && !o[2].equals("")) {
//				bankNetAsset = Double.valueOf(o[2].toString());
//			}
//
//		}

		sb = new StringBuffer();
		list.clear();
		sb.append(" select IO_TYPE, sum(MONEY)");
		sb.append(" from OCRM_F_FIN_CUST_IO");
		sb.append(" where CUST_ID = '" + custId + "'");
		sb.append(" group by IO_TYPE");
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		for (Object[] o : list) {

			if (o[0].equals("1") && o[1] != null && !o[1].equals("")) {
				monthIn = Double.valueOf(o[1].toString());
			} else if (o[0].equals("2") && o[1] != null && !o[1].equals("")) {
				monthOut = Double.valueOf(o[1].toString());
			}

		}
		monthNet = monthIn - monthOut;
		assetSum = bankAssetSum + otherAssetSum + anotherAssetSum + monthIn;
		debtSum = bankDebtSum + otherDebtSum + anotherDebtSum + monthOut;
		newAsset = bankNetAsset + otherNetAsset + anotherNetAsset + monthNet;

		map.put("bankAssetSum", bankAssetSum);
		map.put("bankDebtSum", bankDebtSum);
		map.put("bankNetAsset", bankNetAsset);
		map.put("otherAssetSum", otherAssetSum);
		map.put("otherDebtSum", otherDebtSum);
		map.put("otherNetAsset", otherNetAsset);
		map.put("anotherAssetSum", anotherAssetSum);
		map.put("anotherDebtSum", anotherDebtSum);
		map.put("anotherNetAsset", anotherNetAsset);
		map.put("monthIn", monthIn);
		map.put("monthOut", monthOut);
		map.put("monthNet", monthNet);
		map.put("assetSum", assetSum);
		map.put("debtSum", debtSum);
		map.put("newAsset", newAsset);

		return map;
	}

	public void finIndexSaveOrUpdate(String custId, String userId,
			List<String> list, List<String> list2) {

		for (String s : list) {

			OcrmFFmFinAnaIndicat o = new OcrmFFmFinAnaIndicat();
			String[] ss = s.split(":");//下放至 ss[0] ss[1]
			o.setCustId(custId);
			o.setUpdateDate(new Date());
			o.setUpdaterId(userId);//updateId, 由机构号得到userId
			o.setTarget(ss[0]);//code 是target（1-7）
			o.setProposal(ss[1]);//建议

			// 保存

			StringBuffer sb = new StringBuffer();
			sb.append(" select ID");
			sb.append(" from OCRM_F_FIN_ANA_INDICAT");
			sb.append(" where CUST_ID = '" + custId + "'");
			sb.append(" AND TARGET = '" + o.getTarget() + "'");

			List idList = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
			if (idList != null && idList.size() > 0) {
				String id = idList.get(0).toString();
				o.setId(Long.parseLong(id));
				baseDAO.merge(o);
			} else
				baseDAO.save(o);
		}
		//保存建议数据
		OcrmFFinAnaAdviseVista o2 = new OcrmFFinAnaAdviseVista();
		Map<String, String> map = new HashMap<String, String>();
		for(String k : list2){
			String[] kk=k.split(":");
			if(kk.length>1){
				map.put(kk[0], kk[1]);
			}
		}
		o2.setFzzzcbldpAdvise(map.get("dp_1"));
		o2.setFzsrbldpAdvise(map.get("dp_2"));
		o2.setLdxbldpAdvise(map.get("dp_3"));
		o2.setCxbldpAdvise(map.get("dp_4"));
		o2.setTzyjzcbldplAdvise(map.get("dp_5"));
		o2.setXfldpAdvise(map.get("dp_6"));
		o2.setLccjldpAdvise(map.get("dp_7"));
		o2.setSyldpAdvise(map.get("dp_8"));
		o2.setZhdpAdvise(map.get("dp_9"));
		o2.setCustId(custId);
		StringBuffer sb2 = new StringBuffer();
		sb2.append("SELECT CUST_ID ");
		sb2.append("FROM OCRM_F_FIN_ANA_ADVISE_VISTA ");
		sb2.append("WHERE CUST_ID = '"+ custId +"' ");
		List idList2 = baseDAO.findByNativeSQLWithIndexParam(sb2.toString());
		if(idList2 != null && idList2.size() > 0){
			StringBuffer sb3 = new StringBuffer();
			sb3.append("delete from OCRM_F_FIN_ANA_ADVISE_VISTA ");
			sb3.append("where CUST_ID = '"+ custId +"'");
			baseDAO.getEntityManager().createNativeQuery(sb3.toString()).executeUpdate();
			baseDAO.save(o2);
		}
		baseDAO.save(o2);
	}

	public Map findFinIndex(String custId, String instCode) {
		Map map = new HashMap();
		StringBuffer sb = new StringBuffer();

		String[] finIndex = new String[7];
		String[] proposal = new String[7];
		String[] value = new String[7];
		finIndex[0] = "如果为负，则说明目前的财务状况不容乐观，有必要将近期的债务尽快偿还，同时尽快增加收入。";
		proposal[0] = "差值应该为正";
		finIndex[1] = "建议控制在50%左右。如果净资产偿付比例太低，意味着现在的生活靠借债来维持，一旦债务到期或经济不景气时，资产出现损失，可能资不抵债。如果比例很高，接近1，意味信用额度未得到充分利用，应通过借款来优化其财务结构。";
		proposal[1] = "控制在0.5较为适宜";
		finIndex[2] = "建议控制在50%以下，以减少由于资产流动性不足而出现财务危机的可能。";
		proposal[2] = "比率应该小于0.5";
		finIndex[3] = "健康的家庭偿债能力在1.5以上。";
		proposal[3] = "比率应该大于1.5";
		finIndex[4] = "健康的储蓄投资能力要保持在0.2-0.3以上。";
		proposal[4] = "比率应该在0.2-0.3之间";
		finIndex[5] = "此指标逐年下降为好。";
		proposal[5] = "此比率逐年下降为好";
		finIndex[6] = "如净资产收入比<0.5，说明有必要控制开支，需要更多地进行储蓄或投资，同时努力工作使收入增加。如0.5<净资产收入比<3，适合较年轻客户，若已接近退休年龄，则有必要采取措施增加其净资产。如净资产收入比>3,说明目前的财务状况良好。";
		proposal[6] = "比率应该大于1.5";

		sb.append(" select O1.F_CODE,O2.PROPOSAL,O2.ID");
		sb.append(" from OCRM_SYS_LOOKUP_ITEM O1");
		sb
				.append(" LEFT OUTER JOIN OCRM_F_FIN_ANA_INDICAT O2 ON O1.F_CODE = O2.TARGET");
		sb.append(" AND O2.CUST_ID = '"+custId+"'");
		sb.append(" where F_LOOKUP_ID = 'FIN_INDEX'");
        sb.append(" order by O1.F_CODE asc");//增加排序 修改人兰超 2012-07-26
		List<Object[]> list = baseDAO.findByNativeSQLWithIndexParam(sb
				.toString());//结果[3]*X
		Map<String, Double> assetDebtMap = this.findCustAllAssetAndDebt(custId,
				instCode);//instCode就是机构号//在这里获取15值
		Double v;
		// 净资产
		value[0] = assetDebtMap.get("newAsset").toString();

		// 总资产自有权益比例=净资产/总资产
		v = (assetDebtMap.get("newAsset") / assetDebtMap.get("assetSum"))*100;
		if(assetDebtMap.get("assetSum")==null||assetDebtMap.get("assetSum").equals(0.0)){
			value[1]=new String("#1#");
		}else{
			value[1] = v.toString()+"%"; //增加%号和*100 修改人兰超 2012-07-26	
		}
		

		// 总资产负债比例=负债/总资产
		v = (assetDebtMap.get("debtSum") / assetDebtMap.get("assetSum"))*100;
		if(assetDebtMap.get("assetSum")==null||assetDebtMap.get("assetSum").equals(0.0)){
			value[2]=new String("#1#");
		}else {
			value[2] = v.toString()+"%"; //增加%号和*100 修改人兰超 2012-07-26
		}
		
		


		// 家庭偿债能力=总资产/总负债
		v = assetDebtMap.get("assetSum") / assetDebtMap.get("debtSum") ;
		if(assetDebtMap.get("debtSum")==null||assetDebtMap.get("debtSum").equals(0.0)){
			value[3] = new String("#2#");
		}
		else{
			value[3] = v.toString();
		}
		

		// 储蓄投资能力=盈余/收入总额
		v = assetDebtMap.get("monthNet") / assetDebtMap.get("monthIn");
		if(assetDebtMap.get("monthIn")==null||assetDebtMap.get("monthIn").equals(0.0)){
			value[4] = new String("#3#");
		}
		else{
			value[4] = v.toString();
		}

		// 开源节流能力=支出/收入
		v = assetDebtMap.get("monthOut") / assetDebtMap.get("monthIn");
		if(assetDebtMap.get("monthIn")==null||assetDebtMap.get("monthIn").equals(0.0)){
			value[5] = new String("#3#");
		}
		else{
			value[5] = v.toString();
		}

		// 资产收入比率 =净资产/年收入
		v = assetDebtMap.get("newAsset") / (assetDebtMap.get("monthIn") * 12);
		if(assetDebtMap.get("monthIn")==null||assetDebtMap.get("monthIn").equals(0.0)){
			value[6] = new String("#3#");
		}
		else{
			value[6] = v.toString();
		}

		List dataList = new ArrayList();
		for (Object[] o : list) {
			Map data = new HashMap();
			data.put("CODE", o[0]);
			int i = Integer.parseInt(o[0].toString());
			data.put("MEANING", finIndex[i - 1]);
			if (o[1] == null || o[1].equals("")) {
				data.put("PROPOSAL", proposal[i - 1]);
			} else
				data.put("PROPOSAL", o[1]);
			data.put("VALUE", value[i - 1]);
			dataList.add(data);
		}
		map.put("data", dataList);
		return map;
	}
	
}
