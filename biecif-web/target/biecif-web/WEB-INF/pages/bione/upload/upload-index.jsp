<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_load.jsp"%>
<%@ include file="/common/ligerUI_load.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/classics/plupload/css/jquery.plupload.queue.css" />
<style type="text/css">
.l-dialog-win .l-dialog-content {
	overflow: hidden;
}
</style>
<script type="text/javascript" src="${ctx}/js/plupload/plupload.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/js/plupload/i18n/cn.js"></script>
<script type="text/javascript">
	$(function() {
		$("#uploader").pluploadQueue({
			// General settings
			runtimes : 'gears,flash,silverlight,browserplus,html5,html4',
			url : '${ctx}/bione/upload/upload!startUpload.xhtml',
			max_file_size : '100MB',
			unique_names : true,
			chunk_size : '10MB',
			// Specify what files to browse for
			filters : [ {
				title : "xls, xlsx文档",
				extensions : "xls,xlsx"
			} ],

			// Flash settings
			flash_swf_url : '${ctx}/js/plupload/plupload.flash.swf',
			// Silverlight settings
			silverlight_xap_url : '${ctx}/js/plupload/plupload.silverlight.xap'
		});
		$('form')
				.submit(
						function(e) {
							var uploader = $('#uploader').pluploadQueue();
							if (uploader.files.length > 0) {
								// When all files are uploaded submit form
								uploader
										.bind(
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
	});
</script>

</head>

<body style="padding: 0px">
	<div style="width: 550px; margin: 0px auto">
		<form id="formId"  method="post">
			<div id="uploader">
			</div>
		</form>
	</div>
</body>
</html>