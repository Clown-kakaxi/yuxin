Ext.onReady(function() { 
	
	var qForm = new Ext.form.FormPanel({
		labelWidth :90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		region:'north',
		buttonAlign : 'center',
		height : 100,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '产品1级分类',
											name : 'c1',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
										},{
											fieldLabel : '产品编号',
											name : 'c2',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '产品2级分类', // 标签
											id : 'c3',
											name : 'c3', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										},{
											fieldLabel : '产品名称', // 标签
											id : 'c4',
											labelStyle: 'text-align:right;',
											name : 'c4', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '产品3级分类', // 标签
											name : 'c5', // name:后台根据此name属性取值
											maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
											labelStyle: 'text-align:right;',
											allowBlank : true,
											anchor : '80%'// 宽度百分比
										}]
							},{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
							        //xtype : 'datefield',
									fieldLabel : '产品4级分类', // 标签
									name : 'c6', // name:后台根据此name属性取值 
									format:'Y-m-d', //日期格式化
									labelStyle: 'text-align:right;',
									//value:new Date(),
									anchor : '80%' // 宽度百分比
								}]
							}]
				}],
		buttons : [{
					text : '查询'
				}, {
					text : '重置'
				}]
	});
	var windowForm = new Ext.form.FormPanel({
		labelWidth :90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		region:'north',
		buttonAlign : 'center',
		height : 100,
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '起始日期',
											name : 'c1',
											xtype : 'textfield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '截止日期', // 标签
											name : 'c2', // name:后台根据此name属性取值
											labelStyle: 'text-align:right;',
											allowBlank : true, // 是否允许为空
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '90%' // 宽度百分比
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '产品编号', // 标签
											name : 'c4', // name:后台根据此name属性取值
											maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
											allowBlank : true,
											labelStyle: 'text-align:right;',
											anchor : '90%'// 宽度百分比
										}]
							},{
								columnWidth : .25,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									fieldLabel : '产品名称', // 标签
									name : 'c5', // name:后台根据此name属性取值 
									labelStyle: 'text-align:right;',
									format:'Y-m-d', //日期格式化
									//value:new Date(),
									anchor : '90%' // 宽度百分比
								}]
							}]
				}],
		buttons : [{
					text : '查询'
				}, {
					text : '重置'
				}]
	});
	 var panel2 = new Ext.FormPanel({ 
		 title : '<span style="font-weight:normal">产品详情</span>',
	        frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	       height:600,

	        items: [{
	        	//columnWidth:.5,
	            	//xtype:'fieldset',
	            //checkboxToggle:true,
	          //  title: '查询条件',
	           autoHeight:true,
	           // defaults: {width:'33.3%'},
	            //defaultType: 'textfield',
	            //collapsed: true,
	            //layout:'column',
	            items :[{ layout:'column',
	                     items:[{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '序号',
	                             value :'12312',
	                              labelStyle: 'text-align:right;',
	                             name: 'first',
	                             anchor:'90%'
	                         }, {
	                             xtype:'textfield',
	                             fieldLabel: '产品名称',
	                             name: 'state',
	                              labelStyle: 'text-align:right;',
	                             value :'单位普通活期存款',
	                             anchor:'90%'
	                         },{
	                         	xtype:'textfield',
	                         	fieldLabel:'渠道',
	                         	value:'',
	                         	 labelStyle: 'text-align:right;',
	                         	name:'date',
	                         	anchor:'90%'
	                         
	                         },{
	                         	xtype:'textfield',
	                         	fieldLabel:'发生金额',
	                         	value:'10.00万元',
	                         	style: 'text-align:right;',
	                         	 labelStyle: 'text-align:right;',
	                         	name:'startdate',
	                         	anchor:'90%'
	                         
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                              fieldLabel: '统计日期',
	                               labelStyle: 'text-align:right;',
	                             name: 'orgid',
	                             value :'2011-02-12',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '机构号',
	                             name: 'baseid',
	                             value :'12312',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '时点余额',
	                             name: 'lendCount',
	                             style: 'text-align:right;',
	                             value :'1.00万元',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }]
	                     },{
	                         columnWidth:.34,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '产品编号',
	                             value :'0001',
	                             
	                              labelStyle: 'text-align:right;',
	                             name: 'lendid',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '机构名称',
	                             name: 'basebank',
	                             value :'市场产品部',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '日均余额',
	                             name: 'myCount',
	                             style: 'text-align:right;',
	                             value :'1.00万元',
	                              labelStyle: 'text-align:right;',
	                             anchor:'90%'
	                         }]
	                     }
	            ]}
	            ]}]
	    });
	 //复选框
	var fsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var frownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var fcm = new Ext.grid.ColumnModel([frownum,fsm, 
	                    	           {
	                    				header : '序号', // 列标题
	                    				dataIndex : 'a1', // 数据索引:和Store模型对应
	                    				sortable : true,
	                    				width : 100
	                    				// 是否可排序
	                    		    }, {
	                    				header : '协议日期',
	                    				dataIndex : 'a2',
	                    				sortable : true,
	                    				width : 100
	                    			}, {
	                    				header : '产品编号',
	                    				dataIndex : 'a3',
	                    				width : 100
	                    			},{
	                    				header : '产品名称',
	                    				dataIndex : 'a4',
	                    				width : 100
	                    			},{
	                    				header : '机构号',
	                    				dataIndex : 'a5'
	                    			},{
	                    				header : '机构名称',
	                    				dataIndex : 'a6',
	                    				width : 100
	                    			},{
	                    				header : '贷方发生额',
	                    				dataIndex : 'a7',
	                    					width : 100
	                    			},{
	                    				header : '余额',
	                    				dataIndex : 'a8',
	                    				width : 100
	                    			},{
	                    				header : '交易金额',
	                    				align:'right',
	                    				dataIndex : 'a9'
	                    			},{
	                    				header : '交易摘要',
	                    				align:'right',
	                    				dataIndex : 'a10',
	                    				width : 100
	                    			},{
	                    				header : '交易对手账号',
	                    				dataIndex : 'a13',
	                    				width : 100
	                    			},{
	                    				header : '交易对手名称',
	                    				dataIndex : 'a14',
	                    				width : 100
	                    			}
	                    			]);


	/**
	 * 数据存储
	 */
	var fstore = new Ext.data.Store({
		// 数据读取器
		reader : new Ext.data.JsonReader({
					totalProperty:'num',// 记录总数
					//idIndex:'blocName', 
					root:'rows'// Json中的列表数据根节点
				}, [{
							name : 'a1' // Json中的属性Key值
						}, {
							name : 'a2'
						}, {
							name : 'a3'
						}, {
							name : 'a4'
						}, {
							name : 'a5'
						}, {
							name : 'a6'
						}, {
							name : 'a7'
						}, {
							name : 'a8'
						}, {
							name : 'a9'
						}, {
							name : 'a10'
						}, {
							name : 'a11'
						}, {
							name : 'a12'
						}, {
							name : 'a13'
						}, {
							name : 'a14'
						}
						])
	});
	
	var fData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","a1":"101" ,"a2":"2011-03-01","a3":"000273","a4":"单位协定存款","a5":"","a6":"","a7":"123123","a8":"","a9":"10.00万元","a10":"5.00万元","a11":"4.00万元","a12":"","a13":"123123","a14":""},
			{"rownum":"2","a1":"102" ,"a2":"2011-04-01","a3":"100382","a4":"国库定期存款","a5":"","a6":"" ,"a7":"234142","a8":"","a9":"8.00万元","a10":"6.00万元","a11":"5.00万元","a12":"","a13":"23234","a14":""},
			{"rownum":"3","a1":"103" ,"a2":"2011-05-01","a3":"200123" ,"a4":"汇票垫款贷款","a5":"","a6":"" ,"a7":"234142","a8":"","a9":"11.00万元","a10":"5.00万元","a11":"4.00万元","a12":"","a13":"34532","a14":""},			
			{"rownum":"4","a1":"104" ,"a2":"2011-06-01","a3":"200483","a4":"信用证垫款贷款","a5":"","a6":"","a7":"2343241","a8":"","a9":"6.00万元","a10":"4.00万元","a11":"4.00万元","a12":"","a13":"356343","a14":""}				
			]
		};
	
	fstore.loadData(fData);
	
	
	// 表格实例
	var fgrid = new Ext.grid.GridPanel({
		        title : '<span style="font-weight:normal">产品明细</span>',
				height : 300,
				frame : true,
				autoScroll : true,
				//region : 'center', // 返回给页面的div
				store : fstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : fcm, // 列模型
				sm : fsm, // 复选框
				//tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
				
			});
	var fWindow = new Ext.Window(
			{
				layout : 'column',
				width : 700,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal"></span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [windowForm,fgrid],
				buttons : [
						 {
							text : '关闭',
							
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	var kWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 800,
				height : 300,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal"></span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [panel2],
				buttons : [
						 {
							text : '关闭',
							
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	 //复选框
	var lsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var lrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var lcm = new Ext.grid.ColumnModel([lrownum,lsm, 
	                    	           {
	                    				header : '序号', // 列标题
	                    				dataIndex : 'a1', // 数据索引:和Store模型对应
	                    				sortable : true,
	                    				width : 100
	                    				// 是否可排序
	                    		    },{
	                    				header : '产品编号',
	                    				dataIndex : 'a3',
	                    				width : 100
	                    			},{
	                    				header : '产品名称',
	                    				dataIndex : 'a4',
	                    				width : 100
	                    			},{
	                    				header : '利率',
	                    				dataIndex : 'a5'
	                    			},{
	                    				header : '预计收益率',
	                    				dataIndex : 'a6',
	                    				width : 100
	                    			},{
	                    				header : '估计费率',
	                    				dataIndex : 'a7',
	                    					width : 100
	                    			},{
	                    				header : '渠道建议',
	                    				dataIndex : 'a8',
	                    					width : 100
	                    			},{
	                    				header : '客户经理',
	                    				dataIndex : 'a9',
	                    				width : 100
	                    			},{
	                    				header : '联系方式',
	                    				//align:'right',
	                    				dataIndex : 'a10'
	                    			}
	                    			]);


	/**
	 * 数据存储
	 */
	var lstore = new Ext.data.Store({
		// 数据读取器
		reader : new Ext.data.JsonReader({
					totalProperty:'num',// 记录总数
					//idIndex:'blocName', 
					root:'rows'// Json中的列表数据根节点
				}, [{
							name : 'a1' // Json中的属性Key值
						}, {
							name : 'a2'
						}, {
							name : 'a3'
						}, {
							name : 'a4'
						}, {
							name : 'a5'
						}, {
							name : 'a6'
						}, {
							name : 'a7'
						}, {
							name : 'a8'
						}, {
							name : 'a9'
						}, {
							name : 'a10'
						}
						])
	});
	
	var lData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","a1":"101" ,"a2":"000273","a3":"单位协定存款","a4":"4.00%","a5":"4.00%","a6":"","a7":"123123","a8":"焦向波","a9":"13902834543"},
			{"rownum":"2","a1":"102" ,"a2":"100382","a3":"国库定期存款","a4":"4.50%","a5":"7.00%","a6":"" ,"a7":"234142","a8":"陈群","a9":"158283945342"},
			{"rownum":"3","a1":"103" ,"a2":"200123","a3":"汇票垫款贷款" ,"a4":"5.00%","a5":"10.00%","a6":"" ,"a7":"234142","a8":"姚亮","a9":"010-2983242"},			
			{"rownum":"4","a1":"104" ,"a2":"200483","a3":"信用证垫款贷款","a4":"3.00%","a5":"4.00%","a6":"","a7":"2343241","a8":"余勇智","a9":"0102738495"}				
			]
		};
	
	lstore.loadData(lData);
	
	
	// 表格实例
	var lgrid = new Ext.grid.GridPanel({
		        title : '<span style="font-weight:normal">产品推荐</span>',
				height : 300,
				frame : true,
				autoScroll : true,
				//region : 'center', // 返回给页面的div
				store : lstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : lcm, // 列模型
				sm : lsm, // 复选框
				//tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
				
			});
	var lWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 800,
				height : 300,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal"></span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [lgrid],
				buttons : [
						 {
							text : '关闭',
							
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	
	
	var psm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var prownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	var tbar = new Ext.Toolbar({
		items : [{
			text : '产品详情',
			handler : function() {
				kWindow.show();
				//Ext.MessageBox.alert('提示', "取消关注成功!");
			}},{
			text : '产品明细',
			handler : function() {
				fWindow.show();
				//Ext.MessageBox.alert('提示', "取消关注成功!");
			}},{
			text : '产品推荐',
			handler : function() {
				lWindow.show();
				//Ext.MessageBox.alert('提示', "取消关注成功!");
			}
		}
		]
	});


	// 定义列模型
	var pcm = new Ext.grid.ColumnModel([prownum,psm, 
	           {
				header : '产品1级分类', // 列标题
				dataIndex : 'd1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '产品2级分类',
				dataIndex : 'd2',
				sortable : true,
				width : 150
			}, {
				header : '产品3级分类',
				dataIndex : 'd3'
			}, {
				header : '产品4级分类',
				dataIndex : 'd4'
			}, {
				header : '产品编号',
				dataIndex : 'd5',
				sortable : true,
				width : 150
			}, {
				header : '产品名称',
				dataIndex : 'd6'
			}, {
				header : '期限',
				dataIndex : 'd7'
			}, {
				header : '利率',
				dataIndex : 'd8',
				sortable : true,
				width : 150
			}, {
				header : '时点余额',
				align:'right',
				dataIndex : 'd9'
			}, {
				header : '年均余额',
				align:'right',
				dataIndex : 'd10'
			}, {
				header : '发生金额',
				align:'right',
				dataIndex : 'd11',
				sortable : true,
				width : 150
			}, {
				header : '发生数量',
				dataIndex : 'd12'
			}, {
				header : '购买日期',
				dataIndex : 'd13'
			}, {
				header : '收入合计',
				align:'right',
				dataIndex : 'd14',
				sortable : true,
				width : 150
			}, {
				header : '支出合计',
				align:'right',
				dataIndex : 'd15'
			}, {
				header : '产品贡献度',
				align:'right',
				dataIndex : 'd16'
			}
			]);

	/**
	 * 数据存储
	 */
	var pstore = new Ext.data.Store({
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
									name : 'd1' // Json中的属性Key值
								}, {
									name : 'd2'
								}, {
									name : 'd3'
								}, {
									name : 'd4'
								}, {
									name : 'd5'
								}, {
									name : 'd6'
								}, {
									name : 'd7'
								}, {
									name : 'd8'
								}, {
									name : 'd9'
								}, {
									name : 'd10'
								}, {
									name : 'd11'
								}, {
									name : 'd12'
								}, {
									name : 'd13'
								}, {
									name : 'd14'
								}, {
									name : 'd15'
								}, {
									name : 'd16'
								}
								])
			});
	
	var pData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","d1":"负债业务","d2":"一般存款","d3":"单位普通活期存款","d4":"","d5":"101","d6":"单位普通活期存款","d7":"1年","d8":"4.00%","d9":"10.00万元","d10":"4.00万元","d11":"2.00万元","d12":"2","d13":"2011-03-12","d14":"3.00万元","d15":"1.00万元","d16":"10.00万元"},
			{"rownum":"2","d1":"资产业务","d2":"流动资金贷款","d3":"流动资金贷款","d4":"","d5":"227","d6":"流动资金贷款","d7":"2年","d8":"3.50%"    ,"d9":"4.00万元","d10":"3.00万元","d11":"2.00万元","d12":"3","d13":"2011-04-13","d14":"4.00万元","d15":"3.00万元","d16":"4.00万元"},
			{"rownum":"3","d1":"中间业务","d2":"支付结算业务","d3":"支票"        ,"d4":"","d5":"323","d6":"支票","d7":"10天","d8":"3.50%"          ,"d9":"2.00万元","d10":"1.00万元","d11":"1.00万元","d12":"1","d13":"2011-05-23","d14":"3.00万元","d15":"2.00万元","d16":"2.00万元"},			
			{"rownum":"4","d1":"负债业务","d2":"一般存款","d3":"单位通知存款"    ,"d4":"","d5":"123","d6":"单位通知存款","d7":"1天","d8":"2.10%"    ,"d9":"12.00万元","d10":"9.00万元","d11":"1.00万元","d12":"3","d13":"2010-02-12","d14":"5.00万元","d15":"3.00万元","d16":"12.00万元"}				
			]
		};
	pstore.loadData(pData);
	// 表格实例
	var pgrid = new Ext.grid.GridPanel({
				
				height : 300,
				frame : true,
				autoScroll : true,
				region:'center',
				store : pstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : pcm, // 列模型
				sm :psm, // 复选框
				tbar:tbar,
		
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	
	/* //复选框
	var windowsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var windowrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var windowcm = new Ext.grid.ColumnModel([windowrownum,windowsm, 
	           {
				header : '客户中文名称', // 列标题
				dataIndex : 'customername', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '组织机构代码',
				dataIndex : 'organizationcode',
				sortable : true,
				width : 150
			}, {
				header : '客户类型',
				dataIndex : 'customertype'
			}, {
				header : '客户级别',
				dataIndex : 'customerlevel'
			}
			]);

	*//**
	 * 数据存储
	 *//*
	var windowstore = new Ext.data.Store({
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
	
	var windowmemberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"路人甲","organizationcode":"101","customertype":"老客户","customerlevel":"1"},
			{"rownum":"2","customername":"路人乙","organizationcode":"102","customertype":"大客户","customerlevel":"2"},
			{"rownum":"3","customername":"路人丙","organizationcode":"103","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"路人丁","organizationcode":"104","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	debugger;
	//windowstore.loadData(windowmemberData);
	// 表格实例
	var windowgrid = new Ext.grid.GridPanel({
				
				height : 500,
				frame : true,
				autoScroll : true,
	
				store : windowstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : windowcm, // 列模型
				sm : windowsm, // 复选框
		
		
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var addRoleWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 400,
				height : 300,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal"></span>',
				// iconCls : 'page_addIcon',产品中账户信息
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				pageY : 20,
				pageX : document.body.clientWidth / 2 - 420 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [windowgrid],
				buttons : [
						{
							text : '保存',
							iconCls : 'acceptIcon',
							handler : function() {
							
								addRoleWindow.hide();
								Ext.MessageBox.alert('提示', "保存成功!");
							}
						}, {
							text : '重置',
							id : 'btnReset',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
								//clearForm(addRoleFormPanel.getForm());
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	
	
	*/
	//复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	           {
				header : '合同号', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '业务类型',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			}, {
				header : '业务品种',
				dataIndex : 'c3'
			}, {
				header : '代表行',
				dataIndex : 'c4'
			}, {
				header : '额度',
				dataIndex : 'c5'
			}, {
				header : '币种',
				dataIndex : 'c6'
			}, {
				header : '产品类型',
				dataIndex : 'c7'
			}, {
				header : '业务关系',
				dataIndex : 'c8'
			}, {
				header : '账户代码',
				dataIndex : 'c9'
			}, {
				header : '总额',
				dataIndex : 'c10'
			}, {
				header : '费用描述',
				dataIndex : 'c11'
			}, {
				header : '产品编号',
				dataIndex : 'c12'
			}, {
				header : '产品全称',
				dataIndex : 'c13'
			}, {
				header : '业务状态',
				dataIndex : 'c14'
			}
			]);

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
									name : 'c1' // Json中的属性Key值
								}, {
									name : 'c2'
								}, {
									name : 'c3'
								}, {
									name : 'c4'
								}, {
									name : 'c5'
								}, {
									name : 'c6'
								}, {
									name : 'c7'
								}, {
									name : 'c8'
								}, {
									name : 'c9'
								}, {
									name : 'c10'
								}, {
									name : 'c11'
								}, {
									name : 'c12'
								}, {
									name : 'c13'
								}, {
									name : 'c14'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"路人甲","organizationcode":"101","customertype":"老客户","customerlevel":"1"},
			{"rownum":"2","customername":"路人乙","organizationcode":"102","customertype":"大客户","customerlevel":"2"},
			{"rownum":"3","customername":"路人丙","organizationcode":"103","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"路人丁","organizationcode":"104","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	//store.loadData(memberData);


		// 表格实例
	var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">定期产品</span>',
				height :100,
				frame : true,
				autoScroll : true,
				//region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				//tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
	// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
				// forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	
	var sm1 = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum1 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm1 = new Ext.grid.ColumnModel([rownum1,sm1, 
	           {
				header : '合同号', // 列标题
				dataIndex : 'c1', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
				// 是否可排序
		    }, {
				header : '业务类型',
				dataIndex : 'c2',
				sortable : true,
				width : 150
			}, {
				header : '业务品种',
				dataIndex : 'c3'
			}, {
				header : '授信项下使用方式',
				dataIndex : 'c4'
			}, {
				header : '授信种类',
				dataIndex : 'c5'
			}, {
				header : '币种',
				dataIndex : 'c6'
			}, {
				header : '金额',
				dataIndex : 'c7'
			}, {
				header : '利率',
				dataIndex : 'c8'
			}, {
				header : '罚息利率',
				dataIndex : 'c9'
			}, {
				header : '表内余额',
				dataIndex : 'c10'
			}, {
				header : '表外余额',
				dataIndex : 'c11'
			}, {
				header : '表内欠息余额',
				dataIndex : 'c12'
			}, {
				header : '表外欠息余额',
				dataIndex : 'c13'
			}, {
				header : '表内实收利息',
				dataIndex : 'c14'
			}, {
				header : '表外实收利息',
				dataIndex : 'c14'
			}
			]);

	/**
	 * 数据存储
	 */
	var store1 = new Ext.data.Store({
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
									name : 'c1' // Json中的属性Key值
								}, {
									name : 'c2'
								}, {
									name : 'c3'
								}, {
									name : 'c4'
								}, {
									name : 'c5'
								}, {
									name : 'c6'
								}, {
									name : 'c7'
								}, {
									name : 'c8'
								}, {
									name : 'c9'
								}, {
									name : 'c10'
								}, {
									name : 'c11'
								}, {
									name : 'c12'
								}, {
									name : 'c13'
								}, {
									name : 'c14'
								}
								])
			});
	
	var memberData1= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customername":"路人甲","organizationcode":"101","customertype":"老客户","customerlevel":"1"},
			{"rownum":"2","customername":"路人乙","organizationcode":"102","customertype":"大客户","customerlevel":"2"},
			{"rownum":"3","customername":"路人丙","organizationcode":"103","customertype":"潜在客户","customerlevel":"3"},			
			{"rownum":"4","customername":"路人丁","organizationcode":"104","customertype":"普特客户","customerlevel":"4"}				
			]
		};
	//store1.loadData(memberData1);

		// 表格实例
	var grid1 = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">短期贷款</span>',
				height : 500,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store1, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm1, // 列模型
				sm : sm1, // 复选框
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	var mWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 700,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal"></span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [grid],
				buttons : [
						 {
							text : '关闭',
							
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	var nWindow = new Ext.Window(
			{
				layout : 'fit',
				width : 700,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal"></span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [grid1],
				buttons : [
						 {
							text : '关闭',
							
							handler : function() {
								addRoleWindow.hide();
							}
						} ]
			});
	
	
	
    //定义树的跟节点
   var root=new Ext.tree.TreeNode({
          id:"root",//根节点id
   draggable:false,
          text:"银行产品"
    });
   
    //定义树节点
    var c1=new Ext.tree.TreeNode({
      id:'c1',//子结点id
      text:'存款产品'
      
    });
    var c2=new Ext.tree.TreeNode({
      id:'c2',//子结点id
      text:'贷款产品'
      
    });
   
     var c11=new Ext.tree.TreeNode({
        id:'c11',
        text:'定期产品'
      });
       var c12=new Ext.tree.TreeNode({
        id:'c12',
        text:'活期产品'
      });
       var c21=new Ext.tree.TreeNode({
        id:'c21',
        text:'短期贷款'
      }); 
	  var c22=new Ext.tree.TreeNode({
        id:'c22',
        text:'长期贷款'
      });
      
      
      
 
   
    root.appendChild(c1);//为根节点增加子结点c1
    root.appendChild(c2);//为c1增加子节点c2。
    
    c1.appendChild(c11);
    c1.appendChild(c12);
    c2.appendChild(c21);
    c2.appendChild(c22);
    
    //生成树形面板

       var tree=new Ext.tree.TreePanel({
         root:root,//定位到根节点
         animate:true,//开启动画效果
         enableDD:true,//允许子节点拖动
         border:false,//没有边框
    containerScroll: true,
         rootVisible:true//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性

      });
/*       c11.on('click', function(){
    	   mWindow.show();
       });
   c21.on('click', function(){
	   nWindow.show();
	    Ext.getCmp("center-panel").doLayout(); 
   Ext.getCmp("center-panel").getComponent(0).destroy(); 
   Ext.getCmp("center-panel").add(grid1); 
   Ext.getCmp("center-panel").doLayout(); 
   });*/
  grid.on('click', function(){ 
	  addRoleWindow.show();
  });
var view = new Ext.Viewport({
	
layout: 'border',
items: [
       /* {   
	region: 'north',
    id: 'north-panel',
    //title: "客户管理->关联客户管理", 
    height: 95,
    hidden:false,
    margins: '0 0 0 0',
    //layout: 'fit',
	items:[qForm]
 },*/
{   region: 'west',
    id: 'west-panel',
	collapsible: true, 
	title: "产品信息树", 
	split: true,
	width: 200,
	minSize: 175,
	maxSize: 400,
	collapsible: true,
	margins: '0 0 0 5',
	layout: 'fit',
	items:[tree]
    },

    
{   region:'center',
    id: 'center-panel',
	//collapsible: true,
     //html:'<img src="客户基本信息.JPG"/>', 
    title: "  ",
	layout: 'border',
	items:[qForm,pgrid]
   
    }] 
      });debugger;
      
     	root.expand(true);
      
}); 