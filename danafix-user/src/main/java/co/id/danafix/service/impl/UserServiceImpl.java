package co.id.danafix.service.impl;

import co.id.danafix.api.CustomCrypto;
import co.id.danafix.dto.request.LoginRequestDto;
import co.id.danafix.dto.request.RegisterUserDto;
import co.id.danafix.dto.response.LoginResponseDto;
import co.id.danafix.entity.Role;
import co.id.danafix.entity.User;
import co.id.danafix.exception.ResourceNotFoundException;
import co.id.danafix.repository.RoleRepository;
import co.id.danafix.repository.UserRepository;
import co.id.danafix.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void Save(RegisterUserDto registerUserDto) {

        CustomCrypto customCrypto = new CustomCrypto();

        User user = new User(
                registerUserDto.getFisrt_name(),
                registerUserDto.getLast_name(),
                registerUserDto.getEmail(),
                registerUserDto.getUsername(),
                customCrypto.encryptPasswordUser(registerUserDto.getPassword()),
                true
        );

        for (Long roleId : registerUserDto.getRole()) {
            Optional<Role> role = Optional.ofNullable(roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("ID "+roleId +" Not Found In Database")));

            user.getRoles().add(role.get());
        }

        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID "+id +" Not Found In Database")));
        return user.get();
    }

    @Override
    public LoginResponseDto loginUserDanafix(LoginRequestDto loginRequestDto) {
        CustomCrypto customCrypto = new CustomCrypto();
        LoginResponseDto responseDto = new LoginResponseDto();
        String cek_password = customCrypto.decryptPasswordUser(loginRequestDto.getPassword());

        if(!cek_password.equals(loginRequestDto.getPassword())){
            responseDto.setError("Password anda salah !");
            return responseDto;
        }

        User user = userRepository.findByUsername(loginRequestDto.getUsername()).get();
        if(!user.getEnabled()){
            responseDto.setError("User anda sudah tidak aktif, silahkan hubungi tim terkait");
            return responseDto;
        }

        BeanUtils.copyProperties(user, responseDto);

        return responseDto;
    }
}
