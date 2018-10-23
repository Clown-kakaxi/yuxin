/**
 * 礼品领取页面
 * @author luyy
 * @since 2014-06-11
 */
imports([
			'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
			'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
			'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
			'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
			'/contents/pages/common/Com.yucheng.crm.common.Goods.js'
		]);

var goodsStore = new Ext.data.Store({
	restful:true,	
    proxy : new Ext.data.HttpProxy(
    		{
    			url:basepath+'/ocrmFSeGoods.json'
    		}),
    reader: new Ext.data.JsonReader({
    	root : 'json.data'
    }, [{name:'ID'},
        {name:'GOODS_NAME'},
    	{name:'GOODS_NUMBER'},
    	{name:'CREATE_DATE'},
    	{name:'COMP_ACTI'}]
)
});
goodsStore.load();	
var scoreStore = new Ext.data.Store({
	restful:true,	
    proxy : new Ext.data.HttpProxy(
    		{
    			url:basepath+'/ocrmFSeScore.json?right=no'
    		}),
    reader: new Ext.data.JsonReader({
    	root : 'json.data'
    }, [{name:'ID'},
        {name:'CUST_ID'},
    	{name:'SCORE_TOTAL'},
    	{name:'SCORE_TODEL'}]
)
});
scoreStore.load();	

var needOld = 0;//当前领取所需积分，在修改页面使用，用以记录所需积分修改前的数值
var createView = !JsContext.checkGrant("_goodsHisManagerCreate");
var editView = !JsContext.checkGrant("_goodsHisManagerEdit");
var detailView = !JsContext.checkGrant("_goodsHisManagerDetail");
WLJUTIL.alwaysLockCurrentView = true;

var url = basepath+'/ocrmFSeGoodsHis.json';

var comitUrl = basepath+'/ocrmFSeGoodsHis!save.json';


var lookupTypes = ['GIVE_STATE',{
	TYPE : 'COMP_ACTI',
	url : '/ocrmFSeGoods!searchActi.json?type=search',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
},{
	TYPE : 'ORG_ID',
	url : '/ocrmFSeGoods!searchOrg.json',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
}];

var condition = {
		searchType : 'SUBTREE' // 查询子机构
	};
	var treeLoaders = [ {
		key : 'DATASETMANAGERLOADER',
		url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
		parentAttr : 'SUPERUNITID',
		locateAttr : 'UNITID',
		jsonRoot : 'json.data',
		rootValue : JsContext._orgId,
		textField : 'UNITNAME',
		idProperties : 'UNITID'
	} ];
var treeCfgs = [ {
	key : 'DATASETMANAGERTREE1',
	loaderKey : 'DATASETMANAGERLOADER',
	autoScroll : true,
	rootCfg : {
		expanded:true,
		id:JsContext._orgId,
		text:JsContext._unitname,
		autoScroll:true,
		children:[],
		UNITID:JsContext._orgId,
		UNITNAME:JsContext._unitname
	}
} ];

var fields = [  {name: 'ID',hidden : true},
                {name:'CUST_ID',text:'客户编号',dataType:'string',resutlWidth:100,allowBlank:false},
	  		    {name:'CUST_NAME',text:'客户名称',dataType:'string'},
	  		    {name:'SCORE_TOTAL',text:'客户可用积分',dataType:'numberNoDot',hidden:'true'},
	  		    {name:'SCORE_TODEL',text:'客户锁定积分',dataType:'numberNoDot',hidden:'true'},
	  		    {name:'ORG_ID',text:'单位名称',hidden:'true'},
	  		    {name: 'ORG_NAME',text: '单位名称',xtype : 'wcombotree',innerTree : 'DATASETMANAGERTREE1'
	            	,showField : 'UNITNAME',hideField : 'UNITID',hiddenName : 'ORG_NAME',searchField : true},
				{name:'OPARTER_ID',text:'',dataType:'string',hidden:true},
				{name:'CREATE_NAME',text:'操作人',dataType:'string',resutlWidth:100},
				{name:'COMP_ACTI',text:'配合活动',translateType : 'COMP_ACTI',	searchField : true,allowBlank:false},
				{name:'GOODS_ID',hidden:true},
	  		    {name:'GOODS_NAME',text:'礼品名称',dataType:'string',searchField : true,allowBlank:false},
	  		    {name:'GOODS_NUMBER',text:'礼品库存',resutlWidth:80,decimalPrecision :0 ,dataType:'numberNoDot'},
	  		    {name:'GIVE_NUMBER',text:'赠送数量',resutlWidth:80,decimalPrecision :0 ,dataType:'numberNoDot',allowBlank:false},
	  		    {name:'GIVE_STATE',text:'复核状态',translateType : 'GIVE_STATE',allowBlank:false},
	  		    {name:'NEED_SCORE',text:'所需积分',resutlWidth:80,decimalPrecision :0 ,dataType:'numberNoDot',allowBlank:false},
	  		    {name:'GIVE_DATE',text:'赠送日期',dataType:'date'},
	  		    {name:'GIVE_REASON',text:'赠送原因',xtype:'textarea'},
	  		    {name:'REMARK',text:'备注',xtype:'textarea'},
	  		    {name:'CREATE_DATE',text:'创建日期',dataType:'date'}
				
	  		];
var tbar = [{
	text:'删除',
	hidden: JsContext.checkGrant("_goodsHisManagerDelete"),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.OPARTER_ID != __userId || getAllSelects()[i].data.GIVE_STATE != '1'){
					Ext.Msg.alert('提示','只能选择本人发起的[草稿]状态的礼品领取记录！');
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
                    url: basepath+'/ocrmFSeGoodsHis!batchDel.json?idStr='+ID,                                
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

var createFormViewer =[{
	fields : ['ID','ORG_ID','OPARTER_ID','COMP_ACTI','GOODS_NUMBER','CREATE_DATE','GIVE_NUMBER','GIVE_DATE',
	          'NEED_SCORE','GIVE_STATE','SCORE_TOTAL','SCORE_TODEL'],
	fn : function(ID,ORG_ID,OPARTER_ID,COMP_ACTI,GOODS_NUMBER,CREATE_DATE,GIVE_NUMBER,GIVE_DATE,NEED_SCORE,GIVE_STATE,SCORE_TOTAL,SCORE_TODEL	){
		GIVE_DATE.value = new Date().format('Y-m-d');
		GIVE_STATE.value = '1';
		GIVE_STATE.hidden = true;
		OPARTER_ID.value = __userId;
		CREATE_DATE.hidden = true;
		ORG_ID.value = __units;
		GOODS_NUMBER.readOnly = true;
		SCORE_TOTAL.readOnly = true;
		SCORE_TODEL.readOnly = true;
		CREATE_DATE.readOnly = true;
		GOODS_NUMBER.cls='x-readOnly' ;
		SCORE_TOTAL.cls='x-readOnly' ;
		SCORE_TODEL.cls='x-readOnly' ;
		CREATE_DATE.cls='x-readOnly' ;
		OPARTER_ID.hidden = true;
		ORG_ID.hidden = true;
		var GOODS_NAME = new Com.yucheng.crm.common.Goods({
			fieldLabel : '礼品名称',
			text:'礼品名称',
			allowBlank : false,
			name : 'GOODS_NAME',
			singleSelected : true,// 单选复选标志
			editable : false,
			hiddenName : 'GOODS_ID',
			callback:function(b){
				getCurrentView().setValues({
					GOODS_NUMBER:b[0].data.GOODS_NUMBER,
					CREATE_DATE:b[0].data.CREATE_DATE
				});
			}
		});
		var cust = new Com.yucheng.bcrm.common.CustomerQueryField({
					fieldLabel : '客户名称',
					text:'客户名称',
					allowBlank : false,
					name : 'CUST_NAME',
					singleSelected : true,// 单选复选标志
					editable : false,
					hiddenName : 'CUST_ID',
					callback:function(a,b){
						scoreStore.each(function(e){
							if(e.get('CUST_ID') == b[0].data.CUST_ID){
								getCurrentView().setValues({
									 SCORE_TOTAL: e.get('SCORE_TOTAL'),
									 SCORE_TODEL: e.get('SCORE_TODEL')
								});
								return false;
							}
						});
					}
				});
		return [cust,SCORE_TOTAL,SCORE_TODEL,ORG_ID,OPARTER_ID,COMP_ACTI,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GIVE_NUMBER,GIVE_DATE,NEED_SCORE,GIVE_STATE,ID];
	}
},{
	columnCount : 1 ,
	fields : ['GIVE_REASON','REMARK'],
	fn : function(GIVE_REASON,REMARK){
		return [GIVE_REASON,REMARK];
	}
}];

var editFormViewer =[{
	fields : ['ID','CUST_ID','CUST_NAME','ORG_ID','OPARTER_ID','COMP_ACTI','GOODS_NUMBER','CREATE_DATE',
	          'GIVE_NUMBER','GIVE_DATE','NEED_SCORE','GIVE_STATE','SCORE_TOTAL','SCORE_TODEL'],
	fn : function(ID,CUST_ID,CUST_NAME,ORG_ID,OPARTER_ID,COMP_ACTI,GOODS_NUMBER,CREATE_DATE,GIVE_NUMBER,GIVE_DATE,
			NEED_SCORE,GIVE_STATE,SCORE_TOTAL,SCORE_TODEL	){
		OPARTER_ID.hidden = true;
		ORG_ID.hidden = true;
		CREATE_DATE.hidden = true;
		GIVE_STATE.hidden = true;
		CUST_ID.readOnly = true;
		CUST_NAME.readOnly = true;
		SCORE_TOTAL.readOnly = true;
		SCORE_TODEL.readOnly = true;
		GOODS_NUMBER.readOnly = true;
		CREATE_DATE.readOnly = true;
		CUST_ID.cls='x-readOnly' ;
		CUST_NAME.cls='x-readOnly' ;
		SCORE_TOTAL.cls='x-readOnly' ;
		SCORE_TODEL.cls='x-readOnly' ;
		GOODS_NUMBER.cls='x-readOnly' ;
		CREATE_DATE.cls='x-readOnly' ;
		var GOODS_NAME = new Com.yucheng.crm.common.Goods({
			fieldLabel : '礼品名称',
			text:'礼品名称',
			allowBlank : false,
			name : 'GOODS_NAME',
			singleSelected : true,// 单选复选标志
			editable : false,
			hiddenName : 'GOODS_ID',
			callback:function(b){
				getCurrentView().setValues({
					GOODS_NUMBER:b[0].data.GOODS_NUMBER,
					CREATE_DATE:b[0].data.CREATE_DATE
				});
			}
		});
		return [CUST_ID,CUST_NAME,SCORE_TOTAL,SCORE_TODEL,ORG_ID,OPARTER_ID,COMP_ACTI,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GIVE_NUMBER,GIVE_DATE,NEED_SCORE,GIVE_STATE,ID];
	}
},{
	columnCount : 1 ,
	fields : ['GIVE_REASON','REMARK'],
	fn : function(GIVE_REASON,REMARK){
		return [GIVE_REASON,REMARK];
	}
}];

var detailFormViewer =[{
	fields : ['CUST_ID','CUST_NAME','ORG_ID','OPARTER_ID','COMP_ACTI','GOODS_NAME','GOODS_NUMBER','CREATE_DATE','GIVE_NUMBER','GIVE_DATE','NEED_SCORE','GIVE_STATE','GOODS_ID'],
	fn : function(CUST_ID,CUST_NAME,ORG_ID,OPARTER_ID,COMP_ACTI,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GIVE_NUMBER,GIVE_DATE,NEED_SCORE,GIVE_STATE,GOODS_ID	){
		CUST_ID.disabled = true;
		CUST_NAME.disabled = true;
		ORG_ID.disabled = true;
		OPARTER_ID.disabled = true;
		COMP_ACTI.disabled = true;
		GOODS_NAME.disabled = true;
		GOODS_NUMBER.disabled = true;
		CREATE_DATE.disabled = true;
		GIVE_NUMBER.disabled = true;
		GIVE_DATE.disabled = true;
		NEED_SCORE.disabled = true;
		GIVE_STATE.disabled = true;
		GOODS_ID.disabled = true;
		CUST_ID.cls = 'x-readOnly';
		CUST_NAME.cls = 'x-readOnly';
		ORG_ID.cls = 'x-readOnly';
		OPARTER_ID.cls = 'x-readOnly';
		COMP_ACTI.cls = 'x-readOnly';
		GOODS_NAME.cls = 'x-readOnly';
		GOODS_NUMBER.cls = 'x-readOnly';
		CREATE_DATE.cls = 'x-readOnly';
		GIVE_NUMBER.cls = 'x-readOnly';
		GIVE_DATE.cls = 'x-readOnly';
		NEED_SCORE.cls = 'x-readOnly';
		GIVE_STATE.cls = 'x-readOnly';
		GOODS_ID.cls = 'x-readOnly';
		return [CUST_ID,CUST_NAME,ORG_ID,OPARTER_ID,COMP_ACTI,GOODS_NAME,GOODS_NUMBER,CREATE_DATE,GIVE_NUMBER,GIVE_DATE,NEED_SCORE,GIVE_STATE,GOODS_ID];
	}
},{
	columnCount : 1 ,
	fields : ['GIVE_REASON','REMARK'],
	fn : function(GIVE_REASON,REMARK){
		GIVE_REASON.disabled = true;
		GIVE_REASON.cls = 'x-readOnly';
		REMARK.disabled = true;
		REMARK.cls = 'x-readOnly';
		return [GIVE_REASON,REMARK];
	}
}];

//根据所选活动，填入礼品信息
var linkages = {
//		COMP_ACTI:{
//			fields : ['GOODS_ID','GOODS_NAME','GOODS_NUMBER','CREATE_DATE'],
//			fn : function(COMP_ACTI,GOODS_ID,GOODS_NAME,GOODS_NUMBER,CREATE_DATE){
//				if(goodsStore.getCount() != 0 && getCurrentView() != getDetailView()){
//					goodsStore.each(function(e){
//						if(e.get('COMP_ACTI') == COMP_ACTI.getValue()){
//							getCurrentView().setValues({
//								 GOODS_ID: e.get('ID'),
//								 GOODS_NAME: e.get('GOODS_NAME'),
//								 GOODS_NUMBER: e.get('GOODS_NUMBER'),
//								 CREATE_DATE: e.get('CREATE_DATE')
//							});
//							return false;
//						}
//					});
//				}
//			}},
			NEED_SCORE:{
				fields : ['SCORE_TOTAL','SCORE_TODEL'],
				fn : function(NEED_SCORE,SCORE_TOTAL,SCORE_TODEL){
					if(getCurrentView() == getEditView()||getCurrentView() == getCreateView()){
						//客户实际可用积分 = 可用积分 + 修改前所需积分（新增第一次保存时值为0，后与修改同） - 锁定积分
						if(Number(NEED_SCORE.value)>(Number(SCORE_TOTAL.value)+needOld-Number(SCORE_TODEL.value))){
							Ext.Msg.alert('提示', '客户积分不足!');	
							getCurrentView().setValues({
										 NEED_SCORE: ''
									});
						}
					}
			}
			}
};

var createFormCfgs = {
		formButtons : [{
			text : '提交',
			fn : function(formPanel, basicForm){
				if(formPanel.getForm().getValues().ID == ''||formPanel.getForm().getValues().ID == undefined){
					Ext.Msg.alert('提示','请先执行保存操作！');
					return false;
				}else{
					 Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
						url : basepath + '/ocrmFSeGoodsHis!initFlow.json',
						method : 'GET',
						params : {
							instanceid:formPanel.getForm().getValues().ID, //将id传给后台关联流程的实例号（唯一）
							name:formPanel.getForm().getValues().CUST_NAME
						},
						waitMsg : '正在提交申请,请等待...',										
						success : function(response) {
//							Ext.Ajax.request({
//								url : basepath + '/ocrmFSeGoodsHis!initFlowJob.json',
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
							
							var ret = Ext.decode(response.responseText);
							var instanceid = ret.instanceid;//流程实例ID
							var currNode = ret.currNode;//当前节点
							var nextNode = ret.nextNode;//下一步节点
							selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
							reloadCurrentData();
							hideCurrentView();
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
					url : basepath + '/ocrmFSeGoodsHis!initFlow.json',
					method : 'GET',
					params : {
						instanceid:formPanel.getForm().getValues().ID, //将id传给后台关联流程的实例号（唯一）
						name:formPanel.getForm().getValues().CUST_NAME
					},
					waitMsg : '正在提交申请,请等待...',										
					success : function(response) {
//						Ext.Ajax.request({
//							url : basepath + '/ocrmFSeGoodsHis!initFlowJob.json',
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
		}]
	};

var beforeviewshow = function(view){
	if(view == getDetailView()|| view == getEditView()){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}	
			needOld = 0;//新增时设置为0
	}
	if(view == getEditView()){
		if(getSelectedData().data.GIVE_STATE != '1'){
			Ext.Msg.alert('提示','只有[草稿]状态的申请记录才能修改');
			return false;
		}
		needOld = Number(getSelectedData().data.NEED_SCORE);
	}
};	

var beforecommit = function(data){
	if(typeof data.CREATE_DATE === 'string'){
		data.CREATE_DATE = dateTransFn(data.CREATE_DATE);
	}
};

var dateTransFn = function(str){
	var date = Date.parseDate(str, "Y-m-d");
	if(!date){
		try{
			date = new Date();
			var l = Date.parse(str);
			date.setTime(l);
		}catch(e){
			date = "";
		}
	}
	return date;
};

//返回保存记录的id
var afertcommit = function(){
	var todelold = Number(getCurrentView().contentPanel.getForm().getValues().SCORE_TODEL);
	var needNew = Number(getCurrentView().contentPanel.getForm().getValues().NEED_SCORE);
	if(getCurrentView() == getCreateView()){
		getCurrentView().setValues({
			SCORE_TODEL:todelold-needOld+needNew //修改锁定分数
	    });
		needOld = needNew;
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
	if(getCurrentView() == getEditView()){
		getCurrentView().setValues({
			SCORE_TODEL:todelold-needOld+needNew//修改锁定分数
	    });
		needOld = needNew;
	}
	scoreStore.reload();
};


