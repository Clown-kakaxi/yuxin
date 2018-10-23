<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html> 
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/commonExtPanel.js"></script>

<script type="text/javascript"src="<%=request.getContextPath()%>/contents/commonjs/scriptLoader.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/customer/customerGroup/baseGlobalVariable.js"></script>
<!-- 客户群视图 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.custGroup.ViewWindow.js"></script>
<!-- 客户视图 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.cust.ViewWindow.js"></script>
<!-- 基础工具，被产品放大镜依赖 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>

<!-- 产品放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<!-- 人员放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<!-- 组织机构放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<!-- 客户放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.indexfeild.js"></script>
<!-- 客户群放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js"></script>

<!-- 营销活动引入 START● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● -->
<%@ page import="com.yuchengtech.bob.core.MktActivityParamManager"  language = "java"%>
<%@ page import="com.yuchengtech.bob.core.MktActivityParamManager1"  language = "java"%>

<script type="text/javascript">
	var a="<%=request.getContextPath()%>";
	var basepath = "/" + a.substring(1, a.length);	
	<%
	String aimCustSource =   MktActivityParamManager.getInstance().findParamValueByName(MktActivityParamManager.AIM_CUST_SOURCE);
	String mktAppType =   MktActivityParamManager1.getInstance().findParamValueByName(MktActivityParamManager1.MKT_APP_TYPE);
	
	out.print("var __aimCustSource = '"+aimCustSource+"';");//客户来源渠道参数
	out.print("var __mktAppType = '"+mktAppType+"';");//营销活动审批方式参数
	%>

</script>
<!-- 复选下拉框 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/css/LovCombo.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/LovCombo.js"></script>

<!-- 营销活动产品修改 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityProdEditInfo1.js"></script>
<!-- 营销活动客户修改 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityCustEditInfo.js"></script>
<!-- 营销活动渠道修改 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityChanelEditInfo.js"></script>
<!-- 附件信息 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js"></script>
<!-- 审批信息 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityShenPiInfo.js"></script>

<!-- 活动主文件-->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage/mktActivityList1.js"></script>

<!-- 生成营销活动和创建商机 -->
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusOpportManage/myBusOpportAddForm_ProdList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusOpportManage/myBusOpportSalesActivView.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusOpportManage/myBusOpportForm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityProdEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityCustEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityChanelEditInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktActivityManage2/mktActivityList1.js"></script>

<!-- 下面3个JS是展示关系图需要的JS -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxclient-ie1.8.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxGrapth-Crm-locale-ext-v1.000.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/crm-mxGraph-api-v1.000.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/offerLinkMkt/membersRelationEdit.js"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/customer/offerLinkMkt/offerLinkMkt.js"></script> 

</head>

<body>

</body>
</html>