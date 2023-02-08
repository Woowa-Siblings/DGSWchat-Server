package woowhahan.dgswchat.domain.post.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import woowhahan.dgswchat.domain.post.domain.entity.Post;
import woowhahan.dgswchat.domain.post.domain.entity.Tag;
import woowhahan.dgswchat.domain.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user u")
    List<Post> findAll(Sort sort);

    @Query("select p from Post p join fetch p.user u where p.id=:id")
    Optional<Post> findById(Long id);

    @Query("select p from Post p join fetch p.user u where p.tag=:tag")
    List<Post> findAllByTag(Tag tag, Sort sort);

    List<Post> findByTitleContains(String keyword, Sort sort);

    List<Post> findAllByUser(User user);

}
