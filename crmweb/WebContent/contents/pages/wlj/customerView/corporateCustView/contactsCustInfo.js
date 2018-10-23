/**
*@description 360客户视图 对公联系人信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

var createView = !JsContext.checkGrant('contactCustInfo_create');
var createView=false;
//var editView = !JsContext.checkGrant('contactCustInfo_modify');
var editView =false;
var detailView = !JsContext.checkGrant('contactCustInfo_detail');
var needCondition = false;

Ext.QuickTips.init();
var custId =_custId;

var lookupTypes = [
	'XD000040',//证件类型
    'PAR1300083',//发证机构
    'XD000025',//国籍
    'XD000020',//民族
    'XD000001',//籍贯
    'XD000024',//婚姻
    'XD000011',//政治面貌
    'XD000015',//最高学位
    'XD000339',//联系人类型
    'XD000016',//性别
    'XD000308',//是否我行客户
    'XD000204'//证件是否核查
];

//flag 是否高管标识 1是0否
var url = basepath+'/acrmFCiOrgExecutiveinfo.json?custId='+custId+'&flag=0';

var fields = [
  		    {name: 'LINKMAN_ID',hidden : true},
  		    {name: 'IDENT_TYPE', text : '证件类型',translateType:'XD000040',allowBlank:false},
  		    {name: 'IDENT_NO', text : '证件号码',allowBlank:false},
  		    {name: 'LINKMAN_NAME', text : '姓名',allowBlank:false}, 
  		    {name: 'GENDER', text : '性别',allowBlank:false,translateType:'XD000016',resutlWidth:50}, 
  		    {name: 'BIRTHDAY', text : '出生日期',resutlWidth:70,allowBlank:false,xtype:'datefield',format:'Y-m-d',dataType:'date'},                                   
  		    {name: 'LINKMAN_TITLE',text:'称谓',resutlWidth:70},  
	        {name: 'WORK_POSITION', text : '职位',resutlWidth:70},
//	        {name: 'OFFICE_TEL_FJ', text : '办公电话',resutlWidth:90,vtype:'telephone'},
	        {name: 'OFFICE_TELN', text : '办公国家地区区号',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
							var reg = /^[0\+]\d{2,3}$/;
							if(getCurrentView()._defaultTitle == '新增'){
								var nation = getCreateView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getCreateView().contentPanel.getForm().findField('OFFICE_TELN').setValue("");
									return false;
								}
								var result = nation+"/"+getCreateView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
								getCreateView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
							}
							if(getCurrentView()._defaultTitle == '修改'){
								var nation = getEditView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getEditView().contentPanel.getForm().findField('OFFICE_TELN').setValue("");
									return false;
								}
								var result = nation+"/"+getEditView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
								getEditView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
							}
						}
				}
	        },
	        {name: 'OFFICE_TELX', text : '办公电话',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
									getCreateView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('OFFICE_TELX').getValue();
									getEditView().contentPanel.getForm().findField('OFFICE_TEL').setValue(result);
								}
						}
				}
	        },
	        {name: 'OFFICE_TEL', text : '储存办公电话',resutlWidth:120,gridField:false,hidden : true},
	        {name: 'OFFICE_TELL', text : '办公电话',resutlWidth:120},
//	        {name: 'OFFICE_TEL_FJ', text : '办公电话',resutlWidth:90,vtype:'telephone'},
	        {name: 'MOBILEN', text : '手机国家地区区号',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
							var reg = /^[0\+]\d{2,3}$/;
							if(getCurrentView()._defaultTitle == '新增'){
								var nation = getCreateView().contentPanel.getForm().findField('MOBILEN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getCreateView().contentPanel.getForm().findField('MOBILEN').setValue("");
									return false;
								}
								var result = nation+"/"+getCreateView().contentPanel.getForm().findField('MOBILEX').getValue();
								getCreateView().contentPanel.getForm().findField('MOBILE').setValue(result);
							}
							if(getCurrentView()._defaultTitle == '修改'){
								var nation = getEditView().contentPanel.getForm().findField('MOBILEN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getEditView().contentPanel.getForm().findField('MOBILEN').setValue("");
									return false;
								}
								var result = nation+"/"+getEditView().contentPanel.getForm().findField('MOBILEX').getValue();
								getEditView().contentPanel.getForm().findField('MOBILE').setValue(result);
							}
						}
				}
	        },
	        {name: 'MOBILEX', text : '手机号码',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('MOBILEN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('MOBILEX').getValue();
									getCreateView().contentPanel.getForm().findField('MOBILE').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('MOBILEN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('MOBILEX').getValue();
									getEditView().contentPanel.getForm().findField('MOBILE').setValue(result);
								}
						}
				}
	        },
	        {name: 'MOBILE', text : '储存手机号码',resutlWidth:120,gridField:false,hidden : true},
	        {name: 'MOBILEE', text : '手机号码',resutlWidth:120},
//	        {name: 'MOBILE', text : '手机号码',resutlWidth:90,vtype:'telephone'},
//	        {name: 'EXTENSION_TEL', text : '分机号码',resutlWidth:90,vtype:'telephone'},
	        {name: 'HOME_TELN', text : '家庭国家地区区号',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
							var reg = /^[0\+]\d{2,3}$/;
							if(getCurrentView()._defaultTitle == '新增'){
								var nation = getCreateView().contentPanel.getForm().findField('HOME_TELN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getCreateView().contentPanel.getForm().findField('HOME_TELN').setValue("");
									return false;
								}
								var result = nation+"/"+getCreateView().contentPanel.getForm().findField('HOME_TELX').getValue();
								getCreateView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
							}
							if(getCurrentView()._defaultTitle == '修改'){
								var nation = getEditView().contentPanel.getForm().findField('HOME_TELN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getEditView().contentPanel.getForm().findField('HOME_TELN').setValue("");
									return false;
								}
								var result = nation+"/"+getEditView().contentPanel.getForm().findField('HOME_TELX').getValue();
								getEditView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
							}
						}
				}
	        },
	        {name: 'HOME_TELX', text : '家庭电话',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('HOME_TELN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('HOME_TELX').getValue();
									getCreateView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('HOME_TELN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('HOME_TELX').getValue();
									getEditView().contentPanel.getForm().findField('HOME_TEL').setValue(result);
								}
						}
				}
	        },
	        {name: 'HOME_TEL', text : '储存家庭电话',resutlWidth:120,gridField:false,hidden : true},
	        {name: 'HOME_TELL', text : '家庭电话',resutlWidth:120},
//	        {name: 'HOME_TEL', text : '家庭电话',resutlWidth:90,vtype:'telephone'},
//	        {name: 'FEXN', text : '传真国家地区区号',resutlWidth:120},
	        {name: 'FEXY', text : '传真',resutlWidth:120,gridField:false,
	        	listeners:{
						blur : function(){
								if(getCurrentView()._defaultTitle == '新增'){
									var nation = getCreateView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									var result = nation+"/"+getCreateView().contentPanel.getForm().findField('FEXY').getValue();
									getCreateView().contentPanel.getForm().findField('FEX').setValue(result);
								}
								if(getCurrentView()._defaultTitle == '修改'){
									var nation = getEditView().contentPanel.getForm().findField('OFFICE_TELN').getValue();
									var result = nation+"/"+getEditView().contentPanel.getForm().findField('FEXY').getValue();
									getEditView().contentPanel.getForm().findField('FEX').setValue(result);
								}
						}
				}
	        },
	        {name: 'FEX', text : '储存传真',resutlWidth:120,gridField:false,hidden : true},
	        {name: 'FEXX', text : '传真',resutlWidth:120},
//	        {name: 'FEX', text : '传真',resutlWidth:120},
	        {name: 'EMAIL', text : '电子邮件',resutlWidth:120,vtype:'email'},
	        {name: 'REMARK', text : '其他',xtype:'textarea'},
	        
	        {name: 'LINKMAN_TYPE', text : '干系人类型',translateType:'XD000339',hidden:true},
	        {name: 'ORG_CUST_ID', text : '机构客户编号',hidden:true},
  		    {name: 'LINKMAN_EN_NAME', text : '干系人英文名',hidden:true},
  		    {name: 'IS_THIS_BANK_CUST', text : '是否我行客户',translateType:'XD000308'},
  		    {name: 'INDIV_CUS_ID', text : '个人客户编号'},
  		    {name: 'IDENT_REG_ADDRN', text : '证件注册国家地区',gridField:false,
  		    	listeners:{
						blur : function(){
							var reg = /^\D{1,20}$/;
							if(getCurrentView()._defaultTitle == '新增'){
								var nation = getCreateView().contentPanel.getForm().findField('IDENT_REG_ADDRN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getCreateView().contentPanel.getForm().findField('IDENT_REG_ADDRN').setValue("");
									return false;
								}
								var result = nation+"/"+getCreateView().contentPanel.getForm().findField('IDENT_REG_ADDRX').getValue();
								getCreateView().contentPanel.getForm().findField('IDENT_REG_ADDR').setValue(result);
							}
							if(getCurrentView()._defaultTitle == '修改'){
								var nation = getEditView().contentPanel.getForm().findField('IDENT_REG_ADDRN').getValue();
								if(!reg.test(nation)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									getEditView().contentPanel.getForm().findField('IDENT_REG_ADDRN').setValue("");
									return false;
								}
								var result = nation+"/"+getEditView().contentPanel.getForm().findField('IDENT_REG_ADDRX').getValue();
								getEditView().contentPanel.getForm().findField('IDENT_REG_ADDR').setValue(result);
							}
						}
				}
  		    },
  		    {name: 'IDENT_REG_ADDRX', text : '证件注册地址',gridField:false,
  		    	listeners:{
						blur : function(){
							var nation = custBaseInfoPanel.getForm().findField('IDENT_REG_ADDRN').getValue();
							var result = nation+"/"+custBaseInfoPanel.getForm().findField('IDENT_REG_ADDRX').getValue();
							custBaseInfoPanel.getForm().findField('IDENT_REG_ADDR').setValue(result);
						}
				}
  		    },
  		    {name: 'IDENT_REG_ADDRR', text : '证件注册地址',gridField:false},
  		    {name: 'IDENT_REG_ADDR', text : '储存证件注册地址',gridField:false,hidden : true},
  		    {name: 'IDENT_REG_ADDR_POST', text : '证件注册地址邮编'},
  		    {name: 'IDENT_EXPIRED_DATE', text : '证件失效日期',xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'IDENT_IS_VERIFY', text : '证件是否核查',translateType:'XD000204'},
  		    
  		    {name: 'CITIZENSHIP', text : '国籍',translateType:'XD000042'},
  		    {name: 'NATIONALITY', text : '民族',translateType:'XD000020'},
  		    {name: 'NATIVEPLACE', text : '籍贯',translateType:'XD000001'},
  		    {name: 'HIGHEST_SCHOOLING', text : '最高学历',translateType:'XD000015'},
		    {name: 'MARRIAGE', text : '婚姻状况',translateType:'XD000024'},
		    {name: 'POLITICAL_FACE', text : '政治面貌',translateType:'XD000011'},
//		    {name: 'OFFICE_TEL_FJ2', text : '办公电话2',hidden:true},
		    {name: 'OFFICE_TEL22', text : '办公电话2',hidden:true},
		    {name: 'HOME_TEL22', text : '家庭电话2'},
		    {name: 'MOBILE22', text : '手机号码2',hidden:true},

		    {name: 'ADDRESS', text : '地址信息',hidden:true},
		    {name: 'ADDRESSS', text : '地址信息'},
		    {name: 'ZIP_CODE', text : '邮政编码',hidden:true},
		    {name: 'WORK_DEPT', text : '任职部门',hidden:true},
		    {name: 'START_DATE', text : '职位开始日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
		    {name: 'END_DATE', text : '职位结束日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
	        {name: 'LAST_UPDATE_SYS', text : '最后更新系统',hidden:true},
	        {name: 'LAST_UPDATE_USER', text : '最后更新人'},	
	        {name: 'LAST_UPDATE_TMM', text : '统计日期',gridField:false},
	        {name: 'TX_SEQ_NO', text : '交易流水号',hidden:true}
  		   ];

var createFormViewer =[{
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
		return [IDENT_TYPE,IDENT_NO,LINKMAN_NAME];
	}
},{
	columnCount:0.94,
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
		return [createPanel];
	}
},{
	fields : ['LINKMAN_ID','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TELN','OFFICE_TELX','OFFICE_TEL','FEXY','FEX','MOBILEN','MOBILEX','MOBILE','HOME_TELN','HOME_TELX','HOME_TEL','IDENT_REG_ADDRN','IDENT_REG_ADDRX','IDENT_REG_ADDR','EMAIL'],
	fn : function(LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELN,OFFICE_TELX,OFFICE_TEL,FEXY,FEX,MOBILEN,MOBILEX,MOBILE,HOME_TELN,HOME_TELX,HOME_TEL,IDENT_REG_ADDRN,IDENT_REG_ADDRX,IDENT_REG_ADDR,EMAIL){
		return [LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELN,OFFICE_TELX,OFFICE_TEL,FEXY,FEX,MOBILEN,MOBILEX,MOBILE,HOME_TELN,HOME_TELX,HOME_TEL,IDENT_REG_ADDRN,IDENT_REG_ADDRX,IDENT_REG_ADDR,EMAIL];
	}
},{
	columnCount:0.95,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var editFormViewer = [{
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
		return [IDENT_TYPE,IDENT_NO,LINKMAN_NAME];
	}
},{
	columnCount:0.94,
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME){
		return [editPanel];
	}
},{
	fields : ['LINKMAN_ID','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TELN','OFFICE_TELX','OFFICE_TEL','FEXY','FEX','MOBILEN','MOBILEX','MOBILE','HOME_TELN','HOME_TELX','HOME_TEL','IDENT_REG_ADDRN','IDENT_REG_ADDRX','IDENT_REG_ADDR','EMAIL'],
	fn : function(LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELN,OFFICE_TELX,OFFICE_TEL,FEXY,FEX,MOBILEN,MOBILEX,MOBILE,HOME_TELN,HOME_TELX,HOME_TEL,IDENT_REG_ADDRN,IDENT_REG_ADDRX,IDENT_REG_ADDR,EMAIL){
		return [LINKMAN_ID,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELN,OFFICE_TELX,OFFICE_TEL,FEXY,FEX,MOBILEN,MOBILEX,MOBILE,HOME_TELN,HOME_TELX,HOME_TEL,IDENT_REG_ADDRN,IDENT_REG_ADDRX,IDENT_REG_ADDR,EMAIL];
	}
},{
	columnCount:0.95,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];


var detailFormViewer = [{
	columnCount:2,
	fields : ['IDENT_TYPE','IDENT_NO','LINKMAN_NAME','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TELL','MOBILEE','HOME_TELL','FEXX','EMAIL','IS_THIS_BANK_CUST','INDIV_CUS_ID','IDENT_REG_ADDRR','IDENT_REG_ADDR_POST','IDENT_EXPIRED_DATE','IDENT_IS_VERIFY','CITIZENSHIP','NATIONALITY','NATIVEPLACE','HIGHEST_SCHOOLING','MARRIAGE','POLITICAL_FACE','HOME_TEL22','ADDRESSS','LAST_UPDATE_USER','LAST_UPDATE_TMM'],
	fn : function(IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELL,MOBILEE,HOME_TELL,FEXX,EMAIL,IS_THIS_BANK_CUST,INDIV_CUS_ID,IDENT_REG_ADDRR,IDENT_REG_ADDR_POST,IDENT_EXPIRED_DATE,IDENT_IS_VERIFY,CITIZENSHIP,NATIONALITY,NATIVEPLACE,HIGHEST_SCHOOLING,MARRIAGE,POLITICAL_FACE,HOME_TEL22,ADDRESSS,LAST_UPDATE_USER,LAST_UPDATE_TMM){
        IDENT_TYPE.readOnly = true;
		IDENT_TYPE.cls = 'x-readOnly';
		IDENT_NO.readOnly = true;
		IDENT_NO.cls = 'x-readOnly';
		LINKMAN_NAME.readOnly = true;
		LINKMAN_NAME.cls = 'x-readOnly';
		GENDER.readOnly = true;
		GENDER.cls = 'x-readOnly';
		BIRTHDAY.readOnly = true;
		BIRTHDAY.cls = 'x-readOnly';
		LINKMAN_TITLE.readOnly = true;
		LINKMAN_TITLE.cls = 'x-readOnly';
		WORK_POSITION.readOnly = true;
		WORK_POSITION.cls = 'x-readOnly';
		OFFICE_TELL.readOnly = true;
		OFFICE_TELL.cls = 'x-readOnly';
		MOBILEE.readOnly = true;
		MOBILEE.cls = 'x-readOnly';
		HOME_TELL.readOnly = true;
		HOME_TELL.cls = 'x-readOnly';
		FEXX.readOnly = true;
		FEXX.cls = 'x-readOnly';
		EMAIL.readOnly = true;
		EMAIL.cls = 'x-readOnly';
		IS_THIS_BANK_CUST.readOnly = true;
		IS_THIS_BANK_CUST.cls = 'x-readOnly';
		INDIV_CUS_ID.readOnly = true;
		INDIV_CUS_ID.cls = 'x-readOnly';
		IDENT_REG_ADDRR.readOnly = true;
		IDENT_REG_ADDRR.cls = 'x-readOnly';
		IDENT_REG_ADDR_POST.readOnly = true;
		IDENT_REG_ADDR_POST.cls = 'x-readOnly';
		IDENT_EXPIRED_DATE.readOnly = true;
		IDENT_EXPIRED_DATE.cls = 'x-readOnly';
		IDENT_IS_VERIFY.readOnly = true;
		IDENT_IS_VERIFY.cls = 'x-readOnly';
		CITIZENSHIP.readOnly = true;
		CITIZENSHIP.cls = 'x-readOnly';
		NATIONALITY.readOnly = true;
		NATIONALITY.cls = 'x-readOnly';
		NATIVEPLACE.readOnly = true;
		NATIVEPLACE.cls = 'x-readOnly';
		HIGHEST_SCHOOLING.readOnly = true;
		HIGHEST_SCHOOLING.cls = 'x-readOnly';
		MARRIAGE.readOnly = true;
		MARRIAGE.cls = 'x-readOnly';
		POLITICAL_FACE.readOnly = true;
		POLITICAL_FACE.cls = 'x-readOnly';
		HOME_TEL22.readOnly = true;
		HOME_TEL22.cls = 'x-readOnly';
		ADDRESSS.readOnly = true;
		ADDRESSS.cls = 'x-readOnly';
		LAST_UPDATE_USER.readOnly = true;
		LAST_UPDATE_USER.cls = 'x-readOnly';
		LAST_UPDATE_TMM.readOnly = true;
		LAST_UPDATE_TMM.cls = 'x-readOnly';
        return [IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELL,MOBILEE,HOME_TELL,FEXX,EMAIL,IS_THIS_BANK_CUST,INDIV_CUS_ID,IDENT_REG_ADDRR,IDENT_REG_ADDR_POST,IDENT_EXPIRED_DATE,IDENT_IS_VERIFY,CITIZENSHIP,NATIONALITY,NATIVEPLACE,HIGHEST_SCHOOLING,MARRIAGE,POLITICAL_FACE,HOME_TEL22,ADDRESSS,LAST_UPDATE_USER,LAST_UPDATE_TMM];
	}
},{
	columnCount:0.95,
	fields : ['REMARK'],
	fn : function(REMARK){
		REMARK.readOnly = true;
		REMARK.cls = 'x-readOnly';
		return [REMARK];
	}
}];

//var detailFormViewer = [{
//	columnCount:2,
//	fields : ['LINKMAN_ID','IDENT_TYPE','IDENT_NO','LINKMAN_NAME','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TELL','MOBILEE','HOME_TELL',
//	          'FEXX','EMAIL','LINKMAN_TYPE',
//	          'ORG_CUST_ID','LINKMAN_EN_NAME','IS_THIS_BANK_CUST','INDIV_CUS_ID','IDENT_REG_ADDR','IDENT_REG_ADDRR','IDENT_REG_ADDR_POST','IDENT_EXPIRED_DATE','IDENT_IS_VERIFY',
//	          'CITIZENSHIP','NATIONALITY','NATIVEPLACE','HIGHEST_SCHOOLING','MARRIAGE','POLITICAL_FACE','OFFICE_TEL22','HOME_TEL22','MOBILE22',
//	          'ADDRESS','ADDRESSS','ZIP_CODE','WORK_DEPT','START_DATE','END_DATE','LAST_UPDATE_SYS','LAST_UPDATE_USER','LAST_UPDATE_TMM','TX_SEQ_NO'],
//	fn : function(LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELL,MOBILEE,HOME_TELL,
//	          FEXX,EMAIL,LINKMAN_TYPE,
//	          ORG_CUST_ID,LINKMAN_EN_NAME,IS_THIS_BANK_CUST,INDIV_CUS_ID,IDENT_REG_ADDR,IDENT_REG_ADDRR,IDENT_REG_ADDR_POST,IDENT_EXPIRED_DATE,IDENT_IS_VERIFY,
//	          CITIZENSHIP,NATIONALITY,NATIVEPLACE,HIGHEST_SCHOOLING,MARRIAGE,POLITICAL_FACE,OFFICE_TEL22,HOME_TEL22,MOBILE22,
//	          ADDRESS,ADDRESSS,ZIP_CODE,WORK_DEPT,START_DATE,END_DATE,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO){
//		
//		ORG_CUST_ID.hidden = true;LINKMAN_EN_NAME.hidden = false;IS_THIS_BANK_CUST.hidden = false;INDIV_CUS_ID.hidden = true;IDENT_TYPE.hidden = false;IDENT_NO.hidden = false;IDENT_REG_ADDR.hidden = false;IDENT_REG_ADDR_POST.hidden = false;IDENT_EXPIRED_DATE.hidden = false;IDENT_IS_VERIFY.hidden = false;
//        CITIZENSHIP.hidden = false;NATIONALITY.hidden = false;NATIVEPLACE.hidden = false;HIGHEST_SCHOOLING.hidden = false;MARRIAGE.hidden = false;POLITICAL_FACE.hidden = false;OFFICE_TEL_FJ2.hidden = false;HOME_TEL2.hidden = false;MOBILE2.hidden = false;
//        ADDRESS.hidden = false;ZIP_CODE.hidden = false;WORK_DEPT.hidden = false;START_DATE.hidden = false;END_DATE.hidden = false;LAST_UPDATE_SYS.hidden = false;LAST_UPDATE_USER.hidden = false;LAST_UPDATE_TMM.hidden = false;TX_SEQ_NO.hidden = false;
//       
//        IDENT_TYPE.readOnly = true;IDENT_NO.readOnly = true;LINKMAN_NAME.readOnly = true;GENDER.readOnly = true;BIRTHDAY.readOnly = true;LINKMAN_TITLE.readOnly = true;WORK_POSITION.readOnly = true;OFFICE_TEL_FJ.readOnly = true;MOBILE.readOnly = true;EXTENSION_TEL.readOnly = true;HOME_TEL.readOnly = true;
//        FEX.readOnly = true;EMAIL.readOnly = true;LINKMAN_TYPE.readOnly = true;
//        LINKMAN_EN_NAME.readOnly = true;IS_THIS_BANK_CUST.readOnly = true;INDIV_CUS_ID.readOnly = true;IDENT_TYPE.readOnly = true;IDENT_NO.readOnly = true;IDENT_REG_ADDR.readOnly = true;IDENT_REG_ADDR_POST.readOnly = true;IDENT_EXPIRED_DATE.readOnly = true;IDENT_IS_VERIFY.readOnly = true;
//        CITIZENSHIP.readOnly = true;NATIONALITY.readOnly = true;NATIVEPLACE.readOnly = true;HIGHEST_SCHOOLING.readOnly = true;MARRIAGE.readOnly = true;POLITICAL_FACE.readOnly = true;OFFICE_TEL_FJ2.readOnly = true;HOME_TEL2.readOnly = true;MOBILE2.readOnly = true;
//        ADDRESS.readOnly = true;ZIP_CODE.readOnly = true;WORK_DEPT.readOnly = true;START_DATE.readOnly = true;END_DATE.readOnly = true;LAST_UPDATE_SYS.readOnly = true;LAST_UPDATE_USER.readOnly = true;LAST_UPDATE_TMM.readOnly = true;TX_SEQ_NO.readOnly = true;
//       
//        IDENT_TYPE.cls = 'x-readOnly';IDENT_NO.cls = 'x-readOnly';LINKMAN_NAME.cls = 'x-readOnly';GENDER.cls = 'x-readOnly';BIRTHDAY.cls = 'x-readOnly';LINKMAN_TITLE.cls = 'x-readOnly';WORK_POSITION.cls = 'x-readOnly';OFFICE_TEL_FJ.cls = 'x-readOnly';MOBILE.cls = 'x-readOnly';EXTENSION_TEL.cls = 'x-readOnly';HOME_TEL.cls = 'x-readOnly';
//        FEX.cls = 'x-readOnly';EMAIL.cls = 'x-readOnly';LINKMAN_TYPE.cls = 'x-readOnly';
//        LINKMAN_EN_NAME.cls = 'x-readOnly';IS_THIS_BANK_CUST.cls = 'x-readOnly';INDIV_CUS_ID.cls = 'x-readOnly';IDENT_TYPE.cls = 'x-readOnly';IDENT_NO.cls = 'x-readOnly';IDENT_REG_ADDR.cls = 'x-readOnly';IDENT_REG_ADDR_POST.cls = 'x-readOnly';IDENT_EXPIRED_DATE.cls = 'x-readOnly';IDENT_IS_VERIFY.cls = 'x-readOnly';
//        CITIZENSHIP.cls = 'x-readOnly';NATIONALITY.cls = 'x-readOnly';NATIVEPLACE.cls = 'x-readOnly';HIGHEST_SCHOOLING.cls = 'x-readOnly';MARRIAGE.cls = 'x-readOnly';POLITICAL_FACE.cls = 'x-readOnly';OFFICE_TEL_FJ2.cls = 'x-readOnly';HOME_TEL2.cls = 'x-readOnly';MOBILE2.cls = 'x-readOnly';
//        ADDRESS.cls = 'x-readOnly';ZIP_CODE.cls = 'x-readOnly';WORK_DEPT.cls = 'x-readOnly';START_DATE.cls = 'x-readOnly';END_DATE.cls = 'x-readOnly';LAST_UPDATE_SYS.cls = 'x-readOnly';LAST_UPDATE_USER.cls = 'x-readOnly';LAST_UPDATE_TMM.cls = 'x-readOnly';TX_SEQ_NO.cls = 'x-readOnly';
//        
//        return [LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TELL,MOBILEE,HOME_TELL,
//		          FEXX,EMAIL,LINKMAN_TYPE,
//		          ORG_CUST_ID,LINKMAN_EN_NAME,IS_THIS_BANK_CUST,INDIV_CUS_ID,IDENT_REG_ADDR,IDENT_REG_ADDRR,IDENT_REG_ADDR_POST,IDENT_EXPIRED_DATE,IDENT_IS_VERIFY,
//		          CITIZENSHIP,NATIONALITY,NATIVEPLACE,HIGHEST_SCHOOLING,MARRIAGE,POLITICAL_FACE,OFFICE_TEL22,HOME_TEL22,MOBILE22,
//		          ADDRESS,ADDRESSS,ZIP_CODE,WORK_DEPT,START_DATE,END_DATE,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO];
//	}
//},{
//	columnCount:0.95,
//	fields : ['REMARK'],
//	fn : function(REMARK){
//		return [REMARK];
//	}
//}];
/**************控制详情面板的宽度************/
var detailFormCfgs = {
//		suspendWidth: 870
};

var tbar = [{
	text : '删除',
	//hidden:JsContext.checkGrant('contactCustInfo_delete'),
	hidden:true,
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
//			var messageId=getSelectedData().data.LINKMAN_ID;
			var p=getSelectedData().data.LAST_UPDATE_USER;
			if(p != __userName){
				Ext.Msg.alert('提示','只能维护本人录入的联系人信息！');
				return false;
			}
			var selectRecords = getAllSelects();
			var messageId = '';
			for(var i=0;i<selectRecords.length;i++){
				if(i == 0){
					messageId = "'"+selectRecords[i].data.LINKMAN_ID+"'";
				}else{
					messageId +=",'"+ selectRecords[i].data.LINKMAN_ID+"'";
				}
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiOrgExecutiveinfo!batchDestroy.json?messageId='+messageId,                                
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
},new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    hidden:JsContext.checkGrant('contactCustInfo_export'),
    url : basepath+'/acrmFCiOrgExecutiveinfo.json?custId='+custId+'&flag=0'
})];

var setQueryP = function(st){//st--false
	getEditView().contentPanel.getForm().findField('IDENT_TYPE').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('IDENT_NO').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('LINKMAN_TITLE').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('BIRTHDAY').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('EMAIL').setDisabled(st);
	getEditView().contentPanel.getForm().findField('REMARK').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('LINKMAN_NAME').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('GENDER').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('WORK_POSITION').setDisabled(!st);
	
	getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('IDENT_NO').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('LINKMAN_TITLE').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('BIRTHDAY').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('EMAIL').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('REMARK').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('LINKMAN_NAME').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('GENDER').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('WORK_POSITION').setDisabled(!st);
};

var setQueryA = function(st){//st--false
	getEditView().contentPanel.getForm().findField('IDENT_TYPE').setDisabled(st);
	getEditView().contentPanel.getForm().findField('IDENT_NO').setDisabled(st);
	getEditView().contentPanel.getForm().findField('LINKMAN_TITLE').setDisabled(st);
	getEditView().contentPanel.getForm().findField('BIRTHDAY').setDisabled(st);
	getEditView().contentPanel.getForm().findField('EMAIL').setDisabled(!st);
	getEditView().contentPanel.getForm().findField('REMARK').setDisabled(st);
	getEditView().contentPanel.getForm().findField('LINKMAN_NAME').setDisabled(st);
	getEditView().contentPanel.getForm().findField('GENDER').setDisabled(st);
	getEditView().contentPanel.getForm().findField('WORK_POSITION').setDisabled(st);
	
	getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('IDENT_NO').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('LINKMAN_TITLE').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('BIRTHDAY').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('EMAIL').setDisabled(!st);
	getCreateView().contentPanel.getForm().findField('REMARK').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('LINKMAN_NAME').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('GENDER').setDisabled(st);
	getCreateView().contentPanel.getForm().findField('WORK_POSITION').setDisabled(st);
};

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(view == getEditView()){
			var p=getSelectedData().data.LAST_UPDATE_USER;
			if(p != __userName){
				Ext.Msg.alert('提示','只能维护本人录入的联系人信息！');
				return false;
			}
		}
	}else {
		
	}
	if(view == getEditView()||view == getCreateView()){
		var stp = JsContext.checkGrant('publicContacterInfo_mtain1_OP');
		var sta = JsContext.checkGrant('publicContacterInfo_mtain2_AO');
		if(sta == true && stp == false){
			setQueryP(stp);
		}else if(sta == false && stp == true){
			setQueryA(sta);
		}
	}
};

var viewshow = function(view){
	if(view._defaultTitle=="修改"){
		var offT = getSelectedData().data.OFFICE_TEL;
		if(offT.indexOf('/',0)>0){
			offTArr = offT.split('/');
			getEditView().contentPanel.getForm().findField('OFFICE_TELN').setValue(offTArr[0]);
			getEditView().contentPanel.getForm().findField('OFFICE_TELX').setValue(offTArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('OFFICE_TELX').setValue(offT);
		}
		var mob = getSelectedData().data.MOBILE;
		if(mob.indexOf('/',0)>0){
			mobArr = mob.split('/');
			getEditView().contentPanel.getForm().findField('MOBILEN').setValue(mobArr[0]);
			getEditView().contentPanel.getForm().findField('MOBILEX').setValue(mobArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('MOBILEX').setValue(mob);
		}
		var homT = getSelectedData().data.HOME_TEL;
		if(homT.indexOf('/',0)>0){
			homTArr = homT.split('/');
			getEditView().contentPanel.getForm().findField('HOME_TELN').setValue(homTArr[0]);
			getEditView().contentPanel.getForm().findField('HOME_TELX').setValue(homTArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('HOME_TELX').setValue(homT);
		}
		var fex = getSelectedData().data.FEX;
		if(fex.indexOf('/',0)>0){
			fexArr = fex.split('/');
			getEditView().contentPanel.getForm().findField('FEXY').setValue(fexArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('FEXY').setValue(fex);
		}
		var addr = getSelectedData().data.IDENT_REG_ADDR;
		if(addr.indexOf('/',0)>0){
			addrArr = addr.split('/');
			getEditView().contentPanel.getForm().findField('IDENT_REG_ADDRN').setValue(addrArr[0]);
			getEditView().contentPanel.getForm().findField('IDENT_REG_ADDRX').setValue(addrArr[1]);
		}else{
			getEditView().contentPanel.getForm().findField('IDENT_REG_ADDRX').setValue(addr);
		}
	}
}

var createPanel = new Ext.Panel({
	buttonAlign:'center',
    buttons:[{
		id : 'xy',
		text :'校验',
		handler:function(){
		var a = getCurrentView().contentPanel.getForm().findField('IDENT_TYPE').getValue();
		var b = getCurrentView().contentPanel.getForm().findField('IDENT_NO').getValue();
		var c = getCurrentView().contentPanel.getForm().findField('LINKMAN_NAME').getValue();
		if(a.trim() == "" || b.trim() == "" || c.trim() == ""){
			Ext.Msg.alert('提示','存在漏输入项,请重新输入！');
			return false;
		}
		Ext.Ajax.request({
			url : basepath+'/acrmFCiOrgExecutiveinfo!check.json',
			method : 'GET',
			params :{
				identType:a,
				identNo:b,
				linkmanName:c
			},
			success : function(response) {
			    var json = Ext.decode(response.responseText).json;
			    var flag = '0';
			    setViewData(json,flag);
			}
		});
		}}]
});
var editPanel = new Ext.Panel({
	buttonAlign:'center',
    buttons:[{
		id : 'xy',
		text :'校验',
		handler:function(){
		var a = getSelectedData().data.IDENT_TYPE;
		var b = getSelectedData().data.IDENT_NO;
		var c = getSelectedData().data.LINKMAN_NAME;
		if(a.trim() == "" || b.trim() == "" || c.trim() == ""){
			Ext.Msg.alert('提示','存在漏输入项,请重新输入！');
			return false;
		}
		Ext.Ajax.request({
			url : basepath+'/acrmFCiOrgExecutiveinfo!check.json',
			method : 'GET',
			params :{
				identType:a,
				identNo:b,
				linkmanName:c
			},
			success : function(response) {
			    var json = Ext.decode(response.responseText).json;
			    var flag = '0';
			    setViewData(json,flag);
			}
		});
		}},{
			id : 'cx',
			text :'撤销',
			handler:function(){
				var flag = '1';
				var json = '';
				setViewData(json,flag);
			}
		}]
});
var setViewData = function(json, flag){
	if(flag == '1'){
		getCurrentView().contentPanel.getForm().findField('GENDER').setValue(getSelectedData().data.GENDER);
		getCurrentView().contentPanel.getForm().findField('BIRTHDAY').setValue(getSelectedData().data.BIRTHDAY);
		getCurrentView().contentPanel.getForm().findField('LINKMAN_TITLE').setValue(getSelectedData().data.LINKMAN_TITLE);
		getCurrentView().contentPanel.getForm().findField('WORK_POSITION').setValue(getSelectedData().data.WORK_POSITION);
		getCurrentView().contentPanel.getForm().findField('OFFICE_TEL_FJ').setValue(getSelectedData().data.OFFICE_TEL_FJ);
		getCurrentView().contentPanel.getForm().findField('MOBILE').setValue(getSelectedData().data.MOBILE);
		getCurrentView().contentPanel.getForm().findField('EXTENSION_TEL').setValue(getSelectedData().data.EXTENSION_TEL);
		getCurrentView().contentPanel.getForm().findField('HOME_TEL').setValue(getSelectedData().data.HOME_TEL);
		getCurrentView().contentPanel.getForm().findField('FEX').setValue(getSelectedData().data.FEX);
		getCurrentView().contentPanel.getForm().findField('EMAIL').setValue(getSelectedData().data.EMAIL);
		getCurrentView().contentPanel.getForm().findField('REMARK').setValue(getSelectedData().data.REMARK);
	}else{
//		getCurrentView().contentPanel.getForm().reset();
		getCurrentView().contentPanel.getForm().findField('GENDER').setValue(json.gender);
		getCurrentView().contentPanel.getForm().findField('BIRTHDAY').setValue(json.birthday);
		getCurrentView().contentPanel.getForm().findField('OFFICE_TEL_FJ').setValue(json.unit_tel);
		getCurrentView().contentPanel.getForm().findField('MOBILE').setValue(json.mobile_phone);
		getCurrentView().contentPanel.getForm().findField('HOME_TEL').setValue(json.home_tel);
		getCurrentView().contentPanel.getForm().findField('FEX').setValue(json.unit_fex);
		getCurrentView().contentPanel.getForm().findField('EMAIL').setValue(json.email);
		getCurrentView().contentPanel.getForm().findField('REMARK').setValue(json.remark);
	}
};
