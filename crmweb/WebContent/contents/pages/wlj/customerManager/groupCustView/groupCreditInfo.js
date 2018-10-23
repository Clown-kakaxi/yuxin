
Ext.QuickTips.init();
var lookupTypes=[
                 'XD000106',
                 'XD000226'
         ];
var needGrid = true;
var needCondition=false;
//WLJUTIL.suspendViews=false;  //自定义面板是否浮动
//_busiId集团客户编号
var id=_busiId;
//使用到的字段
var url=basepath + '/groupCustomer!searchBasicInfo.json?id='+id;
var fields = [
     {name:'GROUP_NO',text:'集团编号',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'GROUP_TYPE',text:'集团类型',translateType:'XD000106',readOnly:true,disabled:true,resutlWidth:80,cls:'x-readOnly'},
     {name:'GROUP_NAME',text:'集团名称',resutlWidth:100,disabled:true,cls:'x-readOnly'},
	 {name:'CREDIT_CUR',text:'授信币种',translateType:'XD000226',resutlWidth:80,disabled:true,cls:'x-readOnly'},
	 {name:'CREDIT_AMT',text:'授信总额',resutlWidth:80,disabled:true,cls:'x-readOnly'},
	 {name:'USED_AMT',text:'用信总额',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'LOAN_BALANCE',text:'贷款余额',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'BAD_LOAN_BALANCE',text:'不良贷款余额',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'GUARANTEE_TYPE',text:'担保方式',resutlWidth:80,disabled:true,cls:'x-readOnly'}
];
