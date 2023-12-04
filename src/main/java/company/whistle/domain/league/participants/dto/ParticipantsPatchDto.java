package company.whistle.domain.league.participants.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParticipantsPatchDto {
    private Long participantsId;
    private Long userId;
    private Long teamId;
    private Long leagueId;
    private Long applyId;
    private Long leagueHonorScore;
    private Long honorScore;
    private Long memberCount;
    private Long championCount;
    private String formation;
    private String subManagerName;
    private String ageType;
    private String locationType;
    private String levelType;
    private String leagueRules;
    private String frequency;
    private String uniformType;
    private Long leagueMatchCount;
    private Long leagueMatchPoints;
    private Long leagueWinRecord;
    private Long leagueDrawRecord;
    private Long leagueLoseRecord;
    private Long teamGoals;
    private Long teamAssist;
    private Long cleanSheet;
}
