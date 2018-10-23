Ext.onReady(function() {	
	
	var sm2 = new Ext.grid.CheckboxSelectionModel();
	var rownum2 = new Ext.grid.RowNumberer({
		header : 'NO',
		width : 28
	});
	var fields2 = [{
		name : 'c1'
	}, {
		name : 'c2'
	}, {
		name : 'c3'
	}, {
		name : 'c4'
	}];
	
	var Columns2 = new Ext.grid.ColumnModel([//gridtable中的列定义
                                             sm2,
                                            rownum2,
                                            {header :'产品编号',dataIndex:'c1',width:150,sortable : true},
                                            {header:'产品名称',dataIndex:'c2',width:150,sortable : true},
                                            {header:'排名',dataIndex:'c3',width:150,sortable : true,align : 'right'},
                                            {header:'适合率',dataIndex:'c4',width:150,sortable : true,align : 'right'}
                                            ]);
	
	var data2= [['厂商银','贵金属','1','90%'],
				['保兑仓','基金','2','80%'],
				['政府采购贷','贷款','3','70%'],
				['投联贷','理财','4','60%'],
				['有钱途','贷款','5','54%'],
				['小企业e贷款','贷款','6','34%']];
			

			 var store2 = new Ext.data.ArrayStore({
				   fields : fields2,
					data : data2
				});
			var advisePanel = new Ext.grid.GridPanel({
				region : 'center',
				frame : true,
				layout:'fit',
				store : store2,
				stripeRows : true,
				cm : Columns2,
				buttonAlign : "center",
				buttons:[{
					text : '确定',
					handler : function() {
						var selectLength = advisePanel.getSelectionModel().getSelections().length;
						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
						}else
							Ext.MessageBox.confirm('提示', '生成工作日程成功，是否需要生成商机信息？',
								function(buttonId) {
									if (buttonId.toLowerCase() == "no") { 
										productAdvise2.hide();
									} else {
										productAdvise2.hide();
										//商机窗口
										 resetAddForm();
										 addChanceForm01.form.findField('opporStat').setValue('0');
										 addChanceForm01.form.findField('custName').setValue(listPanel.getSelectionModel().getSelections()[0].data.a1);
										 addChanceForm01.form.findField('custType').setValue('1');
										 addChanceForm01.form.findField('custCategoty').setValue('2');
										 addMyBusOpportInit02();
									}
								});
					}
				}
				]
			});
	
	var productAdvise2 = new Ext.Window({//产品推荐window
		title : '产品推荐',
		plain : true,
		layout : 'fit',
		width : 700,
		height : 440,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'center',
						layout : 'fit',
						items : [ advisePanel ]
					}
			]
		} ]
	});
	
	
var fields1 = [{
	name : 'b1'
}, {
	name : 'b2'
}, {
	name : 'b3'
}, {
	name : 'b4'
}];
var num1 = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var sm1 = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});
var columns1 = new Ext.grid.ColumnModel([num1,sm1, {
	dataIndex : 'b1',
	header : '行动策略类型',
	sortable : true,
	width : 120
}, {
	dataIndex : 'b2',
	header : '行动策略名称',
	sortable : true,
	width : 120
}, {
	dataIndex : 'b3',
	header : '异动风险类型',
	width : 120
}, {
	dataIndex : 'b4',
	header : '处理方法说明',
	sortable : true,
	width : 440
}]);
var data1 = [
['客户赢回', '客户赢回策略', '关注', '通过营销等方式赢回客户，需要推荐产品并生成商机'],
['客户挽留', '客户挽留策略', '正常', '客户挽留，生成商机'],
['交叉营销', '交叉营销策略', '正常', '通过交叉营销等方式赢回客户，需要推荐产品操作'],
['贷款保全', '贷款保全策略', '关注', '保全客户在本行的贷款']
];

var store1 = new Ext.data.ArrayStore({
	fields : fields1,
	data : data1
});
//行动策略说明
var listPanel1 = new Ext.grid.GridPanel({
	region : 'center',
	frame : true,
	layout:'fit',
	store : store1,
	stripeRows : true,
	sm:sm1,
	cm : columns1,
	buttonAlign : "center",
	buttons:[{
		text : '推荐产品',
		handler : function() {
			var selectLength = listPanel1.getSelectionModel().getSelections().length;
			if (selectLength != 1) {
				Ext.Msg.alert("提示", "请选择处理策略!");
			}else{
				productAdvise2.show();
				editActivityWindow.hide();
			} 
		}}, '-',{
			text : '创建商机',
			handler : function() {
				var selectLength = listPanel1.getSelectionModel().getSelections().length;
				if (selectLength != 1) {
					Ext.Msg.alert("提示", "请选择处理策略!");
				}else{
					editActivityWindow.hide();
					 resetAddForm();
					 addChanceForm01.form.findField('opporStat').setValue('0');
					 addChanceForm01.form.findField('custName').setValue(listPanel.getSelectionModel().getSelections()[0].data.a1);
					 addChanceForm01.form.findField('custType').setValue('1');
					 addChanceForm01.form.findField('custCategoty').setValue('2');
					 addMyBusOpportInit02();
				} 
			}}, '-', {
		text : '完成',
		handler : function() {
			Ext.MessageBox.confirm('提示', '确认不需要处理本次异动信息？',
					function(buttonId) {
						if (buttonId.toLowerCase() == "no") { 
							return false;
						} else {
							editActivityWindow.hide();
							Ext.Msg.alert('提示', '操作成功！');
						}
					});
		}
	}
	]
});

//定义行动策略窗口
var editActivityWindow = new Ext.Window({
	title : '行动策略说明',
	plain : true,
	layout : 'fit',
	width : 700,
	height : 440,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	border : false,
	items : [ {
		layout : 'border',
		items : [
				{
					region : 'center',
					layout : 'fit',
					items : [ listPanel1 ]
				}
		]
	} ]
});


//展示策略窗口
function editInit() {
	editActivityWindow.show();
}



var typeStore =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '账户大额变动' ], [ 2, '现金流异常' ],
			[ 3, '公司高管离职' ],[4,'应收账款大幅上升'],[5,'进出口信用证到期'],[6,'长期订单集中取消'] ]
});


var  searchPanel = new Ext.form.FormPanel( {
	labelWidth : 100,
	labelAlign : 'right',
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [{
		columnWidth : .25,
		layout : 'form',
		items : [{
			xtype : 'textfield',
			fieldLabel : '客户名称',
			name : 'cust_NAME',
			anchor : '90%'
		}]
	},{
		columnWidth : .25,
		layout : 'form',
		items : [
		         new Ext.form.ComboBox({
		    			hiddenName : 'PROGRESS_STAGE',
		    			fieldLabel : '客户异动类型',
		    			labelStyle: 'text-align:right;',
		    			triggerAction : 'all',
		    			store : typeStore,
		    			displayField : 'value',
		    			valueField : 'key',
		    			mode : 'local',
		    			emptyText:'请选择 ',
		    			resizable : true,
		    			anchor : '90%'
		    		})
			]
	}
	,{
		columnWidth : .25,
		layout : 'form',
		items : [{
			fieldLabel : '异动发生日期 从',
			format : 'Y-m-d',
			xtype : 'datefield',
			editable:false,
			name : 'CREATE_DATE',
			id : 'CREATE_DATE',
			anchor : '90%'
		}]
	},{
		columnWidth : .25,
		layout : 'form',
		items : [{
			fieldLabel : '到',
			format : 'Y-m-d',
			xtype : 'datefield',
			editable:false,
			name : 'CREATE_DATE1',
			id : 'CREATE_DATE1',
			anchor : '90%'
		}]
	}],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {

		}
	}, {
		text : '重置',
		handler : function() {
			searchPanel.form.reset();
		}
	}]
	
});


var fields = [{
	name : 'a1'
}, {
	name : 'a8'
}, {
	name : 'a2'
}, {
	name : 'a3'
}, {
	name : 'a4'
}, {
	name : 'a5'
}];

//定义自动当前页行号
var num = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var sm = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});

var columns = new Ext.grid.ColumnModel([num, sm, {
	dataIndex : 'a1',
	header : '客户名称',
	sortable : true,
	width : 140
}, {
	dataIndex : 'a8',
	header : '异动类型',
	sortable : true,
	width : 140
}, {
	dataIndex : 'a2',
	header : '异动时间',
	sortable : true,
	width : 140
}, {
	dataIndex : 'a3',
	header : '变动金额',
	align:'right;',
	renderer: money('0,000.00'),
	sortable : true,
	
	width : 140
}, {
	dataIndex : 'a4',
	header : '处理状态',
	sortable : true,
	width : 140
}, {
	dataIndex : 'a5',
	header : '异动风险类型',
	sortable : true,
	width : 140
}]);
var data = [
['河北嘉禾家具', '账户大额变动', '2013-01-15', '1000000', '已处理(客户赢回)',
		'正常'],
['邯郸宝红制衣厂', '长期订单集中取消', '2013-03-16', '', '已处理(交叉营销)',
		'正常'],
['石家庄制药局', '进出口信用证到期', '2013-02-18', '', '已处理(贷款保全)',
		'关注'],
['石家庄维尼婚庆公司', '应收账款大幅上升', '2013-03-21', '19876500', '未处理',
		'次级'],
['石家庄大力建材', '现金流异常', '2013-05-15', '14538900', '未处理',
		'关注'],
['创美玻璃制品公司', '公司高管离职', '2013-04-15', '', '未处理',
		'可疑']];

//每页显示条数下拉选择框
var combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['value', 'text'],
				data : [[10, '10条/页'], [20, '20条/页'],
						[50, '50条/页'], [100, '100条/页'],
						[250, '250条/页'], [500, '500条/页']]
			}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	editable : false,
	width : 85
});

var store = new Ext.data.ArrayStore({
	fields : fields,
	data : data
});

var number = parseInt(combo.getValue());

//分页工具栏
var bbar = new Ext.PagingToolbar({
	pageSize : number,
	store : store,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', combo]
});


	var listPanel = new Ext.grid.GridPanel({
		title : '客户异动信息',
		region : 'center',
		frame : true,
		layout:'fit',
		store : store,
		stripeRows : true,
		sm : sm,
		cm : columns,
		buttonAlign : "center",
		bbar : bbar,
		tbar :  [{
					text : '异动处理',
					iconCls : 'editIconCss',
					handler : function() {
						var selectLength = listPanel
						.getSelectionModel()
						.getSelections().length;

						var selectRe = listPanel
								.getSelectionModel()
								.getSelections()[0];

						if (selectLength != 1) {
							Ext.Msg.alert("提示", "请选择一条记录!");
						}else if(selectRe.data.a4 != '未处理')
							Ext.Msg.alert("提示", "请选择状态为[未处理]的记录进行操作!");
							else{
								editInit();
							}
								
						}
				}]
	});
	
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'north',
						id : 'north-panel',
						title : "客户异动信息查询",
						height : 100,
						layout : 'fit',
						items : [ searchPanel ]
					},{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [ listPanel ]
					}
			]
		} ]
	});
});