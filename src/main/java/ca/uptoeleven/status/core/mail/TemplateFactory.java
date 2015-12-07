package ca.uptoeleven.status.core.mail;

public interface TemplateFactory {
	EmailTemplate getTemplate(String templateName);
}
