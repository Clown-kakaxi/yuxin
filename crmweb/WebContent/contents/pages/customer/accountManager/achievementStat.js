Ext.onReady(function() {
	/*// 定义自动当前页行号
	var finalrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var finalcm = new Ext.grid.ColumnModel([finalrownum, 
	           {
				header : '客户经理名称', // 列标题
				dataIndex : 'f1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    },{
				header : '客户经理编号',
				dataIndex : 'f2',
				sortable : true,
				width : 150
			},{
				header : '绩效工资',
				dataIndex : 'f3',
				sortable : true,
				width : 150
			}
			]);
	var finalstore = new Ext.data.Store({
				// 数据读取器
		reader : new Ext.data.JsonReader({
					totalProperty:'num',// 记录总数
					//idIndex:'blocName', 
					root:'rows'// Json中的列表数据根节点
				}, [{
							name : 't1' // Json中的属性Key值
						}, {
							name : 't2'
						}, {
							name : 't3'
						}
						])
			});

	var  finalgrid = new Ext.grid.GridPanel({
				margins: '0 0 0 0',
				height : 245,
				frame : true,
				autoScroll : true,
				store : finalstore, // 数据存储
				stripeRows : true, // 斑马线
				cm :finalcm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	// 定义自动当前页行号
	var detailedrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	
	// 定义列模型
	var detailedcm = new Ext.grid.ColumnModel([detailedrownum, 
	           {
				header : '客户经理名称', // 列标题
				dataIndex : 'a1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '客户经理编号',
				dataIndex : 'a2',
				sortable : true,
				width : 150
			}, {
				header : '绩效工资',
				dataIndex : 'a3',
				width : 150
			},{
				header : '客户编号',
				dataIndex : 'a4',
				width : 150
			},{
				header : '客户名称',
				dataIndex : 'a5'
			},{
				header : '账户类型',
				dataIndex : 'a6'
			},{
				header : '账户编号',
				dataIndex : 'a7'
			},{
				header : '交易日期',
				dataIndex : 'a8',
				width : 150
			},{
				header : '交易金额',
				dataIndex : 'a9',
				width : 150
			}
			]);
			var detailedstore = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'a1' // Json中的属性Key值
								},{
									name : 'a2'
								},{
									name : 'a3'
								},{
									name : 'a4'
								},{
									name : 'a5'
								},{
									name : 'a6'
								},{
									name : 'a7'
								},{
									name : 'a8'
								},{
									name : 'a9'
								}
								])
			});


	var detailedgrid = new Ext.grid.GridPanel({
				 margins: '0 0 0 0',
				 height :145,
				frame : true,
				autoScroll : true,
				store : detailedstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : detailedcm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	// 定义自动当前页行号
	var achievementrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	
	// 定义列模型
	var achievementcm = new Ext.grid.ColumnModel([achievementrownum, 
	           {
				header : '客户编号', // 列标题
				dataIndex : 'a1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '客户名称',
				dataIndex : 'a2',
				sortable : true,
				width : 150
			}, {
				header : '绩效工资',
				dataIndex : 'a3',
				width : 150
			},{
				header : '客户编号',
				dataIndex : 'a4',
				width : 150
			},{
				header : '客户名称',
				dataIndex : 'a5'
			},{
				header : '账户类型',
				dataIndex : 'a6'
			},{
				header : '账户编号',
				dataIndex : 'a7'
			},{
				header : '账户余额',
				dataIndex : 'a8',
				width : 150
			},{
				header : '交易流水号',
				dataIndex : 'a9',
				width : 150
			},{
				header : '交易日期',
				dataIndex : 'a10',
				width : 150
			},{
				header : '交易金额',
				dataIndex : 'a11',
				width : 150
			}
			]);
			var achievementstore = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'a1' // Json中的属性Key值
								},{
									name : 'a2'
								},{
									name : 'a3'
								},{
									name : 'a4'
								},{
									name : 'a5'
								},{
									name : 'a6'
								},{
									name : 'a7'
								},{
									name : 'a8'
								},{
									name : 'a9'
								},{
									name : 'a10'
								},{
									name : 'a11'
								}
								])
			});


	var achievementgrid = new Ext.grid.GridPanel({
				 //margins: '0 0 0 0',
				 width : 800,
				 height :200,
				frame : true,
				autoScroll : true,
				store : achievementstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : achievementcm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	 var windowForm = new Ext.form.FormPanel({
	   		frame : true, //是否渲染表单面板背景色
	   		//labelAlign : 'left', // 标签对齐方式
	   		//collapsible : true,// 是否可收缩
	   	    width : 540,
	   		buttonAlign : 'right',
	   		height : 100,
	   		items : [{
	   					layout : 'column',
	   					border : false,
	   					items : [{
	   								columnWidth : .50,
	   								layout : 'form',
	   								labelWidth : 90, // 标签宽度
	   								defaultType : 'textfield',
	   								border : false,
	   								items : [{
	   											fieldLabel : '客户经理名称',
	   											name : 'wincn',
	   											xtype : 'textfield', // 设置为数字输入框类型
	   											anchor : '90%'
	   										}]
	   							},{
	   								columnWidth : .50,
	   								layout : 'form',
	   								labelWidth : 90, // 标签宽度
	   								defaultType : 'textfield',
	   								border : false,
	   								items : [{
	   											fieldLabel : '客户经理编号',
	   											name : 'wincn',
	   											xtype : 'textfield', // 设置为数字输入框类型
	   											anchor : '90%'
	   										}]
	   							}]
	   				}],
	   				buttons : [{
	   					text : '查询',
	   					iconCls : 'previewIcon'
	   				}, {
	   					text : '重置',
	   					iconCls : 'tbar_synchronizeIcon'
	   				}]
	   		
	   	}); */
	// 定义自动当前页行号
	var zrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

		// 定义列模型
	var zcm = new Ext.grid.ColumnModel([zrownum, 
	           {
				header : '产品编号', // 列标题
				dataIndex : 'z1', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				align:'right',
				width : 150
		    }, {
				header : '产品名称',
				dataIndex : 'z2',
				align:'right',
				sortable : true,
				width : 150
			}, {
				header : '产品一级分类',
				dataIndex : 'z3',
				align:'right',
				sortable : true,
				width : 150
			},{
				header : '产品二级分类',
				align:'right',
				dataIndex : 'z4',
				width : 150
			}, {
				header : '产品三级分类',
				dataIndex : 'z5',
				align:'right',
				sortable : true,
				width : 150
			},{
				header : '产品四级分类',
				align:'right',
				dataIndex : 'z6',
				width : 150
			},{
				header : '产品状态',
				align:'right',
				dataIndex : 'z7',
				width : 150
			},{
				header : '产品经理',
				align:'right',
				dataIndex : 'z8',
				width : 150
			},{
				header : '产品发布日期',
				align:'right',
				dataIndex : 'z9',
				width : 150
			},{
				header : '产品截止日期',
				align:'right',
				dataIndex : 'z10',
				width : 150
			},{
				header : '产品所属部门',
				align:'right',
				dataIndex : 'z11',
				width : 150
			}
			]);

	/**
	 * 数据存储
	 */
	var zstore = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'z1' // Json中的属性Key值
								}, {
									name : 'z2'
								}, {
									name : 'z3'
								}, {
									name : 'z4'
								}, {
									name : 'z5'
								}, {
									name : 'z6'
								}, {
									name : 'z7'
								}, {
									name : 'z8'
								}, {
									name : 'z9'
								}, {
									name : 'z10'
								}, {
									name : 'z11'
								}
								])
			});

	var zmemberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","z1":"010101","z2":"单位普通活期存款","z3":"负责业务","z4":"一般存款","z5": "", "z6": "","z7": "已发布", "z8": "姚亮", "z9": "2001-03-12", "z10": "2011-03-12"},
			{"rownum":"2","z1":"010102","z2":"单位协定存款"    ,"z3":"负责业务", "z4":"一般存款", "z5": "","z6": "","z7":"已发布", "z8": "陈群", "z9": "2001-03-12", "z10": "2011-03-12"},
			{"rownum":"3","z1":"010103","z2":"单位普通定期存款","z3":"负责业务","z4":"一般存款",  "z5": "","z6": "","z7": "已发布", "z8": "焦向波", "z9": "2001-03-12", "z10": "2011-03-12"},			
			{"rownum":"4","z1":"010104","z2":"单位通知存款"    ,"z3":"负责业务","z4":"一般存款","z5": "","z6": "","z7": "已发布", "z8": "余勇智", "z9": "2001-03-12", "z10": "2011-03-12"}				
			]
		};
	
	zstore.loadData(zmemberData);
// 表格实例
	var zgrid = new Ext.grid.GridPanel({
				height : 300,
				frame : true,
				autoScroll : true,
				store : zstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : zcm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
		var addRoleWindow = new Ext.Window(
			{
				layout : 'auto',
				width : 800,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal">业绩统计明细</span>',
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [
					 /*{
					layout : 'border',
					height:245,
					border : false,
					items : [
					        {
								region:'west',
//								layout : 'form',
								width:250,
//								border : false,
								items : [finalgrid]
							},
							
							{
							region:'center',
							width:640,
						//	layout : 'form',
	//						border : false,
							items : [windowForm,detailedgrid]
							}
							
							]
					},
					{
							//title:'panel3',
						items:[achievementgrid
						       ]
						
					}*/
					 zgrid
							]
			,
				buttons : [
						{
							text : '保存',
							
							handler : function() {
							
								addRoleWindow.hide();
								Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							id : 'btnReset',
							
							handler : function() {
								//clearForm(addRoleFormPanel.getForm());
							}
						}, {
							text : '关闭',
		
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	
	
	
	
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 100,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '机构号',
											name : 'w1',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
										},{
											fieldLabel : '业务类型',
											name : 'w2',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '统计日期', // 标签
											id : 'w3',
											name : 'w3', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										},{
											fieldLabel : '客户名称', // 标签
											id : 'w4',
											name : 'w4', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户经理ID', // 标签
											name : 'w5', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										},{
											fieldLabel : '组织机构代码', // 标签
											name : 'w6', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户经理姓名',
											name : 'w7',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											maxValue : 120,
											anchor : '80%'
										}]
								}]
				}],
		buttons : [{
					text : '查询'
					/*handler : function() {
						queryBalanceInfo(qForm.getForm());
					}*/
				}, {
					text : '重置'
					/*handler : function() {
						qForm.getForm().reset();
					}*/
				}]
	});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	           {
				header : '机构号', // 列标题
				dataIndex : 'a1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '统计日期',
				dataIndex : 'a2',
				sortable : true,
				width : 150
			}, {
				header : '客户经理ID',
				dataIndex : 'a3',
				width : 150
			},{
				header : '客户经理姓名',
				align:'right',
				dataIndex : 'a4'
			},{
				header : '业务类型',
				align:'right',
				dataIndex : 'a5'
			},{
				header : '客户名称',
				align:'right',
				dataIndex : 'a6'
			},{
				header : '组织机构代码',
				dataIndex : 'a7',
				width : 150
			},{
				header : '账号',
				dataIndex : 'a8'
			},{
				header : '关联占比',
				dataIndex : 'a9'
			},{
				header : '关联日期',
				dataIndex : 'a10'
			},{
				header : '时点余额',
				dataIndex : 'a11',
				width : 150
			},{
				header : '月均余额',
				dataIndex : 'a12',
				width : 150
			},{
				header : '季均余额',
				dataIndex : 'a13'
			},{
				header : '年均余额',
				dataIndex : 'a14'
			},{
				header : '年末11天日均余额',
				dataIndex : 'a15'
			}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'a1' // Json中的属性Key值
								}, {
									name : 'a2'
								}, {
									name : 'a3'
								}, {
									name : 'a4'
								}, {
									name : 'a5'
								}, {
									name : 'a6'
								}, {
									name : 'a7'
								}, {
									name : 'a8'
								}, {
									name : 'a9'
								}, {
									name : 'a10'
								}, {
									name : 'a11'
								}, {
									name : 'a12'
								}, {
									name : 'a13'
								}, {
									name : 'a14'
								}, {
									name : 'a15'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","a1":"陈群",  "a2":"0001001","a3":"010-2114272","a11":"4.00万元","a12":"4.00万元","a13":"5.00万元","a4":"北京银行东单支行","a5":"本科","a6":"2001-06-30","a7":"9年","a8":"5年" ,"a9":"2年","a10":"2级"},
			{"rownum":"2","a1":"焦向波","a2":"0105281","a3":"13900163745","a11":"4.00万元","a12":"10.00万元级","a13":"4.00万元","a4":"北京银行红星支行","a5":"硕士","a6":"2004-06-30","a7":"7年" ,"a8":"2年","a9":"1年","a10":"2级"},
			{"rownum":"3","a1":"姚亮",  "a2":"0022301","a3":"1582993942" ,"a11":"4.00万元","a12":"10.00万元级","a13":"2.00万元","a4":"北京银行东四支行","a5":"本科","a6":"2004-06-30","a7":"7年" ,"a8":"3年","a9":"1年","a10":"3级"},			
			{"rownum":"4","a1":"余永智","a2":"0012343","a3":"020-3452354","a11":"4.00万元","a12":"1.00万元级","a13":"5.00万元","a4":"北京银行上地支行","a5":"硕士","a6":"2001-06-30","a7":"9年","a8":"9年" ,"a9":"6年","a10":"1级"}				
			]
		};
	debugger;
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '业绩统计明细',
					iconCls : 'page_addIcon',
					handler : function() {
						addRoleWindow.show();
					}
				}
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height :370,
				
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户经理管理->客户经理业绩统计", 
				    height: 128,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 

			});
}); 