package company.whistle.domain.apply.league.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueApplyListDto {
    private List<LeagueApplyResponseDto> leagueApplyResponseDtoList;
}
