/**
*@description 360客户视图 对公股东信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		]);
var createView = !JsContext.checkGrant('shareholders_create');
var editView = !JsContext.checkGrant('shareholders_modify');
var detailView = !JsContext.checkGrant('shareholders_detail');
var lookupTypes = ['XD000025',//国别代码
                   'XD000040',//证件类型
                   'XD000027',//币种
                   'XD000016',//性别
                   'XD000207',//股东类型  个人股东 SH10001 SH10002
                   'XD000213',//出资方式
                   'XD000338',//注册地是否在美国
                   'XD000208',//是否存在违规违纪现象
                   'XD000215',//是否验资
                   'XD000336',//是否合并报表
                   'XD000337'//是否上报
                   ];
var custId =_custId;
Ext.QuickTips.init();
var url = basepath+'/acrmFCiOrgHolderinfo.json?custId='+custId;

var fields = [
  		    {name: 'HOLDER_ID',hidden : true},
  		    {name: 'HOLDER_NAME', text : '股东名称',searchField:true,allowBlank:false}, 
  		    {name: 'HOLDER_TYPE', text : '股东类型',searchField:true,allowBlank:false,gridField:true,translateType:'XD000207'}, 
  		    {name: 'IDENT_TYPE', text : '证件类型',resutlWidth:70,allowBlank:false,translateType:'XD000040'},                                  
  		    {name: 'IDENT_NO',text:'证件号码',allowBlank:false,vtype:'non-chinese'},  
	        {name: 'LEGAL_REPR_NAME', text : '法人代表名称',allowBlank:false,resutlWidth:70},
	        {name: 'SPONSOR_KIND', text : '出资方式',allowBlank:false,resutlWidth:70,translateType:'XD000213'},
	        {name: 'SPONSOR_CURR', text : '出资币种',allowBlank:false,resutlWidth:70,translateType:'XD000027'},
	        {name: 'SPONSOR_AMT', text : '出资金额',allowBlank:false,resutlWidth:60,viewFn:money('0,000.00'),xtype:'numberfield'},//
	        {name: 'STOCK_PERCENT', text : '持股比例(%)',allowBlank:false,xtype:'numberfield'},//
	        {name: 'ACTUAL_STOCK_PERCENT', text : '实际持股金额',allowBlank:false,viewFn:money('0,000.00'),xtype:'numberfield'},//
	        {name: 'IS_REG_AT_USA', text : '注册地是否在美国',translateType:'XD000338',allowBlank:false,resutlWidth:60},
	        
	        {name: 'CUST_ID', text : '客户编号',hidden:true},
  		    {name: 'IDENT_EXPIRED_DATE', text : '证件失效日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'BIRTHDAY',  text : '出生日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'EMAIL',  text : '邮件地址',hidden:true},
  		    {name: 'IS_OFFENCE_FLAG', text : '是否存在违规违纪现象',hidden:true,translateType:'XD000208'},
	        {name: 'SPONSOR_PERCENT', text : '出资占比',hidden:true},//
	        {name: 'IS_CHECK_FLAG', text : '是否验资',hidden:true,translateType:'XD000215'},
	        {name: 'SPONSOR_DATE', text : '出资日期',hidden:true,xtype:'datefield',format:'Y-m-d',dataType:'date'},
	        
	        {name: 'COUNTRY_CODE', text : '国别代码',hidden:true,translateType:'XD000025'},
	        {name: 'HOLDER_ORG_ADDR', text : '法人股东公司地址',hidden:true},
	        {name: 'HOLDER_ORG_REG_ADDR', text : '法人股东注册地',hidden:true},
	        {name: 'HOLDER_ORG_TEL', text : '法人股东公司电话',hidden:true},
	        {name: 'HOLDER_PER_GENDER', text : '个人股东性别',hidden:true,translateType:'XD000016'},
	        {name: 'HOLDER_PER_BIRTH_LOCALE', text : '个人股东出生地',hidden:true},
	        {name: 'HOLDER_PER_CTRY_ADDR', text : '个人股东国别地址',hidden:true},
	        {name: 'HOLDER_PER_CTRY_TEL', text : '个人股东国别电话',hidden:true},
	        {name: 'REMT_RECVER_CTRY_CD', text : '个人股东汇出汇款收款人国别',hidden:true},
	        {name: 'REMT_RECVER_CTRY_ADDR', text : '个人股东汇出汇款收款人国别地址',hidden:true},
	        {name: 'AUTHED_PER_CTRY_CD', text : '个人股东被授权人国别',hidden:true},
	        {name: 'AUTHED_PER_CTRY_ADDR', text : '个人股东被授权人国别地址',hidden:true},
	        {name: 'HOLDER_PER_POST_ADDR', text : '个人股东转信及代存邮寄地址',hidden:true},
	        {name: 'HOLDER_PER_OFFC_TEL', text : '个人股东办公电话',hidden:true},
	        {name: 'HOLDER_PER_FAMLY_TEL', text : '个人股东家庭电话',hidden:true},
	        {name: 'HOLDER_PER_MOBILE', text : '个人股东手机号码',hidden:true},
	        {name: 'HOLDER_PER_IND_POS', text : '个人行业任职',hidden:true},
	        
	        {name: 'NEED_SPONSOR_AMT', text : '应出资金额（元）',viewFn:money('0,000.00'),hidden:true},//
	        {name: 'IS_RPT_MERGE', text : '是否合并报表',hidden:true,translateType:'XD000336'},
	        {name: 'IS_REPORTED', text : '是否上报',hidden:true,translateType:'XD000337'},
	        {name: 'REMARK', text : '备注',hidden:true},
	        {name: 'LAST_UPDATE_SYS', text : '最后更新系统',hidden:true},
	        {name: 'LAST_UPDATE_USER', text : '最后更新人',hidden:true},
	        {name: 'LAST_UPDATE_TMM', text : '最后更新时间',gridField:false},
	        {name: 'TX_SEQ_NO', text : '交易流水号',hidden:true}
  		   ];

var createFormViewer =[{
	fields : ['HOLDER_ID','HOLDER_NAME','HOLDER_TYPE','IDENT_TYPE','IDENT_NO','LEGAL_REPR_NAME','SPONSOR_KIND','SPONSOR_CURR','SPONSOR_AMT','STOCK_PERCENT','ACTUAL_STOCK_PERCENT','IS_REG_AT_USA'],
	fn : function(HOLDER_ID,HOLDER_NAME,HOLDER_TYPE,IDENT_TYPE,IDENT_NO,LEGAL_REPR_NAME,SPONSOR_KIND,SPONSOR_CURR,SPONSOR_AMT,STOCK_PERCENT,ACTUAL_STOCK_PERCENT,IS_REG_AT_USA){
		return [HOLDER_ID,HOLDER_NAME,HOLDER_TYPE,IDENT_TYPE,IDENT_NO,LEGAL_REPR_NAME,SPONSOR_KIND,SPONSOR_CURR,SPONSOR_AMT,STOCK_PERCENT,ACTUAL_STOCK_PERCENT,IS_REG_AT_USA];
	}
}];

var editFormViewer = [{
	fields : ['HOLDER_ID','HOLDER_NAME','HOLDER_TYPE','IDENT_TYPE','IDENT_NO','LEGAL_REPR_NAME','SPONSOR_KIND','SPONSOR_CURR','SPONSOR_AMT','STOCK_PERCENT','ACTUAL_STOCK_PERCENT','IS_REG_AT_USA'],
	fn : function(HOLDER_ID,HOLDER_NAME,HOLDER_TYPE,IDENT_TYPE,IDENT_NO,LEGAL_REPR_NAME,SPONSOR_KIND,SPONSOR_CURR,SPONSOR_AMT,STOCK_PERCENT,ACTUAL_STOCK_PERCENT,IS_REG_AT_USA){
		return [HOLDER_ID,HOLDER_NAME,HOLDER_TYPE,IDENT_TYPE,IDENT_NO,LEGAL_REPR_NAME,SPONSOR_KIND,SPONSOR_CURR,SPONSOR_AMT,STOCK_PERCENT,ACTUAL_STOCK_PERCENT,IS_REG_AT_USA];
	}
}];

var detailFormViewer = [{
	columnCount : 3,
	fields : ['HOLDER_ID','HOLDER_NAME','HOLDER_TYPE','IDENT_TYPE','IDENT_NO','LEGAL_REPR_NAME','SPONSOR_KIND','SPONSOR_CURR','SPONSOR_AMT','STOCK_PERCENT','ACTUAL_STOCK_PERCENT','IS_REG_AT_USA',
	          'CUST_ID','IDENT_EXPIRED_DATE','BIRTHDAY','EMAIL','IS_OFFENCE_FLAG','SPONSOR_PERCENT','IS_CHECK_FLAG','SPONSOR_DATE',
	          'COUNTRY_CODE','HOLDER_ORG_ADDR','HOLDER_ORG_REG_ADDR','HOLDER_ORG_TEL','HOLDER_PER_GENDER','HOLDER_PER_BIRTH_LOCALE','HOLDER_PER_CTRY_ADDR',
	          'HOLDER_PER_CTRY_TEL','REMT_RECVER_CTRY_CD','REMT_RECVER_CTRY_ADDR','AUTHED_PER_CTRY_CD','AUTHED_PER_CTRY_ADDR','HOLDER_PER_POST_ADDR',
	          'HOLDER_PER_OFFC_TEL','HOLDER_PER_FAMLY_TEL','HOLDER_PER_MOBILE','HOLDER_PER_IND_POS',
	          'NEED_SPONSOR_AMT','IS_RPT_MERGE','IS_REPORTED','REMARK','LAST_UPDATE_SYS','LAST_UPDATE_USER','LAST_UPDATE_TMM','TX_SEQ_NO'],
	fn : function(HOLDER_ID,HOLDER_NAME,HOLDER_TYPE,IDENT_TYPE,IDENT_NO,LEGAL_REPR_NAME,SPONSOR_KIND,SPONSOR_CURR,SPONSOR_AMT,STOCK_PERCENT,ACTUAL_STOCK_PERCENT,IS_REG_AT_USA,
	          CUST_ID,IDENT_EXPIRED_DATE,BIRTHDAY,EMAIL,IS_OFFENCE_FLAG,SPONSOR_PERCENT,IS_CHECK_FLAG,SPONSOR_DATE,
	          COUNTRY_CODE,HOLDER_ORG_ADDR,HOLDER_ORG_REG_ADDR,HOLDER_ORG_TEL,HOLDER_PER_GENDER,HOLDER_PER_BIRTH_LOCALE,HOLDER_PER_CTRY_ADDR,
	          HOLDER_PER_CTRY_TEL,REMT_RECVER_CTRY_CD,REMT_RECVER_CTRY_ADDR,AUTHED_PER_CTRY_CD,AUTHED_PER_CTRY_ADDR,HOLDER_PER_POST_ADDR,
	          HOLDER_PER_OFFC_TEL,HOLDER_PER_FAMLY_TEL,HOLDER_PER_MOBILE,HOLDER_PER_IND_POS,
	          NEED_SPONSOR_AMT,IS_RPT_MERGE,IS_REPORTED,REMARK,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO){
		
				CUST_ID.hidden = true;IDENT_EXPIRED_DATE.hidden = false;BIRTHDAY.hidden = false;EMAIL.hidden = false;IS_OFFENCE_FLAG.hidden = false;SPONSOR_PERCENT.hidden = false;IS_CHECK_FLAG.hidden = false;SPONSOR_DATE.hidden = false;
			    COUNTRY_CODE.hidden = false;HOLDER_ORG_ADDR.hidden = false;HOLDER_ORG_REG_ADDR.hidden = false;HOLDER_ORG_TEL.hidden = false;HOLDER_PER_GENDER.hidden = false;HOLDER_PER_BIRTH_LOCALE.hidden = false;HOLDER_PER_CTRY_ADDR.hidden = false;
			    HOLDER_PER_CTRY_TEL.hidden = false;REMT_RECVER_CTRY_CD.hidden = false;REMT_RECVER_CTRY_ADDR.hidden = false;AUTHED_PER_CTRY_CD.hidden = false;AUTHED_PER_CTRY_ADDR.hidden = false;HOLDER_PER_POST_ADDR.hidden = false;
			    HOLDER_PER_OFFC_TEL.hidden = false;HOLDER_PER_FAMLY_TEL.hidden = false;HOLDER_PER_MOBILE.hidden = false;HOLDER_PER_IND_POS.hidden = false;
			    NEED_SPONSOR_AMT.hidden = false;IS_RPT_MERGE.hidden = false;IS_REPORTED.hidden = false;REMARK.hidden = false;LAST_UPDATE_SYS.hidden = false;LAST_UPDATE_USER.hidden = false;LAST_UPDATE_TMM.hidden = false;TX_SEQ_NO.hidden = false;
        		
			    HOLDER_NAME.readOnly = true;HOLDER_TYPE.readOnly = true;IDENT_TYPE.readOnly = true;IDENT_NO.readOnly = true;LEGAL_REPR_NAME.readOnly = true;SPONSOR_KIND.readOnly = true;SPONSOR_CURR.readOnly = true;SPONSOR_AMT.readOnly = true;STOCK_PERCENT.readOnly = true;ACTUAL_STOCK_PERCENT.readOnly = true;IS_REG_AT_USA.readOnly = true;
        		IDENT_EXPIRED_DATE.readOnly = true;BIRTHDAY.readOnly = true;EMAIL.readOnly = true;IS_OFFENCE_FLAG.readOnly = true;SPONSOR_PERCENT.readOnly = true;IS_CHECK_FLAG.readOnly = true;SPONSOR_DATE.readOnly = true;
			    COUNTRY_CODE.readOnly = true;HOLDER_ORG_ADDR.readOnly = true;HOLDER_ORG_REG_ADDR.readOnly = true;HOLDER_ORG_TEL.readOnly = true;HOLDER_PER_GENDER.readOnly = true;HOLDER_PER_BIRTH_LOCALE.readOnly = true;HOLDER_PER_CTRY_ADDR.readOnly = true;
			    HOLDER_PER_CTRY_TEL.readOnly = true;REMT_RECVER_CTRY_CD.readOnly = true;REMT_RECVER_CTRY_ADDR.readOnly = true;AUTHED_PER_CTRY_CD.readOnly = true;AUTHED_PER_CTRY_ADDR.readOnly = true;HOLDER_PER_POST_ADDR.readOnly = true;
			    HOLDER_PER_OFFC_TEL.readOnly = true;HOLDER_PER_FAMLY_TEL.readOnly = true;HOLDER_PER_MOBILE.readOnly = true;HOLDER_PER_IND_POS.readOnly = true;
			    NEED_SPONSOR_AMT.readOnly = true;IS_RPT_MERGE.readOnly = true;IS_REPORTED.readOnly = true;REMARK.readOnly = true;LAST_UPDATE_SYS.readOnly = true;LAST_UPDATE_USER.readOnly = true;LAST_UPDATE_TMM.readOnly = true;TX_SEQ_NO.readOnly = true;
        		
			    HOLDER_NAME.cls = 'x-readOnly';HOLDER_TYPE.cls = 'x-readOnly';IDENT_TYPE.cls = 'x-readOnly';IDENT_NO.cls = 'x-readOnly';LEGAL_REPR_NAME.cls = 'x-readOnly';SPONSOR_KIND.cls = 'x-readOnly';SPONSOR_CURR.cls = 'x-readOnly';SPONSOR_AMT.cls = 'x-readOnly';STOCK_PERCENT.cls = 'x-readOnly';ACTUAL_STOCK_PERCENT.cls = 'x-readOnly';IS_REG_AT_USA.cls = 'x-readOnly';
			    IDENT_EXPIRED_DATE.cls = 'x-readOnly';BIRTHDAY.cls = 'x-readOnly';EMAIL.cls = 'x-readOnly';IS_OFFENCE_FLAG.cls = 'x-readOnly';SPONSOR_PERCENT.cls = 'x-readOnly';IS_CHECK_FLAG.cls = 'x-readOnly';SPONSOR_DATE.cls = 'x-readOnly';
			    COUNTRY_CODE.cls = 'x-readOnly';HOLDER_ORG_ADDR.cls = 'x-readOnly';HOLDER_ORG_REG_ADDR.cls = 'x-readOnly';HOLDER_ORG_TEL.cls = 'x-readOnly';HOLDER_PER_GENDER.cls = 'x-readOnly';HOLDER_PER_BIRTH_LOCALE.cls = 'x-readOnly';HOLDER_PER_CTRY_ADDR.cls = 'x-readOnly';
			    HOLDER_PER_CTRY_TEL.cls = 'x-readOnly';REMT_RECVER_CTRY_CD.cls = 'x-readOnly';REMT_RECVER_CTRY_ADDR.cls = 'x-readOnly';AUTHED_PER_CTRY_CD.cls = 'x-readOnly';AUTHED_PER_CTRY_ADDR.cls = 'x-readOnly';HOLDER_PER_POST_ADDR.cls = 'x-readOnly';
			    HOLDER_PER_OFFC_TEL.cls = 'x-readOnly';HOLDER_PER_FAMLY_TEL.cls = 'x-readOnly';HOLDER_PER_MOBILE.cls = 'x-readOnly';HOLDER_PER_IND_POS.cls = 'x-readOnly';
			    NEED_SPONSOR_AMT.cls = 'x-readOnly';IS_RPT_MERGE.cls = 'x-readOnly';IS_REPORTED.cls = 'x-readOnly';REMARK.cls = 'x-readOnly';LAST_UPDATE_SYS.cls = 'x-readOnly';LAST_UPDATE_USER.cls = 'x-readOnly';LAST_UPDATE_TMM.cls = 'x-readOnly';TX_SEQ_NO.cls = 'x-readOnly';
        		
		return [HOLDER_ID,HOLDER_NAME,HOLDER_TYPE,IDENT_TYPE,IDENT_NO,LEGAL_REPR_NAME,SPONSOR_KIND,SPONSOR_CURR,SPONSOR_AMT,STOCK_PERCENT,ACTUAL_STOCK_PERCENT,IS_REG_AT_USA,
		          CUST_ID,IDENT_EXPIRED_DATE,BIRTHDAY,EMAIL,IS_OFFENCE_FLAG,SPONSOR_PERCENT,IS_CHECK_FLAG,SPONSOR_DATE,
		          COUNTRY_CODE,HOLDER_ORG_ADDR,HOLDER_ORG_REG_ADDR,HOLDER_ORG_TEL,HOLDER_PER_GENDER,HOLDER_PER_BIRTH_LOCALE,HOLDER_PER_CTRY_ADDR,
		          HOLDER_PER_CTRY_TEL,REMT_RECVER_CTRY_CD,REMT_RECVER_CTRY_ADDR,AUTHED_PER_CTRY_CD,AUTHED_PER_CTRY_ADDR,HOLDER_PER_POST_ADDR,
		          HOLDER_PER_OFFC_TEL,HOLDER_PER_FAMLY_TEL,HOLDER_PER_MOBILE,HOLDER_PER_IND_POS,
		          NEED_SPONSOR_AMT,IS_RPT_MERGE,IS_REPORTED,REMARK,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO];
	}
}];

/**************控制详情面板的宽度************/
var detailFormCfgs = {
		suspendWidth: 870
};
var tbar = [{
	text : '删除',
	hidden:JsContext.checkGrant('shareholders_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
//			var messageId=getSelectedData().data.HOLDER_ID;
			var selectRecords = getAllSelects();
			var messageId = '';
			for(var i=0;i<selectRecords.length;i++){
				if(i == 0){
					messageId = "'"+selectRecords[i].data.HOLDER_ID+"'";
				}else{
					messageId +=",'"+ selectRecords[i].data.HOLDER_ID+"'";
				}
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiOrgHolderinfo!batchDestroy.json?messageId='+messageId,                                
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
    hidden:JsContext.checkGrant('shareholders_export'),
    url : basepath+'/acrmFCiOrgHolderinfo.json?custId='+custId
})];

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};