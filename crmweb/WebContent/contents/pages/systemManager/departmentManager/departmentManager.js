/**
 * 部门管理模块
 * @author changzh
 * @since 2012-11-23
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({//部门树加载属性值
		checkField : 'ASTRUE',
		parentAttr : 'SUPERUNITID',//指向父节点的属性列
		locateAttr : 'UNITID',//部门编号
		rootValue :JsContext._orgId,
		textField : 'UNITNAME',//部门名称
		idProperties : 'UNITID'//主键
	});
	var condition = {searchType:'SUBTREE'};
	var nodeArra;//部门树节点信息
	var filter = false;//过滤条件
	var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({//左边展示的部门树
		id:'orgTreePanel',
		height : 400,
		width : 210,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
		resloader:loader,
		region:'west',
		split:true,
		root: new Ext.tree.AsyncTreeNode({
			id:JsContext._orgId,
			expanded:true,
			text:JsContext._unitname,
			autoScroll:true,
			children:[]
		}),
		//单击部门树的节点，获取部门ID赋值给隐藏域,根据ID查询
		clickFn:function(node){
			Ext.getCmp('selectOrgName').setValue(node.text);
			orgPanel.selectForm.buttons[0].handler({'click' : function search(){}});
		}	
	});
	var orgId = '';
	var getSubOgrs = {searchType:'SUBORGS', orgId: orgId};
	var includeOrgStore = new Ext.data.SimpleStore({
		//fields: ['key','value'],
		//data : []
		autoLoad :false,
		restful:true,	
        proxy : new Ext.data.HttpProxy({
        	url : basepath + '/departmentManagerAction!QueryOrgs.json?condition='+Ext.encode(getSubOgrs)
        }),
       reader: new Ext.data.JsonReader({
    	   root:'json.data'
       }, [{name: 'key', mapping: 'UNITID'},{name: 'value', mapping: 'UNITNAME'}])
	});
	//上级部门
	var departParentStore = new Ext.data.Store({
		autoLoad :true,
		restful:true,	
        proxy : new Ext.data.HttpProxy({
        	url : basepath + '/departmentManagerAction!getDptParents.json'
        }),
        reader: new Ext.data.JsonReader({
    		root:'json.data'
        }, [{name: 'dptId'},{name: 'dptName'}])
	});
	var departmentParentCombo = new Ext.form.ComboBox({//为新增面板上的部门树添加的下拉框
		xtype : 'combo',
		store : departParentStore,
		name : 'dptParentIdCombo',
		id : 'dptParentIdCombo',
		emptyText : '请选择',
		valueField: 'dptId',
		displayField: 'dptName',
		resizable : false,
		labelStyle : 'text-align:right;',
		fieldLabel : '上级部门',
		anchor : '100%',
		typeAhead : true,
		lazyRender:false,
		forceSelection : true,

		editable : false,
		allowBlank : true,
		ComboBox : true,
		mode : 'remote',
		triggerAction : 'all',
		checked : false,
		maxHeight : 390,
		//当下拉框展开时
		listeners : {
			'beforequery' : function(combo) {
				if (Ext.getCmp('dptId').getValue() === null || Ext.getCmp('dptId').getValue() === '') {
					Ext.Msg.alert("系统提示信息", "请先填写部门编号！");
					return false;
				}
				if (Ext.getCmp('belongOrgId').getValue() === null || Ext.getCmp('belongOrgId').getValue() === '') {
					Ext.Msg.alert("系统提示信息", "请先选择所属机构！");
					return false;
				}
				//delete combo.combo.lastQuery;
				
			},'select' : function(combo, record, index) {
				Ext.getCmp('dptParentId').setValue(combo.getValue());
			}
		}
	});
	
	var comBoxOrgTreeForShow = new Com.yucheng.bcrm.TreePanel({//新增面板上的机构下拉树
		id:'comBoxOrgTreePanel',
		height : 400,
		width : 270,
		autoScroll:true,
		checkBox : false, //是否现实复选框
		_hiddens : [],
		resloader:loader,
		split:true,
		root: new Ext.tree.AsyncTreeNode({
			id:JsContext._orgId,
			text:JsContext._unitname,
			expanded:true,
			autoScroll:true,
			children:[]
		}),
		//选中部门名称，将部门层次自动赋值
		listeners : {
			'click' : function(node) {
				node.expand();
				Ext.getCmp('belongOrgId').setValue(node.id);
				belongOrgsCombo.setValue(node.text);
				includeOrgStore.removeAll();
				node.eachChild(function(child) { 
					var newRecord = new Ext.data.Record({key:child.id,value:child.text});
					includeOrgStore.add(newRecord);
				});
				if (includeOrgStore.getCount() === 0) {
					var newRecord = new Ext.data.Record({key:node.id,value:node.text});
					includeOrgStore.add(newRecord);
				}
				Ext.getCmp('includeOrgDisplay').store  = includeOrgStore;
				Ext.getCmp('dptParentIdCombo').clearValue();
				departmentParentCombo.store = new Ext.data.Store({
					autoLoad :false,
					restful:true,	
			        proxy : new Ext.data.HttpProxy({
			        	url :  basepath + '/departmentManagerAction!getDptParents.json?dptId='
						   + Ext.getCmp('dptId').getValue()
						   + '&belongOrgId='+Ext.getCmp('belongOrgId').getValue()
			        }),
			        reader: new Ext.data.JsonReader({
			    		root:'json.data'
			        }, [{name: 'dptId'},{name: 'dptName'}])
				});	
				departmentParentCombo.store.removeAll();
				departmentParentCombo.store.reload();
			}
		}
	});
	
	var belongOrgsCombo = new Ext.form.ComboBox({//为新增面板上的部门树添加的下拉框
		xtype : 'combo',
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [ [] ]
		}),
		name : 'belongOrgIdDisplay',
		id : 'belongOrgIdDisplay',
		emptyText : '请选择',
		resizable : false,
		labelStyle : 'text-align:right;',
		fieldLabel : '<font color=red>*</font>所属机构',
		anchor : '100%',
		editable : false,
		allowBlank : false,
		mode : 'local',
		triggerAction : 'all',
		checked : false,
		maxHeight : 390,
		// 下拉框的显示模板,orgTreeDiv作为显示下拉树的容器
		tpl : "<tpl for='.'> <div style='height:390px'> <div id='orgTreeDiv'></div></div></tpl>",
		onSelect : Ext.emptyFn,
		//当下拉框展开时，comBoxOrgTreeForShow渲染到orgTreeDiv上
		listeners : {
			'expand' : function(combo) {
				comBoxOrgTreeForShow.render('orgTreeDiv');
			},'change' : function(combo) {
				includeOrgsCombo.clearValue();
			},'beforequery' : function() {
				if (Ext.getCmp('dptId').getValue() === null || Ext.getCmp('dptId').getValue() === '') {
					Ext.Msg.alert("系统提示信息", "请先填写部门编号！");
					return false;
				}
			}
		}
	});
	//所辖机构
	var includeOrgsCombo = new Ext.ux.form.LovCombo({
		fieldLabel: '<font color="red">*</font>所辖机构',
		id:'includeOrgDisplay',
		labelStyle : 'text-align:right;',
		displayField:'value',
		valueField:'key',
		width : 500,
		store : includeOrgStore,
		hideOnSelect : false,
		editable : false,
		triggerAction:'all',
		anchor : '100%',
		mode:'local',
		allowBlank:false,
		editable:true,
		listeners : {
			'select' : function(node) {
		 		if (this.getCheckedValue() != '' && this.getCheckedValue() != null)
		 			Ext.getCmp('includeOrgIds').setValue(this.getCheckedValue());
			}
		}
	 });
	
	//业务条线
	var dptTypeStore = new Ext.data.SimpleStore({
		fields: ['EN','CH'],
		data : [['','无'],['01','零售'],['02','对公']]
	});
	
	var orgPanel = new Mis.Ext.CrudPanel({// 查询条件的展现和查询结果的展示以及数据维护的panel
		region :'center',
		id : "orgPanel",
		stUrl : basepath + '/departmentManagerAction.json',//// 查看URL
		addUrl : basepath + '/departmentManagerAction!saveData.json',// 新增URL
		updateUrl : basepath + '/departmentManagerAction!saveData.json',// 修改URL
		deUrl : basepath + '/departmentManagerAction!batchDestroy.json',// 删除URL
		primary : "id",//主键ID
		checkbox : true,//是否复选框
		seFormHeight : 80,// 定义查询条件Form的高度
		winHeight : 260,// 定义增删详情页面弹出窗口高度
		winWidth : 800,// 宽度
		
		// 查询字段定义，若不定义则不出现查询条件Form
		selectItems : {
			layout : 'column',
			items : [{
						columnWidth : .5,
						layout : 'form',
						defaultType : 'textfield',
						border : false,
						items : [{
									name : 'dptName',
									xtype : 'textfield',
									fieldLabel : '部门名称',
									width : '100',
									allowBlank :true,
									labelStyle : 'text-align:right;',
									anchor : '90%'
								}]
						},{
							columnWidth : .5,
							layout : 'form',
							defaultType : 'textfield',
							border : false,
							items : [{
										id : 'selectOrgName',
										xtype : 'textfield',
										fieldLabel : '所属机构名称',
										width : '100',
										allowBlank :true,
										labelStyle : 'text-align:right;',
										anchor : '90%'
									}]
						}]
		},
		// 查询列表字段定义，有header属性则在页面显示
		gclms : [{
			name : 'id',
			mapping : 'ID'
		}, {
			name : 'dptId',
			mapping : 'DPT_ID',
			header : '部门编号',
			width : 75
		}, {
			name : 'dptName',
			mapping : 'DPT_NAME',
			header : '部门名称',
			width : 150
		}, {
			name : 'dptParentId',
			mapping : 'DPT_PARENT_ID',
			//header : '上级部门编号',
			width : 110
		}, {
			name : 'dptParentName',
			mapping : 'DPT_PARENT_NAME',
			header : '上级部门',
			width : 110
		}, {
			name : 'belongOrgId',
			mapping : 'BELONG_ORG_ID',
			header : '所属机构编号',
			width : 80
		}, {
			name : 'orgName',
			mapping : 'ORG_NAME',
			header : '所属机构',
			width : 120
		}, {
			name : 'includeOrgIds',
			mapping : 'INCLUDE_ORG_IDS',
			header : '所辖机构号',
			hidden : true,
			width : 230
		}, {
			name : 'countIncludeOrgs',
			mapping : 'COUNT_INCLUDE_ORGS',
			header : '所辖机构数',
			align : 'right',
			width : 80
		}, {
			name : 'dptType',
			mapping : 'DPT_TYPE',
			header : '业务条线',
			type : 'mapping',
			store : dptTypeStore,
			mappingkey : 'EN',
			mappingvalue : 'CH',
			width : 75
		}, {
			name : 'remark',
			mapping : 'REMARK',
			header : '备注',
			width : 230
		}],
		pagesize : 20,// 设置分页每页显示条数，若不设置则不出现分页栏
		editFun : function () {
			Ext.getCmp('includeOrgDisplay').setValue(Ext.getCmp('includeOrgIds').getValue());
		},
		detailFun : function () {
			//Ext.getCmp('dptIdField').setDisabled(true);
		},
		// 选中一行点击详情或双击，
		detail : function() {
			if (this.grid.selModel.hasSelection()) {
				var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
				} else {
					var id = this.grid.getSelectionModel()
							.getSelected().get(this.primary);
					var winButsArray = [];
					winButsArray.push({text : "关闭",handler : this.closeWin,scope : this});
		    		this.winButs = winButsArray;
		    		var orgName = this.grid.getSelectionModel()
					.getSelected().get('orgName');
		    		Ext.getCmp('belongOrgIdDisplay').setValue(orgName);
		    		var dptParentName = this.grid.getSelectionModel()
					.getSelected().get('dptParentName');
		    		Ext.getCmp('dptParentIdCombo').setValue(dptParentName);
		    		
		    		this.showWin();
		    		if(this.detailFun)
						this.detailFun();
		    		if(this.stUrl)
		    			this.seOneRecord(id);
		    		else if(this.demoData)
		    			this.fp.getForm().loadRecord(record);
		    		//部门编号
		    		Ext.getCmp('dptId_show').setValue(records[0].json.DPT_ID);
		    		Ext.getCmp('dptId_show').setVisible(true);
		    		Ext.getCmp('dptId_show').setDisabled(true);
		    		Ext.getCmp('dptId').setVisible(false);
		    		Ext.getCmp('belongOrgIdDisplay').setDisabled(true);
		    		Ext.getCmp('dptParentIdCombo').setDisabled(true);
		    		Ext.getCmp('includeOrgDisplay').setDisabled(true);
		    		
		    		
		    		var orgId         = this.grid.getSelectionModel().getSelected().get('belongOrgId');
					var includeOrgIds = this.grid.getSelectionModel().getSelected().get('includeOrgIds');
					var dptType         = this.grid.getSelectionModel().getSelected().get('dptType');
					Ext.getCmp('dptTypeShow').setValue(dptType);
					
					Ext.getCmp('orgTreePanel').expandAll();
					includeOrgStore.removeAll();
					var node = Ext.getCmp('orgTreePanel').getNodeById(orgId);
					node.eachChild(function(child) { 
						var newRecord = new Ext.data.Record({key:child.id,value:child.text});
						includeOrgStore.add(newRecord);
					});
					if (includeOrgStore.getCount() === 0) {
						var newRecord = new Ext.data.Record({key:node.id,value:node.text});
						includeOrgStore.add(newRecord);
					}
					Ext.getCmp('includeOrgDisplay').store  = includeOrgStore;
					includeOrgsCombo.setValue(includeOrgIds);
					 
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要查看的记录!");
			}
		},
		// 选中一行点击修改，
		edit : function() {
			if (this.grid.selModel.hasSelection()) {
				var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行修改！");
				} else {
					var record = this.grid.getSelectionModel()
							.getSelected();
					var id = record.get(this.primary);
					this.opUrl = this.updateUrl;
					var winButsArray = [];
					winButsArray.push({text : "保存",handler : this.save, scope : this});
					winButsArray.push({text : "关闭",handler : this.closeWin,scope : this});
		    		this.winButs = winButsArray;
		    		this.showWin();
		    		if(this.editFun)
						this.editFun();
		    		if(this.stUrl)
		    			this.seOneRecord(id);
		    		else if(this.demoData)
		    			this.fp.getForm().loadRecord(record);
		    		
		    		//部门编号
		    		Ext.getCmp('dptId_show').setValue(records[0].json.DPT_ID);
		    		Ext.getCmp('dptId_show').setVisible(true);
		    		Ext.getCmp('dptId_show').setDisabled(true);
		    		Ext.getCmp('dptId').setVisible(false);
		    		
		    		
		    		var orgName = this.grid.getSelectionModel().getSelected().get('orgName');
		    		Ext.getCmp('belongOrgIdDisplay').setValue(orgName);
		    		var dptParentName = this.grid.getSelectionModel().getSelected().get('dptParentName');
		    		Ext.getCmp('dptParentIdCombo').setValue(dptParentName);
		    		
		    		Ext.getCmp('belongOrgIdDisplay').setDisabled(true);
		    		Ext.getCmp('dptParentIdCombo').setDisabled(true);
		    		Ext.getCmp('includeOrgDisplay').setDisabled(false);
		    		
		    		var orgId         = this.grid.getSelectionModel().getSelected().get('belongOrgId');
					var includeOrgIds = this.grid.getSelectionModel().getSelected().get('includeOrgIds');
					var dptType         = this.grid.getSelectionModel().getSelected().get('dptType');
					
					Ext.getCmp('orgTreePanel').expandAll();
					includeOrgStore.removeAll();
					
					Ext.getCmp('dptTypeShow').setValue(dptType);
					var node = Ext.getCmp('orgTreePanel').getNodeById(orgId);
					Ext.getCmp('belongOrgId').setValue(node.id);
					belongOrgsCombo.setValue(node.text);
					node.eachChild(function(child) { 
						var newRecord = new Ext.data.Record({key:child.id,value:child.text});
						includeOrgStore.add(newRecord);
					});
					if (includeOrgStore.getCount() === 0) {
						var newRecord = new Ext.data.Record({key:node.id,value:node.text});
						includeOrgStore.add(newRecord);
					}
					Ext.getCmp('includeOrgDisplay').store  = includeOrgStore;
					includeOrgsCombo.setValue(includeOrgIds);
					 
					
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要修改的记录!");
			}
		},
		afterSeOneFun : function(orgId, includeOrgIds) {
			
		},
		createFun : function() {
			//部门编号
			Ext.getCmp('dptId').setVisible(true);
			Ext.getCmp('dptId_show').setVisible(false);
			Ext.getCmp('belongOrgIdDisplay').setDisabled(false);
			Ext.getCmp('dptParentIdCombo').setDisabled(false);
		},
		// 新增、修改、详情的form的字段
		fclms : [ {
			layout : 'column',
			items : [ {
				columnWidth : .45,
				layout : 'form',
				items : [ {
					id : 'dptId',
					name : 'dptId',
					fieldLabel : '<font color="red">*</font>部门编号',
					xtype : 'textfield',
					allowBlank : false,
					//value : '',
					labelStyle : 'text-align:right;',
					anchor : '100%'
				},{
					id : 'dptId_show',
					name : 'dptId_show',
					fieldLabel : '<font color="red">*</font>部门编号',
					xtype : 'textfield',
					//value : '',
					labelStyle : 'text-align:right;',
					anchor : '100%'
				}]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					id : 'dptName',
					name : 'dptName',
					xtype : 'textfield',
					fieldLabel : '<font color="red">*</font>部门名称',
					//value : '',
					allowBlank : false,
					anchor : '100%',
					labelStyle : 'text-align:right;'
					
				} ]
			}]
		}, {
			layout : 'column',
			items : [{
				columnWidth : .45,
				layout : 'form',
				items : [belongOrgsCombo]
			},{
				columnWidth : .45,
				layout : 'form',
				items : [{
					id : 'dptTypeShow',
					name : 'dptTypeShow',
					xtype : 'combo',
					fieldLabel : '业务条线',
					store: dptTypeStore,
					editable : false,
					//emptyText : '请选择',
					valueField:'EN',
					mode : 'local',
					triggerAction : 'all',
					displayField:'CH',
					anchor : '100%',
					labelStyle : 'text-align:right;',
					listeners : {
						'select' : function () {
							Ext.getCmp('dptType').setValue(this.getValue());
						}
					}
					
				}]
			}]
		},{

			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ departmentParentCombo]
			}]
		},{

			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ includeOrgsCombo]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					id : 'remark',
					name : 'remark',
					xtype : 'textarea',
					labelStyle : 'text-align:right;',
					allowBlank : true,
					anchor : '100%',
					maxLength : 255,
					fieldLabel : '备注'					
				}]
			},{
				// 特别注意：
				// 必须放置隐藏域的主键
				name : 'id',
				xtype : 'hidden' 
			},{
				id : 'appId',
				xtype : 'hidden',
				value : 62
			},{
				id : 'includeOrgIds',
				xtype : 'hidden'				 
			},{
				id : 'belongOrgId',
				xtype : 'hidden'				 
			},{
				id : 'dptParentId',
				xtype : 'hidden'				 
			},{
				id : 'dptType',
				xtype : 'hidden'				 
			}]
		}]
	});
	
	var view = new Ext.Viewport({// 布局模型 页面展示
		layout : 'fit',
		//frame : true ,
		items : [{
			title : '部门管理',
			layout:'border',
			items : [ orgTreeForShow,orgPanel ]
		}]
	});
	//加载机构树的信息
	Ext.Ajax.request({
		url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
		method:'GET',
		success:function(response){
		nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
		loader.nodeArray = nodeArra;
		var children = loader.loadAll();
		Ext.getCmp('orgTreePanel').appendChild(children);
		filter=new Ext.tree.TreeFilter(this.orgTreeForShow,{
			clearBlank:true,
			autoclear:true,
			ignoreFolder:true
		});
		},failure:function(a,b,c){}
	});
});