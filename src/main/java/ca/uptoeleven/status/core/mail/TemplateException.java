package ca.uptoeleven.status.core.mail;

public class TemplateException extends Exception {
	public TemplateException() {}

	public TemplateException(String message) { super(message); }

	public TemplateException(Throwable cause) { super(cause); }

	public TemplateException(String message, Throwable cause) { super(message, cause); }
}
