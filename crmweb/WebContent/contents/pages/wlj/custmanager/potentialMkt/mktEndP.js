/**
 * @description 个金客户营销流程 - 个金结案信息信息页面
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
}];


var url = basepath + '/mktEndP.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT'];

var fields = [{name:'ID',text:'ID',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},
              {name:'DEPT_NAME',text:'营业单位名称',dataType:'string',searchField: true},
              {name:"IF_DEAL",text:"是否成交",translateType:"IF_FLAG",allowBlank:false,resutlWidth:'60',searchField:true},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT",allowBlank:false},
              {name:'BUY_AMT',text:'申购金额',dataType:'money'},
              {name: 'PRODUCT_ID',dataType:'string',text:"申购产品",gridField:false},
	          {name: 'PRODUCT_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,
	        	  text:"购买产品",searchField : true},
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'RM',text:'RM',dataType:'string',gridField:false},
              {name:"IF_HAS_PROD",text:"是否已有本行产品",translateType:"IF_FLAG",allowBlank:false,resutlWidth:'60'},
              {name:'DEAL_DATE',text:'成交日期',dataType:'date',gridField:false},
              {name:"ACCOUNT_DATE",text:'开户日期',dataType:'date',gridField:false},
              {name:'REFUSE_REASON',text:'拒绝原因',xtype:'textarea',gridField:false}
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
                url: basepath+'/mktEndP!batchDel.json?idStr='+ID,                                
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
		fields :['ID','CUST_NAME','AREA_NAME','DEPT_NAME','RM','PRODUCT_NAME','IF_HAS_PROD',
		         {name:"IF_DEAL",text:'是否成交',translateType:'IF_FLAG',listeners:{
		     			select:function(){
		     				var value = getCurrentView().contentPanel.form.findField("IF_DEAL").getValue();
		     				if(value == '1'){
		     					getCurrentView().contentPanel.form.findField("DEAL_DATE").show();
		     					getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").show();
		     					getCurrentView().contentPanel.form.findField("BUY_AMT").show();
		     					getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
		     				}else if(value == '0'){
		     					getCurrentView().contentPanel.form.findField("DEAL_DATE").hide();
		     					getCurrentView().contentPanel.form.findField("BUY_AMT").hide();
		     					getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").hide();
		     					getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
		     				}
		     			}
		     		}},'BUY_AMT','DEAL_DATE','ACCOUNT_DATE'],
			fn:function(ID,CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,IF_HAS_PROD,IF_DEAL,BUY_AMT,DEAL_DATE,ACCOUNT_DATE){
				ID.hidden = true;
				CUST_NAME.readOnly = true;
				AREA_NAME.readOnly = true;
				DEPT_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.cls = 'x-readOnly';
				PRODUCT_NAME.readOnly = true;
				PRODUCT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,IF_HAS_PROD,IF_DEAL,BUY_AMT,DEAL_DATE,ACCOUNT_DATE,ID];
			}
	},{
		   columnCount : 1,
		   fields:['REFUSE_REASON'],
		   fn:function(REFUSE_REASON){
			   return [REFUSE_REASON];
		   }
		}],
		formButtons : [{
			text:'提交',
			fn : function(formPanel,basicForm){
				 if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		           };
		           var value = getCurrentView().contentPanel.form.findField("IF_DEAL").getValue();
		           if(value == '1'){
		        	   if(getCurrentView().contentPanel.form.findField("DEAL_DATE").getValue() == ''
					   ||getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").getValue() == ''
						||getCurrentView().contentPanel.form.findField("BUY_AMT").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[申购金额],[成交日期]和[开户日期]');
			               return false;
		        	   }
					}else if(value == '0'){
						if(getCurrentView().contentPanel.form.findField("REFUSE_REASON").getValue() == ''){
			        		   Ext.MessageBox.alert('提示','请填写[拒绝理由]');
				               return false;
			        	   }
					}
		           var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data,1);
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktEndP!save.json',
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
		fields :['ID','CUST_NAME','AREA_NAME','DEPT_NAME','RM','PRODUCT_NAME','IF_HAS_PROD','IF_DEAL'
			,'BUY_AMT','DEAL_DATE','ACCOUNT_DATE'],
			fn:function(ID,CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,IF_HAS_PROD,IF_DEAL,BUY_AMT,DEAL_DATE,ACCOUNT_DATE){
				ID.hidden = true;
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'readOnly';
				RM.readOnly = true;
				RM.cls = 'readOnly';
				PRODUCT_NAME.readOnly = true;
				PRODUCT_NAME.cls = 'readOnly';
				IF_HAS_PROD.readOnly = true;
				IF_HAS_PROD.cls = 'readOnly';
				IF_DEAL.readOnly = true;
				IF_DEAL.cls = 'readOnly';
				BUY_AMT.readOnly = true;
				BUY_AMT.cls = 'readOnly';
				DEAL_DATE.readOnly = true;
				DEAL_DATE.cls = 'readOnly';
				ACCOUNT_DATE.readOnly = true;
				ACCOUNT_DATE.cls = 'readOnly';
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,IF_HAS_PROD,IF_DEAL,BUY_AMT,DEAL_DATE,ACCOUNT_DATE,ID];
			}
	},{
		   columnCount : 1,
		   fields:['REFUSE_REASON'],
		   fn:function(REFUSE_REASON){
			   return [REFUSE_REASON];
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

var viewshow = function(view){
	if(view._defaultTitle == '修改'){
		getCurrentView().contentPanel.form.findField("DEAL_DATE").hide();
		getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").hide();
		getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();	
		getCurrentView().contentPanel.form.findField("BUY_AMT").hide();
	}else if(view._defaultTitle == '查看'){
		var value = getSelectedData().data.IF_DEAL;
		if(value == '1'){
			getCurrentView().contentPanel.form.findField("DEAL_DATE").show();
			getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").show();
			getCurrentView().contentPanel.form.findField("BUY_AMT").show();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
		}else if(value == '0'){
			getCurrentView().contentPanel.form.findField("DEAL_DATE").hide();
			getCurrentView().contentPanel.form.findField("BUY_AMT").hide();
			getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").hide();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
		}		
	}
};
