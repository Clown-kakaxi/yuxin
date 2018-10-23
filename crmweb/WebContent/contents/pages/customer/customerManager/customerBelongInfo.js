Ext.onReady(function() {
	Ext.QuickTips.init();
	var cust_id =oCustInfo.cust_id;
	var mainTypeOrg=[];//主办机构更改数组
	var mainTypeCount=0;//校验主办机构个数标志位
	var firstOrg=[];//协办机构增加数组
	var secondOrg=[];//协办机构删除数组
	var cust_mgr_mainType_flag='';
	var mainTypeNode='';//遍历机构树后得到当前设置主办机构节点
	var currentOrgId='';//点击机构树节点后得到的当前机构号
	var custMgrGrid_store=[];
	var addStr=[];//调整客户经理新增数组
	var delStr=[];//调整客户经理删除数组
	var currentNode='';
	/**
	 * 归属客户经理调整
	 * @author wangwan
	 * @since 2012-1-14
	 */
	var selRecord_1 = "";
	var custMgrPanel = new Ext.form.FormPanel({
		id:'custMgrPanel',
		frame:true,
		region:'north',
		height : 80,
		autoScroll : true,
		labelWidth:120,
		split:true,
		items:[{
			columnWidth:.99,
			layout: 'form',
			items:[{
				id:'id',
				xtype:'textfield',
				fieldLabel: 'ID',
				name: 'id',
				hidden : true,
				anchor:'99%'
			},{
				id:'mgrId',
				xtype:'textfield',
				fieldLabel:'主办客户经理编号',
				name:'mgrId',
				hidden:true,
				anchor:'99%'
			},{
				id:'mgrName',
				xtype:'textfield',
				fieldLabel:'主办客户经理',
				name:'mgrName',
				anchor:'80%',
				listeners:{//清空 按钮
					render: function(obj) {
						var button=document.createElement("button");
						var redStar=document.createTextNode('清空');
						 button.setAttribute("className","x-btn");//IE设置class的方法    
						 button.setAttribute("backgroundColor","blue");//IE设置class的方法    
						 button.appendChild(redStar);
						 obj.el.dom.parentNode.appendChild(button);
						 button.onclick = (function(a){
								custMgrPanel.getForm().reset();
								if(selRecord_1!=""){
									custMgrInfoGrid.store.add(selRecord_1);
									custMgrInfoGrid.store.sort('mgrId', 'ASC');
									selRecord_1 = "";
								}
						 });
						}
				}
			},{
				id:'institution',
				xtype:'textfield',
				fieldLabel:'主办客户经理所属机构',
				name:'institution',
				hidden:true,
				anchor:'99%'
			},{
				id:'institution_name',
				xtype:'textfield',
				fieldLabel:'主办客户经理所属机构号',
				name:'institution_name',
				hidden:true,
				anchor:'80%'
			}]
		}]
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	var rownum_1 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var cm_1 = new Ext.grid.ColumnModel([ sm,rownum_1,   //协办客户经理分配列表的模型
	                                     {header : 'ID',dataIndex : 'id',sortable : true,width : 175,hidden:true},
	                                     {header : '归属客户经理编号',dataIndex : 'mgrId',sortable : true,width : 175,hidden:true},
	                                     {header : '客户经理',dataIndex : 'mgrName',sortable : true,width : 120},
	                                     {header : '所在机构',dataIndex : 'institution_name',sortable : true,width : 175}
	                                     ]);
	var record2 = Ext.data.Record.create([
	                                      {name: 'id', mapping: 'ID'},
	                                      {name: 'mgrId', mapping: 'MGR_ID'},                                    
	                                      {name: 'mgrName', mapping: 'MGR_NAME'},
	                                      {name: 'institution', mapping: 'INSTITUTION'},                                   
	                                      {name: 'institution_name', mapping: 'INSTITUTION_NAME'},  
	                                      {name:'mainType',mapping:'MAIN_TYPE'},
	                                      {name:'assignUsername',mapping:'ASSIGN_USERNAME'},
	                                      {name : 'assignDate',mapping : 'ASSIGN_DATE'}
	                                      ]);
	var main_type2='2';                         	
	var custmgrStore = new Ext.data.Store( {//协办客户经理列表store
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_custmgr_info.json?cust_id='+cust_id+'&main_type='+main_type2+'&org_id='+JsContext._orgId
		}),
		reader : new Ext.data.JsonReader( {
			successProperty: 'success',
			messageProperty: 'message',
			root : 'json.data',
			totalProperty: 'json.count'
		},record2)
	});
	custmgrStore.load();
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
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
	var number = parseInt(pagesize_combo.getValue());
	var custmgrStore2 = new Ext.data.Store( {//协办客户经理列表store
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_custmgr_info.json?cust_id='+cust_id+'&org_id='+JsContext._orgId
				}),
				reader : new Ext.data.JsonReader( {
					successProperty: 'success',
//		       	 	                 	        idProperty: 'ID',
					messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				},record2)
	});
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : custmgrStore2,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo]
	});
	custmgrStore2.load({
		params:{
		start:0,
		limit: parseInt(pagesize_combo.getValue())
	}
	});
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		custmgrStore2.load({
			params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
		});
	});
	var main_type_='1';                         	
	var custMainTypeMgrStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_custmgr_info.json?cust_id='+cust_id+'&main_type='+main_type_
			}),
			reader : new Ext.data.JsonReader( {
				successProperty: 'success',
//		        	                 	        idProperty: 'ID',
				messageProperty: 'message',
				root : 'json.data',
				totalProperty: 'json.count'
			},record2)
	});
	custMainTypeMgrStore.load();
	var removeTbar = new Ext.Toolbar({
		items:[{
				text: '移除',
				handler : function(value){
			var record = custMgrGrid.getSelectionModel().selections.items;
			if(!record){
				Ext.Msg.alert('提示','请选择要移除的数据');
			}else{
				Ext.MessageBox.confirm('提示','确定移除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return;
					}}); 
				for(var i=0;i<record.length;i++){
					var del={};
					del.id=record[i].json.ID;
					del.mgrId = record[i].json.MGR_ID;
					del.mgrName = record[i].json.MGR_NAME;
					del.institution=record[i].json.INSTITUTION;
					del.institution_name=record[i].json.INSTITUTION_NAME;
					delStr.push(del);
					custMgrGrid.store.remove(record[i]);
					custMgrGrid.store.sort('mgrId', 'ASC');
				}
			}
		}}
		       ]
	});
	var custMgrGrid =  new Ext.grid.GridPanel({	//协办客户经理分配数据表
		id : 'custMgrGrid',
		title:'协办客户经理',
		enableDragDrop   : true,
		ddText :'可将选择的数据拖到上方，设置为主办客户经理或者拖回原机构列表下',
		tbar:removeTbar,
		ddGroup:'gridDDGroup_1',
		region:"center",
		height : 270,
		layout:'fit',
		autoExpandMin:10,
		store : custmgrStore,
		cm : cm_1,
		loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
	});
	
	var rownum_2= new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var cm_2 = new Ext.grid.ColumnModel([  sm,  //供选择的客户经理列表的模型
	                                     {header : '客户经理编号',dataIndex : 'mgrId',sortable : true,width : 140,hidden:true},
	                                     {header : '客户经理名称',dataIndex : 'mgrName',sortable : true,width : 140},
	                                     {header : '客户经理所属机构',dataIndex : 'institution_name',sortable : true,width : 140,hidden:true},
	                                     {header : '客户经理所属机构号',dataIndex : 'institution',sortable : true,width : 140,hidden:true}
	                                     ]);
		
	var custMgrInfoStore = new Ext.data.Store( {//供选择的客户经理列表store
		restful : true,
		autoLoad : false,
		proxy : new Ext.data.HttpProxy( {
			url:basepath+'/custMgrBelongAction.json'
		}),
		baseParams:{
		'cust_id':cust_id
		},
		reader : new Ext.data.JsonReader( {//reader
		root : 'json.data'
		},[{name:'institution_name',mapping : 'INSTITUTION_NAME'},{name:'institution',mapping : 'INSTITUTION'},{name:'mgrId',mapping:'MGR_ID'},{name: 'mgrName',mapping : 'MGR_NAME'}])
	});
	custMgrInfoStore.load({
		params:{
		'orgId':JsContext._orgId
	}
	});
	var custMgrInfoGrid =  new Ext.grid.GridPanel({	//供选择的客户经理的数据表
		id : 'custMgrInfoGrid',
		title:'客户经理列表',
		enableDragDrop : true,
		ddText :'可将选择的数据拖到左侧，设置为协办客户经理或者拖至主办客户经理设置面板，设为主办客户经理',
		ddGroup:'gridDDGroup_2',
		autoLoad:null,
		height : 350,
		store : custMgrInfoStore,
		cm : cm_2,
		loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
	});
	/**
	 * 逐级查找父节点是否有主办权限
	 */
	
	function findMainTypeIcon(node){
		if(node.parentNode ){
			if( node.parentNode.icon && node.parentNode.icon=='主办'){
				return true;
			}else {
				findMainTypeIcon(node.parentNode);
				return false;
			}
		}
	}
	/**
	 * 判断节点是否存在于数组中(针对客户经理)
	 */
	function includeMgrControl(arr,tmp){
		for(var i=0;i<arr.length;i++){
			if(arr[i].MGR_ID==tmp.MGR_ID){
				return true;
			}
		}
		return false;
	};
	/**
	 * 判断节点是否存在于数组中(针对客户经理2)
	 */
	function includeMgrControl2(arr,tmp){
		for(var i=0;i<arr.length;i++){
			if(arr[i].json.MGR_ID==tmp.MGR_ID){
				return true;
			}
		}
		return false;
	};
	/**
	 * 判断节点是否存在于数组中(针对机构)
	 */
	
	function includeControl(arr,id){
		for(var i=0;i<arr.length;i++){
			if(arr[i].org_id==id){
				return true;
			}
		}
		return false;
	};

	/**
	 * 节点设置为‘主办’时，递归父节点将其标志为主办标志
	 */
	function mainTypeIndex(node){
		if(node.parentNode){
			var node =node.parentNode;
			if(node.icon && node.icon=='协办'){
				node.ui.elNode.removeChild(node.mainType);
				node.icon='';
				node.mainType='';
				if(includeControl(firstOrg,node.id)){
					for(var i=0;i<firstOrg.length;i++){
						if(firstOrg[i].org_id==node.id){}
						firstOrg.remove(firstOrg[i]);
					}
				}
			}
			if(node.icon && node.icon=='主办'){
				node.ui.elNode.removeChild(node.mainType);
				node.icon='';
				node.mainType='';
			}
			var imgs = document.createElement("img");
        	imgs.className = 'x-tree-icon';
        	imgs.name='x-tree-icon_';
			imgs.setAttribute("src", basepath+'/contents/images/zhuban.gif');
			node.mainType=imgs;
			node.icon='主办';
			node.ui.elNode.appendChild(imgs);
			mainTypeIndex(node);
		
		}
	}
	/**
	 * 节点设置为‘协办’时，递归父节点将其标志为协办标志
	 */
	function mainTypeIndex2(node){
		if(node.parentNode){
			var node =node.parentNode;
			if(node.icon=='主办'&&node.icon){
				return false;
			}else if(node.icon && node.icon=='协办'){
				node.ui.elNode.removeChild(node.mainType);
				node.icon='';
				node.mainType='';
				var imgs = document.createElement("img");
	        	imgs.className = 'x-tree-icon';
	        	imgs.name='x-tree-icon_';
				imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
				node.mainType=imgs;
				node.icon='协办';
				node.ui.elNode.appendChild(imgs);
				mainTypeIndex2(node);
			}else if(!node.icon){
				var imgs = document.createElement("img");
	        	imgs.className = 'x-tree-icon';
	        	imgs.name='x-tree-icon_';
				imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
				node.mainType=imgs;
				node.icon='协办';
				node.ui.elNode.appendChild(imgs);
				mainTypeIndex2(node);
			}
		}
	}
	/**
	 * 展开机构树全部节点
	 */
	function expandAllNodes(node){
		node.expanded=true;
		node.expand();
		if(node.hasChildNodes()){
			for(var i=0;i<node.childNodes.length;i++){
				expandAllNodes(node.childNodes[i]);
			}
		}
	}
	/**
	 * 清空设置时，若子节点协办标志取消，保存相应父节点作为协办机构
	 */
	function indexChildNodes(node){
		if(node.parentNode){
			var parentNode=node.parentNode;
			for(var i=0;i<firstOrg.length;i++){
				if(firstOrg[i].id==parentNode.id){
					firstOrg.remove(firstOrg[i]);
				}
			}
			if(!includeControl(secondOrg,node.id)){
				var second={};
	        	second.cust_id=oCustInfo.cust_id;
	        	second.org_id=node.id;
	    		second.type='2';
	    		second.org_name=node.text;
	    		secondOrg.push(second);	
			}
    		
			indexChildNodes(parentNode);
		}
	}
	/**
	 * 递归遍历树的全部节点，查找主办机构节点
	 */
	function indexAllNodes(node){
		if(node.icon=='主办'){
			if(node.hasChildNodes()){
				var childCount=0;
			for(var i=0;i<node.childNodes.length;i++){
				if(node.childNodes[i].icon=='主办'){
					childCount++;
					if(node.childNodes[i].hasChildNodes()){
						indexAllNodes(node.childNodes[i]);
					}else{
						var mainOrg={};
						mainOrg.cust_id=oCustInfo.cust_id;
						mainOrg.org_id=node.childNodes[i].id;
						mainOrg.org_name=node.childNodes[i].text;
						mainOrg.type='1';
						mainTypeOrg.push(mainOrg);
						mainTypeCount++;
						mainTypeNode='';
						mainTypeNode=node.childNodes[i];
					}
				}
			}
			if(childCount==0){
				var mainOrg={};
				mainOrg.cust_id=oCustInfo.cust_id;
				mainOrg.org_name=node.text;
				mainOrg.org_id=node.id;
				mainOrg.type='1';
				mainTypeOrg.push(mainOrg);
				mainTypeCount++;
				mainTypeNode='';
				mainTypeNode=node;
			}
			}else{
				var mainOrg={};
				mainOrg.cust_id=oCustInfo.cust_id;
				mainOrg.org_name=node.text;
				mainOrg.org_id=node.id;
				mainOrg.type='1';
				mainTypeOrg.push(mainOrg);
				mainTypeCount++;
				mainTypeNode='';
				mainTypeNode=node;
			}
		}
		
	}
	/**
	 * 清除设置时，遍历节点的全部子节点，清除‘主办’标志
	 */
  function destoryMainType(node){
		if(node.hasChildNodes()){
			for(var i =0;i<node.childNodes.length;i++){
				var children = node.childNodes[i];
				if(children.icon && children.icon=='主办'){
					children.ui.elNode.removeChild(children.ui.elNode.children[4]);
					children.icon='';
					children.mainType='';
				}
				destoryMainType(children);
			}
		}
  }
	/**
	 * 清除设置时，遍历节点的全部子节点，清除‘协办’标志
	 */
  function destoryCoType(node){
	  if(node.hasChildNodes()){
			for(var i =0;i<node.childNodes.length;i++){
				var children = node.childNodes[i];
				if(children.icon && children.icon=='协办'){
					children.icon='';
					children.mainType='';
					children.ui.elNode.removeChild(children.ui.elNode.children[4]);
				
					if(!includeControl(secondOrg,children.id))
					{
					var second={};
		        	second.cust_id=oCustInfo.cust_id;
		        	second.org_id=children.id;
	        		second.type='2';
	        		second.org_name=node.text;
	        		secondOrg.push(second);
	        		}
				}
				destoryCoType(children);
			}
		}
  }
  
	var mainTypeStore = new Ext.data.Store ({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=MAINTAIN_TYPE'
		}),
		reader : new Ext.data.JsonReader ({
			root : 'JSON'
		},['key','value'])
	});
	mainTypeStore.load();
	// 下拉框(分配机构弹窗)
	var combo = new Ext.form.ComboBox({
		typeAhead: true,
		triggerAction: 'all',
		forceSelection : true,
		mode: 'local',
		store: new Ext.data.ArrayStore({
			id: 0,
			fields: ['mainType',
			         'displayText' ],
			data: [[3,'空'],[1, '主办'], [2, '协办']]
		    }),
		    valueField: 'mainType',
		    displayField: 'displayText',
		    hiddenName : 'mainType',
		    allowBlank : false,
		    listeners: {
		    	select: function (a, b) {
		  			var v = a.value;	//取到valueField中的值
		  			combo.setValue(v);
	  			}
	  		}
	  });
	  Ext.util.Format.comboRenderer = function(combo){
		    return function(value){
		        var record = combo.findRecord(combo.valueField, value);
		        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
		    };
		};
	//归属机构信息Start**********************************************************
	// 归属机构列表的数据查询
	var record1 = Ext.data.Record.create([
	                                      {name: 'institutionCode', mapping: 'INSTITUTION_CODE'},                                    
	                                      {name: 'institutionName', mapping: 'INSTITUTION_NAME'},
	                                      {name: 'mainType', mapping: 'MAIN_TYPE'},                                   
	                                      {name: 'assignUsername', mapping: 'ASSIGN_USERNAME'},  
	                                      {name : 'assignDate',mapping : 'ASSIGN_DATE'},
	                                      {name : 'parentId',mapping : 'UNITSEQ'}
	                                      ]);
	
		// 每页显示条数下拉选择框
	var pagesize_combo2 = new Ext.form.ComboBox({
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
	var number2 = parseInt(pagesize_combo2.getValue());
	var orgStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_org_adjust.json?cust_id=' + cust_id/*,
			success:function(response){
			alert(response.responseText);
		}*/
		}),
		reader : new Ext.data.JsonReader( {
			successProperty: 'success',
	        messageProperty: 'message',
			root : 'json.data',
			totalProperty: 'json.count'
		},record1)
	});
	var bbar2 = new Ext.PagingToolbar({
		pageSize : number,
		store : orgStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', pagesize_combo2]
	});
	custmgrStore2.load({
		params:{
		start:0,
		limit: parseInt(pagesize_combo2.getValue())
	}
	});
	pagesize_combo2.on("select", function(comboBox) {
		bbar2.pageSize = parseInt(pagesize_combo2.getValue()),
		orgStore.load({
			params : {
			start : 0,
			limit : parseInt(pagesize_combo2.getValue())
		}
		});
	});
	// 定义自动当前页行号
	var orgRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//归属机构列表的模型
	var belOrgCm = new Ext.grid.ColumnModel([orgRownum,
	       {
			 	header : '机构编号',
			 	dataIndex : 'institutionCode',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '机构名称',
			 	dataIndex : 'institutionName',
			 	sortable : true,
			 	width : 200
	       }, {
				dataIndex : 'mainType',
				header : '主协办类型',
				sortable : true,
				width : 80,
				renderer: Ext.util.Format.comboRenderer(combo)
			}, {
			 	header : '分配人名称',
			 	dataIndex : 'assignUsername',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '分配日期',
			 	dataIndex : 'assignDate',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '父节点机构号',
			 	dataIndex : 'parentId',
			 	sortable : true,
			 	width : 100,
			 	hidden:true
	       }
	]);

	var adjustOrgStore = new Ext.data.Store( {
		restful : true,
//		autoLoad : false,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_org_adjust.json?cust_id=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},['ID','CUST_ID','INSTITUTION_CODE','INSTITUTION_NAME','ASSIGN_USER'])
	});
	adjustOrgStore.load();

	

	// 定义自动当前页行号
	var custmgrRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//归属客户经理列表的模型
	var belCustmgrCm = new Ext.grid.ColumnModel([custmgrRownum,
	       {
			 	header : '客户经理编号',
			 	dataIndex : 'mgrId',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '客户经理名称',
			 	dataIndex : 'mgrName',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '所属机构号',
			 	dataIndex : 'institution',
			 	sortable : true,
			 	width : 100
	       }, {
				dataIndex : 'institution_name',
				header : '所属机构名称',
				sortable : true,
				width : 200
			}, {
				dataIndex : 'mainType',
				header : '主协办类型',
				sortable : true,
				width : 80,
				renderer: Ext.util.Format.comboRenderer(combo)
			}, {
			 	header : '分配人名称',
			 	dataIndex : 'assignUsername',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '分配日期',
			 	dataIndex : 'assignDate',
			 	sortable : true,
			 	//renderer : Ext.util.Format.dateRenderer('Y-m-d'),
			 	width : 100
	       }
	]);
  	
	//归属客户经理工具栏
	var belCustmgrTbar = new Ext.Toolbar({
	    items: [{
	            text: '归属调整',
	            iconCls : 'editIconCss', 
	            handler : function(value){
	    	var unitseqMain=[];
	    	var unitseq=[];
	    	for(var i = 0;i<orgStore.data.items.length;i++ ){
	    		var data = orgStore.data.items[i].json;
	    		var dataLength = data.UNITSEQ.split(',').length;
	    		if(data.MAIN_TYPE == '1'){
	    			for(var j=0;j<dataLength;j++){
		    			if(unitseqMain.indexOf(data.UNITSEQ.split(',')[j])<0){
		    				unitseqMain.push(data.UNITSEQ.split(',')[j]);
		    			}
		    		}
	    		}else if (data.MAIN_TYPE == '2'){
	    			for(var k=0;k<dataLength;k++){
		    			if(unitseq.indexOf(data.UNITSEQ.split(',')[k])<0){
		    				unitseq.push(data.UNITSEQ.split(',')[k]);
		    			}
		    		}
	    		}
	    	
	    	}
			currentNode='';
	    	custMainTypeMgrStore.reload();
//	    	if(!leftTreeForShows.root.hasChildNodes() && !leftTreeForShows2.root.hasChildNodes()){
//	    		Ext.msg.alert('提示','该客户尚未分配归属机构');
//	    		return false;
//	    	}
	    	for( var k=0;k<custMgrGrid.store.data.items.length;k++){
	    		custMgrGrid_store.push(custMgrGrid.store.data.items[k].json);
	    	}
	    			//用户登录系统后，列出的客户就是该用户所在的机构下的
					//该客户是本机构下的客户，不属于下级机构的
	    			//该客户分配的客户经理也是该机构的客户经理
	    			//客户通过该客户编号到归属客户经理表，找到该客户已分配的客户经理
	    			//根据系统用户登录的机构，找出该机构下的经理
	    			//将客户编号传到Action中，查找归属客户经理信息表下对应的记录
		var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
			checkField : 'ASTRUE',
			parentAttr : 'SUPERUNITID',
			locateAttr : 'root',//UNITID
			rootValue : JsContext._orgId,
			textField : 'UNITNAME',
			idProperties : 'ID'
		});
		var condition = {searchType:'ALLORG'};
		Ext.Ajax.request({
			url : basepath + '/commsearch.json?condition='+ Ext.encode(condition),
			method : 'GET',
			success : function(response) {
				var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
				loader.nodeArray = nodeArra;
				var children = loader.loadAll();
				orgTreeForShow.appendChild(children);
			},
			failure : function(a, b, c) {
			}
		});

		var orgTreeForShow = new Com.yucheng.bcrm.TreePanel({
			checkBox : this.checkBox,
			width : 300,
			heigth : 385,
			autoScroll : true,
			/** 虚拟树形根节点 */
			root : new Ext.tree.AsyncTreeNode({
				id :JsContext._orgId,
				expanded : true,
				text:JsContext._unitname,
				autoScroll : true,
				children : []
			}),
			resloader : loader,
			split : true,
			listeners:{
			'contextmenu':function(node,a){
			if(!node.isRoot){
				a.stopEvent();
				new Ext.menu.Menu({
			        items: [{
			            text: '设置为主办',
			            hidden:(orgTreeForShow.root.icon=='主办')?false:true,
			            handler: function(){
			        	indexAllNodes(orgTreeForShow.root);
			        	var index=mainTypeNode.findChild('id',node.id,true);
//				        	mainTypeNode='';
			        	mainTypeOrg=[];
//				        	mainTypeCount=0;
			        	if(node.icon=='主办' && node.icon){
			        		Ext.MessageBox.alert('提示','当前机构已是主办机构');
			        		mainTypeOrg=[];
							mainTypeCount=0;
			        		return false;
			        	}else if(mainTypeCount> 0 && mainTypeNode.id!=orgTreeForShow.root.id && !index ){
			        		
			        		Ext.MessageBox.alert('提示','主办机构只能有一个，请取消后再设置');
			        		mainTypeOrg=[];
							mainTypeCount=0;
			        	}else {
			        		if(node.mainType){
			        			if(node.icon=='主办'){
			        				
	    				       	}else if(node.icon=='协办'){
	    				       	if(!includeControl(secondOrg,node.id)){
	    				       		var second={};
		    				       	second.cust_id=oCustInfo.cust_id;
		    				       	second.org_id=node.id;
	    				       		second.type='2';
	    				       		second.org_name=node.text;
	    				       		secondOrg.push(second);
	    				       		}
	    				       	}
			        			node.ui.elNode.removeChild(node.mainType);
			        			node.icon='';
			        			node.mainType='';
			        		}
				        	var imgs = document.createElement("img");
				        	imgs.className = 'x-tree-icon';
				        	imgs.name='x-tree-icon_';
							imgs.setAttribute("src", basepath+'/contents/images/zhuban.gif');
							node.mainType=imgs;
							node.icon='主办';
							node.ui.elNode.appendChild(imgs);
				        	mainTypeIndex(node);
				        	mainTypeOrg=[];
							mainTypeCount=0;
			        	}
		        	},
			            scope: this
			        },
			        {
			            text: '设置为协办',
			            hidden:(cust_distribute_type_params=='3'||cust_distribute_type_params=='4')?true:false,
			            handler: function(){
			        	if(node.icon=='协办' && node.icon){
			        		Ext.MessageBox.alert('提示','当前机构已是协办机构');
			        		return false;
			        	}else{
			        		if(node.mainType){
			        			if(node.icon=='主办'){
			        				mainTypeCount=0;
			        				indexAllNodes(node);
			        				mainTypeNode;
			        				if(mainTypeCount>0 && mainTypeNode.id!=node.id){
			        				Ext.MessageBox.alert('提示','当前机构辖内含有主办机构，不可被设为协办机构');
			        				return false;
			        				}
	    				       	}else if(node.icon=='协办'){
	    				       		
	    				       	if(!includeControl(secondOrg,node.id)){
	    				       		var second={};
		    				       	second.cust_id=oCustInfo.cust_id;
		    				       	second.org_id=node.id;
	    				       		second.type='2';
	    				       		second.org_name=node.text;
	    				       		secondOrg.push(second);
	    				       	}
	    				       	}
			        			node.ui.elNode.removeChild(node.mainType);
			        			node.icon='';
			        			node.mainType='';
			        			
			        		}
			               	var first={};
			              
				        	var imgs = document.createElement("img");
				        	imgs.className = 'x-tree-icon';
				        	imgs.name='x-tree-icon_';
							imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
							node.mainType=imgs;
							node.icon='协办';
							node.ui.elNode.appendChild(imgs);
							if(!includeControl(firstOrg,node.id))
							{
							first.cust_id=oCustInfo.cust_id;
							first.org_id=node.id;
							first.org_name=node.text;
							first.type='2';
							firstOrg.push(first);
							}
							mainTypeIndex2(node);
			        	}
			        	},
			            scope: this
			        },
			        {
			            text: '清空设置',
			            handler: function(){
			        	if(node.mainType){
			        		if(node.icon=='主办'){
			        			destoryMainType(node);	
			        			node.ui.elNode.removeChild(node.mainType);//node.ui.elNode.children[4]
			        			node.mainType='';
			        			node.icon='';
    				        	if(node.hasChildNodes()){
    				        		for(var i = 0;i<node.childNodes.length;i++){
    				        			if(node.childNodes[i].icon=='协办'){
    				        				var imgs = document.createElement("img");
    				        				imgs.className = 'x-tree-icon';
    		    				        	imgs.name='x-tree-icon_';
    		    							imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
    		    							node.mainType=imgs;
    		    							node.icon='协办';
    				        			}
    				        		}
    				        		if(node.mainType){
    				        			node.ui.elNode.appendChild(node.mainType);
    				        		}
    				        	}
			        		}else if(node.icon=='协办'){
			        			if(!includeControl(secondOrg,node.id)){
			        				var second={};
	    				        	second.cust_id=oCustInfo.cust_id;
	    				        	second.org_id=node.id;
    				        		second.type='2';
    				        		second.org_name=node.text;
    				        		secondOrg.push(second);
			        			}
			        			node.ui.elNode.removeChild(node.mainType);//node.ui.elNode.children[4]
			        			node.mainType='';
			        			node.icon='';
				        		indexChildNodes(node);//递归将父节点置为协办标志(在父节点不为主办机构的前提下)
				        		
				        		var tag=0;
				        		if(node.parentNode){
				        			if(node.parentNode.icon && node.parentNode.icon=='协办'){
				        				for(var j=0;j<adjustOrgStore.data.items.length;j++){
    				        				if(adjustOrgStore.data.items[j].json.ID==node.parentNode.id)
    				        					tag++;
    				        			}
	    				        	if(tag==0){
	    				        		if(!includeControl(firstOrg,node.parentNode.id)){
	    				        			var q=0;
	    				        			for(var i=0;i<node.parentNode.childNodes.length;i++){
	    				        				if(node.parentNode.childNodes[i].icon=='协办'){
	    				        					q++;
	    				        				}
	    				        			}
	    				        			if(q=0){
	    				        				var first={};
			    				        		first.cust_id=oCustInfo.cust_id;
			    				        		first.org_id=node.parentNode.id;
			    				        		first.org_name=node.parentNode.text;
			    				        		first.type='2';
			    				        		firstOrg.push(first);
	    				        			}
	    				        		}
	    				        	}	
				        			}
				        		}
				        		destoryCoType(node);
			        		}
			        	}
			        },
			        scope: this
			        }]
				}).showAt(a.getXY());
			}
		},
		'expandnode':function(a,b,c,d){
			
//			expandAllNodes(b);
			a.expanded=true;
			a.expand();
			   setTimeout(function(){
//					adjustOrgStore.load();
  				for(var i = 0;i<unitseqMain.length;i++){
  					

						var imgs = document.createElement("img");
						imgs.className = 'x-tree-icon';
						imgs.name='x-tree-icon_';
						imgs.setAttribute("src", basepath+'/contents/images/zhuban.gif');
						if(!(unitseqMain[i]==orgTreeForShow.root.id)){
							var node = a.findChild('id',unitseqMain[i],true);
//							var node = orgTreeForShow.root.findChild('id',adjustOrgStore.data.items[i].json.INSTITUTION_CODE,true);
	    					if(node){
//	    						if(node.mainType){
//	    							node.ui.elNode.removeChild(node.mainType);
//	    							node.icon='';
//	    							node.mainType='';
//	    						}
    						if(!node.mainType){
    							node.mainType=imgs;
   	    						node.icon='主办';
   	    						node.ui.elNode.appendChild(imgs);
   	    						mainTypeIndex(node);
						}
	    					}
						}else{

    						if(orgTreeForShow.root.mainType){
    							orgTreeForShow.root.ui.elNode.removeChild(orgTreeForShow.root.mainType);
    							orgTreeForShow.root.icon='';
    							orgTreeForShow.root.mainType='';
    						}
    						orgTreeForShow.root.mainType=imgs;
    						orgTreeForShow.root.icon='主办';
    						
    						orgTreeForShow.root.ui.elNode.appendChild(imgs);
    						mainTypeIndex(orgTreeForShow.root);
    					
						} };
						
						
						for(var j = 0;j<unitseq.length;j++)	{

	  						var imgs = document.createElement("img");
	  						imgs.className = 'x-tree-icon';
	  						imgs.name='x-tree-icon_';
	  						imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
	  						
	  						if(!(unitseq[j]==orgTreeForShow.root.id)){
		    						var node = orgTreeForShow.root.findChild('id',unitseq[j],true);
	  	    					if(node && (!node.mainType)){
	  	    						node.mainType=imgs;
	  	    						node.icon='协办';
	  	    						node.ui.elNode.appendChild(imgs);
	  	    						mainTypeIndex2(node);
	  	    						custMgrGrid.setDisabled(false);
	  	    					}
	  						}else{
	  	    					if(orgTreeForShow.root && (!orgTreeForShow.root.mainType)){
	  	    						orgTreeForShow.root.mainType=imgs;
	  	    						orgTreeForShow.root.icon='协办';
	  	    						orgTreeForShow.root.ui.elNode.appendChild(imgs);
	  	    						mainTypeIndex2(orgTreeForShow.root);
	  	    						custMgrGrid.setDisabled(false);
	  	    					}
	  						}
						}
  				
			   },1000);
			   
			   
			   setTimeout(function(){
				   
//					adjustOrgStore.load();
   				for(var i = 0;i<adjustOrgStore.data.items.length;i++){
   					if(adjustOrgStore.data.items[i].json.MAIN_TYPE=='1'){
   						var imgs = document.createElement("img");
   						imgs.className = 'x-tree-icon';
   						imgs.name='x-tree-icon_';
   						imgs.setAttribute("src", basepath+'/contents/images/zhuban.gif');
   						if(!(adjustOrgStore.data.items[i].json.INSTITUTION_CODE==orgTreeForShow.root.id)){
   							var node = a.findChild('id',adjustOrgStore.data.items[i].json.INSTITUTION_CODE,true);
//   							var node = orgTreeForShow.root.findChild('id',adjustOrgStore.data.items[i].json.INSTITUTION_CODE,true);
   	    					if(node){
//   	    						if(node.mainType){
//   	    							node.ui.elNode.removeChild(node.mainType);
//   	    							node.icon='';
//   	    							node.mainType='';
//   	    						}
	    						if(!node.mainType){
	    							node.mainType=imgs;
	   	    						node.icon='主办';
	   	    						node.ui.elNode.appendChild(imgs);
	   	    						mainTypeIndex(node);
	   	    					
   						}
   	    					
   	    					}
   						}else{

	    						if(orgTreeForShow.root.mainType){
	    							orgTreeForShow.root.ui.elNode.removeChild(orgTreeForShow.root.mainType);
	    							orgTreeForShow.root.icon='';
	    							orgTreeForShow.root.mainType='';
	    						}
	    						orgTreeForShow.root.mainType=imgs;
	    						orgTreeForShow.root.icon='主办';
	    						
	    						orgTreeForShow.root.ui.elNode.appendChild(imgs);
	    						mainTypeIndex(orgTreeForShow.root);
	    					
   						}
   						
   					}else if(adjustOrgStore.data.items[i].json.MAIN_TYPE=='2'){
   						var imgs = document.createElement("img");
   						imgs.className = 'x-tree-icon';
   						imgs.name='x-tree-icon_';
   						imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
   						
   						if(!(adjustOrgStore.data.items[i].json.INSTITUTION_CODE==orgTreeForShow.root.id)){
	    						var node = orgTreeForShow.root.findChild('id',adjustOrgStore.data.items[i].json.INSTITUTION_CODE,true);
   	    					if(node && (!node.mainType)){
   	    						node.mainType=imgs;
   	    						node.icon='协办';
   	    						node.ui.elNode.appendChild(imgs);
   	    						mainTypeIndex2(node);
   	    						custMgrGrid.setDisabled(false);
   	    					}
   						}else{
   	    					if(orgTreeForShow.root && (!orgTreeForShow.root.mainType)){
   	    						orgTreeForShow.root.mainType=imgs;
   	    						orgTreeForShow.root.icon='协办';
   	    						orgTreeForShow.root.ui.elNode.appendChild(imgs);
   	    						mainTypeIndex2(orgTreeForShow.root);
   	    						custMgrGrid.setDisabled(false);
   	    					}
   						}
   					}
   				}
			   },2000);
//			c.addListener('show',function(){
//				debugger;
//			});
		},
        'click' : function(node) {
			selRecord_1="";
			var orgid = node.id;
			currentNode=node;

			custMgrInfoStore.load({
				params:{
				'orgId':orgid
				}
			});	
			
//				custmgrStore.reload();
			custMainTypeMgrStore.reload();
//				custMgrInfoStore.reload();
			setTimeout(function(){
				for(var k=0; k<custMgrInfoStore.data.items.length; k++){
					if(custMgrPanel.getForm().findField('mgrId').getValue()==custMgrInfoStore.data.items[k].json.ACCOUNT_NAME){
						selRecord_1=custMgrInfoStore.data.items[k];
						custMgrInfoStore.removeAt(k);
						break;
					}
				}
				
			},500);
			setTimeout(function(){
//					custMgrGrid.store.reload();
				
				for(var i=0;i<custMgrGrid.store.data.items.length;i++){
					for(var j=0;j<custMgrInfoStore.data.items.length;j++){
						if(custMgrGrid.store.data.items[i].json.MGR_ID==custMgrInfoStore.data.items[j].json.ACCOUNT_NAME){
							custMgrInfoStore.removeAt(j);
						}
					}
				}
			},500);
	    }
		}
		});
			var orgUserPanel = new Ext.form.FormPanel({//查询panel
				width:'25%',
				height:350,
				frame:true,
				autoScroll : true,
				region:'west',
				split:true,
				items:[orgTreeForShow]
			});
			var adjustMgrWindow = new Ext.Window({//展示窗口
				layout : 'fit',
				width : 900,
				height : 416,
				closable : true,
				autoScroll : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				shadow : true,
				loadMask : true,
				maximizable : true,
				collapsible : true,
				titleCollapse : true,
				animCollapse : false,
				animateTarget : Ext.getBody(),
				border : false,
				buttonAlign : "center",
				title : '客户归属调整',
				items : [{
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .35,
						layout : 'form',
						items : [orgUserPanel ]
					}, {
						columnWidth : .45,
						layout : 'form',
						items : [custMgrPanel,custMgrGrid ]
					}, {
						columnWidth : .20,
						layout : 'form',
						items : [ custMgrInfoGrid]
					}
					]
				}],
				buttons:[{
					text:'保存',
						id:'saveButton',
						handler:function(){
				/**				
				 * 归属机构保存逻辑
				 */
					var flag=0;
					mainTypeCount=0;
					indexAllNodes(orgTreeForShow.root);
					if (mainTypeCount == 0 && (orgTreeForShow.root.icon!='协办')) {
						Ext.MessageBox.alert('提示','尚未设置主办机构!');
						mainTypeOrg=[];
						mainTypeCount=0;
					}else if (mainTypeCount> 1) {
						Ext.MessageBox.alert('提示','主办机构只能有一个!');
						mainTypeOrg=[];
						mainTypeCount=0;
					} else if(cust_distribute_type_params=='3'||cust_distribute_type_params=='4'){
    					for(var i = 0;i<adjustOrgStore.data.items.length;i++){
    						if(adjustOrgStore.data.items[i].json.MAIN_TYPE=='2'){
    							var k=0;
    							for(var j=0;j<secondOrg.length;j++){
    							if(	secondOrg[j].org_id==adjustOrgStore.data.items[i].json.INSTITUTION_CODE){
    								k++;
    							}
    						}
    							if(k==0){
    								Ext.MessageBox.alert('提示','归属机构分配模式已更改，请根据新的分配模式进行调整!');
									mainTypeOrg=[];
									mainTypeCount=0;	
    							} else {
    	    						for(var i =0;i<firstOrg.length;i++){
    									var tag=0;
    									for(var j =0;j<secondOrg.length;j++){
    										if(firstOrg[i].org_id==secondOrg[j].org_id && firstOrg[i].cust_id==secondOrg[j].cust_id){
    											secondOrg.remove(secondOrg[j]);
    											j--;
    											tag++;
    										}
    										
    									}
    									if(tag>0){
    										firstOrg.remove(firstOrg[i]);
    										i--;
    									}
    									tag=0;
    								}	
    							}
    						}
    					}
					} else if(cust_distribute_type_params=='1'||cust_distribute_type_params=='2'){
						for(var i =0;i<firstOrg.length;i++){
							var tag=0;
							for(var j =0;j<secondOrg.length;j++){
								if(firstOrg[i].org_id==secondOrg[j].org_id && firstOrg[i].cust_id==secondOrg[j].cust_id){
									secondOrg.remove(secondOrg[j]);
									j--;
									tag++;
								}
							}
							if(tag>0){
								firstOrg.remove(firstOrg[i]);
								i--;
							}
							tag=0;
						}	
					}
					
					/**				
					 * 归属客户经理保存逻辑
					 */
					var mainMgrId = "";//已选择的主办客户经理的ID
					var mainMgrName = "";//已选的主办客户经理的NAME
					var mainMgr_institution ="";
					var mainMgr_institution_name ="";
					var mainMgrIdt = custMgrPanel.getForm().findField('mgrId').getValue();//获取当前主办客户经理的ID，用来判断是否选择主办客户经理
				    if(!custMgrPanel.disabled && mainMgrIdt==""){
				    	Ext.Msg.alert('提示信息', '主办客户经理为空，请选择！');
				    	return false;
				    }
					mainMgrId = mainMgrIdt;
				    mainMgrName = custMgrPanel.getForm().findField('mgrName').getValue();
				    mainMgr_institution=custMgrPanel.getForm().findField('institution').getValue();
				    mainMgr_institution_name=custMgrPanel.getForm().findField('institution_name').getValue();
				    
						var oMainLength = custMgrGrid.store.data.length;//已选择协办客户经理的数目
						var newData=custMgrGrid.store.data.items;
						var oldData=custMgrGrid_store;

						for(var i =0;i<newData.length;i++){
							if(!includeMgrControl(oldData,newData[i].json)){
								var add={};
								add.mgrId = newData[i].json.MGR_ID;
								add.mgrName = newData[i].json.MGR_NAME;
								add.institution=newData[i].json.INSTITUTION;
								add.institution_name=newData[i].json.INSTITUTION_NAME;
								addStr.push(add);
							}
						}
						
						for(var i =0;i<oldData.length;i++){
							if(!includeMgrControl2(newData,oldData[i])){
								var del={};
								del.id=oldData[i].ID;
								del.mgrId = oldData[i].MGR_ID;
								del.mgrName = oldData[i].MGR_NAME;
								del.institution=oldData[i].INSTITUTION;
								del.institution_name=oldData[i].INSTITUTION_NAME;
								delStr.push(del);
							}
						}
						
//						custMgrGrid_store=[];
						Ext.Ajax.request( {
							url : basepath + '/custMgrBelongAction!save.json',
							method : 'POST',
							params : {
								'mainTypeOrg' : Ext.encode(mainTypeOrg),
								'firstOrg' : Ext.encode(firstOrg),
								'secondOrg' : Ext.encode(secondOrg),
								
								'addStr' : Ext.encode(addStr),//增加协办客户经理数组
								'delStr' : Ext.encode(delStr),//删除协办客户经理数组
								'custIds' :cust_id,//所选择的客户的custId
								'mainMgrId':mainMgrId,
								'mainMgrName':mainMgrName,
								'mainMgr_institution':mainMgr_institution,
								'mainMgr_institution_name':mainMgr_institution_name
						},
						success : function(){
							
							firstOrg=[];
							secondOrg=[];
							mainTypeOrg=[];
							mainTypeCount=0;
							Ext.Msg.alert('提示', '操作成功');
							orgStore.load();
							adjustOrgStore.reload();
							addStr=[];
							delStr=[];
							mainTypeOrg=[];
							firstOrg=[];
							secondOrg=[];
							mainMgrId="";
							mainMgrName="";
							mainMgr_institution="";
							mainMgr_institution_name="";
							custmgrStore2.reload();
//							Ext.getCmp('saveButton').setDisabled(true);
							custBelongHisStore.reload();
							
						},
						failure : function(){
							mainTypeOrg=[];
							mainTypeCount=0;
							Ext.Msg.alert('提示信息','操作失败');
							addStr=[];
							delStr=[];
						}
						}); 
						mainTypeOrg=[];
						mainTypeCount=0;

				
				}},'-',{
					text:'重置',
					handler:function(){
					if(custMainTypeMgrStore.data.items.length>0){
						custMgrPanel.getForm().findField('mgrName').setValue(custMainTypeMgrStore.data.items[0].json.MGR_NAME);
						custMgrPanel.getForm().findField('mgrId').setValue(custMainTypeMgrStore.data.items[0].json.MGR_ID);
						custMgrPanel.getForm().findField('institution').setValue(custMainTypeMgrStore.data.items[0].json.INSTITUTION);
						custMgrPanel.getForm().findField('institution_name').setValue(custMainTypeMgrStore.data.items[0].json.INSTITUTION_NAME);
					}
		
					custmgrStore.reload();
					custMainTypeMgrStore.reload();
					custMgrInfoStore.removeAll();
				}
				}
				]
			});
			adjustMgrWindow.on('hide',function(){
				Ext.getCmp('saveButton').setDisabled(false);
			});
	    			adjustMgrWindow.show();
	    		
	    			custmgrStore.reload();
					custMainTypeMgrStore.reload();
					custMgrInfoStore.removeAll();
	    			var lm = new Ext.LoadMask (document.body,{
	    			   	msg : '正在加载菜单数据,请稍等...'
	    			});
	    			lm.show();	
    				
    				   setTimeout(function(){
    					   
//    						adjustOrgStore.load();
    	    				for(var i = 0;i<adjustOrgStore.data.items.length;i++){
    	    					if(adjustOrgStore.data.items[i].json.MAIN_TYPE=='1'){
    	    						var imgs = document.createElement("img");
    	    						imgs.className = 'x-tree-icon';
    	    						imgs.name='x-tree-icon_';
    	    						imgs.setAttribute("src", basepath+'/contents/images/zhuban.gif');
    	    						if(!(adjustOrgStore.data.items[i].json.INSTITUTION_CODE==orgTreeForShow.root.id)){
    	    							var node = orgTreeForShow.root.findChild('id',adjustOrgStore.data.items[i].json.INSTITUTION_CODE,true);
    	    	    					if(node){
    	    	    						if(node.mainType){
    	    	    							node.ui.elNode.removeChild(node.mainType);
    	    	    							node.icon='';
    	    	    							node.mainType='';
    	    	    						}
    	    	    						node.mainType=imgs;
    	    	    						node.icon='主办';
    	    	    						
    	    	    						node.ui.elNode.appendChild(imgs);
    	    	    						mainTypeIndex(node);
    	    	    					}
    	    						}else{

	    	    						if(orgTreeForShow.root.mainType){
	    	    							orgTreeForShow.root.ui.elNode.removeChild(orgTreeForShow.root.mainType);
	    	    							orgTreeForShow.root.icon='';
	    	    							orgTreeForShow.root.mainType='';
	    	    						}
	    	    						orgTreeForShow.root.mainType=imgs;
	    	    						orgTreeForShow.root.icon='主办';
	    	    						
	    	    						orgTreeForShow.root.ui.elNode.appendChild(imgs);
	    	    						mainTypeIndex(orgTreeForShow.root);
	    	    					
    	    						}
    	    						
    	    					}else if(adjustOrgStore.data.items[i].json.MAIN_TYPE=='2'){
    	    						var imgs = document.createElement("img");
    	    						imgs.className = 'x-tree-icon';
    	    						imgs.name='x-tree-icon_';
    	    						imgs.setAttribute("src", basepath+'/contents/images/xieban.gif');
    	    						
    	    						if(!(adjustOrgStore.data.items[i].json.INSTITUTION_CODE==orgTreeForShow.root.id)){
        	    						var node = orgTreeForShow.root.findChild('id',adjustOrgStore.data.items[i].json.INSTITUTION_CODE,true);
            	    					if(node && (!node.mainType)){
            	    						node.mainType=imgs;
            	    						node.icon='协办';
            	    						node.ui.elNode.appendChild(imgs);
            	    						mainTypeIndex2(node);
            	    					}
    	    						}else{
            	    					if(orgTreeForShow.root && (!orgTreeForShow.root.mainType)){
            	    						orgTreeForShow.root.mainType=imgs;
            	    						orgTreeForShow.root.icon='协办';
            	    						orgTreeForShow.root.ui.elNode.appendChild(imgs);
            	    						mainTypeIndex2(orgTreeForShow.root);
            	    					}
    	    						}
    	    					}
    	    				}
    				   },2000);
    				   setTimeout(function(){
    					   lm.hide();
    				   },2000);
	    			setTimeout(function(){
    					if(!orgTreeForShow.root.icon || orgTreeForShow.root.icon=='协办'){
    						custMgrPanel.setDisabled(true);
    					}else if(orgTreeForShow.root.icon && orgTreeForShow.root.icon=='主办'){
    						custMgrPanel.setDisabled(false);
    					}
    					if(!orgTreeForShow.root.icon){
    						custMgrGrid.setDisabled(true);
    					}else{
    						custMgrGrid.setDisabled(false);
    					}
	    			},4000);
	    			
    					if(custMainTypeMgrStore.data.items.length>0){
    						custMgrPanel.getForm().findField('mgrName').setValue(custMainTypeMgrStore.data.items[0].json.MGR_NAME);
    						custMgrPanel.getForm().findField('mgrId').setValue(custMainTypeMgrStore.data.items[0].json.MGR_ID);
    						custMgrPanel.getForm().findField('institution').setValue(custMainTypeMgrStore.data.items[0].json.INSTITUTION);
    						custMgrPanel.getForm().findField('institution_name').setValue(custMainTypeMgrStore.data.items[0].json.INSTITUTION_NAME);
    					}
					
	    			function dropTargetEl_1(){//拖拽方法
	    				var custMgrInfoGridDropTargetE1 =  custMgrInfoGrid.getView().scroller.dom;//custMgrInfoGrid的作用域
	    				var custMgrGridDropTargetE1 = custMgrGrid.getView().scroller.dom;//custMgrGrid的作用域
	    				var custMgrPanelDropTargetEl = custMgrPanel.getForm().findField('mgrName').getEl().dom;//custMgrPanel的作用域

	    				var custMgrInfoGridDropTarget1 = new Ext.dd.DropTarget(custMgrGridDropTargetE1, {
	    					ddGroup : 'gridDDGroup_2',
	    					notifyEnter : function(ddSource, e, data) {
	    					data.ddel.innerText = "设置为协办客户经理";
	    				},
	    				notifyOut : function(ddSource,e,data){
	    				data.ddel.innerHTML = "请将选择的数据拖至主办客户经理设置面板或者拖至协办客户经理列表";
	    				},
	    					notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
	    					if(currentNode.icon){
	    						var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
		    					var belongt = cust_distribute_type_params;//归属分配类型参数
		    					if(belongt == '2' || belongt == '4'){//若该客户只可归属于一个客户经理，需加约束
		    						var addLength = custMgrGrid.store.data.items.length;
		    						if(addLength >= 1){
		    		    				Ext.Msg.alert("提示", "每个归属机构下只允许分配一个客户经理，请先将已选择的客户经理托出!");
		    		    				return false;
		    						}else{
		    							Ext.Msg.alert("提示", "客户只可归属于一名客户经理!");
		    		    				return false;

		    						}
		    						}
		    					else{
		    					Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
		    					custMgrGrid.store.add(selectedRecord);
		    					custMgrGrid.store.sort('mgrId', 'ASC');
		    					return true;}
	    					}else{
	    						Ext.Msg.alert("提示", "当前机构非此客户的主/协办归属机构!");
	    					}
	    				}			
	    				});
	    				
	    				var custMgrGridDropTarget1 = new Ext.dd.DropTarget(custMgrInfoGridDropTargetE1, {
	    					ddGroup : 'gridDDGroup_1',
	    					notifyEnter : function(ddSource, e, data) {
	    					data.ddel.innerText = "移除协办客户经理";
	    					custMgrGrid.body.stopFx();
	    					custMgrInfoGrid.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
	    				},
	    				notifyOut : function(ddSource,e,data){
	    					data.ddel.innerHTML = "请将选择的数据拖到主办客户经理设置面板或者拖出移除该数据";
	    				},
	    				notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
	    					var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
	    					if(currentNode!=''){
	    						if(selectedRecord[0].json.INSTITUTION==currentNode.id){
		    						Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
			    					custMgrInfoGrid.store.add(selectedRecord);
			    					custMgrInfoGrid.store.sort('mgrId', 'ASC');
			    					return true;
		    					}
	    						else{
		    						Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
		    						Ext.Msg.alert('提示','该客户经理不属于此机构');
		    					}
	    					}else{
	    						Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
	    					}
	    				}			
	    				});
	    				var custMgrPanelDropTarget = new Ext.dd.DropTarget(custMgrPanelDropTargetEl, {
	    					ddGroup : 'gridDDGroup_2',
	    					notifyEnter : function(ddSource, e, data) {
//	    					data.ddel.innerHTML = "设置为<b>主办</b>客户经理";
	    					custMgrPanel.body.stopFx();
	    					custMgrPanel.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
	    				},	
	    					notifyOut : function(ddSource,e,data){
	    						data.ddel.innerHTML = "请将选择的数据拖到主办客户经理调整面板，或者拖出移除该条数据";

	    					},
	    				notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
	    					var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
	    					indexAllNodes(orgTreeForShow.root);
	    					if( selectedRecord[0].json.INSTITUTION==mainTypeNode.id ){
	    						custMgrPanel.getForm().findField('mgrName').setValue(selectedRecord[0].data.mgrName);
		    					custMgrPanel.getForm().findField('mgrId').setValue(selectedRecord[0].data.mgrId);
		    					custMgrPanel.getForm().findField('institution').setValue(selectedRecord[0].data.institution);
		    					custMgrPanel.getForm().findField('institution_name').setValue(selectedRecord[0].data.institution_name);
		    					selRecord_1=selectedRecord[0];
		    					Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
		    					return true;
	    					}else{
	    						Ext.Msg.alert('提示','请选择主办机构客户经理进行调整！');
	    					}
	    				}
	    				});
	    				
	    				var custMgrPanelDropTarget_1 = new Ext.dd.DropTarget(custMgrPanelDropTargetEl, {
	    					ddGroup : 'gridDDGroup_1',
	    					notifyEnter : function(ddSource, e, data) {
	    					data.ddel.innerHTML = "设置为<b>主办</b>客户经理";
	    					custMgrPanel.body.stopFx();
	    					custMgrPanel.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
	    				},		
    					notifyOut : function(ddSource,e,data){
    						data.ddel.innerHTML = "请将选择的数据拖到上方，设置主办客户经理或者拖出移除数据";

    					},
	    				notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
	    					var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
	    					if(currentNode.icon=='主办'){
		    					custMgrPanel.getForm().findField('mgrName').setValue(selectedRecord[0].data.mgrName);
		    					custMgrPanel.getForm().findField('mgrId').setValue(selectedRecord[0].data.mgrId);
		    					custMgrPanel.getForm().findField('institution').setValue(selectedRecord[0].data.institution);
		    					custMgrPanel.getForm().findField('institution_name').setValue(selectedRecord[0].data.institution_name);
		    					if(selRecord_1 != ""){
		    						custMgrInfoGrid.store.add(selRecord_1);
		    						custMgrInfoGrid.store.sort('mgrId', 'ASC');
		    					}
		    					Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
		    					selRecord_1 = selectedRecord;
		    					return true;
	    					}else{
	    						Ext.Msg.alert('提示','请选择主办机构客户经理进行调整！');
	    					}
	    				}
	    				});
	    			}
	    			dropTargetEl_1();
				}
	    },'-',{
	    text: '调整历史',
	    iconCls : 'detailIconCss',
	    handler : function(value){
	    	cusMgrHisWin.show();
	    	
	    	custBelongHisStore.load({
	    		params : {
	    		'custId':cust_id
			}}
		);
	    	
	    }
	}]});
	// 归属机构信息grid
	var belOrgGrid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">归属机构列表</span>',
		height :220,
		//width : document.body.scrollWidth-240,
		frame : true,
		region:'north',
		autoScroll : true,
		store : orgStore, 			// 数据存储
		stripeRows : true, 		// 斑马线
		bbar:bbar2,
		tbar : belCustmgrTbar,		// 归属调整工具栏
		cm : belOrgCm, 			// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//归属客户经理信息grid
	var belCustmgrGrid = new Ext.grid.GridPanel({
		title : '<span style="font-weight:normal">归属客户经理列表</span>',
		height :document.body.scrollHeight-250,
		//width : document.body.scrollWidth-240,
		frame : true,
		region:'center',
		autoScroll : true,
		store : custmgrStore2, 			// 数据存储
		stripeRows : true, 		// 斑马线
		cm : belCustmgrCm, 	
		bbar:bbar,
		// 列模型
		tbar : belCustmgrTbar,		// 归属调整工具栏
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//归属客户经理信息end**********************************************************
	//归属机构调整Start************************************************************
	// 归属机构列表的数据查询
	var orgAdjustStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_org_adjust!indexPage.json?cust_id=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name : 'oId'},{name : 'custId'},{name: 'institutionCode'},{name: 'institutionName'},{name : 'mainType'}])
	});
	

	// 定义自动当前页行号
	var orgAdjustRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//归属机构调整列表的模型
	var orgAdjustCm = new Ext.grid.ColumnModel([orgAdjustRownum,
	       {
			 	dataIndex : 'oId',
			 	hidden : true
		   },{
			 	dataIndex : 'custId',
			 	hidden : true
		   },{
			 	header : '所属机构号',
			 	dataIndex : 'institutionCode',
			 	sortable : true,
			 	width : 100
	       }, {
				dataIndex : 'institutionName',
				header : '所属机构名称',
				sortable : true,
				width : 200
			}, {
				dataIndex : 'mainType',
				header : '主协办类型',
				sortable : true,
				width : 80,
				editor: combo,
				renderer: Ext.util.Format.comboRenderer(combo)
			}
	]);
	
				// 定义自动当前页行号
	 var rownum = new Ext.grid.RowNumberer({
    	header : 'No.',
	    width : 28
	 });
	// 定义自动当前页行号
	 var mgrAdjustRownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//归属客户经理调整列表的模型
	 var mgrAdjustCm = new Ext.grid.ColumnModel([mgrAdjustRownum,
	       {
			 	dataIndex : 'cId',
			 	hidden : true
		   },{
			 	dataIndex : 'custId',
			 	hidden : true
		   }, {
			 	header : '客户经理编号',
			 	dataIndex : 'mgrId',
			 	sortable : true,
			 	width : 100
	       }, {
			 	header : '客户经理名称',
			 	dataIndex : 'mgrName',
			 	sortable : true,
			 	width : 150
	       }, {
			 	header : '所属机构号',
			 	dataIndex : 'institutionCode',
			 	sortable : true,
			 	width : 100,
			 	hidden : true
	       }, {
				dataIndex : 'institutionName',
				header : '所属机构名称',
				sortable : true,
				width : 200
			}, {
				dataIndex : 'mainType',
				header : '主协办类型',
				sortable : true,
				width : 80,
				editor: mgrCombo,
				renderer: Ext.util.Format.comboRenderer(mgrCombo)
	     		}
	 ]);
			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
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
				
			var number = parseInt(pagesize_combo.getValue());
//				pagesize_combo.on("select", function(comboBox) {
//				bbar.pageSize = parseInt(pagesize_combo.getValue()),
//				orgUserManageInfoStore.load({
//				params : {
//				start : 0,
//				limit : parseInt(pagesize_combo.getValue())
//				}
//				});
//				});
//				bbar = new Ext.PagingToolbar({
//				pageSize : number,
//				store : orgUserManageInfoStore,
//				displayInfo : true,
//				displayMsg : '显示{0}条到{1}条,共{2}条',
//				emptyMsg : "没有符合条件的记录",
//				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
//				});

				
//				}); 
	//归属客户经理调整Start************************************************************
	 var mgrCombo = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    forceSelection : true,
		    mode: 'local',
		    store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'mainType',
		            'displayText'
		        ],
		        data: [[3,'空'],[1, '主办'], [2, '协办']]
		    }),
		    valueField: 'mainType',
		    displayField: 'displayText',
		    hiddenName : 'mainType',
		    allowBlank : false
	  });
	// 归属客户经理列表的数据查询
	var mgrAdjustStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/cust_bel_custmgr_adjust!indexPage.json?cust_id=' + cust_id
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name : 'cId'},{name : 'custId'},{name:'mgrId'},{name:'mgrName'},{name: 'institutionCode'},{name: 'institutionName'},
		   {name : 'mainType'}])
	});

	//归属客户经理调整grid
	var mgrAdjustGrid = new Ext.grid.EditorGridPanel({
		tbar: ['->','主协办类型为空时，取消该分配'],
		autoScroll : true,
		clicksToEdit : 1,
		store : mgrAdjustStore, 	// 数据存储
		cm : mgrAdjustCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var rownum_ = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var cm_ = new Ext.grid.ColumnModel([rownum_,    //协办客户经理分配列表的模型
	                                     {header : 'ID',dataIndex : 'id',sortable : true,width : 175,hidden:true},
	                                     {header : '客户号',dataIndex : 'custId',sortable : true,width : 100,hidden:false},
	                                     {header : '客户分配类型',dataIndex : 'assignTypeOra',sortable : true,width : 120},
	                                    {header : '目标机构',dataIndex : 'institution',sortable : true,width : 120},
	                                     {header : '目标客户经理名称',dataIndex : 'mgrName',sortable : true,width : 120},
	                                     {header : '主协办类型',dataIndex : 'mainTypeOra',sortable : true,width : 120},
	                                     {header : '分配时间',dataIndex : 'assignData',sortable : true,width : 120},
	                                     {header : '分配人名称',dataIndex : 'assign_user',sortable : true,width : 100,hidden:false},
	                                     {header : '分配人所属机构名称',dataIndex : 'assignInstitution',sortable : true,width : 120}
	                                     ]);
	var record_ = Ext.data.Record.create([
	                                      {name: 'id', mapping: 'ID'},
	                                      {name: 'custId', mapping: 'CUST_ID'},                                    
	                                      {name: 'assignType', mapping: 'CUST_ASSIGN_TYPE'},
	                                      {name:'assignTypeOra',mapping:'CUST_ASSIGN_TYPE_ORA'},  
	                                      {name: 'assign_user', mapping: 'ASSIGN_USER'},                                   
	                                      {name: 'institution', mapping: 'INSTUTION_NAME'},  
	                                      {name:'mgrName',mapping:'MGR_NAME'},
	                                      {name:'mainType',mapping:'MAIN_TYPE'},
	                                      {name:'mainTypeOra',mapping:'MAIN_TYPE_ORA'},  
	                                      {name : 'assignData',mapping : 'ASSIGN_TIME'},
	                                      {name : 'assignInstitution',mapping : 'CURRENT_INSTITUTION_NAME'}
	                                      ]);
	var custBelongHisStore = new Ext.data.Store( {//协办客户经理列表store
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/custBelongHisAction.json?cust_id='+cust_id
		}),
		reader : new Ext.data.JsonReader( {
			successProperty: 'success',
			messageProperty: 'message',
			root : 'json.data',
			totalProperty: 'json.count'
		},record_)
	});
	custmgrStore.load();
	//归属客户经理调整历史grid
	var cusAdjustHisGrid = new Ext.grid.EditorGridPanel({
		autoScroll : true,
		//clicksToEdit : 1,
		store : custBelongHisStore, 	// 数据存储
		cm : cm_, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//归属客户经理调整历史Start************************************************************
	var cusMgrHisWin = new Ext.Window({
		plain : true,
		layout : 'fit',
		resizable : true,
		draggable : true,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 985,
		height : 400,
		buttonAlign : "center",
		title : '归属客户经理调整历史',
		items : cusAdjustHisGrid,
		buttons : [{
			text : '返回',
			handler : function(){
			cusMgrHisWin.hide();
		}
		}
		]
		
	});
	mgrCombo.addListener('select',function(){
		mgrAdjustGrid.stopEditing();
	});
	//归属客户经理调整end************************************************************
	
	// 布局模型
	var viewport = new Ext.Panel( {
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		layout : 'fit',
		autoScroll:true,
		items : [{
			layout:'border',
			items:[belOrgGrid,belCustmgrGrid]
		}]
	});

});