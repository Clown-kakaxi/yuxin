var menuData =    [{
	id:'10', 
	text: '客户管理', 
	cls: 'folder',
	parentId:'root'
},{
	id:'11', 
	text: '客户业务', 
	cls: 'folder',
	parentId:'root'
},{
	id:'12', 
	text: '集团客户管理', 
	cls: 'folder',
	parentId:'root'
},{
	id:'13', 
	text: '中小企业管理', 
	cls: 'folder',
	parentId:'root'
},{
	id:'14', 
	text: '客户经理管理', 
	cls: 'folder',
	parentId:'root'
},{
	id:'100',
	text: '客户间关系',
	leaf: true,
	parentId:'10',
	jumUrl: basepath + '/contents/pages/customer/customerManager/customerRelationManagement.jsp',
	imgUrl: basepath +'/contents/img/img/icon-01.png'
},{
	id:'101',
	text: '我关注的客户',
	leaf: true,
	parentId:'10',
	jumUrl: basepath + '/contents/pages/customer/customerManager/attentionCustomer.jsp',
	imgUrl: basepath +'/contents/img/img/icon-02.png'
},{
	id:'102',
	text: '潜在客户管理',
	leaf: true,
	parentId:'10',
	jumUrl: basepath + '/contents/pages/customer/potentialCustomerManager/myPotentialCustomer.jsp',
	imgUrl: basepath +'/contents/img/img/icon-03.png'
},{
	id:'83',
	text: '产品查询',
	leaf: true,
	parentId:'11',
	jumUrl: basepath + '/contents/pages/customer/customerBusinessInfo/customerProd.jsp',
	imgUrl: basepath +'/contents/img/img/icon-04.png'
},{
	id:'82',
	text: '贡献度查询',
	leaf: true,
	parentId:'11',
	jumUrl: basepath + '/contents/pages/customer/customerBusinessInfo/customerContribution.jsp',
	imgUrl: basepath +'/contents/img/img/icon-05.png'
},{
	id:'120',
	text: '集团管理',
	leaf: true,
	parentId:'12',
	jumUrl: basepath + '/contents/pages/customer/groupClientManager/groupClientMaintenance1.jsp',
	imgUrl: basepath +'/contents/img/img/icon-06.png'
},{
	id:'121',
	text: '集团授权管理',
	leaf: true,
	parentId:'12',
	jumUrl: basepath + '/contents/pages/customer/potentialCustomerManager/myPotentialCustomer.jsp',
	imgUrl: basepath +'/contents/img/img/icon-07.png'
},{
	id:'130',
	text: '中小企业客户查询',
	leaf: true,
	parentId:'13',
	jumUrl: basepath + '/contents/pages/smallEnterprise/SMEOCustomerQuery.jsp',
	imgUrl: basepath +'/contents/img/img/icon-08.png'
},{
	id:'131',
	text: '渠道管理',
	leaf: true,
	parentId:'13',
	jumUrl: basepath + '/contents/pages/mktManage/mktTools/channelManager.jsp',
	imgUrl: basepath +'/contents/img/img/icon-09.png'
},{
	id:'140',
	text: '客户经理基本信息',
	leaf: true,
	parentId:'14',
	jumUrl: basepath + '/contents/pages/customer/accountManager/customerManager.jsp',
	imgUrl: basepath +'/contents/img/img/icon-10.png'
},{
	id:'141',
	text: '营销团队管理',
	leaf: true,
	parentId:'14',
	jumUrl: basepath + '/contents/pages/smallEnterprise/salesTeamManager.jsp',
	imgUrl: basepath +'/contents/img/img/icon-11.png'
},{
	id:'142',
	text: '客户经理业绩',
	parentId:'14'
},{
	id:'1420',
	text: '产品信息',
	leaf: true,
	parentId:'142',
	jumUrl: basepath + '/contents/pages/productManage/productInfoList.jsp',
	imgUrl: basepath +'/contents/img/img/icon-12.png'
}];
