package company.whistle.domain.board.comment.controller;

import company.whistle.domain.board.comment.dto.CommentPostDto;
import company.whistle.domain.board.comment.dto.CommentResponseDto;
import company.whistle.domain.board.comment.dto.CommentPatchDto;
import company.whistle.domain.board.comment.entity.Comment;
import company.whistle.domain.board.comment.mapper.CommentMapper;
import company.whistle.domain.board.comment.service.CommentService;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.global.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    /*
     * 댓글 생성
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> postComment(@Valid @RequestBody CommentPostDto requestBody ){
        Comment comment = commentService.createComment(
                commentMapper.commentPostDtoToComment(requestBody), requestBody.getContentId(), requestBody.getUserId()
        );
        CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(comment);
        log.info("COMMENT POST COMPLETE: {}",commentResponseDto.toString());

        return ResponseEntity.ok(commentResponseDto);
    }

    /*
     * 댓글 수정
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> patchComment(@Valid @RequestBody CommentPatchDto requestBody,
                                       @PathVariable("commentId") @Positive Long commentId) {
        Comment comment = commentService.updateComment(
                commentMapper.commentPatchDtoToComment(requestBody), commentId
        );
        comment.setCommentId(commentId);

        CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(comment);
        log.info("COMMENT PATCH COMPLETE: {}",commentResponseDto.toString());

        return ResponseEntity.ok(commentResponseDto);
    }

    /*
     * 댓글 단건 조회
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable("commentId") @Positive Long commentId){
        Comment comment = commentService.findComment(commentId);
        CommentResponseDto commentResponse = commentMapper.commentToCommentResponseDto(comment);

        return ResponseEntity.ok(commentResponse);
    }

    /*
     * 특정 게시글 ID에 있는 댓글 전체 조회
     */
    @GetMapping("/contents/{contentId}")
    public ResponseEntity<List<CommentResponseDto>> getContentComments(@PathVariable("contentId") @Positive int contentId) {
        List<Comment> comments = commentService.findContentComments(contentId);
        List<CommentResponseDto> commentResponseDtos = commentMapper.contentCommentsToCommentResponseDtos(comments);
        return ResponseEntity.ok(commentResponseDtos);
    }

    /*
     * 댓글 전체 조회
     */
    @GetMapping
    public ResponseEntity<MultiResponseDto<CommentResponseDto>> getComments(@Positive @RequestParam("page") int page,
                                      @Positive @RequestParam("size") int size) {
        Page<Comment> pageComments = commentService.findComments(page - 1, size);
        List<Comment> comments = pageComments.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(commentMapper.commentsToCommentResponseDtos(comments),
                        pageComments),
                HttpStatus.OK);
    }
    
    /*
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") @Positive Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
