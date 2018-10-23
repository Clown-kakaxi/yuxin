/**
 * 客户群视图   适合产品
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
     '/contents/pages/common/Com.yucheng.bcrm.common.CreateMktOppor.js'
//     '/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'  //客户群放大镜
 ]);
Ext.QuickTips.init();
var groupId=_busiId;//获取客户群ID

var url = basepath+'/GetGroupProd.json?base_id='+groupId;

var needCondition = false;

var fields = [{name:'CUST_ID',text:'客户编号'},
              {name:'CUST_NAME',text:'客户名称'},
              {name:'PROD_ID',text:'产品编号'},
              {name:'PROD_NAME',text:'产品名称'}];

var tbar = [{
	text:'创建营销活动',
	handler : function(){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		Ext.ScriptLoader.loadScript({
			scripts: [
				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',
				basepath+'/contents/pages/wlj/mktManage/mktActivityManager/mktAddFunction.js',
				basepath+'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
				basepath+'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
			],
		    finalCallback: function() {
		    	getActiveAddWindowShow(getSelectedData().data.CUST_ID,getSelectedData().data.PROD_ID,'',false,false,false,false,false,false);
		    }
		});
	}
},{
	text:'生成商机',
	handler : function(){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}else{
			var CUST_ID=getSelectedData().data.CUST_ID;
			var CUST_NAME=getSelectedData().data.CUST_NAME;
			var PROD_ID=getSelectedData().data.PROD_ID;
     	    var PROD_NAME=getSelectedData().data.PROD_NAME;
    		var createMktOppor = new Com.yucheng.bcrm.common.CreateMktOppor ({
    		iscust:true,
    		custId:CUST_ID,
    		height:500,
    		custName:CUST_NAME,
    		prodId:PROD_ID,
    		prodName:PROD_NAME});
			createMktOppor.show();
		}
	}
}];