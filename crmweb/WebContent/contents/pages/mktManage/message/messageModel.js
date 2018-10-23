/**
 * 营销短信模板处理
 */
Ext.onReady(function(){
	var catlCode = '';
	var catlName = '';
	var oprate = '';
/**
	 *产品树数据加载 
	 */			
	var productTreeLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'CATL_PARENT',//指向父节点的属性列
		locateAttr : 'CATL_CODE',//节点定位属性列，也是父属性所指向的列
		rootValue :'0',//虚拟根节点id 若果select的值为null则为根节点
		textField : 'CATL_NAME',//用于展示节点名称的属性列
		idProperties : 'CATL_CODE'//,//指定节点ID的属性列
	});
	
	/**
	 * 产品树数据请求
	 */
	Ext.Ajax.request({//请求产品树数据
		url : basepath + '/productCatlTreeAction.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			productTreeLoader.nodeArray = nodeArra;
			var children = productTreeLoader.loadAll();
			debugger;
			Ext.getCmp('productLeftTreePanel').appendChild(children);
		}
	});

	/**
	 * 产品树面板，在模块左侧显示
	 */
	var productLeftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		id:'productLeftTreePanel',
		height : document.body.clientHeight,
		width : 200,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
		resloader:productTreeLoader,//加载产品树
		region:'west',//布局位置设置
		split:true,
		root: new Ext.tree.AsyncTreeNode({//设置根节点
			id:'root',
			expanded:true,
			text:'稠州集团产品树',
			autoScroll:true,
			children:[]
		}),
		clickFn:function(node){//单击事件
			if( node.hasChildNodes()){
				Ext.MessageBox.alert('提示', '请选择基础类别进项操作 !');
				atlCode = '';
				catlName = '';
				store.removeAll();
				modelGrid.tbar.setDisplayed(false);
				return;
			}else{
				//根据选择的产品类别，查询配置的模板信息，不级联查询
				catlCode = node.attributes.CATL_CODE;
				catlName = node.attributes.CATL_NAME;
				modelGrid.tbar.setDisplayed(true);
				store.reload({
					params : {
						'catlCode':catlCode
					}
				});
			}
	 	}
	 });

	// 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([
			rownum,
			sm,
			{header : 'id',dataIndex : 'id',hidden : true},
			{header : '产品类别id',dataIndex : 'catlCode',hidden : true,sortable : true,align : 'left',width : 170},
			{header:'产品类别名称',dataIndex:'catlName',hidden : true,sortable:true,align:'left',width:200},
			{header:'模板名称',dataIndex:'modelName',sortable:true,align:'left',width:200},
			{header:'模板内容',dataIndex:'modelInfo',sortable:true,align:'left',width:450}
			]);
	
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy(
				{
					url : basepath + '/ocrmFMmSysType.json',
					method : 'POST',
					failure : function(response) {
						var resultArray = Ext.util.JSON
								.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
				}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'json.data',
			totalProperty : 'json.count'
		}, [ {name : 'id',mapping:'ID'},
		     {name:'catlCode',mapping:'CATL_CODE'},
		     {name:'catlName',mapping:'CATL_NAME'},
		     {name:'modelName',mapping:'MODEL_NAME'},
		     {name:'modelInfo',mapping:'MODEL_INFO'}
		     ])
	});
	
	var panel = new Ext.form.FormPanel({
		frame:true,
		region:'center',
		labelWidth:80,
		items:[{
			html:'<div style="line-height:150%"> &ensp;&ensp;&ensp;&ensp;短信内容填写说明:模板中的客户名称等信息请使用约定符号代替!<p/>&ensp;&ensp;&ensp;&ensp;符号说明如下：<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户名称：@custName <p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品编号：@productId<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品名称：@prodName<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品发布日期：@prodStartDate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品截止日期：@prodEndDate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品利率：@rate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品费率：@castRate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户经理：@manger<p/>'+
				'&ensp;&ensp;&ensp;&ensp;例如：<p/>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;'+
				'@custName先生/女士你好，我行将于@prodStartDate发售新产品@prodName，请关注。联系客户经理：@manger。</div>'
		},{
			xtype:'textfield',
			fieldLabel : 'id',
			name : 'id',
			hidden:true,
			labelStyle : 'text-align:right;',
			anchor : '100%'
		},{
			xtype:'textfield',
			fieldLabel : '类型id',
			hidden:true,
			name : 'catlCode',
			labelStyle : 'text-align:right;',
			anchor : '100%'
		},{
			xtype:'textfield',
			fieldLabel : '类型编号',
			name : 'catlName',
			hidden:true,
			labelStyle : 'text-align:right;',
			anchor : '100%'
		},{
			xtype:'textfield',
			fieldLabel : '模板名称',
			name : 'modelName',
			allowBlank:false,
			labelStyle : 'text-align:right;',
			anchor : '100%'
		},{
			xtype:'textarea',
			fieldLabel : '模板内容',
			name : 'modelInfo',
			allowBlank:false,
			labelStyle : 'text-align:right;',
			anchor : '100%'
		}]
		});
	var panel2 = new Ext.form.FormPanel({
		frame:true,
		region:'center',
		labelWidth:80,
		items:[{
			xtype:'textfield',
			fieldLabel : '模板名称',
			name : 'modelName',
			allowBlank:false,
			labelStyle : 'text-align:right;',
			anchor : '100%'
		},{
			xtype:'textarea',
			fieldLabel : '模板内容',
			name : 'modelInfo',
			allowBlank:false,
			labelStyle : 'text-align:right;',
			anchor : '100%'
		}]
		});
	var infoWin2 = new Ext.Window({
		title:'短信模板信息',
		height:170,
		width:450,
		buttonAlign : 'center',
		draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll : true,
		closeAction : 'hide',
		border : false,
		items : [ panel2 ],
		buttons : [ {
			text : '关 闭',
			handler : function() {
				infoWin2.hide();
			}
		} ]
	});
	var infoWin = new Ext.Window({
		title:'短信模板信息',
		height:450,
		width:450,
		buttonAlign : 'center',
		draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll : true,
		closeAction : 'hide',
		border : false,
		items : [ panel ],
		buttons : [ {text:'保存',
			handler:function(){
				if(!panel.getForm().isValid()){
					 Ext.MessageBox.alert('提示','输入有误,请检查输入项');
			         return false;
				}else{
					 Ext.Msg.wait('正在保存，请稍后......','系统提示');
						Ext.Ajax.request({
				             url : basepath + '/ocrmFMmSysType!saveData.json',
				             method : 'POST',
				             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				             form : panel.getForm().id,
				             params:{
				            	 'oprate':oprate
				             },
				             success : function() {
				                 Ext.Msg.alert('提示', '操作成功');
				                 infoWin.hide();
				                 store.reload({
				 					params : {
				 						'catlCode':catlCode
				 					}
				 				});
				             },
				             failure : function(response) {
				                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				             }
				         });
				}
			}},{
			text : '关 闭',
			handler : function() {
				infoWin.hide();
			}
		} ]
	});
	var tbar = new Ext.Toolbar({
		items : [{text:'新增模板',
			iconCls:'addIconCss',
			handler:function(){
				infoWin.show();
				panel.getForm().reset();
				oprate = 'add';
				panel.getForm().findField('catlCode').setValue(catlCode);
				panel.getForm().findField('catlName').setValue(catlName);
		}},{text:'修改模板',
			iconCls:'editIconCss',
			handler:function(){
				var selectLength = modelGrid.getSelectionModel().getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = modelGrid.getSelectionModel().getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						panel.getForm().loadRecord(infoRecord);
						infoWin.show();
						oprate = 'update';
					}
				}
			
		}},{text:'删除模板',
			iconCls : 'deleteIconCss',
			handler:function(){
	        	var checkedNodes = modelGrid.getSelectionModel().selections.items;
	      		var selectLength = modelGrid.getSelectionModel().getSelections().length;
	      		var selectRe;
	      		var tempId;
	      		var idStr = '';
	      		if (selectLength < 1) {
	      			Ext.Msg.alert('提示', '请选择要删除的记录！');
	      		} else {
	      				var json = {'id' : []};
	      				for (var i = 0; i < checkedNodes.length; i++) {
	      					json.id.push(checkedNodes[i].data.id);
	      				}
	      				Ext.MessageBox.confirm('提示', '确定删除吗？', function(buttonId) {
	      					if (buttonId.toLowerCase() == "no") {
	      						return;
	      					}
	      					Ext.Ajax.request({
	      								url : basepath + '/ocrmFMmSysType!batchDel.json',
	      								method : 'POST',
	      								params : {
	      									ids : Ext.encode(json)
	      								},
	      								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	      								success : function() {
	      									Ext.Msg.alert('提示', '操作成功！');
	      									store.reload({
	      					 					params : {
	      					 						'catlCode':catlCode
	      					 					}
	      					 				});
	      								},
	      								failure : function(response) {
	      									var resultArray = Ext.util.JSON.decode(response.status);
	      									if (resultArray == 403) {
	      										Ext.Msg.alert('提示',
	      												response.responseText);
	      									} else {
	      										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
	      									}
	      									store.reload({
	      					 					params : {
	      					 						'catlCode':catlCode
	      					 					}
	      					 				});
	      								}
	      							});

	      				});
	      		}
	        	  
	          
				
		}},{text:'内容查看',
			iconCls : 'detailIconCss',
			handler:function(){
				var selectLength = modelGrid.getSelectionModel().getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = modelGrid.getSelectionModel().getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						panel2.getForm().loadRecord(infoRecord);
						infoWin2.show();
					}
				}
			}}]
	});
	
	var pagesize_combo = new Ext.form.ComboBox({
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
		width : 85
	});
	var number = parseInt(pagesize_combo.getValue());
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	// 表格实例
	var modelGrid = new Ext.grid.GridPanel({
		frame : true,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var view = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [ {
			layout : 'border',
			items : [ productLeftTreeForShow, modelGrid ]
		} ]
	 	});
	modelGrid.tbar.setDisplayed(false);
});