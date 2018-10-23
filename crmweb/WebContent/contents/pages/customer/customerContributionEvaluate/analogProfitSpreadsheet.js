/**
 * 模拟利润试算
 * @author weijl
 * @since 2012-09-21
 */
Ext.onReady(function() {
	var unitLeveL = __unitlevel;
	var boxstore1 = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','总行参数'],['2','分行参数']]
	});
	
	/*******************币种数据源******************/
	var bizhongStore = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=ACC1300012'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
	});
	
	/*******************查询未设置参数的数据源******************/
	var checkrecord = Ext.data.Record.create([ 
   	    {name: 'paramName', mapping: 'PARAM_NAME'},
   	    {name: 'paramId', mapping: 'PARAM_ID'}
   		]);
   	var checkStore = new Ext.data.Store({
   		restful : true,
   		proxy : new Ext.data.HttpProxy({
   			url : basepath + '/ContributionParamCheck.json',
   			method:'GET'
   		}),
   		autoLoad : true,
   		reader : new Ext.data.JsonReader({
   			successProperty: 'success',
   	        idProperty: 'paramId',
   	        messageProperty: 'message',
   			root : 'json.data',
   			totalProperty : 'json.count'
   		}, checkrecord)
   	});
	
   	/*******************获取试算参数的数据源******************/
	var paramrecord = Ext.data.Record.create([ 
 	    {name: 'parmName', mapping: 'PARM_NAME'},
 	    {name: 'parmNum', mapping: 'PARM_NUM'}
 		]);
	                             	
 	var paramStore = new Ext.data.Store({
 		restful : true,
 		proxy : new Ext.data.HttpProxy({
 			url : basepath + '/ContributionParam.json',
 			method:'GET'
 		}),
 		autoLoad : true,
 		reader : new Ext.data.JsonReader({
 			successProperty: 'success',
 	        idProperty: 'parmName',
 	        messageProperty: 'message',
 			root : 'json.data',
 			totalProperty : 'json.count'
 		}, paramrecord)
 	});
 	
 	/*******************只取总行试算参数的数据源******************/
 	var headParamrecord = Ext.data.Record.create([ 
  	    {name: 'parmName', mapping: 'PARM_NAME'},
  	    {name: 'parmNum', mapping: 'PARM_NUM'}
  		]);
 	                             	
  	var headParamStore = new Ext.data.Store({
  		restful : true,
  		proxy : new Ext.data.HttpProxy({
  			url : basepath + '/ContributionParamHeadBank.json',
  			method:'GET'
  		}),
  		autoLoad : true,
  		reader : new Ext.data.JsonReader({
  			successProperty: 'success',
  	        idProperty: 'parmName',
  	        messageProperty: 'message',
  			root : 'json.data',
  			totalProperty : 'json.count'
  		}, headParamrecord)
  	});

  	/*******************试算表单******************/
  	var qForm = new Ext.form.FormPanel({
		id : "qfrom",
		frame : true, //是否渲染表单面板背景色
		title: "客户贡献度评价->客户模拟利润试算 ", 
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
	    items :[{
	    	xtype : 'fieldset',
			height : 50,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 100,
					items : [ {
						xtype : 'combo',
						store : boxstore1,
						fieldLabel : '参数级别',
						name : 'level',
						hiddenName : 'level',
						allowBlank : false,
						disabled : false,
						valueField : 'key',
						displayField : 'value',
						triggerAction : 'all',
						editable : false,
						mode : 'local',
						labelStyle : 'text-align:right;',
						anchor : '99%'
					}]
				   }, {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 100,
					items : [ {
						xtype : 'combo',
						store : bizhongStore,
						fieldLabel : '币种',
						hiddenName : 'bizhong',
						triggerAction : 'all',
						valueField : 'key',
						displayField : 'value',
						editable : false,
						emptyText : '请选择',
						mode : 'local',
						labelStyle : 'text-align:right;',
						anchor : '99%'
					}]
				}]
			} ]
       },{
			xtype : 'fieldset',
			title : '业务种类',
			titleCollapse : true,
			collapsible : true,
			labelWidth : 85,
			height:356,
			items : [ {
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg1',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb1',
						boxLabel : '非保证金对公存款',
						inputValue:'nodeppubfunds',
						name : 'cb-auto-3-1'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum1',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						allowBlank : false,
						fieldLabel : '业务金额',
						name:'sum1',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate1',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						format : 'Y-m-d',
						disabled : true,
						allowBlank : false,
						name:'startDate1',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate1',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						disabled : true,
						allowBlank : false,
						format : 'Y-m-d',
						name:'endDate1',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate1',
						xtype : 'numberfield',
						fieldLabel : '利率',
						allowDecimals: true,
						decimalPrecision:6,
						disabled : true,
						allowBlank : false,
						name:'interestRate1',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit1',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit1',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg2',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb2',
						boxLabel : '保证金对公存款',
						inputValue:'deppubfunds',
						name : 'cb-auto-3-2'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum2',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum2',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate2',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						disabled : true,
						allowBlank : false,
						format : 'Y-m-d',
						name:'startDate2',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate2',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						format : 'Y-m-d',
						disabled : true,
						allowBlank : false,
						name:'endDate2',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate2',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate2',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit2',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit2',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			}, {
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg3',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb3',
						boxLabel : '同业存款',
						inputValue:'interbankdeposits',
						name : 'cb-auto-3-3'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum3',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum3',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate3',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						disabled : true,
						allowBlank : false,
						format : 'Y-m-d',
						name:'startDate3',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate3',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						format : 'Y-m-d',
						disabled : true,
						allowBlank : false,
						name:'endDate3',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate3',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate3',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit3',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit3',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			}, {
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg4',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb4',
						boxLabel : '对公非质押纯贷款',
						inputValue:'pubnopledgepureloan',
						name : 'cb-auto-3-4'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum4',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum4',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate4',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						format : 'Y-m-d',
						disabled : true,
						allowBlank : false,
						name:'startDate4',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate4',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						disabled : true,
						allowBlank : false,
						format : 'Y-m-d',
						name:'endDate4',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate4',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate4',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit4',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit4',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			}, {
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg5',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb5',
						boxLabel : '对公质押纯贷款',
						inputValue:'pubpledgepureloan',
						name : 'cb-auto-3-5'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum5',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum5',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate5',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						format : 'Y-m-d',
						disabled : true,
						allowBlank : false,
						name:'startDate5',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate5',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						disabled : true,
						allowBlank : false,
						format : 'Y-m-d',
						name:'endDate5',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate5',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate5',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit5',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit5',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			}, {
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg6',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb6',
						boxLabel : '贴现',
						inputValue:'discount',
						name : 'cb-auto-3-6'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum6',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum6',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate6',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						disabled : true,
						allowBlank : false,
						format : 'Y-m-d',
						name:'startDate6',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate6',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						format : 'Y-m-d',
						disabled : true,
						allowBlank : false,
						name:'endDate6',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate6',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate6',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit6',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit6',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg7',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb7',
						boxLabel : '签发承兑',
						inputValue:'issuedpromissory',
						name : 'cb-auto-3-7'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum7',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum7',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate7',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						disabled : true,
						format : 'Y-m-d',
						name:'startDate7',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate7',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						disabled : true,
						format : 'Y-m-d',
						name:'endDate7',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate7',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate7',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit7',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit7',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg8',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb8',
						boxLabel : '开出信用证',
						inputValue:'openlettercredit',
						name : 'cb-auto-3-8'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum8',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum8',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate8',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						format : 'Y-m-d',
						disabled : true,
						name:'startDate8',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate8',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						format : 'Y-m-d',
						disabled : true,
						name:'endDate8',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate8',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate8',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit8',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit8',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg9',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb9',
						boxLabel : '开出保函',
						inputValue:'lettersguarantee',
						name : 'cb-auto-3-9'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum9',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum9',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate9',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						disabled : true,
						format : 'Y-m-d',
						name:'startDate9',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate9',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						disabled : true,
						format : 'Y-m-d',
						name:'endDate9',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate9',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate9',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'analogProfit9',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit9',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			},{
				xtype : "panel",
				layout : "column", // 也可以是table,实现多列布局
				items : [{
					columnWidth : .15,
					xtype : 'checkboxgroup',
					id : 'cg10',
					fieldLabel : 'Multi-Column (horizontal)',
					items : [{
						id : 'cgb10',
						boxLabel : '融资顾问',
						inputValue:'deppubfunds',
						name : 'cb-auto-3-10'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'sum10',
						xtype : 'numberfield',
						allowDecimals: true,
						decimalPrecision:2,
						fieldLabel : '业务金额',
						disabled : true,
						allowBlank : false,
						name:'sum10',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'startDate10',
						xtype : 'datefield',
						fieldLabel : '业务起始日期',
						disabled : true,
						format : 'Y-m-d',
						name:'startDate10',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'endDate10',
						xtype : 'datefield',
						fieldLabel : '业务截至日期',
						disabled : true,
						format : 'Y-m-d',
						name:'endDate10',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					items : [ {
						id : 'interestRate10',
						xtype : 'textfield',
						fieldLabel : '利率',
						disabled : true,
						allowBlank : false,
						name:'interestRate10',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				}, {
					columnWidth : .17,
					layout : 'form',
					
					items : [ {
						id : 'analogProfit10',
						xtype : 'numberfield',
						fieldLabel : '模拟利润',
						allowDecimals: true,
						decimalPrecision:2,
						disabled : true,
						name:'analogProfit10',
						value:'',
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
				} ]
			},{
				layout : 'form',
				items : [{
					id : 'analogProfit11',
					xtype : 'numberfield',
					fieldLabel : '模拟利润合计',
					allowDecimals: true,
					decimalPrecision:2,
					editable : false,
					name:'analogProfitSum',
					value:'',
					style:{
						float:'right',
						labelAlign : 'right',
						marginRight: '22px'
					},
					anchor : '17%'
				}]
			} ]
       	}],
		buttons : [{
			text : '试算',
			handler : function() {
				var tempSum = 0;
				/*******************循环调用相关方法设置试算结果并计算合计数值******************/
				for(var i = 1;i < 11;i++ ){
					var tempid = "cb-auto-3-"+i;
					if(qForm.getForm().findField(tempid).checked){
						qForm.getForm().findField("analogProfit"+i).setValue(getValue(tempid));
						tempSum = tempSum + parseFloat(getValue(tempid));
					}
				}
				qForm.getForm().findField("analogProfitSum").setValue(tempSum);
			}
		},{
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
				/******若用户机构级别为总行，则只能以总行参数试算******/
				if(parseInt(unitLeveL) == 1){
					qForm.getForm().findField("level").setValue(1);
				}
			}
		}]
	});
  	
  	/*******************检查并提示所需但未设置的参数******************/
	function checkParm(parmName){
		for(var i = 0; i<checkStore.totalLength ;i++){
 			var temp = checkStore.getRange()[i].data;
 			if(temp.paramId == parmName){
 				Ext.Msg.alert("提示","您需要设置参数:"+"'"+temp.paramName+"'");
 			}
 		}
	};
	
	/*******************获取参数值******************/
	function getParmNum(parmName){
 		var tempstore = null ;
 		var tempnum = "";
 		if(parseInt(qForm.getForm().findField("level").getValue()) == 1){
 			tempstore = headParamStore;
 		}else{
 			tempstore = paramStore;
 		}
 		for(var i = 0; i<tempstore.totalLength ;i++){
 			var temp = tempstore.getRange()[i].data;
 			if(temp.parmName == parmName){
 				tempnum = temp.parmNum;
 			}
 		}
 		if(tempnum == ""){
 			checkParm(parmName);
 		}
 		return parseFloat(tempnum);
 	};

	/*******************获取时间******************/
	function getDateNum(start,end){
		var startDate = qForm.getForm().findField(start).getValue();
		var endDate = qForm.getForm().findField(end).getValue();
		return Math.round(0.5+(parseFloat(endDate-startDate))/1000/60/60/24);
	};
	
	/*******************获取试算结果******************/
	function getValue(id){
		var tempValue ;
		switch(id){
		case "cb-auto-3-1" :
			tempValue = qForm.getForm().findField("sum1").getValue()*(((getParmNum("1001")/100)*((getParmNum("2001")/100)/360)+((getParmNum("1002"))/100)*((getParmNum("2002")/100)/360)+((getParmNum("1005"))/100)*((getParmNum("1006")/100)/360))+(1-(getParmNum("1001")/100)-(getParmNum("1002")/100))*(getParmNum("1101")/100/360)-((qForm.getForm().findField("interestRate1").getValue()/100)/360))*getParmNum("1201")*(getDateNum("startDate1","endDate1"));
			break;
		case "cb-auto-3-2" :
			tempValue = qForm.getForm().findField("sum2").getValue()*(((getParmNum("1001")/100)*((getParmNum("2001")/100)/360)+((getParmNum("1002"))/100)*((getParmNum("2002")/100)/360)+((getParmNum("1005"))/100)*((getParmNum("1006")/100)/360))+(1-(getParmNum("1001")/100)-(getParmNum("1002")/100))*(getParmNum("1101")/100/360)-((qForm.getForm().findField("interestRate2").getValue()/100)/360))*getParmNum("1301")*(getDateNum("startDate2","endDate2"));
			break;
		case "cb-auto-3-3" :
			tempValue = qForm.getForm().findField("sum3").getValue()*((getParmNum("1101")/100/360)-((qForm.getForm().findField("interestRate3").getValue()/100)/360))*getParmNum("1401")*(getDateNum("startDate3","endDate3"));
			break;
		case "cb-auto-3-4" :
			tempValue = ((qForm.getForm().findField("sum4").getValue()*((qForm.getForm().findField("interestRate4").getValue()/100)/360))*(1-(getParmNum("1901")/100))-(qForm.getForm().findField("sum4").getValue()*(getParmNum("1101")/100/360)))*getParmNum("1501")*(getDateNum("startDate4","endDate4"));
			break;
		case "cb-auto-3-5" :
			tempValue = ((qForm.getForm().findField("sum5").getValue()*((qForm.getForm().findField("interestRate5").getValue()/100)/360))*(1-(getParmNum("1901")/100))-(qForm.getForm().findField("sum5").getValue()*(getParmNum("1101")/100/360)))*getParmNum("1501")*(getDateNum("startDate5","endDate5"));
			break;
		case "cb-auto-3-6" :
			tempValue = (qForm.getForm().findField("sum6").getValue()*((qForm.getForm().findField("interestRate6").getValue()/100)/360)*(getDateNum("startDate6","endDate6")-1)*(1-getParmNum("1901")/100)-qForm.getForm().findField("sum6").getValue()*((getParmNum("1103")/100)/360)*(Math.round(getDateNum("startDate6","endDate6"))));
			break;
		case "cb-auto-3-7" :
			tempValue = qForm.getForm().findField("sum7").getValue()*(1-getParmNum("1901")/100)*getParmNum("1601");
			break;
		case "cb-auto-3-8" :
			tempValue = qForm.getForm().findField("sum8").getValue()*(1-getParmNum("1901")/100)*getParmNum("1601");
			break;
		case "cb-auto-3-9" :
			tempValue = qForm.getForm().findField("sum9").getValue()*(1-getParmNum("1901")/100)*getParmNum("1601");
			break;
		case "cb-auto-3-10" :
			tempValue = qForm.getForm().findField("sum10").getValue()*(1-getParmNum("1901")/100)*getParmNum("1601");
			break;
		}
		return tempValue;
	};
	
	/*******************整体布局******************/
	var viewport = new Ext.Viewport({
		layout:'fit',
		items: [qForm] 
	});
	
	/*******************若用户机构级别为总行，则只能以总行参数试算******************/
	if(parseInt(unitLeveL) == 1){
		qForm.getForm().findField("level").setValue(1);
		qForm.getForm().findField("level").setDisabled(true);
	}

	/*******************通过监控checkbox的check事件控制试算表单相关输入框是否可编辑******************/
	var point1 = Ext.getCmp('cgb1');
	point1.on('check',function(){
    	if(point1.getValue()==''){
    		Ext.getCmp('sum1').setDisabled(true);
    		Ext.getCmp('startDate1').setDisabled(true);
    		Ext.getCmp('endDate1').setDisabled(true);
    		Ext.getCmp('interestRate1').setDisabled(true);
    		Ext.getCmp('analogProfit1').setDisabled(true);
    	}else if(point1.getValue()!=''){
    		Ext.getCmp('sum1').setDisabled(false);
    		Ext.getCmp('startDate1').setDisabled(false);
    		Ext.getCmp('endDate1').setDisabled(false);
    		Ext.getCmp('interestRate1').setDisabled(false);
    		Ext.getCmp('analogProfit1').setDisabled(false);
    	}
	});
	var point2 = Ext.getCmp('cgb2');
	point2.on('check',function(){
    	if(point2.getValue()==''){
    		Ext.getCmp('sum2').setDisabled(true);
    		Ext.getCmp('startDate2').setDisabled(true);
    		Ext.getCmp('endDate2').setDisabled(true);
    		Ext.getCmp('interestRate2').setDisabled(true);
    		Ext.getCmp('analogProfit2').setDisabled(true);
    	}else if(point2.getValue()!=''){
    		Ext.getCmp('sum2').setDisabled(false);
    		Ext.getCmp('startDate2').setDisabled(false);
    		Ext.getCmp('endDate2').setDisabled(false);
    		Ext.getCmp('interestRate2').setDisabled(false);
    		Ext.getCmp('analogProfit2').setDisabled(false);
    	}
	});
	var point3 = Ext.getCmp('cgb3');
	point3.on('check',function(){
    	if(point3.getValue()==''){
    		Ext.getCmp('sum3').setDisabled(true);
    		Ext.getCmp('startDate3').setDisabled(true);
    		Ext.getCmp('endDate3').setDisabled(true);
    		Ext.getCmp('interestRate3').setDisabled(true);
    		Ext.getCmp('analogProfit3').setDisabled(true);
    	}else if(point3.getValue()!=''){
    		Ext.getCmp('sum3').setDisabled(false);
    		Ext.getCmp('startDate3').setDisabled(false);
    		Ext.getCmp('endDate3').setDisabled(false);
    		Ext.getCmp('interestRate3').setDisabled(false);
    		Ext.getCmp('analogProfit3').setDisabled(false);
    	}
	});
	var point4 = Ext.getCmp('cgb4');
	point4.on('check',function(){
    	if(point4.getValue()==''){
    		Ext.getCmp('sum4').setDisabled(true);
    		Ext.getCmp('startDate4').setDisabled(true);
    		Ext.getCmp('endDate4').setDisabled(true);
    		Ext.getCmp('interestRate4').setDisabled(true);
    		Ext.getCmp('analogProfit4').setDisabled(true);
    	}else if(point4.getValue()!=''){
    		Ext.getCmp('sum4').setDisabled(false);
    		Ext.getCmp('startDate4').setDisabled(false);
    		Ext.getCmp('endDate4').setDisabled(false);
    		Ext.getCmp('interestRate4').setDisabled(false);
    		Ext.getCmp('analogProfit4').setDisabled(false);
    	}
	});
	var point5 = Ext.getCmp('cgb5');
	point5.on('check',function(){
    	if(point5.getValue()==''){
    		Ext.getCmp('sum5').setDisabled(true);
    		Ext.getCmp('startDate5').setDisabled(true);
    		Ext.getCmp('endDate5').setDisabled(true);
    		Ext.getCmp('interestRate5').setDisabled(true);
    		Ext.getCmp('analogProfit5').setDisabled(true);
    	}else if(point5.getValue()!=''){
    		Ext.getCmp('sum5').setDisabled(false);
    		Ext.getCmp('startDate5').setDisabled(false);
    		Ext.getCmp('endDate5').setDisabled(false);
    		Ext.getCmp('interestRate5').setDisabled(false);
    		Ext.getCmp('analogProfit5').setDisabled(false);
    	}
	});
	var point6 = Ext.getCmp('cgb6');
	point6.on('check',function(){
    	if(point6.getValue()==''){
    		Ext.getCmp('sum6').setDisabled(true);
    		Ext.getCmp('startDate6').setDisabled(true);
    		Ext.getCmp('endDate6').setDisabled(true);
    		Ext.getCmp('interestRate6').setDisabled(true);
    		Ext.getCmp('analogProfit6').setDisabled(true);
    	}else if(point6.getValue()!=''){
    		Ext.getCmp('sum6').setDisabled(false);
    		Ext.getCmp('startDate6').setDisabled(false);
    		Ext.getCmp('endDate6').setDisabled(false);
    		Ext.getCmp('interestRate6').setDisabled(false);
    		Ext.getCmp('analogProfit6').setDisabled(false);
    	}
	});
	var point7 = Ext.getCmp('cgb7');
	point7.on('check',function(){
    	if(point7.getValue()==''){
    		Ext.getCmp('sum7').setDisabled(true);
    		Ext.getCmp('startDate7').setDisabled(true);
    		Ext.getCmp('endDate7').setDisabled(true);
    		Ext.getCmp('interestRate7').setDisabled(true);
    		Ext.getCmp('analogProfit7').setDisabled(true);
    	}else if(point7.getValue()!=''){
    		Ext.getCmp('sum7').setDisabled(false);
    		Ext.getCmp('analogProfit7').setDisabled(false);
    	}
	});
	var point8 = Ext.getCmp('cgb8');
	point8.on('check',function(){
    	if(point8.getValue()==''){
    		Ext.getCmp('sum8').setDisabled(true);
    		Ext.getCmp('startDate8').setDisabled(true);
    		Ext.getCmp('endDate8').setDisabled(true);
    		Ext.getCmp('interestRate8').setDisabled(true);
    		Ext.getCmp('analogProfit8').setDisabled(true);
    	}else if(point8.getValue()!=''){
    		Ext.getCmp('sum8').setDisabled(false);
    		Ext.getCmp('analogProfit8').setDisabled(false);
    	}
	});
	var point9 = Ext.getCmp('cgb9');
	point9.on('check',function(){
    	if(point9.getValue()==''){
    		Ext.getCmp('sum9').setDisabled(true);
    		Ext.getCmp('startDate9').setDisabled(true);
    		Ext.getCmp('endDate9').setDisabled(true);
    		Ext.getCmp('interestRate9').setDisabled(true);
    		Ext.getCmp('analogProfit9').setDisabled(true);
    	}else if(point9.getValue()!=''){
    		Ext.getCmp('sum9').setDisabled(false);
    		Ext.getCmp('analogProfit9').setDisabled(false);
    	}
	});
	var point10 = Ext.getCmp('cgb10');
	point10.on('check',function(){
    	if(point10.getValue()==''){
    		Ext.getCmp('sum10').setDisabled(true);
    		Ext.getCmp('startDate10').setDisabled(true);
    		Ext.getCmp('endDate10').setDisabled(true);
    		Ext.getCmp('interestRate10').setDisabled(true);
    		Ext.getCmp('analogProfit10').setDisabled(true);
    	}else if(point10.getValue()!=''){
    		Ext.getCmp('sum10').setDisabled(false);
    		Ext.getCmp('analogProfit10').setDisabled(false);
    	}
	});
}); 