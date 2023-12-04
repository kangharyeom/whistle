package company.whistle.domain.league.participants.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParticipantsPostDto {
    private Long userId;
    private Long teamId;
    private Long leagueId;
    private Long leagueApplyId;
    private Long leagueHonorScore;
    private Long honorScore;
    private Long memberCount;
    private Long championCount;
    private String formation;
    private String teamName;
    private String managerName;
    private String subManagerName;
    private String leagueName;
    private String ageType;
    private String locationType;
    private String levelType;
    private String frequency;
    private String uniformType;
    private Long leagueMatchCount = 0L;
    private Long leagueMatchPoints = 0L;
    private Long leagueWinRecord = 0L;
    private Long leagueDrawRecord = 0L;
    private Long leagueLoseRecord = 0L;
    private Long cleanSheet = 0L;
    private Long teamGoals = 0L;
    private Long teamAssist = 0L;

   /* private Long mostGoals = 0L;
    private Long mostAssists = 0L;
    private Long mostMoMs = 0L;*/
}
