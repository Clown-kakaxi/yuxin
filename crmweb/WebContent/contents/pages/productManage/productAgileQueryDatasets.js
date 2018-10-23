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
		url : basepath + '/querytatgetcusquery.json',
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
			setTimeout(function() {
				selectItems(0);
					},3000);
		}
	});
	/**
	 * 数据集字段树
	 */
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
		title : '条件字段',
		width : document.body.scrollWidth / 100*40-1,
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
	var selectItems = function(b){
		if(b<30){
		Ext.Ajax.request({
			url:basepath+'/querytatgetcusquery!queryAgileCondition.json?SS_ID='+pProductId,
			method: 'GET',
			success : function(response) {
				simple.removeAllItems();
				var conditionData = Ext.util.JSON.decode(response.responseText);
				var conditionArray=conditionData.JSON.data;
				 if(conditionArray.length>0){
						var reloadFlag = false;
						Ext.each(conditionArray,function(con){
							var node = treeOfPoroduct.root.findChild("id", "b"+con.SS_COL_ITEM, true);
							if(node){
								simple.addItems(node,con.SS_COL_OP,con.SS_COL_VALUE);
							}else{
								reloadFlag=true;
							}
						});
						if(reloadFlag){
							simple.removeAllItems();
							var fortims = b+1;
							setTimeout(function() {
								selectItems(fortims);
									},3000);
						}
						if(conditionArray[0].SS_COL_JOIN=='true'){
							radio.items.items[0].items.items[0].setValue(true);
							right_panel.conditionJoinType = 'true';
						}else{
							radio.items.items[1].items.items[0].setValue(true);
							right_panel.conditionJoinType = 'false';
						}
				 }
			},
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示','您没有此权限!');
				} else {
					Ext.Msg.alert('提示','操作失败!');
				}
			}
		});	
	}else{
		Ext.Msg.alert('提示','产品条件字段加载错误,请重新打开目标客户设定!');
	}
	};
	treeOfPoroduct.on('afterrender',function(tree){
		treeOfPoroduct.root.expand( true, false, function(a,b,c,d){
		});
	});
