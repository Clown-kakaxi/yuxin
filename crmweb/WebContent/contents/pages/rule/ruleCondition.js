/**
 * 规则编辑页面
 */
//执行动作
	var activeWayStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=ACTIVE_WAY'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	activeWayStore.load();
//规则编辑面板
var ruleForm = new Ext.form.FormPanel({
    frame : true,
    title : '规则信息',
    buttonAlign : "center",
    region : 'north',
    autoScroll : true,
    height : 120,
    labelWidth : 140,
    items:[{ 
    	layout : 'column',
        items:[
               {columnWidth : .5,
	            layout : 'form',
	            items :[{
	            	xtype : 'textfield',
					fieldLabel : '规则名称',
					name : 'ruleName',
					anchor : '100%'
	            },{
	            	xtype : 'numberfield',
					fieldLabel : '<span style="color:red">*</span>分值/系数',
					name : 'ruleResult',
					allowBlank:false,
					decimalPrecision : 6 ,
					anchor : '100%'
	            }]
        	
               },{columnWidth : .5,
	            layout : 'form',
	            items :[{
	            	store : activeWayStore,
					xtype : 'combo', 
					resizable : true,
					fieldLabel : '<span style="color:red">*</span>执行动作',
					hiddenName : 'operate',
					name : 'operate',
					valueField : 'key',
					labelStyle : 'text-align:right;',
					displayField : 'value',
					mode : 'local',
					allowBlank:false,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					anchor : '100%'
	            },{
	            	xtype : 'textfield',
					fieldLabel : 'id',
					name : 'id',
					hidden:true,
					anchor : '100%'
	            },{
	            	xtype : 'textfield',
					fieldLabel : 'rsId',
					name : 'rsId',
					hidden:true,
					anchor : '100%'
	            }]
        	
               },{columnWidth : 1,
   	            layout : 'form',
            	 items:[{
            		 xtype : 'textfield',
 					fieldLabel : '<span style="color:red">*</span>表达式',
 					hidden:true,
 					name : 'ruleExpress',
 					readOnly:true,
 					anchor : '100%'
            	 }]  
               }]
    }]
});

var conditionStore = new Ext.data.Store({//数据存储
  	restful:true,
  	proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFSysRuleContition.json'
  	}),
  	  reader: new Ext.data.JsonReader({
	  totalProperty : 'json.count',
	  root:'json.data'
  },  [{name: 'id', mapping: 'ID'}
      ,{name: 'leftBracktes', mapping: 'LEFT_BRACKTES'}
      ,{name: 'indexCode', mapping: 'INDEX_CODE'}
      ,{name: 'indexName', mapping: 'INDEX_NAME'}
      ,{name: 'operate',mapping:'OPERATE'}
      ,{name: 'compareValue', mapping: 'COMPARE_VALUE'}
      ,{name: 'rightBracktes', mapping: 'RIGHT_BRACKTES'}
      ,{name: 'andorFlag', mapping: 'ANDOR_FLAG'}
      ])
	});
		  
conditionStore.on('beforeload', function() {
this.baseParams = {
	'ruleId':ruleForm.form.findField('id').getValue()
  };
});
conditionStore.load();

var tarDictColumns = new Ext.grid.ColumnModel({
    columns : [{
                header : '左括号',
                width : 100,
                align : 'center',
                dataIndex : 'leftBracktes',
                editor : new Ext.form.ComboBox({
				 typeAhead : true,
				 triggerAction : 'all',
				 lazyRender : true,
				 listClass : 'x-combo-list-small',
				 mode : 'local',
				 valueField : 'myId1',
				 displayField : 'displayText1',
				 store : new Ext.data.ArrayStore({
					id : 'tarName',
					fields : ['myId1', 'displayText1'],
					data : [['(', '('],
					       ['((', '(('],
					       ['(((', '(((']
							]})
					}),
                sortable : false
        	}, {
               header : '指标',
               width : 100,
               align : 'center',
               hidden:true,
               dataIndex : 'indexCode',
               sortable : false
               }, {
                   header : '指标',
                   width : 100,
                   align : 'center',
                   dataIndex : 'indexName',
                   sortable : false
                   },
				{
               header : '操作',
               width : 50,
               align : 'center',
               dataIndex : 'operate',
               sortable : false,
				editor : new Ext.form.ComboBox({
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				listClass : 'x-combo-list-small',
				mode : 'local',
				valueField : 'myId1',
				displayField : 'displayText1',
				store : new Ext.data.ArrayStore({
					id : 'tarName',
					fields : ['myId1', 'displayText1'],
					data : [['>', '大于'],['>=', '大于等于'],
					       ['<', '小于'],['<=', '小于等于'],
					       ['=', '等于']
							]})
				})},
			 {
             header : '阀值',
             width : 100,
             align : 'center',
             dataIndex : 'compareValue',
             sortable : false,
             editor : new Ext.form.Field()
        		},{
            header : '右括号',
            width : 100,
            align : 'center',
            dataIndex : 'rightBracktes',
            editor : new Ext.form.ComboBox({
			 typeAhead : true,
			 triggerAction : 'all',
			 lazyRender : true,
			 listClass : 'x-combo-list-small',
			 mode : 'local',
			 valueField : 'myId1',
			 displayField : 'displayText1',
			 store : new Ext.data.ArrayStore({
				id : 'tarName',
				fields : ['myId1', 'displayText1'],
				data : [[')', ')'],
				       ['))', '))'],
				       [')))', ')))']
						]})
			}),
            sortable : false
         },{
            header : '连接符',
            width : 50,
            align : 'center',
            dataIndex : 'andorFlag',
            editor : new Ext.form.ComboBox({
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				listClass : 'x-combo-list-small',
				mode : 'local',
				valueField : 'myId1',
				displayField : 'displayText1',
				store : new Ext.data.ArrayStore({
							id : 'tarName',
							fields : ['myId1', 'displayText1'],
							data : [['AND', '与'],
							       ['OR', '或']
									]})
					}),
            sortable : false
            }]
			});	




var onAdd = function(){
    var u = new conditionStore.recordType({
    	"id" :"",             
		"leftBracktes" :"",
		"indexCode" :ruleSetForm.getForm().findField("indexCode").getValue(),
		"indexName" :ruleSetForm.getForm().findField("indexName").getValue(),
		"operate":"",
		"compareValue" :"",
		"rightBracktes" :"",
		"andorFlag" :""
    });
    conditionGrid.stopEditing();
    conditionStore.insert(0, u);
    conditionGrid.startEditing(0, 0);
    ruleForm.getForm().findField('ruleExpress').setValue('');
};
var onDelete = function(){
	debugger;
	 var index = conditionGrid.getSelectionModel().getSelectedCell();
     if (!index) {
     	alert("请选择一条记录");
         return false;
     }
     var rec = conditionStore.getAt(index[0]);
     conditionStore.remove(rec);
     ruleForm.getForm().findField('ruleExpress').setValue('');
	};
	var ruleExpress = '';
	var checkResult = function() {
		if(conditionStore.getCount()<1){
			Ext.Msg.alert('系统提示','您没有添加任何规则');
			return;
		}else{
			ruleExpress = '';
			for(var i=0;i<conditionStore.getCount();i++){
	                var temp=conditionStore.getAt(i);
	        			if(""!=temp.data.leftBracktes&&null!=temp.data.leftBracktes){
	                    }else{
	                    Ext.Msg.alert('系统提示','左括号不能为空');
	                    return false;
	                    }
	                    if(""!=temp.data.indexCode&&null!=temp.data.indexCode){
	                    }else{
	                    Ext.Msg.alert('系统提示','指标不能为空');
	                    return false;
	                    }if(""!=temp.data.operate&&null!=temp.data.operate){
	                    }else{
	                    Ext.Msg.alert('系统提示','条件不能为空');
	                    return false;
	                    }if(""!=temp.data.compareValue&&null!=temp.data.compareValue){
	                    }else{
	                    Ext.Msg.alert('系统提示','阈值不能为空');
	                    return false;
	                    }if(""!=temp.data.rightBracktes&&null!=temp.data.rightBracktes){
	                    }else{
	                    Ext.Msg.alert('系统提示','右括号不能为空');
	                    return false;
	                    }if(""!=temp.data.andorFlag&&null!=temp.data.andorFlag){
	                    }else{
	                    	if(i!=conditionStore.getCount()-1){//最后一条连接符可以为空
	                    		Ext.Msg.alert('系统提示','连接符不能为空');
	                    		return false;
	                    	}
	                    }
	                    ruleExpress=ruleExpress+ temp.data.leftBracktes +  'A.INDEX_VALUE' +  temp.data.operate + temp.data.compareValue + temp.data.rightBracktes;
						if(i!=conditionStore.getCount()-1){
							ruleExpress=ruleExpress+ temp.data.andorFlag;
						}
	         }
			ruleForm.getForm().findField('ruleExpress').setValue(ruleExpress);
		 	return true;
		}
};

//规则编辑列表
var conditionGrid = new Ext.grid.EditorGridPanel({
	tbar : [{
        text : '新增',
        iconCls:'addIconCss',
        handler:function() {
        onAdd();
    }},{
    text : '删除',
    iconCls:'deleteIconCss',
    handler:function() {
        onDelete();
    },
    scope: this
    }
	],
	height : 300,
	store : conditionStore,
	frame : true,
	cm : tarDictColumns,
	stripeRows : true,
	clicksToEdit : 1
});

//规则编辑panel
var conditionForm = new Ext.form.FormPanel({
	labelWidth : 150,
	height : 200,
	frame : true,
	autoScroll : true,
	region : 'center',
	buttonAlign : "center",
	items : [
	         conditionGrid
				],
				buttons : [
					{
					text : '规则校验',
					handler : function(){
					if(checkResult()){
					 Ext.Msg.alert('系统提示','校验通过');
					};
					}
				},
				{
					text : '重    置',
					handler : function() {
						conditionStore.load();
					}
				}]

});
conditionGrid.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
	rowNo1=row;	
});
	
function savedata(){
	 var ss = checkResult();
	 if (!ruleForm.getForm().isValid()) {
         Ext.MessageBox.alert('提示','输入有误,请检查输入项');
         return false;
     };
	 var json1 = {'leftBracktes':[]};
	 var json2 = {'indexCode':[]};
	 var json3 = {'operate':[]};
	 var json4 = {'compareValue':[]};
	 var json5 = {'rightBracktes':[]};
	 var json6 = {'andorFlag':[]};
	 
	 if(ss){
		 for(var i=0;i<conditionStore.getCount();i++){
             var temp=conditionStore.getAt(i);
             if(temp.data.leftBracktes!=''){
                 json1.leftBracktes.push(temp.data.leftBracktes);
                 json2.indexCode.push(temp.data.indexCode);
                 json3.operate.push(temp.data.operate);
                 json4.compareValue.push(temp.data.compareValue);
                 json5.rightBracktes.push(temp.data.rightBracktes);
					
					if(i!=conditionStore.getCount()-1){
						json6.andorFlag.push(temp.data.andorFlag);
					}else
						json6.andorFlag.push('');
						
             }
         }
		 debugger;
	 Ext.Msg.wait('正在保存，请稍后......','系统提示');
		Ext.Ajax.request({
             url : basepath + '/ocrmFSysRule!saveData.json',
             method : 'POST',
             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
             form : ruleForm.getForm().id,
             params:{
            	 leftBracktes:Ext.encode(json1),
                 indexCode:Ext.encode(json2),
                 operate1:Ext.encode(json3),//用operate1避免与规则表的字段冲突
                 compareValue:Ext.encode(json4),
                 rightBracktes:Ext.encode(json5),
                 andorFlag:Ext.encode(json6)
             },
             success : function() {
                 Ext.Msg.alert('提示', '操作成功');
                 conditionWindow.hide();
             },
             failure : function(response) {
                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
             }
         });
		}else{
		 Ext.Msg.alert('系统提示', '校验未通过，请检查');
		}
	
};
function close(){
	conditionWindow.hide();
}
var conditionPanel = new Ext.Panel({
	buttonAlign : "center",
    layout:'border',
    items : [  ruleForm ,conditionForm],
    buttons : [{ 
	     text : '保存', 
	     handler : savedata
	    }, {
			text : '关闭',
			handler : close
		} ]
});

//规则编辑窗口
var conditionWindow =  new Ext.Window({
	layout : 'fit',
    autoScroll : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true,
    width : 600,
    height : 400,
    loadMask : true,
    border : false,
    items : [ {
        buttonAlign : "center",
        layout : 'fit',
        items : [conditionPanel]
    }]
});