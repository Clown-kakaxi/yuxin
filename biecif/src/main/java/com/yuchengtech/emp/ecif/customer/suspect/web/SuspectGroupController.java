/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.suspect.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ExportUtil;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectGroup;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectList;
import com.yuchengtech.emp.ecif.customer.exportdata.service.ExportReportBS;
import com.yuchengtech.emp.ecif.customer.importdata.service.ImportListBS;
import com.yuchengtech.emp.ecif.customer.suspect.service.SuspectGroupBS;
import com.yuchengtech.emp.ecif.customer.suspect.web.vo.SuspectCustVO;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/customer/suspectgroup")
public class SuspectGroupController extends BaseController {

	protected static Logger log = LoggerFactory
			.getLogger(SuspectGroupController.class);

	@Autowired
	private SuspectGroupBS suspectGroupBS;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private ImportListBS importListBS;
	
	@Autowired
	private ExportReportBS exportReportBS;
	
	//一次进入不查询
	private boolean flag = false;
	
	private Map<String, String> codeMapComfirmType = Maps.newHashMap();//确认标识
	private Map<String, String> codeMapMergeType = Maps.newHashMap();//合并标识
	private Map<String, String> codeMapSuspectType = Maps.newHashMap();//疑似标识
	private Map<String, String> codeMapSCustType = Maps.newHashMap();//客户类型标识
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		flag = false;
		return "/ecif/customer/suspect/suspectcust-index";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		if(flag == false){
			flag = true;
			return null;
		}
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<SuspectCustVO> searchResult = suspectGroupBS.getSuspectGroupList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		
		List<SuspectCustVO> voList = searchResult.getResult();
		for(SuspectCustVO vo : voList){
			//替换列入原因的码值
			String enterReason = vo.getEnterReason();			
			vo.setEnterReason(findFlag(enterReason));
		}
		//searchResult.setResult(voList);		
		userMap.put("Rows", voList);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	public String findFlag(String target){
		//疑似规则
		String result = "";
		//标志位
		if(target == null || target.equals("") || target.length() != 7){
			target = "0000000";//疑似七个规则
		}
		char[] temp = target.toCharArray();
		if(temp[0] == '1'){
			result += "规则1.";
		}
		if(temp[1] == '1'){
			result += "规则2.";
		}
		if(temp[2] == '1'){
			result += "规则3.";
		}
		if(temp[3] == '1'){
			result += "规则4.";
		}
		if(temp[4] == '1'){
			result += "规则5.";
		}
		if(temp[5] == '1'){
			result += "规则6.";
		}
		if(temp[6] == '1'){
			result += "规则7.";
		}
		return result;		
	}
	
	/**
	 * 获取combobox疑似确认标志
	 */
	@ResponseBody
	@RequestMapping("getComBoBoxCustType.*")
	public List<Map<String,String>> getComBoBoxCustType() {			
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.CUST_ORG_TYPE);
		harvMap.put("text", "对公");
		harvComboList.add(harvMap);
		harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.CUST_PERSON_TYPE);
		harvMap.put("text", "对私");
		harvComboList.add(harvMap);
		
		return harvComboList;
	}
	
	/**
	 * 获取combobox疑似确认标志
	 */
	@ResponseBody
	@RequestMapping("getComBoBoxComfirmType.*")
	public List<Map<String,String>> getComBoBoxComfirmType() {			
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.CUST_COMFIRM_0);
		harvMap.put("text", "未确认");
		harvComboList.add(harvMap);
		harvMap = Maps.newHashMap();
		harvMap.put("id", GlobalConstants.CUST_COMFIRM_1);
		harvMap.put("text", "确认");
		harvComboList.add(harvMap);
		
		return harvComboList;
	}
	
	/**
	 * 下载文件
	 * @throws Exception
	 */
	@RequestMapping(value = "export.*", method = RequestMethod.POST)
	public void export(HttpServletResponse response, String file)
			throws Exception {

		ExportUtil.download(response, new File(file), "application/vnd.ms-excel");
		ExportUtil.deleteFile(file);
	}
	
	/**
	 * 下载文件
	 * @param repo
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getReportFile")
	@ResponseBody
	public String getReportFile(String repo, String custNo, String identNo, String lastUpdateSys) {
		int report = Integer.parseInt(repo);
		return exportReportBS.export(report, custNo, identNo, lastUpdateSys, "", "", "");
	}
	
	/**
	 * 跳转到上传页面
	 */	
	@RequestMapping(value = "/importresult", method = RequestMethod.GET)
	public ModelAndView importResult() {
		return new ModelAndView("/ecif/customer/suspect/suspectgroup-upload");
	}
	
	/**
	 * 开始上传并且读取文件内容
	 * @param checkResultSort
	 * @param uploader
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/startUpload")
	@ResponseBody
	public String startUpload(Uploader uploader, HttpServletResponse response) throws Exception {
		File file = null;
		try {
			file = this.uploadFile(uploader, GlobalConstants.EXCEL_IMPORT_FOLDER, false);
		} catch (Exception e) {
			logger.info("文件上传出现异常", e);
		}
		if (file != null) {
			logger.info("文件[" + file.getName() + "]上传完成");
			//importListBS.importSuspectGroup(file.getAbsolutePath(), 2);
			//this.readFile(file, checkResultSort);
			List<List<Object[]>> reportData = importListBS.importSuspectGroup(file.getPath(), 3);
			ExportUtil.deleteFile(file.getPath());
			if(reportData == null){
				return "没有导入数据";
			}else{
				return getResultByData(reportData);
			}
		}
		return "文件错误";
	}
	/**
	 * 导入数据处理
	 * @param reportData
	 * @return
	 */
	public String getResultByData(List<List<Object[]>> reportData){
		//
		List<List<Object[]>> errorData = new ArrayList<List<Object[]>>();
		//
		Map<String, String> codeMapComfirmType = Maps.newHashMap();//确认标识
		codeMapComfirmType.put("未确认", GlobalConstants.CUST_COMFIRM_0);
		codeMapComfirmType.put("确认", GlobalConstants.CUST_COMFIRM_1);
		//
		int result = 0;
		int count = 0;
		for(int i=0; i < reportData.size(); i++){
			List<Object[]> temp = reportData.get(i);
			List<Object[]> errorTemp = new ArrayList<Object[]>();
			if(temp == null){
				continue;
			}
			if(i == 0){//读取第一个sheet
				count = temp.size();
				//BiOneUser user = BiOneSecurityUtils.getCurrentUserInfo();
				//List<SpecialList> list = new ArrayList<SpecialList>();
				SuspectList sl = null;
				Object[] oTemp = null;
				for(Object[] o : temp){
					
					oTemp = Arrays.copyOf(o, o.length+1);
					//分组标识	客户编号 客户名称 	疑似客户分组描述	疑似信息数据日期	疑似信息生成日期	
					//疑似确认标志	疑似确认时间	疑似确认人	合并处理标志	合并处理日期 列入原因
					if(o[0] != null && !o[0].toString().equals("") &&
							o[1] != null && !o[1].toString().equals("") &&
							o[6] != null && !o[6].toString().equals("") &&
							o[7] != null && !o[7].toString().equals("") &&
							o[8] != null && !o[8].toString().equals("")){
						String comfirm = codeMapComfirmType.get(o[6].toString());
						if(StringUtils.isEmpty(comfirm)){
							//输入的确认标志无法对应码值
							oTemp[oTemp.length-1] = "输入的确认标志无法对应码值";
							errorTemp.add(oTemp);
							result++;
							continue;
						}
						sl = suspectGroupBS.getSuspectListByCustId(Long.valueOf(o[0].toString()), o[1].toString());
						if(sl != null){
							//SuspectGroup sg = suspectGroupBS.getEntityById(sl.getSuspectGroupId());
							String comfirmtemp = sl.getSuspectComfirmFlag() != null?sl.getSuspectComfirmFlag():"";
							if(!comfirmtemp.equals(GlobalConstants.CUST_COMFIRM_1)){
								suspectGroupBS.updateComfirmInfo(
										sl.getSuspectListId(), 
										comfirm,
										o[7].toString(),
										o[8].toString());
							}else{
								//已经确认过了
								oTemp[oTemp.length-1] = "该记录已经确认过了";
								errorTemp.add(oTemp);
								result++;
							}
						}else{
							//没有对应的疑似客户
							oTemp[oTemp.length-1] = "没有对应的疑似客户";
							errorTemp.add(oTemp);
							result++;
						}
					}else{
						//必填项有空值
						oTemp[oTemp.length-1] = "必填项有空值";
						errorTemp.add(oTemp);
						result++;
					}
				}
				errorData.add(errorTemp);
			}
		}
		//错误数据结果集 errorData
		String file = exportReportBS.export(6, errorData);
		//
		if(result == 0){
			return "成功" + count + "条记录";
		}else{
			//return "成功" + (count-result) + "条记录，错误" + result + "条";
			return "err" + file;
		}
	}
	/**
	 * 获取确认标识的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapComfirmType.*")
	public String getCodeMapComfirmType(String paramTypeNo, String paramValue){
		//
		if(codeMapComfirmType == null || codeMapComfirmType.isEmpty()){
			codeMapComfirmType = Maps.newHashMap();
			codeMapComfirmType.put(GlobalConstants.CUST_COMFIRM_0, "未确认");
			codeMapComfirmType.put(GlobalConstants.CUST_COMFIRM_1, "确认");
		}
		String result = "";
		if(codeMapComfirmType.get(paramValue)!= null && !codeMapComfirmType.get(paramValue).equals("")){
			result = codeMapComfirmType.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}	
	/**
	 * 获取合并标识的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapMergeType.*")
	public String getCodeMapMergeType(String paramTypeNo, String paramValue){
		//
		if(codeMapMergeType == null || codeMapMergeType.isEmpty()){
			codeMapMergeType = Maps.newHashMap();
			codeMapMergeType.put(GlobalConstants.CUST_MERGE_TYPE, "已合并");
			codeMapMergeType.put(GlobalConstants.CUST_SPLIT_TYPE, "已拆分");
		}
		String result = "未合并";
		if(codeMapMergeType.get(paramValue)!= null && !codeMapMergeType.get(paramValue).equals("")){
			result = codeMapMergeType.get(paramValue);
		}else{
			return result;
		}
		return result;
	}
	/**
	 * 获取疑似标识的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapSuspectType.*")
	public String getCodeMapSuspectType(String paramTypeNo, String paramValue){
		//
		if(codeMapSuspectType == null || codeMapSuspectType.isEmpty()){
			codeMapSuspectType = Maps.newHashMap();
			codeMapSuspectType.put(GlobalConstants.CUST_SUSPECT_0, "非疑似");
			codeMapSuspectType.put(GlobalConstants.CUST_SUSPECT_1, "疑似");
		}
		String result = "未合并";
		if(codeMapSuspectType.get(paramValue)!= null && !codeMapSuspectType.get(paramValue).equals("")){
			result = codeMapSuspectType.get(paramValue);
		}else{
			return result;
		}
		return result;
	}
	/**
	 * 获取疑似标识的码值
	 */
	@ResponseBody
	@RequestMapping("getCodeMapCustType.*")
	public String getCodeMapCustType(String paramTypeNo, String paramValue){
		//
		if(codeMapSCustType == null || codeMapSCustType.isEmpty()){
			codeMapSCustType = Maps.newHashMap();
			codeMapSCustType.put(GlobalConstants.CUST_PERSON_TYPE, "个人");
			codeMapSCustType.put(GlobalConstants.CUST_ORG_TYPE, "机构");
			codeMapSCustType.put(GlobalConstants.CUST_ORG_TYPE2, "同业");
		}
		String result = "";
		if(codeMapSCustType.get(paramValue)!= null && !codeMapSCustType.get(paramValue).equals("")){
			result = codeMapSCustType.get(paramValue);
		}else{
			return result;
		}
		return result;
	}
}
