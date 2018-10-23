<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="ChartDirector.*" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%
// The XYZ points for the bubble chart
double ztradefinance04v=0.0;
double productspiev=0.0;
double mztradefinance04v=0.0;
double mproductspiev=0.00;
String customerTypev="";
/* if(request.getParameter("tradefinancepie33v")!=null){
 tradefinancepie33v=Double.parseDouble(request.getParameter("tradefinancepie33v"));
};
 if(request.getParameter("tradefinancepie33v")!=null){
 tradefinancepie33v1=Double.parseDouble(request.getParameter("tradefinancepie33v"));
};  */
if(request.getParameter("ztradefinance04v")!=""&&request.getParameter("ztradefinance04v")!=null){
 ztradefinance04v=Double.parseDouble(request.getParameter("ztradefinance04v"));
};
if(request.getParameter("productspiev")!=""&&request.getParameter("productspiev")!=null){
 productspiev=Double.parseDouble(request.getParameter("productspiev"));
};
 if(request.getParameter("mztradefinance04v")!=""&&request.getParameter("mztradefinance04v")!=null){
 mztradefinance04v=Double.parseDouble(request.getParameter("mztradefinance04v"));
};
if(request.getParameter("mproductspiev")!=""&&request.getParameter("mproductspiev")!=null){
 mproductspiev=Double.parseDouble(request.getParameter("mproductspiev"));
} 

if(request.getParameter("customerTypev")!=""&&request.getParameter("customerTypev")!=null){
	customerTypev=new String(request.getParameter("customerTypev").getBytes("iso8859-1"),"utf-8");
};
double[] dataX0 = {ztradefinance04v };
double[] dataY0 = {productspiev};
double[] dataZ0 = {52};

double[] dataX1 = {mztradefinance04v};
double[] dataY1 = {mproductspiev};
double[] dataZ1 = {56};


// Create a XYChart object of size 540 x 480 pixels
XYChart c = new XYChart(650, 500);

// Set the plotarea at (70, 65) and of size 400 x 350 pixels. Turn on both horizontal and vertical
// grid lines with light grey color (0xc0c0c0)
c.setPlotArea(70, 65, 550, 350, -1, -1, Chart.Transparent, 0xc0c0c0, -1);

// Add a legend box at (70, 30) (top of the chart) with horizontal layout. Use 12pt Times Bold
// Italic font. Set the background and border color to Transparent.
c.addLegend(70, 30, false, "宋体 Bold Italic", 12).setBackground(Chart.Transparent);

// Add a title to the chart using 18pt Times Bold Itatic font.
c.addTitle("Wallet-Size营销目标二维分析图\n"+customerTypev, "宋体  Bold Italic", 14);

// Add titles to the axes using 12pt Arial Bold Italic font
c.yAxis().setTitle("Products(Amounts)", "Arial Bold Italic", 12);
c.xAxis().setTitle("Share of Wallet -Size(%)", "Arial Bold Italic", 12);

// Set the axes line width to 3 pixels
c.xAxis().setWidth(3);
c.yAxis().setWidth(3);

// Add (dataX0, dataY0) as a scatter layer with red (ff3333) spheres, where the sphere size is
// modulated by dataZ0. This creates a bubble effect.
c.addScatterLayer(dataX0, dataY0, "目前 SOW(%)", Chart.SolidSphereShape, 15, 0xff3333
    ).setSymbolScale(dataZ0);

// Add (dataX1, dataY1) as a scatter layer with blue (0000ff) spheres, where the sphere size is
// modulated by dataZ1. This creates a bubble effect.
c.addScatterLayer(dataX1, dataY1, "Target SOW(%)", Chart.SolidSphereShape, 15, 0x0000ff
    ).setSymbolScale(dataZ1);

// Output the chart
String chart1URL = c.makeSession(request, "chart1");

// Include tool tip for the chart
String imageMap1 = c.getHTMLImageMap("", "",
    "title='[{dataSetName}] x = {x} %, y = {value} %, z = {z} meters'");
%>
<html>
<body style="margin:5px 0px 0px 5px">
<img src='<%=response.encodeURL("getchart.jsp?"+chart1URL)%>'
    usemap="#map1" border="0">
<map name="map1"><%=imageMap1%></map>
</body>
</html>

