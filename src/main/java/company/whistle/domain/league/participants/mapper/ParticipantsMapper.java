package company.whistle.domain.league.participants.mapper;

import company.whistle.domain.apply.league.entity.LeagueApply;
import company.whistle.domain.league.participants.dto.ParticipantsPatchDto;
import company.whistle.domain.league.participants.dto.ParticipantsPostDto;
import company.whistle.domain.league.participants.dto.ParticipantsResponseDto;
import company.whistle.domain.league.participants.dto.ParticipantsResponseListDto;
import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantsMapper {

    Participants participantsPostDtoToParticipants(ParticipantsPostDto requestBody);

    Participants participantsPatchDtoToParticipants(ParticipantsPatchDto requestBody);


    default ParticipantsResponseDto participantsToParticipantsResponse(Participants participants){

        User user = participants.getUser();

        Team team = participants.getTeam();

        League league = participants.getLeague();

        LeagueApply leagueApply = participants.getLeagueApply();

        return ParticipantsResponseDto.builder()
                .userId(user.getUserId())
                .teamId(team.getTeamId())
                .leagueId(league.getLeagueId())
                .leagueApplyId(leagueApply.getLeagueApplyId())
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

    default ParticipantsResponseListDto participantsToParticipantsResponseListDto(List<Participants> participants){

        return ParticipantsResponseListDto.builder()
                .participants(participantssToParticipantsResponse(participants))
                .build();
    }

    List<ParticipantsResponseDto> participantssToParticipantsResponse(List<Participants> participants);
}