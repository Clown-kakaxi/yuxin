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
	
                
                },
                {

					columnWidth : .25,
					layout : 'form',
					labelWidth : 70, // 标签宽度
					defaultType : 'textfield',
					border : false,
					items : [
				    new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '客户经理', 
					id:'CUST_MANAGER',
					labelStyle: 'text-align:right;',
					name : 'CUST_MANAGER',
					hiddenName:'custMgrId',
					searchRoleType:('1025,1027'),  //指定查询角色属性 ,默认全部角色
					searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:false,
					anchor : '90%'
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
					    	 Ext.getCmp('CUST_MANAGER').setValue('');
						}
					}]
            });
            var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/custMgrVipCustZbNumSum.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'CUST_MANAGER_ID',mapping :'CUST_MANAGER_ID'},
					{name: 'CUST_MANAGER_NAME',mapping :'CUST_MANAGER_NAME'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'ORG_NAME4',mapping :'ORG_NAME4'},
					{name: 'TOTAL_CUST_NUM',mapping :'TOTAL_CUST_NUM'},
					{name: 'TOTAL_DEP',mapping :'TOTAL_DEP'},
					{name: 'TOTAL_AUM',mapping :'TOTAL_AUM'},
					{name: 'LC_CUST_NUM',mapping :'LC_CUST_NUM'},
					{name: 'LC_CUST_NUM_RATE',mapping :'LC_CUST_NUM_RATE'},
					{name: 'LC_AUM',mapping :'LC_AUM'},
					{name: 'LC_AUM_RATE',mapping :'LC_AUM_RATE'},
					{name: 'LC_DEP',mapping :'LC_DEP'},
					{name: 'LC_DEP_RATE',mapping :'LC_DEP_RATE'},
					{name: 'ZJ_CUST_NUM',mapping :'ZJ_CUST_NUM'},
					{name: 'ZJ_CUST_NUM_RATE',mapping :'ZJ_CUST_NUM_RATE'},
					{name: 'ZJ_AUM',mapping :'ZJ_AUM'},
					{name: 'ZJ_AUM_RATE',mapping :'ZJ_AUM_RATE'},
					{name: 'ZJ_DEP',mapping :'ZJ_DEP'},
					{name: 'ZJ_DEP_RATE',mapping :'ZJ_DEP_RATE'},
					{name: 'HJ_CUST_NUM',mapping :'HJ_CUST_NUM'},
					{name: 'HJ_CUST_NUM_RATE',mapping :'HJ_CUST_NUM_RATE'},
					{name: 'HJ_AUM',mapping :'HJ_AUM'},
					{name: 'HJ_AUM_RATE',mapping :'HJ_AUM_RATE'},
					{name: 'HJ_DEP',mapping :'HJ_DEP'},
					{name: 'HJ_DEP_RATE',mapping :'HJ_DEP_RATE'},
					{name: 'BJ_CUST_NUM',mapping :'BJ_CUST_NUM'},
					{name: 'BJ_CUST_NUM_RATE',mapping :'BJ_CUST_NUM_RATE'},
					{name: 'BJ_AUM',mapping :'BJ_AUM'},
					{name: 'BJ_AUM_RATE',mapping :'BJ_AUM_RATE'},
					{name: 'BJ_DEP',mapping :'BJ_DEP'},
					{name: 'BJ_DEP_RATE',mapping :'BJ_DEP_RATE'},
					{name: 'PB_CUST_NUM',mapping :'PB_CUST_NUM'},
					{name: 'PB_CUST_NUM_RATE',mapping :'PB_CUST_NUM_RATE'},
					{name: 'PB_AUM',mapping :'PB_AUM'},
					{name: 'PB_AUM_RATE',mapping :'PB_AUM_RATE'},
					{name: 'PB_DEP',mapping :'PB_DEP'},
					{name: 'PB_DEP_RATE',mapping :'PB_DEP_RATE'}
					
				])
			});
            /** **************************************************************** */
            var fields = [], columns = [],  continentGroupRow = [],

            continentGroupRow = [ {
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
            },
            {
                header : '',
                colspan : 1,
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
                header : '月份',
                sortable : true
            },  {
                dataIndex : 'CUST_MANAGER_ID',
                header : '客户经理编号',
                sortable : true
            }, {
                dataIndex : 'CUST_MANAGER_NAME',
                header : '客户经理名称',
                sortable : true
            }, {
                dataIndex : 'ORG_NAME2',
                header : '中心支行名称',
                sortable : true
            }, {
                dataIndex : 'ORG_NAME4',
                header : '网点名称',
                sortable : true
            },{
                dataIndex : 'TOTAL_CUST_NUM',
                header : '总客户数',
                sortable : true
            }, {
                dataIndex : 'TOTAL_AUM',
                header : '总资产',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'TOTAL_DEP',
                header : '总存款',
                sortable : true
            }, {
                dataIndex : 'LC_CUST_NUM_RATE',
                header : '数量占比',
                sortable : true
            }, {
                dataIndex : 'LC_AUM_RATE',
                header : '资产占比',
                sortable : true
            }, {
                dataIndex : 'LC_DEP_RATE',
                header : '存款占比',
                sortable : true
            }, {
                dataIndex : 'LC_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'LC_DEP',
                header : '存款',
                sortable : true
            }, {
                dataIndex : 'LC_AUM',
                header : '资产',
                sortable : true
            },{
                dataIndex : 'ZJ_CUST_NUM_RATE',
                header : '数量占比',
                sortable : true
            }, {
                dataIndex : 'ZJ_AUM_RATE',
                header : '资产占比',
                sortable : true
            }, {
                dataIndex : 'ZJ_DEP_RATE',
                header : '存款占比',
                sortable : true
            }, {
                dataIndex : 'ZJ_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'ZJ_DEP',
                header : '存款',
                sortable : true
            }, {
                dataIndex : 'ZJ_AUM',
                header : '资产',
                sortable : true
            },{
                dataIndex : 'HJ_CUST_NUM_RATE',
                header : '数量占比',
                sortable : true
            }, {
                dataIndex : 'HJ_AUM_RATE',
                header : '资产占比',
                sortable : true
            }, {
                dataIndex : 'HJ_DEP_RATE',
                header : '存款占比',
                sortable : true
            }, {
                dataIndex : 'HJ_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'HJ_DEP',
                header : '存款',
                sortable : true
            }, {
                dataIndex : 'HJ_AUM',
                header : '资产',
                sortable : true
            },{
                dataIndex : 'BJ_CUST_NUM_RATE',
                header : '数量占比',
                sortable : true
            }, {
                dataIndex : 'BJ_AUM_RATE',
                header : '资产占比',
                sortable : true
            }, {
                dataIndex : 'BJ_DEP_RATE',
                header : '存款占比',
                sortable : true
            }, {
                dataIndex : 'BJ_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'BJ_DEP',
                header : '存款',
                sortable : true
            }, {
                dataIndex : 'BJ_AUM',
                header : '资产',
                sortable : true
            },{
                dataIndex : 'PB_CUST_NUM_RATE',
                header : '数量占比',
                sortable : true
            }, {
                dataIndex : 'PB_AUM_RATE',
                header : '资产占比',
                sortable : true
            }, {
                dataIndex : 'PB_DEP_RATE',
                header : '存款占比',
                sortable : true
            }, {
                dataIndex : 'PB_CUST_NUM',
                header : '客户数',
                sortable : true
            }, {
                dataIndex : 'PB_DEP',
                header : '存款',
                sortable : true
            }, {
                dataIndex : 'PB_AUM',
                header : '资产',
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
								  url : basepath+'/custMgrVipCustzbqkSum.json?'
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
                        title : "统计分析->零售报表->客户占比情况数量汇总-网点客户占比情况数量汇总",
                        height : 148,
                        hidden : false,
                        margins : '0 0 0 0',
                        // layout: 'fit',
                        items : [ qForm ]
                    }, {
                        region : 'center',
                        layout: 'fit',
                        autoScroll : true,
                        id : 'center-panel',
                        margins : '0 0 0 0',
                        items : [ grid ]
                    } ]
                } ]
            });
        });