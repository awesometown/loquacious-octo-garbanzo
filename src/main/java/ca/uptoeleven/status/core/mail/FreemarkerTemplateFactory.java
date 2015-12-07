package ca.uptoeleven.status.core.mail;

import com.google.inject.Inject;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

public class FreemarkerTemplateFactory implements TemplateFactory {

	private final String TEMPLATE_RESOURCE_PATH = "/templates/";

	private final Configuration config;

	@Inject
	public FreemarkerTemplateFactory(Configuration freemarkerConfig) {
		this.config = freemarkerConfig;
	}

	@Override
	public EmailTemplate getTemplate(String templateName) {
		try {
			Template template = config.getTemplate(TEMPLATE_RESOURCE_PATH + templateName + ".html.tpl");
			return new FreemarkerEmailTemplate(template);
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
