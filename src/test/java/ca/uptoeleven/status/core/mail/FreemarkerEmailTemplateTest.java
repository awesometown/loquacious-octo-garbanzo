package ca.uptoeleven.status.core.mail;

import ca.uptoeleven.status.core.Incident;
import org.junit.Test;

import static ca.uptoeleven.status.core.EntityHelpers.newIncidentForTest;
import static org.junit.Assert.assertEquals;

public class FreemarkerEmailTemplateTest {

	@Test
	public void testSimpleSubstitution() throws TemplateException {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate("Hello ${serviceName}!");
		TemplateSubstitutions subs = new TemplateSubstitutions();
		assertEquals("Hello status!", template.resolve(subs).getHtmlContent());
	}

	@Test
	public void testNestedSubstitution()  throws TemplateException {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate("Hello ${incident.id}");
		Incident newIncident = newIncidentForTest();
		TemplateSubstitutions subs = new NewIncidentSubstitutions(newIncident);
		assertEquals("Hello " + newIncident.getId(), template.resolve(subs).getHtmlContent());
	}

	@Test(expected = TemplateException.class)
	public void testMissingSubstitutionsThrowsException() throws TemplateException {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate("Hello ${nope}!");
		template.resolve(new TemplateSubstitutions()).getHtmlContent();
	}

}
