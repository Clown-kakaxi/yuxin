<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template40.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	$(function() {
		url = "${ctx}/ecif/transstatistic/bytimeconsumelist.json";
		searchForm();
		initGrid();
		addSearchButtons("#search", grid, "#searchbtn");
	});
	function greaterThan(){
		var value1 = $('#startDate').val();
		var value2 = $('#endDate').val();
		if(value1 == "" && value2 == ""){
			BIONE.tip('日期不能为空！');
			return false;
		}
		var tdate;
		var fdate;
		tdate = new Date(new Number(value1.substr(0, 4)), new Number(value1.substr(5, 2)) - 1, 
				new Number(value1.substr(8, 2))).valueOf() / (60 * 60 * 24 * 1000);
		fdate = new Date(new Number(value2.substr(0, 4)), new Number(value2.substr(5, 2)) - 1,
				new Number(value2.substr(8, 2))).valueOf() / (60 * 60 * 24 * 1000);
		
		if(value2!='' && tdate -  fdate > 0){
			BIONE.tip('结束日期不能小于开始日期！');
			return false;
		} 
		return true;
	}
	
	// 创建表单搜索按钮：搜索、高级搜索
	addSearchButtons = function(form, grid, btnContainer) {
		if (!form)
			return;
		form = $(form);
		if (btnContainer) {
			BIONE.createButton({
				appendTo : btnContainer,
				text : '搜索',
				icon : 'search3',
				width : '50px',
				click : function() {
					var loading=$(".l-grid-loading");
					if(loading&&loading.css("display")=="block"){
						BIONE.tip('数据正在加载中,请稍后进行查询操作!');
					}else{
					
					if(greaterThan()){
						
						//设置图片区域的显示内容为空
						$("#chartContainer").html('');
						
						var rule = BIONE.bulidFilterGroup(form);
						if (rule.rules.length) {
							grid.set('parms', {
								condition : JSON2.stringify(rule)
							});
							grid.set('newPage', 1);
						} else {
							grid.set('parms', {
								condition : ''
							});
							grid.set('newPage', 1);
						}
						/* @Revision 20130704182000 liucheng2@yuchengtech.com
						 * 防止grid进行ajax取数时session失效而无法正确获取数据； */
						if (!grid.loading || grid.loading!=true) {
							var opurl = grid.options.url;
							var ctx = "/";
							if (opurl) {
								ctx = "/" + opurl.split("/")[1] + "/";
							}
							$.ajax({
								cache : false,
								async : true,
								url : ctx + "/bione/common/getComboxData.json",
								dataType : 'json',
								data : {
									"paramTypeNo" : ""
								},
								type : grid.options.type,
								complete : function(XMLHttpRequest) {
									BIONE.isSessionAlive(XMLHttpRequest);
								},
								success : function() {
									grid.loadData();
								}
							});
						} else {
							BIONE.showError("查询进行中，请勿重复查询！");
						}
						/* @Revision 20130704182000 END */

					}
					}
				}
			});
			BIONE.createButton({
				appendTo : btnContainer,
				text : '重置',
				icon : 'refresh2',
				width : '50px',
				click : function() {
					$(":input", form)
							.not(":submit, :reset,:hidden,:image,:button, [disabled]")
							.each(function() {
								$(this).val("");
							});
					$(":input[ltype=combobox]", form)
					.each(function() {
						$(this).val("");
					});
				}
			});
		}
	};
		
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "日期区间(开始)",
				name : "startDate",
				id:'startDate',
				newline : true,
				type : "date",
				options:{
					onChangeDate:function(){
						$("#endDate").focus();
						$("#startDate").focus();
					}
				},
				attr : {
					op : ">=",
					field : "txDt"
				}

			}, {
				display : "日期区间(结束)",
				name : "endDate",
				id:'endDate',
				newline : false,
				type : "date",
				validate : {
					greaterThan : "startDate"
				},
				attr : {
					op : "<=",
					field : "txDt"
				}

			}]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '交易日期',
								name : 'txDt',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '交易耗时时间',
								name : 'label',
								align : 'center',
								width : '15%',
								minWidth : '10%',
								isSort :true
							},

							{
								display : '交易笔数',
								name : 'value',
								align : 'center',
								width : '15%',
								minWidth : '10%',
								isSort :true
							}
 					],
					checkbox : false,
					usePager : false,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					delayLoad :false,
					sortName : 'txDt', //第一次默认排序的字段
					sortOrder : 'desc',
					onAfterShowData: function (grid){
						//表格加载完毕后，取出表格的Rows数据
						var jsonObj = {};
						jsonObj = grid.Rows;
						//alert(jsonObj);
						
                        var DataJSON = {
                            "chart":{
                                "caption":"按交易耗时码统计交易量",
                                "xaxisname":"交易耗时时间",
                                "yaxisname":"交易笔数",
                                "labelDisplay":"rotate"  
                            },
                            "data":[jsonObj]
                        };

                        var myChart = new FusionCharts( "${ctx}/Fusioncharts/Column3D.swf", "myChartId", "800", "400");
                        myChart.setChartData(DataJSON, 'json');
                        myChart.render( "chartContainer" );
						
					} ,
					
					toolbar : {}
				});
	}

  

</script>
</head>
<body>
</body>
</html>