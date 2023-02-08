package woowhahan.dgswchat.domain.user.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private String userId;

    private String nickname;

    private int grade;

    private int room;

    private int number;

}
