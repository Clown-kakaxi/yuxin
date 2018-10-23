/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.util
 * @文件名：MdmConstants.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:14:54
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.util;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：MdmConstants
 * @类描述：MDM常量类
 * @功能描述:维护所有的常量
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:15:08
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:15:08
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class MdmConstants {

	public static final String TXSYSPARAMNAME = "txSystemJvmId";

	public static final char SPACE = ' ';

	/**
	 * The Constant TX_CODE_Q.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_Q = "Q";

	/**
	 * The Constant TX_CODE_S.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_S = "S";

	/**
	 * The Constant TX_CODE_K.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_K = "K";

	/**
	 * The Constant TX_CODE_A.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_A = "A";

	/**
	 * The Constant TX_CODE_U.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_U = "U";

	/**
	 * The Constant TX_CODE_D.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_D = "D";
	// 交易客户类型
	/**
	 * The Constant TX_CODE_PER.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_PER = "1"; // 个人

	/**
	 * The Constant TX_CODE_ORG.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_ORG = "2"; // 公司

	/**
	 * The Constant TX_CODE_INTER.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CODE_INTER = "3"; // 同业
	// 交易类型
	/**
	 * The Constant TX_TYPE_R.
	 * 
	 * @属性描述:
	 */
	public static final String TX_TYPE_R = "R";// 读交易

	/**
	 * The Constant TX_TYPE_W.
	 * 
	 * @属性描述:
	 */
	public static final String TX_TYPE_W = "W";// 写交易

	/**
	 * The Constant TX_DIV_INS_UPD.
	 * 
	 * @属性描述:
	 */
	public static final String TX_DIV_INS_UPD = "1"; // 区分新增与更新

	/**
	 * The Constant TX_CFG_TP_STA.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CFG_TP_STA = "0";// 标准

	/**
	 * The Constant TX_CFG_TP_CUS.
	 * 
	 * @属性描述:
	 */
	public static final String TX_CFG_TP_CUS = "1";// 扩展
	// 报文类型
	/**
	 * The Constant MSG_TYPE_REQ.
	 * 
	 * @属性描述:
	 */
	public static final String MSG_TYPE_REQ = "1";// 请求报文

	/**
	 * The Constant MSG_TYPE_RES.
	 * 
	 * @属性描述:
	 */
	public static final String MSG_TYPE_RES = "2";// 响应报文

	/**
	 * The Constant NODE_TP_C.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TP_C = "C";// 节点类型，普通节点

	/**
	 * The Constant NODE_TP_T.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TP_T = "T";// 节点类型，基于数据库表

	/**
	 * The Constant NODE_GROUP_M.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_GROUP_M = "1";// 节点组

	/**
	 * The Constant NODE_GROUP_S.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_GROUP_S = "0";// 单节点

	/**
	 * The Constant NODE_GROUP_NO_LABEL.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_GROUP_NO_LABEL = "0"; // 不需要节点组标签 1:需要(默认)

	/**
	 * The Constant NODE_GROUP_SUFFIX.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_GROUP_SUFFIX = "List";// 节点组标签名后缀

	/**
	 * The Constant QUERY_SQL_FILTER_FK_FLAG.
	 * 
	 * @属性描述:
	 */
	public static final String QUERY_SQL_FILTER_FK_FLAG = "pk_";// 构造查询SQL时，外键条件属性的前缀标识
	// 节点属性类型
	/**
	 * The Constant NODE_TYPE_FIX_STR.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_FIX_STR = "10";// 定长字符串

	/**
	 * The Constant NODE_TYPE_VARI_STR.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_VARI_STR = "20";// 可变长度

	/**
	 * The Constant NODE_TYPE_INT.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_INT = "30";// 整形

	/**
	 * The Constant NODE_TYPE_FLOAT.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_FLOAT = "40";// 浮点

	/**
	 * The Constant NODE_TYPE_DATE.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_DATE = "50";// 日期

	/**
	 * The Constant NODE_TYPE_TIME.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_TIME = "60";// 时间

	/**
	 * The Constant NODE_TYPE_TIMESTAMP.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_TIMESTAMP = "70";// 时间戳

	/**
	 * The Constant NODE_TYPE_CLOB.
	 * 
	 * @属性描述:
	 */
	public static final String NODE_TYPE_CLOB = "80";// 大文本
	// 客户识别
	// 按原客户号识别
	/**
	 * The Constant TX_DEF_GETCONTID_SRCSYSCD.
	 * 源系统编号
	 * @属性描述:
	 */
	public static final String TX_DEF_GETCONTID_SRCSYSCD = "srcSysCd";

	/**
	 * The Constant TX_DEF_GETCONTID_SRCSYSCUSTNO.
	 * 源系统客户号
	 * @属性描述:
	 */
	public static final String TX_DEF_GETCONTID_SRCSYSCUSTNO = "srcSysCustNo";
	// 按ECIF客户号识别
	/**
	 * The Constant TX_DEF_GETCONTID_ECIFCUSTNO.
	 * 
	 * @属性描述:
	 */
	public static final String TX_DEF_GETCONTID_ECIFCUSTNO = "custNo";
	// 按三证识别
	/**
	 * The Constant TX_DEF_GETCONTID_IDENTTPCD.
	 * 
	 * @属性描述:
	 */
	public static final String TX_DEF_GETCONTID_IDENTTPCD = "identType";

	/**
	 * The Constant TX_DEF_GETCONTID_IDENTNO.
	 * 
	 * @属性描述:
	 */
	public static final String TX_DEF_GETCONTID_IDENTNO = "identNo";

	/**
	 * The Constant TX_DEF_GETCONTID_IDENTNAME.
	 * 
	 * @属性描述:
	 */
	public static final String TX_DEF_GETCONTID_IDENTNAME = "identCustName";

	/**
	 * The Constant TX_DEF_TX_CODE.
	 * 
	 * @属性描述:报文定义中交易编号的节点名称
	 */
	public static final String TX_DEF_TX_CODE = "txCode";

	/**
	 * The Constant TX_CUST_TYPE.
	 * 
	 * @属性描述:客户类型
	 */
	public static final String TX_CUST_TYPE = "custType";

	/**
	 * @属性名称:TX_CUST_STATE
	 * @属性描述:客户状态名称
	 * @since 1.0.0
	 */
	public static final String TX_CUST_STATE = "custStat";

	/**
	 * @属性名称:SYSTEMJVMID
	 * @属性描述:系统JVM ID
	 * @since 1.0.0
	 */
	public static int SYSTEMJVMID;

	/**
	 * The lisence model.
	 * 
	 * @属性描述:lisence限制级别
	 */
	public static String lisenceModel;

	/**
	 * The license file.
	 * 
	 * @属性描述:lisence文件
	 */
	public static String licenseFile;

	/**
	 * The ROO t_ applicatio n_ contex t_ file.
	 * 
	 * @属性描述:Spring配置文件
	 */
	public static String ROOT_APPLICATION_CONTEXT_FILE;

	/**
	 * The MODE l_ personidentifier.
	 * 
	 * @属性描述:
	 */
	public static String MODEL_PERSONIDENTIFIER;

	/**
	 * The MODE l_ orgidentifier.
	 * 
	 * @属性描述:
	 */
	public static String MODEL_ORGIDENTIFIER;

	/**
	 * The QUER y_ t x_ re q_ prar m_ node.
	 * 
	 * @属性描述:查询交易请求报文的查询条件节点路径，相对根结点
	 */
	public static String QUERY_TX_REQ_PRARM_NODE;

	/**
	 * The MS g_ respons e_ body.
	 * 
	 * @属性描述:响应报文根节点
	 */
	public static String MSG_RESPONSE_BODY;

	/**
	 * The CUSTID.
	 * 
	 * @属性描述:客户标识
	 */
	public static String CUSTID;

	/**
	 * The SEQ_CUST_ID
	 * 
	 * @属性描述:ECIF客户号生成所依赖的数据库序列
	 */
	public static String SEQ_CUST_ID;

	/**
	 * The CUSTID_TYPE.
	 * 
	 * @属性描述:客户标识类型 S:字符型 L:数值型
	 */
	public static String CUSTID_TYPE;

	/**
	 * The T x_ cus t_ pe r_ type.
	 * 
	 * @属性描述:对私客户类型
	 */
	public static String TX_CUST_PER_TYPE;

	/**
	 * The T x_ cus t_ or g_ type.
	 * 
	 * @属性描述:对公客户类型
	 */
	public static String TX_CUST_ORG_TYPE;

	/**
	 * The T x_ cus t_ ban k_ type.
	 * 
	 * @属性描述:同业客户类型
	 */
	public static String TX_CUST_BANK_TYPE;

	/**
	 * The BLAN k_ flag.
	 * 
	 * @属性描述:空白客户标志
	 */
	public static String BLANK_FLAG;

	/**
	 * The STATE.
	 * 
	 * @属性描述:数据库中客户效状态
	 */
	public static String STATE;

	/**
	 * The HI s_ ope r_ typ e_ u.
	 * 
	 * @属性描述:操作类型：修改
	 */
	public static String HIS_OPER_TYPE_U;

	/**
	 * The HI s_ ope r_ typ e_ d.
	 * 
	 * @属性描述:// 操作类型：删除
	 */
	public static String HIS_OPER_TYPE_D;

	/**
	 * The HI s_ ope r_ typ e_ g.
	 * 
	 * @属性描述:操作类型：归并
	 */
	public static String HIS_OPER_TYPE_G;

	/**
	 * The CTRLTYP e_ query.
	 * 
	 * @属性描述: 信息分级：授权类型，查询
	 */
	public static String CTRLTYPE_QUERY;

	/**
	 * The CTRLTYP .
	 * 
	 * @属性描述: 信息分级：授权类型，写
	 */
	public static String CTRLTYPE_WRITE;

	/**
	 * The OPENIDENTFLAG.
	 * 
	 * @属性描述:开户证件标志
	 */
	public static String OPENIDENTFLAG;

	/**
	 * The AUT h_ type.
	 * 
	 * @属性描述:信息分级:授权类型
	 */
	public static String AUTH_TYPE;

	/**
	 * The AUT h_ code.
	 * 
	 * @属性描述: 信息分级：授权代码
	 */
	public static String AUTH_CODE;

	/**
	 * The NOD e_ nogrou p_ lis t_ suffix.
	 * 
	 * @属性描述:允许配置结点为单一，但数据为多条。
	 */
	public static boolean NODE_NOGROUP_LIST_SUFFIX;

	/**
	 * The T x_ xm l_ encoding.
	 * 
	 * @属性描述:XML编码字符集
	 */
	public static String TX_XML_ENCODING;

	/**
	 * The global tx info ctrl.
	 * 
	 * @属性描述:是否信息分级
	 */
	public static boolean globalTxInfoCtrl;

	/**
	 * @属性描述:码值，一致性校验信息是否加载内存
	 */
	public static boolean isLoadtoMem;

	/**
	 * @属性描述:
	 */
	public static String formSeq;

	/**
	 * The query maxsize.
	 * 
	 * @属性描述:查询最大条数
	 */
	public static int queryMaxsize;

	/**
	 * The tx db log.
	 * 
	 * @属性描述:是否保存数据库交易日志
	 */
	public static boolean txDbLog;

	/**
	 * The tx log lev.
	 * 
	 * @属性描述:数据库交易日志级别
	 */
	public static int txLogLev;

	/**
	 * The is save history.
	 * 
	 * @属性描述:是否保存历史
	 */
	public static boolean isSaveHistory;

	/**
	 * The choose save history.
	 * 
	 * @属性描述:是否选择保存历史
	 */
	public static boolean chooseSaveHistory;

	/**
	 * The holding cate.
	 * 
	 * @属性描述:资产类别码
	 */
	public static String holdingCate;

	/**
	 * The exist businesskey error.
	 * 
	 * @属性描述:业务主键不全是否报错
	 */
	public static boolean existBusinesskeyError;

	/**
	 * The global tx def check.
	 * 
	 * @属性描述:是否交易配置动态监测更新
	 */
	public static boolean globalTxDefCheck;

	/**
	 * The tx info ctrlformat.
	 * 
	 * @属性描述:信息过滤显示字符
	 */
	public static String txInfoCtrlformat;

	/**
	 * The check customer status.
	 * 
	 * @属性描述:维护交易是否检查客户状态
	 */
	public static boolean checkCustomerStatus;

	/**
	 * @属性描述:客户号,客户标识，技术主键生成规则的类名
	 */
	public static String MCIDENTIFYING;
	/**
	 * @属性描述:覆盖规则
	 */
	public static String COVERINGRULE;
	/**
	 * @属性描述:信息校验转换规则组号
	 */
	public static String INFORCHECKCONVERSION;
	/**
	 * @属性描述: 客户识别规则组号
	 */
	public static String CUSTOMERIDENTIFICATION;
	/**
	 * @属性描述: ECIF客户号识别
	 */
	public static String GETCONTIDBYECIFCUSTNO;
	/**
	 * @属性描述:证件识别
	 */
	public static String GETCONTIDBYIDENT;
	/**
	 * add by sunjing5 20170729
	 * @属性描述: 客户合并识别规则
	 */
	public static String GETCUSTINFOBYCUSTNO;
	/**
	 * @属性描述: 原客户号识别
	 */
	public static String GETCONTIDBYSRCCUSTNO;
	/**
	 * The COLUMNUTILS.
	 * 
	 * @属性描述:通用字段处理
	 */
	public static String COLUMNUTILS;

	/**
	 * The COMBIN e_ flag.
	 * 
	 * @属性描述:客户合并状态
	 */
	public static String COMBINE_FLAG;

	// 开户交易XSD校验后，针对节点默认值非空容错严格限定 TODO:读取配置文件
	public static boolean XSD_CHECK_NULL_ATTR;

	//
	public static String TX_LOCK_STAT;

	// 信用等级
	public static String CRTEDIT_GRADE_CODE;
	// 结婚证
	public static String IDENT_TYPE_MARI;
	// 台胎证
	public static String IDENT_TYPE_TWID;
	// 组织机构代码
	public static String IDENT_TYPE_NOC;
	// 国税登记证
	public static String IDENT_TYPE_NAT_TAX;
	// 地税登记证
	public static String IDENT_TYPE_LOC_TAX;
	// 外汇许可证
	public static String IDENT_TYPE_FEXC_TAX;
	// 潜在客户标志字段，1为潜在客户
	public static String IS_POTENTIAL_CUST;
	// 核心开户证件标识，is_open_acc_ident 向核心同步证件只选择核心开户证件 TODO
	public static String IS_CB_OPEN_IDENT;
	// 信贷开户证件标识，is_open_acc_ident 向信贷同步证件只选择信贷开户证件 TODO
	public static String IS_LN_OPEN_IDENT;
	// 实际经营地址
	public static String ADDR_TYPE_ACT_BUSI;
	// 法人代表户籍地址 legalReprAddr
	public static String ADDR_TYPE_LEGAL_REPR;
	// 通讯地址 postAddr
	public static String ADDR_TYPE_POST;
	// 注册登记地址 registerEnAddr
	public static String ADDR_TYPE_REG;
	
	public static String ORG_EXCE_ACT_CTL;//实际控制人
	public static String ORG_EXCE_ACT_CTL_MATE;//实际控制人配偶
	public static String ORG_EXCE_LEGAL_REPR_MATE;//法人配偶
	public static String ORG_EXCE_FINA_LINK;//财务负责人
	public static String ORG_EXCE_OP;// 企业经办人
	
	//TODO 核心系统代号
	public static String SRC_SYS_CD_CB = "CB";
	
	//TODO 信贷系统代号
	public static String SRC_SYS_CD_LN = "LN";
	
	public static String SYNC_CONTTYPE_CB_IN = "'102','103','104','209','210','211','2031','2032','2033','2041','2042','2043'";

}
