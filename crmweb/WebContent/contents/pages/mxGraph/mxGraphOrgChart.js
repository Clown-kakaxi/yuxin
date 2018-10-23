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
		var h = gco.graph.container.offsetHeight;
		var v1 = gco.graph.insertVertex(gco.graph.getDefaultParent(), 'treeRoot',
			'Organization', 20,h/2- 20, 140, 60, 'image='+mxBasePath+'/editors/images/house.png');
		addOverlays(gco.graph, v1, false);
	};
	
	/**
	 * 进行机构图的布局调整
	 */
	function orgChartLayout(){
		var layout = new mxCompactTreeLayout(gco.graph);
		layout.useBoundingBox = false;
		layout.levelDistance = 40;
		layout.nodeDistance = 16;
		layout.isVertexMovable = function(cell)
		{
			return true;
		};

		var layoutMgr = new mxLayoutManager(gco.graph);

		layoutMgr.getLayout = function(cell)
		{
			if (cell.getChildCount() > 0)
			{
				return layout;
			}
		};

		// Fix for wrong preferred size
		var oldGetPreferredSizeForCell = gco.graph.getPreferredSizeForCell;
		gco.graph.getPreferredSizeForCell = function(cell)
		{
			var result = oldGetPreferredSizeForCell.apply(this, arguments);

			if (result != null)
			{
				result.width = Math.max(120, result.width - 40);
			}

			return result;
		};

		layout.execute(gco.graph.getDefaultParent());//按照layout对象重新布局
	
	}
	
	/**
	 * 增加界面按钮
	 */
	gco.pushToolButton({
		id:'hid',
		text:'机构图',
		handler:function(){
		orgChartLayout();
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
		style[mxConstants.STYLE_SHAPE] = 'label';
		
		style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
		style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_LEFT;
		style[mxConstants.STYLE_SPACING_LEFT] = 54;
		
		style[mxConstants.STYLE_GRADIENTCOLOR] = '#7d85df';
		style[mxConstants.STYLE_STROKECOLOR] = '#5d65df';
		style[mxConstants.STYLE_FILLCOLOR] = '#adc5ff';
		
		style[mxConstants.STYLE_FONTCOLOR] = '#1d258f';
		style[mxConstants.STYLE_FONTFAMILY] = 'Verdana';
		style[mxConstants.STYLE_FONTSIZE] = '12';
		style[mxConstants.STYLE_FONTSTYLE] = '1';
		
		style[mxConstants.STYLE_SHADOW] = '1';
		style[mxConstants.STYLE_ROUNDED] = '1';
		style[mxConstants.STYLE_GLASS] = '1';
		
		style[mxConstants.STYLE_IMAGE] = ''+mxBasePath+'/editors/images/dude3.png';
		style[mxConstants.STYLE_IMAGE_WIDTH] = '48';
		style[mxConstants.STYLE_IMAGE_HEIGHT] = '48';
		style[mxConstants.STYLE_SPACING] = 8;

		// Sets the default style for edges
		style = gco.graph.getStylesheet().getDefaultEdgeStyle();
		style[mxConstants.STYLE_ROUNDED] = true;
		style[mxConstants.STYLE_STROKEWIDTH] = 3;
		style[mxConstants.STYLE_EDGE] = mxEdgeStyle.SideToSide;
		
	};

	/**
	 * 图形整体配置
	 */
	gco.graphSepecialOptions = function(o){
		new mxRubberband(o.graph);
		o.graph.setConnectable(false);	//设置是否可以拖拽的方式连接节点
		o.graph.setAllowDanglingEdges(false);
		o.graph.setEnabled(o.opGrant);
		o.graph.setAutoSizeCells(true);
		o.graph.setPanning(true);
		o.graph.panningHandler.selectOnPopup = false;
		o.graph.setAutoSizeCells(true);
		o.graph.setMultigraph(false);
		o.graph.panningHandler.useLeftButtonForPanning = true;
		o.graph.cellsEditable = false;
		o.graph.setCellsMovable(false);
		if (mxClient.IS_IE){
			new mxDivResizer(o.container);
		}
	};		
	
	/**
	 * 右键菜单内容
	 */
	gco.createPopupMenu = function(o)
	{
		o.graph.panningHandler.factoryMethod = function(menu, cell, evt){
		var model = o.graph.getModel();

		if (cell != null)
		{
			if (model.isVertex(cell))
			{
				menu.addItem('添加子节点', ''+mxBasePath+'/editors/images/overlays/check.png', function()
				{
					addChild(o.graph, cell);
				});
			}

			menu.addItem('编辑节点名称', ''+mxBasePath+'/editors/images/text.gif', function()
			{
				o.graph.startEditingAtCell(cell);
			});

			if (cell.id != 'treeRoot' &&
				model.isVertex(cell))
			{
				menu.addItem('删除节点', ''+mxBasePath+'/editors/images/delete.gif', function()
				{
					deleteSubtree(o.graph, cell);
				});
			}

			menu.addSeparator();
		}

		menu.addItem('合适大小', ''+mxBasePath+'/editors/images/zoom.gif', function()
		{
			o.graph.fit();
		});

		menu.addItem('实际大小', ''+mxBasePath+'/editors/images/zoomactual.gif', function()
		{
			o.graph.zoomActual();
		});

		menu.addSeparator();

		menu.addItem('打印', ''+mxBasePath+'/editors/images/print.gif', function()
		{
			var preview = new mxPrintPreview(o.graph, 1);
			preview.open();
		});

		menu.addItem('Poster Print', ''+mxBasePath+'/editors/images/print.gif', function()
		{
			var pageCount = mxUtils.prompt('Enter maximum page count', '1');

			if (pageCount != null)
			{
				var scale = mxUtils.getScaleForPageCount(pageCount, o.graph);
				var preview = new mxPrintPreview(o.graph, scale);
				preview.open();
			}
		});
		};
	};

	/**
	 * 节点鼠标事件
	 */
	gco.addCellEvents = function(o){
		var iconTolerance = 20;
		o.graph.addMouseListener({
			currentState: null,
			currentIconSet: null,
			mouseDown: function(sender, me){
			},
			mouseMove: function(sender, me){
			},
			mouseUp: function(sender, me) {
			},
			dragEnter: function(evt, state) {
			},
			dragLeave: function(evt, state){
			}
		});
	};
	
	/**
	 * 绘制图形后调用的方法，用于处理特殊逻辑
	 */
	gco.customerfull = function(o){
		if(o.opGrant)
			addDbClkEdit(o);
	};
	/********************************以上重载API方法结束***************************************/
	
	/**
	 * 给节点添加按钮
	 */
	function addOverlays(graph, cell, addDeleteIcon)
	{
		var overlay = new mxCellOverlay(new mxImage(mxBasePath+'/images/add.png', 24, 24), 'Add child');
		overlay.cursor = 'hand';
		overlay.align = mxConstants.ALIGN_CENTER;
		overlay.addListener(mxEvent.CLICK, mxUtils.bind(this, function(sender, evt)
		{
			addChild(graph, cell);
		}));
		
		graph.addCellOverlay(cell, overlay);

		if (addDeleteIcon)
		{
			overlay = new mxCellOverlay(new mxImage(mxBasePath+'/images/close.png', 30, 30), 'Delete');
			overlay.cursor = 'hand';
			overlay.offset = new mxPoint(-4, 8);
			overlay.align = mxConstants.ALIGN_RIGHT;
			overlay.verticalAlign = mxConstants.ALIGN_TOP;
			overlay.addListener(mxEvent.CLICK, mxUtils.bind(this, function(sender, evt)
			{
				deleteSubtree(graph, cell);
			}));
		
			graph.addCellOverlay(cell, overlay);
		}
	};

	/**
	 * 添加子节点
	 */
	function addChild(graph, cell)
	{
		var model = graph.getModel();
		var parent = graph.getDefaultParent();

		model.beginUpdate();
		try
		{
			var vertex = graph.insertVertex(parent, null, '双击编辑名称');
			var geometry = model.getGeometry(vertex);

			// Updates the geometry of the vertex with the
			// preferred size computed in the graph
			var size = graph.getPreferredSizeForCell(vertex);
			geometry.width = size.width;
			geometry.height = size.height;

			// Adds the edge between the existing cell
			// and the new vertex and executes the
			// automatic layout on the parent
			var edge = graph.insertEdge(parent, null, '', cell, vertex);

			// Configures the edge label "in-place" to reside
			// at the end of the edge (x = 1) and with an offset
			// of 20 pixels in negative, vertical direction.
			edge.geometry.x = 1;
			edge.geometry.y = 0;
			edge.geometry.offset = new mxPoint(0, -20);

			addOverlays(graph, vertex, true);
		}
		finally
		{
			model.endUpdate();
		}
	};

	/**
	 * 删除节点（包括子节点）
	 */
	function deleteSubtree(graph, cell)
	{
		// Gets the subtree from cell downwards
		var cells = [];
		graph.traverse(cell, true, function(vertex)
		{
			cells.push(vertex);
			
			return true;
		});

		graph.removeCells(cells);
	};
	
	/**
	 * 双击节点事件
	 */
	function addDbClkEdit(o){
		o.graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, evt){
			if(!o.opGrant){
				return false;
			}
			var cell = evt.getProperty('cell');
			o.graph.startEditingAtCell(cell);
		});
	}
	
	//绘制图形
	gco.showData(false,true);
	orgChartLayout();
	/*********************************************************/
});
