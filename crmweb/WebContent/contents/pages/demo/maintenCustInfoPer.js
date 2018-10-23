Ext.onReady(function() {
	var tmepMethod = 'add';
	var sOrgIdJson={'orgid':[]};
	var boxstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000080'
    
				
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var certstore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000040'
    
				
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	var boxstore8 = new Ext.data.Store({  
		sortInfo: {
	    field: 'key',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=P_CUST_GRADE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore5 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querycustomerbase.json'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'ID', 'CUST_BASE_NAME' ])
		});
		var boxstore6 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/queryrollcustomer.json'
				}),
				reader : new Ext.data.JsonReader({
					root : 'json.data'
				}, [ 'ROLL_ID', 'ROLL_NAME' ])
			});

	var boxstore2 = new Ext.data.Store({  
		sortInfo: {
    	    field: 'key',
    	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
    	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup_gp.json?name=QYGM'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore3 = new Ext.data.Store({ 
		sortInfo: {
    	    field: 'key',
    	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
    	},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup_gp.json?name=KHQYGM'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	var boxstore9 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup_gp.json?name=YN'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});  
		var boxstore10 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup_gp.json?name=HYLX1'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		var boxstore11 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup_gp.json?name=HYLX2'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});  
		var boxstore12 = new Ext.data.Store({  
			sortInfo: {
        	    field: 'key',
        	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
        	},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup_gp.json?name=XSE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		var boxstore13 = new Ext.data.Store({  
			sortInfo: {
        	    field: 'key',
        	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
        	},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup_gp.json?name=CYRYS'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		var boxstore14 = new Ext.data.Store({  
			sortInfo: {
        	    field: 'key',
        	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
        	},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup_gp.json?name=XSZE'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});

		var boxstore18 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=CUST_LEVEL1'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
		var boxstore19 = new Ext.data.Store({  
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=CUST_LEVEL2'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			});
	     var boxstore20 = new Ext.data.Store({  
	        	sortInfo: {
	        	    field: 'key',
	        	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	        	},

		            restful:true,   
		            autoLoad :true,
		            proxy : new Ext.data.HttpProxy({
		                    url :basepath+'/lookup.json?name=CUST_LEVEL4'
		                }),
		                reader : new Ext.data.JsonReader({
		                    root : 'JSON'
		                }, [ 'key', 'value' ])
		            });
	     var boxstore21 = new Ext.data.Store({  
	        	sortInfo: {
	        	    field: 'key',
	        	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	        	},

		            restful:true,   
		            autoLoad :true,
		            proxy : new Ext.data.HttpProxy({
		                    url :basepath+'/lookup_gp.json?name=JJZZLX'
		                }),
		                reader : new Ext.data.JsonReader({
		                    root : 'JSON'
		                }, [ 'key', 'value' ])
		            });
		   //新增客户群
		  var addCustomerGroup=new Ext.FormPanel({
			//layout:'fit',
		  	//title : '新增客户群',
			name:'addCustomerGroup',
		    id:'addCustomerGroup',
		  	labelAlign:'right',
			frame:true,
			border:false,
//			style:'padding:10 10 10 10',
						layout:'column',
						items : [
								{
									columnWidth : .5,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										fieldLabel : '客户群名称',
										labelStyle:{
											width:'120px'
										},	
										Width:'100',
										name : 'customerBaseName',
										anchor : '90%'
									}]
								}, 
								{
									columnWidth : .5,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										disabled:true,
										fieldLabel : '客户群编号',
										labelStyle:{
											width:'120px'
										},	
										Width:'100',
										id : 'customerBaseNumber',
										name : 'customerBaseNumber',
										anchor : '90%'
									}]
					}
					,
					{
						layout:'form',
						items:{
							name : 'customerBaseDescribe',
							height :155,
							anchor:'80%',
							xtype:'textarea',
							fieldLabel : '客户群描述'
						}
					}
					]
		});
	
    
	 // 表格工具栏
		var cusGrouptbar = new Ext.Toolbar({
			items : [ {
						text : '新增客户群',
						//iconCls : 'page_findIcon',
						handler : function() {
							var win=new Ext.Window({
								layout : 'fit',
										closable : true,
										resizable : false,
										collapsible : false,
										height:300,
										width:700,
										draggable : true,
										closeAction : 'hide',
										
										//titleCollapse : false,
										modal : true, // 模态窗口 
										//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
										animCollapse : false,
//										maximizable : true,
										border : false,
										closable : true,
										animateTarget : Ext.getBody(),
										constrain : true,
										items : [addCustomerGroup],
										buttonAlign:'center',
										buttons:[{
											text:'保存',
											handler:function()
											{Ext.Ajax.request({
											    url:basepath+'/customer-base.json',
											    method: 'POST',
											    form:addCustomerGroup.getForm().id,
												success : function(response) {
					    							Ext.Msg.alert('提示', '成功');
					    						},
					    						failure : function(response) {
					    							Ext.Msg.alert('提示', response.responseText);
					    						},
													params : {
														'customerBaseNumber':Ext.getCmp('customerBaseNumber').getValue(),
														'operate':'add'
													}
											});
												win.hide();
											}
										},{
		          				  			text: '取消',
		           				  			handler:function(){
		            			 		    win.hide();
		            						}
		       			 				}]	
								});
							Ext.Ajax.request({
								url:basepath+'/querycustomerbasenumber.json',
							    method: 'GET',
							    success:function(response){
							    	var json=Ext.util.JSON.decode(response.responseText);
							    	 Ext.getCmp('customerBaseNumber').setValue(json.json.data);
						    }
							});
							win.show();
						}
					}
					]
		});
    var cusGroupsm = new Ext.grid.CheckboxSelectionModel();
	
	var cusGrouprownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});


    // 定义列模型
   	var cusGroupcm = new Ext.grid.ColumnModel([cusGrouprownum,
   	       {header : 'id', dataIndex : 'id',sortable : true,width : 150,hidden :true}, 
   	       {header : '客户群编号', dataIndex : 'customerBaseNumber',sortable : true,width : 150 }, 
              {header : '客户群名称',dataIndex : 'customerBaseName',sortable : true,width : 150}, 
              {header : '客户群创建日期',dataIndex : 'customerBaseCreateDate',sortable : true,width : 150},
              {header : '客户群成员数', dataIndex : 'customerBaseMemberNum',renderer:function(value){
                  if(value==''){
                      return "0";
                  }else{
                      return value;
                  }
              },sortable : true,width : 150 }, 
              {header : '客户群描述',dataIndex : 'customerBaseDescribe',sortable : true,width : 150}
   			]);

	/**
	 * 数据存储
	 */

	 var simple1 = new Ext.FormPanel({
	        frame:true,
	        id:'queryGroup',
	        bodyStyle:'padding:5px 5px 0',
	        split:true,
			height:100,
	           // xtype:'fieldset',
	            title: '查询条件',
	        	
	            		layout:'column',
	            		labelWidth : 100, // 标签宽度
	                     items:[{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户群名称',
	                             labelStyle: 'text-align:right;',
	                             name: 'CUST_BASE_NAME',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'datefield',
	                             fieldLabel: '客户群创建日期',
	                             labelStyle: 'text-align:right;',
	                             format:'Y-m-d', //日期格式化
	                             name: 'CUST_BASE_CREATE_DATE',
	                             anchor:'95%'
	                     }
	            ]}],
			buttonAlign:'center',
	        buttons: [{
	            text: '查询',
	            handler : function() {
	            	cusGroupstore.load(
	        			/*	{ params : {
                                  start : 0,
                                  limit : cusGroupbbar.pageSize }}*/ );
              
			}
	        },{
	            text: '重置',
	            handler : function() {
	            	simple1.getForm().reset();   
			}
	        }]
	    });
		var cusGroupstore = new Ext.data.Store({
			restful:true,
			proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerbase.json'
	       /* 	success : function(response) {
					var resultArray = Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert('提示', response.responseText);
				}*/
	        }),
	        reader: new Ext.data.JsonReader({
	        totalProperty : 'json.count',
	        root:'json.data'
	        }, [{name: 'id', mapping: 'ID'},{name: 'customerBaseNumber', mapping: 'CUST_BASE_NUMBER'},{name: 'customerBaseName', mapping: 'CUST_BASE_NAME'},{name: 'customerBaseCreateDate', mapping: 'CUST_BASE_CREATE_DATE'},{name: 'customerBaseMemberNum', mapping: 'MEMBERSNUM',type:'float'},{name: 'customerBaseDescribe', mapping: 'CUST_BASE_DESC'}])
		});
		cusGroupstore.on('beforeload', function() {
	    	var conditionStr =  simple1.getForm().getValues(false);
	        this.baseParams = {
	                "condition":Ext.encode(conditionStr)
	        };
	     });


	 var cusGrouppagesize_combo = new Ext.form.ComboBox({
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
         value : '100',
         editable : false,
         width : 85
     });
    var cusGroupnumber = parseInt(cusGrouppagesize_combo.getValue());
   
    cusGrouppagesize_combo.on("select", function(comboBox) {
    	cusGroupbbar.pageSize = parseInt(cusGrouppagesize_combo.getValue()),
    	 alert(cusGroupbbar.pageSize);
    	cusGroupstore.load({
					params : {
						start : 0,
						limit : cusGroupparseInt(pagesize_combo.getValue())
					}
				});
	});
	var cusGroupbbar = new Ext.PagingToolbar({
        pageSize : cusGroupnumber,
        store : cusGroupstore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', cusGrouppagesize_combo
                 ]
    });
	    var cusGroupGrid = new Ext.grid.GridPanel({
	    	height :285,
			width : 886,
	    	//title:'客户群列表',
	    	tbar:cusGrouptbar,
	        store: cusGroupstore,
			cm : cusGroupcm,
			//autoExpandColumn:'affichename',
			//sm:cusGroupsm,
			bbar : cusGroupbbar,
			selModel:new Ext.grid.RowSelectionModel({
					singleSelect:true
					})
	       
	    });
    var cusGroupQueryWind = new Ext.Window({
       	height:'450',
       	width:'900',
       	closable:true,
       	closeAction:'hide',
       	frame:true,
//       	maximized:true,
       	maximizable:true,
        //layout:'border',

    	items:[
//    		   		region:'north',
////    			   	height:100,
//    			   	items:simple,
//    			   	split:true
//    			 },
    			   simple1,
    			   cusGroupGrid
    			 ],

       	buttonAlign:'center',
       	buttons:[
       	{
       		text:'加入该群',
       		handler:function()
       		{
       			batchAdd();
       			//cusGroupQueryWind.hide();
       		}
       	},   	
       	{
       		text:'返 回',
       		handler:function()
       		{
       			cusGroupQueryWind.hide();
       		}
       	}
       	]
       });

							
  	
	
	
	 var simple = new Ext.FormPanel({
	        //labelWidth: 75, 
	        frame:true,
			title : '<span style="font-weight:normal">高级查询条件</span>',
	        bodyStyle:'padding:5px 5px 0',
	        width: 800,
	        items: [{
	            	//xtype:'fieldset',
	            //title: '高级查询条件',
	           autoHeight:true,
	       
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         labelWidth : 110, 
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户号',
	                             labelStyle: 'text-align:right;',
	                             name: 'CUST_ZH_NAME',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'HY_CLASS2',
									fieldLabel : '行业',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore8,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'ROLL_NAME',
									fieldLabel : '客户名单',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore6,
									displayField : 'ROLL_NAME',
									valueField : 'ROLL_ID',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								}),{
	                             xtype:'textfield',
	                             fieldLabel: '归属网点',
	                             labelStyle: 'text-align:right;',
	                             name: 'BELONG_INSTN',
	                             anchor:'95%'
	                         },{
	                        	 xtype:'textfield',
	                             fieldLabel: '行业小类',
	                             labelStyle: 'text-align:right;',
	                             name: 'HY_TYP_DESC',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'SPECIFY_FLG',
									fieldLabel : '是否本行股东',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore9,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								}),{
	                             xtype:'datefield',
	                             fieldLabel: '营业执照到期日期',
	                             labelStyle: 'text-align:right;',
	                             format:'Y-m-d', //日期格式化
	                             name: 'MATURE_DT',
	                             anchor:'95%'
	                         },{
	                        	 xtype:'datefield',
	                             fieldLabel: '组织机构代码有效期到期日',
	                             labelStyle: 'text-align:right;',
	                             format:'Y-m-d', //日期格式化
	                             name: 'ZZDM_VALDT',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         labelWidth : 110, 
	                         layout: 'form',
	                         items: [{
	                        	 xtype:'textfield',
	                             fieldLabel: '组织机构代码',
	                             labelStyle: 'text-align:right;',
	                             name: 'CUST_ZZDM',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'CRM_SCOPE',
									fieldLabel : '考核口径客户规模',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore3,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
	                        	 xtype:'datefield',
	                             fieldLabel: '建立日期起始日',
	                             format:'Y-m-d', //日期格式化
	                             labelStyle: 'text-align:right;',
	                             name: 'BGN_DT_BEFORE',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
                                 hiddenName : 'CUST_LEV',
                                 fieldLabel : '客户评级',
                                 labelStyle: 'text-align:right;',
                                 triggerAction : 'all',
                                 store : boxstore20,
                                 displayField : 'value',
                                 valueField : 'key',
                                 mode : 'local',
                                 forceSelection : true,
                                 typeAhead : true,
                                 emptyText:'请选择',
                                 resizable : true,
                                 anchor : '95%'
                         }),
	                         new Ext.form.ComboBox({
									hiddenName : 'HY_TYP2',   
									fieldLabel : '行业类型1',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore10,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'ASS_AMT',   
									fieldLabel : '资产总额起始金额',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore14,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'BUSINESS_JSR',
									fieldLabel : '销售额',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore12,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.25,
	                         labelWidth : 110, 
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
									hiddenName : 'STS',
									fieldLabel : '客户状态',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'CUST_BIG_LEV',
									fieldLabel : '大客户级别',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore18,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
	                        	 xtype:'datefield',
	                             fieldLabel: '建立日期截止日',
	                             format:'Y-m-d', //日期格式化
	                             labelStyle: 'text-align:right;',
	                             name: 'BGN_DT_AFTER',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'JJZZ_TYP',
									fieldLabel : '经济组织类型',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore21,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								})
	                         ,new Ext.form.ComboBox({
									hiddenName : 'HY_TYP3',   
									fieldLabel : '行业类型2',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore11,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'GROUP_FLG',
									fieldLabel : '是否集团客户',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore9,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'HN_FLG',
									fieldLabel : '是否高新科技企业',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore9,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         labelWidth : 110, 
	                         items: [new Ext.form.ComboBox({
									hiddenName : 'CUST_SCOPE',
									fieldLabel : '客户规模',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore2,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
							}),new Ext.form.ComboBox({
								hiddenName : 'CUST_SMALL_LEV',
								fieldLabel : '中小客户级别',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore19,
								displayField : 'value',
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),new Ext.form.ComboBox({
								hiddenName : 'CUST_BASE_NAME',
								fieldLabel : '客户群组',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore5,
								displayField : 'CUST_BASE_NAME',
								valueField : 'ID',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),new Ext.form.ComboBox({
								hiddenName : 'HY_CLASS',
								fieldLabel : '行业大类',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore8,
								displayField : 'value',
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),{
	                             fieldLabel: '建立网点',
	                             xtype:'textfield',
	                             labelStyle: 'text-align:right;',
	                             name: 'CREATE_ORG',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
									hiddenName : 'MARKET_FLG',
									fieldLabel : '是否上市公司',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore9,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									emptyText:'请选择',
									typeAhead : true,
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
									hiddenName : 'EMPLOYEE_NUM',   
									fieldLabel : '从业人员数',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore13,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									listHeight : 100, 
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								})]
	                     }
	            ]}
	            ]}]
	    });
	 var addPotentialCustomerPanel = new Ext.FormPanel({
		 id:'add',
		  frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        items: [{
	           autoHeight:true,
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*客户ID',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
//	                             allowBlank : false,
	                             hidden:true,
	                             id: 'custId',
	                             name: 'custId',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '*客户名称',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             id: 'custZhName',
	                             name: 'custZhName',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	 							name : 'custTyp',
	 							id:'custTyp',
								fieldLabel : '*客户大类',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : boxstore,
								displayField : 'value',
								allowBlank : false,
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),{
	                             xtype:'textfield',
	                             fieldLabel: '联系人',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
//	                             allowBlank : false,
	                             id: 'linkUser',
	                             name: 'linkUser',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
									name : 'certType',
									id:'certType',
									fieldLabel : '*证件类型',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : certstore,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),{
	                             xtype:'textfield',
	                             fieldLabel: '客户英文名称',
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             id: 'custEnName',
	                             name: 'custEnName',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '联系电话',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             vtype: 'number',
//	                             allowBlank : false,
	                             id: 'linkPhone',
	                             name: 'linkPhone',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户状态',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
//	                             allowBlank : false,
	                             hidden:true,
	                             id: 'custStat',
	                             name: 'custStat',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '*证件号码',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             vtype: 'number',
	                             allowBlank : false,
	                             id: 'certNum',
	                             name: 'certNum',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '其它名称',
	                             maxLength:50,
	                             labelStyle: 'text-align:right;',
	                             id: 'otherName',
	                             name: 'otherName',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '邮编',
	                             vtype: 'number',
								 maxLength : '6',
								 minLength : '6',
	                             labelStyle: 'text-align:right;',
//	                             allowBlank : false,
	                             id: 'postNo',
	                             name: 'postNo',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.99,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textarea',
	                             fieldLabel: '通讯地址',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
//	                             allowBlank : false,
	                             id: 'addr',
	                             name: 'addr',
	                             anchor:'99%'
	                         }]
	                     }
	            ]}
	            ]}]
	    });
	    //潜在客户管理
	 var addPotentialCustomerWindow = new Ext.Window(
				{
				    title:'新增潜在客户',
					layout : 'fit',
					width : 700,
					height : 280,
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
					items : [addPotentialCustomerPanel],
					buttons : [
							{
								text : '保存',
								handler : function(){
									if(!addPotentialCustomerPanel.getForm().isValid()){
										Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
									return false;
									}
									Ext.Ajax.request({
										url : basepath + '/myPotentialCustomer.json?a=1',
										method : 'POST',
										form : addPotentialCustomerPanel.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										params : {
											'custId':Ext.getCmp('custId').getValue(),
											'custZhName':Ext.getCmp('custZhName').getValue(),
											'custTyp':Ext.getCmp('custTyp').getValue(),
											'linkUser':Ext.getCmp('linkUser').getValue(),
											'postNo':Ext.getCmp('postNo').getValue(),
											'custEnName':Ext.getCmp('custEnName').getValue(),
											'certType':Ext.getCmp('certType').getValue(),
											'linkPhone':Ext.getCmp('linkPhone').getValue(),
											'custStat':Ext.getCmp('custStat').getValue(),
											'otherName':Ext.getCmp('otherName').getValue(),
											'certNum':Ext.getCmp('certNum').getValue(),
											'addr':Ext.getCmp('addr').getValue(),
											'operate':'add'
										},
										success :checkResult,
								  		failure:function(a,b){
											var t = Ext.decode(a.responseText);
											Ext.Msg.alert('系统提示','客户已重复，无法新增!');
										}
									});
									function checkResult(response) {
										var resultArray = Ext.util.JSON.decode(response.status);
										var resultError = response.responseText;
										if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
											Ext.Msg.alert('提示', '操作成功');
											addPotentialCustomerPanel.getForm().reset();
											addPotentialCustomerWindow.hide();
											store.reload({
									params : {
									start : 0,
									limit :bbar.pageSize
									                    }
									                });
										} else{
											if(resultArray == 403){
												Ext.Msg.alert('提示', response.responseText);
												}
											else {
											Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
											store.reload({
									params : {
									start : 0,
									limit :bbar.pageSize
									                    }
									                });
										}
									}
									};
								}
							}, {
								text : '重置',
								id : 'btnReset',
								handler : function() {
								addPotentialCustomerPanel.getForm().reset();   
									//clearForm(addRoleFormPanel.getForm());
								}
							}, {
								text : '关闭',
								handler : function() {
								addPotentialCustomerWindow.hide();
								}
							} ]
				});
	    
		var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 1000,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				//collapsible : true,// 是否可收缩
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
								addRoleWindow.hide();
								 Ext.getCmp('exportbatten').formPanel=simple;
								//Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							handler : function() {
								simple.getForm().reset();
							}
						}, {
							text : '关闭',
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	/*	var qFormsdfsdf= new Ext.form.SearchField({ 
				fieldLabel : '客户号', 
				name : 'CUST_ZH_NAMEsdf',
				store: store          
				//width:320 
				});*/
	
		var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 97,
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
//							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [
				         {
							fieldLabel : '客户号',
							name : 'CUST_ID',
							xtype : 'textfield', // 设置为数字输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
						},new Ext.form.ComboBox({
							id : 'custTyps1',
							hiddenName : 'CUST_TYP',
							fieldLabel : '客户类型',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							hidden : true,
							resizable : true,
							anchor : '90%'
						}),new Ext.ux.form.CustMgrField({ 
							fieldLabel : '所属客户经理', 
							id:'CUST_MANAGER',
							labelStyle: 'text-align:right;',
							name : 'CUST_MANAGER',
							//store: store, 
							//singleSelected:true,
							anchor : '90%'
							})]
						}, {
							columnWidth : .25,
							layout : 'form',
//							labelWidth: 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '客户名称',
								id:'CUST_ZH_NAME',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}/*new Ext.ux.form.CustomerQueryField({ 
											fieldLabel : '客户名称', 
											labelStyle: 'text-align:right;',
											name : 'CUST_ZH_NAME',
											id:'sdfsaf',
											anchor : '90%',
											callback: function() {
										}  
											})*/,new Ext.form.ComboBox({
												hiddenName : 'CUST_LEV',
												fieldLabel : '客户级别',
												labelStyle: 'text-align:right;',
												triggerAction : 'all',
												store : boxstore8,
												displayField : 'value',
												valueField : 'key',
												mode : 'local',
												forceSelection : true,
												typeAhead : true,
												emptyText:'请选择 ',
											//	value : '对公',
												resizable : true,
												anchor : '90%'
											})]
						}, {
							columnWidth : .25,
							layout : 'form',
//							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '证件号码',
								name : 'CERT_NUM',
								id:'CERT_NUM',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},{
								fieldLabel : '数据日期',
								name : 'dataDate',
								xtype : 'datefield',
                                format:'Y-m-d',
								labelStyle: 'text-align:right;',
								anchor : '90%'
								
							}
							]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
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
							}),{
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
					text : '查询',

					handler : function() {
				        store.on('beforeload', function() {
				        	var conditionStr =  qForm.getForm().getValues(false);
				        	
				        	
				            this.baseParams = {
				                    "condition":Ext.encode(conditionStr)
				                    
				            };
					});
				        if(Ext.getCmp('CUST_MANAGER').getValue()!=''){
				        var userId=Ext.getCmp('CUST_MANAGER').userId;
						Ext.getCmp('custMgrId').setValue(userId.aId);
				        }
						store.load({      
							  params : {
                                   start : 0,
                                   limit : bbar.pageSize/*,
                                   userId:Ext.encode(userId.aId)*/}});     
				
				   }}, {
					text : '高级查询',
						handler : function() {
							addRoleWindow.show();
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
   Ext.getCmp('custTyps1').setValue('2');

	
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
		    {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true},
		    {header : '客户维护人',dataIndex : 'MGR_NAME',width : 150,hidden : true ,sortable : true},
		 
		    {header : '一级分行',dataIndex : 'supbrId',width : 200,sortable : true},
		    {header : '二级分行',width : 200,sortable : true},
		    {header : '所属机构',dataIndex : 'INSTITUTION_NAME',sortable : true},
		    {header : '客户状态',dataIndex : 'CUST_STAT_ORA',width : 150,sortable : true},
		    {header : '客户类型',dataIndex : 'custTyp',width : 200,sortable : true,hidden:true},
		    {header : '客户类型',dataIndex : 'CUST_TYP_ORA',width : 200,sortable : true},
		    {header : '客户级别',dataIndex : 'CUST_LEV_ORA',width : 200,hidden : true ,sortable : true},
		    {header : '客户网银状态',dataIndex : 'isEgoldCustOra',width : 150,sortable : true},

	/*	    {header : '开户性质',width : 200,sortable : true},
		    {header : '开户时间',width : 200,sortable : true},
		    {header : '销户时间',width : 200,sortable : true},
		    {header : '开户性质',width : 200,sortable : true},*/
		    {header : '行业门类',dataIndex:'HY_CLASS_ORA',width : 200,sortable : true},
		    {header : '组织类别',dataIndex:'ORGTYPE_ORA',width : 200,sortable : true},
		    {header : '所有制',dataIndex:'ORGINFO_ORA',width : 200,sortable : true},
		    {header : '客户规模',dataIndex:'QYGM_ORA',width : 200,sortable : true},
		    {header : '利润贡献度',dataIndex :'rotecb',width : 200,sortable : true},
		    {header : '存款余额',dataIndex :'ckbal',width : 200,sortable : true},
		    {header : '存款日均',dataIndex :'ckbalavg',width : 200,sortable : true},
		    {header : '贷款余额',dataIndex :'loanbal',width : 200,sortable : true},
		    {header : '贷款日均',dataIndex :'loanbalavg',width : 200,sortable : true},
		    {header : '承兑余额',dataIndex :'cdbal',width : 200,sortable : true},
		    {header : '其中:电票承兑余额',dataIndex :'dpcdbal',width : 200,sortable : true},
		    {header : '承兑累计',dataIndex :'cdsum',width : 200,sortable : true},
		    {header : '其中：电票承兑累计',dataIndex :'dpcdsum',width : 200,sortable : true},
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
			        proxy : new Ext.data.HttpProxy({url:basepath+'/comCustomerInfo.json'}),
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
	var checkedNodessd = '';
	// 表格工具栏

	var tbar = new Ext.Toolbar({

				items : [
					{
					text : '客户视图',
					handler : function() {
			        var checkedNodes = grid.getSelectionModel().selections.items;
						if(checkedNodes.length==0)
							{
								Ext.Msg.alert('提示', '未选择任何客户');
								return ;
							}
						else if(checkedNodes.length>1)
						{
							Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
							return ;
						}
						var viewWindow = new Com.yucheng.crm.cust.ViewWindow({
							id:'viewWindow',
							custId:checkedNodes[0].data.custId,
							custName:checkedNodes[0].data.custZhName,
							custTyp:checkedNodes[0].data.custTyp
						});
						viewWindow.show();
			
					}
				}/*,'-',
				{
					text : '加入客户群',
					handler : function() {
						var checkedNodes = grid.getSelectionModel().selections.items;
							if(checkedNodes.length==0)
							{
								Ext.Msg.alert('提示', '未选择任何客户');
								return ;
							}
						  cusGroupQueryWind.show();

					}
				},'-',
				{
					text : '设为关注客户',
					handler : function() {
						add();
					}
				},'-',
		        new Com.yucheng.bob.ExpButton({
		        	id:'exportbatten',
		            formPanel : qForm,
		            url : basepath+'/querycustomerquery.json'
		        })		*/
				,'-',{
					text : '新增潜在客户',
					id:'addPer',
					handler : function() {
					Ext.getCmp('custId').setValue('');
					Ext.getCmp('custZhName').setValue('');
					Ext.getCmp('custTyp').setValue('');
					Ext.getCmp('linkUser').setValue('');
					Ext.getCmp('certType').setValue('');
					Ext.getCmp('custEnName').setValue('');
					Ext.getCmp('linkPhone').setValue('');
					Ext.getCmp('custStat').setValue('');
					Ext.getCmp('certNum').setValue('');
					Ext.getCmp('otherName').setValue('');
					Ext.getCmp('postNo').setValue('');
					Ext.getCmp('commuAddr').setValue('');
					
					Ext.getCmp("custZhName").setReadOnly(false);
					Ext.getCmp("certNum").setReadOnly(false);
					tmepMethod = 'add';
					addPotentialCustomerWindow.setTitle('新增潜在客户');
					addPotentialCustomerWindow.show();
					}
				}
				,'-',{
					text : '修改潜在客户',
					handler : function() {
					tmepMethod = 'update';
						var selectLength = grid
								.getSelectionModel()
								.getSelections().length;

						var selectRe = grid
								.getSelectionModel()
								.getSelections()[0];

						if (selectLength != 1) {
							alert('请选择一条记录');
						} else {
							var tt = grid.getSelectionModel().getSelections()[0].data.CUST_STAT_ORA;
							if(tt!="潜在"){
								Ext.Msg.alert('系统提示','只能选择客户状态为【潜在】的客户!');
								return false;
							}
							Ext.getCmp("custZhName").setReadOnly(true);
							Ext.getCmp("certNum").setReadOnly(true);
//							var checkCreater = grid.getSelectionModel().selections.items[0].data.createUser;
//							if(__userId!=checkCreater){
//								Ext.Msg.alert("系统提示","您不能修改别人创建的团队！");
//								return false;
//							}
							addPotentialCustomerPanel.getForm().loadRecord(selectRe);
							addPotentialCustomerWindow.setTitle('修改潜在客户');
							addPotentialCustomerWindow.show();
							
						}
					}
				}				
				
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height :document.body.scrollHeight-123,
				width : document.body.scrollWidth,
				id:'viewgrid',
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : [tbar,{
					text : '设为关注客户',
					handler : function() {

					if (grid.selModel.hasSelection()) {
						var records = grid.selModel
								.getSelections();// 得到被选择的行的数组
						var recordsLen = records.length;// 得到行数组的长度
						var idStr = '';
						for ( var i = 0; i < recordsLen; i++) {
							selectRe = records[i];
							tempId = selectRe.data.custId;
									//get(this.primary);
							idStr += tempId;
							if (i != recordsLen - 1)
								idStr += ',';
						};
						Ext.Ajax.request({

							url : basepath + '/custConcernOper!create.json',
							method : 'GET',
							 params:{
								'condition':idStr
							
							},
							//form : simple2.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								//store.reload();
							},
							failure:function(a,b){
								var t = Ext.decode(a.responseText);
								Ext.Msg.alert('系统提示','该客户已经是您的关注客户!');
							}
						});		
					} else {
						Ext.Msg.alert("提示", "请先选择要增加的记录!");
					}

				}
				}], // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


    //查询条件
	    var simpleCompare = new Ext.FormPanel({
		    labelAlign: 'right',
		    buttonAlign:'center',
	        frame:true,
	       // bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        	xtype:'fieldset',
	            title: '查询条件',
	           autoHeight:true,
	            items :[{
	            	layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         labelWidth : 80, // 标签宽度
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '开始时间点',
	                             labelStyle: 'text-align:right;',
	                             name: 'first',
	                             anchor:'100%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         labelWidth :80, // 标签宽度
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '结束时间点',
	                             labelStyle: 'text-align:right;',
	                             name: 'last',
	                             anchor:'100%'
	                         }]
	                     },{columnWidth:.25,
							layout:'form',
							labelWidth:80,
							items:[{
								id:'productStatus',
								name:'productStatus',
								xtype:'combo',
								anchor:'100%',
								fieldLabel:'对比项目',
								triggerAction:'all',
							mode:'local',
							store: new Ext.data.ArrayStore({
					        id: 0,
					        fields: [
					            'myId',
					            'displayText'
					        ],
					        data: [[1, '存款时点'],[2,'存款日均'],[3,'贷款时点'],[4,'贷款日均'],[5,'理财产品'],[6,'贡献度']]
					 	   }),
					       valueField:'myId',
					       displayField:'displayText'
							}]
					  }]
	           }],

				buttons: [{
					text: '预览'
				},{
					text: '保存'	            
				}]
	    });
	// 布局模型
	    
	var viewport = new Ext.Viewport({
				//layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户管理->对公客户查询", 
				    height: 120,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    height: document.body.scrollHeight-120,
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 

			});
	var add= function(){
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	var json={'cust_id':[]};
	 	var json1={'cust_lev':[]};
	 	var json2={'cust_zh_name':[]};
	 	var json3={'cust_zzdm':[]};
		
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.cust_id.push(checkedNodes[i].data.CUST_ID);
				json1.cust_lev.push(checkedNodes[i].data.CUST_LEV);
				json2.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
				json3.cust_zzdm.push(checkedNodes[i].data.CUST_ZZDM);
			}
			Ext.Ajax.request({
						url:basepath+'/customer-attention.json',
                        method: 'POST',
                        success : function(response) {
            				Ext.Msg.alert('提示', '设置成功');
            			},
            			failure : function(response) {
            				  var resultArray = Ext.util.JSON.decode(response.status);
							   if(resultArray == 403) {
							      Ext.Msg.alert('提示','您没有此权限!');
							   } else {
								  Ext.Msg.alert('提示','设置失败!');
							   }
            			},
						params : {
							'cust_id':Ext.encode(json),
							'cust_lev': Ext.encode(json1),
							'cust_zh_name': Ext.encode(json2),
							'cust_zzdm': Ext.encode(json3),
							'operate':'add'
						}});
	
	};	
	var batchAdd= function(){
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	var checkedNodes2 = cusGroupGrid.getSelectionModel().selections.items;
		var json={'id':[]};
		var json2={'cust_zh_name':[]};
	 	var json3={'cust_zzdm':[]};
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.id.push(checkedNodes[i].data.CUST_ID);
				json2.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
				json3.cust_zzdm.push(checkedNodes[i].data.CUST_ZZDM);
			}
			Ext.Ajax.request({
						url:basepath+'/customer-relate-customer-base.json',
                        method: 'POST',
						success : function(response) {
							Ext.Msg.alert('提示', '加入成功');
						},
						failure : function(response) {
							  var resultArray = Ext.util.JSON.decode(response.status);
							   if(resultArray == 403) {
							      Ext.Msg.alert('提示','您没有此权限!');
							   } else {
								  Ext.Msg.alert('提示','加入失败!');
							   }
						},
						params : {
							'cid':Ext.encode(json),
							'cust_zh_name': Ext.encode(json2),
							'cust_zzdm': Ext.encode(json3),
							'cbid': checkedNodes2[0].data.id,
							'operate': 'add'
						}});
	
	};
	var fnCondisDecide= function(){
      	var sName1=window.location.href.split("?condis=")[1];
      	var sID1=window.location.href.split("?qStyle=")[1];
      	if(typeof sName1 != "undefined"){
      		
      		Ext.getCmp('CUST_ZH_NAME').setValue(sName1);
      	  store.on('beforeload', function() {
	        	var conditionStr =  qForm.getForm().getValues(false);
	            this.baseParams = {
	                    "condition":Ext.encode(conditionStr)
	                    
	            };
		});
			store.reload({
		                   
				  params : {
                     start : 0,
                     limit : bbar.pageSize}});
      	};
		if(typeof sID1 != "undefined"){
		      		
		      		Ext.getCmp('CERT_NUM').setValue(sID1);
		      	  store.on('beforeload', function() {
			        	var conditionStr =  qForm.getForm().getValues(false);
			            this.baseParams = {
			                    "condition":Ext.encode(conditionStr)
			                    
			            };
				});
					store.reload({
				                   
						  params : {
		                     start : 0,
		                     limit : bbar.pageSize}});
		      	}
	};
	fnCondisDecide();
}); 