package company.whistle.domain.board.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentPatchDto {
    private Long commentId;
    private Long userId;
    private Long contentId;
    @NotBlank(message = "댓글의 내용을 입력해야 합니다.")
    private String comment;

    // 유저 정보
    private String name;
}
