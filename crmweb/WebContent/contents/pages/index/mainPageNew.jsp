<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ include file="/contents/pages/common/includes.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/contents/css/comm.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/contents/css/frame.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--bg-->
<div class="bg">
  <div class="bgbody clearfix">

    <div class="ic_box clearfix">
      <!--date-->
      <div class="ic_box_left">
      	<iframe scrolling="no" frameborder="0" width="440" height="362"
      			src="<%=request.getContextPath()%>/contents/pages/index/mainPageCalander.jsp"
      	></iframe>
      </div>
      <!--date end-->
      <div class="ic_box_cr">
     	 <iframe scrolling="no" frameborder="0" width="695" height="362"
     	 		 src="<%=request.getContextPath()%>/contents/pages/index/mainPagePortal.jsp">
     	 </iframe>
      </div>
    <div class="ic_under">
      <iframe scrolling="no" frameborder="0" width="100%" height="192"
      		  src="<%=request.getContextPath()%>/contents/pages/index/mainPageIcunder.jsp">
      </iframe>
    </div>
  </div>
  <!--link-->
  <!--link end-->
</div>
</div>
</body>
</html>