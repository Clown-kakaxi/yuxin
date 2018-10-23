
/*************************************************************************************************

  采用控件替换的办法，解决select控件不美观和永远浮在上面的问题。 
  只要引用了本js文件，将自动替换所有Select控件。如select未设置ID，则不予替换。
  每个select控件的id不能与其它任何控件相同。
  在html中可设置下拉图片的位置(要带斜杠/)，不设则使用默认路径。
  默认select下拉框与select控件同宽，如欲实现下拉框更宽，则设置select的DropDownWidth属性即可（单位为点）。
  默认select下拉框的行数为6行，设置select的DropDownRows属性即可（取值范围为1到10）。
  例子为：
    <select DropDownWidth="200" DropDownRows="10" ID="Select2" style="width:100px">

  最新修改 文兵 2002-12-26 适用于IE 5.0以上浏览器
  
***************************************************************/

if (!SelDropdownPicturePath) {
  var SelDropdownPicturePath = "../Images/";
}

var Sel_popup;

// 弹出下拉框 
function Sel_DropDown(edit,select)
{
  // 创建下拉框
  var ColorOver="#97D6DB";
  var ColorOut="#DFF0F4";
  var len=select.options.length;

  var yl = 20;     // 下拉框高度 
  var overflowy="hidden";

  var Sel_popup_rows = Number(select.getAttribute('DropDownRows'));
  if (isNaN(Sel_popup_rows)||(Sel_popup_rows<1)||(Sel_popup_rows>10))
    Sel_popup_rows = 6;
  
  if (len!=0) {
    if (len <= Sel_popup_rows) yl = len * 20 + 2;
    else {
      yl = Sel_popup_rows * 20 + 2;
      overflowy="";
    }
  }

  // 下拉框宽度 
  var xl = select.getAttribute('DropDownWidth');
  if (xl == null) xl = edit.parentElement.style.pixelWidth;
  else xl = Math.max(parseInt(xl),edit.parentElement.style.pixelWidth);

  var html;
  if (!Sel_popup) {
    html = '<DIV ' +
      'style="z-index:20000;position:absolute;font-family:宋体; font-size:9pt;'+
      'overflow:scroll; overflow-x:hidden; scrollbar-base-color:#5598B9; '+
      'color:#000080; border:1px solid #1B5977; cursor:hand; '+
      'SCROLLBAR-HIGHLIGHT-COLOR: #B4D9EC; SCROLLBAR-ARROW-COLOR: white"' +
      'onclick="Sel_capture_click()" onmouseover="Sel_capture_mover()" ' +
      'onmouseout="Sel_capture_mout()" onmousemove="Sel_capture_mmove()">';
    Sel_popup = document.createElement(html);
    document.body.insertAdjacentElement('beforeEnd',Sel_popup);
  }
  
  Sel_popup.style.width = xl;
  Sel_popup.style.height = yl;
  Sel_popup.style.overflowY = overflowy; 
  
  // 创建下拉框的内容 
  html = "";
  for (var i=0;i<len;i++) {
    html += '<DIV onmouseover="this.style.background='
          + "'" + ColorOver + "'" + '" '
          + 'onmouseout="this.style.background='
          + "'" + ColorOut + "'" + '" '
          + 'NOWRAP STYLE="height:20px; background:' + ColorOut 
          + '; border-bottom:1px solid #1B5977; padding-top:4px; padding-left:2px" '
          + 'onclick="Sel_DoSelect(' + "'" + edit.id + "','" + select.id + "'," + i + ');">'
          + select.options(i).text + '</DIV>';
  }
  
  var DropIMG = event.srcElement;
  Sel_popup.innerHTML = html;

  // 定位
  var pos =
    event.clientX - event.offsetX - DropIMG.offsetLeft + edit.offsetLeft 
    - document.body.clientLeft + document.body.scrollLeft;
  var max =
    document.body.scrollLeft + document.body.getBoundingClientRect().right
    - Sel_popup.style.pixelWidth;
  // 如果下拉框宽度超出则往左移
  // 注意，当页面右侧出现scrollbar时，该定位将没有估算scrollbar的宽度
  if (pos > max) Sel_popup.style.left = max;
  else Sel_popup.style.left =  pos;
  
  pos = 
    event.clientY - event.offsetY - DropIMG.offsetTop + edit.offsetTop 
    + edit.offsetHeight - document.body.clientTop + document.body.scrollTop;
  // 如果下拉框高度超出则往上弹出
  if (pos > document.body.scrollTop + document.body.getBoundingClientRect().bottom - yl)
    pos -= edit.offsetHeight + yl; 
  Sel_popup.style.top = pos;
  Sel_popup.style.display="";
  
  Sel_popup.setCapture();
}

function Sel_hide()
{
  Sel_popup.releaseCapture();
  Sel_popup.style.display="none";
}

function Sel_capture_click()
{
  var obj=event.srcElement;
  if (Sel_popup.contains(obj)) {
    if ( (obj!=Sel_popup) && obj.onclick) obj.onclick();
  } else {
    Sel_hide();
  }
}

function Sel_inPopup(x,y)
{
  x += document.body.scrollLeft - (Sel_popup.offsetLeft + document.body.clientLeft);
  y += document.body.scrollTop - (Sel_popup.offsetTop + document.body.clientTop);
  if ( (x>=0)&&(x<Sel_popup.offsetWidth)&&(y>=0)&&(y<Sel_popup.offsetHeight) )
    return true;
  else
    return false;
}

function Sel_capture_mmove()
{
  // 防止鼠标移动过快的一些问题
  var obj=event.srcElement;
  if ( (Sel_popup.style.display=="none") || 
       Sel_inPopup(event.clientX,event.clientY)
     ) {
    Sel_popup.releaseCapture();
  } else Sel_popup.setCapture();
}

function Sel_capture_mover()
{
  var obj=event.srcElement;
  if ( (Sel_popup.style.display=="none") || 
//       (obj==Sel_popup) ||  // 该条件不能加，如果鼠标移动快的话，则出现问题
       Sel_inPopup(event.clientX,event.clientY)
     ) {
    Sel_popup.releaseCapture();
  }
  if (Sel_popup.contains(obj)) {
    if ( (obj!=Sel_popup) && obj.onmouseover) obj.onmouseover();
  }
}

function Sel_capture_mout()
{
  var obj=event.srcElement;
  if ( Sel_popup.style.display == "none" ) return;
  if ((obj==Sel_popup)||(!Sel_inPopup(event.clientX,event.clientY)))
  {
    Sel_popup.setCapture();
  }
  if (Sel_popup.contains(obj)) {
    if ( (obj!=Sel_popup) && obj.onmouseout) obj.onmouseout();
  }
}

// 选中下拉框中的选项 
function Sel_DoSelect(edit,select,i)
{
  edit = document.all(edit);
  select = document.all(select);
  edit.value = select.options(i).text;
  edit.select();
  edit.focus();
  if (select.selectedIndex != i) {
    select.selectedIndex = i;
    if (select.onchange) select.onchange();
  }
  Sel_hide();
}

// 替换原Select控件 
function Sel_ReplaceSelect(select) {
  select.style.display="none";

  var parent=select.parentElement;
  
  var container=document.createElement("DIV");
  with (container.style) {
    pixelWidth = select.style.pixelWidth;
    if (pixelWidth==0) pixelWidth=select.clientWidth;
    pixelLeft = select.style.pixelLeft;
    pixelTop = select.style.pixelTop;
    position = select.style.position;
  }
  parent.insertBefore(container,select);
  
  // 创建edit控件 
  var edit=document.createElement("<input type='text' class='selecteditctrl'>");
  edit.style.pixelWidth = select.clientWidth - 16;
  edit.tabIndex=select.tabIndex;
  if (select.selectedIndex>=0) {
    edit.value = select.options(select.selectedIndex).text;
  }
  edit.readOnly = true;
  edit.disabled=select.disabled;  
  edit.id = "E_"+select.id;
  container.appendChild(edit);

  // 创建下拉按钮 
  var down=document.createElement(
    '<IMG SRC="'+SelDropdownPicturePath+'SelDropdown.gif" border=0 ' +
    'style="margin-bottom:1px;vertical-align:bottom;cursor:hand" onclick="Sel_DropDown(' +
    edit.id + ',' + select.id + ')">');
    
  down.disabled=select.disabled;  
  down.id = "D_"+select.id;    
  container.appendChild(down);
}

function Sel_onloadinit() 
{
  var obj;
  var selects = document.all.tags("SELECT");
  for (var i=0;i<selects.length;i++) {
    obj = selects[i];
    if ((obj.size==0)&&(obj.id!="")) Sel_ReplaceSelect(obj);
  }
}

window.attachEvent("onload",Sel_onloadinit);
