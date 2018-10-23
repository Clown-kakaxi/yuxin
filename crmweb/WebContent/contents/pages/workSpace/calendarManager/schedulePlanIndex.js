Ext.onReady(function(){
    Ext.QuickTips.init();

    Ext.util.Format.comboRenderer = function(combo){
        return function(value){
            var record = combo.findRecord(combo.valueField, value);
            return record ? record.get(combo.displayField) : combo.valueNotFoundText;
        };
    };
    /********************************************store*************************************************/
    
    /**
     * @par dataStr format:'yyyy-MM-dd hh:mm:ss.ms'
     * @return Date Object.
     */
    function toDateTime(dateStr){
        var result = new Date();
        result.setFullYear(dateStr.substring(0,4));
        var month = dateStr.substring(5,7);
        var monthInt = parseInt(month,10);
        monthInt--;
        if(monthInt<0){
            monthInt = 11;
        }
        result.setMonth(monthInt);
        result.setDate(dateStr.substring(8,10));
        result.setHours(dateStr.substring(11,13));
        result.setMinutes(dateStr.substring(14,16));
        result.setSeconds(dateStr.substring(17,19));
        return result;
    }
    Ext.ensible.cal.CalendarMappings = {
    		CalendarId:   {name:'ID', mapping: 'remindBelong', type: 'string'}, // int by default
    		Title:        {name:'CalTitle', mapping: 'cal_title', type: 'string'},
    		Description:  {name:'Desc', mapping: 'cal_desc', type: 'string'},
    		ColorId:      {name:'Color', mapping: 'cal_color', type: 'int'},
    		IsHidden:     {name:'Hidden', mapping: 'hidden', type: 'boolean'}
    };
    Ext.ensible.cal.CalendarRecord.reconfigure();
    var calendarStore = new Ext.ensible.sample.CalendarStore({
    	data: Ext.ensible.sample.CalendarDataCustom
    }); 
    
    Ext.ensible.cal.EventMappings = {
    		EventId:     {name: 'scheduleId', type:'string'}, // int by default
    		CalendarId:  {name: 'remindBelong', type: 'string'}, // int by default
    		Title:       {name: 'scheduleTitle'},
    		StartDate:   {name: 'startDate', type: 'date', dateFormat: 'c'},
    		EndDate:     {name: 'endDate', type: 'date', dateFormat: 'c'},
    		RRule:       {name: 'RecurRule', mapping: 'recur_rule'},
    		Location:    {name: 'Location', mapping: 'location'},
    		Notes:       {name: 'scheduleContent', mapping: 'scheduleContent'},
    		Url:         {name: 'LinkUrl', mapping: 'link_url'},
    		IsAllDay:    {name: 'all_day', mapping: 'all_day', type: 'boolean'},
    		Reminder:    {name: 'Reminder', mapping: 'reminder'},    
    		RemindType:  {name: 'RemindType', mapping: 'remindType', type: 'string'},
    		relatedCustomer:{name: 'relatedCustomer', mapping:'relationCust', type:'string'},   
    		Customer:    {name: 'relationCust', type:'string'},
    		CustomerName:{name: 'relationCustName',type:'string'},
    		isTeam:        {name: 'isTeam',type:'String'},
    		CreatedBy:   {name: 'CreatedBy', mapping: 'created_by'},
    		IsPrivate:   {name: 'Private', mapping:'private', type:'boolean'}
    };
    var dataFields = [{name:'scheduleId', mapping:'SCH_ID'},
                      {name:'scheduleTitle',mapping:'SCHEDULE_COUNT'},
                      {name:'startTime',mapping:'START_TIME'},
                      {name:'endTime',mapping:'END_TIME'},
                      {name:'startDate',mapping:'SCH_DATE',type: 'date', dateFormat: 'c'},
                      {name:'endDate',mapping:'SCH_DATE',type: 'date', dateFormat: 'c'},
                      {name:'remindCycle',mapping:'REMIND_CYCLE'},
                      {name:'isRemind',mapping:'IS_REMIND'},
                      {name:'isRepeat',mapping:'IS_REPEAT'},
                      {name:'all_day',mapping:'ALL_DAY',type:'boolean'},
                      {name:'beforeheadDay',mapping:'BEFOREHEAD_DAY'},
                      {name:'relationCust',mapping:'RELATION_CUST'},
                      {name:'creator',mapping:'USER_ID'},
                      {name:'createOrganizer',mapping:'CREATE_ORG'},
                      {name:'createDate',mapping:'SCH_DATE'},
                      {name:'remindStsrtTime',mapping:'remindStsrtTime'},
                      {name:'remindEndTime',mapping:'remindEndTime'},
                      {name:'remindType',mapping:'REMIND_TYPE'},
                      {name:'remindBelong',mapping:'REMIND_BELONG'},
                      {name:'scheduleContent',mapping:'SCHEDULE_CONTENT'},
                      {name:'isTeam',mapping:'IS_TEAM'},
                      {name:'relationCustName',mapping:'RELATION_CUST_NAME'}];
    Ext.ensible.cal.EventRecord.reconfigure();   
    var eventProxy = new Ext.data.HttpProxy({
    	api:{
    		read:{
    			url:basepath+'/ocrmFWpSchedule!querySchedule.json',
    			method:'GET'
    		},
    		destroy:{
    			 url: basepath+'/workformschedule.json',
                 method: 'POST'
    		},
    		create:{
    			 url: basepath+'/workformschedule.json',
                 method: 'POST'
    		},
    		update:{
    			 url: basepath+'/workformschedule.json',
                 method: 'POST'
    		}
    	},
    	 listeners: {
            exception: function(proxy, type, action, o, res, arg){
                //var msg = res.message ? res.message : Ext.decode(res.responseText).message;
                // ideally an app would provide a less intrusive message display
                debugger;
                Ext.Msg.alert('Server Error', res.responseText);
            }
        }
    });
    var reader = new Ext.data.JsonReader({
    	idProperty: 'SCH_ID',
    	successProperty: false,
    	root:'json.data',
    	fields: dataFields
    });
    var eventStore = new Ext.ensible.cal.EventStore({
    	restful : true,
    	proxy : eventProxy,
    	reader : reader,
   
    	 onWrite: function(store, action, data, resp, rec){
    		return;
    	}
    });
    Ext.ensible.cal.EventRecord.reconfigure(); 
    var calenp = new Ext.ensible.cal.CalendarPanelCust({
    	eventStore: eventStore,
    	calendarStore:calendarStore,
    	monthViewCfg : {
    		showHeader : true,
    		showWeekLinks : true,
    		showWeekNumbers : true
    		
    	},
    	viewConfig:{
    		enableContextMenus : false
    	},
    	layout:'fit',
    	title: '<span style="text-align:center;font-size:14px;">日程安排</span>'              
    }); 
 
    var viewport = new Ext.Viewport({
        layout : 'fit',
        items : [ calenp ]
    });
    eventStore.load({
    	params:{ 
    		start:calenp.activeView.viewStart.dateFormat('Y-m-d'),
    		end:calenp.activeView.viewEnd.dateFormat('Y-m-d')
    	},callback:function(){
    	}
    });
    var currDate = new Date();
    var curr_day = DateFor(currDate);
});
