Ext.onReady(function() {
	        Ext.QuickTips.init(); 
			var sm = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record= Ext.data.Record.create([
			                 {name:'ageWide'},
			                 {name:'lifeCycle'}
			                                    ]);
			debugger;
			var cm = new Ext.grid.ColumnModel([
			                  rownum,sm,
			                  {header:'年龄段',dataIndex:'ageWide',sortable:true,width:200,editor : new Ext.form.TextField()},
			                  {header:'生命周期',dataIndex:'lifeCycle',sortable:true,width:240,editor : new Ext.form.TextField()}
			                               ]);
			/**
			 * 数据存储
			 */
			var data=[
			          ['20~35','年轻人（积累阶段）'],
			          ['35~50','中年人（巩固增值阶段）'],
			          ['50~55','近退休人（保值保障阶段）'],
			          ['55~','退休人（享受/分配阶段）']
			          ];
			var store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.ArrayReader({}, record)
			});

			// 默认加载数据
			store.load(data);			
			
			var grid_1 = new Ext.grid.EditorGridPanel({
				frame:true,
				layout:'fit',
				height:410,
				autoScroll:true,
				region:'center',
				store:store,
				stripeRows:true,
				cm:cm,
				sm:sm,
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				},
				tbar:[{
					text:'新增',
					iconCls:'addIconCss',
					handler:function(){
						var p=new Ext.data.Record({
							ageWide:'',
							lifeCycle:''
						});
						grid_1.stopEditing();
						store.insert(0,p);
						grid_1.startEditing(0,0);
					}
				},{
					text:'删除',
					iconCls:'deleteIconCss',
					handler:function(){
						var record = grid_1.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							var Nodes = grid_1.getSelectionModel().getSelections().length;
							for(var i=Nodes-1;i>=0;i--){
								var record=grid_1.getSelectionModel().getSelections()[i];
								store.remove(record);
							};
							Ext.Msg.alert("提示","删除成功");
						}
					}
				}]
			});
			var Panel_1 = new Ext.Panel({
				layout:'fit',
				labelAlign : 'right',
				frame : true,
				buttonAlign : "center",
				items : [grid_1],
				buttons:[{
					text:'保存',
					handler:function(){}
				}]
			});
		/***************************************************************/
			var sm_2 = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum_2 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record_2= Ext.data.Record.create([
			                 {name:'custStyle'},
			                 {name:'productStyle'}
			                                    ]);
			debugger;
			var cm_2 = new Ext.grid.ColumnModel([
			                  rownum_2,sm_2,
			                  {header:'客户类型',dataIndex:'custStyle',sortable:true,width:240,editor : new Ext.form.TextField()},
			                  {header:'适合的产品类型',dataIndex:'productStyle',sortable:true,width:300,editor : new Ext.form.TextField()}
			                               ]);
			/**
			 * 数据存储
			 */
			var data_2=[
			          ['保守型','极低风险产品'],
			          ['稳健型','极低、低风险产品'],
			          ['平衡型','极低、低、中等风险产品'],
			          ['成长型','极低、低、中等、较高风险产品'],
			          ['进取型','极低、低、中等、较高及高风险产品']
			          ];
			var store_2 = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data_2),
				reader : new Ext.data.ArrayReader({}, record_2)
			});

			// 默认加载数据
			store_2.load(data_2);			
			
			var grid_2 = new Ext.grid.EditorGridPanel({
				frame:true,
				layout:'fit',
				height:410,
				autoScroll:true,
				region:'center',
				store:store_2,
				stripeRows:true,
				cm:cm_2,
				sm:sm_2,
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				},
				tbar:[{
					text:'新增',
					iconCls:'addIconCss',
					handler:function(){
						var p=new Ext.data.Record({
							custStyle:'',
							productStyle:''
						});
						grid_2.stopEditing();
						store_2.insert(0,p);
						grid_2.startEditing(0,0);
					}
				},{
					text:'删除',
					iconCls:'deleteIconCss',
					handler:function(){
						var record = grid_2.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							var Nodes = grid_2.getSelectionModel().getSelections().length;
							for(var i=Nodes-1;i>=0;i--){
								var record=grid_2.getSelectionModel().getSelections()[i];
								store_2.remove(record);
							};
		                
							Ext.Msg.alert("提示","删除成功");
						}
					}
				}]
			});
			var Panel_2 = new Ext.Panel({
				layout:'fit',
				labelAlign : 'right',
				frame : true,
				buttonAlign : "center",
				items : [grid_2],
				buttons:[{
					text:'保存',
					handler:function(){}
				}]
			});
	/*****************************************************************/		
			var sm_3 = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum_3 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});		
			
		var continentGroupRow = [
		                         {header: '', colspan: 3, align: 'center'},
		                         {header: '年轻投资者', colspan: 3, align: 'center'},
		                         {header: '中年投资者', colspan: 3, align: 'center'},
		                         {header: '近退休投资者', colspan: 3, align: 'center'},
		                         {header: '退休投资者', colspan: 3, align: 'center'}
		];
		    var group = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow]
		    });		    
			var record_3= Ext.data.Record.create([
			                 {name:'riskStyle'},
			                 {name:'stock_1'},
			                 {name:'bond_1'},
			                 {name:'cash_1'},
			                 {name:'stock_2'},
			                 {name:'bond_2'},
			                 {name:'cash_2'},
			                 {name:'stock_3'},
			                 {name:'bond_3'},
			                 {name:'cash_3'},
			                 {name:'stock_4'},
			                 {name:'bond_4'},
			                 {name:'cash_4'}
			                                    ]);
			debugger;
			var cm_3 = new Ext.grid.ColumnModel([
			                  rownum_3,sm_3,
			                  {header:'风险类型型',dataIndex:'riskStyle',sortable:true,width:100},
			                  {header:'股票',dataIndex:'stock_1',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'债券',dataIndex:'bond_1',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'现金',dataIndex:'cash_1',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'股票',dataIndex:'stock_2',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'债券',dataIndex:'bond_2',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'现金',dataIndex:'cash_2',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'股票',dataIndex:'stock_3',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'债券',dataIndex:'bond_3',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'现金',dataIndex:'cash_3',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'股票',dataIndex:'stock_4',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'债券',dataIndex:'bond_4',sortable:true,width:100,editor : new Ext.form.TextField()},
			                  {header:'现金',dataIndex:'cash_4',sortable:true,width:100,editor : new Ext.form.TextField()}
			                  ]);
			/**
			 * 数据存储
			 */
			var data_3=[
			          ['低风险','35%','30%','35%','30%','25%','45%','25%','21%','54%','30%','25%','41%'],
			          ['中等风险','55%','40%','5%','50%','40%','10%','55%','40%','5%','40%','33%','27%'],
			          ['高风险','70%','25%','5%','65%','30%','5%','65%','32%','3%','50%','40%','10%']
			          ];
			var store_3 = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data_3),
				reader : new Ext.data.ArrayReader({}, record_3)
			});

			// 默认加载数据
			store_3.load(data_3);			
			
			var grid_3 = new Ext.grid.EditorGridPanel({
				frame:true,
				layout:'fit',
				height:410,
				autoScroll:true,
				region:'center',
				store:store_3,
				stripeRows:true,
				cm:cm_3,
				sm:sm_3,
				plugins: group,
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				},
				tbar:[{
					text:'新增',
					iconCls:'addIconCss',
					handler:function(){
						var p=new Ext.data.Record({
							custStyle:'',
							productStyle:''
						});
						grid_3.stopEditing();
						store_3.insert(0,p);
						grid_3.startEditing(0,0);
					}
				},{
					text:'删除',
					iconCls:'deleteIconCss',
					handler:function(){
						var record = grid_3.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
							var Nodes = grid_3.getSelectionModel().getSelections().length;
							for(var i=Nodes-1;i>=0;i--){
								var record=grid_3.getSelectionModel().getSelections()[i];
								store_3.remove(record);
							};
							Ext.Msg.alert("提示","删除成功");
						}
					}
				}]
			});
			var Panel_3 = new Ext.Panel({
				layout:'fit',
				labelAlign : 'right',
				frame : true,
				buttonAlign : "center",
				items : [grid_3],
				buttons:[{
					text:'保存',
					handler:function(){}
				}]
			});
			var addTapPanel = new Ext.TabPanel({
				activeTab : 0,
				tabPosition : 'top',// 控制tab页签显示的位置（顶部：top；底部：bottom）
				//height : 440,
				//layout:'fit',
				buttonAlign : "center",
				items : [{
							title : '年龄段',
							items : [Panel_1]
						}, {
							title : '客户类型',
							items : [Panel_2]
						},{
							title:'投资比例',
							items:[Panel_3]
						}]
			});

	
			var view = new Ext.Viewport({
				layout:'fit',
				items:[addTapPanel]				
			});
});