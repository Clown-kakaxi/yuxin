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
		title : '营销管理->事件式营销->事件式营销',
		border : true,
		region : 'north',
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 100,
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
			text : '创建商机',
			iconCls:'addIconCss',
			handler : function() {
        	busiOpportAddWindowInit();
			}
		},{
            text : '生成营销活动',
            iconCls:'addIconCss',
            handler : function() {
            	
			addActivityForm.form.reset();
			addActivityProdForm.form.reset();
			addActivityCustForm.form.reset();
			addActivityForm.form.findField('createUser').setValue(__userId);
			addActivityForm.form.findField('test').setValue(__userName);
			addActivityForm.form.findField('createDate').setValue(new Date());
			addActivityForm.form.findField('mktActiStat').setValue(1);
			addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
			addActivityForm.form.findField('mktActiType').setValue('推广活动');
			addActivityForm.form.findField('mktActiMode').setValue('宣传');
			addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
			addActivityForm.form.findField('mktActiCost').setValue('1000');
			addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
			addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
			addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
			addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
			addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
			addActivityForm.form.findField('mktActiAim').setValue('推广');
			addActivityForm.form.findField('actiRemark').setValue('无');
					 				
			addActivityWindow.show();

            }}]
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
			layout : 'border',
			items : [ qForm, grid ]
		} ]
	});
});