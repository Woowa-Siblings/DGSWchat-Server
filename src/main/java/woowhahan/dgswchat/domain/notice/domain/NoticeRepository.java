package woowhahan.dgswchat.domain.notice.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import woowhahan.dgswchat.domain.notice.domain.entity.Notice;
import woowhahan.dgswchat.domain.user.domain.entity.User;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @EntityGraph(attributePaths = {"sendUser", "post", "comment"})
    List<Notice> findAllByWriterUser(User writerUser, Sort sort);
}
