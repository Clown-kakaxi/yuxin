Ext.onReady(function() {
	debugger;
	        Ext.QuickTips.init(); 

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
			                 {name:'villageWide'}
			                                    ]);
			debugger;
			var cm = new Ext.grid.ColumnModel([
			                  rownum,sm,
			                  {header:'日期',dataIndex:'dataTime',sortable:true,align:'right',width:100,editor : new Ext.form.DateField({format:'Y-m'})},
			                  {header:'国房景气指数',dataIndex:'countryWide',align:'right',sortable:true,width:120,editor : new Ext.form.TextField()},
			                  {header:'土地开发面积指数',dataIndex:'cityWide',align:'right',sortable:true,width:150,editor : new Ext.form.TextField()},
			                  {header:'销售指数',dataIndex:'villageWide',align:'right',sortable:true,width:100,editor : new Ext.form.TextField()}
			                               ]);
			/**
			 * 数据存储
			 */
			var data=[
			          ['2010-01','110','121','110'],
			          ['2010-05','130','142','100'],
			          ['2010-09','124','135','124'],
			          ['2011-02','123','152','143'],
			          ['2011-07','115','122','138'],
			          ['2011-11','152','110','129'],
			          ['2012-01','132','130','142']
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
			
			var grid = new Ext.grid.EditorGridPanel({
				title:'房屋价格指数列表',
				frame:true,
				autoScroll:true,
				region:'center',
				store:store,
				stripeRows:true,
				cm:cm,
				sm:sm,
				bbar:bbar,
				tbar:[{
					text:'新增',
					iconCls:'addIconCss',
					handler:function(){
						var p=new Ext.data.Record({
							dataTime:'',
			                 countryWide:'',
			                 cityWide:'',
			                 villageWide:''
						});
						grid.stopEditing();
						store.insert(0,p);
						grid.startEditing(0,0);
					}
				},'-',{
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
				},'-',{
					text:'保存',
					iconCls:'saveIconCss',
					handler:function(){
						
					}
				}],
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				}
			});
//			grid.on('celldblclick',function(grid,rowIndex,columnIndex,e){
//				if(columnIndex>2){
//					var record = grid.getStore().getAt(rowIndex); //Get the Record 
//					var fieldName = grid.getColumnModel().getDataIndex(columnIndex); //Get field name 
//					var data = record.get(fieldName); 
//					var time = record.get('dataTime');
//					//Ext.MessageBox.alert('show','当前选中的数据是'+data+"日期"+time); 
//					Ext.getCmp('data').setValue(data);
//					Ext.getCmp('dataTime').setValue(time);
//				}
//			});
			var panel=new Ext.form.FormPanel({
				title:'房屋价格趋势图',
				frame:true,
				split:true,
				maximizable:true,
				width:600,
				buttonAlign:'center',
				region:'west',
				html:'<iframe id="contentFrame1" name="content1" style="width:100%;height:100%" frameborder="no"  src=\"houseDataView.html\"/>   scrolling="no"> </iframe>'
			});
			
			var view = new Ext.Viewport({
				layout:'fit',
				items:[{
					layout:'border',
					items:[
					       { region : 'center',
				    	   layout : 'border',
				    	   items : [panel, grid ]}]
				}]
			});
});