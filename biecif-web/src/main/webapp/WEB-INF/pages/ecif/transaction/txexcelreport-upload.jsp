<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/classics/plupload/css/jquery.plupload.queue.css" />
<style type="text/css">
.l-dialog-win .l-dialog-content {
	overflow: hidden;
}
</style>
<%-- <script type="text/javascript" src="${ctx}/js/plupload5/plupload.min.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/js/plupload5/jquery.plupload.queue/jquery.plupload.queue.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/js/plupload5/i18n/cn.js"></script> --%>
 

<!-- Third party script for BrowserPlus runtime (Google Gears included in Gears runtime now) -->
<!-- <script type="text/javascript" src="http://bp.yahooapis.com/2.4.21/browserplus-min.js"></script> -->
<!-- Load plupload and all it's runtimes and finally the jQuery queue widget -->
<script type="text/javascript" src="${ctx}/js/plupload/plupload.full.js"></script>
<script type="text/javascript" src="${ctx}/js/plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/js/plupload/i18n/cn.js"></script>
<script type="text/javascript">
	$(function() {
		$("#uploader").pluploadQueue({
			// General settings
			runtimes : 'gears,flash,silverlight,browserplus,html5,html4',
			url : '${ctx}/ecif/transaction/excel/startUpload',
			max_file_size : '100MB',
			unique_names : true,
			chunk_size : '10MB',
			// Specify what files to browse for
			filters : [ {
				title: "xml文档",
				extensions: "xml"
			}, {
				title: "Zip文档",
				extensions: "zip"
			}],
			// Flash settings
			flash_swf_url : '${ctx}/js/plupload/plupload.flash.swf',
			// Silverlight settings
			silverlight_xap_url : '${ctx}/js/plupload/plupload.silverlight.xap'
		});
		$('form').submit(function(e) {
			var uploader = $('#uploader').pluploadQueue();
			if (uploader.files.length > 0) {
				// When all files are uploaded submit form
				uploader.bind(
					'StateChanged',
					function() {
						if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
							$('form')[0].submit();
						}
					});

				uploader.start();
			} else {
				BIONE.tip("请添加要上传的数据文件");
			}
			return false;
		});
		var uploader = $('#uploader').pluploadQueue();
		uploader.bind('BeforeUpload', function(up, file) {
			this.settings.url = "${ctx}/ecif/transaction/excel/startUpload?saveType=" + $('input[name="upTyp"]:checked').val();
		});
		
 		uploader.bind('FileUploaded', function(result, file, resp) {
 			closeDialogAndReloadParent("uploadobj", "maingrid","导入成功");
 		});		
//  		var uploader = $('#uploader').pluploadQueue();
// 		uploader.bind('FileUploaded', function(result, file, resp) {
// 			//parent.$.ligerui.get("maingrid").loadData();
// 			if (resp) {
// 				var temp = resp.response;
// 				if(temp.substring(0,3) == "err"){
// 					alert("错误信息下载");
// 					doDownload(temp.substring(3));
// 				}else{
// 					parent.BIONE.tip(resp.response);
// 				}
// 			}
// 			$('#uploader').html("");
// 			uploader.trigger("Destroy");
// 			uploader.unbindAll();
// 			BIONE.closeDialogAndReloadParent("upload", "maingrid");
// 		});
	});
	
	closeDialogAndReloadParent = function(dialogName, gridName, message) {
		var main = parent || window;
		
		var dialog = main.jQuery.ligerui.get(dialogName);
		dialog.close();
		alert(message);
		main.jQuery.ligerui.get(gridName).loadData();
		
		//prompt('',main.jQuery.ligerui.get(gridName));
		//if (message) {
		//	main.BIONE.tip(message);
		//}
	};	
	
// 	function doDownload(file) {
// 		if(file==null||file==""){
// 			BIONE.tip("下载失败。");
// 			return;
// 		}
// 		var form = $('<form/>').attr({
// 			target: '',
// 			method: 'post',
// 			action: '${ctx}/ecif/customer/custmergerecordapp/export.xls?'+new Date().getTime()
// 		}).css('display', 'none');
// 		var input = $('<input/>').attr({
// 			type: 'hidden',
// 			name: 'file',
// 			value: file
// 		});
// 		$('body').append(form);
// 		form.append(input);
// 		form.submit();
// 		form.remove();
// 	}
</script>

</head>

<body style="padding: 0px">
	<div style="width: 550px; margin: 0px auto">
		<form id="formId" method="post">
			<div id="uploader"></div>
		</form>
	</div>
	<div style="background-color: #DFDFDF; padding-left: 8px;">
		如果有相同交易：
		<label><input type="radio" name="upTyp" value="1" checked="checked" />覆盖</label>
		<label><input type="radio" name="upTyp" value="2" />跳过</label>
<!-- 		<label><input type="radio" name="upTyp" value="0"/>取消全部</label> -->
	</div>
</body>
</html>