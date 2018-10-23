Ext.onReady(function() {
	debugger;
	        Ext.QuickTips.init(); 

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
							   fieldLabel:'教育阶段',							  					
							   name:'dataTime',
							   anchor:'90%'
							
						}]
					},{
					   columnWidth:.5,
					   layout:'form',
					   items:[{
						   xtype:'textfield',
							fieldLabel:'年龄范围(开始)',							
							name:'countryWide',
							anchor:'90%'
					   }]
					},{
						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'年龄范围(结束)',								
								name:'cityWide',
								anchor:'90%'
						   }]
						
					},{

						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'平均费用(元/年)',								
								name:'villageWide',
								anchor:'90%'
						   }]
						
					
					},{


						   columnWidth:.5,
						   layout:'form',
						   items:[{
							   xtype:'textfield',
								fieldLabel:'合计费用(元)',								
								name:'costAll',
								anchor:'90%'
						   }]
						
					
					
					}]
				}],
				buttons:[{
					text:'保存',
					handler:function(){
						
					}
				}]
			});
			var win = new Ext.Window(
					{
						title:'数据修改',
						width:540,
						height:200,
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
			var sm = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record= Ext.data.Record.create([
			                 {name:'dataTime'},
			                 {name:'countryWide'},
			                 {name:'cityWide'},
			                 {name:'villageWide'},
			                 {name:'costAll'}
			                                    ]);
			debugger;
			var cm = new Ext.grid.ColumnModel([
			                  rownum,sm,
			                  {header:'教育阶段',dataIndex:'dataTime',align:'right',sortable:true,width:100},
			                  {header:'年龄范围(开始)',dataIndex:'countryWide',align:'right',sortable:true,width:200},
			                  {header:'年龄范围(结束)',dataIndex:'cityWide',align:'right',sortable:true,width:200},
			                  {header:'平均费用(元/年)',dataIndex:'villageWide',align:'right',sortable:true,width:200},
			                  {header:'合计费用(元)',dataIndex:'costAll',align:'right',sortable:true,width:200}
			                               ]);
			/**
			 * 数据存储
			 */
			var data=[
			          ['幼儿','4','6','2000','6000'],
			          ['小学','6','12','700','4200'],
			          ['初中','12','15','500','1500'],
			          ['高中','15','18','600','1800'],
			          ['大学','18','22','6000','24000'],
			          ['研究生','22','25','5000','15000'],
			          ['博士生','25','28','4000','12000']
			     
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
				title:'国内教育费用数据列表',
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
					text:'新增',
					iconCls:'addIconCss',
					handler:function(){
						win.setTitle('新增数据');
						win.show();
					}
				},{
					text:'修改',
					iconCls:'editIconCss',
					handler:function(){
						var record = grid.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							editForm.getForm().loadRecord(record);
							win.setTitle('修改数据');
							win.show();
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
				layout:'fit',
				items:[grid]				
			});
});