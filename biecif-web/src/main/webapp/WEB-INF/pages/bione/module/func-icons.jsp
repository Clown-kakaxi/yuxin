<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<style type="text/css">
.iconlist {
	width: 450px;
	padding: 10px;
}

.iconlist li {
	border: 1px solid #FFFFFF;
	float: left;
	display: block;
	padding: 2px;
	cursor: pointer;
	width: 16px;
	height: 16px;
}

.iconlist li.over {
	border: 1px solid #516B9F;
}

.iconlist li img {
	width: 16px;
	height: 16px;
}
</style>

</head>
<body>${iconsHTML}
</body>
<script type="text/javascript">
	$(".iconlist li").live('mouseover', function() {
		$(this).addClass("over");
	}).live('mouseout', function() {
		$(this).removeClass("over");
	}).live('click', function() {
		var src = $("img", this).attr("alt");
		BIONE.closeIconDialog('iconselector',src);
	});
</script>
</html>