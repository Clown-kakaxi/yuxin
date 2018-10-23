<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<%@ include file="/common/meta.jsp"%>
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
</style>
<script type="text/javascript">
	var objId = "${objId}"; // 对象ID
	var labelObjId = "${labelObjId}"; // 标签对象ID
	$(function(){
		// 初始化按钮
		BIONE.addFormButtons([{
			text : '取消',
			onclick : f_close
		}, {
			text : '确定',
			onclick : f_save
		}]);
		// 加载标签列表
		BIONE.showLoading();
		$.ajax({
			url: "${ctx}/bione/label/apply/labelOfObj.json",
			type: "get",
			dataType:"json",
			data: {
				labelObjId: labelObjId
			},
			success: function(data) {
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
					$('#labelContent').append(dl);
				});
				
				// 设置展开收起/按钮
				
				if (data.length > 1) {
					toggleExt(1);
				}
				paramSet();
				BIONE.hideLoading();
				initLabels();
			},
			error: function() {
				BIONE.hideLoading();
				BIONE.tip("数据加载失败！");
			}
		});
	});
	
	function paramSet() {
		var centerWidth = $("#center").width();
		var commonDt = centerWidth * 0.1;
		var commonDd = centerWidth * 0.87;
		$("#select dt").width(commonDt);
		$("#select dd:not(#conditionDd)").width(commonDd);
		$("#conditionDd").width(commonDd - 100);
		$("#select-brand .content ").width(commonDd);
		$("#select dd div:not(.content)").width(commonDd/5);
		//在ie8下dd下div自动计算宽度后换行会有问题，没办法，给死宽度，这样每个标签都一样宽，- ，-
		var labelWidth = (commonDd-100)/4;
		$("#conditionDd div").width(labelWidth);
		
		//初始化标签关闭按钮
		var iconCss = "url(${ctx}/images/classics/icons/icons_label.png) no-repeat -288px -30px";
		$(".closeIcon").css("background",iconCss);

		//为所有a标签附上点击方法
		$("#select a").bind("click",function(){
			if ($(this).attr("select") == "true") {
				return;
			}
			$(this).attr("select", true);
			$(this).css("text-decoration","underline");
			var titleTmp = $(this).attr("title");
			var typeTmp = $(this).attr("type");
			var typeId = $(this).attr("typeId");
			var labelId = $(this).attr("labelId");
			var typeStr = "";
			if(!titleTmp || titleTmp == null){
				//若没有title属性，直接获取html
				titleTmp = $(this).html();
			}
			if(typeTmp && typeTmp != null
					&& typeTmp != ""){
				//若type属性不为空
				typeStr += (typeTmp+"：");
			}else{
				typeStr = "";
			}
			var typeLen = getLength(typeStr);
			var labelLen = getLength(titleTmp);
			if (typeLen + labelLen > 18) {
				if (typeLen < 9) {
					labelLen = 18 - typeLen;
				} else if (labelLen < 9) {
					typeLen = 18 - labelLen;
				} else {
					typeLen = 8;
					labelLen = 8;
				}
			}
			//在mt处追加标签
			$("#conditionDd").append("<div style='width:"+labelWidth+"px;'><strong>"+strParse(typeStr, typeLen)+"<font color='#417EB7' title='" + titleTmp + "'>"+strParse(titleTmp, labelLen)+"</font></strong><b class='closeIcon' title='"+titleTmp+"' type='"+typeTmp+"' typeId=" + typeId + "  labelId=" + labelId + " style='background:"+iconCss+";'></b></div>");
			//为‘取消’按钮哦添加方法
			$(".closeIcon").bind("click",function(){
				if($(this).parent().is("#conditionDd div")){
					var title = $(this).attr('title');
					var type = $(this).attr('type');
					$("a[title='"+title+"'][type='"+type+"']").css("text-decoration","none").attr("select", false);
					$(this).parent().remove();
				}
			});
		});
		//为‘全部取消’附上点击方法
		$("#all-revocation").bind("click",function(){
			$("#conditionDd div").remove();
			$(".label_a,a").css("text-decoration","none").attr("select", false);
		});
// 		//为‘取消’按钮哦添加方法
// 		$(".closeIcon").bind("click",function(){
// 			if($(this).parent().is("#conditionDd div")){
// 				var title = $(this).attr('title');
// 				var type = $(this).attr('type');
// 				$("a[title='"+title+"'][type='"+type+"']").css("text-decoration","none");
// 				$(this).parent().remove();
// 			}
// 		});
	}
	
	function getSelectedLabel() {
		var labels = [];
		$.each($('#conditionDd b'), function(i, n) {
			var b = $(n);
			labels.push(b.attr('labelId'));
		});
		return labels;
	}
	
	function f_save() {
		var labels = getSelectedLabel();
		var str = JSON2.stringify(labels);
		$.ajax({
			url: "${ctx}/bione/label/apply/exmaple",
			type: "post",
			dataType: "json",
			data: {
				labels: str,
				objId: objId
			},
			success: function() {
				BIONE.tip("保存成功");
				BIONE.closeDialog("frame");
			},
			error: function() {
				BIONE.tip("保存失败");
			}
		});
	}
	
	function f_close() {
		BIONE.closeDialog("frame");
	}
	// 字符串转换，长度过大时截取部分字符
	function strParse(str, maxLength) {
		var len = 20;
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
	};
	
	function initLabels() {
		$.ajax({
			url: "${ctx}/bione/label/apply/exmaple/labels.json",
			type: "get",
			dataType: "json",
			data: {
				objId: objId,
				labelObjId: labelObjId
			},
			success: function(data){
				$.each(data, function(i, n) {
					var labelId = n.id.labelId;
					$("#labelContent a[labelId=" + labelId + "]").each(function(i, n){
						addLabel($(n));
					});
				});
			}
		});
	}
	
	function addLabel(label) {
		var centerWidth = $("#center").width();
		var commonDd = centerWidth * 0.87;
		var labelWidth = (commonDd-100)/4;
		var iconCss = "url(${ctx}/images/classics/icons/icons_label.png) no-repeat -288px -30px";
		if (label.attr("select") == "true") {
			return;
		}
		label.attr("select", true);
		label.css("text-decoration","underline");
		var titleTmp = label.attr("title");
		var typeTmp = label.attr("type");
		var typeId = label.attr("typeId");
		var labelId = label.attr("labelId");
		var typeStr = "";
		if(!titleTmp || titleTmp == null){
			//若没有title属性，直接获取html
			titleTmp = label.html();
		}
		if(typeTmp && typeTmp != null
				&& typeTmp != ""){
			//若type属性不为空
			typeStr += (typeTmp+"：");
		}else{
			typeStr = "";
		}
		var typeLen = getLength(typeStr);
		var labelLen = getLength(titleTmp);
		if (typeLen + labelLen > 18) {
			if (typeLen < 9) {
				labelLen = 18 - typeLen;
			} else if (labelLen < 9) {
				typeLen = 18 - labelLen;
			} else {
				typeLen = 8;
				labelLen = 8;
			}
		}
		//在mt处追加标签
		$("#conditionDd").append("<div style='width:"+labelWidth+"px;'><strong>"+strParse(typeStr, typeLen)+"<font color='#417EB7' title='" + titleTmp + "'>"+strParse(titleTmp, labelLen)+"</font></strong><b class='closeIcon' title='"+titleTmp+"' type='"+typeTmp+"' typeId=" + typeId + "  labelId=" + labelId + " style='background:"+iconCss+";'></b></div>");
		//为‘取消’按钮哦添加方法
		$(".closeIcon").bind("click",function(){
			if($(this).parent().is("#conditionDd div")){
				var title = label.attr('title');
				var type = label.attr('type');
				$("a[title='"+title+"'][type='"+type+"']").css("text-decoration","none").attr("select", false);
				$(this).parent().remove();
			}
		});
	}
	
	function toggleExt(i) {
		$("#advance").css("display", "block");
		$("#select dl:gt(" + i + ")").css("display", "none");
		$("#extend").toggle(function() {
			$("#extend strong").text("收起");
			$("#select dl:gt(" + i + ")").css("display", "block");
		}, function() {
			$("#extend strong").text("展开");
			$("#select dl:gt(" + i + ")").css("display", "none");
		});
	}
</script>
</head>
<body>
<div id="template.center">
	<div class="m" id="select">
		<div class="mt">
			<dl class="conditionDiv">
				<dt>已选标签：</dt>
				<dd id="conditionDd">
					<a id="all-revocation" href="#">全部撤消</a>
				</dd>
			</dl>
		</div>
		<div id="labelContent"></div>
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