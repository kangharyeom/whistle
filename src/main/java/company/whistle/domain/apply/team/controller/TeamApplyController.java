package company.whistle.domain.apply.team.controller;

import company.whistle.domain.apply.team.dto.*;
import company.whistle.domain.apply.team.entity.TeamApply;
import company.whistle.domain.apply.team.mapper.TeamApplyMapper;
import company.whistle.domain.apply.team.service.TeamApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/applies/teams")
public class TeamApplyController {
    private final TeamApplyService teamApplyService;
    private final TeamApplyMapper teamApplyMapper;

    /*
    * 팀 가입 신청
    */
    @PostMapping({"/{teamId}"})
    public ResponseEntity<TeamApplyResponseDto> postTeamApply(@Validated @RequestBody TeamApplyPostDto requestBody) {
        TeamApply teamApply = teamApplyService.createTeamApply(
                teamApplyMapper.teamApplyPostDtoToTeamApply(requestBody),requestBody.getUserId(), requestBody.getTeamId());

        TeamApplyResponseDto teamApplyResponseDto = teamApplyMapper.teamApplyToTeamApplyResponse(teamApply);
        log.info("TEAM_APPLY POST COMPLETE: {}",teamApplyResponseDto.toString());

        return ResponseEntity.ok(teamApplyResponseDto);
    }

    /*
     * teamApplyId 단위 팀 가입 신청 단건 조회
     */
    @GetMapping("/{teamId}/{teamApplyId}")
    public ResponseEntity<TeamApplyResponseDto> getTeamAppliesByTeamApplyId(@PathVariable("teamApplyId") Long teamApplyId){

        TeamApply teamApply = teamApplyService.findTeamApply(teamApplyId);
        TeamApplyResponseDto teamApplyResponseDto = teamApplyMapper.teamApplyToTeamApplyResponse(teamApply);
        log.info("TEAM_APPLIY BY TEAM_APPLY_ID INFO:" + teamApplyResponseDto);
        return ResponseEntity.ok(teamApplyResponseDto);
    }

    /*
     * teamId 단위 팀 가입 신청 전체 조회
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<TeamApplyListDto> getTeamAppliesByTeamId(@PathVariable("teamId") Long teamId){

        List<TeamApply> teamApplies = teamApplyService.findAllByTeamApplyId(teamId);
        log.info("TOTAL TEAM_APPLIES INFO:" + teamApplies);
        return new ResponseEntity<>(teamApplyMapper.teamApplyListDtoToTeamApplyResponse(teamApplies),
                HttpStatus.OK);
    }

    /*
     * apply 제거
     */
    @DeleteMapping("/{teamId}/{teamApplyId}")
    public ResponseEntity<HttpStatus> deleteTeamApply(@PathVariable("teamApplyId") Long teamApplyId) {
        teamApplyService.deleteTeamApply(teamApplyId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

