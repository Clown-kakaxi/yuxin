/**
 * 对私客户视图-客户拜访信息-服务
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		      
		        ]);
    var _custId;//视图传送的客户ID
    var url = basepath+'/ocrmCustViewQuery.json?custId='+_custId+'&&flag='+2;
    var formCfgs = false;
    var needCondition = true;
    var needGrid = true;
    var detailView = true;
    
    var lookupTypes=[
                    'SERVICE_CHANNEL'
                     ];


	var fields = [
				  {name:'CANTACT_DATE',text:'拜访时间',xtype:'datefield',format:'Y-m-d',searchField:true},
				  {name:'CANTACT_CHANNEL',text:'拜访方式',translateType:'SERVICE_CHANNEL',searchField:false},
				  {name:'MARKET_RESULT',text:'拜访内容',searchField:false,xtype:'textarea'}
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
        formPanel : 'searchCondition',
        url :basepath+'/ocrmCustViewQuery.json?custId='+_custId+'&&flag='+2
    })];
	
	
	var detailFormViewer = [{
		fields : ['CANTACT_DATE','CANTACT_CHANNEL'],
		fn : function(CANTACT_DATE,CANTACT_CHANNEL){
			CANTACT_DATE.readOnly=true;
			CANTACT_DATE.cls='x-readOnly';
			CANTACT_CHANNEL.readOnly=true;
			CANTACT_CHANNEL.cls='x-readOnly';
			return [CANTACT_DATE,CANTACT_CHANNEL];
		}
	},{
		columnCount : 0.95 ,
		fields : ['MARKET_RESULT'],
		fn : function(MARKET_RESULT){
			MARKET_RESULT.readOnly=true;
			MARKET_RESULT.cls='x-readOnly';
			return [MARKET_RESULT];
		}
	}];
	
	
	var beforeviewshow=function(view){
		if(view==getDetailView()){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		}
		
	};
	
	/**
	 * 结果域面板滑入后触发,系统提供listener事件方法
	 */
	var viewshow = function(theview){
		
	};	



