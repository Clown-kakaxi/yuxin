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
		        proxy : new Ext.data.HttpProxy({url:basepath+'/branchVipCustdblvSum.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'ZJ_VIP_NUM',mapping :'ZJ_VIP_NUM'},
					{name: 'ZJ_STANDARD_NUM',mapping :'ZJ_STANDARD_NUM'},
					{name: 'ZJ_STANDARD_RATE',mapping :'ZJ_STANDARD_RATE'},
					{name: 'HJ_VIP_NUM',mapping :'HJ_VIP_NUM'},
					{name: 'HJ_STANDARD_NUM',mapping :'HJ_STANDARD_NUM'},
					{name: 'HJ_STANDARD_RATE',mapping :'HJ_STANDARD_RATE'},
					{name: 'BJ_VIP_NUM',mapping :'BJ_VIP_NUM'},
					{name: 'BJ_STANDARD_NUM',mapping :'BJ_STANDARD_NUM'},
					{name: 'BJ_STANDARD_RATE',mapping :'BJ_STANDARD_RATE'}
					
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
                header : '白金客户',
                colspan :3,
                align : 'center'
            }, {
                header : '黄金客户',
                colspan : 3,
                align : 'center'
            }, {
                header : '紫金客户',
                colspan : 3,
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
                dataIndex : 'ORG_NAME2',
                header : '机构',
                sortable : true
            },{
                dataIndex : 'ZJ_VIP_NUM',
                header : '持卡客户数',
                sortable : true
            }, {
                dataIndex : 'ZJ_STANDARD_NUM',
                header : '达标客户数',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'ZJ_STANDARD_RATE',
                header : '达标率',
                sortable : true
            },{
                dataIndex : 'HJ_VIP_NUM',
                header : '持卡客户数',
                sortable : true
            }, {
                dataIndex : 'HJ_STANDARD_NUM',
                header : '达标客户数',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'HJ_STANDARD_RATE',
                header : '达标率',
                sortable : true
            },{
                dataIndex : 'BJ_VIP_NUM',
                header : '持卡客户数',
                sortable : true
            }, {
                dataIndex : 'BJ_STANDARD_NUM',
                header : '达标客户数',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'BJ_STANDARD_RATE',
                header : '达标率',
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
								  url : basepath+'/branchVipCustdblvSum.json?'
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
                        title : "统计分析->零售报表->持卡客户达标率统计->支行持卡客户达标率统计",
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