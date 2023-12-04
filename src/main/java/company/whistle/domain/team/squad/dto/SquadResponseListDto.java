package company.whistle.domain.team.squad.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SquadResponseListDto {
    private List<SquadResponseDto> squadResponseDtoList;
}
