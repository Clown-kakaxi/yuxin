Ext.onReady(function() {
	debugger;
	var qfrom = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '客户名称',
				name : 'q1',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '创建日期从',
				name : 'q1',
				xtype : 'datefield',
				 format : 'Y-m-d',
				anchor : '90%'
			}]
		},{
			columnWidth : .25,
			layout : 'form',
			items : [{
				fieldLabel : '创建日期到',
				name : 'q1',
				xtype : 'datefield', 
				 format : 'Y-m-d',
				anchor : '90%'
			}]
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '查询',
			handler : function() {}},
			{
				text : '重置',
			     handler : function() {
			    	 qfrom.form.reset();
						}
			}]
});
	
	var fields = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}, {
		name : 'a3'
	}, {
		name : 'a4'
	}];

	var num = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	var columns = new Ext.grid.ColumnModel([num, sm,{
		dataIndex : 'a0',
		header : '报告名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '客户姓名',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '创建日期',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a3',
		header : '目标开始日期',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a4',
		header : '备注',
		sortable : true,
		width : 140
	}]);

	var data = [
	            ['李晓丽投资规划','李晓丽','2013-08-26','2013-08-26','李晓丽房规划'],
	            ['李四投资规划','李四','2013-07-24','2013-08-26','李四投资规划'],
	            ['五XX投资规划','五XX','2013-08-26','2013-08-26','五XX投资规划'],
	            ['张XX投资规划','张XX','2013-07-26','2013-08-26','张XX投资规划'],
	            ['软三投资规划','软三','2013-11-25','2013-08-26','软三投资规划']
	           
	];



	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});
	var combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
					fields : ['value', 'text'],
					data : [[10, '10条/页'], [20, '20条/页'],
							[50, '50条/页'], [100, '100条/页'],
							[250, '250条/页'], [500, '500条/页']]
				}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var number = parseInt(combo.getValue());

	//分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', combo]
	});

	  var blocViewRecord = new Ext.data.Record.create(
			    [
			    	{
			    		name:'viewItem'
			   		}
			    ]
			    );
			    var blocViewReader = new Ext.data.JsonReader({
			    	root:'rows'
			    },blocViewRecord);
			    
			    var blocViewStore = new Ext.data.Store(
			    {
			    	reader:blocViewReader
			    }
			    );
			    var blocViewData ={
			     rows:[
			     {viewItem:'规划创建'},
			     {viewItem:'投资方式'},
			     {viewItem:'资产配置'},
			     {viewItem:'产品推荐'}
			     ]
			    };
	blocViewStore.loadData(blocViewData);
	
	
	
	 var leftPanel = new Ext.grid.GridPanel({

	    	columns:[
	    	{
	    		header:'投资规划',
	    		id:'viewItem',
	    		align:'center',
	    		renderer:function(value)
	    		{
	    			return "<a href='#' onClick='gotoBlocView( "+"\""+value+"\""+" )'>"+value+"</a>";	
	    		}
	    	}
	    	],
	    	autoExpandColumn:'viewItem',
	    	store:blocViewStore
	    });
	 
	var win = new Ext.Window(
			{
				layout : 'fit',
				draggable : true,// 是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				maximized : true,// 默认最大化
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [ {
					layout : 'border',
					items : [
							{
								region : 'west',
								id : 'west-panel',
								split : true,// 是否可拖动
								width : 200,
								minSize : 175,
								maxSize : 400,
								collapsible : true,// 是否有伸缩按钮
								margins : '0 0 0 5',
								layout : 'fit',
								items : [ leftPanel ]
							},
							{
								region : 'center',
								id : 'center-panel',
								title : "",
								html : '<iframe src="planCreate.jsp" frameborder="0" scrolling="yes" id="blocViewFrame" name="blocViewFrame" width="100%" height="100%"></iframe>'
							} ]
				} ]
			});
	
		var grid = new Ext.grid.GridPanel({
			title:'投资规划列表',
			region : 'center',
			frame : true,
			store : store,
			stripeRows : true,
			sm:sm,
			cm : columns,
			tbar:[
//			      {
//	            text : '详情',
//	            iconCls:'detailIconCss',
//	            handler:function() {
//	            	var records = grid.selModel.getSelections();// 得到被选择的行的数组
//	            	var selectLength = records.length;
//	                 if (selectLength!=1) {
//	                 	alert("请选择一条记录");
//	                     return false;
//	                 }
//	                 win.show();
//	                 
//	        }}
{
				 text : '新增',
				 iconCls:'addIconCss',
		            handler:function() {
		                 addWindow.show();
		                 cardPanel.getLayout().setActiveItem(0);
		        }
},'-',{
	text:'编辑',
	iconCls:'editIconCss',
	handler:function() {
		var records = grid.selModel.getSelections();// 得到被选择的行的数组
    	var selectLength = records.length;
			if (selectLength != 1) {
				Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
			} else {
				addWindow.show();
                 cardPanel.getLayout().setActiveItem(0);
			}
		}
},'-',{
	text:'删除',
	iconCls:'deleteIconCss',
	handler:function() {
		var records = grid.selModel.getSelections();// 得到被选择的行的数组
    	var selectLength = records.length;
			if (selectLength < 1) {
				Ext.Msg.alert("系统提示信息", "请选择记录！");
			} else {
				Ext.Msg.alert("系统提示信息", "删除成功！");
			}
		}
},'-',{
	    		text:'下载报告',
	    		iconCls:'detailIconCss',
	    		handler:function() {
	    			var records = grid.selModel.getSelections();// 得到被选择的行的数组
	            	var selectLength = records.length;
						if (selectLength < 1) {
							Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
						} else {
							var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'+ ' scrollbars=no, resizable=no,location=no, status=no';
	    					var fileName = 'readme.txt';// 电子杂志文件名称
	    					var uploadUrl = basepath + '/AnnexeDownload?filename='+ fileName + '&annexeName=' + fileName;
	    					window.open(uploadUrl, '', winPara);
						}
					}
				}
			      ],
			bbar:bbar
	});
		
	//页面布局
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [{
				region : 'north',
				id : 'north-panel',
				title : "规划查询",
				height : 100,
				layout : 'fit',
				items : [qfrom]
			},{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [grid]
					}
			]
		} ]
	});
});