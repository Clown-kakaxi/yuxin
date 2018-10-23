Ext.onReady(function() {
    Ext.QuickTips.init(); 
    /****************************************************************/
    var today =  new Date();
    var year = today.getYear();
    var mon = today.getMonth()+1;
    var day = today.getDate(); 
	/***********************************************************************************/
	var remindType ;
    /*****************************************************************************************/
	
	var yxhdrecord = Ext.data.Record.create([
	                                     {name: 'marketActivityName'},
	                                     {name: 'activityCustomerName'},  
	                                     {name: 'activityStartDate'},
	                                     {name: 'planId'},
	                                     {name: 'activityOperaterName'},
	                                     {name : 'activityEndDate'},
	                                     {name: 'marketActivityAddress'},
	                                     {name : 'activityAim'},
	                                     {name: 'activityComplementCircumstance'},
	                                     {name: 'activityCustomerId'},
	                                     {name:'activityOperaterId'},
	                                     {name: 'createUser'},
	                                     {name: 'createUserid'},
	                                     {name: 'createDate'}
	                                	  ]);
     //营销活动数据
	var yxhdstore = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
											root : 'rows',
											totalProperty : 'num'
										 }, 
										 yxhdrecord
		)
	});
      var yxhdData= {
			TOTALCOUNT:1,
			rows:[{"marketActivityName":"春节节假日消费营销",
				"activityCustomerName":"南京春辉科技实业有限公司",
				"activityStartDate":"2013-02-20",
				"planId":"1876543210",
				"activityOperaterName":"营销团队1",
				"activityEndDate":"2013-03-20",
				"marketActivityAddress":" 中国 江苏 南京市 南京市太平北路",
				"activityAim":"实现200个客户新开户",
				"activityComplementCircumstance":"进行中",
				"activityCustomerId":"CNJ2013021800001",
				"activityOperaterId":"210293",
				"createUser":"系统管理员",
				"createUserid":"admin",
				"createDate":"2013-02-19"}	
			]
		};
      yxhdstore.loadData(yxhdData);
      
      //商机数据
  	var sjrecord = Ext.data.Record.create([
  		                                     {name: 'marketActivityId'},
  		                                     {name: 'marketOpportunityType'},  
  		                                     {name: 'opportunityStartDate'},
  		                                     {name: 'opportunityPlanEndDate'},
  		                                     {name: 'marketOpportunityName'},
  		                                     {name : 'aimCustomerId'},
  		                                     {name: 'opportunityContent'},
  		                                     {name : 'opportunityAnalysis'},
  		                                     {name:'operUserId'},
  		                                     {name: 'marketOpportunityStatementNew'},
  		                                     {name:'aimCustomerName'}
  		                                	  ]);
  	var sjtore = new Ext.data.Store({
  		reader : new Ext.data.JsonReader({
  											root : 'rows',
  											totalProperty : 'num'
  										 }, 
  										sjrecord
  		)
  	});
        var sjData= {
  			TOTALCOUNT:1,
  			rows:[{"marketActivityId":"营销活动1",
  				"aimCustomerName":"南京春辉科技实业有限公司",
  				"opportunityStartDate":"2013-02-20",
  				"marketOpportunityType":"客户账户异动",
  				"marketOpportunityName":"XXX销售商机",
  				"opportunityPlanEndDate":"2013-03-20",
  				"opportunityAnalysis":" 暂无",
  				"opportunityContent":"实现200个客户新开户",
  				"marketOpportunityStatementNew":"进行中",
  				"aimCustomerId":"CNJ2013021800001",
  				"activityOperaterId":"210293",
  				"operUserId":"系统管理员",
  				"createUserid":"admin",
  				"createDate":"2013-02-19"}	
  			]
  		};
        sjtore.loadData(sjData);

	/*******************************************************************************************/
    var panel2 = new Ext.FormPanel({ 
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        title : '<span style="font-weight:normal">事件式营销</span>',
        width: '100%',
        height:460,
        items: [{
            autoHeight:true,
            items :[{ layout:'column',
                buttonAlign : 'center',
                     items:[{
                         columnWidth:.3,
                         labelWidth : 100, // 标签宽度
                         defaultType : 'textfield',
                         layout: 'form',
                         items: [{
                        	 id:'msgType',
                             xtype:'combo',
                             name:'MSG_TYP',
                             hiddenName:'MSG_TYP',
                             fieldLabel:'事件式营销类型',
                             labelStyle: 'text-align:right;',
                             anchor:'100%',
                             mode:'local',
                             //editable:false,
                             triggerAction:'all',
                             resizable:true,
                             readOnly:true,
                             store: new Ext.data.Store({                 
                             autoLoad:true,
                             sortInfo:{
                                 field:'key',
                                 direction:'ASC'
                             },
                             proxy:new Ext.data.HttpProxy({
                                 url:basepath+'/lookup.json?name=MARKET_EVENT_TYP',
                                 method:'GET'
                             }),
                             reader:new Ext.data.JsonReader({
                                 root:'JSON'
                             },['key','value']),
                             fields: [
                                 'key',
                                 'value'
                             ],
                             listeners:{
                            	 'load':function(){
                            	 Ext.getCmp('msgType').setValue(Ext.getCmp('msgType').getValue());
                             }
                             }
                            }),
                            valueField:'key',
                            displayField:'value'
                         },
                         {id:'comp1',fieldLabel:'客户名称',name:'CUST_NAME',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp2',fieldLabel:'开户日',name:'KH_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp3',fieldLabel:'到期日',name:'DQ_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp8',fieldLabel:'客户名称',name:'CUST_NAME1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp10',fieldLabel:'账号',name:'ACCT_NO',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp16',fieldLabel:'开户机构',name:'ACCT_ORGNO',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp17',fieldLabel:'客户姓名',name:'MANAGER_NAME',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp21',fieldLabel:'客户名称',name:'CUST_NAME2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp25',fieldLabel:'账号',name:'ACCT_NO1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp24',fieldLabel:'借款金额',name:'JK_AMT',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp30',fieldLabel:'客户名称',name:'CUST_NAME3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp31',fieldLabel:'账号',name:'ACCT_NO2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp36',fieldLabel:'所属机构',name:'ACCT_ORGNO1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp39',fieldLabel:'开户机构',name:'ACCT_ORGNO2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp40',fieldLabel:'客户名称',name:'CUST_NAME4',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp41',fieldLabel:'客户号',name:'CUST_ID',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp42',fieldLabel:'证件类型',name:'CREDIT_TYP',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp49',fieldLabel:'客户名称',name:'CUST_NAME5',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp57',fieldLabel:'开户机构',name:'ACCT_ORGNO3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp52',fieldLabel:'借款金额',name:'JK_AMT1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp58',fieldLabel:'卡号',name:'CARD_NO',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp59',fieldLabel:'户名',name:'ACCT_NAME',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp64',fieldLabel:'开户机构',name:'ACCT_ORGNO4',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp65',fieldLabel:'账号',name:'ACCT_NO3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp66',fieldLabel:'用户姓名',name:'MANAGER_NAME1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp70',fieldLabel:'客户姓名',name:'MANAGER_NAME2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp71',fieldLabel:'开户机构',name:'ACCT_ORGNO5',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'PRODUCTID',fieldLabel:'推荐产品',name:'PRODUCTID',labelStyle:'text-align:right;',anchor:'100%'}

                         ]
                     },{
                         columnWidth:.3,
                         labelWidth : 100, // 标签宽度
                         defaultType : 'textfield',
                         layout: 'form',
                         items: [{
                             fieldLabel:'提醒到期日',
                             labelStyle: 'text-align:right;',
                             name:'MSG_END_DATE',
                             readOnly:true,
                             anchor:'100%'
                         },
                         {id:'comp4',fieldLabel:'余额',name:'ACCT_BAL',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp7',fieldLabel:'开户机构',name:'ACCT_ORGNO6',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp11',fieldLabel:'借款金额',name:'JK_AMT2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp14',fieldLabel:'余额',name:'ACCT_BAL1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp15',fieldLabel:'应缴利息',name:'YJLX',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp18',fieldLabel:'阴历生日',name:'BIRTHDAY1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp20',fieldLabel:'阳历生日',name:'BIRTHDAY2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp28',fieldLabel:'余额',name:'ACCT_BAL2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp29',fieldLabel:'未还期数',name:'WHQS',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp26',fieldLabel:'发放日',name:'FF_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp32',fieldLabel:'交易金额',name:'ACCT_AMT',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp33',fieldLabel:'账户余额',name:'ACCT_BAL3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp34',fieldLabel:'交易日期',name:'JY_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp43',fieldLabel:'证件号码',name:'CUST_ZZDM',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp44',fieldLabel:'证件签发日期',name:'ZJQF_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp45',fieldLabel:'证件到期日期',name:'ZJDQ_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp53',fieldLabel:'发放日',name:'FF_DATE1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp54',fieldLabel:'到期日',name:'DQ_DATE1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp55',fieldLabel:'余额',name:'ACCT_BAL4',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp60',fieldLabel:'透支金额',name:'TZ_AMT',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp61',fieldLabel:'最后还款日',name:'LAST_HK_DATE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp67',fieldLabel:'当前余额',name:'ACCT_BAL5',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp68',fieldLabel:'最后一笔交易日期',name:'JY_DATE1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp72',fieldLabel:'阴历生日',name:'BIRTHDAY11',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp73',fieldLabel:'阳历生日',name:'BIRTHDAY22',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'XX',fieldLabel:'推荐渠道',name:'XX',labelStyle:'text-align:right;',anchor:'100%'}
                         ]
                     },{
                         columnWidth:.3,
                         labelWidth : 100, // 标签宽度
                         defaultType : 'textfield',
                         layout: 'form',
                         items: [{
                             xtype:'textfield',
                             fieldLabel:'提醒剩余天数',
                             labelStyle: 'text-align:right;',
                             name:'MSG_LAST',
                             readOnly:true,
                             allowDecimal:false,
                             anchor:'100%'
                         },
                         {id:'comp5',fieldLabel:'联系地址',name:'ADDR',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp6',fieldLabel:'联系电话',name:'MANAGER_PHONE',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp12',fieldLabel:'发放日',name:'FF_DATE2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp13',fieldLabel:'到期日',name:'DQ_DATE2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp9',fieldLabel:'联系电话',name:'MANAGER_PHONE1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp19',fieldLabel:'联系电话',name:'MANAGER_PHONE2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp27',fieldLabel:'到期日',name:'DQ_DATE3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp22',fieldLabel:'联系电话',name:'MANAGER_PHONE3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp23',fieldLabel:'联系地址',name:'ADDR1',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp35',fieldLabel:'交易类型',name:'JY_TYP',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp37',fieldLabel:'联系地址',name:'ADDR2',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp38',fieldLabel:'联系电话',name:'MANAGER_PHONE4',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp46',fieldLabel:'签发国家',name:'QFGJ',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp47',fieldLabel:'签发机构',name:'QF_ORG',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp48',fieldLabel:'年检标识',name:'NJBZ',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp56',fieldLabel:'欠息金额',name:'QX_AMT',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp50',fieldLabel:'联系电话',name:'MANAGER_PHONE5',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp51',fieldLabel:'联系地址',name:'ADDR3',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp62',fieldLabel:'联系地址',name:'ADDR4',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp63',fieldLabel:'联系电话',name:'MANAGER_PHONE6',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp69',fieldLabel:'开户机构',name:'ACCT_ORGNO7',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'},
                         {id:'comp74',fieldLabel:'联系电话',name:'MANAGER_PHONE7',labelStyle:'text-align:right;',readOnly:true,hidden:true,anchor:'100%'}
                         ]
                     }
                ]},{layout:'column',
                    items:[{
                        columnWidth:.9,
                        labelWidth : 100, // 标签宽度
                        layout: 'form',
                        items: [{
                        fieldLabel:'提醒备注',
                        readOnly:true,
                        xtype:'textarea',
                        labelStyle: 'text-align:right;',
                        name:'MSG_REMARK',
                        height:200,
                        anchor:'100%'
                    }]}]
                }
                ]}]
        });
 // 定义详情窗口的tabPanel
	var planDetailPanel = new Ext.TabPanel({
		activeTab : 0,
		tabPosition : 'top',
		items : [ {
			title : '基本信息',
			items : [ panel2 ]
		},{
			title : '关联客户信息',
			items : [ customergrid ]
		},{
			title : '关联产品信息',
			items : [ productgrid ]
		}   ]

	});
    
    var addRoleWindow = new Ext.Window(
    {
        //layout : 'fit',
        height : 470,
        width:880,
        buttonAlign : 'center',
        draggable : true,//是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        autoScroll:true,
        closeAction : 'hide',
        // iconCls : 'page_addIcon',
        //maximizable: true,
        //maximized:true,
        collapsible : true,// 是否可收缩
        titleCollapse : true,
        border : false,
        animCollapse : true,
        pageY : 20,
        //pageX : document.body.clientWidth / 2 - 420 / 2,
        animateTarget : Ext.getBody(),
        constrain : true,
        items : [planDetailPanel],
        buttons : [
                    {
                        text : '关    闭',
                        handler : function() {
                            addRoleWindow.hide();
                        }
                    },'-',  {
        				text : '创建商机',
        				iconCls:'addIconCss',
        				handler:function(){
                    	busiOpportAddWindowInit();
                    	//	window.location.href = '../mktManage/mktChanceManage/mktChanceList.jsp';
                    	//addChanceWindow.show();
                        }
        			},{
        	            text : '生成营销活动',
        	            iconCls:'addIconCss',
        	            handler : function() {
        	            	
        				addActivityForm.form.reset();
        				addActivityProdForm.form.reset();
        				addActivityCustForm.form.reset();
        				addActivityForm.form.findField('createUser').setValue(__userId);
        				addActivityForm.form.findField('test').setValue(__userName);
        				addActivityForm.form.findField('createDate').setValue(new Date());
        				addActivityForm.form.findField('mktActiStat').setValue(1);
        				addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
        				addActivityForm.form.findField('mktActiType').setValue('推广活动');
        				addActivityForm.form.findField('mktActiMode').setValue('宣传');
        				addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
        				addActivityForm.form.findField('mktActiCost').setValue('1000');
        				addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
        				addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
        				addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
        				addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
        				addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
        				addActivityForm.form.findField('mktActiAim').setValue('推广');
        				addActivityForm.form.findField('actiRemark').setValue('无');
        						 				
        				addActivityWindow.show();

        	            }}  ]
    });
    /*****************************************************************************************/
	/***************************查询条件****************************************/
	var qForm = new Ext.form.FormPanel({
	    id:'qform',
		title : '营销管理->事件式营销->事件式营销',
		border : true,
		region : 'north',
//        autoScroll : true,
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 100,
//		width:document.body.scrollWidth-10,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [
                                {
                               	 id:'msg_type',
                                    xtype:'combo',
                                    name:'MSG_TYP',
                                    hiddenName:'MSG_TYP',
                                    fieldLabel:'事件式营销类型',
                                    labelStyle: 'text-align:right;',
                                    anchor:'85%',
                                    mode:'local',
                                    //editable:false,
                                    triggerAction:'all',
                                    resizable:true,
                                    store: new Ext.data.Store({                 
                                    autoLoad:true,
                                    sortInfo:{
                                        field:'key',
                                        direction:'ASC'
                                    },
                                    proxy:new Ext.data.HttpProxy({
                                        url:basepath+'/lookup.json?name=MARKET_EVENT_TYP',
                                        method:'GET'
                                    }),
                                    reader:new Ext.data.JsonReader({
                                        root:'JSON'
                                    },['key','value']),
                                    fields: [
                                        'key',
                                        'value'
                                    ]
                                   }),
                                   valueField:'key',
                                   displayField:'value'
                                }
                                ]
							}, {
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 100, // 标签宽度
                                defaultType : 'textfield',
                                border : false,
                                items : [{
                                    xtype:'numberfield',
                                    fieldLabel:'剩余天数',
                                    labelStyle: 'text-align:right;',
                                    name:'MSG_LAST',
                                    allowDecimal:false,
                                    anchor:'85%'
                                },{
                                    hidden:true,
                                    name:'MSG_LAST_DATE',
                                    id:'msgLastDate'
                                }]
                            }, {
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 100, // 标签宽度
                                defaultType : 'textfield',
                                border : false,
                                items : [{
                                    fieldLabel : '信息状态',
                                    hiddenName: 'MSG_STS' ,
                                    resizable:true,
                                    forceSelection : true,
                                    xtype:'combo',
                                    labelStyle: 'text-align:right;',
                                    triggerAction:'all',
                                    mode:'local',
                                    store:new Ext.data.ArrayStore({
                                        fields:['myId','displayText'],
                                        data:[['1','已阅读'],['0','未阅读']]
                                    }),
                                    valueField:'myId',
                                    displayField:'displayText',
                                    emptyText:'请选择',
                                   anchor : '85%'
                               }]
                            }, {
                                columnWidth : .25,
                                layout : 'form',
                                labelWidth : 100, // 标签宽度
                                defaultType : 'textfield',
                                border : false,
                                items : [{
                                    xtype:'datefield',
                                    fieldLabel:'提醒到期日',
                                    labelStyle: 'text-align:right;',
                                    name:'MSG_END_DATE',
                                    format:'Y-m-d',
                                    allowDecimal:false,
                                    anchor:'85%',
                                    editable:false
                                    	
                                }]
                            }]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
					    
					    if(!qForm.getForm().isValid())
					    { 
					        Ext.Msg.alert('输入有误');
					        return false;
					    }
					    var remainDay=qForm.getForm().findField('MSG_LAST').value;
					    if(remainDay!=''&&remainDay!=undefined){
					        var remindDay=new Date(year,mon-1,day+parseInt(remainDay)).format('Y-m-d');
					        Ext.getCmp('msgLastDate').setValue(remindDay);
					    }
//					    debugger;
					    var conditionStr =  qForm.getForm().getFieldValues();
                        store.on('beforeload', function() {
                            this.baseParams = {
                                    "condition":Ext.encode(conditionStr)
                            };
                            });
						store.reload({
                            params : {
                                start : 0,
                                limit : bbar.pageSize
                            }
                        });
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
					}
				}]
	});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, sm,
   {header:'ID',dataIndex:'ID',hidden:true},
   {header:'事件式营销类型',dataIndex:'MSG_TYP_ORA',sortable:true,align:'left',width:170},
   {id:'row1',header:'客户名称',dataIndex:'CUST_NAME',sortable:true,align:'left',hidden:false,width:170},
   {id:'row2',header:'客户号',dataIndex:'CUST_ID',sortable:true,align:'left'},
   {id:'row3',header:'账号',dataIndex:'ACCT_NO',sortable:true,align:'left'},
   {id:'row4',header:'客户姓名',dataIndex:'MANAGER_NAME',sortable:true,align:'left'},
   {header:'信息状态',dataIndex:'MSG_STS',sortable:true,align:'left',
       renderer:function(value){
           if(value==1)value='已阅读';
           else value='未阅读';
           return value;
       }},
   {header:'提醒剩余天数',dataIndex:'MSG_END_DATE',sortable:true,width:110,align:'right',
       renderer:function(value){
       if(value!=null&&value!=undefined){
           var today1=new Date();
           var value1=new Date(value.split('-')[0],value.split('-')[1]-1,value.split('-')[2]);
           debugger;
           value=value1.format('z')-today1.format('z')/*+(value1.format('Y')-year)*(365+today1.format('L'))*/;//提醒提前日期不可能超过一年
       }else value = '未知';
       return value;
   }},
   {header:'提醒到期日',dataIndex:'MSG_END_DATE',sortable:true,align:'left'},
   {header:'事件式营销备注',dataIndex:'MSG_REMARK',sortable:true,align:'left'},
  // {header:'推荐产品',dataIndex:'PRODUCTID',sortable:true,align:'left',width:300},
 //  {header:'推荐渠道',dataIndex:'XX',sortable:true,align:'left',width:300},


   {id:'row5', header:'开户日',dataIndex:'KH_DATE',sortable:true,align:'left'}, //new added
   {id:'row6', header:'到期日',dataIndex:'DQ_DATE',sortable:true,align:'left'},
   {id:'row29', header:'账户余额',dataIndex:'ACCT_BAL',sortable:true,align:'right',renderer: money('0,000.00' )},
   {id:'row7', header:'联系地址',dataIndex:'ADDR',sortable:true,align:'left'},
   {id:'row8', header:'联系电话',dataIndex:'MANAGER_PHONE',sortable:true,align:'left'},
   {id:'row9', header:'开户机构',dataIndex:'ACCT_ORGNO',sortable:true,align:'left'},
   {id:'row10',header:'借款金额',dataIndex:'JK_AMT',sortable:true,align:'right',renderer: money('0,000.00' )},
   {id:'row11',header:'发放日',dataIndex:'FF_DATE',sortable:true,align:'left'},
   {id:'row12',header:'应缴利息',dataIndex:'YJLX',sortable:true,align:'right',renderer: money('0,000.00' )},
   {id:'row13',header:'未还期数',dataIndex:'WHQS',sortable:true,align:'left'},
   {id:'row14',header:'欠息金额',dataIndex:'QX_AMT',sortable:true,align:'right',renderer: money('0,000.00' )},
   {id:'row30',header:'交易金额',dataIndex:'ACCT_AMT',sortable:true,align:'right',renderer: money('0,000.00' )},
   {id:'row15',header:'交易日期',dataIndex:'JY_DATE',sortable:true,align:'left'},
   {id:'row16',header:'交易类型',dataIndex:'JY_TYP',sortable:true,align:'left'},
   {id:'row17',header:'阴历生日日期',dataIndex:'BIRTHDAY1',sortable:true,align:'left'},
   {id:'row18',header:'阳历生日日期',dataIndex:'BIRTHDAY2',sortable:true,align:'left'},
   {id:'row19',header:'证件类型',dataIndex:'CREDIT_TYP',sortable:true,align:'left'},
   {id:'row20',header:'证件号码',dataIndex:'CUST_ZZDM',sortable:true,align:'left'},
   {id:'row21',header:'证件签发日期',dataIndex:'ZJQF_DATE',sortable:true,align:'left'},
   {id:'row22',header:'证件到期日期',dataIndex:'ZJDQ_DATE',sortable:true,align:'left'},
   {id:'row23',header:'签发国家',dataIndex:'QFGJ',sortable:true,align:'left'},
   {id:'row24',header:'签发机构',dataIndex:'QF_ORG',sortable:true,align:'left'},
   {id:'row25',header:'年检标识',dataIndex:'NJBZ',sortable:true,align:'left'},
   {id:'row31',header:'户名',dataIndex:'ACCT_NAME',sortable:true,align:'left'},
   {id:'row26',header:'卡号',dataIndex:'CARD_NO',sortable:true,align:'left'},
   {id:'row27',header:'透支金额',dataIndex:'TZ_AMT',sortable:true,align:'right',renderer: money('0,000.00' )},
   {id:'row28',header:'最后还款日',dataIndex:'LAST_HK_DATE',sortable:true,align:'left'}

			]);
	
    data = [
          	[1,'证件到期事件类型','圣洁科技有限公司','1','129','2012-1-1','无','推荐产品,推荐产品1，推荐产品2','渠道1']
			];
 
    
	/**
	 * 数据存储
	 */
 var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/querymarketingevent.json',
			            method:'POST',
                        failure : function(response) {
                            var resultArray = Ext.util.JSON.decode(response.status);
                            if(resultArray == 403) {
                                Ext.Msg.alert('提示', response.responseText);
                            }
                        }
			        }),
			        reader: new Ext.data.JsonReader({
			            successProperty: 'success',
			        root:'json.data',
                    totalProperty: 'json.count'
			        }, [
						{name: 'ID'},
                        {name: 'MSG_TYP'},
                        {name: 'MSG_STS'},
                        {name: 'MSG_TYP_ORA'},
                        {name: 'CUST_ID'},
                        {name: 'CUST_NAME'},
                        {name: 'CUST_NAME1',mapping:'CUST_NAME'},
                        {name: 'CUST_NAME2',mapping:'CUST_NAME'},
                        {name: 'CUST_NAME3',mapping:'CUST_NAME'},
                        {name: 'CUST_NAME4',mapping:'CUST_NAME'},
                        {name: 'CUST_NAME5',mapping:'CUST_NAME'},
                        {name: 'CUST_ZZDM'},
                        {name: 'ACCT_NO'},
                        {name: 'ACCT_NO1',mapping:'ACCT_NO'},
                        {name: 'ACCT_NO2',mapping:'ACCT_NO'},
                        {name: 'ACCT_NO3',mapping:'ACCT_NO'},
                        {name: 'ACCT_NAME'},
                        {name: 'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO1',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO2',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO3',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO4',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO5',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO6',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_ORGNO7',mapping:'ACCT_ORGNO'},
                        {name: 'ACCT_BAL'},
                        {name: 'ACCT_BAL1',mapping:'ACCT_BAL'},
                        {name: 'ACCT_BAL2',mapping:'ACCT_BAL'},
                        {name: 'ACCT_BAL3',mapping:'ACCT_BAL'},
                        {name: 'ACCT_BAL4',mapping:'ACCT_BAL'},
                        {name: 'ACCT_BAL5',mapping:'ACCT_BAL'},
                        {name: 'ACCT_AMT'},
                        {name: 'MANAGER_NAME'},
                        {name: 'MANAGER_NAME1',mapping:'MANAGER_NAME'},
                        {name: 'MANAGER_NAME2',mapping:'MANAGER_NAME'},
                        {name: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE1',mapping: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE2',mapping: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE3',mapping: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE4',mapping: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE5',mapping: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE6',mapping: 'MANAGER_PHONE'},
                        {name: 'MANAGER_PHONE7',mapping: 'MANAGER_PHONE'},
                        {name: 'EVENT_NAME'},
                        {name: 'MSG_END_DATE'},
                        {name: 'MSG_LAST'},
                        {name: 'MSG_REMARK'},
                        {name: 'USER_NO'},
                        {name: 'MSG_CRT_DATE'},
                        {name: 'READ_DATE'},
                        {name: 'USER_UNITID'},
                        {name: 'KH_DATE'},//new added
                        {name: 'DQ_DATE'},
                        {name: 'DQ_DATE1',mapping:'DQ_DATE'},
                        {name: 'DQ_DATE2',mapping:'DQ_DATE'},
                        {name: 'DQ_DATE3',mapping:'DQ_DATE'},
                        {name: 'ADDR'},
                        {name: 'ADDR1',mapping:'ADDR'},
                        {name: 'ADDR2',mapping:'ADDR'},
                        {name: 'ADDR3',mapping:'ADDR'},
                        {name: 'ADDR4',mapping:'ADDR'},
                        {name: 'ACCT_ORGNO'},
                        {name: 'KH_ORG'},
                        {name: 'JK_AMT'},
                        {name: 'JK_AMT1',mapping:'JK_AMT'},
                        {name: 'JK_AMT2',mapping:'JK_AMT'},
                        {name: 'FF_DATE'},
                        {name: 'FF_DATE1',mapping:'FF_DATE'},
                        {name: 'FF_DATE2',mapping:'FF_DATE'},
                        {name: 'YJLX'},
                        {name: 'WHQS'},
                        {name: 'QX_AMT'},
                        {name: 'JY_DATE'},
                        {name: 'JY_DATE1',mapping:'JY_DATE'},
                        {name: 'JY_TYP'},
                        {name: 'BIRTHDAY1'},
                        {name: 'BIRTHDAY2'},
                        {name: 'BIRTHDAY11',mapping:'BIRTHDAY1'},
                        {name: 'BIRTHDAY22',mapping:'BIRTHDAY2'},
                        {name: 'CREDIT_TYP'},
                        {name: 'CREDIT_NO'},
                        {name: 'ZJQF_DATE'},
                        {name: 'ZJDQ_DATE'},
                        {name: 'QFGJ'},
                        {name: 'QF_ORG'},
                        {name: 'NJBZ'},
                        {name: 'CARD_NO'},
                        {name: 'TZ_AMT'},
                        {name: 'LAST_HK_DATE'},
                        {name: 'PRODUCTID'},
                        {name: 'XX'}

					])
				});
	
// 表格工具栏
	var tbar = new Ext.Toolbar({items:[
{
	    text:'查看详细信息',
	    iconCls:'detailIconCss',
	    handler:function(){
	        viewInit();
	    }
			},'-',{
		        text:'设为已读',
		        iconCls:'optionIconCss',
		        handler:function(){
		            read();
		        }},'-',{
					text : '创建商机',
					iconCls:'addIconCss',
					handler : function() {
		        	busiOpportAddWindowInit();
					}
				},{
		            text : '生成营销活动',
		            iconCls:'addIconCss',
		            handler : function() {
		            	
					addActivityForm.form.reset();
					addActivityProdForm.form.reset();
					addActivityCustForm.form.reset();
					addActivityForm.form.findField('createUser').setValue(__userId);
					addActivityForm.form.findField('test').setValue(__userName);
					addActivityForm.form.findField('createDate').setValue(new Date());
					addActivityForm.form.findField('mktActiStat').setValue(1);
					addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
					addActivityForm.form.findField('mktActiType').setValue('推广活动');
					addActivityForm.form.findField('mktActiMode').setValue('宣传');
					addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
					addActivityForm.form.findField('mktActiCost').setValue('1000');
					addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
					addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
					addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
					addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
					addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
					addActivityForm.form.findField('mktActiAim').setValue('推广');
					addActivityForm.form.findField('actiRemark').setValue('无');
							 				
					addActivityWindow.show();

		            }}
			]});
	// 每页显示条数下拉选择框
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '20',
        //editable : false,
        width : 85
    });
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
    pagesize_combo.on("select", function(comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
        store.reload({
            params : {
                start : 0,
                limit : bbar.pageSize
            }
        });
    });
    // 分页工具栏
    var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });

	// 表格实例
	var grid = new Ext.grid.GridPanel({
//        height : document.body.scrollHeight-107,
//        width:document.body.scrollWidth-10,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	//拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        grid.setHeight(document.body.scrollHeight-107);
        grid.setWidth(document.body.scrollWidth-10);
        grid.getView().refresh();
    });
    var viewport = new Ext.Viewport({
        layout : 'fit',
        items : [ {
            layout : 'border',
            items : [qForm,grid]
        } ]
    });
    /**********************************************************/
    //根据不同的提醒类型展示不同的form
    function showForm(type){
	    	for(var i=1;i<75;i++){
	    		var comp = 'comp'+i;
//	    		addRoleWindow.findById(comp).hidden=true;
	    		addRoleWindow.findById(comp).hide();
	    	}
    	if(type!=undefined&&type!=null&&type!=''){
    		if(type==1000000001){
    			for(var i=1;i<8;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000002){
    			for(var i=8;i<17;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000003){
    			for(var i=17;i<21;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000004){
    			for(var i=21;i<30;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000005){
    			for(var i=30;i<40;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000006){
    			for(var i=40;i<49;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000007){
    			for(var i=49;i<58;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000008){
    			for(var i=58;i<65;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000009){
    			for(var i=65;i<70;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}else if(type==1000000010){
    			for(var i=70;i<75;i++){
    				var comp = 'comp'+i;
//    				addRoleWindow.findById(comp).hidden=false;
    	    		addRoleWindow.findById(comp).show();
    			}
    		}
    	}
    }
    /**********************************************************/
    grid.on('rowdblclick', function(grid, rowIndex, event) {
        viewInit();
    });
    function viewInit(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            Ext.Msg.alert('请选择一条记录!');
        } else{
        var infoRecord = grid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
                panel2.getForm().loadRecord(infoRecord);
                var value = infoRecord.data.MSG_END_DATE;
                if(value!=null&&value!=undefined){
                var today1=new Date();
                var value1=new Date(value.split('-')[0],value.split('-')[1]-1,value.split('-')[2]);
                value=value1.format('z')-today1.format('z')+(value1.format('Y')-year)*(365+today1.format('L'));//提醒提前日期不可能超过一年
                }else value = '未知';
                panel2.getForm().findField('MSG_LAST').setValue(value);
                
                //type 信息提醒类型
                var type = infoRecord.data.MSG_TYP;
                showForm(type);
//                addRoleWindow.removeAll(false);
//                addRoleWindow.add({items:[panel2]});
//                panel2.doLayout();
//                addRoleWindow.doLayout();
                panel2.getForm().findField('PRODUCTID').setValue('理财产品');
                panel2.getForm().findField('XX').setValue('中信银行');

                addRoleWindow.show();
        }}
    }
    
    function read(){
        var selectLength = grid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            Ext.Msg.alert('提示','请选择需要设为已读的记录!');
        } 
        
        else {
        	Ext.MessageBox.confirm('提示','确定设为已读吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
						return;
					}
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = grid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/workplatremindlist!read.json?idStr='+idStr,
                method : 'POST',        
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : checkResult,
                failure : checkResult
            });
            
            });
    }}
    
    function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            if(resultArray == 403) {
                Ext.Msg.alert('提示', response.responseText);
        }else{
            
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            store.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
        }
    }
    //实现选择不同事件展现不同字段
    function showColumn(remindType){
    	if(remindType!=undefined&&remindType!=null&&remindType!=''){
    		for(var i=1;i<32;i++){
    			var row = 'row'+i;
    			cm.getColumnById(row).hidden=true;
    		}
    		
    		if(remindType == 1000000001){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row5').hidden=false;
    			cm.getColumnById('row6').hidden=false;
    			cm.getColumnById('row7').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.getColumnById('row29').hidden=false;
    			cm.totalWidth=1308;
    		}else if(remindType == 1000000002){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row3').hidden=false;
    			cm.getColumnById('row10').hidden=false;
    			cm.getColumnById('row11').hidden=false;
    			cm.getColumnById('row6').hidden=false;
    			cm.getColumnById('row29').hidden=false;
    			cm.getColumnById('row12').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.totalWidth=1508;
    		}else if(remindType == 1000000003){
    			cm.getColumnById('row4').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row17').hidden=false;
    			cm.getColumnById('row18').hidden=false;
    			cm.totalWidth=1008;
    		}else if(remindType == 1000000004){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row3').hidden=false;
    			cm.getColumnById('row6').hidden=false;
    			cm.getColumnById('row7').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row10').hidden=false;
    			cm.getColumnById('row11').hidden=false;
    			cm.getColumnById('row13').hidden=false;
    			cm.getColumnById('row29').hidden=false;
    			cm.totalWidth=1508;
    		}else if(remindType == 1000000005){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row3').hidden=false;
    			cm.getColumnById('row7').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.getColumnById('row16').hidden=false;
    			cm.getColumnById('row15').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.getColumnById('row29').hidden=false;
    			cm.getColumnById('row30').hidden=false;
    			cm.totalWidth=1608;
    		}else if(remindType == 1000000006){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row2').hidden=false;
    			cm.getColumnById('row23').hidden=false;
    			cm.getColumnById('row24').hidden=false;
    			cm.getColumnById('row25').hidden=false;
    			cm.getColumnById('row19').hidden=false;
    			cm.getColumnById('row20').hidden=false;
    			cm.getColumnById('row21').hidden=false;
    			cm.getColumnById('row22').hidden=false;
    			cm.totalWidth=1508;
    		}else if(remindType == 1000000007){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row10').hidden=false;
    			cm.getColumnById('row11').hidden=false;
    			cm.getColumnById('row14').hidden=false;
    			cm.getColumnById('row29').hidden=false;
    			cm.getColumnById('row6').hidden=false;
    			cm.getColumnById('row7').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.totalWidth=1508;
    		}else if(remindType == 1000000008){
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.getColumnById('row31').hidden=false;
    			cm.getColumnById('row7').hidden=false;
    			cm.getColumnById('row28').hidden=false;
    			cm.getColumnById('row26').hidden=false;
    			cm.getColumnById('row27').hidden=false;
    			cm.totalWidth=1308;
    		}else if(remindType == 1000000009){
    			cm.getColumnById('row15').hidden=false;
    			cm.getColumnById('row29').hidden=false;
    			cm.getColumnById('row3').hidden=false;
    			cm.getColumnById('row4').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.totalWidth=1108;
    		}else if(remindType == 1000000010){
    			cm.getColumnById('row1').hidden=false;
    			cm.getColumnById('row8').hidden=false;
    			cm.getColumnById('row9').hidden=false;
    			cm.getColumnById('row17').hidden=false;
    			cm.getColumnById('row18').hidden=false;
    			cm.totalWidth=1108;
    		}
    	}else{
    		for(var i=1;i<32;i++){
    			var row = 'row'+i;
    			cm.getColumnById(row).hidden=true;
    		}
			cm.getColumnById('row1').hidden=false;
			cm.totalWidth=708;
    	}
//    	cm.syncSize() ;  //
    }
    /**********************************************************/
    var cb = Ext.getCmp('msg_type');
//    cb.addListener('select',function(){
//    	alert(cb.getValue());
//    	showColumn(cb.getValue());
//    });
    
    var msgSts = '';
    if(window.location.href.split("msgTyp=")[1]!=undefined){
    	remindType = window.location.href.split("msgTyp=")[1];
    	msgSts = 1;
    	cb.setValue(remindType);
    }
    
    store.on('beforeload',function(){
    	 showColumn(cb.getValue());
    });
    
    store.load({
        params : {
            MSG_TYP : remindType,
            MSG_STS : msgSts,
            start : 0,
            limit : bbar.pageSize
        }
    });
    
    cb.store.load({callback : function(){
    	cb.setValue(cb.getValue());
    }});
    
    
}); 