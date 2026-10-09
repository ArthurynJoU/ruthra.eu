package eu.ruthra.contact;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

import java.util.Map;

public class ContactHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SesV2Client SES = SesV2Client.create();
    private static final String MAIL = System.getenv("CONTACT_EMAIL");

    record ContactRequest(String name, String email, String message) {}

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        try {
            ContactRequest raw = MAPPER.readValue(event.getBody(), ContactRequest.class);

            String error = validate(raw);
            if (error != null) {
                return response(400, error);
            }

            ContactRequest req = new ContactRequest(raw.name().trim(), raw.email().trim(), raw.message().trim());

            SendEmailRequest email = SendEmailRequest.builder()
                    .fromEmailAddress(MAIL)
                    .destination(Destination.builder().toAddresses(MAIL).build())
                    .replyToAddresses(req.email())
                    .content(EmailContent.builder()
                            .simple(Message.builder()
                                    .subject(Content.builder().data("ruthra.eu: message from " + req.name()).build())
                                    .body(Body.builder()
                                            .text(Content.builder().data(req.message() + "\n\nFrom: " + req.name() + " <" + req.email() + ">").build())
                                            .build())
                                    .build())
                            .build())
                    .build();

            SES.sendEmail(email);
            return response(200, "Message sent");

        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            return response(500, "Something went wrong");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    static String validate(ContactRequest req) {
        if (req == null) {
            return "Request body is required";
        }
        if (isBlank(req.name())) {
            return "Name is required";
        }
        if (isBlank(req.email())) {
            return "Email is required";
        }
        if (isBlank(req.message())) {
            return "Message is required";
        }

        String name = req.name().trim();
        String email = req.email().trim();
        String message = req.message().trim();

        if (name.length() > 100) {
            return "Name is too long (max 100 characters)";
        }
        if (name.contains("\n") || name.contains("\r")) {
            return "Name must be on one line";
        }
        if (email.length() > 200) {
            return "Email is too long (max 200 characters)";
        }
        if (message.length() > 5000) {
            return "Message is too long (max 5000 characters)";
        }

        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        if (at <= 0
                || at != email.lastIndexOf('@')
                || dot < at + 2
                || dot == email.length() - 1
                || email.contains(" ")) {
            return "Invalid email";
        }

        return null;
    }

    private APIGatewayV2HTTPResponse response(int status, String message) {
        return APIGatewayV2HTTPResponse.builder()
                .withStatusCode(status)
                .withHeaders(Map.of("Content-Type", "application/json"))
                .withBody("{\"message\":\"" + message + "\"}")
                .build();
    }
}