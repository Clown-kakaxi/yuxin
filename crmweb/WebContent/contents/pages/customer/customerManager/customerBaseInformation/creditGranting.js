/**
 * 业务合作信息->授信
 */
Ext.onReady(function() {
    /**************************授信信息****************************************/
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, sm,
	            {header : 'id',dataIndex : 'id',sortable : true,hidden :true},
		        {header : 'CUST_ID',dataIndex : 'cust_id',sortable : true,hidden :true},
		        {header : '入帐机构',dataIndex : 'manageorgid',sortable : true},
				{header : '合同编号',dataIndex : 'serialno',sortable : true},
				{header : '申请人组织机构代码',dataIndex : 'cust_zzdm',sortable : true},
				{header : '申请人名称',dataIndex : 'cust_zh_name',sortable : true},
				{header : '授信起始日',dataIndex : 'putoutdate',sortable : true},
				{header : '授信终止日',dataIndex : 'maturity',sortable : true},
				{header : '授信总额(元)',dataIndex : 'businesssum',sortable : true},
				{header : '实际出帐金额(元)',dataIndex : 'actualputoutsum',sortable : true},
				{header : '余额(元)',dataIndex : 'balance1',sortable : true},
				{header : '合同状态',dataIndex : 'freezetype',sortable : true},
				{header : '管户人',dataIndex : 'manageuserid',sortable : true}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCreditGranting1.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			        root:'json.data'
			        }, [{name: 'id'},
						{name: 'cust_id'},
						{name: 'manageorgid'},
						{name: 'serialno'},
						{name: 'cust_zzdm'},
						{name: 'cust_zh_name'},
						{name: 'putoutdate'},
						{name: 'maturity'},
						{name: 'businesssum'},
						{name: 'actualputoutsum'},
						{name: 'balance1'},
						{name: 'freezetype'},
						{name: 'manageuserid'}
					])
				});
	
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value1',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});
	
	var number = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
	/*		pagesize_combo.on("select", function(comboBox) {
						bbar.pageSize = parseInt(comboBox.getValue());
						number = parseInt(comboBox.getValue());
						store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});*/
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});

    /**************************贷款信息****************************************/

	 //复选框
	var sm2 = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum2 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm2 = new Ext.grid.ColumnModel([rownum2,sm2, 
	            {header : 'id',dataIndex : 'id',sortable : true,hidden :true},
		        {header : 'CUST_ID',dataIndex : 'cust_id',sortable : true,hidden :true},
		        {header : '入帐机构',dataIndex : 'manageorgid',sortable : true},
				{header : '合同编号',dataIndex : 'serialno',sortable : true},
				{header : '申请人组织机构代码',dataIndex : 'cust_zzdm',sortable : true},
				{header : '申请人名称',dataIndex : 'cust_zh_name',sortable : true},
				{header : '授信起始日',dataIndex : 'putoutdate',sortable : true},
				{header : '授信终止日',dataIndex : 'maturity',sortable : true},
				{header : '授信总额(元)',dataIndex : 'businesssum',sortable : true},
				{header : '实际出帐金额(元)',dataIndex : 'actualputoutsum',sortable : true},
				{header : '余额(元)',dataIndex : 'balance1',sortable : true},
				{header : '合同状态',dataIndex : 'freezetype',sortable : true},
				{header : '管户人',dataIndex : 'manageuserid',sortable : true}
			]);

	/**
	 * 数据存储
	 */
	var store2 = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/QueryCreditGranting1.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			        root:'json.data'
			        }, [{name: 'id'},
						{name: 'cust_id'},
						{name: 'manageorgid'},
						{name: 'serialno'},
						{name: 'cust_zzdm'},
						{name: 'cust_zh_name'},
						{name: 'putoutdate'},
						{name: 'maturity'},
						{name: 'businesssum'},
						{name: 'actualputoutsum'},
						{name: 'balance1'},
						{name: 'freezetype'},
						{name: 'manageuserid'}
					])
				});
	
	
	// 每页显示条数下拉选择框
	var pagesize_combo2 = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value1',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});
	
	var number2 = parseInt(pagesize_combo.getValue());
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
	var bbar2 = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});






	var loanpagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value1',
						displayField : 'text1',
						value : '20',
						editable : false,
						width : 85
					});
	
	var loannumber = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
			loanpagesize_combo.on("select", function(comboBox) {
						loanbbar.pageSize = parseInt(comboBox.getValue());
						loannumber = parseInt(comboBox.getValue());
						store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});
					// 每页显示条数下拉选择框
			
	// 分页工具栏
	var loanbbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', loanpagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});

     
    //复选框
	var loansm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var loanrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var loancm = new Ext.grid.ColumnModel([loanrownum,loansm, 
	           {
				header : '客户中文名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '组织机构代码',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 150
			}, {
				header : '客户类型',
				dataIndex : 'customertype'
			}, {
				header : '客户级别',
				dataIndex : 'customerlevel'
			}
			]);

	/**
	 * 数据存储
	 */
	var loanstore = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customername' // Json中的属性Key值
								}, {
									name : 'organizationcode'
								}, {
									name : 'customertype'
								}, {
									name : 'customerlevel'
								}
								])
			});
	var dueBillpagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value1',
						displayField : 'text1',
						value : '20',
						editable : false,
						width : 85
					});
	
	var dueBillnumber = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
			loanpagesize_combo.on("select", function(comboBox) {
						dueBillbbar.pageSize = parseInt(comboBox.getValue());
						dueBillnumber = parseInt(comboBox.getValue());
						dueBillstore.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});
					// 每页显示条数下拉选择框
			
	// 分页工具栏
	var dueBillbbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', loanpagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});

     
    //复选框
	var dueBillsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var dueBillrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var dueBillcm = new Ext.grid.ColumnModel([loanrownum,loansm, 
	           {
				header : '客户中文名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '组织机构代码',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 150
			}, {
				header : '客户类型',
				dataIndex : 'customertype'
			}, {
				header : '客户级别',
				dataIndex : 'customerlevel'
			}
			]);

	/**
	 * 数据存储
	 */
	var dueBillstore = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customername' // Json中的属性Key值
								}, {
									name : 'organizationcode'
								}, {
									name : 'customertype'
								}, {
									name : 'customerlevel'
								}
								])
			});
	
	
	var standingBooknumber = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
			loanpagesize_combo.on("select", function(comboBox) {
						loanbbar.pageSize = parseInt(comboBox.getValue());
						loannumber = parseInt(comboBox.getValue());
						store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});
					// 每页显示条数下拉选择框
			
	// 分页工具栏
	var standingBookbbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', loanpagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});

     
    //复选框
	var standingBooksm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var standingBookrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var standingBookcm = new Ext.grid.ColumnModel([loanrownum,loansm, 
	           {
				header : '客户中文名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '组织机构代码',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 150
			}, {
				header : '客户类型',
				dataIndex : 'customertype'
			}, {
				header : '客户级别',
				dataIndex : 'customerlevel'
			}
			]);

	/**
	 * 数据存储
	 */
	var standingBookstore = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customername' // Json中的属性Key值
								}, {
									name : 'organizationcode'
								}, {
									name : 'customertype'
								}, {
									name : 'customerlevel'
								}
								])
			});
   var lowerpagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value1',
						displayField : 'text1',
						value : '20',
						editable : false,
						width : 85
					});
	
	var lowernumber = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
			loanpagesize_combo.on("select", function(comboBox) {
						dueBillbbar.pageSize = parseInt(comboBox.getValue());
						dueBillnumber = parseInt(comboBox.getValue());
						dueBillstore.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});
					// 每页显示条数下拉选择框
			
	// 分页工具栏
	var lowerbbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', loanpagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});

     
    //复选框
	var lowersm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var lowerrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var lowercm = new Ext.grid.ColumnModel([loanrownum,loansm, 
	           {
				header : '客户中文名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '组织机构代码',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 150
			}, {
				header : '客户类型',
				dataIndex : 'customertype'
			}, {
				header : '客户级别',
				dataIndex : 'customerlevel'
			}
			]);

	/**
	 * 数据存储
	 */
	var lowerstore = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customername' // Json中的属性Key值
								}, {
									name : 'organizationcode'
								}, {
									name : 'customertype'
								}, {
									name : 'customerlevel'
								}
								])
			});
	var tbar = new Ext.Toolbar({
				items : [{
					text : '客户授信合同列表',
					handler : function() {
						addRoleWindow.show();
					}
				}
				]
			});
	var tbar1 = new Ext.Toolbar({
			items : [{
				text : '查询借据',
				handler : function() {
					addRoleWindow1.show();
					}
				},
				{
				text : '授信业务下放款明细',
				handler : function() {
					addRoleWindow3.show();
					}
				}
				
				]
			});
	var tbar2 = new Ext.Toolbar({
			items : [{
				text : '信贷台账',
				handler : function() {
					addRoleWindow2.show();
					}
				}
				]
			});
	var tbar3 = new Ext.Toolbar({
			items : [{
				text : '信贷台账',
				handler : function() {
					addRoleWindow2.show();
					}
				}
				]
			});
	var tbar4 = new Ext.Toolbar({
			items : [{
				text : '查询借据',
				handler : function() {
					addRoleWindow1.show();
					}
				}
				]
			});


	// 授信信息
	var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">授信信息</span>',
				height :212,
				width : 1194,
				frame : true,
				autoScroll : true,
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm:sm,
				tbar:tbar,
				bbar : bbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var grid2 = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">贷款信息</span>',
				height :212,
				width : 1194,
				frame : true,
				autoScroll : true,
				store : store2, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm2, // 列模型
				sm:sm2,
				tbar:tbar4,
				bbar : bbar2,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	

    // 授信合同
	var loanGrid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">客户授信合同列表</span>',
				height :215,
				frame : true,
				autoScroll : true,
				store : loanstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : loancm, // 列模型
				tbar:tbar1,
				sm : loansm, // 复选框
				bbar : loanbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	//借据信息
	var dueBillGrid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">借据信息</span>',
				height :215,
				frame : true,
				autoScroll : true,
				store : dueBillstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : dueBillcm, // 列模型
				tbar:tbar2,
				sm : dueBillsm, // 复选框
				bbar : dueBillbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	//信贷台账
	var standingBookGrid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">信贷台账</span>',
				height :215,
				
				frame : true,
				autoScroll : true,
				store : standingBookstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : standingBookcm, // 列模型
				sm : standingBooksm, // 复选框
				bbar : standingBookbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	//授信业务下放款明细
	var lowerGrid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">授信业务下放款明细</span>',
				height :215,
				frame : true,
				autoScroll : true,
				store : lowerstore, // 数据存储
				stripeRows : true, // 斑马线
				tbar:tbar3,
				cm : lowercm, // 列模型
				sm : lowersm, // 复选框
				bbar : lowerbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 900,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				
				// iconCls : 'page_addIcon',
				maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [loanGrid]
			});
	var addRoleWindow1 = new Ext.Window(
			{
				layout : 'fit',
				width : 900,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				// iconCls : 'page_addIcon',
				maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [dueBillGrid]
			});
	var addRoleWindow2 = new Ext.Window(
			{
				layout : 'fit',
				width : 900,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				//iconCls : 'page_addIcon',
				maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [standingBookGrid]
			});
	var addRoleWindow3= new Ext.Window(
			{
				layout : 'fit',
				width : 900,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				// iconCls : 'page_addIcon',
				maximizable: true,
				maximized:true,
				//collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [lowerGrid]
			});
	// 布局模型 
	var viewport = new Ext.Panel({
			renderTo:'grid',
				items: [{  
				    margins: '0 0 0 0',
				    items : [grid2,grid]
			    }] 

			});
	store2.load();
}) ;