<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], user = [];
	var userId;
	$(init);

	function init() {
		url = "${ctx}/bione/admin/user/list.json";
		initGrid();
		searchForm();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/* 	labelWidth : 100,
			inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "用户帐号",
				name : "userNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "user.userNo"
				}
			}, {
				display : "用户名称",
				name : "userName",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "user.userName"
				}
			}, {
				display : "机构标识",
				name : "orgNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "user.orgNo"
				}
			}, {
				display : "用户状态",
				name : "userStsBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'userSts',
					data : [{id : '1', text : '启用'}, {id : '0', text : '停用'}]
				},
				attr : {
					op : "=",
					field : "user.userSts"
				}
			} ]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					width : '100%',
					columns : [
							{
								display : '用户帐号',
								name : 'userNo',
								align : 'left',
								width : '13%',
								minWidth : '10%'
							},
							{
								display : '用户名称',
								name : 'userName',
								align : 'center',
								width : '14%',
								minWidth : '10%'
							},
							{
								display : '机构',
								name : 'orgNo',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : QYBZRenderOrg
							},
							{
								display : '部门',
								name : 'deptNo',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : QYBZRenderDept
							},
							{
								display : '邮箱',
								name : 'email',
								align : 'center',
								width : '14%',
								minWidth : '10%'
							},
							{
								display : '用户状态',
								name : 'userSts',
								align : 'center',
								width : '11%',
								minWidth : '10%',
								render : QYBZRenderSts
							},
							{
								display : '最后密码修改时间',
								name : 'lastPwdUpdateTime',
								type : 'date',
								format : 'yyyy-MM-dd hh:mm:ss',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							} ],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'userNo', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '增加',
			click : user_add_dynamic,
			icon : 'add',
			operNo : 'user_add'
		},{
			text : '修改',
			click : user_modify_dynamic,
			icon : 'modify',
			operNo : 'user_modify'
		},{
			text : '删除',
			click : user_delete_dynamic,
			icon : 'delete',
			operNo : 'user_delete'
		},{
			text : '密码重置',
			click : user_pwd,
			icon : 'memeber',
			operNo : 'user_pwd'
		},{
			text : '用户解锁',
			click : user_unlock,
			icon : 'lock',
			operNo : 'user_unlock'
		}];
		BIONE.loadToolbar(grid, btns, function() { });
	}
	
	// 获取机构名称
	function QYBZRenderOrg(rowdata) {
		return BIONE.paramTransformer(rowdata.orgNo,
				'${ctx}/bione/common/getOrgName');
	}
	
	// 获取部门名称
	function QYBZRenderDept(rowdata) {
		return BIONE.paramTransformer(rowdata.orgNo + rowdata.deptNo, 
				'${ctx}/bione/common/getDeptName');
	}
	
	// 状态显示,停/启用等
	function QYBZRenderSts(rowdata) {
		if(rowdata.userSts == '1') {
			return "启用";
		} else if(rowdata.userSts == '0') {
			return "停用";
		} else {
			return rowdata.userSts;
		}
	}
	
	
	/*
	 * 修改密码
	 * @Revision 2013-6-5
	 * liucheng2@yuchengtech.com
	 * 这个功能去掉了。 改成密码重置功能。
	 */ 
	/* function user_pwd(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenSmallDialog("修改密码", "userManage",
					"${ctx}/bione/admin/user/updatePwd?id=" + ids[0]);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	} */
	function user_pwd(item) {
		achieveIds();
		if (ids.length == 1) {
			$.ligerDialog.confirm('确实要重置该用户的密码吗?', function(yes) {
				if (yes) {
					var flag = false;
					var msg = '密码重置失败！';
					$.ajax({
						async : false,
						type : "POST",
						url : "${ctx}/bione/admin/user/" + ids[0] + "/resetPwd",
						success : function(data) {
							if(data && data.msg=='S') {
								flag = true;
							} else if(data && data.msg!='F') {
								msg = data.msg;
							}
						}
					});
					if (flag == true) {
						BIONE.tip('密码重置成功！');
						initGrid();
					} else {
						BIONE.tip(msg);
					}
				}
			});
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	// 用户功能配置
/* 	function user_config(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenLargeDialog("用户功能配置", "userManage",
					"${ctx}/bione/user/func!index.xhtml?id=" + ids[0]);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	} */
	
	function user_add_dynamic(){
		BIONE.commonOpenLargeDialog('添加用户', 'userManage',
				'${ctx}/bione/admin/user/new');
	}
	
	function user_modify_dynamic(){
		achieveIds();
		if (ids.length == 1) {
			if(user != "") {
				$.ligerDialog.error("用户: [ " + user.join(", ").toString() + " ] 不可修改!", "错误", function() {
					return false;
				});
			} else {
				userId = ids[0];
				BIONE.commonOpenLargeDialog('修改用户', 'userManage',
					'${ctx}/bione/admin/user/'+userId+'/edit');
			}
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}

	function user_delete_dynamic(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					if(user != "") {
						$.ligerDialog.error("用户: [ " + user.join(", ").toString() + " ] 不可删除!", "错误", function() {
							return false;
						});
					} else {
					 	$.ajax({
							async : false,
							type : "DELETE",
							url : "${ctx}/bione/admin/user/"+ids.join(","),
							success : function() {
								flag = true;
							}
						});
					}
					if (flag == true) {
						BIONE.tip('删除成功');
						initGrid();
					} else {
						BIONE.tip('删除失败');
					}
				}
			});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	function user_unlock() {
		achieveIds();
		if (ids.length == 1) {
			$.ligerDialog.confirm('确实要解锁该用户吗?', function(yes) {
				if (yes) {
					var flag = false;
					var msg = '解锁失败';
					$.ajax({
						async : false,
						type : "POST",
						url : "${ctx}/bione/admin/acclock/" + ids[0] + "/unlock",
						//dataType : "script",
						success : function(data) {
							if(data && data.msg=='S') {
								flag = true;
							} else if(data && data.msg!='F') {
								msg = data.msg;
							}
						}
					});
					if (flag == true) {
						BIONE.tip('解锁成功');
						initGrid();
					} else {
						BIONE.tip(msg);
					}
				}
			});
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function achieveIds() {
		ids = [];
		user = [];
		var rows = grid.getSelectedRows();
		$(rows).each(function() {
			ids.push(this.userId);
			if(this.isBuiltin == "1") 
				user.push(this.userNo);
		});
/* 		for(var i in rows) {
			ids.push(rows[i].userId);
		} */
	}
</script>
</head>
<body>
</body>
</html>