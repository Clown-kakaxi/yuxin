Ext.onReady(function(){
	/************************graphListForm*********************/
	var grapListForm = new Ext.form.FormPanel({
		id : "searchCondition",
		labelWidth : 100,
        frame : true,
        autoScroll : true,
        region : 'north',
        title : '客户关系图查询',
        buttonAlign:"center",
        height:100,
        width: '100%',
        labelAlign:'right',
        layout:'column',
            items : [{
            	columnWidth : .25,
                layout : 'form',
                items : [{
                    xtype : 'textfield',
                    fieldLabel : '关系图名称',
                    id : 'GRAPH_NAME',
                    name : 'GRAPH_NAME',
                    anchor : '90%'
                }]
            },{
                columnWidth : .25,
                layout : 'form',
                items : [{
                    xtype : 'datefield',
                    fieldLabel : '创建日期', 
                    name : 'CREATE_DATE', 
                    format:'Y-m-d', 
                    editable : false,
                    anchor : '90%' 
                }]
        }],
        buttons:[{
            text : '查询',
            xtype:'button',
            handler : function() {
        		var condition = grapListForm.getForm().getValues();
        		graphListStore.load({
        			params : {
        	            start : 0,
        	            limit : parseInt(graphListPagesize.getValue()),
        	            condition : Ext.encode(condition)
        	        }
        	    });
        	}
        },{
            text : '重置',
            xtype:'button',
            handler : function() {}
        }]
    });
	
	/***********************graphListGrid*************************/ 
    var graphListProxy = new Ext.data.HttpProxy({
        url : basepath+'/graphlist.json'
    });
    
    var graphListRecord = Ext.data.Record.create([
        {name: 'ID', mapping: 'ID'},
        {name: 'GRAPH_NAME', mapping: 'GRAPH_NAME'},                                   
        {name: 'GRAPH_DESCRIBE', mapping: 'GRAPH_DESCRIBE'},  
        {name: 'AUTHOR_NAME', mapping: 'AUTHOR_NAME'},
        {name: 'CREATE_ORG_NAME', mapping: 'CREATE_ORG_NAME'},
        {name: 'CREATE_DATE', mapping: 'CREATE_DATE'},
        {name: 'UPDATER_NAME', mapping: 'UPDATER_NAME'},
        {name: 'UPDATE_DATE', mapping: 'UPDATE_DATE'}
    ]);

    var graphListReader = new Ext.data.JsonReader({
        successProperty: 'success',
        idProperty: 'ID',
        messageProperty: 'message',
        totalProperty: 'json.count',
        root : 'json.data'
    },graphListRecord);

    var graphListSm = new Ext.grid.CheckboxSelectionModel();

    var graphListRownum = new Ext.grid.RowNumberer({
        header : 'No.',
        width : 28
    });
    var graphListCm = new Ext.grid.ColumnModel([graphListRownum, graphListSm,
        {
            hidden : true,
            header : '关系图ID',
            dataIndex : 'ID',
            sortable : true,
            width : 120
        },{
            header : '关系图名称', 
            dataIndex : 'GRAPH_NAME', 
            sortable : true,
            width : 120
        },{
            header : '关系图描述',
            sortable : true,
            dataIndex : 'GRAPH_DESCRIBE',
            width : 120
        },{
            header : '创建人', 
            width : 120,
            sortable : true,
            dataIndex : 'AUTHOR_NAME'
        },{
            header : '创建机构',
            width : 120,
            sortable : true,
            dataIndex : 'CREATE_ORG_NAME'
        },{
            header : '创建时间',
            dataIndex : 'CREATE_DATE',
            sortable : true,
            width : 120
        },{
            header : '最近修改人',
            width : 120,
            sortable : true,
            dataIndex : 'UPDATER_NAME'
        },{
          header:'最近修改时间',
          width : 120,
          dataIndex : 'UPDATE_DATE',
          sortable : true,
          hidden:true
        }]);

    var graphListStore = new Ext.data.Store({
        id: 'notice',
        restful : true,     
        proxy : graphListProxy,
        reader : graphListReader,
        recordType:graphListRecord
    });
   
    var graphListPagesize = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
						[ 100, '100条/页' ], [ 250, '250条/页' ],
						[ 500, '500条/页' ] ]
        }),
        valueField : 'value',
        displayField : 'text',
        value: 20,
        editable : false,
        width : 85
    });
    
    var graphListBbar = new Ext.PagingToolbar({
        pageSize : parseInt(graphListPagesize.getValue()),
        store : graphListStore,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',       
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', graphListPagesize]
    });
    graphListPagesize.on("select", function(comboBox) {
    	graphListBbar.pageSize = parseInt(graphListPagesize.getValue()),
        graphListStore.reload({
        	params : {
        		start : 0,
                limit : parseInt(graphListPagesize.getValue())
            }
        });
    });

    graphListStore.load({
        params : {
            start : 0,
            limit : parseInt(graphListPagesize.getValue())
        }
    });
	
    var graphListTbar = new Ext.Toolbar({
        items : [{
            id:'_addNot',
            text : '关系图新增',
            iconCls:'addIconCss',
            handler : function() {
        		store.removeAll();
        		custChoose.setVisible(true);
        		infoPanel.getForm().reset();
        		GraphWindow.show();
        		gco.showData(false,true);
        	}
        },'-',{
        	id:'__upNot',
            text : '关系图修改',
            iconCls:'editIconCss',
            handler : function() {
        		
        		var _record = graphListGrid.getSelectionModel().getSelected();
        			if (!_record) {
        				Ext.MessageBox.alert('修改操作', '请选择要操作的记录！');
        				return false;
        			} else {
        				var checkedNodes = graphListGrid.getSelectionModel().selections.items;
        				if(checkedNodes.length>1){
        					Ext.MessageBox.alert('修改操作', '您选择的记录过多！');
        					return false;
        				}
        				Ext.Ajax.request({
        					url:basepath+'/graphoption/'+_record.data.ID+"/edit.json",
        					method:'GET',
        					success:function(a,b,c){
        						store.removeAll();
        						custChoose.setVisible(true);
        						infoPanel.getForm().reset();
        						GraphWindow.show();
        						gco.showData(Ext.decode(a.responseText),true);
        					},failure:function(a,b,c,d){
        					}
        				});
        			}
        		}
        },'-',{
        	id:'delNot',
            text : '关系图删除',
            iconCls:'deleteIconCss',
            handler : function() {
        		var _record = graphListGrid.getSelectionModel().getSelected();
        		if (!_record) {
        			Ext.MessageBox.alert('修改操作', '请选择要操作的记录！');
        			return false;
        		} else {
        			var checkedNodes = graphListGrid.getSelectionModel().selections.items;
        			var ids;
        			for(var de in checkedNodes){
        				if(checkedNodes[de].data){
        					if(!ids)
        						ids = checkedNodes[de].data.ID;
        					else
        						ids += "-"+checkedNodes[de].data.ID;
        				}
        			}
        			Ext.Ajax.request({
        				url:basepath+'/graphoption/'+ids+"/destroy.json",
        				method:'GET',
        				success:function(a,b,c){
        				graphListStore.load({
        					params : {
        						start : 0,
        						limit : parseInt(graphListPagesize.getValue())
                	        }
                	    });
                  	  },
                  	  failure:function(a,b,c,d){
                  		  graphListStore.load({
                  			  params : {
                  			  	start : 0,
                  	            limit : parseInt(graphListPagesize.getValue())
                  		  	  }
                  		  });
                  	   }
        			});
        		}
        	}
        },'-',{
        	id:'_detailShow',
        	text:'关系图查看',
        	iconCls:'detailIconCss',
        	handler:function(){
    	 		var _record = graphListGrid.getSelectionModel().getSelected();
    	 		if (!_record) {
    	 			Ext.MessageBox.alert('修改操作', '请选择要操作的记录！');
    	 			return false;
    	 		} else {
    	 			var checkedNodes = graphListGrid.getSelectionModel().selections.items;
    	 			if(checkedNodes.length>1){
    	 				Ext.MessageBox.alert('修改操作', '您选择的记录过多！');
    	 				return false;
    	 			}
    	 			Ext.Ajax.request({
    	 				url:basepath+'/graphoption/'+_record.data.ID+"/show.json",
    	 				method:'GET',
    	 				success:function(a,b,c){
    	 					store.removeAll();
    	 					custChoose.setVisible(false);
    	 					infoPanel.getForm().reset();
    	 					GraphWindow.show();
    	 					gco.showData(Ext.decode(a.responseText),false);
    	 				},failure:function(a,b,c,d){
    	 				}
    	 			});
    	 		}
        	}
        }]
    });
    /**关系图列表**/
    var graphListGrid = new Ext.grid.GridPanel({
        title : '关系图列表',
        frame : true,
        store : graphListStore, 
        region : 'center',
        stripeRows : true, 
        tbar : graphListTbar,
        cm : graphListCm,
        sm : graphListSm,
        bbar : graphListBbar,
        viewConfig : {
        },
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
    });	
	/****************************graph viewport******************************************/
    var graphListViewport = new Ext.Viewport({
        layout : 'fit',
        items : [{
            layout : 'border',
            items : [grapListForm,graphListGrid]
        }]
    });
    
	/*********************graph infomation panel*******************************/
	var infoPanel = new Ext.form.FormPanel({
		title : "关系图信息",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		split:true,
		height : 100,
		items : [{
			layout : 'column',
			items : [{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'hidden',
					name : 'id'
				},{
					xtype : 'textfield',
					Width : '100',
					name : 'graphName',
					fieldLabel : '关系图名称',
					anchor : '90%'
				},{
					xtype : 'textfield',
					Width : '100',
					name : 'graphDescribe',
					fieldLabel : '关系图描述',
					anchor : '90%'
				}]
			}]
		}]
	});
	
	/*********************cust search condition********************/
	var custSearchPanel = new Ext.form.FormPanel({
		title : "客户查询",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		split:true,
		height : 125,
		items : [{
			layout : 'column',
			items : [{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					Width : '100',
					name : 'CUST_ID',
					fieldLabel : '客户号',
					anchor : '90%'
				},{
					xtype : 'textfield',
					Width : '100',
					name : 'CUST_ZH_NAME',
					fieldLabel : '客户名称',
					anchor : '90%'
				}]
			}]
		}],
		buttons : [ {
			text : '查询',
			handler : function() {
			var condition = custSearchPanel.getForm().getValues();
			store.load({
				params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue()),
				condition : Ext.encode(condition)
			}
		});
		}
		},{
			text : '重置',
			handler : function() {
			custSearchPanel.getForm().reset();
		}
		} ]
	});
	/*************************custgrid*********************/
	var cm = new Ext.grid.ColumnModel({columns:[
	     {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
	     {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true}
	]});
	
	 var store = new Ext.data.Store({
		 restful:true,	
		 proxy : new Ext.data.HttpProxy({
			 url:basepath+'/customerBaseInformation.json'
		 }),
		 reader: new Ext.data.JsonReader({
			 totalProperty : 'json.count',
			 root:'json.data'
		 }, [
		     {name: 'custId',mapping :'CUST_ID'},
		     {name: 'custZhName',mapping :'CUST_ZH_NAME'}
		 ])
	 });
     var pagesize_combo = new Ext.form.ComboBox({
         name : 'pagesize',
         triggerAction : 'all',
         mode : 'local',
         store : new Ext.data.ArrayStore({
             fields : ['value', 'text'],
             data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ],
					[ 500, '500条/页' ] ]
         }),
         valueField : 'value',
         displayField : 'text',
         value : '20',
         editable : false,
         width : 85
     });
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
    	  store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : parseInt(pagesize_combo.getValue()),
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	
	var custGrid = new Ext.grid.GridPanel({
		width : 350,
		height: 250,
		frame : true,
		autoScroll : true,
		ddGroup : 'custGroup',
		store : store,
		stripeRows : true, 
		enableDragDrop   : true,
		cm : cm,
		bbar:bbar
	});
	/*****************************************************/
	var custChoose = new Ext.Panel({
		width: 350,
		items:[custSearchPanel,custGrid]
	});
	/****************************************************/
	var leftPanel = new Ext.Panel({
		region : 'west',
		layout:'form',
		width: 350,
		items:[infoPanel,custChoose]
	});
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
	var mainGraphPanel = new Ext.Panel({
		layout : 'border',
		items : [leftPanel,containPanel]
	 });
	var GraphWindow = new Ext.Window({
		layout : 'fit',
		draggable : true,//是否可以拖动
		closable : true,// 是否可关闭
		modal : true,
		closeAction : 'hide',
		maximized:true,
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		items:[mainGraphPanel]
	});
	var boxstore8 = new Ext.data.Store({
		sortInfo: {
			field: 'key',
			direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
		},
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=CUS0100038'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var gco = new GraphCrmObject(containPanel,infoPanel,false,false);
	
	gco.customerfull = function(o){
		addCustomers(o);
		if(o.opGrant)
			customerTypeChoose(o);
	};
	/************************************************************/
	gco.initDataFunc = function(o){
		if(!o.graphData){
			return;
		}
		for(var key in o.graphData.graph){
			if(o.iPanel.getForm().findField(key))
				o.iPanel.getForm().findField(key).setValue( o.graphData.graph[key]);
		}
		var tBs = new Array();
		for(var v in o.graphData.vertexes){
			if(o.graphData.vertexes[v].custId){
				var tB = o.graph.insertVertex(o.graph.getDefaultParent(),o.graphData.vertexes[v].custId, o.graphData.vertexes[v].custName,0,0,80,30);
				tBs.push(tB);
			}
		}
		for(var e in o.graphData.edges){
			if(o.graphData.edges[e].id){
				var te = o.graph.insertEdge(o.graph.getDefaultParent(),o.graphData.edges[e].id,o.graphData.edges[e].relationName,getPointBaby(tBs,o.graphData.edges[e].fromVertex),getPointBaby(tBs,o.graphData.edges[e].toVertex));
				te._typecode = o.graphData.edges[e].relationCode;
			}
		}
	}
	
	
	
	/*********************客户关系类型选择***********************/
	function customerTypeChoose(o){
		o.graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, evt){
			if(!o.opGrant){
				return false;
			}
			var cell = evt.getProperty('cell');
			if(o.graph.getModel().isEdge(cell)){
				var typediv = o.container.appendChild(document.createElement('div'));
				typediv.id = "___comb";
				typediv.style.cssText = "position:absolute;width:100px;height:25px;background:#000;";
				typediv.style.top=evt.properties.event.y;
				typediv.style.left=evt.properties.event.x;
				typediv.style.display = "block";
				var typecom = new Ext.form.ComboBox({
					hiddenName : 'HY_CLASS2',
					fieldLabel : '行业',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : boxstore8,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					editable:false,
					emptyText:'请选择',
					resizable : true,
					anchor : '95%'
				});
				var tmpPanel = new Ext.Panel({
					height:25,
					width:150,
					layout:'fit',
					items:[typecom]
				});
				typecom.expand() ;
				typecom.on('select',function(){
					if(typecom.getValue()){
						cell.valueChanged(typecom.lastSelectionText);
							cell._typecode = typecom.getValue();
							o.graph.refresh(cell);
					}
					tmpPanel.destroy();
					typediv.parentNode.removeChild(typediv);
				});
				typecom.on('blur',function(){
					typediv.parentNode.removeChild(typediv);
				});
				if(!tmpPanel.rendered)
					tmpPanel.render(typediv);
				typecom.focus();
			}
		});	
	}
	/****************************数据拖动************************/
	function addCustomers(o){
		var firstGridDropTargetEl =  o.graph.container;
		var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
			ddGroup    : 'custGroup',
			notifyDrop : function(ddSource, e, data){
				var gf = o.graphIn(e.browserEvent);
				if(getPointBaby(o.graph.getModel().cells,data.selections[0].data.custId)){
					Ext.Msg.alert("错误","该客户已存在");
					return false;
				}
				if(gf){
					gf.insertVertex(o.graph.getDefaultParent(), data.selections[0].data.custId, data.selections[0].data.custZhName, e.browserEvent.offsetX, e.browserEvent.offsetY, 80, 30);
				}
				return true;
			}
		});
	}
	
	/******************************************************/
	gco.pushToolButton({
		id:'hid',
		text:'树形布局',
		iconCls:'treeIconCss',
		handler:function(){
			var layout = new mxHierarchicalLayout(gco.graph);
			layout.execute(gco.graph.getDefaultParent());
		}					
	}); 
	gco.pushToolButton({
		id:'on',
		text:'有机布局',
		iconCls:'youJiIconCss',
		handler:function(){
			var organic = new mxFastOrganicLayout(gco.graph);
			organic.forceConstant = 120;
			organic.execute(gco.graph.getDefaultParent());
		}					
	}); 
	gco.pushToolButton({
		id:'cir',
		text:'圆形布局',
		iconCls:'circleIconCss',
		handler:function(){
			var circleLayout = new mxCircleLayout(gco.graph);
			circleLayout.radius = 100;
			circleLayout.execute(gco.graph.getDefaultParent());
		}
	});
	gco.pushToolButton({
		text:'放大',
		iconCls:'searchMaxMirrorIconCss',
		handler: function(){
			gco.graph.zoomIn();
		}
	});
	gco.pushToolButton({
		text:'缩小',
		iconCls:'searchMinMirrorIconCss',
		handler: function(){
			o.graph.zoomOut();
		}
	});
	gco.pushToolButton({
		text:'实际大小',
		iconCls:'realDaXiaoIconCss',
		handler: function(){
			gco.graph.zoomActual();
		}
	});
	gco.pushToolButton({
		text:'合适大小',
		iconCls:'fitDaXiaoIconCss',
		handler: function(){
			gco.graph.fit();
		}
	});
	gco.pushToolButton({
		text:'打印',
		iconCls:'printIconCss',
		handler: function(){
			var scale = mxUtils.getScaleForPageCount(1, gco.graph);
			var preview = new mxPrintPreview(gco.graph, scale);
			preview.open();
		}
	});
	gco.pushToolButton({
		text:'保存',
		iconCls:'saveIconCss',
		withGrant:'disabled',// 接受权限约束的属性
		handler :function(){
			var graphInfo = gco.iPanel.getForm().getValues();
			var vertexes = new Array();
			var edges = new Array();
			var model = gco.graph.getModel();
			for(var i in model.cells){
				if(model.isVertex(model.cells[i]))
					if(model.cells[i].edges){
						var ver = {};
						ver.custId = model.cells[i].id;
						ver.custName = model.cells[i].value;
						if(graphInfo.id)
							ver.graphId = graphInfo.id;
						vertexes.push(ver);
					}
			}
			for(var i in model.cells){
				if(model.isEdge(model.cells[i])){
					var edg = {};
					edg.fromVertex = model.cells[i].source.id;
					edg.toVertex = model.cells[i].target.id;
					if( model.cells[i].value){
						edg.relationCode = model.cells[i]._typecode;
						edg.relationName = model.cells[i].value;
					}
					if(graphInfo.id)
							edg.graphId = graphInfo.id;
					edges.push(edg);
				}
			}
			var infos = {
					"vertexes" : vertexes,
					"edges" : edges,
					"graph" : graphInfo
			};
			if(graphInfo.id){
				Ext.Ajax.request({
					url:basepath+'/graphoption/'+graphInfo.id+'/update.json',
					method : 'POST',
					params : {
						infos : Ext.encode(infos)
					},
					success :function(){
						Ext.Msg.alert("操作提示","保存成功");
					},failure:function(){
						
					}
				});
			} else {
				Ext.Ajax.request({
					url:basepath+'/graphoption.json',
					method : 'POST',
					params : {
						infos : Ext.encode(infos)
					},
					success :function(a,b,c,d){
						Ext.Ajax.request({
							url: basepath+ '/graphlist!getPid.json',
							method:'GET',
							success:function(a){
								gco.iPanel.getForm().findField('id').setValue(Ext.decode(a.responseText).pid);
								Ext.Msg.alert("操作提示","保存成功");
							}
						});
					},failure:function(){
						
					}
				});
			}
		}
	});
});
