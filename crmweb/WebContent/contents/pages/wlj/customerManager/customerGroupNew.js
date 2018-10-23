/**
 * 客户群管理
 * hujun
 * 2014-7-17
 * helin,20140828,JIRA FSBCRMECIF-82 bug fixed 
 */

imports([
     '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
     '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
     '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
     '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',
     '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
     '/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js',
     '/contents/pages/common/Com.yucheng.bcrm.common.MarketTarget.js',	//指标选择放大镜,
     '/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',//营销活动放大镜
     '/contents/pages/common/Com.yucheng.bcrm.common.MarketTask.js'	,
     '/contents/pages/common/Com.yucheng.bcrm.common.MktTaskTarget.js',//营销任务指标明细放大镜
     '/contents/pages/common/Com.yucheng.bcrm.common.CreateMktOppor.js',
     '/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js',  //客户群放大镜
     '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
     ,'/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js' //所有数据字典定义
             
 ]);
var createView=true;
var eidtView=false;
var detailView=false;
var needReset = false  ;//用于判断新增时是否需要重置基本信息页面    （因为上一步时不需要）
var ifprve = false;
var custBaseid='';//客户群新建时返回的ID
var menberType = '';
var url=basepath+'/customergroupinfo.json';
var lookupTypes=[
     'CUSTOMER_GROUP_TYPE',
     'GROUP_MEMEBER_TYPE',
     'SHARE_FLAG',
     'XD000040',
     'CUSTOMER_SOURCE_TYPE',
     'FXQ_RISK_LEVEL',
     'XD000025'
 ];
 //证件类型
 var certTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
 
 //客户群分类
 var customergroupTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=CUSTOMER_GROUP_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//客户来源
var customerSourceTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=CUSTOMER_SOURCE_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//群成员类型
var groupMemeberTypeStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=GROUP_MEMEBER_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
   //客户群共享范围
var shareFlagStore = new Ext.data.Store({
	restful : true,
	sortInfo : {field : 'key',direction : 'ASC'},
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=SHARE_FLAG'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var fields=[
    {name:'ID',text:'客户群ID',hidden:true},
    {name:'CUST_BASE_NUMBER',text:'客户群编号'},
    {name:'CUST_BASE_NAME',text:'客户群名称',searchField:true},
    //{name:'GROUP_TYPE',text:'客户群分类',translateType:'CUSTOMER_GROUP_TYPE',searchField:true},
    {name:'CUST_FROM',text:'客户来源',id:'custFrom',translateType:'CUSTOMER_SOURCE_TYPE',searchField:true},
    {name:'GROUP_MEMBER_TYPE',text:'群成员类型',translateType:'GROUP_MEMEBER_TYPE',searchField:true},
    {name:'SHARE_FLAG',text:'共享范围',translateType:'SHARE_FLAG',searchField:true},
    {name:'MEMBERSNUM',text:'成员数'},
    {name:'CREATENAME',text:'创建人'},
    {name:'CUST_BASE_CREATE_DATE',text:'创建日期',format:'Y-m-d'},
    {name:'CUST_BASE_CREATE_ORG',text:'创建机构ID',hidden:true},
    {name:'CUST_BASE_CREATE_ORG_NAME',text:'创建机构'},
    {name:'CUST_BASE_CREATE_NAME',text:'客户群创建人',hidden:true},
    {name:'RECENT_UPDATE_USER',text:'最近更新人',gridField:false},
    {name:'RECENT_UPDATE_ORG',text:'最近更新机构',gridField:false},
    {name:'RECENT_UPDATE_DATE',text:'最近更新日期',format:'Y-m-d'},
    {name:'CUST_BASE_DESC',text:'客户群描述',hidden:true} ,
    {name:'CUST_ID',text:'客户号',searchField:true,gridField:false},
    {name:'CUST_NAME',text:'客户名称',searchField:true,gridField:false} ,
    {name:'CERT_TYPE',text:'证件类型',searchField:true,hidenName:'CERT_TYPE',translateType:'XD000040',gridField:false} ,
    {name:'CERT_NUM',text:'证件号码',gridField:false} ,
    {name:'CUST_BASE_CREATE_DATE_S',gridField:false,text:'创建开始日期',xtype:'datefield',format:'Y-m-d',searchField:true} ,
    {name:'CUST_BASE_CREATE_DATE_E',text:'创建开结束日期',gridField:false,xtype:'datefield',format:'Y-m-d',searchField:true},
    {name:'MAIN_USER_NAME',text:'客户经理',hiddenName:'custMgrId',searchField:true,xtype:'userchoose',gridField:false} ,
    {name:'MIAN_ORG_NAME',text:'创建机构',hiddenName:'CUST_ORG_ID',xtype:'orgchoose',searchType:'SUBTREE',searchField:true,gridField:false} 
];

//批量调整反洗钱等级方案
var fnBatchUpdate= function(){
	Ext.ScriptLoader.loadScript({
		scripts: [
//			basepath+'/contents/pages/customer/customerClub/groupCustMgrEdit.js',
//			basepath+'/contents/pages/customer/customerClub/groupLeaguerEdit.js',
//			basepath+'/contents/pages/customer/customerClub/agileQuery.js',
//			basepath+'/contents/pages/customer/customerClub/customerGroupEdit.js',
			//basepath+'/contents/pages/customer/custSearchByDetailType/searchCustForGroup.js'
			basepath+'/contents/pages/wlj/custmanager/antiMoney/fxqBatchUpdate.js'
		],        
		finalCallback: function() {
			debugger;
			var selectLength = antMoneyTargetGrid.getSelectionModel().getSelections().length;
			if (selectLength < 1) {
				Ext.Msg.alert('提示','请至少选择一个客户!');
			} else {
				var selectRe;
				var tempId;
				var custType,m,n;
				idStr = '';//需要先清空
				for ( var i = 0; i < selectLength; i++) {
					tempId =antMoneyTargetGrid.getSelectionModel().getSelections()[i].data.CUST_ID;
					custType = antMoneyTargetGrid.getSelectionModel().getSelections()[i].data.CUST_NAME;
					if(custType == '2'){
						m=1;
					}else if(custType == '1'){
						n=1;
					}
					idStr += tempId;
					if (i != selectLength - 1)
						idStr += ',';
				}
				groupMemberType = custType;
				if(m==1 && n==1)
					groupMemberType = '3';
				choseWin.show();
				fxqForm.form.findField('custGroup').setVisible(false);
				fxqForm.getForm().reset();
			}
		}
	});
	
	
	
	//Ext.Msg.alert('提示', '操作成功!');

	/*//批量删除的参数 作为参考
	 * var selectLength = grid.getSelectionModel().getSelections().length;
 	var checkedNodes = grid.getSelectionModel().selections.items;
		if(checkedNodes.length==0){
			Ext.Msg.alert('提示', '未选择任何客户');
			return ;
		}
		var json='';
		for(var i=0;i<checkedNodes.length;i++)
		{
			if(i==0){
				json = checkedNodes[i].data.ID;
			}else {
				json += ',' + checkedNodes[i].data.ID;
			}
		}
		Ext.Ajax.request({
			url:basepath+'/agilesearch.json',
            method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '操作成功!');
				store.reload();
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
				'solutionID': json
			}
		});*/
};

var tbar=[{
	text:'客户群视图',
	handler:function(){
		debugger;
		 if(!getSelectedData()){
			  Ext.Msg.alert('提示', '请选择一条数据');
			  return ;
		  }
		 var id=getSelectedData().data.ID;
		 var name= getSelectedData().data.CUST_BASE_NAME;
		 parent.Wlj.ViewMgr.openViewWindow(1,id,name);
    }
},{
    text:'新增客户群',
    handler:function(){
    	needReset = true;
    	showCustomerViewByTitle('基本信息');
    }
},{
    text:'删除客户群',
    handler:function(){
		var records =getAllSelects();// 得到被选择的行的数组;
		var json={'id':[]};
		var selectRe;
		if(records.length < 1){
			Ext.Msg.alert('提示','请选择需要删除的记录!');
		}else {
			for(var i = 0; i<records.length;i++){
				selectRe = records[i];
				//增加判断：如果是“黑名单(特殊名单)客户群”，则不能删除（根据客户客户群编号“C0228276”进行判断）
				var cBaseNum = selectRe.data.CUST_BASE_NUMBER;
				if(cBaseNum == "C0228276"){
					Ext.Msg.alert("提示","[黑名单(特殊名单)客户群]不能删除!");
					return false;
				}
				if(selectRe.data.CUST_BASE_CREATE_NAME!=__userId){
					Ext.Msg.alert("提示","不是自己创建的不能删除!");
					return false;
				}
				 json.id.push(selectRe.data.ID);
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				} 
				Ext.Ajax.request({
					url : basepath+ '/customergroupinfo.json',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					params : {
						cbid:Ext.encode(json),
						'operate':'delete'
					},
					success : function() {
						Ext.Msg.alert('提示', '操作成功');
						reloadCurrentData();
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
					           Ext.Msg.alert('提示', response.responseText);
						}else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							reloadCurrentData();
						}
					}
				});
			});
		}
	}
},{
	text:'创建营销活动',
	handler:function(){
		 if(!getSelectedData()){
			  Ext.Msg.alert('提示', '请选择一条数据');
			  return ;
		 }
		//增加判断：如果是“黑名单(特殊名单)客户群”，则不能创建营销活动（根据客户客户群编号“C0228276”进行判断）
		var cBaseNum = getSelectedData().data.CUST_BASE_NUMBER;
		if(cBaseNum == "C0228276"){
			Ext.Msg.alert("提示","[黑名单(特殊名单)客户群]不能创建营销活动!");
			return false;
		}
		 var id=getSelectedData().data.ID;
		 Ext.ScriptLoader.loadScript({
			scripts: [
				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',
				basepath+'/contents/pages/wlj/mktManage/mktActivityManager/mktAddFunction.js',
				basepath+'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
			],
		    finalCallback: function() {
		    	getActiveAddWindowShow('','',id,true,false,false,false,true,true);
		    }
		});
	}
},{
	text:'生成商机',
	handler:function(){
		if(getSelectedData() == false){
    		Ext.Msg.alert('提示','请选择数据!');
    		return false;
		}else{ 
			//增加判断：如果是“黑名单(特殊名单)客户群”，则不能生成商机（根据客户客户群编号“C0228276”进行判断）
			var cBaseNum = getSelectedData().data.CUST_BASE_NUMBER;
			if(cBaseNum == "C0228276"){
				Ext.Msg.alert("提示","[黑名单(特殊名单)客户群]不能生成商机!");
				return false;
			}
			var ID=getSelectedData().data.ID;
			var CUST_BASE_NAME=getSelectedData().data.CUST_BASE_NAME;
    		var createMktOppor = new Com.yucheng.bcrm.common.CreateMktOppor ({
    		isgroup:true,
    		groupId:ID,
    		groupName:CUST_BASE_NAME});
			createMktOppor.show();
        }
	}
},{
	text:'模板下载',
	handler:function(){
		//下载导入黑名单客户的Excel模板
		var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
		winPara += ' scrollbars=no, resizable=no,location=no, status=no';
		var fileName = 'blacklistCustImpTept.xlsx';// 模板文件名称
		var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
		window.open(uploadUrl, '', winPara);
	}
},{
	text:'批量导入',
	handler:function(){
//		var secData = getSelectedData();
//		//判断是否选择了数据
//		if(!secData){
//    		Ext.Msg.alert('提示','请选择[黑名单(特殊名单)客户群]！');
//    		return false;
//		}
//		//判断：如果不是“黑名单(特殊名单)客户群”，则不能执行批量导入（根据客户客户群编号“C0228276”进行判断）
//		var cBaseNum = getSelectedData().data.CUST_BASE_NUMBER;
//		if(cBaseNum != "C0228276"){
//			Ext.Msg.alert("提示","请选择[黑名单(特殊名单)客户群]执行导入！");
//			return false;
//		}
		//直接执行黑名单批量导入（根据客户客户群编号“C0228276”进行导入）
		importForm_bl.tradecode = 'importBlacklistCustInfo';
		importWindow_bl.show();
	}
},
	//导出黑名单客户数据（根据客户客户群编号“C0228276”进行导出）
	new Com.yucheng.crm.common.NewExpButton({
        url :basepath+'/blacklistCustExpAction.json?custBaseNumber=C0228276'
    })
,{
	text:'反洗钱指标信息',
	handler:function(){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			showCustomerViewByTitle('反洗钱指标信息');
		}
	}
}
];





/*****************手动筛选面板********************/
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
var searchPanel1 = new Ext.form.FormPanel({
	labelWidth : 80,
	height:90,
	labelAlign : 'right',
	frame : true,
	region : 'north',
	autoScroll : true,
	layout : 'column',
	items : [ {
		columnWidth : .32,
        layout : 'form',
        items : [
			{xtype : 'textfield',fieldLabel : '客户名称',name : 'CUST_ZH_NAME',anchor : '95%'}
		]
    }, {
        columnWidth : .32,
        layout : 'form',
        items : [
        	{ xtype : 'textfield',fieldLabel : '客户编号',name : 'CUST_ID',anchor : '95%'}
        ]
    },{
        columnWidth : .32,
        layout : 'form',
        items : [
        	new Ext.form.ComboBox({
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
			})
		]
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
	width : 28
});

// 定义列模型
var groupLeaguerCm = new Ext.grid.ColumnModel([groupLeaguerrownum,groupLeaguerSm,
	{header : 'ID', dataIndex : 'ID',sortable : true,width : 150,hidden:true,hideable:false}, 
    {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
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
	}, [ 'ID','CUST_ID','CUST_ZH_NAME','IDENT_TYPE','IDENT_TYPE_ORA','IDENT_NO','CUST_TYPE'])
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
groupLeaguerStore.on('beforeload', function() {
	this.baseParams = {
		querySign:'queryGroupMember',
		groupId: custBaseid
		
  	};
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
	width : 28
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
	}, [ 'CUST_ID','CUST_NAME','IDENT_TYPE','IDENT_NO','CUST_TYPE','MGR_ID','MGR_NAME','INSTITUTION','INSTITUTION_NAME'])
});
    customerInfoStore.on('beforeload', function() {
    	if(''!=queryStr){
    	__Str = Ext.encode(queryStr);	
    	}else{
    	__Str='';	
    	}
	this.baseParams = {
			"condition":__Str,
			querySign:'queryCustomer',
			custType:Ext.getCmp('groupMemberType').getValue(),
			groupId: custBaseid
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

var queryStr = '';
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
				  'CUST_BASE_ID':custBaseid,
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
	height: 430,
	title : '客户群成员列表',
	frame : true,
	autoScroll : true,
	store : groupLeaguerStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : groupLeaguerCm, // 列模型
	sm : groupLeaguerSm, // 复选框
	bbar : groupLeaguerBar,
	tbar:[{'text':'移除客户群',handler:function(){
		  batchDelete();
	  }}				 
	  ],
    viewConfig : {
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var customerInfoGrid = new Ext.grid.GridPanel({
	height: 350,
	frame : true,
	autoScroll : true,
	store : customerInfoStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : customerInfoCm, // 列模型
	sm : customerInfoSm, // 复选框
	bbar : customerInfoBar,
    tbar:customerInfoTar,
    viewConfig : {
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/******************************************/
var ifadd = 'true';
var __hiddeAble=false;
var __modelSign1=.45;
var __modelSign2=.55;

var _tempImpFileName = "";
var pkHead = "";
/**
 * 导入表单对象，此对象为全局对象，页面直接调用。
 */
var importForm = new Ext.FormPanel({ 
	id:'info5',
    height : 200,
    width : '100%',
    title:'文件导入',
    fileUpload : true, 
    dataName:'file',
    frame:true,
    tradecode:"importantGroupCust",
    
    /**是否显示导入状态*/
    importStateInfo : '',
    importStateMsg : function (state){
		var titleArray = ['excel数据转化为SQL脚本','执行SQL脚本...','正在将临时表数据导入到业务表中...','导入成功！'];
		this.importStateInfo = "当前状态为：[<font color='red'>"+titleArray[state]+"</font>];<br>";
	},    
    /**是否显示 当前excel数据转化为SQL脚本成功记录数*/
    curRecordNumInfo : '',
    curRecordNumMsg : function(o){
		this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 当前sheet页签记录数*/
	curSheetRecordNumInfo : '',
    curSheetRecordNumMsg : function (o) {
		this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 当前sheet页签号*/
    sheetNumInfo : '',
    sheetNumMsg : function(o){
		this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 sheet页签总数*/
    totalSheetNumInfo : '',
    totalSheetNumMsg : function(o){
		this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 已导入完成sheet数*/
    finishSheetNumInfo : '',
    finishSheetNumMsg : function(o){
		this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>"+o+"</font>];<br>";
	},
    /**是否显示 已导入完成记录数*/
	finishRecordNumInfo : '',
    finishRecordNumMsg : function(o){
		this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>"+o+"</font>];<br>";
	},
    /**当前excel数据转化为SQL脚本成功记录数*/
    curRecordNum : 0,
    /**导入总数*/
	totalNum : 1,
	/**进度条信息*/
	progressBarText : '',
	/**进度条Msg*/
	progressBartitle : '',
    proxyStore : undefined,
    items: [new Ext.form.TextField({
        xtype : 'textfield',
        id:'littleim',
        name:'annexeName',
        inputType:'file',
        fieldLabel : '文件名称',
        anchor : '90%'
    })],
	/**进度条*/
    progressBar : null,    
    /***import成功句柄*/
    importSuccessHandler : function (json){	
		if (json != null) {	
			if (typeof(json.curRecordNum) != 'undefined' && this.curRecordNumMsg) {
				this.curRecordNumMsg(json.curRecordNum);
				this.curRecordNum = json.curRecordNum;
			}
			if (typeof(json.importState) != 'undefined' && this.importStateMsg) {
				this.importStateMsg(json.importState);
			}				
			if (typeof(json.curSheetRecordNum) != 'undefined' && this.curSheetRecordNumMsg) {
				this.curSheetRecordNumMsg(json.curSheetRecordNum);
			}
			if (typeof(json.sheetNum) != 'undefined' && this.sheetNumMsg) {
				this.sheetNumMsg(json.sheetNum);
			}
			if (typeof(json.totalSheetNum) != 'undefined' && this.totalSheetNumMsg) {
				this.totalSheetNumMsg(json.totalSheetNum);
			}	
			if (typeof(json.finishSheetNum) != 'undefined' && this.finishSheetNumMsg) {
				this.finishSheetNumMsg(json.finishSheetNum);
			}
			if (typeof(json.finishRecordNum) != 'undefined' && this.finishRecordNumMsg) {
				this.finishRecordNumMsg(json.finishRecordNum);
			}
		} else {
			this.curRecordNumInfo = '';
			this.importStateInfo = '';
			this.curSheetRecordNumInfo = '';
			this.sheetNumInfo = '';
			this.totalSheetNumInfo = '';
			this.finishSheetNumInfo = '';
			this.finishRecordNumInfo = '';
		}
		
		this.progressBartitle = '';
		/**进度条Msg信息配置：各信息项目显示内容由各自方法配置*/
		this.progressBartitle += this.curRecordNumMsg      ?this.curRecordNumInfo:'';
		this.progressBartitle += this.importStateMsg 	   ?this.importStateInfo:'';
		this.progressBartitle += this.curSheetRecordNumMsg ?this.curSheetRecordNumInfo:'';
		this.progressBartitle += this.sheetNumMsg 	   	   ?this.sheetNumInfo:'';
		this.progressBartitle += this.totalSheetNumMsg 	   ?this.totalSheetNumInfo:'';
		this.progressBartitle += this.finishSheetNumMsg    ?this.finishSheetNumInfo:'';
		this.progressBartitle += this.finishRecordNumMsg   ?this.finishRecordNumInfo:'';
		
		showProgressBar(this.totalNum,this.curRecordNum,this.progressBarText,this.progressBartitle,"上传成功，正在导入数据，请稍候");
	},
    buttons : [{
            text : '导入文件',
            handler : function() {
                var tradecode = this.ownerCt.ownerCt.tradecode;
                var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
                var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
                if(tradecode==undefined ||tradecode==''){
                    Ext.MessageBox.alert('Debugging！','You forgot to define the tradecode for the import form!');
                    return false;
                }
                var impRefreshHandler = 0;
                if (this.ownerCt.ownerCt.getForm().isValid()){
//		                    this.ownerCt.ownerCt.ownerCt.hide();
                    var fileNamesFull = this.ownerCt.ownerCt.items.items[0].getValue();
                    var extPoit = fileNamesFull.substring(fileNamesFull.indexOf('.'));
                    if(extPoit=='.xls'||extPoit=='.XLS'||extPoit=='.xlsx'||extPoit=='.XLSX'){
                    } else {
                    	Ext.MessageBox.alert("文件错误","导入文件不是XLS或XLSX文件。");
                        return false;
                    }
                    showProgressBar(1,0,'','','正在上传文件...');
                    
                    this.ownerCt.ownerCt.getForm().submit({
                        url : basepath + '/FileUpload?isImport=true',
                        success : function(form,o){                    		 
                            _tempImpFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
                            var condi = {};
                            condi['filename'] =_tempImpFileName;
                            condi['tradecode'] = tradecode;
                            Ext.Ajax.request({
                                url:basepath+"/ImportAction.json",
                                method:'GET',
                                params:{
                                    "condition":Ext.encode(condi)
                                },
                                success:function(){
                                	importForm.importSuccessHandler(null);                                	
                                    var importFresh = function(){
                                        Ext.Ajax.request({
                                            url:basepath+"/ImportAction!refresh.json",
                                            method:'GET',
                                            success:function(a){                                        		
                                                if(a.status == '200' || a.status=='201'){
                                                    var res = Ext.util.JSON.decode(a.responseText);
                                                    if(res.json.result!=undefined&&res.json.result=='200'){
                                                        window.clearInterval(impRefreshHandler); 
                                                        if(res.json.BACK_IMPORT_ERROR&&res.json.BACK_IMPORT_ERROR=='FILE_ERROR'){
                                                        	Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
                                                        	return;
                                                        }
                                                        importState = true;
                                                        progressWin.hide();
                                                        //将临时表数据存入客户群成员表
                                                        var pkHead = res.json.pkHead;
                                                        Ext.Msg.wait('正在处理，请稍后......','系统提示');
                                                        Ext.Ajax.request({
		    		    		    	    				url : basepath + '/groupmemberedit!saveMemberByImp.json',
		    		    		    	    				params:{
		    		    		    	    					pkHead: pkHead,
		    		    		    	    					groupId:custBaseid
		    		    		    	    					},
		    		    		    	    					method : 'GET',
		    		    		    	    					waitMsg : '正在保存,请等待...',
		    		    		    	    					success :checkResult,
		    		    		    							failure :checkResult
		    		    							 });
                                                        function checkResult(response){
                                                        	
                                                        	 var resultArray = Ext.util.JSON.decode(response.status);
                                     						if (resultArray == 200 ||resultArray == 201) {
                                     							var number =  Ext.util.JSON.decode(response.responseText).number;
                                     							Ext.MessageBox.alert('系统提示信息', '操作成功，成功加入客户'+number+'位');
															
	    		    		    	    				}else if(resultArray == 403) {
	    		    		    	 				           Ext.Msg.alert('系统提示', response.responseText);
	    		    		  	    				  } else{
	    		    		  	    					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	    		    		  	    				  }
	    		    		  				 }
                                                        
                                                        
                                                    }else if(res.json.result!=undefined&&res.json.result=='900'){
                                                        window.clearInterval(impRefreshHandler);
                                                        new Ext.Window({
                                                            title:"导入失败：导入线程处理失败！",
                                                            width:200,
                                                            height:200,
                                                            bodyStyle:'text-align:center',
                                                            html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
                                                        }).show();
                                                    }else if (res.json.result!=undefined&&res.json.result=='999'){
                                                    	importForm.importSuccessHandler(res.json);
                                                    }
                                                }
                                            }
                                        });
                                    };
                                    impRefreshHandler = window.setInterval(importFresh, 1000);
                                },
                                failure:function(){}
                            });
                           
                        },
                        failure : function(form, o){
                            Ext.Msg.show({
                                title : 'Result',
                                msg : '数据文件上传失败，请稍后重试!',
                                buttons : Ext.Msg.OK,
                                icon : Ext.Msg.ERROR
                            });
                        }
                    });
                }
            }
        },{
			id:'importTemple',
			text : '导入模板下载',
			iconCls:'addIconCss',
			disabled:false,
			handler : function(){
                window.open( basepath+'/TempDownload?filename=importantGroupCust.xlsx','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
            }
		}]
});
		/********************************/
		
		/**
		 * 数据集加载器
		 */	
		var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
				parentAttr : 'PARENT_ID',
				locateAttr : 'NODEID',
				rootValue : '0',
				textField : 'NAME',
				idProperties : 'NODEID'
			});
			Ext.Ajax.request({
				url : basepath + '/queryagilequery.json',
				method : 'GET',
				success : function(response) {
					var nodeArra = Ext.util.JSON.decode(response.responseText);
					loader.nodeArray = nodeArra.JSON.data;
					nodeArrays=nodeArra.JSON.data;
					for ( var item in loader.nodeArray) {
						if (typeof loader.nodeArray[item] === 'object') {
							if (loader.nodeArray[item].TABLES == '2')
								loader.nodeArray[item].NODEID = 'b' + loader.nodeArray[item].NODEID;
						}
					}
					var children = loader.loadAll();
					treeOfPoroduct2.appendChild(children);
					treeOfPoroduct2.expandAll();
				}
			});
			/**
			 * 数据集字段树
			 */
			var treeOfPoroduct2 = new Com.yucheng.bcrm.TreePanel({
				title : '条件字段',
				autoScroll : true,
				rootVisible : false,
				ddGroup : 'rightPanel2',
				split : true,
				enableDrag:true,
				/** 虚拟树形根节点 */
				root : new Ext.tree.AsyncTreeNode({
					id : 'root',
					expanded : true,
					text : '客户视图',
					autoScroll : true,
					children : []
				}),
				resloader : loader
			});
			
			treeOfPoroduct2.on('afterrender',function(tree){
				treeOfPoroduct2.root.expand( true, false, function(a,b,c,d){
				});
			});

			
			treeOfPoroduct2.on('afterrender',function(tree){
				treeOfPoroduct2.root.expand( true, false, function(a,b,c,d){
				});
			});
				
			/**
			 * 条件、查询项展示面板
			 */


			Ext.ns('Com.yucheng.crm.query');

			/**
			 * 查询条件常量
			 */
			Com.yucheng.crm.query.Util = {
				optypes :{
					INCLUDE : [['等于', '0002'],['包含', '0000']],
					COMPARE : [['大于', '0001'], ['等于', '0002'], ['小于', '0003'], ['大于等于', '0004'], ['小于等于', '0005']],
					EQUAL : [['等于', '0002']],
					ALL : [['包含', '0000'],['大于', '0001'], ['等于', '0002'], ['小于', '0003'], ['大于等于', '0004'], ['小于等于', '0005']]
				},
				types : {
					VARCHAR2:'QUERYUTIL.optypes.INCLUDE',
					DATE:'QUERYUTIL.optypes.COMPARE',
					NUMBER:'QUERYUTIL.optypes.COMPARE',
					DECIMAL:'QUERYUTIL.optypes.COMPARE',
					INTEGER:'QUERYUTIL.optypes.COMPARE',
					VARCHAR:'QUERYUTIL.optypes.INCLUDE',
					CHAR:'QUERYUTIL.optypes.INCLUDE',
					BIGINT:'QUERYUTIL.optypes.COMPARE'
				},
				orderTypes : new Ext.data.SimpleStore({
					fields : ['name', 'code'],
					data : [['不排序', '0'],['正序', '1'],['逆序', '2']]
				}),
				custBaseInfo : {
					dbTable :'ACRM_F_CI_CUSTOMER',
					idColumn : 'CUST_ID',
					nameColumn : 'CUST_NAME',
					typeColumn : 'CUST_TYPE',
					certTypeColumn : 'IDENT_TYPE',
					certNoColumn : 'IDENT_NO',
					dbNode : false,
					idNode : false,
					nameNode : false,
					typeNode : false
				}
			};
			QUERYUTIL = Com.yucheng.crm.query.Util;

			Com.yucheng.crm.query.QeuryPanel = Ext.extend(Ext.FormPanel, {
				height :400,
				labelAlign: 'top',
				bodyStyle:'padding:0px 0px 0px 5px',
				autoHeight : true,
				autoWidth : false
			});
			/**
			 * 查询条件面板类
			 */
			Com.yucheng.crm.query.SearchPanel = Ext.extend(Com.yucheng.crm.query.QeuryPanel, {
				
				conditions : new Array(),
				initComponent : function(){
					Com.yucheng.crm.query.SearchPanel.superclass.initComponent.call(this);
					this.add(new Ext.Panel({
						html:'<table><tr><td style= "text-align:center;width:80px;font-size:12px;">属性 </td><td style= "text-align:center;width:170px;font-size:12px;">操作符</td><td style= "text-align:center;width:170px;font-size:12px;">属性值</td><td></td></tr></table> '
					}));
				},
				/**
				 * 添加一个查询条件
				 * @param node:数据字段节点，从数据集树上获取
				 * @param op : 可选参数，查询条件操作符
				 * @param value : 可选参数，查询条件值
				 */
				addItems : function(node,op,value){
					for(var n=0;n<this.conditions.length;n++){
						if(this.conditions[n].nodeInfo === node){
							Ext.Msg.alert('提示','该列已选');
							return false;
						}
					}
					
					var si = new Com.yucheng.crm.query.SearchItem({
						nodeInfo:node,
						oprater :op,
						conditionValue : value
					});
					this.conditions.push(si);
					this.add(si);
					this.doLayout();
				},
				/**
				 * 移除一个查询条件项
				 * @param item:查询条件面板
				 */
				removeItem : function(item){
					this.conditions.remove(item);
					this.remove(item);
				},
				/**
				 * 移除所有查询条件
				 */
				removeAllItems : function(){
					var _this = this;
					while(_this.conditions.length>0){
						_this.removeItem(_this.conditions[0]);
					}
				},
				/**
				 * 获取查询条件
				 */
				getConditionsAttrs : function(){
					var _this = this;
					var conditions = new Array();
					Ext.each(_this.conditions, function(con){
						var conAtt = {};
						conAtt.ss_col_item = con.columnId.substring(1);
						conAtt.ss_col_op = con.oprater;
						conAtt.ss_col_value = con.conditionValue;
						conditions.push(conAtt);
					});
					return conditions;
				}
			});
			/**
			 * 查询条件项面板
			 */
			Com.yucheng.crm.query.SearchItem = Ext.extend(Ext.Panel,{
				nodeInfo : false, //关联数据字段节点
				oprater: null,	//条件操作符
				conditionValue:null,//条件值
				
				valueStore : false,
				/**
				 * 对象构建方法，初始化面板各数据属性，根据字段类型以及字典编码，创建操作符、字段值下拉框数据源（store）
				 */
				initComponent : function(){
			    	if(!this.nodeInfo){
			    		return false;
			    	}
			    	this.textName = this.nodeInfo.text;
			    	this.columnId = this.nodeInfo.id;
			    	this.columnName = this.nodeInfo.attributes.ENAME;
			    	this.columnType  = this.nodeInfo.attributes.CTYPE;
			    	this.datasetId = this.nodeInfo.attributes.PARENT_ID;
			    	this.opstore = new Ext.data.SimpleStore({
			    		fields : ['name', 'code']
			    	});
			    	this.opstore.loadData(this.nodeInfo.attributes.NOTES?QUERYUTIL.optypes.EQUAL:eval(QUERYUTIL.types[this.columnType]));
			    	if(this.nodeInfo.attributes.NOTES){
			    		this.valueStore = new Ext.data.Store({
			    			restful:true,
			    			proxy : new Ext.data.HttpProxy({
			    				url :basepath+'/lookup.json?name='+this.nodeInfo.attributes.NOTES
			    			}),
			    			reader : new Ext.data.JsonReader({
			    				root : 'JSON'
			    			}, [ 'key', 'value' ])
			    		});
			    	}
			    	
			    	Com.yucheng.crm.query.SearchItem.superclass.initComponent.call(this);
				},
				/**
				 * 销毁方法，对象销毁时触发，销毁对象所创建的数据源
				 */
				onDestroy :function(){
					var _this = this;
					if(_this.valueStore)
						_this.valueStore.destroy();
					if(_this.opstore){
						_this.opstore.destroy();
					}
					Com.yucheng.crm.query.SearchItem.superclass.onDestroy.call(this);
				},
				/**
				 * 渲染方法，对象渲染时（首次展示）触发，创建字段名、操作符、条件值展示框。
				 */
				onRender : function(ct, position){
					
					var _this = this;
					_this.nameField = new Ext.form.DisplayField({
						emptyText : '',
						editable : false,
						triggerAction : 'all',
						allowBlank : false,
						hideLabel:true,
						xtype : 'displayfield',
						name : 'attributeName_' + id,
						anchor : '95%',
						value : _this.textName
					});
					_this.opField = new Ext.form.ComboBox({
						hiddenName:'operateName_'+id,
						hideLabel:true,
						allowBlank : false,
						labelStyle: 'text-align:right;',
						triggerAction : 'all',
						store : _this.opstore,
						displayField : 'name',
						valueField : 'code',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText:'请选择',
						resizable : true,
						anchor : '95%',
						value : _this.oprater,
						listeners : {
							select : function(combo,record,index){
								_this.oprater = record.data.code;
							}
						}
					});
					if(_this.nodeInfo.attributes.CTYPE == 'DATE'){
						_this.valueField = new Ext.form.DateField({
							allowBlank : false,
							hideLabel:true,
							labelStyle: 'text-align:right;',
							format:'Y-m-d', //日期格式化
							name : 'attributeValueName_' + id,
							anchor:'95%',
							value : _this.conditionValue,
							listeners : {
								select : function(combo,date){
									_this.conditionValue = date.format('Y-m-d');
								}
							}
						});
					} else if (_this.valueStore){
						_this.valueStore.load();
						_this.valueField = new Ext.form.ComboBox({
							hiddenName:'operateName_'+id,
							hideLabel:true,
							allowBlank : false,
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							store : _this.valueStore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '95%',
							value : _this.conditionValue,
							listeners : {
								select : function(combo,record,index){
									_this.conditionValue = record.data.key;
								}
							}
						});
					} else {
						_this.valueField = new Ext.form.TextField({
							emptyText : '',
							editable : false,
							triggerAction : 'all',
							allowBlank : false,
							hideLabel : true,
							name : 'attributeValueName_' + id,
							labelStyle : 'text-align:right;',
							xtype : 'textfield',
							anchor : '95%',
							value : _this.conditionValue,
							listeners : {
								change : function(field,newValue,oldValue){
									_this.conditionValue = field.getValue();
								}
							}
						});
					}
					this.add(new Ext.Panel({
						items : [{
							layout : 'column',
							border : false,
							items : [{
								columnWidth : .15,
								layout : 'form',
								border : false,
								items : [_this.nameField]
							}, {
								columnWidth : .30,
								layout : 'form',
								border : false,
								items : [_this.opField]
							}, {
								columnWidth : .30,
								layout : 'form',
								border : false,
								items : [_this.valueField]
							}, {
								columnWidth : .021,
								layout : 'form',
								border : false,
								items : []
							}, {
								columnWidth : .079,
								layout : 'form',
								border : false,
								items : [{ 
									xtype: 'button', 
									text: '删除', 
									scope: this, 
									handler: function(){ 
										_this.ownerCt.removeItem(_this);
									} 
								}]
							}
						]}]
					}));
					
					Com.yucheng.crm.query.SearchItem.superclass.onRender.call(this, ct, position);
				}
			});

			/**
			 * 查询结果列面板
			 */
			Com.yucheng.crm.query.ColumnsPanel = Ext.extend(Com.yucheng.crm.query.QeuryPanel, {
				
				resultColumns : new Array(),	//查询结果面板数组
				selectModel : new Ext.grid.CheckboxSelectionModel(),//公用复选框
			    resultNumber : new Ext.grid.RowNumberer({	//数据行号框
			        header : 'No.',
			        width : 28
			    }),
			    /**
			     * 构造方法，创建面板头
			     */
			    initComponent:function(){
					Com.yucheng.crm.query.ColumnsPanel.superclass.initComponent.call(this);
					this.add(new Ext.Panel({
						layout : 'column',
						border : false,
						items : [{
							html:'<table><tr><td style= "text-align:center;width:80px;font-size:12px;">名称 </td><td style= "text-align:center;width:170px;font-size:12px;">排序方式</td><td></td></tr></table> '
						}]
					}));
				},
				onRender : function(ct, position){
					Com.yucheng.crm.query.ColumnsPanel.superclass.onRender.call(this, ct, position);
				},
				
				/**
				 * 添加一个结果列
				 * @param node:字段节点对象；
				 * @param sortType:排序属性；
				 * @param hidden:是否隐藏字段；
				 * @param override:只有当值为：false时，不会改变hidden属性；
				 */
				addItems : function(node,sortType,hidden,override){
					
					var _this = this;
					for(var n=0;n<this.resultColumns.length;n++){
						if(this.resultColumns[n].nodeInfo === node){
							
							if(override === false){
								this.resultColumns[n].columnTotle = 'BASE';
							}else if(override !== false){
								this.resultColumns[n].show();
							}
							return false;
						}
					}
					var tSort = 0;
					if(sortType){
						tSort = sortType;
					}
					
					var columnTotle = 0;
					
					if( node === QUERYUTIL.custBaseInfo.idNode 
							|| node === QUERYUTIL.custBaseInfo.nameNode 
							|| node === QUERYUTIL.custBaseInfo.typeNode ){
						columnTotle = "BASE";
					}else {
						Ext.each(_this.resultColumns,function(rc){
							if(rc.nodeInfo.attributes.ENAME == node.attributes.ENAME){
								columnTotle ++;
							}
						});
					}
					var ci = new Com.yucheng.crm.query.ColumnsItem({
						nodeInfo:node,
						sortType:tSort,
						hidden:hidden,
						columnTotle:columnTotle
					});
					this.resultColumns.push(ci);
					this.add(ci);
					this.doLayout();
				},
				/**
				 * 移除一个结果字段面板
				 */
				removeItem : function(columnItem){
					this.resultColumns.remove(columnItem);
					this.remove(columnItem);
				},
				/**
				 * 移除所有结果字段
				 */
				removeAllItems : function(){
					var _this = this;
					while(_this.resultColumns.length>0){
						_this.removeItem(_this.resultColumns[0]);
					}
				},
				/**
				 * 获取查询结果字段属性，包括字段ID，排序类型，以及别名后缀，供后台使用
				 */
				getResults : function(){
					var _this = this;
					var rsults = [];
					Ext.each(_this.resultColumns,function(column){
						var r = {};
						r.columnId = column.nodeId.substring(1);
						r.sortType = column.sortType;
						r.columnTotle = column.columnTotle;
						rsults.push(r);
					});
					return rsults;
				},
				/**
				 * 获取查询结果字段ID拼串，保存查询方案时调用
				 */
				getResultsIds : function(){
					var resuldIds = [];
					var _this = this;
					Ext.each(_this.resultColumns,function(column){
						resuldIds.push(column.nodeId.substring(1));
					});
					return resuldIds.join(',');
				},
				/**
				 * 获取排序类型拼串，保存查询方案时调用
				 */
				getSortTypes : function(){
					var resuldIds = [];
					var _this = this;
					Ext.each(_this.resultColumns,function(column){
						resuldIds.push(column.sortType);
					});
					return resuldIds.join(',');
				},
				/**
				 * 获取查询结果reader属性，查询结果时调用；
				 * 有字典编码属性的字段讲生成两个字段。
				 */
				getResultReaderMetas : function(){
					var readerMetas = {
							successProperty: 'success',
							messageProperty: 'message',
							idProperty: 'CUST_ID',
							root:'json.data',
							totalProperty: 'json.count'
					};
					var readerFields = [];
					Ext.each(simple21.resultColumns,function(column){
						var t = {};
						t.name = column.columnName+'_'+column.columnTotle;
						readerFields.push(t);
						if(column.columnLookup){
							var t2 = {};
							t2.name = column.columnName+'_'+column.columnTotle+'_ORA';
							readerFields.push(t2);
						}
					});
					readerMetas.fields = readerFields;
					return readerMetas;
				},
				/**
				 * 获取查询结果列模型，查询结果时调用；
				 * 1、有字典编码属性的字段将生成两个字段；
				 * 2、隐藏字段将也包含在内。
				 */
				getResultColumnHeaders : function(){
					var _this = this;
					var columnHeaders = [];
					columnHeaders.push(_this.resultNumber);
					columnHeaders.push(_this.selectModel);
					Ext.each(simple21.resultColumns,function(column){
						
						var columnHead = {};
						
						columnHead.header = column.textName;
						columnHead.hidden = column.hidden;
						columnHead.hideable  = !column.hidden;
						columnHead.dataIndex = column.columnName+'_'+column.columnTotle;
						columnHeaders.push(columnHead);
						
						if(column.columnLookup){
							var columnHeadLooked = {};
							columnHeadLooked.header = column.textName;
							columnHeadLooked.hidden = column.hidden;
							columnHeadLooked.hideable  = !column.hidden;
							columnHeadLooked.dataIndex = column.columnName+'_'+column.columnTotle+'_ORA';
							columnHeaders.push(columnHeadLooked);
							columnHead.hidden = true;
							columnHead.hideable = false;
						}
					});
					return columnHeaders;
				},
				/**
				 *  获取可分组字段数据，排除隐藏字段，分组统计时调用
				 */
				getResultColumnHeaderByNodeId : function(id){
					var _this = this;
					var columnHeaders = [];
					for(var i=0;i<_this.resultColumns.length;i++){
						if(_this.resultColumns[i].nodeId == id){
							var header = {};
							header.header = _this.resultColumns[i].textName;
							if(_this.resultColumns[i].columnLookup){
								header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle+'_ORA';
							}else{
								header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle;
							}
							return header;
						}
					}
					return;
				},
				/**
				 * 获取可统计字段，排除隐藏字段，分组统计时调用
				 */
				getSumColumnsByNodeIds : function(ids){
					var _this = this;
					var sums = [];
					for(var i=0;i<_this.resultColumns.length;i++){
						if(ids.indexOf(_this.resultColumns[i].nodeId)>=0){
							var header = {};
							header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle+'_SUM';
							header.header = _this.resultColumns[i].textName+'(统计)';
							sums.push(header);
						}
					}
					return sums;
				},
				/**
				 * 获取当前展示列数
				 */
				viewedColumnsCount : function(){
					var _this = this;
					var count = 0;
					Ext.each(_this.resultColumns,function(column){
						if(!column.hidden){
							count ++ ;
						}
					});
					return count;
				}
			});

			/**
			 * 查询结果列面板
			 */
			Com.yucheng.crm.query.ColumnsItem =  Ext.extend(Ext.Panel,{
				nodeInfo : false,//字段节点
				sortType : 0,//排序类型
				/**
				 * 构造方法，初始化面板各项数据信息
				 */
				initComponent : function(){
				
					if(!this.nodeInfo){
						return false;
					}
					this.textName = this.nodeInfo.text;
					this.nodeId = this.nodeInfo.id;
					this.columnName = this.nodeInfo.attributes.ENAME;
					this.columnType = this.nodeInfo.attributes.CTYPE;
					this.datasetId = this.nodeInfo.attributes.PARENT_ID;
					this.columnLookup = this.nodeInfo.attributes.NOTES;
					Com.yucheng.crm.query.ColumnsItem.superclass.initComponent.call(this);
				},
				
				/**
				 * 渲染方法，面板渲染时调用，构建结果列名、排序下拉框
				 */
				onRender : function(ct, position){
					
					var _this = this;
					_this.columnField = new Ext.form.DisplayField({
						emptyText : '',
						editable : false,
						triggerAction : 'all',
						allowBlank : false,
						hideLabel:true,
						xtype : 'displayfield',
						anchor : '95%',
						value : _this.textName
					});
					
					_this.orderField =  new Ext.form.ComboBox({
						hideLabel : true,
						labelStyle : 'text-align:right;',
						triggerAction : 'all',
						store : QUERYUTIL.orderTypes,
						value:_this.sortType,
						displayField : 'name',
						valueField : 'code',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText : '请选择',
						resizable : true,
						anchor : '95%',
						listeners : {
							select : function(combo,record,index){
								_this.sortType = record.data.code;
							}
						}
					}) ;
					
					this.add(new Ext.Panel({
						items : [{
							layout : 'column',
							border : false,
							items : [{
								columnWidth : .15,
								layout : 'form',
								labelWidth : 60,
								border : false,
								items : [_this.columnField]
							}, {
								columnWidth : .30,
								layout : 'form',
								labelWidth : 60,
								border : false,
								items : [_this.orderField]
							}, {
								columnWidth : .30,
								layout : 'form',
								labelWidth : 60,
								border : false,
								items : []
							}, {
								columnWidth : .021,
								layout : 'form',
								labelWidth : 100,
								border : false,
								items : []
							}, {
								columnWidth : .079,
								layout : 'form',
								labelWidth : 100,
								border : false,
								items : [{ 
									xtype: 'button', 
									text: '删除', 
									scope: this, 
									handler: function(){ 
										_this.ownerCt.removeItem(_this);
									} 
								}]
							}]
						}]
					}));
					Com.yucheng.crm.query.ColumnsItem.superclass.onRender.call(this, ct, position);
				}
			});
				//查询条件对象
				var simple21 = new Com.yucheng.crm.query.SearchPanel({
					title : '查询条件',
					listeners:{
						"activate":function(){
							this.doLayout();
						}
					}
				});
				//结果列对象
				var simple22 = new Com.yucheng.crm.query.ColumnsPanel({
					title : '显示列',
					hidden:true,
					listeners:{
						"activate":function(){
							this.doLayout();
						}
					}
				});
				
				var tabmain2 = new Ext.TabPanel({
					autoScroll : true,
					id : 'tabmain2',
					width : 600,
					height : 200,
					activeTab : 0,
					frame : true,
					defaults : {
						autoHeight : true
					},
					items : [simple21]
				});
				//条件链接符面板
				var radio2 = new Ext.Panel({
					layout : 'column',
					border : false,
					items : [ {
						columnWidth : .09,
						layout : 'form',
						labelWidth : 8,
						border : false,
						items : [ new Ext.form.Radio({
							boxLabel : "与",
							labelStyle: 'text-align:right;',
							id : "Radio21",
							name : "a",
							checked : true,
							listeners : {
								check : function(r,v){
									if(v)
										right_panel2.conditionJoinType = 'true';
									else
										right_panel2.conditionJoinType = 'false';
								}
							}
						})]
					}, {
						columnWidth : .09,
						layout : 'form',
						labelWidth :8,
						border : false,
						items : [ new Ext.form.Radio({
							boxLabel : "或",
							//labelStyle: 'text-align:right;',
							id : "Radio22",
							name : "a"
						}) ]
					} ]
				});
				
				//保存方案
				var fnBatchSave2= function(){
					
					var solutionAttr = {};
					solutionAttr.ss_results = simple22.getResultsIds();
					solutionAttr.ss_sort = simple22.getSortTypes();
					var conditions = simple21.getConditionsAttrs();
					Ext.Ajax.request({
						url:basepath+'/agilesearch!create1.json',
						success : function(response) {
							Ext.Msg.alert('提示', '操作成功');
							ifadd = 'false';
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
								Ext.Msg.alert('提示','您没有此权限!');
							} else {
								Ext.Msg.alert('提示','操作失败!');
							}
						},
						params : {
							solutionAttr:Ext.encode(solutionAttr),
							conditionCols : Ext.encode(conditions),
							group_id:custBaseid,
							flag:ifadd,
							group_name:getCustomerViewByTitle("基本信息").getFieldByName('CUST_BASE_NAME').getValue(),
							'radio':right_panel2.conditionJoinType
						}
					});
				};
				
				//查询结果数据源
				var getCustStore = new Ext.data.Store({
					restful : true,
					url:basepath+'/queryagileresult.json',
					reader:new Ext.data.JsonReader({
						successProperty: 'success',
						messageProperty: 'message',
						fields : [{
							name:'CUST_ID'
						}]
					})
				});
				
				/**
			 * 查询结果分页
			 */ 
			var getCustsize_combo = new Ext.form.ComboBox({
				  	name : 'pagesize',
				  	triggerAction : 'all',
				  	mode : 'local',
				  	store : new Ext.data.ArrayStore({
				  				fields : ['value', 'text'],
				  				data : [ 
				  						[ 20, '20条/页' ],[ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],
				  						[ 500, '500条/页' ] ]
				  			}),
				  	valueField : 'value',
				  	displayField : 'text',
				  	value : '20',
				  	editable : false,
				  	width : 85,
				  	listeners : {
						select : function(combo){
							custBar.pageSize = parseInt(getCustsize_combo.getValue());
							getCustStore.reload({
								params : {
									groupMemberType:getCustomerViewByTitle("基本信息").getFieldByName('CUST_MEMBER_TYPE').getValue(),
									start : 0,
									limit : parseInt(getCustsize_combo.getValue())
								}
							});
						}
					}
				});
				
			//查询结果列模型
			var getCustCm = new Ext.grid.ColumnModel(simple22.getResultColumnHeaders());
			getCustCm.on("configchange",function(){
				getCustGrid.view.refresh(true);
			}); 

			//分页工具栏
				var custBar = new Ext.PagingToolbar({
					pageSize : getCustsize_combo.getValue(),
					store : getCustStore,
					displayInfo : true,
					displayMsg : '显示{0}条到{1}条,共{2}条',
					//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
					emptyMsg : "没有符合条件的记录",
					items : ['-', '&nbsp;&nbsp;', getCustsize_combo]
				});
				
			//查询结果列表面板
				var getCustGrid = new Ext.grid.GridPanel({
					frame : true,
					region : 'center', // 返回给页面的div
					store : getCustStore, // 数据存储
					stripeRows : true, // 斑马线
					cm : getCustCm, // 列模型
					sm : simple22.selectModel,
					bbar : custBar,// 分页工具栏
					viewConfig : {
					},
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});


			//查询结果窗口
				var resultWindow = new Ext.Window({
					layout : 'fit',
					maximized : true,
					closable : true,// 是否可关闭
			        title : '查询结果展示',
					modal : true,
					closeAction : 'hide',
					titleCollapse : true,
					buttonAlign : 'center',
					border : false,
					animCollapse : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [{
						layout:'border',
						items:[
							getCustGrid
						]
					}],
					buttons : [{
						text : '关闭',
						handler : function() {
							resultWindow.hide();
							getCustStore.removeAll();
						}
					}]
				});
				
				function getResultReaderMetas1(){
					var readerMetas = {
							successProperty: 'success',
							messageProperty: 'message',
							idProperty: 'CUST_ID',
							root:'json.data',
							totalProperty: 'json.count'
					};
					var readerFields = [];
					Ext.each(simple22.resultColumns,function(column){
						var t = {};
						t.name = column.columnName+'_'+column.columnTotle;
						readerFields.push(t);
						if(column.columnLookup){
							var t2 = {};
							t2.name = column.columnName+'_'+column.columnTotle+'_ORA';
							readerFields.push(t2);
						}
					});
					readerMetas.fields = readerFields;
					return readerMetas;
				
				};
				
				function getResultColumnHeaders1() {
					var columnHeaders = [];
					Ext.each(simple22.resultColumns,function(column){
						
						var columnHead = {};
						
						columnHead.header = column.textName;
						columnHead.hidden = column.hidden;
						columnHead.hideable  = !column.hidden;
						columnHead.dataIndex = column.columnName+'_'+column.columnTotle;
						columnHeaders.push(columnHead);
						
						if(column.columnLookup){
							var columnHeadLooked = {};
							columnHeadLooked.header = column.textName;
							columnHeadLooked.hidden = column.hidden;
							columnHeadLooked.hideable  = !column.hidden;
							columnHeadLooked.dataIndex = column.columnName+'_'+column.columnTotle+'_ORA';
							columnHeaders.push(columnHeadLooked);
							columnHead.hidden = true;
							columnHead.hideable = false;
						}
					});
					return columnHeaders;
				};
				
				//查询结果方法
				var fnSearchResult = function(){
					if(simple21.conditions.length==0){
						Ext.Msg.alert('提示', '未加入任何条件列！');
						return;
					}
					if(!simple21.getForm().isValid()){
						Ext.Msg.alert('提示', '查询条件输入有误！');
						return;
					}
					
					//初始化客户ID、客户名称、证件类型，证件号码，客户类型字段节点，从数据集树上获取
					if(QUERYUTIL.custBaseInfo.dbNode === false){
						QUERYUTIL.custBaseInfo.dbNode = treeOfPoroduct2.root.findChild('VALUE',QUERYUTIL.custBaseInfo.dbTable,true);
						QUERYUTIL.custBaseInfo.idNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.idColumn);
						QUERYUTIL.custBaseInfo.nameNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.nameColumn);
						QUERYUTIL.custBaseInfo.typeNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.typeColumn);
						QUERYUTIL.custBaseInfo.certTypeNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.certTypeColumn);
						QUERYUTIL.custBaseInfo.certNoNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.certNoColumn);
					}
					
					//结果列添加客户ID、名称、类型字段
					simple22.addItems(QUERYUTIL.custBaseInfo.idNode,'0',false,false);
					simple22.addItems(QUERYUTIL.custBaseInfo.nameNode,'0',false,false);
					simple22.addItems(QUERYUTIL.custBaseInfo.typeNode,'0',false,false);
					simple22.addItems(QUERYUTIL.custBaseInfo.certTypeNode,'0',false,false);
					simple22.addItems(QUERYUTIL.custBaseInfo.certNoNode,'0',false,false);
					//弹出窗口
					resultWindow.show();
					
					//根据查询结果列配置，修改查询结果数据源reader字段
					getCustGrid.store.reader.onMetaChange(getResultReaderMetas1());
					//根据查询结果列配置，修改查询结果列表面板列模型
					getCustGrid.colModel.setConfig(getResultColumnHeaders1(), false) ;
					
					//获取动态查询所需要的各个数据参数
					getCustGrid.store.baseParams = {
						conditionAttrs : Ext.encode(simple21.getConditionsAttrs()),
						results :  Ext.encode(simple22.getResults()),
						radio : right_panel2.conditionJoinType
					};
					//查询数据
					getCustGrid.store.load({
						params: {
							groupMemberType: menberType ,
							start : 0,
							limit : getCustsize_combo.getValue()
						}
					});
				};
				//右侧面板
				var right_panel2 = new Ext.Panel({
					conditionJoinType : 'true',//条件连接符数据，根据radio对象点选情况
					height : 250,
					width : 625,
					frame : true,
					autoScroll : true,
					items : [ tabmain2,radio2],
					title : '查询设置',
					buttonAlign : 'center',
					buttons : [ {
						text : '保存',
						handler : function() {
							
							if(simple21.conditions.length==0){
								Ext.Msg.alert('提示', '未加入任何条件列！');
								return;
							}
							if(!simple21.getForm().isValid()){
								Ext.Msg.alert('提示', '查询条件输入有误！');
								return;
							}
								fnBatchSave2();
						}
					},{
						text : '试查询',
						handler : function() {
							fnSearchResult();
						}
					}]
				});
				right_panel2.on('afterrender',function(){
					/**数据字段拖拽代理*/
					new Ext.dd.DropTarget(right_panel2.body.dom, {
				    	ddGroup : 'rightPanel2',
				    	notifyDrop : function(ddSource, e, data) {
				    	if (!data.node.leaf) {
				    		return;
				    	}
				    	var changeFlag=false;
				    	if(tabmain2.activeTab==simple21){
				    		simple21.addItems(data.node);
				    		tabmain2.setActiveTab(1);	
				    		changeFlag = true;
				    	}
				    	simple22.addItems(data.node);
				    	if(changeFlag){
				    		tabmain2.setActiveTab(0);	
				    	}
				    	return true;
				    	}
				    });
				});		
				right_panel2.on('show',function(){
					simple1.removeAllItems();
					simple12.removeAllItems();
				});	

var customerView=[{
	title:'基本信息',
	type:'form',
	hideTitle:true,
	groups:[{
		fields:[
			{name:'ID',text:'客户群ID',id:'id'},
            {name:'CUST_BASE_NUMBER',text:'客户群编号'},
            {name:'CUST_BASE_NAME',id:'custBaseName',text:'客户群名称',allowBlank:false},
            {name:'CUST_FROM',text:'客户来源',translateType:'CUSTOMER_SOURCE_TYPE',allowBlank:false},
            {name:'GROUP_MEMBER_TYPE',id:'groupMemberType',text:'群成员类型',allowBlank:false,translateType:'GROUP_MEMEBER_TYPE'},
            {name:'SHARE_FLAG',text:'共享范围',translateType:'SHARE_FLAG',allowBlank:false},
            {name:'CUST_BASE_CREATE_DATE',text:'创建日期'},
            {name:'CUST_BASE_CREATE_ORG',text:'创建机构ID'},
            {name:'CUST_BASE_CREATE_NAME',text:'客户群创建人'},
            {name:'RECENT_UPDATE_USER',text:'最近更新人'},
            {name:'RECENT_UPDATE_ORG',text:'最近更新机构'},
            {name:'RECENT_UPDATE_DATE',text:'最近更新日期'}
        ],
		fn:function(ID,CUST_BASE_NUMBER,CUST_BASE_NAME,CUST_FROM,GROUP_MEMBER_TYPE,
			SHARE_FLAG,CUST_BASE_CREATE_DATE,CUST_BASE_CREATE_ORG,
			CUST_BASE_CREATE_NAME,RECENT_UPDATE_USER,RECENT_UPDATE_ORG,RECENT_UPDATE_DATE){
			
			ID.hidden=true;
			CUST_BASE_NUMBER.hidden=true;
			CUST_BASE_CREATE_DATE.hidden=true;
			CUST_BASE_CREATE_ORG.hidden=true;
			CUST_BASE_CREATE_NAME.hidden=true;
			RECENT_UPDATE_USER.hidden=true;
			RECENT_UPDATE_ORG.hidden=true;
			RECENT_UPDATE_DATE.hidden=true;
			
		  	return [ID,CUST_BASE_NUMBER,CUST_BASE_NAME,CUST_FROM,GROUP_MEMBER_TYPE,
				SHARE_FLAG,CUST_BASE_CREATE_DATE,CUST_BASE_CREATE_ORG,
				CUST_BASE_CREATE_NAME,RECENT_UPDATE_USER,RECENT_UPDATE_ORG,RECENT_UPDATE_DATE];
		}
	},{
		columnCount:1,
		fields:[
			{name:'CUST_BASE_DESC',text:'客户群描述',xtype:'textarea',resutlWidth:350,anchor:'95%'}
		],
		fn:function(CUST_BASE_DESC){
			return [CUST_BASE_DESC];
		}
	}],
	formButtons:[{
		text:'保存并下一步',
		fn:function(formPanel,basicForm){
			if (!basicForm.isValid()) {
	            Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	            return false;
	        }
            var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/customergroupinfo.json?operate='+'add',
				params :commintData,
				method : 'POST',
				success : function() {
    				Ext.Ajax.request({
    					url: basepath +'/customergroupinfo!getPid.json',
    					success:function(response){
							var groupId = Ext.util.JSON.decode(response.responseText).pid;
							var tempGroupNumber = '';
    						if(groupId.length==5){
					        	tempGroupNumber=tempGroupNumber+'C00'+groupId;
					  	    }else if(groupId.length==6){
					  	    	tempGroupNumber=tempGroupNumber+'C0'+groupId;
					  	    }else {
					  	    	tempGroupNumber=tempGroupNumber+'C0'+groupId;
					  		}
						     custBaseid=groupId;
						     var source=basicForm.findField('CUST_FROM').getValue();
						     var custType=basicForm.findField('GROUP_MEMBER_TYPE').getValue();
						     menberType = custType;
						     formPanel.form.findField('ID').setValue(groupId);
						     formPanel.form.findField('CUST_BASE_NUMBER').setValue(tempGroupNumber);
						     Ext.Msg.alert('提示', '操作成功');
						     if(source=='1'){
						    	 customerInfoStore.removeAll();
						    	 groupLeaguerStore.removeAll();
						    	 showCustomerViewByTitle('手动添加客户群成员');
						    	 if(custType=='1'||custType=='2'){
						    		 searchPanel1.form.findField("CUST_TYPE").setValue(custType);
						    		 searchPanel1.form.findField("CUST_TYPE").setVisible(false);
						    	 }else{
						    	 	searchPanel1.form.findField("CUST_TYPE").setVisible(true);
						    	 }
						     }else if(source=='2'){
						    	 showCustomerViewByTitle('自动筛选客户群成员');
						     }else if(source=='3'){
						    	 showCustomerViewByTitle('证件导入客户群成员');
						     }
						}
					});
				},
				failure : function(response) {
					Ext.Msg.alert("提示","操作失败");
				}
			});
		}
	}]
},{
	title:'手动添加客户群成员',
	hideTitle:true,
	suspendWidth:1000,
	items:[{
		layout: 'border',
		items:[{
			region:'center',
			layout: 'border',
			items:[{
				region : 'north',
				height : 100,
				layout: 'fit',
				items:[searchPanel1]
			},{
				region: 'center',
				layout: 'fit',
				items:[customerInfoGrid]
			}]
		},{ 
			region:'east',
			layout:'fit',
			width: 400,
			items:[groupLeaguerGrid]
		}]
	}],
	buttonAlign:'center',
	buttons:[{
		text:'上一步',
		handler:function(){
			needReset = false;
			ifprve = true;
			showCustomerViewByTitle('基本信息');
		}
	},{
		text:'完成',
		handler:function(){
			hideCurrentView();
		}
	
	}]
},{
	title:'自动筛选客户群成员',
	hideTitle:true,
	suspendWidth:860,
	items:[{
		layout: 'border',
		items:[{
			region:'west',
			layout:'fit',
			width: 200,
			items:[treeOfPoroduct2]
		},{ 
			region:'center',
			items:[right_panel2]
		}]
	}],
	buttonAlign:'center',
	buttons:[{
		text:'上一步',
		handler:function(){
			needReset = false;
			ifprve = true;
			showCustomerViewByTitle('基本信息');
		}
	},{
		text:'完成',
		handler:function(){
			hideCurrentView();
		}
	
	}]
},{
	title:'证件导入客户群成员',
	hideTitle:true,
	suspendWidth:860,
	items:[{
		layout: 'border',
		items:[{
			region:'center',
			items:[importForm]
		}]
	}],
	buttons:[{
		text:'上一步',
		handler:function(){
			needReset = false; 
			ifprve = true;
			showCustomerViewByTitle('基本信息');
		}
	},{
		text:'完成',
		handler:function(){
			hideCurrentView();
		}
	
	}]
},{
	title:'反洗钱指标信息',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	suspendWidth:1,
	groups:[{
		columnCount : 1,
		fields : [],
		fn : function(){
			return [antMoneyTargetGrid];
		}
	}]
}];

var viewhide = function(view){
	if(view._defaultTitle == '手动添加客户群成员'||view._defaultTitle == '自动筛选客户群成员'||view._defaultTitle == '证件导入客户群成员'){
		if(!ifprve)
			reloadCurrentData();
	}
};
	
var viewshow = function(view){
	if(view._defaultTitle == '基本信息'){
		if(needReset){
			view.contentPanel.form.reset();
		}
	}
};

//面板滑入前操作
var beforeviewshow = function(view){
	if(view._defaultTitle=='反洗钱指标信息'){
		debugger;
		var id = getSelectedData().data.ID;
			//customerViewByTitle().hidden=false;
		antMoneyTargetStore.load({
			params:{id:id
				,
				start : 0,
				limit : parseInt(antMoneyTargetPagesize_combo.getValue())}
		});
	}
}

/******************************反洗钱指标信息************************************/
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});

var antMoneyTargetStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/groupAntMoneyTarget.json'
	}),
	reader : new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root : 'json.data'
	},[ {
		name : 'CUST_ID',
		mapping : 'CUST_ID'
	}, {
		name : 'CORE_NO',
		mapping : 'CORE_NO'
	}, {
		name : 'CUST_NAME',
		mapping : 'CUST_NAME'
	}, {
		name : 'NATION_CODE',
		mapping : 'NATION_CODE'
	}, {
		name : 'CUST_GRADE',
		mapping : 'CUST_GRADE'
	}, {
		name : 'IDENT_TYPE1',
		mapping : 'IDENT_TYPE1'
	}, {
		name : 'IDENT_NO1',
		mapping : 'IDENT_NO1'
	}, {
		name : 'IDENT_EXPIRED_DATE1',
		mapping : 'IDENT_EXPIRED_DATE1'
	}, {
		name : 'IDENT_TYPE2',
		mapping : 'IDENT_TYPE2'
	}, {
		name : 'IDENT_NO2',
		mapping : 'IDENT_NO2'
	}, {
		name : 'IDENT_EXPIRED_DATE2',
		mapping : 'IDENT_EXPIRED_DATE2'
	}, {
		name : 'INSTITUTION_CODE',
		mapping : 'INSTITUTION_CODE'
	}, {
		name : 'MGR_ID',
		mapping : 'MGR_ID'
	}, {
		name : 'USER_NAME',
		mapping : 'USER_NAME'
	}
	
	])
});
//antMoneyTargetStore.baseParams={
//			custId :_custId
//		};
//antMoneyTargetStore.load({
//	params:{custId:_custId}
//});
var sm = new Ext.grid.CheckboxSelectionModel();
//交易流水--gridtable中的列定义




var antMoneyTarget_cm = new Ext.grid.ColumnModel([
	rownum, sm,
	{header : '客户编号',dataIndex : 'CUST_ID',width : 120,sortable : true},
	{header : '核心客户号',dataIndex : 'CORE_NO',width : 120,sortable : true},
	{header : '客户名称',dataIndex : 'CUST_NAME',width : 120,sortable : true},
	{header : '国籍',dataIndex : 'NATION_CODE',width : 150,sortable : true
		,renderer:function(value){
			var val = translateLookupByKey("XD000025",value);
			return val?val:"";
		}
		},
	{header : '反洗钱等级',dataIndex : 'CUST_GRADE',width : 150,sortable :true
		,renderer:function(value){
			var val = translateLookupByKey("FXQ_RISK_LEVEL",value);
			return val?val:"";
		}},
	{header : '证件类型1',dataIndex : 'IDENT_TYPE1',width : 150,sortable : true
			,renderer:function(value){
				var val = translateLookupByKey("XD000040",value);
				return val?val:"";
			}},
	{header : '证件号码1',dataIndex : 'IDENT_NO1',width : 150,sortable : true},
	{header : '证件有效期1',dataIndex : 'IDENT_EXPIRED_DATE1',width : 150,sortable : true},
	{header : '证件类型2',dataIndex : 'IDENT_TYPE2',width : 150,sortable : true
		,renderer:function(value){
			var val = translateLookupByKey("XD000040",value);
			return val?val:"";
		}},
	{header : '证件号码2',dataIndex : 'IDENT_NO2',width : 150,sortable : true},
	{header : '证件有效期2',dataIndex : 'IDENT_EXPIRED_DATE2',width : 150,sortable : true},
	{header : '归属机构',dataIndex : 'INSTITUTION_CODE',width : 150,sortable : true},
	{header : '所属客户经理ID',dataIndex : 'MGR_ID',width : 150,sortable : true},
	{header : '所属客户经理名称',dataIndex : 'USER_NAME',width : 150,sortable : true}
]);


/**
 * 查询结果分页
 */ 
var antMoneyTargetPagesize_combo = new Ext.form.ComboBox({
	  	name : 'pagesize',
	  	triggerAction : 'all',
	  	mode : 'local',
	  	store : new Ext.data.ArrayStore({
	  				fields : ['value', 'text'],
	  				data : [ 
	  						[ 20, '20条/页' ],[ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],
	  						[ 500, '500条/页' ] ]
	  			}),
	  	valueField : 'value',
	  	displayField : 'text',
	  	value : '20',
	  	editable : false,
	  	width : 85,
	  	listeners : {
			select : function(combo){
				antMoneyTargetBbar.pageSize = parseInt(antMoneyTargetPagesize_combo.getValue());
				var id = getSelectedData().data.ID;
				antMoneyTargetStore.load({
					params : {
						id:id,
						start : 0,
						limit : parseInt(antMoneyTargetPagesize_combo.getValue())
					}
				});
			}
		}
	});

//分页工具栏
var antMoneyTargetBbar = new Ext.PagingToolbar({
	pageSize : parseInt(antMoneyTargetPagesize_combo.getValue()),
	store : antMoneyTargetStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', antMoneyTargetPagesize_combo]
});

var antMoneyTargetTbar = new Ext.Toolbar({
	items : [{
		text:'批量调整反洗钱等级',
		hidden:JsContext.checkGrant('fxq_pltz'),//检查当前角色是否有当前功能权限只对经办开放
		iconCls:'optionIconCss',//批量修改
		//hidden:true,
		handler:function(){fnBatchUpdate();}
	}
	]
});

//交易信息GRID
var antMoneyTargetGrid = new Ext.grid.GridPanel({
	bodyStyle:"height:100%",
	height:395,
	hideTitle : true,
	frame : true,
	store : antMoneyTargetStore,
	//autoWidth:true,
	stripeRows : true, // 斑马线
	loadMask : true,
	cm : antMoneyTarget_cm,
	sm:sm,
	tbar : antMoneyTargetTbar, // 表格工具栏
	bbar:antMoneyTargetBbar,
	region : 'center',
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/******************************反洗钱指标信息************************************/

	
//=============================黑名单客户群，客户信息导入窗口定义模块=============================================//
		var _tempImpFileName = "";
		var pkHead = "";
		/**
		 * 导入表单对象，此对象为全局对象，页面直接调用。
		 */
		var importForm_bl = new Ext.FormPanel({ 
		    height : 200,
		    width : '100%',
		    title:'文件导入',
		    fileUpload : true, 
		    dataName:'file',
		    frame:true,
		    tradecode:"",
		    
		    /**是否显示导入状态*/
		    importStateInfo : '',
		    importStateMsg : function (state){
				var titleArray = ['excel数据转化为SQL脚本','执行SQL脚本...','正在将临时表数据导入到业务表中...','导入成功！'];
				this.importStateInfo = "当前状态为：[<font color='red'>"+titleArray[state]+"</font>];<br>";
			},    
		    /**是否显示 当前excel数据转化为SQL脚本成功记录数*/
		    curRecordNumInfo : '',
		    curRecordNumMsg : function(o){
				this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 当前sheet页签记录数*/
			curSheetRecordNumInfo : '',
		    curSheetRecordNumMsg : function (o) {
				this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 当前sheet页签号*/
		    sheetNumInfo : '',
		    sheetNumMsg : function(o){
				this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 sheet页签总数*/
		    totalSheetNumInfo : '',
		    totalSheetNumMsg : function(o){
				this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 已导入完成sheet数*/
		    finishSheetNumInfo : '',
		    finishSheetNumMsg : function(o){
				this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>"+o+"</font>];<br>";
			},
		    /**是否显示 已导入完成记录数*/
			finishRecordNumInfo : '',
		    finishRecordNumMsg : function(o){
				this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>"+o+"</font>];<br>";
			},
		    /**当前excel数据转化为SQL脚本成功记录数*/
		    curRecordNum : 0,
		    /**导入总数*/
			totalNum : 1,
			/**进度条信息*/
			progressBarText : '',
			/**进度条Msg*/
			progressBartitle : '',
		    proxyStore : undefined,
		    items: [],
			/**进度条*/
		    progressBar : null,    
		    /***import成功句柄*/
		    importSuccessHandler : function (json){	
				if (json != null) {	
					if (typeof(json.curRecordNum) != 'undefined' && this.curRecordNumMsg) {
						this.curRecordNumMsg(json.curRecordNum);
						this.curRecordNum = json.curRecordNum;
					}
					if (typeof(json.importState) != 'undefined' && this.importStateMsg) {
						this.importStateMsg(json.importState);
					}				
					if (typeof(json.curSheetRecordNum) != 'undefined' && this.curSheetRecordNumMsg) {
						this.curSheetRecordNumMsg(json.curSheetRecordNum);
					}
					if (typeof(json.sheetNum) != 'undefined' && this.sheetNumMsg) {
						this.sheetNumMsg(json.sheetNum);
					}
					if (typeof(json.totalSheetNum) != 'undefined' && this.totalSheetNumMsg) {
						this.totalSheetNumMsg(json.totalSheetNum);
					}	
					if (typeof(json.finishSheetNum) != 'undefined' && this.finishSheetNumMsg) {
						this.finishSheetNumMsg(json.finishSheetNum);
					}
					if (typeof(json.finishRecordNum) != 'undefined' && this.finishRecordNumMsg) {
						this.finishRecordNumMsg(json.finishRecordNum);
					}
				} else {
					this.curRecordNumInfo = '';
					this.importStateInfo = '';
					this.curSheetRecordNumInfo = '';
					this.sheetNumInfo = '';
					this.totalSheetNumInfo = '';
					this.finishSheetNumInfo = '';
					this.finishRecordNumInfo = '';
				}
				
				this.progressBartitle = '';
				/**进度条Msg信息配置：各信息项目显示内容由各自方法配置*/
				this.progressBartitle += this.curRecordNumMsg      ?this.curRecordNumInfo:'';
				this.progressBartitle += this.importStateMsg 	   ?this.importStateInfo:'';
				this.progressBartitle += this.curSheetRecordNumMsg ?this.curSheetRecordNumInfo:'';
				this.progressBartitle += this.sheetNumMsg 	   	   ?this.sheetNumInfo:'';
				this.progressBartitle += this.totalSheetNumMsg 	   ?this.totalSheetNumInfo:'';
				this.progressBartitle += this.finishSheetNumMsg    ?this.finishSheetNumInfo:'';
				this.progressBartitle += this.finishRecordNumMsg   ?this.finishRecordNumInfo:'';
				
				showProgressBar(this.totalNum,this.curRecordNum,this.progressBarText,this.progressBartitle,"上传成功，正在导入数据，请稍候");
			},
		    buttons : [{
		            text : '导入文件',
		            handler : function() {
		                var tradecode = this.ownerCt.ownerCt.tradecode;
		                var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
		                var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
		                if(tradecode==undefined ||tradecode==''){
		                    Ext.MessageBox.alert('Debugging！','You forgot to define the tradecode for the import form!');
		                    return false;
		                }
		                var impRefreshHandler = 0;
		                if (this.ownerCt.ownerCt.getForm().isValid()){
		                    this.ownerCt.ownerCt.ownerCt.hide();
		                    var fileNamesFull = this.ownerCt.ownerCt.items.items[0].getValue();
		                    var extPoit = fileNamesFull.substring(fileNamesFull.indexOf('.'));
		                    if(extPoit=='.xls'||extPoit=='.XLS'||extPoit=='.xlsx'||extPoit=='.XLSX'){
		                    } else {
		                    	Ext.MessageBox.alert("文件错误","导入文件不是XLS或XLSX文件。");
		                        return false;
		                    }
		                    showProgressBar(1,0,'','','正在上传文件...');
		                    
		                    this.ownerCt.ownerCt.getForm().submit({
		                        url : basepath + '/FileUpload?isImport=true',
		                        success : function(form,o){                    		 
		                            _tempImpFileName = Ext.util.JSON.decode(o.response.responseText).realFileName;
		                            var condi = {};
		                            condi['filename'] =_tempImpFileName;
		                            condi['tradecode'] = tradecode;
		                            Ext.Ajax.request({
		                                url:basepath+"/ImportAction.json",
		                                method:'GET',
		                                params:{
		                                    "condition":Ext.encode(condi)
		                                },
		                                success:function(){
		                                	importForm_bl.importSuccessHandler(null);                                	
		                                    var importFresh = function(){
		                                        Ext.Ajax.request({
		                                            url:basepath+"/ImportAction!refresh.json",
		                                            method:'GET',
		                                            success:function(a){                                        		
		                                                if(a.status == '200' || a.status=='201'){
		                                                    var res = Ext.util.JSON.decode(a.responseText);
		                                                    if(res.json.result!=undefined&&res.json.result=='200'){
		                                                        window.clearInterval(impRefreshHandler); 
		                                                        if(res.json.BACK_IMPORT_ERROR&&res.json.BACK_IMPORT_ERROR=='FILE_ERROR'){
		                                                        	Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
		                                                        	return;
		                                                        }
		                                                        if(proxyStorePS!=undefined){
		                                                            var condiFormP = {};
		                                                            condiFormP['pkHaed'] =res.json.PK_HEAD;
		                                                            pkHead = res.json.PK_HEAD;
		                                                            proxyStorePS.load({
		                                                                params:{
		                                                                    pkHead: pkHead
		                                                                }
		                                                            });
		                                                        }else {
		                                                        	importForm_bl.importSuccessHandler(res.json); 
		                                                        	showSuccessWin(res.json.curRecordNum);
		                                                        }
		                                                    }else if(res.json.result!=undefined&&res.json.result=='900'){
		                                                        window.clearInterval(impRefreshHandler);
		                                                        new Ext.Window({
		                                                            title:"导入失败：导入线程处理失败！",
		                                                            width:200,
		                                                            height:200,
		                                                            bodyStyle:'text-align:center',
		                                                            html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
		                                                        }).show();
		                                                    }else if (res.json.result!=undefined&&res.json.result=='999'){
		                                                    	importForm_bl.importSuccessHandler(res.json);
		                                                    }
		                                                }
		                                            }
		                                        });
		                                    };
		                                    impRefreshHandler = window.setInterval(importFresh, 1000);
		                                },
		                                failure:function(){}
		                            });
		                           
		                        },
		                        failure : function(form, o){
		                            Ext.Msg.show({
		                                title : 'Result',
		                                msg : '数据文件上传失败，请稍后重试!',
		                                buttons : Ext.Msg.OK,
		                                icon : Ext.Msg.ERROR
		                            });
		                        }
		                    });
		                }
		            }
		        }]
		});
		/**
		 * 导入弹出窗口，此对象为全局对象，由各页面直接调用。
		 */

		var importWindow_bl = new Ext.Window({     
		    width : 700,
		    hideMode : 'offsets',
		    modal : true,
		    height : 250,
		    closeAction:'hide',
		    items : [importForm_bl]
		});
		importWindow_bl.on('show',function(upWindow){
			if(Ext.getCmp('littleup')){
				importForm_bl.remove(Ext.getCmp('littleup'));
			}
			importForm_bl.removeAll(true);
			importForm_bl.add(new Ext.form.TextField({
		        xtype : 'textfield',
		        id:'littleim',
		        name:'annexeName',
		        inputType:'file',
		        fieldLabel : '文件名称',
		        anchor : '90%'
		    }));
			importForm_bl.doLayout();
		});
		var progressBar = {};
		var importState = false;
		var progressWin = new Ext.Window({     
		    width : 300,
		    hideMode : 'offsets',
		    closable : true,
		    modal : true,
		    autoHeight : true,
		    closeAction:'hide',
		    items : [],
		    listeners :{
				'beforehide': function(){
					return importState;
				}
			}
		});
		function showProgressBar(count,curnum,bartext,msg,title) {
			importState = false;
			progressBar = new Ext.ProgressBar({width : 285 });
			progressBar.wait({
		        interval: 200,          	//每次更新的间隔周期
		        duration: 5000,             //进度条运作时候的长度，单位是毫秒
		        increment: 5,               //进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
		        fn: function () {           //当进度条完成主动更新后履行的回调函数。该函数没有参数。
					progressBar.reset();
		        }
		    });
			progressWin.removeAll();
			progressWin.setTitle(title);
			if (msg.length == 0) {
				msg = '正在导入...';
			}
			var importContext = new Ext.Panel({
										title: '',
										frame : true,
										region :'center',
										height : 100,
										width : '100%',
										autoScroll:true,
										html : '<span>'+ msg +'</span>'
									});
			progressWin.add(importContext);
			progressWin.add(progressBar);
			progressWin.doLayout();
			progressWin.show();
			
		}
		function showSuccessWin(curRecordNum) {
			importState = true;
			progressWin.removeAll();
			progressWin.setTitle("成功导入记录数为["+curRecordNum+"]");
			progressWin.add(new Ext.Panel({
				title:'',
				width:300,
				layout : 'fit',
				autoHeight : true,
				bodyStyle:'text-align:center',
				html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />'
			}));
			progressWin.doLayout();
			progressWin.show();
		}
