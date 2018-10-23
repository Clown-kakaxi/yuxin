/**
 * 
* @Description: 集团成员列表
* @author wangmk1 
* @date 2014-7-21
*
 */
//_busiId集团客户编号
var id=_busiId;
var url=basepath+"/groupCustMenberList.json?id="+id+"";
imports([
	 '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
   ]);
var needCondition = true;
var createView = false;
var editView = false;
var detailView = false;
var fields=[
            {name:'CUST_ID',text:'客户编号',resutlWidth:80,searchField:true},
            {name:'ID',text:'主键',resutlWidth:80,hidden :true},
            {name:'CUST_NAME',text:'客户名称',resutlWidth:80},
            {name:'CROP_CODE',text:'组织机构代码',resutlWidth:80},
            {name:'CORP_NAME_UP',text:'上级企业名称',resutlWidth:80},
            {name:'CUST_STAT',text:'客户状态',resutlWidth:80},
            {name:'INDUSTRY',text:'行业',resutlWidth:80},
            {name:'CUST_SCALE',text:'客户规模',resutlWidth:80},
            {name:'CUST_SCALE_CHECK',text:'考核口径客户规模',resutlWidth:80},
            {name:'TAX_CERT_NO',text:'税务证号',resutlWidth:80},
            {name:'LICENSE_NO',text:'营业执照号',resutlWidth:80},
            {name:'RELATIONSHIP_UP',text:'与上级企业关系',resutlWidth:80},
            {name:'MEMBER_TYPE',text:'成员类型',resutlWidth:80},        
            {name:'STOCK_RATE',text:'持股比例',resutlWidth:100},
            {name:'REMARK',text:'备注',resutlWidth:130},
            {name:'GROUP_NO',text:'所属集团编号',resutlWidth:80}           
            ];
var tbar = [new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    url : basepath+"/groupCustMenberList.json?id="+id+""
})];