/***
 * 客户满意度调查视图展示
 * luyy
 * date:2014-06-20
 */
	var cust_id = oCustInfo.cust_id;
	var riskCharactStore =  new Ext.data.SimpleStore({
		fields : ['key', 'value'],
		data : [['1', '不满意'], ['2', '基本满意'], ['3', '很满意'],['4', '非常满意']]
	});
	
	var title_count = 0;
		
	var title_record = Ext.data.Record.create([{
		name : 'titleId',
		mapping : 'TITLE_ID'
	}, {
		name : 'titleName',
		mapping : 'TITLE_NAME'
	}, {
		name : 'titleRemark',
		mapping : 'TITLE_REMARK'
	}, {
		name : 'titleIdL',
		mapping : 'titleId'
	}]);


	var title_store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/TitleQuery!loadTitleById.json'
			}),
		reader : new Ext.data.JsonReader({
					successProperty : 'success',
					messageProperty : 'message',
					root : 'data',
					totalProperty : 'count'
				}, title_record)
	});
	var record = Ext.data.Record.create([{name:'ID'},
	                                      {name:'RESULT_ID'}]);
	
	var qaStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/TitleQuery!loadResultById.json'
			}),
		reader : new Ext.data.JsonReader({
					successProperty : 'success',
					messageProperty : 'message',
					root : 'data',
					totalProperty : 'count'
				}, record)
	});
	var cust_form = new Ext.form.FormPanel({
		id : 'cust_form',
		height : 80,
		labelAlign : 'right',
		frame : true,
		items : [{
					layout : 'column',
					labelWidth : 100,
					items : [{
								layout : 'form',
								columnWidth : .33,
								items : [{name:'CUST_NAME',
										xtype:'textfield',
										fieldLabel:'客户名称',
										anchor : '80%'}]
							}, {
								layout : 'form',
								columnWidth : .33,
								items : [{
											name : 'INDAGETE_QA_SCORING',
											xtype : 'textfield',
											fieldLabel:'得分',
											readOnly  : true,
											anchor : '80%'
										}]
							}, {
								layout : 'form',
								columnWidth : .33,
								items : [{
									store : riskCharactStore,
									labelStyle : 'text-align:right;',
									xtype : 'combo',
									resizable : true,
									fieldLabel : '客户满意度',
									name : 'type',
									hiddenName : 'SATISFY_TYPE',
									valueField : 'key',
									displayField : 'value',
									mode : 'local',
									typeAhead : true,
									readOnly:true,
									forceSelection : true,
									triggerAction : 'all',
									emptyText : '请选择',
									selectOnFocus : true,
									width : '100',
									anchor : '90%',
									labelSeparator:''
								}]
							}]
				}]
	});


	var rd_set = new Ext.form.FieldSet({
		xtype : 'fieldset',
		title : '问卷调查',
		labelWidth : 200,
		labelAlign : 'right',
		collapsible : true,
		items : []
	});

	var opForm = new Ext.Panel({
	id : 'opForm',
	layout : 'form',
	autoScroll : true,
	labelAlign : 'right',
	frame : true,
	buttonAlign : "center",
	items : [cust_form, rd_set],
	buttons : [{
	text : '关闭',
	handler : function() {
		opWin.hide();
	}
	}]

	});

	var opWin = new Ext.Window({
		plain : true,
		layout : 'fit',
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
//		maximized : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 900,
		height : 450,
		title : '客户满意度调查',
		items : [opForm]
	});

	
	var store = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFSeCustSatisfyList.json',
		    	method:'get'//,
//		    	 success:function(response){
//				 Ext.Msg.alert("数据",response.responseText);
//				 }
		  }),
		  reader: new Ext.data.JsonReader({
			  successProperty : 'success',
			 idProperty : 'ID',
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'ID'},
		      {name:'PAPERS_ID'},
		      {name:'PAPER_NAME'},
		      {name:'CUST_ID'},
		      {name:'CUST_NAME'},
		      {name:'type',mapping:'SATISFY_TYPE'},
		      {name:'INDAGETE_QA_SCORING'},
		      {name:'EVALUATE_NAME'},
		      {name:'EVALUATE_DATE'}
		      ])
	  });
	//复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	
	 var cm = new Ext.grid.ColumnModel([rownum,sm,	// 定义列模型
	                                     {header : '编号', dataIndex : 'ID',sortable : true,width : 120,hidden : true}, 
	                                     {header:'问卷编号',dataIndex:'PAPERS_ID',hidden:true},
	                                     {header:'问卷名称',dataIndex:'PAPER_NAME',sortable : true,width : 170},
	                                     {header:'满意度',dateIndex:'type',sortable : true,width : 120,renderer:function(v){
		                                    	if(v == '1')
		                                    		return '不满意';
		                                    	else if(v == '2')
		                                    		return '基本满意';
		                                    	else if(v == '3')
		                                    		return '很满意';
		                                    	else if(v == '4')
		                                    		return '非常满意';
		                                    	else return v;
		                                    	
		                                     }},
	                                     {header:'问卷得分',dataIndex:'INDAGETE_QA_SCORING',sortable : true,width : 100,align:'right'},
	                                     {header:'调查人',dateIndex:'EVALUATE_NAME',sortable : true,width : 120},
	                                     {header:'调查日期',dateIndex:'EVALUATE_DATE',sortable : true,width : 120}
	                                     ]);
	 
	 var grid = new Ext.grid.GridPanel({
			      layout:'fit',
			      height:document.body.scrollHeight,
				  frame : true,
				  autoScroll : true,
				  region : 'center', // 返回给页面的div
				  store: store,
				  stripeRows : true, // 斑马线
				  sm:sm,
				  cm : cm,
				  tbar:[{text:'查看问卷',
					  handler:function(){
					  var selectLength = grid.getSelectionModel().getSelections().length;
						var selectRe = grid.getSelectionModel().getSelections()[0];
						if (selectLength < 1) {
							Ext.Msg.alert('提示', '请先选择一条记录！');
							return false;
						} else if (selectLength > 1) {
							Ext.Msg.alert('提示', '只能选择一条记录！');
							return false;
						}
						//先清空
						title_count = title_store.getCount();
						for ( var i = 0; i < title_count; i++) 
							rd_set.remove(Ext.getCmp('rg' + i));
						//重新加载title_store
						title_store.load({
							 params : {
				 				'paperId':selectRe.data.PAPERS_ID
				 			 },
				 			 callback:function(){
				 				var title_count = null;
				 				var title = null;
				 				var title_rs = null;
				 				var rs = null;
				 				title_count = title_store.getCount();
				 				//画出问卷
				 				for (var i = 0; i < title_count; i++) {
				 					title = title_store.getAt(i);
				 					title_rs = new Array();
				 					for (var b = 0; b < title.json.titleIdL.length; b++) {
				 						rs = title.json.titleIdL[b];
				 						title_rs.push(new Ext.form.Radio({
				 									boxLabel : rs.result,
				 									name : 'result' + i,
				 									inputValue : rs.resultId
				 								}));
				 					}
				 					new Ext.form.RadioGroup({
				 								id : 'rg' + i,
				 								fieldLabel : title.json.titleName,
				 								name : title.json.titleId,
				 								items : [title_rs]
				 							});
				 					rd_set.add(Ext.getCmp('rg' + i));
				 					rd_set.doLayout();
				 				}
				 				//填写问卷答案
				 				qaStore.load({
				 					params:{
				 						SATISFY_ID:selectRe.data.ID,
				 						paperId:selectRe.data.PAPERS_ID
				 					},
				 					callback:function(){
				 						var count = qaStore.getCount();
				 						for(var i=0;i<count;i++){
				 							var qa = qaStore.getAt(i);
				 							Ext.getCmp('rg' + i).setValue(qa.get('RESULT_ID'));
				 						}
				 					}
				 				});
				 			 }
						});
						opWin.show();
						cust_form.getForm().loadRecord(selectRe);
						
				  }
				}]
	 });
	 
	 store.load({
		 params:{
			 cust_id:cust_id
		 }
	 });
		
		var tree_panel = new Ext.Panel({
			renderTo : oCustInfo.view_source,
			height:document.body.scrollHeight,
			width : document.body.clientWidth-180,
			items : [grid ]
	});
		
