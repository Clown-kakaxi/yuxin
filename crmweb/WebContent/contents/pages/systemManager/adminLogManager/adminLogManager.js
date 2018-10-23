/**
 * 日志管理
 * @author weijl
 * @since 2012-09-20
 */
Ext.onReady(function() {
	
	/**
	 * 删除日志方法
	 */
	function deleteInit(){
		
        var selectLength = logGrid.getSelectionModel().getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的日志!');
        } else {
        	if(confirm("确定删除吗?")){
        		var selectRe;
        		var tempId;
        		var idStr = '';
        		for (var i = 0; i<selectLength;i++){//循环获取所选列id并拼装成字符串
        			selectRe = logGrid.getSelectionModel().getSelections()[i];
        			tempId = selectRe.data.id;
        			idStr += tempId;
        			if( i != selectLength-1){
        				idStr += ',';
        			}
        		}
        		Ext.Ajax.request({
        			url : basepath+'/AdminLogManagerAction/'+tempId+'.json?idStr='+idStr,
        			method : 'DELETE',        
        			waitMsg : '正在删除数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        			success : function() {
        				Ext.Msg.alert('提示', '操作成功!');
        				logQueryStore.reload();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败!');
					}
        		});
            }
        }
    }
	
	/*******************事件类型数据源******************/
	var logTypeStore = new Ext.data.Store({
		
		sortInfo: {
	    	field: 'key',
	    	direction: 'ASC' // or 'DESC' ,按照正序/逆序排序
		},
		restful:true,
		autoLoad : true,//默认自动加载
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/AdminLogType.json'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	/*******************时间排序数据源******************/
	var sortStore = new Ext.data.ArrayStore({
		fields:['key','value'],
		data : [['1','时间顺序'],['2','时间逆序']]
	});
	
	/*******************查询面板******************/
	var logQueryForm = new Ext.form.FormPanel({
		labelWidth:90,
		height:110,
		frame:true,
		labelAlign:'middle',
		buttonAlign:'center',
			layout:'column',
			items:[{
				columnWidth:.25,
				layout:'form',
				items:[new Ext.form.ComboBox({
                    xtype:'combo',
                    fieldLabel : '事件类型',
                    name: 'logType' ,
                    hiddenName : 'logType' ,
                    editable : false,
                    resizable:true,
                    forceSelection : true,
                    labelStyle: 'text-align:right;',
                    triggerAction:'all',
                    mode:'local',
                    store:logTypeStore,
                    valueField:'key',
                    displayField:'value',
                    emptyText:'请选择',
                    anchor : '90%'
               }),new Ext.form.ComboBox({
                   xtype:'combo',
                   fieldLabel : '时间排序',
                   name: 'timesort' ,
                   hiddenName : 'timesort' ,
                   value:'2',
                   editable : false,
                   resizable:true,
                   forceSelection : true,
                   labelStyle: 'text-align:right;',
                   triggerAction:'all',
                   mode:'local',
                   store:sortStore,
                   valueField:'key',
                   displayField:'value',
                   emptyText:'请选择',
                   anchor : '90%'
              })]
			},{
				columnWidth:.25,
				layout:'form',
				items:[new Com.yucheng.crm.common.OrgUserManage({
					fieldLabel : '操作用户',
					id : 'userName',
					labelStyle: 'text-align:right;',
					name:'userName',
					hiddenName:'userId',
					//searchRoleType:('127,47'),  //指定查询角色属性
					searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:false,
					anchor : '90%'
				})]
			
			},{
				columnWidth:.25,
				layout:'form',
				items:[{
					xtype : 'datefield',
					fieldLabel : '开始时间',
					format:'Y-m-d',
					name:'startTime',
					labelStyle: 'text-align:right;',
					anchor : '90%'
				}]
			},{
				columnWidth:.25,
				layout:'form',
				items:[{
					xtype : 'datefield',
					fieldLabel : '截止时间',
					format:'Y-m-d',
					name:'endTime',
					labelStyle: 'text-align:right;',
					anchor : '90%'
			}]
		},{
				columnWidth:.25,
				layout:'form',
				items:[]
		}],
		buttons:[{
			text : '查询',
			handler : function() {//查询方法，包含起始时间与截止时间的判断
			
				var starttime = Date.parseDate(logQueryForm.getForm().findField("startTime").value,"Y-m-d");
				var endtime = Date.parseDate(logQueryForm.getForm().findField("endTime").value,"Y-m-d");
				if(endtime < starttime){
					Ext.Msg.alert("提示","截止时间不能早于开始时间！");
					return false;
				}
				var conditionStr = logQueryForm.getForm().getFieldValues();
				
				logQueryStore.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				
				logQueryStore.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			}
		},{
			text:'重置',
			handler:function(){//重置方法，查询表单重置
				logQueryForm.getForm().reset();
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
		pageBar.pageSize = parseInt(pagesize_combo.getValue()),
		logQueryStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});

	/*******************复选框******************/
	var multiSm = new Ext.grid.CheckboxSelectionModel();
	
	/*******************定义自动当前页行号******************/
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	/*******************列模型******************/
	var logColumns = new Ext.grid.ColumnModel([rownum,multiSm,{
		header : 'id',
		dataIndex : 'id', 
		sortable : true,
		hidden : true,
		width : 120
	},{
        header : '操作用户',
        dataIndex : 'userName', 
        sortable : true,
        width : 150
    },{
        header : '操作信息',
        dataIndex : 'content', 
        sortable : true,
        width : 220
    },{
        header : '参数',
        dataIndex : 'afterValue',
        sortable : true,
        width : 220
    },{
		header : '登陆IP地址', 
		dataIndex : 'loginIp', 
		sortable : true,
		width : 150
	},{
		header : '时间', 
		dataIndex : 'operTime', 
		renderer : function(value){//截取时间，只保留“年-月-日 时：分：秒”
			return value.toString().substring(0,19);
		},
		sortable : true,
		width : 150
	}]);

	var logQueryRecord = Ext.data.Record.create([ 
	    {name: 'id', mapping: 'ID'},
	    {name: 'userName', mapping: 'USERNAME'},
	    {name: 'content', mapping: 'CONTENT'},
		{name: 'afterValue', mapping: 'AFTER_VALUE'},
		{name: 'loginIp', mapping: 'LOGIN_IP'},
		{name: 'operTime', mapping: 'OPER_TIME'}
		]);
	
	/*******************查询数据源******************/
	var logQueryStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/AdminLogQuery.json',
			method:'GET'
		}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
	        idProperty: 'ID',
	        messageProperty: 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, logQueryRecord)
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
		store : logQueryStore,
		frame : true,
		sm : multiSm,
		cm : logColumns,
		stripeRows : true,
		region : 'center',
		frame : true,
		tbar : [new Com.yucheng.bob.ExpButton({ //导出按钮
		            formPanel : logQueryForm,
		            iconCls:'exportIconCss',
		            url : basepath + '/AdminLogQuery.json'
				}),'-',
				{
					text : '删除',
					iconCls:'deleteIconCss',
					handler : function() { //调用查询方法
						deleteInit();
					}							
				}],
		bbar : pageBar, // 分页工具栏
		viewConfig : { //强制fit,禁用滚动条
		 	forceFit:true,
		 	autoScroll:false
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	/*******************整体显示布局******************/
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [{
			layout:'border',
			items:[{
				region:'north',
				id:'north-panel',
				title:"日志管理",
				height:138,
				margins:'0 0 0 0',
				items:[logQueryForm]
			},{
				region:'center',
				id:'center-panel',
				layout:'fit',
				autoScroll:true,
				margins:'0 0 0 0',
				items:[logGrid]
			}]
		}]
	});
});