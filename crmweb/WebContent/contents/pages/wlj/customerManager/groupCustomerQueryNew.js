/**
 * 
* @Description: 集团客户查询
* @author wangmk1 
* @date 2014-7-16
*
 */
Ext.QuickTips.init();
var url=basepath+"/groupCustomer.json";
imports([
	 '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
	,'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js'
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
  ,'/contents/pages/common/Com.yucheng.bcrm.common.TradeGroupQueryField1.js'
	]);
       
var lookupTypes=[
    'XD000106',//集团类型
    'XD000358',
    'XD000040',//证件类型
    'RELATION_ID',//成员关系
    'XD000359',//与上级企业关系
    'XD000216',//集团关联关系类型
    'CDE0100014',//集团成员类型
    'XD000292',//关联（集团）形式
    'XD000081'//客户状态
];

var comitUrl=basepath+"/groupCustomer.json";
var createView = false;
var editView = !JsContext.checkGrant("_groupCustEdit");
var detailView = !JsContext.checkGrant("_groupCustDetail");
var fields=[
    {name:'GRP_NO',text:'集团编号',resutlWidth:120,searchField:true}, 
    {name:'GRP_NAME',text:'集团名称',resutlWidth:120,searchField:true},
    {name:'PARENT_CUS_ID',text:'主申请（集团）客户号',resutlWidth:130},
    {name:'PARENT_CUS_NAME',text:'主申请集团名称',resutlWidth:130,searchField:true},
    {name:'GRP_FINANCE_TYPE',text:'关联（集团）形式',translateType:'XD000292',resutlWidth:120,searchField:true},
    {name:'CUS_MANAGER_NAME',text:'主办GAO',resutlWidth:80},
    {name:'MAIN_BR_NAME',text:'主办行',resutlWidth:80},
    {name:'INPUT_USER_NAME',text:'登记人',resutlWidth:80},
    {name:'INPUT_BR_NAME',text:'登记机构',resutlWidth:80,hidden:true},
    {name:'GRP_DETAIL',text:'关联集团情况说明',resutlWidth:80,xtype:'textarea',hidden:true},
    {name:'INPUT_DATE',text:'登记日期',resutlWidth:80,hidden:true},
    {name:'PARENT_LOAN_CARD',text:'主申请客户贷款卡编号',hidden:true}

];

var detailFormViewer=[{
	columnCount:2,
	labelWidth:160,
	fields:['GRP_NO','GRP_NAME','PARENT_CUS_ID','PARENT_CUS_NAME','PARENT_LOAN_CARD','GRP_FINANCE_TYPE','MAIN_BR_ID','MAIN_BR_NAME','CUS_MANAGER',      
	        'CUS_MANAGER_NAME','INPUT_USER_ID','INPUT_USER_NAME','INPUT_DATE','INPUT_BR_ID','INPUT_BR_NAME'],
	fn:function(GRP_NO,GRP_NAME,PARENT_CUS_ID,PARENT_CUS_NAME,PARENT_LOAN_CARD,GRP_FINANCE_TYPE,
			MAIN_BR_ID,MAIN_BR_NAME,CUS_MANAGER,CUS_MANAGER_NAME,INPUT_USER_ID,INPUT_USER_NAME,INPUT_DATE,INPUT_BR_ID,INPUT_BR_NAME   
){		
		GRP_NO.readOnly=true;
		GRP_NO.cls='x-readOnly';
		GRP_NO.readOnly=true;
		GRP_NO.cls='x-readOnly';
		GRP_NAME.readOnly=true;
		GRP_NAME.cls='x-readOnly';
		PARENT_CUS_ID.readOnly=true;
		PARENT_CUS_ID.cls='x-readOnly';
		PARENT_CUS_NAME.readOnly=true;
		PARENT_CUS_NAME.cls='x-readOnly';
		PARENT_LOAN_CARD.readOnly=true;
		PARENT_LOAN_CARD.cls='x-readOnly';
		GRP_FINANCE_TYPE.readOnly=true;
		GRP_FINANCE_TYPE.cls='x-readOnly';
		MAIN_BR_NAME.readOnly=true;
		MAIN_BR_NAME.cls='x-readOnly';
		CUS_MANAGER_NAME.readOnly=true;
		CUS_MANAGER_NAME.cls='x-readOnly';
		INPUT_USER_NAME.readOnly=true;
		INPUT_USER_NAME.cls='x-readOnly';
		INPUT_DATE.hidden=false;
		INPUT_DATE.readOnly=true;
		INPUT_DATE.cls='x-readOnly';
		INPUT_BR_NAME.readOnly=true;
		INPUT_BR_NAME.cls='x-readOnly';
		PARENT_LOAN_CARD.hidden=false;
		INPUT_BR_NAME.hidden=false;
		INPUT_DATE.hidden=false;


		
	
		return [GRP_NO,GRP_NAME,PARENT_CUS_ID,PARENT_CUS_NAME,PARENT_LOAN_CARD,GRP_FINANCE_TYPE,
		        MAIN_BR_ID,MAIN_BR_NAME,CUS_MANAGER,CUS_MANAGER_NAME,INPUT_USER_ID,INPUT_USER_NAME,INPUT_DATE,INPUT_BR_ID,INPUT_BR_NAME]
	}
},
{
	columnCount : 1 ,
	fields : ['GRP_DETAIL'],
	fn:function(GRP_DETAIL){
		GRP_DETAIL.hidden=false;
		GRP_DETAIL.readOnly=true;
		GRP_DETAIL.cls='x-readOnly';
		return [GRP_DETAIL];
	}
}
];
var customerView = [{
	title:'集团成员信息',
	suspendWidth: 950,
	//hideTitle:JsContext.checkGrant("_groupCustMemberMaintain"),
	type:'grid',
	layout:'fit',
	isCsm:false,
	url:basepath+"/groupCustMenberListNew1.json",
	autoScroll : true,
	pageable:true,
	fields:{
		fields : [
			{name: 'MEMBER_ID',text:'成员ID',hidden:true},
			{name: 'GRP_NO',text: '集团编号',hidden:true},
			{name: 'CUS_ID',text: '客户编号'},			
			{name: 'CUS_NAME',text: '客户名称',width:180},
			{name: 'IDENT_TYPE',text: '证件类型',width:150,renderer:function(value){
     			var val = translateLookupByKey("XD000040",value);
    			return val?val:"";}},
			{name: 'IDENT_NUMBER',text: '证件号码',hidden:true},
			{name: 'GRP_CORRE_TYPE',text: '关联（集团）关联关系类型'},
			{name: 'GRP_CORRE_DETAIL',text: '关联(集团)关联关系描述',hidden:true},
			{name: 'MAIN_BR_ID',text: '主办行编号'},
			{name: 'MAIN_BR_NAME',text: '主办行名称',width:150},
			{name: 'CUS_MANAGER',text: '主办客户经理编号',hidden:true},
			{name: 'CUS_MANAGER_NAME',text: '主办客户经理名称'},
			{name: 'INPUT_USER_ID',text: '登记人编号',hidden:true},
			{name: 'INPUT_USER_NAME',text: '登记人名称',hidden:true},
			{name: 'INPUT_DATE',text: '等级日期',hidden:true},
			{name: 'INPUT_BR_ID',text: '等级机构编号'},
			{name: 'INPUT_BR_NAME',text: '登记机构名称',hidden:true}
		]
	}
},{
	title:'买售商成员维护',
	suspendWidth: 950,
	//hideTitle:JsContext.checkGrant("_groupCustMemberMaintain"),
	type:'grid',
	layout:'fit',
	url:basepath+"/groupCustMenberListNew.json",
	autoScroll : true,
	pageable:true,
	fields:{
		fields : [
			{name: 'MEMBER_ID',text:'成员ID',hidden:true},
			{name: 'GRP_NO',text: '集团编号',hidden:true},
			{name: 'CUS_ID',text: '客户编号',width:150},			
			{name: 'CUS_NAME',text: '客户名称',width:180},
			{name: 'IDENT_TYPE',text: '证件类型',width:100,hidden:true,renderer:function(value){
     			var val = translateLookupByKey("XD000040",value);
    			return val?val:"";}},
			{name: 'IDENT_NUMBER',text: '证件号码',hidden:true},
			{name: 'GRP_CORRE_TYPE',text: '关联（集团）关联关系类型',hidden:true},
			{name: 'GRP_CORRE_DETAIL',text: '关联(集团)关联关系描述',hidden:true},
			{name: 'MAIN_BR_ID',text: '主办行编号'},
			{name: 'MAIN_BR_NAME',text: '主办行名称',width:180},
			{name: 'CUS_MANAGER',text: '主办客户经理编号',hidden:true},
			{name: 'CUS_MANAGER_NAME',text: '主办客户经理名称'},
			{name: 'INPUT_USER_ID',text: '登记人编号',hidden:true},
			{name: 'INPUT_USER_NAME',text: '登记人名称',hidden:true},
			{name: 'INPUT_DATE',text: '等级日期',hidden:true},
			{name: 'INPUT_BR_ID',text: '等级机构编号'},
			{name: 'INPUT_BR_NAME',text: '登记机构名称',hidden:true}
		]
	},
	gridButtons:[{
		/**
		 * 关联买售商成员
		 */
		text : '关联买售商信息',
		hidden:false,
		fn : function(){
			showCustomerViewByTitle('关联买售商成员');
		}	
	},{
		/**
		 * 关联买售商成员
		 */
		text : '取消关联',
		hidden:false,
		fn : function(grid){
		//	showCustomerViewByTitle('关联买售商成员');
			
			var checkedNodes=getCustomerViewByTitle("买售商成员维护").getGrid().getSelectionModel().getSelections();
			var selectLength=0;
			selectLength = checkedNodes.length;// 得到行数组的长度
			
			var ids="";
			var grpno=checkedNodes[0].data.GRP_NO;
			var checkedid;
			if (selectLength <1) {
				Ext.Msg.alert('提示信息', '请选择至少一条记录！');
				return;
			};
			for(var i=0;i<checkedNodes.length;i++){
				checkedid=checkedNodes[i].data.CUS_ID;
				ids+=checkedid;
				if (i != selectLength - 1)
					ids += "','";
			};
			ids="'"+ids+"'";
			if (ids != '') {
				Ext.MessageBox
						.confirm(
								'提示',
								'确认进行删除操作吗？',
								function(buttonobj) {
									if (buttonobj == 'yes') {
										Ext.Ajax
												.request({
													url : basepath
															+ '/groupCustMenberListNew1!batchDestory.json',
													params : {
														idStr : ids,
														grpno:grpno
													},
													waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
													method : 'GET',
													scope : this,
													success : function() {
														Ext.Msg
																.alert(
																		'提示信息',
																		'操作成功！');
														reloadCurrentData();
													}
												});
									}
								}, this);
			}
			
		}	
	}
	]	
},{
	title:'关联买售商成员',
    hideTitle:true,
    type:'form',
    suspendWidth:900,
    autoLoadSeleted : false,
    groups : {
    	columnCount :2,
    	fields : [
    				{name: 'GRP_NO',text:'买受商集团编号',width:150,xtype:'tradequery',hiddenName:'GRP_NO',singleSelected:true,callback:function(a,b){
    					getCurrentView().setValues({
    						'GRP_NAME':getCurrentView().contentPanel.form.findField("GRP_NO").grpname,
    						'MAIN_BR_ID':getCurrentView().contentPanel.form.findField("GRP_NO").mainbrid,
    						'MAIN_BR_NAME':getCurrentView().contentPanel.form.findField("GRP_NO").mainbrname
    					});
    				//	getCurrentView().contentPanel.getForm().findField('MAIN_BR_ID').setValue("123");
    			}},
    				{name: 'GRP_NAME',text: '买受商集团名称',width:190,hiddenName:'GRP_NAME'},
    				{name: 'MAIN_BR_ID',text: '授信主办行编号'},			
    				{name: 'MAIN_BR_NAME',text: '授信主办行名称',width:190}
    			]
    },
formButtons:[{
	text:'确定',
	fn : function(formPanel,basicForm){
		var  id=getSelectedData().data.GRP_NO;
		var grpno = basicForm.getFieldValues().GRP_NO;
		var grpname=basicForm.getFieldValues().GRP_NAME;
		var mainbrid=basicForm.getFieldValues().MAIN_BR_ID;
		var mainbrname=basicForm.getFieldValues().MAIN_BR_NAME;
		 getCustomerViewByTitle("买售商成员维护").getGrid().getSelectionModel().selectAll();
		var checkedNodes=getCustomerViewByTitle("买售商成员维护").getGrid().getSelectionModel().getSelections();
		for(var i=0;i<checkedNodes.length;i++){
			if(checkedNodes[i].data.CUS_ID==grpno){
				 Ext.Msg.alert('提示', '此成员已存在！');
				 return;
			}
		};

		Ext.MessageBox
		.confirm(
				'提示',
				'确认进行关联操作吗？',
				function(buttonobj) {
					if (buttonobj == 'yes') {
		
          Ext.Ajax.request({
                url: basepath+'/groupCustBuy!saveGroupMember.json',
                method:'POST',
                params:{
                	groupno:grpno,
                	grpname:grpname,
                	mainbrid:mainbrid,
                	mainbrname:mainbrname,
                	id:id
                },
                success: function(response) {
                    Ext.Msg.alert('提示', '新增集团成员成功！');
                    gridStore.reload();
                    showCustomerViewByTitle('买售商成员维护');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '新增集团成员失败！');
                }
            });
					}
				}, this);
		
		
		
	}
},{
	text:'关闭',
	fn : function(formPanel,basicForm){
		 reloadCurrentData();
		 hideCurrentView();
	}
}
]
}

];


var firstLayout = -1;//用于控制在自定义grid上添加查询form
var seleRecod='';
var model='';
var gridStore='';
var beforeviewshow = function(theView) {
	if (!getSelectedData()) {
		Ext.Msg.alert('提示', '请选择一条数据进行操作！');
		return false;
	} else {
	
		if(theView._defaultTitle =='买售商成员维护'){
			if(firstLayout <0){
				firstLayout++;
				theView.remove(theView.grid,false);
				theView.doLayout();
				theView.grid.setHeight(theView.grid.getHeight()-30);
				model = theView.grid.getSelectionModel(); 
				gridStore=theView.grid.getStore();
				theView.add({
					xtype:'panel',
					layout:'form',
					items:[theView.grid]
				});
				theView.doLayout();
			}
			theView.setParameters({
				id:getSelectedData().data.GRP_NO
			});
		};
		
		if(theView._defaultTitle =='集团成员信息'){
			if(firstLayout <0){
				firstLayout++;
				theView.remove(theView.grid,false);
				theView.doLayout();
				theView.grid.setHeight(theView.grid.getHeight()-30);
				model = theView.grid.getSelectionModel(); 
				gridStore=theView.grid.getStore();
				theView.add({
					xtype:'panel',
					layout:'form',
					items:[theView.grid]
				});
				theView.doLayout();
			}
			theView.setParameters({
				id:getSelectedData().data.GRP_NO
			});
		};
		
		if(theView._defaultTitle =='关联买售商成员'){

			
		}
	}
}
 /**
 * 查询条件域对象渲染之前触发，此时对象尚未渲染；
 * params ：con：查询条件面板对象；
 * 			app：当前APP对象；
 */
var beforeconditionrender=function(con,app){
	con.layout='form';
	con.autoHeight=true;
}                    