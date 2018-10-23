Ext.onReady(function() {
	Ext.QuickTips.init();
	//var cust_id =oCustInfo.cust_id;
	var cust_id = oCustInfo.cust_id;
	//时间类型
	var timeTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CDE0100057'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	timeTypStore.load();
	//报表状态
	var repStatusStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CDE0100068'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	repStatusStore.load();
	//审计类型
	var adtTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CDE0100003'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	adtTypStore.load();
	//报表参数form
	var custFincFPanel = new Ext.FormPanel( {
		frame : true,
		autoScroll : true,
		//anchor: '100%',
		//autoWidth : true,
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		}, [{name : 'REP_TYP',mapping:'REP_TYP_ORA'},
		    {name : 'TIME_TYP',mapping:'TIME_TYP_ORA'},
		    {name : 'YEAR'},
		    {name : 'MONTH'},
		    {name : 'ADT_TYP'}]),
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .25,
				items : [ {id : 'repTyp',name : 'REP_TYP',xtype : 'textfield',fieldLabel : '报表类型',readOnly: true,labelStyle : 'text-align:right;',anchor : '95%'} ]
			},{
				layout : 'form',columnWidth : .25,
				items : [ {id : 'timeTyp',name : 'TIME_TYP',xtype : 'textfield',fieldLabel : '时间类型',readOnly: true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .25,
				items : [ {id : 'year',name : 'YEAR',xtype : 'textfield',fieldLabel : '时间参数',readOnly: true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .25,
				items : [ {id : 'adtTyp',name : 'ADT_TYP',xtype : 'textfield',fieldLabel : '审计类型',readOnly: true,labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	//var sm = new Ext.grid.CheckboxSelectionModel();							//选中行
	//var rownum = new Ext.grid.RowNumberer({ header : 'No.',width : 28 });  	//行号
	//资产负债列模型
	var zcfzCm = new Ext.grid.ColumnModel([
	       {
			 	dataIndex : 'ID',
			 	hidden : true
		   },
	       {
			 	header : '行次',
			 	dataIndex : 'MYSORT',
			 	sortable : true,
			 	width : 50
	       },{
			 	header : '项目名称',
			 	dataIndex : 'ITEM_NAME',
			 	width : 200
	       },{
			 	header : '年初数',
			 	dataIndex : 'BEGIN_YEAR',
			 	width : 200
	       },{
			 	header : '本期数',
			 	dataIndex : 'CURR_PER_AMOUNT',
			 	width : 200
	       }
	]);
	var zcfzStore = new Ext.data.Store( {
		restful : true,
		//autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_zcfz.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[
	        {name: 'ID'},
	        {name: 'MYSORT'},
	        {name: 'ITEM_NAME'},
	        {name: 'BEGIN_YEAR'},
	        {name: 'CURR_PER_AMOUNT'}
	    ])
	});
	//损益（利润）列模型
	var lrCm = new Ext.grid.ColumnModel([
	       {
			 	dataIndex : 'ID',
			 	hidden : true
		   },
	       {
			 	header : '行次',
			 	dataIndex : 'MYSORT',
			 	sortable : true,
			 	width : 50
	       },{
			 	header : '项目名称',
			 	dataIndex : 'ITEM_NAME',
			 	width : 200
	       },{
			 	header : '本期数',
			 	dataIndex : 'CURR_PER_AMOUNT',
			 	width : 200
	       },{
			 	header : '本年累计数',
			 	dataIndex : 'BEGIN_YEAR',
			 	width : 200
	       }
	]);
	//损益（利润）数据
	var lrStore = new Ext.data.Store( {
		restful : true,
		//autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_lr.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[
	        {name: 'ID'},
	        {name: 'MYSORT'},
	        {name: 'ITEM_NAME'},
	        {name: 'BEGIN_YEAR'},
	        {name: 'CURR_PER_AMOUNT'}
	    ])
	});
	//现金流量列模型
	var xjllCm = new Ext.grid.ColumnModel([
	       {
			 	dataIndex : 'ID',
			 	hidden : true
		   },
	       {
			 	header : '行次',
			 	dataIndex : 'MYSORT',
			 	sortable : true,
			 	width : 50
	       },{
			 	header : '项目名称',
			 	dataIndex : 'ITEM_NAME',
			 	width : 200
	       },{
			 	header : '金额',
			 	dataIndex : 'CURR_PER_AMOUNT',
			 	width : 200
	       }
	]);
	//现金流量数据
	var xjllStore = new Ext.data.Store( {
		restful : true,
		//autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_xjll.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[
	        {name: 'ID'},
	        {name: 'MYSORT'},
	        {name: 'ITEM_NAME'},
	        {name: 'BEGIN_YEAR'},
	        {name: 'CURR_PER_AMOUNT'}
	    ])
	});
	//财务简表列模型
	var cwjbCm = new Ext.grid.ColumnModel([
	       {
			 	dataIndex : 'ID',
			 	hidden : true
		   },
	       {
			 	header : '行次',
			 	dataIndex : 'MYSORT',
			 	sortable : true,
			 	width : 50
	       },{
			 	header : '项目名称',
			 	dataIndex : 'ITEM_NAME',
			 	width : 200
	       },{
			 	header : '金额',
			 	dataIndex : 'CURR_PER_AMOUNT',
			 	width : 200
	       }
	]);
	//财务简表数据
	var cwjbStore = new Ext.data.Store( {
		restful : true,
		//autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_cwjb.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[
	        {name: 'ID'},
	        {name: 'MYSORT'},
	        {name: 'ITEM_NAME'},
	        {name: 'BEGIN_YEAR'},
	        {name: 'CURR_PER_AMOUNT'}
	    ])
	});
	
	// 最终展示panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户财务信息",
		//defaultLoad : false,
		dbclick : false,
		stUrl : basepath + '/cust_finc.json?custId='+cust_id, 
		primary : "ID",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 30,
		buts : [{
			id : 'trans',
			xtype : 'button',
			tooltip : '查看财务报表',
			text : '查看',
			listeners : {
				click : function(n) {
					if (listPanel.grid.selModel.hasSelection()) {
						
						var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
						var recordsLen = records.length;// 得到行数组的长度
						if (recordsLen > 1) {
							Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
						} else {debugger;
							//报表类型
							var repTyp = listPanel.grid.getSelectionModel().getSelected().get('REP_TYP');
							//报表类型名称
							var repTypName = listPanel.grid.getSelectionModel().getSelected().get('REP_TYP_NAME');
							//年份
							var year = listPanel.grid.getSelectionModel().getSelected().get('YEAR');
							//月份
							var month = listPanel.grid.getSelectionModel().getSelected().get('MONTH');
							//时间类型
							var timeTyp = listPanel.grid.getSelectionModel().getSelected().get('TIME_TYP');
							//审计类型
							var adtTyp = listPanel.grid.getSelectionModel().getSelected().get('ADT_TYP');
							//取到时间类型的值
							var timeTypValue = timeTypStore.query("key",timeTyp.trim(),false,true).first().get("value");
							//取到审计类型的值
							var adtTypValue = adtTypStore.query("key",adtTyp.trim(),false,true).first().get("value");
							var urlString = "";
							Ext.getCmp("repTyp").setValue(repTypName);
							Ext.getCmp("year").setValue(year + '年' + month + '月');
							//Ext.getCmp("month").setValue(month);
							Ext.getCmp("timeTyp").setValue(timeTypValue);
							Ext.getCmp("adtTyp").setValue(adtTypValue);
							if (repTyp == '400') {	//400为资产负债表的代码
								zcfzStore.load({
									params : { repTyp : repTyp,year : year,month : month,timeTyp : timeTyp,custId : cust_id}
								});
//								urlString = '/cust_zcfz.json';
								//客户财务信息grid
								var custFincGrid = new Ext.grid.GridPanel({
									id : 'custFincGrid',
									title : '<span style="font-weight:normal">客户财务信息</span>',
									autoHeight : true,
									//height : 350,
									anchor: '100%',
									autoScroll : true,
									store : zcfzStore, 			// 数据存储
									stripeRows : true, 				// 斑马线
									cm : zcfzCm, 				// 列模型
									tbar:[new Com.yucheng.bob.ExpButton({
										url : basepath + '/cust_zcfz.json?repTyp='+repTyp+'&year='+year+'&month='+month+'&timeTyp='+timeTyp+'&custId='+cust_id
							        })],
									loadMask : { msg : '正在加载表格数据,请稍等...' } 
								});
								//配置store和cm
								custFincGrid.reconfigure(zcfzStore,zcfzCm);
							} else if (repTyp == '200') {	//200为损益表的代码
								lrStore.load({
									params : { repTyp : repTyp,year : year,month : month,timeTyp : timeTyp,custId : cust_id}
								});
//								urlString = '/cust_lr.json';
								//客户财务信息grid
								var custFincGrid = new Ext.grid.GridPanel({
									id : 'custFincGrid',
									title : '<span style="font-weight:normal">客户财务信息</span>',
									autoHeight : true,
									//height : 350,
									anchor: '100%',
									autoScroll : true,
									store : zcfzStore, 			// 数据存储
									stripeRows : true, 				// 斑马线
									cm : zcfzCm, 				// 列模型
									tbar:[new Com.yucheng.bob.ExpButton({
							            url : basepath + '/cust_lr.json?repTyp='+repTyp+'&year='+year+'&month='+month+'&timeTyp='+timeTyp+'&custId='+cust_id
							        })],
									loadMask : { msg : '正在加载表格数据,请稍等...' } 
								});
								custFincGrid.reconfigure(lrStore,lrCm);
							} else if (repTyp == '800') {	//800为现金流量表的代码
								xjllStore.load({
									params : { repTyp : repTyp,year : year,month : month,timeTyp : timeTyp,custId : cust_id}
								});
//								urlString = '/cust_xjll.json';
								//客户财务信息grid
								var custFincGrid = new Ext.grid.GridPanel({
									id : 'custFincGrid',
									title : '<span style="font-weight:normal">客户财务信息</span>',
									autoHeight : true,
									//height : 350,
									anchor: '100%',
									autoScroll : true,
									store : zcfzStore, 			// 数据存储
									stripeRows : true, 				// 斑马线
									cm : zcfzCm, 				// 列模型
									tbar:[new Com.yucheng.bob.ExpButton({
							            url : basepath + '/cust_xjll.json?repTyp='+repTyp+'&year='+year+'&month='+month+'&timeTyp='+timeTyp+'&custId='+cust_id
							        })],
									loadMask : { msg : '正在加载表格数据,请稍等...' } 
								});
								custFincGrid.reconfigure(xjllStore,xjllCm);
							} else if (repTyp == '500') {	//500为财务简表的代码
								cwjbStore.load({
									params : { repTyp : repTyp,year : year,month : month,timeTyp : timeTyp,custId : cust_id}
								});
//								urlString = '/cust_cwjb.json';
								//客户财务信息grid
								var custFincGrid = new Ext.grid.GridPanel({
									id : 'custFincGrid',
									title : '<span style="font-weight:normal">客户财务信息</span>',
									autoHeight : true,
									//height : 350,
									anchor: '100%',
									autoScroll : true,
									store : zcfzStore, 			// 数据存储
									stripeRows : true, 				// 斑马线
									cm : zcfzCm, 				// 列模型
									tbar:[new Com.yucheng.bob.ExpButton({
							            url : basepath + '/cust_cwjb.json?repTyp='+repTyp+'&year='+year+'&month='+month+'&timeTyp='+timeTyp+'&custId='+cust_id
							        })],
									loadMask : { msg : '正在加载表格数据,请稍等...' } 
								});
								custFincGrid.reconfigure(cwjbStore,cwjbCm);
							}
							//机构分配模态窗口
							var custFincWin = new Ext.Window({
								plain : true,
								layout : 'anchor',
								resizable : true,
								draggable : true,
								closable : true,
								autoScroll : true,
								closeAction : 'hide',
								modal : true, // 模态窗口
								shadow : true,
								loadMask : true,
								maximizable : true,
								collapsible : true,
								titleCollapse : true,
								border : false,
								width : 800,
								height : 450,
								buttonAlign : "center",
								title : '客户财务信息',
								items : [custFincFPanel,custFincGrid],
								buttons : [{
							    	text:'关闭',handler:function(){custFincWin.hide();}
							    }]
							});
//							custFincGrid.doLayout();
							custFincWin.show();
						}
					} else {
						Ext.Msg.alert("系统提示信息", "请先选择记录!");
					}
				}
			}
		}],
		// 查询字段定义，若不定义则不出现查询条件From
		/*
		selectItems : {
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				allowBlank:false,
				items : []
			}]
		},*/
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ {
			name : 'REP_TYP',
			header : '报表类型',
			hidden : true
		},{
			name : 'REP_TYP_NAME',
			header : '报表类型'
		},{
			name : 'YEAR',
			header : '年份',
			width : 80
		},{
			name : 'MONTH',
			header : '月份',
			width : 80
		},{
			name : 'ADT_TYP',
			header : '审计类型',
			type : 'mapping',
			store : adtTypStore,
			mappingkey : 'key',
			mappingvalue : 'value'
		},{
			name : 'REP_STS',
			header : '报表状态',
			type : 'mapping',
			store : repStatusStore,
			mappingkey : 'key',
			mappingvalue : 'value'
		},{
			name : 'FILL_DATE',
			header : '填报日期',
			type : 'date'
		},{
			name : 'TIME_TYP',
			header : '时间类型',
			type : 'mapping',
			store : timeTypStore,
			mappingkey : 'key',
			mappingvalue : 'value'
		},{
			name : 'OPERATOR',
			header : '经办人'
		}],
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20
	});
	
	// 布局模型
	/*
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
	*/
	var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		layout : 'fit',
		autoScroll:true,
		items : [listPanel]
	});
});