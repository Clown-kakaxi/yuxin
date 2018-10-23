/**
 * 客户视图-客户评级信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var url = basepath+'/acrmFCiRatingQuery.json?custId='+_custId;
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
    var lookupTypes=[
                     'XD000267',//等级类型
                     'XD000082'//客户等级
    ]
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'CUST_GRADE_TYPE',text:'等级类型',searchField: true,translateType:'XD000267'},
				  {name:'CUST_GRADE',text:'客户等级',searchField: false,translateType:'XD000082'}, 
				  {name:'EVALUATE_DATE',text:'评定日期',searchField: false,dataType:'date'},
				  {name:'EFFECTIVE_DATE',text:'生效日期',searchField: false,dataType:'date'},
				  {name:'EXPIRED_DATE',text:'失效日期',searchField: false,dataType:'date'},
				  {name:'LAST_UPDATE_SYS',text:'最后更新系统',searchField: false},
				  {name:'LAST_UPDATE_USER',text:'最后更新人',searchField: false},
				  {name:'LAST_UPDATE_TM',text:'最后更新时间',searchField: false,dataType:'date'}
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
		hidden:JsContext.checkGrant('custRatingInfo_export'),
        formPanel : 'searchCondition',
        url :basepath+'/acrmFCiRatingQuery.json?custId='+_custId
    })];
	
	
	
