package ca.uptoeleven.status.core.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateSubstitutions {
	public String getServiceName() {
		return "status";
	}

	public String getServiceUrl() {
		return "http://example.com";
	}
}
