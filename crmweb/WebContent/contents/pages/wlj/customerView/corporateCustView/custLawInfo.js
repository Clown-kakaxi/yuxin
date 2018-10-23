/**
 * @description 客户诉讼信息
 * @author likai
 * @since 2014-08-11
 */
 
imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]); 
 
Ext.QuickTips.init();//提示信息

var _custId;
var url = basepath + '/acrmFCiCustLawInfo.json?borrName='+_custId;
var comitUrl = basepath + '/acrmFCiCustLawInfo!saveData.json?borrName='+_custId;
var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('_lawInfoCreate');
var editView = !JsContext.checkGrant('_lawInfoEdit');
var detailView = !JsContext.checkGrant('_lawInfoDetail');

var lookupTypes = [
	'CDE0100028'
];

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'ID',hidden: true},
	{name: 'BORR_NAME', text: '客户编号', dataType: 'string', allowBlank: false, value: _custId},
	{name: 'CUST_NAME', text: '客户姓名', dataType: 'string', searchField: true, hidden: true},
	{name: 'OTH_PROSEC', text: '其他被起诉人', dataType: 'string', searchField: false},
	{name: 'MA_CASE_NO', text: '主案件编号', dataType: 'string',searchField: true, allowBlank: false},
	{name: 'LITI_STA', text: '诉讼阶段', translateType: 'CDE0100028', searchField: false},
	{name: 'ORI_BORR_AMT',text: '原借款金额(元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false, searchField: false},
    {name: 'BAL_AMT',text: '结欠金额(元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false},
    {name: 'RECORD_DATE',text: '登记日期', dataType: 'date',searchField: false}
];

//新增面板
var createFormViewer =[{
	fields : ['ID','BORR_NAME','OTH_PROSEC','MA_CASE_NO','LITI_STA','ORI_BORR_AMT','BAL_AMT'],
		fn : function(ID,BORR_NAME,OTH_PROSEC,MA_CASE_NO,LITI_STA,ORI_BORR_AMT,BAL_AMT){
			BORR_NAME.disabled = true;
			BORR_NAME.cls = 'x-readOnly';
			return [ID,BORR_NAME,OTH_PROSEC,MA_CASE_NO,LITI_STA,ORI_BORR_AMT,BAL_AMT];
		}
	}];

//修改面板
var editFormViewer = [{
	fields : ['ID','BORR_NAME','OTH_PROSEC','MA_CASE_NO','LITI_STA','ORI_BORR_AMT','BAL_AMT'],
		fn : function(ID,BORR_NAME,OTH_PROSEC,MA_CASE_NO,LITI_STA,ORI_BORR_AMT,BAL_AMT){
			BORR_NAME.disabled = true;
			BORR_NAME.cls = 'x-readOnly';
			return [ID,BORR_NAME,OTH_PROSEC,MA_CASE_NO,LITI_STA,ORI_BORR_AMT,BAL_AMT];
		}
	}];

//详情面板
var detailFormViewer = [{
	fields : ['BORR_NAME','OTH_PROSEC','MA_CASE_NO','LITI_STA','ORI_BORR_AMT','BAL_AMT','RECORD_DATE'],
		fn : function(BORR_NAME,OTH_PROSEC,MA_CASE_NO,LITI_STA,ORI_BORR_AMT,BAL_AMT,RECORD_DATE){
			BORR_NAME.disabled = true;
			OTH_PROSEC.disabled = true;
			MA_CASE_NO.disabled = true;
			LITI_STA.disabled = true;
			ORI_BORR_AMT.disabled = true;
			BAL_AMT.disabled = true;
			RECORD_DATE.disabled = true;
			BORR_NAME.cls = 'x-readOnly';
			OTH_PROSEC.cls = 'x-readOnly';
			MA_CASE_NO.cls = 'x-readOnly';
			LITI_STA.cls = 'x-readOnly';
			ORI_BORR_AMT.cls = 'x-readOnly';
			BAL_AMT.cls = 'x-readOnly';
			RECORD_DATE.cls = 'x-readOnly';
			return [BORR_NAME,OTH_PROSEC,MA_CASE_NO,LITI_STA,ORI_BORR_AMT,BAL_AMT,RECORD_DATE];
		}
	}];

//用户扩展工具栏按钮
var tbar = [
	{
		text : '删除',
		hidden : JsContext.checkGrant('_lawInfoDelete'),
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
					var id = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							id = selectRecords[i].data.ID;
						}else{
							id +=","+ selectRecords[i].data.ID;
						}
					}
				    Ext.Ajax.request({
				    	url : basepath + '/acrmFCiCustLawInfo!batchDel.json',  
				    	params : {
								id : id
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
		hidden:JsContext.checkGrant('_lawInfoExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/acrmFCiCustLawInfo.json?custId='+_custId
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
