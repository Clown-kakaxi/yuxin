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
	$(function(){
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
		$(".label_a , a").bind("click",function(){
			$(this).css("text-decoration","underline");
			var titleTmp = $(this).attr("title");
			var typeTmp = $(this).attr("type");
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
			//在mt处追加标签
			$("#conditionDd").append("<div style='width:"+labelWidth+"px;'><strong>"+typeStr+"<font color='red'>"+titleTmp+"</font></strong><b class='closeIcon' title='"+titleTmp+"' type='"+typeTmp+"' style='background:"+iconCss+";'></b></div>");
			$(".closeIcon").bind("click",function(){
				if($(this).parent().is("#conditionDd div")){
					var title = $(this).attr('title');
					var type = $(this).attr('type');
					$("a[title='"+title+"'][type='"+type+"']").css("text-decoration","none");
					$(this).parent().remove();	
				}
			});
		});
		//为‘全部取消’附上点击方法
		$("#all-revocation").bind("click",function(){
			$("#conditionDd div").remove();
			$(".label_a,a").css("text-decoration","none");
		});
		//为‘取消’按钮哦添加方法
		$(".closeIcon").bind("click",function(){
			if($(this).parent().is("#conditionDd div")){
				var title = $(this).attr('title');
				var type = $(this).attr('type');
				$("a[title='"+title+"'][type='"+type+"']").css("text-decoration","none");
				$(this).parent().remove();	
			}
		});
	});
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
		<dl id="select-brand" 
			style="margin-top: 5px;">
			<dt>品牌：</dt>
			<dd>
				<div class="content">
					<div>
						<a class="label_a" href="#" type="品牌" title="三星（SAMSUNG）">三星（SAMSUNG）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="诺基亚（NOKIA）">诺基亚（NOKIA）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="苹果（Apple）">苹果（Apple）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="华为（HUAWEI）">华为（HUAWEI）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="HTC">HTC</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="魅族（Meizu）">魅族（Meizu）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="OPPO">OPPO</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="索尼（sony）">索尼（sony）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="中兴（ZTE）">中兴（ZTE）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="小米（MI）">小米（MI）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="青橙">青橙</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="七喜（HEDY）">七喜（HEDY）</a>
					</div>
					<div>
						<a class="label_a" href="#" type="品牌" title="天语（K-Touch）">天语（K-Touch）</a>
					</div>
				</div>
			</dd>
		</dl>
		<dl class="subbrand">
			<dt>大家说：</dt>
			<dd>
				<div>
					<a class="label_a" href="#" type="大家说" title="屏幕大 ">屏幕大 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="反应快 ">反应快 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="外观漂亮 ">外观漂亮 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="系统流畅 ">系统流畅 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="字体大 ">字体大 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="功能齐全 ">功能齐全 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="铃声大">铃声大 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="通话质量好 ">通话质量好 </a>
				</div>
				<div>
					<a class="label_a" href="#" type="大家说" title="价格便宜 ">价格便宜 </a>
				</div>
			</dd>
		</dl>
		<div class="clr"></div>
	</div>
	<div id="advance" style="height: 21px; text-align: center; line-height: 20px;">
  		<div style="height: 20px; width:100px; MARGIN-RIGHT: auto; MARGIN-LEFT: auto; border-left: solid 1px #CCC; border-right: solid 1px #CCC;border-bottom: solid 1px #CCC;">
  			<a>展开</a>
  		</div>
	</div>
</div>
</body>
</html>