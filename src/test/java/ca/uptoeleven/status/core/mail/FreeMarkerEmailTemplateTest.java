package ca.uptoeleven.status.core.mail;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FreeMarkerEmailTemplateTest {

	@Test
	public void testGetHtmlContentReturnsSimpleText() throws Exception {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate("Hello World!");
		assertEquals("Hello World!", template.getHtmlContent());
	}

	@Test
	public void testSimpleSubstitution() throws Exception {
		FreemarkerEmailTemplate template = new FreemarkerEmailTemplate("Hello ${general.serviceName}!");
		TemplateSubstitutions subs = new TemplateSubstitutions();
		template.setSubstitutions(subs);
		assertEquals("Hello status!", template.getHtmlContent());
	}

}
