/**
*@description 360客户视图 个人投资信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',//用户放大镜
        '/contents/pages/common/LovCombo.js'
		]);

var createView = true;
var editView = true;
var detailView = true;

var lookupTypes = ['ACC1300012',//币种
                   'INVESTMENT_TYPE'//投资类型
                   ];
var localLookup = {'REMIND_TYPE':
					[
                       {key:'1',value:'本人'},
                       {key:'2',value:'本机构主管'}
                    ]
};

var custId =_custId;
var url = basepath+'/acrmFCiPerInvestmentInfo.json?custId='+custId;

var fields = [
  		    {name: 'INVESTMENT_ID',hidden : true},
  		    {name: 'CUST_ID',hidden : true},
  		    {name: 'INVEST_AIM', text : '投资目的', searchField: true,allowBlank:false,resutlWidth:100}, 
  		    {name: 'INVEST_EXPECT', text : '投资预期',searchField: true,allowBlank:false,resutlWidth:70}, 
  		    {name: 'INVEST_TYPE', text : '投资种类',searchField:true,allowBlank:false,resutlWidth:70,translateType:'INVESTMENT_TYPE'},                                   
  		    {name: 'INVEST_AMT',text:'投资金额',dataType: 'number',allowBlank:false,resutlWidth:70},  
  		    {name: 'INVEST_CURR', text : '投资币种',translateType:'ACC1300012',allowBlank:false,resutlWidth:70},
  		    {name: 'INVEST_YIELD', text : '投资收益率%',dataType: 'number',allowBlank:false,resutlWidth:90},
  		    {name: 'INVEST_INCOME', text : '投资收益',dataType: 'number',allowBlank:false,resutlWidth:70},
  		    {name: 'START_DATE', text : '开始日期',xtype:'datefield',format:'Y-m-d',dataType:'date',allowBlank:false,resutlWidth:70},
  		    {name: 'END_DATE', text:'结束日期',xtype:'datefield',format:'Y-m-d',dataType:'date',allowBlank:false,resutlWidth:70},
  		    
  		    {name: 'LAST_UPDATE_SYS',  text : '最后更新系统',gridField:false,resutlWidth:70},
  		    {name: 'LAST_UPDATE_USER',  text : '最后更新人',gridField:false,resutlWidth:70},
  		    {name: 'LAST_UPDATE_TMM',  text : '最后更新时间',gridField:false},
  		    {name: 'TX_SEQ_NO',  text : '交易流水号',gridField:false}
  		   ];

var createFormViewer =[{
	fields : ['INVESTMENT_ID','CUST_ID','INVEST_AIM','INVEST_EXPECT','INVEST_TYPE','INVEST_AMT','INVEST_CURR','INVEST_YIELD','INVEST_INCOME','START_DATE','END_DATE'],
	fn : function(INVESTMENT_ID,CUST_ID,INVEST_AIM,INVEST_EXPECT,INVEST_TYPE,INVEST_AMT,INVEST_CURR,INVEST_YIELD,INVEST_INCOME,START_DATE,END_DATE){
		return [INVESTMENT_ID,CUST_ID,INVEST_AIM,INVEST_EXPECT,INVEST_TYPE,INVEST_AMT,INVEST_CURR,INVEST_YIELD,INVEST_INCOME,START_DATE,END_DATE];
	}
}];

var editFormViewer = [{
	fields : ['INVESTMENT_ID','CUST_ID','INVEST_AIM','INVEST_EXPECT','INVEST_TYPE','INVEST_AMT','INVEST_CURR','INVEST_YIELD','INVEST_INCOME','START_DATE','END_DATE'],
	fn : function(INVESTMENT_ID,CUST_ID,INVEST_AIM,INVEST_EXPECT,INVEST_TYPE,INVEST_AMT,INVEST_CURR,INVEST_YIELD,INVEST_INCOME,START_DATE,END_DATE){
		return [INVESTMENT_ID,CUST_ID,INVEST_AIM,INVEST_EXPECT,INVEST_TYPE,INVEST_AMT,INVEST_CURR,INVEST_YIELD,INVEST_INCOME,START_DATE,END_DATE];
	}
}];

var detailFormViewer = [{
	fields : ['INVESTMENT_ID','CUST_ID','INVEST_AIM','INVEST_EXPECT','INVEST_TYPE','INVEST_AMT','INVEST_CURR','INVEST_YIELD','INVEST_INCOME',
	          'START_DATE','END_DATE','LAST_UPDATE_SYS','LAST_UPDATE_USER','LAST_UPDATE_TMM','TX_SEQ_NO'],
	fn : function(INVESTMENT_ID,CUST_ID,INVEST_AIM,INVEST_EXPECT,INVEST_TYPE,INVEST_AMT,INVEST_CURR,INVEST_YIELD,INVEST_INCOME,START_DATE,END_DATE,
			LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO){
		return [INVESTMENT_ID,CUST_ID,INVEST_AIM,INVEST_EXPECT,INVEST_TYPE,INVEST_AMT,INVEST_CURR,INVEST_YIELD,INVEST_INCOME,START_DATE,END_DATE,
		        LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO];
	}
}];

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
},{
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var messageId=getSelectedData().data.INVESTMENT_ID;
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiPerInvestmentInfo!batchDestroy.json?messageId='+messageId,                                
                    success : function(){
                        Ext.Msg.alert('提示', '删除成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                        reloadCurrentData();
                    }
                });
		});
	}
}
}];

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};
/**新增、修改提交时,字段校验逻辑*/
var validates = [];