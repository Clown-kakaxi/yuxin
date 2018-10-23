package com.yuchengtech.emp.core.sequence;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import com.yuchengtech.emp.core.sequence.dbaccess.DBAccess;
import com.yuchengtech.emp.utils.SpringContextHolder;

/**
 * Sequence Factory
 * 
 * @version 1.0
 * @since 1.0.1
 */
@Component
public class SequenceFactory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Use Cache */
	private static boolean useCache = true; // 目前不提供开关...
	
	/** SEQUENCE POOL */
	private Map<String, Sequence> SEQUENCE_POOL = new HashMap<String, Sequence>();
	
	/** DB-Access */
	private DBAccess dbAccesser = SpringContextHolder.getBean(DBAccess.class);
	
	/** Instance */
	private static SequenceFactory instance ;
	
	/** Get Instance */
	public static SequenceFactory getInstance() {
		if(instance==null) {
			instance = new SequenceFactory();
		}
		return instance;
	}
	
	/** Constructor */
	private SequenceFactory() { }
	
	
	/** Get Sequence */
	public Sequence getSequence(String seqKey) {
		return this.getSequence(seqKey, null);
	}
	
	
	/** Get Sequence By Sign */
	public Sequence getSequence(String seqKey, String roolingKey) {
		if(seqKey==null || seqKey.trim().length()<=0 ) {
			throw new IllegalArgumentException("Sequence Sign [seqKey] should not be null! ");
		}
		if(useCache) {
			return this.getUseCache(seqKey, roolingKey);
		} else {
			return this.getNoCache(seqKey, roolingKey);
		}
	}
	
	/** Get Sequence Use Cache */
	private Sequence getUseCache(String seqKey, String roolingKey) {
		String key = this.toKey(seqKey, roolingKey);
		if(this.SEQUENCE_POOL.containsKey(key)) {
			return this.SEQUENCE_POOL.get(key);
		}
		else {
			Sequence sequence = null;
			try {
				sequence = this.fetchSequence(seqKey, roolingKey);
			} catch(NullPointerException ex) {
				throw ex;
			}
			sequence.setFactory(this);
			this.SEQUENCE_POOL.put(key, sequence);
			return sequence;
		}
	}
	
	/** Get Sequence Use No Cache */
	private Sequence getNoCache(String seqKey, String roolingKey) {
		Sequence sequence = null;
		try {
			sequence = this.fetchSequence(seqKey, roolingKey);
		} catch(NullPointerException ex) {
			throw ex;
		}
		return sequence;
	}
	
	
	/** To Key */
	private String toKey(String seqKey, String roolingKey) {
		if(roolingKey!=null && roolingKey.trim().length()>0) {
			return seqKey.concat("#").concat(roolingKey);
		}
		return seqKey;
	}
	
	/** Refill */
	public void refill(Sequence sequence) {
		String seqKey = sequence.getSeqKey(), roolingKey = sequence.getRoolingKey();
		Sequence seq = null;
		try {
			seq = this.fetchSequence(seqKey, roolingKey);
		} catch(NullPointerException ex) {
			throw ex;
		}
		// Update Properties
		try {
			PropertyUtils.copyProperties(((com.yuchengtech.emp.core.sequence.dbaccess.DBSequence)sequence), 
					((com.yuchengtech.emp.core.sequence.dbaccess.DBSequence)seq));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	private Sequence fetchSequence(String seqKey, String roolingKey) throws NullPointerException {
		Sequence sequence = dbAccesser.fetchSequence(seqKey, roolingKey);
		if(sequence==null) {
			throw new NullPointerException("Sequence [" + this.toKey(seqKey, roolingKey) + "] not exists !");
		}
		sequence.setFactory(this); // Important!!
		return sequence;
	}
	
	/** ReCycle */
	public void reCycle(Sequence sequence) {
		// Update
		this.dbAccesser.reCycleSequence(sequence);
		// Refill
		this.refill(sequence);
	}
	
}
