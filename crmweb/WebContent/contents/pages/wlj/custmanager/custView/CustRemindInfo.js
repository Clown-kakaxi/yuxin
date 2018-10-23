/**
 * 客户视图-客户提醒信息
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    var _custId;
    var url = basepath+'/custRemindInfoQuery.json?custId='+_custId;
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = !JsContext.checkGrant('custRemindInfo_detail');
    var lookupTypes=[
                     'REMIND_TYPE'
    ]
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'RULE_CODE',text:'信息提醒类型',translateType:'REMIND_TYPE',searchField: true},
				  {name:'MSG_CRT_DATE',text:'提醒生成日期',dataType:'date',searchField: true},
				  {name:'MSG_END_DATE',text:'提醒到期日期',dataType:'date',searchField: true},
				  {name:'REMIND_REMARK',text:'信息提醒内容',xtype:'textarea',searchField: false,hidden:true}
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
	 	hidden:JsContext.checkGrant('custRemindInfo_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custRemindInfoQuery.json?custId='+_custId
    })];
	
	var detailFormViewer = [{
		columnCount : 2 ,
		fields : ['RULE_CODE','MSG_CRT_DATE','MSG_END_DATE'],
		fn : function(RULE_CODE,MSG_CRT_DATE,MSG_END_DATE){
			RULE_CODE.readOnly=true;
			RULE_CODE.cls='x-readOnly';
			MSG_CRT_DATE.readOnly=true;
			MSG_CRT_DATE.cls='x-readOnly';
			MSG_END_DATE.readOnly=true;
			MSG_END_DATE.cls='x-readOnly';
			return [RULE_CODE,MSG_CRT_DATE,MSG_END_DATE];
		}
		
	},{
		columnCount : 0.95 ,
		fields : ['REMIND_REMARK'],
		fn : function(REMIND_REMARK){
			REMIND_REMARK.hidden=false;
			REMIND_REMARK.readOnly=true;
			REMIND_REMARK.cls='x-readOnly';
			return [REMIND_REMARK];
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
	
