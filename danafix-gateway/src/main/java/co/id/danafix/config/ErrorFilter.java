package co.id.danafix.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ErrorFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        boolean isFilterError = RequestContext.getCurrentContext().containsKey("throwable");
        return isFilterError;
    }

    @Override
    public Object run() throws ZuulException {
        log.debug("--enter into gateway error filter--");
        RequestContext requestContext = RequestContext.getCurrentContext();
        try{
            Throwable throwable = getOriginalException(requestContext.getThrowable());
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType("application/json; charset=utf8");
            PrintWriter writer = null;
            try{
                response.setStatus(500);
                writer = response.getWriter();
                writer.println("{\"message\" : \"something error\"}");
            }catch (IOException ee){
                ee.getMessage();
            }finally {
                if (writer != null)
                    writer.close();
            }
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    private Throwable getOriginalException(Throwable e){
        e = e.getCause();
        while (e.getCause() != null)
            e = e.getCause();
        return e;
    }
}
