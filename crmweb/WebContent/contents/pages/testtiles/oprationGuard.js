Ext.onReady(function(){
	
	var tf = new Ext.form.TextField({
		xtype : 'textfield',
		name : 'menuId',
		fieldLabel : '待配功能点',
		anchar : '80%'
	});
	/***
	 * 选择的功能点
	 */
	var functionForm = new Ext.form.FormPanel({
		frame : true,
		height : 100,
		title : '待配功能点',
		items : [tf]
	});
	
	var comsitPanel = new Ext.Panel({
		frame : true ,
		title : '关联功能点',
		listeners : {
			afterrender : function(cp){
				new Ext.dd.DropTarget(comsitPanel.el.dom, {
					ddGroup : 'comsitfunction',
					notifyEnter : function(ddSource, e, data) {
					},
					notifyDrop  : function(ddSource, e, data){
						var menuId = functionForm.getForm().findField('menuId').menuId;
						var menuText = functionForm.getForm().findField('menuId').getValue();
						if(data.node.id == menuId){
							Ext.Msg.alert('提示','【'+menuText+'】不能配置自身为关联功能');
							return false;
						}
						if(comsitPanel.items.get('menutile_'+data.node.id)){
							Ext.Msg.alert('提示','【'+data.node.text+'】功能已存在，请选择其他功能');
							return false;
						}
						var si = comsitPanel.el.getViewSize();
						var count = comsitPanel.items.getCount();
						var newtile = new Wlj.widgets.search.tile.Tile({
							id : 'menutile_'+data.node.id,
							cls : 'function_re',
					        	 ownerW :  10,
					        	 ownerH : 20,
					        	 defaultDDGroup : 'addcontarget',
					        	 removeable : true,
					        	 labelText : 'AUM',
					        	 baseSize : si.width -40,
					        	 baseHeight : 34,
					        	 baseMargin : 4,
					        	 pos_size : {
					        	 	TX : 0,
					        	 	TY : count,
					        	 	TW : 1,
					        	 	TH : 1
					         	},
					         	html:  '<br>&nbsp;&nbsp;&nbsp;'+data.node.text,
					         	removeThis : function(){
					         		if(this.el.hasClass('resultgroup')){
					         			moveIn(1);
					         		}
									var index = this.ownerCt.items.indexOf(this);
									var ownerCt = this.ownerCt;
									this.ownerCt.remove(this,true);
									for(var i=index;i<ownerCt.items.getCount();i++){
										var item = ownerCt.items.itemAt(i);
										item.moveToPoint({
											x : item.pos_size.TX,
											y : item.pos_size.TY - 1
										});
									}
								}
					         });
						  newtile.menuId = data.node.id;
						  comsitPanel.add(newtile);
						  comsitPanel.doLayout();
						  newtile.el.on('click',function(e,t){
							  if(comsitPanel.curFunction && comsitPanel.curFunction != newtile){
								  comsitPanel.curFunction.el.removeClass('resultgroup');
								  moveIn(1);
							  }
							  if(Ext.fly(t).hasClass('resultgroup')){
								  comsitPanel.curFunction = false;
								  Ext.fly(t).removeClass('resultgroup');
								  moveIn(1);
							  }else {
								  comsitPanel.curFunction = newtile;
								  Ext.fly(t).addClass('resultgroup');
								  moveIn(0);
							  }
						  });
					}
				})
			}
		}
	});
	
	
	var paramsPanel = new Ext.Panel({
		title : '传递参数',
		frame : true,
		buttons :[{
			text:'保存',
			handler :function(){
				var menuId = functionForm.getForm().findField('menuId').menuId;
				if(!menuId){
					Ext.Msg.alert('提示','当前没有待配功能点');
					return;
				}
				var comMenus = [];
				comsitPanel.items.each(function(nt){
					comMenus.push(nt.menuId);
				});
				if(comMenus.length == 0 ){
					Ext.Msg.alert('提示','当前未配置相关功能');
					return;
				}
				var par = {
					menuId : menuId,
					comsits : comMenus.join(',')
				};
				Ext.Ajax.request({
					url : basepath + '/composit.json',
					method : 'POST',
					params : par,
					success : function(){
						Ext.Msg.alert('提示','保存成功！');
					},
					failure : function(){
						Ext.Msg.alert('提示','保存失败，请稍后再试！');
					}
				});
			}
		}]
	});
	
	var custPanel = new Ext.Panel({
		region : 'center',
		height : 200,
		layout : 'column',
		items : [{
			columnWidth : .3,
			layout : 'form',
			items : [functionForm,comsitPanel]
		},{
			columnWidth : .7,
			layout : 'fit',
			items : [paramsPanel]
		}]
	});
	
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'PARENT_ID',
		locateAttr : 'ID',
		rootValue : "0",
		textField : 'NAME',
		idProperties : 'ID'
	});
	Ext.Ajax.request({
		url : basepath + '/indexinit.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.json.data;
			var children = loader.loadAll();
			leftTreeForShow.appendChild(children);
		}
	});

	var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title:'可选功能',
		width:300,
		autoScroll:true,
		enableDD : true,
		ddGroup : 'comsitfunction',
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
			tf.setValue('');
			if(node.leaf === true){
				tf.setValue(node.text);
				tf.menuId = node.id;
				comsitPanel.removeAll();
				comsitPanel.doLayout();
				moveIn(1);
				
				var coms = node.attributes.COMSITS;
				if(!coms){
					return;
				}else{
					var comarr = coms.split(',');
					Ext.each(comarr,function(cf){
						var node = leftTreeForShow.root.findChild('id',cf, true);
						var data = {};
						data.node = node;
						if(node){
							var si = comsitPanel.el.getViewSize();
							var count = comsitPanel.items.getCount();
							var newtile = new Wlj.widgets.search.tile.Tile({
								id : 'menutile_'+data.node.id,
								cls : 'function_re',
						        	 ownerW :  10,
						        	 ownerH : 20,
						        	 defaultDDGroup : 'addcontarget',
						        	 removeable : true,
						        	 labelText : 'AUM',
						        	 baseSize : si.width -40,
						        	 baseHeight : 34,
						        	 baseMargin : 4,
						        	 pos_size : {
						        	 	TX : 0,
						        	 	TY : count,
						        	 	TW : 1,
						        	 	TH : 1
						         	},
						         	html:  '<br>&nbsp;&nbsp;&nbsp;'+data.node.text,
						         	removeThis : function(){
						         		if(this.el.hasClass('resultgroup')){
						         			moveIn(1);
						         		}
										var index = this.ownerCt.items.indexOf(this);
										var ownerCt = this.ownerCt;
										this.ownerCt.remove(this,true);
										for(var i=index;i<ownerCt.items.getCount();i++){
											var item = ownerCt.items.itemAt(i);
											item.moveToPoint({
												x : item.pos_size.TX,
												y : item.pos_size.TY - 1
											});
										}
									}
						         });
							  newtile.menuId = data.node.id;
							  comsitPanel.add(newtile);
							  comsitPanel.doLayout();
							  newtile.el.on('click',function(e,t){
								  if(comsitPanel.curFunction && comsitPanel.curFunction != newtile){
									  comsitPanel.curFunction.el.removeClass('resultgroup');
									  moveIn(1);
								  }
								  if(Ext.fly(t).hasClass('resultgroup')){
									  comsitPanel.curFunction = false;
									  Ext.fly(t).removeClass('resultgroup');
									  moveIn(1);
								  }else {
									  comsitPanel.curFunction = newtile;
									  Ext.fly(t).addClass('resultgroup');
									  moveIn(0);
								  }
							  });
						}
					})
				}
				
			}
		}
	});	
	
	var mainView = new Ext.Viewport({
		layout:'border',
		items:[leftTreeForShow,custPanel]
	});
	var vs = mainView.el.getViewSize();
	comsitPanel.setHeight(vs.height);
	paramsPanel.setHeight(vs.height);
	var paravs = paramsPanel.el.getViewSize();
	var tilep1 = new Wlj.widgets.search.tile.Tile({
    	 cls : 'search_level',
    	 ownerW :  10,
    	 ownerH : 20,
    	 defaultDDGroup : 'addcontarget',
    	 removeable : false,
    	 labelText : 'AUM',
    	 baseSize : paravs.width - 10,
    	 baseHeight : 34,
    	 baseMargin : 2,
    	 pos_size : {
    	 	TX : 1,
    	 	TY : 0,
    	 	TW : 1,
    	 	TH : 1
     	},
     	html:  '<br>&nbsp;&nbsp;&nbsp;'+'客户ID'
     });
	var tilep2 = new Wlj.widgets.search.tile.Tile({
   	 cls : 'search_level',
   	 ownerW :  10,
   	 ownerH : 20,
   	 defaultDDGroup : 'addcontarget',
   	 removeable : false,
   	 labelText : 'AUM',
   	 baseSize : paravs.width - 10,
   	 baseHeight : 34,
   	 baseMargin : 2,
   	 pos_size : {
   	 	TX : 1,
   	 	TY : 1,
   	 	TW : 1,
   	 	TH : 1
    	},
    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'客户名称'
    });
	var tilep3 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 2,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'性别'
	    });
	var tilep4 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 3,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'年龄'
	    });
	var tilep5 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 4,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'客户级别'
	    });
	var tilep6 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 5,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'客户类型'
	    });
	var tilep7 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 6,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'职称'
	    });
	var tilep8 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 7,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'所属机构'
	    });
	var tilep9 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 8,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'所属经理'
	    });
	var tilep10 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 9,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'开户日期'
	    });
	var tilep11 = new Wlj.widgets.search.tile.Tile({
	   	 cls : 'search_level',
	   	 ownerW :  10,
	   	 ownerH : 20,
	   	 defaultDDGroup : 'addcontarget',
	   	 removeable : false,
	   	 labelText : 'AUM',
	   	 baseSize : paravs.width - 10,
	   	 baseHeight : 34,
	   	 baseMargin : 2,
	   	 pos_size : {
	   	 	TX : 1,
	   	 	TY : 10,
	   	 	TW : 1,
	   	 	TH : 1
	    	},
	    	html:  '<br>&nbsp;&nbsp;&nbsp;'+'开户网点'
	    });
	
	var paramTiles = [tilep1,tilep2,tilep3,tilep4,tilep5,tilep6,tilep7,tilep8,tilep9,tilep10,tilep11
	                  ];
	
	paramsPanel.add(paramTiles);
	paramsPanel.doLayout();
	
	for(var i=0;i<paramTiles.length;i++){
		paramTiles[i].el.on('click',function(e,t){
			 if(Ext.fly(t).hasClass('search_level')){
				  Ext.fly(t).removeClass('search_level');
				  Ext.fly(t).addClass('search_info');
			  }else {
				  Ext.fly(t).removeClass('search_info');
				  Ext.fly(t).addClass('search_level');
			  }
		});
	}
	
	
	function moveIn(xpoint){
		
		var timout = 100;
		
		Ext.each(paramTiles,function(pt){
			pt.el.removeClass('search_info');
			pt.el.addClass('search_level');
		});
		
		setTimeout(function(){
			tilep1.moveToPoint({
				x : xpoint,
				y : tilep1.pos_size.TY
			});
		}, timout * 0);
		setTimeout(function(){
			tilep2.moveToPoint({
				x : xpoint,
				y : tilep2.pos_size.TY
			});
		}, timout * 1);
		setTimeout(function(){
			tilep3.moveToPoint({
				x : xpoint,
				y : tilep3.pos_size.TY
			});
		}, timout*2);
		setTimeout(function(){
			tilep4.moveToPoint({
				x : xpoint,
				y : tilep4.pos_size.TY
			});
		}, timout*3);
		setTimeout(function(){
			tilep5.moveToPoint({
				x : xpoint,
				y : tilep5.pos_size.TY
			});
		}, timout*4);
		setTimeout(function(){
			tilep6.moveToPoint({
				x : xpoint,
				y : tilep6.pos_size.TY
			});
		}, timout*5);
		setTimeout(function(){
			tilep7.moveToPoint({
				x : xpoint,
				y : tilep7.pos_size.TY
			});
		}, timout*6);
		setTimeout(function(){
			tilep8.moveToPoint({
				x : xpoint,
				y : tilep8.pos_size.TY
			});
		}, timout*7);
		setTimeout(function(){
			tilep9.moveToPoint({
				x : xpoint,
				y : tilep9.pos_size.TY
			});
		}, timout*8);
		setTimeout(function(){
			tilep10.moveToPoint({
				x : xpoint,
				y : tilep10.pos_size.TY
			});
		}, timout*9);
		setTimeout(function(){
			tilep11.moveToPoint({
				x : xpoint,
				y : tilep11.pos_size.TY
			});
		}, timout*10);
	}
	
});