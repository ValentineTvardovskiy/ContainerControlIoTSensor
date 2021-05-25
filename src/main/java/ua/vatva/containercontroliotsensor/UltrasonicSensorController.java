package ua.vatva.containercontroliotsensor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ua.vatva.containercontroliotsensor.domain.UltrasonicData;

import static ua.vatva.containercontroliotsensor.SensorConstrants.DEVICE_ID;
import static ua.vatva.containercontroliotsensor.SensorConstrants.ULTRASONIC_PATH;

@RestController
public class UltrasonicSensorController {

    @GetMapping("/" + DEVICE_ID + "/ultrasonicsensordata")
    public ResponseEntity<UltrasonicData> getData() {
        String url = "http://127.0.0.1:5000" + ULTRASONIC_PATH;
        return new RestTemplate().getForEntity(url, UltrasonicData.class);
    }
}
