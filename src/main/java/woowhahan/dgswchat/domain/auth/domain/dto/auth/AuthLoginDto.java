package woowhahan.dgswchat.domain.auth.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthLoginDto {

    @NotBlank(message = "아이디가 공백이거나 Null입니다")
    private String userId;

    @Size(min = 128, max = 128, message = "패스워드가 SHA-512으로 암호화 되어있지 않습니다")
    @NotBlank(message = "패스워드가 공백이거나 Null입니다")
    private String password;

}
