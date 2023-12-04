package company.whistle.domain.match.unrank.mapper;

import company.whistle.domain.match.unrank.dto.*;
import company.whistle.domain.match.unrank.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    Match matchPostDtoToMatch(MatchPostDto requestBody);

    Match matchPatchDtoToMatch(MatchPatchDto requestBody);

    Match matchEndDtoToMatch(MatchEndDto requestBody);

    Match homeTeamPostDtoToMatch(HomeTeamPostDto requestBody);
    Match matchPatchDtoToBothMatch(AwayTeamPostDto requestBody);

    @Mapping(source = "user.userId", target = "homeTeamUserId")
    @Mapping(source = "match.awayTeamUserId", target = "awayTeamUserId")
    @Mapping(source = "team.teamId", target = "homeTeamId")
    @Mapping(source = "match.awayTeamId", target = "awayTeamId")
    @Mapping(source = "team.honorScore", target = "homeTeamHonorScore")
    BothTeamInfoResponseDto matchBothTeamResponse(Match match);

    @Mapping(source = "match.homeTeamUserId", target = "homeTeamUserId")
    @Mapping(source = "match.awayTeamUserId", target = "awayTeamUserId")
    @Mapping(source = "match.homeTeamId", target = "homeTeamId")
    @Mapping(source = "match.awayTeamId", target = "awayTeamId")
    MatchEndResponseDto MatchEndResponseDtoTeamResponse(Match match);
    MatchResponseDto matchToMatchResponse(Match match);
//    default MatchResponseDto matchToMatchResponse(Match match){
//        User user = match.getUser();
//        Team team = match.getTeam();
//
//        return MatchResponseDto.builder()
//                .matchId(match.getMatchId())
//                .userId(user.getUserId())
//                .teamId(team.getTeamId())
//                .homeTeamHonorScore(team.getHonorScore())
//                .homeTeamName(team.getTeamName())
//                .homeTeamManagerName(team.getManagerName())
//                .homeTeamTotalWinRecord(team.getTotalWinRecord())
//                .homeTeamTotalDrawRecord(team.getTotalDrawRecord())
//                .homeTeamTotalLoseRecord(team.getTotalLoseRecord())
//                .homeTeamLevelType(String.valueOf(team.getLevelType()))
//                .homeTeamAgeType(String.valueOf(team.getAgeType()))
//                .homeTeamUniformType(String.valueOf(team.getUniformType()))
//                .sportsType(String.valueOf(match.getSportsType()))
//                .locationType(String.valueOf(match.getLocationType()))
//                .matchTime(match.getMatchTime())
//                .matchDate(match.getMatchDate())
//                .matchStatus(String.valueOf(match.getMatchStatus()))
//                .matchType(String.valueOf(match.getMatchType()))
//                .createdAt(match.getCreatedAt())
//                .modifiedAt(match.getModifiedAt())
//                .build();
//    }

    default MatchListDto matchListDtoToMatchResponse(List<Match> matches){

        return MatchListDto.builder()
                .matchResponseDto(matchesToMatchesResponse(matches))
                .build();
    }

    List<MatchResponseDto> matchesToMatchesResponse(List<Match> matches);
}
