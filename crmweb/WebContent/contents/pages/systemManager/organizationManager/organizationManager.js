/**
 * 机构管理模块
 * @author lixb
 * @since 2012-9-21
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({//机构树加载属性值
		checkField : 'ASTRUE',
		parentAttr : 'SUPERUNITID',//指向父节点的属性列
		locateAttr : 'UNITID',//机构编号
		rootValue :JsContext._orgId,
		textField : 'UNITNAME',//机构名称
		idProperties : 'UNITID'//主键
	});
	var condition = {searchType:'SUBTREE'};
	var nodeArra;//机构树节点信息
	var filter = false;//过滤条件
	var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({//左边展示的机构树
		id:'orgTreePanel',
		height : document.body.clientHeight,
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
		//单击机构树的节点，获取机构ID赋值给隐藏域,根据ID查询
		clickFn:function(node){
			Ext.getCmp('selectOrgId').setValue(node.id);
			orgPanel.selectForm.buttons[0].handler({'click' : function search(){}});
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
		//选中机构名称，将机构层次自动赋值
		listeners : {
			'click' : function(n) {
				Ext.getCmp("upOrgId").setValue(n.id);
				instnCombo.setValue(n.text);
				for(var i=0;i<loader.nodeArray.length;i++){
					if(loader.nodeArray[i].UNITID==n.id){
						Ext.getCmp('orgLevel').setValue(parseInt(loader.nodeArray[i].LEVELUNIT)+1);
					}
				}
			}
		}
	});
	var instnCombo = new Ext.form.ComboBox({//为新增面板上的机构树添加的下拉框
		xtype : 'combo',
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [ [] ]
		}),
		name : 'comboxOrg',
		id : 'comboxOrg',
		emptyText : '请选择',
		resizable : false,
		labelStyle : 'text-align:right;',
		fieldLabel : '<font color=red>*</font>父节点机构',
		anchor : '99%',
		editable : false,
		allowBlank : false,
		mode : 'local',
		triggerAction : 'all',
		checked : false,
		maxHeight : 390,
		// 下拉框的显示模板,orgTreeDiv作为显示下拉树的容器
		tpl : "<tpl for='.'> <div style='height:400px;'> <div id='orgTreeDiv'></div></div></tpl>",
		onSelect : Ext.emptyFn,
		//当下拉框展开时，comBoxOrgTreeForShow渲染到orgTreeDiv上
		listeners : {
			'expand' : function(combo) {
				comBoxOrgTreeForShow.render('orgTreeDiv');
			}
		}
	});
	var orgPanel = new Mis.Ext.CrudPanel({// 查询条件的展现和查询结果的展示以及数据维护的panel
		region :'center',
		id : "orgPanel",
		stUrl : basepath + '/systemUnit-query.json',//// 查看URL
		addUrl : basepath + '/systemUnit-query.json',// 新增URL
		updateUrl : basepath + '/systemUnit-query.json',// 修改URL
		deUrl : basepath + '/systemUnit-query!batchDestroy.json',// 删除URL
		primary : "id",//主键ID
		checkbox : false,//是否复选框
		seFormHeight : 80,// 定义查询条件Form的高度
		winHeight : 260,// 定义增删详情页面弹出窗口高度
		winWidth : 400,// 宽度
		defaultLoad : false,
		afterSeOneFun : function(a) {// 重载afterSeOneFun方法，加载一条数据后，显示机构名称
		for(var i=0;i<loader.nodeArray.length;i++){
			if(loader.nodeArray[i].UNITID==a.UP_ORG_ID){
				instnCombo.setValue(loader.nodeArray[i].UNITNAME);
			}
		}
		},
		// 查询字段定义，若不定义则不出现查询条件Form
		selectItems : {
			layout : 'column',
			items : [{
				columnWidth : .5,
				layout : 'form',
				defaultType : 'textfield',
				border : false,
				items : [{
					id : 'selectOrgName',
					name : 'ORG_NAME',
					xtype : 'textfield',
					fieldLabel : '机构名称',
					width : '100',
					allowBlank :true,
					labelStyle : 'text-align:right;',
					enableKeyEvents : true,
					listeners:{
						keypress : function(a, b, c) {
							if (b.getKey() == 13) {
								orgPanel.selectForm.buttons[0].handler({'click' : function search(){}});
							}
						}
					},
					anchor : '90%'
				},{
					id : 'selectOrgId',
					name : 'SELECT_ORG_ID',
					xtype : 'textfield',
					fieldLabel : '机构编号',
					hidden : true,
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
			mapping:'ID',
			header : 'Id',
			hidden :true
		}, {
			name : 'upOrgId',
			mapping : 'UP_ORG_ID'
		}, {
			name : 'orgName',
			mapping : 'ORG_NAME',
			header : '机构名称',
			columnWidth:.25
		}, {
			name : 'orgId',
			mapping : 'ORG_ID',
			header : '机构编号',
			columnWidth:.25
		}, {
			name : 'orgLevel',
			mapping : 'ORG_LEVEL',
			header : '层次',
			columnWidth:.25
		}, {
			name : 'parentOrgName',
			mapping : 'PARENT_ORG_NAME',
			header : '上层机构',
			columnWidth:.25
		}, {
			name : 'appId',
			mapping : 'APP_ID'
		}, {
			name : 'accountId',
			mapping : 'ACCOUNT_ID'
		}, {
			name : 'acOrgId',
			mapping : 'AC_ORG_ID'
		}],
		pagesize : 20,// 设置分页每页显示条数，若不设置则不出现分页栏
		// 新增、修改、详情的form的字段
		fclms : [ {
			layout : 'column',
			items : [ {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					id : 'orgId',
					name : 'orgId',
					fieldLabel : '<font color="red">*</font>机构编号<font color="red"></font>',
					xtype : 'textfield',
					allowBlank : false,
					value : '',
					labelStyle : 'text-align:right;',
					anchor : '99%'/*,//前台验证是否重复
					listeners: {
						blur:function(field){
							for(var i=0;i<loader.nodeArray.length;i++){
								if(loader.nodeArray[i].UNITID==field.getValue()){
									alert('提示：机构编号【'+field.getValue()+'】重复,请重新输入！');
									Ext.getCmp("orgId").focus();
									return;
								}
							}
						}
					}*/
				},{
					id : 'orgId_show',
					fieldLabel : '<font color="red">*</font>机构编号<font color="red"></font>',
					xtype : 'textfield',
					hidden : true,
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}]
			}, {
				columnWidth : .9,
				layout : 'form',
				items : [ {
					id : 'orgName',
					name : 'orgName',
					fieldLabel : '<font color="red">*</font>机构名称',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					allowBlank : false,
					value : '',
					anchor : '99%'/*, //前台验证是否重复
					listeners: {
						blur:function(field){
							for(var i=0;i<loader.nodeArray.length;i++){
								if(loader.nodeArray[i].UNITNAME==field.getValue()){
									alert('提示：','机构名称【'+field.getValue()+'】重复,请重新输入！');
									Ext.getCmp("orgName").focus();
									return;
								}
							}
						}
					}*/
				},{
					id : 'orgName_show',
					fieldLabel : '<font color="red">*</font>机构名称',
					xtype : 'textfield',
					hidden :true,
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}]
			}]
		}, {
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '所属机构',
					hidden : true,
					id : 'upOrgId',
					name : 'upOrgId'
				}, instnCombo ]
			},{
				columnWidth : .9,
				layout : 'form',
				items : [{
					id : 'orgLevel',
					name : 'orgLevel',
					fieldLabel : '层次',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					readOnly : true,
					anchor : '99%'
				},{
					id : 'orgLevel_show',
					fieldLabel : '层次',
					xtype : 'textfield',
					hidden :true,
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}]
			},{
				// 特别注意：
				// 必须放置隐藏域的主键
				id : 'id',
				name : 'id',
				xtype : 'textfield',
				fieldLabel : 'ID',
				hidden :true
			}]
		}],
		//删除操作
		removeFun:function(a){
			if (this.grid.selModel.hasSelection()) {
				Ext.MessageBox.confirm('系统提示信息','确定要删除所选的记录吗?',
						function(buttonobj) {
							if (buttonobj == 'yes'&& this.primary) {
								var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
								var selectLength = records.length;// 得到行数组的长度
								var idStr = '';
								var tempId;
								var orgNodeId;
								var orgNodeUnitId;
								var orgNodeName;
								var orgNodeLevel;
								var orgNodeUperId;
								for ( var i = 0; i < selectLength; i++) {
									selectRe = records[i];
									tempId = selectRe.get(this.primary);
									for(var j = 0;j < nodeArra.length; j++){
										if(nodeArra[j].SUPERUNITID==records[i].data.orgId){
											Ext.Msg.alert('提示：',"【"+records[i].data.orgName+"】"+'下有分支机构,不能删除！');
											return;
										}
										if(records[i].data.acOrgId!=''){
											Ext.Msg.alert('提示：',"【"+records[i].data.orgName+"】"+'下有分配的用户,不能删除！');
											return;
										}
										orgNodeId=records[i].data.orgId;
										orgNodeUnitId=records[i].data.orgId;
										orgNodeName=records[i].data.orgName;
										orgNodeLevel=records[i].data.orgLevel;
										orgNodeUperId=records[i].data.upOrgId;
									};
									idStr += tempId;
									if (i != selectLength - 1)
										idStr += ',';
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
									var node = {};
									node.UNITID = orgNodeUnitId;
									node.UNITNAME = orgNodeName;
									node.LEVELUNIT =  orgNodeLevel;
									node.SUPERUNITID =orgNodeUperId;
									orgTreeForShow.deleteNode(node);
								}
								});
							}
				}, this);
			} else {
				Ext.Msg.alert("提示", "请先选择要删除的行!");
			}
		},
		//保存操作
		save : function() {
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
				success : function(response) {
					Ext.Msg.alert('提示', '操作成功');
					this.loadCurrData();
					 var node = {};
					 node.UNITID = Ext.getCmp("orgId").getValue();
					 node.UNITNAME = Ext.getCmp("orgName").getValue(); 
					 node.LEVELUNIT = parseInt(Ext.getCmp("orgLevel").getValue());
					 node.SUPERUNITID = Ext.getCmp("upOrgId").getValue(); 
					 if(Ext.getCmp('id').getValue()==''){
						 orgTreeForShow.addNode(node); 
					 }
					 else
					 {
						 orgTreeForShow.editNode(node);
					 }
					 this.closeWin();
				}
			});
		},
		//修改操作
		edit : function() {
			if (this.grid.selModel.hasSelection()) {
				var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录进行修改！");
				} else {
					var record = this.grid.getSelectionModel().getSelected();
					var id = record.get(this.primary);
					instnCombo.setValue(record.data.parentOrgName);
					this.opUrl = this.updateUrl;
					var winButsArray = [];
					winButsArray.push({text : "保存",handler : this.save, scope : this});
					winButsArray.push({text : "关闭",handler : this.closeWin,scope : this});
		    		this.winButs = winButsArray;
		    		this.showWin();
		    		//机构编号
		    		Ext.getCmp('orgId_show').setValue(records[0].json.ORG_ID);
		    		Ext.getCmp('orgId_show').setVisible(true);
		    		Ext.getCmp('orgId_show').setDisabled(true);
		    		Ext.getCmp('orgId').setVisible(false);
		    		//机构层级
		    		Ext.getCmp('orgLevel_show').setValue(records[0].json.ORG_LEVEL);
		    		Ext.getCmp('orgLevel_show').setDisabled(true);
		    		Ext.getCmp('orgLevel_show').setVisible(true);
		    		Ext.getCmp('orgLevel').setVisible(false);
		    		//机构名称
		    		Ext.getCmp('orgName_show').setValue(records[0].json.ORG_NAME);
		    		Ext.getCmp('orgName_show').setDisabled(true);
		    		Ext.getCmp('orgName').setVisible(true);
		    		Ext.getCmp('orgName_show').setVisible(false);
		    		//父机构名称
		    		Ext.getCmp('comboxOrg').setDisabled(true);
		    		if(this.editFun)
						this.editFun();
		    		if(this.stUrl)
		    			this.seOneRecord(id);
		    		else if(this.demoData)
		    			this.fp.getForm().loadRecord(record);
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要修改的记录!");
			}
		},
		//新增操作
		create : function() {
			this.opUrl = this.addUrl;
		    var winButsArray = [];
		    winButsArray.push({text : "保存",handler : this.save, scope : this});
		    winButsArray.push({text : "清空",handler : this.reset,scope : this});
		    winButsArray.push({text : "关闭",handler : this.closeWin,scope : this});
    		this.winButs = winButsArray;
			this.showWin();
			this.reset();
			if (instnCombo.getValue()!='') {
				instnCombo.setValue('');
			}
			//机构编号
			Ext.getCmp('orgId').setVisible(true);
			Ext.getCmp('orgId_show').setVisible(false);
			//机构层级
			Ext.getCmp('orgLevel_show').setVisible(false);
			Ext.getCmp('orgLevel').setVisible(true);
			//机构名称
			Ext.getCmp('orgName_show').setVisible(false);
			Ext.getCmp('orgName').setVisible(true);
			//父机构名称
			Ext.getCmp('comboxOrg').setDisabled(false);
			if(this.createFun)
				this.createFun();
		},
		//查看详情
		detail : function() {
			if (this.grid.selModel.hasSelection()) {
				var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
				} else {
					var record = this.grid.getSelectionModel().getSelected();
					var id = record.get(this.primary);
					var winButsArray = [];
					instnCombo.setValue(record.data.parentOrgName);
					winButsArray.push({text : "关闭",handler : this.closeWin,scope : this});
		    		this.winButs = winButsArray;
		    		this.showWin();
		    		//机构编号
		    		Ext.getCmp('orgId_show').setValue(records[0].json.ORG_ID);
		    		Ext.getCmp('orgId_show').setVisible(true);
		    		Ext.getCmp('orgId').setVisible(false);
		    		Ext.getCmp('orgId_show').setDisabled(true);
		    		//机构层级
		    		Ext.getCmp('orgLevel_show').setValue(records[0].json.ORG_LEVEL);
		    		Ext.getCmp('orgLevel_show').setDisabled(true);
		    		Ext.getCmp('orgLevel_show').setVisible(true);
		    		Ext.getCmp('orgLevel').setVisible(false);
		    		//机构名称
		    		Ext.getCmp('orgName_show').setValue(records[0].json.ORG_NAME);
		    		Ext.getCmp('orgName_show').setVisible(true);
		    		Ext.getCmp('orgName_show').setDisabled(true);
		    		Ext.getCmp('orgName').setVisible(false);
		    		//父机构名称
		    		Ext.getCmp('comboxOrg').setDisabled(true);
		    		if(this.detailFun)
						this.detailFun();
		    		if(this.stUrl)
		    			this.seOneRecord(id);
		    		else if(this.demoData)
		    			this.fp.getForm().loadRecord(record);
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要查看的记录!");
			}
		}
	});
	
	var view = new Ext.Viewport({// 布局模型 页面展示
		layout : 'fit',
		frame : true ,
		items : [{
			title : '机构管理',
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