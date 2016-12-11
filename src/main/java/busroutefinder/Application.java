package busroutefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("ERROR: Supply data route file.");
        } else {
            System.setProperty("routeFile", args[0]);
            SpringApplication.run(Application.class, args);
        }
    }
}