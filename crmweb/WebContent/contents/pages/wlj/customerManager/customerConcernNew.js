/**
  * @description 我关注的客户
  * @author likai
  * @since 2014/08/16
  *
  */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
//	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

Ext.QuickTips.init();//提示信息

var url = basepath + '/custConcernDel.json';

var lookupTypes = [
     'XD000080',
     'XD000081',
     'FXQ_RISK_LEVEL',
     'XD000040'//,
//     'SHARE_FLAG',
//     'CUSTOMER_SOURCE_TYPE',
//     'GROUP_MEMEBER_TYPE',
//     'CUSTOMER_GROUP_TYPE'
];

//var localLookup = {
//	'ADD_WAY' : [
//	   {key : '1',value : '加入已有客户群'},
//	   {key : '2',value : '新建客户群'}
//	]
//};

var fields = [
	{name: 'ID', hidden: true},
    {name:'CUST_ID',text:'客户编号', resutlWidth:100, dataType: 'string', searchField: true},  
    {name:'CUST_NAME',text : '客户名称', resutlWidth:200, dataType: 'string', searchField: true},  
	{name:'CUST_TYPE',text : '客户类型', resutlWidth:70, translateType: 'XD000080'},
	{name:'CUST_STAT',text : '客户状态', resutlWidth:70, translateType: 'XD000081'},
	{name:'IDENT_TYPE',text : '证件类型', translateType: 'XD000040'},
	{name:'IDENT_NO', resutlWidth:120, text : '证件号码'},
	{name:'IDENT_EXPIRED_DATE', resutlWidth:100, text : '证件到期日'},
	{name:'IDENT_TYPE2',text : '证件类型2', translateType: 'XD000040'},
	{name:'IDENT_NO2', resutlWidth:120, text : '证件号码2'},
	{name:'IDENT_EXPIRED_DATE2', resutlWidth:100, text : '证件2到期日'},
	{name:'FXQ_RISK_LEVEL',text:'反洗钱风险等级',resutlWidth:100,translateType:'FXQ_RISK_LEVEL',gridField:true,searchField: true}
];


var tbar = [
	{
		text : '取消关注',
		hidden : JsContext.checkGrant('_custConcernDelete'),
		handler : function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				Ext.MessageBox.confirm('提示','确定取消关注吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return false;
					} 
					var selectRecords = getAllSelects();
					var id = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							id = selectRecords[i].data.ID;
						}else{
							id +=","+ selectRecords[i].data.ID;
						}
					}
				    Ext.Ajax.request({
				    	url : basepath + '/custConcernDel!batchDel.json',  
				    	params : {
								id : id
							},
                        success : function(){
                            Ext.Msg.alert('提示', '取消关注成功');
                            reloadCurrentData();
                        },
                        failure : function(){
                            Ext.Msg.alert('提示', '取消关注失败');
                            reloadCurrentData();
                        }
                    });
				});
			}
		}
	},{
		text:'客户视图',
		hidden : JsContext.checkGrant('_custConcernView'),
		handler:function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择数据！');
				return false;
			}
			var tempId = getSelectedData().data.CUST_ID;
			var str = tempId.substr(0, 3);
			if (getSelectedData().length > 1) {
				Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
				return;
			} else if (str == "crm") {
				Ext.Msg.alert('提示', '该用户为临时用户');
				return;
			}
			var custId = getSelectedData().data.CUST_ID;
			var custName = getSelectedData().data.CUST_NAME;
			parent.Wlj.ViewMgr.openViewWindow(0,custId,custName);
		}
	},
	//导出按钮
	new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('_custConcernExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/custConcernDel.json'
    })
];


