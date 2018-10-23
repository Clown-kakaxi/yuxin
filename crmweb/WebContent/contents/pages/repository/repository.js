Ext.onReady(function(){
	
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'parentSection',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'sectionId',
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : 'root',
		/**用于展示节点名称的属性列*/
		textField : 'sectionName',
		/**指定节点ID的属性列*/
		idProperties : 'sectionId'
		/**节点点击事件句柄*/
	});
	Ext.Ajax.request({
		url : basepath + '/workplatforminfosection!indexAll.json',
		method:'GET',
		success:function(response){
//		alert(response.responseText);
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra;
			var children = loader.loadAll();
			leftTreeForShow.appendChild(children);
		}
	});
	 //左边新增栏目面板下拉框
		var comboxWithTree = new Ext.form.ComboBox({
			id : 'sectionCategory', 
			xtype:'combo',
			store : new Ext.data.SimpleStore({
						fields : [],
						data : [[]]
					}),
			labelStyle: 'text-align:right;',
			editable : false,
			emptyText : '请选择...',
			fieldLabel : '上级栏目',
			anchor : '95%',
			mode : 'local',
			resizable :false,
			forceSelection:true,
			name:'parentSection',
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
		//左边新增栏目面板
		var tbar = new Ext.Toolbar({
			items : [ {
				text : '新增栏目',
				iconCls:'addIconCss',
				handler:function(){
		           addtitle.getForm().reset();
					var newwindow_1 = new Ext.Window({
							layout : 'fit',
							width : 450,
							height : 180,
							closable : true,
							draggable : true,
							title : '编辑栏目',
							closeAction : 'hide',
							titleCollapse : true,
							modal : true, // 模态窗口 
							animCollapse : false,
							border : false,
							animateTarget : Ext.getBody(),
							constrain : true,
							buttonAlign:'center',
							items : [addtitle],
							buttons:[{
									text:'新增',
									handler:function(){
									if(!addtitle.getForm().isValid()){ 
				                       Ext.MessageBox.alert('提示','输入有误,请检查输入项');
				                       return false;
				                    }
									Ext.Ajax.request({
										url : basepath + '/workplatforminfosection.json',
										method : 'POST',
										params : {
										'parentSection' : Ext.getCmp("parentSection").getValue(),
										'sectionSummary' : Ext.getCmp("sectionSummary").getValue(),
										'sectionName' : Ext.getCmp("sectionName").getValue(),
										'sectionCategory' :Ext.getCmp('sectionCategory').getValue()
										},
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										scope : this,
										success : function() {
											 Ext.Ajax.request({
									         url: basepath +'/workplatforminfosection!getPid.json',
										         success:function(response){
										        	 var node = {};
										        	 node.sectionId = Ext.util.JSON.decode(response.responseText).pid;
										        	 node.sectionName = Ext.getCmp("sectionName").getValue();
										        	 node.parentSection = Ext.getCmp("parentSection").getValue();
										        	 leftTreeForShow.addNode(node);
										        	 if(leftTreeForShow1.rendered)
										        		 leftTreeForShow1.addNode(node);
										        	 if(leftTreeForShow2.rendere)
										        		 leftTreeForShow2.addNode(node);
											 	}
											 });
//											 Ext.Msg.alert('提示', '操作成功');
											 Ext.Msg.alert('提示', '操作成功',function(btn){
												 location.href = basepath + '/contents/pages/repository/information.jsp';
											 },this);
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
						}
			},'-', {
				text : '编辑',
				iconCls:'editIconCss',
				handler : function() {
					var record = leftTreeForShow.getSelectionModel().getSelectedNode();
					if (Ext.isEmpty(record)) {
						Ext.MessageBox.alert('提示', '请先选择要修改的栏目!');
					}else{
						Ext.Ajax.request({
							url : basepath + '/workplatforminfosection!indexPage.json?sectionId='+record.id+'',
							method : 'GET',
							success:function(a,b,c){
								var nodeArra = Ext.util.JSON.decode(a.responseText);
								store.data = nodeArra;
								Ext.getCmp("sectionSummary").setValue(store.data.json.data[0].sectionSummary);
								Ext.getCmp("sectionName").setValue(store.data.json.data[0].sectionName);
								Ext.getCmp("parentSection").setValue(store.data.json.data[0].parentSection);
								Ext.getCmp("sectionCategory").setValue(store.data.json.data[0].sectionCategory);
							}
						});
						var store = Ext.data.Store;
						var newwindow_1 = new Ext.Window({
							layout : 'fit',
							width : 450,
							height : 180,
							closable : true,
							draggable : true,
							title : '编辑栏目',
							closeAction : 'hide',
							titleCollapse : true,
							modal : true, // 模态窗口 
							animCollapse : false,
							border : false,
							animateTarget : Ext.getBody(),
							constrain : true,
							buttonAlign:'center',
							items : [addtitle],
							buttons:[{
								text:'修改',
								handler:function(){
								debugger;
								if(Ext.getCmp("parentSection").getValue()==Ext.getCmp('sectionId').getValue()){
									Ext.Msg.alert('提示', '当前目录不能做为上层目录!否则会出现未知错误');
									return false;
								    }else{
										if(!addtitle.getForm().isValid()){ 
										 Ext.MessageBox.alert('提示','输入有误,请检查输入项');
										 return false;
										}
										debugger;
										Ext.Ajax.request({
										url : basepath + '/workplatforminfosection!update_new.json',
										method : 'POST',
										params : {
										'sectionSummary' : Ext.getCmp("sectionSummary").getValue(),
										'sectionName' : Ext.getCmp("sectionName").getValue(),
										'parentSection':Ext.getCmp("parentSection").getValue(),
										'sectionId' :Ext.getCmp('sectionId').getValue(),
										'sectionCategory' :Ext.getCmp('sectionCategory').getValue()
										},
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										//修改文档的目录
										scope : this,
										success : function() {
											Ext.Ajax.request({
											url : basepath + '/workingplatformInfo!update_productType.json',
												method : 'POST',
												params : {
												'messageType':Ext.getCmp("sectionId").getValue(),
												'productType':Ext.getCmp("sectionName").getValue()
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												scope : this,
												success : function() {
//													Ext.Msg.alert('提示', '操作成功');
//													custGroupStore.reload();
													 Ext.Msg.alert('提示', '操作成功',function(btn){
														 location.href = basepath + '/contents/pages/repository/information.jsp';
													 },this);
												},
												failure : function() {
													Ext.Msg.alert('提示', '操作失败');
													custGroupStore.reload();
												}
												}
											  );
												 Ext.Msg.alert('提示', '操作成功');
												 var node = {};
												 node.id = Ext.getCmp("sectionId").getValue();
									        	 node.text = Ext.getCmp("sectionName").getValue();
									        	 node.parentSection = Ext.getCmp("parentSection").getValue();
									        	 leftTreeForShow.editNode(node);
									        	 leftTreeForShow1.editNode(node);
									        	 leftTreeForShow2.editNode(node);
										    },  
											    failure : function() {
												Ext.Msg.alert('提示', '操作失败');
										        }
										
								        }
									  );
									
								}

								  newwindow_1.hide();
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
				iconCls:'deleteIconCss',
				handler:function(){
				var record = leftTreeForShow.getSelectionModel().getSelectedNode();
				if (Ext.isEmpty(record)) {
					Ext.MessageBox.alert('提示', '请先选择要修改的栏目!');
				}else{
						Ext.Msg.confirm(
								'请确认',
								'<b>提示!:</b><span  style="color:red" >删除栏目将同时删除栏目下的主题,请慎重! </span> <br/>继续删除吗?',
								function(btn, text) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											url : basepath + '/workplatforminfosection!batchDestroy.json',
											method : 'POST',
											params : {
											'sectionId' : Ext.getCmp("sectionId").getValue()
											},
											waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											scope : this,
											success : function() {
									        	 leftTreeForShow.deleteNode(record);
//									        	 leftTreeForShow1.deleteNode(record);
//									        	 leftTreeForShow2.deleteNode(record);
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
		labelAlign: 'right',
		frame:true,
		columnWidth : .25,
		layout : 'form',
		labelWidth : 60,
		defaultType : 'textfield',
		border : false,
        items :[{
        	 allowBlank: false,
        	 id:'sectionName',
        	 xtype:'textfield',
             fieldLabel: '栏目名称',
             name: 'sectionName',
             anchor:'95%'
         }, 
         comboxWithTree
         ,{
             id:'sectionSummary',
             xtype:'textarea',
             fieldLabel: '备注',
             name: 'sectionSummary',
             anchor:'95%',
             hidden:true
        },{
        	id:'parentSection',
        	name:'parentSection',
        	fieldLabel: '父节点的Id',
        	hidden:true
        },{
        	id:'sectionId',
        	name:'sectionId',
        	 xtype:'textarea',
        	fieldLabel: '当前用户ID',
        	hidden:true
        }]
 });
  //左边的tree的形状
    var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title:'资讯文档树',
		id:'blocMemberTree',
		width:200,
		autoScroll:true,
		tbar:tbar,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'全部目录',
			autoScroll:true,
			children:[]
		}),
		resloader:loader,
		region:'west',
		split:true,
		 clickFn:function(node){
		 Ext.getCmp('sectionId').setValue(node.id);
		 Ext.getCmp('sectionName').setValue(node.text);
		 custGroupStore.reload({
			 params : {
	    	   'messageType':node.id,
				start : 0,
				limit : 100
			}
		 });
    	}
	});
    var leftTreeForShow2 = new Com.yucheng.bcrm.TreePanel({
		width:200,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			text:'全部目录',
			autoScroll:true,
	        expanded:true,
	        leaf:false,
			children:[]
		}),
		 resloader: loader,
		 split:true,
		 clickFn:function(node){
            Ext.getCmp('sectionCategory').setValue(node.text);
            Ext.getCmp('parentSection').setValue(node.id);
		},
	    animate : false,
	    useArrows : false,
	    border : false
	});
    var leftTreeForShow1 = new Com.yucheng.bcrm.TreePanel({
		width:400,
		autoScroll:true,
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'全部目录',
			autoScroll:true,
			children:[]
			         
		}),
		resloader:loader,
		split:true,
		listeners:{
    		'click':function(node){
	    	comboxWithTree1.setValue(node.text);
			//当前的父节点给子节点
			Ext.getCmp('messageType').setValue(node.id);
			debugger;
			//comboxWithTree1.collapse().createCallback();
			debugger;
    	}
	},
    animate : false,
    useArrows : false,
    border : false
	});
    //****弹出框的下拉框treede定义
    //右边新增编辑下拉框
    var comboxWithTree1 = new Ext.form.ComboBox({
    	id : 'productType',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		emptyText : '请选择...',
		fieldLabel : '上级栏目',
		anchor : '95%',
		mode : 'local',
		name:'productType',
		triggerAction : 'all',
		editable:false,
		resizable:true,
		labelStyle: 'text-align:right;',
		maxHeight : 390,
		// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv_2'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
			leftTreeForShow1.render('addDeptTreeDiv_2');
			},
			'collapse':function(combo){
			}
		}
	   });
	var addtheme=new Ext.form.FormPanel({
            allowBlank: false,
            msgTarget: 'side',
        	id:'addtheme',
			labelAlign: 'right',
			frame:true,
			columnWidth : .25,
			layout : 'form',
			labelWidth : 60,
			defaultType : 'textfield',
			border : false,
		 	frame:true,
	        items :[{
	        	 id:'messageId',
	        	// allowBlank: false,
	        	 xtype:'textfield',
	             fieldLabel: 'messageId',
	             name: 'messageId',
	             anchor:'95%',
	             hidden:true
	        	 }, {
					id : 'messageType',
					//allowBlank: false,
					xtype : 'textfield',
					fieldLabel : '父节点ID',
					name : 'messageType',
					anchor : '95%',
					hidden:true
	        	 }, {
		        	 id:'messageTitle',
		        	 xtype:'textfield',
		             fieldLabel: '文档名称',
		             allowBlank: false,
		             name: 'messageTitle',
		             anchor:'95%'
	             },comboxWithTree1,{
	            	id:'messageIntroduce',
            	    allowBlank: false,
	             	xtype:'textarea',
	             	fieldLabel:'描述',
	             	name:'messageIntroduce',
	             	anchor:'95%',
	             	height:'50'
	             }
	             ]
	});
	
	/**-添加查看详情按钮-start*/
	/**查看详细-start*/
	var detailForm = new Ext.form.FormPanel({
		formId : 'detailForm',
		frame : true,
		border : false,
		labelAlign : 'right',
		standardSubmit : false,
		layout : 'form',
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '文档名称',
					labelStyle : {
						width : '120px'
					},
					width : '100',
					name : 'MESSAGE_TITLE_D',
					anchor : '90%',
					labelStyle : 'text-align:right;',
					readOnly : true
				}, {
					xtype : 'combo',
					fieldLabel : '文档描述',
					name : 'MESSAGE_INTRODUCE_D',
					anchor : '90%',
					labelStyle : 'text-align:right;',
					readOnly : true
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'combo',
					fieldLabel : '所属栏目',
					name : 'PRODUCT_TYPE_D',
					anchor : '90%',
					labelStyle : 'text-align:right;',
					readOnly : true
				},{
					xtype : 'textfield',
					fieldLabel : '发布人',
					name : 'USERNAME_D',
					anchor : '90%',
					labelStyle : 'text-align:right;',
					readOnly : true
				}]
			}]
		},{
			hidden : true,
			name : 'messageId',
			xtype : 'textfield',
			anchor : '90%'
		} ]
	});
	/**-添加查看详情按钮-end*/
	
	//center面板工具栏
    var centertbar = new Ext.Toolbar({
		items : [ {
			iconCls : 'detailIconCss',
			text:'查看详情',
			handler:function(){
				var record = cusgrid.getSelectionModel().getSelected();
				if (!record) {
					Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
					return false;
				}
				var checkedNodes = cusgrid.getSelectionModel().selections.items;
				if (checkedNodes.length > 1) {
					Ext.MessageBox.alert('查询操作','您选择的记录过多！');
					return false;
				}
				Ext.Ajax.request({
					url : basepath + '/workingplatformInfo!findWithType.json?msgId='+record.data.messageId,
					method : 'GET',
					scope : this,
					success : function(a,b,c) {
					var win = new Ext.Window( {
						width : 900,
						height : 200,
						closable : true,
						resizable : false,
						draggable : true,
						closeAction : 'hide',
						title : '详情',
						collapsible : false,
						modal : true,
						animCollapse : false,
//						maximizable : true,
						border : false,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [ detailForm,
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
										win.setHeight(200);
										win.setWidth(900);
										win.setPosition(220,50);
										win.doLayout();
									},
								    'expand':function(){
										win.setHeight(380);
										win.setWidth(900);
										win.setPosition(220,50);
										win.doLayout();
									}
							   }
					          } ],
						buttonAlign : 'center',
						buttons : [ {
							text : '返回',
							handler : function() {
								win.hide();
							}
						} ],
				        listeners : {
				    		'beforeshow' : function(){
				    			Ext.getCmp('_downId').setDisabled(false);
				    			Ext.getCmp('_upload').setDisabled(true);
				    			Ext.getCmp('_delload').setDisabled(true);
				    		}
				    	}
					});
						var pubData = Ext.util.JSON.decode(a.responseText);
						var data = pubData.json.data[0];
						detailForm.getForm().findField('MESSAGE_TITLE_D').setValue(data.messageTitle);
						detailForm.getForm().findField('MESSAGE_INTRODUCE_D').setValue(data.messageIntroduce);
						detailForm.getForm().findField('PRODUCT_TYPE_D').setValue(data.productType);
						detailForm.getForm().findField('USERNAME_D').setValue(data.publishUser);
//						var pubDate = new Date(pubData.data.publishDate.time).format('Y-m-d');
//						detailForm.getForm().findField('publishDate').setValue(pubDate);
						/**-添加附件信息-start*/
						/* 附件编辑列表  */	
						var noticeIdStr = record.get('messageId');
						uploadForm.relaId = noticeIdStr;
						uploadForm.modinfo = 'infomation';
						var condi = {};
						condi['relationInfo'] = noticeIdStr;
						condi['relationMod'] = 'infomation';
						Ext.Ajax
								.request( {
									url : basepath + '/queryanna.json',
									method : 'GET',
									params : {
										"condition" : Ext
												.encode(condi)
									},
									failure : function(a, b, c) {
										Ext.MessageBox.alert(
												'查询异常', '查询失败！');
									},
									success : function(response) {
										var anaExeArray = Ext.util.JSON
												.decode(response.responseText);
										appendixStore
												.loadData(anaExeArray.json.data);
										appendixGridPanel2.getView()
												.refresh();
									}
								});
						/**-添加附件信息-end*/
						win.show();
					},
					failure : function() {
					}
				});
			}
		},{
			text : '知识发布',
			iconCls:'publishIconCss',
			handler:function(){
			addtheme.getForm().reset();
			debugger;
			var newWindow_2 = new Ext.Window({
//				layout : 'fit',
				width : 450,
				height : 230,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				title : '新增主题',
				collapsible : true,
				closeAction : 'hide',
				//titleCollapse : false,
				modal : true, // 模态窗口 
				//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
				animCollapse : false,
				//maximizable : true,
				border : false,
				//closable : false,
				animateTarget : Ext.getBody(),
				constrain : true,
				items :[addtheme,
				        { 
					id:'filefields',
					xtype:'form',
					height : 50,
				    width : '100%',
				    fileUpload : true, 
				    dataName:'file',
				    frame:true,
				    relaId:'',/**关联数据ID*/
				    modinfo:'infomation',/**modinfo: notice:公告;customer:客户;infomation:资讯;*/
				    items: [
				        new Ext.form.TextField({
				        	xtype :'textfield',
				        	name:'annexeName',
				        	inputType:'file',
				        	fieldLabel : '附件名称',
				        	anchor :'90%'
					})]}],
				buttonAlign:'center',
				buttons:[{
					id :'annNot_2',
					text:'确认',
					handler:function(){
					if(!addtheme.getForm().isValid()){ 
                       Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                        return false;
                    	}
					Ext.Ajax.request({
						url : basepath + '/workingplatformInfo.json',
						method : 'POST',
						params : {
						'messageTitle' : Ext.getCmp("messageTitle").getValue(),
						'messageIntroduce' : Ext.getCmp("messageIntroduce").getValue(),
						'messageType':Ext.getCmp("messageType").getValue(),
						'productType':Ext.getCmp("productType").getValue()
						//'messageId':Ext.getCmp('messageId').getValue()
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						scope : this,
						success : function() {
							Ext.Ajax.request({
								url : basepath+'/session-info!getPid.json',
								method : 'GET',
								success : function(a,b,v) {debugger;
								    var noticeIdStr = Ext.decode(a.responseText).pid;
								    var filefields = Ext.getCmp('filefields');
								    filefields.relaId = noticeIdStr;
								    filefields.modinfo = 'infomation';
								    Com.yucheng.bcrm.common.uploadFiles(filefields);//调用annacommit.js中方法
								}
							});
							Ext.Msg.alert('提示', '操作成功');
							newWindow_2.hide();
							custGroupStore.reload();
						},
						failure : function() {
							newWindow_2.hide();
							Ext.Msg.alert('提示', '操作失败');
						}
						}
					  );
					}
					},{
			  			text: '关闭',
			  			handler:function(){
						newWindow_2.hide();
					}
	 				}]
		});
          newWindow_2.show();
		}
		},'-', {
				text : '编辑',
				iconCls:'editIconCss',
				//iconCls : 'page_refreshIcon',
					handler : function() {
					var record = cusgrid.getSelectionModel().getSelected(id);
					var checkedNodes_1 = cusgrid.getSelectionModel().selections.items;
					if (Ext.isEmpty(record)) {
						Ext.MessageBox.alert('提示', '请先选择要编辑的主题!');
					} else if(checkedNodes_1.length>1){
						Ext.MessageBox.alert('提示', '你选择的条数太多!');
					}else{
						/* 附件编辑列表  */	
						var noticeIdStr = record.get('messageId');
						uploadForm.relaId = noticeIdStr;
						uploadForm.modinfo = 'infomation';
						var condi = {};
						condi['relationInfo'] = noticeIdStr;
						condi['relationMod'] = 'infomation';
						Ext.Ajax
								.request( {
									url : basepath + '/queryanna.json',
									method : 'GET',
									params : {
										"condition" : Ext
												.encode(condi)
									},
									failure : function(a, b, c) {
										Ext.MessageBox.alert(
												'查询异常', '查询失败！');
									},
									success : function(response) {
										var anaExeArray = Ext.util.JSON
												.decode(response.responseText);
										appendixStore
												.loadData(anaExeArray.json.data);
										appendixGridPanel2.getView()
												.refresh();
									}
						});
						var editWindow = new Ext.Window({
							collapsible : true,
//							layout : 'fit',
							width : 450,
							height : 230,
							resizable : true,
							draggable : true,
							closeAction : 'hide',
							title : '修改知识主题',
							//titleCollapse : false,
							modal : true, // 模态窗口 
							//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
							//animCollapse : false,
							border : false,
							animateTarget : Ext.getBody(),
							constrain : true,
							autoScroll : true,
							items : [addtheme,
							         {
								   xtype : 'fieldset',
								   title: '附件',
								   autoHeight : true,
								   layout : 'form',
								   collapsed: true,
							       collapsible: true,
							       autoScroll : true,
						           items : [appendixGridPanel2],
						           listeners:{
										'collapse':function(){
											editWindow.setHeight(250);
											editWindow.setPosition(260,10);
											editWindow.doLayout();
										},
									    'expand':function(){
											editWindow.setWidth(450);
											editWindow.setHeight(400);
											editWindow.setPosition(260,10);
											editWindow.doLayout();
										}
								   }
						          }],
							buttonAlign:'center',
							buttons:[{
								id :'annNot_2',
								text:'确认',
								handler:function(){
								Ext.Ajax.request({
									url : basepath + '/workingplatformInfo.json',
									method : 'POST',
									params : {
									'messageTitle' : Ext.getCmp("messageTitle").getValue(),
									'messageIntroduce' : Ext.getCmp("messageIntroduce").getValue(),
									'messageType':Ext.getCmp("messageType").getValue(),
									'productType':Ext.getCmp("productType").getValue(),
									'messageId':selectRe.data.messageId
									},
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									scope : this,
									success : function() {
										Ext.Msg.alert('提示', '操作成功');
										editWindow.hide();
										custGroupStore.reload();
									},
									failure : function() {
										Ext.Msg.alert('提示', '操作失败');
										editWindow.hide();
										custGroupStore.reload();
									}
									}
								  );
								}
								},{
						  			text: '关闭',
						  			handler:function(){
									editWindow.hide();
								}
				 				}],
				 				listeners : {
		                    		'beforeshow' : function(){
		                    			Ext.getCmp('_downId').setDisabled(true);
		                    			Ext.getCmp('_upload').setDisabled(false);
		                    			Ext.getCmp('_delload').setDisabled(false);
		                    		}
		                    	}   
							});
							editWindow.show();
							var selectRe = cusgrid.getSelectionModel().getSelections()[0];
							Ext.getCmp("messageType").setValue(selectRe.data.messageType);
							Ext.getCmp("productType").setValue(selectRe.data.productType);
							Ext.getCmp("messageTitle").setValue(selectRe.data.messageTitle);
							Ext.getCmp("messageIntroduce").setValue(selectRe.data.messageIntroduce);
							Ext.getCmp("messageId").setValue(selectRe.data.messageId);
						}
				}
				},'-',{
				text:'删除',
				iconCls:'deleteIconCss',
				handler:function(){
				var record = cusgrid.getSelectionModel().getSelected();
				var selectRe = cusgrid.getSelectionModel().getSelections()[0];
				var checkedNodes = cusgrid.getSelectionModel().selections.items;
				if (Ext.isEmpty(record)) {
					Ext.MessageBox.alert('提示', '请先选择要删除的记录！');
					return false;
				}else if(checkedNodes.length>1){
					Ext.MessageBox.alert('提示', '您只能选择一条记录！');
					return false;
				}else{
					Ext.Msg.confirm(
						'请确认',
						'确认删除吗?',
							function(btn, text) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : basepath + '/workingplatformInfo!batchDestroy.json',
											params : {
										     'messageId':selectRe.data.messageId
											},
										    failure : function() {
											Ext.Msg.alert('提示', '操作失败');
											custGroupStore.reload();
											},
											success : function(){
												custGroupStore.reload();
												Ext.Msg.alert('提示', '删除成功!');
											}
										}
									  );
								}
							});
					}
				}
		},'-',{
            text:'附件信息',
            iconCls:'dailyDetailIconCss',
            handler:function()
            {
                var record = cusgrid.getSelectionModel().getSelected(); 
                if (!record) {
                    Ext.MessageBox.alert('查询操作', '请选择要操作的数据！');
                    return false;
                }
                var checkedNodes = cusgrid.getSelectionModel().selections.items;
                if(checkedNodes.length>1){
                    Ext.MessageBox.alert('查询操作', '您选择的记录过多！');
                    return false;
                }
                var messageIdStr = record.get('messageId');
                uploadForm.relaId = messageIdStr;
                uploadForm.modinfo = 'infomation';
                var condi = {};
                condi['relationInfo'] = messageIdStr;
                condi['relationMod'] = 'infomation';
                Ext.Ajax.request({
                    url:basepath+'/queryanna.json',
                    method : 'GET',
                    params : {
                        "condition":Ext.encode(condi)
                    },
                    failure : function(a,b,c){
                        Ext.MessageBox.alert('查询异常', '查询失败！');
                    },
                    success : function(response){
                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
                        appendixStore.loadData(anaExeArray.json.data);
                        appendixGridPanel.getView().refresh();
                    }
                });
                appendixWindow.show();
            }
        }]
	});
	var recordStore = Ext.data.Record.create([
             {name: 'messageId'},
             {name: 'messageTitle'},
             {name: 'messageSummary'},
             {name:'publishUser'},
             {name:'messageIntroduce'},
             {name:'publishDate'},
             {name:'messageType'},
             {name:'productType'}
    		 ]);
	  var custGroupStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/workingplatformInfo!findWithType.json'
//			success : function(success) {
//				Ext.Msg.alert('提示', success.responseText);
//				alert('dddddddddddddddd');
//			}
		}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
			root:'json.data',
			totalProperty:'json.count'
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
    var sm = new Ext.grid.CheckboxSelectionModel();
    var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
    var cusgrid=new Ext.grid.GridPanel({
    	title:'文档信息',
     	layout:'fit',
     	tbar:centertbar,
     	sm:sm,
     	bbar:sbbar,
        store: custGroupStore,
        columns: [rownum,sm,
            {   
        	    header:'文档ID',
            	id:'messageId',
            	dataIndex: 'messageId',
            	name:'messageId',
            	hidden:true
            },
            {
            	id:'productType',
                header :'所属栏目', 
                width : 220,
                dataIndex: 'productType',
                name:'productType',
                align:'center'	,
                sortable:true	
            },
            {
            	id:'messageTitle',
                header   : '文档名称',
                width : 220,
                dataIndex: 'messageTitle',
            	name:'messageTitle',
            	align:'center',
            	sortable:true
            },
            {
            	id:'messageIntroduce',
                header   : '文档描述',
                width : 220,
                dataIndex: 'messageIntroduce',
            	name:'messageIntroduce',
            	align:'center',
                sortable:true
            },
            {
            	id:'publishUser',
                header   : '发布人',
                width : 200,
                dataIndex: 'publishUser',
                name:'publishUser',
                align:'center',
                sortable:true
            },
            {
            	id:'publishDate',
                header   : '发布日期',
                width : 205,
                dataIndex: 'publishDate',
                name:'publishDate',
                //format :'Y-m-d',
                align:'center',
                sortable:true,
                renderer : function(value){
					if (value != undefined && value.time != undefined) {
					return new Date(value.time).format('Y-m-d');
					} else {
					    return "";
				      }
			   }
            }
        ],
        stripeRows: true,
        width:'100%',
        height:350
     });
    
    
     var tabs = new Ext.TabPanel({
        	resizeTabs:true, 
        	enableTabScroll:true,
        	 minTabWidth: 115,
        	 activeItem:0,
        	tabWidth:135,
        	defaults: {autoScroll:true},
       		plugins: new Ext.ux.TabCloseMenu()
    });
//    addTab("资讯文档");
//    function addTab(title){
//        tabs.add({
//            title: title,
//            items:[cusgrid],
//            closable:true
//        }).show();
//    }
    
	var formgrid=new Ext.FormPanel({
    	layout:'fit',
    	labelAlign: 'right',
    	height:300,
			frame:true,
			html:'<p style="line-height:30px;font-size:14px;">※信息描述:</p><p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;总行和分行的系统管理员都可以在构建好的知识库栏目下进行知识发布、修改、删除、查询，</p><p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;不管是总行还是分行系统管理员发布知识，他们都可以选择是将此知识列为全行共享还是所辖机构共享，</p><p style="line-height:20px;font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;如果是全行共享则全行所有用户都可以看到，如果是所辖机构共享则只能本分行及其所辖机构看到。</p>'
    });
	new Ext.Viewport({
		layout:'fit',
		items:[{
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
	           			items:[cusgrid]
	           		},{
	           			id:'south-panel',
	            		region:'south',
	            		title:'->主题描述信息',
	            		collapsible: true,
	            		split:true,
	            		height: 160,
	            		minSize: 100,
	           			maxSize: 300,
	            		items:[formgrid]
	           		}]
	           	}]
	        }]
		}]
	});
	
});