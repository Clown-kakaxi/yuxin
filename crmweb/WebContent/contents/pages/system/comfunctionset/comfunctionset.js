Ext.onReady(function() {
	/**全局变量，存储菜单数据*/
	var _menus = [];
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		rootValue : 'root',
		textField : 'text',
		idProperties : 'id',
		locateAttr : 'id',
		parentAttr : 'parentId'
	});
	var functionTreeForShow = new Com.yucheng.bcrm.TreePanel({
		checkBox : false,
		title:'可选功能',
		layout:'fit',
		region:'west',
		frame : true,
		autoScroll:true,
		width:300,
		ddGroup : 'iconforviewpanel',
		enableDD : true,
		resloader:loader,
		root: new Ext.tree.AsyncTreeNode({
			children:[],
			id:'root',
			text:'CRM系统',
			expanded:true,
			split:true
		}),
		listeners:{
			/**节点添加事件，当菜单节点展开时，判断用户是否已经配置了该功能，如已配置，则隐藏该子节点。*/
			append:function(a,b,c,d){
				if(!c.leaf){
					return;
				}
				for (var i =0;i<iconForViewPanel.body.dom.children.length;i++){
					if(iconForViewPanel.body.dom.children[i].id==c.id+'_div'){
						c.getUI().hide();
						return;
					}
				}
			}
		}
	});
	/*************************功能点设置预览区****************************/
	var iconForViewPanel = new Ext.form.FormPanel({
		id:'iconForViewPanel',
		labelAlign : 'right',
		frame : true,
		title : "常用功能图标展示",
		autoScroll : true,
		ddGroup : 'functiontree',
		enableDD : true,
		region:'center',
		buttons :[{
			text : '重置', 
			handler : function(){
				while(iconForViewPanel.body.dom.firstChild){
					iconForViewPanel.body.dom.firstChild.click();
				}
			}
		},{
			text: '保存',
			handler : function(){
				if(iconForViewPanel.body.dom.children.length <= 0){
					Ext.Msg.alert('提示','你未选择任何模块'); 
					return false;
				}
				var moduleJson = {'modules':[]};
				for(var i=0; i<Ext.getCmp('iconForViewPanel').body.dom.children.length; i++){
					var MODULE_SEQ = i;
					var MODULE_ID = Ext.getCmp('iconForViewPanel').body.dom.children[i].id.split('_')[0];
					var tmpImage = Ext.getCmp('iconForViewPanel').body.dom.children[i].firstChild.style.backgroundImage.split(basepath)[1].replace('\"','');
					var ICON_URL = tmpImage.substring(0,tmpImage.length-1);
					var tmpModJson = {'MODULE_SEQ':MODULE_SEQ,'MODULE_ID':MODULE_ID,'ICON_URL':ICON_URL};
					moduleJson.modules.push(tmpModJson);
				}
				Ext.Ajax.request({
					url: basepath+'/comfunctionset.json',
					method: 'POST',
					params:{
						"modules" : Ext.encode(moduleJson)
					},
					failure : function(form, action){
						Ext.Msg.alert('提示','您的方案保存失败！'); 
					},
					success : function(response){
						Ext.Msg.alert('提示','您的方案保存成功！'); 
					}
				});
			}
		}]
	});
	var view = new Ext.Viewport({
		layout :'fit',
		items:[{
			layout : 'border',
			items:[iconForViewPanel,functionTreeForShow]
		}]

	});
	var iconForViewPanelDropTargetEl =  iconForViewPanel.body.dom;
	var iconForViewPanelDropTarget = new Ext.dd.DropTarget(iconForViewPanelDropTargetEl, {
		ddGroup    : 'iconforviewpanel',
		notifyDrop : function(ddSource, e, data){
			var records = functionTreeForShow.childNodes;
			if (!data.node.leaf){
				return;
			}else{
				var i = data.node.id;
				var n = data.node.attributes.imgUrl;
				var q = data.node.text;
				var insertOne = iconForViewPanel.body.appendChild(iconForViewPanel.body.createChild({
					tag: 'div',
					id:i+'_div',																			
					cls:'oftenlabel',
					html:'<a href="#" style="padding:90px 0 0 0;width:64px;height:64px;float:left;background:url('+n+') no-repeat;" >'+q+'</a>',
					style:'margin-left:10px;margin-right:10px;margin-top:10px;'
				}));
				insertOne.on('click',function(){
					iconForViewPanel.body.dom.removeChild(insertOne.dom);
					functionTreeForShow.root.findChild('id',insertOne.dom.id.split('_')[0],true).getUI().show();
				});
				insertOne.on('contextmenu',function(a,b,c,d){
					a.stopEvent();
					var _menusCfg = [];
					for(var i=1;i<=20;i++){
						var tm = {};
						tm.cls='width:50;';
						tm.width=50;
						tm.hideLabel = true;
						tm.hideBorders = true ;
						if(i<10){
							tm.html = '<img style="zoom:.5;" src='+basepath +'/contents/img/img/icon-0'+i+'.png>';
							tm.src = basepath +'/contents/img/img/icon-0'+i+'.png';
						}else{
							tm.html = '<img style="zoom:.5;" src='+basepath +'/contents/img/img/icon-'+i+'.png>';
							tm.src = basepath +'/contents/img/img/icon-'+i+'.png';
						}
						tm.handler = function(){
							iconChangeHandler(this);
						};
						_menusCfg.push(tm);
					}
					function iconChangeHandler(menu){
						insertOne.dom.firstChild.style.backgroundImage = 'url('+ menu.src +')';
					}
					new Ext.menu.Menu({
				        items: [{
				            text: '图表选择',
				            menu:_menusCfg,
				            scope: this
				        },
				        {
				            text: '取消功能',
				            handler: function(){
				        		iconForViewPanel.body.dom.removeChild(insertOne.dom);
				        		functionTreeForShow.root.findChild('id',insertOne.dom.id.split('_')[0],true).getUI().show();
				        	},
				            scope: this
				        }]
				    }).showAt(a.getXY());
				});
				data.node.getUI().hide();
			};
			return true;
		}										
	});
	/***************************初始化左侧菜单树，以及右侧用户配置信息******************************/
	Ext.Ajax.request({
		url : basepath + '/indexinit.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			var Array1= new Array();
			Ext.each(nodeArra.json.data ,function(n){
				var tmp = {};
				tmp.id= n.ID;
				if(n.PARENT_ID=='0')
					tmp.parentId = 'root';
				else
					tmp.parentId=n.PARENT_ID;
				tmp.text=n.NAME;
				tmp.jumUrl=n.ACTION;
				var iconNum = Math.round(Math.random()*20);
				if(iconNum==0){
					iconNum = 1;
				}
				if(iconNum<10){
					var imgUrl =  basepath+"/contents/img/img/icon-0"+iconNum+'.png';
				}else {
					var imgUrl =  basepath+"/contents/img/img/icon-"+iconNum+'.png';
				}
				tmp.imgUrl = imgUrl;
				Array1.push(tmp);
			});
			_menus = Array1;
			loader.nodeArray = Array1;
			var children = loader.loadAll();
			functionTreeForShow.appendChild(children);
			Ext.Ajax.request({
				url : basepath+'/comfunctionset.json',
				method : "GET",
				success : function(response){
				
					userSetting = Ext.util.JSON.decode(response.responseText);
					var userModule = userSetting.returns.data;
//					for(var i=0;i<userModule.length;i++){
//						for(var j=0;j<_menus.length;j++){
//							if(userModule[i].MODULE_ID == _menus[j].id){}
//						}
//						
//					}

					for(var i=0;i<_menus.length;i++){
						var _resultP = Ext.getCmp('iconForViewPanel');
						_resultP.userModule = userModule; 
						Ext.each(_resultP.userModule,function(um){
							if(um.MODULE_ID==_menus[i].id){
								var w = um.MODULE_ID;
								var m ;
								var n = basepath+um.ICON_URL;
								var q ;
								for(var j = 0;j<_menus.length;j++){
									if (_menus[j].id == w){
										q = _menus[j].text;
									}			
								}
								iconForViewPanel.body.appendChild(iconForViewPanel.body.createChild({
									tag: 'div',
									id:w+"_div",																			
									cls:'oftenlabel',
									html:'<a href="#" style="padding:80px 0 0 0;width:64px;height:64px;float:left;background:url('+n+') no-repeat;"  >'+q+'</a>',
									style:'margin-left:10px;margin-right:10px;margin-top:10px;'					
								}));
								Ext.fly(w+"_div").on('click',function(a,b,c,d){
									iconForViewPanel.body.dom.removeChild(Ext.fly(w+"_div").dom);
									if(functionTreeForShow.root.findChild('id',w,true))
										functionTreeForShow.root.findChild('id',w,true).getUI().show();
								});
								Ext.fly(w+"_div").on('contextmenu',function(a,b,c,d){
									a.stopEvent();
									var _menusCfg = [];
									for(var i=1;i<=20;i++){
										var tm = {};
										tm.cls='width:50;';
										tm.width=50;
										tm.hideLabel = true;
										tm.hideBorders = true ;
										if(i<10){
											tm.html = '<img style="zoom:.5;" src='+basepath +'/contents/img/img/icon-0'+i+'.png>';
											tm.src = basepath +'/contents/img/img/icon-0'+i+'.png';
										}else{
											tm.html = '<img style="zoom:.5;" src='+basepath +'/contents/img/img/icon-'+i+'.png>';
											tm.src = basepath +'/contents/img/img/icon-'+i+'.png';
										}
										tm.handler = function(){
											iconChangeHandler(w,this);
										};
										_menusCfg.push(tm);
									}
									function iconChangeHandler(w,menu){
										Ext.fly(w+"_div").dom.firstChild.style.backgroundImage = 'url('+ menu.src +')';
									}
									new Ext.menu.Menu({
								        items: [{
								            text: '图标选择',
								            menu:_menusCfg,
								            scope: this
								        },
								        {
								            text: '取消功能',
								            handler: function(){
								        		iconForViewPanel.body.dom.removeChild(Ext.fly(w+"_div").dom);
								        		if(functionTreeForShow.root.findChild('id',w,true))
								        			functionTreeForShow.root.findChild('id',w,true).getUI().show();
								        	},
								            scope: this
								        }]
								    }).showAt(a.getXY());
								});
							}

						});
					}

				

					return;
				},
				failure : function(action,form){
					Ext.Msg.alert('提示','你的常用功能设置信息查询失败，将为您初始化默认配置'); 
					var _resultP = Ext.getCmp('iconForViewPanel');
					_resultP.doUserLayout();
				}
			});
		}
	});
	
});
