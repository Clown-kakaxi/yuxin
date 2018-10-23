/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2010 CITIC Holdings All rights reserved.
 * 
 * Created on 2011-7-1
 *******************************************************************************/

package com.yuchengtech.emp.ecif.report.constant;

/**
 * 定制查询常量类
 * @author pengsenlin
 *
 */
public class ReportConstant {    
	
	/**
	 * 个人客户维度统计表查询类型 ：10 = 年龄段分析报表&最高学历分析报表
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTTYPE_AGE_EDUCATION = "10";
	/**
	 * 个人客户维度统计表查询类型 ：最高学历
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTSIGN1_QUERY_EDUCATION = "20,21,22,23,24,00";
	/**
	 * 个人客户维度统计表查询类型 ：年龄段
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTSIGN2_QUERY_AGE = "10,11,12,13,14,15,00";
	
	/**
	 * 个人客户维度统计表查询类型 ：20 = 客户的分布分析报表
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTTYPE_DISTRIBUTION = "20";
	/**
	 * 个人客户维度统计表查询类型 ：AUM等级划分
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTSIGN1_QUERY_AUMDISTRIBUTION = "30,31,32,33,34,35,36,37,38,39,00";
	
	/**
	 * 个人客户维度统计表查询类型 ：30 = 客户签约分析报表
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTSIGN = "30";
	/**
	 * 个人客户维度统计表查询类型 ：业务名称
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTSIGN2_QUERY_CSTSIGN_SIGNTYPE = "20,21,22,23,24,25,26";
	/**
	 * 个人客户维度统计表查询类型 ：AUM等级划分
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTSIGN1_QUERY_CSTSIGN_AUMDISTRIBUTION = "30,31,32,33,34,35,36,37,38,39,00";
	
	/**
	 * 个人客户维度统计表查询类型 ：40 = 客户风险等级分析报表
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTRISKGRADE = "40";
	/**
	 * 个人客户维度统计表查询类型 ：客户风险等级
	 */
	public static final String RPT_PERSON_INFO_DETAIL_REPORTSIGN1_QUERY_CSTRISKGRADE = "40,41,42,43,44,45,46,47,48,49,50,00";
	
	/**
	 * 客户账户情况表客户账户变化情况 ：0 = 新客户开户
	 */
	public static final String RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTOPEN = "0";
	
	/**
	 * 客户账户情况表客户账户变化情况 ：1 = 注销所有账户
	 */
	public static final String RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE = "1";
}
