package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static ca.uptoeleven.status.core.EntityHelpers.newIncidentForTest;
import static ca.uptoeleven.status.core.IdGenerator.newId;
import static org.junit.Assert.assertEquals;

public class FreemarkerEmailTemplateTest {

	private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

	private Template createTemplate(String contents) {
		try {
			return new Template(newId(), new StringReader(contents), cfg);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testSimpleSubstitution() throws TemplateException {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate(createTemplate("Hello ${serviceName}!"));
		TemplateSubstitutions subs = new TemplateSubstitutions();
		assertEquals("Hello status!", template.resolve(subs).getHtmlContent());
	}

	@Test
	public void testNestedSubstitution()  throws TemplateException {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate(createTemplate("Hello ${incident.id}"));
		Incident newIncident = newIncidentForTest();
		TemplateSubstitutions subs = new NewIncidentSubstitutions(newIncident);
		assertEquals("Hello " + newIncident.getId(), template.resolve(subs).getHtmlContent());
	}

	@Test(expected = TemplateException.class)
	public void testMissingSubstitutionsThrowsException() throws TemplateException {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate(createTemplate("Hello ${nope}!"));
		template.resolve(new TemplateSubstitutions()).getHtmlContent();
	}




}
