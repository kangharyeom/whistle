package company.board_project.leaguematch.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueMatchListDto {
    List<LeagueMatchResponseDto> leagueMatchResponseDto;
}