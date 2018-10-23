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
                } ,{


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
		        proxy : new Ext.data.HttpProxy({url:basepath+'/wdVipCustProSum.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'ORG_NAME4',mapping :'ORG_NAME4'},
					{name: 'ZJ_STANDARD_NUM',mapping :'ZJ_STANDARD_NUM'},
					{name: 'ZJ_STANDARD_RATE',mapping :'ZJ_STANDARD_RATE'},
					{name: 'ZJ_DXT',mapping :'ZJ_DXT'},
					{name: 'ZJ_WY',mapping :'ZJ_WY'},
					{name: 'ZJ_XYK',mapping :'ZJ_XYK'},
					{name: 'ZJ_LC',mapping :'ZJ_LC'},
					{name: 'ZJ_SJYH',mapping :'ZJ_SJYH'},
					{name: 'ZJ_YZKX',mapping :'ZJ_YZKX'},
					{name: 'ZJ_ZFZHT',mapping :'ZJ_ZFZHT'},
					{name: 'ZJ_TYHF',mapping :'ZJ_TYHF'},
					{name: 'ZJ_CDSY',mapping :'ZJ_CDSY'},
					{name: 'ZJ_JJ',mapping :'ZJ_JJ'},
					{name: 'ZJ_GJS',mapping :'ZJ_GJS'},
					{name: 'ZJ_YBT',mapping :'ZJ_YBT'},
					{name: 'ZJ_CFYW',mapping :'ZJ_CFYW'},
					{name: 'HJ_STANDARD_NUM',mapping :'HJ_STANDARD_NUM'},
					{name: 'HJ_STANDARD_RATE',mapping :'HJ_STANDARD_RATE'},
					{name: 'HJ_DXT',mapping :'HJ_DXT'},
					{name: 'HJ_WY',mapping :'HJ_WY'},
					{name: 'HJ_XYK',mapping :'HJ_XYK'},
					{name: 'HJ_LC',mapping :'HJ_LC'},
					{name: 'HJ_SJYH',mapping :'HJ_SJYH'},
					{name: 'HJ_YZKX',mapping :'HJ_YZKX'},
					{name: 'HJ_ZFZHT',mapping :'HJ_ZFZHT'},
					{name: 'HJ_TYHF',mapping :'HJ_TYHF'},
					{name: 'HJ_CDSY',mapping :'HJ_CDSY'},
					{name: 'HJ_JJ',mapping :'HJ_JJ'},
					{name: 'HJ_GJS',mapping :'HJ_GJS'},
					{name: 'HJ_YBT',mapping :'HJ_YBT'},
					{name: 'HJ_CFYW',mapping :'HJ_CFYW'},
					{name: 'BJ_STANDARD_NUM',mapping :'BJ_STANDARD_NUM'},
					{name: 'BJ_STANDARD_RATE',mapping :'BJ_STANDARD_RATE'},
					{name: 'BJ_DXT',mapping :'BJ_DXT'},
					{name: 'BJ_WY',mapping :'BJ_WY'},
					{name: 'BJ_XYK',mapping :'BJ_XYK'},
					{name: 'BJ_LC',mapping :'BJ_LC'},
					{name: 'BJ_SJYH',mapping :'BJ_SJYH'},
					{name: 'BJ_YZKX',mapping :'BJ_YZKX'},
					{name: 'BJ_ZFZHT',mapping :'BJ_ZFZHT'},
					{name: 'BJ_TYHF',mapping :'BJ_TYHF'},
					{name: 'BJ_CDSY',mapping :'BJ_CDSY'},
					{name: 'BJ_JJ',mapping :'BJ_JJ'},
					{name: 'BJ_GJS',mapping :'BJ_GJS'},
					{name: 'BJ_YBT',mapping :'BJ_YBT'},
					{name: 'BJ_CFYW',mapping :'BJ_CFYW'}
					
					
					
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
            }, {
                header : '紫金客户',
                colspan : 15,
                align : 'center'
            }, {
                header : '黄金客户',
                colspan : 15,
                align : 'center'
            }, {
                header : '白金客户',
                colspan : 15,
                align : 'center'
            } ];
            continentGroupRow1 = [ {
                header : '',
                colspan : 1,
                align : 'center'
            },  {
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
                header : '达标率',
                colspan : 2,
                align : 'center'
            },
            {
                header : '短信通',
                colspan : 1,
                align : 'center'
            },
            {
                header : '网银',
                colspan : 1,
                align : 'center'
            },
            {
                header : '信用卡',
                colspan : 1,
                align : 'center'
            },{
                header : '理财产品',
                colspan : 1,
                align : 'center'
            }, {
                header : '手机银行',
                colspan : 1,
                align : 'center'
            }, 
            {
                header : '银证快线',
                colspan : 1,
                align : 'center'
            },
            {
                header : '智富账户通',
                colspan : 1,
                align : 'center'
            },
            {
                header : '太阳豪富',
                colspan : 1,
                align : 'center'
            },
            {
                header : '基金',
                colspan : 1,
                align : 'center'
            },{
                header : '贵金属',
                colspan : 1,
                align : 'center'
            }, {
                header : '银保通',
                colspan : 1,
                align : 'center'
            }, 
            {
                header : '创富业务',
                colspan : 1,
                align : 'center'
            },
            {
                header : '存贷双盈',
                colspan : 1,
                align : 'center'
            }, {
                header : '达标率',
                colspan : 2,
                align : 'center'
            },
            {
                header : '短信通',
                colspan : 1,
                align : 'center'
            },
            {
                header : '网银',
                colspan : 1,
                align : 'center'
            },
            {
                header : '信用卡',
                colspan : 1,
                align : 'center'
            },{
                header : '理财产品',
                colspan : 1,
                align : 'center'
            }, {
                header : '手机银行',
                colspan : 1,
                align : 'center'
            }, 
            {
                header : '银证快线',
                colspan : 1,
                align : 'center'
            },
            {
                header : '智富账户通',
                colspan : 1,
                align : 'center'
            },
            {
                header : '太阳豪富',
                colspan : 1,
                align : 'center'
            },
            {
                header : '基金',
                colspan : 1,
                align : 'center'
            },{
                header : '贵金属',
                colspan : 1,
                align : 'center'
            }, {
                header : '银保通',
                colspan : 1,
                align : 'center'
            }, 
            {
                header : '创富业务',
                colspan : 1,
                align : 'center'
            },
            {
                header : '存贷双盈',
                colspan : 1,
                align : 'center'
            }, {
                header : '达标率',
                colspan : 2,
                align : 'center'
            },
            {
                header : '短信通',
                colspan : 1,
                align : 'center'
            },
            {
                header : '网银',
                colspan : 1,
                align : 'center'
            },
            {
                header : '信用卡',
                colspan : 1,
                align : 'center'
            },{
                header : '理财产品',
                colspan : 1,
                align : 'center'
            }, {
                header : '手机银行',
                colspan : 1,
                align : 'center'
            }, 
            {
                header : '银证快线',
                colspan : 1,
                align : 'center'
            },
            {
                header : '智富账户通',
                colspan : 1,
                align : 'center'
            },
            {
                header : '太阳豪富',
                colspan : 1,
                align : 'center'
            },
            {
                header : '基金',
                colspan : 1,
                align : 'center'
            },{
                header : '贵金属',
                colspan : 1,
                align : 'center'
            }, {
                header : '银保通',
                colspan : 1,
                align : 'center'
            }, 
            {
                header : '创富业务',
                colspan : 1,
                align : 'center'
            },
            {
                header : '存贷双盈',
                colspan : 1,
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
                dataIndex : 'ZJ_STANDARD_NUM',
                header : '达标人数',
                sortable : true
            }, {
                dataIndex : 'ZJ_STANDARD_RATE',
                header : '覆盖率',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'ZJ_DXT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_WY',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_XYK',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_LC',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_SJYH',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_YZKX',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_ZFZHT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_TYHF',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_CDSY',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_JJ',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_GJS',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_YBT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'ZJ_CFYW',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            } , {
                dataIndex : 'HJ_STANDARD_NUM',
                header : '达标人数',
                sortable : true
            }, {
                dataIndex : 'HJ_STANDARD_RATE',
                header : '覆盖率',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'HJ_DXT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_WY',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_XYK',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_LC',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_SJYH',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_YZKX',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_ZFZHT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_TYHF',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_CDSY',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_JJ',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_GJS',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_YBT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'HJ_CFYW',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            },{
                dataIndex : 'BJ_STANDARD_NUM',
                header : '达标人数',
                sortable : true
            }, {
                dataIndex : 'BJ_STANDARD_RATE',
                header : '覆盖率',
                sortable : true,
                width : 100
            }, {
                dataIndex : 'BJ_DXT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_WY',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_XYK',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_LC',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_SJYH',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_YZKX',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_ZFZHT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_TYHF',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_CDSY',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_JJ',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_GJS',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_YBT',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }, {
                dataIndex : 'BJ_CFYW',
                header : '持有率',
                sortable : true
                ,renderer:function(value){
				if(value!=null&&value.length>=10){
					return value.substr(0,3);
				}else return value;
			},renderer : percent(false)
            }];

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
								  url : basepath+'/wdVipCustProSum.json?'
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
                        title : "统计分析->零售报表->客户（持卡）产品持有率统计->网点客户（持卡）产品持有率统计",
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