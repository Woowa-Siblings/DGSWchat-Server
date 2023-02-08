package woowhahan.dgswchat.domain.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import woowhahan.dgswchat.domain.comment.domain.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long userId;
    private Long postId;
    private Long commentId;
    private String userName;
    private int grade;
    private int room;
    private int number;
    private LocalDateTime createDateTime;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();
        this.userName = comment.getUser().getNickname();
        this.grade = comment.getUser().getGrade();
        this.room = comment.getUser().getRoom();
        this.number = comment.getUser().getNumber();
        this.createDateTime = comment.getCreatedDate();
        this.content = comment.getContent();
    }

}
