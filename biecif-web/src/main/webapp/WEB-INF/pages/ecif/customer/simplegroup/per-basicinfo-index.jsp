<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template19.jsp">
<script type="text/javascript">
	var grid, btns, url, tab, ids = [], test = [];
	var testId;
	var selectedTab;
	var param0;
	var param1;
	var param2;
	var param3;
	var laterRequesttime;
	$(init);

	function init() {
		url = "${ctx}/ecif/perinfo/perbasic/list.json";
		initGrid();
		initTab();
		searchForm();
		initButtons();
		addSearchButtons("#search", grid, "#searchbtn");
		returnInit();
	}
	// 定义沙漏计数器；初始值为0；
	var sandglass = 0;

	function returnInit(){
		// 查看页面中grid对象是否已生成；
		if (!$(".l-grid-hd-cell-inner")){ // 如果grid对象还未初始化完毕；
			if (sandglass<4) { // 如果计数器的值小于4，则继续等待，500毫秒后再次查看grid对象初始化是否完成；
				sandglass ++;
				window.setTimeout(returnInit, 500);
				return;
			} else {
				// 如果等待时间超过4x500=2000毫秒，也即2秒，grid对象还未加载成功，则提示【页面加载超时】，并终止执行；
				BIONE.tip("页面加载超时！");
				return;
			}
		}
		
		var returnTabTag = "${returnTabTag}";
		if(returnTabTag != null && returnTabTag.length != 0){
			tab.selectTabItem(returnTabTag);
		}
		var existsRule = "${existsRule}";
		if(existsRule != null && existsRule == "true"){
			$('#custNo').val("${custNo}");
			$('#name').val("${name}");
		 	$('#ebankRegNo').val("${ebankRegNo}");
		 	var identType = "${identType}";
		 	if(identType != null && identType.length != 0){
				$.ligerui.get("identType").selectValue(identType);		 	
				$('#identType').val("${identType}");
		 	}
		 	$('#identNo').val("${identNo}");
		 	var productType = "${productType}";
		 	if(productType != null && productType.length != 0){
				$.ligerui.get("productType").selectValue(productType);
				$('#productType').valid("${productType}");
		 	}
		 	$('#productNo').val("${productNo}");
		 	$('#Contmeth').val("${Contmeth}");
		 	$('#Address').val("${Address}");
		 	//$("#searchbtn div:first").click();
			var rule = bulidFilterGroup($("#search"));
			if (rule.rules.length) {
				grid.set('parms', {
					condition : JSON2.stringify(rule)
				});
				grid.set('newPage', 1);
			} else {
				grid.set('parms', {
					condition : ''
				});
				grid.set('newPage', 1);
			}
			//grid.loadData();
			/* @Revision 20130704182000 liucheng2@yuchengtech.com
			 * 防止grid进行ajax取数时session失效而无法正确获取数据； */
			if (!grid.loading || grid.loading!=true) {
				var opurl = grid.options.url;
				var ctx = "/";
				if (opurl) {
					ctx = "/" + opurl.split("/")[1] + "/";
				}
				$.ajax({
					cache : false,
					async : true,
					url : ctx + "/bione/common/getComboxData.json",
					dataType : 'json',
					data : {
						"paramTypeNo" : ""
					},
					type : grid.options.type,
					/* beforeSend : function() {
						BIONE.loading = true;
						BIONE.showLoading("请稍候...");
					}, */
					complete : function(XMLHttpRequest) {
						//BIONE.loading = false;
						//BIONE.hideLoading();
						BIONE.isSessionAlive(XMLHttpRequest);
					},
					success : function() {
						grid.loadData();
					}
				});
			}
			else {
				BIONE.showError("查询进行中，请勿重复查询！");
			}
			/* @Revision 20130704182000 END */
		}
	}

	addSearchButtons = function(form, grid, btnContainer) {
		if (!form)
			return;
		form = $(form);
		if (btnContainer) {
			BIONE.createButton({
				appendTo : btnContainer,
				text : '搜索',
				icon : 'search3',
				width : '50px',
				click : function() {
					var loading=$(".l-grid-loading");
					if(loading&&loading.css("display")=="block"){
						BIONE.tip('数据正在加载中,请稍后进行查询操作!');
					}else{
											
					var rule = bulidFilterGroup(form);
					if(!$('#form_basic').valid()){
						return;
					}
					if (tab.getSelectedTabItemID() == 'styles'
							&& ($('#identType').val() == null || $('#identType').val() == "") 
									&& ($('#identNo').val() != null && $('#identNo').val() != "")) {
						BIONE.tip("当证件号码不为空时请输入证件类型");
					} else if (tab.getSelectedTabItemID() == 'product'
							&& ($('#productType').val() == null || $('#productType').val() == "") 
									&& ($('#productNo').val() != null && $('#productNo').val() != "")) {
						BIONE.tip("当产品号码不为空时请输入产品类型");
					} else {
						if (rule.rules.length) {
							grid.set('parms', {
								condition : JSON2.stringify(rule)
							});
							grid.set('newPage', 1);
						} else {
							grid.set('parms', {
								condition : ''
							});
							grid.set('newPage', 1);
						}
						/* @Revision 20130704182000 liucheng2@yuchengtech.com
						 * 防止grid进行ajax取数时session失效而无法正确获取数据； */
						if (!grid.loading || grid.loading!=true) {
							var opurl = grid.options.url;
							var ctx = "/";
							if (opurl) {
								ctx = "/" + opurl.split("/")[1] + "/";
							}
							$.ajax({
								cache : false,
								async : true,
								url : ctx + "/bione/common/getComboxData.json",
								dataType : 'json',
								data : {
									"paramTypeNo" : ""
								},
								//type : grid.options.type,
								type : "post",
								/* beforeSend : function() {
									BIONE.loading = true;
									BIONE.showLoading("请稍候...");
								}, */
								complete : function(XMLHttpRequest) {
									//BIONE.loading = false;
									//BIONE.hideLoading();
									BIONE.isSessionAlive(XMLHttpRequest);
								},
								success : function() {
									grid.loadData();
								}
							});
						}
						else {
							BIONE.showError("查询进行中，请勿重复查询！");
						}
						/* @Revision 20130704182000 END */
					}
					//点击搜索之后给隐藏域赋值，传递当前搜索条件
					var tabName = tab.getSelectedTabItemID();
					var custNo = $('#custNo').val();
					var name = $('#name').val();
					var ebankRegNo = $('#ebankRegNo').val();
					var identType = $('#identType').val();
					var identNo = $('#identNo').val();
					var productType = $('#productType').val();
					var productNo = $('#productNo').val();
					var Contmeth = $('#Contmeth').val();
					var Address = $('#Address').val();
					if ($('#custNo').attr("tabFlag") == tabName) {
						param0 = tabName;
						param1 = custNo;
						param2 = name;
						param3 = ebankRegNo;
					}
					if ($('#identType').attr("tabFlag") == tabName) {
						param0 = tabName;
						param1 = identType;
						param2 = identNo;
					}
					if ($('#productType').attr("tabFlag") == tabName) {
						param0 = tabName;
						param1 = productType;
						param2 = productNo;
					}
					if ($('#Contmeth').attr("tabFlag") == tabName) {
						param0 = tabName;
						param1 = Contmeth;
						param2 = Address;
					}
					}
				}
			});
			BIONE.createButton({
				appendTo : btnContainer,
				text : '重置',
				icon : 'refresh2',
				width : '50px',
				click : function() {
					var tabName = tab.getSelectedTabItemID();
					if ($('#custNo').attr("tabFlag") == tabName) {
						$('#custNo').val("");
						$('#name').val("");
						$('#ebankRegNo').val("");
					}
					if ($('#identType').attr("tabFlag") == tabName) {
						$('#identType').val("");
						$('#identNo').val("");
					}
					if ($('#productType').attr("tabFlag") == tabName) {
						$('#productType').val("");
						$('#productNo').val("");
					}
					if ($('#Contmeth').attr("tabFlag") == tabName) {
						$('#Contmeth').val("");
						$('#Address').val("");
					}
				}
			});
		}
	};

	// 创建过滤规则(查询表单)
	bulidFilterGroup = function(form) {
		if (!form)
			return null;
		var group = {
			op : "and",
			rules : []
		};
		$(":input", form).not(":submit, :reset, :image,:button, [disabled]")
				.each(
						function() {
							if (!this.name)
								return;
							// if (!$(this).hasClass("field"))
							// return;
							if ($(this).val() == null || $(this).val() == "")
								return;
							if ($(this).attr("field") == null
									|| $(this).attr("field") == "")
								return;
							if ($(this).attr("tabFlag") != tab
									.getSelectedTabItemID())
								return;
							var ltype = $(this).attr("ltype");
							var optionsJSON = $(this).attr("ligerui"), options;
							if (optionsJSON) {
								options = JSON2.parse(optionsJSON);
							}
							var field = $(this).attr("field");
							var tabName = $(this).attr("tabFlag");
							var op = $(this).attr("op") || "=";
							var type = $(this).attr("vt") || "string";
							var value = $(this).val();
							// 如果是下拉框，那么读取下拉框关联的隐藏控件的值(ID值,常用与外表关联)
							if (ltype == "select" && options && options.valueFieldID) {
								value = $("#" + options.valueFieldID).val();
								name = options.valueFieldID;
							}
							group.rules.push({
								op : op,
								field : field,
								value : value,
								type : type,
								tabName : tabName
							});
						});
		return group;
	};

	//搜索表单应用ligerui样式
	function searchForm() {
		//按照客户查找
		$("#form_basic").ligerForm({
			fields : [ {
				display : "搜索条件0",
				name : "param0",
				type : "hidden",
				value : param0
			}, {
				display : "搜索条件1",
				name : "param1",
				type : "hidden",
				value : param1
			}, {
				display : "搜索条件2",
				name : "param2",
				type : "hidden",
				value : param2
			}, {
				display : "搜索条件3",
				name : "param3",
				type : "hidden",
				value : param3
			}, {
				display : "客户号",
				name : "custNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "basic",
					op : "=",
					field : "custNo"
				},
				validate : {
				    required : false,
				    maxlength : 32,
				    number : "请输入合法的数值"
				}
			}, {
				display : "客户名称",
				name : "name",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "basic",
					op : "like",
					field : "name"
				}
			}, {
				display : "网银注册号",
				name : "ebankRegNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "basic",
					op : "=",
					field : "ebankRegNo"
				}
			} ]
		});

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#form_basic"));

		$("#form_styles").ligerForm({
			fields : [ {
				display : "证件类型",
				name : "identType",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'id',
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010060",
					ajaxType : "post"
				},
				attr : {
					tabFlag : "styles",
					op : "=",
					field : "identType"
				}
			}, {
				display : "证件号码",
				name : "identNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "styles",
					op : "=",
					field : "identNo"
				}
			} ]
		});

		$("#form_product").ligerForm({
			fields : [ {
				display : "产品类型",
				name : "productType",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'id',
					data : [ {
						id : '0',
						text : '存款账户账号'
					}, {
						id : '1',
						text : '贷款账户账号'
					}, {
						id : '2',
						text : '担保合同编号'
					}, {
						id : '3',
						text : '贷款合同编号'
					}, {
						id : '4',
						text : '理财账户信息理财账号'
					}, {
						id : '5',
						text : '电子式债券号'
					}, {
						id : '6',
						text : '基金份额理财账号'
					}, {
						id : '7',
						text : '网银协议账号'
					}, {
						id : '8',
						text : '授信协议授信合同号'
					}, /* {
						id : '9',
						text : '黄金交易签约黄金账号'
					}, {
						id : '10',
						text : '网银签约银行账号'
					},  */{
						id : '11',
						text : '凭证式债券号'
					}]
				},
				attr : {
					tabFlag : "product",
					op : "=",
					field : "productType"
				}
			}, {
				display : "产品号码",
				name : "productNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "product",
					op : "=",
					field : "productNo"
				}
			} ]
		});

		$("#form_verify").ligerForm({
			fields : [ {
				display : "客户联系号码",
				name : "Contmeth",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "verify",
					op : "=",
					field : "Contmeth"
				}
			}, {
				display : "客户联系地址",
				name : "Address",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					tabFlag : "verify",
					op : "like",
					field : "Address"
				}
			} ]
		});
	}

	function initTab() {
		tab = $('#tab').ligerTab({
			onBeforeSelectTabItem : f_selectTab,
			onAfterSelectTabItem : f_selectedTab
		});

	}

	function f_selectedTab(TabId) {
		selectedTab = tab.getSelectedTabItemID();
		return true;
	}

	//切换tab页面的时候清空数据
	function f_selectTab(TabId) {
		var st = $.ligerui.find($.ligerui.controls.ComboBox);
		$.each(st, function(i, n) {
			if (n.selectBox.is(":visible")) {
				n._toggleSelectBox(true);
			}
		});
		if ($('#form_basic').valid() && $('#form_styles').valid()
				&& $('#form_product').valid() && $('#form_verify').valid()) {
			return true;
		} else {
			return false;
		}

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			width : '100%',
			columns : [ {
				display : '客户号',
				name : 'custNo',
				align : 'center',
				width : 100,
				minWidth : 100,
				render : renderData
			}, {
				display : '客户名称',
				name : 'name',
				align : 'center',
				width : 100
			}, {
				display : '性别',
				name : 'gender',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '出生日期',
				name : 'birthday',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '国籍',
				name : 'citizenship',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '民族',
				name : 'nationality',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '婚姻状况',
				name : 'marriage',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '职业',
				name : 'career',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '职务',
				name : 'duty',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '最高学位',
				name : 'highestDegree',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '最高学历',
				name : 'highestSchooling',
				align : 'center',
				width : 130,
				minWidth : 100	
			}, {
				display : '是否本行员工',
				name : 'isEmployee',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '是否重要客户',
				name : 'isImportantCust',
				align : 'center',
				width : 130,
				minWidth : 100
			}, {
				display : '客户生命周期状态类型',
				name : 'lifecycleStatType',
				align : 'center',
				width : 130,
				minWidth : 100
			} ],
			checkbox : false,
			delayLoad :true,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'custId', //第一次默认排序的字段
			sortOrder : 'asc',
			toolbar : {}
		});
	}

	function renderData(data) {
		var custId = data.custId;
		var queryName = data.name;
		<%--
		var value = "<a href='${ctx}/ecif/perinfo/perdetailed?custId="
				+ data.custId + "&queryName="+data.name+"&tabTag="+tabTag+"&rule="+rule+"&pageName="+pageName+"'><font color='blue'>" + data.custNo + "</font></a>";
				alert(value);
		return value;
		--%>
		var value = "<a href='#' onclick='submitFrom(\""+custId+"\", \""+queryName+"\");'><font color='blue'>" + data.custNo + "</font></a>";
		return value;
	}
	
	function submitFrom(custId, queryName){
		var tabTag = tab.getSelectedTabItemID();
		var rule = JSON2.stringify(bulidFilterGroup($("#search")));
		var pageName = "per-basicinfo-index";
		var form = $('<form/>').attr({
			target : '',
			method : 'post',
			action : '${ctx}/ecif/perinfo/perdetailed'
		}).css('display', 'none');
		var inputTabTag = $('<input/>').attr({
			type : 'hidden',
			name : 'tabTag',
			value : tabTag
		});
		var inputRule = $('<input/>').attr({
			type : 'hidden',
			name : 'rule',
			value : rule
		});
		var inputPageName = $('<input/>').attr({
			type : 'hidden',
			name : 'pageName',
			value : pageName
		});
		var inputCustId = $('<input/>').attr({
			type : 'hidden',
			name : 'custId',
			value : custId
		});
		var inputQueryName = $('<input/>').attr({
			type : 'hidden',
			name : 'queryName',
			value : queryName
		});
		$('body').append(form);
		form.append(inputTabTag);
		form.append(inputRule);
		form.append(inputPageName);
		form.append(inputCustId);
		form.append(inputQueryName);
		form.submit();
		form.remove();
	}
	
	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '导出文件',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});
	}

	function doDownload(file) {
		if (file == null || file == "") {
			BIONE.tip("没有查询到符合条件的信息。");
			return;
		}
		var form = $('<form/>').attr({
			target : '',
			method : 'post',
			action : '${ctx}/ecif/perinfo/perbasic/export.xls'
		}).css('display', 'none');
		var input = $('<input/>').attr({
			type : 'hidden',
			name : 'file',
			value : file
		});
		$('body').append(form);
		form.append(input);
		form.submit();
		form.remove();
	}

	function file_down() {
		var param0 = null;
		var param1 = null;
		var param2 = null;
		var param3 = null;
		if(tab.getSelectedTabItemID() == 'basic'){
			param0 = 'basic';
			param1 = $('#custNo').val();
			param2 = $('#name').val();
			param3 = $('#ebankRegNo').val();
		}
		if(tab.getSelectedTabItemID() == 'styles'){
			param0 = 'styles';
			//param1 = $('#identType').val();
			param1 = $('#id').val();
			param2 = $('#identNo').val();
		}
		if(tab.getSelectedTabItemID() == 'product'){
			param0 = 'product';
			//param1 = $('#productType').val();
			param1 = $('#id').val();
			param2 = $('#productNo').val();
		}
		if(tab.getSelectedTabItemID() == 'verify'){
			param0 = 'verify';
			param1 = $('#Contmeth').val();
			param2 = $('#Address').val();
		}
		var timestamp = new Date().getTime();
		$.ajax({
			url : '${ctx}/ecif/perinfo/perbasic/getReportFile?' + timestamp,
			type : 'get',
			data : {
				param0 : param0,
				param1 : param1,
				param2 : param2,
				param3 : param3
			},
			//success : doDownload
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("下载中，请稍候...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success: doDownload,
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	window.document.onkeydown = function(e) {
		e = !e ? window.event : e;
		var key = window.event ? e.keyCode : e.which;
		if (key == 13) {
			var btns = $(".l-btn");
			for(var i=0;i<btns.length;i++){
				var offset = btns[i].innerText.indexOf('搜索');
				if (offset != -1) {
					btns[i].click();
				}
			}
		}
	};
	//产生时间戳
	function unixtime(){		
		var dt = new Date();		
		var ux = Date.UTC(dt.getFullYear(),dt.getMonth(),dt.getDay(),dt.getHours(),dt.getMinutes(),dt.getSeconds())/1000;		
		return ux;	
	}
</script>
</head>
<body>
<a href="" onclick=""></a>
</body>
</html>