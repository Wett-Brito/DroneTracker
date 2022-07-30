package fiap.drone.tracker.master.producer.business;

import fiap.drone.tracker.master.producer.models.DroneInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
@Log4j2
public class DroneBusiness {

    private final static Integer MAX_ALERT = 6;
    private final static Integer MAX_LOWER_TEMPERATURE = 0;
    private final static Integer MIN_HIGHER_TEMPERATURE = 35;
    private final static Integer MIN_UMIDITY = 15;
    private final static Integer MIN_TEMPERATURE = -25;
    private final static Integer MAX_TEMPERATURE = 40;
    private final static Integer MIN_HUMIDITY = 0;
    private final static String DUPLICATED_ID = "Id já existente.";

    private final static Integer MAX_HUMIDITY = 100;

    private List<DroneInfo> dronesInfo =  new ArrayList<>();

    public void startDrone(DroneInfo droneInfo) {
        Optional.ofNullable(droneInfo)
                .map(DroneInfo::getId_drone)
                .map(this::verifyIfNotExistsDroneById)
                .filter(exists -> exists.equals(Boolean.TRUE))
                .ifPresentOrElse(
                        (d) -> this.addNewDroneOrReactivate(droneInfo),
                        () -> log.error(DUPLICATED_ID)
                );
    }

    public boolean verifyIfNotExistsDroneById(Integer idDrone) {
        return this.dronesInfo
                .stream()
                .map(DroneInfo::getId_drone)
                .filter(e -> e.equals(idDrone))
                .findFirst()
                .isEmpty();
    }

    public void addNewDroneOrReactivate(DroneInfo droneInfo) {
        this.dronesInfo
                .stream()
                .filter(drone -> drone.getId_drone().equals(droneInfo.getId_drone()))
                .findFirst()
                .ifPresentOrElse(
                        drone -> this.reactiveDrone(droneInfo.getId_drone()),
                        () -> dronesInfo.add(droneInfo)
                );
    }

    public void reactiveDrone(Integer id) {
        this.dronesInfo
                .stream()
                .filter(drone -> drone.getId_drone().equals(id))
                .findFirst()
                .ifPresent(drone -> drone.setRunningAlertDrone(true));
    }

    public boolean hasAnyWorkingDrone() {
        return this.dronesInfo.stream().anyMatch(DroneInfo::isRunningAlertDrone);
    }

    public void generateDronesRandomValues() {
        this.getDronesInfo().forEach(this::generateDroneRandomValues);
    }

    public void generateDroneRandomValues(DroneInfo droneInfo) {
        Random random =  new Random();

        Integer randomTemperature = random.nextInt(MAX_TEMPERATURE - MIN_TEMPERATURE + 1) + MIN_TEMPERATURE;
        Integer randomHumidity = random.nextInt(MAX_HUMIDITY - MIN_HUMIDITY + 1) + MIN_HUMIDITY;

        droneInfo.setTemperatura(randomTemperature);
        droneInfo.setUmidade(randomHumidity);
    }

    public void stopDrone(Integer idDrone) {
        this.dronesInfo
                .stream()
                .filter(drone -> drone.getId_drone().equals(idDrone))
                .findFirst()
                .ifPresent(drone -> drone.setRunningAlertDrone(false));
    }

    private boolean alertTemperature(DroneInfo droneInfo) {
        droneInfo.setAlertTimesTemperature(droneInfo.getAlertTimesTemperature() + 1);

        return droneInfo.getAlertTimesTemperature().equals(MAX_ALERT);
    }

    private boolean clearAlertTemperature(DroneInfo droneInfo) {
        droneInfo.setAlertTimesTemperature(0);

        return false;
    }

    private boolean alertUmidity(DroneInfo droneInfo) {
        droneInfo.setAlertTimesUmidity(droneInfo.getAlertTimesUmidity() + 1);

        return droneInfo.getAlertTimesUmidity().equals(MAX_ALERT);
    }

    private boolean clearAlertUmidity(DroneInfo droneInfo) {
        droneInfo.setAlertTimesUmidity(0);

        return false;
    }

    private boolean verifyTemperature(DroneInfo droneInfo) {
        return droneInfo.getTemperatura() >= MIN_HIGHER_TEMPERATURE || droneInfo.getTemperatura() <= MAX_LOWER_TEMPERATURE ?
                this.alertTemperature(droneInfo) :
                this.clearAlertTemperature(droneInfo);
    }

    private boolean verifyUmidity(DroneInfo droneInfo) {
        return droneInfo.getUmidade() <= MIN_UMIDITY  ?
                this.alertUmidity(droneInfo) :
                this.clearAlertUmidity(droneInfo);
    }

    private Boolean haveToAlertAboutDroneHealth(DroneInfo droneInfo) {
        return this.verifyTemperature(droneInfo) || this.verifyUmidity(droneInfo);
    }

    public List<DroneInfo> getDroneWithRisk() {
        return this.getDronesInfo()
                .stream()
                .filter(this::haveToAlertAboutDroneHealth)
                .collect(Collectors.toList());
    }

    public Integer getDroneSize() {
        return (int) this.dronesInfo.stream().filter(DroneInfo::isRunningAlertDrone).count();
    }

}
