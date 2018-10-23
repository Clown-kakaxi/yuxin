/**
*@description 360客户视图 对公高管信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
		]);
var createView = true;
var editView = true;
var detailView = true;
var needCondition = false;
var lookupTypes = ['XD000040',//证件类型
                   'PAR1300083',//发证机构
                   'IF_FLAG',
                   'DEM0100011',//国籍
                   'DEM0100001',//民族
                   'HOUSEHOLD_TYPE',//籍贯
                   'DEM0100003',//婚姻
                   'POL_LANDSCAPE',//政治面貌
                   'DEM0100020',//学位
                   'LINKMAN_TYPE',//联系人类型
                   'DEM0100005'//性别
                   ];
var custId =_custId;
//flag 是否高管 1是0否
var url = basepath+'/acrmFCiOrgExecutiveinfo.json?custId='+custId+'&flag=1';

var certTypeStore = new Ext.data.Store({//证件类型store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var sexTypeStore = new Ext.data.Store({//性别store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100005'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var flagTypeStore = new Ext.data.Store({//是否store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=IF_FLAG'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var fields = [
  		    {name: 'LINKMAN_ID',hidden : true},
  		    {name: 'IDENT_TYPE', text : '证件类型',translateType:'XD000040',allowBlank:false},
  		    {name: 'IDENT_NO', text : '证件号码',allowBlank:false},
  		    {name: 'LINKMAN_NAME', text : '姓名',allowBlank:false}, 
  		    {name: 'GENDER', text : '性别',allowBlank:false,translateType:'DEM0100005',resutlWidth:50}, 
  		    {name: 'BIRTHDAY', text : '出生日期',resutlWidth:70,allowBlank:false,xtype:'datefield',format:'Y-m-d',dataType:'date'},                                   
  		    {name: 'LINKMAN_TITLE',text:'称谓',allowBlank:false,resutlWidth:70},  
	        {name: 'WORK_POSITION', text : '职位',allowBlank:false,resutlWidth:70},
	        {name: 'OFFICE_TEL', text : '办公电话',allowBlank:false,resutlWidth:90},
	        {name: 'MOBILE', text : '手机号码',allowBlank:false,resutlWidth:90},
	        {name: 'EXTENSION_TEL', text : '分机号码',allowBlank:false,resutlWidth:90},
	        {name: 'HOME_TEL', text : '家庭电话',allowBlank:false,resutlWidth:90},
	        {name: 'FEX', text : '传真',allowBlank:false,resutlWidth:120},
	        {name: 'EMAIL', text : '电子邮件',allowBlank:false,resutlWidth:120},
	        {name: 'REMARK', text : '其他',xtype:'textarea'},
	        {name: 'REMARK1', text : '其他1',xtype:'textarea'},
	        {name: 'REMARK2', text : '其他2',xtype:'textarea'},
	        {name: 'REMARK3', text : '其他3',xtype:'textarea'},
	        {name: 'REMARK4', text : '其他4',xtype:'textarea'},
	        {name: 'REMARK5', text : '其他5',xtype:'textarea'},
	        {name: 'REMARK6', text : '其他6',xtype:'textarea'},
	        {name: 'REMARK7', text : '其他7',xtype:'textarea'},
	        
	        {name: 'LINKMAN_TYPE', text : '干系人类型',translateType:'LINKMAN_TYPE',hidden:true},
	        {name: 'ORG_CUST_ID', text : '机构客户编号',hidden:true},
  		    {name: 'LINKMAN_EN_NAME', text : '干系人英文名',hidden:true},
  		    {name: 'IS_THIS_BANK_CUST', text : '是否我行客户',hidden:true,translateType:'IF_FLAG'},
  		    {name: 'INDIV_CUS_ID', text : '个人客户编号',hidden:true},
  		    {name: 'IDENT_REG_ADDR', text : '证件注册地址',hidden:true},
  		    {name: 'IDENT_REG_ADDR_POST', text : '证件注册地址邮编',hidden:true},
  		    {name: 'IDENT_EXPIRED_DATE', text : '证件失效日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'IDENT_IS_VERIFY', text : '证件是否核查',hidden:true,translateType:'IF_FLAG'},
  		    
  		    {name: 'CITIZENSHIP', text : '国籍',hidden:true,translateType:'DEM0100011'},
  		    {name: 'NATIONALITY', text : '民族',hidden:true,translateType:'DEM0100001'},
  		    {name: 'NATIVEPLACE', text : '籍贯',hidden:true,translateType:'HOUSEHOLD_TYPE'},
  		    {name: 'HIGHEST_SCHOOLING', text : '最高学历',hidden:true,translateType:'DEM0100020'},
		    {name: 'MARRIAGE', text : '婚姻状况',hidden:true,translateType:'DEM0100003'},
		    {name: 'POLITICAL_FACE', text : '政治面貌',hidden:true,translateType:'POL_LANDSCAPE'},
		    {name: 'OFFICE_TEL2', text : '办公电话2',hidden:true},
		    {name: 'HOME_TEL2', text : '家庭电话2',hidden:true},
		    {name: 'MOBILE2', text : '手机号码2',hidden:true},

		    {name: 'ADDRESS', text : '地址信息',hidden:true},
		    {name: 'ZIP_CODE', text : '邮政编码',hidden:true},
		    {name: 'WORK_DEPT', text : '任职部门',hidden:true},
		    {name: 'START_DATE', text : '职位开始日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
		    {name: 'END_DATE', text : '职位结束日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
	        {name: 'LAST_UPDATE_SYS', text : '最后更新系统',hidden:true},
	        {name: 'LAST_UPDATE_USER', text : '最后更新人',hidden:true},	
	        {name: 'LAST_UPDATE_TMM', text : '统计日期',gridField:false},
	        {name: 'TX_SEQ_NO', text : '交易流水号',hidden:true}
  		   ];

var createFormViewer =[{
	fields : ['LINKMAN_ID','IDENT_TYPE','IDENT_NO','LINKMAN_NAME','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TEL','MOBILE','EXTENSION_TEL','HOME_TEL','FEX','EMAIL'],
	fn : function(LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,MOBILE,EXTENSION_TEL,HOME_TEL,FEX,EMAIL){
		     LAST_UPDATE_TMM.hidden = true;
		return [LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,MOBILE,EXTENSION_TEL,HOME_TEL,FEX,EMAIL];
	}
},{
	columnCount:1,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var editFormViewer = [{
	fields : ['LINKMAN_ID','IDENT_TYPE','IDENT_NO','LINKMAN_NAME','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TEL','MOBILE','EXTENSION_TEL','HOME_TEL','FEX','EMAIL'],
	fn : function(LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,MOBILE,EXTENSION_TEL,HOME_TEL,FEX,EMAIL){
		LAST_UPDATE_TMM.hidden = true;
		IDENT_TYPE.readOnly = true;
		IDENT_NO.readdOnly = true;
		LINKMAN_NAME.readOnly = true;
		return [LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,MOBILE,EXTENSION_TEL,HOME_TEL,FEX,EMAIL];
	}
},{
	columnCount:1,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var detailFormViewer = [{
	columnCount:3,
	fields : ['LINKMAN_ID','IDENT_TYPE','IDENT_NO','LINKMAN_NAME','GENDER','BIRTHDAY','LINKMAN_TITLE','WORK_POSITION','OFFICE_TEL','MOBILE','EXTENSION_TEL','HOME_TEL',
	          'FEX','EMAIL','LINKMAN_TYPE',
	          'ORG_CUST_ID','LINKMAN_EN_NAME','IS_THIS_BANK_CUST','INDIV_CUS_ID','IDENT_REG_ADDR','IDENT_REG_ADDR_POST','IDENT_EXPIRED_DATE','IDENT_IS_VERIFY',
	          'CITIZENSHIP','NATIONALITY','NATIVEPLACE','HIGHEST_SCHOOLING','MARRIAGE','POLITICAL_FACE','OFFICE_TEL2','HOME_TEL2','MOBILE2',
	          'ADDRESS','ZIP_CODE','WORK_DEPT','START_DATE','END_DATE','LAST_UPDATE_SYS','LAST_UPDATE_USER','LAST_UPDATE_TMM','TX_SEQ_NO'],
	fn : function(LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,OFFICE_TEL,MOBILE,EXTENSION_TEL,HOME_TEL,
	          FEX,EMAIL,LINKMAN_TYPE,
	          ORG_CUST_ID,LINKMAN_EN_NAME,IS_THIS_BANK_CUST,INDIV_CUS_ID,IDENT_REG_ADDR,IDENT_REG_ADDR_POST,IDENT_EXPIRED_DATE,IDENT_IS_VERIFY,
	          CITIZENSHIP,NATIONALITY,NATIVEPLACE,HIGHEST_SCHOOLING,MARRIAGE,POLITICAL_FACE,OFFICE_TEL2,HOME_TEL2,MOBILE2,
	          ADDRESS,ZIP_CODE,WORK_DEPT,START_DATE,END_DATE,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO){
		
		ORG_CUST_ID.hidden = true;LINKMAN_EN_NAME.hidden = false;IS_THIS_BANK_CUST.hidden = false;INDIV_CUS_ID.hidden = false;IDENT_TYPE.hidden = false;IDENT_NO.hidden = false;IDENT_REG_ADDR.hidden = false;IDENT_REG_ADDR_POST.hidden = false;IDENT_EXPIRED_DATE.hidden = false;IDENT_IS_VERIFY.hidden = false;
        CITIZENSHIP.hidden = false;NATIONALITY.hidden = false;NATIVEPLACE.hidden = false;HIGHEST_SCHOOLING.hidden = false;MARRIAGE.hidden = false;POLITICAL_FACE.hidden = false;OFFICE_TEL2.hidden = false;HOME_TEL2.hidden = false;MOBILE2.hidden = false;
        ADDRESS.hidden = false;ZIP_CODE.hidden = false;WORK_DEPT.hidden = false;START_DATE.hidden = false;END_DATE.hidden = false;LAST_UPDATE_SYS.hidden = false;LAST_UPDATE_USER.hidden = false;LAST_UPDATE_TMM.hidden = false;TX_SEQ_NO.hidden = false;
        
		return [LINKMAN_ID,IDENT_TYPE,IDENT_NO,LINKMAN_NAME,GENDER,BIRTHDAY,LINKMAN_TITLE,WORK_POSITION,LAST_UPDATE_TMD,OFFICE_TEL,MOBILE,EXTENSION_TEL,HOME_TEL,
		          FEX,EMAIL,LINKMAN_TYPE,
		          ORG_CUST_ID,LINKMAN_EN_NAME,IS_THIS_BANK_CUST,INDIV_CUS_ID,IDENT_REG_ADDR,IDENT_REG_ADDR_POST,IDENT_EXPIRED_DATE,IDENT_IS_VERIFY,
		          CITIZENSHIP,NATIONALITY,NATIVEPLACE,HIGHEST_SCHOOLING,MARRIAGE,POLITICAL_FACE,OFFICE_TEL2,HOME_TEL2,MOBILE2,
		          ADDRESS,ZIP_CODE,WORK_DEPT,START_DATE,END_DATE,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO];
	}
},{
	columnCount:1,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];
/**************控制详情面板的宽度************/
var detailFormCfgs = {
		suspendWidth: 870,
};
var tbar = [{
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var messageId=getSelectedData().data.LINKMAN_ID;
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiOrgExecutiveTempInfo!batchDestroy.json?messageId='+messageId,                                
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
}];
var jt_form = new Ext.form.FieldSet({
	//ACRM_F_CI_PER_FAMILIES 家庭成员表
	xtype:'fieldset',
	title:'家庭主要成员',
	titleCollapse : true,
	collapsible : true,
	autoHeight : true,
	collapsed :true,
	anchor : '95%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'MEMBERNAME',fieldLabel:'成员名称',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'FAMILYRELA',fieldLabel:'家庭成员关系',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'MEMBERCRET_TYP',fieldLabel:'成员证件类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'MEMBERCRET_NO',fieldLabel:'成员证件号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'TEL',fieldLabel:'电话',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'MOBILE',fieldLabel:'手机号码',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'email',fieldLabel:'邮件地址',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'BIRTHDAY',fieldLabel:'生日',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'COMPANY',fieldLabel:'家族成员所在企业名称',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'REMARK',fieldLabel:'备注',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		}]
	}]
});
var ah_form = new Ext.form.FieldSet({
	//ACRM_F_CI_PER_LIKEINFO 个人客户喜好信息
	xtype:'fieldset',
	title:'个人兴趣爱好',
	titleCollapse : true,
	collapsible : true,
	autoHeight : true,
	collapsed :true,
	anchor : '95%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'like_echanl_type',fieldLabel:'客户喜好电子渠道类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_leisure_type',fieldLabel:'客户喜好休闲类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_media_type',fieldLabel:'客户喜好媒体类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_sport_type',fieldLabel:'客户喜好运动类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_magazine_type',fieldLabel:'客户喜好杂志类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_film_type',fieldLabel:'客户喜好电影节目类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'like_pet_type',fieldLabel:'客户喜好宠物类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_collection_type',fieldLabel:'客户喜好收藏类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_invest_type',fieldLabel:'客户喜好投资类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_brand_type',fieldLabel:'客户喜好品牌类型',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'like_brand_text',fieldLabel:'客户其他喜好品牌',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'fina_serv',fieldLabel:'希望得到的理财服务',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'contact_type',fieldLabel:'希望客户经理的联系方式',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'fina_news',fieldLabel:'希望得到的理财资讯',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'salon',fieldLabel:'希望参加的沙龙活动',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'interests',fieldLabel:'个人兴趣爱好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'avoid',fieldLabel:'禁忌',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'other',fieldLabel:'其他',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		}]
	}]
});
var ph_form = new Ext.form.FieldSet({
	//ACRM_F_CI_PER_PREFERENCE 个人偏好表
	xtype:'fieldset',
	title:'个人偏好',
	titleCollapse : true,
	collapsible : true,
	autoHeight : true,
	collapsed :true,
	anchor : '95%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'lang_prefer',fieldLabel:'语言偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'title_prefer',fieldLabel:'称谓偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'contact_type',fieldLabel:'联络方式偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'contact_freq_prefer',fieldLabel:'联络频率偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'contact_time_prefer',fieldLabel:'联络时间偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'gift_prefer',fieldLabel:'赠品礼物偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'vehicle_prefer',fieldLabel:'出行交通工具偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'consum_habit',fieldLabel:'消费习惯',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'insurance_prefer',fieldLabel:'保险倾向',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'invest_expr',fieldLabel:'投资经验',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'risk_prefer',fieldLabel:'投资风险偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'invest_position',fieldLabel:'投资方向偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'invest_cycle',fieldLabel:'投资周期偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'finance_business_prefer',fieldLabel:'金融业务类型偏好',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'interest_investment',fieldLabel:'感兴趣的投资信息',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'invest_style',fieldLabel:'客户风险承受能力',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'invest_target',fieldLabel:'主要的投资目标',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'invest_channel',fieldLabel:'主要的投资渠道',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'post_data_flag',fieldLabel:'是否接受我行寄发的资料',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
			    	   store:flagTypeStore,valueField : 'key',displayField : 'value',
			    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
			       {name:'join_camp_flag',fieldLabel:'是否愿意参加联谊活动',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
		    		   store:flagTypeStore,valueField : 'key',displayField : 'value',
			    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
			       {name:'receive_sms_flag',fieldLabel:'是否愿意接受短信',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
		    		   store:flagTypeStore,valueField : 'key',displayField : 'value',
			    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true}
			       ]
		}]
	},{
		layout : 'form',
		columnWidth : .99,
		items:[
		       {name:'welcome_text',fieldLabel:'个人欢迎文字',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%'}
		       ]
	}]
});
var zl_form = new Ext.form.FieldSet({
	//ACRM_F_CI_PER_EDURESUME 学业履历表
	xtype:'fieldset',
	title:'个人资历',
	titleCollapse : true,
	collapsible : true,
	autoHeight : true,
	collapsed :true,
	anchor : '95%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'university',fieldLabel:'所在学校',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'college',fieldLabel:'所在院系',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'major',fieldLabel:'专业',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'edu_sys',fieldLabel:'学制',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'certificate_no',fieldLabel:'学历证书号',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'diploma_no',fieldLabel:'学位证书号',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'start_date',fieldLabel:'开始日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'end_date',fieldLabel:'结束日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		}]
	}]
});
var cy_form = new Ext.form.FieldSet({
	//ACRM_F_CI_PER_JOBRESUME 工作履历表
	xtype:'fieldset',
	title:'从业经历',
	titleCollapse : true,
	collapsible : true,
	autoHeight : true,
	collapsed :true,
	anchor : '95%',
	items:[{
		layout : 'column',
		items:[{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'unit_name',fieldLabel:'单位名称',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'unit_char',fieldLabel:'单位性质',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'work_dept',fieldLabel:'所在部门',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'position',fieldLabel:'担任职务',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'unit_tel',fieldLabel:'单位电话',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'unit_address',fieldLabel:'单位地址',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		},{
			layout : 'form',
			columnWidth : .33,
			items:[
			       {name:'unit_zipcode',fieldLabel:'单位邮编',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'start_date',fieldLabel:'开始日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%'},
			       {name:'end_date',fieldLabel:'结束日期',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%'}
			       ]
		}]
	}]
});
var base_form = new Ext.form.FieldSet({
		xtype:'fieldset',
		title:'基本信息',
		titleCollapse : true,
		collapsible : false,
		autoHeight : true,
		anchor : '95%',
		items:[{
			layout : 'column',
			items:[{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'IDENT_TYPE',fieldLabel:'证件类型<font color=red>*</font>',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:certTypeStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'IDENT_NO',fieldLabel:'证件号码<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
				       {name:'LINKMAN_NAME',fieldLabel:'姓名<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
					   {name:'GENDER',fieldLabel:'性别<font color=red>*</font>',xtype:'combo',labelStyle:'text-align:right;',anchor:'95%',
				    	   store:sexTypeStore,valueField : 'key',displayField : 'value',
				    	   typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true},
				       {name:'BIRTHDAY',fieldLabel:'出生日期<font color=red>*</font>',xtype:'datefield',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'95%'}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'LINKMAN_TITLE',fieldLabel:'称谓<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
				       {name:'WORK_POSITION',fieldLabel:'职位<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
				       {name:'OFFICE_TEL',fieldLabel:'办公电话<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
				       {name:'MOBILE',fieldLabel:'手机号码<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
				       {name:'EXTENSION_TEL',fieldLabel:'分机号码<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'}
				       ]
			},{
				layout : 'form',
				columnWidth : .33,
				items:[
				       {name:'HOME_TEL',fieldLabel:'家庭电话<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
						{name:'EMAIL',fieldLabel:'电子邮件<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
					    {name:'FEX',fieldLabel:'传真<font color=red>*</font>',xtype:'textfield',labelStyle:'text-align:right;',anchor:'95%'},
					    {name:'REMARK',fieldLabel:'其他<font color=red>*</font>',xtype:'textarea',labelStyle:'text-align:right;',anchor:'95%'}
			           ]
			}]
		}]
});

var opForm = new Ext.form.FormPanel({
	id : 'opForm',
	layout : 'form',
	labelAlign : 'right',
	frame : true,
	buttonAlign : "center",
	items:[base_form,cy_form,zl_form,ph_form,ah_form,jt_form],
	buttons:[{
	 	text : '保存',
	 	handler:function(){
	 		if(!getCurrentView().getForm().isValid()){
	 			Ext.Msg.alert('提示','');
	 			return false;
	 		}
	 		var commintData = translateDataKey(opForm.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
	 		alert(Ext.encode(commintData));
	 		return false;
	 		Ext.Ajax.request({
				url : basepath+ '/acrmFCiOrgExecutiveinfo.json',
				method : 'POST',
				params : commintData,
				failure : function() {
					Ext.MessageBox.alert('提示', '操作失败！');
				},
				success : function(response) {
					Ext.MessageBox.alert('提示', '操作成功！');
					reloadCurrentData();
				}
			});
	 	}
	}]
});

var customerView = [{
	title:'详情测试',
	xtype : 'form',
	frame : true,
	layout : 'fit',
	recordView : true,
	suspendWidth: 850,
	items:[opForm]
}];

/**修改和详情面板滑入之前判断是否选择了数据**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		var p=getSelectedData().data.LAST_UPDATE_USER;
		if(p != __userName){
			Ext.Msg.alert('提示','只能维护本人录入的联系人信息！');
			return false;
		}
	}
};