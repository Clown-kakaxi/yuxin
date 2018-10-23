/**
*@description 家庭信息 .360客户视图
*@author: fan zheming
*@since: 2014-08-13
*/
imports([
         	'/contents/pages/common/Com.yucheng.crm.common.ImpExp.js'
]);
//初始化提示框
Ext.QuickTips.init();
var _custId;

//var url = basepath + '/acrmFCiPerFamily.json?custId='+_custId;
//var comitUrl = basepath + '/acrmFCiPerFamily!saveData.json?custId='+_custId;

var lookupTypes = ['FAMILY_RELA','XD000040'
                   ];
                   
/**
 * 数据字典
 * @type Boolean
 */
//住宅情况
var houseTStore = new Ext.data.Store({
	restful:true,
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=HOUSE_STATE_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//家庭成员关系
var famliyStore = new Ext.data.Store({
	restful:true,
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=FAMILY_RELA'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//家庭成员证件类型
var identStore = new Ext.data.Store({
	restful:true,
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//有无私家车
var carTStore = new Ext.data.Store({
	restful:true,
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=CARHOLD_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//是否授信
var creditTStore = new Ext.data.Store({
	restful:true,
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=CREDIT_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//家庭和睦
var harmonyTStore = new Ext.data.Store({
	restful:true,
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=HARMONY_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});


var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

var fields = [//形式FIELDS
	{name : 'TEST', text : 'TEST', resutlWidth: 100}
];

var record = Ext.data.Record.create([
    {name:'ID'},
    {name:'CUST_ID'},
    {name:'HOUSE_HOLDER_NAME'},
    {name:'POPULATION'},
    {name:'FAMILY_ADDR'},
    {name:'LABOR_POP_NUM'},
    {name:'BUSI_AND_SCALE'},
    {name:'HOME_TEL'},
    {name:'HOUSE_STAT'},
    {name:'CREDIT_INFO'},
    {name:'FAMILY_ADVERSE_RECORDS'},
    {name:'DEBT_STATE'},
    {name:'IS_HARMONY'},
    {name:'HAS_HOME_CAR'},
    {name:'FMY_JITSURYOKU'},
    {name:'IS_CREDIT_FAMILY'},
    {name:'CREDIT_AMOUNT'},
    {name:'REMARK'}
]);

var yoreader = new Ext.data.JsonReader( {
	successProperty : 'success',
	idProperty : 'custId',
	messageProperty : 'message',
	root : 'json.data'
}, record);

var custFamilyInfoPanel=new Ext.FormPanel({
	title : '客户家庭信息',
	reader : yoreader,
	collapsible : true,
	autoHeight:true,
	autoWidth:true,
//	labelWidth:200,//label的宽度
//	layout: 'form',
	labelAlign:'right',
	frame:false,//疑问 ..
	autoScroll : true,
	buttonAlign:'center',
	anchor:'95%',
	items : [{
    	xtype : 'fieldset',//疑问
		title : '基本信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight:true,
		layout: 'column',
    	items:[{
    		columnWidth: 0.25,
	        layout: 'form',
	        items: [
	        	{name: 'ID', xtype: 'textfield',fieldLabel: '主键编号',hidden:true},
	        	{name: 'CUST_ID', xtype: 'textfield',fieldLabel: '客户编号',hidden:true},
	        	{name: 'HOUSE_HOLDER_NAME', xtype: 'textfield',fieldLabel: '户主姓名',anchor: '95%'},
	        	{name: 'TELH_NATION',xtype: 'textfield',fieldLabel: '国家地区区号',anchor: '95%',
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								var result = custFamilyInfoPanel.getForm().findField('TELH_NATION').getValue();
								if(!reg.test(result)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									custFamilyInfoPanel.getForm().findField('TELH_NATION').setValue("")
									return false;
								}
								var syuuseiA = result+"/"+custFamilyInfoPanel.getForm().findField('HOMEX_TEL').getValue();
								custFamilyInfoPanel.getForm().findField('HOME_TEL').setValue(syuuseiA);
							}
					}
	        	},
	        	{name: 'HOMEX_TEL',xtype: 'textfield',fieldLabel: '固定电话',anchor: '95%' ,vtype:'telephone',
	        		listeners:{
							blur : function(){
								var result = custFamilyInfoPanel.getForm().findField('TELH_NATION').getValue();
								var syuuseiB = result+"/"+custFamilyInfoPanel.getForm().findField('HOMEX_TEL').getValue();
								custFamilyInfoPanel.getForm().findField('HOME_TEL').setValue(syuuseiB);
							}
					}
	        	},
	        	{name: 'HOME_TEL',xtype: 'textfield',fieldLabel: '存储固定电话',anchor: '95%',hidden:true}
       		]
    	},{
    		columnWidth: 0.25,
	        layout: 'form',
	        items: [
	        	{name: 'POPULATION',xtype: 'numberfield',fieldLabel: '家庭人数',anchor: '95%'},
        		{name: 'LABOR_POP_NUM',xtype: 'numberfield',fieldLabel: '其中:劳动力人数', anchor: '95%'}
       		]
    	},{
    		columnWidth: 0.5,
	        layout: 'form',
	        items: [
	        	{name: 'FAMILY_ADDR',xtype: 'textfield',fieldLabel: '家庭地址', anchor: '95%'},
	        	{name: 'BUSI_AND_SCALE',xtype: 'textfield',fieldLabel: '经营项目及规模', anchor: '95%'}
	        ]
    	}]
    },{
    	xtype : 'fieldset',//疑问
		title : '更多信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight:true,
		layout: 'form',
    	items : [{
    		layout: 'column',
    		items : [{
    			columnWidth: 0.33,
		        layout: 'form',
		        items: [
		        	{name:'HAS_HOME_CAR',xtype:'combo',anchor:'85%',fieldLabel:'有无私家车',
		              store:carTStore, valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
		        	{name: 'FMY_JITSURYOKU',xtype: 'textfield',fieldLabel: '家庭经济实力',anchor: '85%'},
		        	{name:'HOUSE_STAT',xtype:'combo',anchor:'85%',fieldLabel:'住宅情况',
		              store:houseTStore, valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true}
	       		]
    		},{
    			columnWidth: 0.33,
		        layout: 'form',
		        items: [
		        	{name: 'DEBT_STATE', xtype: 'textfield',fieldLabel: '负债情况',anchor: '85%'},
		        	{name: 'FAMILY_ADVERSE_RECORDS', xtype: 'textfield',fieldLabel: '不良记录',anchor: '85%'},
		        	{name:'IS_HARMONY',xtype:'combo',anchor:'85%',fieldLabel:'家庭和睦',
		              store:harmonyTStore, valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true}
	       		]
    		},{
    			columnWidth: 0.33,
		        layout: 'form',
		        items: [
		        	{name:'IS_CREDIT_FAMILY',xtype:'combo',anchor:'85%',fieldLabel:'是否授信',
		              store:creditTStore, valueField : 'key',displayField : 'value',mode : 'local',
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',
		              labelStyle:'text-align:right;',selectOnFocus : true},
		        	{name: 'CREDIT_AMOUNT',fieldLabel: '授信金额',anchor: '85%',viewFn: money('0,000.00'),xtype: 'numberfield'},
		        	{name: 'CREDIT_INFO', xtype: 'textfield',fieldLabel: '信用情况',anchor: '85%'}
	       		]
    		}]
    	},{
    		layout: 'column',
    		items : [{
	    		columnWidth: 1,
		        layout: 'form',
		        items: [
		        	{name: 'REMARK',xtype: 'textarea',fieldLabel: '其他',anchor: '95%'}
	       		]
    	}]
    }]
	}],
	buttons:[{
		text:'确认保存',
		hidden:JsContext.checkGrant('privateFmyInfo_modify'),
		id:'confirm_theMSG',
		handler:function(){
			if (!custFamilyInfoPanel.getForm().isValid()) {
                Ext.MessageBox.alert('确认保存', '请正确输入各项必要信息！');
                return false;
            }
            Ext.MessageBox.confirm("客户家庭信息", "客户是否已经确认？",function(a) {
                if (a == 'no') {
                	return ;
                }   
                custFamilyInfoPanel.getForm().findField('CUST_ID').setValue(_custId);
                var commintData = translateDataKey(custFamilyInfoPanel.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
               	Ext.Ajax.request({
	                url: basepath + '/acrmFCiPerFamily.json',
	                method: 'POST',
	                params : commintData,
	                success: function(response){
	                   	 		Ext.Msg.alert('提示', '操作成功！');
	                     			custFamilyInfoPanel.getForm().load({
										url: basepath + '/acrmFCiPerFamily.json',
										method:'get',
										params : {
											'custId':_custId
									    }
									});
	                     		showCustomerViewByTitle('客户家庭信息维护');
	                },
	                failure: function(){
	                	Ext.Msg.alert('提示', '操作失败！');
	                }
            	});
            });
		}
	}]
	
});

/**
 * 家庭成员信息·列表
 */
var fmyrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var fmysm = new Ext.grid.CheckboxSelectionModel();
var fmycm = new Ext.grid.ColumnModel( [
    fmyrownum,fmysm,
    {header:'成员编号', dataIndex:'MXTID', align:'left', width : 180, hidden:true},
    {header:'客户编号', dataIndex:'CUST_ID', sortable:true, align:'left', width : 180,hidden:true},
    {header:'成员名称', dataIndex:'MEMBERNAME', sortable:true, align:'left', width : 180},
    {header:'家庭成员关系', dataIndex:'FAMILYRELA', sortable:true, align:'left', width : 180,renderer:function(value){
			var val = translateLookupByKey("FAMILY_RELA",value);
			return val?val:"";
			}},
    {header:'成员证件类型', dataIndex:'MEMBERCRET_TYP', sortable:true, align:'left', width : 180,renderer:function(value){
		var val = translateLookupByKey("XD000040",value);
		return val?val:"";
		}},
    {header:'成员证件号码', dataIndex:'MEMBERCRET_NO', sortable:true, align:'left', width : 180},
    {header:'电话', dataIndex:'TEL', sortable:true, align:'left', width : 180},
    {header:'手机号码', dataIndex:'MOBILE', id:'mobileA',sortable:true, align:'left', width : 180},
    {header:'邮箱', dataIndex:'EMAIL', sortable:true, align:'left', width : 180},
    {header:'生日', dataIndex:'BIRTHDAY', sortable:true, align:'left', width : 180},
    {header:'成员所在企业名称', dataIndex:'COMPANY', sortable:true, align:'left', width : 180},
    {header:'成员ID', dataIndex:'MEMBER_ID', sortable:true, align:'left', width : 180},
    {header:'关系ID', dataIndex:'MANAGER_ID', sortable:true, align:'left', width : 180},
    {header:'备注', dataIndex:'REMARK', sortable:true, align:'left', width : 180},
    {header:'最后更新系统', dataIndex:'LAST_UPDATE_SYS', sortable:true, align:'left', width : 180},
    {header:'最后更新人', dataIndex:'LAST_UPDATE_USER', sortable:true, align:'left', width : 180},
    {header:'最后更新时间', dataIndex:'LAST_UPDATE_TM', sortable:true, align:'left', width : 180},
    {header:'交易流水号', dataIndex:'TX_SEQ_NO', sortable:true, align:'left', width : 180}
]);

var fmyStore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
//		url : basepath + '/acrmFCiPerFamily!findMemberInfo.json?custId='+_custId,
		url : basepath + '/acrmFCiPerFamilies.json?custId='+_custId,
		method: 'POST',
		failure : function(response) {//状态码解码.--报错码
			var resultArray = Ext.util.JSON
					.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		root : 'json.data'
	}, [ 
	    {name : 'MXTID'},
	    {name : 'CUST_ID'},
		{name : 'MEMBERNAME'},
		{name : 'FAMILYRELA'},
		{name : 'MEMBERCRET_TYP'},
		{name : 'MEMBERCRET_NO'},
		{name : 'TEL'},
		{name : 'MOBILE'},
		{name : 'EMAIL'},
		{name : 'BIRTHDAY'},
		{name : 'COMPANY'},
		{name : 'MEMBER_ID'},
		{name : 'MANAGER_ID'},
		{name : 'REMARK'},
		{name : 'LAST_UPDATE_SYS'},
		{name : 'LAST_UPDATE_USER'},
		{name : 'LAST_UPDATE_TM'},
		{name : 'TX_SEQ_NO'}
	])
});

fmyStore.load(
//{
//	callback:function(a){
//		for(var i=0;i<a.length;i++){
//			var tel = a[i].data.TEL;
//			if(tel.indexOf("/",0)>0){
//				var tArr = tel.split("/");
//				var tNew = tArr[0]+"-"+tArr[1];
//				a[i].data.TEL = tNew;//回复格式
//			}
//			var mob = a[i].data.MOBILE;
//			if(mob.indexOf("/",0)>0){
//				var mArr = mob.split("/");
//				var mNew = mArr[0]+"-"+mArr[1];
//				a[i].data.MOBILE = mNew;//回复格式
//			}
//		}
//	}
//}
);
var fmyGrid = new Ext.grid.GridPanel( {
	title : '客户家庭成员信息',
	collapsible : true,
	frame : true,
	store : fmyStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : fmycm, // 列模型
	sm : fmysm,
	height : 200,
	tbar: [{
    	id : 'addTargetForm',
        text: '新增',
        hidden:JsContext.checkGrant('privateFmyMemberInfo_create'),
        handler: function() {//新增时, 自动生成mxtid(ID)不需要获取不需要考虑生成!
            add_form.form.reset();
            add_form.getForm().findField('CUST_ID').setValue(_custId);
            showCustomerViewByTitle('新增成员');
        }
    },{
    	id : 'modTargetForm',
        text: '修改',
        hidden:JsContext.checkGrant('privateFmyMemInfo_modify'),
        handler: function() {
           if(fmyGrid.getSelectionModel().getSelected() == null){
        		Ext.Msg.alert('提示','请选择一条数据!');
				return false;
        	}
            mod_form.form.reset();
            var record = fmyGrid.getSelectionModel().getSelected();
            mod_form.getForm().findField('MXTID').setValue(record.data.MXTID);
            mod_form.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
            mod_form.getForm().findField('MEMBERNAME').setValue(record.data.MEMBERNAME);
            mod_form.getForm().findField('FAMILYRELA').setValue(record.data.FAMILYRELA);
            mod_form.getForm().findField('MEMBERCRET_TYP').setValue(record.data.MEMBERCRET_TYP);
            mod_form.getForm().findField('MEMBERCRET_NO').setValue(record.data.MEMBERCRET_NO);
            mod_form.getForm().findField('TEL').setValue(record.data.TEL);
            mod_form.getForm().findField('MOBILE').setValue(record.data.MOBILE);
            if(record.data.TEL.indexOf('-')>0){
            	mod_form.getForm().findField('TEL_NATION').setValue(record.data.TEL.split('-')[0]);
            	mod_form.getForm().findField('TELX').setValue(record.data.TEL.split('-')[1]);
            }
            if(record.data.MOBILE.indexOf('-')>0){
            	mod_form.getForm().findField('MOBILE_NATION').setValue(record.data.MOBILE.split('-')[0]);
            	mod_form.getForm().findField('MOBILEX').setValue(record.data.MOBILE.split('-')[1]);
            }
            mod_form.getForm().findField('EMAIL').setValue(record.data.EMAIL);
            mod_form.getForm().findField('BIRTHDAY').setValue(record.data.BIRTHDAY);
            mod_form.getForm().findField('COMPANY').setValue(record.data.COMPANY);
            mod_form.getForm().findField('MEMBER_ID').setValue(record.data.MEMBER_ID);
            mod_form.getForm().findField('MANAGER_ID').setValue(record.data.MANAGER_ID);
            mod_form.getForm().findField('LAST_UPDATE_SYS').setValue(record.data.LAST_UPDATE_SYS);
            mod_form.getForm().findField('LAST_UPDATE_USER').setValue(record.data.LAST_UPDATE_USER);
            mod_form.getForm().findField('LAST_UPDATE_TM').setValue(record.data.LAST_UPDATE_TM);
            mod_form.getForm().findField('REMARK').setValue(record.data.REMARK);
            
            showCustomerViewByTitle('修改成员');
        }
    },{
    	id : 'delTargetForm',
        text: '删除',
        hidden:JsContext.checkGrant('privateFmyMemInfo_delete'),
        handler: function() {
        	if(fmyGrid.getSelectionModel().getSelected() == null){
        		Ext.Msg.alert('提示','请选择一条数据!');
				return false;
        	}
			Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}
				var record = fmyGrid.getSelectionModel().getSelected();
				var id = record.data.MXTID;
				Ext.Ajax.request({
			    	url : basepath + '/acrmFCiPerFamilies!batchDestroy.json',  
			    	params : {
						id : id
					},
                    success : function(){
                        fmyStore.load({
		                    params:{
		                    	custId: _custId
		                    }
	                    });
                        Ext.Msg.alert('提示', '删除成功');
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                    }
                });
			})
        }
    },new Com.yucheng.bob.ExpButton({
		hidden:JsContext.checkGrant('privateFmyMemInfo_excel'),
//      formPanel : 'searchCondition',
      url :  basepath + '/acrmFCiPerFamilies.json?custId='+_custId
  })
    ],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var add_form = new Ext.form.FormPanel({
    labelWidth: 100,	// 标签宽度
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'middle',	// 标签对齐方式
    buttonAlign: 'center',
    items: [{
    	layout:'column',
    	items:[{
    		columnWidth: 0.33,
	        layout: 'form',
	        items: [
	        	{xtype:'textfield', fieldLabel:'客户编号', name:'CUST_ID', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, readOnly:true, cls:'x-readOnly'},
			    {xtype:'textfield', fieldLabel:'成员名称', name:'MEMBERNAME', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype : 'combo', fieldLabel:'家庭成员关系', name:'FAMILYRELA',anchor: '85%',
			    	store:famliyStore,triggerAction : 'all',displayField:'value',valueField:'key'},
			    {xtype:'combo', fieldLabel:'成员证件类型', name:'MEMBERCRET_TYP', labelStyle: 'text-align:right;',anchor: '85%',editable:false,
			    	store:identStore,triggerAction : 'all',displayField:'value',valueField:'key'},
			    {xtype:'textfield', fieldLabel:'成员证件号码', name:'MEMBERCRET_NO', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'textfield', fieldLabel:'成员ID', name:'MEMBER_ID', labelStyle: 'text-align:right;',anchor: '85%'}
			    
       		]
    	},{
    		columnWidth: 0.33,
	        layout: 'form',
	        items: [
	        	{xtype:'textfield', fieldLabel:'电话国家地区区号', name:'TEL_NATION', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								var result = add_form.getForm().findField('TEL_NATION').getValue();
								if(!reg.test(result)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									add_form.getForm().findField('TEL_NATION').setValue("")
									return false;
								}
								var syuuseiA = result+"/"+add_form.getForm().findField('TELX').getValue();
								add_form.getForm().findField('TEL').setValue(syuuseiA);
							}
					}
	        	
	        	},
	        	{xtype:'textfield', fieldLabel:'手机国家地区区号', name:'MOBILE_NATION', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								var result = add_form.getForm().findField('MOBILE_NATION').getValue();
								if(!reg.test(result)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									add_form.getForm().findField('MOBILE_NATION').setValue("")
									return false;
								}
								var syuuseiA = result+"/"+add_form.getForm().findField('MOBILEX').getValue();
								add_form.getForm().findField('MOBILE').setValue(syuuseiA);
							}
					}
	        	},
			    {xtype:'textfield', fieldLabel:'邮箱', name:'EMAIL',vtype:'email', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'datefield', fieldLabel:'生日', name:'BIRTHDAY', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'textfield', fieldLabel:'成员所在企业名称', name:'COMPANY', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'textfield', fieldLabel:'关系ID', name:'MANAGER_ID', labelStyle: 'text-align:right;',anchor: '85%'}
       		]
    	},{
    		columnWidth: 0.33,
	        layout: 'form',
	        items: [
	        	{xtype:'textfield', fieldLabel:'电话', name:'TELX', labelStyle: 'text-align:right;',vtype:'telephone',anchor: '85%',
	        		listeners:{
							blur : function(){
								var result = add_form.getForm().findField('TEL_NATION').getValue();
								var syuuseiB = result+"/"+add_form.getForm().findField('TELX').getValue();
								add_form.getForm().findField('TEL').setValue(syuuseiB);
							}
					}
	        	},
	        	{xtype:'textfield', fieldLabel:'手机号码', name:'MOBILEX',vtype:'telephone', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var result = add_form.getForm().findField('MOBILE_NATION').getValue();
								var syuuseiB = result+"/"+add_form.getForm().findField('MOBILEX').getValue();
								add_form.getForm().findField('MOBILE').setValue(syuuseiB);
							}
					}
	        	},
	        	{xtype:'textfield', fieldLabel:'储存电话', name:'TEL', labelStyle: 'text-align:right;',anchor: '85%',hidden:true},
	        	{xtype:'textfield', fieldLabel:'储存手机号码', name:'MOBILE', labelStyle: 'text-align:right;',anchor: '85%',hidden:true},
	        	{xtype:'textfield', fieldLabel:'最后更新系统', name:'LAST_UPDATE_SYS', labelStyle: 'text-align:right;',anchor: '85%',hidden:true},
			    {xtype:'textfield', fieldLabel:'最后更新人', name:'LAST_UPDATE_USER', labelStyle: 'text-align:right;',anchor: '85%',hidden:true},
			    {xtype:'datefield', fieldLabel:'最后更新时间', name:'LAST_UPDATE_TM', labelStyle: 'text-align:right;',anchor: '85%',hidden:true}
       		]
    	}]
    	},{
    	layout:'column',
    	items:[{
    		columnWidth: 1,
	        layout: 'form',
	        items: [
	        	{xtype:'textarea', fieldLabel:'备注', name:'REMARK', labelStyle: 'text-align:right;',anchor: '94%'}
       		]
    	}]
    	}
    ],
    buttons: [{
        text: '保存',
        handler: function() {
        	if (!add_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(add_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
            Ext.Ajax.request({
                url: basepath + '/acrmFCiPerFamilies.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                   	 Ext.Msg.alert('提示', '操作成功！');
                     add_form.form.reset();
                     fmyStore.load({
                         params: {
                            custId: commintData.custId
                         }
                     });
                     showCustomerViewByTitle('客户家庭信息维护');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
        }
    },{
        text: '返回',
        handler: function() {
            showCustomerViewByTitle('客户家庭信息维护');
        }
    }]
});

//以下是更改
var mod_form = new Ext.form.FormPanel({
    labelWidth: 100,	// 标签宽度
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'middle',	// 标签对齐方式
    buttonAlign: 'center',
    height: 200,
    items: [{
    	layout:'column',
    	items:[{
    		columnWidth: 0.33,
	        layout: 'form',
	        items: [
	        	{xtype:'textfield', fieldLabel:'主键编号', name:'MXTID', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, hidden:true},
	        	{xtype:'textfield', fieldLabel:'客户编号', name:'CUST_ID', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, readOnly:true, cls:'x-readOnly'},
			    {xtype:'textfield', fieldLabel:'成员名称', name:'MEMBERNAME', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'combo', fieldLabel:'家庭成员关系', name:'FAMILYRELA', labelStyle: 'text-align:right;',anchor: '85%',
			    	store:famliyStore,triggerAction : 'all',displayField:'value',valueField:'key'},
			    {xtype:'combo', fieldLabel:'成员证件类型', name:'MEMBERCRET_TYP', labelStyle: 'text-align:right;',anchor: '85%',editable:false,
			    	store:identStore,triggerAction : 'all',displayField:'value',valueField:'key'},
			    {xtype:'textfield', fieldLabel:'成员证件号码', name:'MEMBERCRET_NO', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'textfield', fieldLabel:'成员ID', name:'MEMBER_ID', labelStyle: 'text-align:right;',anchor: '85%'}
			    
       		]
    	},{
    		columnWidth: 0.33,
	        layout: 'form',
	        items: [
	        	{xtype:'textfield', fieldLabel:'电话国家地区区号', name:'TEL_NATION', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								var result = mod_form.getForm().findField('TEL_NATION').getValue();
								if(!reg.test(result)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									mod_form.getForm().findField('TEL_NATION').setValue("")
									return false;
								}
								var syuuseiA = result+"/"+mod_form.getForm().findField('TELX').getValue();
								mod_form.getForm().findField('TEL').setValue(syuuseiA);
							}
					}
	        	
	        	},
	        	{xtype:'textfield', fieldLabel:'手机国家地区区号', name:'MOBILE_NATION', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var reg = /^[0\+]\d{2,3}$/;
								var result = mod_form.getForm().findField('MOBILE_NATION').getValue();
								if(!reg.test(result)){
									Ext.Msg.alert('提示','请输入正确格式的区号！');
									mod_form.getForm().findField('MOBILE_NATION').setValue("")
									return false;
								}
								var syuuseiA = result+"/"+mod_form.getForm().findField('MOBILEX').getValue();
								mod_form.getForm().findField('MOBILE').setValue(syuuseiA);
							}
					}
	        	},
			    {xtype:'textfield', fieldLabel:'邮箱', name:'EMAIL', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'datefield', fieldLabel:'生日', name:'BIRTHDAY', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'textfield', fieldLabel:'成员所在企业名称', name:'COMPANY', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'textfield', fieldLabel:'关系ID', name:'MANAGER_ID', labelStyle: 'text-align:right;',anchor: '85%'}
       		]
    	},{
    		columnWidth: 0.33,
	        layout: 'form',
	        items: [
	        	{xtype:'textfield', fieldLabel:'电话', name:'TELX', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var result = mod_form.getForm().findField('TEL_NATION').getValue();
								var syuuseiB = result+"/"+mod_form.getForm().findField('TELX').getValue();
								mod_form.getForm().findField('TEL').setValue(syuuseiB);
							}
					}
	        	},
	        	{xtype:'textfield', fieldLabel:'手机号码', name:'MOBILEX', labelStyle: 'text-align:right;',anchor: '85%',
	        		listeners:{
							blur : function(){
								var result = mod_form.getForm().findField('MOBILE_NATION').getValue();
								var syuuseiB = result+"/"+mod_form.getForm().findField('MOBILEX').getValue();
								mod_form.getForm().findField('MOBILE').setValue(syuuseiB);
							}
					}
	        	},
	        	{xtype:'textfield', fieldLabel:'储存电话', name:'TEL', labelStyle: 'text-align:right;',anchor: '85%',hidden:true},
	        	{xtype:'textfield', fieldLabel:'储存手机号码', name:'MOBILE', labelStyle: 'text-align:right;',anchor: '85%',hidden:true},
	        	{xtype:'textfield', fieldLabel:'最后更新系统', name:'LAST_UPDATE_SYS', labelStyle: 'text-align:right;',anchor: '85%', readOnly:true, cls:'x-readOnly'},
			    {xtype:'textfield', fieldLabel:'最后更新人', name:'LAST_UPDATE_USER', labelStyle: 'text-align:right;',anchor: '85%', readOnly:true, cls:'x-readOnly'},
			    {xtype:'textfield', fieldLabel:'最后更新时间', name:'LAST_UPDATE_TM', labelStyle: 'text-align:right;',anchor: '85%', readOnly:true, cls:'x-readOnly'}
       		]
    	}]
    	},{
    	layout:'column',
    	items:[{
    		columnWidth: 1,
	        layout: 'form',
	        items: [
	        	{xtype:'textarea', fieldLabel:'备注', name:'REMARK', labelStyle: 'text-align:right;',anchor: '94%'}
       		]
    	}]
    	}
    ],
    buttons: [{
        text: '保存',
        handler: function() {
        	if (!mod_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(mod_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
            Ext.Ajax.request({
                url: basepath + '/acrmFCiPerFamilies.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                   	 Ext.Msg.alert('提示', '操作成功！');
                     add_form.form.reset();
                     fmyStore.load({
                         params: {
                            custId: commintData.custId
                         }
                     });
                     showCustomerViewByTitle('客户家庭信息维护');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
        }
    },{
        text: '返回',
        handler: function() {
            showCustomerViewByTitle('客户家庭信息维护');
        }
    }]
});

/**
 * 自定义面板
 * @type 
 */
var customerView =[{
	title:'客户家庭信息维护',
	hideTitle:true,//隐藏抬头
	type: 'form',
	items:[custFamilyInfoPanel,fmyGrid]
},{
  	title:'新增成员',
	hideTitle:true,//隐藏抬头
	type: 'form',
	items:[add_form]
},{
  	title:'修改成员',
	hideTitle:true,//隐藏抬头
	type: 'form',
	items:[mod_form]
}];

//customerBaseInfoPanel加载数据，load!
custFamilyInfoPanel.getForm().load({
	url: basepath + '/acrmFCiPerFamily.json',
	method:'get',
	params : {
		'custId':_custId
    },
    success:function(form,action){
    	//固定电话号码与国家区号分发
    	var telHoto = form.findField('HOME_TEL').getValue();
    	if(telHoto.indexOf("/",0)>0){
    		var telHArr = telHoto.split("/");
	    	form.findField('HOMEX_TEL').setValue(telHArr[1]);
	    	form.findField('TELH_NATION').setValue(telHArr[0]);
    	}else{
    		form.findField('HOMEX_TEL').setValue(telHoto);
    	}
    }
});