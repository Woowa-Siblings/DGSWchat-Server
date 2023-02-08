package woowhahan.dgswchat.domain.notice.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowhahan.dgswchat.domain.notice.domain.entity.Notice;
import woowhahan.dgswchat.domain.notice.domain.entity.NoticeStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeResponseDto {

    private Long postId;
    private String senderName;
    private String commentContent;
    private LocalDateTime createDateTime;
    private NoticeStatus noticeStatus;

    public NoticeResponseDto(Notice notice) {
        this.postId = notice.getPost().getId();
        this.senderName = notice.getSendUser().getNickname();
        this.commentContent = notice.getComment().getContent();
        this.createDateTime = notice.getCreatedDate();
        this.noticeStatus = notice.getNoticeStatus();
    }
}
