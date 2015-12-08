package ca.uptoeleven.status.core.mail;

public interface EmailTemplate {

	EmailContent resolve(TemplateSubstitutions substitutions) throws TemplateException;

}
