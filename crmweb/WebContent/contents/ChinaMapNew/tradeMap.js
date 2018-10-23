function eventHandler(e) {
//        var ddd = new Ext.Window({
//            height:400,
//            title:'aaaaa'
//            });
//        ddd.show();
    chart10.setXMLUrl(basepath+"/contents/ChinaMapNew/data/"+e.value+"1.xml");  
    chart20.setXMLUrl(basepath+"/contents/ChinaMapNew/data/"+e.value+"2.xml");
//   Ext.fly('chartdiv').dom.src=basepath+'/contents/pages/customer/customerManager/customerBaseInformation/fusionchartsDemo/contribute/c2.html';
//    Ext.getCmp('www');
}
var viewport = new Ext.Viewport({
        layout : 'fit',
        items : [{
            layout : 'border',
            items : [{
                region:'west',
                width:600,
                html:'<div id="container"  style="height:100%,width;100%"></div>'
            },{
                region:'center',
                    layout : 'border',
                    items : [{
                        id:'www',
                        region:'north',
                       height:250,
                        html:'<div id="chartdiv10" style="height:80%;width:100%" ></div>'
//                         html : '<iframe  id="a01" width="100%" height="100%"   frameborder=0 src="'+ basepath+ '/contents/pages/customer/customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/c4.html">'
                    },{
                        region:'center',
                        height:250,
                         layout:'fit',
                        html : '<div id="chartdiv20" style="height:100%;width:90%" ></div>'
                }]
            }]
        }]
    });
    var s1 = new SWFObject("ChinaMap.swf","ply","600","500","0");
    s1.addParam("allowfullscreen","true");
    s1.addParam("allownetworking","all");
    s1.addParam("allowscriptaccess","always");
    s1.addParam("wmode","transparent");
    s1.addVariable("title","");
    s1.addVariable("xmlurl","data/d.xml");
    s1.addVariable("jsHandler","eventHandler");
    s1.write("container");
        var chart10 = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "myChartId000", "100%", "100%", "0", "0");
            chart10.setXMLUrl(basepath+"/contents/ChinaMapNew/data/all1.xml");         
            chart10.render("chartdiv10");
    
        var chart20 = new FusionCharts(basepath+"/FusionCharts/MSColumn3D.swf", "ChartId000", "100%", "100%", "0", "0");
            chart20.setXMLUrl(basepath+"/contents/ChinaMapNew/data/all2.xml");         
            chart20.render("chartdiv20");
Ext.onReady(function() {
	Ext.QuickTips.init();

    
    // 布局模型
    
//    
//    var s1 = new SWFObject(basepath+"/contents/ChinaMapNew/ChinaMap.swf","ply","600","500","10","#FFFFFF");
//        s1.addParam("allowfullscreen","true");
//        s1.addParam("allownetworking","all");
//        s1.addParam("allowscriptaccess","always");
//        s1.addParam("wmode","transparent");
//        s1.addVariable("title","资产托管行业地图");
//        s1.addVariable("xmlurl",basepath+"/contents/ChinaMapNew/data/d.xml");
//        s1.addVariable("jsHandler","eventHandler");
//        s1.write("container");
});

