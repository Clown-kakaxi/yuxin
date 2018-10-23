/*******
 *@author : songxs
 *@since  : 2012-11-19
 *@constructor :同步树和异步树例子
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
	var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({	//左边的tree的形状

		title:'菜单维护（同步加载树）',
		id:'blocMemberTree',
		width:400,
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
			leftTreeForShow.appendChild(children);
		}
	});
    var tempCode = '';//用于行业分类（主营）树动态加载的一个标志，根据标志去加载树
    var rootid = '0';
	var loaders = new Com.yucheng.bcrm.ArrayTreeLoader({//异步树加载
		/**指向父节点的属性列*/
		parentAttr : 'PARENT',
		/**虚拟根节点id 如果select的值为root则为根节点*/
		rootValue : "0",
		/**用于展示节点名称的属性列*/
		textField : 'VALUE',
		/**指定节点ID的属性列*/
		idProperties : 'ID'
		/**节点点击事件句柄*/
	});
	  var entMainIndustryTreeForShow = new Com.yucheng.bcrm.TreePanel({//行业分类（主营）
	    	lazyLoad:true,
	    	url : basepath + '/lookupEntIndustry.json?name=PAR2100001',
			width:400,
			autoScroll:true,
			title:'行业分类主菜单（异步加载树）',
			/**虚拟树形根节点*/
			root: new Ext.tree.AsyncTreeNode({
				id:'0',
				text:'行业分类主菜单',
				autoScroll:true,
		        expanded:true,
		        leaf:false,
				children:[]
			}),
			 resloader: loaders,
			 split:true,
			 region : 'center',
			 clickFn:function(node){
	    },
	    	animate : false,
	    	useArrows : false,
	    	border : false
	    });
	var mainView = new Ext.Viewport({
		layout:'border',
		items:[leftTreeForShow,entMainIndustryTreeForShow]
	});	
	
	
	
	
	
});