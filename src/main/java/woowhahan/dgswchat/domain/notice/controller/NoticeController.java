package woowhahan.dgswchat.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowhahan.dgswchat.domain.notice.domain.dto.NoticeResponseDto;
import woowhahan.dgswchat.domain.notice.domain.dto.NoticeStatusDto;
import woowhahan.dgswchat.domain.notice.service.NoticeService;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.annotation.CheckToken;
import woowhahan.dgswchat.global.response.DataResponse;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @CheckToken
    @GetMapping("/check")
    public ResponseEntity<DataResponse<NoticeStatusDto>> getNoticeStatus(@RequestAttribute User user) {
        return DataResponse.ok("알림 여부 조회 성공", noticeService.getNoticeStatus(user));
    }

    @CheckToken
    @GetMapping("/list")
    public ResponseEntity<DataResponse<List<NoticeResponseDto>>> getNoticeList(@RequestAttribute User user) {
        return DataResponse.ok("알림 정보 조회 성공", noticeService.findAllNotice(user));
    }

}
