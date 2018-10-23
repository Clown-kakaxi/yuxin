var rowNo1=-1;
// 复选框选择模式
var checkboxSM_result = new Ext.grid.CheckboxSelectionModel({
	checkOnly : false,
	singleSelect : false
});

var typeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=GRADE_LEVEL'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var cellSM_result = new Ext.grid.CellSelectionModel();

var teamstoreRecord = Ext.data.Record.create(
		[
		 {name:'levelId',mapping:'LEVEL_ID'},
		 {name:'schemeId',mapping:'SCHEME_ID'},
		 {name:'levelName',mapping:'LEVEL_NAME'},
		 {name:'levelLower',mapping:'LEVEL_LOWER'},
		 {name:'levelUpper',mapping:'LEVEL_UPPER'}
		 ]
);
var teamstoreReader = new Ext.data.JsonReader(//读取jsonReader
		{
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root:'json.data'
		},teamstoreRecord
);
var teamstore_result = new Ext.data.Store({//产品对照关系store
    restful : true, 
    proxy : new Ext.data.HttpProxy({ 
    	url:basepath+'/ocrmFCiGradeLevelManage.json',
    	method:'get'
    }),
	reader:teamstoreReader
	
});
teamstore_result.on('beforeload', function() {
	this.baseParams = {
			schemeId:addaffiche.getForm().findField("schemeId").getValue()
	};
});

	var taskAdd = function(){
		debugger;
	var u = new teamstore_result.recordType({
		"levelName" :"",
		"levelLower":"",
		"levelUpper" :""
	});
	grid_express_result.stopEditing();
	teamstore_result.insert(0, u);
	grid_express_result.startEditing(0, 0);
	};
	
	//根据Combobox列表中对应的Id的值来渲染 
	function rendererCombobox(value, p, r) { 
	var index = typeStore.find(Ext.getCmp('levelName1').valueField, value); 
	var  record= typeStore.getAt(index); 
	var displayText = ""; 
	if (record == null) { 
	return value; 
	} else { 
	return record.data.value; // 获取record中的数据集中的display字段的值 
	
	} 
	} 


var grid_express_result = new Ext.grid.EditorGridPanel({
	title : "评级结果细项",
	width : 800,
	height : 230,
	frame : true,
	tbar : [
			{
				text : '新增',
				handler : function() {
					if(addaffiche.getForm().findField("schemeId").getValue()==null||addaffiche.getForm().findField("schemeId").getValue()==''){
						Ext.Msg.alert("系统提示", "请先保存方案基本信息，再进行评级结果细项设置！");
					}else
						taskAdd();
				}
			},
			'-',
			{
				text : '删除',
				handler : function() {
					var records = grid_express_result.getSelectionModel()
					.getSelections();
					var recordsLen = records.length;
					if (recordsLen < 1) {
						Ext.Msg.alert("系统提示信息", "请选择记录后进行删除！");
						return;
					} else {
						teamstore_result.remove(records);
					}
				}
			} ],
	store : teamstore_result,
	clicksToEdit: 1,
	columns : [ checkboxSM_result, {
        header : '等级ID',
        width : 200,
        hidden:true,
        align : 'center',
        dataIndex : 'levelId',
        sortable : true
        },{
            header : '评级方案ID',
            width : 200,
            hidden:true,
            align : 'center',
            dataIndex : 'schemeId',
            sortable : true
            },{
		header : "客户等级名称",
		width : 300,
		dataIndex : "levelName",
//		renderer: rendererCombobox, 
		editor :new Ext.form.Field()
//			new Ext.form.ComboBox({
//			id:'levelName1',
//			store : typeStore,
//			 typeAhead : true,
//			 triggerAction : 'all',
//			 lazyRender : true,
//			 listClass : 'x-combo-list-small',
//			 mode : 'local',
//			 valueField : 'key',
//			 displayField : 'value'
//				})
	}, {
		header : "指标总分下限(包含)",
		width : 220,
		dataIndex : "levelLower",
		editor : new Ext.form.Field()
	}, {
		header : "指标总分上限(不包含)",
		width : 220,
		dataIndex : "levelUpper",
		editor : new Ext.form.Field()
	} ],
	stripeRows : true,
	sm : checkboxSM_result,
	buttonAlign:'center',
	buttons:[{
		text:'保存',
		id:'save1',
		handler:function(){
		 var json0 = {'levelName':[]};
		 var json1 = {'levelLower':[]};
		 var json2 = {'levelUpper':[]};
	for(var i=0;i<teamstore_result.getCount();i++){
	    var temp=teamstore_result.getAt(i);
	    if(temp.data.levelName!=''){
//	    	var index = typeStore.find(Ext.getCmp('levelName1').valueField, temp.data.levelName); 
//	    	var  record= typeStore.getAt(index); 
	    	if(temp.data.levelLower!=''&&temp.data.levelUpper!=''){//都不空时，判断是否设置错误
	        	if(Number(temp.data.levelUpper)<=Number(temp.data.levelLower)){
	        		Ext.Msg.alert('系统提示','客户等级'+temp.data.levelName+'指标上限小于下线值，请修改!');
	        		return false;
	        	}
	        }
	    	if(temp.data.levelLower==''&&temp.data.levelUpper==''){//都为空需要补充
	        		Ext.Msg.alert('系统提示','客户等级'+temp.data.levelName+'指标上限、下线值均为空，请修改!');
	        		return false;
	        }
	    	json0.levelName.push(temp.data.levelName);
	        json1.levelLower.push(temp.data.levelLower);
	        json2.levelUpper.push(temp.data.levelUpper);
	    }else{
	    	Ext.Msg.alert('系统提示','请选择客户等级名称!');
			return false;
	    }
	}
    
    Ext.Msg.wait('正在保存，请稍后......','系统提示');
    Ext.Ajax.request({
        url : basepath + '/ocrmFCiGradeLevelManage!saveValue.json',
        method : 'POST',
        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        params:{
            'levelName':Ext.encode(json0),
            'levelLower':Ext.encode(json1),
            'levelUpper':Ext.encode(json2),
            'schemeId':addaffiche.getForm().findField("schemeId").getValue()
        },
        success : function() {
            Ext.Msg.alert('提示', '操作成功');
        },
        failure : function(response) {
            Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
        }
    });
		}},{
		text:'重置',
		id:'reset',
		handler:function(){
			teamstore_result.reload();
		}
		}]
});


grid_express_result.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
	rowNo1=row;	
});