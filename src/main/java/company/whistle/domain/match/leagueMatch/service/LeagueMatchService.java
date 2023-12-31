package company.whistle.domain.match.leagueMatch.service;

import company.whistle.domain.league.league.service.LeagueService;
import company.whistle.domain.league.participants.entity.Participants;
import company.whistle.domain.match.leagueMatch.repository.LeagueMatchRepository;
import company.whistle.global.constant.MatchResultStatus;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.domain.league.league.repository.LeagueRepository;
import company.whistle.domain.league.participants.service.ParticipantsService;
import company.whistle.domain.match.leagueMatch.entity.LeagueMatch;
import company.whistle.domain.team.team.entity.Team;
import company.whistle.domain.team.team.service.TeamService;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class LeagueMatchService {
    private final LeagueMatchRepository leagueMatchRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TeamService teamService;
    private final LeagueService leagueService;
    private final ParticipantsService participantsService;
    private final LeagueRepository leagueRepository;

    public LeagueMatch createLeagueMatch(LeagueMatch leagueMatch, Long leagueId, Long homeTeamId, Long awayTeamId
    ) {
        try {
            User loginUser = userService.getLoginUser();
            Long loginUserId = loginUser.getUserId();
            String loginUserName = loginUser.getName();
            if (leagueId == null || homeTeamId == null || awayTeamId == null || loginUserId==null) {
                log.info("leagueId:{}",leagueId);
                log.info("homeTeamId:{}",homeTeamId);
                log.info("awayTeamId:{}",awayTeamId);
                throw new BusinessLogicException(Exceptions.IDS_OR_NAMES_ARE_NULL);
            }

            /* HOME AND AWAY TEAM 정보 주입 */
            Team homeTeam = teamService.findByTeamId(homeTeamId);
            Team awayTeam = teamService.findByTeamId(awayTeamId);

            /*
             * CHECK USER AUTHORIZATION
             * 유저가 관리자 권한을 가지고 접근하는지 검사
             * 로그인한 유저가 '리그 관리자'가 아니거나, '홈' or '어웨이 팀 매니저'가 아닌 경우 예외처리
             * */
            String loginTeamManagerName = teamService.findTeamManagerNameByUserId(loginUserId);
            if ((!Objects.equals(leagueService.findManagerNameByUserId(loginUserId), loginUserName))) {
                if ((!Objects.equals(homeTeam.getManagerName(), loginTeamManagerName))) {
                    if ((!Objects.equals(awayTeam.getManagerName(), loginTeamManagerName))) {
                        throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
                    }
                    throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
                }
                throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
            }

            User homeTeamUser = userService.findByUserId(homeTeam.getUser().getUserId());
            User awayTeamUser = userService.findByUserId(awayTeam.getUser().getUserId());

            Participants homeTeamParticipants = participantsService.findByTeamId(homeTeamId);
            Participants awayTeamParticipants = participantsService.findByTeamId(awayTeamId);

            leagueMatch.setUser(homeTeamUser);
            leagueMatch.setUser(awayTeamUser);
            leagueMatch.setTeam(homeTeam);
            leagueMatch.setTeam(awayTeam);
            leagueMatch.setParticipants(homeTeamParticipants);
            leagueMatch.setParticipants(awayTeamParticipants);

            // 홈팀 정보 주입
            leagueMatch.setHomeTeamId(homeTeam.getTeamId());
            leagueMatch.setHomeTeamUserId(homeTeamUser.getUserId());
            leagueMatch.setHomeTeamParticipantsId(homeTeamParticipants.getParticipantsId());

            // 어웨이팀 정보 주입
            leagueMatch.setAwayTeamId(awayTeam.getTeamId());
            leagueMatch.setAwayTeamUserId(awayTeamUser.getUserId());
            leagueMatch.setAwayTeamParticipantsId(awayTeamParticipants.getParticipantsId());

            // 홈팀 정보 주입
            leagueMatch.setHomeTeamScore(leagueMatch.getHomeTeamScore());
            leagueMatch.setHomeTeamHonorScore(homeTeam.getHonorScore());
            leagueMatch.setHomeTeamName(homeTeam.getTeamName());
            leagueMatch.setHomeTeamManagerName(homeTeam.getManagerName());
            leagueMatch.setHomeTeamTotalWinRecord(homeTeam.getTotalWinRecord());
            leagueMatch.setHomeTeamTotalDrawRecord(homeTeam.getTotalDrawRecord());
            leagueMatch.setHomeTeamTotalLoseRecord(homeTeam.getTotalLoseRecord());
            leagueMatch.setHomeTeamLevelType(homeTeam.getLevelType());
            leagueMatch.setHomeTeamAgeType(homeTeam.getAgeType());
            leagueMatch.setHomeTeamUniformType(homeTeam.getUniformType());
            leagueMatch.setHomeTeamLeagueMatchPoints(homeTeamParticipants.getLeagueMatchPoints());
            leagueMatch.setHomeTeamLeagueWinRecord(homeTeamParticipants.getLeagueWinRecord());
            leagueMatch.setHomeTeamLeagueDrawRecord(homeTeamParticipants.getLeagueDrawRecord());
            leagueMatch.setHomeTeamLeagueLoseRecord(homeTeamParticipants.getLeagueLoseRecord());
            leagueMatch.setHomeTeamMatchResultStatus(leagueMatch.getHomeTeamMatchResultStatus());

            // 어웨이팀 정보 주입
            leagueMatch.setAwayTeamScore(leagueMatch.getAwayTeamScore());
            leagueMatch.setAwayTeamHonorScore(awayTeam.getHonorScore());
            leagueMatch.setAwayTeamName(awayTeam.getTeamName());
            leagueMatch.setAwayTeamManagerName(awayTeam.getManagerName());
            leagueMatch.setAwayTeamTotalWinRecord(awayTeam.getTotalWinRecord());
            leagueMatch.setAwayTeamTotalDrawRecord(awayTeam.getTotalDrawRecord());
            leagueMatch.setAwayTeamTotalLoseRecord(awayTeam.getTotalLoseRecord());
            leagueMatch.setAwayTeamLevelType(awayTeam.getLevelType());
            leagueMatch.setAwayTeamAgeType(awayTeam.getAgeType());
            leagueMatch.setAwayTeamUniformType(awayTeam.getUniformType());
            leagueMatch.setAwayTeamLeagueMatchPoints(awayTeamParticipants.getLeagueMatchPoints());
            leagueMatch.setAwayTeamLeagueWinRecord(awayTeamParticipants.getLeagueWinRecord());
            leagueMatch.setAwayTeamLeagueDrawRecord(awayTeamParticipants.getLeagueDrawRecord());
            leagueMatch.setAwayTeamLeagueLoseRecord(awayTeamParticipants.getLeagueLoseRecord());
            leagueMatch.setAwayTeamMatchResultStatus(leagueMatch.getAwayTeamMatchResultStatus());

            leagueMatchRepository.save(leagueMatch);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.LEAGUE_MATCH_NOT_CREATED);
        }
        return leagueMatch;
    }

    public LeagueMatch updateLeagueMatch(LeagueMatch leagueMatch, Long leagueMatchId, Long homeTeamId, Long awayTeamId) {
        try {
            Long loginUserId = userService.getLoginUser().getUserId();
            if (loginUserId == null) {
                throw new BusinessLogicException(Exceptions.USER_ID_IS_NULL);
            }

            if (leagueMatchId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            LeagueMatch findLeagueMatch = findByLeagueMatchId(leagueMatchId);

            User loginUser = userService.getLoginUser();
            String loginUserName = loginUser.getName();
            long teamId = loginUser.getTeamId();
            Team loginTeam = teamService.findByTeamId(teamId);
//            if ((!Objects.equals(league.getManagerName(), loginUserName))) {
//                if ((!Objects.equals(homeTeam.getManagerName(), loginTeam.getManagerName()))) {
//                    if ((!Objects.equals(awayTeam.getManagerName(), loginTeam.getManagerName()))) {
//                        throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
//                    }
//                    throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
//                }
//                throw new BusinessLogicException(Exceptions.UNAUTHORIZED);
//            }

            Optional.ofNullable(leagueMatch.getHomeTeamHonorScore())
                    .ifPresent(findLeagueMatch::setHomeTeamHonorScore);

            Optional.ofNullable(leagueMatch.getHomeTeamName())
                    .ifPresent(findLeagueMatch::setHomeTeamName);

            Optional.ofNullable(leagueMatch.getHomeTeamManagerName())
                    .ifPresent(findLeagueMatch::setHomeTeamManagerName);

            Optional.ofNullable(leagueMatch.getHomeTeamTotalWinRecord())
                    .ifPresent(findLeagueMatch::setHomeTeamTotalWinRecord);

            Optional.ofNullable(leagueMatch.getHomeTeamTotalDrawRecord())
                    .ifPresent(findLeagueMatch::setHomeTeamTotalDrawRecord);

            Optional.ofNullable(leagueMatch.getHomeTeamTotalLoseRecord())
                    .ifPresent(findLeagueMatch::setHomeTeamTotalLoseRecord);


            Optional.ofNullable(leagueMatch.getHomeTeamLevelType())
                    .ifPresent(findLeagueMatch::setHomeTeamLevelType);

            Optional.ofNullable(leagueMatch.getHomeTeamAgeType())
                    .ifPresent(findLeagueMatch::setHomeTeamAgeType);

            Optional.ofNullable(leagueMatch.getHomeTeamUniformType())
                    .ifPresent(findLeagueMatch::setHomeTeamUniformType);

            Optional.ofNullable(leagueMatch.getHomeTeamLeagueMatchPoints())
                    .ifPresent(findLeagueMatch::setHomeTeamLeagueMatchPoints);

            Optional.ofNullable(leagueMatch.getHomeTeamLeagueWinRecord())
                    .ifPresent(findLeagueMatch::setHomeTeamLeagueWinRecord);

            Optional.ofNullable(leagueMatch.getHomeTeamLeagueDrawRecord())
                    .ifPresent(findLeagueMatch::setHomeTeamLeagueDrawRecord);

            Optional.ofNullable(leagueMatch.getHomeTeamLeagueLoseRecord())
                    .ifPresent(findLeagueMatch::setHomeTeamLeagueLoseRecord);

            Optional.ofNullable(leagueMatch.getAwayTeamHonorScore())
                    .ifPresent(findLeagueMatch::setAwayTeamHonorScore);

            Optional.ofNullable(leagueMatch.getAwayTeamName())
                    .ifPresent(findLeagueMatch::setAwayTeamName);

            Optional.ofNullable(leagueMatch.getAwayTeamManagerName())
                    .ifPresent(findLeagueMatch::setAwayTeamManagerName);

            Optional.ofNullable(leagueMatch.getAwayTeamTotalWinRecord())
                    .ifPresent(findLeagueMatch::setAwayTeamTotalWinRecord);

            Optional.ofNullable(leagueMatch.getAwayTeamTotalDrawRecord())
                    .ifPresent(findLeagueMatch::setAwayTeamTotalDrawRecord);

            Optional.ofNullable(leagueMatch.getAwayTeamTotalLoseRecord())
                    .ifPresent(findLeagueMatch::setAwayTeamTotalLoseRecord);


            Optional.ofNullable(leagueMatch.getAwayTeamLevelType())
                    .ifPresent(findLeagueMatch::setAwayTeamLevelType);

            Optional.ofNullable(leagueMatch.getAwayTeamAgeType())
                    .ifPresent(findLeagueMatch::setAwayTeamAgeType);

            Optional.ofNullable(leagueMatch.getAwayTeamUniformType())
                    .ifPresent(findLeagueMatch::setAwayTeamUniformType);

            Optional.ofNullable(leagueMatch.getAwayTeamLeagueMatchPoints())
                    .ifPresent(findLeagueMatch::setAwayTeamLeagueMatchPoints);

            Optional.ofNullable(leagueMatch.getAwayTeamLeagueWinRecord())
                    .ifPresent(findLeagueMatch::setAwayTeamLeagueWinRecord);

            Optional.ofNullable(leagueMatch.getAwayTeamLeagueDrawRecord())
                    .ifPresent(findLeagueMatch::setAwayTeamLeagueDrawRecord);

            Optional.ofNullable(leagueMatch.getAwayTeamLeagueLoseRecord())
                    .ifPresent(findLeagueMatch::setAwayTeamLeagueLoseRecord);

            Optional.ofNullable(leagueMatch.getMatchType())
                    .ifPresent(findLeagueMatch::setMatchType);

            Optional.ofNullable(leagueMatch.getMatchTime())
                    .ifPresent(findLeagueMatch::setMatchTime);

            Optional.ofNullable(leagueMatch.getMatchStatus())
                    .ifPresent(findLeagueMatch::setMatchStatus);

            Optional.ofNullable(leagueMatch.getHomeTeamMatchResultStatus())
                    .ifPresent(findLeagueMatch::setHomeTeamMatchResultStatus);

            Optional.ofNullable(leagueMatch.getAwayTeamMatchResultStatus())
                    .ifPresent(findLeagueMatch::setAwayTeamMatchResultStatus);

            leagueMatchRepository.save(findLeagueMatch);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.LEAGUE_MATCH_NOT_PATCHED);
        }
        return leagueMatch;
    }

    public LeagueMatch updateLeagueMatchEnd(LeagueMatch leagueMatch
            , Long leagueMatchId
    ) {
        try {
            if (leagueMatchId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }

            LeagueMatch findLeagueMatch = findByLeagueMatchId(leagueMatchId);

            Optional.ofNullable(leagueMatch.getHomeTeamScore())
                    .ifPresent(findLeagueMatch::setHomeTeamScore);

            Optional.ofNullable(leagueMatch.getAwayTeamScore())
                    .ifPresent(findLeagueMatch::setAwayTeamScore);

            Optional.ofNullable(leagueMatch.getMatchStatus())
                    .ifPresent(findLeagueMatch::setMatchStatus);

            leagueMatchRepository.save(findLeagueMatch);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.LEAGUE_MATCH_NOT_PATCHED);
        }
        return leagueMatch;
    }

    public void updateForLeagueMatchEnd(
            Long homeTeamScore
            , Long awayTeamScore
            , Long leagueMatchId
    ) {
        try {
            if (leagueMatchId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            //리그 매치 정보 수정
            LeagueMatch findLeagueMatch = findByLeagueMatchId(leagueMatchId);

            //homeTeam 이긴 경우
            if(homeTeamScore>awayTeamScore){
                findLeagueMatch.setHomeTeamHonorScore(findLeagueMatch.getHomeTeamHonorScore()+300L);
                findLeagueMatch.setHomeTeamTotalWinRecord(findLeagueMatch.getHomeTeamTotalWinRecord()+1L);
                findLeagueMatch.setHomeTeamLeagueMatchPoints(findLeagueMatch.getHomeTeamLeagueMatchPoints()+3L);
                findLeagueMatch.setHomeTeamLeagueWinRecord(findLeagueMatch.getHomeTeamLeagueWinRecord()+1L);
                findLeagueMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf("WIN"));

                findLeagueMatch.setAwayTeamHonorScore(findLeagueMatch.getAwayTeamHonorScore()+10L);
                findLeagueMatch.setAwayTeamTotalLoseRecord(findLeagueMatch.getAwayTeamTotalLoseRecord()+1L);
                findLeagueMatch.setAwayTeamLeagueLoseRecord(findLeagueMatch.getAwayTeamLeagueLoseRecord()+1L);
                findLeagueMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf("LOSE"));

                //homeTeam 패배한 경우
            } else if(homeTeamScore<awayTeamScore){
                findLeagueMatch.setHomeTeamHonorScore(findLeagueMatch.getHomeTeamHonorScore()+10L);
                findLeagueMatch.setHomeTeamTotalLoseRecord(findLeagueMatch.getHomeTeamTotalLoseRecord()+1L);
                findLeagueMatch.setHomeTeamLeagueLoseRecord(findLeagueMatch.getHomeTeamLeagueLoseRecord()+1L);
                findLeagueMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf("LOSE"));

                findLeagueMatch.setAwayTeamHonorScore(findLeagueMatch.getAwayTeamHonorScore()+300L);
                findLeagueMatch.setAwayTeamTotalWinRecord(findLeagueMatch.getAwayTeamTotalWinRecord()+1L);
                findLeagueMatch.setAwayTeamLeagueMatchPoints(findLeagueMatch.getAwayTeamLeagueMatchPoints()+3L);
                findLeagueMatch.setAwayTeamLeagueWinRecord(findLeagueMatch.getAwayTeamLeagueWinRecord()+1L);
                findLeagueMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf("WIM"));

                //무승부인 경우
            } else {
                findLeagueMatch.setHomeTeamHonorScore(findLeagueMatch.getHomeTeamHonorScore()+100L);
                findLeagueMatch.setHomeTeamTotalDrawRecord(findLeagueMatch.getHomeTeamTotalDrawRecord()+1L);
                findLeagueMatch.setHomeTeamLeagueMatchPoints(findLeagueMatch.getHomeTeamLeagueMatchPoints()+1L);
                findLeagueMatch.setHomeTeamLeagueDrawRecord(findLeagueMatch.getHomeTeamLeagueDrawRecord()+1L);
                findLeagueMatch.setHomeTeamMatchResultStatus(MatchResultStatus.valueOf("DRAW"));

                findLeagueMatch.setAwayTeamHonorScore(findLeagueMatch.getAwayTeamHonorScore()+100L);
                findLeagueMatch.setAwayTeamTotalDrawRecord(findLeagueMatch.getAwayTeamTotalDrawRecord()+1L);
                findLeagueMatch.setAwayTeamLeagueMatchPoints(findLeagueMatch.getAwayTeamLeagueMatchPoints()+1L);
                findLeagueMatch.setAwayTeamLeagueDrawRecord(findLeagueMatch.getAwayTeamLeagueDrawRecord()+1L);
                findLeagueMatch.setAwayTeamMatchResultStatus(MatchResultStatus.valueOf("DRAW"));
            }
            leagueMatchRepository.save(findLeagueMatch);
            log.info("LEAGUE_MATCH_END TO LEAGUE_MATCH_REPOSITORY:{}", findLeagueMatch);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.LEAGUE_MATCH_NOT_PATCHED);
        }
    }

    public Page<LeagueMatch> findLeagueMatches(int page, int size) {
        return leagueMatchRepository.findAll(PageRequest.of(page, size,
                Sort.by("leagueMatchId").descending()));
    }

    public List<LeagueMatch> findAllSearch(String keyword){
        return leagueMatchRepository.findAllSearch(keyword);
    }

    public List<LeagueMatch> findAllSearchByUserName(String name){
        return leagueMatchRepository.findAllSearchByUserName(name);
    }

    public List<LeagueMatch> findLeagueMatchesNewest() {
        return leagueMatchRepository.findLeagueMatchesNewest();
    }

    public List<LeagueMatch> findLeagueMatchesLatest() {
        return leagueMatchRepository.findLeagueMatchesLatest();
    }

    public void deleteLeagueMatch(Long leagueMatchId) {
        try {
            LeagueMatch findLeagueMatch = findByLeagueMatchId(leagueMatchId);
            leagueMatchRepository.delete(findLeagueMatch);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new BusinessLogicException(Exceptions.LEAGUE_MATCH_NOT_DELETED);
        }
    }

    public LeagueMatch findByLeagueMatchId(Long leagueMatchId) {
        Optional<LeagueMatch> optionalLeagueMatch = leagueMatchRepository.findById(leagueMatchId);

        return optionalLeagueMatch.orElseThrow(() ->
                new BusinessLogicException(Exceptions.LEAGUE_MATCH_NOT_FOUND));
    }
}