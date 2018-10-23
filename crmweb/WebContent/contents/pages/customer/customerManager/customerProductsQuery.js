/**
 * 功能：客户产品查询
 */

Ext.onReady(function() {
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['正式', '0001'], ['注销', '0002'], ['全部', '0003']]
			});
	var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中型', '0002'], ['小型', '0003']]
			});
	var boxstore4 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var boxstore3 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});
	var boxstore5 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000']]
			});
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 140,
		items : [{
				layout : 'column',
				border : false,
				items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '机构号',
									name : 'q1',
									xtype : 'textfield', // 设置为数字输入框类型
									 labelStyle: 'text-align:right;',
									anchor : '90%'
								},{
									fieldLabel : '组织机构代码',
									name : 'q2',
									xtype : 'textfield', // 设置为数字输入框类型
									 labelStyle: 'text-align:right;',
									anchor : '90%'
								},{
									fieldLabel : '客户级别', // 标签
									name : 'q3', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '机构层次', // 标签
									name : 'q4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},new Ext.form.ComboBox({
									id : 'q5',
									hiddenName : 'area1',
									fieldLabel : '客户状态',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									id : 'q6',
									hiddenName : 'area1',
									fieldLabel : '考核口径客户规模',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore3,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								})]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '统计日期', // 标签
									name : 'q7', // name:后台根据此name属性取值
									xtype:'datefield',
									labelStyle: 'text-align:right;',
									allowBlank : true,
									anchor : '90%'// 宽度百分比
								},new Ext.form.ComboBox({
									id : 'q8',
									hiddenName : 'area1',
									fieldLabel : '客户营销类型',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore4,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									id : 'q9',
									hiddenName : 'area1',
									fieldLabel : '行业大类',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore5,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								})]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
							fieldLabel : '客户名称',
							name : 'q10',
							xtype : 'numberfield', // 设置为数字输入框类型
							 labelStyle: 'text-align:right;',
							maxValue : 120,
							anchor : '90%'
						},new Ext.form.ComboBox({
							id : 'q11',
							hiddenName : 'area1',
							fieldLabel : '客户规模',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore2,
							displayField : 'name',
							valueField : 'code',
							mode : 'local',
							listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
							forceSelection : true,
							typeAhead : true,
							value : '0000',
							resizable : true,
							anchor : '90%'
						}),new Ext.form.ComboBox({
							id : 'q12',
							hiddenName : 'area1',
							fieldLabel : '行业小类',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore5,
							displayField : 'name',
							valueField : 'code',
							mode : 'local',
							listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
							forceSelection : true,
							typeAhead : true,
							value : '0000',
							resizable : true,
							anchor : '90%'
						})]
					}]
		}],
	buttons : [{
				text : '查询'
				/*handler : function() {
				}*/
			}, {
				text : '重置'
				/*handler : function() {
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
				header : '统计日期', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    },{
				header : '机构号',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			},{
				header : '机构名称',
				dataIndex : 'c3'
			},{
				header : '组织机构代码',
				dataIndex : 'c4'
			},{
				header : '产品编号',
				dataIndex : 'c5'
			},{
				header : '产品名称',
				dataIndex : 'c6'
			},{
				header : '产品1级分类',
				dataIndex : 'c7'
			},{
				header : '产品2级分类',
				dataIndex : 'c8'
			},{
				header : '产品3级分类',
				dataIndex : 'c9'
			},{
				header : '产品4级分类',
				dataIndex : 'c10'
			},{
				header : '时点余额',
				dataIndex : 'c11'
			},{
				header : '年均余额',
				dataIndex : 'c12'
			},{
				header : '销售金额',
				dataIndex : 'c13'
			},{
				header : '利息收入',
				dataIndex : 'c14'
			},{
				header : '利息支出',
				dataIndex : 'c15'
			},{
				header : '费用收入',
				dataIndex : 'c16'
			}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 获取数据的方式
				//proxy : new Ext.data.HttpProxy({
						//	url : 'gridDemo.ered?reqCode=querySfxmDatas'
						//}),
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'c1' // Json中的属性Key值
								}, {
									name : 'c2'
								}, {
									name : 'c3'
								}, {
									name : 'c4'
								}, {
									name : 'c5'
								}, {
									name : 'c6'
								}, {
									name : 'c7'
								}, {
									name : 'c8'
								}, {
									name : 'c9'
								}, {
									name : 'c10'
								}, {
									name : 'c11'
								}, {
									name : 'c12'
								}, {
									name : 'c13'
								}, {
									name : 'c14'
								}, {
									name : 'c15'
								}, {
									name : 'c16'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","c1":"天津市日昊昌盛钢铁贸易有限公司","c2":"67235353","c3":"集团客户","c4":"1"},
			{"rownum":"2","c1":"天地人传媒有限公司","c2":"67002938","c3":"集团客户","c4":"2"},
			{"rownum":"3","c1":"西尼亚（北京）环境科技有限公司","c2":"6827365423","c3":"潜在客户","c4":"3"},			
			{"rownum":"4","c1":"威克瑞电线电缆有司电缆销售分司","c2":"68263810","c3":"普特客户","c4":"4"}				
			]
		};
	store.loadData(memberData);
		var sm2 = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum2 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm2 = new Ext.grid.ColumnModel([rownum2,sm2, 
	           {
				header : '统计日期', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    },{
				header : '机构号',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			},{
				header : '机构名称',
				dataIndex : 'c3'
			},{
				header : '客户名称',
				dataIndex : 'c4'
			},{
				header : '组织机构代码',
				dataIndex : 'c5'
			},{
				header : '账号',
				dataIndex : 'c6'
			},{
				header : '账户名称',
				dataIndex : 'c7'
			},{
				header : '产品编号',
				dataIndex : 'c8'
			},{
				header : '产品名称',
				dataIndex : 'c9'
			},{
				header : '时点余额',
				dataIndex : 'c10'
			},{
				header : '年均余额',
				dataIndex : 'c11'
			},{
				header : '本年积数',
				dataIndex : 'c12'
			},{
				header : '累计费用收入',
				dataIndex : 'c13'
			},{
				header : 'FTP',
				dataIndex : 'c14'
			},{
				header : '利率',
				dataIndex : 'c15'
			},{
				header : '费率',
				dataIndex : 'c16'
			},{
				header : '期限',
				dataIndex : 'c17'
			},{
				header : '经济资本占用',
				dataIndex : 'c16'
			},{
				header : '费用成本',
				dataIndex : 'c18'
			},{
				header : '币种',
				dataIndex : 'c19'
			},{
				header : '汇率中间价',
				dataIndex : 'c20'
			},{
				header : '综合贡献度A',
				dataIndex : 'c21'
			},{
				header : '综合贡献度B',
				dataIndex : 'c22'
			}
			]);

	/**
	 * 数据存储
	 */
	var store2 = new Ext.data.Store({
				// 获取数据的方式
				//proxy : new Ext.data.HttpProxy({
						//	url : 'gridDemo.ered?reqCode=querySfxmDatas'
						//}),
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'c1' // Json中的属性Key值
								}, {
									name : 'c2'
								}, {
									name : 'c3'
								}, {
									name : 'c4'
								}, {
									name : 'c5'
								}, {
									name : 'c6'
								}, {
									name : 'c7'
								}, {
									name : 'c8'
								}, {
									name : 'c9'
								}, {
									name : 'c10'
								}, {
									name : 'c11'
								}, {
									name : 'c12'
								}, {
									name : 'c13'
								}, {
									name : 'c14'
								}, {
									name : 'c15'
								}, {
									name : 'c16'
								}, {
									name : 'c17'
								}, {
									name : 'c18'
								}, {
									name : 'c19'
								}, {
									name : 'c20'
								}, {
									name : 'c21'
								}, {
									name : 'c22'
								}
								])
			});
	
	var memberData2= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","c1":"天津市日昊昌盛钢铁贸易有限公司","c2":"67235353","c3":"集团客户","c4":"1"},
			{"rownum":"2","c1":"天地人传媒有限公司","c2":"67002938","c3":"集团客户","c4":"2"},
			{"rownum":"3","c1":"西尼亚（北京）环境科技有限公司","c2":"6827365423","c3":"潜在客户","c4":"3"},			
			{"rownum":"4","c1":"威克瑞电线电缆有司电缆销售分司","c2":"68263810","c3":"普特客户","c4":"4"}				
			]
		};
	store2.loadData(memberData2);



	// 表格实例
	var grid2 = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal"></span>',
				height : 415,
				frame : true,
				autoScroll : true,
				region : 'center', // 返回给页面的div
				store : store2, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm2, // 列模型
				sm : sm2, // 复选框
				//tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var addRoleWindow = new Ext.Window(
	{
		//layout : 'fit',
		width : 1000,
		height : 450,
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		// iconCls : 'page_addIcon',
		//maximizable: true,
		//maximized:true,
		collapsible : true,// 是否可收缩
		titleCollapse : true,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		pageY : 20,
		//pageX : document.body.clientWidth / 2 - 420 / 2,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [grid2]
	});

	 //表格工具栏
	var tbar = new Ext.Toolbar({
			items : [{
			text : '客户产品明显',
			handler : function() {
					 window.location.href = 'customerBaseInformation.html' ;
					 tp.setActiveTab(6);
				}},{
			text : '客户产品收入明细',
			handler : function() {
				addRoleWindow.show();
				}
				}
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal"></span>',
				height : 340,
				frame : true,
				autoScroll : true,
				region : 'center', // 返回给页面的div
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
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
				    title: "客户管理->客户产品查询", 
				    height: 160,
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

}) ;