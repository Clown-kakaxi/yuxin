Ext.onReady(function(){
    /************************首页设置：布局选择、模块拖动等************************************/
    /***********************数据源**********************************/  
    /**
     * Of course , u can make it more beautiful. E.g. you can extend from Ext.Store ,and make your owner store for your logic!
     */
    var listArray = [];
    Ext.each(moduleData,function (model){
    	if(model.hidden==false){
    		 listArray.push(model);
             return;
    	}
    });
    var shecusStore = new Ext.data.Store({
        reader:new Ext.data.JsonReader({
            successProperty: 'success',
            messageProperty: 'message',
            fields:[
                {name: 'name',mapping:'name'},
                {name: 'modId',mapping:'modId'}
            ]
        })
    });
    shecusStore.loadData(listArray);
	/*****************************************/
    var individuationsetPanel =  new Ext.Panel({
        title : '个性化设置',
        layout : 'border',
        border:false,
        items:[{
            region:'west',
            id:'westPanels',
            frame:true,
            layout:'fit',
            title:'选项列表',
            border:false,
            split:true,
            width: 170,
            items:[{
                xtype:'crm.modlist',
                store: shecusStore,
                ddGroup : 'previewmodel',
                enableDragDrop : true,
                previewPan:'centerPanel'
            }]
        },{
            id:'east-panel',
            region:'east',
            title:'设置首页布局面板',
            frame:true,
            collapsible: true,
            split:true,
            width: 120,
            minSize: 100,
            maxSize: 150,
            border:false,
            items:[{
            	id:'eastChooses',
                xtype: 'crm.chooselayout',
                items: layoutData
            }]
        },{
        	xtype:'panel',
            region:'center',
            title:'效果预览',
            id:'panelSet',
            frame:true,
            style:'padding:5px 5px 5px 5px',
            buttonAlign:'center',
            buttons : [{
            	text:'保存',
            	handler:function(){
            		/***get layoutId user choosed!****/
            		var myLayout = 'oneColumn';
            		Ext.each(layoutData,function(ld){
            			if(Ext.getCmp(ld.checkBoxId).checked){
            				myLayout = ld.layoutId;                                
            			}
            		});
            		var layoutJson = {'layoutId':myLayout};
            		var moduleJson = {'modules':[]};
            		var layoutPanel = Ext.getCmp('panelSet');
            		var columnArray;
            		if(layoutPanel.items.items.length>0){
            			columnArray = layoutPanel.items.items[0].items.items;
            		}else {
            			Ext.Msg.alert('提示','您未选择任何布局'); 
            			return false;
            		}
            		for(var columnCount=0; columnCount<columnArray.length; columnCount++){
            			var tmpRowArray = columnArray[columnCount].items.items;
            			var rowLen = tmpRowArray.length;
            			for(var row = 0; row<rowLen; row++){
            				var COLUMN_SEQ = columnCount;
            				var MODULE_SEQ = row;
            				var MODULE_ID = tmpRowArray[row].id;
            				var tmpModJson = {'COLUMN_SEQ':COLUMN_SEQ,'MODULE_SEQ':MODULE_SEQ,'MODULE_ID':MODULE_ID};
            				moduleJson.modules.push(tmpModJson);
            			}
            		}
            		if(moduleJson.modules.length==0){
            			Ext.Msg.alert('提示','你未选择任何模块'); 
            			return false;
            		}
            		Ext.Ajax.request({
            			url: basepath+'/indexset.json',
            			method: 'POST',
            			params:{
            				"layoutId" : myLayout,
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
            },{
            	text : '重置', 
            	handler : function() {
                /***************************查询客户数据**************************************************/
                Ext.Ajax.request({
                    url : basepath+'/indexset.json',
                    method : "GET",
                    success : function(response){
                	userSetting = Ext.util.JSON.decode(response.responseText);
                	if(!userSetting.returns.layoutId){
                		Ext.getCmp('panelSet').removeAll();
                		return false;
                	}

                	
                	var userLayout = userSetting.returns.layoutId;
                	var userModule = userSetting.returns.data;
                	var _layoutP = Ext.getCmp(userLayout);
                	_layoutP.checkLayout();
                	var _columnCount = _layoutP.columnCount;
                	var _resultP = Ext.getCmp('panelSet').items.items[0];
                	
                    var userModule2=[];
                    for(var k=0;k<userModule.length;k++){
                    	var flag=0;
                    	for(var p=0;p<listArray.length;p++){
                    		if(userModule[k].MODULE_ID==listArray[p].modId){
                    			flag++;
                    		}
                    	}
                    	if(flag>0){
                    		userModule2.push(userModule[k]);
                    	}
                    
                    }
                	
                	for(var i=0;i<_columnCount;i++){
                		var tmpColumnObjs = [];
                		Ext.each(userModule2,function(m){
                			if(m.COLUMN_SEQ==i){
                				tmpColumnObjs.push(m);
                			}
                		});
                		
                        var tmpColumnObjs2 = [];//原数组长度计算
                        Ext.each(userModule,function(m){
                            if(m.COLUMN_SEQ==i ){
                                tmpColumnObjs2.push(m);
                            }
                        });
                		for(var j=0;j<tmpColumnObjs2.length;j++){
                			
                				Ext.each(tmpColumnObjs,function(m){
                                		if(m.MODULE_SEQ==j){
                                			var id = m.MODULE_ID;
                                            var name;
                                            for(var mInd=0;mInd<moduleData.length;mInd++){
                                                if(moduleData[mInd].modId==id){
                                                    name = moduleData[mInd].name;
                                                }
                                            }
                                            var tmpDragMod = new Com.yucheng.crm.index.DragMod({
                                                frame:true,
                                                title:name,
                                                bodyStyle:'text-align:center',
                                                id:id,
                                                html:'<img style="margin:0 auto" src="../../../img/img/groupCustList.png"/>'
                                            });
                                            _resultP.items.items[i].add(tmpDragMod);
                                        }
                                    });
                                }
                            }
                            _resultP.doLayout();
                },
                    failure : function(action,form){
//                        Ext.Msg.alert('提示','您尚未做过首页设置！'); 
                    }
                });

                    }
                }]
            }]
        });
    /*********************************个性化设置页签*****************************************/
    var individuationTabPanel = new Ext.TabPanel({
        activeTab:0,
        border:false,
        items:[
               individuationsetPanel//,
               //navigatorPanel
               ]
    });
    var individuationView = new Ext.Viewport({
        items:individuationTabPanel,
        border:false,
        layout:'fit'
    });
    /**********************Drag and Drop zone***************************/
    var modePreview =  Ext.getCmp('panelSet').body;
	var firstGridDropTarget = new Ext.dd.DropTarget(modePreview, {
		ddGroup    : 'previewmodel',
		notifyDrop : function(ddSource, e, data){
			if(data.selections.length>1){
				Ext.Msg.alert('提示','只能选择一个模块!'); 
				return;
			}
			if(!Ext.getCmp('panelSet').items.items[0]){
				Ext.Msg.alert('提示','您还没有选择布局，请先选择布局!'); 
				return;
			}
			if(Ext.getCmp(data.selections[0].data.modId)){
				Ext.Msg.alert('提示','该模块已经存在，请您选择其他模块！'); 
				return;
			}
			var uxPanel = Ext.getCmp('panelSet').items.items[0];
			var wholeWidth = uxPanel.body.dom.clientWidth;
			var wholeHeigth = uxPanel.body.dom.clientHeight;
			var columnCount = 1;
    		Ext.each(layoutData,function(ld){
    			if(Ext.getCmp(ld.checkBoxId).checked){
    				columnCount = ld.columnCount;                                
    			}
    		});
    		var mouseX = e.browserEvent.clientX - uxPanel.body.getLeft(false);
    		var mouseY = e.browserEvent.clientY - uxPanel.body.getTop(false);
    		
    		var eventIndex = 0;
    		try{
    			eventIndex =Math.floor( mouseX/(wholeWidth/columnCount));
    			if(eventIndex>=columnCount){
    				eventIndex = columnCount-1;
    			}
    		}catch(ef){}
    		var conatainColumn = uxPanel.items.items[eventIndex];
    		if(Com.yucheng.crm.index.DragMod.prototype.height*conatainColumn.items.items.length>mouseY){
    			var modIndex = 0;
    			try{
    				modIndex =Math.floor(mouseY/Com.yucheng.crm.index.DragMod.prototype.height);
    				conatainColumn.insert(modIndex, new Com.yucheng.crm.index.DragMod({
                        title:data.selections[0].data.name,
                        frame:true,
                        bodyStyle:'text-align:center',
                        id:data.selections[0].data.modId,
                        html:'<img style="margin:0 auto" src="../../../img/img/groupCustList.png"/>'
                    }));
    			}catch(ef){}
    		}else{
    			conatainColumn.add(new Com.yucheng.crm.index.DragMod({
                    title:data.selections[0].data.name,
                    frame:true,
                    bodyStyle:'text-align:center',
                    id:data.selections[0].data.modId,
                    html:'<img style="margin:0 auto" src="../../../img/img/groupCustList.png"/>'
                }));
    		}
    		Ext.getCmp('panelSet').doLayout();
		}
	});
    /***************************查询客户数据**************************************************/
    Ext.Ajax.request({
        url : basepath+'/indexset.json',
        method : "GET",
        success : function(response){
            userSetting = Ext.util.JSON.decode(response.responseText);
            if(!userSetting.returns.layoutId){
                Ext.Msg.alert('提示','您尚未做过首页设置！'); 
                return;
            }
            var userLayout = userSetting.returns.layoutId;
            var userModule = userSetting.returns.data;
            var _layoutP = Ext.getCmp(userLayout);
            _layoutP.checkLayout();
            var _columnCount = _layoutP.columnCount;
            var _resultP = Ext.getCmp('panelSet').items.items[0];
            
            var userModule2=[];
            for(var k=0;k<userModule.length;k++){
            	var flag=0;
            	for(var p=0;p<listArray.length;p++){
            		if(userModule[k].MODULE_ID==listArray[p].modId){
            			flag++;
            		}
            	}
            	if(flag>0){
            		userModule2.push(userModule[k]);
            	}
            
            }
            for(var i=0;i<_columnCount;i++){
                var tmpColumnObjs = [];
              
                Ext.each(userModule2,function(m){
                    if(m.COLUMN_SEQ==i ){
                        tmpColumnObjs.push(m);
                        
                    }
                });
                var tmpColumnObjs2 = [];//原数组长度计算
                Ext.each(userModule,function(m){
                    if(m.COLUMN_SEQ==i ){
                        tmpColumnObjs2.push(m);
                    }
                });
                for(var j=0;j<=tmpColumnObjs2.length;j++){
                    Ext.each(tmpColumnObjs,function(m){
                        if(m.MODULE_SEQ==j){
                            var id = m.MODULE_ID;
                            var name;
                            for(var mInd=0;mInd<moduleData.length;mInd++){
                                if(moduleData[mInd].modId==id){
                                    name = moduleData[mInd].name;
                                }
                            }
                            var tmpDragMod = new Com.yucheng.crm.index.DragMod({
                                title:name,
                                frame:true,
                                bodyStyle:'text-align:center',
                                id:id,
                                html:'<img style="margin:0 auto" src="../../../img/img/groupCustList.png"/>'
                               // html:'<img src="../../../img/img/'+id+'.png"/>'
                            });
                            _resultP.items.items[i].add(tmpDragMod);
                        }
                    });
                }
            }
            _resultP.doLayout();
        },
        failure : function(action,form){
            Ext.Msg.alert('提示','您尚未做过首页设置！'); 
        }
    });
});
