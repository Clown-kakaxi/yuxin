package com.ecc.echain.wf;

/**
 * @describtion: 工作流涉及到的相关常量
 * 
 * **工作流相关变量定义说明：**************************
 *  变量前缀统一为：
 *  WF_ID_		流程wfid
 *  WF_PRE_		流程实例前缀
 *  WF_NODES_	流程节点集合（代码需要用到的）
 * ************************************************
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年10月17日 上午10:34:53
 */
public class WfConstant {
	
	/** 客户分配WFID */
	public final static String WF_ID_KHFP = "10";
	/** 客户分配前缀 */
	public final static String WF_PRE_KHFP = "KHFP_";
	/** 客户分配节点集合 */
	public final static String[] WF_NODES_KHFP = { "10_a3", "10_a4" };

	/** 客户移交WFID */
	public final static String WF_ID_KHYJ = "11";
	/** 客户移交前缀 */
	public final static String WF_PRE_KHYJ = "KHYJ_";
	/** 客户移交节点集合 */
	public final static String[] WF_NODES_KHYJ = { "11_a3", "11_a4" };
	
	public WfInfoVo getWf(String wfPer){
		WfInfoVo vo = new WfInfoVo();
		return vo;
	}
	
	class WfInfoVo {
		private String wfId;
		private String wfPer;
		private String wfNodes;
		
		public String getWfId() {
			return wfId;
		}
		public void setWfId(String wfId) {
			this.wfId = wfId;
		}
		public String getWfPer() {
			return wfPer;
		}
		public void setWfPer(String wfPer) {
			this.wfPer = wfPer;
		}
		public String getWfNodes() {
			return wfNodes;
		}
		public void setWfNodes(String wfNodes) {
			this.wfNodes = wfNodes;
		}
	}
}
