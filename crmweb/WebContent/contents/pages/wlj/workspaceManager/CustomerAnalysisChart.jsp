<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1"
	user-scalable="no">
<link rel="shortcut icon" href="images/favicon.png">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/echarts-2.2.7/util/bootstrap.min.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries-->
<!--[if lt IE 9]><script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script><script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/echarts-2.2.7/util/main.css">
<script id="font-hack" type="text/javascript">
	if (/windows/i.test(navigator.userAgent)) {
		var el = document.createElement('style');
		el.innerHTML = ''
				+ '@font-face {font-family:"noto-thin";src:local("Microsoft Yahei");}'
				+ '@font-face {font-family:"noto-light";src:local("Microsoft Yahei");}';
		document.head.insertBefore(el, document.getElementById('font-hack'));
	}
</script>
<title>ECharts Examples</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/echarts-2.2.7/util/perfect-scrollbar.min.css">
</head>
<!--[if lte IE 8]>
<body class="lower-ie">
<![endif]-->
<!--[if (gt IE 8)|!(IE)]><body class="undefined"></body><![endif]-->
<div id="lowie-main">
	<img src="./echarts-2.2.7/util/forie.png" alt="ie tip">
</div>
<div id="main">
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class=""><img
				src="<%=request.getContextPath()%>/echarts-2.2.7/util/pic_01.gif"
				alt="echarts logo" class="navbar-logo"></a>
		</div>
		<div id="bs-example-navbar-collapse-1"
			class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li id="nav-download" class="dropdown"><a href="#"
					data-toggle="dropdown" class="dropdown-toggle">下载<b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="download.html">下载</a></li>
						<li><a href="builder.html">在线构建</a></li>
						<li><a href="download-map.html">地图</a></li>
						<li><a href="download-theme.html">主题</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
	</nav>
	<div id="left-chart-nav">
		<ul></ul>
	</div>
	<div id="nav-mask"></div>
	<div id="nav-layer">
		<ul class="chart-list"></ul>
	</div>
	<div id="chart-demo"></div>
</div>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jQuery/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/validator.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/waypoint.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/perfect-scrollbar.min.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/jquery.lazyload.min.js"></script>
<script type="text/javascript">
	var GALLERY_PATH = 'http://echarts.baidu.com/gallery/';
	console.log(GALLERY_PATH);
	var GALLERY_EDITOR_PATH = GALLERY_PATH + 'editor.html?c=';
	var GALLERY_VIEW_PATH = GALLERY_PATH + 'view.html?c=';
	var GALLERY_THUMB_PATH = GALLERY_PATH + 'data/thumb/';
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/config.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/chart-list.js"></script>
<!-- 图表面板 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/examples-nav.js"></script>
<!-- 左边导航树 -->
<script type="text/javascript">
	function loadDemo() {
		var chartId = location.hash.slice(1);
		$('#chart-demo')
				.html(
						'<iframe src="' + GALLERY_EDITOR_PATH + chartId + '"></iframe>');
	}
	$(window).on('hashchange', function() {
		loadDemo();
	});
	loadDemo();
</script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/echarts-2.2.7/util/hm.js"></script>

</html>
