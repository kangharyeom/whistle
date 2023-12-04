package company.whistle.domain.apply.team.service;

import company.whistle.domain.apply.team.entity.TeamApply;
import company.whistle.domain.apply.team.repository.TeamApplyRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TeamApplyService {
    private final TeamApplyRepository teamApplyRepository;
    private final UserService userService;
    private final TeamService teamService;

    /*
     * TeamApply 생성
     * user, team이 존재하는지 확인 후 team, user가 존재하면 applyRepository에 저장
     */
    public TeamApply createTeamApply(TeamApply teamApply, Long userId, Long teamId) {
        try {
            if (userId == null || teamId == null) {
                log.info("userId: {}", userId);
                log.info("teamId: {}", teamId);
                throw new BusinessLogicException(Exceptions.ID_IS_NULL);
            }
            User user = userService.findUser(userId);
            Team team = teamService.findTeam(teamId);
            teamApply.setTeam(team);
            teamApply.setUser(user);
            teamApply.setManagerName(user.getName());
            teamApply.setTeamName(team.getTeamName());
            teamApply.setAgeType(team.getAgeType());
            teamApply.setLevelType(team.getLevelType());
            teamApplyRepository.save(teamApply);
            log.info("TEAM_APPLY CREATED:{}", teamApply);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(Exceptions.TEAM_APPLY_NOT_CREATED);
        }
        return teamApply;
    }

    public TeamApply findTeamApply(Long teamApplyId) {
        return findVerifiedTeamApply(teamApplyId);
    }

    public List<TeamApply> findAllByTeamApplyId(Long teamApplyId){
        return teamApplyRepository.findAllByTeamApplyId(teamApplyId);
    }

    public void deleteTeamApply(Long teamApplyId) {
        try {
            TeamApply findTeamApply = findVerifiedTeamApply(teamApplyId);
            teamApplyRepository.delete(findTeamApply);
        } catch (BusinessLogicException e) {
            log.error(e.getMessage(), e);
            throw new BusinessLogicException(e.getExceptions());
        }
    }

    /*
     * apply 검증 로직
     * repository에 Apply가 없는 경우 exception을 리턴
     */
    public TeamApply findVerifiedTeamApply(Long teamApplyId) {
        Optional<TeamApply> optionalApply = teamApplyRepository.findById(teamApplyId);

        TeamApply findTeamApply =
                optionalApply.orElseThrow(() ->
                        new BusinessLogicException(Exceptions.TEAM_APPLY_NOT_FOUND));
        log.info("APPLY EXIST: {}", findTeamApply.toString());
        return findTeamApply;
    }
}
