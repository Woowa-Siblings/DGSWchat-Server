package woowhahan.dgswchat.domain.notice.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowhahan.dgswchat.domain.notice.domain.entity.NoticeStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NoticeStatusDto {

    private NoticeStatus noticeStatus;
}
