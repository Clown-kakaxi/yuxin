/**
 * 
* @Description: 对公客户信息首页
* @author wangmk1 
* @date 2014-7-25
*
*/
/**数据字典***/
//证件类型
var cardTypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//注册资本币种
var capitalCurr = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000027'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//是否上市企业
var isListedStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000156'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//是否央企国企
var isStateStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000181'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//是否台资企业
var isTaiStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000182'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//行业分类（企业规模）
var industryStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000287'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//企业类型------组织形式
var orgFormStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000063'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//员工规模
var staffStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000061'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//资产规模
var assetSizeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000060'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});

//客户来源渠道
var custSourceStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000353'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
}); 
//客户状态
var custStatStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000081'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
}); 

Ext.onReady(function(){	
	var customerBaseInfoPanel= new Ext.FormPanel({
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		items : [ {
			xtype : 'fieldset',
			title : '基本信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
						 {xtype:'displayfield',width:200,readOnly:true,readOnly:true,fieldLabel:'客户编号',name:'CUST_ID'},
			             {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'客户名称',name:'CUST_NAME'}	,
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'证件类型',name:'IDENT_TYPE'
//			              ,store:cardTypeStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  },
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'证件号码',name:'IDENT_NO'},
			              {xtype:'displayfield',width:200,fieldLabel:'成立日期',name:'BUILD_DATE',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'是否上市企业',name:'IS_LISTED_CORP'
//			              ,store:isListedStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  }, //ACRM_F_CI_ORG_KEYFLAG
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'是否央企国企',name:'IS_SOE'
//			              ,store:isStateStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  },
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'是否台资企业',name:'IS_TAIWAN_CORP'
//			              ,store:isTaiStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  },
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'行业分类（企业规模）',name:'INDUSTRY_CATEGORY'
//			              ,store:industryStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  }	
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[				       
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'法定代表人',name:'LEGAL_REPR_NAME',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'注册地址',name:'REGISTER_ADDR'},    //ACRM_F_CI_ORG_REGISTERINFO  注册信息表 
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'注册资本（万元）',name:'REGISTER_CAPITAL'},
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'注册资本币种',name:'REGISTER_CAPITAL_CURR'
//			               ,store:capitalCurr,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
			            	  },
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'实收资本',name:'FACT_CAPITAL'},
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'企业类型',name:'ENT_SCALE'
//			               ,store:orgFormStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  },  
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'年销售额',name:'AMT1',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},  //ACRM_F_CI_ORG_BUSIINFO
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'员工规模',name:'EMPLOYEE_SCALE'
//			               ,store:staffStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  },
			              {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'资产规模',name:'AMT'
//			               ,store:assetSizeStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  }
				          ]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '所属关系',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				labelWidth:220,
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
						 {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'隶属集团',name:'BELONG_GROUP'},   //（用客户号关联‘集团’表）
						   {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'归属机构名称',name:'INSTITUTION_NAME'} //OCRM_F_CI_BELONG_ORG
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'隶属业务条线',name:'BELONG_LINE'}//（用客户号关联‘归属业务条线’表）
				          ]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '联系信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			labelWidth:220,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       //{xtype:'displayfield',width:200,readOnly:true,fieldLabel:'联系人',name:'ORG_CUS',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'客户来源渠道',name:'SOURCE_CHANNEL'
//					        ,store:custSourceStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
					       ,hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))
		             	  },
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'联系地址',name:'ORG_ADDR',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))} 
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'联系电话',name:'ORG_TEL',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'客户状态',name:'CUST_STAT'
//					        ,store:custStatStore,valueField : 'key',displayField : 'value',mode : 'local',editable : false,
//			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
//		             	  labelStyle:'text-align:right;',selectOnFocus : true
		             	  },   
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'前次拜访状况',name:'VISIT_NOTE',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}  //OCRM_F_WP_SCHEDULE_VISIT
				              ]
					}]
			}]
			
		}]
	});
	
	var customerBusinessPanel= new Ext.FormPanel({
		// title: '总负债——表内总负债',
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		border:false,
		autoScroll : true,
		items : [{
			xtype : 'fieldset',
			title : '管理总资产',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'活期存款余额',name:'CURRENT_BALANCE'},  //ACRM_A_CI_GATH_BUSINESS  客户业务汇总表
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'定期存款余额',name:'FIX_PERIOD_BALANCE',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'理财存款余额',name:'FINAN_DEPOSIT_BALANCE'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'管理总资产均量(本年度截至上个月)',name:'CURRENT_AVG_AUM',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'管理总资产当前时点值',name:'CURRENT_AUM'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'管理总资产上年同期时点值',name:'CORRESPOD_AUM'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'管理总资产均量(上年全年)',name:'LAST_YEAR_AUM',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
				              ]
					}]
			}]
			
		}, {
			xtype : 'fieldset',
			title : '总负债——表内总负债',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'一般贷款',name:'LOAN_AMOUNT'},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表内总负债均量（本年度截至上个月）',name:'LIABILITIES_IN_TB_AVG',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表内总负债均量（上年全年）',name:'LIABILITIES_IN_TB_SN',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'银票贴现',name:'TAELS_DISCOUNT'},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表内总负债当前时点值',name:'LIABILITIES_IN_TB',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
		              ]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'商票贴现及商票保贴',name:'DISCOUNT_AMOUNT'},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'应收账款融资',name:'FINAN_AMOUNT'},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'保理、发票融资',name:'FACTOR_AMOUNT'},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'进出口项下融资',name:'INPORT_FINAN_AMOUNT'},
				       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表内总负债上年同期值',name:'LIABILITIES_IN_TB_SNTQ'}
			              ]
				}]
		}]
		},{
			xtype : 'fieldset',
			title : '总负债——表外或有负债',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'未支付银行承兑汇票',name:'UNPAY_BANK_ACCEPTANCE'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'未支付L/C',name:'UNPAY_LC'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'未支付SBLC',name:'UNPAY_SBLC'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表外总负债当前时点值',name:'LIABILITIES_OFF_TB',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表外总负债上年同期值',name:'LIABILITIES_OFF_TB_SNTQ'}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'未支付保函',name:'UNPAY_LG'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'未结清外汇买卖',name:'UNCLOSE_FOREIGN_EXCHANGE'},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表外总负债均量（本年度截至上个月）',name:'LIABILITIES_OFF_TB_AVG',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'表外总负债均量（上年全年）',name:'LIABILITIES_OFF_TB_SN',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
				              ]
					}]
			}]
			
		},{
			xtype : 'fieldset',
			title : '利润——累计营业净额',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'累计营业净额（本年度截至上个月）',name:'TOTAL_SALE',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'累计营业净额（上一年度全年）',name:'TOTAL_SALE_SN',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
				              ]
					}]
			}]
		},{
			xtype : 'fieldset',
			title : 'TMU',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'累计承做量（本年度截至目前）',name:'TOTAL_UNDERTAKE',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'累计收益（本年度截至上个月）',name:'TOTAL_INCOME',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'累计承做量（上一年度全年）',name:'TOTAL_UNDERTAKE_SN',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))},
					       {xtype:'displayfield',width:200,readOnly:true,fieldLabel:'累计收益（上一年度全年）',name:'TOTAL_INCOME_SN',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
				              ]
					}]
			}]
		},{
			xtype : 'fieldset',
			title : '授信信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[{xtype:'displayfield',width:200,readOnly:true,fieldLabel:'授信额度',name:'CRD_AMT',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
						{xtype:'displayfield',width:200,readOnly:true,fieldLabel:'币种',name:'CURRENCY',hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))}
						]
					}]
			}]
		}]
	});
	var _height= 250;	
	

	//客户存款时点业务量
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchDeposit.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var len = data.length;
//			if(len > 12){
//				len = 12
//			}
//			if(data.length == 24 || data.length> 12){
			var xml = "";
			xml +=  "<chart  palette='2' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13' showValues ='0'> ";
			xml +=	"<categories> " ;
			for(var i=0;i<len;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,5)+"' />   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='连续30天存款时点值'>";
			for(var i=0;i<len;i++){
				xml+="  <set value='"+data[i].INDEX1+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="<dataset id='current' seriesName='上年存款同期值' showValues='0'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].DEPOSIT_BALANCE+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSColumn2D.swf", "custDepositChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custDepositChart");
//			}
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	//客户贷款时点业务量	
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchLoan.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			
			var data = Ext.util.JSON.decode(response.responseText).data;
			var len = data.length;
//			if(len > 12){
//				len = 12
//			}
//			if(data.length == 24 || data.length> 12){
			var xml = "";
			xml +=  "<chart  palette='2'  xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13' showValues ='0'> ";
			xml +=	"<categories> " ;
			for(var i=0;i<len;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,5)+"' />   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='连续30天贷款时点值'>";
			for(var i=0;i<len;i++){
				xml+="  <set value='"+data[i].INDEX4+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			
			xml+="<dataset id='current' seriesName='上年贷款同期值' showValues='0'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].LIABILITIES_IN_TB+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSColumn2D.swf", "custLoanChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custLoanChart");
//			}
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	//客户存款业务趋势图		
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchDepositTrend.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showYAxisValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13' showValues ='0'> ";
			xml +=	"<categories> " ;
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+"月' />   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='存款余额' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX1+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
//			xml+="<dataset id='current' seriesName='存款年日均' dragBorderColor='CC9900'>";
//			for(var i=0;i<data.length;i++){
//				xml+="  <set value='"+data[i].INDEX3+"' allowDrag='0'/>  ";
//			}
//			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custDepositTrendChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custDepositTrendChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	//客户贷款业务趋势图	
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchLoanTrend.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showYAxisValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13' showValues ='0'> ";
			xml +=	"<categories> " ;
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+"月' />   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='贷款余额' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX4+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
//			xml+="<dataset id='current' seriesName='贷款年日均' dragBorderColor='CC9900'>";
//			for(var i=0;i<data.length;i++){
//				xml+="  <set value='"+data[i].INDEX6+"' allowDrag='0'/>  ";
//			}
//			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custLoanTrendChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custLoanTrendChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	//客户营业净额趋势图NetProfit
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchNetProfit.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showYAxisValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13' showValues ='0'> ";
			xml +=	"<categories> " ;
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+"月' />   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='营业净额' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX11+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custNetProfitChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custNetProfitChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	//总资产时点值占比图
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchAssetsInfo.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = '';
			if(data.length >0){
				data = data[0];
		        xml +=  '<chart bgColor="#E8E8D0" caption="个人管理总资产时点值(单位:元)" subcaption="'+ (data.ETL_DATE).substr(0,4) + '年'+ (data.ETL_DATE).substr(5,2) + '月'+ (data.ETL_DATE).substr(8,2)+ '日'+'" formatNumberScale="0" baseFontSize="13">';
		        xml += '<set label="存款时点值" value="'+ data.DEPOSIT_BALANCE +'" />';
		        xml += '<set label="贷款时点值" value="'+ data.LOAN_BALANCE +'" />';
		    	xml += '<set label="表外或有负债时点值" value="'+ data.LIABILITIES_OFF_TB +'" />';
				xml += '</chart>';
			}
			var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "custAssetsChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custAssetsChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
    //分类存款产品占比图	DepositJournal
	Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchDepositJournal.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = '';
			var i=0;
		    xml +=  '<chart bgColor="#E8E8D0" caption="分类存款产品占比图" formatNumberScale="0" baseFontSize="13">';
			for(i=0;i<data.length;i++){
				var temp = data[i];
		        xml += '<set label="'+temp.PROD_NAME+'" value="'+ temp.CUR_AC_BL +'" />';
			}
				xml += '</chart>';
			var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "custDepositJournalChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custDepositJournalChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	
	//分类贷款产品占比图	searchLoanJournal
		Ext.Ajax.request({
		url : basepath + '/publicCustChart!searchLoanJournal.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = '';
			var i=0;
		    xml +=  '<chart bgColor="#E8E8D0" caption="分类贷款产品占比图" formatNumberScale="0" baseFontSize="13">';
			for(i=0;i<data.length;i++){
				var temp = data[i];
		        xml += '<set label="'+temp.PROD_NAME+'" value="'+ temp.CUR_AC_BL +'" />';
			}
				xml += '</chart>';
			var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "custLoanJournalChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custLoanJournalChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});
	//页面视图根面板
   var viewport = new Ext.Viewport({
	    layout:'border',
	    items:[{
	        xtype:'portal',
	        id:'center',
	        region:'center',
	        items:[
	               {
	                columnWidth: 1,
	                autoHeight:true,
	                border:false,
	                items:[{
	              	  	draggable:false,
	                    title: '客户信息',
	                    layout:'fit',
//	                    style:'padding:5px 5px 5px 5px',
	                	collapsible:true,
	                    items:[customerBaseInfoPanel]
	                }]	                
	            },{
	            	 columnWidth: 1,
		                autoHeight:true,
		                border:false,
		                items:[{
		                	draggable:false,
		                    title: '业务汇总',
		                    layout:'fit',
		                    style:'padding:5px 5px 5px 5px',
		                	collapsible:true,
		                    items:[customerBusinessPanel]
		                }]	
	            },{
	            columnWidth:.5,
	            border:false,
	            autoHeight:true,
	            id:'cus',
	            items:[{
	            	draggable:false,
	                title: '客户存款时点业务量',
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custDepositChart"></div>'
	            },{
	            	draggable:false,
	            	title: '客户存款业务趋势图',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custDepositTrendChart"></div>'
					,hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))
	            },{
	            	draggable:false,
	            	title: '客户营业净额趋势图',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custNetProfitChart"></div>'
					,hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))
	            },{
	            	draggable:false,
	            	title: '分类存款产品占比图',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custDepositJournalChart"></div>'
					,hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))
	            }]
	        },{
	            columnWidth:.5,
	            autoHeight:true,
	            //layout:'fit',
	            border:false,
	            items:[{
	            	draggable:false,
	            	title: '客户贷款时点业务量',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custLoanChart"></div>'
	            },{
	            	draggable:false,
	            	title: '客户贷款业务趋势图',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custLoanTrendChart"></div>'
					,hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))
	            },{
	            	draggable:false,
	            	title: '总资产时点值占比图',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custAssetsChart"></div>'
	            },{
	            	draggable:false,
	            	title: '分类贷款产品占比图',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
					html :'<div id="custLoanJournalChart"></div>'
					,hidden:((JsContext.checkGrant('publicOverviewBaseInfo_AO')==true) && (JsContext.checkGrant('publicOverviewBaseInfo_OP')==false))
	            }]	                
	        }] 
	    }]
   });
   //record
  var record=Ext.data.Record.create(
		  [     
		   {name: 'CUST_ID'}
		  ,{name: 'CUST_NAME'}
		  ,{name: 'IDENT_TYPE',mapping:'IDENT_TYPE_ORA'}
		  ,{name: 'IDENT_NO'}
		  ,{name: 'BUILD_DATE'}
		  ,{name: 'LEGAL_REPR_NAME'}
		  ,{name: 'REGISTER_ADDR'}  //ACRM_F_CI_ORG_REGISTERINFO  注册信息表 
		  ,{name: 'REGISTER_CAPITAL'}
		  ,{name: 'REGISTER_CAPITAL_CURR',mapping:'REGISTER_CAPITAL_CURR_ORA'}
		  ,{name: 'FACT_CAPITAL'}
		  ,{name: 'IS_LISTED_CORP',mapping:'IS_LISTED_CORP_ORA'}//ACRM_F_CI_ORG_KEYFLAG
		  ,{name: 'IS_SOE',mapping:'IS_SOE_ORA'}
		  ,{name: 'IS_TAIWAN_CORP',mapping:'IS_TAIWAN_CORP_ORA'}
		  ,{name: 'INDUSTRY_CATEGORY',mapping:'INDUSTRY_CATEGORY_ORA'}
		  ,{name: 'ORG_FORM',mapping:'ORG_FORM_ORA'} //企业类型用 组织形式
		  ,{name: 'AMT1'} //年销售额
		  ,{name: 'EMPLOYEE_SCALE',mapping:'EMPLOYEE_SCALE_ORA'}
		  ,{name: 'AMT'}//资产规模
		  ,{name: 'BELONG_GROUP'}//（用客户号关联‘集团’表）
		  ,{name: 'BELONG_LINE'}//（关联‘业务条线’表）
		  ,{name: 'INSTITUTION_NAME'}//OCRM_F_CI_BELONG_ORG
		  ,{name: 'ORG_CUS'}
		  ,{name: 'SOURCE_CHANNEL',mapping:'SOURCE_CHANNEL_ORA'}
		  ,{name: 'ORG_TEL'}
		  ,{name: 'CUST_STAT',mapping:'CUST_STAT_ORA'}
		  ,{name:'RISK_LEVEL',mapping:'RISK_LEVEL_ORA'}//风险等级
		  ,{name: 'ORG_ADDR'}
		  ,{name: 'VISIT_NOTE'}   //OCRM_F_WP_SCHEDULE_VISIT
		  ,{name: 'ENT_SCALE',mapping:'ENT_SCALE_ORA'} 
		//ACRM_A_CI_GATH_BUSINESS  客户业务汇总表
		  ,{name: 'CURRENT_BALANCE'}
		  ,{name: 'FIX_PERIOD_BALANCE'}
		  ,{name: 'FINAN_DEPOSIT_BALANCE'}
		  ,{name: 'CURRENT_AUM'}
		  ,{name: 'CORRESPOD_AUM'}
		  ,{name: 'CURRENT_AVG_AUM'}
		  ,{name: 'LAST_YEAR_AUM'}
		  ,{name: 'LOAN_AMOUNT'}
		  ,{name: 'LIABILITIES_IN_TB_AVG'}
		  ,{name: 'LIABILITIES_IN_TB_SN'}
		  ,{name: 'DISCOUNT_AMOUNT'}
		  ,{name: 'FINAN_AMOUNT'}
		  ,{name: 'FACTOR_AMOUNT'}
		  ,{name: 'INPORT_FINAN_AMOUNT'}
		  ,{name: 'TAELS_DISCOUNT'}
		  ,{name: 'LIABILITIES_IN_TB'}
		  ,{name: 'LIABILITIES_IN_TB_SNTQ'}
		  ,{name: 'UNPAY_BANK_ACCEPTANCE'}
		  ,{name: 'UNPAY_LC'}
		  ,{name: 'UNPAY_SBLC'}
		  ,{name: 'UNPAY_LG'} 
		  ,{name: 'UNCLOSE_FOREIGN_EXCHANGE'}
		  ,{name: 'LIABILITIES_OFF_TB_AVG'} 
		  ,{name: 'LIABILITIES_OFF_TB'} 
		  ,{name: 'LIABILITIES_OFF_TB_SNTQ'} 
		  ,{name: 'LIABILITIES_OFF_TB_SN'} 
		  ,{name: 'TOTAL_SALE_SN'} 
		  ,{name: 'TOTAL_SALE'} 
		  ,{name: 'TOTAL_UNDERTAKE'} 
		  ,{name: 'TOTAL_INCOME'} 
		  ,{name: 'TOTAL_UNDERTAKE_SN'} 
		  ,{name: 'TOTAL_INCOME_SN'}
		  ,{name: 'CRD_AMT'} 
		  ,{name: 'CURRENCY'} 
		 

//		  ,{name: 'Q'} 
		   ]);

  //基本信息reader
   customerBaseInfoPanel.getForm().reader=new Ext.data.JsonReader({
        root:'data'
    },record);
   
   //customerBaseInfoPanel加载数据
   customerBaseInfoPanel.getForm().load({
   	url: basepath + '/publicCustInfo!searchBasicInfo.json',
   	method:'get',
   	params : {
   		'custId':_custId
       }
   });
  
  
   //业务信息reader
   customerBusinessPanel.getForm().reader=new Ext.data.JsonReader({
       root:'data'
   },record);
  //customerBusinessPanel加载数据
   customerBusinessPanel.getForm().load({
  	url: basepath + '/publicCustInfo!searchContactInfo.json',
  	method:'get',
  	params : {
  		'custId':_custId
      }
  });
});
