
Ext.onReady(function(){
	Ext.QuickTips.init();
	var cxcqStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['三个月', '3'],['半年', '6'],['一年', '12'],['二年', '24'],['三年', '36'],['五年', '60']]
    });
	var titleForm = new Ext.form.FormPanel({
		frame:true,
		labelAlign:'middle',
		region:'north',
		height:125,
		border: false,
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:1,
				items:[{
					xtype:'displayfield',
					value:'整存整取计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;银行存款是使用较多的投资方式，通过本计算器，可以对整存整取的初始存入金额或',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield',
					value:'到期本息总额进行计算。其中，到期本息总额为扣除利息税后的净值。',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				}]
			}]
		}]
	});
	var inputForm = new Ext.form.FormPanel({
		labelWidth:200,
		frame:true,
		border: false,
		buttonAlign:'left',//按钮对齐方式
		labelAlign:'right',
		region:'center',
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.58,
				layout:'form',
				items:[{
					fieldLabel:'计算项目',
					xtype:'radiogroup',
					anchor:'100%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '到期本息总额',
						name: 'jsxm',
						inputValue: '1',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								var dqbxMoney = Ext.getCmp("dqbxMoney");
								dqbxMoney.setVisible(false);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(true);
								
								
								var result1 = Ext.getCmp("result1");
								result1.setVisible(true);
								var result2 = Ext.getCmp("result2");
								result2.setVisible(false);
							}
						}
					},{  
						boxLabel: '初期存入金额',
						name: 'jsxm',
						inputValue:'2',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								var dqbxMoney = Ext.getCmp("dqbxMoney");
								dqbxMoney.setVisible(true);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(false);
								
								
								var result1 = Ext.getCmp("result1");
								result1.setVisible(false);
								var result2 = Ext.getCmp("result2");
								result2.setVisible(true);
							}
						}
					}] 
				},{
                    xtype : 'combo',
                    fieldLabel : '储蓄存期',
                    mode:'local',
                    emptyText:'请选择',
                    store:cxcqStore,
                    triggerAction:'all',
                    valueField:'value',
                    editable : false,
                    displayField:'key',
                    id : 'cxcq',
                    name : 'cxcq',
                    value:'3',
                    anchor : '80%',
                    listeners : {
						'select' : function(combo) {
							var cxcq = inputForm.form.findField('cxcq').getValue();
							if(cxcq == '3'){
								Ext.getCmp("rateYear").setValue("2.6");
							}else if(cxcq == '6'){
								Ext.getCmp("rateYear").setValue("2.8");
							}else if(cxcq == '12'){
								Ext.getCmp("rateYear").setValue("3.0");
							}else if(cxcq == '24'){
								Ext.getCmp("rateYear").setValue("3.75");
							}else if(cxcq == '36'){
								Ext.getCmp("rateYear").setValue("4.25");
							}else if(cxcq == '60'){
								Ext.getCmp("rateYear").setValue("4.75");
							}
						}
					}
                },{
					xtype:'datefield',
					id:'startDate',
					name:'startDate',
					fieldLabel:'初始存入日期',
					editable:false,
					format:'Y-m-d',
					value: new Date(),
					maxValue:'3000-01-01',
					minValue:'1910-01-01',
					allowBlank:false,
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'rateYear',
					name:'rateYear',
					fieldLabel:'年利率(%)',
					value:'2.6',
					minValue:0,
					allowBlank:false,
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'fdbl',
					name:'fdbl',
					fieldLabel:'利率浮动比例(%)',
					value:'0',
					allowBlank:false,
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'startMoney',
					name:'startMoney',
					fieldLabel:'存入金额(元)',
					minValue:0,
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'dqbxMoney',
					name:'dqbxMoney',
					fieldLabel:'到期本息总额(元)',
					hidden:true,
					minValue:0,
					anchor:'80%'
				}]
			}]
		}],
		buttons:[{
			text:'计算',
			style: {
				marginLeft:'200px'
			},
			handler:function(){
				if(!inputForm.getForm().isValid()){
					Ext.Msg.alert('提示','输入格式有误，请重新输入');
					return false;
				}
				var formValue = inputForm.getForm().getValues();
				var jsxm = formValue.jsxm * 1;
				var rateYear = parseFloat(formValue.rateYear) * 0.01;
				//浮动比例
				var fdbl = parseFloat(formValue.fdbl) * 0.01;
				rateYear = rateYear * (1+fdbl);
				
				var InterestTax = 0.00; //利息税
				var cxcq = parseFloat(inputForm.form.findField('cxcq').getValue());
				cxcq = cxcq / 12;
				switch(jsxm){
					case 1:
						var startMoney = parseFloat(formValue.startMoney);
						if(formValue.startMoney == "" || isNaN(startMoney)){
							Ext.Msg.alert('提示','请输入存入金额');
							return false;
						}
						var result1 =startMoney *(1 + rateYear *cxcq * (1-InterestTax));
						Ext.getCmp("result1").setValue(result1.toFixed(2));
					    Ext.getCmp("kclxsje").setValue("0.00");
						break;
					case 2:
						var dqbxMoney = parseFloat(formValue.dqbxMoney);
						if(formValue.dqbxMoney == "" ||  isNaN(dqbxMoney)){
							Ext.Msg.alert('提示','请输入到期本息总额');
							return false;
						}
						var result2 = dqbxMoney * 1.0 / (1 + rateYear * cxcq * (1 - InterestTax));
						Ext.getCmp("result2").setValue(result2.toFixed(2));
					    Ext.getCmp("kclxsje").setValue("0.00");
						break;
				}
			}
		},{
			text:'重置',
			handler:function(){
				inputForm.getForm().reset();
				outputForm.getForm().reset();
			}
		}]
	});
	var outputForm = new Ext.form.FormPanel({
		labelWidth:200,
		frame:true,
		border: false,
		height:175,
		labelAlign:'right',
		buttonAlign:'center',
		region: 'south',
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.58,
				layout:'form',
				items:[{
					xtype:'textfield',
					id:'result1',
					fieldLabel:'到期本息总额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'result2',
					fieldLabel:'存入金额(元)',
					anchor:'80%',
					hidden: true,
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'kclxsje',
					fieldLabel:'扣除利息税金额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				}]
			}]
		}]
	});
	
//	var view = new Ext.Viewport({
//		layout : 'fit',
//		items:[{
//			layout: 'border',
//			items:[titleForm,inputForm,outputForm]
//	    }]
//	});
	
	var p = new Ext.Panel({
	    renderTo:'viewDiv',
	    layout:'column',
	    width:840,
	    items: [{
			columnWidth:1,
			layout:'form',
			items:[titleForm,inputForm,outputForm]
		}]
	});
});