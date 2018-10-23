Ext.onReady(function() {
	//区域码值
	/***
	 * 数据来源
	 * SELECT AREA_ID, AREA_NAME
	 *	FROM CRM.OCRM_F_MM_AREA_INFO
	 *	WHERE FLAG = '1'
	 */
	var areaStore = new Ext.data.ArrayStore({
        fields:['areaId','areaName'],
        data:[['999000111','北京'],['1','杭州'],['2','绍兴'],['3','嘉兴'],['4','温州'],['5','台州'],['','其它'],['root','其它']]});
	
	//客户经理列表
	var searchCustmgr = new Ext.ux.form.CustMgrField({ 
		fieldLabel : '客户经理名称', 
		labelStyle: 'text-align:right;',
		name : 'MGR_NAME',
		id:'MGR_NAME',
		 editable : false,
		 allowBlank:false,//不允许为空
         blankText:"不能为空，请填写",
		singleSelected:true
	});
	//区域ID与区域名对应store
//	var areaStore = new Ext.data.JsonStore({
//		restful : true,
//		autoLoad : true,
//		proxy : new Ext.data.HttpProxy({
//			url : basepath + '/OcrmFMmAreaInfoAction-info.json'
//		}),
//		fields : [ 'areaId', 'areaName' ],
//		reader : new Ext.data.JsonReader({
//			totalProperty : 'list'
//		}, [ {
//			name : 'areaId',
//			mapping : 'areaId'
//		}, {
//			name : 'areaName',
//			mapping : 'areaName'
//		} ])
//	});

//	var mgrStore = new Ext.data.JsonStore({
//		restful:true,
//		autoLoad:true,
//		proxy : new Ext.data.HttpProxy({
//			url : basepath+'/OcrmFMmManagerAreaInfo-info!queryUser.json'
//		}),
//		fields : [ 'USERID', 'USERNAME' ],
//		 reader: new Ext.data.JsonReader({
//			 totalProperty:'list2'
//			 }, [{name: 'USERID'},{name: 'USERNAME'}])
//	});
		
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户经理管理区域信息",
		stUrl:basepath + '/OcrmFMmManagerAreaInfo-info!indexPage.json',
		deUrl : basepath + '/OcrmFMmManagerAreaInfo-info!batchDestroy.json',
		primary : "id",
		//checkbox : true,
		//定义是否进入页面就查询数据
//		defaultLoad : false,
		//定义查询条件Form的高度
		seFormHeight : 100,
		//定义增删详情页面弹出窗口高度
		winHeight : 450,
		//宽度
		winWidth : 800,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		//重载afterSeOneFun方法，加载一条数据后做的特殊处理
		afterSeOneFun : function(b) {
//			Ext.getCmp('createDate').setValue(new Date(b.createDate.time));
//	    	Ext.getCmp('updateDate').setValue(new Date(b.updateDate.time));
		},
		// 查询字段定义，若不定义则不出现查询条件From
		selectItems :{
			layout:'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				labelWidth : 90,
				allowBlank:false,
				items : [[new Com.yucheng.crm.common.OrgUserManage({ 
					xtype:'userchoose',
					fieldLabel : '客户经理名称', 
					id:'Mgr',
					labelStyle: 'text-align:right;',
					name : 'MGR_NAME',
					hiddenName:'custId',
					//searchRoleType:('127,47'),  //指定查询角色属性
					searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
					singleSelect:true,
					anchor : '90%'
					})]]},{columnWidth : .25,
		                layout : 'form',
		                labelWidth : 90,
		                allowBlank:false,
		                items : [
				         [util.form._td({id:'custId',name : 'custId',xtype : 'textfield',fieldLabel : '客户经理ID'})]
											]
		}]},
		buts : [{
				id : 'renling',
				xtype : 'button',
				tooltip : '客户经理管理区域设置',
				text : '客户经理管理区域设置',
				iconCls:'optionIconCss ',
				listeners : {
					click : function(n) {
						if (Ext.getCmp('Mgr').getValue()!==null&&Ext.getCmp('Mgr').getValue()!=='') {
							areaWindow.show();
						} else {
							Ext.Msg.alert("提示", "请输入客户经理名称");
						}
					}
			}
			}
		
		        ],
		//查询列表字段定义，有header属性则在页面显示
		//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
		gclms : [ 
		    {name : 'id'},  
			{name : 'custId',header : '客户经理'	},
		    {name : 'areaId',header : '区域',type :'mapping',store : areaStore, mappingkey : 'areaId',mappingvalue : 'areaName'},  
			{name : 'createUser',header : '创建人'},
			{name : 'createDate',header : '创建时间',type : 'date'}
//		    {name : 'channelTypeId',header : '渠道类型',type :'mapping',store : channelTypeStore, mappingkey : 'channelTypeId',mappingvalue : 'channelTypeName'}, 
		]
		// 新增、修改、详情的form的字段
//		formColums :function(){
//			return new Ext.form.FieldSet({items:[
//				util.layout._tr([util.form._td({name : 'channelName',xtype : 'textfield',fieldLabel : '渠道名称'})],
//								[util.form._td({name : 'channelTypeId',xtype : 'combo',fieldLabel : '渠道类型',store : channelTypeStore,valueField : 'channelTypeId',displayField : 'channelTypeName'})]
//								),
//				util.layout._tr([util.form._td({name : 'accessCondition',fieldLabel : '准入条件',xtype : 'textarea',maxLength : 400})]
//								),
//				util.layout._tr([util.form._td({name : 'channelFeature',fieldLabel : '渠道特点',xtype : 'textarea',maxLength : 400})]
//				),
//				util.layout._tr([util.form._td({name : 'channelPolicy',fieldLabel : '渠道政策',xtype : 'textarea',maxLength : 400})]
//				),
//				util.layout._tr([util.form._td({name : 'guarantee',fieldLabel : '担保',xtype : 'textarea',maxLength : 400})]
//				),
//				util.layout._tr([util.form._td({name : 'remark',fieldLabel : '备注',xtype : 'textarea',maxLength : 400})]
//				),
//				util.layout._tr([util.form._td({name : 'createUser',fieldLabel : '渠道建立人',xtype : 'textfield',readOnly : true})],
//								[util.form._td({name : 'createOrganization',fieldLabel : '渠道建立机构',	xtype : 'textfield',readOnly : true})]
//				),
//				util.layout._tr([util.form._td({id : 'createDate',name : 'createDate',fieldLabel : '渠道建立日期',xtype : 'datefield',readOnly : true})],
//								[util.form._td({id : 'updateDate',name : 'updateDate',fieldLabel : '最近更新日期',xtype : 'datefield',readOnly : true})]
//				),
//				util.layout._tr([util.form._td({name : 'channelId',xtype : 'hidden'})]
//				)
//		]})}

	});
//	listPanel.grid.on('rowdblclick',function(a,b,c,d){
//		listPanel.win.hide();
//	});
	var nodeAreaId;
	var nodeAreaName;
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'upAreaId',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'areaId',
		/**虚拟根节点id 若果select的值为root则为根节点*/
		rootValue : 'root',
		/**用于展示节点名称的属性列*/
		textField : 'areaName',
		/**指定节点ID的属性列*/
		idProperties : 'areaId'
		/**节点点击事件句柄*/
	});
//	Ext.Ajax.request({
//		url : basepath + '/OcrmFMmAreaInfoAction-info.json',
//		method:'GET',
//		success:function(response){
//			var nodeArra = Ext.util.JSON.decode(response.responseText);
//			loader.nodeArray = nodeArra;
//			var children = loader.loadAll();
//			orgTreeForShow.appendChild(children);
//		}
//	});
	
    var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title:'请选择区域',
		width:200,
		autoScroll:true,
		tbar:[
		      {text:'新增',
		    	 handler:
		    	  function(){
		    	  Ext.Ajax.request({
						url : basepath + '/OcrmFMmManagerAreaInfo-info!mgrAreaSave.json',
						method : 'POST',
						params:{
		    		  'areaId':nodeAreaId,
		    		  'areaName':nodeAreaName,
//		    		  'custId':mgrId
		    		  'custId':Ext.getCmp('custId').getValue()
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
//							store.reload();
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
						       if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else{

							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						  }
						}
					});		    	  
		    	  }
		    	  }],
		  
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
    	nodeAreaId=node.id;
    	nodeAreaName=node.text;
//    	alert(nodeAreaId+"/"+nodeAreaName);
//		 Ext.getCmp('areaId').setValue(node.id);
//		 Ext.getCmp('areaName').setValue(node.text);
//		 custGroupStore.reload({
//			 params : {
//	    	   'messageType':node.id,
//				start : 0,
//				limit : 100
//			}
//		 });
    	}
	});
	var areaWindow = new Ext.Window({
		title : '分配管理区域',
		plain : true,
		layout : 'fit',
		width : 680,
		height :380,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		buttonAlign : 'right',
		border : false,
		items : [orgTreeForShow]
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
		}),
		reader : new Ext.data.JsonReader({
			successProperty: 'success',
			root:'json.data',
			totalProperty:'json.count'
		},recordStore)
	});
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
		});