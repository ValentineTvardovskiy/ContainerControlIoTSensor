package ua.vatva.containercontroliotsensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ua.vatva.containercontroliotsensor.SensorConstrants.DEVICE_ID;

@RestController
public class UltrasonicSensorController {

    public static final int ECHO_LIMIT = 5;
//    public static GpioController gpio = GpioFactory.getInstance();
//    public static GpioPinDigitalOutput sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
//    public static GpioPinDigitalInput sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);

    @GetMapping("/" + DEVICE_ID + "/ultrasonicsensordata")
    public ResponseEntity<String> getData() {

        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
        GpioPinDigitalInput sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);

//        int echoCounter = 0;
        double distanceSum = 0;

//        while (echoCounter < ECHO_LIMIT) {
            try {
                sensorTriggerPin.low();
                Thread.sleep(2000);
                sensorTriggerPin.high();
                Thread.sleep((long) 0.01);
                sensorTriggerPin.low();

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH
                }
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.

                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW
                }
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.

                distanceSum += (endTime-startTime) * 17150;
//                echoCounter++;

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
//                break;
            }
//        }
        String response = "Distance : " + distanceSum + " cm";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
