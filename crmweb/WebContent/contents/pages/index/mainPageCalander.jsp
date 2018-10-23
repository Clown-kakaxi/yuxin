<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link href="<%=request.getContextPath()%>/contents/css/comm.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath()%>/contents/css/frame.css" rel="stylesheet" type="text/css" /> 
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/adapter/ext/ext-base.js"/></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ext-all-debug.js"/></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/ux-all.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/locale/ext-lang-zh_CN.js"></script>
  
  <script type="text/javascript"> 
  //accept FF using innerText -- begin
  function isIE(){ //ie?
    if(navigator.appName.indexOf("Explorer") > -1)
      return true;
    else
      return false;
  }
  if(!isIE()){ //firefox innerText define
    HTMLElement.prototype.__defineGetter__( "innerText",
      function(){
        var anyString = "";
        var childS = this.childNodes;
        for(var i=0; i<childS.length; i++) {
          if(childS[i].nodeType==1)
            anyString += childS[i].tagName=="BR" ? '\n' : childS[i].textContent;
          else if(childS[i].nodeType==3)
            anyString += childS[i].nodeValue;
        }
        return anyString;
      }
    );
    HTMLElement.prototype.__defineSetter__( "innerText",
      function(sText){
        this.textContent=sText;
      }
    );
  }
  //accept FF using innerText -- end
  var months_cn = new Array("一", "二", "三","四", "五", "六", "七", "八", "九","十", "十一", "十二");
  var months_en = new Array("January","February","March","April","May","June","July","August","September","October ","November","December"); 
  var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31,30, 31, 30, 31); 

  var classTemp; 
  var today=new getToday(); 
  var year=today.year; 
  var month=today.month; 
  var newCal; 
  
  function getDays(month, year) { 
    if (1 == month){
      return ((0 == year % 4) && (0 != (year % 100))) ||(0 == year % 400) ? 29 : 28; 
    }
    else return daysInMonth[month]; 
  } 

  function getToday() { 
    this.now = new Date(); 
    this.year = this.now.getFullYear(); 
    this.month = this.now.getMonth(); 
    this.day = this.now.getDate(); 
  } 
  
  function Calendar() { 
    newCal = new Date(year,month,1); 
    today = new getToday(); 
    var day = -1; 
    var startDay = newCal.getDay(); 
    var endDay=getDays(newCal.getMonth(), newCal.getFullYear()); 
    var daily = 0; 
    if ((today.year == newCal.getFullYear()) && (today.month == newCal.getMonth())) { 
      day = today.day; 
    } 
    var caltable = document.getElementById('mainPage_calendar'); 
    var intDaysInMonth =getDays(newCal.getMonth(), newCal.getFullYear());
    for (var intWeek = 0; intWeek < caltable.rows.length;intWeek++) 
    for (var intDay = 0;intDay < caltable.rows[intWeek].cells.length;intDay++) { 
      var cell = caltable.rows[intWeek].cells[intDay]; 
  
      if ((intDay == (startDay==0?startDay:startDay-1)) && (0 == daily)){ daily = 1;} 
      var daytemp=daily<10?("0"+daily):(daily);  

	  var currDate = new Date();
	  currDate.setFullYear(year,month,daily);
      if ((daily > 0) && (daily <= intDaysInMonth)) {
        if(currDate.getDate() == new Date().getDate()
            && currDate.getMonth() == new Date().getMonth()
            && currDate.getFullYear() == new Date().getFullYear()){
			//若是当天
			cell.className = "curr_date";
        }else{
        	cell.className = "";
        }
        cell.innerText = daytemp; 
        daily++; 
      } else { 
        cell.innerText = ""; 
      } 
    } 
    document.getElementById('mainPage_year').innerText=year; 
    document.getElementById('mainPage_month').innerText=months_en[month]; 
  } 
  
  function subMonth(){ 
    if ((month-1)<0){ 
      month=11; 
      year=year-1; 
    } else{ 
      month=month-1; 
    } 
    Calendar(); 
  } 
  
  function addMonth(){ 
    if((month+1)>11){ 
      month=0; 
      year=year+1; 
    } else{ 
      month=month+1; 
    } 
    Calendar(); 
  } 
  
  function subYear(){
    if((year-1)<0){
      year=new Date().getFullYear();
      month=new Date().getMonth();
    }else{
      year=year-1;
    }
    Calendar();
  }
  
  function addYear(){
    year=year+1;
    Calendar();
  }
  
  function setDate(){ 
    if (document.getElementById('mainPage_month').innerText<1
        ||document.getElementById('mainPage_month').innerText>12){ 
      alert("月的有效范围在1-12之间!"); 
      return; 
    } 
    Calendar(); 
  } 
  
  function dayClick(tdNode){
	var clickDate = year+"-"+(month+1)+"-"+tdNode.innerText;
	var contextPath = document.getElementById('mainPage_contextPath').value;
	parent.location = contextPath+'/contents/pages/workSpace/calendarManager/schedulePlanIndex.jsp?calDate='+clickDate;
  }
</script>
  
</head>
<body>
<div class="ic_box_left">
  <div class="ic_date">
    <div class="ic_date_select">
      <div class="ic_month_select">
        <div class="ic_month_left"><a href="javaScript:subMonth();"></a></div>
        <div class="ic_month_con">
          <p><span  id="mainPage_month"></span></p>
        </div>
        <div class="ic_month_right"><a href="javaScript:addMonth();"></a></div>
      </div>
      <div class="ic_year_select">
        <div class="ic_year_left"><a href="javaScript:subYear();"></a></div>
        <div class="ic_year_con">
          <p><span id="mainPage_year"></span></p>
        </div>
        <div class="ic_year_right"><a href="javaScript:addYear();"></a></div>
      </div>
    </div>
    <div class="ic_date_tit"></div>
    <div class="ic_date_list">
      <table border="0" cellpadding="1" cellspacing="1" id="mainPage_caltable" class="ic_date_table">
        <tbody id="mainPage_calendar">
          <script type="text/javascript"> 
     	 	for (var intWeeks = 0; intWeeks < 6; intWeeks++) { 
       			 document.write("<TR>"); 
       	   		 for (var intDays = 0; intDays < 7;intDays++) {
          			document.write("<TD onClick='dayClick(this);'></TD>"); 
        		 }
        		 document.write("</TR>"); 
      		} 
    	  </script>
        </tbody>
      </table>
    </div>
    <script type="text/javascript">
 		 Calendar(); 
	</script>
  </div>
</div>
<input id="mainPage_contextPath" type="hidden" value="<%=request.getContextPath()%>" />
</body>
</html>