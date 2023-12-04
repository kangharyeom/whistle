package company.whistle.domain.apply.team.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamApplyListDto {
    private List<TeamApplyResponseDto> teamApplyResponseDtoList;
}
