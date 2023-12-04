package company.whistle.domain.team.domain.mapper;

import company.whistle.domain.apply.team.entity.TeamApply;
import company.whistle.domain.team.domain.dto.*;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.team.domain.dto.SquadResponseDto;
import company.whistle.domain.team.squad.entity.Squad;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team teamPostDtoToTeam(TeamPostDto requestBody);
    Squad squadPostDtoToSquad(TeamPostDto requestBody);

    Team teamPatchDtoToTeam(TeamPatchDto requestBody);

    @Mapping(source = "user.userId", target = "userId")
    TeamResponseDto teamToTeamResponseDto(Team responseDto);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "team.teamId", target = "teamId")
    SquadResponseDto squadToSquadResponse(Squad squad);

    default TeamListDto teamListDtoToTeamResponse(List<Team> teams){

        return TeamListDto.builder()
                .teamResponseDtoList(teamsToTeamResponse(teams))
                .build();
    }

    List<TeamResponseDto> teamsToTeamResponse(List<Team> teams);
}
