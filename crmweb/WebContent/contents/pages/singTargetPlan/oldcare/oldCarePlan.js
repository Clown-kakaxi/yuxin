	
	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});
	

	//客户信息form		
	var infoForm = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		height:250,
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称',
				labelWidth : 100,
				name : 'custName',
				custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
				custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				anchor : '90%',
				hiddenName : 'custId',
				value:'李晓丽'
			}),{
				fieldLabel : '通胀率',
				name : 'q2',
				xtype : 'textfield', 
				value:'35%',
				anchor : '90%'
			},{
				fieldLabel : '回报率',
				name : 'q2',
				xtype : 'textfield', 
				value:'25%',
				anchor : '90%'
			},{
				fieldLabel : '期望退休年龄',
				name : 'q2',
				xtype : 'textfield', 
				value:'55',
				anchor : '90%'
			},{
				fieldLabel : '当前资产',
				name : 'q2',
				xtype : 'textfield', 
				value:'1,000,000.00',
				anchor : '90%'
			},{
				fieldLabel : '当前年收入',
				name : 'q2',
				xtype : 'textfield', 
				value:'100,000.00',
				anchor : '90%'
			},{
				fieldLabel : '当前净资产',
				name : 'q2',
				xtype : 'textfield', 
				value:'980,000.00',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '规划名称',
				name : 'q3',
				value:'李晓丽_养老规划',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '我国居民价格指数',
				name : 'q4',
				xtype : 'textfield', 
				value:'105',
				anchor : '90%'
			},{
				store : typeStore ,
				xtype : 'combo',
				resizable : true,
				name : 'DBTABLE_ID',
				hiddenName : 'DBTABLE_ID',
				fieldLabel : '风险偏好',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				value:'2',
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%'
			
		},{
			fieldLabel : '养老年数',
			name : 'q4',
			xtype : 'textfield', 
			value:'20',
			anchor : '90%'
		},{
			fieldLabel : '当前负债',
			name : 'q2',
			xtype : 'textfield', 
			value:'20,000.00',
			anchor : '90%'
		},{
			fieldLabel : '当前年支出',
			name : 'q2',
			xtype : 'textfield', 
			value:'20,000.00',
			anchor : '90%'
		},{
			fieldLabel : '当前月净收入',
			name : 'q2',
			xtype : 'textfield', 
			value:'10,000.00',
			anchor : '90%'
		}]
		}]
	});
	
	//养老金需求
	var needForm = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		height:150,
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '退休后支出占当前的百分比',
				name : 'q1',
				xtype : 'textfield', 
				value:'75%',
				anchor : '90%'
			},{
				fieldLabel : '投资回报为当前的',
				name : 'q2',
				xtype : 'textfield', 
				value:'80%',
				anchor : '90%'
			},{
				fieldLabel : '退休时每年获得社保',
				name : 'q2',
				xtype : 'textfield', 
				value:'250,000.00',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '退休后年支出',
				name : 'q3',
				value:'15,000.00',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '投资回报',
				name : 'q4',
				xtype : 'textfield', 
				value:'16,000.00',
				anchor : '90%'
			},{
			fieldLabel : '退休时每年可获的养老保险金',
			name : 'q2',
			xtype : 'textfield', 
			value:'10,000.00',
			anchor : '90%'
		}]
		}]
	});
		//表格部分
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
	}, {
		name : 'a5'
	}, {
		name : 'a6'
	}, {
		name : 'a7'
	}];



	var columns = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '日期',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '2011',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a2',
		header : '2012',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a3',
		header : '2013',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a4',
		header : '2014',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a5',
		header : '2015',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a6',
		header : '2016',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a7',
		header : '......',
		sortable : true,
		width : 140
	}]);

	var data = [
	            ['期末资产余额','10,900.00','11,881.00','12,950.29','14,115.82','15,386.24','16,771.00','......'],
	            ['投入资金','10,000.00','0','0','0','0','0','......'],
	            ['教育支出','10,000.00','0','0','0','0','0','......'],
	            ['生活支出','0','0','0','0','0','0','......'],
	            ['投资收益','900','981','1,069.29','1,165.53','1,270.42','1,384.76','1,384.76']
	];



	var store = new Ext.data.ArrayStore({
		fields : fields,
		data : data
	});

		var infoGrid = new Ext.grid.GridPanel({
			region : 'center',
			frame : true,
			store : store,
			stripeRows : true,
			cm : columns
			
	});
		
//		//产品库选择
//		var win = new Ext.Window({
//			title : '添加产品',
//			height:'200',
//			width:'500',
//			modal : true,//遮罩
//			buttonAlign:'center',
//			layout:'fit',
//			items:[{	
//				xtype : 'panel',
//				layout : 'column',
//				frame : true,
//					items : [{
//						columnWidth : .5,
//						layout : 'form',
//						items : [ new Com.yucheng.crm.common.ProductManage({
//							xtype : 'productChoose',
//							fieldLabel : '产品选择',
//							labelStyle : 'text-align:right;',
//							name : 'prodName',
//							hiddenName : 'prodId',
//							singleSelect : false,
//							allowBlank : false,
//							blankText : '此项为必填项，请检查！',
//							anchor : '90%'
//						})]
//					}
//					]}],
//			buttons:[
//			         {
//			        	 text:'添加',
//			        	 handler: function(){
//			    					Ext.MessageBox.alert('系统提示信息', '操作成功');
//			    					win.hide();
//						 }
//						
//			         }, {
//			        	 text:'关闭',
//			        	 handler:function(){
//			        		 win.hide();
//			         	}
//			         }]
//		});
		
		//精品选择
		var ruleSetForm1 = new Ext.form.FormPanel({
		    frame : true,
		    buttonAlign : "center",
		    region : 'north',
		    autoScroll : true,
		    height:100,
		    labelWidth : 100,
		    items:[{
		    	layout : 'column',
		        items:[
		                {columnWidth : .5,
			            layout : 'form',
			            items :[{
			            	xtype : 'textfield',
							fieldLabel : '产品编号',
							name : 'scoreName',
							labelStyle: 'text-align:right;',
							allowBlank:false,
							anchor : '100%'
			            }]
		        	
		                },
		                {columnWidth : .5,
		    	            layout : 'form',
		    	            items :[{
				            	xtype : 'textfield',
								fieldLabel : '产品名称',
								name : 'scoreName',
								labelStyle: 'text-align:right;',
								allowBlank:false,
								anchor : '100%'
				            }]
		            	
		                    }
		                ]
		    }],
			buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {

					}
				}, {
					text : '重置',
					handler : function() {
						ruleSetForm1.form.reset();
					}
				}]
		});

		var fields2 = [ {
			name : 'a0'
		}, {
			name : 'a1'
		}, {
			name : 'a2'
		}, {
			name : 'a3'
		}];

		//定义自动当前页行号
		var num2 = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

		var sm2 = new Ext.grid.CheckboxSelectionModel();

		var columns2 = new Ext.grid.ColumnModel([num2, sm2,{
			dataIndex : 'a0',
			header : '产品编号',
			sortable : true,
			width : 80
		}, {
			dataIndex : 'a1',
			header : '产品名称',
			sortable : true,
			width : 140
		}, {
			dataIndex : 'a2',
			header : '起购金额',
			sortable : true,
			align : 'right',
			width : 140
		}, {
			dataIndex : 'a3',
			header : '推荐原因',
			sortable : true,
			width : 140
		}]);



		var data2 = [
		            ['101','得利宝天添利Ａ款','1，000，000','收益可观'],
		            ['102','得利宝天添利B款','1，000，000','收益可观'],
		            ['103','得利宝天添利C款','100，000','收益可观'],
		            ['104','天添利A款至尊版','2，000，000','收益可观'],
		            ['105','天添利D款至尊版','100，000','收益可观']
		];

		//每页显示条数下拉选择框
		var combo2 = new Ext.form.ComboBox({
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

		var store2 = new Ext.data.ArrayStore({
			fields : fields2,
			data : data2
		});

		var number2 = parseInt(combo2.getValue());

		//分页工具栏
		var bbar2 = new Ext.PagingToolbar({
			pageSize : number2,
			store : store2,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', combo2]
		});


		var ruleGrid1 = new Ext.grid.GridPanel({
				region : 'center',
				frame : true,
				store : store2,
				stripeRows : true,
				cm : columns2,
				sm : sm2,
				buttonAlign : "center",
				bbar : bbar2
		});

		var ruleSetPanel1 = new Ext.Panel({
		    layout : 'border',
		    autoScroll : true,
		    buttonAlign : "center",
		    items : [ ruleSetForm1,ruleGrid1],
		    buttons : [{ 
			     text : '添加', 
			     handler  : function() {
			    	 var records = ruleGrid1.selModel.getSelections();// 得到被选择的行的数组
		            	var selectLength = records.length;
		                 if (selectLength <= 0 ) {
		                 	alert("请选择产品");
		                     return false;
		                 }
		                 var tempName = '';
		                 for(var i = 0; i<selectLength;i++)
							{
								selectRe = ruleGrid1.getSelectionModel().getSelections()[i];
								tempName = selectRe.data.a1;
								 var u = new store1.recordType({
								    	"a0" :tempName,             
										"a1" :"",
										"a2" :""
								    });
								 productGrid.stopEditing();
								 store1.insert(0, u);
								 productGrid.startEditing(0, 0);
							}
			    	 win1.hide();
					}
			    }, {
					text : '关闭',
					handler  : function() {
						win1.hide();
					}
				} ]
		});
		
		var win1 = new Ext.Window({
			title:'精选产品',
			layout : 'fit',
		    autoScroll : true,
		    draggable : true,
		    closable : true,
		    closeAction : 'hide',
		    modal : true,
		    width : 600,
		    height : 400,
		    loadMask : true,
		    border : false,
		    items : [ {
		        buttonAlign : "center",
		        layout : 'fit',
		        items : [ruleSetPanel1]
		    }]
		});
		//产品介绍
		var dateails = new Ext.form.FormPanel({
		    frame : true,
		    buttonAlign : "center",
		    region : 'center',
		    autoScroll : true,
		    labelWidth : 100,
		    items:[{
		    	layout : 'column',
		        items:[
		                {columnWidth : .5,
			            layout : 'form',
			            items :[{
			            	xtype : 'textfield',
							fieldLabel : '产品编号',
							name : 'scoreName',
							value:'CP001',
							disabled:true,
							labelStyle: 'text-align:right;',
							allowBlank:false,
							anchor : '90%'
			            },{
			            	xtype : 'textfield',
							fieldLabel : '风险等级',
							name : 'scoreName',
							value:'中',
							disabled:true,
							labelStyle: 'text-align:right;',
							allowBlank:false,
							anchor : '90%'
			            }]
		        	
		                },
		                {columnWidth : .5,
		    	            layout : 'form',
		    	            items :[{
				            	xtype : 'textfield',
								fieldLabel : '产品名称',
								name : 'scoreName',
								disabled:true,
								value:'得利宝天天利A款',
								labelStyle: 'text-align:right;',
								allowBlank:false,
								anchor : '90%'
				            },{
				            	xtype : 'textfield',
								fieldLabel : '逾期收益',
								name : 'scoreName',
								disabled:true,
								value:'4%',
								labelStyle: 'text-align:right;',
								allowBlank:false,
								anchor : '90%'
				            }]
		            	
		                    },
		                    {columnWidth : 1,
			    	            layout : 'form',
			    	            items :[{
					            	xtype : 'textarea',
									fieldLabel : '产品介绍',
									name : 'scoreName',
									disabled:true,
									value:'得利宝天天利A......',
									labelStyle: 'text-align:right;',
									allowBlank:false,
									anchor : '95%'
					            }]
			            	
			                    }
		                ]
		    }]
		});
		var dateail = new Ext.Panel({
		    layout : 'border',
		    autoScroll : true,
		    buttonAlign : "center",
		    items : [ dateails],
		    buttons : [ {
					text : '关闭',
					handler  : function() {
						win2.hide();
					}
				} ]
		});
		var win2 = new Ext.Window({
			title:'产品介绍',
			layout : 'fit',
		    autoScroll : true,
		    draggable : true,
		    closable : true,
		    closeAction : 'hide',
		    modal : true,
		    width : 600,
		    height : 240,
		    loadMask : true,
		    border : false,
		    items : [ {
		        buttonAlign : "center",
		        layout : 'fit',
		        items : [dateail]
		    }]
		});
		//产品推荐
		var fields1 = [ {
			name : 'a0'
		}, {
			name : 'a1'
		}, {
			name : 'a2'
		}];

		var sm = new Ext.grid.CheckboxSelectionModel();
		var columns1 = new Ext.grid.ColumnModel([sm,{
			dataIndex : 'a0',
			header : '产品名称',
			sortable : true,
			width : 140
		}, {
			dataIndex : 'a1',
			header : '购买金额',
			sortable : true,
			width : 140,
			align : 'right',
			editor : new Ext.form.Field()
		}, {
			dataIndex : 'a2',
			header : '推荐理由',
			sortable : true,
			width : 300,
			editor : new Ext.form.Field()
		}]);
		
		
		var data1 = [
		            ['得利宝天添利Ａ款','100,000','投资小，收效快'],
		            ['得利宝天添利B款','100,000','效益高']
		];



		var store1 = new Ext.data.ArrayStore({
			fields : fields1,
			data : data1
		});

		
		var productGrid = new Ext.grid.EditorGridPanel({
			region : 'center',
			height:300,
			frame : true,
			store : store1,
			stripeRows : true,
			tbar:[{
	            text : '从产品库选择',
	            iconCls:'addIconCss',
	            handler:function() {
	            	productManageInfoStore.load({
	        			params:{
	        				start:0,
	        				limit: 100
	        			}
	        		});

	            	productManageWindow.show();
	        }},{
	            text : '从精选库选择',
	            iconCls:'addIconCss',
	            handler:function() {
	            	win1.show();
	        }},{
	            text : '产品介绍',
	            iconCls:'detailIconCss',
	            handler:function() {
	            	var records = productGrid.selModel.getSelections();// 得到被选择的行的数组
	            	var selectLength = records.length;
	                 if (selectLength != 1) {
	                 	alert("请选择一条记录");
	                     return false;
	                 }
	            	win2.show();
	        }},{
	            text : '删除',
	            iconCls:'deleteIconCss',
	            handler:function() {
	            	var records = productGrid.selModel.getSelections();// 得到被选择的行的数组
	            	var selectLength = records.length;
	            	
	                 if (selectLength < 1) {
	                 	alert("请选择记录");
	                     return false;
	                 }
	                 var selectRe ;
	                 for(var i = selectLength-1; i>=0;i--)
						{
							selectRe = productGrid.getSelectionModel().getSelections()[i];
							store1.remove(selectRe);
						}
	                 alert("删除成功");
	        }}],
	        sm:sm,
			cm : columns1
	});
	 //主体card布局
	var cardPanel=new Ext.Panel({
		layout:"card",
		activeItem: 0,
		autoScroll:true,
		layoutConfig: {
		animate: true 
		},
		items:[
//		       {title:"规划愿景",layout:'border',items:[{
//			xtype:'portal',
//            id:'center',
//            region:'center',
//            items:[{
//                columnWidth: .3,
//                autoHeight:true,
//                height:250,
//                border:false,
//                items:[{
//                    collapsible:true,
//                    layout:'fit',
//                    style:'padding:0px 0px 0px 0px',
//                    html:'<iframe id="contentFrame" name="content" height="240" frameborder="no" width="100%" src=\"top.jpg\" " scrolling="no"> </iframe>'
//                }]
//            },{
//	                columnWidth: .7,
//	                border:false,
//	                height:150,
//	                items:[{
//	                    collapsible:true,
//	                    layout:'fit',
//	                    style:'padding:0px 0px 0px 0px',
//	            			html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;年轻时工作繁忙，退休后有了大把大把充裕的时间，怎样完成由职业者到休闲者的角色过度，怎样打点自己数不胜数退休时间，可能是摆在许多退休老人面前的棘手问题。与其冥思苦想，不如打开世界之窗，看看国外的老人是怎样打点自己的退休生活吧。</p>'
//	                }]
//	            },{
//	            	 columnWidth:.7,
//		                border:false,
//		                height:150,
//		                items:[{
//		                    title: '美国',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;调查显示，西方国家老人花费在家庭之外的活动时间要多于东方国家的老人。在物质生活方面，由于美国退休金、社会保险等社会福利制度相对完善，绝大多数老年人的晚年生活还是有保障的，因此很多美国人把退休生活当做实现自己心中理想的黄金时光。</p>'
//		                }]
//	            },{
//	            	 columnWidth:.5,
//		                border:false,
//		                height:200,
//		                items:[{
//		                    title: '英国',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;在英国，常常看到许多老人在进修学院里，和青年学生一起学习各种课程。比如说，参加一些工艺课程学习，如食品制作、雕塑、绘画、书法、美容等。虽然他们有些人已经有80多岁高龄，但是他们学习新东西来，还真是勤勤恳恳，乐此不疲。英国众多的社会及民间团体或俱乐部也给老年人提供了活动机会和去处。以退休者组织为例，地方政府定期为他们提供集会场所，并免费供膳。有些社团是按宗教信仰或兴趣组成的，如教友派教徒会、钓鱼爱好者俱乐部、训狗赛狗俱乐部等。</p>'
//		                }]
//	            },{
//	            	 columnWidth:.5,
//		                border:false,
//		                height:200,
//		                items:[{
//		                    title: '加拿大',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;加拿大地广人稀，资源丰富，经济发达，许多社区的基础设施比较完善。在加拿大的许多居住小区都建有健身房和室内游泳池，经常有老人们前去健身和游泳。有的小区还建有人工湖，在湖边随时都可以看见老人一边绕湖散步，一边愉快地谈心，几乎每天有两位七八十岁的老太在钓鱼，经常有人围观。此外，加拿大的国民教育资源优越，在加拿大本科生、硕士生、博士生比比皆是，退休老人大多都多才多艺，这让他们可以选择多种职业。退休后，他们很多人都在尝试一种新的感兴趣的职业，实现人生的多种价值。比如说，在加拿大的很多商店，临时促销员都是退休老人，他们把食品切成小块，放在塑料盒里，递给路过的人品尝，并给你介绍橱窗里的食品。</p>'
//		                }]
//	            },{
//	            	 columnWidth:.5,
//		                border:false,
//		                height:170,
//		                items:[{
//		                    title: '日本',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;日本是当今世界人均寿命最长的国家之一，女性的平均寿命80.9岁，男子的平均寿命75.2岁。政府完善的养老制度和丰富的老龄活动是他们长寿的源泉。现在的日本老人越来越不赞同应该待在家里这种传统的规范。很多老人退休后，倾向于选择茶道、插花、书法，以及一些运动和健康保健等方面的课程，来度过大把的退休时间。他们认为，这些不仅可以愉悦身心，也可以结识很多趣味相投的老年朋友。</p>'
//		                }]
//	            },{
//	            	 columnWidth:.5,
//		                border:false,
//		                height:170,
//		                items:[{
//		                    title: '新加坡',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;以华人为主的新加坡，中华民族尊老的传统美德得到了政府的积极提倡与鼓励。在倾向性政策的引导下，目前新加坡已婚子女与父母合住同一组屋或同一组屋区的住户已达41%左右，这样既方便了老人与子女在生活上互相照顾，又能使得老人含饴弄孙，享受天伦之乐。另一方面，新加坡的退休金较高，足以使老人们应付日常开销，因此子女们多不愿意父母退休了还工作操劳。所以，新加坡老人乐于过着悠闲的生活。</p>'
//		                }]
//	            },{
//	            	 columnWidth:.5,
//		                border:false,
//		                autoHeight:true,
//		                items:[{
//		                    title: '退休后生活来源',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<iframe id="contentFrame" name="content" height="330" frameborder="no" width="100%" src=\"a.jpg\" " scrolling="no"> </iframe>'
//		                }]
//	            },{
//	            	 columnWidth:.5,
//		                border:false,
//		                autoHeight:true,
//		                items:[{
//		                    title: '养老金替代率参考',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<iframe id="contentFrame" name="content" height="330" frameborder="no" width="100%" src=\"b.jpg\" " scrolling="no"> </iframe>'
//		                }]
//	            }
//            	
//            ]
//			
//		}],
//		buttonAlign : 'center',
//		buttons : [{text : '养老规划',
//			handler : function() {
//				cardPanel.getLayout().setActiveItem(1);
//			}}]
//		},
		{title:"客户需求采集",layout:'border',items:[{
			xtype:'portal',
            id:'center',
            region:'center',
            items:[{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		                title: '客户基本信息',
		                    items:[infoForm]
	            },{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		                items:[{
		                    title: '养老金需求',
		                    collapsible:true,
		                    layout:'fit',
		                    style:'padding:0px 0px 0px 0px',
		                    items:[needForm]
		                }]
	            }],
		buttonAlign : 'center',
		buttons : [/*{text : '上一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(0);
			}},*/{text : '下一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(1);
			}}]
		}] },
		{
			title:"规划策略",layout:'border',items:[{
			xtype:'portal',
            id:'center',
            region:'center',
            items:[{
           	 columnWidth:.5,
             border:false,
             autoHeight:true,
             items:[{
                 title: '养老金缺口',
                 collapsible:true,
                 layout:'fit',
                 style:'padding:0px 0px 0px 0px',
                 html:'<iframe id="contentFrame" name="content" height="240" frameborder="no" width="100%" src=\"chart/a.html\"  scrolling="no"> </iframe>'
             }]
     },{
     	 columnWidth:.5,
             border:false,
             autoHeight:true,
             items:[{
                 title: '现金流图',
                 collapsible:true,
                 layout:'fit',
                 style:'padding:0px 0px 0px 0px',
                 html:'<iframe id="contentFrame" name="content" height="240" frameborder="no" width="100%" src=\"chart/b.html\" scrolling="no"> </iframe>'
       }]},
       {
            	 columnWidth:1,
	                border:false,
	                autoHeight:true,
	                items:[{
	                	title: '投资收益预计',
	                    collapsible:true,
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    items:[infoGrid]
	    }]}],
		buttonAlign : 'center',
		buttons : [{text : '上一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(0);
			}},{text : '购买产品',
			handler : function() {
				cardPanel.getLayout().setActiveItem(2);
			}}
//		,{text : '投资规划',
//				handler : function() {
//					alert('待完成');
//				}}
		]
		}] 
			
		},{
			title:"产品推荐",layout:'border',items:[{
				xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		                    items:[productGrid]
		    }],
			buttonAlign : 'center',
			buttons : [{text : '上一步',
				handler : function() {
					cardPanel.getLayout().setActiveItem(1);
				}},{text : '保存',
				handler : function() {
					Ext.Msg.alert('提示','保存成功!');
				}},{
		  			text:'生成报告',
					handler:function(){
						var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'+ ' scrollbars=no, resizable=no,location=no, status=no';
						var fileName = 'readme.txt';// 电子杂志文件名称
						var uploadUrl = basepath + '/AnnexeDownload?filename='+ fileName + '&annexeName=' + fileName;
						window.open(uploadUrl, '', winPara);
					}
		  }]
			}] 
				
			}
]
});
	
	var addWindow = new Ext.Window({
		layout : 'fit',
		title:'养老规划新增',
	    autoScroll : true,
	    draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
	    modal : true,
	    width : 600,
	    height : 400,
	    loadMask : true,
	    border : false,
	    items : [ {
	        buttonAlign : "center",
	        layout : 'fit',
	        items : [cardPanel]
	    }]
	});
