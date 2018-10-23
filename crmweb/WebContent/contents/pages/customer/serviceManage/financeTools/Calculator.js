
/*------------------------------------------------------------------------
  计算器控件 
  function Calc_dropdown(edit)
    弹出计算器，参数edit可无 
  最新修改 文兵 2002-12-24 适用于IE 5.0以上浏览器
*/

var Calc_popup;

var Calc_edit;
var Calc_stored = 0;
var Calc_state = "0";
var Calc_newnum = false;

function Calc_hide()
{
  Calc_popup.releaseCapture();
  Calc_popup.style.display="none";
}

function Calc_capture_click()
{
  var obj=event.srcElement;
  if (Calc_popup.contains(obj)) {
    if ( (obj!=Calc_popup) && obj.onclick) obj.onclick();
  } else {
    Calc_hide();
  }
}

function Calc_writeHTML()
{
  if (typeof(Calc_popup) != "undefined") return;
  var html =
    '<table style="border:black 1px solid;text-align:center;width:108px;FONT-SIZE:10.5pt;cursor:hand;'+
    'background-color:#fffef5;" cellspacing="1" hidefocus="true" class="calc_table">'+
	'<style>'+
	'TD.calc_btn {border-top:#e3eff4 1px solid;border-left:#e3eff4 1px solid;border-right:#a5c2d1 1px solid;'+
	'border-bottom:#a5c2d1 1px solid;BACKGROUND-COLOR:#d6e7ef;vertical-align:bottom;font-size:9pt;color:#800000}'+
	'TD.calc_num {border-top:#e7ebd6 1px solid;border-left:#e7ebd6 1px solid;border-right:#c6d09d 1px solid;'+
	'border-bottom:#c6d09d 1px solid;BACKGROUND-COLOR:#e7ebd6;color:navy}'+
	'</style>'+
	'<tr><td colspan="2" class="calc_btn" onclick="Calc_clear()">清除</td>'+
	'<td colspan="2" class="calc_btn" onclick="Calc_backspace()" ondblclick="this.click()">←</td></tr>'+
	'<tr><td class="calc_num" width="25%" onclick="Calc_number(7)" ondblclick="this.click()">7</td>'+
	'<td class="calc_num" width="25%" onclick="Calc_number(8)" ondblclick="this.click()">8</td>'+
	'<td class="calc_num" width="25%" onclick="Calc_number(9)" ondblclick="this.click()">9</td>'+
	'<td class="calc_btn" width="25%" onclick="Calc_plus()">┼'+
	'</td></tr><tr>'+
	'<td class="calc_num" onclick="Calc_number(4)" ondblclick="this.click()">4</td>'+
	'<td class="calc_num" onclick="Calc_number(5)" ondblclick="this.click()">5</td>'+
	'<td class="calc_num" onclick="Calc_number(6)" ondblclick="this.click()">6</td>'+
	'<td class="calc_btn" onclick="Calc_minus()">─</td>'+
	'</tr><tr>'+
	'<td class="calc_num" onclick="Calc_number(1)" ondblclick="this.click()">1</td>'+
	'<td class="calc_num" onclick="Calc_number(2)" ondblclick="this.click()">2</td>'+
	'<td class="calc_num" onclick="Calc_number(3)" ondblclick="this.click()">3</td>'+
	'<td class="calc_btn" onclick="Calc_times()">╳</td>'+
	'</tr><tr>'+
	'<td colspan="2" class="calc_num" onclick="Calc_number(0)" ondblclick="this.click()">0</td>'+
	'<td class="calc_num" style="font-weight:bold" onclick="Calc_dot()">.</td>'+
	'<td class="calc_btn" onclick="Calc_divide()">╱</td>'+
	'</tr><tr>'+
	'<td class="calc_btn" colspan="4" onclick="Calc_enter()">'+
	'<font color=green>=</font> 确定</td></tr></table>';

  Calc_popup = document.createElement(
    '<Div style="z-index:20000;position: absolute;display:none" hidefocus=true '
   +'onclick="Calc_capture_click()" ondblclick="this.click()">');
  document.body.insertAdjacentElement('beforeEnd',Calc_popup);
  Calc_popup.innerHTML = html;
}

// 弹出日历，参数edit可无 
function Calc_dropdown(edit) {
  if (!edit) {
    edit = window.event.srcElement.parentElement.children(0);
    if ((!edit.type) || (edit.type.toLowerCase() != "text")) return;
  }
  if(edit.readOnly) return;
  if ((Calc_edit==edit)&&(Calc_popup.style.display="")) return;
  Calc_edit = edit;
  Calc_stored = Number(Calc_edit.value);
  if (isNaN(Calc_stored)) Calc_stored = 0;
  edit.focus();
  Calc_state = 0;
  Calc_newnum = true;
  Calc_writeHTML();
  
  // 定位
  Calc_popup.style.left = 
    event.clientX - event.offsetX + edit.offsetWidth - 108
    - edit.clientLeft - document.body.clientLeft + document.body.scrollLeft;
  
  var top = 
    event.clientY - event.offsetY + edit.offsetHeight
    - edit.clientTop - document.body.clientTop + document.body.scrollTop;
  
  // 如果下拉框高度超出则往上弹出
  if (top > document.body.scrollTop + document.body.getBoundingClientRect().bottom - 117)
    top -= edit.offsetHeight + 117; 
  Calc_popup.style.top = top;
  
  Calc_popup.style.display="";
  
  // 如果下拉框弹出后看不见（超出底部），则自动滚页
//  if (Calc_popup.offsetTop + Calc_popup.offsetHeight + document.body.clientTop>= 
//      document.body.offsetHeight + document.body.scrollTop)
//    document.body.doScroll("scrollbarPageDown");
  
  Calc_popup.setCapture();
}

function Calc_clear()
{
  Calc_stored = 0;
  Calc_state = 0;
  Calc_newnum = false;
  Calc_edit.value = 0;
}

function Calc_backspace()
{
  if (Calc_newnum) return;
  var str = Calc_edit.value;
  if (str.length <= 1)
    Calc_edit.value = 0;
  else {
    str = str.substr(0,str.length-1);
    if (isNaN(Number(str))) Calc_edit.value = 0;
    else Calc_edit.value = str;
  }
}

function Calc_docalc(state)
{
  if (Calc_newnum) return;
  var num = Number(Calc_edit.value);
  if (isNaN(num)) return;
  switch (state) {
    case '+':
      num = Calc_stored + num;
      break;
    case '-':
      num = Calc_stored - num;
      break;
    case '*':
      num = Calc_stored * num;
      break;
    case '/':
      if (num == 0) num = Calc_stored;
      else num = Calc_stored / num;
      break;
  }
  Calc_edit.value = Math.round(num*10000)/10000; //结果保留4位小数
  Calc_stored = num;
  Calc_newnum = true;
}

function Calc_plus()
{
  Calc_docalc(Calc_state);
  Calc_state = "+";
}

function Calc_minus()
{
  Calc_docalc(Calc_state);
  Calc_state = "-";
}

function Calc_times()
{
  Calc_docalc(Calc_state);
  Calc_state = "*";
}

function Calc_divide()
{
  Calc_docalc(Calc_state);
  Calc_state = "/";
}

function Calc_dot()
{
  if (Calc_newnum) {
    Calc_edit.value = '0.';
    Calc_newnum = false;
    return;
  }
  var str = Calc_edit.value;
  if (str.indexOf('.') < 0)
    Calc_edit.value +='.';
}

function Calc_enter()
{
  Calc_docalc(Calc_state);
  Calc_hide();
}

function Calc_number(num)
{
  var en = Number(Calc_edit.value);
  if (isNaN(en) || ((en==0)&&(Calc_edit.value.indexOf('0.') != 0)) || Calc_newnum) {
    Calc_edit.value = num;
    Calc_newnum = false;
  } else {
    Calc_edit.value += num;
  }
}
//生成计算器关闭按扭的字符串
function GetCalc_Close()
{
   var headstr;
   headstr='<IMG onmouseover="this.src='+"'../Images/CalcClose2.gif'"+
           '" style="right: 10px; CURSOR: hand; POSITION: absolute; TOP: 0px" onclick="document.location.href ='+
           "'EXIT'"+
           '" onmouseout="this.src='+
           "'../Images/CalcClose1.gif'"+
           '" src="../Images/CalcClose1.gif"><IMG onmouseover="this.src='+
           "'../Images/CalcMini2.gif'"+
           '" style="right: 34px; CURSOR: hand; POSITION: absolute; TOP: 0px" onclick="document.location.href = '+
           "'MINI'"+
           '" onmouseout="this.src='
           +"'../Images/CalcMini1.gif'"+
           '" src="../Images/CalcMini1.gif">';
   document.writeln(headstr);
}