<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>ExtTop - Desktop Sample App</title>

	<link  type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/contents/resource/ext3/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/pages/desktop/css/desktop.css" />

	<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/adapter/ext/ext-base.js"/></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ext-all.js"/></script>


    <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/desktop/js/StartMenu.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/desktop/js/TaskBar.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/desktop/js/Desktop.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/desktop/js/App.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/desktop/js/Module.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/desktop/sample.js"></script>
</head>
<body scroll="no">

<div id="x-desktop">

    <dl id="x-shortcuts">
        <dt id="grid-win-shortcut">
            <a href="#"><img src="images/s.gif" />
            <div>Grid Window</div></a>
        </dt>
        <dt id="acc-win-shortcut">
            <a href="#"><img src="images/s.gif" />
            <div>Accordion Window</div></a>
        </dt>
    </dl>
</div>

<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbuttons-panel"></div>
	<div class="x-clear"></div>
</div>

</body>
</html>
