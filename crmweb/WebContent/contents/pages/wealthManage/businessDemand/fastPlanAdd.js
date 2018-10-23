/**
 * 财富管理-业务需求：快速规划新增页面（静态页面） wzy，2013-09-05
 */

// 定义一个样式单，改变table中某些单元格的背景色
document.createStyleSheet().cssText = ".x-grid-back-color {background: #f1f2f4;}";
var custName = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '客户名称',
	labelWidth : 100,
	name : 'custName',
	custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
	custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
	singleSelected : true,// 单选复选标志
	editable : false,
	value:'王一平',
	anchor : '95%',
	hiddenName : 'custId',
	callback : function() {// 回调方法，给其它字段设置相关属性值
	}
});
var PlanNameForm = new Ext.form.FormPanel({
			frame : true,
			width : 770,
			height : 35,
			// layout : 'fit',
			border : false,
			labelAlign : 'right',
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .4,
									labelWidth : 80, // 标签宽度
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '快速规划名称',
												name : 'reportDate',
												anchor : '95%',
												value : '教育规划',
												disabled : false
											}]
								}]
					}]
		});
 var jbxxPanel =new Ext.form.FormPanel({
		 // title:'家庭收支信息',
	  //width:770,
	  border : false,
	  labelAlign : 'right',
	  items:[{
		  layout:'column',
		  labelWidth:150,
		  items:[{
			  layout:'form',
			  columnWidth:.5,
			  items:[custName,{
				  xtype:'textfield',
				  name:'parantMoney',
				  fieldLabel:'客户年龄',
				  value:'32',
				  anchor:'95%'
			  },{
				  xtype:'textfield',
				  name:'parantMoney1',
				  fieldLabel:'电话',
				  value:'13523441023',
				  anchor:'95%'
			  }]
		  },{
			  layout:'form',
			  columnWidth:.5,
			  items:[{
				  xtype:'textfield',
				  name:'parantMoney3',
				  fieldLabel:'您的风险偏好',
				  value:'B类',
				  anchor:'95%'
			  },{
				  xtype:'textfield',
				  name:'parantMoney4',
				  fieldLabel:'婚姻状态',
				  value:'已婚',
				  anchor:'95%'
			  },{
				  xtype:'textfield',
				  name:'parantMoney4',
				  value:'www.wangyiping@163.com',
				  fieldLabel:'Email',
				  anchor:'95%'
			  }]
		  }]
	  },{
		  layout:'column',
		  labelWidth:150,
		  items:[{
			  layout:'form',
			  columnWidth:1,
			  items:[{
				  xtype:'textfield',
				  name:'address',
				  fieldLabel:'地址',
				  value:'北京市朝阳区',
				  anchor:'97%'
			  }]
		  }]
	  }]
});
 var jtszxxPanel= new Ext.form.FormPanel({
	 			 // title:'家庭收支信息',
	 			//  width:770,
	 			  border : false,
				  labelAlign : 'right',
				  items:[{
					  layout:'column',
					  labelWidth:150,
					  items:[{
						  layout:'form',
						  columnWidth:.5,
						  items:[{
							  xtype:'textfield',
							  name:'parantMoney',
							  fieldLabel:'目前可投资金额(元)',
							
							  value:'2340000',
							  anchor:'95%'
						  },{
							  xtype:'textfield',
							  name:'parantMoney1',
							  fieldLabel:'每年的固定指出(旅游费，赠与费)',
							  
							  value:'23400',
							  anchor:'95%'
						  },{
							  xtype:'textfield',
							  name:'parantMoney2',
							  fieldLabel:'您退休前每月的现金储备',
							  
							  value:'4000',
							  anchor:'95%'
						  }]
					  },{
						  layout:'form',
						  columnWidth:.5,
						  items:[{
							  xtype:'textfield',
							  name:'parantMoney3',
							  fieldLabel:'每月家庭日常消费(生活费，交通费等)',
							 
							  value:'3400',
							  anchor:'95%'
						  },{
							  xtype:'textfield',
							  name:'parantMoney4',
							  fieldLabel:'通货膨胀',
							 
							  value:'2340',
							  anchor:'95%'
						  }]
					  }]
				  }]
 });
 var ylmbPanel =new Ext.form.FormPanel({
	  			title:'养老目标',
	  		//	width:770,
	  			border : false,
	  			labelAlign : 'right',
	  			items:[{
	  				layout:'column',
	  				labelWidth:150,
	  				items:[{
	  					layout:'form',
	  					columnWidth:.5,
	  					items:[{
	  						xtype:'textfield',
	  						name:'parantMoney5',
	  						fieldLabel:'您的计划退休年龄',
	  						value:'50',
	  						anchor:'95%'
	  					},{
	  						xtype:'textfield',
	  						name:'parantMoney6',
	  						fieldLabel:'您的预计寿命',
	  						value:'89',
	  						anchor:'95%'
	  					}]
	  				},{
	  					layout:'form',
	  					columnWidth:.5,
	  					items:[{
	  						xtype:'textfield',
	  						name:'parantMoney7',
	  						fieldLabel:'退休后每月您希望的生活支出',
	  						
							 value:'2340',
	  						anchor:'95%'
	  					}]
	  				}]
	  	}]

 });
 var gfmbPanel =new Ext.form.FormPanel({
		title:'购房目标',
		//width:770,
		border : false,
		labelAlign : 'right',
		items:[{
			layout:'column',
			labelWidth:150,
			items:[{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					name:'parantMoney8',
					fieldLabel:'您希望多少年后购置新的房屋(年)',
					value:'7',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney9',
					fieldLabel:'准备购置的房屋价值(万元)',
					value:'23',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney10',
					fieldLabel:'首付款比例(%)',
					value:'30',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney11',
					fieldLabel:'期望的贷款年限(年)',
					value:'10',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney12',
					fieldLabel:'当前房屋的总负债(万元)',
					value:'40',
					anchor:'95%'
				}]
			},{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					name:'parantMoney13',
					fieldLabel:'您现在的房屋值(万元)',
					value:'120',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney14',
					fieldLabel:'贷款利息(%)',
					value:'0.12',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney15',
					fieldLabel:'每月还款额(元)',
					value:'4300',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney16',
					fieldLabel:'剩余的还款年数',
					value:'9',
					anchor:'95%'
				}]
			}]
}]
});
 var znjymbPanel =new Ext.form.FormPanel({
		title:'子女教育目标',
		///width:770,
		border : false,
		labelAlign : 'right',
		items:[{
			layout:'column',
			labelWidth:150,
			items:[{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					name:'parantMoney17',
					fieldLabel:'预计子女年龄(未出生子女用负数表示)',
					value:'3',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney18',
					fieldLabel:'接受高等教育的年数',
					value:'4',
					anchor:'95%'
				}]
			},{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					name:'parantMoney19',
					fieldLabel:'预计子女个数',
					value:'1',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney20',
					fieldLabel:'预计每年高等教育费用',
					value:'120000',
					anchor:'95%'
				}]
			}]
}]

});
 var bxmbPanel =new Ext.form.FormPanel({
		title:'保险目标',
		//width:770,
		border : false,
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
					fieldLabel:'期望的投资收益率(%)',
					value:'1.34',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney22',
					fieldLabel:'您现在拥有的寿险保额',
					value:'4300',
					anchor:'95%'
				}]
			},{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					name:'parantMoney23',
					fieldLabel:'您期望给家人的保障年数',
					value:'20',
					anchor:'95%'
				},{
					xtype:'textfield',
					name:'parantMoney24',
					fieldLabel:'您家庭的供养人数',
					value:'4',
					anchor:'95%'
				}]
			}]
}]

});
 var fd_01 = new Ext.form.FieldSet({
		xtype : 'fieldset',
		title : '客户基本信息',
		//width : 770,
		// height : 300,
		autoScroll : true,
		labelAlign : 'right',
		collapsible : true,
		itemCls : 'x-check-group-alt',
		items : [jbxxPanel]
	});
 var fd_02 = new Ext.form.FieldSet({
		xtype : 'fieldset',
		title : '家庭收支信息',
		//width : 770,
		// height : 300,
		autoScroll : true,
		labelAlign : 'right',
		collapsible : true,
		itemCls : 'x-check-group-alt',
		items : [jtszxxPanel]
	});
var fd_03 = new Ext.form.FieldSet({
			xtype : 'fieldset',
			title : '财务目标',
		//	width : 770,
			// height : 300,
			autoScroll : true,
			labelAlign : 'right',
			collapsible : true,
			itemCls : 'x-check-group-alt',
			items : [ylmbPanel,gfmbPanel,znjymbPanel,bxmbPanel]
		});
var addTapPanel = new Ext.Panel({
	frame:true,
	height : 430,
	autoScroll : true,
	buttonAlign : "center",
	items : [PlanNameForm,fd_01,fd_02,fd_03]
});
