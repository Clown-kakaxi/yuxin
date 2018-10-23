Ext.onReady(function(){
    
	var groupCustNo = _busiId;//parent.document.getElementById("groupNo").value;
	var groupName   = "TEST";//parent.document.getElementById("groupName").value;
	
	var dataArray = [];
	var vertexArray = [];
	Ext.Ajax.request({		
		url:basepath+'/customer-org-chart.json?groupCustNo='+groupCustNo,
		//method:"GET",
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function(response) {
			var jsonObject = Ext.util.JSON.decode(response.responseText);
			dataArray = eval(jsonObject.json.json);
			//绘制图形
			gco.showData(false,false);
			showLegend();
			starChartLayout();			
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '获取集团客户数据出错');
		}
	});	
	
	function showLegend() {
		var content = document.createElement('div');
		content.style.padding = '4px';
		//集团
		var url1 = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/node.gif';
		//正式
		var url2 = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/com.gif';
		//潜在
		var url3 = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/gua.gif';
		//对外担保
		var url4 = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/lat.gif';

		var tb = new mxToolbar(content);

		tb.addItem('集团客户', url1, function(evt)
		{
		});

		tb.addItem('正式客户', url2, function(evt)
		{
		});
		
		tb.addItem('潜在客户', url3, function(evt)
		{
		});

		tb.addItem('对外担保客户', url4, function(evt)
		{
		});

		wnd = new mxWindow('客户图例', content, 0, 0, 200, 66, false);
		wnd.setMaximizable(false);
		wnd.setScrollable(false);
		wnd.setResizable(false);
		wnd.setVisible(true);
	}
	/*******************containPanel**********************/
	var containPanel =new  Ext.Panel({
		height:500,
		width:1000,
		region:'center',
		tbar : new Ext.Toolbar({
			items:[]
		})
	});
	
	/***************************************************/
	var mainGraphPanel = new Ext.Viewport({
		layout : 'fit',
		items : [containPanel]
	 });
	var gco = new GraphCrmObject(containPanel,false,false,true);
	
	
	/************************************************************/
	
	/**
	 * 初始化数据，对数据进行处理
	 */
	gco.initDataFunc = function(o){

		var style1 = new Object();
		var style2 = new Object();
		var style3 = new Object();
		var style4 = new Object();
		style1[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
		style1[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
		style1[mxConstants.STYLE_IMAGE] = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/lat.gif';
		
		style2[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
		style2[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
		style2[mxConstants.STYLE_IMAGE] = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/com.gif';
		
		style3[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
		style3[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
		style3[mxConstants.STYLE_IMAGE] = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/node.gif';
		
		style4[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_IMAGE;
		style4[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
		style4[mxConstants.STYLE_IMAGE] = basepath+'/contents/pages/customer/groupClientManager/customerOrgChart/gua.gif';
		gco.graph.getStylesheet().putCellStyle('imageQZ', style1);
		gco.graph.getStylesheet().putCellStyle('imageZS', style2);
		gco.graph.getStylesheet().putCellStyle('imageJT', style3);
		gco.graph.getStylesheet().putCellStyle('imageDWDB', style4);
		
		var imageStyle = 'imageZS';
		var v0 = {};
		for(var v = 0; v < dataArray.length; v++){
			var vid;
			var vname;
			//正式客户
			if(dataArray[v].IS_POTENTIAL == '1')
				imageStyle = 'imageZS';
			//潜在客户
			if(dataArray[v].IS_POTENTIAL == '2')
				imageStyle = 'imageQZ';
			//担保客户
			if(dataArray[v].MEMBER_TYPE == '2')
				imageStyle = 'imageDWDB';
			vid = dataArray[v].ID;
			vname = dataArray[v].CUST_ZH_NAME;
			
			if (dataArray[v].CUST_ID == groupCustNo) {
				var tB = gco.graph.insertVertex(o.graph.getDefaultParent(),vid, vname,0,0,30,30,'imageJT;rounded=true;strokeColor=none;fillColor=yellow;');
				v0 = tB;
			} else {
				var tB = gco.graph.insertVertex(o.graph.getDefaultParent(),vid, vname,0,0,30,30,imageStyle+';rounded=true;strokeColor=none;fillColor=yellow;');
			}
			vertexArray.push(tB);
		}
		var vFrom = null;
		for(var v = 0; v < vertexArray.length; v++){
			if (dataArray[v].CUST_ID != groupCustNo) {
				if (getCellById(dataArray[v].PARENT_ID)) {
					vFrom = getCellById(dataArray[v].PARENT_ID);
				}			
				var vTo   = vertexArray[v];
				var te = gco.graph.insertEdge(o.graph.getDefaultParent(),'200'+v,'',vFrom,vTo);
			}			
		}		
	};
	
	function getCellById(id) {
		var vertex = null;
		for(var i = 0; i < vertexArray.length; i++){
			vertex = vertexArray[i];
			if (vertex.id == id) {				
				break;
			}
		}
		return vertex;
	}
	
	/**
	 * 进行星形图的布局调整
	 */
	function starChartLayout(){
		var starLayout = new mxStarLayout(gco.graph,200);
		starLayout.execute(gco.graph.getDefaultParent());}
	
	/**
	 * 增加界面按钮
	 */
	gco.pushToolButton({
		id:'hid',
		text:'星形图',
		handler:function(){
		starChartLayout();
		}					
	}); 
	
	gco.pushToolButton({
		text:'放大',
		handler: function(){
			gco.graph.zoomIn();
		}
	});
	gco.pushToolButton({
		text:'缩小',
		handler: function(){
			gco.graph.zoomOut();
		}
	});
	gco.pushToolButton({
		text:'实际大小',
		handler: function(){
			gco.graph.zoomActual();
		}
	});
	gco.pushToolButton({
		text:'合适大小',
		handler: function(){
			gco.graph.fit();
		}
	});
	gco.pushToolButton({
		text:'打印',
		handler: function(){
			var scale = mxUtils.getScaleForPageCount(1, gco.graph);
			var preview = new mxPrintPreview(gco.graph, scale);
			preview.open();
		}
	});
	
	/*******************************重载API方法***************************************/
	
	/**
	 * 设置节点样式
	 */
	gco.setVertexStyle=function(o){
		var style = gco.graph.getStylesheet().getDefaultVertexStyle();
		style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_ELLIPSE;
		style[mxConstants.STYLE_PERIMETER] = mxPerimeter.EllipsePerimeter;
		style[mxConstants.STYLE_GRADIENTCOLOR] = 'white';
	};

	
	/*********************************************************/
});
