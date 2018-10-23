/*
 * 数据集管理
 * 2012-11-08
 * sena
 * */
/***
 * 解决不能保存多条的问题 auther:zm 20130114
 */
var changeDataList = new Array();
Ext.onReady(function(){
	var operate='0';
	var updateTyep='0';
	var rowId='';
 	var tabActivate=0;
 	var tbname='';
 	var tbchange=false;
	var boxstore = new Ext.data.SimpleStore({
		fields : ['value', 'key'],
		data : [['CRM_DB', '0000']]
			});
	var boxstore = new Ext.data.SimpleStore({
		fields : ['value', 'key'],
		data : [['数据库表', '1'],['标准SQL', '2']]
			});
	
	var lookupIdStore = new Ext.data.JsonStore({
		id : lookupIdStore,
		restful : true,
//		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup-mapping.json'
		}),
		fields : [ 'name','comment' ],
		reader : new Ext.data.JsonReader({
			totalProperty : 'list'
		}, [ {
			name : 'name',
			mapping : 'name'
			},{
				name : 'comment',
				mapping : 'comment'
				} ])
		});
	lookupIdStore.load({
		callback:function(){
		/*
		 * 创建构造函数，然后生成一条record记录
		 * */
		var newcreate = Ext.data.Record.create(['name', 'comment']);
		var recordadd = new newcreate({name:'',comment:'无'});
		lookupIdStore.insert(0,recordadd);
		
	}
	});
	var eTableStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/datasetmanagerquery!queryDataSetSolution.json',
				success : function(response) {}
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON.data'
		}, [ 'KEY', 'VALUE' ])
	});


	var comboxloader = new Com.yucheng.bcrm.ArrayTreeLoader( {
		// checked:true,
		// /**节点数组，可以改为从后台读取*/
		// nodeArray :nodeArra,
		/** 指向父节点的属性列 */
		parentAttr : 'PARENT_ID',
		/** 节点定位属性列，也是父属性所指向的列 */
		locateAttr : 'NODEID',
		/** 虚拟根节点id */
		rootValue : '0',
		/** 用于展示节点名称的属性列 */
		textField : 'NAME',
		/** 指定节点ID的属性列 */
		idProperties : 'NODEID',
		/** 节点点击事件句柄 */
		clickFn : function(node) {
		}
	});
	Ext.Ajax.request( {
		url : basepath + '/datasetmanagerquery.json',
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			comboxloader.nodeArray = nodeArra.JSON.data;
			nodeArrays=nodeArra.JSON.data;
		    var children=comboxloader.loadAll();
		    comboxtreeOfPoroduct.appendChild(children);
			comboxtreeOfPoroduct.expandAll();
		}
	});

	var comboxtreeOfPoroduct = new Com.yucheng.bcrm.TreePanel( {
		//title : '目录导航树',
		width : 280,
		height : 210,
		autoScroll : true,
		rootVisible : false,
		ddGroup : 'rightPanel',
		split : true,
		enableDrag:true,
		//checkBox : true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode( {
			id : 'root',
			expanded : true,
			text : '目录导航',
			autoScroll : true,
			children : []
		}),
		resloader : comboxloader,
		clickFn : function(node) {
		instnCombo.setValue(node.attributes.NAME);
		Ext.getCmp("PARENT_ID").setValue(node.attributes.ID);
		instnCombo.collapse();
		},
		listeners : {
			append:function(a,b,c,d){
			if(!c.leaf){
				return;
			}
		}
		}
	});
	var instnCombo = new Ext.form.ComboBox({
		xtype:'combo',					
		store : new Ext.data.SimpleStore( {
			fields : [],
			data : [ [] ]
		}),
		name:'PARENT',
		emptyText : '',
		resizable :true,
        labelStyle: 'text-align:right;',
		fieldLabel : '<span style="color:red">*</span>所属目录',
		anchor : '99%',
		editable:false,
		allowBlank : false,
		mode : 'local',
		triggerAction : 'all',
		maxHeight : 160,
		tpl:"<tpl for='.'><div style='height:160px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
				comboxtreeOfPoroduct.render('addOrgTreeDivForAdd');
			},
			'collapse':function(combo){
			}
		}
	});	
	
    var tabForm = new Ext.FormPanel({
        frame:true,
        id:'tabForm',
		width : document.body.scrollWidth ,
		buttonAlign : 'center',
		autoHeight : true,
        items: [{
           autoHeight:true,
            items :[  {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 150,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '<span style="color:red">*</span>数据集名称',
						name : 'name',
						//id:'sdfsdf',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						anchor : '99%'
					},{
						xtype : 'textfield',
						fieldLabel : 'ID',
						name : 'id',
						hidden:true,
						//allowBlank : false,
						labelStyle : 'text-align:right;',
						anchor : '99%'
					} ]
				   } ]
			},{
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 150,
					items : [ new Ext.form.ComboBox({
						hiddenName : 'type',
						fieldLabel : '<span style="color:red">*</span>类型',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						//name:'CUST_TYP',
						store : boxstore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						allowBlank : false,
						emptyText:'请选择',
						resizable : true,
						anchor : '99%'
					}) ]
				   } ]
			}
			
			
			,{
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 150,
					items : [ new Ext.form.ComboBox({
						name : 'value',
						id:'tbename',
						fieldLabel : '<span style="color:red">*</span>英文名称',
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						//name:'CUST_TYP',
						store : eTableStore,
						displayField : 'VALUE',
						valueField : 'KEY',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						allowBlank : false,
						resizable : true,
						anchor : '99%',
						listeners : {
									'expand' : function(combo) {
										tbname = combo.getRawValue();
									},
									'collapse' : function(combo) {
										if (tbname != combo.getRawValue()&&operate=='1') {
											Ext.Msg.confirm('是否重置','修改表,会清空以前保存的字段设置,是否确认修改?',
													function(btn,text) {
														if (btn == 'yes') {
															updateTyep='1';
															tbchange = true;
															tbname = combo.getRawValue();
														} else {
															combo.setRawValue(tbname);
														}
													});
														}
										else{
											tbchange = true;
											tbname = combo.getRawValue();
										}
															}
														}
													}) ]
										} ]
			},
			{
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .33,
					labelWidth : 150,
					items : [ instnCombo,
					 {  xtype : 'textfield',
						fieldLabel : '父节点id',
						name : 'parentId',
						id:'PARENT_ID',
						hidden:true,
						allowBlank : false,
						labelStyle : 'text-align:right;',
						anchor : '99%'
					}]
				   } ]
			},{
				layout : 'column',
				items : [ 	{
					layout:'form',
					columnWidth : .99,
					labelWidth : 150,
					items:{
					   
					    labelStyle : 'text-align:right;',
						name : 'notes',
						anchor:'80%',
						xtype:'textarea',
						height: 230,
						maxLength :360,
						fieldLabel : '描述'
					}
				} ]
			} ]}],
	buttons : [{
				text : '下一步',
				handler : function() {
		        if(tabForm.getForm().isValid()){
		          tabActivate=2;
		          if(tbchange){
		        	  if(operate=='1'){
		        		  Ext.getCmp('tabs2').setVisible(true);
				            tbchange=false;
		        	  }
		        	  if(operate=='0'){
		        	  Ext.getCmp('tabs2').setVisible(true);
			         // columnStore.load();
			          tbchange=false;
		        	  }
		          }
		          else{
		        	  if(operate=='1'){
		        		Ext.Msg.confirm('是否重置','是否清空以前保存的字段设置,以便重新选择新的字段?',
								function(btn,text) {
									if (btn == 'yes') {
										updateTyep='1';
										tbchange = true;
										tbname=Ext.getCmp('tbename').getRawValue();
										 Ext.getCmp('tabs2').setVisible(true);
										 
										//tbname = combo.getRawValue();
									} else {
										updateTyep='0';
										tbchange = true;
										 Ext.getCmp('tabs2').setVisible(true);
										 
										//combo.setRawValue(tbname);
									}
								});
		        	  }
		        	  else{
		        		  Ext.getCmp('tabs2').setVisible(true);
		        	  }
		          }
		       }
			   }},{
				text : '关闭',
				     handler : function() {
				         tabForm.getForm().reset();
				    	 addSolutionWindow.hide();
					}
				}]
		});
    var numberField = new Ext.form.NumberField({allowBlank : false,minValue:0.01,maxValue:100.00});  
    var textField = new Ext.form.TextField({allowBlank : false,minValue:0}); 
	var columnsm = new Ext.grid.CheckboxSelectionModel();

	 var columnrownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
			});    
    var cmodel = new Ext.grid.ColumnModel([
               columnrownum,columnsm, 
           	{header :'ID',dataIndex:'ID',width:130,sortable : true,hidden:true},
           	{header :'字段名',dataIndex:'NAME',width:130,sortable : true},
           	{header :'中文名称 ',dataIndex:'REMARKS',width:150,sortable : true,editor:textField},
           	{header :'字段类型',dataIndex:'COLTYPE',width:130,sortable:true},
           	{header :'长度',dataIndex:'LENGTH',width:130,sortable:true},
           	{header :'是否主键 ',dataIndex:'NULLS',width:100,sortable : true},
           	{header :'是否为空 ',dataIndex:'KEYSEQ',width:100,sortable : true},
        	{header :'排序 ',dataIndex:'COLNO',width:100,sortable : true,hidden:true},
        	{header :'备注 (数据字典类型)',dataIndex : 'NOTES',width:150,editor :{
        		xtype:'combo',
        		store : lookupIdStore,
        		mode : 'local',
        		triggerAction : 'all',
        		valueField : 'name',
        		displayField : 'comment',
//        		editable:false,
        		forceSelection:true,
				resizable:true,
				typeAhead : true,
				emptyText : '请选择',
        		listeners:{
        		select:function(){
        			var valuefind = this.value;
        			this.fireEvent('blur',this);
        		}
        	} },sortable : true,
        		renderer:function(val){
        		if(val!=''){
        			var stolength = lookupIdStore.data.items;
        			var i=0;
        			for(i=0;i< stolength.length;i++){
        				if(stolength[i].data.name==val){
        					return stolength[i].data.comment;
        				}
        			}
        		}
        	return val;	
        	}}
           	]);

 
 	var columnStore = new Ext.data.Store( {
				restful : true,
				proxy : new Ext.data.HttpProxy(
						{
							url : basepath + '/datasetmanagerquery!queryDataSetColumn.json'
						}),
				reader : new Ext.data.JsonReader( {
					root : 'JSON.data'
				}, [ {
					name : 'ID'
				}, {
					name : 'NAME'
				}, {
					name : 'REMARKS'
				}, {
					name : 'COLTYPE'
				}, {
					name : 'LENGTH'
				}, {
					name : 'NULLS'
				}, {
					name : 'KEYSEQ'
				}, {
					name : 'COLNO'
				}, {
					name : 'NOTES'
				}
				
				
				
				]
				)
			});
 	
	 	columnStore.on('beforeload', function() {
	          this.baseParams = {
	                  "tbname":tbname,
	                  "operate":operate,
	                  "rowId":rowId,
	                  "updateTyep":updateTyep
	          };
		});
	 	/***
	 	 * 解决不能保存多条的问题 auther:zm 20130114
	 	 */
	 	columnStore.on('update', function(store,records,operation) {
        	 if(changeDataList.length > 0){
        		 var count = 0;
        		 for ( var i = 0; i < changeDataList.length; i++) {// 遍历改变数据集，若不为重复记录，则添加至数据集
	        		 if(changeDataList[i].data.ID == records.data.ID)
		        	 {
	        			 count++;
		        	 }
        		 }
        		 if(count==0)
        			 changeDataList.push(records);
        	 }
        	 else
        	 {
        		 changeDataList.push(records);
        	 }
		});
		var columnGrid = new Ext.grid.EditorGridPanel( {
				frame : true,
				// region:"center",
				clicksToEdit : 1,
				id : 'assignInfoGrid',				
				height : 400,
				store : columnStore,
				loadMask : true,
				cm : cmodel,
				sm : columnsm,
				// tbar:[],
				buttonAlign : 'center',
				buttons : [ {
					text : '上一步',
					id : 'back',
					// disabled:true,
					handler : function() {
						Ext.getCmp('tabs1').setVisible(true);
					}
				}, {
					text : '保存',
					id : 'save',
					// disabled:true,
					handler : function() {
					if (operate == '0') {
							saveall();
						} else if (operate == '1') {
							if (updateTyep == '0') {
								updateall();
							} else if (updateTyep == '1') {
								updatenew();
							}
						}
					}
				}, {
					text : '关闭',
					id : 'close',
					// disabled:true,
					handler : function() {
						addSolutionWindow.hide();
					}
				} ]

			});	
	var tabmain = new Ext.TabPanel( {
		autoScroll : true,
		//region : 'center',
		id : 'tabmain',
		width:'100%',
        heignt:'100%',
		activeTab : 0,
		frame : true,
		defaults : {
			autoHeight : true
		},
		resizeTabs:true, 
		items : [ {
			title : '基本信息',
			id:'tabs1',
			items : [tabForm],
			listeners : {
				'activate' : function() {
					if(tabActivate==1){
						 Ext.getCmp('tabs2').setVisible(true);
					}
					else if(tabActivate==0){
						tabActivate=1;
					}
				
				}
			}
		}, {
			title : '字段设置',
			id:'tabs2',
			items : [columnGrid],
			listeners : {
				'activate' : function() {
					if(tabActivate==1){
						tabActivate=0;
					     Ext.getCmp('tabs1').setVisible(true);
					}
					else if(tabActivate==2){
						if(tabForm.getForm().isValid()){
						   if(tbchange){
						          columnStore.reload();
						          tbchange=false;
					          }
						}
						else{
						  Ext.getCmp('tabs1').setVisible(true);
						}
					}
				}
			}
		} ]
	});
	var addSolutionWindow = new Ext.Window(
			{
				height:500,
                width:800,
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				maximized:true,
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				//animateTarget : Ext.getBody(),
				constrain : true,
				items : [tabmain]
			});
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {
		// checked:true,
		// /**节点数组，可以改为从后台读取*/
		// nodeArray :nodeArra,
		/** 指向父节点的属性列 */
		parentAttr : 'PARENT_ID',
		/** 节点定位属性列，也是父属性所指向的列 */
		locateAttr : 'ID',
		/** 虚拟根节点id */
		rootValue : '0',
		/** 用于展示节点名称的属性列 */
		textField : 'NAME',
		/** 指定节点ID的属性列 */
		idProperties : 'ID',
		/** 节点点击事件句柄 */
		clickFn : function(node) {
		}
	});
	Ext.Ajax.request( {
		url : basepath + '/datasetmanagerquery.json',
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.JSON.data;
			nodeArrays=nodeArra.JSON.data;
		    var children=loader.loadAll();
			treeOfPoroduct.appendChild(children);
			treeOfPoroduct.expandAll();
		}
	});

    var dataSetType='';
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel( {
		title : '目录导航树',
		autoScroll : true,
		region : 'west',
		rootVisible : true,
		width:150,
		ddGroup : 'rightPanel',
		split : true,
		enableDrag:true,
		//checkBox : true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode( {
			id : 'root',
			expanded : true,
			text : '客户视图',
			autoScroll : true,
			children : []
		}),
		resloader : loader,
		clickFn : function(node) {
		dataSetType=node.attributes.ID;
		store.load({      
			  params : {
                   start : 0,
                   limit : bbar.pageSize
                  }}); 
		},
		listeners : {
			append:function(a,b,c,d){
			if(!c.leaf){
				return;
			}
		}
		}
	});
	var qForm = new Ext.form.FormPanel({
		id:'qForm',
		labelWidth : 130, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		height : 70,
		items : [{
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, // 标签宽度
						defaultType : 'textfield',
						border : false,
						items : [
			         {
						fieldLabel : '数据集名称',
						name : 'NAME',
						xtype : 'textfield', // 设置为数字输入框类型
						labelStyle: 'text-align:right;',
						anchor : '90%'
					}]
					}]
		}],
	buttons : [{
				text : '查询',
				handler : function() {
					store.load({      
						  params : {
                               start : 0,
                               limit : bbar.pageSize
                              }});     
			
			   }},{
				text : '重置',
				     handler : function() {
				    	 qForm.getForm().reset();
					}
				}]
	});
    //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
	  //复选框
	var sm2 = new Ext.grid.CheckboxSelectionModel({
		header : '供应商名称',  
		width : 200,  
		dataIndex : 'providerName',  
		align : 'center',  
		hidden : false  
	});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer( {
		header : 'No.',
		width : 28
	});
	// 定义列模型
	var cm = new Ext.grid.ColumnModel( [ rownum, sm,{
		header : 'id',
		dataIndex : 'id',
		hidden:true,
		sortable : true,
		width : 100
		}, {
			header : '数据集名称',
			dataIndex : 'name',
			sortable : true,
			width : 100
		}, {
			header : '类型',
			dataIndex : 'TYPE_NAME',
			sortable : true,
			width : 100
		}, {
			header : '类型',
			dataIndex : 'type',
			hidden:true,
			sortable : true,
			width : 100
		}, {
			header : '父类节点',
			dataIndex : 'parentId',
			hidden:true,
			sortable : true,
			width : 100
		}, {
			header : '表名',
			dataIndex : 'value',
			hidden:true,
			sortable : true,
			width : 100
		}, {
			header : '描述',
			dataIndex : 'notes',
			sortable : true,
			width : 100
		} 
		 ]);
	var store = new Ext.data.Store( {
		restful : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath+'/datasetsinglequery.json',
		   	success : function(response) {
			dataSetType='';
			//	Ext.Msg.alert('提示', response.responseText);
			}
		}),
	  reader: new Ext.data.JsonReader({
	        root:'json.data'
	        }, [
	            {name: 'id',mapping:'ID'},
	            {name: 'name',mapping:'NAME'},
	            {name: 'parentId',mapping:'PARENT_ID'},
	            {name: 'type',mapping:'TYPE'},
				{name: 'TYPE_NAME'},
				{name: 'value',mapping:'VALUE'},
				{name: 'notes',mapping:'NOTES'}
				
			])
	});
	  store.on('beforeload', function() {
      	var conditionStr =  qForm.getForm().getValues(false);
          this.baseParams = {
                  "condition":Ext.encode(conditionStr),
                  "dataSetType":dataSetType
          };
	});
    var pagesize_combo = new Ext.form.ComboBox({
        name : 'pagesize',
        triggerAction : 'all',
        mode : 'local',
        store : new Ext.data.ArrayStore({
            fields : ['value', 'text'],
            data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ],
					[ 500, '500条/页' ] ]
        }),
        valueField : 'value',
        displayField : 'text',
        value : '20',
        editable : false,
        width : 85
    });
   var number = parseInt(pagesize_combo.getValue());
   pagesize_combo.on("select", function(comboBox) {
   	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	var bbar = new Ext.PagingToolbar({
       pageSize : number,
       store : store,
       displayInfo : true,
       displayMsg : '显示{0}条到{1}条,共{2}条',
       emptyMsg : "没有符合条件的记录",
       items : ['-', '&nbsp;&nbsp;', pagesize_combo]
   });
	var tbar = new Ext.Toolbar( {

		items : [ {
			text : '新增',
			handler : function() {
			 Ext.getCmp('tabs1').setVisible(true);
			operate='0';
			var tabActivate=0;
	         
			addSolutionWindow.show();
			tabForm.getForm().getEl().dom.reset();

			}
		}, '-',{
			text : '修改',
			handler : function() {
			 Ext.getCmp('tabs1').setVisible(true);
			operate='1';
			var _record = grid.getSelectionModel().getSelected();
		 	var checkedNodes = grid.getSelectionModel().selections.items;
	        if (!_record||checkedNodes.length>1) {
	        	Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
	        } else {
			var record = grid.getSelectionModel().getSelected();
			tabForm.getForm().loadRecord(record);
			for(var f=0;f<nodeArrays.length;f++){
				if(nodeArrays[f].ID==record.data.parentId){
					instnCombo.setValue(nodeArrays[f].NAME);
					rowId=record.data.id;
			}
			//tbchange=true;
	        //  updatewin.show();
			addSolutionWindow.show();}
		/*	updateCondition=true;
			fnUpdateQuery();*/
			}
		 }},'-', {
			text : '删除',
			handler : function() {
			 deleteall();
			}
		} ]
	});
	// 表格实例
	var grid = new Ext.grid.GridPanel( {
		frame : true,
		region : 'center',
		autoScroll : true,
		enableDragDrop:false,
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,
		viewConfig : {
			forceFit : false,
			autoScroll : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	
	var saveall=function(){
		var checkedNodes = columnGrid.getSelectionModel().selections.items;
		if (checkedNodes.length == 0) 
		{
			Ext.Msg.alert('提示', '未选择任何客户');
			return;
		};
		var json1={'NAME':[]};
	 	var json2={'REMARKS':[]};
	 	var json3={'COLTYPE':[]};
	 	var json4={'LENGTH':[]};
	 	var json5={'NULLS':[]};
		var json6={'KEYSEQ':[]};
		var json7={'NOTES':[]};
		var json8={'COLNO':[]};
		for(var i=0;i<checkedNodes.length;i++)
		{
			json1.NAME.push(checkedNodes[i].data.NAME);
			json2.REMARKS.push(checkedNodes[i].data.REMARKS);
			json3.COLTYPE.push(checkedNodes[i].data.COLTYPE);
			json4.LENGTH.push(checkedNodes[i].data.LENGTH);
			json5.NULLS.push(checkedNodes[i].data.NULLS);
			json6.KEYSEQ.push(checkedNodes[i].data.KEYSEQ);
			json7.NOTES.push(checkedNodes[i].data.NOTES);
			json8.COLNO.push(checkedNodes[i].data.COLNO);
			
			
		}
		Ext.Ajax.request({
			url:basepath+'/dataset.json',
		    form:tabForm.getForm().id,
            method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '新增成功');
				addSolutionWindow.hide();
				store.load({      
					  params : {
                         start : 0,
                         limit : bbar.pageSize
                        }});     
		
			
			},
			failure : function(response) {
				  var resultArray = Ext.util.JSON.decode(response.status);
				   if(resultArray == 403) {
				      Ext.Msg.alert('提示','您没有此权限!');
				   } else {
					  Ext.Msg.alert('提示','加入失败!');
				   }
			},
			params : {
				'NAME':Ext.encode(json1),
				'REMARKS': Ext.encode(json2),
				'COLTYPE': Ext.encode(json3),
				'LENGTH': Ext.encode(json4),
				'NULLS': Ext.encode(json5),
				'KEYSEQ': Ext.encode(json6),
				'NOTES': Ext.encode(json7),
				'COLNO':Ext.encode(json8),
				'operate': 'add'
			}});
		
	};
	
	var updateall=function (){
//		var checkedNodes = columnGrid.getSelectionModel().selections.items;
		if (changeDataList.length == 0) 
		{
			Ext.Msg.alert('提示', '未作任何数据变动');
			return;
		}
	 	var json2={'REMARKS':[]};
		var json7={'NOTES':[]};
		var json9={'ID':[]};
		for(var i=0;i<changeDataList.length;i++)
		{
			json2.REMARKS.push(changeDataList[i].data.REMARKS);
			json7.NOTES.push(changeDataList[i].data.NOTES);
			json9.ID.push(changeDataList[i].data.ID);
		}
		changeDataList = new Array();
		Ext.Ajax.request({
			url:basepath+'/dataset.json',
		    form:tabForm.getForm().id,
            method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '修改成功');
				addSolutionWindow.hide();
				store.load({      
					  params : {
                         start : 0,
                         limit : bbar.pageSize
                        }});     
		
			
			},
			failure : function(response) {
				  var resultArray = Ext.util.JSON.decode(response.status);
				   if(resultArray == 403) {
				      Ext.Msg.alert('提示','您没有此权限!');
				   } else {
					  Ext.Msg.alert('提示','加入失败!');
				   }
			},
			params : {
				'REMARKS': Ext.encode(json2),
				'NOTES': Ext.encode(json7),
				'ID':Ext.encode(json9),
				'operate': 'update'
			}});
		
	};
	var updatenew=function (){
		var checkedNodes = columnGrid.getSelectionModel().selections.items;
		if (checkedNodes.length == 0) 
		{
			Ext.Msg.alert('提示', '未选择任何客户');
			return;
		};
		var json1={'NAME':[]};
	 	var json2={'REMARKS':[]};
	 	var json3={'COLTYPE':[]};
	 	var json4={'LENGTH':[]};
	 	var json5={'NULLS':[]};
		var json6={'KEYSEQ':[]};
		var json7={'NOTES':[]};
		var json8={'COLNO':[]};
		for(var i=0;i<checkedNodes.length;i++)
		{
			json1.NAME.push(checkedNodes[i].data.NAME);
			json2.REMARKS.push(checkedNodes[i].data.REMARKS);
			json3.COLTYPE.push(checkedNodes[i].data.COLTYPE);
			json4.LENGTH.push(checkedNodes[i].data.LENGTH);
			json5.NULLS.push(checkedNodes[i].data.NULLS);
			json6.KEYSEQ.push(checkedNodes[i].data.KEYSEQ);
			json7.NOTES.push(checkedNodes[i].data.NOTES);
			json8.COLNO.push(checkedNodes[i].data.COLNO);
			
			
		}
		Ext.Ajax.request({
			url:basepath+'/dataset.json',
		    form:tabForm.getForm().id,
            method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '修改成功');
				addSolutionWindow.hide();
				store.load({      
					  params : {
                         start : 0,
                         limit : bbar.pageSize
                        }});     
		
			
			},
			failure : function(response) {
				  var resultArray = Ext.util.JSON.decode(response.status);
				   if(resultArray == 403) {
				      Ext.Msg.alert('提示','您没有此权限!');
				   } else {
					  Ext.Msg.alert('提示','加入失败!');
				   }
			},
			params : {
				'NAME':Ext.encode(json1),
				'REMARKS': Ext.encode(json2),
				'COLTYPE': Ext.encode(json3),
				'LENGTH': Ext.encode(json4),
				'NULLS': Ext.encode(json5),
				'KEYSEQ': Ext.encode(json6),
				'NOTES': Ext.encode(json7),
				'COLNO':Ext.encode(json8),
				'operate': 'updatenew'
			}});
		
	};
	var deleteall=function (){
		var checkedNodes = grid.getSelectionModel().selections.items;
		if (checkedNodes.length == 0) 
		{
			Ext.Msg.alert('提示', '未选择任何客户');
			return;
		}
		var json9={'id':[]};
		for(var i=0;i<checkedNodes.length;i++)
		{
			json9.id.push(checkedNodes[i].data.id);
		}
		Ext.Ajax.request({
			url:basepath+'/dataset.json',
		    form:tabForm.getForm().id,
            method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '删除成功');
				addSolutionWindow.hide();
				store.load({      
					  params : {
                         start : 0,
                         limit : bbar.pageSize
                        }});     
		
			
			},
			failure : function(response) {
				  var resultArray = Ext.util.JSON.decode(response.status);
				   if(resultArray == 403) {
				      Ext.Msg.alert('提示','您没有此权限!');
				   } else {
					  Ext.Msg.alert('提示','删除失败失败!');
				   }
			},
			params : {
				'ID':Ext.encode(json9),
				'operate': 'delete'
			}});
		
		
	};
	var view = new Ext.Viewport( {
		layout : 'fit',
		items : [ {
	            layout : 'border',
	            items : [treeOfPoroduct ,{
                    region:'center',
                    layout:'border',
                    items:[qForm,grid ]               
            }]
	        } ]

	});
});