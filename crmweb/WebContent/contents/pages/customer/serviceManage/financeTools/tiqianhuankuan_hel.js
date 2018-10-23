
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var ydkqxStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['2年(24期)', '24'],['3年(36期)', '36'],['4年(48期)', '48'],['5年(60期)', '60'],['6年(72期)', '72']
        		,['7年(84期)', '84'],['8年(96期)', '96'],['9年(108期)', '108'],['10年(120期)', '120'],['11年(132期)', '132']
        		,['12年(144期)', '144'],['13年(156期)', '156'],['14年(168期)', '168'],['15年(180期)', '180'],['20年(240期)', '240']
        		,['25年(300期)', '300'],['30年(360期)', '360']]
    });
    
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
					value:'提前还款计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'10px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;提前还贷计算器可以帮您计算提前还贷节省的利息支出。',
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
				columnWidth:.80,
				layout:'form',
				items:[{
					xtype:'numberfield',
					id:'startMoney',
					name:'startMoney',
					fieldLabel:'贷款金额(元)',
					allowBlank:false,
					minValue:0,
					anchor:'50%'
				},{
                    xtype : 'combo',
                    fieldLabel : '原贷款期限',
                    mode:'local',
                    emptyText:'请选择',
                    store:ydkqxStore,
                    triggerAction:'all',
                    valueField:'value',
                    editable : false,
                    displayField:'key',
                    id : 'ydkqx',
                    name : 'ydkqx',
                    value: '120',
                    anchor : '50%',
                    listeners : {
						'select' : function(combo) {
							var formValue = inputForm.getForm().getValues();
					        var dktype = parseInt(formValue.dktype); 
					        var ydkqx = parseFloat(inputForm.form.findField('ydkqx').getValue());
							switch (dktype){
								case 1:
									if(ydkqx > 60){
										Ext.getCmp("rateYear").setValue(4.5);
									}else if(ydkqx >0 && ydkqx <= 60){
										Ext.getCmp("rateYear").setValue(4.0);
									}
									break;
								case 2:
									if(ydkqx > 60){
										Ext.getCmp("rateYear").setValue(6.55);
									}else if(ydkqx > 36 && ydkqx <= 60){
										Ext.getCmp("rateYear").setValue(6.40);
									}else if(ydkqx > 12 && ydkqx <= 36){
										Ext.getCmp("rateYear").setValue(6.15);
									}else if(ydkqx > 6 && ydkqx <= 12){
										Ext.getCmp("rateYear").setValue(6.00);
									}else if(ydkqx >0 && ydkqx <= 6){
										Ext.getCmp("rateYear").setValue(5.60);
									}
									break;
							}
						}
					}
                },{
					xtype:'numberfield',
					id:'rateYear',
					name:'rateYear',
					fieldLabel:'年利率(%)',
					value:'4.5',
					minValue:0,
					allowBlank:false,
					anchor:'50%'
				},{
					xtype:'datefield',
					id:'startDate',
					name:'startDate',
					fieldLabel:'第一次还款日期',
					editable:false,
					format:'Y-m-d',
					value: new Date(),
					maxValue:'3000-01-01',
					minValue:'1910-01-01',
					allowBlank:false,
					anchor:'50%'
				},{
					xtype:'datefield',
					id:'yjtqhkDate',
					name:'yjtqhkDate',
					fieldLabel:'预计提前还款日期',
					editable:false,
					format:'Y-m-d',
					value: new Date(),
					maxValue:'3000-01-01',
					minValue:'1910-01-01',
					allowBlank:false,
					anchor:'50%'
				},{
					fieldLabel:'贷款类型',
					xtype:'radiogroup',
					anchor:'60%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '公积金贷款',
						name: 'dktype',
						inputValue: '1',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								var ydkqx = parseFloat(inputForm.form.findField('ydkqx').getValue());
								if(ydkqx > 60){
									Ext.getCmp("rateYear").setValue(4.5);
								}else if(ydkqx >0 && ydkqx <= 60){
									Ext.getCmp("rateYear").setValue(4.0);
								}
							}
						}
					},{  
						boxLabel: '商业贷款',
						name: 'dktype',
						inputValue:'2',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								var ydkqx = parseFloat(inputForm.form.findField('ydkqx').getValue());
								if(ydkqx > 60){
									Ext.getCmp("rateYear").setValue(6.55);
								}else if(ydkqx > 36 && ydkqx <= 60){
									Ext.getCmp("rateYear").setValue(6.40);
								}else if(ydkqx > 12 && ydkqx <= 36){
									Ext.getCmp("rateYear").setValue(6.15);
								}else if(ydkqx > 6 && ydkqx <= 12){
									Ext.getCmp("rateYear").setValue(6.00);
								}else if(ydkqx >0 && ydkqx <= 6){
									Ext.getCmp("rateYear").setValue(5.60);
								}
							}
						}
					}] 
				},{
					fieldLabel:'还款方式',
					xtype:'radiogroup',
					anchor:'100%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '一次提前还清',
						name: 'hktype',
						inputValue: '1',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								Ext.getCmp("bftqhkMoney").setVisible(false);
							}
						}
					},{  
						boxLabel: '部分提前还款(不含当月应还款额)',
						name: 'hktype',
						inputValue:'2',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
								Ext.getCmp("bftqhkMoney").setVisible(true);
							}
						}
					}] 
				},{
					xtype:'numberfield',
					id:'bftqhkMoney',
					name:'bftqhkMoney',
					fieldLabel:'部分提前还款金额(元)',
					hidden:true,
					minValue:0,
					anchor:'50%'
				},{
					fieldLabel:'处理方式',
					xtype:'radiogroup',
					anchor:'100%',
					defaultType: 'radio', 
					items:[{  
						checked: true,
						boxLabel: '缩短还款年限，月还款额基本不变',
						name: 'cltype',
						inputValue: '1',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
							}
						}
					},{  
						boxLabel: '减少月还款额，还款期不变',
						name: 'cltype',
						inputValue:'2',
						listeners : {
							"check" : function(a,b) {
								if(!b){
									return;
								}
							}
						}
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
				var cltype = parseInt(formValue.cltype);
				var hktype = parseInt(formValue.hktype);
				var startMoney = parseFloat(formValue.startMoney);//贷款金额
				var bftqhkMoney = parseFloat(formValue.bftqhkMoney); //部分提前还款金额
				var ydkqx = parseFloat(inputForm.form.findField('ydkqx').getValue()); //原贷款期限
				var rateYear = parseInt(formValue.rateYear) * 0.01 / 12;//贷款月利率
				var startDate = formValue.startDate;
				var yjtqhkDate = formValue.yjtqhkDate;
				if(yjtqhkDate <= startDate){
					Ext.Msg.alert('提示','预计提前还款日期必须大于第一次还款日期，请重新输入');
					return false;
				}
				
				startDate = strToDate(startDate);
				yjtqhkDate = strToDate(yjtqhkDate); 
				
				 //已还贷款期数
				var yhdkqs = (parseInt(yjtqhkDate.getFullYear()) * 12 + parseInt(yjtqhkDate.getMonth() + 1)) - (parseInt(startDate.getFullYear()) * 12 + parseInt(startDate.getMonth() + 1));
			    if (yhdkqs < 0 || yhdkqs > ydkqx) {
			    	Ext.Msg.alert('提示','预计提前还款日期与第一次还款日期有矛盾，请重新输入');
			        return false;
			    }
				var yhk = startMoney * (rateYear * Math.pow((1 + rateYear), ydkqx)) / (Math.pow((1 + rateYear), ydkqx) - 1);
    			//yhk = dkzys * (dklv * Math.pow((1 + dklv), s_yhkqs)) / (Math.pow((1 + dklv), s_yhkqs) - 1);
				var yhkjssj = Math.floor((parseInt(startDate.getFullYear()) * 12 + parseInt(startDate.getMonth() + 1) + ydkqx - 2) / 12) + "年" + ((parseInt(startDate.getFullYear()) * 12 + parseInt(startDate.getMonth() + 1) + ydkqx - 2) % 12 + 1) + "月";
    			//yhkjssj = Math.floor((parseInt(ylhkDate.getFullYear()) * 12 + parseInt(ylhkDate.getMonth() + 1) + s_yhkqs - 2) / 12) + "年" + ((parseInt(ylhkDate.getFullYear()) * 12 + parseInt(ylhkDate.getMonth() + 1) + s_yhkqs - 2) % 12 + 1) + "月";
    			var yhdkys = yhk * yhdkqs;

			    yhlxs = 0;
			    yhbjs = 0;
			    for (i = 1; i <= yhdkqs; i++) {
			        yhlxs = yhlxs + (startMoney - yhbjs) * rateYear;
			        yhbjs = yhbjs + yhk - (startMoney - yhbjs) * rateYear;
			    }

	    		remark = "";
			    if (hktype == 2) {
			    	if(formValue.bftqhkMoney == "" || isNaN(bftqhkMoney)){
						Ext.Msg.alert('提示','请输入部分提前还款金额');
						return false;
					}
			       // tqhkys = parseInt($("tqhkws").value) * 10000;
			        if (bftqhkMoney + yhk >= (startMoney - yhbjs) * (1 + rateYear)) {
			            remark = "您的提前还款额已足够还清所欠贷款！";
			            Ext.Msg.alert('提示',remark);
						return false;
			        } else {
			            yhbjs = yhbjs + yhk;
			            byhk = yhk + bftqhkMoney;
			            if (cltype == 1) {
			                yhbjs_temp = yhbjs + bftqhkMoney;
			                for (xdkqs = 0; yhbjs_temp <= startMoney; xdkqs++){
			                	yhbjs_temp = yhbjs_temp + yhk - (startMoney - yhbjs_temp) * rateYear;
			                }
			                xdkqs = xdkqs - 1;
			                xyhk = (startMoney - yhbjs - bftqhkMoney) * (rateYear * Math.pow((1 + rateYear), xdkqs)) / (Math.pow((1 + rateYear), xdkqs) - 1);
			                jslx = yhk * ydkqx - yhdkys - byhk - xyhk * xdkqs;
			                xdkjssj = Math.floor((parseInt(yjtqhkDate.getFullYear()) * 12 + parseInt(yjtqhkDate.getMonth() + 1) + xdkqs - 2) / 12) + "年" + ((parseInt(yjtqhkDate.getFullYear()) * 12 + parseInt(yjtqhkDate.getMonth() + 1) + xdkqs - 2) % 12 + 1) + "月";
			            } else {
			                xyhk = (startMoney - yhbjs - bftqhkMoney) * (rateYear * Math.pow((1 + rateYear), (ydkqx - yhdkqs))) / (Math.pow((1 + rateYear), (ydkqx - yhdkqs)) - 1);
			                jslx = yhk * ydkqx - yhdkys - byhk - xyhk * (ydkqx - yhdkqs);
			                xdkjssj = yhkjssj;
			            }
			        }
			    }
	
			    if (hktype == 1 || remark != "") {
			        byhk = (startMoney - yhbjs) * (1 + rateYear);
			        xyhk = 0;
			        jslx = yhk * ydkqx - yhdkys - byhk;
			        xdkjssj = yjtqhkDate.getFullYear() + "年" + (yjtqhkDate.getMonth() + 1) + "月";
			    }
	
			   	Ext.getCmp("yhke").setValue(yhk.toFixed(2));
			   	Ext.getCmp("yhkze").setValue(yhdkys.toFixed(2));
			   	Ext.getCmp("yhlxe").setValue(yhlxs.toFixed(2));
			   	Ext.getCmp("yzhhkq").setValue(yhkjssj);
			   	
			   	Ext.getCmp("gyychke").setValue(byhk.toFixed(2));
			   	Ext.getCmp("xeqyhke").setValue(xyhk.toFixed(2));
			   	Ext.getCmp("jslxzc").setValue(jslx.toFixed(2));
			   	Ext.getCmp("xdzhhkq").setValue(xdkjssj);
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
		labelWidth:120,
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
				columnWidth:.40,
				layout:'form',
				items:[{
					xtype:'textfield',
					id:'yhke',
					fieldLabel:'原月还款额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'yhkze',
					fieldLabel:'已还款总额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'yhlxe',
					fieldLabel:'已还利息额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'yzhhkq',
					fieldLabel:'原最后还款期',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				}]
			},{
				columnWidth:.40,
				layout:'form',
				items:[{
					xtype:'textfield',
					id:'gyychke',
					fieldLabel:'该月一次还款额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'xeqyhke',
					fieldLabel:'下月起月还款额(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'jslxzc',
					fieldLabel:'节省利息支出(元)',
					anchor:'80%',
					value:'计算得出',
					readOnly:true
				},{
					xtype:'textfield',
					id:'xdzhhkq',
					fieldLabel:'新的最后还款期',
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