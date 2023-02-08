package woowhahan.dgswchat.domain.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowhahan.dgswchat.domain.auth.domain.dto.auth.AuthLoginDto;
import woowhahan.dgswchat.domain.auth.domain.dto.auth.AuthRegisterDto;
import woowhahan.dgswchat.domain.auth.domain.dto.code.CodeResponseDto;
import woowhahan.dgswchat.domain.auth.domain.dto.token.TokenResponseDto;
import woowhahan.dgswchat.domain.user.domain.UserRepository;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.error.CustomError;
import woowhahan.dgswchat.global.error.ErrorCode;
import woowhahan.dgswchat.global.jwt.JwtTokenUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtTokenUtil jwtToken;
    private final UserRepository userRepository;
    private static final Long expTime = 1000 * 3600L;  //1시간

    public User login(@Valid AuthLoginDto authLoginDto) {

        User user = userRepository.findUserByUserId(authLoginDto.getUserId())
                .orElseThrow(() -> new CustomError(ErrorCode.INVALID_USER));

        if (!user.getPassword().equals(authLoginDto.getPassword()))
                throw CustomError.of(ErrorCode.INVALID_USER);

        return user;

    }

    @Transactional
    public void register(@Valid AuthRegisterDto authRegisterDto) {

        if (userRepository.existsByUserId(authRegisterDto.getUserId()))
            throw CustomError.of(ErrorCode.EXISTS_USER);

        if (userRepository.existsByNickname(authRegisterDto.getNickname()))
            throw CustomError.of(ErrorCode.EXISTS_NICKNAME);

        userRepository.save(authRegisterDto.toEntity(authRegisterDto));

    }

    @Transactional
    public CodeResponseDto getCode(User user) {

        String authCode = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now().plusMinutes(30);

        user.generateAuthCode(authCode, time);
        userRepository.save(user);

        return CodeResponseDto.builder()
                .authCode(authCode.toString())
                .build();

    }

    public TokenResponseDto getToken(String authCode) {

        User user = userRepository.findUserByAuthCode(authCode)
                .orElseThrow(() -> new CustomError(ErrorCode.WRONG_AUTH_CODE));

        if (user.getAuthCodeExpDate().isBefore(LocalDateTime.now()))
            throw CustomError.of(ErrorCode.AUTH_CODE_EXPIRED);

        return TokenResponseDto.builder()
                .accessToken(jwtToken.generateAccessToken(user.getNickname()))
                .refreshToken(jwtToken.generateRefreshToken(user.getNickname()))
                .build();

    }

}