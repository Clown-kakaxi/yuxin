Ext.onReady(function() {
	/* //复选框
	var windowsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var windowrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var windowcm = new Ext.grid.ColumnModel([windowrownum,windowsm, 
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

	*//**
	 * 数据存储
	 *//*
	var windowstore = new Ext.data.Store({
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
								}
								])
			});
	
	var windowmemberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"英联国际化学品（北京）有限公司","organizationcode":"101","customertype":"集团客户","customerlevel":"1"},
			{"rownum":"2","customername":"中融润华国际投资(北京)有限公司","organizationcode":"102","customertype":"集团客户","customerlevel":"2"},
			{"rownum":"3","customername":"未来科讯信息技术(北京)有限公司","organizationcode":"103","customertype":"集团客户","customerlevel":"3"},			
			{"rownum":"4","customername":"世铁比亚(北京)国际贸易有限公司","organizationcode":"104","customertype":"集团客户","customerlevel":"4"}				
			]
		};
	windowstore.loadData(windowmemberData);
	
	
	// 表格实例
	var windowgrid = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal"></span>',
				//height : 500,
				frame : true,
				autoScroll : true,
				//region : 'center', // 返回给页面的div
				store : windowstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : windowcm, // 列模型
				sm : windowsm, // 复选框
				//tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
				
			});
	//windowgrid.getView().getRow(1).style.backgroundColor='#FF0000'; 
	debugger;
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 400,
				height : 300,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal">客户分配</span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				//pageY : 20,
				//pageX : document.body.clientWidth / 2 - 420 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [windowgrid],
				//listeners: {'hide':{fn: makesure}},
				buttons : [
						{
							text : '保存',
							iconCls : 'acceptIcon',
							handler : function() {
							
								addRoleWindow.hide();
								Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							id : 'btnReset',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
								//clearForm(addRoleFormPanel.getForm());
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});*/
	
	var qForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 120,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户号',
											name : 'gcn',
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}, {
											fieldLabel : '成员名称',
											name : 'groupType',
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											maxValue : 120,
											anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户名称', // 标签
											id : 'groupCorporation',
											labelStyle: 'text-align:right;',
											name : 'groupCorporation', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										},{
											fieldLabel : '成员类型', // 标签
											id : 'founder',
											labelStyle: 'text-align:right;',
											name : 'founder', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '营销团队名称', // 标签
											name : 'organizer', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '成员ID号',
									name : 'update',
									labelStyle: 'text-align:right;',
									xtype : 'numberfield', // 设置为数字输入框类型
									maxValue : 120,
									anchor : '80%'
								}]
							}]
				}],
		buttons : [{
					text : '查询',
					iconCls : 'previewIcon'
					/*handler : function() {
					}*/
				}, {
					text : '重置',
					iconCls : 'tbar_synchronizeIcon'
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
				header : '客户号', // 列标题
				dataIndex : 'gcn', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '客户名称',
				dataIndex : 'groupCorporation',
				sortable : true,
				width : 150
			},{
				header : '营销团队名称',
				dataIndex : 'adscription'
			},{
				header : '成员ID号',
				dataIndex : 'organizer'
			},{
				header : '成员名称',
				dataIndex : 'groupType'
			},{
				header : '成员类型',
				dataIndex : 'founder'
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
									name : 'gcn' // Json中的属性Key值
								},{
									name : 'groupCorporation'
								},{
									name : 'adscription'
								},{
									name : 'organizer'
								},{
									name : 'groupType'
								},{
									name : 'founder'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
					{"rownum":"1","gcn":"12323","groupCorporation":"北京国安信息产业股份有限公司","adscription":"北京银行上地支行团队1","organizer":"12323","groupType":"焦向波","founder":""},
					{"rownum":"2","gcn":"23413","groupCorporation":"北京国安信息产业股份有限公司","adscription":"北京银行上地支行团队2","organizer":"12341","groupType":"焦向波","founder":""},
					{"rownum":"3","gcn":"23453","groupCorporation":"北京国安信息产业股份有限公司","adscription":"北京银行上地支行团队3","organizer":"23411","groupType":"焦向波","founder":""},			
					{"rownum":"4","gcn":"23452","groupCorporation":"北京证券"                   ,"adscription":"北京银行上地支行团队4","organizer":"23423","groupType":"焦向波","founder":""}				
					]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '团队新增成员',
					iconCls : 'page_addIcon',
					handler : function() {
						editInit();
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}}
					/*,{
					text : '修改',
					iconCls : 'page_addIcon',
					handler : function() {
						editInit();
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}},{
					text : '删除',
					iconCls : 'page_addIcon',
					handler : function() {
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}
				}*/
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
				    title: "客户经理管理->营销团队成员维护", 
				    height: 148,
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


}); 