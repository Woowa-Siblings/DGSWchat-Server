package woowhahan.dgswchat.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowhahan.dgswchat.domain.comment.domain.dto.CommentResponseDto;
import woowhahan.dgswchat.domain.comment.domain.dto.CommentSubmitDto;
import woowhahan.dgswchat.domain.comment.service.CommentService;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.annotation.CheckToken;
import woowhahan.dgswchat.global.response.DataResponse;
import woowhahan.dgswchat.global.response.Response;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/read/{postId}")
    public ResponseEntity<DataResponse<List<CommentResponseDto>>> getComment(final @PathVariable Long postId) {
        return DataResponse.ok("게시물 댓글 정보 조회 성공", commentService.findAllComment(postId));
    }

    @CheckToken
    @PostMapping("/submit")
    public ResponseEntity<Response> postComment(
            final @RequestAttribute User user,
            final @Valid @RequestBody CommentSubmitDto commentSubmitDto
    ) {
        commentService.save(user, commentSubmitDto);
        return Response.ok("댓글 등록 성공");
    }

    @CheckToken
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteComment(
            final @RequestAttribute User user,
            final @PathVariable Long id
    ) {
        commentService.deleteById(user, id);
        return Response.ok("댓글 삭제 성공");
    }

}
