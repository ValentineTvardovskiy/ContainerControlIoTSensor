package ua.vatva.containercontroliotsensor;

import com.pi4j.concurrent.ExecutorServiceFactory;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiBcmPin;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//@SpringBootApplication
public class ContainercontroliotsensorApplication {

    private static GpioPinDigitalOutput sensorTriggerPin;
    private static GpioPinDigitalInput sensorEchoPin;
    static final GpioController gpio;

    static {
        GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
        gpio = GpioFactory.getInstance();
    }


    public static void main(String[] args) {
//        SpringApplication.run(ContainercontroliotsensorApplication.class, args);
        System.out.println("Hello 1");
        new ContainercontroliotsensorApplication().run();
    }

    public void run() {
        sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiBcmPin.GPIO_23); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(RaspiBcmPin.GPIO_24,PinPullResistance.PULL_DOWN); // Echo pin as INPUT

        while(true){
            try {
                sensorTriggerPin.low();
                Thread.sleep(2000);
                sensorTriggerPin.high(); // Make trigger pin HIGH
                Thread.sleep((long) 0.01);// Delay for 10 microseconds
                sensorTriggerPin.low(); //Make trigger pin LOW

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH

                }
                long startTime= System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                System.out.println("Hello 2");
                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW

                }
                System.out.println("Hello 3");
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
                long distance = Math.round((endTime-startTime) * 17150);

                System.out.println("Distance : " + distance + " cm"); //Printing out the distance in cm
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
