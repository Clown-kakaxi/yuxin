/**
*@description 客户经理团队 业绩参数设置
*/ 
imports( [
'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
'/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js' // 机构放大镜
]);
// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};
var treeLoaders = [ {
	key : 'DATASETMANAGERLOADER',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
} ];

/**
 * 左侧机构树配置
 */
var treeCfgs = [ {
	key : 'DATASETMANAGERTREE1',
	loaderKey : 'DATASETMANAGERLOADER',
	autoScroll : true,
	rootCfg : {
		expanded:true,
		id:JsContext._orgId,
		text:JsContext._unitname,
		autoScroll:true,
		children:[],
		UNITID:JsContext._orgId,
		UNITNAME:JsContext._unitname
	}
} ];
var createView=true;//新增
var editView=true;//修改
var tbar =true;

var url=basepath+'/custMgParameterNew.json';
var comitUrl=basepath + '/custMgParameterNew.json?a=1';
var fields=[
            {name:'ORG_ID',text:'机构号',searchField:true,resutlWidth:80,allowBlank: false,hidden:true}, 
            {name:'ORG_NAME',text:'机构名称',searchField:true,resutlWidth:120,xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',allowBlank: false},
            {name:'USER_ID',text:'客户经理编号',xtype:'textfield',searchField:false,hidden:true},
            {name:'USER_NAME',text:'客户经理名称',searchField:true,hiddenName:'USER_ID',dataType:'userchoose',allowBlank: false},
            {name:'TIME',text:'时间',searchField:true,resutlFloat:'right',dataType:'date',xtype:'datefield',format:'Y-m'},
            {name:'TOTAL_CONTRI_MONTH',text:'总贡献度本月目标总量'},
            {name:'TOTAL_CONTRI_SEASON',text:'总贡献度本季目标总量'},
            {name:'TOTAL_CONTRI_YEAR',text:'总贡献度本年目标总量',searchField:false,resutlFloat:'right'},
            {name:'SAVE_INCRE_MONTH',text:'存款时点增量本月目标总量',searchField:false,xtype:'textfield'},
            {name:'SAVE_INCRE_SEASON',text:'存款时点增量本季目标总量',xtype:'textfield',searchField:false},
            {name:'SAVE_INCRE_YEAR',text:'存款时点增量本年目标总量',xtype:'textfield',resutlFloat:'right',searchField:false},
            {name:'LOAN_MONTH',text:'贷款动拨金额本月目标总量',xtype:'textfield'},
            {name:'LAON_SEASON',text:'贷款动拨金额本季目标总量',xtype:'textfield'},
            {name:'LOAN_YEAR',text:'贷款动拨金额本年目标总量',xtype:'textfield'},
            {name:'INSURANCE_SALE_MONTH',text:'保险销售额本月目标总量',xtype:'textfield'},
            {name:'INSURANCE_SALE_SEASON',text:'保险销售额本季目标总量'},
            {name:'INSURANCE_SALE_YEAR',text:'保险销售额本年目标总量'}
            ];
/***
 * 新增
 */
var createFormViewer =[{
	fields : ['ORG_ID','ORG_NAME','USER_NAME','USER_ID','TIME','TOTAL_CONTRI_MONTH','TOTAL_CONTRI_SEASON','TOTAL_CONTRI_YEAR','SAVE_INCRE_MONTH','SAVE_INCRE_SEASON','SAVE_INCRE_YEAR',
	          'LOAN_MONTH','LAON_SEASON','LOAN_YEAR','INSURANCE_SALE_MONTH','INSURANCE_SALE_SEASON','INSURANCE_SALE_YEAR'],
	fn : function(ORG_ID,ORG_NAME,USER_NAME,USER_ID,TIME,TOTAL_CONTRI_MONTH,TOTAL_CONTRI_SEASON,TOTAL_CONTRI_YEAR,SAVE_INCRE_MONTH,SAVE_INCRE_SEASON,SAVE_INCRE_YEAR,
			LOAN_MONTH,LAON_SEASON,LOAN_YEAR,INSURANCE_SALE_MONTH,INSURANCE_SALE_SEASON,INSURANCE_SALE_YEAR){
		USER_ID.hidden = true;
		USER_NAME.singleSelect = false;
		return [ORG_ID,ORG_NAME,USER_NAME,USER_ID,TIME,TOTAL_CONTRI_MONTH,TOTAL_CONTRI_SEASON,TOTAL_CONTRI_YEAR,SAVE_INCRE_MONTH,SAVE_INCRE_SEASON,SAVE_INCRE_YEAR,
		        LOAN_MONTH,LAON_SEASON,LOAN_YEAR,INSURANCE_SALE_MONTH,INSURANCE_SALE_SEASON,INSURANCE_SALE_YEAR];
	}
}];
/***
 * 修改
 */
var editFormViewer =[{
	fields : ['ORG_ID','ORG_NAME','USER_NAME','USER_ID','TIME','TOTAL_CONTRI_MONTH','TOTAL_CONTRI_SEASON','TOTAL_CONTRI_YEAR','SAVE_INCRE_MONTH','SAVE_INCRE_SEASON','SAVE_INCRE_YEAR',
	          'LOAN_MONTH','LAON_SEASON','LOAN_YEAR','INSURANCE_SALE_MONTH','INSURANCE_SALE_SEASON','INSURANCE_SALE_YEAR'],
	fn : function(ORG_ID,ORG_NAME,USER_NAME,USER_ID,TIME,TOTAL_CONTRI_MONTH,TOTAL_CONTRI_SEASON,TOTAL_CONTRI_YEAR,SAVE_INCRE_MONTH,SAVE_INCRE_SEASON,SAVE_INCRE_YEAR,
			LOAN_MONTH,LAON_SEASON,LOAN_YEAR,INSURANCE_SALE_MONTH,INSURANCE_SALE_SEASON,INSURANCE_SALE_YEAR){
		USER_ID.hidden = true;
		USER_NAME.singleSelect=true;
		return [ORG_ID,ORG_NAME,USER_NAME,USER_ID,TIME,TOTAL_CONTRI_MONTH,TOTAL_CONTRI_SEASON,TOTAL_CONTRI_YEAR,SAVE_INCRE_MONTH,SAVE_INCRE_SEASON,SAVE_INCRE_YEAR,
		        LOAN_MONTH,LAON_SEASON,LOAN_YEAR,INSURANCE_SALE_MONTH,INSURANCE_SALE_SEASON,INSURANCE_SALE_YEAR];
	}
}];
/**
 * 自定义工具条上按钮
 * 注：批量选择未实现,目前只支持单条删除、启用、停用
 * 删除客户经理贡献度业绩参数设置数据
 */
var tbar = [{
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请至少选择一条数据！');
			return false;
		}else{
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				}
				var selectRecords = getAllSelects();
				var custmId = '';
				for(var i=0;i<selectRecords.length;i++){
					if(i == 0){
						custmId = selectRecords[i].data.USER_ID;
					}else{
						custmId +=","+ selectRecords[i].data.USER_ID;
					}
				}
				Ext.Ajax.request({
//					url: basepath + '/custMgParameterNew!batchDestroy.json?idStr='+id,
					url: basepath + '/custMgParameterNew!batchDestroy.json',
					params : {
							custmId : custmId
					},
					success : function() {
                        Ext.Msg.alert('提示', '删除数据成功' );
						reloadCurrentData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除数据失败' );
						reloadCurrentData();
					}
				});
			});
		}
	}
}];

var beforeviewshow = function(view){
	if(view == getEditView()){
		if(getSelectedData()==false || getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据!');
			return false;
		}
	}
};
