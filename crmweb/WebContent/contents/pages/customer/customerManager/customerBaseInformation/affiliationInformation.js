Ext.onReady(function() {

	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, 
	        {header : '机构号', dataIndex : 'INSTN_NO',sortable : true,align :'right'},
	        {header : '机构名称',dataIndex : 'UNITNAME',sortable : true,align :'right'},
	        {header : '是否归属网点',dataIndex : 'GS_FLG',sortable : true,align :'right'}
	        
			]);

	/**
	 * 数据存储
	 */
		var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/queryaffiliationinformation2.json?customerId='+parent.location.href.split("customerId=")[1]
		  }
		  ),
	       reader: new Ext.data.JsonReader({
	       totalProperty : 'json.count',
	        root:'json.data'
	        }, [
	            {name: 'INSTN_NO'},
				{name: 'UNITNAME'},
				{name: 'GS_FLG'}
			])
		});


	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [ [100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
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
	var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo
								]
					});
	var loanpagesize_combo = new Ext.form.ComboBox({
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
						editable : false,
						width : 85
					});
	
	var loannumber = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
			loanpagesize_combo.on("select", function(comboBox) {
						loanbbar.pageSize = parseInt(comboBox.getValue());
						loannumber = parseInt(comboBox.getValue());
						store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});
					// 每页显示条数下拉选择框
			
	// 分页工具栏
	var loanbbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', loanpagesize_combo, '-', {
									text : '合计',
									iconCls : 'addIcon'
									//handler : function() {
										//summary.toggleSummary();
									//}
								}]
					});

     
    //复选框
	var loansm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var loanrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var loancm = new Ext.grid.ColumnModel([loanrownum,loansm, 
	        {header : '客户经理ID',dataIndex : 'USER_ID',sortable : true,width : 150}, 
	        {header : '客户经理名称',dataIndex : 'USER_NAME',sortable : true,width : 150},
	        {header : '联系方式',dataIndex : 'CONTACT'},
	        {header : '所属机构号',dataIndex : 'INSTN_NO'},
	        {header : '所属机构名称',dataIndex : 'UNITNAME'}
			]);

	/**
	 * 数据存储
	 */
	var loanstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/queryaffiliationinformation3.json?customerId='+parent.location.href.split("customerId=")[1]
   /*     ,success : function(response) {
            var resultArray = Ext.util.JSON.decode(response.responseText);
         Ext.Msg.alert('提示', response.responseText);
       }*/
	  }
	  ),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
        root:'json.data'
        }, [
            {name: 'USER_ID'},
			{name: 'USER_NAME'},
			{name: 'CONTACT'},
			{name: 'INSTN_NO'},
			{name: 'UNITNAME'}
		])
	});
	  //复选框
	var teamsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var teamrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var teamcm = new Ext.grid.ColumnModel([teamrownum,teamsm, 
	           {
				header : '营销团队名称', // 列标题
				dataIndex : 'e1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '营销团队描述',
				dataIndex : 'e2',
				sortable : true,
				width : 150
			}, {
				header : '团队负责人名称',
				dataIndex : 'e3'
			}, {
				header : '团队负责人所属机构',
				dataIndex : 'e4'
			}, {
				header : '团队负责人联系电话',
				dataIndex : 'e5'
			}, {
				header : '团队创建人名称',
				dataIndex : 'e6'
			}, {
				header : '团队创建人所属机构',
				dataIndex : 'e7'
			}, {
				header : '团队创建人联系电话',
				dataIndex : 'e8'
			}, {
				header : '营销团队成员数',
				dataIndex : 'e9'
			}, {
				header : '创建日期',
				dataIndex : 'e10'
			}
			]);

	/**
	 * 数据存储
	 */
	var teamstore = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'e1' // Json中的属性Key值
								}, {
									name : 'e2'
								}, {
									name : 'e3'
								}, {
									name : 'e4'
								}, {
									name : 'e5'
								}, {
									name : 'e6'
								}, {
									name : 'e7'
								}, {
									name : 'e8'
								}, {
									name : 'e9'
								}, {
									name : 'e10'
								}
								])
			});
	var teamData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","e1":"012","e2":"焦向波","e3":"北京银行上地支行","e4":"1302342424","e5":"主办"},
			{"rownum":"2","e1":"761","e2":"陈群","e3":"北京银行东单支行","e4":"13927386676","e5":"协办"},
			{"rownum":"3","e1":"098","e2":"姚亮","e3":"北京银行中关村支行","e4":"15872736491","e5":"协办"},			
			{"rownum":"4","e1":"231","e2":"余勇智","e3":"北京银行天津分行","e4":"12673876283","e5":"协办"}				
			]
		};
	teamstore.loadData(teamData);
	var clientbasecm = new Ext.grid.ColumnModel([teamrownum,teamsm, 
	     {header : '客户群编号',dataIndex : 'CUST_BASE_NUMBER',sortable : true,width : 150},
	     {header : '客户群名称',dataIndex : 'CUST_BASE_NAME',sortable : true,width : 150},
	     {header : '客户群描述',dataIndex : 'CUST_BASE_DESC'},
	     {header : '客户群成员数',dataIndex : 'MEMBERSNUM'},
	     //{header : '创建人',dataIndex : 'e5'},
	     {header : '创建日期',dataIndex : 'CUST_BASE_CREATE_DATE'}
			]);

	/**
	 * 数据存储
	 */
		 var clientbasestore = new Ext.data.Store({
			 
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/queryaffiliationinformation1.json?customerId='+parent.location.href.split("customerId=")[1]
		/*        success : function(response) {
                 var resultArray = Ext.util.JSON.decode(response.responseText);
              Ext.Msg.alert('提示', response.responseText);
            }*/
		        }),
		        reader: new Ext.data.JsonReader({
		       // successProperty: 'success',
		        root:'json.data',
                totalProperty: 'json.count'
		        }, [
					{name: 'CUST_BASE_NUMBER'},
					{name: 'CUST_BASE_NAME'},
					{name: 'CUST_BASE_DESC'},
					{name: 'MEMBERSNUM'},
					//{name: 'e5'},
					{name: 'CUST_BASE_CREATE_DATE'}
				])
			});
		var listcm = new Ext.grid.ColumnModel([teamrownum,teamsm, 
	         {header : '客户名单编号',dataIndex : 'ROLL_ID', sortable : true,width : 150},
	         {header : '客户名单名称',dataIndex : 'ROLL_NAME',sortable : true,width : 150},
	         {header : '客户名单描述',dataIndex : 'ROLL_DESC'},
	         {header : '名单成员数',dataIndex : 'MEMBERSNUM'}
			]);

	/**
	 * 数据存储
	 */
	var liststore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/queryaffiliationinformation4.json?customerId='+parent.location.href.split("customerId=")[1]
	  }
	  ),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
        root:'json.data'
        }, [
            {name: 'ROLL_ID'},
			{name: 'ROLL_NAME'},
			{name: 'ROLL_DESC'},
			{name: 'MEMBERSNUM'}
			
		])
	});



	// 存款表格
	var grid = new Ext.grid.GridPanel({
//		renderTo:'',
		renderTo:'viewport_center',
				title : '<span style="font-weight:normal">归属机构信息</span>',
				height :215,
				frame : true,
				autoScroll : true,
				//region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm:loansm,
				//bbar : bbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	

    // 贷款表格
			var loanGrid = new Ext.grid.GridPanel({
				renderTo:'viewport_center',
				title : '<span style="font-weight:normal">归属客户经理信息</span>',
				height :215,
				frame : true,
				autoScroll : true,
				//region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : loanstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : loancm, // 列模型
				sm : loansm, // 复选框
				//bbar : loanbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	var teamGrid = new Ext.grid.GridPanel({
		renderTo:'viewport_center',
				title : '<span style="font-weight:normal">归属营销团队信息</span>',
				height :215,
				frame : true,
				autoScroll : true,
				//region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : teamstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : teamcm, // 列模型
				sm : teamsm, // 复选框
				//bbar : loanbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var clientbaseGrid = new Ext.grid.GridPanel({
				renderTo:'viewport_center',
				title : '<span style="font-weight:normal">归属客户群信息</span>',
				height :215,
				frame : true,
				autoScroll : true,
				//region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : clientbasestore, // 数据存储
				stripeRows : true, // 斑马线
				cm : clientbasecm, // 列模型
				sm : teamsm, // 复选框
				//bbar : loanbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var listGrid = new Ext.grid.GridPanel({
		renderTo:'viewport_center',
				title : '<span style="font-weight:normal">归属客户名单信息</span>',
				height :215,
				frame : true,
				autoScroll : true,
				//region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : liststore, // 数据存储
				stripeRows : true, // 斑马线
				cm : listcm, // 列模型
				sm : teamsm, // 复选框
				//bbar : loanbbar,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
//
//	// 布局模型 
//	var viewport = new Ext.Viewport({
//		   layout:'fit',
//	        items:{
//				layout : 'border',
//				autoScroll : true,
//				items: [{  
//			    	region:'center',
//				    id: 'center-panel',
//				    autoScroll : true,
//				    margins: '0 0 0 0',
//				    items : [grid,loanGrid,clientbaseGrid,listGrid]
//			    }] 
//	        }
//			});
//	
	clientbasestore.load();
	liststore.load();
	loanstore.load();
	store.load();


}) ;