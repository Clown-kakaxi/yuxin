Ext.onReady(function(){
    Ext.QuickTips.init(); 
	var boxstore1 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=FGXS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		var boxstore2 = new Ext.data.Store({  
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
		var boxstore3 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=FTP'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		var boxstore4 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=YN'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});  
		
		var boxstore41 = new Ext.data.Store({  
            restful:true,   
            autoLoad :true,
            proxy : new Ext.data.HttpProxy({
                    url :basepath+'/lookup.json?name=DBXSHU'
                }),
                reader : new Ext.data.JsonReader({
                    root : 'JSON'
                }, [ 'key', 'value' ])
            });
		
		
		var boxstore5= new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=DKQX'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		var boxstore6= new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=PJXS'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		
		var crmCode;
		
		//信用客户评级系数
        var noReLoanCustLv ;
		
      //融资平台流动资金覆盖系数
        var jintaizjfgxs;
        
      //担保系数
      var jintaiDanbaoxishu;
      
      var ftpNo;
      
      var ftp;
      
    //行业系数
      var jintaiXishu;
      
      var ftpStore = new Ext.data.Store({
          restful:true,  
          autoLoad : true,
          proxy : new Ext.data.HttpProxy({url:basepath+'/queryftp.json'}),
          fields:['SYSTEM_FTP_NAME','SYSTEM_FTP_NO'],
          reader: new Ext.data.JsonReader({
//              totalProperty : 'list',
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
          proxy:xsProxy,
          fields:['code_name_1'],
          reader : new Ext.data.JsonReader({
              successProperty: 'success',
              root:'json.data'
          },['code_name_1'])
      });
		
				
	var help = new Ext.Panel({
		title:'公式说明',
		height: 100,
		html : '<font color="red">请填写各项因子,公式：</font>'
	});
	var factorPanel = new Ext.form.FormPanel({
	    layout:'form',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		margins : '12 0',
		height : 240,
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
								    id:'ecid',
								    name:'ID',
								    hidden:true
								}, {
                                    fieldLabel : '*方案名称',
                                    allowBlank:false,
                                    name : 'FA_NAME',
                                    xtype : 'textfield', // 设置为数字输入框类型
                                    labelStyle: 'text-align:right;',
                                    validator:function(value){
                                        for(var i=0;i<gridStore.getCount();i++){
                                        if(value == gridStore.getAt(i).data.FA_NAME && Ext.getCmp('ecid').getValue()!=gridStore.getAt(i).data.ID)
                                         return '方案名已存在';
                                        }
                                        return true;
                                    },
                                    anchor : '100%'
                                },{
                                            fieldLabel : '*利率浮动比率(%)',
                                            id : 'floatRate',
                                            allowBlank:false,
                                            allowNegative:false,minValue:0,
                                            decimalPrecision:4,
                                            maxValue:100,
                                            name : 'INTEREST_FLOAT_RATE',
                                            xtype : 'numberfield', // 设置为数字输入框类型
                                            labelStyle: 'text-align:right;',
                                            anchor : '100%'
                                        },{
                                            fieldLabel : '执行利率(%)',
                                            id:'runRate',
                                            maxValue:100,
                                            name : 'RUN_RATE',
                                            decimalPrecision:4,
                                            readOnly:true,
                                            xtype : 'numberfield', // 设置为数字输入框类型
                                            labelStyle: 'text-align:right;',
                                            anchor : '100%'
                                        },new Ext.form.ComboBox({
											hiddenName : 'COVER_STS',
											fieldLabel : '*融资平台现金流覆盖情况',
											id:'coverxs',
                                            allowBlank:false,
                                            //editable:false,
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : boxstore1,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											typeAhead : true,
											emptyText:'请选择',
											resizable : true,
											anchor : '100%'
										})]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    fieldLabel : '*贷款金额(万元)',
                                    id :'loanAccount',
                                    name : 'LOAN_ACCOUNT',
                                    maxLength:18,
                                    xtype : 'numberfield', // 设置为数字输入框类型
                                    allowNegative:false,minValue:0,
                                    allowBlank:false,
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%'
                                },new Ext.form.ComboBox({
									hiddenName : 'CURRENCY',
									fieldLabel : '*币种',
									id:'mxttest',
									value:'CRM_CCY_001',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore2,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									//editable:false,
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '100%'
								}),{
                                    fieldLabel : '产品权重(%)',
                                    id : 'prodRatio',
                                    allowBlank:false,
                                    maxValue:100,
                                    decimalPrecision:4,
                                    readOnly:true,
                                    name : 'PRODUCT_RATIO',
                                    xtype : 'numberfield', // 设置为数字输入框类型
                                    allowNegative:false,minValue:0,
                                    value:100,
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%'
                                },
											new Ext.form.ComboBox({
												id : 'isRealEstate',
												hiddenName : 'IS_REAL_ESTATE',
												fieldLabel : '*是否房地产行业',
	                                            allowBlank:false,
	                                            //editable:false,
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												store : boxstore4,
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择',
												resizable : true,
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
											fieldLabel : '*贷款期限',
											id:'mxttest1',
                                            allowBlank:false,
                                            //editable:false,
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : boxstore5,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											value:'CRM_DKQX_002',
											forceSelection : true,
											typeAhead : true,
											emptyText:'请选择',
											resizable : true,
											anchor : '100%'
										}), 
										new Ext.form.ComboBox({
                                            id : 'ftpinfo',
                                            hiddenName : 'FTP',
                                            fieldLabel : '*FTP',
                                            labelStyle: 'text-align:right;',
                                            triggerAction : 'all',
                                            store : ftpStore,
                                            displayField : 'SYSTEM_FTP_NAME',
                                            valueField : 'SYSTEM_FTP_NO',
                                            mode : 'local',
                                            //editable:false,
                                            forceSelection : true,
                                            typeAhead : true,
                                            emptyText:'请选择',
                                            resizable : true,
                                            anchor : '100%'
                                        }),{
                                    fieldLabel : '减值准备计提比率(%)',
                                    id : 'provisionRate',
                                    value:1,
                                    maxValue:100,
                                    readOnly:true,
                                    allowBlank:false,
                                    decimalPrecision:4,
                                    name : 'IMPAIRMENT_PROVISION_RATE',
                                    xtype : 'numberfield', // 设置为数字输入框类型
                                    allowNegative:false,minValue:0,
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%'
                                },
										new Ext.form.ComboBox({
											id : 'noReLoanCustLv',
											hiddenName : 'NO_RE_LOAN_CUST_LV',
											fieldLabel : '非房地产行业信用贷款的客户评级',
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : boxstore6,
                                            disabled:true,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											typeAhead : true,
											emptyText:'请选择',
											resizable : true,
											anchor : '100%'
										})]
							},{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 150, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
                                    fieldLabel : '基准利率(%)',
                                    id : 'referenceRate',
                                    allowBlank:false,
                                    decimalPrecision:4,
                                    readOnly:true,
                                    maxValue:100,
                                    name : 'REFERENCE_RATE',
                                    xtype : 'numberfield', // 设置为数字输入框类型
                                    labelStyle: 'text-align:right;',
                                    anchor : '100%'
                                },{
                                            fieldLabel : 'FTP值(%)',
                                            id:'ftpvalue',
                                            maxValue:100,
                                            name : 'ftpv',
                                            decimalPrecision:4,
                                            disabled:true,
                                            readOnly:true,
                                            xtype : 'numberfield', // 设置为数字输入框类型
                                            labelStyle: 'text-align:right;',
                                            anchor : '100%'
                                        },{
										    xtype:'numberfield',
											fieldLabel : '基本比例(%)', // 标签
											id : 'baseRatio',
                                            allowBlank:false,
                                            decimalPrecision:4,
                                            allowNegative:false,minValue:0,
                                            maxValue:100,
                                            readOnly:true,
											value:8,
											name : 'BASE_RATIO', // name:后台根据此name属性取值
										    labelStyle: 'text-align:right;',
											anchor : '100%' // 宽度百分比
										},new Ext.form.ComboBox({
										    id : 'guaranteeMode',
											hiddenName : 'GUARANTEE_MODE',
											fieldLabel : '*担保方式',
		                                    //editable:false,
											disabled:true,
											allowBlank:false,
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : boxstore41,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											typeAhead : true,
											emptyText:'请选择',
											resizable : true,
											anchor : '100%'
										})]
							}]
				},{
		               xtype:'fieldset',
		               title: '测算结果', 
		               items:[
		               new Ext.Panel({
				    layout:'column',
                    border : false,
                    items:[{columnWidth:.25,
                            layout:'form',
                            labelWidth:150,
                            items:[{
							id : 'loanAssessProfit',
							fieldLabel : '<span style="color:red" >贷款考核利润(万)</span>',
							name : 'LOAN_ASSESS_PROFIT',
							xtype : 'numberfield', // 设置为数字输入框类型
							readOnly : true,
							labelStyle: 'text-align:right;',
							anchor : '100%'
                            }]
                    },{
                        columnWidth:.25,
                        layout:'form',
                        labelWidth:150,
                        items:[{
                            id : 'economicCapitalOccupied',
                            fieldLabel : '<span style="color:red" >经济资本占用(万)</span>',
                            name : 'ECONOMIC_CAPITAL_OCCUPIED',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            readOnly : true,
                            labelStyle: 'text-align:right;',
                            anchor : '100%'
                    }]
                    },{
                        columnWidth:.25,
                        layout:'form',
                        labelWidth:150,
                        items:[{
                            id : 'economicIncrease',
                            fieldLabel : '<span style="color:red" >经济增加值(万)</span>',
                            name : 'ECONOMIC_INCREASE',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            readOnly : true,
                            labelStyle: 'text-align:right;',
                            anchor : '100%'
                        }]
                    }]
                     
             })]}],
		buttons : [{
            text:'产品收益测算',
            handler : function(){
                window.location.href="productBenefits.jsp";
            }
        },{
					text : '计算',
					handler : function() {
					    if(!factorPanel.getForm().isValid())
		                { 
		                    alert('请填写正确信息');
		                    return false;
		                }
						
						//贷款金额
						var loanAccount = Ext.getCmp("loanAccount").getValue();
						
						//利率浮动比率
						var floatRate = Ext.getCmp("floatRate").getValue()/100;
						
						
						//基准利率
						var referenceRate = Ext.getCmp("referenceRate").getValue()/100;
						
						//贷款考核利润=贷款金额×[基准利率×（1＋浮动比例）×（1－5.5%）－FTP]
						Ext.getCmp("loanAssessProfit").setValue(loanAccount*((referenceRate*(1+floatRate)*(1-0.055)-ftp/100)));
						
						//减值准备计提比率
						var provisionRate = Ext.getCmp("provisionRate").getValue()/100;
						
						//产品权重
						var prodRatio = Ext.getCmp("prodRatio").getValue()/100;
						
						
						//基本比例
						var baseRatio = Ext.getCmp("baseRatio").getValue()/100;
						
						//融资平台经济资本占用=基本比例×[贷款金额×（1—减值准备计提比率）]×产品权重×（融资平台流动资金覆盖系数-1）
						var coverScOccupied = baseRatio*(loanAccount*(1-provisionRate)*prodRatio*(jintaizjfgxs-1));
						
						coverScOccupied=parseFloat(coverScOccupied,10);
						
						Ext.getCmp('runRate').setValue(referenceRate*(1+floatRate)*100);
						
						//经济资本占用
						if(Ext.getCmp("isRealEstate").getValue() == 'CRM_YN_002')
							
						{
							//如果为房地产贷款 公式一[信用风险经济资本占用= 基本比例×[贷款金额×（1—减值准备计提比率）]×产品权重×行业系数+ 融资平台经济资本占用
							Ext.getCmp("economicCapitalOccupied").setValue(baseRatio*((loanAccount*(1-provisionRate)*prodRatio*jintaiXishu))+coverScOccupied); 
						}
						else if(Ext.getCmp("noReLoanCustLv").getValue() != null&&Ext.getCmp("noReLoanCustLv").getValue() !='')
						{
//						    alert('222');
							//如果为信用贷款  公式二[信用风险经济资本占用= 基本比例×[贷款金额×（1—减值准备计提比率）]×产品权重×信用客户评级系数 + 融资平台经济资本占用
						    var var1=parseFloat(baseRatio*((loanAccount*(1-provisionRate)*prodRatio*noReLoanCustLv)));
							Ext.getCmp("economicCapitalOccupied").setValue(var1+coverScOccupied); 
						}
						else
						{
							//如果为其他贷款（非房地产非信用） 公式三[信用风险经济资本占用= 基本比例×[ 贷款金额×（1—减值准备计提比率）]×产品权重×担保系数+ 融资平台经济资本占用
						    var var2=parseFloat(baseRatio*((loanAccount*(1-provisionRate)*prodRatio*jintaiDanbaoxishu)));
							Ext.getCmp("economicCapitalOccupied").setValue(var2+coverScOccupied);
						}
						
						Ext.getCmp("economicIncrease").setValue(Ext.getCmp("loanAssessProfit").getValue()-Ext.getCmp("economicCapitalOccupied").getValue()*0.15);
					
					}
				},/* {
					text : '系数设置',
					handler : function() 
					 {

					}
				},*/ {
					text : '保存',
					handler : function() 
					 {
					    if(!factorPanel.getForm().isValid())
                        { 
                            alert('请填写正确信息');
                            return false;
                        }
						Ext.Ajax.request({
						    url:basepath+'/custeconomiccaptlcalcu.json',
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
	
	
	/******************************************************************************/
	//是否房地产行业
	var cb = Ext.getCmp("isRealEstate");
    cb.addListener("select",function(){
        if(Ext.getCmp('isRealEstate').getValue()=="CRM_YN_002"||Ext.getCmp('isRealEstate').getValue()=="是"){
            Ext.getCmp('noReLoanCustLv').setDisabled(true);
            Ext.getCmp('guaranteeMode').setDisabled(true);
            xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode=CRM_HYXS_001');
            xsStore.load({callback:function(){
                if(!xsStore.getCount()==0){
                jintaiXishu=parseFloat(xsStore.getAt(0).data.code_name_1);
                }
            }});
        }
        else{
            if(cb1.getValue()==""||cb1.getValue()=="请选择"){
                Ext.getCmp('guaranteeMode').setDisabled(false);
            }else{
                Ext.getCmp('guaranteeMode').setDisabled(true);
            }
            Ext.getCmp('noReLoanCustLv').setDisabled(false);
                xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode=CRM_HYXS_002');
            xsStore.load({callback:function(){
                if(!xsStore.getCount()==0){
                jintaiXishu=parseFloat(xsStore.getAt(0).data.code_name_1);
                }
            }});
        }
        
        
    });
    
    var cb1 = Ext.getCmp('noReLoanCustLv');
    
    //非房地产行业的信用评级
    cb1.addListener("change",function(){
        if(cb1.getValue()==""||cb1.getValue()=="请选择"){
            Ext.getCmp('guaranteeMode').setDisabled(false);
        }else{
            Ext.getCmp('guaranteeMode').setDisabled(true);
            crmCode=Ext.getCmp('noReLoanCustLv').getValue();
            xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
            xsStore.load({callback:function(){
                if(!xsStore.getCount()==0){
                noReLoanCustLv=parseFloat(xsStore.getAt(0).data.code_name_1);
                }
            }});
        }
    });

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
          }});
        }
    });
    
    //担保系数获取
    var cb3 = Ext.getCmp('guaranteeMode');
    cb3.addListener("select",function(){
        if(cb3.getValue()!=''){
            crmCode=cb3.getValue();
          xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
          xsStore.load({callback:function(){
              if(!xsStore.getCount()==0){
              jintaiDanbaoxishu=parseFloat(xsStore.getAt(0).data.code_name_1);
              }
          }});
        }
    });
    
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
    
  //ftp
    var cb6 = Ext.getCmp('ftpinfo');
    cb6.addListener("select",function(){
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
    });
    
    //币种改变对FTP的影响
    var cb4 = Ext.getCmp('mxttest');
    cb4.addListener("select",function(){
        if(cb6.getValue() != ''){
            ftpNo=cb6.getValue();
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
                Ext.getCmp('ftpvalue').setValue(ftp);
            }});
        }
    });
	/******************************************************************************/
    if(cb5.getValue()!=''){
        crmCode=cb5.getValue();
      xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
      xsStore.load({callback:function(){
          if(!xsStore.getCount()==0){
              Ext.getCmp('referenceRate').setValue(parseFloat(xsStore.getAt(0).data.code_name_1));
          }
      }});
    }
	/******************************************************************************/

	function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
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
	
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
		
	var gridStore = new Ext.data.Store({
        restful:true,   
        proxy : new Ext.data.HttpProxy({url:basepath+'/economiccapitalcalculation.json'}),
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
                    name : 'CURRENCY'
                }, {
                    name : 'LOAN_LIMIT_TIME_GP'
                }, {
                    name : 'LOAN_LIMIT_TIME'
                }, {
					name : 'INTEREST_FLOAT_RATE'
				}, {
					name : 'REFERENCE_RATE'
				}, {
					name : 'RUN_RATE'
				}, {
                    name : 'FTP_ORA'
                }, {
                    name : 'FTP'
                }, {
					name : 'IMPAIRMENT_PROVISION_RATE'
				}, {
					name : 'BASE_RATIO'
				}, {
					name : 'PRODUCT_RATIO'
				}, {
                    name : 'IS_REAL_ESTATE_GP'
                }, {
                    name : 'IS_REAL_ESTATE'
                }, {
                    name : 'NO_RE_LOAN_CUST_LV_GP'
                },{
                    name : 'NO_RE_LOAN_CUST_LV'
                }, {
                    name : 'GUARANTEE_MODE_GP'
                }, {
                    name : 'COVER_STS_GP'
                }, {
                    name : 'GUARANTEE_MODE'
                }, {
                    name : 'COVER_STS'
                }, {
					name : 'LOAN_ASSESS_PROFIT'
				}, {
					name : 'ECONOMIC_CAPITAL_OCCUPIED'
				}, {
					name : 'ECONOMIC_INCREASE'
				}, {
					name : 'CALCULATE_TIME'
				}, {
                    name : 'CUST_ID'
                }, {
                    name : 'ORG_ID'
                }, {
                    name : 'FA_NAME'
                }])
	});

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
    
 // 表格工具栏
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
		store : gridStore,
		title : '<span style="font-weight:normal" >计算结果保存列表</span>',
		frame : true, // 是否渲染表单面板背景色
		width :document.body.scrollWidth-20,
		height : document.body.scrollHeight-200,
		//cm : cm, // 列模型
		tbar:tbar,
		bbar:bbar,
		sm : sm, // 复选框
		columns : [rownum,sm,{
            header : '测算结果编号',
            width : document.body.clientWidth / 9,
            dataIndex : 'ID',
            align : 'center'
        },{
					header : '方案名称',
					width : document.body.clientWidth / 9,
					dataIndex : 'FA_NAME',
					align : 'center'
				},{
					header : '贷款金额（万元）',
					width : document.body.clientWidth / 9,
					dataIndex : 'LOAN_ACCOUNT',
	                align : 'right',
	                renderer: money('0,000.00' )
				}, {
					header : '币种',
					width : document.body.clientWidth / 18,
					dataIndex : 'CURRENCY_GP',
					align : 'center'
				},{
                    header : '币种',
                    width : document.body.clientWidth / 18,
                    dataIndex : 'CURRENCY',
                    hidden:true,
                    align : 'center'
                },{
					header : '贷款期限 ',
					width : document.body.clientWidth / 9,
					dataIndex : 'LOAN_LIMIT_TIME_GP',
	                align : 'center'
				},{
                    header : '贷款期限（%） ',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'LOAN_LIMIT_TIME',
                    align : 'right',
                    hidden:true,
                    renderer: ratePercent(false)
                }, {
					header : '利率浮动比率（%）',
					width : document.body.clientWidth / 9,
					dataIndex : 'INTEREST_FLOAT_RATE',
	                align : 'right',
	                renderer: ratePercent(false)
				}, {
					header : '基准利率（%）',
					width : document.body.clientWidth / 9,
					dataIndex : 'REFERENCE_RATE',
	                align : 'right',
	                renderer: ratePercent(false)
				}, {
					header : '执行利率',
					width : document.body.clientWidth / 9,
					dataIndex : 'RUN_RATE',
	                align : 'right',
	                renderer: ratePercent(false)
				}, {
					header : 'FTP',
					width : document.body.clientWidth*3 / 18,
					dataIndex : 'FTP_ORA',
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
				},{
					header : '减值准备计提比率',
					width : document.body.clientWidth / 9,
					dataIndex : 'IMPAIRMENT_PROVISION_RATE',
	                align : 'right',
	                renderer: ratePercent(false)
				}, {
					header : '基本比例',
					width : document.body.clientWidth / 9,
					dataIndex : 'BASE_RATIO',
	                align : 'right',
	                renderer: ratePercent(false)
				}, {
					header : '产品权重',
					width : document.body.clientWidth / 9,
					dataIndex : 'PRODUCT_RATIO',
					align : 'center'
				}, {
                    header : '是否房地产行业',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'IS_REAL_ESTATE_GP',
                    align : 'center'
                }, {
                    header : '非房地产行业信用贷款的客户评级',
                    width : document.body.clientWidth / 6,
                    dataIndex : 'NO_RE_LOAN_CUST_LV_GP',
                    align : 'center'
                }, {
                    header : '融资平台现金流覆盖情况',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'COVER_STS_GP',
                    align : 'center'
                },{
                    header : '是否房地产行业',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'IS_REAL_ESTATE',
                    hidden:true,
                    align : 'center'
                }, {
                    header : '非房地产行业的信用评级',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'NO_RE_LOAN_CUST_LV',
                    hidden:true,
                    align : 'center'
                }, {
                    header : '融资平台现金流覆盖情况',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'COVER_STS',
                    hidden:true,
                    align : 'center'
                }, {
					header : '担保系数',
					width : document.body.clientWidth / 9,
					dataIndex : 'GUARANTEE_MODE_GP',
					align : 'center'
				},  {
                    header : '担保系数',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'GUARANTEE_MODE',
                    hidden:true,
                    align : 'center'
                },{
					header : '贷款考核利润',
					width : document.body.clientWidth / 9,
					dataIndex : 'LOAN_ASSESS_PROFIT',
	                align : 'right',
	                renderer: money('0,000.00' )
				}, {
                    header : '经济资本占用',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'ECONOMIC_CAPITAL_OCCUPIED',
                    align : 'center'
                },{
                    header : '经济增加值',
                    width : document.body.clientWidth / 9,
                    dataIndex : 'ECONOMIC_INCREASE',
                    align : 'center'
                }, {
					header : '测算日期时间',
					width : document.body.clientWidth / 9,
					dataIndex : 'CALCULATE_TIME',
					align : 'center'
				}, {
					header : '用户ID',
					width : document.body.clientWidth / 9,
					dataIndex : 'CUST_ID',
					align : 'center'
				}, {
					header : '机构号',
					width : document.body.clientWidth / 9,
					dataIndex : 'ORG_ID',
					align : 'center'
				}],
		stripeRows : true
	});
	
	var mainPanel = new Ext.Panel({
		title: '经济资本测算工具',
		renderTo:'economicCapital',
		items:[factorPanel,resultGrid]
	});
	
	
	
	function editInit(){
        var infoRecord = resultGrid.getSelectionModel().getSelected();
        var selectLength = resultGrid.getSelectionModel()
        .getSelections().length;
        
        if(selectLength > 1){
            alert('请选择一条记录!');
        } else{
        if(infoRecord == null||infoRecord == ''){
            Ext.Msg.alert('提示','请选择一行数据');
        }else{
            factorPanel.getForm().loadRecord(infoRecord);
            /**************************************/
            if(Ext.getCmp('isRealEstate').getValue()=="CRM_YN_002"||Ext.getCmp('isRealEstate').getValue()=="是"){
                Ext.getCmp('noReLoanCustLv').setDisabled(true);
                Ext.getCmp('guaranteeMode').setDisabled(true);
                xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode=CRM_HYXS_001');
                xsStore.load({callback:function(){
                    if(!xsStore.getCount()==0){
                        
                    jintaiXishu=parseFloat(xsStore.getAt(0).data.code_name_1);
                    }
                }});
            }
            else{
                if(cb1.getValue()==""||cb1.getValue()=="请选择"){
                    Ext.getCmp('guaranteeMode').setDisabled(false);
                }else{
                    Ext.getCmp('guaranteeMode').setDisabled(true);
                    crmCode=Ext.getCmp('noReLoanCustLv').getValue();
                    xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
                    xsStore.load({callback:function(){
                        if(!xsStore.getCount()==0){
                            noReLoanCustLv=parseFloat(xsStore.getAt(0).data.code_name_1);
                        }
                    }});
                }
                Ext.getCmp('noReLoanCustLv').setDisabled(false);
                    xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode=CRM_HYXS_002');
                xsStore.load({callback:function(){
                    if(!xsStore.getCount()==0){
                        
                    jintaiXishu=parseFloat(xsStore.getAt(0).data.code_name_1);
                    }
                }});
            }
            /**************************************/
            
            /**************************************/
                if(cb2.getValue()!=""){
                    crmCode=cb2.getValue();
                  xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
                  xsStore.load({callback:function(){
                      if(!xsStore.getCount()==0){
                      jintaizjfgxs=parseFloat(xsStore.getAt(0).data.code_name_1);
                      }
                  }});
                }
           
            /**************************************/
                if(cb3.getValue()!=""){
                    crmCode=cb3.getValue();
                  xsProxy.setApi(Ext.data.Api.actions.read, basepath+'/querylookupxs.json?crmCode='+crmCode+'');
                  xsStore.load({callback:function(){
                      if(!xsStore.getCount()==0){
                      jintaiDanbaoxishu=parseFloat(xsStore.getAt(0).data.code_name_1);
                      }
                  }});
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
        }
        }
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
                url : basepath+'/custeconomiccaptlcalcu/'
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
	
	gridStore.load({
        params : {
            start : 0,
            limit : bbar.pageSize
        }
    });
	
	boxstore2.load({callback:function(){
	    Ext.getCmp('mxttest').setValue('CRM_CCY_001');
	    
	}});
	
	boxstore5.load({callback:function(){
	    Ext.getCmp('mxttest1').setValue('CRM_DKQX_002');
	    
	}});
});