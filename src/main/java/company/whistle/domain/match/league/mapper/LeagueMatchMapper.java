package company.whistle.domain.match.league.mapper;

import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.match.league.dto.*;
import company.whistle.global.constant.*;
import company.whistle.domain.match.league.entity.LeagueMatch;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LeagueMatchMapper {
    default LeagueMatch leagueMatchPostDtoToLeagueMatch(LeagueMatchPostDto requestBody){
        User homeTeamUser = new User();
        homeTeamUser.setUserId(requestBody.getHomeTeamUserId());

        User awayTeamUser = new User();
        awayTeamUser.setUserId(requestBody.getAwayTeamUserId());

        Team homeTeam = new Team();
        homeTeam.setTeamId(requestBody.getHomeTeamId());
        homeTeam.setHonorScore(requestBody.getHomeTeamHonorScore());
        homeTeam.setTeamName(requestBody.getHomeTeamName());
        homeTeam.setManagerName(requestBody.getHomeTeamManagerName());
        homeTeam.setTotalWinRecord(requestBody.getHomeTeamTotalWinRecord());
        homeTeam.setTotalDrawRecord(requestBody.getHomeTeamTotalDrawRecord());
        homeTeam.setTotalLoseRecord(requestBody.getHomeTeamTotalLoseRecord());
        homeTeam.setLevelType(LevelType.valueOf(requestBody.getHomeTeamLevelType()));
        homeTeam.setAgeType(AgeType.valueOf(requestBody.getHomeTeamAgeType()));
        homeTeam.setUniformType(UniformType.valueOf(requestBody.getHomeTeamUniformType()));

        Team awayTeam = new Team();
        awayTeam.setTeamId(requestBody.getAwayTeamId());
        awayTeam.setHonorScore(requestBody.getAwayTeamHonorScore());
        awayTeam.setTeamName(requestBody.getAwayTeamName());
        awayTeam.setManagerName(requestBody.getAwayTeamManagerName());
        awayTeam.setTotalWinRecord(requestBody.getAwayTeamTotalWinRecord());
        awayTeam.setTotalDrawRecord(requestBody.getAwayTeamTotalDrawRecord());
        awayTeam.setTotalLoseRecord(requestBody.getAwayTeamTotalLoseRecord());
        awayTeam.setLevelType(LevelType.valueOf(requestBody.getAwayTeamLevelType()));
        awayTeam.setAgeType(AgeType.valueOf(requestBody.getAwayTeamAgeType()));
        awayTeam.setUniformType(UniformType.valueOf(requestBody.getAwayTeamUniformType()));

        Participants homeTeamParticipants = new Participants();
        homeTeamParticipants.setParticipantsId(requestBody.getHomeTeamLeagueListId());
        homeTeamParticipants.setLeagueMatchPoints(requestBody.getHomeTeamLeagueMatchPoints());
        homeTeamParticipants.setLeagueWinRecord(requestBody.getHomeTeamLeagueWinRecord());
        homeTeamParticipants.setLeagueDrawRecord(requestBody.getHomeTeamLeagueDrawRecord());
        homeTeamParticipants.setLeagueLoseRecord(requestBody.getHomeTeamLeagueLoseRecord());

        Participants awayTeamParticipants = new Participants();
        awayTeamParticipants.setParticipantsId(requestBody.getAwayTeamLeagueListId());
        awayTeamParticipants.setLeagueMatchPoints(requestBody.getAwayTeamLeagueMatchPoints());
        awayTeamParticipants.setLeagueWinRecord(requestBody.getAwayTeamLeagueWinRecord());
        awayTeamParticipants.setLeagueDrawRecord(requestBody.getAwayTeamLeagueDrawRecord());
        awayTeamParticipants.setLeagueLoseRecord(requestBody.getAwayTeamLeagueLoseRecord());

        LeagueMatch leagueMatch = new LeagueMatch();

        leagueMatch.setUser(homeTeamUser);
        leagueMatch.setUser(awayTeamUser);

        leagueMatch.setTeam(homeTeam);
        leagueMatch.setTeam(awayTeam);

        leagueMatch.setParticipants(homeTeamParticipants);
        leagueMatch.setParticipants(awayTeamParticipants);

        leagueMatch.setHomeTeamScore(requestBody.getHomeTeamScore());
        leagueMatch.setHomeTeamHonorScore(requestBody.getHomeTeamHonorScore());
        leagueMatch.setHomeTeamName(requestBody.getHomeTeamName());
        leagueMatch.setHomeTeamManagerName(requestBody.getHomeTeamManagerName());
        leagueMatch.setHomeTeamLeagueMatchPoints(requestBody.getHomeTeamLeagueMatchPoints());
        leagueMatch.setHomeTeamLeagueWinRecord(requestBody.getHomeTeamLeagueWinRecord());
        leagueMatch.setHomeTeamLeagueDrawRecord(requestBody.getHomeTeamLeagueDrawRecord());
        leagueMatch.setHomeTeamLeagueLoseRecord(requestBody.getHomeTeamLeagueLoseRecord());
        leagueMatch.setHomeTeamTotalWinRecord(requestBody.getHomeTeamTotalWinRecord());
        leagueMatch.setHomeTeamTotalDrawRecord(requestBody.getHomeTeamTotalDrawRecord());
        leagueMatch.setHomeTeamTotalLoseRecord(requestBody.getHomeTeamTotalLoseRecord());
        leagueMatch.setHomeTeamLevelType(LevelType.valueOf(requestBody.getHomeTeamLevelType()));
        leagueMatch.setHomeTeamAgeType(AgeType.valueOf(requestBody.getHomeTeamAgeType()));
        leagueMatch.setHomeTeamUniformType(UniformType.valueOf(requestBody.getHomeTeamUniformType()));
        leagueMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf(requestBody.getHomeTeamMatchResultStatus()));

        leagueMatch.setAwayTeamScore(requestBody.getAwayTeamScore());
        leagueMatch.setAwayTeamHonorScore(requestBody.getAwayTeamHonorScore());
        leagueMatch.setAwayTeamName(requestBody.getAwayTeamName());
        leagueMatch.setAwayTeamManagerName(requestBody.getAwayTeamManagerName());
        leagueMatch.setAwayTeamLeagueMatchPoints(requestBody.getAwayTeamLeagueMatchPoints());
        leagueMatch.setAwayTeamLeagueWinRecord(requestBody.getAwayTeamLeagueWinRecord());
        leagueMatch.setAwayTeamLeagueDrawRecord(requestBody.getAwayTeamLeagueDrawRecord());
        leagueMatch.setAwayTeamLeagueLoseRecord(requestBody.getAwayTeamLeagueLoseRecord());
        leagueMatch.setAwayTeamTotalWinRecord(requestBody.getAwayTeamTotalWinRecord());
        leagueMatch.setAwayTeamTotalDrawRecord(requestBody.getAwayTeamTotalDrawRecord());
        leagueMatch.setAwayTeamTotalLoseRecord(requestBody.getAwayTeamTotalLoseRecord());
        leagueMatch.setAwayTeamLevelType(LevelType.valueOf(requestBody.getAwayTeamLevelType()));
        leagueMatch.setAwayTeamAgeType(AgeType.valueOf(requestBody.getAwayTeamAgeType()));
        leagueMatch.setAwayTeamUniformType(UniformType.valueOf(requestBody.getAwayTeamUniformType()));
        leagueMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf(requestBody.getAwayTeamMatchResultStatus()));

        leagueMatch.setMatchType(MatchType.valueOf(requestBody.getMatchType()));
        leagueMatch.setSportType(SportsType.valueOf(requestBody.getSportType()));
        leagueMatch.setMatchTime(requestBody.getMatchTime());
        leagueMatch.setMatchStatus(MatchStatus.valueOf(requestBody.getMatchStatus()));
        leagueMatch.setLocationType(LocationType.valueOf(requestBody.getLocationType()));
        leagueMatch.setMatchRules(requestBody.getMatchRules());

        return leagueMatch;
    }

    default LeagueMatch leagueMatchPatchDtoToLeagueMatch(LeagueMatchPatchDto requestBody) {
        LeagueMatch leagueMatch = new LeagueMatch();

        leagueMatch.setHomeTeamScore(requestBody.getHomeTeamScore());
        leagueMatch.setHomeTeamHonorScore(requestBody.getHomeTeamHonorScore());
        leagueMatch.setHomeTeamName(requestBody.getHomeTeamName());
        leagueMatch.setHomeTeamManagerName(requestBody.getHomeTeamManagerName());
        leagueMatch.setHomeTeamLeagueMatchPoints(requestBody.getHomeTeamLeagueMatchPoints());
        leagueMatch.setHomeTeamLeagueWinRecord(requestBody.getHomeTeamLeagueWinRecord());
        leagueMatch.setHomeTeamLeagueDrawRecord(requestBody.getHomeTeamLeagueDrawRecord());
        leagueMatch.setHomeTeamLeagueLoseRecord(requestBody.getHomeTeamLeagueLoseRecord());
        leagueMatch.setHomeTeamTotalWinRecord(requestBody.getHomeTeamTotalWinRecord());
        leagueMatch.setHomeTeamTotalDrawRecord(requestBody.getHomeTeamTotalDrawRecord());
        leagueMatch.setHomeTeamTotalLoseRecord(requestBody.getHomeTeamTotalLoseRecord());
        leagueMatch.setHomeTeamLevelType(LevelType.valueOf(requestBody.getLevelType()));
        leagueMatch.setHomeTeamAgeType(AgeType.valueOf(requestBody.getAgeType()));
        leagueMatch.setHomeTeamUniformType(UniformType.valueOf(requestBody.getHomeTeamUniformType()));
        leagueMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf(requestBody.getHomeTeamMatchResultStatus()));

        leagueMatch.setAwayTeamScore(requestBody.getAwayTeamScore());
        leagueMatch.setAwayTeamHonorScore(requestBody.getAwayTeamHonorScore());
        leagueMatch.setAwayTeamName(requestBody.getAwayTeamName());
        leagueMatch.setAwayTeamManagerName(requestBody.getAwayTeamManagerName());
        leagueMatch.setAwayTeamLeagueMatchPoints(requestBody.getAwayTeamLeagueMatchPoints());
        leagueMatch.setAwayTeamLeagueWinRecord(requestBody.getAwayTeamLeagueWinRecord());
        leagueMatch.setAwayTeamLeagueDrawRecord(requestBody.getAwayTeamLeagueDrawRecord());
        leagueMatch.setAwayTeamLeagueLoseRecord(requestBody.getAwayTeamLeagueLoseRecord());
        leagueMatch.setAwayTeamTotalWinRecord(requestBody.getAwayTeamTotalWinRecord());
        leagueMatch.setAwayTeamTotalDrawRecord(requestBody.getAwayTeamTotalDrawRecord());
        leagueMatch.setAwayTeamTotalLoseRecord(requestBody.getAwayTeamTotalLoseRecord());
        leagueMatch.setAwayTeamLevelType(LevelType.valueOf(requestBody.getLevelType()));
        leagueMatch.setAwayTeamAgeType(AgeType.valueOf(requestBody.getAgeType()));
        leagueMatch.setAwayTeamUniformType(UniformType.valueOf(requestBody.getAwayTeamUniformType()));
        leagueMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf(requestBody.getAwayTeamMatchResultStatus()));

        leagueMatch.setMatchType(MatchType.valueOf(requestBody.getMatchType()));
        leagueMatch.setSportType(SportsType.valueOf(requestBody.getSportType()));
        leagueMatch.setMatchTime(requestBody.getMatchTime());
        leagueMatch.setMatchStatus(MatchStatus.valueOf(requestBody.getMatchStatus()));
        leagueMatch.setLocationType(LocationType.valueOf(requestBody.getLocationType()));
        leagueMatch.setMatchRules(requestBody.getMatchRules());

        return leagueMatch;
    }

    default LeagueMatch leagueMatchEndDtoToLeagueMatch(LeagueMatchEndDto requestBody) {
        LeagueMatch leagueMatch = new LeagueMatch();

        leagueMatch.setHomeTeamScore(requestBody.getHomeTeamScore());
        leagueMatch.setAwayTeamScore(requestBody.getAwayTeamScore());
        leagueMatch.setMatchStatus(MatchStatus.valueOf(requestBody.getMatchStatus()));

        return leagueMatch;
    }

    default LeagueMatchResponseDto leagueMatchToLeagueMatchResponse(
            LeagueMatch leagueMatch
    ){
        User homeTeamUser = leagueMatch.getUser();
        User awayTeamUser = leagueMatch.getUser();
        Team homeTeam = leagueMatch.getTeam();
        Team awayTeam = leagueMatch.getTeam();
        Participants homeTeamParticipants = leagueMatch.getParticipants();
        Participants awayTeamParticipants = leagueMatch.getParticipants();

        return LeagueMatchResponseDto.builder()
                .leagueMatchId(leagueMatch.getLeagueMatchId())
                .homeTeamUserId(homeTeamUser.getUserId())
                .awayTeamUserId(awayTeamUser.getUserId())
                .homeTeamId(homeTeam.getTeamId())
                .awayTeamId(awayTeam.getTeamId())
                .homeTeamLeagueListId(homeTeamParticipants.getParticipantsId())
                .awayTeamLeagueListId(awayTeamParticipants.getParticipantsId())
                .homeTeamScore(leagueMatch.getHomeTeamScore())
                .homeTeamHonorScore(leagueMatch.getHomeTeamHonorScore())
                .homeTeamName(leagueMatch.getHomeTeamName())
                .homeTeamManagerName(leagueMatch.getHomeTeamManagerName())
                .homeTeamLeagueWinRecord(leagueMatch.getAwayTeamLeagueWinRecord())
                .homeTeamLeagueDrawRecord(leagueMatch.getHomeTeamTotalDrawRecord())
                .homeTeamLeagueLoseRecord(leagueMatch.getHomeTeamTotalLoseRecord())
                .homeTeamTotalWinRecord(leagueMatch.getHomeTeamTotalWinRecord())
                .homeTeamTotalDrawRecord(leagueMatch.getHomeTeamTotalDrawRecord())
                .homeTeamTotalLoseRecord(leagueMatch.getHomeTeamTotalLoseRecord())
                .homeTeamLevelType(String.valueOf(leagueMatch.getHomeTeamLevelType()))
                .homeTeamAgeType(String.valueOf(leagueMatch.getHomeTeamAgeType()))
                .homeTeamUniformType(String.valueOf(leagueMatch.getHomeTeamUniformType()))
                .homeTeamMatchResultStatus(String.valueOf(leagueMatch.getHomeTeamMatchResultStatus()))
                .awayTeamScore(leagueMatch.getAwayTeamScore())
                .awayTeamHonorScore(leagueMatch.getAwayTeamHonorScore())
                .awayTeamName(leagueMatch.getAwayTeamName())
                .awayTeamManagerName(leagueMatch.getAwayTeamManagerName())
                .awayTeamLeagueWinRecord(leagueMatch.getAwayTeamLeagueWinRecord())
                .awayTeamLeagueDrawRecord(leagueMatch.getAwayTeamTotalDrawRecord())
                .awayTeamLeagueLoseRecord(leagueMatch.getAwayTeamTotalLoseRecord())
                .awayTeamTotalWinRecord(leagueMatch.getAwayTeamTotalWinRecord())
                .awayTeamTotalDrawRecord(leagueMatch.getAwayTeamTotalDrawRecord())
                .awayTeamTotalLoseRecord(leagueMatch.getAwayTeamTotalLoseRecord())
                .awayTeamLevelType(String.valueOf(leagueMatch.getAwayTeamLevelType()))
                .awayTeamAgeType(String.valueOf(leagueMatch.getAwayTeamAgeType()))
                .awayTeamUniformType(String.valueOf(leagueMatch.getAwayTeamUniformType()))
                .awayTeamMatchResultStatus(String.valueOf(leagueMatch.getAwayTeamMatchResultStatus()))
                .sportType(String.valueOf(leagueMatch.getSportType()))
                .locationType(String.valueOf(leagueMatch.getLocationType()))
                .matchTime(leagueMatch.getMatchTime())
                .matchStatus(String.valueOf(leagueMatch.getMatchStatus()))
                .matchType(String.valueOf(leagueMatch.getMatchType()))
                .createdAt(leagueMatch.getCreatedAt())
                .modifiedAt(leagueMatch.getModifiedAt())
                .build();
    }

    LeagueMatchResponseDto leagueMatchPostToLeagueMatchResponse(
            LeagueMatch leagueMatch
            , Long homeTeamUserId
            , Long awayTeamUserId
            , Long homeTeamId
            , Long awayTeamId
            , Long homeTeamLeagueListId
            , Long awayTeamLeagueListId
    );

    default LeagueMatchEndResponseDto leagueMatchToLeagueMatchEndResponse(LeagueMatch leagueMatch){
        User homeTeamUser = leagueMatch.getUser();
        User awayTeamUser = leagueMatch.getUser();
        Team homeTeam = leagueMatch.getTeam();
        Team awayTeam = leagueMatch.getTeam();
        Participants homeTeamParticipants = leagueMatch.getParticipants();
        Participants awayTeamParticipants = leagueMatch.getParticipants();

        return LeagueMatchEndResponseDto.builder()
                .leagueMatchId(leagueMatch.getLeagueMatchId())
                .homeTeamUserId(homeTeamUser.getUserId())
                .awayTeamUserId(awayTeamUser.getUserId())
                .homeTeamId(homeTeam.getTeamId())
                .awayTeamId(awayTeam.getTeamId())
                .homeTeamLeagueListId(homeTeamParticipants.getParticipantsId())
                .awayTeamLeagueListId(awayTeamParticipants.getParticipantsId())
                .homeTeamScore(leagueMatch.getHomeTeamScore())
                .awayTeamScore(leagueMatch.getAwayTeamScore())
                .matchStatus(String.valueOf(leagueMatch.getMatchStatus()))
                .createdAt(leagueMatch.getCreatedAt())
                .modifiedAt(leagueMatch.getModifiedAt())
                .build();
    }

    default LeagueMatchListDto leagueMatchListDtoToLeagueMatchResponse(List<LeagueMatch> leagueMatches){

        return LeagueMatchListDto.builder()
                .leagueMatchResponseDto(leagueMatchesToLeagueMatchesResponse(leagueMatches))
                .build();
    }

    default List<LeagueMatchResponseDto> leagueMatchesToLeagueMatchesResponse(List<LeagueMatch> leagueMatches){
        return leagueMatches.stream()
                .map(leagueMatch -> LeagueMatchResponseDto.builder()
                        .leagueMatchId(leagueMatch.getLeagueMatchId())
                        .homeTeamScore(leagueMatch.getHomeTeamScore())
                        .homeTeamHonorScore(leagueMatch.getHomeTeamHonorScore())
                        .homeTeamName(leagueMatch.getHomeTeamName())
                        .homeTeamManagerName(leagueMatch.getHomeTeamManagerName())
                        .homeTeamLeagueWinRecord(leagueMatch.getAwayTeamLeagueWinRecord())
                        .homeTeamLeagueDrawRecord(leagueMatch.getHomeTeamTotalDrawRecord())
                        .homeTeamLeagueLoseRecord(leagueMatch.getHomeTeamTotalLoseRecord())
                        .homeTeamTotalWinRecord(leagueMatch.getHomeTeamTotalWinRecord())
                        .homeTeamTotalDrawRecord(leagueMatch.getHomeTeamTotalDrawRecord())
                        .homeTeamTotalLoseRecord(leagueMatch.getHomeTeamTotalLoseRecord())
                        .homeTeamLevelType(String.valueOf(leagueMatch.getHomeTeamLevelType()))
                        .homeTeamAgeType(String.valueOf(leagueMatch.getHomeTeamAgeType()))
                        .homeTeamUniformType(String.valueOf(leagueMatch.getHomeTeamUniformType()))
                        .homeTeamMatchResultStatus(String.valueOf(leagueMatch.getHomeTeamMatchResultStatus()))
                        .awayTeamScore(leagueMatch.getAwayTeamScore())
                        .awayTeamHonorScore(leagueMatch.getAwayTeamHonorScore())
                        .awayTeamName(leagueMatch.getAwayTeamName())
                        .awayTeamManagerName(leagueMatch.getAwayTeamManagerName())
                        .awayTeamLeagueWinRecord(leagueMatch.getAwayTeamLeagueWinRecord())
                        .awayTeamLeagueDrawRecord(leagueMatch.getAwayTeamTotalDrawRecord())
                        .awayTeamLeagueLoseRecord(leagueMatch.getAwayTeamTotalLoseRecord())
                        .awayTeamTotalWinRecord(leagueMatch.getAwayTeamTotalWinRecord())
                        .awayTeamTotalDrawRecord(leagueMatch.getAwayTeamTotalDrawRecord())
                        .awayTeamTotalLoseRecord(leagueMatch.getAwayTeamTotalLoseRecord())
                        .awayTeamLevelType(String.valueOf(leagueMatch.getAwayTeamLevelType()))
                        .awayTeamAgeType(String.valueOf(leagueMatch.getAwayTeamAgeType()))
                        .awayTeamUniformType(String.valueOf(leagueMatch.getAwayTeamUniformType()))
                        .awayTeamMatchResultStatus(String.valueOf(leagueMatch.getHomeTeamMatchResultStatus()))
                        .locationType(String.valueOf(leagueMatch.getLocationType()))
                        .matchStatus(String.valueOf(leagueMatch.getMatchStatus()))
                        .sportType(String.valueOf(leagueMatch.getSportType()))
                        .locationType(String.valueOf(leagueMatch.getLocationType()))
                        .matchTime(leagueMatch.getMatchTime())
                        .matchStatus(String.valueOf(leagueMatch.getMatchStatus()))
                        .matchType(String.valueOf(leagueMatch.getMatchType()))
                        .createdAt(leagueMatch.getCreatedAt())
                        .modifiedAt(leagueMatch.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
