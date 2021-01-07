package co.id.danafix.dto.response;

import co.id.danafix.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
    private String error;
    private String token;
}
