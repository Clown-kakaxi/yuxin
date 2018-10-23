Ext.onReady(function() {
	debugger;
	        Ext.QuickTips.init(); 

var search =new Ext.form.FormPanel({
				title:'条件查询',
				labelWidth:90,
				id:'sreach',
				frame:true,
				region:'north',
				//layout:'fit',
				labelAlign:'right',
				buttonAlign:'center',
				split:true,
				height:110,
				items:[{
					layout:'column',
					border:false,
					items:[{
						columnWidth:.25,
						layout:'form',
						items:[{							
							 xtype:'textfield',
							   fieldLabel:'客户编号',							  					
							   name:'CUST_ID',
							   anchor:'90%'
							
						}]
					},{
					   columnWidth:.25,
					   layout:'form',
					   items:[{
						   xtype:'textfield',
							fieldLabel:'客户名称',							
							name:'CUST_NAME',
							anchor:'90%'
					   }]
					},{
						   columnWidth:.25,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'产品名称',								
								name:'PROD_NAME',
								anchor:'90%'
						   }]
						
					},{

						   columnWidth:.25,
						   layout:'form',
						   items:[{
							    xtype:'combo',
								name:'ruleType1',
								triggerAction:'all',
								labelStyle : 'text-align:right;',
								anchor:'90%',
							//	lazyRender:true,
								fieldLabel:'产品状态',
								mode:'local',
								store: new Ext.data.ArrayStore({
						        id: 0,
						        fields: ['value','displayText'],
						        data: [[1, '买入'], [2, '增仓'],[3,'减仓']]
						               }),
						       valueField:'value',
						       displayField:'displayText'	
						   }]
						
					
					}]
				}],
				buttons:[{
					text:'查询',
					handler:function(){
						
					}
				},{
					text:'重置',
					handler:function(){
						search.getForm().reset();
					}
				}]
			});
			var editForm =new Ext.form.FormPanel({				
				labelWidth:90,
				id:'sreach',
				frame:true,
				layout:'fit',
				labelAlign:'right',
				buttonAlign:'center',
				split:true,
				height:80,
				items:[{
					layout:'column',
					border:false,
					items:[{
						columnWidth:.5,
						layout:'form',
						items:[{							
							 xtype:'textfield',
							   fieldLabel:'客户名称',							  					
							   name:'custName',
							   disabled:true,
							   anchor:'90%'
							
						}]
					},{
					   columnWidth:.5,
					   layout:'form',
					   items:[{
						   xtype:'textfield',
							fieldLabel:'产品名称',							
							name:'prodName',
							disabled:true,
							anchor:'90%'
					   }]
					},{

						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'购买单价',								
								name:'buyPrice',
								disabled:true,
								anchor:'90%'
						   }]
						
					
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'增仓数额',								
								name:'addNum',
								anchor:'90%'
						   }]
						
					},{

						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'购买日期',								
								name:'buyTime',
								disabled:true,
								anchor:'90%'
						   }]
						
					
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'到期日期',	
								disabled:true,
								name:'limitTime',
								anchor:'90%'
						   }]
						
					
					
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'状态',								
								name:'state1',
								value:'增仓',
								disabled:true,
								anchor:'90%'
						   }]
						
					
					
					}]
				}],
				buttons:[{
					text:'保存',
					handler:function(){
						Ext.Msg.alert("提示","操作成功！");
						win.hide();
					}
				}]
			});
			var win = new Ext.Window(
					{
						title:'产品增仓',
						width:640,
						height:260,
						closeAction:'hide',
						closable:true,
						maximizable:true,
						buttonAlign:'center',
						border:false,
						layout:'fit',
						draggable:true,
						collapsible:true,
						titleCollapse:true,
						items:[editForm]
					}
					);
			var editForm1 =new Ext.form.FormPanel({				
				labelWidth:90,
				id:'sreach',
				frame:true,
				layout:'fit',
				labelAlign:'right',
				buttonAlign:'center',
				split:true,
				//height:80,
				items:[{
					layout:'column',
					border:false,
					items:[{
						columnWidth:.5,
						layout:'form',
						items:[{							
							 xtype:'textfield',
							   fieldLabel:'客户名称',							  					
							   name:'custName',
							   disabled:true,
							   anchor:'90%'
							
						}]
					},{
					   columnWidth:.5,
					   layout:'form',
					   items:[{
						   xtype:'textfield',
							fieldLabel:'产品名称',							
							name:'prodName',
							disabled:true,
							anchor:'90%'
					   }]
					},{

						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'购买单价',								
								name:'buyPrice',
								disabled:true,
								anchor:'90%'
						   }]
						
					
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'减仓数额',								
								name:'addNum',
								anchor:'90%'
						   }]
						
					},{

						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'购买日期',								
								name:'buyTime',
								disabled:true,
								anchor:'90%'
						   }]
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'到期日期',	
								disabled:true,
								name:'limitTime',
								anchor:'90%'
						   }]
						
					
					
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'状态',								
								name:'state1',
								value:'减仓',
								disabled:true,
								anchor:'90%'
						   }]
						
					
					
					}]
				}],
				buttons:[{
					text:'保存',
					handler:function(){
						Ext.Msg.alert("提示","操作成功！");
						win1.hide();
					}
				}]
			});
			var win1 = new Ext.Window(
					{
						title:'产品减仓',
						width:640,
						height:260,
						closeAction:'hide',
						closable:true,
						maximizable:true,
						buttonAlign:'center',
						border:false,
						layout:'fit',
						draggable:true,
						collapsible:true,
						titleCollapse:true,
						items:[editForm1]
					}
					);
			var sm = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record= Ext.data.Record.create([
			                 {name:'custName'},
			                 {name:'prodName'},
			                 {name:'keepNum'},
			                 {name:'buyPrice'},
			                 {name:'buyMoney'},
			                 {name:'buyTime'},
			                 {name:'limitTime'},
			                 {name:'state'}
			                                    ]);
			debugger;
			var cm = new Ext.grid.ColumnModel([
			                  rownum,sm,
			                  {header:'客户名称',dataIndex:'custName',sortable:true,width:100},
			                  {header:'产品名称',dataIndex:'prodName',sortable:true,width:140},
			                  {header:'持有份额',dataIndex:'keepNum',align:'right',sortable:true,width:140},
			                  {header:'买入单价',dataIndex:'buyPrice',align:'right',renderer:function(val){return Ext.util.Format.usMoney(val);},sortable:true,width:150},
			                  {header:'购买金额',dataIndex:'buyMoney',align:'right',renderer:function(val){return Ext.util.Format.usMoney(val);},sortable:true,width:150},
			                  {header:'购买日期',dataIndex:'buyTime',sortable:true,width:150},
			                  {header:'到期日期',dataIndex:'limitTime',sortable:true,width:150},
			                  {header:'状态',dataIndex:'state',sortable:true,width:150}
			                               ]);
			/**
			 * 数据存储
			 */
			var data=[
			          ['张三','100G黄金','3200','1000','32000000','2012-12-12','2015-12-12','买入'],
			          ['张三','100G黄金','100','1000','1000000','2012-12-12','2015-12-12','增仓'],
			          ['张三','100G黄金','120','1000','1200000','2012-12-12','2015-12-12','减仓'],
			          ['大壮','中秋黄金100G','3000','1000','30000000','2012-09-12','2015-09-13','买入'],
			          ['张三','中秋黄金100G','1200','1000','12000000','2012-09-12','2015-09-13','买入'],
			          ['胡杰','国企黄金300','2200','1000','22000000','2012-10-09','2015-10-10','买入'],
			          ['黄钟','国企黄金300','4320','3000','14320000','2012-10-09','2015-10-10','买入'],
			          ['赵四','中秋黄金100G','220','1000','2200000','2012-09-12','2015-09-13','减仓']
			          ];
			var store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.ArrayReader({}, record)
			});

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
							[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				resizable : true,
				width : 85
			});

			// 默认加载数据
			store.load(data);

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			
			var grid = new Ext.grid.GridPanel({
				title:'数据列表',
				frame:true,
				autoScroll:true,
				region:'center',
				store:store,
				stripeRows:true,
				cm:cm,
				sm:sm,
				bbar:bbar,
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				},
				tbar:[{
					text:'增仓',
					iconCls:'addIconCss',
					handler:function(){
						var record = grid.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							var state = grid.getSelectionModel().getSelections()[0].data.state;
							if(state!='买入'){
								Ext.Msg.alert("提示","只能选择买入状态的数据");
								return;
							}
							editForm.getForm().loadRecord(record);
							win.setTitle('修改数据');
							win.show();
						}						
					}
				},{
					text:'减仓',
					iconCls:'editIconCss',
					handler:function(){
						var record = grid.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							var state = grid.getSelectionModel().getSelections()[0].data.state;
							if(state!='买入'){
								Ext.Msg.alert("提示","只能选择买入状态的数据");
								return;
							}
							editForm1.getForm().loadRecord(record);
							win1.setTitle('修改数据');
							win1.show();
						}						
					}
				},{
					text:'删除',
					iconCls:'deleteIconCss',
					handler:function(){
						var record = grid.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							var Nodes = grid.getSelectionModel().getSelections().length;
							for(var i=Nodes-1;i>=0;i--){
								var record=grid.getSelectionModel().getSelections()[i];
								store.remove(record);
							};
							Ext.Msg.alert("提示","删除成功");
						}
					}
				}]
			});
			
			var view = new Ext.Viewport({
				layout:'border',
				items:[search,grid]				
			});
});