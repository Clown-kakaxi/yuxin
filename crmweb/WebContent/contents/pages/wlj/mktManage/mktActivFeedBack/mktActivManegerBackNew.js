/**
 * 营销活动反馈-客户经理页面js
 * hujun
 * 2014-07-03
 */

	imports([
	         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
	         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js'
	         ]);
	
	var createView=false;
	var editView=false;
	var detailView=false;
	
	var url=basepath+'/mktActivityManegerback.json';
	var lookupTypes=[
	                 	'IF_FLAG',
	                 	'STAGE_LEAVL',
	                 	'MACTI_STATUS'
	                 ];
	
	var fields=[
	            {name:'ORG_NAME',text:'机构',hiddenName:'ORG_ID',searchField:true,xtype:'orgchoose'},
	            {name:'EXECUTOR_NAME',text:'客户经理',searchField:true,xtype:'userchoose'},
	            {name:'CUST_NAME',text:'客户名称'},
	            {name:'MKT_ACTI_NAME',text:'营销活动名称',searchField:true},
	            {name:'MKT_ACTI_STAT',text:'活动状态',translateType:'MACTI_STATUS'},
	            {name:'PROGRESS_STAGE',text:'进展阶段',searchField:true,translateType:'STAGE_LEAVL'},
	            {name:'IS_CRE_CHANCE',text:'是否已创建商机',searchField:true,translateType:'IF_FLAG'},
	            {name:'NOW_DATE',text:'统计日期',xtype:'datefield',format:'Y-m-d'}
	            ];
	
	
	