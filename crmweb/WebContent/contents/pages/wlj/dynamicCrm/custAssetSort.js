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
//var lookupTypes = [{
//	TYPE : 'VIEW_DETAIL',//产品视图展示方案
//	url : '/productPlan!searchPlan.json?type=1',
//	key : 'KEY',
//	value : 'VALUE',
//	root : 'data'
//},{
//	TYPE : 'PROD_VIEW',//产品信息展示方案
//	url : '/productPlan!searchPlan.json?type=2',
//	key : 'KEY',
//	value : 'VALUE',
//	root : 'data'
//}];
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
	key : 'ASSETLOADER',
	url : basepath + '/assetsCategoryMaintain.json',//加载产品种类树
	parentAttr : 'ASSET_PARENT',
	locateAttr : 'ASSET_ID',
	rootValue : '0',
	textField : 'ASSET_NAME',
	idProperties : 'ASSET_ID',
	jsonRoot : 'json.data'
} ];
var treeCfgs = [ {
	key : 'ASSETTREE',
	loaderKey : 'ASSETLOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		text : '银行产品树',
		autoScroll : true,
		children : []
	},
	clickFn : function(node) {
		nodeNow = node;
		if(node.attributes.ASSET_ID == undefined){
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
				var id = nodeNow.attributes.ASSET_ID;
				if (!nodeNow.leaf) {
					Ext.Msg.alert('提示', '本类别下含有子类别，不能删除！');
					return false;
				}
				Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return false;
					}
					Ext.Ajax.request({
						url : basepath + '/assetsCategoryMaintain!delete.json?id=' + id,
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
	key : 'ASSETTREECOMBO',
	loaderKey : 'ASSETLOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		text : '银行产品树',
		autoScroll : true,
		children : []
	},clickFn : function(node1) {//单击事件，当单击树节点时触发并且获得这个节点的CATL_CODE
		nodeNow = node1;
		if(node1.attributes.ASSET_ID == undefined){
//		   Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
			return ;
		}
	}
} ]

//占据整个视图
WLJUTIL.suspendViews = false;
WLJUTIL.alwaysLockCurrentView = false;

var createView = true;
var editView = true;
var detailView = true;


var comitUrl = basepath + '/assetsCategoryMaintain.json';//操作产品类别CRUD

var needGrid = false;

/**
 * 展示页面用到的字段
 */
var fields = [
	{name : 'ASSET_ID',text : '资产类别编码',searchField : true ,editable : false},
	{name : 'ASSET_NAME',	text : '资产类别名称',	searchField : true,	editable : true,allowBlank : false},
	{name : 'ASSET_LEVEL',text : '资产级别',readOnly : true,cls:'x-readOnly'},
	//{name : 'ASSET_PARENT',text : '上级产品类别名称',readOnly : true,cls:'x-readOnly'},
	{
		name : 'ASSET_PARENT',
		text : '上级产品类别',
		xtype : 'wcombotree',
		innerTree : 'ASSETTREECOMBO',
		allowBlank : false,
		showField : 'text',
		hidden : false,
		hideField : 'ASSET_ID',
		editable : false
	//	listeners:{
	//	'change' : function(attr){
	//		var v = attr.value;
	//		alert(v);
	//		if(v != undefined && v.indexOf("2") == 0){
	//			Ext.Msg.alert('提示','该节点不可选择！');
	//			return false;
	//		}
	//		if(v == undefined){
	//			getCurrentView().contentPanel.getForm().findField('ASSET_LEVEL').setValue(1);
	//		}else{
	//			getCurrentView().contentPanel.getForm().findField('ASSET_LEVEL').setValue(Number(v.substr(0,1))+1);
	//		}
	//	}
	//}
	},
	{name : 'ASSET_ORDER',text : '产品类别顺序',dataType:'numberNoDot'},
	{name : 'ASSET_NAME_ID',text : '上级产品名称'}
];

var createFormViewer = [ {
	columnCount : 2,
	fields : [ 'ASSET_ID', 'ASSET_NAME', 'ASSET_LEVEL', 'ASSET_PARENT','ASSET_ORDER' ],
	fn : function(ASSET_ID, ASSET_NAME, ASSET_LEVEL, ASSET_PARENT, ASSET_ORDER) {
		ASSET_ID.hidden = true;
		ASSET_NAME.hidden = false;
		ASSET_LEVEL.hidden = false;
		ASSET_PARENT.hidden = false;
		ASSET_ORDER.hidden = false;
		return [ ASSET_ID, ASSET_NAME, ASSET_LEVEL, ASSET_PARENT, ASSET_ORDER];
	}
} ];

var editFormViewer = [ {
	columnCount : 2,
	fields : [ 'ASSET_ID', 'ASSET_NAME', 'ASSET_LEVEL', 'ASSET_PARENT','ASSET_ORDER' ,'ASSET_NAME_ID'],
	fn : function(ASSET_ID, ASSET_NAME, ASSET_LEVEL, ASSET_PARENT, ASSET_ORDER, ASSET_NAME_ID) {
		ASSET_PARENT.hidden = true;
		ASSET_ID.hidden = true;
		ASSET_ID.readOnly = true;
		ASSET_ID.cls = 'x-readOnly';
		return [ ASSET_ID, ASSET_NAME, ASSET_LEVEL, ASSET_PARENT, ASSET_ORDER, ASSET_NAME_ID ];
	}
} ];

var detailFormViewer = [ {
	columnCount : 2,
	fields : [ 'ASSET_ID', 'ASSET_NAME', 'ASSET_LEVEL', 'ASSET_PARENT','ASSET_ORDER','ASSET_NAME_ID'],
	fn : function(ASSET_ID, ASSET_NAME, ASSET_LEVEL, ASSET_PARENT, ASSET_ORDER, ASSET_NAME_ID) {
		ASSET_ID.hidden = true;
		ASSET_PARENT.hidden = true;
		//CATL_NAME.readOnly = true;
		//CATL_CODE.readOnly = true;
		//CATL_ORDER.readOnly = true;
		//CATL_PARENT_NAME.readOnly = true;
		//CATL_PARENT.readOnly = true;
		//VIEW_DETAIL.readOnly = true;
		//PROD_VIEW.readOnly = true;
		//CATL_NAME.cls = 'x-readOnly';
		//CATL_CODE.cls = 'x-readOnly';
		//CATL_ORDER.cls = 'x-readOnly';
		//CATL_PARENT_NAME.cls = 'x-readOnly';
		//CATL_PARENT.cls = 'x-readOnly';
		//VIEW_DETAIL.cls = 'x-readOnly';
		//PROD_VIEW.cls = 'x-readOnly';
		return [ ASSET_ID, ASSET_NAME, ASSET_LEVEL, ASSET_PARENT, ASSET_ORDER ,ASSET_NAME_ID];
	}
} ];

//左侧树对象
var leftTree = TreeManager.createTree('ASSETTREE');
//加载树到左侧区域
var edgeVies = {
	left : {
		width : 200,
		layout : 'form',
		items : [ leftTree ]
	}
};

var linkages = {
	ASSET_PARENT : {
		fields : ['ASSET_LEVEL'],
		fn : function(ASSET_PARENT,ASSET_LEVEL){
			Ext.each(treeCfgs[1].resloader.nodeArray,function(a){
				if(a.id == ASSET_PARENT.value){
					ASSET_LEVEL.setValue(Number(a.ASSET_LEVEL)+1);
				}
				if(ASSET_PARENT.value == undefined){
					ASSET_LEVEL.setValue(1);
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
				ASSET_ID:'',
				ASSET_NAME:'',
				ASSET_LEVEL:'',
				ASSET_PARENT: nodeNow.attributes.ASSET_ID,
				ASSET_ORDER:''
			});
		}
	}
};
/**
 * 数据提交后，对左侧产品数数据做处理
 */
var afertcommit = function(data, cUrl, result) {
//	var tempView = getCurrentView();
//	if (result) {
//		if(tempView.baseType == 'createView'){
//			Ext.Ajax.request({
//				url : basepath + '/systemUnit-query!getPid.json',
//				method : 'GET',
//				success : function(response) {
//					var nodeArra = Ext.util.JSON.decode(response.responseText);
//					var node = {};
//					node.id = nodeArra.pid;
//					node.text = data.CATL_NAME;
//					node.CATL_CODE = nodeArra.pid;
//					node.CATL_NAME = data.CATL_NAME;
//					node.CATL_ORDER = data.CATL_ORDER;
//					node.CATL_PARENT = data.CATL_PARENT;
//					node.CATL_PARENT_NAME = data.CATL_PARENT_NAME;
//					node.VIEW_DETAIL = data.VIEW_DETAIL;
//					node.PROD_VIEW = data.PROD_VIEW;
//					node.leaf = true;
//					leftTree.resloader.addNode(node);
//					getCurrentView().setValues({CATL_CODE: nodeArra.pid});
//
//					//新增 2015-1-30
//					Ext.Ajax.request({// 重新加载菜单树
//						url : basepath + '/product-kinds1.json',
//						method : 'GET',
//						success : function(response) {
//							var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
//							leftTree.resloader.nodeArray = nodeArra;
//							leftTree.resloader.refreshCache();
//							var children = leftTree.resloader.loadAll();
//							leftTree.root.removeAll(true);
//							leftTree.appendChild(children);
							window.location.reload();// 当前页面刷新
//						}
//					});	hideCurrentView();
//
//				}
//			});
//		}else if(tempView.baseType == 'editView'){
//			var node = {};
//			node.id = data.CATL_CODE;
//			node.text = data.CATL_NAME;
//			node.CATL_CODE = data.CATL_CODE;
//			node.CATL_NAME = data.CATL_NAME;
//			node.CATL_ORDER = data.CATL_ORDER;
//			node.CATL_PARENT = data.CATL_PARENT;
//			node.CATL_PARENT_NAME = data.CATL_PARENT_NAME;
//			node.VIEW_DETAIL = data.VIEW_DETAIL;
//			node.PROD_VIEW = data.PROD_VIEW;
//			leftTree.editNode(node);
//			hideCurrentView();
//
//		}
//	}
};
//加载树节点信息
function loadTreeInfo(nodeAsset){
	getCurrentView().setValues({
		ASSET_ID: nodeAsset.attributes.ASSET_ID,
		ASSET_NAME:nodeAsset.attributes.ASSET_NAME,
		ASSET_LEVEL:nodeAsset.attributes.ASSET_LEVEL,
		ASSET_PARENT:nodeAsset.attributes.ASSET_PARENT,
		ASSET_ORDER:nodeAsset.attributes.ASSET_ORDER,
		ASSET_NAME_ID:nodeAsset.attributes.ASSET_NAME_ID
	});
}
