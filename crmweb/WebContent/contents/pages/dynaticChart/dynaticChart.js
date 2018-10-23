Ext.onReady(function(){
	/**
	 * 主题
	 */
	 var majorStore = new Ext.data.JsonStore({
	        fields : ['key', 'value'],
	        data :major
	});
	
	 var inlcStore = new Ext.data.JsonStore({
	        fields : ['key', 'value'],
	        data :incl
	});
	
	 var chartTypeStore = new Ext.data.JsonStore({
	        fields : ['key', 'value'],
	        data :chartType
	});
	 
	 var tinaTypeStore = new Ext.data.JsonStore({
	        fields : ['key', 'value'],
	        data :tinaType
	});
	 
	 var targetStore = new Ext.data.JsonStore({
	        fields : ['index', 'type', 'name'],
	        data : []
	});
	 
	 var resultStore = new Ext.data.JsonStore({
	        fields : ['index', 'type', 'name'],
	        data :[]
	});
	 
	 var pieStore = new Ext.data.JsonStore({
	        fields : ['target', 'number', 'rate'],
	        data : pieData
	});
	 
	 var columnStore = new Ext.data.JsonStore({
	        fields : ['target', 'number'],
	        data : columnData
	});
	 
	 var lineStore = new Ext.data.JsonStore({
	        fields : ['target', 'number1','number2','number3','number4','number5','number6'],
	        data : lineData
	});
	 
	 var pieGrid = new Ext.grid.GridPanel({
		 hidden :true,
		 store : pieStore,
		 height : 200,
		 frame : true,
		 columns :[{
			 id : 'target',
			 header : '指标',
			 dataIndex : 'target'
		 },{
			 id : 'number',
			 header : '客户数',
			 dataIndex : 'number'
		 },{
			 id : 'rate',
			 header : '占比',
			 dataIndex : 'rate'
		 }]
	 });
	 
	 var columnGrid = new Ext.grid.GridPanel({
		 hidden :true,
		 store : columnStore,
		 height : 200,
		 frame : true,
		 columns :[{
			 id : 'target',
			 header : '指标',
			 dataIndex : 'target'
		 },{
			 id : 'number',
			 header : '客户数',
			 dataIndex : 'number'
		 }]
	 });
	 
	 var lineGrid = new Ext.grid.GridPanel({
		 hidden :true,
		 store : lineStore,
		 height : 200,
		 frame : true,
		 columns :[{
			 id : 'target',
			 header : '指标',
			 dataIndex : 'target'
		 },{
			 id : 'number1',
			 header : '一月',
			 dataIndex : 'number1'
		 },{
			 id : 'number2',
			 header : '二月',
			 dataIndex : 'number2'
		 },{
			 id : 'number3',
			 header : '三月',
			 dataIndex : 'number3'
		 },{
			 id : 'number4',
			 header : '四月',
			 dataIndex : 'number4'
		 },{
			 id : 'number5',
			 header : '五月',
			 dataIndex : 'number5'
		 },{
			 id : 'number6',
			 header : '六月',
			 dataIndex : 'number6'
		 }]
	 });
	 
	 var targetGrid = new Ext.grid.GridPanel({
		 title : '可选指标',
		 store : targetStore,
		 height : 200,
		 frame : true,
		 ddGroup : 'result',
		 enableDragDrop : true,
		 columns :[{
			 id : 'index',
			 header : '次序',
			 dataIndex : 'index',
			 hidden : true
		 },{
			 id : 'type',
			 header : '指标类型',
			 dataIndex : 'type'
		 },{
			 id : 'name',
			 header : '指标名称',
			 dataIndex : 'name'
		 }]
	 });
	 
	 var resultGrid = new Ext.grid.GridPanel({
		 title : '统计指标',
		 store : resultStore,
		 frame : true,
		 height : 200,
		 ddGroup : 'target',
		 enableDragDrop : true,
		 columns :[{
			 id : 'index',
			 header : '次序',
			 dataIndex : 'index',
			 hidden : true
		 },{
			 id : 'type',
			 header : '指标类型',
			 dataIndex : 'type'
		 },{
			 id : 'name',
			 header : '指标名称',
			 dataIndex : 'name'
		 }]
	 });
	 
	 
	var searchInfo = new Ext.form.FormPanel({
		region:'north',
		height:266,
		frame:true,
		labelAlign:'right',
		buttons : [{
			text : '生成',
			handler : function(){
				var type = Ext.getCmp('tubiaoleixing').getValue();
				if(!type){
					Ext.Msg.alert('提示','请选择图标类型！');
					return;
				}
				if(type == 'pie'){
					var pieChart = new FusionCharts( basepath+"/FusionCharts/Pie3D.swf", Ext.id(), "100%", "100%", "0", "0");
					pieChart.setXMLUrl(basepath + "/contents/pages/dynaticChart/pie.xml");
					pieChart.render(chartView.body.id);
					pieGrid.show();
					columnGrid.hide();
					lineGrid.hide();
				}else if(type == 'tie'){
					var MSColumnChart = new FusionCharts( basepath+"/FusionCharts/MSColumn2D.swf", Ext.id(), "100%", "100%", "0", "0");
					MSColumnChart.setXMLUrl(basepath + "/contents/pages/dynaticChart/mscolumn.xml");
					MSColumnChart.render(chartView.body.id);
					pieGrid.hide();
					columnGrid.show();
					lineGrid.hide();
				}else if(type == 'qushi'){
					var MSLineChart = new FusionCharts( basepath+"/FusionCharts/MSLine.swf",Ext.id(), "100%", "100%", "0", "0");
					MSLineChart.setXMLUrl(basepath + "/contents/pages/dynaticChart/msline.xml");
					MSLineChart.render(chartView.body.id);
					pieGrid.hide();
					columnGrid.hide();
					lineGrid.show();
				}else if(type == 'rada'){
					
				}
			}
		}],
		items : [{
			xtype:'fieldset',
			bodyStyle : 'float:left;',
			layout:'column',
			items:[{
				title : '统计选项',
				frame : true,
				columnWidth :.3,
				layout:'form',
				items : [{
		            xtype : 'combo',
		            fieldLabel : '主题类型',
		            mode:'local',
		            emptyText:'请选择',
		            store:majorStore,
		            triggerAction:'all',
		            valueField:'key',
		            editable : false,
		            displayField:'value',
		            name : 'isRead',
		            anchor : '80%'    
		        },{
		            xtype : 'combo',
		            fieldLabel : '统计范围',
		            mode:'local',
		            emptyText:'请选择',
		            store:inlcStore,
		            triggerAction:'all',
		            valueField:'key',
		            editable : false,
		            displayField:'value',
		            name : 'isRead',
		            anchor : '80%',
		            listeners : {
						select : function(combo,record,index){
							if(index == 0 ){
								targetStore.removeAll();
								targetStore.loadData(targets.orgTargets);
								resultStore.removeAll();
							}else if(index == 1){
								targetStore.removeAll();
								targetStore.loadData(targets.userTargets);
								resultStore.removeAll();
							}else if(index == 2){
								targetStore.removeAll();
								targetStore.loadData(targets.custTargets);
								resultStore.removeAll();
							}else {
								targetStore.removeAll();
								resultStore.removeAll();
							}
						}
					}	 
		        },{
		            xtype : 'combo',
		            fieldLabel : '图表类型',
		            id : 'tubiaoleixing',
		            mode:'local',
		            emptyText:'请选择',
		            store:chartTypeStore,
		            triggerAction:'all',
		            valueField:'key',
		            editable : false,
		            displayField:'value',
		            name : 'isRead',
		            anchor : '80%'    
		        },{
		            xtype : 'combo',
		            fieldLabel : '统计粒度',
		            mode:'local',
		            emptyText:'请选择',
		            store:tinaTypeStore,
		            triggerAction:'all',
		            valueField:'key',
		            editable : false,
		            displayField:'value',
		            name : 'isRead',
		            anchor : '80%'    
		        },{
		        	xtype : 'datefield',
		        	fieldLabel : '统计周期',
		        	anchor : '80%'    
		        },{
					xtype : 'datefield',
					fieldLabel : '至',
					anchor : '80%'    
				}]
			},{
				columnWidth : .35,
				layout:'form',
				style : 'text-align:center;',
				items:[targetGrid]
			},{
				columnWidth : .35,
				layout:'form',
				style : 'text-align:center;',
				items:[resultGrid]
			}]
		}]
	});
	
	var chartView = new Ext.Panel({
		region : 'center',
		height : 300,
		width : '100%',
		title : '图形',
		id : 'chartview'
	});
	var gridView = new Ext.Panel({
		region : 'south',
		height : 300,
		width : '100%',
		title : '统计数据',
		items : [pieGrid,columnGrid,lineGrid
		         ]
	});
	
	var d = new Ext.Viewport({
		layout:'form',
		items:[{
			xtype : 'panel',
			layout : 'form',
			autoScroll : true,
			height : Ext.getBody().getViewSize().height,
			items : [ searchInfo,chartView,gridView ]
		}]
		
	});
	
	var domtarget = targetGrid.el.dom;
	var domresult = resultGrid.el.dom;
	
	var dropheng = new Ext.dd.DropTarget(domtarget, {
		ddGroup     : 'target',
		notifyEnter : function(ddSource, e, data) {},
		notifyDrop  : function(ddSource, e, data) {
			targetStore.add(data.selections);
			resultStore.remove(data.selections);
		}
	});
	
	var dropzong = new Ext.dd.DropTarget(domresult, {
		ddGroup     : 'result',
		notifyEnter : function(ddSource, e, data) {},
		notifyDrop  : function(ddSource, e, data) {
			resultStore.add(data.selections);
			targetStore.remove(data.selections);
		}
	});
	
	
});