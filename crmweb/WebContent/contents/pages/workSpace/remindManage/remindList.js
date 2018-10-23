/*
 * 信息提醒列表
 * 2011-06-09
 * 姚亮
 * */

Ext.onReady(function(){
	
	var msgDetail = new Ext.Window({
		height:350,
		width:600,
		layout:'fit',
		closeAction:'hide',
		buttonAlign:'center',		
		items:new Ext.form.FormPanel({
			frame:true,
			labelWidth:70,
			style:'padding: 10 10 10 10',
			title:'消息信息',
			buttonAlign:'center',
			items:[
			{
				xtype:'textfield',
				fieldLabel:'消息标题',
				value:'王总生日提醒',
				anchor:'60%'
			},
			
			{
				xtype:'textfield',
				fieldLabel:'消息类型',
				value:'生日提醒',
				anchor:'60%'
			},
			
			{
				id:'productEndToDate',
				name:'productEndToDate',
				//anchor:'78%',
				xtype:'datefield',
				format:'Y-m-d',
				anchor:'60%',
				value:'2011-06-08',
				fieldLabel:'提醒日期'
			},
			
			{
				id:'memo',
				name:'memo',
				fieldLabel:'消息内容',
				xtype:'textarea',
				anchor:'90%',
				value:'王总生日提醒,北京柏宏房地产开发有限公司,总经理 王总,生日:2011-06-15,快到了.'
			}
			
			],
			buttons:[
			{
				text:'关  闭',
				handler:function()
				{
					msgDetail.hide();
				}
			}
			]
		})
	
	});
	
	
	var msgDetailValueChange = new Ext.Window({
		height:350,
		width:600,
		layout:'fit',
		closeAction:'hide',
		buttonAlign:'center',		
		items:new Ext.form.FormPanel({
			frame:true,
			labelWidth:70,
			style:'padding: 10 10 10 10',
			title:'消息信息',
			buttonAlign:'center',
			items:[
			{
				xtype:'textfield',
				fieldLabel:'消息标题',
				value:'武汉钢铁大额变动提醒',
				anchor:'60%'
			},
			{
				xtype:'textfield',
				fieldLabel:'消息类型',
				value:'大额变动提醒',
				anchor:'60%'
			},
			{
				id:'productEndToDateChange',
				name:'productEndToDateChange',
				//anchor:'78%',
				xtype:'datefield',
				format:'Y-m-d',
				anchor:'60%',
				value:'2011-06-08',
				fieldLabel:'提醒日期'
			},
			{
				id:'memoChange',
				name:'memoChange',
				fieldLabel:'消息内容',
				xtype:'textarea',
				anchor:'90%',
				value:'武汉钢铁大额变动提醒,2011-06-08 11:50 分,武汉钢铁公司,存款账号: 0101010101, 资金增加额度 10,000（万元）.'
			}
			],
			buttons:[
			{
				text:'关  闭',
				handler:function()
				{
					msgDetailValueChange.hide();
				}
			}
			]
		})
	
	});	
	
	
	var msgDetailValueProduct = new Ext.Window({
		height:350,
		width:600,
		layout:'fit',
		closeAction:'hide',
		buttonAlign:'center',		
		items:new Ext.form.FormPanel({
			frame:true,
			labelWidth:70,
			style:'padding: 10 10 10 10',
			title:'消息信息',
			buttonAlign:'center',
			items:[
			{
				xtype:'textfield',
				fieldLabel:'消息标题',
				value:'节能减排产品到期提醒',
				anchor:'60%'
			},
			{
				xtype:'textfield',
				fieldLabel:'消息类型',
				value:'产品到期提醒',
				anchor:'60%'
			},
			{
				id:'productEndToDateProduct',
				name:'productEndToDateProduct',
				//anchor:'78%',
				xtype:'datefield',
				format:'Y-m-d',
				anchor:'60%',
				value:'2011-06-08',
				fieldLabel:'提醒日期'
			},
			{
				id:'memoProduct',
				name:'memoProduct',
				fieldLabel:'消息内容',
				xtype:'textarea',
				anchor:'90%',
				value:'产品到期提醒:中小企业事业部节能减排产品将于2011-06-15日到期'
			}
			],
			buttons:[
			{
				text:'关  闭',
				handler:function()
				{
					msgDetailValueProduct.hide();
				}
			}
			]
		})
	
	});
	
	/***********网页上部**查询信息框******************************************/
	var msgSearchPanel = new Ext.form.FormPanel({//查询panel
		title:'查询信息',
		height:110,
		labelWidth:120,//label的宽度
		frame:true,
		region:'north',
		split:true,
		buttonAlign:'center',
		items:[
		{
			layout:'column',
			items:[
			{
			 columnWidth:.25,
			 layout:'form',
			 items:[
			 	{
					columnWidth:.25,
					xtype:'textfield',
					id:'msgTitle',
					name:'msgTitle',
					fieldLabel:'信息标题',
					anchor:'90%'
				}
				]
			 },
			 {
			 columnWidth:.25,
			 layout:'form',
			 items:[

				{
					columnWidth:.25,
					xtype:'combo',
					id:'msgType',
					name:'msgType',
					triggerAction:'all',
					anchor:'90%',
					fieldLabel:'信息类型',
					mode:'local',
					store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '大额变动提醒'],[2,'生日提醒'],[3,'产品到期提醒']]
			 	   }),
			       valueField:'myId',
			       displayField:'displayText'

				}

				]			 	
			 },
			 {
			 columnWidth:.25,
			 layout:'form',
			 items:[
				{
					columnWidth:.25,
					xtype:'combo',
					id:'msgStatus',
					name:'msgStatus',
					triggerAction:'all',
					anchor:'90%',
				//	lazyRender:true,
					fieldLabel:'信息状态',
					mode:'local',
					store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '未阅读'],[2,''],[3,'已阅读']]
			 	   }),
			       valueField:'myId',
			       displayField:'displayText'
				}
				]			 	
			 }
			]
		}
		],
		buttons:[
		{
			text:'查询',
			handler:function()
			{
				msgStore.load();
				debugger;
			}
		}
		]
		
		
	});

//************数据源store配置*****************************************************************
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var msgColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	 new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		}),sm,
	{dataIndex:'remindId',sortable : true,hidden:true},
	{header:'消息状态',dataIndex:'isRead',sortable : true,width:90},
	{header:'消息标题',dataIndex:'remindTitle',sortable : true,width:150},
	{header:'消息类别',dataIndex:'remindType',sortable : true,width:100},
	{header:'消息日期',dataIndex:'statisticsDate',sortable : true,width:100},
	{header:'消息内容',dataIndex:'remindContent',id:'memo'}
	]);
	
	var msgRecord = new Ext.data.Record.create([
	    {name:'remindId', mapping: 'remindId'},
		{name:'isRead', mapping:'isRead'},
		{name:'remindTitle',mapping:'remindTitle'},
		{name:'remindType',mapping:'remindType'},
		{name:'statisticsDate',mapping:'statisticsDate'},
		{name:'remindContent',mapping:'remindContent'}
		]);

	
	var msgReader = new Ext.data.JsonReader({//读取json数据的panel
	totalProperty:'num'
	},msgRecord);
	
	var proxyIndex = new Ext.data.HttpProxy({
	    url: basepath+'/workplatremind.json'
	});
	
	var msgStore = new Ext.data.Store(
	{
		restful:true,
		proxy: proxyIndex,
		reader:msgReader
	}
	);
	
	msgStore.load({callback:function(){
	    debugger;
	}});
//*****************查看详细信息form******************************************************
	var addInfoForm = new Ext.form.FormPanel({
		labelAlign : 'left',
		buttonAlign : 'center',
		height : 280,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [{
			layout : 'column',
			items : [{
				columnWidth : .6,
				layout : 'form',
				labelWidth : 60, // 标签宽度
				defaultType : 'textfield',
				border : false,
				layout : 'form',
				items : [{
					fieldLabel : '消息标题',
					labelStyle: 'text-align:center;',
				    name : 'remindTitle',
					xtype : 'textfield',
					anchor : '90%'
				}
				]
			},{
				columnWidth : .6,
				layout : 'form',
				labelWidth : 60, // 标签宽度
				defaultType : 'textfield',
				border : false,
				layout : 'form',
				items :[{
					fieldLabel : '消息状态',
					labelStyle: 'text-align:center;',
					name : 'isRead',
					xtype : 'textfield',
					anchor : '90%'
				}]},{
					columnWidth : .6,
					layout : 'form',
					labelWidth : 60, // 标签宽度
					defaultType : 'textfield',
					border : false,
					layout : 'form',
					items :[{
					fieldLabel : '消息类别',
					labelStyle: 'text-align:center;',
				    name : 'remindType',
					xtype : 'textfield',
					anchor : '90%'
					}]
				},{
					columnWidth : .6,
					layout : 'form',
					labelWidth : 60, // 标签宽度
					//labelAlign : 'right', // 标签对齐方式
					//defaultType : 'textfield',
					border : false,
					items :[{
					fieldLabel : '消息日期',
					labelStyle: 'text-align:center;',
					name : 'statisticsDate',
					xtype : 'datefield',
					anchor : '90%'
					}]
				},{
					columnWidth : 1.0,
					layout:'form',
					labelAlign : 'left', // 标签对齐方式
					items : {
					fieldLabel : '消息内容',
					labelStyle: 'text-align:right;',
					height:'150',
					name : 'remindContent',
					xtype : 'textarea',
					anchor : '90%'
					}
				}
				]
		}]
	});
//**************查看详细信息window*********************************************************
	var addInfoWindow = new Ext.Window({
		title : '新增资讯',
		plain : true,
		layout : 'fit',
		width : 800,
		height : 350,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		items : [addInfoForm],
		buttons : [{
			text : '关   闭',
			handler : function() {
				addInfoWindow.hide();
			}
		}]
		});
	
//***********用于显示详细信息的funtion方法***************************************************
	function updateInit(){
		 var _record = msgInfoGrid.getSelectionModel().getSelected();
	        if (!_record) {
	        	Ext.MessageBox.alert('查看详细信息', '请选择要操作的一列！');
	        } else {
	          var record = msgInfoGrid.getSelectionModel().getSelected();
	          addInfoForm.getForm().loadRecord(record);
	          addInfoWindow.show();
	        }
	}
	
//***********用于修改已读字段的方法*******************************************************
	function altInit(){
		//var cbid= Ext.getCmp('cbid').getValue();
		var checkno = msgInfoGrid.getSelectionModel().selections.items;
		var _record = msgInfoGrid.getSelectionModel().getSelected();
		debugger;
		var json = {'remindId':[]};
		
	    if (!_record) {
	    	Ext.MessageBox.alert('设置成已读', '请选择要操作的一列！');
	    } else {
	    	for(var i=0;i<checkno.length;i++)
			{
	    		var id = checkno[i].data.remindId;
	    	Ext.Ajax.request({
				url:basepath+'/workplatremind/'+id+'.json',
				method:'PUT'
			});
			}
	    }
	}
	
//*************用于主体显示的grid**********************************************************
	var msgInfoGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		
		id:'信息列表',
		frame:true,
		title:'提醒信息列表',
		id:'msgInfoListGrid',
		autoExpandColumn:'memo',
		sm:sm,
		store:msgStore,
		listeners:{
			rowdblclick:function()
			{
				updateInit();
				setTimeout(function(){msgStore.load();},500);
			}
		},
      	tbar:[ 
       	{
      		text:'查看详细信息',
      		iconCls:'page_edit_1Icon',
      		handler:function()
      		{
      			updateInit();
				setTimeout(function(){msgStore.load();},500);
      		}
      	},'-',
       	{
      		text:'设置成已读',
      		iconCls:'page_edit_1Icon',
      		handler:function()
      		{
      			altInit();
      			setTimeout(function(){msgStore.load();},500);
			}
      	
      	}
      	],
      	
		cm :msgColumns,
//		autoExpandColumn:'productName',
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		
		bbar:
			{	
				xtype:'paging',
				pageSize : 10,
				store : msgStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', {xtype:'textfield',value:'10'} ]
			}	
	});
	
	
	
	var view = new Ext.Viewport({//页面展示
		layout:'border',
		items:[
					msgSearchPanel,
					{
						region:'center',
						layout:'fit',
						
						items:[msgInfoGrid]
					}
			]

	});	

});