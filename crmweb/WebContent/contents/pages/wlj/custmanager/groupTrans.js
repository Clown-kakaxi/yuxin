/**
 * @description 集团GAO移交
 * @author luyy
 * @since 2014-07-09
 */
	imports([
			        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
			        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'
			        ]);
	
var url = basepath+'/groupTrans.json?type=1';

var createView = false;
var editView = false;
var detailView = false;

var fields = [{name:'ID',text:'ID',hidden:true},
              {name:'GROUP_NAME',text:'集团名称',dataType:'string',searchField:true},
              {name:'GROUP_NAME_MAIN',text:'主申请(集团)名称',dataType:'string',searchField : true},
              {name:'GAO',hidden:true},
              {name:'USER_NAME',text:'集团GAO',dataType:'string'},
              {name:'GAO_ORG',hidden:true},
              {name:'ORG_NAME',text:'集团所属机构',dataType:'string'},
              {name:'CREATE_USER_NAME',text:'创建人',dataType:'string'},
              {name:'CREATA_DATE',text:'创建日期',dataType:'date'}
			];


var customerView = [{
	title:'移交GAO',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount :2,
		fields : [{name:'GROUP_ID',text:'',hidden:true},
		          {name:'GROUP_NAME',text:'集团客户名称'},
		          {name:'GAO_OLD',text:'',hidden:true},
		          {name:'GAO_ORG_OLD',text:'',hidden:true},
		          {name:'GAO_NEW_NAME',text:'<font color=red>*</font>新集团GAO',xtype:'userchoose',hiddenName:'GAO_NEW',singleSelect: true,callback:function(b){
		        	  getCurrentView().contentPanel.form.findField("GAO_ORG_NEW").setValue(b[0].data.orgId);
		        	  getCurrentView().contentPanel.form.findField("GAO_ORG_NEW_NAME").setValue(b[0].data.orgName);
		          }},{name:'GAO_ORG_NEW',text:''},
		          {name:'GAO_ORG_NEW_NAME',text:'新归属机构'}],
		fn : function(GROUP_ID,GROUP_NAME,GAO_NEW_NAME,GAO_OLD,GAO_ORG_OLD,GAO_ORG_NEW,GAO_ORG_NEW_NAME){
			GAO_ORG_NEW_NAME.readOnly = true;
			GAO_ORG_NEW_NAME.cls = 'x-readOnly';
			return [GROUP_NAME,GAO_NEW_NAME,GAO_ORG_NEW_NAME,GAO_OLD,GROUP_ID,GAO_ORG_OLD,GAO_ORG_NEW];
		}
	},{
		columnCount :1,
		fields : [{name:'TRANS_REASON',text:'调整原因',xtype:'textarea'}],
		fn : function(TRANS_REASON){
			return [TRANS_REASON];
		}
	}],
	formButtons:[{text:'确定',
		fn : function(formPanel,basicForm){
			if(formPanel.getForm().getFieldValues().GAO_NEW == ''){
				Ext.Msg.alert('提示','请选择新的集团GAO');
				return false;
			}
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			 Ext.Msg.wait('正在处理，请稍后......','系统提示');
			  Ext.Ajax.request({
					url : basepath + '/groupTrans!save.json',
					method : 'POST',
					params : commintData,
					success : function(r) {
						Ext.Msg.alert('提示','操作成功！');
						reloadCurrentData();
					}
				});
		}
		}]
},{
	title:'查看移交记录',
	type:'grid',
	pageable:true,
	url : basepath + '/groupTrans.json?type=2',
	fields : {
		fields : [{name : 'GROUP_NAME',text : '集团客户名称'},
		{name : 'GAO_OLD_NAME',text : '原GAO'},
		{name : 'GAO_NEW_NAME',text : '新GAO'},
		{name : 'GAO_ORG_OLD_NAME',text : '原归属机构'},
		{name : 'GAO_ORG_NEW_NAME',text : '新归属机构'},
		{name : 'USER_NAME',text : '调整人名称'},
		{name : 'TRANS_DATE',text : '调整日期'},
		{name : 'TRANS_REASON',text : '调整原因'}],
		fn : function(GROUP_NAME,GAO_OLD_NAME,GAO_NEW_NAME,GAO_ORG_OLD_NAME,GAO_ORG_NEW_NAME,USER_NAME,TRANS_DATE,TRANS_REASON){
			return [GROUP_NAME,GAO_OLD_NAME,GAO_NEW_NAME,GAO_ORG_OLD_NAME,GAO_ORG_NEW_NAME,USER_NAME,TRANS_DATE,TRANS_REASON];
		}
	}
}];


var beforeviewshow = function(view){
		if(view._defaultTitle == '移交GAO'&&getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}else if(view._defaultTitle == '查看移交记录'){
			view.setParameters (); 
		}
};

var viewshow = function(view){
	if(view._defaultTitle == '移交GAO'){
		view.contentPanel.getForm().findField('GROUP_ID').setValue(getSelectedData().data.ID);
		view.contentPanel.getForm().findField('GAO_OLD').setValue(getSelectedData().data.GAO);
		view.contentPanel.getForm().findField('GAO_ORG_OLD').setValue(getSelectedData().data.GAO_ORG);
	}
};