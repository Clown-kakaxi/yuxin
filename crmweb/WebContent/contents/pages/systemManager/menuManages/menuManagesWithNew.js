/**
 * @constructor 当点击右侧展示的图标的时候在panel上显示相应的图标
 * @param id: id是指静态数组
 * @return ;
 */
function setIcon(id){
	Ext.ComponentMgr.all.map.icon.setValue(Ext.getCmp('icon').setValue(id));
	Ext.getCmp('icon').setValue(Ext.util.Format.substr(id,9,100));
	var tImp = document.createElement('img');
	tImp.src = basepath+id;
	if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
		Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild
		(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
	}
	Ext.ComponentMgr.all.map.icon.el.dom.parentNode.appendChild(tImp);
} 
function setMainIcon(id){
	Ext.getCmp('icon').setValue(id);
}
/**
 * 菜单项管理（EXT版本）主界面
 * @author songxs
 * @since 2012-9-23
 */
Ext.onReady(function(){
	
	var addUdapte = '0';//用来区分是‘新增’还是‘修改’操作的标志，‘0’表示新增，‘1’表示修改
	
	var asStore = new Ext.data.ArrayStore({//是否新窗口打开的码值定义
		fields:['myId','displayText'],
		data:[['0','否'],['1','是']]
	});
	
	var store = new Ext.data.ArrayStore({//右侧图片选择时展示所用的store
		proxy : new Ext.data.MemoryProxy(),
		fields : ['id'],
		sortInfo: {
			field : 'id',
			direction: 'ASC'
		}
	});
	
	var mainStore = new Ext.data.ArrayStore({//右侧图片选择时展示所用的store
		proxy : new Ext.data.MemoryProxy(),
		fields : ['id','backGroundP'],
		sortInfo: {
			field : 'id',
			direction: 'ASC'
		}
	});
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({//左边树加载数据
		parentAttr : 'PARENT_ID', 	 //指向父节点的属性列
		locateAttr : 'ID',       	 //节点定位属性列，也是父属性所指向的列
		rootValue : "0",          	 //虚拟根节点id 若果select的值为root则为根节点
		textField : 'NAME',			 //用于展示节点名称的属性列
		idProperties : 'ID'          //指定节点ID的属性列
	});

	var loader1 = new Com.yucheng.bcrm.ArrayTreeLoader({//功能模块选择的树加载数据
		parentAttr : 'PARENT_ID',	//指向父节点的属性列
		locateAttr : 'ID',			//节点定位属性列，也是父属性所指向的列
		rootValue : "0",			//虚拟根节点id 若果select的值为root则为根节点
		textField : 'NAME',			//用于展示节点名称的属性列
		idProperties : 'ID'			//指定节点ID的属性列
	});	
		
	var rightTreeForShows = new Com.yucheng.bcrm.TreePanel({
		title : '模块功能选择',
		width:200,
		autoScroll:true,
		ddGroup: 'gridDDGroup',
		enableDD  : true,
		animate : false,
		useArrows : false,
		border : false,
		root: new Ext.tree.AsyncTreeNode({//虚拟树形根节点
			id:'root',
			text:'模块功能菜单',
			autoScroll:true,
			expanded:true,
			leaf:false,
			children:[]
		}),
		resloader: loader1,
		split:true,
		
		clickFn:function(node){//点击事件，当点击该节点的时候先判断是否节点，若不是则获取该模块功能的ID和Name给菜单的模块功能赋值
			if(node.attributes.ACTION == ''){
				Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
				return;
			}else{
				Ext.getCmp('funcName').setValue(node.attributes.NAME);
				Ext.getCmp('modFuncId').setValue(node.attributes.ID);
			}
		}
	});
	
	var dataView = new Ext.DataView({//右侧图标展示的样式(包括图标的排版)
		store: store,
		id: 'iconss',
		tpl  : new Ext.XTemplate(
				'<ul>',
				'<tpl for=".">',
				'<li class="icon" onclick="setIcon(\'{[values.id]}\')" style="float:left;margin-left:12px;margin-right:12px;margin-top:10px;">',
				'<img src= "'+basepath+'/{[values.id]}" />',
				'</li>',
				'</tpl>',
				'</ul>'
		),
		itemSelector: 'li.icon',
		overClass   : 'phone-hover',
		singleSelect: true,
		autoScroll  : true
	});
	var dataViewMain = new Ext.DataView({//右侧图标展示的样式(包括图标的排版)
		store: mainStore,
		id: 'iconss_1',
		tpl  : new Ext.XTemplate(
				'<ul>',
				'<tpl for=".">',
				'<a href="#" onclick="setMainIcon(\'{[values.id]}\')" style="display:block;float:left;margin-left:12px;margin-right:12px;margin-top:10px;	padding:62px 0px 0px 0px;background:url('+basepath+'/contents/images/blue/navbg.gif) no-repeat;background-position:-567px 0px;width:81px;height:22px;background-position:{[values.backGroundP]};"></a>',
				//'<br>',
				//'<img src= "{[values.id]}" />',
				'</tpl>',
				'</ul>'

		),
		overClass   : 'phone-hover',
		singleSelect: true,
		autoScroll  : true
	});
	
	
	var rightMenuTreeShow = new Com.yucheng.bcrm.TreePanel({
		title:'上层菜单选择',
		ddGroup: 'gridDDGroups',
		enableDD  : true,
		width:200,
		autoScroll:true,
		layout:'fit',
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			text:'主菜单',
			autoScroll:true,
			expanded:true,
			leaf:false,
			children:[]
		}),
		resloader: loader,
		split:true,
		clickFn:function(node){//点击事件，当点击右侧‘上层菜单选择’树的时候，将把所选择的节点的ID和TEXT值赋给所要创建或修改的节点，作为该节点的父节点ID和父节点名称
			Ext.getCmp('parentName').setValue(node.text);
			Ext.getCmp('parentId').setValue(node.id);
		},
		animate : false,
		useArrows : false,
		border : false
	});
	
	var iconView = new Ext.Panel({//右侧图标展示的Panel
		title: '图标',
		id :'iconView',
		ddGroup  : 'gridDDGroups1',
		enableDD  : true,
		layout: 'fit',
		items : dataView,
		height: 700,
		width: 300
	});
	
	var mainIconView = new Ext.Panel({
		title: '一级菜单图标',
		id :'mainIconView',
		ddGroup  : 'gridDDGroups2',
		enableDD  : true,
		layout: 'fit',
		items : dataViewMain,
		height: 700,
		width: 300
	});
	var accordion = new Ext.Panel({//用于页签展示的Panel
		region:'east',
		margins:'5 10 5 0',
		split:true,
		width: 280,
		layout:'accordion',
		items: [ rightTreeForShows,rightMenuTreeShow,mainIconView,iconView]
	});

	/**
	 * 
	 */
	function dropTargetEl(){		//模块功能选择的拖拽

		var formPanelDropTargetEl =  addMenu.getForm().findField('funcName').getEl().dom;
		var formPanelDropTarget = new Ext.dd.DropTarget(formPanelDropTargetEl, {
			ddGroup : 'gridDDGroup',
			notifyEnter : function(ddSource, e, data) {
				addMenu.body.stopFx();
				addMenu.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
			},
			notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
				var selectedRecord = ddSource.dragData.node;//拖拽的那条记录
				if(selectedRecord.attributes.ACTION == ''){
					Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
					return;
				}else{
					Ext.getCmp('modFuncId').setValue((selectedRecord.attributes.ID));
					Ext.getCmp('funcName').setValue((selectedRecord.attributes.NAME));
					Ext.getCmp('parentName').focus();
					return(true);
			
				}
			}
			
		});
		
		var formPanelDropTargetE2 = addMenu.getForm().findField('parentName').getEl().dom;
		
		var formPanelDropTarget = new Ext.dd.DropTarget(formPanelDropTargetE2, {//上层菜单选择的拖拽

			ddGroup : 'gridDDGroups',
			notifyEnter : function(ddSource, e, data) {
				addMenu.body.stopFx();
				addMenu.body.highlight();
			},
			notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的上层菜单赋值
				var selectedRecord = ddSource.dragData.node;
				Ext.getCmp('parentId').setValue((selectedRecord.attributes.id));
				Ext.getCmp('parentName').setValue((selectedRecord.attributes.NAME));
				Ext.getCmp('icon').focus();
				return(true);
			}
		});	
	}

	var tbar = new Ext.Toolbar({
		items : [{
			id : 'add',
			disabled : true,
			iconCls : 'addIconCss',
			text : '新增',
			handler:function(){//点击新增按钮的时候所触发的事件
				addUpdate = '0';
				sel = leftTreeForShow.getSelectionModel().getSelectedNode();
				addMenu.getForm().reset();
				Ext.getCmp('save').setDisabled(false);
				Ext.getCmp('cancel').setDisabled(false);
				if(sel.attributes.id == 'root'){
					Ext.getCmp('parentId').setValue('0');
					Ext.getCmp('parentName').setValue(sel.attributes.text);
				}else{
				Ext.getCmp('parentId').setValue(sel.attributes.ID);
				Ext.getCmp('parentName').setValue(sel.attributes.NAME);}
				Ext.getCmp('appId').setValue(JsContext._appId);
				Ext.getCmp('crtDate').setValue(new Date());
				Ext.getCmp('_issamewin').setValue('0');
				Ext.getCmp('funcName').getValue();
				if(sel.attributes.ICON != undefined ){//新增时移除显示的图标
					if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
						Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild(
								Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
					}		
				}
				dropTargetEl();
			}
		},'-',{
			id : 'delete',
			disabled :true,
			iconCls : 'deleteIconCss',
			text :'删除',
			handler:function(){//点击删除按钮的时候所触发的事件
				var record = leftTreeForShow.getSelectionModel().getSelectedNode();
				var sel =  leftTreeForShow.root.findChild('id', record.id, true);
				Ext.Msg.confirm(
 						'请确认',
 						'<b>提示!:</b><span  style="color:red" >删除该菜单项的同时将删掉其子菜单,请慎重! </span> <br/>继续删除吗?',
 						function(btn, text) {
 							if (btn == 'yes') {//当确认删除时，获取该节点及其节点下的所有子节点的ID值
 								var res = [];
 								if (sel.childNodes.length == 0 && sel.attributes.children == undefined){
 									res.push(sel.id);
 								} else {
 									var stack = function(node){
 										res.push(node.id);
 										if(node.attributes == undefined){
 											if(node.children == undefined){
 												return;
 											}else{
 												if(node.children.length == 0  ){
 													return;
 												}else{
 													Ext.each(node.children,function(cn){
 														stack(cn);
 													});
 												}
 											}
 										} else {
 										/*	if(node.attributes.PARENT_ID == "0"){
 												Ext.each(node.childNodes,function(cn){
 													stack(cn);
 												});
 												debugger;
 											}
 											else if(node.attributes.children.length==0 && node.childNodes.length == 0){
 												return;
 											}else {
 												Ext.each(node.attributes.children,function(cn){
 													stack(cn);
 												});
 											}*/
 											if(node.childNodes.length != 0){
 												Ext.each(node.childNodes,function(cn){
 													stack(cn);
 												});
 											}else if(node.attributes.children == undefined){
 												return;
 											}else if(node.attributes.children.length == 0){
 												return;
 											}
 										}
 									};
 								
 									stack(sel);
 								}
 								Ext.Ajax.request({
 									url : basepath + '/CntMenu-action!batchDestroy.json',
 									method : 'POST',
 									params : {
 										'idStr' : Ext.encode(res)
 									},
 									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
 									scope : this,
 									success : function() {
 										if(record.attributes.PARENT_ID == '0'){
 											leftTreeForShow.root.removeChild(record,true);
 										}else{
 										leftTreeForShow.deleteNode(record);}
 										addMenu.getForm().reset();
 										Ext.getCmp('save').setDisabled(true);
 										Ext.getCmp('cancel').setDisabled(true);
 										Ext.getCmp('add').setDisabled(true);//新增按钮显示
 										Ext.getCmp('delete').setDisabled(true);//删除按钮显示
 										if(Ext.getCmp('icon').getValue() != undefined){//新增时移除显示的图标
 											if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
 												Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild(
 														Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
 											}	
 										}
 									},	
 									failure : function() {
 										Ext.Msg.alert('提示', '操作失败');
 									}
 								});
 							}
 					});
			}
		}]
	});


	var addMenu=new Ext.form.FormPanel({//中间显示的面板
		id:'addMenu',
		title : '菜单设置',
		frame:true,
		region:'north',
		bodyStyle:'padding:5px 5px 0',
		width: '80%',
		height:380,
		tbar :tbar,
		autoScroll : true,
		split:true,
		items: [{
				layout:'column',
				items:[{
					columnWidth:.80,
					layout: 'form',
					items: [{
						id:'id',
						xtype:'textfield',
						fieldLabel: '当前用户ID',
						name: 'id',
						hidden : true,
						anchor:'95%'
					},{
						id:'name',
						name: 'name',
						xtype:'textfield',
						fieldLabel: '名称',
						allowBlank: false,
						anchor:'95%'
					},{
						id:'icon',
						xtype:'textfield',
						fieldLabel: '图标',
						readOnly:true,
						name: 'icon',
						anchor:'95%',
						listeners:{//监控事件，当鼠标点击到该field的时候自动打开右侧相应的页签
							focus:function(c){
							var iconShow = addMenu.getForm().findField('parentId').getValue();
							if(iconShow == '0'){
								accordion.layout.setActiveItem(mainIconView);
							}else{
							accordion.layout.setActiveItem(iconView);
							}
							}
						}
					},{
						id:'order',
						name:'order',
						xtype:'numberfield',
						fieldLabel: '排序',
						anchor:'95%'
					},{
						fieldLabel: '新窗口打开',
						xtype : 'combo',
						editable : false,
						id : '_issamewin',
						name : 'issamewin',
						mode : 'local',
						triggerAction:'all',
						store:asStore,
						valueField:'myId',
						displayField:'displayText',
						anchor:'95%'
					},{
						id:'modFuncId',
						name:'modFuncId',
						xtype:'textfield',
						fieldLabel: '功能模块选择',
						hidden:true,
						anchor:'95%'
					},
					{	id:'funcName',
						name:'funcName',
						fieldLabel: '功能模块选择',
						hideTrigger:false,
						readOnly : true,
						anchor:'81%',
						xtype : 'textfield',
						listeners:{//监控事件，当鼠标点击到该field的时候自动打开右侧相应的页签
							focus:function(a){
								accordion.layout.setActiveItem(rightTreeForShows);
								
							},
							render: function(obj) {
								var button=document.createElement("button");
								var redStar=document.createTextNode('清空');
								 button.setAttribute("className","x-btn");//IE设置class的方法    
								 button.setAttribute("backgroundColor","blue");//IE设置class的方法    
								 button.appendChild(redStar);
								 obj.el.dom.parentNode.appendChild(button);
								 button.onclick = (function(a){
									 Ext.getCmp('funcName').setValue('');
									 Ext.getCmp('modFuncId').setValue('');
								 });
								}
						}
					}
						
					,{
						id:'parentId',
						name:'parentId',
						xtype:'textfield',
						readOnly:true,
						fieldLabel: '上层菜单选择',
						anchor:'95%',
						hidden : true
					},{
						id:'parentName',
						name:'parentName',
						xtype:'textfield',
						readOnly:true,
						allowBlank : false,
						fieldLabel: '上层菜单选择',
						anchor:'95%',
						listeners:{//监控事件，当鼠标点击到该field的时候自动打开右侧相应的页签
							focus:function(b){
								accordion.layout.setActiveItem(rightMenuTreeShow);
							}
						}
					},{
						id : 'appId',
						name : 'appId',
						xtype : 'textfield',
						fieldLabel : '逻辑ID',
						anchor :'95%',
						hidden : true
					},{
						id : 'crtDate',
						name : 'crtDate',
						xtype : 'datefield',
						format : 'Y-m-d',
						fieldLabel : '日期',
						readOnly : true,
						hidden : true,
						anchor : '95%'
					},{
						id : 'leafFlag',
						name : 'leafFlag',
						xtype : 'textfield',
						fieldLabel : '是否是叶子节点',
						hidden : true,
						anchor : '95%'
					}]
				}]
			
		}],
		buttonAlign:'center',
		buttons:[{
			id : 'save',
			text:'保存',
			disabled : true,
			handler: function(){
				if(!addMenu.getForm().isValid()){ //输入项检查
					Ext.MessageBox.alert('提示','输入有误或存在漏输项,请检查！');
					return false;
				}
				var lf = Ext.getCmp('funcName').getValue();
				if(lf != ''){
					Ext.getCmp('leafFlag').setValue('1');
				}else{
					Ext.getCmp('leafFlag').setValue('0');

				}
				if(addUpdate == '1'){
			          //点击修改按钮的时候所触发的事件
					var record = leftTreeForShow.getSelectionModel().getSelectedNode();//获取当前所选的记录
					if(record.id == 'root'){
						Ext.MessageBox.alert('提示', '根节点不能修改 !');
					}
					
					
					if(Ext.getCmp("parentId").getValue()==Ext.getCmp('id').getValue()){
						Ext.Msg.alert('提示', '当前菜单不能做为上层菜单!否则会出现未知错误');
						return false;
					}else{
						if(!addMenu.getForm().isValid()){ 
							Ext.MessageBox.alert('提示','输入有误,请检查输入项');
							return false;
						}
						dropTargetEl();
					}
				
			}
				//新增成功后将新添加的节点显示到该树上
				Ext.Ajax.request({
					url : basepath + '/CntMenu-action.json',
					method : 'POST',
					params : addMenu.getForm().getFieldValues(),
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					scope : this,
					success : function() {
						Ext.Ajax.request({
							url: basepath +'/CntMenu-action!getPid.json',
							success:function(response){
								var node = {};
								function putNode() {
									node.NAME = Ext.getCmp("name").getValue();
									node.PARENT_ID = Ext.getCmp("parentId").getValue();
									node.ICON = Ext.getCmp('icon').getValue();
									debugger;
									if(node.PARENT_ID == '0' && node.ICON != ""){
										node.icon = "";
										
									}else if(node.ICON != "" && node.PARENT_ID == ""){
										node.icon = basepath+'/'+'contents'+Ext.getCmp('icon').getValue();
									}
									node.ORDER_ = Ext.getCmp('order').getValue();
									node.ISSAMEWIN = Ext.getCmp('_issamewin').getValue();
									node.MOD_FUNC_ID = Ext.getCmp('modFuncId').getValue();
									node.FUNC_NAME = Ext.getCmp('funcName').getValue();
									node.APP_ID = Ext.getCmp('appId').getValue();
									node.CRT_DATE = Ext.getCmp('crtDate').getValue();
									node.PARENT_NAME = Ext.getCmp('parentName').getValue();
								};
								if (addUpdate == '0'){//表示新增
									node.ID = Ext.util.JSON.decode(response.responseText).pid;
									putNode();
									if(node.PARENT_ID == '0'){
										node.id = node.ID;
										node.text = node.NAME;
										node.children=[];
										leftTreeForShow.root.appendChild(node);
										rightMenuTreeShow.root.appendChild(node);
										leftTreeForShow.getNodeById(node.id).expand();
									}else{
										leftTreeForShow.addNode(node);
										rightMenuTreeShow.addNode(node);}
								} else if (addUpdate == '1'){//表示修改
									putNode();
									node.ID = Ext.getCmp("id").getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).ui.getIconEl().src = basepath+'/'+'contents'+node.ICON;//修改节点的图片显示
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.icon = basepath+Ext.getCmp('icon').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.ICON = Ext.getCmp('icon').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.PARENT_ID =Ext.getCmp("parentId").getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.ORDER_ =  Ext.getCmp('order').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.ISSAMEWIN = Ext.getCmp('_issamewin').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.MOD_FUNC_ID = Ext.getCmp('modFuncId').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.FUNC_NAME = Ext.getCmp('funcName').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.APP_ID = Ext.getCmp('appId').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.CRT_DATE = Ext.getCmp('crtDate').getValue();
									leftTreeForShow.root.findChild('id', node.ID, true).attributes.PARENT_NAME = Ext.getCmp('parentName').getValue();
									if(node.PARENT_ID == '0'){
									leftTreeForShow.root.findChild('id', node.ID, true).setText(Ext.getCmp("name").getValue());}
								//	node.text = Ext.getCmp("name").getValue();
									debugger;
									leftTreeForShow.editNode(node);
									rightMenuTreeShow.editNode(node);
								}
							}
						});	
						Ext.Msg.alert('提示', '操作成功');
						Ext.getCmp('save').setDisabled(true);
						Ext.getCmp('cancel').setDisabled(true);
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
		},'-',{
			id : 'cancel',
			text:'重置',
			disabled : true,
			handler:function(){//点击重置按钮的时候，面板清空，所有按钮置灰，移除显示的图标
				addMenu.getForm().reset();
				Ext.getCmp('add').setDisabled(true);//新增按钮隐藏
				Ext.getCmp('delete').setDisabled(true);//删除按钮 隐藏
				Ext.getCmp('save').setDisabled(true);
				Ext.getCmp('cancel').setDisabled(true);
				if(Ext.getCmp('icon').getValue() != undefined){//取消后移除显示的图标
					if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
						Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild(
								Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
					}	
				}
			}
		}]
	});

	var recordStore = Ext.data.Record.create([
	                                          {name: 'id' ,mapping : 'ID'},
	                                          {name: 'name' , mapping : 'NAME'},
	                                          {name: 'icon' , mapping : 'ICON'},
	                                          {name:'order' , mapping : 'ORDER_'},
	                                          {name:'issamewin' , mapping : 'ISSAMEWIN'},
	                                          {name:'modFuncId' , mapping : 'MOD_FUNC_ID'},                                        
	                                          {name:'parentId' , mapping : 'PARENT_ID'},
	                                          {name:'funcName' , mapping : 'FUNC_NAME'},
	                                          {name:'parentName' , mapping : 'PARENT_NAME'},
	                                          {name:'appId',mapping : 'APP_ID'},
	                                          {name:'crtDate',mapping : 'CRT_DATE'},
	                                          {name:'leafFlag',mapping : 'LEAF_FLAG'}
	                                          ]);	
			
	var menuManageStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/CntMenu-action!indexAll.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
			root:''
		},recordStore)
	});	

	var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({	//左边的tree的形状

		title:'菜单维护',
		id:'blocMemberTree',
		width:300,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'主菜单',
			autoScroll:true,
			children:[]
		}),
		resloader:loader,
		region:'west',
		split:true,
		clickFn:function(node){//点击事件，当点击该树的某一节点后，所有按钮变为可用，在Panel--addMenu上展示该节点的相应的值
			Ext.getCmp('add').setDisabled(false);//新增按钮显示
			Ext.getCmp('delete').setDisabled(false);//删除按钮显示
			Ext.getCmp('save').setDisabled(true);
			Ext.getCmp('cancel').setDisabled(true);
			Ext.getCmp('id').setValue((node.attributes.ID));
			Ext.getCmp('name').setValue((node.text));
			
			if(node.attributes.icon != undefined  ){
				var test2 = Ext.util.Format.substr(node.attributes.icon,0,1);//验证图片路径是否规范，若不规范则只显示值，不显示图标
				if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
					Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild(
							Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
				}
				if(test2 == '/'){
				var tImp = document.createElement('img');
				tImp.src = node.attributes.icon;
				Ext.ComponentMgr.all.map.icon.el.dom.parentNode.appendChild(tImp);}
				Ext.getCmp('icon').setValue((node.attributes.ICON));

			}else {
				if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
					Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild(
							Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
				}
				Ext.getCmp('icon').setValue(node.attributes.icon);
			}
			
			Ext.getCmp('order').setValue((node.attributes.ORDER_));
			Ext.getCmp('_issamewin').setValue((node.attributes.ISSAMEWIN));
			Ext.getCmp('modFuncId').setValue((node.attributes.MOD_FUNC_ID));
			Ext.getCmp('funcName').setValue((node.attributes.FUNC_NAME));
			Ext.getCmp('appId').setValue((node.attributes.APP_ID));
			Ext.getCmp('crtDate').setValue((node.attributes.CRT_DATE));

			if(node.attributes.PARENT_ID == '0'){	
				Ext.getCmp('parentId').setValue((node.attributes.PARENT_ID));
				Ext.getCmp('parentName').setValue(('主菜单 '));
			}else{
				Ext.getCmp('parentId').setValue((node.attributes.PARENT_ID));
				Ext.getCmp('parentName').setValue((node.attributes.PARENT_NAME));}
			
			addUpdate = '1';//代表进行修改操作
			Ext.getCmp('save').setDisabled(false);
			Ext.getCmp('cancel').setDisabled(false);
			dropTargetEl();
			}
	});	  

	var mainView = new Ext.Viewport({
		layout:'border',
		items:[leftTreeForShow,{
			region:'center',
			layout:'fit',
			items:[{
				layout:'border',
				split:true,
				height: 25055,
				minSize: 100,
				maxSize: 300,
				items:[{
					id:'center-panel',
					region:'center',
					layout:'fit',
					items:[addMenu]
				}]
			}]
		},accordion]
	});
	
	Ext.Ajax.request({//左侧菜单项树的Ajax请求事件
		url : basepath + '/menuInit.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			Ext.each(nodeArra.json.data,function(n){
				if(n.ICON){
					var test;
					test = Ext.util.Format.substr(n.ICON,0,1);
					if(test=='/')
					{n.icon = basepath+'/'+'contents'+n.ICON;//对从台读取图标数据进行处理，变成绝对路径，那样才能显示图标
					}else{
						n.icon = '';
					}
				}
			});
			loader.nodeArray = nodeArra.json.data;
			var children = loader.loadAll();
			leftTreeForShow.appendChild(children);
			rightMenuTreeShow.appendChild(children);
		}
	});
			
	Ext.Ajax.request({//右侧模块功能树的Ajax请求事件
		url : basepath + '/fwFunction-Action.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			loader1.nodeArray = nodeArra;
			var children = loader1.loadAll();
			rightTreeForShows.appendChild(children);
		}
	});
			
	store.loadData (iconData);//右侧图标展示时加载静态数据
	mainStore.loadData (iconData_1);//右侧图标展示时加载静态数据

});