/**
 * 客户视图-客户财务信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var url = basepath+'/custFinInfoQuery.json?custId='+_custId;
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
    
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'REPORT_TYPE',text:'报表类型',dataType: 'string', searchField: true},
				  {name:'ETL_DATE',text:'统计日期', dataType: 'date', searchField: true}
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
	}];
	
	var customerView=[{
		title:'报表详情',
		type:'grid',
		isCsm : false,
		url:basepath + '/custFinInfoDetailQuery.json',
		method:'get',
		frame : true,
		fields:{fields:[  
		                  {name:'ITEM_NAME',text:'科目'},
		                  {name:'AMT',text:'金额'},
		                  {name:'CURR',text:'币种',translateType:'XD000226',hidden:true},
		                  {name:'CURR_ORA',text:'币种',translateType:'XD000226'},
		                  {name:'AMT_PRE',text:'上年同期金额'},
		                  {name:'CURR_PRE',text:'币种',translateType:'XD000226',hidden:true},
		                  {name:'CURR_PRE_ORA',text:'币种',translateType:'XD000226'}
		                ]}
	}];
	
	
	var beforeviewshow=function(view){
		if(view._defaultTitle=='报表详情'){
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			};
			var etlDate=getSelectedData().data.ETL_DATE;
			var reportType=getSelectedData().data.REPORT_TYPE;
			view.setParameters ({
				custId : _custId,
				etlDate:etlDate,
				reportType:reportType
    		});
		}
	};
	
