
Ext.onReady(function(){
	Ext.QuickTips.init();
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
					value:'存本取息计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;存本取息是指一次性存入较大金额，分次支取利息，到期支取本金的一种定期储蓄，',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield',
					value:'5000元起存，存期分一年、三年、五年。储户于开户次月起每月取息一次，以开户日为',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield',
					value:'每月取息日。提前支取的，按活期利率重新计息，并将已分期支付的利息扣回。',
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
				columnWidth:.50,
				layout:'form',
				items:[{
					fieldLabel:'储蓄存期',
					xtype:'radiogroup',
					anchor:'90%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '一年',
						name: 'cxcq',
						inputValue: '1',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								Ext.getCmp("rateYear").setValue(2.85);
							}
						}
					},{  
						boxLabel: '三年',
						name: 'cxcq',
						inputValue:'3',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								Ext.getCmp("rateYear").setValue(2.90);
							}
						}
					},{  
						boxLabel: '五年',
						name: 'cxcq',
						inputValue:'5',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								Ext.getCmp("rateYear").setValue(3.00);
							}
						}
					}] 
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
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'startMoney',
					name:'startMoney',
					fieldLabel:'初始存入金额(元)',
					allowBlank:false,
					minValue:5000,
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'rateYear',
					name:'rateYear',
					fieldLabel:'年利率(%)',
					value:'2.85',
					minValue:0,
					allowBlank:false,
					anchor:'90%'
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
				var zlx = formValue.startMoney * formValue.cxcq *  formValue.rateYear * 0.01;
				var myzqlxje = zlx / (formValue.cxcq * 12);
				var bxzegj = parseFloat(formValue.startMoney) + zlx ;
				Ext.getCmp("myzqlxje").setValue(myzqlxje.toFixed(2));
				Ext.getCmp("kclxsje").setValue("0.00");
				Ext.getCmp("bxzegj").setValue(bxzegj.toFixed(2));
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
		height:150,
		labelAlign:'right',
		buttonAlign:'center',
		region: 'south',
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.50,
				layout:'form',
				items:[{
					xtype:'textfield',
					id:'myzqlxje',
					fieldLabel:'每月支取利息金额(元)',
					anchor:'90%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'kclxsje',
					fieldLabel:'扣除利息税金额(暂不征收)(元)',
					anchor:'90%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'bxzegj',
					fieldLabel:'本息总额共计(元)',
					anchor:'90%',
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