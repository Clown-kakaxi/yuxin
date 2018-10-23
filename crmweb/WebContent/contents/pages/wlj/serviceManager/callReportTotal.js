/**
 *  @description callReport汇总
 *  @author likai
 *  @since 2014-12-23
 */
imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
Ext.QuickTips.init();
var url = basepath + '/callReportTotal.json';
//var comitUrl = basepath + '/callReportTotal.json';
var needCondition = true;
var needGrid = true;

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var lookupTypes = ['CALLREPORT_VISIT_TYPE',//callreport拜访方式
                   'CALLREPORT_CUST_TYPE',//callreport客户类型
                   'CALLREPORT_FAIL_REASON',//call Report失败原因
                   'CALLREPORT_SAVES_STAGE',//call Report销售阶段
                   'CALLREPORT_PRODUCT_NAME',//产品名称
                   'INTERVIEW_RESULTS'
                  ];
                   
var fields = [
            {name: 'ID',hidden:true},
  		    {name: 'CUST_ID',text:'客户编号',gridField:true,searchField: true,resutlWidth:100},
  		    {name: 'CUST_NAME',text:'客户姓名',gridField:true,searchField: true,resutlWidth:100},	
  			/*{name: 'CUST_TYPE',text :'客户类型',translateType:'CALLREPORT_CUST_TYPE',readOnly:true,cls:'x-readOnly',resutlWidth:100},
	        {name: 'LINK_PHONE',text:'联系电话',resutlWidth:100,vtype:'phone'},
  			{name: 'VISIT_DATE',text :'拜访日期',format:'Y-m-d',xtype:'datefield',searchField: true,
  		    listeners:{
  		    	select:function(combo,record){
  		    		var v = this.getValue();
  		    		if(v.format('Y-m-d')<new Date().format('Y-m-d')){
  		    			alert("拜访日期["+v.format('Y-m-d')+"]不能小于当前日期["+new Date().format('Y-m-d')+"]");
  		    			getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue('');
  		    		}
  				}
		    	}
		    },*/
		    	
  		    /*{name: 'BEGIN_DATE',text :'开始时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',value:new Date()},
  		    {name: 'END_DATE',text :'结束时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',value:new Date(),
  		    	listeners:{
  		    		select:function(combo,record){
  		    			//结束时间
  		    			var v = this.getValue().split(":");
  		    			var h1 = parseInt(v[0]);
  		    			var s1 = parseInt(v[1]);
  		    			//起始时间
  		    			var o = getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue().split(":");
  		    			var h2 = parseInt(o[0]);
  		    			var s2 = parseInt(o[1]);
  		    			if(h1<h2){
  		    				alert("结束时间["+this.getValue()+"]不能小于开始时间["+getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue()+"]");
  		    				getCurrentView().contentPanel.getForm().findField('END_DATE').setValue('');
  		    			}else if(h1==h2){
  		    				if(s1<s2){
  		    					alert("结束时间["+this.getValue()+"]不能小于开始时间["+getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue()+"]");
  		    					getCurrentView().contentPanel.getForm().findField('END_DATE').setValue('');
  		    				}
  		    			}
  					}
		    	}},
  		    {name: 'VISIT_WAY',text:'拜访方式',translateType:'CALLREPORT_VISIT_TYPE'},
  		    {name: 'VISIT_CONTENT', text : '访谈结果',translateType:'INTERVIEW_RESULTS',maxLength:1000},*/
		    	
  		    {name: 'CALLREPORT_INFO', text : '访谈内容',xtype:'textarea',resutlWidth:600,maxLength:1000},
  		    
  		    /*{name: 'NEXT_VISIT_DATE',text:'下次约谈时间',resutlWidth:100,format:'Y-m-d',xtype:'datefield',
  		    	listeners:{
  		    		select:function(combo,record){
  		    			//下次拜访时间
  		    			var v = this.getValue();
  		    			//拜访日期
  		    			var o = getCurrentView().contentPanel.getForm().findField('VISIT_DATE').getValue();
  		    			if(o==undefined || o == null || o == ''){
  		    				alert("拜访日期不能为空!");
  		    				getCurrentView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue('');
  		    				return false;
  		    			}
  		    			if(v.format('Y-m-d')<o.format('Y-m-d')){
  		    				alert("下次拜访时间["+v.format('Y-m-d')+"]不能小于拜访日期["+o.format('Y-m-d')+"]");
  		    				getCurrentView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue('');
  		    			}
  					}
		    	}},
  		    {name: 'NEXT_VISIT_WAY',text:'下次拜访方式',resutlWidth:100,translateType:'CALLREPORT_VISIT_TYPE'},
  		    {name: 'FAIL_REASON',text:'失败原因',resutlWidth:100,translateType:'CALLREPORT_FAIL_REASON'},*/
  		    
  		    {name: 'CREATE_USER',text:'记录人编号',resutlWidth:100,searchField:true},
  		    {name: 'CREATE_USERNAME',text:'记录人名称',resutlWidth:100,searchField:true},
//  		{name: 'ORG_NAME',text:'主管客户经理所在机构',resutlWidth:120,searchField:false},
//  		{name: 'UP_ORG_NAME',text:'主管客户经理所在上级机构',resutlWidth:150,searchField:false},
  		    {name: 'MANAGER_OPINION',text:'主管意见',resutlWidth:200,xtype:'textarea',allowBlank:false},
  		    {name: 'MANAGER_USER_NAME',text:'意见主管人姓名',resutlWidth:100,allowBlank:false},
  		    {name: 'MANAGER_USER',text:'意见主管人编号',resutlWidth:100,allowBlank:false}
  		    
  		    /*{name: 'VISIT_LEFT_DT',text :'从日期(拜访日期)',format:'Y-m-d',xtype:'datefield',searchField: true,gridField:false},
  		    {name: 'VISIT_RIGHT_DT',text :'到日期(拜访日期)',format:'Y-m-d',xtype:'datefield',searchField: true,gridField:false}*/
  		  
  		];

var tbar = [
	{
		text: '主管意见录入',
		hidden:JsContext.checkGrant('_managerOpinion'),
		handler:function(){
			if (getSelectedData() == false) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			} else {
				showCustomerViewByIndex(0);
			}
		}
	},
	new Com.yucheng.crm.common.NewExpButton({
        formPanel : 'searchCondition',
        url :  basepath + '/callReportTotal.json'
    })
	/*,{
		text:'导出',
		handler : function(){
			var custId = '';
			if(getAllSelects().length > 0){
				for(var i=0;i<getAllSelects().length;i++){
					custId += getAllSelects()[i].data.CUST_ID;
					if(i != getAllSelects().length-1){
						custId += ",";
					}
				}
			}
			var userId = __userId;
			var orgId = __units;
			var flg;
			var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
			if (roleCodes != null && roleCodes != "") {	
				var roleArrs = roleCodes.split('$');
				for ( var i = 0; i < roleArrs.length; i++) {
					if (roleArrs[i] == "R125" || roleArrs[i] == "R313") {//总行个金财管专员  总行个金财管审批专员
						flg = 1;
						break;
					}else if(roleArrs[i] == "R302" || roleArrs[i] == "R303"){//个金ARM  个金RM
						flg = 2;
						break;
					}else if(roleArrs[i] == "R310"){//个金AO主管
						flg = 3;
						break;
					}
				}
			}
			var Url;
			if(flg == 1){
				Url = basepath+'/reportJsp/callReportExport.jsp?raq=callreport_st.raq&custId='+custId;
			}else if(flg == 2){
				Url = basepath+'/reportJsp/callReportExport.jsp?raq=callreport_st.raq&custId='+custId+'&mgr='+userId;
			}else if(flg == 3){
				Url = basepath+'/reportJsp/callReportExport.jsp?raq=callreport_st.raq&custId='+custId+'&org_id='+orgId;
			}
			window.open(Url);
				}
}*/
];

var customerView = [{
		title:'主管意见录入',
		hideTitle:true,
		type:'form',
		autoLoadSeleted : true,
		groups:[{
			columnCount : 1,
			fields:['ID','CALLREPORT_INFO','MANAGER_OPINION'
//			        {name:'MANAGER_OPINION',text:'个金主管意见',xtype:'textarea',allowBlank:false}
				],
			fn:function(ID,CALLREPORT_INFO,MANAGER_OPINION){
				CALLREPORT_INFO.readOnly = true;
				CALLREPORT_INFO.cls = 'x-readOnly';
				return [ID,CALLREPORT_INFO,MANAGER_OPINION];
			}
		}],
		formButtons:[{
			text:'保存',
			fn : function(formPanel,basicForm){
				var id = this.contentPanel.getForm().getValues().ID;
				var managerOpinion = this.contentPanel.getForm().getValues().MANAGER_OPINION;
				Ext.Ajax.request({
					url : basepath + '/callReportTotal!save.json',
					method : 'POST',
					params : {
						'id' : id,
						'managerOpinion' : managerOpinion
					},
					success : function() {
						 Ext.Msg.alert('提示','操作成功！');
						 reloadCurrentData();
					}
				});
			}
		},{
			text:'关闭',
			fn : function(formPanel){
				 hideCurrentView();
		}
	}]
},{
	/**
	 * 商机详情
	 * @param data
	 * @param cUrl
	 * @returns
	 */

	title:'商机详情',
	type:'grid',
	frame:true,
	url:basepath + '/callreportTotalBusiDetail.json',
	fields : {fields:[
	        {name: 'ID', text : 'ID',hidden:true},
			{name: 'BUSI_NAME', text : '商机名称'},  
			{name: 'PRODUCT_ID', text : '产品编号',renderer:function(value){
     			var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
    			return val?val:"";
    			}},  
			{name: 'SALES_STAGE', text : '销售阶段',renderer:function(value){
     			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
    			return val?val:"";
    			}},  
			{name: 'AMOUNT', text : '金额(元)'},  
			{name: 'FAIL_REASON', text : '失败原因'},  
			{name: 'ESTIMATED_TIME', text : '预计成交时间'},  
			{name: 'REMARK', text : '备注'}
			]}
		
}];

var beforeviewshow=function(view){
	if(view==getDetailView()||view==getEditView()||view._defaultTitle=='商机详情'){ 
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		if(view._defaultTitle=='商机详情'){
			var ID=getSelectedData().data.ID;
			view.setParameters ({
				id : ID
    		}); 
		};

	}};
