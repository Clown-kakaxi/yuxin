/**
 * @description 风险参数维护
 * @author helin
 * @since 2014-06-24
 */

Ext.QuickTips.init();

//数据字典定义
var lookupTypes = [
	'CUST_RISK_CHARACT' //客户风险特性
	,'RISK_EVA_STAT'	 //评估状态
	,'IF_FLAG'	 //是否标志
];

var url = basepath+'/riskParm.json';

var fields = [
    {name: 'ID',hidden : true},
    {name: 'RISK_CHARACT', text : '风险类型', searchField: true,translateType : 'CUST_RISK_CHARACT'},
    {name: 'LOWER_VALUE',text:'下限值（含）'},
    {name: 'UPPER_VALUE',text:'上限值（含）'},
    {name: 'REMARK',text:'备注',resutlWidth: 200}
];

var createView = false;
var editView = false;
var detailView = false;

var riskCharactCombo= new Ext.form.ComboBox({
	editable: false,
	mode : 'local',
	store: new Ext.data.Store(),
	triggerAction : 'all',
	displayField:'value',
	valueField:'key'
});
//自定义下拉框 反显数据
Ext.util.Format.comboRenderer = function(combo){
	return function(value){
		var record = combo.findRecord(combo.valueField, value);
		return record ? record.get(combo.displayField) : combo.valueNotFoundText;
	}
};

//结果域扩展功能面板
var customerView = [{
	/**
	 * 自定义参数设置面板
	 */
	title:'参数设置',
	type : 'grid',
	url : basepath + '/riskParm.json',
	pageable : true,
	grideditable : true,
	fields: {
		fields : [
			{name: 'ID',text: 'ID',hidden: true},
			{name: 'RISK_CHARACT', text: '风险类型',width:120,translateType : 'CUST_RISK_CHARACT'
				,editor: riskCharactCombo,renderer :Ext.util.Format.comboRenderer(riskCharactCombo)},
			{name: 'LOWER_VALUE',text:'下限值（含）',editor : new Ext.form.NumberField()},
		    {name: 'UPPER_VALUE',text:'上限值（含）',editor : new Ext.form.NumberField()},
		    {name: 'REMARK',text:'备注',width:200,editor : new Ext.form.TextField()}
		]
	},
	gridButtons:[{
		/**
		 * 参数设置添加
		 */
		text : '添加',
		fn : function(grid){
			var obj = {
					ID : '',
					RISK_CHARACT : '',
					LOWER_VALUE : '',
					UPPER_VALUE : '',
					REMARK : ''
				};
			var r = new Ext.data.Record(obj,null);
			grid.store.addSorted(r);
		}
	},{
		/**
		 * 参数设置删除
		 */
		text : '删除',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
            var selectRecords = grid.getSelectionModel().getSelections();
            if(selectLength < 1){
                Ext.Msg.alert('提示','请选择一条数据进行操作!');
                return false;
            }
            for(var i=0;i<selectLength;i++){
            	grid.store.remove(selectRecords[i]);
            }
		}
	},{
		/**
		 * 参数设置提交
		 */
		text : '提交',
		fn : function(grid){
			grid.stopEditing();
			//将Ext.data.store转换成json对象
			var storeToJson = function(jsondata){  
			    var listRecord;  
			    if(jsondata instanceof Ext.data.Store){  
			        listRecord = new Array();  
				    jsondata.each(function(record){  
				        listRecord.push(record.data);  
				    });  
			    }else if(jsondata instanceof Array){  
			        listRecord = new Array();  
			        Ext.each(jsondata,function(record){  
			            listRecord.push(record.data);  
			        });  
			    }  
			    return listRecord;  
			};
			var listRecord = storeToJson(grid.store);
			for(var i=0;i<listRecord.length;i++){
				if(listRecord[i].RISK_CHARACT == null || listRecord[i].RISK_CHARACT == undefined || listRecord[i].RISK_CHARACT === ""
					|| listRecord[i].LOWER_VALUE == null || listRecord[i].LOWER_VALUE == undefined || listRecord[i].LOWER_VALUE === ""
					|| listRecord[i].UPPER_VALUE == null || listRecord[i].UPPER_VALUE == undefined || listRecord[i].UPPER_VALUE === ""){
					Ext.Msg.alert('提示','请完整录入风险类型、下限值及上限值！');
					return false;
				}
				if(Number(listRecord[i].LOWER_VALUE) > Number(listRecord[i].UPPER_VALUE)){
					Ext.Msg.alert('提示','下限值不能大于上限值！');
					return false;
				}
				for(var j=i+1;j<listRecord.length;j++){
					if(listRecord[i].RISK_CHARACT == listRecord[j].RISK_CHARACT){
						Ext.Msg.alert('提示','存在风险类型重复的记录，请调整！');
						return false;
					}
					if((Number(listRecord[i].LOWER_VALUE) <= Number(listRecord[j].UPPER_VALUE) && Number(listRecord[i].LOWER_VALUE) >= Number(listRecord[j].LOWER_VALUE))
						|| (Number(listRecord[i].UPPER_VALUE) <= Number(listRecord[j].UPPER_VALUE) && Number(listRecord[i].UPPER_VALUE) >= Number(listRecord[j].LOWER_VALUE))
						|| (Number(listRecord[i].UPPER_VALUE) >= Number(listRecord[j].LOWER_VALUE) && Number(listRecord[i].LOWER_VALUE) <= Number(listRecord[j].LOWER_VALUE))
						|| (Number(listRecord[i].UPPER_VALUE) >= Number(listRecord[j].UPPER_VALUE) && Number(listRecord[i].UPPER_VALUE) <= Number(listRecord[j].UPPER_VALUE))){
						Ext.Msg.alert('提示','存在区间重复请检查！');
						return false;
					}
				}
			}
			Ext.Ajax.request({
                url: basepath + '/riskParm.json',
                method: 'POST',
                params: {
                    'listRecordJson': Ext.encode(listRecord)
                },
                success: function(response) {
                    reloadCurrentData();
                    var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
                    reloadCurrentData();
                   // Ext.Msg.alert('提示', '保存成功');
                },
                failure: function(response) {
                    Ext.Msg.alert('提示', '保存失败');
                }
            });
		}
	},{
    	xtype:'displayfield',
    	value: '<font color="red">&nbsp;&nbsp;注：添加、删除操作后必须点击“保存”按钮,操作才能生效!!!</font>'
    }]
}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view._defaultTitle == '参数设置'){
		riskCharactCombo.bindStore(findLookupByType('CUST_RISK_CHARACT'));
		view.setParameters({});
	}
};