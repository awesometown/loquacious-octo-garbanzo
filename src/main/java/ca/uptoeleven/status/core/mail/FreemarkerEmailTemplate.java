package ca.uptoeleven.status.core.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class FreemarkerEmailTemplate implements EmailTemplate {

	private final String templateContent;

	public FreemarkerEmailTemplate(String templateContent) {
		this.templateContent = templateContent;
	}

	public EmailContent resolve(TemplateSubstitutions substitutions) throws TemplateException {
		String htmlContent = getHtmlContent(substitutions);
		return new EmailContent(htmlContent, null);
	}

	private String getHtmlContent(TemplateSubstitutions substitutions) throws TemplateException {
		try {
			//This stuff needs to get moved out of here and into a singleton factory class
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			Template template = new Template("foo", new StringReader(templateContent), cfg);
			StringWriter out = new StringWriter();
			template.process(substitutions, out);
			return out.toString();
		} catch(Exception ex) {
			log.error("Exception generating html template content.", ex);
			throw new TemplateException(ex);
		}
	}
}
