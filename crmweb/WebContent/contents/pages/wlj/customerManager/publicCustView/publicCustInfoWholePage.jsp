<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户关系管理系统</title>
	<meta name="keywords" content="客户关系管理系统,CRM" />
	<meta name="description" content="客户关系管理系统,CRM" />
	<meta name="Author" content="YuchengTech" />
	<link rel="shortcut icon" href="favicon.ico" />
	<%@ include file="/contents/pages/common/includes.jsp"%>
<script type="text/javascript">
	<%
		String custId = request.getParameter("custId");
		String busiId = request.getParameter("busiId");
		out.print("var _custId = '"+custId+"';");
		out.print("var _busiId = '"+busiId+"';");
	%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/echarts-2.2.7/build/dist/echarts.js"></script>
</head>
<body>
<div id="main" style="height:800px"></div>
<div id="main1" style="height:800px"></div>
<div id="main2" style="height:800px"></div>
<div id="main3" style="height:800px"></div>
<div id="main4" style="height:800px"></div>
<div id="main5" style="height:800px"></div>
<div id="main6" style="height:800px"></div>
<div id="main7" style="height:800px"></div>
<div id="main8" style="height:800px"></div>
<div id="main9" style="height:800px"></div>
<div id="main10" style="height:800px"></div>
<div id="main11" style="height:800px"></div>
<div id="main12" style="height:800px"></div>
<div id="main13" style="height:800px"></div>
<div id="main14" style="height:800px"></div>



<script type="text/javascript">
window.onload=function(){
	//路径配置
	require.config({
	    paths: {
	        echarts: "/crmweb/echarts-2.2.7/build/dist"
	    }
	});

	// 使用
	require(
	    [
	        'echarts',
	        'echarts/chart/funnel', // 使用柱状图就加载bar模块，按需加载
	        'echarts/chart/line',
	        'echarts/chart/bar',
	        'echarts/chart/scatter',
	        'echarts/chart/pie',
	        'echarts/chart/radar',
	        'echarts/chart/force',
	        'echarts/chart/map',
	        'echarts/chart/gauge'
	       
	    ],
	    function (ec) {
	    	debugger;
	        // 基于准备好的dom，初始化echarts图表
	        var myChart = ec.init(document.getElementById('main')); 
	        var myChart1 = ec.init(document.getElementById('main1'));
	        var myChart2 = ec.init(document.getElementById('main2'));
	        var myChart3 = ec.init(document.getElementById('main3'));
	        var myChart4 = ec.init(document.getElementById('main4'));
	        var myChart5 = ec.init(document.getElementById('main5'));
	        var myChart6 = ec.init(document.getElementById('main6'));
	        var myChart7 = ec.init(document.getElementById('main7'));
	        var myChart8 = ec.init(document.getElementById('main8'));
	        var myChart9 = ec.init(document.getElementById('main9'));
	        var myChart10 = ec.init(document.getElementById('main10'));
	        var myChart11 = ec.init(document.getElementById('main11'));
	        var myChart12 = ec.init(document.getElementById('main12'));
	        var myChart13 = ec.init(document.getElementById('main13'));
	        var myChart14 = ec.init(document.getElementById('main14'));
	        


	        var optionMain = {
	        	    color : [
	        	        'rgba(255, 69, 0, 0.5)',
	        	        'rgba(255, 150, 0, 0.5)',
	        	        'rgba(255, 200, 0, 0.5)',
	        	        'rgba(155, 200, 50, 0.5)',
	        	        'rgba(55, 200, 100, 0.5)'
	        	    ],
	        	    title : {
	        	        text: '漏斗图',
	        	        subtext: '纯属虚构'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c}%"
	        	    },
	        	    toolbox: {
	        	        show : true,
	        	        feature : {
	        	            mark : {show: true},
	        	            dataView : {show: true, readOnly: false},
	        	            restore : {show: true},
	        	            saveAsImage : {show: true}
	        	        }
	        	    },
	        	    legend: {
	        	        data : ['展现','点击','访问','咨询','订单']
	        	    },
	        	    calculable : true,
	        	    series : [
	        	        {
	        	            name:'预期',
	        	            type:'funnel',
	        	            x: '10%',
	        	            width: '80%',
	        	            itemStyle: {
	        	                normal: {
	        	                    label: {
	        	                        formatter: '{b}预期'
	        	                    },
	        	                    labelLine: {
	        	                        show : false
	        	                    }
	        	                },
	        	                emphasis: {
	        	                    label: {
	        	                        position:'inside',
	        	                        formatter: '{b}预期 : {c}%'
	        	                    }
	        	                }
	        	            },
	        	            data:[
	        	                {value:60, name:'访问'},
	        	                {value:40, name:'咨询'},
	        	                {value:20, name:'订单'},
	        	                {value:80, name:'点击'},
	        	                {value:100, name:'展现'}
	        	            ]
	        	        },
	        	        {
	        	            name:'实际',
	        	            type:'funnel',
	        	            x: '10%',
	        	            width: '80%',
	        	            maxSize: '80%',
	        	            itemStyle: {
	        	                normal: {
	        	                    borderColor: '#fff',
	        	                    borderWidth: 2,
	        	                    label: {
	        	                        position: 'inside',
	        	                        formatter: '{c}%',
	        	                        textStyle: {
	        	                            color: '#fff'
	        	                        }
	        	                    }
	        	                },
	        	                emphasis: {
	        	                    label: {
	        	                        position:'inside',
	        	                        formatter: '{b}实际 : {c}%'
	        	                    }
	        	                }
	        	            },
	        	            data:[
	        	                {value:30, name:'访问'},
	        	                {value:10, name:'咨询'},
	        	                {value:5, name:'订单'},
	        	                {value:50, name:'点击'},
	        	                {value:80, name:'展现'}
	        	            ]
	        	        }
	        	    ]
	        	};
	        
	        optionMain1 = {
	        	    backgroundColor: '#1b1b1b',
	        	    color: ['gold','aqua','lime'],
	        	    title : {
	        	        text: '模拟迁徙',
	        	        subtext:'数据纯属虚构',
	        	        x:'center',
	        	        textStyle : {
	        	            color: '#fff'
	        	        }
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: '{b}'
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        x:'left',
	        	        data:['北京 Top10', '上海 Top10', '广州 Top10'],
	        	        selectedMode: 'single',
	        	        selected:{
	        	            '上海 Top10' : false,
	        	            '广州 Top10' : false
	        	        },
	        	        textStyle : {
	        	            color: '#fff'
	        	        }
	        	    },
	        	    toolbox: {
	        	        show : true,
	        	        orient : 'vertical',
	        	        x: 'right',
	        	        y: 'center',
	        	        feature : {
	        	            mark : {show: true},
	        	            dataView : {show: true, readOnly: false},
	        	            restore : {show: true},
	        	            saveAsImage : {show: true}
	        	        }
	        	    },
	        	    dataRange: {
	        	        min : 0,
	        	        max : 100,
	        	        calculable : true,
	        	        color: ['#ff3333', 'orange', 'yellow','lime','aqua'],
	        	        textStyle:{
	        	            color:'#fff'
	        	        }
	        	    },
	        	    series : [
	        	        {
	        	            name: '全国',
	        	            type: 'map',
	        	            roam: true,
	        	            hoverable: false,
	        	            mapType: 'china',
	        	            itemStyle:{
	        	                normal:{
	        	                    borderColor:'rgba(100,149,237,1)',
	        	                    borderWidth:0.5,
	        	                    areaStyle:{
	        	                        color: '#1b1b1b'
	        	                    }
	        	                }
	        	            },
	        	            data:[],
	        	            markLine : {
	        	                smooth:true,
	        	                symbol: ['none', 'circle'],  
	        	                symbolSize : 1,
	        	                itemStyle : {
	        	                    normal: {
	        	                        color:'#fff',
	        	                        borderWidth:1,
	        	                        borderColor:'rgba(30,144,255,0.5)'
	        	                    }
	        	                },
	        	                data : [
	        	                    [{name:'北京'},{name:'包头'}],
	        	                    [{name:'北京'},{name:'北海'}],
	        	                    [{name:'北京'},{name:'广州'}],
	        	                    [{name:'北京'},{name:'郑州'}],
	        	                    [{name:'北京'},{name:'长春'}],
	        	                    [{name:'北京'},{name:'长治'}],
	        	                    [{name:'北京'},{name:'重庆'}],
	        	                    [{name:'北京'},{name:'长沙'}],
	        	                    [{name:'北京'},{name:'成都'}],
	        	                    [{name:'北京'},{name:'常州'}],
	        	                    [{name:'北京'},{name:'丹东'}],
	        	                    [{name:'北京'},{name:'大连'}],
	        	                    [{name:'北京'},{name:'东营'}],
	        	                    [{name:'北京'},{name:'延安'}],
	        	                    [{name:'北京'},{name:'福州'}],
	        	                    [{name:'北京'},{name:'海口'}],
	        	                    [{name:'北京'},{name:'呼和浩特'}],
	        	                    [{name:'北京'},{name:'合肥'}],
	        	                    [{name:'北京'},{name:'杭州'}],
	        	                    [{name:'北京'},{name:'哈尔滨'}],
	        	                    [{name:'北京'},{name:'舟山'}],
	        	                    [{name:'北京'},{name:'银川'}],
	        	                    [{name:'北京'},{name:'衢州'}],
	        	                    [{name:'北京'},{name:'南昌'}],
	        	                    [{name:'北京'},{name:'昆明'}],
	        	                    [{name:'北京'},{name:'贵阳'}],
	        	                    [{name:'北京'},{name:'兰州'}],
	        	                    [{name:'北京'},{name:'拉萨'}],
	        	                    [{name:'北京'},{name:'连云港'}],
	        	                    [{name:'北京'},{name:'临沂'}],
	        	                    [{name:'北京'},{name:'柳州'}],
	        	                    [{name:'北京'},{name:'宁波'}],
	        	                    [{name:'北京'},{name:'南京'}],
	        	                    [{name:'北京'},{name:'南宁'}],
	        	                    [{name:'北京'},{name:'南通'}],
	        	                    [{name:'北京'},{name:'上海'}],
	        	                    [{name:'北京'},{name:'沈阳'}],
	        	                    [{name:'北京'},{name:'西安'}],
	        	                    [{name:'北京'},{name:'汕头'}],
	        	                    [{name:'北京'},{name:'深圳'}],
	        	                    [{name:'北京'},{name:'青岛'}],
	        	                    [{name:'北京'},{name:'济南'}],
	        	                    [{name:'北京'},{name:'太原'}],
	        	                    [{name:'北京'},{name:'乌鲁木齐'}],
	        	                    [{name:'北京'},{name:'潍坊'}],
	        	                    [{name:'北京'},{name:'威海'}],
	        	                    [{name:'北京'},{name:'温州'}],
	        	                    [{name:'北京'},{name:'武汉'}],
	        	                    [{name:'北京'},{name:'无锡'}],
	        	                    [{name:'北京'},{name:'厦门'}],
	        	                    [{name:'北京'},{name:'西宁'}],
	        	                    [{name:'北京'},{name:'徐州'}],
	        	                    [{name:'北京'},{name:'烟台'}],
	        	                    [{name:'北京'},{name:'盐城'}],
	        	                    [{name:'北京'},{name:'珠海'}],
	        	                    [{name:'上海'},{name:'包头'}],
	        	                    [{name:'上海'},{name:'北海'}],
	        	                    [{name:'上海'},{name:'广州'}],
	        	                    [{name:'上海'},{name:'郑州'}],
	        	                    [{name:'上海'},{name:'长春'}],
	        	                    [{name:'上海'},{name:'重庆'}],
	        	                    [{name:'上海'},{name:'长沙'}],
	        	                    [{name:'上海'},{name:'成都'}],
	        	                    [{name:'上海'},{name:'丹东'}],
	        	                    [{name:'上海'},{name:'大连'}],
	        	                    [{name:'上海'},{name:'福州'}],
	        	                    [{name:'上海'},{name:'海口'}],
	        	                    [{name:'上海'},{name:'呼和浩特'}],
	        	                    [{name:'上海'},{name:'合肥'}],
	        	                    [{name:'上海'},{name:'哈尔滨'}],
	        	                    [{name:'上海'},{name:'舟山'}],
	        	                    [{name:'上海'},{name:'银川'}],
	        	                    [{name:'上海'},{name:'南昌'}],
	        	                    [{name:'上海'},{name:'昆明'}],
	        	                    [{name:'上海'},{name:'贵阳'}],
	        	                    [{name:'上海'},{name:'兰州'}],
	        	                    [{name:'上海'},{name:'拉萨'}],
	        	                    [{name:'上海'},{name:'连云港'}],
	        	                    [{name:'上海'},{name:'临沂'}],
	        	                    [{name:'上海'},{name:'柳州'}],
	        	                    [{name:'上海'},{name:'宁波'}],
	        	                    [{name:'上海'},{name:'南宁'}],
	        	                    [{name:'上海'},{name:'北京'}],
	        	                    [{name:'上海'},{name:'沈阳'}],
	        	                    [{name:'上海'},{name:'秦皇岛'}],
	        	                    [{name:'上海'},{name:'西安'}],
	        	                    [{name:'上海'},{name:'石家庄'}],
	        	                    [{name:'上海'},{name:'汕头'}],
	        	                    [{name:'上海'},{name:'深圳'}],
	        	                    [{name:'上海'},{name:'青岛'}],
	        	                    [{name:'上海'},{name:'济南'}],
	        	                    [{name:'上海'},{name:'天津'}],
	        	                    [{name:'上海'},{name:'太原'}],
	        	                    [{name:'上海'},{name:'乌鲁木齐'}],
	        	                    [{name:'上海'},{name:'潍坊'}],
	        	                    [{name:'上海'},{name:'威海'}],
	        	                    [{name:'上海'},{name:'温州'}],
	        	                    [{name:'上海'},{name:'武汉'}],
	        	                    [{name:'上海'},{name:'厦门'}],
	        	                    [{name:'上海'},{name:'西宁'}],
	        	                    [{name:'上海'},{name:'徐州'}],
	        	                    [{name:'上海'},{name:'烟台'}],
	        	                    [{name:'上海'},{name:'珠海'}],
	        	                    [{name:'广州'},{name:'北海'}],
	        	                    [{name:'广州'},{name:'郑州'}],
	        	                    [{name:'广州'},{name:'长春'}],
	        	                    [{name:'广州'},{name:'重庆'}],
	        	                    [{name:'广州'},{name:'长沙'}],
	        	                    [{name:'广州'},{name:'成都'}],
	        	                    [{name:'广州'},{name:'常州'}],
	        	                    [{name:'广州'},{name:'大连'}],
	        	                    [{name:'广州'},{name:'福州'}],
	        	                    [{name:'广州'},{name:'海口'}],
	        	                    [{name:'广州'},{name:'呼和浩特'}],
	        	                    [{name:'广州'},{name:'合肥'}],
	        	                    [{name:'广州'},{name:'杭州'}],
	        	                    [{name:'广州'},{name:'哈尔滨'}],
	        	                    [{name:'广州'},{name:'舟山'}],
	        	                    [{name:'广州'},{name:'银川'}],
	        	                    [{name:'广州'},{name:'南昌'}],
	        	                    [{name:'广州'},{name:'昆明'}],
	        	                    [{name:'广州'},{name:'贵阳'}],
	        	                    [{name:'广州'},{name:'兰州'}],
	        	                    [{name:'广州'},{name:'拉萨'}],
	        	                    [{name:'广州'},{name:'连云港'}],
	        	                    [{name:'广州'},{name:'临沂'}],
	        	                    [{name:'广州'},{name:'柳州'}],
	        	                    [{name:'广州'},{name:'宁波'}],
	        	                    [{name:'广州'},{name:'南京'}],
	        	                    [{name:'广州'},{name:'南宁'}],
	        	                    [{name:'广州'},{name:'南通'}],
	        	                    [{name:'广州'},{name:'北京'}],
	        	                    [{name:'广州'},{name:'上海'}],
	        	                    [{name:'广州'},{name:'沈阳'}],
	        	                    [{name:'广州'},{name:'西安'}],
	        	                    [{name:'广州'},{name:'石家庄'}],
	        	                    [{name:'广州'},{name:'汕头'}],
	        	                    [{name:'广州'},{name:'青岛'}],
	        	                    [{name:'广州'},{name:'济南'}],
	        	                    [{name:'广州'},{name:'天津'}],
	        	                    [{name:'广州'},{name:'太原'}],
	        	                    [{name:'广州'},{name:'乌鲁木齐'}],
	        	                    [{name:'广州'},{name:'温州'}],
	        	                    [{name:'广州'},{name:'武汉'}],
	        	                    [{name:'广州'},{name:'无锡'}],
	        	                    [{name:'广州'},{name:'厦门'}],
	        	                    [{name:'广州'},{name:'西宁'}],
	        	                    [{name:'广州'},{name:'徐州'}],
	        	                    [{name:'广州'},{name:'烟台'}],
	        	                    [{name:'广州'},{name:'盐城'}]
	        	                ],
	        	            },
	        	            geoCoord: {
	        	                '上海': [121.4648,31.2891],
	        	                '东莞': [113.8953,22.901],
	        	                '东营': [118.7073,37.5513],
	        	                '中山': [113.4229,22.478],
	        	                '临汾': [111.4783,36.1615],
	        	                '临沂': [118.3118,35.2936],
	        	                '丹东': [124.541,40.4242],
	        	                '丽水': [119.5642,28.1854],
	        	                '乌鲁木齐': [87.9236,43.5883],
	        	                '佛山': [112.8955,23.1097],
	        	                '保定': [115.0488,39.0948],
	        	                '兰州': [103.5901,36.3043],
	        	                '包头': [110.3467,41.4899],
	        	                '北京': [116.4551,40.2539],
	        	                '北海': [109.314,21.6211],
	        	                '南京': [118.8062,31.9208],
	        	                '南宁': [108.479,23.1152],
	        	                '南昌': [116.0046,28.6633],
	        	                '南通': [121.1023,32.1625],
	        	                '厦门': [118.1689,24.6478],
	        	                '台州': [121.1353,28.6688],
	        	                '合肥': [117.29,32.0581],
	        	                '呼和浩特': [111.4124,40.4901],
	        	                '咸阳': [108.4131,34.8706],
	        	                '哈尔滨': [127.9688,45.368],
	        	                '唐山': [118.4766,39.6826],
	        	                '嘉兴': [120.9155,30.6354],
	        	                '大同': [113.7854,39.8035],
	        	                '大连': [122.2229,39.4409],
	        	                '天津': [117.4219,39.4189],
	        	                '太原': [112.3352,37.9413],
	        	                '威海': [121.9482,37.1393],
	        	                '宁波': [121.5967,29.6466],
	        	                '宝鸡': [107.1826,34.3433],
	        	                '宿迁': [118.5535,33.7775],
	        	                '常州': [119.4543,31.5582],
	        	                '广州': [113.5107,23.2196],
	        	                '廊坊': [116.521,39.0509],
	        	                '延安': [109.1052,36.4252],
	        	                '张家口': [115.1477,40.8527],
	        	                '徐州': [117.5208,34.3268],
	        	                '德州': [116.6858,37.2107],
	        	                '惠州': [114.6204,23.1647],
	        	                '成都': [103.9526,30.7617],
	        	                '扬州': [119.4653,32.8162],
	        	                '承德': [117.5757,41.4075],
	        	                '拉萨': [91.1865,30.1465],
	        	                '无锡': [120.3442,31.5527],
	        	                '日照': [119.2786,35.5023],
	        	                '昆明': [102.9199,25.4663],
	        	                '杭州': [119.5313,29.8773],
	        	                '枣庄': [117.323,34.8926],
	        	                '柳州': [109.3799,24.9774],
	        	                '株洲': [113.5327,27.0319],
	        	                '武汉': [114.3896,30.6628],
	        	                '汕头': [117.1692,23.3405],
	        	                '江门': [112.6318,22.1484],
	        	                '沈阳': [123.1238,42.1216],
	        	                '沧州': [116.8286,38.2104],
	        	                '河源': [114.917,23.9722],
	        	                '泉州': [118.3228,25.1147],
	        	                '泰安': [117.0264,36.0516],
	        	                '泰州': [120.0586,32.5525],
	        	                '济南': [117.1582,36.8701],
	        	                '济宁': [116.8286,35.3375],
	        	                '海口': [110.3893,19.8516],
	        	                '淄博': [118.0371,36.6064],
	        	                '淮安': [118.927,33.4039],
	        	                '深圳': [114.5435,22.5439],
	        	                '清远': [112.9175,24.3292],
	        	                '温州': [120.498,27.8119],
	        	                '渭南': [109.7864,35.0299],
	        	                '湖州': [119.8608,30.7782],
	        	                '湘潭': [112.5439,27.7075],
	        	                '滨州': [117.8174,37.4963],
	        	                '潍坊': [119.0918,36.524],
	        	                '烟台': [120.7397,37.5128],
	        	                '玉溪': [101.9312,23.8898],
	        	                '珠海': [113.7305,22.1155],
	        	                '盐城': [120.2234,33.5577],
	        	                '盘锦': [121.9482,41.0449],
	        	                '石家庄': [114.4995,38.1006],
	        	                '福州': [119.4543,25.9222],
	        	                '秦皇岛': [119.2126,40.0232],
	        	                '绍兴': [120.564,29.7565],
	        	                '聊城': [115.9167,36.4032],
	        	                '肇庆': [112.1265,23.5822],
	        	                '舟山': [122.2559,30.2234],
	        	                '苏州': [120.6519,31.3989],
	        	                '莱芜': [117.6526,36.2714],
	        	                '菏泽': [115.6201,35.2057],
	        	                '营口': [122.4316,40.4297],
	        	                '葫芦岛': [120.1575,40.578],
	        	                '衡水': [115.8838,37.7161],
	        	                '衢州': [118.6853,28.8666],
	        	                '西宁': [101.4038,36.8207],
	        	                '西安': [109.1162,34.2004],
	        	                '贵阳': [106.6992,26.7682],
	        	                '连云港': [119.1248,34.552],
	        	                '邢台': [114.8071,37.2821],
	        	                '邯郸': [114.4775,36.535],
	        	                '郑州': [113.4668,34.6234],
	        	                '鄂尔多斯': [108.9734,39.2487],
	        	                '重庆': [107.7539,30.1904],
	        	                '金华': [120.0037,29.1028],
	        	                '铜川': [109.0393,35.1947],
	        	                '银川': [106.3586,38.1775],
	        	                '镇江': [119.4763,31.9702],
	        	                '长春': [125.8154,44.2584],
	        	                '长沙': [113.0823,28.2568],
	        	                '长治': [112.8625,36.4746],
	        	                '阳泉': [113.4778,38.0951],
	        	                '青岛': [120.4651,36.3373],
	        	                '韶关': [113.7964,24.7028]
	        	            }
	        	        },
	        	        {
	        	            name: '北京 Top10',
	        	            type: 'map',
	        	            mapType: 'china',
	        	            data:[],
	        	            markLine : {
	        	                smooth:true,
	        	                effect : {
	        	                    show: true,
	        	                    scaleSize: 1,
	        	                    period: 30,
	        	                    color: '#fff',
	        	                    shadowBlur: 10
	        	                },
	        	                itemStyle : {
	        	                    normal: {
	        	                        borderWidth:1,
	        	                        lineStyle: {
	        	                            type: 'solid',
	        	                            shadowBlur: 10
	        	                        }
	        	                    }
	        	                },
	        	                data : [
	        	                    [{name:'北京'}, {name:'上海',value:95}],
	        	                    [{name:'北京'}, {name:'广州',value:90}],
	        	                    [{name:'北京'}, {name:'大连',value:80}],
	        	                    [{name:'北京'}, {name:'南宁',value:70}],
	        	                    [{name:'北京'}, {name:'南昌',value:60}],
	        	                    [{name:'北京'}, {name:'拉萨',value:50}],
	        	                    [{name:'北京'}, {name:'长春',value:40}],
	        	                    [{name:'北京'}, {name:'包头',value:30}],
	        	                    [{name:'北京'}, {name:'重庆',value:20}],
	        	                    [{name:'北京'}, {name:'常州',value:10}]
	        	                ]
	        	            },
	        	            markPoint : {
	        	                symbol:'emptyCircle',
	        	                symbolSize : function (v){
	        	                    return 10 + v/10
	        	                },
	        	                effect : {
	        	                    show: true,
	        	                    shadowBlur : 0
	        	                },
	        	                itemStyle:{
	        	                    normal:{
	        	                        label:{show:false}
	        	                    },
	        	                    emphasis: {
	        	                        label:{position:'top'}
	        	                    }
	        	                },
	        	                data : [
	        	                    {name:'上海',value:95},
	        	                    {name:'广州',value:90},
	        	                    {name:'大连',value:80},
	        	                    {name:'南宁',value:70},
	        	                    {name:'南昌',value:60},
	        	                    {name:'拉萨',value:50},
	        	                    {name:'长春',value:40},
	        	                    {name:'包头',value:30},
	        	                    {name:'重庆',value:20},
	        	                    {name:'常州',value:10}
	        	                ]
	        	            }
	        	        },
	        	        {
	        	            name: '上海 Top10',
	        	            type: 'map',
	        	            mapType: 'china',
	        	            data:[],
	        	            markLine : {
	        	                smooth:true,
	        	                effect : {
	        	                    show: true,
	        	                    scaleSize: 1,
	        	                    period: 30,
	        	                    color: '#fff',
	        	                    shadowBlur: 10
	        	                },
	        	                itemStyle : {
	        	                    normal: {
	        	                        borderWidth:1,
	        	                        lineStyle: {
	        	                            type: 'solid',
	        	                            shadowBlur: 10
	        	                        }
	        	                    }
	        	                },
	        	                data : [
	        	                    [{name:'上海'},{name:'包头',value:95}],
	        	                    [{name:'上海'},{name:'昆明',value:90}],
	        	                    [{name:'上海'},{name:'广州',value:80}],
	        	                    [{name:'上海'},{name:'郑州',value:70}],
	        	                    [{name:'上海'},{name:'长春',value:60}],
	        	                    [{name:'上海'},{name:'重庆',value:50}],
	        	                    [{name:'上海'},{name:'长沙',value:40}],
	        	                    [{name:'上海'},{name:'北京',value:30}],
	        	                    [{name:'上海'},{name:'丹东',value:20}],
	        	                    [{name:'上海'},{name:'大连',value:10}]
	        	                ]
	        	            },
	        	            markPoint : {
	        	                symbol:'emptyCircle',
	        	                symbolSize : function (v){
	        	                    return 10 + v/10
	        	                },
	        	                effect : {
	        	                    show: true,
	        	                    shadowBlur : 0
	        	                },
	        	                itemStyle:{
	        	                    normal:{
	        	                        label:{show:false}
	        	                    },
	        	                    emphasis: {
	        	                        label:{position:'top'}
	        	                    }
	        	                },
	        	                data : [
	        	                    {name:'包头',value:95},
	        	                    {name:'昆明',value:90},
	        	                    {name:'广州',value:80},
	        	                    {name:'郑州',value:70},
	        	                    {name:'长春',value:60},
	        	                    {name:'重庆',value:50},
	        	                    {name:'长沙',value:40},
	        	                    {name:'北京',value:30},
	        	                    {name:'丹东',value:20},
	        	                    {name:'大连',value:10}
	        	                ]
	        	            }
	        	        },
	        	        {
	        	            name: '广州 Top10',
	        	            type: 'map',
	        	            mapType: 'china',
	        	            data:[],
	        	            markLine : {
	        	                smooth:true,
	        	                effect : {
	        	                    show: true,
	        	                    scaleSize: 1,
	        	                    period: 30,
	        	                    color: '#fff',
	        	                    shadowBlur: 10
	        	                },
	        	                itemStyle : {
	        	                    normal: {
	        	                        borderWidth:1,
	        	                        lineStyle: {
	        	                            type: 'solid',
	        	                            shadowBlur: 10
	        	                        }
	        	                    }
	        	                },
	        	                data : [
	        	                    [{name:'广州'},{name:'福州',value:95}],
	        	                    [{name:'广州'},{name:'太原',value:90}],
	        	                    [{name:'广州'},{name:'长春',value:80}],
	        	                    [{name:'广州'},{name:'重庆',value:70}],
	        	                    [{name:'广州'},{name:'西安',value:60}],
	        	                    [{name:'广州'},{name:'成都',value:50}],
	        	                    [{name:'广州'},{name:'常州',value:40}],
	        	                    [{name:'广州'},{name:'北京',value:30}],
	        	                    [{name:'广州'},{name:'北海',value:20}],
	        	                    [{name:'广州'},{name:'海口',value:10}]
	        	                ]
	        	            },
	        	            markPoint : {
	        	                symbol:'emptyCircle',
	        	                symbolSize : function (v){
	        	                    return 10 + v/10
	        	                },
	        	                effect : {
	        	                    show: true,
	        	                    shadowBlur : 0
	        	                },
	        	                itemStyle:{
	        	                    normal:{
	        	                        label:{show:false}
	        	                    },
	        	                    emphasis: {
	        	                        label:{position:'top'}
	        	                    }
	        	                },
	        	                data : [
	        	                    {name:'福州',value:95},
	        	                    {name:'太原',value:90},
	        	                    {name:'长春',value:80},
	        	                    {name:'重庆',value:70},
	        	                    {name:'西安',value:60},
	        	                    {name:'成都',value:50},
	        	                    {name:'常州',value:40},
	        	                    {name:'北京',value:30},
	        	                    {name:'北海',value:20},
	        	                    {name:'海口',value:10}
	        	                ]
	        	            }
	        	        }
	        	    ]
	        	};
	        
	        optionMain2 = {
	        	    title : {
	        	        text: '浏览器占比变化',
	        	        subtext: '纯属虚构',
	        	        x:'right',
	        	        y:'bottom'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient : 'vertical',
	        	        x : 'left',
	        	        data:['Chrome','Firefox','Safari','IE9+','IE8-']
	        	    },
	        	    toolbox: {
	        	        show : true,
	        	        feature : {
	        	            mark : {show: true},
	        	            dataView : {show: true, readOnly: false},
	        	            restore : {show: true},
	        	            saveAsImage : {show: true}
	        	        }
	        	    },
	        	    calculable : false,
	        	    series : (function (){
	        	        var series = [];
	        	        for (var i = 0; i < 30; i++) {
	        	            series.push({
	        	                name:'浏览器（数据纯属虚构）',
	        	                type:'pie',
	        	                itemStyle : {normal : {
	        	                    label : {show : i > 28},
	        	                    labelLine : {show : i > 28, length:20}
	        	                }},
	        	                radius : [i * 4 + 40, i * 4 + 43],
	        	                data:[
	        	                    {value: i * 128 + 80,  name:'Chrome'},
	        	                    {value: i * 64  + 160,  name:'Firefox'},
	        	                    {value: i * 32  + 320,  name:'Safari'},
	        	                    {value: i * 16  + 640,  name:'IE9+'},
	        	                    {value: i * 8  + 1280, name:'IE8-'}
	        	                ]
	        	            })
	        	        }
	        	        series[0].markPoint = {
	        	            symbol:'emptyCircle',
	        	            symbolSize:series[0].radius[0],
	        	            effect:{show:true,scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
	        	            data:[{x:'50%',y:'50%'}]
	        	        };
	        	        return series;
	        	    })()
	        	};
	        	setTimeout(function (){
	        	    var _ZR = myChart.getZrender();
	        	    var TextShape = require('zrender/shape/Text');
	        	    // 补充千层饼
	        	    _ZR.addShape(new TextShape({
	        	        style : {
	        	            x : _ZR.getWidth() / 2,
	        	            y : _ZR.getHeight() / 2,
	        	            color: '#666',
	        	            text : '恶梦的过去',
	        	            textAlign : 'center'
	        	        }
	        	    }));
	        	    _ZR.addShape(new TextShape({
	        	        style : {
	        	            x : _ZR.getWidth() / 2 + 200,
	        	            y : _ZR.getHeight() / 2,
	        	            brushType:'fill',
	        	            color: 'orange',
	        	            text : '美好的未来',
	        	            textAlign : 'left',
	        	            textFont:'normal 20px 微软雅黑'
	        	        }
	        	    }));
	        	    _ZR.refresh();
	        	}, 2000);
	        	
	        	optionMain3 = {
	        		    title : {
	        		        text: '南丁格尔玫瑰图',
	        		        subtext: '纯属虚构',
	        		        x:'center'
	        		    },
	        		    tooltip : {
	        		        trigger: 'item',
	        		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        		    },
	        		    legend: {
	        		        x : 'center',
	        		        y : 'bottom',
	        		        data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
	        		    },
	        		    toolbox: {
	        		        show : true,
	        		        feature : {
	        		            mark : {show: true},
	        		            dataView : {show: true, readOnly: false},
	        		            magicType : {
	        		                show: true, 
	        		                type: ['pie', 'funnel']
	        		            },
	        		            restore : {show: true},
	        		            saveAsImage : {show: true}
	        		        }
	        		    },
	        		    calculable : true,
	        		    series : [
	        		        {
	        		            name:'半径模式',
	        		            type:'pie',
	        		            radius : [20, 110],
	        		            center : ['25%', 200],
	        		            roseType : 'radius',
	        		            width: '40%',       // for funnel
	        		            max: 40,            // for funnel
	        		            itemStyle : {
	        		                normal : {
	        		                    label : {
	        		                        show : false
	        		                    },
	        		                    labelLine : {
	        		                        show : false
	        		                    }
	        		                },
	        		                emphasis : {
	        		                    label : {
	        		                        show : true
	        		                    },
	        		                    labelLine : {
	        		                        show : true
	        		                    }
	        		                }
	        		            },
	        		            data:[
	        		                {value:10, name:'rose1'},
	        		                {value:5, name:'rose2'},
	        		                {value:15, name:'rose3'},
	        		                {value:25, name:'rose4'},
	        		                {value:20, name:'rose5'},
	        		                {value:35, name:'rose6'},
	        		                {value:30, name:'rose7'},
	        		                {value:40, name:'rose8'}
	        		            ]
	        		        },
	        		        {
	        		            name:'面积模式',
	        		            type:'pie',
	        		            radius : [30, 110],
	        		            center : ['75%', 200],
	        		            roseType : 'area',
	        		            x: '50%',               // for funnel
	        		            max: 40,                // for funnel
	        		            sort : 'ascending',     // for funnel
	        		            data:[
	        		                {value:10, name:'rose1'},
	        		                {value:5, name:'rose2'},
	        		                {value:15, name:'rose3'},
	        		                {value:25, name:'rose4'},
	        		                {value:20, name:'rose5'},
	        		                {value:35, name:'rose6'},
	        		                {value:30, name:'rose7'},
	        		                {value:40, name:'rose8'}
	        		            ]
	        		        }
	        		    ]
	        		};
	        	optionMain4 = {
	        		    tooltip : {
	        		        show: true,
	        		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        		    },
	        		    legend: {
	        		        orient : 'vertical',
	        		        x : 'left',
	        		        data:['直达','营销广告','搜索引擎','邮件营销','联盟广告','视频广告','百度','谷歌','必应','其他']
	        		    },
	        		    toolbox: {
	        		        show : true,
	        		        feature : {
	        		            mark : {show: true},
	        		            dataView : {show: true, readOnly: false},
	        		            restore : {show: true},
	        		            saveAsImage : {show: true}
	        		        }
	        		    },
	        		    calculable : true,
	        		    series : [
	        		        {
	        		            name:'访问来源',
	        		            type:'pie',
	        		            center : ['35%', 200],
	        		            radius : 80,
	        		            itemStyle : {
	        		                normal : {
	        		                    label : {
	        		                        position : 'inner',
	        		                        formatter : function (params) {                         
	        		                          return (params.percent - 0).toFixed(0) + '%'
	        		                        }
	        		                    },
	        		                    labelLine : {
	        		                        show : false
	        		                    }
	        		                },
	        		                emphasis : {
	        		                    label : {
	        		                        show : true,
	        		                        formatter : "{b}\n{d}%"
	        		                    }
	        		                }
	        		                
	        		            },
	        		            data:[
	        		                {value:335, name:'直达'},
	        		                {value:679, name:'营销广告'},
	        		                {value:1548, name:'搜索引擎'}
	        		            ]
	        		        },
	        		        {
	        		            name:'访问来源',
	        		            type:'pie',
	        		            center : ['35%', 200],
	        		            radius : [110, 140],
	        		            data:[
	        		                {value:335, name:'直达'},
	        		                {value:310, name:'邮件营销'},
	        		                {value:234, name:'联盟广告'},
	        		                {value:135, name:'视频广告'},
	        		                {
	        		                    value:1048,
	        		                    name:'百度',
	        		                    itemStyle : {
	        		                        normal : {
	        		                            color : (function (){
	        		                                var zrColor = require('zrender/tool/color');
	        		                                return zrColor.getRadialGradient(
	        		                                    300, 200, 110, 300, 200, 140,
	        		                                    [[0, 'rgba(255,255,0,1)'],[1, 'rgba(30,144,250,1)']]
	        		                                )
	        		                            })(),
	        		                            label : {
	        		                                textStyle : {
	        		                                    color : 'rgba(30,144,255,0.8)',
	        		                                    align : 'center',
	        		                                    baseline : 'middle',
	        		                                    fontFamily : '微软雅黑',
	        		                                    fontSize : 30,
	        		                                    fontWeight : 'bolder'
	        		                                }
	        		                            },
	        		                            labelLine : {
	        		                                length : 40,
	        		                                lineStyle : {
	        		                                    color : '#f0f',
	        		                                    width : 3,
	        		                                    type : 'dotted'
	        		                                }
	        		                            }
	        		                        }
	        		                    }
	        		                },
	        		                {value:251, name:'谷歌'},
	        		                {
	        		                    value:102,
	        		                    name:'必应',
	        		                    itemStyle : {
	        		                        normal : {
	        		                            label : {
	        		                                show : false
	        		                            },
	        		                            labelLine : {
	        		                                show : false
	        		                            }
	        		                        },
	        		                        emphasis : {
	        		                            label : {
	        		                                show : true
	        		                            },
	        		                            labelLine : {
	        		                                show : true,
	        		                                length : 50
	        		                            }
	        		                        }
	        		                    }
	        		                },
	        		                {value:147, name:'其他'}
	        		            ]
	        		        },
	        		        {
	        		            name:'访问来源',
	        		            type:'pie',
	        		            clockWise:true,
	        		            startAngle: 135,
	        		            center : ['75%', 200],
	        		            radius : [80, 120],
	        		            itemStyle :　{
	        		                normal : {
	        		                    label : {
	        		                        show : false
	        		                    },
	        		                    labelLine : {
	        		                        show : false
	        		                    }
	        		                },
	        		                emphasis : {
	        		                    color: (function (){
	        		                        var zrColor = require('zrender/tool/color');
	        		                        return zrColor.getRadialGradient(
	        		                            650, 200, 80, 650, 200, 120,
	        		                            [[0, 'rgba(255,255,0,1)'],[1, 'rgba(255,0,0,1)']]
	        		                        )
	        		                    })(),
	        		                    label : {
	        		                        show : true,
	        		                        position : 'center',
	        		                        formatter : "{d}%",
	        		                        textStyle : {
	        		                            color : 'red',
	        		                            fontSize : '30',
	        		                            fontFamily : '微软雅黑',
	        		                            fontWeight : 'bold'
	        		                        }
	        		                    }
	        		                }
	        		            },
	        		            data:[
	        		                {value:335, name:'直达'},
	        		                {value:310, name:'邮件营销'},
	        		                {value:234, name:'联盟广告'},
	        		                {value:135, name:'视频广告'},
	        		                {value:1548, name:'搜索引擎'}
	        		            ],
	        		            markPoint : {
	        		                symbol: 'star',
	        		                data : [
	        		                    {name : '最大', value : 1548, x:'80%', y:50, symbolSize:32}
	        		                ]
	        		            }
	        		        }
	        		    ]
	        		};
	        	optionMain5 = {
	        		    tooltip : {
	        		        trigger: 'axis'
	        		    },
	        		    legend: {
	        		        x : 'left',
	        		        data:['图一','图二','图三']
	        		    },
	        		    toolbox: {
	        		        show : true,
	        		        feature : {
	        		            mark : {show: true},
	        		            dataView : {show: true, readOnly: false},
	        		            restore : {show: true},
	        		            saveAsImage : {show: true}
	        		        }
	        		    },
	        		    calculable: true,
	        		    polar : [
	        		        {
	        		            indicator : [
	        		                { text : '指标一' },
	        		                { text : '指标二' },
	        		                { text : '指标三' },
	        		                { text : '指标四' },
	        		                { text : '指标五' }
	        		            ],
	        		            center : ['25%',210],
	        		            radius : 150,
	        		            startAngle: 90,
	        		            splitNumber: 8,
	        		            name : {
	        		                formatter:'【{value}】',
	        		                textStyle: {color:'red'}
	        		            },
	        		            scale: true,
	        		            type: 'circle',
	        		            axisLine: {            // 坐标轴线
	        		                show: true,        // 默认显示，属性show控制显示与否
	        		                lineStyle: {       // 属性lineStyle控制线条样式
	        		                    color: 'green',
	        		                    width: 2,
	        		                    type: 'solid'
	        		                }
	        		            },
	        		            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
	        		                show: true,
	        		                // formatter: null,
	        		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	        		                    color: '#ccc'
	        		                }
	        		            },
	        		            splitArea : {
	        		                show : true,
	        		                areaStyle : {
	        		                    color: ['rgba(250,0,250,0.3)','rgba(0,200,200,0.3)']
	        		                }
	        		            },
	        		            splitLine : {
	        		                show : true,
	        		                lineStyle : {
	        		                    width : 2,
	        		                    color : 'yellow'
	        		                }
	        		            }
	        		        },
	        		        {
	        		            indicator : [
	        		                { text : '语文', max  : 150 },
	        		                { text : '数学', max  : 150 },
	        		                { text : '英语', max  : 150 },
	        		                { text : '物理', max  : 120 },
	        		                { text : '化学', max  : 108 },
	        		                { text : '生物', max  : 72 }
	        		            ],
	        		            center : ['75%', 210],
	        		            radius : 150
	        		        }
	        		    ],
	        		    series : [
	        		        {
	        		            name: '雷达图',
	        		            type: 'radar',
	        		            itemStyle: {
	        		                emphasis: {
	        		                    // color: 各异,
	        		                    lineStyle: {
	        		                        width: 4
	        		                    }
	        		                }
	        		            },
	        		            data : [
	        		                {
	        		                    value : [100, 8, 0.40, -80, 2000],
	        		                    name : '图一',
	        		                    symbol: 'star5',
	        		                    symbolSize: 4,           // 可计算特性参数，空数据拖拽提示图形大小
	        		                    itemStyle: {
	        		                        normal: {
	        		                            lineStyle: {
	        		                                type: 'dashed'
	        		                            }
	        		                        }
	        		                    }
	        		                },
	        		                {
	        		                    value : [10, 3, 0.20, -100, 1000],
	        		                    name : '图二',
	        		                    itemStyle: {
	        		                        normal: {
	        		                            areaStyle: {
	        		                                type: 'default'
	        		                            }
	        		                        }
	        		                    }
	        		                },
	        		                {
	        		                    value : [20, 3, 0.3, -13.5, 3000],
	        		                    name : '图三',
	        		                    symbol: 'none',         // 拐点图形类型，非标准参数
	        		                    itemStyle: {
	        		                        normal: {
	        		                            lineStyle: {
	        		                                type: 'dotted'
	        		                            }
	        		                        }
	        		                    }
	        		                }
	        		            ]
	        		        },
	        		        {
	        		            name: '成绩单',
	        		            type: 'radar',
	        		            polarIndex : 1,
	        		            itemStyle: {
	        		                normal: {
	        		                    areaStyle: {
	        		                        type: 'default'
	        		                    }
	        		                }
	        		            },
	        		            data : [
	        		                {
	        		                    value : [120, 118, 130, 100, 99, 70],
	        		                    name : '张三',
	        		                    itemStyle: {
	        		                        normal: {
	        		                            color: function(params) {
	        		                                var value = params.data
	        		                                return isNaN(value) 
	        		                                       ? undefined
	        		                                       : (value >= 120 ? 'green' : 'red')
	        		                            },
	        		                            label: {
	        		                                show: true,
	        		                                formatter:function(params) {
	        		                                    return params.value;
	        		                                }
	        		                            },
	        		                            areaStyle: {
	        		                                color: (function (){
	        		                                    var zrColor = require('zrender/tool/color');
	        		                                    var x = document.getElementById('main').offsetWidth - 250;
	        		                                    return zrColor.getRadialGradient(
	        		                                        x, 210, 0, x, 200, 150,
	        		                                        [[0, 'rgba(255,255,0,0.3)'],[1, 'rgba(255,0,0,0.5)']]
	        		                                    )
	        		                                })()
	        		                            }
	        		                        }
	        		                    }
	        		                },
	        		                {
	        		                    value : [90, 113, 140, 30, 70, 60],
	        		                    name : '李四',
	        		                    itemStyle: {
	        		                        normal: {
	        		                            lineStyle: {
	        		                                type: 'dashed'
	        		                            }
	        		                        }
	        		                    }
	        		                }
	        		            ],
	        		            markPoint : {
	        		                symbol: 'emptyHeart',
	        		                data : [
	        		                    {name : '打酱油的标注', value : 100, x:'50%', y:'15%', symbolSize:32}
	        		                ]
	        		            }
	        		        }
	        		    ]
	        		};
	        	
	        	optionMain6 = {
	        		    title : {
	        		        text: '人物关系：乔布斯',
	        		        subtext: '数据来自人立方',
	        		        x:'right',
	        		        y:'bottom'
	        		    },
	        		    tooltip : {
	        		        trigger: 'item',
	        		        formatter: '{a} : {b}'
	        		    },
	        		    toolbox: {
	        		        show : true,
	        		        feature : {
	        		            restore : {show: true},
	        		            magicType: {show: true, type: ['force', 'chord']},
	        		            saveAsImage : {show: true}
	        		        }
	        		    },
	        		    legend: {
	        		        x: 'left',
	        		        data:['家人','朋友']
	        		    },
	        		    series : [
	        		        {
	        		            type:'force',
	        		            name : "人物关系",
	        		            ribbonType: false,
	        		            categories : [
	        		                {
	        		                    name: '人物'
	        		                },
	        		                {
	        		                    name: '家人'
	        		                },
	        		                {
	        		                    name:'朋友'
	        		                }
	        		            ],
	        		            itemStyle: {
	        		                normal: {
	        		                    label: {
	        		                        show: true,
	        		                        textStyle: {
	        		                            color: '#333'
	        		                        }
	        		                    },
	        		                    nodeStyle : {
	        		                        brushType : 'both',
	        		                        borderColor : 'rgba(255,215,0,0.4)',
	        		                        borderWidth : 1
	        		                    },
	        		                    linkStyle: {
	        		                        type: 'curve'
	        		                    }
	        		                },
	        		                emphasis: {
	        		                    label: {
	        		                        show: false
	        		                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
	        		                    },
	        		                    nodeStyle : {
	        		                        //r: 30
	        		                    },
	        		                    linkStyle : {}
	        		                }
	        		            },
	        		            useWorker: false,
	        		            minRadius : 15,
	        		            maxRadius : 25,
	        		            gravity: 1.1,
	        		            scaling: 1.1,
	        		            roam: 'move',
	        		            nodes:[
	        		                {category:0, name: '乔布斯', value : 10, label: '乔布斯\n（主要）'},
	        		                {category:1, name: '丽萨-乔布斯',value : 2},
	        		                {category:1, name: '保罗-乔布斯',value : 3},
	        		                {category:1, name: '克拉拉-乔布斯',value : 3},
	        		                {category:1, name: '劳伦-鲍威尔',value : 7},
	        		                {category:2, name: '史蒂夫-沃兹尼艾克',value : 5},
	        		                {category:2, name: '奥巴马',value : 8},
	        		                {category:2, name: '比尔-盖茨',value : 9},
	        		                {category:2, name: '乔纳森-艾夫',value : 4},
	        		                {category:2, name: '蒂姆-库克',value : 4},
	        		                {category:2, name: '龙-韦恩',value : 1},
	        		            ],
	        		            links : [
	        		                {source : '丽萨-乔布斯', target : '乔布斯', weight : 1, name: '女儿'},
	        		                {source : '保罗-乔布斯', target : '乔布斯', weight : 2, name: '父亲'},
	        		                {source : '克拉拉-乔布斯', target : '乔布斯', weight : 1, name: '母亲'},
	        		                {source : '劳伦-鲍威尔', target : '乔布斯', weight : 2},
	        		                {source : '史蒂夫-沃兹尼艾克', target : '乔布斯', weight : 3, name: '合伙人'},
	        		                {source : '奥巴马', target : '乔布斯', weight : 1},
	        		                {source : '比尔-盖茨', target : '乔布斯', weight : 6, name: '竞争对手'},
	        		                {source : '乔纳森-艾夫', target : '乔布斯', weight : 1, name: '爱将'},
	        		                {source : '蒂姆-库克', target : '乔布斯', weight : 1},
	        		                {source : '龙-韦恩', target : '乔布斯', weight : 1},
	        		                {source : '克拉拉-乔布斯', target : '保罗-乔布斯', weight : 1},
	        		                {source : '奥巴马', target : '保罗-乔布斯', weight : 1},
	        		                {source : '奥巴马', target : '克拉拉-乔布斯', weight : 1},
	        		                {source : '奥巴马', target : '劳伦-鲍威尔', weight : 1},
	        		                {source : '奥巴马', target : '史蒂夫-沃兹尼艾克', weight : 1},
	        		                {source : '比尔-盖茨', target : '奥巴马', weight : 6},
	        		                {source : '比尔-盖茨', target : '克拉拉-乔布斯', weight : 1},
	        		                {source : '蒂姆-库克', target : '奥巴马', weight : 1}
	        		            ]
	        		        }
	        		    ]
	        		};
	        		var ecConfig = require('echarts/config');
	        		function focus(param) {
	        		    var data = param.data;
	        		    var links = option.series[0].links;
	        		    var nodes = option.series[0].nodes;
	        		    if (
	        		        data.source !== undefined
	        		        && data.target !== undefined
	        		    ) { //点击的是边
	        		        var sourceNode = nodes.filter(function (n) {return n.name == data.source})[0];
	        		        var targetNode = nodes.filter(function (n) {return n.name == data.target})[0];
	        		        console.log("选中了边 " + sourceNode.name + ' -> ' + targetNode.name + ' (' + data.weight + ')');
	        		    } else { // 点击的是点
	        		        console.log("选中了" + data.name + '(' + data.value + ')');
	        		    }
	        		}
	        		myChart6.on(ecConfig.EVENT.CLICK, focus)

	        		myChart6.on(ecConfig.EVENT.FORCE_LAYOUT_END, function () {
	        		    console.log(myChart.chart.force.getPosition());
	        		});
	        		
	        		var nodes = [];
	        		var links = [];
	        		var constMaxDepth = 2;
	        		var constMaxChildren = 7;
	        		var constMinChildren = 4;
	        		var constMaxRadius = 10;
	        		var constMinRadius = 2;

	        		function rangeRandom(min, max) {
	        		    return Math.random() * (max - min) + min;
	        		}

	        		function createRandomNode(depth) {
	        		    var node = {
	        		        name : 'NODE_' + nodes.length,
	        		        value : rangeRandom(constMinRadius, constMaxRadius),
	        		        // Custom properties
	        		        id : nodes.length,
	        		        depth : depth,
	        		        category : depth === constMaxDepth ? 0 : 1
	        		    }
	        		    nodes.push(node);

	        		    return node;
	        		}

	        		function forceMockThreeData() {
	        		    var depth = 0;
	        		    var rootNode = {
	        		        name : 'ROOT',
	        		        value : rangeRandom(constMinRadius, constMaxRadius),
	        		        // Custom properties
	        		        id : 0,
	        		        depth : 0,
	        		        category : 2
	        		    }
	        		    nodes.push(rootNode);

	        		    function mock(parentNode, depth) {
	        		        var nChildren = Math.round(rangeRandom(constMinChildren, constMaxChildren));
	        		        
	        		        for (var i = 0; i < nChildren; i++) {
	        		            var childNode = createRandomNode(depth);
	        		            links.push({
	        		                source : parentNode.id,
	        		                target : childNode.id,
	        		                weight : 1 
	        		            });
	        		            if (depth < constMaxDepth) {
	        		                mock(childNode, depth + 1);
	        		            }
	        		        }
	        		    }

	        		    mock(rootNode, 0);
	        		}

	        		forceMockThreeData();

	        		optionMain7 = {
	        		    title : {
	        		        text: 'Force',
	        		        subtext: 'Force-directed tree',
	        		        x:'right',
	        		        y:'bottom'
	        		    },
	        		    tooltip : {
	        		        trigger: 'item',
	        		        formatter: '{a} : {b}'
	        		    },
	        		    toolbox: {
	        		        show : true,
	        		        feature : {
	        		            restore : {show: true},
	        		            magicType: {show: true, type: ['force', 'chord']},
	        		            saveAsImage : {show: true}
	        		        }
	        		    },
	        		    legend: {
	        		        x: 'left',
	        		        data:['叶子节点','非叶子节点', '根节点']
	        		    },
	        		    series : [
	        		        {
	        		            type:'force',
	        		            name : "Force tree",
	        		            ribbonType: false,
	        		            categories : [
	        		                {
	        		                    name: '叶子节点'
	        		                },
	        		                {
	        		                    name: '非叶子节点'
	        		                },
	        		                {
	        		                    name: '根节点'
	        		                }
	        		            ],
	        		            itemStyle: {
	        		                normal: {
	        		                    label: {
	        		                        show: false
	        		                    },
	        		                    nodeStyle : {
	        		                        brushType : 'both',
	        		                        borderColor : 'rgba(255,215,0,0.6)',
	        		                        borderWidth : 1
	        		                    }
	        		                }
	        		            },
	        		            minRadius : constMinRadius,
	        		            maxRadius : constMaxRadius,
	        		            coolDown: 0.995,
	        		            steps: 10,
	        		            nodes : nodes,
	        		            links : links,
	        		            steps: 1
	        		        }
	        		    ]
	        		};
	        		
	        		optionMain8 = {
	        			    title : {
	        			        text: '男性女性身高体重分布',
	        			        subtext: '抽样调查来自: Heinz  2003'
	        			    },
	        			    tooltip : {
	        			        trigger: 'axis',
	        			        showDelay : 0,
	        			        formatter : function (params) {
	        			            if (params.value.length > 1) {
	        			                return params.seriesName + ' :<br/>'
	        			                   + params.value[0] + 'cm ' 
	        			                   + params.value[1] + 'kg ';
	        			            }
	        			            else {
	        			                return params.seriesName + ' :<br/>'
	        			                   + params.name + ' : '
	        			                   + params.value + 'kg ';
	        			            }
	        			        },  
	        			        axisPointer:{
	        			            show: true,
	        			            type : 'cross',
	        			            lineStyle: {
	        			                type : 'dashed',
	        			                width : 1
	        			            }
	        			        }
	        			    },
	        			    legend: {
	        			        data:['女性','男性']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        feature : {
	        			            mark : {show: true},
	        			            dataZoom : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    xAxis : [
	        			        {
	        			            type : 'value',
	        			            scale:true,
	        			            axisLabel : {
	        			                formatter: '{value} cm'
	        			            }
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'value',
	        			            scale:true,
	        			            axisLabel : {
	        			                formatter: '{value} kg'
	        			            }
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'女性',
	        			            type:'scatter',
	        			            data: [[161.2, 51.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
	        			                [170.0, 59.0], [159.1, 47.6], [166.0, 69.8], [176.2, 66.8], [160.2, 75.2],
	        			                [172.5, 55.2], [170.9, 54.2], [172.9, 62.5], [153.4, 42.0], [160.0, 50.0],
	        			                [147.2, 49.8], [168.2, 49.2], [175.0, 73.2], [157.0, 47.8], [167.6, 68.8],
	        			                [159.5, 50.6], [175.0, 82.5], [166.8, 57.2], [176.5, 87.8], [170.2, 72.8],
	        			                [174.0, 54.5], [173.0, 59.8], [179.9, 67.3], [170.5, 67.8], [160.0, 47.0],
	        			                [154.4, 46.2], [162.0, 55.0], [176.5, 83.0], [160.0, 54.4], [152.0, 45.8],
	        			                [162.1, 53.6], [170.0, 73.2], [160.2, 52.1], [161.3, 67.9], [166.4, 56.6],
	        			                [168.9, 62.3], [163.8, 58.5], [167.6, 54.5], [160.0, 50.2], [161.3, 60.3],
	        			                [167.6, 58.3], [165.1, 56.2], [160.0, 50.2], [170.0, 72.9], [157.5, 59.8],
	        			                [167.6, 61.0], [160.7, 69.1], [163.2, 55.9], [152.4, 46.5], [157.5, 54.3],
	        			                [168.3, 54.8], [180.3, 60.7], [165.5, 60.0], [165.0, 62.0], [164.5, 60.3],
	        			                [156.0, 52.7], [160.0, 74.3], [163.0, 62.0], [165.7, 73.1], [161.0, 80.0],
	        			                [162.0, 54.7], [166.0, 53.2], [174.0, 75.7], [172.7, 61.1], [167.6, 55.7],
	        			                [151.1, 48.7], [164.5, 52.3], [163.5, 50.0], [152.0, 59.3], [169.0, 62.5],
	        			                [164.0, 55.7], [161.2, 54.8], [155.0, 45.9], [170.0, 70.6], [176.2, 67.2],
	        			                [170.0, 69.4], [162.5, 58.2], [170.3, 64.8], [164.1, 71.6], [169.5, 52.8],
	        			                [163.2, 59.8], [154.5, 49.0], [159.8, 50.0], [173.2, 69.2], [170.0, 55.9],
	        			                [161.4, 63.4], [169.0, 58.2], [166.2, 58.6], [159.4, 45.7], [162.5, 52.2],
	        			                [159.0, 48.6], [162.8, 57.8], [159.0, 55.6], [179.8, 66.8], [162.9, 59.4],
	        			                [161.0, 53.6], [151.1, 73.2], [168.2, 53.4], [168.9, 69.0], [173.2, 58.4],
	        			                [171.8, 56.2], [178.0, 70.6], [164.3, 59.8], [163.0, 72.0], [168.5, 65.2],
	        			                [166.8, 56.6], [172.7, 105.2], [163.5, 51.8], [169.4, 63.4], [167.8, 59.0],
	        			                [159.5, 47.6], [167.6, 63.0], [161.2, 55.2], [160.0, 45.0], [163.2, 54.0],
	        			                [162.2, 50.2], [161.3, 60.2], [149.5, 44.8], [157.5, 58.8], [163.2, 56.4],
	        			                [172.7, 62.0], [155.0, 49.2], [156.5, 67.2], [164.0, 53.8], [160.9, 54.4],
	        			                [162.8, 58.0], [167.0, 59.8], [160.0, 54.8], [160.0, 43.2], [168.9, 60.5],
	        			                [158.2, 46.4], [156.0, 64.4], [160.0, 48.8], [167.1, 62.2], [158.0, 55.5],
	        			                [167.6, 57.8], [156.0, 54.6], [162.1, 59.2], [173.4, 52.7], [159.8, 53.2],
	        			                [170.5, 64.5], [159.2, 51.8], [157.5, 56.0], [161.3, 63.6], [162.6, 63.2],
	        			                [160.0, 59.5], [168.9, 56.8], [165.1, 64.1], [162.6, 50.0], [165.1, 72.3],
	        			                [166.4, 55.0], [160.0, 55.9], [152.4, 60.4], [170.2, 69.1], [162.6, 84.5],
	        			                [170.2, 55.9], [158.8, 55.5], [172.7, 69.5], [167.6, 76.4], [162.6, 61.4],
	        			                [167.6, 65.9], [156.2, 58.6], [175.2, 66.8], [172.1, 56.6], [162.6, 58.6],
	        			                [160.0, 55.9], [165.1, 59.1], [182.9, 81.8], [166.4, 70.7], [165.1, 56.8],
	        			                [177.8, 60.0], [165.1, 58.2], [175.3, 72.7], [154.9, 54.1], [158.8, 49.1],
	        			                [172.7, 75.9], [168.9, 55.0], [161.3, 57.3], [167.6, 55.0], [165.1, 65.5],
	        			                [175.3, 65.5], [157.5, 48.6], [163.8, 58.6], [167.6, 63.6], [165.1, 55.2],
	        			                [165.1, 62.7], [168.9, 56.6], [162.6, 53.9], [164.5, 63.2], [176.5, 73.6],
	        			                [168.9, 62.0], [175.3, 63.6], [159.4, 53.2], [160.0, 53.4], [170.2, 55.0],
	        			                [162.6, 70.5], [167.6, 54.5], [162.6, 54.5], [160.7, 55.9], [160.0, 59.0],
	        			                [157.5, 63.6], [162.6, 54.5], [152.4, 47.3], [170.2, 67.7], [165.1, 80.9],
	        			                [172.7, 70.5], [165.1, 60.9], [170.2, 63.6], [170.2, 54.5], [170.2, 59.1],
	        			                [161.3, 70.5], [167.6, 52.7], [167.6, 62.7], [165.1, 86.3], [162.6, 66.4],
	        			                [152.4, 67.3], [168.9, 63.0], [170.2, 73.6], [175.2, 62.3], [175.2, 57.7],
	        			                [160.0, 55.4], [165.1, 104.1], [174.0, 55.5], [170.2, 77.3], [160.0, 80.5],
	        			                [167.6, 64.5], [167.6, 72.3], [167.6, 61.4], [154.9, 58.2], [162.6, 81.8],
	        			                [175.3, 63.6], [171.4, 53.4], [157.5, 54.5], [165.1, 53.6], [160.0, 60.0],
	        			                [174.0, 73.6], [162.6, 61.4], [174.0, 55.5], [162.6, 63.6], [161.3, 60.9],
	        			                [156.2, 60.0], [149.9, 46.8], [169.5, 57.3], [160.0, 64.1], [175.3, 63.6],
	        			                [169.5, 67.3], [160.0, 75.5], [172.7, 68.2], [162.6, 61.4], [157.5, 76.8],
	        			                [176.5, 71.8], [164.4, 55.5], [160.7, 48.6], [174.0, 66.4], [163.8, 67.3]
	        			            ],
	        			            markPoint : {
	        			                data : [
	        			                    {type : 'max', name: '最大值'},
	        			                    {type : 'min', name: '最小值'}
	        			                ]
	        			            },
	        			            markLine : {
	        			                data : [
	        			                    {type : 'average', name: '平均值'}
	        			                ]
	        			            }
	        			        },
	        			        {
	        			            name:'男性',
	        			            type:'scatter',
	        			            data: [[174.0, 65.6], [175.3, 71.8], [193.5, 80.7], [186.5, 72.6], [187.2, 78.8],
	        			                [181.5, 74.8], [184.0, 86.4], [184.5, 78.4], [175.0, 62.0], [184.0, 81.6],
	        			                [180.0, 76.6], [177.8, 83.6], [192.0, 90.0], [176.0, 74.6], [174.0, 71.0],
	        			                [184.0, 79.6], [192.7, 93.8], [171.5, 70.0], [173.0, 72.4], [176.0, 85.9],
	        			                [176.0, 78.8], [180.5, 77.8], [172.7, 66.2], [176.0, 86.4], [173.5, 81.8],
	        			                [178.0, 89.6], [180.3, 82.8], [180.3, 76.4], [164.5, 63.2], [173.0, 60.9],
	        			                [183.5, 74.8], [175.5, 70.0], [188.0, 72.4], [189.2, 84.1], [172.8, 69.1],
	        			                [170.0, 59.5], [182.0, 67.2], [170.0, 61.3], [177.8, 68.6], [184.2, 80.1],
	        			                [186.7, 87.8], [171.4, 84.7], [172.7, 73.4], [175.3, 72.1], [180.3, 82.6],
	        			                [182.9, 88.7], [188.0, 84.1], [177.2, 94.1], [172.1, 74.9], [167.0, 59.1],
	        			                [169.5, 75.6], [174.0, 86.2], [172.7, 75.3], [182.2, 87.1], [164.1, 55.2],
	        			                [163.0, 57.0], [171.5, 61.4], [184.2, 76.8], [174.0, 86.8], [174.0, 72.2],
	        			                [177.0, 71.6], [186.0, 84.8], [167.0, 68.2], [171.8, 66.1], [182.0, 72.0],
	        			                [167.0, 64.6], [177.8, 74.8], [164.5, 70.0], [192.0, 101.6], [175.5, 63.2],
	        			                [171.2, 79.1], [181.6, 78.9], [167.4, 67.7], [181.1, 66.0], [177.0, 68.2],
	        			                [174.5, 63.9], [177.5, 72.0], [170.5, 56.8], [182.4, 74.5], [197.1, 90.9],
	        			                [180.1, 93.0], [175.5, 80.9], [180.6, 72.7], [184.4, 68.0], [175.5, 70.9],
	        			                [180.6, 72.5], [177.0, 72.5], [177.1, 83.4], [181.6, 75.5], [176.5, 73.0],
	        			                [175.0, 70.2], [174.0, 73.4], [165.1, 70.5], [177.0, 68.9], [192.0, 102.3],
	        			                [176.5, 68.4], [169.4, 65.9], [182.1, 75.7], [179.8, 84.5], [175.3, 87.7],
	        			                [184.9, 86.4], [177.3, 73.2], [167.4, 53.9], [178.1, 72.0], [168.9, 55.5],
	        			                [157.2, 58.4], [180.3, 83.2], [170.2, 72.7], [177.8, 64.1], [172.7, 72.3],
	        			                [165.1, 65.0], [186.7, 86.4], [165.1, 65.0], [174.0, 88.6], [175.3, 84.1],
	        			                [185.4, 66.8], [177.8, 75.5], [180.3, 93.2], [180.3, 82.7], [177.8, 58.0],
	        			                [177.8, 79.5], [177.8, 78.6], [177.8, 71.8], [177.8, 116.4], [163.8, 72.2],
	        			                [188.0, 83.6], [198.1, 85.5], [175.3, 90.9], [166.4, 85.9], [190.5, 89.1],
	        			                [166.4, 75.0], [177.8, 77.7], [179.7, 86.4], [172.7, 90.9], [190.5, 73.6],
	        			                [185.4, 76.4], [168.9, 69.1], [167.6, 84.5], [175.3, 64.5], [170.2, 69.1],
	        			                [190.5, 108.6], [177.8, 86.4], [190.5, 80.9], [177.8, 87.7], [184.2, 94.5],
	        			                [176.5, 80.2], [177.8, 72.0], [180.3, 71.4], [171.4, 72.7], [172.7, 84.1],
	        			                [172.7, 76.8], [177.8, 63.6], [177.8, 80.9], [182.9, 80.9], [170.2, 85.5],
	        			                [167.6, 68.6], [175.3, 67.7], [165.1, 66.4], [185.4, 102.3], [181.6, 70.5],
	        			                [172.7, 95.9], [190.5, 84.1], [179.1, 87.3], [175.3, 71.8], [170.2, 65.9],
	        			                [193.0, 95.9], [171.4, 91.4], [177.8, 81.8], [177.8, 96.8], [167.6, 69.1],
	        			                [167.6, 82.7], [180.3, 75.5], [182.9, 79.5], [176.5, 73.6], [186.7, 91.8],
	        			                [188.0, 84.1], [188.0, 85.9], [177.8, 81.8], [174.0, 82.5], [177.8, 80.5],
	        			                [171.4, 70.0], [185.4, 81.8], [185.4, 84.1], [188.0, 90.5], [188.0, 91.4],
	        			                [182.9, 89.1], [176.5, 85.0], [175.3, 69.1], [175.3, 73.6], [188.0, 80.5],
	        			                [188.0, 82.7], [175.3, 86.4], [170.5, 67.7], [179.1, 92.7], [177.8, 93.6],
	        			                [175.3, 70.9], [182.9, 75.0], [170.8, 93.2], [188.0, 93.2], [180.3, 77.7],
	        			                [177.8, 61.4], [185.4, 94.1], [168.9, 75.0], [185.4, 83.6], [180.3, 85.5],
	        			                [174.0, 73.9], [167.6, 66.8], [182.9, 87.3], [160.0, 72.3], [180.3, 88.6],
	        			                [167.6, 75.5], [186.7, 101.4], [175.3, 91.1], [175.3, 67.3], [175.9, 77.7],
	        			                [175.3, 81.8], [179.1, 75.5], [181.6, 84.5], [177.8, 76.6], [182.9, 85.0],
	        			                [177.8, 102.5], [184.2, 77.3], [179.1, 71.8], [176.5, 87.9], [188.0, 94.3],
	        			                [174.0, 70.9], [167.6, 64.5], [170.2, 77.3], [167.6, 72.3], [188.0, 87.3],
	        			                [174.0, 80.0], [176.5, 82.3], [180.3, 73.6], [167.6, 74.1], [188.0, 85.9],
	        			                [180.3, 73.2], [167.6, 76.3], [183.0, 65.9], [183.0, 90.9], [179.1, 89.1],
	        			                [170.2, 62.3], [177.8, 82.7], [179.1, 79.1], [190.5, 98.2], [177.8, 84.1],
	        			                [180.3, 83.2], [180.3, 83.2]
	        			            ],
	        			            markPoint : {
	        			                data : [
	        			                    {type : 'max', name: '最大值'},
	        			                    {type : 'min', name: '最小值'}
	        			                ]
	        			            },
	        			            markLine : {
	        			                data : [
	        			                    {type : 'average', name: '平均值'}
	        			                ]
	        			            }
	        			        }
	        			    ]
	        			};
	        		optionMain9 = {
	        			    tooltip : {
	        			        trigger: 'axis',
	        			        showDelay : 0,
	        			        axisPointer:{
	        			            show: true,
	        			            type : 'cross',
	        			            lineStyle: {
	        			                type : 'dashed',
	        			                width : 1
	        			            }
	        			        }
	        			    },
	        			    legend: {
	        			        data:['sin','cos']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        feature : {
	        			            mark : {show: true},
	        			            dataZoom : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    xAxis : [
	        			        {
	        			            type : 'value',
	        			            scale:true
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'value',
	        			            scale:true
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'sin',
	        			            type:'scatter',
	        			            large: true,
	        			            data: (function () {
	        			                var d = [];
	        			                var len = 10000;
	        			                var x = 0;
	        			                while (len--) {
	        			                    x = (Math.random() * 10).toFixed(3) - 0;
	        			                    d.push([
	        			                        x,
	        			                        //Math.random() * 10
	        			                        (Math.sin(x) - x * (len % 2 ? 0.1 : -0.1) * Math.random()).toFixed(3) - 0
	        			                    ]);
	        			                }
	        			                //console.log(d)
	        			                return d;
	        			            })()
	        			        },
	        			        {
	        			            name:'cos',
	        			            type:'scatter',
	        			            large: true,
	        			            data: (function () {
	        			                var d = [];
	        			                var len = 10000;
	        			                var x = 0;
	        			                while (len--) {
	        			                    x = (Math.random() * 10).toFixed(3) - 0;
	        			                    d.push([
	        			                        x,
	        			                        //Math.random() * 10
	        			                        (Math.cos(x) - x * (len % 2 ? 0.1 : -0.1) * Math.random()).toFixed(3) - 0
	        			                    ]);
	        			                }
	        			                //console.log(d)
	        			                return d;
	        			            })()
	        			        }
	        			    ]
	        			};
	        		optionMain10 = {
	        			    tooltip : {
	        			        trigger: 'axis',
	        			        axisPointer:{
	        			            show: true,
	        			            type : 'cross',
	        			            lineStyle: {
	        			                type : 'dashed',
	        			                width : 1
	        			            }
	        			        }
	        			    },
	        			    legend: {
	        			        data:['scatter1','scatter2']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        feature : {
	        			            mark : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    calculable : true,
	        			    xAxis : [
	        			        {
	        			            type : 'value'
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'value'
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'scatter1',
	        			            type:'scatter',
	        			            symbol: 'emptyCircle', //'circle', 'rectangle', 'triangle', 'diamond', 'emptyCircle', 'emptyRectangle', 'emptyTriangle', 'emptyDiamond'
	        			            symbolSize: function (value){
	        			                if (value[0] < 2) {
	        			                    return 2;
	        			                }
	        			                else if (value[0] < 8) {
	        			                    return Math.round(value[2] * 3);
	        			                }
	        			                else {
	        			                    return 20;
	        			                }
	        			            },
	        			            itemStyle: {
	        			                normal: {
	        			                    color: 'lightblue',
	        			                    borderWidth: 4,
	        			                    label : {show: true}
	        			                },
	        			                emphasis: {
	        			                    color: 'lightgreen',
	        			                }
	        			            },
	        			            data: (function () {
	        			                var d = [];
	        			                var len = 20;
	        			                while (len--) {
	        			                    d.push([
	        			                        (Math.random()*10).toFixed(2) - 0,
	        			                        (Math.random()*10).toFixed(2) - 0,
	        			                        (Math.random()*10).toFixed(2) - 0
	        			                    ]);
	        			                }
	        			                return d;
	        			            })(),
	        			            markPoint : {
	        			                data : [
	        			                    {type : 'max', name: 'y最大值'},
	        			                    {type : 'min', name: 'y最小值'},
	        			                    {type : 'max', name: 'x最大值', valueIndex : 0, symbol:'arrow',itemStyle:{normal:{borderColor:'red'}}},
	        			                    {type : 'min', name: 'x最小值', valueIndex : 0, symbol:'arrow',itemStyle:{normal:{borderColor:'red'}}}
	        			                ]
	        			            },
	        			            markLine : {
	        			                data : [
	        			                    {type : 'average', name: 'y平均值'},
	        			                    {type : 'average', name: 'x平均值', valueIndex : 0, itemStyle:{normal:{borderColor:'red'}}}
	        			                ]
	        			            }
	        			        },
	        			        {
	        			            name:'scatter2',
	        			            type:'scatter',
	        			            symbol: 'image://../asset/ico/favicon.png',     // 系列级个性化拐点图形
	        			            symbolSize: function (value){
	        			                return Math.round(value[2] * 3);
	        			            },
	        			            itemStyle: {
	        			                emphasis : {
	        			                    label : {show: true}
	        			                }
	        			            },
	        			            data: (function () {
	        			                var d = [];
	        			                var len = 20;
	        			                while (len--) {
	        			                    d.push([
	        			                        (Math.random()*10).toFixed(2) - 0,
	        			                        (Math.random()*10).toFixed(2) - 0,
	        			                        (Math.random()*10).toFixed(2) - 0
	        			                    ]);
	        			                }
	        			                d.push({
	        			                    value : [5,5,1000],
	        			                    itemStyle: {
	        			                        normal: {
	        			                            borderWidth: 8,
	        			                            color: 'orange'
	        			                        },
	        			                        emphasis: {
	        			                            borderWidth: 10,
	        			                            color: '#ff4500'
	        			                        }
	        			                    },
	        			                    symbol: 'emptyTriangle',
	        			                    symbolRotate:90,
	        			                    symbolSize:30
	        			                })
	        			                return d;
	        			            })(),
	        			            markPoint : {
	        			                symbol: 'emptyCircle',
	        			                itemStyle:{
	        			                    normal:{label:{position:'top'}}
	        			                },
	        			                data : [
	        			                    {name : '有个东西', value : 1000, xAxis: 5, yAxis: 5, symbolSize:80}
	        			                ]
	        			            }
	        			        }
	        			    ]
	        			};
	        		optionMain11 = {
	        			    tooltip : {
	        			        trigger: 'axis',
	        			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	        			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        			        }
	        			    },
	        			    legend: {
	        			        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        orient: 'vertical',
	        			        x: 'right',
	        			        y: 'center',
	        			        feature : {
	        			            mark : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    calculable : true,
	        			    xAxis : [
	        			        {
	        			            type : 'category',
	        			            data : ['周一','周二','周三','周四','周五','周六','周日']
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'value'
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'直接访问',
	        			            type:'bar',
	        			            data:[320, 332, 301, 334, 390, 330, 320]
	        			        },
	        			        {
	        			            name:'邮件营销',
	        			            type:'bar',
	        			            stack: '广告',
	        			            data:[120, 132, 101, 134, 90, 230, 210]
	        			        },
	        			        {
	        			            name:'联盟广告',
	        			            type:'bar',
	        			            stack: '广告',
	        			            data:[220, 182, 191, 234, 290, 330, 310]
	        			        },
	        			        {
	        			            name:'视频广告',
	        			            type:'bar',
	        			            stack: '广告',
	        			            data:[150, 232, 201, 154, 190, 330, 410]
	        			        },
	        			        {
	        			            name:'搜索引擎',
	        			            type:'bar',
	        			            data:[862, 1018, 964, 1026, 1679, 1600, 1570],
	        			            markLine : {
	        			                itemStyle:{
	        			                    normal:{
	        			                        lineStyle:{
	        			                            type: 'dashed'
	        			                        }
	        			                    }
	        			                },
	        			                data : [
	        			                    [{type : 'min'}, {type : 'max'}]
	        			                ]
	        			            }
	        			        },
	        			        {
	        			            name:'百度',
	        			            type:'bar',
	        			            barWidth : 5,
	        			            stack: '搜索引擎',
	        			            data:[620, 732, 701, 734, 1090, 1130, 1120]
	        			        },
	        			        {
	        			            name:'谷歌',
	        			            type:'bar',
	        			            stack: '搜索引擎',
	        			            data:[120, 132, 101, 134, 290, 230, 220]
	        			        },
	        			        {
	        			            name:'必应',
	        			            type:'bar',
	        			            stack: '搜索引擎',
	        			            data:[60, 72, 71, 74, 190, 130, 110]
	        			        },
	        			        {
	        			            name:'其他',
	        			            type:'bar',
	        			            stack: '搜索引擎',
	        			            data:[62, 82, 91, 84, 109, 110, 120]
	        			        }
	        			    ]
	        			};
	        		optionMain12 = {
	        			    title : {
	        			        text: '双数值柱形图',
	        			        subtext: '纯属虚构'
	        			    },
	        			    tooltip : {
	        			        trigger: 'axis',
	        			        axisPointer:{
	        			            show: true,
	        			            type : 'cross',
	        			            lineStyle: {
	        			                type : 'dashed',
	        			                width : 1
	        			            }
	        			        },
	        			        formatter : function (params) {
	        			            return params.seriesName + ' : [ '
	        			                   + params.value[0] + ', ' 
	        			                   + params.value[1] + ' ]';
	        			        }
	        			    },
	        			    legend: {
	        			        data:['数据1','数据2']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        feature : {
	        			            mark : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            magicType : {show: true, type: ['line', 'bar']},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    calculable : true,
	        			    xAxis : [
	        			        {
	        			            type : 'value'
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'value',
	        			            axisLine: {
	        			                lineStyle: {
	        			                    color: '#dc143c'
	        			                }
	        			            }
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'数据1',
	        			            type:'bar',
	        			            data:[
	        			                [1.5, 10], [5, 7], [8, 8], [12, 6], [11, 12], [16, 9], [14, 6], [17, 4], [19, 9]
	        			            ],
	        			            markPoint : {
	        			                data : [
	        			                    // 纵轴，默认
	        			                    {type : 'max', name: '最大值',symbol: 'emptyCircle', itemStyle:{normal:{color:'#dc143c',label:{position:'top'}}}},
	        			                    {type : 'min', name: '最小值',symbol: 'emptyCircle', itemStyle:{normal:{color:'#dc143c',label:{position:'bottom'}}}},
	        			                    // 横轴
	        			                    {type : 'max', name: '最大值', valueIndex: 0, symbol: 'emptyCircle', itemStyle:{normal:{color:'#1e90ff',label:{position:'right'}}}},
	        			                    {type : 'min', name: '最小值', valueIndex: 0, symbol: 'emptyCircle', itemStyle:{normal:{color:'#1e90ff',label:{position:'left'}}}}
	        			                ]
	        			            },
	        			            markLine : {
	        			                data : [
	        			                    // 纵轴，默认
	        			                    {type : 'max', name: '最大值', itemStyle:{normal:{color:'#dc143c'}}},
	        			                    {type : 'min', name: '最小值', itemStyle:{normal:{color:'#dc143c'}}},
	        			                    {type : 'average', name : '平均值', itemStyle:{normal:{color:'#dc143c'}}},
	        			                    // 横轴
	        			                    {type : 'max', name: '最大值', valueIndex: 0, itemStyle:{normal:{color:'#1e90ff'}}},
	        			                    {type : 'min', name: '最小值', valueIndex: 0, itemStyle:{normal:{color:'#1e90ff'}}},
	        			                    {type : 'average', name : '平均值', valueIndex: 0, itemStyle:{normal:{color:'#1e90ff'}}}
	        			                ]
	        			            }
	        			        },
	        			        {
	        			            name:'数据2',
	        			            type:'bar',
	        			            barHeight:10,
	        			            data:[
	        			                [1, 2], [2, 3], [4, 4], [7, 5], [11, 11], [18, 15]
	        			            ]
	        			        }
	        			    ]
	        			};
	        		optionMain13 = {
	        			    tooltip : {
	        			        show: true,
	        			        trigger: 'item'
	        			    },
	        			    legend: {
	        			        data:['邮件营销','联盟广告','直接访问','搜索引擎']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        feature : {
	        			            mark : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    calculable : true,
	        			    xAxis : [
	        			        {
	        			            type : 'category',
	        			            data : ['周一','周二','周三','周四','周五','周六','周日']
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'value'
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'邮件营销',
	        			            type:'bar',
	        			            itemStyle: {        // 系列级个性化样式，纵向渐变填充
	        			                normal: {
	        			                    barBorderColor:'red',
	        			                    barBorderWidth: 5,
	        			                    color : (function (){
	        			                        var zrColor = require('zrender/tool/color');
	        			                        return zrColor.getLinearGradient(
	        			                            0, 400, 0, 300,
	        			                            [[0, 'green'],[1, 'yellow']]
	        			                        )
	        			                    })()
	        			                },
	        			                emphasis: {
	        			                    barBorderWidth: 5,
	        			                    barBorderColor:'green',
	        			                    color: (function (){
	        			                        var zrColor = require('zrender/tool/color');
	        			                        return zrColor.getLinearGradient(
	        			                            0, 400, 0, 300,
	        			                            [[0, 'red'],[1, 'orange']]
	        			                        )
	        			                    })(),
	        			                    label : {
	        			                        show : true,
	        			                        position : 'top',
	        			                        formatter : "{a} {b} {c}",
	        			                        textStyle : {
	        			                            color: 'blue'
	        			                        }
	        			                    }
	        			                }
	        			            },
	        			            data:[220, 232, 101, 234, 190, 330, 210]
	        			        },
	        			        {
	        			            name:'联盟广告',
	        			            type:'bar',
	        			            stack: '总量',
	        			            data:[120, '-', 451, 134, 190, 230, 110]
	        			        },
	        			        {
	        			            name:'直接访问',
	        			            type:'bar',
	        			            stack: '总量',
	        			            itemStyle: {                // 系列级个性化
	        			                normal: {
	        			                    barBorderWidth: 6,
	        			                    barBorderColor:'tomato',
	        			                    color: 'red'
	        			                },
	        			                emphasis: {
	        			                    barBorderColor:'red',
	        			                    color: 'blue'
	        			                }
	        			            },
	        			            data:[
	        			                320, 332, 100, 334,
	        			                {
	        			                    value: 390,
	        			                    symbolSize : 10,   // 数据级个性化
	        			                    itemStyle: {
	        			                        normal: {
	        			                            color :'lime'
	        			                        },
	        			                        emphasis: {
	        			                            color: 'skyBlue'
	        			                        }
	        			                    }
	        			                },
	        			                330, 320
	        			            ]
	        			        },
	        			        {
	        			            name:'搜索引擎',
	        			            type:'bar',
	        			            barWidth: 40,                   // 系列级个性化，柱形宽度
	        			            itemStyle: {
	        			                normal: {                   // 系列级个性化，横向渐变填充
	        			                    borderRadius: 5,
	        			                    color : (function (){
	        			                        var zrColor = require('zrender/tool/color');
	        			                        return zrColor.getLinearGradient(
	        			                            0, 0, 1000, 0,
	        			                            [[0, 'rgba(30,144,255,0.8)'],[1, 'rgba(138,43,226,0.8)']]
	        			                        )
	        			                    })(),
	        			                    label : {
	        			                        show : true,
	        			                        textStyle : {
	        			                            fontSize : '20',
	        			                            fontFamily : '微软雅黑',
	        			                            fontWeight : 'bold'
	        			                        }
	        			                    }
	        			                }
	        			            },
	        			            data:[
	        			                620, 732, 
	        			                {
	        			                    value: 701,
	        			                    itemStyle : { normal: {label : {position: 'inside'}}}
	        			                },
	        			                734, 890, 930, 820
	        			            ],
	        			            markLine : {
	        			                data : [
	        			                    {type : 'average', name : '平均值'},
	        			                    {type : 'max'},
	        			                    {type : 'min'}
	        			                ]
	        			            }
	        			        }
	        			    ]
	        			};
	        			                    		
	        		optionMain14 = {
	        			    tooltip : {
	        			        trigger: 'axis',
	        			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	        			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        			        }
	        			    },
	        			    legend: {
	        			        data:['直接访问', '邮件营销','联盟广告','视频广告','搜索引擎']
	        			    },
	        			    toolbox: {
	        			        show : true,
	        			        feature : {
	        			            mark : {show: true},
	        			            dataView : {show: true, readOnly: false},
	        			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	        			            restore : {show: true},
	        			            saveAsImage : {show: true}
	        			        }
	        			    },
	        			    calculable : true,
	        			    xAxis : [
	        			        {
	        			            type : 'value'
	        			        }
	        			    ],
	        			    yAxis : [
	        			        {
	        			            type : 'category',
	        			            data : ['周一','周二','周三','周四','周五','周六','周日']
	        			        }
	        			    ],
	        			    series : [
	        			        {
	        			            name:'直接访问',
	        			            type:'bar',
	        			            stack: '总量',
	        			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
	        			            data:[320, 302, 301, 334, 390, 330, 320]
	        			        },
	        			        {
	        			            name:'邮件营销',
	        			            type:'bar',
	        			            stack: '总量',
	        			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
	        			            data:[120, 132, 101, 134, 90, 230, 210]
	        			        },
	        			        {
	        			            name:'联盟广告',
	        			            type:'bar',
	        			            stack: '总量',
	        			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
	        			            data:[220, 182, 191, 234, 290, 330, 310]
	        			        },
	        			        {
	        			            name:'视频广告',
	        			            type:'bar',
	        			            stack: '总量',
	        			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
	        			            data:[150, 212, 201, 154, 190, 330, 410]
	        			        },
	        			        {
	        			            name:'搜索引擎',
	        			            type:'bar',
	        			            stack: '总量',
	        			            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
	        			            data:[820, 832, 901, 934, 1290, 1330, 1320]
	        			        }
	        			    ]
	        			};
	        			                     			                    			                     			                    			                    			                    			                    		
	        // 为echarts对象加载数据 
	        myChart.setOption(optionMain); 
	        myChart1.setOption(optionMain1);
	        myChart2.setOption(optionMain2);
	        myChart3.setOption(optionMain3);
	        myChart4.setOption(optionMain4);
	        myChart5.setOption(optionMain5);
	        myChart6.setOption(optionMain6);
	        myChart7.setOption(optionMain7);
	        myChart8.setOption(optionMain8);
	        myChart9.setOption(optionMain9);
	        myChart10.setOption(optionMain10);
	        myChart11.setOption(optionMain11);
	        myChart12.setOption(optionMain12);
	        myChart13.setOption(optionMain13);
	        myChart14.setOption(optionMain14);
	        
	    }
	);
}
</script>
</body>
</html>