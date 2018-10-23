Ext
        .onReady(function() {
        	
        	var qForm = new Ext.form.FormPanel({
    			id:'qForm',
    			labelWidth : 90, // 标签宽度
    			frame : true, //是否渲染表单面板背景色
    			labelAlign : 'middle', // 标签对齐方式
    			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
    			buttonAlign : 'center',
    			height : 97,
    				layout : 'column',
    				border : false,
    				items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 70, // 标签宽度
						wdefaultType : 'textfield',
						border : false,
						items :[ {
	                            xtype : 'datefield',
	                            fieldLabel : '统计日期', // 标签
	                            id : 'MONTH',
	                            format : 'Y-m',
	                            editable : false,
	                            name : 'MONTH', // name:后台根据此name属性取值
	                            allowBlank : true, // 是否允许为空
	                            labelStyle : 'text-align:right;',
	                            // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
	                            anchor : '80%' // 宽度百分比
	                }]
					},{


						columnWidth : .25,
						layout : 'form',
						labelWidth : 70, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '机构',
							labelStyle : 'text-align:right;',
							id : 'ORG_NAME2', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'ORG_NAME2', 
							hiddenName: 'instncode',   //后台获取的参数名称
							anchor : '90%',
							checkBox:false //复选标志
						})]
		
	                
					}],
    		buttons : [{
    					text : '查询',
    					handler : function() {
    						var parameters = qForm.getForm().getValues(false);
    						store.baseParams = {
    							'condition':Ext.util.JSON.encode(parameters)
    						};
    						store.load({      
    							params : {
                                   start : 0,
                                   limit : bbar.pageSize/*,
                                   userId:Ext.encode(userId.aId)*/
                                }
    						});     
    				
    				   }},{
    					text : '重置',
    					     handler : function() {
    					    	 qForm.getForm().reset();
    					    	 Ext.getCmp('ORG_NAME2').setValue('');
    						}
    					}]
    		});
   		 var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/wdVipCustomerAveAumDetail.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'ORG_NAME4',mapping :'ORG_NAME4'},
					{name: 'CUST_ID',mapping :'CUST_ID'},
					{name: 'CUST_NAME',mapping :'CUST_NAME'},
					{name: 'PRE_GRADE',mapping :'PRE_GRADE'},
					{name: 'CUR_GRADE',mapping :'CUR_GRADE'},
					{name: 'AUM_AVG_MONTH',mapping :'AUM_AVG_MONTH'},
					{name: 'COMP_AUM_AVG_MONTH',mapping :'COMP_AUM_AVG_MONTH'}
					
					
				])
			});
   		//var sm = new Ext.grid.CheckboxSelectionModel();

   		// 定义自动当前页行号
   		var rownum = new Ext.grid.RowNumberer({
   					header : 'No.',
   					width : 28
   				});
   		// 定义列模型
   		var cm = new Ext.grid.ColumnModel([rownum, 
   		                                    {header : '统计日期',dataIndex : 'MONTH',width : 150,sortable : true},
   		                                    {header : '中心支行名称',dataIndex : 'ORG_NAME2',width : 150,sortable : true},
   		                                 {header : '网点名称',dataIndex : 'ORG_NAME4',width : 150,sortable : true},
   		                               		 {header : '客户号',dataIndex : 'CUST_ID',width : 150,sortable : true},
   		                               	    {header : '客户名称',dataIndex : 'CUST_NAME',sortable : true,width : 150},
   		                       			    {header : '变动前等级',dataIndex : 'PRE_GRADE',width : 100,sortable : true},
   		                    			    {header : '当前等级',dataIndex : 'CUR_GRADE',width : 100,sortable : true},
   		                    			     {header : '客户日均AUM',dataIndex : 'AUM_AVG_MONTH',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')},
    		                       			{header : '客户日均AUM增减',dataIndex : 'COMP_AUM_AVG_MONTH',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')}
   		                       			    
   		                       	           
   		                       			   ]);

   		
           

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								[ 100, '100条/页' ], [ 250, '250条/页' ],
								[ 500, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				resizable : true,
				width : 85
			});


			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
        	var listPanel = new Ext.grid.GridPanel(
					{
						id : 'listPanel',
						title : "查询结果",
						height:document.body.clientHeight-148,
						region : 'center',
						store : store,
						frame : true,
						stripeRows : true,
						layout:'fit',
						cm : cm,
						//sm : sm,
						bbar : bbar,// 分页工具栏
						tbar : [

								//导出处理函数部分
									 new Com.yucheng.bob.ExpButton({
										  formPanel : 'listPanel',
										  iconCls:'exportIconCss',
										  id:'exportbt',
										  url : basepath+'/wdVipCustomerAveAumDetail.json?'
									  	})	
						            
								
								],
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
            var viewport = new Ext.Viewport( {
                layout : 'fit',
                items : [ {
                    layout : 'border',
                    items : [ {
                        region : 'north',
                        id : 'north-panel',
                        title : "统计分析->零售报表->客户日均AUM统计->网点客户日均AUM明细",
                        height : 148,
                        hidden : false,
                        margins : '0 0 0 0',
                        // layout: 'fit',
                        items : [ qForm ]
                    }, {
                        region : 'center',
                        autoScroll : true,
                        layout: 'fit',
                        id : 'center-panel',
                        margins : '0 0 0 0',
                        items : [ listPanel ]
                    } ]
                } ]
            });
        });