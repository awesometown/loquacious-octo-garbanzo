package ca.uptoeleven.status.core.mail;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

public class EmailModule extends AbstractModule {

	private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

	@Override
	protected void configure() {
		bind(Mailer.class).to(DummyMailer.class);


		cfg.setClassForTemplateLoading(this.getClass(), "/");
		bind(Configuration.class).toInstance(cfg);
		bind(TemplateFactory.class).to(FreemarkerTemplateFactory.class);
		bind(EmailNotificationEventHandler.class).asEagerSingleton();
	}

	@Provides
	public Template getTemplate(String templateName) {
		try {
			return cfg.getTemplate(templateName);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
