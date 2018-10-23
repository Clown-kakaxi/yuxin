/**
 * 姚亮
 * 20110523
 */
Ext.onReady(function(){
	
	/**
	 *产品树数据加载 
	 */			
	var productTreeLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'CATL_PARENT',//指向父节点的属性列
		locateAttr : 'CATL_CODE',//节点定位属性列，也是父属性所指向的列
		rootValue :'0',//虚拟根节点id 若果select的值为null则为根节点
		textField : 'CATL_NAME',//用于展示节点名称的属性列
		idProperties : 'CATL_CODE'//,//指定节点ID的属性列
	});
	
	/**
	 * 产品树数据请求
	 */
	Ext.Ajax.request({//请求产品树数据
		url : basepath + '/productCatlTreeAction.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			productTreeLoader.nodeArray = nodeArra;
			var children = productTreeLoader.loadAll();
			Ext.getCmp('productLeftTreePanel').appendChild(children);
		}
	});

	/**
	 * 产品树面板，在模块左侧显示
	 */
	var productLeftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		id:'productLeftTreePanel',
		height : document.body.clientHeight,
		width : 200,
		autoScroll:true,
		checkBox : false, //是否现实复选框：
		_hiddens : [],
		resloader:productTreeLoader,//加载产品树
		region:'west',//布局位置设置
		split:true,
		root: new Ext.tree.AsyncTreeNode({//设置根节点
			id:'root',
			expanded:true,
			text:'银行产品树',
			autoScroll:true,
			children:[]
		}),
		clickFn:function(node){//单击事件，当单击树节点时触发并且获得这个节点的CATL_CODE
			if(node.attributes.CATL_CODE == undefined){
				Ext.MessageBox.alert('提示', '不能选择根节点,请重新选择 !');
				return;
			}else{
				productFormForShow.getForm().load({//为右侧表单加载数据
					restful:true,	
					url:basepath+'/product-kinds1.json',
					method: 'GET',
					params : {
						'nodeid':node.attributes.CATL_CODE,
						start : 0
					}
				});
			}
	 	}
	 });
	var record = Ext.data.Record.create([
	     {name: 'id', mapping: 'ID'},
         {name :'isLeaf',mapping :'IS_LEAF'},
         {name: 'catlName', mapping: 'CATL_NAME'},  
         {name: 'catlCode', mapping: 'CATL_CODE'},
         {name: 'catlParent', mapping: 'CATL_PARENT'},
         {name: 'catlParentName', mapping: 'CATL_PARENT_NAME'},
         {name :'catlLevel',mapping :'CATL_LEVEL'},
         {name :'catlOrder',mapping :'CATL_ORDER'},
         {name :'viewDetail',mapping :'VIEW_DETAIL'},
         {name :'productId',mapping:'PRODUCT_ID'}
    ]);

	var productFormForShow = new Ext.form.FormPanel({
		id:'productFormShow',
		frame:true,
		title:'产品类别',
		region:'center',
		labelWidth:120,
		reader: new Ext.data.JsonReader({root:'json.data'},record),
		layout:'column',
        items:[{
            layout:'form',
            columnWidth:.5,
            items:[{
                xtype:'textfield',
                fieldLabel:'产品类别名称',
                anchor:'90%',
                labelStyle: 'text-align:right;',
                name:'catlName',
                id:'catlName'
            }, {
                xtype:'textfield',
                fieldLabel:'产品类别编码',
                 labelStyle: 'text-align:right;',
                anchor:'90%',
                name:'catlCode',
                id:'CtlCode'
            },{
                xtype:'textfield',
                fieldLabel:'产品节点顺序',
                 labelStyle: 'text-align:right;',
                anchor:'90%',
                name:'catlOrder',
                id:'catlOrder'
            },{
                xtype:'textfield',
                name:'productId',
                hidden:true,
                labelStyle: 'text-align:right;',
                id:'productId'
            }]
        },{
            columnWidth:.5,
            border:false,
            bodyBorder:false,
            layout:'form',
            items:[{
                xtype:'textfield',
                fieldLabel:'上级产品类别编码',
                anchor:'90%',
                labelStyle: 'text-align:right;',
                name:'catlParent',
                id:'catlParent'
            },{
                xtype:'textfield',
                fieldLabel:'上级产品类别名称',
                anchor:'90%',
                 labelStyle: 'text-align:right;',
                name:'catlParentName',
                id:'catlParentName'
            },{
                store:viewDetailStore,
                xtype:'combo',
                resizable : true,
                fieldLabel:'展示详情',
                id:'viewDetail',
                name:'viewDetail',
                hiddenName : 'viewDetail2',
                valueField : 'key',
                displayField : 'value',
                mode : 'local',
                editable : false,
                typeAhead : true,
                forceSelection : true,
                triggerAction : 'all',
                emptyText : '请选择',
                anchor:'90%',
                labelStyle: 'text-align:right;',
                allowBlank : true
            },{
                xtype:'textfield',
                name:'catlLevel',
                hidden:true,
                labelStyle: 'text-align:right;',
                id:'catlLevel'
            },{
                xtype:'textfield',
                name:'isLeaf',
                hidden:true,
                labelStyle: 'text-align:right;',
                id:'isLeaf'
            }]
        }],
            tbar:[{
			text:'新增子类别',
			iconCls:'addIconCss',
			handler:function()
			{
				prodFormAdd.getForm().reset();
				var catlLevel = productFormForShow.getForm().findField("catlLevel").value;
				var catlParent = productFormForShow.getForm().findField("catlCode").value;
				var catlParentName = productFormForShow.getForm().findField("catlName").value;
				if(catlLevel==null||catlLevel=="undefined")
				{
					Ext.Msg.alert("系统提醒","请选择上级产品类别！");
					return false;
				}
				prodFormAdd.getForm().findField("catlLevel").setValue (parseInt(catlLevel)+1);
				prodFormAdd.getForm().findField("catlParent").setValue(catlParent);
				prodFormAdd.getForm().findField("catlParentName").setValue(catlParentName);
				
				prodCategAddWind.show();
			}
		},{
			text:'修改本类别',
			iconCls:'editIconCss',
			handler:function()
			{
				prodCategEditWind.setTitle('修改本类别');
				var catlCode = productFormForShow.getForm().findField("catlCode").value;
				if(catlCode == null || catlCode == "" || catlCode=="null" || catlCode=="undefined")
				{
					Ext.MessageBox.alert('提示','根类别不能修改或删除');
					return;
				}
			 	Ext.getCmp("catlCode1").setValue(Ext.getCmp("CtlCode").getValue());
			 	Ext.getCmp("catlName1").setValue(Ext.getCmp("catlName").getValue());
			 	Ext.getCmp("catlParent1").setValue(Ext.getCmp("catlParent").getValue());
			 	Ext.getCmp("catlParentName1").setValue(Ext.getCmp("catlParentName").getValue());
			 	Ext.getCmp("catlLevel1").setValue(Ext.getCmp("catlLevel").getValue());
			 	Ext.getCmp("catlOrder1").setValue(Ext.getCmp("catlOrder").getValue());
			 	Ext.getCmp("viewDetail1").setValue(Ext.getCmp("viewDetail").getValue());
				prodCategEditWind.show();
			}
		},{
			text:'删除本类别',
			iconCls : 'deleteIconCss',
			handler:function()
			{
				var record = productLeftTreeForShow.getSelectionModel().getSelectedNode();
				var catlCode = productFormForShow.getForm().findField("catlCode").value;
				if(catlCode == null || catlCode == "" || catlCode=="null" || catlCode=="undefined")
				{
					Ext.MessageBox.alert('提示','根类别不能修改或删除');
					return;
				}
				if(record.leaf==true){//判断是否为叶子节点，非叶子节点的类别不可删除
					if(Ext.getCmp('productId').getValue()!='')
					{//判断是叶子节点的类别中是否已经有产品信息，有产品信息不可删除
						Ext.Msg.alert('系统提示', '该产品类别下已有产品信息，不可删除！');
						return;
					}
					if (confirm("确定删除吗?")) 
					{			
						Ext.Ajax.request({
							url:basepath+'/product-kinds/'+catlCode+'.json',
							method:'DELETE',
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success :checkResult,
							failure :checkResult
						});
						function checkResult(response) 
						{
							var resultArray = Ext.util.JSON.decode(response.status);
							var resultError = response.responseText;
							
							if ((resultArray == 200 ||resultArray == 201)) 
							{
								productLeftTreeForShow.deleteNode(record);
								Ext.Msg.alert('提示', '操作成功');
								productFormForShow.getForm().reset();
							}else{
								if(resultArray == 403)
								{
									Ext.Msg.alert('提示', response.responseText);
								}else {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
								}
							}
						};
					}
				}//end of if(record.leaf==true){...}
				else{
					Ext.Msg.alert('系统提示', '该产品类别下有子类别，不可删除！');
					return;
				}
			}//end of handle:function(){...}
		}]
	});

 	var view = new Ext.Viewport({
 		layout : 'fit',
		frame : true,
		items : [{
		    layout:'border', 
		    items:[productLeftTreeForShow,productFormForShow]
		}]
 	});
});