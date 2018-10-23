/**
 * 财富管理->业务需求->止损设置：JS文件；hujun；2013-10-21
 */

	var custSelectPartAdd1 = new Com.yucheng.bcrm.common.CustomerQueryField({
		fieldLabel : '客户名称',
		labelWidth : 100,
		name : 'custName',
		id:'custName1',
		custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
		custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
		singleSelected : true,// 单选复选标志
		editable : false,
		allowBlank : false,
		blankText : '此项为必填项，请检查！',
		anchor : '95%',
		hiddenName : 'custId'
	});
	var addProd1 = new Com.yucheng.crm.common.ProductManage({
		xtype : 'productChoose',
		fieldLabel : '产品',
		labelStyle : 'text-align:right;',
		name : 'prodName',
		id:'prodName1',
		hiddenName : 'prodId',
		singleSelect : false,
		allowBlank : false,
		blankText : '此项为必填项，请检查！',
		anchor : '95%'
	});
	var addAccount1 = new Ext.form.TextField({
		 fieldLabel:'帐号',
		 name:'accounts',
		 id:'accounts1',
		 anchor:'95%'
	});
	
	 var editPanel = new Ext.form.FormPanel({
		 labelWidth : 100,
		 //height : 215,
		 frame : true,
		 labelAlign : 'right',
		 region : 'center',
		 autoScroll : true,
		 buttonAlign : "center",
		 items:[{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'textfield',
					 fieldLabel:'规则名称',
					 name:'ruleName',
					 id:'ruleName1',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
						xtype:'combo',
						id:'ruleType1',
						name:'ruleType',
						triggerAction:'all',
						anchor:'90%',
					//	lazyRender:true,
						fieldLabel:'规则类型',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 4,
				        fields: ['value','displayText'],
				        data: [['1', '客户'], ['2', '账户'],['3','产品']]
				               }),
				       valueField:'value',
				       displayField:'displayText',				 
				       listeners : {
								'select' : function(combo) {
									distTaskTypeAddChange1();
								}
							}
				 }]
			 }]
		 },{
				 layout:'form',
				 items:[custSelectPartAdd1,addProd1,addAccount1]
			 
		 },{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'textfield',
					 fieldLabel:'止损阀值',
					 name:'ruleLimit',
					 id:'ruleLimit1',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'datefield',
					 fieldLabel:'开始日期',
					 format:'Y-m-d',
					 name:'ruleStar',
					 id:'ruleStar1',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'datefield',
					 fieldLabel:'截止日期',
					 format:'Y-m-d',
					 name:'ruleEnd',
					 id:'ruleEnd1',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
						xtype:'combo',
						name:'remindType',
						id:'remindType1',
						triggerAction:'all',
						anchor:'90%',
						fieldLabel:'规则类型',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 3,
				        fields: ['value','displayText'],
				        data: [[1, '站内信'], [2, '短信']]
				               }),
				       valueField:'value',
				       displayField:'displayText'
				 
				 }]
			 }]
		 },{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:1,
					 items:[{
						 xtype:'textarea',
						 id:'textarea1',
						 fieldLabel:'描述',
						 name:'describe',
						 height:'100',
						 anchor:'95%'
					 }]}]	 
		 }]
	 });
	// “规则类型”下拉框选择值时，处理逻辑
		 function distTaskTypeAddChange1() {
			 	var Type = editPanel.form.findField('ruleType').getValue();
			 	if (Type == "1") {
			 		// 规则类型为“客户”
			 		Ext.getCmp('custName1').setVisible(true);
			 		Ext.getCmp('custName1')["allowBlank"] = false;
			 		Ext.getCmp('prodName1').setVisible(false);
			 		Ext.getCmp('prodName1')["allowBlank"] = true;
			 		Ext.getCmp('accounts1').setVisible(false);
			 		Ext.getCmp('accounts1')["allowBlank"] = true;
			 	} else if (Type == "2") {
			 		// 规则类型为：账户
			 		Ext.getCmp('custName1').setVisible(false);
			 		Ext.getCmp('custName1')["allowBlank"] = true;
			 		Ext.getCmp('prodName1').setVisible(false);
			 		Ext.getCmp('prodName1')["allowBlank"] = true;
			 		Ext.getCmp('accounts1').setVisible(true);
			 		Ext.getCmp('accounts1')["allowBlank"] = false;
			 	}else if(Type=='3'){
			 		//规则类型为：产品
			 		Ext.getCmp('custName1').setVisible(false);
			 		Ext.getCmp('custName1')["allowBlank"] = true;
			 		Ext.getCmp('prodName1').setVisible(true);
			 		Ext.getCmp('prodName1')["allowBlank"] = false;
			 		Ext.getCmp('accounts1').setVisible(false);
			 		Ext.getCmp('accounts1')["allowBlank"] = true;
			 	}
			 };
	 var editRuleWind=new Ext.Window({
	   		title:'维护规则',
	   		width:880,
	   		height:420,
	   		closeAction:'hide',
	   		closable:true,
	   		maximizable:true,
	   		buttonAlign:'center',
	   		border:false,
	   		layout:'fit',
	   		draggable:true,
	   		collapsible:true,
	   		titleCollapse:true,
	   		items:[editPanel],
	   		buttons:[{
	   			text:'保 存',
	   			handler:function(){
	   				Ext.Msg.alert("提示","保存成功！");
	   				editRuleWind.hide();
	   				editPanel.getForm().reset();
	   		}
	   		}]
	   	});

