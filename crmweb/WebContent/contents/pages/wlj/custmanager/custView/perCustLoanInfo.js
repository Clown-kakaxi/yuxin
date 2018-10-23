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
				  {name:'LAST_AC_BL',text:'上年余额',searchField: false,viewFn:money('0,000.00')},
				  {name:'CUR_AC_BL',text:'本期余额',searchField: false,viewFn:money('0,000.00')},
				  {name:'ETL_DATE',text:'统计日期',searchField: true,dataType:'date'}
	              ];
	
	var tbar = [new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('perCustLoanOverView_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab
    })];
	
	
	
	
	
