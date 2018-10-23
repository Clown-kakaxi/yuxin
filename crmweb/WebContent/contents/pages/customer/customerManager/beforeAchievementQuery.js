Ext.onReady(function() {
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['正式', '0001'], ['注销', '0002'], ['全部', '0003']]
			});
	var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中型', '0002'], ['小型', '0003']]
			});
	var boxstore3 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});
	var boxstore4 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var boxstore5 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['客户群1', '0001'], ['客户群2', '0002'], ['客户群3', '0003'], ['客户群4', '0004']]
			});
	var boxstore6 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['名单1', '0001'], ['名单2', '0002'], ['名单3', '0003'], ['名单4', '0004']]
			});
	var boxstore7 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 120,
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
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},new Ext.form.ComboBox({
									id : 'c2',
									hiddenName : 'area1',
									fieldLabel : '客户状态',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									//listWidth : 140, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									id : 'c1112',
									hiddenName : 'area1',
									fieldLabel : '客户级别',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore7,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									//listWidth : 140, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								})
							]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '统计日期', // 标签
									 xtype:'datefield',
									name : 'c4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},new Ext.form.ComboBox({
									id : 'c5',
									hiddenName : 'area1',
									fieldLabel : '客户规模',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore2,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									//listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									id : 'c3',
									hiddenName : 'area1',
									fieldLabel : '客户群组',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore5,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									//listWidth : 140, // 下拉列表的宽度,默认为下拉选择框的宽度
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
									fieldLabel : '客户名称', // 标签
									name : 'c7', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},new Ext.form.ComboBox({
									id : 'c8',
									hiddenName : 'area1',
									fieldLabel : '考核口径客户规模',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore3,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									//listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									id : 'c6',
									hiddenName : 'area1',
									fieldLabel : '客户名单',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore6,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									//listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
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
									fieldLabel : '组织机构代码', // 标签
									name : 'c9', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '账户', // 标签
									name : 'c9', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
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
				header : '客户名称', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 200
				// 是否可排序
		    }, {
				header : '组织机构代码',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			}, {
				header : '机构号',
				dataIndex : 'c3',
				sortable : true,
				width : 150
			}, {
				header : '统计日期',
				dataIndex : 'c4',
				sortable : true,
				width : 150
			}, {
				header : '客户状态',
				dataIndex : 'c5',
				sortable : true,
				width : 150
			}, {
				header : '客户规模',
				dataIndex : 'c6',
				sortable : true,
				width : 150
			}, {
				header : '账户',
				dataIndex : 'c7',
				sortable : true,
				width : 150
			}, {
				header : '考核口径客户规模',
				dataIndex : 'c8',
				sortable : true,
				width : 150
			}, {
				header : '客户级别',
				dataIndex : 'c9',
				sortable : true,
				width : 150
			}, {
				header : '客户群组',
				dataIndex : 'c10',
				sortable : true,
				width : 150
			}, {
				header : '客户名单',
				dataIndex : 'c11',
				sortable : true,
				width : 150
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
								},{
									name : 'c2'
								},{
									name : 'c3'
								},{
									name : 'c4'
								},{
									name : 'c5'
								},{
									name : 'c6'
								},{
									name : 'c7'
								},{
									name : 'c8'
								},{
									name : 'c9'
								},{
									name : 'c10'
								},{
									name : 'c11'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","c1":"天津市日昊昌盛钢铁贸易有限公司","c2":"56010369-1","c3":"100231","c4":"2011-01-31","c5":"正式","c6":"大型"},
			{"rownum":"2","c1":"天地人传媒有限公司"           ,"c2":"56020136-1","c3":"100753","c4":"2010-04-13","c5":"正式","c6":"中型"},
			{"rownum":"3","c1":"西尼亚（北京）环境科技有限公司","c2":"56010821-1","c3":"200851","c4":"2009-10-23","c5":"正式","c6":"中型"},			
			{"rownum":"4","c1":"威克瑞电线电缆有司电缆销售分司","c2":"56020622-3","c3":"200714","c4":"2011-04-12","c5":"正式","c6":"中型"}				
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '分配',
					handler : function() {
					 window.location.href = '../customerDistribution/customerDistribution.html' ;
					}
				},'-',{
					text : '导出',
					handler : function() {
					}
				}
				]
			});
	// 表格实例
	var grid = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal"></span>',
				height : 360,
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
    grid.on('rowdblclick', function(grid, rowIndex, event) {
		 window.location.href = '../customerDistribution/customerDistribution.html';
	});

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户管理->客户分配", 
				    height: 140,
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