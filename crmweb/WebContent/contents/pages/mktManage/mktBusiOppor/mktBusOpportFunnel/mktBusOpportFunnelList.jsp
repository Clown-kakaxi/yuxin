<%-- 营销管理->商机管理->销售漏斗 --%>
<%@ page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>

<script type="text/javascript">
	
<%//声明JS变量
			//查询条件
			String paraJson = URLDecoder.decode(
					request.getParameter("paraJson") == null ? "" : request
							.getParameter("paraJson"), "utf-8");
			out.println("var paraJson = '" + paraJson + "';");%>
	
</script>

<head>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<!-- 基础工具JS文件 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<!-- 客户视图基础JS文件 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.cust.ViewWindow.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxclient-ie1.8.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/mxGrapth-Crm-locale-ext-v1.000.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/commonjs/mxGraphLocal/crm-mxGraph-api-v1.000.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/resource/ext3/ux/maximgb/TreeGrid.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/customer/customerManager/globalVariable.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/common/Crm-Ext-Extends-1.000-v1.0.js"></script>
<!-- 产品放大镜 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js"></script>
<!-- 客户放大镜 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js"></script>
<!-- 用户放大镜 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<!-- 组织机构放大镜 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js"></script>
<!-- 商机池-查询条件定义JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporQueryCondition.js"></script>
<!-- 商机池-销售活动列表JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporSalesActivQuery.js"></script>
<!-- 商机池-商机详情查看JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusiOpporViewForm.js"></script>
<!-- 销售漏斗查询条件Form对象定义JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusOpportFunnel/mktBusOpportFunnelQuery.js"></script>
<!-- 销售漏斗结果列表JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusOpportFunnel/funnelList.js"></script>
<!-- 销售漏斗图形化展示及查询结果列表JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusOpportFunnel/mktBusOpportFunnelList.js"></script>
<!-- 销售漏斗结果列表超链接查看商机列表JS -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/contents/pages/mktManage/mktBusiOppor/mktBusOpportFunnel/mktBusiOpporList.js"></script>

</head>
<body>
</body>
</html>