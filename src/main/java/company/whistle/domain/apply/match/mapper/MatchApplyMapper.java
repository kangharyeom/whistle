package company.whistle.domain.apply.match.mapper;

import company.whistle.domain.apply.match.dto.*;
import company.whistle.domain.apply.match.entity.MatchApply;
import company.whistle.domain.match.unrank.entity.Match;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MatchApplyMapper {
    MatchApply matchApplyPostDtoToMatchApply(MatchApplyPostDto requestBody);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "team.teamId", target = "teamId")
    @Mapping(source = "match.matchId", target = "matchId")
    MatchApplyResponseDto matchApplyToMatchApplyResponse(MatchApply matchApply);

    default MatchApplyListDto matchApplyListDtoToMatchApplyResponse(List<MatchApply> matchApplies){

        return MatchApplyListDto.builder()
                .matchApplyResponseDtoList(matchAppliesToMatchApplyResponse(matchApplies))
                .build();
    }

    default List<MatchApplyResponseDto> matchAppliesToMatchApplyResponse(List<MatchApply> matchApplies){
        return matchApplies.stream()
                .map(matchApply -> MatchApplyResponseDto.builder()
                        .matchApplyId(matchApply.getMatchApplyId())
                        .teamName(matchApply.getTeamName())
                        .managerName(matchApply.getManagerName())
                        .applyType(String.valueOf(matchApply.getApplyType()))
                        .levelType(String.valueOf(matchApply.getLevelType()))
                        .ageType(String.valueOf(matchApply.getAgeType()))
                        .createdAt(matchApply.getCreatedAt())
                        .modifiedAt(matchApply.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}

