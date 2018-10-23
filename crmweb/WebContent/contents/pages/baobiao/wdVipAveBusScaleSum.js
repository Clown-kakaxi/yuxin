Ext
        .onReady(function() {
        	var boxstore = new Ext.data.Store({  
        		sortInfo: {
        	    	field: 'key',
        	    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
        		},
        		restful:true,   
        		autoLoad :true,
        		proxy : new Ext.data.HttpProxy({
        			url :basepath+'/lookup.json?name=P_VIP_CUST_LEV'
        		}),
        		reader : new Ext.data.JsonReader({
        			root : 'JSON'
        		}, [ 'key', 'value' ])
        	});
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
	                            fieldLabel : '统计日期开始', // 标签
	                            id : 'MONTH1',
	                            format : 'Y-m',
	                            editable : false,
	                            name : 'MONTH1', // name:后台根据此name属性取值
	                            allowBlank : true, // 是否允许为空
	                            labelStyle : 'text-align:right;',
	                            // maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
	                            anchor : '80%' // 宽度百分比
	                }]
					},{
						
							columnWidth : .25,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							wdefaultType : 'textfield',
							border : false,
							items :[ {
		                            xtype : 'datefield',
		                            fieldLabel : '统计日期结束', // 标签
		                            id : 'MONTH2',
		                            format : 'Y-m',
		                            editable : false,
		                            name : 'MONTH2', // name:后台根据此name属性取值
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
						items :
						new Ext.form.ComboBox({
							hiddenName : 'CUR_G',
							fieldLabel : '客户等级',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							name:'CUR_GRADE',
							id:'CUR_GRADE',
							store :boxstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
						})},
//						{
//
//							columnWidth : .25,
//							layout : 'form',
//							labelWidth : 70, // 标签宽度
//							defaultType : 'textfield',
//							border : false,
//							items :
//							new Ext.form.ComboBox({
//								hiddenName : 'CUST_TYP',
//								fieldLabel : '业务品种',
//								labelStyle: 'text-align:right;',
//								triggerAction : 'all',
//								name:'CUST_TYPE',
//								id:'CUST_TYPE',
//								store : boxstore,
//								displayField : 'value',
//								valueField : 'key',
//								mode : 'local',
//								forceSelection : true,
//								typeAhead : true,
//								emptyText:'请选择',
//								resizable : true,
//								anchor : '90%'
//							})
//						},
						{


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
			
		                
						}
						],
    		buttons : [{
				text : '查询',
				handler : function() {
					
    			var month1=Ext.getCmp("MONTH1").getValue();
				
				var month2=Ext.getCmp("MONTH2").getValue();
				if(  month1!='' && month1 !=null && month2!='' && month2 !=null &&  month1>=month2)
					{
						alert("开始日期不能大于或等于结束日期");
						return;
						
					}
					else {
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
			
			   }}},{
    					text : '重置',
    					     handler : function() {
    					    	 qForm.getForm().reset();
    					    	 Ext.getCmp('ORG_NAME2').setValue('');
    						}
    					}]
    		});
   		 var store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy({url:basepath+'/wdVipAveBusScaleSum.json'}),
		        reader: new Ext.data.JsonReader({
		        	totalProperty : 'json.count',
		        	root:'json.data'
		        }, [
					{name: 'MONTH',mapping :'MONTH'},
					{name: 'ORG_NAME2',mapping :'ORG_NAME2'},
					{name: 'ORG_NAME4',mapping :'ORG_NAME4'},
					{name: 'CUR_GRADE',mapping :'CUR_GRADE'},
					{name: 'CUR_GRADE_ORA'},
					{name: 'CUST_NUM',mapping :'CUST_NUM'},
					//{name: 'BUSI_TYPE',mapping :'BUSI_TYPE'},
					{name: 'BUSI_SCALE',mapping :'BUSI_SCALE'},
					{name: 'BUSI_SCALE_YEAR_START',mapping :'BUSI_SCALE_YEAR_START'},
					{name: 'COMP_PRE_MONTH',mapping :'COMP_PRE_MONTH'},
					{name: 'COMP_PRE_MONTH_SCOPE',mapping :'COMP_PRE_MONTH_SCOPE'},
					{name: 'COMP_CUR_YEAR',mapping :'COMP_CUR_YEAR'},
					{name: 'COMP_CUR_YEAR_SCOPE',mapping :'COMP_CUR_YEAR_SCOPE'},
					{name: 'COMP_PRE_YEAR',mapping :'COMP_PRE_YEAR'},
					{name: 'COMP_PRE_YEAR_SCOPE',mapping :'COMP_PRE_YEAR_SCOPE'},
					{name: 'COMP_PRE_QUARTER',mapping :'COMP_PRE_QUARTER'},
					{name: 'COMP_PRE_QUARTER_SCOPE',mapping :'COMP_PRE_QUARTER_SCOPE'}
					
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
   		                                {header : '日期',dataIndex : 'MONTH',width : 80,sortable : true},
   		                                    {header : '中心支行名称',dataIndex : 'ORG_NAME2',width : 80,sortable : true},
   		                                 {header : '网点名称',dataIndex : 'ORG_NAME4',width : 80,sortable : true},
   		                                    //{header : '业务品种',dataIndex : 'zh_ORG',width : 150,sortable : true},
   		                                    {header : '客户等级',dataIndex : 'CUR_GRADE_ORA',width : 80,sortable : true},
   		                               		 {header : '客户数',dataIndex : 'CUST_NUM',width : 100,sortable : true},
   		                               	    {header : '业务规模',dataIndex : 'BUSI_SCALE',sortable : true,width : 150,align:'right;',renderer: money('0,000.00')},
   		                       			    {header : '较上月增量',dataIndex : 'COMP_PRE_MONTH',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')},
   		                    			    {header : '较上月增幅',dataIndex : 'COMP_PRE_MONTH_SCOPE',width : 80,sortable : true},
   		                    			     {header : '较年初增量',dataIndex : 'COMP_CUR_YEAR',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')},
    		                       			{header : '较年初增幅',dataIndex : 'COMP_CUR_YEAR_SCOPE',width : 80,sortable : true},
    		                       			{header : '较上年同期增量',dataIndex : 'COMP_PRE_YEAR',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')},
    		                       			{header : '较上年同期增幅',dataIndex : 'COMP_PRE_YEAR_SCOPE',width : 80,sortable : true},
    		                       			{header : '较季度初增量',dataIndex : 'COMP_PRE_QUARTER',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')},
    		                       			{header : '较季度初增幅',dataIndex : 'COMP_PRE_QUARTER_SCOPE',width : 80,sortable : true},
    		                       			{header : '年初基数',dataIndex : 'BUSI_SCALE_YEAR_START',width : 100,sortable : true,align:'right;',renderer: money('0,000.00')}
   		                       			    
   		                       	           
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
										  url : basepath+'/wdVipAveBusScaleSum.json?'
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
                        title : "统计分析->零售报表->客户日均业务规模统计->网点客户日均业务规模汇总",
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