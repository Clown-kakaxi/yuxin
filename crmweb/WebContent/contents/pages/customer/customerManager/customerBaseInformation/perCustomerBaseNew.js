
/**对私客户基本信息
 * @author 2012
 * @since 2012-9-27
 */

/*********************************码值定义********************************/

Ext.onReady(function() {
	
	var sexStore = new Ext.data.Store({//性别的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100005'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});   
	var politicalStatStore = new Ext.data.Store({//政治面貌的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=POL_LANDSCAPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});  
	var folkStore = new Ext.data.Store({//民族的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100001'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	}); 
	var religionTypeStore = new Ext.data.Store({//宗教信仰的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=RELIGION_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var citizenshipStore = new Ext.data.Store({//国籍的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=NAT0100001'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var marryStatusStore = new Ext.data.Store({//婚姻状况的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100003'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var healthStatusStore = new Ext.data.Store({//健康状况的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100016'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var householdStore = new Ext.data.Store({//户籍性质的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=HOUSEHOLD_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var custGradeStore = new Ext.data.Store({//客户级别的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=CDE0100016'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var custCreditLevelStore = new Ext.data.Store({//信用等级的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=CDE0100033'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var empFlagStore = new Ext.data.Store({//本行员工的标志的，是否本行股东的，代发工资客户类型的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var workStateStore = new Ext.data.Store({//就业状态的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=WORK_STATE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var induCodeStore = new Ext.data.Store({//所属单位行业的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=PAR2100001'
		}),	
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var currWorkTypeStore = new Ext.data.Store({//单位性质的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0200002'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var perWorkPropStore = new Ext.data.Store({//个人从业性质，职业的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100013'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var eduLevelStore = new Ext.data.Store({//教育水平的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100007'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var hostStatusStore = new Ext.data.Store({//居住状况的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100014'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var incoSourStore = new Ext.data.Store({//主要收入来源，其他收入来源的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=BCD0100003'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var incoCurrStore = new Ext.data.Store({//收入币种的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=ACC1300012'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});

	var rsRecord = Ext.data.Record.create([
	                                       {name:'custId',mapping:'CUST_ID'},                     
	                                       {name:'custZhName',mapping:'CUST_ZH_NAME'},          
	                                       {name:'custEnName',mapping:'CUST_EN_NAME'},          
	                                       {name:'usedName',mapping:'USED_NAME'},                
	                                       {name:'birthday',mapping:'BIRTHDAY'},                  
	                                       {name:'sex',mapping:'SEX'},        
	                                       {name:'sexOra',mapping:'SEX_ORA'},
	                                       {name:'politicalStat',mapping:'POLITICAL_STAT'},    
	                                       {name:'politicalStatOra',mapping:'POLITICAL_STAT_ORA'},
	                                       {name:'bierhAddress',mapping:'BIERH_ADDRESS'},        
	                                       {name:'folk',mapping:'FOLK'},          
	                                       {name:'folkOra',mapping:'FOLK_ORA'},
	                                       {name:'religionType',mapping:'RELIGION_TYPE'}, 
	                                       {name:'religionTypeOra',mapping:'RELIGION_TYPE_ORA'},
	                                       {name:'citizenship',mapping:'CITIZENSHIP'},  
	                                       {name:'citizenshipOra',mapping:'CITIZENSHIP_ORA'},
	                                       {name:'marrgStatus',mapping:'MARRG_STATUS'},
	                                       {name:'marrgStatusOra',mapping:'MARRG_STATUS_ORA'},
	                                       {name:'healthStatus',mapping:'HEALTH_STATUS'},
	                                       {name:'healthStatusOra',mapping:'HEALTH_STATUS_ORA'},
	                                       {name:'householdType',mapping:'HOUSEHOLD_TYPE'},
	                                       {name:'householdTypeOra',mapping:'HOUSEHOLD_TYPE_ORA'},
	                                       {name:'senAddr',mapping :'SEN_ADDR'},                 
	                                       {name:'currCity',mapping:'CURR_CITY'},                
	                                       {name:'currCityTime',mapping:'CURR_CITY_TIME'},      
	                                       {name:'custGrade',mapping:'CUST_GRADE'},   
	                                       {name:'custGradeOra',mapping:'CUST_GRADE_ORA'},
	                                       {name:'custCreditLevel',mapping:'CUST_CREDIT_LEVEL'},
	                                       {name:'custCreditLevelOra',mapping:'CUST_CREDIT_LEVEL_ORA'},
	                                       {name:'empFlag',mapping:'EMP_FLAG'},
	                                       {name:'empFlagOra',mapping:'EMP_FLAG_ORA'},
	                                       {name:'isCurrBnkPart',mapping:'IS_CURR_BNK_PART'},
	                                       {name:'isCurrBnkPartOra',mapping:'IS_CURR_BNK_PART_ORA'},
	                                       {name:'undeCustType',mapping:'UNDE_CUST_TYPE'},
	                                       {name:'undeCustTypeOra',mapping:'UNDE_CUST_TYPE_ORA'},
	                                       {name:'perCustIden',mapping:'PER_CUST_IDEN'},        
	                                       {name:'eduLevel',mapping:'EDU_LEVEL'},
	                                       {name:'eduLevelOra',mapping:'EDU_LEVEL_ORA'},
	                                       {name:'graduateDate',mapping:'GRADUATE_DATE'},        
	                                       {name:'graduateSchool',mapping:'GRADUATE_SCHOOL'},    
	                                       {name:'workState',mapping:'WORK_STATE'},
	                                       {name:'workStateOra',mapping:'WORK_STATE_ORA'},
	                                       {name:'workTime',mapping:'WORK_TIME'},                
	                                       {name:'laborContLimit',mapping:'LABOR_CONT_LIMIT'},  
	                                       {name:'workUnit',mapping:'WORK_UNIT'},                
	                                       {name:'induCode',mapping:'INDU_CODE'},
	                                       {name:'induCodeOra',mapping:'INDU_CODE_ORA'},
	                                       {name:'currWorkType',mapping:'CURR_WORK_TYPE'},
	                                       {name:'currWorkTypeOra',mapping:'CURR_WORK_TYPE_ORA'},
	                                       {name:'workDept',mapping:'WORK_DEPT'},                
	                                       {name:'currWorkTime',mapping:'CURR_WORK_TIME'},      
	                                       {name:'perWorkProp',mapping:'PER_WORK_PROP'},
	                                       {name:'perWorkPropOra',mapping:'PER_WORK_PROP_ORA'},
	                                       {name:'modiBrccode',mapping:'MODI_BRCCODE'},          
	                                       {name:'isUnitLeader',mapping:'IS_UNIT_LEADER'},
	                                       {name:'isUnitLeaderOra',mapping:'IS_UNIT_LEADER_ORA'},
	                                       {name:'modiTeller',mapping:'MODI_TELLER'},
	                                       {name:'modiTellerOra',mapping:'MODI_TELLER_ORA'},
	                                       {name:'socPosi',mapping:'SOC_POSI'},                  
	                                       {name:'perDesc',mapping:'PER_DESC'},                  
	                                       {name:'resume',mapping:'RESUME'},                    
	                                       {name:'wrokPlace',mapping:'WROK_PLACE'},                  
	                                       {name:'workPostNo',mapping:'WORK_POST_NO'},          
	                                       {name:'officePhone',mapping:'OFFICE_PHONE'},          
	                                       {name:'hostAddr',mapping:'HOST_ADDR'},                
	                                       {name:'hostPostNo',mapping:'HOST_POST_NO'},          
	                                       {name:'hostTelNum',mapping:'HOST_TEL_NUM'},          
	                                       {name:'telephoneNum',mapping:'TELEPHONE_NUM'},        
	                                       {name:'email',mapping:'EMAIL'},                        
	                                       {name:'qqNo',mapping:'QQ_NO'},                        
	                                       {name:'weibo',mapping:'WEIBO'},                        
	                                       {name:'otherContact',mapping:'OTHER_CONTACT'},        
	                                       {name:'hostStatus',mapping:'HOST_STATUS'},
	                                       {name:'hostStatusOra',mapping:'HOST_STATUS'},
	                                       {name:'equityType',mapping:'EQUITY_TYPE'},            
	                                       {name:'mainIncoSour',mapping:'MAIN_INCO_SOUR'},
	                                       {name:'mainIncoSourOra',mapping:'MAIN_INCO_SOUR_ORA'},
	                                       {name:'otherIncoSour',mapping:'OTHER_INCO_SOUR'},
	                                       {name:'otherIncoSourOra',mapping:'OTHER_INCO_SOUR_ORA'},
	                                       {name:'perYeIncoCurr',mapping:'PER_YE_INCO_CURR'},
	                                       {name:'perYeIncoCurrOra',mapping:'PER_YE_INCO_CURR_ORA'},
	                                       {name:'perYeIncome',mapping:'PER_YE_INCOME'},        
	                                       {name:'perMonIncoCurr',mapping:'PER_MON_INCO_CURR'},
	                                       {name:'perMonIncoCurrOra',mapping:'PER_MON_INCO_CURR_ORA'},
	                                       {name:'perMonIncome',mapping:'PER_MON_INCOME'},      
	                                       {name:'hostYeIncoCurr',mapping:'HOST_YE_INCO_CURR'},
	                                       {name:'hostYeIncoCurrOra',mapping:'HOST_YE_INCO_CURR_ORA'},
	                                       {name:'hostYeIncome',mapping:'HOST_YE_INCOME'},      
	                                       {name:'isLifeInsu',mapping:'IS_LIFE_INSU'},   
	                                       {name:'isLifeInsuOra',mapping:'IS_LIFE_INSU_ORA'},
	                                       {name:'isIllnInsu',mapping:'IS_ILLN_INSU'},
	                                       {name:'isIllnInsuOra',mapping:'IS_ILLN_INSU_ORA'},
	                                       {name:'isPension',mapping:'IS_PENSION'},
	                                       {name:'isPensionOra',mapping:'IS_PENSION_ORA'},
	                                       {name:'burdenNums',mapping:'BURDEN_NUMS'},            
	                                       {name:'isOwercar',mapping:'IS_OWERCAR'},
	                                       {name:'isOwercarOra',mapping:'IS_OWERCAR_ORA'},
	                                       {name:'remark',mapping:'REMARK'},                      
	                                       {name:'updateSys',mapping:'UPDATE_SYS'},  
	                                       {name:'updateOrg',mapping:'UPDATE_ORG'},
	                                       {name:'updateOrgName',mapping:'UPDATE_ORG_NAME'},              
	                                       {name:'updateDate',mapping:'UPDATE_DATE'},  
	                                       {name:'updateUser',mapping:'UPDATE_USER'},
	                                       {name:'updateUserName',mapping:'UPDATE_USER_NAME'},
	                                       {name:'certType',mapping:'CERT_TYPE'},
	                                       {name:'certNum',mapping:'CERT_NUM'}
	                                       ]);
	var rsreader = new Ext.data.JsonReader( {
		successProperty : 'success',
		idProperty : 'custId',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, rsRecord);
	var rownums = new Ext.grid.RowNumberer({//对私客户基本信息调整历史
		header : 'No.',
		width : 28
	});
	var perCusBaseHisCm = new Ext.grid.ColumnModel([rownums,//对私客户基本信息调整历史grid
	                                                {header : 'upId',dataIndex : 'upId',sortable :true,width : 100,hidden : true },
	                                                { header : '客户编号',dataIndex : 'custId', sortable : 100, width : 100,hidden : true },
	                                                { header : '修改项目', dataIndex : 'updateItem', sortable : true, width : 120 },
	                                                { header : '修改前内容', dataIndex : 'updateBeCont', sortable : true, width : 120 },
	                                                { header : '修改后内容', dataIndex : 'updateAfCont', sortable : true, width : 120},
	                                                { header : '修改人', dataIndex : 'userName',ortable : true, width : 100 },
	                                                { header : '修改时间',dataIndex : 'updateDate',sortable : true, width : 135}
	                                              ]);
	
 	var perCustBaseHisRecord = new Ext.data.Record.create([//对私客户基本信息调整历史record
 	  	                                                  {name:'upId',mapping:'UP_ID'},
 	  	                                                  {name:'custId',mapping:'CUST_ID'},
 	  	                                                  {name:'custName',mapping:'CUST_NAME'},
 	  	                                                  {name:'updateItem',mapping:'UPDATE_ITEM'},
 	  	                                                  {name:'updateBeCont',mapping:'UPDATE_BE_CONT'},
 	  	                                                  {name:'updateAfCont',mapping:'UPDATE_AF_CONT'},
 	  	                                                  {name:'updateUser',mapping:'UPDATE_USER'},
 	  	                                                  {name:'updateDate',mapping:'UPDATE_DATE'},
 	  	                                                  {name:'userName',mapping:'USER_NAME'}
 	  	                                              ]);
	
	var perCustBaseHisReader = new Ext.data.JsonReader({//对私客户借本信息历史调整读取json数据的panel
       	totalProperty:'json.count',
       	root:'json.data'
       	},perCustBaseHisRecord);
  	var perCustBaseHisStore = new Ext.data.Store(
           	{
           		proxy:new Ext.data.HttpProxy({
           		url:basepath+'/cusBaseAdujstHis-Action.json',
           		failure : function(response){
           			var resultArray = Ext.util.JSON.decode(response.status);
           			if(resultArray == 403) {
           				Ext.Msg.alert('提示', response.responseText);
           			}
           		},
           		method:'GET'
           		}),
           		reader:perCustBaseHisReader
           	}
           	);	
	var perCustAdjustHisGrid = new Ext.grid.EditorGridPanel({
		autoScroll : true,
		store : perCustBaseHisStore, 	// 数据存储
		cm : perCusBaseHisCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var perCustBaseHisWin = new Ext.Window({
		plain : true,
		layout : 'fit',
		resizable : true,
		draggable : true,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 650,
		height : 400,
		buttonAlign : "center",
		title : '对私客户基本信息修改历史',
		items : perCustAdjustHisGrid,
		buttons : [{
			text : '返回',
			handler : function(){
			perCustBaseHisWin.hide();
			}
		}]
	});
	
	var perCustomerBaseInfo = new Ext.FormPanel( {//对私客户基本信息PANEL
		reader : rsreader,
		frame : true,
		autoScroll : true,
//		region:'center',
		buttonAlign :'center',
		items : [{
			xtype : 'fieldset',
			title : '一、基本信息 ',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [
					         {xtype : 'textfield',fieldLabel:'客户号',name:'custId',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},                                                                              
					         {xtype : 'textfield',fieldLabel:'证件类型',name:'certType',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'英文名',name:'custEnName',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},                                                                             
					         {xtype : 'combo', store:sexStore,resizable : true,name : 'sex',hiddenName : 'sex', fieldLabel :'性别',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	    typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype :'textfield',fieldLabel:'籍贯',name:'bierhAddress',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},                                                                          
					         {xtype : 'combo', store:religionTypeStore,resizable : true,name : 'religionType',hiddenName : 'religionType', fieldLabel :'宗教信仰',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	    typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:marryStatusStore,resizable : true,name : 'marrgStatus',hiddenName : 'marrgStatus', fieldLabel :'婚姻状况',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	    typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:politicalStatStore,resizable : true,name : 'politicalStat',hiddenName : 'politicalStat', fieldLabel :'政治面貌',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						        	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
						     {xtype : 'textfield',fieldLabel:'所在城市',name:'currCity',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}   	
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
							 {xtype : 'textfield',fieldLabel:'姓名',name:'custZhName',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
							 {xtype : 'textfield',fieldLabel:'证件号码',name:'certNum',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
							 {xtype : 'textfield',fieldLabel:'曾用名',name:'usedName',labelStyle:'text-align:right;',anchor:'95%'},                                                                                   
							 {xtype : 'datefield',fieldLabel:'出生日期',id:'birthday',editable : false,labelStyle:'text-align:right;',format : 'Y-m-d',anchor:'95%'},
							 {xtype : 'combo', store:folkStore,resizable : true,name : 'folk',hiddenName : 'folk', fieldLabel :'民族',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						        	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'combo', store:citizenshipStore,resizable : true,name : 'citizenship',hiddenName : 'citizenship', fieldLabel :'国籍',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						        	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'combo', store:healthStatusStore,resizable : true,name : 'healthStatus',hiddenName : 'healthStatus', fieldLabel :'健康状况',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						        	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
						     {xtype : 'combo', store:householdStore,resizable : true,name : 'householdType',hiddenName : 'householdType', fieldLabel :'户籍状态',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
							        typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype:'textfield',fieldLabel:'所在城市居住时间',name:'currCityTime',labelStyle:'text-align:right;',anchor:'95%'}
							]
				},{
					layout : 'form',                                                                                                                                                                                     
					columnWidth : .50,                                                                                                                                                                                   
					labelWidth : 150,                                                                                                                                                                                    
					items : [                                                                                                                                                                                            
					         {xtype : 'textfield',fieldLabel:'户口所在地',name:'senAddr',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}                                                                    
					        ]      
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '二、分类信息 ',
			titleCollapse : true,
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [
					         {xtype : 'combo', store:custGradeStore,resizable : true,name : 'custGrade',hiddenName : 'custGrade', fieldLabel :'客户级别',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	 	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:empFlagStore,resizable : true,name : 'empFlag',hiddenName : 'empFlag', fieldLabel :'本行员工标识',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					    	    	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:empFlagStore,resizable : true,name : 'undeCustType',hiddenName : 'undeCustType', fieldLabel :'代发工资客户类型',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        		typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'}
					        ]
				}, {
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					          {xtype : 'combo', store:custCreditLevelStore,resizable : true,name : 'custCreditLevel',hiddenName : 'custCreditLevel', fieldLabel :'信用等级',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	  	 typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					          {xtype : 'combo', store:empFlagStore,resizable : true,name : 'isCurrBnkPart',hiddenName : 'isCurrBnkPart', fieldLabel :'是否本行股东',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	     typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					          {xtype:'textfield',fieldLabel:'私人银行客户标识',name:'perCustIden',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
					}]
				} ]
		},{
			xtype : 'fieldset',
			title : '三、履历信息',
			titleCollapse : true,
    		collapsed : true,
			collapsible : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [
					         {xtype : 'combo', store:eduLevelStore,resizable : true,name : 'eduLevel',hiddenName : 'eduLevel', fieldLabel :'教育水平',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	 	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'datefield',fieldLabel:'毕业时间',id:'graduateDate',name:'graduateDate',labelStyle:'text-align:right;',format : 'Y-m-d',readOnly:false,anchor:'95%'},
					         {xtype : 'datefield',fieldLabel:'工作时间',name:'workTime',id:'workTime',labelStyle:'text-align:right;',format : 'Y-m-d',readOnly:false,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'工作单位名称',name:'workUnit',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					         {xtype : 'combo', store:currWorkTypeStore,resizable : true,name : 'currWorkType',hiddenName : 'currWorkType', fieldLabel :'单位性质',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	 	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'datefield',fieldLabel:'现单位工作时间',id:'currWorkTime',name:'currWorkTime',labelStyle:'text-align:right;',format : 'Y-m-d',anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'职务',name:'modiBrccode',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					         {xtype : 'combo', store:perWorkPropStore,resizable : true,name : 'modiTeller',hiddenName : 'modiTeller', fieldLabel :'职业',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
					        	 	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [
							 {xtype : 'combo', store:workStateStore,resizable : true,name : 'workState',hiddenName : 'workState', fieldLabel :'就业状态',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
									typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'textfield',fieldLabel:'毕业学校',name:'graduateSchool',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
						   	 {xtype : 'numberfield',fieldLabel:'劳动合同期限',name:'laborContLimit',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
							 {xtype : 'combo', store:induCodeStore,resizable : true,name : 'induCode',hiddenName : 'induCode', fieldLabel :'所属单位行业',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						   		 	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'textfield',fieldLabel:'单位部门',name:'workDept',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
							 {xtype : 'combo', store:perWorkPropStore,resizable : true,name : 'perWorkProp',hiddenName : 'perWorkProp', fieldLabel :'个人从业性质',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
								 	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'combo', store:empFlagStore,resizable : true,name : 'isUnitLeader',hiddenName : 'isUnitLeader', fieldLabel :'是否单位控制人',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
									typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype:'textfield',fieldLabel:'社会服务',name:'socPosi',labelStyle:'text-align:right;',anchor:'95%'}
					        ]
				},{
					layout : 'form',                                                                                                                                                                                     
					columnWidth : .50,                                                                                                                                                                                   
					labelWidth : 150,                                                                                                                                                                                    
					items : [                                                                                                                                                                                            
					         {xtype : 'textarea',fieldLabel:'个人描述',name:'perDesc',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}                                                                    
					        ]  
				},{
					layout : 'form',                                                                                                                                                                                     
					columnWidth : .50,                                                                                                                                                                                   
					labelWidth : 150,                                                                                                                                                                                    
					items : [                                                                                                                                                                                            
					         {xtype : 'textarea',fieldLabel:'个人简历',name:'resume',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}                                                                    
					        ]  
				}]
			} ]
		},{  	
			xtype : 'fieldset',
			title : '四、联系信息 ',
			titleCollapse : true,
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			items : [{
				layout : 'column',
				items : [{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textfield',fieldLabel:'办公地址邮编',name:'workPostNo',maxLength:6,vtype:'postcode',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textfield',fieldLabel:'办公电话',name:'officePhone',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textfield',fieldLabel:'家庭地址邮编',name:'hostPostNo',maxLength:6,vtype:'postcode',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'移动电话',name:'telephoneNum',id:'telephoneNum',vtype:'mobile',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'邮箱',name:'email',vtype:'email',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textfield',fieldLabel:'家庭联系电话',name:'hostTelNum',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'QQ',name:'qqNo',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'微博',name:'weibo',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				},{
					layout : 'form',                                                                                                                                                                                     
					columnWidth : .50,                                                                                                                                                                                   
					labelWidth : 150,                                                                                                                                                                                    
					items : [                                                                                                                                                                                            
					         {xtype : 'textarea',fieldLabel:'办公地址',name:'wrokPlace',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}                                                                    
					        ]  
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textarea',fieldLabel:'家庭住址',name:'hostAddr',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textarea',fieldLabel:'其他联系方式',name:'otherContact',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				} ]
			} ]
		},{
			xtype : 'fieldset',
			title : '五、财务信息 ',
			titleCollapse : true,
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [{

					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'combo', store:hostStatusStore,resizable : true,name : 'hostStatus',hiddenName : 'hostStatus', fieldLabel :'居住状况',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						            typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:incoSourStore,resizable : true,name : 'mainIncoSour',hiddenName : 'mainIncoSour', fieldLabel :'主要收入来源',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						            typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:incoCurrStore,resizable : true,name : 'perYeIncoCurr',hiddenName : 'perYeIncoCurr', fieldLabel :'个人税前年收入币种',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						            typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
						     {xtype : 'combo', store:incoCurrStore,resizable : true,name : 'perMonIncoCurr',hiddenName : 'perMonIncoCurr', fieldLabel :'个人月工资收入币种',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
							        typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:incoCurrStore,resizable : true,name : 'hostYeIncoCurr',hiddenName : 'hostYeIncoCurr', fieldLabel :'家庭年收入币种',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
							        typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:empFlagStore,resizable : true,name : 'isLifeInsu',hiddenName : 'isLifeInsu', fieldLabel :'是否参加人寿保险',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
							        typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
					         {xtype : 'combo', store:empFlagStore,resizable : true,name : 'isPension',hiddenName : 'isPension', fieldLabel :'是否参加养老保险',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
							        typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
				   	         {xtype : 'combo', store:empFlagStore,resizable : true,name : 'isOwercar',hiddenName : 'isOwercar', fieldLabel :'是否拥有车辆',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						            typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'}
					        ]
				},{

					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
							 {xtype : 'textfield',fieldLabel:'产权性质',name:'equityType',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
							 {xtype : 'combo', store:incoSourStore,resizable : true,name : 'otherIncoSour',hiddenName : 'otherIncoSour', fieldLabel :'其他经济来源',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						            typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'numberfield',fieldLabel:'个人税前年收入金额',name:'perYeIncome',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
							 {xtype : 'numberfield',fieldLabel:'个人月工资收入金额',name:'perMonIncome',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
							 {xtype : 'numberfield',fieldLabel:'家庭年收入金额',name:'hostYeIncome',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
							 {xtype : 'combo', store:empFlagStore,resizable : true,name : 'isIllnInsu',hiddenName : 'isIllnInsu', fieldLabel :'是否参加大病保险',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
						            typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '95%'},
							 {xtype : 'numberfield',fieldLabel:'供养人数',name:'burdenNums',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '六、其他信息 ',
			titleCollapse : true,
			collapsed : true,
			collapsible : true,
			autoHeight : true,
			items : [{
				layout : 'column',
				items : [{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textfield',fieldLabel:'最近更新系统',id:'updateSys',name:'updateSys',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
					         {xtype : 'textfield',fieldLabel:'最近更新人',id:'updateUser',name:'updateUser',labelStyle:'text-align:right;',readOnly:true,anchor:'95%',hidden:true},
					         {xtype : 'textfield',fieldLabel:'最近更新人',id:'updateUserName',name:'updateUserName',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textfield',fieldLabel:'最近更新机构',id:'updateOrg',name:'updateOrg',labelStyle:'text-align:right;',readOnly:true,anchor:'95%',hidden:true},
					         {xtype : 'textfield',fieldLabel:'最近更新机构',id:'updateOrgName',name:'updateOrgName',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
					         {xtype : 'datefield',fieldLabel:'最近更新日期',id:'updateDate',name:'updateDate',format:'Y-m-d',labelStyle:'text-align:right;',readOnly:true,anchor:'95%'}
					        ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 150,
					items : [  
					         {xtype : 'textarea',fieldLabel:'备注',name:'remark',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        ]
				}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{text :'保存',
			id :'Add',
			handler: function(){
			if (!perCustomerBaseInfo.getForm().isValid()) {
				Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
				return false;
			}
			
			Ext.getCmp('updateSys').setValue('CRM');
			Ext.getCmp('updateUser').setValue(__userId);
			Ext.getCmp('updateOrg').setValue(__units);
			Ext.getCmp('updateDate').setValue(new Date());
	 		json1 = perCustomerBaseInfo.reader.jsonData.json.data[0];
	 		json2 = perCustomerBaseInfo.form.getValues(false);//在提交FORM前若该字段为空，切数据库类型为bigDecimal，则将该字段的值设为'0',json2为即将保存的form上的值
			var perModel = [];
	 		for(var key in json2){
	 			if(key != 'certType'){
	 			var pcbhModel = {};
	 			rsRecord.prototype.fields.items;
				Ext.each(rsRecord.prototype.fields.items,function(a){
					if(a.name==key){
						if(json2[key] != json1[a.mapping]){
							if(perCustomerBaseInfo.getForm().findField(a.name).getXType()=='numberfield'){
								var t = parseFloat(json1[a.mapping]); 
								if(json2[key]!= t){
									pcbhModel.custId = oCustInfo.cust_id;
									pcbhModel.updateBeCont = json1[key];
									pcbhModel.updateAfCont = json2[a.name];
									pcbhModel.updateItem = perCustomerBaseInfo.getForm().findField(key).fieldLabel;
									perModel.push(pcbhModel);	
								}
							}else{
									pcbhModel.custId = oCustInfo.cust_id;
									pcbhModel.updateBeCont = json1[a.mapping];
									pcbhModel.updateAfCont = json2[key];
									pcbhModel.updateItem = perCustomerBaseInfo.getForm().findField(key).fieldLabel;
									perModel.push(pcbhModel);
							}}
							return;
					}
				});
	 			}
	 		}
			
			
			Ext.Ajax.request({
				url:basepath+'/PerCustomerBaseInfo-Action.json',//保存的action
				method:'post',
				form : perCustomerBaseInfo.getForm().id,
				success : checkResult,
				failure: checkResult
			});
			function checkResult(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
					Ext.Msg.alert('提示', '操作成功');
					perCustomerBaseInfo.getForm().load({
						restful : true,
						url : basepath + '/PerCustomerBaseQueryAction.json',
						method : 'GET',
						params : {
							'custId' : oCustInfo.cust_id
						}
					});
					Ext.Ajax.request({
						url:basepath+'/PerCustomerBaseInfo-Action!save.json',//保存的action
						method:'POST',
						params : {
			                'perModel':Ext.encode(perModel)
						}
					});					
				} else {
					if(resultArray == 403){
						Ext.Msg.alert('提示', response.responseText);
					}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
					}
				};
			}
		}
		},'-',{
			text:'修改历史',
			handler:function(){
			perCustBaseHisWin.show();
			perCustBaseHisStore.load({
	    		params : {
	    		'custId':oCustInfo.cust_id
			}
			});
		}
		}]
	});

	perCustomerBaseInfo.getForm().load({//对私客户基本信息面板数据加载load
		restful : true,
		url : basepath + '/PerCustomerBaseQueryAction.json',//查询Action
		method : 'GET',
		params:{
		'custId':oCustInfo.cust_id
	}
	}); 
	
	// 布局模型
 	var viewport_center = new Ext.Panel({//页面展示
 		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		layout:'fit',
		autoScroll:true,
 	    items:[perCustomerBaseInfo]
 	});	
});
