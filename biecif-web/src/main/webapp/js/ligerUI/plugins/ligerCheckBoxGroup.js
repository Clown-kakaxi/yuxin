/**
 * jQuery ligerUI 1.1.9
 * 
 * http://ligerui.com
 *  
 * Author tanxu 2013 [ tanxu@yuchengtech.com ]
 * 
 */
(function($) {
	
	$.fn.ligerCheckBoxGroup = function(options) {
		return $.ligerui.run.call(this, "ligerCheckBoxGroup", arguments);
	};
	
	$.fn.ligerGetCheckBoxGroupManager = function() {
		return $.ligerui.run.call(this, "ligerGetCheckBoxGroupManager", arguments);
	};
	
	$.ligerDefaults.CheckBoxGroup = {
		disabled : false
	};

	$.ligerMethos.CheckBoxGroup = {
		sepHtml : "&nbsp;", 	// radio标签之间的间隔符
		symbol : ",",			// 选值之间的分隔符
		ajaxType : "get", 		// 默认的ajax请求方式
		appendID : "_checkboxGroup",// 每个checkbox的name的名称后缀
		disabled : false, 		// 是否可选
		maxColumn : 2, 			// 单选按钮每行的最大排列数
		defaultValue : "", 		// 默认选中的checkbox值
		data : null, 			// 本地数据源，键值的形式为[{id:"",text:""},{……}]
		ajaxSuccessful : false,	// 是否成功载入
		callbackContext : {}, 	// 回调函数上下文
		url : null				// 通过URL异步读取数据源(需返回JSON){id:"",text:""},{……}
	};

	$.ligerui.controls.CheckBoxGroup = function(element, options) {
		$.ligerui.controls.CheckBoxGroup.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.CheckBoxGroup.ligerExtend($.ligerui.controls.Input, {
		__getType : function() {
			return 'CheckBoxGroup';
		},
		__idPrev : function() {
			return 'CheckBoxGroup';
		},
		_extendMethods : function() {
			return $.ligerMethos.CheckBoxGroup;
		},
		_render : function() {
			var g = this, p = this.options;
            g.input = $(g.element);
            if(p.data){
            	g.data = p.data;
            	g._renderByData(p.data);
            }  else if(p.url) {
            	$.ajax({
                    type: "get",
                    url: p.url,
                    cache: false,
                    dataType: 'json',
                    success: function (data){
                        g.data = data;
                        g._renderByData(g.data);
                        g._callbackFun();
                        g.trigger('success', [g.data]);
                    },
                    error: function (XMLHttpRequest, textStatus){
                        g.trigger('error', [XMLHttpRequest, textStatus]);
                    }
                });
            }
			g.set(p);
//			g.updateStyle();
		},
		
		//遍历json数据做渲染
		_renderByData: function(data){
        	var g = this, p = this.options, maxColumn = p.maxColumn | g.maxColumn, itemLength = data.length;
            $(data).each(function (i, item){
            	g._renderLabelAndCheckbox(item.id, item.text);
            	if((i+1)%maxColumn==0 && (i+1) != itemLength){
            		g.input.parent().append("<br/>");
            	}
            });
            p.defaultValue && g.setValue(p.defaultValue);
            g.ajaxSuccessful = true;
        },
        
        //根据参数值渲染控件组并添加相应的事件
        _renderLabelAndCheckbox: function(id, text){
        	var g = this, p = this.options, $input = g.input;
        	var checkbox = $('<input type="checkbox" style="resize: none;" class="l-hidden" />');
        	checkbox.attr("value", id).attr("name", $input.attr("name") + g.appendID);
        	var link = $('<a class="l-checkbox"></a>');
        	var label = $('<label>' + text + '</label>');
			var wrapper = checkbox.wrap(
					'<div class="l-checkbox-wrapper"></div>').parent();
			wrapper.prepend(link);
			$input.parent().append(wrapper).append(label).append(g.sepHtml);
			checkbox.change(function () {
                if (this.checked) {
                	link.addClass('l-checkbox-checked');
                }
                else {
                	link.removeClass('l-checkbox-checked');
                }
                return true;
            });
			link.click(function() {
				if (p.disabled)
					return false;
				if ($(this).hasClass("l-checkbox-checked")) {
					g._setValue(checkbox, false);
				} else {
					g._setValue(checkbox, true);
				}
				checkbox.trigger("change");
			});
			wrapper.hover(function() {
				if (!p.disabled)
					$(this).addClass("l-over");
			}, function() {
				$(this).removeClass("l-over");
			});
			p.disabled && wrapper.addClass("l-disabled");
        },
        
        //遍历调用回调函数上下文的函数调用并删除配置
        _callbackFun: function () {
        	var g = this;
        	for(var fun in g.callbackContext){
        		g[fun].call(this, g.callbackContext[fun]);
        		delete g.callbackContext[fun];
        	}
        },
        
        setValue: function (value) {
        	var g = this, $input = g.input, values = value.split(g.symbol);
        	if (g.ajaxSuccessful) {
        		$('input:checkbox[name=' + $input.attr('name') + g.appendID + ']').each(function(i, item){
        			var $checkbox = $(this);
        			if ($.inArray($checkbox.val(), values) != -1) {
        				g._setValue($checkbox, true);
        			} else {
        				g._setValue($checkbox, false);
        			}
        			$checkbox.trigger("change");
        		});
        	} else {
        		g.callbackContext["setValue"] = value;
        	}
        },
		
		_setValue : function($checkbox, checked) {
			var g = this;
			if (!checked) {
				$checkbox.attr('checked', false);
			} else {
				$checkbox.attr('checked', true);
			}
			g._setInputValue($checkbox);
		},
		
		//向隐藏域设置选中值
		_setInputValue : function($checkbox) {
			var g = this, $input = g.input, val = $input.val();
			if ($checkbox.attr('checked')) {
				if(!val || val == ""){
					$input.val($checkbox.val());
				} else {
					$input.val(val + g.symbol + $checkbox.val());
				}
			} else {
				if(val.indexOf(g.symbol) < 0){
					$input.val(val.replace($checkbox.val(), ""));
				} else {
					if(val.indexOf($checkbox.val()) == 0){
						$input.val(val.replace($checkbox.val() + g.symbol, ""));
					} else {
						$input.val(val.replace(g.symbol + $checkbox.val(), ""));
					}
				}
			}
		},
		
		setEnabled: function () {
			var g = this;
        	if (g.ajaxSuccessful) {
        		$('input:checkbox[name=' + g.input.attr('name') + g.appendID + ']').each(function(i, item){
        			var $checkbox = $(this);
        			$checkbox.attr('disabled', false);
        			g._getWraper($checkbox).removeClass('l-disabled');
        		});
        		this.options.disabled = false;
        	} else {
        		g.callbackContext["setEnabled"] = null;
        	}
        },
        
        setDisabled: function () {
        	var g = this;
        	if (g.ajaxSuccessful) {
        		$('input:checkbox[name=' + g.input.attr('name') + g.appendID + ']').each(function(i, item){
        			var $checkbox = $(this);
        			$checkbox.attr('disabled', true);
        			g._getWraper($checkbox).addClass('l-disabled');
        		});
        		this.options.disabled = true;
        	} else {
        		g.callbackContext["setDisabled"] = null;
        	}
        },
        
        //得到当前复选框的外面的div
        _getWraper: function ($checkbox){
        	return $checkbox.parent();
        },
        
        //得到当前复选框对应的超链接
        _getLink: function ($checkbox){
        	return $checkbox.siblings('a');
        },
		
		getValue : function() {
			return g.input.val();
		},
		
		updateStyle : function() {
			var g = this;
        	if (g.ajaxSuccessful) {
        		$('input:checkbox[name=' + g.input.attr('name') + g.appendID + ']').each(function(i, item){
        			var $checkbox = $(this);
        			if($checkbox.attr('checked')){
        				g._getLink($checkbox).addClass('l-checkbox-checked');
        			}else{
        				g._getLink($checkbox).removeClass('l-checkbox-checked');
        			}
        		});
        	} else {
        		g.callbackContext["updateStyle"] = null;
        	}
		}
	});
})(jQuery);