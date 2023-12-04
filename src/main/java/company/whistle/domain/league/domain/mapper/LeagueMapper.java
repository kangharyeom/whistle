package company.whistle.domain.league.domain.mapper;

import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.league.domain.dto.LeagueListDto;
import company.whistle.domain.league.domain.dto.LeaguePatchDto;
import company.whistle.domain.league.domain.dto.LeaguePostDto;
import company.whistle.domain.league.domain.dto.LeagueResponseDto;
import company.whistle.domain.league.domain.dto.ParticipantsResponseDto;
import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueMapper {
    League leaguePostDtoToLeague(LeaguePostDto requestBody);
    Participants participantsPostDtoToParticipants(LeaguePostDto requestBody);

    League leaguePatchDtoToLeague(LeaguePatchDto requestBody);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "team.teamId", target = "teamId")
    LeagueResponseDto leagueToLeagueResponse(League league);

    default ParticipantsResponseDto participantsToParticipantsResponse(Participants participants){
        User user = participants.getUser();
        Team team = participants.getTeam();
        League league = participants.getLeague();

        return ParticipantsResponseDto.builder()
                .userId(user.getUserId())
                .teamId(team.getTeamId())
                .leagueId(league.getLeagueId())
                .participantsId(participants.getParticipantsId())

                .managerName(user.getName())

                .subManagerName(team.getSubManagerName())
                .championCount(team.getChampionCount())
                .memberCount(team.getMemberCount())
                .honorScore(team.getHonorScore())
                .teamName(team.getTeamName())
                .ageType(String.valueOf(team.getAgeType()))
                .locationType(String.valueOf(team.getLocationType()))
                .levelType(String.valueOf(team.getLevelType()))
                .uniformType(String.valueOf(team.getUniformType()))
                .formation(String.valueOf(team.getFormation()))
                .frequency(String.valueOf(team.getFrequency()))

                .leagueName(league.getLeagueName())

                .leagueMatchCount(participants.getLeagueMatchCount())
                .leagueHonorScore(participants.getLeagueHonorScore())
                .leagueMatchPoints(participants.getLeagueMatchPoints())
                .leagueWinRecord(participants.getLeagueWinRecord())
                .leagueDrawRecord(participants.getLeagueDrawRecord())
                .leagueLoseRecord(participants.getLeagueLoseRecord())
                .teamAssist(participants.getTeamAssist())
                .teamGoals(participants.getTeamGoals())
                .cleanSheet(participants.getCleanSheet())

//                .mostGoals(participants.getMostGoals())
//                .mostAssist(participants.getMostAssist())
//                .mostMom(participants.getMostMom())

                .createdAt(participants.getCreatedAt())
                .modifiedAt(participants.getModifiedAt())
                .build();
    }

    default LeagueListDto leagueListDtoToLeagueResponse(List<League> leagues){

        return LeagueListDto.builder()
                .leagueResponseDto(leaguesToLeagueResponse(leagues))
                .build();
    }

    List<LeagueResponseDto> leaguesToLeagueResponse(List<League> leagues);
}
