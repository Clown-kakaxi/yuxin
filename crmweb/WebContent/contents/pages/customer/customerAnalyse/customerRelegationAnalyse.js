Ext.onReady(function() {
	var serviceStatStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SERVICE_STAT'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	serviceStatStore.load();
	var serviceKindStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SERVICE_KIND'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	serviceKindStore.load();
	var planChannelStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SERVICE_CHANNEL'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	planChannelStore.load();
	var panel2 = new Ext.FormPanel({ 
		frame:true,
		bodyStyle:'padding:5px 5px 0',
		title : '<span style="font-weight:normal">主动服务新增</span>',
		width: '100%',
	    height:'100%',
		items: [
				{
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : serviceStatStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '服务状态',
							name : 'serviceStat',
							editable : false,
							hiddenName : 'serviceStat',
							value : '01',
							readOnly : true,
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						}, {
							name : 'serviceId',
							xtype : 'hidden'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : serviceKindStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '服务类别',
							editable : false,
							name : 'serviceKind',
							hiddenName : 'serviceKind',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							value : '01',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					} ]
				},
				{
					layout : 'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [ {
									id : 'custId',
									name : 'custId',
									fieldLabel : '客户编号',
									readOnly : true,
									width : 100,
									xtype : 'textfield',
									anchor : '90%'
								} ]
							},
							{
								columnWidth : .5,
								layout : 'form',
								items : [ new Com.yucheng.bcrm.common.CustomerQueryField(
										{
											fieldLabel : '*客户名称',
											labelWidth : 100,
											allowBlank : false,
											blankText : '此项不能为空',
											id : 'CUST_NAMEIDFS',
											name : 'custName',
											custtype : '1',// 客户类型：
															// 1：对私,
															// 2:对公,
															// 不设默认全部
											singleSelected : true,// 单选复选标志
											editable : false,
											anchor : '95%',
											hiddenName : 'CUST_ID',
											callback : function() {
												var cust_name = null;
												cust_name = Ext.getCmp('CUST_NAMEIDFS').getValue();
												if (cust_name != null
														&& cust_name != '') {
													var cust_id = Ext.getCmp('CUST_NAMEIDFS').customerId;
													this.ownerCt.ownerCt.ownerCt.getForm().findField('custId').setValue(cust_id);
												}
											}
										}) ]
							} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ new Com.yucheng.crm.common.ProductManage({ 
							 xtype:'productChoose',
								fieldLabel : '目标营销产品',
						 name : 'productName',
						  hiddenName:'aimProd',
						        singleSelect:false,
						        anchor : '90%'
						       })]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : planChannelStore,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '服务预计渠道',
							editable : false,
							name : 'planChannel',
							hiddenName : 'planChannel',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ new Ext.form.DateField( {
							name : 'pStartDate',
							format : 'Y-m-d',
							editable : false,
							fieldLabel : '*计划开始日期',
							allowBlank : false,
							blankText : '此项不能为空',
							anchor : '90%'
						}) ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							columnWidth : .5,
							layout : 'form',
							items : [ new Ext.form.DateField( {
								name : 'pEndDate',
								format : 'Y-m-d',
								editable : false,
								fieldLabel : '*预计结束日期',
								allowBlank : false,
								blankText : '此项不能为空',
								anchor : '90%'
							}) ]
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							columnWidth : .5,
							layout : 'form',
							items : [ new Ext.form.DateField( {
								name : 'actualDate',
								disabled : true,
								format : 'Y-m-d',
								fieldLabel : '实际服务日期',
								anchor : '90%'
							}) ]
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							store : planChannelStore,
							disabled : true,
							xtype : 'combo',
							resizable : true,
							fieldLabel : '实际接触渠道',
							name : 'cantactChannel',
							hiddenName : 'cantactChannel',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							selectOnFocus : true,
							width : '100',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'serviceCont',
						xtype : 'textarea',
						fieldLabel : '*服务内容',
						allowBlank : false,
						blankText : '此项不能为空',
						maxLength : 1000,
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'needResource',
						xtype : 'textarea',
						fieldLabel : '所需资源',
						maxLength : 1000,
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'serviceResult',
						xtype : 'textarea',
						fieldLabel : '服务结果',
						maxLength : 1000,
						anchor : '90%'
					} ]
				}, {
					layout : 'form',
					items : [ {
						name : 'needEvent',
						xtype : 'textarea',
						fieldLabel : '待跟进事项',
						maxLength : 2000,
						anchor : '90%'
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'hidden',
							fieldLabel : '创建人ID',
							name : 'createUser',
							anchor : '90%'
						},{
							xtype : 'hidden',
							name : 'createOrg',
							anchor : '90%'
						},{
							xtype : 'hidden',
							name : 'pOrC',
							anchor : '90%'
						},  {
							id : 'userName',
							xtype : 'textfield',
							fieldLabel : '创建人',
							readOnly : true,
							name : 'USER_NAME',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'createDate',
							xtype : 'textfield',
							fieldLabel : '创建日期',
							readOnly : true,
							name : 'createDate',
							anchor : '90%'
						} ]
					} ]
				}, {
					layout : 'column',
					items : [ {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'hidden',
							fieldLabel : '最近更新人ID',
							name : 'updateUser',
							anchor : '90%'
						}, {
							id : 'updateUserName',
							xtype : 'textfield',
							fieldLabel : '最近更新人',
							name : 'UPDATEUSER_NAME',
							readOnly : true,
							anchor : '90%'
						} ]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [ {
							id : 'updateDate',
							xtype : 'textfield',
							fieldLabel : '最近更新日期',
							readOnly : true,
							name : 'updateDate',
							anchor : '90%'
						} ]
					} ]
				} ]
		});
	var addRoleWindow = new Ext.Window(
			{
				//layout : 'fit',
		        height :500,
		        width:800,
				buttonAlign : 'center',
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
		        autoScroll:true,
				closeAction : 'hide',
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				border : false,
				animCollapse : true,
				pageY : 20,
				//pageX : document.body.clientWidth / 2 - 420 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [panel2],
			    buttons : [
							{
								text : '保存',
								handler : function() {
//									insert();
								}
							}, {
								text : '重置',
								handler : function() {
//									resetForm(panel2);
								}
							}, {
								text : '关闭',
								handler : function() {
									addRoleWindow.hide();
								}
							} ]
			});
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		region: 'north',
	    title: "客户升降级分析", 
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 120,
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
									xtype:'datefield',
									fieldLabel : '统计起始日期', // 标签
									name : 'e5', // name:后台根据此name属性取值
									allowBlank : true, // 是否允许为空
									labelStyle: 'text-align:right;',
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
									xtype:'datefield',
											fieldLabel : '统计截止日期', // 标签
											id : 'e4',
											name : 'e4', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
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
									fieldLabel : '机构号',
									name : 'e1',
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
								items : []
							}]
				}],
		buttons : [{
					text : '查询'
					/*handler : function() {
						queryBalanceInfo(qForm.getForm());
					}*/
				}, {
					text : '重置'
					/*handler : function() {
						qForm.getForm().reset();
					}*/
				}]
	});
	 /*******************************************************************/
	var fields = [],
	    columns = [],
	    data = [],
	    continentGroupRow = [],
	    
	continentGroupRow = [
	{header: '', colspan: 2, align: 'center'},
	{header: '普通', colspan: 6, align: 'center'},
	{header: '潜力', colspan: 5, align: 'center'},
	{header: '优质', colspan: 4, align: 'center'},
	{header: '高净值', colspan: 3, align: 'center'},
	{header: '私人银行', colspan: 1, align: 'center'}
	];
	    var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });
	    
	    
	      fields =
	      [
	          {name:'a1'},
	{name:'a2'},
	{name:'a3'},
	{name:'a4'},
	{name:'a5'},
	{name:'a6'},
	{name:'a7'},
	{name:'a8'},
	{name:'a9'},
	{name:'a10'},
	{name:'a11'},
	{name:'a12'},
	{name:'a13'},
	{name:'a14'},
	{name:'a15'},
	{name:'a16'},
	{name:'a17'},
	{name:'a18'},
	{name:'a19'},
	{name:'a20'},
	{name:'a21'}
	      ];
	      
	      columns =
	      [
			{dataIndex:'',header:'',sortable:true,width:1},
			{dataIndex:'a2',header:'机构名称',sortable:true,width:100},
			{dataIndex:'a3',header:'客户数',sortable:true},
			{dataIndex:'a4',header:'升为潜力客户数',sortable:true},
			{dataIndex:'a5',header:'升为优质力客户数',sortable:true},
			{dataIndex:'a6',header:'升为高净值客户数',sortable:true},
			{dataIndex:'a7',header:'升为私人银行客户数',sortable:true},
			{dataIndex:'a8',header:'升级率',sortable:true},
			{dataIndex:'a9',header:'客户数',sortable:true},
			{dataIndex:'a10',header:'升为优质力客户数',sortable:true},
			{dataIndex:'a11',header:'升为高净值客户数',sortable:true},
			{dataIndex:'a12',header:'升为私人银行客户数',sortable:true},
			{dataIndex:'a13',header:'升级率',sortable:true},
			{dataIndex:'a14',header:'客户数',sortable:true},
			{dataIndex:'a15',header:'升为高净值客户数',sortable:true},
			{dataIndex:'a16',header:'升为私人银行客户数',sortable:true},
			{dataIndex:'a17',header:'升级率',sortable:true},
			{dataIndex:'a18',header:'客户数',sortable:true},
			{dataIndex:'a19',header:'升为私人银行客户数',sortable:true},
			{dataIndex:'a20',header:'升级率',sortable:true},
			{dataIndex:'a21',header:'客户数',sortable:true}
	      ];
	      data = [
	              ['101','某某银行',30,20,30,40,30,0.75,35,30,40,33,0.6,20,59,40,0.8,34,65,23,0.78],
	              ['102','某某银行1',60,40,30,80,60,0.75,34,65,12,3,0.6,12,559,324,0.8,34,65,23,0.78],
	              ['103','某某银行2',90,60,30,60,45,0.75,23,55,56,54,0.6,32,86,54,0.8,34,65,23,0.74],
	              ['104','某某银行3',15,10,30,20,15,0.75,75,23,23,12,0.6,54,65,65,0.8,34,65,23,0.72],
	              ['105','某某银行4',30,20,30,400,300,0.75,66,43,53,35,0.6,20,12,23,0.8,34,65,23,0.71],
	              ['106','某某银行5',30,20,30,200,150,0.75,87,23,12,8,0.6,20,345,254,0.8,34,65,23,0.71],
	              ['107','某某银行6',30,20,30,40,30,0.75,35,30,40,33,0.6,20,59,40,0.8,34,65,23,0.70],
	              ['108','某某银行7',60,40,30,80,60,0.75,34,65,12,3,0.6,12,559,324,0.8,34,65,23,0.73],
	              ['109','某某银行8',90,60,30,60,45,0.75,23,55,56,54,0.6,32,86,54,0.8,34,65,23,0.68],
	              ['110','某某银行9',15,10,30,20,15,0.75,75,23,23,12,0.6,54,65,65,0.8,34,65,23,0.78],
	              ['111','某某银行10',30,20,30,400,300,0.75,66,43,53,35,0.6,20,12,23,0.8,34,65,23,0.78],
	              ['112','合计',300,200,300,2000,150,0.75,87,23,12,8,0.6,20,345,254,0.8,34,65,23,0.68]/*,
	              ['113','某某银行12',30,20,30,40,30,0.75,35,30,40,33,0.6,20,59,40,0.8,34,65,23,0.78],
	              ['114','某某银行13',60,40,30,80,60,0.75,34,65,12,3,0.6,12,559,324,0.8,34,65,23,0.68],
	              ['115','某某银行14',90,60,30,60,45,0.75,23,55,56,54,0.6,32,86,54,0.8,34,65,23,0.58],
	              ['116','某某银行15',15,10,30,20,15,0.75,75,23,23,12,0.6,54,65,65,0.8,34,65,23,0.68],
	              ['117','某某银行16',30,20,30,400,300,0.75,66,43,53,35,0.6,20,12,23,0.8,34,65,23,0.55],
	              ['118','某某银行17',30,20,30,200,150,0.75,87,23,12,8,0.6,20,345,254,0.8,34,65,23,0.78]*/
	              ];
	  	// 新增商机
	  	function addInit() {
	  		busiOpportAddWindowInit();
	  	}
	    var grid = new Ext.grid.GridPanel({
	    	region:'center',
			tbar : [{
				text : '产品推荐',
				iconCls : 'addIconCss',
//				hidden:true,
				handler : function() {
				addInit();
			}
			},'-',{
				text : '制定服务计划',
//				hidden:true,
				iconCls : 'editIconCss',
				handler : function() {
				addRoleWindow.show();
			}							
			},'-',new Com.yucheng.bob.ExpButton({ //导出按钮
	            formPanel : qForm,
	            iconCls:'exportIconCss',
	            url : basepath + '/AdminLogQuery.json'
			})],
	    	store: new Ext.data.ArrayStore({
	            fields: fields,
	            data: data
	        }),
	        stripeRows:true,
	        columns: columns,
	        viewConfig: {
	            forceFit: true
	        },
	        plugins: group
	    });
	 /*******************************************************************/
	// 布局模型
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
		layout : 'border',
		items: [qForm,grid] 
		}]
});
}); 