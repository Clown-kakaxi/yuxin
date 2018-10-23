Ext.onReady(function(){
	
	var monthStore = new Ext.data.ArrayStore({
	    fields:['myId','displayText'],
	    data:[['1','存款业务'],['2','授信业务'],['3','国际业务'],
	          ['4','其他重点产品销售'],['5','其他工作']]
	});	
	var monthCombo = new Ext.form.ComboBox({id:'monthStore',
		store: monthStore,displayField:'displayText',valueField: 'myId',mode: 'local',
		editable:true, triggerAction: 'all',allowBlank:false,
		listeners : {
			select : function(a, b) {
				var v = a.value; // 取到valueField中的值
				monthCombo.setValue(v);
	}
}
    });	
	Ext.util.Format.comboRenderer = function(combo) {
		return function(value) {
			var record = combo.findRecord(combo.valueField, value);
			return record ? record.get(combo.displayField)
					: combo.valueNotFoundText;
		};
	};
/************************************右边添加的详情的PANEL**********************************************/
	 var rownum1 = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
			});      
	 var numberField = new Ext.form.NumberField({allowBlank : false,minValue:0.01});  
     var textField = new Ext.form.TextField({allowBlank : false,minValue:0});  
     var grids = new Ext.grid.ColumnModel([//gridtable中的列定义
                                           rownum1,
                                                      	{header :'月工作记录ID',dataIndex:'id',id:"id",width:100,sortable : true,hidden:true},
                                                      	{header :'关联主表ID',dataIndex:'worklogId',width:125,sortable : true,hidden:true},
                                                      	{header : '项目类型',dataIndex:'workType',width:125,sortable : true,renderer : Ext.util.Format.comboRenderer(monthCombo)},
                                                      	{header :'项目',dataIndex:'work',width:180,sortable : true,editor:textField},
                                                      	{header :'显示序号',dataIndex:'workOrder',width:125,sortable : true,hidden:true},
                                                      	{header :'计划完成 ',dataIndex:'workPlan',width:145,sortable : true,editor:textField},
                                                      	{header :'实际完成',dataIndex:'workExecute',width:145,sortable : true,editor:textField}
                                                      	]);
 	var monthWorkInfoRecords = new Ext.data.Record.create([	
                                                        	{name:'id',mapping:'ID'},
                                                        	{name:'worklogId',mapping:'WORKLOG_ID'},
                                                        	{name:'workType',mapping:'WORK_TYPE'},//项目类型
                                                        	{name:'work',mapping:'WORK'},//项目
                                                        	{name:'workOrder',mapping:'WORK_ORDER'},//显示序号
                                                        	{name:'workPlan',mapping:'WORK_PLAN'},//计划完成
                                                     	    {name:'workExecute',mapping:'WORK_EXECUTE'}//实际完成
                                            
                                                        	]); 
	var monthInfoReaders = new Ext.data.JsonReader({//读取json数据的panel
       	totalProperty:'json.count',
       	root:'json.data'
       	},monthWorkInfoRecords);
	
	var monthInfoStore = new Ext.data.Store(
           	{
           		proxy:new Ext.data.HttpProxy({
           		url:basepath+'/monthInfo-Action.json',
//           		},
           		failure : function(response){
           			var resultArray = Ext.util.JSON.decode(response.status);
           			if(resultArray == 403) {
           				Ext.Msg.alert('提示', response.responseText);
           			}
           		},
           		method:'GET'
           		}),
           		reader:monthInfoReaders
           	}
           	);	
	var monthWorkGrids =  new Ext.grid.EditorGridPanel({//产品列表数据grid
		frame:true,
		region:"center",
		id:'monthWorkGrids',
		height : document.body.clientHeight-80,
		width : document.body.clientWidth-602,

		store:monthInfoStore,
		loadMask:true,

		cm :grids,
		buttonAlign:'center',
		buttons:[{text: '确认',
			     id : 'ok',
			
			handler: function(){}
			
			},'-',{text: '取消',
    			id : 'cancel',
    			handler: function(){}
    			
    			}],
	
      loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }



	});

	var monthWorkPanel = new Ext.form.FormPanel( {

		title : '月工作记录',
		height : 80,
	   width : document.body.clientWidth-602,				
		labelWidth : 120,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'center',
		split : true,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .50,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					name : 'USER_NAME',
					id : 'userNames',
					fieldLabel : '客户经理姓名',
					value:__userName,
					anchor : '100%'
				}]
			},{
				columnWidth : .50,
				layout : 'form',
				items : [ {
					name : 'WORKLOG_DATE',
					id : 'WORKLOG_DATE',
					anchor : '100%',
					xtype : 'datefield',
					editable : false,
					value:new Date(),
					format : 'Y-m',
					fieldLabel : '工作周期'
				}]
			}
			]
		} ]});
	
	
	
	
	
	
/*************************************日历********************************************************/
    Ext.ensible.cal.CalendarMappings = {
        	CalendarId:   {name:'ID', mapping: 'remindBelong', type: 'string'},
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
        	EventId:     {name: 'id', type:'string'},
        	CalendarId:  {name: 'CalID', type: 'string'}, 
        	Title:       {name: 'userName'},
        	StartDate:   {name: 'worklogDate', type: 'date', dateFormat: 'c'},
        	EndDate:     {name: 'worklogDate', type: 'date', dateFormat: 'c'},
        	RRule:       {name: 'RecurRule', mapping: 'recur_rule'},
        	Location:    {name: 'Location', mapping: 'location'},
        	Notes:       {name: 'Desc', mapping: 'SCHEDULE_CONTENT'},
        	Url:         {name: 'LinkUrl', mapping: 'link_url'},
        	IsAllDay:    {name: 'all_day', type: 'boolean'},
        	Reminder:    {name: 'Reminder', mapping: 'reminder'},    
        	RemindType:  {name: 'RemindType', mapping: 'REMIND_TYPE', type: 'string'},
        	relatedCustomer:{name: 'relatedCustomer', mapping:'RELATION_CUST', type:'string'},                
        	CreatedBy:   {name: 'CreatedBy', mapping: 'CREATOR'},
        	IsPrivate:   {name: 'Private', mapping:'private', type:'boolean'}
        };
  
   var dataFields = [
                  	{name:'id',mapping:'ID'},
                   	{name:'worklogType',mapping:'WORKLOG_TYPE'},//周期类型
                   	{name:'userId',mapping:'USER_ID'},//客户经理
                   	{name:'userName',mapping:'USER_NAME'},//客户经理姓名
                   	{name:'orgId',mapping:'ORG_ID'},//客户经理所属机构ID
                   	{name:'orgName',mapping:'ORG_NAME'},//客户经理所属机构名称
                   	{name:'worklogDate',mapping:'WORKLOG_DATE',type : 'date',dateFormat: 'Y-m-d'},//工作周期
                	{name:'createDate',mapping:'CREATE_DATE'}//录入日期
       ];
   Ext.ensible.cal.EventRecord.reconfigure();  
   var eventProxy = new Ext.data.HttpProxy({
  	 api:{
  		 read:{
  			 url:basepath+'/monthWork-Action.json',
  			 method:'GET'
  		 }
  	 }
   });
 
   var eventReader = new Ext.data.JsonReader({
       idProperty: 'id',
       root: 'json.data',
       fields: dataFields
  });
   var eventStore = new Ext.ensible.cal.EventStore({
	   proxy : eventProxy,
	   reader : eventReader
   });
   Ext.ensible.cal.EventRecord.reconfigure();  
	var calPanel = new Ext.ensible.cal.CalendarPanel({
		eventStore : eventStore,
   	    calendarStore : calendarStore,
   	    showDayView : false,
   	    showWeekView : false
   	   // showMonthView : true
	});
	calPanel.on('dayclick',function(a,b,c,d,f){
        Ext.getCmp('ok').setDisabled(false);
        Ext.getCmp('cancel').setDisabled(false);
		return false;
	});
	calPanel.on('eventclick',function(a,b,c,d,f){
        Ext.getCmp('cancel').setDisabled(true);
        Ext.getCmp('ok').setDisabled(true);
	    dailyForm.getForm().loadRecord(b);
	       monthInfoStore.load( {
           	params : {
					'worklogId' : b.data.worklogId
				}
			});
     
		return false;
	});
	calPanel.items.items[1].enableDD = false;
	Ext.each(calPanel.items.items,function(it){
		it.enableDD = false;
		it.enableContextMenus=false;
	});

	var view = new Ext.Viewport({//页面展示
		layout:'border',
		frame:true,
	    items:[
	      {
				region : 'west',
				layout : 'form',
				width :'600',
				items : [calPanel ]
				
			},{
				region : 'center',
				layout : 'form',
				width :'600',
				items : [monthWorkPanel, monthWorkGrids ]
				
			}
			]}
	           
	           
	    );	
    debugger;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	});