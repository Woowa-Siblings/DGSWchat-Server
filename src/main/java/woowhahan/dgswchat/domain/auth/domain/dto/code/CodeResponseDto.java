package woowhahan.dgswchat.domain.auth.domain.dto.code;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeResponseDto {

    private String authCode;

}
