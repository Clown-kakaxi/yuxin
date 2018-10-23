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
		title:'基础示例树',
		id:'blocMemberTree',
		width:200,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'示例目录',
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
			
    		if(node.attributes.url == '')return;
    		
    		pointUrl = basepath+node.attributes.url;
    		document.getElementById('content').src = pointUrl;
    		
    		
    		Ext.getCmp('tabPanel').activate(0);
    	}
	});

	var tabPanel = new Ext.TabPanel({
		id : 'tabPanel',
		region:'center',
		autoScroll :true,
		activeTab : 0,
		tabWidth  : 400,
		minTabWidth : 400,
		tabPosition : 'bottom',
		items : [ 
          {
        	  title : '演  示',
        	  items : [
        	      {
        	    	  html:'<iframe id="content" name="content" style="width:100%;height:'
        	  			+ 500
        				+ 'px;" frameborder="no"" src=\"'
        				+ pointUrl
        				+ '\" "/> scrolling="auto"> '
        	      }
              ],listeners : {
				'activate' : function(a,b,c) {
        	  		if(nodeId == "")
        	  		{
        	  			return;
        	  		}
				}
			}
          },
          {
        	  title : '源  码',
        	  id:'cont2',
        	  width:1800,
        	  autoScroll : true,
        	  html:'',
			  listeners : {
				'activate' : function(o) {
		        	if(nodeId == "")
		  	  		{
		  	  			return;
		  	  		}
        	  		o.el.dom.innerHTML='<iframe id="content2" name="content2" style="width:100%;height:'
        	  			+ 450
        				+ 'px;" frameborder="no"" src=\"'
        				+ basepath
        				+ '/contents/pages/demo/soundCode.jsp?nodeId='+nodeId+'&nodeName='+nodeName
        				+ '\" "/> scrolling="auto"> ';
				}
			  }
          },
          {
        	  title : '文  档',
        	  id:'cont3',
			  html:'',
	          listeners : {
				'activate' : function(o) {
		        	if(nodeId == "")
		  	  		{
		  	  			return;
		  	  		}
        	  		o.el.dom.innerHTML = '<iframe id="content3" name="content3" style="width:100%;height:'
		  	  			+ 450
		  				+ 'px;" frameborder="no"" src=\"'
		  				+ basepath
		  				+ '/contents/pages/demo/docs/doc1.jsp?nodeId='+nodeId+'&nodeName='+nodeName
		  				+ '\" "/> scrolling="auto"> ';
				}
			  }
          }
		]
	});
	
	var arrLst = new Array();
	var childrens1 = {
		id:'2001',
		sectionId:'2001',
		parentSection:'root',
		sectionName:'导入Excel',
		sectionSummary:'',
		sectionCategory:'示例目录',
		sectionDistribute:"",
		url:'/contents/pages/demo/importExcel.jsp'
	};
	arrLst.push(childrens1);
	var childrens2 = {
		id:'2002',
		sectionId:'2002',
		parentSection:'root',
		sectionName:'导出Excel(CVS)',
		sectionSummary:'',
		sectionCategory:'示例目录',
		sectionDistribute:"",
		url:'/contents/pages/demo/exportExcel.jsp'
	};
	arrLst.push(childrens2);
	var childrens3 = {
		id:'2003',
		sectionId:'2003',
		parentSection:'root',
		sectionName:'附件上传下载',
		sectionSummary:'',
		sectionCategory:'示例目录',
		sectionDistribute:"",
		url:''//'/contents/pages/mxtTest/mxtTestwait.jsp'
	};
	arrLst.push(childrens3);
	var childrens4 = {
		id:'2004',
		sectionId:'2004',
		parentSection:'root',
		sectionName:'网络图组件',
		sectionSummary:'',
		sectionCategory:'示例目录',
		sectionDistribute:"",
		url:'/contents/pages/mxGraph/mxGraphStarChart.jsp'
	};
	arrLst.push(childrens4);
	
	var childrens5 = {
			id:'2005',
			sectionId:'2005',
			parentSection:'root',
			sectionName:'机构选择树',
			sectionSummary:'',
			sectionCategory:'示例目录',
			sectionDistribute:"",
			url:'/contents/pages/mktManage/mktTools/personManager.jsp'
	};
	arrLst.push(childrens5);
		
	var childrens6 = {
			id:'2006',
			sectionId:'2006',
			parentSection:'root',
			sectionName:'机构用户选择器',
			sectionSummary:'',
			sectionCategory:'示例目录',
			sectionDistribute:"",
			url:'/contents/pages/customer/customerManager/customerQuery.jsp'
	};
	arrLst.push(childrens6);
	
	var childrens7 = {
			id:'2007',
			sectionId:'2007',
			parentSection:'root',
			sectionName:'同步和异步加载树',
			sectionSummary:'',
			sectionCategory:'示例目录',
			sectionDistribute:"",
			url:'/contents/pages/demo/treeExamples.jsp'	
	};
	arrLst.push(childrens7);
	
	var childrens8 = {
		id : '2008',
		sectionId : '2008',
		parentSection : 'root',
		sectionName:'单选和多选框树',
		sectionSummary:'',
		sectionCategory : '示例目录',
		sectionDistribute : "",
		url:'/contents/pages/demo/checkBoxTree.jsp'
	};
	arrLst.push(childrens8);
	
	var childrens9 = {
			id : '2009',
			sectionId : '2009',
			parentSection : 'root',
			sectionName:'客户选择放大镜',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:''
		};
	arrLst.push(childrens9);
		
	var childrens10 = {
			id : '2010',
			sectionId : '2010',
			parentSection : 'root',
			sectionName:'树形Grid',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:'/contents/pages/demo/dataGrant.jsp'
		};
	arrLst.push(childrens10);

	var childrens11 = {
			id : '2011',
			sectionId : '2011',
			parentSection : 'root',
			sectionName:'CRUD封装模版',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:'/contents/pages/mktManage/mktTools/channelManager.jsp'
		};
	arrLst.push(childrens11);

	var childrens12 = {
			id : '2012',
			sectionId : '2012',
			parentSection : 'root',
			sectionName:'CRUD原生例子',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:'/contents/pages/demo/originalCRUDdemo.jsp'
		};
	arrLst.push(childrens12);

	var childrens13 = {
			id : '2013',
			sectionId : '2013',
			parentSection : 'root',
			sectionName:'复选下拉框',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:'/contents/pages/demo/demo5.jsp'
		};
	arrLst.push(childrens13);
	
	var childrens14 = {
			id : '2014',
			sectionId : '2014',
			parentSection : 'root',
			sectionName:'主从表关联Form',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:''
		};
	arrLst.push(childrens14);

	var childrens15 = {
			id : '2015',
			sectionId : '2015',
			parentSection : 'root',
			sectionName:'页面格式校验',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:''
		};
	arrLst.push(childrens15);	
	
	var childrens16 = {
			id : '2016',
			sectionId : '2016',
			parentSection : 'root',
			sectionName:'页面元素(按钮、列数据、表单字段)控制',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:''
		};
	arrLst.push(childrens16);

	var childrens17 = {
			id : '2017',
			sectionId : '2017',
			parentSection : 'root',
			sectionName:'智能建议搜索',
			sectionSummary:'',
			sectionCategory : '示例目录',
			sectionDistribute : "",
			url:''
		};
	arrLst.push(childrens17);	
	
	loader.nodeArray =arrLst;
	var children = loader.loadAll();
	leftTreeForShow.appendChild(children);
	leftTreeForShow.expandAll();
	
	
	new Ext.Viewport({
		layout:'border',
        items:[
           leftTreeForShow,
           tabPanel
        ]
	});
	
});