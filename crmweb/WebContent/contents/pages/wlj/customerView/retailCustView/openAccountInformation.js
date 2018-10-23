/**
*@description 360客户视图个金视图  开通账户信息
*@author:xiebz
*@since:2014-09-25
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
		]);

var custId =_custId;
var needCondition = false;
var url = basepath + '/lookup.json?name=LOVE_INVEST_TYPE';
var fields = [{name:'LIKE_INVEST_TYPE',text:'',hidden :true}];
/**
*@description 自定义动态数据checkboxgroup
*/

Ext.ns('UX.form');
UX.form.CheckboxGroup=Ext.extend(Ext.form.CheckboxGroup,{
	//每行显示多少列，默认3列
	columns:1,
	//数据URL
	dataUrl:'',
	//请求数据URL参数
	params:{},
	//boxName
	boxName:'',//具体每个check的name
	//checkbox对应的inputValue值字段
	labelField:'key',
	//checkbox对应的boxLabel值字段
	valueField:'value',
	/**
	 * 
	 * @param {} val 如：  '1,2,3'
	 * @return {}
	 */
	setValue: function(val){
		//注：此处的空字符是保存时产生的，故要先替换掉
		val = val.replace(/ /g,'')
        val = String(val).split(',');
        this.eachItem(function(item){
            if(val.indexOf(item.inputValue)> -1){
                item.setValue(true);
            }else{
            	item.setValue(false);
            }
        });
        return this;
    },
//    setDisabled:function(val){
//    	 item.setDisabled(false);
//    }
	/**
	 * 初始化自定义组件
	 */
	initComponent:function(){
		var _this = this;
		if(_this.items==undefined && _this.item==undefined){
			_this.items=[{boxLabel: '',name: _this.name||'',hidden:true, inputValue: ''}];
			_this.initStore();
		}
		UX.form.CheckboxGroup.superclass.initComponent.call(_this);
	},
	/**
	 * 渲染checkboxgroup对应的checkbox项
	 */
	initStore:function(){
		var _this = this;
		if(_this.dataUrl != ""){
			new Ext.data.Store({
		        restful : true,
		        autoLoad : true,
		        sortInfo : {
		            field:_this.valueField,
		            direction:'ASC'
		        },
		        proxy : new Ext.data.HttpProxy({
		            url : _this.dataUrl
		        }),
		        reader : new Ext.data.JsonReader({
		            root : 'JSON'
		        }, [ _this.labelField, _this.valueField ]),
		        listeners:{
					load:function(store,records){
						var columns =_this.panel.items;
						for(var i=0;i<columns.items.length;i++){
							var column = columns.items[i];
							column.removeAll();
						}
						_this.items.clear();
						for(var i=0,k=0;i<records.length;i++){
							var d = records[i].data;
							var chk = new Ext.form.Checkbox({boxLabel: d[_this.valueField],name: _this.boxName||'',hideMode:'display', inputValue: d[_this.labelField]});
							var column=columns.items[k];
							k++;
							if(k >= columns.items.length) k=0;
							checkbox = column.add(chk);
							_this.items.add(checkbox);
						}
						_this.doLayout();
						_total_ckg = _total_ckg - 1;
					}
				}
		    });
		}
	}
});

//定义总共有多少个checkboxgroup，
var _total_ckg = 0;//默认为0，具体使用时赋值，当使其全部load完毕之后

Ext.onReady(function() {

//注：store在客户视图里，设置autoLoad : true表示比form的store.load慢，导致赋值后还是显示编码，故手工调用load()方法

//定义总共有多少个checkboxgroup，
_total_ckg = 2;

var loveChannelCkg = new UX.form.CheckboxGroup({
	columns:1,
	boxName:'accountContents',
//	fieldLabel: '境内客户',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=OPEN_ACCOUNT_STATE_JN',
	anchor : '95%'
});
var loveBusiTypeCkg = new UX.form.CheckboxGroup({
	columns:1,
	boxName:'accountContents',
//	fieldLabel: '境外客户',
	labelStyle: 'text-align:right;disabled:true',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=OPEN_ACCOUNT_STATE_JW',
	anchor : '95%'
});

var rsRecord = new Ext.data.Record.create([
                                           {name:'custId',mapping:'CUST_ID'},
                                           {name:'accountContents',mapping:'ACCOUNT_CONTENTS',readOnly:true},
                                           {name:'isDomesticCust',mapping:'IS_DOMESTIC_CUST',readOnly:true},
                                           {name:'state',mapping:'STATE'}
                                       ]);
var rsreader = new Ext.data.JsonReader( {
	root : 'json.data',
	totalProperty : 'json.count'
}, rsRecord);

var opForm = new Ext.form.FormPanel({
	id : 'opForm',
	layout : 'form',
	labelAlign : 'center',
	autoScroll : true,
	frame : true,
	buttonAlign : "center",
	items:[{
		xtype:'fieldset',
	   	title:'开通账户信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items:[ 
		        {id:'idjn',xtype:'radio',fieldLabel:'境内客户',name:'jn',labelWidth : 140},
		        loveChannelCkg,        	
				{id:'idjw',xtype:'radio',fieldLabel:'境外客户',name:'jw',labelWidth : 140},
				loveBusiTypeCkg
			   ]
		
	}],
	buttons:[{
		text :'保存',
		hidden:JsContext.checkGrant('openAccountInfo_save'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在保存数据,请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/acrmFCiAccountInfo.json?custId='+custId,
				method : 'POST',
				form : opForm.form.id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Ajax.request({
						url : basepath+'/session-info!getPid.json',
						method : 'GET',
						success : function(a,b,v) {
						    var idStr = Ext.decode(a.responseText).pid;
//						    opForm.getForm().findField('custId').setValue(idStr);
						    Ext.Msg.alert('提示', '操作成功');
							store.reload();
						}
					});
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
	},{
		text :'提交',
		hidden:JsContext.checkGrant('openAccountInfo_commit'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在提交审核的数据,请稍等...','提示');
		    Ext.Ajax.request({
				url : basepath + '/acrmFCiAccountInfo!initFlow.json?custId='+custId,
				method : 'GET',
				form : opForm.form.id,
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
	}]
});

Ext.getCmp('idjn').on('focus',function(){
	opForm.getForm().findField('jw').setValue(false);
	loveBusiTypeCkg.setValue("");
	loveBusiTypeCkg.setDisabled(true);
	loveChannelCkg.setDisabled(false);
});
Ext.getCmp('idjw').on('focus',function(){
	opForm.getForm().findField('jn').setValue(false);
	loveChannelCkg.setValue("");
	loveChannelCkg.setDisabled(true);
	loveBusiTypeCkg.setDisabled(false);
});

var store = new Ext.data.Store({
	restful:true,
    proxy : new Ext.data.HttpProxy({
		url:basepath + '/acrmFCiAccountInfo.json'
	}),
    reader: rsreader
});
//初始化家庭信息方法
store.load({
	params : {
		custId : custId
	},
	method : 'GET',
    callback:function(){
    	window.__setFormValue();
	}
});

/**
 * 为form表单赋值，主要是解决异步请求时的问题
 * 注：把方法定义到window对象上是因为定时器带参数时，会找不到此方法，故加在对象上调用即可
 */
window.__setFormValue = function(){
	if(_total_ckg > 0){
		setTimeout('window.__setFormValue(\''+0+'\');',100);
	}else{
		if(store.getCount() != 0){
			for(var i=0;i<store.getCount();i++){
				var data = store.getAt(i).data;
	    		opForm.getForm().loadRecord(store.getAt(i));
	    		var j = data.isDomesticCust;
	    		if(j=='0'){//境内
	    			opForm.getForm().findField('jn').setValue(true);
	    			loveChannelCkg.setValue(data.accountContents);
	    		}else{//境外
	    			opForm.getForm().findField('jw').setValue(true);
	    			loveBusiTypeCkg.setValue(data.accountContents);
	    		}
	    	}
    	}
	}
};

var tabs = new Ext.Panel( {
	layout : 'form',
	autoScroll : true,
	items : [opForm]
});

//展现页面
var viewport = new Ext.Viewport({
layout : 'fit',
items:[tabs]
});

});
