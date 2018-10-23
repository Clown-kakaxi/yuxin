<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		 	labelWidth : 120,
			inputWidth : 260,
			space : 30, 
			fields : [ {
				name : 'alarmId',
				type : 'hidden'
			}, {
				display : "报警模块",
				name : "alarmModule",
				newline : true,
				type : "text"
			}, {
				display : "报警级别",
				name : "alarmLevel",
				newline : true,
				type : "text"
			}, {
				display : "错误代码",
				name : "errorCode",
				newline : true,
				type : "text"
			}, {
				display : "发生日期",
				name : "occurDate",
				newline : true,
				type : "text"
			}, {
				display : "发生时间",
				name : "occurTimeStr",
				newline : true,
				type : "text"
			}, {
				display : "发起系统",
				name : "srcSysCd",
				newline : true,
				type : "text"
			}, {
				display : "报警状态",
				name : "alarmStat",
				newline : true,
				type : "text"
			}, {
				display : "发送次数",
				name : "sendNum",
				newline : true,
				type : "text"
			}, {
				display : "报警内容",
				name : "alarmInfo",
				newline : true,
				type : "textarea",
				attr : {
					style : "height: 100px;"
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmweb/${id}"});
		}
		BIONE.validate(mainform);
		var buttons = [];
		buttons.push({
			text : '关闭',
			onclick : cancleCallBack
		});

		BIONE.addFormButtons(buttons);

	});

	function cancleCallBack() {
		BIONE.closeDialogAndReloadParent("authResWin","maingrid" );
		//BIONE.closeDialog("authResWin");
	}
	
	//数字时间戳转换成日期时间函数，time为传入的数字时间戳，如果数字时间戳先前作了/1000运算，请先*1000再传入
	function changeTimeFormat(time) {
	    var date = new Date(time);
	    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
	    var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	    var hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	    var mm = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	    return date.getFullYear() + "-" + month + "-" + currentDate+" "+hh + ":" + mm;
	    //返回格式：yyyy-MM-dd hh:mm
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="" method="post"></form>
	</div>
</body>
</html>