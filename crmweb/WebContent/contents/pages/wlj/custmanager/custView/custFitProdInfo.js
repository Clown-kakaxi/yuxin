/**
 * 客户视图-客户适合的产品
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTarget.js',	//指标选择放大镜,
		        '/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',//营销活动放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',//客户放大镜
		        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',//用户放大镜
		        //'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',//组织机构放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js', // 机构放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTask.js'	,
		        '/contents/pages/common/Com.yucheng.bcrm.common.MktTaskTarget.js'//营销任务指标明细放大镜
		        ,'/contents/pages/common/Com.yucheng.bcrm.common.CreateMktOppor.js'
		        ,'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'  //客户群放大镜
		        ]);
    var _custId;
    var url = basepath+'/custFitProdQuery.json?custId='+_custId;
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'PROD_ID',text:'产品编号',searchField: true},
				  {name:'PROD_NAME',text:'产品名称',dataType:'productChoose',hiddenName:'PROD_ID',searchField: true},
				  {name:'CUST_NAME',text:'客户名称',searchField: false,hidden:true},
				  {name:'CUST_TYPE',text:'客户类型',searchField: false,hidden:true},
				  {name:'CUST_STAT',text:'客户状态',searchField: false,hidden:true},
				  {name:'LINKMAN_NAME',text:'客户联系人',searchField: false,hidden:true},
				  {name:'MGR_ID',text:'商机执行人ID',searchField: false,hidden:true},
				  {name:'MGR_NAME',text:'商机执行人名称',searchField: false,hidden:true},
				  {name:'ORG_ID',text:'商机执行机构Id',searchField: false,hidden:true},
				  {name:'ORG_NAME',text:'商机执行机构名称',searchField: false,hidden:true}
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
	},new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('custFitProdInfo_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custFitProdQuery.json?custId='+_custId
    }),{
		text:'生成营销活动',
		hidden:JsContext.checkGrant('custFitProdInfo_createActivity'),
		handler:function(){
            if(getSelectedData() == false){
                Ext.Msg.alert('提示','请选择数据!');
                return false;
            }
            else{
            	var prodIdStrs = getSelectedData().data.PROD_ID;
            	Ext.ScriptLoader.loadScript({
        			scripts: [
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',
        				basepath+'/contents/pages/wlj/mktManage/mktActivityManager/mktAddFunction.js',
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
        				basepath+'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
        			],
        		    finalCallback: function() {
        		    	getActiveAddWindowShow(_custId,prodIdStrs,'',false,false,false,false,false,false);
        		    }
        		});
            	
            } 
		}
	},{
		text:'生成商机',
		hidden:JsContext.checkGrant('custFitProdInfo_createOppor'),
		handler:function(){
			if(getSelectedData() == false){
                Ext.Msg.alert('提示','请选择数据!');
                return false;
            }else{  
            	    var PROD_ID=getSelectedData().data.PROD_ID;
            	    var PROD_NAME=getSelectedData().data.PROD_NAME;
            	    var CUST_NAME=getSelectedData().data.CUST_NAME;
            	    var CUST_TYPE=getSelectedData().data.CUST_TYPE;
            	    var CUST_STAT=getSelectedData().data.CUST_STAT;
            	    var LINKMAN_NAME=getSelectedData().data.LINKMAN_NAME;
            	    var MGR_ID=getSelectedData().data.MGR_ID;
            	    var MGR_NAME=getSelectedData().data.MGR_NAME;
            	    var ORG_ID=getSelectedData().data.ORG_ID;
            	    var ORG_NAME=getSelectedData().data.ORG_NAME;
            		var createMktOppor = new Com.yucheng.bcrm.common.CreateMktOppor ({iscust:true,
					custId:_custId,isgroup:false,prodId:PROD_ID,prodName:PROD_NAME,custName:CUST_NAME,custType:CUST_TYPE,custStat:CUST_STAT,linkMan:LINKMAN_NAME,
					mgrId:MGR_ID,mgrName:MGR_NAME,orgId:ORG_ID,orgName:ORG_NAME
			})
			createMktOppor.show();
            }
		}
	}];
	
	
	
