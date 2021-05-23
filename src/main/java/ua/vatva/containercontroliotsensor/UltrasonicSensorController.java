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

    @GetMapping("/" + DEVICE_ID + "/ultrasonicsensordata")
    public ResponseEntity<String> getData() {

        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
        GpioPinDigitalInput sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);

        while (true) {
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

                System.out.println("Distance :" + ((((endTime-startTime)/1e3)/2) / 29.1) + " cm");

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        return new ResponseEntity<>("finished", HttpStatus.OK);
    }
}
