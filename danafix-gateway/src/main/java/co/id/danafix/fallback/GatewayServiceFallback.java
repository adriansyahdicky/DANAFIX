package co.id.danafix.fallback;

import co.id.danafix.config.ApiGatewayClientServiceResponse;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class GatewayServiceFallback implements FallbackProvider {

    //falback ini untuk keseluruhan service yang ada di
    //micorservices danafix untuk mengecek
    //service sudah ada atau belum / sudah up

    private static final String DEFAULT_MESSAGE = "SERVICE TIDAK TEREDIA ATAU BELUM TERDAFTAR";

    @Override
    public String getRoute() {
        return "*"; // atau di return kan null aja
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
