/**
 * @description 对公客户他行信息
 * @author likai
 * @since 2014-07-25
 */
 
imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
 
Ext.QuickTips.init();//提示信息
var _custId;
var url = basepath + '/ocrmFCiOtherBank.json?custId='+_custId;
var comitUrl = basepath + '/ocrmFCiOtherBank!saveData.json?custId='+_custId;
var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('_otherBankInfoCreate');
var editView = !JsContext.checkGrant('_otherBankInfoEdit');
var detailView = !JsContext.checkGrant('_otherBankInfoDetail');

var lookupTypes=[
     'XD000156'
];


// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'MXTID', text:'ID',hidden: true},
 	{name: 'PRD_USE', text: '产品使用情况', dataType: 'string', hidden: true},
 	{name: 'HZYEARS', text: '合作年限', xtype: 'numberfield',allowDecimals: false, allowNegative: false,searchField: false},
 	{name: 'IS_BASIC_BANK', text: '是否基本开户行', dataType: 'string', translateType: 'XD000156', hidden: true},
 	{name: 'UPDT_DT', text: '维护日期',  dataType: 'date', hidden: true},
 	{name: 'INPUT_DT', text: '录入时间',  dataType: 'date', hidden: true},
 	{name: 'USERNAME', text: '维护人员姓名',  dataType: 'string', hidden: true},
 	{name: 'USERID', text: '维护人员',  dataType: 'string', hidden: true},
 	{name: 'REMARK', text: '备注', xtype: 'textarea', maxLength: 200, height: 60, hidden: true},
	{name: 'INSTN_NAME', text: '金融机构名称', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'CRED_AMT', text: '授信总额(万元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false,searchField: false},
	{name: 'CRED_LIMIT', text: '授信期限', xtype: 'numberfield', allowDecimals: false, allowNegative: false,searchField: false},
	{name: 'LON_VAL',text: '贷款时点余额(万元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false,searchField: false},
    {name: 'PERIODCIAL_VAL',text: '定期存款时点余额(万元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false,searchField: false},
    {name: 'CURRENT_VAL',text: '活期存款时点余额(万元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false,searchField: false},
    {name: 'BAIL_VAL',text: '保证金存款时点余额(万元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false,searchField: false}
];

//新增面板
var createFormViewer =[{
//	columnCount : 1,
	fields : ['INSTN_NAME','IS_BASIC_BANK','HZYEARS','PRD_USE','CURRENT_VAL','PERIODCIAL_VAL','BAIL_VAL','LON_VAL','CRED_AMT','CRED_LIMIT'],
		fn : function(INSTN_NAME,IS_BASIC_BANK,HZYEARS,PRD_USE,CURRENT_VAL,PERIODCIAL_VAL,BAIL_VAL,LON_VAL,CRED_AMT,CRED_LIMIT){
			PRD_USE.hidden = false;
			HZYEARS.hidden = false;
			IS_BASIC_BANK.hidden = false;
			return [INSTN_NAME,IS_BASIC_BANK,HZYEARS,PRD_USE,CURRENT_VAL,PERIODCIAL_VAL,BAIL_VAL,LON_VAL,CRED_AMT,CRED_LIMIT];
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
	fields : ['MXTID','INSTN_NAME','IS_BASIC_BANK','HZYEARS','PRD_USE','CURRENT_VAL','PERIODCIAL_VAL','BAIL_VAL','LON_VAL','CRED_AMT','CRED_LIMIT'],
		fn : function(MXTID,INSTN_NAME,IS_BASIC_BANK,HZYEARS,PRD_USE,CURRENT_VAL,PERIODCIAL_VAL,BAIL_VAL,LON_VAL,CRED_AMT,CRED_LIMIT){
			PRD_USE.hidden = false;
			HZYEARS.hidden = false;
			IS_BASIC_BANK.hidden = false;
			return [MXTID,INSTN_NAME,IS_BASIC_BANK,HZYEARS,PRD_USE,CURRENT_VAL,PERIODCIAL_VAL,BAIL_VAL,LON_VAL,CRED_AMT,CRED_LIMIT];
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
	fields : ['INSTN_NAME','IS_BASIC_BANK','HZYEARS','PRD_USE','CURRENT_VAL','PERIODCIAL_VAL','BAIL_VAL','LON_VAL','CRED_AMT','CRED_LIMIT'],
		fn : function(INSTN_NAME,IS_BASIC_BANK,HZYEARS,PRD_USE,CURRENT_VAL,PERIODCIAL_VAL,BAIL_VAL,LON_VAL,CRED_AMT,CRED_LIMIT){
			PRD_USE.hidden = false;
			HZYEARS.hidden = false;
			IS_BASIC_BANK.hidden = false;
			INSTN_NAME.disabled = true;
			CURRENT_VAL.disabled = true;
			HZYEARS.disabled = true;
			PERIODCIAL_VAL.disabled = true;
			IS_BASIC_BANK.disabled = true;
			BAIL_VAL.disabled = true;
			CRED_LIMIT.disabled = true;
			LON_VAL.disabled = true;
			PRD_USE.disabled = true;
			CRED_AMT.disabled = true;
			INSTN_NAME.cls = 'x-readOnly';
			CURRENT_VAL.cls = 'x-readOnly';
			HZYEARS.cls = 'x-readOnly';
			PERIODCIAL_VAL.cls = 'x-readOnly';
			IS_BASIC_BANK.cls = 'x-readOnly';
			BAIL_VAL.cls = 'x-readOnly';
			CRED_LIMIT.cls = 'x-readOnly';
			LON_VAL.cls = 'x-readOnly';
			PRD_USE.cls = 'x-readOnly';
			CRED_AMT.cls = 'x-readOnly';
			return [INSTN_NAME,IS_BASIC_BANK,HZYEARS,PRD_USE,CURRENT_VAL,PERIODCIAL_VAL,BAIL_VAL,LON_VAL,CRED_AMT,CRED_LIMIT];
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
		hidden : JsContext.checkGrant('_otherBankInfoDelete'),
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
					var mxtid = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							mxtid = selectRecords[i].data.MXTID;
						}else{
							mxtid +=","+ selectRecords[i].data.MXTID;
						}
					}
				    Ext.Ajax.request({
				    	url : basepath + '/ocrmFCiOtherBank!batchDel.json',  
				    	params : {
								mxtid : mxtid
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
		hidden:JsContext.checkGrant('_otherBankInfoExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/ocrmFCiOtherBank.json?custId='+_custId
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
