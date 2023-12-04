package company.whistle.domain.team.squad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SquadPatchDto {
    private Long squadId;
    private Long userId;
    private Long teamId;
    private Long applyId;
    private String name;
    private String position;
    private String teamMemberType;
    private String ageType;
    private String locationType;
    private String levelType;
    private String frequency;
}
