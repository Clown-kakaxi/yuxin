	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	
	var traceColumns = new Ext.grid.ColumnModel([rownum,{
		header : 'id', 
		dataIndex : 'id',
		sortable : true,
		width : 150,
		hidden:true
	},
	                                             {
							header : '商机阶段',
							width : 170,
							align : 'center',
							dataIndex : 'opportunityStage',
							sortable : true
						}, {
							header : '商机工作描述',
							width : 200,
							align : 'center',
							editor:new Ext.form.Field(),
							dataIndex : 'stageDesc',
							sortable : true
						},{
							header : '完成日期',
							width : 150,
							align : 'center',
							renderer:Ext.util.Format.dateRenderer('Y-m-d'),
							editor:new Ext.form.DateField({
								format : 'Y-m-d',
								forceSelection : true
							}),
							dataIndex : 'stageCompleteDate',
							sortable : true
						},{
							header : '更新日期',
							width : 150,
							align : 'center',
							dataIndex : 'updateDate',
							sortable : true
						}]
			);

	var traceRecord = Ext.data.Record.create([ {
				name : 'id',mapping : 'ID'
			},{
				name : 'opportunityStage',mapping : 'OPPOR_STAGE'
			},{
				name : 'stageCompleteDate',mapping : 'STAGE_COM_DATE',type:'date',dateFormat:'Y-m-d'
			},{
				name : 'updateDate',mapping : 'UPDATE_DATE'
			},{
				name : 'stageDesc',mapping : 'STAGE_DESC'
			}
			]);	
	
	var traceStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/marketOppStageQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, traceRecord)
	});
	
    var tracePanel = new Ext.grid.EditorGridPanel({
			height : 250,
			title:'商机跟踪',
			store : traceStore,
			frame : true,
			cm : traceColumns,
			stripeRows : true,
			clicksToEdit : 1
		});
		
	var traceWindow = new Ext.Window({
		closeAction:'hide',
		buttonAlign:'center',
		height:300,
		width:750,
		buttons:[
		{
			text:'保存',
			handler:function()
			{
				var allRecorde = tracePanel.store;
				var allLength = allRecorde.getCount();
				
				var json={'id':[]};
				var json2={'stageCompleteDate':[]};
			 	var json3={'stageDesc':[]};
			 	var json4={'opportunityStage':[]};
			 	
			 	for(var i=0;i<allLength;i++)
				{
					json.id.push(allRecorde.getAt(i).get("id"));
					json2.stageCompleteDate.push(allRecorde.getAt(i).get("stageCompleteDate"));
					json3.stageDesc.push(allRecorde.getAt(i).get("stageDesc"));
					json4.opportunityStage.push(allRecorde.getAt(i).get("opportunityStage"));
				}
			 	
				Ext.Ajax.request({
					url:basepath+'/market-opp-stage!brachUpdate.json',
					method : 'GET',
					params : {
						'idJson':Ext.encode(json),
						'stageCompleteDateJson': Ext.encode(json2),
						'stageDescJson': Ext.encode(json3),
						'opportunityStageJson':Ext.encode(json4),
						
						'opportunityStageId': document.getElementById("marketOpporIdStr").value
					},
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功');
					},
					failure : function(response) {
						Ext.Msg.alert('提示','操作失败' );
					}
					});
				traceWindow.hide();
			}
		},
		{
			text:'返回',
			handler:function()
			{
				traceWindow.hide();
			}
		}
		],
		items:tracePanel
	});
		