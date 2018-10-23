/**
 * 营销活动反馈-总分行页面js
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
	
	var url=basepath+'/mktActivityOrgback.json';
	var lookupTypes=[
	                 	'IF_FLAG',
	                 	'STAGE_LEAVL',
	                 	'MACTI_STATUS'
	                 ];
	
	var fields=[
	            {name:'ORG_NAME',text:'机构',hiddenName:'ORG_ID',searchField:true,xtype:'orgchoose'},
	            {name:'MKT_ACTI_NAME',text:'营销活动名称',searchField:true},
	            {name:'DELIVER',text:'下发客户数'},
	            {name:'SUCESS',text:'营销成功客户数'},
	            {name:'PER',text:'完成率',renderer : percent(false)},
	            {name:'NOW_DATE',text:'统计日期',xtype:'datefield',format:'Y-m-d'}
	            ];
	
	
	