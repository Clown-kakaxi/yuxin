Ext.onReady(function() {
	var boxstore1 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=ZHZT'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});

	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
     debugger;
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, 
	                    	           {
	                    				header : '账号', // 列标题
	                    				dataIndex : 'customername', // 数据索引:和Store模型对应
	                    				sortable : true,
	                    				width : 150
	                    				// 是否可排序
	                    		    }, {
	                    				header : '账户名称',
	                    				dataIndex : 'organizationcode',
	                    				sortable : true,
	                    				width : 150
	                    			}, {
	                    				header : '余额',
	                    				dataIndex : 'customertype'
	                    			}, {
	                    				header : '开户日期',
	                    				dataIndex : 'customerlevel'
	                    			}
	                    			]);
	// 定义列模型
	var savingcm = new Ext.grid.ColumnModel([rownum, 
		    {header : 'id', dataIndex : 'agreement_id',sortable : true,width : 150,hidden :true},
			{header : '客户编号', dataIndex : 'cust_id',sortable : true,width : 150,hidden :true },
			{header : '统计日期',dataIndex : 'crm_dt',sortable : true},
	        {header : '账号', dataIndex : 'acc_no',sortable : true,width : 150}, 
		    {header : '账户名称',dataIndex : 'acc_name',sortable : true,width : 150},
		    {header : '开户网点号',dataIndex : 'org_no',sortable : true,hidden :true }, 
		    {header : '网点名称',dataIndex : 'sdf',sortable : true}, 
		    {header : '币种',dataIndex : 'currency_GP',sortable : true}, 
		    {header : '余额(原币种)',dataIndex : 'bal',sortable : true,align:'right',renderer: money('0,000.00')},
            {header : '余额',dataIndex : 'cny_bal',sortable : true,align:'right',renderer: money('0,000.00')},
            {header : '年均余额',dataIndex : 'year_aver_cny',sortable : true,align:'right',renderer: money('0,000.00')}, 
            {header : '年均余额（原币种）',dataIndex : 'year_avg',sortable : true,align:'right',renderer: money('0,000.00')}, 
		    {header : '本年实付利息',dataIndex : 'fact_int',sortable : true,align:'right',renderer: money('0,000.00')},
		    {header : '本年应付利息',dataIndex : 'accint',sortable : true,align:'right',renderer: money('0,000.00')},
            {header : '账户状态',dataIndex : 'acc_sts_GP',sortable : true},
            {header : '开户日期',dataIndex : 'open_dt',sortable : true},
            {header : '销户日期',dataIndex : 'close_dt',sortable : true},
            {header : '起息日',dataIndex : 'int_dt_ef',sortable : true},
		    {header : '到期日',dataIndex : 'int_dt_ee',sortable : true},
            {header : 'FTP',dataIndex : 'ftp',sortable : true},
            {header : '汇率中间价',dataIndex : 'ex_val',sortable : true},
		    {header : '当前利率',dataIndex : 'nor_intr',sortable : true},
		    {header : '当日考核利润',dataIndex : 'profit',sortable : true,sortable : true},
            {header : '贡献度(模拟利润)',dataIndex : 'acc_contribution',sortable : true},
            //
            {header : '自动转存',dataIndex : 'zcflg_GP',sortable : true,hidden :true},
            {header : '科目',dataIndex : 'sjno',sortable : true,hidden :true},
            {header : '计息种类',dataIndex : 'incls_GP',sortable : true,hidden :true},
            {header : '计息方式',dataIndex : 'inmod_GP',sortable : true,hidden :true},
            //{header : '账户类型',dataIndex : 'acc_sts',sortable : true,hidden :true},
            {header : '账户性质',dataIndex : 'base_flg_GP',sortable : true,hidden :true}
			]);
	/**
	 * 数据存储
	 */
  var savingStore = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/queryinformationonbusinesscooperation.json?customerId='+parent.location.href.split("customerId=")[1]
			     /*   ,
			        	 success : function(response) {
			        			//var resultArray = Ext.util.JSON.decode(response.responseText);
			        				Ext.Msg.alert('提示',response.responseText);
			        			}*/
			        }),
			        reader: new Ext.data.JsonReader({
			        	 totalProperty : 'json.count',
			        root:'json.data'
			        }, [{name: 'agreement_id'},
						{name: 'cust_id'},
						{name: 'acc_no'},
						{name: 'acc_name'},
						{name: 'org_no'},
						{name: 'currency_GP'},
						{name: 'bal'},
						{name: 'cny_bal'},
						{name: 'year_avg'},
						{name: 'fact_int'},
                        {name: 'accint'},
						{name: 'acc_sts_GP'},
						{name: 'year_aver_cny'},
						{name: 'crm_dt'},
						{name: 'profit'},
						{name: 'open_dt'},
						{name: 'close_dt'},
						{name: 'int_dt_ef'},
						{name: 'int_dt_ee'},
						{name: 'ftp'},
						{name: 'ex_val'},
						{name: 'nor_intr'},
						{name: 'acc_contribution'},
						{name: 'sjno'},
						{name: 'incls_GP'},
						{name: 'zcflg_GP'},
						{name: 'inmod_GP'},
						{name: 'base_flg_GP'}
					])
				});
  savingStore.on('beforeload', function() {
  	var conditionStr =  qForm.getForm().getValues(false);
      this.baseParams = {
              "condition":Ext.encode(conditionStr)
              
      };
});
	// 定义列模型
	var subsavingcm = new Ext.grid.ColumnModel([rownum,
		    {header : 'id', dataIndex : 'agreement_id',sortable : true,width : 150,hidden :true},
			{header : '客户编号', dataIndex : 'cust_id',sortable : true,width : 150,hidden :true }, 
			{header : '统计日期',dataIndex : 'crm_dt',sortable : true},
	        {header : '账号', dataIndex : 'acc_no',sortable : true,width : 150}, 
		    {header : '账户名称',dataIndex : 'acc_name',sortable : true,width : 150},
		    {header : '开户网点号',dataIndex : 'org_no',sortable : true,hidden :true }, 
		    {header : '网点名称',dataIndex : 'sdf',sortable : true}, 
		    {header : '币种',dataIndex : 'currency_GP',sortable : true}, 
		    {header : '余额(原币种)',dataIndex : 'bal',sortable : true,align:'right',renderer: money('0,000.00')},
		    {header : '余额',dataIndex : 'cny_bal',sortable : true,align:'right',renderer: money('0,000.00')},
		    {header : '年均余额',dataIndex : 'cny_bal',sortable : true,align:'right',renderer: money('0,000.00')}, 
          	{header : '年均余额（原币种）',dataIndex : 'year_avg',sortable : true,align:'right',renderer: money('0,000.00')}, 
		    {header : '本年实付利息',dataIndex : 'fact_int',sortable : true,align:'right',renderer: money('0,000.00')},
		    {header : '本年应付利息',dataIndex : 'accint',sortable : true,align:'right',renderer: money('0,000.00')},
		    {header : '账户状态',dataIndex : 'acc_sts_GP',sortable : true},
		    {header : '开户日期',dataIndex : 'open_dt',sortable : true},
		    {header : '销户日期',dataIndex : 'close_dt',sortable : true},
          	{header : '起息日',dataIndex : 'int_dt_ef',sortable : true},
		    {header : '到期日',dataIndex : 'int_dt_ee',sortable : true},
		    {header : 'FTP',dataIndex : 'ftp',sortable : true},
		    {header : '汇率中间价',dataIndex : 'ex_val',sortable : true},
		    {header : '当前利率',dataIndex : 'nor_intr',sortable : true},
		    {header : '贡献度(模拟利润)',dataIndex : 'acc_contribution',sortable : true},
		    {header : '当日考核利润',dataIndex : 'profit',sortable : true}
			   
			]);
	/**
	 * 数据存储
	 */
        var subsavingStore = new Ext.data.Store({
					restful:true,	
			       proxy : new Ext.data.HttpProxy({url:basepath+'/queryinformationonbusinesscooperation1.json'
			      /*  ,    success : function(response) {
							var resultArray = Ext.util.JSON.decode(response.responseText);
							Ext.Msg.alert('提示',response.responseText);
						}*/
			        }),
			        reader: new Ext.data.JsonReader({
			        	 totalProperty : 'json.count',
			        root:'json.data'
			        }, [{name: 'agreement_id'},
						{name: 'cust_id'},
						{name: 'acc_no'},
						{name: 'acc_name'},
						{name: 'org_no'},
						{name: 'currency_GP'},
						{name: 'bal'},
						{name: 'cny_bal'},
						{name: 'year_avg'},
						{name: 'fact_int'},
                        {name: 'accint'},
						{name: 'acc_sts_GP'},
						{name: 'open_dt'},
						{name: 'close_dt'},
						{name: 'int_dt_ef'},
						{name: 'int_dt_ee'},
						{name: 'ftp'},
						{name: 'ex_val'},
						{name: 'nor_intr'},
						{name: 'crm_dt'},
						{name: 'profit'},
						
						{name: 'acc_contribution'}
					])
				});
        subsavingStore.on('beforeload', function() {
        	//Ext.Msg.alert('提示', Ext.getCmp('agreement_id').getValue());//.split("COR")[1]);
            this.baseParams = {
                    "agreementId": Ext.getCmp('agreement_id').getValue()//.split("COR")[1]
            };
            debugger;
      });
//每页显示条数下拉选择框
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
	bbar.pageSize = parseInt(pagesize_combo.getValue());
	savingStore.reload({
					params : {
						start : 0,
						limit :parseInt(pagesize_combo.getValue())
					}
				});
		});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : savingStore,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 获取数据的方式
				//proxy : new Ext.data.HttpProxy({
						//	url : 'gridDemo.ered?reqCode=querySfxmDatas'
						//}),
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customername' // Json中的属性Key值
								}, {
									name : 'organizationcode'
								}, {
									name : 'customertype'
								}, {
									name : 'customerlevel'
								}
								])
			});
	

     
    //复选框
	var loansm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var loanrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var loancm = new Ext.grid.ColumnModel([loanrownum,
	        {header : 'id', dataIndex : 'cust_id', sortable : true,width : 150,hidden:true},
	        {header : '统计日期',dataIndex : 'crm_dt',sortable : true},
			{header : '账号', dataIndex : 'aa20acno', sortable : true,width : 150 },
			{header : '账户名称',dataIndex : 'aa20acna',sortable : true,width : 150},
			{header : '开户网点号',dataIndex : 'aa20dpid',hidden:true}, 
			{header : '网点名称',dataIndex : 'latticepointname'}, 
			{header : '币种',dataIndex : 'aa20cu_GP'},
			{header : '科目',dataIndex : 'aa20sjno'},
			{header : '余额(原币种)',dataIndex : 'balance1',align:'right',renderer: money('0,000.00')},
			{header : '余额',dataIndex : 'balance_cny',align:'right',renderer: money('0,000.00')},
			{header : '年均余额(原币种)',dataIndex : 'year_avg',align:'right',renderer: money('0,000.00')},
			{header : '年均余额',dataIndex : 'year_avg_cny',align:'right',renderer: money('0,000.00')},
			{header : '本年实收利息',dataIndex : 'al30ocam',align:'right',renderer: money('0,000.00')},
			{header : '本年应收利息',dataIndex : 'aieaccrdt',align:'right',renderer: money('0,000.00')},
			{header : '计息种类',dataIndex : 'aa20incls_GP',align :'right',hidden:true},
			{header : '计息方式',dataIndex : 'aa20inmod_GP',hidden:true},
			{header : '利率',dataIndex : 'aln0itr'},
			{header : '开户日期',dataIndex : 'open_date'},
			{header : '销户日期',dataIndex : 'act4date'},
			{header : '起息日',dataIndex : 'aa20datef'},
			{header : '到期日',dataIndex : 'aa20datet'},
			{header : '账户状态',dataIndex : 'aa20acsts_GP'},
			{header : '借据号',dataIndex : 'serialno',hidden:true},
			{header : '发放日期',dataIndex : 'actualputoutdate',hidden:true},
			{header : '到期日期',dataIndex : 'maturity',hidden:true},
			{header : '实际到期日',dataIndex : 'actualmaturity',hidden:true},
			{header : '五级分类',dataIndex : 'conveyreturnflag_GP'},
			{header : 'FTP',dataIndex : 'ftp'},
			{header : '汇率中间价',dataIndex : 'ABK0BAR'},
			{header : '信用风险经济资本占用',dataIndex : 'capital_occupancy'},
			{header : '贡献度(模拟利润)',dataIndex : 'contribution_a'},
			{header : '贡献度(经济资本调整后模拟利润)',dataIndex : 'contribution_b'},
			{header : '当日考核利润',dataIndex : 'profit'},
			{header : '四级分类结果',dataIndex : 'class_four',hidden:true},
			{header : '利率档次',dataIndex : 'rateterm',align :'right',hidden:true},
			{header : '利率变更类型',dataIndex : 'ratechangetype',hidden:true},
			{header : '罚息利率浮动比例',dataIndex : 'finerateratio',hidden:true},
			{header : '对应收息存款账号',dataIndex : 'aln0acnoy',hidden:true},
			{header : '账户id',dataIndex : '	sub_agreement_id',hidden:true}
			]);

	/**
	 * 数据存储
	 */
	  var loanstore = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/queryinformationonbusinesscooperation2.json?customerId='+parent.location.href.split("customerId=")[1]
	   /*    ,
	        success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert('提示',response.responseText);
			}*/
	        }),
	        reader: new Ext.data.JsonReader({
	        	  totalProperty : 'json.count',
	        root:'json.data'
	        }, [
				{name: 'cust_id'},
				{name: 'crm_dt'},
				
				{name: 'aa20acno'},
				{name: 'aa20acna'},
				{name: 'aa20dpid'},
				{name: 'latticepointname'},
				{name: 'aa20cu_GP'},
				{name: 'aa20sjno'},
				{name: 'balance1'},
				{name: 'balance_cny'},
                {name: 'year_avg'},
				{name: 'year_avg_cny'},
				{name: 'al30ocam'},
				{name: 'aieaccrdt'},
				{name: 'aa20incls_GP'},
				{name: 'aa20inmod_GP'},
				{name: 'aln0itr'},
				{name: 'open_date'},
				{name: 'act4date'},
				{name: 'aa20datef'},
				{name: 'aa20datet'},
				{name: 'aa20acsts_GP'},
				{name: 'serialno'},
				{name: 'actualputoutdate'},
				{name: 'maturity'},
				{name: 'actualmaturity'},
				{name: 'conveyreturnflag_GP'},
				{name: 'ftp'},
				{name: 'abk0bar'},
				{name: 'capital_occupancy'},
				{name: 'contribution_a'},
				{name: 'contribution_b'},
				{name: 'profit'},
				{name: 'class_four'},
				{name: 'rateterm'},
				{name: 'ratechangetype'},
				{name: 'finerateratio'},
				{name: 'aln0acnoy'},
				{name: 'sub_agreement_id'}
			])
		});
	  loanstore.on('beforeload', function() {
		  	var conditionStr =  qForm2.getForm().getValues(false);
		      this.baseParams = {
		              "condition":Ext.encode(conditionStr)
		              
		      };
		});
	  var loanpagesize_combo = new Ext.form.ComboBox({
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
	    var loannumber = parseInt(loanpagesize_combo.getValue());
	    loanpagesize_combo.on("select", function(comboBox) {
	    	loanbbar.pageSize = parseInt(loanpagesize_combo.getValue()),
	    	loanstore.load({
						params : {
							start : 0,
							limit : parseInt(loanpagesize_combo.getValue())
						}
					});
		});
		var loanbbar = new Ext.PagingToolbar({
	        pageSize : loannumber,
	        store : loanstore,
	        displayInfo : true,
	        displayMsg : '显示{0}条到{1}条,共{2}条',
	        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	        emptyMsg : "没有符合条件的记录",
	        items : ['-', '&nbsp;&nbsp;', loanpagesize_combo
	                 ]
	    });




	var windowForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 120,
		items : [{
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .33,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '客户手机提醒',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								},{
									fieldLabel : '客户经理手机提醒', // 标签
									name : 'c4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '客户邮件提醒', // 标签
									name : 'c4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '客户经理邮件提醒', // 标签
									name : 'c4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [{
									fieldLabel : '客户站内提醒', // 标签
									name : 'c7', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								},{
									fieldLabel : '客户经理站内提醒', // 标签
									name : 'c4', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
									anchor : '90%' // 宽度百分比
								}]
					}]
		}]
	});
	/*var boxstore2 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CPQZ'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});*/
	var boxstore2 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querydictionary.json?name=CPQZ'
			/*	,
	        	 success : function(response) {
	        			//var resultArray = Ext.util.JSON.decode(response.responseText);
	        				Ext.Msg.alert('提示',response.responseText);
	        			}*/
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'code_name_1', 'code_name' ])
		});
	
	
	var boxstore3 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querydictionary.json?name=PJXS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'code_name_1', 'code_name' ])
		});
	var boxstore4 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querydictionary.json?name=DBXSHU'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'code_name_1', 'code_name' ])
		});
	var boxstore5 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querydictionary.json?name=QXXS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'code_name_1', 'code_name' ])
		});
	var boxstore6 = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querydictionary.json?name=HYXS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'code_name_1', 'code_name' ])
		});
	var boxstore7= new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/querydictionary.json?name=FGXS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'json.data'
			}, [ 'code_name_1', 'code_name' ])
		});
/*	var boxstore8= new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=DKLX'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});*/
	 var panel2 = new Ext.FormPanel({ 
	        frame:true,
	        id:'panel2',
	        bodyStyle:'padding:5px 5px 0',
	        title : '<span style="font-weight:normal">信用风险经济资本设置</span>',
	        width: '100%',
	        height:300,
	        reader: new Ext.data.JsonReader({
             root:'json.data'
             }, ['PRODRATIO','BUSINESSCOEFF','LON_TYPE','GRADECOEFF','COVERCOEFF','GUARANTEECOEFF','ADJUSTCOEFF','TIMELIMITCOEFF','AJUSTVALUE']),
	        items: [{
	            autoHeight:true,
	            items :[{ layout:'column',
	                buttonAlign : 'center',
	                     items:[{
	                         columnWidth:.25,
	                         labelWidth : 80, // 标签宽度
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
                              hiddenName : 'PRODRATIO',   
                              fieldLabel : '*产品权重',
                              labelStyle: 'text-align:right;',
                              triggerAction : 'all',
                              store : boxstore2,
                              displayField : 'code_name',
                              valueField : 'code_name_1',
                              mode : 'local',
                              forceSelection : true,
                              typeAhead : true,
                              emptyText:'请选择',
                              resizable : true,
                              allowBlank : false,
                              anchor : '95%'
                          }),new Ext.form.ComboBox({
                              hiddenName : 'GUARANTEECOEFF',
                              fieldLabel : '*担保系数',
                              labelStyle: 'text-align:right;',
                              triggerAction : 'all',
                              store : boxstore4,
                              displayField : 'code_name',
                              valueField : 'code_name_1',
                              mode : 'local',
                              forceSelection : true,
                              typeAhead : true,
                              emptyText:'请选择',
                              resizable : true,
                              allowBlank : false,
                              anchor : '95%'
                          })]
	                     },{
	                         columnWidth:.25,
	                         labelWidth : 80, // 标签宽度
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
	                              hiddenName : 'GRADECOEFF',  
	                              id:'grade_coeff',
	                              fieldLabel : '客户评级系数',
	                              labelStyle: 'text-align:right;',
	                              triggerAction : 'all',
	                              store : boxstore3,
	                              displayField : 'code_name',
	                              valueField : 'code_name_1',
	                              mode : 'local',
	                              forceSelection : true,
	                              typeAhead : true,
	                              emptyText:'请选择',
	                              resizable : true,
	                              anchor : '95%'
	                          }),/* new Ext.form.ComboBox({
	                              hiddenName : 'LON_TYPE',   
	                              fieldLabel : '*贷款类型',
	                              labelStyle: 'text-align:right;',
	                              triggerAction : 'all',
	                              store : boxstore8,
	                              displayField : 'value',
	                              valueField : 'key',
	                              mode : 'local',
	                              forceSelection : true,
	                              typeAhead : true,
	                              emptyText:'请选择',
	                              resizable : true,
	                             //allowBlank : false,
	                              anchor : '99%'
	                          }),*/{
	                               name : 'ADJUSTCOEFF',
	                               xtype: 'numberfield',
	                               maxValue:100000000000000000,
	                               anchor : '95%',
	                               allowBlank : false,
				                   fieldLabel: '*调整系数',
				                   labelStyle: 'text-align:right;'
		                           }]
	                     },{
	                         columnWidth:.25,
	                         labelWidth : 80, // 标签宽度
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
	                              hiddenName : 'BUSINESSCOEFF',  
	                              id:'business_coeff',
	                              fieldLabel : '行业系数',
	                              labelStyle: 'text-align:right;',
	                              triggerAction : 'all',
	                              store : boxstore6,
	                              displayField : 'code_name',
	                              valueField : 'code_name_1',
	                              mode : 'local',
	                              forceSelection : true,
	                              typeAhead : true,
	                              emptyText:'请选择',
	                              resizable : true,
	                              anchor : '95%'
	                          }),{
	                               name : 'AJUSTVALUE',
	                               xtype: 'numberfield',
	                               maxValue:100,
	                               anchor : '95%',
				                   fieldLabel: '*调整值',
				                   allowBlank : false,
				                   labelStyle: 'text-align:right;'
		                           }]
	                     },{
	                         columnWidth:.25,
	                         labelWidth : 80, // 标签宽度
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
									hiddenName : 'TIMELIMITCOEFF',
									fieldLabel : '*期限系数',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore5,
									displayField : 'code_name',
									valueField : 'code_name_1',
									mode : 'local',
									forceSelection : true,
									allowBlank : false,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								}),new Ext.form.ComboBox({
		                              hiddenName : 'COVERCOEFF',   
		                              fieldLabel : '*融资平台流动资金覆盖系数',
		                              labelStyle: 'text-align:right;',
		                              triggerAction : 'all',
		                              store : boxstore7,
		                              displayField : 'code_name',
		                              valueField : 'code_name_1',
		                              mode : 'local',
		                              forceSelection : true,
		                              typeAhead : true,
		                              emptyText:'请选择',
		                              allowBlank : false,
		                              resizable : true,
		                              anchor : '95%'
		                          })]
	                     }
	                ]} 
	                ]}]
	        });
		Ext.getCmp('business_coeff').on('select', function() {
			//alert('sdfsdfsdf');
			if(
			Ext.getCmp('business_coeff').value!=""){
				Ext.getCmp('grade_coeff').setValue("");}
		});
		Ext.getCmp('grade_coeff').on('select', function() {
			//alert('sdfsdfsdf');
			if(
			Ext.getCmp('grade_coeff').value!=""){
				Ext.getCmp('business_coeff').setValue("");}
		});
	   var addRoleWindow = new Ext.Window(
			    {
			        //layout : 'fit',
			        width : 850,
			        height :250,
			        buttonAlign : 'center',
			        draggable : true,//是否可以拖动
			        closable : true,// 是否可关闭
			        modal : true,
			        closeAction : 'hide',
			        // iconCls : 'page_addIcon',
			        //maximizable: true,
			        //maximized:true,
			        collapsible : true,// 是否可收缩
			        titleCollapse : true,
			        border : false,
			        animCollapse : true,
			        pageY : 20,
			        //pageX : document.body.clientWidth / 2 - 420 / 2,
			        animateTarget : Ext.getBody(),
			        constrain : true,
			        items : [panel2
			                 ],
			        buttons : [
			                    {
			                        text : '保存',
			                        handler : function() {
			                            insert();
			                        }
			                    }, {
			                        text : '重置',
			                        handler : function() {
			                        	panel2.getForm().reset();
//			                        	.getEl().dom
			                        	
			                        }
			                    }, {
			                        text : '关闭',
			                        handler : function() {
			                            addRoleWindow.hide();
			                        }
			                    } ]
			    });
	
	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
					text : '账号详细信息',
					handler : function() {
					 	var checkedNodes = grid.getSelectionModel().selections.items;
						if(checkedNodes.length>1)
						{
								Ext.Msg.alert('提示', '您只能选中一条数据!');
								return ;
						}
						if(checkedNodes.length==0)
						{
								Ext.Msg.alert('提示', '未选择任何数据!');
								return ;
						}
					    var s = Ext.getCmp('south-panel');
					    s.setVisible(true);
						Ext.getCmp('tabs').setVisible(true);
						 var record = grid.getSelectionModel().getSelected();
						 blocAssetDebtReport.getForm().loadRecord(record);
						  tabs.setActiveTab(0);
					    s.expand() ; 
					}
				}]
			});
	// 表格工具栏
	var tbar2 = new Ext.Toolbar({
				items : [{
					text : '账号详细信息',
					handler : function() {
						var checkedNodes = loanGrid.getSelectionModel().selections.items;
						if(checkedNodes.length>1)
						{
								Ext.Msg.alert('提示', '您只能选中一条数据!');
								return ;
						}
						if(checkedNodes.length==0)
						{
								Ext.Msg.alert('提示', '未选择任何数据!');
								return ;
						}
					    var s = Ext.getCmp('south-panel');
					    s.setVisible(true);
						Ext.getCmp('tabs2').setVisible(true);
						 var record = loanGrid.getSelectionModel().getSelected();
						 LoanDebtReport.getForm().loadRecord(record);
						 tabs2.setActiveTab(0);
					    s.expand() ; 
					}
				},{
					text : '信用风险经济资本',
					handler : function() {
						var checkedNodes = loanGrid.getSelectionModel().selections.items;
						if(checkedNodes.length>1)
						{
								Ext.Msg.alert('提示', '您只能选中一条数据!');
								return ;
						}
					  	var _record = loanGrid.getSelectionModel().getSelected();
				        if (!_record) {
				           	Ext.MessageBox.alert('提示', '请选择要操作的一列！');
				           }
				        else{
				        	addRoleWindow.show();
				       	 var checkedNodes = loanGrid.getSelectionModel().selections.items;
							panel2.getForm().load({
						         //waitMsg: '正在加载数据',
						 	        //waitTitle: '提示',
							 restful:true,	
						     url:basepath+'/querycreditrisk.json?accountId='+checkedNodes[0].data.payaccount,
						     method: 'GET'/*,
						     success : function() {
						    	 Ext.Msg.alert('提示',Ext.getCmp('CPTL_AMT').getValue);
						    	 
						    	 Ext.getCmp('CPTL_AMT').setValue(Ext.util.Format.number(Ext.getCmp('CPTL_AMT').getValue, "0,000.00"));
						    	 debugger;*/
								//var resultArray = Ext.util.JSON.decode(response.responseText);
									//Ext.Msg.alert('提示',response.responseText);
					    });
					}}
				}]
			});
	// 存款表格
	var grid = new Ext.grid.GridPanel({
				//title : '<span style="font-weight:normal">存款</span>',
				height :document.body.scrollHeight-135,
				width:document.body.scrollWidth-5,
				frame : true,
				autoScroll : true,
				store : savingStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : savingcm, // 列模型
				tbar:tbar,
				//sm:sm,
			/*	selModel:new Ext.grid.RowSelectionModel({
					singleSelect:true,
					listeners:{
					'rowselect':function( model,  rowIndex,  record )
					{
						if(rowIndex==0){
							   var s = Ext.getCmp('south-panel');
					            // expand or collapse that Panel based on its collapsed property state
					            w.collapsed ? w.expand() : w.collapse();
					            south-panel
						Ext.getCmp('tabs').setVisible(true);
					        s.expand() ; 
						}
					}
					}
				}),*/
				bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	/*grid.on('click', function(){
		//if (grid.store.data.items==0){
		alert("1232");
		//}
	});*/

    // 贷款表格
	var loanGrid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">贷款</span>',
				height :document.body.scrollHeight-135,
				width:document.body.scrollWidth-3,
				frame : true,
				autoScroll : true,
				store : loanstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : loancm, // 列模型
				//sm : loansm, // 复选框
				bbar : loanbbar,// 分页工具栏
			    tbar:tbar2,
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	
	var blocAssetDebtReport = new Ext.form.FormPanel(
			{
				labelWidth : 90, // 标签宽度
				frame : true, //是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'right',
				id:"blocAssetDebtReport",
				height : 300,
				frame:true,
				items:[
				{
					layout:'column',
					border : false,
					items:[
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						defaultType : 'textfield',
						border : false,
						items:[
						{
							xtype:'textfield',
							fieldLabel:'账号',
							labelStyle: 'text-align:right;',
							name:'acc_no',
							anchor:'80%'
						},{
							xtype:'textfield',
							anchor:'80%',
							fieldLabel:'币种',
							labelStyle: 'text-align:right;',
							name:'currency_GP'
						},{
							xtype:'textfield',
							fieldLabel:'科目',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'sjno'
						},{
							hidden :true,
							xtype : 'textfield',
							fieldLabel : 'id',
							id:'agreement_id',
							name : 'agreement_id',
							anchor : '90%'
						}		
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						defaultType : 'textfield',
						border : false,
						items:[
						{
							xtype:'textfield',
							fieldLabel:'账户名称',
							name:'acc_name',
							labelStyle: 'text-align:right;',
							anchor:'80%'
						},{
							xtype:'textfield',
							fieldLabel:'计息种类',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'incls_GP'
						},{
							xtype:'textfield',
							fieldLabel:'账户性质',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'base_flg_GP'
						}		
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						labelAlign:'right',
						items:[
						{
							xtype:'textfield',
							fieldLabel:'开户网点号',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'org_no'
						},{
							xtype:'textfield',
							fieldLabel:'计息方式',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'inmod_GP'
						},{
							xtype:'textfield',
							fieldLabel:'账户状态',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'acc_sts_GP'
						}	
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						labelAlign:'right',
						items:[
						{
							xtype:'textfield',
							fieldLabel:'开户网点名称',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'q15'
						},{
							xtype:'textfield',
							fieldLabel:'利率',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'nor_intr'
						},{
							xtype:'textfield',
							fieldLabel:'自动转存',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'zcflg_GP'
						}				
						]
					}
					
					
				]
				
				}
			  ]
			}
			);
	var detailForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 70,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								labelAlign:'right',
								border : false,
								items : [{
											fieldLabel : '交易日期从',
											name : 'acc7date_start',
											format:'Y-m-d', //日期格式化
											xtype : 'datefield', // 设置为数字输入框类型
											anchor : '90%'
										}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								labelAlign:'right',
								border : false,
								items : [{
											fieldLabel : '交易日期至',
											name : 'acc7date_end',
											format:'Y-m-d', //日期格式化
											xtype : 'datefield', // 设置为数字输入框类型
											anchor : '90%'
										}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
						detailStore.reload({
							  params : {
                                 start : 0,
                                 limit : bbar.pageSize }} );
					}
				}, {
					text : '重置',
					handler : function() {
						detailForm.getForm().reset();
					}
				}]
	});
	

	// 定义列模型
	var detailcm = new Ext.grid.ColumnModel([rownum, 
	       {header : '账号', dataIndex : 'acno',width : 150},
	       {header : '子账号', dataIndex : 'sub_acc',width : 150},
	       {header : '交易日期', dataIndex : 'tran_date',width : 150},
	       {header : '交易时间', dataIndex : 'tran_time',width : 150},
	       {header : '借方发生额', dataIndex : 'd_amt',width : 150,align:'right',renderer: money('0,000.00')},
	       {header : '贷方发生额', dataIndex : 'c_amt',width : 150,align:'right',renderer: money('0,000.00')},
	       {header : '余额',	dataIndex : 'bal',align:'right',renderer: money('0,000.00')},
	       {header : '摘要',dataIndex : 'abco'},
	       {header : '账户所在网点',dataIndex : 'org_no'},
	       {header : '对手名称',dataIndex : 'tran_name'},
	       {header : '对手银行名称',dataIndex : 'tran_bank'},
	       {header : '出入账网点号',dataIndex : 'tran_org'},
	       {header : '对手账户',dataIndex : 'tran_acc'},
	       {header : '交易原因',dataIndex : 'sdfsdf'}
			]);

	  var detailStore = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/queryinformationonbusinesscooperation3.json'}),
	        reader: new Ext.data.JsonReader({
	        totalProperty : 'json.count',
	        root:'json.data'
	        }, [
				{name: 'acno'},
				{name: 'sub_acc'},
				{name: 'tran_date'},
				{name: 'tran_time'},
				{name: 'd_amt'},
				{name: 'c_amt'},
				{name: 'bal'},
				{name: 'abco'},
				{name: 'org_no'},
				{name: 'tran_org'},
				{name: 'tran_name'},
				{name: 'tran_bank'},
				{name: 'tran_acc'}
			])
		});
	  detailStore.on('beforeload', function() {
			var conditionStr =  detailForm.getForm().getValues(false);
		  //var checkedNodes = grid.getSelectionModel().selections.items;jj
      	//Ext.Msg.alert('提示', Ext.getCmp('agreement_id').getValue().split("COR")[1]);
          this.baseParams = {
                  "agreementId": Ext.getCmp('agreement_id').getValue(),
                  "condition":Ext.encode(conditionStr)
          };
    });
	  var detailpagesize_combo = new Ext.form.ComboBox({
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
var detailnumber = parseInt(detailpagesize_combo.getValue());
		// 改变每页显示条数reload数据
detailpagesize_combo.on("select", function(comboBox) {
	detailbbar.pageSize = parseInt(detailpagesize_combo.getValue());
		detailStore.reload({
					params : {
						start : 0,
						limit :parseInt(detailpagesize_combo.getValue())
					}
				});
	});
// 分页工具栏
var detailbbar = new Ext.PagingToolbar({
					pageSize : detailnumber,
					store : detailStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
					emptyMsg : "没有符合条件的记录",
					items : ['-', '&nbsp;&nbsp;', detailpagesize_combo]
				});
	


	// 表格实例
	var detailgrid = new Ext.grid.GridPanel({
		height :223,
		width:document.body.scrollWidth-5,
				frame : true,
				autoScroll : true,
				store : detailStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : detailcm, // 列模型
				sm : sm, // 复选框
				bbar:detailbbar,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
	         /*   ,
				tbar:[
						{
							text:'导出交易明细',
							handler:function()
							{
							//window.location="creditValueUseApply.html";
							//setSMEWindow.show();
								savingTrends.show();
							}
							
						},{
							text:'变化趋势图',
							handler:function()
							{
							//window.location="creditValueUseApply.html";
							//setSMEWindow.show();
								savingTrends.show();
							}
							
						}		
				]*/
	
			});
	var loandetailForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 70,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								labelAlign:'right',
								border : false,
								items : [{
											fieldLabel : '客户名称',
											name : 'cn',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								labelAlign:'right',
								border : false,
								items : [{
											fieldLabel : '组织机构代码', // 标签
											id : 'ct',
											name : 'ct', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 100, // 标签宽度
								defaultType : 'textfield',
								labelAlign:'right',
								border : false,
								items : [{
											fieldLabel : '客户类型', // 标签
											name : 'c', // name:后台根据此name属性取值
											maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
						loandetailStore.reload({
							  params : {
                                 start : 0,
                                 limit : loandetailbbar.pageSize }} );
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
					}
				}]
	});
	

	// 定义列模型
	var loandetailcm = new Ext.grid.ColumnModel([rownum, 
	       {header : '账户', dataIndex : 'alp0acno',width : 150},
	       {header : '交易日期', dataIndex : 'alp0datef',width : 150},
	       //{header : '交易时间', dataIndex : 'sdfsdfas',width : 150},
	       {header : '借方发生额', dataIndex : 'alp0ocam',width : 150,align:'right',renderer: money('0,000.00')},
	       //{header : '贷方发生额', dataIndex : 'acc7cdid',width : 150},
	       {header : '余额',	dataIndex : 'acc7acbl',align:'right'},
	       //{header : '摘要',dataIndex : 'acc7abco'},
	       {header : '账户所在网点',dataIndex : 'acc7dpidj'},
	       //{header : '对手名称',dataIndex : 'tran_name'},
	       //{header : '对手银行名称',dataIndex : 'tran_bank'},
	       //{header : '出入账网点号',dataIndex : 'sdf'},
	       {header : '放款账户',dataIndex : 'alp0acno'},
	       {header : '交易原因',dataIndex : 'crm_alp0mdco_1_GP'}
			]);

	  var loandetailStore = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/queryinformationonbusinesscooperation4.json'}),
	        reader: new Ext.data.JsonReader({
	        	 totalProperty : 'json.count',
	        root:'json.data'
	        }, [
				{name: 'alp0acno'},
				{name: 'alp0datef'},
				//{name: 'acc7ocam'},
				{name: 'alp0ocam'},
				{name: 'acc7acbl'},
				//{name: 'acc7abco'},
				{name: 'acc7dpidj'},
				//{name: 'tran_name'},
				//{name: 'alp0acno'},
				{name: 'alp0acno'},
				{name: 'crm_alp0mdco_1_GP'}
			])
		});
	  loandetailStore.on('beforeload', function() {
      	//Ext.Msg.alert('提示', Ext.getCmp('agreement_id').getValue().split("COR")[1]);
          this.baseParams = {
                  "agreementId": Ext.getCmp('sub_agreement_id').getValue()
          };
    });
	  var loandetailpagesize_combo = new Ext.form.ComboBox({
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
var loandetailnumber = parseInt(loandetailpagesize_combo.getValue());
		// 改变每页显示条数reload数据
loandetailpagesize_combo.on("select", function(comboBox) {
	loandetailbbar.pageSize = parseInt(loandetailpagesize_combo.getValue());
	loandetailStore.reload({
					params : {
						start : 0,
						limit :parseInt(loandetailpagesize_combo.getValue())
					}
				});
	});
// 分页工具栏
var loandetailbbar = new Ext.PagingToolbar({
					pageSize : loandetailnumber,
					store : loandetailStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
					emptyMsg : "没有符合条件的记录",
					items : ['-', '&nbsp;&nbsp;', loandetailpagesize_combo]
				});
	


	// 表格实例
	var loandetailgrid = new Ext.grid.GridPanel({
		height :document.body.scrollHeight-105,
		width:document.body.scrollWidth-5,
				frame : true,
				autoScroll : true,
				store : loandetailStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : loandetailcm, // 列模型
				sm : sm, // 复选框
				bbar:detailbbar,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
/*	,
				tbar:[
						{
							text:'导出交易明细',
							handler:function()
							{
							//window.location="creditValueUseApply.html";
							//setSMEWindow.show();
								savingTrends.show();
							}
							
						},{
							text:'变化趋势图',
							handler:function()
							{
							//window.location="creditValueUseApply.html";
							//setSMEWindow.show();
								savingTrends.show();
							}
							
						}		
				]*/
	
			});
	// 表格实例
	var detailgrid2= new Ext.grid.GridPanel({
				height:295,
					//document.body.scrollHeight-120,
				width:document.body.scrollWidth-6,
				frame : true,
				autoScroll : true,
				store : subsavingStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : subsavingcm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	debugger;
	var LoanDebtReport = new Ext.form.FormPanel(
			{
				labelWidth : 90, // 标签宽度
				frame : true, //是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'right',
				id:"LoanDebtReport",
				height : 300,
				frame:true,
				items:[
				{
					layout:'column',
					border : false,
					items:[
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						defaultType : 'textfield',
						border : false,
						items:[
						{
							xtype:'textfield',
							fieldLabel:'账号',
							labelStyle: 'text-align:right;',
							name:'payaccount',
							anchor:'80%'
						},{
							xtype:'textfield',
							anchor:'80%',
							fieldLabel:'币种',
							labelStyle: 'text-align:right;',
							name:'aa20cu_GP'
						},{
							xtype:'textfield',
							fieldLabel:'科目',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'aa20sjno'
						}/*,{
							xtype:'textfield',
							fieldLabel:'到期日期',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'maturity'
						}*/,{
							xtype:'textfield',
							fieldLabel:'利率档次',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'rateterm'
						},{
							hidden :true,
							xtype : 'textfield',
							fieldLabel : 'id',
							id:'sub_agreement_id',
							name : 'sub_agreement_id',
							anchor : '90%'
						},{
							xtype:'textfield',
							fieldLabel:'利率变更类型',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'ratechangetype'
						}		
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						defaultType : 'textfield',
						border : false,
						items:[
						{
							xtype:'textfield',
							fieldLabel:'账户名称',
							name:'aa20acna',
							labelStyle: 'text-align:right;',
							anchor:'80%'
						},{
							xtype:'textfield',
							fieldLabel:'计息种类',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'aa20incls_GP'
						},{
							xtype:'textfield',
							fieldLabel:'账户状态',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'aa20acsts_GP'
						},{
							xtype:'textfield',
							fieldLabel:'实际到期日',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'actualmaturity'
						},{
							xtype:'textfield',
							fieldLabel:'对应收息存款账号',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'aln0acnoy'
						}					
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						labelAlign:'right',
						items:[
						{
							xtype:'textfield',
							fieldLabel:'开户网点号',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'aa20dpid'
						},{
							xtype:'textfield',
							fieldLabel:'计息方式',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'aa20inmod_GP'
						},{
							xtype:'textfield',
							fieldLabel:'借据号',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'serialno'
						},{
							xtype:'textfield',
							fieldLabel:'五级分类结果',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'conveyreturnflag_GP'
						},{
							xtype:'textfield',
							fieldLabel:'罚息利率浮动比例',
							anchor:'80%',
							labelStyle: 'text-align:right;',
							name:'finerateratio'
						}		
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, 
						labelAlign:'right',
						items:[
						{
							xtype:'textfield',
							fieldLabel:'开户网点名称',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'q15'
						},{
							xtype:'textfield',
							fieldLabel:'利率',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'aln0itr'
						},{
							xtype:'textfield',
							fieldLabel:'发放日期',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'actualputoutdate'
						},{
							xtype:'textfield',
							fieldLabel:'四级分类结果',
							labelStyle: 'text-align:right;',
							anchor:'80%',
							name:'class_four'
						}
						]
					}
					
					
				]
				
				}
			  ]
			}
			);
	
	
	var tabs = new Ext.TabPanel({
		id:'tabs',
		hidden:true,
		hideMode:'display',
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            { title: '账号信息',items:[blocAssetDebtReport]},
            { title: '子账号',items:[detailgrid2],listeners : {
				'activate' : function() {subsavingStore.load();}
            }},
            { title: '交易明细',items:[detailForm,detailgrid],listeners : {
				'activate' : function() {detailStore.load();}
            }}
        ]
    });
	var tabs2 = new Ext.TabPanel({
		id:'tabs2',
		hidden:true,
		hideMode:'display',
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            { title: '账号信息',items:[LoanDebtReport]}
           ,
            { title: '交易明细',items:[
                                   //loandetailForm,
                                   loandetailgrid],listeners : {
				'activate' : function() {loandetailStore.load();}}
            }
        ]
    });
	var qForm = new Ext.form.FormPanel({
		labelWidth : 60, // 标签宽度
		width:document.body.scrollWidth-5,
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height :70,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 70, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '统计日期',
											name : 'crm_dt',
											format:'Y-m-d', //日期格式化
											 labelStyle: 'text-align:right;',
											xtype : 'datefield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 70, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [new Ext.form.ComboBox({
											hiddenName : 'acc_sts',
											fieldLabel : '账户状态',
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : boxstore1,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											emptyText:'请选择',
											typeAhead : true,
											resizable : true,
											anchor : '80%'
										})]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
						 savingStore.load();//queryBalanceInfo(qForm.getForm());
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
					}
				}]
	});
	var qForm2 = new Ext.form.FormPanel({
		labelWidth : 60, // 标签宽度
		width:document.body.scrollWidth-5,
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height :70,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 70, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '统计日期',
											name : 'crm_dt',
											format:'Y-m-d', //日期格式化
											 labelStyle: 'text-align:right;',
											xtype : 'datefield', // 设置为数字输入框类型
											anchor : '80%'
										}]
							},{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 70, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [new Ext.form.ComboBox({
											hiddenName : 'aa20acsts',
											fieldLabel : '账户状态',
											labelStyle: 'text-align:right;',
											triggerAction : 'all',
											store : boxstore1,
											displayField : 'value',
											valueField : 'key',
											mode : 'local',
											forceSelection : true,
											emptyText:'请选择',
											typeAhead : true,
											resizable : true,
											anchor : '80%'
										})]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
						loanstore.load();// savingStore.load();//queryBalanceInfo(qForm.getForm());
					}
				}, {
					text : '重置',
					handler : function() {
						qForm2.getForm().reset();
					}
				}]
	});
	var tabmain = new Ext.TabPanel({
		id:'tabmain',
		width:document.body.scrollWidth,
		//hidden:true,
		//hideMode:'display',
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            {  title: '存款账户信息',items:[qForm,grid],listeners : {
				'activate' : function() {
					var s = Ext.getCmp('south-panel');
					qForm2.getForm().reset();
					s.collapse();
					//s.setVisible(false);
					//s.hidden=true;
					//s.remove();
					//s.collapse();
				    Ext.getCmp('tabs2').setVisible(false);
				    savingStore.load();
				  
				    }
            }},
            {  title: '贷款账户信息',items:[qForm2,loanGrid],listeners : {
				'activate' : function() {
					var s = Ext.getCmp('south-panel');
					qForm.getForm().reset();
					s.collapse();
					//s.setVisible(false);
					//s.hidden=true;
					//s.remove();
					//s.collapse();
				    Ext.getCmp('tabs').setVisible(false);
				    loanstore.load();}
}}
        ]
    });
	//tabmain.getComponent('存款账户登记信息').body.on("click", function( ) { Ext.Msg.alert('提示', '234234'); }); 

	// 布局模型 
	var viewport = new Ext.Viewport(
			{
				layout:'fit',
				items:{
					layout : 'border',
					items: [{  region: 'south',
								id: 'south-panel',
								collapsible: true,
								//title: "资产负债表详细信息", 
								split: true,
								height: 350,
								minSize: 80,
								//maxSize: 200,
								collapsed : true,
								collapsible: true,
								//title: 'South',
								margins: '0 0 0 0',
								layout: 'fit',
								items:[tabs,tabs2]
							},{   
								region:'center',
								id: 'center-panel',
								margins: '0 0 0 0',
								layout: 'fit',
								items : [tabmain]
				    }] 
				}
			}
			
	);
	savingStore.load();
    function insert(){
    	if(Ext.getCmp('business_coeff').value==""&&Ext.getCmp('grade_coeff').value==""
    			){
    		Ext.Msg.alert('提示','请选则一个行业系数或评级系数!');
			return false;
    		
    	}
    	if(!panel2.getForm().isValid())
		{ 
			Ext.Msg.alert('提示','输入信息有误!');
			return false;
		}
    	var checkedNodes = loanGrid.getSelectionModel().selections.items;
  
        Ext.Ajax.request({
            url: basepath+'/loaninformation.json',
            method: 'POST',
            form:panel2.getForm().id,
    		success : function(response) {
				Ext.Msg.alert('提示', '操作成功');
			},
			failure : function(response) {
				Ext.Msg.alert('提示','操作失败' );
			},
          params : {
				'payaccount': checkedNodes[0].data.payaccount
				//parent.location.href.split("customerId=")[1]
			}
      /*      waitMsg : '正在保存数据,请等待...' */// 显示读盘的动画效果，执行完成后效果消失
           // success : checkResult,
           // failure : checkResult
        });
        addRoleWindow.hide();
           
    };

});