package woowhahan.dgswchat.domain.auth.domain.dto.token;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {

    private String accessToken;

    private String refreshToken;

}
