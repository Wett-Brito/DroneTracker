package fiap.drone.tracker.master.consumer.business;

import fiap.drone.tracker.master.consumer.config.EmailSender;
import fiap.drone.tracker.master.consumer.models.DroneInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Setter
@Getter
@Component
public class DroneHealthBO {

    private final static String WORKS_FINE = "O drone está funcionando perfeitamente";
    private final static String SEND_EMAIL = "Enviando Email";

    @Autowired
    private EmailSender emailService;

    public void alertDroneHealthWithEmail(DroneInfo droneInfo) {
        log.info(SEND_EMAIL);

        this.emailService.sendSimpleMessage(this.createMensagemEmail(droneInfo));
    }

    private String createMensagemEmail(DroneInfo droneInfo) {
        String message = "Olá, a medição encontrou problemas no ambiente, os dados são: ";

        message = message.concat(String.format("ID: %d;", droneInfo.getId_drone()));
        message = message.concat(String.format("Temperatura: %d;", droneInfo.getTemperatura()));
        message = message.concat(String.format("Umidade: %d.", droneInfo.getUmidade()));

        return message;
    }
}
