Ext.onReady(function (){

var detailCreditCodeRecord = new Ext.data.Record.create([
 	{name:"creditCode"},
 	{name:"detailCreditCode"},
 	{name:"adjustProValue"},
 	{name:"adjustProd"},
 	{name:"applyCreditCode"},
 	{name:"applyCreditDetailCode"},
 	{name:"applyBizKind"}
 	]);
 	
 	var fm = Ext.form;
 	var detailCreditCodeColumns = new Ext.grid.ColumnModel({
 		columns:[
 		new Ext.grid.RowNumberer(),
		{header:'申请授信合同号',dataIndex:'applyCreditCode',width:60},
		{header:'申请授信明细号',dataIndex:'applyCreditDetailCode',width:60}, 		
		{header:'申请产品',dataIndex:'applyBizKind',width:100},

		{header:'被调剂授信合同号',dataIndex:'creditCode',id:'creditCode',width:60},
 		{header:'被调剂授信明细号',dataIndex:'detailCreditCode',id:'detailCreditCode',width:60},
 		{header:'被调剂产品',dataIndex:'adjustProd',id:'adjustProd',width:60} ,
 		{header:'调剂额度额度',dataIndex:'adjustProValue',id:'adjustProValue',width:100}
 		]

 	});
 	
 	var detailCreditCodeData = {
 	num:3,
 	rows:[
		
 	{applyCreditCode:'33176',applyCreditDetailCode:'88654',applyBizKind:'信用',
 	creditCode:'22222222',detailCreditCode:'66666666',adjustProd:'信用'
 	},
 	{
	applyCreditCode:'33176',applyCreditDetailCode:'88654',applyBizKind:'信用',
 	creditCode:'22222222',detailCreditCode:'77777777',adjustProd:'信用'
 	},
 	{
	applyCreditCode:'33176',applyCreditDetailCode:'88654',applyBizKind:'信用',
 	creditCode:'22222222',detailCreditCode:'88888888',adjustProd:'信用'
 	}
	
 	]
 	}
  	var detailCreditCodeData2 = {
 	num:3,
 	rows:[
 	{
 	//creditCode:'22222222',detailCreditCode:'9999'
 	},
 	{
 	//creditCode:'22222222',detailCreditCode:'9999'
 	},
 	{
 	//creditCode:'22222222',detailCreditCode:'9999'
 	}
 	]
 	}	
 	var detailCreditCodeReader = new Ext.data.JsonReader({
 	totalProperty:'num',
  	root:'rows'
 	},detailCreditCodeRecord);
 	
 	var detailCreditCodeStore = new Ext.data.Store({
 		autoDestroy:true,
 		reader:detailCreditCodeReader
 	});
 	detailCreditCodeStore.loadData(detailCreditCodeData);
 	
 	var detailCreditCodeEditGrid = new Ext.grid.GridPanel({
 		cm:detailCreditCodeColumns,
 		clicksToEdit:1,
		autoScroll:true,
		title:'额度调整授信子明细',
 		id:'detailCreditCodeEditGrid',
 		store:detailCreditCodeStore
 	});	
	
	

var creValAdjDetailFormPanel = new Ext.form.FormPanel({
		labelWidth : 100,
		labelAlign:'right',
		height : 150, 
		split:true,
		frame : true,
		region:'north',
		items : [{
					layout:'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [
										{ 
											xtype : 'textfield',
											fieldLabel : '调整企业名称',
											labelStyle:{
												width:'120px'
											},	
											anchor : '90%'
										},
										{ 
											xtype : 'textfield',
											fieldLabel : '调整额度',
											labelStyle:{
												width:'120px'
											},	
											anchor : '90%'
										}
										]
							}, 
							{
								columnWidth : .33,
								layout : 'form',
								items : [
										{ 
											xtype : 'textfield',
											fieldLabel : '目标企业名称',
											labelStyle:{
												width:'120px'
											},	
											anchor : '90%'
										},
												{ 
													xtype : 'textfield',
													fieldLabel : '本次申请业务品种及分配方式',
													labelStyle:{
														width:'120px'
													},	
													anchor : '90%'
												}											
										
									]
							},
							{
								columnWidth : .34,
								layout:'form',
								labelWidth:130,
								items:{
								fieldLabel:'本次申请额度',
								xtype:'textfield',
								format:'Y-m-d',
								anchor:'100%'
								}
							}							
							]
				},
				{
						layout:'form',
						labelStyle:'text-align:center',
						labelAlign:'right',
						labelWidth:100,
						items:
						{
						xtype:'textarea',
						fieldLabel:'调整意见',
						anchor:'100%'
						}
				}				
				]
	});
 	
 	
 	
 	
	var creValUseAppHisColumns = new Ext.grid.ColumnModel({
	columns:[
			new Ext.grid.RowNumberer({
				width:40,
				header:'序号'
			}),	
	{header:'集团名称',dataIndex:'groupName',id:'groupName',width:150},
	{header:'申请企业名称',dataIndex:'applyCompany',width:150},
//	{header:'申请授信合同号',dataIndex:'creditContractCode',width:150},
//	{header:'申请授信明细号',dataIndex:'creditContractDetailCode',width:150},
//	{header:'申请业务品种',dataIndex:'applyBusinessKind',width:150},
	{header:'申请额度',dataIndex:'applyValue',width:150,align:'right'},
	{header:'被调剂客户名称',dataIndex:'adjustCom',width:150},
//	{header:'被调剂授信合同号',dataIndex:'adjustContractCode',width:150},
//	{header:'被调剂授信明细号',dataIndex:'adjustContractDetailCode',width:150},
//	{header:'被调剂业务品种',dataIndex:'adjustBusinessKind',width:150},
	{header:'被调剂额度',dataIndex:'adjustCreditValue',width:150},
	{header:'额度调整申请日期',dataIndex:'applyAdjustDate',width:150},
	{header:'调整后额度',dataIndex:'afterAdjustValue',width:150}
	]
	});
	var creValUseAppHisData= {
		num:3,
		rows:[
			{"groupName":"中国北京集团公司","applyCompany":"北京国安通信有限公司","creditContractCode":"0101020101","creditContractDetailCode":"03020101",
			"applyBusinessKind":"信用",applyValue:"100,000,000.00",adjustCom:'北京远通股份有限公司',adjustContractCode:'050302',adjustContractDetailCode:'040302',			
			adjustBusinessKind:'信用',adjustCreditValue:'100,000,000.00',applyAdjustDate:'2011-06-06',afterAdjustValue:'300,000,000.00'},
			{"groupName":"中国北京集团公司","applyCompany":"北京国安通信有限公司","creditContractCode":"0101020101","creditContractDetailCode":"03020101",
			"applyBusinessKind":"信用",applyValue:"100,000,000.00",adjustCom:'北京证券股份有限公司',adjustContractCode:'050302',adjustContractDetailCode:'040302',
			adjustBusinessKind:'信用',adjustCreditValue:'100,000,000.00',applyAdjustDate:'2011-06-06',afterAdjustValue:'400,000,000.00'},
			{"groupName":"中国北京集团公司","applyCompany":"北京国安通信有限公司","creditContractCode":"0101020101","creditContractDetailCode":"03020101",
			"applyBusinessKind":"信用",applyValue:"100,000,000.00",adjustCom:'北京证券股份有限公司',adjustContractCode:'050302',adjustContractDetailCode:'040302',
			adjustBusinessKind:'信用',adjustCreditValue:'100,000,000.00',applyAdjustDate:'2011-06-06',afterAdjustValue:'500,000,000.00'}

			]
	};
	var creValUseAppHisRecord = Ext.data.Record.create(
		[
		{name:'groupName'},
		{name:'applyCompany'},
		{name:'creditContractCode'},
		{name:'creditContractDetailCode'},
		{name:'applyBusinessKind'},
		{name:'applyValue'},
		{name:'adjustCom'},

		{name:'adjustContractCode'},
		{name:'adjustContractDetailCode'},
		{name:'applyBusinessKind'},
		{name:'adjustBusinessKind'},
		{name:'adjustCreditValue'},
		{name:'applyAdjustDate'},
		{name:'afterAdjustValue'}
		]	
	);
	
	var creValUseAppHisReader= new Ext.data.JsonReader({totalProperty:'num',root:'rows'},
		creValUseAppHisRecord
	);
	var creValUseAppHisStore = new Ext.data.Store({
		reader:creValUseAppHisReader
	});
	creValUseAppHisStore.loadData(creValUseAppHisData);
	

var creValUseAppHisFormSear = new Ext.form.FormPanel({
		labelWidth : 100,
		labelAlign:'right',
		height : 100, 
		frame : true,
		region:'north',
		autoScroll : true,
		items : [{
					layout:'column',
					items : [
							{
								columnWidth : .25,
								layout : 'form',
								items : [
										{ 
											xtype : 'textfield',
											fieldLabel : '集团公司名称',
											value:'中国北京集团公司',
											anchor : '90%'
										}

										]
							}, 
							{
								columnWidth : .25,
								layout : 'form',
								items : [
										{ 
											xtype : 'textfield',
											fieldLabel : '授信申请人',
											anchor : '90%'
										}
									
									]
							},
							{
								columnWidth : .5,
								layout:'column',
								xtype:'panel',
								items : [
											{
												columnWidth:.5,
												layout:'form',
												labelWidth:130,
												items:{
												fieldLabel:'调整申请-起始日期',
												xtype:'datefield',
												format:'Y-m-d',
												id:'fromDate',
												anchor:'100%'
												}
											},
											{
												columnWidth:.5,
												layout:'form',
												labelStyle:'text-align:center',
												labelAlign:'right',
												labelWidth:130,
												items:{xtype:'datefield',
												fieldLabel:'调整申请-截止日期',
												format:'Y-m-d',
												anchor:'90%'
												}
											}										
											
										]
							}							
							]
				}
				],
			buttonAlign:'center',
			buttons:[
				{
					text:'查询',
					handler:function()
					{
						
					}
				}
			]

	});
	var creValUseAppHisListPal = new Ext.grid.GridPanel({
		title:'额度调整明细列表',
		cm:creValUseAppHisColumns, 
		selModel:new Ext.grid.RowSelectionModel({
			singleSelect:true
		}),
		tbar:[
		{
			text:'查看调整明细',
			handler:function()
			{
				creditAdjustItemWin.show();
			}
		}
		],
		
		region:'center',
		store:creValUseAppHisStore,
		frame:true,
		bbar:
			{	
				xtype:'paging',
				pageSize : 10,
				store : creValUseAppHisStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', {xtype:'textfield',value:'2'} ]
			}
	});	
	
	var creValUseAppAdjList= new Ext.Viewport({
		layout:'border',
	  	items:[
	  		creValUseAppHisFormSear,
	  		creValUseAppHisListPal
	  	]

	});	
	
var creditAdjustItemWin = new Ext.Window({
		frame : true,
		height : 500,
		title:'额度调整详细信息',
		width:1000,
		closeAction:'hide',
		constrain:true,
		modal:true,
		id:'creValUseAppHisForm', 
		buttonAlign:"center" ,
		labelAlign:'right',
		layout : 'fit',
	//	items: [new Ext.grid.GridPanel()],
		buttons:[
		{
			text:'返回',
			handler:function()
			{
					creditAdjustItemWin.hide();
			}
		}
		]
	});	
	
var creditAdjustItemDetailWin = new Ext.Window({
		frame : true,
		height : 450,
		width:800,
		closeAction:'hide',
		constrain:true,
		title:'子明细',
		modal:true,
		buttonAlign:"center" ,
		labelAlign:'right',
		layout : 'border',
		buttons:[
		{
			text:'返回',
			handler:function()
			{
					creditAdjustItemDetailWin.hide();
			}
		}
		],
		items:[
		creValAdjDetailFormPanel,
		detailCreditCodeEditGrid
		]
		
	});	
	

});