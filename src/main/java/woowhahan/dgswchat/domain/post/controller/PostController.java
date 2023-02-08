package woowhahan.dgswchat.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowhahan.dgswchat.domain.post.domain.entity.Tag;
import woowhahan.dgswchat.domain.post.domain.dto.PostResponseDto;
import woowhahan.dgswchat.domain.post.domain.dto.PostSubmitDto;
import woowhahan.dgswchat.domain.post.service.PostService;
import woowhahan.dgswchat.domain.user.domain.entity.User;
import woowhahan.dgswchat.global.annotation.CheckToken;
import woowhahan.dgswchat.global.response.DataResponse;
import woowhahan.dgswchat.global.response.Response;

import java.util.List;


@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @CheckToken
    @PostMapping("/submit")
    public ResponseEntity<Response> submit(@RequestAttribute User user, @RequestBody PostSubmitDto postSubmitDto) {
        postService.submit(postSubmitDto, user);
        return Response.ok("게시물 등록 성공");
    }

    @GetMapping("/read-all")
    public ResponseEntity<DataResponse<List<PostResponseDto>>> readAll() {
        return DataResponse.ok("전체 게시물 조회 성공", postService.findPostAll());
    }

    @GetMapping("/read-one/{postId}")
    public ResponseEntity<DataResponse<PostResponseDto>> readOne(@PathVariable("postId") Long postId) {
        return DataResponse.ok("단일 게시물 조회 성공", postService.findPostOne(postId));
    }

    @GetMapping("/read-all/{tag}")
    public ResponseEntity<DataResponse<List<PostResponseDto>>> readAllByTag(@PathVariable("tag") Tag tag) {
        return DataResponse.ok("태그로 게시물 조회 성공", postService.findPostByTag(tag));
    }

    @CheckToken
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Response> delete(@RequestAttribute User user, @PathVariable("postId")Long postId) {
        postService.delete(postId, user);
        return Response.ok("게시물 삭제 성공");
    }

    @GetMapping("/search/{keyWord}")
    public ResponseEntity<DataResponse<List<PostResponseDto>>> search(@PathVariable("keyWord")String keyWord) {
        return DataResponse.ok("게시물 검색 성공", postService.search(keyWord));
    }
}