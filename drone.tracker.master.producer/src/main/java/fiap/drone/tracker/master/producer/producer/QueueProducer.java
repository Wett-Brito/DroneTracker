package fiap.drone.tracker.master.producer.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.drone.tracker.master.producer.models.DroneInfo;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Value("${drone.tracker.master.queue}")
    private String queueName;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void sendMessage(DroneInfo droneInfo) {
        amqpTemplate.convertAndSend(this.queueName, this.convertModelToString(droneInfo));
    }

    @SneakyThrows
    private String convertModelToString(DroneInfo droneInfo) {
        return mapper.writeValueAsString(droneInfo);
    }
}
