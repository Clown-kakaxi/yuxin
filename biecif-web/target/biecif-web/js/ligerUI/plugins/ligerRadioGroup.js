/**
* jQuery ligerUI 1.1.9
* 
* http://ligerui.com
*  
* Author tanxu 2013 [ tanxu@yuchengtech.com ] 
* 
*/

(function ($) {

    $.fn.ligerRadioGroup = function () {
        return $.ligerui.run.call(this, "ligerRadioGroup", arguments);
    };

    $.fn.ligerGetRadioGroupManager = function () {
        return $.ligerui.run.call(this, "ligerGetRadioGroupManager", arguments);
    };

    $.ligerDefaults.RadioGroup = { disabled: false };

    $.ligerMethos.RadioGroup = {
    		sepHtml: "&nbsp;",		// radio标签之间的间隔符
    		ajaxType: "get",		// 默认的ajax请求方式
    		appendID: "_radioGroup",// 每个radio的name的名称后缀
    		disabled: false,		// 是否可选
    		maxColumn: 2,			// 单选按钮每行的最大排列数
    		defaultValue: "",		// 默认选中的radio值
    		data: null,				// 本地数据源，键值的形式为[{id:"",text:""},{……}]
    		ajaxSuccessful : false,	// 是否成功载入
    		callbackContext : {}, 	// 回调函数上下文
    		url: null              	// 通过URL异步读取数据源(需返回JSON){id:"",text:""},{……}
    };

    $.ligerui.controls.RadioGroup = function (element, options) {
        $.ligerui.controls.RadioGroup.base.constructor.call(this, element, options);
    };
    
    $.ligerui.controls.RadioGroup.ligerExtend($.ligerui.controls.Input, {
        __getType: function () {
            return 'RadioGroup';
        },
        __idPrev: function () {
            return 'RadioGroup';
        },
        _extendMethods: function () {
            return $.ligerMethos.RadioGroup;
        },
        _render: function () {
        	var g = this, p = this.options;
            g.input = $(this.element);
            if(p.data){
            	g.data = p.data;
            	g._renderByData(p.data);
            } else if(p.url) {
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
        },
        
        //遍历json数据做渲染
        _renderByData: function(data){
        	var g = this, p = this.options, maxColumn = p.maxColumn | g.maxColumn, itemLength = data.length;
            $(data).each(function (i, item){
            	g._renderLabelAndRadio(item.id, item.text);
            	if((i+1)%maxColumn==0 && (i+1) != itemLength){
            		g.input.parent().append("<br/>");
            	}
            });
            p.defaultValue && g.setValue(p.defaultValue);
            g.ajaxSuccessful = true;
        },
        
        //根据参数值渲染控件组并添加相应的事件
        _renderLabelAndRadio: function(id, text){
        	var g = this, p = this.options, $input = g.input;
        	var radio = $('<input type="radio" class="l-hidden" style="resize: none;"/>');
        	radio.attr("value", id).attr("name", $input.attr("name") + g.appendID);
        	var wrapper = radio.wrap('<div class="l-radio-wrapper"></div>').parent();
        	var label = $('<label>' + text + '</label>');
            var link = $('<a class="l-radio"></a>');
            wrapper.prepend(link);
            $input.parent().append(wrapper).append(label).append(g.sepHtml);
            radio.click(function() {
            	if(!p.disabled && !this.checked){
            		$input.val(radio.val());
            	}
            });
            radio.change(function () {
                if (this.checked) {
                	link.addClass('l-radio-checked');
                }
                else {
                	link.removeClass('l-radio-checked');
                }
                return true;
            });
			link.click(function() {
				if (!p.disabled)g._doclick(radio);
			});
			wrapper.hover(function() {
				if (!p.disabled)
					$(this).addClass('l-over');
			}, function() {
				$(this).removeClass('l-over');
			});
			radio.checked && link.addClass('l-radio-checked');
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
            var g = this;
            if (g.ajaxSuccessful) {
            	$('input:radio[name=' + g.input.attr('name') + g.appendID + '][value=' + value + ']').each(function(i, item){
            		g._doclick($(this));
            	});
            } else {
            	g.callbackContext["setValue"] = value;
            }
        },
        
        getValue: function () {
            return this.input.val();
        },
        
        setEnabled: function () {
        	 var g = this;
             if (g.ajaxSuccessful) {
            	 $('input:radio[name=' + g.input.attr('name') + g.appendID + ']').each(function(i, item){
            		 var $radio = $(this);
            		 $radio.attr('disabled', false);
            		 g._getWraper($radio).removeClass('l-disabled');
            	 });
            	 this.options.disabled = false;
             } else {
             	g.callbackContext["setEnabled"] = null;
             }
        },
        
        setDisabled: function () {
        	var g = this;
            if (g.ajaxSuccessful) {
            	$('input:radio[name=' + g.input.attr('name') + g.appendID + ']').each(function(i, item){
            		var $radio = $(this);
            		$radio.attr('disabled', true);
            		g._getWraper($radio).addClass('l-disabled');
            	});
            	this.options.disabled = true;
            } else {
             	g.callbackContext["setDisabled"] = null;
            }
        },
        
        updateStyle: function () {
        	var g = this;
            if (g.ajaxSuccessful) {
            	$('input:radio[name=' + g.input.attr('name') + g.appendID + ']').each(function(i, item){
            		var $radio = $(this);
            		if($radio.attr('checked')){
            			g._getLink($radio).addClass('l-checkbox-checked');
            		}else{
            			g._getLink($radio).removeClass('l-checkbox-checked');
            		}
            	});
            } else {
             	g.callbackContext["updateStyle"] = null;
            }
        },
        
        //得到当前复选框的外面的div
        _getWraper: function ($radio){
        	return $radio.parent();
        },
        
        //得到当前复选框对应的超链接
        _getLink: function ($radio){
        	return $radio.siblings('a');
        },
        
        //单选按钮上绑定的单击事件
        _doclick: function ($radio){
//            if ($radio.attr('disabled')) { return false; }
            $radio.trigger('click').trigger('change');
            $("input:radio[name=" + $radio.attr('name') + ']').not($radio).trigger('change');
            return false;
        }
    });
})(jQuery);