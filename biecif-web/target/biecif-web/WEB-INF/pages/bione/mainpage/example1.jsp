<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<!-- 首页例子1 -> 列表型 -->
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/mainpage/example.css" />
<script type="text/javascript">

	$(function(){
		var contentHeight = $(document).height();
		$("#all").height(contentHeight);
		//列头图片维护
		$(".iconspan").css("background","url(${ctx}/images/classics/icons/bullet_blue.png) no-repeat");
	});
	
</script>
</head>
<body>
	<div id="all">
		<ul>
			<li class="gridli">
				<p>
					<!-- warn: 此处span和a标签顺序必须先span再a，不然span右对齐会在ie6，7，8下自动换行 -->
					<div class="iconspan"></div>
					<span>[2013-05-12]</span>
					<a href="#">集团客户授信信息报表</a>
				</p>
			</li>
			<li class="gridli">
				<p>
					<div class="iconspan"></div>
					<span>[2013-05-14]</span>
					<a href="#">贷款明细报表</a>
				</p>
			</li>
		</ul>
	</div>
</body>
</html>