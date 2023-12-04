package company.whistle.domain.league.domain.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueListDto {
    List<LeagueResponseDto> leagueResponseDto;
}
