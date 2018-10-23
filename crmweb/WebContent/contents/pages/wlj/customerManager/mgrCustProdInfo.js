/**
 * 
* @Description: 客户经理视图-客户持有产品
* @author geyu 
* @date 2014-7-8 
*
 */
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
]);

var url=basepath+"/mgrCustProdInfoQuery.json?mgrId="+_busiId;
var comitUrl=false;
var needCondition = false;

var viewId = "";//产品视图展示方案
var custId = "";

var fields=[
     {name:'CUST_ID',text:'客户编号', dataType:'string'},
     {name:'CUST_NAME',text:'客户名称', dataType:'string'}
];

var beforeviewshow = function(view){
	if(view._defaultTitle=='客户持有产品信息'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		window.CUSTVIEW = {};
		window.CUSTVIEW.CURRENT_VIEW_URL = '/contents/pages/wlj/custmanager/custView/poroductInformation.js';
		var url = builtfunctionurl(window.CUSTVIEW.CURRENT_VIEW_URL,getSelectedData().data.CUST_ID);
		document.getElementById('viewport_center').src = url;
	}
}
var builtfunctionurl = function(baseUrl,tempCustId){
	var url = false;
	if(baseUrl.indexOf('.jsp') < 0 ){
		url = basepath + '/contents/frameControllers/view/Wlj-view-function.jsp';
	}else{
		url = basepath + baseUrl.split('.jsp')[0]+'.jsp';
	}
	var turl = baseUrl.indexOf('?')>=0 ? (baseUrl + '&resId=view$-$0$-$'+ tempCustId ): (baseUrl + '?resId=view$-$0$-$'+tempCustId) ;
	url += '?' + turl.split('?')[1] + '&custId='+tempCustId+'&viewResId=101515';
	return url;
};
var customerView = [{
	title: '客户持有产品信息',
	hideTitle: false,
	suspendFitAll:true,
	items : [{
		html:'<iframe id="viewport_center" name="viewport_center" src="" style="width:100%;height:100%;" frameborder="no"/>'
	}]
}];
