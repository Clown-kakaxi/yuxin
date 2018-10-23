/**
 * 客户视图-客户业务信息概览-渠道业务信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var tab='channel';
    var url = basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab;
    var needCondition = false;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'CHANNEL',text:'渠道类型',searchField: true},
				  {name:'TRAN_AMT',text:'交易金额',viewFn:money('0,000.00')},
				  {name:'TRANS_NUM',text:'交易笔数'},
				  {name:'ETL_DATE',text:'统计日期',dataType:'date'}
	              ];
	
	var tbar = [new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('orgCustChannelOverView_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custBusiInfoOverViewQuery.json?custId='+_custId+'&&tab='+tab
    })];
	
	
	
	
	
