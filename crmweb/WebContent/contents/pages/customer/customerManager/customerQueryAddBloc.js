
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['全行型', '0001'], ['区域型', '0002']]
	});
	
	var windowForm = new Ext.form.FormPanel({
		title : '<span style="font-weight:normal">新增集团</span>',
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			 style:'padding: 10 10 10 10',
			height : 150,
			items : [{
						
									layout : 'form',
									labelWidth : 80, // 标签宽度
									defaultType : 'textfield',
									border : false,
									items : [{
												fieldLabel : '集团中文全称',
												name : 'q1',
												value:'北京国安信息科技股份有限公司',
												 disabled : true,
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											}, {
												fieldLabel : '集团英文全称',
												name : 'q2',
												xtype : 'numberfield', // 设置为数字输入框类型
												anchor : '80%'
											},{
												fieldLabel : '集团简称',
												name : 'q3',
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											},{
												fieldLabel : '集团编号',
												disabled : true,
												value:'0001',
												name : 'q4',
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											},{
												fieldLabel : '集团类型',
												name : 'q5',
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											},{
												fieldLabel : '主办机构',
												name : 'q6',
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											},{
												fieldLabel : '协办机构',
												name : 'q7',
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											},{
												fieldLabel : '集团母公司',
												name : 'q8',
												xtype : 'textfield', // 设置为数字输入框类型
												anchor : '80%'
											}
											]
					}]

		});
		
		var addRoleWindow = new Ext.Window(
				{
					layout : 'fit',
					width : 400,
					height : 400,
					//resizable : false,//是否允许缩放
					draggable : true,//是否可以拖动
					closable : true,// 是否可关闭
					modal : true,
					closeAction : 'hide',
					title : '<span style="font-weight:normal"></span>',
					// iconCls : 'page_addIcon',
					//maximizable: true,
					//maximized:true,
					collapsible : true,// 是否可收缩
					titleCollapse : true,
					buttonAlign : 'center',
					border : false,
					animCollapse : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [windowForm],
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
		region:'north',
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
											fieldLabel : '集团客户名称',
											name : 'gcn',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										}, new Ext.form.ComboBox({
											id : 'id_area1',
											hiddenName : 'area1',
											fieldLabel : '集团类型',
											triggerAction : 'all',
											store : boxstore,
											displayField : 'name',
											valueField : 'code',
											labelStyle: 'text-align:right;',
											mode : 'local',
											//listWidth : 120, // 下拉列表的宽度,默认为下拉选择框的宽度
											forceSelection : true,
											typeAhead : true,
											value : '0000',
											resizable : true,
											anchor : '90%'
										})]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '主办机构', // 标签
											id : 'groupCorporation',
											name : 'groupCorporation', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											anchor : '90%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '创建日期', // 标签
											name : 'organizer', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '90%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '创建人', // 标签
									id : 'founder',
									name : 'founder', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
							}]
				}],
		buttonAlign:'center',
		buttons : [{
					text : '查询'
					/*handler : function() {
					}*/
				}
//				, 
//				{
//					text : '重置'
//					/*handler : function() {
//					}*/
//				}
				]
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
				header : '集团中文全称', // 列标题
				dataIndex : 'g1', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团英文全称', // 列标题
				dataIndex : 'g2', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团简称', // 列标题
				dataIndex : 'g3', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团编号', // 列标题
				dataIndex : 'g4', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团类型', // 列标题
				dataIndex : 'g5', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '主办机构',
				dataIndex : 'g6',
				sortable : true,
				width : 150
			},{
				header : '协办机构',
				dataIndex : 'g7'
			}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'g1' // Json中的属性Key值
								},{
									name : 'g2'
								},{
									name : 'g3'
								},{
									name : 'g4'
								},{
									name : 'g5'
								},{
									name : 'g6'
								},{
									name : 'g7'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","g1":"北京国安信息科技股份有限公司","g2":"CITIC Guoan Information Industry Co.,Ltd.","g3":"国安信息","g4":"2342342","g5":"全行型","g6":"北京银行上地支行","g7":"北京银行东单支行"},
			{"rownum":"2","g1":"北京鸿联九五信息产业有限公司","g2":"CITIC Guoan Information Industry Co.,Ltd.","g3":"鸿联九五","g4":"3463634","g5":"全行型","g6":"北京银行东单支行","g7":"北京银行东单支行"},
			{"rownum":"3","g1":"北京国安通信有限公司"       ,"g2":"CITIC Guoan Information Industry Co.,Ltd.","g3":"国安通信","g4":"73542543","g5":"区域型","g6":"北京银行东单支行","g7":"北京银行上地支行"},			
			{"rownum":"4","g1":"华夏基金管理有限公司"       ,"g2":"China Asset Management Co.,Ltd."          ,"g3":"华夏基金","g4":"37356455","g5":"区域型","g6":"北京银行天津分行","g7":"北京银行天津分行和平支行"}				
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [
					{
					text : '新增',
					iconCls : 'page_addIcon',
					handler : function() {
						editInit();
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}},{
					text : '修改',
					handler : function() {editInit();
						
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}},
					{
					text : '删除',
					handler : function() {
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}
					},
					{
					text : '集团视图',
					handler : function() {
						
						window.location.href = 'groupView.html' ;
					}
				}
					
				
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				frame : true,
				title:'集团列表',
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
//				tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		 window.location.href = 'groupView.html' ;
	});


	// 布局模型
	var customerQueryAddBlocWind = new Ext.Window({
				layout : 'border',
				closeAction:'hide',
				width:1200,
				height:500,
				constrain:true,
//				maximized:true,
				maximizable:true,
				modal:true,
				items: [   
						qForm,
				  		grid
			    ] ,
			    buttonAlign:'center',
			    buttons:[
			    {
			    	text:'加入该集团',
			    	handler:function()
			    	{
			    		customerQueryAddBlocWind.hide();
			    	}
			    },
			    {
			    	text:'返回',
			    	handler:function()
			    	{
			    		customerQueryAddBlocWind.hide();
			    	}
			    }
			    ]

			});
			
	function editInit(){
		addRoleWindow.show();
		
	};

	/*// 获取选择行
	function getCheckboxValues() {
		// 返回一个行集合JS数组
		var rows = grid.getSelectionModel().getSelections();
		if (Ext.isEmpty(rows)) {
			Ext.MessageBox.alert('提示', '您没有选中任何数据!');
			return;
		}
		// 将JS数组中的行级主键，生成以,分隔的字符串
		var strChecked = jsArray2JsString(rows, 'xmid');
		Ext.MessageBox.alert('提示', strChecked);
		// 获得选中数据后则可以传入后台继续处理
	}*/
