/**
 * 对私客户视图-客户拜访信息-日程
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		      
		        ]);
    var _custId;//视图传送的客户ID
    var url = basepath+'/ocrmCustViewQuery.json?custId='+_custId+'&&flag='+1;
    var formCfgs = false;
    var needCondition = true;
    var needGrid = true;
    var detailView = !JsContext.checkGrant('custVistInfo_detail');
    
    var lookupTypes=[
                     'VISIT_STAT',
                     'VISIT_TYPE'
                     ];


	var fields = [
				  {name:'SCH_START_TIME',text:'拜访开始时间',xtype:'datefield',format:'Y-m-d',searchField:true},
				  {name:'SCH_EDN_TIME',text:'拜访完成时间',xtype:'datefield',format:'Y-m-d',searchField:true},
				  {name:'VISIT_TYPE',text:'拜访方式',translateType:'VISIT_TYPE',searchField:true},
				  {name:'VISIT_STAT',text:'拜访状态',translateType:'VISIT_STAT',searchField:true},
				  {name:'VISIT_NOTE',text:'拜访内容',searchField:false,xtype:'textarea'}
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
		hidden:JsContext.checkGrant('custVistInfo_export'),
        formPanel : 'searchCondition',
        url :basepath+'/ocrmCustViewQuery.json?custId='+_custId+'&&flag='+1
    })];
	
	
	var detailFormViewer = [{
		fields : ['SCH_START_TIME','SCH_EDN_TIME','VISIT_TYPE','VISIT_STAT'],
		fn : function(SCH_START_TIME,SCH_EDN_TIME,VISIT_TYPE,VISIT_STAT){
			SCH_START_TIME.readOnly=true;
			SCH_START_TIME.cls='x-readOnly';
			SCH_EDN_TIME.readOnly=true;
			SCH_EDN_TIME.cls='x-readOnly';
			VISIT_TYPE.readOnly=true;
			VISIT_TYPE.cls='x-readOnly';
			VISIT_STAT.readOnly=true;
			VISIT_STAT.cls='x-readOnly';
			
			return [SCH_START_TIME,SCH_EDN_TIME,VISIT_TYPE,VISIT_STAT];
		}
	},{
		columnCount : 0.95 ,
		fields : ['VISIT_NOTE'],
		fn : function(VISIT_NOTE){
			VISIT_NOTE.readOnly=true;
			VISIT_NOTE.cls='x-readOnly';
			return [VISIT_NOTE];
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



