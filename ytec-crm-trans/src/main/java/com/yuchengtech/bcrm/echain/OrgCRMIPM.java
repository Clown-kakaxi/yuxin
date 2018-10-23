package com.yuchengtech.bcrm.echain;

import java.security.Key;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import org.apache.commons.net.util.Base64;
import com.ecc.echain.db.DbControl;
import com.ecc.echain.log.WfLog;
import com.ecc.echain.org.OrgIF;
import com.ecc.echain.org.model.DepModel;
import com.ecc.echain.org.model.GroupModel;
import com.ecc.echain.org.model.OrgModel;
import com.ecc.echain.org.model.RoleModel;
import com.ecc.echain.org.model.UserModel;
import com.ecc.echain.workflow.cache.OUCache;

public class OrgCRMIPM implements OrgIF{

	public OrgModel getRootOrg(Connection con)
	  {
	    return OUCache.getInstance().rootOrgModel;
	  }

	public List getAllBaseOrgs(Connection con)
	  {
	    return OUCache.getInstance().rootOrgModel.getOrgList();
	  }

	public List getDirectSubOrgs(String orgid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return new ArrayList();
	    return om.getOrgList();
	  }

	public List getAllSubOrgs(String orgid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return new ArrayList();
	    return om.getAllorgList();
	  }

	public OrgModel getParentOrg(String orgid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return null;
	    return (OrgModel)OUCache.getInstance().hmOMCache.get(om.getSuporgid());
	  }

	public OrgModel getOrgModel(String orgid, Connection con)
	  {
	    return (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	  }

	public List getAllOrgs(Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmOMCache.values());
	    return list;
	  }

	public List getDirectDepsByOrg(String orgid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return new ArrayList();
	    return om.getDepList();
	  }

	public List getAllDepsByOrg(String orgid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return new ArrayList();
	    return om.getAlldepList();
	  }

	public List getDirectSubDepsByDep(String orgid, String depid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return new ArrayList();
	    return om.getDepModel(depid).getDepList();
	  }

	public List getAllSubDepsByDep(String orgid, String depid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return new ArrayList();
	    return om.getDepModel(depid).getAlldepList();
	  }

	public DepModel getParentDep(String orgid, String depid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return null;
	    return om.getDepModel(om.getDepModel(depid).getSupdepid());
	  }

	public DepModel getDepModel(String orgid, String depid, Connection con)
	  {
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om == null)
	      return null;
	    return om.getDepModel(depid);
	  }

	public List getDirectUsersByOrg(String orgid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.account_name,t1.user_name,t1.user_state,t1.email,t1.mobilephone from admin_auth_account t1 where t1.org_id='" + 
	        orgid + "' ", con);
	      for (int i = 0; i < vecData.size(); i++) {
	        Vector vecRow = (Vector)vecData.elementAt(i);
	        UserModel um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "-1" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid(orgid);
	        um.setDepid(null);
	        list.add(um);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常"); 
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	public List getAllUsersByOrg(String orgid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.account_name,t1.user_name,t1.user_state,t1.email,t1.mobilephone,t1.org_id from admin_auth_account t1  where t1.org_id='" + 
	        orgid + "' ", con);
	      for (int i = 0; i < vecData.size(); i++) {
	        Vector vecRow = (Vector)vecData.elementAt(i);
	        UserModel um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid(orgid);
	        um.setDepid((String)vecRow.elementAt(5));
	        list.add(um);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	  public List getDirectUsersByDep(String orgid, String depid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.account_name,t1.user_name,t1.user_state,t1.email,t1.mobilephone from admin_auth_account t1   where t1.org_id='" + 
	        depid + "' ", con);
	      for (int i = 0; i < vecData.size(); i++) {
	        Vector vecRow = (Vector)vecData.elementAt(i);
	        UserModel um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid(orgid);
	        um.setDepid(depid);
	        list.add(um);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	  public List getAllUsersByDep(String orgid, String depid, Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(getDirectUsersByDep(orgid, depid, con));
	    DepModel dm = getDepModel(orgid, depid, con);
	    for (int i = 0; i < dm.getAlldepList().size(); i++) {
	      list.addAll(getDirectUsersByDep(orgid, ((DepModel)dm.getAlldepList().get(i)).getDepid(), con));
	    }
	    return list;
	  }

	  public UserModel getUserModel(String orgid, String userid, Connection con)
	  {
	    UserModel um = null;
	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.account_name,t1.user_name,t1.user_state,t1.email,t1.mobilephone,t1.org_id from admin_auth_account t1   where t1.org_id='" + 
	        orgid + "' and t1.account_name='" + userid + "'", con);
	      if ((vecData != null) && (vecData.size() > 0)) {
	        Vector vecRow = (Vector)vecData.elementAt(0);
	        um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid(orgid);
	        um.setDepid((String)vecRow.elementAt(5));
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return um;
	  }
	  public UserModel getUserModel(String userid, Connection con) {
	    UserModel um = null;
	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.account_name,t1.user_name,t1.user_state,t1.email,t1.mobilephone,t1.org_id,t1.org_id from admin_auth_account t1   where t1.account_name='" + 
	        userid + "'", con);
	      if ((vecData != null) && (vecData.size() > 0)) {
	        Vector vecRow = (Vector)vecData.elementAt(0);
	        um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid((String)vecRow.elementAt(5));
	        um.setDepid((String)vecRow.elementAt(6));
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return um;
	  }

	  public String getOrgIdByUser(String userid, Connection con)
	  {
	    String orgid = null;
	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      String[] useridString = userid.split(";");
	      Vector vecData = db.performQuery("select t1.org_id from  admin_auth_account t1  where t1.account_name='" + useridString[0] + "'", con);
	      if ((vecData != null) && (vecData.size() > 0)) {
	        Vector vecRow = (Vector)vecData.elementAt(0);
	        orgid = (String)vecRow.elementAt(0);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return orgid;
	  }

	  public UserModel isValidUser(String orgid, String userid, String password, Connection con)
	  {
	    try
	    {
	      byte[] keyBytes = Base64.decodeBase64("lianame");
	      KeyGenerator generator = KeyGenerator.getInstance("DES");
	      generator.init(new SecureRandom(keyBytes));
	      Key key = generator.generateKey();
	      Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	      cipher.init(1, key);
	      byte[] encryptBytes = cipher.doFinal(password.getBytes("UTF-8"));

	      password = Base64.encodeBase64(encryptBytes).toString();
	    } catch (Exception e) {
	      WfLog.log(4, "isValidUser密码加密算法执行异常");
	      e.printStackTrace();
	    }
	    UserModel um = null;
	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.account_name,t1.user_name,t1.user_state,t1.email,t1.mobilephone,t1.org_id,t1.org_id from admin_auth_account t1  where t1.account_name='" + 
	        userid + "' and t1.password='" + password + "' and t1.org_id='" + orgid + "'", con);
	      if ((vecData != null) && (vecData.size() > 0)) {
	        Vector vecRow = (Vector)vecData.elementAt(0);
	        um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid((String)vecRow.elementAt(5));
	        um.setDepid((String)vecRow.elementAt(6));
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return um;
	  }

	  public List getOrgLeaders(String orgid, Connection con)
	  {
	    return null;
	  }

	  public List getOrgDirectors(String orgid, Connection con)
	  {
	    return null;
	  }

	  public List getDepLeaders(String orgid, String depid, Connection con)
	  {
	    return null;
	  }

	  public List getDepDirectors(String orgid, String depid, Connection con)
	  {
	    return null;
	  }

	  public String getDepIdByUser(String userid, Connection con)
	  {
	    String depid = null;
	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select t1.org_id from  admin_auth_account t1  where t1.account_name='" + userid + "'", con);
	      if ((vecData != null) && (vecData.size() > 0)) {
	        Vector vecRow = (Vector)vecData.elementAt(0);
	        depid = (String)vecRow.elementAt(0);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return depid;
	  }

	  public List getAllBaseRoles(Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmRMCache.values());
	    return list;
	  }

	  public List getAllRoles(Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmRMCache.values());
	    Iterator it = OUCache.getInstance().hmOMCache.keySet().iterator();

	    while (it.hasNext()) {
	      String orgid = (String)it.next();
	      OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	      list.addAll(om.getRoleList());
	    }
	    return list;
	  }

	  public List getAllRoles(String orgid, Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmRMCache.values());
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om != null)
	      list.addAll(om.getRoleList());
	    return list;
	  }

	  public List getAllRoles(String orgid, String depid, Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmRMCache.values());
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om != null)
	      list.addAll(om.getDepModel(depid).getRoleList());
	    return list;
	  }

	  public RoleModel getRoleModel(String orgid, String roleid, Connection con)
	  {
	    if ((orgid == null) || (orgid.equals("")))
	      return (RoleModel)OUCache.getInstance().hmRMCache.get(roleid);
	    RoleModel rm = ((OrgModel)OUCache.getInstance().hmOMCache.get(orgid)).getRoleModel(roleid);
	    if (rm != null) {
	      return rm;
	    }
	    return (RoleModel)OUCache.getInstance().hmRMCache.get(roleid);
	  }

	  public List getUsersByRole(String orgid, String roleid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select a.account_name,a.user_name,a.user_state,a.email,a.mobilephone,a.org_id,a.org_id from admin_auth_account a left join admin_auth_account_role b on a.id = b.account_id   where   b.role_id=" + 
	        roleid, con);
	      for (int i = 0; i < vecData.size(); i++) {
	        Vector vecRow = (Vector)vecData.elementAt(i);
	        UserModel um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt(((String)vecRow.elementAt(2) == null) || (vecRow.elementAt(2).equals("")) ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid((String)vecRow.elementAt(5));
	        um.setDepid((String)vecRow.elementAt(6));
	        list.add(um);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	  public List getRolesByUser(String orgid, String userid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select d.id,d.role_type,d.role_name,d.role_code,a.org_id from admin_auth_account a left join admin_auth_account_role b on a.id = b.account_id  left join admin_auth_role d on b.role_id = d.id  where a.id='" + 
	        userid + "'", con);
	      for (int j = 0; j < vecData.size(); j++) {
	        Vector vecRow = (Vector)vecData.elementAt(j);
	        RoleModel rolemodel = new RoleModel();
	        rolemodel.setRoleid((String)vecRow.elementAt(0));
	        rolemodel.setRoletype((String)vecRow.elementAt(1));
	        rolemodel.setRolename((String)vecRow.elementAt(2));
	        rolemodel.setRoleright((String)vecRow.elementAt(3));
	        rolemodel.setOrgid((String)vecRow.elementAt(4));
	        rolemodel.setDepid((String)vecRow.elementAt(5));
	        list.add(rolemodel);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	  public List getAllBaseGroups(Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmGMCache.values());
	    return list;
	  }

	  public List getAllGroups(Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmGMCache.values());
	    Iterator it = OUCache.getInstance().hmOMCache.keySet().iterator();

	    while (it.hasNext()) {
	      String orgid = (String)it.next();
	      OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	      list.addAll(om.getGroupList());
	    }
	    return list;
	  }

	  public List getAllGroups(String orgid, Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmGMCache.values());
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om != null)
	      list.addAll(om.getGroupList());
	    return list;
	  }

	  public List getAllGroups(String orgid, String depid, Connection con)
	  {
	    List list = new ArrayList();
	    list.addAll(OUCache.getInstance().hmGMCache.values());
	    OrgModel om = (OrgModel)OUCache.getInstance().hmOMCache.get(orgid);
	    if (om != null)
	      list.addAll(om.getDepModel(depid).getGroupList());
	    return list;
	  }

	  public GroupModel getGroupModel(String orgid, String groupid, Connection con)
	  {
	    if ((orgid == null) || (orgid.equals("")))
	      return (GroupModel)OUCache.getInstance().hmGMCache.get(groupid);
	    GroupModel gm = ((OrgModel)OUCache.getInstance().hmOMCache.get(orgid)).getGroupModel(groupid);
	    if (gm != null) {
	      return gm;
	    }
	    return (GroupModel)OUCache.getInstance().hmRMCache.get(groupid);
	  }

	  public List getUsersByGroup(String orgid, String groupid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select a.actorno,a.actorname,a.state,a.usermail,a.mobile,a.organno,a.depno from s_user a,s_dutyuser b where a.actorno=b.actorno and a.organno='" + orgid + "' and b.dutyno='" + groupid + "'", con);
	      for (int i = 0; i < vecData.size(); i++) {
	        Vector vecRow = (Vector)vecData.elementAt(i);
	        UserModel um = new UserModel();
	        um.setUserid((String)vecRow.elementAt(0));
	        um.setUsername((String)vecRow.elementAt(1));
	        um.setUserstatus(Integer.parseInt((String)vecRow.elementAt(2) == null ? "0" : (String)vecRow.elementAt(2)));
	        um.setEmail((String)vecRow.elementAt(3));
	        um.setMobile((String)vecRow.elementAt(4));
	        um.setOrgid((String)vecRow.elementAt(5));
	        um.setDepid((String)vecRow.elementAt(6));
	        list.add(um);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	  public List getGroupByUser(String orgid, String userid, Connection con)
	  {
	    List list = new ArrayList();

	    DbControl db = DbControl.getInstance();

	    boolean bClose = false;
	    try {
	      if (con == null) {
	        con = db.getConnection();
	        bClose = true;
	      }
	      Vector vecData = db.performQuery("select a.dutyno,a.dutyname,a.dutyright,a.organno,a.depno from s_duty a,s_dutyuser b where a.dutyno=b.dutyno and b.actorno='" + userid + "'", con);
	      for (int j = 0; j < vecData.size(); j++) {
	        Vector vecRow = (Vector)vecData.elementAt(j);
	        GroupModel groupmodel = new GroupModel();
	        groupmodel.setGroupid((String)vecRow.elementAt(0));
	        groupmodel.setGroupname((String)vecRow.elementAt(1));
	        groupmodel.setGroupright((String)vecRow.elementAt(2));
	        groupmodel.setOrgid((String)vecRow.elementAt(3));
	        groupmodel.setDepid((String)vecRow.elementAt(4));
	        list.add(groupmodel);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();

	      if ((bClose) && (con != null))
	        try {
	          db.freeConnection(con);
	        } catch (Exception e1) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e1.printStackTrace();
	        }
	    }
	    finally
	    {
	      if ((bClose) && (con != null)) {
	        try {
	          db.freeConnection(con);
	        } catch (Exception e) {
	          WfLog.log(4, "释放当前数据库连接时出现异常");
	          e.printStackTrace();
	        }
	      }
	    }
	    return list;
	  }

	  public String getGrantor(String orgid, String userid, String appid, Connection con)
	  {
	    return null;
	  }

	  public List getSuperiorUsers(String orgid, String userid, Connection con)
	  {
	    OrgModel om = getParentOrg(getOrgIdByUser(userid, con), con);
	    return getDirectUsersByOrg(om.getOrgid(), con);
	  }

	  public List getJuniorUsers(String orgid, String userid, Connection con)
	  {
	    List al = getDirectSubOrgs(getOrgIdByUser(userid, con), con);
	    if ((al == null) || (al.size() == 0))
	      return new ArrayList();
	    List list = new ArrayList();

	    for (int i = 0; i < al.size(); i++) {
	      OrgModel om = (OrgModel)al.get(i);
	      list.addAll(getDirectUsersByOrg(om.getOrgid(), con));
	    }
	    return list;
	  }

	  public List getSameDepUsers(String orgid, String userid, Connection con)
	  {
	    return getDirectUsersByDep(orgid, getDepIdByUser(userid, con), con);
	  }

	  public List getSameOrgUsers(String orgid, String userid, Connection con)
	  {
	    String organno = getOrgIdByUser(userid, con);
	    return getDirectUsersByOrg(organno, con);
	  }

	  public String getUserEmail(String orgid, String userid, Connection con)
	  {
	    if ((userid == null) || (userid.equals("")))
	      return null;
	    if (userid.indexOf(";") == -1) {
	      return getUserModel(orgid, userid, con).getEmail();
	    }
	    String strResult = "";
	    StringTokenizer st = new StringTokenizer(userid, ";");
	    while (st.hasMoreElements()) {
	      strResult = strResult + ";" + getUserModel(orgid, (String)st.nextElement(), con).getEmail();
	    }
	    if (strResult.length() > 1)
	      strResult = strResult.substring(1);
	    return strResult;
	  }

	  public void loadOUCache(OUCache oucache, Connection con)
	  {
	    oucache.hmOMCache.clear();
	    oucache.hmRMCache.clear();
	    oucache.hmGMCache.clear();

	    DbControl db = DbControl.getInstance();
	    try
	    {
	      String sqlstr = "select org_id,up_org_id,org_id,org_name,org_level from admin_auth_org  order by org_level,id";
	      Vector vecData = db.performQuery(sqlstr, con);
	      for (int i = 0; i < vecData.size(); i++) {
	        Vector vecRow = (Vector)vecData.elementAt(i);
	        String orgid = (String)vecRow.elementAt(0);
	        String suporgid = (String)vecRow.elementAt(1);
	        OrgModel orgmodel = new OrgModel();
	        orgmodel.setOrgid(orgid);
	        orgmodel.setOrgcode((String)vecRow.elementAt(2));
	        orgmodel.setOrgname((String)vecRow.elementAt(3));
	        orgmodel.setOrgstatus(0);
	        oucache.hmOMCache.put(orgid, orgmodel);
	        if ("1".equals(vecRow.elementAt(4))) {
	          orgmodel.setSuporgid(null);
	          orgmodel.setOrglevel(1);
	          oucache.rootOrgModel = orgmodel;
	        } else {
	          orgmodel.setSuporgid(suporgid);
	          orgmodel.setOrglevel(Integer.parseInt((String)vecRow.elementAt(4) == null ? "-1" : (String)vecRow.elementAt(4)));
	          if (oucache.hmOMCache.containsKey(suporgid)) {
	            ((OrgModel)oucache.hmOMCache.get(suporgid)).getOrgList().add(orgmodel);
	            OrgModel omtmp = orgmodel;
	            while (omtmp.getSuporgid() != null) {
	              if (!oucache.hmOMCache.containsKey(omtmp.getSuporgid())) break;
	              omtmp = (OrgModel)oucache.hmOMCache.get(omtmp.getSuporgid());
	              omtmp.getAllorgList().add(orgmodel);
	            }

	          }

	        }

	        sqlstr = "select org_id,up_org_id,org_name from admin_auth_org where org_level = '4' and org_id='" + orgid + "' order by id";
	        Vector vecData2 = db.performQuery(sqlstr, con);
	        for (int j = 0; j < vecData2.size(); j++) {
	          Vector vecRow2 = (Vector)vecData2.elementAt(j);
	          String depid = (String)vecRow2.elementAt(0);
	          String supdepid = (String)vecRow2.elementAt(1);
	          DepModel depmodel = new DepModel();
	          depmodel.setDepid(depid);
	          depmodel.setDepname((String)vecRow2.elementAt(2));
	          depmodel.setOrgid(orgid);
	          depmodel.setDepstatus(0);
	          if ((supdepid == null) || (supdepid.equals("")) || (supdepid.equals("1"))) {
	            depmodel.setSupdepid(null);
	            orgmodel.getDepList().add(depmodel);
	            orgmodel.getAlldepList().add(depmodel);
	          } else {
	            depmodel.setSupdepid(supdepid);
	            orgmodel.getAlldepList().add(depmodel);
	            DepModel supdm = orgmodel.getDepModel(supdepid);
	            if (supdm != null) {
	              supdm.getDepList().add(depmodel);
	              supdm.getAlldepList().add(depmodel);
	              while (supdm.getSupdepid() != null) {
	                supdm = orgmodel.getDepModel(supdm.getSupdepid());
	                if (supdm == null) break;
	                supdm.getAlldepList().add(depmodel);
	              }

	            }

	          }

	        }

	      }

	      sqlstr = "select id,role_type,role_name from admin_auth_role  order by id";
	      Vector vecData2 = db.performQuery(sqlstr, con);
	      for (int j = 0; j < vecData2.size(); j++) {
	        Vector vecRow2 = (Vector)vecData2.elementAt(j);
	        String roleid = (String)vecRow2.elementAt(0);
	        RoleModel rolemodel = new RoleModel();
	        rolemodel.setRoleid(roleid);
	        rolemodel.setRoletype((String)vecRow2.elementAt(1));
	        rolemodel.setRolename((String)vecRow2.elementAt(2));
	        rolemodel.setRoleright("0");
	        rolemodel.setOrgid(null);
	        rolemodel.setDepid(null);
	        rolemodel.isBaseFlag = true;
	        oucache.hmRMCache.put(roleid, rolemodel);
	      }

	    }
	    catch (Exception e)
	    {
	      WfLog.log(4, "【Exception】加载组织机构缓存信息异常，出错信息如下：");
	      e.printStackTrace();
	    }
	  }

	  public List getAllWFClient(String arg0, Connection arg1)
	  {
	    return null;
	  }

	  public List getRolesByName(String arg0, String arg1, Connection arg2)
	  {
	    return null;
	  }
}
