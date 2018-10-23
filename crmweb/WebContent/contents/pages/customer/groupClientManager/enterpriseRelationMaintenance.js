Ext.onReady(function() {
	var windowForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'left', // 标签对齐方式
		collapsible : true,// 是否可收缩
		buttonAlign : 'center',
		height : 150,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								//columnWidth : .100,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户名称',
											name : 'wincn',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '100%'
										}]
							}]
				}],
				buttons : [{
					text : '查询'
				}, {
					text : '重置'
				}]
	});
	// 定义自动当前页行号
	var windowrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var windowcm = new Ext.grid.ColumnModel([windowrownum,
	           {
				header : '客户中文名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 100
		    }, {
				header : '组织机构代码',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 100
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
	var windowstore = new Ext.data.Store({
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
					{"rownum":"1","gcn":"英联国际化学品（北京）有限公司","groupCorporation":"101","adscription":"集团客户","organizer":"1"},
					{"rownum":"2","gcn":"太通建设有限公司北京第八分公司","groupCorporation":"102","adscription":"集团客户","organizer":"2"},
					{"rownum":"3","gcn":"北京中视金麒麟广告传媒有限公司","groupCorporation":"103","adscription":"集团客户","organizer":"3"},			
					{"rownum":"4","gcn":"北京怡园小馆家常菜餐饮有限公司","groupCorporation":"104","adscription":"集团客户","organizer":"4"}				
					]
		};
	windowstore.loadData(windowmemberData);

	 //复选框
	var windowsm2 = new Ext.grid.CheckboxSelectionModel();
	
	// 定义自动当前页行号
	var windowrownum2 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var windowcm2 = new Ext.grid.ColumnModel([windowrownum2,windowsm2,
	           {
				header : '客户间关系', // 列标题
				dataIndex : 'customerrelation', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
				// 是否可排序
		    }
			]);

	/**
	 * 数据存储
	 */
	var windowstore2 = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customerrelation' // Json中的属性Key值
								}
								])
			});
	
	var windowmemberData2= {
			TOTALCOUNT:2,
			rows:[
			{"rownum":"1","customerrelation":"合作关系"},
			{"rownum":"2","customerrelation":"控股关系"},
			{"rownum":"3","customerrelation":"伙伴关系"},			
			{"rownum":"4","customerrelation":"上下游关系"},
			{"rownum":"5","customerrelation":"其他关系1"},
			{"rownum":"6","customerrelation":"其他关系2"},
			{"rownum":"7","customerrelation":"其他关系3"},
			{"rownum":"8","customerrelation":"其他关系4"},
			{"rownum":"9","customerrelation":"其他关系5"},
			{"rownum":"10","customerrelation":"其他关系6"}
			]
		};
	windowstore2.loadData(windowmemberData2);
	
	var windowgrid2 = new Ext.grid.GridPanel({
		height : 488,
		frame : true,
		autoScroll : true,
		store : windowstore2, // 数据存储
		stripeRows : true, // 斑马线
		cm : windowcm2, // 列模型
        sm : windowsm2,

		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
// 表格实例
var windowgrid = new Ext.grid.GridPanel({
		height : 338,
		frame : true,
		autoScroll : true,
		store : windowstore, // 数据存储
		stripeRows : true, // 斑马线
		cm : windowcm, // 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 700,
				height : 550,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal">企业关系维护</span>',
				// iconCls : 'page_addIcon',
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				maximizable : false,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				pageY : 20,
				pageX : 200,
					//document.body.clientWidth / 2 - 200 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .66,
						layout : 'form',
						//labelWidth : 60, // 标签宽度
						//defaultType : 'textfield',
						border : false,
						items : [windowForm,windowgrid]}, {
							columnWidth : .34,
							layout : 'form',
							//labelWidth : 60, // 标签宽度
							//defaultType : 'textfield',
							border : false,
							items : [windowgrid2]
						}]
				}],
				buttons : [
						{
							text : '保存',
							handler : function() {
							
								addRoleWindow.hide();
								Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	
	
	var qForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 80,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '子公司名称',
											name : 'gcn',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '关系名称', // 标签
											id : 'groupCorporation',
											name : 'groupCorporation', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '创建人', // 标签
											name : 'organizer', // name:后台根据此name属性取值
											maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
											allowBlank : true,
											anchor : '80%'// 宽度百分比
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
				header : '子公司名称', // 列标题
				dataIndex : 'gcn', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '关系名称',
				dataIndex : 'groupCorporation',
				sortable : true,
				width : 150
			},{
				header : '创建人',
				dataIndex : 'adscription'
			},{
				header : '创建时间',
				dataIndex : 'organizer'
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
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","gcn":"英联国际化学品（北京）有限公司","groupCorporation":"101","adscription":"集团客户","organizer":"1"},
			{"rownum":"2","gcn":"太通建设有限公司北京第八分公司","groupCorporation":"102","adscription":"集团客户","organizer":"2"},
			{"rownum":"3","gcn":"北京中视金麒麟广告传媒有限公司","groupCorporation":"103","adscription":"集团客户","organizer":"3"},			
			{"rownum":"4","gcn":"北京怡园小馆家常菜餐饮有限公司","groupCorporation":"104","adscription":"集团客户","organizer":"4"}				
			]
		};
	debugger;
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '新增',
					iconCls : 'page_addIcon',
					handler : function() {
						editInit();
					}},{
					text : '修改',
					iconCls : 'page_addIcon',
					handler : function() {
						editInit();
					}},{
					text : '删除',
					iconCls : 'page_addIcon',
					handler : function() {
						//Ext.MessageBox.alert('提示', "取消关注成功!");
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
				    title: "集团客户管理->集团企业关系维护", 
				    height: 108,
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
	}
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