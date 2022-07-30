package fiap.drone.tracker.master.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Value("${email.to}")
    private String to;

    @Value("${email.from}")
    private String from;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String droneMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Drone Tracker");
        message.setText(droneMessage);
        emailSender.send(message);
    }
}
