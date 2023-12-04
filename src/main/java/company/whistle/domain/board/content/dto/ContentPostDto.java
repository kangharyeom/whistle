package company.whistle.domain.board.content.dto;

import company.whistle.domain.board.content.entity.ContentFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContentPostDto {
    private Long userId;
    @NotBlank(message = "게시글의 제목을 입력해야 합니다.")
    private String title;
    @NotBlank(message = "게시글의 내용을 입력해야 합니다.")
    private String content;
    private String categoryType;
    private String boardCategory;
    public List<ContentFile> contentFileList;

    private String name;
}
