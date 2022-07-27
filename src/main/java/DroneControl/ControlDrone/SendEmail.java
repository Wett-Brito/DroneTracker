package DroneControl.ControlDrone;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

public class SendEmail {

	public void enviarEmail(String titulo, String mensagem, String destinatario) throws MailjetException, MailjetSocketTimeoutException {
		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		client = new MailjetClient(System.getenv("9cafb6093a5db22fe398a454102d35bc"),
				System.getenv("5aa1035e1d8e18ea5f8916b8bc0fec69"), new ClientOptions("v3.1"));
		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject()
						.put(Emailv31.Message.FROM,
								new JSONObject().put("Email", "dronerastreioteste@gmail.com").put("Name", "teste"))
						.put(Emailv31.Message.TO,
								new JSONArray()
										.put(new JSONObject().put("Email", "dronerastreioteste@gmail.com").put("Name",
												"teste")))
						.put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
						.put(Emailv31.Message.TEXTPART, "My first Mailjet email")
						.put(Emailv31.Message.HTMLPART,
								"<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
						.put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
		response = client.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}

}
