function graphTrace(options) {
    var _defaults = {
        srcEle: this,
        pid: $(this).attr('pid')
    };
    var opts = $.extend(true, _defaults, options);
    
    // 获取图片资源
    var imageUrl = ctx + "/dataquality/problemprocess/loadProcessImage?pid=" + opts.pid + "&type=image";
    $.getJSON(ctx + "/dataquality/problemprocess/traceProcess.json?pid=" + opts.pid, function(infos) {
    
        var positionHtml = $("<div></div>");
        
        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div class="activiyAttr" style="position: absolute; left: '
            		+ (v.x - 1) + 'px; top: ' + (v.y - 1) + 'px; width: ' + (v.width - 2) + 'px; height: ' + (v.height - 2) + 'px;"></div>');
            $positionDiv.css('background','url(about:blank)');
            if (v.currentActiviti) {
                $positionDiv.addClass('ui-corner-all-12').css({
                    border: '2px solid red'
                });
            }
            positionHtml.append($positionDiv);
            varsArray[varsArray.length] = v.vars;
        });
        
        // 若不存在, 则生成一个DIV
        if ($('#problemTraceDialog').length == 0) {
            $('<div id="problemTraceDialog" title="查看流程"><div><img src="' + imageUrl + '" style="position: absolute; left: 0px; top: 0px;" />'
            		+ '<div id="processImageBorder"></div></div>').appendTo('div#imgdiv');
            $("#processImageBorder").append(positionHtml.children());
        } else {
            $('#problemTraceDialog img').attr('src', imageUrl);
            $('#problemTraceDialog #processImageBorder').html(positionHtml.children());
        }

        // 逐个 div 渲染ligertip, 并添加 tip 信息
        $(".activiyAttr").each( function(i, v) {
        	$(this).ligerTip({
        		auto : true,
        		width : '200',
        		content : function () {
        		    var tipContent = "<table class='need-border'>";
                	var vars = varsArray[i];
                	$.each(vars, function(varKey, varValue) {
                        if (varValue) {
                            tipContent += "<tr><td class='label' width='75' valign='top'>" + varKey + " : &nbsp;</td><td>" + varValue + "<td/></tr>";
                        }
                    });
                    tipContent += "</table>";
                    return tipContent;
                 }
        	});
        });
    });
}
