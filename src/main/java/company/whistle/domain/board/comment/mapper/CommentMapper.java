package company.whistle.domain.board.comment.mapper;

import company.whistle.domain.board.comment.dto.CommentPatchDto;
import company.whistle.domain.board.comment.dto.CommentPostDto;
import company.whistle.domain.board.comment.dto.CommentResponseDto;
import company.whistle.domain.board.comment.entity.Comment;
import company.whistle.domain.board.content.entity.Content;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentPostDto requestBody);

    Comment commentPatchDtoToComment(CommentPatchDto requestBody);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "content.contentId", target = "contentId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "content.title", target = "title")
    CommentResponseDto commentToCommentResponseDto(Comment comment);
//    default CommentResponseDto commentToCommentResponseDto(Comment comment){
//        return CommentResponseDto.builder()
//                .contentId(content.getContentId())
//                .userId(user.getUserId())
//                .name(user.getName())
//                .title(content.getTitle())
//                .commentId(comment.getCommentId())
//                .comment(comment.getComment())
//                .createdAt(comment.getCreatedAt())
//                .modifiedAt(comment.getModifiedAt())
//                .build();
//    }
    List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comment);
    List<CommentResponseDto> contentCommentsToCommentResponseDtos(List<Comment> comment);
}