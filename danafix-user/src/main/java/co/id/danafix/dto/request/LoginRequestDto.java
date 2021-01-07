package co.id.danafix.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto implements Serializable {

    @NotBlank(message = "Username no required")
    private String username;
    @NotBlank(message = "Password no required")
    private String password;

}
