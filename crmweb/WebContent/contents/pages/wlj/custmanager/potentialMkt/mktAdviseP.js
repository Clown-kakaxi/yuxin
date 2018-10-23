/**
 * @description 个金客户营销流程 - 个金产品建议书准备信息页面
 * @author luyy
 * @since 2014-07-24
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
	, '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
]);
//机构树加载条件
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
},{
	key : 'PROUDUCTLOADER',
	url : basepath + '/product-kinds1.json',//加载产品种类树
	parentAttr : 'CATL_PARENT',
	locateAttr : 'CATL_CODE',
	rootValue : '0',
	textField : 'CATL_NAME',
	idProperties : 'CATL_CODE',
	jsonRoot : 'json.data'
} ];


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
},{
	key : 'PROUDUCTTREE',
	loaderKey : 'PROUDUCTLOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		text : '银行产品树',
		autoScroll : true,
		children : []
	}
}];


var url = basepath + '/mktAdviseP.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','RISK_CHARACT'];

var fields = [{name:'ID',text:'ID',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},
              {name:'DEPT_NAME',text:'营业单位名称',dataType:'string',searchField: true},
              {name:'PRODUCT_CATL',text:'产品种类',dataType:'string',gridField:false},
//              {name:'PRODUCT_CATL_NAME',text:'产品种类',dataType:'string',gridField:false},
              {name:'PRODUCT_CATL_NAME',text:'产品种类',xtype : 'wcombotree',innerTree:'PROUDUCTTREE',
                  resutlWidth:80,searchField: true, showField:'text',hideField:'CATL_CODE',allowBlank:false},
              {name:'SALE_AMT',text:'预估销售金额',dataType:'money'},
			  {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT",allowBlank:false},
			  {name:"IF_THIRD_STEP",text:"是否进入第三阶段",translateType:"IF_FLAG",allowBlank:false},
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'PRODUCT_ID',text:'PRODUCT_ID',gridField:false},
              {name:'RM',text:'RM',dataType:'string',gridField:false},
              {name:'PRODUCT_NAME',text:'产品',gridField:false},
              {name:'RISK_LEVEL',text:'客户风险',translateType:'RISK_CHARACT',gridField:false,allowBlank:false}
			  ];

var tbar = [{
	text:'删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		var ID = '';
		for (var i=0;i<getAllSelects().length;i++){
			if(getAllSelects()[i].data.USER_ID != __userId || getAllSelects()[i].data.CHECK_STAT != '1'){
				Ext.Msg.alert('提示','只能选择本人[草稿]状态的信息！');
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
                url: basepath+'/mktAdviseP!batchDel.json?idStr='+ID,                                
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
}];

var customerView = [{
	title:'修改',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount:2,
		fields :['ID','CUST_NAME','AREA_NAME','DEPT_NAME','RM',
			{name:'PRODUCT_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,text:"预售产品",allowBlank:false,
			callback:function(b){
				getCurrentView().setValues({
					'PRODUCT_CATL':b[0].data.CATL_CODE,
					'PRODUCT_CATL_NAME':b[0].data.CATL_NAME
				});
			}}
			,'PRODUCT_CATL_NAME','SALE_AMT','RISK_LEVEL','IF_THIRD_STEP','PRODUCT_CATL'],
			fn:function(ID,CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,PRODUCT_CATL_NAME,SALE_AMT,RISK_LEVEL,IF_THIRD_STEP,PRODUCT_CATL){
				ID.hidden = true;
				CUST_NAME.readOnly = true;
				AREA_NAME.readOnly = true;
				DEPT_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				PRODUCT_CATL_NAME.readOnly = true;
				PRODUCT_CATL_NAME.cls = 'x-readOnly';
				PRODUCT_CATL.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,PRODUCT_CATL_NAME,SALE_AMT,RISK_LEVEL,IF_THIRD_STEP,ID,PRODUCT_CATL];
			}
	}],
		formButtons : [{
			text:'提交',
			fn : function(formPanel,basicForm){
				 if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		           };
		         var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data,1);
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktAdviseP!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									 var ret = Ext.decode(response.responseText);
										var instanceid = ret.instanceid;//流程实例ID
										var currNode = ret.currNode;//当前节点
										var nextNode = ret.nextNode;//下一步节点
										selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
								}
							}); 
		        	   
			}
		}]
},{
	title:'查看',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount:2,
		fields :['CUST_NAME','AREA_NAME','DEPT_NAME','RM','PRODUCT_NAME'
			,'PRODUCT_CATL_NAME','SALE_AMT','RISK_LEVEL','IF_THIRD_STEP'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,PRODUCT_CATL_NAME,SALE_AMT,RISK_LEVEL,IF_THIRD_STEP){
				CUST_NAME.readOnly = true;
				AREA_NAME.readOnly = true;
				DEPT_NAME.readOnly = true;
				RM.readOnly = true;
				PRODUCT_NAME.readOnly = true;
				PRODUCT_CATL_NAME.readOnly = true;
				SALE_AMT.readOnly = true;
				RISK_LEVEL.readOnly = true;
				IF_THIRD_STEP.readOnly = true;
				
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.cls = 'x-readOnly';
				RM.cls = 'x-readOnly';
				PRODUCT_NAME.cls = 'x-readOnly';
				PRODUCT_CATL_NAME.cls = 'x-readOnly';
				SALE_AMT.cls = 'x-readOnly';
				RISK_LEVEL.cls = 'x-readOnly';
				IF_THIRD_STEP.cls = 'x-readOnly';
				
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,PRODUCT_CATL_NAME,SALE_AMT,RISK_LEVEL,IF_THIRD_STEP];
			}
	}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.CHECK_STAT != '1'){
			Ext.Msg.alert('提示','只能修改[草稿]状态的信息');
			return false;
		}
	}
};
