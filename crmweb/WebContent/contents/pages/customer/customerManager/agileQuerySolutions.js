
/**
 * 灵活查询方案列表面板
 */

/**
 * 新增方案名称面板
 */
var search = new Ext.FormPanel({
	frame:true,
	width: 120,
	items: [{
		autoHeight:true,
		items :[ {
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : .99,
				labelWidth : 80,
				items : [ {
					xtype : 'textfield',
					fieldLabel : '方案名称',
					name : 'solutionName',
					id:'solutionNameId',
					allowBlank : false,
					labelStyle : 'text-align:right;',
					anchor : '99%'
				} ]
			} ]
		} ]
	}]
});
/**
 * 新增方案名称窗口
 */
var addSolutionWindow = new Ext.Window({
	layout : 'fit',
	width : 260,
	height : 150,
	draggable : true,//是否可以拖动
	closable : true,// 是否可关闭
	modal : true,
	closeAction : 'hide',
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	animCollapse : true,
	animateTarget : Ext.getBody(),
	constrain : true,
	items : [search],
	buttons : [ {
		text : '确认',
		handler : function() {
			if(!search.getForm().isValid()){
				Ext.Msg.alert('提示', '请输入名称！');
				return;
			}
			fnBatchSave();
			search.getForm().reset(); 
			addSolutionWindow.hide();
		}
	}, {
		text : '关闭',
		handler : function() {
			search.getForm().reset();
			addSolutionWindow.hide();
		}
	} ]
});
		/**
		 * 查询方案列表
		 */

		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28,
			hidden : true
		});
		
	    var sm = new Ext.grid.CheckboxSelectionModel();

		// 定义列模型
		var cm = new Ext.grid.ColumnModel( [ rownum, sm,{
				header : '查询结果',
				dataIndex : 'SS_RESULT',
				hidden : true,
				hideable:false,
				sortable : true,
				width : 100
			}, {
				header : '方案ID',
				dataIndex : 'ID',
				hideable:false,
				hidden : true,
				sortable : true,
				width : 100
			}, {
				header : '方案名称',
				dataIndex : 'SS_NAME',
				sortable : true,
				width : 100
			}, {
				header : '创建人',
				dataIndex : 'SS_USER',
				sortable : true,
				width : 75
			}, {
				header : '创建机构',
				dataIndex : 'custId',
				sortable : true,
				width : 100
			} ]);

		/**
		 * 数据存储
		 */
		var store = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath+'/queryagilequery!queryAgileSolution.json'
			}),
		  reader: new Ext.data.JsonReader({
		        root:'JSON.data'
		        }, [
		            {name: 'ID'},
					{name: 'SS_USER'},
					{name: 'SS_SORT'},
					{name:'SS_NAME'},
					{name:'SS_RESULT'}
					
				])
		});

		var tbar = new Ext.Toolbar({
			items : [ {
				text : '新增',
				iconCls:'addIconCss',
				handler : function() {
					simple.removeAllItems();
					simple2.removeAllItems();
					right_panel.currentSolutionsId = false;
				}
			}, '-', {
				text : '删除',
				iconCls:'deleteIconCss',
				handler : function() {
					fnConditionDelete();
				}
			} ]
		});

		// 表格实例
		var grid = new Ext.grid.GridPanel({
			height : document.body.scrollHeight-60,
			width:'100%',
			frame : true,
			autoScroll : true,
			store : store, // 数据存储
			stripeRows : true, // 斑马线
			cm : cm, // 列模型
			sm : sm,
			tbar : tbar, // 表格工具栏
			viewConfig : {
				forceFit : false,
				autoScroll : true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
		grid.on('rowdblclick', function(grid, rowIndex, event) {
			
			var selectLength = grid.getSelectionModel().getSelections().length;
			var selectRe = grid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录!');
				return;
			}
			
			simple.removeAllItems();
			simple2.removeAllItems();
			var ssResult=selectRe.data.SS_RESULT.split(",");
			var rankResult = selectRe.data.SS_SORT.split(",");
			for(var rIndex = 0 ;rIndex<ssResult.length; rIndex++){
				var node = treeOfPoroduct.root.findChild("id", "b"+ssResult[rIndex], true);
				if(node){
					simple2.addItems(node,rankResult[rIndex]);
				}
			}
			id=0;
			right_panel.currentSolutionsId = selectRe.data.ID;
			Ext.Ajax.request({
				url:basepath+'/queryagilequery!queryAgileCondition.json?SS_ID='+selectRe.data.ID,
				method: 'GET',
				success : function(response) {
					var conditionData = Ext.util.JSON.decode(response.responseText);
					var conditionArray=conditionData.JSON.data;
					if(conditionArray.length>0){
						Ext.each(conditionArray,function(con){
							var node = treeOfPoroduct.root.findChild("id", "b"+con.SS_COL_ITEM, true);
							if(node){
								simple.addItems(node,con.SS_COL_OP,con.SS_COL_VALUE);
							}
						});
						if(conditionArray[0].SS_COL_JOIN=='true'){
							radio.items.items[0].items.items[0].setValue(true);
							right_panel.conditionJoinType = 'true';
						}else{
							radio.items.items[1].items.items[0].setValue(true);
							right_panel.conditionJoinType = 'false';
						}
					}
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示','您没有此权限!');
					} else {
						Ext.Msg.alert('提示','操作失败!');
					}
				}
			});
		});
		store.load();
		
		//保存方案
		var fnBatchSave= function(){
			var selectRe = grid.getSelectionModel().getSelections()[0];
			var solutionID=  right_panel.currentSolutionsId? right_panel.currentSolutionsId:'';
			var solutionAttr = {};
			solutionAttr.solutionName= Ext.getCmp("solutionNameId").getValue();
			solutionAttr.ss_results = simple2.getResultsIds();
			solutionAttr.ss_sort = simple2.getSortTypes();
			var conditions = simple.getConditionsAttrs();
			Ext.Ajax.request({
				url:basepath+'/agilesearch.json',
				method: 'POST',
				success : function(response) {
					Ext.Msg.alert('提示', '操作成功');
					store.reload();
					Ext.Ajax.request({
						url: basepath+'/session-info!getPid.json',
						method:'GET',
						success:function(response){
							right_panel.currentSolutionsId = Ext.decode(response.responseText).pid;
						}
					});
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示','您没有此权限!');
					} else {
						Ext.Msg.alert('提示','操作失败!');
					}
				},
				params : {
					solutionAttr:Ext.encode(solutionAttr),
					conditionCols : Ext.encode(conditions),
					solutionID:solutionID,
					'radio':right_panel.conditionJoinType
				}
			});
		};
		//删除方案
		var fnConditionDelete= function(){
			var selectLength = grid.getSelectionModel().getSelections().length;
		 	var checkedNodes = grid.getSelectionModel().selections.items;
				if(checkedNodes.length==0){
					Ext.Msg.alert('提示', '未选择任何客户');
					return ;
				}
				var json='';
				for(var i=0;i<checkedNodes.length;i++)
				{
					if(i==0){
						json = checkedNodes[i].data.ID;
					}else {
						json += ',' + checkedNodes[i].data.ID;
					}
				}
				Ext.Ajax.request({
					url:basepath+'/agilesearch.json',
                    method: 'POST',
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功!');
						store.reload();
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示','您没有此权限!');
						} else {
							Ext.Msg.alert('提示','加入失败!');
						}
					},
					params : {
						'solutionID': json
					}
				});
		};