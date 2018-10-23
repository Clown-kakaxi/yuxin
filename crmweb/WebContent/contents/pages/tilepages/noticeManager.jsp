<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/contents/pages/common/includes.jsp"%>
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript"src="<%=request.getContextPath()%>/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/CustomerQueryMagnifier.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/frameControllers/WljAPPBooter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/frameControllers/widgets/search/newSearch.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/tilepages/noticeManager.js"></script>

<style type="text/css">
layout_search_background.png
	.search_bt_t{
		width:25px;
		height:25px;
		float:right;
		margin-top:1px;
		cursor:pointer;
    }
    .search_inner_t{	
	width:150px;
	height:30px;
	position:absolute;
	z-index:1;
	top:55px;
	left:35px;
	}
	.search_inner_t.search_input{
		width:402px;
		height:45px;
		float:left;	
		padding:0px 7px 0px 10px;
	}
	.search_inner_t.search_input input{
		width:402px;
		height:44px;
		line-height:44px;
		border:0 none;	
	}
	.search_input_test
	{
	    background-color : #39BBEA;
	}
	.menuButton
	{
	    background-color : #F3BF00;
	}
	.resultgroup{
		border : 2px solid #000;
	}
	
	.search_cust{
		background-color : #FFDDAA;
	}
	
	.search_info{
		background-color : #00FFFF;
	}
	
	.search_level{
		background-color : #FF0000;
	}
	
	.search_in{
		background-color : #F5DEB3;
	}
	
	.search_or{
		background-color : #FFFF99;
	}
	.searchTile{
		background-color :#C5E089;
	}
	.test {
     background-image: url(test.png);
   }
	
</style>
<script type="text/javascript">
	<%
		out.print("var conditionP='"+request.getParameter("condition")+"';");
	%>
</script>
</head>
<body>
<script type="text/javascript" src="../shared/examples.js"></script><!-- EXAMPLES -->
<script>
		function daohang(ccc){
			debugger;
			var bodysize = Ext.getBody().getViewSize();
			var tileWidth = parseInt((bodysize.width - 16)/136);
			var searchComponent = new Wlj.widgets.search.search.SearchComponent({
				style : {
					marginTop :0,
					marginLeft :50,
					top:0,
					left :50
				},
				onRender : function(ct, position){
					Wlj.widgets.search.search.SearchComponent.superclass.onRender.call(this, ct, position);
					this.searchType = new Wlj.widgets.search.search.SearchType({
						appObject : this.appObject
					});
					this.searchField = new Wlj.widgets.search.search.SearchField({
						comfex : this.comfex,
						appObject : this.appObject
					});
//					this.add(this.searchType );
					this.add(this.searchField );
					this.doLayout();
				},
				height: 128,
				width : 494,
				doSearch:function(){
				
					loadData();
				},
				comfex : true,
				comfexFn : function(){
					if(fexed){
						fexed = false;
						panelgaoji.body.applyStyles({
							height : 0,
							width : 0
						});
						panelgaoji.el.animate(
								{	
									height : {
										to : 0,
										from : 200
									}
								},
								1,
								null,
								'easeOut',
								'run'
							);
						panelgaoji.removeAll(true);
					}else {
						fexed = true;
						panelgaoji.el.animate(
								{	
									height : {
										to : 200,
										from : 0
									}
								},
								1,
								null,
								'easeOut',
								'run'
						);
						
					}
				},
				afterRender:function(){
					
				},
				listeners : {
//					afterrender : function(){
//						this.appObject = parent._APP;
//					}
				}
			});
			var panel1 = new Ext.Panel({
				height : 128,
				frame : true,
				items : [searchComponent]
			});
			var checkData =[['公告名称','noticeTile'],
			             ['公告内容','noticeContent']];
			for(var i=0;i<checkData.length;i++){
				var noticeTitle = checkData[i][0];
				var noticeContent = checkData[i][1];
				var cls='search_or';
				var inforClor;
				var left1 = new Wlj.widgets.search.tile.Tile({
					cls : cls,
					ownerH : tileWidth*2,
					ownerW :  tileWidth*2,
					removeable :true,
					baseSize : 68,
					baseMargin : 1,
					pos_size : {
						TX : (i%4)*3,
						TY :Math.floor(i/4)*3,
						TW : 2,
						TH : 1
					},
					html : '<div id="'+i+'_div" font size="2" color="#000000" style="width:100%;height:100%;margin-top:5px;text-align:center;">1728172</div>',
					listeners : {
						afterrender : function(ccc){
							
						}
					},
					removeThis:function(){
					}
				});
				panel1.add(left1);
			}
			var selectWindow = new Ext.Window({
				height:400,
				width:700,
				layout: 'fit',
				items: [panel1]
			});	
			selectWindow.show();
			}
		
</script>
<!--<div id="bubble-markup" >&nbsp;&nbsp;&nbsp;&nbsp;
<img style="padding-right:5px;" onmouseenter ="javascript:daohang(this)" src = "icon1.png">
</div>

<div id="veiwPort" style="padding-top: 5px;"></div>-->

</body>
</html>