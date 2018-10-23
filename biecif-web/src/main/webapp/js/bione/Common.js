/*******************************************************/
/****             BIONE(基础应用平台)公用JS文件           ****/
/****         维护具有公用价值的JS代码，比如校验、Ajax调用等    ****/
/******************************************************/

/**
 * 根据URL请求内容，根据返回的内容更新页面指定DIV
 * 
 * url:请求的资源路径 divId：需要被更新的DIV的id showTip:操作成功后是否在右下角提示 默认false
 */
function commonAjaxCall(url, divId, showTip , type) {

    var result = true;
    if(typeof type == 'undefined'){
    	type = 'get';
    }

    $.ajax({
	url : url,
	async : false,// 同步
	dataType : 'html',// 返回内容格式应为html
	timeout : 3000,// 超时时间，毫秒
	type : type,
	cache : false,
	success : function(data, textStatus, jqXHR) {// 调用成功后的操作

	    $("#" + divId).html(data);

	},
	error : function(jqXHR, textStatus, errorThrown) {// 调用失败后的操作

	    result = false;
	    commonAlertMsg("系统运行中出现异常,请重试或联系管理员!");

	},
	beforeSend : function(jqXHR) {

	}

    });

    return result;
}

function loadPage(url, frameid, needAnimation) {
    var mainContent = $("#" + frameid);
    $.ajax({
	url : url,
	dataType : "html",
	async : false,// 同步
	type : 'get',
	cache : false,
	success : function(data, textStatus, jqXHR) {
	    function callback() {
		var manager = window.liger.managers;
		$.each(manager, function(n, object) {
		    object.destroy();
		});
		mainContent.empty();
		mainContent.html(data);
		mainContent.fadeIn("slow");
	    }
	    if (needAnimation) {
		mainContent.fadeOut("fast", callback);
	    } else {
		callback();
	    }

	},
	error : function(err) {
	    console.log(err);
	}
    });
}

/**
 * 提示业务异常信息
 * 
 * @message 错误信息
 */
function commonAlertMsg(message) {

    $.ligerDefaults.Dialog.width = 460;
    $.ligerDialog.error(message, "系统提示");
}

//document.oncontextmenu = function() {
//    return false;
//}
//
//var shiftKeyOrCtrlKey = false;
//// 禁止刷新、F11等按键功能
//window.document.onkeydown = function(e) {
//    e = !e ? window.event : e;
//    var key = window.event ? e.keyCode : e.which;
//
//    shiftKeyOrCtrlKey = false;
//
//    if (e.shiftKey || e.ctrlKey) {
//	shiftKeyOrCtrlKey = true;
//    }
//
//    if ((e.altKey) && (key == 37 || key == 39)) { // Alt+左右方向
//	return false;
//    }
//    if (key == 8) { // backspace
//	try {
//	    // 如果是输入框\密码框\文本区域\下拉框\日历,那么不使用backspace
//	    var type = (e.srcElement || e.target).type;
//	    var readonly = (e.srcElement || e.target).readonly;
//	    var ltype = (e.srcElement || e.target).ltype;
//	    if (readonly != true && readonly != "readonly") {
//		if (type == "password" || type == "textarea") {
//		    return true;
//		}
//		if (type == "text" && ltype && ltype != "date"
//			&& ltype != "select") {
//		    return true;
//		}
//		if (type == "text" && !ltype) {
//		    return true;
//		}
//	    }
//	} catch (e) {
//	}
//	key = 0;
//	if (window.event) {
//	    e.cancelBubble = true;
//	} else {
//	    e.stopPropagation();
//	}
//	return false;
//    }
//
//    if ((key == 116) || (e.ctrlKey && key == 82)) { // Ctrl + R
//	return false;
//    }
//
//    if ((e.ctrlKey) && (key == 78)) { // Ctrl+n
//	return false;
//    }
//
//    if ((e.shiftKey) && (key == 121)) { // shift+F10
//	return false;
//    }
//
//};
//
//// 禁止ctrl+鼠标滚轮进行页面缩放
//$(document).ready(function() {
//
//    var mousewheelevt = $.browser.msie ? "mousewheel" : "DOMMouseScroll";// FF
//    // doesn't
//    // recognize
//    // mousewheel
//    // as of FF3.x
//
//    if (document.attachEvent) { // if IE (and Opera depending on
//	// user setting)
//	document.attachEvent("on" + mousewheelevt, function(e) {
//	    e.cancelBubble = true;
//	    e.returnValue = false;
//	    e.keyCode = 0;
//	});
//    } else if (document.addEventListener) { // WC3 browsers
//	document.addEventListener(mousewheelevt, function(e) {
//
//	    e.preventDefault();
//	}, false);
//    }
//
//});
