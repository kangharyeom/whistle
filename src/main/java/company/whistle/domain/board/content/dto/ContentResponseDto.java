package company.whistle.domain.board.content.dto;

import company.whistle.domain.board.content.entity.ContentFile;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentResponseDto {
    private Long contentId;
    private Long userId;
    private String title;
    private String content;
    private String categoryType;
    private String boardCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public List<ContentFile> contentFileList;
    private String name;

    @Override
    public String toString() {
        return "ContentResponseDto{" +
                "contentId=" + contentId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", boardCategory='" + boardCategory + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", contentFileList=" + contentFileList +
                ", name='" + name + '\'' +
                '}';
    }
}
