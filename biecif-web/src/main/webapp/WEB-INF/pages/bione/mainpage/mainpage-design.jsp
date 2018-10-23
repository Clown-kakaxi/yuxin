<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template7.jsp">
<head>
<script src="${ctx}/js/mainpage/layout.js"></script>
<!-- jquery ui 拖拽功能扩展 -->
<script src="${ctx}/js/jqueryUI/jquery.ui.core.js"></script>
<script src="${ctx}/js/jqueryUI/jquery.ui.widget.js"></script>
<script src="${ctx}/js/jqueryUI/jquery.ui.mouse.js"></script>
<script src="${ctx}/js/jqueryUI/jquery.ui.draggable.js"></script>
<script src="${ctx}/js/jqueryUI/jquery.ui.droppable.js"></script>
<script type="text/javascript">
	//定制id(当用户已存在定制布局时，该值不为空)
	var designId;
	
	//默认标签头图标
	var labelIcon = "/images/classics/icons/application_view_icons.png";
	//提示用图标
	var tipIcon = "${ctx}/images/classics/icons/tip.png";
	//通用布局缩略图
	var defaultLayoutPic = "/images/classics/mainpage/layout_1_1_3.gif";
	//布局信息html
	var layouts = "";
	//布局信息map:布局id -> cssurl
	var layoutMap = [];
	//具体布局容器div类名(用于选择器定位)
	var designSubDiv = "design_subdiv";

	//模块树对象
	var moduleTree;
	//模块map:模块id -> 模块信息(名称，标签，路径等)
	var moduleMap = [];
	
	//记录初始位置与模块关系、布局id
	var defaults = {
		layoutId:null,
		rels:null
	};
	
	//当前位置与模块关系、布局id
	//@params layoutId : 布局ID
	//@params rels ：布局信息[{index,showLabel,moduleId},...]
	var currents = {
		layoutId:null,
		rels:null
	};
	
	$(function() {
		var mainHeight = $("#right").height();
		//移除template7中的tab部分div
		$("#tab").remove();
		//初始化左侧模块树
		initLeftTree();
		//初始化右侧布局
		//1、初始化提示部分
		var tipHtml = 
		'<div id="tipMainDiv"'+
			'style="width: 100%; margin: 0 auto; overflow: hidden; position: relative; border-bottom: 1px solid #D6D6D6;">'+
			'<div id="tipContainerDiv"'+
				'style="padding: 5px 2px; background: #fffee6; color: #8f5700;">'+
				'<div style="width:24px;float:left;height:16px;background:url('+tipIcon+') no-repeat" />'+
				'<div id="tipAreaDiv">'+
					'tips : 选择布局后，从【功能模块树】中拖拽相应模块到布局指定区域中;<br />'+
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
					'定制模块头部的【收起/展开】按钮，可以控制该模块是否需要展示标题部分(含图标和标题)。<br />'+
				'</div>'+
			'</div>'+
		'</div>';
		$("#right").append(tipHtml);
		//2、初始化布局选择部分
		var layoutHtml = 
		'<div id="layout" style="position: relative;top: 0;width: 100%;z-index: 0;overflow: hidden;margin-left: auto;margin-right: auto;border-bottom: 1px solid #D6D6D6;">'+
			'<div style="width:100%;height:20px;"></div>'+
			'<label style="font-weight:bold;width:7em;display:block;float:left;text-align:center;padding-top:5px;">布局选择：</label>'+
		'</div>';
		$("#right").append(layoutHtml);
		//3、初始化布局设计部分
		var designHtml = 
		'<div id="design" style="position: relative;top: 0;width: 100%;z-index: 0;overflow: hidden;margin-left: auto;margin-right: auto;border-bottom: 1px solid #D6D6D6;">'+
		'</div>';
		$("#right").append(designHtml);
		//4、底部按钮部分
		var bottomHtml = 
		'<div id="bottom" style="width:100%;margin:0 auto;overflow: hidden;position: relative;margin-top:5px;">'+
		'</div>';
		$("#right").append(bottomHtml);
		//初始化各模块高度
		$("#bottom").height("40");
		var tmpHeight = mainHeight - 40 - $("#tipMainDiv").height() - 3;
		$("#layout").height(80);
		$("#design").height(tmpHeight - 80);
		//渲染底部按钮
		BIONE.createButton({
		    text : '保 存',
		    width : '50px',
		    appendTo : '#bottom',
		    icon : 'save',
		    click : saveFuc
		});
		BIONE.createButton({
			text : '重置',
			width : '50px',
			appendTo : '#bottom',
			icon:'refresh',
			click : resetFuc
		});
		BIONE.createButton({
			text : '预览',
			width : '50px',
			appendTo : '#bottom',
			icon: 'search2',
			click : previewFuc
		});
		//获取平台布局信息，渲染 布局选择
		initLayoutInfo();
	});
	
	//利用当前布局设计同步currents缓存对象
	function sycCurrents(){
		var subDivs = $("."+designSubDiv);
		var rels4Save = [];
		if(subDivs != null && subDivs.length > 0){
			for(var i = 0 , j = subDivs.length ; i < j ; i++){
				var subTmp = $(subDivs[i]);
				var indexTmp = subTmp.attr("index");
				var moduleIdTmp = subTmp.attr("moduleId");
				if(indexTmp == null || indexTmp == ""
						|| moduleIdTmp == null
						|| moduleIdTmp == ""){
					continue;					
				}
				var showLabelTmp = subTmp.attr("showLabel");
				var relTmp = {
					posNo:indexTmp,
					isDisplayLabel:showLabelTmp,
					moduleId:moduleIdTmp
				};
				rels4Save.push(relTmp);
			}
			currents.rels = rels4Save;
		}
	}
	
	//保存方法
	function saveFuc(){
		//同步模块设计信息
		sycCurrents();
		$.ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/mainpage/saveLayout.json",
			dataType : 'json',
			type : "post",
			data : {
				designId:designId,
				layoutId:currents.layoutId,
				relsString:JSON2.stringify(currents.rels)
			},
			success : function(){
				//修改布局相关默认信息
				defaults = currents;
				BIONE.tip("保存成功");
			},
			error:function(){
				BIONE.tip("保存失败，请联系系统管理员");
			}
		});
	}
	
	//重置方法
	function resetFuc(){
		//重置布局信息
		if(defaults.layoutId == null 
				|| defaults.layoutId == ""){
			$(".layout_wrap").children("input").attr("checked",false);
			$("#design").empty();
		}else{
			$(".layout_wrap").children("input[value='"+defaults.layoutId+"']").attr("checked",true);
			initDesign(defaults.layoutId);
			//重置模块设计信息
			var relsTmp = defaults.rels;
			if(relsTmp != null && relsTmp.length){
				for(var i = 0 , j = relsTmp.length ; i < j ; i++){
					var indexTmp = relsTmp[i].posNo;
					var showLabelTmp = relsTmp[i].isDisplayLabel;
					var moduleIdTmp = relsTmp[i].moduleId;
					var moduleTmp = moduleMap[moduleIdTmp];
					if(moduleTmp == null){
						continue;
					}
					//找出指定渲染对象目标
					var target = $("."+designSubDiv+"[index='"+indexTmp+"']");
					if(target != null && target.length > 0){
						target = target[0];
					}
					$(target).attr("showLabel",showLabelTmp);
					setLayoutModule(target,moduleIdTmp);
					setDragDrop(target,moduleIdTmp);
				}
			}else{
				$("."+designSubDiv).empty();
				$("."+designSubDiv).attr("showLabel","1");
				$("."+designSubDiv).attr("moduleId","");
			}
		}
		currents.layoutId = defaults.layoutId;
		currents.rels = defaults.rels;
	}
	
	//预览
	function previewFuc(){
		if(currents.layoutId == null
				|| currents.layoutId == ""){
			//若当前没有选择布局，提示
			BIONE.tip("请选择布局");
			return;
		}
		sycCurrents();
		BIONE.commonOpenDialog("预览", "mainPagePreview", 900, 450, 
				"${ctx}/bione/mainpage/mainpage?isPreView=1");
	}
	
	//获取当前用户在当前逻辑系统中的布局信息
	function initUserDesign(){
		$.ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/mainpage/initUserDesign.json",
			dataType : 'json',
			type : "post",
			success : function(result){
				if(result != null){
					if(result.designId && result.designId != null
							&& result.designId != ""){						
						designId = result.designId;
					}
					defaults = result;
					resetFuc();
				}	
			},
			error:function(){
				BIONE.tip("获取用户当前布局信息失败");
			}
		});
	}
	
	//初始化布局内容
	function initDesign(layoutId){
		if(layoutId == null || layoutId == ""){
			return ;
		}
		if(layoutId == currents.layoutId){
			//若当前点击布局是已选布局，不做处理
			return ;
		}
		currents.layoutId = layoutId;
		Layout.generateLayout("#design",layoutId);
		$("."+designSubDiv).droppable({
			accept:"."+designSubDiv,
			addClasses: false,
			drop:function(event,ui){
				var source = ui.draggable;
				var target = $(this);
				//清空目标当前选中的模块
				source.empty();
				//维护showLabel属性
				var oldShowLabel = source.attr("showLabel");
				source.attr("showLabel","1");
				target.attr("showLabel",oldShowLabel);
				//添加拖拽模块
				setLayoutModule(target,source.attr("moduleId"));		
				setDragDrop(target,source.attr("moduleId"));
				source.attr("moduleId","");
			}
		});
	}
	
	//初始化平台布局信息
	function initLayoutInfo(){
		//获取平台布局信息
		$.ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/mainpage/getLayouts.json",
			dataType : 'json',
			type : "post",
			success : function(result){
				if(result 
					&& result.length){
					for(var i = 0 , l = result.length ; i < l ; i++){
						var lTmp = result[i];
						//将布局相关信息放入缓存
						layoutMap[lTmp.layoutId] = lTmp.cssPath;
						var picPath = (lTmp.picPath==null||lTmp.picPath=="")?defaultLayoutPic:lTmp.picPath;
						var htmlTmp = 
						'<div class="layout_wrap" style="width:7em;float:left;padding-bottom:5px;cursor:pointer;display:block;">'+
							'<input type="radio" name="layoutSel" value="'+lTmp.layoutId+'" />'+
							'<img style="padding-left:2px;padding-right:8px;vertical-align:middle;display:inline-block;" src="${ctx}'+picPath+'" />'+
						'</div>';
						layouts += htmlTmp;
					}
					//追加html 
					$("#layout").append(layouts);
					//追加布局外围div点击事件
					$(".layout_wrap").bind("click",function(){
						$(this).children("input").attr("checked",true);
						//渲染定制展现
						initDesign($(this).children("input").val());
					});
					initUserDesign();
				}
			},
			error : function(){
				BIONE.tip('初始化布局信息失败，请联系管理员');
			}
		});
	}
	
	//将模块内容渲染到布局指定位置
	function setLayoutModule(target,moduleId,isInit){
		if(typeof target == "undefined"
				|| target == null
				|| !moduleId){
			return ;
		}
		var module = moduleMap[moduleId];
		if(module == null || module == ""){
			return ;
		}
		var labelName = module.text;
		var labelPath = module.params.labelPath;
		var picPath = module.params.picPath;
		var minMaxIcon = "url(${ctx}/images/classics/mainpage/dialog-winbtns.gif) no-repeat";
		var minMaxPos = "-51px 0px";
		var closeIcon = "url(${ctx}/images/classics/mainpage/dialog.gif) no-repeat";
		var closePos = "-24px -16px";
		var headerHtml = 
		'<div class="subdiv_layout_buttondiv" style="width：100%;position: relative;padding-left: 10px;color: #183152;font-weight: bold;height: 20px;line-height: 19px;overflow: hidden;background: #E5EFFE ;border: 1px solid #DDDDDD;border-bottom:none;">'+
			'<div class="subdiv_layout_button_wrap" style="float:right;margin-top:1px;margin-right:1px;height:16px;">'+
				'<div class="subdiv_layout_button_close" style="cursor: pointer;overflow: hidden;width: 17px;height: 16px;float: right;margin-right: 4px;background:'+closeIcon+';background-position:'+closePos+';" />'+
				'<div class="subdiv_layout_button_minmax" style="cursor: pointer;overflow: hidden;width: 17px;height: 16px;float: right;margin-right: 4px;background:'+minMaxIcon+';background-position:'+minMaxPos+';" />'+
			'</div>'+
		'</div>'+
		'<div class="subdiv_layout_header" style="width：100%;position: relative;padding-left: 10px;color: #183152;font-weight: bold;height: 20px;line-height: 19px;overflow: hidden;background: #E5EFFE ;border: 1px solid #DDDDDD;border-top:none;">'+
			'<span class="subdiv_titleicon" style="position:absolute;width:16px;height:16px;left:5px;top:2px;"></span>'+
			'<span class="subdiv_titletext" style="padding-left: 14px;">'+labelName+'</span>'+
		'</div>'+
		'<div class="subdiv_layout_content" style="width: 100%;text-align:center;" />';
		//先移除当前target之下已存在的html,再追加
		if(typeof isInit != "undefined"){
			//若是初始化位置(从左侧树拖拽过来的)，维护showLabel属性
			$(target).attr("showLabel","1");
		}
		$(target).empty();
		$(target).append(headerHtml);
		$(target).attr("moduleId",module.params.realId);
		//初始化标签头部图标
		if(typeof labelPath == "undefined"
				|| labelPath == null
				|| labelPath == ""){
			//labelPath不合法，使用默认图标
			labelPath = labelIcon;
		}
		$(target).children(".subdiv_layout_header").children(".subdiv_titleicon").css("background","url(${ctx}"+labelPath+")");
		//按钮绑定事件
		$(target).children().children().children(".subdiv_layout_button_close").bind("click",function(){
			//关闭按钮，会将target之下所有内容清空
			var targetTmp = $(this).parent().parent().parent();
			targetTmp.attr("showLabel","1");
			targetTmp.attr("moduleId","");
			targetTmp.empty();
			//清楚该元素的拖拽支持
			targetTmp.draggable("destroy");
		});
		var contentTmp = $(target).children(".subdiv_layout_content"); 
		if($(target).attr("showLabel") == 0){
			//若初始化是不显示标签头
			//象征性隐藏头
			$(target).children(".subdiv_layout_header").hide();
			//改变按钮图标样式
			$(target).children(".subdiv_layout_buttondiv").children(".subdiv_layout_button_wrap").children(".subdiv_layout_button_minmax").css("background-position","-68px 0px");
			//改变content部分高度
			contentTmp.height($(target).height() - 22);
			contentTmp.css("line-height",($(target).height() - 22)+"px");
		}else{
			//初始化模块预览
			contentTmp.height($(target).height() - 42);
			//暂时用文字描述的形式代替模块缩略图展示
			contentTmp.css("line-height",($(target).height() - 42)+"px");
		}
		contentTmp.html("<h5>"+labelName+"</h5>");
		$(target).children().children().children(".subdiv_layout_button_minmax").bind("click",function(){
			var targetTmp = $(this).parent().parent().parent(); 
			var showLabel = targetTmp.attr("showLabel");
			if(typeof showLabel == "undefined"
					|| showLabel == null
					|| showLabel == ""
					|| showLabel == "1"){
				//若点击前是显示标签头，切换为隐藏标签头模式
				targetTmp.attr("showLabel","0");	
				//象征性隐藏头
				$(this).parent().parent().next(".subdiv_layout_header").hide("fast");
				//改变按钮图标样式
				$(this).css("background-position","-68px 0px");
				//改变content部分高度
				targetTmp.children(".subdiv_layout_content").height(targetTmp.height() - 22);
				targetTmp.children(".subdiv_layout_content").css("line-height",(targetTmp.height() - 22)+"px");
				return ;
			}else{
				//其他，切换为显示标签头模式
				targetTmp.attr("showLabel","1");	
				//显示头
				$(this).parent().parent().next(".subdiv_layout_header").show("fast");
				//改变按钮图标样式
				$(this).css("background-position","-51px 0px");
				//改变content部分高度
				targetTmp.children(".subdiv_layout_content").height(targetTmp.height() - 42);
				targetTmp.children(".subdiv_layout_content").css("line-height",(targetTmp.height() - 42)+"px");
				return ;
			}
		});
	}
	
	//添加拖拽控制
	function setDragDrop(target,moduleId){
		if(typeof target == "undefined"
			|| target == null
			|| !moduleId){
			return ;
		}
		var module = moduleMap[moduleId];
		if(module == null || module == ""){
			return ;
		}
		var labelName = module.text;
		$(target).draggable({
			addClasses: false,
			containment: "#design",
			revert: "invalid",
			cursor: "move",
			cursorAt :{left:50,top:10},
			helper: function(){
				var proxyLabel = "${ctx}"+labelIcon;
				var proxyHtml = 
				'<div style="width：150;position: relative;padding-left: 10px;color: #183152;font-weight: bold;height: 20px;line-height: 19px;overflow: hidden;background: #E5EFFE ;border: 1px solid #DDDDDD;border-top:none;">'+
					'<span style="position:absolute;width:16px;height:16px;left:5px;top:2px;background:url('+proxyLabel+')"></span>'+
					'<span style="padding-left: 14px;">'+labelName+'</span>'+
				'</div>';
				return proxyHtml;
			},
			stop: function(){
				if($(target).attr("moduleId") == null
						|| $(target).attr("moduleId") == ""){
					//消除该dom的拖拽事件支持
					$(target).draggable("destroy");
				}
			}
		});
	}
	
	//初始化平台模块信息
	function initLeftTree(){
		//获取平台模块信息
		$.ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/mainpage/getModules.json",
			dataType : 'json',
			type : "post",
			success : function(result){
				if(result 
					&& result.length){
					//将result放入moduleMap中
					for(var i = 0 , l = result.length ; i < l ; i++){
						moduleMap[result[i].params.realId] = result[i];
					}
					moduleTree = $.fn.zTree.init(
						$("#tree"),
						{
							data : {
								keep : {
									parent : true
								},
								key : {
									name : "text"
								},
								simpleData : {
									enable : true,
									idKey : "id",
									pIdKey : "upId",
									rootPId : null
								}
							},
							edit:{
								drag: {
									isCopy: true,
									prev : false,
									next : false,
									inner : false,
									isMove: true
								},
								enable: true,
								showRemoveBtn: false,
								showRenameBtn: false
							},
							callback:{
								beforeDrag:function(treeId,treeNodes){
									if(currents.layoutId == ""){
										BIONE.tip("请选择布局样式");
										return false;
									}
									return true;
								},
								onDrop:function(event,treeId,treeNodes,targetNode,moveType){
									var target = event.target;
									if($(target).hasClass(designSubDiv)){
										//渲染功能模块
										setLayoutModule(target,treeNodes[0].params.realId,true);
										//添加拖拽控制
										setDragDrop(target,treeNodes[0].params.realId);
									}else{
										var designParents = $(target).parents("."+designSubDiv);
										if(designParents.length > 0){
											$(designParents[0]).empty();
											//渲染功能模块
											setLayoutModule(designParents[0],treeNodes[0].params.realId,true);
											//添加拖拽控制
											setDragDrop(designParents[0],treeNodes[0].params.realId);
										}
									}						
									return false;
								}
							},
							view : {
								selectedMulti : false,
								showLine : false
							}
						}, result
					);
				}
			},
			error : function(){
				BIONE.tip('初始化功能模块失败，请联系管理员');
			}
		});
	}

</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12" id="left_up_span">功能模块</span>
	</div>
	<div id="template.left.down">
		<div id="btn"></div>
	</div>
</body>
</html>