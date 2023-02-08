package woowhahan.dgswchat.domain.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import woowhahan.dgswchat.domain.comment.domain.entity.Comment;
import woowhahan.dgswchat.domain.post.domain.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

}
