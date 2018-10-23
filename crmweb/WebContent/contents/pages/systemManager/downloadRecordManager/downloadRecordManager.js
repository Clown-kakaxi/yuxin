/**
 * 报表下载管理
 * @author CHANGZH
 * @since 2013-05-22
 */
Ext.onReady(function() {
	var rownum = new Ext.grid.RowNumberer({//定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var downloadDetailPanel = new Ext.form.FormPanel({//显示查询结果Grid
		frame : true,
		region : 'north',
		hiddendownloadBtn : false,
		viewConfig : { // 强制fit,禁用滚动条
			forceFit : true,
			autoScroll : false
		}
	});
	
	var downloadQueryForm = new Ext.form.FormPanel({//查询Form
	    labelWidth : 90,
//	    height : 120,
	    frame : true,
	    region : 'north',
	    labelAlign : 'middle',
	    buttonAlign : 'center',
	    downloadCondition : "",
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
					fieldLabel : '开始时间',
					name : 'START_TIME1',
					xtype : 'datefield',
					format : 'Y-m-d H:i:s ',
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
					fieldLabel : '到',
					name : 'START_TIME2',
					xtype : 'datefield',
					format : 'Y-m-d H:i:s ',
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
					fieldLabel : '完成时间',
					name : 'END_TIME1',
					xtype : 'datefield',
					format : 'Y-m-d H:i:s ',
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
				fieldLabel : '到',
				name : 'END_TIME2',
				xtype : 'datefield',
				format : 'Y-m-d H:i:s ',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		},{
			    columnWidth : .25,
				layout : 'form',
				labelWidth : 80,
				border : false,
				items : [new Ext.form.ComboBox({
					fieldLabel : '当前下载状态',
					store : new Ext.data.SimpleStore({
						fields: ['value','key'],
						data : [['下载中...','1'],['下载完成','0'],['进程被结束','3']]
					}),
					mode: 'local',
					//readOnly : true,
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					name:'THREAD_STATUS',
					hiddenName:'THREAD_STATUS',
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
	         	var conditionStr = downloadQueryForm.getForm().getValues();
				store.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				store.load({
		      		params : {
				    	start : 0,
				    	limit : parseInt(pagesize_combo.getValue())
				    }
		      	});
			}
		}, {
			text : '重置',
			handler : function() {// 重置方法，查询表单重置
				downloadQueryForm.getForm().reset();
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
		pageBar.pageSize = parseInt(pagesize_combo.getValue()), store.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	var multiSm = new Ext.grid.CheckboxSelectionModel();//复选框
	
	var downloadColumns = new Ext.grid.ColumnModel([rownum, //multiSm,//列模型 
		{header : 'ID',dataIndex : 'id',sortable : true,hidden : true,width : 120},
		{header : '下载人Id',dataIndex : 'userId',sortable : true,width : 100},
		{header : '下载人',dataIndex : 'userName',sortable : true,width : 100},
		{header : '下载菜单',dataIndex : 'menuName',sortable : true,width : 100},
		{header : '下载文件名称',dataIndex : 'fileName',sortable : true,width : 100},
		{header : '开始时间',dataIndex : 'startTime',sortable : true,width : 100,
			renderer : function(value){//截取时间，只保留“年-月-日 时：分：秒”
			return value.toString().substring(0,19);
		}},
		{header : '完成时间',dataIndex : 'finishTime',sortable : true,width : 100,
			renderer : function(value){//截取时间，只保留“年-月-日 时：分：秒”
			return value.toString().substring(0,19);
		}},
		{header : '耗时(分钟)',dataIndex : 'useTime',sortable : true,hidden : true, width : 100},
		{header : '当前状态',dataIndex : 'downloadStatus',hidden : false,sortable : true,width : 100,
			renderer : function(value){
			if(value == '0')
				return '下载完成';
			else if(value == '1')
				return '下载中...';
			else if(value == '2')
				return '等待';
			else if(value == '3')
				return '进程被结束';
			else
				return '状态未知';
		}}
		]);
	var downloadQueryRecord = Ext.data.Record.create([
		{name : 'id',       mapping : 'ID'},
		{name : 'fileName',	mapping : 'FILENAME'},
		{name : 'userId',	mapping : 'USER_ID'},
		{name : 'userName',	mapping : 'USER_NAME'},
		{name : 'menuName',	mapping : 'MENU_NAME'},
		{name : 'startTime',	mapping : 'START_TIME'},
		{name : 'finishTime',	mapping : 'FINISH_TIME'},
		{name : 'useTime',	mapping : 'USE_TIME'},
		{name : 'downloadStatus',	mapping : 'THREAD_STATUS'}]);
	var store = new Ext.data.Store({//查询数据源
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/DownloadRecordAction.json',
			method : 'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, downloadQueryRecord)
	});
	var pageBar = new Ext.PagingToolbar({//分页工具栏
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});

	var downloadGrid = new Ext.grid.GridPanel({//显示查询结果Grid
		store : store,
		frame : true,
		sm : multiSm,
		cm : downloadColumns,
		stripeRows : true,
		region : 'center',
		frame : true,
		tbar : [{
			   		text : '导出',
			   		disabled:JsContext.checkGrant('EXPORT'),
			   		iconCls : 'importIconCss',
			   		handler : function() {
						var selectLength = downloadGrid.getSelectionModel().getSelections().length;
			            if (selectLength != 1) {
			            	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
			            	return;
			            }
			            selectRe = downloadGrid.getSelectionModel().getSelections()[0];
			            
			            if(selectRe.json.USER_ID != JsContext._userId)
			            {
			            	Ext.Msg.alert('提示','只能导出自己的文件');
			            	return false;
			            }
			            
	        			var taskStatus = selectRe.json.THREAD_STATUS;
			            if (taskStatus != '0') {
			            	Ext.Msg.alert('提示', '请选择<font color="red">下载完成的记录</font>!');
			            	return;
			            }
			            var filename =  selectRe.json.FILENAME;
			            Ext.Ajax.request({
							url : basepath + '/DownloadRecordAction!exportFile.json?fileName='+filename,
							method:'GET',
							success:function(response){
								var res = Ext.util.JSON.decode(response.responseText);
								if(res.filename){
									
									window.location.href = basepath+'/FileDownload?filename='+res.filename;
		                        }
							},
		    				failure:function(){
								Ext.Msg.alert('提示', '导出失败!');
		    				}
						});
			            
					}
				},{
					text : '结束下载',
					iconCls : 'endCss',
					disabled:JsContext.checkGrant('STOP_T'),
					hidden : true,
					id : 'endBtn',
					handler : function() {
						var selectLength = downloadGrid.getSelectionModel().getSelections().length;
			            if (selectLength != 1) {
			            	Ext.Msg.alert('提示', '请选择<font color="red">一条记录</font>!');
			            	return;
			            }
			            var selectRe = downloadGrid.getSelectionModel().getSelections()[0];			            
	        			var threadId = selectRe.json.THREAD_ID;
	        			var threadStatus = selectRe.json.THREAD_STATUS;
	        			
			            if (threadStatus != '1') {
			            	Ext.Msg.alert('提示', '请选择<font color="red">一条下载中的记录</font>!');
			            	return;
			            }
						Ext.Ajax.request({
							url : basepath + '/DownloadRecordAction!stopDownloadThread.json?threadId='+threadId,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							scope : this,
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								store.reload();
							}
						});
					}
				},{
					text : '删除',
					iconCls : 'deleteIconCss',
					disabled:JsContext.checkGrant('DELETE'),
					id : 'delBtn',
					hidden : true,
					handler : function() {
						var selectLength = downloadGrid.getSelectionModel().getSelections().length;
			            if (selectLength < 1) {
			            	Ext.Msg.alert('提示', '请选择要删除的记录</font>!');
			            	return;
			            }			            
			            var selectRe;
		        		var tempId;
		        		var idStr = '';
		        		for (var i = 0; i<selectLength;i++){//循环获取所选列id并拼装成字符串
		        			selectRe = downloadGrid.getSelectionModel().getSelections()[i];
		        			var taskStatus = selectRe.json.THREAD_STATUS;
//				            if (taskStatus != '0') {
//				            	Ext.Msg.alert('提示', '请选择<font color="red">下载完成的记录</font>!');
//				            	return;
//				            }
		        			tempId = selectRe.json.ID;
		        			idStr += tempId;
		        			if( i != selectLength-1){
		        				idStr += ',';
		        			}
		        		}
						Ext.Ajax.request({
							url : basepath + '/DownloadRecordAction!delFile.json?id='+idStr,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							scope : this,
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								store.reload();
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
		
	store.load({
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
    	        title : "下载中心",
    	        height : 130,
    	        margins : '0 0 0 0',
    	        items : [downloadQueryForm]
    	    }, {
    	        region : 'center',
    	        layout:'fit',
    	        autoScroll : true,
    	        margins : '0 0 0 0',
    	        items : [downloadGrid]
    	    }]
	    }]
	});
});