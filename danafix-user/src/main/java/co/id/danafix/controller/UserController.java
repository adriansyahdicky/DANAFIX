package co.id.danafix.controller;

import co.id.danafix.api.BaseController;
import co.id.danafix.api.JwtUtil;
import co.id.danafix.dto.request.LoginRequestDto;
import co.id.danafix.dto.request.RegisterUserDto;
import co.id.danafix.dto.response.JwtResponseDto;
import co.id.danafix.dto.response.LoginResponseDto;
import co.id.danafix.response.ResponseApi;
import co.id.danafix.service.UserService;
import co.id.danafix.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import static co.id.danafix.constant.ConstantVariable.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register")
    public ResponseApi registerUser(@RequestBody @Valid RegisterUserDto registerUserDto,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }

        userService.Save(registerUserDto);
        return ResponseApi.responseOk("Data Berhasil Disimpan");
    }

    @PostMapping(value = "/login")
    public ResponseApi loginUserDanafix(@RequestBody @Valid LoginRequestDto loginRequestDto,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ErrorUtils.customErrors(bindingResult.getAllErrors());
        }

        LoginResponseDto responselogin = userService.loginUserDanafix(loginRequestDto);

        if(!responselogin.getError().isEmpty()){
            return ResponseApi.responseFailed(responselogin.getError());
        }

        JwtResponseDto jwtResponseDto = JwtResponseDto
                .builder()
                .firstName(responselogin.getFirstName())
                .lastName(responselogin.getLastName())
                .username(responselogin.getUsername())
                .email(responselogin.getEmail())
                .roles(responselogin.getRoles())
                .build();
        String generateToken = JwtUtil.generateTokenJwt
                (UUID.randomUUID().toString(), "Generate Token Login",
                        jwtResponseDto);
        responselogin.setToken(generateToken);
        saveCookie("Authorization", generateToken, JWT_SESSION);

        return ResponseApi.responseOk(responselogin);
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseApi getUserById(@PathVariable(value = "id") Long id){
        return ResponseApi.responseOk(userService.getUserById(id));
    }

}
