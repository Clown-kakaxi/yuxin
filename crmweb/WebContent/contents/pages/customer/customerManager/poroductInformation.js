Ext.onReady(function() {
	
	
	
	
	var qForm = new Ext.form.FormPanel({
		//title : '<span style="font-weight:normal">查询条件<span>',
		//border : true,
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 150,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '产品代码',
											name : 'cn',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}, {
											fieldLabel : '开办行',
											name : 'cl',
											xtype : 'numberfield', // 设置为数字输入框类型
											allowDecimals : false, // 是否允许输入小数
											allowNegative : false, // 是否允许输入负数
											maxValue : 120,
											anchor : '80%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '产品类型', // 标签
											id : 'ct',
											name : 'ct', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '状态', // 标签
											name : 'c', // name:后台根据此name属性取值
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
						queryBalanceInfo(qForm.getForm());
					}*/
				}, {
					text : '重置',
					iconCls : 'tbar_synchronizeIcon'
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
				header : '产品代码', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true
				
				// 是否可排序
		    },{
				header : '状态',
				dataIndex : 'c2',
				sortable : true
				
			},{
				header : '开办行',
				dataIndex : 'c3'
			},{
				header : '代表行',
				dataIndex : 'c4'
			},{
				header : '额度',
				dataIndex : 'c5'
			},{
				header : '币种',
				dataIndex : 'c6'
			},{
				header : '产品类型',
				dataIndex : 'c7'
			},{
				header : '业务关系',
				dataIndex : 'c8'
			},{
				header : '账户代码',
				dataIndex : 'c9'
			},{
				header : '总额',
				dataIndex : 'c10'
			},{
				header : '费用描述',
				dataIndex : 'c11'
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
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"路人甲","organizationcode":"101","customertype":"老客户","customerlevel":"1"},
			{"rownum":"2","customername":"路人乙","organizationcode":"102","customertype":"大客户","customerlevel":"2"},
			{"rownum":"3","customername":"路人丙","organizationcode":"103","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"路人丁","organizationcode":"104","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	debugger;
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '',
					iconCls : 'page_addIcon',
					handler : function() {
						Ext.MessageBox.alert('提示', "取消关注成功!");
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
				    title: "产品信息", 
				    height: 180,
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
	

	// 获取选择行
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


}); 