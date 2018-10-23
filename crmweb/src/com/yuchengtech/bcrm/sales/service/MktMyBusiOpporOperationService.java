package com.yuchengtech.bcrm.sales.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOppor;
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktBusiOpporHisS;
import com.yuchengtech.bcrm.sales.model.OcrmFMmMktSalesActiv;
import com.yuchengtech.bcrm.sales.model.OcrmFWpRemind;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：营销管理->商机管理->我的商机功能操作Service
 * @author wzy
 * @date:2013-02-28
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class MktMyBusiOpporOperationService {
	private static HashMap<String, String> map = null;

	static {
		// 1-网点、2-电话、3-短信、4-上门拜访、5-其他
		map = new HashMap<String, String>();
		map.put("1", "网点");
		map.put("2", "电话");
		map.put("3", "短信");
		map.put("4", "上门拜访");
		map.put("5", "其他");
	}

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	// 保存/更新销售活动信息
	// 备注：由于数据结构中，活动记录和提醒记录不能关联，所以在更新/删除活动时，没有对提醒记录做相应更新/删除）
	@SuppressWarnings("unchecked")
	public void saveOrUpdateBusiOpporActiv(OcrmFMmMktSalesActiv ofmss,
			AuthUser auth) {
		if (ofmss != null) {
			if (ofmss.getSalesActivId() != null) {
				// 更新(修改)销售活动记录
				em.merge(ofmss);
				// 写商机历史记录信息
				this.addBusiOpporHis("修改", ofmss, auth);
			} else {
				// 保存(新增)销售活动记录
				ofmss.setExecUserId(auth.getUserId());// 活动执行人ID
				ofmss.setExecUserName(auth.getUsername());// 活动执行人名称
				ofmss.setExecOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));// 活动执行人所在机构ID
				ofmss.setExecOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));// 活动执行人所在部门名称
				em.persist(ofmss);
				// 新增提醒信息
//				this.addRemind(ofmss);  因信息提醒部分已修改，暂时屏蔽掉新增信息提醒
				// 写商机历史记录信息
				this.addBusiOpporHis("新增", ofmss, auth);
			}
			// 更新活动对应商机的相关信息
			this.updateBusiOpporBySalesActiv(ofmss, 0);
		}
	}

	// 根据销售活动ID集合，删除对应的销售活动记录
	public void delBusiOpporActivByIds(String salesActivIds, AuthUser auth) {
		String[] id_arr = null;
		OcrmFMmMktSalesActiv ofmss = null;
		if (salesActivIds != null && !"".equals(salesActivIds)) {
			id_arr = salesActivIds.split(",");
			if (id_arr != null && id_arr.length > 0) {
				for (int i = 0; i < id_arr.length; i++) {
					ofmss = em.find(OcrmFMmMktSalesActiv.class, id_arr[i]);
					if (ofmss != null) {
						// 删除销售活动
						em.remove(ofmss);
						// 更新商机信息
						this.updateBusiOpporBySalesActiv(ofmss, 1);
						if (i == 0) {
							// 写商机历史记录信息
							this.addBusiOpporHis("删除", ofmss, auth);
						}
					}
				}
			}
		}
	}

	// 新增/修改/删除销售活动时，根据销售活动所处阶段，将对应商机的“商机阶段”和“达成概率”进行更新，规则如下：
	// 1-了解客户需求========================1-了解商机===============10%
	// 2-确认客户需求3-确认产品/服务==========2-确认商机===============25%
	// 4-制定服务方案5-产品介绍==============3-方案论证===============50%
	// 6-销售洽谈===========================4-商务谈判===============75%
	// 7-签约/购买==========================5-商机成交===============100%
	private void updateBusiOpporBySalesActiv(OcrmFMmMktSalesActiv ofmss,
			int type) {
		OcrmFMmMktBusiOppor ofmmbo = null;
		if (ofmss != null && ofmss.getOpporId() != null) {
			ofmmbo = em.find(OcrmFMmMktBusiOppor.class, ofmss.getOpporId());
			if (ofmmbo != null) {
				if (type == 0) {
					// 新增/修改销售活动时
					if ("1".equals(ofmss.getSalesStage())) {
						ofmmbo.setOpporStage("1");
						ofmmbo.setReachProb("10%");
					} else if ("2".equals(ofmss.getSalesStage())
							|| "3".equals(ofmss.getSalesStage())) {
						ofmmbo.setOpporStage("2");
						ofmmbo.setReachProb("25%");
					} else if ("4".equals(ofmss.getSalesStage())
							|| "5".equals(ofmss.getSalesStage())) {
						ofmmbo.setOpporStage("3");
						ofmmbo.setReachProb("50%");
					} else if ("6".equals(ofmss.getSalesStage())) {
						ofmmbo.setOpporStage("4");
						ofmmbo.setReachProb("75%");
					} else if ("7".equals(ofmss.getSalesStage())) {
						ofmmbo.setOpporStage("5");
						ofmmbo.setReachProb("100%");
					}
				} else if (type == 1) {
					// 删除销售活动时
					ofmmbo.setOpporStage("");
					ofmmbo.setReachProb("");
				}
				em.merge(ofmmbo);
			}
		}
	}

	// 增加日程提醒记录
	private void addRemind(OcrmFMmMktSalesActiv ofmss) {
		OcrmFWpRemind ofwr = null;
		SimpleDateFormat sdf = null;
		OcrmFMmMktBusiOppor ofmmbo = null;
		if (ofmss != null && ofmss.getOpporId() != null) {
			ofmmbo = em.find(OcrmFMmMktBusiOppor.class, ofmss.getOpporId());
			if (ofmmbo != null) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ofwr = new OcrmFWpRemind();
				ofwr.setCustId(ofmmbo.getCustId());// 客户ID
				ofwr.setCustName(ofmmbo.getCustName());// 客户名称
				ofwr.setEventName(ofmmbo.getOpporName() + "-"
						+ ofmss.getSalesActivName() + "-"
						+ ofmmbo.getCustName() + "-" + ofmss.getActivContent()
						+ "-" + map.get(ofmss.getExecWay()));// 事件名称（提醒内容）->商机名称+“-”+活动名称+“-”+客户名称+“-“+活动执行内容+活动执行方式
				ofwr.setMsgCrtDate(sdf.format(new Date()));// 信息创建日期
				ofwr.setMsgEndDate(ofmss.getNextContactTime().toString()
						.substring(0, 19));// 信息截止日期
				ofwr.setUserNo(ofmss.getExecUserId());// 用户编号
				ofwr.setUserUnitid(ofmss.getExecOrgId());// 用户机构号
				ofwr.setMsgRemark(ofwr.getEventName());// 备注
				em.persist(ofwr);
			}
		}
	}

	// 关闭商机
	@SuppressWarnings("unchecked")
	public void closeBusiOppor(OcrmFMmMktBusiOppor ofmmbo, String closeType,String failure_reason,
			AuthUser auth) {
		OcrmFMmMktBusiOppor ofmmbo_h = null;
		OcrmFMmMktBusiOpporHisS ofmboh = null;
		if (ofmmbo != null) {
			ofmmbo_h = em.find(OcrmFMmMktBusiOppor.class, ofmmbo.getOpporId());
			if (ofmmbo_h != null) {
				// 更新商机信息
				// 关闭状态，7-失败关闭、8-成功关闭
				if ("0".equals(closeType)) {
					// 成功关闭
					ofmmbo_h.setOpporStat("8");
				} else if ("1".equals(closeType)) {
					// 失败关闭
					ofmmbo_h.setOpporStat(failure_reason);
				}
				ofmmbo_h.setOpporEndDate(ofmmbo.getOpporEndDate());// 商机完成日期
				ofmmbo_h.setReachAmount(ofmmbo.getReachAmount());// 达成金额
				ofmmbo_h.setMktTargetId(ofmmbo.getMktTargetId());// 营销任务指标ID
				ofmmbo_h.setMktTargetName(ofmmbo.getMktTargetName());// 营销任务指标名称
				ofmmbo_h.setMktActivId(ofmmbo.getMktActivId());// 营销活动ID
				ofmmbo_h.setMktActivName(ofmmbo.getMktActivName());// 营销活动名称
				ofmmbo_h.setRelTrad(ofmmbo.getRelTrad());// 关联交易
				ofmmbo_h.setMemo(ofmmbo.getMemo());// 备注
				em.merge(ofmmbo_h);
				// 写商机操作历史记录
				ofmboh = new OcrmFMmMktBusiOpporHisS();
				ofmboh.setOpporId(ofmmbo.getOpporId());
				ofmboh.setOprDateTime(new Timestamp(System.currentTimeMillis()));
				ofmboh.setOprOrgId(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("ID"));
				ofmboh.setOprOrgName(((HashMap<String, String>) (auth
						.getPathOrgList().get(0))).get("UNITNAME"));
				ofmboh.setOprUserId(auth.getUserId());
				ofmboh.setOprUserName(auth.getUsername());
				if ("0".equals(closeType)) {
					// 成功关闭
					ofmboh.setOprContent("“" + auth.getUsername() + "”成功关闭商机。");
				} else if ("1".equals(closeType)) {
					// 失败关闭
					ofmboh.setOprContent("“" + auth.getUsername()
							+ "”失败关闭商机，失败关闭理由：" + ofmmbo.getOpporContent()
							+ "。");
				}
				em.persist(ofmboh);
			}
		}
	}

	// 写商机操作历史记录
	@SuppressWarnings("unchecked")
	private void addBusiOpporHis(String typeMsg, OcrmFMmMktSalesActiv ofmss,
			AuthUser auth) {
		OcrmFMmMktBusiOpporHisS ofmboh = new OcrmFMmMktBusiOpporHisS();
		ofmboh.setOpporId(ofmss.getOpporId());
		ofmboh.setOprDateTime(new Timestamp(System.currentTimeMillis()));
		ofmboh.setOprOrgId(((HashMap<String, String>) (auth.getPathOrgList()
				.get(0))).get("ID"));
		ofmboh.setOprOrgName(((HashMap<String, String>) (auth.getPathOrgList()
				.get(0))).get("UNITNAME"));
		ofmboh.setOprUserId(auth.getUserId());
		ofmboh.setOprUserName(auth.getUsername());
		ofmboh.setOprContent("“" + auth.getUsername() + "”通过" + typeMsg
				+ "销售活动更新商机。");
		em.persist(ofmboh);
	}
}