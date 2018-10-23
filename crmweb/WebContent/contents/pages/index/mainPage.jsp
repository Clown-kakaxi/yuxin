<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head> 
<script type="text/javascript">
	<%
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
    	String menuId= "240";
		List<String> grants = auth.findGrantByRes(menuId);
		if(grants!=null){
			for(int i=0;i<grants.size();i++){
				out.print("JsContext._grants.push('"+grants.get(i)+"');");
			}
		}
	%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/workSpace/individuationSet/indexPageData.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/workSpace/individuationSet/indexSetCompnent.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/index/mainPage.js"></script> 
<style type="text/css">

.info_box LI {
	list-style:square url(../../resource/ext3/resources/images/default/sys_/tag_list.gif) outside;
	line-height:20px;
	height:20px;
	cursor:pointer;
}

</style>
<script type="text/javascript">
	//信息提醒跳转
	function text(url,ct){
		parent.booter.indexLocate(400,url);
	}
	function redirect() {

		document.location.href =basepath+'/contents/pages/workSpace/afficheManage/affiche.jsp';
	}
	function redirect1() {
		document.location.href = basepath+'/contents/pages/workSpace/remindManage/remindListNew.jsp?msgTyp=100000000';

	}
	function moreredirect() {

		document.location.href = basepath+'/contents/pages/customer/customerManager/customerQuery.jsp';
	}
</script>


</head>
<body>
</body>
</html>