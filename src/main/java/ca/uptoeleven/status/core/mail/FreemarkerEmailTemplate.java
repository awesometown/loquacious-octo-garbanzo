package ca.uptoeleven.status.core.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class FreemarkerEmailTemplate implements EmailTemplate {

	private final Template template;

	public FreemarkerEmailTemplate(Template freemarkerTemplate) {
		this.template = freemarkerTemplate;
	}

	public EmailContent resolve(TemplateSubstitutions substitutions) throws TemplateException {
		String htmlContent = getHtmlContent(substitutions);
		return new EmailContent(htmlContent, null);
	}

	private String getHtmlContent(TemplateSubstitutions substitutions) throws TemplateException {
		try {
			StringWriter out = new StringWriter();
			template.process(substitutions, out);
			return out.toString();
		} catch(Exception ex) {
			log.error("Exception generating html template content.", ex);
			throw new TemplateException(ex);
		}
	}
}
