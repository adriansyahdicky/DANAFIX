package co.id.danafix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = {"co.id.danafix"})
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
public class DanafixGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanafixGatewayApplication.class, args);
	}

}
