/**
 * 客户管理->决策支持维度方案管理：业务信息定义JS文件；wzy；2013-05-16
 */

// 供应链维度方案---业务信息
var gyl_yw_form = new Ext.FormPanel({
			title : '业务信息',
			frame : true,
			border : false,
			labelAlign : 'right',
			standardSubmit : false,
			layout : 'form',
			width : 850,
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '核心企业名称',
												name : 'f7',
												anchor : '99%',
												value : '河北省石家庄市国宾大酒店'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [new Ext.ux.form.LovCombo({
												fieldLabel : '上游企业交易码',
												name : 'f31',
												displayField : 'key',
												valueField : 'value',
												hideOnSelect : false,
												store : pjplStore,
												triggerAction : 'all',
												mode : 'local',
												anchor : '99%',
												editable : false,
												value : 'AC,BB'
											})]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '上游企业交易方向',
												editable : false,
												emptyText : '请选择',
												name : 'f2',
												mode : 'local',
												anchor : '100%',
												triggerAction : 'all',
												store : fafwStore,
												valueField : 'value',
												displayField : 'key',
												value : 'C'
											}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [new Ext.ux.form.LovCombo({
												fieldLabel : '下游企业交易码',
												name : 'f32',
												displayField : 'key',
												valueField : 'value',
												hideOnSelect : false,
												store : pjplStore,
												triggerAction : 'all',
												mode : 'local',
												anchor : '99%',
												editable : false,
												value : 'CC,FT'
											})]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '下游企业交易方向',
												editable : false,
												emptyText : '请选择',
												name : 'f2',
												mode : 'local',
												anchor : '100%',
												triggerAction : 'all',
												store : fafwStore,
												valueField : 'value',
												displayField : 'key',
												value : 'D'
											}]
								}]
					}]
		});

// 商圈维度方案---业务信息
var sq_yw_form = new Ext.FormPanel({
			title : '业务信息',
			frame : true,
			border : false,
			labelAlign : 'right',
			standardSubmit : false,
			layout : 'form',
			width : 850,
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '商圈名称',
												name : 'f7',
												anchor : '99%',
												value : '理财业务商圈'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '商圈成员行业',
												name : 'f7',
												anchor : '99%',
												value : 'IT业'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '行业逻辑关系符',
												editable : false,
												emptyText : '请选择',
												name : 'NOTICE_LEVEL',
												mode : 'local',
												anchor : '99%',
												triggerAction : 'all',
												store : aoStore,
												valueField : 'value',
												displayField : 'key',
												value : '1'
											}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '商圈成员经营地址',
												name : 'f7',
												anchor : '99%',
												value : '河北省石家庄市桥东区中山东路99号'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '经营地址逻辑关系符',
												editable : false,
												emptyText : '请选择',
												name : 'NOTICE_LEVEL',
												mode : 'local',
												anchor : '99%',
												triggerAction : 'all',
												store : aoStore,
												valueField : 'value',
												displayField : 'key',
												value : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '商圈成员协会',
												editable : false,
												emptyText : '请选择',
												name : 'NOTICE_LEVEL',
												mode : 'local',
												anchor : '99%',
												triggerAction : 'all',
												store : xhStore,
												valueField : 'value',
												displayField : 'key',
												value : '1'
											}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '协会逻辑关系符',
												editable : false,
												emptyText : '请选择',
												name : 'NOTICE_LEVEL',
												mode : 'local',
												anchor : '99%',
												triggerAction : 'all',
												store : aoStore,
												valueField : 'value',
												displayField : 'key',
												value : '1'
											}]
								}]
					}]
		});

// 公私联动维度方案---业务信息
var gsld_yw_form = new Ext.FormPanel({
			title : '业务信息',
			frame : true,
			border : false,
			labelAlign : 'right',
			standardSubmit : false,
			layout : 'form',
			width : 850,
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '零售成员类型',
												editable : false,
												emptyText : '请选择',
												name : 'NOTICE_LEVEL',
												mode : 'local',
												anchor : '99%',
												triggerAction : 'all',
												store : lscyStore,
												valueField : 'value',
												displayField : 'key',
												value : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '零售客户类型',
												editable : false,
												emptyText : '请选择',
												name : 'NOTICE_LEVEL',
												mode : 'local',
												anchor : '99%',
												triggerAction : 'all',
												store : lskhStore,
												valueField : 'value',
												displayField : 'key',
												value : '1'
											}]
								}]
					}]
		});

// 客户生命周期维度方案---业务信息
var khsmzq_yw_form = new Ext.FormPanel({
			title : '业务信息',
			frame : true,
			border : false,
			labelAlign : 'right',
			standardSubmit : false,
			layout : 'form',
			width : 850,
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [new Com.yucheng.crm.common.IndexField(
											{
												xtype : 'userchoose',
												fieldLabel : '潜在阶段属性',
												name : 'searchIndex_mend',
												hiddenName : 'searchIndex_mend',
												labelStyle : 'text-align:right;',
												singleSelect : false,
												anchor : '100%',
												value : '潜在阶段属性',
												callback : function(a, b, c, d) {
												}
											})]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [new Com.yucheng.crm.common.IndexField(
											{
												xtype : 'userchoose',
												fieldLabel : '正式阶段属性',
												name : 'searchIndex_mend',
												hiddenName : 'searchIndex_mend',
												labelStyle : 'text-align:right;',
												singleSelect : false,
												anchor : '100%',
												value : '正式阶段属性',
												callback : function(a, b, c, d) {
												}
											})]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [new Com.yucheng.crm.common.IndexField(
											{
												xtype : 'userchoose',
												fieldLabel : '提升阶段属性',
												name : 'searchIndex_mend',
												hiddenName : 'searchIndex_mend',
												labelStyle : 'text-align:right;',
												singleSelect : false,
												anchor : '100%',
												value : '提升阶段属性',
												callback : function(a, b, c, d) {
												}
											})]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .33,
									layout : 'form',
									items : [new Com.yucheng.crm.common.IndexField(
											{
												xtype : 'userchoose',
												fieldLabel : '流失阶段属性',
												name : 'searchIndex_mend',
												hiddenName : 'searchIndex_mend',
												labelStyle : 'text-align:right;',
												singleSelect : false,
												anchor : '100%',
												value : '流失阶段属性',
												callback : function(a, b, c, d) {
												}
											})]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [new Com.yucheng.crm.common.IndexField(
											{
												xtype : 'userchoose',
												fieldLabel : '挽回阶段属性',
												name : 'searchIndex_mend',
												hiddenName : 'searchIndex_mend',
												labelStyle : 'text-align:right;',
												singleSelect : false,
												anchor : '100%',
												value : '挽回阶段属性',
												callback : function(a, b, c, d) {
												}
											})]
								}]
					}]
		});