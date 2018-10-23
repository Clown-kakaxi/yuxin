/**
*@description 日程管理页面
*@author:luyy
*@since:2014-06-23
* helin,20140829,bug fixed
* 注：样式my_row_set_red 是写在extensible-all.css中的
*/

imports([
	'/contents/pages/calendarApp/extensible-lang-zh_CN.js',
	'/contents/pages/calendarApp/CalendarStore.js',
	'/contents/pages/calendarApp/data/calendars-custom.js',
	'/contents/pages/calendarApp/calendarFormCust.js',
	'/contents/commonjs/calendar/extensible-all.js',
	
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
    '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
    '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'
    
]);
Ext.QuickTips.init();
//临时添加日历样式
var _addLink = function(){
	try{
		if(document.createStyleSheet){
			document.createStyleSheet(basepath+"/contents/css/extensible-all.css",document.styleSheets.length);
		}else{
			var link = document.createElement("link");
			link.type = "text/css";
			link.rel = "stylesheet";
			link.href = basepath+"/contents/css/extensible-all.css";
			document.getElementsByTagName("head")[0].appendChild(link);
		}
	}catch(e){
	}
};
_addLink();
 
/**系统变量定义部分**/
WLJUTIL.suspendViews = false;
WLJUTIL.alwaysLockCurrentView = true;
var needGrid = false;//页面不用列表panel，展示自定义的内容
var createView = false;
var editView = false;
var detailView = false;
var fields = [{name:'TEST',text:'字段'}];//该属性为必须的
var lookupTypes = [
	'XD000080',
	'VISIT_TYPE',
	'VISIT_STAT',
	'STAT_PLAN',
	'STAT',
	'STAT_CRD'
];

var holidayStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	url :basepath+'/lookup.json?name=HOLIDAY_TYPE',
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
}); 

var timeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	url :basepath+'/lookup.json?name=TIME_TYPE',
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
}); 
//自定义变量
var schId = '';//日程id编号
var userId = '';//查看的客户经理id
var date = '';//查看的日程日期
var record = null;  //被选中的记录，用于反馈
var openWay = null;//打开方式，指示打开新增还是反馈页面  1：新增   2：反馈
var week = '';//周工作周期
var month = '';//月工作周期

Ext.ensible.cal.CalendarMappings = {
	CalendarId:   {name:'ID', mapping: 'remindBelong', type: 'string'}, // int by default
	Title:        {name:'CalTitle', mapping: 'cal_title', type: 'string'},
	Description:  {name:'Desc', mapping: 'cal_desc', type: 'string'},
	ColorId:      {name:'Color', mapping: 'cal_color', type: 'int'},
	IsHidden:     {name:'Hidden', mapping: 'hidden', type: 'boolean'}
};
Ext.ensible.cal.CalendarRecord.reconfigure();
var calendarStore = new Ext.ensible.sample.CalendarStore({
	data: Ext.ensible.sample.CalendarDataCustom
}); 

Ext.ensible.cal.EventMappings = {
	EventId:     {name: 'scheduleId', type:'string'}, // int by default
	CalendarId:  {name: 'remindBelong', type: 'string'}, // int by default
	Title:       {name: 'scheduleTitle'},
	StartDate:   {name: 'startDate', type: 'date', dateFormat: 'c'},
	EndDate:     {name: 'endDate', type: 'date', dateFormat: 'c'},
	RRule:       {name: 'RecurRule', mapping: 'recur_rule'},
	Location:    {name: 'Location', mapping: 'location'},
	Notes:       {name: 'scheduleContent', mapping: 'scheduleContent'},
	Url:         {name: 'LinkUrl', mapping: 'link_url'},
	IsAllDay:    {name: 'all_day', mapping: 'all_day', type: 'boolean'},
	Reminder:    {name: 'Reminder', mapping: 'reminder'},    
	RemindType:  {name: 'RemindType', mapping: 'remindType', type: 'string'},
	relatedCustomer:{name: 'relatedCustomer', mapping:'relationCust', type:'string'},   
	Customer:    {name: 'relationCust', type:'string'},
	CustomerName:{name: 'relationCustName',type:'string'},
	isTeam:      {name: 'isTeam',type:'String'},
	CreatedBy:   {name: 'CreatedBy', mapping: 'created_by'},
	IsPrivate:   {name: 'Private', mapping:'private', type:'boolean'}
};
var dataFields = [
	{name:'scheduleId', mapping:'SCH_ID'},
    {name:'scheduleTitle',mapping:'SCHEDULE_COUNT'},
    {name:'scheduleContent',mapping:'SCHEDULE_CONTENT'},
    {name:'startDate',mapping:'SCH_DATE',type: 'date', dateFormat: 'c'},
    {name:'endDate',mapping:'SCH_DATE',type: 'date', dateFormat: 'c'},
    {name:'all_day',mapping:'ALL_DAY',type:'boolean'},
    {name:'creator',mapping:'USER_ID'}
//    {name:'startTime',mapping:'START_TIME'},
//    {name:'endTime',mapping:'END_TIME'},
//    {name:'beforeheadDay',mapping:'BEFOREHEAD_DAY'},
//    {name:'relationCust',mapping:'RELATION_CUST'},
//    {name:'remindCycle',mapping:'REMIND_CYCLE'},
//    {name:'isRemind',mapping:'IS_REMIND'},
//    {name:'isRepeat',mapping:'IS_REPEAT'},
//    {name:'createOrganizer',mapping:'CREATE_ORG'},
//    {name:'createDate',mapping:'SCH_DATE'},
//    {name:'remindStsrtTime',mapping:'remindStsrtTime'},
//    {name:'remindEndTime',mapping:'remindEndTime'},
//    {name:'remindType',mapping:'REMIND_TYPE'},
//    {name:'remindBelong',mapping:'REMIND_BELONG'},
//    {name:'isTeam',mapping:'IS_TEAM'},
//    {name:'relationCustName',mapping:'RELATION_CUST_NAME'}
];
Ext.ensible.cal.EventRecord.reconfigure();   
var eventProxy = new Ext.data.HttpProxy({
	api:{
		read:{
			url:basepath+'/ocrmFWpSchedule!querySchedule.json',
			method:'GET'
		},
		destroy:{
			 url: basepath+'',
             method: 'POST'
		},
		create:{
			 url: basepath+'',
             method: 'POST'
		},
		update:{
			 url: basepath+'',
             method: 'POST'
		}
	},
	 listeners: {
        exception: function(proxy, type, action, o, res, arg){
            //var msg = res.message ? res.message : Ext.decode(res.responseText).message;
            // ideally an app would provide a less intrusive message display
            Ext.Msg.alert('Server Error', res.responseText);
        }
    }
});
var reader = new Ext.data.JsonReader({
	idProperty: 'SCH_ID',
	successProperty: false,
	root:'json.data',
   	fields: dataFields
});
var eventStore = new Ext.ensible.cal.EventStore({
	restful : true,
	proxy : eventProxy,
	reader : reader,
	onWrite: function(store, action, data, resp, rec){
		return;
	}
});
Ext.ensible.cal.EventRecord.reconfigure();
/**
 * 左边日期面板
 */
var calPanel = new Ext.ensible.cal.CalendarPanelCust({
	eventStore : eventStore,
	calendarStore : calendarStore,
	showDayView : false,
	showWeekView : false,
	listeners : {
		dayclick: function(a,b,c,d,f){
			infofrom.getForm().setValues({
				date:b
			});
			month = (b.getFullYear())+'年'+(b.getMonth()+1)+'月';//截取周期
			week = b.getFullYear() +'年'+(b.getWeekOfYear()+1)+'周';
			getInformation(infofrom.getForm().getValues().USER_ID,b.format('Y-m-d'));
			return false;
		},
		eventclick: function(a,b,c,d,f){
			var date = b.data.startDate;
			infofrom.getForm().setValues({
				date:date
			});
			month = (date.getFullYear()) +'年'+(date.getMonth()+1)+'月';
			week = date.getFullYear() +'年'+(date.getWeekOfYear()+1)+'周';
			getInformation(infofrom.getForm().getValues().USER_ID,date.format('Y-m-d'));
//			debugger;
			return false;
		},
		rangeselect: function(){
			//禁止其拖拽
			return false;
		}
	}
});




//边缘面板，左侧 日期控件
var edgeVies = {
	left : {
		width : 650,
		title:'日程安排',
		items : [calPanel]
	}
};

/********几个子表grid*******/
var store1 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleV.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name:'V_ID'},
	    {name:'SCH_ID'},
	    {name:'CUST_TYPE'},
	    {name:'CUST_TYPE_ORA'},
	    {name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'VISIT_TYPE'},
	    {name:'VISIT_TYPE_ORA'},
	    {name:'PHONE'},
	    {name:'VISITOR'},
	    {name:'VISIT_STAT'},
	    {name:'VISIT_STAT_ORA'},
	    {name:'VISIT_NOTE'},
	    {name:'ARANGE_ID'},
	    {name:'ARANGE_NAME'},
	    {name:'SCH_START_TIME'},
	    {name:'SCH_EDN_TIME'}
	])
});

//store1.on('beforeload', function(store1) { 
//	var start = calPanel.activeView.viewStart.dateFormat('Ymd');
//	var end = calPanel.activeView.viewEnd.dateFormat('Ymd');
//	store1.baseParams = {'start':start,'end':end};
//});

var store2 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleW.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [
		{name:'W_ID'},
	    {name:'SCH_ID'},
	    {name:'WEEK_CYCLE'},
	    {name:'SCH_CONTENT'},
	    {name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'SCH_START_TIME'},
	    {name:'SCH_EDN_TIME'},
	    {name:'ACT_START_TIME'},
	    {name:'ACT_END_TIME'},
	    {name:'SCHEDULE'},
	    {name:'UNFINISHED_REMARK'},
	    {name:'STAT'},{name:'STAT_ORA'},
	    {name:'REMARK'},
	    {name:'VISITOR'},
	    {name:'ARANGE_ID'},
	    {name:'ARANGE_NAME'}
	])
});
var store3 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleM.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [
		{name:'W_ID'},
	    {name:'SCH_ID'},
	    {name:'MONTH_CYCLE'},
	    {name:'SCH_CONTENT'},
	    {name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'SCH_START_TIME'},
	    {name:'SCH_EDN_TIME'},
	    {name:'ACT_START_TIME'},
	    {name:'ACT_END_TIME'},
	    {name:'SCHEDULE'},
	    {name:'UNFINISHED_REMARK'},
	    {name:'STAT'},{name:'STAT_ORA'},
	    {name:'REMARK'},
	    {name:'VISITOR'},
	    {name:'ARANGE_ID'},
	    {name:'ARANGE_NAME'}
	])
});
var store4 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleL.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [
		{name:'L_ID'},
	    {name:'SCH_ID'},
	    {name:'LOAN_BAL'},
	    {name:'CHECK_SIT'},
	    {name:'STAT'},{name:'STAT_ORA'},
	    {name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'REMARK'},
	    {name:'VISITOR'},
	    {name:'ARANGE_ID'},
	    {name:'ARANGE_NAME'}
	])
});
var store5 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleC.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [
		{name:'C_ID'},
	    {name:'SCH_ID'},
	    {name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'CRD_REQ'},
	    {name:'COMP_SIT'},
	    {name:'STAT'},{name:'STAT_ORA'},
	    {name:'REMARK'},
	    {name:'VISITOR'},
	    {name:'ARANGE_ID'},
	    {name:'ARANGE_NAME'}
	])
});
var store6 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleO.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [
		{name:'O_ID'},
	    {name:'SCH_ID'},
	    {name:'CUST_ID'},
	    {name:'CUST_NAME'},
	    {name:'OTH_SIT_REMARK'},
	    {name:'OTH_COMP_REMARK'},
	    {name:'STAT'},
	    {name:'STAT_ORA'},
	    {name:'VISITOR'},
	    {name:'ARANGE_ID'},
	    {name:'ARANGE_NAME'}
	])
});

var store7 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFWpScheduleH.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [
		{name:'ID'},
	    {name:'BEGIN_DATE'},
	    {name:'BEGIN_TIME'},
	    {name:'BEGIN_TIME_ORA'},
	    {name:'END_DATE'},
	    {name:'END_TIME'},
	    {name:'END_TIME_ORA'},
	    {name:'HOLIDAY_TYPE'},
	    {name:'HOLIDAY_TYPE_ORA'},
	    {name:'ACCOUNT_NAME'},
	    {name:'RECORD_DATE'}
	])
});
store7.reload(); 

var sm1 = new Ext.grid.CheckboxSelectionModel();
var rownum1 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm1 =  new Ext.grid.ColumnModel([
	 rownum1,sm1,	
	 {header : 'V_ID', dataIndex : 'V_ID',sortable : true,width : 120,hidden:true},
	 {header:'SCH_ID',dataIndex:'SCH_ID',sortable:true,width:120,hidden:true},
	 {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
	 {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
	 {header:'客户类型',dataIndex:'CUST_TYPE',sortable:true,width:120,hidden:true},
	 {header:'客户类型',dataIndex:'CUST_TYPE_ORA',sortable:true,width:120},
	 {header:'拜访状态',dataIndex:'VISIT_STAT',sortable:true,width:120,hidden:true},
	 {header:'拜访状态',dataIndex:'VISIT_STAT_ORA',sortable:true,width:120},
	 {header:'拜访方式',dataIndex:'VISIT_TYPE',sortable:true,width:120,hidden:true},
	 {header:'拜访方式',dataIndex:'VISIT_TYPE_ORA',sortable:true,width:120},
	 {header:'拜访开始时间',dataIndex:'SCH_START_TIME',format: 'y-m-d',sortable:true,width:120},
	 {header:'拜访结束时间',dataIndex:'SCH_EDN_TIME',format: 'y-m-d',sortable:true,width:120},
	 {header:'联系电话',dataIndex:'PHONE',sortable:true,width:120},
	 {header:'拜访人',dataIndex:'VISITOR',sortable:true,width:120},
	 {header:'拜访情况',dataIndex:'VISIT_NOTE',sortable:true,width:120,listeners : {
			 'click' : {
					fn : function(field,row) {
						var custId = row.store.reader.jsonData.json.data[0].CUST_ID;
						parent._APP.openWindow({
							name : 'callReport',
							action : basepath
									+ '/crmweb/contents/pages/wlj/serviceManager/callReport.js?condis='+custId,
							resId : 192278,//系统菜单表ID
							id : 'task_192278',
							serviceObject : false
						});
					},
					scope : this
				}
		}},
	 {header:'任务安排人',dataIndex:'ARANGE_ID',sortable:true,width:120,hidden:true},
	 {header:'任务安排人',dataIndex:'ARANGE_NAME',sortable:true,width:120}
]); 
var sm2 = new Ext.grid.CheckboxSelectionModel();
var rownum2 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm2 =  new Ext.grid.ColumnModel([rownum2,sm2,	
     {header : 'W_ID', dataIndex : 'W_ID',sortable : true,width : 120,hidden:true},
     {header:'SCH_ID',dataIndex:'SCH_ID',sortable:true,width:120,hidden:true},
     {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
     {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
     {header:'任务周期',dataIndex:'WEEK_CYCLE',sortable:true,width:120},
     {header:'计划完成进度',dataIndex:'SCHEDULE',sortable:true,width:120},
     {header:'计划开始时间',dataIndex:'SCH_START_TIME',sortable:true,width:120},
     {header:'计划完成时间',dataIndex:'SCH_EDN_TIME',sortable:true,width:120},
     {header:'实际开始时间',dataIndex:'ACT_START_TIME',sortable:true,width:120},
     {header:'实际完成时间',dataIndex:'ACT_END_TIME',sortable:true,width:120},
     {header:'周工作计划任务内容',dataIndex:'SCH_CONTENT',sortable:true,width:120},
     {header:'未按时完成说明',dataIndex:'UNFINISHED_REMARK',sortable:true,width:120},
     {header:'计划执行状态',dataIndex:'STAT',sortable:true,width:120,hidden:true},
     {header:'计划执行状态',dataIndex:'STAT_ORA',sortable:true,width:120},
     {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
     {header:'任务安排人',dataIndex:'ARANGE_ID',sortable:true,width:120,hidden:true},
     {header:'任务安排人',dataIndex:'ARANGE_NAME',sortable:true,width:120}
]); 

var sm3 = new Ext.grid.CheckboxSelectionModel();
var rownum3 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm3 =  new Ext.grid.ColumnModel([rownum3,sm3,	
	 {header : 'W_ID', dataIndex : 'W_ID',sortable : true,width : 120,hidden:true},
	 {header:'SCH_ID',dataIndex:'SCH_ID',sortable:true,width:120,hidden:true},
	 {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
	 {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
	 {header:'任务周期',dataIndex:'MONTH_CYCLE',sortable:true,width:120},
	 {header:'计划完成进度',dataIndex:'SCHEDULE',sortable:true,width:120},
	 {header:'计划开始时间',dataIndex:'SCH_START_TIME',sortable:true,width:120},
	 {header:'计划完成时间',dataIndex:'SCH_EDN_TIME',sortable:true,width:120},
	 {header:'实际开始时间',dataIndex:'ACT_START_TIME',sortable:true,width:120},
	 {header:'实际完成时间',dataIndex:'ACT_END_TIME',sortable:true,width:120},
	 {header:'月工作计划任务内容',dataIndex:'SCH_CONTENT',sortable:true,width:120},
	 {header:'未按时完成说明',dataIndex:'UNFINISHED_REMARK',sortable:true,width:120},
	 {header:'计划执行状态',dataIndex:'STAT',sortable:true,width:120,hidden:true},
	 {header:'计划执行状态',dataIndex:'STAT_ORA',sortable:true,width:120},
	 {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
	 {header:'任务安排人',dataIndex:'ARANGE_ID',sortable:true,width:120,hidden:true},
	 {header:'任务安排人',dataIndex:'ARANGE_NAME',sortable:true,width:120}
]); 
var sm4 = new Ext.grid.CheckboxSelectionModel();
var rownum4 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm4 =  new Ext.grid.ColumnModel([rownum4,sm4,	
	 {header : 'L_ID', dataIndex : 'L_ID',sortable : true,width : 120,hidden:true},
	 {header:'SCH_ID',dataIndex:'SCH_ID',sortable:true,width:120,hidden:true},
	 {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
	 {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
	 {header:'贷款余额',dataIndex:'LOAN_BAL',sortable:true,width:120},
	 {header:'检查状态',dataIndex:'STAT',sortable:true,width:120,hidden:true},
	 {header:'检查状态',dataIndex:'STAT_ORA',sortable:true,width:120},
	 {header:'检查落实情况',dataIndex:'CHECK_SIT',sortable:true,width:120},
	 {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
	 {header:'任务安排人',dataIndex:'ARANGE_ID',sortable:true,width:120,hidden:true},
	 {header:'任务安排人',dataIndex:'ARANGE_NAME',sortable:true,width:120}
]); 

var sm5 = new Ext.grid.CheckboxSelectionModel();
var rownum5 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm5 =  new Ext.grid.ColumnModel([rownum5,sm5,	
    {header : 'C_ID', dataIndex : 'C_ID',sortable : true,width : 120,hidden:true},
	{header:'SCH_ID',dataIndex:'SCH_ID',sortable:true,width:120,hidden:true},
	{header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
	{header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
	{header:'完成状态',dataIndex:'STAT',sortable:true,width:120,hidden:true},
	{header:'完成状态',dataIndex:'STAT_ORA',sortable:true,width:120},
	{header:'授信工作要求',dataIndex:'CRD_REQ',sortable:true,width:120},
	{header:'完成情况',dataIndex:'COMP_SIT',sortable:true,width:120},
	{header:'备注',dataIndex:'REMARK',sortable:true,width:120},
	{header:'任务安排人',dataIndex:'ARANGE_NAME',sortable:true,width:120}
]); 

var sm6 = new Ext.grid.CheckboxSelectionModel();
var rownum6 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm6 =  new Ext.grid.ColumnModel([rownum6,sm6,	
     {header : 'O_ID', dataIndex : 'O_ID',sortable : true,width : 120,hidden:true},
     {header:'SCH_ID',dataIndex:'SCH_ID',sortable:true,width:120,hidden:true},
     {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
     {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
     {header:'完成状态',dataIndex:'STAT',sortable:true,width:120,hidden:true},
     {header:'完成状态',dataIndex:'STAT_ORA',sortable:true,width:120},
     {header:'情况说明',dataIndex:'OTH_SIT_REMARK',sortable:true,width:120},
     {header:'完成情况说明',dataIndex:'OTH_COMP_REMARK',sortable:true,width:120},
     {header:'任务安排人',dataIndex:'ARANGE_NAME',sortable:true,width:120}
]); 
	

var sm7 = new Ext.grid.CheckboxSelectionModel();
var rownum7 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
var cm7 =  new Ext.grid.ColumnModel([rownum6,sm6,	
     {header:'主键ID', dataIndex:'ID',sortable : true,width : 120,hidden:true},
     {header:'假期开始日期',dataIndex:'BEGIN_DATE',sortable:true,width:120},
     {header:'假期开始时间',dataIndex:'BEGIN_TIME',sortable:true,width:120,hidden:true},
     {header:'假期开始时间',dataIndex:'BEGIN_TIME_ORA',sortable:true,width:120},
     {header:'假期结束日期',dataIndex:'END_DATE',sortable:true,width:120},
     {header:'假期结束时间',dataIndex:'END_TIME',sortable:true,width:120,hidden:true},
     {header:'假期结束时间',dataIndex:'END_TIME_ORA',sortable:true,width:120},
     {header:'假期类型',dataIndex:'HOLIDAY_TYPE',sortable:true,width:120,hidden:true},
     {header:'假期类型',dataIndex:'HOLIDAY_TYPE_ORA',sortable:true,width:120},
     {header:'用户编号',dataIndex:'ACCOUNT_NAME',sortable:true,width:120},
     {header:'操作时间',dataIndex:'RECORD_DATE',sortable:true,width:120}
]);
var grid1 = new Ext.grid.GridPanel({
	title:'客户拜访',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        hidden:JsContext.checkGrant('schedulVistAdd'),
        handler:function() {
        	add(1);
        }
	},{
		id:'back1',
		text:'反馈',
		hidden:JsContext.checkGrant('schedulVistBack'),
		handler:function(){
			feedback(1,grid1);
		}
	},{
		text:'删除',
		hidden:JsContext.checkGrant('schedulVistDel'),
		handler:function(){
			del(1,grid1,store1,'V_ID');
		}
	}],
	store : store1,
	frame : true,
	sm:sm1,
	cm : cm1
});
var grid2 = new Ext.grid.GridPanel({
	title:'周工作计划',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        handler:function() {
        	add(2);
        }
	},{
		text:'反馈',
		id:'back2',
		handler:function(){
			feedback(2,grid2);
		}
	},{
		text:'删除',
		handler:function(){
			del(2,grid2,store2,'W_ID');
		}
	}],
	store : store2,
	frame : true,
	sm:sm2,
	cm : cm2
});
var grid3 = new Ext.grid.GridPanel({
	title:'月工作计划',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        handler:function() {
        	add(3);
        }
	},{
		text:'反馈',
		id:'back3',
		handler:function(){
			feedback(3,grid3);
		}
	},{
		text:'删除',
		handler:function(){
			del(3,grid3,store3,'W_ID');
		}
	}],
	store : store3,
	frame : true,
	sm:sm3,
	cm : cm3
});
var grid4 = new Ext.grid.GridPanel({
	title:'贷后检查任务',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        handler:function() {
        	add(4);
        }
	},{
		text:'反馈',
		id:'back4',
		handler:function(){
			feedback(4,grid4);
		}
	},{
		text:'删除',
		handler:function(){
			del(4,grid4,store4,'L_ID');
		}
	}],
	store : store4,
	frame : true,
	sm:sm4,
	cm : cm4
});
var grid5 = new Ext.grid.GridPanel({
	title:'授信任务',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        handler:function() {
        	add(5);
        }
	},{
		text:'反馈',
		id:'back5',
		handler:function(){
			feedback(5,grid5);
		}
	},{
		text:'删除',
		handler:function(){
			del(5,grid5,store5,'C_ID');
		}
	}],
	store : store5,
	frame : true,
	sm:sm5,
	cm : cm5
});
var grid6 = new Ext.grid.GridPanel({
	title:'其他任务',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        handler:function() {
        	add(6);
        }
	},{
		text:'反馈',
		id:'back6',
		handler:function(){
			feedback(6,grid6);
		}
	},{
		text:'删除',
		handler:function(){
			del(6,grid6,store6,'O_ID');
		}
	}],
	store : store6,
	frame : true,
	sm:sm6,
	cm : cm6
});

var grid7 = new Ext.grid.GridPanel({
	title:'扣除的工作用时',
	height:200,
	stripeRows : true,
	viewConfig : {
		getRowClass : function(record,rowIndex,rowParams,store){
			//根据阅读状态修改背景颜色  
			if(record.data.ARANGE_ID==__userId || ( record.data.VISITOR == record.data.ARANGE_ID)){
			  	return 'my_row_set_blue';  //修改背景颜色
		  	}else{
		   		return 'my_row_set_red';
		  	}
		}
	},
	tbar : [{
        text : '新增',
        id:'addForm',
        handler:function() {
        	add_form.form.reset();
            showCustomerViewByTitle('新增信息');
        }
	},{
		text:'修改',
		id:'modForm',
		handler:function(){
			 if(grid7.getSelectionModel().getSelected() == null){
        		Ext.Msg.alert('提示','请选择一条数据!');
				return false;
        	}
            mod_form.form.reset();
            var record = grid7.getSelectionModel().getSelected();
            mod_form.getForm().findField('ID').setValue(record.data.ID);
            mod_form.getForm().findField('BEGIN_DATE').setValue(record.data.BEGIN_DATE);
            mod_form.getForm().findField('BEGIN_TIME').setValue(record.data.BEGIN_TIME);
            mod_form.getForm().findField('END_DATE').setValue(record.data.END_DATE);
            mod_form.getForm().findField('END_TIME').setValue(record.data.END_TIME);
            mod_form.getForm().findField('HOLIDAY_TYPE').setValue(record.data.HOLIDAY_TYPE);
            mod_form.getForm().findField('ACCOUNT_NAME').setValue(record.data.ACCOUNT_NAME);
            mod_form.getForm().findField('RECORD_DATE').setValue(record.data.RECORD_DATE);
            showCustomerViewByTitle('修改信息');
		}
	},{
		text:'删除',
		id:'delForm',
		handler:function(){
			if(grid7.getSelectionModel().getSelected() == null){
        		Ext.Msg.alert('提示','请选择一条数据!');
				return false;
        	}
			Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}
				var record = grid7.getSelectionModel().getSelected();
				var id = record.data.ID;
				Ext.Ajax.request({
			    	url : basepath + '/ocrmFWpScheduleH!batchDel.json',  
			    	params : {
						id : id
					},
                    success : function(){
                        Ext.Msg.alert('提示', '删除成功');
                        store7.reload();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                        store7.reload();
                    }
                });
			})
		}
	}],
	store : store7,
	frame : true,
	sm : sm7,
	cm : cm7,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var add_form = new Ext.form.FormPanel({
	labelWidth: 100,	
    frame: true,	
    labelAlign: 'middle',	
    buttonAlign: 'center',
    items: [{
    	layout:'column',
    	items:[{
    		columnWidth: 0.50,
	        layout: 'form',
	        items: [{xtype : 'textfield',fieldLabel : '主键ID',name : 'ID',anchor:'90%',labelStyle: 'text-align:right;',hidden:true},
				  {xtype : 'datefield',fieldLabel : '<font color="red">*</font>假期开始日期',name : 'BEGIN_DATE',anchor:'90%',format : 'Y-m-d',labelStyle: 'text-align:right;',allowBlank: false},
				  {xtype : 'datefield',fieldLabel : '<font color="red">*</font>假期结束日期',name : 'END_DATE',anchor:'90%',format : 'Y-m-d',labelStyle: 'text-align:right;',allowBlank: false},
				  {xtype : 'combo',fieldLabel : '<font color="red">*</font>假期类型',name : 'HOLIDAY_TYPE',anchor:'90%',mode : 'local',store:holidayStore,triggerAction : 'all',displayField:'value',valueField:'key',labelStyle: 'text-align:right;',allowBlank: false,editable:false},
				  {xtype : 'datefield',fieldLabel : '操作时间',name : 'RECORD_DATE',readOnly:true,cls:'x-readOnly',anchor:'90%',format : 'Y-m-d',labelStyle: 'text-align:right;',value:new Date(),hidden:true}]
    	},{
    		columnWidth: 0.50,
	        layout: 'form',
	        items: [
				  {xtype : 'combo',fieldLabel : '<font color="red">*</font>假期开始时间',name : 'BEGIN_TIME',anchor:'90%',mode : 'local',store:timeStore,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank: false,labelStyle: 'text-align:right;'},
				  {xtype : 'combo',fieldLabel : '<font color="red">*</font>假期结束时间',name : 'END_TIME',anchor:'90%',mode : 'local',store:timeStore,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank: false,labelStyle: 'text-align:right;'},
				  {xtype : 'textfield',fieldLabel : '用户编号',name : 'ACCOUNT_NAME',readOnly:true,cls:'x-readOnly',anchor:'90%',labelStyle: 'text-align:right;',hidden:true}
       		]
    	}]
    }],
    buttons: [{
        text: '确认',
        handler: function() {
        	if (!add_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(add_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
        	var beginDate = commintData.beginDate;
	        var endDate = commintData.endDate;
//	        var beginTime = commintData.beginTime;
//	        var endTime = commintData.endTime;
	        if(beginDate.format('Y-m-d')>endDate.format('Y-m-d')){
	            Ext.MessageBox.alert('提示','假期结束日期不能小于假期开始日期!');
			    return false;
	        }
            Ext.Ajax.request({
                url: basepath + '/ocrmFWpScheduleH!saveData.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                   	 Ext.Msg.alert('提示', '操作成功！');
                     add_form.form.reset();
                     store7.load({
                         params: {
                            id: commintData.id
                         }
                     });
                     showCustomerViewByTitle('日程信息');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
        }
    },{
        text: '取消',
        handler: function() {
            showCustomerViewByTitle('日程信息');
        }
    }]
});

var mod_form = new Ext.form.FormPanel({
    labelWidth: 100,	// 标签宽度
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'middle',	// 标签对齐方式
    buttonAlign: 'center',
    height: 200,
    items: [{
    	layout:'column',
    	items:[{
    		columnWidth: 0.50,
	        layout: 'form',
	        items: [{xtype : 'textfield',fieldLabel : '主键ID',name : 'ID',anchor:'90%',labelStyle: 'text-align:right;',hidden:true},
				  {xtype : 'datefield',fieldLabel : '<font color="red">*</font>假期开始日期',name : 'BEGIN_DATE',anchor:'90%',format : 'Y-m-d',labelStyle: 'text-align:right;',allowBlank: false},
				  {xtype : 'datefield',fieldLabel : '<font color="red">*</font>假期结束日期',name : 'END_DATE',anchor:'90%',format : 'Y-m-d',labelStyle: 'text-align:right;',allowBlank: false},
				  {xtype : 'combo',fieldLabel : '<font color="red">*</font>假期类型',name : 'HOLIDAY_TYPE',anchor:'90%',mode : 'local',store:holidayStore,triggerAction : 'all',displayField:'value',valueField:'key',labelStyle: 'text-align:right;',allowBlank: false,editable:false},
				  {xtype : 'datefield',fieldLabel : '操作时间',name : 'RECORD_DATE',readOnly:true,cls:'x-readOnly',anchor:'90%',format : 'Y-m-d',labelStyle: 'text-align:right;',value:new Date()}]
    	},{
    		columnWidth: 0.50,
	        layout: 'form',
	        items: [
				  {xtype : 'combo',fieldLabel : '<font color="red">*</font>假期开始时间',name : 'BEGIN_TIME',anchor:'90%',mode : 'local',store:timeStore,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank: false,labelStyle: 'text-align:right;'},
				  {xtype : 'combo',fieldLabel : '<font color="red">*</font>假期结束时间',name : 'END_TIME',anchor:'90%',mode : 'local',store:timeStore,triggerAction : 'all',displayField:'value',valueField:'key',allowBlank: false,labelStyle: 'text-align:right;'},
				  {xtype : 'textfield',fieldLabel : '用户编号',name : 'ACCOUNT_NAME',readOnly:true,cls:'x-readOnly',anchor:'90%',labelStyle: 'text-align:right;'}
       		]
    	}]
    }],
    buttons: [{
        text: '确认',
        handler: function() {
        	if (!mod_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(mod_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
        	var beginDate = commintData.beginDate;
	        var endDate = commintData.endDate;
//	        var beginTime = commintData.beginTime;
//	        var endTime = commintData.endTime;
	        if(beginDate.format('Y-m-d')>endDate.format('Y-m-d')){
	            Ext.MessageBox.alert('提示','假期结束日期不能小于假期开始日期!');
			    return false;
	        }
            Ext.Ajax.request({
                url: basepath + '/ocrmFWpScheduleH!saveData.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                   	 Ext.Msg.alert('提示', '操作成功！');
                     add_form.form.reset();
                     store7.load({
                         params: {
                            id: commintData.id
                         }
                     });
                     showCustomerViewByTitle('日程信息');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
        }
    },{
        text: '取消',
        handler: function() {
            showCustomerViewByTitle('日程信息');
        }
    }]
});

var infofrom = new Ext.FormPanel({
	frame : true,
	height:60,
	items:[ {
		layout : 'column',
		items : [ {
			layout : 'form',
			columnWidth : .5,
			labelWidth : 80,
			items : [{xtype:'datefield',fieldLabel:'工作日期',editable : false,labelStyle:'text-align:right;',
						value:new Date(),format : 'Y-m-d',anchor:'90%',readOnly:true,name:'date'}]
		},{
			layout : 'form',
			columnWidth : .5,
			labelWidth : 80,
			items : [new Com.yucheng.crm.common.OrgUserManage({ 
							xtype:'userchoose',
							fieldLabel : '客户经理', 
							labelStyle: 'text-align:right;',
							name : 'USER_NAME',
							hiddenName:'USER_ID',
//							searchRoleType:('127,47'),  //指定查询角色属性 ,默认全部角色
							searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							singleSelect:false,
							anchor : '90%',
							callback:function(){
								getInformation(infofrom.getForm().getValues().USER_ID,infofrom.getForm().getValues().date);
							}
							})]
		}]}]
});
		
var infoPanel = new Ext.Panel({
	autoScroll : true,
	items:[infofrom,grid1,grid7,grid2,grid3,grid4,grid5,grid6],
	buttonAlign:'center',
	buttons:[{text:'下达',
		id:'xd',
		handler:function(){
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/ocrmFWpSchedule!jobXD.json',
				method : 'POST',
				params : {
					schId:schId
				},
				success : function(response) {
					var num = response.responseText;
					var nums = num.split("#");
					Ext.Msg.alert('提示', '操作成功.下发客户拜访任务['+nums[0]+']条;周工作任务['+nums[1]+']条;月工作任务['+nums[2]+']条;'+
							'贷后检查任务['+nums[3]+']条;授信工作任务['+nums[4]+']条;其他任务['+nums[5]+']条');
					
					//查询子表信息
					store1.load({ params:{schId:schId,userId:userId}});
					store2.load({ params:{schId:schId,userId:userId}});
					store3.load({ params:{schId:schId,userId:userId}});
					store4.load({ params:{schId:schId,userId:userId}});
					store5.load({ params:{schId:schId,userId:userId}});
					store6.load({ params:{schId:schId,userId:userId}});
				}
			});
			
		}},
		{text:'确认',
		id:'qr',
		handler:function(){
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/ocrmFWpSchedule!jobQR.json',
				method : 'POST',
				params : {
					schId:schId
				},
				success : function(response) {
					var num = response.responseText;
					var nums = num.split("#");
					Ext.Msg.alert('提示', '操作成功.确认客户拜访任务['+nums[0]+']条;周工作任务['+nums[1]+']条;月工作任务['+nums[2]+']条;'+
							'贷后检查任务['+nums[3]+']条;授信工作任务['+nums[4]+']条;其他任务['+nums[5]+']条');
					
					//查询子表信息
					store1.load({ params:{schId:schId,userId:userId}});
					store2.load({ params:{schId:schId,userId:userId}});
					store3.load({ params:{schId:schId,userId:userId}});
					store4.load({ params:{schId:schId,userId:userId}});
					store5.load({ params:{schId:schId,userId:userId}});
					store6.load({ params:{schId:schId,userId:userId}});
				}
			});
			
		
		}}]
});
		
var cust2 = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '<font color="red">*</font>客户名称',
	allowBlank : false,
	text:'客户名称',
	name : 'CUST_NAME',
	singleSelected : true,// 单选复选标志
	editable : false,
	hiddenName : 'CUST_ID',
	anchor:'90%'});
var cust3 = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '<font color="red">*</font>客户名称',
	allowBlank : false,
	text:'客户名称',
	name : 'CUST_NAME',
	singleSelected : true,// 单选复选标志
	editable : false,
	hiddenName : 'CUST_ID',
	anchor:'90%'});
var cust4 = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '<font color="red">*</font>客户名称',
	allowBlank : false,
	text:'客户名称',
	name : 'CUST_NAME',
	singleSelected : true,// 单选复选标志
	editable : false,
	hiddenName : 'CUST_ID',
	anchor:'90%'});
var cust5 = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '<font color="red">*</font>客户名称',
	allowBlank : false,
	text:'客户名称',
	name : 'CUST_NAME',
	singleSelected : true,// 单选复选标志
	editable : false,
	hiddenName : 'CUST_ID',
	anchor:'90%'});
var cust6 = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '客户名称',
	text:'客户名称',
	name : 'CUST_NAME',
	singleSelected : true,// 单选复选标志
	editable : false,
	hiddenName : 'CUST_ID',
	anchor:'90%'});


//日程信息内容
var customerView = [{
	title : '日程信息',
	hideTitle : true,
	xtype : 'panel',
	frame : true,
	layout : 'fit',
	recordView : true,
	items:[infoPanel]
},{
				title : '客户拜访',
				type : 'form',
				hideTitle : true,
				groups : [{
					columnCount : 2 ,
					fields : [{name : 'CUST_TYPE',anchor:'90%',xtype : 'textfield',text : '客户类型',translateType : 'XD000080',hidden:false},
								{name : 'VISIT_TYPE',anchor:'90%',xtype : 'textfield',text : '拜访方式',translateType : 'VISIT_TYPE',allowBlank:false},
								{name : 'PHONE',anchor:'90%',xtype : 'textfield',text : '联系电话'},
								{name : 'VISITOR',anchor:'90%',xtype : 'textfield',text : '拜访人',readOnly:true},
								{name : 'VISIT_STAT',anchor:'90%',xtype : 'textfield',text : '拜访状态',translateType : 'VISIT_STAT'},
								{name : 'SCH_START_TIME',anchor:'90%',xtype : 'datefield',format:'Y-m-d',text : '拜访开始时间'},
								{name : 'SCH_EDN_TIME',anchor:'90%',xtype : 'datefield',format:'Y-m-d',text : '拜访结束时间'},
								{name : 'SCH_ID',anchor:'90%',xtype : 'textfield',text : 'SCH_ID'},
								{name : 'V_ID',anchor:'90%',xtype : 'textfield',text : 'V_ID'},
								{name : 'ARANGE_ID',anchor:'90%',xtype : 'textfield',text : 'ARANGE_ID'}],
					fn : function(CUST_TYPE,VISIT_TYPE,PHONE,VISITOR,VISIT_STAT,SCH_START_TIME,SCH_EDN_TIME,SCH_ID,V_ID, ARANGE_ID){
						SCH_ID.hidden = true;
						ARANGE_ID.hidden = true;
						V_ID.hidden = true;
						var cust1 = new Com.yucheng.bcrm.common.CustomerQueryField({
							fieldLabel : '<font color="red">*</font>客户名称',
							text:'客户名称',
							allowBlank : false,
							name : 'CUST_NAME',
							singleSelected : true,// 单选复选标志
							editable : false,
							hiddenName : 'CUST_ID',
							callback:function(a,b){
								//设置CUST_TYPE
								var custType=b[0].data.CUST_TYPE;
								getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue(custType);
								getCurrentView().contentPanel.getForm().findField('CUST_TYPE').readOnly=true;
							}
							,anchor:'90%'
						});
						return [cust1,CUST_TYPE,VISIT_TYPE,PHONE,SCH_START_TIME,SCH_EDN_TIME,VISITOR,VISIT_STAT,SCH_ID,V_ID,ARANGE_ID];
					}
				},
				{
					columnCount : 1,
					fields : [{name : 'VISIT_NOTE',anchor:'90%',xtype : 'textarea',text : '客户联系和拜访情况'}],
					fn : function(VISIT_NOTE){
						return [VISIT_NOTE];
					}
				}],
				formButtons : [{text:'确认',
					fn:function(formPanel,basicForm){
						form(1,formPanel,basicForm,store1);
					}},{text:'取消',
				         fn:function(){
				        	 showCustomerViewByTitle('日程信息');		
				   }}]
			},{
				title : '周工作计划',
				type : 'form',
				hideTitle : true,
				groups : [{
					columnCount : 2 ,
					fields : [{name : 'SCH_START_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '计划开始时间'},
								{name : 'SCH_EDN_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '计划完成时间'},
								{name : 'ACT_START_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '实际开始时间'},
								{name : 'ACT_END_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '实际完成时间'},
								{name : 'SCHEDULE',anchor:'90%',xtype : 'textfield',text : '计划完成进度'},
								{name : 'WEEK_CYCLE',anchor:'90%',xtype : 'textfield',text : '任务周期'},
								{name : 'STAT',anchor:'90%',xtype : 'textfield',text : '计划执行状态',translateType : 'STAT_PLAN'},
							    {name : 'SCH_ID',anchor:'90%',xtype : 'textfield',text : 'SCH_ID'},
							    {name : 'W_ID',anchor:'90%',xtype : 'textfield',text : 'W_ID'},
							    {name : 'ARANGE_ID',anchor:'90%',xtype : 'textfield',text : 'ARANGE_ID'}],
					fn : function(SCH_START_TIME,SCH_EDN_TIME,ACT_START_TIME,ACT_END_TIME,SCHEDULE,WEEK_CYCLE,STAT,SCH_ID,W_ID, ARANGE_ID){
						SCH_ID.hidden = true;
						ARANGE_ID.hidden = true;
						W_ID.hidden = true;
						return [cust2,WEEK_CYCLE,SCH_START_TIME,SCH_EDN_TIME,ACT_START_TIME,ACT_END_TIME,SCHEDULE,STAT,SCH_ID,W_ID,ARANGE_ID];
					}
				},
				{
					columnCount : 1,
					fields : [{name : 'SCH_CONTENT',anchor:'90%',xtype : 'textarea',text : '周工作计划任务内容'},
					{name : 'UNFINISHED_REMARK',anchor:'90%',xtype : 'textarea',text : '未按时完成说明'},
					{name : 'REMARK',anchor:'90%',xtype : 'textarea',text : '备注'}],
					fn : function(SCH_CONTENT,UNFINISHED_REMARK,REMARK){
						return [SCH_CONTENT,UNFINISHED_REMARK,REMARK];
					}
				}],
				formButtons : [{text:'确认',
					fn:function(formPanel,basicForm){
						form(2,formPanel,basicForm,store2);
					}},{text:'取消',
				         fn:function(){
				        	 showCustomerViewByTitle('日程信息');		
				   }}]
			
			},{
				title : '月工作计划',
				type : 'form',
				hideTitle : true,
				groups : [{
					columnCount : 2 ,
					fields : [{name : 'SCH_START_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '计划开始时间'},
								{name : 'SCH_EDN_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '计划完成时间'},
								{name : 'ACT_START_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '实际开始时间'},
								{name : 'ACT_END_TIME',anchor:'90%',xtype : 'datefield',format : 'Y-m-d',text : '实际完成时间'},
								{name : 'SCHEDULE',anchor:'90%',xtype : 'textfield',text : '计划完成进度'},
								{name : 'MONTH_CYCLE',anchor:'90%',xtype : 'textfield',text : '任务周期'},
								{name : 'STAT',anchor:'90%',xtype : 'textfield',text : '计划执行状态',translateType : 'STAT_PLAN'},
							    {name : 'SCH_ID',anchor:'90%',xtype : 'textfield',text : 'SCH_ID'},
							    {name : 'W_ID',anchor:'90%',xtype : 'textfield',text : 'W_ID'},
							    {name : 'ARANGE_ID',anchor:'90%',xtype : 'textfield',text : 'ARANGE_ID'}],
					fn : function(SCH_START_TIME,SCH_EDN_TIME,ACT_START_TIME,ACT_END_TIME,SCHEDULE,MONTH_CYCLE,STAT,SCH_ID,W_ID, ARANGE_ID){
						SCH_ID.hidden = true;
						ARANGE_ID.hidden = true;
						W_ID.hidden = true;
						return [cust3,MONTH_CYCLE,SCH_START_TIME,SCH_EDN_TIME,ACT_START_TIME,ACT_END_TIME,SCHEDULE,STAT,SCH_ID,W_ID,ARANGE_ID];
					}
				},
				{
					columnCount : 1,
					fields : [{name : 'SCH_CONTENT',anchor:'90%',xtype : 'textarea',text : '月工作计划任务内容'},
					{name : 'UNFINISHED_REMARK',anchor:'90%',xtype : 'textarea',text : '未按时完成说明'},
					{name : 'REMARK',anchor:'90%',xtype : 'textarea',text : '备注'}],
					fn : function(SCH_CONTENT,UNFINISHED_REMARK,REMARK){
						return [SCH_CONTENT,UNFINISHED_REMARK,REMARK];
					}
				}],
				formButtons : [{text:'确认',
					fn:function(formPanel,basicForm){
						form(3,formPanel,basicForm,store3);
					}},{text:'取消',
				         fn:function(){
				        	 showCustomerViewByTitle('日程信息');		
				   }}]
			},{
				title : '贷后检查',
				type : 'form',
				hideTitle : true,
				groups : [{
					columnCount : 2 ,
					fields : [
							{name : 'LOAN_BAL',anchor:'90%',xtype : 'numberfield',text : '贷款余额'},
							{name : 'STAT',anchor:'90%',xtype : 'textfield',text : '检查状态',translateType : 'STAT'},
							  {name : 'SCH_ID',anchor:'90%',xtype : 'textfield',text : 'SCH_ID'},
							  {name : 'L_ID',anchor:'90%',xtype : 'textfield',text : 'L_ID'},
							  {name : 'ARANGE_ID',anchor:'90%',xtype : 'textfield',text : 'ARANGE_ID'}],
					fn : function(LOAN_BAL,STAT,SCH_ID,L_ID, ARANGE_ID){
						SCH_ID.hidden = true;
						ARANGE_ID.hidden = true;
						L_ID.hidden = true;
						return [cust4,LOAN_BAL,STAT,SCH_ID,L_ID,ARANGE_ID];
					}
				},
				{
					columnCount : 1,
					fields : [{name : 'CHECK_SIT',anchor:'90%',xtype : 'textarea',text : '检查落实情况'},
					{name : 'REMARK',anchor:'90%',xtype : 'textarea',text : '备注'}],
					fn : function(CHECK_SIT,REMARK){
						return [CHECK_SIT,REMARK];
					}
				}],
				formButtons : [{text:'确认',
					fn:function(formPanel,basicForm){
						form(4,formPanel,basicForm,store4);
					}},{text:'取消',
				         fn:function(){
				        	 showCustomerViewByTitle('日程信息');		
				   }}]
			
			},{
				title : '授信检查',
				type : 'form',
				hideTitle : true,
				groups : [{
					columnCount : 1 ,
					fields : [
					          {name : 'STAT',anchor:'90%',xtype : 'textfield',text : '检查状态',translateType : 'STAT_CRD'},
							  {name : 'SCH_ID',anchor:'90%',xtype : 'textfield',text : 'SCH_ID'},
							  {name : 'C_ID',anchor:'90%',xtype : 'textfield',text : 'C_ID'},
							  {name : 'ARANGE_ID',anchor:'90%',xtype : 'textfield',text : 'ARANGE_ID'}],
					fn : function(STAT,SCH_ID,C_ID, ARANGE_ID){
						SCH_ID.hidden = true;
						ARANGE_ID.hidden = true;
						C_ID.hidden = true;
						return [cust5,STAT,SCH_ID,C_ID,ARANGE_ID];
					}
				},
				{
					columnCount : 1,
					fields : [{name : 'CRD_REQ',anchor:'90%',xtype : 'textarea',text : '授信工作要求'},
					{name : 'COMP_SIT',anchor:'90%',xtype : 'textarea',text : '完成情况'},
					{name : 'REMARK',anchor:'90%',xtype : 'textarea',text : '备注'}],
					fn : function(CRD_REQ,COMP_SIT,REMARK){
						return [CRD_REQ,COMP_SIT,REMARK];
					}
				}],
				formButtons : [{text:'确认',
					fn:function(formPanel,basicForm){
						form(5,formPanel,basicForm,store5);
					}},{text:'取消',
				         fn:function(){
				        	 showCustomerViewByTitle('日程信息');		
				   }}]
			
			},{
				title : '其他工作',
				type : 'form',
				hideTitle : true,
				groups : [{
					columnCount : 1 ,
					fields : [
					          {name : 'STAT',anchor:'90%',xtype : 'textfield',text : '完成状态',translateType : 'STAT'},
							  {name : 'SCH_ID',anchor:'90%',xtype : 'textfield',text : 'SCH_ID'},
							  {name : 'O_ID',anchor:'90%',xtype : 'textfield',text : 'O_ID'},
							  {name : 'ARANGE_ID',anchor:'90%',xtype : 'textfield',text : 'ARANGE_ID'}],
					fn : function(STAT,SCH_ID,O_ID, ARANGE_ID){
						SCH_ID.hidden = true;
						ARANGE_ID.hidden = true;
						O_ID.hidden = true;
						return [cust6,STAT,SCH_ID,O_ID,ARANGE_ID];
					}
				},
				{
					columnCount : 1,
					fields : [{name : 'OTH_SIT_REMARK',anchor:'90%',xtype : 'textarea',text : '情况说明'},
					{name : 'OTH_COMP_REMARK',anchor:'90%',xtype : 'textarea',text : '完成情况说明'},
					{name : 'REMARK',anchor:'90%',xtype : 'textarea',text : '备注'}],
					fn : function(OTH_SIT_REMARK,OTH_COMP_REMARK,REMARK){
						return [OTH_SIT_REMARK,OTH_COMP_REMARK,REMARK];
					}
				}],
				formButtons : [{text:'确认',
					fn:function(formPanel,basicForm){
						form(6,formPanel,basicForm,store6);
					}},{text:'取消',
				         fn:function(){
				        	 showCustomerViewByTitle('日程信息');		
				   }}]
			},{
			  	title:'新增信息',
				hideTitle:true,
				type: 'form',
				items:[add_form]
			},{
			  	title:'修改信息',
				hideTitle:true,
				type: 'form',
				items:[mod_form]
			}];

/**
 * 根据日期和用户，获取日程记录id，然后返回，查询子表信息
 * @param {} user
 * @param {} dateNow
 */
function getInformation(user,dateNow){
	if(user != userId || dateNow != date){//只要有一个改变就需要重新查询
		Ext.Ajax.request({
			url : basepath + '/ocrmFWpSchedule!getSchId.json',
			method : 'POST',
			params : {
				date:dateNow,
		   	    userId:user
			},
			success : function(response) {
				schId = response.responseText;
				userId = user;
				date = dateNow;
				//查询子表信息
				store1.load({ params:{schId:schId,userId:userId,date:date}});
				store2.load({ params:{schId:schId,userId:userId}});
				store3.load({ params:{schId:schId,userId:userId}});
				store4.load({ params:{schId:schId,userId:userId}});
				store5.load({ params:{schId:schId,userId:userId}});
				store6.load({ params:{schId:schId,userId:userId}});
			}
		});
	}
	//处理按钮
	if(user == __userId){
		//Ext.getCmp('back1').show();
		Ext.getCmp('back2').show();
		Ext.getCmp('back3').show();
		Ext.getCmp('back4').show();
		Ext.getCmp('back5').show();
		Ext.getCmp('back6').show();
		Ext.getCmp('xd').setDisabled(true);
		Ext.getCmp('qr').setDisabled(true);
	}else{
		//Ext.getCmp('back1').hide();
		Ext.getCmp('back2').hide();
		Ext.getCmp('back3').hide();
		Ext.getCmp('back4').hide();
		Ext.getCmp('back5').hide();
		Ext.getCmp('back6').hide();
		Ext.getCmp('xd').setDisabled(false);
		Ext.getCmp('qr').setDisabled(false);
	}
};

//页面初始化完成之后，查询
var afterinit = function(){
	infofrom.getForm().setValues({
		USER_ID:__userId,
		USER_NAME:__userName
	});
	month = (new Date()).getFullYear() +'年'+((new Date()).getMonth()+1)+'月';
	week = (new Date()).getFullYear() +'年'+((new Date()).getWeekOfYear()+1)+'周';
	getInformation(__userId,(new Date()).format('Y-m-d'));
	eventStore.load({
		params:{ 
			start:calPanel.activeView.viewStart.dateFormat('Ymd'),
			end:calPanel.activeView.viewEnd.dateFormat('Ymd')
		},
		callback:function(){
		}
	});
};

//打开新增
function add(type){
	openWay = 1;
	showCustomerViewByIndex(type);
};
//打开反馈
function feedback(type,grid){
   var selectLength = grid.getSelectionModel().getSelections().length;
	if (selectLength != 1) {
		Ext.Msg.alert('提示', '请选择一条记录！');
		return false;
	} 
   record = grid.getSelectionModel().getSelections()[0];
   if((record.data.STAT == 'wc'||record.data.STAT == 'qr')&&type!=1){
	   Ext.Msg.alert('提示', '[已完成]或[已确认]的任务不需要再反馈！');
		return false;
   }
   if((record.data.VISIT_STAT == 'wc'||record.data.VISIT_STAT == 'qr')&&type==1){
	   Ext.Msg.alert('提示', '[已拜访]或[已确认拜访]的任务不需要再反馈！');
		return false;
   }
   openWay = 2;
   showCustomerViewByIndex(type);
}
       //删除方法
   function del(type,grid,store,value){
	   var selectLength = grid.getSelectionModel().getSelections().length;
		if (selectLength < 1) {
			Ext.Msg.alert('提示', '请选择记录！');
		return false;
	} 
	var ids = '';
   if(userId == __userId){//操作自己的日程页面
	   for(var i=0;i<selectLength;i++){
		   var record = grid.getSelectionModel().getSelections()[i].data;
		   if(record.ARANGE_ID != __userId){
			   Ext.Msg.alert('提示', '只能删除自己安排的任务记录！');
				return false;
		   }
		   if(type!=1&&(record.STAT == 'wc'||record.STAT == 'qr')){
			   Ext.Msg.alert('提示', '[已完成]或[已确认]的任务不能删除！');
				return false;
		   }
		   if(type==1&&(record.VISIT_STAT == 'wc'||record.VISIT_STAT == 'qr')){
			   Ext.Msg.alert('提示', '[已拜访]或[已确认拜访]的任务不能删除！');
				return false;
		   }
		   ids += record[value]+',';
	   }
   }else{//操作他人的日程页面
	   for(var i=0;i<selectLength;i++){
		   var record = grid.getSelectionModel().getSelections()[i].data;
		   if(record.ARANGE_ID != __userId){
			   Ext.Msg.alert('提示', '只能删除自己安排的任务记录！');
				return false;
		   }
		   if(type!=1&&record.STAT != 'xf'){
			   Ext.Msg.alert('提示', '只能删除[待下发]的任务！');
				return false;
		   }
		   if(type==1&&record.VISIT_STAT != 'xf'){
			   Ext.Msg.alert('提示', '只能删除[待下发]的任务！');
				return false;
		   }
		   ids += record[value]+',';
	   }
   }
   Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {
				return;
			}
			Ext.Ajax.request({
						url : basepath + '/ocrmFWpSchedule!batchDel.json?type='+type+'&schId='+schId,
						method : 'POST',
						params : {
							ids : ids
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功！');
							store.load({ params:{schId:schId,userId:userId}});
						},
						failure : function(response) {
								Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
							}
						});

			});
	   
   }

//保存方法
function form(type,formPanel,basicForm,store){
	   if (!formPanel.getForm().isValid()) {
           Ext.MessageBox.alert('提示','输入有误,请检查输入项');
           return false;
       }
	   
	   var data = formPanel.getForm().getFieldValues();
	  if(type==1 ||type==2 ||type==3){
		   var now_time = infofrom.getForm().getValues().date;
		   now_time = now_time.replace(/-/g, '/'); // "2010/08/01";
		   // 创建日期对象
		   var date = new Date(now_time);
		   var sch_start_time = data.SCH_START_TIME;
		   var sch_end_time = data.SCH_EDN_TIME;
		   var result = sch_start_time - sch_end_time;
		   var nowResult = date - sch_start_time;
		   if(date!="" && sch_start_time!="" && nowResult>0){
			   Ext.MessageBox.alert('提示','开始日期必须不小于当前日期');
	           return false;
		   }
		   if(sch_start_time!="" && sch_end_time!="" && result>0){
			   Ext.MessageBox.alert('提示','结束日期不能早于开始日期');
	           return false;
		   }
	   }
	   if(type==2 ||type==3){
		   var now_time = infofrom.getForm().getValues().date;
		   now_time = now_time.replace(/-/g, '/'); // "2010/08/01";
		   // 创建日期对象
		   var date = new Date(now_time);
		   
		   var act_start_time = data.ACT_START_TIME;
		   var act_end_time = data.ACT_END_TIME;
		   var result = act_start_time - act_end_time;
		   
		   var nowResult = date - act_start_time;
		   if(date!="" && act_start_time!="" && nowResult>0){
			   Ext.MessageBox.alert('提示','开始日期必须不小于当前日期');
	           return false;
		   }
		   if(act_start_time!="" && act_end_time!="" && result>0){
			   Ext.MessageBox.alert('提示','实际结束日期不能早于实际开始日期');
	           return false;
		   }
	   }
	   if( type !=1&&openWay == 2 && data.STAT == 'qr' &&__userId != data.ARANGE_ID){
		   Ext.MessageBox.alert('提示','这是领导安排的任务，不可以自己置为[已确认]状态!');
           return false;
	   }
	   if( type ==1&&openWay == 2 && data.VISIT_STAT == 'qr' &&__userId != data.ARANGE_ID){
		   Ext.MessageBox.alert('提示','这是领导安排的任务，不可以自己置为[已确认拜访]状态!');
           return false;
	   }
	   var commintData = translateDataKey(data,1);
	   var action = '';
	  if(type == 1){
		  action = 'ocrmFWpScheduleV';
	  } 
	  if(type == 2){
		  action = 'ocrmFWpScheduleW';
	  } 
	  if(type == 3){
		  action = 'ocrmFWpScheduleM';
	  } 
	  if(type == 4){
		  action = 'ocrmFWpScheduleL';
	  } 
	  if(type == 5){
		  action = 'ocrmFWpScheduleC';
	  } 
	  if(type == 6){
		  action = 'ocrmFWpScheduleO';
	  } 
	  Ext.Msg.wait('正在处理，请稍后......','系统提示');
	  Ext.Ajax.request({
			url : basepath + '/'+action+'!save.json?schIds='+schId+'&userId='+userId,
			method : 'POST',
			params : commintData,
			success : function(r) {
				Ext.Msg.alert('提示','操作成功！');
				showCustomerViewByIndex(0);
				store.load({ params:{schId:schId,userId:userId}});
			}
		});
	   
   }
var beforeviewshow = function(view){
	if(openWay == 1&& view._defaultTitle != '日程信息'){//新增页面，初始化表格
		view.contentPanel.getForm().reset();
	   	if(view._defaultTitle == '客户拜访'){
		   	view.contentPanel.getForm().findField("VISIT_STAT").hide();
		   	view.contentPanel.getForm().setValues({
			 	ARANGE_ID : __userId,
			 	VISITOR : infofrom.getForm().getValues().USER_ID
			});
	   	}else{
		   	view.contentPanel.getForm().findField("STAT").hide();
		   	view.contentPanel.getForm().setValues({
			 	ARANGE_ID : __userId
			});
	   	}
	   	if(view._defaultTitle == '周工作计划'){
		   	view.contentPanel.getForm().setValues({
			   WEEK_CYCLE : week,
			   SCH_START_TIME:(new Date()).format('Y-m-d')
			});
	   	}
	   	if(view._defaultTitle == '月工作计划'){
		   	view.contentPanel.getForm().setValues({
			   MONTH_CYCLE : month,
			   SCH_START_TIME:(new Date()).format('Y-m-d')
			});
	   	}
   	}
   	if(view._defaultTitle == '扣除的工作用时'){
	   	view.contentPanel.getForm().setValues({
	   	    ACCOUNT_NAME: __userId,
		    RECORD_DATE:(new Date()).format('Y-m-d')
		});
   	}
   	if(openWay == 2&& view._defaultTitle != '日程信息'){//修改页面，初始化表格
   		view.contentPanel.getForm().loadRecord(record);
   		if(view._defaultTitle == '客户拜访')
	   		view.contentPanel.getForm().findField("VISIT_STAT").show();
   		else
	   		view.contentPanel.getForm().findField("STAT").show();
	}
};
