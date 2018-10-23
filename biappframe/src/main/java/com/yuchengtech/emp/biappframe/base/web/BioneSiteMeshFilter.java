package com.yuchengtech.emp.biappframe.base.web;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.DivExtractingTagRuleBundle;

import com.yuchengtech.emp.biappframe.base.web.MetaTagDecoratorSelector;

public class BioneSiteMeshFilter extends ConfigurableSiteMeshFilter {

	// private final String DECORATOR_PREFIX = "/WEB-INF/pages";
	// private final String DECORATOR_SUFFIX = "";

	// 使用meta定义装饰模版
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		super.applyCustomConfiguration(builder);
		builder.addTagRuleBundle(new DivExtractingTagRuleBundle());
		builder.setCustomDecoratorSelector(new MetaTagDecoratorSelector(builder
				.getDecoratorSelector()));
		// builder.setCustomContentProcessor(new CustomContentProcessor());
	}
}
