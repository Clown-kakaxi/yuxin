Ext.onReady(function() {
var windowForm = new Ext.form.FormPanel({
	title : '<span style="font-weight:normal">新增潜在客户</span>',
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		 style:'padding: 10 10 10 10',
		height : 150,
		items : [{
					
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户名称',
											name : 'gcn',
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}, {
											fieldLabel : '组织机构代码',
											name : 'num',
											labelStyle: 'text-align:right;',
											xtype : 'numberfield', // 设置为数字输入框类型
											anchor : '80%'
										},{
											fieldLabel : '行业',
											name : 'ct',
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										},{
											fieldLabel : '联系人',
											name : 'cl',
											labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										},{
											fieldLabel : '联系方式',
											name : 'cl1',
											labelStyle: 'text-align:right;',
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
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户名称',
											name : 'cn',
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
											fieldLabel : '组织机构代码', // 标签
											id : 'ct',
											name : 'ct', // name:后台根据此name属性取值
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
											fieldLabel : '客户类型', // 标签
											name : 'c', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
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
									fieldLabel : '客户级别',
									name : 'cl',
									xtype : 'numberfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									maxValue : 120,
									anchor : '80%'
								}]
							}]
				}],
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
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	           {
				header : '客户名称', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    },{
				header : '组织机构代码',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			},{
				header : '行业',
				dataIndex : 'c3'
			},{
				header : '联系人',
				dataIndex : 'c4'
			},{
				header : '联系方式',
				dataIndex : 'c5'
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
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","c1":"威克瑞电线电缆有司电缆销售分司","c2":"101","c3":"通信业","c4":"焦向波","c5":"13902738843"},
			{"rownum":"2","c1":"歌乐网（北京）网络技术有限公司","c2":"102","c3":"信息服务业","c4":"余勇智","c5":"1302873223"},
			{"rownum":"3","c1":"北京雄财新能源科技发展有限公司","c2":"103","c3":"能源业","c4":"姚亮","c5":"15902839823"},			
			{"rownum":"4","c1":"北京茶礼沃德茶文化推广中心","c2":"104","c3":"餐饮业","c4":"陈群","c5":"158022345323"}				
			]
		};
	debugger;
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '新增',
					
					handler : function() {
						addRoleWindow.show();
						//Ext.MessageBox.alert('提示', "修改成功!");
					}},{
					text : '修改',
					handler : function() {
						Ext.MessageBox.alert('提示', "修改成功!");
					}
				}
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
				//title : '<span style="font-weight:normal">表格综合演示一</span>',
				//renderTo : 'gridDiv', // 和JSP页面的DIV元素ID对应
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
				    title: "潜在客户管理->潜在客户维护", 
				    height: 100,
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
	}
*/

});