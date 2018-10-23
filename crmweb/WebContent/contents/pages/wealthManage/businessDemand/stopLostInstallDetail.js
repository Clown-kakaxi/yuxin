/**
 * 财富管理->业务需求->止损设置：JS文件；hujun；2013-10-21
 */

	var custSelectPartAdd2 = new Com.yucheng.bcrm.common.CustomerQueryField({
		fieldLabel : '客户名称',
		labelWidth : 100,
		name : 'custName',
		id:'custName2',
		disabled:true,
		custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
		custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
		singleSelected : true,// 单选复选标志
		editable : false,
		allowBlank : false,
		blankText : '此项为必填项，请检查！',
		anchor : '95%',
		hiddenName : 'custId'
	});
	var addProd2 = new Com.yucheng.crm.common.ProductManage({
		xtype : 'productChoose',
		fieldLabel : '产品',
		labelStyle : 'text-align:right;',
		name : 'prodName',
		disabled:true,
		id:'prodName2',
		hiddenName : 'prodId',
		singleSelect : false,
		allowBlank : false,
		blankText : '此项为必填项，请检查！',
		anchor : '95%'
	});
	var addAccount2 = new Ext.form.TextField({
		 fieldLabel:'帐号',
		 name:'accounts',
		 value:'2232882942',
		 disabled:true,
		 id:'accounts2',
		 anchor:'95%'
	});
	
	 var detailPanel = new Ext.form.FormPanel({
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
					 disabled:true,
					 id:'ruleName2',
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
						xtype:'combo',
						id:'ruleType2',
						name:'ruleType',
						triggerAction:'all',
						anchor:'90%',
						disabled:true,
					//	lazyRender:true,
						fieldLabel:'规则类型',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        //id: 0,
				        fields: ['value','displayText'],
				        data: [[1, '客户'], [2, '账户'],[3,'产品']]
				               }),
				       valueField:'value',
				       displayField:'displayText',				 
				       listeners : {
								'select' : function(combo) {
									distTaskTypeAddChange2();
								}
							}
				 }]
			 }]
		 },{
				 layout:'form',
				// columnWidth:.5,
				 items:[custSelectPartAdd2,addProd2,addAccount2]
			 
		 },{
			 layout:'column',
			 items:[{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
					 xtype:'textfield',
					 fieldLabel:'止损阀值',
					 name:'ruleLimit',
					 disabled:true,
					 id:'ruleLimit2',
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
					 disabled:true,
					 id:'ruleStar2',
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
					 id:'ruleEnd2',
					 disabled:true,
					 anchor:'90%'
				 }]
			 },{
				 layout:'form',
				 columnWidth:.5,
				 items:[{
						xtype:'combo',
						id:'remindType2',
						name:'remindType',
						triggerAction:'all',
						anchor:'90%',
						disabled:true,
						fieldLabel:'规则类型',
						mode:'local',
						store: new Ext.data.ArrayStore({
				        id: 2,
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
						 id:'2',
						 fieldLabel:'描述',
						 name:'describe',
						 disabled:true,
						 height:'100',
						 anchor:'95%'
					 }]}]	 
		 }]
	 });
	    Ext.getCmp('custName2').setVisible(false);		
		Ext.getCmp('prodName2').setVisible(false);		
		Ext.getCmp('accounts2').setVisible(false);
	// “规则类型”下拉框选择值时，处理逻辑
		detailPanel.on('show',function(){
			Ext.Msg.alert("dsewe");
			distTaskTypeAddChange();
		});
	 function distTaskTypeAddChange2() {
	 	var Type = detailPanel.form.findField('ruleType').getValue();
	 	if (Type == "1") {
	 		// 规则类型为“客户”
	 		Ext.getCmp('custName2').setVisible(true);
	 		Ext.getCmp('custName2')["allowBlank"] = false;
	 		Ext.getCmp('prodName2').setVisible(false);
	 		Ext.getCmp('prodName2')["allowBlank"] = true;
	 		Ext.getCmp('accounts2').setVisible(false);
	 		Ext.getCmp('accounts2')["allowBlank"] = true;
	 	} else if (Type == "2") {
	 		// 规则类型为：账户
	 		Ext.getCmp('custName2').setVisible(false);
	 		Ext.getCmp('custName2')["allowBlank"] = true;
	 		Ext.getCmp('prodName2').setVisible(false);
	 		Ext.getCmp('prodName2')["allowBlank"] = true;
	 		Ext.getCmp('accounts2').setVisible(true);
	 		Ext.getCmp('accounts2')["allowBlank"] = false;
	 	}else if(Type=='3'){
	 		//规则类型为：产品
	 		Ext.getCmp('custName2').setVisible(false);
	 		Ext.getCmp('custName2')["allowBlank"] = true;
	 		Ext.getCmp('prodName2').setVisible(true);
	 		Ext.getCmp('prodName2')["allowBlank"] = false;
	 		Ext.getCmp('accounts2').setVisible(false);
	 		Ext.getCmp('accounts2')["allowBlank"] = true;
	 	}
	 };
	 var detailRuleWind=new Ext.Window({
	   		title:'详情',
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
	   		items:[detailPanel],
	   		buttons:[{
	   			text:'返回',
	   			handler:function(){
	   				detailRuleWind.hide();
	   		}
	   		}]
	   	});

