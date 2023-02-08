package woowhahan.dgswchat.domain.comment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import woowhahan.dgswchat.domain.comment.domain.entity.Comment;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.user.domain.entity.User;

@Getter
@Builder
public class CommentSubmitDto {

    @NotNull(message = "게시물 ID가 Null일 수 없습니다")
    private Long postId;

    @NotBlank(message = "내용이 Null이거나 ' '일 수 없습니다")
    private String content;

    public Comment toEntity(User user, Post post, CommentSubmitDto commentSubmitDto) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(commentSubmitDto.getContent())
                .build();
    }

}
