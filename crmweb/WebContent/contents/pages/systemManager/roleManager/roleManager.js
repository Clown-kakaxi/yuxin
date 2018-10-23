/**
 * 角色信息查询与维护
 * @author weilh 
 * @since 2012-9-20
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	 var roleIdgrant = '';
	 var roleNamegrant = '';
	 istr = '';
	/**
	 * 角色类型数据源 
	 */
	var roleTypeStore  = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=ROLE_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	/**
	 * 角色级别数据源 
	 */
	var roleLevelStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=ROLE_LEVEL'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var orgTreeLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
		checkField : 'ASTRUE',//选则字段
		parentAttr : 'SUPERUNITID',//指向父节点的属性列
		locateAttr : 'UNITID',//节点定位属性列，也是父属性所指向的列
		rootValue :JsContext._orgId,//虚拟根节点id 若果select的值为root则为根节点
		textField : 'UNITNAME',//用于展示节点名称的属性列
		idProperties : 'ID',//指定节点ID的属性列
		orgLevel : 'ORGLEVEL'//节点点击事件句柄	
	});
	
	var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({
		id:'orgTreePanel',
		height : document.body.clientHeight,
		width : 200,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
		resloader:orgTreeLoader,//加载机构树
		region:'west',//布局位置设置
		split:true,
		root: new Ext.tree.AsyncTreeNode({//设置根节点
			id:JsContext._orgId,
			expanded:true,
			text:JsContext._unitname,
			autoScroll:true,
			children:[]
		}),
		clickFn:function(node){//单击事件，当单击树节点时触发并且获得这个节点的id
			if(node.attributes.id!='root'){
				roleInfoPanel.grid.store.load({
		 			params : {
		 				accountId : node.id
		 			}
		 		});
			}
	 	}
	 });
    
	/**
	 * 下拉框树
	 */
	var instnComboId; // 下拉框机构ID
	var instnComboText;// 下拉框机构名称
	
	var comBoxOrgTreeForShow = new Com.yucheng.bcrm.TreePanel({//定义树形面板
		id:'comBoxOrgTreePanel',
		height : 400,
		width : 230,
		autoScroll:true,
		checkBox : false, //是否现实复选框
		_hiddens : [],
		resloader:orgTreeLoader,
		split:true,
		root: new Ext.tree.AsyncTreeNode({
			id:JsContext._orgId,
			text:JsContext._unitname,
			expanded:true,
			autoScroll:true,
			children:[]
		}),
		listeners : {
			'click' : function(n) {//点击事件，获得这个节点的id
				instnComboId = n.id;
			}
		}
	});
	
	var instnCombo = new Ext.form.ComboBox({//定义下拉框树的下拉框
		xtype : 'combo',
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [ [] ]
		}),
		name : 'accountId',
		id : 'accountId',
		emptyText : '请选择',
		resizable : false,
		labelStyle : 'text-align:right;',
		fieldLabel : '<font color=red>*</font>机构编号',
		anchor : '99%',
		editable : false,
		allowBlank : false,
		mode : 'local',
		triggerAction : 'all',
		checked : false,
		maxHeight : 390,
		// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
		tpl : "<tpl for='.'> <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
		onSelect : Ext.emptyFn,
		listeners : {
			'expand' : function(combo) {//将下拉框树渲染到下拉框中
				comBoxOrgTreeForShow.render('addOrgTreeDivForAdd');
			},
			'collapse' : function(combo) {//设置下拉框取值为选中节点id
				combo.setValue(instnComboId);
			}
		}
	});
	
	var roleDetailPanel = new Ext.FormPanel({//展示详情的formPanel
		reader  :"",
		frame : true,
		autoScroll : true,
		region:'center',
		height : 250,
		width : 400,
		items:[{
			layout:'column',
			items:[{
				columnWidth : .9,
				layout : 'form',
				labelWidth : 100,
				items:[{
					name:'id',
					xtype:'displayfield',
					fieldLabel:'id',
					hidden:true,
					labelStyle:'text-align:right;',
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'accountId',
					fieldLabel:'机构名称',
					labelStyle:'text-align:right;',
					anchor:'99%'

				}]
			},{
				columnWidth : .9,
				layout : 'form',
				labelWidth : 100,
				items:[{
					xtype:'displayfield',
					name:'roleCode',
					fieldLabel:'角色编号',
					labelStyle:'text-align:right;',
					anchor:'99%'
				}]
			},{
				columnWidth : .9,
				layout : 'form',
				labelWidth : 100,
				items:[{
					xtype:'displayfield',
					name:'roleName',
					fieldLabel:'角色名称',
					labelStyle:'text-align:right;',
					anchor:'99%'
				}]
			},{
				columnWidth : .9,
				layout : 'form',
				labelWidth : 100,
				items:[{
					xtype:'displayfield',
					name:'roleTypeName',
					fieldLabel:'角色类型',
					labelStyle:'text-align:right;',
					anchor:'99%'
				}]
			},{
				columnWidth : .9,
				layout : 'form',
				labelWidth : 100,
				items:[{
					xtype:'displayfield',
					name:'roleLevelName',
					fieldLabel:'角色级别',
					labelStyle:'text-align:right;',
					anchor:'99%'
				}]
			}]
		}]
	});
	var roleInfoPanel = new Mis.Ext.CrudPanel({//最终展现的panel
		id : "roleInfoPanel",
		region : 'center',
		stUrl : basepath + '/roleManagerQuery.json',// 请求展示数据的Url
		addUrl : basepath + '/roleManagerQuery.json',// 新增Url，如果不定义则不出现新增按钮
		updateUrl : basepath + '/roleManagerQuery.json',// 修改请求Url，如果不定义则不出现修改按钮
		deUrl : basepath + '/roleManagerQuery!batchDestroy.json',// 删除请求Url，如果不定义则不出现删除按钮
		buts : [{//定义一个授权用户查看按钮
			text : '复制角色',
			iconCls : 'resetIconCss',
//			id : 'buts',
			handler : function() {
				if (roleInfoPanel.grid.selModel.hasSelection()) {//判断是否选择记录
					var records = roleInfoPanel.grid.selModel.getSelections();// 得到被选择的行的数组
					var selectLength = records.length;// 得到行数组的长度
					if(selectLength>1){//判断是否只选择一条记录
						Ext.Msg.alert('提示信息','请选择一条记录复制角色！');  
					}
					else{
						copyToNewRoleForm.getForm().reset();
						Ext.getCmp('copyRoleWin').setTitle("复制角色: "+ records[0].data.roleName);
						_roleCodeGloble = records[0].data.id;
						// 去掉复制源角色
						_roleStore.on('load',function(a,b,c,d){
							var dataArr = _roleStore.data.items;
							for ( var i = 0; i < dataArr.length; i++) {
								if(dataArr[i].data.id == _roleCodeGloble)
								{
									_roleStore.remove(dataArr[i]);
								}
							}
						});
						_roleStore.load();
						copyRoleWin.show();
						
						copyRoleWin.on('hide',function(){
							roleInfoPanel.grid.store.load();
						});
					}
				}else{
					Ext.Msg.alert('提示信息','请选择一条记录复制角色！');  
				}
			}},
			'-',{//定义一个授权用户查看按钮
			text : '授权用户查看',
			iconCls : 'detailIconCss',
			id : 'buts',
			handler : function() {
				if (roleInfoPanel.grid.selModel.hasSelection()) {//判断是否选择记录
					var records = roleInfoPanel.grid.selModel.getSelections();// 得到被选择的行的数组
					var selectLength = records.length;// 得到行数组的长度
					if(selectLength>1){//判断是否只选择一条记录
						Ext.Msg.alert('提示信息','请选择一条记录查看！');  
					}
					else{
						roleAccountInfoWindow.show();//显示授权用户窗口
						selectRe = records[0];
						roleIdgrant = selectRe.data.id;//获得选中记录的id
						roleNamegrant = selectRe.data.roleName;
						if(roleIdgrant!=''){//条件判断，如果已经选择角色名称，进行授权用户查看，将roleId作为参数传递到后台
							accountInfoPanel.grid.store.load({
					 			params : {
								roleId : roleIdgrant
					 			}
					 		});
						}
					}
				}else{
					Ext.Msg.alert('提示信息','请选择一条记录查看！');  
				}
			}
		}],
		primary : "id",
		checkbox : true,
		seFormHeight : 80,// 定义查询条件Form的高度
		winHeight : 260,// 定义新增、修改、详情弹出窗口高度
		winWidth : 400,// 定义新增、修改、详情弹出窗口宽度
		pagesize : 20,// 设置分页每页显示条数，若不设置则不出现分页栏
		selectItems : {// 查询字段定义，若不定义则不出现查询条件Form
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .25,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						name : 'ROLE_NAME',
						fieldLabel : '角色名称',
						labelStyle : 'text-align:right;',
						anchor : '99%'
					}]
				}, {
					columnWidth : .25,
					layout : 'form',
					items : [{
						xtype : 'combo',
						name : 'ROLE_TYPE',
						hiddenName : 'ROLE_TYPE',
						fieldLabel : '角色类型',
						store : roleTypeStore,
						forceSelection : true,
						resizable : true,
						labelStyle : 'text-align:right;',
						triggerAction : 'all',
						mode : 'local',
						valueField : 'key',
						displayField : 'value',
						anchor : '99%'
					}]
				}]
		},
		gclms : [{//查询列表字段定义，有header属性则在页面显示
			name : 'id', 
			mapping : 'ID',
			header : 'Id',
			hidden : true
		},{
			name : 'roleId', 
			mapping : 'ROLE_ID'
		},{
			header : '角色编码',
			name : 'roleCode',
			mapping : 'ROLE_CODE',
			columnWidth: .25
		}, {
			header : '角色名称',
			name : 'roleName',
			mapping : 'ROLE_NAME',
			columnWidth: .25
		}, {
			name : 'roleType',
			mapping : 'ROLE_TYPE',
			sortable : true
		}, {
			header : '角色类型',
			name : 'roleTypeName',
			mapping : 'ROLE_TYPE_ORA',
			columnWidth: .25
		}, {
			name : 'roleLevel',
			mapping : 'ROLE_LEVEL',
			sortable : true
		}, {
			header : '角色级别',
			name : 'roleLevelName',
			mapping : 'ROLE_LEVEL_ORA',
			columnWidth: .25
		}, {
			name : 'appId',
			mapping : 'APP_ID',
			sortable : true
		}, {
			name : 'accountId',
			mapping : 'ACCOUNT_ID',
			sortable : true
		}],
		fclms : [{// 新增、修改、详情的form的字段
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [ instnCombo ]
			},{
				columnWidth : .9,
				layout : 'form',
				items : [{
					id : 'roleCode',
					name : 'roleCode',
					fieldLabel : '<font color="red">*</font>角色编号',
					xtype : 'textfield',
					allowBlank : false,
					value : '',
					labelStyle : 'text-align:right;',
					anchor : '99%',
					listeners: {}
				}]
			},{
				columnWidth : .9,
				layout : 'form',
				items : [{
					id:'roleName',
					name : 'roleName',
					fieldLabel : '<font color="red">*</font>角色名称',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					allowBlank : false,
					value : '',
					anchor : '99%',
					listeners: {}
				}]
			}]
		},{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					xtype : 'combo',
					name : 'roleType',
					id : 'qRoleType',
					hiddenName :'roleType',
					fieldLabel : '<font color="red">*</font>角色类型',
					forceSelection : true,
					resizable : true,
					labelStyle : 'text-align:right;',
					triggerAction : 'all',
					mode : 'local',
					store : roleTypeStore,
					valueField : 'key',
					displayField : 'value',
					anchor : '99%'
				}]
			},{
				columnWidth : .9,
				layout : 'form',
				items : [{
					xtype : 'combo',
					name : 'roleLevel',
					id : 'qRoleLevel',
					hiddenName :'roleLevel',
					fieldLabel : '<font color="red">*</font>角色级别',
					forceSelection : true,
					resizable : true,
					labelStyle : 'text-align:right;',
					triggerAction : 'all',
					mode : 'local',
					store : roleLevelStore,
					valueField : 'key',
					displayField : 'value',
					anchor : '99%'
				}]
			},{// 特别注意：必须放置隐藏域的主键
				name : 'id',
				xtype : 'hidden'
			}]
		}],
		detail : function() {
			if (this.grid.selModel.hasSelection()) {
				var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
				} else {

				var	record_1 = this.grid.getSelectionModel().getSelected();
					var roleDetailWind = new Ext.Window({
						title:'查看角色详情',
						closeAction:'hide',
						closable:true,
						constrain:true,
						modal:true,
						maximizable:true,
						height:250,
						width:400,
						buttonAlign:'center',
						items:[roleDetailPanel],
						buttons : [{
						        	   text:'关闭',
						        	   handler:function(){
											roleDetailPanel.getForm().reset();
											roleDetailWind.hide();
						           }
						           }]
					});
					roleDetailPanel.getForm().reader = this.reader;//设定查看详情的formPanel的reader
					roleDetailPanel.getForm().loadRecord(record_1);//加载当前选择的数据
					roleDetailWind.show();    //窗口展示
					var OrArra = orgTreeLoader.nodeArray;//取左边机构树上的数据
					var orgIds = roleDetailPanel.getForm().findField('accountId').getValue();//取机构ID的值
						for(var i = 0;i<OrArra.length;i++){//将机构ID进行映射，展示机构名称
							if(orgIds == OrArra[i].UNITID){
								roleDetailPanel.getForm().findField('accountId').setValue(OrArra[i].UNITNAME);
								return;
							}
						}
				}
			} else {
				Ext.Msg.alert("提示", "请先选择要查看的记录!");
			}
		},
		removeFun:function(a){//重写commonExtPanel.js中的函数
			if (this.grid.selModel.hasSelection()) {//删除角色function，根据角色是否已分配用户判断是否可删除，已分配用户的角色不能删除
				Ext.MessageBox.confirm('系统提示信息','确定要删除所选的记录吗?',
					function(buttonobj) {
						if (buttonobj == 'yes'&& this.primary) {
							var records = this.grid.selModel.getSelections();// 得到被选择的行的数组
							var selectLength = records.length;// 得到行数组的长度
							var idStr = '';//用于存储将删除角色的id
							var tempId;
							for ( var i = 0; i < selectLength; i++) {
								selectRe = records[i];
								tempId = selectRe.get(this.primary);
								if(selectRe.data.id==selectRe.data.roleId){//判断角色是否可删除，已分配客户的角色不可删除
									Ext.Msg.alert('提示：',records[i].data.roleName+'已分配客户，不能删除！');
									return;
								}
								idStr += tempId;
								if (i != selectLength - 1){
									idStr += ',';
								}
							};	
							Ext.Ajax.request({
								url : this.deUrl,//删除请求url
								params : {
									idStr : idStr//传递参数，将删除角色id
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								method : 'POST',
								scope : this,
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									this.loadCurrData();//重新加载数据
								}
							});
						}
					}, 
					this
				);
			} else {
				Ext.Msg.alert("提示", "请先选择要删除的行!");
			}
		}
	});	
	
	var roleGrantPanel = new Ext.form.FormPanel({
		id : 'roleGrantPanel',
		//title : '新增授权',
	    frame:true,
        bodyStyle:'padding:5px 5px 0',
        width: 450,
      	height:350,
      	autoScroll : true,
		split:true,
		buttonAlign : 'center',
		items : [{
			layout : 'column',
			items : [{
				columnWidth : .9,
				layout : 'form',
				defaultType : 'textfield',
				items : [{
					fieldLabel : '所授权的角色',
					id : 'roleAccount',
					name : 'roleName',
					xtype : 'textfield',
					labelStyle : 'text-align:right;',
					anchor : '90%'
				},new Com.yucheng.crm.common.OrgUserManage({
					fieldLabel : '所选择的用户',
					id : 'AccountRole',
					labelStyle: 'text-align:right;',
					name:'userName',
					hiddenName:'accountId',
					searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:false,
					allowBlank : true,
					anchor : '90%',
					callback:function(a){
					istr = '';
					var tempid = '';
					for ( var i = 0; i < a.length; i++) {
						seletId = a[i].data.id;
						tempid = seletId;
						istr += tempid;
						if (i != a.length - 1){
							istr += ',';
						}
					};	
				}
				})
				]
			}]
		}],
		buttons : [{
			id : 'save',
			text : '保存',
			handler : function(){
			if(!roleGrantPanel.getForm().isValid()){ //输入项检查
				Ext.MessageBox.alert('提示','输入有误或存在漏输项,请检查！');
				return false;
			}
			var str = istr.split(","); 
			for(i=0;i<str.length;i++){
				for(j=1;j<str.length-1;j++){
					if(str[i]== str[j]){
						Ext.MessageBox.alert('提示','您所选择的用户重复,请检查！');
						break;
					}
					break;
				}
			}
			Ext.Ajax.request( {
				url : basepath + '/roleAccountGrant-action!create.json',
				method : 'POST',
				params : {
					'roleId' : roleIdgrant,
					'accountIds':istr
			},
				success : checkResult,
				failure: checkResult
			});
			function checkResult(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
					Ext.Msg.alert('提示', '操作成功');
					roleGrantPanel.getForm().reset();
					roleAccountGrantWindow.hide();
					accountInfoPanel.grid.store.load({
			 			params : {
			 				roleId : roleIdgrant
			 			}
			 		});
				} else {
					if(resultArray == 403){
						Ext.Msg.alert('提示', response.responseText);
					}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
					}
				};
			}	
		}
			
			
		}]
	});
	/**
	 * 授权用户列表
	 */
	var accountInfoPanel = new Mis.Ext.CrudPanel({//最终展现的panel
		id : "accountInfoPanel",
		frame : true,
		gridHeight:400,
		gridWidth : 700,
		winHeight : 280,
		winWidth : 400,
		layout : 'fit',
		grid : false,
		stUrl : basepath + '/roleWarrantUserInfoQuery.json',// 请求展示数据的Url
		primary : "id",
		checkbox : true,
		pagesize : 20,// 设置分页每页显示条数，若不设置则不出现分页栏
		gclms : [{//查询列表字段定义，有header属性则在页面显示
			header : '用户ID',
			name : 'accountName', 
			mapping : 'ACCOUNT_NAME',
			width : 150
		},{
			header : '主键ID',
			name : 'id',
			mapping : 'ID',
			width : 150,
			hidden : true
			},{
			header : '用户姓名',
			name : 'userName', 
			mapping : 'USER_NAME',
			width : 200
		},{
			header : '机构ID',
			name : 'orgId',
			mapping : 'ORG_ID',
			width : 150
		}],
		buts : [{
			text : '新增授权',
			iconCls : 'addIconCss',
			handler : function(){
			roleAccountGrantWindow.show();
			Ext.getCmp('roleAccount').setValue(roleNamegrant);
		}
		},'-',{
			text :'取消授权',
			iconCls : 'deleteIconCss',
			handler : function(){
			if (accountInfoPanel.grid.selModel.hasSelection()) {//取消授权
				Ext.MessageBox.confirm('系统提示信息','确定要对所选的用户取消授权么?',
					function(buttonobj) {
						if (buttonobj == 'yes') {
							var records = accountInfoPanel.grid.selModel.getSelections();// 得到被选择的行的数组
							var selectLength = records.length;// 得到行数组的长度
							var idStr = '';//用于存储将删除角色的id
							var tempId;
							for ( var i = 0; i < selectLength; i++) {
								selectRe = records[i].data.id;
								tempId = selectRe;
								idStr += tempId;
								if (i != selectLength - 1){
									idStr += ',';
								}
							};	
							Ext.Ajax.request({
								url :basepath+'/roleAccountGrant-action!batchDestroy.json',//删除请求url
								params : {
									idStr : idStr//传递参数，将删除角色id
								},
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								method : 'POST',
								scope : this,
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									accountInfoPanel.grid.store.load({
							 			params : {
							 				roleId : roleIdgrant
							 			}
							 		});
								}
							});
						}
					}, 
					accountInfoPanel
				);
			} else {
				Ext.Msg.alert("提示", "请先选择要取消授权的用户的行!");
			}
		}
		}],

		viewConfig: {
			forceFit: true   //自动延展每列的长度
		}
		
	});

	var roleAccountGrantWindow = new Ext.Window({
		title : '新增授权',
		plain : true,
		width : 450,
		heigth : 350,
		resizable : false,
		draggable : true,
		closeable : true, 
		closeAction : 'hide',
		modal : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		border : false,
		items : [roleGrantPanel]
	});
	/**
	 * 定义授权用户信息窗口
	 */ 
	var roleAccountInfoWindow = new Ext.Window({
		title : '授权用户信息',
		plain : true,
		width : 760,
		height : 450,
		resizable : false,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ accountInfoPanel ]
	});

	/**
	 * 布局模型
	 */ 
	var view = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [{
			title : '系统管理->系统管理新版->角色管理',
			layout:'border',
			items : [ orgTreeForShow,roleInfoPanel ]
		}]
	});
	
	/**
	 * 机构树数据请求
	 */
	var condition = {searchType:'SUBTREE'};
 	var nodeArra;
	var filter = false;
	Ext.Ajax.request({//请求机构树数据
		url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
		method:'GET',
		success:function(response){
			nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			orgTreeLoader.nodeArray = nodeArra;
			var children = orgTreeLoader.loadAll();
			Ext.getCmp('orgTreePanel').appendChild(children);
            filter=new Ext.tree.TreeFilter(this.orgTreeForShow,{
	    		clearBlank:true,
	    		autoclear:true,
	    		ignoreFolder:true
	    	});
		},failure:function(a,b,c){}
	});
});