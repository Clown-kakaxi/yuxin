<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link href="<%=request.getContextPath()%>/contents/css/comm.css" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath()%>/contents/css/frame.css" rel="stylesheet" type="text/css" /> 
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/contents/commonjs/jquery-1.5.2.min.js"></script>

</head>

<body>
<div class="ic_under">
<div class="ic_box_tt">
        <div class="ic_box_tit ic_cpxx">
        	<!--tab-->
            <div class="ic_box_tab">
            	<ul>
                	<li class="selected"><a href="#"><span>预售产品</span></a></li>
                    <li><a href="#"><span>在售产品</span></a></li>
                    <li><a href="#"><span>分红产品</span></a></li>
                    <li><a href="#"><span>到期产品</span></a></li>
                </ul>
            </div>
            <!--tab end-->
        </div>
      </div>
      <div class="ic_box_con">
      	<!--scroll-->
   	  <div class="ic_rota">
        	<div id="cover_left" class="ic_rota_left"><a href="javascript:void(0)"></a></div>
            <div class="ic_rota_list">
            	<div class="jCarouselLite" style="visibility: visible; overflow: hidden; width:970px; height: 110px;">
            	<ul class="ic_rota_ul">
                	<li><dl><dt><img src="<%=request.getContextPath()%>/contents/images/tmp1.jpg" width="99" height="82" /></dt>
               	    <dd><p>文字文字文字文字文字文字文字文字文字文字文字文字</p><a href="#" class="ic_rota_more">查看详情</a></dd></dl></li>
                    <li><dl><dt><img src="<%=request.getContextPath()%>/contents/images/tmp1.jpg" width="99" height="82" /></dt>
               	    <dd><p>文字文字文字文字文字文字文字文字文字文字文字文字</p><a href="#" class="ic_rota_more">查看详情</a></dd></dl></li>
                    <li><dl><dt><img src="<%=request.getContextPath()%>/contents/images/tmp1.jpg" width="99" height="82" /></dt>
               	    <dd><p>文字文字文字文字文字文字文字文字文字文字文字文字</p><a href="#" class="ic_rota_more">查看详情</a></dd></dl></li>
                    <li><dl><dt><img src="<%=request.getContextPath()%>/contents/images/tmp1.jpg" width="99" height="82" /></dt>
               	    <dd><p>文字文字文字文字文字文字文字文字文字文字文字文字</p><a href="#" class="ic_rota_more">查看详情</a></dd></dl></li>
                    <li><dl><dt><img src="<%=request.getContextPath()%>/contents/images/tmp1.jpg" width="99" height="82" /></dt>
               	    <dd><p>文字文字文字文字文字文字文字文字文字文字文字文字</p><a href="#" class="ic_rota_more">查看详情</a></dd></dl></li>
                </ul>
                </div>
          </div>
            <div id="cover_right" class="ic_rota_right"><a href="javascript:void(0)"></a></div>
        </div>      	
      	<!--scroll end-->
      </div>
      <script src="<%=request.getContextPath()%>/contents/commonjs/jcarousellite.js" type="text/javascript"></script>
      <script type="text/javascript">
		//幻灯片列表的滚动效果
		$(".jCarouselLite").jCarouselLite({
		btnNext: "#cover_right",
		btnPrev: "#cover_left",
		visible: 4,
		speed: 700
		}); 
	  
	  </script>
</div>
</body>
</html>