/**
 * @description 客户发卡申请页面页面
 * @author luyy
 * @since 2014-07-21
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
]);
var url = basepath + '/cardApply.json';

var comitUrl = basepath +'/cardApply!saveData.json';

var lookupTypes = ['XD000040','PRE_CUST_LEVEL','GOODS_CUST_LEVEL','CARD_APP_STATUS','GET_WAY'];

var fields = [
	{name:'ID',text:'编号',gridField:false},
	{name:'CUST_ID',text:'客户编号',dataType:'string',searchField:true},
	{name:'CUST_NAME',text:'客户名称',dataType:'string',searchField:true},
	{name:'IDENT_TYPE',text:'证件类型',translateType:'XD000040',searchField:true},
	{name:'IDENT_NO',text:'证件号码',dataType:'string',searchField:true},
	{name:'AMT_AVG_30DAYS',text:'滚动30天日均',dataType:'number',resutlWidth:80},
	{name:'CUST_GRADE',text:'客户等级',translateType:'PRE_CUST_LEVEL',resutlWidth:80},
	{name:'CARD_LVL',text:'可发卡等级',translateType:'GOODS_CUST_LEVEL',searchField:true,multiSelect:true,allowBlank:false,resutlWidth:80},
	{name:'CARD_NUM',text:'客户持卡数量',dataType:'number',viewFn:function(v){ return Ext.util.Format.number(v, '0,000');},resutlWidth:80},
	{name:'H_CARD_LEV',text:'持有最高卡等级',translateType:'GOODS_CUST_LEVEL',resutlWidth:80},
	{name:'CARD_LEV_APP',text:'申请发卡等级',translateType:'GOODS_CUST_LEVEL',resutlWidth:80},
	{name:'CARD_APP_STATUS',text:'申请状态',translateType:'CARD_APP_STATUS',resutlWidth:80},
	{name:'CARD_APP_VALIDATE',text:'申请发卡有效期',dataType:'date',resutlWidth:80},
	{name:'GET_WAY',text:'领卡方式',translateType:'GET_WAY'},
	{name:'SENT_ADDRESS',text:'收件地址',dataType:'string',gridField:false},
	{name:'SENT_NAME',text:'收件人',dataType:'string',gridField:false},
	{name:'SENT_PHONE',text:'收件电话',dataType:'string',gridField:false}
];


var customerView = [{
	title:'新增申请',
	type:'form',
	groups:[{
		columnCount: 2,
		fields:[{name:'CUST_NAME',text:'客户名称',dataType:'customerquery',hiddenName:'CUST_ID',allowBlank:false,custType:'2',custStat:'A',
			singleSelected:true,callback:function(a){
				getCurrentView().setValues({
					'IDENT_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").identType,
					'IDENT_NO':getCurrentView().contentPanel.form.findField("CUST_NAME").identNo
				});
				maskRegion('resultDomain','正在查询客户持卡信息，请稍后');
				//查询贵宾客户信息并反显
				Ext.Ajax.request({
					url : basepath + '/cardApply!getInfo.json',
					params : {
					'custId':getCurrentView().contentPanel.form.findField("CUST_ID").getValue()
					},
					success : function(response) {
						var info =  response.responseText;
						infoList = info.split('#');
						if(infoList.length == 5){
							getCurrentView().setValues({
								AMT_AVG_30DAYS:infoList[0]=='null'?0:infoList[0],
								CUST_GRADE:infoList[1]=='null'?'':infoList[1],
								CARD_LVL:infoList[2]=='null'?'':infoList[2],
								CARD_NUM:infoList[3]=='null'?'':infoList[3],
								H_CARD_LEV:infoList[4]=='null'?'':infoList[4]
							});
						}else{
							
						}
						unmaskRegion('resultDomain');
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '查询客户持卡信息失败');
						unmaskRegion('resultDomain');
					}
				});
				
		}},'IDENT_TYPE','IDENT_NO','AMT_AVG_30DAYS','CUST_GRADE','CARD_LVL','CARD_NUM','H_CARD_LEV',
		{name:'CARD_LEV_APP',text:'申请卡等级',translateType:'GOODS_CUST_LEVEL',allowBlank:false,listeners:{
			select : function(){
				var card = getCurrentView().contentPanel.form.findField("CARD_LVL").getValue();
				if(card != '' ){//当客户可持卡等级不为空时 需要判断是否填入日期 
					if(Number(getCurrentView().contentPanel.form.findField("CARD_LEV_APP").getValue())>Number(card)){
						getCurrentView().contentPanel.form.findField("CARD_APP_VALIDATE").show();
					}else{
						getCurrentView().contentPanel.form.findField("CARD_APP_VALIDATE").hide();
					}
				}
			}
		}},
		'CARD_APP_VALIDATE',
		{name:'GET_WAY',text:'领卡方式',translateType:'GET_WAY',allowBlank:false,listeners:{
			select:function(){//根据领卡方式 控制地址等是否显示
				var value = getCurrentView().contentPanel.form.findField("GET_WAY").getValue();
				if(value == '2'){//显示
					getCurrentView().contentPanel.form.findField("SENT_NAME").show();
					getCurrentView().contentPanel.form.findField("SENT_ADDRESS").show();
					getCurrentView().contentPanel.form.findField("SENT_PHONE").show();
					getCurrentView().setValues({
						'SENT_NAME':getCurrentView().contentPanel.form.findField("CUST_NAME").linkUser,
						'SENT_PHONE':getCurrentView().contentPanel.form.findField("CUST_NAME").mobileNum
					});
				}else{//隐藏
					getCurrentView().contentPanel.form.findField("SENT_NAME").hide();
					getCurrentView().contentPanel.form.findField("SENT_ADDRESS").hide();
					getCurrentView().contentPanel.form.findField("SENT_PHONE").hide();
				}
			}
		}},'SENT_ADDRESS','SENT_NAME','SENT_PHONE'],
		fn : function(CUST_NAME,IDENT_TYPE,IDENT_NO,AMT_AVG_30DAYS,CUST_GRADE,CARD_LVL,CARD_NUM,
					 H_CARD_LEV,CARD_LEV_APP,CARD_APP_VALIDATE,GET_WAY,SENT_ADDRESS,SENT_NAME,SENT_PHONE){
			CARD_APP_VALIDATE.hidden = true;
			SENT_ADDRESS.hidden = true;
			SENT_NAME.hidden = true;
			SENT_PHONE.hidden = true;
			IDENT_TYPE.readOnly = true;
			IDENT_NO.readOnly = true;
			AMT_AVG_30DAYS.readOnly = true;
			CUST_GRADE.readOnly = true;
			CARD_LVL.readOnly = true;
			CARD_NUM.readOnly = true;
			H_CARD_LEV.readOnly = true;
			IDENT_TYPE.cls='x-readOnly' ;
			IDENT_NO.cls='x-readOnly' ;
			AMT_AVG_30DAYS.cls='x-readOnly' ;
			CUST_GRADE.cls='x-readOnly' ;
			CARD_LVL.cls='x-readOnly' ;
			CARD_NUM.cls='x-readOnly' ;
			H_CARD_LEV.cls='x-readOnly' ;
			return [CUST_NAME,IDENT_TYPE,IDENT_NO,AMT_AVG_30DAYS,CUST_GRADE,CARD_LVL,CARD_NUM,
					H_CARD_LEV,CARD_LEV_APP,GET_WAY,SENT_ADDRESS,SENT_NAME,SENT_PHONE,CARD_APP_VALIDATE]
		}
	}],
	formButtons:[{
		text:'提交',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
	             Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	             return false;
	        };
	        var card = formPanel.form.findField("CARD_LVL").getValue();
			if(card != '' ){//当客户可持卡等级不为空时 需要判断是否填入日期 
				if(Number(formPanel.form.findField("CARD_LEV_APP").getValue())>Number(card)){
					if(formPanel.form.findField("CARD_APP_VALIDATE").getValue() == ''){
						 Ext.MessageBox.alert('提示','申请发卡等级高于客户可发卡等级，请填入申请发卡有效期!');
			               return false;
					}
				}
			}
			if(formPanel.form.findField("GET_WAY").getValue() == '2'){
				if(formPanel.form.findField("SENT_NAME").getValue() == ''||
						formPanel.form.findField("SENT_ADDRESS").getValue() == ''||
						formPanel.form.findField("SENT_PHONE").getValue() == ''){
					Ext.MessageBox.alert('提示','领卡方式为邮寄，请填写完整的收件信息!');
		               return false;
				}
			}
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
		  	Ext.Ajax.request({
				url : basepath + '/cardApply!saveData.json',
				method : 'GET',
				params : commintData,
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				}
			});
			
		}
	}]
},{
	title:'查看',
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount: 2,
		fields:['CUST_NAME','IDENT_TYPE','IDENT_NO','AMT_AVG_30DAYS','CUST_GRADE','CARD_LVL','CARD_NUM','H_CARD_LEV','CARD_LEV_APP',
		'CARD_APP_VALIDATE','GET_WAY','SENT_ADDRESS','SENT_NAME','SENT_PHONE'],
		fn : function(CUST_NAME,IDENT_TYPE,IDENT_NO,AMT_AVG_30DAYS,CUST_GRADE,CARD_LVL,CARD_NUM,
					 H_CARD_LEV,CARD_LEV_APP,CARD_APP_VALIDATE,GET_WAY,SENT_ADDRESS,SENT_NAME,SENT_PHONE){
			CUST_NAME.readOnly = true;
			IDENT_TYPE.readOnly = true;
			IDENT_NO.readOnly = true;
			AMT_AVG_30DAYS.readOnly = true;
			CUST_GRADE.readOnly = true;
			CARD_LVL.readOnly = true;
			CARD_NUM.readOnly = true;
			H_CARD_LEV.readOnly = true;
			CARD_LEV_APP.readOnly = true;
			CARD_APP_VALIDATE.readOnly = true;
			GET_WAY.readOnly = true;
			SENT_ADDRESS.readOnly = true;
			SENT_NAME.readOnly = true;
			SENT_PHONE.readOnly = true;
			
			CUST_NAME.cls='x-readOnly' ;
			IDENT_TYPE.cls='x-readOnly' ;
			IDENT_NO.cls='x-readOnly' ;
			AMT_AVG_30DAYS.cls='x-readOnly' ;
			CUST_GRADE.cls='x-readOnly' ;
			CARD_LVL.cls='x-readOnly' ;
			CARD_NUM.cls='x-readOnly' ;
			H_CARD_LEV.cls='x-readOnly' ;
			CARD_LEV_APP.cls='x-readOnly' ;
			CARD_APP_VALIDATE.cls='x-readOnly' ;
			GET_WAY.cls='x-readOnly' ;
			SENT_ADDRESS.cls='x-readOnly' ;
			SENT_NAME.cls='x-readOnly' ;
			SENT_PHONE.cls='x-readOnly' ;
			return [CUST_NAME,IDENT_TYPE,IDENT_NO,AMT_AVG_30DAYS,CUST_GRADE,CARD_LVL,CARD_NUM,
					H_CARD_LEV,CARD_LEV_APP,CARD_APP_VALIDATE,GET_WAY,SENT_ADDRESS,SENT_NAME,SENT_PHONE]
		}
	}]
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '查看'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};

var viewshow = function(view){
	if(view._defaultTitle == '新增申请'){
		getCurrentView().contentPanel.form.findField("CARD_APP_VALIDATE").hide();
		getCurrentView().contentPanel.form.findField("SENT_NAME").hide();
		getCurrentView().contentPanel.form.findField("SENT_ADDRESS").hide();
		getCurrentView().contentPanel.form.findField("SENT_PHONE").hide();
	}else if(view._defaultTitle == '查看'){
		if(getSelectedData().data.GET_WAY == '1'){
			getCurrentView().contentPanel.form.findField("SENT_NAME").hide();
			getCurrentView().contentPanel.form.findField("SENT_ADDRESS").hide();
			getCurrentView().contentPanel.form.findField("SENT_PHONE").hide();
		}else{
			getCurrentView().contentPanel.form.findField("SENT_NAME").show();
			getCurrentView().contentPanel.form.findField("SENT_ADDRESS").show();
			getCurrentView().contentPanel.form.findField("SENT_PHONE").show();
		}
		if(getSelectedData().data.CARD_LVL != '' && Number(getSelectedData().data.CARD_LEV_APP)>Number(getSelectedData().data.CARD_LVL)){
			getCurrentView().contentPanel.form.findField("CARD_APP_VALIDATE").show();
		}else{
			getCurrentView().contentPanel.form.findField("CARD_APP_VALIDATE").hide();
		}
	}
};