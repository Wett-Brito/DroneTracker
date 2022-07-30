package fiap.drone.tracker.master.consumer.consumer;

import fiap.drone.tracker.master.consumer.business.DroneHealthBO;
import fiap.drone.tracker.master.consumer.models.DroneInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Log4j2
@Component
public class QueueConsumer {

    private final static String NEW_MESSAGE = "Mensagem recebida";

    @Autowired
    private DroneHealthBO droneHealthBO;

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = {"${drone.tracker.master.queue}"})
    public void receive(@Payload Message message) throws IOException {
        log.info(NEW_MESSAGE);

        DroneInfo droneInfo = mapper.readValue(message.getBody(), DroneInfo.class);

        droneHealthBO.alertDroneHealthWithEmail(droneInfo);
    }
}
