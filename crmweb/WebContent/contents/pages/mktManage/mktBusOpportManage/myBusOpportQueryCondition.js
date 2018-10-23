/**
 * 营销管理->商机管理->我的商机
 * 查询条件Form对象定义
 * wzy，2013-02-17
 */

/*****************************定义查询条件中的下拉框对象***********开始*****************/
//"商机类型"下拉框定义
var combo_oppor_type = new Ext.data.Store({                 
    autoLoad:true,
    sortInfo:{
        field:'key',
        direction:'ASC'
    },
    proxy:new Ext.data.HttpProxy({
        url:basepath+'/lookup.json?name=OPPOR_TYPE',
        method:'GET'
    }),
    reader:new Ext.data.JsonReader({
        root:'JSON'
    },['key','value']),
    fields: [
        'key',
        'value'
    ]
});
//"商机状态"下拉框定义
var combo_busi_chance_status = new Ext.data.Store({                
    autoLoad:true,
    sortInfo:{
        field:'key',
        direction:'ASC'
    },
    proxy:new Ext.data.HttpProxy({
        url:basepath+'/lookup.json?name=BUSI_CHANCE_STATUS',
        method:'GET'
    }),
    reader:new Ext.data.JsonReader({
        root:'JSON'
    },['key','value']),
    fields: [
        'key',
        'value'
    ]
});
//"商机阶段"下拉框定义
var combo_busi_chance_stage = new Ext.data.Store({                
    autoLoad:true,
    sortInfo:{
        field:'key',
        direction:'ASC'
    },
    proxy:new Ext.data.HttpProxy({
        url:basepath+'/lookup.json?name=BUSI_CHANCE_STAGE',
        method:'GET'
    }),
    reader:new Ext.data.JsonReader({
        root:'JSON'
    },['key','value']),
    fields: [
        'key',
        'value'
    ]
});
//"商机来源"下拉框定义
var combo_busi_chance_source = new Ext.data.Store({                
    autoLoad:true,
    sortInfo:{
        field:'key',
        direction:'ASC'
    },
    proxy:new Ext.data.HttpProxy({
        url:basepath+'/lookup.json?name=BUSI_CHANCE_SOURCE',
        method:'GET'
    }),
    reader:new Ext.data.JsonReader({
        root:'JSON'
    },['key','value']),
    fields: [
        'key',
        'value'
    ]
});
//"达成概率"下拉框定义
var combo_reach_prob = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=REACH_PROB'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
/*****************************定义查询条件中的下拉框对象***********结束*****************/

/*****************************定义查询条件Form表单中的按钮*********开始*****************/
var queryButton = [
	{
		text : '查询',
		handler : function() {
			//判断查询条件是否合法
		    if(!qForm.getForm().isValid()){ 
		        Ext.Msg.alert('查询条件输入有误！');
		        return false;
		    }
		    //判断“商机开始日期”和“商机完成日期”的先后顺序
//		    var remainDay=qForm.getForm().findField('MSG_LAST').value;
//		    if(remainDay!=''&&remainDay!=undefined){
//		        var remindDay=new Date(year,mon-1,day+parseInt(remainDay)).format('Y-m-d');
//		        Ext.getCmp('msgLastDate').setValue(remindDay);
//		    }
		    var conditionStr =  qForm.getForm().getFieldValues();
	        store.on('beforeload', function() {
	            this.baseParams = {
	                    "condition":Ext.encode(conditionStr)
	            };
	        });
			store.reload({
	            params : {
	                start : 0,
	                limit : bbar.pageSize
	            }
	        });
		}
	}, {
		text : '重置',
		handler : function() {
			qForm.getForm().reset();
		}
	}
];
/*****************************定义查询条件Form表单中的按钮*********结束*****************/

/*****************************查询条件Form表单定义*****************开始*****************/
var qForm = new Ext.form.FormPanel({
    id:'qform',
	title : '我的商机查询',
	border : true,
	region : 'north',
    autoScroll : true,
	frame : true, //是否渲染表单面板背景色
	labelAlign : 'middle', // 标签对齐方式
	//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
	buttonAlign : 'center',
	height : 150,
	//width:document.body.scrollWidth-10,
	items : [{
		layout : 'column',
		border : false,
		items : [{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
           	 	id:'oppor_type',
                xtype:'combo',
                name:'OPPOR_TYPE',
                hiddenName:'OPPOR_TYPE',
                fieldLabel:'商机类型',
                labelStyle: 'text-align:right;',
                anchor:'95%',
                mode:'local',
                triggerAction:'all',
                resizable:true,
                store:combo_oppor_type,
    			emptyText:'请选择 ',
                valueField:'key',
                displayField:'value'
            }]
		}, {
            columnWidth : .25,
            layout : 'form',
            labelWidth : 100, // 标签宽度
            defaultType : 'textfield',
            border : false,
            items : [{
                xtype:'textfield',
                fieldLabel:'商机名称',
                labelStyle: 'text-align:right;',
                name:'OPPOR_NAME',
                allowDecimal:false,
                anchor:'95%'
            }]
        }, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
           	 	id:'oppor_stat',
                xtype:'combo',
                name:'OPPOR_STAT',
                hiddenName:'OPPOR_STAT',
                fieldLabel:'商机状态',
                labelStyle: 'text-align:right;',
                anchor:'95%',
                mode:'local',
                triggerAction:'all',
                resizable:true,
                store: combo_busi_chance_status,
    			emptyText:'请选择 ',
                valueField:'key',
                displayField:'value'
            }]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
           	 	id:'oppor_stage',
                xtype:'combo',
                name:'OPPOR_STAGE',
                hiddenName:'OPPOR_STAGE',
                fieldLabel:'商机阶段',
                labelStyle: 'text-align:right;',
                anchor:'95%',
                mode:'local',
                triggerAction:'all',
                resizable:true,
                store: combo_busi_chance_stage,
    			emptyText:'请选择 ',
                valueField:'key',
                displayField:'value'
            }]
		}]
	},{
		layout : 'column',
		border : false,
		items : [{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
           	 	id:'oppor_source',
                xtype:'combo',
                name:'OPPOR_SOURCE',
                hiddenName:'OPPOR_SOURCE',
                fieldLabel:'商机来源',
                labelStyle: 'text-align:right;',
                anchor:'95%',
                mode:'local',
                triggerAction:'all',
                resizable:true,
                store:combo_busi_chance_source,
    			emptyText:'请选择 ',
                valueField:'key',
                displayField:'value'
            }]
		},{
			columnWidth : .25,
			layout : 'form',
			border : false,
			items:[
		       new Com.yucheng.crm.common.ProductManage({
		    	   xtype:'productChoose',
		    	   fieldLabel : '产品选择', 
		    	   id:'productSelect',
		    	   labelStyle: 'text-align:right;',
		    	   name : 'PROD_NAME',
		    	   hiddenName:'PROD_ID',
		    	   singleSelect:false,
		    	   anchor : '95%'
		       })
		    ]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
           	 	id:'reach_prob',
                xtype:'combo',
                name:'REACH_PROB',
                hiddenName:'REACH_PROB',
                fieldLabel:'达成概率',
                labelStyle: 'text-align:right;',
                anchor:'95%',
                mode:'local',
                triggerAction:'all',
                resizable:true,
                store:combo_reach_prob,
    			emptyText:'请选择 ',
                valueField:'key',
                displayField:'value'
            }]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
				xtype:'datefield',
                fieldLabel:'商机有效期',
                labelStyle: 'text-align:right;',
                name:'OPPOR_DUE_DATE',
                format:'Y-m-d',
                allowDecimal:false,
                anchor:'95%',
                editable:false
	        }]
		}]
	},{
		layout : 'column',
		border : false,
		items : [{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
				xtype:'datefield',
                fieldLabel:'商机开始日期',
                labelStyle: 'text-align:right;',
                name:'OPPOR_START_DATE',
                format:'Y-m-d',
                allowDecimal:false,
                anchor:'95%',
                editable:false
	        }]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 100, // 标签宽度
			defaultType : 'textfield',
			border : false,
			items : [{
				xtype:'datefield',
                fieldLabel:'商机完成日期',
                labelStyle: 'text-align:right;',
                name:'OPPOR_END_DATE',
                format:'Y-m-d',
                allowDecimal:false,
                anchor:'95%',
                editable:false
	        }]
		}]
	}],
	buttons : queryButton
});
/*****************************查询条件Form表单定义*****************结束*****************/