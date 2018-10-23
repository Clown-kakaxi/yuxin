
/**
 * @describe Set the icon value , and repair the image show. 
 * 	Wait ...
 */
function setIcon(id)
{
	Ext.ComponentMgr.all.map.icons.setValue(id);
	var tImp = document.createElement('img');
	tImp.src = basepath+id;
	if(Ext.ComponentMgr.all.map.icons.el.dom.parentNode.childNodes.length>1){
		Ext.ComponentMgr.all.map.icons.el.dom.parentNode.removeChild(Ext.ComponentMgr.all.map.icons.el.dom.parentNode.childNodes[1]);
	}
	Ext.ComponentMgr.all.map.icons.el.dom.parentNode.appendChild(tImp);
}
Ext.onReady(function(){

	var asStore = new Ext.data.ArrayStore({
	    fields:['myId','displayText'],
	    data:[['0','否'],['1','是']]
	});
	
	
/******************************************************************************************************************/
    var store = new Ext.data.ArrayStore({
        proxy   : new Ext.data.MemoryProxy(),
        fields  : ['id', 'name'],
        sortInfo: {
            field    : 'name',
            direction: 'ASC'
        }
    });
    store.loadData ([
	                             
	                             ['/images/fw/icon_menu_management.gif', '图标1'],
	                             ['/images/fw/icon_menu_menu_management.gif', '图标2'],
		                         ['/images/fw/icon_menu_report_management.gif', '图标3'],
			                     ['/images/fw/icon_menu_template_manageme.gif', '图标4'],
			                     ['/images/fw/icon_menu_182.gif', '图标5'],
			                     ['/images/fw/icon_menu_016.gif', '图标6'],
			                     ['/images/fw/icon_menu_072.gif', '图标7'],
			                     ['/images/fw/icon_menu_219.gif', '图标8'],
			                     ['/images/fw/icon_menu_202.gif', '图标9'],
			                     ['/images/fw/icon_menu_075.gif', '图标10'],
			                     ['/images/fw/icon_menu_093.gif', '图标11'],
			                     ['/images/fw/icon_menu_284.gif', '图标12'],
			                     ['/images/fw/icon_menu_274.gif', '图标13'],
			                     ['/images/fw/icon_menu_065.gif', '图标14'],
			                     ['/images/fw/icon_menu_071.gif', '图标15'],
			                     ['/images/fw/icon_menu_248.gif', '图标16'],
			                     ['/images/fw/icon_menu_285.gif', '图标17'],
			                     ['/images/fw/icon_menu_286.gif', '图标18'],
			                     ['/images/fw/icon_menu_027.gif', '图标19'],
			                     ['/images/fw/icon_menu_107.gif', '图标20'],
			                     ['/images/fw/icon_menu_108.gif', '图标21'],
			                     ['/images/fw/icon_menu_157.gif', '图标22'],
			                     ['/images/fw/icon_menu_229.gif', '图标23'],
			                     ['/images/fw/icon_menu_304.gif', '图标24'],
			                     ['/images/fw/icon_menu_311.gif', '图标25'],
				                 ['/images/fw/icon_menu_020.gif', '图标26'],
				                 ['/images/fw/icon_menu_156.gif', '图标27'],
				                 ['/images/fw/icon_menu_326.gif', '图标28'],
				                 ['/images/fw/icon_menu_325.gif', '图标29'],
				                 ['/images/fw/icon_menu_020.gif', '图标30'],
				                 ['/images/fw/icon_menu_027.gif', '图标31'],
				                 ['/images/fw/icon_menu_041.gif', '图标32'],
				                 ['/images/fw/icon_menu_051.gif', '图标33'],
				                 ['/images/fw/icon_menu_072.gif', '图标34'],
				                 ['/images/fw/icon_menu_190.gif', '图标35'],
				                 ['/images/fw/icon_menu_254.gif', '图标36'],
				                 ['/images/fw/icon_menu_255.gif', '图标37']


	                         ]);
    
	Ext.namespace('Ext.exampledata');

	Ext.exampledata.states = [
	                             ['images/fw/icon_menu_management.gif', '图标1'],
	                             ['images/fw/icon_menu_menu_management.gif', '图标2'],
		                         ['images/fw/icon_menu_report_management.gif', '图标3'],
			                     ['images/fw/icon_menu_template_manageme.gif', '图标4'],
			                     ['images/fw/icon_menu_182.gif', '图标5'],
			                     ['images/fw/icon_menu_016.gif', '图标6'],
			                     ['images/fw/icon_menu_072.gif', '图标7'],
			                     ['images/fw/icon_menu_219.gif', '图标8'],
			                     ['images/fw/icon_menu_202.gif', '图标9'],
			                     ['images/fw/icon_menu_075.gif', '图标10'],
			                     ['images/fw/icon_menu_093.gif', '图标11'],
			                     ['images/fw/icon_menu_284.gif', '图标12'],
			                     ['images/fw/icon_menu_274.gif', '图标13'],
			                     ['images/fw/icon_menu_065.gif', '图标14'],
			                     ['images/fw/icon_menu_071.gif', '图标15'],
			                     ['images/fw/icon_menu_248.gif', '图标16'],
			                     ['images/fw/icon_menu_285.gif', '图标17'],
			                     ['images/fw/icon_menu_286.gif', '图标18'],
			                     ['images/fw/icon_menu_027.gif', '图标19'],
			                     ['images/fw/icon_menu_107.gif', '图标20'],
			                     ['images/fw/icon_menu_108.gif', '图标21'],
			                     ['images/fw/icon_menu_157.gif', '图标22'],
			                     ['images/fw/icon_menu_229.gif', '图标23'],
			                     ['images/fw/icon_menu_304.gif', '图标24'],
			                     ['images/fw/icon_menu_311.gif', '图标25'],
				                 ['images/fw/icon_menu_020.gif', '图标26'],
				                 ['images/fw/icon_menu_156.gif', '图标27'],
				                 ['images/fw/icon_menu_326.gif', '图标28'],
				                 ['images/fw/icon_menu_325.gif', '图标29'],
				                 ['images/fw/icon_menu_020.gif', '图标30'],
				                 ['images/fw/icon_menu_027.gif', '图标31'],
				                 ['images/fw/icon_menu_041.gif', '图标32'],
				                 ['images/fw/icon_menu_051.gif', '图标33'],
				                 ['images/fw/icon_menu_072.gif', '图标34'],
				                 ['images/fw/icon_menu_190.gif', '图标35'],
				                 ['images/fw/icon_menu_254.gif', '图标36'],
				                 ['images/fw/icon_menu_255.gif', '图标37']


	                         ];
	
	
	/******************************************************************************************************************/

	  var iconStore = new Ext.data.ArrayStore({
	        fields: ['myId', 'displayText'],
	        data : Ext.exampledata.states // from states.js
	    });
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
 //		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'PARENT_ID',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'ID',
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : "0",
		/**用于展示节点名称的属性列*/
		textField : 'NAME',
		/**指定节点ID的属性列*/
		idProperties : 'ID'
		/**节点点击事件句柄*/
	});
	Ext.Ajax.request({
		url : basepath + '/indexinit.json',
		method:'GET',
		success:function(response){
	//alert(response.responseText);
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			Ext.each(nodeArra.json.data,function(n){
				if(n.ICON){
					n.icon = basepath+n.ICON;
				}
			});
			
			loader.nodeArray = nodeArra.json.data;
			var children = loader.loadAll();
			leftTreeForShow.appendChild(children);
		}
	});
	var loader1 = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'PARENT_ID',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'ID',
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : '0',
		/**用于展示节点名称的属性列*/
		textField : 'MDUL_NAME',
		/**指定节点ID的属性列*/
		idProperties : 'ID'
		/**节点点击事件句柄*/
	});
	Ext.Ajax.request({
		url : basepath + '/fwFunction-Action.json',
		method:'GET',
		success:function(response){
//		alert(response.responseText);
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
		

			loader1.nodeArray = nodeArra;
			var children = loader1.loadAll();
			leftTreeForShows.appendChild(children);
		}
	});
	 //左边新增栏目面板下拉框
	var comboxWithTree = new Ext.form.ComboBox({
		id : 'parentName', 
		xtype:'combo',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		labelStyle: 'text-align:left;',
		editable : false,
		emptyText : '请选择...',
		fieldLabel : '上层菜单选择',
		anchor : '95%',
		mode : 'local',
		resizable :false,
		forceSelection:true,
		name:'parentId',
		triggerAction : 'all',
		maxHeight : 390,
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv_1'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
			leftTreeForShow2.render('addDeptTreeDiv_1');
			},
			'collapse':function(combo){
			}
		}
	});
	 //左边新增栏目面板下拉框
	var comboxWithTrees = new Ext.form.ComboBox({
		id : 'fwname', 
		xtype:'combo',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		labelStyle: 'text-align:left;',
		editable : false,
		emptyText : '请选择...',
		fieldLabel : '功能菜单选择',
		anchor : '95%',
		mode : 'local',
		resizable :false,
		forceSelection:true,
		name:'modFuncId',
		triggerAction : 'all',
		maxHeight : 390,
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv_1s'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
			leftTreeForShows.render('addDeptTreeDiv_1s');
			},
			'collapse':function(combo){
			}
		}
	});
	
	var fwColumns = new Ext.grid.ColumnModel([//gridtable中的列定义
	        {header :'模块ID',dataIndex:'moduleId',id:"moduleId",width:100,hidden:true,sortable : true},
	                                               	{header:'模块名称',dataIndex:'mdulName',id:'mdulName',sortable : true,width:150},
	                                               	{header:'功能ID',dataIndex:'id',id:'id',width:150,sortable : true,hidden:true},
	                                               	{header : '功能名称',dataIndex : 'funcName',id : 'funcName',width : 150,sortable : true}
	                                               	]);
	
	var fwRecord = new Ext.data.Record.create([	
	                                                	{name:'moduleId',mapping:'MODULE_ID'},
	                                                	{name:'mdulName',mapping:'MDUL_NAME'},
	                                                	{name:'id',mapping:'ID'},
	                                                	{name:'funcName',mapping:'FUNC_NAME'}
	                                                	
	                                                	]);
	var fwReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
		},fwRecord);
	var fwInfoStore = new Ext.data.Store(
			{
				proxy:new Ext.data.HttpProxy({
				url:basepath+'/fwFunction-Action.json',
//				success : function(response){
//					alert(response.responseText);
//				},
				failure : function(response){
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示', response.responseText);
					}
				},
				method:'GET'
				}),
				reader:fwReader
			}
			);
	fwInfoStore.load();
	var cntMenuGrid =  new Ext.grid.GridPanel({//产品列表数据grid
		title:'模块功能选择',
		height : 390,
		layout:'fit',
		ddGroup     : 'gridDDGroup',
		id:'cntMenuGrid',
		enableDragDrop   : true,
		store:fwInfoStore,
		loadMask:true,
		cm :fwColumns,
    	//bbar:sbbars,
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }

	});
	

	    var leftTreeForShow2 = new Com.yucheng.bcrm.TreePanel({
	    	title:'上层菜单选择',
			ddGroup: 'gridDDGroups',
			enableDD  : true,

			width:200,
			autoScroll:true,
			layout:'fit',
			
			/**虚拟树形根节点*/
			root: new Ext.tree.AsyncTreeNode({
				id:'root',
				text:'主菜单',
				autoScroll:true,
		        expanded:true,
		        leaf:false,
				children:[]
			}),
			 resloader: loader,
			 split:true,
			 clickFn:function(node){
	            Ext.getCmp('parentName').setValue(node.text);
	            debugger;
	            Ext.getCmp('parentId').setValue(node.id);
			},
		    animate : false,
		    useArrows : false,
		    border : false
		});
	    var dataView = new Ext.DataView({
	        store: store,
	        id: 'iconss',
	        tpl  : new Ext.XTemplate(
	            '<ul>',
	                '<tpl for=".">',
	                    '<li class="icon" onclick="setIcon(\'{[values.id]}\')" style="float:left;margin-left:12px;margin-right:12px;margin-top:10px;">',
	                    '<img src= "'+basepath+'/{[values.id]}" />',
	                    '</li>',
	                '</tpl>',
	            '</ul>'
	        ),

	        
	        itemSelector: 'li.icon',
	        overClass   : 'phone-hover',
	        singleSelect: true,
	        //multiSelect : true,
	        autoScroll  : true
	       // overClass:'x-view-over',//鼠标悬停item时的类样式,defaults to undefined
            //itemSelector:'div.thumb-wrap'//必须项,值为item选择器,此值也可为.thumb-wrap  e.g. div.some-class
    

    });

	  var iconView = new Ext.Panel({
	        title: '图标',
	        id :'iconView',
			ddGroup  : 'gridDDGroups1',
			enableDD  : true,
	        layout: 'fit',
	        items : dataView,
	        height: 700,
	        width: 300
	    });

	    var accordion = new Ext.Panel({
	        region:'west',
	        margins:'5 10 5 0',
	        split:true,
	        width: 280,
	        layout:'accordion',
	        items: [ cntMenuGrid,leftTreeForShow2,iconView]
	    });
	    
	  

	//右边新增栏目面板
	var tbar = new Ext.Toolbar({
		items : [ {
			text : '新增',
			handler:function(){
	           addtitle.getForm().reset();
				var newwindow_1 = new Ext.Window({
						layout : 'fit',
						width : 800,
						height : 500,
						closable : false,
						resizable : false,
						collapsible : false,
						//maximizable : true,
						//maximized : true,
						closeAction : 'hide',
						closable : true,
						constrain : true,
						modal : true,
						autoScroll : true,
						animCollapse : false,
						border : false,

						animateTarget : Ext.getBody(),
						constrain : true,
						buttonAlign:'center',
					//	items : [accordion,addtitles],
						
						items : [ {
							layout : 'column',
							border : false,
							items : [ {
								columnWidth : .35,
								layout : 'form',
								border : false,
								items : [ accordion ]
							}, {
								columnWidth : .65,
								layout : 'form',
								border : false,
								items : [ addtitles]
							} ]
						} ],
						buttons:[{
								text:'新增 ',
								handler:function(){
							fwInfoStore.load();

							/*	if(!addtitle.getForm().isValid()){ 
			                       Ext.MessageBox.alert('提示','输入有误,请检查输入项');
			                       return false;
			                    }*/
							var t = Ext.getCmp("parentIds").getValue();
debugger;		        var m =  Ext.getCmp("modFuncIds").getValue();

								Ext.Ajax.request({
									url : basepath + '/CntMenu-action.json',
									method : 'POST',
									params : {
									'parentId' : Ext.getCmp("parentIds").getValue(),
									'name' : Ext.getCmp("names").getValue(),
									'modFuncId' : Ext.getCmp("modFuncIds").getValue(),
									'issamewin' : Ext.getCmp("issamewins").getValue(),
									'order_' : Ext.getCmp("orders").getValue(),
									'icon' : Ext.getCmp("icons").getValue()


									

									//'sectionCategory' :Ext.getCmp('sectionCategory').getValue()
									},
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									scope : this,
									success : function() {
										 Ext.Ajax.request({
								         url: basepath +'/CntMenu-action!getPid.json',
									         success:function(response){
									        	 var node = {};
									        	 node.id = Ext.util.JSON.decode(response.responseText).pid;
									        	 node.name = Ext.getCmp("name").getValue();
									        	 node.parentId = Ext.getCmp("parentId").getValue();
									        	 leftTreeForShow.addNode(node);
									        	 /*if(leftTreeForShow1.rendered)
									        		 leftTreeForShow1.addNode(node);
									        	 if(leftTreeForShow2.rendere)
									        		 leftTreeForShow2.addNode(node);*/
										 	}
										 });
										 Ext.Msg.alert('提示', '操作成功');
									},
									failure : function() {
										Ext.Msg.alert('提示', '操作失败');
									}
							        }
								  );
								  newwindow_1.hide();
								}
							},{
      				  			text: '关闭',
       				  			handler:function(){
        			 		    newwindow_1.hide();
        						}
   			 				}]
					});
					newwindow_1.show();
					  
				 

				    var formPanelDropTargetEl =  addtitles.getForm().findField('funcNames').getEl().dom;
				    

					var formPanelDropTarget = new Ext.dd.DropTarget(formPanelDropTargetEl, {
						ddGroup     : 'gridDDGroup',
						notifyEnter : function(ddSource, e, data) {

							//Add some flare to invite drop.
						addtitles.body.stopFx();
						addtitles.body.highlight();
						},
						notifyDrop  : function(ddSource, e, data){

							// Reference the record (single selection) for readability
							var selectedRecord = ddSource.dragData.selections[0];
							debugger;
							 Ext.getCmp('modFuncIds').setValue((selectedRecord.data.id));
						    Ext.getCmp('funcNames').setValue((selectedRecord.data.funcName));
			    	   		 Ext.getCmp('parentNames').focus();

				
							return(true);
						}
					});
    var formPanelDropTargetE2 = addtitles.getForm().findField('parentNames').getEl().dom;
				    

					var formPanelDropTarget = new Ext.dd.DropTarget(formPanelDropTargetE2, {
						ddGroup : 'gridDDGroups',
						notifyEnter : function(ddSource, e, data) {

							//Add some flare to invite drop.
						addtitles.body.stopFx();
						addtitles.body.highlight();
						},
						notifyDrop  : function(ddSource, e, data){

							// Reference the record (single selection) for readability
							var selectedRecord = ddSource.dragData.node;
							debugger;
							 Ext.getCmp('parentIds').setValue((selectedRecord.attributes.id));
							 Ext.getCmp('parentNames').setValue((selectedRecord.attributes.NAME));

			    	   		 Ext.getCmp('icons').focus();

							return(true);
						}
					});

					

					}
		},'-', {
			text : '修改',
			handler : function() {
				var record = leftTreeForShow.getSelectionModel().getSelectedNode();
				debugger;
				if (Ext.isEmpty(record)) {
					Ext.MessageBox.alert('提示', '请先选择要修改菜单项!');
				}else if(record.id == 'root')
				{
					Ext.MessageBox.alert('提示', '根节点不能修改 !');

				}else{
				
					Ext.getCmp("ids").setValue(record.attributes.id);
					Ext.getCmp("names").setValue(record.attributes.NAME);
					Ext.getCmp("modFuncIds").setValue(record.attributes.MOD_FUNC_ID);
					Ext.getCmp("funcNames").setValue(record.attributes.FUNC_NAME);
					Ext.getCmp("parentIds").setValue(record.attributes.PARENT_ID);
					Ext.getCmp("parentNames").setValue(record.attributes.PARENT_NAME);
					Ext.getCmp("orders").setValue(record.attributes.ORDER_);
					 
					 var i = asStore.find('myId',record.attributes.ISSAMEWIN);//反显出汉字，不是码值
						if(i!=-1){
						
							
							Ext.getCmp('issamewins').setValue(asStore.getAt(i).get('displayText'));
						}
						else
						{
							Ext.getCmp('issamewins').setValue((asStore.attributes.ISSAMEWIN));
						}
						 
						 if(record.attributes.icon != undefined ){
						
			
								Ext.getCmp('icons').setValue((record.attributes.ICON));

							
						 }
               	var store = Ext.data.Store;
					var newwindow_1 = new Ext.Window({
						layout : 'fit',
						width : 800,
						height : 500,
						closable : true,
						draggable : true,
						title : '修改菜单',
						closeAction : 'hide',
						titleCollapse : true,
						modal : true, // 模态窗口 
						animCollapse : false,
						border : false,
						animateTarget : Ext.getBody(),
						constrain : true,
						buttonAlign:'center',
						items : [ {
							layout : 'column',
							border : false,
							items : [ {
								columnWidth : .35,
								layout : 'form',
								border : false,
								items : [ accordion ]
							}, {
								columnWidth : .65,
								layout : 'form',
								border : false,
								items : [ addtitles]
							} ]
						} ],
						buttons:[{
							text:'修改',
							handler:function(){
							debugger;
							if(Ext.getCmp("parentId").getValue()==Ext.getCmp('id').getValue()){
								Ext.Msg.alert('提示', '当前菜单不能做为上层菜单!否则会出现未知错误');
								return false;
							    }else{
									if(!addtitles.getForm().isValid()){ 
									 Ext.MessageBox.alert('提示','输入有误,请检查输入项');
									 return false;
									}
									Ext.Ajax.request({
									url : basepath + '/CntMenu-action!update_new.json',
									method : 'POST',
									params : {
									'name' : Ext.getCmp("names").getValue(),
									'parentId':Ext.getCmp("parentIds").getValue(),
									'id' :Ext.getCmp('ids').getValue(),
									'icon':Ext.getCmp('icons').getValue(),
									'order':Ext.getCmp('orders').getValue(),
									'modFuncId':Ext.getCmp('modFuncIds').getValue(),
									'issamewin':Ext.getCmp('issamewins').getValue()

									},
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									//修改文档的目录
									scope : this,
									success : function() {
					
											 Ext.Msg.alert('提示', '操作成功');
											 custGroupStore.load();
											 var node = {};
											 node.id = Ext.getCmp("ids").getValue();
								        	 node.text = Ext.getCmp("names").getValue();
								        	 node.parentId = Ext.getCmp("parentIds").getValue();
								        	 node.icon = Ext.getCmp('icons').getValue(),
                                             node.modFuncId = Ext.getCmp('modFuncIds').getValue(),
                                             node.order = Ext.getCmp('orders').getValue(),
                                             node.issamewin = Ext.getCmp('issamewins').getValue(),

								        	 leftTreeForShow.editNode(node);
								        	 //leftTreeForShow1.editNode(node);
								        	// leftTreeForShow2.editNode(node);
									    },  
										    failure : function() {
											Ext.Msg.alert('提示', '操作失败');
									        }
									
							        }
								  );
								
							}


							  newwindow_1.hide();
							  
								 

							    var formPanelDropTargetE3 =  addtitles.getForm().findField('funcNames').getEl().dom;
							    

								var formPanelDropTarget = new Ext.dd.DropTarget(formPanelDropTargetE3, {
									ddGroup     : 'gridDDGroup',
									notifyEnter : function(ddSource, e, data) {

										//Add some flare to invite drop.
									addtitles.body.stopFx();
									addtitles.body.highlight();
									},
									notifyDrop  : function(ddSource, e, data){

										// Reference the record (single selection) for readability
										var selectedRecord = ddSource.dragData.selections[0];
										debugger;
										 Ext.getCmp('modFuncIds').setValue((selectedRecord.data.id));
									    Ext.getCmp('funcNames').setValue((selectedRecord.data.funcName));

							
										return(true);
									}
								});		  
							  
							  
							  
							  
							  
							}
						},{
      				  		text: '取消',
       				  		handler:function(){
        			 		newwindow_1.hide();
        					}
   			 			}]
					});
					newwindow_1.show();}
			}
	    	},'-',{
			text:'删除',
			handler:function(){
			var record = leftTreeForShow.getSelectionModel().getSelectedNode();
			if (Ext.isEmpty(record)) {
				Ext.MessageBox.alert('提示', '请先选择要删除的菜单!');
			}else{
					Ext.Msg.confirm(
							'请确认',
							'确定删除么?',
							function(btn, text) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : basepath + '/CntMenu-action!batchDestroy.json',
										method : 'POST',
										params : {
										'id' : Ext.getCmp("id").getValue()
										},
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										scope : this,
										success : function() {
								        	 leftTreeForShow.deleteNode(record);
								        	 Ext.Msg.alert('提示', '操作成功');
								        	
										},
										    failure : function() {
											Ext.Msg.alert('提示', '操作失败');
										}
								        }
									  );
									}
								});
					}
				}
			}]
});
	
	
	
	
	var addtitle=new Ext.form.FormPanel({
		id:'addtitle',
		title : '菜单设置',
	    frame:true,
        region:'north',
        bodyStyle:'padding:5px 5px 0',
        width: '100%',
      	height:380,
      	autoScroll : true,
		split:true,
         items: [
    		        	{
    			            items :[
    			            	{  
    			            		layout:'column',
    			                     items:[
    			                     	{
    			                        columnWidth:.50,
    		                         	layout: 'form',
    		                         	items: [
{
  	 //allowBlank: false,
	 id:'id',
	 xtype:'textfield',
    fieldLabel: '当前用户ID',
    name: 'name',
    hidden : true,
    anchor:'95%'
},{
   	 allowBlank: false,
   	 id:'name',
   	 xtype:'textfield',
        fieldLabel: '名称',
        readOnly:true,
        name: 'name',
       anchor:'95%'
    }, 
   // comboxWithTree,
    {
        id:'icon',
        xtype:'textfield',
        fieldLabel: '图标',
        readOnly:true,
        name: 'icon',
        anchor:'95%'

       // hidden:true
   },{
   	id:'order',
   	name:'order',
    xtype:'textfield',
    readOnly:true,
   	fieldLabel: '排序',
    anchor:'95%'
   	//hidden:true
   },{
   	id:'issamewin',
   	name:'issamewin',
   	 xtype:'textfield',
   	 readOnly:true,
   	fieldLabel: '是否新窗口打开',
    anchor:'95%'

   	//hidden:true
   },{
   	id:'modFuncId',
   	name:'modFuncId',
   	 xtype:'textfield',
   	 readOnly:true,
   	fieldLabel: '功能模块选择',
   	hidden:true,
    anchor:'95%'

   },{
	   	id:'funcName',
	   	name:'funcName',
	   	 xtype:'textfield',
	   	 readOnly:true,
	   	fieldLabel: '功能模块选择',
	    anchor:'95%'

	   },{
   	id:'parentId',
   	name:'parentId',
   	 xtype:'textfield',
   	 readOnly:true,
   //	 allowBlank : false,
   	fieldLabel: '上层菜单选择',
    anchor:'95%',
    hidden : true
	   },{
		   	id:'parentName',
		   	name:'parentName',
		   	 xtype:'textfield',
		   	 readOnly:true,
		   	 allowBlank : false,
		   	fieldLabel: '上层菜单选择',
		    anchor:'95%'
		   // hidden : true
			   }             
    			                     	   ]}
    			                     	]}
    			            	]}
    		        	]}
    			                     	
    			                     
 
	);
	
	var addtitles=new Ext.form.FormPanel({
		id:'addtitles',
		title : '菜单设置',
	    frame:true,
        region:'north',
        bodyStyle:'padding:5px 5px 0',
        width: 500,
      	height:500,
      	autoScroll : true,
		split:true,
         items: [
    		        	{
    			            items :[
    			            	{  
    			            		layout:'column',
    			                     items:[
    			                     	{
    			                        columnWidth:.80,
    		                         	layout: 'form',
    		                         	items: [
{
  	 //allowBlank: false,
	 id:'ids',
	 xtype:'textfield',
    fieldLabel: '当前用户ID',
    name: 'id',
    hidden : true,
    anchor:'95%'
},{
   	 allowBlank: false,
   	 id:'names',
   	 xtype:'textfield',
        fieldLabel: '名称',
        name: 'name',
       anchor:'95%'
    }, 
   // comboxWithTree,
    {
       	id:'modFuncIds',
       	name:'modFuncId',
       	 xtype:'textfield',
      // 	fieldLabel: '功能模块选择',
       	 hidden : true,
        anchor:'95%',
  
      	hidden:true
       },{
    	   	id:'funcNames',
    	   	name:'funcName',
    	   	 xtype:'textfield',
    	   	fieldLabel: '功能模块选择',
    	    anchor:'95%',
    	      listeners:{
    	   		focus:function(a){
    	   			accordion.layout.setActiveItem(cntMenuGrid);
    	   		// Ext.getCmp('parentNames').focus();
       			}
       		}
    	 //  	hidden:true
    	   },{
    	      	 allowBlank: false,
    	      	 id : 'parentIds',
    	      	 xtype:'textfield',
    	          // fieldLabel: '上层菜单选择',
    	           name: 'parentId',
    	           hidden:true,
    	          anchor:'95%'
    	       }, {
    	        	 allowBlank: false,
    	          	 id : 'parentNames',
    	          	 xtype:'textfield',
    	               fieldLabel: '上层菜单选择',
    	               name: 'parentName',
    	              anchor:'95%',
    	              listeners:{
    	      	   		focus:function(b){
    	      	   			accordion.layout.setActiveItem(leftTreeForShow2);
    	       	   	

    	         			}

    	         		}
    	           },
{
   	
   	fieldLabel: '新窗口打开',
   	xtype : 'combo',
    editable : false,
    emptyText:'请选择',
    id : 'issamewins',
    name : 'issamewin',
    mode : 'local',
    triggerAction:'all',
    store:asStore,
    valueField:'myId',
    displayField:'displayText',
    anchor:'95%'

   	//hidden:true
   },  {
	   	id:'orders',
	   	name:'order',
	    xtype:'textfield',
	   	fieldLabel: '排序',
	    anchor:'95%'
	   	//hidden:true
	   },{id:'icons',
    	       xtype:'textfield',
    	       fieldLabel:'图标',
    	       readOnly:true,
    	       name: 'icon',
    	       anchor:'95%',
    	       listeners:{
	      	   		focus:function(c){
	      	   			accordion.layout.setActiveItem(iconView);
	         			}
           }}
            
    			                     	   ]}
    			                     	]}
    			            	]}
    		        	]}
    			                     	
    			                     
 
	);
	var recordStore = Ext.data.Record.create([
                                              {name: 'id' ,mapping : 'ID'},
                                              {name: 'name' , mapping : 'NAME'},
                                              {name: 'icon' , mapping : 'ICON'},
                                              {name:'order' , mapping : 'ORDER_'},
                                              {name:'issamewin' , mapping : 'ISSAMEWIN'},
                                              {name:'modFuncId' , mapping : 'MOD_FUNC_ID'},                                        
                                              {name:'parentId' , mapping : 'PARENT_ID'},
                                              {name:'funcName' , mapping : 'FUNC_NAME'},
                                              {name:'parentName' , mapping : 'PARENT_NAME'}
                                     		 ]);
                                 	  var custGroupStore = new Ext.data.Store({
                                 		restful : true,
                                 		proxy : new Ext.data.HttpProxy({
                                 			url : basepath + '/CntMenu-action.json'
//                                 			success : function(success) {
//                                 				Ext.Msg.alert('提示', success.responseText);
//                                 				alert('dddddddddddddddd');
//                                 			}
                                 		}),
                                 		reader : new Ext.data.JsonReader({
                                 			successProperty: 'success',
                                 			root:''
                                 		},recordStore)
                                 	});
                                 	  
                                 	  
                                 	//***********************

                                 		// 每页显示条数下拉选择框
                                 		var spagesize_combo = new Ext.form.ComboBox({
                                 			name : 'pagesize',
                                 			triggerAction : 'all',
                                 			mode : 'local',
                                 			store : new Ext.data.ArrayStore({
                                 				fields : [ 'value', 'text' ],
                                 				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
                                 							[ 100, '100条/页' ], [ 250, '250条/页' ],
                                 							[ 500, '500条/页' ] ]
                                 			}),
                                 			valueField : 'value',
                                 			displayField : 'text',
                                 			
                                 			value : '20',
                                 			forceSelection : true,
                                 			width : 85
                                 		});

                                 		// 改变每页显示条数reload数据
                                 		spagesize_combo.on("select", function(comboBox) {
                                 			sbbar.pageSize = parseInt(spagesize_combo.getValue()),
                                 			custGroupStore.reload({
                                 				params : {
                                 					start : 0,
                                 					limit : parseInt(spagesize_combo.getValue())
                                 				}
                                 			});
                                 		});
                                 		// 分页工具栏
                                 		var sbbar = new Ext.PagingToolbar({
                                 			pageSize : parseInt(spagesize_combo.getValue()),
                                 			store : custGroupStore,
                                 			displayInfo : true,
                                 			displayMsg : '显示{0}条到{1}条,共{2}条',
                                 			emptyMsg : "没有符合条件的记录",
                                 			items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
                                 		});

                                 		//***********************
                                 	  
                                 	// 默认加载数据
                                     custGroupStore.load({
                                 		params : {
                                 			start : 0,
                                 			limit : 100
                                 		}
                                 	});
/***************************************************************************************************************************************/                              
                                 
	  //左边的tree的形状
    var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title:'菜单维护',
		id:'blocMemberTree',
		width:300,
		autoScroll:true,
		tbar:tbar,
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
		 clickFn:function(node){
		 
	
		 Ext.getCmp('id').setValue((node.attributes.ID));

		 Ext.getCmp('name').setValue((node.text));
		 
		 if(node.attributes.icon != undefined ){
		 var is = iconStore.find('myId',node.attributes.icon.substr(8.40));
		 if(is!=-1){
				//alert(iconStore.getAt(is).get('displayText'));
		
				Ext.getCmp('icon').setValue(iconStore.getAt(is).get('displayText'));
				var tImp = document.createElement('img');
				tImp.src = node.attributes.icon;
				
				if(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes.length>1){
					Ext.ComponentMgr.all.map.icon.el.dom.parentNode.removeChild(Ext.ComponentMgr.all.map.icon.el.dom.parentNode.childNodes[1]);
				}
				Ext.ComponentMgr.all.map.icon.el.dom.parentNode.appendChild(tImp);
			}
			else
			{
				Ext.getCmp('icon').setValue((node.attributes.icon));
			}
		 }

		 Ext.getCmp('order').setValue((node.attributes.ORDER_));
		 
		 var i = asStore.find('myId',node.attributes.ISSAMEWIN);
			if(i!=-1){
			
				
				Ext.getCmp('issamewin').setValue(asStore.getAt(i).get('displayText'));
			}
			else
			{
				Ext.getCmp('issamewin').setValue((node.attributes.ISSAMEWIN));
			}
		 
		 Ext.getCmp('modFuncId').setValue((node.attributes.MOD_FUNC_ID));
		 
		 Ext.getCmp('funcName').setValue((node.attributes.FUNC_NAME));
		 debugger;
         if(node.attributes.PARENT_ID == '0')
         {	
        	 Ext.getCmp('parentId').setValue((node.attributes.PARENT_ID));

        	 Ext.getCmp('parentName').setValue(('主菜单 '));
           }else{
		 Ext.getCmp('parentId').setValue((node.attributes.PARENT_ID));
		 Ext.getCmp('parentName').setValue((node.attributes.PARENT_NAME));}


	

		 
		 custGroupStore.reload({
			 params : {
	    	   'nodeid': node.id,
				start : 0,
				limit : 100
			}
		 });

    	}
    
	});	
   
    var leftTreeForShows = new Com.yucheng.bcrm.TreePanel({
		width:200,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			text:'模块功能菜单',
			autoScroll:true,
	        expanded:true,
	        leaf:false,
			children:[]
		}),
		 resloader: loader1,
		 split:true,
		 clickFn:function(node){
            Ext.getCmp('fwname').setValue(node.attributes.PARENT_ID);
            Ext.getCmp('modFuncId').setValue(node.ID);
		},
	    animate : false,
	    useArrows : false,
	    border : false
	});
    
    
    
	new Ext.Viewport({
		layout:'border',
	        items:[leftTreeForShow,{
	            region:'center',
	            layout:'fit',
	           	items:[{
	           		layout:'border',
	           		split:true,
            		height: 25055,
            		minSize: 100,
           			maxSize: 300,
	           		items:[{
	           			id:'center-panel',
	           			region:'center',
	           			layout:'fit',
	           			items:[addtitle]
	           		}]
	           	}]
	        }]
	});
});
