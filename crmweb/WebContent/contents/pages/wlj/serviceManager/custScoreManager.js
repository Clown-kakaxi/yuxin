/**
 * 客户积分管理页面
 * @author luyy
 * @since 2014-06-09
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js'
]);

Ext.QuickTips.init();
var createAnna;  //附件列表部分

var createView = false;
var editView = false;
var detailView = false;
WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出
var url = basepath+'/ocrmFSeScore.json';
var lookupTypes = ['ADD_STATE'];

var fields = [
	  		    {name: 'ID',hidden : true},
	  		    {name:'CUST_ID',text:'客户编号',dataType:'string'},
	  		    {name:'CUST_NAME',text:'客户名称',dataType:'string'},
	  		    {name:'INSTITUTION',text:'机构',dataType:'string',dataType:'string',hidden:true},
	  		    {name:'INSTITUTION_NAME',text:'归属机构',dataType:'orgchoose',hiddenName:'INSTITUTION',searchField: true,searchType:'SUBTREE'},
	  		    {name:'MGR_NAME',text:'客户经理',dataType:'string',searchField: true},
	  		    {name:'SCORE_TOTAL',text:'可用积分',resutlWidth:70,decimalPrecision : 0 ,dataType:'numberNoDot'},
	  		    {name:'SCORE_USED',text:'已用积分',resutlWidth:70,decimalPrecision : 0 ,dataType:'numberNoDot',minValue:0},
	  		    {name:'SCORE_TODEL',text:'待减积分',dataType:'number',hidden:'true'},
	  		    {name:'SCORE_ADD',text:'拟加积分',resutlWidth:70,decimalPrecision : 0 ,dataType:'numberNoDot',minValue:0},
	  		    {name:'ADD_STATE',text:'拟加状态',dataType:'string',translateType : 'ADD_STATE'},
	  		    {name:'ADD_REASON',text:'拟加原因',xtype:'textarea'},
	  		    {name:'ADD_DATE',text:'拟加日期',dataType:'date'},
	  		    {name:'COUNT_NUM',text:'客户累计积分',dataType:'numberNoDot',hidden:true},
	  		    {name:'CUST_CUM_COUNT',text:'客户当月积分',dataType:'numberNoDot',hidden:true},
	  		    {name:'CUST_CUM_COST',text:'客户当月消费积分',dataType:'numberNoDot',hidden:true},
	  		    {name:'CUST_COST_SUM',text:'客户累计消费积分',dataType:'numberNoDot',hidden:true},
	  		    {name:'ADD_ID',dataType:'string',hidden:true}
	  		];

var tbar = [{
	text:'积分录入',
	hidden:JsContext.checkGrant("_insertScore"),
	handler:function(){
		showCustomerViewByTitle('积分录入');	
	}
}];

var customerView = [{
	title : '积分录入',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		fields : ['ID','CUST_ID','CUST_NAME','SCORE_TOTAL','SCORE_USED','SCORE_ADD','ADD_STATE','ADD_DATE','ADD_ID','COUNT_NUM','CUST_CUM_COUNT','CUST_CUM_COST','CUST_COST_SUM'],
		fn : function(ID,CUST_ID,CUST_NAME,SCORE_TOTAL,SCORE_USED,SCORE_ADD,ADD_STATE,ADD_DATE,ADD_ID,COUNT_NUM,CUST_CUM_COUNT,CUST_CUM_COST,CUST_COST_SUM){
			CUST_ID.readOnly = true ;
			CUST_NAME.readOnly = true ;
			SCORE_TOTAL.readOnly = true ;
			SCORE_TOTAL.readOnly = true ;
			ADD_STATE.readOnly = true ;
			CUST_ID.cls='x-readOnly' ;
			CUST_NAME.cls='x-readOnly' ;
			SCORE_TOTAL.cls='x-readOnly' ;
			SCORE_TOTAL.cls='x-readOnly' ;
			ADD_STATE.cls='x-readOnly' ;
			ADD_STATE.hidden = true;
			ADD_DATE.hidden = true;
			COUNT_NUM.hidden = true;
			CUST_CUM_COUNT.hidden = true;
			CUST_CUM_COST.hidden = true;
			CUST_COST_SUM.hidden = true;
			return [CUST_ID,CUST_NAME,SCORE_TOTAL,SCORE_USED,SCORE_ADD,ADD_STATE,ADD_DATE,ADD_ID,ID,COUNT_NUM,CUST_CUM_COUNT,CUST_CUM_COST,CUST_COST_SUM];
		}
	},{
		columnCount : 1 ,
		fields : ['ADD_REASON'],
		fn : function(ADD_REASON){
			ADD_REASON.anchor = '90%';
			return [ADD_REASON];
		}
	},{
		columnCount:1,
		fields:['ADD_REASON'],
		fn:function(ADD_REASON){
			createAnna = createAnnGrid(false,true,false,'<font color="red">(保存信息后可操作附件列表)</font>');
			return [createAnna];
		}
	}],
	formButtons : [{
		text : '保存',
		fn : function(formPanel,basicForm){
			if(!basicForm.isValid()){
				Ext.Msg.alert('提示', '输入项格式错误,!' );
				return false;
			}
			if(Number(formPanel.getForm().getFieldValues().SCORE_ADD) == 0){
				Ext.Msg.alert('提示', '拟加积分不能为0!' );
				return false;
			}
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/ocrmFSeScore!save.json',
				method : 'POST',
				params : commintData,
				success : function(response) {
					var ids = response.responseText;
					var id = ids.split('#');
					createAnna.tbar.setDisplayed(true);
					formPanel.getForm().setValues({
							ID : id[0],
							ADD_ID:id[1]
					}); 
		        	uploadForm.relaId = id[1];
				    createAnna.tbar.setDisplayed(true);
					Ext.Msg.alert('提示','操作成功！');
					reloadCurrentData();
				}
			});
		}
	},{
		text:'提交',
		fn:function(formPanel,basicForm){
			if(formPanel.getForm().getValues().ADD_ID == ''||formPanel.getForm().getValues().ADD_ID == undefined){
				Ext.Msg.alert('提示','请先执行保存操作！');
				return false;
			}else{
				 Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
					url : basepath + '/ocrmFSeScore!initFlow.json',
					method : 'GET',
					params : {
						instanceid:formPanel.getForm().getValues().ADD_ID, //将id传给后台关联流程的实例号（唯一）
						name:formPanel.getForm().getValues().CUST_NAME
					},
					waitMsg : '正在提交申请,请等待...',										
					success : function(response) {
//						Ext.Ajax.request({
//							url : basepath + '/ocrmFSeScore!initFlowJob.json',
//							method : 'POST',
//							params : {
//								instanceid:formPanel.getForm().getValues().ADD_ID //将id传给后台关联流程的实例号（唯一）
//							},success : function() {
//								Ext.Msg.alert('提示', '提交成功!');	
//								reloadCurrentData();
//								hideCurrentView();
//							},	
//							failure : function() {
//								Ext.Msg.alert('提示', '提交失败,请手动到代办任务中提交!');	
//							}
//						});
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败');
						reloadCurrentData();
					}
				});
			}
		}
	}]
}];
var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据！');
		return false;
	}else{
		var state = getSelectedData().data.ADD_STATE;
		if(state == '1' ||state == '6'||state == '7'){
		}else{
			Ext.Msg.alert('提示','数据正在审批复核中，不可操作！');
			return false;
		}
	}
};
var viewshow = function(view){
	if(view._defaultTitle == '积分录入'){
		var state = getSelectedData().data.ADD_STATE;
		if(state == '1' ){//状态为草稿  加载附件列表数据
			uploadForm.relaId = getSelectedData().data.ADD_ID;
			uploadForm.modinfo = 'scoreAdd';
			createAnna.tbar.setDisplayed(true);
			var condi = {};
            condi['relationInfo'] = getSelectedData().data.ADD_ID;
            condi['relationMod'] = 'scoreAdd';
            Ext.Ajax.request({
                url:basepath+'/queryanna.json',
                method : 'GET',
                params : {
                    "condition":Ext.encode(condi)
                },
                failure : function(a,b,c){
                    Ext.MessageBox.alert('查询异常', '查询失败！');
                },
                success : function(response){
                    var anaExeArray = Ext.util.JSON.decode(response.responseText);
                    	createAnna.store.loadData(anaExeArray.json.data);
                    	createAnna.getView().refresh();
                }
            });
			
		}else if(state == '6'||state == '7'){//状态为 无
			uploadForm.relaId = '';
			uploadForm.modinfo = 'scoreAdd';
			createAnna.tbar.setDisplayed(false);
			getCustomerViewByTitle('积分录入').contentPanel.getForm().setValues({
				SCORE_ADD : 0,
				ADD_STATE : '1',
				ADD_REASON: '',
				ADD_ID:'',
				ADD_DATE:new Date().format('Y-m-d')
			});
			createAnna.store.removeAll();
        	createAnna.getView().refresh();
			
		}
	}
};
