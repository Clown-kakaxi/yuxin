<%@page import="ChartDirector.*" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%


double products1v=0.0;
double ztradefinance03v=0.0;
double ztradefinance04v=0.00;

if(request.getParameter("ztradefinance04v")!=""&&request.getParameter("ztradefinance04v")!=null){
	 ztradefinance04v=Double.parseDouble(request.getParameter("ztradefinance04v"));
	};
if(request.getParameter("products1v")!=""&&request.getParameter("products1v")!=null){
	products1v=Double.parseDouble(request.getParameter("products1v"));
};
 if(request.getParameter("ztradefinance03v")!=""&&request.getParameter("ztradefinance03v")!=null){
	 ztradefinance03v=Double.parseDouble(request.getParameter("ztradefinance03v"));
}; 

// The x coordinates for the 2 scatter groups
double[] dataX0 = {products1v};

// The y and z coordinates for the first scatter group
double[] dataY0 = {ztradefinance03v};
double[] dataZ0 = {ztradefinance04v};




// Create a ThreeDScatterChart object of size 760 x 520 pixels
ThreeDScatterChart c = new ThreeDScatterChart(650, 500);

// Add a title to the chart using 18 points Arial font
c.addTitle("Wallet-Size营销目标3D分析图  ", "宋体  Bold Italic", 16);

// Set the center of the plot region at (385, 270), and set width x depth x height to 480 x 240 x
// 240 pixels
c.setPlotRegion(310, 230, 400, 260, 260);

// Set the elevation and rotation angles to 30 and -10 degrees
c.setViewAngle(15, 30);

// Add a legend box at (380, 40) with horizontal layout. Use 9pt Arial Bold font.
LegendBox b = c.addLegend(380, 40, false, "宋体  Bold", 9);
b.setAlignment(Chart.TopCenter);
b.setRoundedCorners();

// Add a scatter group to the chart using 13 pixels red (ff0000) glass sphere symbols, with dotted
// drop lines
ThreeDScatterGroup g0 = c.addScatterGroup(dataX0, dataY0, dataZ0, "客户属性值",
    Chart.GlassSphere2Shape, 13, 0xff0000);
g0.setDropLine(c.dashLineColor(Chart.SameAsMainColor, Chart.DotLine));



// Set x-axis tick density to 50 pixels. ChartDirector auto-scaling will use this as the guideline
// when putting ticks on the x-axis.
c.xAxis().setTickDensity(50);


// Set label style to Arial bold for all axes
c.xAxis().setLabelStyle("Arial Bold");
c.yAxis().setLabelStyle("Arial Bold");
c.zAxis().setLabelStyle("Arial Bold");

// Set the x, y and z axis titles using deep blue (000088) 15 points Arial font
c.xAxis().setTitle("X-可使用Products数", "宋体  Bold", 10, 0x000088);
c.yAxis().setTitle("y-企业上年WalletSize", "宋体  Bold", 9, 0x000088);
c.zAxis().setTitle("z-SOW（%）", "宋体  Bold", 10, 0x000088);
// Output the chart
String chart1URL = c.makeSession(request, "chart4");

//Include tool tip for the chart
String imageMap1 = c.getHTMLImageMap("", "",
 "title='[{dataSetName}] x = {x} 个, y = {y} mil, z = {z} %'");
%>
<html>
<body style="margin:5px 0px 0px 5px">
<img src='<%=response.encodeURL("getchart.jsp?"+chart1URL)%>'
    usemap="#map1" border="0">
<map name="map1"><%=imageMap1%></map>
</body>
</html>

