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

package com.yuchengtech.emp.ecif.customer.customization.constant;

/**
 * 定制查询常量类
 * @author pengsenlin
 *
 */
public class CtztConstant {    
	

	/**
	 * 客户表缩写 : CST 
	 */
    public static final String CUSTOMER_ACRONYM = "CST";
    
	/**
	 * 定制查询表类型 : 0 = 公共
	 */
    public static final String ECIF_CTZT_TABLE_TYPE_PUB = "0";
    
	/**
	 * 定制查询表类型 : 1 = 个人
	 */
    public static final String ECIF_CTZT_TABLE_TYPE_PER = "1";
    
    /**
	 * 定制查询表类型 : 2 = 机构
	 */
    public static final String ECIF_CTZT_TABLE_TYPE_ORG = "2";    

    /**
	 * 是否与客户是多对一关系 ：0 = 否
	 */
    public static final String ECIF_CTZT_IS_MANYTOONE_NO = "0";

    /**
	 * 是否与客户是多对一关系 ：1 = 是
	 */
    public static final String ECIF_CTZT_IS_MANYTOONE_YES = "1";
    
    /**
	 * 是否与客户表存在中间表关系 ：0 = 否
	 */
    public static final String ECIF_CTZT_IS_MIDDLE_TABLE_NO = "0";

    /**
	 * 是否与客户表存在中间表关系 ：1 = 是
	 */
    public static final String ECIF_CTZT_IS_MIDDLE_TABLE_YES = "1";
    
    /**
	 * 是否是查询条件 ：0 = 否
	 */
    public static final String ECIF_CTZT_IS_CONDITION_NO = "0";

    /**
	 * 是否是查询条件 ：1 = 是
	 */
    public static final String ECIF_CTZT_IS_CONDITION_YES = "1";
    
    /**
	 * 是否是查询结果：0 = 否
	 */
    public static final String ECIF_CTZT_IS_RESULT_NO = "0";

    /**
	 * 是否是查询结果 ：1 = 是
	 */
    public static final String ECIF_CTZT_IS_RESULT_YES = "1";
    
    /**
	 * 查询必有：0 = 否
	 */
    public static final String ECIF_CTZT_IS_MUST_NO = "0";

    /**
	 * 查询必有 ：1 = 是
	 */
    public static final String ECIF_CTZT_IS_MUST_YES = "1";
}
