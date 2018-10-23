/**
 * 客户视图-客户业务信息概览-中间业务信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var tab='middel';
    var url = basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab;
    var needCondition = false;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'PROD_NAME',text:'业务种类'},
				  {name:'COUNT',text:'业务笔数'},
				  {name:'AMOUNT',text:'业务发生额',viewFn:money('0,000.00')},
				  {name:'INCOME',text:'手续费收入',viewFn:money('0,000.00')},
				  {name:'ETL_DATE',text:'统计日期',dataType:'date'}
	              ];
	
	var tbar = [new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('orgCustZjywOverView_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab
    })];
	
	
	
	
	
