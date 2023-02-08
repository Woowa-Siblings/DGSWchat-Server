package woowhahan.dgswchat.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowhahan.dgswchat.domain.auth.domain.dto.auth.AuthProfileResponseDto;
import woowhahan.dgswchat.domain.post.domain.PostRepository;
import woowhahan.dgswchat.domain.post.domain.dto.PostResponseDto;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.user.domain.UserRepository;
import woowhahan.dgswchat.domain.user.domain.dto.UserResponseDto;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.error.CustomError;
import woowhahan.dgswchat.global.error.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.NOT_FOUND);
        });
    }

    public AuthProfileResponseDto getProfile(User user) {

        List<PostResponseDto> post = postRepository.findAllByUser(user).stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .grade(user.getGrade())
                .room(user.getRoom())
                .number(user.getNumber())
                .build();

        return AuthProfileResponseDto.builder()
                .user(userResponseDto)
                .post(post)
                .build();
    }

}
