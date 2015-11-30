package ca.uptoeleven.status.core.mail;

import com.github.rjeschke.txtmark.Processor;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class EmailTemplate {

	private final String templateContent;
	private Map<String, String> substitutions;

	public EmailTemplate(String templateContent) {
		this.templateContent = templateContent;
	}

	public void setSubstitutions(Map<String, String> substitutions) {
		this.substitutions = new HashMap<>(substitutions);
	}

	public String getTextContent() {
		StrSubstitutor substitutor =
				new StrSubstitutor(substitutions)
						.setVariablePrefix("{{")
						.setVariableSuffix("}}");
		return substitutor.replace(templateContent);
	}

	public String getHtmlContent() {
		return Processor.process(getTextContent());
	}
}
