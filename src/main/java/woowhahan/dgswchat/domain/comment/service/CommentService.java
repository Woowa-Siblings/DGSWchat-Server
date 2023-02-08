package woowhahan.dgswchat.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowhahan.dgswchat.domain.comment.domain.CommentRepository;
import woowhahan.dgswchat.domain.comment.domain.dto.CommentResponseDto;
import woowhahan.dgswchat.domain.comment.domain.dto.CommentSubmitDto;
import woowhahan.dgswchat.domain.comment.domain.entity.Comment;
import woowhahan.dgswchat.domain.notice.service.NoticeService;
import woowhahan.dgswchat.domain.post.domain.PostRepository;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.error.CustomError;
import woowhahan.dgswchat.global.error.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NoticeService noticeService;

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComment(final Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomError(ErrorCode.NOT_FOUND));
        return commentRepository.findAllByPost(post).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(User user, CommentSubmitDto commentSubmitDto) {
        Post post = postRepository.findById(commentSubmitDto.getPostId())
                .orElseThrow(() -> new CustomError(ErrorCode.NOT_FOUND));
        Comment comment = commentRepository.save(commentSubmitDto.toEntity(user, post, commentSubmitDto));
        if(!user.getId().toString().equals(post.getUser().getId().toString())) noticeService.submitNotice(user, post, comment);
    }

    @Transactional
    public void deleteById(User user, Long id) {
        if (!(commentRepository.existsById(id))) {
            throw CustomError.of(ErrorCode.USER_CONFLICT);
        }
        commentRepository.deleteById(id);
    }

}
