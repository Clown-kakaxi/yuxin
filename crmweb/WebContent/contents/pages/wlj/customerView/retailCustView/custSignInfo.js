/**
 * @description 对私客户签约信息
 * @author likai
 * @since 2014-07-25
 */
 
Ext.QuickTips.init();//提示信息

var _custId;
var url = basepath + '/ocrmFCiContractInfo.json?custId='+_custId;;

var detailView = true;

   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'ID',hidden: true},
    {name: 'CUST_ID',text: '客户编号', dataType: 'string', searchField: false, hidden: true},
	{name: 'SIGN_NAME', text: '签约名称', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'SIGN_ORG', text: '签约机构', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'SIGN_DATE', text: '签约日期', dataType: 'date', searchField: true, allowBlank: false},
	{name: 'SIGN_CHANEL', text: '签约渠道', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'SIGN_END_DATE',text: '签约到期日期', dataType: 'date', searchField: false},
    {name: 'OGR_NAME',text: '机构名称', dataType: 'string', searchField: false},
    {name: 'ATTN',text: '经办人', dataType: 'string', searchField: false},
    {name: 'SIGN_STS',text: '签约状态', dataType: 'string', searchField: false}
    
];



//详情面板
var detailFormViewer = [{
	fields : ['SIGN_NAME','SIGN_ORG','SIGN_DATE','SIGN_CHANEL','SIGN_END_DATE','OGR_NAME','ATTN','SIGN_STS'],
	fn : function(SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS){
		SIGN_NAME.disabled = true;
		SIGN_ORG.disabled = true;
		SIGN_DATE.disabled = true;
		SIGN_CHANEL.disabled = true;
		SIGN_END_DATE.disabled = true;
        OGR_NAME.disabled = true;
        ATTN.disabled = true;
		SIGN_STS.disabled = true;
		return [SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS];
	}
},{/**附件信息**/
	columnCount:1,
	fields:['TEST'],
	fn:function(TEST){
		detailAnna = createAnnGrid(true,false,true,false);
		return [detailAnna];
	}
}];

var beforeviewshow=function(view){
	if(view == getDetailView()){
		if(getSelectedData() == false || getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	
	}
}