/**
*@description 授信信息（公私）
*/

imports( [
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);
	
var _custId;
var lookupTypes=[
	'XD000226',//币种代码
	'IF_FLAG'
]

var needCondition=false;//取消查询面板
var createView=false;
var editView=false;

var url=basepath+'/AcrmFCiCreContractNew.json?CustId='+_custId;

var fields=[
			{name:'CUST_ID',text:'客户编号',xtype:'textfield',resutlWidth:120,gridField:false},
			{name:'LIMIT_NO',text:'额度编号',xtype:'textfield',resutlWidth:100},
			{name: 'PRODUCT', text : '额度名称',xtype:'textfield',resutlWidth:100}, 
			{name:'CURRENCY',text:'币种',translateType:'XD000226',resutlWidth:100},
			{name:'CRD_AMT',text:'授信金额',viewFn:money('0,000.00'),xtype:'textfield',resutlWidth:100},
            {name:'ENABLE_AMT',text:'启用金额',viewFn:money('0,000.00'),xtype:'textfield',resutlWidth:100},
            {name:'USE_AMT',text:'使用金额',viewFn:money('0,000.00'),xtype:'textfield',resutlWidth:100},
            {name:'USE_RATE1',text:'动用率1(%)(使用金额/授信金额)',xtype:'textfield',resutlWidth:200},
            {name:'USE_RATE2',text:'动用率2(%)(使用金额/启用金额)',xtype:'textfield',resutlWidth:200},
            {name:'FLAG1',text:'是否合控',translateType:'IF_FLAG',resutlWidth:100},
            {name:'SUB_LIMIT_ID',text:'子额度编号',xtype:'textfield',resutlWidth:120,gridField:false},
            {name:'BAK1',text:'标识位',translateType:'IF_FLAG',resutlWidth:120,gridField:false}
            ];


var customerView = [{
	title:'子额度占用状态表',
	type:'grid',
	url : basepath + '/AcrmFCiCreContractLmtSt.json',
	fields : {
		fields:[
			{name: 'CREDIT_NO', text : '授信流水号'},
			{name: 'OCCUR_CURRENCY',renderer:function(value){
				var val = translateLookupByKey("XD000226",value);
				return val?val:"";
				}, text : '动拨币种'},
			{name: 'DUE_BILL_NO', text : '台账流水号'},
			{name: 'OCCUR_AMT', text : '动拨金额',renderer:money('0,0.00')},
			{name: 'SUB_LIMIT_ID', text : '子额度编号'},
			{name: 'LMT_STATUE', text : '占用状态'}
			
			
		]
	}
},{
	title:'子额度起始日',
	type:'grid',
	url : basepath + '/AcrmFCiCreContractDate.json',
	fields : {
		fields:[
			{name: 'CREDIT_NO', text : '授信流水号'},
			{name: 'SUB_LIMIT',text : '子额度'},  
			{name: 'CONT_START_DT', text : '合同起始日'}, 
			{name: 'CONT_END_DT', text : '合同到期日'},
			{name: 'LIMIT_START_DT', text : '额度起始日'},  
			{name: 'LIMIT_END_DT', text : '额度到期日'},
			{name: 'SUB_LIMIT_ID', text : '子额度编号'}
			
		]
	}
}]

beforeviewshow = function(theView){
	if(theView._defaultTitle == '子额度起始日'){
		if(!getSelectedData()||getSelectedData().data.PRODUCT == ''){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择有额度名称数据的记录进行操作！');
			return false;
		}
		theView.setParameters({
			'SubLimitId':getSelectedData().data.SUB_LIMIT_ID
		});
	}
	if(theView._defaultTitle == '子额度占用状态表'){
		if(!getSelectedData()||getSelectedData().data.PRODUCT == ''){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择有额度名称数据的记录进行操作！');
			return false;
		}
		theView.setParameters(
			{
			'SubLimitId':getSelectedData().data.SUB_LIMIT_ID
		});
	} 
}