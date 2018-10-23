Ext.onReady(function() {
	var eGoldstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=IF_FLAG'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	   
	 var simple = new Ext.FormPanel({
	        frame:true,
			title : '<span style="font-weight:normal">分类查询</span>',
	        bodyStyle:'padding:5px 5px 0',
	        autoScroll : true,
	        width: 800,
	        items: [{
				autoHeight:true,
				xtype : 'fieldset',
				title : '分类查询条件',
				titleCollapse : true,
				collapsible : true,
				//labelWidth : 120,
				items : [ {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg1',
//						name :'cgn1',
						fieldLabel : 'Multi-Column (horizontal)',
				
						items : [{
							id : 'cgb1',
							boxLabel : '存款',
							inputValue:'nodeppubfunds',
							name : 'cb-auto-3-1'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum1',
							xtype : 'checkbox',
							boxLabel : '本月日均',
							name:'sum1',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate1',
							xtype : 'checkbox',
							boxLabel : '本季日均',
							format : 'Y-m-d',
							name:'startDate1',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate1',
							xtype : 'checkbox',
							boxLabel : '累计日均',
							format : 'y-m-d',
							name:'endDate1',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'interestRate1',
							xtype : 'checkbox',
							boxLabel : '本日余额',
							name:'interestRate1',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					} ]
				},{
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg2',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb2',
							boxLabel : '贷款',
							inputValue:'deppubfunds',
							name : 'cb-auto-3-2'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum2',
							xtype : 'checkbox',
							boxLabel : '本月日均',
							//hidden : true,
							name:'sum2',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate2',
							xtype : 'checkbox',
							boxLabel : '本季日均',
							//hidden : true,
							format : 'Y-m-d',
							name:'startDate2',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate2',
							xtype : 'checkbox',
							boxLabel : '累计日均',
							format : 'Y-m-d',
							//hidden : true,
							name:'endDate2',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'interestRate2',
							xtype : 'checkbox',
							boxLabel : '本日余额',
							//hidden : true,
							name:'interestRate2',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					} ]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg3',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb3',
							boxLabel : '贴现',
							inputValue:'interbankdeposits',
							name : 'cb-auto-3-3'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum3',
							xtype : 'checkbox',
							boxLabel : '本月日均',
							//hidden : true,
							name:'sum3',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate3',
							xtype : 'checkbox',
							boxLabel : '本季日均',
							//hidden : true,
							format : 'Y-m-d',
							name:'startDate3',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate3',
							xtype : 'checkbox',
							boxLabel : '累计日均',
							format : 'Y-m-d',
							//hidden : true,
							name:'endDate3',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'interestRate3',
							xtype : 'checkbox',
							boxLabel : '本日余额',
							//hidden : true,
							name:'interestRate3',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					} ]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg4',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb4',
							boxLabel : '银行承兑',
							inputValue:'pubnopledgepureloan',
							name : 'cb-auto-3-4'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum4',
							xtype : 'checkbox',
							boxLabel : '本日余额',
							//hidden : true,
							name:'sum4',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate4',
							xtype : 'checkbox',
							boxLabel : '本季签发',
							format : 'Y-m-d',
							//hidden : true,
							name:'startDate4',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate4',
							xtype : 'checkbox',
							boxLabel : '累计签发',
							//hidden : true,
							format : 'Y-m-d',
							name:'endDate4',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg5',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb5',
							boxLabel : '信用证',
							inputValue:'pubpledgepureloan',
							name : 'cb-auto-3-5'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum5',
							xtype : 'checkbox',
							boxLabel : '本日余额',
							//hidden : true,
							name:'sum5',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate5',
							xtype : 'checkbox',
							boxLabel : '本季签发',
							format : 'Y-m-d',
							//hidden : true,
							name:'startDate5',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate5',
							xtype : 'checkbox',
							boxLabel : '累计签发',
							//hidden : true,
							format : 'Y-m-d',
							name:'endDate5',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg6',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb6',
							boxLabel : '国际业务结算量',
							inputValue:'discount',
							name : 'cb-auto-3-6'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum6',
							xtype : 'checkbox',
							boxLabel : '本月累计',
							//hidden : true,
							name:'sum6',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate6',
							xtype : 'checkbox',
							boxLabel : '本季累计',
							//hidden : true,
							format : 'Y-m-d',
							name:'startDate6',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate6',
							xtype : 'checkbox',
							boxLabel : '本年累计',
							format : 'Y-m-d',
							//hidden : true,
							name:'endDate6',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}]
				},{
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg7',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb7',
							boxLabel : '累计结算量',
							inputValue:'issuedpromissory',
							name : 'cb-auto-3-7'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum7',
							xtype : 'checkbox',
							boxLabel : '本月累计',
							//hidden : true,
							name:'sum7',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate7',
							xtype : 'checkbox',
							boxLabel : '本季累计',
							//hidden : true,
							format : 'Y-m-d',
							name:'startDate7',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate7',
							xtype : 'checkbox',
							boxLabel : '本年累计',
							//hidden : true,
							format : 'Y-m-d',
							name:'endDate7',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}]
				},{
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg8',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb8',
							boxLabel : '电子银行结算量',
							inputValue:'openlettercredit',
							name : 'cb-auto-3-8'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum8',
							xtype : 'checkbox',
							boxLabel : '本月累计',
							//hidden : true,
							name:'sum8',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate8',
							xtype : 'checkbox',
							boxLabel : '本季累计',
							format : 'Y-m-d',
							//hidden : true,
							name:'startDate8',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate8',
							xtype : 'checkbox',
							boxLabel : '本年累计',
							format : 'Y-m-d',
							//hidden : true,
							name:'endDate8',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}]
				},{
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					items : [{
						columnWidth : .20,
						xtype : 'checkboxgroup',
						id : 'cg9',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [{
							id : 'cgb9',
							boxLabel : '中间业务收入',
							inputValue:'lettersguarantee',
							name : 'cb-auto-3-9'
							
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'sum9',
							xtype : 'checkbox',
							boxLabel : '本月累计',
							//hidden : true,
							name:'sum9',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'startDate9',
							xtype : 'checkbox',
							boxLabel : '本季累计',
							//hidden : true,
							format : 'Y-m-d',
							name:'startDate9',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ {
							id : 'endDate9',
							xtype : 'checkbox',
							boxLabel : '本年累计',
							//hidden : true,
							format : 'Y-m-d',
							name:'endDate9',
							value:' ',
							labelStyle: 'text-align:right;',
							anchor : '95%'
						}]
					}]
				}]
			}]
	    });
	    
		var classifyQWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 1000,
				height : 420,
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [simple],
				buttons : [
						{
							text : '查询',
							handler : function() {
								var conditionStr1 =  simple.getForm().getValues(false);
								store.baseParams={
										'condition':Ext.encode(conditionStr1)								
						};
								store.reload({
									  params : {
		                                   start : 0,
		                                   limit : bbar.pageSize
									  
									  }}); 
								classifyQWindow.hide();
								 Ext.getCmp('exportbatten').formPanel=simple;
							}
						}, {
							text : '重置',
							handler : function() {
								simple.getForm().reset();
							}
						}, {
							text : '关闭',
							handler : function() {
								classifyQWindow.hide();
							}
						} ]
			});
	
		var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			region: 'north',
		    title: "客户经理管理->客户经理小组管理->维护客户信息查询->机构汇总", 
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			height : 97,
				layout : 'column',
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							items : [{
								fieldLabel : '数据日期',
								name : 'dataDate',
								xtype : 'datefield',
                                format:'Y-m-d',
								labelStyle: 'text-align:right;',
								anchor : '90%'
								
							}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth: 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Com.yucheng.bcrm.common.OrgField({
								searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								fieldLabel : '所属机构',
								labelStyle : 'text-align:right;',
								id : 'jigouhao', //放大镜组件ID，用于在重置清空时获取句柄
								name : 'CUST_ORG', 
								hiddenName: 'instncode',   //后台获取的参数名称
								anchor : '90%',
								checkBox:true //复选标志
							})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Ext.form.ComboBox({
								hiddenName : 'MARKET_FLG',
								fieldLabel : '是否上市公司',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : eGoldstore,
								displayField : 'value',
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								emptyText:'请选择',
								typeAhead : true,
								resizable : true,
								anchor : '90%'
							})]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '所属客户经理ID',
								id: 'custMgrId',
								name : 'custMgrId',
								hidden: true,
								xtype : 'textfield',
								readOnly: true,
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
			}],
		buttons : [{
					text : '综合查询',

					handler : function() {
				        store.on('beforeload', function() {
				        	var conditionStr =  qForm.getForm().getValues(false);
				            this.baseParams = {
				                    "condition":Ext.encode(conditionStr)
				                    
				            };
					});
						store.load({      
							  params : {
                                   start : 0,
                                   limit : bbar.pageSize}});     
				
				   }}, {
					text : '分类查询',
						handler : function() {
							classifyQWindow.show();
					 Ext.getCmp('CUST_ZH_NAME').customerId;
						}
					},{
					text : '重置',
					     handler : function() {
					    	 qForm.getForm().reset();
					    	 Ext.getCmp('jigouhao').setValue('');
					    	 Ext.getCmp('CUST_MANAGER').setValue('');
						}
					}]
		});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, 
	        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
		    {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true},
		    {header : '客户维护人',dataIndex : 'MGR_NAME',width : 150,hidden : true ,sortable : true},
		    {header : '一级分行',dataIndex : 'supbrId',width : 200,sortable : true},
		    {header : '二级分行',width : 200,sortable : true},
		    {header : '开户网点',dataIndex : 'INSTITUTION_NAME',sortable : true},
		    {header : '所属机构',dataIndex : 'INSTITUTION_NAME',sortable : true},
		    
		    {header : '开户性质',width : 200,sortable : true},
		    {header : '开户时间',width : 200,sortable : true},
		    {header : '销户时间',width : 200,sortable : true},
		    
		    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 150,sortable : true},
		    //{header : '客户类型',dataIndex : 'custTyp',width : 200,sortable : true,hidden:true},
		   
		    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 200,hidden : true ,sortable : true},
		    {header : '客户网银状态',dataIndex : 'isEgoldCustOra',width : 150,sortable : true},

		    {header : '行业门类',dataIndex:'HY_CLASS_ORA',width : 200,sortable : true},
		    {header : '组织类别',dataIndex:'ORGTYPE_ORA',width : 200,sortable : true},
		    {header : '所有制',dataIndex:'ORGINFO_ORA',width : 200,sortable : true},
		    {header : '客户规模',dataIndex:'QYGM_ORA',width : 200,sortable : true},
		    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 200,sortable : true},
		    {header : '利润贡献度',dataIndex :'rotecb',width : 200,sortable : true},
		    {header : '存款余额',dataIndex :'ckbal',width : 200,sortable : true},
		    {header : '存款日均',dataIndex :'ckbalavg',width : 200,sortable : true},
		    {header : '贷款余额',dataIndex :'loanbal',width : 200,sortable : true},
		    {header : '贷款日均',dataIndex :'loanbalavg',width : 200,sortable : true},
		    {header : '承兑余额',dataIndex :'cdbal',width : 200,sortable : true},
		    {header : '其中:电票承兑余额',dataIndex :'dpcdbal',width : 200,sortable : true},
		    {header : '承兑累计',dataIndex :'cdsum',width : 200,sortable : true},
		    {header : '其中：电票承兑累计',dataIndex :'dpcdsum',width : 200,sortable : true},
		    {header : '贴现余额',dataIndex :'dptiebal',width : 200,sortable : true},
		    {header : '其中:电票贴现余额',dataIndex :'dptiebal',width : 200,sortable : true},
		    {header : '贴现累计',dataIndex :'tiexsum',width : 200,sortable : true},
		    {header : '其中：电票贴现累计',dataIndex :'dptiebal',width : 200,sortable : true},
		    {header : '客户累计结算量',dataIndex :'custsumbal',width : 200,sortable : true},
		    {header : '中间业务收入',dataIndex :'midbal',width : 200,sortable : true},
		    {header : '国际结算量',dataIndex :'nassumbal',width : 200,sortable : true},
		    {header : '电子银行结算量',dataIndex :'ebanksum',width : 200,sortable : true},
		    {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',width : 150,hidden : true ,sortable : true},
		    {header : '证件号码',dataIndex : 'certNum',width : 150,hidden : true ,sortable : true}
			]);

	/**
	 * 数据存储
	 */
	 var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/comCustomerInfo.json'
			        }),
			       reader: new Ext.data.JsonReader({
			       totalProperty : 'json.count',
			        root:'json.data'
			        }, [
						{name: 'custId',mapping :'CUST_ID'},
						{name: 'custZhName',mapping :'CUST_ZH_NAME'},
						{name: 'CERT_TYPE_ORA'},
						{name:'CUST_STAT_ORA'},
						{name: 'CUST_TYP_ORA'},
						{name: 'CUST_LEV_ORA'},
						{name:'certType',mapping: 'CERT_TYPE'},
						{name:'custStat',mapping: 'CUST_STAT'},
						{name:'custTyp',mapping: 'CUST_TYP'},
						{name:'custLev',mapping: 'CUST_LEV'},
//						{name: 'EN_ABBR'},
						{name: 'INSTITUTION_NAME'},
//						{name: 'BGN_DT'},
						{name: 'MGR_NAME'},
						{name: 'custEnName',mapping :'CUST_EN_NAME'},//英文名
						{name: 'otherName',mapping :'OTHER_NAME'},//其他名
						{name: 'certNum',mapping :'CERT_NUM'},//证件号码
						{name: 'linkPhone',mapping :'LINK_PHONE'},//联系电话
						{name: 'postNo',mapping :'POST_NO'},//邮编
						{name: 'commuAddr',mapping :'COMMU_ADDR'},//地址
						{name: 'linkUser',mapping :'LINK_USER'},//联系人
						{name:'supbrId',mapping : 'SUPBRID'},//所属分行
						{name: 'rotecb',mapping : 'ROTECB'},//利润贡献度
						{name: 'ckbal',mapping : 'CKBAL'},//存款余额
						{name:'ckbalavg',mapping : 'CKBALAVG'},//存款日均
						{name:'loanbal',mapping:'LOANBAL'},//贷款余额
						{name:'loanbalavg',mapping:'LOANBALAVG'},//贷款日均
						{name:'cdbal',mapping:'CDBAL'},//承兑余额
						{name:'dpcdbal',mapping:'DPCDBAL'},//电票承兑余额
						{name:'cdsum',mapping:'CDSUM'},//承兑累计
						{name:'dpcdsum',mapping:'DPCDSUM'},//电票承兑累计
						{name:'tiexbal',mapping:'TIEXBAL '},//贴现余额
						{name:'dptiebal',mapping:'DPTIEXBAL'},//电票贴现余额
						{name:'tiexsum',mapping:'TIEXSUM'},//贴现累计
						{name:'dptiexsum',mapping:'DPTIEXSUM'},//电票贴现累计
						{name:'custsumbal',mapping:'CUSTSUMBAL'},//客户累计结算量
						{name:'midbal',mapping:'MIDBAL'},//中间业务收入
						{name:'nassumbal',mapping:'NASSUMBAL'},//国际结算量
						{name:'ebanksum',mapping:'EBANKSUM'},//电子银行结算量
						{name:'etldate',mapping:'ETLDATE'},//数据日期
						{name:'isEgoldCust',mapping:'IS_E_GOLD_CUST'},//客户网银状态
						{name:'isEgoldCustOra',mapping:'IS_E_GOLD_CUST_ORA'},//客户网银状态
						{name:'HY_CLASS'},//行业分类
						{name:'HY_CLASS_ORA'},
						{name:'ORGTYPE'},//组织类别
						{name:'ORGTYPE_ORA'},
						{name:'ORGINFO'},//所有制
						{name:'ORGINFO_ORA'},
						{name:'QYGM'},//客户规模
						{name:'QYGM_ORA'}
						
					])
				});

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
         editable : false,
         width : 85
     });
    var number = parseInt(pagesize_combo.getValue());
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });

	// 表格实例
	var grid = new Ext.grid.GridPanel({
				title : '客户信息列表',
				frame : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	// 布局模型
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
		       layout:'border',
		       items: [qForm,grid]
	    }]
			});
}); 