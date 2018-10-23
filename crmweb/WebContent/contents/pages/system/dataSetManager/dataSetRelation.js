/**
 * @constructor 数据集关联关系管理
 * @author songxs
 * @since 2012-12-05
 * 
 */

Ext.onReady(function(){
	Ext.QuickTips.init(); 

	var joinLeftStore = new Ext.data.Store({
	
		restful:true,
		autoLoad:true,
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/queryJoinLeftTable.json?type=1'
		}),
		reader:new Ext.data.JsonReader({
			root:'json.data'
		},['VALUE','NAME'])
		
	});
	var joinLeftStore1 = new Ext.data.Store({
		
		restful:true,
		autoLoad:true,
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/queryJoinLeftTable.json?type=2'
		}),
		reader:new Ext.data.JsonReader({
			root:'json.data'
		},['VALUE','NAME'])
		
	});
	var joinLeftColStore = new Ext.data.Store({
		restful:true,
		autoLoad:false,
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/queryJoinLeftCol.json'
		}),
		reader:new Ext.data.JsonReader({
			root:'json.data'
		},['VALUE','NAME'])
		
	});	
	var joinLeftColStore1 = new Ext.data.Store({
		restful:true,
		autoLoad:false,
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/queryJoinLeftCol.json'
		}),
		reader:new Ext.data.JsonReader({
			root:'json.data'
		},['VALUE','NAME'])
		
	});
	
	var ssColLeftStore =  new Ext.data.ArrayStore({
		    fields:['key','value'],
		    data:[['inner','inner'],['left','left'],['right','right']]
		});
		
		
	var dataSetRelationPanel = new  Ext.form.FormPanel({//数据集关联关系查询条件PANEL
		
		title : '数据集关联关系查询',
		height : 120,
		buttonAlign : 'center',
		labelWidth : 100,//label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		items : [{
			layout:'column',
			items:[{
				 columnWidth:.33,
				 layout:'form',
				 items:[{
					 	name:'JOIN_LEFT_TABLE',
						xtype:'textfield',
						fieldLabel : '左表表名',
						anchor:'90%'
				 }]
			},{
				 columnWidth:.33,
				layout:'form',
				items:[{
					name:'JOIN_RIGHT_TABLE',
					xtype:'textfield',
					fieldLabel : '右表表名',
					anchor:'90%'
				}]
			}]
		}],
		buttons:[{
			text:'查询',
			handler:function(){
			var parameters = dataSetRelationPanel.getForm().getValues(false);
			dataSetStore.removeAll();
			dataSetStore.baseParams = {
					'condition':Ext.util.JSON.encode(parameters)
				};
			dataSetStore.load({
				params:{
					start:0,
					limit: parseInt(spagesize_combo.getValue())
				}
			});
			
		}
		},{
			text:'重置',
			handler:function(){
			dataSetRelationPanel.getForm().reset();
		}
		}
		]

	});
	
	var sm = new Ext.grid.CheckboxSelectionModel(); //复选框

	var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var dataSetColumns = new Ext.grid.ColumnModel([rownum,sm,
	                                              {header :'ID',dataIndex:'id',sortable : true,hidden:true},
	                                              {header :'关联左表表名',dataIndex:'joinLeftTable',sortable : true,width:145,hidden:true},
	                                              {header :'左表备注',dataIndex:'joinLeftTableName',sortable : true,width:145},
	                                              {header :'关联左表表名',dataIndex:'joinLeftName',sortable : true,width:145},
	                                              {header :'关联右表表名',dataIndex:'joinRightTable',sortbale : true,width:145,hidden:true},
	                                              {header :'右表备注',dataIndex:'joinRightTableName',sortable : true,width:145},
	                                              {header :'关联右表表名',dataIndex:'joinRightName',sortable : true,width:145},
	                                              {header :'关联方式',dataIndex:'ssColLeft',sortable : true,width:140},
	                                              {header :'左表别名',dataIndex:'joinLeftAlias',sortable : true,width:140},
//	                                              {header :'右表别名',dataIndex:'joinRightAlias',soratable : true,width:140},
	                                              {header :'左表关联字段',dataIndex:'joinLeftCol',sortable : true,width:145,hidden:true},
	                                              {header :'左表关联字段备注',dataIndex:'joinLeftColRemark',sortable : true,width:145},
	                                              {header :'左表关联字段',dataIndex:'joinLeftColName',sortable : true,width:145},
	                                              {header :'右表关联字段',dataIndex:'joinRightCol',sortable : true,width:145,hidden:true},
	                                              {header :'右表关联字段备注',dataIndex:'joinRightColRemark',sortable : true,width:145},
	                                              {header :'右表关联字段',dataIndex:'joinRightColName',sortable : true,width:145}
	                                              ]);
	
	var dataSetRecord=  new Ext.data.Record.create([
	                                                  {name:'id',mapping:'ID'},
	                                                  {name:'joinLeftTable',mapping:'JOIN_LEFT_TABLE'},
	                                                  {name:'joinRightTable',mapping:'JOIN_RIGHT_TABLE'},
	                                                  {name:'joinLeftName',mapping:'JOIN_LEFT_NAME'},
	                                                  {name:'joinRightName',mapping:'JOIN_RIGHT_NAME'},
	                                                  {name:'joinLeftTableName',mapping:'JOIN_LEFT_TABLE_NAME'},
	                                                  {name:'joinRightTableName',mapping:'JOIN_RIGHT_TABLE_NAME'},
	                                                  {name:'ssColLeft',mapping:'SS_COL_LEFT'},
	                                                  {name:'joinLeftAlias',mapping:'JOIN_LEFT_ALIAS'},
	                                                  {name:'joinRightAlias',mapping:'JOIN_RIGHT_ALIAS'},
	                                                  {name:'joinLeftCol',mapping:'JOIN_LEFT_COL'},
	                                                  {name:'joinRightCol',mapping:'JOIN_RIGHT_COL'},
	                                                  {name:'joinLeftColName',mapping:'JOIN_LEFT_COL_NAME'},
	                                                  {name:'joinRightColName',mapping:'JOIN_RIGHT_COL_NAME'},
	                                                  {name:'joinLeftColRemark',mapping:'JOIN_LEFT_COL_REMARK'},
	                                                  {name:'joinRightColRemark',mapping:'JOIN_RIGHT_COL_REMARK'}
	                                                  ]);
	
	var dataSetReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},dataSetRecord);
	
	var dataSetStore = new Ext.data.Store({
		
		proxy:new Ext.data.HttpProxy({
		url:basepath+'/setDateRelationQuery-action.json',
		method:'GET'
		}),
		reader:dataSetReader
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
		dataSetStore.reload({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
	});	
	
	dataSetStore.load({params:{		
		start:0,
		limit: parseInt(spagesize_combo.getValue())
	}});
	
	var sbbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : dataSetStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	
	var dataSetAddPanel = new Ext.form.FormPanel({
		
		id : 'dataSetPanel',
	    frame:true,
        bodyStyle:'padding:5px 5px 0',
      	autoScroll : true,
		split:true,
		buttonAlign : 'center',
		items:[{
			layout : 'column',
			items:[{
				columnWidth : .5,
				layout : 'form',
				items:[{
					xtype:'textfield',
					name:'id',
					fieldLabel:'主键',
					hidden:true,
					anchor:'90%'
				},{
					id:'joinLeftTables',
					xtype : 'combo', 
					resizable : true,
					name : 'joinLeftName',
					store : joinLeftStore,
					hiddenName : 'joinLeftTable',
					fieldLabel : '关联左表',
					valueField : 'VALUE',
					displayField : 'NAME',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					editable:true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					allowBlank:false,
					anchor : '90%',
				    listeners : {
							select : function() {
								Ext.getCmp('joinLeftCols').clearValue();
								var id = Ext.getCmp('joinLeftTables').getValue();
								Ext.apply(joinLeftColStore.baseParams,{joinLeftCols:id});
								joinLeftColStore.reload();
							}
						}
				},{
					id:'joinLeftCols',
					xtype : 'combo', 
					resizable : true,
					name : 'joinLeftColName',
					store : joinLeftColStore,
					hiddenName : 'joinLeftCol',
					fieldLabel : '左表关联字段',
					valueField : 'VALUE',
					displayField : 'NAME',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					editable:true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					allowBlank:false,
					anchor : '90%'
				},{
					id:'joinLeftAliasId',
					xtype:'textfield',
					name:'joinLeftAlias',
					fieldLabel:'左表别名',
					readOnly:true,
					value:'custinfo',
					anchor:'90%'
				},{
					id:'ssColLeftId',
					xtype:'textfield',
					name:'ssColLeft',
					fieldLabel:'关联方式',
					readOnly:true,
					value:'left',
					anchor:'90%'
				}/*,{
					xtype : 'combo', 
					resizable : true,
					name : 'ssColLeft',
					store : ssColLeftStore,
					hiddenName : 'ssColLeft',
					fieldLabel : '关联方式',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					editable:true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					allowBlank:false,
					anchor : '90%'				
				}*/]
			},{
				columnWidth : .5,
				layout : 'form',
				items:[
					{	id:'joinRightTables',
						xtype : 'combo', 
						resizable : true,
						name : 'joinRightName',
						store : joinLeftStore1,
						hiddenName : 'joinRightTable',
						fieldLabel : '关联右表',
						valueField : 'VALUE',
						displayField : 'NAME',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						editable:true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						allowBlank:false,
						anchor : '90%',
						listeners : {
							select : function() {
							Ext.getCmp('joinRightCols').clearValue();
							var id = Ext.getCmp('joinRightTables').getValue();
							Ext.apply(joinLeftColStore1.baseParams,{joinLeftCols:id});
							joinLeftColStore1.reload();
					}
					}
					},{	
						id:'joinRightCols',
						xtype : 'combo', 
						resizable : true,
						name : 'joinRightColName',
						store : joinLeftColStore1,
						hiddenName : 'joinRightCol',
						fieldLabel : '右表关联字段',
						valueField : 'VALUE',
						displayField : 'NAME',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						editable:true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						allowBlak:false,
						anchor : '90%'
					}/*,{
						xtype:'textfield',
						name:'joinRightAlias',
						fieldLabel:'右表别名',
						anchor:'90%'
					}*/]
			}]
		}],
		buttons:[{
			text:'保存',
			handler:function(){
			if(!dataSetAddPanel.getForm().isValid()){ //输入项检查
				Ext.MessageBox.alert('提示','输入有误或存在漏输项,请检查！');
				return false;
			}
			Ext.Ajax.request( {
				url : basepath + '/dataSetRelation-action.json',
				method : 'POST',
				params : dataSetAddPanel.getForm().getFieldValues(),
				success : checkResult,
				failure: checkResult
			});
			function checkResult(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
					Ext.Msg.alert('提示', '操作成功');
					dataSetAddPanel.getForm().reset();
					dataSetAddWindow.hide();
					dataSetStore.reload({
						params:{
							start:0,
							limit: parseInt(spagesize_combo.getValue())
						}
					});
				} else {
					if(resultArray == 403){
						Ext.Msg.alert('提示', response.responseText);
					}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
					}
				};
			}
		}},'-',{
			text:'重置',
			handler:function(){
			dataSetAddPanel.getForm().reset();
			dataSetAddPanel.getForm().findField('ssColLeft').setValue('left');
			dataSetAddPanel.getForm().findField('joinLeftAlias').setValue('custinfo');
		}
		}
		]
	});

	var dataSetAddWindow = new Ext.Window({
		plain : true,
		layout : 'fit',
		width : 600,
		height : 200,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		buttonAlign : 'right',
		border : false,
		items : [ dataSetAddPanel ]
	});
	
	
	var dataSetGrid =  new Ext.grid.GridPanel({//日程查询列表数据grid
		
		id:'数据集关联关系列表',
		id:'dataSetGrid',
		store:dataSetStore,
		region:'center',
		frame:true,
		height:document.body.scrollHeight-160,
		loadMask:true,
		cm :dataSetColumns,
		sm:sm,
    	bbar:sbbar,
		stripeRows : true,
		tbar : [{
			text:'新增',
			iconCls:'addIconCss',
			handler: function(){
			dataSetAddPanel.getForm().reset();
			dataSetAddPanel.getForm().findField('ssColLeft').setValue('left');
			dataSetAddPanel.getForm().findField('joinLeftAlias').setValue('custinfo');
			dataSetAddWindow.show();
        	dataSetAddWindow.setTitle('数据集关联关系新增');

        }
        },'-',{
        	text:'修改',
        	iconCls:'editIconCss',
        	handler: function(){

			var selectLength = dataSetGrid.getSelectionModel().getSelections().length;

			var selectRe = dataSetGrid.getSelectionModel().getSelections()[0];

			if (selectLength != 1) {
				Ext.Msg.alert('提示','请选择一条记录!');
			} else {
				dataSetAddPanel.getForm().loadRecord(selectRe);
	        	dataSetAddWindow.show();
	        	dataSetAddWindow.setTitle('数据集关联关系修改');
			}
        }
        },'-',{
        	text:'删除',
        	iconCls:'deleteIconCss',
        	handler : function(){
			var selectLength = dataSetGrid.getSelectionModel().getSelections().length;
			if (selectLength < 1) {
				Ext.Msg.alert('提示','请选择需要删除的记录!');
			}										else {
				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
							return;
						}      
					var selectRe;
					var tempId;
					var idStr = '';
					for ( var i = 0; i < selectLength; i++) {
						selectRe = dataSetGrid.getSelectionModel().getSelections()[i];
						tempId = selectRe.data.id;
						idStr += tempId;
						if (i != selectLength - 1)
							idStr += ',';
					}
					Ext.Ajax.request({
								url : basepath
										+ '/dataSetRelation-action!batchDestroy.json?idStr='
										+ idStr,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									dataSetStore.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
									window.location = basepath + '/403.jsp';
									} else {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
									store.reload();
								}
							});

				})
				;
			}
        }
        }],
        loadMask : {
        	msg : '正在加载表格数据,请稍等...'
        }
      
	});
	
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		items : [{
		layout:'border',
		items:[dataSetRelationPanel,dataSetGrid]
		}]
	});	

});