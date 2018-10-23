Ext.onReady(function() {
	
	var custManagerRecord = new Ext.data.Record.create([
                            {name:'custNum',mapping:'CUST_NUM'},
                            {name:'mainCustNum',mapping:'MAIN_CUST_NUM'},
                            {name:'omainCustNum',mapping:'OMAIN_CUST_NUM'},
                            {name:'saveYearAvg',mapping:'SAVE_YEAR_AVG'},
                            {name:'saveBl',mapping:'SAVE_BL'},
                            {name:'loanYearAvg',mapping:'LOAN_YEAR_AVG'},
                            {name:'loanBl',mapping:'LOAN_BL'},
                            {name:'newCreateCust',mapping:'NEW_CREATE_CUST'},
                            {name:'custAum',mapping:'CUST_AUM'}
                            ]);
	
	var custManagerReader = new Ext.data.JsonReader({//读取json数据的panel
 	},custManagerRecord);

	var custManagerPanel = new Ext.FormPanel({
		title : '客户经理概览信息',
		reader  : custManagerReader,
		frame : true,
		 height : document.documentElement.clientHeight,
		autoScroll : true,
		region:'center',
		width:'100%',
		items:[{
			layout:'column',
			items:[{
				layout : 'form',
				columnWidth : .45,
				labelWidth : 120,
				items:[{
					xtype:'displayfield',
					name:'custNum',
					fieldLabel:'批次启动时间',
					labelStyle:'text-align:right;',
					readOnly:true,
					value:'2013-03-01',
					format:money,
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'omainCustNum',
					fieldLabel:'已经运行时间',
					labelStyle:'text-align:right',
					readOnly:'true',
					value:'23(小时)38(分钟)',
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'saveYearAvg',
					fieldLabel:'完成job数',
					labelStyle:'text-align:right;',
					value:'23(个)',
					readOnly:true,
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'loanYearAvg',
					fieldLabel:'正在运行job数',
					labelStyle:'text-align:right;',
					readOnly:true,
					value:'16(个)',
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'custAum',
					fieldLabel:'等待行运行job数',
					labelStyle:'text-align:right;',
					readOnly:true,
					value:'8(个)',
					anchor:'90%'
				},{
					xtype:'displayfield',
					id:'interBus',
					fieldLabel:'错误行运行job数',
					labelStyle:'text-align:right;',
					value:'5(个)',
					readOnly:true,
					anchor:'90%'
				}]
			}]
		}]
	});
	
	//1
	var custContrastRecord = Ext.data.Record.create(
			[
			 {name:'jobId',mapping:'JOBID'},
			 {name:'jobName',mapping:'JOBNAME'},
			 {name:'startTime',mapping:'STARTTIME'},
			 {name:'endTime',mapping:'ENDTIME'},
			 {name:'runTime',mapping:'RUNTIME'},
			 {name:'errorTime',mapping:'ERRORTIME'}
			 ]
	);

	var custContrastStore = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
										root : 'rows',
										totalProperty : 'num'
										 }, 
										 custContrastRecord
		)
	});

	var memberData = {
			TOTALCOUNT : 3,
			rows : [ {
				"JOBID" : "10001",
				"JOBNAME" : "客户数量跑批",
				"STARTTIME" : "2013-03-17",
				"ENDTIME" : "2013-03-18",
				"RUNTIME" : "27(小时)",
				"ERRORTIME" : ""
			}, {
				"JOBID" : "10002",
				"JOBNAME" : "存款日均跑批",
				"STARTTIME" : "2013-03-17",
				"ENDTIME" : "2013-03-18",
				"RUNTIME" : "27(小时)",
				"ERRORTIME" : ""
			}]
		};
	custContrastStore.loadData(memberData);




	 /*************************************列模型***********************************************/
	 // 定义自动当前页行号
	 var rownum = new Ext.grid.RowNumberer( {
	     header : 'No.',
	     width : 28
	 });
	 var sm = new Ext.grid.CheckboxSelectionModel();
	 var custContrastColumns = new Ext.grid.ColumnModel([rownum,
             				{ header:'Job编号',dataIndex:'jobId',sortable:true,width:150},
             				{ header:'Job名称',dataIndex:'jobName',sortable:true,width:150},
             				{ header:'开始时间',dataIndex:'startTime',sortable:true,width:150},
             				{ header:'结束时间',dataIndex:'endTime',sortable:true,width:150},
             				{ header:'运行时间',dataIndex:'runTime',sortable:true,width:150},
             				{ header:'报错误时间',dataIndex:'errorTime',sortable:true,width:150}
             				]);
	 var custContrastGrid = new Ext.grid.EditorGridPanel({			
			store:custContrastStore, 
			frame:true,
			height : document.documentElement.clientHeight,
			cm:custContrastColumns,
			region:'center',
//			sm:sm,
	        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		    },
		    loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		    }
	 });
	 //1 end
		
	//2
		var custContrastRecord2 = Ext.data.Record.create(
				[
				 {name:'jobId',mapping:'JOBID'},
				 {name:'jobName',mapping:'JOBNAME'},
				 {name:'startTime',mapping:'STARTTIME'},
				 {name:'endTime',mapping:'ENDTIME'},
				 {name:'runTime',mapping:'RUNTIME'},
				 {name:'errorTime',mapping:'ERRORTIME'}
				 ]
		);

		var custContrastStore2 = new Ext.data.Store({
			reader : new Ext.data.JsonReader({
											root : 'rows',
											totalProperty : 'num'
											 }, 
											 custContrastRecord2
			)
		});

		var memberData2 = {
				TOTALCOUNT : 3,
				rows : [ {
					"JOBID" : "10001",
					"JOBNAME" : "客户数量跑批",
					"STARTTIME" : "2013-03-17",
					"ENDTIME" : "",
					"RUNTIME" : "27(小时)",
					"ERRORTIME" : ""
				}, {
					"JOBID" : "10002",
					"JOBNAME" : "存款日均跑批",
					"STARTTIME" : "2013-03-17",
					"ENDTIME" : "",
					"RUNTIME" : "27(小时)",
					"ERRORTIME" : ""
				}]
			};
		custContrastStore2.loadData(memberData2);




		 /*************************************列模型***********************************************/
		 // 定义自动当前页行号
		 var rownum2 = new Ext.grid.RowNumberer( {
		     header : 'No.',
		     width : 28
		 });
		 var sm2 = new Ext.grid.CheckboxSelectionModel();
		 var custContrastColumns2 = new Ext.grid.ColumnModel([rownum,
	             				{ header:'Job编号',dataIndex:'jobId',sortable:true,width:150},
	             				{ header:'Job名称',dataIndex:'jobName',sortable:true,width:150},
	             				{ header:'开始时间',dataIndex:'startTime',sortable:true,width:150},
	             				{ header:'结束时间',dataIndex:'endTime',sortable:true,width:150},
	             				{ header:'运行时间',dataIndex:'runTime',sortable:true,width:150},
	             				{ header:'报错误时间',dataIndex:'errorTime',sortable:true,width:150}
	             				]);
		 var custContrastGrid2 = new Ext.grid.EditorGridPanel({			
				store:custContrastStore2, 
				frame:true,
				height : document.documentElement.clientHeight,
				cm:custContrastColumns2,
				region:'center',
//				sm:sm,
		        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			    },
			    loadMask : {
				  msg : '正在加载表格数据,请稍等...'
			    }
		 });
		 //2 end
		 
		//3
			var custContrastRecord3 = Ext.data.Record.create(
					[
					 {name:'jobId',mapping:'JOBID'},
					 {name:'jobName',mapping:'JOBNAME'},
					 {name:'startTime',mapping:'STARTTIME'},
					 {name:'endTime',mapping:'ENDTIME'},
					 {name:'runTime',mapping:'RUNTIME'},
					 {name:'errorTime',mapping:'ERRORTIME'}
					 ]
			);

			var custContrastStore3 = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
												root : 'rows',
												totalProperty : 'num'
												 }, 
												 custContrastRecord3
				)
			});

			var memberData3 = {
					TOTALCOUNT : 3,
					rows : [ {
						"JOBID" : "10001",
						"JOBNAME" : "客户数量跑批",
						"STARTTIME" : "2013-03-28",
						"ENDTIME" : "",
						"RUNTIME" : "",
						"ERRORTIME" : ""
					}, {
						"JOBID" : "10002",
						"JOBNAME" : "存款日均跑批",
						"STARTTIME" : "2013-03-28",
						"ENDTIME" : "",
						"RUNTIME" : "",
						"ERRORTIME" : ""
					}]
				};
			custContrastStore3.loadData(memberData3);




			 /*************************************列模型***********************************************/
			 // 定义自动当前页行号
			 var rownum3 = new Ext.grid.RowNumberer( {
			     header : 'No.',
			     width : 28
			 });
			 var sm3 = new Ext.grid.CheckboxSelectionModel();
			 var custContrastColumns3 = new Ext.grid.ColumnModel([rownum,
		             				{ header:'Job编号',dataIndex:'jobId',sortable:true,width:150},
		             				{ header:'Job名称',dataIndex:'jobName',sortable:true,width:150},
		             				{ header:'开始时间',dataIndex:'startTime',sortable:true,width:150},
		             				{ header:'结束时间',dataIndex:'endTime',sortable:true,width:150},
		             				{ header:'运行时间',dataIndex:'runTime',sortable:true,width:150},
		             				{ header:'报错误时间',dataIndex:'errorTime',sortable:true,width:150}
		             				]);
			 var custContrastGrid3 = new Ext.grid.EditorGridPanel({			
					store:custContrastStore3, 
					frame:true,
					height : document.documentElement.clientHeight,
					cm:custContrastColumns3,
					region:'center',
//					sm:sm,
			        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				    },
				    loadMask : {
					  msg : '正在加载表格数据,请稍等...'
				    }
			 });
			 //3 end
			 
			//4
				var custContrastRecord4 = Ext.data.Record.create(
						[
						 {name:'jobId',mapping:'JOBID'},
						 {name:'jobName',mapping:'JOBNAME'},
						 {name:'startTime',mapping:'STARTTIME'},
						 {name:'endTime',mapping:'ENDTIME'},
						 {name:'runTime',mapping:'RUNTIME'},
						 {name:'errorTime',mapping:'ERRORTIME'}
						 ]
				);

				var custContrastStore4 = new Ext.data.Store({
					reader : new Ext.data.JsonReader({
													root : 'rows',
													totalProperty : 'num'
													 }, 
													 custContrastRecord4
					)
				});

				var memberData4 = {
						TOTALCOUNT : 3,
						rows : [ {
							"JOBID" : "10001",
							"JOBNAME" : "客户数量跑批",
							"STARTTIME" : "2013-03-17",
							"ENDTIME" : "2013-03-18",
							"RUNTIME" : "27(小时)",
							"ERRORTIME" : "2013-03-18"
						}, {
							"JOBID" : "10002",
							"JOBNAME" : "存款日均跑批",
							"STARTTIME" : "2013-03-17",
							"ENDTIME" : "2013-03-18",
							"RUNTIME" : "27(小时)",
							"ERRORTIME" : "2013-03-18"
						}]
					};
				custContrastStore4.loadData(memberData4);




				 /*************************************列模型***********************************************/
				 // 定义自动当前页行号
				 var rownum4 = new Ext.grid.RowNumberer( {
				     header : 'No.',
				     width : 28
				 });
				 var sm4 = new Ext.grid.CheckboxSelectionModel();
				 var custContrastColumns4 = new Ext.grid.ColumnModel([rownum,
			             				{ header:'Job编号',dataIndex:'jobId',sortable:true,width:150},
			             				{ header:'Job名称',dataIndex:'jobName',sortable:true,width:150},
			             				{ header:'开始时间',dataIndex:'startTime',sortable:true,width:150},
			             				{ header:'结束时间',dataIndex:'endTime',sortable:true,width:150},
			             				{ header:'运行时间',dataIndex:'runTime',sortable:true,width:150},
			             				{ header:'报错误时间',dataIndex:'errorTime',sortable:true,width:150}
			             				]);
				 var custContrastGrid4 = new Ext.grid.EditorGridPanel({			
						store:custContrastStore4, 
						frame:true,
						height : document.documentElement.clientHeight,
						cm:custContrastColumns4,
						region:'center',
//						sm:sm,
				        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
					    },
					    loadMask : {
						  msg : '正在加载表格数据,请稍等...'
					    }
				 });
				 //4 end
	 
		var FormatCnMoney = function(v) {
			return Ext.util.Format.number(v, '0,000.00');
		};
		
		var date= document.documentElement.clientHeight/1.8;

		
		// 布局模型
		var tabmain = new Ext.TabPanel({
			id:'tabmain',
	        activeTab: 0,
	        frame:true,
	        height:document.documentElement.clientHeight,
	        defaults:{autoHeight: true},
	        items:[
	            { title: 'ETL监控页面',items:[custManagerPanel]},
	            { title: '完成列表',items:[custContrastGrid]},
				{ title: '运行列表',items:[custContrastGrid2]},
				{ title: '等待列表',items:[custContrastGrid3]},
				{ title: '错误列表',items:[custContrastGrid4]}
	        ]
	    });
		
		var viewport = new Ext.Viewport({
			layout : 'fit',
			autoScroll:true,
			items:[{
	            xtype:'portal',
	            id:'center',
	            region:'center',
			items: [{
	              columnWidth:1,
	              collapsible:true,
	              items:[tabmain]
	              }] 
			}]
		});
		

	
});