<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>关于</title>
<style text/css>
body {
	margin: 0px;
	background-color: #F1F1F1;
	overflow-x: hidden;
	overflow-y: hidden;
	font-family: "微软雅黑", "宋体", Arial, sans-serif;
	font-size: 12px;
}
</style>
</head>
<body>
	<div id="center">
		<div id="image">
			<img src="${ctx}/images/classics/index/about-top.png" />
		</div>
		<div id="content" style="width: 100%">
			<ul style="list-style-type: none; margin-top: 10px;margin-left: 20px;">
				<li style="margin-top: 5px; ">${systemVersion}</li>
				<li style="margin-top: 5px;">${cnCopyright}</li>
				<li style="margin-top: 5px;">${enCopyright}</li>
			</ul>
		</div>
	</div>
</body>
</html>
