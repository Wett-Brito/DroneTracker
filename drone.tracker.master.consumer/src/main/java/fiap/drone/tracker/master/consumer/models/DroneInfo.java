package fiap.drone.tracker.master.consumer.models;


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
    private Boolean ativarRastreamento;
}

