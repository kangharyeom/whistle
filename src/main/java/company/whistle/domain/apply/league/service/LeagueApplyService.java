package company.whistle.domain.apply.league.service;

import company.whistle.domain.apply.league.entity.LeagueApply;
import company.whistle.domain.apply.league.repository.LeagueApplyRepository;
import company.whistle.domain.league.domain.entity.League;
import company.whistle.domain.league.domain.service.LeagueService;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.team.domain.service.TeamService;
import company.whistle.domain.user.entity.User;
import company.whistle.domain.user.service.UserService;
import company.whistle.global.exception.BusinessLogicException;
import company.whistle.global.exception.Exceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/*
 * ApplyService
 */
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class LeagueApplyService {
    private final LeagueApplyRepository leagueApplyRepository;
    private final UserService userService;
    private final TeamService teamService;
    private final LeagueService leagueService;

    /*
     * LeagueApply 생성
     * user, team, league가 존재하는지 확인 후 team, user, league가 존재하면 applyRepository에 저장
     */
    public LeagueApply createLeagueApply(LeagueApply leagueApply, Long userId, Long leagueId, Long teamId) {
        try {
            if (userId == null || leagueId == null || teamId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(userId);
            League league = leagueService.findLeague(leagueId);
            Team team = teamService.findTeam(teamId);

            leagueApply.setLeague(league);
            leagueApply.setUser(user);
            leagueApply.setTeam(team);
            leagueApply.setManagerName(user.getName());
            leagueApply.setTeamName(team.getTeamName());
            leagueApply.setLevelType(team.getLevelType());
            leagueApply.setAgeType(team.getAgeType());
            leagueApply.setApplyType(leagueApply.getApplyType());

            leagueApplyRepository.save(leagueApply);
        } catch (Exception e) {
            throw new BusinessLogicException(Exceptions.LEAGUE_APPLY_NOT_CREATED);
        }
        log.info("LEAGUE_APPLY CREATED:{}", leagueApply);
        return leagueApply;
    }

    public LeagueApply findLeagueApply(Long leagueApplyId) {
        return findVerifiedLeagueApply(leagueApplyId);
    }

    public List<LeagueApply> findAllByLeagueId(Long leagueId){
        return leagueApplyRepository.findAllByLeagueId(leagueId);
    }

    public void deleteApply(Long leagueApplyId) {
        try {
            LeagueApply findLeagueApply = findVerifiedLeagueApply(leagueApplyId);
            leagueApplyRepository.delete(findLeagueApply);
        } catch (Exception e) {
            throw new BusinessLogicException(Exceptions.LEAGUE_APPLY_NOT_DELETED);
        }
    }

    /*
     * apply 검증 로직
     * repository에 Apply가 없는 경우 exception을 리턴
     */
    public LeagueApply findVerifiedLeagueApply(Long leagueApplyId) {
        Optional<LeagueApply> optionalApply = leagueApplyRepository.findById(leagueApplyId);

        LeagueApply findLeagueApply =
                optionalApply.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.LEAGUE_APPLY_NOT_FOUND));
        log.info("APPLY EXIST: {}", findLeagueApply.toString());
        return findLeagueApply;
    }
}
