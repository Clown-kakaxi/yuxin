/**
 * @description 客户债券信息
 * @author likai
 * @since 2014-07-23
 */
 
imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
 
Ext.QuickTips.init();//提示信息

var _custId;
var url = basepath + '/acrmFCiOrgIssuebond.json?custId='+_custId;
var comitUrl = basepath + '/acrmFCiOrgIssuebond!saveData.json?custId='+_custId;
var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('_bondInfoCreate');
var editView = !JsContext.checkGrant('_bondInfoEdit');
var detailView = !JsContext.checkGrant('_bondInfoDetail');
//var createView = true;
//var editView = true;
//var detailView = true;

var lookupTypes=[
     'XD000343',
     'XD000226',
     'XD000323',
     'XD000234'
];

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'ISSUE_BOND_ID',hidden: true},
	{name: 'BOND_TERM', text: '债券期限(年)', dataType: 'number', allowDecimals: false, allowNegative: false,hidden: true},
	{name: 'REMARK', text: '备注', xtype: 'textarea', maxLength: 200,height: 60, hidden: true},
	{name: 'BOND_TYPE', text: '债券类型', translateType: 'XD000234', searchField: true, allowBlank: false},
	{name: 'BOND_CODE', text: '债券代码', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'BOND_NAME', text: '债券名称', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'BOND_STATE', text: '债券状态', translateType: 'XD000323', searchField: false},
	{name: 'IS_MARKET',text: '是否上市', translateType: 'XD000343', gridField: false, searchField: true},
    {name: 'BOND_CURR',text: '币种', dataType: 'string', translateType: 'XD000226'},
    {name: 'ISSUE_AMT',text: '发行金额(元)', dataType: 'number', allowNegative: false,searchField: false},
    {name: 'BOND_INTR',text: '利率规定', dataType: 'textarea', maxLength: 200,searchField: false}
];

//新增面板
var createFormViewer =[{
	fields : ['BOND_NAME','BOND_CURR','BOND_TYPE','BOND_STATE','ISSUE_AMT','BOND_CODE','BOND_INTR','BOND_TERM','IS_MARKET'],
		fn : function(BOND_NAME,BOND_CURR,BOND_TYPE,BOND_STATE,ISSUE_AMT,BOND_CODE,BOND_INTR,BOND_TERM,IS_MARKET){
			BOND_TERM.hidden = false;
			return [BOND_NAME,BOND_CURR,BOND_TYPE,BOND_STATE,ISSUE_AMT,BOND_CODE,BOND_INTR,BOND_TERM,IS_MARKET];
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
	fields : ['BOND_NAME','BOND_CURR','BOND_TYPE','BOND_STATE','ISSUE_AMT','BOND_CODE','BOND_INTR','BOND_TERM','IS_MARKET'],
		fn : function(BOND_NAME,BOND_CURR,BOND_TYPE,BOND_STATE,ISSUE_AMT,BOND_CODE,BOND_INTR,BOND_TERM,IS_MARKET){
			BOND_TERM.hidden = false;
			return [BOND_NAME,BOND_CURR,BOND_TYPE,BOND_STATE,ISSUE_AMT,BOND_CODE,BOND_INTR,BOND_TERM,IS_MARKET];
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
	fields : ['BOND_NAME','BOND_CURR','BOND_TYPE','BOND_STATE','ISSUE_AMT','BOND_CODE','BOND_INTR','BOND_TERM','IS_MARKET'],
		fn : function(BOND_NAME,BOND_CURR,BOND_TYPE,BOND_STATE,ISSUE_AMT,BOND_CODE,BOND_INTR,BOND_TERM,IS_MARKET){
			BOND_TERM.hidden = false;
			BOND_NAME.disabled = true;
			BOND_CURR.disabled = true;
			BOND_TYPE.disabled = true;
			BOND_STATE.disabled = true;
			ISSUE_AMT.disabled = true;
			BOND_CODE.disabled = true;
			BOND_INTR.disabled = true;
			BOND_TERM.disabled = true;
			IS_MARKET.disabled = true;
			BOND_NAME.cls = 'x-readOnly';
			BOND_CURR.cls = 'x-readOnly';
			BOND_TYPE.cls = 'x-readOnly';
			BOND_STATE.cls = 'x-readOnly';
			ISSUE_AMT.cls = 'x-readOnly';
			BOND_CODE.cls = 'x-readOnly';
			BOND_INTR.cls = 'x-readOnly';
			BOND_TERM.cls = 'x-readOnly';
			IS_MARKET.cls = 'x-readOnly';
			return [BOND_NAME,BOND_CURR,BOND_TYPE,BOND_STATE,ISSUE_AMT,BOND_CODE,BOND_INTR,BOND_TERM,IS_MARKET];
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
		hidden : JsContext.checkGrant('_bondInfoDelete'),
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
					var issueBondId = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							issueBondId = selectRecords[i].data.ISSUE_BOND_ID;
						}else{
							issueBondId +=","+ selectRecords[i].data.ISSUE_BOND_ID;
						}
					}
				    Ext.Ajax.request({
				    	url : basepath + '/acrmFCiOrgIssuebond!batchDel.json',  
				    	params : {
								issueBondId : issueBondId
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
		hidden:JsContext.checkGrant('_bondInfoExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/acrmFCiOrgIssuebond.json?custId='+_custId
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
