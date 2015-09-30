package ca.uptoeleven.status.mail;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EmailTemplateTest {

    @Test
    public void testGetSimpleTextReturnsUnmodified() {
        EmailTemplate template = new EmailTemplate("Hello World!");
        assertEquals("Hello World!", template.getTextContent());
    }

    @Test
    public void testGetTextContentIncludesSubstitutions() {
        EmailTemplate template = new EmailTemplate("Hello {{user.name}}. Welcome to {{service.name}}!");
        Map<String, String> substitutions = new HashMap<>();
        substitutions.put("user.name", "John" );
        substitutions.put("service.name", "AwesomeService" );
        template.setSubstitutions(substitutions);
        assertEquals("Hello John. Welcome to AwesomeService!", template.getTextContent());
    }

    @Test
    public void testSimpleMarkdownToHtml() {
        EmailTemplate template = new EmailTemplate("### Hello World!");
        assertEquals("<h3>Hello World!</h3>\n", template.getHtmlContent());
    }

    @Test
    public void testGetHtmlContentIncludesSubstitutions() {
        EmailTemplate template = new EmailTemplate("Hello {{user.name}}. Welcome to {{service.name}}!");
        Map<String, String> substitutions = new HashMap<>();
        substitutions.put("user.name", "John");
        substitutions.put("service.name", "AwesomeService" );
        template.setSubstitutions(substitutions);
        assertEquals("<p>Hello John. Welcome to AwesomeService!</p>\n", template.getHtmlContent());
    }
}
