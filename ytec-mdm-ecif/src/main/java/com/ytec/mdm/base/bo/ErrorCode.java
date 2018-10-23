package com.ytec.mdm.base.bo;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�ErrorCode
 * @�������������붨��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����9:55:39
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����9:55:39
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class ErrorCode {
	public static final Error SUCCESS = new Error("000000|success|�ɹ�");

	public static final Error INF_QUERY_SUCCESS = new Error("000001|query success|��ѯ�ɹ�");
	public static final Error INF_ADD_SUCCESS = new Error("000002|add success|�����ɹ�");
	public static final Error INF_UPDATE_SUCCESS = new Error("000003|update success|�޸ĳɹ�");
	public static final Error INF_DELETE_SUCCESS = new Error("000004|delete success|ɾ���ɹ�");
	public static final Error INF_BATCH_QUERY_SUCCESS = new Error("000101|batch query success|������ѯ�ɹ�");
	public static final Error INF_BATCH_ADD_SUCCESS = new Error("000102|batch add success|���������ɹ�");
	public static final Error INF_BATCH_UPDATE_SUCCESS = new Error("000103|batch update success|�������³ɹ�");
	public static final Error INF_BATCH_DELETE_SUCCESS = new Error("000104|batch delete success|����ɾ���ɹ�");
	public static final Error INF_EVENT_NOTIFICATION_SUCCESS = new Error("000201|event notification success|�¼�֪ͨ�ɹ�");
	public static final Error INF_PUBLISH_SUCCESS = new Error("000301|publish success|�����ɹ�");

	public static final Error WRN_SUCCESS = new Error("010000|success with warning|�ɹ����о���");
	public static final Error WRN_SUCCESS_PARTIAL_NO_DATA = new Error(
			"010001|success but partial node has no data|�ɹ����о���(������Ϣ��������)");
	public static final Error WRN_NONE_FOUND = new Error("010002|no any record was found |δ�鵽�κμ�¼");
	public static final Error WRN_NONE_INSERT = new Error("010003|no any record was inserted|δ�����κμ�¼����");
	public static final Error WRN_NONE_UPDATE = new Error("010004|no any record was updated|δ�����κμ�¼����");
	public static final Error WRN_NONE_DELETE = new Error("010005|no any record was deleted|δɾ���κμ�¼����");
	public static final Error WRN_BATCH_HAS_SKIP = new Error("010006|at least one record was skipped|����������������¼����");
	public static final Error WRN_BATCH_HAS_REJECT = new Error("010007|at least one record was rejected|���������оܾ���¼����");
	public static final Error WRN_BATCH_HAS_FAILURE = new Error(
			"010008|at least one failure in batch process|����������ʧ�ܼ�¼����");

	public static final Error ERR_ALL = new Error(
			"100000|all error|���д���");				//��ָ���д��󣬰������д��������100000�Ĵ���
	public static final Error ERR_COMPRESS_FORMAT_NOT_SUPPORT = new Error(
			"100001|comperss format is not suppert|��֧�ֵ�ѹ����ʽ");
	public static final Error ERR_COMPRESS_LEVEL_NOT_SUPPORT = new Error(
			"100002|comperss level is not suppert|��֧�ֵ�ѹ������");

	public static final Error ERR_TX_SERVER_STAT_BLOCK = new Error(
			"109000|transaction server blocked by data process module|���׷���ͣ�ã�������������");
	public static final Error ERR_COMPRESS_UNKNOWN_ERROR = new Error("109999|compress unknown error|ѹ��δ֪����");
	public static final Error ERR_UNCOMPRESS_ERROR = new Error("110001|uncompress error|ѹ���������𻵣��޷���ѹ��");
	public static final Error ERR_UNCOMPRESS_UNKNOWN_ERROR = new Error("119999|uncompress unknown error|��ѹ��δ֪����");
	public static final Error ERR_ENCRYPTION_ALGORITHM_NOT_SUPPORT = new Error(
			"150001|encryption algorithm is not support|��֧�ֵļ����㷨");
	public static final Error ERR_ENCRYPTION_UNKNOWN_ERROR = new Error("159999|encryption unknown error|����δ֪����");
	public static final Error ERR_DECRYPTION_ERROR = new Error("160001|decryption error|��Կ���ԣ��޷�����");
	public static final Error ERR_DECRYPTION_UNKNOWN_ERROR = new Error("169999|decryption unknown error|����δ֪����");
	public static final Error ERR_SIGNATURE_VALID_ERROR = new Error("200001|signature valid error|ǩ����֤��ͨ��");
	public static final Error ERR_SIGNATURE_UNKNOWN_ERROR = new Error("209999|signature unknown error|ǩ����֤δ֪����");
	public static final Error ERR_CLIENT_NOT_AUTHORIZED = new Error("250001|client not authorized|����ͻ���δ��Ȩ");
	public static final Error ERR_CLIENT_WAS_SUSPENDED = new Error("250002|client was suspended|����ͻ��˱���ͣ����");
	public static final Error ERR_CLIENT_UNKNOWN_ERROR = new Error(
			"250003|client authentication unknown error|�ͻ��������֤δ֪����");
	public static final Error ERR_CLIENT_REQUEST_NOT_FOUND = new Error("250004|client request not found|������񲻴���");
	public static final Error ERR_CLIENT_REQUEST_NOT_AUTHORIZED = new Error(
			"250005|client request not authorized|�������δ��Ȩ");
	public static final Error ERR_CLIENT_REQUEST_PARA_ERROR = new Error(
			"250006|client request parameter error|��������������");
	public static final Error ERR_CLIENT_AUTHORIZED_PW = new Error("250006|client request password err|������Ȩ����Ϊ��");
	public static final Error ERR_CLIENT_BAD_REQUEST = new Error("250007|client request bad|�ͻ�������Ƿ�");
	public static final Error ERR_CLIENT_REQUEST_UNKNOWN_ERROR = new Error("259999|client request unknown error|����δ֪����");
	// ���ڽ����н�����ˮ�š��������ڡ�����ʱ���У��
	public static final Error ERR_REQ_INFO_MISSING_REQSEQNO = new Error(
			"270001|bad reuqest, transaction sequence NO needed|����Ƿ�����Ҫ������ˮ��");
	public static final Error ERR_REQ_INFO_MISSING_REQDATE = new Error(
			"270002|bad reuqest, request date needed|����Ƿ�����Ҫ������������");
	public static final Error ERR_REQ_INFO_MISSING_REQTIME = new Error(
			"270003|bad reuqest, request time needed|����Ƿ�����Ҫ��������ʱ��");
	public static final Error ERR_REQ_REQSEQ_TIMEOUT = new Error(
			"270004|bad reuqest, timeout while receiving request|����Ƿ���ͨ�������н���ʱ����Ϣ����������Ĵ���ECIFʱ�����");
	public static final Error ERR_REQ_REQSEQ_FORMAT = new Error(
			"270004|bad reuqest, request transaction sequence NO format error|����Ƿ�����������ˮ�Ÿ�ʽ����");
	public static final Error ERR_XML_CFG_DATATYPE_NOT_SUPPORT = new Error(
			"280001|xml message configure data type not support|XML���������������Ͳ�֧��");
	public static final Error ERR_XML_CFG_DATAFMT_NOT_FOUND = new Error(
			"280002|xml message configure data format not found|XML�����������ݸ�ʽδ����");
	public static final Error ERR_XML_CFG_DATA_STDCODE_NULL = new Error(
			"280003|xml message configure data has no standard code|XML�����������Ա�׼����Ϊ��");
	public static final Error ERR_XML_CFG_NO_ROOT_NODE = new Error(
			"280004|xml message configure no root node|XML��������û�����ø��ڵ�");
	public static final Error ERR_XML_CFG_NO_FILTER = new Error("280005|xml message configure no filter|XML���������Ҳ�����ѯ����");
	public static final Error ERR_XML_CFG_NO_FK = new Error(
			"280006|xml message configure no foreign key|XML���������Ҳ����������");
	public static final Error ERR_XML_CFG_NO_CHECKINFO = new Error(
			"280007|xml message configure no check info|XML���������Ҳ���У���ĵ�");
	public static final Error ERR_XML_CHECKSUM_ERR = new Error("280008|xml message checksum info error|����У����Ϣ����");

	public static final Error ERR_XML_CFG_UNKNOWN_ERROR = new Error(
			"289999|xml message configure unknown error|XML��������δ֪����");
	public static final Error ERR_XML_IS_NULL = new Error("300001|request xml message is null|XML����Ϊ��");
	public static final Error ERR_XML_NOT_ENCODING = new Error("300002|request xml message encoding invalid|XML���ı��벻��");
	public static final Error ERR_XML_FORMAT_ERROR = new Error("300003|request xml message format error|������XML��׼");
	public static final Error ERR_XML_FORMAT_INVALID = new Error("300004|request xml message format invalid|�����ϱ��Ľӿڹ淶");
	public static final Error ERR_XML_NO_SUCH_TXNO = new Error(
			"300005|request xml message number is not exist|XML���ı�Ų�����");
	public static final Error ERR_XML_NO_SUCH_TXCODE = new Error(
			"300006|request xml message transaction code is not exist|XML���Ľ��״��벻����");
	public static final Error ERR_XML_NO_SUCH_TXNAME = new Error(
			"300007|request xml message service name is not exist|XML���ķ������Ʋ�����");
	public static final Error ERR_XML_FILTER_VALUE_IS_NULL = new Error(
			"300008|request xml message filter value is null|XML���Ĳ�ѯ����Ϊ��");
	public static final Error ERR_XML_FILTER_VALUE_CONSISTENCE = new Error(
			"300009|consistence value is invalid |�����������ֶ�ֵ��һ��");
	public static final Error ERR_XML_UNKNOWN_ERROR = new Error("309999|request xml message unknown error|XML����δ֪����");
	public static final Error ERR_FIX_IS_NULL = new Error("350001|request fixed length message is null|��������Ϊ��");
	public static final Error ERR_FIX_UNKNOWN_ERROR = new Error(
			"359999|request fixed length message unknown error|��������δ֪����");
	public static final Error ERR_POJO_IS_NULL = new Error("400001|request POJO is null|JAVA����Ϊ��");
	public static final Error ERR_POJO_UNKNOWN_ERROR = new Error("409999|request POJO unknown error|JAVA����δ֪����");
	public static final Error ERR_FILE_NOT_EXIST = new Error("450001|interface file not exist|�ӿ��ļ�������");
	public static final Error ERR_FILE_IS_NULL = new Error("450002|interface file is null|�ӿ��ļ�����Ϊ��");
	public static final Error ERR_FILE_UNKNOWN_ERROR = new Error("459999|interface file unknown error|�ӿ��ļ�δ֪����");
	public static final Error ERR_RULE_CHECK_TRANSFORM_ERROR = new Error("500000|chaeck and transform error|У��ת��ʧ��");
	public static final Error ERR_ADAPTER_TRANSFORM_ERROR = new Error("500001|adapter transform error|������ת��ʧ��");
	public static final Error ERR_ADAPTER_UNKNOWN_ERROR = new Error("509999|adapter unknown error|������ת��δ֪����");
	public static final Error ERR_VER_MODEL_UNCOMPATIBLE = new Error("510001|model version uncompatible|�ͻ���ģ�Ͱ汾����");
	public static final Error ERR_VER_TX_UNCOMPATIBLE = new Error("510002|transcation version uncompatible|�������ð汾����");
	public static final Error ERR_VER_CODE_UNCOMPATIBLE = new Error("510003|code version uncompatible|�ͻ��˱�׼��汾����");
	public static final Error ERR_VER_JRE_UNCOMPATIBLE = new Error("510004|jre version uncompatible|JRE�汾������");
	public static final Error ERR_VER_OTHER = new Error("519999|other version error|�����汾����");
	public static final Error ERR_RULE_INVALID_INT = new Error("550101|invalid integer format|������ֵ��ʽ����");
	public static final Error ERR_RULE_INVALID_LONG = new Error("550102|invalid long format|��������ֵ��ʽ����");
	public static final Error ERR_RULE_INVALID_FLOAT = new Error("550103|invalid float format|��������ֵ��ʽ����");
	public static final Error ERR_RULE_INVALID_DOUBLE = new Error("550104|invalid double format|˫���ȸ�������ֵ��ʽ����");
	public static final Error ERR_RULE_INVALID_DATE_FORMAT = new Error("550105|invalid date format|���ڸ�ʽ����");
	public static final Error ERR_RULE_INVALID_DATE = new Error("550106|invalid date|���ڲ���");
	public static final Error ERR_RULE_INVALID_TIME_FORMAT = new Error("550107|invalid time format|ʱ���ʽ����");
	public static final Error ERR_RULE_INVALID_TIME = new Error("550108|invalid time|ʱ�䲻��");
	public static final Error ERR_RULE_INVALID_TIMESTAMP_FORMAT = new Error("550109|invalid timestamp format|ʱ�����ʽ����");
	public static final Error ERR_RULE_INVALID_TIMESTAMP = new Error("550110|invalid timestamp|ʱ�������");
	public static final Error ERR_RULE_INVALID_DATA_FORMAT = new Error("550111|invalid data format|�������ݸ�ʽ����");
	public static final Error ERR_RULE_INVALID_DATA = new Error("550112|invalid data|�������ݲ���");
	public static final Error ERR_RULE_INVALID_LENGTH = new Error("550201|invalid length|���Ȳ�����Ҫ��");
	public static final Error ERR_RULE_INVALID_PREC = new Error("550301|invalid precision|���Ȳ�����Ҫ��");
	public static final Error ERR_RULE_VALUE_IS_NULL = new Error("550401|value is null|����Ϊ��");
	public static final Error ERR_RULE_OVERFLOW_RANGE = new Error("550501|value overflow the range|����ֵ��Χ");
	public static final Error ERR_RULE_NOT_IN_LIST = new Error("550601|value not in the list|ȡֵ����ָ����Χ��");
	public static final Error ERR_STD_CATE_NOT_EXIST = new Error("560101|source cate is not exist|������಻����");
	public static final Error ERR_STD_CODE_NOT_EXIST = new Error("560102|source code is not exist|���벻����");
	public static final Error ERR_CODETRAN_ERROR = new Error("560201|code transform error|ת�����");
	public static final Error ERR_CODETRAN_REVE_ERROR = new Error("560202|reverse code transform error|��ת�����");
	public static final Error ERR_CONVERT_ERROR = new Error("580001|convert error|ת������");

	public static final Error ERR_ECIF_NULL_IDENTTP = new Error("600101|identifier type is null or blank|֤������Ϊ��");
	public static final Error ERR_ECIF_NULL_IDENTNO = new Error("600102|identifier number is null or blank|֤������Ϊ��");
	public static final Error ERR_ECIF_NULL_IDENTNAME = new Error("600103|contact name is null or blank|�ͻ�����Ϊ��");
	public static final Error ERR_ECIF_NULL_SRCSYSCD = new Error("600104|source system code is null or blank|Դϵͳ����Ϊ��");
	public static final Error ERR_ECIF_NULL_SRCSYSCUSTNO = new Error(
			"600105|source system customer number is null or blank|Դϵͳ�ͻ���Ϊ��");
	public static final Error ERR_ECIF_NULL_ECIFCUSTNO = new Error(
			"600106|ECIF customer number is null or blank|ECIF�ͻ���Ϊ��");
	public static final Error ERR_ECIF_NULL_CONTID = new Error("600107|contact id is null or blank|�ͻ���ʶΪ��");
	public static final Error ERR_ECIF_NULL_ACCTNO = new Error("600108|account number is null or blank|�˺�Ϊ��");
	public static final Error ERR_ECIF_NULL_CARDNO = new Error("600109|card number is null or blank|����Ϊ��");
	public static final Error ERR_ECIF_NULL_OTHER = new Error("600199|other required data is null or blank|������������Ϊ��");
	public static final Error ERR_ECIF_INVALID_IDNO = new Error("600201|invalid identifier card number|���֤����У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_ORGCODE = new Error("600202|invalid organization code|��֯��������У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_LICENSE = new Error("600203|invalid business license number|Ӫҵִ�պ�У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_IDENTNO = new Error("600299|invalid other identifier number|����֤��У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_SRCCUSTNO = new Error(
			"600301|invalid source system customer number|Դϵͳ�ͻ���У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_ECIFCUSTNO = new Error(
			"600302|invalid ECIF customer number|ECIF�ͻ���У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_CONTID = new Error("600303|invalid contact id|�ͻ���ʶУ�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_ACCTNO = new Error("600401|invalid account number|�˺�У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_CARDNO = new Error("600402|invalid card number|����У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_MP = new Error("600501|invalid mobile phone number|�ֻ���У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_TEL = new Error("600502|invalid telephone number|�绰����У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_EMAIL = new Error("600503|invalid email address|�ʼ���ַУ�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_CONTMETH = new Error("600599|invalid other contact method|������ϵ��ʽУ�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_BIRTHDATE = new Error("600601|invalid birth date|��������У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_OPENACCTDATE = new Error("600602|invalid open account date|��������У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_CLOSEACCTDATE = new Error("600603|invalid close account date|��������У�鲻ͨ��");
	public static final Error ERR_ECIF_INVALID_OTHERDATE = new Error("600699|invalid other date|��������У�鲻ͨ��");
	public static final Error ERR_ECIF_NOT_EXIST_SRCSYSCUSTNO = new Error(
			"600701|source customer number is not exist|Դϵͳ�ͻ��Ų�����");
	public static final Error ERR_ECIF_NOT_EXIST_ECIFCUSTNO = new Error(
			"600702|ecif customer number is not exist|ECIF�ͻ��Ų�����");
	public static final Error ERR_ECIF_NOT_EXIST_CONTID = new Error("600703|cont id is not exist|�ͻ�������");
	public static final Error ERR_ECIF_NOT_EXIST_ACCTNO = new Error("600704|account number is not exist|�˺Ų�����");
	public static final Error ERR_ECIF_NOT_EXIST_CARDNO = new Error("600705|card number is not exist|���Ų�����");
	public static final Error ERR_ECIF_NOT_EXIST_IDENT = new Error(
			"600706|no identifier is exist in request XML|�������в�����֤����Ϣ�ڵ�");
	public static final Error ERR_ECIF_NOT_EXIST_PK = new Error("600707|primary key is not exist|����������");
	public static final Error ERR_ECIF_NOT_EXIST_TECHNICALKEY = new Error(
			"600708|technical key key is not exist|��������������");
	public static final Error ERR_ECIF_NOT_EXIST_BUSINESSKEY = new Error("600709|business key is not exist|ҵ�������ֶ�δ��ֵ");
	public static final Error ERR_ECIF_EXIST_CUST = new Error("600710|ecif customer is exist|�ͻ��Ѵ���");
	public static final Error ERR_ECIF_CUSTNAME = new Error("600711|invalid ecif cust name |�ͻ���������ȷ");
	public static final Error ERR_ECIF_EXIST_CUST_UPDATE = new Error(
			"000000|ecif customer is exist and update customer information|�ͻ��Ѵ��ڣ����������ĸ��¿ͻ���Ϣ");
	public static final Error ERR_ECIF_NOT_EXIST_OTHER = new Error("600799|other required data is not exist|�������ݲ�����");

	public static final Error ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE = new Error(
			"600801|identify rule is not support|��֧�ֵĿͻ�ʶ�����");
	public static final Error ERR_ECIF_NOT_SUPPORT_SUSPECT_RULE = new Error(
			"600802|suspect rule is not support|��֧�ֵ����ƿͻ�ʶ�����");
	public static final Error ERR_ECIF_NOT_SUPPORT_IDENTIFY_RULE_COMB = new Error(
			"600803|identify rule combination is not support|��֧�ֵĿͻ�ʶ��������");
	public static final Error ERR_ECIF_NOT_SUPPORT_SUSPECT_RULE_COMB = new Error(
			"600804|suspect rule combination is not support|��֧�ֵ����ƿͻ�ʶ��������");
	public static final Error ERR_ECIF_NOT_SUPPORT_MERGE_RULE = new Error(
			"600805|customer merge rule is not support|��֧�ֵĿͻ��鲢����");
	public static final Error ERR_ECIF_NOT_SUPPORT_MERGE_METHOD = new Error(
			"600806|customer merge method is not support|��֧�ֵĿͻ��鲢��ʽ");
	public static final Error ERR_ECIF_NOT_SUPPORT_COVER_RULE = new Error(
			"600807|customer cover rule is not support|��֧�ֵĿͻ���Ϣ���ǹ���");
	public static final Error ERR_ECIF_NOT_SUPPORT_SPLIT_RULE = new Error(
			"600808|customer split rule is not support|��֧�ֵĿͻ���ֹ���");
	public static final Error ERR_ECIF_NOT_SUPPORT_OTHER = new Error("600899|other rule is not support|��֧�ֵ���������");

	public static final Error ERR_ECIF_DATA_NOT_EXPECT = new Error("600901|data is not expectant|���ط�����������");
	public static final Error ERR_ECIF_DATA_TOO_MANY = new Error("600902|too many records|����һ����¼�����ض�����¼");
	public static final Error ERR_ECIF_DATA_MAX = new Error("600903|too many records|���ؼ�¼�����������");
	public static final Error ERR_ECIF_CUST_STATUS = new Error("600904|error cust status|�ͻ�״̬�쳣");
	public static final Error ERR_ECIF_DATA_ERROR = new Error("600999|data error|���������쳣");
	public static final Error ERR_ECIF_GEN_PK_ERROR = new Error("601001|generate primary key error|���������쳣");
	public static final Error ERR_ECIF_GEN_ECIFCUSTNO_ERROR = new Error(
			"601002|generate ECIF customer number error|����ECIF�ͻ����쳣");
	public static final Error ERR_ECIF_GEN_ERROR = new Error("601099|generate other data error|�������������쳣");

	/** ����ͬ�� */
	public static final Error ERR_SYNCHRO_CUSTNO_ERROR = new Error("620001|synchro customer number is null|ͬ���ͻ��Ŀͻ���Ϊ��");
	public static final Error ERR_SYNCHRO_INFO_ERROR = new Error("620002|synchro info is null|ͬ������Ϊ��");
	public static final Error ERR_SYNCHRO_TXCODE_ERROR = new Error("620003|synchro query tx is not exist|ͬ����ѯ���ײ����ڻ���ͣ��");
	public static final Error ERR_SYNCHRO_TX_ERROR = new Error("620004|synchro query tx error|ִ��ͬ����ѯ���״���");
	public static final Error ERR_SYNCHRO_MSG_ERROR = new Error("620005|synchro request msg error|��װ������ͱ���ͷʧ��");
	public static final Error ERR_SYNCHRO_CLIENT_ERROR = new Error("620006|synchro get send client error|��ȡ�ͻ���ʧ��");
	public static final Error ERR_SYNCHRO_SEND_ERROR = new Error("620007|synchro client send error|�ͻ��˷���ʧ��");
	public static final Error ERR_SYNCHRO_RESPSONSE_ERROR = new Error("620008|synchro exe response error|��Ӧ���Ĵ���ʧ��");
	public static final Error ERR_SYNCHRO_CLASS_ERROR = new Error("620009|synchro business class error|��ȡͬ��������ʧ��");
	public static final Error ERR_SYNCHRO_CFG_ERROR = new Error("620010|synchro cfg data error|û�н��׵�ͬ��������Ϣ");
	public static final Error ERR_SYNCHRO_BIZLOGIC_ERROR = new Error("620011|synchro logic error|ͬ�������쳣");
	public static final Error ERR_SYNCHRO_DATA_ERROR = new Error("620011|synchro data error|ͬ�������쳣�����ݴ���");
	public static final Error ERR_SYNCHRO_RESP_BODY_ERROR = new Error(
			"620012|synchro response data error|ͬ�������쳣����Ӧ���Ĵ���");

	public static final Error ERR_SERVER_PROG_ERROR = new Error("700001|server program error|�������");
	public static final Error ERR_SERVER_CFG_FILE_ERROR = new Error("700002|configure file error|�����ļ�����");
	public static final Error ERR_SERVER_BIZLOGIC_ERROR = new Error("700003|business logic error|ҵ���߼��쳣");
	public static final Error ERR_SERVER_SERVICE_BUSY = new Error("700004|server service is busy|����æ");
	public static final Error ERR_SERVER_INTERNAL_ERROR = new Error("700005|server internal error|�������ڲ�����");
	public static final Error ERR_BATCH_FILE_ERROR = new Error("700006|batch file error|�����ļ�����");
	public static final Error ERR_BATCH_BIZLOGIC_ERROR = new Error("700007|batch logic error|����ҵ���߼��쳣");
	public static final Error ERR_SERVER_UNKNOWN_ERROR = new Error("709999|server unknown error|������δ֪����");
	public static final Error ERR_COMM_PROTOCOL_NOT_SUPPORT = new Error(
			"800001|communicate protocol not support|��֧�ֵ�ͨѶЭ��");
	public static final Error ERR_COMM_UNKNOWN_ERROR = new Error("809999|communicate unknown error|δ֪ͨѶ����");
	public static final Error ERR_DIR_NOT_EXIST = new Error("820001|directory not exist|Ŀ¼������");
	public static final Error ERR_DIR_NO_PRIVILEGE = new Error("820002|directory operation has no privilege|Ŀ¼����Ȩ�޲���");
	public static final Error ERR_FILE_OPEN_ERROR = new Error("830001|file open error|���ļ�ʧ��");
	public static final Error ERR_FILE_CLOSE_ERROR = new Error("830002|file close error|�ر��ļ�ʧ��");
	public static final Error ERR_FILE_NO_PRIVILEGE = new Error("830003|file operation has no privilege|�ļ�����Ȩ�޲���");
	public static final Error ERR_DB_CONN_ERROR = new Error("840001|can not connect to database|���ݿ����Ӳ���");
	public static final Error ERR_DB_ACCESS_ERROR = new Error("840002|the accessed object is not exist|���ݿ���󲻴���");
	public static final Error ERR_DB_LOCK_ERROR = new Error("840003|the accessed object is locked|���ݿ��������");
	public static final Error ERR_DB_NO_PRIVILEGE = new Error("840004|database operation has no privilege|���ݿ����Ȩ�޲���");
	public static final Error ERR_DB_OPER_ERROR = new Error("840005|database operation fail|���ݿ����ʧ��");
	public static final Error ERR_MQ_ERROR = new Error("860001|message queue middleware error|��Ϣ�����м������");
	public static final Error ERR_APPSVR_ERROR = new Error("880001|application server error|Ӧ�÷���������");
	public static final Error ERR_OS_ERROR = new Error("900001|OS error|����ϵͳ����");
	public static final Error ERR_DISK_FULL = new Error("920001|disk full|���̿ռ䲻��");
	public static final Error ERR_DISK_ERROR = new Error("920002|disk error|���̶�Щ����");
	public static final Error ERR_NETWORK_ERROR = new Error("940001|network error|�������");
	public static final Error ERR_OTHER_ERROR = new Error("999998|other error|��������");
	public static final Error ERR_UNKNOWN_ERROR = new Error("999999|unknown error|δ֪����");
}
