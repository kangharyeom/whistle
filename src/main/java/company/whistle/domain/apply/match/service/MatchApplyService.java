package company.whistle.domain.apply.match.service;

import company.whistle.domain.apply.match.entity.MatchApply;
import company.whistle.domain.apply.match.repository.MatchApplyRepository;
import company.whistle.domain.match.unrank.entity.Match;
import company.whistle.domain.match.unrank.service.MatchService;
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
 * LeagueApplyService
 */
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MatchApplyService {
    private final MatchApplyRepository matchApplyRepository;
    private final UserService userService;
    private final TeamService teamService;
    private final MatchService matchService;

    /*
     * MatchApply 생성
     * user, team, match가 존재하는지 확인 후 team, user, match가 존재하면 applyRepository에 저장
     */
    public MatchApply createMatchApply(MatchApply matchApply, Long userId, Long matchId, Long teamId) {
        try {
            if (userId == null || matchId == null || teamId == null) {
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(userId);
            Match match = matchService.findMatch(matchId);
            Team team = teamService.findTeam(teamId);

            matchApply.setUser(user);
            matchApply.setMatch(match);
            matchApply.setTeam(team);

            matchApply.setManagerName(user.getName());
            matchApply.setTeamName(team.getTeamName());
            matchApply.setLevelType(team.getLevelType());
            matchApply.setAgeType(team.getAgeType());
            matchApply.setApplyType(matchApply.getApplyType());

            matchApplyRepository.save(matchApply);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(Exceptions.MATCH_APPLY_NOT_CREATED);
        }
        log.info("MATCH_APPLY CREATED:{}", matchApply);
        return matchApply;
    }

    public MatchApply findMatchApply(Long matchApplyId) {
        return findVerifiedMatchApply(matchApplyId);
    }

    public List<MatchApply> findAllByTeamId(Long teamId){
        return matchApplyRepository.findAllByTeamId(teamId);
    }

    public List<MatchApply> findAllByMatchId(Long matchId){
        return matchApplyRepository.findAllByMatchId(matchId);
    }

    public List<MatchApply> findAllByMatchApplyId(Long matchApplyId){
        return matchApplyRepository.findAllByMatchApplyId(matchApplyId);
    }

    public void deleteMatchApply(Long matchApplyId) {
        try {
            MatchApply findMatchApply = findVerifiedMatchApply(matchApplyId);
            matchApplyRepository.delete(findMatchApply);
        } catch (Exception e) {
            throw new BusinessLogicException(Exceptions.MATCH_APPLY_NOT_DELETED);
        }
    }

    /*
     * apply 검증 로직
     * repository에 Apply가 없는 경우 exception을 리턴
     */
    public MatchApply findVerifiedMatchApply(Long matchApplyId) {
        Optional<MatchApply> optionalApply = matchApplyRepository.findById(matchApplyId);

        MatchApply findMatchApply =
                optionalApply.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.MATCH_APPLY_NOT_FOUND));
        log.info("APPLY EXIST: {}", findMatchApply.toString());
        return findMatchApply;
    }
}
