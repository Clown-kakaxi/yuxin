<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/template/template1.css" />
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	$(function() {
		url = "${ctx}/ecif/servicemonitor/list.json";
		initGrid();
		grid.loadData();
		setInterval(function(){
					var opurl = grid.options.url;
					var ctx = "/";
					if (opurl) {
						ctx = "/" + opurl.split("/")[1] + "/";
					}
					$.ajax({
						cache : false,
						async : true,
						url : ctx + "/bione/common/getComboxData.json",
						dataType : 'json',
						data : {
							"paramTypeNo" : ""
						},
						type : grid.options.type,
						complete : function(XMLHttpRequest) {
							BIONE.isSessionAlive(XMLHttpRequest);
						},
						success : function() {
							grid.loadData();
						}
					});
		},1000*10);
	});
	
	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							
							{
								display : '主机名',
								name : 'hostName',
								align : 'center',
								width : '12%',
								minWidth : '10%'
							},{
								display : 'IP地址',
								name : 'ipAddr',
								align : 'center',
								width : '13%',
								minWidth : '5%'
							},
							{
								display : '端口号',
								name : 'servicePort',
								align : 'center',
								width : '5%',
								minWidth : '2%'
							},
							{
								display : '服务名',
								name : 'serviceName',
								align : 'center',
								width : '15%',
								minWidth : '5%'
							},
							{
								display : '进程号',
								name : 'processID',
								align : 'center',
								width : '8%',
								minWidth : '2%'
							},
							{
								display : '服务状态',
								name : 'serviceStart',
								align : 'center',
								width : '9%',
								minWidth : '2%',
								render : RenderFlag3
							},
							/**
							{
								display : '启动时间',
								name : 'startTime',
								align : 'center',
								width : '16%',
								minWidth : '3%',
								render : Long2Date1
							},
							**/
							{
								display : '停止时间',
								name : 'stopTime',
								align : 'center',
								width : '16%',
								minWidth : '3%',
								render : Long2Date2
							}
							],
					checkbox : false,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					delayLoad :true,
					sortName : 'stopTime', //第一次默认排序的字段
					sortOrder : 'desc',
					onDblClickRow : null,
					toolbar : {}
				});
	}

	function RenderFlag3(rowdata){
		if(rowdata.serviceStart == '0'){
			return '启动';
		}else if(rowdata.serviceStart == '1'){
			return '<font color="red">停止<font>';
		}else{
			return '<font color="red">未知<font>';
		}
	}
	

    
	function Long2Date1(rowdata){
		if(rowdata.startTime!=null ){
			return Long2Date(rowdata.startTime);
		}
		
	}
	function Long2Date2(rowdata){
		if(rowdata.stopTime!=null){
			return Long2Date(rowdata.stopTime);			
		}

	}
	
	function Long2Date(time){
		if(time!=null){
           var d;
           d = new Date(time);
           return d.getYear()+"年"+(d.getMonth()+1)+"月"+d.getDate()+"日 "+d.getHours()+"时"+d.getMinutes()+"分"+d.getSeconds()+"秒";
			
		}

	}
	
	
	
    var grid = null;
    $(function() {
	templateshow();
	templateinit();

    });
    function templateinit() {
	$(".l-dialog-btn").live('mouseover', function() {
	    $(this).addClass("l-dialog-btn-over");
	}).live('mouseout', function() {
	    $(this).removeClass("l-dialog-btn-over");
	});
	$(".l-dialog-tc .l-dialog-close").live('mouseover', function() {
	    $(this).addClass("l-dialog-close-over");
	}).live('mouseout', function() {
	    $(this).removeClass("l-dialog-close-over");
	});
	$(".searchtitle .togglebtn").live(
		'click',
		function() {
		    var searchbox = $(this).parent().nextAll(
			    "div.searchbox:first");
		    var centerHeight = $("#center").height();
		    if ($(this).hasClass("togglebtn-down")) {
			$(this).removeClass("togglebtn-down");
			searchbox.slideToggle('fast', function() {

			    if (grid) {
				grid.setHeight(centerHeight
					- $("#mainsearch").height() - 8);
			    }
			});
		    } else {
			$(this).addClass("togglebtn-down");
			searchbox.slideToggle('fast', function() {

			    if (grid) {
				grid.setHeight(centerHeight
					- $("#mainsearch").height() - 3);
			    }
			});
		    }
		});
    }
    function templateshow() {
	$("#center").height($(document).height()-8);
	if (grid) {
	    var centerHeight = $("#center").height();
	    grid.setHeight(centerHeight - $("#mainsearch").height() - 8);
	}
    }
</script>
</head>
<body>
	<div id="center">
		<div id="mainsearch">
			<div class="searchtitle">
			      <div class="togglebtn">&nbsp;</div>
			</div>
		</div>
		<div class="content">
			<div id="maingrid" class="maingrid"></div>
		</div>
	</div>
</body>
</html>