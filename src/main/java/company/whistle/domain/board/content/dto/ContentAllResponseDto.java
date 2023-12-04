package company.whistle.domain.board.content.dto;

import company.whistle.domain.board.comment.dto.CommentResponseDto;
import company.whistle.domain.board.content.entity.ContentFile;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentAllResponseDto {
    private Long contentId;
    private Long userId;
    private String title;
    private String content;
    private String boardCategory;
    private String categoryType;
    public List<ContentFile> contentFileList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private String name;
    private List<CommentResponseDto> comments;
}