package co.id.danafix.service;

import co.id.danafix.dto.request.LoginRequestDto;
import co.id.danafix.dto.request.RegisterUserDto;
import co.id.danafix.dto.response.LoginResponseDto;
import co.id.danafix.entity.User;

public interface UserService {
    void Save(RegisterUserDto registerUserDto);
    User getUserById(Long id);
    LoginResponseDto loginUserDanafix(LoginRequestDto loginRequestDto);
}
