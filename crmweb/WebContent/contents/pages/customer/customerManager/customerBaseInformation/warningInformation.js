Ext.onReady(function() {
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">客户预警详细信息</span>',
		width: '100%',
	    height:300,
		items: [{
		    autoHeight:true,
			items :[{ layout:'column',
				buttonAlign : 'center',
					 items:[{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '警示名称',
							 value :'李强',
							  labelStyle: 'text-align:right;',
							 name: 'first',
							 anchor:'90%'
						 }, {
							 xtype:'textfield',
							 fieldLabel: '处理状态',
							 name: 'state',
							  labelStyle: 'text-align:right;',
							 value :'总经理',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							  fieldLabel: '警示信息内容',
							   labelStyle: 'text-align:right;',
							 name: 'orgid',
							 value :'tom',
							 anchor:'90%'
						 },{
							 xtype:'textfield',
							 fieldLabel: '处理结果',
							 name: 'baseid',
							 value :'6年',
							  labelStyle: 'text-align:right;',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '警示类型',
							 value :'是',
							  labelStyle: 'text-align:right;',
							 name: 'lendid',
							 anchor:'90%'
						 }]
					 },{
						 columnWidth:.25,
						 layout: 'form',
						 items: [{
							 xtype:'textfield',
							 fieldLabel: '发生日期',
							 value :'是',
							  labelStyle: 'text-align:right;',
							 name: 'lendid',
							 anchor:'90%'
						 }]
					 }
				],
				buttons : [{
					text : '保存'
				}]}
				]}]
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
		items : [panel2]
	});
	var qForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
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
											fieldLabel : '警示名称',
											name : 'cn',
											 labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '警示类型', // 标签
											id : 'ct',
											 labelStyle: 'text-align:right;',
											name : 'ct', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
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
											fieldLabel : '处理状态', // 标签
											name : 'c', // name:后台根据此name属性取值
											 labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
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
				header : '警示名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '警示信息内容',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 150
			}, {
				header : '警示类型',
				dataIndex : 'customertype'
			}, {
				header : '发生日期',
				dataIndex : 'customerlevel'
			}, {
				header : '处理状态',
				dataIndex : 'w1'
			}, {
				header : '处理结果',
				dataIndex : 'w2'
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
									name : 'customername' // Json中的属性Key值
								}, {
									name : 'organizationcode'
								}, {
									name : 'customertype'
								}, {
									name : 'customerlevel'
								}, {
									name : 'w1'
								}, {
									name : 'w2'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"北京国安信息科技股份有限公司","organizationcode":"101","customertype":"集团客户","customerlevel":"1"},
			{"rownum":"2","customername":"北京鸿联九五信息产业有限公司","organizationcode":"102","customertype":"集团客户","customerlevel":"2"},
			{"rownum":"3","customername":"北京国安通信有限公司","organizationcode":"103","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"华夏基金管理有限公司","organizationcode":"104","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	store.loadData(memberData);
	
// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '修改',
					
					handler : function() {
						editInit();
			
					}
				}]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height : 500,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
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
				    title: "客户统一视图->客户预警信息", 
				    height: 128,
				    hidden:false,
				    margins: '0 0 0 0',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 

			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	function editInit(){
		addRoleWindow.show();
	};

}); 