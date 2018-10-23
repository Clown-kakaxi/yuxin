(function(){
	
    var proxyIndex = new Ext.data.HttpProxy({
        url : basepath+'/noticequery.json'
    });
    var writer = new Ext.data.JsonWriter({
        encode: false   
    });
    var TopicRecord = Ext.data.Record.create([
        {name: 'noticeId', mapping: 'NOTICE_ID'},
        {name: 'noticeTitle', mapping: 'NOTICE_TITLE'},                                   
        {name: 'topActiveDate', mapping: 'TOP_ACTIVE_DATE'},  
        {name: 'noticeContent', mapping: 'NOTICE_CONTENT'},
        {name: 'noticeLevel', mapping: 'NOTICE_LEVEL_ORA'},
        {name: 'publisher', mapping: 'PUBLISHER'},
        {name: 'publishOrganizer', mapping: 'PUBLISH_ORG'},
        {name: 'isTop', mapping: 'IS_TOP'},
        {name: 'publishTime', mapping: 'PUBLISH_TIME'},
        {name: 'moduleType', mapping: 'MODULE_TYPE'},
        {name: 'published', mapping: 'PUBLISHED'},
        {name: 'activeDate', mapping: 'ACTIVE_DATE'},
        {name: 'noticeType', mapping: 'NOTICE_TYPE'},
        {name: 'isRead', mapping: 'IS_READ_ORA'},
        {name: 'creator', mapping: 'CREATOR'},
        {name: 'publisherName', mapping: 'PUBLISHER_NAME'},
        {name: 'pubOrgName', mapping: 'PUB_ORG_NAME'},
        {name: 'creatorName', mapping: 'CREATOR_NAME'},
        {name: 'annCount', mapping:'ANN_COUNT'}
    ]);
    var reader = new Ext.data.JsonReader({
        successProperty: 'success',
        idProperty: 'NOTICE_ID',
        messageProperty: 'message',
        totalProperty: 'json.count',
        root : 'json.data'
    },TopicRecord);
    var restfulStore = new Ext.data.Store({
        id: 'notice',
        restful : true,     
        proxy : proxyIndex,
        reader : reader,
        writer : writer,
        recordType:TopicRecord
    });
	var tileBox = new Ext.DataView({
		title : '我的日程',
		index : 1,
		tpl : new Ext.XTemplate(
			'<div class="w2h2" >',
				
			'<div class="tile w2h2">',
        	'<div class="calendar_bg">',
            	'<div class="calendar_ym">',
                	'<div class="calendar_ym_left">','</div>',
                    '<p class="calendar_ym_p">','<span class="year">','<i>','2013年','</i>','</span>','<span class="month">','<i>','8月','</i>','</span>','</p>',							
                	'<div class="calendar_ym_right">','</div>',
                '</div>',
            	'<div class="calendar_tit">',
                	'<p class="calendar_tit_p">','<span class="sun">','<i>','日','</i>','</span>','<span class="mon">','<i>','一','</i>','</span>','<span class="tue">','<i>','二','</i>','</span>','<span class="wed">','<i>','三','</i>','</span>','<span class="thurs">','<i>','四','</i>','</span>','<span class="fri">','<i>','五','</i>','</span>','<span class="sat">','<i>','六','</i>','</span>','</p>',
                '</div>',
            	'<div class="calendar_date">',
                	'<p class="calendar_date_p">','<span class="other">','<i>','28','</i>','</span>','<span class="other">','<i>','29','</i>','</span>','<span class="other">','<i>','30','</i>','</span>','<span class="other">','<i>','31','</i>','</span>','<span>','<i>','1','</i>','</span>','<span>','<i>','2','</i>','</span>','<span>','<i>','3','</i>','</span>','</p>',
                    '<p class="calendar_date_p">','<span>','<i>','4','</i>','</span>','<span>','<i>','5','</i>','</span>','<span>','<i>','6','</i>','</span>','<span>','<i>','7','</i>','</span>','<span>','<i>','8','</i>','</span>','<span>','<i>','9','</i>','</span>','<span>','<i>','10','</i>','</span>','</p>',
                    '<p class="calendar_date_p">','<span>','<i>','11','</i>','</span>','<span>','<i>','12','</i>','</span>','<span>','<i>','13','</i>','</span>','<span>','<i>','14','</i>','</span>','<span>','<i>','15','</i>','</span>','<span>','<i>','16','</i>','</span>','<span>','<i>','17','</i>','</span>','</p>',
                    '<p class="calendar_date_p">','<span>','<i>','18','</i>','</span>','<span>','<i>','19','</i>','</span>','<span>','<i>','20','</i>','</span>','<span>','<i>','21','</i>','</span>','<span>','<i>','22','</i>','</span>','<span>','<i>','23','</i>','</span>','<span>','<i>','24','</i>','</span>','</p>',
                    '<p class="calendar_date_p">','<span>','<i>','25','</i>','</span>','<span>','<i>','26','</i>','</span>','<span>','<i>','27','</i>','</span>','<span>','<i>','28','</i>','</span>','<span>','<i>','29','</i>','</span>','<span>','<i>','30','</i>','</span>','<span class="curr">','<i>','31','</i>','</span>','</p>',
                '</div>',
            '</div>',
             '<div class="rc_list">',
             	'<div class="rc_div">','<p class="rc_p">','<a href="javascript:void(0)" title="【会议】业务知识培训">','【会议】业务知识培训','</a>','</p>','</div>',
             	'<div class="rc_div">','<p class="rc_p">','<a href="javascript:void(0)" title="【会议】业务知识培训">','【会议】业务知识培训','</a>','</p>','</div>',
             	'<div class="rc_div">','<p class="rc_p">','<a href="javascript:void(0)" title="【会议】业务知识培训">','【会议】业务知识培训','</a>','</p>','</div>',
             	'<div class="rc_div">','<p class="rc_p">','<a href="javascript:void(0)" title="【会议】业务知识培训">','【会议】业务知识培训','</a>','</p>','</div>',
              '</div>',
        '</div>',
		'</div>'),
		store : restfulStore,
		listeners : {
			afterrender : function(){
				Wlj.TileMgr.addDataCfg({
					controlPanel : tileBox,
					store : restfulStore
				});
				var _this = this;
				this.el.on('click',function(){
					_this.ownerCt.clickFire();
				});
			}
		}
	});
	return tileBox;
})();