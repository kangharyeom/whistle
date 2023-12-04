package company.whistle.domain.board.content.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentListDto {
    List<ContentResponseDto> contentResponseDto;
}