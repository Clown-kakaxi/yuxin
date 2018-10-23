/**
 * 数据权限过滤器配置
 * @author wz
 * @since 2012-10-16
 */
Ext.onReady(function() {
	
	/**MAP 表操作****************start**/
//	var pagesize_combo = new Ext.form.ComboBox({//每页显示条数下拉选择框
//		name : 'pagesize',
//		triggerAction : 'all',
//		mode : 'local',
//		store : new Ext.data.ArrayStore({
//			fields : ['value', 'text'],
//			data : [[10, '10条/页'], [20, '20条/页'],[50, '50条/页'], [100, '100条/页'],[250, '250条/页'], [500, '500条/页']]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		value : '20',
//		forceSelection : true,
//		width : 85
//	});

//	pagesize_combo.on("select", function(comboBox) {//改变每页显示条数reload数据
//		pageBar.pageSize = parseInt(pagesize_combo.getValue()), mapStore.reload({
//			params : {
//				start : 0,
//				limit : parseInt(pagesize_combo.getValue())
//			}
//		});
//	});
	
//	var pageBar = new Ext.PagingToolbar({//分页工具栏
//		pageSize : parseInt(pagesize_combo.getValue()),
//		store : mapStore,
//		displayInfo : true,
//		displayMsg : '显示{0}条到{1}条,共{2}条',
//		emptyMsg : "没有符合条件的记录",
//		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
//	});
	
	var mapSM = new Ext.grid.CheckboxSelectionModel();//复选框
	var mapRN = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var mapColumns = new Ext.grid.ColumnModel([mapRN, mapSM,//列模型 
		{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 120},
		{header : '功能函数ID',dataIndex : 'functionId',hidden : true,sortable : true,width : 100}, 
		{header : '查询类名称',dataIndex : 'className',sortable : true,width : 100}, 
		{header : '查询类描述',dataIndex : 'classDesc',sortable : true,width : 80}
		]);
	
	//map新增时 类描述用store
	var classDescStore = new Ext.data.Store({
		restful: true,
		autoLoad: true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/functionQueryAction.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		},['ID','FUNC_NAME'])
	});
	
	//mapgrid 用record
	var mapRecord = Ext.data.Record.create([
		{name : 'id',           mapping : 'ID'},
		{name : 'functionId',	mapping : 'FUNCTION_ID'},
		{name : 'className',	mapping : 'CLASS_NAME'},
		{name : 'classDesc',	mapping : 'CLASS_DESC'}
		]);
	
	
	//mapgrid 用store
	var mapStore = new Ext.data.Store({//查询数据源
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/dataGrantMapQueryAction.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, mapRecord)
	});
	
	mapStore.load({
//		params : {
//			start : 0,
//			limit : parseInt(pagesize_combo.getValue())
//    	}
	});
	
	var mapGrid = new Ext.grid.GridPanel({//显示查询结果Grid
		store : mapStore,
		frame : true,
		sm : mapSM,
		cm : mapColumns,
		height:445,
//		height : document.body.clientHeight-30,
		tbar : [{
			text : '新增',
			iconCls : 'addIconCss',
			handler : function() {
				mapForm.getForm().reset();
				mapWin.setTitle('MAP新增');
				mapWin.show();
			}
		}, '-', {
    		text : '修改',
			iconCls : 'editIconCss',
			handler : function() {
				var record = mapGrid.getSelectionModel().getSelected();
				var recordLeng = mapGrid.getSelectionModel().getSelections();
				if(recordLeng < 1){
					Ext.Msg.alert('提示','请选择一条记录！');
					return false;
				}
				mapForm.getForm().loadRecord(record);
				mapForm.getForm().findField('classDescId').setValue(record.data.classDesc);
				mapWin.setTitle('MAP修改 ');
				mapWin.show();
			}
		}, '-', {
			text : '删除',
			iconCls : 'deleteIconCss',
			handler : function() {
				var records = mapGrid.getSelectionModel().getSelections();//选择的记录
				var selectLength = records.length;//获得选择记录的条数
				if(selectLength < 1){
					Ext.Msg.alert('提示','请选择要删除的记录！');
					return false;
				}
				var idStr = '';
				for(var i=0;i<selectLength;i++){
					idStr += records[i].data.id;
					if(i != selectLength - 1){
						idStr += ',';
					}
				}
				Ext.MessageBox.confirm('提示','确定要删除所选信息么？',function(btn){
					if(btn == 'yes'){
						Ext.Ajax.request({
							url: basepath + '/dataGrantMapAction!delFun.json',
							method : 'POST',
							params : {
								delId : idStr
							},
							success: function(){
								Ext.Msg.alert('提示','删除成功！');
								mapStore.reload();
							},
							failure: function(){
								Ext.Msg.alert('提示','删除失败，请稍后再试！');
							}
						});
					}else{
						
					}
				});
			}
		}],
//    	bbar : pageBar, // 分页工具栏
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	// MAP新增窗口展示的form
	var mapForm = new Ext.form.FormPanel({
		id : 'mapForm',
		frame : true,
		labelAlign : 'right',
		region : 'center',
		buttonAlign : "center",
		items : [{
				layout : 'form',
				items : [{
					id : 'classNameId',
				    xtype : 'textfield',
				    fieldLabel : '查询类名称',
				    name : 'className',
				    allowBlank : false,
				    anchor : '90%'
				}, {
					id : 'classDescId',
				    xtype : 'textfield',
				    fieldLabel : '查询类描述',
				    name : 'classDesc',
				    allowBlank : false,
				    anchor : '90%'
				},{
					id : 'funcId',
				    name : 'functionId',
				    xtype : 'textfield',
				    hidden : true,
				    fieldLabel : '功能函数ID',
				    anchor : '90%'
				},{
					id : 'id',
				    xtype : 'textfield',
				    name : 'id',
				    hidden : true,
				    anchor : '90%'
				}]
		}, {
		    layout : 'form',
			buttonAlign : 'center',
			buttons : [{
			    text : '保  存',
				handler : function() {
    			    if(!mapForm.getForm().isValid()){
                        Ext.MessageBox.alert('新增操作', '请正确输入各项必要信息！');
                        return false;
                    }
    			    Ext.Ajax.request({
    			    	url: basepath + '/dataGrantMapAction.json',
    			    	method : 'POST',
    			    	params : mapForm.getForm().getFieldValues(),
    			    	waitMsg : '正在保存数据,请等待...',
    			    	success : function(){
    			    		Ext.Msg.alert('提示','操作成功!');
    			    		mapStore.reload();
    			    		mapWin.hide();
    			    },
    			    	failure : function(){
    			    		Ext.Msg.alert('提示','操作失败，请稍后再试！');
    			    }
    			    });
				}
			}, {
				text : '取  消',
				handler : function() {
					mapWin.hide();
			}
			}]
		}]
	});

	var mapWin = new Ext.Window({// 定义新增窗口
	//	title : 'MAP新增',
		width : 400,
		draggable : true,  //可拖动，默认为false
		closable : true,
		closeAction : 'hide',	
		modal : true,     //对其后面的一切内容进行灰显
		border : false,   //隐藏面板body元素的边框
		items : [mapForm]
	});
	/**MAP 表操作****************end**/
	
//	/**FILTER 表操作*************start**/
//	var filterPagesize_combo = new Ext.form.ComboBox({//每页显示条数下拉选择框
//		name : 'pagesize',
//		triggerAction : 'all',
//		mode : 'local',
//		store : new Ext.data.ArrayStore({
//			fields : ['value', 'text'],
//			data : [[10, '10条/页'], [20, '20条/页'],[50, '50条/页'], [100, '100条/页'],[250, '250条/页'], [500, '500条/页']]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		value : '20',
//		forceSelection : true,
//		width : 85
//	});

//	filterPagesize_combo.on("select", function(comboBox) {//改变每页显示条数reload数据
//		pageBarFilter.pageSize = parseInt(filterPagesize_combo.getValue()), filterStore.reload({
//			params : {
//				start : 0,
//				limit : parseInt(filterPagesize_combo.getValue())
//			}
//		});
//	});
	
//	var pageBarFilter = new Ext.PagingToolbar({//分页工具栏
//		pageSize : parseInt(filterPagesize_combo.getValue()),
//		store : filterStore,
//		displayInfo : true,
//		displayMsg : '显示{0}条到{1}条,共{2}条',
//		emptyMsg : "没有符合条件的记录",
//		items : ['-', '&nbsp;&nbsp;', filterPagesize_combo]
//	});
	
	var filterSM = new Ext.grid.CheckboxSelectionModel();//复选框
	var filterRN = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var filterColumns = new Ext.grid.ColumnModel([filterRN, filterSM,//列模型 
		{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 120},
		{header : '类名称',dataIndex : 'className',sortable : true,width : 100}, 
		{header : '方法名',dataIndex : 'methodName',sortable : true,hidden : true,width : 100},
		{header : '角色ID',dataIndex : 'roleId',sortable : true,hidden : true,width : 100},
		{header : '过滤SQL',dataIndex : 'sqlString',sortable : true,width : 100},
		{header : 'SQL描述',dataIndex : 'describetion',sortable : true,width : 100}
		]);
	
	//filtergrid 用record
	var filterRecord = Ext.data.Record.create([
		{name : 'id',           mapping : 'ID'},
		{name : 'className',	mapping : 'CLASS_NAME'},
		{name : 'methodName',	mapping : 'METHOD_NAME'},
		{name : 'roleId',	mapping : 'ROLE_ID'},
		{name : 'sqlString',	mapping : 'SQL_STRING'},
		{name : 'describetion',	mapping : 'DESCRIBETION'}
		]);
	
	
	//filtergrid 用store
	var filterStore = new Ext.data.Store({//查询数据源
		restful : true,
		autoLoad : false,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/dataGrantFilterQueryAction.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, filterRecord)
	});
	
	var filterGrid = new Ext.grid.GridPanel({//显示查询结果Grid
		store : filterStore,
		frame : true,
		sm : filterSM,
		cm : filterColumns,
		height:445,
//		height : document.body.clientHeight-30,
		tbar : [{
			id : 'filterAdd',
			text : '新增',
			iconCls : 'addIconCss',
			disabled : true,
			handler : function() {
			filterWin.setTitle('新增过滤条件');
				filterWin.show();
				
			}
		}, '-', {
			id : 'filterMod',
    		text : '修改',
			iconCls : 'editIconCss',
			disabled : true,
			handler : function() {
				var record = filterGrid.getSelectionModel().getSelected();
				var recordLeng = filterGrid.getSelectionModel().getSelections();
				if(recordLeng < 1){
					Ext.Msg.alert('提示','请选择一条记录！');
					return false;
				}
				filterForm.getForm().loadRecord(record);
				filterWin.setTitle('修改过滤条件');
				filterWin.show();
			}
		}, '-', {
			id : 'filterDel',
			text : '删除',
			iconCls : 'deleteIconCss',
			disabled : true,
			handler : function() {
				var records = filterGrid.getSelectionModel().getSelections();//选择的记录
				var selectLength = records.length;//获得选择记录的条数
				if(selectLength < 1){
					Ext.Msg.alert('提示','请选择要删除的记录！');
					return false;
				}
				var idStr = '';
				for(var i=0;i<selectLength;i++){
					idStr += records[i].data.id;
					if(i != selectLength - 1){
						idStr += ',';
					}
				}
				Ext.MessageBox.confirm('提示','确定要删除所选信息么？',function(btn){
					if(btn == 'yes'){
						Ext.Ajax.request({
							url: basepath + '/dataGrantFilterAction!delFun.json',
							method : 'POST',
							params : {
								delId : idStr
							},
							success: function(){
								Ext.Msg.alert('提示','删除成功！');
								filterStore.reload();
							}
						});
					}else{
						
					}
				});
			}
		}],
//    	bbar : pageBarFilter, // 分页工具栏
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	// MAP新增窗口展示的form
	var filterForm = new Ext.form.FormPanel({
		id : 'filterForm',
		frame : true,
		labelAlign : 'right',
		region : 'center',
		buttonAlign : "center",
		items : [{
				layout : 'form',
				items : [{
					id : 'classNameF',
				    xtype : 'textfield',
				    fieldLabel : '类名称',
				    name : 'className',
				    allowBlank : false,
				    readOnly : true,
				    anchor : '90%'
				},{
					id : 'sqlStringId',
				    name : 'sqlString',
				    xtype : 'textfield',
				    fieldLabel : '过滤SQL',
				    allowBlank : false,
				    anchor : '90%'
				},{
					id : 'sqlDescId',
				    name : 'describetion',
				    xtype : 'textfield',
				    fieldLabel : 'SQL描述',
				    allowBlank : false,
				    anchor : '90%'
				},{
					id : 'idF',
				    xtype : 'textfield',
				    name : 'id',
				    hidden : true,
				    anchor : '90%'
				}]
		}, {
		    layout : 'form',
			buttonAlign : 'center',
			buttons : [{
			    text : '保  存',
				handler : function() {
    			    if(!filterForm.getForm().isValid()){
                        Ext.MessageBox.alert('请正确输入各项必要信息！');
                        return false;
                    }
    			    Ext.Ajax.request({
    			    	url: basepath + '/dataGrantFilterAction.json',
    			    	method : 'POST',
    			    	params : filterForm.getForm().getFieldValues(),
    			    	waitMsg : '正在保存数据,请等待...',
    			    	success : function(){
    			    		Ext.Msg.alert('提示','操作成功!');
    			    		filterForm.getForm().reset();
    			    		filterStore.reload();
    			    		filterWin.hide();
    			    },
    			    	failure : function(){
    			    		Ext.Msg.alert('提示','操作失败，请稍后再试！');
    			    }
    			    });
				}
			}, {
				text : '取  消',
				handler : function() {
					filterWin.hide();
			}
			}]
		}]
	});

	var filterWin = new Ext.Window({// 定义新增窗口
//		title : '新增过滤条件',
		width : 400,
		draggable : true,  //可拖动，默认为false
		closable : true,
		closeAction : 'hide',	
		modal : true,     //对其后面的一切内容进行灰显
		border : false,   //隐藏面板body元素的边框
		items : [filterForm]
	});
	/**FILTER 表操作*************end****/
	
	mapGrid.on('cellclick',function(e){
		if (e.getSelectionModel().hasSelection()) {
			var rs =  e.getSelectionModel().getSelected();
			filterStore.load({
				params:{
				selectName : rs.data.className
			},
			callback : function(){//控制数据查询过滤器tbar 显示用
				var len = filterStore.data.length;
				if(len >= 1){
					Ext.getCmp('filterAdd').enable();
					Ext.getCmp('filterMod').enable();
					Ext.getCmp('filterDel').enable();
				}
			}
			});
			filterForm.getForm().findField('classNameF').setValue(rs.data.className);
		}
	});

	var viewport = new Ext.Viewport({//整体显示布局
	    layout : 'fit',
	    items:[{
	        layout : 'border',
    	    items : [{
    	        region : 'west',
    	        title : '数据查询类',
    	        autoScroll: true,
    	        width:400,
    	        items : [mapGrid]
    	    },{
    	        region : 'center',
    	        title : '数据查询过滤器',
    	        autoScroll: true,
    	        items : [filterGrid]
    	    }]
	    }]
	});
});