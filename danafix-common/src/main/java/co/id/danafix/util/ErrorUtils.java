package co.id.danafix.util;

import co.id.danafix.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ErrorUtils {
    public static ResponseApi customErrors(List<ObjectError> errors){

        ResponseApi globalResponse = new ResponseApi();
        List<HashMap<String, String>> response_message = new ArrayList<>();
        try{
            for(ObjectError objectError : errors){
                if(objectError instanceof FieldError){
                    FieldError fieldError = (FieldError) objectError;
                    HashMap<String, String> mapError= new HashMap<>();
                    mapError.put(fieldError.getField(), fieldError.getDefaultMessage());
                    response_message.add(mapError);
                }
            }
            globalResponse.setData(response_message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseApi.responseOk(globalResponse);
    }
}
