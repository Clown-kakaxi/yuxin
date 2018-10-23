/**
 * 礼品管理页面
 * @author luyy
 * @since 2014-06-10
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js'
]);
 
var createView = !JsContext.checkGrant("_goodsManagerCreate");
var editView = !JsContext.checkGrant("_goodsManagerEdit");
var detailView = !JsContext.checkGrant("_goodsManagerDetail");
WLJUTIL.alwaysLockCurrentView = true;

var url = basepath+'/ocrmFSeGoods.json';


var lookupTypes = ['GOODS_STATE','GOODS_CUST_LEVEL',{
	TYPE : 'COMP_ACTI',//查询是使用：活动状态为结束和执行中
	url : '/ocrmFSeGoods!searchActi.json?type=search',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
},{
	TYPE : 'COMP_ACTI_NEW',//新增和修改时使用，活动状态为执行中
	url : '/ocrmFSeGoods!searchActi.json?type=new',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
}
//,{
//	TYPE : 'ORG_ID',
//	url : '/ocrmFSeGoods!searchOrg.json',
//	key : 'KEY',
//	value : 'VALUE',
//	root : 'data'
//}
];

// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};

//加载机构树
var treeLoaders = [ {
	key : 'ORGTREELOADER',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
}];


//树配置
var treeCfgs = [ {
	key : 'ORGTREE',
	loaderKey : 'ORGTREELOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		id : JsContext._orgId,
		text : JsContext._unitname,
		autoScroll : true,
		children : [],
		UNITID : JsContext._orgId,
		UNITNAME : JsContext._unitname
	}
} ];

var fields = [ {name: 'ID',hidden : true},
	  		    {name:'GOODS_NAME',text:'礼品名称',dataType:'string',	searchField : true,allowBlank:false},
	  		    {name:'GOODS_NUMBER',text:'礼品库存',resutlWidth:80,dataType:'numberNoDot',allowBlank:false},
	  		    {name:'GOODS_PRICE',text:'礼品人民币单价',resutlWidth:80,decimalPrecision : 2 ,dataType:'number',allowBlank:false},
	  		    {name:'GOODS_SCORE',text:'礼品积分单价',resutlWidth:80,dataType:'numberNoDot',allowBlank:false},
	  		    {name:'COMP_ACTI',text:'配合活动',translateType : 'COMP_ACTI',	searchField : true,allowBlank:false},
				{name:'CUST_LEVEL',text:'赠送客户等级',translateType : 'GOODS_CUST_LEVEL',	searchField : true,allowBlank:false,multiSelect:true},
	  		    {name:'GOODS_STATE',text:'礼品状态',translateType : 'GOODS_STATE',allowBlank:false},
				{name:'CREATE_DATE',text:'创建日期',dataType:'date'},
    			{name:'ORG_ID',text:'单位名称',xtype : 'wcombotree',innerTree:'ORGTREE',resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',editable:false},
//				{name:'ORG_ID',text:'单位名称',translateType : 'ORG_ID',	searchField : true},
				{name:'CREATE_ID',text:'',dataType:'string',hidden:true},
				{name:'CREATE_NAME',text:'创建人',dataType:'string'}
	  		];


var createFormViewer =[{
	fields : ['ID','GOODS_NAME','GOODS_NUMBER','CREATE_DATE','GOODS_STATE','COMP_ACTI','CUST_LEVEL','GOODS_PRICE','GOODS_SCORE','CREATE_ID','ORG_ID'],
	fn : function(ID,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GOODS_STATE,COMP_ACTI,CUST_LEVEL,GOODS_PRICE,GOODS_SCORE,CREATE_ID,ORG_ID	){
			CREATE_DATE.value = new Date().format('Y-m-d');
			COMP_ACTI.translateType = 'COMP_ACTI_NEW';
			GOODS_STATE.value = '1';
			CREATE_ID.value = __userId;
			ORG_ID.value = __units;
			CREATE_ID.hidden = true;
			ORG_ID.hidden = true;
			CREATE_DATE.hidden = true;
			GOODS_STATE.hidden =true;
		return [GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GOODS_STATE,COMP_ACTI,CUST_LEVEL,GOODS_PRICE,GOODS_SCORE,ID,CREATE_ID,ORG_ID];
	}
}];

var editFormViewer =[{
	fields : ['ID','GOODS_NAME','GOODS_NUMBER','CREATE_DATE','GOODS_STATE','COMP_ACTI','CUST_LEVEL','GOODS_PRICE','GOODS_SCORE','CREATE_ID','ORG_ID'],
	fn : function(ID,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GOODS_STATE,COMP_ACTI,CUST_LEVEL,GOODS_PRICE,GOODS_SCORE,CREATE_ID,ORG_ID	){
		CREATE_ID.hidden = true;
		ORG_ID.hidden = true;
		CREATE_DATE.hidden = true;
		GOODS_STATE.hidden =true;
		return [GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GOODS_STATE,COMP_ACTI,CUST_LEVEL,GOODS_PRICE,GOODS_SCORE,ID,CREATE_ID,ORG_ID];
	}
}];

var detailFormViewer =[{
	fields : ['ID','GOODS_NAME','GOODS_NUMBER','CREATE_DATE','GOODS_STATE','COMP_ACTI','CUST_LEVEL','GOODS_PRICE','GOODS_SCORE'],
	fn : function(ID,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GOODS_STATE,COMP_ACTI,CUST_LEVEL,GOODS_PRICE,GOODS_SCORE	){
		GOODS_NAME.disabled = true;
		GOODS_NUMBER.disabled = true;
		CREATE_DATE.disabled = true;
		GOODS_STATE.disabled = true;
		COMP_ACTI.disabled = true;
		CUST_LEVEL.disabled = true;
		GOODS_PRICE.disabled = true;
		GOODS_SCORE.disabled = true;
		GOODS_NAME.cls = 'x-readOnly';
		GOODS_NUMBER.cls = 'x-readOnly';
		CREATE_DATE.cls = 'x-readOnly';
		GOODS_STATE.cls = 'x-readOnly';
		COMP_ACTI.cls = 'x-readOnly';
		CUST_LEVEL.cls = 'x-readOnly';
		GOODS_PRICE.cls = 'x-readOnly';
		GOODS_SCORE.cls = 'x-readOnly';
		return [GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GOODS_STATE,COMP_ACTI,CUST_LEVEL,GOODS_PRICE,GOODS_SCORE,ID];
	}
}];

var createFormCfgs = {
		formButtons : [{
			text : '提交',
			hidden: JsContext.checkGrant("_goodsManagerDelete"),
			fn : function(formPanel, basicForm){
				if(formPanel.getForm().getValues().ID == ''||formPanel.getForm().getValues().ID == undefined){
					Ext.Msg.alert('提示','请先执行保存操作！');
					return false;
				}else{
					 Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
						url : basepath + '/ocrmFSeGoods!initFlow.json',
						method : 'GET',
						params : {
							instanceid:formPanel.getForm().getValues().ID, //将id传给后台关联流程的实例号（唯一）
							name:formPanel.getForm().getValues().GOODS_NAME
						},
						waitMsg : '正在提交申请,请等待...',										
						success : function(response) {
//							Ext.Ajax.request({
//								url : basepath + '/ocrmFSeGoods!initFlowJob.json',
//								method : 'POST',
//								params : {
//									instanceid:formPanel.getForm().getValues().ID //将id传给后台关联流程的实例号（唯一）
//								},success : function() {
//									Ext.Msg.alert('提示', '提交成功!');	
//									reloadCurrentData();
//									hideCurrentView();
//								},	
//								failure : function() {
//									Ext.Msg.alert('提示', '提交失败,请手动到代办任务中提交!');	
//								}
//							});
							reloadCurrentData();
							hideCurrentView();
							var ret = Ext.decode(response.responseText);
							var instanceid = ret.instanceid;//流程实例ID
							var currNode = ret.currNode;//当前节点
							var nextNode = ret.nextNode;//下一步节点
							selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							reloadCurrentData();
						}
					});
				}
			
			}
		}]
	};

var editFormCfgs = {
		formButtons : [{
			text : '提交',
			fn : function(formPanel, basicForm){
				 Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
					url : basepath + '/ocrmFSeGoods!initFlow.json',
					method : 'GET',
					params : {
						instanceid:formPanel.getForm().getValues().ID, //将id传给后台关联流程的实例号（唯一）
						name:formPanel.getForm().getValues().GOODS_NAME
					},
					waitMsg : '正在提交申请,请等待...',										
					success : function(response) {
//						Ext.Ajax.request({
//							url : basepath + '/ocrmFSeGoods!initFlowJob.json',
//							method : 'POST',
//							params : {
//								instanceid:formPanel.getForm().getValues().ID //将id传给后台关联流程的实例号（唯一）
//							},success : function() {
//								Ext.Msg.alert('提示', '提交成功!');	
//								reloadCurrentData();
//								hideCurrentView();
//							},	
//							failure : function() {
//								Ext.Msg.alert('提示', '提交失败,请手动到代办任务中提交!');	
//							}
//						});
						reloadCurrentData();
						hideCurrentView();
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
						Ext.Msg.alert('提示', '提交成功!');	
						
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						reloadCurrentData();
					}
				});
			}
		}]
	};

var tbar = [{
	text:'删除',
	hidden: JsContext.checkGrant("_goodsManagerDelete"),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.CREATE_ID != __userId || getAllSelects()[i].data.GOODS_STATE != '1'){
					Ext.Msg.alert('提示','只能选择本人创建的[草稿]状态的礼品！');
					return false;
				}
				ID += getAllSelects()[i].data.ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/ocrmFSeGoods!batchDel.json?idStr='+ID,                                
                    success : function(){
                        Ext.Msg.alert('提示', '删除成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                        reloadCurrentData();
                    }
                });
			});			
		}
	}
}];
var beforeviewshow = function(view){
	if(view == getDetailView()|| view == getEditView()){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}	
	}
	if(view == getEditView()){
		if(getSelectedData().data.GOODS_STATE != '1'){
			Ext.Msg.alert('提示','只有[草稿]状态的礼品才能修改');
			return false;
		}
	}
};	

//返回保存记录的id
var afertcommit = function(){
	if(getCurrentView() == getCreateView()){
		Ext.Ajax.request({
			url : basepath+'/session-info!getPid.json',
			method : 'GET',
			success : function(a,b,v) {
			    var noticeIdStr = Ext.decode(a.responseText).pid;
			    getCurrentView().setValues({
			    	ID:noticeIdStr
			    });
			}
		});
	}
//	if(getCurrentView()._defaultTitle=='新增'){
//		hideCurrentView();
//	}
};


