	  var batchAdd= function(){
		  var checkedNodes = custGroupGrid.getSelectionModel().selections.items;
		  var json=document.getElementById("json").Value;
		  var custGroupIds = '';
		  var tempCustGroupId;
		  if(checkedNodes.length==0)
		  {
			  Ext.Msg.alert('提示', '请先选择需要加入的客户群');
			  return false;
		  }
		  Ext.MessageBox.confirm('提示','确定将客户加入到所选群吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			}else{
			custGroupWindow.hide();
			for(var i=0;i<checkedNodes.length;i++)
		  {
			  tempCustGroupId = checkedNodes[i].data.ID;
			  custGroupIds += tempCustGroupId;
				if (i != checkedNodes.length - 1)
					custGroupIds += ',';
		  }
		
		  Ext.Msg.wait('正在保存，请稍后......','系统提示');
		  Ext.Ajax.request({
			  url:basepath+'/groupmemberedit!saveMember.json',
			  method: 'POST',
			  success : function(response) {
			  Ext.Msg.alert('提示', '加入成功');
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
			  'JSON': Ext.encode(json),
			  'CUST_BASE_IDS':custGroupIds
		  }});
			}});
		  
	  };	

var custGroupCombo = new Ext.form.ComboBox({
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

var custGroupNum = parseInt(custGroupCombo.getValue());
custGroupCombo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		custGroupBbar.pageSize = parseInt(custGroupCombo.getValue());
		custGroupStore.reload({
			params : {
			start : 0,
			limit : parseInt(custGroupCombo.getValue())
		}
		});
	});

var custGroupBbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : custGroupNum,
		store : custGroupStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', custGroupCombo]
	});

var custGroupStore = new Ext.data.Store({
	restful:true,	
	proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerbaseinfo.json'}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
	    {name: 'ID'},
	    {name: 'CUST_BASE_NUMBER'},
	    {name: 'CUST_BASE_NAME'},
	    {name: 'CUST_BASE_CREATE_DATE'},
	    {name: 'CUST_BASE_DESC'},
	    {name: 'USER_NAME'},
	    {name: 'CUST_BASE_CREATE_ORG'},
	    {name: 'CUST_STR'},
	    {name: 'CUST_STR1'}
	    ])
});

//新增客户群成员的表格面板 
	var custbar = new Ext.Toolbar({
		items:[{
		    text:'归入客户群',
		    iconCls:'guiRuIconCss',
		    handler:function(){
		        batchAdd();
		    }
		}]
	});

var cussmGroupMemberCheck = new Ext.grid.CheckboxSelectionModel();//复选框
	  
var cusGrouprownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
});			
				
var groupListModel = new Ext.grid.ColumnModel([
	                                                 cusGrouprownum,cussmGroupMemberCheck,
	                                                 {header : 'id',dataIndex : 'ID',sortable : true,width : 150,hidden:true},
	                                         	    {header : '客户群编号',dataIndex : 'CUST_BASE_NUMBER',sortable : true,width : 150},
	                                        	    {header : '客户群名称',dataIndex : 'CUST_BASE_NAME',width : 200,sortable : true},
	                                        	    {header : '客户群描述',dataIndex : 'CUST_BASE_DESC',width : 150,sortable : true},
	                                        	    {header : '创建时间',dataIndex : 'CUST_BASE_CREATE_DATE',width : 80,sortable : true},
	                                        	    {header : '创建人',dataIndex : 'USER_NAME',width : 80,sortable : true},
	                                        	    {header : '创建机构',dataIndex : 'CUST_BASE_CREATE_ORG',width : 80,sortable : true,hidden:true},
	                                        	    {header : '群成员编号',dataIndex : 'CUST_STR',width : 80,sortable : true,hidden:true},
	                                        	    {header : '群成员名称',dataIndex : 'CUST_STR1',width : 80,sortable : true,hidden:true}
	                                                 ]);
	
var custGroupGrid = new Ext.grid.GridPanel({
	title : "尚未加入群列表",
	height : 275,
	width:1000,
	region:'center',
	frame : true,
	autoScroll : true,
	store : custGroupStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : groupListModel, // 列模型
	sm : cussmGroupMemberCheck, // 复选框
	bbar:custGroupBbar,
	viewConfig:{
		forceFit:false,
		autoScroll:true
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var custGroupQuery=new Ext.FormPanel({
	    title:'客户群查询',
	    frame:true,
		border:false,
		labelAlign:'right',
		layout:'column',
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .5,
				layout : 'form',
				labelWidth : 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '客户群编号',
					name : 'CUST_BASE_NUMBER',
					xtype : 'textfield', // 设置为数字输入框类型
					labelStyle: 'text-align:right;',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				labelWidth: 100, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [{
					fieldLabel : '客户群名称',
					name : 'CUST_BASE_NAME',
					xtype : 'textfield', // 设置为数字输入框类型
					labelStyle: 'text-align:right;',
					anchor : '90%'
				}]
			}]
		}],
		buttonAlign:'center',
		buttons:[{
		    text:'查询',
		    width:80,
		    handler:function(){
		        var conditionStr =  custGroupQuery.getForm().getValues(false);
		        custGroupStore.baseParams = {
					"condition":Ext.encode(conditionStr)
			    };
		        custGroupStore.load({ 
			        params : {
						start : 0,
						limit : custGroupBbar.pageSize,
						custIds : document.getElementById("custs_id").Value
					}
			    });
		    }
		},{
		    text:'重置',
		    handler : function() {
			custGroupQuery.getForm().reset();
	        }
		}]
});

var custGroupWindow=new Ext.Window(
				{
					layout : 'fit',
					width:800,
					height :420,
					closable : true,
					resizable : false,
					collapsible : false,
					draggable : true,
					closeAction : 'hide',
					title : '加入客户群',
					buttonAlign:'center',
					modal : true, 
					animCollapse : false,
					border : false,
					closable : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [
						         {
						        	 layout : 'form',
						        	 border : false,
						        	 items : [{
						                 region : 'north',
						                 id : 'north-panel',
						                 height : 120,
						                 layout : 'fit',
						                 items : [ custGroupQuery ]
						             },{
						                 region : 'center',
						                 id : 'center-panel',
						                 layout : 'fit',
						                 height : 300,
						                 items : [ custGroupGrid ]
						             }]
						         }
						         ],
					buttonAlign:'center',
					buttons:[{
						text: '加入',
						handler:function(){
						batchAdd();
					}
					},{
						text: '关闭',
						handler:function(){
						custGroupWindow.hide();
					}
					}]	
				});