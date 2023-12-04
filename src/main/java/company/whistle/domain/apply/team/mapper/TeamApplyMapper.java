package company.whistle.domain.apply.team.mapper;

import company.whistle.domain.apply.team.dto.*;
import company.whistle.domain.apply.team.entity.TeamApply;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeamApplyMapper {
    TeamApply teamApplyPostDtoToTeamApply(TeamApplyPostDto requestBody);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "team.teamId", target = "teamId")
    TeamApplyResponseDto teamApplyToTeamApplyResponse(TeamApply teamApply);

    default TeamApplyListDto teamApplyListDtoToTeamApplyResponse(List<TeamApply> teamApplies){

        return TeamApplyListDto.builder()
                .teamApplyResponseDtoList(teamAppliesToTeamApplyResponse(teamApplies))
                .build();
    }

    default List<TeamApplyResponseDto> teamAppliesToTeamApplyResponse(List<TeamApply> teamApplies){
        return teamApplies.stream()
                .map(teamApply -> TeamApplyResponseDto.builder()
                        .teamApplyId(teamApply.getTeamApplyId())
                        .teamName(teamApply.getTeamName())
                        .managerName(teamApply.getManagerName())
                        .applyType(String.valueOf(teamApply.getApplyType()))
                        .levelType(String.valueOf(teamApply.getLevelType()))
                        .ageType(String.valueOf(teamApply.getAgeType()))
                        .createdAt(teamApply.getCreatedAt())
                        .modifiedAt(teamApply.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}

