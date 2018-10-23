/**
 * 功能：客户存贷款查询
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
		data : [['请选择', '0000']]
			});
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 180,
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
									listWidth : 140, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
							id : 'q3',
							hiddenName : 'area1',
							fieldLabel : '行业小类',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : boxstore7,
							displayField : 'name',
							valueField : 'code',
							mode : 'local',
							listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
							forceSelection : true,
							typeAhead : true,
							value : '0000',
							resizable : true,
							anchor : '90%'
						}),{
									fieldLabel : '存款时点余额起始', // 标签
									name : 'q4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '贷款时点余额起始', // 标签
									name : 'q5', // name:后台根据此name属性取值
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
									name : 'q6', // name:后台根据此name属性取值
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
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									id : 'c4',
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
								}),{
									fieldLabel : '存款时点余额截止', // 标签
									name : 'q9', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '贷款时点余额截止', // 标签
									name : 'q10', // name:后台根据此name属性取值
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
									fieldLabel : '客户名称', // 标签
									name : 'q11', // name:后台根据此name属性取值
									labelStyle: 'text-align:right;',
									allowBlank : true,
									anchor : '90%'// 宽度百分比
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
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),{
									fieldLabel : '客户级别', // 标签
									name : 'q13', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '存款日均余额起始', // 标签
									name : 'q14', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '贷款日均余额起始', // 标签
									name : 'q15', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
							fieldLabel : '组织机构代码',
							name : 'q16',
							xtype : 'numberfield', // 设置为数字输入框类型
							 labelStyle: 'text-align:right;',
							maxValue : 120,
							anchor : '90%'
						},new Ext.form.ComboBox({
									id : 'q229',
									hiddenName : 'area1',
									fieldLabel : '行业大类',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore7,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),{
							fieldLabel : '统计口径', // 标签
							name : 'q18', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
						},{
							fieldLabel : '存款日均余额截止', // 标签
							name : 'q19', // name:后台根据此name属性取值
							allowBlank : true, // 是否允许为空
							labelStyle: 'text-align:right;',
							anchor : '90%' // 宽度百分比
						},{
							fieldLabel : '贷款日均余额截止', // 标签
							name : 'q20', // name:后台根据此name属性取值
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
				header : '统计日期', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    },{
				header : '客户名称',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			},{
				header : '组织机构代码',
				dataIndex : 'c3'
			},{
				header : '客户状态',
				dataIndex : 'c4'
			},{
				header : '客户规模',
				dataIndex : 'c5'
			},{
				header : '考核口径规模类型',
				dataIndex : 'c6'
			},{
				header : '行业大类',
				dataIndex : 'c7'
			},{
				header : '行业小类',
				dataIndex : 'c8'
			},{
				header : '客户类型',
				dataIndex : 'c9'
			},{
				header : '客户级别',
				dataIndex : 'c10'
			},{
				header : '考核口径规模类型',
				dataIndex : 'c11'
			},{
				header : '存款：活期存款时点余额',
				width : 150,
				dataIndex : 'c12'
			},{
				header : '存款：定期存款时点余额',
				width : 150,
				dataIndex : 'c13'
			},{
				header : '存款：协议存款时点余额',
				width : 150,
				width : 150,
				dataIndex : 'c14'
			},{
				header : '存款：保证金存款时点余额',
				width : 150,
				dataIndex : 'c15'
			},{
				header : '存款：保本机构理财存款时点余额',
				width : 150,
				dataIndex : 'c16'
			},{
				header : '存款：其他存款时点余额',
				dataIndex : 'c17'
			},{
				header : '存款时点余额合计',
				dataIndex : 'c18'
			},{
				header : '存款：活期存款日均余额',
				width : 150,
				dataIndex : 'c19'
			},{
				header : '存款：定期存款日均余额',
				width : 150,
				dataIndex : 'c20'
			},{
				header : '存款：协议存款日均余额',
				width : 150,
				dataIndex : 'c21'
			},{
				header : '存款：保证金存款日均余额',
				width : 150,
				dataIndex : 'c22'
			},{
				header : '存款：保本机构理财存款日均余额',
				width : 150,
				dataIndex : 'c23'
			},{
				header : '存款：其他存款日均余额',
				dataIndex : 'c24'
			},{
				header : '存款日均余额合计',
				dataIndex : 'c25'
			},{
				header : '贷款：流动资金贷款时点余额',
				width : 150,
				dataIndex : 'c26'
			},{
				header : '贷款：固定资产贷款时点余额',
				width : 150,
				dataIndex : 'c27'
			},{
				header : '贷款：贸易融资贷款时点余额',
				width : 150,
				dataIndex : 'c28'
			},{
				header : '贷款：贴现点余额',
				dataIndex : 'c29'
			},{
				header : '贷款：其他贷款时点余额',
				width : 150,
				dataIndex : 'c30'
			},{
				header : '贷款：不良贷款时点余额',
				width : 150,
				dataIndex : 'c31'
			},{
				header : '贷款时点余额合计',
				dataIndex : 'c32'
			},{
				header : '贷款：流动资金贷款日均余额',
				width : 150,
				dataIndex : 'c33'
			},{
				header : '贷款：固定资产贷款日均余额',
				width : 150,
				dataIndex : 'c34'
			},{
				header : '贷款：贸易融资贷款日均余额',
				width : 150,
				dataIndex : 'c35'
			},{
				header : '贷款：贴现时点余额',
				width : 150,
				dataIndex : 'c36'
			},{
				header : '贷款：其他贷款日均余额',
				width : 150,
				dataIndex : 'c37'
			},{
				header : '贷款：不良贷款日均余额',
				width : 150,
				dataIndex : 'c38'
			},{
				header : '贷款日均余额合计',
				width : 150,
				dataIndex : 'c39'
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
								}, {
									name : 'c23'
								}, {
									name : 'c24'
								}, {
									name : 'c25'
								}, {
									name : 'c26'
								}, {
									name : 'c27'
								}, {
									name : 'c28'
								}, {
									name : 'c29'
								}, {
									name : 'c30'
								}, {
									name : 'c31'
								}, {
									name : 'c32'
								}, {
									name : 'c28'
								}, {
									name : 'c29'
								}, {
									name : 'c30'
								}, {
									name : 'c31'
								}, {
									name : 'c32'
								}, {
									name : 'c33'
								}, {
									name : 'c34'
								}, {
									name : 'c35'
								}, {
									name : 'c36'
								}, {
									name : 'c37'
								}, {
									name : 'c38'
								}, {
									name : 'c39'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"天津市日昊昌盛钢铁贸易有限公司","organizationcode":"67235353","customertype":"集团客户","customerlevel":"1"},
			{"rownum":"2","customername":"天地人传媒有限公司","organizationcode":"67002938","customertype":"集团客户","customerlevel":"2"},
			{"rownum":"3","customername":"西尼亚（北京）环境科技有限公司","organizationcode":"6827365423","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"威克瑞电线电缆有司电缆销售分司","organizationcode":"68263810","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	//var tbar = new Ext.Toolbar({
			//	items : [{
			//		text : '取消关注',
				//	handler : function() {
			//			Ext.MessageBox.alert('提示', "取消关注成功!");
			//		}
			//	}
			//	]
			//});
//

	// 表格实例
	var grid = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal"></span>',
				height : 300,
				frame : true,
				autoScroll : true,
				region : 'center', // 返回给页面的div
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				//tbar : tbar, // 表格工具栏
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
				    title: "客户管理->客户存贷款查询", 
				    height: 200,
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