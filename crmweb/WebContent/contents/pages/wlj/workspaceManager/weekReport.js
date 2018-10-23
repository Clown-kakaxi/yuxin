/**
*@description  周工作报告页面
*@author:luyy
*@since:2014-06-25
*@checkedby:
*/

var name = '';//报告人
var cycle = '';//周期

var url = basepath+'/reportManger.json?type=week';

var lookupTypes = ['REPORT_STAT','WORK_REPORT_BUSI_TYPE'];

var fields = [
	{name:'REPORT_ID',hidden:true},
	{name:'WORK_REPORT_BUSI_TYPE',text:'报告业务类型',translateType:'WORK_REPORT_BUSI_TYPE',searchField:true},
	{name:'REPORTER_ID',text:'报告人ID',dataType:'string'},
	{name:'REPORTER_NAME',text:'报告人名称',dataType:'string',searchField:true},
	{name:'REPORTER_ORG',text:'报告人机构编号',dataType:'string'},
	{name:'REPORTER_ORG_NAME',text:'报告人机构名称',dataType:'string'},
	{name:'REPORTER_CYCLE',text:'报告周期',dataType:'string'},
	{name:'REPORT_DATE',text:'报告生成日期',dataType:'date'},
	{name:'REPORT_STAT',text:'报告状态',translateType:'REPORT_STAT',searchField:true},
	{name:'ID',hidden:true},
	{name:'REPORT_SUB1',hidden:true},
	{name:'REPORT_SUB2',hidden:true},
	{name:'REPORT_SUB3',hidden:true},
	{name:'REPORT_SUB4',hidden:true},
	{name:'REPORT_SUB5',hidden:true},
	{name:'REPORT_SUB6',hidden:true},
	{name:'REPORT_SUB7',hidden:true},
	{name:'REPORT_SUB8',hidden:true},
	{name:'REPORT_SUB9',hidden:true},
	{name:'REPORT_SUB10',hidden:true},
	{name:'REPORT_SUB11',hidden:true},
	{name:'REPORT_SUB12',hidden:true},
	{name:'REPORT_SUB13',hidden:true},
	{name:'REPORT_SUB14',hidden:true},
	{name:'REPORT_SUB15',hidden:true},
	{name:'REPORT_SUB16',hidden:true},
	{name:'REPORT_SUB17',hidden:true},
	{name:'REPORT_SUB18',hidden:true}
];

var createView = false;
var editView = false;
var detailView = false;

var tbar = [{
	text:'查看报告信息',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		var type = getSelectedData().data.WORK_REPORT_BUSI_TYPE;
		name = getSelectedData().data.REPORTER_NAME;
		cycle = getSelectedData().data.REPORTER_CYCLE;
		if(type == '01'){
			 showCustomerViewByIndex(0);
		}else{
			 showCustomerViewByIndex(1);
		}
	}
}];

var recordselect = function(){
	hideCurrentView();
};

var customerView = [{
	title : '个金报告详情',
	type : 'form',
	autoLoadSeleted : true,
	hideTitle : true,
	groups : [{
		columnCount : 2 ,
		fields : [
			{name:'ID',text:'ID',hidden:true},
			{name:'REPORT_SUB1',text:'联系客户次数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB2',text:'联系发送邮件次数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB3',text:'新增客户数',readOnly:true,cls:'x-readOnly'},
	        {name:'REPORT_SUB4',text:'新增潜在客户数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB5',text:'销售理财产品金额',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB6',text:'销售保险金额',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB7',text:'联系发送短信次数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB8',text:'联系发送微信次数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB9',text:'新增管理资产余额',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB10',text:'新增存款余额',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB11',text:'提升客户数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB12',text:'有效客户数',readOnly:true,cls:'x-readOnly'},															
	        {name:'REPORT_SUB13',text:'流失客户数',readOnly:true,cls:'x-readOnly'}
	    ],
		fn : function(ID,REPORT_SUB1,REPORT_SUB2,REPORT_SUB3,REPORT_SUB4,REPORT_SUB5,REPORT_SUB6,REPORT_SUB7
		        ,REPORT_SUB8,REPORT_SUB9,REPORT_SUB10,REPORT_SUB11,REPORT_SUB12,REPORT_SUB13){
			ID.hidden = true;
			return [REPORT_SUB1,REPORT_SUB2,REPORT_SUB3,REPORT_SUB4,REPORT_SUB5,REPORT_SUB6,REPORT_SUB7
			        ,REPORT_SUB8,REPORT_SUB9,REPORT_SUB10,REPORT_SUB11,REPORT_SUB12,REPORT_SUB13,ID];
		}
	}],
	formButtons:[{
		text:'导出报告',
		fn:function(formPanel,basicForm){
			Ext.Ajax.request({
				url : basepath + '/reportManger!getWordReport.json',
				method : 'POST',
				params : {
					ID:formPanel.getForm().getFieldValues().ID,
					reportType:'weekReportP',
					name:name,
					cycle:cycle
				},
				success : function() {
					top.window.open( basepath+'/TempDownload?filename=weekReportP'+formPanel.getForm().getFieldValues().ID+'.doc','', 
							'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
				},	
				failure : function() {
					Ext.Msg.alert('提示', '生成报告失败');	
				}
			});
		}
	}]
},{
	title :'法金报告详情',
	type:'form',
	hideTitle:true,
	autoLoadSeleted : true,
	groups:[{
		columnCount : 2 ,
		fields:[{
			name:'ID',text:'ID',hidden:true},
	        {name:'REPORT_SUB1',text:'新增客户数',readOnly:true,cls:'x-readOnly'},																	
	        {name:'REPORT_SUB2',text:'新增潜在客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB3',text:'销售保险金额',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB4',text:'新增TMU业务量',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB5',text:'电访客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB6',text:'纳入PIPLIE统计客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB7',text:'纳入To-be-submitted (CA准备阶段）客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB8',text:'纳入In approval (核批阶段）客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB9',text:'纳入额度启用客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB10',text:'新增管理资产余额',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB11',text:'新增贷款余额',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB12',text:'新增存款余额',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB13',text:'进出口新增业务量',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB14',text:'拜访客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB15',text:'纳入Pipeline（客户愿意接触或有意愿合作）客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB16',text:'纳入In credit process（信用审查阶段）客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB17',text:'纳入NCA （签约开户）客户数',readOnly:true,cls:'x-readOnly'},																			
	        {name:'REPORT_SUB18',text:'纳入结案客户数',readOnly:true,cls:'x-readOnly'}
	    ],
		fn : function(ID,REPORT_SUB1,REPORT_SUB2,REPORT_SUB3,REPORT_SUB4,REPORT_SUB5,REPORT_SUB6,REPORT_SUB7,REPORT_SUB8,REPORT_SUB9,
		        REPORT_SUB10,REPORT_SUB11,REPORT_SUB12,REPORT_SUB13,REPORT_SUB14,REPORT_SUB15,REPORT_SUB16,REPORT_SUB17,REPORT_SUB18){
			ID.hidden = true;
			return [REPORT_SUB1,REPORT_SUB2,REPORT_SUB3,REPORT_SUB4,REPORT_SUB9,REPORT_SUB6,REPORT_SUB7,REPORT_SUB8,REPORT_SUB5,
			        REPORT_SUB10,REPORT_SUB11,REPORT_SUB12,REPORT_SUB13,REPORT_SUB14,REPORT_SUB15,REPORT_SUB16,REPORT_SUB17,REPORT_SUB18,ID];
		}
	}],
	formButtons:[{
		text:'导出报告',
		fn:function(formPanel,basicForm){
			Ext.Ajax.request({
				url : basepath + '/reportManger!getWordReport.json',
				method : 'POST',
				params : {
					ID:formPanel.getForm().getFieldValues().ID,
					reportType:'weekReportC',
					name:name,
					cycle:cycle
				},
				success : function() {
					top.window.open( basepath+'/TempDownload?filename=weekReportC'+formPanel.getForm().getFieldValues().ID+'.doc','', 
							'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
				},	
				failure : function() {
					Ext.Msg.alert('提示', '生成报告失败');	
				}
			});
		}
	}]
}];

