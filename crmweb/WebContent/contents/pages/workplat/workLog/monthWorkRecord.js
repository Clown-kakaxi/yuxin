Ext.onReady(function() {
	
	var monthStore = new Ext.data.ArrayStore({
	    fields:['myId','displayText'],
	    data:[['1','存款业务'],['2','授信业务'],['3','国际业务'],
	          ['4','其他重点产品销售'],['5','其他工作']]
	});	
	var monthCombo = new Ext.form.ComboBox({id:'monthStore',
		store: monthStore,displayField:'displayText',valueField: 'myId',mode: 'local',
		editable:true, triggerAction: 'all',allowBlank:false,
		listeners : { select : function(a, b) {
			var v = a.value; // 取到valueField中的值
			monthCombo.setValue(v);
		}
	}
    });	
	Ext.util.Format.comboRenderer = function(combo) {
		return function(value) {
			var record = combo.findRecord(combo.valueField, value);
			return record ? record.get(combo.displayField) : combo.valueNotFoundText;
		};
	};
	
	var monthWorkSearchPanel = new Ext.form.FormPanel( {// 查询panel
		title : '月工作记录查询',
		height : 130,
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
			layout : 'column',
			items : [ {
				columnWidth : .50,
				labelWidth : 100,// label的宽度
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					name : 'USER_NAME',
					value : __userName,
					fieldLabel : '客户经理姓名',
					anchor : '100%'
				}, {
					name : 'ADD_START_DATE_FROM',
					id : 'ADD_START_DATE_FROM',
					anchor : '100%',
					xtype : 'datefield',
					editable : false,
					format : 'Y-m',
					fieldLabel : '工作日期从'
				} ]
			}, {
				columnWidth : .50,
				layout : 'form',
				labelWidth : 110,// label的宽度
				items : [ {
					xtype : 'textfield',
					name : 'ORG_ID',
					fieldLabel : '客户经理所在机构',
					anchor : '100%'
				},{
					name : 'ADD_START_DATE_TO',
					id : 'ADD_START_DATE_TO',
					anchor : '100%',
					xtype : 'datefield',
					editable : false,
					format : 'Y-m',
					fieldLabel : '工作日期到'
				}]
		} ],
		buttonAlign : 'center',
		buttons : [{
			text : '查询',
			handler : function() {
				var start = Ext.getCmp('ADD_START_DATE_FROM').getValue();
				var end = Ext.getCmp('ADD_START_DATE_TO').getValue();
				if (start == '' && end != '') {
					Ext.Msg.alert('消息框', '请先选择开始时间！');
					Ext.getCmp('ADD_START_DATE_TO').reset();
					return;
				} else if (end != '' && start > end) {
					Ext.Msg.alert('消息框', '开始时间大于结束时间，请检查！');
					Ext.getCmp('ADD_START_DATE_TO').reset();
					return;
				}
				var parameters = monthWorkSearchPanel.getForm().getValues(false);
				monthWorkInfoStore.removeAll();
				monthWorkInfoStore.baseParams = {
						'condition' : Ext.util.JSON.encode(parameters)
				};
				monthWorkInfoStore.load( {
					params : {
					start : 0,
					limit : parseInt(spagesize_combo.getValue())
				}
				});
			}
		},{
			text : '重置',
			handler : function() {
				monthWorkSearchPanel.getForm().reset();
		}
		} ]
	});
	var rownums = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var grid = new Ext.grid.ColumnModel([rownums,{
		header :'工作记录ID',dataIndex:'mainId',id:"id",width:125,sortable : true,hidden:true},
		{header :'客户经理编号',dataIndex:'userId',width:125,sortable : true,hidden:true},
		{header : '客户经理姓名',dataIndex:'userName',width:150,sortable : true},
		{header :'客户经理所属机构ID',dataIndex:'orgId',width:135,sortable : true,hidden:true},
		{header :'客户经理所属机构名称',dataIndex:'orgName',width:160,sortable : true},
		{header :'工作周期 ',dataIndex:'worklogDate',width:125,sortable : true},
		{header :'录入日期',dataIndex:'createDate',width:125,sortable : true}
		]);
	var monthWorkInfoRecord = new Ext.data.Record.create([ {
		name:'mainId',mapping:'ID'},
		{name:'worklogType',mapping:'WORKLOG_TYPE'},//周期类型
		{name:'userId',mapping:'USER_ID'},//客户经理
		{name:'userName',mapping:'USER_NAME'},//客户经理姓名
		{name:'orgId',mapping:'ORG_ID'},//客户经理所属机构ID
		{name:'orgName',mapping:'ORG_NAME'},//客户经理所属机构名称
		{name:'worklogDate',mapping:'WORKLOG_DATE'},//工作周期
		{name:'createDate',mapping:'CREATE_DATE'}//录入日期
		]);
	
	var monthWorkInfoReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},monthWorkInfoRecord);
	
	var monthWorkInfoStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/monthWork-Action.json',
			failure : function(response){
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		},
		method:'GET'
		}),
		reader:monthWorkInfoReader
	}
	);
	// 每页显示条数下拉选择框
	var spagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});
	
	// 改变每页显示条数reload数据
	spagesize_combo.on("select", function(comboBox) {
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		monthWorkInfoStore.reload({
			params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		}
		});
	});
	// 分页工具栏
	var sbbar = new Ext.PagingToolbar({
		pageSize : parseInt(spagesize_combo.getValue()),
		store : monthWorkInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	monthWorkInfoStore.load({params:{		
		start:0,
		limit: parseInt(spagesize_combo.getValue())
	}});
	
	var rownum1 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});                           		
	
	var numberField = new Ext.form.NumberField({allowBlank : false,minValue:0.01});  
	var textField = new Ext.form.TextField({allowBlank : false,minValue:0});  
	
	var grids = new Ext.grid.ColumnModel([rownum1,{
		header :'月工作记录ID',dataIndex:'id',id:"id",width:100,sortable : true,hidden:true},
		{header :'关联主表ID',dataIndex:'worklogId',width:125,sortable : true,hidden:true},
		{header : '项目类型',dataIndex:'workType',width:125,sortable : false,renderer : Ext.util.Format.comboRenderer(monthCombo)},
		{header :'项目',dataIndex:'work',width:210,sortable : false},
		{header :'显示序号',dataIndex:'workOrder',width:125,sortable : false,hidden:true},
		{header :'计划完成 ',dataIndex:'workPlan',width:145,sortable : false,editor:textField},
		{header :'实际完成',dataIndex:'workExecute',width:145,sortable : false,editor:textField}
		]);
	var monthWorkInfoRecords = new Ext.data.Record.create([{
		name:'workId',mapping:'ID'},//主键ID
		{name:'worklogId',mapping:'WORKLOG_ID'},//关联外键
		{name:'workType',mapping:'WORK_TYPE'},//项目类型
		{name:'work',mapping:'WORK'},//项目存中文
		{name:'workOrder',mapping:'WORK_ORDER'},//显示序号
		{name:'workPlan',mapping:'WORK_PLAN'},//计划完成
		{name:'workExecute',mapping:'WORK_EXECUTE'}//实际完成
		]);                              		
	
	var monthInfoReaders = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},monthWorkInfoRecords);
	
	var monthInfoStore = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/monthInfo-Action.json',
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
		clicksToEdit : 1,
		//height : document.body.clientHeight-90,
		store : monthInfoStore,
		loadMask : true,
		cm : grids,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		buttonAlign:'center',
		buttons:[{text: '保存',
			handler : function() {
			var json1 = {'workId' : []};
			var json2 = {'worklogId' : []};
			var json3 = {'workType' : []};
			var json4 = {'work' : []};
			var json5 = {'workOrder' : []};
			var json6 = {'workPlan' : []};
			var json7 = {'workExecute' : []};
			for ( var i = 0; i < monthInfoStore.getCount(); i++) {
				var temp = monthInfoStore.getAt(i);
				json1.workId.push(temp.data.workId);
				json2.worklogId.push(temp.data.worklogId);
				json3.workType.push(temp.data.workType);
				json4.work.push(temp.data.work);
				json5.workOrder.push(temp.data.workOrder);
				json6.workPlan.push(temp.data.workPlan);
				json7.workExecute.push(temp.data.workExecute);
			}
			var temp1 = Ext.encode(json1).toString();
			var temp2 = Ext.encode(json2).toString();
			var temp3 = Ext.encode(json3).toString();
			var	temp4 = Ext.encode(json4).toString();
			var temp5 = Ext.encode(json5).toString();
			var temp6 = Ext.encode(json6).toString();
			var	temp7 = Ext.encode(json7).toString();
			var mainIdshow = Ext.getCmp('mainId').getValue();
			//var workWeek = Ext.getCmp()
			Ext.Ajax.request( {
				url : basepath + '/MonthWork!monthSave.json',
				method : 'POST',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				params : {
					'workIdshow'	: 	temp1,
					'worklogIdshow' : 	temp2,
					'workTypeshow' 	:	temp3,
					'workshow' 		: 	temp4,
					'workOrdershow'	: 	temp5,
					'workPlanshow' 	: 	temp6,
					'workExecuteshow' :	temp7,
					'mainIdshow'	: mainIdshow
			},
			success : function() {
				Ext.Msg.alert('提示', '操作成功');
				//queryStore.reload();
			},
			failure : function(response) {
				Ext.Msg.alert('提示','操作失败,失败原因:' + response.responseText);
				//queryStore.reload();
			}
			});
		}
		},'-',
		{text: '重置',
			handler: function(){
			monthInfoStore.loadData(data);
		}
		}]
	});	
	
	var monthWorkPanel = new Ext.form.FormPanel( {// 查询panel
		title : '月工作记录',
		height : 90,
		labelWidth : 120,// label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					name : 'mainId',
					id : 'mainId',
					anchor : '100%',
					xtype : 'textfield',
					hidden : false
				},{
					name : 'WORKLOG_DATE',
					id : 'WORKLOG_DATE',
					anchor : '100%',
					xtype : 'datefield',
					editable : false,
					value:new Date(),
					format : 'Y-m',
					fieldLabel : '周期'
				},{
					xtype : 'datefield',
					fieldLabel : '填写日期',
					editable : false,
					name : 'createDateNew',
					id : 'createDateNew',
					labelStyle : 'text-align:right;',
					value : new Date(),
					format : 'Y-m-d',
					anchor : '100%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户经理编号',
					name : 'userId',
					value : __userId,
					labelStyle : 'text-align:right;',
					readOnly : true,
					anchor : '100%'
				},{
					xtype : 'textfield',
					name : 'userName',
					id : 'userNames',
					fieldLabel : '客户经理姓名',
					value:__userName,
					anchor : '100%'
				}]
		} ]});
	var monthWorkGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		frame:true,
		id:'monthWorkGrid',
		//height : document.body.clientHeight-135,
		region:'center',
		stripeRows : true,
		store:monthWorkInfoStore,
		loadMask:true,
		tbar:[{
			text:'添加工作记录',
			iconCls : 'addIconCss',
			handler:function(){      			
			monthInfoStore.loadData(data);
			//monthWorkGrids.startEditing(1, 1);
		}
		},'-',{
			text:'修改工作记录',
			iconCls : 'editIconCss'
		}
		],
		cm :grid,
		bbar:sbbar,
		loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}, 
	listeners:{
		'rowclick':function(a,b,c){	
		record = monthWorkGrid.store.getAt(b);
		//.getForm().loadRecord(record);
		Ext.getCmp('userNames').setValue(record.data.userName);
		var t1 = record.data.worklogDate;
		if(t1 != null && t1 != ''){
			var t2 = t1.format('Y-m');
			Ext.getCmp('WORKLOG_DATE').setValue(t2);}
		else { Ext.getCmp('WORKLOG_DATE').setValue(t1);
		}
		var ma = record.data.mainId;
		debugger;
		monthInfoStore.load( {
			params : {
			'worklogId' : ma
		}
		});
	}
	}
	});	
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		items : [{
			layout:'border',
			frame:true,
			items:[{
				region : 'west',
				layout : 'border',
				width :'500',
				items : [monthWorkSearchPanel, monthWorkGrid ]
			},{
				region : 'center',
				layout : 'border',
				//width :'600',
				items : [monthWorkPanel, monthWorkGrids ]
			}
			]}
		]}
	);
	
	var data={json: {data:[{
		WORK_TYPE:"1","WORK":"（一）一般性存款余额 "},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"其中：对公存款余额"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"其中：储蓄存款余额"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"（二）一般性存款日均"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"其中：对公存款日均"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"其中：储蓄存款日均"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"（三）活期结算存款余额"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"1","WORK":"（四）活期结算存款日均"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"2","WORK":"（一）新开发授信客户"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"2","WORK":"（二）贷款余额"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"2","WORK":"（三）承兑余额"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"2","WORK":"（四）贴现余额"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"3","WORK":"（一）累计实现结算量"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"3","WORK":"（二）累计结售汇量"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"4","WORK":"其他重点产品销售"},//,"WORK_PLAN":"","WORK_EXECUTE":""
		{WORK_TYPE:"5","WORK":"其他工作"}//,"WORK_PLAN":"","WORK_EXECUTE":""
		]}};
});