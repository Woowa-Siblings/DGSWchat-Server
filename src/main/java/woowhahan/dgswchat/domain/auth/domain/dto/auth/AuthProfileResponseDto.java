package woowhahan.dgswchat.domain.auth.domain.dto.auth;

import lombok.Builder;
import lombok.Getter;
import woowhahan.dgswchat.domain.post.domain.dto.PostResponseDto;
import woowhahan.dgswchat.domain.user.domain.dto.UserResponseDto;

import java.util.List;

@Getter
@Builder
public class AuthProfileResponseDto {

    private UserResponseDto user;

    private List<PostResponseDto> post;

}
