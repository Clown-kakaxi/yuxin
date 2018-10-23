<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript">
var deptNode="";
$(function () {
	$("#deptTreeDiv").ligerTree({
		url : "${ctx}/bione/admin/dept/buildDeptTree.json",
		checkbox : false,
		isLeaf : function(node) {
			if (node.children && node.children.length == 0) {
				return false;
			} else {
				return true;
			}
		},
		onSelect:function(node){
			deptNode=node;
		}
	});
 });
 
function select()
{
    return deptNode;
}
</script>
<title>机构列表</title>
</head>
<body>
<div id="deptTreeDiv" style="margin:0; padding:0"></div>
</body>
</html>