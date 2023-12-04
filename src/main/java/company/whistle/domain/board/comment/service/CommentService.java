package company.whistle.domain.board.comment.service;

import company.whistle.domain.board.comment.repository.CommentRepository;
import company.whistle.domain.board.comment.entity.Comment;
import company.whistle.domain.board.content.entity.Content;
import company.whistle.domain.board.content.service.ContentService;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ContentService contentService;

    /*
     * 댓글 등록
     */
    public Comment createComment(Comment comment, Long contentId, Long userId) {
        try {
            if (userId == null || contentId == null) {
                log.info("userId: {}", userId);
                log.info("teamId: {}", contentId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Content content = contentService.findContent(contentId);
            User user = userService.findUser(userId);

            comment.setUser(user);
            comment.setContent(content);
            commentRepository.save(comment);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.COMMENT_NOT_CREATED);
        }
        return comment;
    }

    /*
     * 댓글 수정
     */
    public Comment updateComment(Comment comment, Long commentId) {
        try {
            if (commentId == null) {
                log.info("commentId: {}", commentId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Comment findComment = findVerifiedComment(commentId);
            User writer = userService.findUser(findComment.getUser().getUserId()); // 작성자 찾기
            if (!Objects.equals(userService.getLoginUser().getUserId(), writer.getUserId())) { // 작성자와 로그인한 사람이 다를 경우
                throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
            }

            Optional.ofNullable(comment.getComment())
                    .ifPresent(findComment::setComment);
            commentRepository.save(findComment);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.COMMENT_NOT_PATCHED);
        }
        return comment;
    }

    /*
     * 댓글 단건 조회
     */
    public Comment findComment(long commentId) {
        return findVerifiedComment(commentId);
    }

    /*
     * 댓글 전체 조회
     */
    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size,
                Sort.by("commentId").descending()));
    }

    /*
     * 게시글 Id 단위 댓글 조회
     */
    public List<Comment> findContentComments(int contentId) {
        return findVerifiedContentComments(contentId);
    }

    /*
     * 댓글 삭제
     */
    public void deleteComment(long commentId) {
        try {
            Comment findComment = findVerifiedComment(commentId);

            User writer = userService.findUser(findComment.getUser().getUserId()); // 작성자 찾기
            if (userService.getLoginUser().getUserId() != writer.getUserId()) { // 작성자와 로그인한 사람이 다를 경우
                throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
            }

            commentRepository.delete(findComment);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessLogicException(Exceptions.CONTENT_NOT_DELETED);
        }
    }

    /*
     * 댓글 존재 유무 확인
     */
    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.COMMENT_NOT_FOUND));
    }

    /*
     * 게시글 존재 유무 확인
     */
    public List<Comment> findVerifiedContentComments(long contentId) {
        List<Comment> findContentComments = commentRepository.findAllByContentId(contentId);
        if(findContentComments==null){
            throw new BusinessLogicException(Exceptions.COMMENT_NOT_FOUND);
        }
        return findContentComments;
    }
}