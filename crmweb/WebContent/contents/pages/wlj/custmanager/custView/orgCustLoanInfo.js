/**
 * 客户视图-客户业务信息概览-贷款信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var tab='loan';
    var url = basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab;
    var needCondition = false;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'PROD_NAME',text:'贷款类型',searchField: true},
				  {name:'CUR_AC_BL',text:'本期余额',searchField: false,viewFn:money('0,000.00')},
				  {name:'LAST_AC_BL',text:'上年同期余额',searchField: false,viewFn:money('0,000.00')},
				  {name:'SNSY_AVG',text:'上年上月月均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'BNSY_AVG',text:'本年上月月均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'MONTH_AVG',text:'本年近3个月均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'LAST_QUARTER_AVG',text:'上年季度均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'CUR_QUARTER_AVG',text:'本年季度均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'LAST_YEAR_AVG',text:'上年度日均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'CUR_YEAR_AVG',text:'本年度日均量',searchField: false,viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'ETL_DATE',text:'统计日期',searchField: true,dataType:'date'}
	              ];
	
	var tbar = [new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('orgCustLoanOverView_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab
    })];
	
	
	
	
