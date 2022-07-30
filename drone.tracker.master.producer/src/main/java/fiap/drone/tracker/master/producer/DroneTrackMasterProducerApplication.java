package fiap.drone.tracker.master.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DroneTrackMasterProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneTrackMasterProducerApplication.class, args);
	}

}
