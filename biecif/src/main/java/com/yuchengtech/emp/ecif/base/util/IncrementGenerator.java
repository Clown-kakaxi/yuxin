package com.yuchengtech.emp.ecif.base.util;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.type.Type;

public class IncrementGenerator implements IdentifierGenerator, Configurable {

	private static final Log log = LogFactory.getLog(IncrementGenerator.class);
	
	private String entityName;
	
	private Long next;

	private String sql;

	public Serializable generate(SessionImplementor session, Object object)

	throws HibernateException {

		Serializable id = session.getEntityPersister( entityName, object )   
				.getIdentifier( object, session.getEntityMode() );  
		
		if (id!=null) {  
			 return id;
		}  

		
		//if (sql != null) {

			getNext(session.connection());

		//}

		return next;

	}

	public void configure(Type type, Properties params, Dialect d)
			throws MappingException {

		entityName = params.getProperty(ENTITY_NAME);  
		
		String table = params.getProperty("table");

		if (table == null)
			table = params.getProperty(PersistentIdentifierGenerator.TABLE);

		String column = params.getProperty("column");

		if (column == null)
			column = params.getProperty(PersistentIdentifierGenerator.PK);

		String schema = params
				.getProperty(PersistentIdentifierGenerator.SCHEMA);

		sql = "select max(" + column + ") from "
				+ (schema == null ? table : schema + '.' + table);

		log.info(sql);

	}

	private void getNext(Connection conn) throws HibernateException {

		StringBuffer buf = new StringBuffer();

		buf.append(System.currentTimeMillis());//加上日期
		Random random = new Random();
		buf.append(random.nextInt(1000));
		
		next =  new Long(buf.toString());
		
//		try {
//
//			PreparedStatement st = conn.prepareStatement(sql);
//
//			ResultSet rs = st.executeQuery();
//
//			if (rs.next()) {
//
//				next = rs.getLong(1) + 1;
//
//			}
//
//			else {
//
//				next = 1l;
//
//			}
//
//		} catch (SQLException e)
//
//		{
//
//			throw new HibernateException(e);
//
//		}
//
//		finally {
//
//			try {
//
//				conn.close();
//
//			} catch (SQLException e)
//
//			{
//
//				throw new HibernateException(e);
//
//			}
//
//		}

	}

}
