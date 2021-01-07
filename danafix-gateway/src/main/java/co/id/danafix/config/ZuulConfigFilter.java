package co.id.danafix.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Configuration
public class ZuulConfigFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(ZuulConfigFilter.class);

    @Autowired
    private FilterConfig filterConfig;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        String[] ignoreArrayFilter = filterConfig.getIgnores();
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        HttpServletResponse httpServletResponse = requestContext.getResponse();
        boolean flag = false;
        System.out.println("####### Request incoming ######");
        System.out.println(String.format("%s request to %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString()));
        try {
            System.out.println("Request Payload : "+httpServletRequest.getReader().lines().collect(Collectors.joining()));
            System.out.println("Token From Header : "+getTokenFromHeader(httpServletRequest));
            String uri = httpServletRequest.getRequestURL().toString();
            System.out.println("method : "+httpServletRequest.getMethod());
            System.out.println("uri : "+uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //cek url yang tidak menggunakan token
        for(int i=0; i<ignoreArrayFilter.length; i++){
            if(httpServletRequest.getRequestURL().toString().contains(ignoreArrayFilter[i])){
                flag = true;
                break;
            }
        }

        if(flag){
            return null;
        }

        String compareTokenFromHeader = getTokenFromHeader(httpServletRequest);

        if (compareTokenFromHeader == null){
            System.out.println("token cookies is null");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            httpServletResponse.setContentType("application/json");
            requestContext.setResponseBody("{\"code\" : \"401\", \"message\" : \"Token Kemungkinan expired\"}");
            requestContext.setResponse(httpServletResponse);
            return null;
        }

        return null;
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest){
        if (log.isInfoEnabled()){
            log.debug("{\"getTokenFromHeader\" : \" "+httpServletRequest.getHeader("Authorization")+" \"}");
        }
        return httpServletRequest.getHeader("Authorization");
    }
}
