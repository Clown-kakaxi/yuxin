Ext.ns('Com.yucheng.crm.index');
/**
 * yeah! This is the class of preview module. You can drag it to the layout!
 */
Com.yucheng.crm.index.DragMod = Ext.extend(Ext.Panel,{
    tools:[{
        id:'close',
        handler: function(e, target, panel){
    		if(panel.ownerCt.getXType()=='panel'){
    			itemx="0";
    		}
            panel.ownerCt.remove(panel, true);
        }
    }],
    width:'100%',
    height:(document.documentElement.clientHeight-95)/2,
    style:'padding:0px 0px 0px 0px',
    collapsible : true,   
    draggable : true,   
    cls : 'x-portlet',
    html: '<img src="../../../img/img/test.png"/>'
});
Ext.reg('crm.dragmod', Com.yucheng.crm.index.DragMod);

/**
 * Oh, this is the real modules in index page. Just different from preview module with contains and the trans.
 */
Com.yucheng.crm.index.IndexMod = Ext.extend(Com.yucheng.crm.index.DragMod,{
    width:'100%',
    tools:[{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }],
    draggable : false,
    frame:true,
    maximizable:true,
    isJump:false,
    jumpUrl:'',
    html:''
});
Ext.reg('crm.indexmod', Com.yucheng.crm.index.IndexMod);



/**
 * Oh!Of course, I've no much time to attention that :what's this!
 */
var itemx="0";

/**
 * Preview Panel.Maybe it's better if it dosen't exist!
 * But at least, the preview modules are created in this panel!
 */
Com.yucheng.crm.index.PreviewPanel = Ext.extend(Ext.Panel,{
    xtype:'portal',
    columnWidth: .3,
    title:'面板效果',
    height:document.documentElement.clientHeight-65,
    style:'padding:5px 5px 5px 5px',
    
    onRender : function(ct, position){
        this.prePanel = new Ext.Panel({
            xtype:'panel',
            columnWidth:1,
            style:'padding:5px 5px 5px 5px',
            id:'centerPanel',
            items:[]
        });
        this.add(this.prePanel);
        Com.yucheng.crm.index.PreviewPanel.superclass.onRender.call(this, ct, position);
    },
    
    listeners: {
        'drop': function(e){
            if(itemx!="0"&&itemx!=e.panel.id){
                var panel=Ext.getCmp(itemx); 
                panel.ownerCt.remove(panel, true);
            }
            itemx=e.panel.id;
        }
    }
});
Ext.reg('crm.previewpanel', Com.yucheng.crm.index.PreviewPanel);

/**
 * List the names of modules now we have.
 * And a preview Mod will be create, if you click a record!
 */
Com.yucheng.crm.index.ModListPanel = Ext.extend(Ext.grid.GridPanel,{
    previewPan:'centerPanel',
    width: '100%',
    height:50,
    columns:[
        {dataIndex:"name", sortable:true, id:'name'},
        {dataIndex:'modId',hidden:true,   id:'modId'}
    ],
    autoExpandColumn:'name'
});
Ext.reg('crm.modlist', Com.yucheng.crm.index.ModListPanel);

/**
 * Oh! The east panel of the viewport.it will clear the layout panel, if you choose one item.
 */
Com.yucheng.crm.index.LytChkPanel = Ext.extend(Ext.Panel,{
    style:'padding:5px 5px 5px 5px',
    imgLabel:'img',
    boxLabel:'bo',
    layoutPanel:'viewP',
    checkBoxId:'bId',
    checkBoxName:'bName',
    columnCount:3,
    onRender : function(ct, position){
        this.layoutLabelField = new Ext.Panel({
                border:false,
                html:'<img style="margin:0 auto" src="../../../img/img/'+this.imgLabel+'.png"/>'
        });
        this.checkBoxField = new Ext.form.Checkbox({
            name : this.checkBoxName,
            id : this.checkBoxId,
            border : false,
            boxLabel : this.boxLabel,
            listeners:{
                check:function(obj,c){
                    if(c){
                        var p=Ext.getCmp(this.ownerCt.layoutPanel);
                        p.removeAll(true);
                        var count = this.ownerCt.columnCount;
                        
                        var mainPanel = new Ext.ux.Portal({
                            id:this.id+'mainc',
                            height:document.documentElement.clientHeight-65,
                            listeners: {
                                'drop': function(e){
                                    if(itemx==e.panel.id){
                                        itemx="0";
                                    }
                                }
                            },                            
                            items:[]
                        });
                        for(var i=0;i<count;i++){
                            mainPanel.add({
                                columnWidth : 1/count,
                                style : 'padding:5px 5px 5px 5px;'
                            });
                        }           
                        
                        p.add(mainPanel);
                        p.doLayout();    
                        var itemsCheckButton = this.ownerCt.ownerCt.items.items;
                        var tmpId = this.id;
                        Ext.each(itemsCheckButton,function(cb){
                            if(tmpId!=cb.checkBoxId){
                                Ext.getCmp(cb.checkBoxId).setValue(false);
                            }else {
                                Ext.getCmp(cb.checkBoxId).checked = true;
                            }
                        });
                    }  
                }
            }
        });
        this.add(this.layoutLabelField);
        this.add(this.checkBoxField);
        Com.yucheng.crm.index.PreviewPanel.superclass.onRender.call(this, ct, position);
    },
    /**Jusr for init, not so beautiful!**/
    checkLayout : function(){

        var p=Ext.getCmp(this.layoutPanel);
        p.removeAll(true);
        var count = this.columnCount;
        
        var mainPanel = new Ext.ux.Portal({
            height:document.documentElement.clientHeight-65,
            listeners: {
                'drop': function(e){
                    if(itemx==e.panel.id){
                        itemx="0";
                    }
                }
            },
            items:[]
        });
        for(var i=0;i<count;i++){
            mainPanel.add({
                columnWidth : 1/count,
                style : 'padding:5px 5px 5px 5px;' 
            });
        }
        
        p.add(mainPanel);
        p.doLayout();    
        var itemsCheckButton = this.ownerCt.items.items;
        var tmpId = this.id;
        Ext.each(itemsCheckButton,function(cb){
            if(tmpId!=cb.checkBoxId){
                Ext.getCmp(cb.checkBoxId).setValue(false);
            }else {
                Ext.getCmp(cb.checkBoxId).checked = true;
            }
        });
        this.checkBoxField.setValue(true);
    
    }
});
Ext.reg('crm.check', Com.yucheng.crm.index.LytChkPanel);

/**
 * It looks like useless.But I create it, for later to make this more productful.
 */
Com.yucheng.crm.index.LytChoosePanel = Ext.extend(Ext.Panel,{
    style:'padding:5px 5px 5px 5px'
});
Ext.reg('crm.chooselayout', Com.yucheng.crm.index.LytChoosePanel);

/**
 * The main view panel, used in mainPage.js . That's funny!
 */
Com.yucheng.crm.index.MainViewPanel = Ext.extend(Ext.ux.Portal,{
    id:'mainViewPanel',
    region:'center',
    userLayout:'twoColumn',
    userModule:[
                {MODULE_ID:'notice',MODULE_SEQ:0,COLUMN_SEQ:0},
                {MODULE_ID:'remind',MODULE_SEQ:1,COLUMN_SEQ:0},
                {MODULE_ID:'infomation',MODULE_SEQ:0,COLUMN_SEQ:1},
                {MODULE_ID:'opportunity',MODULE_SEQ:1,COLUMN_SEQ:1}
    ],
    columnCount : 2,
    doUserLayout : function(){
    var listArray = [];
    Ext.each(moduleData,function (model){
    	if(model.hidden==false){
    		 listArray.push(model);
             return;
    	}
    });
	this.removeAll();
        for(var i=0;i<layoutData.length;i++){
            if(layoutData[i].layoutId == this.userLayout){
                this.columnCount = layoutData[i].columnCount;
                break;
            }else continue;
         }
        for(var i=0; i<this.columnCount;i++){
            this.add({
                columnWidth : 1/this.columnCount,
                style : 'padding:0px 0px 0px 0px' 
            });
        }
        var _columnCount = this.columnCount;
        
        var userModule2=[];
        for(var k=0;k<this.userModule.length;k++){
        	var flag=0;
        	for(var p=0;p<listArray.length;p++){
        		if(this.userModule[k].MODULE_ID==listArray[p].modId){
        			flag++;
        		}
        	}
        	if(flag>0){
        		userModule2.push(this.userModule[k]);
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
            Ext.each(this.userModule,function(m){
                if(m.COLUMN_SEQ==i ){
                    tmpColumnObjs2.push(m);
                }
            });
            
            for(var j=0;j<tmpColumnObjs2.length;j++){
                var ct = this;
                Ext.each(tmpColumnObjs,function(m){
                    if(m.MODULE_SEQ==j){
                        var id = m.MODULE_ID;
                        var name;
                        var isJump = false;
                        var jumpUrl = '';
                        var actionUrl = '';
                        var dataNames;
                        var root = 'json.data';
                        var isFrame = false;
                        var frameSrc = '<img src='+basepath+'/contents/img/noData.PNG >';
                        for(var mInd=0;mInd<moduleData.length;mInd++){
                            if(moduleData[mInd].modId==id){
                                name = moduleData[mInd].name;
                                isJump = moduleData[mInd].isJump;
                                jumpUrl = moduleData[mInd].jumpUrl;
                                if(moduleData[mInd].isFrame){
                                    isFrame = moduleData[mInd].isFrame;
                                }
                                if(isFrame&&moduleData[mInd]!=undefined){
                                    frameSrc = moduleData[mInd].frameSrc;
                                }
                                if(moduleData[mInd].dataNames){
                                    dataNames = moduleData[mInd].dataNames;
                                }
                                if(moduleData[mInd].action){
                                    actionUrl = moduleData[mInd].action;
                                }
                                if(moduleData[mInd].root){
                                    root = moduleData[mInd].root;
                                }
                                break;
                            }
                        }
                        var tmpDragMod = new Com.yucheng.crm.index.IndexMod({
                            title:name,
                            height:300,
                            //height:(document.documentElement.clientHeight-95)/2-5,
                            id:id,
                            width:'100%',
                            isJump:isJump,
                            jumpUrl:jumpUrl,
                            actionUrl:actionUrl,
                            layout:'form',
                            isFrame:isFrame,
                            bodyStyle:'text-align:center',
                            tools:[{
                                id:'close',
                                handler: function(e, target, panel){
                                    panel.ownerCt.remove(panel, true);
                                }
                            }]
                        });
                        ct.items.items[m.COLUMN_SEQ].add(tmpDragMod);
                        if(isFrame){
                            //tmpDragMod.height = 300;
                        	tmpDragMod.frameSrc = frameSrc
                        	tmpDragMod.tools.unshift({
                            	id:'maximize',
                            	handler:function(e,target,panel){
	                            	new Ext.Window({
	                            		maximized:true,
	                            		closable:true,
	                            		closeAction : 'close' ,
	                            		html:'<iframe src="'+panel.frameSrc+'" "'+'" height="100%" width="100%"></iframe>'
	                            	}).show(target);
                            	}
                            });
                            tmpDragMod.add(new Ext.Panel({
                                width:'100%',
                                height:263,
                                html:'<iframe src="'+frameSrc+'" "'+'" height="100%" width="100%"></iframe>'
                            }));
                        }else{
                            if(actionUrl!=''){
                                var modulesdata;
                                var fieldsArray = [];
                                var columnModArray = [];
                                if(dataNames){
                                	columnModArray = dataNames;
                                }else {
                                	/**
                                	 * TODO init page if no column array！
                                	 */
                                	Ext.Msg.alert('调试信息','模块【'+name+'】未做列模型设置！'); 
                                	return;
                                }
                                for(var i=0;i<columnModArray.length;i++){
                                	var tmpF = {};
                                	tmpF.name = columnModArray[i].dataIndex;
                                	fieldsArray.push(tmpF);
                                }
                                var tmpStore = new Ext.data.Store({
                                	reader:new Ext.data.JsonReader({
                                		successProperty: 'success',
                                		messageProperty: 'message',
                                		fields : fieldsArray
                                	})
                                });
                                var tmpgrid = new Ext.grid.GridPanel({
                                	height:300,
                                	width: tmpDragMod.ownerCt.ownerCt.width/_columnCount,
                                	style: "text-align:left;",
                                	store : tmpStore,
                                	autoScroll: true,
                                	stripeRows:true,
                                	viewConfig:{
                                	autoFill:true,
                                	foreceFit:true
                                },
                                cm : new Ext.grid.ColumnModel(columnModArray),
                                listeners :{
                                	'rowdblclick' : function(){
                                	if(isJump){
                                		//parent.booter.indexLocate(jumpUrl);	
                                		window.location.href= jumpUrl;
                                	}
                                	}
                                }
                                });
                                tmpDragMod.add(new Ext.Panel({
                                	width:'100%',
                                	height:300,
                                	items:[tmpgrid]
                                }));
                                tmpDragMod.doLayout();
                                Ext.Ajax.request({
                                    url:actionUrl,
                                    method:'GET',
                                    params:{
                                        'start':'0',
                                        'limit':'9'
                                    },
                                    success:function(response){
                                        modulesdata = Ext.util.JSON.decode(response.responseText);
                                        var inData =[];
                                        if(root=='base'){
                                            inData = modulesdata;
                                        }else{
                                            inData = eval("modulesdata."+root);
                                        }
                                        tmpStore.loadData(inData);
                                    },
                                    failure:function(){
                                    	var tmpDataName = "memoryData";
                                    	var tmpData;
                                    	tmpData = eval("memoryData."+id);
                                    	if(tmpData==undefined)
                                    		Ext.Msg.alert('调试信息','【'+name+'】模块数据获取失败，后台程序有误！'); 
                                    	else tmpStore.loadData(tmpData);
                                    }
                                });
                            }
                        }
                        
                    }
                });
            }
        }
        this.doLayout();
    }

});
Ext.reg('crm.mainviewpanel',Com.yucheng.crm.index.MainViewPanel);

