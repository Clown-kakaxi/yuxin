Ext.ensible.cal.EventEditFormCust = Ext.extend(Ext.ensible.cal.EventEditForm, {
    
        remindTypeText: 'Remind Type',
        relatedCustomerText: 'Related Customer',
        customerText:'Customers',
        customerNameText:'CustomerNames',
        isTeamText:'ISTeam ',
        /**ext save function, with ajax to RSETFul**/
        onSave: function(){
            if(!this.form.isValid()){
                return;
            }
            if(!this.updateRecord()){
                this.onCancel();
                return;
            }
            this.fireEvent(this.activeRecord.phantom ? 'eventadd' : 'eventupdate', this, this.activeRecord);
        },
        
        
        
        
        
        
         initComponent: function(){
        
        this.addEvents({
            /**
             * @event eventadd
             * Fires after a new event is added
             * @param {Ext.ensible.cal.EventEditForm} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was added
             */
            eventadd: true,
            /**
             * @event eventupdate
             * Fires after an existing event is updated
             * @param {Ext.ensible.cal.EventEditForm} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was updated
             */
            eventupdate: true,
            /**
             * @event eventdelete
             * Fires after an event is deleted
             * @param {Ext.ensible.cal.EventEditForm} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was deleted
             */
            eventdelete: true,
            /**
             * @event eventcancel
             * Fires after an event add/edit operation is canceled by the user and no store update took place
             * @param {Ext.ensible.cal.EventEditForm} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was canceled
             */
            eventcancel: true
        });
                
        this.titleField = new Ext.form.TextField({
            fieldLabel: this.titleLabelText,
            name: Ext.ensible.cal.EventMappings.Title.name,
            anchor: '90%'
        });
        this.dateRangeField = new Ext.ensible.cal.DateRangeField({
            fieldLabel: this.datesLabelText,
            singleLine: false,
            anchor: '90%',
            listeners: {
                'change': this.onDateChange.createDelegate(this)
            }
        });
        this.reminderField = new Ext.ensible.cal.ReminderField({
            name: Ext.ensible.cal.EventMappings.Reminder.name,
            fieldLabel: this.reminderLabelText
        });
        this.notesField = new Ext.form.TextArea({
            fieldLabel: this.notesLabelText,
            name: Ext.ensible.cal.EventMappings.Notes.name,
            grow: true,
            growMax: 250,
            anchor: '100%'
        });
        this.locationField = new Ext.form.TextField({
            fieldLabel: this.locationLabelText,
            name: Ext.ensible.cal.EventMappings.Location.name,
            anchor: '100%'
        });
        this.urlField = new Ext.form.TextField({
            fieldLabel: this.webLinkLabelText,
            name: Ext.ensible.cal.EventMappings.Url.name,
            anchor: '100%'
        });
        /**extends fields*/ 
        
        this.remindTypeField = new Ext.form.ComboBox({
            fieldLabel: this.remindTypeText,
            name: Ext.ensible.cal.EventMappings.RemindType.name,
            store:new Ext.data.ArrayStore({
                fields:['remindType','displayText'],
                data:[
                      [0,'站内'],[1,'短信'],[2,'邮件']
                     ],
                valueField:'remindType',
                displayField:'displayText',
                value:0,
                mode:'local'
                }),
            anchor: '100%'
        });
        
        this.relatedCustomerField = new Ext.form.TextField({
            fieldLabel: this.relatedCustomerText,
            name: Ext.ensible.cal.EventMappings.relatedCustomer.name,
            anchor:'100%'
        });
        
        this.customerField =  new Com.yucheng.bcrm.common.CustomerQueryField({ 
			fieldLabel : this.customerText, 
			labelWidth : 100,
			name : Ext.ensible.cal.EventMappings.CustomerName.name,
		//	custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
		//    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
		    singleSelected:true,//单选复选标志
			editable : false,
			allowBlank:false,//不允许为空
			blankText:"不能为空，请填写",
			anchor : '90%',
			hiddenName:Ext.ensible.cal.EventMappings.Customer.name
	/*		callback :function(){
			
			}
		}*/
		});
        this.isTeamField = new Ext.form.ComboBox({
    		fieldLabel: this.isTeamText,
			editable : false,
			name : Ext.ensible.cal.EventMappings.isTeam.name ,
			mode : 'local',
			triggerAction:'all',
			store:new Ext.data.Store({//是否store
				restful:true,   
				autoLoad :true,
				proxy : new Ext.data.HttpProxy({
						url :basepath+'/lookup.json?name=IF_FLAG'
				}),
				reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, [ 'key', 'value' ])
			}),
			value:'0',
			valueField:'key',
			displayField:'value',
			anchor:'90%'
        });
        /****************/
        var leftFields = [this.titleField, this.dateRangeField,this.customerField,this.isTeamField], 
            rightFields = [this.notesField];
            
        if(this.enableRecurrence){
            this.recurrenceField = new Ext.ensible.cal.RecurrenceField({
                name: Ext.ensible.cal.EventMappings.RRule.name,
                fieldLabel: this.repeatsLabelText,
                anchor: '100%'
            });
            leftFields.splice(2, 0, this.recurrenceField);
        }
        
        if(this.calendarStore){
            this.calendarField = new Ext.ensible.cal.CalendarCombo({
                store: this.calendarStore,
                fieldLabel: this.calendarLabelText,
                name: Ext.ensible.cal.EventMappings.CalendarId.name
            });
            leftFields.splice(2, 0, this.calendarField);
        };
        
        this.items = [{
            id: this.id+'-left-col',
            columnWidth: .4,
            layout: 'form',
            border: false,
            items: leftFields
        },{
            id: this.id+'-right-col',
            columnWidth: .6,
            layout: 'form',
            labelWidth: this.labelWidthRightCol || this.labelWidth,
            border: false,
            items: rightFields
        }];
        
        this.fbar = [{
            text:this.saveButtonText, scope: this, handler: this.onSave
        },{
            cls:'ext-del-btn', text:this.deleteButtonText, scope:this, handler:this.onDelete
        },{
            text:this.cancelButtonText, scope: this, handler: this.onCancel
        }];
        
        Ext.ensible.cal.EventEditForm.superclass.initComponent.call(this);
    }
    
});
Ext.reg('extensible.eventeditformcust', Ext.ensible.cal.EventEditFormCust);
Ext.ensible.cal.CalendarPanelCust = Ext.extend(Ext.ensible.cal.CalendarPanel, {
    
    // private
    initComponent : function(){
        this.tbar = {
            cls: 'ext-cal-toolbar',
            border: true,
            items: []
        };
        
        this.viewCount = 0;
        
        var multiDayViewCount = (this.multiDayViewCfg && this.multiDayViewCfg.dayCount) || 3,
            multiWeekViewCount = (this.multiWeekViewCfg && this.multiWeekViewCfg.weekCount) || 2;
        
        if(this.showNavToday){
            this.tbar.items.push({
                id: this.id+'-tb-today', text: this.todayText, handler: this.onTodayClick, scope: this
            });
        }
        if(this.showNavNextPrev){
            this.tbar.items.push([
                {id: this.id+'-tb-prev', handler: this.onPrevClick, scope: this, iconCls: 'x-tbar-page-prev'},
                {id: this.id+'-tb-next', handler: this.onNextClick, scope: this, iconCls: 'x-tbar-page-next'}
            ]);
        }
        if(this.showNavJump){
            this.tbar.items.push([
                this.jumpToText,
                {id: this.id+'-tb-jump-dt', xtype: 'datefield',format:'Y-m-d', showToday: false},
                {id: this.id+'-tb-jump', text: this.goText, handler: this.onJumpClick, scope: this}
            ]);
        }
        
        this.tbar.items.push('->');
        
        if(this.showDayView){
            this.tbar.items.push({
                id: this.id+'-tb-day', text: this.dayText, handler: this.onDayNavClick, scope: this, toggleGroup: this.id+'-tb-views'
            });
            this.viewCount++;
        }
        if(this.showMultiDayView){
            var text = String.format(this.getMultiDayText(multiDayViewCount), multiDayViewCount);
            this.tbar.items.push({
                id: this.id+'-tb-multiday', text: text, handler: this.onMultiDayNavClick, scope: this, toggleGroup: this.id+'-tb-views'
            });
            this.viewCount++;
        }
        if(this.showWeekView){
            this.tbar.items.push({
                id: this.id+'-tb-week', text: this.weekText, handler: this.onWeekNavClick, scope: this, toggleGroup: this.id+'-tb-views'
            });
            this.viewCount++;
        }
        if(this.showMultiWeekView){
            var text = String.format(this.getMultiWeekText(multiWeekViewCount), multiWeekViewCount);
            this.tbar.items.push({
                id: this.id+'-tb-multiweek', text: text, handler: this.onMultiWeekNavClick, scope: this, toggleGroup: this.id+'-tb-views'
            });
            this.viewCount++;
        }
        if(this.showMonthView || this.viewCount == 0){
            this.tbar.items.push({
                id: this.id+'-tb-month', text: this.monthText, handler: this.onMonthNavClick, scope: this, toggleGroup: this.id+'-tb-views'
            });
            this.viewCount++;
            this.showMonthView = true;
        }
        
        var idx = this.viewCount-1;
        this.activeItem = this.activeItem === undefined ? idx : (this.activeItem > idx ? idx : this.activeItem);
        
        if(this.showNavBar === false){
            delete this.tbar;
            this.addClass('x-calendar-nonav');
        }
        
        Ext.ensible.cal.CalendarPanel.superclass.initComponent.call(this);
        
        this.addEvents({
            /**
             * @event eventadd
             * Fires after a new event is added to the underlying store
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was added
             */
            eventadd: true,
            /**
             * @event eventupdate
             * Fires after an existing event is updated
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was updated
             */
            eventupdate: true,
            /**
             * @event beforeeventdelete
             * Fires before an event is deleted by the user. This is a cancelable event, so returning false from a handler 
             * will cancel the delete operation.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that was deleted
             * @param {Ext.Element} el The target element
             */
            beforeeventdelete: true,
            /**
             * @event eventdelete
             * Fires after an event is deleted by the user.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that was deleted
             * @param {Ext.Element} el The target element
             */
            eventdelete: true,
            /**
             * @event eventcancel
             * Fires after an event add/edit operation is canceled by the user and no store update took place
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The new {@link Ext.ensible.cal.EventRecord record} that was canceled
             */
            eventcancel: true,
            /**
             * @event viewchange
             * Fires after a different calendar view is activated (but not when the event edit form is activated)
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.CalendarView} view The view being activated (any valid {@link Ext.ensible.cal.CalendarView CalendarView} subclass)
             * @param {Object} info Extra information about the newly activated view. This is a plain object 
             * with following properties:<div class="mdetail-params"><ul>
             * <li><b><code>activeDate</code></b> : <div class="sub-desc">The currently-selected date</div></li>
             * <li><b><code>viewStart</code></b> : <div class="sub-desc">The first date in the new view range</div></li>
             * <li><b><code>viewEnd</code></b> : <div class="sub-desc">The last date in the new view range</div></li>
             * </ul></div>
             */
            viewchange: true,
            /**
             * @event editdetails
             * Fires when the user selects the option to edit the selected event in the detailed edit form
             * (by default, an instance of {@link Ext.ensible.cal.EventEditForm}). Handling code should hide the active
             * event editor and transfer the current event record to the appropriate instance of the detailed form by showing it
             * and calling {@link Ext.ensible.cal.EventEditForm#loadRecord loadRecord}.
             * @param {Ext.ensible.cal.CalendarPanel} this The CalendarPanel
             * @param {Ext.ensible.cal.CalendarView} view The currently active {@link Ext.ensible.cal.CalendarView CalendarView} subclass
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} that is currently being edited
             * @param {Ext.Element} el The target element
             */
            editdetails: true
            
            
            //
            // NOTE: CalendarPanel also relays the following events from contained views as if they originated from this:
            //
            
            /**
             * @event eventsrendered
             * Fires after events are finished rendering in the view
             * @param {Ext.ensible.cal.CalendarPanel} this 
             */
            /**
             * @event eventclick
             * <p>Fires after the user clicks on an event element.</p>
             * <p><strong>NOTE:</strong> This version of <code>eventclick</code> differs from the same event fired directly by
             * {@link Ext.ensible.cal.CalendarView CalendarView} subclasses in that it provides a default implementation (showing
             * the default edit window) and is also cancelable (if a handler returns <code>false</code> the edit window will not be shown).
             * This event when fired from a view class is simply a notification that an event was clicked and has no default behavior.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that was clicked on
             * @param {HTMLNode} el The DOM node that was clicked on
             */
            /**
             * @event rangeselect
             * Fires after the user drags on the calendar to select a range of dates/times in which to create an event
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Object} dates An object containing the start (StartDate property) and end (EndDate property) dates selected
             * @param {Function} callback A callback function that MUST be called after the event handling is complete so that
             * the view is properly cleaned up (shim elements are persisted in the view while the user is prompted to handle the
             * range selection). The callback is already created in the proper scope, so it simply needs to be executed as a standard
             * function call (e.g., callback()).
             */
            /**
             * @event eventover
             * Fires anytime the mouse is over an event element
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that the cursor is over
             * @param {HTMLNode} el The DOM node that is being moused over
             */
            /**
             * @event eventout
             * Fires anytime the mouse exits an event element
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that the cursor exited
             * @param {HTMLNode} el The DOM node that was exited
             */
            /**
             * @event beforedatechange
             * Fires before the start date of the view changes, giving you an opportunity to save state or anything else you may need
             * to do prior to the UI view changing. This is a cancelable event, so returning false from a handler will cancel both the
             * view change and the setting of the start date.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Date} startDate The current start date of the view (as explained in {@link #getStartDate}
             * @param {Date} newStartDate The new start date that will be set when the view changes
             * @param {Date} viewStart The first displayed date in the current view
             * @param {Date} viewEnd The last displayed date in the current view
             */
            /**
             * @event dayclick
             * Fires after the user clicks within a day/week view container and not on an event element
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Date} dt The date/time that was clicked on
             * @param {Boolean} allday True if the day clicked on represents an all-day box, else false.
             * @param {Ext.Element} el The Element that was clicked on
             */
            /**
             * @event datechange
             * Fires after the start date of the view changes
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Date} startDate The start date of the view (as explained in {@link #getStartDate}
             * @param {Date} viewStart The first displayed date in the view
             * @param {Date} viewEnd The last displayed date in the view
             */
            /**
             * @event beforeeventmove
             * Fires before an event element is dragged by the user and dropped in a new position. This is a cancelable event, so 
             * returning false from a handler will cancel the move operation.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that will be moved
             */
            /**
             * @event eventmove
             * Fires after an event element is dragged by the user and dropped in a new position
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that was moved with
             * updated start and end dates
             */
            /**
             * @event initdrag
             * Fires when a drag operation is initiated in the view
             * @param {Ext.ensible.cal.CalendarPanel} this
             */
            /**
             * @event dayover
             * Fires while the mouse is over a day element 
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Date} dt The date that is being moused over
             * @param {Ext.Element} el The day Element that is being moused over
             */
            /**
             * @event dayout
             * Fires when the mouse exits a day element 
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Date} dt The date that is exited
             * @param {Ext.Element} el The day Element that is exited
             */
            /**
             * @event beforeeventresize
             * Fires after the user drags the resize handle of an event to resize it, but before the resize operation is carried out.
             * This is a cancelable event, so returning false from a handler will cancel the resize operation. <strong>NOTE:</strong>
             * This event is only fired from views that support event resizing.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that was resized
             * containing the updated start and end dates
             */
            /**
             * @event eventresize
             * Fires after the user drags the resize handle of an event and the resize operation is complete. <strong>NOTE:</strong>
             * This event is only fired from views that support event resizing.
             * @param {Ext.ensible.cal.CalendarPanel} this
             * @param {Ext.ensible.cal.EventRecord} rec The {@link Ext.ensible.cal.EventRecord record} for the event that was resized
             * containing the updated start and end dates
             */
        });
        
        this.layout = 'card'; // do not allow override
        this.addClass('x-cal-panel');
        
        if(this.eventStore){
            this.store = this.eventStore;
            delete this.eventStore;
        }
        this.setStore(this.store);
        
        var sharedViewCfg = {
            showToday: this.showToday,
            todayText: this.todayText,
            showTodayText: this.showTodayText,
            showTime: this.showTime,
            readOnly: this.readOnly,
            enableRecurrence: this.enableRecurrence,
            store: this.store,
            calendarStore: this.calendarStore,
            editModal: this.editModal,
            enableEditDetails: this.enableEditDetails,
            ownerCalendarPanel: this
        };
        
        if(this.showDayView){
            var day = Ext.apply({
                xtype: 'extensible.dayview',
                title: this.dayText
            }, sharedViewCfg);
            
            day = Ext.apply(Ext.apply(day, this.viewConfig), this.dayViewCfg);
            day.id = this.id+'-day';
            this.initEventRelay(day);
            this.add(day);
        }
        if(this.showMultiDayView){
            var mday = Ext.apply({
                xtype: 'extensible.multidayview',
                title: this.getMultiDayText(multiDayViewCount)
            }, sharedViewCfg);
            
            mday = Ext.apply(Ext.apply(mday, this.viewConfig), this.multiDayViewCfg);
            mday.id = this.id+'-multiday';
            this.initEventRelay(mday);
            this.add(mday);
        }
        if(this.showWeekView){
            var wk = Ext.applyIf({
                xtype: 'extensible.weekview',
                title: this.weekText
            }, sharedViewCfg);
            
            wk = Ext.apply(Ext.apply(wk, this.viewConfig), this.weekViewCfg);
            wk.id = this.id+'-week';
            this.initEventRelay(wk);
            this.add(wk);
        }
        if(this.showMultiWeekView){
            var mwk = Ext.applyIf({
                xtype: 'extensible.multiweekview',
                title: this.getMultiWeekText(multiWeekViewCount)
            }, sharedViewCfg);
            
            mwk = Ext.apply(Ext.apply(mwk, this.viewConfig), this.multiWeekViewCfg);
            mwk.id = this.id+'-multiweek';
            this.initEventRelay(mwk);
            this.add(mwk);
        }
        if(this.showMonthView){
            var month = Ext.applyIf({
                xtype: 'extensible.monthview',
                title: this.monthText,
                listeners: {
                    'weekclick': {
                        fn: function(vw, dt){
                            this.showWeek(dt);
                        },
                        scope: this
                    }
                }
            }, sharedViewCfg);
            
            month = Ext.apply(Ext.apply(month, this.viewConfig), this.monthViewCfg);
            month.id = this.id+'-month';
            this.initEventRelay(month);
            this.add(month);
        }

        this.add(Ext.applyIf({
            xtype: 'extensible.eventeditformcust',
            id: this.id+'-edit',
            calendarStore: this.calendarStore,
            enableRecurrence: this.enableRecurrence,
            listeners: {
                'eventadd':    { scope: this, fn: this.onEventAdd },
                'eventupdate': { scope: this, fn: this.onEventUpdate },
                'eventdelete': { scope: this, fn: this.onEventDelete },
                'eventcancel': { scope: this, fn: this.onEventCancel }
            }
        }, this.editViewCfg));
    }   ,// private
    onEventAdd: function(form, rec){
        if(!rec.store){
            this.store.add(rec);
            this.save();
        }
        this.fireEvent('eventadd', this, rec);
        if(Ext.getCmp('ext_cal_fullitemwin')){
        	if(Ext.getCmp('ext_cal_fullitemwin').hidden){
        		Ext.getCmp('ext_cal_fullitem_mynotic').getStore().ownLoad(this.store);
        		Ext.getCmp('ext_cal_fullitemwin').show();
        	}
        }
    }
});

Ext.reg('extensible.calendarpanelcust', Ext.ensible.cal.CalendarPanelCust);

Ext.override(Ext.ensible.cal.CalendarPanelCust,{
	onEventAdd : function(form, rec){
		var view = this;
		Ext.Ajax.request({
			url: basepath+'/workformschedule.json',
			method: 'POST',
			params:{
				eventJson : Ext.encode(rec.data)
			},
			success:function(a,b,c,d){
				if(view.activeView.isVisible()){
					view.activeView.hide();
				}
				view.setActiveView(view.preEditView);
				view.activeView.store.load({
					params:{
						start:view.activeView.viewStart.dateFormat('Ymd'),
						end:view.activeView.viewEnd.dateFormat('Ymd')
					}	
				});
			}
		});
    	this.fireEvent('eventadd', this, rec);
	},	// private
    onEventUpdate: function(form, rec){
        var view = this;
		Ext.Ajax.request({
			url: basepath+'/workformschedule.json',
			method: 'POST',
			params:{
				eventJson : Ext.encode(rec.data)
			},
			success:function(a,b,c,d){
				if(view.activeView.isVisible()){
					view.activeView.hide();
				}
				view.setActiveView(view.preEditView);
				view.activeView.store.load({
					params:{
						start:view.activeView.viewStart.dateFormat('Ymd'),
						end:view.activeView.viewEnd.dateFormat('Ymd')
					}	
				});
			}
		});
        this.fireEvent('eventupdate', this, rec);
    },
    // private
    onEventDelete: function(form, rec){
    	var view = this;
        Ext.Ajax.request({
        	url: basepath+'/workformschedule/'+rec.id+'.json',
			method: 'DELETE',
			success:function(a,b,c,d){
        		if(view.activeView.isVisible()){
        			view.activeView.hide();
        		}
        		view.setActiveView(view.preEditView);
        		view.activeView.store.load({
        			params:{
						start:view.activeView.viewStart.dateFormat('Ymd'),
						end:view.activeView.viewEnd.dateFormat('Ymd')
        			}	
        		});
        	}
        });
        this.fireEvent('eventdelete', this, rec);
    }
});
Ext.override(Ext.ensible.cal.CalendarView,{
	onEventAdd: function(form, rec){
		var view = this;
		Ext.Ajax.request({
			url: basepath+'/workformschedule.json',
			method: 'POST',
			params:{
				eventJson : Ext.encode(rec.data)
			},
			success:function(a,b,c,d){
				if(view.editWin.isVisible()){
					view.editWin.hide();
				}
				view.store.load({
					params:{
						start:view.viewStart.dateFormat('Ymd'),
						end:view.viewEnd.dateFormat('Ymd')
					}	
				});
			}
		});
		this.fireEvent('eventadd', this, rec);
	},
	// private
    onEventUpdate: function(form, rec){
        var view = this;
		Ext.Ajax.request({
			url: basepath+'/workformschedule.json',
			method: 'POST',
			params:{
				eventJson : Ext.encode(rec.data)
			},
			success:function(a,b,c,d){
				if(view.editWin.isVisible()){
					view.editWin.hide();
				}
				view.store.load({
					params:{
						start:view.viewStart.dateFormat('Ymd'),
						end:view.viewEnd.dateFormat('Ymd')
					}	
				});
			}
		});
        this.fireEvent('eventupdate', this, rec);
    },
    // private
    onEventDelete: function(form, rec){
    	var view = this;
        Ext.Ajax.request({
        	url: basepath+'/workformschedule/'+rec.id+'.json',
			method: 'DELETE',
			success:function(a,b,c,d){
        		if(view.editWin.isVisible()){
        			view.editWin.hide();
        		}
        		view.store.load({
        			params:{
						start:view.viewStart.dateFormat('Ymd'),
						end:view.viewEnd.dateFormat('Ymd')
        			}	
        		});
        	}
        });
        this.fireEvent('eventdelete', this, rec);
    }
});