package woowhahan.dgswchat.domain.comment.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woowhahan.dgswchat.domain.notice.domain.entity.Notice;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.user.domain.entity.User;

import java.time.LocalDateTime;

@Entity(name = "Comment")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL)
    private Notice notice;

    @Builder
    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

}
