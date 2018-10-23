<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<style>
	form {margin: 0;}
	textarea {display: block;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/kindeditor/lang/zh_CN.js"></script>
</head>
<body>
<form>
	<textarea name="content" style="width:100%;height:360px;visibility:hidden;"></textarea>
	<p align="center">
		  <!--<input type="button" name="saveText" value="保存文档" />-->
		<input type="hidden" id="pid" value="" />
	</p>
	
</form>
<script>
	var requestStr = document.location.search;
	var nodeId = requestStr.split('=')[1].split('&')[0];//节点ID
	//var nodeName = requestStr.split('=')[2];//节点名
	//document.getElementById('pid').innerHTML = nodeName+'--';
	var editor;
	var docid='';
	function queryData()
	{
		Ext.Ajax.request({
			url:basepath+'/ocrmSysRicheditInfo!indexPage.json',
			method:'GET',
			params:{
				relId:nodeId
			},
			success:function(r){
				if(Ext.decode(r.responseText).json.data.length>0){
					docid = Ext.decode(r.responseText).json.data[0].id;
					alert("1");
					var context = Ext.decode(r.responseText).json.data[0].content;
					editor.html(context);
					//editor.insertHtml(context);
				}
			},failure:function(){
			}
		});
	}
	function saveData(nodeId)
	{
		Ext.Ajax.request({
			url:basepath+'/ocrmSysRicheditInfo.json',
			method:'post',
			params:{
				wordId : docid,
				relId:nodeId,
				content:editor.html()
			},
			success:function(r){
				//alert('保存成功!');
				queryData(nodeId);
			}
		});
	}
	
	function queryData(nodeId)
	{
		if(nodeId=='0'){
			editor.html("");
			
		}else{
		Ext.Ajax.request({
			url:basepath+'/ocrmSysRicheditInfo!indexPage.json',
			method:'GET',
			params:{
				relId:nodeId
			},
			success:function(r){
				if(Ext.decode(r.responseText).json.data.length>0){
					docid = Ext.decode(r.responseText).json.data[0].id;
					var context = Ext.decode(r.responseText).json.data[0].content;
					editor.html(context);
					//editor.insertHtml(context);
				}
			},failure:function(){
				debugger;
			}
		});
		}
	}
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			allowFileManager : true,
			showRemote:false,
			uploadJson : basepath + '/contents/resource/kindeditor/upload/upload_json.jsp',
			fileManagerJson : basepath + '/contents/resource/kindeditor/upload/file_manager_json.jsp'
		});
		
		K('input[name=saveText]').click(function(e) {
			
			Ext.Ajax.request({
				url:basepath+'/ocrmSysRicheditInfo.json',
				method:'post',
				params:{
					wordId : docid,
					relId:nodeId,
					content:editor.html()
				},
				success:function(r){
					alert('保存成功!');
					queryData();
				}
			});
		});
		queryData(nodeId);
	});
			
</script>
</body>
</html>