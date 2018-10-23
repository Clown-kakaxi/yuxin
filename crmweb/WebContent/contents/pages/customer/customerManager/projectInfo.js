Ext.onReady(function() {
	Ext.QuickTips.init();
	//每个详细信息包含以下对象
	//1.reader
	//2.formPanel
	var cust_id = oCustInfo.cust_id;
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "项目信息",
		stUrl : basepath + '/project_info!indexPage.json?custId=' + cust_id,
		primary : "projectId",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 30,
		// 定义增删详情页面弹出窗口高度
		winHeight : 500,
		//宽度
		winWidth : 800,
		gridHeight:document.body.scrollHeight-30,
		gridWidth:document.body.scrollWidth-228,
		// 查询列表字段定义，有header属性则在页面显示
		// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ {
			name : 'projectId',
			sortable : true,
			header : '项目编号'
		},{
			name : 'custId',
			header : '客户编号'
		}, {
			name : 'projectName',
			sortable : true,
			header : '项目名称'
		},  {
			name : 'projectQuality',
			sortable : true,
			header : '项目性质'
		}, {
			name : 'projectIvsAmount',
			sortable : true,
			align:'right',
			renderer:money('0,000.00'),
			header : '项目投资总额'
		}],
		// 设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		buts : [{
					id : 'ivsCons',
					xtype : 'button',
					tooltip : '项目投资构成',
					text : '项目投资构成',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									ivsConsFPanel.getForm().load( {		//从后台获取数据
										restful : true,
										url : basepath + '/project_ivs_cons!indexPage.json',
										params : {'condition' : Ext.encode( { projectId : projectId })},
										method : 'GET',
										success : function(a) {
											var sum = 0;
											//计算合计字段的值
											for (var i = 2; i < a.items.length - 1; i++) {
												//取出items[i]是根据form的items的顺序取值的
												sum += a.items.items[i].value;
											}
											Ext.getCmp("ivsConsSum").setValue(sum);
										}
									});
									ivsConsWin.show();
								}
							} else {Ext.Msg.alert("提示", "请先选择记录!"); }
						}
					}
				},'-',{
					id : 'fundSrc',
					xtype : 'button',
					tooltip : '项目资金来源',
					text : '项目资金来源',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									fundSrcFPanel.getForm().load( {		//从后台获取数据
										restful : true,
										url : basepath + '/project_fund_src!indexPage.json',
										params : {'condition' : Ext.encode( { projectId : projectId })},
										method : 'GET',
										success :function(a,b,c) {
											var sum = 0;
											//计算合计字段的值
											for (var i = 2; i < a.items.length - 1; i= i + 2) {
												//取出items[i]是根据form的items的顺序取值的
												sum += a.items.items[i].value;
											}
											Ext.getCmp('fundSrcSum').setValue(sum);
										}
									});
									fundSrcWin.show();
								}
							} else {
								Ext.Msg.alert("提示", "请先选择记录!");
							}
						}
					}
				},'-',{
					id : 'appState',
					xtype : 'button',
					tooltip : '立项批准情况',
					text : '立项批准情况',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									appStateFPanel.getForm().load( {		//从后台获取数据
										restful : true,
										url : basepath + '/project_app_state!indexPage.json',
										params : {'condition' : Ext.encode( { projectId : projectId })},
										method : 'GET'
									});
									appStateWin.show();
								}
							} else {
								Ext.Msg.alert("提示", "请先选择记录!");
							}
						}
					}
				},'-',{
					id : 'constrCondition',
					xtype : 'button',
					tooltip : '建设条件',
					text : '建设条件',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									constrConditionFPanel.getForm().load( {		//从后台获取数据
										restful : true,
										url : basepath + '/project_constr_condition!indexPage.json',
										params : {'condition' : Ext.encode( { projectId : projectId })},
										method : 'GET'
									});
									constrConditionWin.show();
								}
							} else {
								Ext.Msg.alert("提示", "请先选择记录!");
							}
						}
					}
				},'-',{
					id : 'saleInfo',
					xtype : 'button',
					tooltip : '产品销售',
					text : '产品销售',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									saleInfoFPanel.getForm().load( {		//从后台获取数据
										restful : true,
										url : basepath + '/project_sale_info!indexPage.json',
										params : {'condition' : Ext.encode( { projectId : projectId })},
										method : 'GET'
									});
									saleInfoWin.show();
								}
							} else {
								Ext.Msg.alert("提示", "请先选择记录!");
							}
						}
					}
				},'-',{
					id : 'epctEmicEff',
					xtype : 'button',
					tooltip : '预计经济效率',
					text : '预计经济效率',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									epctEmicEffFPanel.getForm().load( {		//从后台获取数据
										restful : true,
										url : basepath + '/project_epct_emic_eff!indexPage.json',
										params : {'condition' : Ext.encode( { projectId : projectId })},
										method : 'GET'
									});
									epctEmicEffWin.show();
								}
							} else {
								Ext.Msg.alert("提示", "请先选择记录!");
							}
						}
					}
				},'-',{
					id : 'mainMtrSuplly',
					xtype : 'button',
					tooltip : '主要原料提供',
					text : '主要原料提供',
					listeners : {
						click : function(n) {
							if (listPanel.grid.selModel.hasSelection()) {
								var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
								var recordsLen = records.length;// 得到行数组的长度
								if (recordsLen > 1) {
									Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
								} else {
									var projectId = listPanel.grid.getSelectionModel().getSelected().get('projectId');
									mmsStore.load({
										proxy : new Ext.data.HttpProxy( {
											url : basepath + '/project_main_mtr_suplly!indexPage.json',
											params : {'condition' : Ext.encode( { projectId : projectId })}
										})
									});
									mmsWin.show();
								}
							} else {
								Ext.Msg.alert("提示", "请先选择记录!");
							}
						}
					}
				}]
	});
	//1.项目投资构成start**********************************************************
	//pr_ivs_cons
	//投资构成Reader
	var ivsConsReader = new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name : 'projectId'},{name : 'auxEquipAmount'},{name : 'civilWorkAmount'},{name : 'custId'},
	    {name : 'equipmentAmount'},{name : 'installCosts'},{name : 'otherCosts'},{name : 'supLipAmount'}]);
	//投资构成FormPanel
	var ivsConsFPanel = new Ext.FormPanel( {
		reader : ivsConsReader,
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'projectId',xtype : 'textfield',fieldLabel : '项目编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'civilWorkAmount',xtype : 'textfield',fieldLabel : '土建工程',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'equipmentAmount',xtype : 'textfield',fieldLabel : '生产设备',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'auxEquipAmount',xtype : 'textfield',fieldLabel : '附属设备',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'installCosts',xtype : 'textfield',fieldLabel : '安装费用',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'otherCosts',xtype : 'textfield',fieldLabel : '其他费用',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:90,
				items : [ {name : 'supLipAmount',xtype : 'textfield',fieldLabel : '配套流动资金',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {id : 'ivsConsSum', name : 'ivsConsSum',xtype : 'textfield',fieldLabel : '合计',style : 'color : red;',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	var ivsConsWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 600,		height : 250,			buttonAlign : "center",	title : '项目投资构成',
		items : ivsConsFPanel,
		buttons : [{text:'关闭',handler:function(){	ivsConsWin.hide();}
		}]
	});
	//1.项目投资构成end**********************************************************
	//2.项目资金来源start**********************************************************
	//fund_src
	//资金来源Reader
	var fundSrcReader = new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name : 'projectId'},{name : 'custId'},{name : 'financeLoanAmount'},{name : 'financeLoanIvsDt',type : 'date',mapping : 'financeLoanIvsDt.time',dateFormat : 'time'},
	    {name : 'higherFundAmount'},{name : 'higherFundIvsDt',type : 'date',mapping : 'higherFundIvsDt.time',dateFormat : 'time'},{name : 'jointIvsAmount'},
	    {name : 'jointIvsDt',type : 'date',mapping : 'jointIvsDt.time',dateFormat : 'time'},
	    {name : 'otherOrgLoanAmount'},{name : 'otherOrgLoanIvsdt',type : 'date',mapping : 'otherOrgLoanIvsdt.time',dateFormat : 'time'},{name : 'otherUnitLoanAmount'},
	    {name : 'otherUnitLoanIvsdt',type : 'date',mapping : 'otherUnitLoanIvsdt.time',dateFormat : 'time'},
	    {name : 'paidUpCapIvsDt',type : 'date',mapping : 'paidUpCapIvsDt.time',dateFormat : 'time'},{name : 'paidUpCapital'},{name : 'raiseFundAmount'},
	    {name : 'raiseFundIvsDt',type : 'date',mapping : 'raiseFundIvsDt.time',dateFormat : 'time'}]);
	
	//投资构成FormPanel
	var fundSrcFPanel = new Ext.FormPanel( {
		reader : fundSrcReader,
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {name : 'projectId',xtype : 'textfield',fieldLabel : '项目编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'financeLoanAmount', name : 'financeLoanAmount',xtype : 'textfield',fieldLabel : '财政借款',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'financeLoanIvsDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '财政借款投入日期',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'higherFundAmount',name : 'higherFundAmount',xtype : 'textfield',fieldLabel : '上级拨款金额',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'higherFundIvsDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '上级拨款计划投入日期',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'jointIvsAmount',name : 'jointIvsAmount',xtype : 'textfield',fieldLabel : '联合合资方投入金额',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'jointIvsDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '联合合资方投入日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'otherOrgLoanAmount', name : 'otherOrgLoanAmount',xtype : 'textfield',fieldLabel : '其他金融机构借款',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'otherOrgLoanIvsdt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '其他金融机构借款投入日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'otherUnitLoanAmount', name : 'otherUnitLoanAmount',xtype : 'textfield',fieldLabel : '其他单位借款',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'otherUnitLoanIvsdt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '其他单位贷款投入日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'paidUpCapital', name : 'paidUpCapital',xtype : 'textfield',fieldLabel : '企业实收资本',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'paidUpCapIvsDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '企业实收资本投入日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'raiseFundAmount', name : 'raiseFundAmount',xtype : 'textfield',fieldLabel : '集资',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 170,
				items : [ {name : 'raiseFundIvsDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '集资投入日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 120,
				items : [ {id : 'fundSrcSum', name : 'fundSrcSum',xtype : 'textfield',fieldLabel : '合计',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	var fundSrcWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 700,		height : 350,			buttonAlign : "center",	title : '项目资金来源',
		items : fundSrcFPanel,
		buttons : [{text:'关闭',handler:function(){	fundSrcWin.hide();}
		}]
	});
	//2.项目资金来源end**********************************************************
	//3.立项批准情况start**********************************************************
	//app_state
	//立项批准Reader
	var appStateReader = new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name : 'projectId'},{name : 'custId'},{name : 'designDvlpApprover'},{name : 'prjReplyApprover'},{name : 'prjReplyDocNum'},
	    {name : 'designDvlpDocNum'},{name : 'fsbltRepApprover'},{name : 'fsbltRepDocNum'},{name : 'ivsPlanApprover'},{name : 'ivsPlanDocNum'},
	    {name : 'legalLicenseApprover'},{name : 'legalLicenseDocNum'},{name : 'prjProApprover'},{name : 'prjProDocNum'},
	    {name : 'prjProAppDate',type : 'date',mapping : 'prjProAppDate.time',dateFormat : 'time'},
	    {name : 'designDvlpAppDate',type : 'date',mapping : 'designDvlpAppDate.time',dateFormat : 'time'},
	    {name : 'prjReplyAppDate',type : 'date',mapping : 'prjReplyAppDate.time',dateFormat : 'time'},
	    {name : 'fsbltRepAppDate',type : 'date',mapping : 'fsbltRepAppDate.time',dateFormat : 'time'},
	    {name : 'ivsPlanAppDate',type : 'date',mapping : 'ivsPlanAppDate.time',dateFormat : 'time'},
	    {name : 'legalLicenceAppDate',type : 'date',mapping : 'legalLicenceAppDate.time',dateFormat : 'time'}]);
	//立项批准FormPanel
	var appStateFPanel = new Ext.FormPanel( {
		reader : appStateReader,
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'projectId',xtype : 'textfield',fieldLabel : '项目编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'legalLicenseApprover',xtype : 'textfield',fieldLabel : '企业法人营业执照批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'legalLicenseDocNum',xtype : 'textfield',fieldLabel : '企业法人营业执照批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'legalLicenceAppDate',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '企业法人营业执照批准日期',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'prjProApprover',xtype : 'textfield',fieldLabel : '项目建议书批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'prjProDocNum',xtype : 'textfield',fieldLabel : '项目建议书批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'prjProAppDate',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '项目建议书批准日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'fsbltRepApprover',xtype : 'textfield',fieldLabel : '项目可行性报告批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'fsbltRepDocNum',xtype : 'textfield',fieldLabel : '项目可行性报告批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'fsbltRepAppDate',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '项目可行性报告批准日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'designDvlpApprover',xtype : 'textfield',fieldLabel : '项目扩初设计批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'designDvlpDocNum',xtype : 'textfield',fieldLabel : '项目扩初设计批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'designDvlpAppDate',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '项目扩初设计批准日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'ivsPlanApprover',xtype : 'textfield',fieldLabel : '项目投资计划批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'ivsPlanDocNum',xtype : 'textfield',fieldLabel : '项目投资计划批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'ivsPlanAppDate',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '项目投资计划批准日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'prjReplyApprover',xtype : 'textfield',fieldLabel : '项目批复批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'prjReplyDocNum',xtype : 'textfield',fieldLabel : '项目批复批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'prjReplyAppDate',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '项目批复批准日期',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	var appStateWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 900,		height : 400,			buttonAlign : "center",	title : '立项批准情况',
		items : appStateFPanel,
		buttons : [{text:'关闭',handler:function(){	appStateWin.hide();}
		}]
	});
	//3.立项批准情况end**********************************************************
	//4.建设条件start**********************************************************
	//constr_condition
	//建设条件Reader
	var constrConditionReader = new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name : 'projectId'},{name : 'custId'},{name : 'buildingAreaAppAmount'},{name : 'buildingAreaApprover'},
	    {name : 'buildingAreaDocNum'},{name : 'icrPowerAppAmount'},{name : 'icrPowerApprover'},{name : 'icrPowerDocNum'},
	    {name : 'newLandAppAmount'},{name : 'newLandApprover'},{name : 'newLandDocNum'},{name : 'wasteTreatAppAmount'},
	    {name : 'wasteTreatApprover'},{name : 'wasteTreatDocNum'}]);
	//建设条件FormPanel
	var constrConditionFPanel = new Ext.FormPanel( {
		reader : constrConditionReader,
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'projectId',xtype : 'textfield',fieldLabel : '项目编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'buildingAreaApprover',xtype : 'textfield',fieldLabel : '建筑面积批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'buildingAreaDocNum',xtype : 'textfield',fieldLabel : '建筑面积批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'buildingAreaAppAmount',xtype : 'textfield',fieldLabel : '建筑面积批准量',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'icrPowerApprover',xtype : 'textfield',fieldLabel : '增加用电量批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrPowerDocNum',xtype : 'textfield',fieldLabel : '增加用电量批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrPowerAppAmount',xtype : 'textfield',fieldLabel : '增加用电量批准量',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'newLandApprover',xtype : 'textfield',fieldLabel : '新增土地批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'newLandDocNum',xtype : 'textfield',fieldLabel : '新增土地批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'newLandAppAmount',xtype : 'textfield',fieldLabel : '新增土地批准量',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 180,
				items : [ {name : 'wasteTreatApprover',xtype : 'textfield',fieldLabel : '三废治理批准单位',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'wasteTreatDocNum',xtype : 'textfield',fieldLabel : '三废处理批准文号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'wasteTreatAppAmount',xtype : 'textfield',fieldLabel : '三废处理批准量',labelStyle : 'text-align:right;',disabled : true,anchor : '95%'}]
			}]
		}]
	});
	var constrConditionWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 900,		height : 400,			buttonAlign : "center",	title : '建设条件',
		items : constrConditionFPanel,
		buttons : [{text:'关闭',handler:function(){	constrConditionWin.hide();}
		}]
	});
	//4.建设条件end**********************************************************
	//5.主要原料供应start**********************************************************
	//main_mtr_suplly
	var mmsStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/project_main_mtr_suplly!indexPage.json'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name : 'mmsId',mapping : 'id'},{name : 'projectId'},{name:'custId'},{name:'nameAndSpce'},{name: 'implementInfo'},{name: 'yearlyConsume'}])
	});
	// 定义自动当前页行号
	var mmsRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var mmsCm = new Ext.grid.ColumnModel([mmsRownum,
	       {dataIndex : 'mmsId',hidden : true},
	       {dataIndex : 'custId',hidden : true}, 
	       {header : '项目编号',dataIndex : 'projectId',sortable : true,width : 100}, 
	       {header : '名称及规格',dataIndex : 'nameAndSpce',sortable : true,width : 150}, 
	       {header : '年耗量',dataIndex : 'yearlyConsume',sortable : true,width : 100}, 
	       {dataIndex : 'implementInfo',header : '落实情况',sortable : true,width : 200}
	]);
	var mmsGrid = new Ext.grid.GridPanel({
		autoScroll : true,
		store : mmsStore, 	// 数据存储
		cm : mmsCm, 		// 列模型
		loadMask : {msg : '正在加载表格数据,请稍等...' }
	});
	var mmsWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 600,		height : 400,			buttonAlign : "center",	title : '主要原料供应',
		items : mmsGrid,
		buttons : [{text:'关闭',handler:function(){	mmsWin.hide();}
		}]
	});
	//5.主要原料供应end**********************************************************
	//6.产品销售start**********************************************************
	//sale_info
	//产品销售Reader
	var saleInfoReader = new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name : 'projectId'},{name : 'custId'},{name : 'saleArea'},{name : 'saleChannel'},
	    {name : 'saleObj'},{name : 'saleWay'}]);
	//产品销售FormPanel
	var saleInfoFPanel = new Ext.FormPanel( {
		reader : saleInfoReader,
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'projectId',xtype : 'textfield',fieldLabel : '项目编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'saleArea',xtype : 'textfield',fieldLabel : '销售地区',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'saleChannel',xtype : 'textfield',fieldLabel : '销售渠道',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'saleObj',xtype : 'textfield',fieldLabel : '销售对象',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth:80,
				items : [ {name : 'saleWay',xtype : 'textfield',fieldLabel : '销售方式',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	var saleInfoWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 600,		height : 250,			buttonAlign : "center",	title : '产品销售',
		items : saleInfoFPanel,
		buttons : [{text:'关闭',handler:function(){	saleInfoWin.hide();}
		}]
	});
	//6.产品销售end**********************************************************
	//7.预计经济效率start**********************************************************
	//epct_emic_eff
	//预计经济效率Reader
	var epctEmicEffReader = new Ext.data.JsonReader( {
		root : 'json.data'
	}, [{name : 'projectId'},{name : 'custId'},{name : 'exchIvtRatio'},{name : 'fixedAssets'},
	    {name : 'icrAnnualAmount'},{name : 'icrAnnualForeignExch'},{name : 'icrAnnualOutput'},{name : 'icrAnnualProfit'},
	    {name : 'icrAnnualTax'},{name : 'mainProduct'},{name : 'newMainProduct'},{name : 'oriAnnualAmount'},
	    {name : 'oriAnnualForeignExch'},{name : 'oriAnnualOutput'},{name : 'oriAnnualProfit'},{name : 'oriAnnualTax'},
	    {name : 'productivity'},{name : 'profitLossAmount'},{name : 'profitLossBreakeven'},{name : 'roi'}]);
	//预计经济效率FormPanel
	var epctEmicEffFPanel = new Ext.FormPanel( {
		reader : epctEmicEffReader,
		frame : true,
		autoScroll : true,
		items : [ {
			layout : 'column',
			items : [{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'projectId',xtype : 'textfield',fieldLabel : '项目编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'oriAnnualOutput',xtype : 'textfield',fieldLabel : '原年产值',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrAnnualOutput',xtype : 'textfield',fieldLabel : '新增年产值',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'mainProduct',xtype : 'textfield',fieldLabel : '主要产品',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'newMainProduct',xtype : 'textfield',fieldLabel : '新增主要产品',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'oriAnnualAmount',xtype : 'textfield',fieldLabel : '原年产量',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrAnnualAmount',xtype : 'textfield',fieldLabel : '新增年产量',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'oriAnnualProfit',xtype : 'textfield',fieldLabel : '原年利润',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrAnnualProfit',xtype : 'textfield',fieldLabel : '新增年利润',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'oriAnnualTax',xtype : 'textfield',fieldLabel : '原年税金',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrAnnualTax',xtype : 'textfield',fieldLabel : '新增年税金',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'oriAnnualForeignExch',xtype : 'textfield',fieldLabel : '原年创汇',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'icrAnnualForeignExch',xtype : 'textfield',fieldLabel : '新增年创汇',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'profitLossBreakeven',xtype : 'textfield',fieldLabel : '盈亏保本',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'profitLossAmount',xtype : 'textfield',fieldLabel : '盈亏保本量',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'productivity',xtype : 'textfield',fieldLabel : '生产率',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'},
				          {name : 'fixedAssets',xtype : 'textfield',fieldLabel : '固定资产',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [ {name : 'roi',xtype : 'textfield',fieldLabel : '投资利润率',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			},{
				layout : 'form',columnWidth : .5,labelWidth : 110,
				items : [{name : 'exchIvtRatio',xtype : 'textfield',fieldLabel : '创汇投资利润率',disabled : true,labelStyle : 'text-align:right;',anchor : '95%'}]
			}]
		}]
	});
	var epctEmicEffWin = new Ext.Window({
		plain : true,		layout : 'fit',			resizable : true,		draggable : true,		closable : true,
		autoScroll : true,	closeAction : 'hide',	modal : true,			shadow : true,			loadMask : true,
		maximizable : true,	collapsible : true,		titleCollapse : true,	border : false,
		width : 700,		height : 400,			buttonAlign : "center",	title : '预计经济效率',
		items : epctEmicEffFPanel,
		buttons : [{text:'关闭',handler:function(){	epctEmicEffWin.hide();}
		}]
	});
	//7.预计经济效率end**********************************************************
	
	// 布局模型
	var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		//layout : 'fit',
		autoScroll:true,
		items : [ listPanel ]
	});

});