/*
 * 姚亮
 * 20110523
 * */

	Ext.QuickTips.init(); 
	
	/**
	 * 产品-展示详情store
	 */
	var viewDetailStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=PRODUCT_VIEW_DETAIL'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	
	var prodFormAdd = new Ext.form.FormPanel({
		frame:true,
		labelWidth:120,
		buttonAlign:'center',	
		items:[{
			xtype:'textfield',
			fieldLabel:'产品类别名称',
			anchor:'90%',
			maxLength : '20',
            labelStyle: 'text-align:right;',
			minLength : '1',
			allowBlank : false,
			id : 'catlNameAdd',
			name:'catlName'
		},
		{
			xtype:'textfield',
			fieldLabel:'上级产品类别编码',
			anchor:'90%',
			readOnly:true,
	        labelStyle: 'text-align:right;',
			allowBlank : false,
			id : 'catlParentAdd',
			name:'catlParent'
		},
		{
			xtype:'textfield',
			fieldLabel:'上级产品类别名称',
			anchor:'90%',
            labelStyle: 'text-align:right;',
			allowBlank : false,
			readOnly:true,
			id : 'catlParentNameAdd',
			name:'catlParentName'
		},{
			xtype:'numberfield',
			fieldLabel:'节点顺序',
			anchor:'90%',
            labelStyle: 'text-align:right;',
			allowBlank : true,
			id : 'catlOrderAdd',
			regexText:'限输入数字!',
			name:'catlOrder'
		},{
			store:viewDetailStore,
			xtype:'combo',
			resizable : true,
			fieldLabel:'展示详情',
			id : 'viewDetailAdd',
			name:'viewDetail3',
			hiddenName : 'viewDetail',
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
		},
		{
			xtype:'textfield',
			hidden:true,
            labelStyle: 'text-align:right;',
            id : 'catlLevelAdd',
			name:'catlLevel'
		}],
		buttons:[{
			text:'保  存',
			handler : function()
			{
				if(Ext.getCmp("catlParent").getValue()==undefined){
					Ext.Msg.alert("系统提醒","请选择上级产品！");
					return;
				}
				if(!prodFormAdd.getForm().isValid()){
					Ext.Msg.alert("提醒","请填写必填项");
					return ;
				}
				Ext.Ajax.request({
					url:basepath+'/product-kinds.json?a=1',
					method:'POST',
					form:prodFormAdd.getForm().id,
					success :checkResult,
					failure :checkResult
				});
				function checkResult(response) {
					
					var resultArray = Ext.util.JSON.decode(response.status);
					var resultError = response.responseText;
					if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
						Ext.Msg.alert('提示', '操作成功');
						Ext.Ajax.request({
							url : basepath+'/session-info!getPid.json',
							method : 'GET',
							success : function(a,b,v) {
								var node = {};
								node.CATL_NAME = Ext.getCmp("catlNameAdd").getValue();
								node.CATL_CODE = Ext.decode(a.responseText).pid;
								node.CATL_PARENT = Ext.getCmp('catlParentAdd').getValue();
								node.CATL_ORDER = Ext.getCmp('catlOrderAdd').getValue();
								node.VIEW_DETAIL = Ext.getCmp('viewDetailAdd').getValue();
								Ext.getCmp("productLeftTreePanel").addNode(node);
							},
							failure : function(a,b,c) {
								Ext.Msg.alert('提示','获取群序列号错误!');
							}
						});
						prodCategAddWind.hide();
						//Ext.getCmp("productFormShow").getForm().reset();
					} else{
						if(resultArray == 403){
							Ext.Msg.alert('提示', response.responseText);
						}
						else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
							prodFormAdd.getForm().reset();
							Ext.getCmp("productFormForShow").getForm().reset();
							prodCategAddWind.hide();
						}
					}
				}
			}
		},'-',{
			text:'返     回',
			handler:function()
			{
				prodCategAddWind.hide();
			}
		}]
	});

	var prodFormEdit = new Ext.form.FormPanel({
		frame:true,
		labelWidth:120,
		buttonAlign:'center',
		buttons:[{
		text:'保  存',
		handler:function()
		{
			Ext.Ajax.request({
				url:basepath+'/product-kinds.json?a=2',
				method:'POST',
				form:prodFormEdit.getForm().id,
				success :checkResult,
				failure :checkResult
			});
			function checkResult(response) {//不需要刷新树，使修改的节点显示
				var node = {};
				function putNode() {
					node.CATL_NAME = Ext.getCmp("catlName1").getValue();
					node.CATL_PARENT = Ext.getCmp("catlParent1").getValue();
					node.CATL_ORDER = Ext.getCmp('catlOrder1').getValue();
					node.VIEW_DETAIL = Ext.getCmp('viewDetail1').getValue();
				};
				putNode();
				node.CATL_CODE = Ext.getCmp("catlCode1").getValue();
				Ext.getCmp("productLeftTreePanel").root.findChild('id', node.CATL_CODE, true).attributes.CATL_PARENT =Ext.getCmp("catlParent1").getValue();
				Ext.getCmp("productLeftTreePanel").root.findChild('id', node.CATL_CODE, true).attributes.CATL_NAME =Ext.getCmp("catlName1").getValue();
				Ext.getCmp("productLeftTreePanel").root.findChild('id', node.CATL_CODE, true).attributes.CATL_CODE =Ext.getCmp("catlCode1").getValue();
				Ext.getCmp("productLeftTreePanel").root.findChild('id', node.CATL_CODE, true).attributes.CATL_ORDER =Ext.getCmp("catlOrder1").getValue();
				Ext.getCmp("productLeftTreePanel").root.findChild('id', node.CATL_CODE, true).attributes.VIEW_DETAIL =Ext.getCmp("viewDetail1").getValue();
				Ext.getCmp("productLeftTreePanel").editNode(node);
				
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
					Ext.Msg.alert('提示', '操作成功');
					prodCategEditWind.hide();
					//Ext.getCmp("productFormShow").getForm().reset();
				} else{
					if(resultArray == 403){
						Ext.Msg.alert('提示', response.responseText);
					}
					else {
						Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
						prodCategEditWind.hide();
						//Ext.getCmp("productFormShow").getForm().reset();
					}
				}
			};
		}
	},
	{
			text:'返     回',
			handler:function()
			{
				prodCategEditWind.hide();
			}
	}],		
	items:[		
		{					
			xtype:'textfield',						
			hidden:true,
			name:'catlCode',
			id:'catlCode1'
			
		},
		{
			xtype:'textfield',
			fieldLabel:'产品类别名称',
			anchor:'90%',
             labelStyle: 'text-align:right;',
			name:'catlName',
			id:'catlName1'
		},
		{
			xtype:'textfield',
			fieldLabel:'上级产品类别编码',
			anchor:'90%',
			readOnly:true,
			name:'catlParent',
            labelStyle: 'text-align:right;',
			id:'catlParent1'
		},
		{
			xtype:'textfield',
			fieldLabel:'上级产品类别名称',
			anchor:'90%',
			readOnly:true,
            labelStyle: 'text-align:right;',
			name:'catlParentName',
			id:'catlParentName1'
		},
		{

			xtype:'numberfield',
			fieldLabel:'节点顺序',
			anchor:'90%',
            labelStyle: 'text-align:right;',
			allowBlank : false,
			id:'catlOrder1',
			regexText:'限输入数字!',
			name:'catlOrder'
		},
		{
			store:viewDetailStore,
			xtype:'combo',
			resizable : true,
			fieldLabel:'展示详情',
			id:'viewDetail1',
			name:'viewDetail1',
			hiddenName : 'viewDetail',
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
		},					
		{
			xtype:'textfield',
			anchor:'90%',
             labelStyle: 'text-align:right;',
			hidden:true,
			name:'catlLevel',
			id:'catlLevel1'
		},
		{
			xtype:'textfield',
			anchor:'90%',
		     labelStyle: 'text-align:right;',
			hidden:true,
			name:'isLeaf',
			id:'isLeaf1'
		}]
	});
	
	var prodCategAddWind = new Ext.Window({
		layout : 'fit',
		closeAction:'hide',
		closable:true,
		title:'新增子类别',
		height:350,
		width:450,
		items:prodFormAdd
	});
	
	var prodCategEditWind = new Ext.Window({
		layout : 'fit',
		closeAction:'hide',
		closable:true,
		title:'修改子类别',
		height:350,
		width:450,
		items:prodFormEdit
	});	