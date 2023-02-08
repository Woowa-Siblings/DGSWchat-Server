package woowhahan.dgswchat.domain.post.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.post.domain.entity.Tag;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostResponseDto {

    private String title;
    private Tag tag;
    private LocalDateTime createDateTime;
    private String content;
    private Long postId;
    private String userName;
    private int grade;
    private int room;
    private int number;
    private Long author;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.tag = post.getTag();
        this.createDateTime = post.getCreatePostDateTime();
        this.content = post.getContent();
        this.postId = post.getId();
        this.userName = post.getUser().getNickname();
        this.grade = post.getUser().getGrade();
        this.room = post.getUser().getRoom();
        this.number = post.getUser().getNumber();
        this.author = post.getUser().getId();
    }
}
