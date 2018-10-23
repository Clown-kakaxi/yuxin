/************************************************************/
/****       提供首页定制功能中，大体布局类型的初始化函数    ****/
/************************************************************/
(function($) {

	// 全局系统对象
	window['Layout'] = {};
	
	//平台能支持的布局id集合
	//*此处布局id必须和数据库中的平台布局表中数据保持一致
	Layout.ids = ["1","2","3"];
	
	//动态添加css样式表
	Layout.appendCssLink = function(cssPath){
		if(cssPath != null){
			$("#layout_cssLink").attr("href",cssPath);
		}
	};
	
	//构造布局
	//@param target:目标dom，可以是dom的id或dom对象
	//@param layoutId:所选择的布局id
	//@param cssPath:当前布局对应的css路径
	Layout.generateLayout = function(target,layoutId,cssPath){
		if (typeof (target) == "string"){			
			target = $(target);
		}else if (typeof (target) == "object"){			
			target = $(target);
		}else{
			//其他情况，target不合法
			return ;
		}
		//判断layoutId是否合法
		if(typeof layoutId == "undefined"){
			return ;
		}
		layoutId = layoutId + "";
		if(jQuery.inArray(layoutId,Layout.ids) == -1){
			return ;
		}
		var cssSheet = false;
		if(typeof cssPath != "undefined"
			&& cssPath != null && cssPath != ""){
			cssSheet = true;
			//引用该css样式
			Layout.appendCssLink(cssPath);
		}
		//清除target下元素
		target.empty();
		//构造布局html
		var html = '';
		switch(layoutId){
		case "1":
			//3列，第1列一行，第2列两行，第3列三行
			if(cssSheet){
				//若存在样式表指定
				html = 
					'<div class="design_all">'+
						'<div class="design_left">'+
							'<div class="design_subdiv" index="1">'+
							'</div>'+
						'</div>'+
						'<div class="design_center">'+
							'<div class="design_subdiv" index="2">'+
							'</div>'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="4">'+
							'</div>'+
						'</div>'+
						'<div class="design_right">'+
							'<div class="design_subdiv" index="3">'+
							'</div>'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="5">'+
							'</div>'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="6">'+
							'</div>'+
						'</div>'+
					'</div>';
			}else{
				html = 
					'<div class="design_all" style="width: 100%;position: relative;">'+
						'<div class="design_left" style="float: left;width: 20%;margin-top:5px;margin-left:5px;">'+
							'<div class="design_subdiv" index="1" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;">'+
							'</div>'+
						'</div>'+
						'<div class="design_center" style="float: left;width: 56%;margin-left: 1%;margin-top:5px;">'+
							'<div class="design_subdiv" index="2" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;">'+
							'</div>'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="4" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;">'+
							'</div>'+
						'</div>'+
						'<div class="design_right" style="width: 20%;float: left;margin-top:5px;margin-left:1%;border:1px dotted #D6D6D6;">'+
							'<div class="design_subdiv" index="3" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;">'+
							'</div>'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="5" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;">'+
							'</div>'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="6" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;">'+
							'</div>'+
						'</div>'+
					'</div>';
			}
			break;
		case "2":
			//3列，第1列一行，第2列一行，第3列三行
			if(cssSheet){
				//若存在css样式指定
				html = 
					'<div class="design_all">'+
						'<div class="design_left">'+
							'<div class="design_subdiv" index="1" />'+
						'</div>'+
						'<div class="design_center">'+
							'<div class="design_subdiv" index="2" />'+
						'</div>'+
						'<div class="design_right">'+
							'<div class="design_subdiv" index="3" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="4" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="5" />'+
						'</div>'+
					'</div>';
			}else{
				html = 
					'<div class="design_all" style="width: 100%;position: relative;">'+
						'<div class="design_left" style="float: left;width: 20%;margin-top:5px;margin-left:5px;">'+
							'<div class="design_subdiv" index="1" style="width:100%;background-color: #FFFFFF;border:1px dotted #D6D6D6;" />'+
						'</div>'+
						'<div class="design_center" style="float: left;width: 56%;margin-left: 1%;margin-top:5px;">'+
							'<div class="design_subdiv" index="2" style="width:100%;background-color: #FFFFFF;border:1px dotted #D6D6D6;" />'+
						'</div>'+
						'<div class="design_right" style="width: 20%;float: left;margin-top:5px;margin-left:1%;">'+
							'<div class="design_subdiv" index="3" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="4" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="5" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
						'</div>'+
					'</div>';
			}
			break;
		case "3":
			//2列，第1列两行，第2列两行
			if(cssSheet){
				//若存在css样式指定
				html = 
					'<div class="design_all">'+
						'<div class="design_left">'+
							'<div class="design_subdiv" index="1" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="2" />'+
						'</div>'+
						'<div class="design_right">'+
							'<div class="design_subdiv" index="3" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="4" />'+
						'</div>'+
					'</div>';
			}else{
				html = 
					'<div class="design_all" style="width: 100%;position: relative;">'+
						'<div class="design_left" style="float: left;width: 78%;margin-top:5px;margin-left:5px;">'+
							'<div class="design_subdiv" index="1" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="2" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
						'</div>'+
						'<div class="design_right" style="width: 20%;float: left;margin-top:5px;margin-left:1%;">'+
							'<div class="design_subdiv" index="3" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
							'<div style="width:100%;height:2px;" />'+
							'<div class="design_subdiv" index="4" style="background-color: #FFFFFF;width:100%;border:1px dotted #D6D6D6;" />'+
						'</div>'+
					'</div>';
			}
			break;
		}
		//拼接html
		target.append(html);
		//初始化样式
		Layout.initLayoutHeight(target,layoutId);
		if(!cssSheet){
			//在指明样式表的情况下，不进行hover设置
			$(".design_subdiv").hover(
					function(){
						$(this).css("border-color","#0000CC");
					},
					function(){
						$(this).css("border-color","#D6D6D6");
					}
			);
		}
	};
	
	//初始化布局高度
	//@param target:目标dom，可以是dom的id或dom对象
	//@param layoutId:所选择的布局id
	Layout.initLayoutHeight = function(target,layoutId){
		if (typeof (target) == "string"){			
			target = $(target);
		}else if (typeof (target) == "object"){			
			target = $(target);
		}else{
			//其他情况，target不合法
			return ;
		}
		//判断layoutId是否合法
		if(typeof layoutId == "undefined"){
			return ;
		}
		layoutId = layoutId + "";
		if(jQuery.inArray(layoutId,Layout.ids) == -1){
			return ;
		}
		//初始化样式
		var targetHeight = target.height();
		switch(layoutId){
		case "1":
			//3列，第1列一行，第2列两行，第3列三行
			$(".design_all").height(targetHeight - 10);
			$(".design_left").height(targetHeight - 10);
			$(".design_center").height(targetHeight - 10);
			$(".design_right").height(targetHeight - 10);
			$($(".design_left").children(".design_subdiv")[0]).height((targetHeight - 10));
			$($(".design_center").children(".design_subdiv")[0]).height((targetHeight - 10 - 2)*0.6);
			$($(".design_center").children(".design_subdiv")[1]).height((targetHeight - 10 - 2)*0.4);
			$($(".design_right").children(".design_subdiv")[0]).height((targetHeight - 10 - 4)*0.33);
			$($(".design_right").children(".design_subdiv")[1]).height((targetHeight - 10 - 4)*0.33);
			$($(".design_right").children(".design_subdiv")[2]).height((targetHeight - 10 - 4)*0.33);
			
			break;
		case "2":
			//3列，第1列一行，第2列一行，第3列三行
			$(".design_all").height(targetHeight - 10);
			$(".design_left").height(targetHeight - 10);
			$(".design_center").height(targetHeight - 10);
			$(".design_right").height(targetHeight - 10);
			$($(".design_left").children(".design_subdiv")[0]).height((targetHeight - 10));
			$($(".design_center").children(".design_subdiv")[0]).height(targetHeight - 10);
			$($(".design_right").children(".design_subdiv")[0]).height((targetHeight - 10 - 4)*0.33);
			$($(".design_right").children(".design_subdiv")[1]).height((targetHeight - 10 - 4)*0.33);
			$($(".design_right").children(".design_subdiv")[2]).height((targetHeight - 10 - 4)*0.33);
			
			break;
		case "3":
			//2列，第1列两行，第2列两行
			$(".design_all").height(targetHeight - 10);
			$(".design_left").height(targetHeight - 10);
			$(".design_right").height(targetHeight - 10);
			$($(".design_left").children(".design_subdiv")[0]).height((targetHeight - 10 - 4)*0.76);
			$($(".design_left").children(".design_subdiv")[1]).height((targetHeight - 10 - 4)*0.23);
			$($(".design_right").children(".design_subdiv")[0]).height((targetHeight - 10 - 4)*0.76);
			$($(".design_right").children(".design_subdiv")[1]).height((targetHeight - 10 - 4)*0.23);
			break;
		}
	};
	
})(jQuery);