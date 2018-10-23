/**
 * 营销管理-->营销任务管理-->营销任务下达：任务下达功能JS，wzy，2013-06-20
 */
// ===================================下达任务，支持批量下达==================================
function transTask(listPanel_in, store_in) {
	var checkedNodes = listPanel_in.getSelectionModel().selections.items;
	var selectLength = listPanel_in.getSelectionModel().getSelections().length;
	// 选中的任务ID对象
	var json = {
		'id' : []
	};
	// 1、判断是否选择了要下达的任务
	if (selectLength < 1) {
		Ext.Msg.alert('提示', '请选择需要下达的记录！');
		return false;
	}
	// 2、判断选中的任务是否全是“暂存”状态，判断当前用户是否是任务创建人
	for (var i = 0; i < checkedNodes.length; i++) {
		// 2.1、判断状态
		if (checkedNodes[i].data.taskStat != "暂存") {
			Ext.Msg.alert('提示', '只能下达【暂存】状态的营销任务！');
			return false;
		}
		// 2.2、判断创建人是否是当前用户
		if (checkedNodes[i].data.createUser != __userId) {
			Ext.Msg.alert('提示', '您选择的任务中，有些不是您创建的，不能下达！');
			return false;
		}
		json.id.push(checkedNodes[i].data.taskId);// 增加选中的任务ID
	}
	Ext.MessageBox.confirm('提示', '您确定要下达选中的任务吗？', function(buttonId) {
		if (buttonId.toLowerCase() == "no") {
			return false;
		}
		Ext.Ajax.request({
					url : basepath
							+ '/marketTaskAssignAction!taskAssignTransmit.json',
					method : 'POST',
					params : {
						cbid : Ext.encode(json)
					},
					waitMsg : '正在保存数据,请等待...',
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						store_in.reload();
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {

							Ext.Msg.alert('提示', '操作失败，失败原因：'
											+ response.responseText);
							store_in.reload();
						}
					}
				});
	});
}