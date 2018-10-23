/**
 * 客户满意度调查问卷
 * @author luyy
 * @since 2014-06-16
 */
imports([
			'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
			'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
			'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
			'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'
		]);
var sum_flag = false;
var title_count = 0;
var title_result = null;
var riskCharactStore =  new Ext.data.SimpleStore({
	fields : ['key', 'value'],
	data : [['1', '不满意'], ['2', '基本满意'], ['3', '很满意'],['4', '非常满意']]
});
var getQuestionData = function(){
	title_count = 0;
	questionStore.load({
		params : {
			'paperId':getSelectedData().data.ID
		 },
		callback:function(){
	        var questionReArr = [];
	        var preQuestionId = '';
	        questionArr = [];
	        var k = 0;
			for(var i=0,len = questionStore.getCount();i<len;i++){
				if(preQuestionId != questionStore.getAt(i).data.TITLE_ID){
					if(i >0 ){
						questionArr.push({
							xtype: 'radiogroup',
				            hideLabel: true,
				            columns: 4,
				            items:questionReArr
						});
					}
					k++;
					preQuestionId = questionStore.getAt(i).data.TITLE_ID;
					questionArr.push({xtype:'displayfield',hideLabel: true,value:k+"、"+questionStore.getAt(i).data.TITLE_NAME});
					questionReArr = [];
					questionReArr.push({boxLabel: questionStore.getAt(i).data.RESULT,name: preQuestionId,inputValue: questionStore.getAt(i).data.RESULT_ID});
//					continue;
				}else{
				questionReArr.push({boxLabel: questionStore.getAt(i).data.RESULT,name: preQuestionId,inputValue: questionStore.getAt(i).data.RESULT_ID});
				}
				if(i == len - 1){
					questionArr.push({
						xtype: 'radiogroup',
			            hideLabel: true,
			            items:questionReArr
					});
				}
			}
			title_count = k;
			rd_set.removeAll();
			rd_set.doLayout();
				rd_set.add({
					layout : 'form',
					columnWidth : 0.8,
					padding:'0 20 0',
					items:questionArr
	        });
				rd_set.doLayout();
		}
	});
};

var title_record = Ext.data.Record.create([{
	name : 'titleId',
	mapping : 'TITLE_ID'
}, {
	name : 'titleName',
	mapping : 'TITLE_NAME'
}, {
	name : 'titleRemark',
	mapping : 'TITLE_REMARK'
}, {
	name : 'titleIdL',
	mapping : 'titleId'
}]);

//定义风险评估试题查询
var questionArr = [];
var questionStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/TitleQuery!queryCustRiskQuestion.json'
	}),
	reader : new Ext.data.JsonReader({
		successProperty : 'success',
		// idProperty : 'TITLE_ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	},['TITLE_ID','TITLE_NAME','RESULT_ID','RESULT','RESULT_SCORING'])
});

var title_store = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/TitleQuery!loadTitleById.json'
			// success:function(response){
			// Ext.Msg.alert("数据",response.responseText);
			// }
		}),
	reader : new Ext.data.JsonReader({
				successProperty : 'success',
				// idProperty : 'TITLE_ID',
				messageProperty : 'message',
				root : 'data',
				totalProperty : 'count'
			}, title_record)
});

var search_cust = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '客户名称',
	text:'客户名称',
	allowBlank : false,
	name : 'custName',
	singleSelected : true,// 单选复选标志
	editable : false,
	hiddenName : 'custId',
	anchor : '80%'
});
var cust_form = new Ext.form.FormPanel({
//	id : 'cust_form',
	height : 80,
	region:'north',
	labelAlign : 'right',
	frame : true,
	items : [{
				layout : 'column',
				labelWidth : 80,
				items : [{
							layout : 'form',
							columnWidth : .33,
							items : [search_cust]
						}, {
							layout : 'form',
							columnWidth : .33,
							items : [{
										name : 'indageteQaScoring',
//										id : 'indageteQaScoring',
										xtype : 'textfield',
										fieldLabel : '调查问卷得分',
										maxLength : 10,
										maxLengthText : '最大长度为10',
										blankText : '该项为必填项',
										readOnly  : true,
										anchor : '80%'
									}]
						}, {
							layout : 'form',
							columnWidth : .33,
							items : [{
								store : riskCharactStore,
								labelStyle : 'text-align:right;',
								xtype : 'combo',
								resizable : true,
								fieldLabel : '客户满意度',
								name : 'satisfyType',
//								id:'satisfyType',
								hiddenName : 'satisfyType',
								valueField : 'key',
								displayField : 'value',
								mode : 'local',
								typeAhead : true,
								readOnly:true,
								forceSelection : true,
								triggerAction : 'all',
								emptyText : '',
								selectOnFocus : true,
								width : '100',
								anchor : '80%',
								labelSeparator:''
							},{
								name:'papersId',
//								id:'papersId',
								xtype:'textfield',
								hidden:true
							}]
						}]
			}]
});


var rd_set = new Ext.form.FormPanel({
	id:'rd_set',
	region:'center',
	title : '问卷调查',
	labelWidth : 200,
	height:300,
	autoScroll : true,
	labelAlign : 'right',
	collapsible : true,
	items : []
});

var url = basepath+'/paperManage.json?type=3';

var lookupTypes = ['OPTION_TYPE','AVAILABLE'];

var detailView = true;



var fields = [{name:'ID',hidden:true},
              {name:'PAPER_NAME',text:'问卷名称',dataType:'string',searchField:true,allowBlank:false},
              {name:'OPTION_TYPE',text:'问卷类型',translateType : 'OPTION_TYPE',allowBlank:false},
              {name:'AVAILABLE',text:'问卷状态',translateType:'AVAILABLE',allowBlank:false},
              {name:'CREATOR',text:'创建者',dataType:'string'},
              {name:'CREATE_ORG',text:'创建机构',dataType:'string'},
              {name:'PUBLISH_DATE',text:'发布日期',dataType:'date'},
              {name:'CREATE_DATE',text:'创建时间',dataType:'date'},
              {name:'REMARK',text:'问卷备注',xtype:'textarea'},
              {name:'HASQ',hidden:true,text:'是否选择试题'}
              ];



var detailFormViewer =[{
	fields : ['ID','OPTION_TYPE','PAPER_NAME','AVAILABLE','PUBLISH_DATE','CREATOR','CREATE_ORG','CREATE_DATE'],
	fn : function(ID,OPTION_TYPE,PAPER_NAME,AVAILABLE,PUBLISH_DATE,CREATOR,CREATE_ORG,CREATE_DATE){
		return [PAPER_NAME,OPTION_TYPE,AVAILABLE,PUBLISH_DATE,CREATOR,CREATE_ORG,CREATE_DATE,ID];
	}
},{
	columnCount : 1 ,
	fields : ['REMARK'],
	fn : function(REMARK){
		return [REMARK];
	}
}];

var customerView = [{
	title : '问卷调查',
	type : 'form',
	frame : true,
	recordView : true,
	buttonAlign : "center",
	items : [{
				id : 'opForm',
				xtyp:'form',
				layout : 'border',
				labelAlign : 'right',
				autoScroll : true,
				frame : true,
				buttonAlign : "center",
				items : [cust_form, rd_set]
			}],
	formButtons : [{
		text : '统计得分',
		fn : function(contentpanel, baseform) {
			var titleCount = Ext
					.encode(Ext.getCmp('rd_set').getForm().getValues(false)).split(',').length;// 获取当前答题个数
			if (title_count > titleCount) {
				Ext.Msg.alert('系统提示', '还有' + parseInt(title_count - titleCount)
								+ '道题未完成');
				return false;
			}
			var str = Ext.encode(Ext.getCmp('rd_set').getForm().getValues(false))
					.split(',');// 获取当前答题个数
			var strLength = str.length;
			var sumCount = 0;
			debugger;
			for (var i = 0; i < strLength - 5; i++) {
				var tempStr = str[i].split(':')[1].split('_')[2].substring(0,
						str[0].split(':')[1].split('_')[2].length - 1);
				sumCount += parseInt(tempStr);
			}
			cust_form.form.findField('indageteQaScoring').setValue(sumCount);
			if (parseFloat(sumCount) <= 50) {
				cust_form.form.findField('satisfyType').setValue('1');
			} else if (parseFloat(sumCount) <= 60) {
				cust_form.form.findField('satisfyType').setValue('2');
			} else if (parseFloat(sumCount) <= 85) {
				cust_form.form.findField('satisfyType').setValue('3');
			} else if (parseFloat(sumCount) <= 90) {
				cust_form.form.findField('satisfyType').setValue('4');
			} else {
				cust_form.form.findField('satisfyType').setValue('4');
			}
			Ext.Msg.alert("提示", "得分已统计！");
		}
	}, {
		text : '保存',
		fn : function(contentpanel, baseform) {
			if (!cust_form.form.isValid()) {
				Ext.Msg.alert('提示', '输入格式不合法，请重新输入');
				sum_flag = false;
				return;
			}
			var titleCount = Ext
					.encode(Ext.getCmp('rd_set').getForm().getValues(false)).split(',').length;// 获取当前答题个数
			if (title_count > titleCount) {
				Ext.Msg.alert('系统提示', '还有' + parseInt(title_count - titleCount)
								+ '道题未完成');
				return false;
			}
			Ext.Ajax.request({
						url : basepath
								+ '/ocrmFSeCustSatisfyList!addSatisfy.json',
						form : cust_form.form.id,
						method : 'POST',
						params : {
							title_result : Ext.encode(Ext.getCmp('rd_set').getForm()
									.getValues(false))
						},
						failure : function(form, action) {
							Ext.MessageBox.alert('提示', '操作失败！');
							sum_flag = false;
						},
						success : function(response) {
							Ext.MessageBox.alert('提示', '操作成功！');
							reloadCurrentData();
							sum_flag = false;
						}
					});
		}
	}, {
		text : '重置',
		fn : function(contentpanel, baseform) {
			Ext.getCmp('rd_set').getForm().reset();
			cust_form.form.reset();
		}
	}]
}];

var beforeviewshow =function(theView){
	if(!getSelectedData()){
		Ext.Msg.alert('系统提示','请选择一条记录');
		return false;
	}
}
var viewshow =function(theView){
	if(theView._defaultTitle =='问卷调查'){
		getQuestionData();
		cust_form.getForm().findField('papersId').setValue(getSelectedData().data.ID);
	}
}
recordselect = function(){
	if(getCurrentView()!=undefined){
		getQuestionData();
	}
};