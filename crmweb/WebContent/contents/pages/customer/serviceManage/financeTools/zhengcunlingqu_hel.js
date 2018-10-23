
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var cxcqStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['一年', '1'],['三年', '3'],['五年', '5']]
    });
	var titleForm = new Ext.form.FormPanel({
		title:null,
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
					value:'整存零取计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;整存零取存款指客户须一次存入，然后按期定额支取的储蓄品种。本计算器可依据一',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield',
					value:'定要求计算出整存零取存款的每次支取金额和所得利息金额（已扣除利息税），并可反',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield',
					value:'向计算整存零取的初始存入金额和储蓄存期。',
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
				columnWidth:.62,
				layout:'form',
				items:[{
					fieldLabel:'计算项目',
					xtype:'radiogroup',
					anchor:'100%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '每次支取金额',
						name: 'jsxm',
						inputValue: '1',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								var mczqMoney = Ext.getCmp("mczqMoney");
								mczqMoney.setVisible(false);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(true);
								
								
								var result1 = Ext.getCmp("result1");
								result1.setVisible(true);
								var result2 = Ext.getCmp("result2");
								result2.setVisible(false);
								var result3 = Ext.getCmp("result3");
								result3.setVisible(false);
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
								var mczqMoney = Ext.getCmp("mczqMoney");
								mczqMoney.setVisible(true);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(false);
								
								var result1 = Ext.getCmp("result1");
								result1.setVisible(false);
								var result2 = Ext.getCmp("result2");
								result2.setVisible(true);
								var result3 = Ext.getCmp("result3");
								result3.setVisible(false);
							}
						}
					},{  
						boxLabel: '储蓄存期',
						name: 'jsxm',
						inputValue:'3',
						listeners : {
							"focus" : function() {
								var mczqMoney = Ext.getCmp("mczqMoney");
								mczqMoney.setVisible(true);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(true);
								
								var result1 = Ext.getCmp("result1");
								result1.setVisible(false);
								var result2 = Ext.getCmp("result2");
								result2.setVisible(false);
								var result3 = Ext.getCmp("result3");
								result3.setVisible(true);
							}
						}
					}] 
				},{
					xtype:'numberfield',
					id:'startMoney',
					name:'startMoney',
					fieldLabel:'存入金额(元)',
					minValue:0,
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'mczqMoney',
					name:'mczqMoney',
					fieldLabel:'每次支取金额(元)',
					minValue:0,
					hidden:true,
					anchor:'80%'
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
                    value:'1',
                    anchor : '80%',
                    listeners : {
						'select' : function(combo) {
							var cxcq = inputForm.form.findField('cxcq').getValue();
							if(cxcq == '1'){
								Ext.getCmp("rateYear").setValue("2.85");
							}else if(cxcq == '3'){
								Ext.getCmp("rateYear").setValue("2.90");
							}else if(cxcq == '5'){
								Ext.getCmp("rateYear").setValue("3.0");
							}
						}
					}
                },{
					xtype:'datefield',
					id:'startDate',
					name:'startDate',
					fieldLabel:'存入日期',
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
					value:'2.85',
					minValue:0,
					allowBlank:false,
					anchor:'80%'
				},{
				columnWidth:.58,
				layout:'form',
				items:[{
					fieldLabel:'支取频度',
					xtype:'radiogroup',
					anchor:'80%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '每月',
						name: 'zqpd',
						inputValue: '1'
					},{  
						boxLabel: '每季',
						name: 'zqpd',
						inputValue: '3'
					},{  
						boxLabel: '每半年',
						name: 'zqpd',
						inputValue: '6'
					}] 
				}]
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
				var InterestTax = 0.00; //利息税
				var nc = 12 / parseFloat(formValue.zqpd); //年次数
				var cxcq = parseFloat(inputForm.form.findField('cxcq').getValue());
				switch(jsxm){
					case 1:
						var startMoney = parseFloat(formValue.startMoney);
						if(formValue.startMoney == "" || isNaN(startMoney)){
							Ext.Msg.alert('提示','请输入存入金额');
							return false;
						}
						var startDate = formValue.startDate;
						var result1 = startMoney / (cxcq * nc);
						var sdlxje = result1 * (nc * cxcq + 1) / 2 * (nc * cxcq) * (rateYear / nc);
						sdlxje = sdlxje * (1 - InterestTax);
						//var kclxsje = ();
		
					    Ext.getCmp("result1").setValue(result1.toFixed(2));
					    Ext.getCmp("sdlxje").setValue(sdlxje.toFixed(2));
					    Ext.getCmp("kclxsje").setValue("0.00");
						break;
					case 2:
						var mczqMoney = parseFloat(formValue.mczqMoney);
						if(formValue.mczqMoney == "" || isNaN(mczqMoney)){
							Ext.Msg.alert('提示','请输入每次支取金额');
							return false;
						}
						var startDate = formValue.startDate;
						
						var result2 = mczqMoney * cxcq * nc;
						var sdlxje = mczqMoney * (nc * cxcq + 1) / 2 * (nc * cxcq) * (rateYear / nc);
						sdlxje = sdlxje * (1 - InterestTax);
					    //var kclxsje = result2 * (rateYear/360) * dayNum * InterestTax;
					    Ext.getCmp("result2").setValue(result2.toFixed(2));
					    Ext.getCmp("sdlxje").setValue(sdlxje.toFixed(2));
					    Ext.getCmp("kclxsje").setValue("0.00");
						break;
					case 3:
						var startMoney = parseFloat(formValue.startMoney);
						var mczqMoney = parseFloat(formValue.mczqMoney);
						if(formValue.startMoney == "" || isNaN(startMoney)){
							Ext.Msg.alert('提示','请输入存入金额');
							return false;
						}
						if(formValue.mczqMoney == "" || isNaN(mczqMoney)){
							Ext.Msg.alert('提示','请输入每次支取金额');
							return false;
						}
						if((mczqMoney * nc) > startMoney){
							Ext.Msg.alert('提示','存入金额必须大于等于每次支取金额的'+ nc + "倍");
							return false;
						}
						var startDate = formValue.startDate;
						
						var sdlxje = mczqMoney * (nc * cxcq + 1) / 2 * (nc * cxcq) * (rateYear / nc);
						sdlxje = sdlxje * (1 - InterestTax);
						var result3 = startMoney / (mczqMoney * nc);
						
						Ext.getCmp("result3").setValue("计算得出的储蓄存期为"+result3.toFixed(2)+"年");
						Ext.getCmp("sdlxje").setValue(sdlxje.toFixed(2));
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
				columnWidth:.60,
				layout:'form',
				items:[{
					xtype:'textfield',
					id:'result1',
					fieldLabel:'每次支取金额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'result2',
					fieldLabel:'存入金额(元)',
					anchor:'80%',
					value:'计算得出',
					hidden:true,
					readOnly:true
				},{
					xtype:'textfield',
					id:'result3',
					fieldLabel:'储蓄存期(年)',
					anchor:'80%',
					value:'计算得出',
					hidden:true,
					readOnly:true
				},{
					xtype:'textfield',
					id:'sdlxje',
					fieldLabel:'所得利息金额(元)',
					anchor:'80%',
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
	
	/**
	 * s1 格式 "yyyy-MM-dd"
	 * s2 格式 "yyyy-MM-dd"
	 * 返回两个日期相差天数(s2 - s1)
	 */
	var calcuDay = function(s1,s2){
		var date1 = strToDate(s1);
		var date2 = strToDate(s2);
		var day = parseInt(Math.abs(date2 - date1)) / (24 * 60 * 60 * 1000);
		return day;
	};
	
	/**
	 * s 格式 "yyyy-MM-dd"
	 */
	var strToDate = function(s){
		return new Date(s.substr(0,4) + "/" + s.substr(5,2) + "/" + s.substr(8,2));
	};
	
	/**
	 * s 格式 "yyyy-MM-dd"
	 */
	var dateToStr = function(date){
		var s = date.getFullYear() + "-";
		s += ((date.getMonth()+1) < 10 ? ("0" + (date.getMonth() + 1)) : (date.getMonth() + 1)) + "-";
		s += (date.getDate() < 10 ) ? "0" + date.getDate() : date.getDate();
		return  s;
	};
	
	/**
	 * s1 格式 "yyyy-MM-dd"
	 * days 加上N天
	 */
	function addday(s1,days){
		var date = strToDate(s1);
		date.setDate(date.getDate() + days);
		return dateToStr(date);
	}
	
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