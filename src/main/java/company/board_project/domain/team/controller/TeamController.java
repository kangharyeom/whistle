package company.board_project.domain.team.controller;

import company.board_project.domain.team.dto.*;
import company.board_project.domain.team.entity.TeamMemberList;
import company.board_project.response.MultiResponseDto;
import company.board_project.domain.team.entity.Team;
import company.board_project.domain.team.mapper.TeamMapper;
import company.board_project.domain.team.service.TeamService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @Transactional
    @PostMapping
    public ResponseEntity postTeam(@Valid @RequestBody TeamPostDto requestBody) {
        Team team = teamService.createTeamAndTeamMemberList(teamMapper.teamPostDtoToTeam(requestBody), requestBody.getUserId());
        TeamResponseDto teamResponseDto = teamMapper.teamToTeamResponseDto(team);
        log.info("teamResponseDto.getTeamId() : {}", teamResponseDto.getTeamId());
        log.info("teamResponseDto.getUserId() : {}", teamResponseDto.getUserId());
        log.info("requestBody.getUserId() : {}", requestBody.getUserId());

        TeamMemberListPostDto teamMemberListPostDto = new TeamMemberListPostDto();
        teamMemberListPostDto.setName(teamResponseDto.getManagerName());
        teamMemberListPostDto.setTeamMemberRole("MANAGER");
        log.info("teamMemberListPostDto[{}]", teamMemberListPostDto.toString());
        teamService.createTeamMemberList(teamMapper.teamMemberListPostDtoToTeam(teamMemberListPostDto),teamResponseDto.getUserId(),teamResponseDto.getTeamId());

        return ResponseEntity.ok(teamResponseDto);
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity patchTeam(@Valid @RequestBody TeamPatchDto requestBody,
                                    @PathVariable("teamId") @Positive Long teamId) {
        Team team = teamService.updateTeam(
                teamMapper.teamPatchDtoToTeam(requestBody),
                teamId);

        team.setTeamId(teamId);
        TeamResponseDto userResponseDto = teamMapper.teamToTeamResponseDto(team);

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity getTeam(@PathVariable("teamId") @Positive Long teamId) {
        Team team = teamService.findTeam(teamId);
        TeamResponseDto teamResponse = teamMapper.teamToTeamResponseDto(team);
        log.info("팀 리스 폰스 {}", teamResponse);

        return ResponseEntity.ok(teamResponse);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getTeamByUserId(@PathVariable("userId") @Positive Long userId) {
        Team team = teamService.findTeamByUserId(userId);
        TeamResponseDto teamResponse = teamMapper.teamToTeamResponseDto(team);

        return ResponseEntity.ok(teamResponse);
    }


    @GetMapping("/league/{leagueId}")
    public ResponseEntity getAllTeamsByLeagueId(@PathVariable("leagueId") @Positive Long leagueId) {
        List<Team> teams = teamService.findAllTeamsByLeagueId(leagueId);
        TeamListDto teamListDto = teamMapper.teamListDtoToTeamResponse(teams);

        return new ResponseEntity<>(teamListDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTeams(@Positive @RequestParam("page") int page,
                                   @Positive @RequestParam("size") int size) {
        Page<Team> pageTeams = teamService.findTeams(page - 1, size);
        List<Team> teams = pageTeams.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(teamMapper.teamsToTeamResponse(teams),
                        pageTeams),
                HttpStatus.OK);
    }

    @GetMapping("/honor/high")
    public ResponseEntity getTeamsByHighestHonorScore() {
        List<Team> teams = teamService.findByHighestHonorScore();
        List<TeamResponseDto> teamResponseDtos = teamMapper.teamsToTeamResponse(teams);

        return ResponseEntity.ok(teamResponseDtos);
    }

    @GetMapping("/honor/low")
    public ResponseEntity getTeamsByLowestHonorScore() {
        List<Team> teams = teamService.findByLowestHonorScore();
        List<TeamResponseDto> teamResponseDtos = teamMapper.teamsToTeamResponse(teams);

        return ResponseEntity.ok(teamResponseDtos);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity deleteTeam(@PathVariable("teamId") @Positive Long teamId) {
        teamService.deleteTeam(teamId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

