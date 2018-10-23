/**
 * 本js主要负责显示客户群【批量调整反洗钱等级】窗口
 * 2014-09-02 update
 */

//群成员类型
var groupMemberType = ''; 
var fxqRiskLevel =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ_RISK_LEVEL'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
/* //客户群放大镜
var search_cust_group = new Com.yucheng.bcrm.common.CustGroup({ 
	fieldLabel : '选择客户群', 
	labelStyle: 'text-align:right;',
	name : 'custGroup',
	allowDtGroup:false,//不能选择动态客户群
    singleSelected:true,//单选复选标志
	editable : false,
	blankText:"请填写",
	//hidden:true,
	anchor : '85%',
	hiddenName:'groupStr'
});*/

//选择加入已有客户群还是新建客户群
var fxqForm = new Ext.form.FormPanel({
	 labelWidth : 80,
	 height : 200,
	 frame : true,
	 labelAlign : 'right',
	 region : 'center',
	 autoScroll : true,
	 buttonAlign : "center",
	 items : [{
		 layout : 'column',
    	 items : [ {
    		 columnWidth : .5,
    		 layout : 'form',
    		 items : [ 
    		 	{xtype : 'combo',name : 'fxqRiskLevel',fieldLabel : '反洗钱风险等级',forceSelection : true,resizable : true,labelStyle : 'text-align:right;',
                    triggerAction : 'all',mode : 'local', store : fxqRiskLevel, valueField : 'key',displayField : 'value',emptyText : '请选择',anchor : '85%'
                }]
    	 }]
	 }]
 });

//选择加入已有客户群还是新建客户群的窗口
var choseWin = new Ext.Window({
	title : '批量调整反洗钱等级',
	closeAction:'hide',
	height:'200',
	width:'600',
	modal : true,//遮罩
	buttonAlign:'center',
	layout:'fit',
	items:[fxqForm],
	buttons:[{
    	 text:'保存',
    	 handler: function(){
   
    	 if (fxqForm.form.findField('fxqRiskLevel').getValue()==null||fxqForm.form.findField('fxqRiskLevel').getValue()=='') {
    		 Ext.MessageBox.alert('系统提示信息', '请先选择风险等级！');
    		 return false;
    	 }
    	 Ext.MessageBox.confirm("系统提示", "是否确认等级修改？",
                 function(a) {
                     if (a == 'yes') {
                    	 
                    	//将选中的客户存入关联客户信息
                     	var fxqRiskLevel= fxqForm.form.findField('fxqRiskLevel').getValue();
                 		Ext.Ajax.request({
                 			url : basepath + '/antMoneyGradeBatchAdjust!batchUpdate.json',
                 			params:{
                 				'custId':idStr,
                 				'fxqRiskLevel':fxqRiskLevel
                 			},
                 			method : 'GET',
                 			waitMsg : '正在保存,请等待...',
                 			success :checkResult,
                 			failure :checkResult
                 		 });
                 		 function checkResult(response){
                			 debugger;
                			var resultArray = Ext.util.JSON.decode(response.status);
                			if (resultArray == 200 ||resultArray == 201) {
                				var decode=Ext.util.JSON.decode(response.responseText);
                				var itemId	=decode.itemId;
                				if(itemId!=""){
                					Ext.MessageBox.alert('系统提示信息', '操作失败，修改客户"'+itemId+'"正在流程中不允许更改');
                				}else{
                					var number =  decode.number;
                					Ext.MessageBox.alert('系统提示信息', '操作成功，修改客户'+number+'位');
                					//重新刷新反洗钱指标信息面板信息
                					var id = getSelectedData().data.ID;
                					antMoneyTargetStore.load({
                					params:{id:id
                						,
                						start : 0,
                						limit : parseInt(antMoneyTargetPagesize_combo.getValue())}
                				});
                				}
                			}else if(resultArray == 403) {
                		        Ext.Msg.alert('系统提示', response.responseText);
                			} else{
                				Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                			}
                			choseWin.hide();
                			idStr = '';
                		 }
                     }
    	 });
    	
		
     }
	         
 }, {
	 text:'重置',
	 handler:function(){
		 fxqForm.getForm().reset();
 	}
 }]
});

