/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.dao
 * @文件名：Oracle10gDialect.java
 * @版本信息：1.0.0
 * @日期：2014-1-17-下午12:04:31
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.dao;

import java.sql.Types;
import org.hibernate.type.StandardBasicTypes;



/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：Oracle10gDialect
 * @类描述：修改hibernate方言
 * @功能描述:修改hibernate方言 ,解决oracle 查询char返回一个字符的问题
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-1-17 下午12:04:31   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-1-17 下午12:04:31
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class Oracle10gDialect extends org.hibernate.dialect.Oracle10gDialect {

	public Oracle10gDialect() {
		super();
		// TODO Auto-generated constructor stub
		//注册CHAR类型映射为STRING
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName()); 
	}
	

}
