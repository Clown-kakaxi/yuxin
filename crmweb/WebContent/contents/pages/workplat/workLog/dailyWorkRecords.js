Ext.onReady(function() {

	var dailyStore = new Ext.data.ArrayStore({
	    fields:['myId','displayText'],
	    data:[['1','现场拜访'],['2','电话拜访'],['3','接待来访'],
	          ['4','客户联谊'],['5','座谈沟通']]
	});	
	
	var dailyWorkSearchPanel = new Ext.form.FormPanel( {// 查询panel

				title : '日工作记录查询',
				height : 135,
				width:500,				labelWidth : 80,// label的宽度
				labelAlign : 'right',
				frame : true,
				autoScroll : true,
				region : 'north',
				split : true,
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .50,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							name : 'OWENERID',
							fieldLabel : '客户经理编号',
							anchor : '100%'
						}, {
							name : 'ADD_START_DATE_FROM',
							id : 'ADD_START_DATE_FROM',
							anchor : '100%',
							xtype : 'datefield',
							editable : false,
							format : 'Y-m-d',
							fieldLabel : '工作日期从'
						} ]
					}, {
						columnWidth : .50,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							name : 'OWENERNAME',
							fieldLabel : '客户经理姓名',
							anchor : '100%'
						},{
							name : 'ADD_START_DATE_TO',
							id : 'ADD_START_DATE_TO',
							anchor : '100%',
							xtype : 'datefield',
							editable : false,
							format : 'Y-m-d',
							fieldLabel : '工作日期到'
						}]
					}
					]
				} ],
				buttonAlign : 'center',
				buttons : [
						{
							text : '查询',
							handler : function() {
								var start = Ext.getCmp(
										'ADD_START_DATE_FROM').getValue();
								var end = Ext.getCmp('ADD_START_DATE_TO')
										.getValue();
								if (start == '' && end != '') {
									Ext.Msg.alert('消息框', '请先选择开始时间！');
									Ext.getCmp('ADD_START_DATE_TO')
											.reset();
									return;
								} else if (end != '' && start > end) {
									Ext.Msg.alert('消息框', '开始时间大于结束时间，请检查！');
									Ext.getCmp('ADD_START_DATE_TO')
											.reset();
									return;
								}

								var parameters = dailyWorkSearchPanel
										.getForm().getValues(false);

								dailyWorkInfoStore.removeAll();
								dailyWorkInfoStore.baseParams = {
									'condition' : Ext.util.JSON
											.encode(parameters)
								};
								dailyWorkInfoStore.load( {
									params : {
										start : 0,
										limit : parseInt(spagesize_combo
												.getValue())
									}
								});
							}
						},
						{
							text : '重置',
							handler : function() {
							dailyWorkSearchPanel.getForm().reset();
								
							}
						} ]
			});
	var rownums = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var sm = new Ext.grid.CheckboxSelectionModel();

	var grid = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                    sm, rownums,
	                                               	{header :'日工作记录',dataIndex:'logid',id:"logid",width:100,sortable : true,hidden:true},
	                                               	{header :'客户经理编号',dataIndex:'owenerid',width:125,sortable : true},
	                                               	{header : '客户经理姓名',dataIndex:'owenername',width:125,sortable : true},
	                                               	{header :'新客户名称',dataIndex:'newcustname',width:120,hidden:true,sortable : true},
	                                               	{header :'新客户拜访方式',dataIndex:'newctvitme',width:120,sortable : true,hidden:true},
	                                               	{header :'新客户联系电话 ',dataIndex:'newcusttel',width:120,sortable : true,hidden:true},
	                                               	{header :'新客户拜访人',dataIndex:'newcustvtman',width:120,sortable : true,hidden:true},
	                                              	{header :'新客户拜访联系和拜访情况',dataIndex:'newcustthing',width:180,sortable:true,hidden:true},
	                                               	{header :'老客户名称',dataIndex:'oldcustname',width:120,sortable:true,hidden:true},
	                                               	{header :'老客户联系电话',dataIndex:'oldcusttel',width:120,sortable:true,hidden:true},
	                                               	{header :'拜访方式',dataIndex:'oldctvitme',width:120,sortable:true,hidden:true},
	                                               	{header :'老客户拜访人',dataIndex:'oldcustvtman',width:120,sortable:true,hidden:true},
	                                               	{header :'老客户拜访联系和 拜访情况',dataIndex:'oldcustthing',width:150,sortable:true,hidden:true},
	                                               	{header :'月度，周计划进度和完成情况',dataIndex:'monweekplan',width:180,sortable:true,hidden:true},
	                                               	{header :'贷后检查企业名称',dataIndex:'loancomname',width:150,sortable:true,hidden:true},
	                                               	{header :'贷后检查贷款余额',dataIndex:'loancombal',width:150,sortable:true,hidden:true},
	                                               	{header :'贷后检查落实情况',dataIndex:'loanproce',width:150,sortable:true,hidden:true},
	                                               	{header :'关注和需要解决的问题以及分行的建议',dataIndex:'needtodo',width:220,sortable:true,hidden:true},
	                                               	{header :'工作日期',dataIndex:'etldate',width:100,sortable:true},
	                                               	{header :'补录日期',dataIndex:'adddate',width:105,sortable:true}
	                                               	]);
	                                               	var dailyWorkInfoRecord = new Ext.data.Record.create([	
	                                               	{name:'logid',mapping:'LOGID'},
	                                               	{name:'owenerid',mapping:'OWENERID'},
	                                               	{name:'owenername',mapping:'OWENERNAME'},
	                                               	{name:'newcustname',mapping:'NEWCUSTNAME'},
	                                               	{name:'newctvitme',mapping:'NEWCTVITME'},
	                                               	{name:'newcusttel',mapping:'NEWCUSTTEL'},
	                                            	{name:'newcustvtman',mapping:'NEWCUSTVTMAN'},
	                                            	{name:'newcustthing',mapping:'NEWCUSTTHING'},
	                                            	{name:'oldcustname',mapping:'OLDCUSTNAME'},
	                                            	{name:'oldctvitme',mapping:'OLDCTVITME'},
	                                            	{name:'oldcusttel',mapping:'OLDCUSTTEL'},
	                                            	{name:'oldcustvtman',mapping:'OLDCUSTVTMAN'},
	                                            	{name:'oldcustthing',mapping:'OLDCUSTTHING'},
	                                            	{name:'monweekplan',mapping:'MONWEEKPLAN'},
	                                            	{name:'loancomname',mapping:'LOANCOMNAME'},
	                                            	{name:'loancombal',mapping:'LOANCOMBAL'},
	                                            	{name:'loanproce',mapping:'LOANPROCE'},
	                                            	{name:'needtodo',mapping:'NEEDTODO'},
	                                            	{name:'etldate',mapping:'ETLDATE'},
	                                            	{name:'adddate',mapping:'ADDDATE'}
	                                               	]);
	                                               	
	                                               	var dailyWorkInfoReader = new Ext.data.JsonReader({//读取json数据的panel
	                                               	totalProperty:'json.count',
	                                               	root:'json.data'
	                                               	},dailyWorkInfoRecord);
	                                               	
	                                               	var dailyWorkInfoStore = new Ext.data.Store(
	                                               	{
	                                               		proxy:new Ext.data.HttpProxy({
	                                               		url:basepath+'/dailyWork-Action.json',
//	                                               		},
	                                               		failure : function(response){
	                                               			var resultArray = Ext.util.JSON.decode(response.status);
	                                               			if(resultArray == 403) {
	                                               				Ext.Msg.alert('提示', response.responseText);
	                                               			}
	                                               		},
	                                               		method:'GET'
	                                               		}),
	                                               		reader:dailyWorkInfoReader
	                                               	}
	                                               	);
	                                        		// 每页显示条数下拉选择框
	                                        		var spagesize_combo = new Ext.form.ComboBox({
	                                        			name : 'pagesize',
	                                        			triggerAction : 'all',
	                                        			mode : 'local',
	                                        			store : new Ext.data.ArrayStore({
	                                        				fields : [ 'value', 'text' ],
	                                        				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
	                                        							[ 100, '100条/页' ], [ 250, '250条/页' ],
	                                        							[ 500, '500条/页' ] ]
	                                        			}),
	                                        			valueField : 'value',
	                                        			displayField : 'text',
	                                        			value : '20',
	                                        			forceSelection : true,
	                                        			width : 85
	                                        		});

	                                        		// 改变每页显示条数reload数据
	                                        		spagesize_combo.on("select", function(comboBox) {
	                                        			sbbar.pageSize = parseInt(spagesize_combo.getValue()),
	                                        			dailyWorkInfoStore.reload({
	                                        				params : {
	                                        					start : 0,
	                                        					limit : parseInt(spagesize_combo.getValue())
	                                        				}
	                                        			});
	                                        		});
	                                        		// 分页工具栏
	                                        		var sbbar = new Ext.PagingToolbar({
	                                        			pageSize : parseInt(spagesize_combo.getValue()),
	                                        			store : dailyWorkInfoStore,
	                                        			displayInfo : true,
	                                        			displayMsg : '显示{0}条到{1}条,共{2}条',
	                                        			emptyMsg : "没有符合条件的记录",
	                                        			items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	                                        		});
	                                        		dailyWorkInfoStore.load({params:{		
	                                        			start:0,
	                                        			limit: parseInt(spagesize_combo.getValue())
	                                        		}});
	var dailyForm = new Ext.FormPanel( {
		frame : true,
		autoScroll : true,
		region:'center',
		items : [ {
			xtype : 'fieldset',
			title : '日工作记录',
			titleCollapse : true,
//			collapsed : true,
			collapsible : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .32,
					labelWidth : 80,
					items : [{xtype:'textfield',fieldLabel:'日工作记录',name:'logid',labelStyle:'text-align:right;',hidden:true,anchor:'90%'},
					         {xtype:'datefield',fieldLabel:'工作日期	',id:'etldates',editable : false,labelStyle:'text-align:right;',value:new Date(),format : 'Y-m-d',anchor:'90%',listeners:{'change':function(a,b,c){
								Ext.getCmp('etldate').setValue(a.getValue());
					         }}},
					         {xtype:'datefield',fieldLabel:'工作日期	',editable : false,name:'etldate',id:'etldate',hidden:true,value:new Date(),labelStyle:'text-align:right;',format : 'Y-m-d',anchor:'90%'},
					         {xtype:'datefield',fieldLabel:'补录日期	',editable : false,name:'adddate',id:'adddate',labelStyle:'text-align:right;',value:new Date(),hidden:true,format : 'Y-m-d',anchor:'90%'}
					         ]
				}, {
					layout : 'form',
					columnWidth : .32,
					labelWidth : 100,
					items : [ 
					         {xtype:'textfield',fieldLabel:'客户经理编号',id:'owenerids',value:__userId,labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
					         {xtype:'textfield',fieldLabel:'客户经理编号',name:'owenerid',id:'owenerid',value:__userId,labelStyle:'text-align:right;',readOnly:true,anchor:'95%',hidden:true}

					          ]
				}, {
					layout : 'form',
					columnWidth : .32,
					labelWidth : 100,
					items : [
					         {xtype:'textfield',fieldLabel:'客户经理姓名',id:'owenernames',value:__userName,labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
					         {xtype:'textfield',fieldLabel:'客户经理姓名',name:'owenername',id:'owenername',value:__userName,labelStyle:'text-align:right;',readOnly:true,anchor:'95%',hidden:true}


					         ]
				} ]
			} ]
		},{
			xtype : 'fieldset',
			title : '一、新客户或目标客户联系和拜访 ',
			titleCollapse : true,
//			collapsed : true,
			collapsible : true,
			autoHeight : true,
			buttonAlign :'center',
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .48,
					labelWidth : 150,
					items : [
			         {xtype:'textfield',fieldLabel:'新客户名称',name:'newcustname',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					        
					 {xtype:'numberfield',fieldLabel:'联系电话',name:'newcusttel',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					         ]
				}, {
					layout : 'form',
					columnWidth : .48,
					labelWidth : 150,
					items : [  
			
					          {
									store : dailyStore,xtype : 'combo', resizable : true,name : 'newctvitme',hiddenName : 'newctvitme',
									fieldLabel : '拜访方式',valueField : 'myId',displayField : 'displayText',mode : 'local',editable : false,
									typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',
									selectOnFocus : true,anchor : '95%'
								},
					          {xtype:'textfield',fieldLabel:'拜访人',name:'newcustvtman',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					          
					          ]
				},{
					layout : 'form',
					columnWidth : .99,
					labelWidth : 150,
					items : [  
					
					          {xtype:'textarea',fieldLabel:'新客户联系和拜访情况',name:'newcustthing',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        
					          ]
				} ]
			} ]
		},{
			xtype : 'fieldset',
			title : '二、老客户日常联系 ',
			titleCollapse : true,
//			collapsed : true,
			collapsible : true,
			autoHeight : true,
			buttonAlign :'center',
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .48,
					labelWidth : 150,
					items : [
			         {xtype:'textfield',fieldLabel:'老客户名称',name:'oldcustname',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'},
					        
					 {xtype:'numberfield',fieldLabel:'联系电话',name:'oldcusttel',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					         ]
				}, {
					layout : 'form',
					columnWidth : .48,
					labelWidth : 150,
					items : [  
					
					    
					          {
									store : dailyStore,xtype : 'combo', resizable : true,name : 'oldctvitme',hiddenName : 'oldctvitme',
									fieldLabel : '拜访方式',valueField : 'myId',displayField : 'displayText',mode : 'local',editable : false,
									typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',
									selectOnFocus : true,anchor : '95%'
								},
					          {xtype:'textfield',fieldLabel:'拜访人',name:'oldcustvtman',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					          
					          ]
				},{
					layout : 'form',
					columnWidth : .99,
					labelWidth : 150,
					items : [  
					
					          {xtype:'textarea',fieldLabel:'老客户联系和拜访情况',name:'oldcustthing',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        
					          ]
				} ]
			} ]
		},{
			xtype : 'fieldset',
			title : '三、月度、周计划进度和完成情况（截至当日情况)',
			titleCollapse : true,
//			collapsed : true,
			collapsible : true,
			autoHeight : true,
			buttonAlign :'center',
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',
					columnWidth : .99,
					labelWidth : 150,
					items : [  
					
					          {xtype:'textarea',name:'monweekplan',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        
					          ]
				} ]
			} ]
		},{  	
			xtype : 'fieldset',
			title : '四、贷后检查工作 ',
			titleCollapse : true,
//			collapsed : true,
			collapsible : true,
			autoHeight : true,
			buttonAlign :'center',
			items : [{
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .48,
					labelWidth : 150,
					items : [
			         {xtype:'textfield',fieldLabel:'企业名称',name:'loancomname',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					         ]
				}, {
					layout : 'form',
					columnWidth : .48,
					labelWidth : 150,
					items : [  
					
					          {xtype:'numberfield',fieldLabel:'贷款余额',name:'loancombal',value:0,labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					          
					          ]
				},{
					layout : 'form',
					columnWidth : .99,
					labelWidth : 150,
					items : [  
					
					          {xtype:'textarea',fieldLabel:'检查落实情况',name:'loanproce',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        
					          ]
				} ]
			} ]
		},{
			xtype : 'fieldset',
			title : '五、关注和需要解决的问题以及对分行的建议 ',
			titleCollapse : true,
//			collapsed : true,
			collapsible : true,
			autoHeight : true,
			buttonAlign :'center',
			baseCls :'x-fieldset',
			items : [ {
				layout : 'column',
				items : [{
					layout : 'form',
					columnWidth : .99,
					labelWidth : 150,
					items : [  
					
					          {xtype:'textarea',name:'needtodo',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					        
					          ]
				} ]
			} ]
		}
		
		],
		buttonAlign:'center',

		buttons:[{text :'保存',
			disabled:true,
			id :'Add',
		 	handler: function(){
			if (!dailyForm.getForm().isValid()) {
				Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
				return false;
			}
			var	test = Ext.getCmp('etldates').getValue();
  var test1 = test.format('Y-m-d');
            var test2 = new Date();
            var test3 = test2.format('Y-m-d');
              
            if(test1 > test3)
            {
				Ext.Msg.alert("系统提示信息", "工作日期不能大于 当前日期，请重新输入!");
				return false;

            }
			Ext.Ajax.request( {
				url : basepath + '/daily-Action.json',
				method : 'POST',
				form : dailyForm.getForm().id,
				success : checkResult,
				failure: checkResult,
				params : {
					'operate':'add'
				}
			});
			function checkResult(response) {
				var resultArray = Ext.util.JSON
						.decode(response.status);
				var resultError = response.responseText;
				  
				if ((resultArray == 200 || resultArray == 201)
						&& resultError == '') {
					Ext.Msg.alert('系统提示信息', '操作成功');
				      Ext.getCmp('Add').setDisabled(true);
			            Ext.getCmp('rewrite').setDisabled(true);
					dailyWorkInfoStore
							.load( {
								params : {
									start : 0,
									limit : parseInt(spagesize_combo
											.getValue())
								}
							});
				} else   {
					var st = response.responseText.split('#sxs#')[1];
					if (st != null) {
						Ext.Msg.alert('提示', '当日的工作记录已添加，请取消！');
					} else {
						Ext.Msg.alert('提示', response.responseText);
					}
				}
			}
		}
		
		},'-',{
			id :'rewrite',
			text :'重置',
			disabled:true,
		 	handler: function(){
			dailyForm.getForm().reset();
		}}]
	});
		var dailyWorkGrid =  new Ext.grid.GridPanel({//产品列表数据grid
			frame:true,
			region:"west",
			id:'dailyWorkGrid',
			width:500,
			height : document.body.clientHeight-135,

			store:dailyWorkInfoStore,
			loadMask:true,
	      	tbar:[ 
	      	{
	      		text:'添加工作记录',
	      		handler:function()
	      		{      			
	      		dailyForm.getForm().reset();	
	      		dailyForm.setDisabled(false);
	            Ext.getCmp('Add').setDisabled(false);
	            Ext.getCmp('rewrite').setDisabled(false);
	      		}
	      	},'-',	{
		      		text:'修改工作记录',
		      		handler:function()
		      		{      			

	      		record = dailyWorkGrid.getSelectionModel().getSelected();
	    		var selectLength = dailyWorkGrid.getSelectionModel().getSelections().length;
	      		
	      		if(selectLength != 1){
      				Ext.MessageBox.alert('提示','请选择一条记录');
      				return;
      			}   
	      		else {
			      		var a = record.data.owenerid;
			      		var b = __userId;
			  if(a == b)
			  {     var t = record.data.etldate;
			        var p = record.data.owenerid;
			        var q = record.data.owenername;
		      		dailyForm.setDisabled(false);
		            Ext.getCmp('Add').setDisabled(false);
			        Ext.getCmp('etldates').setDisabled(true);
		            Ext.getCmp('etldate').setValue(t);
		            Ext.getCmp('owenerids').setDisabled(true);
		            Ext.getCmp('owenerid').setValue(p);
		            Ext.getCmp('owenernames').setDisabled(true);
		            Ext.getCmp('owenername').setValue(q);
		  		    var m  =  Ext.getCmp('etldates').getValue();
				 
				     Ext.getCmp('etldate').setValue(m);
						var t1 = new Date();
				        var t2 = t1.format('Y-m-d');
				     Ext.getCmp('adddate').setValue(t2);

	      		}else{
      				Ext.MessageBox.alert('提示','您没有权限进行修改！');
	      		}
		      		}
		      		}}
	      	],
			cm :grid,
			sm :sm,
	    	bbar:sbbar,
	        loadMask : {
	            msg : '正在加载表格数据,请稍等...'
	        }, 
	    	listeners:{
				'rowclick':function(a,b,c){	
				record = dailyWorkGrid.store.getAt(b);
				Ext.getCmp('Add').setDisabled(true);
		        Ext.getCmp('rewrite').setDisabled(true);
			    dailyForm.getForm().loadRecord(record);
		        Ext.getCmp('etldates').setValue(record.data.etldate);
				}
			}

		});	
	// 布局模型
	var view = new Ext.Viewport({//页面展示
		layout:'border',
		frame:true,
	    items:[
	      {
				region : 'west',
				layout : 'form',
				width :'500',
				items : [dailyWorkSearchPanel, dailyWorkGrid ]
				
			},
	           dailyForm]}
	           
	           
	    );	
	
});
	
	
	
	
	
	
	
	
	
	
