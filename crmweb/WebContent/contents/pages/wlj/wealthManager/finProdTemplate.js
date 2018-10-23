/**
 * @description 理财风险等级模板配置
 * @author helin
 * @since 2014-07-24
 */

Ext.QuickTips.init();

//数据字典定义
var lookupTypes = [
	'CUST_RISK_CHARACT'	//客户风险等级，即对应的理财风险等级
];

var url = basepath + '/finProdTemplate.json';

var fields = [
    {name: 'ID',hidden : true},
    {name: 'RISK_TYPE', text : '风险等级', searchField: true,translateType : 'CUST_RISK_CHARACT'},                                   
    {name: 'PROD_TYPE1',text:'存款(%)',dataType:'number',allowBlank: false},
    {name: 'PROD_TYPE2',text:'理财(%)',dataType:'number',allowBlank: false},
    {name: 'PROD_TYPE3',text:'保险(%)',dataType:'number',allowBlank: false},
    {name: 'PROD_TYPE4',text:'基金(%)',dataType:'number',allowBlank: false},
    {name: 'PROD_TYPE5',text:'其它(%)',dataType:'number',allowBlank: false}
];

var customerView=[{
	title:'修改',
	type:'form',
	autoLoadSeleted:true,
	groups:[{
		columnCount : 1,
		fields : ['RISK_TYPE','PROD_TYPE1','PROD_TYPE2','PROD_TYPE3','PROD_TYPE4','PROD_TYPE5'],
		fn : function(RISK_TYPE,PROD_TYPE1,PROD_TYPE2,PROD_TYPE3,PROD_TYPE4,PROD_TYPE5){
			RISK_TYPE.readOnly=true;
			RISK_TYPE.cls='x-readOnly';
			return [RISK_TYPE,PROD_TYPE1,PROD_TYPE2,PROD_TYPE3,PROD_TYPE4,PROD_TYPE5];
		}
	}],
	formButtons:[{
		text : '保存',
		fn : function(contentPanel, baseform){
			if (!baseform.isValid()) {
                Ext.MessageBox.alert('提示', '存在漏输入项或格式有误,请重新输入！');
                return false;
            }
            var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
            var total = Number(commintData.prodType1) + Number(commintData.prodType2) + Number(commintData.prodType3)
            	+ Number(commintData.prodType4) + Number(commintData.prodType5);
            if(total != 100){
            	Ext.MessageBox.alert('提示', '各类占比总和不等于100,请调整！');
            	return false;
            }
			Ext.Ajax.request({
                url: basepath + '/finProdTemplate.json',
                method: 'POST',
                params: commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '操作成功！');
                    reloadCurrentData();
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
		}
	}]
}];