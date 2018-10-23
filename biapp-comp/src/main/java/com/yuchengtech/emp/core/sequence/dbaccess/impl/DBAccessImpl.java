package com.yuchengtech.emp.core.sequence.dbaccess.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.core.dao.JdbcEntityManager;
import com.yuchengtech.emp.core.sequence.Sequence;
import com.yuchengtech.emp.core.sequence.SequenceConstant;
import com.yuchengtech.emp.core.sequence.dbaccess.DBAccess;
import com.yuchengtech.emp.core.sequence.dbaccess.DBSequence;
import com.yuchengtech.emp.core.sequence.util.NumberBlock;
import com.yuchengtech.emp.core.sequence.util.NumberBlockProvider;

/**
 * 序列数据库操作实现类
 * 
 * @version 1.0
 * @since 1.0.1
 */
@Repository
@Transactional(readOnly=true)
public class DBAccessImpl implements DBAccess {

	
	@Autowired
	private JdbcEntityManager jdbcPersist ;

//	@Override
	@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
	public Sequence fetchSequence(String seqKey) {
		return this.fetchSequence(seqKey, null);
	}

//	@Override
	@SuppressWarnings("unused")
	@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
	public Sequence fetchSequence(String seqKey, String roolingKey) {
		if(roolingKey==null || roolingKey.trim().length()<=0) {
			roolingKey = SequenceConstant.NULL_KEY;
		}
		// 查询
		DBSequence sequence = null;
		try {
			sequence = new DBAccessDao().querySequenceById(seqKey, roolingKey);
		} catch(DataAccessException ex) {
			sequence = null;
		}
		if(sequence==null) { 
			return null; 
		}
		// 取值
		NumberBlock numBlock = NumberBlockProvider.getInstance().getNumberBlock(sequence);
		// 计算下一可用值
		long newCutoff = numBlock.getMax() + sequence.getIncremental();
		// 这里做可循环判断
		if(sequence.getCyclical()!=null && 
				sequence.getCyclical().equals(SequenceConstant.BOOLEAN_TRUE)) {
			java.util.Date currentDate = new java.util.Date();
			java.util.Date lasteditDate = ((DBSequence)sequence).getLastEditDate();
			// 判断是否需要循环
			if(sequence.getCycleTime()==SequenceConstant.CYCLE_YEAR 
				|| sequence.getCycleTime()==SequenceConstant.CYCLE_MONTH
				|| sequence.getCycleTime()==SequenceConstant.CYCLE_DATE
				|| sequence.getCycleTime()==SequenceConstant.CYCLE_HOUR_OF_DAY
				|| sequence.getCycleTime()==SequenceConstant.CYCLE_MINUTE) {
				if(((DBSequence)sequence).needCycle()) {
					newCutoff = sequence.getStartVal();
				}
			} else {
				if(newCutoff>sequence.getMaxVal()) {
					newCutoff = sequence.getStartVal();
				}
			}
		}
		// 更新数据库
		new DBAccessDao().mergeSequence(seqKey, roolingKey, newCutoff, new java.util.Date());
		// 设置Figures
		sequence.loadData(numBlock);
		// 
		return sequence;
	}
	
//	@Override
	@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
	public void removeSequence(String seqKey, String roolingKey) {
		StringBuilder sql = new StringBuilder(" delete from ")
			.append(SequenceConstant.SEQUENCE_TABLE)
			.append(" where seqkey=").append("'").append(seqKey).append("'")
		;
		if(roolingKey!=null && roolingKey.trim().length()>0) {
			sql.append(" and roolingkey=").append("'").append(roolingKey).append("'");
		}
		jdbcPersist.executeSQL(sql.toString());
	}

//	@Override
	@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
	public Sequence createSequence(Sequence sequence) {
		throw new UnsupportedOperationException("This Method Not yet Implemented ...");
	}

//	@Override
	@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
	public Sequence updateSequence(Sequence sequence) {
		throw new UnsupportedOperationException("This Method Not yet Implemented ...");
	}

//	@Override
	@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
	public Sequence reCycleSequence(Sequence sequence) {
		new DBAccessDao().mergeSequence(sequence.getSeqKey(), sequence.getRoolingKey(), sequence.getStartVal(), new java.util.Date());
		return sequence;
	}
	
	
	/* 先放在这里 */
	class DBAccessDao {
		
		private StringBuilder selectSql = new StringBuilder(" select * from ").append(SequenceConstant.SEQUENCE_TABLE);
		private StringBuilder updateSql = new StringBuilder(" update ").append(SequenceConstant.SEQUENCE_TABLE);
		private StringBuilder updSetSql = new StringBuilder(" set ").append(" cutoff=? ").append(", ").append(" lasteditdate=? ");
		private StringBuilder whereSql  = new StringBuilder(" where seqkey = ? and roolingkey = ? ");
		
		/* query */
		public DBSequence querySequenceById(String seqKey, String roolingKey) {
			StringBuilder select = new StringBuilder().append(selectSql).append(whereSql);
			List<Object> params = new ArrayList<Object>();
			params.add(seqKey);
			params.add(roolingKey);
			// 查询
			DBSequence sequence = null;
			try {
				sequence = jdbcPersist.findObject(select.toString(), params.toArray(), DBSequence.class);
			} catch(DataAccessException ex) {
				throw ex;
			}
			return sequence;
		}
		
		/* merge */
		@Transactional(readOnly=false, rollbackFor=java.lang.Exception.class)
		public void mergeSequence(String seqKey, String roolingKey, long cutoff, java.util.Date lasteditdate) {
			StringBuilder merge = new StringBuilder().append(updateSql).append(updSetSql).append(whereSql);
			List<Object> params = new ArrayList<Object>();
			params.add(cutoff);
			params.add(lasteditdate);
			params.add(seqKey);
			params.add(roolingKey);
			jdbcPersist.update(merge.toString(), params.toArray());
		}
	}
	
	
}
