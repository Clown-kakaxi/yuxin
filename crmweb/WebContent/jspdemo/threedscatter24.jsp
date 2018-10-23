<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="ChartDirector.*" %>
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

// The XYZ data for the 3D scatter chart as 3 random data series
RanSeries r = new RanSeries(3);
/* double[] xData = r.getSeries2(20, 0, products1v, 0);
double[] yData = r.getSeries2(20, 0, ztradefinance03v, 0);
double[] zData = r.getSeries2(20, 0, ztradefinance04v, 0); */

double[] xData = r.getSeries2(20, 0, 0, products1v);
double[] yData = r.getSeries2(20, 0, 0, ztradefinance03v);
double[] zData = r.getSeries2(20, 0, 0, ztradefinance04v);
// Create a ThreeDScatterChart object of size 720 x 520 pixels
ThreeDScatterChart c = new ThreeDScatterChart(650, 500);

// Add a title to the chart using 20 points Times New Roman Italic font
c.addTitle("Wallet-Size营销目标3D分析图  ", "宋体  Bold Italic", 16);

// Set the center of the plot region at (350, 240), and set width x depth x height to 360 x 360 x
// 270 pixels
c.setPlotRegion(350, 240, 360, 360, 270);

// Set the elevation and rotation angles to 15 and 30 degrees
c.setViewAngle(15, 30);

// Add a scatter group to the chart using 13 pixels glass sphere symbols, in which the color depends
// on the z value of the symbol
ThreeDScatterGroup g = c.addScatterGroup(xData, yData, zData, "", Chart.GlassSphere2Shape, 13,
    Chart.SameAsMainColor);

// Add grey (888888) drop lines to the symbols
g.setDropLine(0x888888);

// Add a color axis (the legend) in which the left center is anchored at (645, 220). Set the length
// to 200 pixels and the labels on the right side. Use smooth gradient coloring.
c.setColorAxis(600, 220, Chart.Left, 200, Chart.Right).setColorGradient();

// Set the x, y and z axis titles using 10 points Arial Bold font
c.xAxis().setTitle("X-可使用Products数", "宋体  Bold", 10);
c.yAxis().setTitle("y-企业上年Wallet Size", "宋体  Bold", 10);
c.zAxis().setTitle("z-SOW（%）", "宋体  Bold", 10);

// Output the chart
String chart1URL = c.makeSession(request, "chart1");

// Include tool tip for the chart
String imageMap1 = c.getHTMLImageMap("", "", "title='(x={x|p}, y={y|p}, z={z|p}'");
%>
<html>
<body style="margin:5px 0px 0px 5px">
<img src='<%=response.encodeURL("getchart.jsp?"+chart1URL)%>'
    usemap="#map1" border="0">
<map name="map1"><%=imageMap1%></map>
</body>
</html>

