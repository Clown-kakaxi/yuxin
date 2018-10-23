// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OcrmFCiHhbMappingService.java

package com.yuchengtech.bcrm.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.customer.model.OcrmFCiHhbMapping;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
@Transactional(value = "postgreTransactionManager")
public class OcrmFCiHhbMappingService extends CommonService
{

	public OcrmFCiHhbMappingService()
	{
		JPABaseDAO<OcrmFCiHhbMapping,Long> baseDao = new JPABaseDAO<OcrmFCiHhbMapping,Long>(OcrmFCiHhbMapping.class);
		super.setBaseDAO(baseDao);
	}
}
