package woowhahan.dgswchat.domain.post.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woowhahan.dgswchat.domain.comment.domain.entity.Comment;
import woowhahan.dgswchat.domain.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createPostDateTime;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @Builder
    public Post(User user, Tag tag, String title, String content) {
        this.user = user;
        this.tag = tag;
        this.title = title;
        this.content = content;
    }

}