/**
 * 客户视图项维护
 * @author zhangsxin
 * @since 2012-12-4
 */
Ext.onReady(function(){
	
	/********************************客户视图树**************************************/
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {

		// checked:true,
		// /**节点数组，可以改为从后台读取*/
		// nodeArray :nodeArra,
		/** 指向父节点的属性列 */
		parentAttr : 'PARENTID',
		/** 节点定位属性列，也是父属性所指向的列 */
		locateAttr : 'ID',
		/** 虚拟根节点id */
		rootValue : '0',
		/** 用于展示节点名称的属性列 */
		textField : 'NAME',
		/** 指定节点ID的属性列 */
		idProperties : 'ID',
		/** 节点点击事件句柄 */
		clickFn : function(node) {
		}
	});
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
		layout:'fit',
		region:'center',
		autoScroll : true,
		rootVisible : false,
		split : true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode( {
			id : 'root',
			expanded : true,
			autoScroll : true,
			children : []
		}),
		resloader : loader,
		clickFn:function(node){
    		Ext.getCmp('sectionCategory').setValue(node.text);
    		Ext.getCmp('parentid').setValue(node.attributes.ID);
		}
	});
	
	//新增和修改父节点下拉框
	var comboxWithTree = new Ext.form.ComboBox({
		id : 'sectionCategory', 
		xtype:'combo',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		labelStyle: 'text-align:right;',
//		editable : false,
		emptyText : '请选择...',
		fieldLabel : '<font color=red>*</font>父节点',
		anchor : '90%',
		mode : 'local',
		resizable :false,
//		forceSelection:true,
		name:'parentName',
		triggerAction : 'all',
		maxHeight : 390,
		tpl : "<tpl for='.'><div style='height:390px'><div id='viewTreeDiv_1'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
			treeOfPoroduct.render('viewTreeDiv_1');
			},
			'collapse':function(combo){
			}
		}
	});
	
	 var viewtypeStore = new Ext.data.ArrayStore( {
		         fields : [ 'key', 'value' ],
		         data : [ [ '1', '对私' ], [ '2', '对公' ], [ '3', '普通客户群' ], [ '4', '商圈客户群' ]]
		     });
	
	//父节点ID的store
	var parentidStore = new Ext.data.JsonStore({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/parentidQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [ 'PARENTID' ])
	});
	var custViewRecord = Ext.data.Record.create(
			[
			 {name:'id',mapping:'ID'},
			 {name:'name',mapping:'NAME'},
			 {name:'addr',mapping:'ADDR'},
			 {name:'parentid',mapping:'PARENTID'},
			 {name:'orders',mapping:'ORDERS'},
			 {name:'viewtype',mapping:'VIEWTYPE'},
			 {name:'viewtypeOra',mapping:'VIEWTYPE_ORA'},
			 {name:'parentName',mapping:'PARENT_NAME'}
			 ]
	);
	var custViewReader = new Ext.data.JsonReader(
			{
				successProperty : 'success',
				idProperty : 'ID',
				totalProperty : 'json.count',
				root : 'json.data'	
			},custViewRecord
	);
	var custViewStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath+'/CustViewMaintainInfo-action.json',
			method : 'get'
		}),
		reader : custViewReader
	});
	
	var pagesize_combo = new Ext.form.ComboBox({//每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : ['value', 'text'],
			data : [[10, '10条/页'], [20, '20条/页'],[50, '50条/页'], [100, '100条/页'],[250, '250条/页'], [500, '500条/页']]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});
	
	custViewStore.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
 	   }
	});
	
	
	pagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		custViewStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	
	var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
		pageSize : parseInt(pagesize_combo.getValue()),
		store : custViewStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	var custViewQueryForm = new Ext.form.FormPanel({//客户视图项维护的查询form
		title:'客户视图项维护',
		labelWidth:100,
		region : 'north',
		height:106,
		frame:true,
		labelAlign:'right',
		buttonAlign:'center',
		layout:'column',
		items:[{
			columnWidth:.25,
			layout:'form',
			items:[{
				fieldLabel:'客户类型',
				name:'viewtype',
				id:'viewtype',
				xtype:'combo',
				store:viewtypeStore,
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				labelStyle:'text-align:right;',
				anchor:'95%'
			}]
		},{
			columnWidth:.25,
			layout:'form',
			items:[{
				fieldLabel:'视图项名称',
				name:'name',
				id:'name',
				xtype:'textfield',
				labelStyle:'text-align:right;',
				anchor:'95%'
			}]
		}],
		buttons:[{
			text:'查询',
			handler:function(){
				var conditionStr = custViewQueryForm.getForm().getFieldValues();
				custViewStore.baseParams = {
						"condition" : Ext.encode(conditionStr)
				};
				custViewStore.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
			 	   }
	     	 	});
		}
		},{
			text:'重置',
			handler:function(){//查询表单的重置方法
				custViewQueryForm.getForm().reset();
			}
		}]
		
	});
	
	var custsm = new Ext.grid.CheckboxSelectionModel();//复选框
	var rownum = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	var custViewColumns = new Ext.grid.ColumnModel([rownum, custsm,//列模型 
	                                       		{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 150},
	                                       		{header : '名称',dataIndex : 'name',sortable : true,width : 160}, 
	                                       		{header : '链接地址',dataIndex : 'addr',sortable : true,width : 380}, 
	                                       		{header : '父节点',dataIndex : 'parentName',sortable : true,width : 140}, 
	                                       		{header : '顺序号',dataIndex : 'orders' ,sortable : true,width : 140},
	                                       		{header : '客户类型',dataIndex : 'viewtypeOra',sortable : true,width : 160}
	                                       		]);
	var custViewMaintainGrid = new Ext.grid.GridPanel({//客户视图项信息表格
		store:custViewStore,
		frame:true,
		sm:custsm,
		cm:custViewColumns,
		region : 'center',
		frame : true,
		height:document.body.clientHeight-80,
		tbar:[{
			text:'新增',
			iconCls : 'addIconCss',
			handler:function(){
				custViewMaintainWind.show();
				custViewMaintainWind.setTitle('客户视图项新增');
				custViewMaintainForm.getForm().getEl().dom.reset();
				custViewStore.reload();
			}
		}, '-', {
			text:'修改',
			iconCls:'editIconCss',
			handler:function(){
				update();
				custViewStore.reload();
			}
		}, '-', {
			text:'删除',
			iconCls:'deleteIconCss',
			handler:function(){
			var _record = custViewMaintainGrid.getSelectionModel().getSelected();
			if (!_record) {
				Ext.MessageBox.alert('系统提示信息', '请选择1条删除的的信息！');
				return false;
			}else {
				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return;
					} 
					Ext.Ajax.request({
						url:basepath+'/CustViewMaintainInfo-action!destroy.json',
						method: 'POST',
						params : {
							'idStr' : custViewMaintainGrid.getSelectionModel().getSelected().data.id
						},
						success : function(response) {
							custViewStore.reload();
							Ext.Msg.alert('提示','信息删除成功' /*response.responseText*/);
						},
						failure : function(response) {
							Ext.Msg.alert('提示','操作失败' /*response.responseText*/);
						}
					});

				});
			}
		}
		}],
		bbar:bbar,
		viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	/*******************************************新增修改的form*******************************************/
	var custViewMaintainForm = new Ext.form.FormPanel({
		 labelWidth : 80,
		 height : 200,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : 'center',
		 items : [{
			 layout : 'column',
			 items:[{
				 layout : 'form',
				 columnWidth : .5,
				 items : [{
					 name:'id',
					 xtype:'textfield',
					 fieldLabel:'id',
					 hidden:true,
					 anchor:'90%'
				 },{
					 name:'name',
					 xtype:'textfield',
					 fieldLabel:'<font color=red>*</font>名称',
					 allowBlank : false,
					 anchor:'90%'
				 },{
					 xtype:'combo',
					 store:viewtypeStore,
					 resizable : true,
					 name:'viewtype',
					 id:'viewtype2',
					 hiddenName:'viewtype',
					 fieldLabel:'<font color=red>*</font>客户类型',
					 valueField : 'key',
					 displayField : 'value',
					 mode : 'local',
					 editable : false,
					 typeAhead : true,
					 forceSelection : true,
					 triggerAction : 'all',
					 emptyText : '请选择',
					 labelStyle:'text-align:right;',
					 selectOnFocus : true,
					 allowBlank : false,
					 anchor:'90%'
				 }]
			 },{
				 layout : 'form',
					columnWidth : .5,
					items : [{
						 name:'orders',
						 xtype:'numberfield',
						 fieldLabel:'<font color=red>*</font>顺序号',
						 allowBlank : false,
						 anchor:'90%'
					 },comboxWithTree,{
						 xtype:'textfield',
						 fieldLabel:'父节点值',
						 id:'parentid',
						 name : 'parentid',
						 labelStyle:'text-align:right;',
						 anchor:'90%',
						 hidden:true
					 }]
		},{
			layout : 'form',
			columnWidth : .96,
			items:[{
				name:'addr',
				 xtype:'textfield',
				 fieldLabel:'<font color=red>*</font>链接地址',
				 allowBlank : false,
				 anchor:'99%'
			}]
		}]
		 }]
	});
	//新增修改的窗口
	var custViewMaintainWind = new Ext.Window({//新增和修改的window
		closeAction:'hide',
		height:170,
		width:700,
		modal : true,//遮罩
		buttonAlign:'center',
		layout:'fit',
		items:[custViewMaintainForm],
		buttons:[
		         {
		        	 text:'保存',
		        	 handler:function(){
		        	 if (!custViewMaintainForm.getForm().isValid()) {
		        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		        		 return false;
		        	 }
		        	 Ext.Ajax.request({
		        		 url : basepath + '/CustViewMaintainInfo-action.json',
		        		 method : 'POST',
		        		 params : custViewMaintainForm.getForm().getFieldValues(),
		        		 waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		        		 success : function() {
		        		 Ext.Msg.alert('提示', '操作成功');
		        		 custViewStore.reload();
		        	 },
		        	 failure : function(response) {
		        		 var resultArray = Ext.util.JSON.decode(response.status);
		        		 if (resultArray == 403) {
		        			 Ext.Msg.alert('提示',response.responseText);
		        		 } else {
		        			 Ext.Msg.alert('提示','操作失败,失败原因:'+ response.responseText);
		        		 }
		        	 }
		        	 });
		        	 custViewMaintainWind.hide();
		         }
		         },{
		        	 text:'返回',
		        	 handler:function(){
		        	 	custViewMaintainWind.hide();
		         	}
		         }
		         ]
	});
	
	/*****************************修改的方法***********************************/
	var update = function() {
		var record = custViewMaintainGrid.getSelectionModel().getSelected();
		if(!record){
			Ext.MessageBox.alert('提示', '请选择要修改的一行！');
		}
		else{
		    custViewMaintainWind.setTitle('客户视图项修改');
            custViewMaintainForm.getForm().loadRecord(record);
            var aaa = Ext.getCmp('viewtype2').getValue();
            if(aaa != null && aaa != '')
            {
                Ext.Ajax.request( {
                    url : basepath + '/parentidQuery.json?viewtype='+aaa,
                    method : 'GET',
                    success : function(response) {
                    var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
                    loader.nodeArray = nodeArra;// 拿到从后台返回的数据
                    loader.refreshCache();// 刷新缓存
                    var children = loader.loadAll();// 得到相应的树数据
                    treeOfPoroduct.root.removeAll(true); // 清掉以前的数据
                    treeOfPoroduct.appendChild(children);// 把数据重新填充
                    }
                });
            }
            
			custViewMaintainWind.show();
			
		}
	};
	
	var viewport = new Ext.Viewport({//整体布局
		layout:'fit',
		frame : true,
		items : [{
			layout:'border',
			items:[
			       custViewQueryForm,custViewMaintainGrid
			       ]
		}]
	});
	/** *####################################下拉框联动###########################################** */
	var cb = Ext.getCmp('viewtype2');// 客户类型
	cb.on('select', function() {
				if (cb.getValue() == 1) {//对公
					Ext.Ajax.request( {
						url : basepath + '/parentidQuery.json?viewtype=1',
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
							loader.nodeArray = nodeArra;// 拿到从后台返回的数据
							loader.refreshCache();// 刷新缓存
							var children = loader.loadAll();// 得到相应的树数据
							treeOfPoroduct.root.removeAll(true); // 清掉以前的数据
							treeOfPoroduct.appendChild(children);// 把数据重新填充
						}
					});
				} else if (cb.getValue() == 2) {//对私
					Ext.Ajax.request( {
						url : basepath + '/parentidQuery.json?viewtype=2',
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
							loader.nodeArray = nodeArra;// 拿到从后台返回的数据
							loader.refreshCache();// 刷新缓存
							var children = loader.loadAll();// 得到相应的树数据
							treeOfPoroduct.root.removeAll(true); // 清掉以前的数据
							treeOfPoroduct.appendChild(children);// 把数据重新填充
						}
					});
				}else if (cb.getValue() == 3) {//对私
					Ext.Ajax.request( {
						url : basepath + '/parentidQuery.json?viewtype=3',
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
							loader.nodeArray = nodeArra;// 拿到从后台返回的数据
							loader.refreshCache();// 刷新缓存
							var children = loader.loadAll();// 得到相应的树数据
							treeOfPoroduct.root.removeAll(true); // 清掉以前的数据
							treeOfPoroduct.appendChild(children);// 把数据重新填充
						}
					});
				}else if (cb.getValue() == 4) {//对私
					Ext.Ajax.request( {
						url : basepath + '/parentidQuery.json?viewtype=4',
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
							loader.nodeArray = nodeArra;// 拿到从后台返回的数据
							loader.refreshCache();// 刷新缓存
							var children = loader.loadAll();// 得到相应的树数据
							treeOfPoroduct.root.removeAll(true); // 清掉以前的数据
							treeOfPoroduct.appendChild(children);// 把数据重新填充
						}
					});
				}else{
					treeOfPoroduct.root.removeAll(true); // 清掉以前的数据
				}
			});
});