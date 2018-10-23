/**
 * 报表授权
 * @author chixini 
 * @since 2014-8-18
 */

Ext.onReady(function() {
	
	/*******************复选框******************/
	var multiSm = new Ext.grid.CheckboxSelectionModel();
	var multiSmTwo = new Ext.grid.CheckboxSelectionModel();
	
	/*******************定义自动当前页行号******************/
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	/*******************列模型******************/
	var logColumns = new Ext.grid.ColumnModel([rownum,
	   {header : 'ID',dataIndex : 'id', sortable : true,hidden : true,width : 120},
	   {header : '逻辑系统ID',dataIndex : 'appId',sortable : true,hidden : true,width : 120}, 
       {header : '角色编码',dataIndex : 'roleCode',hidden : true,sortable : true,width : 120},
       {header : '角色名称',dataIndex : 'roleName', sortable : true, width : 120//hidden : true,
    }]);
	   
	var logQueryRecord = Ext.data.Record.create([ 
	    {name: 'id', mapping: 'id'},
	    {name: 'appId', mapping: 'appId'},
	    {name: 'roleCode', mapping: 'roleCode'},
		{name: 'roleName', mapping: 'roleName'}
		]);
	
	var logReportColumns = new Ext.grid.ColumnModel([rownum,multiSm,
	    {header : 'ID',dataIndex : 'id', sortable : true, hidden : true, width : 120},
	    {header : '报表编码',dataIndex : 'reportCode', sortable : true, hidden : true,width : 120},
	    {header : '报表名称',dataIndex : 'reportName',sortable : true, width : 120
	    }]);
	
	var logReportColumnsTwo = new Ext.grid.ColumnModel([rownum,multiSmTwo,
  	    {header : 'ID',dataIndex : 'id', sortable : true,hidden : true,width : 120},
  	    {header : '报表编码',dataIndex : 'reportCode',sortable : true,hidden : true,width : 120},
  	    {header : '报表名称',dataIndex : 'reportName',sortable : true,width : 120
  	    }]);
	
	var logReportRecord = Ext.data.Record.create([ 
	    {name: 'id', mapping: 'id'},
	    {name: 'reportCode', mapping: 'reportCode'},
	    {name: 'reportName', mapping: 'reportName'},
	    {name: 'reportStatus', mapping: 'reportStatus'},
	    {name: 'reportUrl', mapping: 'reportUrl'},
	    {name: 'repotSort', mapping: 'reportSort'},
	    {name: 'creator', mapping: 'creator'},
	    {name: 'appId', mapping: 'appId'},
	    {name: 'reportDesc', mapping: 'reportDesc'}
	    ]);
	/*******************查询数据源******************/
	var logQueryStore = new Ext.data.Store({
        restful : true,
        proxy : new Ext.data.HttpProxy( {
        	url : basepath + '/ReportAuthAction!indexPage.json',
            failure : function(response) {
                var resultArray = Ext.util.JSON.decode(response.status);
                if (resultArray == 403) {
                    Ext.Msg.alert('提示', response.responseText);
                }
            }
            
        }),
        reader : new Ext.data.JsonReader( {
            successProperty : 'success',
            idProperty : 'id',
            messageProperty : 'message',
            root : 'json.data',
            totalProperty : 'json.count'
        }, logQueryRecord)
	});
	
	logQueryStore.reload();

	
	var logReportStore = new Ext.data.Store({
        restful : true,
        proxy : new Ext.data.HttpProxy( {
        	url : basepath + '/ReportAuthAction!indexReportPage.json',
            failure : function(response) {
                var resultArray = Ext.util.JSON.decode(response.status);
                if (resultArray == 403) {
                    Ext.Msg.alert('提示', response.responseText);
                }
            }
            
        }),
        reader : new Ext.data.JsonReader( {
            successProperty : 'success',
            idProperty : 'id',
            messageProperty : 'message',
            root : 'json.data',
            totalProperty : 'json.count'
        }, logReportRecord)
	});
	
	var logReportStoretTwo = new Ext.data.Store({
        restful : true,
        proxy : new Ext.data.HttpProxy( {
        	url : basepath + '/ReportAuthAction!indexReportPageTwo.json',
            failure : function(response) {
                var resultArray = Ext.util.JSON.decode(response.status);
                if (resultArray == 403) {
                    Ext.Msg.alert('提示', response.responseText);
                }
            }
            
        }),
        reader : new Ext.data.JsonReader( {
            successProperty : 'success',
            idProperty : 'id',
            messageProperty : 'message',
            root : 'json.data',
            totalProperty : 'json.count'
        }, logReportRecord)
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

	
	/*******************分页工具栏******************/
	var pageBar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : logQueryStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	
	 /*******************显示查询结果面板******************/

	var logGrid = new Ext.grid.GridPanel({
		listeners:{},
		store : logQueryStore,
		frame : true,
		cm : logColumns,
		stripeRows : true,
		region : 'center',
		frame : true,
		//bbar : pageBar, // 分页工具栏
		viewConfig : { //强制fit,禁用滚动条
		 	forceFit:true,
		 	autoScroll:false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	/*******************显示查询结果面板******************/
	var logReportGrid1 = new Ext.grid.GridPanel({
		title:"未授权列表",
		store : logReportStoretTwo,
		frame : true,
		sm : multiSmTwo,
		cm : logReportColumnsTwo,
		stripeRows : true,
		region : 'west',
		width:210,
		frame : true,
		
		//bbar : pageBar, // 分页工具栏
		viewConfig : { //强制fit,禁用滚动条
		 	forceFit:true,
		 	autoScroll:false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	
	/*******************显示授权面板******************/
	var logReportGrid2 = new Ext.grid.GridPanel({
		title:"已授权列表",
		store : logReportStore,
		frame : true,
		sm : multiSm,
		cm : logReportColumns,
		stripeRows : true,
		region : 'east',
		width:210,
		frame : true,
		//bbar : pageBar, // 分页工具栏
		viewConfig : { //强制fit,禁用滚动条
		 	forceFit:true,
		 	autoScroll:false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	
	/*******************查询面板******************/
	var logQueryForm = new Ext.form.FormPanel({
		frame:true,
		region:'center',
		layout: {
            type:'vbox',
            padding:'5',
            pack:'center',
            align:'center'
        },
        defaults:{margins:'0 0 5 0'},
		items:[{
            text : '  -->',
            xtype:"button",
            width:50,
            handler : function() {
			var selectedFlag = false;
    		var records = logReportGrid1.getSelectionModel().getSelections();
    		for(var i=0;i<records.length;++i)
    		{
    			selectedFlag=true;
    			var obj = {
					id : null,
					appId : 62,
					reportName : records[i].data.reportName,
					reportCode : records[i].data.reportCode							
				};
    			var r = new Ext.data.Record(obj,null);
    			logReportStore.addSorted(r);
    			logReportStoretTwo.remove(records[i]);
    		}
    		if(!selectedFlag)
    		{
    			Ext.Msg.alert('提示', '请选择要增加的记录</font>!');
    		}	
            }
        },{
			text:'  <--',
			xtype:"button",
			width:50,
			handler:function(){
        	   
        		var selectedFlag = false;
        		var records = logReportGrid2.getSelectionModel().getSelections();
        		
        		for(var i=0;i<records.length;++i)
        		{
        			logReportStore.remove(records[i]);
        			selectedFlag=true;
        			var obj = {
        					id : null,
        					appId : 62,
        					reportName : records[i].data.reportName,
        					reportCode : records[i].data.reportCode							
        				};
        			var r = new Ext.data.Record(obj,null);
        			logReportStoretTwo.addSorted(r);
        			
        		}
        		if(!selectedFlag)
        		{
        			Ext.Msg.alert('提示', '请选择要移除的记录</font>!');
        		}	
			}
		},{
			text:'-->>',
			xtype:"button",
			width:50,
			handler:function(){
				
				var records = logReportStoretTwo.getRange();
				
				for(var i=0;i<records.length;++i)
				{
					var obj = {
							id : null,
							appId : 62,
							reportName : records[i].data.reportName,
							reportCode : records[i].data.reportCode							
						};
					var r = new Ext.data.Record(obj,null);
					logReportStore.addSorted(r);
					logReportStoretTwo.remove(records[i]);
				}
		    }	
		},{
			text:'<<--',
			xtype:"button",
			 margins:'0',
			width:50,
			handler:function(){
			
			var records = logReportStore.getRange();
			for(var i=0;i<records.length;++i)
			{
				logReportStore.remove(records[i]);
				var obj = {
						id : null,
						appId : 62,
						reportName : records[i].data.reportName,
						reportCode : records[i].data.reportCode							
					};
				var r = new Ext.data.Record(obj,null);
				logReportStoretTwo.addSorted(r);
			}
			}		
		}]
	});
	
	/*******************查询面板******************/
	var logButtonGrid = new Ext.form.FormPanel({
		frame:true,
		region:'south',
		labelAlign:'center',
		buttonAlign:'center',
		layout:'form',
		buttons:[{
            text : '保存',
            id : 'logButtonGridBtn',
            disabled : true,
            handler : function() {
				var reportCodes = '';
				var records = logReportStore.getRange();
				for (var i = 0; i < records.length; i++){
				
					if (reportCodes === '') {
						reportCodes = records[i].data.reportCode;
					} else {
						reportCodes = reportCodes + ',' + records[i].data.reportCode;
					}
				}
				Ext.Ajax.request( {
					url : basepath + '/ReportAuthAction!saveAuth.json',
					method : 'POST',	
					params:{
					roleCode : logGrid.getSelectionModel().getSelected().data.roleCode,
					reportCodes : reportCodes
				},
				waitMsg : '正在添加数据中,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(){
					Ext.Msg.alert('提示', '操作成功!');
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败!');
				}
				});
				debugger;
		
            }
        },{
			text:'重置',
			id : 'logButtonGridBtn1',
	        disabled : true,
			handler:function(){//重置方法，查询表单重置
        	logReportStore.reload();
        	logReportStoretTwo.reload();
			}
		}]
	});
	
	/**logReportGrid 表操作*************end****/
	logGrid.on('cellclick',function(e){
		if (e.getSelectionModel().hasSelection()) {
			var rs =  e.getSelectionModel().getSelected();
			logReportStore.load({
				params:{
				selectCode : rs.data.roleCode
			},
			callback : function(){//控制数据查询过滤器tbar 显示用
				var len = logReportStore.data.length;
				Ext.getCmp('logButtonGridBtn').setDisabled(false);
				Ext.getCmp('logButtonGridBtn1').setDisabled(false);
			}
			});
			
			logReportStoretTwo.load({
				params:{
				selectCode : rs.data.roleCode
			}
			});
			
		}
	});
	
	/*******************整体显示布局******************/
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [{
			layout:'border',
			items:[{
				region:'west',
				id:'west-panel',
				title:"角色列表",
				width:298,
				layout:'fit',
				autoScroll:true,
				items:[logGrid]
			},{
				region:'center',
				id:'center-panel',
				layout:'border',
				autoScroll:true,  
				items:[logReportGrid1,logQueryForm,logReportGrid2,logButtonGrid]
					     
			}]
		}]
		
	});

	var setPanelsWidth = function () {
		var listWidth = (viewport.getWidth()- 298-70) /2;
		logReportGrid1.setWidth(listWidth);
		logReportGrid2.setWidth(listWidth);
	};
	setPanelsWidth();
	viewport.doLayout();
	Ext.EventManager.addListener(window, "resize", function() {
		setPanelsWidth();
	}); 

	
});