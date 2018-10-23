Ext.onReady(function() {
	Ext.QuickTips.init();

	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
		id: "listPanel",
		title: "客户管理->小贷客户管理->自然村维护",
		stUrl: basepath + '/acrmFCiCreditVillageArea!indexPage.json',
		//新增URL，如果不定义则不出现新增按钮
		addUrl: basepath + '/acrmFCiCreditVillageArea.json',
		updateUrl: basepath + '/acrmFCiCreditVillageArea.json',
		deUrl: basepath + '/acrmFCiCreditVillageArea!batchDestroy.json',
		primary: "villaNo",
		checkbox: true, 
		//定义查询条件Form的高度
		seFormHeight: 80,
		//定义增删详情页面弹出窗口高度
		winHeight: 400,
		//宽度
		winWidth: 800,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize: 20,
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems: {
			layout : 'form',
			//labelWidth : 80,
			border : false,
			items : [{
					name: 'villaName',
					xtype: 'textfield',
					fieldLabel: '村名'
				}
			]
		},remove : function(pid) {
			if (this.grid.selModel.hasSelection()) {
				Ext.MessageBox.confirm(
								'系统提示信息',
								'确定要删除所选的记录吗?',
								function(buttonobj) {
									if (buttonobj == 'yes'&& this.primary) {
										var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
										var selectLength = records.length;// 得到行数组的长度
										var idStr = '';
										for ( var i = 0; i < selectLength; i++) {
											selectRe = records[i];
											tempId = selectRe.get(this.primary);
											tempId = "'"+tempId+"'";
											idStr += tempId;
											if (i != selectLength - 1)
												idStr += ",";
										};
										
										Ext.Ajax.request({
													url : this.deUrl,
													params : {
														idStr : idStr
													},
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													method : 'POST',
													scope : this,
													success : function() {
														Ext.Msg.alert('提示', '操作成功');
														this.loadCurrData();
													},
													failure : function() {
														Ext.Msg.alert('提示', '操作失败');
														this.loadCurrData();
													}
												});
									}
								}, this);
			} else {
				Ext.Msg.alert("提示", "请先选择要删除的记录!");
			}
		},
		save : function() {
			if(Ext.getCmp('bercCode').getValue().split(";").length>2){Ext.Msg.alert('提示', '请选择一个机构'); return;}
			if (this.opUrl == null || this.opUrl == '') {
				Ext.Msg.alert('提示', '链接为空');
				return false;
			}
			if (!this.fp.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Ajax.request({
				url : this.opUrl,
				method : 'POST',
				form : this.fp.form.id,
				scope : this,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Msg.alert('提示', '操作成功');
					this.loadCurrData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
					this.loadCurrData();
				}
			});
			this.closeWin();
		},
		//查询列表字段定义，有header属性则在页面显示
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms: [
			{name : 'beanch',header : '管理机构'},
			{name : 'bercCode'},  
			{name : 'villaNo',header : '村编号'}, 
			{name : 'villaName',header : '村名'},
			{name : 'areaNo',header : '片区'},
			{name : 'linkmanName',header : '联系人姓名'},
			{name : 'linkmanPhone',header : '联系人电话'},
			{name : 'odsStDate'}
		],
		// 新增、修改、详情的form的字段
		formColums: function() {
			return new Ext.form.FieldSet({
				title: '自然村维护',
				items: [ util.layout._tr([
				            util.form._td(
				            	new Com.yucheng.bcrm.common.OrgField({
				            		searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				            		fieldLabel : '所属机构',
				            		id : 'beanch', //放大镜组件ID，用于在重置清空时获取句柄
				            		name : 'beanch', 
				            		hiddenName: 'bercCode',   //后台获取的参数名称
				            		anchor : '90%',
				            		checkBox:false //复选标志
				            	})
				            )], [util.form._td({
							fieldLabel: '村编号',
							id:'villaNo',
							xtype:'textfield',
							name: 'villaNo'
						})]),
						util.layout._tr([util.form._td({
							name: 'villaName',
							fieldLabel: '村名',
							id:'villaName',
							xtype: 'textfield'
						})], [util.form._td({
							name: 'areaNo',
							id:'areaNo',
							fieldLabel: '片区',
							xtype: 'textfield'
						})]),
						
						util.layout._tr([util.form._td({
							name: 'linkmanName',
							xtype: 'textfield',
							allowBlank : false,
							fieldLabel: '联系人姓名'
						})], [util.form._td({
							name: 'linkmanPhone',
							xtype: 'textfield',
							allowBlank : false,
							fieldLabel: '联系人电话'
						})]),
						util.layout._tr([util.form._td({name : 'odsStDate',id:'odsStDate',xtype : 'hidden',fieldLabel : 'odsStDate',anchor:'90%'})])
				]
			}
			);
		}
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout: 'fit',
		items: [listPanel]
	});
});