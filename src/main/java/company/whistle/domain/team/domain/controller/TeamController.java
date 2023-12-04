package company.whistle.domain.team.domain.controller;

import company.whistle.domain.team.domain.dto.*;
import company.whistle.domain.team.squad.entity.Squad;
import company.whistle.domain.team.squad.service.SquadService;
import company.whistle.domain.team.domain.entity.Team;
import company.whistle.domain.team.domain.mapper.TeamMapper;
import company.whistle.domain.team.domain.service.TeamService;
import company.whistle.global.response.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final SquadService squadService;
    @PostMapping
    public ResponseEntity<TeamResponseDto> postTeam(@Valid @RequestBody TeamPostDto requestBody ){
        Team team = teamService.createTeam(
                teamMapper.teamPostDtoToTeam(requestBody), requestBody.getUserId(), requestBody.getTeamName());
        TeamResponseDto teamResponseDto = teamMapper.teamToTeamResponseDto(team);
        log.info("TEAM CREATE COMPLETE: {}", teamResponseDto.toString());

        Squad squad = squadService.createSquadByTeamController(
                teamMapper.squadPostDtoToSquad(requestBody), teamResponseDto.getTeamId(),requestBody.getUserId());
        SquadResponseDto squadResponseDto = teamMapper.squadToSquadResponse(squad);
        log.info("SQUAD CREATE COMPLETE: {}", squadResponseDto.toString());

        return ResponseEntity.ok(teamResponseDto);
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> patchTeam(@Valid @RequestBody TeamPatchDto requestBody,
                                       @PathVariable("teamId") @Positive Long teamId){
        Team team = teamService.updateTeam(
                teamMapper.teamPatchDtoToTeam(requestBody),
                teamId);

        team.setTeamId(teamId);
        TeamResponseDto userResponseDto = teamMapper.teamToTeamResponseDto(team);

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getTeam(@PathVariable("teamId") @Positive Long teamId){
        Team team = teamService.findTeam(teamId);
        TeamResponseDto teamResponse = teamMapper.teamToTeamResponseDto(team);
        log.info("팀 리스 폰스 {}",teamResponse);

        return ResponseEntity.ok(teamResponse);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<TeamResponseDto> getTeamByUserId(@PathVariable("userId") @Positive Long userId){
        Team team = teamService.findTeamByUserId(userId);
        TeamResponseDto teamResponse = teamMapper.teamToTeamResponseDto(team);

        return ResponseEntity.ok(teamResponse);
    }


    @GetMapping("/league/{leagueId}")
    public ResponseEntity<TeamListDto> getAllTeamsByLeagueId(@PathVariable("leagueId") @Positive Long leagueId) {
        List<Team> teams = teamService.findAllTeamsByLeagueId(leagueId);
        TeamListDto teamListDto = teamMapper.teamListDtoToTeamResponse(teams);

        return new ResponseEntity<>(teamListDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<TeamResponseDto>> getTeams(@Positive @RequestParam("page") int page,
                                      @Positive @RequestParam("size") int size) {
        Page<Team> pageTeams = teamService.findTeams(page - 1, size);
        List<Team> teams = pageTeams.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(teamMapper.teamsToTeamResponse(teams),
                        pageTeams),
                HttpStatus.OK);
    }

    @GetMapping("/honor/high")
    public ResponseEntity<List<TeamResponseDto>> getTeamsByHighestHonorScore() {
        List<Team> teams = teamService.findByHighestHonorScore();
        List<TeamResponseDto> teamResponseDtos = teamMapper.teamsToTeamResponse(teams);

        return ResponseEntity.ok(teamResponseDtos);
    }

    @GetMapping("/honor/low")
    public ResponseEntity<List<TeamResponseDto>> getTeamsByLowestHonorScore() {
        List<Team> teams = teamService.findByLowestHonorScore();
        List<TeamResponseDto> teamResponseDtos = teamMapper.teamsToTeamResponse(teams);

        return ResponseEntity.ok(teamResponseDtos);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("teamId") @Positive Long teamId) {
        teamService.deleteTeam(teamId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

