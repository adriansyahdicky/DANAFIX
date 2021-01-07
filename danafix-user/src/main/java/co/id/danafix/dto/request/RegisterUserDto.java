package co.id.danafix.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto implements Serializable {
    @NotBlank(message = "First Name cannot is required")
    private String fisrt_name;
    @NotBlank(message = "Last Name cannot is required")
    private String last_name;
    @NotBlank(message = "Username cannot is required")
    private String username;
    @NotBlank(message = "Email cannot is required")
    @Email(message = "Email Not Valid")
    private String email;
    @NotBlank(message = "Password cannot is required")
    private String password;
    @NotNull(message = "Hak Akses cannot is required")
    private Long[] role;
}
