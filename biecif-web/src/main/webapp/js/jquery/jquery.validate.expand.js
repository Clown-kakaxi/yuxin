/**
 * jQuery合法性校验扩展
 * 
 * @author xuguangyuan xugy@yuchengtech.com
 */
// JAVA特殊符号验证
jQuery.validator.addMethod("specificSymbeol", function(value, element) {
	var specificSymbeol = /^[\u4e00-\u9fa5\da-zA-Z\-\_]+$/;
	return this.optional(element) || (specificSymbeol.test(value));
}, "该字段不能输入特殊符号.");

// JAVA标准变量名验证
jQuery.validator.addMethod("checkAttrName", function(value, element) {
	var attrName = /^[a-zA-Z_]\w*$/;
	return this.optional(element) || (attrName.test(value));
}, "请输入由a-z,A-Z,_开头的合法变量名.");

// 手机号码验证
jQuery.validator.addMethod("mobile", function(value, element) {
	var length = value.length;
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误");

// 电话号码验证
jQuery.validator.addMethod("phone", function(value, element) {
	var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	return this.optional(element) || (tel.test(value));
}, "电话号码格式错误");

// 某个值大于另一个值的验证, 例如结束日期大于开始日期
jQuery.validator.addMethod("greaterThan", function(value, element, params) {
	if (value == '' || value == null) {
		// edit by caiqy,have no endtime , return
		return true;
	}
	var ele = $("[name=" + params + "]");
	if (ele.is(":text") && ele.attr("ltype") == "date") {
		// edit by caiqy, ps:this is not a best way ,0.0
		var tdate;
		var fdate;
		if (typeof value == 'string' 
			  && value.indexOf('-') != -1 && value.length >= 10) {
			// if format as 'yyyy-MM-dd' or 'yyyy-mm-dd hh:mm:ss'
			tdate = new Date(new Number(value.substr(0, 4)), new Number(value
					.substr(5, 2)) - 1, new Number(value.substr(8, 2)))
					.valueOf()
					/ (60 * 60 * 24 * 1000);
		} else {
			tdate = new Date(value).valueOf() / (60 * 60 * 24 * 1000);
		}
		if (ele.val().indexOf('-' != -1 && ele.val().length >= 10)) {
			// if format as 'yyyy-MM-dd' or 'yyyy-mm-dd hh:mm:ss'
			fdate = new Date(new Number(ele.val().substr(0, 4)), new Number(ele
					.val().substr(5, 2)) - 1,
					new Number(ele.val().substr(8, 2))).valueOf()
					/ (60 * 60 * 24 * 1000);
		} else {
			fdate = new Date(ele.val()).valueOf() / (60 * 60 * 24 * 1000);
		}
		// edit end
		//  old code :
		//		var tdate = new Date(value).valueOf() / (60 * 60 * 24 * 1000);
		//		var fdate = new Date(ele.val()).valueOf() / (60 * 60 * 24 * 1000);

		return tdate - fdate > 0 ? true : false;
	} else {
		return value > ele.val() ? true : false;
	}
}, "Data structure error.");

// 大于当前系统时间
jQuery.validator.addMethod("greaterThanNow", function(value, element, params) {
	if (!value) return true;
	if (params) {
		var tdate = new Date();
		var fdate = new Date(tdate.getFullYear(), tdate.getMonth(), tdate.getDate()).valueOf() / (60 * 60 * 24 * 1000);
		if (value.indexOf('-') != -1 && value.length >= 10) {
			tdate = new Date(new Number(value.substr(0, 4)), new Number(value.substr(5, 2)) - 1, new Number(value.substr(8, 2)))
					.valueOf() / (60 * 60 * 24 * 1000);
		} else {
			tdate = new Date(value).valueOf() / (60 * 60 * 24 * 1000);
		}
		return tdate - fdate >= 0 ? true : false;
	}
}, "日期必须大于当前日期.");

// 合法时间格式验证
jQuery.validator.addMethod("time", function(value, element, params) {
	if (params) {
		var exp = new RegExp(/^(([0-1]\d)|2[0-3]):([0-5]\d):([0-5]\d)$/);
		return exp.test(value);
	}
}, "Please input the legal time.");

// 克隆表达式
jQuery.validator.addMethod("cron", function(value, element, params) {
	var cronParams = value.split(" ");
	if (cronParams.length < 6 || cronParams.length > 7) {
		return false;
	}
	// CronTrigger cronTrigger = new CronTrigger();
	// cronTrigger.setvalue( value );
	if (cronParams[3] == "?" || cronParams[5] == "?") {
		// Check seconds param
		if (!checkSecondsField(cronParams[0])) {
			return false;
		}
		// Check minutes param
		if (!checkMinutesField(cronParams[1])) {
			return false;
		}
		// Check hours param
		if (!checkHoursField(cronParams[2])) {
			return false;
		}
		// Check day-of-month param
		if (!checkDayOfMonthField(cronParams[3])) {
			return false;
		}
		// Check months param
		if (!checkMonthsField(cronParams[4])) {
			return false;
		}
		// Check day-of-week param
		if (!checkDayOfWeekField(cronParams[5])) {
			return false;
		}
		// Check year param
		if (cronParams.length == 7) {
			if (!checkYearField(cronParams[6])) {
				return false;
			}
		}
		return true;
	} else {
		return false;
	}
}, "Please input the legal cron.");

function checkSecondsField(secondsField) {
	return checkField(secondsField, 0, 59);
}
function checkField(secondsField, minimal, maximal) {
	if (secondsField.indexOf("-") > -1) {
		var startValue = secondsField.substring(0, secondsField.indexOf("-"));
		var endValue = secondsField.substring(secondsField.indexOf("-") + 1);
		if (!(checkIntValue(startValue, minimal, maximal, true) && checkIntValue(
				endValue, minimal, maximal, true))) {
			return false;
		}
		try {
			var startVal = parseInt(startValue, 10);
			var endVal = parseInt(endValue, 10);
			return endVal > startVal;
		} catch (e) {
			return false;
		}
	} else if (secondsField.indexOf(",") > -1) {
		return checkListField(secondsField, minimal, maximal);
	} else if (secondsField.indexOf("/") > -1) {
		return checkIncrementField(secondsField, minimal, maximal);
	} else if (secondsField.indexOf("*") != -1) {
		return true;
	} else {
		return checkIntValue(secondsField, minimal, maximal);
	}
}

function checkIntValue(value, minimal, maximal, checkExtremity) {
	try {
		var val = parseInt(value, 10);
		// 判断是否为整数
		if (value == val) {
			if (checkExtremity) {
				if (val < minimal || val > maximal) {
					return false;
				}
			}
			return true;
		}
		return false;
	} catch (e) {
		return false;
	}
}

function checkMinutesField(minutesField) {
	return checkField(minutesField, 0, 59);
}

function checkHoursField(hoursField) {
	return checkField(hoursField, 0, 23);
}

function checkDayOfMonthField(dayOfMonthField) {
	if (dayOfMonthField == "?") {
		return true;
	}
	if (dayOfMonthField.indexOf("L") >= 0) {
		return checkFieldWithLetter(dayOfMonthField, "L", 1, 7, -1, -1);
	} else if (dayOfMonthField.indexOf("W") >= 0) {
		return checkFieldWithLetter(dayOfMonthField, "W", 1, 31, -1, -1);
	} else if (dayOfMonthField.indexOf("C") >= 0) {
		return checkFieldWithLetter(dayOfMonthField, "C", 1, 31, -1, -1);
	} else {
		return checkField(dayOfMonthField, 1, 31);
	}
}

function checkMonthsField(monthsField) {
	/*
	 * monthsField = StringUtils.replace( monthsField, "JAN", "1" ); monthsField =
	 * StringUtils.replace( monthsField, "FEB", "2" ); monthsField =
	 * StringUtils.replace( monthsField, "MAR", "3" ); monthsField =
	 * StringUtils.replace( monthsField, "APR", "4" ); monthsField =
	 * StringUtils.replace( monthsField, "MAY", "5" ); monthsField =
	 * StringUtils.replace( monthsField, "JUN", "6" ); monthsField =
	 * StringUtils.replace( monthsField, "JUL", "7" ); monthsField =
	 * StringUtils.replace( monthsField, "AUG", "8" ); monthsField =
	 * StringUtils.replace( monthsField, "SEP", "9" ); monthsField =
	 * StringUtils.replace( monthsField, "OCT", "10" ); monthsField =
	 * StringUtils.replace( monthsField, "NOV", "11" ); monthsField =
	 * StringUtils.replace( monthsField, "DEC", "12" );
	 */

	monthsField.replace("JAN", "1");
	monthsField.replace("FEB", "2");
	monthsField.replace("MAR", "3");
	monthsField.replace("APR", "4");
	monthsField.replace("MAY", "5");
	monthsField.replace("JUN", "6");
	monthsField.replace("JUL", "7");
	monthsField.replace("AUG", "8");
	monthsField.replace("SEP", "9");
	monthsField.replace("OCT", "10");
	monthsField.replace("NOV", "11");
	monthsField.replace("DEC", "12");

	return checkField(monthsField, 1, 31);
}

function checkDayOfWeekField(dayOfWeekField) {
	/*
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "SUN", "1" );
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "MON", "2" );
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "TUE", "3" );
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "WED", "4" );
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "THU", "5" );
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "FRI", "6" );
	 * dayOfWeekField = StringUtils.replace( dayOfWeekField, "SAT", "7" );
	 */

	dayOfWeekField.replace("SUN", "1");
	dayOfWeekField.replace("MON", "2");
	dayOfWeekField.replace("TUE", "3");
	dayOfWeekField.replace("WED", "4");
	dayOfWeekField.replace("THU", "5");
	dayOfWeekField.replace("FRI", "6");
	dayOfWeekField.replace("SAT", "7");

	if (dayOfWeekField == "?") {
		return true;
	}

	if (dayOfWeekField.indexOf("L") >= 0) {
		return checkFieldWithLetter(dayOfWeekField, "L", 1, 7, -1, -1);
	} else if (dayOfWeekField.indexOf("C") >= 0) {
		return checkFieldWithLetter(dayOfWeekField, "C", 1, 7, -1, -1);
	} else if (dayOfWeekField.indexOf("#") >= 0) {
		return checkFieldWithLetter(dayOfWeekField, "#", 1, 7, 1, 5);
	} else {
		return checkField(dayOfWeekField, 1, 7);
	}
}

function checkYearField(yearField) {
	return checkField(yearField, 1970, 2099);
}

function checkFieldWithLetter(value, letter, minimalBefore, maximalBefore,
		minimalAfter, maximalAfter) {
	var canBeAlone = false;
	var canHaveIntBefore = false;
	var canHaveIntAfter = false;
	var mustHaveIntBefore = false;
	var mustHaveIntAfter = false;

	if (letter == "L") {
		canBeAlone = true;
		canHaveIntBefore = true;
		canHaveIntAfter = false;
		mustHaveIntBefore = false;
		mustHaveIntAfter = false;
	}
	if (letter == "W" || letter == "C") {
		canBeAlone = false;
		canHaveIntBefore = true;
		canHaveIntAfter = false;
		mustHaveIntBefore = true;
		mustHaveIntAfter = false;
	}
	if (letter == "#") {
		canBeAlone = false;
		canHaveIntBefore = true;
		canHaveIntAfter = true;
		mustHaveIntBefore = true;
		mustHaveIntAfter = true;
	}

	var beforeLetter = "";
	var afterLetter = "";

	if (value.indexOf(letter) >= 0) {
		beforeLetter = value.substring(0, value.indexOf(letter));
	}

	if (!value.endsWith(letter)) {
		afterLetter = value.substring(value.indexOf(letter) + 1);
	}

	if (value.indexOf(letter) >= 0) {
		if (letter == value) {
			return canBeAlone;
		}

		if (canHaveIntBefore) {
			if (mustHaveIntBefore && beforeLetter.length == 0) {
				return false;
			}

			if (!checkIntValue(beforeLetter, minimalBefore, maximalBefore, true)) {
				return false;
			}
		} else {
			if (beforeLetter.length > 0) {
				return false;
			}
		}
		if (canHaveIntAfter) {
			if (mustHaveIntAfter && afterLetter.length == 0) {
				return false;
			}
			if (!checkIntValue(afterLetter, minimalAfter, maximalAfter, true)) {
				return false;
			}
		} else {
			if (afterLetter.length > 0) {
				return false;
			}
		}
	}
	return true;
}
/*
 * function checkIntValue(value, minimal, maximal) { return checkIntValue(value,
 * minimal, maximal, true); }
 */
function checkIncrementField(value, minimal, maximal) {
	var start = value.substring(0, value.indexOf("/"));
	var increment = value.substring(value.indexOf("/") + 1);
	if (!("*" == start)) {
		return checkIntValue(start, minimal, maximal, true)
				&& checkIntValue(increment, minimal, maximal, false);
	} else {
		return checkIntValue(increment, minimal, maximal, true);
	}
}
function checkListField(value, minimal, maximal) {
	var st = value.split(",");

	var values = new Array(st.length);

	for ( var j = 0; j < st.length; j++) {
		values[j] = st[j];
	}

	var previousValue = -1;

	for ( var i = 0; i < values.length; i++) {
		var currentValue = values[i];

		if (!checkIntValue(currentValue, minimal, maximal, true)) {
			return false;
		}

		try {
			var val = parseInt(currentValue, 10);

			if (val <= previousValue) {
				return false;
			} else {
				previousValue = val;
			}
		} catch (e) {
			// we have always an int
		}
	}

	return true;
}
/**
 * 身份证号码验证
 * 
 */
function isIdCardNo(num) {

	var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1);
	var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
			"2");
	var varArray = new Array();
	var intValue;
	var lngProduct = 0;
	var intCheckDigit;
	var intStrLen = num.length;
	var idNumber = num;
	// initialize
	if ((intStrLen != 15) && (intStrLen != 18)) {
		return false;
	}
	// check and set value
	for ( var i = 0; i < intStrLen; i++) {
		varArray[i] = idNumber.charAt(i);
		if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
			return false;
		} else if (i < 17) {
			varArray[i] = varArray[i] * factorArr[i];
		}
	}

	if (intStrLen == 18) {
		// check date
		var date8 = idNumber.substring(6, 14);
		if (isDate8(date8) == false) {
			return false;
		}
		// calculate the sum of the products
		for ( var i = 0; i < 17; i++) {
			lngProduct = lngProduct + varArray[i];
		}
		// calculate the check digit
		intCheckDigit = parityBit[lngProduct % 11];
		// check last digit
		if (varArray[17] != intCheckDigit) {
			return false;
		}
	} else { // length is 15
		// check date
		var date6 = idNumber.substring(6, 12);
		if (isDate6(date6) == false) {

			return false;
		}
	}
	return true;

}
/**
 * 判断是否为“YYYYMM”式的时期
 * 
 */
function isDate6(sDate) {
	if (!/^[0-9]{6}$/.test(sDate)) {
		return false;
	}
	var year, month, day;
	year = sDate.substring(0, 4);
	month = sDate.substring(4, 6);
	if (year < 1700 || year > 2500)
		return false;
	if (month < 1 || month > 12)
		return false;
	return true;
}
/**
 * 判断是否为“YYYYMMDD”式的时期
 * 
 */
function isDate8(sDate) {
	if (!/^[0-9]{8}$/.test(sDate)) {
		return false;
	}
	var year, month, day;
	year = sDate.substring(0, 4);
	month = sDate.substring(4, 6);
	day = sDate.substring(6, 8);
	var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	if (year < 1700 || year > 2500)
		return false;
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
		iaMonthDays[1] = 29;
	if (month < 1 || month > 12)
		return false;
	if (day < 1 || day > iaMonthDays[month - 1])
		return false;
	return true;
}
// 身份证号码验证
jQuery.validator.addMethod("idcardno", function(value, element) {
	return this.optional(element) || isIdCardNo(value);
}, "请正确输入身份证号码");
// 汉字
jQuery.validator.addMethod("chcharacter", function(value, element) {
	var tel = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (tel.test(value));
}, "请输入汉字");

// 字符最小长度验证（一个中文字符长度为2）
jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length = length + 2;
		}
	}
	return this.optional(element) || (length >= param);
}, $.validator.format("长度不能小于{0}!"));

// 字符最大长度验证（一个中文字符长度为2）
jQuery.validator.addMethod("maxlength", function(value, element, param) {
//	var length = value.length;
//	for ( var i = 0; i < value.length; i++) {
//		if (value.charCodeAt(i) > 127) {
//			length = length + 2;
//		}
//	}
	var length = value.match(/[^ -~]/g) == null ? value.length : value.length + value.match(/[^ -~]/g).length ; 
	//alert(length);
	return this.optional(element) || (length <= param);
}, $.validator.format("长度不能大于{0}!"));

// 字符验证
jQuery.validator.addMethod("string", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "不允许包含特殊符号!");
// 必须以特定字符串开头验证
jQuery.validator.addMethod("begin", function(value, element, param) {
	var begin = new RegExp("^" + param);
	return this.optional(element) || (begin.test(value));
}, $.validator.format("必须以 {0} 开头!"));

// 验证两次输入值是否不相同
jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	return value != $(param).val();
}, $.validator.format("两次输入不能相同!"));

// 验证值不允许与特定值等于
jQuery.validator.addMethod("notEqual", function(value, element, param) {
	return value != param;
}, $.validator.format("输入值不允许为{0}!"));

// 验证值必须大于特定值(不能等于)
jQuery.validator.addMethod("gt", function(value, element, param) {
	return value > param;
}, $.validator.format("输入值必须大于{0}!"));

//验证值必须大于特定值(不能等于)
jQuery.validator.addMethod("ip", function(value, element, param) {
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g ;//匹配IP地址的正则表达式 
	if(re.test(value)) 
	{ 
	  if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true; 
	} 
	return false; 

}, $.validator.format("ip地址不符合规范!"));
