package co.id.danafix.api;

import co.id.danafix.response.ResponseInfoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

import static co.id.danafix.constant.ConstantVariable.LOCATION_PATH;
import static co.id.danafix.constant.ConstantVariable.JWT_SESSION;
import static co.id.danafix.constant.ConstantVariable.REQ_HEADER;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private HttpServletResponse servletResponse;

    public void saveCookie(String cookieName, String valueCookie, Integer expiredCookie){
        Cookie cookie = new Cookie(cookieName, valueCookie);
        cookie.setPath(LOCATION_PATH);
        cookie.setMaxAge(JWT_SESSION);
        servletResponse.addHeader("Authorization", valueCookie);
        servletResponse.addCookie(cookie);
    }

    public void destroyCookie() {
        Cookie cookie = new Cookie(REQ_HEADER, null);
        cookie.setPath(LOCATION_PATH);
        cookie.setMaxAge(0);
        servletResponse.addCookie(cookie);
    }

    /** get header from frontend **/
    public ResponseInfoUser getUserIdFromHeader(){
        if (log.isInfoEnabled()){
            log.debug("{\"method\" : \"getUserIdFromHeader\", " +
                    "\"info\" : \"get information user id from header\"," +
                    "\"data\" : \"id : \" "+servletRequest.getHeader("id")+", " +
                    "\"data\" : \"fisrtName : \" "+servletRequest.getHeader("firstName")+", " +
                    "\"data\" : \"lastName\" : "+servletRequest.getHeader("lastName")+", " +
                    "\"data\" : \"email\" : "+servletRequest.getHeader("email")+", " +
                    "\"data\" : \"username\" : "+servletRequest.getHeader("username")+"}");
        }
        ResponseInfoUser infoUser = new ResponseInfoUser();

        infoUser.setId(Long.valueOf(servletRequest.getHeader("id")));
        infoUser.setFirstName(servletRequest.getHeader("firstName"));
        infoUser.setLastName(servletRequest.getHeader("lastName"));

        return infoUser;
    }

    public String getAllCookies(){
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null){
            if (log.isInfoEnabled()){
                log.debug("COOKIES : "+ Arrays.stream(cookies).map(c -> c.getName() + "=" +c.getValue()).collect(Collectors.joining(", ")));
            }
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }
        return "No Cookies";
    }

}
