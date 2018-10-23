/**
 * 
* @Description: 法金全行客户查询
* @author wangmk 
* @date 2014-7-8 
*
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
]);

var url=basepath+"/groupCustomerQuery.json";

var _custId;
var comitUrl=false;
var createView = false;
var editView = false;
var detailView = false;

var addID = '';//保存带加入客户群的成员id
var memberType = '';//群成员类型

// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};

var lookupTypes=[
     'XD000080',//客户类别
     'XD000040', //证件类别
     'XD000082',
     'FXQ_RISK_LEVEL',//反洗钱风险等级
     'XD000081',//客户状态
     'CUST_RISK_CHARACT',
     'XD000096',
     'PRE_CUST_LEVEL',
     'CUSTOMER_GROUP_TYPE',
     'CUSTOMER_SOURCE_TYPE',
     'GROUP_MEMEBER_TYPE',
     'SHARE_FLAG',
     'XD000267',
     'XD000082',
     'XD000245',
     'XD000246',
     'XD000247',
     'IF_FLAG',
     'FXQ007',
     'FXQ010',
     'FXQ21006',
     'FXQ023',
     'FXQ025',
     'APP_STATUS'//特殊客户审核状态
  ];

var localLookup = {
	'ADD_WAY' : [
	   {key : '1',value : '加入已有客户群'},
	   {key : '2',value : '新建客户群'}
	],
	'LAST_PER':[
	  			{key : 'ETL',value : 'ETL'},
			  	{key : __userId,value : __userName}
			]
};

var fields=[
     {name:'CUST_NAME',text:'客户名称',resutlWidth:150,searchField:true,dataType:'string'},
     {name:'CUST_STAT',text:'客户状态',resutlWidth:80,translateType:'XD000081'},
     {name: 'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false},
     {name: 'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:160,singleSelect: false},
     {name:'ORG_NAME',text:'归属机构',xtype : 'wcombotree',innerTree:'ORGTREE',resutlWidth:80, showField:'text',hideField:'UNITID',editable:false,allowBlank:false},
     {name:'CUST_ID',text:'客户编号',resutlWidth:120,dataType:'string',hidden:true},
     {name:'CORE_NO',text:'核心客户号',resutlWidth:120,searchField:true,dataType:'string',gridField:false},
     {name:'IDENT_TYPE',text:'证件类型',resutlWidth:120,translateType:'XD000040',searchField:true,editable: true},
     {name:'IDENT_NO',text:'证件号码',resutlWidth:120,searchField:true,dataType:'string'},
     {name:'ACCT_NO',text:'账号',resutlWidth:60,searchField: true,dataType:'string',gridField:false},
     {name:'LOAN_CARD_NO',text:'中征码',resutlWidth:120,searchField: true,dataType:'string',gridField:true,
    	 viewFn:function(rs){
    		 //console.log(rs);
    		 return rs;
    	 }, emptyText:'请输入16位字符，且不能含有空格',regex:/^[A-Za-z0-9]{16}$/,regexText:'请输入16位字符，且不能含有空格'
     }
  
];
