Ext.onReady(function() {
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 80,
		items : [{
			
				id : 'annexName',
				name : 'annexName',
				xtype : 'textfield',
				inputType:'file',
				fieldLabel : '附件名称',
				//width : '100',
				anchor : '50%'
			
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
				header : '客户中文名称', // 列标题
				dataIndex : 'w1', // 数据索引:和Store模型对应
				sortable : true,
				width : 200
				// 是否可排序
		    },{
				header : '组织机构代码',
				dataIndex : 'w2',
				sortable : true,
				width : 150
			},{
				header : '客户级别',
				dataIndex : 'w3'
			},{
				header : '来源渠道',
				dataIndex : 'w4'
			},{
				header : '行业',
				dataIndex : 'w5'
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
									name : 'w1' // Json中的属性Key值
								}, {
									name : 'w2'
								}, {
									name : 'w3'
								}, {
									name : 'w4'
								}, {
									name : 'w5'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","w1":"北分图森（北京）科技发展有限公司","w2":"56010323-1","w3":"大型","w4":"","w5":"制造业"},
			{"rownum":"2","w1":"中石理想石油装备(北京)有限公司"  ,"w2":"56010623-1","w3":"中型","w4":"","w5":"制造业"},
			{"rownum":"3","w1":"歌乐网（北京）网络技术有限公司"  ,"w2":"56010067-2","w3":"中型","w4":"","w5":"娱乐业"},			
			{"rownum":"4","w1":"北京鑫金易装饰工程有限责任公司"  ,"w2":"56010233-1","w3":"大型","w4":"","w5":"地产业"}				
			]
		};
	debugger;
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '加载',
					iconCls : 'addIconCss',
					handler : function() {
						 					}
				},{
					text : '导入',
					iconCls : 'addIconCss',
					handler : function() {
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
				layout : 'fit',
				frame : true,
				items : [{
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "潜在客户管理->潜在客户导入", 
				    height: 80,
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
				}]
			});
	

}); 