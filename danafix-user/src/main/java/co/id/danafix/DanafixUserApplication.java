package co.id.danafix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
		"co.id.danafix"
})
@EnableDiscoveryClient
public class DanafixUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanafixUserApplication.class, args);
	}

}
