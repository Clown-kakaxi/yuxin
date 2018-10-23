package com.ytec.mdm.base.bo;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：ErrorCode
 * @类描述：返回码定义
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午9:55:39
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午9:55:39
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class ErrorCode {
	public static final Error SUCCESS = new Error("000000|success|成功");

	public static final Error INF_QUERY_SUCCESS = new Error("000001|query success|查询成功");
	public static final Error INF_ADD_SUCCESS = new Error("000002|add success|新增成功");
	public static final Error INF_UPDATE_SUCCESS = new Error("000003|update success|修改成功");
	public static final Error INF_DELETE_SUCCESS = new Error("000004|delete success|删除成功");
	public static final Error INF_BATCH_QUERY_SUCCESS = new Error("000101|batch query success|批量查询成功");
	public static final Error INF_BATCH_ADD_SUCCESS = new Error("000102|batch add success|批量新增成功");
	public static final Error INF_BATCH_UPDATE_SUCCESS = new Error("000103|batch update success|批量更新成功");
	public static final Error INF_BATCH_DELETE_SUCCESS = new Error("000104|batch delete success|批量删除成功");
	public static final Error INF_EVENT_NOTIFICATION_SUCCESS = new Error("000201|event notification success|事件通知成功");
	public static final Error INF_PUBLISH_SUCCESS = new Error("000301|publish success|发布成功");

	public static final Error WRN_SUCCESS = new Error("010000|success with warning|成功但有警告");
	public static final Error WRN_SUCCESS_PARTIAL_NO_DATA = new Error(
			"010001|success but partial node has no data|成功但有警告(部分信息项无数据)");
	public static final Error WRN_NONE_FOUND = new Error("010002|no any record was found |未查到任何记录");
	public static final Error WRN_NONE_INSERT = new Error("010003|no any record was inserted|未插入任何记录警告");
	public static final Error WRN_NONE_UPDATE = new Error("010004|no any record was updated|未更新任何记录警告");
	public static final Error WRN_NONE_DELETE = new Error("010005|no any record was deleted|未删除任何记录警告");
	public static final Error WRN_BATCH_HAS_SKIP = new Error("010006|at least one record was skipped|批量处理有跳过记录警告");
	public static final Error WRN_BATCH_HAS_REJECT = new Error("010007|at least one record was rejected|批量处理有拒绝记录警告");
	public static final Error WRN_BATCH_HAS_FAILURE = new Error(
			"010008|at least one failure in batch process|批量处理有失败记录警告");

	public static final Error ERR_ALL = new Error(
			"100000|all error|所有错误");				//代指所有错误，包含所有错误码大于100000的错误
	public static final Error ERR_COMPRESS_FORMAT_NOT_SUPPORT = new Error(
			"100001|comperss format is not suppert|不支持的压缩格式");
	public static final Error ERR_COMPRESS_LEVEL_NOT_SUPPORT = new Error(
			"100002|comperss level is not suppert|不支持的压缩级别");

	public static final Error ERR_TX_SERVER_STAT_BLOCK = new Error(
			"109000|transaction server blocked by data process module|交易服务停用，数据批处理中");
	public static final Error ERR_COMPRESS_UNKNOWN_ERROR = new Error("109999|compress unknown error|压缩未知错误");
	public static final Error ERR_UNCOMPRESS_ERROR = new Error("110001|uncompress error|压缩内容已损坏，无法解压缩");
	public static final Error ERR_UNCOMPRESS_UNKNOWN_ERROR = new Error("119999|uncompress unknown error|解压缩未知错误");
	public static final Error ERR_ENCRYPTION_ALGORITHM_NOT_SUPPORT = new Error(
			"150001|encryption algorithm is not support|不支持的加密算法");
	public static final Error ERR_ENCRYPTION_UNKNOWN_ERROR = new Error("159999|encryption unknown error|加密未知错误");
	public static final Error ERR_DECRYPTION_ERROR = new Error("160001|decryption error|密钥不对，无法解密");
	public static final Error ERR_DECRYPTION_UNKNOWN_ERROR = new Error("169999|decryption unknown error|解密未知错误");
	public static final Error ERR_SIGNATURE_VALID_ERROR = new Error("200001|signature valid error|签名验证不通过");
	public static final Error ERR_SIGNATURE_UNKNOWN_ERROR = new Error("209999|signature unknown error|签名验证未知错误");
	public static final Error ERR_CLIENT_NOT_AUTHORIZED = new Error("250001|client not authorized|请求客户端未授权");
	public static final Error ERR_CLIENT_WAS_SUSPENDED = new Error("250002|client was suspended|请求客户端被暂停服务");
	public static final Error ERR_CLIENT_UNKNOWN_ERROR = new Error(
			"250003|client authentication unknown error|客户端身份验证未知错误");
	public static final Error ERR_CLIENT_REQUEST_NOT_FOUND = new Error("250004|client request not found|请求服务不存在");
	public static final Error ERR_CLIENT_REQUEST_NOT_AUTHORIZED = new Error(
			"250005|client request not authorized|请求服务未授权");
	public static final Error ERR_CLIENT_REQUEST_PARA_ERROR = new Error(
			"250006|client request parameter error|请求服务参数错误");
	public static final Error ERR_CLIENT_AUTHORIZED_PW = new Error("250006|client request password err|请求授权密码为空");
	public static final Error ERR_CLIENT_BAD_REQUEST = new Error("250007|client request bad|客户端请求非法");
	public static final Error ERR_CLIENT_REQUEST_UNKNOWN_ERROR = new Error("259999|client request unknown error|请求未知错误");
	// 对于交易中交易流水号、请求日期、请求时间的校验
	public static final Error ERR_REQ_INFO_MISSING_REQSEQNO = new Error(
			"270001|bad reuqest, transaction sequence NO needed|请求非法，需要交易流水号");
	public static final Error ERR_REQ_INFO_MISSING_REQDATE = new Error(
			"270002|bad reuqest, request date needed|请求非法，需要交易请求日期");
	public static final Error ERR_REQ_INFO_MISSING_REQTIME = new Error(
			"270003|bad reuqest, request time needed|请求非法，需要交易请求时间");
	public static final Error ERR_REQ_REQSEQ_TIMEOUT = new Error(
			"270004|bad reuqest, timeout while receiving request|请求非法，通过请求中交易时间信息计算出请求报文传向ECIF时间过长");
	public static final Error ERR_REQ_REQSEQ_FORMAT = new Error(
			"270004|bad reuqest, request transaction sequence NO format error|请求非法，请求交易流水号格式错误");
	public static final Error ERR_XML_CFG_DATATYPE_NOT_SUPPORT = new Error(
			"280001|xml message configure data type not support|XML报文配置数据类型不支持");
	public static final Error ERR_XML_CFG_DATAFMT_NOT_FOUND = new Error(
			"280002|xml message configure data format not found|XML报文配置数据格式未定义");
	public static final Error ERR_XML_CFG_DATA_STDCODE_NULL = new Error(
			"280003|xml message configure data has no standard code|XML报文配置属性标准代码为空");
	public static final Error ERR_XML_CFG_NO_ROOT_NODE = new Error(
			"280004|xml message configure no root node|XML报文配置没有配置根节点");
	public static final Error ERR_XML_CFG_NO_FILTER = new Error("280005|xml message configure no filter|XML报文配置找不到查询条件");
	public static final Error ERR_XML_CFG_NO_FK = new Error(
			"280006|xml message configure no foreign key|XML报文配置找不到外键参数");
	public static final Error ERR_XML_CFG_NO_CHECKINFO = new Error(
			"280007|xml message configure no check info|XML报文配置找不到校验文档");
	public static final Error ERR_XML_CHECKSUM_ERR = new Error("280008|xml message checksum info error|报文校验信息出错");

	public static final Error ERR_XML_CFG_UNKNOWN_ERROR = new Error(
			"289999|xml message configure unknown error|XML报文配置未知错误");
	public static final Error ERR_XML_IS_NULL = new Error("300001|request xml message is null|XML报文为空");
	public static final Error ERR_XML_NOT_ENCODING = new Error("300002|request xml message encoding invalid|XML报文编码不符");
	public static final Error ERR_XML_FORMAT_ERROR = new Error("300003|request xml message format error|不符合XML标准");
	public static final Error ERR_XML_FORMAT_INVALID = new Error("300004|request xml message format invalid|不符合报文接口规范");
	public static final Error ERR_XML_NO_SUCH_TXNO = new Error(
			"300005|request xml message number is not exist|XML报文编号不存在");
	public static final Error ERR_XML_NO_SUCH_TXCODE = new Error(
			"300006|request xml message transaction code is not exist|XML报文交易代码不存在");
	public static final Error ERR_XML_NO_SUCH_TXNAME = new Error(
			"300007|request xml message service name is not exist|XML报文服务名称不存在");
	public static final Error ERR_XML_FILTER_VALUE_IS_NULL = new Error(
			"300008|request xml message filter value is null|XML报文查询条件为空");
	public static final Error ERR_XML_FILTER_VALUE_CONSISTENCE = new Error(
			"300009|consistence value is invalid |报文中冗余字段值不一致");
	public static final Error ERR_XML_UNKNOWN_ERROR = new Error("309999|request xml message unknown error|XML报文未知错误");
	public static final Error ERR_FIX_IS_NULL = new Error("350001|request fixed length message is null|定长报文为空");
	public static final Error ERR_FIX_UNKNOWN_ERROR = new Error(
			"359999|request fixed length message unknown error|定长报文未知错误");
	public static final Error ERR_POJO_IS_NULL = new Error("400001|request POJO is null|JAVA对象为空");
	public static final Error ERR_POJO_UNKNOWN_ERROR = new Error("409999|request POJO unknown error|JAVA对象未知错误");
	public static final Error ERR_FILE_NOT_EXIST = new Error("450001|interface file not exist|接口文件不存在");
	public static final Error ERR_FILE_IS_NULL = new Error("450002|interface file is null|接口文件内容为空");
	public static final Error ERR_FILE_UNKNOWN_ERROR = new Error("459999|interface file unknown error|接口文件未知错误");
	public static final Error ERR_RULE_CHECK_TRANSFORM_ERROR = new Error("500000|chaeck and transform error|校验转换失败");
	public static final Error ERR_ADAPTER_TRANSFORM_ERROR = new Error("500001|adapter transform error|适配器转换失败");
	public static final Error ERR_ADAPTER_UNKNOWN_ERROR = new Error("509999|adapter unknown error|适配器转换未知错误");
	public static final Error ERR_VER_MODEL_UNCOMPATIBLE = new Error("510001|model version uncompatible|客户端模型版本过旧");
	public static final Error ERR_VER_TX_UNCOMPATIBLE = new Error("510002|transcation version uncompatible|交易配置版本过旧");
	public static final Error ERR_VER_CODE_UNCOMPATIBLE = new Error("510003|code version uncompatible|客户端标准码版本过旧");
	public static final Error ERR_VER_JRE_UNCOMPATIBLE = new Error("510004|jre version uncompatible|JRE版本不兼容");
	public static final Error ERR_VER_OTHER = new Error("519999|other version error|其他版本错误");
	public static final Error ERR_RULE_INVALID_INT = new Error("550101|invalid integer format|整型数值格式不对");
	public static final Error ERR_RULE_INVALID_LONG = new Error("550102|invalid long format|长整型数值格式不对");
	public static final Error ERR_RULE_INVALID_FLOAT = new Error("550103|invalid float format|浮点型数值格式不对");
	public static final Error ERR_RULE_INVALID_DOUBLE = new Error("550104|invalid double format|双精度浮点型数值格式不对");
	public static final Error ERR_RULE_INVALID_DATE_FORMAT = new Error("550105|invalid date format|日期格式不对");
	public static final Error ERR_RULE_INVALID_DATE = new Error("550106|invalid date|日期不对");
	public static final Error ERR_RULE_INVALID_TIME_FORMAT = new Error("550107|invalid time format|时间格式不对");
	public static final Error ERR_RULE_INVALID_TIME = new Error("550108|invalid time|时间不对");
	public static final Error ERR_RULE_INVALID_TIMESTAMP_FORMAT = new Error("550109|invalid timestamp format|时间戳格式不对");
	public static final Error ERR_RULE_INVALID_TIMESTAMP = new Error("550110|invalid timestamp|时间戳不对");
	public static final Error ERR_RULE_INVALID_DATA_FORMAT = new Error("550111|invalid data format|其他数据格式不对");
	public static final Error ERR_RULE_INVALID_DATA = new Error("550112|invalid data|其他数据不对");
	public static final Error ERR_RULE_INVALID_LENGTH = new Error("550201|invalid length|长度不符合要求");
	public static final Error ERR_RULE_INVALID_PREC = new Error("550301|invalid precision|精度不符合要求");
	public static final Error ERR_RULE_VALUE_IS_NULL = new Error("550401|value is null|不能为空");
	public static final Error ERR_RULE_OVERFLOW_RANGE = new Error("550501|value overflow the range|超出值域范围");
	public static final Error ERR_RULE_NOT_IN_LIST = new Error("550601|value not in the list|取值不在指定范围内");
	public static final Error ERR_STD_CATE_NOT_EXIST = new Error("560101|source cate is not exist|代码分类不存在");
	public static final Error ERR_STD_CODE_NOT_EXIST = new Error("560102|source code is not exist|代码不存在");
	public static final Error ERR_CODETRAN_ERROR = new Error("560201|code transform error|转码错误");
	public static final Error ERR_CODETRAN_REVE_ERROR = new Error("560202|reverse code transform error|逆转码错误");
	public static final Error ERR_CONVERT_ERROR = new Error("580001|convert error|转换错误");

	public static final Error ERR_ECIF_NULL_IDENTTP = new Error("600101|identifier type is null or blank|证件类型为空");
	public static final Error ERR_ECIF_NULL_IDENTNO = new Error("600102|identifier number is null or blank|证件号码为空");
	public static final Error ERR_ECIF_NULL_IDENTNAME = new Error("600103|contact name is null or blank|客户名称为空");
	public static final Error ERR_ECIF_NULL_SRCSYSCD = new Error("600104|source system code is null or blank|源系统代码为空");
	public static final Error ERR_ECIF_NULL_SRCSYSCUSTNO = new Error(
			"600105|source system customer number is null or blank|源系统客户号为空");
	public static final Error ERR_ECIF_NULL_ECIFCUSTNO = new Error(
			"600106|ECIF customer number is null or blank|ECIF客户号为空");
	public static final Error ERR_ECIF_NULL_CONTID = new Error("600107|contact id is null or blank|客户标识为空");
	public static final Error ERR_ECIF_NULL_ACCTNO = new Error("600108|account number is null or blank|账号为空");
	public static final Error ERR_ECIF_NULL_CARDNO = new Error("600109|card number is null or blank|卡号为空");
	public static final Error ERR_ECIF_NULL_OTHER = new Error("600199|other required data is null or blank|其他必填数据为空");
	public static final Error ERR_ECIF_INVALID_IDNO = new Error("600201|invalid identifier card number|身份证号码校验不通过");
	public static final Error ERR_ECIF_INVALID_ORGCODE = new Error("600202|invalid organization code|组织机构代码校验不通过");
	public static final Error ERR_ECIF_INVALID_LICENSE = new Error("600203|invalid business license number|营业执照号校验不通过");
	public static final Error ERR_ECIF_INVALID_IDENTNO = new Error("600299|invalid other identifier number|其他证件校验不通过");
	public static final Error ERR_ECIF_INVALID_SRCCUSTNO = new Error(
			"600301|invalid source system customer number|源系统客户号校验不通过");
	public static final Error ERR_ECIF_INVALID_ECIFCUSTNO = new Error(
			"600302|invalid ECIF customer number|ECIF客户号校验不通过");
	public static final Error ERR_ECIF_INVALID_CONTID = new Error("600303|invalid contact id|客户标识校验不通过");
	public static final Error ERR_ECIF_INVALID_ACCTNO = new Error("600401|invalid account number|账号校验不通过");
	public static final Error ERR_ECIF_INVALID_CARDNO = new Error("600402|invalid card number|卡号校验不通过");
	public static final Error ERR_ECIF_INVALID_MP = new Error("600501|invalid mobile phone number|手机号校验不通过");
	public static final Error ERR_ECIF_INVALID_TEL = new Error("600502|invalid telephone number|电话号码校验不通过");
	public static final Error ERR_ECIF_INVALID_EMAIL = new Error("600503|invalid email address|邮件地址校验不通过");
	public static final Error ERR_ECIF_INVALID_CONTMETH = new Error("600599|invalid other contact method|其他联系方式校验不通过");
	public static final Error ERR_ECIF_INVALID_BIRTHDATE = new Error("600601|invalid birth date|出生日期校验不通过");
	public static final Error ERR_ECIF_INVALID_OPENACCTDATE = new Error("600602|invalid open account date|开户日期校验不通过");
	public static final Error ERR_ECIF_INVALID_CLOSEACCTDATE = new Error("600603|invalid close account date|销户日期校验不通过");
	public static final Error ERR_ECIF_INVALID_OTHERDATE = new Error("600699|invalid other date|其他日期校验不通过");
	public static final Error ERR_ECIF_NOT_EXIST_SRCSYSCUSTNO = new Error(
			"600701|source customer number is not exist|源系统客户号不存在");
	public static final Error ERR_ECIF_NOT_EXIST_ECIFCUSTNO = new Error(
			"600702|ecif customer number is not exist|ECIF客户号不存在");
	public static final Error ERR_ECIF_NOT_EXIST_CONTID = new Error("600703|cont id is not exist|客户不存在");
	public static final Error ERR_ECIF_NOT_EXIST_ACCTNO = new Error("600704|account number is not exist|账号不存在");
	public static final Error ERR_ECIF_NOT_EXIST_CARDNO = new Error("600705|card number is not exist|卡号不存在");
	public static final Error ERR_ECIF_NOT_EXIST_IDENT = new Error(
			"600706|no identifier is exist in request XML|请求报文中不存在证件信息节点");
	public static final Error ERR_ECIF_NOT_EXIST_PK = new Error("600707|primary key is not exist|主键不存在");
	public static final Error ERR_ECIF_NOT_EXIST_TECHNICALKEY = new Error(
			"600708|technical key key is not exist|技术主键不存在");
	public static final Error ERR_ECIF_NOT_EXIST_BUSINESSKEY = new Error("600709|business key is not exist|业务主键字段未赋值");
	public static final Error ERR_ECIF_EXIST_CUST = new Error("600710|ecif customer is exist|客户已存在");
	public static final Error ERR_ECIF_CUSTNAME = new Error("600711|invalid ecif cust name |客户户名不正确");
	public static final Error ERR_ECIF_EXIST_CUST_UPDATE = new Error(
			"000000|ecif customer is exist and update customer information|客户已存在，根据请求报文更新客户信息");
	public static final Error ERR_ECIF_NOT_EXIST_OTHER = new Error("600799|other required data is not exist|其他数据不存在");

	public static final Error ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE = new Error(
			"600801|identify rule is not support|不支持的客户识别规则");
	public static final Error ERR_ECIF_NOT_SUPPORT_SUSPECT_RULE = new Error(
			"600802|suspect rule is not support|不支持的疑似客户识别规则");
	public static final Error ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE_COMB = new Error(
			"600803|identify rule combination is not support|不支持的客户识别规则组合");
	public static final Error ERR_ECIF_NOT_SUPPORT_SUSPECT_RULE_COMB = new Error(
			"600804|suspect rule combination is not support|不支持的疑似客户识别规则组合");
	public static final Error ERR_ECIF_NOT_SUPPORT_MERGE_RULE = new Error(
			"600805|customer merge rule is not support|不支持的客户归并规则");
	public static final Error ERR_ECIF_NOT_SUPPORT_MERGE_METHOD = new Error(
			"600806|customer merge method is not support|不支持的客户归并方式");
	public static final Error ERR_ECIF_NOT_SUPPORT_COVER_RULE = new Error(
			"600807|customer cover rule is not support|不支持的客户信息覆盖规则");
	public static final Error ERR_ECIF_NOT_SUPPORT_SPLIT_RULE = new Error(
			"600808|customer split rule is not support|不支持的客户拆分规则");
	public static final Error ERR_ECIF_NOT_SUPPORT_OTHER = new Error("600899|other rule is not support|不支持的其他规则");

	public static final Error ERR_ECIF_DATA_NOT_EXPECT = new Error("600901|data is not expectant|返回非期望的数据");
	public static final Error ERR_ECIF_DATA_TOO_MANY = new Error("600902|too many records|期望一条记录，返回多条记录");
	public static final Error ERR_ECIF_DATA_MAX = new Error("600903|too many records|返回记录超过最大限制");
	public static final Error ERR_ECIF_CUST_STATUS = new Error("600904|error cust status|客户状态异常");
	public static final Error ERR_ECIF_DATA_ERROR = new Error("600999|data error|其他数据异常");
	public static final Error ERR_ECIF_GEN_PK_ERROR = new Error("601001|generate primary key error|生成主键异常");
	public static final Error ERR_ECIF_GEN_ECIFCUSTNO_ERROR = new Error(
			"601002|generate ECIF customer number error|生成ECIF客户号异常");
	public static final Error ERR_ECIF_GEN_ERROR = new Error("601099|generate other data error|生成其他数据异常");

	/** 数据同步 */
	public static final Error ERR_SYNCHRO_CUSTNO_ERROR = new Error("620001|synchro customer number is null|同步客户的客户号为空");
	public static final Error ERR_SYNCHRO_INFO_ERROR = new Error("620002|synchro info is null|同步类容为空");
	public static final Error ERR_SYNCHRO_TXCODE_ERROR = new Error("620003|synchro query tx is not exist|同步查询交易不存在或已停用");
	public static final Error ERR_SYNCHRO_TX_ERROR = new Error("620004|synchro query tx error|执行同步查询交易错误");
	public static final Error ERR_SYNCHRO_MSG_ERROR = new Error("620005|synchro request msg error|组装报文体和报文头失败");
	public static final Error ERR_SYNCHRO_CLIENT_ERROR = new Error("620006|synchro get send client error|获取客户端失败");
	public static final Error ERR_SYNCHRO_SEND_ERROR = new Error("620007|synchro client send error|客户端发送失败");
	public static final Error ERR_SYNCHRO_RESPSONSE_ERROR = new Error("620008|synchro exe response error|响应报文处理失败");
	public static final Error ERR_SYNCHRO_CLASS_ERROR = new Error("620009|synchro business class error|获取同步处理类失败");
	public static final Error ERR_SYNCHRO_CFG_ERROR = new Error("620010|synchro cfg data error|没有交易的同步配置信息");
	public static final Error ERR_SYNCHRO_BIZLOGIC_ERROR = new Error("620011|synchro logic error|同步处理异常");
	public static final Error ERR_SYNCHRO_DATA_ERROR = new Error("620011|synchro data error|同步处理异常，数据错误");
	public static final Error ERR_SYNCHRO_RESP_BODY_ERROR = new Error(
			"620012|synchro response data error|同步处理异常，响应报文错误");

	public static final Error ERR_SERVER_PROG_ERROR = new Error("700001|server program error|程序错误");
	public static final Error ERR_SERVER_CFG_FILE_ERROR = new Error("700002|configure file error|配置文件错误");
	public static final Error ERR_SERVER_BIZLOGIC_ERROR = new Error("700003|business logic error|业务逻辑异常");
	public static final Error ERR_SERVER_SERVICE_BUSY = new Error("700004|server service is busy|服务忙");
	public static final Error ERR_SERVER_INTERNAL_ERROR = new Error("700005|server internal error|服务器内部错误");
	public static final Error ERR_BATCH_FILE_ERROR = new Error("700006|batch file error|批量文件错误");
	public static final Error ERR_BATCH_BIZLOGIC_ERROR = new Error("700007|batch logic error|批量业务逻辑异常");
	public static final Error ERR_SERVER_UNKNOWN_ERROR = new Error("709999|server unknown error|服务器未知错误");
	public static final Error ERR_COMM_PROTOCOL_NOT_SUPPORT = new Error(
			"800001|communicate protocol not support|不支持的通讯协议");
	public static final Error ERR_COMM_UNKNOWN_ERROR = new Error("809999|communicate unknown error|未知通讯错误");
	public static final Error ERR_DIR_NOT_EXIST = new Error("820001|directory not exist|目录不存在");
	public static final Error ERR_DIR_NO_PRIVILEGE = new Error("820002|directory operation has no privilege|目录操作权限不足");
	public static final Error ERR_FILE_OPEN_ERROR = new Error("830001|file open error|打开文件失败");
	public static final Error ERR_FILE_CLOSE_ERROR = new Error("830002|file close error|关闭文件失败");
	public static final Error ERR_FILE_NO_PRIVILEGE = new Error("830003|file operation has no privilege|文件操作权限不足");
	public static final Error ERR_DB_CONN_ERROR = new Error("840001|can not connect to database|数据库连接不上");
	public static final Error ERR_DB_ACCESS_ERROR = new Error("840002|the accessed object is not exist|数据库对象不存在");
	public static final Error ERR_DB_LOCK_ERROR = new Error("840003|the accessed object is locked|数据库对象被锁定");
	public static final Error ERR_DB_NO_PRIVILEGE = new Error("840004|database operation has no privilege|数据库操作权限不足");
	public static final Error ERR_DB_OPER_ERROR = new Error("840005|database operation fail|数据库操作失败");
	public static final Error ERR_MQ_ERROR = new Error("860001|message queue middleware error|消息队列中间件错误");
	public static final Error ERR_APPSVR_ERROR = new Error("880001|application server error|应用服务器错误");
	public static final Error ERR_OS_ERROR = new Error("900001|OS error|操作系统错误");
	public static final Error ERR_DISK_FULL = new Error("920001|disk full|磁盘空间不足");
	public static final Error ERR_DISK_ERROR = new Error("920002|disk error|磁盘读些错误");
	public static final Error ERR_NETWORK_ERROR = new Error("940001|network error|网络错误");
	public static final Error ERR_OTHER_ERROR = new Error("999998|other error|其他错误");
	public static final Error ERR_UNKNOWN_ERROR = new Error("999999|unknown error|未知错误");
}
