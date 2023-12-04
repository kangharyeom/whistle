package company.whistle.domain.match.unrank.service;

import company.whistle.domain.match.unrank.entity.Match;
import company.whistle.domain.match.unrank.repository.MatchRepository;
import company.whistle.domain.team.domain.repository.TeamRepository;
import company.whistle.global.constant.MatchResultStatus;
import company.whistle.global.constant.MatchStatus;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.team.domain.service.TeamService;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.repository.UserRepository;
import company.whistle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MatchService {
    private final MatchRepository matchRepository;
    private final UserService userService;
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Match createMatch(Match match, Long homeTeamUserId, Long homeTeamId) {
        try {
            if (homeTeamUserId == null || homeTeamId == null ) {
                log.info("userId: {}", homeTeamUserId);
                log.info("teamId: {}", homeTeamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(homeTeamUserId);
            Team team = teamService.findTeam(homeTeamId);

            findVerifiedExistsByTeamId(homeTeamId);

            match.setUser(user);
            match.setTeam(team);

            match.setHomeTeamName(team.getTeamName());
            match.setHomeTeamManagerName(team.getManagerName());
            match.setHomeTeamTotalWinRecord(team.getTotalWinRecord());
            match.setHomeTeamTotalDrawRecord(team.getTotalDrawRecord());
            match.setHomeTeamTotalLoseRecord(team.getTotalLoseRecord());
            match.setHomeTeamLevelType(team.getLevelType());
            match.setHomeTeamAgeType(team.getAgeType());
            match.setHomeTeamUniformType(team.getUniformType());
            match.setMatchType(match.getMatchType());

            matchRepository.save(match);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.MATCH_NOT_CREATED);
        }
        return match;
    }

    public Match createHomeTeamToMatch(Match match, Long userId, Long teamId) {
        try {
            if (userId == null || teamId == null ) {
                log.info("userId: {}", userId);
                log.info("teamId: {}", teamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(userId);
            Team team = teamService.findTeam(teamId);

            findVerifiedExistsByTeamId(teamId);

            match.setUser(user);
            match.setTeam(team);

            match.setHomeTeamName(team.getTeamName());
            match.setHomeTeamManagerName(team.getManagerName());
            match.setHomeTeamTotalWinRecord(team.getTotalWinRecord());
            match.setHomeTeamTotalDrawRecord(team.getTotalDrawRecord());
            match.setHomeTeamTotalLoseRecord(team.getTotalLoseRecord());
            match.setHomeTeamLevelType(team.getLevelType());
            match.setHomeTeamAgeType(team.getAgeType());
            match.setHomeTeamUniformType(team.getUniformType());
            match.setMatchType(match.getMatchType());

            matchRepository.save(match);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.MATCH_NOT_CREATED);
        }
        return match;
    }

    public Match postAwayTeamForMatch(Match match, Long matchId, Long matchApplyId, Long awayTeamUserId, Long awayTeamId) {
        try {
            if (matchId == null) {
                log.info("matchId: {}", matchId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Match findMatch = findVerifiedMatch(match.getMatchId());
            log.info("findMatch:{}",findMatch.toString());
            if (findMatch.getMatchStatus() != MatchStatus.valueOf("RIVAL_RECRUIT")) {
                throw new BusinessLogicException(Exceptions.MATCH_IS_NOT_RECRUIT_RIVAL);
            }
            userService.findUser(awayTeamUserId);
            Team team = teamService.findTeam(awayTeamId);

            findVerifiedMatchApply(matchApplyId);

            Optional.of(awayTeamId)
                    .ifPresent(findMatch::setAwayTeamId);

            Optional.of(awayTeamUserId)
                    .ifPresent(findMatch::setAwayTeamUserId);

            Optional.ofNullable(team.getHonorScore())
                    .ifPresent(findMatch::setAwayTeamHonorScore);

            Optional.ofNullable(team.getTeamName())
                    .ifPresent(findMatch::setAwayTeamName);

            Optional.ofNullable(team.getManagerName())
                    .ifPresent(findMatch::setAwayTeamManagerName);

            Optional.ofNullable(team.getTotalWinRecord())
                    .ifPresent(findMatch::setAwayTeamTotalWinRecord);

            Optional.ofNullable(team.getTotalDrawRecord())
                    .ifPresent(findMatch::setAwayTeamTotalDrawRecord);

            Optional.ofNullable(team.getTotalLoseRecord())
                    .ifPresent(findMatch::setAwayTeamTotalLoseRecord);

            Optional.ofNullable(team.getLevelType())
                    .ifPresent(findMatch::setAwayTeamLevelType);

            Optional.ofNullable(team.getAgeType())
                    .ifPresent(findMatch::setAwayTeamAgeType);

            Optional.ofNullable(team.getUniformType())
                    .ifPresent(findMatch::setAwayTeamUniformType);

            findMatch.setMatchStatus(MatchStatus.valueOf("BEFORE"));

            matchRepository.save(findMatch);
            return findMatch;
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.MATCH_NOT_PATCHED);
        }
    }

    public Match updateMatch(Match match, Long matchId) {
        try {
            if (matchId == null) {
                log.info("matchId: {}", matchId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Match findMatch = findVerifiedMatch(match.getMatchId());

            Optional.ofNullable(match.getHomeTeamName())
                    .ifPresent(findMatch::setHomeTeamName);

            Optional.ofNullable(match.getHomeTeamManagerName())
                    .ifPresent(findMatch::setHomeTeamManagerName);

            Optional.ofNullable(match.getHomeTeamTotalWinRecord())
                    .ifPresent(findMatch::setHomeTeamTotalWinRecord);

            Optional.ofNullable(match.getHomeTeamTotalDrawRecord())
                    .ifPresent(findMatch::setHomeTeamTotalDrawRecord);

            Optional.ofNullable(match.getHomeTeamTotalLoseRecord())
                    .ifPresent(findMatch::setHomeTeamTotalLoseRecord);

            Optional.ofNullable(match.getHomeTeamLevelType())
                    .ifPresent(findMatch::setHomeTeamLevelType);

            Optional.ofNullable(match.getHomeTeamAgeType())
                    .ifPresent(findMatch::setHomeTeamAgeType);

            Optional.ofNullable(match.getHomeTeamUniformType())
                    .ifPresent(findMatch::setHomeTeamUniformType);

            Optional.ofNullable(match.getMatchType())
                    .ifPresent(findMatch::setMatchType);

            Optional.ofNullable(match.getMatchTime())
                    .ifPresent(findMatch::setMatchTime);

            Optional.ofNullable(match.getMatchStatus())
                    .ifPresent(findMatch::setMatchStatus);

            Optional.ofNullable(match.getHomeTeamMatchResultStatus())
                    .ifPresent(findMatch::setHomeTeamMatchResultStatus);

            Optional.ofNullable(match.getAwayTeamMatchResultStatus())
                    .ifPresent(findMatch::setAwayTeamMatchResultStatus);
            matchRepository.save(findMatch);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.MATCH_NOT_PATCHED);
        }
        return match;
    }

    public Match matchEnd(Match match, Long matchId, Long homeTeamScore, Long awayTeamScore,
                          Long homeTeamId, Long awayTeamId) {
        try {
            if (matchId == null || homeTeamId == null || awayTeamId == null) {
                log.info("matchId: {}", matchId);
                log.info("homeTeamId: {}", homeTeamId);
                log.info("awayTeamId: {}", awayTeamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Match findMatch = findVerifiedMatch(match.getMatchId());

            if(findMatch.getMatchStatus()==MatchStatus.valueOf("END")){
                throw new BusinessLogicException(Exceptions.MATCH_ALREADY_END);
            }
            log.info("homeTeamId: {}", homeTeamId);
            log.info("awayTeamId: {}", awayTeamId);

            Team findHomeTeam = findVerifiedTeam(homeTeamId);
            Team findAwayTeam = findVerifiedTeam(awayTeamId);

            findMatch.setTeam(findHomeTeam);
            findMatch.setTeam(findAwayTeam);
            if(homeTeamScore>awayTeamScore){
                //homeTeam 승리한 경우
                findMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf("WIN"));
                findMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf("LOSE"));

                // teamRepository save
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+300L);
                findHomeTeam.setTotalWinRecord(findHomeTeam.getTotalWinRecord()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+10L);
                findAwayTeam.setTotalLoseRecord(findAwayTeam.getTotalLoseRecord()+1L);

                findMatch.setHomeTeamTotalWinRecord(findHomeTeam.getTotalWinRecord());
                findMatch.setAwayTeamTotalLoseRecord(findAwayTeam.getTotalLoseRecord());
            } else if(homeTeamScore<awayTeamScore){
                //homeTeam 패배한 경우
                findMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf("LOSE"));
                findMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf("WIM"));

                // teamRepository save
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+10L);
                findHomeTeam.setTotalLoseRecord(findHomeTeam.getTotalLoseRecord()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+300L);
                findAwayTeam.setTotalWinRecord(findAwayTeam.getTotalWinRecord()+1L);

                findMatch.setHomeTeamTotalWinRecord(findHomeTeam.getTotalLoseRecord());
                findMatch.setAwayTeamTotalLoseRecord(findAwayTeam.getTotalWinRecord());
            } else {
                //무승부인 경우
                findMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf("DRAW"));
                findMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf("DRAW"));

                // teamRepository save
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+100L);
                findHomeTeam.setTotalDrawRecord(findHomeTeam.getTotalDrawRecord()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+100L);
                findAwayTeam.setTotalDrawRecord(findAwayTeam.getTotalDrawRecord()+1L);

                findMatch.setHomeTeamTotalWinRecord(findHomeTeam.getTotalDrawRecord());
                findMatch.setAwayTeamTotalLoseRecord(findAwayTeam.getTotalDrawRecord());
            }
            teamRepository.save(findHomeTeam);
            teamRepository.save(findAwayTeam);

            findMatch.setHomeTeamHonorScore(findHomeTeam.getHonorScore());
            findMatch.setAwayTeamHonorScore(findAwayTeam.getHonorScore());
            findMatch.setHomeTeamId(homeTeamId);
            findMatch.setHomeTeamScore(homeTeamScore);
            findMatch.setAwayTeamScore(awayTeamScore);

            findMatch.setMatchStatus(MatchStatus.valueOf("END"));

            matchRepository.save(findMatch);
            log.info("MATCH_END ABOUT AWAY_TEAM TO TEAM_REPOSITORY:{}", findMatch);
            return findMatch;
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.MATCH_NOT_PATCHED);
        }
    }

    public Match findMatch(Long matchId) {
        return findVerifiedMatch(matchId);
    }

    public Page<Match> findMatches(int page, int size) {
        return matchRepository.findAll(PageRequest.of(page, size,
                Sort.by("matchId").descending()));
    }

    public List<Match> findAllSearch(String keyword){
        return matchRepository.findAllSearch(keyword);
    }

    public List<Match> findAllSearchByUserName(String name){
        return matchRepository.findAllSearchByUserName(name);
    }

    public List<Match> findMatchesNewest() {
        return matchRepository.findMatchesNewest();
    }

    public List<Match> findMatchesLatest() {
        return matchRepository.findMatchesLatest();
    }

    public void deleteMatch(Long matchId) {
        try {
            Match findMatch = findVerifiedMatch(matchId);
            matchRepository.delete(findMatch);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessLogicException(Exceptions.MATCH_NOT_DELETED);
        }
    }

    public User findVerifiedUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.USER_NOT_FOUND));
    }

    public Match findVerifiedMatch(Long matchId) {
        Optional<Match> optionalMatch = matchRepository.findById(matchId);

        return optionalMatch.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.MATCH_NOT_FOUND));
    }


    public Match findVerifiedMatchApply(Long matchApplyId) {
        Optional<Match> optionalMatch = matchRepository.findById(matchApplyId);

        return optionalMatch.orElseThrow(() ->
                new BusinessLogicException(Exceptions.MATCH_APPLY_NOT_FOUND));
    }

    public void findVerifiedExistsByTeamId(long teamId) {
        Match match = matchRepository.findByVerifiedTeamId(teamId);
        if(match !=null) {
            throw new BusinessLogicException(Exceptions.MATCH_EXISTS);
        }
    }

    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        return optionalTeam.orElseThrow(() ->
                new BusinessLogicException(Exceptions.TEAM_NOT_FOUND));
    }

}