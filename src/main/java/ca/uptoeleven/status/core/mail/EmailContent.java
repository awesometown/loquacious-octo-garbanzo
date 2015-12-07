package ca.uptoeleven.status.core.mail;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class EmailContent {

	private final String htmlContent;
	private final String textContent;

	public boolean hasHtmlContent() {
		return !StringUtils.isBlank(htmlContent);
	}

	public boolean hasTextContent() {
		return !StringUtils.isBlank(textContent);
	}
}
