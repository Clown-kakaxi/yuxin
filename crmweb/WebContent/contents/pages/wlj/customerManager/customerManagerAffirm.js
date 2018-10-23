/**
 * 客户经理认定
 */    
imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js' // 机构放大镜
		        ]);
    
    var url = basepath+'/customerManagerAffirmQuery.json';
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	var lookupTypes=[
	                 
	                 ];
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

    
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'USERID',text:'员工工号',dataType:'string', searchField: true},
				  {name:'USERNAME',text:'员工名称',dataType:'string',searchField: true},
				  {name:'ORG_NAME',text:'归属机构',searchField:true,xtype : 'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',allowBlank:false},
				  {name:'UNITID',text:'归属机构ID',dataType:'string',hidden:true,gridField:false},
				  {name:'DPT_NAME',text:'归属部门',dataType:'string',searchField: false},
				  {name:'DIR_ID',text:'归属部门ID',dataType:'string',hidden:true,gridField:false},
				  {name:'EDUCATION',text:'学历',dataType:'string',searchField: false},
				  {name:'CERTIFICATE',text:'资格证书',dataType:'string',searchField: false},
				  {name:'BIRTHDAY',text:'出生日期',dataType:'date',searchField: false},
				  {name:'ENTRANTS_DATE',text:'入行时间',dataType:'date',searchField: false},
				  {name:'POSITION_TIME',text:'任职时间',dataType:'string',searchField: false},
				  {name:'FINANCIAL_JOB_TIME',text:'金融从业时间',dataType:'string',searchField: false},
				  {name:'POSITION_DEGREE',text:'职级',dataType:'string',searchField: false},
				  {name:'BELONG_BUSI_LINE',text:'归属业务条线',dataType:'string',searchField: false},
				  {name:'BELONG_TEAM_HEAD',text:'归属Team Head',dataType:'string',searchField: false},
				  {name:'SUB_BRANCH_ID',text:'归属支行',searchField: false,allowBlank:false},
				  {name:'BRANCH_ID',text:'归属分行/区域中心主管',searchField: false,allowBlank:false}
	              ];
	
	var tbar = [{
		text : '收起',
		handler : function(){
			collapseSearchPanel();
		}
	},{
		text : '展开',
		handler : function(){
			expandSearchPanel();
		}
	},
	{
		text : '认定',
		hideTitle:JsContext.checkGrant('customerManagerAffirm'),
		handler : function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				Ext.Ajax.request({
					url:basepath + '/customerManagerAffirmQuery!confirm.json',
					method:"POST",
					params : getSelectedData().data,
					success:function(){
						Ext.Msg.alert("提示","操作成功");
						reloadCurrentData();
					},
					failure:function(response){
						var resultArray=Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
								Ext.Msg.alert('提示',
										response.responseText);
							}else {
								Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
							}
					}
				});
			}
		}
	}
	,new Com.yucheng.crm.common.NewExpButton({
        formPanel : 'searchCondition',
        url : basepath+'/customerManagerAffirmQuery.json'
    })];                
	
	
	
	
	
	
	
