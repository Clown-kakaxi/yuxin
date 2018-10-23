/**
 * 数据集加载器
 */	
var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'PARENT_ID',
		locateAttr : 'NODEID',
		rootValue : '0',
		textField : 'NAME',
		idProperties : 'NODEID'
	});
	Ext.Ajax.request({
		url : basepath + '/queryagilequery.json',
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.JSON.data;
			nodeArrays=nodeArra.JSON.data;
			for ( var item in loader.nodeArray) {
				if (typeof loader.nodeArray[item] === 'object') {
					if (loader.nodeArray[item].TABLES == '2')
						loader.nodeArray[item].NODEID = 'b' + loader.nodeArray[item].NODEID;
				}
			}
			var children = loader.loadAll();
			treeOfPoroduct.appendChild(children);
		}
	});
	/**
	 * 数据集字段树
	 */
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
		title : '条件字段',
		width : document.body.scrollWidth / 100*23-1,
		height : document.body.scrollHeight-63,
		autoScroll : true,
		rootVisible : false,
		ddGroup : 'rightPanel',
		split : true,
		enableDrag:true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode({
			id : 'root',
			expanded : true,
			text : '客户视图',
			autoScroll : true,
			children : []
		}),
		resloader : loader
	});
	
	treeOfPoroduct.on('afterrender',function(tree){
		treeOfPoroduct.root.expand( true, false, function(a,b,c,d){
		});
	});