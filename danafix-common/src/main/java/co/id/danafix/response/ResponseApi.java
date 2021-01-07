package co.id.danafix.response;

import java.io.Serializable;
import static co.id.danafix.constant.ConstantVariable.*;

public class ResponseApi implements Serializable {

    private int code;
    private String message;
    private Object data;

    public static ResponseApi responseOk(Object data){
        return new ResponseApi(200, SUCCESS, data);
    }

    public static ResponseApi responseOkButNull(String message){
        return new ResponseApi(200, message, null);
    }

    public static ResponseApi responseConflict(Object data){
        return new ResponseApi(405, CONFLICT, data);
    }

    public static ResponseApi responseBadRequest(){
        return new ResponseApi(400, BADREQUEST, null);
    }

    public static ResponseApi responseFailed(String message){
        return new ResponseApi(400, message, null);
    }

    public static ResponseApi responseNotFound(Object data){
        return new ResponseApi(404, NOTFOUND, data);
    }

    public static ResponseApi responseError(String message){
        return new ResponseApi(500, message, new Object());
    }

    public ResponseApi(){}

    public ResponseApi(int code, String message, Object data){
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
