<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<!-- 首页基础样式 -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/mainpage/mainpage.css" />
<!-- 布局相关样式，由构建布局时动态加入指定样式表 -->
<link rel="stylesheet" type="text/css"
	href="" id="layout_cssLink" />
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<script src="${ctx}/js/mainpage/layout.js"></script>
<script type="text/javascript">

	//默认标签头图标
	var defaultLabelIcon = "/images/classics/icons/application_view_icons.png";
	
	//是否是预览模式
	var isPreView = "${isPreView}";

	//定制id
	var designId = "${designId}";
	//布局id
	var layoutId = "${layoutId}";
	//布局对应样式表路径
	var cssPath = "${cssPath}";
	//具体布局容器div类名(用于选择器定位)
	var designSubDiv = "design_subdiv";
	
	$(function() {
		var contentHeight = $(document).height() - 10;
		$("#all").height(contentHeight);
		//初始化布局
		if(isPreView == "1"){
			//若是预览模式
			layoutId = window.parent.currents.layoutId;
			cssPath = window.parent.layoutMap[layoutId];
		}
		Layout.generateLayout("#all",layoutId,("${ctx}"+cssPath));
		//初始化各模块
		initModule();
	});
	
	//模块初始化
	function initModule(){
		if(isPreView == "1"){
			var rels = window.parent.currents.rels;
			if(rels != null){
				for(var i = 0 , l = rels.length ; i < l ; i++){
					var moduleTmp = window.parent.moduleMap[rels[i].moduleId];
					if(moduleTmp == null){
						continue;
					}
					var detailTmp = {};
					detailTmp.posNo = rels[i].posNo;
					detailTmp.isDisplayLabel = rels[i].isDisplayLabel;
					detailTmp.labelPath = moduleTmp.params.labelPath;
					detailTmp.moduleName = moduleTmp.params.moduleName;
					detailTmp.modulePath = moduleTmp.params.modulePath;
					createModuleDom(detailTmp);
				}
			}
			
		}else{
			$.ajax({
				cache : false,
				async : true,
				url : "${ctx}/bione/mainpage/getDetailInfoById.json",
				dataType : 'json',
				type : "post",
				data : {
					designId:designId
				},
				success : function(result){
					if(result && result.details
							&& result.details.length){
						for(var i = 0 , l = result.details.length ; i < l ; i++){
							var detailTmp = result.details[i];
							createModuleDom(detailTmp);
						}
					}
				},
				error:function(){
					BIONE.tip("初始化失败，请联系系统管理员");
				}
			});
			
		}
	}
	
	//渲染具体某一个布局模块
	function createModuleDom(detail){
		if(detail == null
				|| typeof detail != "object"){
			return ;
		}
		//获取指定渲染的target
		var posNo = detail.posNo;
		if(posNo == null || posNo == ""){
			return ;
		}
		var showLabel = detail.isDisplayLabel;
		var labelPath = detail.labelPath;
		var moduleName = detail.moduleName;
		var modulePath = detail.modulePath;
		
		var subDivTmp = $("."+designSubDiv+"[index='"+posNo+"']");
		var contentHeight = subDivTmp.height();
		if(subDivTmp.length > 0){
			contentHeight = subDivTmp.height();
			//若存在该位置的容器
			if(showLabel != "0"){
				//显示标题
				var labelHtml = 
				'<div class="subdiv_layout_header" >'+
					'<span class="subdiv_titleicon" ></span>'+
					'<span class="subdiv_titletext" >'+moduleName+'</span>'+
				'</div>';
				subDivTmp.append(labelHtml);
				//添加标题图标
				if(labelPath == null || labelPath == ""){
					labelPath = defaultLabelIcon;
					subDivTmp.children(".subdiv_layout_header").children(".subdiv_titleicon").css("background","url(${ctx}"+labelPath+")");
				}
				contentHeight = contentHeight - subDivTmp.children(".subdiv_layout_header").height() - 1;
			}
			//追加主体dom
			var contentHtml = 
			'<div class="subdiv_layout_content" >'+
				'<iframe frameborder="0" src="" style="width:99.9%;"></iframe>'+
			'</div>';
			subDivTmp.append(contentHtml);
			var subContent = subDivTmp.children(".subdiv_layout_content");
			subContent.height(contentHeight);
			subContent.children("iframe").height(subContent.height());
			subContent.children("iframe").attr("src","${ctx}"+modulePath);
		}
	}

</script>
</head>
<body>
	<div id="all">
		
	</div>
</body>
</html>