/**
*@description 360客户视图 个人喜好信息(兴趣爱好)
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
		]);

var custId =_custId;
var needCondition = false;
var url = basepath + '/lookup.json?name=LOVE_INVEST_TYPE';
var fields = [{name:'LIKE_INVEST_TYPE',text:'',hidden :true}];
/**
*@description 自定义动态数据checkboxgroup
*/

Ext.ns('UX.form');
UX.form.CheckboxGroup=Ext.extend(Ext.form.CheckboxGroup,{
	//每行显示多少列，默认3列
	columns:3,
	//数据URL
	dataUrl:'',
	//请求数据URL参数
	params:{},
	//boxName
	boxName:'',//具体每个check的name
	//checkbox对应的inputValue值字段
	labelField:'key',
	//checkbox对应的boxLabel值字段
	valueField:'value',
	/**
	 * 
	 * @param {} val 如：  '1,2,3'
	 * @return {}
	 */
	setValue: function(val){
		//注：此处的空字符是保存时产生的，故要先替换掉
		val = val.replace(/ /g,'')
        val = String(val).split(',');
		val = String(val).split('|')[0];
        this.eachItem(function(item){
            if(val.indexOf(item.inputValue)> -1){
                item.setValue(true);
            }
        });
        return this;
    },
	/**
	 * 初始化自定义组件
	 */
	initComponent:function(){
		var _this = this;
		if(_this.items==undefined && _this.item==undefined){
			_this.items=[{boxLabel: '',name: _this.name||'',hidden:true, inputValue: ''}];
			_this.initStore();
		}
		UX.form.CheckboxGroup.superclass.initComponent.call(_this);
	},
	/**
	 * 渲染checkboxgroup对应的checkbox项
	 */
	initStore:function(){
		var _this = this;
		if(_this.dataUrl != ""){
			new Ext.data.Store({
		        restful : true,
		        autoLoad : true,
		        sortInfo : {
		            field:_this.valueField,
		            direction:'ASC'
		        },
		        proxy : new Ext.data.HttpProxy({
		            url : _this.dataUrl
		        }),
		        reader : new Ext.data.JsonReader({
		            root : 'JSON'
		        }, [ _this.labelField, _this.valueField ]),
		        listeners:{
					load:function(store,records){
						var columns =_this.panel.items;
						for(var i=0;i<columns.items.length;i++){
							var column = columns.items[i];
							column.removeAll();
						}
						_this.items.clear();
						for(var i=0,k=0;i<records.length;i++){
							var d = records[i].data;
							var chk = new Ext.form.Checkbox({boxLabel: d[_this.valueField],name: _this.boxName||'',hideMode:'display', inputValue: d[_this.labelField]});
							var column=columns.items[k];
							k++;
							if(k >= columns.items.length) k=0;
							checkbox = column.add(chk);
							_this.items.add(checkbox);
						}
						_this.doLayout();
						_total_ckg = _total_ckg - 1;
					}
				}
		    });
		}
	}
});

//定义总共有多少个checkboxgroup，
var _total_ckg = 0;//默认为0，具体使用时赋值，当使其全部load完毕之后

Ext.onReady(function() {

//注：store在客户视图里，设置autoLoad : true表示比form的store.load慢，导致赋值后还是显示编码，故手工调用load()方法
//宗教信仰
var religionTypeStore = new Ext.data.Store({
    restful : true,
    autoLoad : true,
    sortInfo : {
        field:'key',
        direction:'DESC'
    },
    proxy : new Ext.data.HttpProxy( {
        url : basepath + '/lookup.json?name=RELIGION_TYPE'
    }),
    reader : new Ext.data.JsonReader( {
        root : 'JSON'
    }, [ 'key', 'value' ])
});
religionTypeStore.load

//定义总共有多少个checkboxgroup，
_total_ckg = 17;

var loveChannelCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeEchanlType',
	fieldLabel: '喜好的电子渠道',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_CHANNEL',
	anchor : '95%'
});
var loveBusiTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeBusiType',
	fieldLabel: '偏好的金融业务类型',
	labelStyle: 'text-align:right;disabled:true',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_BUSI_TYPE',
	anchor : '95%'
});
var loveLeisureTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeLeisureType',
	fieldLabel: '喜好休闲类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_LEISURE_TYPE',
	anchor : '95%'
});
var loveMediaTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeMediaType',
	fieldLabel: '喜好媒体类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_MEDIA_TYPE',
	anchor : '95%'
});
var loveSportTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeSportType',
	fieldLabel: '喜好运动类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_SPORT_TYPE',
	anchor : '95%'
});
var loveMagazineTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeMagazineType',
	fieldLabel: '喜好杂志类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_MAGAZINE_TYPE',
	anchor : '95%'
});
var loveMovieTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeFilmType',
	fieldLabel: '喜好电影节目类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_MOVIE_TYPE',
	anchor : '95%'
});
var lovePetTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likePetType',
	fieldLabel: '喜好宠物类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_PET_TYPE',
	anchor : '95%'
});
var loveCollectTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeCollectionType',
	fieldLabel: '喜好收藏类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_COLLECT_TYPE',
	anchor : '95%'
});
var loveInvestTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeInvestType',
	fieldLabel: '喜好投资类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_INVEST_TYPE',
	anchor : '95%'
});
var loveRandTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeBrandType',
	fieldLabel: '喜好品牌类型',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_BRAND_TYPE',
	anchor : '95%'
});
var loveFinaServTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'finaServ',
	fieldLabel: '希望得到的理财服务',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_FINA_SERV_TYPE',
	anchor : '95%'
});
var loveContactTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'contactType',
	fieldLabel: '希望理财经理的联系方式',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_CONTACT_TYPE',
	anchor : '95%'
});
var loveFinaNewsTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'finaNews',
	fieldLabel: '希望得到的理财咨询',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_FINA_NEWS_TYPE',
	anchor : '95%'
});
var loveSalonTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'salon',
	fieldLabel: '希望参加的沙龙活动',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_SALON_TYPE',
	anchor : '95%'
});
var loveInterestsTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'interests',
	fieldLabel: '个人兴趣爱好',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_INTERESTS_TYPE',
	anchor : '98%'
});
var loveConTimeTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeContactTime',
	fieldLabel: '希望联系的时间',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LOVE_CON_TIME_TYPE',
	anchor : '95%'
});

var loveInvestCycleTypeCkg = new UX.form.CheckboxGroup({
	columns:5,
	boxName:'likeInvestCycle',
	fieldLabel: '投资周期偏好',
	labelStyle: 'text-align:right;',
	labelWidth : 150,
	dataUrl : basepath + '/lookup.json?name=LIKE_INVEST_CYCLE',
	anchor : '95%'
});

var rsRecord = new Ext.data.Record.create([
                                           {name:'custId',mapping:'CUST_ID'},
                                           {name:'likeEchanlType',mapping:'LIKE_ECHANL_TYPE'},
                                           {name:'likeBusiType',mapping:'LIKE_BUSI_TYPE'},
                                           {name:'likeLeisureType',mapping:'LIKE_LEISURE_TYPE'},
                                           {name:'likeMediaType',mapping:'LIKE_MEDIA_TYPE'},
                                           {name:'likeSportType',mapping:'LIKE_SPORT_TYPE'},
                                           {name:'likeMagazineType',mapping:'LIKE_MAGAZINE_TYPE'},
                                           {name:'likeFilmType',mapping:'LIKE_FILM_TYPE'},
                                           {name:'likePetType',mapping:'LIKE_PET_TYPE'},
                                           {name:'likeCollectionType',mapping:'LIKE_COLLECTION_TYPE'},
                                           {name:'likeInvestType',mapping:'LIKE_INVEST_TYPE'},
                                           {name:'finaServ',mapping:'FINA_SERV'},
                                           {name:'contactType',mapping:'CONTACT_TYPE'},
                                           {name:'finaNews',mapping:'FINA_NEWS'},
                                           {name:'salon',mapping:'SALON'},
                                           {name:'interests',mapping:'INTERESTS'},
                                           {name:'likeContactTime',mapping:'LIKE_CONTACT_TIME'},
                                           {name:'likeInvestCycle',mapping:'INVEST_CYCLE'},//投资周期偏好

                                           {name:'likeBrandType',mapping:'LIKE_BRAND_TYPE'},
                                           {name:'custTaboo',mapping:'CUST_TABOO'},
                                           {name:'custReligion',mapping:'CUST_RELIGION'},
                                           {name:'likeBrandText',mapping:'LIKE_BRAND_TEXT'},
                                           {name:'avoid',mapping:'AVOID'},
                                           {name:'other',mapping:'OTHER'},
                                           {name:'test',mapping:''}
                                       ]);
var rsreader = new Ext.data.JsonReader( {
	root : 'json.data',
	totalProperty : 'json.count'
}, rsRecord);
                                   	
var opForm = new Ext.form.FormPanel({
	id : 'opForm',
	layout : 'form',
	labelAlign : 'right',
	autoScroll : true,
	frame : true,
	buttonAlign : "center",
	items:[{
		xtype:'fieldset',
	   	title:'喜好的电子渠道',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveChannelCkg,
			         {xtype:'textfield',name:'loveChannelCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'偏好的金融业务类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveBusiTypeCkg,
			         {xtype:'textfield',name:'loveBusiTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好休闲类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveLeisureTypeCkg,
				     {xtype:'textfield',name:'loveLeisureTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好媒体类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveMediaTypeCkg,
				     {xtype:'textfield',name:'loveMediaTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好运动类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveSportTypeCkg,
				     {xtype:'textfield',name:'loveSportTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好杂志类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveMagazineTypeCkg,
				     {xtype:'textfield',name:'loveMagazineTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好电影节目类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveMovieTypeCkg,
			         {xtype:'textfield',name:'loveMovieTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好宠物类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         lovePetTypeCkg,
				     {xtype:'textfield',name:'lovePetTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好收藏类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveCollectTypeCkg,
			    	{xtype:'textfield',name:'loveCollectTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好投资类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveInvestTypeCkg,
			         {xtype:'textfield',name:'loveInvestTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'喜好品牌类型',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
				loveRandTypeCkg,
				{xtype:'textfield',name:'likeBrandText',fieldLabel:'客户其他喜好品牌',width:120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'希望得到的理财服务',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveFinaServTypeCkg,
					 {xtype:'textfield',name:'loveFinaServTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'希望理财经理的联系方式',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveContactTypeCkg,
					 {xtype:'textfield',name:'loveContactTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'希望得到的理财咨询',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveFinaNewsTypeCkg,
					 {xtype:'textfield',name:'loveFinaNewsTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'希望参加的沙龙活动',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveSalonTypeCkg,
					 {xtype:'textfield',name:'loveSalonTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'个人兴趣爱好',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveInterestsTypeCkg,
					 {xtype:'textfield',name:'loveInterestsTypeCkg',fieldLabel:'其他',width : 120}
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'希望联系的时间',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
					loveConTimeTypeCkg
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'投资周期偏好',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			         loveInvestCycleTypeCkg
			]
		}]
	},{
		xtype:'fieldset',
	   	title:'其他信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		anchor : '95%',
		items : [{
			layout : 'form',
			columnWidth : .25,
			labelWidth : 140,
			items : [
			    {xtype:'combo',name:'custReligion',hiddenName:'custReligion',fieldLabel:'宗教信仰',labelStyle: 'text-align:right;',anchor:'95%',triggerAction:'all',
			    	 mode:'local',store: religionTypeStore,resizable:true,forceSelection : true,valueField:'key',displayField:'value'},
			    	{xtype:'textfield',name:'avoid',fieldLabel:'忌讳',labelStyle: 'text-align:right;',maxLength :200,anchor : '95%'},
				{xtype:'textarea',name:'custTaboo',fieldLabel:'特别需求',labelStyle: 'text-align:right;',maxLength :200,anchor : '95%'},
				{xtype:'textarea',name:'other',fieldLabel:'备注',labelStyle: 'text-align:right;',maxLength :200,anchor : '95%'}
			]
		}]
	}],
	buttons:[{
		id : '_saveHobby',
		text :'保存',
		hidden:JsContext.checkGrant('hobby_saveHobby'),
		handler:function(){
			if (!opForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入');
				return false;
			}
			Ext.Msg.wait('正在保存数据,请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/acrmFCiPerLikeinfo.json?custId='+custId,
				method : 'POST',
				form : opForm.form.id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Ajax.request({
						url : basepath+'/session-info!getPid.json',
						method : 'GET',
						success : function(a,b,v) {
						    var idStr = Ext.decode(a.responseText).pid;
//						    opForm.getForm().findField('custId').setValue(idStr);
						    Ext.Msg.alert('提示', '操作成功');
							store.reload();
						}
					});
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
				}
			});
		}
	}]
});

var store = new Ext.data.Store({
	restful:true,
    proxy : new Ext.data.HttpProxy({
		url:basepath + '/acrmFCiPerLikeinfo.json'
	}),
    reader: rsreader
});
//初始化家庭信息方法
store.load({
	params : {
		custId : custId
	},
	method : 'GET',
    callback:function(){
    	window.__setFormValue();
	}
});
/**
 * 为form表单赋值，主要是解决异步请求时的问题
 * 注：把方法定义到window对象上是因为定时器带参数时，会找不到此方法，故加在对象上调用即可
 */
window.__setFormValue = function(){
	if(_total_ckg > 0){
		setTimeout('window.__setFormValue(\''+0+'\');',100);
	}else{
		fuc();
		if(store.getCount() != 0){
			for(var i=0;i<store.getCount();i++){
				var data = store.getAt(i).data;
	    		opForm.getForm().loadRecord(store.getAt(i));
	    		loveChannelCkg.setValue(data.likeEchanlType);
	    		opForm.getForm().findField('loveChannelCkg').setValue(data.likeEchanlType.split('|')[1]);
	    		
	    		loveBusiTypeCkg.setValue(data.likeBusiType);
	    		opForm.getForm().findField('loveBusiTypeCkg').setValue(data.likeBusiType.split('|')[1]);
	    		
	    		loveLeisureTypeCkg.setValue(data.likeLeisureType);
	    		opForm.getForm().findField('loveLeisureTypeCkg').setValue(data.likeLeisureType.split('|')[1]);
	    		
	    		loveMediaTypeCkg.setValue(data.likeMediaType);
	    		opForm.getForm().findField('loveMediaTypeCkg').setValue(data.likeMediaType.split('|')[1]);
	    		
	    		loveSportTypeCkg.setValue(data.likeSportType);
	    		opForm.getForm().findField('loveSportTypeCkg').setValue(data.likeSportType.split('|')[1]);
	    		
	    		loveMagazineTypeCkg.setValue(data.likeMagazineType);
	    		opForm.getForm().findField('loveMagazineTypeCkg').setValue(data.likeMagazineType.split('|')[1]);
	    		
	    		loveMovieTypeCkg.setValue(data.likeFilmType);
	    		opForm.getForm().findField('loveMovieTypeCkg').setValue(data.likeFilmType.split('|')[1]);
	    		
	    		lovePetTypeCkg.setValue(data.likePetType);
	    		opForm.getForm().findField('lovePetTypeCkg').setValue(data.likePetType.split('|')[1]);
	    		
	    		loveCollectTypeCkg.setValue(data.likeCollectionType);
	    		opForm.getForm().findField('loveCollectTypeCkg').setValue(data.likeCollectionType.split('|')[1]);
	    		
	    		loveInvestTypeCkg.setValue(data.likeInvestType);
	    		opForm.getForm().findField('loveInvestTypeCkg').setValue(data.likeInvestType.split('|')[1]);
	    		
	    		loveRandTypeCkg.setValue(data.likeBrandType);
	    		
	    		loveFinaServTypeCkg.setValue(data.finaServ);
	    		opForm.getForm().findField('loveFinaServTypeCkg').setValue(data.finaServ.split('|')[1]);
	    		
	    		loveContactTypeCkg.setValue(data.contactType);
	    		opForm.getForm().findField('loveContactTypeCkg').setValue(data.contactType.split('|')[1]);
	    		
	    		loveFinaNewsTypeCkg.setValue(data.finaNews);
	    		opForm.getForm().findField('loveFinaNewsTypeCkg').setValue(data.finaNews.split('|')[1]);
	    		
	    		loveSalonTypeCkg.setValue(data.salon);
	    		opForm.getForm().findField('loveSalonTypeCkg').setValue(data.salon.split('|')[1]);
	    		
	    		loveInterestsTypeCkg.setValue(data.interests);
	    		opForm.getForm().findField('loveInterestsTypeCkg').setValue(data.interests.split('|')[1]);
	    		
	    		loveConTimeTypeCkg.setValue(data.likeContactTime);
	    		
	    		loveInvestCycleTypeCkg.setValue(data.likeInvestCycle);
	    		}
    	}else{
    		//解决本身没有家庭信息时，系统新增时，设置custId
//    		opForm.getForm().findField('custId').setValue(custId);
    	}
	}
};

var tabs = new Ext.Panel( {
	layout : 'form',
	autoScroll : true,
	items : [opForm]
});

var fuc = function() {
	var flag = JsContext.checkGrant('hobby_saveHobby');//默认false 
	if(flag){
		loveChannelCkg.setDisabled(true);
		opForm.getForm().findField('loveChannelCkg').setDisabled(true);
		
		loveBusiTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveBusiTypeCkg').setDisabled(true);
		
		loveLeisureTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveLeisureTypeCkg').setDisabled(true);
		
		loveMediaTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveMediaTypeCkg').setDisabled(true);
		
		loveSportTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveSportTypeCkg').setDisabled(true);
		
		loveMagazineTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveMagazineTypeCkg').setDisabled(true);
		
		loveMovieTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveMovieTypeCkg').setDisabled(true);
		
		lovePetTypeCkg.setDisabled(true);
		opForm.getForm().findField('lovePetTypeCkg').setDisabled(true);
		
		loveCollectTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveCollectTypeCkg').setDisabled(true);
		
		loveInvestTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveInvestTypeCkg').setDisabled(true);
		
		loveRandTypeCkg.setDisabled(true);
		
		loveFinaServTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveFinaServTypeCkg').setDisabled(true);
		
		loveContactTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveContactTypeCkg').setDisabled(true);
		
		loveFinaNewsTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveFinaNewsTypeCkg').setDisabled(true);
		
		opForm.getForm().findField('likeBrandText').setDisabled(true);
		opForm.getForm().findField('custReligion').setDisabled(true);
		opForm.getForm().findField('avoid').setDisabled(true);
		opForm.getForm().findField('custTaboo').setDisabled(true);
		opForm.getForm().findField('other').setDisabled(true);
		
		loveSalonTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveSalonTypeCkg').setDisabled(true);
		
		loveInterestsTypeCkg.setDisabled(true);
		opForm.getForm().findField('loveInterestsTypeCkg').setDisabled(true);
		
		loveConTimeTypeCkg.setDisabled(true);
		
		loveInvestCycleTypeCkg.setDisabled(true);
	}
}
//展现页面
var viewport = new Ext.Viewport({
layout : 'fit',
items:[tabs]
});

});
