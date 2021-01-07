package co.id.danafix.fallback;

import co.id.danafix.config.ApiGatewayClientServiceResponse;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DanafixUserFallback implements FallbackProvider {

    private static String SERVICE_USER = "danafix-user";

    private static String DEFAULT_MESSAGE = "{\"code\" : \"400\", " +
            "\"message\" : \"sepertinya service down atau belum up, harap tunggu beberapa saat\"," +
            "\"date\" : \""+new Date()+"\" }";

    @Override
    public String getRoute() {
        return SERVICE_USER;
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException){
            return new ApiGatewayClientServiceResponse(HttpStatus.GATEWAY_TIMEOUT, DEFAULT_MESSAGE);
        }else{
            return new ApiGatewayClientServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE);
        }
    }

}
