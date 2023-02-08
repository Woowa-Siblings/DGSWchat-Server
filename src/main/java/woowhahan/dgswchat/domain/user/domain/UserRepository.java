package woowhahan.dgswchat.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowhahan.dgswchat.domain.user.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);

    boolean existsByNickname(String userId);

    Optional<User> findUserByUserId(String userId);

    Optional<User> findByNickname(String nickname);

    Optional<User>findUserByAuthCode(String authCode);

}
