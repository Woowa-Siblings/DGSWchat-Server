package woowhahan.dgswchat.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류"),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "아이디 혹은 패스워드가 틀렸습니다"),
    EXISTS_USER(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."),
    EXISTS_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    WRONG_AUTH_CODE(HttpStatus.FORBIDDEN, "인증 코드가 올바르지 않습니다"),
    AUTH_CODE_EXPIRED(HttpStatus.FORBIDDEN, "인증 코드가 만료 되었습니다"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 토큰"),
    TOKEN_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "토큰이 입력되지 않았습니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰"),
    WRONG_USER(HttpStatus.FORBIDDEN, "잘못된 사용자"),
    USER_CONFLICT(HttpStatus.CONFLICT, "유저를 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
