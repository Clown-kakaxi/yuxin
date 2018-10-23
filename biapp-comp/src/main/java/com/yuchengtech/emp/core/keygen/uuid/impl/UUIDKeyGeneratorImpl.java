package com.yuchengtech.emp.core.keygen.uuid.impl;

import org.springframework.stereotype.Repository;
import com.yuchengtech.emp.core.keygen.uuid.UUIDKeyGenerator;


@Repository
public class UUIDKeyGeneratorImpl implements UUIDKeyGenerator {

	
//	@Override
	public String getUuidKey() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

//	@Override
	public String getUuidKey(String src) {
		return java.util.UUID.fromString(src).toString().replace("-", "");
	}
	
}
