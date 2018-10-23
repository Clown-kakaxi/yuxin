package com.yuchengtech.bcrm.oneKeyAccount.model;


/**
 * 电信黑灰名单
 * @author wx
 *
 */
public class BlackGrayOrderVO {
	
	private String jydm;//交易代码
	private String jyjg;//交易机构
	private String jygy;//交易柜员
	private String qdbs;//渠道标识
	private String yybs = "sfck";//应用标识
	private String xtbh = "gabdxzp";//系统编号
	private String qdlsh;//渠道流水号
	private String qdrq;//渠道日期
	private String qdsj;//渠道时间
	private String ver = "固定 2.5.5";//接口版本号

	private String mdlx;//名单类型
	private String sjlx;//数据类型
	private String yhbh;//银行编号
	private String sjx;//数据项
	
	
	
	
	public String getJydm() {
		return jydm;
	}
	public void setJydm(String jydm) {
		this.jydm = jydm;
	}
	public String getJyjg() {
		return jyjg;
	}
	public void setJyjg(String jyjg) {
		this.jyjg = jyjg;
	}
	public String getJygy() {
		return jygy;
	}
	public void setJygy(String jygy) {
		this.jygy = jygy;
	}
	public String getQdbs() {
		return qdbs;
	}
	public void setQdbs(String qdbs) {
		this.qdbs = qdbs;
	}
	public String getYybs() {
		return yybs;
	}
	public void setYybs(String yybs) {
		this.yybs = yybs;
	}
	public String getXtbh() {
		return xtbh;
	}
	public void setXtbh(String xtbh) {
		this.xtbh = xtbh;
	}
	public String getQdlsh() {
		return qdlsh;
	}
	public void setQdlsh(String qdlsh) {
		this.qdlsh = qdlsh;
	}
	public String getQdrq() {
		return qdrq;
	}
	public void setQdrq(String qdrq) {
		this.qdrq = qdrq;
	}
	public String getQdsj() {
		return qdsj;
	}
	public void setQdsj(String qdsj) {
		this.qdsj = qdsj;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getMdlx() {
		return mdlx;
	}
	public void setMdlx(String mdlx) {
		this.mdlx = mdlx;
	}
	public String getSjlx() {
		return sjlx;
	}
	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}
	public String getYhbh() {
		return yhbh;
	}
	public void setYhbh(String yhbh) {
		this.yhbh = yhbh;
	}
	public String getSjx() {
		return sjx;
	}
	public void setSjx(String sjx) {
		this.sjx = sjx;
	}
	public String getZhm() {
		return zhm;
	}
	public void setZhm(String zhm) {
		this.zhm = zhm;
	}
	private String zhm;//账户名
}
