/**
 * @Title: 产品类别维护
 * @author luyy
 * @date 2014-06-30
 */
 
	imports([ //导入树控件
	  	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js' 
	]);
	var nodeNow = '';//被选中的节点
	
	Ext.QuickTips.init();
	var lookupTypes = [{
		TYPE : 'VIEW_DETAIL',//产品视图展示方案
		url : '/productPlan!searchPlan.json?type=1',
		key : 'KEY',
		value : 'VALUE',
		root : 'data'
	},{
		TYPE : 'PROD_VIEW',//产品信息展示方案
		url : '/productPlan!searchPlan.json?type=2',
		key : 'KEY',
		value : 'VALUE',
		root : 'data'
	}];
			
//	var productTreeLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		parentAttr : 'CATL_PARENT',
//		locateAttr : 'CATL_CODE',
//		rootValue :'0',
//		textField : 'CATL_NAME',
//		idProperties : 'CATL_CODE',
//		jsonRoot : 'json.data'
//	});
//	
//	Ext.Ajax.request({//请求产品树数据
//		url : basepath + '/product-kinds1.json',//加载产品种类树
//		method:'GET'
//	});
	
	/**
	 * 左侧树面板
	 */
	var treeLoaders = [ {
		key : 'PROUDUCTLOADER',
		url : basepath + '/product-kinds1.json',//加载产品种类树
		parentAttr : 'CATL_PARENT',
		locateAttr : 'CATL_CODE',
		rootValue : '0',
		textField : 'CATL_NAME',
		idProperties : 'CATL_CODE',
		jsonRoot : 'json.data'
	} ];
	var treeCfgs = [ {
		key : 'PROUDUCTTREE',
		loaderKey : 'PROUDUCTLOADER',
		autoScroll : true,
		rootCfg : {
			expanded : true,
			text : '银行产品树',
			autoScroll : true,
			children : []
		},
		clickFn : function(node) {
			nodeNow = node;
			if(node.attributes.CATL_CODE == undefined){
				return;
			}else{
			if(getCurrentView()!= getDetailView())
				showDetailView();
			else{
				loadTreeInfo(nodeNow);
			}
			}
		},
		tbar: [{
			text : '新增',
			handler : function() {
				showCreateView();
			}
		},{
			text : '修改',
			handler : function() {
				showEditView();
			}
		},{
			text : '删除',
			handler : function() {
				if (nodeNow == ''||nodeNow == null) {
					Ext.Msg.alert('提示', '请选择一条数据！');
					return false;
				} else {
					var catlCode = nodeNow.attributes.CATL_CODE;
					if (!nodeNow.leaf) {
						Ext.Msg.alert('提示', '本类别下含有子类别，不能删除！');
						return false;
					}
					Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
						if (buttonId.toLowerCase() == "no") {
							return false;
						}
						Ext.Ajax.request({
							url : basepath + '/product-kinds/' + catlCode + '.json',
							method : 'DELETE',
							success : function() {
								Ext.Msg.alert('提示', '删除成功');
								leftTree.deleteNode(nodeNow);
								nodeNow = '';
								hideCurrentView();
							},
							failure : function() {
								Ext.Msg.alert('提示', '删除失败');
							}
						});
					});
				}
			}
		}]
	}, {
		key : 'PROUDUCTTREECOMBO',
		loaderKey : 'PROUDUCTLOADER',
		autoScroll : true,
		rootCfg : {
			expanded : true,
			text : '银行产品树',
			autoScroll : true,
			children : []
		},clickFn : function(node1) {//单击事件，当单击树节点时触发并且获得这个节点的CATL_CODE 
	       nodeNow = node1;
	       if(node1.attributes.CATL_CODE == undefined){
//		   Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
	       return ;  
	}
}
	} ];
	
	
	WLJUTIL.suspendViews = false;
	WLJUTIL.alwaysLockCurrentView = false;
	
	var createView = true;
	var editView = true;
	var detailView = true;
	
	
	var comitUrl = basepath + '/product-kinds.json';//操作产品类别CRUD
	
	var needGrid = false;
	
	/**
	 * 展示页面用到的字段
	 */
	var fields = [ 
        {name : 'CATL_CODE',text : '产品类别编码',searchField : true,editable : false},
		{name : 'CATL_NAME',	text : '产品类别名',	searchField : true,	editable : true,allowBlank : false},
		{name : 'CATL_ORDER',text : '产品类别节点顺序',dataType:'numberNoDot'}, 
		{name : 'CATL_PARENT_NAME',text : '上级产品类别名称',readOnly : true,cls:'x-readOnly'}, 
		{
			name : 'CATL_PARENT',
			text : '上级产品类别',
			xtype : 'wcombotree',
			innerTree : 'PROUDUCTTREECOMBO',
			allowBlank : false,
			showField : 'text',
			hidden : true,
			hideField : 'CATL_CODE',
			editable : false
		}, 
		{name : 'VIEW_DETAIL',text : '产品视图展示方案',translateType : 'VIEW_DETAIL'}, 
		{name : 'PROD_VIEW',text : '产品信息展示方案',translateType : 'PROD_VIEW'}, 
		{name : 'CATL_LEVEL'} 
	];
	
	var createFormViewer = [ {
		columnCount : 2,
		fields : [ 'CATL_NAME', 'CATL_CODE', 'CATL_ORDER', 'CATL_PARENT','VIEW_DETAIL','PROD_VIEW','CATL_PARENT_NAME' ],
	fn : function(CATL_NAME, CATL_CODE, CATL_ORDER, CATL_PARENT, VIEW_DETAIL,PROD_VIEW,CATL_PARENT_NAME) {
			CATL_PARENT.hidden = false;
			CATL_CODE.hidden = true;
			CATL_PARENT_NAME.hidden = true;
			return [ CATL_NAME, CATL_CODE, CATL_ORDER, CATL_PARENT, VIEW_DETAIL,PROD_VIEW,CATL_PARENT_NAME ];
		}
	} ];
	
	var editFormViewer = [ {
		columnCount : 2,
		fields : [ 'CATL_NAME', 'CATL_CODE', 'CATL_ORDER', 'CATL_PARENT_NAME','CATL_PARENT', 'VIEW_DETAIL','PROD_VIEW' ],
		fn : function(CATL_NAME, CATL_CODE, CATL_ORDER, CATL_PARENT_NAME,CATL_PARENT, VIEW_DETAIL,PROD_VIEW) {
			CATL_CODE.readOnly = true;
			CATL_CODE.cls = 'x-readOnly';
			return [ CATL_NAME, CATL_CODE, CATL_ORDER, CATL_PARENT_NAME,CATL_PARENT, VIEW_DETAIL,PROD_VIEW ];
		}
	} ];
	
	var detailFormViewer = [ {
		columnCount : 2,
		fields : [ 'CATL_NAME', 'CATL_CODE', 'CATL_ORDER', 'CATL_PARENT_NAME','CATL_PARENT', 'VIEW_DETAIL','PROD_VIEW' ],
		fn : function(CATL_NAME, CATL_CODE, CATL_ORDER, CATL_PARENT_NAME,CATL_PARENT, VIEW_DETAIL,PROD_VIEW) {
			CATL_NAME.readOnly = true;
			CATL_CODE.readOnly = true;
			CATL_ORDER.readOnly = true;
			CATL_PARENT_NAME.readOnly = true;
			CATL_PARENT.readOnly = true;
			VIEW_DETAIL.readOnly = true;
			PROD_VIEW.readOnly = true;
			CATL_NAME.cls = 'x-readOnly';
			CATL_CODE.cls = 'x-readOnly';
			CATL_ORDER.cls = 'x-readOnly';
			CATL_PARENT_NAME.cls = 'x-readOnly';
			CATL_PARENT.cls = 'x-readOnly';
			VIEW_DETAIL.cls = 'x-readOnly';
			PROD_VIEW.cls = 'x-readOnly';
			return [ CATL_NAME, CATL_CODE, CATL_ORDER, CATL_PARENT_NAME,CATL_PARENT, VIEW_DETAIL,PROD_VIEW ];
		}
	} ];
	
	//左侧树对象
	var leftTree = TreeManager.createTree('PROUDUCTTREE');
	//加载树到左侧区域
	var edgeVies = {
		left : {
			width : 200,
			layout : 'form',
			items : [ leftTree ]
		}
	};
	
	var linkages = {
		CATL_PARENT : {
			fields : ['CATL_PARENT_NAME'],
			fn : function(CATL_PARENT,CATL_PARENT_NAME){
				Ext.each(treeCfgs[1].resloader.nodeArray,function(a){
					if(a.id == CATL_PARENT.value){
						CATL_PARENT_NAME.setValue(a.text);
					}
				});
			}
		}
	};
	
	/**
	 * 对修改和详情操作，如果未选择数据项则不执行面板的滑入
	 */
	var beforeviewshow = function(view) {
		if (view.baseType == 'editView' || view.baseType == 'detailView') {
			if (nodeNow == ''||nodeNow == null) {
				Ext.Msg.alert('提示', '请选择产品类别');
				return false;
			}
		}
		if (view.baseType == 'editView' ) {
		if (nodeNow.text == '银行产品树') {
			Ext.Msg.alert('提示', '根类别不能修改');
			return false;
		}	
		}
	};
	
	var viewshow = function(view){
		if (view.baseType == 'editView' || view.baseType == 'detailView') {
				loadTreeInfo(nodeNow);
		}
		if(view.baseType == 'createView' ){
			if (nodeNow == ''||nodeNow == null) {
			}else{
				getCurrentView().setValues({
					CATL_CODE:'',
					CATL_NAME:'',
					CATL_ORDER:'',
					CATL_PARENT_NAME:nodeNow.attributes.CATL_NAME,
					CATL_PARENT: nodeNow.attributes.CATL_CODE,
					VIEW_DETAIL:'',
					PROD_VIEW:''
				});
			}
		}
	};
	/**
	 * 数据提交后，对左侧产品数数据做处理
	 */
	var afertcommit = function(data, cUrl, result) {
		var tempView = getCurrentView();
		if (result) {
			if(tempView.baseType == 'createView'){
				Ext.Ajax.request({
					url : basepath + '/systemUnit-query!getPid.json',
					method : 'GET',
					success : function(response) {
						var nodeArra = Ext.util.JSON.decode(response.responseText);
						var node = {};
						node.id = nodeArra.pid;
						node.text = data.CATL_NAME;
			            node.CATL_CODE = nodeArra.pid;
						node.CATL_NAME = data.CATL_NAME;
						node.CATL_ORDER = data.CATL_ORDER;
						node.CATL_PARENT = data.CATL_PARENT;
						node.CATL_PARENT_NAME = data.CATL_PARENT_NAME;
						node.VIEW_DETAIL = data.VIEW_DETAIL;
						node.PROD_VIEW = data.PROD_VIEW;
						node.leaf = true;
						leftTree.resloader.addNode(node);
						getCurrentView().setValues({CATL_CODE: nodeArra.pid});
					
						//新增 2015-1-30
							Ext.Ajax.request({// 重新加载菜单树
								url : basepath + '/product-kinds1.json',
								method : 'GET',
								success : function(response) {
									var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
									leftTree.resloader.nodeArray = nodeArra;
									leftTree.resloader.refreshCache(); 
									var children = leftTree.resloader.loadAll(); 
									leftTree.root.removeAll(true); 
									leftTree.appendChild(children);
									window.location.reload();// 当前页面刷新
								}
							});	hideCurrentView();
					
					}
				});
			}else if(tempView.baseType == 'editView'){
				var node = {};
				node.id = data.CATL_CODE;
				node.text = data.CATL_NAME;
				node.CATL_CODE = data.CATL_CODE;
				node.CATL_NAME = data.CATL_NAME;
				node.CATL_ORDER = data.CATL_ORDER;
				node.CATL_PARENT = data.CATL_PARENT;
				node.CATL_PARENT_NAME = data.CATL_PARENT_NAME;
				node.VIEW_DETAIL = data.VIEW_DETAIL;
				node.PROD_VIEW = data.PROD_VIEW;
				leftTree.editNode(node);
				hideCurrentView();
				
			}
		}	
	};
	//加载树节点信息
	function loadTreeInfo(nodeCatl){
		getCurrentView().setValues({
			CATL_CODE: nodeCatl.attributes.CATL_CODE,
			CATL_NAME:nodeCatl.attributes.CATL_NAME,
			CATL_ORDER:nodeCatl.attributes.CATL_ORDER,
			CATL_PARENT_NAME:nodeCatl.attributes.CATL_PARENT_NAME,
			CATL_PARENT:nodeCatl.attributes.CATL_PARENT,
			VIEW_DETAIL:nodeCatl.attributes.VIEW_DETAIL,
			PROD_VIEW:nodeCatl.attributes.PROD_VIEW
		});
	}
