/**
 * 提醒信息查询
 * luyy 2014-2-19
 */
Ext.onReady(function() {
	
	var typeStore = new Ext.data.Store({
		sortInfo: {
	    	field: 'key',
	    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=REMIND_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	//信息展示window
	var panel =  new Ext.FormPanel({
		frame : true,
		height :'100%',
		items : [{
			xtype:'textarea',
			fieldLabel : '提醒内容',
			name : 'remindRemark',
			labelStyle : 'text-align:right;',
			readOnly : true,
			anchor : '100%'
		}]
	});
	var infoWindo = new Ext.Window({
		title:'提醒内容',
		height : 150,
		width : 400,
		buttonAlign : 'center',
		draggable : true,// 是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		autoScroll : true,
		closeAction : 'hide',
		border : false,
		items : [ panel ],
		buttons : [ {
			text : '关 闭',
			handler : function() {
				infoWindo.hide();
			}
		} ]
	});
	
	
	//查询panel
	var qForm = new Ext.form.FormPanel({
		border : true,
		region : 'north',
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 80,
		width : document.body.scrollWidth,
		items : [ {
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [ {
							xtype : 'combo',
							name : 'RULE_CODE',
							hiddenName : 'RULE_CODE',
							fieldLabel : '提醒类型',
							labelStyle : 'text-align:right;',
							anchor : '100%',
							mode : 'local',
							triggerAction : 'all',
							resizable : true,
							store : typeStore,
							valueField : 'key',
							displayField : 'value'
						} ]
					},
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [ {
							xtype : 'numberfield',
							fieldLabel : '剩余天数',
							decimalPrecision:0,
							labelStyle : 'text-align:right;',
							name : 'LAST_DATE',
							anchor : '100%'
						} ]
					},
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [ {
							fieldLabel : '信息状态',
							hiddenName : 'MSG_STS',
							resizable : true,
							forceSelection : true,
							xtype : 'combo',
							labelStyle : 'text-align:right;',
							triggerAction : 'all',
							mode : 'local',
							store : new Ext.data.ArrayStore({
								fields : [ 'myId',
										'displayText' ],
								data : [ [ '1', '已阅读' ],
										[ '0', '未阅读' ] ]
							}),
							valueField : 'myId',
							displayField : 'displayText',
							emptyText : '请选择',
							anchor : '100%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 100, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [ {
							xtype : 'datefield',
							fieldLabel : '提醒到期日',
							labelStyle : 'text-align:right;',
							name : 'MSG_END_DATE',
							format : 'Y-m-d',
							allowDecimal : false,
							width : 150,
							anchor : '100%',
							editable : false

						} ]
					} ]
		} ],
		buttons : [
				{
					text : '查询',
					handler : function() {
						var conditionStr = qForm.getForm().getFieldValues();
						store.on('beforeload', function() {
							this.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						});
						store.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
					}
				}, {
					text : '重置',
					handler : function() {
						qForm.getForm().reset();
					}
				} ]
	
	});
	
	// 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([
			rownum,
			sm,
			{header : 'INFO_ID',dataIndex : 'infoId',hidden : true},
			{header : '规则类别编号',dataIndex : 'ruleCode',hidden : true,sortable : true,align : 'left',width : 170},
			{header:'提醒类别',dataIndex:'RULE_CODE_ORA',sortable:true,align:'left',width:200},
			{header:'客户ID',dataIndex:'custId',sortable:true,align:'left',hidden : true,width:170},
			{header:'客户名称',dataIndex:'custName',sortable:true,align:'left',width:170},
			{header:'提醒到期日期',dataIndex:'msgEndDate',sortable:true,align:'left',width:120},
			{header:'剩余天数',dataIndex:'lastDate',sortable:true,align:'center',width:80},
			{header:'是否已读',dataIndex:'read',sortable:true,align:'center',width:170,renderer:function(v){
                if(v == '0'){
                    return '未阅';
                }else return '已阅';
            }},
			{header:'提醒内容',dataIndex:'remindRemark',sortable:true,align:'left',hidden : true,width:170},
			{header:'短信内容',dataIndex:'messageRemark',sortable:true,align:'left',hidden : true,width:170},
			{header:'是否已发送短信',dataIndex:'ifMessage',sortable:true,align:'left',hidden : true,width:170},
			{header:'是否已拨打电话',dataIndex:'ifCall',sortable:true,align:'left',hidden : true,width:170},
			{header:'是否已发送短信',dataIndex:'IF_MESSAGE_ORA',sortable:true,align:'left',width:100},
			{header:'是否已拨打电话',dataIndex:'IF_CALL_ORA',sortable:true,align:'left',width:100},
			{header:'telephone_num',dataIndex:'telephoneNum',sortable:true,align:'left',hidden : true,width:100},
			{header:'office_phone',dataIndex:'officePhone',sortable:true,align:'left',hidden : true,width:100},
			{header:'link_phone',dataIndex:'linkPhone',sortable:true,align:'left',hidden : true,width:100}
			]);
	
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy(
				{
					url : basepath + '/remindRead.json',
					method : 'POST',
					failure : function(response) {
						var resultArray = Ext.util.JSON
								.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
				}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			root : 'json.data',
			totalProperty : 'json.count'
		}, [ {name : 'infoId',mapping:'INFO_ID'},
		     {name : 'ruleCode',mapping:'RULE_CODE'},
		     {name : 'read',mapping:'READ'},
		     {name : 'RULE_CODE_ORA'},
		     {name : 'custId',mapping:'CUST_ID'},
		     {name : 'custName',mapping:'CUST_NAME'},
		     {name : 'msgEndDate',mapping:'MSG_END_DATE'},
		     {name : 'lastDate',mapping:'LAST_DATE'},
		     {name : 'remindRemark',mapping:'REMIND_REMARK'},
		     {name : 'messageRemark',mapping:'MESSAGE_REMARK'},
		     {name : 'ifMessage',mapping:'IF_MESSAGE'},
		     {name : 'ifCall',mapping:'IF_CALL'},
		     {name: 'telephoneNum',mapping:'TELEPHONE_NUM'},
		     {name:'officePhone',mapping:'OFFICE_PHONE'},
		     {name:'linkPhone',mapping:'LINK_PHONE'},
		     {name:'custTyp',mapping:'CUST_TYP'},
		     {name:'custZhName',mapping:'CUST_ZH_NAME'},
		     {name : 'IF_MESSAGE_ORA' },
		     {name : 'IF_CALL_ORA' }
		     ])
	});
	
	
	
	var tbar = new Ext.Toolbar({
		items : [ {
			text : '查看详细信息',
			iconCls : 'detailIconCss',
			handler : function() {
				var selectLength = grid.getSelectionModel().getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = grid.getSelectionModel().getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						panel.getForm().loadRecord(infoRecord);
						infoWindo.show();
					}
				}
			}

		}, '-', {
			text : '设为已读',
			iconCls : 'ReadIconCss',
			handler : function() {
				var selectLength = grid.getSelectionModel().getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = grid.getSelectionModel().getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					}else if(infoRecord.data.read!='0'){
						Ext.Msg.alert('提示', '请选择未阅读的数据操作');
					} else {
						Ext.Ajax.request({
							url : basepath+ '/remindRead!read.json',
							params : {
								id:infoRecord.data.infoId
								},
							success : function() {
								Ext.Msg.alert('提示','保存成功');
								store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
							},
							failure : function() {
								Ext.Msg.alert('提示','失败');
							}
						});
					}
				}
				}

		},'-',{
			text:'发送短信',
			iconCls : 'editIconCss',
			handler:function(){
				var selectLength = grid.getSelectionModel().getSelections().length;
				if (selectLength > 1) {
					Ext.Msg.alert('请选择一条记录!');
				} else {
					var infoRecord = grid.getSelectionModel().getSelected();
					if (infoRecord == null || infoRecord == '') {
						Ext.Msg.alert('提示', '请选择一行数据');
					} else {
						//没有短信信息，返回
						if(infoRecord.data.messageRemark==''){
							Ext.Msg.alert('提示', '本信息提醒无相关短信。');
							return ;
						}else if(infoRecord.data.custId==''){//无客户信息
							Ext.Msg.alert('提示', '无相关客户信息。');
							return ;
						}else if(infoRecord.data.cellNumber==''){
							Ext.Msg.alert('提示', '无相关手机信息。');
							return ;
						}else if(infoRecord.data.ifMessage=='1'){//发送过的提示，确认是否在发送
							Ext.MessageBox.confirm('提示','本提醒已经发送短信，是否再次发送?',function(buttonId){
								if(buttonId.toLowerCase() == "no"){
								return;
								} 
								Ext.Ajax.request({
									url : basepath+ '/remindRead!send.json',
									params : {
										id:infoRecord.data.infoId,
										custId:infoRecord.data.custId,
										custName:infoRecord.data.custName,
										number:infoRecord.data.telephoneNum,
										messageRemark:infoRecord.data.messageRemark
										},
									success : function() {
										Ext.Msg.alert('提示','短信已添加到待发送队列，等待发送');
										store.reload({
											params : {
												start : 0,
												limit : bbar.pageSize
											}
										});
									},
									failure : function() {
										Ext.Msg.alert('提示','失败');
									}
								});
							});
						}else{//直接发送
							Ext.Ajax.request({
								url : basepath+ '/remindRead!send.json',
								params : {
									id:infoRecord.data.infoId,
									custId:infoRecord.data.custId,
									custName:infoRecord.data.custName,
									number:infoRecord.data.telephoneNum,
									messageRemark:infoRecord.data.messageRemark
									},
								success : function() {
									Ext.Msg.alert('提示','短信已添加到待发送队列，等待发送');
									store.reload({
										params : {
											start : 0,
											limit : bbar.pageSize
										}
									});
								},
								failure : function() {
									Ext.Msg.alert('提示','失败');
								}
							});
						}
					}
				}
			}
			},'-',{
				text:'致电客户',
				iconCls : 'editIconCss',
				disabled:parent.MType==0?true:false,//根据接入设备控制按钮是否可用
				handler:function(){
					//先判断是否有也联系的方式
					var selectLength = grid.getSelectionModel().getSelections().length;
					if (selectLength > 1) {
						Ext.Msg.alert('请选择一条记录!');
					} else {
						var infoRecord = grid.getSelectionModel().getSelected();
						if (infoRecord == null || infoRecord == '') {
							Ext.Msg.alert('提示', '请选择一行数据');
						} else {
							var data = grid.getSelectionModel().selections.items[0].data;
							if (data.telephoneNum == '' && data.officePhone == ''
								&& data.linkPhone == '') {// 没有电话信息
								Ext.Msg.alert('提示', '本客户没有联系电话信息！');
								return;
							} else {
								//如果已经打过电话，则先进行提示
								if (data.ifCall == '1' ) {
									Ext.MessageBox.confirm('提示','已经针对本提醒致电客户，是否再次致电?',function(buttonId){
										if(buttonId.toLowerCase() == "no"){
										return;
										} else{
											newWindow(data, store);
										}
									});
								}else
									//修改致电状态
									Ext.Ajax.request({
										url : basepath+ '/remindRead!call.json',
										params : {
											id:data.infoId
											},
										success : function() {
											store.reload({
												params : {
													start : 0,
													limit : bbar.pageSize
												}
											});
										},
										failure : function() {
											Ext.Msg.alert('提示','失败');
										}
									});
									newWindow(data, store);
								}
						}
					}
				}
			}]
	});
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
		// editable : false,
		width : 85
	});
	var number = parseInt(pagesize_combo.getValue());
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	// 表格实例
	var grid = new Ext.grid.GridPanel({
		height : document.body.scrollHeight - 107,
		width : document.body.scrollWidth,
		frame : true,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		// forceFit : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	store.reload({
		params : {
			start : 0,
			limit : bbar.pageSize
		}
	});
	
	//页面布局
	new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [ {
			title : '信息提醒查询',
			layout : 'border',
			items : [ qForm, grid ]
		} ]
	});
});