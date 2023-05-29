package Jason.Mini.Project.services;

// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    public void sendEmail(String userEmail, String subject, String message) {
        System.out.println("Sending Email");
        SendGrid sg = new SendGrid(apiKey);
        Request request = createSendGridRequest(userEmail, subject, message);
        Response response;
        try {
            response = sg.api(request);
            System.out.println("Received sendgrid response status code: " +
                    response.getStatusCode());
            System.out.println("Received sendgrid response body: " + response.getBody());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static Request createSendGridRequest(String userEmail, String subject, String message) {

        Email from = new Email("jasonljy90@gmail.com");
        Email to = new Email(userEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        // SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");

        try {
            request.setBody(mail.build());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return request;

    }
}
