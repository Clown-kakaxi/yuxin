package com.yuchengtech.notify.schedule;

import com.yuchengtech.bcrm.sales.model.OcrmFWpRemind;

public class NotifySchedule {
	//所属模块名称	目标表名	目标表中文名	目标表字段名	目标表字段中文名	目标表字段类型
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	INFO_ID	信息ID	NUMBER(22)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	RULE_ID	规则ID	NUMBER(22)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	RULE_CODE	规则类别编号	VARCHAR(10)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	CUST_ID	客户编号	VARCHAR(20)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	CUST_NAME	客户名称	VARCHAR(80)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	BIRTHDAY_M	客户阴历生日	DATE
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	BIRTHDAY_S	客户阳历生日	DATE
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	CHANGE_ACCOUNT	客户账户	VARCHAR(22)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	CHANGE_AMT	变动金额	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	ACCOUNT_AMT	账户余额	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	DUE_AMT	到期金额	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	PRODUCT_AMT	购买金额	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	FUND_AMT	基金确认金额	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	NEW_MGR	新客户经理	VARCHAR(25)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	OLD_MGR	原客户经理	VARCHAR(25)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	OPERATE_MGR	操作人	VARCHAR(25)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	PRODUCT_NO	产品编号	VARCHAR(50)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	PRODUCT_NAME	产品名称	VARCHAR(100)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	HAPPENED_DATE	发生日期（到期日）	VARCHAR(10)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	BEFORE_LEVEL	变动前客户级别	VARCHAR(10)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	AFTER_LEVEL	变动后客户级别	VARCHAR(10)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	SCORE_CHANGE	积分变动值	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	ACORE_AMT	积分余额	NUMBER(22,2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	ACTIVE_NAME	活动名称	VARCHAR(100)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	USER_ID	提醒接收人	VARCHAR(50)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	MSG_CRT_DATE	提醒生成日期	DATE
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	MSG_END_DATE	提醒到期日期	DATE
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	LAST_DATE	提醒剩余天数	NUMBER(2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	REMIND_REMARK	提醒内容	VARCHAR(800)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	MESSAGE_REMARK	短信内容	VARCHAR(800)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	MAIL_REMARK	邮件内容	VARCHAR(800)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	MICRO_REMARK	微信内容	VARCHAR(800)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	IF_MESSAGE	是否已发送短信	VARCHAR(2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	IF_MAIL	是否已发邮件	VARCHAR(2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	IF_MICRO	是否已发微信	VARCHAR(2)
	//提醒管理	OCRM_F_WP_REMIND	信息提醒表	IF_CALL	是否已拨打电话	VARCHAR(2)
	OcrmFWpRemind remind;

}
