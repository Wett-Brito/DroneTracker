package fiap.drone.tracker.master.producer.schedule;

import fiap.drone.tracker.master.producer.business.DroneBusiness;
import fiap.drone.tracker.master.producer.models.DroneInfo;
import fiap.drone.tracker.master.producer.producer.QueueProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Log4j2
@Configuration
@EnableScheduling
public class SchedulerDroneHealth {

    private final static String HEALTH_MESSAGE = "Validando saúde dos Drones";
    private final static String NUMBER_OF_DRONES_WORKING = "Existem %d drones em funcionamento.";
    private final static String ANYONE_WORKING = "Nenhum drone em funcionamento";

    @Autowired
    private DroneBusiness droneBusiness;

    @Autowired
    private QueueProducer queueProducer;

    @Scheduled(fixedDelay = 10000)
    public void scheduleByFixedRate() throws Exception {
        if (droneBusiness.hasAnyWorkingDrone()) {
            log.info(HEALTH_MESSAGE);
            log.info(String.format(NUMBER_OF_DRONES_WORKING, droneBusiness.getDroneSize()));

            this.droneBusiness.generateDronesRandomValues();

            this.alertIfNecessaryAboutDronesHealth();
        }
        else {
            log.info(ANYONE_WORKING);
        }
    }

    public void alertIfNecessaryAboutDronesHealth() {
        this.droneBusiness
                .getDroneWithRisk()
                .forEach(this::sendToWarningDroneHealth);
    }

    public void sendToWarningDroneHealth(DroneInfo droneInfo) {
        queueProducer.sendMessage(droneInfo);
    }
}