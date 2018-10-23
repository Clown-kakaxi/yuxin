Ext.onReady(function(){
    
	
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

		var v0 = gco.graph.insertVertex(o.graph.getDefaultParent(),'1', '中心节点', 0, 0, 40, 30);
		for(var v = 1;v < 9;v++){
			var vid;
			var vname;
			var arrow;
			vid = '100'+v;
			vname = '节点'+v;
			arrow = 1;  //方向为v0指向tB
			var tB = gco.graph.insertVertex(o.graph.getDefaultParent(),vid, vname,0,0,40,30);
			if(arrow == 1){
				var te = gco.graph.insertEdge(o.graph.getDefaultParent(),'200'+v,'边'+v,v0,tB);
			}else{
				var te = gco.graph.insertEdge(o.graph.getDefaultParent(),'200'+v,'边'+v,tB,v0);
			}
		}
	};
	
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

	//绘制图形
	gco.showData(false,false);
	starChartLayout();
	/*********************************************************/
});
