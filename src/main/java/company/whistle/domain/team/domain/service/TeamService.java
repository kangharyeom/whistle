package company.whistle.domain.team.domain.service;

import company.whistle.domain.user.repository.UserRepository;
import company.whistle.global.constant.TeamMemberRole;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.team.domain.repository.TeamRepository;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    public Team createTeam(
            Team team, Long userId, String teamName) {
        try {
            if (userId == null) {
                log.info("userId: {}", userId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            checkDuplUserId(userId);
            checkDuplTeamName(teamName);

            User user = userService.findUser(userId);

            user.setTeamMemberRole(TeamMemberRole.MANAGER);
            team.setUser(user);
            team.setManagerName(user.getName());

            userRepository.save(user);
            teamRepository.save(team);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(Exceptions.TEAM_NOT_CREATED);
        }
        return team;
    }

    public Team updateTeam(
            Team team,
            Long teamId) {
        try {
            if (teamId == null) {
                log.info("teamId: {}", teamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Team findTeam = findVerifiedTeam(teamId); //ID로 멤버 존재 확인하고 comment 정보 반환

            Optional.ofNullable(team.getChampionCount())
                    .ifPresent(findTeam::setChampionCount);

            Optional.ofNullable(team.getMemberCount())
                    .ifPresent(findTeam::setMemberCount);

            Optional.ofNullable(team.getLeagueWinRecord())
                    .ifPresent(findTeam::setLeagueWinRecord);

            Optional.ofNullable(team.getLeagueDrawRecord())
                    .ifPresent(findTeam::setLeagueDrawRecord);

            Optional.ofNullable(team.getLeagueLoseRecord())
                    .ifPresent(findTeam::setLeagueLoseRecord);

            Optional.ofNullable(team.getTotalWinRecord())
                    .ifPresent(findTeam::setTotalWinRecord);

            Optional.ofNullable(team.getTotalDrawRecord())
                    .ifPresent(findTeam::setTotalDrawRecord);

            Optional.ofNullable(team.getTotalLoseRecord())
                    .ifPresent(findTeam::setTotalLoseRecord);

            Optional.ofNullable(team.getHonorScore())
                    .ifPresent(findTeam::setHonorScore);

            Optional.ofNullable(team.getMostGoals())
                    .ifPresent(findTeam::setMostGoals);

            Optional.ofNullable(team.getMostAssist())
                    .ifPresent(findTeam::setMostAssist);

            Optional.ofNullable(team.getMostMom())
                    .ifPresent(findTeam::setMostMom);

            Optional.ofNullable(team.getIntroduction())
                    .ifPresent(findTeam::setIntroduction);

            Optional.ofNullable(team.getAgeType())
                    .ifPresent(findTeam::setAgeType);

            Optional.ofNullable(team.getLocationType())
                    .ifPresent(findTeam::setLocationType);

            Optional.ofNullable(team.getManagerName())
                    .ifPresent(findTeam::setManagerName);

            Optional.ofNullable(team.getSubManagerName())
                    .ifPresent(findTeam::setSubManagerName);

            Optional.ofNullable(team.getUniformType())
                    .ifPresent(findTeam::setUniformType);
            teamRepository.save(findTeam);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(Exceptions.TEAM_NOT_PATCHED);
        }
        return team;
    }

    public void updateForMatchEnd(
            Long homeTeamScore,
            Long awayTeamScore,
            Long homeTeamId,
            Long awayTeamId
    ) {
        try {
            if (homeTeamId == null || awayTeamId == null) {
                log.info("homeTeamId: {}", homeTeamId);
                log.info("awayTeamId: {}", awayTeamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Team findHomeTeam = findVerifiedTeam(homeTeamId);
            Team findAwayTeam = findVerifiedTeam(awayTeamId);
            if(homeTeamScore>awayTeamScore){
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+300L);
                findHomeTeam.setTotalWinRecord(findHomeTeam.getTotalWinRecord()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+10L);
                findAwayTeam.setTotalLoseRecord(findAwayTeam.getTotalLoseRecord()+1L);

                //homeTeam 패배한 경우
            } else if(homeTeamScore<awayTeamScore){
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+10L);
                findHomeTeam.setTotalLoseRecord(findHomeTeam.getTotalLoseRecord()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+300L);
                findAwayTeam.setTotalWinRecord(findAwayTeam.getTotalWinRecord()+1L);

                //무승부인 경우
            } else {
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+100L);
                findHomeTeam.setTotalDrawRecord(findHomeTeam.getTotalDrawRecord()+1L);
                findHomeTeam.setLeagueMatchPoints(findHomeTeam.getLeagueMatchPoints()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+100L);
                findAwayTeam.setTotalDrawRecord(findAwayTeam.getTotalDrawRecord()+1L);

            }
            teamRepository.save(findHomeTeam);
            teamRepository.save(findAwayTeam);
            log.info("UPDATE_FOR_MATCH_END ABOUT: HOME_TEAM TO TEAM_REPOSITORY:{}", findHomeTeam);
            log.info("UPDATE_FOR_MATCH_END ABOUT: AWAY_TEAM TO TEAM_REPOSITORY:{}", findAwayTeam);
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(e.getExceptions());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.TEAM_NOT_PATCHED);
        }
    }

    public void updateForLeagueMatchEnd(
            Long homeTeamScore,
            Long awayTeamScore,
            Long homeTeamId,
            Long awayTeamId
    ) {
        try {
            if (homeTeamId == null || awayTeamId == null) {
                log.info("homeTeamId: {}", homeTeamId);
                log.info("awayTeamId: {}", awayTeamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            Team findHomeTeam = findVerifiedTeam(homeTeamId);
            Team findAwayTeam = findVerifiedTeam(awayTeamId);
            if(homeTeamScore>awayTeamScore){
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+300L);
                findHomeTeam.setTotalWinRecord(findHomeTeam.getTotalWinRecord()+1L);
                findHomeTeam.setLeagueMatchCount(findHomeTeam.getLeagueMatchCount()+1L);
                findHomeTeam.setLeagueMatchPoints(findHomeTeam.getLeagueMatchPoints()+3L);
                findHomeTeam.setLeagueWinRecord(findHomeTeam.getLeagueWinRecord()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+10L);
                findAwayTeam.setTotalLoseRecord(findAwayTeam.getTotalLoseRecord()+1L);
                findAwayTeam.setLeagueLoseRecord(findAwayTeam.getLeagueLoseRecord()+1L);
                findAwayTeam.setLeagueMatchCount(findAwayTeam.getLeagueMatchCount()+1L);

                //homeTeam 패배한 경우
            } else if(homeTeamScore<awayTeamScore){
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+10L);
                findHomeTeam.setTotalLoseRecord(findHomeTeam.getTotalLoseRecord()+1L);
                findHomeTeam.setLeagueLoseRecord(findHomeTeam.getLeagueLoseRecord()+1L);
                findHomeTeam.setLeagueMatchCount(findHomeTeam.getLeagueMatchCount()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+300L);
                findAwayTeam.setTotalWinRecord(findAwayTeam.getTotalWinRecord()+1L);
                findAwayTeam.setLeagueMatchPoints(findAwayTeam.getLeagueMatchPoints()+3L);
                findAwayTeam.setLeagueWinRecord(findAwayTeam.getLeagueWinRecord()+1L);
                findAwayTeam.setLeagueMatchCount(findAwayTeam.getLeagueMatchCount()+1L);

                //무승부인 경우
            } else {
                findHomeTeam.setHonorScore(findHomeTeam.getHonorScore()+100L);
                findHomeTeam.setTotalDrawRecord(findHomeTeam.getTotalDrawRecord()+1L);
                findHomeTeam.setLeagueMatchPoints(findHomeTeam.getLeagueMatchPoints()+1L);
                findHomeTeam.setLeagueDrawRecord(findHomeTeam.getLeagueDrawRecord()+1L);
                findHomeTeam.setLeagueMatchCount(findHomeTeam.getLeagueMatchCount()+1L);

                findAwayTeam.setHonorScore(findAwayTeam.getHonorScore()+100L);
                findAwayTeam.setTotalDrawRecord(findAwayTeam.getTotalDrawRecord()+1L);
                findAwayTeam.setLeagueMatchPoints(findAwayTeam.getLeagueMatchPoints()+1L);
                findAwayTeam.setLeagueDrawRecord(findAwayTeam.getLeagueDrawRecord()+1L);
                findAwayTeam.setLeagueMatchCount(findAwayTeam.getLeagueMatchCount()+1L);
            }
            teamRepository.save(findHomeTeam);
            teamRepository.save(findAwayTeam);
            log.info("LEAGUE_MATCH_END ABOUT HOME_TEAM TO TEAM_REPOSITORY:{}", findHomeTeam);
            log.info("LEAGUE_MATCH_END ABOUT AWAY_TEAM TO TEAM_REPOSITORY:{}", findAwayTeam);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.TEAM_NOT_PATCHED);
        }
    }

    public Team findTeam(long teamId) {
        return findVerifiedTeam(teamId);
    }

    public Team findTeamByUserId(long userId) {
        return findVerifiedTeamByUserId(userId);
    }

    public List<Team> findAllTeamsByLeagueId(long leagueId) {return teamRepository.findAllTeamsByLeagueId(leagueId);}

    // 명예 점수 상위 조회
    public List<Team> findByHighestHonorScore() {return teamRepository.findByHighestHonorScore();}

    // 명예 점수 하위 조회
    public List<Team> findByLowestHonorScore() {return teamRepository.findByLowestHonorScore();}

    public Page<Team> findTeams(int page, int size) {
        return teamRepository.findAll(PageRequest.of(page, size,
                Sort.by("teamId").descending()));
    }

    public Team findTeam(int teamId) {
        return findVerifiedTeam(teamId);
    }

    public void deleteTeam(long teamId) {
        try {
            Team findTeam = findVerifiedTeam(teamId);
            teamRepository.delete(findTeam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.TEAM_NOT_DELETED);
        }
    }

    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        return optionalTeam.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.TEAM_NOT_FOUND));
    }

    public Team findVerifiedTeamByUserId(long userId) {
        Team team;
        try {
            team = teamRepository.findByUserId(userId);
        } catch (NoSuchElementException ex) {
            throw new BusinessLogicException(Exceptions.TEAM_NOT_FOUND);
        }
        return team;
    }

    public void checkDuplUserId(long userId) {
        Team team = teamRepository.findByUserId(userId);
        if(team !=null) {
            throw new BusinessLogicException(Exceptions.USER_ALREADY_HAVE_TEAM);
        }
    }

    public void checkDuplTeamName(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        if(team !=null) {
            throw new BusinessLogicException(Exceptions.TEAM_NAME_EXISTS);
        }
    }
}
