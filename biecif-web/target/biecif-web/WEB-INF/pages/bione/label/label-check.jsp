<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template14.jsp">
<style>
body {
	font: 12px/150% Arial, Verdana, "宋体";
	color: #333;
}

.m,.mt {
	overflow: hidden;
	zoom: 1;
}

#select {
	border: 1px solid #ddd;
	padding-bottom: 5px;
	margin-bottom: 0;
	zoom: 1;
	background-color: #F7F7F7;
}

#select .mt {
	padding: 0 10px;
	background: #F7F7F7;
	border-bottom: 1px solid #ccc;
}

.mt {
	cursor: default;
}

#select dl#select-brand {
	
}

#select dl {
	padding: 4px 0 2px;
	margin: 0 5px;
	overflow: hidden;
	zoom: 1;
	background-color:#F7F7F7;
}

.conditionDiv{
	width:100%;
}

#select dt {
	float: left;
	font-weight: bold;
	text-align: right;
	line-height: 25px;
}

#select dd {
	float: right;
	position: relative;
	overflow: hidden;
}

#select-brand .content {
	float: none;
	overflow: hidden;
	height: auto;
	margin: 0;
	padding: 0px;
}

#select dd div {
	float: left;
	height: 20px;
	margin-right: 15px;
	padding-top: 5px;
}

#select dl.conditionDiv dd {
	position: relative;
	padding-right: 70px;
}

#select dd a#all-revocation {
	display: block;
	position: absolute;
	right: 10px;
	color: #005AA0;
	text-decoration:none;
}

#select dl.conditionDiv div:hover{
}

#select dl.conditionDiv div {
	position: relative;
	padding: 0 20px 0 5px;
	margin-bottom: 2px;
	height: 20px;
	border: 1px solid #E6E6E6;
	line-height: 20px;
}

dl.conditionDiv a:visited {
	float: none;
	white-space: nowrap;
	height: 20px;
	line-height: 20px;
	margin-top: 0;
	background: none;
	color: #333;
}

#select dl.conditionDiv b {
	display: block;
	width: 11px;
	height: 11px;
	position: absolute;
	right: 4px;
	top: 4px;
	cursor: pointer;
}

.subbrand {
	border-top:1px dotted #ccc;
}

.label_a {
	text-decoration:none;
}
.label_a_selected{
	color: 'red';
}
.all {
	width: 30px;
}
</style>
<script type="text/javascript">
	var win = parent || window;
	var advanceHeight = 0;
	if (win.LABEL == null) {
		win.LABEL = {};
	}
	LABEL = win.LABEL;
	
	// 外部调用设置宽度方法
	LABEL.setWidth = function(width) {
		var centerWidth = $("#select").width();
		if (width) {
			centerWidth = width;
		}
		var commonDt = centerWidth * 0.1;
		var commonDd = centerWidth * 0.87;
		$("#select dt").width(commonDt);
		$("#select dd:not(#conditionDd)").width(commonDd);
		$("#select-brand .content ").width(commonDd);
		$("#select dd div:not(.all)").width(commonDd/5);
	};
	$(function() {
		$.ajax({
			url: LABEL.url != null ? LABEL.url : "",
			type: "get",
			dataType:"json",
			data: LABEL.data != null ? LABEL.data : {},
			success: function(data) {
				if (data) {
					$.each(data, function(i, n) {
						// 返回值是一个对象列表，对象有两个参数：labelType是类对象；label是该类对象下的标签对象列表。
						var labelType = n.labelType;
						var label = n.label;
						// 构造网页结构
						var dl = $('<dl/>');
						if (i == 0) {
							dl.attr('id', 'select-brand').css('margin-top', '5px');
						} else {
							dl.addClass('subbrand');
						}
						// 此处设置标签类名
						var dt = $('<dt/>').text(strParse(labelType.typeName, 8) + ":").attr("title", labelType.typeName);
						dl.append(dt);
						var dd = $('<dd/>');
						var content = $('<div/>').addClass("content");
						// 此处设置标签相关
						
						var div1 = $('<div/>').addClass("all");
						var a1 = $('<a/>').addClass("label_a").attr({
							href: '#',
							typeId: labelType.typeId,
							select: "all"
						}).text("不限");
						div1.append(a1);
						if (n == 0) {
							content.append(div1);
						} else {
							dd.append(div1);
						}
						$.each(label, function(i, n) {
							var div = $('<div/>');
							var a = $('<a/>').addClass("label_a").attr({
								href: '#',
								type: labelType.typeName,
								labelId: n.labelId,
								typeId: labelType.typeId,
								title: n.labelName,
								select: false
							}).text(strParse(n.labelName));
							div.append(a);
							if (n == 0) {
								content.append(div);
							} else {
								dd.append(div);
							}
							
						});
						if (n == 0) {
							dd.append(content);
						}
						dl.append(dd);
						$('#label_pad').append(dl);
					});
					if (data.length > 2) {
						toggleExt(1);
						advanceHeight = $("#advance").height();
					}
				}
				setParam();
				// 事件：加载完成
				if (LABEL.onReady) {
					LABEL.onReady($("#select").height() + advanceHeight + 8);
				}
			},
			error: function() {
				win.BIONE.tip("数据加载失败！");
			}
		});
	});
	
	function setParam() {
		// 整体布局
		var centerWidth = $("#select").width();
		var commonDt = centerWidth * 0.1;
		var commonDd = centerWidth * 0.87;
		$("#select dt").width(commonDt);
		$("#select dd:not(#conditionDd)").width(commonDd);
		$("#select-brand .content ").width(commonDd);
		$("#select dd div:not(.all)").width(commonDd/5);
		
		// 事件
		$('#label_pad a[select="all"]').click(function() {
			$("#label_pad a[typeId='" + $(this).attr("typeId") + "'][select!='all']").css("color", "black").attr("select", false);
			var labels = getSelectedLabel();
			// 事件：点击标签
			if (LABEL.onAfterClickLabel) {
				LABEL.onAfterClickLabel(labels);
			}
		});
		$('#label_pad a[select!="all"]').click(function() {
			if ("true" == $(this).attr("select")) {
				$(this).css("color", "black").attr("select", false);
				var labels = getSelectedLabel();
				// 事件：点击标签
				if (LABEL.onAfterClickLabel) {
					LABEL.onAfterClickLabel(labels);
				}
			} else {
				$(this).css("color", "red").attr("select", true);
				var labels = getSelectedLabel();
				// 事件：点击标签
				if (LABEL.onAfterClickLabel) {
					LABEL.onAfterClickLabel(labels);
				}
			}
		});
	}
	
	// 字符串转换，长度过大时截取部分字符
	function strParse(str, maxLength) {
		var len = 16;
		var l = getLength(str);
		if (maxLength) {
			len = maxLength;
		}
		if (l > len) {
			for (var i = 0; i < len; i++) {
				var s = str.slice(0, i);
				if (getLength(s) > len) {
					return s + "..";
				}
			}
			return str.slice(0, len) + "..";
		}
		return str;
	}
	
	// 字符串实际长度
	function getLength(str) {
	    ///<summary>获得字符串实际长度，中文2，英文1</summary>
	    ///<param name="str">要获得长度的字符串</param>
	    var realLength = 0, len = str.length, charCode = -1;
	    for (var i = 0; i < len; i++) {
	        charCode = str.charCodeAt(i);
	        if (charCode >= 0 && charCode <= 128) realLength += 1;
	        else realLength += 2;
	    }
	    return realLength;
	}
	// 获取已经选择标签
	function getSelectedLabel() {
		var labels = [];
		$("#label_pad a[select = 'true']").each(function(i, n) {
			var l = $(n);
			labels.push(l.attr("labelId"));
		});
		return labels;
	}
	
	function toggleExt(i) {
		$("#advance").css("display", "block");
		$("#select dl:gt(" + i + ")").css("display", "none");
		$("#extend").toggle(function() {
			$("#extend strong").text("收起");
			$("#select dl:gt(" + i + ")").css("display", "block");
			if (LABEL.onClickExt) {
				LABEL.onClickExt($("#select").height() + advanceHeight + 8);
			}
		}, function() {
			$("#extend strong").text("展开");
			$("#select dl:gt(" + i + ")").css("display", "none");
			if (LABEL.onClickExt) {
				LABEL.onClickExt($("#select").height() + advanceHeight + 8);
			}
		});
	}
</script>
</head>
<body>
<div id="template.center">
	<div class="m" id="select">
		<div id="label_pad"></div>
		<div class="clr"></div>
	</div>
	<div id="advance" style="height: 21px; text-align: center; line-height: 20px; display: none;">
  		<div id="extend" style="height: 20px; width:100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto; border-left: solid 1px #CCC; border-right: solid 1px #CCC;border-bottom: solid 1px #CCC; cursor: pointer; background: #F7F7F7; position: relative; top: -1px;">
  			<strong>展开</strong>
  		</div>
	</div>
</div>
</body>
</html>