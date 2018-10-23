Ext.onReady(function(){
	var pointUrl = '';//演示页URL
	var sourceUrl = ""; //源码页URL
	var wordUrl = "";// 文档页URL
	var nodeId = "";//节点ID
	var nodeName = "";//节点名称
	var _tempFileName = "";
	var _annaSize = 0;
	

	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'parentSection',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'sectionId',
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : 'root',
		/**用于展示节点名称的属性列*/
		textField : 'sectionName',
		/**指定节点ID的属性列*/
		idProperties : 'sectionId'
		/**节点点击事件句柄*/
	});
	
  //左边的tree的形状
    var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title:'目录树',
		id:'blocMemberTree',
		width:200,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'系统状态监控',
			autoScroll:true,
			children:[]
		}),
		resloader:loader,
		region:'west',
		collapsible: true,
		split:true,
		clickFn:function(node){
	    	nodeId = node.attributes.id;
			nodeName = node.attributes.sectionName;
			if(nodeId == '2001')
			{
				var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
//				Ext.Ajax.request({
//					url:basepath+'/sysStatusMonitoring.json',
//					method: 'GET',
//					success : function(response) {
//						debugger
//						myChart.setJSONData(Ext.util.JSON.decode(response.responseText));
//					}
//				});
				myChart.setJSONUrl(basepath+'/sysStatusMonitoring.json');
				myChart.render(panelView.body.id);
			}
			if(nodeId == '2002')
			{
				var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "myChartId", "100%", "100%", "0", "0");
				myChart.setJSONUrl(basepath+'/sysStatusMonitoringTableSpase.json');
//				myChart.render("content");
				myChart.render(panelView.body.id);
			}
    	}
	});
    var panelView = new Ext.Panel({
    	id : 'panelView',
    	autoScroll :true,
		region:'center'
    });
    
	
	var arrLst = new Array();
	var childrens1 = {
		id:'2001',
		sectionId:'2001',
		parentSection:'root',
		sectionName:'附件空间大小',
		sectionSummary:'',
		sectionCategory:'系统状态监控',
		sectionDistribute:"",
		url:'/contents/pages/systemManager/c2.html'
	};
	arrLst.push(childrens1);
	var childrens2 = {
		id:'2002',
		sectionId:'2002',
		parentSection:'root',
		sectionName:'数据库表空间大小',
		sectionSummary:'',
		sectionCategory:'系统状态监控',
		sectionDistribute:"",
		url:'/contents/pages/systemManager/c3.html'
	};
	arrLst.push(childrens2);
	
	
	loader.nodeArray =arrLst;
	var children = loader.loadAll();
	leftTreeForShow.appendChild(children);
	leftTreeForShow.expandAll();
	
	
	new Ext.Viewport({
		layout:'border',
        items:[
           leftTreeForShow,panelView
           //tabPanel
        ]
	});
	
});