package com.yuchengtech.emp.biappframe.label.web.vo;

import java.util.List;

import com.yuchengtech.emp.biappframe.label.entity.BioneLabelInfo;
import com.yuchengtech.emp.biappframe.label.entity.BioneLabelTypeInfo;

public class LabelVO {
	private BioneLabelTypeInfo labelType;
	private List<BioneLabelInfo> label;

	public LabelVO() {
	}

	public BioneLabelTypeInfo getLabelType() {
		return labelType;
	}

	public void setLabelType(BioneLabelTypeInfo labelType) {
		this.labelType = labelType;
	}

	public List<BioneLabelInfo> getLabel() {
		return label;
	}

	public void setLabel(List<BioneLabelInfo> label) {
		this.label = label;
	}
}
