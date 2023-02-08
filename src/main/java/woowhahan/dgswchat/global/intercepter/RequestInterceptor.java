package woowhahan.dgswchat.global.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import woowhahan.dgswchat.domain.auth.domain.dto.token.AccessTokenDto;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.annotation.CheckToken;
import woowhahan.dgswchat.global.error.CustomError;
import woowhahan.dgswchat.global.error.ErrorCode;
import woowhahan.dgswchat.global.jwt.JwtTokenUtil;
import woowhahan.dgswchat.global.jwt.TokenType;

import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (!(handlerMethod.getMethod().isAnnotationPresent(CheckToken.class))) {
            return true;
        }

        try {
            String token = getTokenOfRequest(request).split(" ")[1];

            TokenType tokenType = jwtTokenUtil.checkTokenType(token);
            User user = jwtTokenUtil.getUserByToken(token);

            if (checkTokenType(request, tokenType, user) == true) {
                return true;
            }

            request.setAttribute("user", user);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw CustomError.of(ErrorCode.TOKEN_NOT_PROVIDED);
        }

        return true;
    }

    private String getTokenOfRequest(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value != null) {
                return value;
            }
        }

        return Strings.EMPTY;
    }

    private boolean checkTokenType(HttpServletRequest request, TokenType tokenType, User user) {
        if (tokenType.equals(TokenType.REFRESH_TOKEN)) {

            String accessToken = jwtTokenUtil.generateAccessToken(user.getNickname());
            AccessTokenDto accessTokenDto = new AccessTokenDto(accessToken);
            request.setAttribute("accessToken", accessTokenDto);

            return true;
        } else return false;
    }
}