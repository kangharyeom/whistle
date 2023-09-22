package company.board_project.team.controller;

import company.board_project.list.teamlist.entity.TeamList;
import company.board_project.list.teamlist.service.TeamListService;
import company.board_project.response.MultiResponseDto;
import company.board_project.team.dto.TeamListDto;
import company.board_project.team.dto.TeamPatchDto;
import company.board_project.team.dto.TeamPostDto;
import company.board_project.team.dto.TeamResponseDto;
import company.board_project.team.entity.Team;
import company.board_project.team.mapper.TeamMapper;
import company.board_project.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final TeamListService teamListService;
    @PostMapping
    public ResponseEntity postTeam(@Valid @RequestBody TeamPostDto requestBody ){
        Team team = teamService.createTeam(
                teamMapper.teamPostDtoToTeam(requestBody),
                requestBody.getUserId()
        );
        TeamResponseDto teamResponseDto = teamMapper.teamToTeamResponseDto(team);

        teamListService.createTeamListByTeamController(new TeamList(), teamResponseDto.getTeamId(),teamResponseDto.getUserId());

        return ResponseEntity.ok(teamResponseDto);
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity patchTeam(@Valid @RequestBody TeamPatchDto requestBody,
                                       @PathVariable("teamId") @Positive Long teamId){
        Team team = teamService.updateTeam(
                teamMapper.teamPatchDtoToTeam(requestBody),
                teamId);

        team.setTeamId(teamId);
        TeamResponseDto userResponseDto = teamMapper.teamToTeamResponseDto(team);

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity getTeam(@PathVariable("teamId") @Positive Long teamId){
        Team team = teamService.findTeam(teamId);
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

    @DeleteMapping("/{teamId}")
    public ResponseEntity deleteTeam(@PathVariable("teamId") @Positive Long teamId) {
        teamService.deleteTeam(teamId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}

