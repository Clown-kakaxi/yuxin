
function appendix(){//附件
	appendixWindow.show();
}
		/********************公告是否置顶*********************/
		var noticeIsTopStore = new Ext.data.Store( {
			restful : true,
			autoLoad : true,
			sortInfo:{
				field:'key',
				direction:'ASC'
			},
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=IF_FLAG'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
		
		/********************重要程度STORE(新增修改用)*********************/
		var noticeLevelStore = new Ext.data.Store({
		    restful:true,   
		    autoLoad :true,
		    proxy : new Ext.data.HttpProxy({
		        url : basepath+'/lookup.json?name=NOTICE_LEV'
		    }),
		    reader : new Ext.data.JsonReader({
		        root : 'JSON'
		    }, [ 'key', 'value' ])
		});
		
		/********************重要程度STORE（查询用）*********************/
		var nlStore = new Ext.data.ArrayStore({
		    fields : ['key', 'value'],
		    data : [['全部',''],['重要', 'lev001'],['一般', 'lev002']]
		});
		
		/********************阅读标识STORE*********************/
		var irStore = new Ext.data.ArrayStore({
		    fields : ['key', 'value'],
		    data : [['全部',''],['已阅', 'red001'],['未阅', 'red002']]
		});
		debugger;
		Ext.ux.BubblePanel = Ext.extend(Ext.Panel, {
		    baseCls: 'x-bubble',
		    frame: true
		});
		 
		  var title = new Ext.ux.BubblePanel({
				bodyStyle: 'padding-left: 8px;color: #39BBEA',
//				renderTo: 'bubbleCt',
				html: '<h3>Ext.ux.BubblePanel</h3',
				width: 200,
				height: 200
			    });
		  
//		    var cp = new Ext.ux.BubblePanel({
//		    	renderTo: 'bubbleCt',
//		    	padding: 5,
//		    	width: 400,
//		    	autoHeight: true,
//		    	contentEl: 'bubble-markup'
//		        });
		    
var showform=new Ext.form.FormPanel({
    formId:'showform',
    frame:true,
    border:false,
    labelAlign:'right',
    standardSubmit:false,
    width : 870,
    layout:'form',
    items : [{
        layout:'column',
        items : [{
            columnWidth : .5,
            layout : 'form',
            items : [{
                xtype : 'textfield',
                fieldLabel : '*公告标题',
                labelStyle:{
                    width:'120px'
                },        
                width:'100',
                name : 'noticeTitle',
                readOnly : true,
                anchor : '90%',
                allowBlank:false
            },{
                xtype : 'datefield',
                fieldLabel : '有效期至',
                name : 'activeDate', 
                labelStyle: 'text-align:right;',
                format:'Y-m-d', 
                readOnly : true,
                anchor : '90%' 
            },
			{
				xtype : 'datefield',
				fieldLabel : '置顶时间至',
				name : 'topActiveDate',
				labelStyle : 'text-align:right;',
				format : 'Y-m-d',
				readOnly : true,
				anchor : '90%'
			}]
        },{
            columnWidth : .5,
            layout : 'form',
            items : [{
                xtype : 'combo',
                fieldLabel : '重要程度',
                editable : false,
                triggerAction:'all',
                mode:'local',
                emptyText:'请选择',
                store:noticeLevelStore,
                valueField:'key',
                displayField : 'value',
                name : 'noticeLevel',
                anchor : '90%'
            },{
				xtype : 'combo',
				fieldLabel : '是否置顶',
				editable : false,
				readOnly : true,
				hiddenName : 'isTop',
				triggerAction : 'all',
				mode : 'local',
				emptyText : '请选择',
				store : noticeIsTopStore,
				valueField : 'key',
				displayField : 'value',
				name : 'isTop',
				anchor : '90%'
			}]
        }]
    },{
        xtype : 'htmleditor',
        height : 140,
        width : 700,
        enableAlignments: false,  
        enableColors: false,  
        enableFont: false,  
        enableFontSize: false,  
        enableLinks: false,  
        enableFormat: false,  
        enableLists: false,  
        enableSourceEdit: false, 
        editable : false,
        fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Times New Roman','Verdana'],
        defaultFont: '宋体',
        fieldLabel : '公告内容',
        name : 'noticeContent'
    },{
        hidden:true,
        id : 'noticeIdBaby',
        xtype : 'textfield',
        anchor : '90%'
    },{
        hidden:true,
        name : 'published',            
        xtype : 'textfield',
        anchor : '90%'
    }]
});  
function detail(){
	 var win=new Ext.Window({
//       layout : 'fit',
       width : 900,
       height :350,
       closable : true,
       resizable : false,
       draggable : true,
       closeAction : 'hide',
       title : '查看公告',
       collapsible : false,
       modal : true,
       animCollapse : false,
       maximizable : true,
       border : false,
       closable : true,
       animateTarget : Ext.getBody(),
       constrain : true,
       autoScroll : true,
       items : [showform,
                {
		   xtype : 'fieldset',
		   title: '附件',
		   id : 'detailApp',
		   autoHeight : true,
		   layout : 'form',
		   collapsed: true,
	       collapsible: true,
           items : [appendixGridPanel2],
           listeners:{
				'collapse':function(){
					win.setHeight(320);
					win.setPosition(120,10);
					win.doLayout();
				},
			    'expand':function(){
					win.setHeight(400);
					win.setPosition(120,10);
					win.doLayout();
				}
		   }
          }],
       buttonAlign:'center',
       buttons:[{
           text: '返回',
           handler:function(){
               win.hide();
           }
       }],
       listeners : {
   		'beforeshow' : function(){
//   			Ext.getCmp('_downId').setDisabled(false);
//   			Ext.getCmp('_upload').setDisabled(true);
//   			Ext.getCmp('_delload').setDisabled(true);
   		}
   	}
   });
//   var record = grid.getSelectionModel().getSelected();
//   /* 附件编辑列表  */	
//	var noticeIdStr = record.get('noticeId');
//	uploadForm.relaId = noticeIdStr;
//	uploadForm.modinfo = 'notice';
//	var condi = {};
//	condi['relationInfo'] = noticeIdStr;
//	condi['relationMod'] = 'notice';
//	Ext.Ajax.request( {
//		url : basepath + '/queryanna.json',
//		method : 'GET',
//		params : {
//			"condition" : Ext.encode(condi)
//		},
//		failure : function(a, b, c) {
//			Ext.MessageBox.alert('查询异常', '查询失败！');
//		},
//		success : function(response) {
//			var anaExeArray = Ext.util.JSON.decode(response.responseText);
//			appendixStore.loadData(anaExeArray.json.data);
//			appendixGridPanel2.getView().refresh();
//		}
//	});
//   showform.getForm().loadRecord(record);
   win.show();
}
function showDetailWindow(){
	   var win=new Ext.Window({
//         layout : 'fit',
         width : 900,
         height :350,
         closable : true,
         resizable : false,
         draggable : true,
         closeAction : 'hide',
         title : '查看公告',
         collapsible : false,
         modal : true,
         animCollapse : false,
         maximizable : true,
         border : false,
         closable : true,
         animateTarget : Ext.getBody(),
         constrain : true,
         autoScroll : true,
         items : [showform,
                  {
			   xtype : 'fieldset',
			   title: '附件',
			   id : 'detailApp',
			   autoHeight : true,
			   layout : 'form',
			   collapsed: true,
		       collapsible: true,
	           items : [appendixGridPanel2],
	           listeners:{
					'collapse':function(){
						win.setHeight(320);
						win.setPosition(120,10);
						win.doLayout();
					},
				    'expand':function(){
						win.setHeight(400);
						win.setPosition(120,10);
						win.doLayout();
					}
			   }
	          }],
         buttonAlign:'center',
         buttons:[{
             text: '返回',
             handler:function(){
                 win.hide();
             }
         }],
         listeners : {
     		'beforeshow' : function(){
     			Ext.getCmp('_downId').setDisabled(false);
     			Ext.getCmp('_upload').setDisabled(true);
     			Ext.getCmp('_delload').setDisabled(true);
     		}
     	}
     });
//     var record = grid.getSelectionModel().getSelected();
//     /* 附件编辑列表  */	
//		var noticeIdStr = record.get('noticeId');
//		uploadForm.relaId = noticeIdStr;
//		uploadForm.modinfo = 'notice';
//		var condi = {};
//		condi['relationInfo'] = noticeIdStr;
//		condi['relationMod'] = 'notice';
//		Ext.Ajax.request( {
//			url : basepath + '/queryanna.json',
//			method : 'GET',
//			params : {
//				"condition" : Ext.encode(condi)
//			},
//			failure : function(a, b, c) {
//				Ext.MessageBox.alert('查询异常', '查询失败！');
//			},
//			success : function(response) {
//				var anaExeArray = Ext.util.JSON.decode(response.responseText);
//				appendixStore.loadData(anaExeArray.json.data);
//				appendixGridPanel2.getView().refresh();
//			}
//		});
//     showform.getForm().loadRecord(record);
     win.show();
}
function showTileMenu(a){
//	a.stopEvent();
//	new Ext.menu.Menu({
//        items: [{
//            text: '新增公告',
//            handler: function(){},
//            scope: this
//        },
//        {
//            text: '导出',
//            handler: function(){},
//            scope: this
//        }]
//    }).showAt([a.x,a.y ]);


}
Ext.onReady(function() {
	function onDataViewRender(v){  //创建拖拽对象 
		
		var dd = new Ext.dd.DragDrop(v.el);  //存储被拖拽的节点 
	 	var dragData = null;  
	 	dd.onMouseDown = function(e) { 
	 		
		 var t,idx,record;     
	                 try { 
	                  t = e.getTarget(v.itemSelector);      
										 idx = v.indexOf(t);      
	 										record = v.getStore().getAt(idx);    // Found a record to move      
	 							if (t && record) {     
											 dragData = {  
											 origIdx : idx, 
											 lastIdx : idx,         
											 record  : record    
											  };              
											return true;     
											 }  
	 							} catch (ex) 
	 							{	 dragData = null; }   
									return false;      },  //开始拖拽
									
									
	 						dd.startDrag = function(x, y) {
	 					
					 if (!dragData) { return false; }   
					Ext.fly(v.getNode(dragData.origIdx)).addClass('x-tool-unpin');   
							 v.el.addClass('x-tool-pin');  },  //结束拖拽 
	 							dd.endDrag = function(e) {
								if (!dragData) { return true; } 
						 Ext.fly(v.getNode(dragData.lastIdx)).removeClass('x-tool-uppin');   
	 						v.el.removeClass('x-tool-pin');
	 								return true; 
						},   
				 dd.onDrag = function(e) { 
				 	
	 								var t,idx,record,data = dragData;
								 if (!data) { return false; } 
								try {    
	  							t = e.getTarget(v.itemSelector);
									idx = v.indexOf(t);  
									record = v.getStore().getAt(idx);  
	   						  if (idx === data.lastIdx) { return true; }            //将数据插入到新的位置   
	 								if (t && record) {   
											data.lastIdx = idx;     
	  									 v.getStore().remove(data.record);    
	    								 v.getStore().insert(idx, [data.record]);    
	   							Ext.fly(v.getNode(idx)).addClass('thumb');    
	   							 return true;  
	      		      }  
	    				    } catch (ex) { return false; }  
	       			     return false;  }}
	   /**
     * Create a standard HttpProxy instance.
     */
    var proxyIndex = new Ext.data.HttpProxy({
        url : basepath+'/noticequery.json?noticeTitle=asas（）'
    });
    /**
     * Data record, used for read records from the JAVA project to store.
     */
    var TopicRecord = Ext.data.Record.create([
        {name: 'noticeId', mapping: 'NOTICE_ID'},
        {name: 'noticeTitle', mapping: 'NOTICE_TITLE'},                                   
        {name: 'topActiveDate', mapping: 'TOP_ACTIVE_DATE'},  
        {name: 'noticeContent', mapping: 'NOTICE_CONTENT'},
        {name: 'noticeLevel', mapping: 'NOTICE_LEVEL_ORA'},
        {name: 'publisher', mapping: 'PUBLISHER'},
        {name: 'publishOrganizer', mapping: 'PUBLISH_ORG'},
        {name: 'isTop', mapping: 'IS_TOP'},
        {name: 'publishTime', mapping: 'PUBLISH_TIME'},
        {name: 'moduleType', mapping: 'MODULE_TYPE'},
        {name: 'published', mapping: 'PUBLISHED'},
        {name: 'activeDate', mapping: 'ACTIVE_DATE'},
        {name: 'noticeType', mapping: 'NOTICE_TYPE'},
        {name: 'isRead', mapping: 'IS_READ_ORA'},
        {name: 'creator', mapping: 'CREATOR'},
        {name: 'publisherName', mapping: 'PUBLISHER_NAME'},
        {name: 'pubOrgName', mapping: 'PUB_ORG_NAME'},
        {name: 'creatorName', mapping: 'CREATOR_NAME'},
        {name: 'annCount', mapping:'ANN_COUNT'}
    ]);
    /**
     * Typical JsonReader.  Notice additional meta-data params for defining the core attributes of your json-response
     */
    var reader = new Ext.data.JsonReader({
        successProperty: 'success',
        idProperty: 'NOTICE_ID',
        messageProperty: 'message',
        totalProperty: 'json.count',
        root : 'json.data'
    },TopicRecord/**data record*/);
    /**
     * store writer, defined for delete records.
     */
    var writer = new Ext.data.JsonWriter({
        encode: false   
    });
    var restfulStore = new Ext.data.Store({
        id: 'notice',
        restful : true,     
        proxy : proxyIndex,
        autoLoad:false,
        reader : reader,
        writer : writer,
        recordType:TopicRecord
    });
	var bodysize = Ext.getBody().getViewSize();
	
	var tileWidth = parseInt((bodysize.width - 16)/136);
	
	function showMark(tile){
		var vs = tile.el.getViewSize();
		tile.opGuard.dom.style.display = 'block';
		tile.opGuard.animate(
			{	
				height : {
					to : vs.height - 100,
					from : 0
				},
				top : {
					to : vs.height -180,
					from : 0
				}
			},
			.35,
			null,
			'easeOut',
			'run'
		);
	}
	function hideMark(tile){
		var vs = tile.el.getViewSize();
		tile.opGuard.animate({	
				height : {
					from :vs.height - 100,
					to : 0 
				},
				top : {
					from : vs.height-180,
					to :0 
				}
			},
			.35,
			null,
			'easeOut',
			'run'
		);
		setTimeout(function(){
			tile.opGuard.dom.style.display = 'none';
		},150);
	
	}
	function loadData(){
		panel.removeAll();
		panel.doLayout();
//		panel.add(searchTile);
		Ext.Ajax.request({
			url:basepath+'/noticequery.json?noticeTitle=asas（）',
			method:'get',
			params : {
                start : 0,
                limit : 2/*,
                userId:Ext.encode(userId.aId)*/
             },
			success:function(response){
            	 var left1 ;
            	 var left2 ;
            	 var right1 ;
            	 var right2 ;
				var data = Ext.decode(response.responseText).json.data;
				for(var i=0;i<data.length;i++){
					var noticeTitle = data[i].NOTICE_TITLE;
					var noticeId = data[i].NOTICE_ID;
					var topActiveDate = data[i].TOP_ACTIVE_DATE;  
					var noticeContent = data[i].NOTICE_CONTENT;
					var noticeLevel = data[i].NOTICE_LEVEL_ORA;
					var publisher = data[i].PUBLISHER;
					var publishOrganizer = data[i].PUBLISH_ORG;
					var isTop = data[i].IS_TOP;
					var publishTime = data[i].PUBLISH_TIME;
					var moduleType = data[i].MODULE_TYPE;
					var published = data[i].PUBLISHED;
					var activeDate = data[i].ACTIVE_DATE;
					var noticeType = data[i].NOTICE_TYPE;
					var isRead = data[i].IS_READ_ORA;
					var creator = data[i].CREATOR;
					var publisherName = data[i].PUBLISHER_NAME;
					var pubOrgName = data[i].PUB_ORG_NAME;
					var creatorName = data[i].CREATOR_NAME;
					var annCount = data[i].ANN_COUNT;
					var noticeContent = "公告详情：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX";  
//					var name = Ext.decode(response.responseText).json.data[i].NOTICE_TITLE;
					var cls;
					var inforClor;
					if(noticeLevel=='重要'){
						cls='search_or';
						inforClor='#FFFF99';
					}else{
						cls='search_in';
						inforClor='#FF8C00';
					}
					var left1 = new Wlj.widgets.search.tile.Tile({
						cls : cls,
						ownerH : tileWidth*2,
						ownerW :  tileWidth*2,
						removeable :true,
						baseSize : 68,
						baseMargin : 1,
						pos_size : {
							TX : (i%5)*3,
							TY :Math.floor(i/5)*3,
							TW : 3,
							TH : 3
						},
						html : '<div id="'+i+'_div" font size="2" color="#000000" style="width:100%;height:100%;margin-top:5px;text-align:center;"><a style="" href="javascript:cWindow()"><br><br><b>公告标题：'+noticeTitle+'</b></a>&nbsp;&nbsp;&nbsp;&nbsp;<br><br>发布人：'+publisherName+'&nbsp;&nbsp;&nbsp;&nbsp;<br><br>发布机构：'+pubOrgName+'<br><br>&nbsp;&nbsp;&nbsp;&nbsp;生效日期：'+topActiveDate+'<br><br>&nbsp;&nbsp;创建人：'+creatorName+'<br><br><img  onclick = "" src = "'+basepath+'/contents/img/img/Tasks.png">&nbsp;&nbsp;<img onclick = "" src = "'+basepath+'/contents/img/img/isRead.png">&nbsp;&nbsp;<img onclick ="javaScript:appendix()" src = "'+basepath+'/contents/img/img/fujian.png"></div>',
						listeners : {
							afterrender : function(ccc){
								var vs = ccc.el.getViewSize();
								ccc.opGuard = ccc.el.createChild({
									tag:'div',
									style:'position:absolute;top:'+vs.height+'px;background-color:#A2FAF1;width:100%;display:none;'
								});
								ccc.addGroup = ccc.opGuard.createChild({
									tag:'div',
									html:'<br>&nbsp;&nbsp;<a href="javascript:showDetailWindow()">公告修改</a><br><br>&nbsp;&nbsp;'+noticeContent
								});
								ccc.addGroup.on('click',function(event,dom){
								});
								ccc.el.on('mouseenter',function(event,dom){
									showMark(ccc);
								});
								ccc.el.on('mouseleave',function(event,dom){
									hideMark(ccc);
								});
							}
						},
						removeThis:function(){
							var _this = this;
							_this.animate.fadeOut();
							setTimeout(function(){
								_this.ownerCt.remove(_this,true);
							},1000);
							alert("删除成功！");
						}
					});
					debugger;
					panel.add(left1);
				}
				
//				Ext.each(data,function(n){});
				panel.setHeight(550);
				panel.doLayout();
				for(var i =0;i<data.length-1;i++){
					Ext.fly(i+"_div").on('contextmenu',function(a,b,c,d){
						a.stopEvent();
						new Ext.menu.Menu({
					        items: [{
					        	text:"新增公告",
//					        	cls:"menuButton",
					        	handler: function(){
					        	alert("123");
					        }
					        },{
					        	text:"导出公告",
//					        	cls:"menuButton",
					        	handler: function(){
					        	alert("123");
					        }
					        }]
					    }).showAt(a.getXY());
					});
				}
			}
		});
	}
	  var pagesize_combo = new Ext.form.ComboBox({
          name: 'pagesize',
          triggerAction: 'all',
          mode: 'local',
          store: new Ext.data.ArrayStore({
              fields: ['value', 'text'],
              data: [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
          }),
          valueField: 'value',
          displayField: 'text',
          value: '20',
          forceSelection: true,
          width: 85
      });
	     var number = parseInt(pagesize_combo.getValue());
      pagesize_combo.on("select",
      function(comboBox) {
          bbar.pageSize = parseInt(pagesize_combo.getValue()),
          restfulStore.load({
              params: {
                  start: 0,
                  limit: parseInt(pagesize_combo.getValue())
              }
          });
      });
	     var bbar = new Ext.PagingToolbar({
          pageSize: number,
          store: restfulStore,
          displayInfo: true,
          displayMsg: '显示{0}条到{1}条,共{2}条',
          emptyMsg: "没有符合条件的记录",
          items: ['-', '&nbsp;&nbsp;', pagesize_combo]
      });

	
	var searchComponent = new New.search.SearchComponent({
		style : {
			marginTop :0,
			marginLeft :530,
			top:0,
			left :530
		},
		onRender : function(ct, position){
			New.search.SearchComponent.superclass.onRender.call(this, ct, position);
			this.searchType = new New.search.SearchType({
				appObject : this.appObject
			});
			this.searchField = new New.search.SearchField({
				width:160,
				triggerWidth:30,
				comfex : this.comfex,
				appObject : this.appObject
			});
//			this.add(this.searchType );
			this.add(this.searchField );
			this.doLayout();
		},
		height: 50,
		width : 100,
		doSearch:function(){
		
			loadData();
		},
		comfex : true,
		comfexFn : function(){
			if(fexed){
				fexed = false;
				panelgaoji.body.applyStyles({
					height : 0,
					width : 0
				});
				panelgaoji.el.animate(
						{	
							height : {
								to : 0,
								from : 200
							}
						},
						1,
						null,
						'easeOut',
						'run'
					);
				panelgaoji.removeAll(true);
			}else {
				fexed = true;
				panelgaoji.el.animate(
						{	
							height : {
								to : 200,
								from : 0
							}
						},
						1,
						null,
						'easeOut',
						'run'
				);
				
			}
		},
		afterRender:function(){
			
		},
		listeners : {
		}
	});
	
	var panelgaoji = new Ext.Panel({
		height: 0,
		layout:'fit',
		title:"123"
			
	});
	
	var panel = new Ext.Panel({
		width:1100,
		height : 550,
		style : {
			overflowY:'scroll'
		},
		 bodyStyle: "background-color:#C5E089;",
//		baseCls :'searchTile',
//		renderTo:'veiwPort',
//		frame : true,
		items:[ {
            xtype: 'textfield',
            name: 'accountName',
            hidden:true,
            anchor: '100%',
            style : {
			marginTop :0,
			marginRight :80,
			top:50,
			left :80
		}
        }],
		listeners:{
			'afterrender':function(a,b,c){
			debugger;
			a.body.dom.attachEvent('onscroll',function(){
				debugger;
			});
		}
		}
		
	});
	panel.addClass('searchTile');
//	restfulStore.load();
	var cls='searchTile';
	var inforClor='#666666';
	var searchTile = new Wlj.widgets.search.tile.Tile({
		cls : cls,
		ownerH : tileWidth*2,
		ownerW :  tileWidth*3,
		removeable :true,
		baseSize : 68,
		baseMargin : 1,
		pos_size : {
			TX : 15.1,
			TY : 0,
			TW : 4,
			TH : 7.7
		},
		html:'<div><br><br><br><br><br><br><br><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img src="'+basepath+'/contents/img/img/tileIcon1.png"><font size="3"><b>查询未阅公告</b></font><br><br><br><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img src="'+basepath+'/contents/img/img/tileIcon2.png"><font size="3"><b>查询重要公告</b></font><br><br><br><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img src="'+basepath+'/contents/img/img/tileIcon3.png"><font size="3"><b>查询近十天内公告</b></font><br><br><br><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img src="'+basepath+'/contents/img/img/tileIcon4.png"><font size="3"><b>更多搜索</b></div>',
		listeners : {
			afterrender : function(ccc){
			
		}
		},
		items:[ {
            xtype: 'textfield',
            name: 'accountName',
            fieldLabel: '登录名',
            readOnly: true,
            anchor: '100%',
            style : {
			marginTop :50,
			marginLeft :80,
			top:50,
			left :80
		}
        }],
		removeThis:function(){
			var _this = this;
			_this.animate.fadeOut();
			setTimeout(function(){
				_this.ownerCt.remove(_this,true);
			},1000);
			alert("删除成功！");
		}
	});
	var viewport = new Ext.Viewport({
		alayout: 'fit',
		style : {
			overflowY:'scroll'
		},
		items: [searchTile,panel]
		    });
	debugger;
	var flag=0;
	var flag2=0;
	document.body.attachEvent('onscroll',function(a,b,c){
		debugger;
		flag++;
		if(a.srcElement.document.body.scrollTop+a.srcElement.document.body.offsetHeight==a.srcElement.document.body.scrollHeight && flag==1){
//			document.body.dettachEvent('onmousewheel');
			Ext.Ajax.request({
				url:basepath+'/noticequery.json?noticeTitle=asas（）',
				method:'get',
				params : {
	                start : 0,
	                limit : 2/*,
	                userId:Ext.encode(userId.aId)*/
	             },
				success:function(response){
	            	 var left1 ;
	            	 var left2 ;
	            	 var right1 ;
	            	 var right2 ;
	            	
					var data = Ext.decode(response.responseText).json.data;
					 if(data.length>0){
	            		 panel.setHeight(500+450);
	 	     			panel.doLayout(); 
	            	 }
					for(var i=0;i<data.length;i++){
						debugger;
						var noticeTitle = data[i].NOTICE_TITLE;
						var noticeId = data[i].NOTICE_ID;
						var topActiveDate = data[i].TOP_ACTIVE_DATE;  
						var noticeContent = data[i].NOTICE_CONTENT;
						var noticeLevel = data[i].NOTICE_LEVEL_ORA;
						var publisher = data[i].PUBLISHER;
						var publishOrganizer = data[i].PUBLISH_ORG;
						var isTop = data[i].IS_TOP;
						var publishTime = data[i].PUBLISH_TIME;
						var moduleType = data[i].MODULE_TYPE;
						var published = data[i].PUBLISHED;
						var activeDate = data[i].ACTIVE_DATE;
						var noticeType = data[i].NOTICE_TYPE;
						var isRead = data[i].IS_READ_ORA;
						var creator = data[i].CREATOR;
						var publisherName = data[i].PUBLISHER_NAME;
						var pubOrgName = data[i].PUB_ORG_NAME;
						var creatorName = data[i].CREATOR_NAME;
						var annCount = data[i].ANN_COUNT;
						var noticeContent = "公告详情：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX";  
//						var name = Ext.decode(response.responseText).json.data[i].NOTICE_TITLE;
						var cls;
						var inforClor;
						if(noticeLevel=='重要'){
							cls='search_or';
							inforClor='#FFFF99';
						}else{
							cls='search_in';
							inforClor='#FF8C00';
						}
						var left1 = new Wlj.widgets.search.tile.Tile({
							cls : cls,
							ownerH : tileWidth*20,
							ownerW :  tileWidth*20,
							removeable :true,
							baseSize : 68,
							baseMargin : 1,
							pos_size : {
								TX : (i%5)*3,
								TY :6+Math.floor(i/5)*3,
								TW : 3,
								TH : 3
							},
							html : '<div id="'+i+'_div" font size="2" color="#000000" style="width:100%;height:100%;margin-top:5px;text-align:center;"><a style="" href="javascript:cWindow()"><br><br><b>公告标题：'+noticeTitle+'</b></a>&nbsp;&nbsp;&nbsp;&nbsp;<br><br>发布人：'+publisherName+'&nbsp;&nbsp;&nbsp;&nbsp;<br><br>发布机构：'+pubOrgName+'<br><br>&nbsp;&nbsp;&nbsp;&nbsp;生效日期：'+topActiveDate+'<br><br>&nbsp;&nbsp;创建人：'+creatorName+'<br><br><img  onclick = "" src = "'+basepath+'/contents/img/img/Tasks.png">&nbsp;&nbsp;<img onclick = "" src = "'+basepath+'/contents/img/img/isRead.png">&nbsp;&nbsp;<img onclick ="javaScript:appendix()" src = "'+basepath+'/contents/img/img/fujian.png"></div>',
							listeners : {
								afterrender : function(ccc){
									var vs = ccc.el.getViewSize();
									ccc.opGuard = ccc.el.createChild({
										tag:'div',
										style:'position:absolute;top:'+vs.height+'px;background-color:#A2FAF1;width:100%;display:none;'
									});
									ccc.addGroup = ccc.opGuard.createChild({
										tag:'div',
										html:'<br>&nbsp;&nbsp;<a href="javascript:showDetailWindow()">公告修改</a><br><br>&nbsp;&nbsp;'+noticeContent
									});
									ccc.addGroup.on('click',function(event,dom){
									});
									ccc.el.on('mouseenter',function(event,dom){
										showMark(ccc);
									});
									ccc.el.on('mouseleave',function(event,dom){
										hideMark(ccc);
									});
								}
							},
							removeThis:function(){
								var _this = this;
								_this.animate.fadeOut();
								setTimeout(function(){
									_this.ownerCt.remove(_this,true);
								},1000);
								alert("删除成功！");
							}
						});
						debugger;
						panel.add(left1);
					}
				}
			});
		
		
		}else if(a.srcElement.document.body.scrollTop+a.srcElement.document.body.offsetHeight==a.srcElement.document.body.scrollHeight ){
//			document.body.dettachEvent('onmousewheel');
			Ext.Ajax.request({
				url:basepath+'/noticequery.json?noticeTitle=asas（）',
				method:'get',
				params : {
	                start : 0,
	                limit : 2/*,
	                userId:Ext.encode(userId.aId)*/
	             },
				success:function(response){
	            	 flag2++;
	            	 var left1 ;
	            	 var left2 ;
	            	 var right1 ;
	            	 var right2 ;
					var data = Ext.decode(response.responseText).json.data;
					debugger;
						if(data.length>0){
							panel.setHeight(500+450*flag2);
							panel.doLayout();
						}
					for(var i=0;i<data.length;i++){
						debugger;
						var noticeTitle = data[i].NOTICE_TITLE;
						var noticeId = data[i].NOTICE_ID;
						var topActiveDate = data[i].TOP_ACTIVE_DATE;  
						var noticeContent = data[i].NOTICE_CONTENT;
						var noticeLevel = data[i].NOTICE_LEVEL_ORA;
						var publisher = data[i].PUBLISHER;
						var publishOrganizer = data[i].PUBLISH_ORG;
						var isTop = data[i].IS_TOP;
						var publishTime = data[i].PUBLISH_TIME;
						var moduleType = data[i].MODULE_TYPE;
						var published = data[i].PUBLISHED;
						var activeDate = data[i].ACTIVE_DATE;
						var noticeType = data[i].NOTICE_TYPE;
						var isRead = data[i].IS_READ_ORA;
						var creator = data[i].CREATOR;
						var publisherName = data[i].PUBLISHER_NAME;
						var pubOrgName = data[i].PUB_ORG_NAME;
						var creatorName = data[i].CREATOR_NAME;
						var annCount = data[i].ANN_COUNT;
						var noticeContent = "公告详情：XXXXXXXXXXXXXXXXXXXXXXXXXXXXX";  
//						var name = Ext.decode(response.responseText).json.data[i].NOTICE_TITLE;
						var cls;
						var inforClor;
						if(noticeLevel=='重要'){
							cls='search_or';
							inforClor='#FFFF99';
						}else{
							cls='search_in';
							inforClor='#FF8C00';
						}
						var left1 = new Wlj.widgets.search.tile.Tile({
							cls : cls,
							ownerH : tileWidth*20,
							ownerW :  tileWidth*20,
							removeable :true,
							baseSize : 68,
							baseMargin : 1,
							pos_size : {
								TX : (i%5)*3,
								TY :6*flag2+6+Math.floor(i/5)*3,
								TW : 3,
								TH : 3
							},
							html : '<div id="'+i+'_div" font size="2" color="#000000" style="width:100%;height:100%;margin-top:5px;text-align:center;"><a style="" href="javascript:cWindow()"><br><br><b>公告标题：'+noticeTitle+'</b></a>&nbsp;&nbsp;&nbsp;&nbsp;<br><br>发布人：'+publisherName+'&nbsp;&nbsp;&nbsp;&nbsp;<br><br>发布机构：'+pubOrgName+'<br><br>&nbsp;&nbsp;&nbsp;&nbsp;生效日期：'+topActiveDate+'<br><br>&nbsp;&nbsp;创建人：'+creatorName+'<br><br><img  onclick = "" src = "'+basepath+'/contents/img/img/Tasks.png">&nbsp;&nbsp;<img onclick = "" src = "'+basepath+'/contents/img/img/isRead.png">&nbsp;&nbsp;<img onclick ="javaScript:appendix()" src = "'+basepath+'/contents/img/img/fujian.png"></div>',
							listeners : {
								afterrender : function(ccc){
									var vs = ccc.el.getViewSize();
									ccc.opGuard = ccc.el.createChild({
										tag:'div',
										style:'position:absolute;top:'+vs.height+'px;background-color:#A2FAF1;width:100%;display:none;'
									});
									ccc.addGroup = ccc.opGuard.createChild({
										tag:'div',
										html:'<br>&nbsp;&nbsp;<a href="javascript:showDetailWindow()">公告修改</a><br><br>&nbsp;&nbsp;'+noticeContent
									});
									ccc.addGroup.on('click',function(event,dom){
									});
									ccc.el.on('mouseenter',function(event,dom){
										showMark(ccc);
									});
									ccc.el.on('mouseleave',function(event,dom){
										hideMark(ccc);
									});
								}
							},
							removeThis:function(){
								var _this = this;
								_this.animate.fadeOut();
								setTimeout(function(){
									_this.ownerCt.remove(_this,true);
								},1000);
								alert("删除成功！");
							}
						});
						debugger;
						panel.add(left1);
					}
				}
			});
		}
		}
	);
//	searchComponent.setValue(conditionP);
	loadData();
});