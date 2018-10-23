<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktPlanManage/mktPlanList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktPlanManage/custDetailList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktPlanManage/prodDetailList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktPlanManage/planCustList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/mktManage/mktPlanManage/planProdList.js"></script>
</head>
<body>
<input id='planIdStr' type='hidden'/>
<input id='executeIdStr' type='hidden'/>
 <div id="s1" class="x-hide-display">
           <p>
		<font size="2" face="Verdana">
		您可以继续操作下面的功能。
		</font>
		</p>
	    <p>
	    <font size="2" face="Verdana">需要指定实施营销计划的目标客户，请点击<A HREF="specifiesTheTargetCustomers.html">指定目标客户</A>。</font>
		<font size="2" face="Verdana">需要制定营销的指标，请点击<A HREF="developmentOfMarketingIndicators.html">制定营销指标</A>。</font>
		<font size="2" face="Verdana">需要指定要实施营销计划的机构，请点击<A HREF="designatedMarketingAgency.html">指定营销机构</A>。</font>
		<font size="2" face="Verdana">需要对已经执行的营销计划进行评价，请点击<A HREF="evaluationOfMarketingPlan.html">营销计划评价</A>。</font>
		<font size="2" face="Verdana">需要制定新的销售活动，请点击<A HREF="../crm/sellManager/sellAction/makeSell.html">制定销售活动</A>。</font>
		<font size="2" face="Verdana">需要创建新的商机，请点击<A HREF="../crm/sellManager/tradeManager/makeTrade.html">创建商机</A>。</font>
        </p>
        </div>
</body>
</html>