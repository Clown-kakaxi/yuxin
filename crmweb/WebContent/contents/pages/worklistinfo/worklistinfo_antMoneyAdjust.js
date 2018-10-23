/**
 * @description 反洗钱风险等级调整--流程客户显示页面js
 * @author
 * @since ?
 * @update 
 */
Ext.onReady(function() {
	/**
	 * 企业规模
	 */
	var entScaleRhStore =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000018'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	行业分类
	*/
	var industryCategoryStore =  new Ext.data.Store({
	    restful : true,
	    autoLoad : true,
	    sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
	    proxy : new Ext.data.HttpProxy({
	        url : basepath + '/lookup.json?name=XD000287'
	    }),
	    reader : new Ext.data.JsonReader( {
	        root : 'JSON'
	    },['key','value'])
	});
	
	/**
	 * 反洗钱等级
	 */
	var riskgradeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=FXQ_RISK_LEVEL'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	 * 是否
	 */
	var ifStore  = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	
	/**
	 * 客户类型
	 */
	var zlXD000080Store  = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	
	/**
	 * 国别
	 */
	var conStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000025'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	//联系人的身份-对公 --数据字典
	var zlFXQ_DQSH028Store =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC' //加载数据后 根据key升序排列
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FXQ_DQSH028'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	//客户办理业务 对私  --数据字典
	var zlFXQ007Store =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC' //加载数据后 根据key升序排列
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FXQ007'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	//客户办理业务 对公 --数据字典
	var zlFXQ025Store =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC' //加载数据后 根据key升序排列
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FXQ025'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});

	var zlFXQ021Store =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC' //加载数据后 根据key升序排列
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FXQ021'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});

	//客户的股权或控制权结构 数据字典
	var zlFXQ023Store =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC' //加载数据后 根据key升序排列
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FXQ023'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});

	var zlFXQ_DQSH004Store =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC' //加载数据后 根据key升序排列
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=FXQ_DQSH004'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});


	debugger;
	Ext.QuickTips.init();
	debugger;
	//instanceid 0标识 1是用户ID,2调整前的的风险等级，3是调整后的风险等级，4 最后更新人ID，5客户类型（流程处理中显示客户信息分辨是个人还是企业）   6.是审核结束表中的数据总数 cust_type
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var custGrade=instanceid.split('_')[2];
	var custType=instanceid.split('_')[5];
	var nodeid = curNodeObj.nodeid;
    var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/antMoneyAdjust.json?CUST_ID='+id+'&CUST_GRADE='+custGrade +'&instanceid='+instanceid 
        		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, [
			'CUST_TYPE'		//	{name:'CUST_TYPE',text:'客户类型',resutlWidth:80,translateType:'XD000080',searchField:true},
			,'CUST_STAT'		//	{name:'CUST_STAT',text:'客户状态',resutlWidth:80,translateType:'XD000081',gridField:false},
			,'CUST_ID'		//	{name:'CUST_ID',text:'客户编号',resutlWidth:120,searchField:true,dataType:'string'},
			,'CORE_NO'		//	{name:'CORE_NO',text:'核心客户号',resutlWidth:120,searchField:true,dataType:'string'},
			,'CUST_NAME'		//	{name:'CUST_NAME',text:'客户名称',resutlWidth:150,searchField:true,dataType:'string'},
			,'IDENT_TYPE'		//	{name:'IDENT_TYPE',text:'证件类型',resutlWidth:120,translateType:'XD000040',editable: true,gridField:false},
			,'IDENT_NO'		//	{name:'IDENT_NO',text:'证件号码',resutlWidth:120,dataType:'string',gridField:false},
			,'ORG_NAME'		//	{name:'ORG_NAME',text:'归属机构',xtype:'wcombotree',innerTree:'ORGTREE',resutlWidth:80,showField:'text',hideField:'UNITID',editable:false,allowBlank:false,gridField:false},	   
			,'MGR_NAME'		//	{name:'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false,gridField:false},
			,'BELONG_TEAM_HEAD_NAME'		//	{name:'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:120,singleSelect: false,gridField:false},	 
			,'CREATE_DATE'		//	{name:'CREATE_DATE',text:'开户日期',resutlWidth:100},
			,'CUST_GRADE'		//	{name:'CUST_GRADE',text:'系统预评等级',resutlWidth:100,translateType:'FXQ_RISK_LEVEL',searchField: true},
			,'STAT_FP'		//	{name:'STAT_FP',text:'复评状态',resutlWidth:120,translateType:'FXQ_FLAG',gridField:true},
			,'CUST_GRADE_ID'		//	{name:'CUST_GRADE_ID',text:'客户等级编号',resultWidth:60,gridField:false},
			//个人客户
			,'CUST_GRADE_ID'		//	{name:'CITIZENSHIP',text:'国籍',resutlWidth:100,gridField:false,translateType:'XD000025'},
			,'CAREER_TYPE'		//	{name:'CAREER_TYPE',text:'职业',resutlWidth:100,gridField:false},
			,'BIRTHDAY'		//	{name:'BIRTHDAY',text:'出生日期',resutlWidth:100,gridField:false},
			//企业客户
			,'BIRTHDAY'		//	{name:'BUILD_DATE',text:'成立日期',resutlWidth:100,gridField:false},
			,'NATION_CODE'		//	{name:'NATION_CODE',text:'注册地',resutlWidth:100,gridField:false},
			
			,'IDENT_TYPE1'		//	{name:'IDENT_TYPE1',text:'证件1类型',resutlWidth:100,gridField:false},
			,'INDENT_NO1'		//	{name:'INDENT_NO1',text:'证件1号码',resutlWidth:100,gridField:false},
			,'IDENT_EXPIRED_DATE1'		//	{name:'IDENT_EXPIRED_DATE1',text:'证件1有效期',resutlWidth:100,gridField:false},
			,'IDENT_TYPE2'		//	{name:'IDENT_TYPE2',text:'证件2类型',resutlWidth:100,gridField:false},
			,'INDENT_NO2'		//	{name:'INDENT_NO2',text:'证件2号码',resutlWidth:100,gridField:false},
			,'IDENT_EXPIRED_DATE2'		//	{name:'IDENT_EXPIRED_DATE2',text:'证件2有效期',resutlWidth:100,gridField:false},
			
			//客户是否为代理开户
			,'FLAG_AGENT'		//	{name:'FLAG_AGENT',text:'客户是否为代理开户',resutlWidth:100,translateType:'IF_FLAG',gridField:false,allowBlank:false},
				
			//个人反洗钱指标
			,'FXQ008'		//	{name:'FXQ008',text:'客户是否涉及风险提示信息或权威媒体报道信息',resutlWidth:100,translateType:'IF_FLAG',gridField:false,allowBlank:false},
			,'FXQ009'		//	{name:'FXQ009',text:'客户或其亲属、关系密切人是否属于外国政要',resutlWidth:100,translateType:'IF_FLAG',gridField:false,allowBlank:false},
			,'FXQ007'		//	{name:'FXQ007',text:'客户在我行办理的业务包括',resutlWidth:100,translateType:'FXQ007',multiSelect:true,gridField:false,allowBlank:false},
			//代理人信息
			,'AGENT_NAME'		//	{name:'AGENT_NAME',text:'代理人姓名',resutlWidth:100,gridField:false},
			,'AGENT_NATION_CODE'		//	{name:'AGENT_NATION_CODE',text:'代理人国籍',resutlWidth:100,gridField:false,translateType:'XD000025'},
			,'AGE_IDENT_TYPE'		//	{name:'AGE_IDENT_TYPE',text:'代理人证件类型',resutlWidth:100,gridField:false},
			,'AGE_IDENT_NO'		//	{name:'AGE_IDENT_NO',text:'代理人证件号码',resutlWidth:100,gridField:false},
			,'TEL'		//	{name:'TEL',text:'代理人联系方式',resutlWidth:100,gridField:false},
			//企业反洗钱指标
			,'FXQ021'		//	{name:'FXQ021',text:'与客户建立业务关系的渠道',gridField:false,translateType:'FXQ21006',allowBlank:false},
			,'FXQ022'		//	 {name:'FXQ022',text:'是否在规范证券市场上市',gridField:false,translateType:'IF_FLAG',allowBlank:false},
		    ,'FXQ023'		//	{name:'FXQ023',text:'客户的股权或控制权结构',gridField:false,translateType:'FXQ023',allowBlank:false},
		    ,'FXQ024'		//	 {name:'FXQ024',text:'客户是否存在隐名股东或匿名股东',gridField:false,translateType:'IF_FLAG',allowBlank:false},
		    ,'FXQ025'		//	{name:'FXQ025',text:'客户办理的业务(对公)',gridField:false,translateType:'FXQ025',multiSelect:true,allowBlank:false},
		    ,'FLAG'		//	{name:'FLAG',gridField:false,hidden:true},
		    //20160308 新增三项数据
		    ,'IF_ORG_SUB_TYPE_PER'		//	{name:'IF_ORG_SUB_TYPE_PER',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG',multiSelect:true},
		    ,'IF_ORG_SUB_TYPE_ORG'		//	{name:'IF_ORG_SUB_TYPE_ORG',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG',multiSelect:true},
		    ,'DQSH024'		//	{name:'DQSH024',text:'客户是否涉及反洗钱黑名单',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
		    ,'CUST_GRADE_FP'// 	复评等级
		    ,'ENT_SCALE_CK'//--企业规模
            ,'IN_CLL_TYPE'//--行业分类
            ,'INSTRUCTION'//调整风险等级说明
           ]
		)
	});
    /**
     * 企业
     */
    
    
    
    var infoORGForm = new Ext.FormPanel({		
		frame : true,
			items : [
				       {
							layout : 'column',//横排列
							
						    items:[
						               {
					                       columnWidth : .5,
					                        layout : 'form',//竖向排列
					                        items :[
					                                {xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
					                                ,{xtype: 'textfield',name : 'CORE_NO', fieldLabel : '核心客户号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
					                                ,{name : 'CUST_TYPE', fieldLabel : '客户类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlXD000080Store,resizable : true,valueField : 'key',displayField : 'value',
					                                    mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
					                                ,{name : 'ENT_SCALE_CK', fieldLabel : '企业规模',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value',
					                                    mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
					
					                                ]
					                   },
					                   {
					                       columnWidth : .5,
					                        layout : 'form',
					                        items :[
					                                {name : 'CUST_NAME', fieldLabel : '客户名称',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
					                                ,{name : 'NATION_CODE', fieldLabel : '注册地',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
					                                    mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
					                                ,{xtype : 'datefield',name : 'BUILD_DATE',fieldLabel : '成立日期',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly'}
					                                ,{name : 'IN_CLL_TYPE', fieldLabel : '行业分类',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
					                                    mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
					
					                                ]
					                   },
									{
						  
						        	   columnWidth : .5,
										layout : 'form',
										items :[
												{name : 'IDENT_TYPE1', fieldLabel : '证证件类型1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												,{name : 'INDENT_NO1', fieldLabel : '证件号1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												,{name : 'IDENT_EXPIRED_DATE1', fieldLabel : '证件1到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												]
						           },
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'IDENT_TYPE2', fieldLabel : '证件类型2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										       ,{name : 'INDENT_NO2', fieldLabel : '证件号2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{name : 'IDENT_EXPIRED_DATE2', fieldLabel : '证件2到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										         ]
						           }
						           ,
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'FLAG_AGENT', fieldLabel : '是否为代理开户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										        ,{name : 'AGE_IDENT_TYPE', fieldLabel : '代理人证件类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{name : 'AGE_IDENT_NO', fieldLabel : '代理人证件号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										         ]
						           },
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'AGENT_NAME', fieldLabel : '代理人姓名',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{xtype : 'combo',name : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
										        ,{name : 'TEL', fieldLabel : '代理人联系方式',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										          ]
						           }
						           
						           ,
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										    	{name : 'IF_ORG_SUB_TYPE_ORG', fieldLabel : '客户是否为自贸区客户 ',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlXD000080Store,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										    	,{name : 'FXQ021', fieldLabel : '与客户建立业务关系的渠道',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ021Store,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										      ,{xtype : 'combo',name : 'FXQ023',fieldLabel : '客户的股权或控制权结构',store : zlFXQ023Store,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										        ,{xtype : 'combo',name : 'CUST_GRADE',fieldLabel : '调整前等级等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										        ,{name : 'CUST_GRADE_FP', fieldLabel : '调整后等级等级',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										       
										          ]
						           }
						           ,
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										          
											        {xtype : 'lovcombo',name : 'FXQ025',multi: true,multiSeparator:',',fieldLabel : '客户办理的业务',store : zlFXQ007Store,resizable : true,valueField : 'key',displayField : 'value',
											        	mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										           , {xtype : 'combo',name : 'FXQ022',fieldLabel : '客户是否在规范证券市场上市',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											       ,{xtype : 'combo',name : 'FXQ024',fieldLabel : '客户是否存在隐名股东或匿名股东',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,readOnly:true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											       ,{xtype : 'combo',name : 'FXQ008',fieldLabel : '客户是否涉及风险提示信息或权威媒体报道信息',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										          ]
						           }
						           
						    ]}
				       
				       ,{
			        	   columnWidth : .95,
							layout : 'form',
							items :[
							        {xtype : 'textarea',fieldLabel : '调整风险等级说明',name : 'INSTRUCTION',maxLength: 100,anchor : '95%',readOnly:true,cls:'x-readOnly'}
							        ]
			           }
					
					]
	});

    /**
     * 个人
     */
    var infoForm = new Ext.FormPanel({		
		frame : true,
			items : [
				       {
							layout : 'column',//横排列
							
						    items:[
						           {
						        	   columnWidth : .5,
										layout : 'form',//竖向排列
										items :[
												{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
												,{xtype: 'textfield',name : 'CORE_NO', fieldLabel : '核心客户号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
												,{xtype : 'datefield',name : 'BIRTHDAY',fieldLabel : '出生日期',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												]
						           },
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'CUST_NAME', fieldLabel : '客户名称',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												,{xtype : 'combo',name : 'CITIZENSHIP',fieldLabel : '国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												,{xtype: 'textfield',name : 'CAREER_TYPE', fieldLabel : '职业',readOnly:true,cls:'x-readOnly',anchor : '90%'}	
												]
						           },
						           {
						  
						        	   columnWidth : .5,
										layout : 'form',
										items :[
												{name : 'IDENT_TYPE1', fieldLabel : '证证件类型1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												,{name : 'INDENT_NO1', fieldLabel : '证件号1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												,{name : 'IDENT_EXPIRED_DATE1', fieldLabel : '证件1到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
												]
						           },
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'IDENT_TYPE2', fieldLabel : '证件类型2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										       ,{name : 'INDENT_NO2', fieldLabel : '证件号2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{name : 'IDENT_EXPIRED_DATE2', fieldLabel : '证件2到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										         ]
						           }
						           ,
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'FLAG_AGENT', fieldLabel : '是否为代理开户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										        ,{name : 'AGE_IDENT_TYPE', fieldLabel : '代理人证件类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{name : 'AGE_IDENT_NO', fieldLabel : '代理人证件号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										         ]
						           },
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										        {name : 'AGENT_NAME', fieldLabel : '代理人姓名',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{xtype : 'combo',name : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
										        ,{name : 'TEL', fieldLabel : '代理人联系方式',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										          ]
						           }
						           
						           ,
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										    	{name : 'CUST_TYPE', fieldLabel : '客户类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlXD000080Store,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										    	,{xtype : 'combo',name : 'FXQ008',fieldLabel : '客户是否涉及风险提示信息或权威媒体报道信息',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										        ,{xtype : 'combo',name : 'CUST_GRADE',fieldLabel : '调整前风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										        ,{name : 'CUST_GRADE_FP', fieldLabel : '调整后风险等级',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											     
										          ]
						           }
						           ,
						           {
						        	   columnWidth : .5,
										layout : 'form',
										items :[
										          
											        {xtype : 'lovcombo',name : 'FXQ007',multi: true,multiSeparator:',',fieldLabel : '客户办理的业务',store : zlFXQ007Store,resizable : true,valueField : 'key',displayField : 'value',
											        	mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										           , {xtype : 'combo',name : 'FXQ009',fieldLabel : '客户或其亲属、关系密切人等是否属于外国政要',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
													mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
										           ,{name : 'IF_ORG_SUB_TYPE_PER', fieldLabel : '客户是否为自贸区客户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											     
										          ]
						           }
						           
						    ]} ,{
					        	   columnWidth : .95,
									layout : 'form',
									items :[
									        {xtype : 'textarea',fieldLabel : '调整风险等级说明',name : 'INSTRUCTION',maxLength: 100,anchor : '95%',readOnly:true,cls:'x-readOnly'}
									        ]
					           }
					
					
					]
	});
    
    
    

   
    	debugger;
    if(custType=='2'){
    	var bussFieldSetGrid= new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息-个人',
		    items:[infoForm]
    	});
    }else{
    	var bussFieldSetGrid= new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息-企业',
		    items:[infoORGForm]
    	});
    }
     
    
    /**
     *  流程意见列表 --我的流程意见
     */
	var EchainPanel = new Mis.Echain.EchainPanel({
		instanceID:instanceid,
		nodeId:nodeid,
		nodeName:curNodeObj.nodeName,
		fOpinionFlag:curNodeObj.fOpinionFlag,
		approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
		WindowIdclode:curNodeObj.windowid,
		callbackCustomFun:'3_a10##1'
	});
	 //********************打印预览开始*************************
    var printPerHis = function() {
    	debugger;
        
        //获取-- 反洗钱指标查看面板中fxqIndexInfoPanel
        var cond = infoForm.getForm().getValues();
        var custId=cond.CUST_ID;
        var custName=cond.CUST_NAME;
        var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
        /*if (roleCodes != null && roleCodes != "") {

            var roleArrs = roleCodes.split('$');
            roleCodes="";
            for ( var i = 0; i < roleArrs.length; i++) {
                if (roleArrs[i] == "R115") {// 合规处反洗钱部经办
                    roleCodes+=roleArrs[i]+"zl";
                    }
                }
        }*/
        var taskMgr = parent.Wlj ? parent.Wlj.TaskMgr : undefined;
        var p = parent;
        for ( var i = 0; i < 10 && !taskMgr; i++) {
            p = p.parent;
            taskMgr = p.Wlj ? p.Wlj.TaskMgr : undefined;
        }
        if (taskMgr.getTask('task_print_1')) {
            taskMgr.getTask('task_print_1').close();
        }
        var turl = '?custId=' + custId+'&custName='+custName+'&roleCodes='+roleCodes+'&instanceid='+instanceid;//+'&UPDATE_ITEM='+cond.UPDATE_ITEM+'&UPDATE_USER='+cond.UPDATE_USER+'&START_DATE='+cond.START_DATE+'&END_DATE='+cond.END_DATE;
        turl +=
        	
        	
        	'&CUST_ID='+cond.CUST_ID+	//客户编号
        	'&CUST_NAME='+cond.CUST_NAME+	//客户名称
        	'&CORE_NO='+cond.CORE_NO+	//核心客户号
        	'&CITIZENSHIP='+cond.CITIZENSHIP+	//国籍
        	'&BIRTHDAY='+cond.BIRTHDAY+	//出生日期

        	   
        	   
        	'&CAREER_TYPE='+cond.CAREER_TYPE+	//职业
        	'&IDENT_TYPE1='+cond.IDENT_TYPE1+	//证件类型1
        	'&IDENT_TYPE2='+cond.IDENT_TYPE2+	//证件类型2
        	'&INDENT_NO1='+cond.INDENT_NO1+	//证件号1
        	'&INDENT_NO2='+cond.INDENT_NO2+	//证件号2
        	          
        	 					
        	 	 
          				
        	'&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+	//证件1到期日
        	'&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+	//证件2到期日
        	'&FLAG_AGENT='+cond.FLAG_AGENT+	//是否为代理开户
        	'&AGENT_NAME='+cond.AGENT_NAME+	//代理人姓名
        	'&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+	//代理人证件类型
        	
        	'&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+	//代理人国籍
        	'&AGE_IDENT_NO='+cond.AGE_IDENT_NO+	//代理人证件号码
        	'&TEL='+cond.TEL+	//代理人联系方式
        	'&CUST_TYPE='+cond.CUST_TYPE+	//客户类型
        	'&FXQ008='+cond.FXQ008+	//客户是否涉及风险提示信息或权威媒体报道信息
        	
        						 
        			 
        					 
        	'&CUST_GRADE='+cond.CUST_GRADE+	//系统预评
        	'&FXQ007='+cond.FXQ007+	//客户办理的业务
        	'&FXQ009='+cond.FXQ009+	//客户或其亲属、关系密切人等是否属于外国政要
        	'&IF_ORG_SUB_TYPE_PER='+cond.IF_ORG_SUB_TYPE_PER+	//客户是否为自贸区客户
        	
        
        	'&CUST_GRADE_FP='+cond.CUST_GRADE_FP+	//复评等级
        	'&INSTRUCTION='+cond.INSTRUCTION+//调整风险等级说明
        		"";
        	

        var tempApp = parent._APP ? parent._APP : parent.parent._APP;
        tempApp.openWindow({
            name : '打印预览',
            action : basepath
            //+'/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyTask.jsp'
            +'/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyAdjust.jsp'
                    + turl,
            resId : 'task_print_1',
            id : 'task_print_1',
            serviceObject : false
        });
    };


    // ********************打印预览结束**************************

    /********************打印预览--企业开始************/
    var printORGHis = function() {
        //获取-- 反洗钱指标查看面板中fxqIndexInfoPanel
        var cond = infoORGForm.getForm().getValues();
        var custId=cond.CUST_ID;
        var custName=cond.CUST_NAME;
        var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
        var taskMgr = parent.Wlj ? parent.Wlj.TaskMgr : undefined;
        var p = parent;
        for ( var i = 0; i < 10 && !taskMgr; i++) {
            p = p.parent;
            taskMgr = p.Wlj ? p.Wlj.TaskMgr : undefined;
        }
        if (taskMgr.getTask('task_print_1')) {
            taskMgr.getTask('task_print_1').close();
        }
        var turl = '?custId=' + custId+'&custName='+custName+'&roleCodes='+roleCodes+'&instanceid='+instanceid;//+'&UPDATE_ITEM='+cond.UPDATE_ITEM+'&UPDATE_USER='+cond.UPDATE_USER+'&START_DATE='+cond.START_DATE+'&END_DATE='+cond.END_DATE;
        turl +=
        	'&CUST_ID='+cond.CUST_ID+	//CUST_ID	客户编号	
        	'&CUST_NAME='+cond.CUST_NAME+	//CUST_NAME	客户名称
        	'&CORE_NO='+cond.CORE_NO+	//CORE_NO	核心客户号
        	'&NATION_CODE='+cond.NATION_CODE+	//NATION_CODE	注册地
        	'&CUST_TYPE='+cond.CUST_TYPE+	//CUST_TYPE	客户类型
        	'&BUILD_DATE='+cond.BUILD_DATE+	//BUILD_DATE	成立日期
        	'&ENT_SCALE_CK='+cond.ENT_SCALE_CK+	//ENT_SCALE_CK	企业规模
        	'&IN_CLL_TYPE='+cond.IN_CLL_TYPE+	//IN_CLL_TYPE	行业分类
        	
        	'&IDENT_TYPE1='+cond.IDENT_TYPE1+	//IDENT_TYPE1	证证件类型1			
        	'&IDENT_TYPE2='+cond.IDENT_TYPE2+	//IDENT_TYPE2	证件类型2
        	'&INDENT_NO1='+cond.INDENT_NO1+	//INDENT_NO1  证件号1  
        	'&INDENT_NO2='+cond.INDENT_NO2+	//INDENT_NO2	证件号2
        	'&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+	//IDENT_EXPIRED_DATE1	证件1到期日
        	'&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+	//IDENT_EXPIRED_DATE2	证件2到期日
        	
        	
        	'&FLAG_AGENT='+cond.FLAG_AGENT+	//FLAG_AGENT	是否为代理开户
        	'&AGENT_NAME='+cond.AGENT_NAME+	//AGENT_NAME	代理人姓名
        	'&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+	//AGE_IDENT_TYPE	代理人证件类型
        	'&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+	//AGENT_NATION_CODE	代理人国籍
        	'&AGE_IDENT_NO='+cond.AGE_IDENT_NO+	//AGE_IDENT_NO	代理人证件号码
        	'&TEL='+cond.TEL+	//TEL	代理人联系方式
        	
        	'&IF_ORG_SUB_TYPE_ORG='+cond.IF_ORG_SUB_TYPE_ORG+	//IF_ORG_SUB_TYPE_ORG	客户是否为自贸区客户
        	'&FXQ025='+cond.FXQ025+	//FXQ025	客户在我行办理的业务包括
        	'&FXQ021='+cond.FXQ021+	//FXQ021	与客户建立业务关系的渠道
        	'&FXQ023='+cond.FXQ023+	//FXQ023  客户的股权或控制权结构  
        	'&FXQ022='+cond.FXQ022+	//FXQ022	客户是否在规范证券市场上市  	
        	'&CUST_GRADE='+cond.CUST_GRADE+	//CUST_GRADE	系统预评等级	
        	'&FXQ024='+cond.FXQ024+	//FXQ024	客户是否存在隐名股东或匿名股东
        	'&CUST_GRADE_FP='+cond.CUST_GRADE_FP+	//CUST_GRADE_FP	复评等级	
        	'&FXQ008='+cond.FXQ008+	//FXQ008	客户是否涉及风险提示信息或权威媒体报道信息
        	'&INSTRUCTION='+cond.INSTRUCTION+//调整风险等级说明
        		"";
        	
        var tempApp = parent._APP ? parent._APP : parent.parent._APP;
        tempApp.openWindow({
            name : '打印预览',
            action : basepath
            +'/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyAdjustORG.jsp'
                    + turl,
            resId : 'task_print_1',
            id : 'task_print_1',
            serviceObject : false
        });
    };
/********************打印--企业结束************/

	
	
	var view = new Ext.Panel( {
		renderTo : 'viewEChian',
		 frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [bussFieldSetGrid,EchainPanel]
		,tbar:  [{
		text : '打印预览',
		//id : 'fxqPrint',
		handler : function() {
			debugger;
			if(custType=='2'){
				printPerHis();
			}else if(custType=='1'){
				printORGHis();
			}
		}
		}]

	});
			
	store.load({params : {
		id:id
    },
    callback:function(){
    	loadFormData();    	
	}});
	
	function loadFormData(){
		if(custType=="1"){
			infoORGForm.getForm().loadRecord(store.getAt(0));
		}else if (custType="2"){

			infoForm.getForm().loadRecord(store.getAt(0));
		}
		 function getFormInput(s) {  
	            s.add(infoORGForm);  
	    }; 
	
	}
	
	  
	    
	    
	    
});
