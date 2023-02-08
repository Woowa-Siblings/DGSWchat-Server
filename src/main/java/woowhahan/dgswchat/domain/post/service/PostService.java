package woowhahan.dgswchat.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.post.domain.PostRepository;
import woowhahan.dgswchat.domain.post.domain.entity.Tag;
import woowhahan.dgswchat.domain.post.domain.dto.PostResponseDto;
import woowhahan.dgswchat.domain.post.domain.dto.PostSubmitDto;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.error.CustomError;
import woowhahan.dgswchat.global.error.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void submit(PostSubmitDto postSubmitDto, User user) {
        postRepository.save(postSubmitDto.toEntity(postSubmitDto, user));
    }

    public List<PostResponseDto> findPostAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(post -> new PostResponseDto(post))
                .collect(Collectors.toList());
    }

    public PostResponseDto findPostOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.NOT_FOUND);
        });
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> findPostByTag(Tag tag) {
        return postRepository.findAllByTag(tag, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map( post -> new PostResponseDto(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long postId, User user) {
        postRepository.findById(postId).ifPresentOrElse(
                post -> {
                    User postUser = post.getUser();
                    if(user.getId().equals(postUser.getId())) {
                        postRepository.delete(post);
                    } else throw CustomError.of(ErrorCode.WRONG_USER);
                },
                () -> { throw CustomError.of(ErrorCode.NOT_FOUND);});
    }

    public List<PostResponseDto> search(String keyWord) {
        return postRepository.findByTitleContains(keyWord, Sort.by(Sort.Direction.DESC, "id") ).stream()
                .map(post -> new PostResponseDto(post)).collect(Collectors.toList());
    }
}
