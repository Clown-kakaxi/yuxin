<%@ page contentType="text/html; charset=utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link  type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/contents/resource/ext3/resources/css/ext-all.css" />
<link href="<%=request.getContextPath()%>/contents/css/comm.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/contents/css/frame.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/adapter/ext/ext-base.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ext-all-debug.js"/></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/ux/ux-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/resource/ext3/locale/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/index/js/mainPageInfos.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/index/js/mainPageMailNotice.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/index/js/mainPageWorkNotice.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/contents/pages/index/js/mainPagePortal.js"></script>

</head>
<body>
<div id="mainPage_frameDiv" class="ic_box_cr">
<div class="ic_box_center" id="ic_box_center_div">
<script type="text/javascript">

	var contextPath = "<%=request.getContextPath()%>";
	/**
	 * 本js是首页EXT部分的入口
	 */
	Ext.onReady(function(){
		Ext.QuickTips.init();

		//平台可定制部分的div容器
		var mainFrameDiv = document.getElementById("mainPage_frameDiv");
		
		//初始化工作提醒模块
		var workNoticePanel ;
		//初始化邮件提醒模块
		var mailNoticePanel ;
		//初始化资讯模块
		var infoPanel ;

		//定制布局panel
		var panel = new Ext.Panel({
            id:'main-panel',
            baseCls:'x-plain',
            renderTo: 'ic_box_center_div',
            layout:'table',
            layoutConfig: {columns:2},
            // applied to child components
            defaults: {xtype:'panel'},
            items:[]
        });

		//构造组件panel对象
		var createModulePanel = function(moduleCode,styleNo){
			switch (moduleCode){
				case 'workNotice' :
					return new WorkNotice(styleNo);
				case 'mailNotice' :
					return new MailNotice(styleNo);
				case 'infomation' :
					return new Infos(styleNo);
				default: return null;
			}
		};
        
		//function : 将首页可定制部分设置为默认布局
		//默认布局:以ic_s1样式为背景，第一列由'工作提醒'，'邮件提醒'组成。第二列为'资讯'单列占两行
		var defaultLayout = function(){
			panel.removeAll();
			//设置默认布局样式
			mainFrameDiv.className = 'ic_box_cr';
			//构建panel的items
			//1.工作提醒
			workNoticePanel = new WorkNotice();
			var item_1_1 = {
					title:' '
				   ,iconCls: 'tit_gztx'
                   ,cls:'ic_sbox ic_s1_1'
                   ,items:[workNoticePanel]
            };
            //2.资讯
            infoPanel = new Infos();
            var item_1_2 = {
                    title:' '
				   ,rowspan:2
				   ,iconCls: 'tit_zx'
				   ,cls:'ic_sbox ic_s1_2'
				   ,items:[infoPanel]
            };
            //3.邮件提醒
            mailNoticePanel = new MailNotice();
            var item_2_1 = {
                    title:' '
                   ,iconCls: 'tit_yjtx'
                   ,cls:'ic_sbox ic_s1_3'
                   ,items:[mailNoticePanel]
            }; 
            panel.add(item_1_1,item_1_2,item_2_1);

            panel.doLayout();
		};

		//function : 将首页可定制部分设置为ic_s1布局
		var s1Layout = function(modules){
			panel.removeAll();
			//设置背景样式
			mainFrameDiv.className = 'ic_box_cr';

		    var item_1_1 = new Ext.Panel({
			    title:' '
			   ,cls:'ic_sbox ic_s1_1'
		    });
			var item_1_2 = new Ext.Panel({
                title:' '
			   ,rowspan:2
			   ,cls:'ic_sbox ic_s1_2'
		    });
			var item_2_1 = new Ext.Panel({
			    title:' '
			   ,cls:'ic_sbox ic_s1_3'
			   ,items:[]
			});

			var modulePanel = null;

			Ext.each(modules,function(m){
				if(m.COLUMN_SEQ == 0 && m.MODULE_SEQ == 0){
					modulePanel = createModulePanel(m.MODULE_ID,1);
					item_1_1.add(modulePanel);
					item_1_1.iconCls = modulePanel.titleCls;
				}else if(m.COLUMN_SEQ == 1 && m.MODULE_SEQ == 0){
					modulePanel = createModulePanel(m.MODULE_ID,2);
					item_1_2.add(modulePanel);
					item_1_2.iconCls = modulePanel.titleCls;
				}else if(m.COLUMN_SEQ == 0 && m.MODULE_SEQ == 1){
					modulePanel = createModulePanel(m.MODULE_ID,3);
					item_2_1.add(modulePanel);
					item_2_1.iconCls = modulePanel.titleCls;
				}			
			});
			panel.add(item_1_1,item_1_2,item_2_1);

            panel.doLayout();
		};

		//function : 将首页可定制部分设置为ic_s2布局
		var s2Layout = function(modules){
			panel.removeAll();
			//设置背景样式
			mainFrameDiv.className = 'ic_box_cr2';
			
			var item_n_n = null;
			//用于定位css
			var css_flag = 1;
		
			for(var row=0;row<2;row++){
				for(var col=0;col<2;col++){
					item_n_n = new Ext.Panel({
						title:' '
					   ,cls:'ic_sbox ic_s2_'+css_flag
					});
					Ext.each(modules,function(m){
						if(m.COLUMN_SEQ == col && m.MODULE_SEQ == row){
							var styleFlag = 3;
							if(row == 0){
								styleFlag = 1;
							}else {
								styleFlag = 3;
							}
							modulePanel = createModulePanel(m.MODULE_ID,styleFlag);
							item_n_n.add(modulePanel);
							item_n_n.iconCls = modulePanel.titleCls;
						}
					});
					panel.add(item_n_n);
					css_flag++;
				}
			}
			panel.doLayout();
		};

		//function : 将首页可定制部分设置为ic_s3布局
		var s3Layout = function(modules){
			panel.removeAll();
			//设置背景样式
			mainFrameDiv.className = 'ic_box_cr3';

			var item_1_1 = new Ext.Panel({
				title:' '
			   ,rowspan:2
		       ,cls:'ic_sbox ic_s3_1'
		    });
			var item_1_2 = new Ext.Panel({
                title:' '
			   ,cls:'ic_sbox ic_s3_2'
		    });
			var item_2_2 = new Ext.Panel({
			    title:' '
			   ,cls:'ic_sbox ic_s3_3'
			});

			var modulePanel = null;

			Ext.each(modules,function(m){
				if(m.COLUMN_SEQ == 0 && m.MODULE_SEQ == 0){
					modulePanel = createModulePanel(m.MODULE_ID,2);
					item_1_1.add(modulePanel);
					item_1_1.iconCls = modulePanel.titleCls;
				}else if(m.COLUMN_SEQ == 1 && m.MODULE_SEQ == 0){
					modulePanel = createModulePanel(m.MODULE_ID,1);
					item_1_2.add(modulePanel);
					item_1_2.iconCls = modulePanel.titleCls;
				}else if(m.COLUMN_SEQ == 1 && m.MODULE_SEQ == 1){
					modulePanel = createModulePanel(m.MODULE_ID,3);
					item_2_2.add(modulePanel);
					item_2_2.iconCls = modulePanel.titleCls;
				}			
			});
			panel.add(item_1_1,item_1_2,item_2_2);

            panel.doLayout();
		};
		/***************************查询客户数据**************************************************/
		var userSetting;
	    Ext.Ajax.request({
	        url : contextPath+'/indexset.json',
	        method : "GET",
	        success : function(response){
	            userSetting = Ext.util.JSON.decode(response.responseText);
	            if(!userSetting.returns.layoutId){
	                Ext.Msg.alert('提示','您尚未做过首页设置！'); 
	                defaultLayout();
	                return;
	            }
	            //布局类型
	            //--目前支持的布局包括:
	            //-- 1.ic_s1 -> 共3个模块，第一列分两个模块，第二列由一个模块独占
	            //-- 2.ic_s2 -> 共4个模块，两行两列，一行一列对应一个模块
	            //-- 3.ic_s3 -> 共3个模块，第一列由一个模块独占，第二列分两个模块
	            var userLayout = userSetting.returns.layoutId;
	            //布局中的列表数组
	            var userModule = userSetting.returns.data;
				//根据布局，改变样式，若是其他未定义布局，采用默认布局
	            if("ic_s1" == userLayout){
	            	s1Layout(userModule);
		        }else if("ic_s2" == userLayout){
		        	s2Layout(userModule);
			    }else if("ic_s3" == userLayout){
			    	s3Layout(userModule);
				}else {
					defaultLayout();
				}

	        },
	        failure : function(action,form){
	        	defaultLayout();
	            Ext.Msg.alert('提示','您尚未做过首页设置！'); 
	        }
	    });

	});

	//屏蔽鼠标右键
	//document.oncontextmenu=function(){return false;};
	
</script>
</div>
</div>
</body>
</html>