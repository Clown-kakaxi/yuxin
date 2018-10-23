/**
 * 模拟利润参数维护
 * @author weijl
 * @since 2012-09-21
 */
Ext.onReady(function() {
	/*******************获取当前登录用户ID，机构ID，机构级别******************/
	var userId = __userId;
	var orgId = __units;
	var unitlevel = __unitlevel;

	/*******************获取参数树所需数据并以loader中的形式加载******************/
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'PARAM_KIND',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'PARAM_ID',
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : '0',
		/**用于展示节点名称的属性列*/
		textField : 'PARAM_NAME',
		/**指定节点ID的属性列*/
		idProperties : 'PARAM_ID'
		/**节点点击事件句柄*/
	});
	Ext.Ajax.request({
		url : basepath + '/ContributionParamTree.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.JSON.data;
			var children = loader.loadAll();
			parmTree.appendChild(children);
		}
	});
	
	/*******************参数选择下拉框******************/
	var comboxWithTree = new Ext.form.ComboBox({
		id : 'sectionCategory', 
		xtype:'combo',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		labelStyle: 'text-align:right;',
		editable : false,
		emptyText : '请选择...',
		fieldLabel : '参数选择',
		width : '100',
		anchor : '90%',
		mode : 'local',
		resizable :false,
		forceSelection:true,
		name:'paramName',
		triggerAction : 'all',
		maxHeight : 360,
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv_1'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
			parmTree.render('addDeptTreeDiv_1');
			},
			'collapse':function(combo){
			}
		}
	});

	/*******************参数分类数据源******************/
	var classStore = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CONTRI_PARAM_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	
	/*******************参数设置级别数据源******************/
	var parmLevelStore = new Ext.data.ArrayStore({
		fields:['key','value'],
		data : [['1','总行'],['2','分行']]
	});
	
	/*******************获取已有参数名数据源，用于防止设置重名参数******************/
	var checkrecord = Ext.data.Record.create([ 
   	    {name: 'parmName', mapping: 'PARM_NAME'},
 	    {name: 'parmNum', mapping: 'PARM_NUM'}
   		]);
  	                             	
   	var checkStore = new Ext.data.Store({
   		restful : true,
   		proxy : new Ext.data.HttpProxy({
   			url : basepath + '/ContributionParam.json',
   			method:'GET'
   		}),
   		autoLoad : true,
   		reader : new Ext.data.JsonReader({
   			successProperty: 'success',
   	        idProperty: 'parmName',
   	        messageProperty: 'message',
   			root : 'json.data',
   			totalProperty : 'json.count'
   		}, checkrecord)
   	});
	
   	/*******************查询表单******************/
	var qform = new Ext.form.FormPanel({
		labelWidth:90,
		height:90,
		frame:true,
		labelAlign:'middle',
		buttonAlign:'center',
		items:[{
			layout:'column',
			border:false,
			items:[{
				columnWidth:.33,
				border:true,
				layout:'form',
				labelWidth:80,
				items:[new Ext.form.ComboBox({
                    xtype:'combo',
                    fieldLabel : '参数分类',
                    name: 'parmType' ,
                    hiddenName : 'parmType' ,
                    id:'parmTypeSearch',
                    editable : false,
                    resizable:true,
                    forceSelection : true,
                    labelStyle: 'text-align:right;',
                    triggerAction:'all',
                    mode:'local',
                    store:classStore,
                    valueField:'key',
                    displayField:'value',
                    emptyText:'请选择',
                    anchor : '100%'
               })]
			}]
		}],
		buttons:[{
			text : '查询',
			handler : function() {
				var conditionStr = qform.getForm().getFieldValues();
				store.baseParams = {
						"condition" : Ext.encode(conditionStr)
					};
				store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
				checkStore.reload();
			}
		},{
			text:'重置',
			handler:function(){
			qform.getForm().reset();
			parmTree.root.getUI().toggleCheck(false);
		}
			
		}]
	});
	
	/*******************每页显示条数下拉选择框******************/
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
		forceSelection : true,
		width : 85
	});
	
	/*******************改变每页显示条数reload数据******************/
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue());
		store.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	
	/*******************复选框******************/
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	/*******************定义自动当前页行号******************/
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	/*******************列模型******************/
	var columns = new Ext.grid.ColumnModel([rownum,sm,{
		header : '参数id',
		dataIndex : 'parmId', 
		sortable : true,
		hidden : true,
		width : 120
	},{
        header : '参数分类',
        dataIndex : 'parmType', 
        sortable : true,
        width : 200
    },{
        header : '参数名',
        dataIndex : 'parmName', 
        sortable : true,
        width : 200
    },{
        header : '参数值',
        dataIndex : 'parmNum',
        align : 'right',
        renderer : money('0.000000'),
        sortable : true,
        width : 120
    },{
		header : '设置级别', 
		dataIndex : 'parmLevel', 
		sortable : true,
		renderer : function(value){
		if(value=='1'){
			return "总行";
		}
		if(value=='2'){
			return "分行";
		}
	},
		width : 120
	},{
		header : '设置机构', 
		dataIndex : 'orgname', 
		sortable : true,
		width : 150
	},{
		header : '设置人', 
		dataIndex : 'username', 
		sortable : true,
		width : 120
	},{
		header : '最近修改日期', 
		dataIndex : 'datedt', 
		sortable : true,
		width : 120
	},{
		header : '参数名值', 
		dataIndex : 'parmNameNum', 
		hidden : true,
		sortable : true,
		width : 120
	},{
		header : '参数类型值', 
		dataIndex : 'parmTypeNum', 
		hidden : true,
		sortable : true,
		width : 120
	}]);

	/*******************查询结果数据源，并以record中的形式匹配后台参数名******************/
	var record = Ext.data.Record.create([ 
	    {name: 'parmId', mapping: 'PARM_ID'},
	    {name: 'parmTypeNum', mapping: 'PARM_TYPE_NUM'},
	    {name: 'parmNameNum', mapping: 'PARM_NAME_NUM'},
		{name: 'parmType', mapping: 'PARM_TYPE'},
		{name: 'parmName', mapping: 'PARM_NAME'},
		{name: 'parmNum', mapping: 'PARM_NUM'},
		{name: 'parmLevel', mapping: 'PARM_LEVEL'},
		{name: 'brid', mapping: 'BRID'},
		{name: 'institutionName', mapping: 'INSTITUTION_NAME'},
		{name: 'orgname',mapping: 'UNITNAME'},
		{name: 'owenerid', mapping: 'OWENERID'},
		{name: 'username',mapping: 'USERNAME'},
		{name: 'datedt', mapping: 'DATEDT'}
		]);
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/analogprofparamquery.json',
			method:'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
	        idProperty: 'PARM_ID',
	        messageProperty: 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record)
	});

	/*******************分页工具栏******************/
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	/*******************新增窗口表单******************/
	var addNewForm = new Ext.form.FormPanel({
		labelWidth : 100,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			labelAlign : 'right',
			labelWidth : 100,
			items : [ {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					width : '100',
					id : "parmType",
					name : 'parmType',
					hidden : true,
					allowBlank : false,
					fieldLabel : '参数分类',
					anchor : '90%'
				
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					id : "parmName",
					name : 'parmName',
					hidden : true,
					allowBlank : false,
					fieldLabel : '参数名',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ comboxWithTree ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					Width : '100',
					name : 'parmNum',
					allowDecimals: true,
					decimalPrecision:6,
					allowBlank : false,
					fieldLabel : '参数值',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					store : parmLevelStore,
					xtype : 'combo',
					id : 'parmLeveladd',
					name : 'parmLevel',
					hiddenName : 'parmLevel',
					fieldLabel : '设置级别',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					allowBlank : false,
					width : '100',
					anchor : '90%'
				} ]
			}, {
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					name : 'brid',
					id : 'brid',
					allowBlank : false,
					hidden : true,
					fieldLabel : '设置机构',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				hidden : true,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					name : 'owenerid',
					id : 'owenerid',
					allowBlank : false,
					fieldLabel : '设置人',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					width : '100',
					name : 'datedt',
					id : 'datedt',
					fieldLabel : '最近修改日期',
					format : 'Y-m-d',
					readOnly : true,
					allowBlank : false,
					hidden : true,
					anchor : '90%'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '保  存',
			handler : function() {
				/*****设置参数级别，设置人ID，所属机构号，系统日期，并保存********/
				Ext.getCmp('parmLeveladd').setDisabled(false);
				if(unitlevel==1){
		        	Ext.getCmp('parmLeveladd').setValue(1);
		        }if(unitlevel==2){
		        	Ext.getCmp('parmLeveladd').setValue(2);
		        }
				Ext.getCmp('owenerid').setValue(userId);
				Ext.getCmp('brid').setValue(orgId);
				Ext.getCmp('datedt').setValue(new Date().format('Y-m-d'));
				
				/*******************判断是否存在同名参数******************/
				for(var i = 0; i<checkStore.totalLength ;i++){
					var temp = checkStore.getRange()[i].data;
		 			if(temp.parmName === Ext.getCmp('parmName').getValue()){
		 				Ext.Msg.alert('提示', '同名参数已存在，请重新选择!');
		 				return false;
		 			}
				};
				if (!addNewForm.getForm().isValid()) {
					Ext.Msg.alert('提示', '输入格式有误，请重新输入!');
					return false;
				}
				Ext.Ajax.request({
					url : basepath + '/AnalogProfParamMainten.json',
					method : 'POST',
					form : addNewForm.getForm().id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
						Ext.Msg.alert('提示', '操作成功!');
						addNewForm.getForm().reset();
						store.reload();
						checkStore.reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败!');
						addNewForm.getForm().reset();
					}
				});
				addNewFormWin.hide();
			}
		}, {
			text : '取  消',
			handler : function() {
			addNewFormWin.hide();
			}
		} ]
	
	});
	
	/*******************参数树******************/
	var parmTree = new Com.yucheng.bcrm.TreePanel({
		width:300,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			text:'全部目录',
			autoScroll:true,
	        expanded:true,
	        leaf:false,
			children:[]
		}),
		resloader: loader,
		split:true,
		clickFn:function(node){
			if(node.isLeaf()){
				Ext.getCmp('sectionCategory').setValue(node.text);
			    Ext.getCmp('parmName').setValue(node.id);
			    Ext.getCmp('parmType').setValue(node.parentNode.id);
			    comboxWithTree.collapse();
			}else{
				Ext.getCmp('sectionCategory').reset();
			    Ext.getCmp('parmName').reset();
			    Ext.getCmp('parmType').reset();
			}
		},
	    animate : false,
	    useArrows : false,
	    border : false
	});
		
	/*******************定义新增窗口******************/
	var addNewFormWin = new Ext.Window({
		title : '新增',
		plain : true,
		layout : 'fit',
		width : 500,
		height : 180,
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
		items : [ addNewForm ]
	});
	
	/**定义新增方法，并根据当前登录用户机构级别设置新增参数级别**/
	function addInit() {
        if(unitlevel==1){
        	Ext.getCmp('parmLeveladd').setValue("总行");
        }if(unitlevel==2){
        	Ext.getCmp('parmLeveladd').setValue("分行");
        }
        Ext.getCmp('parmLeveladd').setDisabled(true);
		addNewFormWin.show();
	};
	
	/*******************修改表单******************/
	var editForm = new Ext.form.FormPanel({
		id : 'mod',
		labelWidth : 100,
		height : 150,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [ {
			layout : 'column',
			labelAlign : 'right',
			labelWidth : 100,
			items : [ {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					store : classStore,
					xtype : 'combo',
					id : "parmTypemod",
					name : 'parmType',
					hiddenName : 'parmType',
					fieldLabel : '参数分类',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					disabled : true,
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					allowBlank : false,
					width : '100',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					name : 'parmName',
					id : "parmNamemod",
					allowBlank : false,
					disabled : true,
					fieldLabel : '参数名',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'numberfield',
					width : '100',
					id : 'parmNum',
					name : 'parmNum',
					allowDecimals: true,
					decimalPrecision:6,
					allowBlank : false,
					fieldLabel : '参数值',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					store : parmLevelStore,
					xtype : 'combo',
					id : 'parmLevelmod',
					name : 'parmLevel',
					hiddenName : 'parmLevel',
					fieldLabel : '设置级别',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					allowBlank : false,
					width : '100',
					anchor : '90%'
				} ]
			}, {
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					name : 'brid',
					id : 'bridmod',
					allowBlank : false,
					hidden : true,
					fieldLabel : '设置机构',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				hidden : true,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					id : 'oweneridmod',
					name : 'owenerid',
					allowBlank : false,
					fieldLabel : '设置人',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'datefield',
					width : '100',
					name : 'datedt',
					id : 'datedtmod',
					fieldLabel : '最近修改日期',
					format : 'Y-m-d',
					readOnly : true,
					allowBlank : false,
					hidden : true,
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					name : 'parmId',
					allowBlank : false,
					hidden : true,
					fieldLabel : '参数ID',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					id : "parmNameNum",
					name : 'parmNameNum',
					allowBlank : false,
					hidden : true,
					fieldLabel : '参数名值',
					anchor : '90%'
				} ]
			}, {
				columnWidth : 1,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					width : '100',
					id : "parmTypeNum",
					name : 'parmTypeNum',
					allowBlank : false,
					hidden : true,
					fieldLabel : '参数类型值',
					anchor : '90%'
				} ]
			} ]
		} ],
		buttons : [ {
			text : '保  存',
			handler : function() {
			/*****设置参数级别，设置人ID，所属机构号，系统日期，并保存********/
			Ext.getCmp('parmTypemod').setDisabled(false);
			Ext.getCmp('parmTypemod').setValue(Ext.getCmp('parmTypeNum').getValue());
			Ext.getCmp('parmNamemod').setDisabled(false);
			Ext.getCmp('parmNamemod').setValue(Ext.getCmp('parmNameNum').getValue());
			Ext.getCmp('parmLevelmod').setDisabled(false);
			if(unitlevel==1){
	        	Ext.getCmp('parmLevelmod').setValue(1);
	        }if(unitlevel==2){
	        	Ext.getCmp('parmLevelmod').setValue(2);
	        }
			Ext.getCmp('oweneridmod').setValue(userId);
			Ext.getCmp('bridmod').setValue(orgId);
			Ext.getCmp('datedtmod').setValue(new Date().format('Y-m-d'));
			if (!editForm.getForm().isValid()) {
				Ext.Msg.alert('提示', '输入格式有误，请重新输入!');
				return false;
				Ext.getCmp('parmTypemod').setDisabled(true);
				Ext.getCmp('parmNamemod').setDisabled(true);
			}
			Ext.Ajax.request({
				url : basepath + '/AnalogProfParamMainten.json',
				method : 'POST',
				form : editForm.getForm().id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Msg.alert('提示', '操作成功!');
					store.reload();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败!');
				}
			});
			Ext.getCmp('mod').getForm().getEl().dom.reset();
			Ext.getCmp('parmTypemod').setDisabled(true);
			Ext.getCmp('parmNamemod').setDisabled(true);
			editFormWin.hide();
			}
		}, {
			text : '取  消',
			handler : function() {
				Ext.getCmp('mod').getForm().getEl().dom.reset();
				Ext.getCmp('parmTypemod').setDisabled(true);
				Ext.getCmp('parmNamemod').setDisabled(true);
				editFormWin.hide();
			}
		} ]
	
	});
	
	/*******************定义修改窗口******************/
	var editFormWin = new Ext.Window({
		title : '修改',
		plain : true,
		layout : 'fit',
		width : 500,
		height : 220,
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
		items : [ editForm ]
	});
	
	/*******************定义修改方法******************/
	function editInit() {
		var infoRecord = grid.getSelectionModel().getSelected();
		var selectLength = grid.getSelectionModel().getSelections().length;
		if(selectLength > 1){
			alert('请选择一条记录!');
	    }else{
	        if(infoRecord == null||infoRecord == ''){
	            Ext.Msg.alert('提示','请选择一行数据');
	        } else{
		        editForm.getForm().loadRecord(infoRecord);
		        if(unitlevel==1){
		        	Ext.getCmp('parmLevelmod').setValue("总行");
		        }if(unitlevel==2){
		        	Ext.getCmp('parmLevelmod').setValue("分行");
		        }
		        Ext.getCmp('parmLevelmod').setDisabled(true);
		        editFormWin.show();
	        }
	    }
	};
	
	/*******************定义删除方法******************/
	function deleteInit(){
		/*******************获取所选列******************/
        var selectLength = grid.getSelectionModel().getSelections().length;
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } else {
            if(confirm("确定删除吗?")){
	            var selectRe;
	            var tempId;
	            var idStr = '';
	            for(var i = 0; i<selectLength;i++){
	                selectRe = grid.getSelectionModel()
	                .getSelections()[i];
	                tempId = selectRe.data.parmId;
	                idStr += tempId;
	                if( i != selectLength-1)
	                    idStr += ',';
	            }
	            Ext.Ajax.request({
	                url : basepath+'/AnalogProfParamMainten/'
	                        +tempId+'.json?idStr='+idStr,
	                method : 'DELETE',        
	                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                success : function() {
						Ext.Msg.alert('提示', '操作成功!');
						store.reload();
						checkStore.reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败!');
					}
	            });
            };
        }
    };
	
    /*******************查询结果显示******************/
	var grid = new Ext.grid.GridPanel({
		title : "模拟利润参数查询",
		store : store,
		frame : true,
		sm : sm,
		cm : columns,
		stripeRows : true,
		tbar : [{
			text : '新增',
			iconCls : 'addIconCss',
			handler : function() {
			addNewForm.getForm().reset();
			if(unitlevel==3){
				Ext.Msg.alert("提示","您无此权限！");
			}else{
				addInit();
			}}
		},'-',{
			text : '修改',
			iconCls : 'editIconCss',
			handler : function() {
			if(unitlevel==3){
				Ext.Msg.alert("提示","您无此权限！");
			}else{
				editInit();
			}}							
		},'-',{
			text : '删除',
			iconCls : 'deleteIconCss',
			handler : function() {
			if(unitlevel==3){
				Ext.Msg.alert("提示","您无此权限！");
			}else{
				deleteInit();
			}}							
		}],
		region : 'center',
		frame : true,
		bbar : bbar,// 分页工具栏
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	/*******************整体布局******************/
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items:[{
		layout:'border',
		items:[{
			region:'north',
			id:'north-panel',
			title:"客户管理->客户贡献度评价->模拟利润参数维护",
			height:'118',
			margins:'0 0 0 0',
			items:[qform]
		},{
			region:'center',
			id:'center-panel',
			layout:'fit',
			autoScroll:true,
			margins:'0 0 0 0',
			items:[grid]
		}]
		}]
	});
});