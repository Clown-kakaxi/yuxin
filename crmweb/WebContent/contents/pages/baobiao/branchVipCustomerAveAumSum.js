Ext
        .onReady(function() {
            var h = document.body.clientHeight;
            var qForm = new Ext.form.FormPanel( {
                labelWidth : 90, // 标签宽度
                frame : true, // 是否渲染表单面板背景色
                labelAlign : 'middle', // 标签对齐方式
                buttonAlign : 'center',
                height : 120,
                    layout : 'column',
                    items : [  {
                        columnWidth : .25,
                        layout : 'form',
                        items : [ {
                            xtype : 'datefield',
                            fieldLabel : '统计日期', // 标签
                            id : 'MONTH',
                            format : 'Y-m-d',
                            editable : false,
                            name : 'MONTH', // name:后台根据此name属性取值
                            allowBlank : true, // 是否允许为空
                            labelStyle : 'text-align:right;',
                            // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                            anchor : '80%' // 宽度百分比
                        } ]
                } ],
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
					    	 
						}
					}]
            });
            var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/branchVipCustomerAveSum.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'PRE_YEAR_CUST_NUM',mapping :'PRE_YEAR_CUST_NUM'},
					{name: 'PRE_YEAR_CUST_AUM',mapping :'PRE_YEAR_CUST_AUM'},
					{name: 'CUST_NUM',mapping :'CUST_NUM'},
					{name: 'CUST_AUM',mapping :'CUST_AUM'},
					{name: 'COMP_CUST_NUM',mapping :'COMP_CUST_NUM'},
					{name: 'COMP_CUST_AUM',mapping :'COMP_CUST_AUM'},
					{name: 'LC_PRE_YEAR_CUST_NUM',mapping :'LC_PRE_YEAR_CUST_NUM'},
					{name: 'LC_PRE_YEAR_CUST_AUM',mapping :'LC_PRE_YEAR_CUST_AUM'},
					{name: 'LC_CUST_NUM',mapping :'LC_CUST_NUM'},
					{name: 'LC_CUST_AUM',mapping :'LC_CUST_AUM'},
					{name: 'LC_COMP_CUST_NUM',mapping :'LC_COMP_CUST_NUM'},
					{name: 'LC_COMP_CUST_AUM',mapping :'LC_COMP_CUST_AUM'},
					{name: 'ZJ_PRE_YEAR_CUST_NUM',mapping :'ZJ_PRE_YEAR_CUST_NUM'},
					{name: 'ZJ_PRE_YEAR_CUST_AUM',mapping :'ZJ_PRE_YEAR_CUST_AUM'},
					{name: 'ZJ_CUST_NUM',mapping :'ZJ_CUST_NUM'},
					{name: 'ZJ_CUST_AUM',mapping :'ZJ_CUST_AUM'},
					{name: 'ZJ_COMP_CUST_NUM',mapping :'ZJ_COMP_CUST_NUM'},
					{name: 'ZJ_COMP_CUST_AUM',mapping :'ZJ_COMP_CUST_AUM'},
					{name: 'HJ_PRE_YEAR_CUST_NUM',mapping :'HJ_PRE_YEAR_CUST_NUM'},
					{name: 'HJ_PRE_YEAR_CUST_AUM',mapping :'HJ_PRE_YEAR_CUST_AUM'},
					{name: 'HJ_CUST_NUM',mapping :'HJ_CUST_NUM'},
					{name: 'HJ_CUST_AUM',mapping :'HJ_CUST_AUM'},
					{name: 'HJ_COMP_CUST_NUM',mapping :'HJ_COMP_CUST_NUM'},
					{name: 'HJ_COMP_CUST_AUM',mapping :'HJ_COMP_CUST_AUM'},
					{name: 'BJ_PRE_YEAR_CUST_NUM',mapping :'BJ_PRE_YEAR_CUST_NUM'},
					{name: 'BJ_PRE_YEAR_CUST_AUM',mapping :'BJ_PRE_YEAR_CUST_AUM'},
					{name: 'BJ_CUST_NUM',mapping :'BJ_CUST_NUM'},
					{name: 'BJ_CUST_AUM',mapping :'BJ_CUST_AUM'},
					{name: 'BJ_COMP_CUST_NUM',mapping :'BJ_COMP_CUST_NUM'},
					{name: 'BJ_COMP_CUST_AUM',mapping :'BJ_COMP_CUST_AUM'},
					{name: 'PB_PRE_YEAR_CUST_NUM',mapping :'PB_PRE_YEAR_CUST_NUM'},
					{name: 'PB_PRE_YEAR_CUST_AUM',mapping :'PB_PRE_YEAR_CUST_AUM'},
					{name: 'PB_CUST_NUM',mapping :'PB_CUST_NUM'},
					{name: 'PB_CUST_AUM',mapping :'PB_CUST_AUM'},
					{name: 'PB_COMP_CUST_NUM',mapping :'PB_COMP_CUST_NUM'},
					{name: 'PB_COMP_CUST_AUM',mapping :'PB_COMP_CUST_AUM'}
					
					
					
				])
			});
            /** **************************************************************** */
            var fields = [], columns = [],  continentGroupRow = [],

            continentGroupRow = [ 
                                  {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '',
                colspan : 1,
                align : 'center'
            }, {
                header : '所有客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '理财客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '紫金客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '黄金客户',
                colspan : 6,
                align : 'center'
            }, {
                header : '白金客户',
                colspan : 6,
                align : 'center'
            },{
                header : '白金客户（私人银行客户）',
                colspan : 6,
                align : 'center'
            } ];
            var group = new Ext.ux.grid.ColumnHeaderGroup( {
                rows : [ continentGroupRow ]
            });

           
            columns = [ {
                dataIndex : 'MONTH',
                header : '统计日期',
                sortable : true
            },{
                dataIndex : 'ORG_NAME2',
                header : '中心支行名称',
                sortable : true
            }, {
                dataIndex : 'PRE_YEAR_CUST_NUM',
                header : '年初客户总数基数',
                sortable : true
            },
            {
                dataIndex : 'PRE_YEAR_CUST_AUM',
                header : '年初客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },{
                dataIndex : 'CUST_NUM',
                header : '客户总数',
                sortable : true
            }, {
                dataIndex : 'CUST_AUM',
                header : '客户日均AUM总数',
                sortable : true,
                width : 100,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'COMP_CUST_NUM',
                header : '客户总数增减',
                sortable : true
            }, {
                dataIndex : 'COMP_CUST_AUM',
                header : '客户日均AUN总数增减',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },  {
                dataIndex : 'LC_PRE_YEAR_CUST_NUM',
                header : '年初客户数基数',
                sortable : true
            },
            {
                dataIndex : 'LC_PRE_YEAR_CUST_AUM',
                header : '年初客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },{
                dataIndex : 'LC_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'LC_CUST_AUM',
                header : '客户日均AUM数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'LC_COMP_CUST_NUM',
                header : '客户数增减',
                sortable : true
            }, {
                dataIndex : 'LC_COMP_CUST_AUM',
                header : '客户日均AUN数增减',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },  {
                dataIndex : 'ZJ_PRE_YEAR_CUST_NUM',
                header : '年初客户数基数',
                sortable : true
            },
            {
                dataIndex : 'ZJ_PRE_YEAR_CUST_AUM',
                header : '年初客户日均AUM数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },{
                dataIndex : 'ZJ_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUST_AUM',
                header : '客户日均AUM数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'ZJ_COMP_CUST_NUM',
                header : '客户数增减',
                sortable : true
            }, {
                dataIndex : 'ZJ_COMP_CUST_AUM',
                header : '客户日均AUN数增减',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },  {
                dataIndex : 'HJ_PRE_YEAR_CUST_NUM',
                header : '年初客户数基数',
                sortable : true
            },
            {
                dataIndex : 'HJ_PRE_YEAR_CUST_AUM',
                header : '年初客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            },{
                dataIndex : 'HJ_CUST_NUM',
                header : '客户总数',
                sortable : true
            }, {
                dataIndex : 'HJ_CUST_AUM',
                header : '客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'HJ_COMP_CUST_NUM',
                header : '客户总数增减',
                sortable : true
            }, {
                dataIndex : 'HJ_COMP_CUST_NUM',
                header : '客户日均AUN总数增减',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'BJ_PRE_YEAR_CUST_NUM',
                header : '年初客户总数基数',
                sortable : true
            },
            {
                dataIndex : 'BJ_PRE_YEAR_CUST_AUM',
                header : '年初客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'BJ_CUST_NUM',
                header : '客户总数',
                sortable : true
            }, {
                dataIndex : 'BJ_CUST_AUM',
                header : '客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'BJ_COMP_CUST_NUM',
                header : '客户总数增减',
                sortable : true
            }, {
                dataIndex : 'BJ_COMP_CUST_AUM',
                header : '客户日均AUN总数增减',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'PB_PRE_YEAR_CUST_NUM',
                header : '年初客户总数基数',
                sortable : true
            },
            {
                dataIndex : 'PB_PRE_YEAR_CUST_AUM',
                header : '年初客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'PB_CUST_NUM',
                header : '客户总数',
                sortable : true
            }, {
                dataIndex : 'PB_CUST_AUM',
                header : '客户日均AUM总数',
                sortable : true,align:'right;',renderer: money('0,000.00')
            }, {
                dataIndex : 'PB_COMP_CUST_NUM',
                header : '客户总数增减',
                sortable : true
            }, {
                dataIndex : 'PB_COMP_CUST_AUM',
                header : '客户日均AUN总数增减',
                sortable : true,
                width : 100,align:'right;',renderer: money('0,000.00')
            } ];

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

            var grid = new Ext.grid.GridPanel( {
                height : h-148,
                store : store,
                stripeRows : true,
                columns : columns,
            	viewConfig : {
					// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
					},
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					},
                plugins : group,
                bbar : bbar,// 分页工具栏
                tbar : [

						//导出处理函数部分
							 new Com.yucheng.bob.ExpButton({
								  formPanel : 'listPanel',
								  iconCls:'exportIconCss',
								  id:'exportbt',
								  url : basepath+'/branchVipCustomerAveSum.json?'
							  	})	
				            
						
						]
						
            });
            /** **************************************************************** */
            // 布局模型
            var viewport = new Ext.Viewport( {
                layout : 'fit',
                items : [ {
                    layout : 'border',
                    items : [ {
                        region : 'north',
                        id : 'north-panel',
                        title : "统计分析->零售报表->客户日均AUM统计->支行客户日均AUM汇总表",
                        height : 148,
                        hidden : false,
                        margins : '0 0 0 0',
                        // layout: 'fit',
                        items : [ qForm ]
                    }, {
                        region : 'center',
                        autoScroll : true,
                        id : 'center-panel',
                        layout: 'fit',
                        margins : '0 0 0 0',
                        items : [ grid ]
                    } ]
                } ]
            });
        });