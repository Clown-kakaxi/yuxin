imports([
//	'/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js' //所有数据字典定义
//	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
]);
Ext.QuickTips.init();
//var needGrid = true;
//var formViewers = true;
//var url = basepath+'/callReportQueryAction.json';
Ext.ns('Wlj.view');
Wlj.view.CustView = Ext.extend(Ext.Panel,{
	layout: 'border',
	resId: '',	//视图资源ID,格式为： task_view$-$0$-$100,  0表示视图类型,100表示客户号或客户经理号
	custId: '', //客户号
	CURRENT_VIEW_URL: '',
	containDIVid : 'viewport_center',
	initComponent : function(){
        Wlj.view.CustView.superclass.initComponent.call(this);
        
		var _this = this;
        _this.resId = JsContext._resId;
        var resIdArr = this.resId.split('$-$');
        if(resIdArr.length < 3){
        	return;
        }
        if(resIdArr[3]==1){
        	_this.viewResId = '577224';
        }else{
        	_this.viewResId = '101515';
        }
         //客户查询界面、viewResId固定为101515/控制视图权限
        _this.custId = resIdArr[2];
        
        _this.initAllViewTree();
        _this.initMyViewTree();
		_this.viewPanel = new Ext.Panel({
			region:'center',
			html:'<iframe id="viewport_center" name="viewport_center" src="" style="width:100%;height:100%;" frameborder="no"/>'
		});
		
		_this.queryViewTree();
		_this.queryCustBase();
		_this.queryMyViewTree();
		
		_this.add(_this.viewPanel);
		_this.add({
			region:'west',
			id:'leftLayout',
			layout:'accordion',
			width:220,
			layoutConfig: {
		        titleCollapse: false,
		        animate: false,
		        hideCollapseTool : true,
		        activeOnTop: true
		    },
			items:[_this.viewTree,_this.myViewTree]
		});
		
		/*var typeStore = new Ext.data.Store({
			sortInfo: {
		    	field: 'key',
		    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
			},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
					url :basepath+'/lookup.json?name=REMIND_TYPE'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});*/
		
		var formtbar = [{
			id	: 'save',
			hidden:JsContext.checkGrant('Wlj-callreportview-base_save'),
			text:'保存',
			handler:function(){
				var custId = window.CUSTVIEW.custId;
				var custName = window.CUSTVIEW.custName;
//				var custName = window.CUSTVIEW.custId;   客户名称
//				console.info(Ext.getCmp("remindInfo"));
				if(Ext.getCmp("remindDt").getValue()!=""||Ext.getCmp("remindType").getValue()!=""){
					Ext.getCmp("remindDt").allowBlank = false;
					Ext.getCmp("remindType").allowBlank = false;
				}
				if((Ext.getCmp("callreport").getValue()!=""&&Ext.getCmp("remindDt").getValue()==""&&Ext.getCmp("remindType").getValue()=="")||(Ext.getCmp("callreport").getValue()!=""&&Ext.getCmp("remindDt").getValue()!=""&&Ext.getCmp("remindType").getValue()!="")||(Ext.getCmp("callreport").getValue()==""&&Ext.getCmp("remindDt").getValue()!=""&&Ext.getCmp("remindType").getValue()!="")){
					var id = Ext.getCmp("id").getValue();
					var createUser = Ext.getCmp("createUser").getValue();
					var createUsername = Ext.getCmp("createUsername").getValue();
					var createTime = Ext.getCmp("createTime").getValue();
					var createOrg = Ext.getCmp("createOrg").getValue();
					var remindDt = Ext.getCmp("remindDt").getRawValue();
					var callreport = Ext.getCmp("callreport").getValue();
//					var remindInfo = Ext.getCmp("remindInfo").getValue();
					var remindType = Ext.getCmp("remindType").getValue();
					Ext.Msg.wait('正在保存，请稍后......','系统提示');
					Ext.Ajax.request({
						url : basepath + '/callReportQueryAction!saveInfo.json',
						params :
//						CallReportForm.getForm().getFieldValues(),
							{
							'id' : id,
							'custId' : custId,
							'custName' : custName,
							'remindDt' : remindDt,
							'callreport' : callreport,
//							'remindInfo' : remindInfo,
							'remindType' : remindType
						},												
						method : 'GET',
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response) {
							Ext.Msg.alert('提示', '操作成功!');
						}
					})
					Ext.getCmp("remindDt").reset();
					Ext.getCmp("remindType").reset();
//				    Ext.getCmp("remindInfo").reset();
				}else{
					Ext.Msg.alert('提示','请输入内容！');
					return false;
				}
			}
		}]
		
		var CallReportForm=new Ext.form.FormPanel({
			region : 'south',
			frame : 'true',
			labelAlign : 'center',
			height : 120,
			labelWidth : 60,
			tbar:formtbar,
			buttonAlign : 'center',
			items : [{
				layout :'column',
				items :[{
					columnWidth : .8,
					layout : 'form',
					items : [{
						id : 'callreport',
						xtype : 'textarea',
						fieldLabel : 'callreport信息',
						height : 80,
						width : '90%'
//						allowBlank : false
					}]
				}/*,{
					columnWidth : .3,
					layout : 'form',
					items : [{
						id : 'remindInfo',
						xtype : 'textarea',
						fieldLabel : '提醒内容',
						height : 50,
						width : '90%'						
//						allowBlank : false
					}]
				}*/,{
					columnWidth : .2,
					layout : 'form',
					items : [{
						id : 'remindDt',
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : true,
						fieldLabel : '提醒日期',
						anchor:'70%',
						minValue : new Date()
//						allowBlank : false,						
					},{
						id : 'remindType',
						xtype : 'combo',
						fieldLabel : '提醒事项',
						mode : 'local',
						displayField : 'w',
						triggerAction:'all',
						anchor:'70%',
						emptyText : '请选择提醒事项',
						resizable : true,
						editable : true,
//						allowBlank : false,
//						store : typeStore,
//						displayField : 'value',
//						valueField : 'key',
//						resizable : true
						store : new Ext.data.SimpleStore({
							  fields : ['w'],
						      data : [['理财'], ['贷款' ], ['代发' ],['保险'],['存款'],['其他']]
						})
					},{
						id : 'createUser',
						xtype :'textfield',
						hidden : true,
						fieldLabel : '创建人'
					},{
						id : 'createUsername',
						xtype :'textfield',
						hidden : true,
						fieldLabel : '创建人姓名'
					},{
						id : 'createTime',
						xtype :'textfield',
						hidden : true,
						fieldLabel : '创建时间'
					},{
						id : 'createOrg',
						xtype :'textfield',
						hidden : true,
						fieldLabel : '创建人机构'
					},{
						id :'id',
						xtype :'textfield',
						hidden : true,
						fieldLabel : '主键'
					}/*,{
						buttons : [{
							id:'save',
							text:'保存',
							width : 80,
							xtype:'button',
							handler : function(){
									var custId = window.CUSTVIEW.custId;
									var custName = window.CUSTVIEW.custName;
					//					var custName = window.CUSTVIEW.custId;   客户名称
					//					console.info(Ext.getCmp("remindInfo"));
									if(Ext.getCmp("remindDt").getValue()!=""||Ext.getCmp("remindType").getValue()!=""){
										Ext.getCmp("remindDt").allowBlank = false;
										Ext.getCmp("remindType").allowBlank = false;
									}
									if(!CallReportForm.getForm().isValid()){
										Ext.Msg.alert('提示','请输入内容！');
										return false;
									}
									var id = Ext.getCmp("id").getValue();
									var createUser = Ext.getCmp("createUser").getValue();
									var createUsername = Ext.getCmp("createUsername").getValue();
					    			var createTime = Ext.getCmp("createTime").getValue();
					    			var createOrg = Ext.getCmp("createOrg").getValue();
									var remindDt = Ext.getCmp("remindDt").getValue();
									var callreport = Ext.getCmp("callreport").getValue();
//									var remindInfo = Ext.getCmp("remindInfo").getValue();
									var remindType = Ext.getCmp("remindType").getValue();
									Ext.Msg.wait('正在保存，请稍后......','系统提示');
									Ext.Ajax.request({
										url : basepath + '/callReportQueryAction!saveInfo.json',
										params :
					//						CallReportForm.getForm().getFieldValues(),
											{
											'id' : id,
											'createOrg' : createOrg,
											'createUser' : createUser,
											'createUsername' : createUsername,
											'createTime' : createTime,
											'custId' : custId,
											'custName' : custName,
											'remindDt' : remindDt,
											'callreport' : callreport,
//											'remindInfo' : remindInfo,
											'remindType' : remindType
										},												
										method : 'POST',
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : function(response) {
											Ext.Msg.alert('提示', '操作成功!');
										}
									})
									Ext.getCmp("remindDt").reset();
									Ext.getCmp("remindType").reset();
//									Ext.getCmp("remindInfo").reset();
								}
						}]				
					}*/]
				}]
			}]
			/*buttons : [{
				text:'重置',
				xtype:'button',
				handler : function(){
					Wlj.view.CustView.superclass.initComponent.call(this);
        
					var _this = this;
					_this.resId = JsContext._resId;
					var resIdArr = this.resId.split('$-$');
					if(resIdArr.length < 3){
						return;
					}
					_this.custId = resIdArr[2];
					Ext.Ajax.request({
						url : basepath + '/callReportQueryAction!serachToday.json',
//						param : {
//							'custId' : _this.custId
//						},	
						success :  function(response) {
							CallReportForm.getForm().reset();
							var aa = Ext.decode(response.responseText);
							Ext.getCmp("callreport").setValue(aa.callreport);
							Ext.getCmp("remindDt").setValue(aa.remindDt);
							Ext.getCmp("remindType").setValue(aa.remindType);
							Ext.getCmp("save").setDisabled(false);
//							Ext.Msg.alert('提示', _this.custId);
						}
					})
				}
			}]*/
		})
		
		var allCustRecord = new Ext.data.Record.create([
			    {text: 'id',name: 'id',hidden:true},
				{text: 'CALLREPORT信息',name: 'CALLREPORT_INFO'},
				{text: '创建时间',name: 'CREATE_TM'},
				{text: '创建人',name: 'CREATE_USER'},
				{text: '创建人姓名',name: 'CREATE_USERNAME'},
				{text: '客户号',name: 'CUST_ID',hidden:true},
				{text: '客户姓名',name: 'CUST_NAME',hidden:true},
//			    {text: '提醒时间',name: 'REMIND_TM',hidden:true},
//			    {text: '提醒类型',name: 'REMIND_TYPE',hidden:true},
//			    {text: '提醒信息',name: 'REMIND_INFO',hidden:true},
//			    {text: '创建时间',name:'CREATE_TM',hidden:true},
			    {text: '创建机构',name:'CREATE_ORG',hidden:true}
		]);
		var allCuststore =  new Ext.data.Store({
			autoLoad : true,
			restful:true,
			proxy : new Ext.data.HttpProxy({
				url:basepath+'/callReportQueryAction.json?custId='+_this.custId,
				method:'GET'
			}),		
			reader: new Ext.data.JsonReader({
				successProperty : 'success',
				messageProperty : 'message',
				idProperty : 'id',
				totalProperty : 'json.count',
				root:'json.data'
			},allCustRecord),
			recordType : allCustRecord
		});
		
		/*var allCuststore = new Ext.data.JsonStore({
			autoLoad : true,
			restful:true,
			url:basepath+'/callReportQueryAction.json?custId='+_this.custId,
			successProperty : 'success',
			messageProperty : 'message',
			idProperty : 'id',
			totalProperty : 'json.count',
			root:'json.data',
			fields	: [{text: 'id',name: 'id',hidden:true},
				{text: 'CALLREPORT信息',name: 'CALLREPORT_INFO'},
				{text: '客户号',name: 'CUST_ID'},
			    {text: '提醒时间',name: 'REMIND_TM'},
			    {text: '提醒类型',name: 'REMIND_TYPE'},
			    {text: '创建时间',name: 'CREATE_TM'},
			    {text: '创建人',name: 'CREATE_USER'}]
		});*/
		var allCustRn = new Ext.grid.RowNumberer({
		    header:'NO.',
		    width:35
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		
		var allCustCm = new Ext.grid.ColumnModel([
		    allCustRn,
		    {dataIndex:'CALLREPORT_INFO',header:'访谈内容',width : 260,sortable : true},
		    {dataIndex:'CREATE_TM',header:'录入时间',width : 50,sortable : true},
		    {dataIndex:'CREATE_USER',header:'记录人',width : 50,sortable : true},
		    {dataIndex:'CREATE_USERNAME',header:'记录人姓名',width : 50,sortable : true}
		]);
		
		var pagingComboAllCust =  new Ext.form.ComboBox({
		    name : 'pagesize',
		    triggerAction : 'all',
		    mode : 'local',
		    store : new Ext.data.ArrayStore({
		        fields : ['value', 'text'],
		        data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ]]
		    }),
		    valueField : 'value',
		    displayField : 'text',
		    value: 10,
		    editable : false,
		    width : 85
		});
		var pagingbarAllCust = new Ext.PagingToolbar({
			pageSize : parseInt(pagingComboAllCust.getValue()),
			store : allCuststore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',       
			items : ['-', '&nbsp;&nbsp;', pagingComboAllCust]
		});
		pagingComboAllCust.on("select", function(comboBox) {
			pagingbarAllCust.pageSize = parseInt(comboBox.getValue()),
		    allCuststore.reload({
		        params : {
		            start : 0,
		            limit : parseInt(comboBox.getValue())
		        }
		    });
		});
		
		var tbar = [{
			text:'修改',
			hidden:JsContext.checkGrant('Wlj-callreportview-base_edit'),
			handler:function(){	
				var checkedNodes = grid.getSelectionModel().selections.items;
				if(checkedNodes.length !=1){
					Ext.Msg.alert('提示','请选择数据！');
					return false;
				}
//		    	console.info(checkedNodes[0].get("REMIND_TM"));
	    		var id = checkedNodes[0].get("ID")
	    		var createUser = checkedNodes[0].get("CREATE_USER")
	    		var createUsername = checkedNodes[0].get("CREATE_USERNAME")
	    		var createTime = checkedNodes[0].get("CREATE_TM")
	    		var createOrg = checkedNodes[0].get("CREATE_ORG")
	    		var callreport = checkedNodes[0].get("CALLREPORT_INFO");
//	    		var remindDt = checkedNodes[0].get("REMIND_TM");
//		    	var remindDt = new Date(checkedNodes[0].get("REMIND_TM")).format('Y-m-d');
//	    		var remindType = checkedNodes[0].get("REMIND_TYPE");
	    		Ext.getCmp("id").setValue(id);
	    		Ext.getCmp("createUser").setValue(createUser);
	    		Ext.getCmp("createUsername").setValue(createUsername);
	    		Ext.getCmp("createTime").setValue(createTime);
	    		Ext.getCmp("createOrg").setValue(createOrg);
	    		Ext.getCmp("callreport").setValue(callreport);
//	    		alert(__userId);
//	    		alert(createUser);
	    		if(__userId==createUser){
	    			Ext.getCmp("save").setDisabled(true);
	    		}
			}
		},
		/**************导出*******************/
		new Com.yucheng.crm.common.NewExcelButton({
		    formPanel : 'searchCondition',
		    hidden:JsContext.checkGrant('Wlj-callreportview-base_export'),
		    url : basepath+'/callReportQueryAction.json?custId='+_this.custId
		})]
		
		var grid = new Ext.grid.GridPanel({
			itemId : 'grid',
			frame: true,
			hideLabel:false,
		    autoScroll: true,
		    stripeRows: true,
		    region : 'north',
		    store: allCuststore,
		    cm : allCustCm,
		    autoHeigh:true,
		    bbar: pagingbarAllCust,
		    tbar: tbar,
		    emptyMsg :'没有符合条件的记录',
		    viewConfig:{
		    	forceFit : true
		    },
		    loadMask: {
		        msg: '正在加载表格数据,请稍等...'
		    }
		})
		this.grid = grid;
		_this.add({
//			title	: 'callreport',
			autoScroll : true,
			region	: 'south',
			height	: 200,
			items	: [CallReportForm,{
				xtype : 'fieldset',
				title : '历史信息',
				titleCollapse : true,
				collapsible : true,
				collapsed	: true,
				autoHeight : true,
				layout	: 'fit',
				items	: [grid]
			}]
		});
			Ext.Ajax.request({
				url : basepath + '/callReportQueryAction!serachToday.json',
				params : {
							'custId' : _this.custId				
						},
				method:'GET',
				success :  function(response) {
					CallReportForm.getForm().reset();
					var aa = Ext.decode(response.responseText);
					Ext.getCmp("id").setValue(aa.id);
					Ext.getCmp("createUser").setValue(aa.createUser);
					Ext.getCmp("createOrg").setValue(aa.createOrg);
					Ext.getCmp("createUsername").setValue(aa.createUsername);
					Ext.getCmp("createTime").setValue(aa.createTime);
					Ext.getCmp("callreport").setValue(aa.callreport);
//					Ext.getCmp("remindDt").setValue(aa.remindDt);
//					Ext.getCmp("remindType").setValue(aa.remindType);
					Ext.getCmp("save").setDisabled(false);
				}
			})
	},
	/**
	 * 初始化全部视图
	 */
	initAllViewTree : function(){
		var _this = this;
		_this.viewLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
			parentAttr : 'PARENTID',
			idProperties : 'ID',
			textField : 'NAME',
			locateAttr : 'ID',
			rootValue : '0'
		});
		_this.viewTree = new Com.yucheng.bcrm.TreePanel({
			title:'全部客户视图',
			width:220,
			region:'west',
			rootVisible:false,
			collapsible:false,
			autoScroll :true,
			root: new Ext.tree.AsyncTreeNode({
				id :'0',
				text:'客户视图',
				expanded:true,
				autoScroll:true,
				children:[]
			}),
			resloader:_this.viewLoader,
			tools : [{
	        	id:'allview',
	        	handler:function(e,target,panel){
	        		_this.switchView(false);
	        	}
	       	}],
		    listeners: {
		    	'click':function(node){
					_this.viewClickHandler(node,_this.viewTree,_this.myViewTree);
				},
		        contextmenu: function(node, e) {
		        	if(!node.leaf){
		        		return;
		        	}
		        	var c = node.getOwnerTree().contextMenu;
				    var tn = _this.myViewTree.root.findChild('id',node.id,true);
					if(tn){
						c.items.items[0].setVisible(false);
						c.items.items[1].setVisible(true);
					}else{
						c.items.items[0].setVisible(true);
						c.items.items[1].setVisible(false);
					}
		            c.contextNode = node;
		            c.showAt(e.getXY());
		        }
		    },
		    contextMenu: new Ext.menu.Menu({
		        items: [{
		            id: 'addnode',
		            iconCls:'ico-g-1',
		            text: '添加到我的视图'
		        },{
		            id: 'removenode',
		            iconCls:'ico-g-2',
		            text: '从我的视图移除'
				}],
		        listeners: {
		            itemclick: function(item) {
		                switch (item.id) {
		                    case 'addnode':
		                    {
		                    	var node = item.parentMenu.contextNode;
		                    	_this.addMyView(node);
		                        break;
		                    }
		                    case 'removenode':
		                    {
		                    	var node = item.parentMenu.contextNode;
		                    	var tn = _this.myViewTree.root.findChild('id',node.id,true);
								if(tn){
									 _this.removeMyView(tn);
								}
		                        break;
		                    }
		                }
		            }
		        }
		    })
		});
	},
	/**
	 * 初始化我的视图
	 */
	initMyViewTree : function(){
		var _this = this;
		_this.myViewLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
			parentAttr : 'PARENTID',
			idProperties : 'ID',
			textField : 'NAME',
			locateAttr : 'ID',
			rootValue : '0'
		});
		_this.myViewTree = new Com.yucheng.bcrm.TreePanel({
			title:'我的客户视图',
			rootVisible:false,
			hidden:true,
			autoScroll :true,
			root: new Ext.tree.AsyncTreeNode({
				id :'0',
				text:'我的客户视图',
				expanded:true,
				autoScroll:true,
				children:[]
			}),
			resloader:_this.myViewLoader,
			tools : [{
	        	id:'myview',
	        	handler:function(e,target,panel){
	        		_this.switchView(true);
	        	}
	       	}],
	       	listeners:{
	       		beforeclick : function(node,e){
	       			var a = e.getTarget('a');
	       			if(a && a.className === 'view-remove'){
	       				//从我的客户视图中移除
	       				_this.removeMyView(node);
	       				//返回false阻止树节点click事件触发
	       				return false;
	       			}
	       		},
				'click':function(node){
					_this.viewClickHandler(node,_this.myViewTree,_this.viewTree);
				}
		    }
		});
	},
	/**
	 * 查询全部客户视图树
	 */
	queryViewTree : function(){
		var _this = this;
		Ext.Ajax.request({
			url : basepath + '/queryCustViewAuthorize!queryCustViewTree.json?custId='+_this.custId,
			method:'GET',
			success:function(response){
				var nodeArra = Ext.util.JSON.decode(response.responseText).JSON.data;
				_this.viewTree.resloader.nodeArray = nodeArra;//重新获取后台数据
				_this.viewTree.resloader.refreshCache(); // 刷新缓存
				var children = _this.viewTree.resloader.loadAll(); //得到相应的树数据
				_this.viewTree.root.removeAll(true); // 清掉以前的数据
				_this.viewTree.appendChild(children);// 把数据重新填充
				_this.viewTree.expandAll();
				var tn = _this.viewTree.root.findChild('NAME','客户信息首页',true);
				if(tn){
					tn.fireEvent('click',tn);
				}
			}
		});
	},
	/**
	 * 查询我的客户视图树
	 */
	queryMyViewTree : function(){
		var _this = this;
		Ext.Ajax.request({
			url : basepath + '/queryCustViewAuthorize!queryMyViewTree.json?custId='+_this.custId,
			method:'GET',
			success:function(response){
				if(Ext.util.JSON.decode(response.responseText).JSON == null){
					Ext.Msg.alert('提示','客户视图树加载失败');
					return false;
				}
				var nodeArra = Ext.util.JSON.decode(response.responseText).JSON.data;
				_this.myViewTree.resloader.nodeArray = nodeArra;//重新获取后台数据
				_this.myViewTree.resloader.refreshCache(); // 刷新缓存
				var children = _this.myViewTree.resloader.loadAll(); //得到相应的树数据
				Ext.each(children,function(i){
					i.uiProvider = Wlj.view.TreeNodeUI;
				});
				_this.myViewTree.root.removeAll(true); // 清掉以前的数据
				_this.myViewTree.appendChild(children);// 把数据重新填充
				_this.myViewTree.expandAll();
				
				var node = _this.viewTree.getSelectionModel().getSelectedNode();
				if(node){
					//判断全部视图上选择的节点，是否存在
					var tn = _this.myViewTree.root.findChild('id',node.id,true);
					if(tn){
						tn.select();
					}
				}
			}
		});
	},
	/**
	 * 请求客户基础信息
	 */
	queryCustBase : function(){
		var _this = this;
		Ext.Ajax.request({
			url : basepath + '/queryCustViewAuthorize!queryCustBase.json?custId='+_this.custId,
			method:'GET',
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText).JSON.data;
				if(data.length >0){
					_this.custName = data[0].CUST_NAME;
					_this.custType = data[0].CUST_TYPE;
				}else{
					Ext.Msg.alert('提示','客户信息加载失败');
				}
			}
		});
	},
	/**
	 * 视图click事件触发
	 * @param {} node	当前节点
	 * @param {} currViewTree	当前点击树
	 * @param {} otherViewTree  当前未点击树
	 */
	viewClickHandler : function(node,currViewTree,otherViewTree){
		if (node.attributes.ADDR && node.attributes.ADDR != '0') {
			//_this.viewPanel.setTitle(node.text);
			this.CURRENT_VIEW_URL = node.attributes.ADDR;
			var url = this.builtfunctionurl(node.attributes.ADDR);
			document.getElementById(this.containDIVid).src = url;
			otherViewTree.getSelectionModel().clearSelections();
			var tn = otherViewTree.root.findChild('id',node.id,true);
			if(tn){
				tn.select();
			}
		}
	},
	/**
	 * 构建视图业务功能url
	 * @param {} baseUrl
	 * @return {}
	 */
	builtfunctionurl : function(baseUrl){
		var url = false;
		if(baseUrl.indexOf('.jsp') < 0 ){
			url = basepath + '/contents/frameControllers/view/Wlj-view-function.jsp';
		}else{
			url = basepath + baseUrl.split('.jsp')[0]+'.jsp';
		}
		var turl = baseUrl.indexOf('?')>=0 ? (baseUrl + '&resId='+this.resId ): (baseUrl + '?resId='+this.resId) ;
		url += '?' + turl.split('?')[1] + '&custId='+this.custId+'&viewResId='+this.viewResId;
		return url;
	},
	/**
	 * 切换视图
	 * @param bool true表示显示全部视图，隐藏我的视图
	 */
	switchView : function(bool){
		this.viewTree.setVisible(bool);
		this.myViewTree.setVisible(!bool);
		Ext.getCmp('leftLayout').layout.setActiveItem(bool?this.viewTree:this.myViewTree);
	},
	addMyView : function(node){
		var _this = this;
		Ext.Ajax.request({
			url : basepath + '/myCustView.json',
			params:{
				viewId : node.id,
				userId : JsContext._userId
			},
			method:'POST',
			success:function(response){
				_this.queryMyViewTree();
			}
		});
	},
	removeMyView : function(node){
		var _this = this;
		Ext.Ajax.request({
			url : basepath + '/myCustView!batchDestroy.json',
			params:{
				ids : node.attributes.MY_VIEW_ID
			},
			method:'POST',
			success:function(response){
				_this.queryMyViewTree();
			}
		});
	}
});

/**
 * 重写Ext树节点展现类
 * @class Wlj.view.TreeNodeUI
 * @extends Ext.tree.TreeNodeUI
 */
Wlj.view.TreeNodeUI = Ext.extend(Ext.tree.TreeNodeUI,{
    // private
    renderElements : function(n, a, targetNode, bulkRender){
        // add some indent caching, this helps performance when rendering a large tree
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';

        var cb = Ext.isBoolean(a.checked),
            nel,
            href = this.getHref(a.href),
            buf = ['<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,' cust-viewList" unselectable="on">',
            '<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
            '<img alt="" src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />',
            '<img alt="" src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on" />',
            cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />' : '/>')) : '',
            '<a hidefocus="on" class="x-tree-node-anchor" href="',href,'" tabIndex="1" ',
             a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", '><span unselectable="on">',n.text,"</span></a>"
             ,'<a class="view-remove" href="javascript:void(0);"></a></div>',
            '<ul class="x-tree-node-ct" style="display:none;"></ul>',
            "</li>"].join('');

        if(bulkRender !== true && n.nextSibling && (nel = n.nextSibling.ui.getEl())){
            this.wrap = Ext.DomHelper.insertHtml("beforeBegin", nel, buf);
        }else{
            this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf);
        }

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
        var cs = this.elNode.childNodes;
        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
        var index = 3;
        if(cb){
            this.checkbox = cs[3];
            // fix for IE6
            this.checkbox.defaultChecked = this.checkbox.checked;
            index++;
        }
        this.anchor = cs[index];
        this.textNode = cs[index].firstChild;
    }
});


Ext.onReady(function(){
	window.CUSTVIEW = new Wlj.view.CustView();
	var viewport = new Ext.Viewport({
        layout : 'fit',
        items: [window.CUSTVIEW]
    });
    
});