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
										fieldLabel : '核心客户号',
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
								fieldLabel : '客户级别',
								typeAhead : true,
								triggerAction : 'all',
								lazyRender : true,
								listClass : 'x-combo-list-small',
								mode : 'local',
								name : 'c1',
								xtype : 'combo', // 设置为数字输入框类型
								store :new Ext.data.ArrayStore({
									fields :['key','value'],
									data :[[1,'金卡客户'],[2,'银卡客户'],[3,'VIP客户'],[4,'普通客户']]
								}),
								valueField :'key',
								displayField :'value',
								editable :false,
								emptyText :'请选择',
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
				header : '核心客户号',
				dataIndex : 'b1',
					width:200,
				sortable : true
			}, {
				header : '客户姓名',
				dataIndex : 'b2',
					width:200,
				sortable : true
			},{
				header : '客户级别',
				dataIndex : 'b3',
					width:200,
				sortable : true
			},{
			
				header :'本行资产',
				dataIndex :'b4',
				width :200,
				align : 'right',
				sortable :true
			},{
				header :'它行资产',
				dataIndex :'b5',
				width :200,
				align : 'right',
				sortable :true
			},{
			
				header :'其他资产',
				dataIndex :'b6',
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
									align : 'right',
									name : 'b4'
									
								},{
									align : 'right',
									name : 'b5'	
								},{
									align : 'right',
									name : 'b6'
								}])
			});
	var tb_memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","b1":"9100155346","b2":"朱峰","b3":"金卡客户","b4":"53,000.00","b5":"4,000.00","b6":"320,570.00"},
			{"rownum":"1","b1":"9100155347","b2":"张鹏","b3":"金卡客户","b4":"3,123,235.00","b5":"1,324,125.00","b6":""},
			{"rownum":"1","b1":"9100155348","b2":"杨荣","b3":"金卡客户","b4":"6,345,190.00","b5":"","b6":""},
			{"rownum":"1","b1":"9100155349","b2":"张伟","b3":"金卡客户","b4":"634,776,934.00","b5":"","b6":""},
			{"rownum":"1","b1":"9100155350","b2":"王宇","b3":"金卡客户","b4":"634,908,378.00","b5":"","b6":""},
			{"rownum":"1","b1":"9100155351","b2":"王萍","b3":"金卡客户","b4":"234,389,234.00","b5":"","b6":""},
			{"rownum":"1","b1":"9100155352","b2":"徐波","b3":"金卡客户","b4":"1,241,156.00","b5":"","b6":""}
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
					text : '查看详情',
					handler :function(){
					if(nullRecordCheckout(tb_grid)==true){
						infoWin.show();
					}
					}
				}],
				bbar :bbar,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
			
		// 定义自动当前页行号
		var teamrownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});
		// 定义列模型
		var teamcm = new Ext.grid.ColumnModel([teamrownum,
		           {
					header : '资产类型',
					dataIndex : 'c1',
						width:180,
					sortable : true
				}, {
					header : '金额',
					dataIndex : 'c2',
					editor :new Ext.form.Field(), 
						width:180,
					sortable : true
				}
				]);
		var teamstore = new Ext.data.Store({
					// 数据读取器
					reader : new Ext.data.JsonReader({
								totalProperty:'num',// 记录总数
								//idIndex:'blocName', 
								root:'rows'// Json中的列表数据根节点
							}, [{
										name : 'c1' // Json中的属性Key值
									}, {
										name : 'c2'
									}])
				});
		var teammemberData= {
				TOTALCOUNT:3,
				rows:[
				{"rownum":"1","c1":"现金","c2":""},
				{"rownum":"1","c1":"股票","c2":""},
				{"rownum":"1","c1":"信托","c2":""},
				{"rownum":"1","c1":"自用房产","c2":""},
				{"rownum":"1","c1":"投资房产","c2":""},
				{"rownum":"1","c1":"车辆","c2":""},
				{"rownum":"1","c1":"其他家具资产","c2":""},
				{"rownum":"1","c1":"公积金与社保","c2":""},
				{"rownum":"1","c1":"其他保险","c2":""},
				{"rownum":"1","c1":"债权","c2":""}
				]
			};
		teamstore.loadData(teammemberData);    
		// 表格实例
		var teamgrid = new Ext.grid.EditorGridPanel ({
					title :'资产信息',
					height : 300,
					frame : true,
					overflow :'auto',
					autoScroll : true,
					store : teamstore, // 数据存储
					stripeRows : true, // 斑马线
					cm : teamcm, // 列模型
					bbar :bbar,
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
				
					// 定义自动当前页行号
		var teamrownum2 = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});
		// 定义列模型
		var teamcm2 = new Ext.grid.ColumnModel([teamrownum2,
		           {
					header : '债务类型',
					dataIndex : 'c1',
						width:180,
					sortable : true
				}, {
					header : '金额',
					dataIndex : 'c2',
					editor :new Ext.form.TextField(), 
						width:180,
					sortable : true
				}
				]);
		var teamstore2 = new Ext.data.Store({
					// 数据读取器
					reader : new Ext.data.JsonReader({
								totalProperty:'num',// 记录总数
								//idIndex:'blocName', 
								root:'rows'// Json中的列表数据根节点
							}, [{
										name : 'c1' // Json中的属性Key值
									}, {
										name : 'c2'
									}])
				});
		var teammemberData2= {
				TOTALCOUNT:3,
				rows:[
				{"rownum":"1","c1":"债务","c2":""},
				{"rownum":"1","c1":"应付租金","c2":""},
				{"rownum":"1","c1":"应付费用","c2":""},
				{"rownum":"1","c1":"其他流动性负债","c2":""},
				{"rownum":"1","c1":"其他长期负债","c2":""}
				]
			};
		teamstore2.loadData(teammemberData2);    
		// 表格实例
		var teamgrid2 = new Ext.grid.EditorGridPanel ({
					title :'债务信息',
					height :  300,
					frame : true,
					overflow :'auto',
					autoScroll : true,
					store : teamstore2, // 数据存储
					stripeRows : true, // 斑马线
					cm : teamcm2, // 列模型
					bbar :bbar,
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
				
		var teamForm = new Ext.form.FormPanel({
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
										fieldLabel : '资产合计',
										name : 'c1',
										value:'0.0000',
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
										fieldLabel : '负债合计',
										name : 'c1',
										value:'0.0000',
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
										fieldLabel : '净资产',
										name : 'c1',
										value:'0.0000',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									}]
						}]
			}],
		buttons : [{
					text : '资产统计'
					/*handler : function() {
					}*/
				   },{
					text : '提交',
					handler : function() {
						Ext.Msg.alert("消息框","提交成功！");
					}
					}]
		});
		
		var tab_1 = new Ext.Panel({
					layout :'form',
					items :[teamForm,{
						layout :'column',
						items:[{
								columnWidth : .5,
								items :[teamgrid]
						},{
								columnWidth : .5,
								items :[teamgrid2]
						}]
					}]
		});
		
	var char_store1 = new Ext.data.JsonStore({
        fields: ['key', 'value'],
        data: [{
            key: '定期存款',
            value: 60
        },{
            key: '活期存款',
            value: 20
        },{
            key: '基金',
            value: 5
        },{
            key: '贵金属',
            value: 2
        },{
            key: '国债',
            value: 10
        },{
        	key: '理财产品总额',
            value: 2
        },{
        	key: '三方存管',
            value: 1
        }]
    });
    
		var  char_set_1 = new Ext.form.FieldSet({
			
					        height: 180,
					        title: '客户本行资产负债信息',
					        items: {
				            store: char_store1,
				            xtype: 'piechart',
				            dataField: 'value',
				            categoryField: 'key',
				            extraStyle:
				            {
				                legend:
				                {
				                    display: 'bottom',
				                    padding: 3,
				                    font:
				                    {
				                        family: 'Tahoma',
				                        size: 9
				                    }
				                }
				            }
				        }
		});
		
		var char_store2 = new Ext.data.JsonStore({
        fields: ['key', 'value'],
        data: [{
            key: '定期存款',
            value: 1382
        },{
            key: '活期存款',
            value: 216
        },{
            key: '其他贷款',
            value: 0
        },{
            key: '个人汽车贷款',
            value: 0
        },{
            key: '个人住房贷款',
            value: 1056
        },{
            key: '信用卡透支余额',
            value: 2112
        },{
            key: '其他投资资产',
            value: 261
        },{
            key: '贵金属',
            value: 1382
        },{
            key: '外币理财',
            value: 261
        },{
            key: '人民币理财',
            value: 1382
        },{
            key: '其他债券',
            value: 261
        },{
            key: '国债',
            value: 1382
        },{
            key: '基金',
            value: 261
        }]
    });
    
		var  char_set_2 = new Ext.form.FieldSet({
					        height: 180,
					        title: '客户它行资产负债信息',
					        items: {
				            store: char_store2,
				            xtype: 'piechart',
				            dataField: 'value',
				            categoryField: 'key',
				            extraStyle:
				            {
				                legend:
				                {
				                    display: 'bottom',
				                    padding: 3,
				                    font:
				                    {
				                        family: 'Tahoma',
				                        size: 9
				                    }
				                }
				            }
				        }
		});
		
		 var char_store3 = new Ext.data.JsonStore({
        fields:['name', 'visits', 'views'],
        data: [
            {name:'现金', visits: 245000, views: 3000000},
            {name:'股票', visits: 0, views: 3000000},
            {name:'信托', visits: 0, views: 3000000},
            {name:'自用房产', visits: 0, views: 3000000},
            {name:'投资房产', visits: 0, views: 3000000},
            {name:'车辆', visits: 0, views: 3000000},
            {name:'其他家具资产', visits: 0, views: 3000000},
            {name:'公积金与社保', visits: 0, views: 3000000},
            {name:'其他保险', visits: 0, views: 3000000},
            {name:'债权', visits: 0, views: 3000000},
            {name:'债务', visits: 0, views: 30000000},
            {name:'应付租金', visits: 0, views: 3000000},
            {name:'应付费用', visits: 0, views: 3000000},
            {name:'其他流动性负债', visits: 0, views: 3000000},
            {name:'其他长期负债', visits: 0, views: 3000000}
        ]
    });
    	//折线
    	var  char_set_3 = new Ext.form.FieldSet({
		        title: '客户其他资产负债信息',
		        height:300,
		        items: {
		            xtype: 'columnchart',
		            store: char_store3,
		            xField: 'name',
		            yField: 'visits',
		             extraStyle: {  
				            xAxis: {  
				                labelRotation: 270//x轴标题旋转度数  
				            }  
//				            yAxis: {  
//				                labelRotation: //y轴标题旋转度数  
//				            }  
				        },    
					listeners: {
						itemclick: function(o){
							var rec = store.getAt(o.index);
							Ext.example.msg('Item Selected', 'You chose {0}.', rec.get('name'));
						}
					}
		        }
		    });
		    
		     var char_store4 = new Ext.data.JsonStore({
        fields:['name', 'visits', 'views'],
        data: [
            {name:'您的工作（或退休）收入', visits: 24500, views: 3000000},
            {name:'配偶工作（或退休）收入', visits: 24000, views: 3500000},
            {name:'租金', visits: 3550, views: 4000000},
            {name:'投资收入', visits: 3750, views: 4200000},
            {name:'按揭贷款', visits: 4950, views: 5800000},
            {name:'日常生活支出', visits: 5200, views: 6000000},
            {name:'子女教育', visits: 6200, views: 7500000},
            {name:'娱乐支出费用', visits: 6200, views: 7500000},
            {name:'赡养父母费用', visits: 6000, views: 7500000},
            {name:'保费支出', visits: 1000, views: 7500000},
            {name:'其他', visits: 1230, views: 4500000}
        ]
    });
    	//折线
    	var  char_set_4 = new Ext.form.FieldSet({
		        title: '客户家庭月度收支信息',
		        height:300,
		        items: {
		            xtype: 'columnchart',
		            store: char_store4,
		            xField: 'name',
		            yField: 'visits',
		            extraStyle: {  
				            xAxis: {  
				                labelRotation: 270//x轴标题旋转度数  
				            }  
//				            yAxis: {  
//				                labelRotation: //y轴标题旋转度数  
//				            }  
				        },    
					listeners: {
						itemclick: function(o){
							var rec = store.getAt(o.index);
							Ext.example.msg('Item Selected', 'You chose {0}.', rec.get('name'));
						}
					}
		        }
		    });
		var  char_set_5 = new Ext.form.FieldSet({
				        title: '客户本行资产负债',
				        items:[{
								fieldLabel : '本行资产合计',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'53,000.00',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '本行负债合计',
								name : 'c1',
								value :'0.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '本行净资产',
								name : 'c1',
								value:'53,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 }]
				    });
				    var  char_set_6 = new Ext.form.FieldSet({
				        title: '客户它行资产负债',
				        items:[{
								fieldLabel : '它行资产合计',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'4,000.00',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '它行负债合计',
								name : 'c1',
								value :'0.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '它行净资产',
								name : 'c1',
								value:'4,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 }]
				    });
				    var  char_set_7 = new Ext.form.FieldSet({
				        title: '客户其他资产负债',
				        items:[{
								fieldLabel : '其他资产合计',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'320,570.00',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '其他负债合计',
								name : 'c1',
								value :'0.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '其他净资产',
								name : 'c1',
								value:'320,570.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 }]
				    });
				    var  char_set_8 = new Ext.form.FieldSet({
				        title: '月度收支信息',
				        items:[{
								fieldLabel : '月度总收入',
								name : 'c1',
								xtype : 'textfield', // 设置为数字输入框类型
								value :'8,000.00',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '月度总支出',
								name : 'c1',
								value :'5,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 },{
								fieldLabel : '盈余',
								name : 'c1',
								value:'3,000.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							 }]
				    });
				   var  char_set_9 = new Ext.form.FieldSet({
				   	title :'客户资产汇总',
				   		layout:'column',
				        items:[{
				        		columnWidth :0.25,
				        		layout :'form',
								items:[{
									fieldLabel : '客户总资产',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									value :'380,570.00',
									labelStyle: 'text-align:right;',
									anchor : '90%'
									}]
								
							 },{columnWidth :0.25,
				        		layout :'form',
								items:[{
									fieldLabel : '客户总负债资产',
									name : 'c1',
									value :'0.00',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								}]
							 },{columnWidth :0.5,
				        		layout :'form',
								items:[{
								fieldLabel : '净资产',
								name : 'c1',
								value:'380,570.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
								}]
							 }]
				    });
			var tab_2 = new Ext.Panel({
				title :'客户资产负债信息（单位：元）',
				overflow :'auto',
				items:[{
					layout :'column',
					items:[{
						layout:'form',
						columnWidth :0.5,
						items:[char_set_1,char_set_3,{
							layout :'column',
							items:[{
								columnWidth :0.5,
								items:[char_set_5]
							},{
								columnWidth :0.5,
								items:[char_set_6]
							}]
						
						}]	
					},{
						layout:'form',
						columnWidth :0.5,
						items:[char_set_2,char_set_4,{
							layout :'column',
							items:[{
								columnWidth :0.5,
								items:[char_set_7]
							},{
								columnWidth :0.5,
								items:[char_set_8]
							}]
						
						}]	
					}]
				},char_set_9]
			});
			
			
				var cm_form_set_1 = new Ext.grid.ColumnModel([
		           {
					header : '指标',
					dataIndex : 'c1',
					width :200,
					sortable : true
				}, {
					header : '含义',
					width :300,
					dataIndex : 'c2',
//					editor :new Ext.form.Field(), 
					sortable : true
				},{
					header : '建议',
					width :200,
					dataIndex : 'c3',
//					editor :new Ext.form.Field(), 
					sortable : true
				},{
					header : '现值',
					width :200,
					dataIndex : 'c4',
					align :'right',
//					editor :new Ext.form.Field(), 
					sortable : true
				}
				]);
		var store_form_set_1 = new Ext.data.Store({
					// 数据读取器
					reader : new Ext.data.JsonReader({
								totalProperty:'num',// 记录总数
								//idIndex:'blocName', 
								root:'rows'// Json中的列表数据根节点
							}, [{
										name : 'c1' // Json中的属性Key值
									}, {
										name : 'c2'
									},{
										name : 'c3'
									},{
										name : 'c4'
									}])
				});
		var teammemberData_form_set_1= {
				TOTALCOUNT:3,
				rows:[
				{"rownum":"1","c1":"净资产","c2":"如果为负，则说明目前的财务状况不容乐观，有必要将近期的债务尽快偿还，同时尽快增加收入。","c3":"差值应该为正","c4":"2,178,590,49"},
				{"rownum":"2","c1":"总资产自有权益比例","c2":"如>0.5较为适宜。如果净资产偿付比例太低，意味着现在的生活靠借债来维持，一旦债务到期或经济不景气时，客户的资产出现损失，可能资不抵债。如果比例很高，接近1，意味着该客户可能没有充分利用自己的信用额度，通过借款来优化其财务结构。","c3":"控制在0.5较为适宜","c4":"0.59"},
				{"rownum":"3","c1":"总资产负债比例","c2":"建议客户控制在0.5以下，以减少由于资产流动性不足而出现财务危机的可能。","c3":"比率应该小于0.5","c4":"0.41"},
				{"rownum":"4","c1":"家庭偿债能力","c2":"健康的家庭偿债能力在1.5以上。","c3":"比率应该大于1.5","c4":"2.45"},
				{"rownum":"5","c1":"储蓄投资能力","c2":"健康的储蓄投资能力要保持在0.2-0.3以上。","c3":"比率应该在0.2-0.3之间","c4":"0.71"},
				{"rownum":"6","c1":"开源节流能力","c2":"此比率逐年下降为好。","c3":"此比率逐年下降为好。","c4":"0.29"},
				{"rownum":"7","c1":"资产收入比例","c2":"如<0.5，说明有必要控制开支，需要更多地进行储蓄或投资，同时努力工作使收入增加。如0.5<净资产收入比率<3，如客户尚年轻，则其财务状况良好，如客户接近退休年龄，有必要采取措施增加其净资产。如>3,客户目前的财务状况良好。","c3":"比率应该大于1.5","c4":"6.05"}
				]
			};
		store_form_set_1.loadData(teammemberData_form_set_1);    
		// 表格实例
		var grid_form_set_1 = new Ext.grid.GridPanel ({
					height : 190,
					frame : true,
					border : true,
					overflow :'auto',
					autoScroll : true,
					store : store_form_set_1, // 数据存储
//					stripeRows : true, // 斑马线
					cm : cm_form_set_1, // 列模型
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
			var  form_set_1 = new Ext.form.FieldSet({
					title :'客户指标信息',
					items:[grid_form_set_1]
			});
			
			var  form_set_2 = new Ext.form.FieldSet({
					title :'投资建议信息修改',
					frame : true,
					defaults :{
					labelWidth:200
					},
					items:[{
									fieldLabel : '净资产',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
									},{
									fieldLabel : '总资产自有权益比例',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
									},{
									fieldLabel : '家庭偿债能力',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
									},{
									fieldLabel : '储蓄投资能力',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
									},{
									fieldLabel : '开源节流能力',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
									},{
									fieldLabel : '资产收入比例',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
									}]
			});
			var tab_3 = new Ext.form.FormPanel({
				buttonAlign : "center",
				frame :true,
				items:[form_set_1,form_set_2],
				buttons :[{
					text :'生成诊断书',
					handler:function(){
						showWin.show();
					}
				},{
					text :'提交',
					handler:function(){
						Ext.Msg.alert("消息框","提交成功！");
					}
				}]
			});
		
		var bk_cm = new Ext.grid.ColumnModel([
		           {
					header : '指标',
					dataIndex : 'c1',
					width :200,
					sortable : true
				}, {
					header : '含义',
					width :300,
					dataIndex : 'c2',
//					editor :new Ext.form.Field(), 
					sortable : true
				},{
					header : '建议',
					width :200,
					dataIndex : 'c3',
//					editor :new Ext.form.Field(), 
					sortable : true
				},{
					header : '现值',
					width :200,
					dataIndex : 'c4',
					align :'right',
//					editor :new Ext.form.Field(), 
					sortable : true
				}
				]);
		var bk_store = new Ext.data.Store({
					// 数据读取器
					reader : new Ext.data.JsonReader({
								totalProperty:'num',// 记录总数
								//idIndex:'blocName', 
								root:'rows'// Json中的列表数据根节点
							}, [{
										name : 'c1' // Json中的属性Key值
									}, {
										name : 'c2'
									},{
										name : 'c3'
									},{
										name : 'c4'
									}])
				});
		var bk_data= {
				TOTALCOUNT:3,
				rows:[
				{"rownum":"1","c1":"净资产","c2":"如果为负，则说明目前的财务状况不容乐观，有必要将近期的债务尽快偿还，同时尽快增加收入。","c3":"差值应该为正","c4":"2,178,590,49"},
				{"rownum":"2","c1":"总资产自有权益比例","c2":"如>0.5较为适宜。如果净资产偿付比例太低，意味着现在的生活靠借债来维持，一旦债务到期或经济不景气时，客户的资产出现损失，可能资不抵债。如果比例很高，接近1，意味着该客户可能没有充分利用自己的信用额度，通过借款来优化其财务结构。","c3":"控制在0.5较为适宜","c4":"0.59"},
				{"rownum":"3","c1":"总资产负债比例","c2":"建议客户控制在0.5以下，以减少由于资产流动性不足而出现财务危机的可能。","c3":"比率应该小于0.5","c4":"0.41"},
				{"rownum":"4","c1":"家庭偿债能力","c2":"健康的家庭偿债能力在1.5以上。","c3":"比率应该大于1.5","c4":"2.45"},
				{"rownum":"5","c1":"储蓄投资能力","c2":"健康的储蓄投资能力要保持在0.2-0.3以上。","c3":"比率应该在0.2-0.3之间","c4":"0.71"},
				{"rownum":"6","c1":"开源节流能力","c2":"此比率逐年下降为好。","c3":"此比率逐年下降为好。","c4":"0.29"},
				{"rownum":"7","c1":"资产收入比例","c2":"如<0.5，说明有必要控制开支，需要更多地进行储蓄或投资，同时努力工作使收入增加。如0.5<净资产收入比率<3，如客户尚年轻，则其财务状况良好，如客户接近退休年龄，有必要采取措施增加其净资产。如>3,客户目前的财务状况良好。","c3":"比率应该大于1.5","c4":"6.05"}
				]
			};
		bk_store.loadData(bk_data);    
		// 表格实例
		var bk_grid = new Ext.grid.GridPanel ({
					height : 190,
					frame : true,
					border : true,
					overflow :'auto',
					autoScroll : true,
					store : bk_store, // 数据存储
//					stripeRows : true, // 斑马线
					cm : bk_cm, // 列模型
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
			var  bk_1 = new Ext.form.FieldSet({
					title :'客户指标信息',
					items:[bk_grid]
			});
			var  bk_2 = new Ext.form.FieldSet({
				   	title :'客户资产汇总',
				   		layout:'column',
				        items:[{
				        		columnWidth :0.25,
				        		layout :'form',
								items:[{
									fieldLabel : '客户总资产',
									name : 'c1',
									xtype : 'textfield', // 设置为数字输入框类型
									value :'380,570.00',
									labelStyle: 'text-align:right;',
									anchor : '90%'
									}]
								
							 },{columnWidth :0.25,
				        		layout :'form',
								items:[{
									fieldLabel : '客户总负债资产',
									name : 'c1',
									value :'0.00',
									xtype : 'textfield', // 设置为数字输入框类型
									labelStyle: 'text-align:right;',
									anchor : '90%'
								}]
							 },{columnWidth :0.5,
				        		layout :'form',
								items:[{
								fieldLabel : '净资产',
								name : 'c1',
								value:'380,570.00',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
								}]
							 }]
				    });
	var showWin = new Ext.Window({
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
		width : 1000,
		height : 460,
		buttonAlign : "center",
		title : '诊断书详情',
		items : [{
			layout :'form',
			items:[bk_1,bk_2]
		}],
		buttons:[{
			text : '关闭',
			handler:function(){
				showWin.hide();
			}
		}]});
		var tabs = new Ext.TabPanel({
					defaults :{
						overflow :'auto',
						autoScroll :true
					},		
				    activeTab: 1,
				    items: [{
				        title: '客户非本行资债信息',
				        items:[tab_1]
				    },{
				        title: '财务分析',
				          items:[tab_2]
				    },{
				        title: '财务指标分析',
				          items:[tab_3]
				    }]
				});

var infoWin = new Ext.Window({
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
		width : 1000,
		height : 460,
		buttonAlign : "center",
		title : '信息详情',
		items : [tabs]});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "客户财务健康诊断", 
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
	        if(_record.length!=1){
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