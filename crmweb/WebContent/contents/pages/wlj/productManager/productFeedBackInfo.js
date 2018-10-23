/**
 *@description 产品反馈信息查询页面
 *@author: luyy
 *@since: 2014-06-27
 */	

	imports([
			        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
			        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
			        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
			        ]);
	var createView = false;
	var editView = false;
	var detailView = false;
	
	var url = basepath+'/productfeedback.json';

	var fields = [{name:'FEEDBACK_ID',hidden:true},
		          {name: 'FEEDBACK_USER', text : '反馈人',hidden:true},
		          {name: 'FEEDBACK_USER_NAME',hiddenName:'FEEDBACK_USER' ,text : '反馈人',dataType:'userchoose',searchField : true,singleSelect:true},
		          {name: 'PRODUCT_ID',dataType:'string',text:"关联产品",hidden:true},
		          {name: 'PROD_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,
		        	  text:"关联产品",searchField : true,prodState:(''),riskLevel:('')},
		          {name: 'FEEDBACK_DATE', text : '反馈日期',dataType:'date',searchField : true},
		          {name: 'FEEDBACK_CONT',text:'反馈内容',dataType: 'string',resutlWidth:300,searchField : true}
		];
	
	
	
