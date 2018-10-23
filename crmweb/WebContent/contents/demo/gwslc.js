/**
 *财务健康诊断
 */
Ext.onReady(function() {
	
			var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 80,
			items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户号',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									}
								]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户名称',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									}]
						},{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '风险等级',
								typeAhead : true,
								triggerAction : 'all',
								lazyRender : true,
								listClass : 'x-combo-list-small',
								mode : 'local',
								name : 'c1',
								xtype : 'combo', // 设置为数字输入框类型
								store :new Ext.data.ArrayStore({
									fields :['key','value'],
									data :[[1,'保守型'],[2,'稳健偏保守型'],[3,'稳健型'],[4,'稳健偏积极型'],[5,'积极进取型']]
								}),
								valueField :'key',
								displayField :'value',
								editable :false,
								emptyText :'请选择',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						},{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							border : false,
							items : [{
										fieldLabel : '方案日期',
										name : 'c1',
										xtype : 'datefield', 
										labelStyle: 'text-align:right;',
										anchor : '90%'
									}]
						}]
			}],
		buttons : [{
					text : '查询'
					/*handler : function() {
					}*/
				   },{
					text : '重置',
					handler : function() {
						qForm.form.reset();
					}
					}]
		});
	// 每页显示条数下拉选择框
    var pagesize_combo = new Ext.form.ComboBox({
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
    var number = parseInt(pagesize_combo.getValue());
    // 改变每页显示条数reload数据
    pagesize_combo.on("select", function(comboBox) {
        bbar.pageSize = parseInt(comboBox.getValue());
        number = parseInt(comboBox.getValue());
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
        store : tb_store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	/**
	 *财务查询信息
	 */
	var tb_sm = new Ext.grid.CheckboxSelectionModel();
	var tb_rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var tb_cm = new Ext.grid.ColumnModel([tb_rownum,tb_sm, 
	           {
				header : '客户号',
				dataIndex : 'b1',
					width:200,
				sortable : true
			}, {
				header : '客户名称',
				dataIndex : 'b2',
					width:200,
					align : 'center',
				sortable : true
			},{
				header : '风险等级',
				dataIndex : 'b3',
					width:200,
				sortable : true
			},{
			
				header :'行内资产',
				dataIndex :'b4',
				width :200,
				align : 'right',
				sortable :true
			},{
				header :'行外资产',
				dataIndex :'b5',
				width :200,
				align : 'right',
				sortable :true
			},{
			
				header :'方案资产',
				dataIndex :'b6',
				width :200,
				align : 'right',
				sortable :true
			},{
			
				header :'客户经理',
				dataIndex :'b7',
				width :200,
				align : 'center',
				sortable :true
			},{
			
				header :'方案日期',
				dataIndex :'b8',
				width :200,
				align : 'right',
				sortable :true
			}
			]);
	var tb_store = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'b1' // Json中的属性Key值
								}, {
									name : 'b2'
								}, {
									name : 'b3'
								},{
									name : 'b4'
									
								},{
									name : 'b5'	
								},{
									name : 'b6'
								},{
									name : 'b7'
								},{
									name : 'b8'
								}])
			});
	var tb_memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","b1":"1001553461","b2":"朱峰","b3":"保守型","b4":"53,000.00","b5":"4,000.00","b6":"100,000.00","b7":"支行客户经理","b8":"2012-02-29"},
			{"rownum":"1","b1":"1001553472","b2":"张鹏","b3":"稳健偏保守型","b4":"3,123,235.00","b5":"1,324,125.00","b6":"600,000.00","b7":"支行客户经理","b8":"2012-02-29"},
			{"rownum":"1","b1":"1001553483","b2":"杨荣","b3":"稳健型","b4":"6,345,190.00","b5":"2,312,123.00","b6":"100,000.00","b7":"支行客户经理","b8":"2012-02-29"},
			{"rownum":"1","b1":"1001553494","b2":"张伟","b3":"稳健偏积极型","b4":"634,776,934.00","b5":"1,564,875.00","b6":"210,000.00","b7":"支行客户经理","b8":"2012-02-29"},
			{"rownum":"1","b1":"1001553505","b2":"王宇","b3":"积极进取型","b4":"634,908,378.00","b5":"5,312,000.00","b6":"120,000.00","b7":"支行客户经理","b8":"2012-02-29"},
			{"rownum":"1","b1":"1001553516","b2":"王萍","b3":"稳健偏保守型","b4":"234,389,234.00","b5":"6,567,345.00","b6":"200,000.00","b7":"支行客户经理","b8":"2012-02-29"},
			{"rownum":"1","b1":"1001553527","b2":"徐波","b3":"稳健型","b4":"1,241,156.00","b5":"1,435,123.00","b6":"110,000.00","b7":"支行客户经理","b8":"2012-02-29"}
			]
		};
	tb_store.loadData(tb_memberData);
	var tb_grid = new Ext.grid.GridPanel({
				height : 400,
				title :'客户信息列表',
				frame : true,
				overflow :'auto',
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : tb_store, // 数据存储
				stripeRows : true, // 斑马线
				cm : tb_cm, // 列模型
				sm : tb_sm, // 复选框
				tbar : [{
						text :'规划向导',
						handler:function(){
						if(nullRecordCheckout(tb_grid)==true){	
								form_set_win.show();
						}
						}
				},{
					text : '查看详情',
					handler :function(){
					if(nullRecordCheckout(tb_grid)==true){
						var record = tb_grid.getSelectionModel().getSelected();
						info_form.getForm().loadRecord(record);
						info_win.show();
					}
					}
				}],
				bbar :bbar,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
			
		
			var info_form = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'right', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 400,
			items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户号',
										name : 'b1',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									},{
										fieldLabel : '风险等级',
										name : 'b3',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									},{
										fieldLabel : '行外资产',
										name : 'b5',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									},{
										fieldLabel : '客户经理',
										name : 'b7',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									}
								]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 100, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户名称',
										name : 'b2',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									},{
										fieldLabel : '行内资产',
										name : 'b4',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									},{
										fieldLabel : '方案资产',
										name : 'b6',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									},{
										fieldLabel : '方案日期',
										name : 'b8',
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '90%'
									}]
						}]
			}],
		buttons : [{
					text : '关闭',
					handler : function() {
						info_form.form.reset();
						info_win.hide();
					}
					}]
		});	
		
	var info_win = new Ext.Window({
		plain : true,
		defaults :{
		overflow :'auto',
		autoScroll :true
		},
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 800,
		height : 300,
		buttonAlign : "center",
		title : '信息详情',
		items : [info_form]});
		
		
	
		var  form_set_1= new Ext.form.FieldSet({
				        title: '客户信息',
				        height :100,
				        layout:'column',
				        labelAlign:'right',
				        items:[{
				        		columnWidth:0.3,
				        		layout:'form',
				        		items:[{
								fieldLabel : '客户名称',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'朱峰',
								anchor : '90%'
							 },{
								fieldLabel : '行内资产',
								name : 'c1',
								value :'53,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				        		
				        },{
				        		columnWidth:0.3,
				        		layout:'form',
				        		items:[{
								fieldLabel : '核心客户号',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'1001553461',
								anchor : '90%'
							 },{
								fieldLabel : '行外资产',
								name : 'c1',
								value :'4,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				        		
				        },{
				        		columnWidth:0.3,
				        		layout:'form',
				        		items:[{
								fieldLabel : '风险等级',
								typeAhead : true,
								triggerAction : 'all',
								lazyRender : true,
								listClass : 'x-combo-list-small',
								mode : 'local',
								name : 'c1',
								xtype : 'combo', // 设置为数字输入框类型
								store :new Ext.data.ArrayStore({
									fields :['key','value'],
									data :[[1,'保守型'],[2,'稳健偏保守型'],[3,'稳健型'],[4,'稳健偏积极型'],[5,'积极进取型']]
								}),
								valueField :'key',
								displayField :'value',
								editable :false,
								emptyText :'保守型',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
				        		
				        }]
				    });
				    var  form_set_2 = new Ext.form.FieldSet({
				        title: '配置需求',
				        height :260,
				        	labelAlign:'right',
				        	labelSeparator :':',
				        	labelWidth:160,
				        items:[{
								fieldLabel : '投机',
								name : 'c1',
								xtype : 'textfield', 
								anchor : '90%'
							 },{
								fieldLabel : '投资（长期）',
								name : 'c1',
								xtype : 'textfield', 
								anchor : '90%'
							 },{
								fieldLabel : '退休金',
								name : 'c1',
								xtype : 'textfield', 
								anchor : '90%'
							 },{
								fieldLabel : '储备（短期）',
								name : 'c1',
								xtype : 'textfield', 
								anchor : '90%'
							 },{
								fieldLabel : '流动资金',
								name : 'c1',
								xtype : 'textfield', 
								anchor : '90%'
							 },{
								fieldLabel : '保护',
								name : 'c1',
								xtype : 'textfield', 
								anchor : '90%'
							 }]
				    });
				    
				    var form_set = new Ext.form.FormPanel({
				    	buttonAlign:'right',
				    	frame :true,
				    	layout:'form',
				    	items:[form_set_1,form_set_2],
				    	buttons:[{
				    		   text:'取消',
				    		   handler:function(){
				    		   		form_set.form.reset();
				    		   		form_set_win.hide();
				    		   }
				    	},{
				    			text:'保存，下一步',
				    			handler:function(){
				    				form_set_next_win.show();
				    			}
				    	}]
				    });
var form_set_win = new Ext.Window({
		plain : true,
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 800,
		height : 400,
		buttonAlign : "center",
		title : '规划向导',
		items : [form_set]});
		
				var  form_set_next_1= new Ext.form.FieldSet({
				        title: '客户信息',
				        height :100,
				        layout:'column',
				        labelAlign:'right',
				        items:[{
				        		columnWidth:0.25,
				        		layout:'form',
				        		items:[{
								fieldLabel : '客户名称',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'朱峰',
								anchor : '90%'
							 },{
								fieldLabel : '行内资产',
								name : 'c1',
								value :'53,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				        		
				        },{
				        		columnWidth:0.25,
				        		layout:'form',
				        		items:[{
								fieldLabel : '核心客户号',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'1001553461',
								anchor : '90%'
							 },{
								fieldLabel : '行外资产',
								name : 'c1',
								value :'4,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				        		
				        },{
				        		columnWidth:0.25,
				        		layout:'form',
				        		items:[{
								fieldLabel : '风险等级',
								typeAhead : true,
								triggerAction : 'all',
								lazyRender : true,
								listClass : 'x-combo-list-small',
								mode : 'local',
								name : 'c1',
								xtype : 'combo', // 设置为数字输入框类型
								store :new Ext.data.ArrayStore({
									fields :['key','value'],
									data :[[1,'保守型'],[2,'稳健偏保守型'],[3,'稳健型'],[4,'稳健偏积极型'],[5,'积极进取型']]
								}),
								valueField :'key',
								displayField :'value',
								editable :false,
								emptyText :'保守型',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},{
								fieldLabel : '方案资产',
								name : 'c1',
								value :'4,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				        		
				        },{
				        		columnWidth:0.25,
				        		layout:'form',
				        		items:[{
								fieldLabel : '目标总资产',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				        		
				        }]
				    });
				    	var type_form = new Ext.Panel({
//				    			width :200,
				    			frame : true,
				    			labelWidth:60,
				    			labelAlign:'right',
				    			layout:'form',
				    		  items:[{
								fieldLabel : '投机',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 },{
								fieldLabel : '投资（长期）',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 },{
								fieldLabel : '退休金',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 },{
								fieldLabel : '储备（短期）',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 },{
								fieldLabel : '流动资金',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 },{
								fieldLabel : '保护',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								anchor : '90%'
							 }]
				    		
				    	});
				    	
	var title_sm = new Ext.grid.CheckboxSelectionModel();
	var title_rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var title_cm = new Ext.grid.ColumnModel([title_rownum,title_sm, 
	           {
				header : '需求类型',
				dataIndex : 'b1',
				width:80,
				sortable : true
			}, {
				header : '目标名称',
				dataIndex : 'b2',
				width:80,
				sortable : true
			},{
				header : '目标规模',
				dataIndex : 'b3',
				width:80,
				sortable : true
			},{
			
				header :'创建人',
				dataIndex :'b4',
				width:80,
//				align : 'right',
				sortable :true
			},{
				header :'创建日期',
				width:80,
				dataIndex :'b5',
//				align : 'right',
				sortable :true
			}
			]);
	var title_store = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'b1' // Json中的属性Key值
								}, {
									name : 'b2'
								}, {
									name : 'b3'
								},{
									name : 'b4'
									
								},{
									name : 'b5'	
								}])
			});
	var title_memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","b1":"投资（长期）","b2":"test1","b3":"2,222.00","b4":"admin","b5":"2012-02-01"},
			{"rownum":"1","b1":"流动资金","b2":"test2","b3":"1,000,000.00","b4":"admin","b5":"2012-02-01"},
			{"rownum":"1","b1":"保护","b2":"test3","b3":"150,000.00","b4":"admin","b5":"2012-02-01"},
			{"rownum":"1","b1":"保护","b2":"test4","b3":"12.00","b4":"admin","b5":"2012-02-01"},
			{"rownum":"1","b1":"储备（短期）","b2":"text5","b3":"15.00","b4":"admin","b5":"2012-02-01"}
			]
		};
	title_store.loadData(title_memberData);
	var title_grid = new Ext.grid.GridPanel({
				height : 260,
				frame : true,
				overflow :'auto',
				autoScroll : true,
				store : title_store, // 数据存储
				stripeRows : true, // 斑马线
				cm : title_cm, // 列模型
				sm : title_sm, // 复选框
				tbar : [{
					text : '新增目标',
					handler :function(){
					save_win.show();
					}
				},{
					text : '产品组合',
					handler : function(){
					products_win.show();
					}
				}],
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
			
			var save_form = new Ext.form.FormPanel({
					labelWidth : 100, // 标签宽度
					frame : true, //是否渲染表单面板背景色
					labelAlign : 'middle', // 标签对齐方式
					buttonAlign : 'center',
					height : 200,
					items:[{
								fieldLabel : '需求类型',
								typeAhead : true,
								triggerAction : 'all',
								lazyRender : true,
								listClass : 'x-combo-list-small',
								mode : 'local',
								name : 'c1',
								xtype : 'combo', // 设置为数字输入框类型
								store :new Ext.data.ArrayStore({
									fields :['key','value'],
									data :[[1,'投机'],[2,'投资（长期）'],[3,'退休金'],[4,'储备（短期）'],[5,'流动资金'],[6,'保护']]
								}),
								valueField :'key',
								displayField :'value',
								editable :false,
								emptyText :'请选择',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},{
										fieldLabel : '目标名称',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									},{
										fieldLabel : '核心客户号',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									},{
										fieldLabel : '目标规模',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									},{
										fieldLabel : '目标介绍',
										name : 'c1',
										xtype : 'textarea', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									}],
									buttons:[{
											text:'保存',
											handler:function(){
												Ext.Msg.alert('消息框','保存成功！');											
											}
									},{
										text:'重置',
										handler:function(){
											save_form.form.reset();
										}
									},{
											text:'返回',
											handler:function(){
												save_form.form.reset();
											    save_win.hide();
											}
									}]
									
			});
	var save_win = new Ext.Window({
		plain : true,
		defaults :{
		overflow :'auto',
		autoScroll :true
		},
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 400,
		height : 260,
		buttonAlign : "center",
		title : '新增目标',
		items : [save_form]});
		
		var product_sm = new Ext.grid.CheckboxSelectionModel();
	var product_rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var product_cm = new Ext.grid.ColumnModel([product_rownum,product_sm, 
	           {
				header : '产品编码',
				dataIndex : 'b1',
				width:80,
				sortable : true
			}, {
				header : '产品名称',
				dataIndex : 'b2',
				width:80,
				sortable : true
			},{
				header : '产品规模',
				dataIndex : 'b3',
				width:80,
				editor:new Ext.form.Field(),
				sortable : true
			},{
			
				header :'产品风险等级',
				dataIndex :'b4',
				width:80,
//				align : 'right',
				sortable :true
			}
			]);
	var product_store = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'b1' // Json中的属性Key值
								}, {
									name : 'b2'
								}, {
									name : 'b3'
								},{
									name : 'b4'
									
								}])
			});
	var product_memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","b1":"31501","b2":"兴业趋势","b3":"50,000.00","b4":"低风险产品"},
			{"rownum":"2","b1":"31502","b2":"量化核心","b3":"100,000.00","b4":"中低风险产品"},
			{"rownum":"3","b1":"31503","b2":"华夏大盘","b3":"150,000.00","b4":"中等风险产品"},
			{"rownum":"4","b1":"31504","b2":"天威保变","b3":"100,000.00","b4":"中高风险产品"},
			{"rownum":"5","b1":"31505","b2":"黄金期货","b3":"200,000.00","b4":"高风险产品"}
			]
		};
	product_store.loadData(product_memberData);
	var product_grid = new Ext.grid.EditorGridPanel({
				height : 260,
				frame : true,
				overflow :'auto',
				autoScroll : true,
				store : product_store, // 数据存储
				stripeRows : true, // 斑马线
				cm : product_cm, // 列模型
				sm : product_sm, // 复选框
				tbar : [{
					text : '添加',
					handler :function(){
						sub_products_win.show();
					}
				},{
					text : '移除',
					handler : function(){
						
					}
				}],
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
			
			var sub_product_form = new Ext.form.FormPanel({
			labelWidth : 100, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'right', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 80,
			items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
										fieldLabel : '产品编号',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										width:100,
										anchor : '90%'
									}
								]
						}, {
							columnWidth : .33,
							layout : 'form',
							border : false,
							items : [{
										fieldLabel : '产品名称',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										width:100,
										anchor : '90%'
									}]
						},{
							columnWidth : .33,
							layout : 'form',
							width:100,
							items : [{
								fieldLabel : '产品风险等级',
								typeAhead : true,
								triggerAction : 'all',
								lazyRender : true,
								mode : 'local',
								name : 'c1',
								xtype : 'combo', // 设置为数字输入框类型
								store :new Ext.data.ArrayStore({
									fields :['key','value'],
									data :[[1,'低风险产品'],[2,'中低风险产品'],[3,'中等风险产品'],[4,'中高风险产品'],[5,'高风险产品']]
								}),
								valueField :'key',
								displayField :'value',
								editable :false,
								emptyText :'请选择',
								anchor : '90%'
							}]
						}]
			}],
		buttons : [{
					text : '查询'
					/*handler : function() {
					}*/
				   },{
					text : '重置',
					handler : function() {
						sub_product_form.form.reset();
					}
					}]
		});
		var sub_product_sm = new Ext.grid.CheckboxSelectionModel();
	var sub_product_rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var sub_product_cm = new Ext.grid.ColumnModel([sub_product_rownum,sub_product_sm, 
	           {
				header : '产品编码',
				dataIndex : 'b1',
				width:160,
				sortable : true
			}, {
				header : '产品名称',
				dataIndex : 'b2',
				width:160,
				sortable : true
			},{
			
				header :'产品风险等级',
				dataIndex :'b4',
				width:160,
//				align : 'right',
				sortable :true
			}
			]);
	var sub_product_store = new Ext.data.Store({
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'b1' // Json中的属性Key值
								}, {
									name : 'b2'
								},{
									name : 'b4'
									
								}])
			});
	var sub_product_memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","b1":"31501","b2":"兴业趋势","b4":"低风险产品"},
			{"rownum":"2","b1":"31502","b2":"量化核心","b4":"中低风险产品"},
			{"rownum":"3","b1":"31503","b2":"华夏大盘","b4":"中等风险产品"},
			{"rownum":"4","b1":"31504","b2":"天威保变","b4":"中高风险产品"},
			{"rownum":"5","b1":"31505","b2":"黄金期货","b4":"高风险产品"}
			]
		};
	sub_product_store.loadData(sub_product_memberData);
	var sub_product_grid = new Ext.grid.GridPanel({
				height : 200,
				frame : true,
				overflow :'auto',
				autoScroll : true,
				store : sub_product_store, // 数据存储
				stripeRows : true, // 斑马线
				cm : sub_product_cm, // 列模型
				sm : sub_product_sm, // 复选框
				tbar : [{
					text : '确定',
					handler :function(){
						Ext.Msg.alert('消息框','产品组添加成功！');
						sub_products_win.hide();
					}
				}],
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});	
		
var sub_products_win = new Ext.Window({
		plain : true,
		defaults :{
		overflow :'auto',
		autoScroll :true
		},
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 600,
		height : 320,
		buttonAlign : "center",
		title : '产品列表',
		items : [{
			layout:'form',
			items:[sub_product_form,sub_product_grid]
		}]
		});
	var products_win = new Ext.Window({
		plain : true,
		defaults :{
		overflow :'auto',
		autoScroll :true
		},
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 400,
		height : 300,
		buttonAlign : "center",
		title : '产品组合',
		items : [product_grid],
		buttons:[{
			text:'保存',
			handler:function(){
					Ext.Msg.alert('消息框','保存成功！');
			}
		},{
			text:'取消',
			handler:function(){
				products_win.hide();
			}
		}]});
				    var  form_set_next_2= new Ext.form.FieldSet({
				    		frame : true,
				    		height : 300,
				    	   title :'目标配置',
				    	   layout:'column',
				    	   items:[{
				    	   		columnWidth:.35,
				    	   		items:[type_form]
				    	   },{
				    	   		columnWidth:.65,
				    	   		items:[title_grid]
				    	   }]
				    });
					var next_forms = new Ext.form.FormPanel({
						 frame : true,
					     items:[form_set_next_1,form_set_next_2]
					});
					
var baogao_win = new Ext.Window({
		plain : true,
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 700,
		height : 460,
		buttonAlign : "center",
		title : '生成报告信息',
		items : [{
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"baogao.htm\" "/> scrolling="auto"> </iframe>'
            }],
         buttons:[{
         		text :'打印'
         },{
         		text :'关闭',
         		handler:function(){
         			baogao_win.hide();
         		}
         }]
		});
	var form_set_next_win = new Ext.Window({
		plain : true,
		layout : 'fit',
		frame : true,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 800,
		height : 460,
		buttonAlign : "right",
		title : '规划向导',
		items : [next_forms],
		buttons:[{
				text:'上一步',
				handler: function(){
					form_set_next_win.hide();
					form_set_win.show();
				}
		},{
				text:'保存->生成报告',
				handler:function(){
					baogao_win.show();
					form_set_next_win.hide();
				}
		}]
		});
		
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "顾问式理财服务", 
				    height: 100,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qForm]
			     },{   
			    	region:'center',
				    id: 'center-panel',
				    margins: '0 0 0 0',
				    items : [tb_grid]
			    }] 

			});
	 var nullRecordCheckout = function(grid) {
	        var _record = grid.getSelectionModel().getSelections();
	       if(_record.length != 1){
	        	Ext.MessageBox.alert('提醒消息', '请选择要操作的一列！');
	        	return false;
	        }else{
	            return true;
	        }
	    };
		//拖动IE时.翻页条自适应
    Ext.EventManager.onWindowResize(function(){
        tb_grid.setHeight(document.body.scrollHeight-120);
        tb_grid.setWidth(document.body.scrollWidth);
        tb_grid.getView().refresh();
    });
}); 