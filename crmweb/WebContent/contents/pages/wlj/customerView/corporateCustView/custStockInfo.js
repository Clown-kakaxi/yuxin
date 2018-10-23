/**
 * @description 客户股票信息
 * @author likai
 * @since 2014-07-23
 */

imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

Ext.QuickTips.init();//提示信息
var _custId;
var url = basepath + '/acrmFCiOrgIssuestock.json?custId='+_custId;
var comitUrl = basepath + '/acrmFCiOrgIssuestock!saveData.json?custId='+_custId;
var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('_stockInfoCreate');
var editView = !JsContext.checkGrant('_stockInfoEdit');
var detailView = !JsContext.checkGrant('_stockInfoDetail');

var lookupTypes = [
	'XD000322',
	'XD000231'
];


// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'ISSUE_STOCK_ID',hidden: true},
 	{name: 'FLOW_STOCK_NUM', text: '流通股数', dataType: 'decimal', viewFn: money('0,000.00'), allowDecimals: false,minValue: 0,hidden: true},
 	{name: 'CURR_STOCK_NUM', text: '当前股本总量', dataType: 'decimal', viewFn: money('0,000.00'), allowDecimals: false,minValue: 0,hidden: true},
 	{name: 'NETASSET_PER_SHARE', text: '每股净资产', dataType: 'decimal', viewFn: money('0,000.00'), minValue: 0,hidden: true},
 	{name: 'ONCF', text: '每股净现金流量',  dataType: 'decimal', viewFn: money('0,000.00'), minValue: 0,hidden: true},
 	{name: 'REMARK', text: '备注', xtype: 'textarea', maxLength: 200, height: 60, hidden: true},
	{name: 'STOCK_TYPE', text: '股票类型', translateType: 'XD000231', searchField: true, allowBlank: false},
	{name: 'STOCK_CODE', text: '股票代码', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'STOCK_NAME', text: '股票名称', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'STOCK_STATE', text: '股票状态', translateType: 'XD000322', searchField: false},
	{name: 'MARKET_PLACE',text: '上市地', dataType: 'string', searchField: true},
    {name: 'IPO_DATE',text: '上市日期', dataType: 'date', searchField: false},
    {name: 'ALLOTMENT_SHARE_AMT',text: '当年增发配股额', dataType: 'decimal', viewFn: money('0,000.00'), allowDecimals: false,minValue: 0,searchField: false}
];

//新增面板
var createFormViewer =[{
	fields : ['STOCK_TYPE','STOCK_CODE','STOCK_NAME','STOCK_STATE','IPO_DATE','FLOW_STOCK_NUM','CURR_STOCK_NUM','NETASSET_PER_SHARE','ONCF','ALLOTMENT_SHARE_AMT','MARKET_PLACE'],
		fn : function(STOCK_TYPE,STOCK_CODE,STOCK_NAME,STOCK_STATE,IPO_DATE,FLOW_STOCK_NUM,CURR_STOCK_NUM,NETASSET_PER_SHARE,ONCF,ALLOTMENT_SHARE_AMT,MARKET_PLACE){
			FLOW_STOCK_NUM.hidden = false;
			CURR_STOCK_NUM.hidden = false;
			NETASSET_PER_SHARE.hidden = false;
			ONCF.hidden = false;
			return [STOCK_TYPE,STOCK_CODE,STOCK_NAME,STOCK_STATE,IPO_DATE,FLOW_STOCK_NUM,CURR_STOCK_NUM,NETASSET_PER_SHARE,ONCF,ALLOTMENT_SHARE_AMT,MARKET_PLACE];
		}
	},{
		columnCount : 0.945 ,
		fields : ['REMARK'],
		fn : function(REMARK){
			REMARK.hidden = false;
			return [REMARK];
		}
}];

//修改面板
var editFormViewer = [{
	fields : ['STOCK_TYPE','STOCK_CODE','STOCK_NAME','STOCK_STATE','IPO_DATE','FLOW_STOCK_NUM','CURR_STOCK_NUM','NETASSET_PER_SHARE','ONCF','ALLOTMENT_SHARE_AMT','MARKET_PLACE'],
		fn : function(STOCK_TYPE,STOCK_CODE,STOCK_NAME,STOCK_STATE,IPO_DATE,FLOW_STOCK_NUM,CURR_STOCK_NUM,NETASSET_PER_SHARE,ONCF,ALLOTMENT_SHARE_AMT,MARKET_PLACE){
			FLOW_STOCK_NUM.hidden = false;
			CURR_STOCK_NUM.hidden = false;
			NETASSET_PER_SHARE.hidden = false;
			ONCF.hidden = false;
			return [STOCK_TYPE,STOCK_CODE,STOCK_NAME,STOCK_STATE,IPO_DATE,FLOW_STOCK_NUM,CURR_STOCK_NUM,NETASSET_PER_SHARE,ONCF,ALLOTMENT_SHARE_AMT,MARKET_PLACE];
		}
	},{
		columnCount : 0.945 ,
		fields : ['REMARK'],
		fn : function(REMARK){
			REMARK.hidden = false;
			return [REMARK];
		}
}];

//详情面板
var detailFormViewer = [{
	fields : ['STOCK_TYPE','STOCK_CODE','STOCK_NAME','STOCK_STATE','IPO_DATE','FLOW_STOCK_NUM','CURR_STOCK_NUM','NETASSET_PER_SHARE','ONCF','ALLOTMENT_SHARE_AMT','MARKET_PLACE'],
		fn : function(STOCK_TYPE,STOCK_CODE,STOCK_NAME,STOCK_STATEIPO_DATE,FLOW_STOCK_NUM,CURR_STOCK_NUM,NETASSET_PER_SHARE,ONCF,ALLOTMENT_SHARE_AMT,MARKET_PLACE){
			FLOW_STOCK_NUM.hidden = false;
			CURR_STOCK_NUM.hidden = false;
			NETASSET_PER_SHARE.hidden = false;
			ONCF.hidden = false;
			STOCK_TYPE.disabled = true;
			STOCK_CODE.disabled = true;
			STOCK_NAME.disabled = true;
			STOCK_STATE.disabled = true;
			IPO_DATE.disabled = true;
			FLOW_STOCK_NUM.disabled = true;
			CURR_STOCK_NUM.disabled = true;
			NETASSET_PER_SHARE.disabled = true;
			ONCF.disabled = true;
			ALLOTMENT_SHARE_AMT.disabled = true;
			MARKET_PLACE.disabled = true;
			STOCK_TYPE.cls = 'x-readOnly';
			STOCK_CODE.cls = 'x-readOnly';
			STOCK_NAME.cls = 'x-readOnly';
			STOCK_STATE.cls = 'x-readOnly';
			IPO_DATE.cls = 'x-readOnly';
			FLOW_STOCK_NUM.cls = 'x-readOnly';
			CURR_STOCK_NUM.cls = 'x-readOnly';
			NETASSET_PER_SHARE.cls = 'x-readOnly';
			ONCF.cls = 'x-readOnly';
			ALLOTMENT_SHARE_AMT.cls = 'x-readOnly';
			MARKET_PLACE.cls = 'x-readOnly';
			return [STOCK_TYPE,STOCK_CODE,STOCK_NAME,STOCK_STATE,IPO_DATE,FLOW_STOCK_NUM,CURR_STOCK_NUM,NETASSET_PER_SHARE,ONCF,ALLOTMENT_SHARE_AMT,MARKET_PLACE];
		}
	},{
		columnCount : 0.945 ,
		fields : ['REMARK'],
		fn : function(REMARK){
			REMARK.hidden = false;
			REMARK.disabled = true;
			REMARK.cls = 'x-readOnly';
			return [REMARK];
		}
}];

//用户扩展工具栏按钮
var tbar = [
	{
		text : '删除',
		hidden : JsContext.checkGrant('_stockInfoDelete'),
		handler : function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return false;
					} 
					var selectRecords = getAllSelects();
					var issueStockId = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							issueStockId = selectRecords[i].data.ISSUE_STOCK_ID;
						}else{
							issueStockId +=","+ selectRecords[i].data.ISSUE_STOCK_ID;
						}
					}
				    Ext.Ajax.request({
				    	url : basepath + '/acrmFCiOrgIssuestock!batchDel.json',  
				    	params : {
								issueStockId : issueStockId
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
			});
		}
	}
	},
	//导出按钮
	new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('_stockInfoExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/acrmFCiOrgIssuestock.json?custId='+_custId
    })
];


var beforeviewshow=function(view){
	if(view == getEditView() || view == getDetailView()){
		if(getSelectedData() == false || getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	
	}
}
