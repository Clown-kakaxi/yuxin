/**
 * 模块管理-功能点维护
 * @author GUOCHI
 * @since 2012-10-09
 */




var moduleId = '';//点击功能点维护是传递的模块ID，在moduleManager.js中赋值
var nodeId = ''; //点击模块功能点树时获取的功能点ID
var loader = new Com.yucheng.bcrm.ArrayTreeLoader({// 功能模块选择的树加载数据
	parentAttr : 'PARENT_ID', // 指向父节点的属性列
	locateAttr : 'ID', // 节点定位属性列，也是父属性所指向的列
	rootValue : "0", // 虚拟根节点id 若果select的值为root则为根节点
	textField : 'AMOUNT_NAME', // 用于展示节点名称的属性列
	idProperties : 'ID' // 指定节点ID的属性列
});

var store = new Ext.data.ArrayStore({//右侧图片选择时展示所用的store
	proxy : new Ext.data.MemoryProxy(),
	fields : ['id'],
	sortInfo: {
		field : 'id',
		direction: 'ASC'
	}
});



iconData = [	//id--图标的路径
                ['/contents/wljFrontFrame/styles/search/searchpics/fun5.png'],
                ['/contents/wljFrontFrame/styles/search/searchpics/fun4.png'],
                ['/contents/wljFrontFrame/styles/search/searchpics/fun3.png'],
                ['/contents/wljFrontFrame/styles/search/searchpics/fun2.png'],
                ['/contents/wljFrontFrame/styles/search/searchpics/fun1.png']
               ];

function setIcon(id){
	Ext.getCmp('tubiao').setValue(id);

} 
store.loadData (iconData);//右侧图标展示时加载静态数据
var dataView = new Ext.DataView({//右侧图标展示的样式(包括图标的排版)
	store: store,
	id: 'iconss',
	tpl  : new Ext.XTemplate(
			'<ul>',
			'<tpl for=".">',
			'<li class="icon" onclick="setIcon(\'{[values.id]}\')" style="float:left;margin-left:12px;background-color:#1b96d1;margin-right:12px;margin-top:10px;">',
			'<img src= "'+basepath+'/{[values.id]}" />',
			'</li>',
			'</tpl>',
			'</ul>'
	),
	itemSelector: 'li.icon',
	overClass   : 'phone-hover',
	singleSelect: true,
	autoScroll  : true
});
var iconView = new Ext.Panel({//右侧图标展示的Panel
	title: '磁贴图标',
	id :'iconView',
	ddGroup  : 'gridDDGroups1',
	enableDD  : true,
	layout: 'fit',
	items : dataView//,
//	height: 700,
//	width: 300
});

var functionManage = new Ext.form.FormPanel({
	id : 'functionManage',
	title : '功能点详情',
	frame : true,
	region : 'north',
	height : 270,
	autoScroll : true,
	split : true,
	items : [{
	    layout : 'form',
	    items : [{
	        id : 'fid',
	        name : 'id',
	        xtype : 'numberfield',
	        hidden : true,
	        anchor : '90%'
	    }, {
	        id : 'moduleId',
	        name : 'moduleId',
	        xtype : 'textfield',
	        hidden : true,
	        anchor : '90%'
	    }, {
	        allowBlank : false,
	        id : 'funcName',
	        name : 'funcName',
	        xtype : 'textfield',
	        fieldLabel : '<font color=red>*</font>功能点名称',
	        anchor : '90%'
	    }, {
	        id : 'funcDesc',
	        name : 'funcDesc',
	        xtype : 'textfield',
	        fieldLabel : '功能点描述',
	        anchor : '90%'
	    }, {
	        id : 'action',
	        name : 'action',
	        xtype : 'textfield',
	        emptyText : '例：/contents/pages/admin/...或http://192.168.1.1/...',
	        allowBlank : false,
	        fieldLabel : '<font color=red>*</font>功能点链接',
	        anchor : '90%'
	    },{
		    title: '是否使用动态磁贴',
		    id:'TacticsFlag0',
		    xtype: 'fieldset',
		    checkboxToggle: true,
		    animCollapse :true,
		    height: 285,
		    items:[{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
				items : [new Ext.form.ComboBox({
					fieldLabel : '默认尺寸',
					store : new Ext.data.SimpleStore({
						fields: ['value','key'],
						data : [['尺寸3x3','1'],['尺寸2x3','2'],['尺寸3x2','3'],['尺寸1x3','4'],['尺寸3x1','5'],['尺寸2x2','6'],['尺寸1x2','7'],['尺寸2x1','8']]
					}),
					mode: 'local',
					//readOnly : true,
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					name:'REPORT_STATUS',
					hiddenName:'REPORT_STATUS',
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					id:'defaultSize',
					width : 270,
					forceSelection : true,
					//typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
		    
		    },
		    {
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸3x3',
		        	 xtype : 'textfield',
		        	 width : 270,
		        	 id:'size1',
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸2x3',
		        	 xtype : 'textfield',	
		        	 width : 270,
		        	 id:'size2',
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸3x2',
		        	 xtype : 'textfield',	
		        	 width : 270,
		        	 id:'size3',
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸1x3',
		        	 xtype : 'textfield',		
		        	 width : 270,
		        	 id:'size4',
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸3x1',
		        	 xtype : 'textfield',	
		        	 width : 270,
		        	 id:'size5',
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸2x2',
		        	 xtype : 'textfield',	
		        	 width : 270,
		        	 id:'size6',
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸1x2',
		        	 xtype : 'textfield',		           
		        	 id:'size7',
		        	 width : 270,
		        	 anchor : '90%'
				}]
		    },{
		        xtype: 'compositefield',
		        fieldLabel: '',
		        combineErrors: false,
		        items: [{
		        	 fieldLabel : '尺寸2x1',
		        	 xtype : 'textfield',	
		        	 width : 270,
		        	 id:'size8',
		        	 anchor : '90%'
				}]
		    }]  
		},{
	        id : 'tubiao',
	        name : 'tubiao',
	        xtype : 'textfield',
	        fieldLabel : '磁贴图标',
	        width : 250,
	        anchor : '90%'
	    }]
	}, {
	    layout : 'form',
	    buttonAlign : 'center',
	    buttons : [{
	        id : 'funcManager',
	        text : ' 保  存 ',
	        disabled : true,
	        handler : function() {
	            if (!functionManage.getForm().isValid()) {
	                Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	                return false;
	            }
	           
	            var ifHas=0;
            	var data = functionManage.getForm().getFieldValues();
            	data.ifHas = ifHas;
            	data.ifAdd = ifAdd;
            	
            	if(Ext.getCmp('tubiao').getValue()!=''){
            		data.tubiao=Ext.getCmp('tubiao').getValue();
            	}
            	else{
            		data.tubiao='';
            	}
            	
            	debugger;
	            if(!Ext.getCmp('TacticsFlag0').collapsed){
	            	var ids='';
	            	var limit;
	            	
	        		if(Ext.getCmp('defaultSize').getValue()!=''){
	        			var index=Ext.getCmp('defaultSize').getValue();
	        			if(Ext.getCmp('size'+index).getValue()==''){
	        				Ext.MessageBox.alert('系统提示信息', '请输入默认尺寸对应的页面！');
		                	return false;
	        			}
	        		}
	            	
	            	
	            	for(var i=1;i<9;i++){
	            		if(Ext.getCmp('size'+i).getValue()!='')
	            			limit=i;
	            	}
	            	var defaultUrl=Ext.getCmp('tubiao').getValue();
	            	
	            	for(i=1;i<9;i++){
            			if((i+'')==( Ext.getCmp('defaultSize').getValue()))
            			{
            				defaultUrl=Ext.getCmp('size'+i).getValue();
            			}
	            	}
	            	
	            	for(var i=1;i<limit+1;i++){
	            		var sizeNumber=Ext.getCmp('size'+i).getValue();
	            		if(Ext.getCmp('size'+i).getValue()!=''){ 
	            			ids+= 'TS_0'+i+'|'+Ext.getCmp('size'+i).getValue();
							if( i != limit){
								ids += ',';
							}
	            		}
	            	}
	            	var tubiao = Ext.getCmp('tubiao').getValue();
	            	var id = Ext.getCmp('fwFunId').getValue();

	            	ifHas=1;
	            	data.ifHas = ifHas;
            		data.ids = ids;
            		data.tubiao=tubiao;
            		data.idd=id;
            		if(Ext.getCmp('defaultSize').getValue()!=''){
            			data.defaultSize=Ext.getCmp('defaultSize').getValue();
            		}
            		data.defaultUrl=defaultUrl;
	            }
	            debugger;
	                
	            Ext.Ajax.request({
	                url : basepath + '/Function-action.json',
	                method : 'POST',
	                params :data,
	                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                success : function() {
	                    Ext.Msg.alert('提示', '操作成功');
	                    Ext.Ajax.request({// 左侧模块功能树的Ajax请求事件
	                        url : basepath + '/fwFunctionTree-action.json?mdulId=' + moduleId,
	                        method : 'GET',
	                        success : function(response) {
	                    	    ifAdd=0;
	                            Ext.getCmp('fwFunId').setValue(Ext.getCmp('fid').value);
	                            var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
	                            loader.nodeArray = nodeArra;// 拿到从后台返回的数据
	                            loader.refreshCache();// 刷新缓存
	                            var children = loader.loadAll();// 得到相应的树数据
	                            leftTreeForShows.root.removeAll(true); // 清掉以前的数据
	                            leftTreeForShows.root.appendChild(children);// 把数据重新填充
	                        }
	                    });
	                    functionManage.getForm().reset();
	                    controllerStore.load();
	                },
	                failure : function(response) {
	                    var resultArray = Ext.util.JSON.decode(response.status);
	                    if (resultArray == 403) {
	                        Ext.Msg.alert('提示', response.responseText);
	                    } else {
	                        Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
	                }
	            });
	            
	            
	            
	            Ext.getCmp('funcManager').setDisabled(true);
	        }
	    }]
	}]
});


var ifAdd=0;
var leftTreeForShows = new Com.yucheng.bcrm.TreePanel({
	title : '模块功能',
	width:'30%',
	layout:'fit',
	region:'west',
	autoScroll : true,
	animate : true,
	tbar : [{ 
	    text : '新增',
	    iconCls : 'addIconCss',
	    handler : function() {// 调用新增方法
	    debugger;
	        functionManage.getForm().reset();
	        Ext.getCmp('moduleId').setValue(moduleId);
	        Ext.getCmp('funcManager').setDisabled(false);
	        ifAdd=1;
	    }
	}, '-', {
	    text : '删除',
	    iconCls : 'deleteIconCss',
	    handler : function() { // 调用删除方法
    	    if (confirm("提示!:删除该菜单项的同时将删掉其子菜单,请慎重! 继续删除吗?")) {
    	        if(nodeId!="root") {
    	            Ext.Ajax.request({
    	               url : basepath + '/Function-action!destroy.json?idStr=' + nodeId,
    	               waitMsg : '正在删除数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                        success : function() {
                            Ext.Msg.alert('提示', '操作成功');
                        Ext.Ajax.request({// 左侧模块功能树的Ajax请求事件
                            url : basepath + '/fwFunctionTree-action.json?mdulId=' + moduleId,
                            method : 'GET',
                            success : function(response) {
                                var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
                                loader.nodeArray = nodeArra;// 拿到从后台返回的数据
                                loader.refreshCache();// 刷新缓存
                                var children = loader.loadAll();// 得到相应的树数据
                                leftTreeForShows.root.removeAll(true); // 清掉以前的数据
                                leftTreeForShows.root.appendChild(children);// 把数据重新填充
                                controllerStore.reload();
                                functionManage.getForm().reset();
                                Ext.getCmp('funcManager').setDisabled(true);
                                }
                            });
                        },
                        failure : function() {
                            Ext.Msg.alert('提示', '操作失败!');
                        }
    	            });
    	        }
    	        else{
    	            Ext.Msg.alert('提示', '只能对子功能点进行删除操作!');
    	        }
    	    }
	    }
	}],
	root : new Ext.tree.AsyncTreeNode({// 虚拟树形根节点
		id : 'root',
		text : '模块功能点列表',
		autoScroll : true,
		expanded : true,
		leaf : false,
		children : []
	}),
	resloader : loader,
	split : true,
    clickFn : function(node) {// 点击事件，当点击该节点的时候先判断是否节点，若不是则获取该模块功能的ID和Name给菜单的模块功能赋值
        
        functionManage.getForm().reset();
        loader.refreshCache();
        nodeId = node.attributes.id;	
        Ext.getCmp('funcName').setValue(node.attributes.FUNC_NAME);
		Ext.getCmp('funcDesc').setValue(node.attributes.FUNC_DESC);
		Ext.getCmp('action').setValue(node.attributes.ACTION);
		Ext.getCmp('moduleId').setValue(node.attributes.MODULE_ID);
		Ext.getCmp('fid').setValue(nodeId);
		addControllerFrom.getForm().reset();
		Ext.getCmp('fwFunId').setValue(nodeId);
		
		Ext.Ajax.request({// 左侧模块功能树的Ajax请求事件
			url : basepath	+ '/fwFunctionExtTree-action.json',
			params: {id:nodeId},
			method : 'GET',
			success : function(response) {
				
				var nodeArray = Ext.util.JSON.decode(response.responseText).json.data;

				if(nodeArray.length!=0){
				Ext.getCmp('tubiao').setValue(nodeArray[0].TILE_LOGO);
				var supportSizeUrl=nodeArray[0].SUPPORT_SIZE_URL;
				var defaultSize=nodeArray[0].DEFAULT_SIZE;
//				var a=Ext.getCmp('defaultSize').setValue();
				var b=defaultSize.split("_0");
				Ext.getCmp('defaultSize').setValue(b[1]);
				
				var a=supportSizeUrl.split(",");
				if(a.length!=0 && a!=""){
				for(var i=0;i<a.length;i++)
				{
					var b=a[i].split("_0");
					var c=b[1].split("|");
					Ext.getCmp('size'+c[0]).setValue(c[1]);
				}
				}
				}
			}
		});
		
		
		Ext.getCmp('funcManager').setDisabled(true);
		if (nodeId != '' && nodeId != 'root') {
		    Ext.getCmp('funcManager').setDisabled(false);
			controllerStore.load({
			    params : {
			        'fwFunId' : nodeId
			    }
			});
		}else{
		    functionManage.getForm().reset();
		    controllerStore.load();
		}
	}
});

var sm = new Ext.grid.CheckboxSelectionModel();// 复选框

var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
    header : 'No.',
    width : 28
});

var cm = new Ext.grid.ColumnModel([rownum, sm, // 定义列模型
{
    header : "ID",
    dataIndex : "id",
    hidden : true,
    sortable : true
}, {
    header : "控制名称",
    dataIndex : "name",
    sortable : false
}, {
    header : "控制代码",
    dataIndex : "conCode",
    sortable : false
}, {
    header : "备注",
    dataIndex : "remark",
    sortable : false
}]);
var controllerQueryRecord = Ext.data.Record.create([
    { name : 'fwFunId',mapping : 'FW_FUN_ID'},
    { name : 'id',     mapping : 'ID'},
    { name : 'name',   mapping : 'NAME'},
    { name : 'conCode',mapping : 'CON_CODE'},
    { name : 'remark', mapping : 'REMARK'}
]);

var controllerStore = new Ext.data.Store({//功能控制STORE数据源
    restful : true,
    proxy : new Ext.data.HttpProxy({
        url : basepath + '/controllerManagerQuery.json',
        method : 'GET'
    }),
    reader : new Ext.data.JsonReader({
        successProperty : 'success',
        idProperty : 'ID',
        messageProperty : 'message',
        root : 'json.data',
        totalProperty : 'json.count'
    }, controllerQueryRecord)
});


var funcGrid = new Ext.grid.GridPanel({// 定义GRID
	stripeRows : true,
	frame : true,
	id : 'funcGrid',
	autoScroll : true,  //自动出现滚动条
	region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
	cm : cm, // 列模型
	sm : sm, // 复选框
	tbar : [{ 
	    text : '添加控制',
	    iconCls : 'addIconCss',
	    handler : function() {
	        if (Ext.getCmp('fwFunId').getValue() == '') {
	            Ext.Msg.alert('提示', '请选择一个功能点');
	        } else {
	            addControllerWindow.show();// 弹出添加控制代码窗口
	        }
	    }
	}, '-', {
	    text : '删除控制',
	    iconCls : 'deleteIconCss',
	    handler : function() {
	        var selectLength = funcGrid.getSelectionModel().getSelections().length;
	        if (selectLength < 1) {
	            alert('请选择需要删除的控制!');
	        } else {
	            if (confirm("确定删除吗?")) {
	                var selectRe;
	                var tempId;
	                var idStr = '';
	                for (var i = 0; i < selectLength; i++) {// 循环获取所选列id并拼装成字符串
	                    selectRe = funcGrid.getSelectionModel().getSelections()[i];
	                    tempId = selectRe.data.id;
	                    idStr += tempId;
	                    if (i != selectLength - 1) {
	                        idStr += ',';
	                    }
	                }
	                Ext.Ajax.request({
	                    url : basepath + '/Controllers-action!destroy.json?idStr=' + idStr,
	                    waitMsg : '正在删除数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                    success : function() {
	                        Ext.Msg.alert('提示', '操作成功!');
	                        Ext.Ajax.request({// 左侧模块功能树的Ajax请求事件
	                            url : basepath + '/fwFunctionTree-action.json?mdulId=' + moduleId,
	                            method : 'GET',
	                            success : function(response) {
	                                Ext.getCmp('fwFunId').setValue(Ext.getCmp('fid').value);
	                                var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
	                                loader.nodeArray = nodeArra;// 拿到从后台返回的数据
	                                loader.refreshCache();// 刷新缓存
	                                var children = loader.loadAll();// 得到相应的树数据
	                                leftTreeForShows.root.removeAll(true); // 清掉以前的数据
	                                leftTreeForShows.root.appendChild(children);// 把数据重新填充
	                            }
	                        });
	                        controllerStore.reload();
	                    },
	                    failure : function() {
	                        Ext.Msg.alert('提示', '操作失败!');
	                    }
	                });
	            }
	        }
	    }
	}], // 表格工具栏
	store : controllerStore,
	autoExpandColumn : 5,
	viewConfig : {
		forceFit : true,
		autoScroll : false
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var addControllerFrom = new Ext.form.FormPanel({// 添加控制Panel
    frame : true,
    region : 'center',
    bodyStyle : 'padding:5px 5px 0',
    split : true,
    items : [{
        layout : 'form',
        items : [{
            allowBlank : false,
            id : 'conName',
            name : 'name',
            xtype : 'textfield',
            fieldLabel : '<font color=red>*</font>控制名称',
            anchor : '95%'
        }, {
            id : 'conCode',
            name : 'conCode',
            xtype : 'textfield',
            allowBlank : false,
            fieldLabel : '<font color=red>*</font>控制代码',
            anchor : '95%'
        }, {
            id : 'remark',
            name : 'remark',
            xtype : 'textarea',
            fieldLabel : '备注',
            anchor : '95%'
        }, {
            id : 'fwFunId',
            name : 'fwFunId',
            hidden : 'true',
            xtype : 'numberfield',
            fieldLabel : '功能点ID',
            anchor : '95%'
        }]
    }]
});

var addControllerWindow = new Ext.Window({// 定义添加控制窗口
    title : '控制添加',
    plain : true,
    layout : 'fit',
    width : 400,
    height : 200,
    resizable : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true, // 模态窗口
    loadMask : true,
    collapsible : true,
    titleCollapse : true,
    border : false,
    constrain : true,// True表示为将window约束在视图中显示，false表示为允许window在视图之外的地方显示（默认为false）。
    animCollapse : true, // True 表示为面板闭合过程附有动画效果
    buttonAlign : 'center',
    items : [addControllerFrom],
    buttons : [{
        text : '保存',
        handler : function() {
            if (!addControllerFrom.getForm().isValid()) {
                Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
                return false;
            }

            Ext.Ajax.request({
                url : basepath + '/Controllers-action.json',
                method : 'POST',
                params : addControllerFrom.getForm().getFieldValues(),
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                success : function() {
                    Ext.Msg.alert('提示', '操作成功');
                    if (nodeId != '') {
                        controllerStore.load({
                            params : {
                                'fwFunId' : nodeId
                            }
                        });
                    }
                    Ext.Ajax.request({// 左侧模块功能树的Ajax请求事件
                        url : basepath + '/fwFunctionTree-action.json?mdulId=' + moduleId,
                        method : 'GET',
                        success : function(response) {
                            Ext.getCmp('fwFunId').setValue(Ext.getCmp('fid').value);
                            var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
                            loader.nodeArray = nodeArra;// 拿到从后台返回的数据
                           
                            loader.refreshCache();// 刷新缓存
                            var children = loader.loadAll();// 得到相应的树数据
                            leftTreeForShows.root.removeAll(true); // 清掉以前的数据
                            leftTreeForShows.root.appendChild(children);// 把数据重新填充
                        }
                    });
                },
                failure : function() {
                    Ext.Msg.alert('系统提示信息', '操作失败');
                }
            });
            addControllerWindow.hide();
            addControllerFrom.getForm().reset();
        }
    }, {
        text : '关闭',
        handler : function() {
            addControllerFrom.getForm().reset();
            addControllerWindow.hide();
        }
    }]
});



var accordion = new Ext.Panel({//用于页签展示的Panel
	region:'east',
	margins:'5 10 5 0',
	split:true,
	width: 280,
	layout:'fit',
	items: [iconView]
});

var viewWindow = new Ext.Window({// 定义功能点管理窗口
    draggable : true,// 是否可以拖动
    closable : true, // 是否可关闭
    modal : true,    //遮罩
    closeAction : 'hide',
    maximized : false,// 默认最大化
    width:1200,
    height:450,
    resizable : true,
    collapsible : false, //可收缩的
    constrain : true,
    autoScroll : true,
    animCollapse : false,
    border : false,
    layout:'border',
    items : [leftTreeForShows, {
        region:'center',
        layout:'border',
        items:[functionManage,{
            region:'center',
            layout:'fit',
            items:[funcGrid]
        }] 
    },accordion]
});


Ext.Ajax.request({//左侧菜单项树的Ajax请求事件
	url : basepath + '/menuInit.json',
	method:'GET',
	success:function(response){
	
		var nodeArra = Ext.util.JSON.decode(response.responseText);
		Ext.each(nodeArra.json.data,function(n){
			if(n.ICON){
				var test;
				test = Ext.util.Format.substr(n.ICON,0,1);
				if(test=='/')
				{n.icon = basepath+'/'+'contents'+n.ICON;//对从台读取图标数据进行处理，变成绝对路径，那样才能显示图标
				}else{
					n.icon = '';
				}
			}
		});

	}
});


	