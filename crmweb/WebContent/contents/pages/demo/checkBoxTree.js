/*******
 *@author : songxs
 *@since  : 2012-11-19
 *@constructor :单选和多选树
 * 
 */	

Ext.onReady(function(){

	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({//同步树加载数据
		parentAttr : 'PARENT_ID', 	 //指向父节点的属性列
		locateAttr : 'ID',       	 //节点定位属性列，也是父属性所指向的列
		rootValue : "0",          	 //虚拟根节点id 若果select的值为root则为根节点
		textField : 'NAME',			 //用于展示节点名称的属性列
		idProperties : 'ID'          //指定节点ID的属性列
	});
	var mutliTreeForShow = new Com.yucheng.bcrm.TreePanel({	//左边的tree的形状

		title:'菜单维护（多选）',
		id:'blocMemberTree',
		width:400,
		autoScroll:true,
		checkBox:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'主菜单',
			autoScroll:true,
			children:[]
		}),
		resloader:loader,
		region:'center',
		split:true,
		clickFn:function(node){//点击事件，当点击该树的某一节点后，所有按钮变为可用，在Panel--addMenu上展示该节点的相应的值
			}
	});	 
	
	var singleTreeForShows = new Com.yucheng.bcrm.TreePanel({	//左边的tree的形状

		title:'菜单维护（单选）',
		id:'blocMemberTrees',
		width:400,
		autoScroll:true,
		checkBox:true,
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
			},
			listeners:{
				'checkchange' : function(node, checked) {//改变check时，判断如果是父节点，其子节点相应进行check
				      if(checked){
				         var checkedNodes = singleTreeForShows.getChecked();
				         for(i=0;i<checkedNodes.length;i++){
				           var checkeNode = checkedNodes[i];
				           if(node.id!=checkeNode.id){
				           checkeNode.getUI().checkbox.checked = false;
				           checkeNode.attributes.checked = false;
				           singleTreeForShows.fireEvent('check', checkeNode, false);
				}
				 }
				
					}
			}
			}
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
			mutliTreeForShow.appendChild(children);
			singleTreeForShows.appendChild(children);

		}
	});
	
	
	var mainView = new Ext.Viewport({
		layout:'border',
		items:[singleTreeForShows,mutliTreeForShow]
	});	
	
	
	
	
	
	
	
	
	
	
});