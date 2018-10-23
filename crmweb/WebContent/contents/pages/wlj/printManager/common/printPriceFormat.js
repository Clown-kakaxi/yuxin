/*
 * 实时监听动拨金额1,2  ------>动拨总金额
 */
function amountAgree(){
	var agree1F = document.getElementById('limit1Agree').value;
	var agree2F = document.getElementById('limit2Agree').value;
	var agree1 = Number(strCut(agree1F));
	var agree2 = Number(strCut(agree2F));
	var LIMIT1N = Number(LIMIT1) * 10;
	var LIMIT2N = Number(LIMIT2) * 10;
	if(LIMIT1N < agree1){
		alert("动拨金额1不能大于核准金额1");
		document.getElementById('limit1Agree').value = '';
		document.getElementById('amountAgree').value = '';		
	}else if(LIMIT2N < agree2){
		alert("动拨金额2不能大于核准金额2");
		document.getElementById('limit2Agree').value = '';
		document.getElementById('amountAgree').value = '';	
	}else{
		var amountAgree = numFormat((agree1 + agree2),2);
		document.getElementById('amountAgree').value = amountAgree;
		var amountRate = ((agree1 * LIMIT1_RATE) + (agree2 * LIMIT2_RATE)) / strCut(amountAgree);
		document.getElementById("amountRate").innerHTML = amountRate.toFixed(4) + '%';
	}
 }
 
/*
 * 实时动态强制更改用户录入
 */
function amount(th){ 
    var regStrs = [ 
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0 
        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点 
        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点 
        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上 
    ]; 
    for(i=0; i<regStrs.length; i++){ 
        var reg = new RegExp(regStrs[i][0]); 
        th.value = th.value.replace(reg, regStrs[i][1]); 
    } 
}

/*
 * 录入完成后，输入模式失去焦点后对录入进行判断并强制更改，并对小数点进行0补全,并改为千字符
 */
function overFormat(th){
	var v = th.value; 
    if(v === ''){ 
        v = '0.00'; 
    }else if(v === '0'){ 
        v = '0.00'; 
    }else if(v === '0.'){ 
        v = '0.00'; 
    }else if('/^0+\d+\.?\d*.*$/' === v){ 
        v = v.replace('/^0+(\d+\.?\d*).*$/', '$1'); 
        v = inp.getRightPriceFormat(v).val; 
    }else if('/^0\.\d$/' === v){ 
        v = v + '0'; 
    }else if(!'/^\d+\.\d{2}$/' === v){ 
        if('/^\d+\.\d{2}.+/' === v){ 
            v = v.replace('/^(\d+\.\d{2}).*$/', '$1'); 
        }else if('/^\d+$/' === v){ 
            v = v + '.00'; 
        }else if('/^\d+\.$/' === v){ 
            v = v + '00'; 
        }else if('/^\d+\.\d$/' === v){ 
            v = v + '0'; 
        }else if('/^[^\d]+\d+\.?\d*$/' === v){ 
            v = v.replace('/^[^\d]+(\d+\.?\d*)$/', '$1'); 
        }else if('/\d+/' === v){ 
            v = v.replace('/^[^\d]*(\d+\.?\d*).*$/', '$1'); 
            ty = false; 
        }else if('/^0+\d+\.?\d*$/' === v){ 
            v = v.replace('/^0+(\d+\.?\d*)$/', '$1'); 
            ty = false; 
        }else{ 
            v = '0.00';   
        } 
    }
   
    th.value =  numFormat(v,2); 
}

/*
 * 数字转换为千字符
 */
var numFormat =  function(s,n){ 
	n = (n > 0 && n < 20 )? n : 2; 
	s = parseFloat((s + '').replace('/[^\d\.-]/g', '')).toFixed(n) + ''; 
	var l = s.split('.')[0].split('').reverse(), r = s.split('.')[1]; 
	t = ''; 
	for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : ''); 
	} 
	return t.split('').reverse().join('') + '.' + r; 
} 


/*
 * 数据字符串截取
 */
function strCut(s){

	var str = [];
	str = s.split(',');
	var t = '';
	var i;
	for(i = 0;i<str.length;i++){
		t += str[i];
	}
	return t;
}









