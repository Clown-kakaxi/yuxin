/**
 * 报表配置管理
 * @author CHANGZH
 * @since 2013-07-17
 */
Ext.onReady(function() {
	var rownum = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	//查询项类型
	var itemTypeStore = new Ext.data.SimpleStore({
		fields: ['key','value'],
		data : [['0','文本框'],['1','数值'],['2','日期'],['3','下拉框'],['4','弹出页面'],['5','多选下拉框']]
	});
	var YNStore = new Ext.data.SimpleStore({
		fields: ['key','value'],
		data : [['1','是'],['0','否']]
	});
	var serverStore = new Ext.data.SimpleStore({
		fields: ['key','value'],
		data : [['0','Cognos'],['1','MicroStategy'],['2','BIEE'],['3','润乾服务器']]
	});
	var cfgrownum = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	var reportDetailPanel = new Ext.form.FormPanel({//显示查询结果Grid
		frame : true,
		region : 'north',
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		}
	});
	
	var reportQueryForm = new Ext.form.FormPanel({//查询Form
	    labelWidth : 90,
	    height : 80,
	    frame : true,
	    region : 'north',
	    labelAlign : 'middle',
	    buttonAlign : 'center',
	    items : [{
	        layout : 'column',
			border : false,
			items : [{
			    columnWidth : .25,
				defaultType : 'textfield',
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [{
					fieldLabel : '报表编码',
					name : 'REPORT_CODE',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					anchor : '90%'
				}]
			},{
			    columnWidth : .25,
				defaultType : 'textfield',
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [{
					fieldLabel : '报表名称',
					name : 'REPORT_NAME',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					anchor : '90%'
				}]
			},{
			    columnWidth : .25,
				defaultType : 'textfield',
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [new Ext.form.ComboBox({
					fieldLabel : '报表状态',
					store : new Ext.data.SimpleStore({
						fields: ['value','key'],
						data : [['未发布','0'],['已发布','1'],['已停用','2']]
					}),
					mode: 'local',
					//readOnly : true,
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					name:'REPORT_STATUS',
					hiddenName:'REPORT_STATUS',
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					//typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
			}]
		}],
		buttons : [{
			text : '查询',
			handler : function() {
	         	var conditionStr = reportQueryForm.getForm().getValues();
	         	reportStore.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
	         	reportStore.load({
		      		params : {
				    	start : 0,
				    	limit : parseInt(pagesize_combo.getValue())
				    }
		      	});
			}
		}, {
			text : '重置',
			handler : function() {// 重置方法，查询表单重置
				reportQueryForm.getForm().reset();
			}
		}]
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

	pagesize_combo.on("select", function(comboBox) {//改变每页显示条数reload数据
		pageBar.pageSize = parseInt(pagesize_combo.getValue()), reportStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var multiSm = new Ext.grid.CheckboxSelectionModel();//复选框
	var cfgmultiSm = new Ext.grid.CheckboxSelectionModel();//复选框
	
	var reportColumns = new Ext.grid.ColumnModel([rownum, //multiSm,//列模型 
		{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 70},
		{header : '报表状态',dataIndex : 'reportStatus',sortable : true,width : 60,
			renderer : function(value){
			if(value == '0')
				return '未发布';
			else if(value == '1')
				return '已发布';
			else if(value == '2')
				return '已停用';
			else
				return '状态未知';
		}},
		{header : '报表编码',dataIndex : 'reportCode',sortable : true,width : 60},
		{header : '报表名称',dataIndex : 'reportName',sortable : true,width : 100},
		{header : '服务器类型',dataIndex : 'reportServerType',sortable : true,width : 80,
				renderer : function(v){
				if (v == '0') 
					return 'Cognos';
				else if (v == '1') 
					return 'MicroStategy';
				else if (v == '2') 
					return 'BIEE';
				else if (v == '3')
					return '润乾服务器';
				else
					return v;
			}},
		{header : '报表URL',dataIndex : 'reportUrl',sortable : true,width : 200},
		{header : '报表排序',dataIndex : 'reportSort',hidden : true,width : 80},
		{header : '报表类型',dataIndex : 'reportType',hidden : true,width : 120},
		{header : '备注',dataIndex : 'reportDesc',sortable : true,width : 100}]);
	var reportQueryRecord = Ext.data.Record.create([
		{name : 'id',       mapping : 'ID'},
		{name : 'reportCode',	mapping : 'REPORT_CODE'},
		{name : 'reportName',	mapping : 'REPORT_NAME'},
		{name : 'reportUrl',	mapping : 'REPORT_URL'},
		{name : 'reportServerType',	mapping : 'REPORT_SERVER_TYPE'},
		{name : 'reportStatus',	mapping : 'REPORT_STATUS'},
		{name : 'reportSort',	mapping : 'REPORT_SORT'},
		{name : 'reportGroup',	mapping : 'REPORT_GROUP'},
		{name : 'reportType',	mapping : 'REPORT_TYPE'},
		{name : 'reportDesc',	mapping : 'REPORT_DESC'}]);
	var reportStore = new Ext.data.Store({//查询数据源
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/ReportCfgAction.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, reportQueryRecord)
	});
	
	var reportCfgColumns = new Ext.grid.ColumnModel([cfgrownum, cfgmultiSm,//列模型 
  		{header : 'ID',dataIndex : 'id',sortable : false,hidden : true,readOnly : true,width : 120},
  		{header : '查询项ID',dataIndex : 'conditionField',sortable : false,width : 75,
	  				editor : new Ext.form.TextField(
	  				{
	  					allowBlank: false
	  				})},
  		{header : '查询项名称',dataIndex : 'conditionName',sortable : false,width : 100,
  					editor : new Ext.form.TextField(
  	  				{
  	  					allowBlank: false
  	  				})},
  		{header : '查询项类型',dataIndex : 'conditionType',sortable : false,width : 85,
  	  				editor : new Ext.form.ComboBox(
  	  				{
  	  					allowBlank: false,
  	  					mode : 'local',
  	  					store: itemTypeStore,
  	  					triggerAction : 'all',
  	  					displayField:'value',  	  					
  	  					valueField:'key'
  	  				}),
  	  				renderer : function(v){
	  						if (v == '0') 
	  							return '文本框';
	  						else if (v == '1') 
	  							return '数值';
	  						else if (v == '2') 
	  							return '日期';
	  						else if (v == '3') 
	  							return '下拉框';
	  						else if (v == '4') 
	  							return '弹出页面';
	  						else if (v == '5') 
	  							return '多选下拉框';
	  						else 
	  							return v;
	  					}},
  		{header : '必填项',dataIndex : 'isAllowBlank',sortable : false,width : 65,
	  						editor : new Ext.form.ComboBox(
	  			  	  				{
	  			  	  					allowBlank: false,
	  			  	  					mode : 'local',
	  			  	  					store: YNStore,
	  			  	  					triggerAction : 'all',
	  			  	  					displayField:'value',  	  					
	  			  	  					valueField:'key'
	  			  	  				}),
	  			  	  				renderer : function(v){
	  				  						if (v == '1') 
	  				  							return '是';
	  				  						else if (v == '0') 
	  				  							return '否';
	  				  						else 
	  				  							return v;
	  				  					}},
		{header : '隐藏项',dataIndex : 'isHidden',sortable : false,width : 65,
	  				  					editor : new Ext.form.ComboBox(
	  			  			  	  				{
	  			  			  	  					allowBlank: false,
	  			  			  	  					mode : 'local',
	  			  			  	  					store: YNStore,
	  			  			  	  					triggerAction : 'all',
	  			  			  	  					displayField:'value',  	  					
	  			  			  	  					valueField:'key'
	  			  			  	  				}),
	  			  			  	  				renderer : function(v){
	  			  				  						if (v == '1') 
	  			  				  							return '是';
	  			  				  						else if (v == '0') 
	  			  				  							return '否';
	  			  				  						else 
	  			  				  							return v;
	  			  				  					}},
		{header : '默认值',dataIndex : 'conditionDefault',sortable : false,width : 120,
			editor : new Ext.form.TextField(
			{
				allowBlank: true
			})}
	  ]);
  	var reportCfgRecord = Ext.data.Record.create([
  		{name : 'id',       mapping : 'ID'},
  		{name : 'conditionField',	mapping : 'conditionField'},
  		{name : 'conditionName',	mapping : 'conditionName'},
  		{name : 'conditionType',	mapping : 'conditionType'},
  		{name : 'conditionDefault',	mapping : 'conditionDefault'},
  		{name : 'reportServerType',	mapping : 'reportServerType'},
  		{name : 'isAllowBlank',	mapping : 'isAllowBlank'},
  		{name : 'isHidden',	mapping : 'isHidden'}]);
	                                      	
	var reportCfgStore = new Ext.data.Store({//查询数据源
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/ReportCfgAction!getCfgItems.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, reportCfgRecord)
	});
	var pageBar = new Ext.PagingToolbar({//分页工具栏
		pageSize : parseInt(pagesize_combo.getValue()),
		store : reportStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});
	
	var updateStatus = function(msg, status, id) {
		Ext.Ajax.request({
			url : basepath + '/ReportCfgAction!updateReportStatus.json?reportStatus='+status+'&id='+id,
			method:'GET',
			success:function(response){
				reportStore.reload();
        		Ext.Msg.alert('提示', msg+'成功.');
			},
			failure:function(){
				Ext.Msg.alert('提示', msg+'失败.');
			}
		});
	};
	
	var checkSelected = function() {
		var selectLength = reportGrid.getSelectionModel().getSelections().length;
        if (selectLength != 1) {
        	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
        	return;
        } else {
        	 var selectRe = reportGrid.getSelectionModel().getSelections()[0];
        	 return selectRe;
        }
	};
	 var teamStatusStore = new Ext.data.ArrayStore( {
         fields : [ 'key', 'value' ],
         data : [ [ '0', '配置' ], [ '1', '自定义' ] ]
     });

	 
	 var boxstore = new Ext.data.Store({
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=reportGroup'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});

	 
	var reportPanel = new Ext.form.FormPanel({
		height : 150,
		frame : true,
		border : false,
		labelAlign:'middle',
		region : 'north',
		layout : 'form',
		margins : '0 0 0 0',
		items : [{
			layout : 'column',
			items :[{
				columnWidth:.5,
				layout:'form',
				items:[{
					xtype : 'textfield',
					fieldLabel : '报表编码',
					allowBlank : false,
					name:'reportCode',
					align : 'center',
					labelStyle: 'text-align:right;',
					anchor : '90%'
				},{
					xtype : 'combo',
					fieldLabel : '报表服务器',
					name:'reportServerType',
					hiddenName:'reportServerType',
					allowBlank : false,
					labelStyle: 'text-align:right;',
					mode : 'local',
					store : serverStore,
					displayField : 'value',
					forceSelection : true,
                    valueField : 'key',
                    emptyText : '请选择',
                    triggerAction : 'all',//all selections
					anchor : '90%'
				}]
			},{
				columnWidth:.5,
				layout:'form',
				items:[{
					xtype : 'textfield',
					fieldLabel : '报表名称',
					name:'reportName',
					labelStyle: 'text-align:right;',
					allowBlank : false,
					anchor : '90%'
				},{
					xtype : 'numberfield',
					fieldLabel : '报表排序',
					name:'reportSort',
					allowBlank : true,
					labelStyle: 'text-align:right;',
					anchor : '90%'
				}]
			},{
				columnWidth:.99,
				layout:'form',
				items:[{
					xtype : 'textfield',
					fieldLabel : '报表URL',
					allowBlank : false,
					name:'reportUrl',
					labelStyle: 'text-align:right;',
					anchor : '96%'
				}]
			},{
				columnWidth:.5,
				layout:'form',
				items:[{
					xtype : 'combo',
					fieldLabel : '报表组',
					name:'reportGroup',
					hiddenName:'reportGroup',
					labelStyle: 'text-align:right;',
					mode : 'local',
					store : boxstore,
					displayField : 'value',
                    valueField : 'key',
                    emptyText : '请选择',
                    triggerAction : 'all',//all selections
					allowBlank : true,
					anchor : '90%'
				}]
			},{
				columnWidth:.5,
				layout:'form',
				items:[{
					xtype : 'combo',
					fieldLabel : '查询条件类型',
					name:'reportType',
					hiddenName:'reportType',
					labelStyle: 'text-align:right;',
					mode : 'local',
					store : teamStatusStore,
					displayField : 'value',
					value : '0',
                    valueField : 'key',
                    emptyText : '请选择',
                    triggerAction : 'all',//all selections
					allowBlank : false,
					anchor : '90%'
				}]
			},{
				columnWidth:.5,
				layout:'form',
				items:[{
					name:'id',
					xtype : 'hidden'
				}]
			}]
		},{
			layout : 'column',
			items :[{
				columnWidth:.99,
				layout:'form',
				items:[{
					xtype : 'textarea',
					fieldLabel : '备注',
					height : 50,
					name:'reportDesc',
					labelStyle: 'text-align:right;',
					anchor : '96%'
				}]
			}]
		}]
	});
	/**
	 * 校验整个grid必填信息
	 * @author CHANGZH
	 **/
	var checkFeilds = function(grid) {
		if(!this.needCheckFields)
        {
            this.needCheckFields=
            {
            };
            for(var i = 0; i < grid.colModel.getColumnCount(); i++)
            {
                var oneCm = grid.colModel.getColumnById(grid.colModel.getColumnId(i));
                if(oneCm && oneCm.editor != null)
                {
                    if(oneCm.editor.allowBlank == false)
                    {
                        this.needCheckFields[oneCm.dataIndex] = i+1;
                    }
                }
            }
        }
        grid.stopEditing();
        var records = grid.store.getRange();
	    for(var i=0;i<records.length;++i)
	    {
	        if(records[i].data!=null)
	        {
	            for(var k in this.needCheckFields)
	            {
	                if(this.needCheckFields[k]&&((records[i].data)[k]===''||(records[i].data)[k]===null||(records[i].data)[k]===undefined))
	                {
	                	grid.selModel.selectRow(grid.store.indexOf(records[i]));
	                    grid.startEditing(grid.store.indexOf(records[i]),this.needCheckFields[k]-1);
	                    return false;
	                }
	            }
	        }
	    }
	    return true;
	};
	/** 
	 * @author CHANGZH
	 * 将Ext.Json.Store对象 
	 * 数组对象 
	 * 转换成Json形式的字符串 
	 * @param {} store 
	 * @return {} 
	 */  
	function storeToJson(jsondata){  
	    var listRecord;  
	    if(jsondata instanceof Ext.data.Store){  
	        listRecord = new Array();  
		    jsondata.each(function(record){  
		        listRecord.push(record.data);  
		    });  
	    }else if(jsondata instanceof Array){  
	        listRecord = new Array();  
	        Ext.each(jsondata,function(record){  
	            listRecord.push(record.data);  
	        });  
	    }  
	    return Ext.encode(listRecord);  
	}  

	var saveReportFun = function () {
		
		var paramObj = {};
		paramObj.reportPanel = reportPanel.getForm().getValues(false);
		paramObj.reportList  = reportCfgStore.data;
		Ext.Ajax.request({
			url : basepath + '/ReportCfgAction!saveReport.json',
			method : 'POST',
			params : {
				reportPanel :  Ext.encode(reportPanel.getForm().getValues(false)),
				//reportGroup :  
				reportList  :  storeToJson(reportCfgStore)
			},
			scope : this,
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			success : function() {
				Ext.Msg.alert('提示', '操作成功');
				detailWin.hide();
				reportStore.reload();
			},
			failure : function() {
				Ext.Msg.alert('提示', '操作失败');
			}
		});
	};
	var showReportWin = function (editFlag, loadFlag) {
		if(editFlag){
			Ext.getCmp('saveReport').setDisabled(false);
			Ext.getCmp('addRec').setDisabled(false);
			Ext.getCmp('removeRec').setDisabled(false);
		} else {
			Ext.getCmp('saveReport').setDisabled(true);
			Ext.getCmp('addRec').setDisabled(true);
			Ext.getCmp('removeRec').setDisabled(true);
		}
		detailWin.show();
		if(loadFlag) {
			var selectRe = reportGrid.getSelectionModel().getSelections()[0];
			reportPanel.getForm().loadRecord(selectRe);
			
			reportCfgStore.reload({
				params : {
					reportCode : selectRe.json.REPORT_CODE
				}
			});
		} else {
			reportPanel.getForm().reset();
			reportCfgStore.removeAll();
		}
	};
	var reportCfgGrid = new Ext.grid.EditorGridPanel({//显示查询结果Grid
		store : reportCfgStore,
		sm : cfgmultiSm,
		cm : reportCfgColumns,
		stripeRows : true,
		region : 'center',
		frame : true,
		tbar : [{
					text : '新增',
					id : 'addRec',
					handler : function() {
						var obj = {
								id : null,
								conditionField : null,
								conditionName : null,
								conditionType : '0',
								isAllowBlank : '0',
								isHidden : '0',
								conditionDefault : null								
							};
						var r = new Ext.data.Record(obj,null);
						reportCfgStore.addSorted(r);
					}
				},'-',{
					text : '移除',
					id : 'removeRec',
					handler : function() {					
						var selectedFlag = false;
	                    var records = reportCfgGrid.getSelectionModel().getSelections();
	                    for(var i=0;i<records.length;++i)
	                    {
	                    	reportCfgStore.remove(records[i]);
	                        selectedFlag=true;
	                    }
	                    if(!selectedFlag)
	                    {
	                    	Ext.Msg.alert('提示', '请选择要移除的记录</font>!');
	                    }					
					}
				}
			],
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		},
		loadMask : {
			msg : '正在加载数据,请稍等...'
		}
	});
	var detailWin = new Ext.Window({     
	    width : 650,
	    hideMode : 'offsets',
	    modal : true,
	    autoScroll : true,
	    height : 450,
	    closeAction:'hide',
	    title : "报表信息",
	    layout : 'fit',
	    tbar : [{
			text : '保存',
			id : 'saveReport',
			handler : function() {
				if(!reportPanel.getForm().isValid()){
					Ext.Msg.alert('提示', '请输入必填信息项');
                    return ;
				}
				if (!checkFeilds(reportCfgGrid)) {
					//Ext.Msg.alert('提示', '请输入必填信息项');
                    return ;
				}
				saveReportFun();
			}
		},'-',{
			text:'返回',
			id : 'saveReportCancel',
			handler:function(){
				detailWin.hide();
			}
		}],
	    items : [{
	    	layout : 'border',
	        items : [reportPanel, reportCfgGrid]
	    }]
	});	 
	
	var reportGrid = new Ext.grid.GridPanel({//显示查询结果Grid
		store : reportStore,
		frame : true,
		//sm : multiSm,
		cm : reportColumns,
		stripeRows : true,
		region : 'center',
		frame : true,
		tbar : [{
			   		text : '详情',
			   		iconCls : 'detailIconCss',
			   		handler : function() {
						var selectLength = reportGrid.getSelectionModel().getSelections().length;
				        if (selectLength != 1) {
				        	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
				        	return ;
				        }
						showReportWin(false, true);
					}
				},'-',{
					text : '新增',
					iconCls : 'addIconCss',
					id : 'addBtn',
					handler : function() {
						showReportWin(true, false);
					}
				},'-',{
					text : '修改',
					iconCls : 'editIconCss',
					id : 'updateBtn',
					handler : function() {
						var selectLength = reportGrid.getSelectionModel().getSelections().length;
						 
				        if (selectLength != 1) {
				        	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
				        	return ;
				        }
						var reportStatus = checkSelected().json.REPORT_STATUS;
			            if (reportStatus != '0') {
			            	Ext.Msg.alert('提示', '请选择<font color="red">状态为未发布的记录</font>!');
			            	return;
			            }
			           
						showReportWin(true, true);
					}
				},'-',{
					text : '发布',
					iconCls : 'publishIconCss',
					id : 'publishBtn',
					handler : function() {
						var selectLength = reportGrid.getSelectionModel().getSelections().length;
				        if (selectLength != 1) {
				        	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
				        	return ;
				        }
	        			var reportStatus = checkSelected().json.REPORT_STATUS;
			            if (reportStatus != '0') {
			            	Ext.Msg.alert('提示', '请选择<font color="red">状态为未发布的记录</font>!');
			            	return;
			            }			            
			            updateStatus('发布','1',checkSelected().json.ID);
					}
				},'-',{
					text : '启用',
					iconCls : 'shenpiIconCss',
					id : 'undisableBtn',
					handler : function() {	
					var selectLength = reportGrid.getSelectionModel().getSelections().length;
				        if (selectLength != 1) {
				        	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
				        	return ;
				        }
	        			var reportStatus = checkSelected().json.REPORT_STATUS;
			            if (reportStatus != '2') {
			            	Ext.Msg.alert('提示', '请选择<font color="red">状态为已停用的记录</font>!');
			            	return;
			            }	
			            updateStatus('启用','1',checkSelected().json.ID);						
					}
				},'-',{
					text : '停用',
					iconCls : 'endCss',
					id : 'disableBtn',
					handler : function() {
						var selectLength = reportGrid.getSelectionModel().getSelections().length;
				        if (selectLength != 1) {
				        	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
				        	return ;
				        }
						var reportStatus = checkSelected().json.REPORT_STATUS;
			            if (reportStatus != '1') {
			            	Ext.Msg.alert('提示', '请选择<font color="red">状态为已发布的记录</font>!');
			            	return;
			            }	
			            updateStatus('停用','2',checkSelected().json.ID);
					}
				},'-',{
					text : '删除',
					iconCls : 'deleteIconCss',
					id : 'endBtn',
					handler : function() {

						var selectLength = reportGrid.getSelectionModel().getSelections().length;
			            if (selectLength < 1) {
			            	Ext.Msg.alert('提示', '请选择要删除的记录</font>!');
			            	return;
			            }
			            
			            var selectRe;
		        		var tempId;
		        		var idStr = '';
		        		for (var i = 0; i<selectLength;i++){//循环获取所选列id并拼装成字符串
		        			selectRe = reportGrid.getSelectionModel().getSelections()[i];
		        			tempId = selectRe.json.ID;
		        			idStr += tempId;
		        			if( i != selectLength-1){
		        				idStr += ',';
		        			}
		        		}
		        		
						Ext.Ajax.request({
							url : basepath + '/ReportCfgAction!delReport.json?id='+idStr,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							scope : this,
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								reportStore.reload();
							}
						});
				
					}
				}
			],
		bbar : pageBar, // 分页工具栏
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		},
		loadMask : {
			msg : '正在加载数据,请稍等...'
		}
	});
		
	reportStore.load({
  		params : {
	    	start : 0,
	    	limit : parseInt(pagesize_combo.getValue())
	    }
  	});
	
	
	
	
	var viewport = new Ext.Viewport({//整体显示布局
	    layout : 'fit',
	    items:[{
	        layout : 'border',
    	    items : [{
    	        region : 'north',
    	        title : "报表配置管理",
    	        height : 106,
    	        margins : '0 0 0 0',
    	        items : [reportQueryForm]
    	    }, {
    	        region : 'center',
    	        layout:'fit',
    	        autoScroll : true,
    	        margins : '0 0 0 0',
    	        items : [reportGrid]
    	    }]
	    }]
	});
});