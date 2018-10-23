Ext.onReady(function() {
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
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户号',
											name : 'a1',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
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
											id : 'a2',
											name : 'a2', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
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
											id : 'a3',
											name : 'a3', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
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
											fieldLabel : '营销团队成员数', // 标签
											id : 'a4',
											name : 'a4', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											anchor : '80%' // 宽度百分比
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
				header : '客户号', // 列标题
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
				header : '营销团队名称',
				dataIndex : 'a3',
				width : 150
			},{
				header : '营销团队成员数',
				dataIndex : 'a4',
				width : 150
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
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","a1":"1233",  "a2":"北京鑫金易装饰工程有限责任公司","a3":"北京银行东单支行团队1","a4":"北京银行东单支行"},
			{"rownum":"2","a1":"1412","a2":"北京中联红月亮文化传播有限公司","a3":"北京银行红星支行团队1","a4":"北京银行红星支行"},
			{"rownum":"3","a1":"2323",  "a2":"北京幸福四叶草婚礼策划有限公司","a3":"北京银行东四支行团队1" ,"a4":"北京银行东四支行"},			
			{"rownum":"4","a1":"2342","a2":"北京奥峰铭金属制品有限公司","a3":"北京银行上地支行团队1","a4":"北京银行上地支行"}				
			]};
	
	store.loadData(memberData);

	/*// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '信息维护',
					iconCls : 'page_addIcon',
					handler : function() {
						addRoleWindow.show();
					}
				}
				]
			});
*/

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
			    //tbar : tbar, // 表格工具栏
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
				    title: "客户经理管理->营销团队查询", 
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