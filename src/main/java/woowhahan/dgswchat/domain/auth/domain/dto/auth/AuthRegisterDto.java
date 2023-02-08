package woowhahan.dgswchat.domain.auth.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import woowhahan.dgswchat.domain.user.domain.entity.User;

@Getter
public class AuthRegisterDto {

    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]*", message = "정규표현식이 맞지 않습니다")
    @Size(min = 4, max = 20, message = "아이디는 4글자 ~ 20글자 사이여야 합니다")
    @NotBlank(message = "아이디가 공백이거나 Null입니다")
    private String userId;

    @Size(min = 1, max = 32, message = "닉네임은 한글 1글자 ~ 16글자, 영어 1글자 ~ 32글자 사이여야 합니다")
    @NotBlank(message = "닉네임이 공백이거나 Null입니다")
    private String nickname;

    @Size(min = 128, max = 128, message = "패스워드가 SHA-512으로 암호화 되어있지 않습니다")
    @NotBlank(message = "패스워드가 공백이거나 Null입니다")
    private String password;

    private int grade;

    private int room;

    private int number;

    public User toEntity(AuthRegisterDto authRegisterDto) {
        return User.builder()
                .userId(authRegisterDto.userId)
                .nickname(authRegisterDto.nickname)
                .password(authRegisterDto.password)
                .grade(authRegisterDto.grade)
                .room(authRegisterDto.room)
                .number(authRegisterDto.number)
                .build();
    }

}
