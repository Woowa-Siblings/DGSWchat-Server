package woowhahan.dgswchat.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "User")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private int room;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime joinedDate;

    private String authCode;

    private LocalDateTime authCodeExpDate;

    @Builder
    public User(String userId, String nickname, String password, int grade, int room, int number) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.grade = grade;
        this.room = room;
        this.number = number;
    }

    public void generateAuthCode(String authCode, LocalDateTime expDate) {
        this.authCode = authCode;
        this.authCodeExpDate = expDate;
    }

}