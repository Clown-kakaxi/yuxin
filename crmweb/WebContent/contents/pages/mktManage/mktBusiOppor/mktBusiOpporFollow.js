/**
 * 营销管理->商机管理->商机池：跟踪商机表单及窗体定义JS文件；wzy；2013-02-25
 */
// 商机详情窗口From表单
var followBusiOpporForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 200,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '商机ID',
				hidden : true,
				name : 'opporId',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'opporName',
				disabled : true,
				anchor : '90%'
			}, new Ext.form.ComboBox({
				hiddenName : 'opporStat',
				fieldLabel : '商机状态',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceStateStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				disabled : true,
				anchor : '90%'
			}), new Ext.form.ComboBox({
				hiddenName : 'opporSource',
				fieldLabel : '商机来源',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceSourceStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				disabled : true,
				anchor : '90%'
			}), {
				xtype : 'datefield',
				fieldLabel : '商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporEndDate',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'datefield',
				fieldLabel : '商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporDueDate',
				disabled : true,
				anchor : '90%'
			}, new Com.yucheng.crm.common.ProductManage({
				xtype : 'productChoose',
				fieldLabel : '商机产品',
				labelStyle : 'text-align:right;',
				name : 'prodName',
				hiddenName : 'prodId',
				singleSelect : false,
				disabled : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'custContactName',
				disabled : true,
				anchor : '90%'
			}, new Ext.form.ComboBox({
				hiddenName : 'custType',
				fieldLabel : '客户类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceTypeStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				disabled : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '主办客户经理',
				name : 'mainCustManager',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '预计金额(元)',
				name : 'planAmount',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '达成金额(元)',
				name : 'reachAmount',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '创建时间',
				name : 'createDateTime',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '最近更新人',
				name : 'updateUserName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '最近更新机构',
				name : 'updateOrgName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '待分配机构',
				name : 'assignOrgName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '认领机构',
				name : 'claimOrgName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ new Ext.form.ComboBox({
				hiddenName : 'opporType',
				fieldLabel : '商机类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceStatStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				disabled : true,
				anchor : '90%'
			}), new Ext.form.ComboBox({
				hiddenName : 'opporStage',
				fieldLabel : '商机阶段',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceStageStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				editable : false,
				disabled : true,
				anchor : '90%'
			}), {
				xtype : 'datefield',
				fieldLabel : '商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporStartDate',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '营销任务指标',
				name : 'mktTargetName',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '营销活动名称',
				name : 'mktActivName',
				disabled : true,
				anchor : '90%'
			}, new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称',
				labelWidth : 100,
				name : 'custName',
				// custtype :'1',//客户类型： 1：对私, 2:对公, 不设默认全部
				// custStat:'1',//客户状态: 1:正式 2：潜在 , 不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				allowBlank : false,// 不允许为空
				blankText : "不能为空，请填写",
				anchor : '90%',
				hiddenName : 'custId',
				disabled : true,
				callback : function() {
				}
			}), new Ext.form.ComboBox({
				hiddenName : 'custCategoty',
				fieldLabel : '客户类别',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceCategoryStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				disabled : true,
				anchor : '90%'
			}), new Ext.form.ComboBox({
				hiddenName : 'reachProb',
				fieldLabel : '达成概率',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceProbStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				disabled : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '主办机构',
				name : 'mainCustOrgname',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '费用预算（元）',
				name : 'planCost',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '创建人',
				name : 'createrName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '创建机构',
				name : 'createOrgName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '最近更新时间',
				name : 'updateDateTime',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '执行人',
				name : 'executeUserName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '执行机构',
				name : 'executeOrgName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '认领人',
				name : 'claimUserName',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			} ]
		} ]
	}, {
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'opporContent',
			disabled : true,
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'memo',
			disabled : true,
			anchor : '95%'
		} ]
	} ]
});

var detailFollowTap = new Ext.Panel({
	activeTab : 0,
	tabPosition : 'top',// 控制tab页签显示的位置（顶部：top；底部：bottom）
	height : 470,
	items : [ {
		title : '商机详情',
		items : [ followBusiOpporForm ]
	}, {
		title : '商机历史',
		items : [ listPanel_follow ]
	} ]
});

// 定义 商机跟踪 窗口
var followBusiOpporWindow = new Ext.Window({
	title : '商机跟踪',
	plain : true,
	layout : 'fit',
	width : 800,
	height : 470,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	buttonAlign : "center",
	constrain : true,
	items : [ detailFollowTap ],
	buttons : [ {
		text : '关闭',
		handler : function() {
			followBusiOpporWindow.hide();
		}
	} ]
});

// 打开 商机跟踪 窗口
function busiOpportFollowWindowInit() {
	var record = listPanel.getSelectionModel().getSelected();
	if (record == null) {
		Ext.Msg.alert('提示', '请先选择要跟踪的商机！');
		return;
	}
	followBusiOpporForm.getForm().reset();
	followBusiOpporForm.getForm().loadRecord(record);
	followBusiOpporWindow.show();
	// 打开页面就执行查询
	followStore.load({
		params : {
			start : 0,
			condition : '',
			'oppor_id' : followBusiOpporForm.form.findField('opporId')
					.getValue()
		}
	});
}