Ext.onReady(function(){

    Ext.QuickTips.init(); 
    var jjzbzyStore = new Ext.data.Store({
        restful:true,  
        autoLoad : true,
        proxy : new Ext.data.HttpProxy({url:basepath+'/economiccapitalcalculation.json'}),
        fields:['ECONOMIC_CAPITAL_OCCUPIED','FA_NAME','FTP','LOAN_ACCOUNT','CURRENCY','INTEREST_FLOAT_RATE',
                'REFERENCE_RATE','RUN_RATE','IS_REAL_ESTATE','COVER_STS','LOAN_LIMIT_TIME','NO_RE_LOAN_CUST_LV',
                'LOAN_ASSESS_PROFIT','COVER_MOUNT','PRODUCT_RATIO','IMPAIRMENT_PROVISION_RATE','BASE_RATIO'],
        reader: new Ext.data.JsonReader({
//            totalProperty : 'list',
            root:'json.data'
        }, [ {
                    name : 'ECONOMIC_CAPITAL_OCCUPIED'
                },  {
                    name : 'FA_NAME'
                },  {
                    name : 'FTP'
                },  {
                    name : 'LOAN_ACCOUNT'
                },  {
                    name : 'CURRENCY'
                },  {
                    name : 'INTEREST_FLOAT_RATE'
                },  {
                    name : 'REFERENCE_RATE'
                },  {
                    name : 'RUN_RATE'
                },  {
                    name : 'IS_REAL_ESTATE'
                },  {
                    name : 'COVER_STS'
                },  {
                    name : 'LOAN_LIMIT_TIME'
                },  {
                    name : 'NO_RE_LOAN_CUST_LV'
                },  {
                    name : 'LOAN_ASSESS_PROFIT'
                },  {
                    name : 'COVER_MOUNT'
                },  {
                    name : 'PRODUCT_RATIO'
                },  {
                    name : 'IMPAIRMENT_PROVISION_RATE'
                },  {
                    name : 'BASE_RATIO'
                }])
    });
    
    var ckqxStore = new Ext.data.Store({
        restful:true,  
        autoLoad : true,
        proxy : new Ext.data.HttpProxy({url:basepath+'/querydepositlimit.json'}),
        fields:['as80itr','as80std'],
        reader: new Ext.data.JsonReader({
//            totalProperty : 'list',
            root:'json.data'
        }, [ {
                    name : 'as80itr'
                },  {
                    name : 'as80std'
                }])
    });
    
    var boxstore= new Ext.data.Store({  
        restful:true,   
        autoLoad :true,
        proxy : new Ext.data.HttpProxy({
                url :basepath+'/lookup.json?name=DKQX'
            }),
            reader : new Ext.data.JsonReader({
                root : 'JSON'
            }, [ 'key', 'value' ])
        });
    
		var boxstore3= new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=DBXSHU'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
		var boxstore41 = new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=PJXS'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
		var boxstore4 = new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            sortInfo:{
                field: 'key',
                direction: 'ASC' 
                    },
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=CCY'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
		var boxstore6 = new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=YN'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
		var boxstore5 = new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=FTP'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
		var boxstore7 = new Ext.data.Store({  
	        restful:true,   
	        autoLoad :true,
	        proxy : new Ext.data.HttpProxy({
	                url :basepath+'/lookup.json?name=FGXS'
	            }),
	            reader : new Ext.data.JsonReader({
	                root : 'JSON'
	            }, [ 'key', 'value' ])
	        });
		
		
		/*****************************************************************************/
		var ftpNo;
	      
      var ftp;
      
      var crmCode;
      
    //经济资本占用(从经济资本测算 取值 目前为写死)
      var economicCapitalOccupied;
      
      var isRealEstate = '0001';
      
      var baseRatio;
      
      var provisionRate;
      
      var prodRatio;
      
      /**融资平台流动资金覆盖系数*/
      var jintaizjfgxs;
		/*****************************************************************************/
		var ftpStore = new Ext.data.Store({
	          restful:true,  
	          autoLoad : true,
	          proxy : new Ext.data.HttpProxy({url:basepath+'/queryftp.json'}),
	          fields:['SYSTEM_FTP_NAME','SYSTEM_FTP_NO'],
	          reader: new Ext.data.JsonReader({
//	              totalProperty : 'list',
	              root:'json.data'
	          }, [ {
	                      name : 'SYSTEM_FTP_NAME'
	                  },  {
	                      name : 'SYSTEM_FTP_NO'
	                  }])
	      });
	      
	      ftp1Proxy = new Ext.data.HttpProxy({
	          url:basepath+'/queryftp1.json?ftpNo='+ftpNo+'',
	          method:'GET'
	      });
	      
	      var ftp1Store = new Ext.data.Store({
	          restful:true,
//	          autoLoad:false,
	          proxy:ftp1Proxy,
	          fields:['SYSTEM_FTP_RMBPRICE','SYSTEM_FTP_OTHERPRICE'],
	          reader : new Ext.data.JsonReader({
	              successProperty: 'success',
	              root:'json.data'
	          },['SYSTEM_FTP_RMBPRICE','SYSTEM_FTP_OTHERPRICE'])
	      });
	      
	      xsProxy = new Ext.data.HttpProxy({
	          url:basepath+'/querylookupxs.json?crmCode='+crmCode+'',
	          method:'GET'
	      });
	      
	      var xsStore = new Ext.data.Store({
	          restful:true,
//	          autoLoad:false,
	          proxy:xsProxy,
	          fields:['code_name_1'],
	          reader : new Ext.data.JsonReader({
	              successProperty: 'success',
	              root:'json.data'
	          },['code_name_1'])
	      });
		/*****************************************************************************/
var factorPanel = new Ext.form.FormPanel({

		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
        width :document.documentElement.clientWidth-20,
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		margins : '12 0',
		height : 225,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    xtype:'textfield',
                                    name:'ID',
                                    hidden:true
                                },{
                                    id : 'economicCapitalOccupied',
                                    fieldLabel : '*经济资本占用方案',
                                    hiddenName : 'ECONOMIC_CAPITAL_OCCUPIED',
                                    //editable:false,
                                    allowBlank:false,
                                    forceSelection : true,
                                    xtype:'combo',
                                    labelStyle: 'text-align:right;',
                                    triggerAction:'all',
                                    mode:'local',
                                    store:jjzbzyStore,
                                    valueField:'ECONOMIC_CAPITAL_OCCUPIED',
                                    displayField:'FA_NAME',
                                    emptyText:'请选择',
                                    anchor : '100%'
                            },{
                                fieldLabel : '利率浮动比率(%)',
                                id : 'floatRate',
                                allowBlank:false,
                                allowNegative:false,minValue:0,
                                readOnly:true,
                                maxValue:100,
                                name : 'INTEREST_FLOAT_RATE',
                                xtype : 'numberfield', // 设置为数字输入框类型
                                decimalPrecision:4,
                                labelStyle: 'text-align:right;',
                                anchor : '100%'
                            },{
                                fieldLabel : '执行利率(%)',
                                id:'runRate',
//                                disabled:true,
                                maxValue:100,
                                name : 'RUN_RATE',
                                readOnly:true,
                                xtype : 'numberfield', // 设置为数字输入框类型
                                decimalPrecision:4,
                                labelStyle: 'text-align:right;',
                                anchor : '100%'
                            }]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    fieldLabel : '贷款金额(万元)', // 标签
                                    id:'loanAccount',
                                    maxLength:24,
                                    name : 'LOAN_ACCOUNT', // name:后台根据此name属性取值
                                    allowBlank : false, // 是否允许为空
                                    readOnly:true,
                                    labelStyle: 'text-align:right;',
                                    //maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                                    anchor : '100%' // 宽度百分比
                                },new Ext.form.ComboBox({
                                    hiddenName : 'CURRENCY',
                                    id:'mxttest',
                                    //editable:false,
                                    readOnly:true,
                                    fieldLabel : '币种',
                                    value:'CRM_CCY_001',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore4,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
                                    //resizable : true,
                                    anchor : '100%'
                                }),new Ext.form.ComboBox({
                                    hiddenName : 'LOAN_TYP',
                                    fieldLabel : '贷款类型',
                                    id:'loanType',
                                    readOnly:true,
                                    //editable:false,
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store:new Ext.data.ArrayStore({
                                        fields:['myId','displayText'],
                                        data:[['房地产贷款','房地产贷款'],['非房地产信用贷款','非房地产信用贷款'],['非房地产非信用其他贷款','非房地产非信用其他贷款']]
                                    }),
                                    valueField:'myId',
                                    displayField:'displayText',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
//                                    //resizable : true,
                                    anchor : '100%'
                                })]
							},{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [new Ext.form.ComboBox({
                                    hiddenName : 'LOAN_LIMIT_TIME',
                                    fieldLabel : '贷款期限',
                                    id:'mxttest1',
                                    allowBlank:false,
                                    readOnly:true,
                                    //editable:false,
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore,
                                    displayField : 'value',
                                    valueField : 'key',
                                    value:'CRM_DKQX_002',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
//                                    //resizable : true,
                                    anchor : '100%'
                                }),new Ext.form.ComboBox({
                                    id : 'ftpinfo',
                                    hiddenName : 'FTP',
                                    readOnly:true,
                                    fieldLabel : 'FTP',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : ftpStore,
                                    displayField : 'SYSTEM_FTP_NAME',
                                    valueField : 'SYSTEM_FTP_NO',
                                    mode : 'local',
                                    //editable:false,
                                    forceSelection : true,
                                    typeAhead : true,
                                    value : null,
                                    emptyText:'请选择',
//                                    //resizable : true,
                                    anchor : '100%'
                                }),{
                                    fieldLabel : 'FTP值(%)',
                                    id:'ftpvalue',
                                    maxValue:100,
                                    name : 'ftpv',
                                    disabled:true,
                                    readOnly:true,
                                    xtype : 'numberfield', // 设置为数字输入框类型
                                    decimalPrecision:4,
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%'
                                }]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    fieldLabel : '基准利率(%)',
                                    id : 'referenceRate',
                                    allowBlank:false,
                                    readOnly:true,
                                    decimalPrecision:4,
                                    maxValue:100,
                                    name : 'REFERENCE_RATE',
                                    xtype : 'numberfield', // 设置为数字输入框类型
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%'
                                },new Ext.form.ComboBox({
                                    hiddenName : 'COVER_STS',
                                    fieldLabel : '融资平台现金流覆盖情况',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore7,
                                    readOnly:true,
                                    id:'coverxs',
                                    allowBlank:false,
                                    //editable:false,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
//                                    resizable : true,
                                    anchor : '100%'
                                })]
							}]
				},new Ext.Panel({
				    layout:'form',
	                labelWidth : 150, // 标签宽度
	                items : [{
	                    layout : 'column',
	                    border : false,
	                    items:[{
	                        columnWidth : .25,
	                        layout : 'form',
	                        items:[{
						id : 'loanAssessProfit',
						fieldLabel : '<span style="color:red" >贷款考核利润(万元)</span>',
						name : 'LOAN_ASSESS_PROFIT',
						xtype : 'numberfield', // 设置为数字输入框类型
						readOnly : true,
                        maxLength:24,
						labelStyle: 'text-align:right;',
						anchor : '100%'
	                        }]
	                    },{
	                        columnWidth : .25,
                            layout : 'form',
                            labelWidth : 150, // 标签宽度
                            items:[{
                                fieldLabel : '<span style="color:red" >经济资本占用(万元)</span>',
                                id:'ecOccupied',
                                disabled:true,
                                maxLength:24,
                                name : 'ECONOMIC_CAPITAL_OCCUPIED',
                                readOnly:true,
                                xtype : 'numberfield', // 设置为数字输入框类型
                                labelStyle: 'text-align:right;',
                                anchor : '100%'
                            }]
	                    }]}]
			}),{
				    xtype:'fieldset',
                    title: '测算结果', 
                    items:[
				    new Ext.Panel({
				layout:'form',
				labelWidth : 150, // 标签宽度
				items : [{
                    layout : 'column',
                    border : false,
                    items:[{
                        columnWidth : .25,
                        layout : 'form',
                        items:[{
                            id : 'coverStseconomicCapitalOccupied',
                            fieldLabel : '<span style="color:red" >融资平台经济资本占用(万元)</span>',
                            name : 'COVER_MOUNT',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            readOnly : true,
                            maxLength:24,
                            labelStyle: 'text-align:right;',
                            anchor : '100%'
                        }]
                    },{
                        columnWidth : .25,
                        layout : 'form',
                        items:[{
                            id : 'testAccrual',
                            fieldLabel : '<span style="color:red" >测算收益值(万元)</span>',
                            name : 'CACULATE_PROFIT',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            readOnly : true,
                            maxLength:24,
                            labelStyle: 'text-align:right;',
                            anchor : '100%'
                        }]
		    }]}]})]}
				],
		buttons : [{
		    text:'经济资本测算',
		    handler : function(){
		        window.location.href="economicCapital.jsp";
		    }
		},{
					text : '计算',
					handler : function() {
					    if(!factorPanel.getForm().isValid())
                        { 
                            alert('请填写必填信息');
                            return false;
                        }
						
					  //利率浮动比率
                        var floatRate = Ext.getCmp("floatRate").getValue()/100;
                        
                      //基准利率
                        var referenceRate = Ext.getCmp("referenceRate").getValue()/100;
					    
						//贷款金额(此处为 手工录入的贷款时点余额)
						var loanAccount = Ext.getCmp("loanAccount").getValue(); 
						
						//执行利率（需要知道怎么取，此处为写死）
						Ext.getCmp('runRate').setValue(referenceRate*(1+floatRate)*100);
						var jintaililv = referenceRate*(1+floatRate);
						
//						//FTP(需要知道怎么取，此处为写死)
//						var jintialFtp = 0.002;
						
						//贷款考核利润=贷款时点余额× [ 执行利率×（1－5.5%）－FTP ] ÷360?????
						var loanAssessProfit = loanAccount * (jintaililv*(1-0.055)- ftp/100) ;
                        Ext.getCmp("loanAssessProfit").setValue(loanAssessProfit);
                        
                        
                        
                        //经济资本成本率（2011年为15%）
                        var jintiajjzbcbl = 0.15;
                        
                        
                        //经济资本占用(从经济资本测算 取值 目前为写死)
//                        var economicCapitalOccupied = 100;
//                        Ext.getCmp("economicCapitalOccupied").setValue(economicCapitalOccupied);
                        
                      //融资平台经济资本占用=基本比例×[贷款金额×（1—减值准备计提比率）]×产品权重×（融资平台流动资金覆盖系数-1）
                        var coverScOccupied = baseRatio/100*(loanAccount*(1-provisionRate/100)*prodRatio/100*(jintaizjfgxs-1));
//                        
                        Ext.getCmp("coverStseconomicCapitalOccupied").setValue(coverScOccupied);
                        
                        
                        //贷款收益（经济增加值）=考核利润—平均经济资本占用×经济资本成本率
                        var testAccrual = loanAssessProfit - economicCapitalOccupied*jintiajjzbcbl;
                        Ext.getCmp("testAccrual").setValue(testAccrual);
                        
					}
				}, {
					text : '保存',
					handler : function() 
                    {
                       Ext.Ajax.request({
                           url:basepath+'/customerloanprofit.json',
                           method: 'POST',
                           form:factorPanel.getForm().id,
                           success : checkResult,
                           failure : checkResult,
                               params : {
                                   'operate':'add'
                               }
                       });
                   }
				},{
                    text:'清空',
                    handler:function(){
                        factorPanel.getForm().reset();
                        if(cb5.getValue()!=''){
                            crmCode=cb5.getValue();
                          xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
                          xsStore.load({callback:function(){
                              if(!xsStore.getCount()==0){
                              Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
                              }
                          }});
                        }
                    }
                }]
	});
//存款利率
var fundRate;

var saveForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		autoShow:true,
        width :document.documentElement.clientWidth-20,
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		margins : '12 0',
		height : 155,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    xtype:'textfield',
                                    name:'ID',
                                    hidden:true
                                },{
												fieldLabel : '计算日期',
												labelStyle: 'text-align:right;',
												hidden:true,
												anchor : '90%'
										},new Ext.form.ComboBox({
		                                    hiddenName : 'DEPOSIT_LIMIT_TIME',
		                                    fieldLabel : '*存款期限',
		                                    id: 'depositLT',
		                                    allowBlank:false,
		                                    forceSelection : true,
//		                                    xtype:'combo',
		                                    labelStyle: 'text-align:right;',
		                                    triggerAction:'all',
		                                    mode:'local',
		                                    value:'协议存款',
		                                    store:ckqxStore,
		                                    valueField:'as80itr',
		                                    displayField:'as80std',
		                                    emptyText:'请选择',
		                                    anchor : '100%'
		                                }),new Ext.form.ComboBox({
		                                    hiddenName : 'FTP',
		                                    id:'depositFtp',
		                                    fieldLabel : 'FTP',
		                                    allowBlank:false,
		                                    labelStyle: 'text-align:right;',
		                                    triggerAction : 'all',
		                                    store : ftpStore,
		                                    displayField : 'SYSTEM_FTP_NAME',
		                                    valueField : 'SYSTEM_FTP_NO',
		                                    mode : 'local',
		                                    forceSelection : true,
		                                    typeAhead : true,
		                                    emptyText:'请选择',
		                                    //resizable : true,
		                                    anchor : '100%'
		                                })]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
								    xtype:'numberfield',
								    allowNegative:false,minValue:0,
									fieldLabel : '*存款金额(万元)',
                                    allowBlank:false,
                                    maxLength:18,
									id : 'fundSum',
									name:'DEPOSIT_ACCOUNT',
									labelStyle: 'text-align:right;',
									anchor : '100%'
							},{
                                fieldLabel : '*FTP值(%)',
                                id:'depositftpvalue',
                                maxValue:100,
                                name : 'ftpv',
                                disabled:true,
                                readOnly:true,
                                xtype : 'numberfield', // 设置为数字输入框类型
                                decimalPrecision:4,
                                labelStyle: 'text-align:right;',
                                anchor : '100%'
                            }]
							},{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [ new Ext.form.ComboBox({
                                    hiddenName : 'CURRENCY',
                                    id:'depositCCY',
                                    fieldLabel : '*币种',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore4,
                                    allowBlank:false,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    value:'CRM_CCY_001',
                                    //editable:false,
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
                                    //resizable : true,
                                    anchor : '100%'
                                })
							]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
								    id:'fundRate',
									fieldLabel : '*存款利率(%)', // 标签
									maxValue:100,
									xtype:'numberfield',
									allowNegative:false,minValue:0,minValue:0,
                                    decimalPrecision:4,
									name : 'DEPOSIT_RATE', // name:后台根据此name属性取值
									allowBlank : false, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '100%' // 宽度百分比
										}]
							}]
				},{
				    xtype:'fieldset',
                    title: '测算结果', 
                    items:[
				    new Ext.Panel({
					layout:'form',
					labelWidth : 150, // 标签宽度
					items : [{
						fieldLabel : '<span style="color:red" >参考收益(万元)</span>',
						id:'testFundProfit',
						name : 'CACULATE_PROFIT',
						xtype : 'numberfield', // 设置为数字输入框类型
						readOnly : true,
                        maxLength:24,
						labelStyle: 'text-align:right;',
						anchor : '25%'
					}]
			    })]}
				],
		buttons : [{
					text : '计算',
					handler : function() {
					    if(!saveForm.getForm().isValid())
                        { 
                            alert('请填写必填信息');
                            return false;
                        }
						//存款金额
						var fundSum = Ext.getCmp("fundSum").getValue();
						
						//存款利率
						var fundRate = Ext.getCmp("fundRate").getValue()/100;
						
						//ftp  为静态 到时候去取
//						var ftp = 0.049;
						
						//存款收益（经济增加值）=存款金额×（FTP—存款利率）
						var testFundProfit = fundSum*(ftp/100 - fundRate);
						Ext.getCmp("testFundProfit").setValue(testFundProfit);
					}
				}, {
					text : '保存',
					handler : function() 
                    {
                       Ext.Ajax.request({
                           url:basepath+'/customerdepositprofit.json',
                           method: 'POST',
                           form:saveForm.getForm().id,
                           success : checkResult1,
                           failure : checkResult1
//                               params : {
//                                   'operate':'add'
//                               }
                       });
                   }
				},{
                    text:'清空',
                    handler:function(){
                        id:'clsbt',
                        saveForm.getForm().reset();
                        Ext.getCmp('fundRate').setReadOnly(false);
                    }
                }]
	});
	
	
	/*var middleForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		margins : '12 0',
		height : 225,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '金额(万元)', // 标签
									name : 'ACCOUNT', // name:后台根据此name属性取值
									allowBlank : false, // 是否允许为空
									id:'midSum',
									xtype:'numberfield',
									allowNegative:false,minValue:0,minValue:0,
									labelStyle: 'text-align:right;',
									//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
									anchor : '100%' // 宽度百分比
								},{
                                    fieldLabel : '中间业务支出(万元)', // 标签
                                    name : 'MARGIN_PAY', // name:后台根据此name属性取值
                                    allowBlank : false, // 是否允许为空
                                    id:'midPay',
                                    xtype:'numberfield',
                                    allowNegative:false,minValue:0,
                                    labelStyle: 'text-align:right;',
                                    //maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                                    anchor : '100%' // 宽度百分比
                                },{
                                    fieldLabel : '手续费比率(%)', // 标签
                                    name : 'SXF_RATIO', // name:后台根据此name属性取值
                                    allowBlank : false, // 是否允许为空
                                    id:'sfxRation',
                                    xtype:'numberfield',
                                    decimalPrecision:4,
                                    allowNegative:false,minValue:0,
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%' // 宽度百分比
                                }]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [new Ext.form.ComboBox({
                                    hiddenName : 'PROD_TYP',
                                    fieldLabel : '产品类型',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore7,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
                                    //resizable : true,
                                    anchor : '100%'
                                }),{
									fieldLabel : '减值准备计提比率', // 标签
									name : 'IMPAIRMENT_PROVISION_RATE', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									id:'b4',
									labelStyle: 'text-align:right;',
									anchor : '100%' // 宽度百分比
								},{
                                    fieldLabel : '保证金比例(%)', // 标签
                                    name : 'MARGIN_RATIO', // name:后台根据此name属性取值
                                    allowBlank : false, // 是否允许为空
                                    id:'marginRatio',
                                    xtype:'numberfield',
                                    decimalPrecision:4,
                                    allowNegative:false,minValue:0,
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%' // 宽度百分比
                                }]
							},{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [ {
											fieldLabel : '基本比例',
											id : 'b6',
											name:'BASE_RATIO',
											xtype : 'numberfield', // 设置为数字输入框类型
											allowDecimals : false, // 是否允许输入小数
                                            decimalPrecision:4,
											labelStyle: 'text-align:right;',
											allowNegative : false, // 是否允许输入负数
											anchor : '90%'
										},new Ext.form.ComboBox({
		                                    hiddenName : 'COVER_STS',
		                                    fieldLabel : '融资平台现金流覆盖情况',
		                                    labelStyle: 'text-align:right;',
		                                    triggerAction : 'all',
		                                    store : boxstore7,
		                                    displayField : 'value',
		                                    valueField : 'key',
		                                    mode : 'local',
		                                    forceSelection : true,
		                                    typeAhead : true,
		                                    emptyText:'请选择',
		                                    //resizable : true,
		                                    anchor : '100%'
		                                }),new Ext.form.ComboBox({
		                                    hiddenName : 'MARGIN_LIMIT_TIME',
		                                    fieldLabel : '保证金期限',
		                                    id:'marginLT',
		                                    allowBlank:false,
		                                    readOnly:true,
		                                    //editable:false,
		                                    labelStyle: 'text-align:right;',
		                                    triggerAction : 'all',
		                                    store : boxstore,
		                                    displayField : 'value',
		                                    valueField : 'key',
		                                    mode : 'local',
		                                    forceSelection : true,
		                                    typeAhead : true,
		                                    emptyText:'请选择',
		                                    //resizable : true,
		                                    anchor : '100%'
		                                })]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [new Ext.form.ComboBox({
                                    hiddenName : 'IS_REAL_ESTATE',
                                    fieldLabel : '是否房地产行业',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore6,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
                                    //resizable : true,
                                    anchor : '100%'
                                }),
                                new Ext.form.ComboBox({
                                    id : 'noReLoanCustLv',
                                    hiddenName : 'NO_RE_LOAN_CUST_LV',
                                    fieldLabel : '非房地产行业的信用评级',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore41,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
                                    //resizable : true,
                                    anchor : '100%'
                                }),new Ext.form.ComboBox({
                                    hiddenName : 'GUARANTEE_MODE',
                                    fieldLabel : '担保系数',
                                    labelStyle: 'text-align:right;',
                                    triggerAction : 'all',
                                    store : boxstore3,
                                    displayField : 'value',
                                    valueField : 'key',
                                    mode : 'local',
                                    forceSelection : true,
                                    typeAhead : true,
                                    emptyText:'请选择',
                                    //resizable : true,
                                    anchor : '100%'
                                })]
							}]
				},new Ext.Panel({
                layout:'form',
                labelWidth : 150, // 标签宽度
                items : [{
                    layout : 'column',
                    border : false,
                    items:[{
                        columnWidth : .5,
                        layout : 'form',
                        items:[{
                                id : 'midEconomicCapitalOccupied',
                                fieldLabel : '经济资本占用方案',
                                hiddenName : 'ECONOMIC_CAPITAL_OCCUPIED',
                                //editable:false,
                                forceSelection : true,
                                xtype:'combo',
                                labelStyle: 'text-align:right;',
                                triggerAction:'all',
                                mode:'local',
                                store:jjzbzyStore,
                                valueField:'ECONOMIC_CAPITAL_OCCUPIED',
                                displayField:'FA_NAME',
                                emptyText:'请选择',
                                anchor : '50%'
                        }]
                    },{
                        columnWidth : .5,
                        layout : 'form',
                        items:[{
                            fieldLabel : '经济资本占用(万元)%',
                            id:'midecOccupied',
                            name : 'ECONOMIC_CAPITAL_OCCUPIED',
                            disabled:true,
                            readOnly:true,
                            xtype : 'numberfield', // 设置为数字输入框类型
                            labelStyle: 'text-align:right;',
                            anchor : '50%'
                        }]
                    }]}]
            }),new Ext.Panel({
				layout:'form',
				labelWidth : 150, // 标签宽度
				items : [{
				id : 'testMidProfit',
				fieldLabel : '测算收益值',
				name:'CACULATE_PROFIT',
				xtype : 'numberfield', // 设置为数字输入框类型
				readOnly : true,
				labelStyle: 'text-align:right;',
				anchor : '25%'
			}]
		})
				
				],
		buttons : [{
					text : '计算',
					handler : function() {
						//目前写死 后期传值
						var midEconomicCapitalOccupied = 10000;
						Ext.getCmp("midEconomicCapitalOccupied").setValue(midEconomicCapitalOccupied);
						
						//考核利润 即中间业务收入
						var midSum = Ext.getCmp("midSum").getValue();
						
						//经济资本成本率
						var jintaiCbl = 0.15;
						
						//中间业务收益（经济增加值）=	考核利润—平均经济资本占用×经济资本成本率
						var testMidProfit = Ext.getCmp("testMidProfit").setValue(midSum-midEconomicCapitalOccupied*jintaiCbl);
					}
				}, {
					text : '保存',
					handler : function() 
                    {
                       Ext.Ajax.request({
                           url:basepath+'/customermiddleprofit.json',
                           method: 'POST',
                           form:middleForm.getForm().id,
                           success : checkResult2,
                           failure : checkResult2
//                               params : {
//                                   'operate':'add'
//                               }
                       });
                   }
				},{
				    text : '清空',
				    handler : function(){
				        middleForm.getForm().reset();
				    }
				}]
	});
	*/
	
	/****************************************************************************************/
	/*var cb9 = Ext.getCmp('midEconomicCapitalOccupied');
    cb9.addListener("select",function(){
        if(cb9.getValue()!=''){
            economicCapitalOccupied=cb9.getValue();
            Ext.getCmp('midecOccupied').setValue(economicCapitalOccupied);
//            
            var index = jjzbzyStore.find('FA_NAME',cb9.getRawValue());
            if(index != -1){
                Ext.getCmp('loanAccount').setValue(jjzbzyStore.getAt(index).data.LOAN_ACCOUNT);
                Ext.getCmp('referenceRate').setValue(jjzbzyStore.getAt(index).data.REFERENCE_RATE);
                Ext.getCmp('mxttest').setValue(jjzbzyStore.getAt(index).data.CURRENCY);
                Ext.getCmp('floatRate').setValue(jjzbzyStore.getAt(index).data.INTEREST_FLOAT_RATE);
                Ext.getCmp('mxttest1').setValue(jjzbzyStore.getAt(index).data.LOAN_LIMIT_TIME);
                Ext.getCmp('ftpinfo').setValue(jjzbzyStore.getAt(index).data.FTP);
                Ext.getCmp('coverxs').setValue(jjzbzyStore.getAt(index).data.COVER_STS);
                Ext.getCmp('runRate').setValue(jjzbzyStore.getAt(index).data.RUN_RATE);
                Ext.getCmp('loanAssessProfit').setValue(jjzbzyStore.getAt(index).data.LOAN_ASSESS_PROFIT);
                Ext.getCmp('coverStseconomicCapitalOccupied').setValue(jjzbzyStore.getAt(index).data.COVER_MOUNT);
                prodRatio = jjzbzyStore.getAt(index).data.PRODUCT_RATIO;
                provisionRate = jjzbzyStore.getAt(index).data.IMPAIRMENT_PROVISION_RATE;
                baseRatio = jjzbzyStore.getAt(index).data.BASE_RATIO;
                if(jjzbzyStore.getAt(index).data.IS_REAL_ESTATE == 'CRM_YN_002'){
                    Ext.getCmp('loanType').setValue('房地产贷款');
                }else if(jjzbzyStore.getAt(index).data.NO_RE_LOAN_CUST_LV !=''){
                    Ext.getCmp('loanType').setValue('非房地产信用贷款');
                }else {
                    Ext.getCmp('loanType').setValue('非房地产非信用其他贷款');
                }
             }
            if(cb6.getValue()!=''){
                ftpNo=cb6.getValue();
              ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
              ftp1Store.load({callback:function(){
                  if(Ext.getCmp('mxttest').getValue()=='CRM_CCY_001'){
                      if(ftp1Store.getCount()!=0){
                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
                      }
                  }else{
                      if(ftp1Store.getCount()!=0){
                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
                      }
                  }
                  Ext.getCmp('ftpvalue').setValue(ftp);
              }});
            }  
            if(cb2.getValue()!=''){
                crmCode=cb2.getValue();
              xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
              xsStore.load({callback:function(){
                  if(!xsStore.getCount()==0){
                  jintaizjfgxs=parseFloat(xsStore.getAt(0).data.code_name_1);
                  }
                  //alert(jintaizjfgxs);
              }});
            }
        }else{
            alert('无对应计算结果！');
        }
    });*/
	/****************************************************************************************/
	
				 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
	//复选框
    var sm1 = new Ext.grid.CheckboxSelectionModel();
  //复选框
    var sm2 = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            gridStore.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            gridStore.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
    }
	function checkResult1(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            saveGridStore.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            saveGridStore.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
    }
/*	function checkResult2(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
//        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            midStore.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        } else {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            midStore.reload({
                        params : {
                            start : 0,
                            limit : bbar.pageSize
                        }
                    });
        }
    }*/
	
var gridStore = new Ext.data.Store({
    restful:true,   
    proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerloanprofit.json'}),
    reader: new Ext.data.JsonReader({
        successProperty: 'success',
    root:'json.data',
    totalProperty: 'json.count'
    }, [{
                name : 'ID'
            },{
                name : 'LOAN_ACCOUNT'
            }, {
                name : 'CURRENCY_GP'
            }, {
                name : 'LOAN_LIMIT_TIME_GP'
            }, {
                name : 'CURRENCY'
            }, {
                name : 'LOAN_LIMIT_TIME'
            },  {
                name : 'REFERENCE_RATE'
            }, {
                name : 'LOAN_TYP'
            }, {
                name : 'COVER_STS_GP'
            }, {
                name : 'COVER_STS'
            }, {
                name : 'LOAN_ASSESS_PROFIT'
            }, {
                name : 'ECONOMIC_CAPITAL_OCCUPIED'
            }, {
                name : 'COVER_MOUNT'
            }, {
                name : 'CACULATE_PROFIT'
            }, {
                name : 'CUST_ID'
            }, {
                name : 'ORG_ID'
            }, {
                name : 'CALCULATE_TIME'
            }, {
                name : 'RUN_RATE'
            }, {
                name : 'FTP_ORA'
            }, {
                name : 'FTP'
            }, {
                name : 'INTEREST_FLOAT_RATE'
            }])
});
//每页显示条数下拉选择框
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
// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
    bbar.pageSize = parseInt(comboBox.getValue());
    number = parseInt(comboBox.getValue());
    gridStore.reload({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });
});
// 分页工具栏
var bbar = new Ext.PagingToolbar({
    pageSize : number,
    store : gridStore,
    displayInfo : true,
    displayMsg : '显示{0}条到{1}条,共{2}条',
    //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
    emptyMsg : "没有符合条件的记录",
    items : ['-', '&nbsp;&nbsp;', pagesize_combo
             ]
});

//表格工具栏
var tbar = new Ext.Toolbar({
            items : [
                {
                text : '修改',
                handler : function() {
                    editInit();
                }},'-',
                {
                text : '删除',
                handler : function() {
                    deleteInit();
                }}]
        });

	var resultGrid = new Ext.grid.GridPanel({
		title:'贷款产品收益率测算结果',
		store : gridStore,
		width :document.documentElement.clientWidth-20,
		frame : true, // 是否渲染表单面板背景色
		autoScroll:true,
		height : 300,
		//cm : cm, // 列模型
		sm : sm, // 复选框
		bbar:bbar,
		tbar:tbar,
		columns : [rownum,sm,{
					header : '计算日期',
					width : document.body.clientWidth / 7,
					dataIndex : 'CALCULATE_TIME',
					align : 'center'
				}, {
					header : '用户ID',
					width : document.body.clientWidth / 7,
					dataIndex : 'CUST_ID',
					align : 'center'
				},{
					header : '机构号',
					width : document.body.clientWidth / 7,
					dataIndex : 'ORG_ID',
					align : 'center'
				}, {
					header : '贷款金额(万元)',
					width : document.body.clientWidth / 7,
					dataIndex : 'LOAN_ACCOUNT',
                    align : 'right',
                    renderer: money('0,000.00' )
				}, {
					header : '币种',
					width : document.body.clientWidth / 7,
					dataIndex : 'CURRENCY_GP',
					align : 'center'
				}, {
					header : '贷款期限',
					width : document.body.clientWidth / 9,
					dataIndex : 'LOAN_LIMIT_TIME_GP',
                    align : 'center'
				}, {
					header : '贷款类型',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'LOAN_TYP',
					align : 'center'
				}, {
					header : '融资平台现金流覆盖情况',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'COVER_STS_GP',
					align : 'center'
				}, {
					header : '执行利率',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'RUN_RATE',
                    align : 'right',
                    renderer: ratePercent(false)
				}, {
					header : 'FTP',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'FTP',
					renderer:function(value){
                        if(ftpStore.getCount()==0){
                            ftpStore.load({callback:function(){
                                var index = ftpStore.find('SYSTEM_FTP_NO',value);
                                if(index != -1){
                                value=ftpStore.getAt(index).data.SYSTEM_FTP_NAME;
                                gridStore.reload({
                                    params : {
                                        start : 0,
                                        limit : bbar.pageSize
                                    }
                                });
                                }
                                return value;
                            }});
                        }else{
                            var index = ftpStore.find('SYSTEM_FTP_NO',value);
                            if(index != -1){
                            value=ftpStore.getAt(index).data.SYSTEM_FTP_NAME;
                            }
                            return value;
                        }
                        },
					align : 'center'
				}, {
					header : '利率浮动比率',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'INTEREST_FLOAT_RATE',
                    align : 'right',
                    renderer: ratePercent(false)
				}, {
					header : '基准利率',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'REFERENCE_RATE',
                    align : 'right',
                    renderer: ratePercent(false)
				}, {
					header : '考核利润(万元)',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'LOAN_ASSESS_PROFIT',
                    align : 'right',
                    renderer: money('0,000.00' )
				}, {
					header : '融资平台经济资本占用',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'COVER_MOUNT',
                    align : 'right',
                    renderer: money('0,000.00' )
				}, {
					header : '经济资本占用',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'ECONOMIC_CAPITAL_OCCUPIED',
                    align : 'right',
                    renderer: money('0,000.00' )
				}, {
					header : '测算收益值',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'CACULATE_PROFIT',
                    align : 'right',
                    renderer: money('0,000.00' )
				}],
		stripeRows : true
	});

var saveGridStore = new Ext.data.Store({
    restful:true,   
    proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerdepositprofit.json'}),
    reader: new Ext.data.JsonReader({
        successProperty: 'success',
    root:'json.data',
    totalProperty: 'json.count'
    }, [{
					name : 'ID'
				}, {
                    name : 'CURRENCY_GP'
                }, {
                    name : 'CURRENCY'
                }, {
					name : 'DEPOSIT_ACCOUNT'
				}, {
                    name : 'DEPOSIT_LIMIT_TIME_GP'
                }, {
                    name : 'DEPOSIT_LIMIT_TIME'
                }, {
					name : 'FTP_ORA'
				}, {
					name : 'FTP'
				}, {
					name : 'CALCULATE_TIME'
				}, {
					name : 'CUST_ID'
				}, {
					name : 'ORG_ID'
				}, {
					name : 'CACULATE_PROFIT'
				}, {
					name : 'DEPOSIT_RATE'
				}])
	});
/*var midStore = new Ext.data.Store({
    restful:true,   
    proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomermiddleprofit.json'}),
    reader: new Ext.data.JsonReader({
        successProperty: 'success',
    root:'json.data',
    totalProperty: 'json.count'
    }, [{
                    name : 'ACCOUNT'
                }, {
                    name : 'IMPAIRMENT_PROVISION_RATE'
                }, {
                    name : 'BASE_RATIO'
                }, {
                    name : 'IS_REAL_ESTATE'
                }, {
                    name : 'NO_RE_LOAN_CUST_LV'
                }, {
                    name : 'GUARANTEE_MODE'
                }, {
                    name : 'ECONOMIC_CAPITAL_OCCUPIED'
                }, {
                    name : 'MARGIN_RATIO'
                }, {
                    name : 'CACULATE_PROFIT'
                }, {
                    name : 'MARGIN_LIMIT_TIME_GP'
                }, {
                    name : 'MARGIN_LIMIT_TIME'
                }, {
                    name : 'MARGIN_INCOME'
                }, {
                    name : 'MARGIN_PAY'
                }, {
                    name : 'MARGIN_NET_INCOME'
                }, {
                    name : 'SXF_RATIO'
                }, {
                    name : 'PROD_TYP'
                }])
    });
*/
//每页显示条数下拉选择框
var pagesize_combo1 = new Ext.form.ComboBox({
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
var number1 = parseInt(pagesize_combo1.getValue());
// 改变每页显示条数reload数据
pagesize_combo1.on("select", function(comboBox) {
    bbar1.pageSize = parseInt(comboBox.getValue());
    number1 = parseInt(comboBox.getValue());
    saveGridStore.reload({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });
});
//分页工具栏
var bbar1 = new Ext.PagingToolbar({
    pageSize : number1,
    store : saveGridStore,
    displayInfo : true,
    displayMsg : '显示{0}条到{1}条,共{2}条',
    //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
    emptyMsg : "没有符合条件的记录",
    items : ['-', '&nbsp;&nbsp;', pagesize_combo1
             ]
});
/*
//每页显示条数下拉选择框
var pagesize_combo2 = new Ext.form.ComboBox({
    name : 'pagesize',
    triggerAction : 'all',
    mode : 'local',
    store : new Ext.data.ArrayStore({
        fields : ['value', 'text'],
        data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
    }),
    valueField : 'value',
    displayField : 'text',
    value : '100',
    editable : false,
    width : 85
});
var number2 = parseInt(pagesize_combo2.getValue());
// 改变每页显示条数reload数据
pagesize_combo2.on("select", function(comboBox) {
    bbar2.pageSize = parseInt(comboBox.getValue());
    number2 = parseInt(comboBox.getValue());
    midStore.reload({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });
});

//分页工具栏
var bbar2 = new Ext.PagingToolbar({
    pageSize : number2,
    store : midStore,
    displayInfo : true,
    displayMsg : '显示{0}条到{1}条,共{2}条',
    //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
    emptyMsg : "没有符合条件的记录",
    items : ['-', '&nbsp;&nbsp;', pagesize_combo2
             ]
});
*/
//表格工具栏
var tbar1 = new Ext.Toolbar({
            items : [
                {
                text : '修改',
                handler : function() {
                    editInit1();
                }},'-',
                {
                text : '删除',
                handler : function() {
                    deleteInit1();
                }}]
        });
//表格工具栏
var tbar2 = new Ext.Toolbar({
            items : [
                {
                text : '修改',
                handler : function() {
                    editInit2();
                }},'-',
                {
                text : '删除',
                handler : function() {
                    deleteInit2();
                }}]
        });
	var saveResultGrid = new Ext.grid.GridPanel({
		title:'存款产品收益率测算结果',
		store : saveGridStore,
		frame : true, // 是否渲染表单面板背景色
        width :document.documentElement.clientWidth-20,
		height : 300,
		//cm : cm, // 列模型
		sm : sm1, // 复选框
        bbar:bbar1,
        tbar:tbar1,
		columns : [rownum,sm1,{
					header : '计算日期',
					width : document.body.clientWidth / 4,
					dataIndex : 'CALCULATE_TIME',
					align : 'center'
				},{
					header : '存款金额(万元)',
					width : document.body.clientWidth / 4,
					dataIndex : 'DEPOSIT_ACCOUNT',
                    align : 'right',
                    renderer: money('0,000.00' )
				},{
					header : '存款期限',
					width : document.body.clientWidth / 4,
					dataIndex : 'DEPOSIT_LIMIT_TIME_GP',
					renderer:function(value){
                        if(ckqxStore.getCount()==0){
                            ckqxStore.load({callback:function(){
                                var index = ckqxStore.find('as80itr',value);
                                if(index != -1){
                                value=ckqxStore.getAt(index).data.as80std;
                                saveGridStore.reload({
                                    params : {
                                        start : 0,
                                        limit : bbar.pageSize
                                    }
                                });
                                }
                                return value;
                            }});
                        }else{
                            var index = ckqxStore.find('as80itr',value);
                            if(index != -1){
                            value=ckqxStore.getAt(index).data.as80std;
                            }
                            return value;
                        }
                        },
					align : 'center'
				}, {
					header : '存款利率(%)',
					width : document.body.clientWidth / 4,
					dataIndex : 'DEPOSIT_RATE',
                    align : 'right',
                    renderer: ratePercent(false)
				},{
					header : 'FTP',
					width : document.body.clientWidth / 4,
					dataIndex : 'FTP',
					renderer:function(value){
                        if(ftpStore.getCount()==0){
                            ftpStore.load({callback:function(){
                                var index = ftpStore.find('SYSTEM_FTP_NO',value);
                                if(index != -1){
                                value=ftpStore.getAt(index).data.SYSTEM_FTP_NAME;
                                }
                                gridStore.reload({
                                    params : {
                                        start : 0,
                                        limit : bbar.pageSize
                                    }
                                });
                                return value;
                            }});
                        }else{
                            var index = ftpStore.find('SYSTEM_FTP_NO',value);
                            if(index != -1){
                            value=ftpStore.getAt(index).data.SYSTEM_FTP_NAME;
                            }
                            return value;
                        }
                        },
					align : 'center'
				}, {
                    header : '测算收益(万元)',
                    width : document.body.clientWidth / 4,
                    dataIndex : 'CACULATE_PROFIT',
                    align : 'right',
                    renderer: money('0,000.00' )
                }, {
                    header : '币种',
                    width : document.body.clientWidth / 4,
                    dataIndex : 'CURRENCY_GP',
                    align : 'center'
                }, {
                    header : '用户ID',
                    width : document.body.clientWidth / 4,
                    dataIndex : 'CUST_ID',
                    align : 'center'
                }, {
                    header : '机构号',
                    width : document.body.clientWidth / 4,
                    dataIndex : 'ORG_ID',
                    align : 'center'
                }],
		stripeRows : true
	});
	/*var middleResultGrid = new Ext.grid.GridPanel({
		title:'中间业务产品收益率测算结果',
		store : midStore,
		frame : true, // 是否渲染表单面板背景色
        width :document.body.scrollWidth,
		height : document.body.clientWidth-150,
		//cm : cm, // 列模型
		sm : sm2, // 复选框
        bbar:bbar2,
        tbar:tbar2,
		columns : [rownum,sm2,{
					header : '中间业务收入',
					width : document.body.clientWidth / 4,
					dataIndex : 'MARGIN_INCOME',
					align : 'center'
				}, {
					header : '中间业务净收入',
					width : document.body.clientWidth / 4,
					dataIndex : 'MARGIN_NET_INCOME',
					align : 'center'
				},{
					header : '经济资本占用',
					width : document.body.clientWidth / 4,
					dataIndex : 'ECONOMIC_CAPITAL_OCCUPIED',
					align : 'center'
				}, {
					header : '测算收益值',
					width : document.body.clientWidth / 4,
					dataIndex : 'CACULATE_PROFIT',
					align : 'center'
				}],
		stripeRows : true,
		height : 455
	});*/
		var loanPanel = new Ext.Panel({
		title: '贷款产品收益测算工具',
		renderTo:'loan',
		items:[factorPanel,resultGrid]
	});
	var savePanel = new Ext.Panel({
		title: '存款产品收益测算工具',
//		renderTo:'save',
//		preventBodyReset:true,
		items:[saveForm,saveResultGrid]
	});
	/*var middlePanel = new Ext.Panel({
		title: '中间业务收益测算工具',
		//renderTo:'save',
		items:[middleForm,middleResultGrid]
	});*/
	var schedulePlanTabs = new Ext.TabPanel({
	        renderTo: 'navigater',
	        width:'100%',
	        heignt:'100%',
	        activeTab: 0,
	        frame:true,
	        defaults:{autoHeight: true},
	        resizeTabs:true, // turn on tab resizing
	       // minTabWidth:20,
	        preferredTabWidth:150,	        
	        items:[
	        	{ 
					contentEl:'loan', 
					title: '<span style=\'text-align:center;\'>贷款测算</span>',
					items:[loanPanel]
				},
	            { 
					contentEl:'save', 
					title: '<span style=\'text-align:center;\' >存款测算</span>',
					items:[savePanel]
				}/*,
	            { 
					contentEl:'save', 
					title: '<span style=\'text-align:center;\'>中间业务测算</span>',
					items:[middlePanel]
				}*/
	        ]
	    });	
	/*midStore.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });*/
	gridStore.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });
	saveGridStore.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });
	
	boxstore4.load({callback:function(){
        Ext.getCmp('mxttest').setValue('CRM_CCY_001');
        Ext.getCmp('depositCCY').setValue('CRM_CCY_001');
        
    }});
    
    boxstore.load({callback:function(){
        Ext.getCmp('mxttest1').setValue('CRM_DKQX_002');
        
    }});
    
//    jjzbzyStore.load();
//    ckqxStore.load();
//    boxstore41.load();
//    boxstore3.load();
//    boxstore5.load();
//    boxstore6.load();
//    boxstore7.load();
    
    /**************************************贷款监听************************************************/
    //融资平台流动资金覆盖系数
    var cb2 = Ext.getCmp('coverxs');
    cb2.addListener("select",function(){
        if(cb2.getValue()!=''){
            crmCode=cb2.getValue();
          xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
          xsStore.load({callback:function(){
              if(!xsStore.getCount()==0){
              jintaizjfgxs=parseFloat(xsStore.getAt(0).data.code_name_1);
              }
              //alert(jintaizjfgxs);
          }});
        }
    });
    
    
  //ftp
    var cb6 = Ext.getCmp('ftpinfo');
//    cb6.addListener("select",function(){
//        if(cb6.getValue()!=''){
//            ftpNo=cb6.getValue();
//          ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
//          ftp1Store.load({callback:function(){
//              if(Ext.getCmp('mxttest').getValue()=='CRM_CCY_001'){
//                  if(ftp1Store.getCount()!=0){
//                  ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
//                  }
//              }else{
//                  if(ftp1Store.getCount()!=0){
//                  ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
//                  }
//              }
//              Ext.getCmp('ftpvalue').setValue(ftp);
//          }});
//        }
//    });
    
  //基准利率获取
    var cb5 = Ext.getCmp('mxttest1');
    cb5.addListener("select",function(){
        if(cb5.getValue()!=''){
            crmCode=cb5.getValue();
          xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
          xsStore.load({callback:function(){
              if(!xsStore.getCount()==0){
              Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
              }
          }});
        }
    });
    
    var cb7 = Ext.getCmp('economicCapitalOccupied');
    cb7.addListener("select",function(){
        if(cb7.getValue()!=''){
            economicCapitalOccupied=cb7.getValue();
            Ext.getCmp('ecOccupied').setValue(economicCapitalOccupied);
//            
            var index = jjzbzyStore.find('FA_NAME',cb7.getRawValue());
            if(index != -1){
                Ext.getCmp('loanAccount').setValue(jjzbzyStore.getAt(index).data.LOAN_ACCOUNT);
                Ext.getCmp('referenceRate').setValue(jjzbzyStore.getAt(index).data.REFERENCE_RATE);
                Ext.getCmp('mxttest').setValue(jjzbzyStore.getAt(index).data.CURRENCY);
                Ext.getCmp('floatRate').setValue(jjzbzyStore.getAt(index).data.INTEREST_FLOAT_RATE);
                Ext.getCmp('mxttest1').setValue(jjzbzyStore.getAt(index).data.LOAN_LIMIT_TIME);
                Ext.getCmp('ftpinfo').setValue(jjzbzyStore.getAt(index).data.FTP);
                Ext.getCmp('coverxs').setValue(jjzbzyStore.getAt(index).data.COVER_STS);
                Ext.getCmp('runRate').setValue(jjzbzyStore.getAt(index).data.RUN_RATE);
                Ext.getCmp('loanAssessProfit').setValue(jjzbzyStore.getAt(index).data.LOAN_ASSESS_PROFIT);
                Ext.getCmp('coverStseconomicCapitalOccupied').setValue(jjzbzyStore.getAt(index).data.COVER_MOUNT);
                prodRatio = jjzbzyStore.getAt(index).data.PRODUCT_RATIO;
                provisionRate = jjzbzyStore.getAt(index).data.IMPAIRMENT_PROVISION_RATE;
                baseRatio = jjzbzyStore.getAt(index).data.BASE_RATIO;
                if(jjzbzyStore.getAt(index).data.IS_REAL_ESTATE == 'CRM_YN_002'){
                    Ext.getCmp('loanType').setValue('房地产贷款');
                }else if(jjzbzyStore.getAt(index).data.NO_RE_LOAN_CUST_LV !=''){
                    Ext.getCmp('loanType').setValue('非房地产信用贷款');
                }else {
                    Ext.getCmp('loanType').setValue('非房地产非信用其他贷款');
                }
             }
            if(cb6.getValue()!=''){
                ftpNo=cb6.getValue();
              ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
              ftp1Store.load({callback:function(){
                  if(Ext.getCmp('mxttest').getValue()=='CRM_CCY_001'){
                      if(ftp1Store.getCount()!=0){
                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
                      }
                  }else{
                      if(ftp1Store.getCount()!=0){
                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
                      }
                  }
                  Ext.getCmp('ftpvalue').setValue(ftp);
              }});
            }  
            if(cb2.getValue()!=''){
                crmCode=cb2.getValue();
              xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
              xsStore.load({callback:function(){
                  if(!xsStore.getCount()==0){
                  jintaizjfgxs=parseFloat(xsStore.getAt(0).data.code_name_1);
                  }
                  //alert(jintaizjfgxs);
              }});
            }
        }else{
            alert('无对应计算结果！');
        }
    });
/********************************************存款监听***********************************************************/
  //ftp
    var cb8 = Ext.getCmp('depositFtp');
    cb8.addListener("select",function(){
        if(cb8.getValue()!=''){
            ftpNo=cb8.getValue();
          ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
          ftp1Store.load({callback:function(){
              if(Ext.getCmp('depositCCY').getValue()=='CRM_CCY_001'){
                  if(ftp1Store.getCount()!=0){
                  ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
                  }
              }else{
                  if(ftp1Store.getCount()!=0){
                  ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
                  }
              }
              Ext.getCmp('depositftpvalue').setValue(ftp);
          }});
        }
    });
    
  //币种改变对FTP的影响
    var cb4 = Ext.getCmp('depositCCY');
    cb4.addListener("select",function(){
        if(cb8.getValue() != ''){
            ftpNo=cb8.getValue();
            ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
            ftp1Store.load({callback:function(){
                if(cb4.getValue()=='CRM_CCY_001'){
                    if(ftp1Store.getCount()!=0){
                    ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
                    }
                }else{
                    if(ftp1Store.getCount()!=0){
                    ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
                    }
                }
                Ext.getCmp('depositftpvalue').setValue(ftp);
            }});
        }
    });
    var cb10 = Ext.getCmp('depositLT');
    cb10.addListener("select",function(){
        if(cb10.getValue()!='协议存款'){
            Ext.getCmp('fundRate').setValue(cb10.getValue());
//            
            Ext.getCmp('fundRate').setReadOnly(true);
        }
    });
/*******************************************************************************************************/   
    
    if(cb5.getValue()!=''){
        crmCode=cb5.getValue();
      xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
      xsStore.load({callback:function(){
          if(!xsStore.getCount()==0){
              Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
          }
      }});
    }
    
    
    function editInit(){
        var selectLength = resultGrid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        }else{
        var infoRecord = resultGrid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            factorPanel.getForm().loadRecord(infoRecord);
            /**************************************/
            if(cb7.getValue()!=''){
                economicCapitalOccupied=cb7.getValue();
                Ext.getCmp('ecOccupied').setValue(economicCapitalOccupied);
//              
              var index = jjzbzyStore.find('FA_NAME',cb7.getRawValue());
              if(index != -1){
//                  Ext.getCmp('loanAccount').setValue(jjzbzyStore.getAt(index).data.LOAN_ACCOUNT);
//                  Ext.getCmp('referenceRate').setValue(jjzbzyStore.getAt(index).data.REFERENCE_RATE);
//                  Ext.getCmp('mxttest').setValue(jjzbzyStore.getAt(index).data.CURRENCY);
//                  Ext.getCmp('floatRate').setValue(jjzbzyStore.getAt(index).data.INTEREST_FLOAT_RATE);
//                  Ext.getCmp('mxttest1').setValue(jjzbzyStore.getAt(index).data.LOAN_LIMIT_TIME);
//                  Ext.getCmp('ftpinfo').setValue(jjzbzyStore.getAt(index).data.FTP);
//                  Ext.getCmp('coverxs').setValue(jjzbzyStore.getAt(index).data.COVER_STS);
//                  Ext.getCmp('runRate').setValue(jjzbzyStore.getAt(index).data.RUN_RATE);
//                  Ext.getCmp('loanAssessProfit').setValue(jjzbzyStore.getAt(index).data.LOAN_ASSESS_PROFIT);
//                  Ext.getCmp('coverStseconomicCapitalOccupied').setValue(jjzbzyStore.getAt(index).data.COVER_MOUNT);
                  prodRatio = jjzbzyStore.getAt(index).data.PRODUCT_RATIO;
                  provisionRate = jjzbzyStore.getAt(index).data.IMPAIRMENT_PROVISION_RATE;
                  baseRatio = jjzbzyStore.getAt(index).data.BASE_RATIO;
//                  if(jjzbzyStore.getAt(index).data.IS_REAL_ESTATE == 'CRM_YN_002'){
//                      Ext.getCmp('loanType').setValue('房地产贷款');
//                  }else if(jjzbzyStore.getAt(index).data.NO_RE_LOAN_CUST_LV !=''){
//                      Ext.getCmp('loanType').setValue('非房地产信用贷款');
//                  }else {
//                      Ext.getCmp('loanType').setValue('非房地产非信用其他贷款');
//                  }
              }
            }else{
                alert('无对应计算结果！');
            }
            /**************************************/
                if(cb5.getValue()!=''){
                    crmCode=cb5.getValue();
                  xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
                  xsStore.load({callback:function(){
                      if(!xsStore.getCount()==0){
                      Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
                      }
                  }});
                }
           /**************************************/
                if(cb6.getValue()!=''){
                    ftpNo=cb6.getValue();
                  ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
                  ftp1Store.load({callback:function(){
                      if(Ext.getCmp('mxttest').getValue()=='CRM_CCY_001'){
                          if(ftp1Store.getCount()!=0){
                          ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
                          }
                      }else{
                          if(ftp1Store.getCount()!=0){
                          ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
                          }
                      }
                      Ext.getCmp('ftpvalue').setValue(ftp);
                  }});
                }
           /**************************************/
                if(cb2.getValue()!=''){
                    crmCode=cb2.getValue();
                  xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
                  xsStore.load({callback:function(){
                      if(!xsStore.getCount()==0){
                      jintaizjfgxs=parseFloat(xsStore.getAt(0).data.code_name_1);
                      }
                      //alert(jintaizjfgxs);
                  }});
                }
        }}
    }
    function deleteInit(){
        /****************************************************************************************/
        var selectLength = resultGrid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } 
        
        else {
            if(confirm("确定删除吗?"))
            {
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = resultGrid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/customerloanprofit/'
                        +tempId+'.json?idStr='+idStr,
                method : 'DELETE',        
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : checkResult,
                failure : checkResult
            });
            
            };
    }
        /****************************************************************************************/
    }
    
    /***********************************存款edit和delete***************************************/
    function editInit1(){
        var selectLength = saveResultGrid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
        var infoRecord = saveResultGrid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            saveForm.getForm().loadRecord(infoRecord);
            /**************************************/
//                if(cb5.getValue()!=''){
//                    crmCode=cb5.getValue();
//                  xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
//                  xsStore.load({callback:function(){
//                      if(!xsStore.getCount()==0){
//                      Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
//                      }
//                  }});
//                }
           /**************************************/
            if(cb8.getValue()!=''){
                ftpNo=cb8.getValue();
              ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
              ftp1Store.load({callback:function(){
                  if(Ext.getCmp('depositCCY').getValue()=='CRM_CCY_001'){
                      if(ftp1Store.getCount()!=0){
                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
                      }
                  }else{
                      if(ftp1Store.getCount()!=0){
                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
                      }
                  }
                  Ext.getCmp('depositftpvalue').setValue(ftp);
              }});
            }
        }}
    }
    function deleteInit1(){
        /****************************************************************************************/
        var selectLength = saveResultGrid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } 
        
        else {
            if(confirm("确定删除吗?"))
            {
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = saveResultGrid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/customerdepositprofit/'
                        +tempId+'.json?idStr='+idStr,
                method : 'DELETE',        
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : checkResult1,
                failure : checkResult1
            });
            
            };
    }
        /****************************************************************************************/
    }
   /* 
    *//***********************************中间业务edit和delete***************************************//*
    function editInit2(){
        var infoRecord = middleResultGrid.getSelectionModel().getSelected();
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            midForm.getForm().loadRecord(infoRecord);
            *//**************************************//*
//                if(cb5.getValue()!=''){
//                    crmCode=cb5.getValue();
//                  xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
//                  xsStore.load({callback:function(){
//                      if(!xsStore.getCount()==0){
//                      Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
//                      }
//                  }});
//                }
           *//**************************************//*
//            if(cb8.getValue()!=''){
//                ftpNo=cb8.getValue();
//              ftp1Proxy.setApi(Ext.data.Api.actions.read, basepath+'/queryftp1.json?ftpNo='+ftpNo+'');
//              ftp1Store.load({callback:function(){
//                  if(Ext.getCmp('depositCCY').getValue()=='CRM_CCY_001'){
//                      if(ftp1Store.getCount()!=0){
//                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_RMBPRICE);
//                      }
//                  }else{
//                      if(ftp1Store.getCount()!=0){
//                      ftp=parseFloat(ftp1Store.getAt(0).data.SYSTEM_FTP_OTHERPRICE);
//                      }
//                  }
//                  Ext.getCmp('depositftpvalue').setValue(ftp);
//              }});
//            }
        }
    }
    function deleteInit2(){
        *//****************************************************************************************//*
        var selectLength = middleResultGrid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength < 1){
            alert('请选择需要删除的记录!');
        } 
        
        else {
            if(confirm("确定删除吗?"))
            {
            var selectRe;
            var tempId;
            var idStr = '';
            for(var i = 0; i<selectLength;i++)
            {
                selectRe = middleResultGrid.getSelectionModel()
                .getSelections()[i];
                tempId = selectRe.data.ID;
                idStr += tempId;
                if( i != selectLength-1)
                    idStr += ',';
            }
            Ext.Ajax.request({
                url : basepath+'/customermiddleprofit/'
                        +tempId+'.json?idStr='+idStr,
                method : 'DELETE',        
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : checkResult2,
                failure : checkResult2
            });
            
            };
    }
        *//****************************************************************************************//*
    }*/
//    cb7.focus();
//    Ext.getCmp('mxttest').focus(0);
    savePanel.on('afterlayout',function(){
//        alert('savePanel rendered!!');
        ckqxStore.load({callback:function(){
            Ext.getCmp('depositLT').focus();
            Ext.getCmp('depositLT').reset();
            Ext.getCmp('depositLT').triggerBlur();
            saveForm.getForm().reset();
        }});
    });
  
//    schedulePlanTabs.render();
//    cb7.blur();
//  Ext.getCmp('loanAccount').focus(0);
});