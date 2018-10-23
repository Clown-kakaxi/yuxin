/**
 * 功能：账户实时查询
 */

Ext.onReady(function() {
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['存款', '0001'], ['贷款', '0002']]
			}); 
	var qForm = new Ext.form.FormPanel({
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
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '客户名称',
									name : 'q1',
									xtype : 'textfield', // 设置为数字输入框类型
									 labelStyle: 'text-align:right;',
									anchor : '80%'
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '组织机构代码', // 标签
									name : 'q6', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '80%' // 宽度百分比
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [new Ext.form.ComboBox({
										id : 'c2',
										hiddenName : 'area1',
										fieldLabel : '账户类别',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : boxstore,
										displayField : 'name',
										valueField : 'code',
										mode : 'local',
										listWidth : 200, // 下拉列表的宽度,默认为下拉选择框的宽度
										forceSelection : true,
										typeAhead : true,
										value : '0000',
										resizable : true,
										anchor : '90%'
									})]
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
				header : '查询时间', // 列标题
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
				header : '账号',
				dataIndex : 'c4'
			},{
				header : '时点余额',
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
			{"rownum":"1","c1":"2011-04-11 8:44:45 PM","c2":"未来科讯信息技术(北京)有限公司","customertype":"集团客户","customerlevel":"1"},
			{"rownum":"2","c1":"2011-02-21 6:44:45 AM","c2":"事天讯通(北京)信息技术有限公司","customertype":"集团客户","customerlevel":"2"},
			{"rownum":"3","c1":"2011-03-01 1:44:45 PM","c2":"歌乐网（北京）网络技术有限公司","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","c1":"2011-03-01 1:54:45 PM","c2":"北京中北联国际服务贸易有限公司","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	store.loadData(memberData);

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
				text : '导出',
				handler : function() {
						//Ext.MessageBox.alert('提示', "取消关注成功!");
				}
			}
			]
		});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal"></span>',
				height : 400,
				frame : true,
				autoScroll : true,
				region : 'center', // 返回给页面的div
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
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
				    title: "客户管理->账户实时查询", 
				    height: 120,
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