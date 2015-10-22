package ca.uptoeleven.status.core.mail;

import java.io.IOException;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;

public class Mailer {

    static final String FROM = "TNTTeamKitchener@ea.com";  // Replace with your "From" address. This address must be verified.
    static final String TO = "john.price@gmail.com"; // Replace with a "To" address. If your account is still in the
    // sandbox, this address must be verified.
    static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
    static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";


    public static void sendEmail(EmailTemplate template) throws IOException {

        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{TO});

        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(template.getTextContent());
        Content htmlBody = new Content().withData(template.getHtmlContent());
        Body body = new Body().withHtml(htmlBody).withText(textBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

        try
        {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials.
            // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials
            // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables
            // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY.
            // For more information, see http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient();

            // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox
            // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS
            // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using
            // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1
            // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
            Region REGION = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(REGION);

            // Send the email.
            client.sendEmail(request);
            System.out.println("Email sent!");
        }
        catch (Exception ex)
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
}