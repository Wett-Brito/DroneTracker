package DroneControl.ControlDrone;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

public class teste {
	public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {
        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(senderEmail, "stanislav"))
                .from(new SendContact(senderEmail, "Mailjet integration test"))
                .htmlPart("<h1>This is the HTML content of the mail</h1>")
                .subject("This is the subject")
                .trackOpens(TrackOpens.ENABLED)
                .attachment(Attachment.fromFile(attachmentPath))
                .header("test-header-key", "test-value")
                .customID("custom-id-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);
	}
}