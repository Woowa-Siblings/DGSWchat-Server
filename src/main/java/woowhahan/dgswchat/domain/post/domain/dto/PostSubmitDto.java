package woowhahan.dgswchat.domain.post.domain.dto;

import lombok.Getter;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.post.domain.entity.Tag;
import woowhahan.dgswchat.domain.user.domain.entity.User;

@Getter
public class PostSubmitDto {

    private String title;

    private Tag tag;

    private String content;

    public Post toEntity(PostSubmitDto postSubmitDto, User user) {
        return Post.builder()
                .user(user)
                .tag(postSubmitDto.getTag())
                .title(postSubmitDto.getTitle())
                .content(postSubmitDto.getContent())
                .build();
    }
}