/**
 * 客户视图-客户担保信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var url = basepath+'/custGrtInfoQuery.json?custId='+_custId;
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = !JsContext.checkGrant('custGuaranteeInfo_detail');
    var lookupTypes=[
	                'DM0016',
	                'DM0017',
	                'DM0018',
	                'DM0019',
	                'DM0020',
	                'XD000040',
	                'IF_FLAG',
	                'XD000226'
	                 ];
	var fields = [
				  {name:'CONT_ID',text:'合同编号',searchField: true},
				  {name:'CN_CONT_NO',text:'中文合同编号',searchField: false},
				  {name:'CONT_TYPE',text:'合同类型',searchField: true,translateType:'DM0016'},
				  {name:'CONT_CATEGORY',text:'合同类别',searchField: true,translateType:'DM0017'},
				  {name:'GUAR_WAY',text:'担保方式',searchField: false,translateType:'DM0018'},
				  {name:'GUAR_CONT_NO',text:'担保合同编号',searchField:false},
				  {name:'GUAR_CONT_CN_NO',text:'担保合同中文号',searchField:false},
				  {name:'GAGE_TYPE',text:'担保品类型',searchField: false,translateType:'DM0019'},
				  {name:'GUARANTEE_IDENT_TYPE',text:'担保人证件类型',searchField: false,translateType:'XD000040'}, 
				  {name:'GUARANTEE_IDENT_ID',text:'担保人证件号码',searchField: false},
				  {name:'GUARANTEE_CUST_ID',text:'担保人客户编号',searchField: false},
				  {name:'GUARANTEE_NAME',text:'担保人姓名',searchField: false},
				  {name:'CURRENCY',text:'币种',searchField: false,translateType:'XD000226'},
				  {name:'ACCOUNT_AMT1',text:'入账金额',searchField: false},
				  {name:'HYC_SHOW',text:'是否show',searchField: false,translateType:'IF_FLAG'},
				  {name:'GUARANTY_STATE',text:'担保品状态',searchField: false,translateType:'DM0020'},
				  {name:'AREA_LOCATION',text:'坐落',searchField: false},
				  {name:'GUARANTY_TYPE',text:'担保品子类型',searchField: false,translateType:'DM0019'},
				  {name:'GUARANTY_START_DATE',text:'担保品有效期起始日',searchField: false,dataType:'date'},
				  {name:'GUARANTY_END_DATE',text:'担保品有效期到期日',searchField: false,dataType:'date'},
				  {name:'IDENT_ID',text:'权属证件号',searchField: false},
				  {name:'CUST_MANAGER_NAME',text:'客户经理名称',searchField: false},
				  {name:'LOAN_CARD_NO',text:'贷款卡号',searchField: false}
	              ];
	
	var tbar = [{
		text : '收起',
		handler : function(){
			collapseSearchPanel();
		}
	},{
		text : '展开',
		handler : function(){
			expandSearchPanel();
		}
	},new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('custGuaranteeInfo_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custGrtInfoQuery.json?custId='+_custId
    })];
	
	/**
	 * 详情
	 */
	var detailFormViewer =[{
		fields:['CONT_ID','CN_CONT_NO','CONT_TYPE','CONT_CATEGORY','GUAR_WAY','GUAR_CONT_NO','GUAR_CONT_CN_NO','GAGE_TYPE','GUARANTEE_IDENT_TYPE','GUARANTEE_IDENT_ID'
		        ,'GUARANTEE_CUST_ID','GUARANTEE_NAME','CURRENCY','ACCOUNT_AMT','HYC_SHOW','GUARANTY_STATE','AREA_LOCATION','GUARANTY_TYPE','GUARANTY_START_DATE','GUARANTY_END_DATE'
		        ,'IDENT_ID','CUST_MANAGER_NAME','LOAN_CARD_NO'],
		fn:function(CONT_ID,CN_CONT_NO,CONT_TYPE,CONT_CATEGORY,GUAR_WAY,GUAR_CONT_NO,GUAR_CONT_CN_NO,GAGE_TYPE,GUARANTEE_IDENT_TYPE,GUARANTEE_IDENT_ID
		        ,GUARANTEE_CUST_ID,GUARANTEE_NAME,CURRENCY,ACCOUNT_AMT,HYC_SHOW,GUARANTY_STATE,AREA_LOCATION,GUARANTY_TYPE,GUARANTY_START_DATE,GUARANTY_END_DATE
		        ,IDENT_ID,CUST_MANAGER_NAME,LOAN_CARD_NO){
			CONT_ID.disabled=true;
			CN_CONT_NO.disabled=true;
			CONT_TYPE.readOnly=true;
			CONT_TYPE.cls='x-readOnly';
			CONT_CATEGORY.readOnly=true;
			CONT_CATEGORY.cls='x-readOnly';
			GUAR_WAY.readOnly=true;
			GUAR_WAY.cls='x-readOnly';
			GUAR_CONT_NO.readOnly=true;
			GUAR_CONT_NO.cls='x-readOnly';
			GUAR_CONT_CN_NO.readOnly=true;
			GUAR_CONT_CN_NO.cls='x-readOnly';
			GAGE_TYPE.readOnly=true;
			GAGE_TYPE.cls='x-readOnly';
			GUARANTEE_IDENT_TYPE.readOnly=true;
			GUARANTEE_IDENT_TYPE.cls='x-readOnly';
			GUARANTEE_IDENT_ID.readOnly=true;
			GUARANTEE_IDENT_ID.cls='x-readOnly';
	        GUARANTEE_CUST_ID.readOnly=true;
	        GUARANTEE_CUST_ID.cls='x-readOnly';
	        GUARANTEE_NAME.readOnly=true;
	        GUARANTEE_NAME.cls='x-readOnly';
	        CURRENCY.readOnly=true;
	        CURRENCY.cls='x-readOnly';
	        ACCOUNT_AMT.readOnly=true;
	        ACCOUNT_AMT.cls='x-readOnly';
	        HYC_SHOW.readOnly=true;
	        HYC_SHOW.cls='x-readOnly';
	        GUARANTY_STATE.readOnly=true;
	        GUARANTY_STATE.cls='x-readOnly';
	        AREA_LOCATION.readOnly=true;
	        AREA_LOCATION.cls='x-readOnly';
	        GUARANTY_TYPE.readOnly=true;
	        GUARANTY_TYPE.cls='x-readOnly';
	        GUARANTY_START_DATE.readOnly=true;
	        GUARANTY_START_DATE.cls='x-readOnly';
	        GUARANTY_END_DATE.readOnly=true;
	        GUARANTY_END_DATE.cls='x-readOnly';
	        IDENT_ID.readOnly=true;
	        IDENT_ID.cls='x-readOnly';
	        CUST_MANAGER_NAME.readOnly=true;
	        CUST_MANAGER_NAME.cls='x-readOnly';
	        LOAN_CARD_NO.readOnly=true;
	        LOAN_CARD_NO.cls='x-readOnly';
			return [CONT_ID,CN_CONT_NO,CONT_TYPE,CONT_CATEGORY,GUAR_WAY,GUAR_CONT_NO,GUAR_CONT_CN_NO,GAGE_TYPE,GUARANTEE_IDENT_TYPE,GUARANTEE_IDENT_ID
			        ,GUARANTEE_CUST_ID,GUARANTEE_NAME,CURRENCY,ACCOUNT_AMT,HYC_SHOW,GUARANTY_STATE,AREA_LOCATION,GUARANTY_TYPE,GUARANTY_START_DATE,GUARANTY_END_DATE
			        ,IDENT_ID,CUST_MANAGER_NAME,LOAN_CARD_NO];
		}
		
	}];
	
	var beforeviewshow=function(view){
		if(view==getDetailView()){
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		}
	}
	
var beforeconditioninit = function(panel, app){
	app.pageSize = 100;
};
