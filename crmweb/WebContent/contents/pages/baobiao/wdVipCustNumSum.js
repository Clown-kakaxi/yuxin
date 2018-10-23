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
                            fieldLabel : '月份', // 标签
                            id : 'MONTH',
                            format : 'Y-m',
                            editable : false,
                            name : 'MONTH', // name:后台根据此name属性取值
                            allowBlank : true, // 是否允许为空
                            labelStyle : 'text-align:right;',
                            // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
                            anchor : '80%' // 宽度百分比
                        } ]
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
					    	 Ext.getCmp('ORG_NAME2').setValue('');
						}
					}]
            });
            var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/wdVipCustNumSum.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'ORG_NAME4',mapping :'ORG_NAME4'},
					{name: 'LC_PRE_MONTH_CUST_NUM',mapping :'LC_PRE_MONTH_CUST_NUM'},
					{name: 'LC_CUR_MONTH_CUST_NUM',mapping :'LC_CUR_MONTH_CUST_NUM'},
					{name: 'LC_COMP_YEAR_START',mapping :'LC_COMP_YEAR_START'},
					{name: 'LC_CUR_MONTH_UP_NUM',mapping :'LC_CUR_MONTH_UP_NUM'},
					{name: 'LC_CUR_MONTH_IN_NUM',mapping :'LC_CUR_MONTH_IN_NUM'},
					{name: 'LC_CUR_MONTH_NEW_NUM',mapping :'LC_CUR_MONTH_NEW_NUM'},
					{name: 'LC_CUR_MONTH_ADD_NUM',mapping :'LC_CUR_MONTH_ADD_NUM'},
					{name: 'LC_CUR_MONTH_DOWN_NUM',mapping :'LC_CUR_MONTH_DOWN_NUM'},
					{name: 'LC_CUR_MONTH_OUT_NUM',mapping :'LC_CUR_MONTH_OUT_NUM'},
					{name: 'LC_CUR_MONTH_CLOSE_NUM',mapping :'LC_CUR_MONTH_CLOSE_NUM'},
					{name: 'LC_CUR_MONTH_REDUCE_NUM',mapping :'LC_CUR_MONTH_REDUCE_NUM'},
					{name: 'ZJ_PRE_MONTH_CUST_NUM',mapping :'ZJ_PRE_MONTH_CUST_NUM'},
					{name: 'ZJ_CUR_MONTH_CUST_NUM',mapping :'ZJ_CUR_MONTH_CUST_NUM'},
					{name: 'ZJ_COMP_YEAR_START',mapping :'ZJ_COMP_YEAR_START'},
					{name: 'ZJ_CUR_MONTH_UP_NUM',mapping :'ZJ_CUR_MONTH_UP_NUM'},
					{name: 'ZJ_CUR_MONTH_IN_NUM',mapping :'ZJ_CUR_MONTH_IN_NUM'},
					{name: 'ZJ_CUR_MONTH_NEW_NUM',mapping :'ZJ_CUR_MONTH_NEW_NUM'},
					{name: 'ZJ_CUR_MONTH_ADD_NUM',mapping :'ZJ_CUR_MONTH_ADD_NUM'},
					{name: 'ZJ_CUR_MONTH_DOWN_NUM',mapping :'ZJ_CUR_MONTH_DOWN_NUM'},
					{name: 'ZJ_CUR_MONTH_OUT_NUM',mapping :'ZJ_CUR_MONTH_OUT_NUM'},
					{name: 'ZJ_CUR_MONTH_CLOSE_NUM',mapping :'ZJ_CUR_MONTH_CLOSE_NUM'},
					{name: 'ZJ_CUR_MONTH_REDUCE_NUM',mapping :'ZJ_CUR_MONTH_REDUCE_NUM'},
					{name: 'HJ_PRE_MONTH_CUST_NUM',mapping :'HJ_PRE_MONTH_CUST_NUM'},
					{name: 'HJ_CUR_MONTH_CUST_NUM',mapping :'HJ_CUR_MONTH_CUST_NUM'},
					{name: 'HJ_COMP_YEAR_START',mapping :'HJ_COMP_YEAR_START'},
					{name: 'HJ_CUR_MONTH_UP_NUM',mapping :'HJ_CUR_MONTH_UP_NUM'},
					{name: 'HJ_CUR_MONTH_IN_NUM',mapping :'HJ_CUR_MONTH_IN_NUM'},
					{name: 'HJ_CUR_MONTH_NEW_NUM',mapping :'HJ_CUR_MONTH_NEW_NUM'},
					{name: 'HJ_CUR_MONTH_ADD_NUM',mapping :'HJ_CUR_MONTH_ADD_NUM'},
					{name: 'HJ_CUR_MONTH_DOWN_NUM',mapping :'HJ_CUR_MONTH_DOWN_NUM'},
					{name: 'HJ_CUR_MONTH_OUT_NUM',mapping :'HJ_CUR_MONTH_OUT_NUM'},
					{name: 'HJ_CUR_MONTH_CLOSE_NUM',mapping :'HJ_CUR_MONTH_CLOSE_NUM'},
					{name: 'HJ_CUR_MONTH_REDUCE_NUM',mapping :'HJ_CUR_MONTH_REDUCE_NUM'},
					{name: 'BJ_PRE_MONTH_CUST_NUM',mapping :'BJ_PRE_MONTH_CUST_NUM'},
					{name: 'BJ_CUR_MONTH_CUST_NUM',mapping :'BJ_CUR_MONTH_CUST_NUM'},
					{name: 'BJ_COMP_YEAR_START',mapping :'BJ_COMP_YEAR_START'},
					{name: 'BJ_CUR_MONTH_UP_NUM',mapping :'BJ_CUR_MONTH_UP_NUM'},
					{name: 'BJ_CUR_MONTH_IN_NUM',mapping :'BJ_CUR_MONTH_IN_NUM'},
					{name: 'BJ_CUR_MONTH_NEW_NUM',mapping :'BJ_CUR_MONTH_NEW_NUM'},
					{name: 'BJ_CUR_MONTH_ADD_NUM',mapping :'BJ_CUR_MONTH_ADD_NUM'},
					{name: 'BJ_CUR_MONTH_DOWN_NUM',mapping :'BJ_CUR_MONTH_DOWN_NUM'},
					{name: 'BJ_CUR_MONTH_OUT_NUM',mapping :'BJ_CUR_MONTH_OUT_NUM'},
					{name: 'BJ_CUR_MONTH_CLOSE_NUM',mapping :'BJ_CUR_MONTH_CLOSE_NUM'},
					{name: 'BJ_CUR_MONTH_REDUCE_NUM',mapping :'BJ_CUR_MONTH_REDUCE_NUM'},
					{name: 'PB_PRE_MONTH_CUST_NUM',mapping :'PB_PRE_MONTH_CUST_NUM'},
					{name: 'PB_CUR_MONTH_CUST_NUM',mapping :'PB_CUR_MONTH_CUST_NUM'},
					{name: 'PB_COMP_YEAR_START',mapping :'PB_COMP_YEAR_START'},
					{name: 'PB_CUR_MONTH_UP_NUM',mapping :'PB_CUR_MONTH_UP_NUM'},
					{name: 'PB_CUR_MONTH_IN_NUM',mapping :'PB_CUR_MONTH_IN_NUM'},
					{name: 'PB_CUR_MONTH_NEW_NUM',mapping :'PB_CUR_MONTH_NEW_NUM'},
					{name: 'PB_CUR_MONTH_ADD_NUM',mapping :'PB_CUR_MONTH_ADD_NUM'},
					{name: 'PB_CUR_MONTH_DOWN_NUM',mapping :'PB_CUR_MONTH_DOWN_NUM'},
					{name: 'PB_CUR_MONTH_OUT_NUM',mapping :'PB_CUR_MONTH_OUT_NUM'},
					{name: 'PB_CUR_MONTH_CLOSE_NUM',mapping :'PB_CUR_MONTH_CLOSE_NUM'},
					{name: 'PB_CUR_MONTH_REDUCE_NUM',mapping :'PB_CUR_MONTH_REDUCE_NUM'}
					
				])
			});
            /** **************************************************************** */
            var fields = [], columns = [],  continentGroupRow = [],continentGroupRow1=[];

            continentGroupRow = [ {
                header : '',
                colspan : 1,
                align : 'center'
            }, {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '理财客户',
                colspan : 11,
                align : 'center'
            }, {
                header : '紫金客户',
                colspan : 11,
                align : 'center'
            }, {
                header : '黄金客户',
                colspan : 11,
                align : 'center'
            }, {
                header : '白金客户',
                colspan : 11,
                align : 'center'
            },{
                header : '白金客户（私人银行客户）',
                colspan :11,
                align : 'center'
            } ];
            continentGroupRow1 = [ {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '',
                colspan : 1,
                align : 'center'
            }, 
            
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '本月新增',
                colspan : 4,
                align : 'center'
            }, {
                header : '本月减少',
                colspan : 4,
                align : 'center'
            }, 
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '本月新增',
                colspan : 4,
                align : 'center'
            }, {
                header : '本月减少',
                colspan : 4,
                align : 'center'
            }, 
           
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '本月新增',
                colspan : 4,
                align : 'center'
            }, {
                header : '本月减少',
                colspan : 4,
                align : 'center'
            }, 
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
           
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '本月新增',
                colspan : 4,
                align : 'center'
            }, {
                header : '本月减少',
                colspan : 4,
                align : 'center'
            }, 
           
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },
            {
                header : '',
                colspan : 1,
                align : 'center'
            },{
                header : '本月新增',
                colspan : 4,
                align : 'center'
            }, {
                header : '本月减少',
                colspan : 4,
                align : 'center'
            } ];
            var group = new Ext.ux.grid.ColumnHeaderGroup( {
                rows : [ continentGroupRow, continentGroupRow1]
            });

           
            columns = [ {
                dataIndex : 'MONTH',
                header : '月份',
                sortable : true
            },{
                dataIndex : 'ORG_NAME2',
                header : '中心支行名称',
                sortable : true
            },{
                dataIndex : 'ORG_NAME4',
                header : '网点名称',
                sortable : true
            }, {
                dataIndex : 'LC_PRE_MONTH_CUST_NUM',
                header : '上月客户存量',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_CUST_NUM',
                header : '本月客户存量',
                sortable : true,
                width : 100
            }, 
            {
                dataIndex : 'LC_COMP_YEAR_START',
                header : '比年初基数变化量',
                sortable : true
            },{
                dataIndex : 'LC_CUR_MONTH_UP_NUM',
                header : '本月升级',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_IN_NUM',
                header : '本月行内转入',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_NEW_NUM',
                header : '本月新增',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_ADD_NUM',
                header : '本月新增总数',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_DOWN_NUM',
                header : '本月降级',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_OUT_NUM',
                header : '本月行内转出',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_CLOSE_NUM',
                header : '本月销户',
                sortable : true
            }, {
                dataIndex : 'LC_CUR_MONTH_REDUCE_NUM',
                header : '本月减少总量',
                sortable : true
            }, {
                dataIndex : 'ZJ_PRE_MONTH_CUST_NUM',
                header : '上月客户存量',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_CUST_NUM',
                header : '本月客户存量',
                sortable : true,
                width : 100
            }, 
            {
                dataIndex : 'ZJ_COMP_YEAR_START',
                header : '比年初基数变化量',
                sortable : true
            },{
                dataIndex : 'ZJ_CUR_MONTH_UP_NUM',
                header : '本月升级',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_IN_NUM',
                header : '本月行内转入',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_NEW_NUM',
                header : '本月新增',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_ADD_NUM',
                header : '本月新增总数',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_DOWN_NUM',
                header : '本月降级',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_OUT_NUM',
                header : '本月行内转出',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_CLOSE_NUM',
                header : '本月销户',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUR_MONTH_REDUCE_NUM',
                header : '本月减少总量',
                sortable : true
            }, {
                dataIndex : 'HJ_PRE_MONTH_CUST_NUM',
                header : '上月客户存量',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_CUST_NUM',
                header : '本月客户存量',
                sortable : true,
                width : 100
            }, 
            {
                dataIndex : 'HJ_COMP_YEAR_START',
                header : '比年初基数变化量',
                sortable : true
            },{
                dataIndex : 'HJ_CUR_MONTH_UP_NUM',
                header : '本月升级',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_IN_NUM',
                header : '本月行内转入',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_NEW_NUM',
                header : '本月新增',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_ADD_NUM',
                header : '本月新增总数',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_DOWN_NUM',
                header : '本月降级',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_OUT_NUM',
                header : '本月行内转出',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_CLOSE_NUM',
                header : '本月销户',
                sortable : true
            }, {
                dataIndex : 'HJ_CUR_MONTH_REDUCE_NUM',
                header : '本月减少总量',
                sortable : true
            }, {
                dataIndex : 'BJ_PRE_MONTH_CUST_NUM',
                header : '上月客户存量',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_CUST_NUM',
                header : '本月客户存量',
                sortable : true,
                width : 100
            },
            {
                dataIndex : 'BJ_COMP_YEAR_START',
                header : '比年初基数变化量',
                sortable : true
            },{
                dataIndex : 'BJ_CUR_MONTH_UP_NUM',
                header : '本月升级',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_IN_NUM',
                header : '本月行内转入',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_NEW_NUM',
                header : '本月新增',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_ADD_NUM',
                header : '本月新增总数',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_DOWN_NUM',
                header : '本月降级',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_OUT_NUM',
                header : '本月行内转出',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_CLOSE_NUM',
                header : '本月销户',
                sortable : true
            }, {
                dataIndex : 'BJ_CUR_MONTH_REDUCE_NUM',
                header : '本月减少总量',
                sortable : true
            }, {
                dataIndex : 'PB_PRE_MONTH_CUST_NUM',
                header : '上月客户存量',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_CUST_NUM',
                header : '本月客户存量',
                sortable : true,
                width : 100
            }, 
            {
                dataIndex : 'PB_COMP_YEAR_START',
                header : '比年初基数变化量',
                sortable : true
            },{
                dataIndex : 'PB_CUR_MONTH_UP_NUM',
                header : '本月升级',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_IN_NUM',
                header : '本月行内转入',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_NEW_NUM',
                header : '本月新增',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_ADD_NUM',
                header : '本月新增总数',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_DOWN_NUM',
                header : '本月降级',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_OUT_NUM',
                header : '本月行内转出',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_CLOSE_NUM',
                header : '本月销户',
                sortable : true
            }, {
                dataIndex : 'PB_CUR_MONTH_REDUCE_NUM',
                header : '本月减少总量',
                sortable : true
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
                columnLines:true,
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
								  url : basepath+'/wdVipCustNumSum.json?'
							  	})	
				            
						
						]
						
            });
            grid.on('afterrender', function(   
                    grid) {   
                var elments = Ext.select(".x-grid3-header");//.x-grid3-hd   
                elments.each(function(el) {   
                            el.setStyle("background-color", '#CBBC82');// 设置不同的颜色   
                        }, this)
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
                        title : "统计分析->零售报表->客户数量汇总->网点客户数量汇总表",
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
                        items : [ grid ]
                    } ]
                } ]
            });
        });