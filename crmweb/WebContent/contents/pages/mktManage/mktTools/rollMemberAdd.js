	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['正式', '0001'], ['注销', '0002'], ['全部', '0003']]
			});
	var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中型', '0002'], ['小型', '0003']]
			});
	var boxstore3 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['大型', '0001'], ['中小型', '0002'], ['其他', '0003'], ['全部', '0004']]
			});
	var boxstore4 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	var boxstore5 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['客户群1', '0001'], ['客户群2', '0002'], ['客户群3', '0003'], ['客户群4', '0004']]
			});
	var boxstore6 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['客户名单1', '0001'], ['客户名单2', '0002'], ['客户名单3', '0003'], ['客户名单4', '0004']]
			});
	var boxstore7 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['基础', '0001'], ['潜力', '0002'], ['核心', '0003'], ['顶级', '0004']]
			});
	  var addCustomerGroup=new Ext.FormPanel({
		// layout:'fit',

		frame:true,
		border:false,
		items : [{
					layout:'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户群名称',
									labelStyle:{
										width:'120px'
									},	
									Width:'100',
									id : 'affichenames',
									name : 'affichenames',
									anchor : '90%'
								}]
							}, 
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户群编号',
									labelStyle:{
										width:'120px'
									},	
									diabled:true,
									Width:'100',
									value:'0000001',
									id : 'levelss',
									name : 'levels',
									anchor : '90%'
								}]
							}]
				},{
					id : 'htmleditor',
					name : 'htmleditor',
					height:'100%',
					width:'80%',
					xtype : 'htmleditor',
					fieldLabel : '客户群描述'
				}]
	});
	
	 var simple = new Ext.FormPanel({
	        // labelWidth: 75,
	        frame:true,
			title : '<span style="font-weight:normal">高级查询条件</span>',
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        items: [{
	            	// xtype:'fieldset',
	            // title: '高级查询条件',
	           autoHeight:true,
	       
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户名称',
	                             labelStyle: 'text-align:right;',
	                             name: 'w1',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '行业',
	                             labelStyle: 'text-align:right;',
	                             name: 'w2',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户名单',
	                             labelStyle: 'text-align:right;',
	                             name: 'w3',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '归属网点',
	                             labelStyle: 'text-align:right;',
	                             name: 'w4',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '行业小类',
	                             labelStyle: 'text-align:right;',
	                             name: 'w5',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '是否本行股东',
	                             labelStyle: 'text-align:right;',
	                             name: 'w6',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '营业执照到期日期',
	                             labelStyle: 'text-align:right;',
	                             name: 'w7',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             
	                             fieldLabel: '组织机构代码',
	                             labelStyle: 'text-align:right;',
	                             xtype:'datefield',
	                             name: 'w8',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '考核口径客户规模',
	                             labelStyle: 'text-align:right;',
	                             name: 'w9',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '建立日期起始日',
	                             labelStyle: 'text-align:right;',
	                             name: 'w10',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户评级',
	                             labelStyle: 'text-align:right;',
	                             name: 'w11',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '行业三级分类明细',
	                             labelStyle: 'text-align:right;',
	                             name: 'w12',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '是否黑名单客户',
	                             labelStyle: 'text-align:right;',
	                             name: 'w13',
	                             anchor:'95%'
	                         },{
	                             
	                             fieldLabel: '资产总额起始金额',
	                             xtype:'datefield',
	                             labelStyle: 'text-align:right;',
	                             name: 'w14',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户状态',
	                             labelStyle: 'text-align:right;',
	                             name: 'w15',
	                             anchor:'95%'
	                         },{
	                            
	                             fieldLabel: '客户级别',
	                             xtype:'datefield',
	                             labelStyle: 'text-align:right;',
	                             name: 'w16',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '建立日期截止日',
	                             labelStyle: 'text-align:right;',
	                             name: 'w17',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '经济组织类型',
	                             labelStyle: 'text-align:right;',
	                             name: 'w18',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '是否集团客户',
	                             labelStyle: 'text-align:right;',
	                             name: 'w19',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '是否高新科技企业',
	                             labelStyle: 'text-align:right;',
	                             name: 'w20',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '销售额',
	                             labelStyle: 'text-align:right;',
	                             name: 'w21',
	                             anchor:'95%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户规模',
	                             labelStyle: 'text-align:right;',
	                             name: 'w22',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户群组',
	                             labelStyle: 'text-align:right;',
	                             name: 'w23',
	                             anchor:'95%'
	                         },{
	                             fieldLabel: '建立网点',
	                             xtype:'datefield',
	                             labelStyle: 'text-align:right;',
	                             name: 'w24',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '行业大类',
	                             labelStyle: 'text-align:right;',
	                             name: 'w25',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '是否上市公司',
	                             labelStyle: 'text-align:right;',
	                             name: 'w26',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '从业人员数',
	                             labelStyle: 'text-align:right;',
	                             name: 'w28',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '组织机构代码有效期到期日',
	                             labelStyle: 'text-align:right;',
	                             name: 'w27',
	                             anchor:'95%'
	                         }]
	                     }
	            ]}
	            ]}]
	    });

		var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			region:'north',
			// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 125,
			items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户名称',
										name : 'cust_zh_name',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									},new Ext.form.ComboBox({
										hiddenName : 'crm_scope',
										fieldLabel : '考核口径客户规模',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : boxstore3,
										displayField : 'name',
										valueField : 'code',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										value : '0000',
										resizable : true,
										anchor : '90%'
									}),new Ext.form.ComboBox({
										hiddenName : 'area1',
										fieldLabel : '客户名单',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : boxstore6,
										displayField : 'name',
										valueField : 'code',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										value : '0000',
										resizable : true,
										anchor : '90%'
									})
								]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '组织机构代码', // 标签
										name : 'cust_zzdm', // name:后台根据此name属性取值
										allowBlank : true, // 是否允许为空
										labelStyle: 'text-align:right;',
										anchor : '90%' // 宽度百分比
									},{
										fieldLabel : '行业', // 标签
										name : 'hy_class', // name:后台根据此name属性取值
										allowBlank : true, // 是否允许为空
										labelStyle: 'text-align:right;',
										anchor : '90%' // 宽度百分比
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Ext.form.ComboBox({
									hiddenName : 'sts',
									fieldLabel : '客户状态',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								}),new Ext.form.ComboBox({
									hiddenName : 'cust_lev',
									fieldLabel : '客户级别',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore7,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								})
							]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 110, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Ext.form.ComboBox({
										hiddenName : 'cust_scope',
										fieldLabel : '客户规模',
										labelStyle: 'text-align:right;',
										triggerAction : 'all',
										store : boxstore2,
										displayField : 'name',
										valueField : 'code',
										mode : 'local',
										forceSelection : true,
										typeAhead : true,
										value : '0000',
										resizable : true,
										anchor : '90%'
								}),new Ext.form.ComboBox({
									hiddenName : 'CUST_BASE_NAME',
									fieldLabel : '客户群组',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : boxstore5,
									displayField : 'name',
									valueField : 'code',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									value : '0000',
									resizable : true,
									anchor : '90%'
								})]
						}]
			}],
		buttons : [{
					text : '查询',
					handler : function() {
						store.reload({
							  params : {
                                   start : 0,
                                   limit : bbar.pageSize }} );
				
				   }},{
					text : '重置'
					/*
					 * handler : function() { }
					 */
					}]
		});
	 // 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	        {header : '客户id', dataIndex : 'cust_id',sortable : true,width : 150,hidden :true}, 
		    {header : '客户名称',dataIndex : 'cust_zh_name',width : 200},
		    {header : '客户简称',dataIndex : 'zh_abbr',width : 200},
		    {header : '英文名称',dataIndex : 'cust_en_name',width : 200},
		    {header : '英文简称',dataIndex : 'en_abbr'},
			{header : '组织机构代码',dataIndex : 'cust_zzdm',sortable : true,width : 150},
		    {header : '建立日期',dataIndex : 'bgn_dt',width : 150},
		    {header : '建立网点',dataIndex : 'create_org',width : 150},
		    {header : '归属网点',dataIndex : 'belong_instn',width : 150},
		    {header : '客户状态',dataIndex : 'sts',width : 150},
		    {header : '大客户级别',dataIndex : 'cust_big_lev',width : 150},
			{header : '中小客户级别',dataIndex : 'cust_small_lev',width : 150},
		    {header : '客户评级',dataIndex : 'cust_lev',width : 150},
		    {header : '基本账户银行', dataIndex : 'base_acct_bank',width : 150},
		    {header : '经济组织类型', dataIndex : 'jjzz_typ', width : 150},
		    {header : '所有者性质', dataIndex : 'syz_char', width : 150},
		    {header : '行业',dataIndex : 'hy_class',width : 150},
		    // {header : '细分行业',dataIndex : 'a16',width : 150},
		    // {header : '行业三级分类明细',dataIndex : 'a17',width : 150},
		    {header : '客户规模', dataIndex : 'cust_scope',width : 150},
		    {header : '考核口径客户规模',dataIndex : 'crm_scope',width : 150},
		    {header : '是否集团客户',dataIndex : 'group_flg',width : 150},
		    {header : '是否上市公司',dataIndex : 'market_flg',width : 150},
		    {header : '是否本行股东',dataIndex : 'specify_flg',width : 150},
		    {header : '是否黑名单客户',dataIndex : 'inblack_flg',width : 150},
		    {header : '是否高新科技企业',dataIndex : 'hn_flg',	width : 150},
		    {header : '核心中所属集团名称',dataIndex : 'group_name',width : 150},
		    {header : '核心中所属集团组织机构代码',dataIndex : 'group_zzdm',width : 150},
		    {header : '所属集团法定代表人（负责人）姓名',dataIndex :'group_frname',width : 150},
		    {header : '组织机构代码有效期到期日',dataIndex : 'zzdm_valdt',width : 150},
		    {header : '税务证号',dataIndex : 'tax_card',width : 150},
		    {header : '营业执照号',dataIndex : 'wk_lincese_no',width : 150},
		    {header : '营业执照到期日期',dataIndex : 'mature_dt',width : 150},
		    {header : '进出口经营权许可证书',dataIndex : 'foreigntradelicence',width : 150},
		    {header : '成立日期',dataIndex : 'create_dt',width : 150},
		    {header : '资产总额',dataIndex : 'ass_amt',width : 150},
		    // {header : '销售额',dataIndex : 'a35',width : 150},
		    {header : '从业人员数',dataIndex : 'employee_num',width : 150},
		    {header : '注册资金币种',dataIndex : 'cptl_cur',width : 150},
		    {header : '注册资金金额',dataIndex : 'cptl_amt',width : 150},
		    // {header : '注册地址',dataIndex : 'a39',width : 150},
		    // {header : '注册地址邮政编码',dataIndex : 'a40',width : 150},
		    // {header : '经营地址（办公地址）',dataIndex : 'a41',width : 150},
		    // {header : '经营地址邮政编码',dataIndex : 'a42',width : 150},
		    {header : '经营范围',dataIndex : 'business_rang',width : 150},
		    {header : '主营业务',dataIndex : 'imt_business',width : 150}
		    // {header : '所在国家(地区)',dataIndex : 'a45',width : 150},
		    // {header : '所在行政区域',dataIndex : 'a46',width : 150},
		    // {header : '所在区县',dataIndex : 'a47',width : 150},
		    // {header : '地区代码',dataIndex : 'a48',width : 150},
		    // {header : '客户联系电话',dataIndex : 'a49',width : 150},
		    // {header : '传真电话',dataIndex : 'a50',width : 150},
		    // {header : '网址',dataIndex : 'a51',width : 150},
		   // {header : '客户联系人1',dataIndex : 'a52',width : 150},
		   // {header : '电话1',dataIndex : 'a53',width : 150},
		   // {header : '客户联系人2',dataIndex : 'a54',width : 150},
		   // {header : '电话2',dataIndex : 'a55',width : 150}
			]);

	/**
	 * 数据存储
	 */
	 var store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerquery.json'
			      /*
					 * , success : function(response) { Ext.Msg.alert('提示',
					 * response.responseText); }
					 */
			        }),
			       reader: new Ext.data.JsonReader({
			       totalProperty : 'json.count',
			        root:'json.data'
			        }, [
						{name: 'cust_id'},
						{name: 'cust_zh_name'},
						{name: 'zh_abbr'},
						{name: 'cust_en_name'},
						{name: 'en_abbr'},
						{name: 'cust_zzdm'},
						{name: 'bgn_dt'},
						{name: 'create_org'},
						{name: 'belong_instn'},
						{name: 'sts'},
						{name: 'cust_big_lev'},
						{name: 'cust_small_lev'},
						{name: 'cust_lev'},
						{name: 'base_acct_bank'},
						{name: 'jjzz_typ'},
						{name: 'syz_char'},
						{name: 'hy_class'},
						{name: 'crm_scope'},
						{name: 'group_flg'},
						{name: 'market_flg'},
						{name: 'specify_flg'},
						{name: 'inblack_flg'},
						{name: 'hn_flg'},
						{name: 'group_name'},
						{name: 'group_zzdm'},
						{name: 'group_frname'},
						{name: 'zzdm_valdt'},
						{name: 'tax_card'},
						{name: 'wk_lincese_no'},
						{name: 'mature_dt'},
						{name: 'foreigntradelicence'},
						{name: 'create_dt'},
						{name: 'ass_amt'},
						{name: 'employee_num'},
						{name: 'cptl_cur'},
						{name: 'cptl_amt'},
						{name: 'business_rang'},
						{name: 'imt_business'}
					])
				});
	  
        store.on('beforeload', function() {
        	var conditionStr =  qForm.getForm().getValues(false);
            this.baseParams = {
                    "condition":Ext.encode(conditionStr)
            };
	});
	
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
         resizable : true,
         width : 85
     });
    var number = parseInt(pagesize_combo.getValue());
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        // plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	// 表格工具栏****************************************************************************************
	
	var tbar = new Ext.Toolbar({
				items : [
				{
					text : '加入名单',
					handler : function() {
						var selectLength = grid.getSelectionModel()
						.getSelections().length;
						
				if (selectLength < 1) {
					alert('请选择需要加入名单的客户');
				} else {
					
						if (confirm("确定将选择的所有客户加入名单吗?")) {
							var selectRe;
							var tempId;
							var idStr = '';
							for ( var i = 0; i < selectLength; i++) {
								selectRe = grid.getSelectionModel()
										.getSelections()[i];
								tempId = selectRe.data.cust_id;
								idStr += "'",
								idStr += tempId;
								idStr += "'";
								if (i != selectLength - 1)
									idStr += ",";
							};
							Ext.Ajax
									.request({
										url : basepath
												+ '/roll-member/1'
												+ '/batchCreate.json?idStr=' + idStr+'&rollId='+document.getElementById("rollIdStr").value,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function() {
											
											Ext.Msg.alert('提示', '操作成功');
//											custStore.reload({
//												params : {
//													rollId : document.getElementById('rollIdStr').value,
//													start : 0,
//													limit : parseInt(cust_pagesize_combo.getValue())
//												}
//											});
											
										},
										failure : function(response) {
											Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
										}
									});
						}
						;
					}
			}
				}			
				
				]
			});


	// 表格实例
	var grid = new Ext.grid.GridPanel({
				height :310,
				width : 200,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : tbar, // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	grid.on('rowdblclick', function(grid, rowIndex, event) {
		 window.location.href = 'customerViewIndex.html' ;
	});
		
    // 查询条件
	    var simpleCompare = new Ext.FormPanel({
		    labelAlign: 'right',
		    buttonAlign:'center',
	        frame:true,
	       // bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	        	xtype:'fieldset',
	            title: '查询条件',
	           autoHeight:true,
	            items :[{
	            	layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         labelWidth : 80, // 标签宽度
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '开始时间点',
	                             labelStyle: 'text-align:right;',
	                             name: 'first',
	                             anchor:'100%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         labelWidth :80, // 标签宽度
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '结束时间点',
	                             labelStyle: 'text-align:right;',
	                             name: 'last',
	                             anchor:'100%'
	                         }]
	                     },{columnWidth:.25,
							layout:'form',
							labelWidth:80,
							items:[{
								id:'productStatus',
								name:'productStatus',
								xtype:'combo',
								anchor:'100%',
								fieldLabel:'对比项目',
								triggerAction:'all',
							mode:'local',
							store: new Ext.data.ArrayStore({
					        id: 0,
					        fields: [
					            'myId',
					            'displayText'
					        ],
					        data: [[1, '存款时点'],[2,'存款日均'],[3,'贷款时点'],[4,'贷款日均'],[5,'理财产品'],[6,'贡献度']]
					 	   }),
					       valueField:'myId',
					       displayField:'displayText'
							}]
					  }]
	           }],

				buttons: [{
					text: '预览'
				},{
					text: '保存'	            
				}]
	    });
// // 布局模型
// var viewport = new Ext.Viewport({
// layout : 'border',
// items: [{
// region: 'north',
// id: 'north-panel',
// title: "客户管理->客户查询",
// height: 148,
// hidden:false,
// margins: '0 0 0 0',
// //layout: 'fit',
// items:[qForm]
// },{
// region:'center',
// id: 'center-panel',
// margins: '0 0 0 0',
// items : [grid]
// }]
//
// });
	var add= function(){
	 	var checkedNodes = grid.getSelectionModel().selections.items;
	 	var json={'cust_id':[]};
	 	var json1={'cust_lev':[]};
	 	var json2={'cust_zh_name':[]};
	 	var json3={'cust_zzdm':[]};
		
			if(checkedNodes.length==0)
			{
				Ext.Msg.alert('提示', '未选择任何客户');
				return ;
			}
			debugger;
			for(var i=0;i<checkedNodes.length;i++)
			{
				json.cust_id.push(checkedNodes[i].data.cust_id);
				json1.cust_lev.push(checkedNodes[i].data.cust_lev);
				json2.cust_zh_name.push(checkedNodes[i].data.cust_zh_name);
				json3.cust_zzdm.push(checkedNodes[i].data.cust_zzdm);
			}
			Ext.Msg.alert('提示', '1');
			Ext.Ajax.request({
						url:basepath+'/customer-attention.json',
                        method: 'POST',
                        success : function(response) {
					    	var resultArray  =response.status;
					    	if(response.status==201||response.status==200)
								Ext.Msg.alert('提示', '设置成功');
					    	else{Ext.Msg.alert('提示', '设置失败');}
							},
						params : {
							'cust_id':Ext.encode(json),
							'cust_lev': Ext.encode(json1),
							'cust_zh_name': Ext.encode(json2),
							'cust_zzdm': Ext.encode(json3),
							'operate':'add'
						}});
	
	};	
