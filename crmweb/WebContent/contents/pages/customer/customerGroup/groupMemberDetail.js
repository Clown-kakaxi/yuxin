/*
 * 2011-06-10
 * 姚亮
 * 查看客户群成员
 * 
 * */

	//新增客户群成员的表格面板
	
	var custbarDetail = new Ext.Toolbar({
		items:[
//			{
//			text:'归入客户群'
//		},'-',{
//			text:'移出客户群'
//		},
		{
			text:'查看详细',
			handler:function()
			{
				window.location="../customer/customerManager/customerViewIndex.html";
			}
		}
		]
	});
//	var cussmDetail = new Ext.grid.CheckboxSelectionModel();
	var cusrownumDetail = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cuscmDetail = new Ext.grid.ColumnModel(
			   [cusrownumDetail,
	           {
				header : '客户名称', // 列标题
				dataIndex : 'cusNames', // 数据索引:和Store模型对应
				sortable : true,
				width : 300
				// 是否可排序
		    },
		    {
		    	header:'组织机构代码',
		    	dataIndex:'customCode',
		    	width:150,
		    	id:'customCode'
		    },
		    {
		    	header:'加入群组日期',
		    	dataIndex:'addDate',
		    	width:150,
		    	id:'addDate'
		    }
//		    {
//				header : '客户类型',
//				dataIndex : 'cusTypes',
//				sortable : true,
//				width : 200
//			},{
//				header : '客户级别',
//				dataIndex : 'lev',
//				sortable : true,
//				width : 200
//			}, {
//				header : '机构名称',
//				dataIndex : 'depandNames',
//				width :200
//			}, {
//				header : '客户贡献度',
//				dataIndex : 'levels',
//				width : 200
//			},{
//				header : '是否集团客户',
//				dataIndex : 'isGroup',
//				width : 200
//			}
			]);

	/**
	 * 数据存储
	 */
	var cusstoreDetail = new Ext.data.Store({
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
									name : 'cusNames' // Json中的属性Key值
								}, {
									name : 'cusTypes'
								},{
									name:'lev'
								}, {
									name : 'depandNames'
								}, {
									name : 'levels'
								},{
									name:'isGroup'
								},
								{
									name:'customCode'
								},
								{
									name:'addDate'
								}
								])
			});
	
	var cusmemberDataDetail= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","cusNames":"北分图森（北京）科技发展有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"2","cusNames":"阿贵家 ( 北京 ) 餐饮管理有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"3","cusNames":"中政红旗(北京)文化传播有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"4","cusNames":"中节能（天津）投资集团有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"5","cusNames":"未来科讯信息技术(北京)有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"6","cusNames":"威克瑞电线电缆有司电缆销售分司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"7","cusNames":"太通建设有限公司北京第八分公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"8","cusNames":"尚恩优品(北京)科技发展有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"rownum":"9","cusNames":"宁波保税区理工监测设备有限公司","customCode":"010101010","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"}
			]
		};
		
	cusstoreDetail.loadData(cusmemberDataDetail);
	
	// 每页显示条数下拉选择框
			var cuspagesize_comboDetail = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value1',
						displayField : 'text',
						value : '20',
						editable : false,
						width : 85
					});
	// 分页工具栏
	var cusbbarDetail = new Ext.PagingToolbar({
						pageSize : cusrownumDetail,
						store : cusstoreDetail,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', cuspagesize_comboDetail]
					});
    // create the Grid
					
    var cusGridDetail = new Ext.grid.GridPanel({
    	tbar:custbarDetail,
    	title:'群成员列表',
        store: cusstoreDetail,
		cm : cuscmDetail,
		height :350,
//		sm:cussmDetail,
		bbar : cusbbarDetail,
		selModel:new Ext.grid.RowSelectionModel({
				singleSelect:true
				}),
        stripeRows: true,
       listeners:{
       	rowdblclick:function()
       	{
       		window.location="../customerManager/customerViewIndex.html";
       		
       	}
       },
        width: '100%'
    });
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});


	var pagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value1',
						displayField : 'text',
						value : '20',
						editable : false,
						width : 85
					});
	var number = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
						bbar.pageSize = parseInt(comboBox.getValue());
						number = parseInt(comboBox.getValue());
						store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});
	// 分页工具栏

	var memberSearch=new Ext.FormPanel({
		//layout:'fit',
		title:'成员查询',
		frame:true,
		border:false,
		buttonAlign:'center',
		labelAlign:'right',
		items : [{
					layout:'column',
					items : [
							{
								columnWidth : .33,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户名称',
									labelStyle:{
										width:'120px'
									},	
									anchor : '90%'
								}]
							}, 
							{
								columnWidth : .33,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '组织机构代码',
									labelStyle:{
										width:'120px'
									},	
								
									anchor : '90%'
								}]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [{ 
									xtype : 'datefield',
									fieldLabel : '加入群日期',
									labelStyle:{
										width:'120px'
									},	
									
									anchor : '90%'
								}]
							}]
				}],
				//buttonAlign:'center',
				buttons:[{
					text:'查询',
					width:80
				},{
					text:'重置'
				}]
	});
	
var winMemberList=new Ext.Window({
							//layout : 'fit',
									width : 800,
									height :450,
									closable : true,
									resizable : false,
									collapsible : false,
									draggable : true,
									closeAction : 'hide',
									title : '查看客户名单成员',
									//titleCollapse : false,
									modal : true, // 模态窗口 
									//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
									animCollapse : false,
									//maximizable : true,
									//maximized : true,
									border : false,
									closable : true,
									animateTarget : Ext.getBody(),
									constrain : true,
									items : [memberSearch,cusGridDetail],
									buttonAlign:'center',
									buttons:[{
										text:'保存',
										handler:function()
										{
											winMemberList.hide();
										}
									},{
	          				  			text: '取消',
	           				  			handler:function(){
	            			 		    winMemberList.hide();
	            						}
	       			 				}]	
							});
