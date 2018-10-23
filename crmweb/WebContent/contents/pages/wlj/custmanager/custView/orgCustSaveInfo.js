/**
 * 客户视图-客户业务信息概览-存款信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var tab='save';
    var url = basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab;
    var needCondition = false;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'PROD_NAME',text:'存款业务分类'},
				  {name:'CUR_AC_BL',text:'本期余额',viewFn:money('0,000.00')},
				  {name:'LAST_AC_BL',text:'上年余额',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'CUR_YEAR_AVG',text:'本年日均',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'LAST_YEAR_AVG',text:'上年日均',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'BNSY_AVG',text:'本年上月月均',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'SNSY_AVG',text:'上年上月月均',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'LAST_SEASON_AVG',text:'上年季均',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'CUR_SEASON_AVG',text:'本年季均',viewFn:money('0,000.00'),hidden:((JsContext.checkGrant('publicOverview_mtain2_AO')==true) && (JsContext.checkGrant('publicOverview_mtain1_OP')==false))},
				  {name:'ETL_DATE',text:'统计日期',dataType:'date'}
	              ];
	
	var tbar = [new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('orgCustSaveOverView_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab
    })];
	

	
	
	
