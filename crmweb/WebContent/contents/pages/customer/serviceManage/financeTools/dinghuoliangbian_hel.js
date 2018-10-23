
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
					value:'定活两便计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;定活两便存款的优点在于兼顾了资金运用的收益性和灵活性。本计算器可方便的计算',
					style: {
						marginBottom:'5px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield',
					value:'出定活两便存款的到期本息总额（已扣除利息税）、初始存入金额和储蓄存期。',
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
								
								var tqDate = Ext.getCmp("tqDate");
								tqDate.setVisible(true);
								
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
								var dqbxMoney = Ext.getCmp("dqbxMoney");
								dqbxMoney.setVisible(true);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(false);
								
								var tqDate = Ext.getCmp("tqDate");
								tqDate.setVisible(true);
								
								var result1 = Ext.getCmp("result1");
								result1.setVisible(false);
								var result2 = Ext.getCmp("result2");
								result2.setVisible(true);
								var result3 = Ext.getCmp("result3");
								result3.setVisible(false);
							}
						}
					},{  
						boxLabel: '提取日期',
						name: 'jsxm',
						inputValue:'3',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								var dqbxMoney = Ext.getCmp("dqbxMoney");
								dqbxMoney.setVisible(true);
								
								var startMoney = Ext.getCmp("startMoney");
								startMoney.setVisible(true);
								
								var tqDate = Ext.getCmp("tqDate");
								tqDate.setVisible(false);
								
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
					id:'dqbxMoney',
					name:'dqbxMoney',
					fieldLabel:'到期本息总额(元)',
					minValue:0,
					hidden:true,
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'startMoney',
					name:'startMoney',
					fieldLabel:'初始存入金额(元)',
					minValue:0,
					anchor:'80%'
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
					xtype:'datefield',
					id:'tqDate',
					name:'tqDate',
					fieldLabel:'提取日期',
					editable:false,
					format:'Y-m-d',
					value: new Date(),
					maxValue:'3000-01-01',
					minValue:'1910-01-01',
					anchor:'80%'
				},{
					xtype:'numberfield',
					id:'rateYear',
					name:'rateYear',
					fieldLabel:'年利率(%)',
					value:'0.35',
					minValue:0,
					allowBlank:false,
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
				var InterestTax = 0.00; //利息税
				switch(jsxm){
					case 1:
						var startMoney = parseFloat(formValue.startMoney);
						if(formValue.startMoney == "" || isNaN(startMoney)){
							Ext.Msg.alert('提示','请输入存入金额');
							return false;
						}
						
						var startDate = formValue.startDate;
						var tqDate = formValue.tqDate;
						if(tqDate <= startDate){
							Ext.Msg.alert('提示','提取日期必须大于初始存入日期，请重新输入');
							return false;
						}
						//到期本息总额＝初始存入金额×（年利率/360）×（提取日期－初始存入日期）×InterestTax + 初始存入金额
						var dayNum = calcuDay(tqDate,startDate);
					    var result1 = startMoney * (rateYear / 360) * dayNum * (1 - InterestTax) + startMoney;
					    //var kclxsje = startMoney * (rateYear / 360) * dayNum * InterestTax;
					    Ext.getCmp("result1").setValue(result1.toFixed(2));
					    Ext.getCmp("kclxsje").setValue("0.00");
						break;
					case 2:
						var dqbxMoney = parseFloat(formValue.dqbxMoney);
						if(formValue.dqbxMoney == "" || isNaN(dqbxMoney)){
							Ext.Msg.alert('提示','请输入到期本息总额');
							return false;
						}
						var startDate = formValue.startDate;
						var tqDate = formValue.tqDate;
						if(tqDate <= startDate){
							Ext.Msg.alert('提示','提取日期必须大于初始存入日期，请重新输入');
							return false;
						}
						//初始存入金额=到期本息总额/ (1+（年利率/360）×（提取日期－初始存入日期）×InterestTax)
						var dayNum = calcuDay(tqDate,startDate);
						var result2 =dqbxMoney /( rateYear/360*dayNum*(1-InterestTax)+1);
					    //var kclxsje = dqbxMoney / (1 + (rateYear / 360) * dayNum * InterestTax);
					    Ext.getCmp("result2").setValue(result2.toFixed(2));
					    Ext.getCmp("kclxsje").setValue("0.00");
						break;
					case 3:
						var startMoney = parseFloat(formValue.startMoney);
						var dqbxMoney = parseFloat(formValue.dqbxMoney);
						if(formValue.startMoney == "" || isNaN(startMoney)){
							Ext.Msg.alert('提示','请输入存入金额');
							return false;
						}
						if(formValue.dqbxMoney == "" || isNaN(dqbxMoney)){
							Ext.Msg.alert('提示','请输入到期本息总额');
							return false;
						}
						var startDate = formValue.startDate;
						if(dqbxMoney <= startMoney){
							Ext.Msg.alert('提示','到期本息总额必须大于初始存入金额，请重新输入');
							return false;
						}
						var dayNum = (dqbxMoney - startMoney) * 360 / ( startMoney * (1- InterestTax) * rateYear);
						dayNum = Math.ceil(dayNum);
						var result3 = addday(startDate,dayNum);
						Ext.getCmp("result3").setValue(result3);
					    Ext.getCmp("kclxsje").setValue("0.00");
						
						//提取日期=开始日期+(到期本息总额-初始存入金额)/初始存入金额*360/InterestTax/年利率
					    //提取日期=开始日期+(到期本息总额-初始存入金额)/初始存入金额*360/InterestTax/年利率
//						valday=(valresult-valstart)*360/valstart/(1-InterestTax)/valrate;
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
		height:120,
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
					fieldLabel:'初始存入金额(元)',
					anchor:'80%',
					value:'计算得出',
					hidden:true,
					readOnly:true
				},{
					xtype:'textfield',
					id:'result3',
					fieldLabel:'提取日期',
					anchor:'80%',
					value:'计算得出',
					hidden:true,
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