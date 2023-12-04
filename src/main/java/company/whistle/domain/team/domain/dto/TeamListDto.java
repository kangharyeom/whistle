package company.whistle.domain.team.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamListDto {
    List<TeamResponseDto> teamResponseDtoList;
}
