

	

var custCharacSm = new Ext.grid.CheckboxSelectionModel();//特性项前面的复选框

var custCharactorColumns = new Ext.grid.ColumnModel(//产品特性项列数
		{
			columns:[
//			custCharacSm,
			{ header:'编号',dataIndex:'ID',width:50,sortable:true},
			{ header:'左括号',dataIndex:'bracketLeft',sortable:true,width:50,editor:new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '('], [2, '(('], [3, '(((']]
			    }),
			    valueField: 'displayText',
			    displayField: 'displayText'
				})},
			{ header:'客户特征项',dataIndex:'custCharac',sortable:true,editor:new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '年龄'], [2, '性别'], [3, '月收入'], [4, '月支出'], [5, '总资产'], [6, '净资产'], [7, '行业'], [8, '职位']]
			    }),
			    valueField: 'displayText',
			    displayField: 'displayText'
				})},
			{ header:'条件',dataIndex:'condition',sortable:true,editor:new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '>'], [2, '<'], [3, '='], [4, '>='], [5, '<='], [6, '<>']]
			    }),
			    valueField: 'displayText',
			    displayField: 'displayText'
				})},
			{ header:'客户特征值',dataIndex:'custCharacValue',sortable:true,editor:new Ext.form.TextField()},
			{ header:'右括号',dataIndex:'bracketRight',sortable:true,editor:new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, ')'], [2, '))'], [3, ')))']]
			    }),
			    valueField: 'displayText',
			    displayField: 'displayText'
				})},
			{ header:'连接符',dataIndex:'connectSymbol',sortable:true,editor:new Ext.form.ComboBox({
			    typeAhead: true,
			    triggerAction: 'all',
			    lazyRender:true,
			    mode: 'local',
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			            'myId',
			            'displayText'
			        ],
			        data: [[1, '并且'], [2, '或者']]
			    }),
			    valueField: 'displayText',
			    displayField: 'displayText'
				})}
			]
		}
	);

	var custCharactorRecord= Ext.data.Record.create(//特性项记录（record）
			[
			{name:'ID'},
			{name:'bracketLeft'},
			{name:'custCharac'},
			{name:'condition'},
			{name:'custCharacValue'},
			{name:'bracketRight'},
			{name:'connectSymbol'}
			]
		);
	var producPropertyData = {//特性项数据
		num:6,
		rows:[
		{"productName":"单位普通活期存款","productPropertyType":"费率","productPropertyName":"利率","productPropertyValue":"5%"},
		{"productName":"单位普通活期存款","productPropertyType":"费率","productPropertyName":"费率","productPropertyValue":"5%"},
		{"productName":"单位普通活期存款","productPropertyType":"期限","productPropertyName":"活期","productPropertyValue":"六个月"},
		{"productName":"单位普通活期存款","productPropertyType":"金额","productPropertyName":"发生额","productPropertyValue":"500,000,000"}
		]
		
	}; 
	
	var custCharactorData = {//特性项数据
			num:6,
			rows:[
			{"ID":"1","bracketLeft":"(","custCharac":"月收入","condition":">","custCharacValue":"1000", "bracketRight":")","connectSymbol":"并且"},
			{"ID":"2","bracketLeft":"", "custCharac":"月支出","condition":"<","custCharacValue":"100",  "bracketRight":"", "connectSymbol":"或者"},
			{"ID":"3","bracketLeft":"(","custCharac":"月收入","condition":">","custCharacValue":"10000","bracketRight":")","connectSymbol":"并且"},
			{"ID":"4","bracketLeft":"", "custCharac":"月支出","condition":"<","custCharacValue":"500",  "bracketRight":"", "connectSymbol":""}
			]
			
		}; 
	
	var targetCustData = {//特性项数据
			num:6,
			rows:[
			{"custId":"001", "custName":"赵蕊","custLevel":"A","hostDep":"世纪支行",    "hostCustMgr":"孙毅", "isBuyTheProd":"是"},
			{"custId":"002", "custName":"李欣","custLevel":"B","hostDep":"长湖支行",    "hostCustMgr":"吴鹏", "isBuyTheProd":"否"},
			{"custId":"003", "custName":"王刚","custLevel":"C","hostDep":"北三环支行",    "hostCustMgr":"李磊", "isBuyTheProd":"否"},
			{"custId":"004", "custName":"孙平","custLevel":"D","hostDep":"西三环支行",    "hostCustMgr":"张雷", "isBuyTheProd":"否"}
			]
			
		};
	
	
	var custCharactorReader = new Ext.data.JsonReader(//读取特性项数据的jsonReader
			{
				totalProperty:'num',
				root:'rows'
			},custCharactorRecord
		);
	
	var custCharactorStore = new Ext.data.Store({//特性项数据的store
		reader:custCharactorReader
	});
	
	
//	producPropertyStore.loadData(producPropertyData);//特性项store 加载特性项数据
	
	custCharactorStore.loadData(custCharactorData);//特性项store 加载特性项数据
	
//	targetCustStore.loadData(targetCustData);//特性项store 加载特性项数据
	
	 var newCulm = new Ext.data.Record({  
		 productName: '',  
		 productPropertyType: '',  
		 productPropertyName: '',  
		 productPropertyValue: ''
	 }); 
	

	var custCharactorGrid = new Ext.grid.EditorGridPanel({//可修改的gridTable
		
		title:'目标客户特征维护',
		store:custCharactorStore, 
		frame:true,
		cm:custCharactorColumns,
		region:'center',
		sm:custCharacSm,
      	stripeRows: true,
      	tbar:[ 
        {
			text:'新增',
			iconCls:'page_edit_1Icon',
			handler:function()
			{
        	custCharactorGrid.stopEditing();  
        		custCharactorStore.insert(0, newCulm);   
        		custCharactorGrid.startEditing(0, 0);  //激活该行的编辑状态
			}
		
	    },'-',
      	{
      		text:'保 存',
      		iconCls:'page_addIcon',
      		handler:function()
      		{
      			alert('操作成功');
      			custCharacor.hide();
      		}
      	},'-',
       	{
      		text:'删除',
      		iconCls:'page_edit_1Icon',
      		handler:function()
      		{
      			Ext.Msg.confirm('信息', '确定要删除', function(btn) {  
      			      if(btn == 'yes') {  
      			        var sm = custCharactorGrid.getSelectionModel(); //得到表格的选择模型  
      			        var cell = sm.getSelected(); //通过选择模型得到选择的单元格    
//      			        var record = producPropertyStore.getAt(cell);  //得到store对应的Record  
      			        	custCharactorStore.remove(cell);    
      			          debugger;
      			      }  
      			}); 
      		}
      	
      	}
      	],
 //      autoExpandColumn:'productName',
      		bbar:
			{	
				xtype:'paging',
				pageSize : 10,
				store : custCharactorStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', {xtype:'textfield',value:'10'} ]
			}	
	});
	//目标客户特征维护
	var custCharacor = new Ext.Window({
		closeAction:'hide',
		height:400,
		width:630,
		buttonAlign:'center',
		layout:'fit',
		buttons:[
		{
			text:'关闭',
			handler:function()
			{
			custCharacor.hide();
			}
		}
		],
		items:custCharactorGrid
	});

	
//	var producBusinessListView = new Ext.Viewport({//加载页面
//		layout:'border',//边界布局
//		items:[
////			producBusinessSearchPanel,
//			producPropertyGrid
//		]
//		
//		
//	});
	

