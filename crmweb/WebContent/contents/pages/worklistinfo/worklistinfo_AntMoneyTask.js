/**
 * @description 反洗钱风险等级审核--流程客户显示页面js
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
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var custGrade=instanceid.split('_')[2];
	var custType=instanceid.split('_')[4];;
	var nodeid = curNodeObj.nodeid;
    var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy(
        		{
        			url:basepath+'/antMoneyTask.json?CUST_ID='+id+'&CUST_GRADE='+custGrade +'&instanceid='+instanceid 
        		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, ['CUST_ID'		//客户号
			,'CORE_NO'		//核心客户号
			,'CUST_NAME'	//客户名称
			,'CUST_TYPE'	//客户类型	
			,'IDENT_TYPE1'	//证证件类型1
			,'INDENT_NO1'	//证件号1
			,'IDENT_EXPIRED_DATE1'//证件1到期日
			,'IDENT_TYPE2'	//证件类型2
			,'INDENT_NO2'	//证件号2
			,'IDENT_EXPIRED_DATE2'		//证件2到期日
			,'CREATE_DATE'		//客户创建日期
			,'CUST_STAT'		//客户状态
			,'ORG_NAME'		//所属机构名称
			,'MGR_NAME'		//归属客户经理名称
			,'BELONG_TEAM_HEAD_NAME'	//归属team head名称（法金）
			,'CITIZENSHIP'	//国籍
			,'CAREER_TYPE'	//职业
			,'BIRTHDAY'	//出生日期
			//,'if_org_sub_type_per'	//客户是否是自贸区客户(对私)
			,'IF_ORG_SUB_TYPE_PER' //客户是否为自贸区客户(对私)
			,'BUILD_DATE'	//成立日期
			,'NATION_CODE'	//国家或地区代码
			,'if_org_sub_type_ORG'	//是否自贸区(对私)
			,'In_Cll_Type'	//行业分类
			,'ENT_SCALE_CK'	//企业规模
			,'AGENT_NAME'	//代理人姓名
			,'AGENT_NATION_CODE'	//代理人国籍
			,'AGE_IDENT_TYPE'	//代理人证件类型
			,'AGE_IDENT_NO'	//代理人证件号码
			,'TEL'	//代理人联系电话
			,'FXQ007'	//客户办理的业务(对私)
			,'FXQ008'	//是否涉及风险提示信息或权威媒体报道信息
			,'FXQ009'	//客户或其亲属、关系密切人等是否属于外国政要
			,'FXQ021'	//与客户建立业务关系的渠道
			,'FXQ022'	//是否在规范证券市场上市
			,'FXQ023'	//客户的股权或控制权结构
			,'FXQ024'	//客户是否存在隐名股东或匿名股东
			,'FXQ025'	//客户办理的业务(对公) 
			,'DQSH001'	//证件是否过期
			,'DQSH002'	//客户是否可取得联系
			,'DQSH003'	//联系时间
			,'DQSH004'	//联系人与帐户持有人的关系
			,'DQSH037'	//联系人与帐户持有人的关系说明
			,'DQSH005'	//预计证件更新时间
			,'DQSH006'	//未及时更新证件的理由
			,'DQSH007'	//客户是否无正当理由拒绝更新证件
			,'DQSH008'	//客户留存的证件及信息是否存在疑点或矛盾
			,'DQSH009'	//账户是否频繁发生大额现金交易
			,'DQSH010'	//账户是否频繁发生外币现钞存取业务
			,'DQSH011'	//账户现金交易是否与客户职业特性相符
			,'DQSH012'	//账户是否频繁发生大额的网上银行交易
			,'DQSH013'	//账户是否与公司账户之间发生频繁或大额的交易
			,'DQSH014'	//账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符
			,'DQSH015'	//账户资金是否快进快出，不留余额或少留余额
			,'DQSH016'	//账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
			,'DQSH017'	//账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
			,'DQSH018'	//账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
			,'DQSH019'	//账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
			,'DQSH020'	//账户是否频繁发生跨境交易，且金额大于1万美元
			,'DQSH021'	//账户是否经常由他人代为办理业务
			,'DQSH022'	//客户是否提前偿还贷款，且与其财务状况明显不符
			,'DQSH023'	//当前账户状态是否正常
			
			,'CURRENT_AUM'	////AUM(人民币)(20160318新增)
			,'DQSH024'	//AUM(人民币)改为  客户是否涉及反洗钱黑名单
			,'DQSH025'	//企业证件是否过期
			,'DQSH026'	//法定代表人证件是否过期
			,'DQSH027'	//联系人证件是否过期
			,'DQSH028'	//联系人的身份
			,'DQSH038'	 //联系人的身份说明
			,'DQSH029'	//账户是否与自然人账户之间发生频繁或大额的交易
			,'DQSH030'	//账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符
			,'DQSH031'	//账户是否频繁收取与其经营业务明显无关的汇款
			,'DQSH032'	//账户资金交易频度、金额是否与其经营背景相符
			,'DQSH033'	//账户交易对手及资金用途是否与其经营背景相符
			,'DQSH034'	//账户是否与关联企业之间频繁发生大额交易
			,'DQSH035'	//客户行为是否存在异常
			,'DQSH036'	//账户交易是否存在异常
			,'CUST_GRADE'	//客户反洗钱等级
			,'INSTANCEID'	//审批流程ID
			,'INSTRUCTION'	//审核结果说明
			,'CUST_GRADE_CHECK'	//定期审核等级
			,'AUDIT_END_DATE'	//审核截止日期
			,'FLAG_AGENT' 	//是否为代理开户
           ]
		)
	});
    /**
     * 企业
     */
//    debugger;
    var infoORGForm = new Ext.FormPanel({		
		frame : true,
		id : 'infoORGForm',//当前组件唯一的id
		frame: true,	// 是否渲染表单面板背景色
		labelAlign: 'middle',	// 标签对齐方式
		autoScroll:true,
		Layout:'FIT',
		items :[{
			layout : 'column',//横排列
			
		    items:[
		           {
		        	   columnWidth : .5,
						layout : 'form',//竖向排列
						items :[
								{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								,{xtype: 'textfield',name : 'CORE_NO', fieldLabel : '核心客户号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								,{name : 'BUILD_DATE', fieldLabel : '成立日期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{name : 'INDENT_NO1', fieldLabel : '证件号1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{name : 'IDENT_TYPE1', fieldLabel : '证件类型1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{name : 'IDENT_EXPIRED_DATE1', fieldLabel : '证件1到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{name : 'In_Cll_Type', fieldLabel : '行业分类',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								
								
								]
		           },
		           {
		        	   columnWidth : .5,
						layout : 'form',
						items :[
						        {name : 'CUST_NAME', fieldLabel : '客户名称',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{xtype : 'combo',name : 'NATION_CODE',fieldLabel : '注册地',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
								,{xtype : 'combo',name : 'ENT_SCALE_CK',fieldLabel : '企业规模',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}	
								,{name : 'INDENT_NO2', fieldLabel : '证件号2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{name : 'IDENT_TYPE2', fieldLabel : '证件类型2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{name : 'IDENT_EXPIRED_DATE2', fieldLabel : '证件2到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{xtype : 'combo',name : 'if_org_sub_type_ORG',fieldLabel : '客户是否为自贸区客户',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
						       
								]
		           },
 
		           {
		        	   columnWidth : .5,
						layout : 'form',
						items :[
						        {xtype : 'combo',name : 'FLAG_AGENT',fieldLabel : '是否为代理开户',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
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
		           
		    ]},
		    {
		      	layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .5,
							layout : 'form',
							items :[
							        {xtype : 'combo',name : 'FXQ021',fieldLabel : '与客户建立业务关系的渠道',store : zlFXQ021Store,resizable : true,valueField : 'key',displayField : 'value',
							        	mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
							       ,{xtype : 'combo',name : 'FXQ022',fieldLabel : '是否在规范证券市场上市',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
							        ,{xtype : 'combo',name : 'FXQ023',fieldLabel : '客户的股权或控制权结构',store : zlFXQ023Store,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
							       
							        ]
			           }
			           ,{
			        	   columnWidth : .5,
							layout : 'form',
							items :[
									{xtype : 'combo',name : 'FXQ024',fieldLabel : '客户是否存在隐名股东或匿名股东',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
							        ,{name : 'DQSH024', fieldLabel : '客户是否涉及反洗钱黑名单',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype : 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{xtype : 'combo',name : 'FXQ008',fieldLabel : '客户是否涉及风险提示信息或权威媒体报道信息',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
							       
							        ]
			           }
			           ,{
			        	   columnWidth : .95,
							layout : 'form',
							items :[
							        {xtype : 'lovcombo',name : 'FXQ025',multi: true,multiSeparator:',',fieldLabel : '客户办理的业务',store : zlFXQ025Store,resizable : true,valueField : 'key',displayField : 'value',
							        	mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
							
							        ]
			           }
			           
			           
			           
			          
		    	 
		    ]}
		     ,
		    {
		      	layout : 'column',//横排列
			    items:[
			           {
			        	   labelWidth:200,
			        	   columnWidth : .5,
							layout : 'form',
							items :[
							        {xtype : 'combo',name : 'DQSH025',fieldLabel : '企业证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							        ,{xtype : 'combo',name : 'DQSH002',fieldLabel : '客户是否可取得联系',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							      
							        ,{xtype : 'combo',name : 'DQSH028',fieldLabel : '联系人的身份',store : zlFXQ_DQSH028Store,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							       
							        ]
			           }
			           ,{
			        	   labelWidth:200,
			        	   columnWidth : .5,
							layout : 'form',
							items :[
							            //  {xtype : 'datefield',name : '',fieldLabel : '：',format:'Y-m-d',anchor : '90%'}
							       {xtype : 'combo',name : 'DQSH027',fieldLabel : '联系人证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							       ,{xtype : 'datefield',name : 'DQSH003',fieldLabel : '联系时间',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							       ,{xtype : 'combo',name : 'DQSH026',fieldLabel : '法定代表人证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							        
							       
								    ]
			           },{
			        	   labelWidth:200,
			        	   columnWidth : .95,
							layout : 'form',
							items :[
							        {xtype : 'textarea',fieldLabel : '联系人的身份说明',name : 'DQSH038',maxLength: 100,anchor : '95%',readOnly:true,cls:'x-readOnly'}
							        ]
			           }
		    	 
		    ]}
		    ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   labelWidth:200,
				        	   columnWidth : .5,
								layout : 'form',
								items :[
								        {xtype : 'datefield',name : 'DQSH005',fieldLabel : '预计证件更新时间',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH007',fieldLabel : '客户是否无正当理由拒绝更新证件',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH008',fieldLabel : '客户留存的证件及信息是否存在疑点或矛盾',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH009',fieldLabel : '账户是否频繁发生大额现金交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH032',fieldLabel : '账户资金交易频度、金额是否与其经营背景相符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH029',fieldLabel : '账户是否与自然人账户之间发生频繁或大额的交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH030',fieldLabel : '账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH015',fieldLabel : '账户资金是否快进快出，不留余额或少留余额',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH016',fieldLabel : '账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH022',fieldLabel : '客户是否提前偿还贷款，且与其财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								       
								        ]
				           }
				           ,{
				        	   labelWidth:200,
				        	   columnWidth : .5,
								layout : 'form',
								items :[
								        {xtype : 'combo',name : 'DQSH006',fieldLabel : '未及时更新证件的理由',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH023',fieldLabel : '当前账户状态是否正常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH031',fieldLabel : '账户是否频繁收取与其经营业务明显无关的汇款',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'FXQ009',fieldLabel : '客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								          ,{xtype : 'combo',name : 'DQSH033',fieldLabel : '账户交易对手及资金用途是否与其经营背景相符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH017',fieldLabel : '账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH018',fieldLabel : '账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH019',fieldLabel : '账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{xtype : 'combo',name : 'DQSH034',fieldLabel : '账户是否与关联企业之间频繁发生大额交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ,{name : 'CURRENT_AUM', fieldLabel : 'AUM(人民币)',anchor : '90%',xtype: 'textfield',readOnly:true,cls:'x-readOnly',anchor : '90%'}
									     
									    ]
				           }
			    	 
			    ]}
			    ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   	columnWidth : .5,
								layout : 'form',
								items :[
								        {xtype : 'combo',name : 'CUST_GRADE',fieldLabel : '当前客户洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,readOnly:true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
								    ]
				           }
				           ,{
				        	  	columnWidth : .5,
								layout : 'form',
								items :[
								        {xtype : 'combo',name : 'CUST_GRADE_CHECK',fieldLabel : '审核结果',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ]
				           }
				           ,{
				        	   columnWidth : .95,
								layout : 'form',
								items :[
								        {xtype : 'textarea',fieldLabel : '审核意见',name : 'INSTRUCTION',maxLength: 100,anchor : '95%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								        ]
				           }
			    	 
			    ]}
		        ]
		});
    
    
    
    
  //********************打印预览开始*************************
    var printPerHis = function() {
        //获取-- 反洗钱指标查看面板中fxqIndexInfoPanel
        var cond = infoForm.getForm().getValues();
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
        	'&CUST_ID='+cond.CUST_ID+	//客户编号
        	'&CUST_NAME='+cond.CUST_NAME+	//客户名称
        	'&CORE_NO='+cond.CORE_NO+	//核心客户号
        	'&CITIZENSHIP='+cond.CITIZENSHIP+	//国籍
        	'&BIRTHDAY='+cond.BIRTHDAY+	//出生日期
        	'&CAREER_TYPE='+cond.CAREER_TYPE+	//职业
        	'&IDENT_TYPE1='+cond.IDENT_TYPE1+	//证证件类型1
        	'&INDENT_NO2='+cond.INDENT_NO2+	//证件号2
        	'&INDENT_NO1='+cond.INDENT_NO1+	//证件号1
        	'&IDENT_TYPE2='+cond.IDENT_TYPE2+	//证件类型2
        	'&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+	//证件1到期日
        	'&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+	//证件2到期日
        	'&FLAG_AGENT='+cond.FLAG_AGENT+	//是否为代理开户
        	
        	'&IF_ORG_SUB_TYPE_PER='+cond.IF_ORG_SUB_TYPE_PER+ //客户是否为自贸区客户(对私)
        	//客户是否为自贸区客户（私有）
        	'&AGENT_NAME='+cond.AGENT_NAME+	//代理人姓名
        	'&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+	//代理人证件类型
        	'&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+	//代理人国籍
        	'&AGE_IDENT_NO='+cond.AGE_IDENT_NO+	//代理人证件号码
        	'&TEL='+cond.TEL+	//代理人联系电话
        	'&FXQ009='+cond.FXQ009+	//客户或其亲属、关系密切人等是否属于外国政要
        	'&FXQ008='+cond.FXQ008+	//客户是否涉及风险提示信息或权威媒体报道信息
        	'&FXQ007='+cond.FXQ007+	//客户办理的业务
        	'&DQSH001='+cond.DQSH001+	//证件是否过期
        	'&DQSH003='+cond.DQSH003+	//联系时间
        	'&DQSH004='+cond.DQSH004+	//联系人与帐户持有人的关系
        	'&DQSH002='+cond.DQSH002+	//客户是否可取得联系
        	
        	'&DQSH037='+cond.DQSH037+	//联系人与帐户持有人的关系说明(占据一行) 
        	
        	'&DQSH005='+cond.DQSH005+	//预计证件更新时间
        	'&DQSH006='+cond.DQSH006+	//未及时更新证件的理由
        	'&DQSH007='+cond.DQSH007+	//客户是否无正当理由拒绝更新证件
        	'&DQSH009='+cond.DQSH009+	//账户是否频繁发生大额现金交易
        	'&DQSH008='+cond.DQSH008+	//客户留存的证件及信息是否存在疑点或矛盾
        	'&DQSH010='+cond.DQSH010+	//账户是否频繁发生外币现钞存取业务
        	'&DQSH011='+cond.DQSH011+	//账户现金交易是否与客户职业特性相符
        	'&DQSH012='+cond.DQSH012+	//账户是否频繁发生大额的网上银行交易
        	'&DQSH013='+cond.DQSH013+	//账户是否与公司账户之间发生频繁或大额的交易
        	'&DQSH015='+cond.DQSH015+	//账户资金是否快进快出，不留余额或少留余额
        	'&DQSH014='+cond.DQSH014+	//账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符
        	'&DQSH018='+cond.DQSH018+	//账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
        	'&DQSH017='+cond.DQSH017+	//账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
        	'&DQSH016='+cond.DQSH016+	//账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
        	'&DQSH021='+cond.DQSH021+	//账户是否经常由他人代为办理业务
        	'&DQSH023='+cond.DQSH023+	//当前账户状态是否正常
        	'&DQSH020='+cond.DQSH020+	//账户是否频繁发生跨境交易，且金额大于1万美元
        	'&DQSH022='+cond.DQSH022+	//客户是否提前偿还贷款，且与其财务状况明显不符
        	'&DQSH019='+cond.DQSH019+	//账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
        	
            '&CURRENT_AUM='+cond.CURRENT_AUM+	//AUM(人民币)(20160318新增)
        	'&DQSH024='+cond.DQSH024+	//AUM(人民币)改为  客户是否涉及反洗钱黑名单
        	'&CUST_GRADE='+cond.CUST_GRADE+	//当前客户洗钱风险等级
        	'&CUST_GRADE_CHECK='+cond.CUST_GRADE_CHECK+	//审核结果
        	'&INSTRUCTION='+cond.INSTRUCTION+	//审核意见
        	
        		"";
        	
//        '&CUST_ID='+cond.CUST_ID+
//            '&CORE_NO='+cond.CORE_NO+
//            '&CUST_NAME='+cond.CUST_NAME+
//            '&CUST_TYPE='+cond.CUST_TYPE+
//            '&IDENT_TYPE1='+cond.IDENT_TYPE1+
//            '&INDENT_NO1='+cond.INDENT_NO1+
//            '&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+
//            '&IDENT_TYPE2='+cond.IDENT_TYPE2+
//            '&INDENT_NO2='+cond.INDENT_NO2+
//            '&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+
//            '&FLAG_AGENT='+cond.FLAG_AGENT+
//            '&AGENT_NAME='+cond.AGENT_NAME+
//            '&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+
//            '&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+
//            '&AGE_IDENT_NO='+cond.AGE_IDENT_NO+
//            '&TEL='+cond.TEL+
//            '&FXQ008='+cond.FXQ008+
//            '&CUST_GRADE='+cond.CUST_GRADE+
//            '&DQSH001='+cond.DQSH001+
//            '&DQSH002='+cond.DQSH002+
//            '&DQSH003='+cond.DQSH003+
//            '&DQSH004='+cond.DQSH004+
//            '&DQSH005='+cond.DQSH005+
//            '&DQSH006='+cond.DQSH006+
//            '&DQSH007='+cond.DQSH007+
//            '&DQSH008='+cond.DQSH008+
//            '&DQSH009='+cond.DQSH009+
//            '&DQSH010='+cond.DQSH010+
//            '&DQSH011='+cond.DQSH011+
//            '&DQSH012='+cond.DQSH012+
//            '&DQSH013='+cond.DQSH013+
//            '&DQSH014='+cond.DQSH014+
//            '&DQSH015='+cond.DQSH015+
//            '&DQSH016='+cond.DQSH016+
//            '&DQSH017='+cond.DQSH017+
//            '&DQSH018='+cond.DQSH018+
//            '&DQSH019='+cond.DQSH019+
//            '&DQSH020='+cond.DQSH020+
//            '&DQSH021='+cond.DQSH021+
//            '&DQSH022='+cond.DQSH022+
//            '&DQSH023='+cond.DQSH023+
//            '&DQSH024='+cond.DQSH024+
//            '&DQSH025='+cond.DQSH025+
//            '&DQSH026='+cond.DQSH026+
//            '&DQSH027='+cond.DQSH027+
//            '&DQSH028='+cond.DQSH028+
//            '&DQSH029='+cond.DQSH029+
//            '&DQSH030='+cond.DQSH030+
//            '&DQSH031='+cond.DQSH031+
//            '&DQSH032='+cond.DQSH032+
//            '&DQSH033='+cond.DQSH033+
//            '&DQSH034='+cond.DQSH034+
//            '&DQSH0351='+cond.DQSH0351+
//            '&DQSH0352='+cond.DQSH0352+
//            '&DQSH0361='+cond.DQSH0361+
//            '&DQSH0362='+cond.DQSH0362+
//            '&CITIZENSHIP='+cond.CITIZENSHIP+
//            '&CAREER_TYPE='+cond.CAREER_TYPE+
//            '&BIRTHDAY='+cond.BIRTHDAY+
//            '&IF_ORG_SUB_TYPE_PER='+cond.IF_ORG_SUB_TYPE_PER+
//            '&FXQ007='+cond.FXQ007+
//            '&FXQ009='+cond.FXQ009+
//            '&BUILD_DATE='+cond.BUILD_DATE+
//            '&IF_ORG_SUB_TYPE_ORG='+cond.IF_ORG_SUB_TYPE_ORG+
//            '&NATION_CODE='+cond.NATION_CODE+
//            '&ENT_SCALE_CK='+cond.ENT_SCALE_CK+
//            '&IN_CLL_TYPE='+cond.IN_CLL_TYPE+
//            '&FXQ021='+cond.FXQ021+
//            '&FXQ022='+cond.FXQ022+
//            '&FXQ023='+cond.FXQ023+
//            '&FXQ024='+cond.FXQ024+
//            '&FXQ025='+cond.FXQ025+
//            '&FXQ010='+cond.FXQ010+
//            '&FXQ012='+cond.FXQ012+
//            '&FXQ013='+cond.FXQ013+
//            '&FXQ014='+cond.FXQ014+
//            '&FXQ015='+cond.FXQ015+
//            '&FXQ016='+cond.FXQ016;
        var tempApp = parent._APP ? parent._APP : parent.parent._APP;
        tempApp.openWindow({
            name : '打印预览',
            action : basepath
            //+'/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyTask.jsp'
            +'/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyTask.jsp'
                    + turl,
            resId : 'task_print_1',
            id : 'task_print_1',
            serviceObject : false
        });
    };
    
    
    // ********************打印预览结束**************************
    
    /**
     * 个人
     */
    var infoForm = new Ext.FormPanel({		
    	id : 'infoForm',//当前组件唯一的id
    	frame: true,	// 是否渲染表单面板背景色
    	labelAlign: 'middle',	// 标签对齐方式
    	autoScroll:true,
    	//labelWidth:160,
    	Layout:'FIT',
		
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
										        {name : 'INDENT_NO2', fieldLabel : '证件号2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										        ,{name : 'IDENT_TYPE2', fieldLabel : '证件类型2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
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
										        ,{name : 'TEL', fieldLabel : '代理人联系电话',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										          ]
						           }
						           
						    ]},
						    {
						      	layout : 'column',//横排列
							    items:[
							           {
							        	   columnWidth : .5,
											layout : 'form',
											items :[
											       {xtype : 'combo',name : 'FXQ009',fieldLabel : '客户或其亲属、关系密切人等是否属于外国政要',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											        ,{name : 'DQSH024', fieldLabel : '客户是否为黑名单客户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype : 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
											         ]
							           }
							           ,{
							        	   columnWidth : .5,
											layout : 'form',
											items :[
											        {xtype : 'combo',name : 'FXQ008',fieldLabel : '客户是否涉及风险提示信息或权威媒体报道信息',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											        ,{name : 'IF_ORG_SUB_TYPE_PER', fieldLabel : '客户是否为自贸区客户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype : 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
										
											  ]	    
							           }
							           ,{
							        	   columnWidth : 8.5,
											layout : 'form',
											items :[
											        {xtype : 'lovcombo',name : 'FXQ007',multi: true,multiSeparator:',',fieldLabel : '客户办理的业务',store : zlFXQ007Store,resizable : true,valueField : 'key',displayField : 'value',
											        	mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
											  ]	    
							           }
						    	 
						    ]}
						     ,
						    {
						      	layout : 'column',//横排列
							    items:[
							           {
							        	   labelWidth:200,
							        	   columnWidth : .5,
											layout : 'form',
											items :[
											        {xtype : 'combo',name : 'DQSH001',fieldLabel : '证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
											        ,{xtype : 'combo',name : 'DQSH004',fieldLabel : '联系人与帐户持有人的关系',store :zlFXQ_DQSH004Store,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												     
											        ]
							           }
							           ,{
							        	   labelWidth:200,
							        	   columnWidth : .5,
											layout : 'form',
											items :[
											        {xtype : 'datefield',name : 'DQSH003',fieldLabel : '联系时间',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly'}
											       ,{xtype : 'combo',name : 'DQSH002',fieldLabel : '客户是否可取得联系',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
														mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
											       
												    ]
							           },{
							        	   labelWidth:200,
							        	   columnWidth : .95,
											layout : 'form',
											items :[
											        {xtype : 'textarea',name : 'DQSH037',fieldLabel : '联系人与帐户持有人的关系说明',name : 'DQSH037',maxLength: 100,anchor : '95%',readOnly:true,cls:'x-readOnly'}
											        ]
							           }
						    	 
						    ]}
						     ,
							    {
							      	layout : 'column',//横排列
								    items:[
								           {
								        	   labelWidth:200,
								        	   columnWidth : .5,
												layout : 'form',
												items :[
									 					{xtype : 'datefield',name : 'DQSH005',fieldLabel : '预计证件更新时间',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        ,{xtype : 'combo',name : 'DQSH007',fieldLabel : '客户是否无正当理由拒绝更新证件',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        ,{xtype : 'combo',name : 'DQSH008',fieldLabel : '客户留存的证件及信息是否存在疑点或矛盾',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
													     
												        ]
								           }
								           ,{
								        	   labelWidth:200,
								        	   columnWidth : .5,
												layout : 'form',
												items :[
												        {name : 'DQSH006', fieldLabel : '未及时更新证件的理由',anchor : '90%',xtype: 'textfield',readOnly:true,cls:'x-readOnly'}
												        ,{xtype : 'combo',name : 'DQSH009',fieldLabel : '账户是否频繁发生大额现金交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH010',fieldLabel : '账户是否频繁发生外币现钞存取业务',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												         
													    ]
								           }
							    	 
							    ]} 
						     ,
							    {
							      	layout : 'column',//横排列
								    items:[
								           {
								        	   labelWidth:200,
								        	   columnWidth : .5,
												layout : 'form',
												items :[
												        {xtype : 'combo',name : 'DQSH011',fieldLabel : '账户现金交易是否与客户职业特性相符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        ,{xtype : 'combo',name : 'DQSH013',fieldLabel : '账户是否与公司账户之间发生频繁或大额的交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH014',fieldLabel : '账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH017',fieldLabel : '账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												         , {xtype : 'combo',name : 'DQSH021',fieldLabel : '账户是否经常由他人代为办理业务',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH020',fieldLabel : '账户是否频繁发生跨境交易，且金额大于1万美元',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												      
												        , {xtype : 'combo',name : 'DQSH019',fieldLabel : '账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												       
												        ]
								           }
								           ,{
								        	   labelWidth:200,
								        	   columnWidth : .5,
												layout : 'form',
												items :[
												        {xtype : 'combo',name : 'DQSH012',fieldLabel : '账户是否频繁发生大额的网上银行交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH015',fieldLabel : '账户资金是否快进快出，不留余额或少留余额',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH018',fieldLabel : '账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH016',fieldLabel : '账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH023',fieldLabel : '当前账户状态是否正常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        , {xtype : 'combo',name : 'DQSH022',fieldLabel : '客户是否提前偿还贷款，且与其财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        ,{name : 'CURRENT_AUM', fieldLabel : 'AUM(人民币)',anchor : '90%',xtype: 'textfield',readOnly:true,cls:'x-readOnly'}
													       
													    ]
								           }
							    	 
							    ]}
							  ,
							    {
							      	layout : 'column',//横排列
								    items:[
								           {
								        	   	columnWidth : .5,
												layout : 'form',
												items :[
												        {xtype : 'combo',name : 'CUST_GRADE',fieldLabel : '当前客户洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
												    ]
								           }
								           ,{
								        	  	columnWidth : .5,
												layout : 'form',
												items :[
												        {xtype : 'combo',name : 'CUST_GRADE_CHECK',fieldLabel : '审核结果',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
															mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
												        ]
								           }
								           ,{
								        	   columnWidth : .95,
												layout : 'form',
												items :[
												        {xtype : 'textarea',fieldLabel : '审核意见',name : 'INSTRUCTION',maxLength: 100,anchor : '95%',readOnly:true,cls:'x-readOnly'}
												        ]
								           }
							    	 
							    ]}
					
					
					]

	});
    
    
    

   
    	
    if(custType=='2'){
    	var bussFieldSetGrid= new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息-个人',
		    items:[infoForm]});
    }else{
    	var bussFieldSetGrid= new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息-企业',
		    items:[infoORGForm]
    	});
    }
     
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
        	'&CUST_ID='+cond.CUST_ID+	//客户编号
        	'&CUST_NAME='+cond.CUST_NAME+	//客户名称
        	'&CORE_NO='+cond.CORE_NO+	//核心客户号
        	'&NATION_CODE='+cond.NATION_CODE+//注册地
        	'&BUILD_DATE='+cond.BUILD_DATE+//成立日期
        	'&ENT_SCALE_CK='+cond.ENT_SCALE_CK+//企业规模
        	'&IDENT_TYPE1='+cond.IDENT_TYPE1+	//证证件类型1
        	'&INDENT_NO2='+cond.INDENT_NO2+	//证件号2
        	'&INDENT_NO1='+cond.INDENT_NO1+	//证件号1
        	'&IDENT_TYPE2='+cond.IDENT_TYPE2+	//证件类型2
        	
        	'&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+	//证件1到期日
        	'&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+	//证件2到期日
        	'&In_Cll_Type='+cond.In_Cll_Type+//行业分类
        	'&if_org_sub_type_ORG'+cond.if_org_sub_type_ORG+	//客户是否为自贸区客户
        	'&FLAG_AGENT='+cond.FLAG_AGENT+	//是否为代理开户
        	
        	'&AGENT_NAME='+cond.AGENT_NAME+	//代理人姓名
        	'&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+	//代理人证件类型
        	'&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+	//代理人国籍
        	'&AGE_IDENT_NO='+cond.AGE_IDENT_NO+	//代理人证件号码
        	'&TEL='+cond.TEL+	//代理人联系电话
        	
        	
        	'&FXQ021='+cond.FXQ021+	//FXQ021	//与客户建立业务关系的渠道
        	'&FXQ022='+cond.FXQ022+	//FXQ022 是否在规范证券市场上市
        	'&FXQ023='+cond.FXQ023+	//FXQ023 客户的股权或控制权结构
        	'&FXQ024='+cond.FXQ024+	//FXQ024 客户是否存在隐名股东或匿名股东
        	
			
        	'&FXQ008='+cond.FXQ008+	//FXQ008 客户是否涉及风险提示信息或权威媒体报道信息
        	'&FXQ025='+cond.FXQ025+	//FXQ025 客户办理的业务
        	'&DQSH025='+cond.DQSH025+	// DQSH025 企业证件是否过期
        	'&DQSH002='+cond.DQSH002+	//DQSH002 客户是否可取得联系
        	'&DQSH028='+cond.DQSH028+	//DQSH028 联系人的身份
		   
        	'&DQSH027='+cond.DQSH027+	//DQSH027 联系人证件是否过期
        	'&DQSH003='+cond.DQSH003+	//DQSH003 联系时间
        	'&DQSH026='+cond.DQSH026+	//DQSH026 法定代表人证件是否过期
        	'&DQSH038='+cond.DQSH038+	//DQSH038  联系人的身份说明
        	'&DQSH005='+cond.DQSH005+	//DQSH005   预计证件更新时间     
		   
        	'&DQSH007='+cond.DQSH007+	//DQSH007   客户是否无正当理由拒绝更新证件 
        	'&DQSH008='+cond.DQSH008+	//DQSH008   客户留存的证件及信息是否存在疑点或矛盾   
        	'&DQSH009='+cond.DQSH009+	//DQSH009	  账户是否频繁发生大额现金交易	
        	'&DQSH032='+cond.DQSH032+	// DQSH032   账户资金交易频度、金额是否与其经营背景相符
        	'&DQSH029='+cond.DQSH029+	//DQSH029   账户是否与自然人账户之间发生频繁或大额的交易
		   
        	'&DQSH030='+cond.DQSH030+	//DQSH030	  账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符	
        	'&DQSH015='+cond.DQSH015+	//DQSH015   账户资金是否快进快出，不留余额或少留余额
        	'&DQSH016='+cond.DQSH016+	//DQSH016	  账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准	\
        	'&DQSH022='+cond.DQSH022+	//DQSH022	  客户是否提前偿还贷款，且与其财务状况明显不符	
        	'&DQSH006='+cond.DQSH006+	//DQSH006	  未及时更新证件的理由
		   
        	'&DQSH023='+cond.DQSH023+	//DQSH023   当前账户状态是否正常     
        	'&DQSH031='+cond.DQSH031+	//DQSH031	  账户是否频繁收取与其经营业务明显无关的汇款
        	'&DQSH010='+cond.DQSH010+	//DQSH010   账户是否频繁发生外币现钞存取业务		
        	'&FXQ009='+cond.FXQ009+	    //FXQ009  客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要
        	'&DQSH033='+cond.DQSH033+	//DQSH033	  账户交易对手及资金用途是否与其经营背景相符	
        	'&DQSH017='+cond.DQSH017+	//DQSH017   账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
		   
        	'&DQSH018='+cond.DQSH018+	//DQSH018   账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
        	'&DQSH019='+cond.DQSH019+	//DQSH019   账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
        	'&DQSH034='+cond.DQSH034+	//DQSH034   账户是否与关联企业之间频繁发生大额交易


        	 '&CURRENT_AUM='+cond.CURRENT_AUM+    //AUM(人民币)(20160318新增)
             '&DQSH024='+cond.DQSH024+    //AUM(人民币)改为  客户是否涉及反洗钱黑名单
        	'&CUST_GRADE='+cond.CUST_GRADE+	//CUST_GRADE 当前客户洗钱风险等级
		    
        	'&CUST_GRADE_CHECK='+cond.CUST_GRADE_CHECK+	//CUST_GRADE_CHECK  审核结果
        	'&INSTRUCTION='+cond.INSTRUCTION+	//INSTRUCTION  审核意见
        		"";
        	

        var tempApp = parent._APP ? parent._APP : parent.parent._APP;
        tempApp.openWindow({
            name : '打印预览',
            action : basepath
            +'/contents/pages/wlj/custmanager/antiMoney/printManager/printAntMoneyORGTask.jsp'
                    + turl,
            resId : 'task_print_1',
            id : 'task_print_1',
            serviceObject : false
        });
    };
/********************打印--企业结束************/
    
    
    
    
    /**
     *  流程意见列表 --我的流程意见
     */
	var EchainPanel = new Mis.Echain.EchainPanel({
		instanceID:instanceid,
		nodeId:nodeid,
		nodeName:curNodeObj.nodeName,
		fOpinionFlag:curNodeObj.fOpinionFlag,//是否需要输入流程意见和弹出按钮，，不需要加载此面板设置为false
		approvalHistoryFlag:curNodeObj.approvalHistoryFlag,//是否展示审批历史页面，一般不与流程办理同时展示
		WindowIdclode:curNodeObj.windowid,//打开的窗口主键
		callbackCustomFun:'3_a10##1'		//是否打回给固定节点，例如流程发起人等自定义打回到某一个节点，设定callbackCustomFun 参数格式为：'打回节点id##打回到的用户id##再次提交格式'， 1表示逐级提交，0表示提交给打回发起人  例如：'a_12##admin##1'

	});
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
