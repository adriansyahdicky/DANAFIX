package co.id.danafix.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApiGatewayClientServiceResponse implements ClientHttpResponse {

    private HttpStatus status;
    private String message;

    public ApiGatewayClientServiceResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return status;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return status.value();
    }

    @Override
    public String getStatusText() throws IOException {
        return status.getReasonPhrase();
    }

    @Override
    public void close() {

    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(message.getBytes());
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public void setStatus(HttpStatus status){
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
