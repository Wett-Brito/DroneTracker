package fiap.drone.tracker.master.producer.controller;

import fiap.drone.tracker.master.producer.business.DroneBusiness;
import fiap.drone.tracker.master.producer.schedule.SchedulerDroneHealth;
import fiap.drone.tracker.master.producer.models.DroneInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@Controller
@RequestMapping("/drone-alert")
public class DroneTrackMasterProducerController {

    private final static String START_MESSAGE = "Adicionando novo Drone";
    private final static String STOP_MESSAGE = "Parando Drone por id";

    @Autowired
    private DroneBusiness droneBusiness;

    @Autowired
    private SchedulerDroneHealth schedulerDroneHealth;

    @PostMapping("/start-drone")
    public ResponseEntity<String> startDrone(@RequestBody DroneInfo droneInfo) {
        log.info(START_MESSAGE);
        this.droneBusiness.startDrone(droneInfo);
        return ResponseEntity.ok(START_MESSAGE);
    }

    @PostMapping("/stop-drone/id/{id}")
    public ResponseEntity<String> stopDrone(@PathVariable(value = "id") Integer idDrone) throws UnknownHostException {
        log.info(STOP_MESSAGE);
        this.droneBusiness.stopDrone(idDrone);
        return ResponseEntity.ok(STOP_MESSAGE);
    }
}
