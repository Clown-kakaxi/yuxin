<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<!-- 公共选择放大镜 -->
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<!-- 指标放大镜 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.IndexSearchField.js"></script>
<!-- 规则条件编辑页面-->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/rule/ruleCondition.js"></script>
<!-- 规则编辑页面-->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/rule/ruleSetManeger.js"></script>
<!-- 主页面 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/rule/ruleScoreSet.js"></script>


	
</head>
<body>
<!-- 保存用途给放大镜用 -->
<input id='useType' type='hidden'/>
</body>
</html>