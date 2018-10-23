imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldRSu.js',//客户放大镜
				'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',//组织机构放大镜
				'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'//用户放大镜

		    ]);
Ext.QuickTips.init();
WLJUTIL.suspendViews = false; // 自定义面板是否浮动	
//第一列有值统计
var countClom1=0;
//第四列有值统计
var countClom4=0;
//第四列有值统计
var countClom41=0;
//第四列有值统计
var countClom42=0;

// 客户类型
var Chkresult_comboData1=[['1','TT'],['2','CB'],['3','RE'],['4','SME']];
// 是否上市
var Chkresult_comboData2=[['1','是'],['2','否']];
//法人代表/决策者
var Chkresult_comboData3=[['1','激进型'],['2','稳健型'],['3','保守型'],['4','退缩型']];
//销售结算方式（主要三类）
var Chkresult_comboData4=[['1','现金'],['2','支票'],['3','L/C'],['4','D/A'],['5','D/P'],['6','银票'],['7','商票'],['8','其它']];
//应收账款周转天期
var Chkresult_comboData5=[['1','30天（含）以下'],['2','30-60天（含）'],['3','60-90天（含）'],['4','90-120天（含）'],['5','120-180天（含）'],['6','180天以上']];
//法金信用评等
var Chkresult_comboData6=[['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6'],['7','7'],['8','8'],['9','9'],['10','10'],['11','11'],['12','12'],['13','13']];
//CB评等
var Chkresult_comboData7=[['1','A'],['2','A-'],['3','B'],['4','B-'],['5','C'],['6','D'],['7','不予准入']];
//企业文化类型
var Chkresult_comboData8=[['1','农、林、牧、渔业'],['2','采矿业'],['3','制造业'],['4','电力、热力、燃气及水的生产和供应业'],['5','建筑业'],['6','批发和零售业'],['7','交通运输、仓储和邮政业'],['8','住宿和餐饮业'],['9','信息传输、软件和信息技术服务业'],['10','金融业'],['11','房地产业'],['12','租赁和商务服务业'],['13','科学研究和技术服务业']
,['14','水利、环境和公共设施管理业'],['15','居民服务、修理和其他服务业'],['16','教育'],['17','科学研究和技术服务业'],['18','卫生和社会'],['19','文化、体育和娱乐业'],['20','公共管理、社会保障和社会组织'],['21','国际组织'],['22','其他']];
//上年销售额
var Chkresult_comboData9=[['1','人民币20亿元以上，人民币12亿元至20亿元（含）'],['2','人民币6亿元至12亿元（含）'],['3','人民币3亿元至6亿元（含）'],['4','人民币1亿元至3亿元（含）'],['5','人民币0.7亿元(含)至1亿元（含）'],['6','人民币0.7亿元以下']];
//本年度预估销售额增幅比例（%）
var Chkresult_comboData10=[['1','10%'],['2','20%'],['3','30%'],['4','40%'],['5','50%'],['6','60%'],['7','70%'],['8','80%'],['9','90%'],['10','100%以上']];
//本年度预估销售额
var Chkresult_comboData11=[['1','100以下'],['2','100（含）~300'],['3','300（含）~500'],['4','500（含）~1000'],['5','1000（含）~3000'],['6','3000（含）以上']];
//企业性质
var Chkresult_comboData12=[['1','国有独资'],['2','国有控股'],['3','国有资本超过 30%的企业'],['4','外资企业'],['5','中外合资企业'],['6','中资企业（其国有资本不超过30%（含），且国有资本投资主体不参与企业的经营管理）']];

//是否上市 XD000343为空，使用是否上市企业：XD000156
var isOnMarStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000156'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//企业性质
var companyNatureStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000059'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//国家和地区代码
var countryStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000029'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//币种
var moneytypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000226'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//所属营业单位  归属机构类型
var businessUnitsStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000271'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//客户类型
var bnnamearStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath + '/cusRelationshipstool!searchBlname.json',
	    method:'GET'
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		root : 'data'
	}, [ 'BL_ID', 'BL_NAME' ])
	});
//行政区划XD000001
var admdivStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000001'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//潜在客户所属行业
var potIndustStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=FJQZKH_OWNBUSI'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});

var fields = [{
			name : 'TEST',
			text : '此文件fields必须要有一个无用字段',
			resutlWidth : 80
		}, {name:'BANK',text:'银行名称',resutlWidth:120}, 
	    {name:'BANK1',text:'授信额度',resutlWidth:120},
	    {name:'BANK2',text:'期限',resutlWidth:130},
	    {name:'BANK3',text:'利率（%）',resutlWidth:130},
	    {name:'BANK4',text:'担保方式',resutlWidth:120},
	    {name:'BANK5',text:'银行编号',hidden:true}];



var custCheckboxGroup = new Ext.form.CheckboxGroup({
    id:'cutGroup',
	name:'cutGroup',
    xtype: 'checkboxgroup',
    itemCls: 'x-check-group-alt',
    columns: 3,
    items: [
        {boxLabel: '正常贷款类客户', name: 'cb-col-1',inputValue:'1',id:'cb-col-1',
		  listeners:{
			   "check" : function(obj,ischecked){
                /*ischeck(obj,ischecked);*/
				isVisible(true,false);
		  }
		}},
        {boxLabel: '存款类客户', name: 'cb-col-2',inputValue:'2',id:'cb-col-2',
		  listeners:{
			   "check" : function(obj,ischecked){
                /*ischeck(obj,ischecked);*/
				if(ischecked){
				isVisible(false,true);}
                else{
				  isVisible(true,false);
				}				
		}}},
        {boxLabel: '潜在客户 ', name: 'cb-col-3',inputValue:'3',id:'cb-col-3',
		  listeners:{
			   "check" : function(obj,ischecked){
               /* ischeck(obj,ischecked);*/
				isVisible(true,false);
		}}}
    ]
});
//控制CheckboxGroup实现单选
/*function ischeck(obj,ischeck){
    if(ischeck){
      var cbgItem = Ext.getCmp('cutGroup').items;
     for(var i=0;i<cbgItem.length;i++){
     if(cbgItem.itemAt(i).name==obj.name){
     }else{
         cbgItem.itemAt(i).setValue(false);
      }
    }
   }
}*/
function isVisible(isv1,isv2){
	cust_form.getForm().findField('BASIC_ACCT_OPEN_DATE').setVisible(isv1);
	cust_form.getForm().findField('BASIC_ACCT_OPEN_DATE').allowBlank=isv2;
	cust_form.getForm().findField('CB_LEVLE').setVisible(isv1);
	cust_form.getForm().findField('CB_LEVLE').allowBlank=isv2;
	cust_form.getForm().findField('CREDIT_LEVEL').setVisible(isv1);
	cust_form.getForm().findField('CREDIT_LEVEL').allowBlank=isv2;
	cust_form.getForm().findField('LINE_OF_CREDIT').setVisible(isv1);
	cust_form.getForm().findField('LINE_OF_CREDIT').allowBlank=isv2;
	cust_form.getForm().findField('OUTSTANDING_LOAN').setVisible(isv1);
	cust_form.getForm().findField('OUTSTANDING_LOAN').allowBlank=isv2;
	cust_form.getForm().findField('LINEUTILIZATION').setVisible(isv1);
	cust_form.getForm().findField('LINEUTILIZATION').allowBlank=isv2;
	cust_form.getForm().findField('NEXT_ANNUAL_TIME').setVisible(isv1);
	cust_form.getForm().findField('NEXT_ANNUAL_TIME').allowBlank=isv2;
}

//基本讯息、交易模式---------开始
var cust_form=new Ext.FormPanel({
	title : '客户关系计划表',
	//reader : basreader,
	collapsible : true,
	autoHeight:true,
	autoWidth:true,
	labelWidth:200,//label的宽度
	labelAlign:'right',
	frame:false,
	autoScroll : true,
	buttonAlign:'center',
	anchor:'95%',
	items : [ {
		xtype : 'fieldset',
		title : '客户关系计划表',
		titleCollapse : true,
		collapsible : true,
//		autoHeight:true,
		items:[{
			layout:'column',
			items:[{
					layout : 'column',
					columnWidth : 1,
					labelWidth : 120,
					items : [ {
						         layout : 'form',
						          columnWidth : .40
						
					           },{
								layout : 'form',
								columnWidth : .60,
								items : [custCheckboxGroup]
							}]
				},{
				columnWidth:1,  
				layout:'form',
				items:[new Com.yucheng.bcrm.common.CustomerQueryFieldRSu({
        				xtype : 'customerrsquery',
        				fieldLabel : '客户名称<font color=red>*</font>',
        				name : 'CUST_NAME',
        				hiddenName:'CUST_ID',
						allowBlank : false,
        				anchor : '90%'
						}),
					  {xtype:'hidden',fieldLabel:'ID',name:'ID'},
					  {xtype:'hidden',fieldLabel:'客户ID',name:'CUST_ID'},
					  {xtype:'hidden',fieldLabel:'计划表ID',name:'PLAN_ID'},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'组织机构代码证/商业登记证号<font color=red>*</font>',name:'IDENT_NO',readOnly:true,cls:'x-readOnly',emptyText : '未知',allowBlank : false},
					  {xtype:'combo',anchor:'90%',fieldLabel:'客户类型<font color=red>*</font>',name:'BL_NAME',store:bnnamearStore,valueField : 'BL_ID',displayField : 'BL_NAME',mode : 'local',editable : true,
		                       typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,allowBlank : false,readOnly:true,cls:'x-readOnly'},
			  {xtype:'hidden',fieldLabel:'客户经理',name:'MGR_ID'},
		      {xtype:'textfield',anchor:'90%',fieldLabel:'客户经理<font color=red>*</font>',name:'MGR_NAME',emptyText : '未知',allowBlank : false,readOnly:true,cls:'x-readOnly'},
		      {xtype:'hidden',fieldLabel:'所属营业单位',name:'ORG_ID'},
		      {xtype:'combo',anchor:'90%',fieldLabel:'所属营业单位<font color=red>*</font>',name:'ORG_NAME',store:businessUnitsStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
                typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,allowBlank : false,readOnly:true,cls:'x-readOnly'},
			  {xtype:'panel',
			   layout:'column',
			   items : [ {
							layout : 'form',
							columnWidth : .5,
							items : [
                                      {xtype:'combo',anchor:'90%',fieldLabel:'企业性质<font color=red>*</font>',name:'ENT_PROPERTY',store:companyNatureStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
                                         typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,allowBlank : false,readOnly:true,cls:'x-readOnly'}, 
							      ]}, {  layout : 'form',
							  columnWidth : .44,
							  items : [{xtype:'combo',anchor:'91%',fieldLabel:'是否上市<font color=red>*</font>',name:'IS_LISTED_CORP',store:isOnMarStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		                                  typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,allowBlank : false,readOnly:true,cls:'x-readOnly'}]
					   }]}
					   
					]
			}
			]
			} 
		
		]
	}, {
		xtype : 'fieldset',
		title : '基本讯息（资料）、交易模式',
		titleCollapse : true,
		collapsible : true,
//		autoHeight:true,
		items:[{
			      layout:'column',
			      items:[
                           /*第一列*/
			             {
				  columnWidth:.31,  
				  layout:'form',
				  items:[
		              {xtype:'textfield',anchor:'90%',fieldLabel:'企业概况<font color=red>*</font>',name:'CORP_PROFILE',maxLength:40},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'法人代表/决策者<font color=red>*</font>',name:'LEGAL_REPR_NAME',allowBlank : false,readOnly:true,cls:'x-readOnly'},
		              {
										name : 'CORP_CULTURE',
										xtype : 'combo',
										fieldLabel : '企业文化类型<font color=red>*</font>',
										width : 180,
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData3
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										multiSelect : true,
								        multiSeparator : ',',
										listeners : { 
														blur : function() {
														
														}
													}				
						},	
		              {xtype:'hidden',fieldLabel:'所属行业',name:'INDUST_TYPE'},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'所属行业<font color=red>*</font>',name:'INDUST_TYPE_ORA',allowBlank : false,readOnly:true,cls:'x-readOnly'},
		              {xtype:'combo',anchor:'90%',fieldLabel:'所属行业<font color=red>*</font>',name:'POT_INDUST_TYPE',store:potIndustStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,readOnly:true,cls:'x-readOnly',hidden:true},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'行业细分/主要产品<font color=red>*</font>',name:'MAIN_BUSINESS',maxLength:40,readOnly:true,cls:'x-readOnly'},
		              {xtype:'textfield',anchor:'90%',fieldLabel:'经营年限（年 ）<font color=red>*</font>',name:'SPANYEARS',vtype:'number',maxLength:40,allowBlank : false,readOnly:true,cls:'x-readOnly'},
		             // {xtype:'textfield',anchor:'90%',fieldLabel:'企业位置<font color=red>*</font>',name:'REGISTER_AREA',allowBlank : false},
		              {xtype:'combo',anchor:'90%',fieldLabel:'企业位置<font color=red>*</font>',name:'REGISTER_AREA',store:admdivStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,readOnly:true,cls:'x-readOnly'},
			          {xtype:'textfield',anchor:'90%',fieldLabel:'上年销售额（万元）<font color=red>*</font>',name:'SALE_AMT',vtype:'number',maxLength:40,allowBlank : false,readOnly:true,cls:'x-readOnly'},
						{
										name : 'SALE_ESTIMATE',
										xtype : 'combo',
										fieldLabel : '本年度预估销售额<font color=red>*</font>',
										width : 180,
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData11
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										listeners : { 
														blur : function() {
														
														}
													}
											
						},{
										name : 'SALE_RANGE_ESTIMATE',
										xtype : 'combo',
										fieldLabel : '本年度预估销售额增幅比例（%）<font color=red>*</font>',
										width : 180,
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData10
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										listeners : { 
														blur : function() {
														
														}
													}
						},
						{xtype:'textfield',anchor:'90%',fieldLabel:'员工人数<font color=red>*</font>',vtype:'number',name:'EMPLOYEE_SCALE',allowBlank : false,readOnly:true,cls:'x-readOnly'},
						{xtype:'lovcombo',anchor:'90%',fieldLabel:'销售地区(前两大)<font color=red>*</font>',name:'SALE_AREA',
		                 store:countryStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true,
					  allowBlank : false
					  },
					  {xtype:'lovcombo',anchor:'90%',fieldLabel:'采购地区(前两大)<font color=red>*</font>',name:'PURCHASE_AREA',
		                 store:countryStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true,
					  allowBlank : false
					  }
						
		          ]
		      },
		      /*第二列*/
		      {
				columnWidth:.31,  
				layout:'form',
				items:[     {xtype:'panel',
			                layout:'column',
							columnWidth:1,
			                items : [ {
							layout : 'form',
							columnWidth : .7,
							items : [{  
										name : 'SETTLE_TYPE_FIR',
										xtype : 'combo',
										fieldLabel : '销售结算方式（主要三类）<font color=red>*</font>',
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData4
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false
			            }]}, {  layout : 'form',
							  columnWidth : .15,
							  items : [{
										name : 'SETTLE_TYPE_SEC',
										xtype : 'combo',
										hideLabel:true,
										anchor : '80%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData4
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false
										
									}]
							
					   }, {  layout : 'form',
							  columnWidth : .15,
							  items : [{
										name : 'SETTLE_TYPE_THIR',
										xtype : 'combo',
										hideLabel:true,
										anchor : '80%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData4
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false
										
									}]
							
					   }]},
					   {xtype:'panel',
			                layout:'column',
							columnWidth:1,
			                items : [ {  layout : 'form',
							  columnWidth : .7,
							  items : [{xtype:'textfield',anchor:'90%',fieldLabel:'结算比例<font color=red>*</font>',name:'SETTLE_TYPE_FIR_SCALE',maxLengths:10,allowBlank : false}]	
					      }, {  layout : 'form',
							  columnWidth : .15,
							  items : [{xtype:'textfield',anchor:'80%',hideLabel:true,name:'SETTLE_TYPE_SEC_SCALE',maxLengths:10,allowBlank : false}]	
					      }, {  layout : 'form',
							  columnWidth : .15,
							  items : [{xtype:'textfield',anchor:'80%',hideLabel:true,name:'SETTLE_TYPE_THIR_SCALE',maxLengths:10,allowBlank : false}]	
					      }
					   ]},   
		              {
										name : 'RECEIVABLES_CYCLE',
										xtype : 'combo',
										fieldLabel : '应收账款周转天期<font color=red>*</font>',
										width : 180,
										anchor : '95%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData5
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										multiSelect : true,
								        multiSeparator : ',',
										listeners : { 
														blur : function() {
														
														}
													}
					},
					{xtype:'panel',
			                layout:'column',
							columnWidth:1,
			                items : [ {
							layout : 'form',
							columnWidth : .7,
							items : [{  
										name : 'PURCHASE_TYPE_FIR',
										xtype : 'combo',
										fieldLabel : '采购结算方式（主要三类）<font color=red>*</font>',
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData4
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false
			            }]}, {  layout : 'form',
							  columnWidth : .15,
							  items : [{
										name : 'PURCHASE_TYPE_SEC',
										xtype : 'combo',
										hideLabel:true,
										anchor : '80%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData4
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false
										
									}]
							
					   }, {  layout : 'form',
							  columnWidth : .15,
							  items : [{
										name : 'PURCHASE_TYPE_THIR',
										xtype : 'combo',
										hideLabel:true,
										anchor : '80%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData4
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false
										
									}]
							
					   }]},
					   {xtype:'panel',
			                layout:'column',
							columnWidth:1,
			                items : [ {  layout : 'form',
							  columnWidth : .7,
							  items : [{xtype:'textfield',anchor:'90%',fieldLabel:'结算比例<font color=red>*</font>',name:'PURCHASE_TYPE_FIR_SCALE',maxLengths:10,allowBlank : false}]	
					      }, {  layout : 'form',
							  columnWidth : .15,
							  items : [{xtype:'textfield',anchor:'80%',hideLabel:true,name:'PURCHASE_TYPE_SEC_SCALE',maxLengths:10,allowBlank : false}]	
					      }, {  layout : 'form',
							  columnWidth : .15,
							  items : [{xtype:'textfield',anchor:'80%',hideLabel:true,name:'PURCHASE_TYPE_THIR_SCALE',maxLengths:10,allowBlank : false}]	
					      }
					   ]}, 
					  {
										name : 'ACCOUNTS_PAYABLE_CYCLE',
										xtype : 'combo',
										fieldLabel : '应付账款周转天期<font color=red>*</font>',
										width : 180,
										anchor : '95%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData5
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										multiSelect : true,
								        multiSeparator : ',',
										listeners : { 
														blur : function() {
														
														}
													}
					},
		              {xtype:'textfield',anchor:'95%',fieldLabel:'主要原材料<font color=red>*</font>',name:'MAIN_MATERIAL',allowBlank : false},
					  {xtype:'textfield',anchor:'95%',fieldLabel:'原材料采购量<font color=red>*</font>',name:'MATERIAL_AMMOUNT',allowBlank : false},
					  {xtype:'lovcombo',anchor:'95%',fieldLabel:'应收账款币种（前两大）<font color=red>*</font>',name:'RECEIVABLES_CURRENCE',
		                store:moneytypeStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true,
					  allowBlank : false
					  },
					  {xtype:'lovcombo',anchor:'95%',fieldLabel:'应付账款币种（前两大）<font color=red>*</font>',name:'ACCOUNTS_PAYABLE_CURRENCE',
		                 store:moneytypeStore,valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true,
					  allowBlank : false
					  },
					  {xtype:'textfield',anchor:'95%',fieldLabel:'出口量<font color=red>*</font>',name:'EXPORT_VOLUME',allowBlank : false},
					  {xtype:'textfield',anchor:'95%',fieldLabel:'进口量<font color=red>*</font>',name:'IMPORT_VOLUME',allowBlank : false}
		             
			       ]
			},
			/*第三列*/
			{
				columnWidth:.31,  
				layout:'form',
			    items:[  
				   {xtype:'datefield',name : 'BASIC_ACCT_OPEN_DATE',format:'Y-m-d',fieldLabel : '建立合作起始日<font color=red>*</font>',anchor : '90%',allowBlank : false,readOnly:true,cls:'x-readOnly'},
				   {
										name : 'CREDIT_LEVEL',
										xtype : 'combo',
										fieldLabel : '法金信用评等<font color=red>*</font>',
										width : 180,
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData6
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										multiSelect : true,
								        multiSeparator : ',',
										listeners : { 
														blur : function() {
														
														}
													}
						},
						{
										name : 'CB_LEVLE',
										xtype : 'combo',
										fieldLabel : 'CB评等<font color=red>*</font>',
										width : 180,
										anchor : '90%',
										mode : 'local',
										store :new Ext.data.SimpleStore(
												  {
													  fields:['key','value'],
													  data:Chkresult_comboData7
													 }),
										triggerAction : 'all',
										displayField : 'value',
										valueField : 'key',
										editable : false,
										allowBlank : false,
										multiSelect : true,
								        multiSeparator : ',',
										listeners : { 
														blur : function() {
														
														}
													}
					},
					{xtype:'textfield',anchor:'90%',fieldLabel:'总授信额度<font color=red>*</font>',name:'LINE_OF_CREDIT',allowBlank : false,
					  listeners : { 
							blur : function() {
								var lineUtilization=0;
								if(cust_form.getForm().findField('LINE_OF_CREDIT').getValue()!=''&&cust_form.getForm().findField('LINE_OF_CREDIT').getValue()!=0){
								  lineUtilization=(cust_form.getForm().findField('OUTSTANDING_LOAN').getValue()/cust_form.getForm().findField('LINE_OF_CREDIT').getValue()).toFixed(2)*100;
								}else{
									lineUtilization=0;
								}
								cust_form.getForm().findField('LINEUTILIZATION').setValue(lineUtilization.toFixed(2));						
						     }
						}
					},
					{xtype:'textfield',anchor:'90%',fieldLabel:'目前贷款余额<font color=red>*</font>',name:'OUTSTANDING_LOAN',allowBlank : false,
					   listeners : { 
							blur : function() {
								var lineUtilization=0;
								if(cust_form.getForm().findField('LINE_OF_CREDIT').getValue()!=''&&cust_form.getForm().findField('LINE_OF_CREDIT').getValue()!=0){
								  lineUtilization=(cust_form.getForm().findField('OUTSTANDING_LOAN').getValue()/cust_form.getForm().findField('LINE_OF_CREDIT').getValue()).toFixed(2)*100;
								}else{
									lineUtilization=0;
								}
								cust_form.getForm().findField('LINEUTILIZATION').setValue(lineUtilization.toFixed(2));						
						     }
						}
					},
					{xtype:'textfield',anchor:'90%',fieldLabel:'额度使用率%<font color=red>*</font>',name:'LINEUTILIZATION',cls:'x-readOnly',readOnly:true,allowBlank : false},
					{xtype:'datefield',name : 'NEXT_ANNUAL_TIME',format:'Y-m-d',fieldLabel : '下次年审时间<font color=red>*</font>',anchor : '90%',allowBlank : false}
				]
			 }
			]
		}]
	}]
});

//基本讯息、交易模式---------结束




//与其他银行合作信息--------开始
var cust_formupdate = new Ext.form.FormPanel({
    title:'与其他银行合作信息',
    autoWidth:true,
	height:260,
    collapsible : true,
	autoScroll: true,
	buttonAlign:'center',
	padding : '10px 0px 0px',
	layout : 'form', // 整个大的表单是form布局,从上往下
	labelAlign : 'center',
	frame : false,
	items : [{
				layout : 'column',
				labelWidth : 100,
				columnWidth : 1,
				items : [ {
							layout : 'form',
							columnWidth : .4,
							items : [{
										name : 'L_BANKNAME',
										xtype : 'textfield',
										fieldLabel : '银行名称',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						},{
							layout : 'form',
							columnWidth : .4,
							items : [{
										name : 'L_LIMITMONEY',
										xtype : 'textfield',
										fieldLabel : '授信额度',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						}]
			},{
				layout : 'column',
				labelWidth : 100,
				columnWidth : 1,
				items : [ {
							layout : 'form',
							columnWidth : .4,
							items : [{
										name : 'L_MEMO',
										xtype : 'textfield',
										fieldLabel : '期限',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						},{
							layout : 'form',
							columnWidth : .4,
							items : [{
										name : 'L_RATE',
										xtype : 'textfield',
										fieldLabel : '利率（%）',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						}]
			},{
				layout : 'column',
				labelWidth : 100,
				columnWidth : 1,
				items : [{
							layout : 'form',
							columnWidth : .4,
							items : [{
										name : 'L_COLLATERAL',
										xtype : 'textfield',
										fieldLabel : '担保方式',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						},{
							layout : 'form',
							columnWidth : .2,
							items : [{
										name : 'ID',
										hidden:true,
										xtype : 'textfield',
										fieldLabel : 'ID',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						},{
							layout : 'form',
							columnWidth : .2,
							items : [{
										name : 'TASK_NUMBER',
										hidden:true,
										xtype : 'textfield',
										fieldLabel : 'TASK_NUMBER',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										anchor : '95%'
									}]
						}]
			}],
			buttons:[{
	                xtype : 'button',
					text : '重置',
					width : 60,
					handler : function() {
						cust_formupdate.getForm().reset();
					}},{
	                xtype : 'button',
					text : '保存',
					width : 60,
					handler : function() {
						var saveUrl='';
						var data=cust_formupdate.getForm().getFieldValues();
						if(data.ID==''){
						saveUrl='/ocrmFInterviewLoanbankRelation.json?flag=0';//新增
						}else{
							saveUrl='/ocrmFInterviewLoanbankRelation.json?flag=1';//修改
						}
						var commintData =translateDataKey(data,1);
						Ext.Msg.wait('正在保存，请稍等...', '提示');
						Ext.Ajax.request({
							url : basepath + saveUrl,
							method : 'POST',
							params :commintData, 
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function(response) {
								Ext.Msg.alert('提示', '操作成功！');
								cust_formupdate.getForm().reset();
								_window.hide();
								transedStore.load({params:{strnum:cust_form.getForm().findField('CUST_ID').getValue()}});
							},
							failure : function() {
								Ext.Msg.alert('提示', '操作失败');
								_window.hide();
								transedStore.load({params:{strnum:cust_form.getForm().findField('CUST_ID').getValue()}});
							}
						})
						
					}}
			]
});

var _window=new Ext.Window({   
        width:700,
	    height:350,
		modal:true,
	    closeAction:'hide',
		autoScroll: true,
	    constrain:true,
		collapsible : true,
        plain:true,   
		buttonAlign:'center',
        items:[cust_formupdate],
		buttons:[{
		 text:'关闭',
		 handler:function(){
		 _window.hide();
		 }
		}]
    });

var transedStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFInterviewLoanbankRelation.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'TASK_NUMBER'},
			    {name:'L_BANKNAME'},
			    {name:'L_LIMITTYPE'},
			    {name:'L_LIMITMONEY'},
			    {name:'L_BALANCE'},
			    {name:'L_RATE'},
			    {name:'L_DBRATE'},
			    {name:'L_COLLATERAL'},
			    {name:'L_MEMO'}
			     ])
});


var num = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});

var sm= new Ext.grid.CheckboxSelectionModel();//要显示多选框列必加此项

var cm = new Ext.grid.ColumnModel([ num, sm,// 定义列模型
{
	header : '银行名称',
	align:'center',
	dataIndex : 'L_BANKNAME',
	sortable : true,
	width : 300
}, {
	header : '授信额度',
	align:'center',
	dataIndex : 'L_LIMITMONEY',
	sortable : true,
	width : 150
}, {
	header : '期限',
	align:'center',
	dataIndex : 'L_MEMO',
	sortable : true,
	width : 150
}, {
	header : '利率（%）',
	align:'center',
	dataIndex : 'L_RATE',
	sortable : true,
	width : 150
}, {
	header : '担保方式',
	align:'center',
	dataIndex : 'L_COLLATERAL',
	sortable : true,
	width : 220
}, {
	header : '银行编号',
	align:'center',
	dataIndex : 'ID',
	sortable : true,
	hidden:true,
	width : 150
}]);

var custGrid = new Ext.grid.GridPanel({
	autoScroll : true,
	width:'95%',
	autoHeight: true,
	region : 'center',
	store : transedStore,
	frame : true,
	stripeRows : true, // 斑马线
	cm : cm,
	sm:sm,
	renderTo:Ext.getBody(),
    tbar:[
     {
    id:"addbtnS",
    text:"新增",
    handler:function(){
	 cust_formupdate.getForm().reset();
	//关联与其他银行合作信息
		cust_formupdate.getForm().findField('TASK_NUMBER').setValue(cust_form.getForm().findField('CUST_ID').getValue());
       _window.show();
    }
   }, {
    id:"updatbtnS",
    text:"修改",
    handler:function(){
    editInit();
    }
   }, {
    id:"deletebtnS",
    text:"删除",
    handler:function(){
     if(custGrid.getSelectionModel().getSelected() == null){
    		Ext.Msg.alert('提示','请选择数据!');
			return false;
    	}
    Ext.MessageBox.confirm('提示', '确定要删除吗?', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {
				return false;
			}
			var tempIdStr='';
			var selectRec=custGrid.getSelectionModel().getSelections();
			for ( var i = 0; i < selectRec.length; i++) {
				if (i == 0) {
					tempIdStr = "'" + selectRec[i].data.ID
							+ "'";
				} else {
					tempIdStr += "," + "'"
							+ selectRec[i].data.ID + "'";
				}
			}
			Ext.Ajax.request({
				url : basepath + '/ocrmFInterviewLoanbankRelation!batchDel.json',
				method : 'GET',
				params : {
					idStr : tempIdStr
				},
				success : function() {
					Ext.Msg.alert('提示', '删除成功');
					transedStore.load({params:{strnum:cust_form.getForm().findField('CUST_ID').getValue()}});
				},
				failure : function() {
					Ext.Msg.alert('提示', '删除失败');
					transedStore.load({params:{strnum:cust_form.getForm().findField('CUST_ID').getValue()}});
				}
			});
	});			
    }
   }
  ],//设置菜单按钮
    loadMask:{
      msg:"数据正在加载中...."
    },
	viewConfig : {}
});


var cust_fieldSet =new Ext.form.FieldSet({
                 title: "与其他银行合作信息",
                 autoWidth: true,
                 autoHeight: true,
                 collapsible : true,
                 items: [
                   custGrid
                 ]
             });
			 
    custGrid.on('rowdblclick', function(custGrid, rowIndex, event) {
        editInit();
    });
	
    function editInit(){
        var selectLength = custGrid.getSelectionModel().getSelections().length;  
        if(selectLength > 1){
             Ext.Msg.alert('提示','请选择一条记录!');
        } else{
        var infoRecord = custGrid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
         }
            cust_formupdate.getForm().loadRecord(infoRecord);
            _window.show();
        }
    }

//与其他银行合作信息--------结束

//-------------------------------------------------------客户二维分析图开始--------------------------------------
var cust_form3_analysis =new Ext.form.FieldSet({
    title: "Wallet-Size客户二维分析图",
    autoWidth: true,
    autoHeight: true,
    collapsible : true,
    items: []
})

//客户类型判断
var typecus=function(blnamev,ztradefinance04v,products1v,ztradefinance03v){
	//展示客户所属属性
	var cusProperty='';
	//展示客户所属类型
	var customerTypev='';
	
	if(ztradefinance04v<30){
		if(blnamev=="CB"&&products1v<3){
			if(ztradefinance03v<4){
			//萌芽型
			customerTypev="丙类";
			cusProperty="萌芽型";
		    }else if(ztradefinance03v>=4){
			//潜力型
			customerTypev="丙类";
			cusProperty="潜力型";
		    }	
		}else if(blnamev=="CB"&&products1v>=3){
			if(ztradefinance03v<4){
			//调整型
		    customerTypev="乙类";
			cusProperty="调整型";
		    }else if(ztradefinance03v>=4){
			//深耕型
			customerTypev="乙类";
			cusProperty="深耕型";
		    }
		}
		if(blnamev=="RE"&&products1v<2){
			if(ztradefinance03v<8){
			//萌芽型
			customerTypev="丙类";
			cusProperty="萌芽型";
		    }else if(ztradefinance03v>=8){
			//潜力型
			customerTypev="丙类";
			cusProperty="潜力型";
		    }	
		}else if(blnamev=="RE"&&products1v>=2){
			if(ztradefinance03v<8){
			//调整型
			customerTypev="乙类";
			cusProperty="调整型";
		    }else if(ztradefinance03v>=8){
			//深耕型
			customerTypev="乙类";
			cusProperty="深耕型";
		    }
		}
		
	}else if(ztradefinance04v>=30){
		if(blnamev=="CB"&&products1v<3){
			if(ztradefinance03v<4){
			//成长型
			customerTypev="丁类";
			cusProperty="成长型";
		    }else if(ztradefinance03v>=4){
			//成熟型
			customerTypev="丁类";
			cusProperty="成熟型";
		    }
		}else if(blnamev=="CB"&&products1v>=3){
			if(ztradefinance03v<4){
			//维护型
			customerTypev="甲类";
			cusProperty="成熟型";
		    }else if(ztradefinance03v>=4){
			//主力型
			customerTypev="甲类";
			cusProperty="主力型";
		    }
		}
        if(blnamev=="RE"&&products1v<2){
			if(ztradefinance03v<8){
			//成长型
			customerTypev="丁类";
			cusProperty="成长型";
		    }else if(ztradefinance03v>=8){
			//成熟型
			customerTypev="丁类";
			cusProperty="成熟型";
		    }
		}else if(blnamev=="RE"&&products1v>=2){
			if(ztradefinance03v<8){
			//维护型
			customerTypev="甲类";
			cusProperty="成熟型";
		    }else if(ztradefinance03v>=8){
			//主力型
			customerTypev="甲类";
			cusProperty="主力型";
		    }
		}
	  }
     return "客户类型："+customerTypev+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  客户属性："+cusProperty;	  
}	

//Wallet-Size客户定位二维分析图 函数
function saveUser_ajaxSubmit1() { 
	//初始化
	cust_form3_analysis.removeAll();
	
	var tradefinancepie33v1=100;
	var tradefinancepie33v;
	var ztradefinance04v;
	var productspiev;
	var mztradefinance04v;
	var mproductspiev;
	var products1v;
	//Wallet-Size y值 tradefinancepie33v  x值暂时为100.%
	var tradefinancepie33v=cust_form3.getForm().findField('tradefinancepie33').getValue();
	//var tradefinancepie33v=cust_form3.getForm().findField('tradefinancepie33').getValue();
	//SOW x值 总计第四列的值 ztradefinance04v  y值  我行目前提供产品使用比例%  productspiev
	var ztradefinance04v=cust_form3.getForm().findField('ztradefinance04').getValue();
	var productspiev=cust_form3.getForm().findField('productspie').getValue();
	
	//客户适合可以使用产品
	var products1v=cust_form3.getForm().findField('SUIT_PRODUCTS').getValue();
	//Wallet size产品使用总数
	var productsumsv=cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').getValue();
	//我行目前提供产品总数
	var productscount1=cust_form3.getForm().findField('PROVIDE_PRODUCTS').getValue();
	
    //客户类型
	var blnamev=cust_form.getForm().findField('BL_NAME').getValue();
	//银行收益
	var ztradefinance03v=cust_form3.getForm().findField('ztradefinance03').getValue()*1000/1000000;

	var customerTypev=typecus(blnamev,ztradefinance04v,products1v,ztradefinance03v);
	
	 
	//Wallet-Size营销目标二维分析图      二维分析图面板
	var fourq3= new Ext.Panel({
		autoHeight:true,
		frame:false,
		autoScroll : true,
		layout:'column',
		style:'padding:0px 0px 0px 0px',
		 draggable:false,
		items:[{
			columnWidth:.5,  
			layout:'form',
			items:[ {
				id:"first_23",
				autoWidth: true,
				autoHeight:true,
				style:'padding:0px 0px 0px 0px',
				//renderTo: Ext.getBody(),
				html: ' <iframe scrolling="auto" id="firstfigure2d3" frameborder="0" width="100%" height="520px" src="/crmweb/jspdemo/threedbubblefir.jsp?tradefinancepie33v='+tradefinancepie33v+'&ztradefinance04v='+ztradefinance04v+'&productspiev='+productspiev+'&tradefinancepie33v1='+tradefinancepie33v1+'&customerTypev='+customerTypev+'"> </iframe>' 
			}]
			},{
			columnWidth:.5,  
			layout:'form',
			items:[ {
				id:"first_three3",
				autoWidth: true,
				autoHeight:true,
				style:'padding:0px 0px 0px 0px',
				//renderTo: Ext.getBody(),
				html: ' <iframe scrolling="auto" id="firstthreefigure3" frameborder="0" width="100%" height="520px" src="/crmweb/jspdemo/threedscatteraxisfir.jsp?ztradefinance04v='+ztradefinance04v+'&products1v='+products1v+'&ztradefinance03v='+ztradefinance03v+'"> </iframe>' 
			 }]
		},
		{
			columnWidth:1,  
			layout:'form',
			items:[ {
				   xtype:"button",
				   style:'margin:0px auto',
				   align:"center",
				   anchor : '15%',
			    	text:"生成分析图",
			        listeners:{
			       	 "click":function(){
			       		if(cust_form.getForm().findField('BL_NAME').getValue()==""){
			       			Ext.Msg.alert("验证提示","请选择“客户类型”:CB或RM");
			       			return;
			       		}
			       		 saveUser_ajaxSubmit1();		 
			       	 } 
			     }}]
		}/*,
		{
			columnWidth:.5,  
			layout:'form',
			items:[ 
			     {
					   xtype:"button",
					   align:"center",
					   style:'margin:0px auto',
					   anchor : '30%',
				    	text:"重置",
				        listeners:{
				       	 "click":function(){
				       		 var temp1=cust_form3.getForm().findField('CUST_ID').getValue();
					       	 var temp2=cust_form3.getForm().findField('ID').getValue();
					       	 var temp3=cust_form3.getForm().findField('PLAN_ID').getValue();
					       		cust_form3.getForm().reset();
					       	 if(temp1!=""&&temp2!=""){
					       		cust_form3.getForm().findField('CUST_ID').setValue(temp1);
					       		cust_form3.getForm().findField('ID').setValue(temp2);
					       		cust_form3.getForm().findField('PLAN_ID').setValue(temp3);
					       	 }
				       	 } 
				     }} ]
		 }*/
			
		]
	});
	cust_form3_analysis.add(fourq3);
	cust_form3_analysis.render(fourq3);
	cust_form3_analysis.doLayout();
	
} 


//Wallet-Size营销目标二维分析图 计算函数
 function saveUser_ajaxSubmit4() { 
	 cust_form4_analysis.removeAll();
	 
	var ztradefinance04v;
	var productspiev;
	var mztradefinance04v;
	var mproductspiev;

	//SOW y值 总计第四列的值 ztradefinance04v  x值  我行目前提供产品使用比例%  productspiev
	var ztradefinance04v=cust_form3.getForm().findField('ztradefinance04').getValue();
	var productspiev=cust_form3.getForm().findField('productspie').getValue();

	//Wallet-Size营销目标二维分析图\n目前SOW 同上SOW的值\n Target SOW y值 总计第四列的值 mztradefinance04v  x值  我行明年目标提供产品使用比例%  mproductspiev
	var mztradefinance04v=cust_form4.getForm().findField('mztradefinance04').getValue();
	var mproductspiev=cust_form4.getForm().findField('mproductspie').getValue();
	
  //客户适合可以使用产品
	var products1v=cust_form3.getForm().findField('SUIT_PRODUCTS').getValue();
	//银行收益
	var ztradefinance03v=cust_form3.getForm().findField('ztradefinance03').getValue()*1000/1000000;
	
	//客户类型
	var mblnamev=cust_form.getForm().findField('BL_NAME').getValue();
	//客户适合可以使用产品
	var mproducts1v=cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue();
	//明年银行收益
	var mztradefinance03v=cust_form4.getForm().findField('mztradefinance03').getValue()*1000/1000000;
	var mztradefinance04v=cust_form4.getForm().findField('mztradefinance04').getValue();
	//汇总客户类型
	var customerTypev=typecus(mblnamev,mztradefinance04v,products1v,mztradefinance03v);

   //Wallet-Size营销目标二维分析图      二维分析图面板
	var fourq4= new Ext.Panel({
		autoHeight:true,
		frame:false,
		autoScroll : true,
		layout:'column',
		style:'padding:0px 0px 0px 0px',
		 draggable:false,
		items:[{
			columnWidth:.5,  
			layout:'form',
			items:[ {
				id:"second_24",
				autoWidth: true,
				autoHeight:true,
				//renderTo: Ext.getBody(),
				html: ' <iframe scrolling="auto" id="secfigure2d4" frameborder="0" width="100%" height="520px" src="/crmweb/jspdemo/threedbubblesec.jsp?ztradefinance04v='+ztradefinance04v+'&productspiev='+productspiev+'&mztradefinance04v='+mztradefinance04v+'&mproductspiev='+mproductspiev+'&customerTypev='+customerTypev+'"> </iframe>' 
			 }]
			},{
			columnWidth:.5,  
			layout:'form',
			items:[ {
				id:"second_three4",
				autoWidth: true,
				autoHeight:true,
				//renderTo: Ext.getBody(),
				html: ' <iframe scrolling="auto" id="secthreefigure4" frameborder="0" width="100%" height="520px" src="/crmweb/jspdemo/threedscatteraxissec.jsp?ztradefinance04v='+mztradefinance04v+'&products1v='+products1v+'&ztradefinance03v='+mztradefinance03v+'"> </iframe>' 
			}]
		},
		{
			columnWidth:1,  
			layout:'form',
			items:[ {
				   xtype:"button",
				   style:'margin:0px auto',
				   align:"center",
				   anchor : '15%',
			    	text:"生成分析图",
			        listeners:{
			       	 "click":function(){
			       		if(cust_form.getForm().findField('BL_NAME').getValue()==""){
			       			Ext.Msg.alert("验证提示","请选择“客户类型”:CB或RM");
			       			return false;
			       		}
			       		 saveUser_ajaxSubmit4();	 
			       	 } 
			     }}]
		}/*,
		{
			columnWidth:.5,  
			layout:'form',
			items:[ 
			     {
					   xtype:"button",
					   align:"center",
					   style:'margin:0px auto',
					   anchor : '30%',
				    	text:"重置",
				        listeners:{
				       	 "click":function(){
				       		var temp1=cust_form4.getForm().findField('CUST_ID').getValue();
					       	 var temp2=cust_form4.getForm().findField('ID').getValue();
					       	 var temp3=cust_form4.getForm().findField('PLAN_ID').getValue();
					       		cust_form4.getForm().reset();
					       	 if(temp1!=""&&temp2!=""){
					       		cust_form4.getForm().findField('CUST_ID').setValue(temp1);
					       		cust_form4.getForm().findField('ID').setValue(temp2);
					       		cust_form4.getForm().findField('PLAN_ID').setValue(temp3);
					       	 }
				       	 } 
				     }} ]
		 }*/]
	});
	cust_form4_analysis.add(fourq4);
	cust_form4_analysis.render(fourq4);
	cust_form4_analysis.doLayout();
} 
var cust_form4_analysis =new Ext.form.FieldSet({
    title: "Wallet-Size营销目标二维分析图",
    autoWidth: true,
    autoHeight: true,
    collapsible : true,
    items: []
})
//-------------------------------------------------------客户二维分析图   --结束--------------------------------------
//分析

var cust_form3 = new Ext.form.FormPanel({
    title:'Wallet Size分析',
     autoWidth: true,
	 autoHeight: true,
    collapsible : true,
	autoScroll: true,
	padding : '10px 0px 0px',
	layout : 'table', // 整个大的表单是table布局,从上往下
	labelAlign : 'right',
	buttonAlign:'center',
	layoutConfig:{columns:7},//将父容器分成7列
	defaults : {  
         layout:'form',
         width:200
      }, 
	items : [  {     
				 colspan:5,
				 xtype: "label",
				 html:"<div style='height:20px;width:947px;line-height:20px;border:1px solid #a6b1be;text-align:center'>今年Wallet Size分析（最新PBOC）</div>"		  
				 },
				 {
				  colspan:2,
				  xtype: "label",
                  html:"<div style='height:20px;width:398px;line-height:20px;border:1px solid #a6b1be;text-align:center'>目前于我行情况</div>"				  
				 },
				  {
				   colspan:2,
				   xtype: "label",
                   html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>产品</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>均量/年(RMB'000)</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>Margin/Spread（%）</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>银行收益（RMB千元）/年</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行目前占比%（SOW）</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行目前收益（RMB千元）/年</div>"				   
				 },{
				   rowspan:3,
				   xtype: "label",
				   html:"<div style='height:64px;width:111px;line-height:64px;border:1px solid #a6b1be;text-align:center'>存款类&nbsp;</div>"	
				 },{
                    xtype: "label",
					html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>折合人民币():&nbsp;</div>"		
				   },{
				    name : 'DEPOSIT_RMB_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('DEPOSIT_RMB_AVERAGE','DEPOSIT_RMB_MARGIN','DEPOSIT_RMB_PROPORTION','rmb30','rmb50','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum(cust_form3,'DEPOSIT_RMB_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											countClom1=countClom1-1;
										}
										  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'DEPOSIT_RMB_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('DEPOSIT_RMB_AVERAGE','DEPOSIT_RMB_MARGIN','DEPOSIT_RMB_PROPORTION','rmb30','rmb50','zwrmb05');
									}
								}
				   },{
				    name : 'rmb30',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'DEPOSIT_RMB_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('DEPOSIT_RMB_AVERAGE','DEPOSIT_RMB_MARGIN','DEPOSIT_RMB_PROPORTION','rmb30','rmb50','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum2(cust_form3,'DEPOSIT_RMB_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											countClom4=countClom4-1;
										}
										  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'rmb50',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
					html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>交易手续费:</div>"
				   },{
				    name : 'DEPOSIT_TRADE_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0Fun('DEPOSIT_TRADE_AVERAGE','DEPOSIT_TRADE_PROPORTION','rmb31','rmb51','zwrmb05');
									}
								}
				   },{
				    name : 'DEPOSIT_TRADE_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
					readOnly:true,
					cls:'x-readOnly',
				    anchor : '95%'
				   },{
				    name : 'rmb31',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'DEPOSIT_TRADE_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0Fun('DEPOSIT_TRADE_AVERAGE','DEPOSIT_TRADE_PROPORTION','rmb31','rmb51','zwrmb05');
									}
								}
				   },{
				    name : 'rmb51',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>其他:</div>"					
				   },{
				    name : 'DEPOSIT_OTHER_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0Fun('DEPOSIT_OTHER_AVERAGE','DEPOSIT_OTHER_PROPORTION','rmb32','rmb52','zwrmb05');
									}
								}
				   },{
				    name : 'DEPOSIT_OTHER_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
					readOnly:true,
					cls:'x-readOnly',
				    anchor : '95%'
				   },{
				    name : 'rmb32',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'DEPOSIT_OTHER_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					readOnly:true,
					cls:'x-readOnly',
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0Fun('DEPOSIT_OTHER_AVERAGE','DEPOSIT_OTHER_PROPORTION','rmb32','rmb52','zwrmb05');
									}
								}
				   },{
				    name : 'rmb52',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				   rowspan:4,
				   xtype: "label",
                   html:"<div style='height:88px;width:111px;line-height:88px;border:1px solid #a6b1be;text-align:center'>外汇类</div>"					   
				 },{
                    xtype: "label",
                    html:"<div style='height:21px;width:234px;line-height:21px;border:1px solid #a6b1be;'>即期交易量（CB）:</div>"					
				   },{
				    name : 'EXCHANGE_IMMEDIATE_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_IMMEDIATE_MARGIN','EXCHANGE_IMMEDIATE_PROPORTION','wrmb30','wrmb50','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_FORWARD_AVERAGE','EXCHANGE_INTEREST_AVERAGE','OPTIONS_TRADING_AVERAGE');
										countClom1=countClom1+parseInt(con);
										var f1=isAllEmps(cust_form3,'EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_FORWARD_AVERAGE','EXCHANGE_INTEREST_AVERAGE','OPTIONS_TRADING_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom1=countClom1-1;
											}
										}
										if(f1==true){
										 countClom1=countClom1-1;
										}
										 cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
									
								}
				   },{
				    name : 'EXCHANGE_IMMEDIATE_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_IMMEDIATE_MARGIN','EXCHANGE_IMMEDIATE_PROPORTION','wrmb30','wrmb50','zwrmb05');
									}
								}
				   
				   },{
				    name : 'wrmb30',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'EXCHANGE_IMMEDIATE_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_IMMEDIATE_MARGIN','EXCHANGE_IMMEDIATE_PROPORTION','wrmb30','wrmb50','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_FORWARD_PROPORTION','EXCHANGE_INTEREST_PROPORTION','OPTIONS_TRADING_PROPORTION');
										countClom4=countClom4+parseInt(con);
										var f1=isAllEmps(cust_form3,'EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_FORWARD_PROPORTION','EXCHANGE_INTEREST_PROPORTION','OPTIONS_TRADING_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom4=countClom4-1;
											}
										}
										if(f1==true){
										  countClom4=countClom4-1;
										}
										 cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   }
				   ,{
				    name : 'wrmb50',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>远期交易量（CB）:</div>"						
				   },{
				    name : 'EXCHANGE_FORWARD_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE','EXCHANGE_FORWARD_MARGIN','EXCHANGE_FORWARD_PROPORTION','wrmb31','wrmb51','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'EXCHANGE_FORWARD_AVERAGE','EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_INTEREST_AVERAGE','OPTIONS_TRADING_AVERAGE');
										countClom1=countClom1+parseInt(con);
										var f1=isAllEmps(cust_form3,'EXCHANGE_FORWARD_AVERAGE','EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_INTEREST_AVERAGE','OPTIONS_TRADING_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom1=countClom1-1;
											}
										}
										if(f1==true){
										 countClom1=countClom1-1;
										}
										 cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'EXCHANGE_FORWARD_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE','EXCHANGE_FORWARD_MARGIN','EXCHANGE_FORWARD_PROPORTION','wrmb31','wrmb51','zwrmb05');
									}
								}
				   },{
				    name : 'wrmb31',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'EXCHANGE_FORWARD_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE','EXCHANGE_FORWARD_MARGIN','EXCHANGE_FORWARD_PROPORTION','wrmb31','wrmb51','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'EXCHANGE_FORWARD_PROPORTION','EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_INTEREST_PROPORTION','OPTIONS_TRADING_PROPORTION');
										countClom4=countClom4+parseInt(con);
										var f1=isAllEmps(cust_form3,'EXCHANGE_FORWARD_PROPORTION','EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_INTEREST_PROPORTION','OPTIONS_TRADING_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom4=countClom4-1;
											}
										}
										if(f1==true){
										  countClom4=countClom4-1;
										}
										 cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'wrmb51',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
					html:"<div style='height:21px;width:234px;line-height:21px;border:1px solid #a6b1be;'>利率互换量:</div>"
				   },{
				    name : 'EXCHANGE_INTEREST_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE','EXCHANGE_INTEREST_MARGIN','EXCHANGE_INTEREST_PROPORTION','wrmb32','wrmb52','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'EXCHANGE_INTEREST_AVERAGE','EXCHANGE_FORWARD_AVERAGE','EXCHANGE_IMMEDIATE_AVERAGE','OPTIONS_TRADING_AVERAGE');
										countClom1=countClom1+parseInt(con);
										var f1=isAllEmps(cust_form3,'EXCHANGE_INTEREST_AVERAGE','EXCHANGE_FORWARD_AVERAGE','EXCHANGE_IMMEDIATE_AVERAGE','OPTIONS_TRADING_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom1=countClom1-1;
											}
										}
										if(f1==true){
										 countClom1=countClom1-1;
										}
										 cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'EXCHANGE_INTEREST_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE','EXCHANGE_INTEREST_MARGIN','EXCHANGE_INTEREST_PROPORTION','wrmb32','wrmb52','zwrmb05');
									}
								}
				   },{
				    name : 'wrmb32',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'EXCHANGE_INTEREST_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE','EXCHANGE_INTEREST_MARGIN','EXCHANGE_INTEREST_PROPORTION','wrmb32','wrmb52','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'EXCHANGE_INTEREST_PROPORTION','EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_FORWARD_PROPORTION','OPTIONS_TRADING_PROPORTION');
										countClom4=countClom4+parseInt(con);
										var f1=isAllEmps(cust_form3,'EXCHANGE_INTEREST_PROPORTION','EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_FORWARD_PROPORTION','OPTIONS_TRADING_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom4=countClom4-1;
											}
										}
										if(f1==true){
										  countClom4=countClom4-1;
										}
										 cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'wrmb52',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
					html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>期权交易量:</div>"
				   },{
				    name : 'OPTIONS_TRADING_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('OPTIONS_TRADING_AVERAGE','OPTIONS_TRADING_MARGIN','OPTIONS_TRADING_PROPORTION','wrmb33','wrmb53','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'OPTIONS_TRADING_AVERAGE','EXCHANGE_FORWARD_AVERAGE','EXCHANGE_INTEREST_AVERAGE','EXCHANGE_IMMEDIATE_AVERAGE');
										countClom1=countClom1+parseInt(con);
										var f1=isAllEmps(cust_form3,'OPTIONS_TRADING_AVERAGE','EXCHANGE_FORWARD_AVERAGE','EXCHANGE_INTEREST_AVERAGE','EXCHANGE_IMMEDIATE_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom1=countClom1-1;
											}
										}
										if(f1==true){
										 countClom1=countClom1-1;
										}
										 cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'OPTIONS_TRADING_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('OPTIONS_TRADING_AVERAGE','OPTIONS_TRADING_MARGIN','OPTIONS_TRADING_PROPORTION','wrmb33','wrmb53','zwrmb05');
									}
								}
				   },{
				    name : 'wrmb33',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'OPTIONS_TRADING_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										calrmbx0y0Fun('OPTIONS_TRADING_AVERAGE','OPTIONS_TRADING_MARGIN','OPTIONS_TRADING_PROPORTION','wrmb33','wrmb53','zwrmb05');
									},
									change:function(field,newValue,oldValue){
										var con=countnumber(cust_form3,'OPTIONS_TRADING_PROPORTION','EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_INTEREST_PROPORTION','EXCHANGE_FORWARD_PROPORTION');
										countClom4=countClom4+parseInt(con);
										var f1=isAllEmps(cust_form3,'OPTIONS_TRADING_PROPORTION','EXCHANGE_IMMEDIATE_PROPORTION','EXCHANGE_INTEREST_PROPORTION','EXCHANGE_FORWARD_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											if(parseInt(con)==1){
											countClom4=countClom4-1;
											}
										}
										if(f1==true){
										  countClom4=countClom4-1;
										}
										 cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'wrmb53',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
					colspan:2,
                    xtype: "label",
                    html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>总收益:</div>"						
				   },{
				    name : 'zwrmb01',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					readOnly:true,
					cls:'x-readOnly',
					blankText : '该项为必填项',
				    anchor : '95%'
				   },{
				    name : 'zwrmb02',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%'
				   },{
				    name : 'zwrmb03',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'zwrmb04',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					readOnly:true,
					cls:'x-readOnly',
					blankText : '该项为必填项',
				    anchor : '95%'
				   },{
				    name : 'zwrmb05',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },
				  {
				   colspan:2,
				   xtype: "label",
                   html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>产品(根据客户PBOC征信记录填写)</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>均量/年</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>客户支付成本（%）</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>银行收益（￥）/年</div>"				   
				 },
				  {
				    name : 'whsy00',
					xtype : 'textfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					readOnly:true,
					cls:'x-readOnly',
					blankText : '该项为必填项',
				    anchor : '95%'   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行收益（￥）/年</div>"				   
				 },{
				   rowspan:6,
				   xtype: "label",
                   html:"<div style='height:130px;width:111px;line-height:130px;border:1px solid #a6b1be;text-align:center'>贸易类</div>"				   
				 },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>贸易融资（TR-loan、进出口押汇业务）:</div>"					
				   },{
				    name : 'TRADE_FINANCING_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE','TRADE_FINANCING_MARGIN','TRADE_FINANCING_PROPORTION','tradefinance30','tradefinance50','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum(cust_form3,'TRADE_FINANCING_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											countClom1=countClom1-1;
										}
										  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'TRADE_FINANCING_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE','TRADE_FINANCING_MARGIN','TRADE_FINANCING_PROPORTION','tradefinance30','tradefinance50','ztradefinance05');
									}
								}
				   },{
				    name : 'tradefinance30',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_FINANCING_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE','TRADE_FINANCING_MARGIN','TRADE_FINANCING_PROPORTION','tradefinance30','tradefinance50','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum2(cust_form3,'TRADE_FINANCING_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											countClom4=countClom4-1;
										}
										  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
										
									}
								}
				   },{
				    name : 'tradefinance50',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>保理(纯保理):</div>"					
				   },{
				    name : 'TRADE_FACTORING_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE','TRADE_FACTORING_MARGIN','TRADE_FACTORING_PROPORTION','tradefinance31','tradefinance51','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum(cust_form3,'TRADE_FACTORING_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											countClom1=countClom1-1;
										}
										  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
										
									}
								}
				   },{
				    name : 'TRADE_FACTORING_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE','TRADE_FACTORING_MARGIN','TRADE_FACTORING_PROPORTION','tradefinance31','tradefinance51','ztradefinance05');
									}
								}
				   },{
				    name : 'tradefinance31',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_FACTORING_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE','TRADE_FACTORING_MARGIN','TRADE_FACTORING_PROPORTION','tradefinance31','tradefinance51','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum2(cust_form3,'TRADE_FACTORING_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											countClom4=countClom4-1;
										}
										  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'tradefinance51',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>票据贴现(商票保贴、商票贴现、银票贴现)</div>"						
				   },{
				    name : 'TRADE_DISCOUNT_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE','TRADE_DISCOUNT_MARGIN','TRADE_DISCOUNT_PROPORTION','tradefinance32','tradefinance52','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum(cust_form3,'TRADE_DISCOUNT_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											countClom1=countClom1-1;
										}
										  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'TRADE_DISCOUNT_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE','TRADE_DISCOUNT_MARGIN','TRADE_DISCOUNT_PROPORTION','tradefinance32','tradefinance52','ztradefinance05');
									}
								}
				   },{
				    name : 'tradefinance32',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_DISCOUNT_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE','TRADE_DISCOUNT_MARGIN','TRADE_DISCOUNT_PROPORTION','tradefinance32','tradefinance52','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum2(cust_form3,'TRADE_DISCOUNT_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											countClom4=countClom4-1;
										}
										  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'tradefinance52',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>银行承兑汇票（开立银票）:</div>"					
				   },{
				    name : 'TRADE_ACCEPTANCE_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE','TRADE_ACCEPTANCE_MARGIN','TRADE_ACCEPTANCE_PROPORTION','tradefinance33','tradefinance53','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum(cust_form3,'TRADE_ACCEPTANCE_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											countClom1=countClom1-1;
										}
										  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'TRADE_ACCEPTANCE_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE','TRADE_ACCEPTANCE_MARGIN','TRADE_ACCEPTANCE_PROPORTION','tradefinance33','tradefinance53','ztradefinance05');
									}
								}
				   },{
				    name : 'tradefinance33',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_ACCEPTANCE_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE','TRADE_ACCEPTANCE_MARGIN','TRADE_ACCEPTANCE_PROPORTION','tradefinance33','tradefinance53','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum2(cust_form3,'TRADE_ACCEPTANCE_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											countClom4=countClom4-1;
										}
										  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'tradefinance53',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>信用证（开立信用证）:</div>"					
				   },{
				    name : 'TRADE_CREDIT_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE','TRADE_CREDIT_MARGIN','TRADE_CREDIT_PROPORTION','tradefinance34','tradefinance54','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum(cust_form3,'TRADE_CREDIT_AVERAGE');
										if(newValue!=oldValue&&oldValue!=''){
											countClom1=countClom1-1;
										}
										  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
									}
								}
				   },{
				    name : 'TRADE_CREDIT_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
						listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE','TRADE_CREDIT_MARGIN','TRADE_CREDIT_PROPORTION','tradefinance34','tradefinance54','ztradefinance05');
									}
								}
				   },{
				    name : 'tradefinance34',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_CREDIT_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
									blur : function() {
										caltradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE','TRADE_CREDIT_MARGIN','TRADE_CREDIT_PROPORTION','tradefinance34','tradefinance54','ztradefinance05');
									},
									change:function(field,newValue,oldValue){
										listenCountNum2(cust_form3,'TRADE_CREDIT_PROPORTION');
										if(newValue!=oldValue&&oldValue!=''){
											countClom4=countClom4-1;
										}
										  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
									}
								}
				   },{
				    name : 'tradefinance54',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>保函（开立保函）:</div>"					
				   },{
				    name : 'TRADE_GUARANTEE_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						 caltradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE','TRADE_GUARANTEE_MARGIN','TRADE_GUARANTEE_PROPORTION','tradefinance35','tradefinance55','ztradefinance05');
							},
					change:function(field,newValue,oldValue){
						listenCountNum(cust_form3,'TRADE_GUARANTEE_AVERAGE');
						if(newValue!=oldValue&&oldValue!=''){
							countClom1=countClom1-1;
						}
						  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
					}
					}
				   },{
				    name : 'TRADE_GUARANTEE_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						caltradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE','TRADE_GUARANTEE_MARGIN','TRADE_GUARANTEE_PROPORTION','tradefinance35','tradefinance55','ztradefinance05');
							}
						}
				   },{
				    name : 'tradefinance35',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_GUARANTEE_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						caltradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE','TRADE_GUARANTEE_MARGIN','TRADE_GUARANTEE_PROPORTION','tradefinance35','tradefinance55','ztradefinance05');
							},
					change:function(field,newValue,oldValue){
							listenCountNum2(cust_form3,'TRADE_GUARANTEE_PROPORTION');
							if(newValue!=oldValue&&oldValue!=''){
								countClom4=countClom4-1;
							}
							  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
							}
						}
				   },{
				    name : 'tradefinance55',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:21px;width:111px;line-height:21px;border:1px solid #a6b1be;text-align:center'>贷款</div>"					
				   },{
                    xtype: "label",
                    html:"<div style='height:21px;width:234px;line-height:21px;border:1px solid #a6b1be;'>贷款（流贷、固贷、发票融资、AR）:</div>"					
				   },{
				    name : 'LOAN_AVERAGE',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						caltradefinancebx0y0Fun('LOAN_AVERAGE','LOAN_MARGIN','LOAN_PROPORTION','tradefinance36','tradefinance56','ztradefinance05');
							},
					change:function(field,newValue,oldValue){
						listenCountNum(cust_form3,'LOAN_AVERAGE');
						if(newValue!=oldValue&&oldValue!=''){
							countClom1=countClom1-1;
						}
						  cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').setValue(countClom1);
				      }
					}
				   },{
				    name : 'LOAN_MARGIN',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						caltradefinancebx0y0Fun('LOAN_AVERAGE','LOAN_MARGIN','LOAN_PROPORTION','tradefinance36','tradefinance56','ztradefinance05');
							}
						}
				   },{
				    name : 'tradefinance36',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'LOAN_PROPORTION',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						caltradefinancebx0y0Fun('LOAN_AVERAGE','LOAN_MARGIN','LOAN_PROPORTION','tradefinance36','tradefinance56','ztradefinance05');
							},
					  change:function(field,newValue,oldValue){
							listenCountNum2(cust_form3,'LOAN_PROPORTION');
							if(newValue!=oldValue&&oldValue!=''){
								countClom4=countClom4-1;
							}
							  cust_form3.getForm().findField('PROVIDE_PRODUCTS').setValue(countClom4);
							}
						}
				   },{
				    name : 'tradefinance56',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
					  colspan:2,
                      xtype: "label",
				     html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>总计</div>"	
				   },{
				    name : 'ztradefinance01',
					xtype : 'numberfield',
					maxLength : 10,
					readOnly:true,
					cls:'x-readOnly',
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'ztradefinance02',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
					readOnly:true,
					cls:'x-readOnly',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'ztradefinance03',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'ztradefinance04',
					xtype : 'numberfield',
					maxLength : 10,
					readOnly:true,
					cls:'x-readOnly',
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'ztradefinance05',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",	
					html:"<div style='height:20px;width:111px;line-height:20px;border:1px solid #a6b1be;'>Products使用数小计</div>"
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>Wallet size产品使用总数:</div>"					
				   },{
				    name : 'WALLETSIZE_PRODUCTS',
					xtype : 'numberfield',
					maxLength : 10,
					readOnly:true,
					cls:'x-readOnly',
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				     name : 'tradefinance12r',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				   
				   },{
				    xtype: "label",
					html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行目前提供产品总数</div>"
				   },{
				    name : 'PROVIDE_PRODUCTS',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
					colspan:2,
                    xtype: "label",
                    html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>客户适合可以使用产品</div>"					
				   },{
				    name : 'SUIT_PRODUCTS',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
					  blur : function() {
						  cust_form4.getForm().findField('SUIT_PRODUCTS_NY').setValue(cust_form3.getForm().findField('SUIT_PRODUCTS').getValue());
						 caltradefinancebx0y0Fun('LOAN_AVERAGE','LOAN_MARGIN','LOAN_PROPORTION','tradefinance36','tradefinance56','ztradefinance05');
						   var mpercentagePro1=0;
							if(cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue()!=''&&cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue()!=0){
							  mpercentagePro1=(cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').getValue()/cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue()).toFixed(2)*100;
							}else{
								mpercentagePro1=0;
							}
							cust_form4.getForm().findField('mproductspie').setValue(mpercentagePro1.toFixed(3));
							saveUser_ajaxSubmit1();
							saveUser_ajaxSubmit4();
						}
					}
				   },{
                     xtype: "label",
					 html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>Wallet size 产品使用比例%</div>"
				   },{
				    name : 'tradefinancepie33',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    xtype: "label",
					html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行目前提供产品使用比例%</div>"
				   },{
				    name : 'productspie',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   }, {xtype:'hidden',fieldLabel:'ID',name:'ID'}, {xtype:'hidden',fieldLabel:'客户ID',name:'CUST_ID'},
				   {xtype:'hidden',fieldLabel:'PLAN_ID',name:'PLAN_ID'}
	           ]
			
});


//预估明年Wallet Size
var cust_form4 = new Ext.form.FormPanel({
    title:'预估明年Wallet Size',
     autoWidth: true,
	 autoHeight: true,
    collapsible : true,
	autoScroll: true,
	padding : '10px 0px 0px',
	layout : 'table', // 整个大的表单是table布局,从上往下
	labelAlign : 'right',
	layoutConfig:{columns:7},//将父容器分成7列
	defaults : {  
         layout:'form',
         width:200
      }, 
	items : [  
	           {     
				 colspan:5,
				 xtype: "label",
				 html:"<div style='height:20px;width:947px;line-height:20px;border:1px solid #a6b1be;text-align:center'>预估明年Wallet Size</div>"
						  
				 },
				 {
				  colspan:2,
				  xtype: "label",	
                  html:"<div style='height:20px;width:398px;line-height:20px;border:1px solid #a6b1be;text-align:center'>预估我行明年目标</div>"				  
				 },
				  {
				   colspan:2,
				   xtype: "label",
                   html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>产品</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>均量/年(RMB'000)</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>Margin/Spread（%）</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>银行收益（RMB千元）/年</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行明年目标占比%</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行明年目标收益（RMB千元）/年</div>"				   
				 },{
				   rowspan:3,
				   xtype: "label",
				   html:"<div style='height:64px;width:111px;line-height:64px;border:1px solid #a6b1be;text-align:center'>存款类</div>"	
				 },{
                    xtype: "label",
					html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>折合人民币():&nbsp;</div>"		
				   },{
				    name : 'DEPOSIT_RMB_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0y0Fun('DEPOSIT_RMB_AVERAGE_NY','DEPOSIT_RMB_MARGIN_NY','DEPOSIT_RMB_PROPORT_NY','mrmb30','mrmb50','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'DEPOSIT_RMB_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'DEPOSIT_RMB_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0y0Fun('DEPOSIT_RMB_AVERAGE_NY','DEPOSIT_RMB_MARGIN_NY','DEPOSIT_RMB_PROPORT_NY','mrmb30','mrmb50','mzwrmb05');
						}
					}
				   },{
				    name : 'mrmb30',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'DEPOSIT_RMB_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0y0Fun('DEPOSIT_RMB_AVERAGE_NY','DEPOSIT_RMB_MARGIN_NY','DEPOSIT_RMB_PROPORT_NY','mrmb30','mrmb50','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'DEPOSIT_RMB_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mrmb50',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
					html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>交易手续费:</div>"
				   },{
				    name : 'DEPOSIT_TRADE_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0Fun('DEPOSIT_TRADE_AVERAGE_NY','DEPOSIT_TRADE_PROPORT_NY','mrmb31','mrmb51','mzwrmb05');
						}
					}
				   },{
				    name : 'DEPOSIT_TRADE_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					readOnly:true,
					cls:'x-readOnly',
					blankText : '该项为必填项',
				    anchor : '95%'
				   },{
				    name : 'mrmb31',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'DEPOSIT_TRADE_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0Fun('DEPOSIT_TRADE_AVERAGE_NY','DEPOSIT_TRADE_PROPORT_NY','mrmb31','mrmb51','mzwrmb05');
						}
					}
				   },{
				    name : 'mrmb51',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>其他:</div>"					
				   },{
				    name : 'DEPOSIT_OTHER_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0Fun('DEPOSIT_OTHER_AVERAGE_NY','DEPOSIT_OTHER_PROPORT_NY','mrmb32','mrmb52','mzwrmb05');
						}
					}
				   },{
				    name : 'DEPOSIT_OTHER_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
					readOnly:true,
					cls:'x-readOnly',
				    anchor : '95%'
				   },{
				    name : 'mrmb32',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'DEPOSIT_OTHER_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomrmbx0Fun('DEPOSIT_OTHER_AVERAGE_NY','DEPOSIT_OTHER_PROPORT_NY','mrmb32','mrmb52','mzwrmb05');
						}
					}
				   },{
				    name : 'mrmb52',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				   rowspan:4,
				   xtype: "label",
                   html:"<div style='height:88px;width:111px;line-height:88px;border:1px solid #a6b1be;text-align:center'>外汇类</div>"					   
				 },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>即期交易量（CB）:</div>"					
				   },{
				    name : 'EXCHANGE_IMMEDIATE_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_IMMEDIATE_MARGIN_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','mwrmb30','mwrmb50','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_INTEREST_AVERAGE_NY','OPTIONS_TRADING_AVERAGE_NY');
								countClom42=countClom42+parseInt(con);
							var f1=isAllEmps(cust_form4,'EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_INTEREST_AVERAGE_NY','OPTIONS_TRADING_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom42=countClom42-1;
								}
							}
							if(f1==true){
							 countClom42=countClom42-1;
							}
							 cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'EXCHANGE_IMMEDIATE_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_IMMEDIATE_MARGIN_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','mwrmb30','mwrmb50','mzwrmb05');
						}
					}
				   },{
				    name : 'mwrmb30',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'EXCHANGE_IMMEDIATE_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_IMMEDIATE_MARGIN_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','mwrmb30','mwrmb50','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_FORWARD_PROPORT_NY','EXCHANGE_INTEREST_PROPORT_NY','OPTIONS_TRADING_PROPORT_NY');
							countClom41=countClom41+parseInt(con);
							var f1=isAllEmps(cust_form4,'EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_FORWARD_PROPORT_NY','EXCHANGE_INTEREST_PROPORT_NY','OPTIONS_TRADING_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom41=countClom41-1;
								}
							}
							if(f1==true){
							  countClom41=countClom41-1;
							}
							 cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mwrmb50',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:21px;width:234px;line-height:21px;border:1px solid #a6b1be;'>远期交易量（CB）:</div>"						
				   },{
				    name : 'EXCHANGE_FORWARD_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_FORWARD_MARGIN_NY','EXCHANGE_FORWARD_PROPORT_NY','mwrmb31','mwrmb51','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_INTEREST_AVERAGE_NY','OPTIONS_TRADING_AVERAGE_NY');
								countClom42=countClom42+parseInt(con);
							var f1=isAllEmps(cust_form4,'EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_INTEREST_AVERAGE_NY','OPTIONS_TRADING_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom42=countClom42-1;
								}
							}
							if(f1==true){
							 countClom42=countClom42-1;
							}
							 cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'EXCHANGE_FORWARD_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_FORWARD_MARGIN_NY','EXCHANGE_FORWARD_PROPORT_NY','mwrmb31','mwrmb51','mzwrmb05');
						}
					}
				   },{
				    name : 'mwrmb31',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'EXCHANGE_FORWARD_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_FORWARD_MARGIN_NY','EXCHANGE_FORWARD_PROPORT_NY','mwrmb31','mwrmb51','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'EXCHANGE_FORWARD_PROPORT_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_INTEREST_PROPORT_NY','OPTIONS_TRADING_PROPORT_NY');
							countClom41=countClom41+parseInt(con);
							var f1=isAllEmps(cust_form4,'EXCHANGE_FORWARD_PROPORT_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_INTEREST_PROPORT_NY','OPTIONS_TRADING_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom41=countClom41-1;
								}
							}
							if(f1==true){
							  countClom41=countClom41-1;
							}
							 cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mwrmb51',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
					html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>利率互换量:</div>"
				   },{
				    name : 'EXCHANGE_INTEREST_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE_NY','EXCHANGE_INTEREST_MARGIN_NY','EXCHANGE_INTEREST_PROPORT_NY','mwrmb32','mwrmb52','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'EXCHANGE_INTEREST_AVERAGE_NY','EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_FORWARD_AVERAGE_NY','OPTIONS_TRADING_AVERAGE_NY');
								countClom42=countClom42+parseInt(con);
							var f1=isAllEmps(cust_form4,'EXCHANGE_INTEREST_AVERAGE_NY','EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_FORWARD_AVERAGE_NY','OPTIONS_TRADING_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom42=countClom42-1;
								}
							}
							if(f1==true){
							 countClom42=countClom42-1;
							}
							 cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'EXCHANGE_INTEREST_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE_NY','EXCHANGE_INTEREST_MARGIN_NY','EXCHANGE_INTEREST_PROPORT_NY','mwrmb32','mwrmb52','mzwrmb05');
						}
					}
				   },{
				    name : 'mwrmb32',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
				   },{
				    name : 'EXCHANGE_INTEREST_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE_NY','EXCHANGE_INTEREST_MARGIN_NY','EXCHANGE_INTEREST_PROPORT_NY','mwrmb32','mwrmb52','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'EXCHANGE_INTEREST_PROPORT_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_FORWARD_PROPORT_NY','OPTIONS_TRADING_PROPORT_NY');
							countClom41=countClom41+parseInt(con);
							var f1=isAllEmps(cust_form4,'EXCHANGE_INTEREST_PROPORT_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_FORWARD_PROPORT_NY','OPTIONS_TRADING_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom41=countClom41-1;
								}
							}
							if(f1==true){
							  countClom41=countClom41-1;
							}
							 cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mwrmb52',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
					html:"<div style='height:21px;width:234px;line-height:21px;border:1px solid #a6b1be;'>期权交易量:</div>"
				   },{
				    name : 'OPTIONS_TRADING_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('OPTIONS_TRADING_AVERAGE_NY','OPTIONS_TRADING_MARGIN_NY','OPTIONS_TRADING_PROPORT_NY','mwrmb33','mwrmb53','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'OPTIONS_TRADING_AVERAGE_NY','EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_INTEREST_AVERAGE_NY');
								countClom42=countClom42+parseInt(con);
							var f1=isAllEmps(cust_form4,'OPTIONS_TRADING_AVERAGE_NY','EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_INTEREST_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								countClom42=countClom42-1;
								}
							}
							if(f1==true){
							 countClom42=countClom42-1;
							}
							 cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'OPTIONS_TRADING_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('OPTIONS_TRADING_AVERAGE_NY','OPTIONS_TRADING_MARGIN_NY','OPTIONS_TRADING_PROPORT_NY','mwrmb33','mwrmb53','mzwrmb05');
						}
					}
				   },{
				    name : 'mwrmb33',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'OPTIONS_TRADING_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
						  calTomrmbx0y0Fun('OPTIONS_TRADING_AVERAGE_NY','OPTIONS_TRADING_MARGIN_NY','OPTIONS_TRADING_PROPORT_NY','mwrmb33','mwrmb53','mzwrmb05');
						},
						change:function(field,newValue,oldValue){
							var con=countnumber(cust_form4,'OPTIONS_TRADING_PROPORT_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_FORWARD_PROPORT_NY','EXCHANGE_INTEREST_PROPORT_NY');
							countClom41=countClom41+parseInt(con);
							var f1=isAllEmps(cust_form4,'OPTIONS_TRADING_PROPORT_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','EXCHANGE_FORWARD_PROPORT_NY','EXCHANGE_INTEREST_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								if(parseInt(con)==1){
								 countClom41=countClom41-1;
								}
							}
							if(f1==true){
							  countClom41=countClom41-1;
							}
							 cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
						
					}
				   },{
				    name : 'mwrmb53',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
					colspan:2,
                    xtype: "label",
                    html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>总收益:</div>"						
				   },{
				    name : 'mzwrmb01',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mzwrmb02',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mzwrmb03',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mzwrmb04',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mzwrmb05',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },
				  {
				   colspan:2,
				   xtype: "label",
                   html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>产品</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>均量/年</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>客户支付成本（%）</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>银行收益（￥）/年</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行明年目标占比%</div>"				   
				 },
				  {
				   xtype: "label",
                   html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行明年目标收益（RMB千元）/年</div>"				   
				 },{
				   rowspan:6,
				   xtype: "label",
                   html:"<div style='height:130px;width:111px;line-height:130px;border:1px solid #a6b1be;text-align:center'>贸易类</div>"				   
				 },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>贸易融资（TR-loan、进出口押汇业务）:</div>"					
				   },{
				    name : 'TRADE_FINANCING_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE_NY','TRADE_FINANCING_MARGIN_NY','TRADE_FINANCING_PROPORT_NY','mtradefinance30','mtradefinance50','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'TRADE_FINANCING_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'TRADE_FINANCING_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE_NY','TRADE_FINANCING_MARGIN_NY','TRADE_FINANCING_PROPORT_NY','mtradefinance30','mtradefinance50','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance30',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_FINANCING_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE_NY','TRADE_FINANCING_MARGIN_NY','TRADE_FINANCING_PROPORT_NY','mtradefinance30','mtradefinance50','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'TRADE_FINANCING_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance50',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>保理(纯保理):</div>"					
				   },{
				    name : 'TRADE_FACTORING_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE_NY','TRADE_FACTORING_MARGIN_NY','TRADE_FACTORING_PROPORT_NY','mtradefinance31','mtradefinance51','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'TRADE_FACTORING_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'TRADE_FACTORING_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE_NY','TRADE_FACTORING_MARGIN_NY','TRADE_FACTORING_PROPORT_NY','mtradefinance31','mtradefinance51','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance31',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_FACTORING_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE_NY','TRADE_FACTORING_MARGIN_NY','TRADE_FACTORING_PROPORT_NY','mtradefinance31','mtradefinance51','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'TRADE_FACTORING_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance51',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>票据贴现(商票保贴、商票贴现、银票贴现)</div>"						
				   },{
				    name : 'TRADE_DISCOUNT_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE_NY','TRADE_DISCOUNT_MARGIN_NY','TRADE_DISCOUNT_PROPORT_NY','mtradefinance32','mtradefinance52','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'TRADE_DISCOUNT_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'TRADE_DISCOUNT_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE_NY','TRADE_DISCOUNT_MARGIN_NY','TRADE_DISCOUNT_PROPORT_NY','mtradefinance32','mtradefinance52','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance32',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_DISCOUNT_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE_NY','TRADE_DISCOUNT_MARGIN_NY','TRADE_DISCOUNT_PROPORT_NY','mtradefinance32','mtradefinance52','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'TRADE_DISCOUNT_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance52',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>银行承兑汇票（开立银票）:</div>"					
				   },{
				    name : 'TRADE_ACCEPTANCE_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE_NY','TRADE_ACCEPTANCE_MARGIN_NY','TRADE_ACCEPTANCE_PROPORT_NY','mtradefinance33','mtradefinance53','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'TRADE_ACCEPTANCE_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'TRADE_ACCEPTANCE_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE_NY','TRADE_ACCEPTANCE_MARGIN_NY','TRADE_ACCEPTANCE_PROPORT_NY','mtradefinance33','mtradefinance53','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance33',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_ACCEPTANCE_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE_NY','TRADE_ACCEPTANCE_MARGIN_NY','TRADE_ACCEPTANCE_PROPORT_NY','mtradefinance33','mtradefinance53','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'TRADE_ACCEPTANCE_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance53',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>信用证（开立信用证）:</div>"					
				   },{
				    name : 'TRADE_CREDIT_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE_NY','TRADE_CREDIT_MARGIN_NY','TRADE_CREDIT_PROPORT_NY','mtradefinance34','mtradefinance54','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'TRADE_CREDIT_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name :'TRADE_CREDIT_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE_NY','TRADE_CREDIT_MARGIN_NY','TRADE_CREDIT_PROPORT_NY','mtradefinance34','mtradefinance54','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance34',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_CREDIT_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE_NY','TRADE_CREDIT_MARGIN_NY','TRADE_CREDIT_PROPORT_NY','mtradefinance34','mtradefinance54','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'TRADE_CREDIT_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance54',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>保函（开立保函）:</div>"					
				   },{
				    name : 'TRADE_GUARANTEE_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE_NY','TRADE_GUARANTEE_MARGIN_NY','TRADE_GUARANTEE_PROPORT_NY','mtradefinance35','mtradefinance55','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'TRADE_GUARANTEE_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'TRADE_GUARANTEE_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE_NY','TRADE_GUARANTEE_MARGIN_NY','TRADE_GUARANTEE_PROPORT_NY','mtradefinance35','mtradefinance55','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance35',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'TRADE_GUARANTEE_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE_NY','TRADE_GUARANTEE_MARGIN_NY','TRADE_GUARANTEE_PROPORT_NY','mtradefinance35','mtradefinance55','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'TRADE_GUARANTEE_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance55',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:21px;width:111px;line-height:21px;border:1px solid #a6b1be;text-align:center'>贷款</div>"					
				   },{
                    xtype: "label",
                    html:"<div style='height:21px;width:234px;line-height:21px;border:1px solid #a6b1be;'>贷款（流贷、固贷、发票融资、AR）:</div>"					
				   },{
				    name : 'LOAN_AVERAGE_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('LOAN_AVERAGE_NY','LOAN_MARGIN_NY','LOAN_PROPORT_NY','mtradefinance36','mtradefinance56','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum4(cust_form4,'LOAN_AVERAGE_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom42=countClom42-1;
							}
							  cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').setValue(countClom42);
						}
					}
				   },{
				    name : 'LOAN_MARGIN_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('LOAN_AVERAGE_NY','LOAN_MARGIN_NY','LOAN_PROPORT_NY','mtradefinance36','mtradefinance56','mztradefinance05');
						}
					}
				   },{
				    name : 'mtradefinance36',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'LOAN_PROPORT_NY',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false,
					listeners : {
						blur : function() {
							calTomtradefinancebx0y0Fun('LOAN_AVERAGE_NY','LOAN_MARGIN_NY','LOAN_PROPORT_NY','mtradefinance36','mtradefinance56','mztradefinance05');
						},
						change:function(field,newValue,oldValue){
							listenCountNum3(cust_form4,'LOAN_PROPORT_NY');
							if(newValue!=oldValue&&oldValue!=''){
								countClom41=countClom41-1;
							}
							  cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').setValue(countClom41);
						}
					}
				   },{
				    name : 'mtradefinance56',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
					  colspan:2,
                      xtype: "label",
				     html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>总计</div>"	
				   },{
				    name : 'mztradefinance01',
					xtype : 'numberfield',
					maxLength : 10,
					readOnly:true,
					cls:'x-readOnly',
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mztradefinance02',
					xtype : 'numberfield',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
					readOnly:true,
					cls:'x-readOnly',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mztradefinance03',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mztradefinance04',
					xtype : 'numberfield',
					maxLength : 10,
					readOnly:true,
					cls:'x-readOnly',
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				    name : 'mztradefinance05',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:111px;line-height:20px;border:1px solid #a6b1be;'>Products使用数小计</div>"					
				   },{
                    xtype: "label",
                    html:"<div style='height:20px;width:234px;line-height:20px;border:1px solid #a6b1be;'>客户明年预估Wallet size产品使用总数</div>"						
				   },{
				    name : 'WALLETSIZE_PRODUCTS_NY',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				   
				   },{
				   
				   },{
				    xtype: "label",
					html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>明年目标使用产品总数</div>"
				   },{
				    name : 'PROVIDE_PRODUCTS_NY',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
					colspan:2,
                    xtype: "label",
                    html:"<div style='height:20px;width:347px;line-height:20px;border:1px solid #a6b1be;text-align:center'>客户适合可以使用产品</div>"					
				   },{
				    name : 'SUIT_PRODUCTS_NY',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   },{
				   
				   },{
				   
				   },{
				    xtype: "label",
					html:"<div style='height:20px;width:198px;line-height:20px;border:1px solid #a6b1be;text-align:center'>我行明年目标提供产品使用比例%</div>"
				   },{
				    name : 'mproductspie',
					xtype : 'numberfield',
					readOnly:true,
					cls:'x-readOnly',
					maxLength : 10,
					maxLengthText : '最大长度为10',
					blankText : '该项为必填项',
				    anchor : '95%',
					allowBlank : false
				   } ,{xtype:'hidden',fieldLabel:'ID',name:'ID'},{xtype:'hidden',fieldLabel:'客户ID',name:'CUST_ID'},
				   {xtype:'hidden',fieldLabel:'PLAN_ID',name:'PLAN_ID'}
				  
	        ]
			
      
});

/**
 * 结果域面板滑入前触发,系统提供listener事件方法 注意：各组写在一个beforeviewshow下面
 * 
 * @param {}
 *          view
 * @return {Boolean}
 */
var beforeviewshow = function(view) {
	if (view._defaultTitle == '客户关系计划表') {
		cust_form.getForm().findField('CUSTT').bindStore(findLookupByType('CUST_TYPE'));
		
	} 
};


//根据名字获取组件
var getAtributebyName=function(formP,name){
	return formP.getForm().findField(name);
}
//计算总收益
var sumAllrmb=function(formP,xrmb30,xrmb31,xrmb32,xxrmb30,xxrmb31,xxrmb32,xxrmb33,zwrmb03,
xrmb50,xrmb51,xrmb52,xxrmb50,xxrmb51,xxrmb52,xxrmb53,zwrmb05){
	var rmb30=getAtributebyName(formP,xrmb30).getValue();
	var rmb31=getAtributebyName(formP,xrmb31).getValue();
	var rmb32=getAtributebyName(formP,xrmb32).getValue();
	var wrmb30=getAtributebyName(formP,xxrmb30).getValue();
	var wrmb31=getAtributebyName(formP,xxrmb31).getValue();
	var wrmb32=getAtributebyName(formP,xxrmb32).getValue();
	var wrmb33=getAtributebyName(formP,xxrmb33).getValue();
	var sumAll=Number(rmb30)+Number(rmb31)+Number(rmb32)+Number(wrmb30)+Number(wrmb31)+Number(wrmb32)+Number(wrmb33);
    formP.getForm().findField(zwrmb03).setValue(sumAll.toFixed(4));	
	var rmb50=getAtributebyName(formP,xrmb50).getValue();
	var rmb51=getAtributebyName(formP,xrmb51).getValue();
	var rmb52=getAtributebyName(formP,xrmb52).getValue();
	var wrmb50=getAtributebyName(formP,xxrmb50).getValue();
	var wrmb51=getAtributebyName(formP,xxrmb51).getValue();
	var wrmb52=getAtributebyName(formP,xxrmb52).getValue();
	var wrmb53=getAtributebyName(formP,xxrmb53).getValue();
	var sumAll1=Number(rmb50)+Number(rmb51)+Number(rmb52)+Number(wrmb50)+Number(wrmb51)+Number(wrmb52)+Number(wrmb53);
    formP.getForm().findField(zwrmb05).setValue(sumAll1.toFixed(4));	
}
//Wallet size产品使用总数、提供产品总数统计---开始
//外汇类 产品计数
var countnumber=function(formP,name,name2,name3,name4){
	var countn=0;
	var v=formP.getForm().findField(name).getValue();
	var v2=formP.getForm().findField(name2).getValue();
	var v3=formP.getForm().findField(name3).getValue();
	var v4=formP.getForm().findField(name4).getValue();
	if(v!=''&&v2==''&&v3==''&&v4==''){
		countn=1;
	}else{
		countn=0;
	}
	return countn;
}
//外汇类 产品计数判断
var isAllEmps=function(formP,name,name2,name3,name4){
	var v=formP.getForm().findField(name).getValue();
	var v2=formP.getForm().findField(name2).getValue();
	var v3=formP.getForm().findField(name3).getValue();
	var v4=formP.getForm().findField(name4).getValue();
	if(v==''&&v2==''&&v3==''&&v4==''){
		return true;
	}else{
		return false;
	}
}
var listenCountNum=function(formP,name){
	var v=formP.getForm().findField(name).getValue();
	if(v!=''){
		countClom1=countClom1+1;
	}else{
		if(countClom1==0){
		 countClom1=0;
		}
	}
}
var listenCountNum2=function(formP,name){
	var v=formP.getForm().findField(name).getValue();
	if(v!=''){
		countClom4=countClom4+1;
	}else{
		if(countClom4==0){
		 countClom4=0;
		}
	}
}
var listenCountNum3=function(formP,name){
	var v=formP.getForm().findField(name).getValue();
	if(v!=''){
		countClom41=countClom41+1;
	}else{
		if(countClom41==0){
		 countClom41=0;
		}
	}
}
var listenCountNum4=function(formP,name){
	var v=formP.getForm().findField(name).getValue();
	if(v!=''){
		countClom42=countClom42+1;
	}else{
		if(countClom42==0){
		 countClom42=0;
		}
	}
}
//Wallet size产品使用总数、提供产品总数统计--------结束
//计算总计-----开始
var sumAlltradefinance=function(formP,xrmb30,xrmb31,xrmb32,xxrmb30,xxrmb31,xxrmb32,xxrmb33,zwrmb03,
xrmb50,xrmb51,xrmb52,xxrmb50,xxrmb51,xxrmb52,xxrmb53,zwrmb05,zwrmb031,zwrmb051){
	var rmb30=getAtributebyName(formP,xrmb30).getValue();
	var rmb31=getAtributebyName(formP,xrmb31).getValue();
	var rmb32=getAtributebyName(formP,xrmb32).getValue();
	var wrmb30=getAtributebyName(formP,xxrmb30).getValue();
	var wrmb31=getAtributebyName(formP,xxrmb31).getValue();
	var wrmb32=getAtributebyName(formP,xxrmb32).getValue();
	var wrmb33=getAtributebyName(formP,xxrmb33).getValue();
	var zwrmb0310=getAtributebyName(formP,zwrmb031).getValue();
	var sumAll=Number(rmb30)+Number(rmb31)+Number(rmb32)+Number(wrmb30)+Number(wrmb31)+Number(wrmb32)+Number(wrmb33)+Number(zwrmb0310);
    formP.getForm().findField(zwrmb03).setValue(sumAll.toFixed(3));	
	var rmb50=getAtributebyName(formP,xrmb50).getValue();
	var rmb51=getAtributebyName(formP,xrmb51).getValue();
	var rmb52=getAtributebyName(formP,xrmb52).getValue();
	var wrmb50=getAtributebyName(formP,xxrmb50).getValue();
	var wrmb51=getAtributebyName(formP,xxrmb51).getValue();
	var wrmb52=getAtributebyName(formP,xxrmb52).getValue();
	var wrmb53=getAtributebyName(formP,xxrmb53).getValue();
	var zwrmb0510=getAtributebyName(formP,zwrmb051).getValue();
	var sumAll1=Number(rmb50)+Number(rmb51)+Number(rmb52)+Number(wrmb50)+Number(wrmb51)+Number(wrmb52)+Number(wrmb53)+Number(zwrmb0510);
    formP.getForm().findField(zwrmb05).setValue(sumAll1.toFixed(3));	
	
	var rmb10y=getAtributebyName(formP,'DEPOSIT_RMB_AVERAGE').getValue();
	var rmb11y=getAtributebyName(formP,'DEPOSIT_TRADE_AVERAGE').getValue();
	var rmb12y=getAtributebyName(formP,'DEPOSIT_OTHER_AVERAGE').getValue();
	var wrmb10y=getAtributebyName(formP,'EXCHANGE_IMMEDIATE_AVERAGE').getValue();
	var wrmb11y=getAtributebyName(formP,'EXCHANGE_FORWARD_AVERAGE').getValue();
	var wrmb12y=getAtributebyName(formP,'EXCHANGE_INTEREST_AVERAGE').getValue();
	var wrmb13y=getAtributebyName(formP,'OPTIONS_TRADING_AVERAGE').getValue();
	
	var tradefinance10y=getAtributebyName(formP,'TRADE_FINANCING_AVERAGE').getValue();
	var tradefinance11y=getAtributebyName(formP,'TRADE_FACTORING_AVERAGE').getValue();
	var tradefinance12y=getAtributebyName(formP,'TRADE_DISCOUNT_AVERAGE').getValue();
	var tradefinance13y=getAtributebyName(formP,'TRADE_ACCEPTANCE_AVERAGE').getValue();
	var tradefinance14y=getAtributebyName(formP,'TRADE_CREDIT_AVERAGE').getValue();
	var tradefinance15y=getAtributebyName(formP,'TRADE_GUARANTEE_AVERAGE').getValue();
	var tradefinance16y=getAtributebyName(formP,'LOAN_AVERAGE').getValue();
	var  sumAllfirstclom=Number(rmb10y)+Number(rmb11y)+Number(rmb12y)+Number(wrmb10y)+Number(wrmb11y)+Number(wrmb12y)+Number(wrmb13y)+Number(tradefinance10y)
	+Number(tradefinance11y)+Number(tradefinance12y)+Number(tradefinance13y)+Number(tradefinance14y)+Number(tradefinance15y)+Number(tradefinance16y);
	formP.getForm().findField('ztradefinance01').setValue(sumAllfirstclom.toFixed(3));
	// 总计 我行目前占比%=我行目前收益/银行收益
	var ztradefinance04v=0;
	if(formP.getForm().findField('ztradefinance03').getValue()!=''&&formP.getForm().findField('ztradefinance03').getValue()!=0){
	  ztradefinance04v=(formP.getForm().findField('ztradefinance05').getValue()/formP.getForm().findField('ztradefinance03').getValue()).toFixed(2)*100;
	}else{
		ztradefinance04v=0;
	}
	formP.getForm().findField('ztradefinance04').setValue(ztradefinance04v.toFixed(3));
  //-------------结束
	
	//Wallet size 产品使用比例   =  Wallet size产品使用总数/客户适合使用产品数
	var percentagePro=0;
	if(formP.getForm().findField('SUIT_PRODUCTS').getValue()!=''&&formP.getForm().findField('SUIT_PRODUCTS').getValue()!=0){
	  percentagePro=(formP.getForm().findField('WALLETSIZE_PRODUCTS').getValue()/formP.getForm().findField('SUIT_PRODUCTS').getValue()).toFixed(2)*100;
	}else{
		percentagePro=0;
	}
	formP.getForm().findField('tradefinancepie33').setValue(percentagePro.toFixed(3));
	
	//我行目前提供产品使用比例  = 我行目前提供产品总数/客户适合使用产品数
	var percentagePro1=0;
	if(formP.getForm().findField('SUIT_PRODUCTS').getValue()!=''&&formP.getForm().findField('SUIT_PRODUCTS').getValue()!=0){
	  percentagePro1=(formP.getForm().findField('PROVIDE_PRODUCTS').getValue()/formP.getForm().findField('SUIT_PRODUCTS').getValue()).toFixed(2)*100;
	}else{
		percentagePro1=0;
	}
	formP.getForm().findField('productspie').setValue(percentagePro1.toFixed(3));
	saveUser_ajaxSubmit1();
}
//计算函数
var calculationFun=function(formP,rmbx1,rmby1,rmbz1,rmba,rmbb,xy05){
	var rmbx = formP.getForm().findField(rmbx1).getValue();// 均量/年(由用户输入)
	var rmby = formP.getForm().findField(rmby1).getValue();// Margin/Spread(由用户输入)
	var rmbz = formP.getForm().findField(rmbz1).getValue();//我行目前占比%(由用户输入)
	/*
	 * 银行收益 公式:银行收益 = 均量/年*(Margin/Spread)
	 */
	if(rmbx<0||rmby<0||rmbz<0){
		Ext.Msg.alert("提示信息","请输入正数！");
		return;
	}
	if(rmbx==0||rmby==0){
		getAtributebyName(formP,rmba).setValue(0);
	}
	if ((rmbx != 0) && (rmby != 0)) {
		var rmbnum1=rmbx*rmby*0.01;
		formP.getForm().findField(rmba).setValue(rmbnum1);
	} 
    
		/*
	     * 我行目前收益（RMB千元）/年 公式:银行收益 = 银行收益*我行目前占比
	     */ 
    	var rmbnum2=getAtributebyName(formP,rmba).getValue()*rmbz*0.01;
		getAtributebyName(formP,rmbb).setValue(Math.round(rmbnum2));
		formP.getForm().findField(xy05).setValue(getAtributebyName(formP,rmbb).getValue());
	
}
// 计算类 银行收益
var calrmbx0y0Fun = function(rmbx1,rmby1,rmbz1,rmba,rmbb,xy05) {
	//计算银行收益
	calculationFun(cust_form3,rmbx1,rmby1,rmbz1,rmba,rmbb,xy05);
	//总收益
	 sumAllrmb(cust_form3,'rmb30','rmb31','rmb32','wrmb30','wrmb31','wrmb32','wrmb33','zwrmb03','rmb50','rmb51','rmb52','wrmb50','wrmb51','wrmb52','wrmb53','zwrmb05');
	 sumAlltradefinance(cust_form3,'tradefinance30','tradefinance31','tradefinance32','tradefinance33','tradefinance34','tradefinance35','tradefinance36','ztradefinance03','tradefinance50','tradefinance51','tradefinance52','tradefinance53','tradefinance54','tradefinance55','tradefinance56','ztradefinance05','zwrmb03','zwrmb05');

}

// 计算类 银行收益
var caltradefinancebx0y0Fun = function(rmbx1,rmby1,rmbz1,rmba,rmbb,xy05) {
	//计算银行收益
	calculationFun(cust_form3,rmbx1,rmby1,rmbz1,rmba,rmbb,xy05);
	//总收益
	 sumAlltradefinance(cust_form3,'tradefinance30','tradefinance31','tradefinance32','tradefinance33','tradefinance34','tradefinance35','tradefinance36','ztradefinance03','tradefinance50','tradefinance51','tradefinance52','tradefinance53','tradefinance54','tradefinance55','tradefinance56','ztradefinance05','zwrmb03','zwrmb05');
}

//只获取均量计算
var calrmbx0Fun = function(rmbx1,rmbz1,rmba,rmbb,xy05) {
	var rmbx = cust_form3.getForm().findField(rmbx1).getValue();// 均量/年(由用户输入)
	var rmbz = cust_form3.getForm().findField(rmbz1).getValue();//我行目前占比%(由用户输入)
	/*
	 * 银行收益 公式:银行收益 = 均量/年*(Margin/Spread)
	 */
	if(rmbx<0||rmbz<0){
		Ext.Msg.alert("提示信息","请输入正数！");
		return;
	}
	if(rmbx==0){
		getAtributebyName(cust_form3,rmba).setValue(0);
	}
	if (rmbx != 0) {
		var rmbnum1=rmbx;
		cust_form3.getForm().findField(rmba).setValue(rmbnum1);
	} 
 
		/*
	     * 我行目前收益（RMB千元）/年 公式:银行收益 = 银行收益*我行目前占比
	     */ 
		var rmbnum2=getAtributebyName(cust_form3,rmba).getValue()*rmbz*0.01;
		getAtributebyName(cust_form3,rmbb).setValue(Math.round(rmbnum2));
		cust_form3.getForm().findField(xy05).setValue(getAtributebyName(cust_form3,rmbb).getValue());

	//总收益
	 sumAllrmb(cust_form3,'rmb30','rmb31','rmb32','wrmb30','wrmb31','wrmb32','wrmb33','zwrmb03','rmb50','rmb51','rmb52','wrmb50','wrmb51','wrmb52','wrmb53','zwrmb05');
	 sumAlltradefinance(cust_form3,'tradefinance30','tradefinance31','tradefinance32','tradefinance33','tradefinance34','tradefinance35','tradefinance36','ztradefinance03','tradefinance50','tradefinance51','tradefinance52','tradefinance53','tradefinance54','tradefinance55','tradefinance56','ztradefinance05','zwrmb03','zwrmb05');

}


//--------------------------------------明年-----
//计算总计
var sumAllTomtradefinance=function(formP,xrmb30,xrmb31,xrmb32,xxrmb30,xxrmb31,xxrmb32,xxrmb33,zwrmb03,
xrmb50,xrmb51,xrmb52,xxrmb50,xxrmb51,xxrmb52,xxrmb53,zwrmb05,zwrmb031,zwrmb051){
	var rmb30=getAtributebyName(formP,xrmb30).getValue();
	var rmb31=getAtributebyName(formP,xrmb31).getValue();
	var rmb32=getAtributebyName(formP,xrmb32).getValue();
	var wrmb30=getAtributebyName(formP,xxrmb30).getValue();
	var wrmb31=getAtributebyName(formP,xxrmb31).getValue();
	var wrmb32=getAtributebyName(formP,xxrmb32).getValue();
	var wrmb33=getAtributebyName(formP,xxrmb33).getValue();
	var zwrmb0310=getAtributebyName(formP,zwrmb031).getValue();
	var sumAll=Number(rmb30)+Number(rmb31)+Number(rmb32)+Number(wrmb30)+Number(wrmb31)+Number(wrmb32)+Number(wrmb33)+Number(zwrmb0310);
    formP.getForm().findField(zwrmb03).setValue(sumAll.toFixed(3));	
	var rmb50=getAtributebyName(formP,xrmb50).getValue();
	var rmb51=getAtributebyName(formP,xrmb51).getValue();
	var rmb52=getAtributebyName(formP,xrmb52).getValue();
	var wrmb50=getAtributebyName(formP,xxrmb50).getValue();
	var wrmb51=getAtributebyName(formP,xxrmb51).getValue();
	var wrmb52=getAtributebyName(formP,xxrmb52).getValue();
	var wrmb53=getAtributebyName(formP,xxrmb53).getValue();
	var zwrmb0510=getAtributebyName(formP,zwrmb051).getValue();
	var sumAll1=Number(rmb50)+Number(rmb51)+Number(rmb52)+Number(wrmb50)+Number(wrmb51)+Number(wrmb52)+Number(wrmb53)+Number(zwrmb0510);
    formP.getForm().findField(zwrmb05).setValue(sumAll1.toFixed(3));	
	
	var rmb10y=getAtributebyName(formP,'DEPOSIT_RMB_AVERAGE_NY').getValue();
	var rmb11y=getAtributebyName(formP,'DEPOSIT_TRADE_AVERAGE_NY').getValue();
	var rmb12y=getAtributebyName(formP,'DEPOSIT_OTHER_AVERAGE_NY').getValue();
	var wrmb10y=getAtributebyName(formP,'EXCHANGE_IMMEDIATE_AVERAGE_NY').getValue();
	var wrmb11y=getAtributebyName(formP,'EXCHANGE_FORWARD_AVERAGE_NY').getValue();
	var wrmb12y=getAtributebyName(formP,'EXCHANGE_INTEREST_AVERAGE_NY').getValue();
	var wrmb13y=getAtributebyName(formP,'OPTIONS_TRADING_AVERAGE_NY').getValue();
	
	var tradefinance10y=getAtributebyName(formP,'TRADE_FINANCING_AVERAGE_NY').getValue();
	var tradefinance11y=getAtributebyName(formP,'TRADE_FACTORING_AVERAGE_NY').getValue();
	var tradefinance12y=getAtributebyName(formP,'TRADE_DISCOUNT_AVERAGE_NY').getValue();
	var tradefinance13y=getAtributebyName(formP,'TRADE_ACCEPTANCE_AVERAGE_NY').getValue();
	var tradefinance14y=getAtributebyName(formP,'TRADE_CREDIT_AVERAGE_NY').getValue();
	var tradefinance15y=getAtributebyName(formP,'TRADE_GUARANTEE_AVERAGE_NY').getValue();
	var tradefinance16y=getAtributebyName(formP,'LOAN_AVERAGE_NY').getValue();
	var  sumAllfirstclom=Number(rmb10y)+Number(rmb11y)+Number(rmb12y)+Number(wrmb10y)+Number(wrmb11y)+Number(wrmb12y)+Number(wrmb13y)+Number(tradefinance10y)
	+Number(tradefinance11y)+Number(tradefinance12y)+Number(tradefinance13y)+Number(tradefinance14y)+Number(tradefinance15y)+Number(tradefinance16y);
	formP.getForm().findField('mztradefinance01').setValue(sumAllfirstclom.toFixed(3));
	//我行明年目标占比%=我行明年目标收益（RMB千元）/银行收益
	var mztradefinance04v=0;
	if(formP.getForm().findField('mztradefinance03').getValue()!=''&&formP.getForm().findField('mztradefinance03').getValue()!=0){
	  mztradefinance04v=(formP.getForm().findField('mztradefinance05').getValue()/formP.getForm().findField('mztradefinance03').getValue()).toFixed(2)*100;
	}else{
		mztradefinance04v=0;
	}
	formP.getForm().findField('mztradefinance04').setValue(mztradefinance04v.toFixed(3));
   
	
	//我行明年目标提供产品使用比例  =  明年目标使用产品总数/客户适合可以使用产品
	var mpercentagePro1=0;
	if(formP.getForm().findField('SUIT_PRODUCTS_NY').getValue()!=''&&formP.getForm().findField('SUIT_PRODUCTS_NY').getValue()!=0){
	  mpercentagePro1=(formP.getForm().findField('PROVIDE_PRODUCTS_NY').getValue()/formP.getForm().findField('SUIT_PRODUCTS_NY').getValue()).toFixed(2)*100;
	}else{
		mpercentagePro1=0;
	}
	formP.getForm().findField('mproductspie').setValue(mpercentagePro1.toFixed(3));
	saveUser_ajaxSubmit4();
	
}

// 计算类 银行收益
var calTomrmbx0y0Fun = function(rmbx1,rmby1,rmbz1,rmba,rmbb,xy05) {
	//计算银行收益
	calculationFun(cust_form4,rmbx1,rmby1,rmbz1,rmba,rmbb,xy05);
	//总收益
	 sumAllrmb(cust_form4,'mrmb30','mrmb31','mrmb32','mwrmb30','mwrmb31','mwrmb32','mwrmb33','mzwrmb03','mrmb50','mrmb51','mrmb52','mwrmb50','mwrmb51','mwrmb52','mwrmb53','mzwrmb05');
	 sumAllTomtradefinance(cust_form4,'mtradefinance30','mtradefinance31','mtradefinance32','mtradefinance33','mtradefinance34','mtradefinance35','mtradefinance36','mztradefinance03','mtradefinance50','mtradefinance51','mtradefinance52','mtradefinance53','mtradefinance54','mtradefinance55','mtradefinance56','mztradefinance05','mzwrmb03','mzwrmb05');

}

// 计算类 银行收益
var calTomtradefinancebx0y0Fun = function(rmbx1,rmby1,rmbz1,rmba,rmbb,xy05) {
	//计算银行收益
	calculationFun(cust_form4,rmbx1,rmby1,rmbz1,rmba,rmbb,xy05);
	//总收益
	 sumAllTomtradefinance(cust_form4,'mtradefinance30','mtradefinance31','mtradefinance32','mtradefinance33','mtradefinance34','mtradefinance35','mtradefinance36','mztradefinance03','mtradefinance50','mtradefinance51','mtradefinance52','mtradefinance53','mtradefinance54','mtradefinance55','mtradefinance56','mztradefinance05','mzwrmb03','mzwrmb05');
}

//只获取均量计算
var calTomrmbx0Fun = function(rmbx1,rmbz1,rmba,rmbb,xy05) {
	var rmbx = cust_form4.getForm().findField(rmbx1).getValue();// 均量/年(由用户输入)
	var rmbz = cust_form4.getForm().findField(rmbz1).getValue();//我行目前占比%(由用户输入)
	/*
	 * 银行收益 公式:银行收益 = 均量/年*(Margin/Spread)
	 */
	if(rmbx<0||rmbz<0){
		Ext.Msg.alert("提示信息","请输入正数！");
		return;
	}
	if(rmbx==0){
		getAtributebyName(cust_form4,rmba).setValue(0);
	}
	if (rmbx != 0) {
		var rmbnum1=rmbx;
		cust_form4.getForm().findField(rmba).setValue(rmbnum1);
	} 
    
		/*
	     * 我行目前收益（RMB千元）/年 公式:银行收益 = 银行收益*我行目前占比
	     */ 
		var rmbnum2=getAtributebyName(cust_form4,rmba).getValue()*rmbz*0.01;
		getAtributebyName(cust_form4,rmbb).setValue(Math.round(rmbnum2));
		cust_form4.getForm().findField(xy05).setValue(getAtributebyName(cust_form4,rmbb).getValue());
	
	//总收益
	 sumAllrmb(cust_form4,'mrmb30','mrmb31','mrmb32','mwrmb30','mwrmb31','mwrmb32','mwrmb33','mzwrmb03','mrmb50','mrmb51','mrmb52','mwrmb50','mwrmb51','mwrmb52','mwrmb53','mzwrmb05');
	 sumAllTomtradefinance(cust_form4,'mtradefinance30','mtradefinance31','mtradefinance32','mtradefinance33','mtradefinance34','mtradefinance35','mtradefinance36','mztradefinance03','mtradefinance50','mtradefinance51','mtradefinance52','mtradefinance53','mtradefinance54','mtradefinance55','mtradefinance56','mztradefinance05','mzwrmb03','mzwrmb05');

};
/*
 *查询数据结果 显示计算 
 */
var creatCalFn=function(){
	if(cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').getValue()!=''){
	countClom1=cust_form3.getForm().findField('WALLETSIZE_PRODUCTS').getValue();
    }
	if(cust_form3.getForm().findField('PROVIDE_PRODUCTS').getValue()!=''){
	countClom4=cust_form3.getForm().findField('PROVIDE_PRODUCTS').getValue();
	}
	if(cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').getValue()!=''){
	countClom41=cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').getValue();
	}
	if(cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').getValue()!=''){
	countClom42=cust_form4.getForm().findField('WALLETSIZE_PRODUCTS_NY').getValue();
	}
	
	calrmbx0y0Fun('DEPOSIT_RMB_AVERAGE','DEPOSIT_RMB_MARGIN','DEPOSIT_RMB_PROPORTION','rmb30','rmb50','zwrmb05');
	calrmbx0Fun('DEPOSIT_TRADE_AVERAGE','DEPOSIT_TRADE_PROPORTION','rmb31','rmb51','zwrmb05');
	calrmbx0Fun('DEPOSIT_OTHER_AVERAGE','DEPOSIT_OTHER_PROPORTION','rmb32','rmb52','zwrmb05');
	calrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE','EXCHANGE_IMMEDIATE_MARGIN','EXCHANGE_IMMEDIATE_PROPORTION','wrmb30','wrmb50','zwrmb05');
	calrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE','EXCHANGE_FORWARD_MARGIN','EXCHANGE_FORWARD_PROPORTION','wrmb31','wrmb51','zwrmb05');
	calrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE','EXCHANGE_INTEREST_MARGIN','EXCHANGE_INTEREST_PROPORTION','wrmb32','wrmb52','zwrmb05');
	calrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE','EXCHANGE_INTEREST_MARGIN','EXCHANGE_INTEREST_PROPORTION','wrmb32','wrmb52','zwrmb05');
	calrmbx0y0Fun('OPTIONS_TRADING_AVERAGE','OPTIONS_TRADING_MARGIN','OPTIONS_TRADING_PROPORTION','wrmb33','wrmb53','zwrmb05');
	caltradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE','TRADE_FINANCING_MARGIN','TRADE_FINANCING_PROPORTION','tradefinance30','tradefinance50','ztradefinance05');
	caltradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE','TRADE_FACTORING_MARGIN','TRADE_FACTORING_PROPORTION','tradefinance31','tradefinance51','ztradefinance05');
	caltradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE','TRADE_DISCOUNT_MARGIN','TRADE_DISCOUNT_PROPORTION','tradefinance32','tradefinance52','ztradefinance05');
	caltradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE','TRADE_ACCEPTANCE_MARGIN','TRADE_ACCEPTANCE_PROPORTION','tradefinance33','tradefinance53','ztradefinance05');
    caltradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE','TRADE_CREDIT_MARGIN','TRADE_CREDIT_PROPORTION','tradefinance34','tradefinance54','ztradefinance05');
	caltradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE','TRADE_GUARANTEE_MARGIN','TRADE_GUARANTEE_PROPORTION','tradefinance35','tradefinance55','ztradefinance05');
	caltradefinancebx0y0Fun('LOAN_AVERAGE','LOAN_MARGIN','LOAN_PROPORTION','tradefinance36','tradefinance56','ztradefinance05');

	calTomrmbx0y0Fun('DEPOSIT_RMB_AVERAGE_NY','DEPOSIT_RMB_MARGIN_NY','DEPOSIT_RMB_PROPORT_NY','mrmb30','mrmb50','mzwrmb05');
	calTomrmbx0Fun('DEPOSIT_TRADE_AVERAGE_NY','DEPOSIT_TRADE_PROPORT_NY','mrmb31','mrmb51','mzwrmb05');
	calTomrmbx0Fun('DEPOSIT_OTHER_AVERAGE_NY','DEPOSIT_OTHER_PROPORT_NY','mrmb32','mrmb52','mzwrmb05');
	calTomrmbx0y0Fun('EXCHANGE_IMMEDIATE_AVERAGE_NY','EXCHANGE_IMMEDIATE_MARGIN_NY','EXCHANGE_IMMEDIATE_PROPORT_NY','mwrmb30','mwrmb50','mzwrmb05');
	calTomrmbx0y0Fun('EXCHANGE_FORWARD_AVERAGE_NY','EXCHANGE_FORWARD_MARGIN_NY','EXCHANGE_FORWARD_PROPORT_NY','mwrmb31','mwrmb51','mzwrmb05');
	calTomrmbx0y0Fun('EXCHANGE_INTEREST_AVERAGE_NY','EXCHANGE_INTEREST_MARGIN_NY','EXCHANGE_INTEREST_PROPORT_NY','mwrmb32','mwrmb52','mzwrmb05');
	calTomrmbx0y0Fun('OPTIONS_TRADING_AVERAGE_NY','OPTIONS_TRADING_MARGIN_NY','OPTIONS_TRADING_PROPORT_NY','mwrmb33','mwrmb53','mzwrmb05');
	calTomtradefinancebx0y0Fun('TRADE_FINANCING_AVERAGE_NY','TRADE_FINANCING_MARGIN_NY','TRADE_FINANCING_PROPORT_NY','mtradefinance30','mtradefinance50','mztradefinance05');
	calTomtradefinancebx0y0Fun('TRADE_FACTORING_AVERAGE_NY','TRADE_FACTORING_MARGIN_NY','TRADE_FACTORING_PROPORT_NY','mtradefinance31','mtradefinance51','mztradefinance05');
	calTomtradefinancebx0y0Fun('TRADE_DISCOUNT_AVERAGE_NY','TRADE_DISCOUNT_MARGIN_NY','TRADE_DISCOUNT_PROPORT_NY','mtradefinance32','mtradefinance52','mztradefinance05');
	calTomtradefinancebx0y0Fun('TRADE_ACCEPTANCE_AVERAGE_NY','TRADE_ACCEPTANCE_MARGIN_NY','TRADE_ACCEPTANCE_PROPORT_NY','mtradefinance33','mtradefinance53','mztradefinance05');
	calTomtradefinancebx0y0Fun('TRADE_CREDIT_AVERAGE_NY','TRADE_CREDIT_MARGIN_NY','TRADE_CREDIT_PROPORT_NY','mtradefinance34','mtradefinance54','mztradefinance05');
	calTomtradefinancebx0y0Fun('TRADE_GUARANTEE_AVERAGE_NY','TRADE_GUARANTEE_MARGIN_NY','TRADE_GUARANTEE_PROPORT_NY','mtradefinance35','mtradefinance55','mztradefinance05');
	calTomtradefinancebx0y0Fun('LOAN_AVERAGE_NY','LOAN_MARGIN_NY','LOAN_PROPORT_NY','mtradefinance36','mtradefinance56','mztradefinance05');
	 var mpercentagePro1=0;
		if(cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue()!=''&&cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue()!=0){
				  mpercentagePro1=(cust_form4.getForm().findField('PROVIDE_PRODUCTS_NY').getValue()/cust_form4.getForm().findField('SUIT_PRODUCTS_NY').getValue()).toFixed(2)*100;
		}else{
				mpercentagePro1=0;
		}
		cust_form4.getForm().findField('mproductspie').setValue(mpercentagePro1.toFixed(3));
		
		var lineUtilization=0;
		if(cust_form.getForm().findField('LINE_OF_CREDIT').getValue()!=''&&cust_form.getForm().findField('LINE_OF_CREDIT').getValue()!=0){
		  lineUtilization=(cust_form.getForm().findField('OUTSTANDING_LOAN').getValue()/cust_form.getForm().findField('LINE_OF_CREDIT').getValue()).toFixed(2)*100;
		}else{
			lineUtilization=0;
		}
		cust_form.getForm().findField('LINEUTILIZATION').setValue(lineUtilization.toFixed(2));
		
		//初始化
		//cust_form3_analysis.removeAll();
		//cust_form4_analysis.removeAll();
	}


var translateDataKey=function(data,transtype){
	var translateType = transtype===2?2:transtype===1?1:3;
	var finnalData = {};
	function stringTrans(string){
		if(translateType == 1){
			var strArr = string.toLowerCase().split('_');
			var result = '';
			if(strArr.length <= 0){
				return result;
			}
			for(var i=1;i<strArr.length;i++){
				strArr[i] = strArr[i].substring(0,1).toUpperCase() + strArr[i].substring(1);
			}
			result = strArr.join('');
			return result;
		}else if(translateType==2){
			if(string.length == 0){
				return false;
			}
			var len = string.length;
			var wordsArr = [];
			var start = 0;
			var cur = 0;
			while(cur < len){
				if(string.charCodeAt(cur)<= 90 && string.charCodeAt(cur) >= 65){
					wordsArr.push(string.substring(start,cur).toUpperCase());
					start = cur;
				}
				cur ++ ;
			}
			wordsArr.push(string.substring(start, len).toUpperCase());
			return wordsArr.join('_');
		}else {
			return string;
		}
	}
	for(var key in data){
		if(!data[key]){
			continue;
		}else {
			finnalData[stringTrans(key)] = data[key];
		}
	}
	return finnalData;
};
/*
 * 清空组件初始化
 */
var clearInitAlls=function(){
	countClom1=0;
	countClom4=0;
	countClom41=0;
	countClom42=0;
	cust_form3_analysis.removeAll();
	cust_form4_analysis.removeAll();
	cust_form.getForm().reset();
	cust_form3.getForm().reset();
	cust_form4.getForm().reset();
}
/*var deldatesDataFn=function(formP,saveUrl,id){ 
	var data=formP.getForm().getFieldValues();
	var commintData =translateDataKey(data,1);
	Ext.Msg.wait('正在删除数据，请稍等...', '提示');
	Ext.Ajax.request({
		url : basepath + saveUrl,
		method : 'POST',
		params :{'id':id}, 
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function(response) {
			Ext.Msg.alert('提示', '删除成功！');
		},
		failure : function() {
			Ext.Msg.alert('提示', '删除失败');
		}
	})
};*/
var saveCalculateDataFn=function(formP,saveUrl){ 
	var data=formP.getForm().getFieldValues();
	var commintData =translateDataKey(data,1);
	Ext.Msg.wait('正在保存，请稍等...', '提示');
	Ext.Ajax.request({
		url : basepath + saveUrl,
		method : 'POST',
		params :commintData, 
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function(response) {
			Ext.Msg.alert('提示', '操作成功！');
		},
		failure : function() {
			Ext.Msg.alert('提示', '操作失败');
		}
	})
};
var saverelationplanpatternFn=function(cust_form,saveUrl){ 
	var data=cust_form.getForm().getFieldValues();
	    data.NEXT_ANNUAL_TIME=Ext.util.Format.date(data.NEXT_ANNUAL_TIME,'Y-m-d');
	var commintData =translateDataKey(data,1);
	Ext.Msg.wait('正在保存，请稍等...', '提示');
	Ext.Ajax.request({
		url : basepath + saveUrl,
		method : 'POST',
		params :commintData,
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function(response) {
			var creatPlanid = response.responseText;
			saveCalculateDataFn(cust_form3,'/ocrmFCiRelationAnalysiAction!saveData.json?creatplanid='+creatPlanid);
			saveCalculateDataFn(cust_form4,'/ocrmFCiRelationAnalNyAction!saveData.json?creatplanid='+creatPlanid);
			Ext.Msg.alert('提示', '操作成功！');
			clearInitAlls(); //初始化组件
		},
		failure : function() {
			Ext.Msg.alert('提示', '操作失败');
		}
	})
};
//布局面板	
var panel1=new Ext.Panel({
	layout : 'form', 
	autoScroll: true,
	buttonAlign:'center',
	items : [ cust_form,
	          cust_fieldSet,
	          cust_form3,
	          cust_form3_analysis,
              cust_form4,
			  cust_form4_analysis,
	         ],
	buttons:[{
	                xtype : 'button',
					text : '重置',
					width : 60,
					handler : function() {
						clearInitAlls(); //初始化组件
					}},{
		                xtype : 'button',
						text : '提交',
						width : 60,
						handler : function() {
							if (cust_form.getForm().findField("CUST_ID").getValue()=="") {
							Ext.MessageBox.alert('提示信息', '请选择客户！');
							 return false;
						    }; 
						saverelationplanpatternFn(cust_form,'/customerRelationshipScheduleMe!saveData.json');
						/*var chr=cust_form.getForm().findField("cutGroup").getValue();
	                        alert(chr[0].inputValue+" "+cusName);
							if (!cust_form.getForm().isValid()) {
								Ext.MessageBox.alert('提示信息', '输入格式错误或存在漏输入项,请重新输入！');
								return false;
							};*/
						}}
					  /*,{
		                xtype : 'button',
						text : '删除',
						width : 60,
						handler : function() {
							if(cust_form.getForm().findField("ID").getValue()==''){
								Ext.Msg.alert("提示","没有可删除的数据！");
								return false;
							}
							Ext.MessageBox.confirm('提示', '确定要删除"'+cust_form.getForm().findField("CUST_NAME").getValue()+'"关系计划表吗?', function(buttonId) {
								if (buttonId.toLowerCase() == "no") {
									return false;
								}
							if(cust_form.getForm().findField("ID").getValue()!=''){
							deldatesDataFn(cust_form,'/customerRelationshipScheduleMe!destroy.json',cust_form.getForm().findField("ID").getValue());
							}
							if(cust_form3.getForm().findField("ID").getValue()!=''){
							deldatesDataFn(cust_form3,'/ocrmFCiRelationAnalysiAction!destroy.json',cust_form3.getForm().findField("ID").getValue());
							}
							if(cust_form4.getForm().findField("ID").getValue()!=''){
							deldatesDataFn(cust_form4,'/ocrmFCiRelationAnalNyAction!destroy.json',cust_form4.getForm().findField("ID").getValue());
							}
							clearInitAlls(); //初始化组件
							});
						}}*/
					
			]
	});		
/*var customerView = [{	
	*//**
	 * 客户关系计划表
	 *//*
	title : '客户关系计划表',
	hideTitle : true,
	layout : 'form',
	items : [cust_form,
	          cust_fieldSet,
	          cust_form3,
              cust_form4]
		// 新增改动
	}];*/

new Ext.Viewport({
    layout: 'fit',
    items: [
      panel1
    ]
    });
