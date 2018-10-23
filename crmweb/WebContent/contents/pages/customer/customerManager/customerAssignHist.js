/**
 * 客户分配-调整历史
 * @author songxs
 * @since 2013-1-20
 * 
 */

	
	var custAssignTypeStore = new Ext.data.Store({//法定代表人证件类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_ASSIGN_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var custAssignHistPanel = new  Ext.form.FormPanel({//客户分配历史查询条件PANEL
		
		title : '客户分配历史查询',
		height : 150,
		buttonAlign : 'center',
		labelWidth : 100,//label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,
				 layout:'form',
				 items:[{
					 	name:'CUST_ID',
						xtype:'textfield',
						fieldLabel : '客户编号',
						anchor:'90%'
				 },{
						name:'BEGIN_TIME',
						xtype:'datefield',
						fieldLabel:'调整时间从',
						editable : false,
						format:'Y-m-d',
						anchor:'90%'
					}]
			},{
				columnWidth:.5,
				 layout:'form',
				 items:[{
					 name:'CUST_ASSIGN_TYPE',
					 xtype:'combo',
					 fieldLabel:'操作类型',
					 labelStyle: 'text-align:right;',
					 triggerAction : 'all',
					 store : custAssignTypeStore,
					 displayField : 'value',
					 allowBlank : true,
					 valueField : 'key',
					 mode : 'local',
					 forceSelection : true,
					 typeAhead : true,
					 emptyText:'请选择',
					 resizable : true,
					 hiddenName:'CUST_ASSIGN_TYPE',
					 anchor:'90%'
				 },{
						name:'END_TIME',
						xtype:'datefield',
						fieldLabel:'到',
						editable:false,
						format:'Y-m-d',
						anchor:'90%'
					}]
			}]

		}],
		buttons:[{
			text:'查询',
			handler:function(){
			var start = custAssignHistPanel.getForm().findField('BEGIN_TIME').getValue();
			var end = custAssignHistPanel.getForm().findField('END_TIME').getValue();
			if(start==''&&end !=''){
				Ext.Msg.alert('消息框','请先选择开始时间！');
				return;
			}else if(end !=''&&start>end){
				Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
				custAssignHistPanel.getForm().findField('BEGIN_TIME').reset();
				return;
			}
			var parameters = custAssignHistPanel.getForm().getValues(false);
			histStore.removeAll();
			histStore.baseParams = {
					'condition':Ext.util.JSON.encode(parameters)
				};
			histStore.load({
				params:{
					start:0,
					limit: parseInt(spagesize_combo.getValue())
				}
			});
		}
		},{
			text:'重置',
			handler:function(){
			custAssignHistPanel.getForm().reset();
		}
		}]

	});
	var rownumt = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});	
	
	var histCol = new Ext.grid.ColumnModel([rownumt,
	                                        {header :'ID',dataIndex:'id',sortable : true,hidden:true},
	                                        {header :'操作类型',dataIndex:'custAssignTypeOra',sortable:true,width:100},
	                                        {header :'分配时间',dataIndex:'assignTime',sortable:true,width:120},
	                                        {header :'客户号',dataIndex:'custId',sortable:true,width:100},
	                                        {header :'客户中文名称',dataIndex:'custZhName',sortable:true,width:100},
	                                        {header :'主协办类型',dataIndex:'mainType',sortabel:true,width:100},
	                                        {header :'归属机构名称',dataIndex:'institutionName',sortable:true,width:120},
	                                        {header :'归属客户经理名称',dataIndex:'mgrName',sortable:true,width:120},
	                                        {header :'分配人',dataIndex:'assignUserName',sortable:true,width:100},
	                                        {header :'分配人当前归属机构',dataIndex:'currentInstitutionName',sortable:true,width:150}
	                                        ]);
	var histRecord=  new Ext.data.Record.create([
	                                             {name:'id',mapping:'ID'},
	                                             {name:'custAssignType',mapping:'CUST_ASSIGN_TYPE'},
	                                             {name:'custAssignTypeOra',mapping:'CUST_ASSIGN_TYPE_ORA'},
	                                             {name:'assignTime',mapping:'ASSIGN_TIME'},
	                                             {name:'custId',mapping:'CUST_ID'},
	                                             {name:'custZhName',mapping:'CUST_ZH_NAME'},
	                                             {name:'mainType',mapping:'MAIN_TYPE'},
	                                             {name:'mainType',mapping:'MAIN_TYPE_ORA'},
	                                             {name:'institutionName',mapping:'INSTUTION_NAME'},
	                                             {name:'mgrName',mapping:'MGR_NAME'},
	                                             {name:'assignUserName',mapping:'ASSIGN_USER_NAME'},
	                                             {name:'currentInstitutionName',mapping:'CURRENT_INSTITUTION_NAME'}
	                                             ]);
	var histReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},histRecord);
	
	
	var histStore = new Ext.data.Store({
		
		proxy:new Ext.data.HttpProxy({
		url:basepath+'/custAssignHistQuery-action.json',
		method:'GET'
		}),
		reader:histReader
	});
	var spagesize_combo = new Ext.form.ComboBox({// 每页显示条数下拉选择框
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
	spagesize_combo.on("select", function(comboBox) {	// 改变每页显示条数reload数据
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		histStore.reload({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
	});	
	histStore.load({
		params:{	
		start:0,
		limit: parseInt(spagesize_combo.getValue())
	}});
	
	var sbbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : histStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	
	var histGrid =  new Ext.grid.GridPanel({
		id:'histGrid',
		store:histStore,
		region:'center',
		frame:true,
		loadMask:true,
		cm :histCol,
    	bbar:sbbar,
		stripeRows : true,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
		});
	
 	var customerAssignHistWin = new Ext.Window({
		layout : 'fit',
		width : 800,
		height :460,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		animCollapse : false,
		animateTarget : Ext.getBody(),
		border : false,
		buttonAlign : "center",
		items:[{
				layout:'border',
				items:[custAssignHistPanel,histGrid]
		}]
 		
 	});
	