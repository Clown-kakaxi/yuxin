/**
 * @description 日程查询
 * @author sniper
 * @since 2014-4-28
 */



imports(['/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',//在客户放大镜中用到机构放大镜
         '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',//客户放大镜
         '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'//用户放大镜
        ]);

var url = basepath + '/scheduleQuery-action.json'; //数据查询URL地址;

var createView = false;   //是否需要新建面板
var editView = false;     //是否需要修改面板
var detailView = false;   //是否需要详情面板

/**
 * @description 本地数据字典(日程类型)
 * @author sniper
 * @since 2014-4-28
 */
var localLookup = {
		'REMIND_BELONG' : [
		   {key : '1000000001',value : '客户拜访'},
		   {key : '1000000002',value : '代办事项'},
		   {key : '1000000003',value : '纪念日'},
		   {key : '1000000004',value : '会议'},
		   {key : '1000000005',value : '约会'},
		   {key : '1000000006',value : '备忘'}
		]
	};

/**
 * @description 日志信息元数据
 * @author sniper
 * @since 2014-4-28
 */
var fields = [{name:'SCHEDULE_ID',text:'日程ID',hidden:true},
              {name:'STATE',text:'状态'},
              {name:'SCHEDULE_TITLE',text:'日程标题'},
              {name:'REMIND_BELONG',text:'日程类型',translateType:'REMIND_BELONG'},
              {name:'STAR_DT',text:'开始时间',xtype:'datefield',format:'Y-m-d',searchField:true,anchor:'80%'},
              {name:'END_DT',text:'结束时间',xtype:'datefield',format:'Y-m-d',searchField:true,anchor:'80%'},
              {name:'CREATOR',text:'用户ID',hidden:true,searchField:true},
              {name:'CREATOR_NAME',text:'用户名称',id:'userchooseId',xtype:'userchoose',searchField:true,hiddenName:'CREATOR',anchor:'80%'},
              {name:'RELATION_CUST',text:'客户ID',hidden:true},
              {name:'RELATION_CUST_NAME',text:'客户名称',xtype:'customerquery',searchField:true,hiddenName:'RELATION_CUST',anchor:'80%'},
              {name:'IS_TEAM',text:'是否团队日程'},
              {name:'MKT_TEAM_ID',text:'团队ID'}
              ];
