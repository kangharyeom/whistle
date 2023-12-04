package company.whistle.domain.match.unrank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HomeTeamPostDto {
    private Long homeTeamUserId;
    private Long homeTeamId;
    @NotBlank(message = "경기 시간을 입력 해야 합니다.")
    private String matchTime;
    @NotBlank(message = "경기 날짜를 입력 해야 합니다.")
    private String matchDate;
    private String matchRules;
    @NotBlank(message = "지역을 입력 해야 합니다.")
    private String locationType;
    private String homeTeamUniformType;
}
