package fiap.drone.tracker.master.producer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneInfo {
    private Integer id_drone;
    private Double longitude;
    private Double latitude;
    private Integer temperatura;
    private Integer umidade;

    private boolean runningAlertDrone = true;

    private Integer alertTimesTemperature = 0;

    private Integer alertTimesUmidity = 0;
}
