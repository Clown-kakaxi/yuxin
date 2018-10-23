
Ext.onReady(function(){
	Ext.QuickTips.init();
    
	var titleForm = new Ext.form.FormPanel({
		frame:true,
		labelAlign:'middle',
		region:'north',
		height:100,
		border: false,
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:1,
				items:[{
					xtype:'displayfield',
					value:'基金申（认）购费用计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;在输入框填入数字，采用外扣法计算。',
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
					xtype:'numberfield',
					id:'a',
					name:'a',
					fieldLabel:'申（认）购金额(元)',
					allowBlank:false,
					minValue:0,
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'b',
					name:'b',
					fieldLabel:'单位基金净值(元)',
					allowBlank:false,
					minValue:0,
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'c',
					name:'c',
					fieldLabel:'申（认）购费率(%)',
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
				var v_feerate = parseFloat(formValue.c);
				var v_cash = parseFloat(formValue.a);
				var v_netvalue = parseFloat(formValue.b);
				
		        var rst = 0.0;
		        rst = v_feerate * v_cash / 100;
		        Ext.getCmp("result1").setValue(round(rst / (1 + v_feerate / 100), 2));
		        Ext.getCmp("result2").setValue(round((v_cash - rst / (1 + v_feerate / 100)) / v_netvalue, 2));
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
				items:[
					{  xtype:'textfield',id:'result1',fieldLabel:'手续费(元)',anchor:'90%',value:'计算得出',readOnly:true},
					{  xtype:'textfield',id:'result2',fieldLabel:'成交份额(份)',anchor:'90%',value:'计算得出',readOnly:true}
				]
			}]
		}]
	});
	
	var round = function(num, precision) {
	    return Math.round(num * Math.pow(10, precision)) / (Math.pow(10, precision));
	};
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