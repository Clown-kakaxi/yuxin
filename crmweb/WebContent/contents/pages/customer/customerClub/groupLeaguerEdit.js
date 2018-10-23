
/**
 * 客户群（拖动添加代码）
 * 2014-09-02 update
 */

var queryStr = '';
/**
 * 客户群成员新增
 * @return {Boolean}
 */
var batchAdd= function(){
	var checkedNodes = customerInfoGrid.getSelectionModel().selections.items;
	var json={'cust_id':[]};
	var json1={'cust_zh_name':[]};
	var json2={'mgr_id':[]};
	var json3={'mgr_name':[]};
	var json4={'institution':[]};
	var json5={'institution_name':[]};
	if(checkedNodes.length==0){
		Ext.Msg.alert('提示', '未选择任何客户');
		return false;
	}
	Ext.MessageBox.confirm('提示','确定将所选客户加入到该群吗?',function(buttonId){
		if(buttonId.toLowerCase() == "no"){
			return false;
		}else{
			for(var i=0;i<checkedNodes.length;i++){
				json.cust_id.push(checkedNodes[i].data.CUST_ID);
				json1.cust_zh_name.push(checkedNodes[i].data.CUST_NAME);
				json2.mgr_id.push(checkedNodes[i].data.MGR_ID);
				json3.mgr_name.push(checkedNodes[i].data.MGR_NAME);
				json4.institution.push(checkedNodes[i].data.INSTITUTION);
				json5.institution_name.push(checkedNodes[i].data.INSTITUTION_NAME);
			}
	
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
				url:basepath+'/groupmemberedit!saveData.json',
				method: 'POST',
				success : function(response) {
					Ext.Msg.alert('提示', '加入成功');
					groupLeaguerStore.reload({
						params : {
							start : 0,
							limit : parseInt(groupLeaguerpagesize_combo.getValue())
						}
					});
					customerInfoStore.reload({
						params : {
							start : 0,
							limit : parseInt(customerInfopagesize_combo.getValue())
						}
					});
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
					'CUST_ID': Ext.encode(json),
					'CUST_ZH_NAME': Ext.encode(json1),
					'CUST_BASE_ID':editGroupBaseInfoForm.form.findField('id').getValue(),
					'MGR_ID':Ext.encode(json2),
					'MGR_NAME':Ext.encode(json3),
					'INSTITUTION':Ext.encode(json4),
					'INSTITUTION_NAME':Ext.encode(json5)
				}
			});
		}
	});
};	

//客户群成员删除功能
var batchDelete=function(){
	var checkedNodes = groupLeaguerGrid.getSelectionModel().selections.items;
	if(checkedNodes.length==0){
		Ext.Msg.alert('提示', '未选择任何客户');
		return ;
	}
	var json={'id':[]};
	for(var i=0;i<checkedNodes.length;i++){
		json.id.push(checkedNodes[i].data.ID);
	}
	var id =checkedNodes[0].data.ID;
	Ext.Ajax.request({url: basepath+'/groupmemberedit!dropData.json',
		method: 'POST',
		success : function(response) {
			Ext.Msg.alert('提示', '删除成功');
			groupLeaguerStore.reload({
				params : {
					start : 0,
					limit : parseInt(groupLeaguerpagesize_combo.getValue())
				}
			});
		},
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} else {
				Ext.Msg.alert('提示','删除失败!');
			}
		},
		params : {
			'delStr':Ext.encode(json)
		}
	});
};

//新增客户群成员的表格面板 
var customerInfoTar = new Ext.Toolbar({
	items:[{
		text:'归入客户群',
		iconCls:'guiRuIconCss',
		handler:function(){
		  	batchAdd();
		}
	}]
});	
	
//客户类型
var customerTypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000080'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});	
var searchPanel1 = new Ext.form.FormPanel( {
//	title : "查询条件",
    labelWidth : 60,
    height:80,
    labelAlign : 'right',
    frame : true,
    region : 'north',
    layout : 'column',
    items : [ {
        columnWidth : .33,
        layout : 'form',
        items : [
        	{xtype : 'textfield',fieldLabel : '客户名称',name : 'CUST_NAME',anchor : '95%'}
        ]
    }, {
        columnWidth : .33,
        layout : 'form',
        items : [
        	{xtype : 'textfield',fieldLabel : '客户号',name : 'CUST_ID',anchor : '95%'}
        ]
    },{
        columnWidth : .33,
        layout : 'form',
        items : [new Ext.form.ComboBox({
			hiddenName : 'CUST_TYPE',
			name:'CUST_TYPE',
			fieldLabel : '客户类型',
			labelStyle: 'text-align:right;',
			triggerAction : 'all',
			store : customerTypeStore,
			displayField : 'value',
			valueField : 'key',
			mode : 'local',
			forceSelection : true,
			typeAhead : true,
			emptyText:'请选择',
			resizable : true,
			anchor : '95%'
		})]
    }],
    buttonAlign : 'center',
    buttons : [ {
        text : '查询',
        handler : function() {
	        queryStr = searchPanel1.getForm().getValues(false);
	        customerInfoStore.load( {
	            params : {
	                start : 0,
	                limit : parseInt(customerInfopagesize_combo.getValue())
	            }
	        });
        }
    }, {
        text : '重置',
        handler : function() {
            searchPanel1.getForm().reset();
        }
    }]
});

var custTypStore = new Ext.data.Store( {//客户类型代码
	restful : true,
	sortInfo : {
		field : 'key',
		direction : 'ASC'
	},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000080'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON',
		totalProperty : 'list'
	}, [ 'key', 'value' ])
});
	
//客户群成员分页，列模型等
var groupLeaguerSm = new Ext.grid.CheckboxSelectionModel();
var groupLeaguerrownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});

// 定义列模型
var groupLeaguerCm = new Ext.grid.ColumnModel([groupLeaguerrownum,groupLeaguerSm,
	{header : 'ID', dataIndex : 'ID',sortable : true,width : 150,hidden:true,hideable:false}, 
    {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 100}, 
    {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150 }, 
    {header : '证件类型',dataIndex : 'IDENT_TYPE',sortable : true,width : 150,hidden:true,hideable:false},
    {header : '证件类型',dataIndex : 'IDENT_TYPE_ORA',sortable : true,width : 150},
    {header : '证件号码',dataIndex : 'IDENT_NO',sortable : true,width : 150}, 
    {header : '客户类型',dataIndex : 'CUST_TYPE',sortable : true,width : 150,
        renderer : function(value){
        if(!value)
            return '';
        else if(custTypStore.query('key',value,false,true).first()==undefined)
            return '';
        else	
            return custTypStore.query('key',value,false,true).first().get('value');
        }
    }
]);

/**
 * 数据存储
 */
var groupLeaguerStore = new Ext.data.Store({
	restful:true,	
	proxy : new Ext.data.HttpProxy({
	url:basepath+'/groupmemberedit.json',
	failure : function(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		if(resultArray == 403) {
			Ext.Msg.alert('提示','您没有此权限!');
		} 
	}
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [ 'ID','CUST_ID','CUST_ZH_NAME','IDENT_TYPE','IDENT_TYPE_ORA','IDENT_NO','CUST_LEV','CUST_TYPE'])
});
		
groupLeaguerStore.on('beforeload', function() {
	this.baseParams = {
		querySign:'queryGroupMember',
		groupId: editGroupBaseInfoForm.form.findField('id').getValue()
	};
});
	
// 每页显示条数下拉选择框
var groupLeaguerpagesize_combo = new Ext.form.ComboBox({
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
var groupLeaguernumber = parseInt(groupLeaguerpagesize_combo.getValue());
groupLeaguerpagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
	groupLeaguerBar.pageSize = parseInt(groupLeaguerpagesize_combo.getValue());
	groupLeaguerStore.reload({
		params : {
		start : 0,
		limit : parseInt(groupLeaguerpagesize_combo.getValue())
	}
	});
});

var groupLeaguerBar = new Ext.PagingToolbar({// 分页工具栏
	pageSize : groupLeaguernumber,
	store : groupLeaguerStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', groupLeaguerpagesize_combo]
});
//end

//待加入成员列表
var customerInfoSm = new Ext.grid.CheckboxSelectionModel();
var customerInforownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});

// 定义列模型
var customerInfoCm = new Ext.grid.ColumnModel([customerInforownum,customerInfoSm,
    {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
    {header : '客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 150 }, 
    {header : '归属客户经理ID', dataIndex : 'MGR_ID',sortable : true,width : 150 ,hidden:true,hideable:false},
    {header : '归属客户经理', dataIndex : 'MGR_NAME',sortable : true,width : 150,hidden:true,hideable:false }, 
    {header : '归属机构ID', dataIndex : 'INSTITUTION',sortable : true,width : 150 ,hidden:true,hideable:false},       
    {header : '客户归属机构', dataIndex : 'INSTITUTION_NAME',sortable : true,width : 150 ,hidden:true,hideable:false},
    {header : '客户类型',dataIndex : 'CUST_TYPE',sortable : true,width : 150,
        renderer : function(value){
	        if(!value)
	            return '';
	        else if(custTypStore.query('key',value,false,true).first()==undefined)
	            return '';
	        else	
	            return custTypStore.query('key',value,false,true).first().get('value');
	    }
    }
]);

/**
 * 数据存储
 */
var customerInfoStore = new Ext.data.Store({
	restful:true,	
	proxy : new Ext.data.HttpProxy({
	url:basepath+'/groupmemberedit.json',
		failure : function(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		if(resultArray == 403) {
			Ext.Msg.alert('提示','您没有此权限!');
		} 
	}
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [ 'CUST_ID','CUST_NAME','IDENT_TYPE','IDENT_NO','CUST_LEV','CUST_TYPE','MGR_ID','MGR_NAME','INSTITUTION','INSTITUTION_NAME'])
});
customerInfoStore.on('beforeload', function() {
	if(''!=queryStr){
		__Str = Ext.encode(queryStr);	
	}else{
		__Str='{}';	
	}
	this.baseParams = {
		"condition":__Str,
		querySign:'queryCustomer',
		custType:editGroupBaseInfoForm.form.findField('groupMemberType').getValue(),
		groupId: editGroupBaseInfoForm.form.findField('id').getValue()
	};
});

// 每页显示条数下拉选择框
var customerInfopagesize_combo = new Ext.form.ComboBox({
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
var customerInfonumber = parseInt(customerInfopagesize_combo.getValue());
customerInfopagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
	customerInfoBar.pageSize = parseInt(customerInfopagesize_combo.getValue());
	customerInfoStore.reload({
		params : {
		start : 0,
		limit : parseInt(customerInfopagesize_combo.getValue())
	}
	});
});

var customerInfoBar = new Ext.PagingToolbar({// 分页工具栏
	pageSize : customerInfonumber,
	store : customerInfoStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', customerInfopagesize_combo]
});
//end

var groupLeaguerGrid = new Ext.grid.GridPanel({
	height: 280,
	title : '客户群成员列表',
	frame : true,
	autoScroll : true,
	store : groupLeaguerStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : groupLeaguerCm, // 列模型
	sm : groupLeaguerSm, // 复选框
	bbar : groupLeaguerBar,
	tbar:[{
		'text':'移除客户群',
		iconCls:'deleteIconCss',
		handler:function(){
		batchDelete();
		}
	}],
    viewConfig : {
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var customerInfoGrid = new Ext.grid.GridPanel({
	height: 220,
//	title : '待加入客户列表',
	frame : true,
	autoScroll : true,
	store : customerInfoStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : customerInfoCm, // 列模型
	sm : customerInfoSm, // 复选框
	bbar : customerInfoBar,
    tbar:customerInfoTar,
    viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
	
 //客户群维护窗口展示的from
var groupLeaguerPanel = new Ext.Panel( {
    layout : 'fit',
    id:'info2',
    activeItem : 0,     
    autoScroll : true,
    buttonAlign : "center",
    defaults : {                         //设置默认属性 
//		bodyStyle:'background-color:#FFFFFF;padding:15px' //设置面板体的背景色 
	}, 
    items : [{
	 	layout : 'column',
	 	border : false,
	 	items : [{
	 		columnWidth : .45,
	 		layout : 'form',
	 		border : false,
	 		items : [{
		       	region : 'north',
		        height : 80,
		        layout : 'fit',
		        items : [ searchPanel1 ]
     		},{
	         	region : 'center',
	         	layout : 'fit',
	         	height : 250,
	         	items : [ customerInfoGrid]
         	}]
     	},{
    	 	columnWidth : .55,
    	 	layout : 'form',
    	 	border : false,
    	 	items : [{
     			region : 'center',
     			layout : 'fit',
     			height : 330,
     			items : [ groupLeaguerGrid ]
     		}]
	    }]
	}]
});
	
	
	