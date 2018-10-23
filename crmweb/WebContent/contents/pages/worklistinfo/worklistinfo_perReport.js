/**
 * 工作报告工作流-个金
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
					{xtype: 'textfield',name: 'REPORTER_NAME',fieldLabel: '报告人',labelStyle : 'text-align:right;',disabled:true,anchor : '90%'},
					{xtype: 'textfield',name: 'REPORTER_CYCLE',fieldLabel: '报告周期',labelStyle : 'text-align:right;',disabled:true,anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB1',fieldLabel:'联系客户次数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
			        {xtype: 'textfield',name:'REPORT_SUB3',fieldLabel:'新增客户数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
			        {xtype: 'textfield',name:'REPORT_SUB5',fieldLabel:'销售理财产品金额',readOnly:true,cls:'x-readOnly',anchor : '90%'},
			        {xtype: 'textfield',name:'REPORT_SUB7',fieldLabel:'联系发送短信次数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
			        {xtype: 'textfield',name:'REPORT_SUB9',fieldLabel:'新增管理资产余额',readOnly:true,cls:'x-readOnly',anchor : '90%'},
			        {xtype: 'textfield',name:'REPORT_SUB11',fieldLabel:'提升客户数',readOnly:true,cls:'x-readOnly',anchor : '90%'},															
			        {xtype: 'textfield',name:'REPORT_SUB13',fieldLabel:'流失客户数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
			        {xtype: 'textfield',name:'REPORT_SUB15',fieldLabel:'亲访',readOnly:true,cls:'x-readOnly',anchor : '90%'},																			
			        {xtype: 'textfield',name:'REPORT_SUB17',fieldLabel:'客户活动',readOnly:true,cls:'x-readOnly',anchor : '90%'}
				]
			},{
				layout : 'form',
				columnWidth : .5,
				items : [ 
					{xtype: 'textfield',name: 'REPORTER_ORG_NAME',fieldLabel: '报告机构',labelStyle : 'text-align:right;',disabled:true,anchor : '90%'},
					{xtype: 'textfield',name: 'REPORT_DATE',fieldLabel: '报告生成日期',labelStyle : 'text-align:right;',disabled:true,anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB2',fieldLabel:'联系发送邮件次数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB4',fieldLabel:'新增潜在客户数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB6',fieldLabel:'销售保险金额',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB8',fieldLabel:'联系发送微信次数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB10',fieldLabel:'新增存款余额',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB12',fieldLabel:'有效客户数',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB14',fieldLabel:'电访',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name:'REPORT_SUB16',fieldLabel:'来行约谈',readOnly:true,cls:'x-readOnly',anchor : '90%'}
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
