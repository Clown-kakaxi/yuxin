package com.yuchengtech.emp.biappframe.logicsys.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthInfo;
import com.yuchengtech.emp.biappframe.authres.entity.BioneMenuInfo;
import com.yuchengtech.emp.biappframe.authres.service.MenuBS;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.common.LogicSysInfoHolder;
import com.yuchengtech.emp.biappframe.base.service.ObjectBS;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAdminUserInfo;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneAdminUserInfoPK;
import com.yuchengtech.emp.biappframe.logicsys.entity.BioneLogicSysInfo;
import com.yuchengtech.emp.biappframe.logicsys.service.AdminUserBS;
import com.yuchengtech.emp.biappframe.logicsys.service.AuthObjSysRelBS;
import com.yuchengtech.emp.biappframe.logicsys.service.AuthResSysRelBS;
import com.yuchengtech.emp.biappframe.logicsys.service.ExportEntityBS;
import com.yuchengtech.emp.biappframe.logicsys.service.LogicSysBS;
import com.yuchengtech.emp.biappframe.logicsys.web.vo.BioneLogicSysInfoVO;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTaskInfo;
import com.yuchengtech.emp.biappframe.schedule.service.TaskBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.entity.upload.Uploader;
import com.yuchengtech.emp.bione.util.CollectionsUtils;
import com.yuchengtech.emp.bione.util.DownloadUtils;
import com.yuchengtech.emp.bione.util.FilesUtils;
import com.yuchengtech.emp.bione.util.FormatUtils;
import com.yuchengtech.emp.bione.util.JsonUtils;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * 
 * <pre>
 * Title:逻辑系统管理
 * Description: 逻辑系统管理
 * </pre>
 * 
 * @author yunlei yunlei@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/logicSys")
public class LogicSysController extends BaseController {

	@Autowired
	private LogicSysBS logicSysBS;// 逻辑系统 操作类
	
	@Autowired
	private AdminUserBS adminUserBS; // 逻辑系统 管理员操作类
	
	@Autowired
	private MenuBS menuBS;// 菜单操作类
	
	@Autowired
	private AuthResSysRelBS authResSysRelBS;

	@Autowired
	private AuthObjSysRelBS authObjSysRelBS;
	
	@Autowired
	private ExportEntityBS exportEntityBS;		// 导出逻辑系统的类
	
	@Autowired
	private TaskBS taskBS;		// 任务服务类
	
	@Autowired
	private ObjectBS objectBS;		// 向数据库批量更新实体, 并且实体类型又不统一时的服务类 

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/logicSys/logic-sys-index";
	}

	// 跳转logic-manage.jsp
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/logicSys/logic-sys-editNew";
	}

	/*
	 * // 版权管理页面 public String copyRight() {
	 * this.getRequest().setAttribute("logicSysId", id); return "copyRight"; }
	 */

	/**
	 * 系统的关于信息
	 * 
	 * @return
	 */
	@RequestMapping("/about")
	@ResponseBody
	public ModelAndView about() {
		String logicSysNo = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		List<BioneLogicSysInfo> list = this.adminUserBS
				.getEntityListByProperty(BioneLogicSysInfo.class, "logicSysNo",
						logicSysNo);
		ModelMap mm = new ModelMap();
		if (!CollectionsUtils.isEmpty(list)) {
			BioneLogicSysInfo model = list.get(0);
			mm.put("systemVersion", model.getSystemVersion());
			mm.put("cnCopyright", model.getCnCopyright());
			mm.put("enCopyright", model.getEnCopyright());
		}
		return new ModelAndView("/index/about", mm);
	}

	@RequestMapping("/checkLogicSysNo")
	@ResponseBody
	public String checkLogicSysNo(String logicSysNo) {
		if (logicSysNo != null && !"".equals(logicSysNo)) {
			BioneLogicSysInfo logicSysInfo = logicSysBS
					.findUniqueEntityByProperty("logicSysNo", logicSysNo);
			if (logicSysInfo != null) {
				return "false";
			}
		}
		return "true";
	}

	// 保存新
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneLogicSysInfo model) {
		if (model.getOrderNo() == null || "".equals(model.getOrderNo())) {
			model.setOrderNo(logicSysBS.getMaxOrder());
		}
		model.setLogicSysSts("".equals(model.getLogicSysSts()) ? "1" : model
				.getLogicSysSts());
		model.setBasicDeptSts("".equals(model.getBasicDeptSts()) ? "1" : model
				.getBasicDeptSts());
		model.setBasicOrgSts("".equals(model.getBasicOrgSts()) ? "1" : model
				.getBasicOrgSts());
		model.setIsBuiltin(GlobalConstants.COMMON_STATUS_INVALID);
		model.setLastUpdateTime(new Timestamp(new Date().getTime()));
		model.setLastUpdateUser(BiOneSecurityUtils.getCurrentUserId());
		if(model.getLogicSysId()==null||model.getLogicSysId().equals("")){
			model.setLogicSysId(RandomUtils.uuid2());
		}
		// 保存
		logicSysBS.updateEntity(model);
		LogicSysInfoHolder.refreshLogicSysInfo();
	}

	/**
	 * 跳转 修改 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/logicSys/logic-sys-editNew", "id", id);
	}

	/**
	 * 修改。加载页面
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneLogicSysInfo show(@PathVariable("id") String id) {
		BioneLogicSysInfo model = logicSysBS.getEntityById(
				BioneLogicSysInfo.class, id);
		return model;
	}

	/**
	 * 新增时默认加载 BIONE 系统的版权信息
	 */
	@RequestMapping(value = "/getBioneLogicSys", method = RequestMethod.GET)
	@ResponseBody
	public BioneLogicSysInfo getBioneLogicSys() {
		BioneLogicSysInfo model = new BioneLogicSysInfo();
		List<BioneLogicSysInfo> list = this.adminUserBS
				.getEntityListByProperty(BioneLogicSysInfo.class, "logicSysNo",
						"BIONE");
		if (!CollectionsUtils.isEmpty(list)) {
			BioneLogicSysInfo model_ = list.get(0);
			model.setCnCopyright(model_.getCnCopyright());
			model.setEnCopyright(model_.getEnCopyright());
		}
		return model;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String destroy(@PathVariable("id") String id) {
		String[] idArr = id.split(",");
		logicSysBS.deleteBatch(idArr);
		LogicSysInfoHolder.refreshLogicSysInfo();
		return "true";
	}

	/**
	 * 显示页面列表
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager rf) {
		Map<String, Object> moduleMap = Maps.newHashMap();

		try {
			SearchResult<BioneLogicSysInfo> searchResult = logicSysBS
					.findResults(rf.getPageFirstIndex(), rf.getPagesize(),
							rf.getSortname(), rf.getSortorder(),
							rf.getSearchCondition());

			List<BioneLogicSysInfoVO> rows = new ArrayList<BioneLogicSysInfoVO>();

			List<BioneLogicSysInfo> logicSysList = searchResult.getResult();

			for (BioneLogicSysInfo logicSysTemp : logicSysList) {

				BioneLogicSysInfoVO logicSysVO = new BioneLogicSysInfoVO();

				BeanUtils.copyProperties(logicSysVO, logicSysTemp);

				List<BioneAuthInfo> authTypeList = logicSysBS
						.getEntityListByProperty(BioneAuthInfo.class,
								"authTypeNo", logicSysTemp.getAuthTypeNo());

				if (authTypeList.size() > 0) {
					logicSysVO.setAuthTypeName(authTypeList.get(0)
							.getAuthTypeName());
				} else {
					logicSysVO.setAuthTypeName(logicSysVO.getAuthTypeNo());
				}

				BioneUserInfo userInfo = logicSysBS.getEntityById(
						BioneUserInfo.class, logicSysTemp.getLastUpdateUser());
				if (userInfo != null) {
					logicSysVO.setLastUpdateUserName(userInfo.getUserName());
				}

				rows.add(logicSysVO);
			}
			moduleMap.put("Rows", rows);
			moduleMap.put("Total", searchResult.getTotalCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moduleMap;
	}

	/**
	 * 给逻辑系统添加管理员
	 * 
	 * @return
	 */
	@RequestMapping("/{id}/adminUser")
	public ModelAndView adminUser(@PathVariable("id") String id) {
		return new ModelAndView("/bione/logicSys/logic-sys-adminUser", "id", id);

	}

	/**
	 * 加载下拉图片选择
	 */
	// public void findImgForCombo() {
	// list = buildIconCombox("sysicons");
	// }

	/**
	 * 选择图片
	 * 
	 * @return
	 */
	// public DefaultHttpHeaders selectImages() {
	// this.buildIconSelectHTML("sysicons");
	// return new DefaultHttpHeaders("images").disableCaching();
	// }

	/**
	 * 获取用户列表
	 */
	@RequestMapping("/{id}/getUserList.*")
	@ResponseBody
	public List<CommonTreeNode> getUserList(@PathVariable("id") String id,
			String userName) {
		Map<String, String> params = new HashMap<String, String>();
		if (userName != null) {
			params.put("userName", userName);
		}
		return logicSysBS.userToTree(logicSysBS.getUserList(id, params));
	}

	/**
	 * 获取逻辑系统的管理信息
	 */
	@RequestMapping("/{id}/getAdminUserList.*")
	@ResponseBody
	public List<CommonTreeNode> getAdminUserList(@PathVariable("id") String id) {
		return logicSysBS.userToTree(logicSysBS.getAdminList(id));
	}

	/**
	 * 保存管理员
	 */
	@RequestMapping("/saveAdmin")
	public void saveAdmin(String id, String params) {
		String[] userIds = params.split(";");
		List<BioneAdminUserInfo> adminList = new ArrayList<BioneAdminUserInfo>();
		for (String userId : userIds) {
			if (!"".equals(userId)) {
				BioneAdminUserInfo adminUser = new BioneAdminUserInfo();
				BioneAdminUserInfoPK adminUserInfoPK = new BioneAdminUserInfoPK();
				adminUserInfoPK.setLogicSysId(id);
				adminUserInfoPK.setUserId(userId);
				adminUser.setId(adminUserInfoPK);
				adminUser.setRemark("");
				adminUser.setUserSts("1");
				adminUser
						.setLastUpdateTime(new Timestamp(new Date().getTime()));
				adminUser.setLastUpdateUser(BiOneSecurityUtils
						.getCurrentUserId());
				adminList.add(adminUser);
			}
		}
		adminUserBS.saveLogicSysAdmin(id, adminList);
	}

	/**
	 * 跳转 添加菜单页面
	 * 
	 * @return
	 */
	@RequestMapping("/addMenu")
	public ModelAndView addMenu(String logicSysNo) {
		return new ModelAndView("/bione/logicSys/logic-sys-menu", "logicSysNo",
				logicSysNo);
	}

	/**
	 * 获取功能列表
	 */
	@RequestMapping("/getFuncList.*")
	public Map<String, Object> getFuncList() {
		String funcName = getRequest().getParameter("funcName");
		List<?> resultList = new ArrayList<Object>();
		if (!"".equals(funcName) && funcName != null) {
			resultList = logicSysBS.searchNodes(funcName);
		} else {
			resultList = logicSysBS.funcToTree(null);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("nodes", resultList);
		return resultMap;
	}

	/**
	 * 根据逻辑系统标示获取功能列表
	 */
	@RequestMapping("/getMenuToTree.*")
	public Map<String, Object> getMenuToTree(String logicSysNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonTreeNode> resultList = new ArrayList<CommonTreeNode>();
		// resultList.add(logicSysBS.getMenuRoot());
		List<CommonTreeNode> indexList = logicSysBS.funcToTree(
				logicSysBS.indexToTree(logicSysNo), true);
		if (indexList.size() == 1) {
			resultMap.put("indexPage", indexList.get(0).getId());
			resultList.add(indexList.get(0));
		}
		resultList.addAll(logicSysBS.funcToTree(logicSysBS
				.getMenuByLogicSysNo(logicSysNo)));
		resultMap.put("nodes", resultList);
		return resultMap;
	}

	/**
	 * 保存菜单相关配置
	 */
	@RequestMapping("/saveMenu")
	public void saveMenu(String logicSysNo, String params,String indexPageId) {
		String[] funcIdAndUpIds = params.split(";");
		
		Map<String,List<BioneMenuInfo>> menuMap = new HashMap<String,List<BioneMenuInfo>>();
		for (String funcIdAndUpId : funcIdAndUpIds) {
			if (!"".equals(funcIdAndUpId)) {
				
				String[] funcIdAndUpIdArr = funcIdAndUpId.split(":");
				String funcId = funcIdAndUpIdArr[0];
				String upId = funcIdAndUpIdArr[1];
				
				BioneMenuInfo menuInfo = new BioneMenuInfo();
				
				menuInfo.setFuncId(funcId);
				menuInfo.setLogicSysNo(logicSysNo);
				menuInfo.setUpId(upId);
				
				if (funcId.equals(indexPageId)) {
					menuInfo.setIndexSts(GlobalConstants.COMMON_STATUS_VALID);
				} else {
					menuInfo.setIndexSts(GlobalConstants.COMMON_STATUS_INVALID);
				}
				List<BioneMenuInfo> menuList = menuMap.get(upId);
				if(menuList == null){
					menuList = new ArrayList<BioneMenuInfo>();
					menuList.add(menuInfo);
					menuMap.put(upId, menuList);
				}else{
					menuList.add(menuInfo);
				}
			}
		}
		menuBS.saveMenuList(logicSysNo, menuMap);
	}

	/**
	 * 授权资源
	 */
	@RequestMapping("/authRes")
	public ModelAndView authRes(String logicSysNo) {
		return new ModelAndView("/bione/logicSys/logic-sys-authRes",
				"logicSysNo", logicSysNo);
	}

	/**
	 * 获取资源定义
	 */
	@RequestMapping("/getResList.*")
	@ResponseBody
	public List<CommonTreeNode> getResList(String logicSysNo) {
		return logicSysBS.AuthResToTree(logicSysBS.getAuthRes(logicSysNo));
	}

	/**
	 * 获取逻辑系统已经授权的资源
	 */
	@RequestMapping("/getAuthResList.*")
	@ResponseBody
	public List<CommonTreeNode> getAuthResList(String logicSysNo) {
		return logicSysBS.AuthResToTree(logicSysBS
				.getAuthResByLogicSysNo(logicSysNo));
	}

	/**
	 * 检查节点是否被引用
	 */
	@RequestMapping("/checkRes")
	@ResponseBody
	public boolean checkRes(String nodeRealId, String logicSysNo) {
		return logicSysBS.checkRes(logicSysNo, nodeRealId);

	}

	/**
	 * 保存授权资源
	 */
	@RequestMapping("/saveAuthRes")
	public void saveAuthRes(String logicSysNo, String params) {
		String[] authResIds = params.split(";");
		authResSysRelBS.saveAuthRes(logicSysNo, authResIds);
	}

	/**
	 * 跳转授权对象
	 * 
	 * @return
	 */
	@RequestMapping("/authObj")
	public ModelAndView authObj(String logicSysNo) {
		return new ModelAndView("/bione/logicSys/logic-sys-authObj",
				"logicSysNo", logicSysNo);
	}

	/**
	 * 获取授权对象定义
	 */
	@RequestMapping("/getObjList.*")
	@ResponseBody
	public List<CommonTreeNode> getObjList(String logicSysNo) {
		return logicSysBS.AuthObjToTree(logicSysBS.getAuthObj(logicSysNo));
	}

	/**
	 * 检查节点是否被引用
	 */
	@RequestMapping("/checkObj")
	@ResponseBody
	public boolean checkObj(String logicSysNo, String nodeRealId) {
		return logicSysBS.checkObj(logicSysNo, nodeRealId);
	}

	/**
	 * 获取逻辑系统已经授权对象
	 */
	@RequestMapping("/getAuthObjList.*")
	@ResponseBody
	public List<CommonTreeNode> getAuthObjList(String logicSysNo) {
		return logicSysBS.AuthObjToTree(logicSysBS
				.getAuthObjByLogicSysNo(logicSysNo));
	}

	/**
	 * 保存授权对象
	 */
	@RequestMapping("/saveAuthObj")
	@ResponseBody
	public String saveAuthObj(String logicSysNo, String params) {
		String[] authObjIds = params.split(";");
		authObjSysRelBS.saveAuthObj(logicSysNo, authObjIds);
		return "true";
	}

	/**
	 * 进行上传时的文件接收与保存
	 */
	@RequestMapping("/startUpload")
	@ResponseBody
	public String startUpload(Uploader uploader, HttpServletResponse response) throws Exception {
		File file = null;
		try {
			file = this.uploadFile(uploader, GlobalConstants.LOGIC_SYS_IMPORT_PATH, false);
		} catch (Exception e) {
			logger.info("文件上传出现异常", e);
		}
		if (file != null) {
			logger.info("文件[" + file.getName() + "]上传完成");
			String content = unzipFile(file);
			String message = updateDataWithJson(content);
			return message;
		}
		return null;
	}

	/**
	 * 导入逻辑系统信息
	 * 
	 * @return
	 */
	@RequestMapping("/importsys")
	public String importsys() {
		return "/bione/logicSys/logic-sys-upload";
	}

	/**
	 * 导出逻辑系统信息
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/exportsys")
	public void exportsys(HttpServletResponse response, String logicSysNo) throws Exception {
		if (StringUtils.isNotEmpty(logicSysNo)) {

			// 所有需要导出的实体全路径名
			List<String> entitiesName = exportEntityBS.getExportEntitiesAll();
			
			// 获取所有包含逻辑系统信息的数据
			Map<String, Map<String, List<Object>>> result = this.adminUserBS.getObjectListWidthLogicSysNo(entitiesName, logicSysNo);
			JsonUtils mapper = JsonUtils.nonDefaultMapper();
			String jsonString = mapper.toJson(result); // 生成 json 数据
			String path = this.getRealPath() + GlobalConstants.LOGIC_SYS_EXPORT_PATH; // 下载临时路径
			String fileName = logicSysNo + "_" + FormatUtils.formatDate(new Date(), "yyyyMMddHHmmss"); // 文件名
			if (FilesUtils.createDir(path)) { // 创建下载临时路径
				File file = new File(path + File.separator + fileName + ".backUp"); // 生成数据文件描述
				PrintWriter pw = null;
				try {
					if (file.createNewFile()) { // 创建数据文件
						pw = new PrintWriter(file);
						pw.write(jsonString);
						pw.flush(); // 写入信息
					}
				} finally {
					if (pw != null) {
						pw.close();
					}
				}
				if (file.exists()) {

					// 将文件进行 zip 压缩处理
					FilesUtils.zip(path + File.separator + fileName + ".backUp", path + File.separator + fileName + ".zip");
					FilesUtils.deleteFile(file); // 压缩后, 将原文件删除
					file = new File(path + File.separator + fileName + ".zip"); // 生成压缩文件描述
					DownloadUtils.download(response, file); // 提供下载
					file.delete(); // 删除压缩文件
				}
			}
		}
	}
	
	/*
	 * 解压缩文件
	 *
	 * @param file
	 * @throws Exception
	 */
	private String unzipFile(File file) throws Exception {
		List<File> files = FilesUtils.unzip(file, file.getParent() + "/"); // 解压缩
		file.delete(); // 删除压缩文件
		if (files.size() > 0) {
			int BUFFER_SIZE = 10240;	// 每次读取字符数
			file = files.get(0);
			BufferedReader br = new BufferedReader(new FileReader(file), BUFFER_SIZE);
			StringBuffer content = new StringBuffer();
			int len = 0;
			char[] buffer = new char[BUFFER_SIZE];
			while ((len = br.read(buffer, 0, BUFFER_SIZE)) > 0) { // 读取解压后文件内容
				content.append(String.valueOf(buffer, 0, len));
			}
			br.close();
			file.delete(); // 删除解压后文件
			return content.toString();
//			return updateDataWithJson(content.toString()); // 更新数据
		}
		return null;
	}

	/*
	 * 更新数据
	 * 
	 * @param file
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String updateDataWithJson(String content) {
		
		if (StringUtils.isEmpty(content)) return null;

		Set<String> res = Sets.newHashSet();

		JSONObject jsonObject = JSONObject.fromObject(content);

		List<Object> list = new ArrayList<Object>();

		// 对 json 的最外层, 即逻辑系统信息生成迭代器
		Iterator<String> iter = (Iterator<String>) jsonObject.keySet().iterator();
		while (iter.hasNext()) {

			// 判断是否存在当前的逻辑系统
			String logicSysNo = iter.next();
			if (isExist(logicSysNo)) {
				res.add(logicSysNo);
				continue;
			}

			// 对 json 的最外层, 即逻辑系统信息进行迭代
			JSONObject jsonObj = jsonObject.getJSONObject(logicSysNo);

			// 对实体名称一层生成迭代器
			Iterator<String> it = (Iterator<String>) jsonObj.keySet().iterator();
			while (it.hasNext()) {

				// 对实体名称进行迭代
				String entitiesName = it.next();
				String entityName = entitiesName;
				JSONArray jsonArray = jsonObj.getJSONArray(entitiesName);

				// 对实体数据数组生成迭代器
				Iterator<?> itJsonArray = jsonArray.iterator();
				Object obj = null;
				if (itJsonArray.hasNext()) {
					try {
						obj = Class.forName(entityName).newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					// 实体实例化是否成功
					if (obj != null) {

						// 实体实例化成功, 则用该实体的类型继续解析数据 obj.getClass()
						while (itJsonArray.hasNext()) {

							// 对实体数据数组进行迭代
							JSONObject arrayObj = (JSONObject) itJsonArray.next();

							JsonUtils mapper = JsonUtils.nonDefaultMapper();
							if ("com.ytec.bione.admin.entity.BioneTaskInfo".equals(entitiesName)) {
								this.taskBS.updateJob((BioneTaskInfo) mapper.fromJson(arrayObj.toString(), obj.getClass()));
							} else {
								Object object = mapper.fromJson(arrayObj.toString(), obj.getClass());
								list.add(object);
							}
						}
					}

				}
			}
		}
		// 系统中不存在当前的逻辑系统则允许导入
		if (CollectionsUtils.isEmpty(res)) {
			this.objectBS.updateDataWithImport(list);
			return null;
		} else {
			// 退出运行, 并向前台发出“已存在, 不能导入信息”
			return StringUtils.join(res, ", ");
//			this.renderText(StringUtils.join(res, ", "), response);
		}
	}

	/**
	 * 判断是否已存在该逻辑系统的记录
	 * 
	 * @param logicSysNo
	 * @return
	 */
	private boolean isExist(String logicSysNo) {
		List<BioneLogicSysInfo> list = this.adminUserBS
				.getEntityListByProperty(BioneLogicSysInfo.class, "logicSysNo", logicSysNo);
		return CollectionsUtils.isEmpty(list) ? false : true;
	}

}
