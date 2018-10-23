
Ext.onReady(function(){
	Ext.QuickTips.init();
	var dkTypeStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [
                ['1','个人住房贷款'],
                ['2','个人旅游贷款'],
                ['3','个人综合消费贷款'],
                ['4','个人短期信用贷款'],
                ['5', '个人小额抵押贷款'],
                ['6', '个人汽车贷款'],
                ['7', '助学贷款'],
                ['8', '个人留学贷款'],
                ['9', '大额耐用消费品贷款'],
                ['10', '一手房按揭贷款'],
                ['11', '二手房按揭贷款'],
                ['12', '新车按揭贷款'],
                ['13', '旧车按揭贷款'],
                ['14', '船舶按揭贷款'],
                ['15', '设备按揭贷款'],
                ['16', '其他按揭贷款'],
                ['17', '生源地商业助学贷款'],
                ['18', '农户小额信用贷款'],
                ['19', '扶贫贴息贷款'],
                ['20', '农户联保贷款'],
                ['21', '扶贫贷款'],
                ['22', '住房贷款(买入\自建)'],
                ['23', '汽车消费贷款'],
                ['24', '其他消费贷款'],
                ['25', '住房装修贷款'],
                ['26', '耐用消费品贷款'],
                ['27', '旅游消费贷款'],
                ['28', '下岗再就业人员小额担保贷款'],
                ['29', '个体工商户贷款'],
                ['30', '农业机械贷款'],
                ['31', '营运车辆贷款'],
                ['32', '商户联保贷款'],
                ['33', '其他经营性贷款'],
                ['34', '妇女创业贷款'],
                ['35', '青年创业贷款'],
                ['36', '其他创业贷款'],
                ['37', '其他创业贷款（等额类）'],
                ['38', '易贷通贷款'],
                ['39', '委托贷款'],
                ['40', '个人综合授信额度'],
                ['41', '个人临时授信额度'],
                ['42', '自然人联保小组授信']
              ]
    });
    var hkTypeStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['1', '等额本息还款'],['2', '等额本金还款']]
    });
    var jxStore = new Ext.data.ArrayStore({
        fields : ['key', 'value'],
        data : [['1', '按季结息'],['2', '按月结息']]
    });
    
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
					value:'个人贷款计算器',
					style: {
						marginLeft:'200px',
						marginBottom:'20px'
					},
					anchor:'99%'
				},{
					xtype:'displayfield'
				},{
					xtype:'displayfield',
					value:'&nbsp;&nbsp;&nbsp;&nbsp;个人贷款计算器可以帮您计算个人贷款的数额。',
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
                    xtype : 'combo',
                    fieldLabel : '贷款种类',
                    mode:'local',
                    emptyText:'请选择',
                    store:dkTypeStore,
                    triggerAction:'all',
                    valueField:'key',
                    editable : false,
                    displayField:'value',
                    id : 'dkType',
                    name : 'dkType',
                    value:'1',
                    anchor : '90%',
                    listeners : {
						'select' : function(combo) {
						}
					}
                },{
                    xtype : 'combo',
                    fieldLabel : '结息方式',
                    mode:'local',
                    emptyText:'请选择',
                    store:jxStore,
                    triggerAction:'all',
                    valueField:'key',
                    editable : false,
                    displayField:'value',
                    id : 'jxfs',
                    name : 'jxfs',
                    value:'1',
                    anchor : '90%',
                    listeners : {
						'select' : function(combo) {
						}
					}
                },{
					xtype:'numberfield',
					id:'dkMoney',
					name:'dkMoney',
					fieldLabel:'贷款金额(元)',
					allowBlank:false,
					minValue:1,
					anchor:'90%'
				},{
					xtype:'numberfield',
					id:'dkMonth',
					name:'dkMonth',
					fieldLabel:'贷款期限(月)',
					allowBlank:false,
					minValue:1,
					anchor:'90%'
				},{
                    xtype : 'combo',
                    fieldLabel : '还款方式',
                    mode:'local',
                    emptyText:'请选择',
                    store:hkTypeStore,
                    triggerAction:'all',
                    valueField:'key',
                    editable : false,
                    displayField:'value',
                    id : 'hkType',
                    name : 'hkType',
                    value:'1',
                    anchor : '90%',
                    listeners : {
						'select' : function(combo) {
							outputForm.getForm().reset();
							var hkType = inputForm.form.findField('hkType').getValue();
							if(hkType == '1'){
								Ext.getCmp("mybx").setVisible(true);
								Ext.getCmp("yhkje").setVisible(false);
							}else{
								Ext.getCmp("mybx").setVisible(false);
								Ext.getCmp("yhkje").setVisible(true);
							}
						}
					}
                },{
					xtype:'numberfield',
					id:'rateYear',
					name:'rateYear',
					fieldLabel:'贷款利率(%)',
					value:'6.15',
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
				//将年利率转换为月利率
				var rateMonth = (parseFloat(formValue.rateYear) / 12) * 0.01;
				var dkMonth = parseFloat(formValue.dkMonth);
				var dkMoney = parseFloat(formValue.dkMoney);
				var hkType = inputForm.form.findField('hkType').getValue();
				//计算贷款利息、本息合计
				var result = new Array();
				if(hkType == '1'){
					result = estateBorrow(dkMoney, rateMonth, dkMonth); //贷款利息、利息税额、实得利息、本息合计封在返回的数组中
					Ext.getCmp("mybx").setValue(result[0]);       
			        Ext.getCmp("totallx").setValue(result[3]);        
			        Ext.getCmp("totalMoney").setValue(result[1]);    
				}else {
					result = estateBorrow1(dkMoney, rateMonth, dkMonth); //贷款利息、利息税额、实得利息、本息合计封在返回的数组中
					Ext.getCmp("yhkje").setValue(result[2]);        
			        Ext.getCmp("totallx").setValue(result[0]);        
			        Ext.getCmp("totalMoney").setValue(result[1]);    
				}
				
			}
		},{
			text:'重置',
			handler:function(){
				inputForm.getForm().reset();
				outputForm.getForm().reset();
				var hkType = inputForm.form.findField('hkType').getValue();
				if(hkType == '1'){
					Ext.getCmp("mybx").setVisible(true);
					Ext.getCmp("yhkje").setVisible(false);
				}else{
					Ext.getCmp("mybx").setVisible(false);
					Ext.getCmp("yhkje").setVisible(true);
				}
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
					{  xtype:'textarea',id:'yhkje',fieldLabel:'月还金额(元)',labelStyle:'text-align:right;',anchor:'90%',value:'计算得出',readOnly:true,hidden:true},
					{  xtype:'textfield',id:'mybx',fieldLabel:'每月支付本息(元)',anchor:'90%',value:'计算得出',readOnly:true},
					{  xtype:'textfield',id:'totallx',fieldLabel:'累计支付利息(元)',anchor:'90%',value:'计算得出',readOnly:true},
					{  xtype:'textfield',id:'totalMoney',fieldLabel:'累计还款总额(元)',anchor:'90%',value:'计算得出',readOnly:true}
				]
			}]
		}]
	});
	
	/**
	 * 函数：住房贷款计算器计算公式
	 * 等额本息
	 * 输入参数：dkMoney（贷款金额）         rateMonth（贷款月利率） dkMonth（贷款时间：月份）   
	 *	    objArray[0]为月还款额 objArray[1]为月还款总额
	 *	    结果保留两位小数
	 */
	function estateBorrow(dkMoney,rateMonth,dkMonth){
		var monthBack= dkMoney * rateMonth * Math.pow((1+parseFloat(rateMonth)),parseFloat(dkMonth))/(Math.pow((1+parseFloat(rateMonth)),parseFloat(dkMonth))-1);
	    var totalBack=monthBack*dkMonth;
	    var totalInterest=totalBack-dkMoney;
	    var monthInterest=totalInterest/dkMonth;
		totalInterest=(Math.round(totalInterest*100))/100;//存款利息：取两位小数
		monthInterest=(Math.round(monthInterest*10000))/10000;//存款利息：取两位小数	
		monthBack=(Math.round(monthBack*10000))/10000;//存款利息：取两位小数
	    totalBack=(Math.round(totalBack*100))/100;//本息合计：取两位小数
	    var objArray=new Array();
	    objArray[0]=monthBack;
	    objArray[1]=totalBack;
	    objArray[2]=monthInterest;
	    objArray[3]=totalInterest;        
	    return objArray;
	}
	/**
	 * 等额本金还款
	 */
	function estateBorrow1(dkMoney,rateMonth,dkMonth){
		var monthOriginal = dkMoney / dkMonth;
		var timeSpan1= parseInt(dkMonth);
		var interestTotal=0;	
		var backMonth = "";
		for(i=1;i<timeSpan1+1;i++){
			interestM=(dkMoney-dkMoney*(i-1)/timeSpan1)*rateMonth;
			backMonth += i + "月:" + (monthOriginal + interestM).toFixed(2);
			if(i<timeSpan1) backMonth += "\n";
			interestTotal = parseFloat(interestTotal) + parseFloat(interestM);			
		}
		var monthBack = dkMoney*rateMonth*Math.pow((1+parseFloat(rateMonth)),parseFloat(dkMonth))/(Math.pow((1+parseFloat(rateMonth)),parseFloat(dkMonth))-1);
	
		interestTotal=(Math.round(interestTotal*100))/100;//贷款利息：取两位小数
	    var moneyTotal=parseFloat(dkMoney)+parseFloat(interestTotal);
	    var objArray=new Array();
	    objArray[0]=interestTotal;
	    objArray[1]=moneyTotal;
		objArray[2] = backMonth;
	    return objArray;
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