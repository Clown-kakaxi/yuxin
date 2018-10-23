Ext.onReady(function(){
	/*得到昨天的日期*/
	var today =  new Date();
	var year1 = today.getYear();
	var mon1 = today.getMonth()+1;
	var date1 = today.getDate(); 
	
	var lastDate = new Date(year1,mon1,date1-1);
	
	var year = lastDate.getYear();
	var month = lastDate.getMonth();
	var day = lastDate.getDate();
	var monthStr =month+""; 
	var dayStr=""+day ;
	if(day<10){
		dayStr=  "0"+dayStr; 
	}
	if(month<10){
		monthStr = "0"+month;
	}
	
	var lastDay = year+"-"+monthStr+"-"+dayStr;	 
	
    Ext.QuickTips.init(); 
	 var cpfl_Store = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['负债业务','负债业务'],['资产业务','资产业务'],['中间业务','中间业务']]
	    });
	 
	var groupNo= parent.document.getElementById("groupNo").value;
	var record = Ext.data.Record.create([
			{name: 'id'},
			{name: 'instn_no'},
			{name: 'system_unitname'},
			{name: 'crm_dt'},
			{name: 'prod_level1'},
			{name: 'prod_level2'},
			{name: 'prod_level3'},
			{name: 'prod_level4'},
			{name: 'prd_id'},
			{name: 'prod_name'},
			{name: 'cust_name'},
			{name: 'cust_id'},
			{name: 'cust_zzdm'},
			{name: 'rcv_int'},
			{name: 'pay_int'},
			{name: 'bal'},
			{name: 'prd_con'},
			{name: 'prd_tzcon'},
			{name: 'year_avg'},
			{name: 'sell_amt'},
			{name: 'rcv'}
		     ]);
		
		 var Sstore = new Ext.data.Store({
//				restful:true,	
//		        proxy : new Ext.data.HttpProxy({
//		        url:basepath+'/groupCustomerProductInfoQuery.json',
//		        failure : function(response) {
//					var resultArray = Ext.util.JSON.decode(response.status);
//					if(resultArray == 403) {
//						Ext.Msg.alert('提示', response.responseText);
//					}
//				}
//		        }),
//		       reader: new Ext.data.JsonReader({
//		       totalProperty : 'json.count',
//		        root:'json.data'
//		        }, record)
			 reader : new Ext.data.JsonReader({
					root : 'rows',
					totalProperty : 'num'
				 }, 
				 record
)
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
			        {header : 'id',dataIndex : 'id',hidden:true,sortable : true},
			        {header : '统计日期',dataIndex : 'crm_dt',sortable : true},
			        {header : '组织机构代码',dataIndex : 'cust_zzdm',sortable : true},
           		    {header : '客户名称',dataIndex : 'cust_name',sortable : true},
			        {header : '机构号',dataIndex : 'instn_no',sortable : true},
			        {header : '机构名称',dataIndex : 'system_unitname',sortable : true},
           		    {header : '产品编号',dataIndex : 'prd_id',sortable : true},
           		    {header : '产品名称',dataIndex : 'prod_name',sortable : true},
           		    {header : '产品1级分类',dataIndex : 'prod_level1',sortable : true},
           		    {header : '产品2级分类',dataIndex : 'prod_level2',sortable : true},
           		    {header : '产品3级分类',dataIndex : 'prod_level3',sortable : true},
           		    {header : '产品4级分类',dataIndex : 'prod_level4',sortable : true},
           		    {header : '时点余额',dataIndex : 'bal',align:'right',sortable : true,renderer: money('0,000.00'),sortable : true},
           		    {header : '年均余额',dataIndex :'year_avg',align:'right',sortable : true,renderer: money('0,000.00'),sortable : true},
           		    {header : '销售金额', dataIndex : 'sell_amt',align:'right',sortable : true,renderer: money('0,000.00'),sortable : true},
           		    {header : '利息收入',dataIndex : 'rcv_int',align:'right',sortable : true,renderer: money('0,000.00'),sortable : true},
           		    {header : '利息支出',dataIndex : 'pay_int',align:'right',sortable : true,renderer: money('0,000.00'),sortable : true},
           		    {header : '费用收入',dataIndex : 'rcv',align:'right',sortable : true,renderer: money('0,000.00'),sortable : true},
           		    {header : '产品贡献度（模拟利润）',dataIndex : 'prd_con',align:'right',renderer: money('0,000.00'),sortable : true},
           		    {header : '产品贡献度（经济资本调整后模拟利润）',dataIndex : 'prd_tzcon',align:'right',renderer: money('0,000.00'),sortable : true,width:250}
					 ]);
			
			
		     var pagesize_combo = new Ext.form.ComboBox({
		         name : 'pagesize',
		         triggerAction : 'all',
		         mode : 'local',
		         store : new Ext.data.ArrayStore({
		             fields : ['value', 'text'],
		             data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
		  					[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 500, '500条/页' ] ]
		         }),
		         valueField : 'value',
		         displayField : 'text',
		         value : '20',
		         forceSelection : true,
		         width : 85
		     });
		    var number = parseInt(pagesize_combo.getValue());
		    pagesize_combo.on("select", function(comboBox) {
		    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
				Sstore.load({
					params : {
						'groupNo':groupNo,
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
				id : 'qForm',
				// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
				buttonAlign : 'center',
//				autoScroll : true,
				height : 120,
				items : [{
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'datefield',
							fieldLabel : '统计日期',
							id : 'crm_dt',
							name : 'crm_dt',
							value:lastDay,
							format:'Y-m-d',
							anchor : '100%'
						},{
                            xtype : 'textfield',
                            fieldLabel : '产品名称',
                            id : 'prod_name',
                            name : 'prod_name',
                            anchor : '100%'
                        },{
                            fieldLabel : '利息支出',
                            id : 'pay_int',
                            name : 'pay_int',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            decimalPrecision:2,
                            align : 'right',
                            anchor : '100%'
                        }]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '组织机构代码',
							id : 'cust_zzdm',
							name : 'cust_zzdm',
							anchor : '100%'
						},{
                            fieldLabel : '时点余额',
                            id : 'bal',
                            name : 'bal',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            decimalPrecision:2,
                            align : 'right',
                            anchor : '100%'
                        },{
                            fieldLabel : '费用收入',
                            id : 'rcv',
                            name : 'rcv',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            decimalPrecision:2,
                            align : 'right',
                            anchor : '100%'
                        } ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'cust_name',
							anchor : '100%'
						},{
                            fieldLabel : '年均余额',
                            id : 'year_avg',
                            name : 'year_avg',
                            xtype : 'numberfield', // 设置为数字输入框类型
                            decimalPrecision:2,
                            align : 'right',
                            anchor : '100%'
                        } ]
					},{
						columnWidth : .25,
						layout : 'form',
						items : [
							{	
                         fieldLabel: '产品一级分类',
                         name: 'prod_level1',
                         id: 'prod_level1',
                     	 labelStyle: 'text-align:right;',
                         forceSelection : true,
						 resizable:true,
	                     xtype:'combo',
	                     triggerAction:'all',
	                     mode:'local',
	                     store:cpfl_Store,
	                     valueField:'myId',
	                     displayField:'displayText',
	                     emptyText:'请选择',
	                     width:100,
	                     anchor : '100%'
                     },{
                         fieldLabel : '利息收入',
                         id : 'rcv_int',
                         xtype : 'numberfield', // 设置为数字输入框类型
                         decimalPrecision:2,
                         align : 'right',
                         name : 'rcv_int',
                         anchor : '100%'
                     } ]
					}]
				}],
			buttons : [{
						text : '查询',
						handler : function() {
							if(!qForm.getForm().isValid()){
								Ext.Msg.alert("提醒","输入错误！");
								return false;
							}
							var conditionStr = qForm.getForm().getFieldValues();
							Sstore.baseParams = {
									'groupNo':groupNo,
									"condition" : Ext.encode(conditionStr)
								};
							Sstore.reload({
								params : {
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
						tbar: [new Com.yucheng.bob.ExpButton({
							id: 'exportbt',
							formPanel:'qForm',
							url:basepath+'/groupCustomerProductInfoQuery.json?groupNo='+ parent.document.getElementById("groupNo").value
						    })],
						bbar:bbar,
						viewConfig:{
							   forceFit:false,
							   autoScroll:true
							},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
				

			var memberData= {
					TOTALCOUNT:1,
					rows:[{"id":"12345",
						"instn_no":"008536",
						"system_unitname":"北三环支行",
						"crm_dt":"2012-01-29",
						"prod_level1":"中间业务",
						"prod_level2":"代收款项",
						"prod_level3":"水电费代收",
						"prod_level4":"",
						"prd_id":"909877654578",
						"prod_name":"社区服务",
						"cust_name":"朝阳社区管理站",
						"cust_id":"5432267",
						"cust_zzdm":"XC233256",
						"rcv_int":"345667",
						"pay_int":"23456",
						"bal":"57587",
						"prd_con":"23456",
						"prd_tzcon":"346687",
						"year_avg":"2345567",
						"sell_amt":"12345",
						"rcv":"85435"}	
					]
				};
			Sstore.loadData(memberData);


////定义展示员工基本信息窗口
//				var addGroupCustInfoWindow = new Ext.Window({
//					title : '产品统计--产品销售明细列表',
//					plain : true,
//					layout : 'border',
//					width : 1100,
//					height : 450,
//					resizable : true,
//					draggable : true,
//					closable : true,
//					closeAction : 'hide',
//					modal : true, // 模态窗口
//					loadMask : true,
//					maximizable : true,
//					collapsible : true,
//					titleCollapse : true,
//					buttonAlign : 'right',
//					border : false,
//					items: [qForm,grid]
//				});	

				var view = new Ext.Viewport({

					layout : 'border',
					items : [ {
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [ grid ]
					},

					{
						region : 'north',
						id : 'north-panel',
						height : 150,
						layout : 'fit',
						items : [ qForm ]
					}

					]
				});
				
});