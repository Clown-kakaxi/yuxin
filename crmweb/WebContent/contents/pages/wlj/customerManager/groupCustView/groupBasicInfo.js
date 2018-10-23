/*
 * 
 * @description 集团基本信息
 * @author wangmk1
 * @since 2014-07-20
 */
 
Ext.QuickTips.init();
var lookupTypes=[
                 'XD000106'
         ];
var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动
//_busiId集团客户编号
var id=_busiId;
//使用到的字段
var fields = [
	 {name:'ID',text:'主键ID',resutlWidth:80,hidden :true},
     {name:'GROUP_NO',text:'集团编号',resutlWidth:80,disabled:true,cls:'x-readOnly'},
	 {name:'ADDR_REGIST',text:'注册地址',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'GROUP_TYPE',text:'集团类型',translateType:'XD000106',readOnly:true,disabled:true,resutlWidth:80,cls:'x-readOnly'},
     {name:'GROUP_NAME',text:'集团名称',resutlWidth:100,disabled:true,cls:'x-readOnly'},
     {name:'EMPLOYEE_NUM',text:'集团成员总数',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'GURANT_CUS_NUM',text:'对外担保客户数',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'GROUP_MEMO',text:'集团描述',xtype:'textarea',resutlWidth:80,disabled:true,cls:'x-readOnly'},
     {name:'EMPLOYEE_NUM_FORMAL',text:'正式成员数',resutlWidth:80,disabled:true,cls:'x-readOnly'}
];
//集团基本信息面板
var customerView =[{
	/**
	 * 自定义面板
	 */
	title:'集团基本信息',
	hideTitle:true,
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : ['GROUP_NO','ADDR_REGIST','GROUP_TYPE','GROUP_NAME','EMPLOYEE_NUM','GURANT_CUS_NUM','GROUP_MEMO','EMPLOYEE_NUM_FORMAL'
			
		],
		/**
		 *面板字段初始化处理
		 */
		fn : function(GROUP_NO,ADDR_REGIST,GROUP_TYPE,GROUP_NAME,EMPLOYEE_NUM,GURANT_CUS_NUM,GROUP_MEMO,EMPLOYEE_NUM_FORMAL){
			return [GROUP_NO,ADDR_REGIST,GROUP_TYPE,GROUP_NAME,EMPLOYEE_NUM,GURANT_CUS_NUM,GROUP_MEMO,EMPLOYEE_NUM_FORMAL];
		}
	}]
}];

var record = Ext.data.Record.create(
  [{name: 'ID', mapping: 'ID'}
  ,{name: 'GROUP_NO', mapping: 'GROUP_NO'}
  ,{name: 'ADDR_REGIST', mapping: 'ADDR_REGIST'}
  ,{name: 'GROUP_TYPE',mapping:'GROUP_TYPE'}
  ,{name: 'GROUP_NAME', mapping: 'GROUP_NAME'}
  ,{name: 'EMPLOYEE_NUM', mapping: 'EMPLOYEE_NUM'} 	
  ,{name: 'GURANT_CUS_NUM', mapping: 'GURANT_CUS_NUM'}
  ,{name: 'EMPLOYEE_NUM_FORMAL', mapping: 'EMPLOYEE_NUM_FORMAL'}
  ,{name: 'GROUP_MEMO', mapping: 'GROUP_MEMO'}
   ]);

//用store请求后台数据的方法
//var store = new Ext.data.Store({
//    restful:true,
//    proxy: new Ext.data.HttpProxy({
//        url: basepath + '/groupCustomer!searchBasicInfo.json'
//    }),
//    reader: new Ext.data.JsonReader({
// //  root对应于需要后台传输的json字符串的根。
//        root:'json.data',
//        totalProperty:'json.count'
//    },record)
//});
//
//var beforeviewshow = function(theview){
//	store.load({
//		params : {
//			id:id
//	    },
//	    callback:function(){
//	    	if(store.getCount()!=0){
//	    		theview.contentPanel.getForm().loadRecord(store.getAt(0));
//	    	}
//		}
//    });
//};

//用form.load()请求后台数据的方法
var beforeviewshow = function(theview){
	theview.contentPanel.getForm().reader=new Ext.data.JsonReader({
        root:'json.data'
    },record);
	theview.contentPanel.getForm().load({
		url: basepath + '/groupCustomer!searchBasicInfo.json',
		method:'get',
		params : {
			'id':id
	    },
	    success:function(form,action){
	    	form.setValues(action.result);
	    },
	    failure:function(){
	    	alert("请求失败");
	    }	
	});
}