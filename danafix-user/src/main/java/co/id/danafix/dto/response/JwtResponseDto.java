package co.id.danafix.dto.response;

import co.id.danafix.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
