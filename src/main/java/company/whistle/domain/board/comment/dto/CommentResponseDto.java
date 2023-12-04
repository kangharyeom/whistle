package company.whistle.domain.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class CommentResponseDto {
    private Long commentId;
    private Long userId;
    private Long contentId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String name;
    private String title;

    @Override
    public String toString() {
        return "CommentResponseDto{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
