Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		//var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({
	        	url:basepath+'/acrmFCiSpeciallist.json',
	        	method:'GET'
	        }),
	        reader: new Ext.data.JsonReader({
	        	totalProperty : 'json.count',
	        	root:'json.data'
	        }, [
				{name:'CUST_ID',mapping:'CUST_ID'},
                {name:'CUST_NAME',mapping:'CUST_NAME'},
                {name:'IDENT_TYPE',mapping:'IDENT_TYPE_ORA'},
                {name:'IDENT_NO',mapping:'IDENT_NO'},
                {name:'SPECIAL_LIST_TYPE',mapping:'SPECIAL_LIST_TYPE_ORA'},
                {name:'SPECIAL_LIST_KIND',mapping:'SPECIAL_LIST_KIND_ORA'},
                {name:'SPECIAL_LIST_FLAG',mapping:'SPECIAL_LIST_FLAG_ORA'},
                {name:'ORIGIN',mapping:'ORIGIN'},
                {name:'STAT_FLAG',mapping:'STAT_FLAG'},
                {name:'START_DATE',mapping:'START_DATE'},
                {name:'END_DATE',mapping:'END_DATE'},
                {name:'ENTER_REASON',mapping:'ENTER_REASON'}
			])
		});
		store.load({params:{'instanceId':instanceid}});
		 //复选框
		var sm = new Ext.grid.CheckboxSelectionModel();

		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		//fields:['CUST_ID','CUST_NAME','IDENT_TYPE','IDENT_NO','SPECIAL_LIST_TYPE','SPECIAL_LIST_KIND','SPECIAL_LIST_FLAG','ORIGIN','STAT_FLAG','APPROVAL_FLAG','START_DATE','END_DATE'],
		
		// 定义列模型
		var cm = new Ext.grid.ColumnModel([rownum,sm, 
	         	{ header : '客户编号',dataIndex : 'CUST_ID', sortable : 100, width : 100},
             	{ header : '客户名称', dataIndex : 'CUST_NAME', sortable : true, width : 120 },
                { header : '证件类型', dataIndex : 'IDENT_TYPE', sortable : true, width : 120 },
                { header : '证件号码', dataIndex : 'IDENT_NO', sortable : true, width : 120},
                { header : '特殊名单类型', dataIndex : 'SPECIAL_LIST_TYPE',sortable : true, width : 100 },
                { header : '特殊名单类别',dataIndex : 'SPECIAL_LIST_KIND',sortable : true, width : 135},
                { header : '特殊名单标志',dataIndex : 'SPECIAL_LIST_FLAG',sortable : true, width : 135},
                { header : '数据来源',dataIndex : 'ORIGIN',sortable : true, width : 100},
                { header : '状态标志',dataIndex : 'STAT_FLAG',sortable : true, width : 100},
                { header : '起始日期',dataIndex : 'START_DATE',sortable : true, width : 100},
                { header : '结束日期',dataIndex : 'END_DATE',sortable : true, width : 100},
                { header : '列入原因',dataIndex : 'ENTER_REASON',sortable : true, width : 135}
		  
		]);
		// 表格实例
		var grid = new Ext.grid.GridPanel({
					id:'viewgrid',
					frame : true,
					height:100,
					autoScroll : true,
					region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
					store : store, // 数据存储
					stripeRows : true, // 斑马线
					cm : cm, // 列模型
					//sm : sm, // 复选框
					viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[grid]
	   }); 
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			  frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
//		function loadFormData(){
//    		infoForm.getForm().loadRecord(store.getAt(0));
//		}
		
				
	});
