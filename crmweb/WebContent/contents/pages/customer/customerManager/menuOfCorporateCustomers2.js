	var h = document.body.clientHeight;
	document.getElementById('viewport_center').innerHTML = '<iframe id="content" name="content2" style="width:100%;height:'
			+ h
			+ 'px;" frameborder="no"" src=\"'
			+ basepath
			+ '/contents/pages/customer/customerManager/customerView1.jsp?customerId='
			+ oCustInfo.cust_id + '\" "/> scrolling="auto"> ';
	
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {
		// /**节点数组，可以改为从后台读取*/
		// nodeArray :nodeArra,
		/** 指向父节点的属性列 */
		parentAttr : 'PARENTID',
		/** 节点定位属性列，也是父属性所指向的列 */
		locateAttr : 'ID',
		/** 虚拟根节点id */
		rootValue : '0',
		/** 用于展示节点名称的属性列 */
		textField : 'NAME',
		/** 指定节点ID的属性列 */
		idProperties : 'ID',
		/** 节点点击事件句柄 */
		clickFn : function(node) {
		}
	});
	Ext.Ajax.request( {
		url : basepath + '/queryCustViewAuthorize!queryCustViewTree.json?viewtype='
				+ oCustInfo.cust_type,
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			debugger;
			loader.nodeArray = nodeArra.JSON.data;
			var children = loader.loadAll();
			treeOfPoroduct.appendChild(children);
			treeOfPoroduct.expandAll();
	}
	});
	
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel(
			{
				autoScroll : true,
				rootVisible : false,
				split : true,
				/** 虚拟树形根节点 */
				root : new Ext.tree.AsyncTreeNode( {
					id : 'root',
					expanded : true,
					text : '客户视图',
					autoScroll : true,
					// hidden:true,
					children : []
				}),
				resloader : loader,
				clickFn : function(node) {
				debugger;
					if (node.attributes.ADDR != '' && node.attributes.ADDR != '0') {
	                       //判断加载的是js文件还是jsp文件
						if (node.attributes.ADDR.indexOf('.jsp') == -1) {
							fElementRemove();
							fnViewLoader(basepath + node.attributes.ADDR);
						} else {
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
									+ h
									+ 'px;" frameborder="no"" src=\"'
									+ basepath + sUrl + '\" "/> scrolling="auto"> ';
	
						}
					}
	
				},
				listeners : {
					'checkchange' : function(node) {
	
					}
				}
			});
	treeOfPoroduct.on("beforeclick", function(node) {
	
	});
	var viewport_left = new Ext.Panel( {
		id : 'viewport_left',
		frame : true,
		renderTo : 'sena_tree',
		height : document.body.scrollHeight - 61,
		autoScroll : true,
		title : '<span style="font-weight:normal">客户全景视图菜单</span>',
		items : [ {
			margins : '0 0 0 0',
			items : [ treeOfPoroduct ]
		} ]
	});
	
	var sJsName = "";
	
	var fnViewLoader = function(sJsName) {
		Ext.ScriptLoader.loadScript( {
			scripts : [ sJsName ]
		});
	};
	var fElementRemove = function() {
	
		document.getElementById('viewport_center').innerHTML = "";
	};
