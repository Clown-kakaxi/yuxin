Ext.onReady(function() {
	var windowForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelWidth:100,
		labelAlign:'right',
		items : [{
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								defaultType : 'textfield',
								items : [{
											fieldLabel : '提醒类别',
											xtype : 'combo', // 设置为数字输入框类型
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'大额变动提醒'],[1,'待办工作提醒'],[2,'贷款到期提醒'],[3,'定期存款到期提醒'],
												[4,'生日提醒']]
											}),
											valueField:'myId',
											displayField:'displayText',
											anchor : '90%'
										},{
											fieldLabel : '阀值',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '90%'
										},
										{
												fieldLabel : '提醒提前天数', // 标签
												//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
												anchor : '90%' // 宽度百分比
										}										
										]
							}, 

							{
								columnWidth : .5,
								layout : 'form',
								defaultType : 'textfield',
								items : [
									{
											fieldLabel : '提醒方式', // 标签
											allowBlank : true,
											xtype:'combo',
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'站内'],[1,'短信'],[2,'邮件']]
											}),
											valueField:'myId',
											displayField:'displayText',
											
											anchor : '90%'// 宽度百分比
									},
										{
											fieldLabel : '提醒时间', // 标签
											xtype:'datefield',
											anchor : '90%' // 宽度百分比
										}									
								]
							}

							]
				}]
	});
	
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 600,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '新增规则',
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
				//listeners: {'hide':{fn: makesure}},
				buttons : [
						{
							text : '保存',
							
							handler : function() {
							
								addRoleWindow.hide();
								Ext.MessageBox.alert('提示', "保存成功!");
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
					items : [
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [
										{
											fieldLabel : '提醒类别',
											xtype : 'combo', // 设置为数字输入框类型
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'大额变动提醒'],[1,'待办工作提醒'],[2,'贷款到期提醒'],[3,'定期存款到期提醒'],
												[4,'生日提醒']]
											}),
											valueField:'myId',
											displayField:'displayText',
											anchor : '90%'
										},
										{
											fieldLabel : '提醒提前天数',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '90%'
										}										
										]
							},
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [
										{
											fieldLabel : '阀值', // 标签
											anchor : '90%' // 宽度百分比
										},								
										{
											fieldLabel : '提醒时间', // 标签
											xtype:'datefield',
											format:'Y-m-d',
											anchor : '90%' // 宽度百分比
										}
										]
							},
							{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [
										{
											xtype:'textfield',
											fieldLabel : '提醒方式', // 标签
											anchor : '90%' // 宽度百分比
										},								
										{
											fieldLabel : '创建人', // 标签
											xtype:'textfield',
											allowBlank : true, // 是否允许为空
											anchor : '90%' // 宽度百分比
										}
										]
							},
							{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								border : false,
								items : [
										{
											fieldLabel : '创建机构', // 标签
											allowBlank : true, // 是否允许为空
											anchor : '90%' // 宽度百分比
										},								
										{
											fieldLabel : '创建时间', // 标签
											xtype:'datefield',
											format:'Y-m-d',
											anchor : '90%' // 宽度百分比
										}
										]
							}							
							]
				}],
		buttonAlign:'center',
		buttons : [{
					text : '查询'
				}, {
					text : '重置'
				}]
	});
	 //复选框
	
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : '序号',
				width : 40
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([
			rownum,
	           {
				header : '提醒类别', // 列标题
				dataIndex : 'a1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '提醒提前天数',
				dataIndex : 'a2'
			}, {
				header : '提醒时间',
				dataIndex : 'a3',
				width : 150
			},
			{
				header : '阀值',
				dataIndex : 'a4'
			},
			{
				header : '提醒方式',
				dataIndex : 'a5'
			},
			{
				header : '创建人',
				dataIndex : 'a6'
			},
			{
				header : '创建机构',
				dataIndex : 'a7'
			},
			{
				header : '创建时间',
				dataIndex : 'a8'
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
						}, [
								{
									name : 'a1' // Json中的属性Key值
								}, 
								{
									name : 'a2'
								}, 
								{
									name : 'a3'
								}, 
								{
									name : 'a4'
								},
								{
									name : 'a5'
								},
								{
									name: 'a6'
								},
								{
									name:'a7'
								},
								{
									name:'a8'
								}
								]
							)
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"a1":"大额变动提醒","a2":"2","a3":"2011-06-20","a4":"1,000,000.00","a5":"站内","a6":"客户经理","a7":"中关村管理部","a8":"2011-05-02" ,"a9":"2年","a10":"2级","a11":"50.00万元","a12":"60.00万元","a13":"10.00万元"},
//			{"a1":"生日提醒","a2":"2","a3":"2011-06-20","a4":"","a5":"站内","a6":"客户经理","a7":"中关村管理部" ,"a8":"2011-05-02","a9":"1年","a10":"2级","a11":"50.00万元","a12":"40.00万元","a13":"10.00万元"},
			{"a1":"贷款到期提醒",  "a2":"2","a3":"2011-06-20" ,"a4":"","a5":"站内","a6":"客户经理","a7":"中关村管理部" ,"a8":"2011-05-02","a9":"1年","a10":"3级","a11":"40.00万元","a12":"40.00万元","a13":"10.00万元"}			
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
		items : [
			{
				text : '详细信息',
				iconCls : 'page_addIcon',
				handler : function() {
					addRoleWindow.show();
				}
			},
			{
				text : '新增规则',
				iconCls : 'page_addIcon',
				handler : function() {
					addRoleWindow.show();
				}
			},
			{
				text : '修改规则',
				iconCls : 'page_addIcon',
				handler : function() {
					addRoleWindow.setTitle('修改规则'),
					addRoleWindow.show();
				}
			}			
		]
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
				    title: "信息提醒规则", 
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