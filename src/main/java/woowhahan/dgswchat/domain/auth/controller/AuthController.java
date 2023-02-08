package woowhahan.dgswchat.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowhahan.dgswchat.domain.auth.domain.dto.auth.AuthLoginDto;
import woowhahan.dgswchat.domain.auth.domain.dto.auth.AuthProfileResponseDto;
import woowhahan.dgswchat.domain.auth.domain.dto.auth.AuthRegisterDto;
import woowhahan.dgswchat.domain.auth.domain.dto.code.CodeResponseDto;
import woowhahan.dgswchat.domain.auth.domain.dto.token.AccessTokenDto;
import woowhahan.dgswchat.domain.auth.domain.dto.token.TokenRequestDto;
import woowhahan.dgswchat.domain.auth.domain.dto.token.TokenResponseDto;
import woowhahan.dgswchat.domain.auth.service.AuthService;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.domain.user.service.UserService;
import woowhahan.dgswchat.global.annotation.CheckToken;
import woowhahan.dgswchat.global.response.DataResponse;
import woowhahan.dgswchat.global.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<DataResponse<CodeResponseDto>> login(final @Valid @RequestBody AuthLoginDto authLoginDto) {
        User user = authService.login(authLoginDto);
        CodeResponseDto authCode = authService.getCode(user);
        return DataResponse.ok("로그인 성공", authCode);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(final @Valid @RequestBody AuthRegisterDto authRegisterDto) {
        authService.register(authRegisterDto);
        return DataResponse.ok("회원가입 성공");
    }

    @PostMapping("/token")
    public ResponseEntity<DataResponse<TokenResponseDto>> getAuthToken(final @RequestBody TokenRequestDto tokenRequestDto) {
        return DataResponse.ok("토큰 발급 성공", authService.getToken(tokenRequestDto.getAuthCode()));
    }

    @CheckToken
    @GetMapping("/refreshToken")
    public ResponseEntity<DataResponse<AccessTokenDto>> getAccessToken(@RequestAttribute("accessToken") AccessTokenDto accessTokenDto) {
        return DataResponse.ok("토큰 생성 성공", accessTokenDto);
    }

    @CheckToken
    @GetMapping("/myProfile")
    public ResponseEntity<DataResponse<AuthProfileResponseDto>> getMyProfile(final @RequestAttribute User user) {
        return DataResponse.ok("프로필 조회 성공", userService.getProfile(user));
    }

}
