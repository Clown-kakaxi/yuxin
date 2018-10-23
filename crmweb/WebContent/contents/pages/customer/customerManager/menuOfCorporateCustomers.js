
var h = document.body.clientHeight;
document.getElementById('viewport_center').innerHTML = '<iframe id="content" name="content2" style="width:100%;height:'
	+ h
	+ 'px;" frameborder="no"" src=\"'
	+ basepath
	+ '/contents/pages/customer/customerManager/customerView1.jsp?customerId='
	+ oCustInfo.cust_id + '\" "/> scrolling="auto"> ';
	
var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {
	parentAttr : 'PARENTID',
	locateAttr : 'ID',
	rootValue : '0',
	textField : 'NAME',
	idProperties : 'ID',
	clickFn : function(node) {
	}
});
Ext.Ajax.request( {
	url : basepath + '/queryCustViewAuthorize!queryCustViewTree.json?viewtype='
			+ oCustInfo.cust_type,
	method : 'GET',
	success : function(response) {
		var nodeArra = Ext.util.JSON.decode(response.responseText);
		loader.nodeArray = nodeArra.JSON.data;
		var children = loader.loadAll();
		treeOfPoroduct.appendChild(children);
		treeOfPoroduct.expandAll();
	}
});
	
var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
	autoScroll : true,
	rootVisible : false,
	split : true,
	root : new Ext.tree.AsyncTreeNode({
		id : 'root',
		expanded : true,
		text : '客户视图',
		autoScroll : true,
		// hidden:true,
		children : []
	}),
	resloader : loader,
	clickFn : function(node) {
		if (node.attributes.ADDR != '' && node.attributes.ADDR != '0') {
			//判断加载的是js文件还是jsp文件
			if (node.attributes.ADDR.indexOf('.jsp') == -1) {
				fnViewLoader(basepath + node.attributes.ADDR);
			} else {
				fElementRemove();
				var aParame = node.attributes.ADDR.split('|');
				var sUrl = '';
				var pattern = /^\d+$/;
				for ( var i in aParame) {
					if (pattern.test(i)) {
						//i为单数的时候是参数
						if (i % 2 != 0) {
							sUrl = sUrl + eval(aParame[i]);
						} else {
							sUrl = sUrl + aParame[i];
						}
					}
				}
				var h = document.body.clientHeight;
				document.getElementById('viewport_center').innerHTML = '<iframe id="content" name="content2" style="width:100%;height:'
						+ h	+ 'px;" frameborder="no"" src=\"'
						+ basepath + sUrl + '\" "/> scrolling="auto"> ';
			}
		}
	}
});
var viewport_left = new Ext.Panel( {
	id : 'viewport_left',
	frame : true,
	renderTo : 'sena_tree',
	height : document.body.scrollHeight-30,
	autoScroll : true,
	title : '<span style="font-weight:normal">客户全景视图菜单</span>',
	items : [ {
		margins : '0 0 0 0',
		items : [ treeOfPoroduct ]
	} ]
});

var fnViewLoader = function(file) {
	fElementRemove();
	Wlj.ext.app.CurrentURL = file;
	Wlj.ext.app.PrintedObject[file] = new Array();
	Wlj.ext.app.PrintedStore[file] = new Array();
	Ext.ScriptLoader.loadScript({
		scripts: [file],callback:function(){}
	});
};
var fElementRemove = function() {
	Ext.each(Wlj.getCurrentRoots(),function(r){
		if(r.destroy)
			r.destroy();
	});
	Ext.each(Wlj.getCurrentStores(),function(s){
		if(s.destroy)
			s.destroy();
	});
	Wlj.ext.app.CurrentURL = '';
	document.getElementById('viewport_center').innerHTML = "";
};
