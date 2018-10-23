/**
*@description 客户反馈信息 .360客户视图
*@author: fan zheming
*@since: 2014-07-23
*/
imports([
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
//初始化提示框
Ext.QuickTips.init();
var _custId;
//获取
var url = basepath + '/ocrmFCiCustFeedbackInfo.json?custNo='+_custId;
//提交
var comitUrl = basepath + '/ocrmFCiCustFeedbackInfo!saveData.json?custNo='+_custId;

var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('privateFeedbackInfo_create');
var editView = !JsContext.checkGrant('privateFeedbackInfo_modify');
var detailView = !JsContext.checkGrant('privateFeedbackInfo_detail');

//本地数据字典

//var lookupTypes = ['FEEDBACK_T_TYPE',//反馈类型
//                   'FEEDBACK_C_TYPE'//反馈渠道
//                   ];
var localLookup={
      'FEEDBACK_T_TYPE' : [
	       {key : 'CPL', value : '投诉信息'},
	       {key : 'MOG', value : '意见信息'},
	       {key : 'ADV', value : '建议信息'},
	       {key : 'REQ', value : '咨询信息'},
	       {key : 'TKF', value : '表扬信息'},
	       {key : 'OTR', value : '其他'}
	    ],
      'FEEDBACK_C_TYPE' : [
	       {key : 'MAL', value : '邮件'},
	       {key : 'DUX', value : '短信'},
	       {key : 'WEX', value : '微信'}
	    ]
};

//SM-选择(复选)框
var sm = new Ext.grid.CheckboxSelectionModel();  
//行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

//fields
var fields = [
      {name: 'ID', text: '反馈编号', resutlWidth:150,hidden:true},
      {name: 'CUST_NO', text: '客户编号', resutlWidth:150,hidden:true},
      {name: 'FEEDBACK_TITLE', text: '反馈标题', resutlWidth:150, searchField: true},
      {name: 'FEEDBACK_TYPE', text: '反馈类型', resutlWidth:150, searchField: true, translateType:'FEEDBACK_T_TYPE'},
      {name: 'FEEDBACK_CONT', text: '反馈渠道', resutlWidth:150, searchField: true, translateType:'FEEDBACK_C_TYPE'},
      {name: 'FEEDBACK_NAIYOO', text: '反馈内容',  dataType:'textarea', height:50, resutlWidth:150, maxLengthText:20},
      {name: 'FEEDBACK_TIME', text: '反馈时间', dataType:'date', resutlWidth:150}
];

//新增面板
var createFormViewer =[{
	fields : ['CUST_NO','FEEDBACK_TITLE','FEEDBACK_TYPE','FEEDBACK_CONT','FEEDBACK_TIME'],
		fn : function(CUST_NO,FEEDBACK_TITLE,FEEDBACK_TYPE,FEEDBACK_CONT,FEEDBACK_TIME){
			return [CUST_NO,FEEDBACK_TITLE,FEEDBACK_TYPE,FEEDBACK_CONT,FEEDBACK_TIME];
		}
},{
	columnCount: 0.945,
	fields : ['FEEDBACK_NAIYOO'],
	    fn : function(FEEDBACK_NAIYOO){
	    	return [FEEDBACK_NAIYOO];
	    }
}];

//修改面板
var editFormViewer = [{
	fields : ['CUST_NO','FEEDBACK_TITLE','FEEDBACK_TYPE','FEEDBACK_CONT','FEEDBACK_TIME'],
	    fn : function(CUST_NO,FEEDBACK_TITLE,FEEDBACK_TYPE,FEEDBACK_CONT,FEEDBACK_TIME){
	    	return [CUST_NO,FEEDBACK_TITLE,FEEDBACK_TYPE,FEEDBACK_CONT,FEEDBACK_TIME];
	    }
},{
	columnCount: 0.945,
	fields : ['FEEDBACK_NAIYOO'],
	    fn : function(FEEDBACK_NAIYOO){
	    	return [FEEDBACK_NAIYOO];
	    }
}];

//详情面板
var detailFormViewer = [{
	fields : ['CUST_NO','FEEDBACK_TITLE','FEEDBACK_TYPE','FEEDBACK_CONT','FEEDBACK_TIME'],
	    fn : function(CUST_NO,FEEDBACK_TITLE,FEEDBACK_TYPE,FEEDBACK_CONT,FEEDBACK_TIME){
	    	FEEDBACK_TYPE.disabled = true;
	    	FEEDBACK_CONT.disabled = true;
	    	FEEDBACK_TIME.disabled = true;
	    	FEEDBACK_TITLE.disabled = true;
	    	return [CUST_NO,FEEDBACK_TITLE,FEEDBACK_TYPE,FEEDBACK_CONT,FEEDBACK_TIME];
	    }
},{
	columnCount: 0.945,
	fields : ['FEEDBACK_NAIYOO'],
	    fn : function(FEEDBACK_NAIYOO){
	    	FEEDBACK_NAIYOO.disabled = true;
	    	return [FEEDBACK_NAIYOO];
	    }
}];

//tbar
var tbar = [
	{
		text : '删除',
		hidden:JsContext.checkGrant('privateFeedbackInfo_delete'),
		handler : function(){
			if( getSelectedData() == false ){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return false;
					}
					var selectRecords = getAllSelects();
					var feedbackId = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							feedbackId = selectRecords[i].data.ID;
						}else{
							feedbackId +=","+ selectRecords[i].data.ID;
						}
					}
					Ext.Ajax.request({
				    	url : basepath + '/ocrmFCiCustFeedbackInfo!batchDel.json',  
				    	params : {
								feedbackId : feedbackId
							},
                        success : function(){
                            Ext.Msg.alert('提示', '删除成功');
                            reloadCurrentData();
                        },
                        failure : function(){
                            Ext.Msg.alert('提示', '删除失败');
                            reloadCurrentData();
                        }
                    });
				})
			}
		}
}
	,new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('privateCustFeedbackInfo_excel'),
        formPanel : 'searchCondition',
        url :  basepath + '/ocrmFCiCustFeedbackInfo.json?custNo='+_custId
    })
];

var beforeviewshow = function(view){
	if(view == getEditView()){
		if(getSelectedData()==false || getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据!');
			return false;
		}
	}
	if(view == getDetailView()){
		if(getSelectedData()==false || getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据!');
			return false;
		}
	}
};