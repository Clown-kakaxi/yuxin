
Ext.onReady(function(){
	Ext.QuickTips.init();
    
	var titleForm = new Ext.form.FormPanel({
		frame:true,
		labelAlign:'middle',
		region:'north',
		height:80,
		border: false,
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:1,
				items:[{
					xtype:'displayfield',
					value:'基金收益费用计算器',
					style: {
						marginTop:'20px',
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
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
					fieldLabel:'投入本金(元)',
					allowBlank:false,
					minValue:0,
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'b',
					name:'b',
					fieldLabel:'收回金额(元)',
					allowBlank:false,
					minValue:0,
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'c',
					name:'c',
					fieldLabel:'持有期限(天)',
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
				var c = parseFloat(formValue.c);
				var a = parseFloat(formValue.a);
				var b = parseFloat(formValue.b);
				
		        var d = 0;
		        var e = 0;
		        d = 100 * (b - a) / a;
		        e = d / c * 365;
		        e = e.toFixed(2);
		        d = d.toFixed(2);
		        Ext.getCmp("result2").setValue(e);
		        if (e == "") {
		        	 Ext.getCmp("result2").setValue("0");
		        } else {
		            if (parseInt(e) < 0) {
		               Ext.getCmp("result2").setValue(e);
		            }
		            else {
		               Ext.getCmp("result2").setValue(+e);
		            }
		        }
		        Ext.getCmp("result1").setValue(d);
		        if (d == "" || d == 0) {
		        	Ext.getCmp("result1").setValue("0");
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
					{  xtype:'textfield',id:'result1',fieldLabel:'持有期总收益率(%)',anchor:'90%',value:'计算得出',readOnly:true},
					{  xtype:'textfield',id:'result2',fieldLabel:'持有期年化收益率(%)',anchor:'90%',value:'计算得出',readOnly:true}
				]
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