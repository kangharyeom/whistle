package company.whistle.domain.match.unrank.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchListDto {
    List<MatchResponseDto> matchResponseDto;
}
