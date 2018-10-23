/*******************************************************/
/****             BIONE(基础应用平台)公用JS文件           ****/
/****         维护具有公用价值的JS代码，比如校验、Ajax调用等    ****/
/******************************************************/

/**
 * 根据URL请求内容，根据返回的内容更新页面指定DIV
 * 
 * url:请求的资源路径 divId：需要被更新的DIV的id showTip:操作成功后是否在右下角提示 默认false
 */

(function($) {
	// 全局系统对象
	window['BIONE'] = {};

	BIONE.cookies = (function() {
		var fn = function() {
		};
		fn.prototype.get = function(name) {
			var cookieValue = "";
			var search = name + "=";
			if (document.cookie.length > 0) {
				offset = document.cookie.indexOf(search);
				if (offset != -1) {
					offset += search.length;
					end = document.cookie.indexOf(";", offset);
					if (end == -1)
						end = document.cookie.length;
					cookieValue = decodeURIComponent(document.cookie.substring(
							offset, end));
				}
			}
			return cookieValue;
		};
		fn.prototype.set = function(cookieName, cookieValue, DayValue) {
			var expire = "";
			var day_value = 1;
			if (DayValue != null) {
				day_value = DayValue;
			}
			expire = new Date((new Date()).getTime() + day_value * 86400000);
			expire = "; expires=" + expire.toGMTString();
			document.cookie = cookieName + "="
					+ encodeURIComponent(cookieValue) + ";path=/" + expire;
		};
		fn.prototype.remvoe = function(cookieName) {
			var expire = "";
			expire = new Date((new Date()).getTime() - 1);
			expire = "; expires=" + expire.toGMTString();
			document.cookie = cookieName + "=" + escape("") + ";path=/"
					+ expire;
			/* path=/ */
		};
		return new fn();
	})();
	// 右下角的提示框
	BIONE.tip = function(message) {
		if (BIONE.wintip) {
			BIONE.wintip.set('content', message);
			BIONE.wintip.show();
		} else {
		    	BIONE.wintip = top.$.ligerDialog.tip({
				content : message
			});
		}
		setTimeout(function() {
		    	BIONE.wintip.hide();
		}, 4000);
	};
	// 预加载图片
	BIONE.prevLoadImage = function(rootpath, paths) {
		for ( var i in paths) {
			$('<img />').attr('src', rootpath + paths[i]);
		}
	};
	// 显示loading
	BIONE.showLoading = function(message) {
		message = message || "正在加载中...";
		$('body').append("<div class='jloading'>" + message + "</div>");
		$.ligerui.win.mask();
	};
	// 隐藏loading
	BIONE.hideLoading = function(message) {
		$('body > div.jloading').remove();
		$.ligerui.win.unmask({
			id : new Date().getTime()
		});
	};
	// 显示成功提示窗口
	BIONE.showSuccess = function(message, callback) {
		if (typeof (message) == "function" || arguments.length == 0) {
			callback = message;
			message = "操作成功!";
		}
		$.ligerDialog.success(message, '提示信息', callback);
	};
	// 显示失败提示窗口
	BIONE.showError = function(message, callback) {
		if (typeof (message) == "function" || arguments.length == 0) {
			callback = message;
			message = "操作失败!";
		}
		$.ligerDialog.error(message, '提示信息', callback);
	};
	// 预加载dialog的图片
	BIONE.prevDialogImage = function(rootPath) {
		rootPath = rootPath || "";
		BIONE.prevLoadImage(rootPath + 'lib/ligerUI/skins/Aqua/images/win/',
				[ 'dialog-icons.gif' ]);
		BIONE.prevLoadImage(rootPath + 'lib/ligerUI/skins/Gray/images/win/',
				[ 'dialogicon.gif' ]);
	};
	/*
	 * @Revision 20130704182000 liucheng2@yuchengtech.com
	 * 防止grid进行ajax取数时session失效而无法正确获取数据；
	 * session失效时，转向登陆页
	 */
	BIONE.isSessionAlive = function(XMLHttpRequest) {
		var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");  
		if (sessionstatus=="timeout") {
			window.top.location.replace("${ctx}/login");
		}
	};
	/* @Revision 20130704182000 END */
	// 提交服务器请求
	// 返回json格式

	BIONE.ajax = function(options, callback) {
		var p = options || {};
		var type = p.type || "get";
		var url = p.url;
		var async = p.async || "true";
		$.ajax({
			cache : false,
			async : async,
			url : url,
			dataType : 'json',
			type : type,
			data : p.data,
			beforeSend : function() {
				BIONE.loading = true;
				if (p.beforeSend)
					p.beforeSend();
				else
					BIONE.showLoading(p.loading);
			},
			complete : function(XMLHttpRequest) {
				BIONE.isSessionAlive(XMLHttpRequest);
				if (p.complete){					
					p.complete();
				}
				else{
					if(typeof BIONE != 'undefined'){						
						BIONE.loading = false;
						BIONE.hideLoading();
					}
				}
			},
			success : callback,
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	};
	// 获取当前页面的MenuNo
	// 优先级1：如果页面存在MenuNo的表单元素，那么加载它的值
	// 优先级2：加载QueryString，名字为MenuNo的值
	BIONE.getPageMenuNo = function() {
		var menuno = $("#MenuNo").val();
		if (!menuno) {
			menuno = getQueryStringByName("MenuNo");
		}
		return menuno;
	};
	// 创建按钮
	BIONE.createButton = function(options) {

		var p = $.extend({
			appendTo : $('body')
		}, options || {});

		if (p.operNo && top.window["protectedResOperNo"]) {

			if ($.inArray(p.operNo, top.window["protectedResOperNo"]) != -1) {
				// 说明改按钮收权限保护，需要判断当前登录用户是否具有权限
				if (!top.window["authorizedResOperNo"]
						|| $.inArray(p.operNo,
								top.window["authorizedResOperNo"]) == -1) {
					return;
				}
			}
		}
		// l-toolbar-item l-panel-btn l-toolbar-item-hasicon
		var hasIcon = false;
		if (p.icon) {
			hasIcon = true;
		}
		// var btn = $('<div class="l-btn l-btn-hasicon"><div
		// class="l-btn-l"></div><div class="l-btn-r"> </div>
		// <span></span></div>');
		var btnHtml = '<div class="l-btn';
		if (hasIcon) {
			btnHtml = btnHtml + ' l-btn-hasicon';
		}
		btnHtml += '"><div class="l-btn-l"></div><div class="l-btn-r"> </div> <span></span></div>';
		var btn = $(btnHtml);
		if (p.icon) {
			btn.append('<div class="l-icon l-icon-' + p.icon + '"> </div> ');
		}

		if (p.width) {
			btn.width(p.width);
		}
		if (p.click) {
			btn.click(p.click);
		}
		if (p.text) {
			$("span", btn).html(p.text);
		}
		if (typeof (p.appendTo) == "string") {
			var space = 30;
			var iconwidth = 0;
			var prewidth = 0;
			var num = $(p.appendTo + ' .l-btn').length;
			p.appendTo = $(p.appendTo);
			if (num != 0)
				prewidth = p.appendTo.width();
			if (hasIcon)
				iconwidth = 10;
			p.appendTo.width(prewidth + parseInt(p.width) + space + iconwidth);
		}

		btn.appendTo(p.appendTo);
	};
	// 创建过滤规则(查询表单)
	BIONE.bulidFilterGroup = function(form) {
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
							var ltype = $(this).attr("ltype");
							var optionsJSON = $(this).attr("ligerui"), options;
							if (optionsJSON) {
								options = JSON2.parse(optionsJSON);
							}
							var field = $(this).attr("field");
							var op = $(this).attr("op") || "=";
							// get the value type(number or date)
							var type = $(this).attr("vt") || "string";
							var value = $(this).val();
							// 如果是下拉框，那么读取下拉框关联的隐藏控件的值(ID值,常用与外表关联)
							if (ltype == "select" && options
									&& options.valueFieldID) {
								value = $("#" + options.valueFieldID).val();
								name = options.valueFieldID;
							}
							group.rules.push({
								op : op,
								field : field,
								value : value,
								type : type
							});
						});
		return group;
	};
	// 创建表单搜索按钮：搜索、高级搜索
	BIONE.addSearchButtons = function(form, grid, btnContainer) {
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
						
					
					var rule = BIONE.bulidFilterGroup(form);
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
					if (grid.gridloading.css("display")=="none") {
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
							
							complete : function(XMLHttpRequest) {
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
			});
			BIONE.createButton({
						appendTo : btnContainer,
						text : '重置',
						icon : 'refresh2',
						width : '50px',
						click : function() {
							$(":input", form)
									.not(":submit, :reset,:hidden,:image,:button, [disabled]")
									.each(function() {
										$(this).val("");
									});
							$(":input[ltype=combobox]", form)
							.each(function() {
								$(this).val("");
							});
						}
					});
		}
	};
	// 快速设置表单底部默认的按钮:保存、取消
	BIONE.setFormDefaultBtn = function(cancleCallback, savedCallback) {
		// 表单底部按钮
		var buttons = [];
		if (cancleCallback) {
			buttons.push({
				text : '取消',
				onclick : cancleCallback
			});
		}
		if (savedCallback) {
			buttons.push({
				text : '保存',
				onclick : savedCallback
			});
		}
		BIONE.addFormButtons(buttons);
	};
	// 增加表单底部按钮,比如：保存、取消
	BIONE.addFormButtons = function(buttons) {
		if (!buttons)
			return;
		var formbar = $("div.form-bar");
		if (formbar.length == 0)
			formbar = $(
					'<div class="form-bar"><div class="form-bar-inner"></div></div>')
					.appendTo('body');
		if (!(buttons instanceof Array)) {
			buttons = [ buttons ];
		}
		$(buttons)
				.each(
						function(i, o) {
							var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div> ');
							$("div.l-dialog-btn-inner:first", btn).html(
									o.text || "BUTTON");
							if (o.onclick) {
								btn.bind('click', function() {
									o.onclick(o);
								});
							}
							if (o.width) {
								btn.width(o.width);
							}
							$("> div:first", formbar).append(btn);
						});
	};

	// 填充表单数据
	BIONE.loadForm = function(mainform, options) {
		options = options || {};
		// 获取form数据之后的success函数
		function f_loadform(data) {
			// 根据返回的属性名，找到相应ID的表单元素，并赋值
			for ( var p in data) {
				var ele = $("[name=" + p + "]");
				// 针对复选框和单选框 处理
				if (ele.is(":checkbox,:radio")) {
					ele[0].checked = data[p] ? true : false;
				} else if (ele.is(":text") && ele.attr("ltype") == "date") {
					if (data[p]) {
						var date=null;
						if(data[p].time){
							date = new Date(data[p].time);
						}else{
							//edit by caiqy
							var tdate;
							if(typeof data[p] == 'string' 
								&& data[p].indexOf('-') != -1 && data[p].length >= 10){
					    		// if format as 'yyyy-MM-dd' or 'yyyy-mm-dd hh:mm:ss'
					    		tdate = new Date(new Number(data[p].substr(0,4)),new Number(data[p].substr(5,2))-1,new Number(data[p].substr(8,2)));
					    	}else{
					    		tdate = new Date(data[p]);
					    	}
							date = new Date(tdate);
						}
						var yy = date.getFullYear();
						var Mm = ((date.getMonth() + 1) < 10) ? ('0' + (date
								.getMonth() + 1)) : (date.getMonth() + 1);
						var dd = (date.getDate() < 10) ? ('0' + date.getDate())
								: date.getDate();
						ele.val(yy + '-' + Mm + '-' + dd);
					}
				} else if (ele.is(":hidden") && (ele.attr("ltype") == "radioGroup" || ele.attr("ltype") == "checkboxGroup")) {
					$.ligerui.get(ele.attr("id")).setValue(data[p]);
				} else {
					ele.val(data[p]);
				}
			}
			// 下面是更新表单的样式
			var managers = $.ligerui.find($.ligerui.controls.Input);
			for ( var i = 0, l = managers.length; i < l; i++) {
				// 改变了表单的值，需要调用这个方法来更新ligerui样式
				var o = managers[i];
				o.updateStyle();
				if (managers[i] instanceof $.ligerui.controls.TextBox)
					o.checkValue();
			}
		}
		BIONE.ajax(options, f_loadform);
	};

	// 带验证、带loading的提交
	BIONE.submitForm = function(mainform, successOwn, error) {
		if (!mainform)
			mainform = $("form:first");
		if (mainform.valid()) {
			mainform
					.ajaxSubmit({
						dataType : 'json',
						success : function(result){
							BIONE.hideLoading();
							successOwn(result);
						},
						beforeSubmit : function(formData, jqForm, options) {
							// 针对复选框和单选框 处理
							$(":checkbox,:radio", jqForm).each(function() {
								if (!existInFormData(formData, this.name)) {
									formData.push({
										name : this.name,
										type : this.type,
										value : this.checked
									});
								}
							});
							for ( var i = 0, l = formData.length; i < l; i++) {
								var o = formData[i];
								if (o.type == "checkbox" || o.type == "radio") {
									o.value = $("[name=" + o.name + "]", jqForm)[0].checked ? "true"
											: "false";
								}
							}
						},
						beforeSend : function(a, b, c) {
							BIONE.showLoading('正在保存数据中...');
						},
						complete : function() {
							//BIONE.hideLoading();
						},
						error : function(result) {
							BIONE.hideLoading();
							BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
						}
					});
		} else {
			BIONE.showInvalid();
		}
		function existInFormData(formData, name) {
			for ( var i = 0, l = formData.length; i < l; i++) {
				var o = formData[i];
				if (o.name == name)
					return true;
			}
			return false;
		}
	};
	// 提示 验证错误信息
	BIONE.showInvalid = function(validator) {
		validator = validator || BIONE.validator;
		if (!validator)
			return;
		var message = '<div class="invalid">存在' + validator.errorList.length
				+ '个字段验证不通过，请检查!</div>';
		// top.BIONE.tip(message);
		$.ligerDialog.error(message);
	};
	// 表单验证
	BIONE.validate = function(form, options) {
		if (typeof (form) == "string")
			form = $(form);
		else if (typeof (form) == "object" && form.NodeType == 1)
			form = $(form);
		options = $.extend({
//			errorPlacement : function(lable, element) {
//				if (!element.attr("id"))
//					element.attr("id", new Date().getTime());
//				if (element.hasClass("l-textarea")) {
//					element.addClass("l-textarea-invalid");
//				} else if (element.hasClass("l-text-field")) {
//					element.parent().addClass("l-text-invalid");
//				}
//				$(element).removeAttr("title").ligerHideTip();
//				$(element).attr("title", lable.html()).ligerTip({
//					distanceX : 5,
//					distanceY : -3,
//					auto : true
//				});
//			},
//			success : function(lable) {
//				if (!lable.attr("for"))
//					return;
//				var element = $("#" + lable.attr("for"));
//				if (element.hasClass("l-textarea")) {
//					element.removeClass("l-textarea-invalid");
//				} else if (element.hasClass("l-text-field")) {
//					element.parent().removeClass("l-text-invalid");
//				}
//				$(element).removeAttr("title").ligerHideTip();
//			}
			//修改为验证样式3
//			ignore: "",//开启对隐藏域进行验证
            errorPlacement: function (lable, element) {
                if (element.hasClass("l-textarea")) {
                    element.addClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field")) {
                    element.parent().addClass("l-text-invalid");
                }
                var nextCell = element.parents("li:first").next("li");
                nextCell.find("div.l-exclamation").remove(); 
                $('<div class="l-exclamation" title="' + lable.html() + '"></div>').appendTo(nextCell).ligerTip(); 
            },
            success: function (lable) {
                var element = $("#" + lable.attr("for"));
                if(!element.attr("id"))element = $("input[name='" + lable.attr("for") + "']:last");
                var nextCell = element.parents("li:first").next("li");
                if (element.hasClass("l-textarea")) {
                    element.removeClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field")) {
                    element.parent().removeClass("l-text-invalid");
                }
                nextCell.find("div.l-exclamation").remove();
                $(".l-verify-tip").remove();
            },width:200
		}, options || {});
		BIONE.validator = form.validate(options);
		return BIONE.validator;
	};

	// 创建表格的操作栏按钮,带权限控制，只创建用户有权限访问的按钮
	// grid:表格对象
	// btns：表格操作栏的按钮集合
	// toolbarBtnItemClick：点击按钮触发的函数，对所有按钮生效，优先使用每个按钮自己的click函数
	BIONE.loadToolbar = function(grid, btns, toolbarBtnItemClick) {

		if (!grid.toolbarManager)
			return;
		if (!btns || !btns.length)
			return;
		var items = [];
		for ( var i = 0, l = btns.length; i < l; i++) {
			var o = btns[i];
			if (o.operNo && top.window["protectedResOperNo"]) {

				if ($.inArray(o.operNo, top.window["protectedResOperNo"]) != -1) {
					// 说明改按钮收权限保护，需要判断当前登录用户是否具有权限
					if (!top.window["authorizedResOperNo"]
							|| $.inArray(o.operNo,
									top.window["authorizedResOperNo"]) == -1) {
						continue;
					}
				}
			}

			items[items.length] = {
				click : o.click || toolbarBtnItemClick,
				text : o.text,
				icon : o.icon,
				id : o.operNo,
				menu : o.menu
			};
			items[items.length] = {
				line : true
			};
		}
		grid.toolbarManager.set('items', items);

	};
	BIONE.loadTreeToolbar = function(grid, btns, toolbarBtnItemClick) {

		if (!grid.treetoolbarManager)
			return;
		if (!btns || !btns.length)
			return;
		var items = [];
		for ( var i = 0, l = btns.length; i < l; i++) {
			var o = btns[i];
			if (o.operNo && top.window["protectedResOperNo"]) {

				if ($.inArray(o.operNo, top.window["protectedResOperNo"]) != -1) {
					// 说明改按钮收权限保护，需要判断当前登录用户是否具有权限
					if (!top.window["authorizedResOperNo"]
							|| $.inArray(o.operNo,
									top.window["authorizedResOperNo"]) == -1) {
						continue;
					}
				}
			}

			items[items.length] = {
				click : o.click || toolbarBtnItemClick,
				text : o.text,
				icon : o.icon,
				id : o.operNo,
				menu : o.menu
			};
			items[items.length] = {
				line : true
			};
		}
		grid.toolbarManager.set('items', items);

	};
	// 设置grid的双击事件(带权限控制)
	BIONE.setGridDoubleClick = function(grid, btnID, btnItemClick) {
		btnItemClick = btnItemClick || toolbarBtnItemClick;
		if (!btnItemClick)
			return;
		grid.bind('dblClickRow', function(rowdata) {
			var item = BIONE.findToolbarItem(grid, btnID);
			if (!item)
				return;
			grid.select(rowdata);
			btnItemClick(item);
		});
	};
	// 关闭dialog窗口
	BIONE.closeDialog = function(dialogName, message, flag) {
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get(dialogName);
		if(dialog.beforeClose != null
         		&& typeof dialog.beforeClose == "function"
         			&&flag!=null&&flag==true){        		
			 dialog.beforeClose();
         }
		dialog.close();
		if (message) {
			main.BIONE.tip(message);
		}
	};
	// 关闭dialog窗口,并刷新父窗口中的grid
	BIONE.closeDialogAndReloadParent = function(dialogName, gridName, message) {
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get(dialogName);
		dialog.close();
		main.jQuery.ligerui.get(gridName).loadData();
		if (message) {
			main.BIONE.tip(message);
		}
	};
	// 关闭图标选择dialog窗口,并向combox中赋值
	BIONE.closeIconDialog = function(dialogName, src) {
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get(dialogName);
		var c = main.jQuery.ligerui.get(dialog.options.comboxName);
		c._changeValue(src, src);
		dialog.close();
	};

	/*
	 * modify by xugy 2012-08-22
	 */
	// 弹出窗口公共函数
	BIONE.commonOpenDialog = function commonOpenDialog(title, name, width,
			height, url, comboxName, beforeClose) {

		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			// xugy modified begin
			comboxName : comboxName,
			// modified finished
			isResize : false,
			isDrag : false,
			isHidden : false
		});
		if(beforeClose!=null && 
				typeof beforeClose == "function"){
			_dialog.beforeClose = beforeClose;
		}
		return _dialog;

	};

	/**
	 * 打开一个固定高宽的弹出窗口(FORM)
	 * 
	 * @param title
	 *            窗口标题
	 * @param name
	 *            窗口名称/Id
	 * @param url
	 *            url
	 * @param beforeClose
	 *            关闭dialog调用函数
	 * @param buttons
	 *            按钮集合
	 * @returns
	 */
	BIONE.commonOpenSmallDialog = function commonOpenSmallDialog(title, name,
			url, beforeClose) {
		var width = 600;
		var height = 320;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : false,
			isHidden : false
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog.beforeClose = beforeClose;
		}
		return _dialog;
	};
	/**
	 * 打开一个固定高宽的弹出窗口(PAGE)
	 * 
	 * @param title
	 *            窗口标题
	 * @param name
	 *            窗口名称/Id
	 * @param url
	 *            url
	 * @param beforeClose
	 *            关闭dialog调用函数
	 * @param buttons
	 *            按钮集合
	 * @returns
	 */
	BIONE.commonOpenLargeDialog = function commonOpenLargeDialog(title, name,
			url, beforeClose) {
		var width = 800;
		var height = 450;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : false,
			isHidden : false
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog.beforeClose = beforeClose;
		}
		return _dialog;
	};
	/**
	 * 打开一个固定高宽的弹出窗口(PAGE)
	 * 
	 * @param title
	 *            窗口标题
	 * @param name
	 *            窗口名称/Id
	 * @param url
	 *            url
	 * @param beforeClose
	 *            关闭dialog调用函数
	 * @param buttons
	 *            按钮集合
	 * @returns
	 */
	BIONE.commonOpenIconDialog = function commonOpenLargeDialog(title, name,
			url, comboxName, beforeClose) {
		var width = 480;
		var height = 350;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : false,
			isHidden : false,
			comboxName : comboxName
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog._beforeClose = beforeClose;
		}
		return _dialog;
	};
	/**
	 * 刷新异步加载ztree指定节点
	 * 
	 * @param treeObj
	 *            树对象
	 * @param paramName
	 *            用来找到刷新节点的参数名称(默认是id)
	 * @param paramValue
	 *            用来找到刷新节点的参数值
	 * @param reloadType
	 *            刷新方式,'refresh'->清空后重新加载 其他->追加子节点处理
	 * @returns
	 */
	BIONE.refreshAsyncTreeNodes = function(treeObj, paramName, paramValue,
			reloadType) {
		if (treeObj && treeObj.setting && paramValue != null) {
			if (reloadType == null || reloadType == "") {
				reloadType = "refresh";
			}
			if (paramName == null || paramName == "") {
				paramName = "id";
			}
			var refreshNode = treeObj.getNodeByParam(paramName, paramValue,
					null);
			treeObj.reAsyncChildNodes(refreshNode, reloadType, false);
		}
	};
	/**
	 * grid字段代码转中文名称,默认通过系统参数表进行代码转换,如需根据其他的表进行代码转换需要开发扩展方法，并将url变量传递到函数内
	 * 
	 * @param paramValue
	 *            参数值
	 * @param url
	 *            获取参数URL "${ctx}/bione/common!getParamName.xhtml
	 * @param paramTypeNo
	 *            代码类型编号 自定义代码转换函数时可以为空
	 * @returns
	 */
	BIONE.paramTransformer = function(paramValue, url, paramTypeNo) {
		var result = "";
		var data = "";
		if (paramTypeNo) {
			data = 'paramTypeNo=' + paramTypeNo + '&paramValue=' + paramValue;
		} else {
			data = 'paramValue=' + paramValue;
		}
		$.ajax({
			cache : false,
			async : false,
			url : url,
			dataType : 'text',
			type : 'post',
			data : data,
			success : function(data) {
				result = data;
			}
		});
		return result;
	};
	
	/**
	 * 构造BIONE基础树
	 * @param target
	 * 				基础树渲染的目标对象
	 * @param options
	 * 				配置项信息
	 * 				
	 * @returns 树对象
	 */
	BIONE.BIONETree = function(target,options){
		if(!$.fn.zTree || typeof $.fn.zTree == 'undefined'
			|| !target || typeof target == 'undefined'){
			//    if zTree not define
			// or if target not define
			return null;
		}
		// init options default
		var defaultOpt = {
			dataType:'local',
			//数据源：本地(local)或远程(server),本地将读取options.nodes;远程将读取url
			nodes:[],				//节点对象数组，当dataType为local时生效
			url:false,				//远程访问url，当dataType为server时有效	
			ajaxType:"json",		//Ajax获取的数据类型
			method:'post',			//提交方式，支持post；get，不区分大小写
			dataFilter:null,		//用于对 Ajax 返回数据进行预处理的函数,远程调用时生效，
			// 参数：树对象，进行异步加载的父节点对象，异步加载完成后数据(Array/JSON)
			// 返回：树支持的Array/JSON即可
			onceInit:false,			//是否一次性加载，true为一次性加载数据；false为逐级异步加载，当dataType为server时有效
			commonParam:[],			//Ajax请求提交的静态参数键值对,远程加载时有效
			//1、Array格式:可以为空[]，如果有 key，则必须存在 value。 例如：[key1,value1,key2,value2],若[key1,value1,key2]，则被当作[key1,value1]解析
			//2、JSON格式：{ key1:value1, key2:value2 }
			autoParam:[],			//异步加载时需要自动提交父节点属性的参数，当远程加载，且不是一次性加载时有效
			//1、将需要作为参数提交的属性名称，制作成 Array 即可，例如：["id", "name"]
			//2、可以设置提交时的参数名称，例如 server 只接受 zId : ["id=zId"]
			keepParent:false,		//若为true，则所有当前为父节点的节点移除所有子节点后，依旧保持父节点状态
			keepLeaf:false,			//若为true，则所有当前为子节点的节点，无法添加子节点
			idKey:"id",				//节点数据中保存id的属性名称，dataType为local或远程取数，但使用一次性加载时有效
			pIdKey:"upId",			//节点数据中保存上级id的属性名称，dataType为local或远程取数，但使用一次性加载时有效
			rootPIdKey:null,		//指定根节点id，dataType为local或远程取数，但使用一次性加载时有效
			checkedKey:"checked",	//节点数据中保存 check 状态的属性名称
			childrenKey:"children",	//节点数据中保存子节点数据的属性名称
			nameKey:"text",			//节点数据中保存节点名称的属性名称
			tipKey:"",				//鼠标移动到节点上的tip提示内容属性名称，当showTip为true时有效；tipKey为空时自动与nameKey一致
			// checkbox - begin
			checkbox:false,			//是否显示复选框
			chkStyle:'checkbox',	//复选框类型，'checkbox'为复选框；'radio'为单选框
			radioType:'level',		//当chkStyle为单选框时，分组范围:'level'为每一级节点为一个分组；'all'为整棵树为一个分组
			chkedEft_p:true,		//勾选，是否影响父节点
			chkedEft_c:true,		//勾选，是否影响子节点
			chkOffEft_p:true,		//取消勾选，是否影响父节点
			chkOffEft_c:true,		//取消勾选，是否影响子节点
			// checkbox - end
			// edit - begin
			editEnable:false,		//是否启用树的编辑模式，只有在编辑模式下，才可以进行删除等操作
			removeTitle:"remove",	//鼠标移到删除按钮上的弹出提示，当editEnable为true时生效
			showRemoveBtn: true,	//是否显示删除按钮，可以为function，参数：树对象，删除节点对象；返回boolean，editEnable为true时有效
			// edit - end
			autoCancelSelected: true,	//点击节点时，按下 Ctrl 键是否允许取消选择操作。
			selectedMulti: false,	//是否允许同时选中多个节点
			//1、设置为 true时，按下 Ctrl 键可以选中多个节点
			//2、设置为 true / false 都不影响按下 Ctrl 键可以让已选中的节点取消选中状态（ 取消选中状态可以参考 autoCancelSelected ）
			showTip:true,			//是否显示节点tip，可以为function，参数：树对象，当前树节点；返回boolean
			showIcon:true,			//是否显示节点图片，可以为function，参数：树对象，当前树节点；返回boolean
			showLine:true,			//是否显示节点间连线
			// event - begin
			beforeCheck:null,		//勾选前,参数:树对象，勾选节点；返回false将不会改变勾选状态
			beforeClick:null,		//点击前,参数:树对象，勾选节点，clickFlag(参考zTree的api)；返回false将不会改变勾选状态
			beforeRemove:null,		//删除前,参数:树对象，勾选节点；返回false将不会删除
			onCheck:null,			//勾选后,参数:标准js的event对象,树对象，勾选节点；返回false将不会改变勾选状态
			onClick:null,			//点击后,参数:标准js的event对象,树对象，勾选节点，clickFlag(参考zTree的api)；返回false将不会改变勾选状态
			onRemove:null			//删除后,参数:标准js的event对象，树对象，要删除的节点对象	
			// event - end
		};
		
		// generator options - begin
		var p = defaultOpt;
		if(options && typeof options == "object"){
			if(options.dataType && typeof options.dataType != "undefine"
				&& (options.dataType == "local" || options.dataType == "server")){
				p.dataType = options.dataType;
			}
			if(options.nodes && typeof options.nodes == "object"){
				if((options.nodes) instanceof Array){
					p.nodes = options.nodes;
				}
			}
			if(options.url && typeof options.url != "undefined"){
				p.url = options.url;
			}
			if(options.ajaxType && typeof options.ajaxType != "undefined"){
				p.ajaxType = options.ajaxType;
			}
			if(options.method && typeof options.method != "undefined"){
				if((options.method).toLowerCase() == "get" 
					|| (options.method).toLowerCase() == "post"){					
					p.method = (options.method).toLowerCase();
				}
			}
			if(options.onceInit && typeof options.onceInit != "undefined"){
				p.onceInit = options.onceInit;
			}
			if(options.commonParam && typeof options.commonParam != "undefined"){
				if(typeof options.commonParam == "object"){
					if(options.commonParam instanceof Array){
						var paramArray = options.commonParam;
						var newObj = {};
						var newArray = [];
						if(paramArray.length > 0 && paramArray.length % 2 != 0){
							//若[key1,value1,key2]，则被当作[key1,value1]解析
							for(var tmp = 0 ; tmp < paramArray.length - 1 ; tmp++){
								newArray.push(paramArray[tmp]);
							}
						}else{
							newArray = paramArray;
						}
						for(var i = 0 ; i < newArray.length ; i+=2){
							newObj[newArray[i]] = newArray[i+1];
						}
						p.commonParam = newObj;
					}else{
						p.commonParam = options.commonParam;
					}
				}
			}
			if(options.autoParam && typeof options.autoParam == "object" 
				&& options.autoParam instanceof Array){
				p.autoParam = options.autoParam;
			}
			if(options.keepParent && typeof options.keepParent != "undefined"){
				p.keepParent = options.keepParent;
			}
			if(options.keepLeaf && typeof options.keepLeaf != "undefined"){
				p.keepLeaf = options.keepLeaf;
			}
			if(options.idKey && typeof options.idKey != "undefined"){
				p.idKey = options.idKey;
			}
			if(options.pIdKey && typeof options.pIdKey != "undefined"){
				p.pIdKey = options.pIdKey;
			}
			if(options.rootPIdKey && typeof options.rootPIdKey != "undefined"){
				p.rootPIdKey = options.rootPIdKey;
			}
			if(options.checkedKey && typeof options.checkedKey != "undefined"){
				p.checkedKey = options.checkedKey;
			}
			if(options.childrenKey && typeof options.childrenKey != "undefined"){
				p.childrenKey = options.childrenKey;
			}
			if(options.nameKey && typeof options.nameKey != "undefined"){
				p.nameKey = options.nameKey;
			}
			if(options.tipKey && typeof options.tipKey != "undefined"){
				p.tipKey = options.tipKey;
			}
			if(options.autoCancelSelected && typeof options.autoCancelSelected != "undefined"){
				p.autoCancelSelected = options.autoCancelSelected;
			}
			if(options.selectedMulti && typeof options.selectedMulti != "undefined"){
				p.selectedMulti = options.selectedMulti;
			}
			if(options.showTip 
					&& (typeof options.showTip == "boolean" || typeof options.showTip == "function")){
				p.showTip = options.showTip;
			}
			if(options.showIcon
					&& (typeof options.showIcon == "boolean" || typeof options.showIcon == "function")){
				p.showIcon = options.showIcon;
			}
			if(options.showLine && typeof options.showLine != "undefined"){
				p.showLine = options.showLine;
			}
			// checkbox - begin
			if(options.checkbox && typeof options.checkbox == "boolean"){
				p.checkbox = options.checkbox;
			}
			if(options.chkStyle && typeof options.chkStyle != "undefined"
				&& (options.chkStyle == "checkbox" || options.chkStyle == "radio")){
				p.chkStyle = options.chkStyle;
			}
			if(options.chkedEft_p && typeof options.chkedEft_p == "boolean"){
				p.chkedEft_p = options.chkedEft_p;
			}
			if(options.chkedEft_c && typeof options.chkedEft_c == "boolean"){
				p.chkedEft_c = options.chkedEft_c;
			}
			if(options.chkOffEft_p && typeof options.chkOffEft_p == "boolean"){
				p.chkOffEft_p = options.chkOffEft_p;
			}
			if(options.chkOffEft_c && typeof options.chkOffEft_c == "boolean"){
				p.chkOffEft_c = options.chkOffEft_c;
			}
			// checkbox - end
			// edit - begin
			if(options.editEnable && typeof options.editEnable == "boolean"){
				p.editEnable = options.editEnable;
			}
			if(options.removeTitle && typeof options.removeTitle != "undefined"){
				p.removeTitle = options.removeTitle;
			}
			if(options.showRemoveBtn && typeof options.showRemoveBtn != "undefined"){
				p.showRemoveBtn = options.showRemoveBtn;
			}
			// edit - end
			// callback - begin
			if(options.beforeCheck && typeof options.beforeCheck == "function"){
				p.beforeCheck = options.beforeCheck;
			}
			if(options.beforeClick && typeof options.beforeClick == "function"){
				p.beforeClick = options.beforeClick;
			}
			if(options.beforeRemove && typeof options.beforeRemove == "function"){
				p.beforeRemove = options.beforeRemove;
			}
			if(options.onCheck && typeof options.onCheck == "function"){
				p.onCheck = options.onCheck;
			}
			if(options.onClick && typeof options.onClick == "function"){
				p.onClick = options.onClick;
			}
			if(options.onRemove && typeof options.onRemove == "function"){
				p.onRemove = options.onRemove;
			}
			// callback - end
		}
		// generator options - end
		
		var setting = {};
		
		var nodes = [];
		
		// init view - begin
		var view = {
			autoCancelSelected:p.autoCancelSelected,
			selectedMulti:p.selectedMulti,
			showLine:p.showLine,
			showTip:true,
			showIcon:true
		};
		if(p.showTip){
			if(typeof p.showTip == "function"){				
				view.showTip = p.showTip;
			}else if(typeof p.showTip == "boolean"){
				view.showTip = p.showTip;
			}
		}
		if(p.showIcon){
			if(typeof p.showIcon == "function"){
				view.showIcon = p.showIcon;
			}
		}
		// init view - end
		// init edit - begin
		if(p.editEnable){
			var edit = {
				enable:true,
				removeTitle:p.removeTitle,
				showRemoveBtn:p.showRemoveBtn
			};
			if(p.showRemoveBtn){
				if(typeof p.showRemoveBtn == "function"){
					edit.showRemoveBtn = p.showRemoveBtn;
				}
			}
			setting.edit = edit;
		}
		// init edit - end
		// init data - begin
		var data = {
			keep:{
				leaf:p.keepLeaf,
				parent:p.keepParent
			},
			key:{
				checked:p.checkedKey,
				children:p.childrenKey,
				name:p.nameKey,
				title:p.tipKey
			},
			simpleData:{
				enable:true,
				idKey:p.idKey,
				pIdKey:p.pIdKey,
				rootPId:p.rootPIdKey
			}
		};
		// init data - end
		// init check - begin
		if(p.checkbox){
			var check = {
				enable:true,
				chkStyle:p.chkStyle,
				radioType:p.radioType
			};
			var type = {
				"Y":"",
				"N":""
			};
			var yTmp = "";
			if(p.chkedEft_p){
				yTmp = yTmp + "p";
			}
			if(p.chkedEft_c){
				yTmp = yTmp + "s";
			}
			var nTmp = "";
			if(p.chkOffEft_p){
				nTmp = nTmp + "p";
			}
			if(p.chkOffEft_c){
				nTmp = nTmp + "s";
			}
			type.Y = yTmp;
			type.N = nTmp;
			check.chkboxType = type;
			
			setting.check = check;
		}
		// init check - end
		// init callback - begin
		var callback = {
			beforeCheck:null,
			beforeClick:null,
			beforeRemove:null,
			onCheck:null,
			onClick:null,
			onRemove:null
		};
		if(p.beforeCheck != null && typeof p.beforeCheck == "function"){
			callback.beforeCheck = p.beforeCheck;
		}
		if(p.beforeClick != null && typeof p.beforeClick == "function"){
			callback.beforeClick = p.beforeClick;
		}
		if(p.beforeRemove != null && typeof p.beforeRemove == "function"){
			callback.beforeRemove = p.beforeRemove
		}
		if(p.onCheck != null && typeof p.onCheck == "function"){
			callback.onCheck = p.onCheck;
		}
		if(p.onClick != null && typeof p.onClick == "function"){
			callback.onClick = p.onClick;
		}
		if(p.onRemove != null && typeof p.onRemove == "function"){
			callback.onRemove = p.onRemove;
		}
		setting.callback = callback;
		// init callback - end
		
		if(p.dataType == "local"){
			nodes = p.nodes;
		}else if(p.dataType == "server" && p.url && p.onceInit){
			// if use server and use onceInit
			var async = {
				enable:false,
				dataType:p.ajaxType,
				type:p.method,
				url:p.url,
				otherParam:p.commonParam
			};
			setting.async = async;
			$.ajax({
				cache : false,
				// no async
				async : false,
				url : p.url,
				dataType : p.ajaxType,
				data:p.commonParam,
				type : p.method,
				success:function(result){
					if(result && typeof result == "object"){
						if(result instanceof Array){
							for(var j = 0 ; j < p.nodes.length ; j++){
								nodes.push(p.nodes[j]);
							}
							for(var i = 0 ; i < result.length ; i++){
								nodes.push(result[i]);
							}
						}
					}
				},
				error : function(result, b) {}
			});
		}else if(p.dataType == "server" && p.url && !p.onceInit){
			// if use async server  
			data.simpleData.enable = false;
			// analyse commonParam
			var async = {
				enable:true,
				dataType:p.ajaxType,
				type:p.method,
				url:p.url,
				otherParam:p.commonParam,
				autoParam:p.autoParam
			};
			setting.async = async;
		}
		
		setting.view = view;
		setting.data = data;
		
		var returnTree = $.fn.zTree.init(target,setting,nodes);
		// init common method - begin
		returnTree.baseNodes = p.nodes;
		// 1、根据id获取树对象
		returnTree["getTreeById"] = function(treeId){
			if(treeId != null){
				return $.fn.zTree.getZTreeObj(treeId);
			}
		};
		// 2、刷新
		if(p.dataType == "server" && !p.onceInit && p.url){			
			returnTree["refreshTree"] = function (treeNode){
				var treeObj = this;
				if(treeNode){
					treeObj.reAsyncChildNodes(treeNode, "refresh");
				}else{
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			};
		}else if(p.dataType == "server" && p.url && p.onceInit){
			returnTree["refreshTree"] = function (){
				var treeObj = this;
				treeObj.removeAll();
				$.ajax({
					cache : false,
					// no async
					async : false,
					url : treeObj.setting.async.url,
					dataType : treeObj.setting.async.ajaxType,
					data:treeObj.setting.async.commonParam,
					type : treeObj.setting.async.method,
					success:function(result){
						if(result && typeof result == "object"){
							if(result instanceof Array){
								var nodes = treeObj.baseNodes;
								for(var i = 0 ; i < result.length ; i++){
									nodes.push(result[i]);
								}
								treeObj.addNodes(null,nodes);
							}
						}
					},
					error : function(result, b) {}
				});
			};
		}
		// 3、移除所有节点
		returnTree["removeAll"] = function(){
			var treeObj = this;
			var nodes = treeObj.getNodes();
			var tIdArray = [];
			if(nodes != null){
				for(var i = 0 ; i < nodes.length ; i++){
					tIdArray.push(nodes[i].tId);
				}
				for(var j = 0 ; j < tIdArray.length ; j++){
					var nodeReal = treeObj.getNodeByParam("tId",tIdArray[j],null);
					if(nodeReal != null){
						treeObj.removeChildNodes(nodeReal);
						treeObj.removeNode(nodeReal);
					}
				}
			}
		};
		
		// init common method - end
		
		return returnTree;
	};

})(jQuery);