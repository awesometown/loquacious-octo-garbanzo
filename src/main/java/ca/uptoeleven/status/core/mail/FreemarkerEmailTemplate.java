package ca.uptoeleven.status.core.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class FreemarkerEmailTemplate {

	private final String templateContent;
	private TemplateSubstitutions substitutions;

	public FreemarkerEmailTemplate(String templateContent) {
		this.templateContent = templateContent;
	}

	public void setSubstitutions(TemplateSubstitutions substitutions) {
		this.substitutions = substitutions;
	}

	public String getTextContent() {
		return "";
	}

	public String getHtmlContent() throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		Template template = new Template("foo", new StringReader(templateContent), cfg);
		StringWriter out = new StringWriter();
		template.process(substitutions, out);
		return out.toString();
	}
}
