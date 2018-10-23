<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>集团客户组织架构图</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/contents/css/groupClients.css" />
</head>
<body>
<div id="contain" style="width:1200px;">  
  <ul id="map" class="solo">  
      <li><div class="root section"><a href="#">XXX集团公司</a></div>  
          <ul>  
              <li><div class="first"><a href="#">A公司</a></div></li>  
              <li><div class="section"><a href="#">B公司</a></div>  
                      <ul>  
                          <li><div class="first"><a href="#">C公司</a></div></li>  
                          <li><div class="section"><a href="#">D公司的名字能有多长？想有多长就能有多长吧。估计它会自动的换行……</a></div>  
                              <ul>  
                                  <li><div class="first"><a href="#">E公司</a></div></li>
                                  <li><div><a href="#">F公司</a></div></li>
                                  <li><div class="last"><a href="#">G公司</a></div></li>  
                              </ul>      
                          </li>  
                          <li><div><a href="#">H公司</a></div></li>  
                          <li><div class="last"><a href="#">I公司</a></div></li>  
                      </ul>  
              </li>  
              <li><div class="last"><a href="#">J公司</a></div></li>  
          </ul>  
      </li>  
  </ul>  
</div>
</body>
</html>
