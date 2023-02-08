package woowhahan.dgswchat.domain.comment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentUpdateDto {

    @NotNull(message = "댓글 ID가 Null일 수 없습니다")
    private Long commentId;

    @NotBlank(message = "내용이 Null이거나 ' '일 수 없습니다")
    private String content;

}
