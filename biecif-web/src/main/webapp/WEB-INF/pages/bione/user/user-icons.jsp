<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<style type="text/css">
.iconlist {
	width: 460px;
	padding: 10px;
}

.iconlist li {
	border: 1px solid #FFFFFF;
	float: left;
	display: block;
	padding: 2px;
	cursor: pointer;
	width: 45px;
	height: 45px;
}

.iconlist li.over {
	border: 1px solid #516B9F;
}

.iconlist li img {
	width: 45px;
	height: 45px;
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
		BIONE.ajax({
			type : "post",
			url : "${ctx}/bione/admin/user/updateHeadIcon",
			data : "userIcon=" + src
		}, function() {
			parent.$("img#headIcon").attr("src", "${ctx}/" + src);
			parent.$.ligerui.get("headIcon").close();
		});
	});
</script>
</html>