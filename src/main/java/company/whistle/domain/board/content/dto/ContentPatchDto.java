package company.whistle.domain.board.content.dto;

import company.whistle.domain.board.content.entity.ContentFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContentPatchDto {
    private Long contentId;
    private Long userId;
    private String title;
    @NotBlank(message = "게시글의 내용을 입력해야 합니다.")
    private String content;
    private String categoryType;
    private String boardCategory;
    public List<ContentFile> contentFileList;

    private String name;

    public void updateId(Long id){
        this.contentId = id;
    }
}
