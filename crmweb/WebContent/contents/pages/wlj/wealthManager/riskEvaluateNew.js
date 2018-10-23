/**
 * @description 风险评估
 * @author helin
 * @since 2014-06-19
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'
]);

Ext.QuickTips.init();

//数据字典定义
var lookupTypes = [
	'CUST_RISK_CHARACT' //客户风险特性
	,'RISK_EVA_STAT'	 //评估状态
	,'IF_FLAG'	 //是否标志
];

var url = basepath+'/custRiskEva.json';

var fields = [
    {name: 'CUST_Q_ID',hidden : true},
    {name: 'CUST_ID', text : '客户号', searchField: true},
    {name: 'CUST_NAME',text:'客户名称', searchField: true},
    {name: 'CUST_RISK_CHARACT',text:'客户风险特性',translateType : 'CUST_RISK_CHARACT'},
    {name: 'INDAGETE_QA_SCORING',text:'调查问卷得分', resutlWidth:80},
    {name: 'Q_STAT',text:'评估状态',translateType : 'RISK_EVA_STAT',resutlWidth:80},
    {name: 'LIMIT_DATE',text:'有效期', resutlWidth:100},
    {name: 'HIS_FLAG',text:'历史标志',translateType : 'IF_FLAG', resutlWidth:80},
    {name: 'EVALUATE_NAME',text:'评估人', resutlWidth:100},
    {name: 'EVALUATE_DATE',text:'评估时间',dataType:'date',format:'y-m-d',resutlWidth:100}, 
    {name: 'EVALUATE_RELAT_TELEPHONE',text:'评估人联系电话', resutlWidth:100},
    {name: 'EVALUATE_INST_NAME',text:'评估人机构', resutlWidth:150}
];

var createView = false;
var editView = false;
var detailView = false;

//定义风险评估试题查询
var questionArr = [];
var questionStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/custRiskEva!queryCustRiskQuestion.json'
	}),
	reader : new Ext.data.JsonReader({
		root : 'json.data'
	},['TITLE_ID','TITLE_NAME','RESULT_ID','RESULT','RESULT_SCORING'])
});
questionStore.load({
	callback:function(){
        var questionReArr = [];
        var preQuestionId = '';
        var k = 0;
		for(var i=0,len = questionStore.getCount();i<len;i++){
			if(preQuestionId != questionStore.getAt(i).data.TITLE_ID){
				if(i >0 ){
					questionArr.push({
						xtype: 'radiogroup',
			            hideLabel: true,
			            columns: 2,
			            items:questionReArr
					});
				}
				k++;
				preQuestionId = questionStore.getAt(i).data.TITLE_ID;
				questionArr.push({xtype:'displayfield',hideLabel: true,value:k+"、"+questionStore.getAt(i).data.TITLE_NAME});
				questionReArr = [];
				questionReArr.push({boxLabel: questionStore.getAt(i).data.RESULT,name: preQuestionId,inputValue: questionStore.getAt(i).data.RESULT_ID});
				continue;
			}
			questionReArr.push({boxLabel: questionStore.getAt(i).data.RESULT,name: preQuestionId,inputValue: questionStore.getAt(i).data.RESULT_ID});
			if(i == len - 1){
				questionArr.push({
					xtype: 'radiogroup',
		            hideLabel: true,
		            columns: 2,
		            items:questionReArr
				});
			}
		}
	}
});

//结果域扩展功能面板
var customerView = [{
	/**
	 * 自定义在线评估面板
	 */
	title:'在线评估',
	type: 'form',
	formButtons:[{
		/**
		 * 在线评估-保存按钮
		 */
		text : '提交',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','请选择要在线评估的客户！');
				return false;
			}
			var obj = contentPanel.getForm().getValues(false);
			//校验是否所有问题均已选择答案
			for(var i=0,len = questionStore.getCount();i<len;i++){
				if(!obj[questionStore.getAt(i).data.TITLE_ID]){
					Ext.Msg.alert('提示','请在完成风险测试题后再进行提交操作！');
					return false;
				}
			}
			Ext.Ajax.request({
				url : basepath + '/custRiskEva!commitRiskEva.json',
				method : 'POST',
				params :{
					questionObj : Ext.encode(obj)
				},
				waitMsg : '正在保存数据,请等待...',//提示信息
				success : function() {
					Ext.Msg.alert('提示', '操作成功！');
                    reloadCurrentData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
	}]
}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view._defaultTitle == '在线评估'){
		if (!getSelectedData()) {
			Ext.Msg.alert('提示', '请选择一条数据进行操作！');
			return false;
		}else if(view.contentPanel.items.length<1){
			view.contentPanel.add({
				layout : 'form',
				columnWidth : 1,
				items:[
					{xtype:'textfield',name: 'CUST_NAME',fieldLabel:'客户名称',readOnly:true,cls:'x-readOnly'},
					{xtype:'textfield',name:'CUST_ID',fieldLabel:'客户ID',hidden:true}
				]
			});
			view.contentPanel.doLayout();
	        view.contentPanel.add({
				layout : 'form',
				columnWidth : 0.8,
				padding:'0 20 0',
				items:questionArr
	        });
			view.contentPanel.doLayout();
		}
		view.contentPanel.getForm().reset();
	}
};

/**
 * 结果域面板滑入后触发,系统提供listener事件方法
 */
var viewshow = function(view){
	if(view._defaultTitle == '在线评估'){
		if(getSelectedData()){
        view.contentPanel.getForm().findField('CUST_NAME').setValue(getSelectedData().data.CUST_NAME);
        view.contentPanel.getForm().findField('CUST_ID').setValue(getSelectedData().data.CUST_ID);
		}
	}
};
