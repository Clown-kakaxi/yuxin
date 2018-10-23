/**
 * 客户经理管理视图
 * @author songxs
 * @since 2012-12-7
 * 
 */


	var nodeArray = [
	                 {"ID":"1","NAME":"概览信息","PARENTID":"0","ADDR":"/contents/pages/customer/accountManager/overViewInformation.js"},
	                 {"ID":"2","NAME":"管辖客户","PARENTID":"0","ADDR":"/contents/pages/customer/accountManager/adminCust.js"},
	                 {"ID":"3","NAME":"客户持有产品","PARENTID":"0","ADDR":"/contents/pages/customer/customerManager/customerBaseInformation/customerPoroductInformation3.js"}
	                 ];
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {
		nodeArray: nodeArray,
		parentAttr : 'PARENTID',
		locateAttr : 'ID',
		rootValue : '0',
		textField : 'NAME',
		idProperties : 'ID',
		clickFn : function(node) {
	}
	});

	
	var treeOfCustManager = new Com.yucheng.bcrm.TreePanel({
		autoScroll : true,
		rootVisible : false,
		split : true,
	//	layout:'fit',
		height : document.body.scrollHeight-100,
		root : new Ext.tree.AsyncTreeNode({
			id : 'root',
			expanded : true,
			text : '客户经理业务全景视图',
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
				document.getElementById('viewport_centers').innerHTML = '<iframe id="content" name="content2" style="width:100%;height:'
					+ h	+ 'px;" frameborder="no"" src=\"'
						+ basepath + sUrl + '\" "/> scrolling="auto"> ';
			}
		}
		}
	});
	var nodeArras = nodeArray;
	loader.nodeArray = nodeArras;
	var children = loader.loadAll();
	treeOfCustManager.appendChild(children);
	treeOfCustManager.expandAll();
	
	var viewport_cust = new Ext.Panel( {
		id : 'viewport_cust',
		frame : true,
		renderTo : 'cust_tree',
		height : document.body.scrollHeight-30,
		autoScroll : true,
		title : '<span style="font-weight:normal">客户经理业务全景视图</span>',
		items : [ {
			margins : '0 0 0 0',
			items : [ treeOfCustManager]
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
	document.getElementById('viewport_centers').innerHTML = "";
};
treeOfCustManager.clickFn(treeOfCustManager.root.childNodes[0]);

