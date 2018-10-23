			//***************************************************************************
			
//			var listPanelTest = Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.marketTeamId
//			alert(listPanelTest);
			
			var record = Ext.data.Record.create([
					{name: 'id', mapping: 'id'},
					{name: 'instn_no', mapping: 'instn_no'},
					{name: 'system_unitname', mapping: 'system_unitname'},
					{name: 'crm_dt', mapping: 'crm_dt'},
					{name: 'prd_id', mapping: 'prd_id'},
					{name: 'prod_name', mapping: 'prod_name'},
					{name: 'cust_name', mapping: 'cust_name'},
					{name: 'cust_id', mapping: 'cust_id'},
					{name: 'cust_zzdm', mapping: 'cust_zzdm'},
					{name: 'bal', mapping: 'bal',type:'float'},
					{name: 'year_avg', mapping: 'year_avg',type:'float'},
					{name: 'sell_amt', mapping: 'sell_amt',type:'float'},
					{name: 'rcv', mapping :'rcv',type:'float'}
				     ]);
				
				 var Sstore = new Ext.data.Store({
						restful:true,	
				        proxy : new Ext.data.HttpProxy({
				        	url:basepath+'/productDetailQuery.json',
				        	failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.status);
								if(resultArray == 403) {
									Ext.Msg.alert('提示', response.responseText);
								}
							}
				        }),
				       reader: new Ext.data.JsonReader({
				       totalProperty : 'json.count',
				        root:'json.data'
				        }, record)
					});

				 //***************************************
				
				 // 复选框
				var sm = new Ext.grid.CheckboxSelectionModel();

				// 定义自动当前页行号
				var rownum = new Ext.grid.RowNumberer({
							header : 'No.',
							width : 28
						});
					// 定义列模型
					var cm = new Ext.grid.ColumnModel([rownum,
					        {header : 'id',dataIndex : 'id',hidden:true},
					        {header : '统计日期',sortable : true,dataIndex : 'crm_dt'},
					        {header : '机构号',sortable : true,dataIndex : 'instn_no'},
					        {header : '机构名称',sortable : true,dataIndex : 'system_unitname'},
                   		    {header : '产品编号',sortable : true,dataIndex : 'prd_id'},
                   		    {header : '产品名称',sortable : true,dataIndex : 'prod_name'},
                   		    {header : '客户名称',sortable : true,dataIndex : 'cust_name'},
                   		    {header : '组织机构代码',dataIndex : 'cust_zzdm'},
                   		    {header : '时点余额 ',dataIndex : 'bal',sortable : true,align:'right',renderer: money('0,000.00')},
                   		    {header : '年均余额',dataIndex :'year_avg',sortable : true,align:'right',renderer: money('0,000.00')},
                   		    {header : '销售金额',dataIndex : 'sell_amt',sortable : true,align:'right',renderer: money('0,000.00')},
                   		    {header : '产品收入',dataIndex : 'rcv',sortable : true,align:'right',renderer: money('0,000.00')}
							 ]);
					
					
				     var pagesize_combo = new Ext.form.ComboBox({
				         name : 'pagesize',
				         triggerAction : 'all',
				         mode : 'local',
				         store : new Ext.data.ArrayStore({
				             fields : ['value', 'text'],
				             data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
				         }),
				         valueField : 'value',
				         displayField : 'text',
				         value : '100',
				         forceSelection : true,
				         width : 85
				     });
				    var number = parseInt(pagesize_combo.getValue());
				    pagesize_combo.on("select", function(comboBox) {
				    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
						Sstore.load({
							params : {
								'product_id':Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.product_id,
								'instn_no':Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.instn_no,
								'crm_date':Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.crm_dt,
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
								});
					});
					var bbar = new Ext.PagingToolbar({
				        pageSize : number,
				        store : Sstore,
				        displayInfo : true,
				        displayMsg : '显示{0}条到{1}条,共{2}条',
				        // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				        emptyMsg : "没有符合条件的记录",
				        items : ['-', '&nbsp;&nbsp;', pagesize_combo
				                 ]
				    });
					// 表格工具栏****************************************************************************************
	
					
					var qForm = new Ext.form.FormPanel({
						labelWidth : 100, // 标签宽度
						frame : true, // 是否渲染表单面板背景色
						labelAlign : 'right',
						region:'north',
						// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
						buttonAlign : 'center',
						height : 80,
						autoScroll : true,
						items : [{
							layout : 'column',
							border : false,
							items : [{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '组织机构代码',
									id : 'cust_zzdm',
									name : 'cust_zzdm',
									anchor : '90%'
								} ]
							},{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户名称',
									name : 'cust_name',
									anchor : '90%'
								} ]
							}]
						}],
					buttons : [{
								text : '查询',
								handler : function() {
									var conditionStr = qForm.getForm().getFieldValues();
									Sstore.baseParams = {
											"condition" : Ext.encode(conditionStr)
										};
									Sstore.reload({
										params : {
											'product_id':Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.product_id,
											'instn_no':Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.instn_id,
											'crm_date':Ext.getCmp("listPanelTest").getSelectionModel().selections.items[0].data.crm_dt,
											start : 0,
											limit : parseInt(pagesize_combo.getValue())
										}} );
							
							   }},{
								text : '重置',
									handler : function() {
										qForm.getForm().reset();
									}
								}]
					});
					// 表格实例
					var grid = new Ext.grid.GridPanel({
								height :310,
								width : 180,
								frame : true,
								autoScroll : true,
								region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
								store : Sstore, // 数据存储
								stripeRows : true, // 斑马线
								cm : cm, // 列模型
								sm : sm, // 复选框
//								tbar : tbar, // 表格工具栏
								bbar:bbar,
								viewConfig:{
									   forceFit:false,
									   autoScroll:true
									},
								loadMask : {
									msg : '正在加载表格数据,请稍等...'
								}
							});
						



// 定义展示员工基本信息窗口
						var addGroupCustInfoWindow = new Ext.Window({
							title : '产品统计--产品销售明细列表',
							plain : true,
							layout : 'border',
							width : 900,
							height : 450,
							resizable : true,
							draggable : true,
							closable : true,
							closeAction : 'hide',
							modal : true, // 模态窗口
							loadMask : true,
							maximizable : true,
							collapsible : true,
							titleCollapse : true,
							buttonAlign : 'center',
							border : false,
							items: [qForm,grid],
							buttons : [
										 {
											text : '关闭',
										
											handler : function() {
												Sstore.removeAll();
												addGroupCustInfoWindow.hide();
											}
										} ]
						});