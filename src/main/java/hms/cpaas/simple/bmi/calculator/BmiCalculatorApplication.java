package hms.cpaas.simple.bmi.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class BmiCalculatorApplication {
    public static void main(String... args) {
        SpringApplication.run(BmiCalculatorApplication.class, args);
    }
}
