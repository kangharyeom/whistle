package company.whistle.domain.team.squad.controller;

import company.whistle.domain.team.squad.dto.SquadPatchDto;
import company.whistle.domain.team.squad.dto.SquadPostDto;
import company.whistle.domain.team.squad.dto.SquadResponseDto;
import company.whistle.domain.team.squad.entity.Squad;
import company.whistle.domain.team.squad.mapper.SquadMapper;
import company.whistle.domain.team.squad.service.SquadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Validated
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/teams/{teamId}/squads")
public class SquadController {
    private final SquadService squadService;
    private final SquadMapper squadMapper;

    @PostMapping
    public ResponseEntity<SquadResponseDto> postSquad(@RequestBody SquadPostDto requestBody){

        Squad squad = squadService.createSquad(squadMapper.squadPostDtoToSquad(requestBody), requestBody.getUserId(), requestBody.getTeamId(), requestBody.getApplyId());
        SquadResponseDto squadResponse = squadMapper.squadToSquadResponse(squad);

        return ResponseEntity.ok(squadResponse);
    }

    @GetMapping("/{squadId}")
    public ResponseEntity<SquadResponseDto> getSquad(@PathVariable("squadId") Long squadId) {
        Squad squad = squadService.findSquad(squadId);
        SquadResponseDto squadResponseDto = squadMapper.squadToSquadResponse(squad);

        return ResponseEntity.ok(squadResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<SquadResponseDto>> getSquads(){

        List<Squad> squads = squadService.findSquads();
        log.info("전체 요청 :" + squads);
        return new ResponseEntity<>(squadMapper.squadsToSquadResponse(squads),
                HttpStatus.OK);
    }

    @GetMapping("/newest")
    public ResponseEntity<List<SquadResponseDto>> getSquadsNewest() {
        List<Squad> squads = squadService.findSquadsNewest();
        List<SquadResponseDto> squadResponseDtos = squadMapper.squadsToSquadResponse(squads);

        return ResponseEntity.ok(squadResponseDtos);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<SquadResponseDto>> getSquadsLatest() {
        List<Squad> squads = squadService.findSquadsLatest();
        List<SquadResponseDto> squadResponseDtos = squadMapper.squadsToSquadResponse(squads);

        return ResponseEntity.ok(squadResponseDtos);
    }

    @GetMapping("/score")
    public ResponseEntity<List<SquadResponseDto>> getSquadsHonorScore() {
        List<Squad> squads = squadService.findHonorScore();
        List<SquadResponseDto> squadResponseDtos = squadMapper.squadsToSquadResponse(squads);

        return ResponseEntity.ok(squadResponseDtos);
    }

    @PatchMapping("/{squadId}")
    public ResponseEntity<SquadResponseDto> patchSquad(@RequestBody SquadPatchDto requestBody,
                                      @PathVariable("squadId") Long squadId) {
        Squad squad = squadService.updateSquad(
                squadMapper.squadPatchDtoToSquad(requestBody), squadId);

        SquadResponseDto squadResponse = squadMapper.squadToSquadResponse(squad);

        return ResponseEntity.ok(squadResponse);
    }

    @DeleteMapping("/{squadId}")
    public ResponseEntity<HttpStatus> deleteSquad(@PathVariable("squadId") Long squadId) {
        squadService.deleteSquad(squadId);

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
