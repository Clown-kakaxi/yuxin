/**
 * @description  大额客户流失预警页面
 * @author 
 * @since 2014-07-19
 */
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
		]);

var url = basepath +'/custLost.json';
var comitUrl = basepath +'/custLost!save.json';

var lookupTypes = ['ACC1300012']; 

//var createView=true;
var createView=!JsContext.checkGrant('xz');//新增按钮（权限控制）

var fields = [{name:'ID',text:'id',gridField:false},
              {name:'CUST_ID',text:'客户编号',dataType:'string',allowBlank: false},
              {name:'CUST_NAME',text:'客户名称',dataType:'string',allowBlank: false},
              {name:'DECLARE_CUR_TYPE',text:'申报币种',translateType:'ACC1300012'},
              {name:'DECLARE_AMT',text:'申报金额',dataType:'number'},
              {name:'TRAD_AMT',text:'交易金额',dataType:'number',allowBlank: false},
//              {name:'TRAD_AMT_S',text:'交易金额(从)',dataType:'number',searchField:false},
//              {name:'TRAD_AMT_E',text:'交易金额(到)',dataType:'number',searchField:false},
              {name:'TRAD_DATE',text:'交易日期',dataType:'date',searchField:true},
              {name:'AO_NAME',text:'客户经理姓名',dataType:'string'},
              {name:'BRANCH_NO',text:'分支行id',gridField:false,allowBlank: false},
              {name:'BRANCH_NAME',text:'分支行',searchField:true,resutlWidth:120,xtype:'orgchoose',
            	  hiddenName:'BRANCH_NO',searchType:'SUBTREE',allowBlank: false},
              {name:'CURR_STATUS',text:'当前状态',dataType:'string',searchField:true},//暂时不知道用什么字典
              {name:'AREA_CENTER',text:'所在区域中心/异地分行id',dataType:'string',allowBlank: false},
              {name:'BUSI_LINE',text:'所在业务条线',dataType:'string'},
              {name:'BUSI_GROUP',text:'所在事业群',dataType:'string'}];


var createFormViewer = [{
	fields:['CUST_ID','CUST_NAME','DECLARE_CUR_TYPE','DECLARE_AMT','TRAD_AMT','AO_NAME','BRANCH_NO','CURR_STATUS','AREA_CENTER',
	        'AREA_CENTER','BUSI_LINE','BUSI_GROUP'],
	fn: function(CUST_ID,CUST_NAME,DECLARE_CUR_TYPE,DECLARE_AMT,TRAD_AMT,AO_NAME,BRANCH_NO,CURR_STATUS,
	        AREA_CENTER,BUSI_LINE,BUSI_GROUP){
		return [CUST_ID,CUST_NAME,DECLARE_CUR_TYPE,DECLARE_AMT,TRAD_AMT,AO_NAME,BRANCH_NO,CURR_STATUS,
		        AREA_CENTER,BUSI_LINE,BUSI_GROUP];
	}
}];

var customerView = [{
	title:'批示信息',
	type:'grid',
	isCsm:false,//多选框
//	isRn:false, 行号
	url : basepath + '/custLostF.json',
	pageable : true,
	frame : true,
	fields : {
		fields:[
			{name:'F_USER_NAME',text:'批示人'},
	        {name:'F_REMARK_DATE',text:'批示时间',format:'Y-m-d'},
	        {name:'F_USER_REMARK',text:'批示内容',width:400}
	    ],
		fn : function(F_USER_NAME,F_REMARK_DATE,F_USER_REMARK){
			return [F_USER_NAME,F_REMARK_DATE,F_USER_REMARK]
		}
	}
},{
	title:'批示',
	hideTitle:JsContext.checkGrant('ps'),
	type:'form',
	groups : [{
		columnCount : 1 ,
		fields : [{
			name : 'F_USER_REMARK',
			xtype : 'textarea',
			text : '批示内容'
		}],
		fn : function(F_USER_REMARK){
			return [F_USER_REMARK];
		}
	}],
	formButtons : [{
		text : '确定',
		fn : function(formPanel,basicForm){
			var F_USER_REMARK = this.contentPanel.getForm().getValues().F_USER_REMARK;
			Ext.Ajax.request({
				url : basepath + '/custLostF!save.json',
				method : 'POST',
				params : {
					'fUserRemark' :F_USER_REMARK,
					yjId:getSelectedData().data.ID
				},
				success : function() {
					 Ext.Msg.alert('提示','操作成功！');
					 reloadCurrentData();
				}
			});
		}
	}]
}];

var beforeviewshow = function(view){
	if(view._defaultTitle != '新增')
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(view._defaultTitle == '批示信息')
			view.setParameters({
				yjId:getSelectedData().data.ID
			});
};


