/**
 * 工作报告工作流-法金
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	
	var record = Ext.data.Record.create([
  		{name:'ID'},
		{name:'REPORT_ID'},
		{name:'WORK_REPORT_BUSI_TYPE'},
		{name:'REPORTER_ID'},
		{name:'REPORTER_NAME'},
		{name:'REPORTER_ORG'},
		{name:'REPORTER_ORG_NAME'},
		{name:'REPORTER_CYCLE'},
		{name:'REPORT_DATE'},
		{name:'REPORT_STAT'},
		{name:'REPORT_SUB1'},
		{name:'REPORT_SUB2'},
		{name:'REPORT_SUB3'},
		{name:'REPORT_SUB4'},
		{name:'REPORT_SUB5'},
		{name:'REPORT_SUB6'},
		{name:'REPORT_SUB7'},
		{name:'REPORT_SUB8'},
		{name:'REPORT_SUB9'},
		{name:'REPORT_SUB10'},
		{name:'REPORT_SUB11'},
		{name:'REPORT_SUB12'},
		{name:'REPORT_SUB13'},
		{name:'REPORT_SUB14'},
		{name:'REPORT_SUB15'},
		{name:'REPORT_SUB16'},
		{name:'REPORT_SUB17'},
		{name:'REPORT_SUB18'}
  	]);
    var store = new Ext.data.Store({
        restful:true,
        proxy: new Ext.data.HttpProxy({
            url: basepath + '/reportManger!queryReport.json'
        }),
        reader: new Ext.data.JsonReader({
            root:'json.data',
            totalProperty:'json.count'
        },record)
    });
    
    var reportForm = new Ext.form.FormPanel({
    	frame : true,
		items : [{
			layout : 'column',
			items : [{
				layout : 'form',
				columnWidth : .5,
				items : [ 
					{xtype: 'textfield',name: 'REPORTER_NAME',fieldLabel: '报告人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{xtype: 'textfield',name: 'CUST_ID',fieldLabel: '报告类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{xtype: 'textfield',name:'REPORT_SUB1',fieldLabel:'新增客户数',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB3',fieldLabel:'销售保险金额',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB5',fieldLabel:'电访客户数',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB7',fieldLabel:'纳入To-be-submitted (CA准备阶段）客户数',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB9',fieldLabel:'纳入额度启用客户数',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB11',fieldLabel:'新增贷款余额',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB13',fieldLabel:'进出口新增业务量',readOnly:true,cls:'x-readOnly'},	
			        {xtype: 'textfield',name:'REPORT_SUB15',fieldLabel:'纳入Pipeline（客户愿意接触或有意愿合作）客户数',readOnly:true,cls:'x-readOnly'},
			        {xtype: 'textfield',name:'REPORT_SUB17',fieldLabel:'纳入NCA （签约开户）客户数',readOnly:true,cls:'x-readOnly'}
				]
			},{
				layout : 'form',
				columnWidth : .5,
				items : [ 
					{xtype: 'textfield',name: 'REPORTER_ORG_NAME',fieldLabel: '报告机构',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{xtype: 'textfield',name: 'REPORT_DATE',fieldLabel: '报告生成日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
					{xtype: 'textfield',name:'REPORT_SUB2',fieldLabel:'新增潜在客户数',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB4',fieldLabel:'新增TMU业务量',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB6',fieldLabel:'纳入PIPLIE统计客户数',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB8',fieldLabel:'纳入In approval (核批阶段）客户数',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB10',fieldLabel:'新增管理资产余额',readOnly:true,cls:'x-readOnly'},	
					{xtype: 'textfield',name:'REPORT_SUB12',fieldLabel:'新增存款余额',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB14',fieldLabel:'拜访客户数',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB16',fieldLabel:'纳入In credit process（信用审查阶段）客户数',readOnly:true,cls:'x-readOnly'},
					{xtype: 'textfield',name:'REPORT_SUB18',fieldLabel:'纳入结案客户数',readOnly:true,cls:'x-readOnly'}
				]
			}]
		}]
    });
    
    
	var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[reportForm]
    }); 
	var EchainPanel = new Mis.Echain.EchainPanel({
		instanceID:instanceid,
		nodeId:nodeid,
		nodeName:curNodeObj.nodeName,
		fOpinionFlag:curNodeObj.fOpinionFlag,
		approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
		WindowIdclode:curNodeObj.windowid,
		callbackCustomFun:'3_a10##1'
	});
	var view = new Ext.Panel({
		renderTo : 'viewEChian',
		frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [bussFieldSetGrid,EchainPanel]
	});
	
	store.load({
		params : {
			id : id
		},
		callback : function(){
			if (store.getCount() != 0){
				reportForm.getForm().loadRecord(store.getAt(0));
			}
		}
	});
});
