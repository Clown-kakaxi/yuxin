package com.yuchengtech.bcrm.sales.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.crm.constance.SystemConstance;

/**
 * @描述：营销管理->商机管理->商机图表统计查询Service
 * @author wzy
 * @date:2013-04-02
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class MktBusiOpporCartogramQueryService {

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 按商机状态分组查询数据
	@SuppressWarnings("rawtypes")
	public String getZTZBJsonData() {
		List rList = null;
		StringBuffer sql = null;
		Object[] objs = null;
		String resultJson = null;

		sql = new StringBuffer("select ");
		sql.append(" b.f_value, count(a.oppor_id) as totalnumber");
		sql.append(" from ocrm_f_mm_mkt_busi_oppor a");
		sql.append(" right outer join (select t.f_code, t.f_value");
		sql.append("   from ocrm_sys_lookup_item t");
		sql.append("  where t.f_lookup_id = 'BUSI_CHANCE_STATUS') b");
		sql.append(" on a.oppor_stat = b.f_code");
		sql.append(" group by b.f_code, b.f_value");
		sql.append(" order by b.f_code asc");
		rList = em.createNativeQuery(sql.toString()).getResultList();
		if (rList != null && rList.size() > 0) {
			resultJson = "";
			for (int i = 0; i < rList.size(); i++) {
				objs = (Object[]) rList.get(i);
				resultJson += (objs[0] + ",");
				resultJson += (objs[1]);
				if (i < rList.size() - 1) {
					resultJson += (",");
				}
			}
		}
		return resultJson;
	}

	// 按商机来源分组查询数据
	@SuppressWarnings("rawtypes")
	public String getLYZBJsonData() {
		List rList = null;
		StringBuffer sql = null;
		Object[] objs = null;
		String resultJson = null;

		sql = new StringBuffer("select ");
		sql.append(" b.f_value, count(a.oppor_id)");
		sql.append(" from ocrm_f_mm_mkt_busi_oppor a");
		sql.append(" right outer join (select t.f_code, t.f_value");
		sql.append("   from ocrm_sys_lookup_item t");
		sql.append("  where t.f_lookup_id = 'BUSI_CHANCE_SOURCE') b");
		sql.append(" on a.oppor_stat = b.f_code");
		sql.append(" group by b.f_code, b.f_value");
		sql.append(" order by b.f_code asc");
		rList = em.createNativeQuery(sql.toString()).getResultList();
		if (rList != null && rList.size() > 0) {
			resultJson = "";
			for (int i = 0; i < rList.size(); i++) {
				objs = (Object[]) rList.get(i);
				resultJson += (objs[0] + ",");
				resultJson += (objs[1]);
				if (i < rList.size() - 1) {
					resultJson += (",");
				}
			}
		}
		return resultJson;
	}

	// 查询商机每个月新增总数、新增成功数、新增失败数
	@SuppressWarnings("rawtypes")
	public String getQSZBJsonData() {
		List rList = null;
		StringBuffer sql = null;
		Object[] objs = null;
		String resultJson = null;

		sql = new StringBuffer("select ");
		sql.append(" (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-01') as m1a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-01'");
		sql.append("           and a.oppor_stat = '8') as m1s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-01'");
		sql.append("           and a.oppor_stat = '7') as m1f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-02') as m2a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-02'");
		sql.append("           and a.oppor_stat = '8') as m2s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-02'");
		sql.append("           and a.oppor_stat = '7') as m2f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-03') as m3a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-03'");
		sql.append("           and a.oppor_stat = '8') as m3s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-03'");
		sql.append("           and a.oppor_stat = '7') as m3f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-04') as m4a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-04'");
		sql.append("           and a.oppor_stat = '8') as m4s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-04'");
		sql.append("           and a.oppor_stat = '7') as m4f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-05') as m5a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-05'");
		sql.append("           and a.oppor_stat = '8') as m5s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-05'");
		sql.append("           and a.oppor_stat = '7') as m5f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-06') as m6a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-06'");
		sql.append("           and a.oppor_stat = '8') as m6s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-06'");
		sql.append("           and a.oppor_stat = '7') as m6f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-07') as m7a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-07'");
		sql.append("           and a.oppor_stat = '8') as m7s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-07'");
		sql.append("           and a.oppor_stat = '7') as m7f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-08') as m8a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-08'");
		sql.append("           and a.oppor_stat = '8') as m8s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-08'");
		sql.append("           and a.oppor_stat = '7') as m8f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-09') as m9a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-09'");
		sql.append("           and a.oppor_stat = '8') as m9s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-09'");
		sql.append("           and a.oppor_stat = '7') as m9f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-10') as m10a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-10'");
		sql.append("           and a.oppor_stat = '8') as m10s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-10'");
		sql.append("           and a.oppor_stat = '7') as m10f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-11') as m11a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-11'");
		sql.append("           and a.oppor_stat = '8') as m11s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-11'");
		sql.append("           and a.oppor_stat = '7') as m11f,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-12') as m12a,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-12'");
		sql.append("           and a.oppor_stat = '8') as m12s,");
		sql.append("       (select count(a.oppor_id)");
		sql.append("          from ocrm_f_mm_mkt_busi_oppor a");
		sql.append("         where to_char(a.create_date_time, 'yyyy-MM') =");
		sql.append("               to_char(sysdate, 'yyyy') || '-12'");
		sql.append("           and a.oppor_stat = '7') as m12f");
		sql.append("  from ");
		if (SystemConstance.DB_TYPE.equalsIgnoreCase("ORACLE")) {
			sql.append(" dual");
		} else if (SystemConstance.DB_TYPE.equalsIgnoreCase("DB2")) {
			sql.append(" sysibm.sysdummy1");
		} else {
			sql.append(" dual");
		}

		rList = em.createNativeQuery(sql.toString()).getResultList();
		if (rList != null && rList.size() > 0) {
			resultJson = "";
			objs = (Object[]) rList.get(0);// 只有一行数据
			for (int i = 0; i < objs.length; i++) {
				resultJson += (objs[i]);
				if (i < objs.length - 1) {
					resultJson += (",");
				}
			}
		}
		return resultJson;
	}
}