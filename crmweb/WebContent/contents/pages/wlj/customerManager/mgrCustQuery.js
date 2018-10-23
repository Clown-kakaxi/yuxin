/**
 * 
* @Description: 客户经理视图-管辖客户查询
* @author geyu 
* @date 2014-7-8 
*
 */
imports([
	        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
         ]);

var url=basepath+'/mgrCustQuery.json?mgrId='+_busiId;

var comitUrl=false;
var createView = false;
var editView = false;
var detailView = false;

var lookupTypes=[
                 'XD000080',//客户类别
                 'XD000040', //证件类别
                 'PRE_CUST_LEVEL'//零售客户级别
              ];



var fields=[
     {name:'CUST_ID',text:'客户编号',resutlWidth:60,searchField:true,dataType:'string'},
     {name:'CUST_NAME',text:'客户名称',resutlWidth:120,searchField:true,dataType:'string'},
     {name:'CUST_TYPE',text:'客户类型',resutlWidth:60,translateType:'XD000080',searchField:true},
     {name:'IDENT_TYPE',text:'证件类型',resutlWidth:60,translateType:'XD000040',searchField:true},
     {name:'IDENT_NO',text:'证件号码',resutlWidth:60,searchField:true,dataType:'string'},
     {name:'CUST_LEVEL',text:'客户级别',resutlWidth:60,searchField:false,translateType:'PRE_CUST_LEVEL'},
     {name:'LINKMAN_NAME',text:'联系人姓名',resutlWidth:60,dataType:'string'},
     {name:'LINKMAN_TEL',text:'联系人电话',resutlWidth:60,dataType:'string'}
     
      ];





var tbar=[{text:'客户视图',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		var custId = getSelectedData().data.CUST_ID;
		var custName = getSelectedData().data.CUST_NAME;
		
		parent.parent.Wlj.ViewMgr.openViewWindow(0,custId,custName);
	}},new Com.yucheng.crm.common.NewExpButton({
        formPanel : 'searchCondition',
        url :  basepath+'/mgrCustQuery.json?mgrId='+_busiId
    })
      ];







