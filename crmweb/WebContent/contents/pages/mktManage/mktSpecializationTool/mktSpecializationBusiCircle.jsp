<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<%@ page import="com.yuchengtech.bob.core.CustBelongParamManager" language="java" %>
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
		out.print("var cust_distribute_type_params="+CustBelongParamManager.getInstance().findParamValueByName(CustBelongParamManager.CUST_MANAGER_TYPE)+";");
	%>
</script>
<!-- 树形Grid -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/index.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/css/TreeGrid.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/TreeGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonExtPanel.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/commonjs/scriptLoader.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.cust.ViewWindow.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/globalVariable.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/customerQuery.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/mktManage/mktSpecializationTool/mktSpecializationBusiCircle.js"></script>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerClub/agileQuery.js"></script>
<!-- 自动筛选规则设置 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerClub/flexibleQuery.js"></script>
<!-- 客户群对应客户经理维护展示的js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerClub/groupCustMgrEdit.js"></script>

<!-- 客户群成员维护展示的js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerClub/groupLeaguerEdit.js"></script>

<!-- 客户群新增，修改详情展示的js -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/customerClub/customerGroupEdit.js"></script>

<!-- 客户群放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js"></script>

<!-- 创建客户群js  -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/custSearchByDetailType/searchCustForGroup.js"></script>
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
</head> 
</html>