package com.yuchengtech.emp.biappframe.mtool.vo;

import com.yuchengtech.emp.biappframe.mtool.entity.BioneDatasetInfo;

@SuppressWarnings("serial")
public class BioneDatasetVO extends BioneDatasetInfo {
	private String dsName;
	private String dsTypeName;

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDsTypeName() {
		return dsTypeName;
	}

	public void setDsTypeName(String dsTypeName) {
		this.dsTypeName = dsTypeName;
	}

}
