/**
 * 业务合作信息->授信
 */
Ext.onReady(function() {
    /**************************担保合同信息****************************************/
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
    debugger;
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, sm,
	            {header : 'id',dataIndex : 'id',sortable : true,hidden :true},
		        {header : 'CUST_ID',dataIndex : 'cust_id',sortable : true,hidden :true},
		        {header : '对象类型',dataIndex : 'object_typ',sortable : true},
				{header : '对象编号',dataIndex : 'object_id',sortable : true},
				{header : '抵押方式',dataIndex : 'guaranty_sty',sortable : true},
				{header : '担保金额',dataIndex : 'guaranty_amt',sortable : true},
				{header : '担保比例',dataIndex : 'guaranty_pct',sortable : true},
				{header : '币种',dataIndex : 'cur_cod',sortable : true},
				{header : '起始日',dataIndex : 'bgn_dt',sortable : true},
				{header : '到期日',dataIndex : 'end_dt',sortable : true},
				{header : '备注',dataIndex : 'rmak',sortable : true},
				{header : '操作机构',dataIndex : 'instn_id',sortable : true},
				{header : '操作人',dataIndex : 'userid',sortable : true},
				{header : '评估价值',dataIndex : 'evaluate_val',sortable : true},
				{header : '评估次数',dataIndex : 'evaluate_cnt',sortable : true},
				{header : '关联抵质押物',dataIndex : 'rela_guaranty',sortable : true},
				{header : '关联日期',dataIndex : 'rela_dt',sortable : true},
				{header : '抵质押物总价值',dataIndex : 'guaranty_val',sortable : true}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
					restful:true,	
			      proxy : new Ext.data.HttpProxy({url:basepath+'/assureinformation1.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			        	totalProperty : 'json.count',
			        root:'json.data'
			        }, [{name: 'id'},
						{name: 'cust_id'},
						{name: 'object_typ'},
						{name: 'object_id'},
						{name: 'guaranty_sty'},
						{name: 'guaranty_amt'},
						{name: 'guaranty_pct'},
						{name: 'cur_cod'},
						{name: 'bgn_dt'},
						{name: 'end_dt'},
						{name: 'rmak'},
						{name: 'instn_id'},
						{name: 'userid'},
						{name: 'evaluate_val'},
						{name: 'evaluate_cnt'},
						{name: 'rela_guaranty'},
						{name: 'rela_dt'},
						{name: 'guaranty_val'}

					])
				});
	
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});
	
	var number = parseInt(pagesize_combo.getValue());
			// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
						bbar.pageSize = parseInt(pagesize_combo.getValue());
						store.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo.getValue())
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
						items : ['-', pagesize_combo]
					});

    /**************************担保物信息****************************************/

	 //复选框
	var sm2 = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum2 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm2 = new Ext.grid.ColumnModel([rownum2,sm2, 
		        {header : '担保标的物名称',dataIndex : 'guaranty_name',sortable : true},
				{header : '业务流水号',dataIndex : 'contract_id',sortable : true},
				{header : '担保物类型',dataIndex : 'guaranty_typ',sortable : true},
				{header : '担保物辅助类型',dataIndex : 'guaranty_typ1',sortable : true},
				{header : '担保物地点位置',dataIndex : 'guarantylocation',sortable : true},
				{header : '权利人名称',dataIndex : 'signeename',sortable : true},
				{header : '币种',dataIndex : 'cur_cod',sortable : true},
				{header : '建成时间',dataIndex : 'establish_tm',sortable : true},
				{header : '担保物数量',dataIndex : 'guaranty_cnt',sortable : true},
				{header : '起始日',dataIndex : 'bgn_dt',sortable : true},
				{header : '到期日',dataIndex : 'end_dt',sortable : true},
				{header : '使用年限',dataIndex : 'holdterm',sortable : true},
				{header : '到期日期',dataIndex : 'maturity_dt',sortable : true},
				{header : '评估时间',dataIndex : 'eval_dt',sortable : true},
				{header : '评估价值',dataIndex : 'eval_val',sortable : true},
				{header : '已抵押价值',dataIndex : 'impawn_val',sortable : true},
				{header : '其他价值',dataIndex : 'else_val',sortable : true},
				{header : '在库状态',dataIndex : 'in_sts',sortable : true},
				{header : '担保债权金额',dataIndex : 'assure_val',sortable : true},
				{header : '登记人员',dataIndex : 'userid',sortable : true},
				{header : '登记机构',dataIndex : 'instn_id',sortable : true},
				{header : '登记时间',dataIndex : 'input_dt',sortable : true},
				{header : '抵押物总价值',dataIndex : 'guaranty_val',sortable : true}
			]);

	/**
	 * 数据存储
	 */
	var store2 = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/assureinformation2.json?customerId='+parent.location.href.split("customerId=")[1]}),
			        reader: new Ext.data.JsonReader({
			        totalProperty : 'json.count',
			        root:'json.data'
			        }, [
						{name: 'contract_id'},
						{name: 'guaranty_name'},
						{name: 'guaranty_typ'},
						{name: 'guaranty_typ1'},
						{name: 'guarantylocation'},
						{name: 'signeename'},
						{name: 'cur_cod'},
						{name: 'establish_tm'},
						{name: 'guaranty_cnt'},
						{name: 'bgn_dt'},
						{name: 'end_dt'},
						{name: 'holdterm'},
						{name: 'maturity_dt'},
						{name: 'eval_dt'},
						{name: 'eval_val'},
						{name: 'impawn_val'},
						{name: 'else_val'},
						{name: 'in_sts'},
						{name: 'assure_val'},
						{name: 'userid'},
						{name: 'instn_id'},
						{name: 'input_dt'},
						{name: 'guaranty_val'}
					])
				});
	
	
	// 每页显示条数下拉选择框
	var pagesize_combo2 = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});
	
	var number2 = parseInt(pagesize_combo2.getValue());
			// 改变每页显示条数reload数据
	pagesize_combo2.on("select", function(comboBox) {
						bbar2.pageSize = parseInt(pagesize_combo2.getValue());
						store2.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo2.getValue())
									}
								});
					});
	// 分页工具栏
	var bbar2 = new Ext.PagingToolbar({
						pageSize : number2,
						store : store2,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo2]
					});
	// 授信信息
	var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">担保合同信息</span>',
				height :212,
				width : 1194,
				frame : true,
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm:sm,
				bbar : bbar,// 分页工具栏
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var grid2 = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">担保物信息</span>',
				height :212,
				width : 1194,
				frame : true,
				autoScroll : true,
				store : store2, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm2, // 列模型
				sm:sm2,
				bbar : bbar2,// 分页工具栏
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	// 布局模型 
	var viewport = new Ext.Panel({
			renderTo:'grid',
				items: [{ 
				    margins: '0 0 0 0',
				    items : [grid,grid2]
			    }] 

			});
	//Ext.Msg.alert('提示',parent.location.href.split("customerId=")[1]);
	store.load();
	store2.load();
}) ;