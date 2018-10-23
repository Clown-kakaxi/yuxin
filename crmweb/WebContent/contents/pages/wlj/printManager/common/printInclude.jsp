<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" language="java"%>
<%@ page import="com.yuchengtech.bob.vo.AuthUser" language="java"%>
<%@ taglib uri="/VersionControlTag" prefix="version" %>

<%@page import="com.yuchengtech.crm.version.VersionInformation.Version"%>
<script type="text/javascript">
	var a="<%=request.getContextPath()%>";
	var basepath = "/" + a.substring(1, a.length);	
	<%
	if((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
		//Session过期，重新登录
		out.print("top.location.href = basepath;");
	}else{
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();
		out.print("var __userId = '"+userId+"';");
		out.print("var __userName = '"+auth.getUsername()+"';");
		out.print("var __userCname = '"+auth.getUsername()+"';");
		//公共JS变量
		out.print("var __units = '"+auth.getUnitId()+"';");
		out.print("var __unitname = '"+auth.getUnitName()+"';");
		out.print("var __unitlevel = '"+auth.getUnitlevel()+"';");
	}
	%>
</script>
<version:frameScript type="text/javascript" src="/contents/resource/ext3/adapter/ext/ext-base.js" />
<version:frameScript type="text/javascript" src="/contents/resource/ext3/ext-all.js"/>
<version:frameScript type="text/javascript" src="/contents/resource/ext3/ux/ux-all.js"/>
<version:frameScript type="text/javascript" src="/contents/pages/common/Crm-Ext-Patch-1.000-v1.0.js"/>
<version:frameScript type="text/javascript" src="/contents/pages/common/Crm-Ext-Extends-1.000-v1.0.js"/>  
<version:frameScript type="text/javascript" src="/contents/resource/ext3/locale/ext-lang-zh_CN.js"/>
<!-- 控制点权限判断组件 -->
<version:frameScript type="text/javascript" src="/contents/pages/common/ViewContext.js"/>  

<script type="text/javascript">
//Ajax请求超时时间
Ext.Ajax.timeout = 90000;
</script>