import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

/**
 * Spring Boot Main Method.
 *
 * @author Germán González
 * @version 1.0
 * @since 2024-06-05
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.example.*")
public class Application {

    /**
     * Method that launches the Application.
     *
     * @param args Shell Arguments to be passed to the invocation
     *
     */
    public static void main(String[] args) {
        run(Application.class, args);
    }
}
