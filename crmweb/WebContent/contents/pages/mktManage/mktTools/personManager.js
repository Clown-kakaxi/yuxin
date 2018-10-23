Ext.onReady(function() {
	Ext.QuickTips.init();
	// 性别 下拉框的数据查询

	var sexStore = new Ext.data.Store({
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
							url : basepath + '/lookup.json?name=sex'
						}),
				reader : new Ext.data.JsonReader({
							root : 'JSON'
						}, ['key', 'value'])
			});
	sexStore.load();
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel({
				id : "listPanel",
				title : "人员界面",
				// seBaseForm ：true,
				stUrl : basepath + '/person-info!indexPage.json',
				// demoData :
				// {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
				// 新增URL，如果不定义则不出现新增按钮
				addUrl : basepath + '/person-info.json',
				updateUrl : basepath + '/person-info.json',
				deUrl : basepath + '/person-info!batchDestroy.json',
				primary : "id",
				checkbox : true,
				buts:[{
					text: '机构树',
					handler : function(){
						showFun();
					}
				}],
				
				// 定义查询条件Form的高度
				seFormHeight : 80,
				// 定义增删详情页面弹出窗口高度
				winHeight : 300,
				// 宽度
				winWidth : 400,
				// 重载afterSeOneFun方法，加载一条数据后做的特殊处理
				// 查询字段定义，若不定义则不出现查询条件Form
				selectItems : {
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								border : false,
								items : [{
											name : 'name',
											xtype : 'textfield',
											fieldLabel : '姓名',
											width : '150',
											labelWidth : 130,
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								labelWidth : 110,
								defaultType : 'textfield',
								border : false,
								items : [{
											store : sexStore,
											xtype : 'combo',
											resizable : true,
											fieldLabel : '性别',
											name : 'sex',
											hiddenName : 'sex',
											valueField : 'key',
											displayField : 'value',
											mode : 'local',
											typeAhead : true,
											forceSelection : true,
											triggerAction : 'all',
											emptyText : '请选择',
											selectOnFocus : true,
											width : '150',
											anchor : '90%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								border : false,
								items : [new Com.yucheng.bcrm.common.OrgField({
											searchType:'ALLORG',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
											fieldLabel : '机构',
											labelStyle : 'text-align:right;',
											name : 'DIST_USER',
											hiddenName: 'ORGID',
											anchor : '90%',
											checkBox:false
										})]
							},{
								columnWidth : .25,
								layout : 'form',
								border : false,
								items:[
								       new Com.yucheng.crm.common.ProductManage({ 
								    	   xtype:'productChoose',
								    	   fieldLabel : '产品选择', 
								    	   id:'productSelect',
								    	   labelStyle: 'text-align:right;',
								    	   name : 'productName',
								    	   hiddenName:'productId',
								    	   singleSelect:false,
								    	   anchor : '90%'
								       })]
							}]
						
				},

				// 查询列表字段定义，有header属性则在页面显示
				// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
				gclms : [{
							name : 'id'
						}, {
							name : 'name',
							header : '姓名'
						}, {
							name : 'sex',
							header : '性别',
							type : 'mapping',
							store : sexStore,
							mappingkey : 'key',
							mappingvalue : 'value'
						}, {
							name : 'age',
							header : '年龄'
						}],
				// 设置分页每页显示条数，若不设置则不出现分页栏
				pagesize : 20,

				// 新增、修改、详情的form的字段
				fclms : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													name : 'name',
													fieldLabel : '姓名',
													xtype : 'textfield',
													width : 100,
													allowBlank : false,
													maxLength : 200,
													anchor : '90%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [{
													store : sexStore,
													xtype : 'combo',
													resizable : true,
													fieldLabel : '性别',
													name : 'sex',
													hiddenName : 'sex',
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
												}]
									}]
						}, {
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													name : 'age',
													fieldLabel : '年齡',
													width : 100,
													xtype : 'textfield',
													vtype : 'age',
													anchor : '90%'
												}]
									}, {
										// 特别注意：
										// 必须放置隐藏域的主键
										name : 'id',
										xtype : 'hidden'
									}]
						}]
			});

function showFun(){
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		checkField : 'ASTRUE',
		parentAttr : 'SUPERUNITID',
		locateAttr : 'root',//UNITID
		rootValue : JsContext._orgId,
		textField : 'UNITNAME',
		idProperties : 'ID'
	});
	var condition = {searchType:'ALLORG'};
	Ext.Ajax.request({
		url : basepath + '/commsearch.json?condition='+ Ext.encode(condition),
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			loader.nodeArray = nodeArra;
			var children = loader.loadAll();
			orgTreeForShow.appendChild(children);
		},
		failure : function(a, b, c) {
		}
	});

	var s2 = new String('所有机构');
	var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({
		checkBox : this.checkBox,
		width : 300,
		heigth : 385,
		autoScroll : true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode({
			id :JsContext._orgId,
			expanded : true,
			text:JsContext._unitname,
			autoScroll : true,
			children : []
		}),
		resloader : loader,
		split : true
	});
	var orgWindow = new Ext.Window({
		title : '机构列表',
		resizable : true,
		width : 300,
		height : 420,
		draggable : true,
		layout : 'fit',
		closeAction : 'hide',
		modal : true, // 模态窗口
		border : false,
		closable : true,
		layout : 'border',
		items : [{
			region : 'center',
			layout : 'fit',
			items : [orgTreeForShow ]
		}],
		buttons : [{
			text : 'rem',
			handler : function(){
				debugger;
				var imgs = document.createElement("img");
				imgs.className = 'x-tree-icon';
				imgs.setAttribute("src", basepath+'/contents/images/fw/icon_menu_management.gif');
				orgTreeForShow.root.childNodes[0].ui.elNode.appendChild(imgs);
				var imgs = document.createElement("img");
				imgs.className = 'x-tree-icon';
				imgs.setAttribute("src", basepath+'/contents/images/fw/icon_menu_management.gif');
				orgTreeForShow.root.childNodes[0].childNodes[3].ui.elNode.appendChild(imgs);
				var imgs = document.createElement("img");
				imgs.className = 'x-tree-icon';
				imgs.setAttribute("src", basepath+'/contents/images/fw/icon_menu_326.gif');
				orgTreeForShow.root.childNodes[1].ui.elNode.appendChild(imgs);
				var imgs = document.createElement("img");
				imgs.className = 'x-tree-icon';
				imgs.setAttribute("src", basepath+'/contents/images/fw/icon_menu_326.gif');
				orgTreeForShow.root.childNodes[1].childNodes[2].ui.elNode.appendChild(imgs);
				var imgs = document.createElement("img");
				imgs.className = 'x-tree-icon';
				imgs.setAttribute("src", basepath+'/contents/images/fw/icon_menu_326.gif');
				orgTreeForShow.root.ui.elNode.appendChild(imgs);
				var imgs = document.createElement("img");
				imgs.className = 'x-tree-icon';
				imgs.setAttribute("src", basepath+'/contents/images/fw/icon_menu_management.gif');
				orgTreeForShow.root.childNodes[1].childNodes[1].ui.elNode.appendChild(imgs);
			}
		}]
	});
	
	orgWindow.show();
};

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				frame : true,
				items : [listPanel]
			});

})