Ext.onReady(function() {
	 Ext.QuickTips.init(); 
	
	 var title_result = null;
		var sum_flag = false;
		// 计算评级得分
			function sum() {
				sum_flag = false;
				var rdName = null;
				var title = null;
				var rs = null;
				var adjustNumber = 0;
				title_result = '';
				var rsCount = 0;
				title_count = title_store.getCount();
				for ( var j = 0; j < title_count; j++) {
					title = title_store.getAt(j);
					if (Ext.getCmp('rg' + j).getValue() != null) {
						rdName = Ext.getCmp('rg' + j).getValue().inputValue;
						for ( var k = 0; k < title.json.titleIdL.length; k++) {
							rs = title.json.titleIdL[k];
							if (rs.resultId == rdName) {
								rsCount = parseFloat(rsCount)
										+ parseFloat(rs.resultScoring);
							}
						}
					}
				}
				for ( var i = 0; i < title_count; i++) {
					if (Ext.getCmp('rg' + i).getValue() != null) {
						var titleId = Ext.getCmp('rg' + i).name;
						rsId = Ext.getCmp('rg' + i).getValue().inputValue;
						var rsScore;
						if (rsId != '') {
							for ( var j = 0; j < title_count; j++) {
								title = title_store.getAt(j);
								for ( var k = 0; k < title.json.titleIdL.length; k++) {
									rs = title.json.titleIdL[k];
									if (rs.resultId == rsId) {
										rsScore = parseFloat(rs.resultScoring);
									}
								}
							}
							title_result += titleId + ':' + rsId + ':' + rsScore;
							if (i != title_count - 1) {
								title_result += ',';
							}
						}
					} else {
						Ext.Msg.alert("提示", "您还有:"
								+ Ext.getCmp('rg' + i).fieldLabel + " 问题没回答！");
						Ext.getCmp('rg' + i).focus();
						return;
					}
				}
				sum_flag = true;

				return "success";
			}
			
		
	//遮挡
	var lm = new Ext.LoadMask(document.body, {// 定义遮屏到body节点上
		msg : '正在加载表格数据,请稍等...',
		removeMask : true
	});
	// 题目类型
		var optionTypeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=OPTION_TYPE'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		optionTypeStore.load();
		
		var typeStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=IF_FLAG'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		typeStore.load();
		
		var paperId='';
	
		
	/*************查询面板**************************/	
		var simple = new Ext.FormPanel( {
			frame : true,
			id : 'queryGroup',
			bodyStyle : 'padding:5px 5px 0',
			width : '100%',
			labelAlign : 'center',
			items : [ {
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '问卷名称',
							labelStyle : 'text-align:right;',
							name : 'paperName2',
							anchor : '90%',
							labelSeparator:''
						} ]
					} ]
				} ]
			} ],
			buttonAlign : 'center',
			keys : [ {
				key : 13,
				fn : function() {
					Ext.getCmp('quession_serch').focus(true);
				},
				scope : this
			} ],
			buttons : [ {
				text : '查询',
				id:'quession_serch',
				handler : function() {
//				searchFunction();

				}
			}, {
				text : '重置',
				handler : function() {
					simple.getForm().reset();
				}

			} ]
		});

		/*************问卷试题store*******************/
	    var title_record = Ext.data.Record.create( [{
			name : 'titleId',
			mapping : 'TITLE_ID'
		}, {
			name : 'titleName',
			mapping : 'TITLE_NAME'
		}, {
			name : 'titleIdL',
			mapping : 'titleId'
		}, {
			name : 'qaId',
			mapping : 'QA_ID'
		}, {
			name : 'updator',
			mapping : 'UPDATOR'
		}, {
			name : 'update_date',
			mapping : 'UPDATOR_DATE'
		}]);
	    
		var title_store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/CustQuestionAction!loadTitleRs.json',
				success:function(response){
			}
			}),
			baseParams :{
				paperId:'1'
			},
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',
				messageProperty : 'message',
				root : 'data',
				totalProperty : 'count'
			}, title_record)
		});
		title_store.load({
			params:{
			paperId:'1'
		}
		});
		
		/***********问卷明细store****************/
		var title_store2 = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/CustQuestionAction!loadTitleRs.json',
				success:function(response){
			}
			}),
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',
				messageProperty : 'message',
				root : 'data',
				totalProperty : 'count'
			}, title_record)
		});
//		title_store2.load();
		
		
		/************答题明细查询store*************/
	    var data_record = Ext.data.Record.create( [{
			name : 'count',
			mapping : 'COUNT'
		}, {
			name : 'cust_select_content',
			mapping : 'CUST_SELECT_CONTENT'
		}, {
			name : 'title_id',
			mapping : 'TITLE_ID'
		}, {
			name : 'paper_id',
			mapping : 'PAPER_ID'
		}]);
		var data_store = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/custQuestionStoreAction.json',
				success:function(response){
			}
			}),
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',
				messageProperty : 'message',
				root : 'json.data',
				totalProperty : 'count'
			}, data_record)
		});
		data_store.load();
		
		
		/*****************答题情况列表******************/
//		// 列选择模型
		var sm = new Ext.grid.CheckboxSelectionModel();
//		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
	 var papermanageStore = new Ext.data.Store({
		        reader: new Ext.data.JsonReader({
		            root:'rows',
		            totalProperty: 'num'
		        }, [ {name:'id'},
	             {name:'paperName'},
	             {name:'optionType'},
	             {name:'creator'},
	             {name:'createOrg'},
	             {name:'createDate'},
	             {name:'available'},
	             {name:'remark'}
				])
			});
	var tb_memberData= {
		num:1,
		rows:[
		{"id":"1","paperName":"客户经理问卷1","optionType":"2012-01-01","creator":"2012-12-31","createOrg":"100","createDate":"20","available":"调查中","remark":"20%"},
		{"id":"2","paperName":"客户经理调查","optionType":"2013-01-01","creator":"2013-12-31","createOrg":"150","createDate":"30","available":"调查中","remark":"20%"}
		]
	};
	papermanageStore.loadData(tb_memberData);
		var productInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
		                                                sm,
		                                               rownum,
		                                               {header :'问卷ID',dataIndex:'id',id:"id",width:100,sortable : true,hidden:true},
		                                               {header :'问卷名称',dataIndex:'paperName',id:"paperName",width:100,sortable : true},
		                                               {header:'开始时间',dataIndex:'optionType',id:'optionType',width:100,sortable : true},
		                                               {header:'结束时间',dataIndex:'creator',id:'creator',width:100,sortable : true,hidden:false},	
		                                               {header:'计划调查人数',dataIndex:'createOrg',id:'createOrg',width:130,sortable : true},
		                                               {header:'已答题人数',dataIndex:'createDate',id:'createDate',width:100,sortable : true,hidden:false},	
		                                               {header:'问卷状态',dataIndex:'available',id:'available',width:100,sortable : true},
		                                               {header:'答题率',dataIndex:'remark',id:'remark',width:300,sortable : true}
		                                               ]);



		var pagesize_combo = new Ext.form.ComboBox( {
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore(
					{
						fields : [ 'value', 'text' ],
						data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
								[ 50, '50条/页' ], [ 100, '100条/页' ],
								[ 250, '250条/页' ], [ 500, '500条/页' ] ]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});
		var number = parseInt(pagesize_combo.getValue());
		// 改变每页显示条数reload数据
		
/**
 * 试题选择window
 */
		
		/*********答题情况列表**************/
//		// 列选择模型
		var sm = new Ext.grid.CheckboxSelectionModel();
//		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});
		var qAInfoColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
		                                                sm,
		                                               rownum,
		                                               {header :'ID',dataIndex:'id',id:"id",width:100,sortable : true,hidden:true},
		                                               {header :'答题人姓名',dataIndex:'answerUser',id:"answerUser",width:100,sortable : true},
		                                               {header:'答题时间',dataIndex:'answerDate',id:'answerDate',width:150,sortable : true},
		                                               {header:'问卷名称',dataIndex:'paperName',id:'paperName',width:200,sortable : true,hidden:false},	
		                                               {header:'问卷ID',dataIndex:'paperId',id:'paperId',width:130,sortable : true,hidden:true}
		                                               ]);
		var qAInfoRecord = new Ext.data.Record.create([
		                                                 {name:'id',mapping:'CUST_Q_ID'},
		                                                {name:'answerUser',mapping:'EVALUATE_NAME'},
		                                                {name:'answerDate',mapping:'EVALUATE_DATE'},
		                                                {name:'paperName',mapping:'PAPER_NAME'},
		                                                {name:'paperId',mapping:'PAPERS_ID'}
		                                                ]);
		var qAInfoReader = new Ext.data.JsonReader({//读取json数据的panel
			totalProperty:'json.count',
			root:'json.data'
		},qAInfoRecord);
		
		var qAProxy = new Ext.data.HttpProxy({
			url:basepath+'/PaperAnswer.json'
		});
		var qAInfoStore = new Ext.data.Store({
			id: 'qAInfoStore',
			restful : true,     
	        proxy : qAProxy,
	        reader : qAInfoReader,
	        recordType:qAInfoRecord
		});
		qAInfoStore.load();
		
		
		
		var questionAnswerInfoGrid =  new Ext.grid.GridPanel({//用户列表数据grid
			frame:true,
			width:'100%',
			height:370,
			id:'questionAnswerInfoGrid',
			autoScroll : true,
			tbar:tbar,
//			bbar:bbar,
			stripeRows : true, // 斑马线
			store:qAInfoStore,
			loadMask:true,
			cm :qAInfoColumns,
			sm :sm,
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
		        loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
		});
		
		var qAInfoWin = new Ext.Window(
				{
					id : 'qAInfoWin',
					resizable : false,
					collapsible : false,
					draggable : true,
					closeAction : 'hide',
					modal : true, // 模态窗口
					animCollapse : false,
					border : false,
					loadMask : true,
					closable : true,
					autoScroll : true,
					constrain : true,
					width : 550,
					height : 375,
					title : '答题明细查询',
					items : [ questionAnswerInfoGrid ]
				});
		
		
		/****************答题结果统计窗口***********************/
		var rd_set2 = new Ext.form.FieldSet( {
			xtype : 'fieldset',
			title : '答题结果展示',
			labelWidth : 250,
			labelAlign : 'right',
			collapsible : false,
			itemCls: 'x-check-group-alt',
			items : []
		});
		
		var title_rs2 = null;
		var simple2 = new Ext.Panel( {
			id : 'simple2',
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
//			contentEl:'simple22',
			buttonAlign : "center",
			items : [ rd_set2 ],
			listeners : {
				render : function() {
			
					var title_count = null;
					var title = null;
					
					var rs = null;
					title_count = title_store.getCount();
					for ( var i = 0; i < title_count; i++) {
						title = title_store.getAt(i);
						title_rs2 = new Array();

						new Ext.form.RadioGroup( {
							id : 'rg3' + i,
							height:150,
							fieldLabel : title.json.titleName,
							name : title.json.titleId,
							items : [ title_rs2 ]
						});
						title_rs2.push( new Ext.Panel( {
							id : 'rg2' + i,
							height:150,
							width:80,
							items : [ ]
						}));
						
						rd_set2.add(Ext.getCmp('rg3' + i));
						rd_set2.doLayout();
					}

				}

			},
			buttons : [ {
				text : '显示统计结果',
				id : 'score_count',
				handler : function() {
				debugger;
				data_store.reload();
				title_store.load({
					params:{
					paperId:'1'
				}
				});
				title_count = title_store.getCount();
			
				
				for ( var i = 0; i < title_count; i++) {
					var a=[];
				
					for(var j = 0;j<data_store.getCount();j++){
						var b = {};
						debugger;
						if(title_store.getAt(i).json.titleId==data_store.reader.jsonData.json.data[j].TITLE_ID){
							b.count = data_store.reader.jsonData.json.data[j].COUNT;
							b.result = data_store.reader.jsonData.json.data[j].RESULT;
							a.push(b);
						}
					}
					debugger;
					if(a.length==3){
						var dataXml = "<chart   formatNumberScale='0' ><set label='"+a[0].result+"' value='"+a[0].count+"' /><set label='"+a[1].result+"' value='"+a[1].count+"' /><set label='"+a[2].result+"' value='"+a[2].count+"' /></chart>";
					} else if (a.length==2){
						var dataXml = "<chart   formatNumberScale='0' ><set label='"+a[0].result+"' value='"+a[0].count+"' /><set label='"+a[1].result+"' value='"+a[1].count+"' /></chart>";
					}else if (a.length==1){
						var dataXml = "<chart   formatNumberScale='0' ><set label='"+a[0].result+"' value='"+a[0].count+"' /></chart>";
					}else if(a.length=0) {
						return;
					}
//					var dataXml = "<chart   formatNumberScale='0' ><set label='"+a[0].result+"' value='"+a[0].count+"' /><set label='"+a[1].result+"' value='"+a[1].count+"' /><set label='"+a[2].result+"' value='"+a[2].count+"' /></chart>";
					var myChart = new FusionCharts("../../../../FusionCharts/Pie3D.swf", "myChartId", "250","180");
					myChart.setDataXML(dataXml);
					myChart.render('rg2'+i);
				}
		
				
			}
			}]

		});
		var detailWin = new Ext.Window( {
			width : 580,
			height : 500,
			title : '答题统计结果展示',
			items : [ simple2 ],
			layout : 'fit',
			resizable : false,
			collapsible : false,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			animCollapse : false,
			border : false,
			loadMask : true,
			closable : true,
			constrain : true
		});
		detailWin.on('hide',function(){
			for ( var i = 0; i < title_store.getCount(); i++) {
				Ext.getCmp('rg3' + i).reset;
				rd_set.doLayout();
			}
		});
//		detailWin.on('hide', function() {
////			debugger;
////			rd_set2;
//			title_count = title_store.getCount();
//			for ( var j = 0; j < title_count; j++) {
//				debugger;
//				Ext.getCmp('rg' + j).setReadOnly(false);
//				Ext.getCmp('rg' + j).reset();
////				title_rs2[0].removeAll();
//			}
////			sum_flag = false;
//		});
		
		
		var rd_set = new Ext.form.FieldSet( {
			xtype : 'fieldset',
			title : '问卷调查',
			labelWidth : 250,
			labelAlign : 'right',
			collapsible : false,
			itemCls: 'x-check-group-alt',
			items : []
		});
		var opForm = new Ext.Panel( {
			id : 'opForm',
			layout : 'form',
			autoScroll : true,
			labelAlign : 'right',
			frame : true,
			buttonAlign : "center",
			items : [ rd_set ],
			listeners : {
				render : function() {
			debugger;
					var title_count = null;
					var title = null;
					var title_rs = null;
					var rs = null;
					title_count = title_store.getCount();
					for ( var i = 0; i < title_count; i++) {
						debugger;
						title = title_store.getAt(i);
						title_rs = new Array();
						for ( var b = 0; b < title.json.titleIdL.length; b++) {
							rs = title.json.titleIdL[b];
							title_rs.push(new Ext.form.Radio( {
								boxLabel : rs.result,
								name : 'result' + i,
								inputValue : rs.resultId
							}));
						}

						new Ext.form.RadioGroup( {
							id : 'rg' + i,
							fieldLabel : title.json.titleName,
							name : title.json.titleId,
							items : [ title_rs ]
						});
						rd_set.add(Ext.getCmp('rg' + i));
						rd_set.doLayout();
					}

				}

			}

		});
		
		
		/******************答题窗口*************************/
		var opWin2 = new Ext.Window( {
			width : 900,
			height : 450,
			items : [ opForm ],
			layout : 'fit',
			resizable : false,
			collapsible : false,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			animCollapse : false,
			border : false,
			loadMask : true,
			closable : true,
			constrain : true,
			buttons : [ {
				text : '提交',
				id : 'commit',
				handler : function() {
				var flag=sum();
				if(flag=='success'){
					Ext.Ajax.request( {
						url : basepath + '/PaperAnswer!addCustRiskEvaluation.json',
						method : 'POST',
						params : {
							paperId : paperId,
							user:JsContext._userId,
							title_result:title_result
						},
						success : function(response) {
							Ext.MessageBox.alert('提示', '保存答题情况成功！');
						}
					});
				}
			
				}
			}, {
				text : '重置',
				id : 'reset_info',
				handler : function() {
					title_count = title_store2.getCount();
					for ( var j = 0; j < title_count; j++) {
						Ext.getCmp('rg' + j).reset();
					}

				}
			}, {
				text : '关闭',
				handler : function() {
					opWin2.hide();
				}
			} ]
		});
		
		opWin2.on('show',function(){
				
		});
		opWin2.on('hide',function(){
			for ( var i = 0; i < title_store.getCount(); i++) {
				debugger;
				Ext.getCmp('rg' + i).reset;
				rd_set.doLayout();
			}
		});

		

		
		
		var tbar = new Ext.Toolbar(
				{
					items : [
							{
								text : '答题',
								iconCls : 'addIconCss',
								handler : function() {
								if(paperManageGrid.getSelectionModel().selections.length>0){
									var records = paperManageGrid.getSelectionModel()
									.getSelections();		
									paperId=records[0].json.id;
									opWin2.show();
								}else{
									Ext.Msg.alert("系统提示信息","请选择其中一条记录进行答题！");
								}
							

								}
							},
							{
								text : '答题明细查询',
								iconCls : 'detailIconCss',
								handler : function() {
								qAInfoStore.load({
									params:{
									paper_id:'1'
								}
								});
								qAInfoWin.show();
							}
							},
							{
								text : '答题统计结果查询',
								iconCls : 'completeIconCss',
								handler : function() {
								data_store.reload();
								title_store.load({
									params:{
									paper_id:'1'
								}
								});
								detailWin.show();
							}
							
							}]
				});
		// 列表
		var paperManageGrid = new Ext.grid.GridPanel( {
			height : document.body.scrollHeight - 97,
			width : document.body.scrollWidth,
			frame:true,
			width:'100%',
			id:'paperManageGrid',
			autoScroll : true,
			tbar:tbar,
//			bbar:bbar,
			stripeRows : true, // 斑马线
			store:papermanageStore,
			loadMask:true,
			cm :productInfoColumns,
			sm :sm,
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
		        loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
//			height : document.body.scrollHeight - 97,
//			width : document.body.scrollWidth,
//			frame : true,
//			autoScroll : true,
//			region : 'center', // 返回给页面的div
//			store : papermanageStore,
//			stripeRows : true, // 斑马线
//			cm : papermanageColumns,
//			sm : sm,
//			tbar : tbar,
//			bbar : bbar,
//			viewConfig : {}
		});
	 
		
		
		// 客户非本行资产信息行号
//		var teamrownum = new Ext.grid.RowNumberer( {
//			header : 'No.',
//			width : 28
//		});
//
//		var teamsm = new Ext.grid.CheckboxSelectionModel();
//		// 客户非本行资产信息定义列模型
//
//		// 客户非本行资产信息数据存储
//		// 答案
//		var teamgrid = new Ext.grid.EditorGridPanel( {
//			// title : '资产信息',
//			height : 300,
//			frame : true,
//			overflow : 'auto',
//			autoScroll : true,
//			store : teamstore, // 数据存储
//			stripeRows : true, // 斑马线
//			cm : teamcm, // 列模型
//			sm : teamsm,
//			// bbar : bbar,
//			tbar : tbar2,
//			loadMask : {
//				msg : '正在加载表格数据,请稍等...'
//			}
//		});


		/**
	      * 输入项校验空格
	      */
	     function trim(_v) {         
	        if( _v != _v.trim()) {
	        	return  false;
	    	}
	        	return true;
	     };
	     //'trimText' : '输入项项首项尾有空格'
		
		/*******************整体显示布局******************/
		var viewport = new Ext.Viewport({
			layout:'border',
			items : [{
					region : 'north',
					id : 'north-panel',
					title : "题库管理->问卷管理",
					height : 100,
					hidden : false,
					margins : '0 0 0 0',
					items : [ simple ]
				},{
					region : 'center',
					id : 'center-panel',
					layout:'fit',
					margins : '0 0 0 0',
					items : [ paperManageGrid ]
				}]
		});

    });