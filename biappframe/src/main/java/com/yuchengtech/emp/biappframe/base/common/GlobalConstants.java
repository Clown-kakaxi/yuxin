package com.yuchengtech.emp.biappframe.base.common;

/**
 * <pre>
 * Title:全局常量定义类
 * Description: 统一维护相关的常量 ,代码中涉及到的常量，统一在此定义，避免到处分散无法维护
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class GlobalConstants {

	// 应用的上下文路径
	public static String APP_CONTEXT_PATH;
	// 应用部署的物理路径
	public static String APP_REAL_PATH;

	// 通用的状态标识 1:有效，0：无效
	public static final String COMMON_STATUS_VALID = "1";
	public static final String COMMON_STATUS_INVALID = "0";
	
	// 通用状态：是/否
	public static final String COMMON_YES = "1";
	public static final String COMMON_NO  = "0";
	
	// 默认每月的天数
	public static int COMMON_MONTH_DAYS = 30;
	
	// 默认的用户密码
	public static String DEFAULT_PASSWD = "123456";
		
	
	// 密码Hash散列的salt值,目前是固定值，可以考虑在User表中增加一个字段动态设置该值
	public static final String CREDENTIALS_SALT = "YTEC_BIONE";

	// 授权对象定义的标识符
	public static final String AUTH_OBJ_DEF_ID_USER = "AUTH_OBJ_USER";// 用户
	public static final String AUTH_OBJ_DEF_ID_ROLE = "AUTH_OBJ_ROLE";// 角色
	public static final String AUTH_OBJ_DEF_ID_ORG = "AUTH_OBJ_ORG";// 机构
	public static final String AUTH_OBJ_DEF_ID_DEPT = "AUTH_OBJ_DEPT";// 部门
	public static final String AUTH_OBJ_DEF_ID_GROUP = "AUTH_OBJ_GROUP";// 授权对象组

	// 授权资源定义的标识符
	public static final String AUTH_RES_DEF_ID_MENU = "AUTH_RES_MENU";// 菜单资源

	// 资源定义数据来源 1、实现类方式 2、SQL语句方式
	public static final String RES_DATA_SRC_TYPE_BEAN = "1";
	public static final String RES_DATA_SRC_TYPE_SQL = "2";

	// 资源许可类型 1、操作授权 2、数据授权
	public static final String RES_PERMISSION_TYPE_OPER = "1";
	public static final String RES_PERMISSION_TYPE_DATA = "2";

	// 全部授权
	public static final String PERMISSION_ALL = "*";
	public static final String PERMISSION_NONE = "-";
	public static final String PERMISSION_SEPARATOR = ":";// 权限许可串的分割符

	// 授权许可字符串前缀 OPER：操作权限 DATA:数据权限,URL:菜单URL
	public static final String PERMISSION_PREFIX_OPER = "OPER";
	public static final String PERMISSION_PREFIX_DATA = "DATA";
	public static final String PERMISSION_PREFIX_URL = "URL";

	// 超级管理员系统标识
	//public static String SUPER_LOGIC_SYSTEM = "BIONE";
	public static String SUPER_LOGIC_SYSTEM = "ecif";

	// 超级管理员用户标志
	// public static String SUPER_USER_NO = "bione_super";

	// 主题
	public static final String THEME = "classics";

	// 图标存放地址
	public static final String ICON_URL = "images/" + THEME + "/icons";

	// 逻辑系统 配置管理员图标
	public static String LOGIC_ADMIN_ICON = "/" + ICON_URL + "/user.png";
	// 逻辑系统 配置功能点 功能默认图标
	public static String LOGIC_FUNC_ICON = "/" + ICON_URL + "/application.png";
	// 逻辑系统 配置功能点 模块默认图标
	public static String LOGIC_MODULE_ICON = "/" + ICON_URL + "/folder.png";
	// 逻辑系统 授权资源 默认图标
	public static String LOGIC_AUTH_RES_ICON = "/" + ICON_URL
			+ "/application_double.png";
	// 逻辑系统 授权对象 默认图标
	public static String LOGIC_AUTH_OBJ_ICON = "/" + ICON_URL + "/rsgl2.gif";

	//机构图标
	public static String LOGIC_ORG_ICON="/"+ICON_URL+"/house.png";
	public static String LOGIC_ALL_ICON="/"+ICON_URL+"/folder.png";
	
	// 菜单节点类型
	public static String MENU_TYPE_MODULE = "M"; // 模块
	public static String MENU_TYPE_FUNCTION = "F";// 功能

	// 任务类型
	public static String TASK_TYPE_SYSTASK = "01";// 系统任务
	public static String TASK_TYPE_EXPANDTASK = "02";// 扩展任务
	// 任务状态
	public static String TASK_STS_NORMAL = "01";// 正常
	public static String TASK_STS_STOP = "02";// 挂起
	// 导出目录
	public static String EXPORT_PATH = "/expfolder/bione";
	// 导出元数据实例
	public static String LOGIC_SYS_EXPORT_PATH = EXPORT_PATH
			+ "/logicsys_export";
	// 导入目录
	public static String IMPORT_PATH = "/import/bione";
	// 导出文件临时路径
	public static String LOGIC_SYS_IMPORT_PATH = IMPORT_PATH
			+ "/logicsys_import";

	// 系统变量信息
	// 变量类型
	public static String BIONE_SYS_VAR_TYPE_CONSTANT = "01"; // 常量
	public static String BIONE_SYS_VAR_TYPE_SQL = "02"; // SQL语句

	// 属性组件类型
	public static String BIONE_ATTR_ELEMENT_TYPE_TEXT = "01"; // 文本框
	public static String BIONE_ATTR_ELEMENT_TYPE_SELECT = "02"; // 下拉框
	public static String BIONE_ATTR_ELEMENT_TYPE_DATE = "03"; // 日期框
	public static String BIONE_ATTR_ELEMENT_TYPE_HIDDEN = "04"; // 隐藏域
	public static String BIONE_ATTR_ELEMENT_TYPE_PASSWORD = "05"; // 密码框
	public static String BIONE_ATTR_ELEMENT_TYPE_TEXTAREA = "06"; // 多行文本输入域
	
	//数据集树图标
	public static final String DATA_TREE_NODE_ICON_ROOT="/images/classics/icons/house.png";//根结点
	public static final String DATA_TREE_NODE_ICON_CATALOG="/images/classics/icons/Catalog.gif";//目录
	
	// 平台逻辑字段类型标识
	public static final String LOGIC_DATA_TYPE_TEXT = "text";// 文本
	public static final String LOGIC_DATA_TYPE_NUMBER = "number";// 数字
	public static final String LOGIC_DATA_TYPE_DATE = "date";// 日期
	public static final String LOGIC_DATA_TYPE_OBJECT = "object";// 对象

	// 系统密码安全配置数据ID默认“1”
	public static String BIONE_PWD_SECURITY_INFO_ID = "1";
	/**
	 * 报表目录树  -根节点图标 路径
	 */
	public static String TREE_ICON_ROOT = "/"+ICON_URL+"/house.png";
	/**
	 * 	报表目录树  -目录图标 路径
	 */
	public static String REPORT_CATALOG_ICON = "/"+ICON_URL+"/folder_page.png";
	/**
	 * 	报表目录树  -报表图标 路径
	 */
	public static String REPORT_ICON = "/"+ICON_URL+"/report.png";

	/**
	 * 读取引擎用
	 */
	public static String REPORT_ENGINE_READ_REPORT = "reportdata";
	
	/**
	 * 缓存报表信息
	 */
	public static String REPORT_SAVE_OBJECT_DIR="objectdata";
	/**
	 * 报表缓存方式
	 */
	public static String REPORT_SAVE_IMPL="fileReportListSaver";//当前为文件缓存
	
	/**
	 * 参数模板属性表：参数类型
	 */
	public static final String RPT_PARAMTMP_ATTRS_PARAM_TYPE_TEXT = "01";//文本
	public static final String RPT_PARAMTMP_ATTRS_PARAM_TYPE_SELECT = "02";//下拉框
	public static final String RPT_PARAMTMP_ATTRS_PARAM_TYPE_DATE = "03";//日期
	public static final String RPT_PARAMTMP_ATTRS_PARAM_TYPE_TREE = "04";//数据树
	public static final String RPT_PARAMTMP_ATTRS_PARAM_TYPE_HIDDEN = "05";//隐藏域
	/**
	 * 参数模板属性表：值类型
	 */
	public static final String RPT_PARAMTMP_ATTRS_VAL_TYPE_VALUE = "01";//定值
	public static final String RPT_PARAMTMP_ATTRS_VAL_TYPE_SQL = "02";//sql语句
	public static final String RPT_PARAMTMP_ATTRS_VAL_TYPE_SYSPARAM = "03";//系统参数（下拉框时可用）
	public static final String RPT_PARAMTMP_ATTRS_VAL_TYPE_DATASET = "04";//数据集（数据树类型时可用）
	// 数据源类型
	public static final String LOGIC_SOURCE_TYPE_TABLE = "02";// 数据库表
	public static final String LOGIC_SOURCE_TYPE_SQL = "01";// 标准SQL
	public static final String ORACLE_DATA_SOURCE = "1";// 数据源为Oracle标识
	public static final String DB2_DATA_SOURCE = "2";// 数据源为DB2标识

	/**
	 * sql语句类型
	 */
	public static final String RPT_SQL_TYPE_COMB = "0";//下拉框sql
	public static final String RPT_SQL_TYPE_TREE = "1";//数据树sql
	
}
