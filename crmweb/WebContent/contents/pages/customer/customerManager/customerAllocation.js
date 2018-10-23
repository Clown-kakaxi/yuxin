Ext.onReady(function() {
	 //定义树的跟节点
	   var root=new Ext.tree.TreeNode({
	          id:"root",//根节点id
	   draggable:false,
	          text:"分配客户至"
	    });
	   
	    //定义树节点
	    var c1=new Ext.tree.TreeNode({
	      id:'c1',//子结点id
	      text:'机构'
	      
	    });
	 
	    var c11=new Ext.tree.TreeNode({
	        id:'c11',
	       
	        text:'北京支行'
	        	
	      });
	     var c12=new Ext.tree.TreeNode({
	        id:'c12',
	    
	        text:'天津支行'
	      });
	    /* var c111=new Ext.tree.TreeNode({
	        id:'c111',
	        checked:false,
	        text:'客户经理甲'
	      });
	     var c112=new Ext.tree.TreeNode({
	        id:'c112',
	        checked:false,
	        text:'客户经理乙'
	      });
	       var c121=new Ext.tree.TreeNode({
	        id:'c121',
	        checked:false,
	        text:'客户经理丙'
	      });*/
	      
	    root.appendChild(c1);//为根节点增加子结点c1
	   
	    c1.appendChild(c11);
	    c1.appendChild(c12);
	   /* c11.appendChild(c111);
	     c11.appendChild(c112);
	    c12.appendChild(c121);
	    */
	    //生成树形面板

	       var tree=new Ext.tree.TreePanel({
	         root:root,//定位到根节点
	         animate:true,//开启动画效果
	         enableDD:true,//允许子节点拖动
	         border:false,//没有边框
	         containerScroll: true,
	         rootVisible:true//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
	        // onlyLeafCheckable: false,
	        
	      });
	     /* c11.on("checkchange",function(e){
	    	   		c111.ui.onDblClick(e);
	       });  */
	       var windowForm = new Ext.form.FormPanel({
	   		frame : true, //是否渲染表单面板背景色
	   		labelAlign : 'left', // 标签对齐方式
	   		collapsible : true,// 是否可收缩
	   		buttonAlign : 'center',
	   		height : 80,
	   		items : [{
	   					layout : 'column',
	   					border : false,
	   					items : [{
	   								columnWidth : .50,
	   								layout : 'form',
	   								labelWidth : 90, // 标签宽度
	   								defaultType : 'textfield',
	   								border : false,
	   								items : [{
	   											fieldLabel : '客户经理名称',
	   											name : 'wincn',
	   											 labelStyle: 'text-align:right;',
	   											xtype : 'textfield', // 设置为数字输入框类型
	   											anchor : '90%'
	   										}]
	   							},{
	   								columnWidth : .50,
	   								layout : 'form',
	   								labelWidth : 90, // 标签宽度
	   								defaultType : 'textfield',
	   								border : false,
	   								items : [{
	   											fieldLabel : '客户经理编号',
	   											name : 'wincn',
	   											 labelStyle: 'text-align:right;',
	   											xtype : 'textfield', // 设置为数字输入框类型
	   											anchor : '90%'
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
			var windowsm = new Ext.grid.CheckboxSelectionModel();
	   	// 定义自动当前页行号
	   	var windowrownum = new Ext.grid.RowNumberer({
	   				header : 'No.',
	   				width : 28
	   			});

	   	// 定义列模型
	   	var windowcm = new Ext.grid.ColumnModel([windowrownum,windowsm,
	   	           {
	   				header : '客户经理名称', // 列标题
	   				dataIndex : 'customername', // 数据索引:和Store模型对应
	   				sortable : true,// 是否可排序
	   				width : 100
	   		    }, {
	   				header : '客户经理编号',
	   				dataIndex : 'organizationcode',
	   				sortable : true,
	   				width : 100
	   			}, {
	   				header : '归属机构',
	   				dataIndex : 'customertype'
	   			}, {
	   				header : '岗位级别',
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
	   			{"rownum":"1","customername":"焦向波","organizationcode":"101","customertype":"北京银行天津分行","customerlevel":"1"},
	   			{"rownum":"2","customername":"陈群","organizationcode":"102","customertype":"北京银行东四支行","customerlevel":"2"},
	   			{"rownum":"3","customername":"姚亮","organizationcode":"103","customertype":"北京银行上地支行","customerlevel":"3"},			
	   			{"rownum":"4","customername":"余勇智","organizationcode":"104","customertype":"北京经营中关村支行","customerlevel":"4"}				
	   			]
	   		};
	   	windowstore.loadData(windowmemberData);
		// 表格工具栏
		var windowtbar = new Ext.Toolbar({
					items : [{
						text : '分配客户',
						handler : function() {
							editInit();
						}
					}
					]
				});
		
	   	var windowgrid = new Ext.grid.GridPanel({
			height : 338,
			frame : true,
			autoScroll : true,
			store : windowstore, // 数据存储
			stripeRows : true, // 斑马线
			sm:windowsm,
			cm : windowcm, // 列模型
			tbar : windowtbar, // 表格工具栏
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	       
	var addRoleWindow = new Ext.Window(
			{
				//layout : 'fit',
				width : 800,
				height : 400,
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
				pageY : 20,
				pageX : document.body.clientWidth / 2 - 420 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [
					/*{
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .34,
						layout : 'form',
						//labelWidth : 60, // 标签宽度
						//defaultType : 'textfield',
						border : false,
						items : [tree]}, {
							columnWidth : .66,
							layout : 'form',
							//labelWidth : 60, // 标签宽度
							//defaultType : 'textfield',
							border : false,
							items : [*/
								
							windowForm,windowgrid
								
						/*	]
						}]
				}*/
							
							],
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
		height : 100,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '客户名称',
											name : 'cn',
											 labelStyle: 'text-align:right;',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '组织机构代码', // 标签
											id : 'ct',
											 labelStyle: 'text-align:right;',
											name : 'ct', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 60, // 标签宽度
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
								labelWidth : 60, // 标签宽度
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
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"北京国安信息科技股份有限公司","organizationcode":"101","customertype":"集团客户","customerlevel":"1"},
			{"rownum":"2","customername":"北京鸿联九五信息产业有限公司","organizationcode":"102","customertype":"集团客户","customerlevel":"2"},
			{"rownum":"3","customername":"北京国安通信有限公司","organizationcode":"103","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"华夏基金管理有限公司","organizationcode":"104","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '分配客户',
					handler : function() {
						editInit();
					}
				}
				/*, '-', {
					text : '修改',
					iconCls : 'page_edit_1Icon',
					handler : function() {
						editInit();
					}
				}, '-', {
					text : '删除',
					iconCls : 'page_delIcon',
					handler : function() {
						deleteRoleItems();
					}
				}*/
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
				    title: "客户管理->客户分配", 
				    height: 128,
				    hidden:false,
				    margins: '0 0 0 0',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [grid]
			    }] 

			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		editInit();
	});
	function editInit(){
		addRoleWindow.show();
		root.expand(true);
	};
/*
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
	}*/


}); 