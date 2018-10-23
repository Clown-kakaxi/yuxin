<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<%@ page import="com.yuchengtech.bob.core.SysPublicParamManager" language="java" %>
<html>
                                                       
<body>
<input id="custs_id" type="hidden" value=""/>
<input id="json" type="hidden" value=""/>
<div id='north'></div>
<div id='center'></div>
<div id="custNameInputDiv" style="position: absolute; bottom: 1in;right: 1in; top: 1in;width:200;overflow-x:auto;display:none;"></div> 
</body>
<head>
<script type="text/javascript">
	<%
		out.print("var cust_distribute_type_params="+SysPublicParamManager.getInstance().findParamValueByName(SysPublicParamManager.CUST_MANAGER_TYPE)+";");
	%>
</script>
<!-- 树形Grid -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/index.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/css/TreeGrid.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/TreeGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonExtPanel.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/commonjs/scriptLoader.js"></script>
<!-- 电话信息window -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/mktPhone/custPhoneList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.cust.ViewWindow.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/globalVariable.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/assistInput.js"></script>
<!-- 关系网络图 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxclient-ie1.8.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxGrapth-Crm-locale-ext-v1.000.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/crm-mxGraph-api-v1.000.js"></script>
<!-- 公共选择放大镜 -->
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<!-- 人员放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<!-- 指标放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.indexfeild.js"></script>
<!-- 组织机构放大镜 -->
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>

<!-- 加入客户群-------------------------------------------------------------------->

<!-- 客户群放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js"></script>
<!-- 下拉复选框 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/css/LovCombo.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/LovCombo.js"></script>
<!-- 生成营销活动和创建商机 -->
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusOpportManage/myBusOpportAddForm_ProdList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusOpportManage/myBusOpportSalesActivView.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusOpportManage/myBusOpportForm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityProdEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityCustEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityChanelEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityList1.js"></script>
<style type="text/css"><!--
.input_Assist
{
	background-color:#DDDDDD;
}
.ul_background
{
	background-color:#FFFFFF;
	list-style-type:none;
	font-size:12px;
 	border-style: solid;
	border-width:3px;
  	border-color:#DDDDDD;
}
</style>
<script type="text/javascript">
	var resId = "<%=request.getParameter("resId")%>";
	var custId = "<%=request.getParameter("custId")%>" ;
	var custName = "<%=request.getParameter("custName")%>" ;
	var custTyp = "<%=request.getParameter("custTyp")%>" ;
Ext.onReady(function(){
	var viewWindow = new Com.yucheng.crm.cust.ViewWindow({
		id:'viewWindow',
		custId:custId,
		closable  :false,
		custName:custName,
		custTyp:custTyp
	});
	Ext.Ajax.request({
		url : basepath + '/commsearch!isMainType.json',
		mothed : 'GET',
		params : {
		'mgrId' : __userId,
		'custId' : custId
	},
	success : function(response) {
		var anaExeArray = Ext.util.JSON.decode(response.responseText); 
		if(anaExeArray.json != null){
		if(anaExeArray.json.MAIN_TYPE=='1'){
			oCustInfo.omain_type=true;
		}else{
			oCustInfo.omain_type=false;
		}}
		else {
			oCustInfo.omain_type=false;
		}
		oCustInfo.cust_id = custId;
		oCustInfo.cust_name = custName;
		oCustInfo.cust_type = custTyp;
		oCustInfo.view_source = 'viewport_center';
		viewWindow.show();
	
	},
	failure : function(form, action) {}
	});
});

	
</script>

</head>
<body>

</body>
</html>