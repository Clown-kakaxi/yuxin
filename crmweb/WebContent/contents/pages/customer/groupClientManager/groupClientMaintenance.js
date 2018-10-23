Ext.onReady(function() {
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['全部', '0000'],['全行型', '0001'], ['区域型', '0002']]
	});
	var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['全部', '0000'],['启用', '0001'], ['停用', '0002']]
	});

	 var tempTreeNode;
	 var orgTree = new Ext.tree.TreePanel(
	 	{
	 	autoScroll:true,
	 	enableDD:true,
	 	id:'orgTree',
		useArrows:false,
		listeners:{
			'click':function(node)
			{
				Ext.getCmp("orgTreeCombo").setValue(node.text);
				//tempTreeNode=node;
				node.expand();
			}
		},
		root:new Ext.tree.AsyncTreeNode({
			id:'root',
//			expanded:false,
			text:'北京银行总行',
			autoScroll:true,
			children:[

			{
				text:'北京分行',
//				expanded:true,
				children:[
				{
					text:'中关村管理部',
					leaf:true
				},
				{
					text:'金运支行',
					leaf:true
				},
				{
					text:'华安支行',
					leaf:true
				}
				]
			},
			{
				text:'天津分行',
				children:[
				{
					text:'中关村管理部',
					leaf:true
				},
				{
					text:'金运支行',
					leaf:true
				},
				{
					text:'华安支行',
					leaf:true
				}
				]				
			},
			{
				text:'上海分行',
				children:[
				{
					text:'中关村管理部',
					leaf:true
				},
				{
					text:'金运支行',
					leaf:true
				},
				{
					text:'华安支行',
					leaf:true
				}
				]
			}
			]
		})
	 }
	 );
	 

	 var orgTreeForAdd = new Ext.tree.TreePanel(
	 	{
	 	autoScroll:true,
	 	enableDD:true,
	 	id:'orgTree',
		useArrows:false,
		listeners:{
			'click':function(node)
			{
				Ext.getCmp("orgTreeComboForAdd").setValue(node.text);
				//tempTreeNode=node;
				node.expand();
			}
		},
		root:new Ext.tree.AsyncTreeNode({
			id:'root',
//			expanded:false,
			text:'北京银行总行',
			autoScroll:true,
			children:[

			{
				text:'北京分行',
//				expanded:true,
				children:[
				{
					text:'中关村管理部',
					leaf:true
				},
				{
					text:'金运支行',
					leaf:true
				},
				{
					text:'华安支行',
					leaf:true
				}
				]
			},
			{
				text:'天津分行',
				children:[
				{
					text:'中关村管理部',
					leaf:true
				},
				{
					text:'金运支行',
					leaf:true
				},
				{
					text:'华安支行',
					leaf:true
				}
				]				
			},
			{
				text:'上海分行',
				children:[
				{
					text:'中关村管理部',
					leaf:true
				},
				{
					text:'金运支行',
					leaf:true
				},
				{
					text:'华安支行',
					leaf:true
				}
				]
			}
			]
		})
	 }
	 );


	var windowForm = new Ext.form.FormPanel({
		title : '<span style="font-weight:normal">新增集团</span>',
		
		labelWidth : 120,
		region : 'north',
		height : 100,
		frame : true,
		split:true,
		labelAlign:'right',
//		collapsible : true,
		autoScroll : true,
//		title : '集团基本信息',
		buttonAlign:"center" ,
		items : [{ 
					layout : 'column',
					items : [
								{
								columnWidth : .5,
								layout : 'form',
								items : [
										{
											columnWidth:.25,
											xtype:'textfield',
											id:'blocCode',
											editable:true,
											name:'blocCode',
											triggerAction:'all',
											anchor:'90%',
										//	lazyRender:true,
											fieldLabel:'集团客户编号',
											value:'01010101'
										},								
										{
											columnWidth:.25,
											xtype:'textfield',
											id:'blocName',
											editable:true,
											name:'blocName',
											triggerAction:'all',
											anchor:'90%',
											fieldLabel:'集团客户名称',
											value:'中国北京集团公司'
										},											
										{
											xtype : 'textfield',
											fieldLabel : '集团母公司名称',
											value:'中国北京集团公司',
											id : 'parentCompay',
											name : 'parentCompay',
											anchor : '90%'
										}
										,
//										{
//											xtype : 'textfield',
//											fieldLabel : '公司业务',
//											value:'房地产',
//											id : 'blocBusiness',
//											name : 'blocBusiness',
//											anchor : '90%'
//										},
										{
											columnWidth:.25,
											xtype:'combo',
											id:'blocStatus',
											editable:true,
											name:'blocStatus',
											triggerAction:'all',
											anchor:'90%',
											fieldLabel:'集团状态',
											mode:'local',
											store: new Ext.data.ArrayStore({
									        id: 0,
									        fields: [
									            'myId',
									            'displayText'
									        ],
									        data: [[0, '停用'], [1, '启用']]
									 	   }),
									       valueField:'myId',
									       displayField:'displayText'
										},
										{
											columnWidth:.25,
											xtype:'combo',
											id:'blocType',
											editable:true,
											name:'blocType',
											triggerAction:'all',
											anchor:'90%',
											fieldLabel:'集团类型',
											mode:'local',
											store: new Ext.data.ArrayStore({
									        id: 0,
									        fields: [
									            'myId',
									            'displayText'
									        ],
									        data: [[0, '全行型'], [1, '区域型']]
									 	   }),
									       valueField:'myId',
									       displayField:'displayText'
										}
										]
							}, 
							{
								columnWidth : .5,
								layout : 'form',
								items : [
									   {
											xtype : 'textfield',
											fieldLabel : '集团母公司注册地址',
											value:'总行',
											id : 'parentCompanyAddress',
											name : 'parentCompanyAddress',
											anchor : '90%'
										},
										{				xtype:'combo',
														store : new Ext.data.SimpleStore( {
															fields : [],
															data : [ [] ]
														}),
														editable : false,
														id:'orgTreeComboForAdd',
														value : ' ',
														emptyText : '请选择...',
														fieldLabel : '授信主办行',
														anchor : '90%',
														
														mode : 'local',
														triggerAction : 'all',
														maxHeight : 390,
														// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
														tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
														allowBlank : false,
														onSelect : Ext.emptyFn,
														listeners:{
															'expand':function(){
															orgTreeForAdd.render('addOrgTreeDivForAdd');
															orgTreeForAdd.root.reload(); // 每次下拉都会加载数据

															}
														}

										},
										{
											xtype:'textfield',
											fieldLabel:'集团规模',
											value:'大型',
											id:'mainOrgScale',
											name:'mainOrgScale',
											anchor:'90%'
										},
										{
											xtype : 'textfield',
											fieldLabel : '公司简介',
											value:'经营房地产',
											id : 'blocBriefIntroduction',
											name : 'blocBriefIntroduction',
											anchor : '90%'
										}
									
										]
							}

							]
				}]


		});
		
		var addRoleWindow = new Ext.Window(
				{
					layout : 'fit',
					width : 800,
					height : 400,
					//resizable : false,//是否允许缩放
					draggable : true,//是否可以拖动
					closable : true,// 是否可关闭
					modal : true,
					closeAction : 'hide',
//					title : '<span style="font-weight:normal">新增集团</span>',
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
								
									//addRoleWindow.hide();
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
	
		var orgTreeCombox= new Ext.form.ComboBox(
			{
							store : new Ext.data.SimpleStore( {
								fields : [],
								data : [ [] ]
							}),
							editable : false,
							id:'orgTreeCombo',
							value : ' ',
							emptyText : '请选择...',
							fieldLabel : '授信主办行',
							anchor : '100%',
							
							mode : 'local',
							triggerAction : 'all',
							maxHeight : 390,
							// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
							tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDiv'></div></div></tpl>",
							allowBlank : false,
							onSelect : Ext.emptyFn

			}	
		);

		orgTreeCombox.on('expand', function() {
				orgTree.render('addOrgTreeDiv');
				orgTree.root.reload(); // 每次下拉都会加载数据

			});
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
											fieldLabel : '集团客户编号',
											name : 'gcn',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
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
											anchor : '80%'
										})]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '集团客户名称', // 标签
											id : 'groupCorporation',
											name : 'groupCorporation', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											anchor : '80%' // 宽度百分比
										}, new Ext.form.ComboBox({
											id : 'id_area2',
											hiddenName : 'area1',
											fieldLabel : '集团客户状态',
											triggerAction : 'all',
											store : boxstore2,
											displayField : 'name',
											valueField : 'code',
											labelStyle: 'text-align:right;',
											mode : 'local',
											//listWidth : 120, // 下拉列表的宽度,默认为下拉选择框的宽度
											forceSelection : true,
											typeAhead : true,
											value : '0000',
											resizable : true,
											anchor : '80%'
										})]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '集团母公司名称', // 标签
											name : 'organizer', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 90, // 标签宽度
								labelAlign:'right',
								border : false,
								items : [
									orgTreeCombox
								]
							}]
				}],
		buttons : [{
					text : '查询'
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
				header : '集团客户编号', // 列标题
				dataIndex : 'g1', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团客户名称', // 列标题
				dataIndex : 'g2', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团母公司名称', // 列标题
				dataIndex : 'g3', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '授信主办行', // 列标题
				dataIndex : 'g4', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团类型', // 列标题
				dataIndex : 'g5', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团客户状态',
				dataIndex : 'g6',
				sortable : true,
				width : 150
			},{
				header : '成员企业数',
				dataIndex : 'g7'
			},{
				header : '创建人',
				dataIndex : 'g8'
			},{
				header : '创建日期',
				dataIndex : 'g9'
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
								},{
									name : 'g8'
								},{
									name : 'g9'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","g1":"北京国安信息科技股份有限公司","g2":"CITIC Guoan Information Industry Co.,Ltd.","g3":"国安信息","g4":"总行营业部","g5":"全行型","g6":"启用","g7":"5"},
			{"rownum":"2","g1":"北京鸿联九五信息产业有限公司","g2":"CITIC Guoan Information Industry Co.,Ltd.","g3":"鸿联九五","g4":"上海分行","g5":"全行型","g6":"启用","g7":"5"},
			{"rownum":"3","g1":"北京国安通信有限公司"       ,"g2":"CITIC Guoan Information Industry Co.,Ltd.","g3":"国安通信","g4":"中关村管理部","g5":"区域型","g6":"启用","g7":"5"},			
			{"rownum":"4","g1":"华夏基金管理有限公司"       ,"g2":"China Asset Management Co.,Ltd."          ,"g3":"华夏基金","g4":"中关村管理部","g5":"区域型","g6":"未启用","g7":"5"}				
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '新增',
					iconCls : 'page_addIcon',
					handler : function() {
						windowForm.setTitle('新增集团基本信息');
						editInit();
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}},{
					text : '修改',
					handler : function() {
						windowForm.setTitle('修改集团基本信息');
						//addRoleWindow.setTitle('修改集团基本信息');
					editInit();
						
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}},
						{
					text : '删除',
					handler : function() {
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}
				},
						{
					text : '启用',
					handler : function() {
						//Ext.MessageBox.alert('提示', "取消关注成功!");
					}
				},
				{
					text : '集团视图',
					handler : function() {
						window.location.href = 'groupView.html' ;
					}
				},
				{
					text : '集团成员维护',
					handler : function() {
						//addMemberWindowForGroup.show();
						window.location="memberMaintenance.html"
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
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		 window.location.href = 'groupView.html' ;
	});


	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "集团客户管理->集团客户查询", 
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