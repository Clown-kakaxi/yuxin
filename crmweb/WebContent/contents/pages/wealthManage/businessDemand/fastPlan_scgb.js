/**
 * 财富管理-业务需求：快速规划——生成报告（静态页面） hujun，2013-09-11
 */



var form1 = new Ext.FormPanel({
	layout : 'fit',
	labelAlign : 'right',
	height : 100,
	frame : true,
	style:'padding:0px 0px 0px 0px',
	 html : '<p style="line-height:30px;font-size:20px; text-align:center">'
			+ '快速规划报告</p>'
			+'<p></p>'
			+ '<p style="line-height:30px;font-size:18px; text-align:center">'
			+ '客户名称：王一平     年龄：32    未婚     现有/预计子女  1 人      联系电话：13523441023</p>'
});

var Panel21 =new Ext.form.FormPanel({
	title:'资产负债信息',
	border:true,
	frame : true,
 labelAlign : 'right',
 items:[{
	  layout:'column',
	  labelWidth:150,
	  items:[{
		  layout:'form',
		  columnWidth:.5,
		  items:[{
				xtype:'textfield',
				name:'parantMoney21',
				fieldLabel:'金融资产(万元)',
				value:'32',
				anchor:'95%'
			},{
				xtype:'textfield',
				name:'parantMoney22',
				fieldLabel:'贷款及负债(万元)',
				value:'54',
				anchor:'95%'
			},{
				xtype:'textfield',
				name:'parantMoney22',
				fieldLabel:'净资产(万元)',
				value:'54',
				anchor:'95%'
			}]
	  },{
		  layout:'form',
		  columnWidth:.5,
		  items:[{
				xtype:'textfield',
				name:'parantMoney23',
				fieldLabel:'房地产(万元)',
				value:'120',
				anchor:'95%'
			},{
				xtype:'textfield',
				name:'parantMoney24',
				fieldLabel:'总资产(万元)',
				value:'174',
				anchor:'95%'
			}]
	  }]
 }]
});
var Panel22 =new Ext.form.FormPanel({
	title:'简易收支信息',
	border:true,
	frame : true,
	labelAlign : 'right',
	items:[{
		layout:'column',
		labelWidth:150,
		items:[{
				layout:'form',
				columnWidth:.5,
				items:[{
						xtype:'textfield',
						name:'parantMoney21',
						fieldLabel:'估计年收入',
						value:'43',
						anchor:'95%'
				},{
						xtype:'textfield',
						name:'parantMoney22',
						fieldLabel:'年支出',
						value:'12',
						anchor:'95%'
				},{
						xtype:'textfield',
						name:'parantMoney22',
						fieldLabel:'年还款额',
						value:'13',
						anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney22',
					fieldLabel:'年自由储蓄额',
					value:'10',
					anchor:'95%'
				}]
		},{
			layout:'form',
			columnWidth:.5,
			items:[{
					xtype:'textfield',
					name:'parantMoney23',
					fieldLabel:'自由储蓄率',
					value:'0.23',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney24',
					fieldLabel:'金融资产率',
					value:'0.18',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney24',
					fieldLabel:'负债率',
					value:'0.31',
					anchor:'95%'
				}]
	  }]
}]
});
var Panel23 =new Ext.form.FormPanel({
	title: '分析:',
	border:true,
	frame : true,
	labelAlign : 'right',
	items:[{
		layout:'column',
		labelWidth:150,
		items:[{
				layout:'form',
				columnWidth:1,
				items:[{
					
			        style:'padding:0px 0px 0px 0px',
			        html: '<p style="line-height:25px;font-size:16px;">'
			        	+'&nbsp;&nbsp;您的家庭属于： 高收入高自由储蓄家庭     理财重点：善于自由储蓄，提高投资报酬率</p>'
			        	+ '<p style="line-height:25px;font-size:16px;">'
			        	+'&nbsp;&nbsp;您的家庭属于： 高资产低投资率   投资重点：投资在高收益率的产品上</p>'
			    }]
		}]
}]
});
var fd_01 = new Ext.form.FieldSet({
	xtype : 'fieldset',
	title : '客户基本信息',
	//width : 770,
	// height : 300,
	titleCollapse : true,
//	collapsed : true,
	collapsible : true,
	autoHeight : true,
	items : [Panel21,Panel22,Panel23]
});
var panel3 = new Ext.FormPanel( {
	frame : true,
	autoScroll : true,
	region:'center',
	items : [ {
		xtype : 'fieldset',
		title:'(二)如果只考虑购房计划：',
		titleCollapse : true,
//		collapsed : true,
		collapsible : true,
		autoHeight : true,
		items : [{
			layout:'column',
			labelWidth:150,
			items:[{
					layout:'form',
					columnWidth:1,
					items:[{
							
						    style:'padding:0px 0px 0px 0px',
	            			html:'<p style="line-height:25px;font-size:16px;">'
	            				+ '&nbsp;&nbsp;13年后 准备购买      130万元的住房 按 4％的通货膨胀率计算5年后需要准备145 万元</p>'
	            				+'<p style="line-height:25px;font-size:16px;">'
	            				+'&nbsp;&nbsp;目前可用来投资的资金为 32万元    按10.9的投资回报率算5年后可贮备46万元</p>'
	            				+ '<p style="line-height:25px;font-size:16px;">'
	            				+ '&nbsp;&nbsp;目前每月可存储       4000元    按10.9的投资回报率算5年后可贮备25.3万元</p>'
	            				+ '<p style="line-height:25px;font-size:16px;">'
	            				+ '&nbsp;&nbsp;目前每月可存储        4000元    按4％的贷款利率算20年可负担31.2万元的贷款</p>'
	            				+'<p style="line-height:25px;font-size:16px;">'
	            				+ '</p>'
	            				+'<p style="line-height:25px;font-size:16px;">'
	            				+ '&nbsp;&nbsp;总计：您可以购买46+25.3+31.2 的房屋，高于您计划的102.2万元的房屋，您的计划可行</p>'
				    }]
			}]
	}]
	},{
		xtype : 'fieldset',
		title: '（三）如果只考虑退休计划</p>',
		titleCollapse : true,
//		collapsed : true,
		collapsible : true,
		autoHeight : true,
		buttonAlign :'center',
		items : [{
			layout:'column',
			labelWidth:150,
			items:[{
					layout:'form',
					columnWidth:1,
					items:[{
						
				        style:'padding:0px 0px 0px 0px',
				        html:'<p style="line-height:25px;font-size:16px;">'
	        				+ '&nbsp;&nbsp;您计划55岁退休，可再工作26年，以80岁的寿命计算您的退休生活年数为25年</p>'
	        				+'<p style="line-height:25px;font-size:16px;">'
	        				+ '&nbsp;&nbsp;退休后每月的生活费现值为8万，按4％的通货膨胀率计算 26 年后每月需要 13.4 万</p>'
	        				+'<p style="line-height:25px;font-size:16px;">'
	        				+ '&nbsp;&nbsp;退休后首年的生活费为161 万，按4%的通货膨胀率和5.6的投资回报率计算，总共需要2629万</p>'
	        				+'<p style="line-height:25px;font-size:16px;">'
	        				+ '&nbsp;&nbsp;目前可用来投资的资金为32万元    按10.9的投资回报率算26年后可贮备48万元</p>'
	        				+'<p style="line-height:25px;font-size:16px;">'
	        				+ '&nbsp;&nbsp;目前每月可存储         4000元    按10.9的投资回报率算26年后可贮备83万元</p>'
	        				+'<p style="line-height:25px;font-size:16px;">'
	        				+ '&nbsp;&nbsp;总上计算：您可以为退休贮备48+83万元 高于您的计划13.4 万，您的梦想可以实现。</p>'
				    }]
			}]
}]
	},{
		xtype : 'fieldset',
		title: '（四）如果只考虑教育目标',
		titleCollapse : true,
//		collapsed : true,
		collapsible : true,
		autoHeight : true,
		buttonAlign :'center',
		items : [{
			layout:'column',
			labelWidth:150,
			items:[{
					layout:'form',
					columnWidth:1,
					items:[{
						
				        style:'padding:0px 0px 0px 0px',
				        html:'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;预计的高等教育费用为15万元，高等教育年数为4年'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;子女距离上大学还有13年，以2％ 的教育费用增长率算13年后需要 19元</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;目前可用来投资的资金为32万元    按10.9的投资回报率算13年后可贮备46.3万元</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;目前每月可存储         4000元    按10.9的投资回报率算13年后可贮备53.5万元</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;可以得到的子女教育费用为46.3+53.5万元 高于您的计划13.4 万，您的教育计划可以实现</p>'
				    }]
			}]
}]
	},{
		xtype : 'fieldset',
		 title: '（五）保险规划',
		titleCollapse : true,
//		collapsed : true,
		collapsible : true,
		autoHeight : true,
		buttonAlign :'center',
		items : [{
			layout:'column',
			labelWidth:150,
			items:[{
					layout:'form',
					columnWidth:1,
					items:[{
						
				        style:'padding:0px 0px 0px 0px',
				        html:'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;家庭生活保障	1,260 	万元	每年家庭开销*給家人保障年數</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;目前房贷负债额	300 	万元	目前的房贷与其他负债总额</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;子女高等教育金	113 	万元	高等教育年需求现值</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;金融性资产	350 	万元	包括存款 股票 海外基金等变現性资产</p>'
							+'<p style="line-height:25px;font-size:16px;">'
							+ '&nbsp;&nbsp;寿险保额应为：23万，已保险为：123万 您的保险不够</p>'
				    }]
			}]
}]
	},{  	
		xtype : 'fieldset',
		title: '（六）同时达成多个理财目标的可能性和需要努力的方向',
		titleCollapse : true,
//		collapsed : true,
		collapsible : true,
		autoHeight : true,
		buttonAlign :'center',
		items : [{
			layout:'column',
			labelWidth:150,
			items:[{
					layout:'form',
					columnWidth:1,
					items:[{
						
				        style:'padding:0px 0px 0px 0px',
				        html:'<p style="line-height:25px;font-size:16px;">&nbsp;&nbsp;总目标合计现值：321万  总能力合计：543万</p>'
				    }]
			}]
}]
	}]
});
//列表记录序号
var prorownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 列表数据定义
var data = [
		['退休安排', '320000', '12', '12.2','120000'],
		['房产安排', '430000', '12', '12.2','120000'],
		['合计', '750000', ,,'240000']
		];

// 列表字段名称
var prodRecord = Ext.data.Record.create([{
			name : 'f1'
		}, {
			name : 'f2'
		}, {
			name : 'f3'
		}, {
			name : 'f4'
		},{
			name : 'f5'
		}]);

// 列表数据存储对象
var prodStore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(data),
			reader : new Ext.data.ArrayReader({}, prodRecord)
		});

// 列表表头定义
var procm = new Ext.grid.ColumnModel([prorownum,{
			header : '目标',
			dataIndex : 'f1',
			sortable : true,
			width : 100
		}, {
			header : '需求金额',
			dataIndex : 'f2',
			align:'right',
			renderer: money('0,000.00'),
			sortable : true,
			width : 100
		}, {
			header : '达成年数',
			dataIndex : 'f3',
			sortable : true,
			align:'right',
			
			width : 100
		}, {
			header : '折现率',
			dataIndex : 'f4',
			align:'right',
			sortable : true,
			width : 100
		},{
			header : '需求金额现值',
			dataIndex : 'f5',
			renderer: money('0,000.00'),
			align:'right',
			sortable : true,
			width : 100
		}]);

// 列表定义
var Grid = new Ext.grid.GridPanel({
	title : '达成理财目标的资金需求',
	autoHeight : true,
	region : 'center',
	store : prodStore,
	cm : procm,
	bbar : new Ext.PagingToolbar({
				pageSize : '10',
				store : prodStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录"
			})
});
prodStore.load(data);

//列表记录序号
var prorownum1 = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 列表数据定义
var data1 = [
		['首期投资', '320000', '12', '12.2','120000'],
		['月定期投资', '430000', '12', '12.2','120000'],
		['合计','750000' , ,,'240000']];

// 列表字段名称
var prodRecord1 = Ext.data.Record.create([{
			name : 'f11'
		}, {
			name : 'f22'
		}, {
			name : 'f33'
		}, {
			name : 'f44'
		},{
			name : 'f55'
		}]);

// 列表数据存储对象
var prodStore1 = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(data1),
			reader : new Ext.data.ArrayReader({}, prodRecord1)
		});

// 列表表头定义
var procm1 = new Ext.grid.ColumnModel([prorownum1, {
			header : '储备类型',
			dataIndex : 'f11',
			sortable : true,
			width : 100
		}, {
			header : '目前金额',
			dataIndex : 'f22',
			align:'right',
			renderer: money('0,000.00'),
			sortable : true,
			width : 100
		}, {
			header : '累计年数',
			dataIndex : 'f33',
			sortable : true,
			align:'right',
			width : 100
		}, {
			header : '折现率',
			dataIndex : 'f44',
			align:'right',
			sortable : true,
			width : 100
		}, {
			header : '累计现值',
			dataIndex : 'f55',
			renderer: money('0,000.00'),
			align:'right',
			sortable : true,
			width : 100
		}]);

// 列表定义
var Grid1 = new Ext.grid.GridPanel({
	title : '目前计划的储备资金',
	autoHeight : true,
	region : 'center',
	store : prodStore1,
	cm : procm1,
	bbar : new Ext.PagingToolbar({
				pageSize : '10',
				store : prodStore1,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录"
			})
});

// 列表数据装载
prodStore1.load(data1);

var form33 =new Ext.FormPanel({			
			border:true,
			frame : true,
			labelAlign : 'right',
			items:[{
				layout:'column',
				labelWidth:150,
				items:[{
					layout:'form',
					columnWidth:1,
					items:[{			
						style:'padding:0px 0px 0px 0px',
						 html : '<p style="line-height:30px;font-size:20px; text-align:center">'
								+ '您是否对以下规划目标有更详细的需求：</p>'
			    }
			    	
					
			    ]
		}]
		},{
				layout:'column',
				//labelWidth:150,
				items:[{
					layout:'form',
					columnWidth:.25,
					items:[new Ext.form.Checkbox({
			    		name:'buyhorsePlan',
						clearCls:'allow-float',
						itemCls:'float-left',
						boxLabel:'购房规划'})]
				},{
					layout:'form',
					columnWidth:.25,
					items:[new Ext.form.Checkbox({
			    		name:'buyhorsePlan1',
						clearCls:'allow-float',
						itemCls:'float-left',
						boxLabel:'养老规划'})]
				},{
					layout:'form',
					columnWidth:.25,
					items:[new Ext.form.Checkbox({
			    		name:'buyhorsePlan2',
						clearCls:'allow-float',
						itemCls:'float-left',
						boxLabel:'教育规划'})]
				},{
					layout:'form',
					columnWidth:.25,
					items:[new Ext.form.Checkbox({
			    		name:'buyhorsePlan3',
						clearCls:'allow-float',
						itemCls:'float-left',
						boxLabel:'保险规划'})]
				},{
					layout:'form',
					columnWidth:.25,
					items:[new Ext.form.Checkbox({
			    		name:'buyhorsePlan4',
						clearCls:'allow-float',
						itemCls:'float-left',
						boxLabel:'购车规划'})]
				},{
					layout:'form',
					columnWidth:.25,
					items:[new Ext.form.Checkbox({
			    		name:'buyhorsePlan5',
						clearCls:'allow-float',
						itemCls:'float-left',
						boxLabel:'投资规划'})]
				}]
		}
			]

});
var panel4=new Ext.Panel({
			frame:true,
			height : 400,
			layout:'fit',
			autoScroll : true,
			title:'综合财务安排评估',
			buttonAlign : "center",
			items:[{
				layout:'form',
				items:[Grid,Grid1,form33]
			}]
});
var bgWin = new Ext.Window({
			closeAction : 'hide',
			height : 430,
			width : 840,
			
			buttonAlign : 'center',
			layout : 'fit',
			modal : true,
			items : [{
				layout:'form',
				autoScroll : true,
				items:[form1,fd_01,panel3,panel4]
			}],
			buttons : [{
						text:'推荐产品',
						handler:function(){
							win_tjcp.show();
						}
				},{
						text : '生成报告',
						handler : function() {
							Ext.Msg.alert('提示','生成报告成功!');
						}
					},{
						text:'保存 ',
						handler:function(){
							Ext.Msg.alert("提示","保存成功!");
						}
					}]
		});