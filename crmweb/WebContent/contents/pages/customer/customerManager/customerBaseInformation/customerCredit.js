Ext.onReady(function() {
	var custid =oCustInfo.cust_id;
	var cust_type = oCustInfo.cust_type;
		var rsRecord = Ext.data.Record.create( [ {
			name : 'customId',
			mapping : 'CUSTOM_ID'
		}, {
			name : 'custName',  
			mapping : 'CUST_NAME' 
		}, {
			name : 'awardKind',
			mapping : 'AWARD_KIND_ORA'
		}, {
			name : 'lineTyp',
			mapping : 'LINE_TYP_ORA'
		}, {
			name : 'currNo',
			mapping : 'CURR_NO_ORA'
		}, {
			name : 'awardYnLine',
			mapping : 'AWARD_YN_LINE'
		} , {
			name : 'usedLine',
			mapping : 'USED_LINE'
		}, {
			name : 'suplusLine',
			mapping : 'SUPLUS_LINE'
		}, {
			name : 'changLine',
			mapping : 'CHANG_LINE'
		}, {
			name : 'changDate',
			mapping : 'CHANG_DATE'
		}, {
			name : 'awardStart',
			mapping : 'AWARD_START'
		}, {
			name : 'ydt',
			mapping : 'YDT'
		}, {
			name : 'lintSts',
			mapping : 'LINE_STS'
		}, {
			name : 'recordDate',
			mapping : 'RECORD_DATE'
		}, {
			name : 'registrant',
			mapping : 'REGISTRANT'
		}, {
			name : 'firstDutyOfficer', 
			mapping : 'FIRST_DUTY_OFFICER'
		}, {
			name : 'secDutyOffice',
			mapping : 'SEC_DUTY_OFFICER'
		}, {
			name : 'agencyBran',
			mapping : 'AGENCY_BRAN'
		}, {
			name : 'manageBran',
			mapping : 'MANAGE_BRAN'
		}]);
		var rsRecord2 = Ext.data.Record.create( [ 
		  {
			name : 'custId',
			mapping : 'CUST_ID'
		}, {
			name : 'ceStanBookType',
			mapping : 'CE_STAN_BOOK_TYPE_ORA' 
		}, {
			name : 'projectNo',
			mapping : 'PROJECT_NO'
		}, {
			name : 'marGinsMoney',
			mapping : 'MARGINS_MONEY'
		}, {
			name : 'amplifyMultiple',
			mapping : 'AMPLIFY_MULTIPLE'
		},{
			name : 'ceLimit',
			mapping : 'CE_LIMIT'
		}, {
			name : 'usedLimit',
			mapping : 'USED_LIMIT'
		}, {
			name : 'limitStat',
			mapping : 'LIMIT_STAT_ORA'
		}, {
			name : 'startDate',
			mapping : 'START_DATE'
		}, {
			name : 'endDate',
			mapping : 'END_DATE'
		} ]);
		var rsRecord3 = Ext.data.Record.create( [

		{
			name : 'customId',
			mapping : 'CUSTOM_ID'
		}, {
			name : 'canGrntAmt',
			mapping : 'CAN_GRNT_AMT'
		}, {
			name : 'indvLine',  
			mapping : 'INDV_LINE'
		}, {
			name : 'corpLine',
			mapping : 'CORP_LINE'
		}, {
			name : 'startDate',
			mapping : 'START_DATE'
		}, {
			name : 'endDate',
			mapping : 'END_DATE'
		}, {
			name : 'status',
			mapping : 'STATUS_ORA'
		}, {
			name : 'astCond',
			mapping : 'AST_COND'
		} , {
			name : 'astDes',
			mapping : 'AST_DES'
		} ]);

		var rsreader = new Ext.data.JsonReader( {
			successProperty : 'success',
			idProperty : 'CUST_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, rsRecord);

		var rsStore = new Ext.data.Store( {
			restful : true,
			method : 'GET',
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/customerCredita.json'
			}),
			reader : rsreader
		});
		rsStore.baseParams = {
			'condition' : Ext.encode({
				custId : custid
			})
		};
		
		var rsreader2 = new Ext.data.JsonReader( {
			successProperty : 'success',
			idProperty : 'CUST_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, rsRecord2);

		var rsStore2 = new Ext.data.Store( {
			restful : true,
			method : 'GET',
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/customerCreditb.json'
			}),
			reader : rsreader2
		});
		rsStore2.baseParams = {
			'condition' : Ext.encode({
				custId : custid
			})
		};

		var rsreader3 = new Ext.data.JsonReader( {
			successProperty : 'success',
			idProperty : 'CUST_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, rsRecord3);

		var rsStore3 = new Ext.data.Store( {
			restful : true,
			method : 'GET',

			url : basepath + '/customerCreditc.json?',

			reader : rsreader3

		});
		rsStore3.baseParams = {
			'condition' : Ext.encode({
				custId : custid
			})
		};

		// 复选框
		var sm = new Ext.grid.CheckboxSelectionModel();

		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});

		// 定义列模型
		var cm = new Ext.grid.ColumnModel( [ rownum, sm, {
					header : '统计日期',
					dataIndex : 'customId',
					sortable : true,
					align:'right',
					renderer:function(){return '2012-06-29';}
					//width : 150
				}, {
					header : '授信品种',
					dataIndex : 'awardKind',
					align:'left',
						sortable : true
				}, {
					header : '额度使用类型',
					dataIndex : 'lineTyp',
					align:'left',
					sortable : true
					//width : 150
				},{
					header : '货币号',
					dataIndex : 'currNo',
					align:'left',
					sortable : true
					//width : 150
				}, {
					header : '授信额度（元）',
					dataIndex : 'awardYnLine',
					align:'right',
					renderer:money('0,000.00'),
					sortable : true
				}, {
					header : '已用额度（元）',
					dataIndex : 'usedLine',
					align:'right',
					renderer:money('0,000.00'),
						sortable : true
				}, {
					header : '剩余额度',
					dataIndex : 'suplusLine', 
					align:'right',
					renderer:money('0,000.00'),
					sortable : true
					//width : 150
				},{
					header : '变更前额度（元）',
					dataIndex : 'changLine',
					align:'right',
					renderer:money('0,000.00'),
					sortable : true,
					width:120
				}, {
					header : '变更日期',
					dataIndex : 'changDate',
					align:'right',
						sortable : true
				}, {
					header : '授信起始日',
					dataIndex : 'awardStart',
					align:'right',
						sortable : true
				}, {
					header : '到期日期',
					dataIndex : 'ydt',
					align:'right',
					sortable : true
					//width : 150
				},{
					header : '额度状态',
					dataIndex : 'lintSts',
					align:'left',
					sortable : true
					//width : 150
				}, {
					header : '登记日期',
					dataIndex : 'recordDate',
					align:'right',
						sortable : true
				}, {
					header : '登记人',
					dataIndex : 'registrant',
					align:'left',
						sortable : true
				}, {
					header : '第一责任人',
					dataIndex : 'firstDutyOfficer',
					align:'left',
					sortable : true
					//width : 150
				},{
					header : '第二责任人',
					dataIndex : 'secDutyOffice',
					align:'left',
					sortable : true
					//width : 150
				}, {
					header : '经办机构',
					dataIndex : 'agencyBran',
					align:'left',
						sortable : true
				}, {
					header : '管理机构',
					dataIndex : 'manageBran',
					align:'left',
						sortable : true
				}]);
		var cm2 = new Ext.grid.ColumnModel( [ rownum, sm, {
			header : '客户编号', // 列标题
			dataIndex : 'custId', // 数据索引:和Store模型对应
			sortable : true
			//width : 150
		// 是否可排序
				}, {
					header : '授信台账类型',
					dataIndex : 'ceStanBookType',   
					align:'left',
					sortable : true
					//width : 150
				}, {
					header : '项目编号',
					dataIndex : 'projectNo',
						sortable : true
				}, {
					header : '保证金金额（元）',
					dataIndex : 'marGinsMoney',
					align:'right',
					renderer:money('0,000.00'),
					sortable : true,
					width:120
				}, {
					header : '放大倍数',
					dataIndex : 'amplifyMultiple',
					sortable : true
					//width : 150
				}, {
					header : '授信额度',
					dataIndex : 'ceLimit',
					align:'right',
					renderer:money('0,000.00'),
						sortable : true
				}, {
					header : '已用额度',
					dataIndex : 'usedLimit',
					align:'right',
					renderer:money('0,000.00'),
						sortable : true
				}, {
					header : '额度状态',
					dataIndex : 'limitStat',
					align:'left',
						sortable : true
				}, {
					header : '起始日期',
					dataIndex : 'startDate',
					align:'right',
						sortable : true
				}, {
					header : '到期日期',
					align:'right',
					dataIndex : 'endDate',
						sortable : true
				} ]);
		var cm3 = new Ext.grid.ColumnModel( [ rownum, sm, {
			header : '统计日期', // 列标题
			dataIndex : 'customId', // 数据索引:和Store模型对应
			sortable : true,
			align:'right',
			width : 100,
			renderer:function(){return '2012-06-29';}
		// 是否可排序
				}, {
					header : '可担保总额',
					dataIndex : 'canGrntAmt',  
					renderer:money('0,000.00'),
					sortable : true
					//width : 150
				}, {
					header : '个人额度',
					dataIndex : 'indvLine',
					renderer:money('0,000.00'),
						sortable : true
				}, {
					header : '企业额度',
					dataIndex : 'corpLine',
					renderer:money('0,000.00'),
					sortable : true
				}, {
					header : '起始日期',
					dataIndex : 'startDate',
					align:'right',
					sortable : true
					//width : 150
				}, {
					header : '到期日期',
					dataIndex : 'endDate',
					align:'right',
						sortable : true
				}, {
					header : '状态',
					dataIndex : 'status',
					align:'left',
						sortable : true
				}, {
					header : '限制条件',
					dataIndex : 'astCond',
					align:'left',
						sortable : true
				}, {
					header : '限制说明',
					dataIndex : 'astDes',
					align:'left',
						sortable : true
				} ]);

		// 表格实例
	
		
		var pagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
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
					//alert(bbar.pageSize);
					number = parseInt(comboBox.getValue());
					//alert(number);
					rsStore.reload({
								params : {
									start : 0,
									limit : bbar.pageSize
								}
							});
				});
		
		// 分页工具栏
		var bbar = new Ext.PagingToolbar({
			
							pageSize : number,
							store : rsStore,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
							emptyMsg : "没有符合条件的记录",
							items : ['-', '&nbsp;&nbsp;', pagesize_combo
//							         , '-', {
//										text : '合计',
//										iconCls : 'addIcon'
//										//handler : function() {
//											//summary.toggleSummary();
//										//}
//									}
							]
						});
		var panel = new Ext.grid.GridPanel( {
			// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
			// title : '<span style="font-weight:normal">表格综合演示一</span>',
			// renderTo : 'gridDiv', // 和JSP页面的DIV元素ID对应
			//autoScroll : true,
			height:document.body.clientHeight-90,
			width : document.body.scrollWidth-230,
			gridHeight : document.body.clientHeight-100,
			frame : true,
			region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
			store : rsStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : cm, // 列模型
			sm : sm, // 复选框
			// tbar : tbar, // 表格工具栏
			 bbar : bbar,// 分页工具栏
			viewConfig : {
			//forceFit:false
			   //autoScroll:true
			// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			// forceFit : true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		rsStore.load( {
			params : {
				start : 0,
				limit : 100
			}
		});
		var pagesize_combo2 = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});

		var number2 = parseInt(pagesize_combo.getValue());

		// 改变每页显示条数reload数据
		pagesize_combo2.on("select", function(comboBox) {
					bbar2.pageSize = parseInt(comboBox.getValue());
					//alert(bbar.pageSize);
					number2 = parseInt(comboBox.getValue());
					//alert(number);
					rsStore2.reload({
								params : {
									start : 0,
									limit : bbar2.pageSize
								}
							});
				});
		
		// 分页工具栏
		var bbar2 = new Ext.PagingToolbar({
			
							pageSize : number2,
							store : rsStore2,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
							emptyMsg : "没有符合条件的记录",
							items : ['-', '&nbsp;&nbsp;', pagesize_combo2
//							         , '-', {
//										text : '合计',
//										iconCls : 'addIcon'
//										//handler : function() {
//											//summary.toggleSummary();
//										//}
//									}
							]
						});
		var panel2 = new Ext.grid.GridPanel( {
			// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
			// title : '<span style="font-weight:normal">表格综合演示一</span>',
			// renderTo : 'gridDiv', // 和JSP页面的DIV元素ID对应
			//autoScroll : true,
			width: 1160,
			height : 450,
			frame : true,
			//autoScroll : true,
			region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
			store : rsStore2, // 数据存储
			stripeRows : true, // 斑马线
			cm : cm2, // 列模型
			sm : sm, // 复选框
			// tbar : tbar, // 表格工具栏
			 bbar : bbar2,// 分页工具栏
			viewConfig : {
			// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			// forceFit : false
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		rsStore2.load( {
			params : {
				start : 0,
				limit : 100
			}
		});
		var pagesize_combo3 = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});

		var number3 = parseInt(pagesize_combo3.getValue());

		// 改变每页显示条数reload数据
		pagesize_combo3.on("select", function(comboBox) {
					bbar3.pageSize = parseInt(comboBox.getValue());
					//alert(bbar.pageSize);
					number3 = parseInt(comboBox.getValue());
					//alert(number);
					rsStore3.reload({
								params : {
									start : 0,
									limit : bbar3.pageSize
								}
							});
				});
		
		// 分页工具栏
		var bbar3 = new Ext.PagingToolbar({
			
							pageSize : number3,
							store : rsStore3,
							displayInfo : true,
							displayMsg : '显示{0}条到{1}条,共{2}条',
							//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
							emptyMsg : "没有符合条件的记录",
							items : ['-', '&nbsp;&nbsp;', pagesize_combo3
//							         , '-', {
//										text : '合计',
//										iconCls : 'addIcon'
//										//handler : function() {
//											//summary.toggleSummary();
//										//}
//									}
							]
						});
		var panel3 = new Ext.grid.GridPanel( {
			// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
			// title : '<span style="font-weight:normal">表格综合演示一</span>',
			// renderTo : 'gridDiv', // 和JSP页面的DIV元素ID对应
			height:document.body.clientHeight-90,
			width : document.body.scrollWidth-230,
			gridHeight : document.body.clientHeight-100,
			frame : true,
			//autoScroll : true,
			region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
			store : rsStore3, // 数据存储
			stripeRows : true, // 斑马线
			cm : cm3, // 列模型
			sm : sm, // 复选框
			// tbar : tbar, // 表格工具栏
			 bbar : bbar3,// 分页工具栏
			viewConfig : {
			// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
			// forceFit : false
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		rsStore3.load( {
			params : {
				start : 0,
				limit : 100
			}
		});
		
	
	

		// 布局模型
		var tabmain = new Ext.TabPanel({
			id:'tabmain',
	        activeTab: 0,
	        frame:true,
	        height:document.body.clientHeight-30,
	        defaults:{autoHeight: true},
	        items:[
	            { title: '客户授信信息',items:[panel]},
	            { id:'titl',title: '项目授信台帐',items:[panel2]},
				{ title: '担保公司授信信息',items:[panel3]}
	        ]
	    });
		
		//添加监听，对私客户进入时，项目授信台帐不可见
		tabmain.addListener('beforerender',function(){
			if(cust_type!='2'){
			Ext.getCmp('titl').destroy();
			}
		});
		
		// 布局模型 
		var viewport_center = new Ext.Panel({
			 renderTo:'viewport_center',
//			 id:'viewport_center',
				frame:true,
				title:'客户授信信息',
				height:document.body.clientHeight-30,
				layout:'fit',
				autoScroll:true,
				items: [tabmain]
				});

	});