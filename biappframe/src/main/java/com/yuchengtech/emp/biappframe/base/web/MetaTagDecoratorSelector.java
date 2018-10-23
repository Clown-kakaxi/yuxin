package com.yuchengtech.emp.biappframe.base.web;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.content.Content;
import org.sitemesh.webapp.WebAppContext;

public class MetaTagDecoratorSelector implements
		DecoratorSelector<WebAppContext> {

	private final String DECORATOR_PREFIX = "/WEB-INF/pages";
	private final String DECORATOR_SUFFIX = "";

	public MetaTagDecoratorSelector(
			DecoratorSelector<WebAppContext> decoratorSelector) {
	}

	
	public String[] selectDecoratorPaths(Content content, WebAppContext context) {
		String decoratorPath = content.getExtractedProperties()
				.getChild("meta").getChild("decorator").getValue();

		if (decoratorPath == null || decoratorPath.trim().equals(""))
			return new String[] {};
		else
			return new String[] { DECORATOR_PREFIX + decoratorPath.trim()
					+ DECORATOR_SUFFIX };
	}
}
