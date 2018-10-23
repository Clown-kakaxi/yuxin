/**
*@description 360客户视图 服务信息
*@author:xiebz
*@since:2014-07-19
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
	columns:3,
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
		val = String(val).split('|')[0];
        this.eachItem(function(item){
            if(val.indexOf(item.inputValue)> -1){
                item.setValue(true);
            }
        });
        return this;
    },
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

var collateral = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'collateral',
	fieldLabel: '担保品',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=COLLATERAL_TYPE',
	anchor : '95%'
});

var product = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'productType',
	fieldLabel: '产品类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=PRODUCT_WILL_TYPE',
	anchor : '95%'
});

var rsRecord = new Ext.data.Record.create([
                                           {name:'custId',mapping:'CUST_ID'},
                                           {name:'financialProducts',mapping:'FINANCIAL_PRODUCTS'},//理财产品
                                           {name:'loanType',mapping:'LOAN_TYPE'},//贷款类型
                                           {name:'collateral',mapping:'COLLATERAL'},//担保品
                                           {name:'productType',mapping:'PRODUCT_TYPE'},//产品类型
                                           {name:'test',mapping:''}
                                       ]);
var rsreader = new Ext.data.JsonReader( {
	root : 'json.data',
	totalProperty : 'json.count'
}, rsRecord);
                                   	
var opForm = new Ext.form.FormPanel({
	id : 'opForm',
	layout : 'form',
	labelAlign : 'right',
	autoScroll : true,
	frame : true,
	buttonAlign : "center",
	items:[{
		xtype:'fieldset',
	   	title:'客户产品意愿',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .35,
			labelWidth : 140,
			items : [
			         	{xtype:'checkbox',fieldLabel:'理财产品',name:'financialProducts',labelWidth : 140,inputValue:1},
			         	{id:'id3',xtype:'radiogroup',fieldLabel:'贷款',name:'loanType', width: 200,
			         		items:[{boxLabel:'按揭贷款',name:'loanType',inputValue:1},
			         		       {boxLabel:'抵押(质)贷款',name:'loanType',inputValue:2}
			         		       ],
		         		    listeners: {  
		         		    	 "change": function() {  
			         		    	 if (Ext.getCmp("id3").getValue().inputValue == "1") {  
			         		    		collateral.setDisabled(true); 
			         		    		product.setDisabled(true); 
			         		         } else {  
			         		        	collateral.setDisabled(false); 
			         		    		product.setDisabled(false); 
			         		    	 }  
		         		    	  }  
		         		     } 
			         	},
			         	collateral,
			         	product
			]
		}]
	}],
	buttons:[{
		id : '_saveHobby',
		text :'保存',
		hidden:JsContext.checkGrant('productWillInfo_save'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在保存数据,请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/acrmFCiProductWill.json?custId='+custId,
				method : 'POST',
				form : opForm.form.id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Ajax.request({
						url : basepath+'/session-info!getPid.json',
						method : 'GET',
						success : function(a,b,v) {
						    var idStr = Ext.decode(a.responseText).pid;
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
		hidden:JsContext.checkGrant('productWillInfo_commit'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在提交审核的数据,请稍等...','提示');
		    Ext.Ajax.request({
				url : basepath + '/acrmFCiProductWill!initFlow.json?custId='+custId,
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

var store = new Ext.data.Store({
	restful:true,
    proxy : new Ext.data.HttpProxy({
		url:basepath + '/acrmFCiProductWill.json'
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
	    		collateral.setValue(data.collateral);
	    		product.setValue(data.productType);
	    		opForm.getForm().findField('financialProducts').setValue(data.financialProducts);
	    		opForm.getForm().findField('loanType').setValue(data.loanType);
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
