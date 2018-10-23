/**
 * @description 客户ATM跨境/境内免收手续费优惠信息
 * @author likai
 * @since 2014-09-09
 */

imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

Ext.QuickTips.init();//提示信息
var _custId;
var url = basepath + '/acrmACardUsed.json?custId='+_custId;
var comitUrl = basepath + '/acrmACardUsed!saveData.json?custId='+_custId;
var needCondition = false;
var needGrid = true;

var createView = true;
var editView = true;
var detailView = true;

var lookupTypes = ['CARD_TYPE'];

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
	{name:'ID', text:'主键ID', hidden:true},
 	{name: 'CUST_ID', text: '客户编号', dataType: 'string', hidden: true},
 	{name: 'CARD_TYPE', text: '持卡类别', dataType: 'string',translateType:'CARD_TYPE'},
 	{name: 'OUT_ATM', text: '境外ATM取款手续费优惠次数', xtype: 'numberfield', allowDecimals: false, allowNegative: false},
 	{name: 'OUT_USED', text: '境外已使用情况',  xtype: 'numberfield', allowDecimals: false, allowNegative: false},
 	{name: 'INNER_SAME_ATM', text: '境内同城跨行ATM取款手续费优惠次数', xtype: 'numberfield', allowDecimals: false, allowNegative: false},
	{name: 'INNER_USED', text: '境内同城跨行优惠已使用情况', xtype: 'numberfield', allowDecimals: false, allowNegative: false},
	{name: 'INNER_DIFF_ATM', text: '境内异地跨行ATM取款手续费优惠次数', xtype: 'numberfield', allowDecimals: false, allowNegative: false},
	{name: 'UPDATE_DATE', text: '更新时间', dataType: 'date', format: 'y-m-d'}
];

//新增面板
var createFormViewer =[{
	fields : ['CARD_TYPE','OUT_ATM','OUT_USED','INNER_SAME_ATM','INNER_USED','INNER_DIFF_ATM'],
		fn : function(CARD_TYPE,OUT_ATM,OUT_USED,INNER_SAME_ATM,INNER_USED,INNER_DIFF_ATM){
			return [CARD_TYPE,OUT_ATM,OUT_USED,INNER_SAME_ATM,INNER_USED,INNER_DIFF_ATM];
		}
	}];

//修改面板
var editFormViewer = [{
	fields : ['CARD_TYPE','OUT_ATM','OUT_USED','INNER_SAME_ATM','INNER_USED','INNER_DIFF_ATM'],
		fn : function(CARD_TYPE,OUT_ATM,OUT_USED,INNER_SAME_ATM,INNER_USED,INNER_DIFF_ATM){
			return [CARD_TYPE,OUT_ATM,OUT_USED,INNER_SAME_ATM,INNER_USED,INNER_DIFF_ATM];
		}
	}];

//详情面板
var detailFormViewer = [{
	fields : ['CARD_TYPE','OUT_ATM','OUT_USED','INNER_SAME_ATM','INNER_USED','INNER_DIFF_ATM'],
		fn : function(CARD_TYPE,OUT_ATM,OUT_USED,INNER_SAME_ATM,INNER_USED,INNER_DIFF_ATM){
			CARD_TYPE.readOnly = true;
			CARD_TYPE.cls = 'x-readOnly';
			OUT_ATM.readOnly = true;
			OUT_ATM.cls = 'x-readOnly';
			OUT_USED.readOnly = true;
			OUT_USED.cls = 'x-readOnly';
			INNER_SAME_ATM.readOnly = true;
			INNER_SAME_ATM.cls = 'x-readOnly';
			INNER_USED.readOnly = true;
			INNER_USED.cls = 'x-readOnly';
			INNER_DIFF_ATM.readOnly = true;
			INNER_DIFF_ATM.cls = 'x-readOnly';
			return [CARD_TYPE,OUT_ATM,OUT_USED,INNER_SAME_ATM,INNER_USED,INNER_DIFF_ATM];
		}
	}];

//用户扩展工具栏按钮
var tbar = [
	{
		text : '删除',
//		hidden : JsContext.checkGrant('_stockInfoDelete'),
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
				    	url : basepath + '/acrmACardUsed!batchDel.json',  
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
        formPanel : 'searchCondition',
        url :  basepath + '/acrmACardUsed.json?custId='+_custId
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