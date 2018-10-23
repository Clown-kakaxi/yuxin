/*!
 * 基于ExtJs的工具函数
 * @author	刘叶祥
 */
Ext.ns("Wlj.fn");
/**
 * 日期转字符串
 * @param {Date} 需要格式化的日期
 * @param {String} 格式化的格式，可选，默认值为Ymd
 * @return {String} 格式化后的日期字符串
 */
Wlj.fn.dateToStr	= function(date, format){
	if(!date){
		return null;
	}
	format = format||'Ymd';
	return Wlj.fn.format(date,format);
};
/**
 * 字符串转日期
 * @param {String} 需要转换日期字符串
 * @param {String} 格式化的格式，可选，默认值为Ymd
 * @param {Boolean} 是否对转换的日期进行合法性校验，默认值为false
 * @return {Date} 转换后的日期，如果日期不合法，返回null
 */
Wlj.fn.strToDate	= function(dateStr, format, strict){
	if(!dateStr){
		return null;
	}
	format = format||'Ymd';
	strict = strict||false;
	return Date.parseDate(dateStr,format,strict);
};
/**
 * 对指定类型的数据进行格式化。可格式化的数据包括日期，数字和字符串。关于此方法的说明分别见Ext.util.Format.date,
 * Ext.util.Format.number和String.format。
 * @param {Object} 需要格式化的值,日期：默认值为"Ymd";数值：默认值为"#,##0.##"。
 * @return {String} 格式化后的字符串。
 */
Wlj.fn.format= function(param) {
	var args = Array.prototype.slice.apply(arguments);
	if (Ext.isDate(param)){
		args[1] = args[1]||'Ymd';
	  return Ext.util.Format.date.apply(this, args);
	}else if (Ext.isNumber(param)){
		args[1] = args[1]||'0,000.00';
	  return Ext.util.Format.number.apply(this, args);
	}else{
	  return String.format.apply(this, arguments);
	}
};
/**
 * 字符串以xxx开头
 * @param {String} 原始字符串
 * @param {String} 开头字符串
 * @param {boolean} 是否忽略大小写
 */
Wlj.fn.startsWith = function(str, startStr, ignoreCase) {
	var t = this.d(str, startStr);
	if (t) {
		if (ignoreCase) {
			str = str.toLowerCase();
			startStr = startStr.toLowerCase();
		}
		t = str.lastIndexOf(startStr, 0) === 0;
	}
	return t;
};
/**
 * 字符串以xxx结尾
 * @param {String} 原始字符串
 * @param {String} 结尾字符串
 * @param {boolean} 是否忽略大小写
 */
Wlj.fn.endsWith = function(str, startStr, ignoreCase) {
	var t = d(str, startStr);
	if (t) {
		if (ignoreCase) {
			str = str.toLowerCase();
			startStr = startStr.toLowerCase();
		}
		t = str.indexOf(startStr, str.length - startStr.length) !== -1;
	}
	return t;
};

/**
 * 把对象转换成以字符串形式显示的值。
 * @param {Object} object 转换的对象。
 * @return {String} 字符串形式显示的值。
 */
Wlj.fn.toString = function(object) {
    return Object.prototype.toString.call(object);
};

/**
 * 显示控件
 * @param {Ext.Component/String} 组件或组件id
 */
Wlj.fn.show = function(component){
	if(!component){
		return false;
	}
	if(Ext.isString(component)){
		component = Ext.getCmp(component);
	}else if(!component instanceof Ext.Component){
		return false;
	}
	if(!component){
		return;
	}
	var labelEl = component.getEl().up('.x-form-item');
	if(labelEl){
		component.enable();   
		labelEl.setDisplayed( true );
	}else{
		component.show();
	}
},
/**
 * 隐藏控件
 * @param {Ext.Component/String} 组件或组件id
 * 
 */
Wlj.fn.hide = function(component){
	if(!component){
		return false;
	}
	if(Ext.isString(component)){
		component = Ext.getCmp(component);
	}else if(!component instanceof Ext.Component){
		return false;
	}
	if(!component){
		return;
	}
	var labelEl = component.getEl().up('.x-form-item');
	if(labelEl){
		component.disable();   
		labelEl.setDisplayed( false );
	}else{
		component.hide();
	}
},

/**
 * 遍历数组或对象的各个元素。
 * 
 * @param {Array/Object} 遍历的数组或对象。
 * @param {Function} 每遍历一个元素所执行的回调方法。对于数组传递的参数为值和索引，对于对象传递的参数为名称和值。
 * 如果函数返回false，将中断遍历。
 * @param {Boolean} [reverse] 是否倒序遍历，仅适用于数组，默认为false。
 * @return {Boolean} true遍历完成，否则返回索引号（数组）或false（对象）。
 */
Wlj.fn.each = function(data, fn, reverse) {
    if (Wlj.fn.toString(data) === '[object Array]') {
      var i, j = data.length;
      if (reverse !== true) {
        for (i = 0; i < j; i++) {
          if (fn(data[i], i) === false) {
            return i;
          }
        }
      } else {
        for (i = j - 1; i > -1; i--) {
          if (fn(data[i], i) === false) {
            return i;
          }
        }
      }
    } else {
      var property;
      for (property in data) {
        if (data.hasOwnProperty(property)) {
          if (fn(property, data[property]) === false) {
            return false;
          }
        }
      }
    }
    return true;
};

/**
 * 判断指定条目在数组中的位置。
 * @param {Array} 数组对象。
 * @param {Object} 需要判断的条目。
 * @return {Number} 条目在数组中的位置。第1个为0，第2个为1，依此类推。-1表示没有找到。
 */
Wlj.fn.indexOf = function(array, item) {
	if (!array)
      return -1;
    var i, j;
    j = array.length;
    for (i = 0; i < j; i++)
      if (array[i] === item)
        return i;
    return -1;
};

/**
 * 获取指定控件及其所包含控件的值组成的对象，对象中每个值的名称为指定控件的itemId，控件识别的优先级为：itemId>id>name。。
 * 如果控件下存在重复itemId的控件，则只返回第一个控件值，其余重名的控件将被忽略。
 * Example:
 *     var jsonObject1 = Wlj.getValue(app.window1); //获取window1下所有控件的值组成的对象
 *     var jsonObject2 = Wlj.getValue([text1, date1]); //获取text1和date1的值组成的对象
 *
 * @param {Component/Component[]} 需要获取值的组件对象或组件对象列表。
 * @param {String/String[]} [itemIds] 需要获取值的组件itemId名称或名称列表，如果值为空则返回所有控件的值。
 * @param {Boolean} [getFileName] 是否获取文件控件的文件名称，默认为false。
 * @return {Object} 获取的控件值组成的对象。
 */
Wlj.fn.getValue = function(components, itemIds) {
	var result = {};
    if (!components)
      return result;

    function query(item) {
  		var itemId = item.itemId||item.id||item.name;
      	if (itemId && item.getValue && (!itemIds || Wlj.indexOf(itemIds, itemId) != -1) &&
			!result.hasOwnProperty(itemId)) {
			result[itemId] = item.getValue();
			if(item.xtype=='datefield'&&item.useRawValue==true){
				result[itemId] = item.getRawValue();
			}
      	}
      	return false;
    }
    if (!Ext.isArray(components))
      	components = [components];
    if (itemIds && !Ext.isArray(itemIds))
      	itemIds = [itemIds];
    Wlj.fn.each(components, function(comp) {
      	comp.findBy(query)
    });
    return result;
};

/**
 * 重置指定控件及其所包含控件的值，控件识别的优先级为：itemId>id>name。
 * 如果控件下存在重复itemId的控件，则只返回第一个控件值，其余重名的控件将被忽略。
 * @param {Ext.Component/Ext.Component[]} 需要获取值的组件对象或组件对象列表。
 * @param {String/String[]} [itemIds] 需要获取值的组件itemId名称或名称列表，如果值为空则返回所有控件的值。
 * @param {Boolean} [getFileName] 是否获取文件控件的文件名称，默认为false。
 * @return {Object} 获取的控件值组成的对象。
 */
Wlj.fn.reset = function(components, itemIds){
	if (!components)
      return;
      
    function query(item) {
  		var itemId = item.itemId||item.id||item.name;
      	if (itemId && item.setValue && (!itemIds || Wlj.fn.indexOf(itemIds, itemId) != -1)) {
			if(item.defaultValue){
				item.setValue(item.defaultValue);	
			}else{
				item.reset();					
			}
      	}
    }
    if (!Ext.isArray(components))
      	components = [components];
    if (itemIds && !Ext.isArray(itemIds))
      	itemIds = [itemIds];
    Wlj.fn.each(components, function(comp) {
      	comp.findBy(query)
    });
};

/**
 * 查找指定容器组件下itemId或id或name与value相等的控件
 * @param {Array/Ext.Component} 容器组件
 * @param {String} itemId或id或name的值
 */
Wlj.fn.find = function(components,value){
	var cmp = null;
	function query(item){
		var itemId = item.itemId||item.id||item.name;
      	if(itemId == value){
			cmp = item;	
		}
	}
	if (!Ext.isArray(components)){
      	components = [components];
	}
	Wlj.fn.each(components, function(comp) {
		if(!cmp){
	      	comp.findBy(query);
		}
    });
    return cmp;
};

/**
 * 获取唯一的表单对象，用于通过常规表单提交的方式提交参数。
 * @param {Object} [params] 参数对象。
 * @param {Boolean} [isUpload] 是否使用multipart/form-data编码，该编码方式通常用于上传文件。默认为false。
 * @return {HTMLForm} 表单dom对象。
 */
Wlj.fn.getForm = function(params, isUpload) {
	var el, form = Wlj.fn.defaultForm;
	if (form) {
		while (form.childNodes.length !== 0) {
			form.removeChild(form.childNodes[0]);
		}
	} else {
		form = document.createElement('FORM');
		Wlj.fn.defaultForm = form;
		document.body.appendChild(form);
	}
	if (params) {
		for (var key in params) {
			var value = params[key];
			el = document.createElement('input');
			el.setAttribute('id', key);
			el.setAttribute('name', key);
			el.setAttribute('type', 'hidden');
			if (Ext.isArray(value) || Ext.isObject(value)){
				value = Ext.encode(value);
			}
			else if (Ext.isDate(value)){
				value = Ext.dateToStr(value);
			}
			el.setAttribute('value', Ext.isEmpty(value) ? '' : value);
			form.appendChild(el);
		}
	}
	if (isUpload){
		form.encoding = "multipart/form-data";
	}
	else{
		form.encoding = "application/x-www-form-urlencoded";
	}
	return form;
};
/**
 * 发送Ajax请求
 * @param {Object} o ajax请求的配置信息
 * @param {Ext.Component} o.out out为容器控件，如panel,
 * @param {String} o.url 请求地址,
 * 取该容器下所有表单控件的值作为请求参数，参数名称为表单控件的(itemId>id>name)，如果参数名称与params相同，优先取params
 * 
 */
Wlj.fn.request	= function(o){
	if(o.out){
		var params = Wlj.getValue(o.out);
		Ext.applyIf(o.params,params)
	}
	Ext.applyIf(o,{
		method 	: 'POST'
	});
	Ext.Ajax.request(o);
};

/**
 * 表单提交
 * @param {Object} o 表单提交的配置信息
 * @param {Ext.FormPanel} [o.formpanel] 表单面板
 * @param {String} o.url 提交的URL地址。
 * @param {Object} [o.params] 参数对象。
 * @param {String} [o.target] 指定在何处打开url，可以为预设值或框架。默认为'_blank'。
 * @param {String} [o.method] 提交使用的方法。默认为'POST'。
 * @param {Boolean} [o.isUpload] 是否使用multipart/form-data编码，该编码方式通常用于上传文件。默认为false。
 */
Wlj.fn.submit = function(o){
	if(!o||!o.url){
		return;
	}
	if(o.out){
		var params = Wlj.fn.getValue(o.out);
		Ext.applyIf(o.params,params)
	}
	var formPanel = o.formpanel
	if(formPanel instanceof Ext.FormPanel){
		Ext.applyIf(o,{
			waitTitle : '请稍候',
			waitMsg : '正在保存......',
			method 	: 'POST',
			scope 	: formPanel
		});
		if(formPanel.form.isValid()){
			formPanel.form.submit(o);
		}
	}else{
		var form = Wlj.fn.getForm(o.params, o.isUpload);
	    form.action = o.url;
	    form.method = o.method || 'POST';
	    if(o.target){
	    	form.target = o.target || '_blank';
	    }
	    form.submit();
	}
};
/**
 * 等待进度条
 * @param {String} 等待的提升信息，正在{msg}，请稍后......
 */
Wlj.fn.progress = function(msg){
	Ext.MessageBox.show({
		msg : '正在'+msg+'，请稍后......',
		title	: '系统消息',
		width : 300,
		wait : true,
		progress : true,
		closable : true,
		waitConfig : {
			interval : 200
		},
		icon : Ext.Msg.INFO
	});
};
/**
 * 获取表格选中的记录
 * @param {Ext.grid.GridPanel} grid 表格
 * @param {String} msg 没有选择时的提升信息
 * @return {Ext.data.Record/Boolean} record/records/false。record:有选中的记录行;没有选中:false
 */
Wlj.fn.getSelected	= function(grid,msg,multiSelect){
	if(!grid){
		return false;
	}
	if(Ext.isEmpty(multiSelect)||multiSelect==false){
		//取得当前选择的行所对应的数据
		var record = grid.getSelectionModel().getSelected();
		if(Ext.isEmpty(record)){
			parent.Ext.Msg.alert('系统消息',msg);
			return false;
		}else{
			return record;
		}
	}else if(multiSelect== true){
		//取得当前选择的行所对应的数据
		var records=grid.getSelectionModel().getSelections();
		if(Ext.isEmpty(records)){
			parent.Ext.Msg.alert('系统消息',msg);
			return false;
		}else{
			return records;
		}
	}
};
/**
 * 初始化下拉框的值
 * @param {Ext.form.ComboBox} 下拉框控件
 * @param {String/Number} 控件需要设置的值(可选)
 * @param {Number} 下拉框数据集store中第N条数据的序号为index，如果第二个参数value为空，则设置下拉框的值为该序号对应的数据(可选)
 * @param {Function} 回调 函数(可选)
 */
Wlj.fn.initCombo = function(component,value,index,callback){
	if(!component){
		return false;
	}
	var store = component.store;
	function setValue(cmp,v,idx){
		if(typeof(idx)=='number'){
			idx = idx<=store.getCount()?idx:store.getCount();
			cmp.setValue(store.getCount()>0?store.getAt(idx).get(cmp.valueField):'');
		}else if(typeof(v)!='undefined'){
			cmp.setValue(v);		
		}else{
			cmp.setValue(store.getCount()>0?store.getAt(0).get(cmp.valueField):'');
		}
		if(callback){
			callback.call(component);
		}
	}
	if(store.getCount()==0){
		store.load({
			callback : function(){
				//初始值设置为数据集里的第一条数据
				setValue(component,value,index);
			}
		});
	}else {//有数据则无需加载，直接设置值
		setValue(component,value,index);
	}
};
/**
 * 在带复选框的树下，从指定节点开始往下获取已选中的节点的id
 * @param {Ext.tree.TreePanel/Ext.tree.TreeNode} 树/树节点
 * return {Array} checkedNodeIds 所有已选中节点的id组成的数组
 */
Wlj.fn.getCheckedNodeIds = function(component){
	var checkedNodeIds = [],root = null;
	if(component instanceof Ext.tree.TreePanel){
		root = component.getRootNode();
	}else if(component instanceof Ext.tree.TreeNode){
		root = componen;
	}else{
		return checkedNodeIds;
	}
	//递归 获取已选择的节点
	function checkNode(node){
		if(node.attributes.checked){
			checkedNodeIds.push(node.id);
		}
		if(!node.isLeaf()){//目录节点
			for (var i = 0; i < node.childNodes.length; i++) {
				checkNode(node.childNodes[i]);
			}
		}
	}
	checkNode(root);
	return checkedNodeIds;
};
/**
 * 延时执行
 * @param {Function} fn 回调函数
 * @param {int} time 延迟的毫秒数
 * @param {Object}  [scope] 作用域
 * @param {Array} [args] 回调函数的参数
 */
Wlj.fn.delay = function(fn, time, scope, args){
	fn = fn||Ext.emptyFn;
	time = time||300;
	var task = new Ext.util.DelayedTask(fn, scope, args);
	task.delay(time);
}
/**
 * 生成数据集
 * @param {Object} o 配置信息
 * @param {boolean} o.codeType 码表类型
 * @param {String} o.url 请求地址
 * @param {Array} o.fields 字段映射，例：[{name:'code'},{name:'desc'}]
 * @param {String} o.root 取数字段,默认为JSON
 * @param {String} o.totalProperty 总数据条数字段,默认为TOTAL
 */
Wlj.fn.getJsonStore = function(o){
	o = o||{};
	if(o.codeType){
		o.url = basepath + '/lookup.json?name='+o.codeType
	}
	var cfg = {
		codeType		: null,
		autoLoad 		: false,
		url				: '',
		root 			: "JSON",
		totalProperty 	: "TOTAL",
		remoteSort 		: true,
		fields 			: ["key","value"],
		fields			: [
			{name:'key',mapping:'key'},
			{name:'value',mapping:'value'}
		]
	};
	Ext.apply(cfg,o);
	var mystore =  new Ext.data.JsonStore(cfg);
	return mystore;
};