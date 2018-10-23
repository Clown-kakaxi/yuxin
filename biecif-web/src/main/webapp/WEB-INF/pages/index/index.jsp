<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<!-- 基础的JS和CSS文件 -->
<script type="text/javascript" src="${ctx}/js/mbMenu/mbMenu.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/ligerui-custom.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/index.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/mbMenu/menu.css" />
<title>${logicSysName}</title>

<SCRIPT type="text/javascript">
    var sidebarWidth = 200;
    var contentWidthOpen;
    var contentWidthClose;
    //侧边栏是否可见
    var sidebarVisible = false;
    //侧边栏当前状态
    var sidebarState = false;
    $(function() {
	window.moveTo(0, 0);
	window.resizeTo(window.screen.width, window.screen.height);
	//初始化头部菜单
	$("#menu").buildMenu({
	    template : "menuVoices.html",
	    additionalData : "pippo=1",
	    menuWidth : 150,
	    openOnRight : false,
	    menuSelector : ".menuContainer",
	    containment : "menu",
	    iconPath : "${ctx}/images/classics/index/",
	    hasImages : true,
	    menuLeft : 20,
	    fadeInTime : 100,
	    fadeOutTime : 300,
	    adjustLeft : 2,
	    minZindex : "auto",
	    adjustTop : 10,
	    opacity : .95,
	    shadow : true,
	    openOnClick : false,
	    closeOnMouseOut : true,
	    closeAfter : 500,
	    hoverIntent : 0
	});
	var $sidebarbtn = $('#open-close-sidebar');
	var delaytime = 500;
	$sidebarbtn.bind("click", function() {

	    if (sidebarVisible) {
		hideSideBar(delaytime);
	    } else {
		showSideBar(delaytime);
	    }

	});

	$("#load").css('display', 'none');
	$("#contentFrame").load(function() {
	    $("#load").css('display', 'none');
	    $("#contentFrame").css('visibility', 'visible');
	});
	showWindow();
	showPwdOverdueTip(); // 弹出密码过期的提示窗口
	showPwdIsDeftTip(); // 提示使用的是默认密码，要求修改！
    });
    function showWindow() {
	//==================处理页面整体布局==================
	var $mainContent = $("#mainContent");
	var $sidebar = $("#sidebar");
	var $sidebarbtn = $('#open-close-sidebar');
	var $sidebarbutton = $("#sidebarbutton");
	var $content = $("#content");
	var sidebarbuttonWidth = 12;
	var $contentFrame = $("#contentFrame");
	var mainContentHeight = 0;
	var $header = $("#header");
	var $navi = $("#navi");
	//设置mainContent DIV的高度
	mainContentHeight = $(window).height() - $header.height()
		- $navi.height();
	$mainContent.height(mainContentHeight);
	$mainContent.css('height', mainContentHeight);
	$sidebar.css('height', mainContentHeight - 10);
	$sidebarbutton.css('height', mainContentHeight);
	$("#open-close-sidebar").css('top', mainContentHeight / 2 - 20);
	$content.css("height", mainContentHeight - 8);
	$contentFrame.height(mainContentHeight - 8);
	//设置content DIV的宽度
	contentWidthOpen = $mainContent.width() - sidebarWidth
		- sidebarbuttonWidth;
	contentWidthClose = $mainContent.width() - sidebarbuttonWidth;
	if (sidebarState) {
	    $content.width(contentWidthOpen);
	} else {
	    $content.width(contentWidthClose);
	}
    }
    window.onresize = function() {
	showWindow();
    };
    //显示侧边栏
    function showSideBar(delaytime) {

	var sideBar = $('#sidebar');

	sideBar.show();

	$('#content').animate({
	    width : contentWidthOpen
	}, delaytime);

	sideBar.animate({
	    width : sidebarWidth
	}, delaytime, null, function() {

	    sidebarVisible = true;
	});

	var openCloseSidebar = $("#open-close-sidebar");
	openCloseSidebar.removeClass("sidebar_open");
	openCloseSidebar.addClass("sidebar_close");
	sidebarState = true;
    }

    //隐藏侧边栏
    function hideSideBar(delaytime) {

	var sideBar = $('#sidebar');

	sideBar.animate({
	    width : 0
	}, delaytime);

	$('#content').animate({
	    width : contentWidthClose
	}, delaytime, null, function() {
	    sidebarVisible = false;
	    sideBar.hide();

	});

	var openCloseSidebar = $("#open-close-sidebar");
	openCloseSidebar.removeClass("sidebar_close");
	openCloseSidebar.addClass("sidebar_open");
	sidebarState = false;
    }

    /**
     * 响应菜单点击事件
     * menuId:菜单Id
     * menuName:菜单Id
     * url:导航路径
     * isFirstLevel:是否是第一层菜单
     */
    function doMenuClick(menuId, menuName, url, isFirstLevel) {
		BIONE.ajax(({
			url:'${ctx}/bione/admin/log/addLog',
			data:'logEvent='+encodeURI('用户点击'+menuName+'菜单')
			}),function(){
		});
		$("#navPathName").html(menuName);
		url = "${ctx}/" + url;
		if (menuName == "首页") {

	    	if (sidebarVisible) {
				//隐藏侧边栏
				hideSideBar(100);
		    }
		    $("#sidebar").html("");
		    $("#contentFrame").attr("src", url);
		    
		} else {
		    var navUrl = "${ctx}/index/initAccordionMenu.json?parentId=" + menuId;
		    var ret = commonAjaxCall(navUrl, 'sidebar', true , 'post');
		    if (ret) {
				//initializing the accordion
				accordionMenu = $('#navigation').accordion({
				    active : false,
				    header : '.head',
				    navigation : true,
				    event : 'click',
				    alwaysOpen : false,
				    animated : 'easeslide',//bounceslide
				    autoHeight : false
				});
		    }
		    if (!sidebarVisible) {
				showSideBar(100);
		    }
		    /**
		     * @Revision : 2013-5-28 
		     * 说明：应hrb-ecif项目组的要求：在主菜单发生变更后，主界面要求回到默认图片；
		     * 此处变更不提交biappframe-web. 
		     */
		    $("#contentFrame").attr("src", "${ctx}/index/welcome");
		    $("#navPathName").html(menuName);
		    /* * @Revision : 2013-5-28 END */
		}
    }
    
    /*updated by sjf 2013-07-12*/
    var parentObj11 = null;			//选中对象的父对象（DIV或LI）
    var parentObj00 = null;         //选中的左侧一级菜单div对象

    /**
     * 响应菜单功能点击事件
     * url:导航路径
     * navPathName: 导航路径的全名称
     */
    function doMenuFuncClick(url, navPathName,menuName,obj) {
    	 //debugger;
    	//先清理以前选中的样式
    	if(parentObj11!=null){
	    	if(parentObj11.tagName == 'DIV'){
	    		parentObj11.className = 'head';
	    	}else if(parentObj11.tagName == 'LI'){
	    		 parentObj11.style.backgroundColor = '';    	
	    		 parentObj00.className = 'head';
	    	}
    	}
    	
    	//设置现在选中的对象样式
    	parentObj11 = obj.parentNode;

    	if(parentObj11.tagName == 'DIV'){         //没有下级菜单
    		parentObj11.className = 'headselected';
    	
    	}else if(parentObj11.tagName == 'LI'){    //有二级、三级菜单
    		parentObj11.style.backgroundColor = '#F1F1F1';
    		
        	parentObj00 = obj.parentNode.parentNode.parentNode.childNodes[1];   
    		//parentObj00.className = 'headselected';
    	}
    	
    	
		BIONE.ajax(({
			url:'${ctx}/bione/admin/log/addLog?logEvent='+encodeURI('用户点击'+menuName+'菜单')
			}), function(){
		});
		$("#contentFrame").css('visibility', 'hidden');
		$("#load").css('display', 'block');
		$("#navPathName").html(navPathName);
		var navUrl = "${ctx}/" + url;
		$("#contentFrame").attr('src', navUrl);
	}
	
	var permissionDataLoadError = function() {
		alert('加载权限数据 失败,请联系管理员!');
    };

    //-----------获取全局的操作权限信息-----------------
    // 受系统权限控制的操作标识(按钮)
    $.ajax({
	cache : false,
	async : true,
	url : '${ctx}/bione/permission/getProtectedResOperNo.json',
	dataType : 'json',
	type : 'get',
	success : function(result, textStatus, jqXHR) {
	    if (!result)
		return;

	    if (result.success) {
		window['protectedResOperNo'] = result.data;
	    } else {
		permissionDataLoadError();
	    }
	},
	error : function(result, textStatus, errorThrown) {
	    permissionDataLoadError();
	}
    });

    // 当前用户有权限访问的操作标识(按钮)
    $.ajax({
	cache : false,
	async : true,
	url : '${ctx}/bione/permission/getAuthorizedResOperNo.json',
	dataType : 'json',
	type : 'get',
	success : function(result, textStatus, jqXHR) {
	    if (!result)
		return;

	    if (result.success) {
		window['authorizedResOperNo'] = result.data;
	    } else {
		permissionDataLoadError();
	    }
	},
	error : function(result, textStatus, errorThrown) {
	    permissionDataLoadError();
	}
    });

    // 修改当前用户头像
    function headIcon() {
	BIONE.commonOpenDialog("头像选择", "headIcon", 500, 350,
		"${ctx}/bione/admin/user/buildHeadIconList");
    }

    // 修改当前用户密码
    function updatePwd() {
	BIONE.commonOpenDialog("修改密码", "userManage", 497, 320,
		"${ctx}/bione/admin/user/updateCurPwd");
    }

    // 帮助手册
    function useHelp() {
	//alert("使用帮助");
    }

    // 关于
    function about() {
	BIONE.commonOpenDialog("关于", "About", 400, 250,
		"${ctx}/bione/admin/logicSys/about");
    }
    //退出系统
    function quit() {
	$.ligerDialog.confirm('您确定要退出系统吗', function(type) {
	    if (type) {
		$.ajax({
		    cache : false,
		    async : false,
		    url : "${ctx}/login/logout?quit=true",
		    type : 'get',
		    success : function(result) {
			window.opener = null;
			window.open('', '_self', '');
			window.close();
		    }
		});
	    }
	});
    }
    //注销系统
    function logout() {
	$.ligerDialog.confirm('您确定要注销吗', function(type) {
	    if (type) {
		$.ajax({
		    cache : false,
		    async : false,
		    url : "${ctx}/login/logout",
		    type : 'get',
		    success : function(result) {
				window.location.href="${ctx}/login";
		    }
		});
	    }
	});
    }
    /*密码过期，弹出提示框*/
    function showPwdOverdueTip() {
    	$.ajax({
    		cache : false,
    		async : true,
    		url : '${ctx}/bione/admin/passwd/getOverdue.json',
    		dataType : 'json',
    		type : 'get',
    		success : function(result, textStatus, jqXHR) {
    		    if (!result){ 
    		    	return; 
    		    }
    		    if (result.success && result.success=="true") {
    		    	var isOverdue = result.data;
    				if(isOverdue=="1") {
    					$.ligerDialog.confirm('密码使用时间已超出有效期，<br/>请修改密码', function(type) {
    			    	    if (type) {
    			    	    	updatePwd();
    			    	    }
    			    	});
    				}
    		    } else {
    		    	BIONE.tip(result.msg);
    		    }
    		},
    		error : function(result, textStatus, errorThrown) {
    			BIONE.tip("获取用户密码已使用过的时间发生异常！");
    		}
		});
    }
    function showPwdIsDeftTip() {
    	$.ajax({
    		cache : false,
    		async : true,
    		url : '${ctx}/bione/admin/passwd/isDefault?d=' + + new Date().getTime(),
    		type : 'get',
    		success : function(result, textStatus, jqXHR) {
    			if (result && result.success && result.success==true) {
    		    	$.ligerDialog.confirm('您使用的是默认密码，<br/>请尽快修改密码', function(type) {
			    	    if (type) {
			    	    	updatePwd();
			    	    }
			    	});
    		    }
    		},
    		error : function(result, textStatus, errorThrown) {
    			BIONE.tip("判断用户密码是否为默认密码时发生异常！");
    		}
		});
    }
</SCRIPT>


</head>
<body>
	<div id="header">
		<table width="100%" height="68" border="0" cellspacing="0"
			background="${ctx}/images/classics/index/header_bg.jpg"
			cellpadding="0">
			<tr>
				<td width="20%" height="68">
					<div id="logo">
						<img height="49" width="173" src="${ctx}/images/classics/index/index_logo1.png" />
					</div>
				</td>
				<td width="80%" height="68">
					<table width="100%" height="68" border="0" cellspacing="0">
						<tr height="38">
							<td width="15%" valign="top" align="right"
								style="padding-right: 20px">
								<table width="162" height="24" cellspacing="0" cellpadding="0"
									border="0">
									<tr>
										<td>
											<table width="10" height="24" cellspacing="0" cellpadding="0"
												border="0"
												background="${ctx}/images/classics/index/left-help.png">
												<tr>
													<td></td>
												</tr>
											</table>
										</td>
										<td>
											<table width="185" height="24" cellspacing="0"
												cellpadding="0" border="0"
												background="${ctx}/images/classics/index/center-help.png"
												valign="top">
												<tr>
													<td width="4px"></td>
													<%--
													<td align="left"><img
														src="${ctx}/images/classics/index/help.png" width="16"
														height="16" /></td>
													<td align="left"><a href="javascript: useHelp();"
														style="text-decoration: none; color: #6A6A6A;">使用帮助 </a></td>
													<td align="left"><img
														src="${ctx}/images/classics/index/leaf_bak.gif" width="16"
														height="16" /></td>
													<td align="left"><a href="javascript: about();"
														style="text-decoration: none; color: #6A6A6A">关于 </a></td>
													--%>
													
													<td align="left"><img
														src="${ctx}/images/classics/index/cog.png" width="16"
														height="16" /></td>
													<td align="left"><a href="javascript: updatePwd();"
														style="text-decoration: none; color: #6A6A6A;">修改密码</a></td>
													<td align="left"><img
														src="${ctx}/images/classics/index/door_in.png" width="16"
														height="16" /></td>
													<td align="left"><a href="javascript:logout();"
														style="text-decoration: none; color: #6A6A6A;">注销</a></td>
													<td align="left"><img
														src="${ctx}/images/classics/index/cross.png" width="16"
														height="16" /></td>
													<td align="left"><a href="javascript:quit();"
														style="text-decoration: none; color: #6A6A6A;">退出</a></td>
														
												</tr>
											</table>
										</td>
										<td>
											<table width="10" height="24" cellspacing="0" cellpadding="0"
												border="0"
												background="${ctx}/images/classics/index/right-help.png">
												<tr>
													<td></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="30">
							<td><div id="menu">${menuInfoHTML}</div></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table height="26" width="100%"
			background="${ctx}/images/classics/index/welcome.jpg">
			<tr>
				<td style="padding-left: 100px; font-size: 12px; color: #666666;">${userInfo}</td>
			</tr>
		</table>
	</div>
	<div id="navi">
		<table height="23" width="100%"
			style="background:url(${ctx}/images/classics/index/navi_bg.jpg) repeat-x center"
			border="0">
			<tr height="23" align="left">
				
				<td background="${ctx}/images/classics/index/logout_bg.jpg"
					width="138" style="padding-left: 60px;">
					<%--
					<table style="font-size: 12px; color: #8E9BAC;">
						<tr>
							<td><img src="${ctx}/images/classics/index/cog.png"
								width="14" height="14" /></td>
							<td>&nbsp;<a href="javascript: updatePwd();">修改密码</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td><img src="${ctx}/images/classics/index/door_in.png"
								width="14" height="14" /></td>
							<td>&nbsp;<a href="javascript:logout();">注销</a>&nbsp;&nbsp;&nbsp;
							</td>
							<td><img src="${ctx}/images/classics/index/cross.png"
								width="14" height="14" /></td>
							<td>&nbsp;<a href="javascript:quit();">退出</a>&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
					--%>
				</td>
				<td width="10"
					style="background:url(${ctx}/images/classics/index/logout.jpg) no-repeat center">&nbsp;</td>
				<td style="font-size: 12px; color: #666666;" align="left">&nbsp;<img
					src="${ctx}/images/classics/index/navi_icon.png" /> &nbsp;<span
					id="navPathName" style="color: #666666">首页</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="userhead">
		<table width="100%" border="0" width="51" cellspacing="0"
			cellpadding="0">
			<tr>
				<td><img src="${ctx}/images/classics/index/head_top.png" /></td>
			</tr>
			<tr height="45">
				<td>
					<table width="51" border="0" cellspacing="0" cellpadding="0"
						height="45">
						<tr height="45">
							<td width="30" height="45"><img
								src="${ctx}/images/classics/index/head_left.png" /></td>
							<td width="45" height="45">
								<!--<a href="javascript: headIcon();">-->
								<img id="headIcon" width="45" height="45" src="${ctx}/${userIcon}" />
								<!--</a>--></td>
							<td width="3" height="45" style="display: block;"><img
								src="${ctx}/images/classics/index/head_right.png" /></td>
						</tr>

					</table>
				</td>
			</tr>
			<tr>
				<td><img src="${ctx}/images/classics/index/head_bottom.png"
					style="display: block;" /></td>
			</tr>
		</table>
	</div>
	<div id="mainContent">
		<div id="sidebar"></div>
		<div id="sidebarbutton">
			<a id="open-close-sidebar"  class="sidebar_open"></a>
		</div>
		<div id="content">
			<div id="load" class="progressBar">页面加载中，请稍等...</div>
			<iframe id="contentFrame" frameborder="0" src="${ctx}/${indexUrl}"></iframe>
		</div>
	</div>
</body>
</html>
