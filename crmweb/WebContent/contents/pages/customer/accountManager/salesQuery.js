Ext.onReady(function() {
	var qForm = new Ext.form.FormPanel({
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
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '机构号',
											name : 'e1',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
										},{
											fieldLabel : '业务类型',
											name : 'e2',
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
											fieldLabel : '统计日期', // 标签
											id : 'e4',
											name : 'e4', // name:后台根据此name属性取值
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
											fieldLabel : '客户经理ID', // 标签
											id : 'e7',
											name : 'e7', // name:后台根据此name属性取值
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
											fieldLabel : '客户经理姓名', // 标签
											id : 'e9',
											name : 'e9', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
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
				header : '机构号',
				dataIndex : 'a1',
				sortable : true,
				width : 150
			}, {
				header : '机构名称',
				dataIndex : 'a2',
				width : 150
			},{
				header : '统计日期',
				dataIndex : 'a3',
				width : 150
			},{
				header : '客户经理ID',
				dataIndex : 'a4'
			},{
				header : '客户经理姓名',
				dataIndex : 'a5'
			},{
				header : '客户名称',
				dataIndex : 'a6'
			},{
				header : '组织机构代码',
				dataIndex : 'a7',
				width : 150
			},{
				header : '产品编号',
				dataIndex : 'a8',
				width : 150
			},{
				header : '产品名称',
				dataIndex : 'a9'
			},{
				header : '时点余额',
				dataIndex : 'a10'
			},{
				header : '年均余额',
				dataIndex : 'a11'
			},{
				header : '销售金额',
				dataIndex : 'a12'
			},{
				header : '费用收入',
				dataIndex : 'a13'
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
								}, {
									name : 'a5'
								}, {
									name : 'a6'
								}, {
									name : 'a7'
								}, {
									name : 'a8'
								}, {
									name : 'a9'
								}, {
									name : 'a10'
								}, {
									name : 'a11'
								}, {
									name : 'a12'
								}, {
									name : 'a13'
								}, {
									name : 'a14'
								}, {
									name : 'a15'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","a1":"陈群",  "a2":"0001001","a3":"010-2114272","a4":"北京银行东单支行","a5":"本科","a6":"2001-06-30","a7":"9年","a8":"5年" ,"a9":"2年","a10":"2级","a11":"50.00万元","a12":"60.00万元","a13":"10.00万元"},
			{"rownum":"2","a1":"焦向波","a2":"0105281","a3":"13900163745","a4":"北京银行红星支行","a5":"硕士","a6":"2004-06-30","a7":"7年" ,"a8":"2年","a9":"1年","a10":"2级","a11":"50.00万元","a12":"40.00万元","a13":"10.00万元"},
			{"rownum":"3","a1":"姚亮",  "a2":"0022301","a3":"1582993942" ,"a4":"北京银行东四支行","a5":"本科","a6":"2004-06-30","a7":"7年" ,"a8":"3年","a9":"1年","a10":"3级","a11":"40.00万元","a12":"40.00万元","a13":"10.00万元"},			
			{"rownum":"4","a1":"余永智","a2":"0012343","a3":"020-3452354","a4":"北京银行上地支行","a5":"硕士","a6":"2001-06-30","a7":"9年","a8":"9年" ,"a9":"6年","a10":"1级","a11":"60.00万元","a12":"30.00万元","a13":"10.00万元"}				
			]
		};
	debugger;
	store.loadData(memberData);

	// 表格实例
	var grid = new Ext.grid.GridPanel({
	
				height : 400,
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
				    title: "客户经理管理->客户经理产品销售查询", 
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
}); 