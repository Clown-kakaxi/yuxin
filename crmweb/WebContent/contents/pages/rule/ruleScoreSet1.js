/**
 * 指标折算规则设置（带公式的）
 * 
 * 
 */

Ext.onReady(function() {

	
	var store = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFSysRuleScoreSet.json',
		    	method:'get'
		  }),
		  reader: new Ext.data.JsonReader({
			  successProperty : 'success',
			 idProperty : 'ID',
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'id', mapping: 'ID'},
		      {name: 'useWay', mapping: 'USE_WAY'},
			  {name:'USE_WAY_ORA'},
		      {name: 'scoreName', mapping: 'SCORE_NAME'},
		      {name: 'indexCode', mapping: 'INDEX_CODE'},
			  {name: 'indexName', mapping: 'INDEX_NAME'},
			  {name: 'custType', mapping: 'CUST_TYPE'},
			  {name:'CUST_TYPE_ORA'},
		      {name: 'status', mapping: 'STATUS'},
			  {name:'STATUS_ORA'},
		      {name: 'computeType', mapping: 'COMPUTE_TYPE'},
			  {name:'COMPUTE_TYPE_ORA'},
		      {name: 'convertRate', mapping: 'CONVERT_RATE'},
		      {name: 'frequence', mapping: 'FREQUENCE'},
			  {name:'FREQUENCE_ORA'},
		      {name: 'orgId', mapping: 'ORG_ID'},
		      {name: 'userId', mapping: 'USER_ID'},
		      {name: 'setDate', mapping: 'SET_DATE'},
		      {name: 'orgName',mapping: 'ORG_NAME'},
		      {name: 'userName', mapping: 'USER_NAME'},
		      {name: 'formula', mapping: 'FORMULA'},
		      {name: 'formulaMean', mapping: 'FORMULA_MEAN'}
		      ])
	  });
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	
	
	pagesize_combo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		  store.reload({
			  params : {
			  start : 0,
			  limit : parseInt(pagesize_combo.getValue())
		  }
		  });
	  });
	
	 var sm = new Ext.grid.CheckboxSelectionModel();
	  var rownum = new Ext.grid.RowNumberer({
		  header : 'No.',
		  width : 28
	  });
	  
	  var cm = new Ext.grid.ColumnModel([rownum,sm,	// 定义列模型
	                                     {header : '编号', dataIndex : 'id',sortable : true,width : 120,hidden : true}, 
	                                     {header : '指标号', dataIndex : 'indexCode',sortable : true,width : 120,hidden : true}, 
	                                     {header : '指标名称', dataIndex : 'indexName',sortable : true,width : 120},
	                                     {header : '折算规则名称', dataIndex : 'scoreName',sortable : true,width : 120},
	                                     {header : '用途', dataIndex : 'USE_WAY_ORA',sortable : true,width : 120},
	                                     {header : '客户类型', dataIndex : 'CUST_TYPE_ORA',sortable : true,width : 120},
	                                     {header : '是否启用', dataIndex : 'STATUS_ORA',sortable : true,width : 120},
	                                     {header : '计算方式', dataIndex : 'COMPUTE_TYPE_ORA',sortable : true,width : 120},
	                                     {header : '折算率', dataIndex : 'convertRate',sortable : true,width : 120},
	                                     {header : '计算频率', dataIndex : 'FREQUENCE_ORA',sortable : true,width : 120},
	                                     {header : '设置人', dataIndex : 'userName',sortable : true,width : 120},
	                                     {header : '设置机构', dataIndex : 'orgName',sortable : true,width : 120},
	                                     {header : '设置日期', dataIndex : 'setDate',sortable : true,width : 120},
	                                     {header : '设置人', dataIndex : 'userId',sortable : true,width : 120,hidden : true},
	                                     {header : '设置机构', dataIndex : 'orgId',sortable : true,width : 120,hidden : true},
	                                     {header : '用途', dataIndex : 'useWay',sortable : true,width : 120,hidden : true},
	                                     {header : '客户类型', dataIndex : 'custType',sortable : true,width : 120,hidden : true},
	                                     {header : '是否启用', dataIndex : 'status',sortable : true,width : 120,hidden : true},
	                                     {header : '计算方式', dataIndex : 'computeType',sortable : true,width : 120,hidden : true},
	                                     {header : '计算频率', dataIndex : 'frequence',sortable : true,width : 120,hidden : true},
	                                     {header : '公示', dataIndex : 'formula',sortable : true,width : 120,hidden : true},
	                                     {header : '公示解释', dataIndex : 'formulaMean',sortable : true,width : 120,hidden : true}
	                                     ]);
	 var number = parseInt(pagesize_combo.getValue());
	
	var bbar = new Ext.PagingToolbar({// 分页工具栏
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', pagesize_combo]
		});
var listPanel = new Ext.grid.GridPanel({
//	title:"规则列表",
	      layout:'fit',
		  frame : true,
		  autoScroll : true,
		  region : 'center', // 返回给页面的div
		  store: store,
		  stripeRows : true, // 斑马线
		  sm:sm,
		  cm : cm,
		  tbar:[{
				text :'新增',
				tooltip: '新增',
				iconCls:'addIconCss',
				handler :function(){
					ruleSetWindow.setTitle('折算规则新增');
					ruleSetWindow.show();
					ruleSetForm.getForm().reset();
					ruleSetForm.getForm().findField('status').setValue("1");
					ruleSetPanel.buttons[0].setDisabled(false);
					document.getElementById('useType').value="";
					ruleSetForm.getForm().findField("convertRate").hide();
					ruleSetForm.getForm().findField("convertRate").setDisabled(true);
					ifIndex = "";
				}
		  },{
			  text :'修改',
				iconCls:'editIconCss',
				handler :function(){
					var selectLength = listPanel.getSelectionModel().getSelections().length;
					var selectRe = listPanel.getSelectionModel().getSelections()[0];
					if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
					} else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
					}
					
					ruleSetWindow.show();
					ruleSetForm.getForm().loadRecord(selectRe);
					ruleSetWindow.setTitle('折算规则');
					var type = selectRe.data.computeType;
					if (type == "1") {//直接折算
						ruleSetForm.getForm().findField("convertRate").show();
						ruleSetForm.getForm().findField("convertRate").setDisabled(false);
						
					} else {
						ruleSetForm.getForm().findField("convertRate").hide();
						ruleSetForm.getForm().findField("convertRate").setDisabled(true);
						getIfIndex(selectRe.data.formula);
					}
					ruleSetPanel.buttons[0].setDisabled(false);
					document.getElementById('useType').value=selectRe.data.useWay;
					
				}
		  },{
	          text : '删除',
	          iconCls:'deleteIconCss',
	          handler:function() {
	        	  var checkedNodes = listPanel.getSelectionModel().selections.items;
	      		var selectLength = listPanel.getSelectionModel().getSelections().length;
	      		var selectRe;
	      		var tempId;
	      		var idStr = '';
	      		if (selectLength < 1) {
	      			Ext.Msg.alert('提示', '请选择要删除的记录！');
	      		} else {
	      				var json = {'id' : []};
	      				for (var i = 0; i < checkedNodes.length; i++) {
	      					if(checkedNodes[i].data.status=='1'){
	      						Ext.Msg.alert('系统提示', '不能删除启用状态的折算规则！');
	      						return false;
	      					}
	      					json.id.push(checkedNodes[i].data.id);
	      				}
	      				Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
	      					if (buttonId.toLowerCase() == "no") {
	      						return;
	      					}
	      					Ext.Ajax.request({
	      								url : basepath + '/ocrmFSysRuleScoreSet!batchDel.json',
	      								method : 'POST',
	      								params : {
	      									ids : Ext.encode(json)
	      								},
	      								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	      								success : function() {
	      									Ext.Msg.alert('提示', '操作成功！');
	      									store.reload();
	      								},
	      								failure : function(response) {
	      									var resultArray = Ext.util.JSON.decode(response.status);
	      									if (resultArray == 403) {
	      										Ext.Msg.alert('提示',
	      												response.responseText);
	      									} else {
	      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
	      									}
	      									store.reload();
	      								}
	      							});

	      				});
	      		}
	        	  
	          }
			  
		  },{
			  text : '查看',
				iconCls : 'detailIconCss',
				handler : function() {
					var selectLength = listPanel.getSelectionModel().getSelections().length;
					var selectRe = listPanel.getSelectionModel().getSelections()[0];
					if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
					} else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
					}
					ruleSetWindow.show();
					ruleSetForm.getForm().loadRecord(selectRe);
					var type = selectRe.data.computeType;
					if (type == "1") {//直接折算
						ruleSetForm.getForm().findField("convertRate").show();
						ruleSetForm.getForm().findField("convertRate").setDisabled(false);
						
					} else {
						ruleSetForm.getForm().findField("convertRate").hide();
						ruleSetForm.getForm().findField("convertRate").setDisabled(true);
					}
					ruleSetPanel.buttons[0].setDisabled(true);
					ruleSetWindow.setTitle('规则设置');
				}
		  },{text : '启用',
				iconCls : 'detailIconCss',
				handler : function() {
					 var checkedNodes = listPanel.getSelectionModel().selections.items;
			      		var selectLength = listPanel.getSelectionModel().getSelections().length;
			      		var selectRe;
			      		var idStr = '';
			      		if (selectLength <1) {
			      			Ext.Msg.alert('提示', '请选择要启用的记录！');
			      		}else{

		      				var json = {'id' : []};
		      				for (var i = 0; i < checkedNodes.length; i++) {
		      					if(checkedNodes[i].data.status!='0'){
		      						Ext.Msg.alert('系统提示', '请选择非启用状态的记录！');
		      						return false;
		      					}
		      					if(i==checkedNodes.length-1)
		      						idStr+=checkedNodes[i].data.id;
		      					else
		      						idStr+=checkedNodes[i].data.id+',';
		      				}
		      				Ext.MessageBox.confirm('提示', '确定启用吗？', function(buttonId) {
		      					if (buttonId.toLowerCase() == "no") {
		      						return;
		      					}
		      					Ext.Ajax.request({
		      								url : basepath + '/ocrmFSysRuleScoreSet!batchUse.json',
		      								method : 'POST',
		      								params : {
		      									ids : idStr,
		      									use : 'yes'
		      								},
		      								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		      								success : function() {
		      									Ext.Msg.alert('提示', '启用成功！');
		      									store.reload();
		      								},
		      								failure : function(response) {
		      									var resultArray = Ext.util.JSON.decode(response.status);
		      									if (resultArray == 403) {
		      										Ext.Msg.alert('提示',
		      												response.responseText);
		      									} else {
		      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
		      									}
		      									store.reload();
		      								}
		      							});

		      				});
			      		}
					
				}
		  
		  },{text : '禁用',
				iconCls : 'closeIconCss',
				handler : function() {
					 var checkedNodes = listPanel.getSelectionModel().selections.items;
			      		var selectLength = listPanel.getSelectionModel().getSelections().length;
			      		var selectRe;
			      		var idStr = '';
			      		if (selectLength <1) {
			      			Ext.Msg.alert('提示', '请选择要禁用的记录！');
			      		}else{

		      				for (var i = 0; i < checkedNodes.length; i++) {
		      					if(checkedNodes[i].data.status!='1'){
		      						Ext.Msg.alert('系统提示', '请选择启用状态的记录！');
		      						return false;
		      					}
		      					if(i==checkedNodes.length-1)
		      						idStr+=checkedNodes[i].data.id;
		      					else
		      						idStr+=checkedNodes[i].data.id+',';
		      					
		      				}
		      				Ext.MessageBox.confirm('提示', '确定禁用吗？', function(buttonId) {
		      					if (buttonId.toLowerCase() == "no") {
		      						return;
		      					}
		      					Ext.Ajax.request({
		      								url : basepath + '/ocrmFSysRuleScoreSet!batchUse.json',
		      								method : 'POST',
		      								params : {
		      									ids : idStr,
		      									use : 'no'
		      								},
		      								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		      								success : function() {
		      									Ext.Msg.alert('提示', '禁用成功！');
		      									store.reload();
		      								},
		      								failure : function(response) {
		      									var resultArray = Ext.util.JSON.decode(response.status);
		      									if (resultArray == 403) {
		      										Ext.Msg.alert('提示',
		      												response.responseText);
		      									} else {
		      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
		      									}
		      									store.reload();
		      								}
		      							});

		      				});
			      		}
					
				}
		  }],
		  bbar : bbar,
		  viewConfig : {},
		  loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  }
	  });


var searchPanel = new Ext.form.FormPanel( {
	title:"规则查询",
	labelWidth : 100,
	frame : true,
	autoScroll : true,
	region : 'north',
	buttonAlign : "center",
	height : 130,
	width : '100%',
	labelAlign : 'right',
	items : [ {
		layout : 'column',
	items : [{
		columnWidth : .25,
		layout : 'form',
		items : [ new Com.yucheng.crm.common.IndexSearchField({
			xtype:'indexchoose',
			fieldLabel : '指标名称', 
			id:'INDEX',
			labelStyle: 'text-align:right;',
			name : 'INDEX_NAME',
			hiddenName:'INDEX_CODE',
			searchType:'INDEXTREE',//INDEXTREE：全部可以查询   INDEXTREEBYTYPE ：需要根据一个下拉框的状态来控制类别
			singleSelect:true,
			anchor : '90%'
		})]
	},{
		columnWidth : .25,
		layout : 'form',
		items : [{
			xtype : 'textfield',
			fieldLabel : '折算规则名称',
			name : 'SCORE_NAME',
			anchor : '90%'
		}]},{
			columnWidth : .25,
			layout : 'form',
			items : [new Ext.form.ComboBox({
				hiddenName : 'USE_WAY',
    			fieldLabel : '用途',
    			labelStyle: 'text-align:right;',
    			triggerAction : 'all',
    			store : ruleUseStore,
    			displayField : 'value',
    			valueField : 'key',
    			mode : 'local',
    			emptyText:'请选择 ',
    			resizable : true,
    			anchor : '90%'
			})]},{
				columnWidth : .25,
				layout : 'form',
				items : [new Ext.form.ComboBox({
					hiddenName : 'STATUS',
	    			fieldLabel : '是否启用',
	    			labelStyle: 'text-align:right;',
	    			triggerAction : 'all',
	    			store : ifStore,
	    			displayField : 'value',
	    			valueField : 'key',
	    			mode : 'local',
	    			emptyText:'请选择 ',
	    			resizable : true,
	    			anchor : '90%'
				})]},{
					columnWidth : .25,
					layout : 'form',
					items : [new Ext.form.ComboBox({
						hiddenName : 'CUST_TYPE',
		    			fieldLabel : '适用客户类型',
		    			labelStyle: 'text-align:right;',
		    			triggerAction : 'all',
		    			store : typeRangeStore,
		    			displayField : 'value',
		    			valueField : 'key',
		    			mode : 'local',
		    			emptyText:'请选择 ',
		    			resizable : true,
		    			anchor : '90%'
					})]}]
	} ],
		buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = searchPanel.getForm().getValues(false);
				store.on('beforeLoad', function() {
					this.baseParams = {
						"condition" : Ext.encode(conditionStr)
					};
				});
				store.load( {
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});

			}
		}, {
			text : '重置',
			handler : function() {
				searchPanel.form.reset();
			}
		}]
		
	});

store.load( {
	params : {
		start : 0,
		limit : bbar.pageSize
	}
});

ruleSetWindow.addListener('hide',function(){
	document.getElementById('useType').value="";
	  store.reload({
		  params : {
		  start : 0,
		  limit : bbar.pageSize
	  }
	  });
  });


var viewport = new Ext.Viewport( {
	layout : 'fit',
	items : [ {
		layout : 'border',
		items : [ searchPanel,listPanel ]
	} ]
});
});