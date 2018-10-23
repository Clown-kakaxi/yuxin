<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/common/includes.jsp"%>
	<script type="text/javascript">
	<%
		String custId = request.getParameter("custId");
		String busiId = request.getParameter("busiId");
		String viewResId = request.getParameter("viewResId");
		out.print("var _custId = '"+custId+"';");
		out.print("var _busiId = '"+busiId+"';");
		
		
		if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//1、重新将菜单下的控制点写入公共变量,用于数据权限控制
			if(viewResId!=null && !"-1".equals(viewResId) && !"".equals(viewResId)){
				List<String> grants = auth.findGrantByRes(viewResId);
				if(grants!=null){
					for(int i=0;i<grants.size();i++){
						out.print("__grants.push('"+grants.get(i)+"');");
					}
				}
			}
		}
	%>
		JsContext.initContext();
	</script>
	<version:frameLink  type="text/css" rel="stylesheet" href="/contents/css/LovCombo.css" />
	<version:frameScript type="text/javascript" src="/contents/pages/common/LovCombo.js"/>
	<version:frameLink type="/contents/wljFrontFrame/styles/search/searchcss/common.css"/>
	<version:frameLink type="/contents/wljFrontFrame/styles/search/searchcss/base_frame.css"/>
	<version:frameLink type="/contents/wljFrontFrame/styles/search/searchthemes/blue/frame.css"/>
	<version:frameLink type="/contents/resource/ext3/resources/css/debug.css"/>
	<version:frameLink type="/contents/wljFrontFrame/styles/search/searchthemes/blue/main.css"/>
	
	<version:frameScript type="text/javascript" src="/contents/frameControllers/Wlj-SyncAjax.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/Wlj-frame-base.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/Wlj-memorise-base.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/search/tiles.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/app/Wlj-frame-function-error.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/debug.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/app/Wlj-frame-function-app-cfg.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/app/Wlj-frame-function-widgets.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/app/Wlj-frame-function-app.js"/>
    
    <version:frameScript type="text/javascript" src="/contents/frameControllers/view/Wlj-view-function-builder.js"/>
    
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/app/Wlj-frame-function-header.js"/>
    <version:frameScript type="text/javascript" src="/contents/frameControllers/widgets/app/Wlj-frame-function-api.js"/>
	
</head>
<body>
</body>
<script type="text/javascript">
    /**
     * 鼠标右键菜单
     * @param {} e 事件
     * @param {} added 额外的菜单配置
     */
    function onContextMenu(e,added){
        var windowMenu = Wlj.frame.functions.app.Util.contextMenus.window;
        for(var key in windowMenu){
            var omenu = {};
            omenu.text = windowMenu[key].text;
            omenu.handler = windowMenu[key].fn.createDelegate(this);
            added.push(omenu);
        }
        if(!window.contextmenu){
            window.contextmenu = new Ext.menu.Menu({
                items: added
            });
        }
        window.contextmenu.showAt(e.getPoint())
    }
    //右键菜单
    Ext.getBody().on("contextmenu",function(e){
        e.preventDefault();
        onContextMenu(e,["-"]);
    });
</script>
</html>