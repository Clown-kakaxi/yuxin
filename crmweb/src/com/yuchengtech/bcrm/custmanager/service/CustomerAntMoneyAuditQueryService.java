package com.yuchengtech.bcrm.custmanager.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiDqshInfo;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiTargetFact;
import com.yuchengtech.bcrm.custmanager.model.AcrmACustFxqIndex;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCiGradeDq;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 
 * @description :风险等级审核保存 
 *
 * @author : zhaolong
 * @date : 2016-2-3 上午11:19:08
 */
@Service("customerAntMoneyAuditQueryService")
public class CustomerAntMoneyAuditQueryService extends CommonService {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public CustomerAntMoneyAuditQueryService() {
		JPABaseDAO<AcrmAAntiDqshInfo, Long> baseDAO = new JPABaseDAO<AcrmAAntiDqshInfo, Long>(
				AcrmAAntiDqshInfo.class);
		super.setBaseDAO(baseDAO);
	}

	/*
	 * 反洗钱风险等级审核保存 (non-Javadoc)
	 * 
	 * @see com.yuchengtech.bob.common.CommonService#save(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.yuchengtech.bob.common.CommonService#save(java.lang.Object)
	 */
	public Object save(Object obj) {

		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();// 获取当前用户信息

		AcrmAAntiDqshInfo acrmAAntiDqshInfo = (AcrmAAntiDqshInfo) obj;
		// getCustGrade 评定内ID 不为空
		this.saveFXQindex(acrmAAntiDqshInfo);// 保存反洗钱指标
		// ,'INSTRUCTION' //审核结果说明
		// ,'CUST_GRADE_CHECK' //定期审核等级

		if (acrmAAntiDqshInfo.getCustGradeCheck() != null
				|| acrmAAntiDqshInfo.getInstruction() != null) {

			// 获取客户定期审核等级信息表
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
			String month = sdf.format(new Date());
			int year = Integer.valueOf((sdf2.format(new Date())));

			// 创建客户定期审核等级信息表
			AcrmFCiGradeDq gradeDq = new AcrmFCiGradeDq();
			// 设置用户id
			gradeDq.setCustId(acrmAAntiDqshInfo.getCustId());
			// 审核结束时间
			gradeDq.setCheckEndDate(acrmAAntiDqshInfo.getCheckEndDate());
			// 审核结果
			gradeDq.setCheckResult(acrmAAntiDqshInfo.getCheckResult());
			// 设置操作用户id
			gradeDq.setCheckUser(auth.getUserId());
			// 主键生成策略 用户ID+当月第一天+风险等级
			
   			
   			
   		
			// 设置审核功能开始时间
			// 高风险：每年3,9月份，审核周期当月，
			// 中风险：每年9月份，审核周期当月，
			// 低风险：奇数年的9月份，审核周期，9月1号-12月31号
			if ("H".equals(acrmAAntiDqshInfo.getCustGrade())) {
				gradeDq.setCheckRq(year + month + "01");
			} else if ("M".equals(acrmAAntiDqshInfo.getCustGrade())) {
				gradeDq.setCheckRq(year + "0901");
			} else if ("L".equals(acrmAAntiDqshInfo.getCustGrade())) {
				gradeDq.setCheckRq(year + "0901");
			}
			// 定期审核等级
			gradeDq.setCustGradeCheck(acrmAAntiDqshInfo.getCustGradeCheck());
			// 当前客户等级
			gradeDq.setCustGradeOld(acrmAAntiDqshInfo.getCustGrade());
			// 等级类型固定格式
			gradeDq.setCustGradeType("01");
			// 审核意见
			gradeDq.setInstruction(acrmAAntiDqshInfo.getInstruction());

			if (acrmAAntiDqshInfo.getInstanceid() != null
					|| "".equals(acrmAAntiDqshInfo.getInstanceid())) {
				// 审核开始时间-- 提交审核时才做
				gradeDq.setCheckStartDate(new Date());
				// 审批流程ID --提交时自动生成 根据 用户ID+CheckRq+当前风险等级
				gradeDq.setInstanceid(acrmAAntiDqshInfo.getInstanceid());
				// 审核状态，0：表示：未审核，1：表示：审核中，2：表示审核完成
				gradeDq.setCheckStatus("1");
			}

			if (acrmAAntiDqshInfo.getGradeId() != null) {
				gradeDq.setGradeId(acrmAAntiDqshInfo.getGradeId());// 添加主键

			}else{
				List<Object[]> list2=(List<Object[]>)this.getBaseDAO().findByNativeSQLWithIndexParam(
	   					" select * from ACRM_F_CI_GRADE_DQ d2 " +
	   					"where d2.CUST_ID= '"+gradeDq.getCustId()+"' " +
	   							"and d2.check_rq='"+gradeDq.getCheckRq()+"'" +
	   							"and  d2.CUST_GRADE_OLD='"+acrmAAntiDqshInfo.getCustGrade()+"'");
	   			if(list2.size()>0){
						String gradeId = list2.get(0)[11].toString();//获取list中存放的gradeId  11为存放位置
	   				gradeDq.setGradeId(gradeId);// 添加主键
	   			}
   			}
			super.save(gradeDq);
		}

		return super.save(obj);
	}

	/**
	 * 保存反洗钱指标
	 */
	private void saveFXQindex(AcrmAAntiDqshInfo fxqDq) {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String flag = fxqDq.getFlag();// 3 新客户 1,2 老客户zzzz
		String custId = fxqDq.getCustId();
		String custType = fxqDq.getCustType();// 2对私，1对公
		// 是1否0
		Map<String, String> map = new HashMap<String, String>();

		//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
		//map.put("fxq006", fxqDq.getFxq006());
		map.put("fxq007", fxqDq.getFxq007());
		map.put("fxq008", fxqDq.getFxq008());
		map.put("fxq009", fxqDq.getFxq009());
		map.put("fxq010", fxqDq.getFxq010());

		map.put("fxq011", fxqDq.getFxq011());
		map.put("fxq012", fxqDq.getFxq012());
		map.put("fxq013", fxqDq.getFxq013());
		map.put("fxq014", fxqDq.getFxq014());
		map.put("fxq015", fxqDq.getFxq015());
		map.put("fxq016", fxqDq.getFxq016());

		map.put("fxq021", fxqDq.getFxq021());
		map.put("fxq022", fxqDq.getFxq022());
		map.put("fxq023", fxqDq.getFxq023());
		map.put("fxq024", fxqDq.getFxq024());
		map.put("fxq025", fxqDq.getFxq025());
		AcrmACustFxqIndex index = new AcrmACustFxqIndex();
		// 删除原有AcrmACustFxqIndex
		super.batchUpdateByName(
				" delete from AcrmACustFxqIndex s where s.custId='" + custId
						+ "'", null);
		// 添加AcrmACustFxqIndex
		index.setCustId(custId);
		//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
		//index.setFxq006(fxqDq.getFxq006());
		index.setFxq007(fxqDq.getFxq007());
		index.setFxq008(fxqDq.getFxq008());
		index.setFxq009(fxqDq.getFxq009());
		index.setFxq010(fxqDq.getFxq010());
		index.setFxq011(fxqDq.getFxq011());
		index.setFxq012(fxqDq.getFxq012());
		index.setFxq013(fxqDq.getFxq013());
		index.setFxq014(fxqDq.getFxq014());
		index.setFxq015(fxqDq.getFxq015());
		index.setFxq016(fxqDq.getFxq016());
		index.setFxq021(fxqDq.getFxq021());
		index.setFxq022(fxqDq.getFxq022());
		index.setFxq023(fxqDq.getFxq023());
		index.setFxq024(fxqDq.getFxq024());
		index.setFxq025(fxqDq.getFxq025());
		super.save(index);

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println(nowDate);
		for (String key : map.keySet()) {
			if (null != map.get(key) && !map.get(key).equals("")) {
				try {
					conn = ds.getConnection();
					statement = conn.createStatement();

					// 删除客户原有客户反洗钱指标
					// service.batchUpdateByName(" delete from AcrmATargetFact s where s.custId='"+custId+"'",
					// null);
					// 添加新的客户反洗钱指标
					String sql = "select * from OCRM_F_CI_BELONG_ORG o where o.cust_id='"
							+ custId + "'";
					rs = statement.executeQuery(sql);
					String orgId = "";
					if (rs.next()) {
						orgId = rs.getString("INSTITUTION_CODE");
					}
					AcrmAAntiTargetFact list = new AcrmAAntiTargetFact();
					list.setCustId(custId);
					list.setEtlDate(new Date());
					/**
					 * 客户是否为代理开户
					 */
					//客户是否为代理开户 更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
					//
					/*if ("fxq006".equals(key)) {
						if ("3".equals(flag)) {// 新客户
							saveList(list, "FXQ11006", rs, statement, custId,
									orgId, map.get(key), false);
						} else {
							saveList(list, "FXQ12006", rs, statement, custId,
									orgId, map.get(key), false);
						}
					}*/
					/**
					 * 客户办理的业务FXQ007(对私),FXQ025(对公)
					 */
					if ("fxq007".equals(key) || "fxq025".equals(key)) {
						// 新客户
						if ("3".equals(flag)) {
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11007", rs, statement,
										custId, orgId, map.get(key), false);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21011", rs, statement,
										custId, orgId, map.get(key), false);
							}
							// 老客户
						} else {
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12007", rs, statement,
										custId, orgId, map.get(key), false);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22011", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}
					/**
					 * 是否涉及风险提示信息或权威媒体报道信息 FXQ008
					 */
					if ("fxq008".equals(key)) {
						// 新客户
						if ("3".equals(flag)) {
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11008", rs, statement,
										custId, orgId, map.get(key), false);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21012", rs, statement,
										custId, orgId, map.get(key), false);
							}
							// 老客户
						} else {
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12008", rs, statement,
										custId, orgId, map.get(key), false);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22012", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}
					/**
					 * 客户或其亲属、关系密切人等是否属于外国政要 FXQ009
					 */
					if ("fxq009".equals(key)) {
						
						if ("3".equals(flag)) {
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11009", rs, statement,
										custId, orgId, map.get(key), false);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21010", rs, statement,
										custId, orgId, map.get(key), false);
							}
							// 老客户
						} else {
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12009", rs, statement,
										custId, orgId, map.get(key), false);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22010", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}
					/**
					 * 反洗钱交易监测记录 FXQ010
					 */
					if ("fxq010".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12010", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22013", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11010", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21013", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}
					/**
					 * 是否被列入中国发布或承认的应实施反洗钱监控措施的名单 FXQ011
					 */
					if ("fxq011".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12011", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22014", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11011", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21014", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}
					/**
					 * 是否发生具有异常特征的大额现金交易 FXQ012
					 */
					if ("fxq012".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12012", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22015", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11012", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21015", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}
					/**
					 * 是否发生具有异常特征的非面对面交易FXQ013
					 */
					if ("fxq013".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12013", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22016", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11013", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21016", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}
					/**
					 * 是否存在多次涉及跨境异常交易报告FXQ014
					 */
					if ("fxq014".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12014", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22017", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11014", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21017", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}

					/**
					 * 代办业务是否存在异常情况FXQ015
					 */
					if ("fxq015".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12015", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22018", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11015", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21018", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}

					/**
					 * 是否频繁进行异常交易 FXQ016
					 */
					if ("fxq016".equals(key)) {
						if (!"3".equals(flag)) {// 老客户
							if ("2".equals(custType)) {// 自然人老客户
								saveList(list, "FXQ12016", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人老客户
								saveList(list, "FXQ22019", rs, statement,
										custId, orgId, map.get(key), true);
							}
						} else if ("3".equals(flag)) {// 新客户
							if ("2".equals(custType)) {// 自然人新客户
								saveList(list, "FXQ11016", rs, statement,
										custId, orgId, map.get(key), true);
							}
							if ("1".equals(custType)) {// 非自然人新客户
								saveList(list, "FXQ21019", rs, statement,
										custId, orgId, map.get(key), true);
							}
						}
					}

					/**
					 * 与客户建立业务关系的渠道FXQ021
					 */
					if ("fxq021".equals(key)) {
						if ("1".equals(custType)) {// 非自然人
							if ("3".equals(flag)) {// 非自然人新客户
								saveList(list, "FXQ21006", rs, statement,
										custId, orgId, map.get(key), false);
							} else {// 非自然人老客户
								saveList(list, "FXQ22006", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}
					/**
					 * 是否在规范证券市场上市FXQ022
					 */
					if ("fxq022".equals(key)) {
						if ("1".equals(custType)) {// 非自然人
							if ("3".equals(flag)) {// 非自然人新客户
								saveList(list, "FXQ21007", rs, statement,
										custId, orgId, map.get(key), false);
							} else {// 非自然人老客户
								saveList(list, "FXQ22007", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}

					/**
					 * 客户的股权或控制权结构FXQ023
					 */
					if ("fxq023".equals(key)) {
						if ("1".equals(custType)) {// 非自然人
							if ("3".equals(flag)) {// 非自然人新客户
								saveList(list, "FXQ21008", rs, statement,
										custId, orgId, map.get(key), false);
							} else {// 非自然人老客户
								saveList(list, "FXQ22008", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}

					/**
					 * 客户是否存在隐名股东或匿名股东FXQ024
					 */
					if ("fxq024".equals(key)) {
						if ("1".equals(custType)) {// 非自然人
							if ("3".equals(flag)) {// 非自然人新客户
								saveList(list, "FXQ21009", rs, statement,
										custId, orgId, map.get(key), false);
							} else {// 非自然人老客户
								saveList(list, "FXQ22009", rs, statement,
										custId, orgId, map.get(key), false);
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					JdbcUtil.close(rs, statement, conn);
				}
			}
		}

	}

	/**
	 * 客户反洗钱指标页面设值保存到ACRM_A_ANTI_TARGET_FACT
	 * 
	 * @param list
	 * @param indexCode
	 * @param rs
	 * @param statement
	 * @param custId
	 * @param orgId
	 * @throws SQLException
	 */
	public void saveList(AcrmAAntiTargetFact list, String indexCode,
			ResultSet rs, Statement statement, String custId, String orgId,
			String key, boolean flag) throws SQLException {
		list.setIndexCode(indexCode);
		list.setIndexId(indexCode
				+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + custId);
		list.setOrgId(orgId);
		// if(flag){//合规处指标不需要flag标记
		// list.setFlag(null);
		// }else{
		list.setFlag(key);
		// }
		String[] str = key.split(",");
		StringBuffer sb = new StringBuffer();
		for (String s : str) {
			sb.append(",'").append(s.toString()).append("'");
		}
		String sql = " select decode(sum((decode(v.high_flag,'1',1,0))),null,0,sum((decode(v.high_flag,'1',1,0)))) as high_flag,"
				+ " decode(sum(v.index_score*v.index_right*0.01),null,0,sum(v.index_score*v.index_right*0.01)) as amount"
				+ " from OCRM_F_CI_ANTI_MONEY_INDEX_VAR v"
				+ " where v.index_code ='"
				+ indexCode
				+ "' and v.index_value in (" + sb.toString().substring(1) + ")";
		rs = statement.executeQuery(sql);
		if (rs.next()) {
			list.setHighFlag(BigDecimal.valueOf(Double.parseDouble(rs
					.getString("HIGH_FLAG").toString())));
			list.setIndexValue(BigDecimal.valueOf(Double.parseDouble(rs
					.getString("AMOUNT").toString())));
		}
		// 删除客户原有客户反洗钱指标
		super.batchUpdateByName(
				" delete from AcrmAAntiTargetFact s where s.custId='" + custId
						+ "' and s.indexCode = '" + indexCode + "'", null);

		super.save(list);
	}

}
