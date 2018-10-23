/**
 * @description 评级方案页面
 * @author luyy
 * @since 2014-07-16
 */
var createView = false;
var editView = false;
var detailView = false;

var lookupTypes = ['IF_FLAG','GRADE_TYPE','SCHEME_TIME','TYPE_RANGE'];

var url = basepath + '/ocrmFCiGradeSchemeManage.json';

var fields = [{name:'SCHEME_ID',text:''},
              {name:'SCHEME_NAME',text:'方案名称',dataType:'string',searchField:true},
              {name:'GRADE_USEAGE',text:'方案类型',translateType:'GRADE_TYPE',searchField:true},
              {name:'GRADE_TYPE',text:'评级客户类型',translateType:'TYPE_RANGE',searchField:true},
              {name:'IS_USED',text:'是否启用',translateType:'IF_FLAG',searchField:true},
              {name:'GRADE_FREQUENCY',text:'评级频率',translateType:'SCHEME_TIME',searchField:true},
              {name:'GRADE_BEGIN_DATE',text:'评级起始日期',dataType:'string'},
              {name:'GRADE_END_DATE',text:'评级结束日期',dataType:'string'},
              {name:'GRADE_FORMULA_EXPLAIN',text:'评级公式解释',hidden:true},
              {name:'GRADE_FORMULA',text:'评级公式',hidden:true},
              {name:'CREATE_USER_NAME',text:'创建人',dataType:'string'},
              {name:'LAST_UPDATE_USER_NAME',text:'最近更新人',dataType:'string'},
              {name:'MEMO',text:'备注',dataType:'string'}];


var tbar = [{
	text:'删除',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.IS_USED != '0' ){
					Ext.Msg.alert('提示','只能选择未启用的记录！');
					return false;
				}
				ID += getAllSelects()[i].data.SCHEME_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/ocrmFCiGradeSchemeManage!destroyBatch.json',  
                    params : {
                    	idStr : ID
						},
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
},{
	text:'启用',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.IS_USED != '0' ){
					Ext.Msg.alert('提示','只能选择未启用的记录！');
					return false;
				}
				ID += getAllSelects()[i].data.SCHEME_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定启用吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/ocrmFCiGradeSchemeManage!batchUse.json',  
                    params : {
							ids : ID,
							use:'yes'
						},
                    success : function(){
                        Ext.Msg.alert('提示', '启用成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '启用失败');
                        reloadCurrentData();
                    }
                });
			});			
		
		}
	}
},{
	text:'禁用',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.IS_USED != '1' ){
					Ext.Msg.alert('提示','只能选择启用的记录！');
					return false;
				}
				ID += getAllSelects()[i].data.SCHEME_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定禁用吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/ocrmFCiGradeSchemeManage!batchUse.json',  
                    params : {
							ids : ID,
							use:'no'
						},
                    success : function(){
                        Ext.Msg.alert('提示', '禁用成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '禁用失败');
                        reloadCurrentData();
                    }
                });
			});			
		
		}
	}
},{
	text :'新增',
	handler :function(){
		if(configForm.getForm().getEl()){
			configForm.getForm().getEl().dom.reset();
		}
		configForm.getForm().findField('isUsed').setValue("1");
		setFormFieldDisabled(false);
		configForm.getForm().findField("gradeType").setReadOnly(false);
		configForm.getForm().findField("gradeType").removeClass('x-readOnly');
		showCustomerViewByIndex(0);
		infoForm.buttons[0].show();
		storeLevel.removeAll();
		levelGrid.getColumnModel().setHidden(5,true);
	}
},{
  text :'修改',
	handler :function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		var data = translateDataKey(getSelectedData().data,1);
		var a = {'data':data};
		configForm.getForm().loadRecord(a);
		//加载等级数据
		storeLevel.reload({
			params:{
				GRADE_USEAGE:getSelectedData().data.GRADE_USEAGE,
				SCHEME_ID:getSelectedData().data.SCHEME_ID
			}
		});
		setFormFieldDisabled(false);
		if(getSelectedData().data.GRADE_USEAGE=='1'){
			configForm.getForm().findField("gradeType").setValue('1');
			configForm.getForm().findField("gradeType").setReadOnly(true);
			configForm.getForm().findField("gradeType").addClass('x-readOnly');
			levelGrid.getColumnModel().setHidden(5,false);
		}else{
			configForm.getForm().findField("gradeType").setReadOnly(false);
			configForm.getForm().findField("gradeType").removeClass('x-readOnly');
			levelGrid.getColumnModel().setHidden(5,true);
		}
		showCustomerViewByIndex(0);
		infoForm.buttons[0].show();
	}
},{
	  text :'查看',
		handler :function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择数据！');
				return false;
			}
			var data = translateDataKey(getSelectedData().data,1);
			var a = {'data':data};
			configForm.getForm().loadRecord(a);
			//加载等级数据
			storeLevel.reload({
				params:{
					GRADE_USEAGE:getSelectedData().data.GRADE_USEAGE,
					SCHEME_ID:getSelectedData().data.SCHEME_ID
				}
			});
			if(getSelectedData().data.GRADE_USEAGE=='1'){
				configForm.getForm().findField("gradeType").setValue('1');
				configForm.getForm().findField("gradeType").setReadOnly(true);
				configForm.getForm().findField("gradeType").addClass('x-readOnly');
				levelGrid.getColumnModel().setHidden(5,false);
			}else{
				configForm.getForm().findField("gradeType").setReadOnly(false);
				configForm.getForm().findField("gradeType").removeClass('x-readOnly');
				levelGrid.getColumnModel().setHidden(5,true);
			}
			setFormFieldDisabled(true);
			showCustomerViewByIndex(0);
			infoForm.buttons[0].hide();
		}

	
}];

/** ******************客户类型******************** */
var nlStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=TYPE_RANGE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, ['key', 'value'])
});
/** ******************评级类型******************** */
var typeStore1 = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=GRADE_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, ['key', 'value'])
});

/** ******************是否启用******************** */
var irStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=IF_FLAG'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, ['key', 'value'])
});

/** ******************评级频率******************** */
var pjplStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=SCHEME_TIME'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, ['key', 'value'])
});
/** ******************贵宾卡等级******************** */
var cardStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=GOODS_CUST_LEVEL'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, ['key', 'value'])
});

var configForm = new Ext.FormPanel({
	frame : true,
	border : false,
	labelAlign : 'right',
	standardSubmit : false,
	layout : 'form',
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [
				{xtype : 'textfield',fieldLabel : '<font color=red>*</font>方案名称',name : 'schemeName',allowBlank : false,anchor : '90%'}
			]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [{
				store : typeStore1,
				xtype : 'combo',
				resizable : true,
				hiddenName : 'gradeUseage',
				name : 'gradeUseage',
				fieldLabel : '<font color=red>*</font>方案类型',
				valueField : 'key',
				displayField : 'value',
				allowBlank : false,
				mode : 'local',
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%',
				listeners : {
					"select" : function() {
						var type = configForm.getForm().findField("gradeUseage").getValue();
						storeLevel.reload({
							params:{
								GRADE_USEAGE:type,
								SCHEME_ID:''
							}
						});
						if(type == '1'){
							configForm.getForm().findField("gradeType").setValue('1');
							configForm.getForm().findField("gradeType").setReadOnly(true);
							configForm.getForm().findField("gradeType").addClass('x-readOnly');
							levelGrid.getColumnModel().setHidden(5,false);
						}else{
							configForm.getForm().findField("gradeType").setReadOnly(false);
							configForm.getForm().findField("gradeType").removeClass('x-readOnly');
							levelGrid.getColumnModel().setHidden(5,true);
						}
					}
				}
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [{
				store : nlStore,
				xtype : 'combo',
				resizable : true,
				hiddenName : 'gradeType',
				name : 'gradeType',
				fieldLabel : '<font color=red>*</font>客户类型',
				valueField : 'key',
				displayField : 'value',
				allowBlank : false,
				mode : 'local',
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				store : irStore,
				xtype : 'combo',
				resizable : true,
				name:"isUsed",
				hiddenName : 'isUsed',
				fieldLabel : '<font color=red>*</font>是否启用',
				valueField : 'key',
				displayField : 'value',
				allowBlank : false,
				mode : 'local',
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%'
			}]
		}]
	}, {
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					store : pjplStore,
					xtype : 'combo',
					resizable : true,
					name:"gradeFrequency",
					hiddenName : 'gradeFrequency',
					fieldLabel : '<font color=red>*</font>评级频率',
					valueField : 'key',
					allowBlank : false,
					displayField : 'value',
					mode : 'local',
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					anchor : '90%'
				}]
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'datefield',
				fieldLabel : '<font color=red>*</font>评级起始日期',
				format : 'Y-m-d',
				name : 'gradeBeginDate',
				allowBlank : false,
				selectOnFocus : true,
				anchor : '90%'
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'datefield',
				fieldLabel : '<font color=red>*</font>评级结束日期',
				format : 'Y-m-d',
				name : 'gradeEndDate',
				allowBlank : false,
				selectOnFocus : true,
				anchor : '90%'
			}]
		}]
	}, {
		layout : 'form',
		items : [{
			xtype : 'textarea',
			fieldLabel : '<font color=red>*</font>评级公式',
			name : 'gradeFormula',
			allowBlank : false,
			readOnly : true,
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '<font color=red>*</font>评级公式解释',
			name : 'gradeFormulaExplain',
			allowBlank : false,
			readOnly : true,
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '备注',
			name : 'memo',
			anchor : '95%'
		}, {
			xtype : 'textfield',
			fieldLabel : '方案编号',
			name : 'schemeId',
			hidden : true,
			anchor : '90%'
		}]
	}]
});

//等级store
var storeLevel = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/ocrmFCiGradeSchemeManage!getLevels.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'data'
	}, [
		{name:'LEVEL_ID'},
	    {name:'LEVEL_NAME'},
	    {name:'LEVEL_SHOW'},
	    {name:'LEVEL_LOWER'},
	    {name:'LEVEL_UPPER'},
	    {name:'CARD_LEVEL'}
	])
});
var cm = new Ext.grid.ColumnModel([
	{header : '等级ID',hidden:true,dataIndex : 'LEVEL_ID'},
     {header : '等级',hidden:true,dataIndex : 'LEVEL_NAME'}, 
     {header : '评级等级',dataIndex : 'LEVEL_SHOW'},
     {header : '指标总分下限(包含)',dataIndex : "LEVEL_LOWER",
    	 editor:new Ext.form.NumberField({allowBlank : true,decimalPrecision : 2})},
     {header : '指标总分上限(不包含)',dataIndex : "LEVEL_UPPER",
    		 editor:new Ext.form.NumberField({allowBlank : true,decimalPrecision : 2})},
     {header:'可发卡等级',dataIndex:'CARD_LEVEL',hidden:true,editor:{
    	xtype:'combo',
    	store : cardStore,
    	mode : 'local',
    	triggerAction : 'all',
    	valueField : 'key',
    	displayField : 'value',
    	forceSelection:true,
		resizable:true,
		typeAhead : true,
		emptyText : '请选择',
    	listeners:{
        	select:function(){
        		var valuefind = this.value;
        		this.fireEvent('blur',this);
        	}
    	}
    	},
    	renderer:function(val){
        	if(val!=''){
        		var stolength = cardStore.data.items;
        		var i=0;
        		for(i=0;i< stolength.length;i++){
        			if(stolength[i].data.key==val){
        					return stolength[i].data.value;
        			}
        		}
        	}
        	return val;	
    	}
    }
]);
var levelGrid = new Ext.grid.EditorGridPanel({
	title : "评级结果细项",
	height : 230,
	frame : true,
	store : storeLevel,
	clicksToEdit: 1,
	cm : cm
});
var infoForm = new Ext.Panel({
	autoScroll : true,
    buttonAlign : "center",
    items : [ configForm,levelGrid],
    buttons : [{
	     text : '保存', 
	     handler :function(){
			if (!configForm.getForm().isValid()) {
				Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
				return false;
			}
			var _date = new Date();
			var _pstartDate = configForm.form.findField('gradeBeginDate').getValue();
			var _pendDate = configForm.form.findField('gradeEndDate').getValue();
			if (_pstartDate.format('Y-m-d') < _date.format('Y-m-d')) {
				Ext.MessageBox.alert('提示', '评级起始日期不能小于今天');
				return false;
			}
			if (_pendDate < _pstartDate) {
				Ext.MessageBox.alert('提示', '评级结束日期不能小于评级起始日期');
				return false;
			}
			var json0 = {'levelName':[]};
			var json1 = {'levelLower':[]};
			var json2 = {'levelUpper':[]};
			var json3 = {'cardLevel':[]};
			for(var i=0;i<storeLevel.getCount();i++){
				var temp=storeLevel.getAt(i);
		    	if(temp.data.LEVEL_LOWER!=''&&temp.data.LEVEL_UPPER!=''){//都不空时，判断是否设置错误
		        	if(Number(temp.data.LEVEL_LOWER)>=Number(temp.data.LEVEL_UPPER)){
		        		Ext.Msg.alert('系统提示','客户等级'+temp.data.LEVEL_SHOW+'指标上限小于下线值，请修改!');
		        		return false;
		        	}
		        }
		    	if(temp.data.LEVEL_LOWER==''&&temp.data.LEVEL_UPPER==''){//都为空需要补充
		        		Ext.Msg.alert('系统提示','客户等级'+temp.data.LEVEL_SHOW+'指标上限、下线值均为空，请修改!');
		        		return false;
		        }
		    	if(configForm.form.findField('gradeUseage').getValue()== '1'){//当为零售客户评级时，判断发卡等级
		    		if(temp.data.CARD_LEVEL==''){
		    			Ext.Msg.alert('系统提示','客户等级'+temp.data.LEVEL_SHOW+'可发卡等级为空，请修改!');
		        		return false;
		    		}
		    	}
		    	json0.levelName.push(temp.data.LEVEL_NAME);
		        json1.levelLower.push(temp.data.LEVEL_LOWER);
		        json2.levelUpper.push(temp.data.LEVEL_UPPER);
		        json3.cardLevel.push(temp.data.CARD_LEVEL);
			}
			var commintData = configForm.getForm().getFieldValues();
			commintData.levelName = Ext.encode(json0),
			commintData.levelLower = Ext.encode(json1),
			commintData.levelUpper = Ext.encode(json2),
			commintData.cardLevel = Ext.encode(json3),
			Ext.Msg.wait('正在保存，请稍后......', '系统提示');
			Ext.Ajax.request({
				url : basepath+ '/ocrmFCiGradeSchemeManage!saveData.json',
				params : commintData,
				method : 'POST',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Msg.alert('提示', '操作成功');
					reloadCurrentData();
				},
				failure : function(response) {
					Ext.Msg.alert('提示','操作失败');
				}
			});
		}
	}]
});

var customerView = [{
	title:'方案设置',
	xtype:'panel',
	hideTitle:true,
	items:[infoForm]	
}];

/**
 * 设置form表单可编辑与否
 * @param {} disable true or false
 */
var setFormFieldDisabled = function(disable){
	configForm.getForm().findField("schemeName").setDisabled(disable);
	configForm.getForm().findField("gradeUseage").setDisabled(disable);
	configForm.getForm().findField("gradeType").setDisabled(disable);
	configForm.getForm().findField("isUsed").setDisabled(disable);
	configForm.getForm().findField("gradeFrequency").setDisabled(disable);
	configForm.getForm().findField("gradeBeginDate").setDisabled(disable);
	configForm.getForm().findField("gradeEndDate").setDisabled(disable);
	configForm.getForm().findField("gradeFormula").setDisabled(disable);
	configForm.getForm().findField("gradeFormulaExplain").setDisabled(disable);
	configForm.getForm().findField("memo").setDisabled(disable);
	
	if(disable){
		configForm.getForm().findField("schemeName").addClass('x-readOnly');
		configForm.getForm().findField("gradeUseage").addClass('x-readOnly');
		configForm.getForm().findField("gradeType").addClass('x-readOnly');
		configForm.getForm().findField("isUsed").addClass('x-readOnly');
		configForm.getForm().findField("gradeFrequency").addClass('x-readOnly');
		configForm.getForm().findField("gradeBeginDate").addClass('x-readOnly');
		configForm.getForm().findField("gradeEndDate").addClass('x-readOnly');
		configForm.getForm().findField("gradeFormula").addClass('x-readOnly');
		configForm.getForm().findField("gradeFormulaExplain").addClass('x-readOnly');
		configForm.getForm().findField("memo").addClass('x-readOnly');
	}else{
		configForm.getForm().findField("schemeName").removeClass('x-readOnly');
		configForm.getForm().findField("gradeUseage").removeClass('x-readOnly');
		configForm.getForm().findField("gradeType").removeClass('x-readOnly');
		configForm.getForm().findField("isUsed").removeClass('x-readOnly');
		configForm.getForm().findField("gradeFrequency").removeClass('x-readOnly');
		configForm.getForm().findField("gradeBeginDate").removeClass('x-readOnly');
		configForm.getForm().findField("gradeEndDate").removeClass('x-readOnly');
		configForm.getForm().findField("gradeFormula").removeClass('x-readOnly');
		configForm.getForm().findField("gradeFormulaExplain").removeClass('x-readOnly');
		configForm.getForm().findField("memo").removeClass('x-readOnly');
	}
	
};

/******************gradeFormula  评级公式处理*******************/
//打开页面，进行“评级公式”中指标及对应名称的查询，将结果放在一个JSON对象中
var indexMap = null;
function getIndexMap() {
	var type = configForm.getForm().findField("gradeUseage").getValue();
	if(type == '1')
		type = 'IND';
	else
		type = 'FXQ';
	var indexCode = configForm.getForm().findField("gradeFormula").getValue();
	Ext.Ajax.request({
				async : false,
				url : basepath + '/ocrmFCiGradeSchemeManage!getIndexMap.json',
				params : {
					'indexPre' : type,
					'indexCodeLength' : 6,
					'indexCode' : indexCode
				},
				method : 'GET',
				waitMsg : '正在查询数据,请等待...',
				success : function(response) {
					indexMap = Ext.util.JSON.decode(response.responseText);
					transCode2Name();
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '查询指标数据出错！');
				}
			});
};
//校验评级公式正确性：正确，返回true；错误，返回false
//逻辑处理：将评级公式中的指标编码替换成数字20，然后调用js的eval方法执行表达式运算，如果出错，证明评级公式配置有问题，给出提示
function checkFormula() {
	var result = true;
	var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
	if (wtValue && wtValue != null && wtValue != "") {
		for (var key in indexMap) {
			wtValue = wtValue.replace(new RegExp(key, "gm"), "(20)");// 将指标编码转换成数字（转换成(20)），替换所有指标（如果有多个相同指标，替换所有相同的指标）
		}
		// 执行公式
		try {
			eval(wtValue);
		} catch (e) {
			result = false;
		}
	}
	return result;
}
//将“评级公式”转换成中文
function transCode2Name() {
	var wtValue = Ext.getCmp('FORMULAWINDOWT').getValue();
	if (wtValue && wtValue != "") {
		// 将“评级公式”中的指标编码转换成指标名称，其它字符不变
		for (var key in indexMap) {
			// wtValue = wtValue.replace(key,
			// indexMap[key]);//替换单个指标（如果有多个相同指标，只替换第一个）
			wtValue = wtValue.replace(new RegExp(key, "gm"), indexMap[key]);// 替换所有指标（如果有多个相同指标，替换所有相同的指标）
		}
	}
	Ext.getCmp('FORMULAWINDOWW').setValue(wtValue);// 赋值
	Ext.getCmp('FORMULAWINDOWT').setDisabled(false);// 让“评级公式”输入框可编辑
}
//在textarea的光标处增加字符
function insertWT(value) {
	var FORMULAWINDOWT = Ext.getCmp("FORMULAWINDOWT");
	if (Ext.isIE) {
		insertAtCaretWT(FORMULAWINDOWT.el.dom, value);
	} else {
		var startPos = FORMULAWINDOWT.el.dom.selectionStart;
		var endPos = FORMULAWINDOWT.el.dom.selectionEnd;
		FORMULAWINDOWT.el.dom.value = FORMULAWINDOWT.el.dom.value.substring(0,
				startPos)
				+ value
				+ FORMULAWINDOWT.el.dom.value.substring(endPos,
						FORMULAWINDOWT.el.dom.value.length);

		FORMULAWINDOWT.el.focus();
		FORMULAWINDOWT.el.dom.setSelectionRange(endPos + value.length, endPos
						+ value.length);
	}
}

function storeCaretWT() {
	if (Ext.getCmp("FORMULAWINDOWT").el.dom.createTextRange) {
		Ext.getCmp("FORMULAWINDOWT").el.dom.curRange = document.selection
				.createRange().duplicate();
	}
}

// 给textarea定义单击事件
Ext.getDoc().on('click', function handleDocClick(e) {
	if (Ext.getCmp("FORMULAWINDOWT")
			&& document.activeElement.id == "FORMULAWINDOWT") {
		if (Ext.getCmp("FORMULAWINDOWT").el.dom.createTextRange) {
			Ext.getCmp("FORMULAWINDOWT").el.dom.curRange = document.selection
					.createRange().duplicate();
		}
	}
});
//在textarea的光标处插入字符
function insertAtCaretWT(txtobj, txt) {
	if (txtobj.curRange) {
		Ext.getCmp("FORMULAWINDOWT").el.focus();
		txtobj.curRange.text = txt;
		txtobj.curRange.select();
	} else {
		txtobj.focus();
		storeCaretWT(txtobj);
		insertAtCaretWT(txtobj, txt);
	}
}

// 屏蔽键盘事件
function maskKeyEvent(event) {
	event.stopEvent();
}

// 删除textarea中光标前的一个字符
function delContent() {
	var cusorPos = getCusorPostion();// 获取光标位置
	if (cusorPos > 0) {
		// 光标位置大于0（光标前有字符）
		var textareaObj = Ext.getCmp('FORMULAWINDOWT');
		var wtValue = textareaObj.getValue();
		var preValue = wtValue.substring(0, cusorPos - 1);
		var sufValue = wtValue.substring(cusorPos);
		textareaObj.setValue(preValue + sufValue);
		go2Pos(cusorPos - 1);
	}
}

// 获取textarea中光标的位置
function getCusorPostion() {
	var txb = document.getElementById('FORMULAWINDOWT');// 根据ID获得对象
	var pos = 0;// 设置初始位置
	txb.focus();// 输入框获得焦点,这句也不能少,不然后面会出错,血的教训啦.
	var s = txb.scrollTop;// 获得滚动条的位置
	var r = document.selection.createRange();// 创建文档选择对象
	var t = txb.createTextRange();// 创建输入框文本对象
	t.collapse(true);// 将光标移到头
	t.select();// 显示光标,这个不能少,不然的话,光标没有移到头.当时我不知道,搞了十几分钟
	var j = document.selection.createRange();// 为新的光标位置创建文档选择对象
	r.setEndPoint("StartToStart", j);// 在以前的文档选择对象和新的对象之间创建对象,妈的,不好解释,我表达能力不算太好.有兴趣自己去看msdn的资料
	var str = r.text;// 获得对象的文本
	var re = new RegExp("[\\n]", "g");// 过滤掉换行符,不然你的文字会有问题,会比你的文字实际长度要长一些.搞死我了.我说我得到的数字怎么总比我的实际长度要长.
	str = str.replace(re, "");// 过滤
	pos = str.length;// 获得长度.也就是光标的位置
	r.collapse(false);
	r.select();// 把光标恢复到以前的位置
	txb.scrollTop = s;// 把滚动条恢复到以前的位置
	return pos;
}

// 将光标定位到textarea的某个位置
function go2Pos(pos) {
	var ta1 = document.getElementById('FORMULAWINDOWT');// 根据ID获得对象
	ta1.focus();
	var o = ta1.createTextRange();
	o.move("character", pos);
	o.select();
}
var FormulaWindow = new Ext.Window({
	plain : true,
	defaults : {
		overflow : 'auto',
		autoScroll : true
	},
	layout : 'fit',
	frame : true,
	resizable : false,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	shadow : true,
	loadMask : true,
	/*maximizable : true,*/
	collapsible : true,
	titleCollapse : true,
	border : false,
	width : 447,
	height : 450,
	buttonAlign : "center",
	title : '公式管理',
	buttons : [{
		text : '确定',
		handler : function() {
			// 校验公式正确性
			if (!checkFormula()) {
				Ext.Msg.alert('提示', '评级公式配置有误，请检查！');
				return false;
			}
			configForm.getForm().findField("gradeFormula").setValue(Ext
					.getCmp('FORMULAWINDOWT').getValue());
			configForm.getForm().findField("gradeFormulaExplain")
					.setValue(Ext.getCmp('FORMULAWINDOWW').getValue());

			FormulaWindow.hide();
		}
	}, '-', {
		text : '返回',
		handler : function() {
			FormulaWindow.hide();
		}
	}]
});

configForm.form.findField('gradeFormula').on("focus", function() {
	if (configForm.form.findField('gradeUseage').getValue() == ''
		|| configForm.form.findField('gradeUseage').getValue() == null) {
		Ext.Msg.alert('提示', '请先选择评级类型！!');
		return false;
	}
	FormulaWindow.removeAll(true);
	FormulaWindow.add({
		height : document.body.clientHeight,
		layout : 'border',
		items : [{
			region : 'north',
			id : 'FORMULAWINDOW',
			height : 75,
			title : '公式表达式',
			items : [{
				name : 'FORMULAWINDOWT',
				id : 'FORMULAWINDOWT',
				width : 447,
				height:50,
				xtype : 'textarea',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%',
				enableKeyEvents : true,
				listeners : {
					'keypress' : function(field, e) {
						maskKeyEvent(e);
					},
					'specialkey' : function(field, e) {
						maskKeyEvent(e);
					},
					'focus' : function() {
						storeCaretWT(this);
					}
				}
			}]
		}, {
			region : 'center',
			id : 'FORMULACONTENTWINDOW',
			title : '中文表达式',
			items : [{
				name : 'FORMULAWINDOWW',
				id : 'FORMULAWINDOWW',
				width : 450,
				xtype : 'textarea',
				labelStyle : 'text-align:right;',
				disabled : true,
				anchor : '90%'
			}]
		}, {
			region : 'south',
			id : 'COUNTWINDOW',
			height : 210,
			title : '计算器',
			layout : 'border',
			items : [{
				region : 'center',
				id : 'FORMULAWINDOW1',
				title : '基本输入',
				layout : 'column',
				items : [{
					width : 51,
					layout : 'form',
					border : false,
					items : [
						new Ext.Button({
							text : '1',
							height : 50,
							width : 50,
							handler : function() {
								insertWT('1');
								transCode2Name();
							}
						}),
						new Ext.Button({
							text : '2',
							height : 50,
							width : 50,
							handler : function() {
								insertWT('2');
								transCode2Name();
							}
						}),
						new Ext.Button({
							text : '3',
							height : 50,
							width : 50,
							handler : function() {
								insertWT('3');
								transCode2Name();
							}
						})
					]
				}, {
					width : 51,
					layout : 'form',
					border : false,
					items : [
						new Ext.Button({
							text : '4',
							height : 50,
							width : 50,
							handler : function() {
								insertWT('4');
								transCode2Name();
							}
						}),
						new Ext.Button({
							text : '5',
							height : 50,
							width : 50,
							handler : function() {
								insertWT('5');
								transCode2Name();
							}
						}),
						new Ext.Button({
							text : '6',
							height : 50,
							width : 50,
							handler : function() {
								insertWT('6');
								transCode2Name();
							}
						})
					]
				}, {
					width : 51,
					layout : 'form',
					border : false,
					items : [
							new Ext.Button({
								text : '7',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('7');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '8',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('8');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '9',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('9');
									transCode2Name();
								}
							})
						]
				}, {
					width : 51,
					layout : 'form',
					border : false,
					items : [
							new Ext.Button({
								text : '0',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('0');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '+',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('+');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '-',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('-');
									transCode2Name();
								}
							})
						]
				}, {
					width : 51,
					layout : 'form',
					border : false,
					items : [
							new Ext.Button({
								text : '*',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('*');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '/',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('/');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '(',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('(');
									transCode2Name();
								}
							})
						]
				}, {
					width : 51,
					layout : 'form',
					border : false,
					items : [
							new Ext.Button({
								text : ')',
								height : 50,
								width : 50,
								handler : function() {
									insertWT(')');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '.',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('.');
									transCode2Name();
								}
							}),
							new Ext.Button({
								text : '%',
								height : 50,
								width : 50,
								handler : function() {
									insertWT('%');
									transCode2Name();
								}
							})
						]
					}]
				}, {
					region : 'east',
					id : 'FORMULACONTENTWINDOW3',
					width : 141,
					title : '指标',
					items : [
						new Ext.Button({
							text : '指标选择',
							height : 76,
							width : 141,
							handler : function() {
								var indexTreeListRecord = Ext.data.Record.create([{
									name : 'indexId',
									mapping : 'INDEX_ID'
								}, {
									name : 'indexCode',
									mapping : 'INDEX_CODE'
								}, {
									name : 'indexUse',
									mapping : 'INDEX_USE'
								}, {
									name : 'indexName',
									mapping : 'INDEX_NAME'
								}, {
									name : "INDEX_USE_ORA"
								}]);
								
								var indexTreeListstorex = new Ext.data.Store({
									restful : true,
									proxy : new Ext.data.HttpProxy({
										url : basepath+ '/IndexbaseQueryAction.json',
										method : 'POST'
									}),
									reader : new Ext.data.JsonReader({
										successProperty : 'success',
										root : 'json.data',
										totalProperty : 'json.count'
									},
									indexTreeListRecord)
								});
								rownum_target = new Ext.grid.RowNumberer({
									header : 'No.',
									width : 28
								});

								var windowIndexGrid = new Ext.grid.GridPanel({
									store : indexTreeListstorex,
									colModel : new Ext.grid.ColumnModel([
										rownum_target, {
											id : 'id',
											header : '指标编码',
											hidden : true,
											dataIndex : 'indexId'
										}, {
											header : '指标编号',
											dataIndex : 'indexCode'
										}, {
											header : '指标名称',
											dataIndex : 'indexName',
											width : 220
										}, {
											header : '指标用途',
											dataIndex : 'INDEX_USE_ORA'
										}
									]),
									id : 'indexTreeList',
									region : 'center'
								});

								windowIndexGrid.on("rowdblclick",function(listPanel, rowIndex,event) {
									var selectRe = listPanel.getSelectionModel().getSelections()[0].data;
									insertWT(selectRe.indexCode);
									indexMap[selectRe.indexCode] = selectRe.indexName;// 将选择的指标编码和指标名称放入JSON对象
									transCode2Name();
									IndexWindow.hide();
								});
								var IndexWindow = new Ext.Window({
									plain : true,
									defaults : {
										overflow : 'auto',
										autoScroll : true
									},
									layout : 'border',
									frame : true,
									resizable : true,
									draggable : true,
									closable : true,
									closeAction : 'hide',
									modal : true, // 模态窗口
									shadow : true,
									loadMask : true,
									maximizable : true,
									collapsible : true,
									titleCollapse : true,
									border : false,
									width : 500,
									height : 400,
									buttonAlign : "center",
									title : '指标选择',
									buttons : [{
										text : '返回',
										handler : function() {
											IndexWindow.hide();
										}
									}],
									items : [
									windowIndexGrid]
								});

								indexTreeListstorex.load({
									params : {
										gradeUseage : configForm.form.findField('gradeUseage').getValue(),
										gradeType : configForm.form.findField('gradeType').getValue()
									}
								});
								IndexWindow.show();

							}
						}), new Ext.Button({
							text : '←',
							height : 75,
							width : 141,
							handler : function() {
								delContent();
								transCode2Name();
							}
						}),
						{
							fieldLabel : 'INDEX_ID',
							name : 'WINDOW_INDEX_ID',
							id : 'WINDOW_INDEX_ID',
							xtype : 'hidden',
							labelStyle : 'text-align:right;',
							anchor : '90%'
						}, {
							fieldLabel : 'INDEX_ID',
							name : 'TEMP',
							id : 'TEMP',
							value : '',
							xtype : 'hidden',
							labelStyle : 'text-align:right;',
							anchor : '90%'
						}, {
							fieldLabel : 'INDEX_ID',
							name : 'TEMPCONTENT',
							id : 'TEMPCONTENT',
							value : '',
							xtype : 'hidden',
							labelStyle : 'text-align:right;',
							anchor : '90%'
						}
					]
				}]
			}]
	});
	Ext.getCmp('FORMULAWINDOWT').setValue(configForm.getForm().findField("gradeFormula").getValue());
	Ext.getCmp('FORMULAWINDOWW').setValue(configForm.getForm().findField("gradeFormulaExplain").getValue());
	getIndexMap();
	FormulaWindow.show();
});
/******************gradeFormula  评级公式处理*******************/